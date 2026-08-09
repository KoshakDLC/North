package ru.metaculture.protection;

import java.awt.Color;
import java.util.HashMap;

public class LegacySettingLayout {
   public static Animation animation = new Animation();
   public static HashMap<String, Float> hashMap = new HashMap<>();
   public static HashMap<String, Float> hashMap2 = new HashMap<>();
   public static HashMap<String, Float> hashMap3 = new HashMap<>();

   public static float measure(RenderManager renderManager, Setting setting) {
      if (setting instanceof SpacerSetting) {
         return ((SpacerSetting)setting).getFloatValue();
      } else if (setting instanceof BooleanSetting) {
         return 10.0F;
      } else if (setting instanceof NumberSetting) {
         return 19.0F;
      } else if (setting instanceof ModeSetting modeSetting) {
         float floatValue = 105.47F;
         float floatValue2 = 2.0F;
         float floatValue3 = 10.075F;
         float floatValue4 = 3.0F;
         float floatValue5 = -2.0F;
         float floatValue6 = floatValue4;
         float floatValue7 = 0.0F;

         for (String text : modeSetting.options) {
            float floatValue8 = RenderManager.resolve7(FontRegistry.fontObject, text, 12.0F).floatValue + floatValue4 * 2.0F;
            if (floatValue6 + floatValue8 > floatValue && floatValue6 > floatValue4) {
               floatValue6 = floatValue4;
               floatValue7 += floatValue3 + floatValue5;
            }

            floatValue6 += floatValue8 + floatValue2;
         }

         return floatValue7 + floatValue3 + 12.0F;
      } else if (setting instanceof KeybindSetting) {
         return 13.0F;
      } else if (setting instanceof TextSetting) {
         return 15.0F;
      } else if (setting instanceof ColorSetting) {
         return 15.0F;
      } else if (setting instanceof GroupSetting groupSetting) {
         float floatValue9 = 0.0F;
         float floatValue10 = 10.0F;
         float floatValue11 = 0.0F;
         float floatValue12 = floatValue10;
         float floatValue13 = 3.0F;
         float floatValue14 = 10.0F;
         float floatValue15 = 4.0F;
         float floatValue16 = 105.47F;

         for (BooleanSetting booleanSetting : groupSetting.options) {
            float floatValue17 = RenderManager.resolve7(FontRegistry.fontObject, booleanSetting.name, 12.0F).floatValue;
            float floatValue18 = floatValue17 + floatValue15 * 2.0F;
            if (floatValue11 + floatValue18 > 0.0F + floatValue16) {
               floatValue11 = 0.0F;
               floatValue12 += floatValue14 + floatValue13;
            }

            floatValue11 += floatValue18 + floatValue13;
         }

         return floatValue12 - 0.0F + floatValue14;
      } else {
         return 15.0F;
      }
   }

   public static float measure2(
      RenderManager renderManager2, Setting setting2, float f, float g, float h, int i, int j, int k, int l, int m, int n, int o, float p
   ) {
      float floatValue19 = 0.0F;
      if (setting2 instanceof SpacerSetting) {
         floatValue19 = ((SpacerSetting)setting2).getFloatValue();
      } else if (setting2 instanceof BooleanSetting booleanSetting2) {
         boolean flag = booleanSetting2.isEnabled();
         float floatValue20 = 8.0F;
         float floatValue21 = f + h - floatValue20 - 3.0F;
         float floatValue22 = g + 2.0F;
         booleanSetting2.animation.check();
         booleanSetting2.animation.resolve2(flag ? 1.0 : 0.0, 0.15F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_15);
         renderManager2.invoke28(floatValue21, floatValue22, floatValue20, floatValue20, 3.0F, k, 0.1F);
         renderManager2.invoke5(floatValue21, floatValue22, floatValue20, floatValue20, 3.0F, m);
         renderManager2.invoke5(floatValue21 + 2.3F, floatValue22 + 2.2F, 3.42F, 3.425F, 3.0F, ColorUtils.compute35(0, l, booleanSetting2.animation.measure3()));
         renderManager2.invoke69(FontRegistry.fontObject, f, g + 3.0F + 5.0F, 13.0F, setting2.name, n);
         floatValue19 = 10.0F;
      } else if (setting2 instanceof NumberSetting numberSetting) {
         float floatValue23 = 4.0F;
         float floatValue24 = g + 10.0F;
         float floatValue25 = h - 2.5F;
         EasedAnimation easedAnimation = LegacyClickGuiState.resolve5(numberSetting);
         float floatValue26 = (numberSetting.value - numberSetting.minimum) / (numberSetting.maximum - numberSetting.minimum);
         double doubleValue = easedAnimation.getDoubleValue3();
         easedAnimation.check();
         easedAnimation.animateTo(floatValue26, 0.24F, Easings.EASE_OUT_QUART);
         float floatValue27 = (float)easedAnimation.getDoubleValue4();
         float floatValue28 = floatValue25 * floatValue27;
         renderManager2.invoke28(f, floatValue24 + 2.0F, floatValue25, floatValue23, 2.0F, k, 0.3F);
         renderManager2.invoke5(f, floatValue24 + 2.0F, floatValue25, floatValue23, 2.0F, m);
         renderManager2.invoke5(f + 1.0F, floatValue24 + 2.5F, floatValue28 - 2.0F, floatValue23 - 1.0F, 2.0F, l);
         renderManager2.invoke5(f + 1.0F + floatValue28 - 5.0F + (floatValue28 == 0.0F ? 5 : 2), floatValue24 + 2.2F, 5.0F, 3.88F, 2.0F, o);
         String text2 = numberSetting.flag2
            ? String.format("%.1f%%", numberSetting.value)
            : String.format("%.1f / %.1f", numberSetting.value, numberSetting.maximum);
         renderManager2.invoke69(FontRegistry.fontObject, f, g + 1.0F + 7.0F, 13.0F, setting2.name, n);
         renderManager2.invoke69(
            FontRegistry.fontObject, f + floatValue25 - RenderManager.resolve7(FontRegistry.fontObject, text2, 13.0F).floatValue - 2.0F, g + 7.0F, 13.0F, text2, l
         );
         floatValue19 = 19.0F;
      } else if (setting2 instanceof ModeSetting modeSetting2) {
         for (String text3 : modeSetting2.options) {
            hashMap.putIfAbsent(text3, 0.0F);
         }

         renderManager2.invoke69(FontRegistry.fontObject, f, g + 7.0F, 13.0F, setting2.name, n);
         float floatValue29 = 2.0F;
         float floatValue30 = 10.075F;
         float floatValue31 = 3.0F;
         float floatValue32 = -2.0F;
         float floatValue33 = floatValue31;
         float floatValue34 = 0.0F;

         for (String text4 : modeSetting2.options) {
            float floatValue35 = RenderManager.resolve7(FontRegistry.fontObject, text4, 12.0F).floatValue + floatValue31 * 2.0F;
            if (floatValue33 + floatValue35 > h && floatValue33 > floatValue31) {
               floatValue33 = floatValue31;
               floatValue34 += floatValue30 + floatValue32;
            }

            floatValue33 += floatValue35 + floatValue29;
         }

         float floatValue36 = g + 10.0F;
         float floatValue37 = floatValue34 + floatValue30;
         renderManager2.invoke28(f, floatValue36, h, floatValue37, 3.0F, k, 0.1F);
         renderManager2.invoke5(f, floatValue36, h, floatValue37, 3.0F, m);
         float floatValue38 = floatValue31;
         float floatValue39 = 1.5F;

         for (String text5 : modeSetting2.options) {
            boolean flag2 = text5.equals(modeSetting2.value);
            float floatValue40 = RenderManager.resolve7(FontRegistry.fontObject, text5, 12.0F).floatValue + floatValue31 * 2.0F;
            if (floatValue38 + floatValue40 > h && floatValue38 > floatValue31) {
               floatValue38 = floatValue31;
               floatValue39 += floatValue30 + floatValue32;
            }

            float floatValue41 = hashMap.get(text5);
            float floatValue42 = flag2 ? 1.0F : 0.0F;
            floatValue41 = DeltaTimeLerp.measure2(floatValue41, floatValue42, 10.0F);
            hashMap.put(text5, floatValue41);
            int intValue = ColorUtils.compute35(n, l, floatValue41);
            renderManager2.invoke69(FontRegistry.fontObject, f + floatValue38, floatValue36 + floatValue39 + 5.5F, 12.0F, text5, intValue);
            floatValue38 += floatValue40 + floatValue29;
         }

         floatValue19 = floatValue37 + 12.0F;
      } else if (setting2 instanceof KeybindSetting keybindSetting) {
         float floatValue43 = 10.075F;
         String text6 = setting2.name != null && !setting2.name.isEmpty() ? setting2.name : "KEY";
         String text7 = keybindSetting.waitingForBind ? "..." : KeyCodeUtils.resolve(keybindSetting.keyCode);
         float floatValue44 = RenderManager.resolve7(FontRegistry.fontObject, text7, 12.0F).floatValue;
         float floatValue45 = 16.055F;
         float floatValue46 = Math.max(floatValue45, floatValue44 + 8.0F);
         float floatValue47 = f + h - floatValue46 - 2.0F;
         if (floatValue47 < f) {
            floatValue47 = f;
            floatValue46 = h - 2.0F;
         }

         renderManager2.invoke69(FontRegistry.fontObject, f, g + 1.0F + 6.8F, 13.0F, text6, n);
         float floatValue48 = floatValue47 - 6.0F;
         float floatValue49 = floatValue46 + 2.0F;
         if (floatValue48 < f) {
            floatValue49 = floatValue48 + floatValue49 - f;
            floatValue48 = f;
         }

         renderManager2.invoke28(floatValue48, g, floatValue49, floatValue43, 3.0F, k, 0.1F);
         renderManager2.invoke5(floatValue48, g, floatValue49, floatValue43, 3.0F, m);
         renderManager2.invoke69(FontRegistry.fontObject, floatValue48 + floatValue49 / 2.0F - floatValue44 / 2.0F, g + 1.5F + 5.7F, 12.0F, text7, keybindSetting.waitingForBind ? l : n);
         floatValue19 = 13.0F;
      } else if (setting2 instanceof TextSetting textSetting) {
         float floatValue50 = 10.075F;
         float floatValue51 = 63.56F;
         float floatValue52 = f + 42.0F;
         float floatValue53 = floatValue52 + 5.0F;
         float floatValue54 = g + 1.5F;
         renderManager2.invoke69(FontRegistry.fontObject, f, g + 1.0F + 6.5F, 13.0F, setting2.name, n);
         renderManager2.invoke28(floatValue52, g, floatValue51, floatValue50, 3.0F, k, 0.1F);
         renderManager2.invoke5(floatValue52, g, floatValue51 - 10.0F, floatValue50, 3.0F, m);
         String text8 = textSetting.value;
         boolean flag3 = text8.isEmpty();
         float floatValue55 = floatValue53;
         if (flag3) {
            renderManager2.invoke69(FontRegistry.fontObject, floatValue53 - 2.0F, floatValue54 - 0.5F + 6.1F, 12.0F, "Enter text", n);
         } else {
            float floatValue56 = floatValue53;
            float floatValue57 = floatValue52 + floatValue51 - 5.0F;
            float floatValue58 = floatValue53;
            float floatValue59 = floatValue52 + floatValue51 - 5.0F;

            for (int intValue2 = 0; intValue2 < text8.length(); intValue2++) {
               char character = text8.charAt(intValue2);
               String text9 = String.valueOf(character);
               float floatValue60 = RenderManager.resolve7(FontRegistry.fontObject, text9, 12.0F).floatValue;
               if (floatValue56 + floatValue60 > floatValue57) {
                  floatValue55 = floatValue56;
                  break;
               }

               o = n;
               if (intValue2 >= 16) {
                  float floatValue61 = floatValue58 + RenderManager.resolve7(FontRegistry.fontObject, text8.substring(0, 16), 12.0F).floatValue;
                  float floatValue62 = Math.min(30.0F, floatValue59 - floatValue61);
                  if (floatValue62 > 0.0F) {
                     float floatValue63 = (floatValue56 - floatValue61) / floatValue62;
                     floatValue63 = RenderMath.measure49(floatValue63, 0.0F, 1.0F);
                     int intValue3 = n >> 24 & 0xFF;
                     intValue3 = (int)(intValue3 * (1.0F - floatValue63));
                     o = RenderManager.RenderManagerState.compute24(n, intValue3);
                  } else {
                     o = RenderManager.RenderManagerState.compute24(n, 0);
                  }
               }

               renderManager2.invoke69(FontRegistry.fontObject, floatValue56 - 2.0F, floatValue54 - 0.5F + 6.1F, 12.0F, text9, o);
               floatValue56 += floatValue60;
               floatValue55 = floatValue56;
            }
         }

         boolean flag4 = LegacyClickGuiState.textSetting == textSetting && textSetting.flag;
         if (flag4) {
            long longValue = System.currentTimeMillis();
            boolean flag5 = longValue / 500L % 2L == 0L;
            if (flag5) {
               renderManager2.invoke5(floatValue55 - 3.0F, floatValue54 - 0.5F, 1.0F, 8.0F, 0.5F, l);
            }
         }

         floatValue19 = 15.0F;
      } else if (setting2 instanceof ColorSetting colorSetting) {
         float floatValue64 = 12.0F;
         float floatValue65 = 40.0F;
         float floatValue66 = f + h - floatValue65 - 2.0F;
         renderManager2.invoke69(FontRegistry.fontObject, f, g + 1.0F + 7.0F, 13.0F, setting2.name, n);
         Color color = colorSetting.getColor();
         renderManager2.invoke28(floatValue66 - 10.0F, g, 46.48F, 10.075F, 3.0F, k, 0.1F);
         renderManager2.invoke5(floatValue66 - 10.0F, g, 46.48F, 10.075F, 3.0F, m);
         float floatValue67 = floatValue66 + 22.0F;
         float floatValue68 = g + 0.8F;
         float floatValue69 = 13.285F;
         float floatValue70 = 8.315F;
         int intValue4 = Math.round(colorSetting.floatValue3 * p * 255.0F) << 24 | color.getRed() << 16 | color.getGreen() << 8 | color.getBlue();
         renderManager2.invoke24(floatValue67, floatValue68, floatValue69, floatValue70, 0.0F, 3.0F, 3.0F, 0.0F);

         try {
            boolean flag6 = false;

            for (float floatValue71 = floatValue68; floatValue71 < floatValue68 + floatValue70; floatValue71 += 3.0F) {
               boolean flag7 = flag6;
               float floatValue72 = Math.min(3.0F, floatValue68 + floatValue70 - floatValue71);

               for (float floatValue73 = floatValue67; floatValue73 < floatValue67 + floatValue69; floatValue73 += 3.0F) {
                  float floatValue74 = Math.min(3.0F, floatValue67 + floatValue69 - floatValue73);
                  renderManager2.invoke4(floatValue73, floatValue71, floatValue74, floatValue72, ColorUtils.compute31(flag7 ? -12762550 : -14407632, p * 0.8F));
                  flag7 = !flag7;
               }

               flag6 = !flag6;
            }

            renderManager2.invoke6(floatValue67, floatValue68, floatValue69, floatValue70, 0.0F, 3.0F, 3.0F, 0.0F, intValue4);
            renderManager2.invoke38(floatValue67, floatValue68, floatValue69, floatValue70 * 0.55F, 0.0F, 3.0F, 0.0F, 0.0F, ColorUtils.compute31(-1, p * 0.28F), 0);
         } finally {
            renderManager2.invoke25();
         }

         renderManager2.invoke29(floatValue67, floatValue68, floatValue69, floatValue70, 0.0F, 3.0F, 3.0F, 0.0F, ColorUtils.compute31(-1, p * 0.4F), 0.5F);
         String text10 = String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());
         renderManager2.invoke69(
            FontRegistry.fontObject,
            floatValue66 + floatValue65 / 2.0F - RenderManager.resolve7(FontRegistry.fontObject, text10, 12.0F).floatValue / 2.0F - 14.0F,
            g + 1.5F + 5.7F,
            12.0F,
            text10,
            n
         );
         floatValue19 = 15.0F;
      } else if (setting2 instanceof GroupSetting groupSetting2) {
         renderManager2.invoke69(FontRegistry.fontObject, f, g + 7.0F, 13.0F, setting2.name, n);
         float floatValue75 = g + 10.0F;
         float floatValue76 = f;
         float floatValue77 = floatValue75;
         float floatValue78 = 3.0F;
         float floatValue79 = 10.0F;
         float floatValue80 = 4.0F;

         for (BooleanSetting booleanSetting3 : groupSetting2.options) {
            float floatValue81 = RenderManager.resolve7(FontRegistry.fontObject, booleanSetting3.name, 12.0F).floatValue;
            float floatValue82 = floatValue81 + floatValue80 * 2.0F;
            if (floatValue76 + floatValue82 > f + h) {
               floatValue76 = f;
               floatValue77 += floatValue79 + floatValue78;
            }

            renderManager2.invoke28(floatValue76, floatValue77, floatValue82, floatValue79, 3.0F, k, 0.1F);
            renderManager2.invoke5(floatValue76, floatValue77, floatValue82, floatValue79, 3.0F, m);
            String text11 = setting2.name + "_" + booleanSetting3.name;
            hashMap3.putIfAbsent(text11, booleanSetting3.isEnabled() ? 1.0F : 0.0F);
            float floatValue83 = hashMap3.get(text11);
            float floatValue84 = booleanSetting3.isEnabled() ? 1.0F : 0.0F;
            floatValue83 = DeltaTimeLerp.measure2(floatValue83, floatValue84, 10.0F);
            hashMap3.put(text11, floatValue83);
            o = ColorUtils.compute35(n, l, floatValue83);
            renderManager2.invoke69(FontRegistry.fontObject, floatValue76 + floatValue80, floatValue77 + 3.0F - 1.0F + 5.0F, 12.0F, booleanSetting3.name, o);
            floatValue76 += floatValue82 + floatValue78;
         }

         floatValue19 = floatValue77 - g + floatValue79;
      }

      return floatValue19 + 1.0F;
   }
}
