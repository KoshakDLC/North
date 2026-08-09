package ru.metaculture.protection;

import java.io.File;
import java.lang.reflect.Method;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Util;

public final class FiguraAvatarImporter {
   private FiguraAvatarImporter() {
   }

   public static File resolve() {
      try {
         Class type = Class.forName("org.lwjgl.util.tinyfd.TinyFileDialogs");
         Class type2 = Class.forName("org.lwjgl.PointerBuffer");
         Method method = type.getMethod("tinyfd_openFileDialog", CharSequence.class, CharSequence.class, type2, CharSequence.class, boolean.class);
         if (method.invoke(null, "Импорт аватара Figura", "", null, "Figura avatar (.zip)", false) instanceof CharSequence charSequence && charSequence.length() > 0) {
            File file = new File(charSequence.toString());
            return file.exists() ? file : null;
         }
      } catch (Throwable exception) {
      }

      return null;
   }

   public static void invoke() {
      try {
         File file2 = StudioLibrary.resolve().getFile();
         if (!file2.exists()) {
            file2.mkdirs();
         }

         Util.getOperatingSystem().open(file2);
      } catch (Throwable exception2) {
      }

      MinecraftClient client = MinecraftClient.getInstance();
      if (client != null) {
      }
   }
}
