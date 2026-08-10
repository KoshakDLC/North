package wild.loader;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Provides the Java the game asks for. Mojang publishes a runtime for every platform, so the
 * loader installs that one next to its own data instead of demanding a JDK from the user; a
 * suitable installation already on the machine is only the fallback.
 */
final class Runtimes {
   private static final String ALL =
      "https://launchermeta.mojang.com/v1/products/java-runtime/2ec0cc96c44e5a76b9c8b7c39df7210883d12871/all.json";
   private static final Pattern VERSION = Pattern.compile("version \"([^\"]+)\"");

   private Runtimes() {
   }

   /**
    * The java binary to launch with. {@code component} is the runtime name from the version
    * description, {@code minimum} the feature release a system installation has to reach.
    */
   static Path locate(String component, int minimum, Game.Report report) throws IOException, InterruptedException {
      Path directory = Config.runtimeDir().resolve(component).resolve(platform());
      Path present = binary(directory);
      if (present != null) {
         return present;
      }

      try {
         Path installed = install(component, directory, report);
         if (installed != null) {
            report.ok("Java для игры установлена.");
            return installed;
         }

         report.warn("Mojang не собирает " + component + " для этой системы.");
      } catch (InterruptedException interruption) {
         throw interruption;
      } catch (Exception failure) {
         report.warn("Java от Mojang не скачалась: " + describe(failure));
      }

      Path system = system(minimum);
      if (system == null) {
         throw new IOException("Нужна Java " + minimum + " — поставь её или дай загрузчику доступ в сеть");
      }

      report.warn("Беру Java из системы: " + system);
      return system;
   }

   private static Path install(String component, Path directory, Game.Report report) throws IOException, InterruptedException {
      List<Object> builds = Json.array(Json.at(Json.parse(Downloader.text(ALL)), platform(), component));
      if (builds.isEmpty()) {
         return null;
      }

      String manifest = Json.text(Json.at(builds.get(0), "manifest", "url"), null);
      if (manifest == null) {
         return null;
      }

      report.info("Скачиваю Java " + Json.text(Json.at(builds.get(0), "version", "name"), component) + " — это разово.");
      Map<String, Object> files = Json.object(Json.get(Json.parse(Downloader.text(manifest)), "files"));
      List<Callable<Void>> downloads = new ArrayList<>();
      List<Path[]> links = new ArrayList<>();
      AtomicLong done = new AtomicLong();
      long[] total = new long[1];

      for (Map.Entry<String, Object> entry : files.entrySet()) {
         Path target = directory.resolve(entry.getKey().replace('/', File.separatorChar));
         Object value = entry.getValue();
         String type = Json.text(Json.get(value, "type"), "");
         if ("directory".equals(type)) {
            Files.createDirectories(target);
         } else if ("link".equals(type)) {
            links.add(new Path[]{target, Paths.get(Json.text(Json.get(value, "target"), ""))});
         } else {
            Object raw = Json.at(value, "downloads", "raw");
            String url = Json.text(Json.get(raw, "url"), null);
            if (url != null) {
               boolean executable = Json.flag(Json.get(value, "executable"), false);
               downloads.add(() -> {
                  Downloader.fileIfMissing(url, target, Json.text(Json.get(raw, "sha1"), null), Json.number(Json.get(raw, "size"), -1L));
                  if (executable) {
                     allowExecution(target);
                  }

                  long count = done.incrementAndGet();
                  if (count % 20L == 0L || count == total[0]) {
                     report.step(0.80 + 0.16 * Math.min(1.0, (double)count / total[0]), "Java");
                  }

                  return null;
               });
            }
         }
      }

      total[0] = Math.max(1, downloads.size());
      Downloader.parallel(downloads);

      for (Path[] link : links) {
         link(link[0], link[1]);
      }

      return binary(directory);
   }

   /** Symlinks only exist in the macOS and Linux runtimes, and Windows refuses them without rights. */
   private static void link(Path path, Path target) {
      try {
         Path parent = path.getParent();
         if (parent != null) {
            Files.createDirectories(parent);
         }

         Files.deleteIfExists(path);
         Files.createSymbolicLink(path, target);
      } catch (Exception exception) {
         try {
            Path source = path.resolveSibling(target).normalize();
            if (Files.isRegularFile(source)) {
               Files.copy(source, path, StandardCopyOption.REPLACE_EXISTING);
            }
         } catch (IOException ignored) {
         }
      }
   }

   private static void allowExecution(Path path) {
      try {
         Files.setPosixFilePermissions(path, PosixFilePermissions.fromString("rwxr-xr-x"));
      } catch (Exception exception) {
      }
   }

   /** {@code javaw} on Windows keeps the console window away; the rest is the usual layout. */
   private static Path binary(Path home) {
      String[] candidates = windows()
         ? new String[]{"bin/javaw.exe", "bin/java.exe"}
         : new String[]{"bin/java", "jre.bundle/Contents/Home/bin/java", "Contents/Home/bin/java"};

      for (String candidate : candidates) {
         Path path = home.resolve(candidate.replace('/', File.separatorChar));
         if (Files.isRegularFile(path)) {
            return path;
         }
      }

      return null;
   }

   /** Looks through the usual installation folders for a Java new enough to run the game. */
   private static Path system(int minimum) {
      List<Path> homes = new ArrayList<>();
      homes.add(Paths.get(System.getProperty("java.home", ".")));
      String configured = System.getenv("JAVA_HOME");
      if (configured != null && !configured.isBlank()) {
         homes.add(Paths.get(configured));
      }

      children(homes, Paths.get(System.getProperty("user.home", "."), ".jdks"));

      for (String variable : new String[]{"ProgramFiles", "ProgramFiles(x86)", "LOCALAPPDATA"}) {
         String base = System.getenv(variable);
         if (base != null && !base.isBlank()) {
            children(homes, Paths.get(base, "Java"));
            children(homes, Paths.get(base, "Eclipse Adoptium"));
            children(homes, Paths.get(base, "Microsoft"));
            children(homes, Paths.get(base, "Zulu"));
            children(homes, Paths.get(base, "Programs", "Eclipse Adoptium"));
         }
      }

      children(homes, Paths.get("/usr/lib/jvm"));
      children(homes, Paths.get("/Library/Java/JavaVirtualMachines"));

      for (Path home : homes) {
         Path binary = binary(home);
         if (binary != null && major(home) >= minimum) {
            return binary;
         }
      }

      return null;
   }

   private static void children(List<Path> target, Path directory) {
      if (Files.isDirectory(directory)) {
         try (java.nio.file.DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
            for (Path candidate : stream) {
               if (Files.isDirectory(candidate)) {
                  target.add(candidate);
               }
            }
         } catch (IOException exception) {
         }
      }
   }

   /** The feature release of an installation, read from its {@code release} file when there is one. */
   private static int major(Path home) {
      if (home.equals(Paths.get(System.getProperty("java.home", ".")))) {
         return Runtime.version().feature();
      }

      Path release = home.resolve("release");
      if (Files.isRegularFile(release)) {
         try {
            for (String line : Files.readAllLines(release, StandardCharsets.ISO_8859_1)) {
               if (line.startsWith("JAVA_VERSION=")) {
                  return feature(line.substring("JAVA_VERSION=".length()).replace("\"", "").trim());
               }
            }
         } catch (IOException exception) {
         }
      }

      return probe(home);
   }

   private static int probe(Path home) {
      Path java = home.resolve("bin").resolve(windows() ? "java.exe" : "java");
      if (!Files.isRegularFile(java)) {
         return -1;
      }

      try {
         Process process = new ProcessBuilder(java.toString(), "-version").redirectErrorStream(true).start();
         String output = new String(process.getInputStream().readAllBytes(), StandardCharsets.ISO_8859_1);
         process.waitFor();
         Matcher matcher = VERSION.matcher(output);
         return matcher.find() ? feature(matcher.group(1)) : -1;
      } catch (InterruptedException interruption) {
         Thread.currentThread().interrupt();
         return -1;
      } catch (Exception exception) {
         return -1;
      }
   }

   /** {@code 21.0.2} is 21, and the old {@code 1.8.0_401} is 8. */
   private static int feature(String version) {
      String[] parts = version.split("[._+-]");

      try {
         int first = Integer.parseInt(parts[0]);
         return first == 1 && parts.length > 1 ? Integer.parseInt(parts[1]) : first;
      } catch (NumberFormatException exception) {
         return -1;
      }
   }

   static String platform() {
      String os = Game.osName();
      String arch = System.getProperty("os.arch", "").toLowerCase(Locale.ROOT);
      boolean arm = arch.contains("aarch64") || arch.contains("arm");
      boolean wide = arch.contains("64");
      if ("windows".equals(os)) {
         return arm ? "windows-arm64" : (wide ? "windows-x64" : "windows-x86");
      } else if ("osx".equals(os)) {
         return arm ? "mac-os-arm64" : "mac-os";
      } else {
         return wide ? "linux" : "linux-i386";
      }
   }

   private static boolean windows() {
      return "windows".equals(Game.osName());
   }

   private static String describe(Throwable throwable) {
      String message = throwable.getMessage();
      return message == null || message.isBlank() ? throwable.getClass().getSimpleName() : message;
   }
}
