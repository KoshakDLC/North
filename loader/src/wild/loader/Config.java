package wild.loader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/** Loader settings, persisted next to the client data in %APPDATA%. */
final class Config {
   /** Hidden override: the loader finds the game folder itself unless this is set by hand. */
   static final String MC_DIR = "minecraft.dir";
   static final String JAR = "client.jar";
   static final String REPO = "github.repo";
   static final String CLIENT_URL = "client.url";
   static final String DEFAULT_REPO = "KoshakDLC/North";
   static final String RAM = "memory.gb";
   static final String NICK = "player.name";
   static final String LAUNCH_CMD = "launch.command";
   static final String CLOSE_ON_LAUNCH = "close.on.launch";
   static final String AUTO_INSTALL = "auto.install";

   private final Path file;
   private final Properties properties = new Properties();

   private Config(Path file) {
      this.file = file;
   }

   static Config load() {
      Path directory = appData().resolve("North");
      Config config = new Config(directory.resolve("loader.properties"));

      try {
         if (Files.exists(config.file)) {
            try (InputStream stream = Files.newInputStream(config.file)) {
               config.properties.load(stream);
            }
         }
      } catch (IOException exception) {
      }

      return config;
   }

   void save() {
      try {
         Path parent = this.file.getParent();
         if (parent != null) {
            Files.createDirectories(parent);
         }

         try (OutputStream stream = Files.newOutputStream(this.file)) {
            this.properties.store(stream, "NorthLoader");
         }
      } catch (IOException exception2) {
      }
   }

   String get(String key, String fallback) {
      String value = this.properties.getProperty(key);
      return value == null || value.isBlank() ? fallback : value.trim();
   }

   void set(String key, String value) {
      if (value == null || value.isBlank()) {
         this.properties.remove(key);
      } else {
         this.properties.setProperty(key, value.trim());
      }
   }

   int getInt(String key, int fallback) {
      try {
         return Integer.parseInt(this.get(key, Integer.toString(fallback)));
      } catch (NumberFormatException exception3) {
         return fallback;
      }
   }

   boolean getBoolean(String key, boolean fallback) {
      return Boolean.parseBoolean(this.get(key, Boolean.toString(fallback)));
   }

   void setBoolean(String key, boolean value) {
      this.set(key, Boolean.toString(value));
   }

   /**
    * The nickname for the offline session. Servers reject names with spaces or non-latin letters,
    * so whatever is typed gets trimmed down to something a server accepts.
    */
   String nickname() {
      String value = this.get(NICK, "");
      return sanitizeNickname(value.isEmpty() ? System.getProperty("user.name", "") : value);
   }

   static String sanitizeNickname(String value) {
      StringBuilder builder = new StringBuilder();

      for (char character : value.toCharArray()) {
         if (character == '_' || character < 128 && Character.isLetterOrDigit(character)) {
            builder.append(character);
         }
      }

      if (builder.length() < 3) {
         return "Player";
      }

      return builder.length() > 16 ? builder.substring(0, 16) : builder.toString();
   }

   /** Where downloaded builds are kept between launches. */
   static Path cacheDir() {
      return appData().resolve("North").resolve("cache");
   }

   /** Java runtimes downloaded from Mojang; shared by every game version. */
   static Path runtimeDir() {
      return appData().resolve("North").resolve("runtime");
   }

   /** Everything the launched game prints, so a crash on startup can be explained. */
   static Path gameLog() {
      return appData().resolve("North").resolve("game.log");
   }

   static Path appData() {
      String appData = System.getenv("APPDATA");
      return appData != null && !appData.isBlank() ? Paths.get(appData) : Paths.get(System.getProperty("user.home"));
   }

   /** Default .minecraft location for the current platform. */
   static Path defaultMinecraftDir() {
      String os = System.getProperty("os.name", "").toLowerCase();
      if (os.contains("win")) {
         return appData().resolve(".minecraft");
      } else {
         Path home = Paths.get(System.getProperty("user.home"));
         return os.contains("mac") ? home.resolve("Library/Application Support/minecraft") : home.resolve(".minecraft");
      }
   }

   /** Newest built mod jar, searched in the gradle output of the surrounding project. */
   static Path findBuiltJar() {
      Path working = Paths.get(System.getProperty("user.dir")).toAbsolutePath();

      for (Path base : new Path[]{working, working.getParent()}) {
         if (base != null) {
            Path libs = base.resolve("build").resolve("libs");
            Path found = newestJar(libs);
            if (found != null) {
               return found;
            }
         }
      }

      return null;
   }

   private static Path newestJar(Path directory) {
      if (!Files.isDirectory(directory)) {
         return null;
      } else {
         Path best = null;

         try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory, "*.jar")) {
            for (Path candidate : stream) {
               String name = candidate.getFileName().toString();
               if (!name.endsWith("-sources.jar") && !name.endsWith("-dev.jar") && !name.endsWith("-javadoc.jar")) {
                  if (best == null || Files.getLastModifiedTime(candidate).compareTo(Files.getLastModifiedTime(best)) > 0) {
                     best = candidate;
                  }
               }
            }
         } catch (IOException exception4) {
            return best;
         }

         return best;
      }
   }

   /** Stable per machine identifier, shown in the interface so a user can name their setup. */
   static String hardwareId() {
      try {
         String seed = System.getProperty("user.name", "?") + "|" + hostName() + "|" + System.getProperty("os.arch", "?");
         byte[] digest = java.security.MessageDigest.getInstance("SHA-256").digest(seed.getBytes(java.nio.charset.StandardCharsets.UTF_8));
         StringBuilder builder = new StringBuilder();

         for (int i = 0; i < 8; i++) {
            builder.append(String.format("%02x", digest[i]));
         }

         return builder.toString();
      } catch (Exception exception5) {
         return "unknown";
      }
   }

   private static String hostName() {
      String name = System.getenv("COMPUTERNAME");
      if (name == null || name.isBlank()) {
         try {
            name = java.net.InetAddress.getLocalHost().getHostName();
         } catch (Exception exception6) {
            name = "host";
         }
      }

      return name;
   }
}
