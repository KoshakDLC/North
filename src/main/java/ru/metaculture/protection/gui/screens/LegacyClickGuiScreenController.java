package ru.metaculture.protection;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.MinecraftClient;
import org.wild.module.api.Module;

public final class LegacyClickGuiScreenController {
   private static final float FLOAT_VALUE = 128.0F;
   private static final float FLOAT_VALUE_2 = 8.0F;
   private static final float FLOAT_VALUE_3 = 17.0F;
   private static final float FLOAT_VALUE_4 = 8.0F;
   private static final float FLOAT_VALUE_5 = 156.0F;
   private static final float FLOAT_VALUE_6 = 22.0F;
   private static final EnumMap<Category, LegacySettingListLayout> ENUM_MAP = new EnumMap<>(Category.class);
   private static final Map<Module, EasedAnimation> VALUES_BY_KEY = new HashMap<>();
   private static final Set<Module> VALUES = new HashSet<>();
   private static boolean flag = false;
   private static boolean flag2 = false;
   private static final EasedAnimation EASED_ANIMATION = new EasedAnimation();

   private LegacyClickGuiScreenController() {
   }

   static EasedAnimation resolve(Module module) {
      return VALUES_BY_KEY.computeIfAbsent(module, modulex -> new EasedAnimation());
   }

   static void invoke(Module module) {
      EasedAnimation easedAnimation = resolve(module);
      if (VALUES.contains(module)) {
         VALUES.remove(module);
         easedAnimation.animateTo(0.0, 0.18F, Easings.EASE_OUT_QUART);
      } else {
         VALUES.add(module);
         easedAnimation.animateTo(1.0, 0.18F, Easings.EASE_OUT_QUART);
      }
   }

   public static boolean check(RenderManager renderManager, double d, double e, int i) {
      float[] floatValues = LegacyMatrixScaleUtils.resolve((float)d, (float)e);
      int intValue = (int)floatValues[0];
      int intValue2 = (int)floatValues[1];
      LegacyClickGuiScreenController.LegacyClickGuiScreenControllerState legacyClickGuiScreenControllerState = LegacyClickGuiScreenController.LegacyClickGuiScreenControllerState.resolve();
      if (!legacyClickGuiScreenControllerState.flag) {
         return false;
      } else {
         invoke5(legacyClickGuiScreenControllerState.categorys);
         if (LegacyClickGuiState.colorSetting != null) {
            float floatValue = LegacyColorPickerRenderer.measure(LegacyClickGuiState.floatValue);
            if (RenderMath.check(intValue, intValue2, floatValue, LegacyClickGuiState.floatValue2, 160.0F, 119.0F)) {
               LegacyColorPickerController.check(intValue, intValue2, i);
               return true;
            }
         }

         if (check3(intValue, intValue2, i, legacyClickGuiScreenControllerState)) {
            return true;
         } else if (check6(intValue, intValue2, i, legacyClickGuiScreenControllerState)) {
            return true;
         } else if (i == 0 && check5(legacyClickGuiScreenControllerState, intValue, intValue2)) {
            return false;
         } else {
            for (LegacySettingListLayout legacySettingListLayout : ENUM_MAP.values()) {
               if (legacySettingListLayout.check(renderManager, intValue, intValue2, i)) {
                  return true;
               }
            }

            if (LegacyClickGuiState.keybindSetting != null && i >= 0 && i <= 8) {
               int intValue3 = -100 - i;
               LegacyClickGuiState.keybindSetting.keyCode = intValue3;
               LegacyClickGuiState.keybindSetting.waitingForBind = false;
               LegacyClickGuiState.keybindSetting = null;
               return true;
            } else if (LegacyClickGuiState.module != null && i >= 0 && i <= 8) {
               int intValue4 = -100 - i;
               LegacyClickGuiState.module.bindKey = intValue4;
               LegacyClickGuiState.module.expanded = false;
               LegacyClickGuiState.module = null;
               return true;
            } else {
               if (LegacyClickGuiState.textSetting != null && i == 0) {
                  LegacyClickGuiState.textSetting.flag = false;
                  LegacyClickGuiState.textSetting = null;
               }

               if (LegacyClickGuiState.colorSetting != null && i == 0) {
                  LegacyClickGuiState.directionalAnimation5.invoke3(AnimationDirection.BACKWARDS);
                  LegacyClickGuiState.colorSetting = null;
                  LegacyClickGuiState.floatValue = 0.0F;
                  LegacyClickGuiState.floatValue2 = 0.0F;
               }

               return false;
            }
         }
      }
   }

   public static boolean check2(double d, double e, double f) {
      float[] floatValues2 = LegacyMatrixScaleUtils.resolve((float)d, (float)e);
      float floatValue2 = floatValues2[0];
      float floatValue3 = floatValues2[1];
      invoke5(Category.values());

      for (LegacySettingListLayout legacySettingListLayout2 : ENUM_MAP.values()) {
         if (legacySettingListLayout2.check2(floatValue2, floatValue3, f)) {
            return true;
         }
      }

      return false;
   }

   public static void invoke2(RenderManager renderManager2, int i, int j, float f) {
      if (WildClient.INSTANCE != null && WildClient.INSTANCE.themeManager != null && WildClient.INSTANCE.themeManager.customThemeColor != null) {
         int intValue5 = WildClient.INSTANCE.themeManager.customThemeColor.compute();
         Color color = new Color(intValue5);
         float[] floatValues3 = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
         Color color2 = Color.getHSBColor(floatValues3[0], floatValues3[1] * 0.15F, 0.3F);
         Color color3 = Color.getHSBColor(floatValues3[0], floatValues3[1] * 0.3F, 0.17F);
         Color color4 = Color.getHSBColor(floatValues3[0], floatValues3[1] * 0.3F, 1.0F);
         Color color5 = Color.getHSBColor(floatValues3[0], floatValues3[1] * 0.2F, 1.0F);
         Theme.CUSTOM.invoke(color, color2, color3, color4, Color.WHITE, color5);
         if (WildClient.INSTANCE.themeManager.getTheme() == Theme.CUSTOM) {
            LegacyClickGuiState.theme2 = Theme.CUSTOM;
            LegacyClickGuiState.theme = Theme.CUSTOM;
         }
      }

      LegacyClickGuiScreenController.LegacyClickGuiScreenControllerState legacyClickGuiScreenControllerState2 = LegacyClickGuiScreenController.LegacyClickGuiScreenControllerState.resolve();
      if (legacyClickGuiScreenControllerState2.flag) {
         LegacyClickGuiState.easedAnimation5.check();
         LegacyClickGuiState.easedAnimation5.resolve3(1.0, 0.2F, Easings.EASING_FUNCTION_26, false);

         for (int intValue6 = 0; intValue6 < legacyClickGuiScreenControllerState2.categorys.length; intValue6++) {
            Category category = legacyClickGuiScreenControllerState2.categorys[intValue6];
            LegacySettingListLayout legacySettingListLayout3 = ENUM_MAP.computeIfAbsent(category, LegacySettingListLayout::new);
            legacySettingListLayout3.invoke(
               legacyClickGuiScreenControllerState2.floatValue7 + intValue6 * (legacyClickGuiScreenControllerState2.floatValue3 + legacyClickGuiScreenControllerState2.floatValue5), legacyClickGuiScreenControllerState2.floatValue8, legacyClickGuiScreenControllerState2.floatValue3, legacyClickGuiScreenControllerState2.floatValue4
            );
            legacySettingListLayout3.invoke2(renderManager2, i, j, f);
         }

         if (LegacyClickGuiState.colorSetting instanceof ColorSetting) {
            int intValue7 = RenderManager.RenderManagerState.compute24(RenderManager.RenderManagerState.compute5(1, 1), (int)(100.0F * f));
            int intValue8 = RenderManager.RenderManagerState.compute24(RenderManager.RenderManagerState.compute3(1, 1), (int)(180.0F * f));
            int intValue9 = RenderManager.RenderManagerState.compute24(RenderManager.RenderManagerState.compute7(1, 1), (int)(200.0F * f));
            LegacyColorPickerRenderer.invoke(renderManager2, LegacyClickGuiState.colorSetting, i, j, intValue7, intValue8, intValue9, f * LegacyClickGuiState.directionalAnimation5.measure3());
         }

         invoke4(renderManager2, legacyClickGuiScreenControllerState2, f, i, j);
         invoke3(renderManager2, i, j, f, legacyClickGuiScreenControllerState2);
      }
   }

   private static void invoke3(RenderManager renderManager3, int i, int j, float f, LegacyClickGuiScreenController.LegacyClickGuiScreenControllerState legacyClickGuiScreenControllerState3) {
      EASED_ANIMATION.check();
      EASED_ANIMATION.resolve3(flag2 ? 1.0 : 0.0, 0.2F, Easings.EASE_OUT_QUART, false);
      float floatValue4 = EASED_ANIMATION.measure3();
      Theme theme = WildClient.INSTANCE != null && WildClient.INSTANCE.themeManager != null ? WildClient.INSTANCE.themeManager.getTheme() : Theme.WILD;
      float floatValue5 = 110.0F;
      float floatValue6 = 22.0F;
      float floatValue7 = legacyClickGuiScreenControllerState3.floatValue - floatValue5 - 10.0F;
      float floatValue8 = legacyClickGuiScreenControllerState3.floatValue2 - floatValue6 - 10.0F;
      boolean glass = LegacyClickGuiState.blyurNada.isEnabled();
      int intValue10 = RenderManager.RenderManagerState.compute24(RenderManager.RenderManagerState.compute5(1, 1), (int)(18.0F * f));
      int intValue11 = RenderManager.RenderManagerState.compute24(RenderManager.RenderManagerState.compute3(1, 1), (int)((glass ? 145.0F : 210.0F) * f));
      int intValue12 = RenderManager.RenderManagerState.compute24(RenderManager.RenderManagerState.compute7(1, 1), (int)(230.0F * f));
      int hairline = RenderManager.RenderManagerState.compute37(255, 255, 255, (int)(28.0F * f));
      if (glass) {
         renderManager3.invoke44(floatValue7, floatValue8, floatValue5, floatValue6, 8.0F, f);
      }

      renderManager3.invoke28(floatValue7, floatValue8, floatValue5, floatValue6, 8.0F, intValue10, 0.8F);
      renderManager3.invoke5(floatValue7, floatValue8, floatValue5, floatValue6, 8.0F, intValue11);
      renderManager3.invoke28(floatValue7, floatValue8, floatValue5, floatValue6, 8.0F, hairline, 1.0F);
      renderManager3.invoke5(floatValue7 + 6.0F, floatValue8 + 7.0F, 8.0F, 8.0F, 4.0F, RenderManager.RenderManagerState.compute24(theme.getColor().getRGB(), (int)(255.0F * f)));
      renderManager3.invoke69(FontRegistry.fontObject, floatValue7 + 20.0F, floatValue8 + 13.0F, 12.0F, theme.text, intValue12);
      if (floatValue4 > 0.01F) {
         Theme[] themes = Theme.values();
         float floatValue9 = 18.0F;
         float floatValue10 = themes.length * floatValue9 + 8.0F;
         float floatValue11 = floatValue7;
         float floatValue12 = floatValue8 - 6.0F - floatValue10 * floatValue4;
         renderManager3.invoke24(floatValue7, floatValue8 - 6.0F - floatValue10, floatValue5, floatValue10, 0.0F, 0.0F, 6.0F, 6.0F);
         if (glass) {
            renderManager3.invoke44(floatValue7, floatValue12, floatValue5, floatValue10, 8.0F, f * floatValue4);
         }

         renderManager3.invoke28(floatValue7, floatValue12, floatValue5, floatValue10, 8.0F, intValue10, 0.8F);
         renderManager3.invoke5(floatValue7, floatValue12, floatValue5, floatValue10, 8.0F, intValue11);
         renderManager3.invoke28(floatValue7, floatValue12, floatValue5, floatValue10, 8.0F, RenderManager.RenderManagerState.compute37(255, 255, 255, (int)(28.0F * f * floatValue4)), 1.0F);
         float floatValue13 = floatValue12 + 4.0F;

         for (Theme theme2 : themes) {
            boolean flag2 = RenderMath.check(i, j, floatValue11, floatValue13, floatValue5, floatValue9);
            int intValue13 = flag2 ? RenderManager.RenderManagerState.compute24(RenderManager.RenderManagerState.compute6(1, 1), (int)(42.0F * f * floatValue4)) : 0;
            if (flag2 || theme2 == theme) {
               renderManager3.invoke5(
                  floatValue11 + 4.0F,
                  floatValue13,
                  floatValue5 - 8.0F,
                  floatValue9,
                  5.0F,
                  intValue13 != 0 ? intValue13 : RenderManager.RenderManagerState.compute24(RenderManager.RenderManagerState.compute6(1, 1), (int)(22.0F * f * floatValue4))
               );
            }

            renderManager3.invoke5(
               floatValue11 + 8.0F, floatValue13 + 5.0F, 8.0F, 8.0F, 4.0F, RenderManager.RenderManagerState.compute24(theme2.getColor().getRGB(), (int)(255.0F * f * floatValue4))
            );
            renderManager3.invoke69(
               FontRegistry.fontObject, floatValue11 + 22.0F, floatValue13 + 10.0F, 11.0F, theme2.text, RenderManager.RenderManagerState.compute24(intValue12, (int)(255.0F * f * floatValue4))
            );
            if (theme2 == Theme.CUSTOM) {
               renderManager3.invoke69(
                  FontRegistry.fontObject, floatValue11 + floatValue5 - 30.0F, floatValue13 + 10.0F, 9.0F, "[ПКМ]", RenderManager.RenderManagerState.compute24(intValue12, (int)(120.0F * f * floatValue4))
               );
            }

            floatValue13 += floatValue9;
         }

         renderManager3.invoke25();
      }
   }

   private static boolean check3(int i, int j, int k, LegacyClickGuiScreenController.LegacyClickGuiScreenControllerState legacyClickGuiScreenControllerState4) {
      float floatValue14 = 110.0F;
      float floatValue15 = 22.0F;
      float floatValue16 = legacyClickGuiScreenControllerState4.floatValue - floatValue14 - 10.0F;
      float floatValue17 = legacyClickGuiScreenControllerState4.floatValue2 - floatValue15 - 10.0F;
      if (k == 0 && RenderMath.check(i, j, floatValue16, floatValue17, floatValue14, floatValue15)) {
         flag2 = !flag2;
         return true;
      } else if (check4(i, j, legacyClickGuiScreenControllerState4)) {
         return false;
      } else {
         if (flag2) {
            Theme[] themes2 = Theme.values();
            float floatValue18 = themes2.length * 18.0F + 8.0F;
            float floatValue19 = floatValue17 - 6.0F - floatValue18;
            if (RenderMath.check(i, j, floatValue16, floatValue19, floatValue14, floatValue18)) {
               float floatValue20 = j - (floatValue19 + 4.0F);
               int intValue14 = (int)(floatValue20 / 18.0F);
               if (intValue14 >= 0 && intValue14 < themes2.length) {
                  Theme theme3 = themes2[intValue14];
                  Theme theme4 = WildClient.INSTANCE.themeManager.getTheme();
                  if (k == 0) {
                     if (theme4 != theme3) {
                        LegacyClickGuiState.directionalAnimation4.invoke();
                        ScreenTransitionController.getINSTANCE().invoke2((double)i, (double)j, theme3.getColor().getRGB(), theme3.getColor4().getRGB());
                        LegacyClickGuiState.theme = theme3;
                        LegacyClickGuiState.theme2 = theme3;
                        WildClient.INSTANCE.themeManager.invoke2(theme3);
                     }
                  } else if (k == 1 && theme3 == Theme.CUSTOM) {
                     if (theme4 != Theme.CUSTOM) {
                        LegacyClickGuiState.directionalAnimation4.invoke();
                        ScreenTransitionController.getINSTANCE().invoke2((double)i, (double)j, Theme.CUSTOM.getColor().getRGB(), Theme.CUSTOM.getColor4().getRGB());
                        LegacyClickGuiState.theme = Theme.CUSTOM;
                        LegacyClickGuiState.theme2 = Theme.CUSTOM;
                        WildClient.INSTANCE.themeManager.invoke2(Theme.CUSTOM);
                     }

                     ColorSetting colorSetting = WildClient.INSTANCE.themeManager.customThemeColor;
                     if (LegacyClickGuiState.colorSetting == colorSetting) {
                        LegacyClickGuiState.directionalAnimation5.invoke3(AnimationDirection.BACKWARDS);
                        LegacyClickGuiState.colorSetting = null;
                     } else {
                        LegacyClickGuiState.colorSetting = colorSetting;
                        LegacyClickGuiState.directionalAnimation5.invoke3(AnimationDirection.FORWARDS);
                        LegacyClickGuiState.floatValue = floatValue16 - 160.0F - 6.0F;
                        LegacyClickGuiState.floatValue2 = floatValue19;
                     }
                  }
               }

               return true;
            }

            if (k == 0 || k == 1) {
               flag2 = false;
               if (LegacyClickGuiState.colorSetting == WildClient.INSTANCE.themeManager.customThemeColor) {
                  LegacyClickGuiState.directionalAnimation5.invoke3(AnimationDirection.BACKWARDS);
                  LegacyClickGuiState.colorSetting = null;
               }
            }
         }

         return false;
      }
   }

   private static boolean check4(float f, float g, LegacyClickGuiScreenController.LegacyClickGuiScreenControllerState legacyClickGuiScreenControllerState5) {
      for (int intValue15 = 0; intValue15 < legacyClickGuiScreenControllerState5.categorys.length; intValue15++) {
         float floatValue21 = legacyClickGuiScreenControllerState5.floatValue7 + intValue15 * (legacyClickGuiScreenControllerState5.floatValue3 + legacyClickGuiScreenControllerState5.floatValue5);
         if (RenderMath.check(f, g, floatValue21, legacyClickGuiScreenControllerState5.floatValue8, legacyClickGuiScreenControllerState5.floatValue3, legacyClickGuiScreenControllerState5.floatValue4)) {
            return true;
         }
      }

      return false;
   }

   private static boolean check5(LegacyClickGuiScreenController.LegacyClickGuiScreenControllerState legacyClickGuiScreenControllerState6, int i, int j) {
      return false;
   }

   private static void invoke4(RenderManager renderManager4, LegacyClickGuiScreenController.LegacyClickGuiScreenControllerState legacyClickGuiScreenControllerState7, float f, int i, int j) {
      float floatValue22 = LegacyClickGuiState.easedAnimation5.measure3();
      if (!(floatValue22 <= 0.01F)) {
         float floatValue23 = measure(legacyClickGuiScreenControllerState7);
         float floatValue24 = legacyClickGuiScreenControllerState7.floatValue7 + (legacyClickGuiScreenControllerState7.floatValue6 - floatValue23) * 0.5F;
         float floatValue25 = measure2(legacyClickGuiScreenControllerState7, floatValue22);
         float floatValue26 = 18.0F;
         boolean glass2 = LegacyClickGuiState.blyurNada.isEnabled();
         int intValue16 = RenderManager.RenderManagerState.compute24(RenderManager.RenderManagerState.compute5(1, 1), (int)(18.0F * f * floatValue22));
         int intValue17 = RenderManager.RenderManagerState.compute24(RenderManager.RenderManagerState.compute4(1, 1), (int)((glass2 ? 145.0F : 210.0F) * f * floatValue22));
         int intValue18 = RenderManager.RenderManagerState.compute24(RenderManager.RenderManagerState.compute7(1, 1), (int)(230.0F * f * floatValue22));
         int intValue19 = RenderManager.RenderManagerState.compute24(RenderManager.RenderManagerState.compute6(1, 1), (int)(110.0F * f * floatValue22));
         int intValuePlaceholder = RenderManager.RenderManagerState.compute24(RenderManager.RenderManagerState.compute6(1, 1), (int)(88.0F * f * floatValue22));
         if (glass2) {
            renderManager4.invoke44(floatValue24, floatValue25, floatValue23, floatValue26, 9.0F, f * floatValue22);
         }

         renderManager4.invoke28(floatValue24, floatValue25, floatValue23, floatValue26, 9.0F, intValue16, 0.7F);
         renderManager4.invoke5(floatValue24, floatValue25, floatValue23, floatValue26, 9.0F, intValue17);
         renderManager4.invoke28(floatValue24, floatValue25, floatValue23, floatValue26, 9.0F, RenderManager.RenderManagerState.compute37(255, 255, 255, (int)(26.0F * f * floatValue22)), 1.0F);
         String text = LegacyClickGuiState.text == null ? "" : LegacyClickGuiState.text;
         String text2 = text.isEmpty() ? "" : resolve2(text);
         String text3 = resolve3(text, text2);
         float floatValue27 = floatValue24 + 8.0F;
         float floatValue28 = floatValue25 + 5.5F + 6.2F;
         if (text.isEmpty() && !LegacyClickGuiState.flag4) {
            renderManager4.invoke69(FontRegistry.fontObject, floatValue27, floatValue28, 11.0F, "Поиск", intValuePlaceholder);
         } else if (!text.isEmpty()) {
            renderManager4.invoke69(FontRegistry.fontObject, floatValue27, floatValue28, 11.0F, text, intValue18);
            if (!text3.isEmpty()) {
               float floatValue29 = RenderManager.resolve7(FontRegistry.fontObject, text, 11.0F).floatValue;
               float floatValue30 = floatValue27 + Math.min(floatValue29 + 1.0F, floatValue23 - 14.0F);
               renderManager4.invoke69(FontRegistry.fontObject, floatValue30, floatValue28, 11.0F, text3, intValue19);
            }
         }

         if (LegacyClickGuiState.flag4) {
            float floatValue31 = RenderManager.resolve7(FontRegistry.fontObject, text, 11.0F).floatValue;
            float floatValue32 = floatValue27 + Math.min(floatValue31 + 1.0F, floatValue23 - 14.0F);
            renderManager4.invoke4(floatValue32, floatValue25 + 4.0F, 1.0F, floatValue26 - 8.0F, RenderManager.RenderManagerState.compute24(intValue18, (int)(200.0F * floatValue22)));
         }

         boolean flag3 = RenderMath.check(i, j, floatValue24, floatValue25, floatValue23, floatValue26);
         if (flag3) {
            renderManager4.invoke28(floatValue24, floatValue25, floatValue23, floatValue26, 9.0F, RenderManager.RenderManagerState.compute24(intValue19, (int)(55.0F * floatValue22)), 0.55F);
         }
      }
   }

   private static boolean check6(int i, int j, int k, LegacyClickGuiScreenController.LegacyClickGuiScreenControllerState legacyClickGuiScreenControllerState8) {
      float floatValue33 = measure(legacyClickGuiScreenControllerState8);
      float floatValue34 = legacyClickGuiScreenControllerState8.floatValue7 + (legacyClickGuiScreenControllerState8.floatValue6 - floatValue33) * 0.5F;
      float floatValue35 = measure2(legacyClickGuiScreenControllerState8, LegacyClickGuiState.easedAnimation5.measure3());
      boolean flag4 = RenderMath.check(i, j, floatValue34, floatValue35, floatValue33, 18.0F);
      if (k == 0 && flag4) {
         LegacyClickGuiState.flag4 = true;
         return true;
      } else {
         if (k == 0 && LegacyClickGuiState.flag4 && !flag4) {
            LegacyClickGuiState.flag4 = false;
         }

         return false;
      }
   }

   private static float measure(LegacyClickGuiScreenController.LegacyClickGuiScreenControllerState legacyClickGuiScreenControllerState9) {
      return Math.min(156.0F, legacyClickGuiScreenControllerState9.floatValue6);
   }

   private static float measure2(LegacyClickGuiScreenController.LegacyClickGuiScreenControllerState legacyClickGuiScreenControllerState10, float f) {
      float floatValue36 = legacyClickGuiScreenControllerState10.floatValue8 + legacyClickGuiScreenControllerState10.floatValue4 + 8.0F;
      return floatValue36 + (1.0F - f) * 22.0F;
   }

   private static String resolve2(String string) {
      if (WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null) {
         String text4 = string.trim().toLowerCase();
         if (text4.isEmpty()) {
            return "";
         } else {
            ArrayList arrayList = WildClient.INSTANCE.moduleManager.getModules();
            return ((ArrayList<Module>)arrayList).stream()
               .filter(module -> module != null && module.name != null && module.name.toLowerCase().contains(text4))
               .min(Comparator.<Module>comparingInt(module -> {
                  String var2x = module.name.toLowerCase();
                  int intValue20 = var2x.indexOf(text4);
                  return intValue20 < 0 ? Integer.MAX_VALUE : intValue20;
               }).thenComparingInt(module -> module.name.length()))
               .map(module -> module.name)
               .orElse("");
         }
      } else {
         return "";
      }
   }

   private static String resolve3(String string, String string2) {
      if (string == null || string2 == null || string.isEmpty() || string2.isEmpty()) {
         return "";
      } else {
         return string2.regionMatches(true, 0, string, 0, string.length()) ? string2.substring(Math.min(string.length(), string2.length())) : string2;
      }
   }

   private static void invoke5(Category[] categories) {
      for (Category category2 : categories) {
         ENUM_MAP.computeIfAbsent(category2, LegacySettingListLayout::new);
      }
   }

   static final class LegacyClickGuiScreenControllerState {
      final boolean flag;
      final float floatValue;
      final float floatValue2;
      final float floatValue3;
      final float floatValue4;
      final float floatValue5;
      final float floatValue6;
      final float floatValue7;
      final float floatValue8;
      final Category[] categorys;

      private LegacyClickGuiScreenControllerState(boolean bl, float f, float g, float h, float i, float j, float k, float l, float m, Category[] categories2) {
         this.flag = bl;
         this.floatValue = f;
         this.floatValue2 = g;
         this.floatValue3 = h;
         this.floatValue4 = i;
         this.floatValue5 = j;
         this.floatValue6 = k;
         this.floatValue7 = l;
         this.floatValue8 = m;
         this.categorys = categories2;
      }

      static LegacyClickGuiScreenController.LegacyClickGuiScreenControllerState resolve() {
         MinecraftClient client = MinecraftClient.getInstance();
         if (client != null && client.getWindow() != null) {
            float floatValue37 = client.getWindow().getScaledWidth();
            float floatValue38 = client.getWindow().getScaledHeight();
            Category[] categories3 = new Category[]{Category.Combat, Category.Movement, Category.Visuals, Category.Player, Category.Misc};
            if (categories3.length == 0) {
               return new LegacyClickGuiScreenController.LegacyClickGuiScreenControllerState(false, floatValue37, floatValue38, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, categories3);
            } else {
               float floatValue39 = 128.0F;
               float floatValue40 = 8.0F;
               float floatValue41 = categories3.length * floatValue39 + (categories3.length - 1) * floatValue40;
               float floatValue42 = (floatValue37 - floatValue41) / 2.0F;
               float floatValue43 = RenderMath.measure49(floatValue38 - 80.0F, 190.0F, 320.0F);
               float floatValue44 = (floatValue38 - floatValue43) / 2.0F;
               return new LegacyClickGuiScreenController.LegacyClickGuiScreenControllerState(true, floatValue37, floatValue38, floatValue39, floatValue43, floatValue40, floatValue41, floatValue42, floatValue44, categories3);
            }
         } else {
            return new LegacyClickGuiScreenController.LegacyClickGuiScreenControllerState(false, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, Category.values());
         }
      }
   }
}
