package ru.metaculture.protection;

public final class RotationPresetEditorState {
   private static final float FLOAT_VALUE = 1.5F;
   private static final float FLOAT_VALUE_2 = 0.875F;
   private static final float FLOAT_VALUE_3 = 0.5625F;
   private static final float FLOAT_VALUE_4 = -0.25F;
   private static final float FLOAT_VALUE_5 = 0.25F;
   private static final float FLOAT_VALUE_6 = 0.0F;
   private static final float FLOAT_VALUE_7 = 0.375F;
   private static final float FLOAT_VALUE_8 = -0.25F;
   private static final float FLOAT_VALUE_9 = 0.25F;
   private static final float FLOAT_VALUE_10 = 0.375F;
   private static final float FLOAT_VALUE_11 = 0.75F;
   private static final float FLOAT_VALUE_12 = -0.5F;
   private static final float FLOAT_VALUE_13 = -0.25F;
   private static final float FLOAT_VALUE_14 = 0.25F;
   private static final float FLOAT_VALUE_15 = 0.5F;
   private static final float FLOAT_VALUE_16 = -0.25F;
   private static final float FLOAT_VALUE_17 = 0.25F;
   private static final float FLOAT_VALUE_18 = 0.75F;
   private static final float FLOAT_VALUE_19 = 1.0F;

   private RotationPresetEditorState() {
   }

   public static void invoke(RenderManager renderManager, float f, float g, float h, float i, float j, float k, float l, float m, float n) {
      if (renderManager != null && !(l <= 1.0F) && !(m <= 1.0F) && !(n <= 0.001F)) {
         invoke7(renderManager, f, g, h, i, j, k, l, m, n);
         invoke2(renderManager, j, k, l, m, n);
      }
   }

   public static float[] resolve(float f, float g, float h, float i, float j, float k) {
      return new float[]{f + j * h, g - k * i};
   }

   public static float[] resolve2(float f, float g, float h, float i, float j, float k) {
      float floatValue = measure3(j / 30.0F * 0.42F, -0.48F, 0.48F);
      float floatValue2 = measure3(0.875F - k / 90.0F * 0.38F, 0.08F, 0.98F);
      return resolve(f, g, h, i, floatValue, floatValue2);
   }

   public static float measure(float f, float g) {
      return f - 0.875F * g;
   }

   public static float measure2(float f, float g) {
      return f - 0.5625F * g;
   }

   public static boolean check(float f, float g) {
      if (g < 0.0F || g > 1.0F) {
         return false;
      } else if (check2(f, g, -0.25F, 0.25F, 0.75F, 1.0F)) {
         return true;
      } else if (check2(f, g, -0.25F, 0.25F, 0.375F, 0.75F)) {
         return true;
      } else if (check2(f, g, -0.5F, -0.25F, 0.375F, 0.75F)) {
         return true;
      } else if (check2(f, g, 0.25F, 0.5F, 0.375F, 0.75F)) {
         return true;
      } else {
         if (g <= 0.375F) {
            if (f >= -0.25F && f < 0.0F) {
               return true;
            }

            if (f >= 0.0F && f <= 0.25F) {
               return true;
            }
         }

         return false;
      }
   }

   private static boolean check2(float f, float g, float h, float i, float j, float k) {
      return f >= h && f <= i && g >= j && g <= k;
   }

   private static void invoke2(RenderManager renderManager2, float f, float g, float h, float i, float j) {
      int intValue = compute(86, 112, 162, Math.round(82.0F * j));
      int intValue2 = compute(58, 78, 118, Math.round(95.0F * j));
      int intValue3 = compute(155, 188, 238, Math.round(65.0F * j));
      invoke3(renderManager2, f, g, h, i, -0.25F, 0.0F, 0.0F, 0.375F, intValue2, intValue3);
      invoke3(renderManager2, f, g, h, i, 0.0F, 0.25F, 0.0F, 0.375F, intValue2, intValue3);
      invoke3(renderManager2, f, g, h, i, -0.25F, 0.25F, 0.375F, 0.75F, intValue, intValue3);
      invoke3(renderManager2, f, g, h, i, -0.5F, -0.25F, 0.375F, 0.75F, intValue, intValue3);
      invoke3(renderManager2, f, g, h, i, 0.25F, 0.5F, 0.375F, 0.75F, intValue, intValue3);
      invoke3(renderManager2, f, g, h, i, -0.25F, 0.25F, 0.75F, 1.0F, intValue, intValue3);
   }

   private static void invoke3(RenderManager renderManager3, float f, float g, float h, float i, float j, float k, float l, float m, int n, int o) {
      float floatValue3 = f + j * h;
      float floatValue4 = g - m * i;
      float floatValue5 = (k - j) * h;
      float floatValue6 = (m - l) * i;
      renderManager3.invoke28(floatValue3, floatValue4, floatValue5, floatValue6, 1.5F, o, 1.0F);
      renderManager3.invoke5(floatValue3, floatValue4, floatValue5, floatValue6, 1.5F, n);
   }

   public static void invoke4(
      RenderManager renderManager4, float f, float g, float h, float i, float j, float k, float l, float m, float n, float o, float p, float q
   ) {
      if (renderManager4 != null && !(q <= 0.001F)) {
         float[] floatValues = resolve2(f, g, h, i, 0.0F, 0.0F);
         float[] floatValues2 = resolve2(f, g, h, i, j, k);
         float floatValue7 = measure3(0.875F - m / 90.0F * 0.38F, 0.08F, 0.98F);
         float floatValue8 = measure3(0.875F - l / 90.0F * 0.38F, 0.08F, 0.98F);
         float floatValue9 = g - Math.max(floatValue7, floatValue8) * i;
         float floatValue10 = g - Math.min(floatValue7, floatValue8) * i;
         float floatValue11 = Math.max(3.0F, floatValue10 - floatValue9);
         renderManager4.invoke5(f + -0.25F * h, floatValue9, 0.5F * h, floatValue11, 1.5F, compute(95, 210, 255, Math.round(18.0F * q)));
         renderManager4.invoke28(f + -0.25F * h, floatValue9, 0.5F * h, floatValue11, 1.5F, compute(95, 210, 255, Math.round(55.0F * q)), 1.0F);
         invoke5(renderManager4, floatValues[0], floatValues[1], compute(95, 210, 255, Math.round(200.0F * q)), 5.0F);
         if (Math.abs(j) > 0.05F || Math.abs(k) > 0.05F) {
            invoke6(renderManager4, floatValues[0], floatValues[1], floatValues2[0], floatValues2[1], compute(95, 210, 255, Math.round(100.0F * q)), 1.2F);
            invoke5(renderManager4, floatValues2[0], floatValues2[1], compute(95, 210, 255, Math.round(220.0F * q)), 4.5F);
         }

         if (n > 0.001F) {
            float floatValue12 = measure3(j / 30.0F * 0.42F + n * 0.55F, -0.48F, 0.48F);
            float floatValue13 = measure3(0.875F - k / 90.0F * 0.38F, 0.08F, 0.98F);
            float[] floatValues3 = resolve(f, g, h, i, floatValue12, floatValue13);
            invoke6(renderManager4, floatValues2[0], floatValues2[1], floatValues3[0], floatValues3[1], compute(255, 190, 90, Math.round(160.0F * q)), 1.4F);
            invoke5(renderManager4, floatValues3[0], floatValues3[1], compute(255, 190, 90, Math.round(220.0F * q)), 4.0F);
         }

         invoke5(renderManager4, o, p, compute(255, 90, 110, Math.round(230.0F * q)), 6.0F);
         renderManager4.invoke5(o - 5.0F, p - 5.0F, 10.0F, 10.0F, 1.5F, compute(255, 90, 110, Math.round(30.0F * q)));
      }
   }

   private static void invoke5(RenderManager renderManager5, float f, float g, int i, float h) {
      renderManager5.invoke5(f - h, g - 0.75F, h * 2.0F, 1.5F, 0.0F, i);
      renderManager5.invoke5(f - 0.75F, g - h, 1.5F, h * 2.0F, 0.0F, i);
      renderManager5.invoke5(f - 1.5F, g - 1.5F, 3.0F, 3.0F, 0.0F, compute(255, 255, 255, 220));
   }

   private static void invoke6(RenderManager renderManager6, float f, float g, float h, float i, int j, float k) {
      float floatValue14 = h - f;
      float floatValue15 = i - g;
      float floatValue16 = (float)Math.hypot(floatValue14, floatValue15);
      if (!(floatValue16 < 1.0F)) {
         float floatValue17 = (f + h) * 0.5F;
         float floatValue18 = (g + i) * 0.5F;
         float floatValue19 = (float)Math.toDegrees(Math.atan2(floatValue15, floatValue14));
         renderManager6.invoke56(floatValue17, floatValue18);
         renderManager6.invoke54(floatValue19);
         renderManager6.invoke5(-floatValue16 * 0.5F, -k * 0.5F, floatValue16, k, 0.0F, j);
         renderManager6.invoke57();
         renderManager6.invoke57();
      }
   }

   private static void invoke7(RenderManager renderManager7, float f, float g, float h, float i, float j, float k, float l, float m, float n) {
      float floatValue20 = k - m;
      renderManager7.invoke37(
         j - l * 0.52F, floatValue20 - 4.0F, l * 1.04F, m + 10.0F, 1.5F, compute(70, 95, 140, Math.round(10.0F * n)), compute(10, 14, 22, Math.round(4.0F * n))
      );
      renderManager7.invoke41(j - l * 0.25F, k - 1.0F, l * 0.5F, 6.0F, 1.5F, 10.0F, 1.0F, compute(95, 160, 255, Math.round(20.0F * n)));
      renderManager7.invoke5(f + 10.0F, k, h - 20.0F, 1.0F, 0.0F, compute(120, 170, 255, Math.round(45.0F * n)));
   }

   private static int compute(int i, int j, int k, int l) {
      return (l & 0xFF) << 24 | (i & 0xFF) << 16 | (j & 0xFF) << 8 | k & 0xFF;
   }

   private static float measure3(float f, float g, float h) {
      return !Float.isFinite(f) ? g : Math.max(g, Math.min(h, f));
   }
}
