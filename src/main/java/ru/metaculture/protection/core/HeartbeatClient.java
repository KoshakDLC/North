package ru.metaculture.protection;

import com.google.gson.JsonObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

public final class HeartbeatClient {
   public static final String HTTP_PEER_TO_PEERCDN_COM_PING = "http://peer-to-peercdn.com/ping";
   private static final Duration DURATION = Duration.ofSeconds(10L);
   private static final Duration DURATION_2 = Duration.ofSeconds(15L);
   private static final long TIMESTAMP = 1L;
   private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger();
   private static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor(runnable -> {
      Thread thread = new Thread(runnable, "Wild-Heartbeat-" + ATOMIC_INTEGER.incrementAndGet());
      thread.setDaemon(true);
      return thread;
   });
   private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder().connectTimeout(DURATION).followRedirects(Redirect.NORMAL).build();
   private static volatile String text = null;

   private HeartbeatClient() {
   }

   public static void invoke() {
   }

   public static void invoke2() {
   }

   private static void invoke3() {
      ProtectionHandler.checkAccess();

      try {
         String fingerprint = text;
         if (fingerprint == null) {
            fingerprint = HwidUtils.resolve();
            text = fingerprint;
         }

         FingerprintCrypto.FingerprintCryptoTimedEntry fingerprintCryptoTimedEntry = ru.metaculture.protection.FingerprintCrypto.resolve(fingerprint);
         JsonObject jsonObject = new JsonObject();
         jsonObject.addProperty("v", fingerprintCryptoTimedEntry.v());
         jsonObject.addProperty("kid", "ping-1");
         jsonObject.addProperty("encryptedPayload", fingerprintCryptoTimedEntry.encryptedPayload());
         jsonObject.addProperty("timestamp", fingerprintCryptoTimedEntry.timestamp());
         jsonObject.addProperty("requestId", fingerprintCryptoTimedEntry.requestId());
         String text2 = resolve();
         HttpRequest httpRequest = HttpRequest.newBuilder(URI.create("http://peer-to-peercdn.com/ping"))
            .timeout(DURATION_2)
            .header("Content-Type", "application/json")
            .header("User-Agent", "WildClient/" + text2)
            .POST(BodyPublishers.ofString(jsonObject.toString(), StandardCharsets.UTF_8))
            .build();
         HTTP_CLIENT.sendAsync(httpRequest, BodyHandlers.discarding()).exceptionally(throwable -> null);
      } catch (GuardException guardException) {
         throw ProtectionHandler.resolve(guardException);
      } catch (FingerprintCrypto.FingerprintCryptoVariant2 fingerprintCryptoVariant2) {
         SCHEDULED_EXECUTOR_SERVICE.shutdownNow();
      } catch (Throwable exception) {
      }
   }

   private static String resolve() {
      return WildClient.INSTANCE == null ? "unknown" : WildClient.INSTANCE.resolve5() + "-" + WildClient.INSTANCE.resolve6();
   }
}
