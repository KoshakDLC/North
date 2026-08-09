package ru.metaculture.protection;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;

public final class RenderStateMaintenance {
   private static long timestamp;
   private static long timestamp2;

   private RenderStateMaintenance() {
   }

   public static void invoke() {
      long longValue = System.nanoTime();
      if (longValue - timestamp >= 2000000L) {
         timestamp = longValue;
         invoke3();
      }
   }

   public static void invoke2() {
      invoke3();
   }

   private static void invoke3() {
      try {
         GL13.glActiveTexture(33984);
         GL11.glPixelStorei(3317, 4);
         GL12.glPixelStorei(3314, 0);
      } catch (Throwable exception) {
      }
   }

   public static void invoke4() {
      long longValue2 = System.nanoTime();
      if (longValue2 - timestamp2 >= 250000000L) {
         timestamp2 = longValue2;

         try {
            GL13.glActiveTexture(33984);
            GL11.glPixelStorei(3317, 4);
            GL12.glPixelStorei(3314, 0);
            FontRegistry.invoke3();
         } catch (Throwable exception2) {
         }
      }
   }
}
