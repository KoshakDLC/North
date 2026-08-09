package wild.loader;

import java.awt.Color;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/** The launch sequence: verify the key, install the mod, start the game. Runs off the UI thread. */
final class Pipeline {
   private static final String MINECRAFT_VERSION = "1.21.8";

   interface Sink {
      void log(String message, Color color);

      void progress(double fraction, String stage);

      void finished(boolean success, String message);
   }

   private final Config config;
   private final Pipeline.Sink sink;

   Pipeline(Config config, Pipeline.Sink sink) {
      this.config = config;
      this.sink = sink;
   }

   void start(String key) {
      Thread thread = new Thread(() -> this.run(key), "loader-pipeline");
      thread.setDaemon(true);
      thread.start();
   }

   private void run(String key) {
      try {
         this.sink.progress(0.06, "Проверка ключа");
         if (!this.verifyKey(key)) {
            this.sink.finished(false, "Ключ не принят");
            return;
         }

         this.sink.progress(0.28, "Поиск Minecraft");
         Path minecraft = this.locateMinecraft();
         if (minecraft == null) {
            this.sink.finished(false, "Папка Minecraft не найдена");
            return;
         }

         this.sink.progress(0.40, "Проверка Fabric");
         this.checkFabric(minecraft);
         if (this.config.getBoolean(Config.AUTO_INSTALL, true)) {
            Path build = this.obtainBuild();
            if (build == null) {
               this.sink.finished(false, "Не удалось получить сборку");
               return;
            }

            this.sink.progress(0.84, "Установка клиента");
            if (!this.install(minecraft, build)) {
               this.sink.finished(false, "Не удалось установить клиент");
               return;
            }
         } else {
            this.info("Установка пропущена (выключена в настройках).");
         }

         this.sink.progress(0.90, "Запуск игры");
         this.launch(minecraft);
         this.sink.progress(1.0, "Готово");
         this.sink.finished(true, "Клиент готов к работе");
      } catch (Throwable exception) {
         this.error("Сбой: " + describe(exception));
         this.sink.finished(false, "Сбой запуска");
      }
   }

   private boolean verifyKey(String key) {
      if (key == null || key.isBlank()) {
         this.error("Введите лицензионный ключ.");
         return false;
      }

      String api = this.config.get(Config.API, "");
      if (api.isEmpty()) {
         this.warn("Сервер лицензий не настроен — офлайн-режим.");
         this.ok("Ключ принят локально (" + mask(key) + ").");
         return true;
      }

      this.info("Запрос к серверу лицензий…");

      try {
         String payload = "{\"key\":\"" + escape(key) + "\",\"hwid\":\"" + Config.hardwareId() + "\"}";
         HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(8L)).build();
         HttpRequest request = HttpRequest.newBuilder(URI.create(api))
            .timeout(Duration.ofSeconds(12L))
            .header("Content-Type", "application/json")
            .header("User-Agent", "WildLoader")
            .POST(HttpRequest.BodyPublishers.ofString(payload, StandardCharsets.UTF_8))
            .build();
         HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
         String body = response.body() == null ? "" : response.body();
         if (response.statusCode() >= 200 && response.statusCode() < 300 && body.contains("\"ok\":true")) {
            this.ok("Ключ подтверждён" + owner(body) + ".");
            return true;
         } else {
            this.error("Сервер отклонил ключ (HTTP " + response.statusCode() + ").");
            return false;
         }
      } catch (Exception exception2) {
         this.error("Сервер лицензий недоступен: " + describe(exception2));
         return false;
      }
   }

   private Path locateMinecraft() {
      String configured = this.config.get(Config.MC_DIR, "");
      Path directory = configured.isEmpty() ? Config.defaultMinecraftDir() : Paths.get(configured);
      if (Files.isDirectory(directory)) {
         this.ok("Minecraft: " + directory);
         return directory;
      } else {
         this.error("Нет папки " + directory);
         return null;
      }
    }

   private void checkFabric(Path minecraft) {
      Path versions = minecraft.resolve("versions");
      List<String> found = new ArrayList<>();
      if (Files.isDirectory(versions)) {
         try (DirectoryStream<Path> stream = Files.newDirectoryStream(versions)) {
            for (Path candidate : stream) {
               String name = candidate.getFileName().toString().toLowerCase();
               if (name.contains("fabric") && name.contains(MINECRAFT_VERSION)) {
                  found.add(candidate.getFileName().toString());
               }
            }
         } catch (IOException exception3) {
         }
      }

      if (found.isEmpty()) {
         this.warn("Fabric для " + MINECRAFT_VERSION + " не найден — поставь его перед запуском.");
      } else {
         this.ok("Fabric: " + found.get(0));
      }
   }

   /**
    * Finds the build to install. A path set by hand wins, then a direct link, then the latest
    * GitHub release; a local gradle output is only used as a last resort on a dev machine.
    */
   private Path obtainBuild() {
      String local = this.config.get(Config.JAR, "");
      if (!local.isEmpty()) {
         Path path = Paths.get(local);
         if (Files.isRegularFile(path)) {
            this.ok("Локальный джарник: " + path.getFileName());
            return path;
         }

         this.warn("Указанный джарник не найден: " + path);
      }

      String direct = this.config.get(Config.CLIENT_URL, "");
      if (!direct.isEmpty()) {
         return this.fetch(direct, Config.cacheDir().resolve(fileNameOf(direct)), -1L);
      }

      String repository = this.config.get(Config.REPO, Config.DEFAULT_REPO);
      if (!repository.isEmpty()) {
         this.sink.progress(0.48, "Проверка обновлений");
         this.info("Репозиторий: " + repository);

         try {
            Downloader.Asset asset = Downloader.latestRelease(repository);
            if (asset == null) {
               this.warn("В релизах " + repository + " нет джарника.");
            } else {
               Path cached = Config.cacheDir().resolve(asset.safeTag()).resolve(asset.name());
               if (Files.isRegularFile(cached) && sizeOf(cached) == asset.size()) {
                  this.ok("Сборка " + asset.tag() + " уже скачана.");
                  return cached;
               }

               this.info("Новая сборка " + asset.tag() + " — " + asset.name() + " (" + Downloader.humanSize(asset.size()) + ")");
               return this.fetch(asset.url(), cached, asset.size());
            }
         } catch (InterruptedException exception9) {
            Thread.currentThread().interrupt();
            this.error("Загрузка прервана.");
            return null;
         } catch (Exception exception10) {
            this.error("GitHub недоступен: " + describe(exception10));
         }
      }

      Path built = Config.findBuiltJar();
      if (built != null) {
         this.warn("Беру локальную сборку " + built.getFileName() + ".");
         return built;
      }

      this.error("Сборку взять неоткуда — проверь репозиторий в настройках.");
      return null;
   }

   private Path fetch(String url, Path target, long expected) {
      this.sink.progress(0.52, "Загрузка клиента");

      try {
         // Чанки приходят по 64 КБ, поэтому прогресс шлём реже, чтобы не заваливать поток интерфейса.
         long[] reported = {0L};
         Downloader.download(url, target, (done, total) -> {
            long size = total > 0L ? total : expected;
            if (size > 0L && done - reported[0] >= 262144L) {
               reported[0] = done;
               this.sink.progress(0.52 + 0.28 * Math.min(1.0, (double)done / size), "Загрузка клиента");
            }
         });
         this.ok("Скачано " + target.getFileName() + " (" + Downloader.humanSize(sizeOf(target)) + ").");
         return target;
      } catch (InterruptedException exception11) {
         Thread.currentThread().interrupt();
         this.error("Загрузка прервана.");
         return null;
      } catch (Exception exception12) {
         this.error("Не удалось скачать: " + describe(exception12));
         return null;
      }
   }

   private static String fileNameOf(String url) {
      String path = url;
      int query = path.indexOf(63);
      if (query > 0) {
         path = path.substring(0, query);
      }

      int slash = path.lastIndexOf(47);
      String name = slash < 0 ? path : path.substring(slash + 1);
      return name.isBlank() ? "client.jar" : name;
   }

   private static long sizeOf(Path path) {
      try {
         return Files.size(path);
      } catch (IOException exception13) {
         return -1L;
      }
   }

   private boolean install(Path minecraft, Path source) {
      Path mods = minecraft.resolve("mods");

      try {
         Files.createDirectories(mods);
         this.removeOldBuilds(mods, source.getFileName().toString());
         Path target = mods.resolve(source.getFileName().toString());
         Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
         this.ok("Установлен " + target.getFileName() + " (" + Downloader.humanSize(sizeOf(target)) + ").");
         return true;
      } catch (IOException exception4) {
         this.error("Копирование не удалось: " + describe(exception4));
         return false;
      }
   }

   /** Drops previous builds of the client so two versions never load at once. */
   private void removeOldBuilds(Path mods, String keep) {
      try (DirectoryStream<Path> stream = Files.newDirectoryStream(mods, "*.jar")) {
         for (Path candidate : stream) {
            String name = candidate.getFileName().toString();
            if (name.toLowerCase().startsWith("wild") && !name.equals(keep)) {
               try {
                  Files.delete(candidate);
                  this.info("Удалена старая сборка " + name);
               } catch (IOException exception5) {
                  this.warn("Не удалось удалить " + name);
               }
            }
         }
      } catch (IOException exception6) {
      }
   }

   private void launch(Path minecraft) {
      String command = this.config.get(Config.LAUNCH_CMD, "");
      if (!command.isEmpty()) {
         this.info("Запуск: " + command);
         if (this.spawn(shell(command), minecraft)) {
            this.ok("Команда запуска выполнена.");
         }

         return;
      }

      Path launcher = this.findLauncher();
      if (launcher == null) {
         this.warn("Лаунчер не найден — открой его сам и выбери профиль Fabric " + MINECRAFT_VERSION + ".");
      } else {
         this.info("Запуск " + launcher.getFileName());
         if (this.spawn(List.of(launcher.toString()), minecraft)) {
            this.ok("Лаунчер запущен, выбери профиль Fabric " + MINECRAFT_VERSION + ".");
         }
      }
   }

   private boolean spawn(List<String> command, Path workingDirectory) {
      try {
         ProcessBuilder builder = new ProcessBuilder(command);
         if (Files.isDirectory(workingDirectory)) {
            builder.directory(workingDirectory.toFile());
         }

         builder.redirectErrorStream(true);
         builder.redirectOutput(ProcessBuilder.Redirect.DISCARD);
         builder.start();
         return true;
      } catch (IOException exception7) {
         this.error("Не удалось запустить: " + describe(exception7));
         return false;
      }
   }

   private static List<String> shell(String command) {
      return System.getProperty("os.name", "").toLowerCase().contains("win")
         ? List.of("cmd.exe", "/c", command)
         : List.of("/bin/sh", "-c", command);
   }

   private Path findLauncher() {
      List<String> candidates = new ArrayList<>();
      String programFiles = System.getenv("ProgramFiles(x86)");
      String programFiles64 = System.getenv("ProgramFiles");
      if (programFiles != null) {
         candidates.add(programFiles + "\\Minecraft Launcher\\MinecraftLauncher.exe");
      }

      if (programFiles64 != null) {
         candidates.add(programFiles64 + "\\Minecraft Launcher\\MinecraftLauncher.exe");
         candidates.add(programFiles64 + "\\Minecraft\\MinecraftLauncher.exe");
      }

      candidates.add("/usr/bin/minecraft-launcher");

      for (String candidate : candidates) {
         Path path = Paths.get(candidate);
         if (Files.isRegularFile(path)) {
            return path;
         }
      }

      return null;
   }

   private static String owner(String body) {
      int index = body.indexOf("\"user\"");
      if (index < 0) {
         return "";
      } else {
         int start = body.indexOf('"', body.indexOf(':', index) + 1);
         int end = start < 0 ? -1 : body.indexOf('"', start + 1);
         return start < 0 || end < 0 ? "" : " — " + body.substring(start + 1, end);
      }
   }

   private static String mask(String key) {
      String trimmed = key.trim();
      return trimmed.length() <= 4 ? "****" : trimmed.substring(0, 2) + "••••" + trimmed.substring(trimmed.length() - 2);
   }

   private static String escape(String value) {
      return value.replace("\\", "\\\\").replace("\"", "\\\"");
   }

   private static String describe(Throwable throwable) {
      String message = throwable.getMessage();
      return message == null || message.isBlank() ? throwable.getClass().getSimpleName() : message;
   }

   private void info(String message) {
      this.sink.log(message, Theme.MUTED);
   }

   private void ok(String message) {
      this.sink.log(message, Theme.OK);
   }

   private void warn(String message) {
      this.sink.log(message, Theme.WARN);
   }

   private void error(String message) {
      this.sink.log(message, Theme.BAD);
   }
}
