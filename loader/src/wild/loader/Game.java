package wild.loader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicLong;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Installs everything the game needs and builds the command that starts it: vanilla files from
 * Mojang, the Fabric loader from its meta service, and the matching Java runtime. The official
 * launcher is never involved, so pressing one button is enough.
 */
final class Game {
   private static final String MANIFEST = "https://piston-meta.mojang.com/mc/game/version_manifest_v2.json";
   private static final String ASSET_HOST = "https://resources.download.minecraft.net/";
   private static final String FABRIC_META = "https://meta.fabricmc.net/v2/versions/loader/";
   private static final String FABRIC_API =
      "https://api.modrinth.com/v2/project/fabric-api/version?game_versions=%5B%22{version}%22%5D&loaders=%5B%22fabric%22%5D";
   private static final String LAUNCHER_NAME = "low-free";

   /** Progress and log callbacks, so the install can talk to the window without knowing about Swing. */
   interface Report {
      void info(String message);

      void ok(String message);

      void warn(String message);

      void step(double fraction, String stage);
   }

   private record Library(String path, String url, String sha1, long size, boolean natives) {
   }

   private final Path root;
   private final String version;
   private final Game.Report report;

   private Map<String, Object> vanilla;
   private Map<String, Object> fabric;
   private final List<Path> classpath = new ArrayList<>();
   private final List<Path> nativeJars = new ArrayList<>();
   private String assetIndex = "";
   private Path runtime;

   Game(Path root, String version, Game.Report report) {
      this.root = root;
      this.version = version;
      this.report = report;
   }

   Path root() {
      return this.root;
   }

   /** Downloads whatever is missing. Repeat launches only verify what is already on disk. */
   void install() throws IOException, InterruptedException {
      this.report.step(0.30, "Проверка Minecraft " + this.version);
      this.vanilla = this.vanillaVersion();
      Path client = this.clientJar();
      this.report.step(0.36, "Библиотеки");
      List<Path> vanillaLibraries = this.libraries(this.vanilla, true);
      this.report.step(0.44, "Ресурсы игры");
      this.assets();
      this.report.step(0.72, "Fabric");
      this.fabric = this.fabricProfile();
      List<Path> fabricLibraries = this.libraries(this.fabric, false);
      this.classpath.clear();
      this.merge(this.classpath, fabricLibraries);
      this.merge(this.classpath, vanillaLibraries);
      this.classpath.add(client);
      this.report.step(0.78, "Fabric API");
      this.fabricApi();
      this.report.step(0.80, "Java");
      this.runtime = Runtimes.locate(Json.text(Json.at(this.vanilla, "javaVersion", "component"), "java-runtime-delta"), 21, this.report);
   }

   /** Adds libraries the classpath does not have yet; the first copy of an artifact wins. */
   private void merge(List<Path> target, List<Path> extra) {
      List<String> present = new ArrayList<>();

      for (Path path : target) {
         present.add(artifactKey(path));
      }

      for (Path path : extra) {
         String key = artifactKey(path);
         if (!present.contains(key)) {
            present.add(key);
            target.add(path);
         }
      }
   }

   /**
    * {@code libraries/org/ow2/asm/asm/9.8/asm-9.8.jar} identifies as {@code org/ow2/asm/asm.jar}:
    * the version drops out, the classifier stays. Without the classifier a natives jar would look
    * like a second copy of the library it belongs to and never reach the classpath.
    */
   private static String artifactKey(Path jar) {
      Path version = jar.getParent();
      Path artifact = version == null ? null : version.getParent();
      if (artifact == null) {
         return jar.getFileName().toString();
      }

      String name = jar.getFileName().toString();
      String prefix = artifact.getFileName() + "-" + version.getFileName();
      return artifact + (name.startsWith(prefix) ? name.substring(prefix.length()) : name);
   }

   private Map<String, Object> vanillaVersion() throws IOException, InterruptedException {
      Path file = this.root.resolve("versions").resolve(this.version).resolve(this.version + ".json");
      if (Files.isRegularFile(file)) {
         try {
            return Json.object(Json.parse(Files.readString(file, StandardCharsets.UTF_8)));
         } catch (Exception exception) {
            this.report.warn("Описание версии повреждено, качаю заново.");
         }
      }

      String url = null;

      for (Object entry : Json.array(Json.get(Json.parse(Downloader.text(MANIFEST)), "versions"))) {
         if (this.version.equals(Json.text(Json.get(entry, "id"), ""))) {
            url = Json.text(Json.get(entry, "url"), null);
            break;
         }
      }

      if (url == null) {
         throw new IOException("Mojang не знает версию " + this.version);
      }

      String body = Downloader.text(url);
      Files.createDirectories(file.getParent());
      Files.writeString(file, body, StandardCharsets.UTF_8);
      this.report.ok("Описание Minecraft " + this.version + " получено.");
      return Json.object(Json.parse(body));
   }

   private Path clientJar() throws IOException, InterruptedException {
      Path jar = this.root.resolve("versions").resolve(this.version).resolve(this.version + ".jar");
      Object client = Json.at(this.vanilla, "downloads", "client");
      String url = Json.text(Json.get(client, "url"), null);
      if (url == null) {
         throw new IOException("В описании версии нет ссылки на клиент");
      }

      long size = Json.number(Json.get(client, "size"), -1L);
      if (Downloader.intact(jar, Json.text(Json.get(client, "sha1"), null), size)) {
         return jar;
      }

      this.report.info("Скачиваю Minecraft " + this.version + " (" + Downloader.humanSize(size) + ")…");
      long[] reported = {0L};
      Downloader.download(url, jar, (done, total) -> {
         long full = total > 0L ? total : size;
         if (full > 0L && (done - reported[0] >= 524288L || done >= full)) {
            reported[0] = done;
            this.report.step(0.30 + 0.06 * Math.min(1.0, (double)done / full), "Minecraft " + this.version);
         }
      });
      this.report.ok("Minecraft " + this.version + " скачан.");
      return jar;
   }

   /**
    * Downloads the libraries of a version description. Vanilla entries carry their own hashes;
    * Fabric only gives maven coordinates, so those are fetched by name.
    */
   private List<Path> libraries(Map<String, Object> description, boolean vanillaStyle) throws IOException, InterruptedException {
      List<Game.Library> wanted = new ArrayList<>();

      for (Object entry : Json.array(description.get("libraries"))) {
         if (allowed(Json.array(Json.get(entry, "rules")))) {
            if (vanillaStyle) {
               collectVanilla(entry, wanted);
            } else {
               collectMaven(entry, wanted);
            }
         }
      }

      List<Path> result = new ArrayList<>();
      List<Game.Library> missing = new ArrayList<>();
      Map<Game.Library, Path> targets = new LinkedHashMap<>();

      for (Game.Library library : wanted) {
         Path target = this.root.resolve("libraries").resolve(library.path().replace('/', File.separatorChar));
         result.add(target);
         targets.put(library, target);
         if (library.natives()) {
            this.nativeJars.add(target);
         }

         if (!Downloader.intact(target, library.sha1(), library.size())) {
            missing.add(library);
         }
      }

      if (!missing.isEmpty()) {
         this.report.info("Догружаю библиотеки: " + missing.size() + " шт.");
         List<Callable<Void>> tasks = new ArrayList<>();
         AtomicLong done = new AtomicLong();

         for (Game.Library library : missing) {
            tasks.add(() -> {
               Downloader.download(library.url(), targets.get(library), null);
               this.report.step(0.36 + 0.08 * Math.min(1.0, (double)done.incrementAndGet() / missing.size()), "Библиотеки");
               return null;
            });
         }

         Downloader.parallel(tasks);
      }

      return result;
   }

   private static void collectVanilla(Object entry, List<Game.Library> target) {
      Object artifact = Json.at(entry, "downloads", "artifact");
      if (artifact != null) {
         String name = Json.text(Json.get(entry, "name"), "");
         target.add(
            new Game.Library(
               Json.text(Json.get(artifact, "path"), ""),
               Json.text(Json.get(artifact, "url"), ""),
               Json.text(Json.get(artifact, "sha1"), null),
               Json.number(Json.get(artifact, "size"), -1L),
               name.toLowerCase(Locale.ROOT).contains("natives")
            )
         );
      }

      // Versions before 1.19 kept the platform binaries in a separate classifier.
      Object classifier = Json.at(entry, "downloads", "classifiers");
      String key = Json.text(Json.at(entry, "natives", osName()), null);
      if (classifier != null && key != null) {
         Object native2 = Json.get(classifier, key.replace("${arch}", is64Bit() ? "64" : "32"));
         if (native2 != null) {
            target.add(
               new Game.Library(
                  Json.text(Json.get(native2, "path"), ""),
                  Json.text(Json.get(native2, "url"), ""),
                  Json.text(Json.get(native2, "sha1"), null),
                  Json.number(Json.get(native2, "size"), -1L),
                  true
               )
            );
         }
      }
   }

   private static void collectMaven(Object entry, List<Game.Library> target) {
      String name = Json.text(Json.get(entry, "name"), "");
      if (!name.isEmpty()) {
         String repository = Json.text(Json.get(entry, "url"), "https://maven.fabricmc.net/");
         if (!repository.endsWith("/")) {
            repository = repository + "/";
         }

         String path = mavenPath(name);
         target.add(
            new Game.Library(
               path,
               repository + path,
               Json.text(Json.get(entry, "sha1"), null),
               Json.number(Json.get(entry, "size"), -1L),
               false
            )
         );
      }
   }

   /** {@code group:artifact:version[:classifier]} to a repository path. */
   private static String mavenPath(String coordinates) {
      String[] parts = coordinates.split(":");
      String group = parts[0].replace('.', '/');
      String artifact = parts[1];
      String version = parts.length > 2 ? parts[2] : "";
      String classifier = parts.length > 3 ? "-" + parts[3] : "";
      return group + "/" + artifact + "/" + version + "/" + artifact + "-" + version + classifier + ".jar";
   }

   private void assets() throws IOException, InterruptedException {
      Object index = Json.get(this.vanilla, "assetIndex");
      this.assetIndex = Json.text(Json.get(index, "id"), "");
      String url = Json.text(Json.get(index, "url"), null);
      if (url == null) {
         this.report.warn("У версии нет списка ресурсов — звуки и языки могут не загрузиться.");
      } else {
         Path indexFile = this.root.resolve("assets").resolve("indexes").resolve(this.assetIndex + ".json");
         String body;
         if (Downloader.intact(indexFile, Json.text(Json.get(index, "sha1"), null), Json.number(Json.get(index, "size"), -1L))) {
            body = Files.readString(indexFile, StandardCharsets.UTF_8);
         } else {
            body = Downloader.text(url);
            Files.createDirectories(indexFile.getParent());
            Files.writeString(indexFile, body, StandardCharsets.UTF_8);
         }

         Map<String, Object> objects = Json.object(Json.get(Json.parse(body), "objects"));
         Path store = this.root.resolve("assets").resolve("objects");
         List<String> missing = new ArrayList<>();

         for (Object value : objects.values()) {
            String hash = Json.text(Json.get(value, "hash"), "");
            if (hash.length() >= 2 && !Downloader.intact(store.resolve(hash.substring(0, 2)).resolve(hash), null, Json.number(Json.get(value, "size"), -1L))) {
               missing.add(hash);
            }
         }

         if (missing.isEmpty()) {
            this.report.ok("Ресурсы игры на месте.");
         } else {
            this.report.info("Качаю ресурсы игры: " + missing.size() + " файлов. В первый раз это долго.");
            List<Callable<Void>> tasks = new ArrayList<>();
            AtomicLong ready = new AtomicLong();

            for (String hash : missing) {
               tasks.add(() -> {
                  String prefix = hash.substring(0, 2);
                  Downloader.download(ASSET_HOST + prefix + "/" + hash, store.resolve(prefix).resolve(hash), null);
                  long count = ready.incrementAndGet();
                  if (count % 25L == 0L || count == missing.size()) {
                     this.report.step(0.44 + 0.28 * Math.min(1.0, (double)count / missing.size()), "Ресурсы игры");
                  }

                  return null;
               });
            }

            Downloader.parallel(tasks);
            this.report.ok("Ресурсы игры готовы.");
         }
      }
   }

   /** Fabric publishes a ready profile for every loader and game version pair. */
   private Map<String, Object> fabricProfile() throws IOException, InterruptedException {
      String loader = null;

      for (Object entry : Json.array(Json.parse(Downloader.text(FABRIC_META + this.version)))) {
         String candidate = Json.text(Json.at(entry, "loader", "version"), null);
         if (candidate != null) {
            boolean stable = Json.flag(Json.at(entry, "loader", "stable"), false);
            if (loader == null || stable) {
               loader = candidate;
            }

            if (stable) {
               break;
            }
         }
      }

      if (loader == null) {
         throw new IOException("Fabric не поддерживает " + this.version);
      }

      String profileId = "fabric-loader-" + loader + "-" + this.version;
      Path file = this.root.resolve("versions").resolve(profileId).resolve(profileId + ".json");
      String body;
      if (Files.isRegularFile(file)) {
         body = Files.readString(file, StandardCharsets.UTF_8);
      } else {
         body = Downloader.text(FABRIC_META + this.version + "/" + loader + "/profile/json");
         Files.createDirectories(file.getParent());
         Files.writeString(file, body, StandardCharsets.UTF_8);
      }

      this.report.ok("Fabric " + loader + " готов.");
      return Json.object(Json.parse(body));
   }

   /**
    * Puts Fabric API into {@code mods}. The client depends on the {@code fabric} mod id, and that
    * is the API jar — the loader alone is not enough.
    */
   private void fabricApi() throws IOException, InterruptedException {
      Path mods = this.root.resolve("mods");
      Files.createDirectories(mods);
      Object release = latestFabricApi();
      if (release == null) {
         if (hasFabricApi(mods)) {
            this.report.warn("Не удалось проверить Fabric API — оставляю то, что уже есть.");
            return;
         }

         throw new IOException("На Modrinth нет Fabric API для " + this.version);
      }

      String name = null;
      String url = null;
      String sha1 = null;
      long size = -1L;

      for (Object file : Json.array(Json.get(release, "files"))) {
         String candidate = Json.text(Json.get(file, "filename"), "");
         if (candidate.toLowerCase(Locale.ROOT).endsWith(".jar")) {
            name = candidate;
            url = Json.text(Json.get(file, "url"), null);
            sha1 = Json.text(Json.at(file, "hashes", "sha1"), null);
            size = Json.number(Json.get(file, "size"), -1L);
            if (Json.flag(Json.get(file, "primary"), false)) {
               break;
            }
         }
      }

      if (name == null || url == null) {
         throw new IOException("У релиза Fabric API нет jar-файла");
      }

      Path target = mods.resolve(name);
      if (Downloader.intact(target, sha1, size)) {
         this.report.ok("Fabric API " + Json.text(Json.get(release, "version_number"), name) + " на месте.");
         return;
      }

      this.removeFabricApi(mods, name);
      this.report.info("Скачиваю Fabric API " + Json.text(Json.get(release, "version_number"), name) + "…");
      Downloader.download(url, target, null);
      this.report.ok("Fabric API установлен.");
   }

   private Object latestFabricApi() throws IOException, InterruptedException {
      List<Object> versions = Json.array(Json.parse(Downloader.text(FABRIC_API.replace("{version}", this.version))));
      return versions.isEmpty() ? null : versions.get(0);
   }

   private static boolean hasFabricApi(Path mods) {
      try (DirectoryStream<Path> stream = Files.newDirectoryStream(mods, "*.jar")) {
         for (Path candidate : stream) {
            if (isFabricApi(candidate.getFileName().toString())) {
               return true;
            }
         }
      } catch (IOException exception) {
      }

      return false;
   }

   private void removeFabricApi(Path mods, String keep) {
      try (DirectoryStream<Path> stream = Files.newDirectoryStream(mods, "*.jar")) {
         for (Path candidate : stream) {
            String name = candidate.getFileName().toString();
            if (isFabricApi(name) && !name.equals(keep)) {
               try {
                  Files.delete(candidate);
                  this.report.info("Удалён старый " + name);
               } catch (IOException exception) {
                  this.report.warn("Не удалось удалить " + name);
               }
            }
         }
      } catch (IOException exception) {
      }
   }

   private static boolean isFabricApi(String name) {
      String lower = name.toLowerCase(Locale.ROOT);
      return lower.startsWith("fabric-api") || lower.startsWith("fabric_api");
   }

   /**
    * Unpacks the platform binaries next to the version. Since 1.19 LWJGL extracts them from the
    * jars on the classpath itself, so this only matters for the libraries that still expect a
    * populated {@code java.library.path}; the layout inside the jars differs, so names are flattened.
    */
   private Path natives() throws IOException {
      Path directory = this.root.resolve("natives").resolve(this.version);
      Files.createDirectories(directory);

      for (Path jar : this.nativeJars) {
         if (Files.isRegularFile(jar)) {
            try (ZipFile zip = new ZipFile(jar.toFile())) {
               Enumeration<? extends ZipEntry> entries = zip.entries();

               while (entries.hasMoreElements()) {
                  ZipEntry entry = entries.nextElement();
                  String name = entry.getName();
                  if (!entry.isDirectory() && !name.startsWith("META-INF/") && isBinary(name)) {
                     Path target = directory.resolve(name.substring(name.lastIndexOf(47) + 1));
                     if (!Files.isRegularFile(target) || Files.size(target) != entry.getSize()) {
                        try (InputStream input = zip.getInputStream(entry); OutputStream output = Files.newOutputStream(target)) {
                           input.transferTo(output);
                        }
                     }
                  }
               }
            }
         }
      }

      return directory;
   }

   private static boolean isBinary(String name) {
      String lower = name.toLowerCase(Locale.ROOT);
      return lower.endsWith(".dll") || lower.endsWith(".so") || lower.endsWith(".dylib") || lower.endsWith(".jnilib");
   }

   /** The full java command line, assembled from the vanilla and Fabric argument templates. */
   List<String> command(String nickname, int memoryGb) throws IOException {
      Path natives = this.natives();
      Map<String, String> values = new LinkedHashMap<>();
      values.put("natives_directory", natives.toString());
      values.put("launcher_name", LAUNCHER_NAME);
      values.put("launcher_version", "1");
      values.put("classpath", join(this.classpath));
      values.put("classpath_separator", File.pathSeparator);
      values.put("library_directory", this.root.resolve("libraries").toString());
      values.put("auth_player_name", nickname);
      values.put("version_name", Json.text(this.fabric.get("id"), this.version));
      values.put("game_directory", this.root.toString());
      values.put("assets_root", this.root.resolve("assets").toString());
      values.put("assets_index_name", this.assetIndex);
      values.put("auth_uuid", offlineId(nickname));
      values.put("auth_access_token", "0");
      values.put("auth_session", "0");
      values.put("clientid", "");
      values.put("auth_xuid", "");
      values.put("user_type", "msa");
      values.put("version_type", Json.text(this.vanilla.get("type"), "release"));
      values.put("user_properties", "{}");

      List<String> command = new ArrayList<>();
      command.add(this.runtime.toString());
      command.add("-Xmx" + Math.max(2, memoryGb) + "G");
      command.add("-Xms512M");
      command.add("-XX:+UseG1GC");
      command.addAll(arguments(this.vanilla, "jvm", values));
      command.addAll(arguments(this.fabric, "jvm", values));
      if (!command.contains("-cp")) {
         command.add("-cp");
         command.add(join(this.classpath));
      }

      command.add(Json.text(this.fabric.get("mainClass"), "net.fabricmc.loader.impl.launch.knot.KnotClient"));
      command.addAll(arguments(this.vanilla, "game", values));
      command.addAll(arguments(this.fabric, "game", values));
      return command;
   }

   /**
    * Reads one argument list out of a version description, keeping only the entries whose rules
    * match this machine. Conditional blocks cover demo mode and a fixed window size, neither of
    * which the loader asks for.
    */
   private static List<String> arguments(Map<String, Object> description, String section, Map<String, String> values) {
      List<String> result = new ArrayList<>();
      Object arguments = Json.at(description, "arguments", section);
      if (arguments == null) {
         if ("game".equals(section)) {
            for (String piece : Json.text(description.get("minecraftArguments"), "").split(" ")) {
               if (!piece.isBlank()) {
                  result.add(fill(piece, values));
               }
            }
         }

         return result;
      }

      for (Object entry : Json.array(arguments)) {
         if (entry instanceof String piece) {
            result.add(fill(piece, values));
         } else if (allowed(Json.array(Json.get(entry, "rules")))) {
            Object value = Json.get(entry, "value");
            if (value instanceof String piece) {
               result.add(fill(piece, values));
            } else {
               for (Object item : Json.array(value)) {
                  result.add(fill(Json.text(item, ""), values));
               }
            }
         }
      }

      return result;
   }

   private static String fill(String template, Map<String, String> values) {
      String result = template;

      for (Map.Entry<String, String> value : values.entrySet()) {
         result = result.replace("${" + value.getKey() + "}", value.getValue());
      }

      return result;
   }

   private static String join(List<Path> paths) {
      StringBuilder builder = new StringBuilder();

      for (Path path : paths) {
         if (builder.length() > 0) {
            builder.append(File.pathSeparatorChar);
         }

         builder.append(path);
      }

      return builder.toString();
   }

   /** The identifier a server without authentication would give this nickname. */
   static String offlineId(String nickname) {
      UUID uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + nickname).getBytes(StandardCharsets.UTF_8));
      return uuid.toString();
   }

   /**
    * Evaluates the rule list of a library or argument. Rules deny by default, and the features
    * they can ask about (demo mode, custom resolution, quick play) are all off here.
    */
   private static boolean allowed(List<Object> rules) {
      if (rules.isEmpty()) {
         return true;
      }

      boolean result = false;

      for (Object rule : rules) {
         boolean matches = true;
         Object os = Json.get(rule, "os");
         if (os != null) {
            String name = Json.text(Json.get(os, "name"), null);
            String architecture = Json.text(Json.get(os, "arch"), null);
            String versionPattern = Json.text(Json.get(os, "version"), null);
            matches = (name == null || name.equals(osName()))
               && (architecture == null || architecture.equals(osArch()))
               && (versionPattern == null || System.getProperty("os.version", "").matches(".*" + versionPattern + ".*"));
         }

         if (!Json.object(Json.get(rule, "features")).isEmpty()) {
            matches = false;
         }

         if (matches) {
            result = "allow".equals(Json.text(Json.get(rule, "action"), "allow"));
         }
      }

      return result;
   }

   static String osName() {
      String os = System.getProperty("os.name", "").toLowerCase(Locale.ROOT);
      if (os.contains("win")) {
         return "windows";
      } else {
         return os.contains("mac") ? "osx" : "linux";
      }
   }

   private static String osArch() {
      String arch = System.getProperty("os.arch", "").toLowerCase(Locale.ROOT);
      if (arch.contains("aarch64") || arch.contains("arm64")) {
         return "arm64";
      } else {
         return is64Bit() ? "x64" : "x86";
      }
   }

   private static boolean is64Bit() {
      return System.getProperty("os.arch", "").contains("64");
   }
}
