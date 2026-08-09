package ru.metaculture.protection;

public class LegacySettingRenderer extends LegacyClickGuiState {
   public static boolean check(RenderManager renderManager, Setting setting, float f, float g, float h, int i, int j, int k) {
      if (setting instanceof BooleanSetting booleanSetting) {
         float floatValue = 8.0F;
         float floatValue2 = f + h - floatValue - 3.0F;
         float floatValue3 = g + 2.0F;
         if (k == 0 && LegacySearchOverlay.check(i, j, floatValue2, floatValue3, floatValue, floatValue)) {
            booleanSetting.setValue(!booleanSetting.isEnabled());
            if (WildClient.INSTANCE.configManager != null) {
               WildClient.INSTANCE.configManager.scheduleSave();
            }

            return true;
         }
      }

      if (setting instanceof KeybindSetting keybindSetting) {
         float floatValue4 = 10.075F;
         String text = keybindSetting.waitingForBind ? "..." : KeyCodeUtils.resolve(keybindSetting.keyCode);
         float floatValue5 = RenderManager.resolve7(FontRegistry.fontObject, text, 12.0F).floatValue;
         float floatValue6 = 16.055F;
         float floatValue7 = Math.max(floatValue6, floatValue5 + 8.0F);
         float floatValue8 = f + h - floatValue7 - 2.0F;
         if (floatValue8 < f) {
            floatValue8 = f;
            floatValue7 = h - 2.0F;
         }

         float floatValue9 = floatValue8 - 6.0F;
         float floatValue10 = floatValue7 + 2.0F;
         if (floatValue9 < f) {
            floatValue10 = floatValue9 + floatValue10 - f;
            floatValue9 = f;
         }

         if (LegacySearchOverlay.check(i, j, floatValue9, g, floatValue10, floatValue4)) {
            if (k == 0) {
               if (LegacyClickGuiState.keybindSetting != keybindSetting) {
                  if (LegacyClickGuiState.keybindSetting != null) {
                     LegacyClickGuiState.keybindSetting.waitingForBind = false;
                  }

                  LegacyClickGuiState.keybindSetting = keybindSetting;
                  keybindSetting.waitingForBind = true;
               }

               return true;
            }

            if (LegacyClickGuiState.keybindSetting == keybindSetting && k >= 0 && k <= 8) {
               int intValue = -100 - k;
               keybindSetting.keyCode = intValue;
               keybindSetting.waitingForBind = false;
               LegacyClickGuiState.keybindSetting = null;
               if (WildClient.INSTANCE.configManager != null) {
                  WildClient.INSTANCE.configManager.scheduleSave();
               }

               return true;
            }
         }
      }

      if (setting instanceof ColorSetting colorSetting) {
         float floatValue11 = 40.0F;
         float floatValue12 = f + h - floatValue11 - 2.0F;
         float floatValue13 = floatValue12 - 10.0F;
         float floatValue14 = 46.48F;
         float floatValue15 = 10.075F;
         if (k == 0 && LegacySearchOverlay.check(i, j, floatValue13, g, floatValue14, floatValue15)) {
            if (LegacyClickGuiState.colorSetting == colorSetting) {
               LegacyClickGuiState.directionalAnimation5.invoke3(AnimationDirection.BACKWARDS);
               LegacyClickGuiState.colorSetting = null;
               LegacyClickGuiState.floatValue = 0.0F;
               LegacyClickGuiState.floatValue2 = 0.0F;
            } else {
               LegacyClickGuiState.colorSetting = colorSetting;
               LegacyClickGuiState.directionalAnimation5.invoke3(AnimationDirection.FORWARDS);
               float[] floatValues = LegacyColorSettingRenderer.resolve(renderManager, colorSetting);
               if (floatValues != null) {
                  LegacyClickGuiState.floatValue = floatValues[0];
                  LegacyClickGuiState.floatValue2 = floatValues[1];
               }
            }

            return true;
         }

         if (LegacyClickGuiState.colorSetting == colorSetting && LegacyColorPickerController.check(i, j, k)) {
            return true;
         }
      }

      if (setting instanceof NumberSetting numberSetting) {
         float floatValue16 = 4.0F;
         float floatValue17 = g + 10.0F;
         float floatValue18 = h - 2.5F;
         float floatValue19 = floatValue17 + 2.0F;
         if (k == 0 && LegacySearchOverlay.check(i, j, f, floatValue19, floatValue18, floatValue16)) {
            LegacyClickGuiState.numberSetting = numberSetting;
            LegacyClickGuiState.floatValue3 = f;
            LegacyClickGuiState.floatValue4 = floatValue19;
            LegacyClickGuiState.floatValue5 = floatValue18;
            float floatValue20 = (i - f) / floatValue18;
            floatValue20 = Math.max(0.0F, Math.min(1.0F, floatValue20));
            numberSetting.value = numberSetting.minimum + (numberSetting.maximum - numberSetting.minimum) * floatValue20;
            if (WildClient.INSTANCE.configManager != null) {
               WildClient.INSTANCE.configManager.scheduleSave();
            }

            return true;
         }
      }

      if (setting instanceof ButtonSetting buttonSetting) {
         float floatValue21 = 10.075F;
         float floatValue22 = 60.0F;
         float floatValue23 = f + h - floatValue22 - 2.0F;
         if (k == 0 && LegacySearchOverlay.check(i, j, floatValue23, g, floatValue22, floatValue21)) {
            buttonSetting.invoke8();
            return true;
         }
      }

      if (setting instanceof ModeSetting modeSetting) {
         float floatValue24 = 2.0F;
         float floatValue25 = 10.075F;
         float floatValue26 = 3.0F;
         float floatValue27 = -2.0F;
         float floatValue28 = floatValue26;
         float floatValue29 = 0.0F;

         for (String text2 : modeSetting.options) {
            float floatValue30 = RenderManager.resolve7(FontRegistry.fontObject, text2, 12.0F).floatValue + floatValue26 * 2.0F;
            if (floatValue28 + floatValue30 > h && floatValue28 > floatValue26) {
               floatValue28 = floatValue26;
               floatValue29 += floatValue25 + floatValue27;
            }

            floatValue28 += floatValue30 + floatValue24;
         }

         float floatValue31 = g + 10.0F;
         float floatValue32 = floatValue29 + floatValue25;
         if (k == 0 && LegacySearchOverlay.check(i, j, f, floatValue31, h, floatValue32)) {
            float floatValue33 = floatValue26;
            float floatValue34 = 1.5F;

            for (String text3 : modeSetting.options) {
               float floatValue35 = RenderManager.resolve7(FontRegistry.fontObject, text3, 12.0F).floatValue + floatValue26 * 2.0F;
               if (floatValue33 + floatValue35 > h && floatValue33 > floatValue26) {
                  floatValue33 = floatValue26;
                  floatValue34 += floatValue25 + floatValue27;
               }

               if (LegacySearchOverlay.check(i, j, f + floatValue33, floatValue31 + floatValue34, floatValue35, floatValue25)) {
                  modeSetting.value = text3;
                  modeSetting.selectedIndex = modeSetting.options.indexOf(text3);
                  if (WildClient.INSTANCE.configManager != null) {
                     WildClient.INSTANCE.configManager.scheduleSave();
                  }

                  return true;
               }

               floatValue33 += floatValue35 + floatValue24;
            }
         }
      }

      if (setting instanceof TextSetting textSetting) {
         float floatValue36 = 10.075F;
         float floatValue37 = 63.56F;
         float floatValue38 = f + 42.0F;
         if (k == 0 && LegacySearchOverlay.check(i, j, floatValue38, g, floatValue37, floatValue36)) {
            if (LegacyClickGuiState.textSetting != textSetting) {
               if (LegacyClickGuiState.textSetting != null) {
                  LegacyClickGuiState.textSetting.flag = false;
               }

               LegacyClickGuiState.textSetting = textSetting;
               textSetting.flag = true;
            }

            return true;
         }

         if (k == 0 && LegacyClickGuiState.textSetting == textSetting) {
            LegacyClickGuiState.textSetting.flag = false;
            LegacyClickGuiState.textSetting = null;
            if (WildClient.INSTANCE.configManager != null) {
               WildClient.INSTANCE.configManager.scheduleSave();
            }
         }
      }

      if (setting instanceof GroupSetting groupSetting) {
         float floatValue39 = g + 10.0F;
         float floatValue40 = f;
         float floatValue41 = floatValue39;
         float floatValue42 = 3.0F;
         float floatValue43 = 10.0F;
         float floatValue44 = 4.0F;

         for (BooleanSetting booleanSetting2 : groupSetting.options) {
            float floatValue45 = RenderManager.resolve7(FontRegistry.fontObject, booleanSetting2.name, 12.0F).floatValue;
            float floatValue46 = floatValue45 + floatValue44 * 2.0F;
            if (floatValue40 + floatValue46 > f + h) {
               floatValue40 = f;
               floatValue41 += floatValue43 + floatValue42;
            }

            if (k == 0 && LegacySearchOverlay.check(i, j, floatValue40, floatValue41, floatValue46, floatValue43)) {
               booleanSetting2.setValue(!booleanSetting2.isEnabled());
               if (WildClient.INSTANCE.configManager != null) {
                  WildClient.INSTANCE.configManager.scheduleSave();
               }

               return true;
            }

            floatValue40 += floatValue46 + floatValue42;
         }
      }

      return false;
   }
}
