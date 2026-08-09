package ru.metaculture.protection;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import lombok.Generated;

public final class NativeMediaController {
   private static boolean flag;

   public static void invoke() {
   }

   public static void invoke2() {
   }

   public static void invoke3() {
   }

   public static void invoke4(long l) {
   }

   @Generated
   public static boolean isFlag() {
      return flag;
   }

   static {
      flag = false;
      label95:
      if (System.getProperty("os.name", "").toLowerCase(Locale.ROOT).contains("win")) {
         Path path = null;
         boolean flag = false ;

         label92: {
            try {
               flag = true;
               path = Files.createTempFile("wild_media_controller", ".dll");

               try (InputStream inputStream = NativeMediaController.class.getResourceAsStream("/assets/wild/natives/MediaController.dll")) {
                  if (inputStream != null) {
                     Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
                     System.load(path.toAbsolutePath().toString());
                     flag = true;
                  }
                  break label92;
               }
            } catch (UnsatisfiedLinkError | Exception exception) {
               flag = false;
               flag = false;
            } finally {
               if (flag) {
                  if (path != null) {
                     path.toFile().deleteOnExit();
                  }
               }
            }

            if (path != null) {
               path.toFile().deleteOnExit();
            }
            break label95;
         }

         if (path != null) {
            path.toFile().deleteOnExit();
         }
      }
   }
}
