package wild.loader;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HexFormat;

/**
 * Activates / revalidates a license. Local signature is not enough: a short signed
 * {@code sessionUntil} forces periodic contact with the license server so revoked keys die.
 */
final class License {
   static final String DEFAULT_URL = "http://127.0.0.1:8787";
   private static final Duration TIMEOUT = Duration.ofSeconds(15L);
   private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("dd-MM-yyyy");
   /** Renew online when less than this remains on the session. */
   private static final long RENEW_BEFORE_MS = 6L * 60L * 60L * 1000L;
   private static final String PUBLIC_PEM = """
      -----BEGIN PUBLIC KEY-----
      MCowBQYDK2VwAyEAakAvrO9bPgPDjIgHhfjtizfV2iwvrXIHRb9H0paB6E4=
      -----END PUBLIC KEY-----
      """;

   private static final HttpClient CLIENT = HttpClient.newBuilder()
      .connectTimeout(Duration.ofSeconds(8L))
      .followRedirects(Redirect.NORMAL)
      .build();

   private License() {
   }

   static Path path() {
      String override = System.getenv("WILD_LICENSE_PATH");
      if (override != null && !override.isBlank()) {
         return Path.of(override);
      }

      return Config.appData().resolve("WildClient").resolve("license.json");
   }

   /**
    * Dev unlock only: create {@code %USERPROFILE%\\.north-dev-unlock} and pass
    * {@code -Dnorth.license.dev=1}. Not documented in the UI on purpose.
    */
   static boolean bypass(Config config) {
      if (!"1".equals(System.getProperty("north.license.dev"))) {
         return false;
      }

      try {
         return Files.isRegularFile(Path.of(System.getProperty("user.home", "."), ".north-dev-unlock"));
      } catch (Exception exception) {
         return false;
      }
   }

   static Status status(Config config) {
      if (bypass(config)) {
         return Status.ok("Dev unlock", Long.MAX_VALUE, Long.MAX_VALUE, "Dev", 0, "ADMIN", "");
      }

      try {
         Parsed parsed = readLocal();
         if (parsed == null) {
            return Status.missing("Лицензия не найдена — введите ключ");
         }

         if (!parsed.signatureOk) {
            return Status.missing(parsed.error);
         }

         long now = Instant.now().toEpochMilli();
         if (parsed.validUntil > 0L && parsed.validUntil <= now) {
            return Status.missing("Срок лицензии истёк (" + formatDate(parsed.validUntil) + ")");
         }

         if (parsed.hwidHash.isBlank() || !parsed.hwidHash.equalsIgnoreCase(hwidHashOf(Config.hardwareId()))) {
            return Status.missing("Лицензия привязана к другому HWID");
         }

         if (parsed.sessionUntil > 0L && parsed.sessionUntil <= now) {
            return Status.missing("Сессия истекла — нужна онлайн-проверка");
         }

         return Status.ok(
            "Активна до " + formatDate(parsed.validUntil),
            parsed.validUntil,
            parsed.sessionUntil,
            parsed.username,
            parsed.uid,
            parsed.role,
            parsed.keyHash
         );
      } catch (Exception exception) {
         return Status.missing("Не удалось прочитать лицензию: " + message(exception));
      }
   }

   static Status activate(Config config, String rawKey) {
      String key = rawKey == null ? "" : rawKey.trim();
      if (key.isEmpty()) {
         return Status.missing("Введите ключ");
      }

      String body = "{\"key\":" + quote(key) + ",\"hwid\":" + quote(Config.hardwareId()) + "}";
      Status remote = postLicense(config, "/v1/activate", body);
      if (remote.valid) {
         config.set(Config.LICENSE_KEY, key);
         config.save();
      }

      return remote;
   }

   static Status validateOnline(Config config, String keyHash) {
      if (keyHash == null || keyHash.isBlank()) {
         String saved = config.get(Config.LICENSE_KEY, "");
         if (!saved.isBlank()) {
            return activate(config, saved);
         }

         return Status.missing("Нет keyHash для проверки");
      }

      String body = "{\"keyHash\":" + quote(keyHash) + ",\"hwid\":" + quote(Config.hardwareId()) + "}";
      return postLicense(config, "/v1/validate", body);
   }

   /** Local check + online renew; revoked keys are wiped. */
   static Status ensure(Config config) {
      if (bypass(config)) {
         return status(config);
      }

      Status local = status(config);
      long now = Instant.now().toEpochMilli();
      boolean needsRenew = !local.valid
         || local.sessionUntil <= 0L
         || local.sessionUntil - now <= RENEW_BEFORE_MS;

      if (!needsRenew) {
         return local;
      }

      Status online;
      if (local.keyHash != null && !local.keyHash.isBlank()) {
         online = validateOnline(config, local.keyHash);
      } else {
         String saved = config.get(Config.LICENSE_KEY, "");
         online = saved.isBlank() ? Status.missing(local.message) : activate(config, saved);
      }

      if (online.valid) {
         return online;
      }

      if (isRevoked(online.message)) {
         wipe();
         return Status.missing(online.message);
      }

      // Offline grace: only while the signed session is still alive.
      if (local.valid && local.sessionUntil > now) {
         return local;
      }

      return Status.missing(online.message.isBlank() ? local.message : online.message);
   }

   static void wipe() {
      try {
         Files.deleteIfExists(path());
      } catch (Exception ignored) {
      }
   }

   static String hwidHashOf(String hwid) {
      try {
         byte[] digest = MessageDigest.getInstance("SHA-256").digest(hwid.getBytes(StandardCharsets.UTF_8));
         return HexFormat.of().formatHex(digest);
      } catch (Exception exception) {
         throw new IllegalStateException(exception);
      }
   }

   private static Status postLicense(Config config, String path, String body) {
      String base = config.get(Config.LICENSE_URL, DEFAULT_URL).replaceAll("/+$", "");
      try {
         HttpRequest request = HttpRequest.newBuilder(URI.create(base + path))
            .timeout(TIMEOUT)
            .header("Content-Type", "application/json")
            .header("User-Agent", "NorthLoader")
            .POST(BodyPublishers.ofString(body, StandardCharsets.UTF_8))
            .build();
         HttpResponse<String> response = CLIENT.send(request, BodyHandlers.ofString(StandardCharsets.UTF_8));
         Object json = Json.parse(response.body());
         boolean ok = Json.flag(Json.get(json, "ok"), false);
         if (!ok || response.statusCode() < 200 || response.statusCode() >= 300) {
            return Status.missing(Json.text(Json.get(json, "error"), "Сервер ответил HTTP " + response.statusCode()));
         }

         Object license = Json.get(json, "license");
         String payload = Json.text(Json.get(license, "payload"), "");
         String signature = Json.text(Json.get(license, "signature"), "");
         if (payload.isBlank() || signature.isBlank()) {
            return Status.missing("Сервер не вернул лицензию");
         }

         Path file = path();
         Path parent = file.getParent();
         if (parent != null) {
            Files.createDirectories(parent);
         }

         Files.writeString(file, "{\n  \"payload\": " + quote(payload) + ",\n  \"signature\": " + quote(signature) + "\n}\n", StandardCharsets.UTF_8);
         return status(config);
      } catch (InterruptedException interruption) {
         Thread.currentThread().interrupt();
         return Status.missing("Проверка прервана");
      } catch (Exception exception) {
         return Status.missing("Сервер лицензий недоступен: " + message(exception));
      }
   }

   private static Parsed readLocal() throws Exception {
      Path file = path();
      if (!Files.isRegularFile(file)) {
         return null;
      }

      Object root = Json.parse(Files.readString(file, StandardCharsets.UTF_8));
      String payloadB64 = Json.text(Json.get(root, "payload"), "");
      String signatureB64 = Json.text(Json.get(root, "signature"), "");
      if (payloadB64.isBlank() || signatureB64.isBlank()) {
         return Parsed.bad("Файл лицензии повреждён");
      }

      byte[] payload = Base64.getUrlDecoder().decode(pad(payloadB64));
      byte[] signature = Base64.getUrlDecoder().decode(pad(signatureB64));
      if (!verify(payload, signature)) {
         return Parsed.bad("Подпись лицензии неверна");
      }

      Object body = Json.parse(new String(payload, StandardCharsets.UTF_8));
      return new Parsed(
         true,
         "",
         Json.number(Json.get(body, "validUntil"), 0L),
         Json.number(Json.get(body, "sessionUntil"), 0L),
         Json.text(Json.get(body, "username"), "User"),
         (int)Json.number(Json.get(body, "uid"), 0L),
         Json.text(Json.get(body, "role"), "USER"),
         Json.text(Json.get(body, "hwidHash"), ""),
         Json.text(Json.get(body, "keyHash"), "")
      );
   }

   private static boolean isRevoked(String message) {
      if (message == null) {
         return false;
      }

      String lower = message.toLowerCase();
      return lower.contains("отозван") || lower.contains("revoked") || lower.contains("не найден") || lower.contains("не привязан");
   }

   private static boolean verify(byte[] payload, byte[] signature) throws Exception {
      Signature verifier = Signature.getInstance("Ed25519");
      verifier.initVerify(publicKey());
      verifier.update(payload);
      return verifier.verify(signature);
   }

   private static PublicKey publicKey() throws Exception {
      String raw = PUBLIC_PEM.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "").replaceAll("\\s+", "");
      return KeyFactory.getInstance("Ed25519").generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(raw)));
   }

   private static String pad(String value) {
      int mod = value.length() % 4;
      return mod == 0 ? value : value + "====".substring(mod);
   }

   private static String formatDate(long epochMs) {
      if (epochMs <= 0L || epochMs == Long.MAX_VALUE) {
         return "бессрочно";
      }

      return LocalDate.ofInstant(Instant.ofEpochMilli(epochMs), ZoneOffset.systemDefault()).format(DATE);
   }

   private static String quote(String value) {
      StringBuilder builder = new StringBuilder("\"");
      for (int i = 0; i < value.length(); i++) {
         char c = value.charAt(i);
         switch (c) {
            case '"' -> builder.append("\\\"");
            case '\\' -> builder.append("\\\\");
            case '\n' -> builder.append("\\n");
            case '\r' -> builder.append("\\r");
            case '\t' -> builder.append("\\t");
            default -> builder.append(c);
         }
      }

      return builder.append('"').toString();
   }

   private static String message(Exception exception) {
      String text = exception.getMessage();
      return text == null || text.isBlank() ? exception.getClass().getSimpleName() : text;
   }

   private record Parsed(
      boolean signatureOk,
      String error,
      long validUntil,
      long sessionUntil,
      String username,
      int uid,
      String role,
      String hwidHash,
      String keyHash
   ) {
      static Parsed bad(String error) {
         return new Parsed(false, error, 0L, 0L, "", 0, "", "", "");
      }
   }

   record Status(boolean valid, String message, long validUntil, long sessionUntil, String username, int uid, String role, String keyHash) {
      static Status ok(String message, long validUntil, long sessionUntil, String username, int uid, String role, String keyHash) {
         return new Status(true, message, validUntil, sessionUntil, username, uid, role, keyHash);
      }

      static Status missing(String message) {
         return new Status(false, message == null ? "" : message, 0L, 0L, "", 0, "", "");
      }
   }
}
