package wild.loader;

import java.awt.Color;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * The launch sequence: pull the build from GitHub, put it in {@code mods}, install Minecraft with
 * Fabric and a matching Java, then start the game. Runs off the UI thread.
 */
final class Pipeline {
   static final String MINECRAFT_VERSION = "1.21.8";

   interface Sink {
      void log(String message, Color color);

      void progress(double fraction, String stage);

      void finished(boolean success, String message);
   }

   private final Config config;
   private final Pipeline.Sink sink;
   private final boolean play;

   Pipeline(Config config, Pipeline.Sink sink, boolean play) {
      this.config = config;
      this.sink = sink;
      this.play = play;
   }

   void start() {
      Thread thread = new Thread(this::run, "loader-pipeline");
      thread.setDaemon(true);
      thread.start();
   }

   private void run() {
      try {
         this.sink.progress(0.04, "Папка игры");
         Path minecraft = this.prepareMinecraft();
         if (minecraft == null) {
            this.sink.finished(false, "Нет доступа к папке игры");
            return;
         }

         if (this.config.getBoolean(Config.AUTO_INSTALL, true)) {
            Path build = this.obtainBuild();
            if (build == null) {
               this.sink.finished(false, "Не удалось получить сборку");
               return;
            }

            this.sink.progress(0.26, "Установка клиента");
            if (!this.install(minecraft, build)) {
               this.sink.finished(false, "Не удалось установить клиент");
               return;
            }
         } else {
            this.info("Обновление сборки выключено в настройках.");
         }

         Game game = new Game(minecraft, MINECRAFT_VERSION, this.report());
         game.install();
         if (!this.play) {
            this.sink.progress(1.0, "Готово");
            this.sink.finished(true, "Всё установлено");
            return;
         }

         this.sink.progress(0.96, "Запуск игры");
         if (!this.launch(game)) {
            this.sink.finished(false, "Игра не запустилась");
            return;
         }

         this.sink.progress(1.0, "Готово");
         this.sink.finished(true, "Игра запущена");
      } catch (InterruptedException interruption) {
         Thread.currentThread().interrupt();
         this.error("Запуск прерван.");
         this.sink.finished(false, "Прервано");
      } catch (Throwable exception) {
         this.error("Сбой: " + describe(exception));
         this.sink.finished(false, "Сбой запуска");
      }
   }

   /** Passes the install steps of {@link Game} through to the interface. */
   private Game.Report report() {
      return new Game.Report() {
         @Override
         public void info(String message) {
            Pipeline.this.info(message);
         }

         @Override
         public void ok(String message) {
            Pipeline.this.ok(message);
         }

         @Override
         public void warn(String message) {
            Pipeline.this.warn(message);
         }

         @Override
         public void step(double fraction, String stage) {
            Pipeline.this.sink.progress(fraction, stage);
         }
      };
   }

   /** The game folder is figured out automatically; the setting is only a manual override. */
   private Path prepareMinecraft() {
      String configured = this.config.get(Config.MC_DIR, "");
      Path directory = configured.isEmpty() ? Config.defaultMinecraftDir() : Paths.get(configured);

      try {
         Files.createDirectories(directory.resolve("mods"));
         this.ok("Папка игры: " + directory);
         return directory;
      } catch (IOException exception2) {
         this.error("Не удалось подготовить " + directory + ": " + describe(exception2));
         return null;
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
         this.sink.progress(0.08, "Проверка обновлений");
         this.info("Репозиторий: " + repository);

         try {
            Downloader.Asset asset = Downloader.latestRelease(repository);
            if (asset == null) {
               this.warn("В релизах " + repository + " нет джарника.");
            } else {
               // Tag is often always "latest", so fold the size into the cache path —
               // otherwise a rebuilt release with the same tag can keep serving a stale jar.
               Path cached = Config.cacheDir().resolve(asset.safeTag() + "-" + asset.size()).resolve(asset.name());
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
      this.sink.progress(0.12, "Загрузка клиента");

      try {
         // Чанки приходят по 64 КБ, поэтому прогресс шлём реже, чтобы не заваливать поток интерфейса.
         long[] reported = {0L};
         Downloader.download(url, target, (done, total) -> {
            long size = total > 0L ? total : expected;
            if (size > 0L && done - reported[0] >= 262144L) {
               reported[0] = done;
               this.sink.progress(0.12 + 0.12 * Math.min(1.0, (double)done / size), "Загрузка клиента");
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
            String lower = name.toLowerCase();
            if ((lower.startsWith("north") || lower.startsWith("wild") || lower.startsWith("low-free") || lower.startsWith("lowfree")) && !name.equals(keep)) {
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

   private boolean launch(Game game) throws IOException {
      String custom = this.config.get(Config.LAUNCH_CMD, "");
      if (!custom.isEmpty()) {
         this.info("Своя команда запуска: " + custom);
         return this.spawn(shell(custom), game.root());
      }

      String nickname = this.config.nickname();
      int memory = Math.max(2, this.config.getInt(Config.RAM, 4));
      this.info("Игрок " + nickname + ", памяти " + memory + " ГБ.");
      return this.spawn(game.command(nickname, memory), game.root());
   }

   private boolean spawn(List<String> command, Path workingDirectory) {
      Path log = Config.gameLog();

      try {
         Path parent = log.getParent();
         if (parent != null) {
            Files.createDirectories(parent);
         }

         ProcessBuilder builder = new ProcessBuilder(command);
         if (Files.isDirectory(workingDirectory)) {
            builder.directory(workingDirectory.toFile());
         }

         builder.redirectErrorStream(true);
         builder.redirectOutput(ProcessBuilder.Redirect.to(log.toFile()));
         this.watch(builder.start(), log);
         return true;
      } catch (IOException exception7) {
         this.error("Не удалось запустить: " + describe(exception7));
         return false;
      }
   }

   /**
    * A crash during startup would otherwise look like a successful launch: the loader says "готово"
    * while nothing opens. Watching the process for a while turns that into a readable message.
    */
   private void watch(Process process, Path log) {
      Thread thread = new Thread(() -> {
         try {
            if (process.waitFor(25L, TimeUnit.SECONDS) && process.exitValue() != 0) {
               this.error("Игра закрылась с кодом " + process.exitValue() + ". Лог: " + log);

               for (String line : tail(log, 6)) {
                  this.warn("  " + line);
               }
            }
         } catch (InterruptedException interruption) {
            Thread.currentThread().interrupt();
         }
      }, "loader-watch");
      thread.setDaemon(true);
      thread.start();
   }

   private static List<String> tail(Path file, int count) {
      try {
         // Игра пишет лог в кодировке системы, поэтому читаем байтами: строгий декодер тут упал бы.
         List<String> lines = new ArrayList<>(new String(Files.readAllBytes(file), StandardCharsets.UTF_8).lines().toList());
         lines.removeIf(String::isBlank);
         return lines.subList(Math.max(0, lines.size() - count), lines.size());
      } catch (Exception exception8) {
         return List.of();
      }
   }

   private static List<String> shell(String command) {
      return System.getProperty("os.name", "").toLowerCase().contains("win")
         ? List.of("cmd.exe", "/c", command)
         : List.of("/bin/sh", "-c", command);
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
