package ru.metaculture.protection;

import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL30;

public final class FramebufferPool {
   private static int intValue;
   private static int intValue2;

   private FramebufferPool() {
   }

   public static int compute() {
      if (!check()) {
         return 0;
      } else {
         if (intValue == 0) {
            intValue = GL30.glGenFramebuffers();
         }

         return intValue;
      }
   }

   public static int compute2() {
      if (!check()) {
         return 0;
      } else {
         if (intValue2 == 0) {
            intValue2 = GL30.glGenFramebuffers();
         }

         return intValue2;
      }
   }

   public static void invoke(int i) {
      if (intValue == i) {
         intValue = 0;
      }
   }

   public static void invoke2() {
      if (!check()) {
         intValue = 0;
         intValue2 = 0;
      } else {
         if (intValue != 0) {
            GL30.glDeleteFramebuffers(intValue);
            intValue = 0;
         }

         if (intValue2 != 0) {
            GL30.glDeleteFramebuffers(intValue2);
            intValue2 = 0;
         }
      }
   }

   private static boolean check() {
      return RenderSystem.isOnRenderThread() && GLFW.glfwGetCurrentContext() != 0L;
   }
}
