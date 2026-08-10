package dev.redstones.mediaplayerinfo.impl.win;

import dev.redstones.mediaplayerinfo.MediaPlayerInfo;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public final class WindowsMediaPlayerInfo implements MediaPlayerInfo {
   private static final String RESOURCE_PATH = "/mediaplayerinfo/natives/win/MediaPlayerInfo.dll";
   private static final boolean LOADED;
   private static final Throwable LOAD_ERROR;
   private static final AtomicBoolean LOAD_ERROR_LOGGED = new AtomicBoolean(false);

   public WindowsMediaPlayerInfo() {
      if (!LOADED) {
         logLoadError();
      }
   }

   public static boolean isAvailable() {
      if (!LOADED) {
         logLoadError();
      }

      return LOADED;
   }

   @Override
   public List getMediaSessions() {
      return null;
   }

   private static void logLoadError() {
      if (LOAD_ERROR_LOGGED.compareAndSet(false, true)) {
         System.err.println("[North][MusicPlayer] MediaPlayerInfo native load failed: " + errorSummary(LOAD_ERROR));
      }
   }

   private static String errorSummary(Throwable throwable) {
      if (throwable == null) {
         return "unknown";
      } else {
         String text = throwable.getMessage();
         return throwable.getClass().getSimpleName() + (text != null && !text.isBlank() ? ": " + text : "");
      }
   }

   private static WindowsMediaPlayerInfo.LoadResult loadNative() {
      try {
         Path path = Files.createTempDirectory("mediaplayerinfo-");
         Path path2 = path.resolve("MediaPlayerInfo.dll");

         try (InputStream inputStream = WindowsMediaPlayerInfo.class.getResourceAsStream("/mediaplayerinfo/natives/win/MediaPlayerInfo.dll")) {
            if (inputStream == null) {
               throw new IOException("Resource not found: /mediaplayerinfo/natives/win/MediaPlayerInfo.dll");
            }

            Files.copy(inputStream, path2, StandardCopyOption.REPLACE_EXISTING);
         }

         System.load(path2.toAbsolutePath().toString());

         try {
            Files.deleteIfExists(path2);
            Files.deleteIfExists(path);
         } catch (IOException ioException) {
            path2.toFile().deleteOnExit();
            path.toFile().deleteOnExit();
         }

         return new WindowsMediaPlayerInfo.LoadResult(true, null);
      } catch (Throwable exception) {
         return new WindowsMediaPlayerInfo.LoadResult(false, exception);
      }
   }

   static {
      WindowsMediaPlayerInfo.LoadResult loadResult = loadNative();
      LOADED = loadResult.loaded();
      LOAD_ERROR = loadResult.error();
   }

   record LoadResult(boolean loaded, Throwable error) {
   }
}
