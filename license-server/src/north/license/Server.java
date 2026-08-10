package north.license;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;

/**
 * License activation API.
 *
 * <pre>
 * POST /v1/activate      {"key":"...","hwid":"..."}
 * POST /v1/validate      {"keyHash":"...","hwid":"..."}
 * POST /v1/admin/keys    {"count":5,"days":30,"role":"USER","maxDevices":1}  (X-Admin-Token)
 * POST /v1/admin/revoke  {"key":"..."}                                      (X-Admin-Token)
 * GET  /v1/health
 * </pre>
 */
public final class Server {
   private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("dd-MM-yyyy");
   private static final long SESSION_TTL_MS = Long.getLong("north.license.sessionTtlMs", 86_400_000L);

   private final Store store;
   private final PrivateKey privateKey;
   private final String adminToken;
   private final int port;

   private Server(Store store, PrivateKey privateKey, String adminToken, int port) {
      this.store = store;
      this.privateKey = privateKey;
      this.adminToken = adminToken;
      this.port = port;
   }

   public static void main(String[] args) throws Exception {
      Path root = Path.of(System.getProperty("north.license.root", "data")).toAbsolutePath().normalize();
      Files.createDirectories(root);
      ensureKeys(root);
      PrivateKey privateKey = Crypto.loadPrivate(root.resolve("private.key"));
      String admin = readOrCreate(root.resolve("admin.token"), () -> UUID.randomUUID().toString().replace("-", ""));
      int port = Integer.getInteger("north.license.port", 8787);
      Store store = new Store(root.resolve("keys.json"));
      if (args.length > 0 && "create".equalsIgnoreCase(args[0])) {
         int count = argInt(args, "--count", 1);
         int days = argInt(args, "--days", 30);
         int maxDevices = argInt(args, "--devices", 1);
         String role = argText(args, "--role", "USER");
         boolean lifetime = days <= 0 || "true".equalsIgnoreCase(argText(args, "--lifetime", "false"));
         long until = lifetime ? 0L : Instant.now().plusSeconds(days * 86400L).toEpochMilli();
         List<String> keys = store.createKeys(count, until, role, argText(args, "--prefix", "User"), maxDevices);
         if (lifetime) {
            System.out.println("Created " + keys.size() + " key(s), lifetime, role " + role + ":");
         } else {
            System.out.println("Created " + keys.size() + " key(s), valid " + days + " day(s), role " + role + ":");
         }
         for (String key : keys) {
            System.out.println("  " + key);
         }

         return;
      }

      new Server(store, privateKey, admin, port).start();
   }

   private void start() throws IOException {
      HttpServer server = HttpServer.create(new InetSocketAddress(this.port), 0);
      server.createContext("/v1/health", this::health);
      server.createContext("/v1/activate", this::activate);
      server.createContext("/v1/validate", this::validate);
      server.createContext("/v1/admin/keys", this::adminCreate);
      server.createContext("/v1/admin/revoke", this::adminRevoke);
      server.setExecutor(Executors.newCachedThreadPool());
      server.start();
      System.out.println("North license server on http://127.0.0.1:" + this.port);
      System.out.println("Admin token: " + this.adminToken);
      System.out.println("Keys in store: " + this.store.size());
   }

   private void health(HttpExchange exchange) throws IOException {
      if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
         this.json(exchange, 405, "{\"ok\":false,\"error\":\"Method not allowed\"}");
         return;
      }

      this.json(exchange, 200, "{\"ok\":true,\"service\":\"north-license\",\"keys\":" + this.store.size() + "}");
   }

   private void activate(HttpExchange exchange) throws IOException {
      if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
         this.json(exchange, 405, "{\"ok\":false,\"error\":\"Method not allowed\"}");
         return;
      }

      try {
         Map<String, Object> body = MiniJson.object(MiniJson.parse(readBody(exchange)));
         String key = MiniJson.text(body.get("key"), "");
         String hwid = MiniJson.text(body.get("hwid"), "");
         Store.ActivationResult result = this.store.activate(key, hwid);
         if (!result.ok()) {
            this.json(exchange, 403, "{\"ok\":false,\"error\":" + MiniJson.quote(result.error()) + "}");
            return;
         }

         this.json(exchange, 200, this.licenseResponse(result.record(), result.hwid()));
      } catch (Exception exception) {
         this.json(exchange, 400, "{\"ok\":false,\"error\":" + MiniJson.quote(exception.getMessage()) + "}");
      }
   }

   private void validate(HttpExchange exchange) throws IOException {
      if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
         this.json(exchange, 405, "{\"ok\":false,\"error\":\"Method not allowed\"}");
         return;
      }

      try {
         Map<String, Object> body = MiniJson.object(MiniJson.parse(readBody(exchange)));
         String keyHash = MiniJson.text(body.get("keyHash"), "");
         String hwid = MiniJson.text(body.get("hwid"), "");
         Store.ActivationResult result = this.store.validate(keyHash, hwid);
         if (!result.ok()) {
            this.json(exchange, 403, "{\"ok\":false,\"error\":" + MiniJson.quote(result.error()) + "}");
            return;
         }

         this.json(exchange, 200, this.licenseResponse(result.record(), result.hwid()));
      } catch (Exception exception) {
         this.json(exchange, 400, "{\"ok\":false,\"error\":" + MiniJson.quote(exception.getMessage()) + "}");
      }
   }

   private String licenseResponse(Store.KeyRecord record, String hwid) throws Exception {
      String hwidHash = Crypto.sha256Hex(hwid);
      String subscriptionEnd = formatDate(record.validUntilMs);
      long sessionUntil = Instant.now().toEpochMilli() + Math.max(3_600_000L, SESSION_TTL_MS);
      String payloadJson = "{"
         + "\"username\":" + MiniJson.quote(record.username) + ","
         + "\"uid\":" + record.uid + ","
         + "\"role\":" + MiniJson.quote(record.role) + ","
         + "\"hwidHash\":" + MiniJson.quote(hwidHash) + ","
         + "\"keyHash\":" + MiniJson.quote(record.keyHash) + ","
         + "\"validUntil\":" + record.validUntilMs + ","
         + "\"sessionUntil\":" + sessionUntil + ","
         + "\"subscriptionEndDate\":" + MiniJson.quote(subscriptionEnd)
         + "}";
      byte[] payloadBytes = payloadJson.getBytes(StandardCharsets.UTF_8);
      String payload = Crypto.encodePayload(payloadBytes);
      String signature = Crypto.signBase64Url(payloadBytes, this.privateKey);
      return "{"
         + "\"ok\":true,"
         + "\"license\":{\"payload\":" + MiniJson.quote(payload) + ",\"signature\":" + MiniJson.quote(signature) + "},"
         + "\"username\":" + MiniJson.quote(record.username) + ","
         + "\"uid\":" + record.uid + ","
         + "\"role\":" + MiniJson.quote(record.role) + ","
         + "\"validUntil\":" + record.validUntilMs + ","
         + "\"sessionUntil\":" + sessionUntil + ","
         + "\"subscriptionEndDate\":" + MiniJson.quote(subscriptionEnd)
         + "}";
   }

   private void adminCreate(HttpExchange exchange) throws IOException {
      if (!this.admin(exchange)) {
         return;
      }

      try {
         Map<String, Object> body = MiniJson.object(MiniJson.parse(readBody(exchange)));
         int count = (int)Math.max(1L, MiniJson.number(body.get("count"), 1L));
         int days = (int)MiniJson.number(body.get("days"), 30L);
         int maxDevices = (int)Math.max(1L, MiniJson.number(body.get("maxDevices"), 1L));
         String role = MiniJson.text(body.get("role"), "USER");
         String prefix = MiniJson.text(body.get("prefix"), "User");
         boolean lifetime = days <= 0 || MiniJson.flag(body.get("lifetime"), false);
         long until = lifetime ? 0L : Instant.now().plusSeconds(Math.max(1, days) * 86400L).toEpochMilli();
         List<String> keys = this.store.createKeys(count, until, role, prefix, maxDevices);
         StringBuilder builder = new StringBuilder("{\"ok\":true,\"validUntil\":" + until + ",\"keys\":[");
         for (int i = 0; i < keys.size(); i++) {
            if (i > 0) {
               builder.append(',');
            }

            builder.append(MiniJson.quote(keys.get(i)));
         }

         builder.append("]}");
         this.json(exchange, 200, builder.toString());
      } catch (Exception exception) {
         this.json(exchange, 400, "{\"ok\":false,\"error\":" + MiniJson.quote(exception.getMessage()) + "}");
      }
   }

   private void adminRevoke(HttpExchange exchange) throws IOException {
      if (!this.admin(exchange)) {
         return;
      }

      try {
         Map<String, Object> body = MiniJson.object(MiniJson.parse(readBody(exchange)));
         String key = MiniJson.text(body.get("key"), "");
         boolean revoked = this.store.revoke(key);
         this.json(exchange, revoked ? 200 : 404, "{\"ok\":" + revoked + "}");
      } catch (Exception exception) {
         this.json(exchange, 400, "{\"ok\":false,\"error\":" + MiniJson.quote(exception.getMessage()) + "}");
      }
   }

   private boolean admin(HttpExchange exchange) throws IOException {
      if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
         this.json(exchange, 405, "{\"ok\":false,\"error\":\"Method not allowed\"}");
         return false;
      }

      String token = exchange.getRequestHeaders().getFirst("X-Admin-Token");
      if (token == null || !token.equals(this.adminToken)) {
         this.json(exchange, 401, "{\"ok\":false,\"error\":\"Unauthorized\"}");
         return false;
      }

      return true;
   }

   private void json(HttpExchange exchange, int status, String body) throws IOException {
      byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
      Headers headers = exchange.getResponseHeaders();
      headers.set("Content-Type", "application/json; charset=utf-8");
      headers.set("Access-Control-Allow-Origin", "*");
      exchange.sendResponseHeaders(status, bytes.length);
      try (OutputStream output = exchange.getResponseBody()) {
         output.write(bytes);
      }
   }

   private static String readBody(HttpExchange exchange) throws IOException {
      return new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
   }

   private static void ensureKeys(Path root) throws Exception {
      Path privateFile = root.resolve("private.key");
      Path publicFile = root.resolve("public.key");
      if (Files.isRegularFile(privateFile) && Files.isRegularFile(publicFile)) {
         return;
      }

      KeyPair pair = Crypto.generate();
      Crypto.savePair(root, pair);
      System.out.println("Generated new Ed25519 keypair in " + root);
      System.out.println("Put this public key into LocalLicenseService / loader License:");
      System.out.println(Crypto.publicPem(pair.getPublic()));
   }

   private static String readOrCreate(Path file, java.util.concurrent.Callable<String> factory) throws Exception {
      if (Files.isRegularFile(file)) {
         return Files.readString(file, StandardCharsets.UTF_8).trim();
      }

      String value = factory.call();
      Files.writeString(file, value, StandardCharsets.UTF_8);
      return value;
   }

   private static String formatDate(long epochMs) {
      if (epochMs <= 0L) {
         return "lifetime";
      }

      return LocalDate.ofInstant(Instant.ofEpochMilli(epochMs), ZoneOffset.UTC).format(DATE);
   }

   private static int argInt(String[] args, String name, int fallback) {
      String text = argText(args, name, null);
      if (text == null) {
         return fallback;
      }

      try {
         return Integer.parseInt(text);
      } catch (NumberFormatException ignored) {
         return fallback;
      }
   }

   private static String argText(String[] args, String name, String fallback) {
      for (int i = 0; i < args.length - 1; i++) {
         if (name.equalsIgnoreCase(args[i])) {
            return args[i + 1];
         }
      }

      return fallback;
   }
}
