package ru.metaculture.protection;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import lombok.Generated;

public final class ClickGuiRenderUtils {
   private static final Map<Long, ClickGuiRenderUtils.ClickGuiRenderUtilsState2> VALUES_BY_KEY = new HashMap<>();
   private static final float FLOAT_VALUE = 1.08F;
   private static float floatValue = 1.08F;
   private static long timestamp;

   public static void invoke(Metrics metrics) {
      float floatValue = metrics == null ? 1.0F : Math.max(1.0F, metrics.getFloatValue());
      ClickGuiRenderUtils.floatValue = floatValue * 1.08F;
   }

   public static int compute(ThemeContext themeContext) {
      return compute2(themeContext == null ? null : themeContext.getColorScheme());
   }

   public static int compute2(ColorScheme colorScheme) {
      return colorScheme == null ? -1 : colorScheme.getIntValue13();
   }

   public static int compute3(ThemeContext themeContext2) {
      return compute4(themeContext2 == null ? null : themeContext2.getColorScheme());
   }

   public static int compute4(ColorScheme colorScheme2) {
      return colorScheme2 == null ? -1711276033 : colorScheme2.getIntValue12();
   }

   public static int compute5(ColorScheme colorScheme3) {
      return colorScheme3 == null ? 1728053247 : colorScheme3.getIntValue11();
   }

   public static int compute6(ThemeContext themeContext3) {
      return compute7(themeContext3 == null ? null : themeContext3.getColorScheme());
   }

   public static int compute7(ColorScheme colorScheme4) {
      return colorScheme4 == null ? -1 : colorScheme4.getIntValue13();
   }

   public static int compute8(ColorScheme colorScheme5) {
      if (colorScheme5 != null && colorScheme5.isFlag()) {
         return -131586;
      } else {
         return colorScheme5 == null ? -1 : colorScheme5.getIntValue13();
      }
   }

   public static int compute9(ColorScheme colorScheme6) {
      if (colorScheme6 != null && colorScheme6.isFlag()) {
         return -723465;
      } else {
         return colorScheme6 == null ? -1711276033 : ColorScheme.compute7(colorScheme6.getIntValue13(), colorScheme6.getIntValue12(), 0.1F);
      }
   }

   public static int compute10(ColorScheme colorScheme7) {
      if (colorScheme7 == null) {
         return ColorScheme.compute5(255, 255, 255, 178);
      } else if (!colorScheme7.isFlag()) {
         if (check3(colorScheme7)) {
            int intValue = colorScheme7.getIntValue() >>> 24 & 0xFF;
            return ColorScheme.compute7(colorScheme7.getIntValue(), ColorScheme.compute6(colorScheme7.getIntValue15(), intValue), 0.13F);
         } else {
            return colorScheme7.getIntValue();
         }
      } else {
         return ColorScheme.compute7(ColorScheme.compute5(255, 255, 255, 178), ColorScheme.compute6(colorScheme7.getIntValue14(), 178), 0.055F);
      }
   }

   public static int compute11(ColorScheme colorScheme8) {
      if (colorScheme8 == null) {
         return ColorScheme.compute5(255, 255, 255, 196);
      } else if (!colorScheme8.isFlag()) {
         if (check3(colorScheme8)) {
            int intValue2 = colorScheme8.getIntValue2() >>> 24 & 0xFF;
            return ColorScheme.compute7(colorScheme8.getIntValue2(), ColorScheme.compute6(colorScheme8.getIntValue14(), intValue2), 0.1F);
         } else {
            return colorScheme8.getIntValue2();
         }
      } else {
         return ColorScheme.compute7(ColorScheme.compute5(255, 255, 255, 196), ColorScheme.compute6(colorScheme8.getIntValue14(), 196), 0.045F);
      }
   }

   public static int compute12(ColorScheme colorScheme9, float f) {
      if (colorScheme9 == null) {
         return ColorScheme.compute5(255, 255, 255, 174);
      } else {
         float floatValue2 = measure20(f);
         if (!colorScheme9.isFlag()) {
            return ColorScheme.compute7(colorScheme9.getIntValue3(), colorScheme9.getIntValue5(), floatValue2);
         } else {
            int intValue3 = ColorScheme.compute7(ColorScheme.compute5(255, 255, 255, 214), ColorScheme.compute5(255, 255, 255, 236), floatValue2);
            return ColorScheme.compute7(intValue3, ColorScheme.compute6(colorScheme9.getIntValue14(), intValue3 >>> 24 & 0xFF), 0.045F + 0.035F * floatValue2);
         }
      }
   }

   public static int compute13(ColorScheme colorScheme10, float f) {
      float floatValue3 = measure20(f);
      if (colorScheme10 == null || colorScheme10.isFlag()) {
         return ColorScheme.compute5(255, 255, 255, Math.round(153.0F * floatValue3));
      } else {
         return check3(colorScheme10)
            ? ColorScheme.compute6(colorScheme10.getIntValue14(), Math.round(52.0F * floatValue3))
            : ColorScheme.compute6(colorScheme10.getIntValue13(), Math.round(10.0F * floatValue3));
      }
   }

   public static int compute14(ColorScheme colorScheme11, float f) {
      float floatValue4 = measure20(f);
      return colorScheme11 != null && colorScheme11.isFlag()
         ? ColorScheme.compute5(0, 0, 0, Math.round(38.0F * floatValue4))
         : ColorScheme.compute5(0, 0, 0, Math.round(180.0F * floatValue4));
   }

   public static int compute15(ColorScheme colorScheme12, int i, float f) {
      float floatValue5 = measure20(f);
      return colorScheme12 != null && colorScheme12.isFlag()
         ? ColorScheme.compute5(0, 0, 0, Math.round(38.0F * floatValue5))
         : ColorScheme.compute6(i, Math.round(255.0F * floatValue5));
   }

   public static void invoke2(
      RenderManager renderManager, Metrics metrics2, ColorScheme colorScheme13, float f, float g, float h, float i, float j, float k, float l, float m
   ) {
      if (renderManager != null && metrics2 != null && colorScheme13 != null && !(m <= 0.0F)) {
         if (colorScheme13.isFlag()) {
            renderManager.invoke41(f, g, h, i, j, k, l, ColorScheme.compute5(0, 0, 0, Math.round(38.0F * measure20(m))));
         } else {
            renderManager.invoke41(f, g, h, i, j, k, l, ColorScheme.compute5(0, 0, 0, Math.round(180.0F * measure20(m))));
         }
      }
   }

   public static int compute16(GroupSetting groupSetting, float f, Metrics metrics3) {
      float floatValue6 = metrics3.measure(3.0F);
      float floatValue7 = 0.0F;
      int intValue4 = 1;

      for (int intValue5 = 0; intValue5 < groupSetting.options.size(); intValue5++) {
         float floatValue8 = measure2(metrics3, FontRegistry.fontObject, resolve(groupSetting.options.get(intValue5)), 8.0F);
         float floatValue9 = Math.max(metrics3.measure(18.0F), floatValue8 + metrics3.measure(8.0F));
         if (floatValue7 > 0.0F && floatValue7 + floatValue9 > f) {
            intValue4++;
            floatValue7 = 0.0F;
         }

         floatValue7 += floatValue9 + floatValue6;
      }

      return intValue4;
   }

   public static String resolve(BooleanSetting booleanSetting) {
      return booleanSetting.bindKey == -1
         ? booleanSetting.name
         : booleanSetting.name + " [" + (booleanSetting.holdToEnable ? "H " : "") + KeyboardKey.resolve(booleanSetting.bindKey) + "]";
   }

   public static void invoke3(
      RenderManager renderManager2, Metrics metrics4, FontObject fontObject, float f, float g, float h, String string, int i
   ) {
      float floatValue10 = measure5(metrics4, h);
      renderManager2.invoke69(fontObject, f, g + floatValue10, floatValue10 * 2.0F, string, i);
   }

   public static void invoke4(
      RenderManager renderManager3, Metrics metrics5, FontObject fontObject2, float f, float g, float h, float i, String string, int j
   ) {
      invoke3(renderManager3, metrics5, fontObject2, f, measure4(metrics5, fontObject2, g, h, i), i, string, j);
   }

   public static void invoke5(
      RenderManager renderManager4, Metrics metrics6, FontObject fontObject3, float f, float g, float h, float i, String string, int j, String string2
   ) {
      float floatValue11 = measure5(metrics6, i);
      renderManager4.invoke70(fontObject3, f, measure4(metrics6, fontObject3, g, h, i) + floatValue11, floatValue11 * 2.0F, string, j, string2);
   }

   public static float measure(FontObject fontObject4, String string, float f) {
      return RenderManager.resolve7(fontObject4, string == null ? "" : string, f * 2.0F * floatValue).floatValue;
   }

   public static float measure2(Metrics metrics7, FontObject fontObject5, String string, float f) {
      return RenderManager.resolve7(fontObject5, string == null ? "" : string, measure5(metrics7, f) * 2.0F).floatValue;
   }

   public static float measure3(Metrics metrics8, FontObject fontObject6, float f) {
      float floatValue12 = measure5(metrics8, f);
      return Math.max(floatValue12, RenderManager.resolve7(fontObject6, "Ag", floatValue12 * 2.0F).floatValue2);
   }

   public static float measure4(Metrics metrics9, FontObject fontObject7, float f, float g, float h) {
      return f + (g - measure3(metrics9, fontObject7, h)) * 0.5F;
   }

   private static float measure5(Metrics metrics10, float f) {
      float floatValue13 = metrics10 == null ? 1.0F : Math.max(1.0F, metrics10.getFloatValue());
      return f * floatValue13 * 1.08F;
   }

   public static boolean check(ClickGuiState clickGuiState, float f, float g, float h, float i) {
      return check2(clickGuiState.getFloatValue(), clickGuiState.getFloatValue2(), f, g, h, i);
   }

   public static boolean check2(float f, float g, float h, float i, float j, float k) {
      return f >= h && g >= i && f < h + j && g < i + k;
   }

   public static float measure6(float f) {
      return measure8(f, 0.0F, 0.03F, 0.012F);
   }

   public static float measure7(float f, float g) {
      return measure8(f, g, 0.03F, 0.012F);
   }

   public static float measure8(float f, float g, float h, float i) {
      float floatValue14 = measure9(f);
      float floatValue15 = Math.min(Math.max(0.0F, i), Math.abs(g) * 0.16F);
      return 1.0F + floatValue14 * h + floatValue15;
   }

   public static float measure9(float f) {
      float floatValue16 = SpringAnimation.measure8(f);
      return 1.0F - (float)Math.exp(-3.25F * floatValue16);
   }

   public static void invoke6(RenderManager renderManager5, float f, float g, float h, float i, float j, Runnable runnable) {
      invoke7(renderManager5, f, g, h, i, j, j, j, j, runnable);
   }

   public static void invoke7(RenderManager renderManager6, float f, float g, float h, float i, float j, float k, float l, float m, Runnable runnable) {
      if (renderManager6 != null && runnable != null && !(h <= 0.0F) && !(i <= 0.0F)) {
         renderManager6.invoke20();
         renderManager6.invoke24(f, g, h, i, j, k, l, m);
         boolean flag = false ;

         try {
            flag = true;
            runnable.run();
            flag = false;
         } finally {
            if (flag) {
               renderManager6.invoke20();
               renderManager6.invoke25();
            }
         }

         renderManager6.invoke20();
         renderManager6.invoke25();
      }
   }

   public static void invoke8(RenderManager renderManager7, ThemeContext themeContext4, float f, float g, float h, float i) {
      invoke9(renderManager7, themeContext4.getMetrics(), themeContext4.getColorScheme(), f, g, h, i);
   }

   public static void invoke9(RenderManager renderManager8, Metrics metrics11, ColorScheme colorScheme14, float f, float g, float h, float i) {
      renderManager8.invoke37(f, g, h, h, metrics11.measure(3.0F), colorScheme14.getIntValue14(), colorScheme14.getIntValue15());
      renderManager8.invoke37(f + h + i, g, h, h, metrics11.measure(3.0F), -24930, -32126);
      renderManager8.invoke37(f, g + h + i, h, h, metrics11.measure(3.0F), -24854, -32032);
      renderManager8.invoke37(f + h + i, g + h + i, h, h, metrics11.measure(3.0F), -6357069, -8192089);
   }

   public static void invoke10(RenderManager renderManager9, ThemeContext themeContext5, String string, float f, float g, float h) {
      Metrics metrics12 = themeContext5.getMetrics();
      ColorScheme colorScheme15 = themeContext5.getColorScheme();
      String text = string == null ? "" : string;
      float floatValue17 = Math.min(h * 0.55F, measure2(metrics12, FontRegistry.fontObject, text, 10.0F));
      float floatValue18 = Math.max(metrics12.measure(34.0F), floatValue17 + metrics12.measure(12.0F));
      float floatValue19 = f + h - floatValue18;
      renderManager9.invoke5(floatValue19, g, floatValue18, metrics12.measure(16.0F), metrics12.measure(4.0F), colorScheme15.getIntValue4());
      renderManager9.invoke28(floatValue19, g, floatValue18, metrics12.measure(16.0F), metrics12.measure(4.0F), colorScheme15.getIntValue6(), 0.5F);
      invoke4(
         renderManager9,
         metrics12,
         FontRegistry.fontObject,
         floatValue19 + metrics12.measure(6.0F),
         g,
         metrics12.measure(16.0F),
         10.0F,
         resolve3(FontRegistry.fontObject, text, 10.0F, floatValue18 - metrics12.measure(12.0F)),
         colorScheme15.getIntValue12()
      );
   }

   public static void invoke11(RenderManager renderManager10, ThemeContext themeContext6, float f, float g) {
      Metrics metrics13 = themeContext6.getMetrics();
      ColorScheme colorScheme16 = themeContext6.getColorScheme();
      renderManager10.invoke5(f, g, metrics13.measure(8.0F), metrics13.measure(8.0F), metrics13.measure(2.0F), compute8(colorScheme16));
      renderManager10.invoke5(
         f + metrics13.measure(2.5F),
         g + metrics13.measure(2.0F),
         metrics13.measure(1.0F),
         metrics13.measure(4.0F),
         metrics13.measure(1.0F),
         ColorScheme.compute5(21, 22, 26, 61)
      );
      renderManager10.invoke5(
         f + metrics13.measure(4.5F),
         g + metrics13.measure(2.0F),
         metrics13.measure(1.0F),
         metrics13.measure(4.0F),
         metrics13.measure(1.0F),
         ColorScheme.compute5(21, 22, 26, 61)
      );
   }

   public static List<String> resolve2(FontObject fontObject8, String string, float f, float g, int i) {
      ArrayList arrayList = new ArrayList();
      if (string != null && !string.isBlank()) {
         StringBuilder stringBuilder = new StringBuilder();

         for (String text2 : string.split("\\s+")) {
            String text3 = stringBuilder.isEmpty() ? text2 : stringBuilder + " " + text2;
            if (!(measure(fontObject8, text3, f) <= g) && !stringBuilder.isEmpty()) {
               arrayList.add(stringBuilder.toString());
               stringBuilder = new StringBuilder(text2);
               if (arrayList.size() == i) {
                  break;
               }
            } else {
               stringBuilder = new StringBuilder(text3);
            }
         }

         if (arrayList.size() < i && !stringBuilder.isEmpty()) {
            arrayList.add(stringBuilder.toString());
         }

         if (!arrayList.isEmpty()) {
            int intValue6 = arrayList.size() - 1;
            arrayList.set(intValue6, resolve3(fontObject8, (String)arrayList.get(intValue6), f, g));
         }

         return arrayList;
      } else {
         return arrayList;
      }
   }

   public static String resolve3(FontObject fontObject9, String string, float f, float g) {
      String text4 = string == null ? "" : string;
      if (measure(fontObject9, text4, f) <= g) {
         return text4;
      } else {
         String text5 = "...";

         while (!text4.isEmpty() && measure(fontObject9, text4 + text5, f) > g) {
            text4 = text4.substring(0, text4.length() - 1);
         }

         return text4 + text5;
      }
   }

   public static String resolve4(Metrics metrics14, FontObject fontObject10, String string, float f, float g) {
      String text6 = string == null ? "" : string;
      if (measure2(metrics14, fontObject10, text6, f) <= g) {
         return text6;
      } else {
         String text7 = "...";

         while (!text6.isEmpty() && measure2(metrics14, fontObject10, text6 + text7, f) > g) {
            text6 = text6.substring(0, text6.length() - 1);
         }

         return text6 + text7;
      }
   }

   public static String resolve5(float f) {
      return Math.abs(f - Math.round(f)) < 0.001F ? Integer.toString(Math.round(f)) : String.format(Locale.ROOT, "%.1f", f);
   }

   public static String resolve6(float f, float g) {
      int intValue7 = compute17(g);
      return intValue7 > 0 && !(Math.abs(f - Math.round(f)) < 0.001F) ? String.format(Locale.ROOT, "%." + intValue7 + "f", f) : Integer.toString(Math.round(f));
   }

   private static int compute17(float f) {
      if (Float.isFinite(f) && !(f <= 0.0F)) {
         try {
            int intValue8 = new BigDecimal(Float.toString(Math.abs(f))).stripTrailingZeros().scale();
            return Math.min(4, Math.max(0, intValue8));
         } catch (NumberFormatException numberFormatException) {
            return 1;
         }
      } else {
         return 1;
      }
   }

   public static float measure10(NumberSetting numberSetting) {
      float floatValue20 = Math.max(1.0E-4F, numberSetting.maximum - numberSetting.minimum);
      return Math.max(0.0F, Math.min(1.0F, (numberSetting.value - numberSetting.minimum) / floatValue20));
   }

   public static float measure11(ColorSetting colorSetting) {
      float floatValue21 = Math.max(1.0E-4F, colorSetting.maximumHue - colorSetting.minimumHue);
      return Math.max(0.0F, Math.min(1.0F, (colorSetting.hueValue - colorSetting.minimumHue) / floatValue21));
   }

   public static int compute18(float f, float g, float h, float i) {
      f %= 360.0F;
      if (f < 0.0F) {
         f += 360.0F;
      }

      float floatValue22 = (1.0F - Math.abs(2.0F * h - 1.0F)) * g;
      float floatValue23 = floatValue22 * (1.0F - Math.abs(f / 60.0F % 2.0F - 1.0F));
      float floatValue24 = h - floatValue22 * 0.5F;
      float floatValue25;
      float floatValue26;
      float floatValue27;
      if (f < 60.0F) {
         floatValue25 = floatValue22;
         floatValue26 = floatValue23;
         floatValue27 = 0.0F;
      } else if (f < 120.0F) {
         floatValue25 = floatValue23;
         floatValue26 = floatValue22;
         floatValue27 = 0.0F;
      } else if (f < 180.0F) {
         floatValue25 = 0.0F;
         floatValue26 = floatValue22;
         floatValue27 = floatValue23;
      } else if (f < 240.0F) {
         floatValue25 = 0.0F;
         floatValue26 = floatValue23;
         floatValue27 = floatValue22;
      } else if (f < 300.0F) {
         floatValue25 = floatValue23;
         floatValue26 = 0.0F;
         floatValue27 = floatValue22;
      } else {
         floatValue25 = floatValue22;
         floatValue26 = 0.0F;
         floatValue27 = floatValue23;
      }

      return ColorScheme.compute5(
         Math.round((floatValue25 + floatValue24) * 255.0F),
         Math.round((floatValue26 + floatValue24) * 255.0F),
         Math.round((floatValue27 + floatValue24) * 255.0F),
         Math.round(Math.max(0.0F, Math.min(1.0F, i)) * 255.0F)
      );
   }

   public static int compute19(float f, float g, float h) {
      return compute20(f, g, h, 1.0F);
   }

   public static int compute20(float f, float g, float h, float i) {
      f %= 1.0F;
      if (f < 0.0F) {
         f++;
      }

      g = measure20(g);
      h = measure20(h);
      int intValue9 = (int)(f * 6.0F);
      float floatValue28 = f * 6.0F - intValue9;
      float floatValue29 = h * (1.0F - g);
      float floatValue30 = h * (1.0F - floatValue28 * g);
      float floatValue31 = h * (1.0F - (1.0F - floatValue28) * g);
      float floatValue32;
      float floatValue33;
      float floatValue34;
      switch (intValue9 % 6) {
         case 0:
            floatValue32 = h;
            floatValue33 = floatValue31;
            floatValue34 = floatValue29;
            break;
         case 1:
            floatValue32 = floatValue30;
            floatValue33 = h;
            floatValue34 = floatValue29;
            break;
         case 2:
            floatValue32 = floatValue29;
            floatValue33 = h;
            floatValue34 = floatValue31;
            break;
         case 3:
            floatValue32 = floatValue29;
            floatValue33 = floatValue30;
            floatValue34 = h;
            break;
         case 4:
            floatValue32 = floatValue31;
            floatValue33 = floatValue29;
            floatValue34 = h;
            break;
         default:
            floatValue32 = h;
            floatValue33 = floatValue29;
            floatValue34 = floatValue30;
      }

      return ColorScheme.compute5(Math.round(floatValue32 * 255.0F), Math.round(floatValue33 * 255.0F), Math.round(floatValue34 * 255.0F), Math.round(measure20(i) * 255.0F));
   }

   public static void invoke12(RenderManager renderManager11, float f, float g, float h, float i, float j) {
      float floatValue35 = i / 6.0F;
      int[] intValues = new int[]{
         compute19(0.0F, 1.0F, 1.0F),
         compute19(0.16666667F, 1.0F, 1.0F),
         compute19(0.33333334F, 1.0F, 1.0F),
         compute19(0.5F, 1.0F, 1.0F),
         compute19(0.6666667F, 1.0F, 1.0F),
         compute19(0.8333333F, 1.0F, 1.0F),
         compute19(1.0F, 1.0F, 1.0F)
      };
      renderManager11.invoke20();
      renderManager11.invoke24(f, g, h, i, j, j, j, j);
      boolean flag2 = false ;

      try {
         flag2 = true;

         for (int intValue10 = 0; intValue10 < 6; intValue10++) {
            float floatValue36 = g + intValue10 * floatValue35;
            renderManager11.invoke37(f, floatValue36, h, floatValue35 + 1.0F, 0.0F, intValues[intValue10], intValues[intValue10 + 1]);
         }

         flag2 = false;
      } finally {
         if (flag2) {
            renderManager11.invoke20();
            renderManager11.invoke25();
         }
      }

      renderManager11.invoke20();
      renderManager11.invoke25();
   }

   public static float measure12(Metrics metrics15) {
      return Math.max(15.0F, Math.min(20.0F, metrics15.measure(18.0F)));
   }

   public static float measure13(Metrics metrics16) {
      return measure12(metrics16) + Math.max(12.0F, metrics16.measure(18.0F));
   }

   public static void invoke13(
      RenderManager renderManager12, Metrics metrics17, float f, float g, float h, float i, float j, float k, float l, float m, float n, Runnable runnable
   ) {
      invoke14(renderManager12, metrics17, null, f, g, h, i, j, k, l, m, n, runnable);
   }

   public static void invoke14(
      RenderManager renderManager13,
      Metrics metrics18,
      ColorScheme colorScheme17,
      float f,
      float g,
      float h,
      float i,
      float j,
      float k,
      float l,
      float m,
      float n,
      Runnable runnable
   ) {
      if (renderManager13 != null && runnable != null && !(h <= 1.0F) && !(i <= 1.0F)) {
         if (!MenuModule.check(MenuModule.RAZMYTIE_SKROLLA)) {
            renderManager13.invoke20();
            renderManager13.invoke24(f, g, h, i, j, k, l, m);

            try {
               runnable.run();
            } finally {
               renderManager13.invoke20();
               renderManager13.invoke25();
            }
         } else {
            long longValue = compute21(f, g, h, i);
            ClickGuiRenderUtils.ClickGuiRenderUtilsState clickGuiRenderUtilsState = resolve7(metrics18, longValue, n, 1.8F, 30.0F, 150.0F);
            float floatValue37 = clickGuiRenderUtilsState.floatValue2;
            float floatValue38 = clickGuiRenderUtilsState.floatValue4;
            if (floatValue37 < 0.006F) {
               renderManager13.invoke20();
               renderManager13.invoke24(f, g, h, i, j, k, l, m);

               try {
                  runnable.run();
               } finally {
                  renderManager13.invoke20();
                  renderManager13.invoke25();
               }
            } else {
               RenderManager.RenderManagerState2 renderManagerState2 = renderManager13.resolve2(f, g, h, i);
               if (renderManagerState2 == null) {
                  renderManager13.invoke20();
                  renderManager13.invoke24(f, g, h, i, j, k, l, m);
                  boolean flag3 = false ;

                  try {
                     flag3 = true;
                     runnable.run();
                     flag3 = false;
                  } finally {
                     if (flag3) {
                        renderManager13.invoke20();
                        renderManager13.invoke25();
                     }
                  }

                  renderManager13.invoke20();
                  renderManager13.invoke25();
               } else {
                  try {
                     runnable.run();
                  } finally {
                     renderManager13.invoke14(renderManagerState2);
                  }

                  float floatValue39 = metrics18.measure(48.0F);
                  float floatValue40 = Math.min(1.0F, floatValue37 / Math.max(floatValue39 * 0.08F, 1.0F));
                  float floatValue41 = measure12(metrics18) * floatValue40;
                  float floatValue42 = Math.min(1.0F, floatValue37 / Math.max(metrics18.measure(26.0F), 1.0F));
                  float floatValue43 = Math.min(metrics18.measure(15.0F), floatValue37 * 0.6F) * floatValue42;
                  renderManager13.invoke15(renderManagerState2, f, g, h, i, j, k, l, m, floatValue41, floatValue43, floatValue37, floatValue42, 0.0F, floatValue38);
               }
            }
         }
      }
   }

   public static void invoke15(
      RenderManager renderManager14, Metrics metrics19, ColorScheme colorScheme18, float f, float g, float h, float i, float j, float k
   ) {
      long longValue2 = compute21(f + 41.7F, g + 19.3F, h + 3.1F, i + 2.4F);
      ClickGuiRenderUtils.ClickGuiRenderUtilsState clickGuiRenderUtilsState2 = resolve7(metrics19, longValue2, k, 4.05F, 28.0F, 88.0F);
      float floatValue44 = Math.min(1.0F, clickGuiRenderUtilsState2.floatValue2 / Math.max(metrics19.measure(48.0F), 1.0F));
      float floatValue45 = 0.0F;
      if (!(floatValue44 <= 1.0E-4F) && !(h <= 1.0F) && !(i <= 1.0F)) {
         float floatValue46 = clickGuiRenderUtilsState2.floatValue4;
         float floatValue47 = measure18(Math.max(floatValue44, floatValue45 * 0.9F), 4.2F);
         float floatValue48 = metrics19.measure(1.1F) + metrics19.measure(5.1F) * floatValue47 + metrics19.measure(2.7F) * floatValue45;
         float floatValue49 = metrics19.measure(0.95F) + metrics19.measure(7.15F) * measure18(floatValue44, 4.18F);
         float floatValue50 = Math.min(i * 0.14F, metrics19.measure(6.5F) + metrics19.measure(10.5F) * floatValue47);
         float floatValue51 = Math.min(i * 0.1F, metrics19.measure(4.2F) + metrics19.measure(7.1F) * floatValue47);
         float floatValue52 = (float)Math.pow(Math.max(floatValue44 * 0.24F + floatValue47 * 0.76F, 0.0F), 0.82F);
         float floatValue53 = (float)Math.pow(Math.max(floatValue44 * 0.32F + floatValue47 * 0.68F, 0.0F), 1.02F);
         float floatValue54 = (float)Math.pow(Math.max(0.0F, floatValue45), 0.82F);
         int intValue11 = ColorScheme.compute6(
            ColorScheme.compute7(colorScheme18.getIntValue(), colorScheme18.getIntValue15(), 0.08F), Math.round(18.0F * floatValue52)
         );
         int intValue12 = ColorScheme.compute6(colorScheme18.getIntValue(), Math.round(10.0F * floatValue53));
         int intValue13 = ColorScheme.compute6(
            ColorScheme.compute7(colorScheme18.getIntValue(), colorScheme18.getIntValue14(), 0.11F), Math.round(14.0F * floatValue54)
         );
         int intValue14 = ColorScheme.compute6(
            ColorScheme.compute7(colorScheme18.getIntValue4(), colorScheme18.getIntValue15(), 0.24F), Math.round(16.0F * floatValue54)
         );
         renderManager14.invoke20();
         renderManager14.invoke51(f, g, h, i, floatValue48);
         renderManager14.invoke24(f, g, h, i, j, j, j, j);

         try {
            if (floatValue44 > 1.0E-4F) {
               for (int intValue15 = 0; intValue15 < 3; intValue15++) {
                  float floatValue55 = (intValue15 + 1.0F) / 3.0F;
                  float floatValue56 = floatValue46 * floatValue49 * floatValue55;
                  float floatValue57 = floatValue44 * (0.019F - intValue15 * 0.0048F);
                  renderManager14.invoke47(f, g + floatValue56, h, i, j, floatValue57);
               }
            }

            if (floatValue45 > 1.0E-4F) {
               float floatValue58 = Math.min(Math.min(h, i) * 0.18F, metrics19.measure(1.15F) + metrics19.measure(2.4F) * floatValue54);
               renderManager14.invoke47(f, g, h, i, j, 0.016F + floatValue45 * 0.016F);
               if (h - floatValue58 * 2.0F > 1.0F && i - floatValue58 * 1.35F > 1.0F) {
                  renderManager14.invoke47(
                     f + floatValue58, g + floatValue58 * 0.65F, h - floatValue58 * 2.0F, i - floatValue58 * 1.35F, Math.max(metrics19.measure(2.0F), j - floatValue58 * 0.4F), floatValue45 * 0.014F
                  );
               }

               renderManager14.invoke5(f, g, h, i, j, intValue13);
               renderManager14.invoke28(f, g, h, i, j, intValue14, 0.5F);
            }

            if (floatValue44 > 1.0E-4F) {
               if (floatValue46 > 0.0F) {
                  renderManager14.invoke37(f, g, h, floatValue50, j, intValue11, ColorScheme.compute5(0, 0, 0, 0));
                  renderManager14.invoke37(f, g + i - floatValue51, h, floatValue51, j, ColorScheme.compute5(0, 0, 0, 0), intValue12);
               } else {
                  renderManager14.invoke37(f, g + i - floatValue50, h, floatValue50, j, ColorScheme.compute5(0, 0, 0, 0), intValue11);
                  renderManager14.invoke37(f, g, h, floatValue51, j, intValue12, ColorScheme.compute5(0, 0, 0, 0));
               }
            }
         } finally {
            renderManager14.invoke20();
            renderManager14.invoke25();
         }
      }
   }

   public static void invoke16(
      RenderManager renderManager15, Metrics metrics20, ColorScheme colorScheme19, float f, float g, float h, float i, float j, float k, float l, float m
   ) {
      invoke17(renderManager15, metrics20, colorScheme19, f, g, h, i, j, k, l, m, 0L, -1.0F, -1.0F, null);
   }

   public static void invoke17(
      RenderManager renderManager16,
      Metrics metrics21,
      ColorScheme colorScheme20,
      float f,
      float g,
      float h,
      float i,
      float j,
      float k,
      float l,
      float m,
      long n,
      float o,
      float p,
      ClickGuiDragRegistry.ClickGuiDragRegistryListener clickGuiDragRegistryListener
   ) {
      if (renderManager16 != null && metrics21 != null && colorScheme20 != null && !(h <= 0.0F) && !(i <= 0.0F) && !(k <= 0.0F)) {
         float floatValue59 = 0.0F;
         if (n != 0L && clickGuiDragRegistryListener != null) {
            floatValue59 = ClickGuiDragRegistry.measure(n, f, g, h, i, j, k, Math.max(metrics21.measure(4.5F), 5.0F), o, p, clickGuiDragRegistryListener);
         }

         long longValue3 = compute21(f + 73.1F, g + 11.7F, h + 5.4F, i + 3.2F);
         ClickGuiRenderUtils.ClickGuiRenderUtilsState clickGuiRenderUtilsState3 = resolve7(metrics21, longValue3, l, 3.4F, 24.0F, 78.0F);
         float floatValue60 = clickGuiRenderUtilsState3.floatValue4;
         float floatValue61 = Math.min(1.0F, clickGuiRenderUtilsState3.floatValue2 / Math.max(metrics21.measure(48.0F), 1.0F));
         float floatValue62 = Math.max(floatValue61, Math.max(m * 0.72F, floatValue59 * 0.85F));
         float floatValue63 = 0.82F + 0.18F * measure19((float)System.currentTimeMillis() * 7.0E-4F + j * 0.015F);
         float floatValue64 = floatValue62 * floatValue63;
         float floatValue65 = floatValue59 * (0.84F + 0.16F * floatValue63);
         float floatValue66 = Math.max(h * 0.5F, metrics21.measure(1.6F));
         float floatValue67 = Math.min(metrics21.measure(1.15F), Math.max(metrics21.measure(0.85F), h * 0.26F));
         float floatValue68 = f + floatValue67;
         float floatValue69 = Math.max(metrics21.measure(0.8F), h - floatValue67 * 2.0F);
         float floatValue70 = g + metrics21.measure(1.2F);
         float floatValue71 = Math.max(metrics21.measure(8.0F), i - metrics21.measure(2.4F));
         float floatValue72 = metrics21.measure(0.9F) * floatValue59;
         float floatValue73 = f - floatValue72;
         float floatValue74 = h + floatValue72 * 2.0F;
         float floatValue75 = Math.max(floatValue74 * 0.5F, metrics21.measure(1.7F));
         renderManager16.invoke5(f, g, h, i, floatValue66, ColorScheme.compute7(colorScheme20.getIntValue3(), colorScheme20.getIntValue5(), 0.26F + floatValue62 * 0.22F));
         renderManager16.invoke5(
            floatValue68,
            floatValue70,
            floatValue69,
            floatValue71,
            floatValue69 * 0.5F,
            ColorScheme.compute7(
               ColorScheme.compute5(0, 0, 0, 24), ColorScheme.compute6(colorScheme20.getIntValue15(), 42), 0.1F + floatValue64 * 0.18F + floatValue59 * 0.08F
            )
         );
         renderManager16.invoke28(f, g, h, i, floatValue66, ColorScheme.compute6(colorScheme20.getIntValue13(), Math.round(14.0F * floatValue62 + 10.0F * floatValue65)), 0.5F);
         invoke18(renderManager16, metrics21, colorScheme20, f, j, h, k, floatValue61, floatValue60);
         int intValue16 = ColorScheme.compute7(
            colorScheme20.getIntValue8(), ColorScheme.compute6(colorScheme20.getIntValue14(), 140), 0.12F + floatValue64 * 0.28F + floatValue59 * 0.08F
         );
         int intValue17 = ColorScheme.compute7(
            colorScheme20.getIntValue7(), ColorScheme.compute6(colorScheme20.getIntValue15(), 154), 0.26F + floatValue64 * 0.46F + floatValue59 * 0.1F
         );
         renderManager16.invoke41(
            floatValue73 - metrics21.measure(0.25F),
            j + metrics21.measure(0.7F),
            floatValue74 + metrics21.measure(0.5F),
            Math.max(metrics21.measure(12.0F), k - metrics21.measure(1.4F)),
            floatValue75,
            metrics21.measure(3.0F + floatValue64 * 4.5F + floatValue59 * 2.1F),
            metrics21.measure(0.9F),
            colorScheme20.isFlag()
               ? ColorScheme.compute5(0, 0, 0, Math.round(18.0F * floatValue64 + 10.0F * floatValue65))
               : ColorScheme.compute6(colorScheme20.getIntValue14(), Math.round(20.0F * floatValue64 + 12.0F * floatValue65))
         );
         renderManager16.invoke37(floatValue73, j, floatValue74, k, floatValue75, intValue16, intValue17);
         float floatValue76 = Math.max(metrics21.measure(0.75F), floatValue74 * 0.22F);
         float floatValue77 = Math.max(metrics21.measure(0.8F), floatValue74 - floatValue76 * 2.0F);
         float floatValue78 = Math.min(
            Math.max(metrics21.measure(5.0F), k * (0.28F + floatValue64 * 0.08F)), Math.max(metrics21.measure(6.0F), k - metrics21.measure(2.2F))
         );
         renderManager16.invoke37(
            floatValue73 + floatValue76,
            j + metrics21.measure(1.15F),
            floatValue77,
            floatValue78,
            floatValue77 * 0.5F,
            ColorScheme.compute6(colorScheme20.getIntValue13(), Math.round(26.0F * floatValue64 + 14.0F * floatValue65)),
            ColorScheme.compute5(255, 255, 255, 0)
         );
         float floatValue79 = Math.max(metrics21.measure(1.0F), floatValue74 * 0.28F);
         float floatValue80 = Math.max(metrics21.measure(0.75F), floatValue74 - floatValue79 * 2.0F);
         float floatValue81 = j + metrics21.measure(2.0F);
         float floatValue82 = Math.max(metrics21.measure(7.0F), k - metrics21.measure(4.0F));
         renderManager16.invoke37(
            floatValue73 + floatValue79,
            floatValue81,
            floatValue80,
            floatValue82,
            floatValue80 * 0.5F,
            ColorScheme.compute6(colorScheme20.getIntValue13(), Math.round(18.0F * floatValue64 + 10.0F * floatValue65)),
            ColorScheme.compute6(
               ColorScheme.compute7(colorScheme20.getIntValue14(), colorScheme20.getIntValue15(), 0.55F), Math.round(48.0F * floatValue64 + 20.0F * floatValue65)
            )
         );
         renderManager16.invoke28(
            floatValue73,
            j,
            floatValue74,
            k,
            floatValue75,
            ColorScheme.compute6(
               ColorScheme.compute7(colorScheme20.getIntValue13(), colorScheme20.getIntValue14(), 0.14F + floatValue64 * 0.1F + floatValue59 * 0.06F),
               Math.round(38.0F * floatValue64 + 14.0F * floatValue65)
            ),
            0.5F
         );
      }
   }

   public static void invoke18(
      RenderManager renderManager17, Metrics metrics22, ColorScheme colorScheme21, float f, float g, float h, float i, float j, float k
   ) {
      if (!(j <= 1.0E-4F) && !(h <= 0.0F) && !(i <= 0.0F)) {
         float floatValue83 = Math.max(metrics22.measure(0.95F), h * 0.26F);
         float floatValue84 = f + floatValue83;
         float floatValue85 = Math.max(metrics22.measure(0.75F), h - floatValue83 * 2.0F);
         float floatValue86 = metrics22.measure(1.8F) + metrics22.measure(7.5F) * j;
         float floatValue87 = metrics22.measure(1.4F) + metrics22.measure(4.5F) * j;
         float floatValue88 = k > 0.0F ? g - floatValue86 : g + i - floatValue87;
         float floatValue89 = floatValue86 + floatValue87;
         float floatValue90 = (float)Math.pow(j, 0.82F);
         int intValue18 = ColorScheme.compute6(ColorScheme.compute7(colorScheme21.getIntValue15(), colorScheme21.getIntValue14(), 0.56F), Math.round(11.0F * floatValue90));
         int intValue19 = ColorScheme.compute6(ColorScheme.compute7(colorScheme21.getIntValue13(), colorScheme21.getIntValue14(), 0.16F), Math.round(18.0F * floatValue90));
         renderManager17.invoke21();

         try {
            if (k > 0.0F) {
               renderManager17.invoke37(floatValue84, floatValue88, floatValue85, floatValue89 * 0.72F, floatValue85 * 0.5F, intValue18, intValue19);
               renderManager17.invoke37(floatValue84, floatValue88 + floatValue89 * 0.72F, floatValue85, floatValue89 * 0.28F, floatValue85 * 0.5F, intValue19, ColorScheme.compute5(255, 255, 255, 0));
            } else {
               renderManager17.invoke37(floatValue84, floatValue88, floatValue85, floatValue89 * 0.28F, floatValue85 * 0.5F, ColorScheme.compute5(255, 255, 255, 0), intValue19);
               renderManager17.invoke37(floatValue84, floatValue88 + floatValue89 * 0.28F, floatValue85, floatValue89 * 0.72F, floatValue85 * 0.5F, intValue19, intValue18);
            }
         } finally {
            renderManager17.invoke20();
            renderManager17.invoke22();
         }
      }
   }

   private static float measure14(Metrics metrics23, float f, float g) {
      float floatValue91 = Math.min(1.0F, Math.abs(f) / Math.max(metrics23.measure(g), 0.5F));
      return floatValue91 <= 0.0F ? 0.0F : (float)Math.pow(floatValue91, 0.82F);
   }

   private static ClickGuiRenderUtils.ClickGuiRenderUtilsState resolve7(Metrics metrics24, long l, float f, float g, float h, float i) {
      long longValue4 = System.currentTimeMillis();
      ClickGuiRenderUtils.ClickGuiRenderUtilsState2 clickGuiRenderUtilsState22 = VALUES_BY_KEY.computeIfAbsent(l, long_ -> new ClickGuiRenderUtils.ClickGuiRenderUtilsState2());
      float floatValue92 = clickGuiRenderUtilsState22.timestamp == 0L ? 16.0F : Math.min(80.0F, Math.max(1.0F, (float)(longValue4 - clickGuiRenderUtilsState22.timestamp)));
      clickGuiRenderUtilsState22.timestamp = longValue4;
      float floatValue93 = metrics24.measure(48.0F);
      float floatValue94 = metrics24.measure(0.028F);
      float floatValue95 = Math.abs(f) * floatValue94;
      float floatValue96 = floatValue93 * (1.0F - (float)Math.exp(-floatValue95 / floatValue93));
      float floatValue97 = f < -0.001F ? -1.0F : (f > 0.001F ? 1.0F : 0.0F);
      if (floatValue97 != 0.0F) {
         clickGuiRenderUtilsState22.floatValue4 = floatValue97;
      }

      float floatValue98 = floatValue96 > clickGuiRenderUtilsState22.floatValue ? h : i;
      clickGuiRenderUtilsState22.floatValue = SpringAnimation.measure5(clickGuiRenderUtilsState22.floatValue, floatValue96, floatValue92, floatValue98);
      clickGuiRenderUtilsState22.floatValue5 = f;
      if (clickGuiRenderUtilsState22.floatValue <= 0.006F && Math.abs(f) <= 0.5F) {
         VALUES_BY_KEY.remove(l);
         invoke20(longValue4);
         return new ClickGuiRenderUtils.ClickGuiRenderUtilsState(0.0F, 0.0F, 0.0F, clickGuiRenderUtilsState22.floatValue4 == 0.0F ? 1.0F : clickGuiRenderUtilsState22.floatValue4);
      } else {
         invoke20(longValue4);
         return new ClickGuiRenderUtils.ClickGuiRenderUtilsState(0.0F, clickGuiRenderUtilsState22.floatValue, 0.0F, clickGuiRenderUtilsState22.floatValue4 == 0.0F ? 1.0F : clickGuiRenderUtilsState22.floatValue4);
      }
   }

   private static void invoke19(
      RenderManager renderManager18,
      Metrics metrics25,
      ColorScheme colorScheme22,
      float f,
      float g,
      float h,
      float i,
      float j,
      float k,
      float l,
      float m,
      float n,
      float o
   ) {
      int intValue20 = ColorScheme.compute7(colorScheme22.getIntValue14(), ColorScheme.compute5(255, 255, 255, 255), 0.24F);
      int intValue21 = ColorScheme.compute7(colorScheme22.getIntValue14(), colorScheme22.getIntValue15(), 0.52F);
      float floatValue99 = Math.max(0.5F, metrics25.measure(0.7F));
      float floatValue100 = f + floatValue99;
      float floatValue101 = g + floatValue99;
      float floatValue102 = Math.max(1.0F, h - floatValue99 * 2.0F);
      float floatValue103 = Math.max(1.0F, i - floatValue99 * 2.0F);
      float floatValue104 = metrics25.measure(1.0F);
      float floatValue105 = floatValue100 + floatValue104;
      float floatValue106 = floatValue101 + floatValue104;
      float floatValue107 = Math.max(1.0F, floatValue102 - floatValue104 * 2.0F);
      float floatValue108 = Math.max(1.0F, floatValue103 - floatValue104 * 2.0F);
      float floatValue109 = Math.max(0.0F, j - floatValue99);
      float floatValue110 = Math.max(0.0F, k - floatValue99);
      float floatValue111 = Math.max(0.0F, l - floatValue99);
      float floatValue112 = Math.max(0.0F, m - floatValue99);
      float floatValue113 = Math.max(0.0F, floatValue109 - floatValue104);
      float floatValue114 = Math.max(0.0F, floatValue110 - floatValue104);
      float floatValue115 = Math.max(0.0F, floatValue111 - floatValue104);
      float floatValue116 = Math.max(0.0F, floatValue112 - floatValue104);
      float floatValue117 = Math.max(metrics25.measure(1.1F), Math.min(metrics25.measure(2.0F), h - metrics25.measure(1.4F)));
      float floatValue118 = f + h - floatValue117 - metrics25.measure(0.7F);
      float floatValue119 = g + metrics25.measure(5.0F);
      float floatValue120 = Math.max(1.0F, i - metrics25.measure(10.0F));
      float floatValue121 = 0.72F + 0.28F * measure19((float)System.currentTimeMillis() * 7.8E-4F + g * 0.018F);
      float floatValue122 = (float)Math.pow(Math.max(0.0F, Math.min(1.0F, o)), 0.72F);
      float floatValue123 = (float)Math.pow(floatValue122, 1.18F);
      float floatValue124 = floatValue123 * (0.78F + 0.22F * floatValue121);
      float floatValue125 = floatValue124 * (0.82F + 0.18F * floatValue121);
      float floatValue126 = Math.min(floatValue120, Math.max(metrics25.measure(10.0F), floatValue120 * (0.18F + o * 0.08F)));
      float floatValue127 = Math.max(0.0F, floatValue120 - floatValue126);
      float floatValue128 = floatValue119 + floatValue127 * measure19((float)System.currentTimeMillis() * 9.2E-4F + n * 0.17F + f * 0.01F);
      renderManager18.invoke20();
      renderManager18.invoke21();

      try {
         renderManager18.invoke29(
            floatValue100,
            floatValue101,
            floatValue102,
            floatValue103,
            floatValue109,
            floatValue110,
            floatValue111,
            floatValue112,
            ColorScheme.compute6(intValue20, Math.round(42.0F * floatValue122)),
            Math.max(0.75F, metrics25.measure(0.7F))
         );
         renderManager18.invoke29(floatValue105, floatValue106, floatValue107, floatValue108, floatValue113, floatValue114, floatValue115, floatValue116, ColorScheme.compute6(intValue21, Math.round(18.0F * floatValue123)), 0.5F);
         renderManager18.invoke41(
            floatValue118,
            floatValue119,
            floatValue117,
            floatValue120,
            floatValue117 * 0.5F,
            metrics25.measure(4.2F + 6.8F * floatValue124),
            metrics25.measure(0.85F),
            ColorScheme.compute6(intValue21, Math.round(16.0F * floatValue124))
         );
         renderManager18.invoke37(
            floatValue118,
            floatValue119,
            floatValue117,
            floatValue120,
            floatValue117 * 0.5F,
            ColorScheme.compute6(intValue20, Math.round(34.0F * floatValue124)),
            ColorScheme.compute6(intValue21, Math.round(18.0F * floatValue123))
         );
         renderManager18.invoke37(
            floatValue118,
            floatValue128,
            floatValue117,
            floatValue126,
            floatValue117 * 0.5F,
            ColorScheme.compute6(colorScheme22.getIntValue13(), Math.round(22.0F * floatValue125)),
            ColorScheme.compute5(255, 255, 255, 0)
         );
      } finally {
         renderManager18.invoke20();
         renderManager18.invoke22();
      }
   }

   private static float measure15(float f) {
      float floatValue129 = Math.max(0.0F, Math.min(1.0F, (f - 0.035F) / 0.5F));
      return floatValue129 * floatValue129 * (3.0F - 2.0F * floatValue129);
   }

   private static float measure16(float f, float g, float h) {
      float floatValue130 = Math.max(0.0F, Math.min(1.0F, (h - f) / Math.max(1.0E-5F, g - f)));
      return floatValue130 * floatValue130 * (3.0F - 2.0F * floatValue130);
   }

   private static float measure17(float f, float g, float h) {
      float floatValue131 = Math.max(0.0F, f);
      if (floatValue131 <= 0.0F) {
         return 0.0F;
      } else {
         float floatValue132 = floatValue131 / Math.max(h, 1.0E-5F);
         floatValue132 /= 1.0F + floatValue132;
         float floatValue133 = floatValue131 / (floatValue131 + Math.max(g, 1.0E-5F) * 2.35F);
         return Math.max(0.0F, Math.min(1.0F, floatValue132 * (0.58F + 0.92F * floatValue133) * 1.42F));
      }
   }

   private static float measure18(float f, float g) {
      float floatValue134 = Math.max(0.0F, Math.min(1.0F, f));
      if (floatValue134 <= 0.0F) {
         return 0.0F;
      } else {
         double doubleValue = Math.expm1(Math.max(1.0E-4F, g));
         return doubleValue <= 1.0E-7 ? floatValue134 : (float)(Math.expm1(g * floatValue134) / doubleValue);
      }
   }

   public static float measure19(float f) {
      float floatValue135 = f - (float)Math.floor(f);
      return 0.5F - 0.5F * (float)Math.cos(floatValue135 * Math.PI * 2.0);
   }

   private static long compute21(float f, float g, float h, float i) {
      long longValue5 = 1469598103934665603L;
      longValue5 = (longValue5 ^ Math.round(f * 2.0F)) * 1099511628211L;
      longValue5 = (longValue5 ^ Math.round(g * 2.0F)) * 1099511628211L;
      longValue5 = (longValue5 ^ Math.round(h * 2.0F)) * 1099511628211L;
      return (longValue5 ^ Math.round(i * 2.0F)) * 1099511628211L;
   }

   private static float measure20(float f) {
      return Math.max(0.0F, Math.min(1.0F, f));
   }

   private static boolean check3(ColorScheme colorScheme23) {
      return colorScheme23 != null && (colorScheme23.getIntValue14() & 16777215) == 61695 && (colorScheme23.getIntValue15() & 16777215) == 17663;
   }

   private static void invoke20(long l) {
      if (l - timestamp >= 1800L) {
         timestamp = l;
         Iterator iterator = VALUES_BY_KEY.entrySet().iterator();

         while (iterator.hasNext()) {
            Entry entry = (Entry)iterator.next();
            ClickGuiRenderUtils.ClickGuiRenderUtilsState2 clickGuiRenderUtilsState23 = (ClickGuiRenderUtils.ClickGuiRenderUtilsState2)entry.getValue();
            if (clickGuiRenderUtilsState23 == null || l - clickGuiRenderUtilsState23.timestamp > 2600L) {
               iterator.remove();
            }
         }
      }
   }

   @Generated
   private ClickGuiRenderUtils() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }

   static final class ClickGuiRenderUtilsState {
      private final float floatValue;
      final float floatValue2;
      private final float floatValue3;
      final float floatValue4;

      ClickGuiRenderUtilsState(float f, float g, float h, float i) {
         this.floatValue = f;
         this.floatValue2 = g;
         this.floatValue3 = h;
         this.floatValue4 = i;
      }
   }

   static final class ClickGuiRenderUtilsState2 {
      float floatValue;
      private float floatValue2;
      private float floatValue3;
      float floatValue4 = 1.0F;
      float floatValue5;
      long timestamp;
   }
}
