package ru.metaculture.protection;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Optional;

public final class LocalLicenseService {
   private static final long CACHE_TTL_NS = Long.getLong("wild.license.cacheTtlMs", 15_000L) * 1_000_000L;
   private static final String PUBLIC_PEM = """
      -----BEGIN PUBLIC KEY-----
      MCowBQYDK2VwAyEAakAvrO9bPgPDjIgHhfjtizfV2iwvrXIHRb9H0paB6E4=
      -----END PUBLIC KEY-----
      """;
   private static volatile LocalLicenseService.LocalLicenseServiceData cache;

   private LocalLicenseService() {
   }

   public static boolean check() {
      if (devUnlock()) {
         return true;
      }

      return snapshot().valid();
   }

   public static boolean check2() {
      return check();
   }

   public static Optional<JsonObject> payload() {
      if (devUnlock()) {
         return Optional.empty();
      }

      LocalLicenseServiceData data = snapshot();
      return data.valid() && data.payload() != null ? Optional.of(data.payload()) : Optional.empty();
   }

   public static long validUntil() {
      return snapshot().validUntil();
   }

   public static long sessionUntil() {
      return snapshot().sessionUntil();
   }

   public static String keyHash() {
      JsonObject payload = payload().orElse(null);
      return payload != null && payload.has("keyHash") ? payload.get("keyHash").getAsString() : "";
   }

   public static void invalidateCache() {
      cache = null;
   }

   public static void wipe() {
      try {
         Path path = resolvePath();
         if (path != null) {
            Files.deleteIfExists(path);
         }
      } catch (Throwable ignored) {
      }

      cache = null;
   }

   private static LocalLicenseServiceData snapshot() {
      long nowNano = System.nanoTime();
      LocalLicenseServiceData current = cache;
      if (current != null && nowNano - current.checkedAtNano() < CACHE_TTL_NS) {
         return current;
      }

      LocalLicenseServiceData fresh = resolve(nowNano, System.currentTimeMillis());
      cache = fresh;
      return fresh;
   }

   /** Hidden: {@code -Dnorth.license.dev=1} + file {@code ~/.north-dev-unlock}. */
   private static boolean devUnlock() {
      if (!"1".equals(System.getProperty("north.license.dev"))) {
         return false;
      }

      try {
         return Files.isRegularFile(Path.of(System.getProperty("user.home", "."), ".north-dev-unlock"));
      } catch (Throwable exception) {
         return false;
      }
   }

   private static LocalLicenseServiceData resolve(long checkedAtNano, long nowMs) {
      long validUntil = 0L;
      long sessionUntil = 0L;

      Path path;
      try {
         path = resolvePath();
      } catch (Throwable exception) {
         return new LocalLicenseServiceData(false, validUntil, sessionUntil, checkedAtNano, true, null);
      }

      if (path == null || !Files.exists(path)) {
         return new LocalLicenseServiceData(false, validUntil, sessionUntil, checkedAtNano, true, null);
      }

      try {
         JsonObject root = JsonParser.parseString(Files.readString(path, StandardCharsets.UTF_8)).getAsJsonObject();
         String payloadB64 = root.get("payload").getAsString();
         String signatureB64 = root.get("signature").getAsString();
         byte[] payloadBytes = Base64.getUrlDecoder().decode(payloadB64);
         byte[] signatureBytes = Base64.getUrlDecoder().decode(signatureB64);
         if (!verify(payloadBytes, signatureBytes, publicKey())) {
            return new LocalLicenseServiceData(false, validUntil, sessionUntil, checkedAtNano, false, null);
         }

         JsonObject payload = JsonParser.parseString(new String(payloadBytes, StandardCharsets.UTF_8)).getAsJsonObject();
         validUntil = payload.has("validUntil") ? payload.get("validUntil").getAsLong() : 0L;
         sessionUntil = payload.has("sessionUntil") ? payload.get("sessionUntil").getAsLong() : 0L;
         if (validUntil > 0L && validUntil <= nowMs) {
            return new LocalLicenseServiceData(false, validUntil, sessionUntil, checkedAtNano, false, null);
         }

         if (sessionUntil > 0L && sessionUntil <= nowMs) {
            return new LocalLicenseServiceData(false, validUntil, sessionUntil, checkedAtNano, false, null);
         }

         // Old licenses without sessionUntil are rejected — force re-activation.
         if (sessionUntil <= 0L) {
            return new LocalLicenseServiceData(false, validUntil, sessionUntil, checkedAtNano, false, null);
         }

         String hwidHash = payload.has("hwidHash") ? payload.get("hwidHash").getAsString() : "";
         if (hwidHash.isBlank() || !HwidUtils.check(hwidHash)) {
            return new LocalLicenseServiceData(false, validUntil, sessionUntil, checkedAtNano, false, null);
         }

         return new LocalLicenseServiceData(true, validUntil, sessionUntil, checkedAtNano, false, payload);
      } catch (Throwable exception) {
         return new LocalLicenseServiceData(false, validUntil, sessionUntil, checkedAtNano, false, null);
      }
   }

   private static Path resolvePath() {
      String property = System.getProperty("wild.license.path");
      if (property != null && !property.isBlank()) {
         return Path.of(property);
      }

      String env = System.getenv("WILD_LICENSE_PATH");
      if (env != null && !env.isBlank()) {
         return Path.of(env);
      }

      String appData = System.getenv("APPDATA");
      if (appData != null && !appData.isBlank()) {
         Path windows = Path.of(appData, "WildClient", "license.json");
         if (Files.exists(windows)) {
            return windows;
         }
      }

      return Path.of(System.getProperty("user.home", "."), ".wildclient", "license.json");
   }

   private static PublicKey publicKey() throws Exception {
      String raw = PUBLIC_PEM.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "").replaceAll("\\s+", "");
      return KeyFactory.getInstance("Ed25519").generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(raw)));
   }

   private static boolean verify(byte[] payload, byte[] signature, PublicKey publicKey) throws Exception {
      Signature verifier = Signature.getInstance("Ed25519");
      verifier.initVerify(publicKey);
      verifier.update(payload);
      return verifier.verify(signature);
   }

   record LocalLicenseServiceData(
      boolean valid, long validUntil, long sessionUntil, long checkedAtNano, boolean fileMissing, JsonObject payload
   ) {
   }
}
