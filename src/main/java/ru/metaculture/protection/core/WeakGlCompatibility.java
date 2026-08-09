package ru.metaculture.protection;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public final class WeakGlCompatibility {
   private static volatile int intValue = -1;
   private static volatile Boolean booleanValue;

   private WeakGlCompatibility() {
   }

   public static void invoke(int i) {
      if (i <= 0) {
         intValue = -1;
      } else {
         intValue = check(i) ? i : -1;
      }
   }

   public static boolean check(int i) {
      if (i <= 0) {
         return false;
      } else {
         try {
            return GL30.glIsFramebuffer(i);
         } catch (Throwable exception) {
            return false;
         }
      }
   }

   public static boolean check2() {
      Boolean booleanValue = resolve(System.getProperty("wild.render.weakGl"));
      if (booleanValue != null) {
         return booleanValue;
      } else if (booleanValue != null) {
         return booleanValue;
      } else {
         synchronized (WeakGlCompatibility.class) {
            if (booleanValue != null) {
               return booleanValue;
            } else {
               booleanValue = check3();
               return booleanValue;
            }
         }
      }
   }

   public static void invoke2(MinecraftClient minecraftClient) {
      if (minecraftClient != null && minecraftClient.getWindow() != null) {
         Window window = minecraftClient.getWindow();
         int intValue = window.getFramebufferWidth();
         int intValue2 = window.getFramebufferHeight();
         if (intValue > 0 && intValue2 > 0) {
            int intValue3 = intValue;
            if (intValue3 > 0 && !check(intValue3)) {
               intValue = -1;
               intValue3 = -1;
            }

            if (intValue3 <= 0) {
               intValue3 = GL11.glGetInteger(36006);
            }

            if (intValue3 > 0 && !check(intValue3)) {
               intValue = -1;
               intValue3 = -1;
            }

            if (intValue3 > 0) {
               GL30.glBindFramebuffer(36009, intValue3);
               GL30.glBindFramebuffer(36008, intValue3);
               GL11.glDrawBuffer(36064);
               GL11.glReadBuffer(36064);
            }

            GL11.glViewport(0, 0, Math.max(0, intValue), Math.max(0, intValue2));
            GL11.glColorMask(true, true, true, true);
            GL11.glDisable(3089);
         }
      }
   }

   private static Boolean resolve(String string) {
      if (string != null && !string.isBlank()) {
         String text = string.trim().toLowerCase();

         return switch (text) {
            case "true", "1", "yes", "on" -> true;
            case "false", "0", "no", "off" -> false;
            default -> null;
         };
      } else {
         return null;
      }
   }

   private static boolean check3() {
      if (GLFW.glfwGetCurrentContext() == 0L) {
         return false;
      } else {
         try {
            String text2 = resolve2(GL11.glGetString(7936));
            String text3 = resolve2(GL11.glGetString(7937));
            String text4 = GL11.glGetString(7938);
            float floatValue = measure(text4);
            return !text2.contains("intel") && !text3.contains("intel") && !text3.contains("hd graphics") ? floatValue > 0.0F && floatValue < 4.0F : true;
         } catch (Throwable exception2) {
            return false;
         }
      }
   }

   private static float measure(String string) {
      if (string != null && !string.isBlank()) {
         int intValue4 = string.indexOf(32);
         String text5 = intValue4 >= 0 ? string.substring(0, intValue4) : string;
         int intValue5 = text5.indexOf(46);
         if (intValue5 <= 0) {
            return 0.0F;
         } else {
            try {
               return Float.parseFloat(text5.substring(0, intValue5));
            } catch (NumberFormatException numberFormatException) {
               return 0.0F;
            }
         }
      } else {
         return 0.0F;
      }
   }

   private static String resolve2(String string) {
      return string == null ? "" : string.toLowerCase();
   }
}
