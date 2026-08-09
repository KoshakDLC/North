package ru.metaculture.protection;

import net.minecraft.util.math.MathHelper;

public class DeltaTimeLerp {
   private static final double DOUBLE_VALUE = 0.1;

   public static double measure() {
      return Math.min((double)AnimationSystem.getINSTANCE().getFloatValue(), 0.1);
   }

   public static float measure2(float f, float g, float h) {
      return (1.0F - MathHelper.clamp((float)(measure() * h), 0.0F, 1.0F)) * f + MathHelper.clamp((float)(measure() * h), 0.0F, 1.0F) * g;
   }

   public static float measure3(float f, float g, float h) {
      float floatValue = (g - f) * MathHelper.clamp((float)(measure() * 15.0), 0.0F, 1.0F);
      if (floatValue > 0.0F) {
         floatValue = Math.max(h, floatValue);
         floatValue = Math.min(g - f, floatValue);
      } else if (floatValue < 0.0F) {
         floatValue = Math.min(-h, floatValue);
         floatValue = Math.max(g - f, floatValue);
      }

      return f + floatValue;
   }

   public static double measure4(double d, double e, double f) {
      return e + (d - e) * f;
   }

   public static float measure5(float f, float g, float h, double d) {
      float floatValue2 = g - f;
      if (h < 1.0F) {
         h = 1.0F;
      }

      if (h > 100.0F) {
         h = 16.666666F;
      }

      double doubleValue = Math.max(d * h / 16.666666F, 0.5);
      if (floatValue2 > d) {
         if ((g = (float)(g - doubleValue)) < f) {
            g = f;
         }
      } else if (floatValue2 < -d) {
         if ((g = (float)(g + doubleValue)) > f) {
            g = f;
         }
      } else {
         g = f;
      }

      return g;
   }

   public static float measure6(float f, float g, float h, float i, float j) {
      float floatValue3 = (g - f) * MathHelper.clamp(j, 0.0F, 1.0F);
      if (floatValue3 < 0.0F) {
         floatValue3 = MathHelper.clamp(floatValue3, -i, -h);
      } else {
         floatValue3 = MathHelper.clamp(floatValue3, h, i);
      }

      return Math.abs(floatValue3) > Math.abs(g - f) ? g : f + floatValue3;
   }

   public static double measure7(double d, double e, double f) {
      boolean flag = d > e;
      if (f < 0.0) {
         f = 0.0;
      } else if (f > 1.0) {
         f = 1.0;
      }

      double doubleValue2 = Math.max(d, e) - Math.min(d, e);
      double doubleValue3 = doubleValue2 * f;
      if (doubleValue3 < 0.1) {
         doubleValue3 = 0.1;
      }

      if (flag) {
         e += doubleValue3;
      } else {
         e -= doubleValue3;
      }

      return e;
   }
}
