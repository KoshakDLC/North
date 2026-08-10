package ru.metaculture.protection;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Periodic online revalidation. If the key is revoked or the session cannot be renewed,
 * the local license is wiped and modules are expected to stop via {@link LocalLicenseService#check()}.
 */
public final class OnlineLicenseGuard {
   private static final Duration TIMEOUT = Duration.ofSeconds(12L);
   private static final long PERIOD_MINUTES = Long.getLong("wild.license.heartbeatMinutes", 8L);
   private static final AtomicBoolean STARTED = new AtomicBoolean();
   private static final ScheduledExecutorService EXECUTOR = Executors.newSingleThreadScheduledExecutor(runnable -> {
      Thread thread = new Thread(runnable, "North-LicenseGuard");
      thread.setDaemon(true);
      return thread;
   });
   private static final HttpClient CLIENT = HttpClient.newBuilder()
      .connectTimeout(Duration.ofSeconds(6L))
      .followRedirects(Redirect.NORMAL)
      .build();

   private OnlineLicenseGuard() {
   }

   public static void start() {
      if (!STARTED.compareAndSet(false, true)) {
         return;
      }

      EXECUTOR.scheduleAtFixedRate(() -> {
         try {
            tick();
         } catch (Throwable ignored) {
         }
      }, 45L, Math.max(2L, PERIOD_MINUTES) * 60L, TimeUnit.SECONDS);
   }

   private static void tick() {
      if (!LocalLicenseService.check()) {
         return;
      }

      long sessionUntil = LocalLicenseService.sessionUntil();
      long now = System.currentTimeMillis();
      // Renew when under 6 hours remain, or every heartbeat if already close.
      if (sessionUntil - now > 6L * 60L * 60L * 1000L) {
         return;
      }

      String keyHash = LocalLicenseService.keyHash();
      if (keyHash == null || keyHash.isBlank()) {
         return;
      }

      String base = licenseUrl();
      if (base == null || base.isBlank()) {
         return;
      }

      try {
         String body = "{\"keyHash\":\"" + jsonEscape(keyHash) + "\",\"hwid\":\"" + jsonEscape(HwidUtils.resolve()) + "\"}";
         HttpRequest request = HttpRequest.newBuilder(URI.create(base.replaceAll("/+$", "") + "/v1/validate"))
            .timeout(TIMEOUT)
            .header("Content-Type", "application/json")
            .header("User-Agent", "NorthClient")
            .POST(BodyPublishers.ofString(body, StandardCharsets.UTF_8))
            .build();
         HttpResponse<String> response = CLIENT.send(request, BodyHandlers.ofString(StandardCharsets.UTF_8));
         JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
         boolean ok = json.has("ok") && json.get("ok").getAsBoolean();
         if (!ok || response.statusCode() < 200 || response.statusCode() >= 300) {
            String error = json.has("error") ? json.get("error").getAsString() : "validate failed";
            if (isFatal(error)) {
               LocalLicenseService.wipe();
            }

            return;
         }

         JsonObject license = json.getAsJsonObject("license");
         String payload = license.get("payload").getAsString();
         String signature = license.get("signature").getAsString();
         Path path = licensePath();
         Path parent = path.getParent();
         if (parent != null) {
            Files.createDirectories(parent);
         }

         Files.writeString(
            path,
            "{\n  \"payload\": \"" + jsonEscape(payload) + "\",\n  \"signature\": \"" + jsonEscape(signature) + "\"\n}\n",
            StandardCharsets.UTF_8
         );
         LocalLicenseService.invalidateCache();
      } catch (Throwable ignored) {
         // Stay on the current signed session until it expires.
      }
   }

   private static boolean isFatal(String error) {
      if (error == null) {
         return false;
      }

      String lower = error.toLowerCase();
      return lower.contains("отозван")
         || lower.contains("revoked")
         || lower.contains("не найден")
         || lower.contains("не привязан")
         || lower.contains("истёк")
         || lower.contains("истек");
   }

   private static String licenseUrl() {
      String property = System.getProperty("wild.license.url");
      if (property != null && !property.isBlank()) {
         return property.trim();
      }

      String env = System.getenv("WILD_LICENSE_URL");
      if (env != null && !env.isBlank()) {
         return env.trim();
      }

      try {
         Path props = Path.of(System.getenv().getOrDefault("APPDATA", System.getProperty("user.home")), "North", "loader.properties");
         if (Files.isRegularFile(props)) {
            Properties properties = new Properties();
            try (var stream = Files.newInputStream(props)) {
               properties.load(stream);
            }

            String url = properties.getProperty("license.url", "");
            if (url != null && !url.isBlank()) {
               return url.trim();
            }
         }
      } catch (Throwable ignored) {
      }

      return "http://127.0.0.1:8787";
   }

   private static Path licensePath() {
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
         return Path.of(appData, "WildClient", "license.json");
      }

      return Path.of(System.getProperty("user.home", "."), ".wildclient", "license.json");
   }

   private static String jsonEscape(String value) {
      return value.replace("\\", "\\\\").replace("\"", "\\\"");
   }
}
