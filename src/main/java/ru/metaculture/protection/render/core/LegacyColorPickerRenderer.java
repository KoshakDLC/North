package ru.metaculture.protection;

import java.awt.Color;

public class LegacyColorPickerRenderer extends LegacyClickGuiState {
   public static final float FLOAT_VALUE = 160.0F;
   public static final float FLOAT_VALUE_2 = 119.0F;
   public static final float FLOAT_VALUE_3 = 6.0F;
   public static final float FLOAT_VALUE_4 = 5.0F;
   public static final float FLOAT_VALUE_5 = 132.0F;
   public static final float FLOAT_VALUE_6 = 62.0F;
   public static final float FLOAT_VALUE_7 = 10.0F;
   public static final float FLOAT_VALUE_8 = 7.0F;
   public static final float FLOAT_VALUE_9 = 10.0F;
   public static final float FLOAT_VALUE_10 = 10.0F;
   private static final int INT_VALUE = -1577754;
   private static final int INT_VALUE_2 = -3945532;

   public static void invoke(RenderManager renderManager, ColorSetting colorSetting, int i, int j, int k, int l, int m, float f) {
      if (colorSetting != null) {
         if (LegacyClickGuiState.floatValue != 0.0F || LegacyClickGuiState.floatValue2 != 0.0F) {
            invoke2(renderManager, colorSetting, LegacyClickGuiState.floatValue, LegacyClickGuiState.floatValue2, i, j, k, l, m, f);
         }
      }
   }

   private static void invoke2(RenderManager renderManager2, ColorSetting colorSetting2, float f, float g, int i, int j, int k, int l, int m, float h) {
      float floatValue = measure(f);
      if (LegacyClickGuiState.blyurNada.isEnabled()) {
         renderManager2.invoke43(floatValue, g, 160.0F, 119.0F, 6.0F);
      }

      renderManager2.invoke5(floatValue, g, 160.0F, 119.0F, 6.0F, l);
      renderManager2.invoke28(floatValue, g, 160.0F, 119.0F, 6.0F, k, 0.35F);
      float floatValue2 = measure2(floatValue);
      float floatValue3 = measure3(g);
      float floatValue4 = measure4(floatValue);
      float floatValue5 = measure5(g);
      float floatValue6 = measure6(g);
      float floatValue7 = measure7(g);
      float floatValue8 = colorSetting2.measure();
      float floatValue9 = colorSetting2.floatValue3;
      invoke3(renderManager2, floatValue2, floatValue3, 132.0F, 62.0F, floatValue8, h);
      renderManager2.invoke28(floatValue2, floatValue3, 132.0F, 62.0F, 4.0F, ColorUtils.compute31(k, h), 0.45F);
      float floatValue10 = floatValue2 + colorSetting2.saturation * 132.0F;
      float floatValue11 = floatValue3 + (1.0F - colorSetting2.brightness) * 62.0F;
      renderManager2.invoke5(floatValue10 - 3.0F, floatValue11 - 3.0F, 6.0F, 6.0F, 3.0F, ColorUtils.compute31(-1, h));
      renderManager2.invoke28(floatValue10 - 4.0F, floatValue11 - 4.0F, 8.0F, 8.0F, 4.0F, ColorUtils.compute31(-16777216, h * 0.7F), 0.4F);
      invoke4(renderManager2, floatValue4, floatValue3, 10.0F, 62.0F, h);
      renderManager2.invoke28(floatValue4, floatValue3, 10.0F, 62.0F, 4.0F, ColorUtils.compute31(k, h), 0.45F);
      float floatValue12 = floatValue3 + floatValue8 * 62.0F;
      renderManager2.invoke5(floatValue4 - 1.5F, floatValue12 - 2.0F, 13.0F, 4.0F, 2.0F, ColorUtils.compute31(-1, h));
      renderManager2.invoke28(floatValue4 - 1.5F, floatValue12 - 2.0F, 13.0F, 4.0F, 2.0F, ColorUtils.compute31(-16777216, h * 0.65F), 0.35F);
      invoke5(renderManager2, floatValue2, floatValue5, 148.0F, 7.0F, colorSetting2, h);
      float floatValue13 = floatValue2 + floatValue9 * 148.0F;
      renderManager2.invoke5(floatValue13 - 1.5F, floatValue5 - 1.0F, 3.0F, 9.0F, 1.5F, ColorUtils.compute31(-1, h));
      invoke6(renderManager2, floatValue2, floatValue6, 148.0F, 10.0F, colorSetting2, h);
      invoke7(renderManager2, floatValue2, floatValue7, 148.0F, 10.0F, colorSetting2, h);
   }

   public static float measure(float f) {
      return f + (30.0F - 30.0F * LegacyClickGuiState.directionalAnimation5.measure3());
   }

   public static float measure2(float f) {
      return f + 6.0F;
   }

   public static float measure3(float f) {
      return f + 6.0F;
   }

   public static float measure4(float f) {
      return f + 6.0F + 132.0F + 5.0F;
   }

   public static float measure5(float f) {
      return f + 6.0F + 62.0F + 5.0F;
   }

   public static float measure6(float f) {
      return measure5(f) + 7.0F + 5.0F;
   }

   public static float measure7(float f) {
      return measure6(f) + 10.0F + 5.0F;
   }

   private static void invoke3(RenderManager renderManager3, float f, float g, float h, float i, float j, float k) {
      Color color = Color.getHSBColor(j, 1.0F, 1.0F);
      renderManager3.invoke34(f, g, h, i, 4.0F, ColorUtils.compute31(-1, k), ColorUtils.compute31(color.getRGB(), k));
      renderManager3.invoke37(f, g, h, i, 4.0F, 0, ColorUtils.compute31(-16777216, k));
   }

   private static void invoke4(RenderManager renderManager4, float f, float g, float h, float i, float j) {
      byte byteValue = 6;
      float floatValue14 = i / byteValue;

      for (int intValue = 0; intValue < byteValue; intValue++) {
         float floatValue15 = g + intValue * floatValue14;
         int intValue2 = compute(intValue / 6.0F, 1.0F, 1.0F, j);
         int intValue3 = compute((intValue + 1.0F) / 6.0F, 1.0F, 1.0F, j);
         renderManager4.invoke36(f, floatValue15, h, floatValue14 + 0.5F, intValue2, intValue3);
      }
   }

   private static void invoke5(RenderManager renderManager5, float f, float g, float h, float i, ColorSetting colorSetting3, float j) {
      invoke9(renderManager5, f, g, h, i, 6.0F, j);
      int intValue4 = compute(colorSetting3.measure(), colorSetting3.saturation, colorSetting3.brightness, 0.0F);
      int intValue5 = compute(colorSetting3.measure(), colorSetting3.saturation, colorSetting3.brightness, j);
      renderManager5.invoke34(f, g, h, i, 3.0F, intValue4, intValue5);
      renderManager5.invoke28(f, g, h, i, 3.0F, ColorUtils.compute31(-1, j * 0.16F), 0.35F);
   }

   private static void invoke6(RenderManager renderManager6, float f, float g, float h, float i, ColorSetting colorSetting4, float j) {
      float[] floatValues = new float[]{0.0F, 0.5F, -0.083333336F, 0.083333336F, 0.33333334F};
      float floatValue16 = 3.0F;
      float floatValue17 = (h - floatValue16 * (floatValues.length - 1)) / floatValues.length;

      for (int intValue6 = 0; intValue6 < floatValues.length; intValue6++) {
         float floatValue18 = f + intValue6 * (floatValue17 + floatValue16);
         invoke9(renderManager6, floatValue18, g, floatValue17, i, 6.0F, j * 0.55F);
         renderManager6.invoke5(
            floatValue18,
            g,
            floatValue17,
            i,
            3.0F,
            compute(
               colorSetting4.measure() + floatValues[intValue6],
               Math.max(colorSetting4.saturation, 0.62F),
               Math.max(colorSetting4.brightness, 0.72F),
               colorSetting4.floatValue3 * j
            )
         );
         renderManager6.invoke28(floatValue18, g, floatValue17, i, 3.0F, ColorUtils.compute31(intValue6 == 0 ? -1 : -1996488705, j * 0.45F), 0.35F);
      }
   }

   private static void invoke7(RenderManager renderManager7, float f, float g, float h, float i, ColorSetting colorSetting5, float j) {
      byte byteValue2 = 9;
      float floatValue19 = 3.0F;
      float floatValue20 = (h - floatValue19 * (byteValue2 - 1)) / byteValue2;
      int intValue7 = colorSetting5.compute2();

      for (int intValue8 = 0; intValue8 < byteValue2; intValue8++) {
         float floatValue21 = f + intValue8 * (floatValue20 + floatValue19);
         boolean flag = intValue8 == 8;
         boolean flag2 = !flag && intValue8 < colorSetting5.items.size();
         invoke9(renderManager7, floatValue21, g, floatValue20, i, 6.0F, flag2 ? j * 0.6F : j * 0.25F);
         if (flag2) {
            renderManager7.invoke5(floatValue21, g, floatValue20, i, 3.0F, ColorUtils.compute31(colorSetting5.items.get(intValue8), j));
         } else {
            renderManager7.invoke5(floatValue21, g, floatValue20, i, 3.0F, ColorUtils.compute31(flag ? 1144649215 : 587202559, j));
         }

         if (flag) {
            invoke8(renderManager7, FontRegistry.fontObject8, "O", floatValue21, g, floatValue20, i, 8.0F, ColorUtils.compute31(-1, j));
         }

         boolean flag3 = flag2 && colorSetting5.items.get(intValue8) == intValue7;
         if (flag3) {
            invoke8(renderManager7, FontRegistry.fontObject8, "j", floatValue21, g, floatValue20, i, 7.0F, ColorUtils.compute31(-1, j * 0.9F));
         }

         renderManager7.invoke28(floatValue21, g, floatValue20, i, 3.0F, ColorUtils.compute31(flag3 ? -1 : 2013265919, j * (flag3 ? 0.9F : 0.34F)), flag3 ? 0.6F : 0.35F);
      }
   }

   private static void invoke8(RenderManager renderManager8, FontObject fontObject, String string, float f, float g, float h, float i, float j, int k) {
      float floatValue22 = RenderManager.resolve7(fontObject, string, j).floatValue;
      renderManager8.invoke69(fontObject, f + (h - floatValue22) * 0.5F, g + i * 0.5F + j * 0.32F, j, string, k);
   }

   private static void invoke9(RenderManager renderManager9, float f, float g, float h, float i, float j, float k) {
      boolean flag4 = false;
      float floatValue23 = g;

      while (floatValue23 < g + i) {
         boolean flag5 = flag4;
         float floatValue24 = Math.min(j, g + i - floatValue23);

         for (float floatValue25 = f; floatValue25 < f + h; floatValue25 += j) {
            float floatValue26 = Math.min(j, f + h - floatValue25);
            renderManager9.invoke4(floatValue25, floatValue23, floatValue26, floatValue24, ColorUtils.compute31(flag5 ? -1577754 : -3945532, k));
            flag5 = !flag5;
         }

         flag4 = !flag4;
         floatValue23 += j;
      }
   }

   private static int compute(float f, float g, float h, float i) {
      float floatValue27 = f - (float)Math.floor(f);
      int intValue9 = Color.HSBtoRGB(floatValue27, measure8(g), measure8(h));
      int intValue10 = Math.round(measure8(i) * 255.0F);
      return intValue10 << 24 | intValue9 & 16777215;
   }

   private static float measure8(float f) {
      return Float.isFinite(f) && !(f <= 0.0F) ? Math.min(f, 1.0F) : 0.0F;
   }
}
