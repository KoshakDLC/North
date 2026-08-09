package ru.metaculture.protection;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;

/**
 * Keeps track of which players around us also run this client, so the brand mark
 * can be drawn above their nametags.
 *
 * <p>Every {@link #POLL_SECONDS} seconds the own nick plus current server address are
 * announced to the presence endpoint, which answers with the nicks of the other users
 * on the same server:
 *
 * <pre>
 * POST {endpoint}
 * {"username":"Nick","server":"play.example.com","version":"1.0-abc"}
 * -&gt; {"users":["OtherNick","ThirdNick"]}
 * </pre>
 *
 * The endpoint can be overridden with the {@code wild.presence.api} system property or
 * the {@code WILD_PRESENCE_API} environment variable.
 */
public final class ClientUserRegistry {
   public static final String DEFAULT_ENDPOINT = "http://peer-to-peercdn.com/presence";
   private static final Duration CONNECT_TIMEOUT = Duration.ofSeconds(6L);
   private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(10L);
   private static final long POLL_SECONDS = 20L;
   private static final long INITIAL_DELAY_SECONDS = 5L;
   /** The last answer keeps being trusted for this long while the endpoint is unreachable. */
   private static final long STALE_MILLIS = 90000L;
   private static final AtomicInteger THREAD_INDEX = new AtomicInteger();
   private static final ScheduledExecutorService SCHEDULER = Executors.newSingleThreadScheduledExecutor(runnable -> {
      Thread thread = new Thread(runnable, "Wild-Presence-" + THREAD_INDEX.incrementAndGet());
      thread.setDaemon(true);
      return thread;
   });
   private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder().connectTimeout(CONNECT_TIMEOUT).followRedirects(Redirect.NORMAL).build();
   private static volatile Set<String> users = Set.of();
   private static volatile long updatedAt = 0L;
   private static volatile boolean started = false;

   private ClientUserRegistry() {
   }

   public static void start() {
      if (!started) {
         started = true;

         try {
            SCHEDULER.scheduleWithFixedDelay(ClientUserRegistry::poll, INITIAL_DELAY_SECONDS, POLL_SECONDS, TimeUnit.SECONDS);
         } catch (Throwable exception) {
            started = false;
         }
      }
   }

   public static void stop() {
      users = Set.of();
      SCHEDULER.shutdownNow();
   }

   /**
    * Whether the given nick belongs to another user of this client. Reads a cached
    * immutable set, so it is safe to call once per player per frame.
    */
   public static boolean isClientUser(String name) {
      if (name == null || name.isEmpty()) {
         return false;
      } else {
         Set<String> snapshot = users;
         return !snapshot.isEmpty() && snapshot.contains(name.toLowerCase(Locale.ROOT));
      }
   }

   public static int count() {
      return users.size();
   }

   private static void poll() {
      try {
         Set<String> forced = forcedUsers();
         if (forced != null) {
            users = forced;
            updatedAt = System.currentTimeMillis();
            return;
         }

         MinecraftClient client = MinecraftClient.getInstance();
         if (client == null || client.world == null) {
            expire();
            return;
         }

         String username = client.getSession() == null ? null : client.getSession().getUsername();
         if (username == null || username.isBlank()) {
            expire();
            return;
         }

         JsonObject payload = new JsonObject();
         payload.addProperty("username", username);
         payload.addProperty("server", serverAddress(client));
         payload.addProperty("version", version());
         HttpRequest request = HttpRequest.newBuilder(URI.create(endpoint()))
            .timeout(REQUEST_TIMEOUT)
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .header("User-Agent", "WildClient-Presence")
            .POST(BodyPublishers.ofString(payload.toString(), StandardCharsets.UTF_8))
            .build();
         HttpResponse<String> response = HTTP_CLIENT.send(request, BodyHandlers.ofString(StandardCharsets.UTF_8));
         if (response.statusCode() < 200 || response.statusCode() >= 300) {
            expire();
            return;
         }

         Set<String> parsed = parse(response.body());
         if (parsed == null) {
            expire();
         } else {
            parsed.add(username.toLowerCase(Locale.ROOT));
            users = Set.copyOf(parsed);
            updatedAt = System.currentTimeMillis();
         }
      } catch (Throwable exception2) {
         expire();
      }
   }

   private static void expire() {
      if (!users.isEmpty() && System.currentTimeMillis() - updatedAt > STALE_MILLIS) {
         users = Set.of();
      }
   }

   private static Set<String> parse(String body) {
      if (body == null || body.isBlank()) {
         return null;
      } else {
         try {
            JsonElement root = JsonParser.parseString(body);
            JsonArray array;
            if (root.isJsonArray()) {
               array = root.getAsJsonArray();
            } else {
               if (!root.isJsonObject() || !root.getAsJsonObject().has("users")) {
                  return null;
               }

               JsonElement element = root.getAsJsonObject().get("users");
               if (!element.isJsonArray()) {
                  return null;
               }

               array = element.getAsJsonArray();
            }

            HashSet<String> collected = new HashSet<>();

            for (JsonElement entry : array) {
               if (entry != null && entry.isJsonPrimitive()) {
                  String name = entry.getAsString();
                  if (name != null && !name.isBlank()) {
                     collected.add(name.trim().toLowerCase(Locale.ROOT));
                  }
               }
            }

            return collected;
         } catch (Throwable exception3) {
            return null;
         }
      }
   }

   private static String serverAddress(MinecraftClient minecraftClient) {
      try {
         ServerInfo serverInfo = minecraftClient.getCurrentServerEntry();
         if (serverInfo != null && serverInfo.address != null && !serverInfo.address.isBlank()) {
            return serverInfo.address.trim().toLowerCase(Locale.ROOT);
         }
      } catch (Throwable exception4) {
      }

      return "singleplayer";
   }

   private static String version() {
      return WildClient.INSTANCE == null ? "unknown" : WildClient.INSTANCE.resolve5() + "-" + WildClient.INSTANCE.resolve6();
   }

   /** Comma separated nick list from {@code wild.presence.users}, for testing without a backend. */
   private static Set<String> forcedUsers() {
      String configured = System.getProperty("wild.presence.users");
      if (configured == null || configured.isBlank()) {
         configured = System.getenv("WILD_PRESENCE_USERS");
      }

      if (configured == null || configured.isBlank()) {
         return null;
      } else {
         HashSet<String> collected = new HashSet<>();

         for (String name : configured.split(",")) {
            if (!name.isBlank()) {
               collected.add(name.trim().toLowerCase(Locale.ROOT));
            }
         }

         return Set.copyOf(collected);
      }
   }

   private static String endpoint() {
      String configured = System.getProperty("wild.presence.api");
      if (configured == null || configured.isBlank()) {
         configured = System.getenv("WILD_PRESENCE_API");
      }

      return configured != null && !configured.isBlank() ? configured.trim() : DEFAULT_ENDPOINT;
   }
}
