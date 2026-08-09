package ru.metaculture.protection;

import java.util.List;
import java.util.stream.Collectors;
import org.wild.module.api.Module;

public class LegacyColorSettingRenderer extends LegacyClickGuiState {
   public static boolean check(RenderManager renderManager, int i, int j, int k) {
      float floatValue = LegacyClickGuiState.floatValue6 + 104.735F;
      float floatValue2 = LegacyClickGuiState.floatValue7 + 34.025F;
      float floatValue3 = 261.5F;
      float floatValue4 = 209.5F;
      float floatValue5 = floatValue + 5.0F;
      float floatValue6 = floatValue2 + 5.0F;
      float floatValue7 = floatValue3 - 10.0F;
      float floatValue8 = floatValue4 - 10.0F;
      if (!LegacySearchOverlay.check(i, j, floatValue5, floatValue6, floatValue7, floatValue8)) {
         return false;
      } else {
         List items = LegacyClickGuiState.items;
         if (LegacyClickGuiState.flag4 && !LegacyClickGuiState.text.isEmpty()) {
            String text = LegacyClickGuiState.text.toLowerCase().trim();
            items = LegacyClickGuiState.items.stream().filter(module -> module.name.toLowerCase().contains(text)).collect(Collectors.toList());
         }

         int intValue = 1;
         float floatValue9 = LegacyClickGuiState.resolve().measure();
         float floatValue10 = 0.0F;
         float floatValue11 = 0.0F;

         for (Module module2 : (Iterable<Module>)items) {
            float floatValue12 = 12.0F;
            if (LegacyClickGuiState.values.contains(module2)) {
               for (Setting setting : module2.getVisibleSettings()) {
                  floatValue12 += LegacySettingLayout.measure(renderManager, setting);
               }

               floatValue12 = Math.max(floatValue12, 20.0F);
            }

            if (intValue % 2 == 0) {
               float floatValue13 = floatValue9 + floatValue11 - 30.0F;
               float floatValue14 = LegacyClickGuiState.floatValue6 + 238.35F;
               float floatValue15 = LegacyClickGuiState.floatValue7 + 43.365F + floatValue13;
               float floatValue16 = 121.47F;
               float floatValue17 = 21.325F;
               if (LegacyClickGuiState.values.contains(module2) && k == 0) {
                  float floatValue18 = LegacyClickGuiState.floatValue7 + 64.69F + floatValue13 + 4.0F;
                  float floatValue19 = LegacyClickGuiState.floatValue6 + 238.35F + 9.0F;
                  float floatValue20 = 105.47F;
                  float floatValue21 = 0.0F;

                  for (Setting setting2 : module2.getVisibleSettings()) {
                     float floatValue22 = floatValue18 + floatValue21;
                     if (LegacySettingRenderer.check(renderManager, setting2, floatValue19, floatValue22, floatValue20, i, j, k)) {
                        return true;
                     }

                     floatValue21 += LegacySettingLayout.measure(renderManager, setting2) + 1.0F;
                  }
               }

               if (LegacyClickGuiState.values.contains(module2)) {
                  floatValue11 += floatValue12;
               }

               if (LegacySearchOverlay.check(i, j, floatValue14, floatValue15, floatValue16, floatValue17) && k == 0) {
                  module2.toggle();
               }

               if (LegacySearchOverlay.check(i, j, floatValue14, floatValue15, floatValue16, floatValue17) && k == 1 && !module2.getVisibleSettings().isEmpty()) {
                  if (LegacyClickGuiState.values.contains(module2)) {
                     LegacyClickGuiState.values.remove(module2);
                     LegacyClickGuiState.resolve2(module2).animateTo(0.0, 0.6F, Easings.EASE_OUT_QUART);
                     LegacyClickGuiState.resolve3(module2).animateTo(0.0, 0.16F, Easings.EASING_FUNCTION_14);
                     if (LegacyClickGuiState.colorSetting != null && module2.getVisibleSettings().contains(LegacyClickGuiState.colorSetting)) {
                        LegacyClickGuiState.directionalAnimation5.invoke3(AnimationDirection.BACKWARDS);
                        LegacyClickGuiState.colorSetting = null;
                        LegacyClickGuiState.floatValue = 0.0F;
                        LegacyClickGuiState.floatValue2 = 0.0F;
                     }
                  } else {
                     LegacyClickGuiState.values.add(module2);
                     LegacyClickGuiState.resolve3(module2).animateTo(1.0, 0.16F, Easings.EASING_FUNCTION_14);
                     LegacyClickGuiState.resolve2(module2).animateTo(1.0, 0.6F, Easings.EASE_OUT_QUART);
                  }
               }

               if (LegacySearchOverlay.check(i, j, floatValue14, floatValue15, floatValue16, floatValue17) && k == 2) {
                  if (module2.expanded) {
                     module2.expanded = false;
                     LegacyClickGuiState.module = null;
                     LegacyClickGuiState.resolve4(module2).animateTo(0.0, 0.2F, Easings.EASING_FUNCTION_14);
                  } else {
                     if (LegacyClickGuiState.module != null) {
                        LegacyClickGuiState.module.expanded = false;
                        LegacyClickGuiState.resolve4(LegacyClickGuiState.module).animateTo(0.0, 0.2F, Easings.EASING_FUNCTION_14);
                     }

                     LegacyClickGuiState.module = module2;
                     module2.expanded = true;
                     LegacyClickGuiState.resolve4(module2).animateTo(1.0, 0.2F, Easings.EASING_FUNCTION_14);
                  }

                  return true;
               }

               if (module2.expanded || module2.bindKey != -1) {
                  float floatValue23 = LegacyClickGuiState.floatValue6 + 247.895F;
                  float floatValue24 = LegacyClickGuiState.floatValue7 + 49.555F + floatValue13;
                  float floatValue25 = RenderManager.resolve7(FontRegistry.fontObject, module2.name, 14.0F).floatValue;
                  float floatValue26 = floatValue23 + floatValue25 + 4.0F;
                  float floatValue27 = floatValue24 - 1.0F;
                  String text2 = module2.expanded ? "..." : KeyboardKey.resolve(module2.bindKey);
                  float floatValue28 = RenderManager.resolve7(FontRegistry.fontObject, text2, 12.0F).floatValue;
                  float floatValue29 = 16.0F;
                  float floatValue30 = Math.max(floatValue29, floatValue28 + 8.0F);
                  if (LegacySearchOverlay.check(i, j, floatValue26, floatValue27, floatValue30, 16.0F)) {
                     if (k == 2) {
                        if (module2.expanded) {
                           module2.expanded = false;
                           LegacyClickGuiState.module = null;
                           LegacyClickGuiState.resolve4(module2).animateTo(0.0, 0.2F, Easings.EASING_FUNCTION_14);
                        } else {
                           if (LegacyClickGuiState.module != null) {
                              LegacyClickGuiState.module.expanded = false;
                              LegacyClickGuiState.resolve4(LegacyClickGuiState.module).animateTo(0.0, 0.2F, Easings.EASING_FUNCTION_14);
                           }

                           LegacyClickGuiState.module = module2;
                           module2.expanded = true;
                           LegacyClickGuiState.resolve4(module2).animateTo(1.0, 0.2F, Easings.EASING_FUNCTION_14);
                        }

                        return true;
                     }

                     if (module2.expanded && k >= 0 && k <= 8) {
                        int intValue2 = -100 - k;
                        module2.bindKey = intValue2;
                        module2.expanded = false;
                        LegacyClickGuiState.module = null;
                        LegacyClickGuiState.resolve4(module2).animateTo(1.0, 0.2F, Easings.EASING_FUNCTION_14);
                        return true;
                     }
                  }
               }
            } else {
               float floatValue31 = floatValue9 + floatValue10;
               float floatValue32 = LegacyClickGuiState.floatValue6 + 111.885F;
               float floatValue33 = LegacyClickGuiState.floatValue7 + 43.365F + floatValue31;
               float floatValue34 = 121.47F;
               float floatValue35 = 21.325F;
               if (LegacyClickGuiState.values.contains(module2) && k == 0) {
                  float floatValue36 = LegacyClickGuiState.floatValue7 + 64.69F + floatValue31 + 4.0F;
                  float floatValue37 = LegacyClickGuiState.floatValue6 + 111.885F + 9.0F;
                  float floatValue38 = 105.47F;
                  float floatValue39 = 0.0F;

                  for (Setting setting3 : module2.getVisibleSettings()) {
                     float floatValue40 = floatValue36 + floatValue39;
                     if (LegacySettingRenderer.check(renderManager, setting3, floatValue37, floatValue40, floatValue38, i, j, k)) {
                        return true;
                     }

                     floatValue39 += LegacySettingLayout.measure(renderManager, setting3) + 1.0F;
                  }
               }

               if (LegacyClickGuiState.values.contains(module2)) {
                  floatValue10 += floatValue12;
               }

               if (module2.expanded || module2.bindKey != -1) {
                  float floatValue41 = LegacyClickGuiState.floatValue6 + 121.425F;
                  float floatValue42 = LegacyClickGuiState.floatValue7 + 49.555F + floatValue31;
                  float floatValue43 = RenderManager.resolve7(FontRegistry.fontObject, module2.name, 14.0F).floatValue;
                  float floatValue44 = floatValue41 + floatValue43 + 4.0F;
                  float floatValue45 = floatValue42 - 1.0F;
                  String text3 = module2.expanded ? "..." : KeyboardKey.resolve(module2.bindKey);
                  float floatValue46 = RenderManager.resolve7(FontRegistry.fontObject, text3, 12.0F).floatValue;
                  float floatValue47 = 16.0F;
                  float floatValue48 = Math.max(floatValue47, floatValue46 + 8.0F);
                  if (LegacySearchOverlay.check(i, j, floatValue44, floatValue45, floatValue48, 16.0F)) {
                     if (k == 2) {
                        if (module2.expanded) {
                           module2.expanded = false;
                           LegacyClickGuiState.module = null;
                           LegacyClickGuiState.resolve4(module2).animateTo(0.0, 0.2F, Easings.EASING_FUNCTION_14);
                        } else {
                           if (LegacyClickGuiState.module != null) {
                              LegacyClickGuiState.module.expanded = false;
                              LegacyClickGuiState.resolve4(LegacyClickGuiState.module).animateTo(0.0, 0.2F, Easings.EASING_FUNCTION_14);
                           }

                           LegacyClickGuiState.module = module2;
                           module2.expanded = true;
                           LegacyClickGuiState.resolve4(module2).animateTo(1.0, 0.2F, Easings.EASING_FUNCTION_14);
                        }

                        return true;
                     }

                     if (module2.expanded && k >= 0 && k <= 8) {
                        int intValue3 = -100 - k;
                        module2.bindKey = intValue3;
                        module2.expanded = false;
                        LegacyClickGuiState.module = null;
                        LegacyClickGuiState.resolve4(module2).animateTo(1.0, 0.2F, Easings.EASING_FUNCTION_14);
                        return true;
                     }
                  }
               }

               if (LegacySearchOverlay.check(i, j, floatValue32, floatValue33, floatValue34, floatValue35) && k == 0) {
                  module2.toggle();
               }

               if (LegacySearchOverlay.check(i, j, floatValue32, floatValue33, floatValue34, floatValue35) && k == 1 && !module2.getVisibleSettings().isEmpty()) {
                  if (LegacyClickGuiState.values.contains(module2)) {
                     LegacyClickGuiState.values.remove(module2);
                     LegacyClickGuiState.resolve2(module2).animateTo(0.0, 0.6F, Easings.EASE_OUT_QUART);
                     LegacyClickGuiState.resolve3(module2).animateTo(0.0, 0.16F, Easings.EASING_FUNCTION_14);
                     if (LegacyClickGuiState.colorSetting != null && module2.getVisibleSettings().contains(LegacyClickGuiState.colorSetting)) {
                        LegacyClickGuiState.directionalAnimation5.invoke3(AnimationDirection.BACKWARDS);
                        LegacyClickGuiState.colorSetting = null;
                        LegacyClickGuiState.floatValue = 0.0F;
                        LegacyClickGuiState.floatValue2 = 0.0F;
                     }
                  } else {
                     LegacyClickGuiState.values.add(module2);
                     LegacyClickGuiState.resolve3(module2).animateTo(1.0, 0.16F, Easings.EASING_FUNCTION_14);
                     LegacyClickGuiState.resolve2(module2).animateTo(1.0, 0.6F, Easings.EASE_OUT_QUART);
                  }
               }

               if (LegacySearchOverlay.check(i, j, floatValue32, floatValue33, floatValue34, floatValue35) && k == 2) {
                  if (module2.expanded) {
                     module2.expanded = false;
                     LegacyClickGuiState.module = null;
                     LegacyClickGuiState.resolve4(module2).animateTo(0.0, 1.0, Easings.EASING_FUNCTION_14);
                  } else {
                     if (LegacyClickGuiState.module != null) {
                        LegacyClickGuiState.module.expanded = false;
                        LegacyClickGuiState.resolve4(LegacyClickGuiState.module).animateTo(0.0, 1.0, Easings.EASING_FUNCTION_14);
                     }

                     LegacyClickGuiState.module = module2;
                     module2.expanded = true;
                     LegacyClickGuiState.resolve4(module2).animateTo(1.0, 1.0, Easings.EASING_FUNCTION_14);
                  }

                  return true;
               }

               floatValue9 += 30.325F;
            }

            intValue++;
         }

         return false;
      }
   }

   public static float[] resolve(RenderManager renderManager2, ColorSetting colorSetting) {
      if (colorSetting == null) {
         return null;
      } else {
         int intValue4 = 1;
         float floatValue49 = LegacyClickGuiState.resolve().measure();
         float floatValue50 = 0.0F;
         float floatValue51 = 0.0F;

         for (Module module3 : LegacyClickGuiState.items) {
            float floatValue52 = 12.0F;
            if (LegacyClickGuiState.values.contains(module3)) {
               for (Setting setting4 : module3.getVisibleSettings()) {
                  floatValue52 += LegacySettingLayout.measure(renderManager2, setting4);
               }

               floatValue52 = Math.max(floatValue52, 20.0F);
            }

            if (intValue4 % 2 == 0) {
               float floatValue53 = floatValue49 + floatValue51 - 30.0F;
               if (LegacyClickGuiState.values.contains(module3)) {
                  float floatValue54 = LegacyClickGuiState.floatValue7 + 64.69F + floatValue53 + 4.0F;
                  float floatValue55 = LegacyClickGuiState.floatValue6 + 238.35F + 9.0F;
                  float floatValue56 = 111.47F;
                  float floatValue57 = 0.0F;

                  for (Setting setting5 : module3.getVisibleSettings()) {
                     if (setting5 == colorSetting) {
                        float floatValue58 = floatValue55 + floatValue56 - 15.0F;
                        float floatValue59 = floatValue54 + floatValue57 - 5.0F;
                        return new float[]{floatValue58, floatValue59};
                     }

                     floatValue57 += LegacySettingLayout.measure(renderManager2, setting5) + 3.0F;
                  }

                  floatValue51 += floatValue52;
               }
            } else {
               float floatValue60 = floatValue49 + floatValue50;
               if (LegacyClickGuiState.values.contains(module3)) {
                  float floatValue61 = LegacyClickGuiState.floatValue7 + 64.69F + floatValue60 + 4.0F;
                  float floatValue62 = LegacyClickGuiState.floatValue6 + 111.885F + 9.0F;
                  float floatValue63 = 111.47F;
                  float floatValue64 = 0.0F;

                  for (Setting setting6 : module3.getVisibleSettings()) {
                     if (setting6 == colorSetting) {
                        float floatValue65 = floatValue62 + floatValue63 - 15.0F;
                        float floatValue66 = floatValue61 + floatValue64 - 5.0F;
                        return new float[]{floatValue65, floatValue66};
                     }

                     floatValue64 += LegacySettingLayout.measure(renderManager2, setting6) + 3.0F;
                  }

                  floatValue50 += floatValue52;
               }

               floatValue49 += 30.325F;
            }

            intValue4++;
         }

         return null;
      }
   }
}
