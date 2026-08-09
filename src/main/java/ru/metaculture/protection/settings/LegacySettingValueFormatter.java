package ru.metaculture.protection;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

final class LegacySettingValueFormatter {
   private static final float FLOAT_VALUE = 12.0F;
   private static final float FLOAT_VALUE_2 = 10.0F;
   private static final float FLOAT_VALUE_3 = 4.0F;
   private static final float FLOAT_VALUE_4 = 12.0F;
   private static final float FLOAT_VALUE_5 = 10.0F;
   private static final float FLOAT_VALUE_6 = 3.0F;
   private static final float FLOAT_VALUE_7 = 8.0F;
   private static final float FLOAT_VALUE_8 = 13.0F;
   private static final float FLOAT_VALUE_9 = 4.0F;
   private static final float FLOAT_VALUE_10 = 5.0F;
   private static final Map<String, Float> VALUES_BY_KEY = new HashMap<>();
   private static final Map<String, Float> VALUES_BY_KEY_2 = new HashMap<>();
   private static final Map<String, Float> VALUES_BY_KEY_3 = new HashMap<>();

   private LegacySettingValueFormatter() {
   }

   static float measure(RenderManager renderManager, Setting setting, float f) {
      if (setting instanceof SpacerSetting) {
         return ((SpacerSetting)setting).getFloatValue();
      } else if (setting instanceof BooleanSetting) {
         return 13.0F;
      } else if (setting instanceof ButtonSetting) {
         return 14.0F;
      } else if (setting instanceof NumberSetting) {
         return 22.0F;
      } else if (setting instanceof ModeSetting modeSetting) {
         float floatValue = measure5(renderManager, modeSetting.options.toArray(new String[0]), f);
         return 10.0F + floatValue * 15.0F;
      } else if (setting instanceof KeybindSetting) {
         return 12.0F;
      } else if (setting instanceof TextSetting) {
         return 14.0F;
      } else if (setting instanceof ColorSetting) {
         return 14.0F;
      } else if (setting instanceof GroupSetting groupSetting) {
         float floatValue2 = measure5(renderManager, groupSetting.options.stream().map(booleanSetting -> booleanSetting.name).toArray(String[]::new), f);
         return 10.0F + floatValue2 * 15.0F;
      } else {
         return 12.0F;
      }
   }

   static float measure2(RenderManager renderManager2, Iterable<Setting> iterable, float f) {
      float floatValue3 = 0.0F;

      for (Setting setting2 : iterable) {
         if (setting2 != null && !setting2.visibilityCondition.get()) {
            floatValue3 += measure(renderManager2, setting2, f) + 4.0F;
         }
      }

      return Math.max(0.0F, floatValue3 - 4.0F);
   }

   static float measure3(
      RenderManager renderManager3, Setting setting3, float f, float g, float h, int i, int j, float k, int l, int m, int n, int o, int p
   ) {
      if (k <= 0.01F) {
         return 0.0F;
      } else if (setting3 instanceof SpacerSetting) {
         return ((SpacerSetting)setting3).getFloatValue();
      } else if (setting3 instanceof BooleanSetting booleanSetting2) {
         float floatValue4 = f + h - 8.0F;
         float floatValue5 = g + 2.0F;
         renderManager3.invoke28(floatValue4, floatValue5, 8.0F, 8.0F, 2.5F, l, 0.4F);
         renderManager3.invoke5(floatValue4, floatValue5, 8.0F, 8.0F, 2.5F, p);
         if (booleanSetting2.isEnabled()) {
            renderManager3.invoke5(floatValue4 + 2.0F, floatValue5 + 2.0F, 4.0F, 4.0F, 2.0F, m);
         }

         invoke4(
            renderManager3,
            setting3.name,
            f,
            g + 2.0F + 6.5F,
            12.0F,
            o,
            f,
            g + 1.0F,
            Math.max(12.0F, floatValue4 - f - 4.0F),
            11.0F,
            RenderMath.check(i, j, f, g, h, 13.0F)
         );
         return 13.0F;
      } else if (setting3 instanceof NumberSetting numberSetting) {
         float floatValue6 = g + 12.0F;
         float floatValue7 = f + 4.0F;
         float floatValue8 = h - 8.0F;
         float floatValue9 = numberSetting.minimum;
         float floatValue10 = numberSetting.maximum;
         float floatValue11 = RenderMath.measure49(numberSetting.value, floatValue9, floatValue10);
         float floatValue12 = floatValue10 - floatValue9 > 1.0E-5F ? (floatValue11 - floatValue9) / (floatValue10 - floatValue9) : 0.0F;
         EasedAnimation easedAnimation = LegacyClickGuiState.resolve5(numberSetting);
         easedAnimation.check();
         easedAnimation.resolve3(floatValue12, 0.18F, Easings.EASE_OUT_QUART, true);
         float floatValue13 = easedAnimation.measure3();
         float floatValue14 = floatValue8 * floatValue13;
         String text = resolve(floatValue11, numberSetting.flag2);
         float floatValue15 = RenderManager.resolve7(FontRegistry.fontObject, text, 10.0F).floatValue;
         invoke4(
            renderManager3,
            setting3.name,
            f,
            g + 2.0F + 6.5F,
            12.0F,
            o,
            f,
            g + 1.0F,
            Math.max(12.0F, h - floatValue15 - 10.0F),
            11.0F,
            RenderMath.check(i, j, f, g, h, 22.0F)
         );
         renderManager3.invoke5(floatValue7, floatValue6, floatValue8, 4.0F, 2.0F, p);
         if (floatValue14 > 0.5F) {
            renderManager3.invoke5(floatValue7, floatValue6, floatValue14, 4.0F, 2.0F, m);
         }

         float floatValue16 = floatValue7 + floatValue14 - 2.5F;
         renderManager3.invoke5(floatValue16, floatValue6 - 0.5F, 5.0F, 5.0F, 2.0F, o);
         renderManager3.invoke69(FontRegistry.fontObject, f + h - floatValue15, g + 2.0F + 6.5F, 10.0F, text, n);
         return 22.0F;
      } else if (setting3 instanceof ButtonSetting buttonSetting) {
         String text2 = buttonSetting.getRun();
         float floatValue17 = RenderManager.resolve7(FontRegistry.fontObject, text2, 10.0F).floatValue;
         float floatValue18 = Math.max(32.0F, floatValue17 + 12.0F);
         float floatValue19 = f + h - floatValue18;
         invoke4(
            renderManager3,
            setting3.name,
            f,
            g + 2.0F + 6.5F,
            12.0F,
            o,
            f,
            g + 1.0F,
            Math.max(12.0F, floatValue19 - f - 4.0F),
            11.0F,
            RenderMath.check(i, j, f, g, h, 14.0F)
         );
         renderManager3.invoke28(floatValue19, g + 1.0F, floatValue18, 11.0F, 3.0F, l, 0.4F);
         renderManager3.invoke5(floatValue19, g + 1.0F, floatValue18, 11.0F, 3.0F, p);
         renderManager3.invoke69(FontRegistry.fontObject, floatValue19 + floatValue18 * 0.5F - floatValue17 * 0.5F, g + 2.0F + 6.5F, 10.0F, text2, n);
         return 14.0F;
      } else if (setting3 instanceof ModeSetting modeSetting2) {
         invoke4(renderManager3, setting3.name, f, g + 2.0F + 6.5F, 12.0F, o, f, g + 1.0F, h, 11.0F, RenderMath.check(i, j, f, g, h, 14.0F));
         float floatValue20 = f;
         float floatValue21 = g + 12.0F;

         for (String text3 : modeSetting2.options) {
            float floatValue22 = RenderManager.resolve7(FontRegistry.fontObject, text3, 10.0F).floatValue + 20.0F;
            if (floatValue20 + floatValue22 > f + h && floatValue20 > f) {
               floatValue20 = f;
               floatValue21 += 15.0F;
            }

            boolean flag = text3.equals(modeSetting2.value);
            String text4 = setting3.name + ":" + text3;
            VALUES_BY_KEY.putIfAbsent(text4, flag ? 1.0F : 0.0F);
            float floatValue23 = VALUES_BY_KEY.get(text4);
            floatValue23 = DeltaTimeLerp.measure2(floatValue23, flag ? 1.0F : 0.0F, 10.0F);
            VALUES_BY_KEY.put(text4, floatValue23);
            int intValue = ColorUtils.compute35(p, m, floatValue23 * 0.45F);
            int intValue2 = ColorUtils.compute35(n, o, floatValue23);
            renderManager3.invoke28(floatValue20, floatValue21, floatValue22, 12.0F, 3.0F, l, 0.4F);
            renderManager3.invoke5(floatValue20, floatValue21, floatValue22, 12.0F, 3.0F, intValue);
            renderManager3.invoke69(FontRegistry.fontObject, floatValue20 + 10.0F, floatValue21 + 2.0F + 5.5F, 10.0F, text3, intValue2);
            floatValue20 += floatValue22 + 3.0F;
         }

         float floatValue24 = measure5(renderManager3, modeSetting2.options.toArray(new String[0]), h);
         return 10.0F + floatValue24 * 15.0F;
      } else if (setting3 instanceof KeybindSetting keybindSetting) {
         String text5 = keybindSetting.waitingForBind ? "..." : KeyCodeUtils.resolve(keybindSetting.keyCode);
         float floatValue25 = RenderManager.resolve7(FontRegistry.fontObject, text5, 10.0F).floatValue;
         float floatValue26 = Math.max(22.0F, floatValue25 + 8.0F);
         float floatValue27 = f + h - floatValue26;
         invoke4(
            renderManager3,
            setting3.name,
            f,
            g + 2.0F + 6.5F,
            12.0F,
            o,
            f,
            g + 1.0F,
            Math.max(12.0F, floatValue27 - f - 4.0F),
            11.0F,
            RenderMath.check(i, j, f, g, h, 12.0F)
         );
         renderManager3.invoke28(floatValue27, g + 1.0F, floatValue26, 11.0F, 3.0F, l, 0.4F);
         renderManager3.invoke5(floatValue27, g + 1.0F, floatValue26, 11.0F, 3.0F, p);
         renderManager3.invoke69(FontRegistry.fontObject, floatValue27 + floatValue26 * 0.5F - floatValue25 * 0.5F, g + 2.0F + 6.5F, 10.0F, text5, keybindSetting.waitingForBind ? m : n);
         return 12.0F;
      } else if (setting3 instanceof TextSetting textSetting) {
         float floatValue28 = h * 0.35F;
         float floatValue29 = f + h - floatValue28;
         float floatValue30 = g + 1.0F;
         invoke4(
            renderManager3,
            setting3.name,
            f,
            g + 2.0F + 6.5F,
            12.0F,
            o,
            f,
            g + 1.0F,
            Math.max(12.0F, floatValue29 - f - 4.0F),
            11.0F,
            RenderMath.check(i, j, f, g, h, 14.0F)
         );
         renderManager3.invoke28(floatValue29, floatValue30, floatValue28, 11.0F, 3.0F, l, 0.4F);
         renderManager3.invoke5(floatValue29, floatValue30, floatValue28, 11.0F, 3.0F, p);
         String text6 = textSetting.value == null ? "" : textSetting.value;
         if (!text6.isEmpty()) {
            renderManager3.invoke69(FontRegistry.fontObject, floatValue29 + 4.0F, floatValue30 + 2.0F + 5.5F, 10.0F, text6, o);
         }

         return 14.0F;
      } else if (setting3 instanceof ColorSetting colorSetting) {
         float floatValue31 = 36.0F;
         float floatValue32 = 11.0F;
         float floatValue33 = f + h - floatValue31;
         float floatValue34 = g + 1.5F;
         int intValue3 = colorSetting.compute();
         invoke4(
            renderManager3,
            setting3.name,
            f,
            g + 2.0F + 6.5F,
            12.0F,
            o,
            f,
            g + 1.0F,
            Math.max(12.0F, floatValue33 - f - 6.0F),
            11.0F,
            RenderMath.check(i, j, f, g, h, 14.0F)
         );
         renderManager3.invoke28(floatValue33, floatValue34, floatValue31, floatValue32, 3.0F, l, 0.4F);
         renderManager3.invoke5(floatValue33, floatValue34, floatValue31, floatValue32, 3.0F, p);
         renderManager3.invoke5(floatValue33 + 2.0F, floatValue34 + 2.0F, floatValue31 - 4.0F, floatValue32 - 4.0F, 2.0F, RenderManager.RenderManagerState.compute24(intValue3, (int)(255.0F * k)));
         String text7 = String.format("#%02X%02X%02X", ColorUtils.compute5(intValue3), ColorUtils.compute6(intValue3), ColorUtils.compute7(intValue3));
         float floatValue35 = RenderManager.resolve7(FontRegistry.fontObject, text7, 10.0F).floatValue;
         renderManager3.invoke69(FontRegistry.fontObject, floatValue33 - floatValue35 - 4.0F, g + 2.0F + 6.5F, 10.0F, text7, n);
         return 14.0F;
      } else if (setting3 instanceof GroupSetting groupSetting2) {
         invoke4(renderManager3, setting3.name, f, g + 2.0F + 6.5F, 12.0F, o, f, g + 1.0F, h, 11.0F, RenderMath.check(i, j, f, g, h, 14.0F));
         float floatValue36 = f;
         float floatValue37 = g + 12.0F;

         for (BooleanSetting booleanSetting3 : groupSetting2.options) {
            float floatValue38 = RenderManager.resolve7(FontRegistry.fontObject, booleanSetting3.name, 10.0F).floatValue + 20.0F;
            if (floatValue36 + floatValue38 > f + h && floatValue36 > f) {
               floatValue36 = f;
               floatValue37 += 15.0F;
            }

            String text8 = setting3.name + ":" + booleanSetting3.name;
            VALUES_BY_KEY_2.putIfAbsent(text8, booleanSetting3.isEnabled() ? 1.0F : 0.0F);
            float floatValue39 = VALUES_BY_KEY_2.get(text8);
            floatValue39 = DeltaTimeLerp.measure2(floatValue39, booleanSetting3.isEnabled() ? 1.0F : 0.0F, 10.0F);
            VALUES_BY_KEY_2.put(text8, floatValue39);
            int intValue4 = ColorUtils.compute35(p, m, floatValue39 * 0.45F);
            int intValue5 = ColorUtils.compute35(n, o, floatValue39);
            renderManager3.invoke28(floatValue36, floatValue37, floatValue38, 12.0F, 3.0F, l, 0.4F);
            renderManager3.invoke5(floatValue36, floatValue37, floatValue38, 12.0F, 3.0F, intValue4);
            renderManager3.invoke69(FontRegistry.fontObject, floatValue36 + 10.0F, floatValue37 + 2.0F + 5.5F, 10.0F, booleanSetting3.name, intValue5);
            floatValue36 += floatValue38 + 3.0F;
         }

         float floatValue40 = measure5(renderManager3, groupSetting2.options.stream().map(booleanSetting4 -> booleanSetting4.name).toArray(String[]::new), h);
         return 10.0F + floatValue40 * 15.0F;
      } else {
         return 12.0F;
      }
   }

   static boolean check(RenderManager renderManager4, Setting setting4, float f, float g, float h, int i, int j, int k) {
      if (setting4 instanceof BooleanSetting) {
         float floatValue41 = f + h - 8.0F;
         float floatValue42 = g + 2.0F;
         if (k == 0 && RenderMath.check(i, j, floatValue41, floatValue42, 8.0F, 8.0F)) {
            BooleanSetting booleanSetting5 = (BooleanSetting)setting4;
            booleanSetting5.setValue(!booleanSetting5.isEnabled());
            invoke3();
            return true;
         }
      }

      if (setting4 instanceof NumberSetting) {
         float floatValue43 = g + 12.0F;
         float floatValue44 = f + 4.0F;
         float floatValue45 = h - 8.0F;
         if (k == 0 && RenderMath.check(i, j, floatValue44, floatValue43, floatValue45, 6.0F)) {
            NumberSetting numberSetting2 = (NumberSetting)setting4;
            LegacyClickGuiState.numberSetting = numberSetting2;
            LegacyClickGuiState.floatValue3 = floatValue44;
            LegacyClickGuiState.floatValue4 = floatValue43;
            LegacyClickGuiState.floatValue5 = floatValue45;
            invoke(numberSetting2, i);
            invoke3();
            return true;
         }
      }

      if (setting4 instanceof ButtonSetting buttonSetting2) {
         String text9 = buttonSetting2.getRun();
         float floatValue46 = RenderManager.resolve7(FontRegistry.fontObject, text9, 10.0F).floatValue;
         float floatValue47 = Math.max(32.0F, floatValue46 + 12.0F);
         float floatValue48 = f + h - floatValue47;
         if (k == 0 && RenderMath.check(i, j, floatValue48, g + 1.0F, floatValue47, 11.0F)) {
            buttonSetting2.invoke8();
            return true;
         }
      }

      if (setting4 instanceof ModeSetting modeSetting3) {
         float floatValue49 = f;
         float floatValue50 = g + 12.0F;

         for (String text10 : modeSetting3.options) {
            float floatValue51 = RenderManager.resolve7(FontRegistry.fontObject, text10, 10.0F).floatValue + 20.0F;
            if (floatValue49 + floatValue51 > f + h && floatValue49 > f) {
               floatValue49 = f;
               floatValue50 += 15.0F;
            }

            if (k == 0 && RenderMath.check(i, j, floatValue49, floatValue50, floatValue51, 12.0F)) {
               modeSetting3.value = text10;
               modeSetting3.selectedIndex = modeSetting3.options.indexOf(text10);
               invoke3();
               return true;
            }

            floatValue49 += floatValue51 + 3.0F;
         }
      }

      if (setting4 instanceof KeybindSetting keybindSetting2) {
         String text11 = keybindSetting2.waitingForBind ? "..." : KeyCodeUtils.resolve(keybindSetting2.keyCode);
         float floatValue52 = RenderManager.resolve7(FontRegistry.fontObject, text11, 10.0F).floatValue;
         float floatValue53 = Math.max(22.0F, floatValue52 + 8.0F);
         float floatValue54 = f + h - floatValue53;
         if (RenderMath.check(i, j, floatValue54, g + 1.0F, floatValue53, 11.0F) && k == 0) {
            if (LegacyClickGuiState.keybindSetting != keybindSetting2) {
               if (LegacyClickGuiState.keybindSetting != null) {
                  LegacyClickGuiState.keybindSetting.waitingForBind = false;
               }

               LegacyClickGuiState.keybindSetting = keybindSetting2;
               keybindSetting2.waitingForBind = true;
            }

            return true;
         }
      }

      if (setting4 instanceof TextSetting) {
         float floatValue55 = h * 0.55F;
         float floatValue56 = f + h - floatValue55;
         float floatValue57 = g + 1.0F;
         if (k == 0 && RenderMath.check(i, j, floatValue56, floatValue57, floatValue55, 11.0F)) {
            TextSetting textSetting2 = (TextSetting)setting4;
            if (LegacyClickGuiState.textSetting != textSetting2) {
               if (LegacyClickGuiState.textSetting != null) {
                  LegacyClickGuiState.textSetting.flag = false;
               }

               LegacyClickGuiState.textSetting = textSetting2;
               textSetting2.flag = true;
            }

            return true;
         }
      }

      if (setting4 instanceof ColorSetting colorSetting2) {
         float floatValue58 = 36.0F;
         float floatValue59 = 11.0F;
         float floatValue60 = f + h - floatValue58;
         float floatValue61 = g + 1.5F;
         if (k == 0 && RenderMath.check(i, j, floatValue60, floatValue61, floatValue58, floatValue59)) {
            invoke2(colorSetting2, floatValue60, floatValue61);
            return true;
         }
      }

      if (setting4 instanceof GroupSetting groupSetting3) {
         float floatValue62 = f;
         float floatValue63 = g + 12.0F;

         for (BooleanSetting booleanSetting6 : groupSetting3.options) {
            float floatValue64 = RenderManager.resolve7(FontRegistry.fontObject, booleanSetting6.name, 10.0F).floatValue + 20.0F;
            if (floatValue62 + floatValue64 > f + h && floatValue62 > f) {
               floatValue62 = f;
               floatValue63 += 15.0F;
            }

            if (k == 0 && RenderMath.check(i, j, floatValue62, floatValue63, floatValue64, 12.0F)) {
               booleanSetting6.setValue(!booleanSetting6.isEnabled());
               invoke3();
               return true;
            }

            floatValue62 += floatValue64 + 3.0F;
         }
      }

      return false;
   }

   static float measure4() {
      return 4.0F;
   }

   private static float measure5(RenderManager renderManager5, String[] strings, float f) {
      float floatValue65 = 1.0F;
      float floatValue66 = 0.0F;

      for (String text12 : strings) {
         float floatValue67 = RenderManager.resolve7(FontRegistry.fontObject, text12, 10.0F).floatValue + 20.0F;
         if (floatValue66 + floatValue67 > f && floatValue66 > 0.0F) {
            floatValue65++;
            floatValue66 = 0.0F;
         }

         floatValue66 += floatValue67 + 3.0F;
      }

      return floatValue65;
   }

   private static void invoke(NumberSetting numberSetting3, float f) {
      float floatValue68 = numberSetting3.minimum;
      float floatValue69 = numberSetting3.maximum;
      float floatValue70 = (f - LegacyClickGuiState.floatValue3) / LegacyClickGuiState.floatValue5;
      floatValue70 = RenderMath.measure49(floatValue70, 0.0F, 1.0F);
      float floatValue71 = floatValue68 + (floatValue69 - floatValue68) * floatValue70;
      float floatValue72 = numberSetting3.step;
      if (floatValue72 > 1.0E-5F) {
         floatValue71 = Math.round(floatValue71 / floatValue72) * floatValue72;
      }

      numberSetting3.value = RenderMath.measure49(floatValue71, floatValue68, floatValue69);
   }

   private static void invoke2(ColorSetting colorSetting3, float f, float g) {
      if (LegacyClickGuiState.colorSetting == colorSetting3) {
         LegacyClickGuiState.directionalAnimation5.invoke3(AnimationDirection.BACKWARDS);
         LegacyClickGuiState.colorSetting = null;
         LegacyClickGuiState.floatValue = 0.0F;
         LegacyClickGuiState.floatValue2 = 0.0F;
      } else {
         LegacyClickGuiState.colorSetting = colorSetting3;
         LegacyClickGuiState.directionalAnimation5.invoke3(AnimationDirection.FORWARDS);
         float floatValue73 = 160.0F;
         float floatValue74 = 119.0F;
         float floatValue75 = MinecraftAccessor.a_.getWindow().getScaledWidth();
         float floatValue76 = MinecraftAccessor.a_.getWindow().getScaledHeight();
         float floatValue77 = f + 40.0F;
         float floatValue78 = g - 4.0F;
         if (floatValue77 + floatValue73 > floatValue75 - 6.0F) {
            floatValue77 = f - floatValue73 - 6.0F;
         }

         floatValue77 = RenderMath.measure49(floatValue77, 6.0F, floatValue75 - floatValue73 - 6.0F);
         floatValue78 = RenderMath.measure49(floatValue78, 6.0F, floatValue76 - floatValue74 - 6.0F);
         LegacyClickGuiState.floatValue = floatValue77;
         LegacyClickGuiState.floatValue2 = floatValue78;
      }
   }

   private static String resolve(float f, boolean bl) {
      if (bl) {
         return String.format("%.1f%%", f);
      } else if (Math.abs(f - Math.round(f)) < 0.001F) {
         return String.format("%.0f", f);
      } else {
         DecimalFormat decimalFormat = new DecimalFormat("#.#");
         return decimalFormat.format(f);
      }
   }

   private static void invoke3() {
      if (WildClient.INSTANCE.configManager != null) {
         WildClient.INSTANCE.configManager.scheduleSave();
      }
   }

   private static void invoke4(RenderManager renderManager6, String string, float f, float g, float h, int i, float j, float k, float l, float m, boolean bl) {
      if (string != null && !string.isEmpty() && !(l <= 3.0F) && !(m <= 2.0F)) {
         float floatValue79 = RenderManager.resolve7(FontRegistry.fontObject, string, h).floatValue;
         if (floatValue79 <= l - 1.0F) {
            renderManager6.invoke69(FontRegistry.fontObject, f, g, h, string, i);
         } else {
            String text13 = string + "|" + h;
            VALUES_BY_KEY_3.putIfAbsent(text13, 0.0F);
            float floatValue80 = bl ? 1.0F : 0.0F;
            float floatValue81 = DeltaTimeLerp.measure2(VALUES_BY_KEY_3.get(text13), floatValue80, 12.0F);
            VALUES_BY_KEY_3.put(text13, floatValue81);
            float floatValue82 = floatValue79 - l;
            float floatValue83 = (float)((Math.sin(System.currentTimeMillis() * 0.0035) + 1.0) * 0.5);
            float floatValue84 = floatValue82 * floatValue83 * floatValue81;
            renderManager6.invoke24(j, k, l, m, 0.0F, 0.0F, 0.0F, 0.0F);
            renderManager6.invoke69(FontRegistry.fontObject, f - floatValue84, g, h, string, i);
            renderManager6.invoke25();
         }
      }
   }
}
