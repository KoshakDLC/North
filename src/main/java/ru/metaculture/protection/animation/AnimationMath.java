package ru.metaculture.protection;

import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class AnimationMath {
   public static double doubleValue;

   public static float measure(float f, float g, float h) {
      float floatValue = (g - f) * MathHelper.clamp(AnimationSystem.getINSTANCE().getFloatValue() * 15.0F, 0.0F, 1.0F);
      if (floatValue > 0.0F) {
         floatValue = Math.max(h, floatValue);
         floatValue = Math.min(g - f, floatValue);
      } else if (floatValue < 0.0F) {
         floatValue = Math.min(-h, floatValue);
         floatValue = Math.max(g - f, floatValue);
      }

      return f + floatValue;
   }

   public static double measure2(double d, double e, double f) {
      double doubleValue = (e - d) * MathHelper.clamp((float)(AnimationSystem.getINSTANCE().getFloatValue() * f), 0.0F, 1.0F);
      if (doubleValue > 0.0) {
         doubleValue = Math.max(f, doubleValue);
         doubleValue = Math.min(e - d, doubleValue);
      } else if (doubleValue < 0.0) {
         doubleValue = Math.min(-f, doubleValue);
         doubleValue = Math.max(e - d, doubleValue);
      }

      return d + doubleValue;
   }

   public static float measure3(float f, float g, float h, double d) {
      float floatValue2 = g - f;
      if (h < 1.0F) {
         h = 1.0F;
      }

      if (h > 1000.0F) {
         h = 16.0F;
      }

      double doubleValue2 = Math.max(d * h / 16.666666F, 0.5);
      if (floatValue2 > d) {
         if ((g = g - (float)doubleValue2) < f) {
            g = f;
         }
      } else if (floatValue2 < -d) {
         if ((g = g + (float)doubleValue2) > f) {
            g = f;
         }
      } else {
         g = f;
      }

      return g;
   }

   public static float measure4(float f, float g, float h, float i, float j) {
      float floatValue3 = (g - f) * MathHelper.clamp(j, 0.0F, 1.0F);
      if (floatValue3 < 0.0F) {
         floatValue3 = MathHelper.clamp(floatValue3, -i, -h);
      } else {
         floatValue3 = MathHelper.clamp(floatValue3, h, i);
      }

      return Math.abs(floatValue3) > Math.abs(g - f) ? g : f + floatValue3;
   }

   public static double measure5(double d, double e, double f) {
      return d + (e - d) * f;
   }

   public static float measure6(float f, float g, float h) {
      float floatValue4 = (float)(doubleValue * (h / 1000.0F));
      if (f < g) {
         if (f + floatValue4 < g) {
            f += floatValue4;
         } else {
            f = g;
         }
      } else if (f - floatValue4 > g) {
         f -= floatValue4;
      } else {
         f = g;
      }

      return f;
   }

   public static void invoke(float f, float g, float h, Runnable runnable) {
      GL11.glPushMatrix();
      GL11.glTranslatef(f, g, 0.0F);
      GL11.glScalef(h, h, 1.0F);
      GL11.glTranslatef(-f, -g, 0.0F);
      runnable.run();
      GL11.glPopMatrix();
   }

   public static void invoke2(float f, float g, Runnable runnable) {
      GL11.glPushMatrix();
      GL11.glTranslatef(f, g, 0.0F);
      runnable.run();
      GL11.glPopMatrix();
   }
}
