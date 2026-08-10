package ru.metaculture.protection;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import net.minecraft.client.MinecraftClient;

public final class CloudConfigService {
   public static final String HTTPS_RAW_GITHUBUSERCONTENT_COM_MINECRAFT_WILD_CONFIGS_MAIN = "https://raw.githubusercontent.com/Minecraft-Wild/configs/main/";
   private static final Duration DURATION = Duration.ofSeconds(6L);
   private static final Duration DURATION_2 = Duration.ofSeconds(10L);
   private static final Pattern PATTERN = Pattern.compile("[A-Za-z0-9._-]+");
   private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger();
   private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(2, runnable -> {
      Thread thread = new Thread(runnable, "Wild-CloudConfig-" + ATOMIC_INTEGER.incrementAndGet());
      thread.setDaemon(true);
      return thread;
   });
   private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
      .connectTimeout(DURATION)
      .followRedirects(Redirect.NORMAL)
      .executor(EXECUTOR_SERVICE)
      .build();
   private static volatile List<String> items = List.of();

   private CloudConfigService() {
   }

   public static CompletableFuture<CloudConfigService.CloudConfigServiceResult2> resolve(String string) {
      URI uri = resolve8(string);
      String text = resolve13(string, uri);
      if (uri == null) {
         return CompletableFuture.completedFuture(CloudConfigService.CloudConfigServiceResult2.failure(text, null, "Некорректное имя или URL."));
      } else {
         HttpRequest httpRequest = resolve10(uri);
         return HTTP_CLIENT.sendAsync(httpRequest, BodyHandlers.ofString(StandardCharsets.UTF_8))
            .thenComposeAsync(httpResponse -> resolve3(text, uri, (HttpResponse<String>)httpResponse), EXECUTOR_SERVICE)
            .exceptionally(throwable -> CloudConfigService.CloudConfigServiceResult2.failure(text, uri.toString(), resolve15(throwable)));
      }
   }

   public static CompletableFuture<CloudConfigService.CloudConfigServiceResult> resolve2() {
      URI uri2 = resolve9();
      HttpRequest httpRequest2 = resolve10(uri2);
      return HTTP_CLIENT.sendAsync(httpRequest2, BodyHandlers.ofString(StandardCharsets.UTF_8))
         .thenApplyAsync(httpResponse -> resolve6(uri2, (HttpResponse<String>)httpResponse), EXECUTOR_SERVICE)
         .exceptionally(throwable -> CloudConfigService.CloudConfigServiceResult.failure(uri2.toString(), resolve15(throwable)));
   }

   public static List<String> getItems() {
      return items;
   }

   public static void invoke() {
      EXECUTOR_SERVICE.shutdownNow();
   }

   private static CompletableFuture<CloudConfigService.CloudConfigServiceResult2> resolve3(String string, URI uRI, HttpResponse<String> httpResponse) {
      if (!check(httpResponse.statusCode())) {
         return CompletableFuture.completedFuture(CloudConfigService.CloudConfigServiceResult2.failure(string, uRI.toString(), "HTTP " + httpResponse.statusCode()));
      } else {
         JsonElement jsonElement2;
         try {
            jsonElement2 = JsonParser.parseString((String)httpResponse.body());
         } catch (Exception exception) {
            return CompletableFuture.completedFuture(CloudConfigService.CloudConfigServiceResult2.failure(string, uRI.toString(), "Некорректный JSON: " + resolve16(exception)));
         }

         if (jsonElement2 != null && jsonElement2.isJsonObject()) {
            JsonObject jsonObject2 = jsonElement2.getAsJsonObject();
            return resolve14((Callable<CloudConfigService.CloudConfigServiceResult3>)(() -> resolve4(string, uRI, jsonObject2)))
               .thenApplyAsync(CloudConfigService::resolve5, EXECUTOR_SERVICE);
         } else {
            return CompletableFuture.completedFuture(CloudConfigService.CloudConfigServiceResult2.failure(string, uRI.toString(), "Файл должен содержать JSON-объект."));
         }
      }
   }

   private static CloudConfigService.CloudConfigServiceResult3 resolve4(String string, URI uRI, JsonObject jsonObject) {
      if (WildClient.INSTANCE == null || WildClient.INSTANCE.configManager == null) {
         return CloudConfigService.CloudConfigServiceResult3.failure(string, uRI.toString(), "ConfigManager не инициализирован.");
      } else if (!WildClient.INSTANCE.configManager.check2(string, jsonObject)) {
         return CloudConfigService.CloudConfigServiceResult3.failure(string, uRI.toString(), "Не удалось применить конфиг.");
      } else {
         Config config = WildClient.INSTANCE.configManager.resolve4(string);
         return config == null
            ? CloudConfigService.CloudConfigServiceResult3.failure(string, uRI.toString(), "Не удалось подготовить локальную копию.")
            : CloudConfigService.CloudConfigServiceResult3.success(string, uRI.toString(), config.getFile(), config.resolve());
      }
   }

   private static CloudConfigService.CloudConfigServiceResult2 resolve5(CloudConfigService.CloudConfigServiceResult3 cloudConfigServiceResult3) {
      if (!cloudConfigServiceResult3.success()) {
         return CloudConfigService.CloudConfigServiceResult2.failure(cloudConfigServiceResult3.name(), cloudConfigServiceResult3.url(), cloudConfigServiceResult3.error());
      } else {
         try {
            if (!ConfigManager.FILE.exists() && !ConfigManager.FILE.mkdirs()) {
               return CloudConfigService.CloudConfigServiceResult2.failure(cloudConfigServiceResult3.name(), cloudConfigServiceResult3.url(), "Не удалось создать папку конфигов.");
            } else {
               String text2 = new GsonBuilder().setPrettyPrinting().create().toJson(cloudConfigServiceResult3.object());
               Files.writeString(cloudConfigServiceResult3.file().toPath(), text2, StandardCharsets.UTF_8);
               return CloudConfigService.CloudConfigServiceResult2.success(cloudConfigServiceResult3.name(), cloudConfigServiceResult3.url());
            }
         } catch (Exception exception2) {
            return CloudConfigService.CloudConfigServiceResult2.failure(cloudConfigServiceResult3.name(), cloudConfigServiceResult3.url(), "Конфиг применен, но не сохранен на диск.");
         }
      }
   }

   private static CloudConfigService.CloudConfigServiceResult resolve6(URI uRI, HttpResponse<String> httpResponse) {
      if (!check(httpResponse.statusCode())) {
         return CloudConfigService.CloudConfigServiceResult.failure(uRI.toString(), "HTTP " + httpResponse.statusCode());
      } else {
         JsonElement jsonElement3;
         try {
            jsonElement3 = JsonParser.parseString((String)httpResponse.body());
         } catch (Exception exception3) {
            return CloudConfigService.CloudConfigServiceResult.failure(uRI.toString(), "Некорректный index.json: " + resolve16(exception3));
         }

         if (jsonElement3 != null && jsonElement3.isJsonArray()) {
            JsonArray jsonArray = jsonElement3.getAsJsonArray();
            LinkedHashSet linkedHashSet = new LinkedHashSet();

            for (JsonElement jsonElement4 : jsonArray) {
               String text3 = resolve7(jsonElement4);
               if (text3 != null) {
                  linkedHashSet.add(text3);
               }
            }

            List items = List.copyOf(linkedHashSet);
            CloudConfigService.items = items;
            return CloudConfigService.CloudConfigServiceResult.success(items, uRI.toString());
         } else {
            return CloudConfigService.CloudConfigServiceResult.failure(uRI.toString(), "index.json должен быть JSON-массивом.");
         }
      }
   }

   private static String resolve7(JsonElement jsonElement) {
      if (jsonElement != null && !jsonElement.isJsonNull()) {
         String text4 = null;

         try {
            if (jsonElement.isJsonPrimitive()) {
               text4 = jsonElement.getAsString();
            } else if (jsonElement.isJsonObject()) {
               JsonObject jsonObject3 = jsonElement.getAsJsonObject();
               if (jsonObject3.has("name")) {
                  text4 = jsonObject3.get("name").getAsString();
               }
            }
         } catch (Exception exception4) {
            return null;
         }

         return resolve12(text4);
      } else {
         return null;
      }
   }

   private static URI resolve8(String string) {
      if (string != null && !string.isBlank()) {
         String text5 = string.trim();
         if (!text5.startsWith("https://") && !text5.startsWith("http://")) {
            String text6 = resolve12(text5);
            if (text6 == null) {
               return null;
            } else {
               String text7 = URLEncoder.encode(text6, StandardCharsets.UTF_8).replace("+", "%20");
               return URI.create(resolve11() + text7 + ".json");
            }
         } else {
            try {
               return URI.create(text5);
            } catch (Exception exception5) {
               return null;
            }
         }
      } else {
         return null;
      }
   }

   private static URI resolve9() {
      return URI.create(resolve11() + "index.json");
   }

   private static HttpRequest resolve10(URI uRI) {
      return HttpRequest.newBuilder(uRI).timeout(DURATION_2).header("Accept", "application/json").header("User-Agent", "NorthClient-CloudConfig").GET().build();
   }

   private static String resolve11() {
      String text8 = System.getProperty("wild.config.repo");
      if (text8 == null || text8.isBlank()) {
         text8 = System.getenv("WILD_CONFIG_REPO");
      }

      if (text8 == null || text8.isBlank()) {
         text8 = "https://raw.githubusercontent.com/Minecraft-Wild/configs/main/";
      }

      text8 = text8.trim();
      return text8.endsWith("/") ? text8 : text8 + "/";
   }

   private static String resolve12(String string) {
      if (string == null) {
         return null;
      } else {
         String text9 = string.trim();
         if (text9.endsWith(".json")) {
            text9 = text9.substring(0, text9.length() - 5);
         }

         return !text9.isBlank() && PATTERN.matcher(text9).matches() ? text9 : null;
      }
   }

   private static String resolve13(String string, URI uRI) {
      String text10 = null;
      if (string != null) {
         String text11 = string.trim();
         if (!text11.startsWith("https://") && !text11.startsWith("http://")) {
            text10 = resolve12(text11);
         } else {
            String text12 = uRI == null ? "" : uRI.getPath();
            int intValue = text12.lastIndexOf(47);
            text10 = intValue >= 0 ? text12.substring(intValue + 1) : "cloud";
         }
      }

      if (text10 == null || text10.isBlank()) {
         text10 = "cloud";
      }

      if (text10.endsWith(".json")) {
         text10 = text10.substring(0, text10.length() - 5);
      }

      text10 = text10.replaceAll("[^A-Za-z0-9._-]", "_");
      return text10.isBlank() ? "cloud" : text10;
   }

   private static boolean check(int i) {
      return i >= 200 && i < 300;
   }

   private static <T> CompletableFuture<T> resolve14(Callable<T> callable) {
      CompletableFuture completableFuture = new CompletableFuture();
      MinecraftClient client = MinecraftClient.getInstance();
      Runnable runnable2 = () -> {
         try {
            completableFuture.complete(callable.call());
         } catch (Throwable var3x) {
            completableFuture.completeExceptionally(var3x);
         }
      };
      if (client == null) {
         runnable2.run();
      } else {
         client.execute(runnable2);
      }

      return completableFuture;
   }

   private static String resolve15(Throwable throwable) {
      Throwable exception6 = throwable;

      while (exception6 instanceof CompletionException && exception6.getCause() != null) {
         exception6 = exception6.getCause();
      }

      return resolve16(exception6);
   }

   private static String resolve16(Throwable throwable) {
      if (throwable == null) {
         return "неизвестная ошибка";
      } else {
         String text13 = throwable.getMessage();
         return text13 != null && !text13.isBlank() ? text13 : throwable.getClass().getSimpleName();
      }
   }

   public record CloudConfigServiceResult(boolean success, List<String> names, String url, String error) {
      public CloudConfigServiceResult(boolean success, List<String> names, String url, String error) {
         names = names == null ? List.of() : List.copyOf(new ArrayList(names));
         this.success = success;
         this.names = names;
         this.url = url;
         this.error = error;
      }

      public static CloudConfigService.CloudConfigServiceResult success(List<String> list, String string) {
         return new CloudConfigService.CloudConfigServiceResult(true, list, string, null);
      }

      public static CloudConfigService.CloudConfigServiceResult failure(String string, String string2) {
         return new CloudConfigService.CloudConfigServiceResult(false, List.of(), string, string2);
      }
   }

   public record CloudConfigServiceResult2(boolean success, String name, String url, String error) {
      public static CloudConfigService.CloudConfigServiceResult2 success(String string, String string2) {
         return new CloudConfigService.CloudConfigServiceResult2(true, string, string2, null);
      }

      public static CloudConfigService.CloudConfigServiceResult2 failure(String string, String string2, String string3) {
         return new CloudConfigService.CloudConfigServiceResult2(false, string, string2, string3);
      }
   }

   record CloudConfigServiceResult3(boolean success, String name, String url, File file, JsonObject object, String error) {
      public static CloudConfigService.CloudConfigServiceResult3 success(String string, String string2, File file, JsonObject jsonObject) {
         return new CloudConfigService.CloudConfigServiceResult3(true, string, string2, file, jsonObject, null);
      }

      public static CloudConfigService.CloudConfigServiceResult3 failure(String string, String string2, String string3) {
         return new CloudConfigService.CloudConfigServiceResult3(false, string, string2, null, null, string3);
      }
   }
}
