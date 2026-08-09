package ru.metaculture.protection;

import java.io.DataOutputStream;
import java.io.IOException;
import org.lwjgl.opengl.GL11;

public final class GlStateInspector {
   private GlStateInspector() {
   }

   public static int getCurrentProgram() {
      try {
         return GL11.glGetInteger(35725);
      } catch (Throwable exception) {
         return -1;
      }
   }

   public static int getActiveTexture() {
      try {
         return GL11.glGetInteger(34016);
      } catch (Throwable exception2) {
         return -1;
      }
   }

   public static int getTextureBinding2D() {
      try {
         return GL11.glGetInteger(32873);
      } catch (Throwable exception3) {
         return -1;
      }
   }

   public static int pollGlError() {
      try {
         for (int intValue = 0; intValue < 4; intValue++) {
            int intValue2 = GL11.glGetError();
            if (intValue2 != 0) {
               return intValue2;
            }
         }

         return 0;
      } catch (Throwable exception4) {
         return -1;
      }
   }

   static void hashInto(RenderStateHasher renderStateHasher) {
      if (renderStateHasher != null) {
         renderStateHasher.invoke(getCurrentProgram());
         renderStateHasher.invoke(getActiveTexture());
         renderStateHasher.invoke(getTextureBinding2D());
      }
   }

   static void writeInto(DataOutputStream dataOutputStream) throws IOException {
      dataOutputStream.writeInt(getCurrentProgram());
      dataOutputStream.writeInt(getActiveTexture());
      dataOutputStream.writeInt(getTextureBinding2D());
      dataOutputStream.writeInt(pollGlError());
   }

   public static String glErrorName(int i) {
      return switch (i) {
         case 0 -> "GL_NO_ERROR";
         case 1280 -> "GL_INVALID_ENUM";
         case 1281 -> "GL_INVALID_VALUE";
         case 1282 -> "GL_INVALID_OPERATION";
         case 1285 -> "GL_OUT_OF_MEMORY";
         default -> "0x" + Integer.toHexString(i);
      };
   }
}
