package ru.metaculture.protection;

public class LegacyColorPickerInput extends LegacyClickGuiState {
   public static boolean check(double d, double e, int i, double f, double g) {
      int intValue = (int)LegacyMatrixScaleUtils.resolve((float)d, (float)e)[0];
      int intValue2 = (int)LegacyMatrixScaleUtils.resolve((float)d, (float)e)[1];
      if (LegacyClickGuiState.colorSetting != null && LegacyClickGuiState.colorSetting instanceof ColorSetting) {
         ColorSetting colorSetting = LegacyClickGuiState.colorSetting;
         float floatValue = LegacyClickGuiState.floatValue;
         float floatValue2 = LegacyClickGuiState.floatValue2;
         if (floatValue != 0.0F || floatValue2 != 0.0F) {
            float floatValue3 = LegacyColorPickerRenderer.measure(floatValue);
            float floatValue4 = LegacyColorPickerRenderer.measure2(floatValue3);
            float floatValue5 = LegacyColorPickerRenderer.measure3(floatValue2);
            float floatValue6 = 148.0F;
            if (LegacyClickGuiState.flag) {
               LegacyColorPickerController.invoke(colorSetting, intValue, intValue2, floatValue4, floatValue5);
               if (WildClient.INSTANCE.configManager != null) {
                  WildClient.INSTANCE.configManager.scheduleSave();
               }

               return true;
            }

            if (LegacyClickGuiState.flag2) {
               LegacyColorPickerController.invoke2(colorSetting, intValue2, floatValue5);
               if (WildClient.INSTANCE.configManager != null) {
                  WildClient.INSTANCE.configManager.scheduleSave();
               }

               return true;
            }

            if (LegacyClickGuiState.flag3) {
               LegacyColorPickerController.invoke3(colorSetting, intValue, floatValue4, floatValue6);
               if (WildClient.INSTANCE.configManager != null) {
                  WildClient.INSTANCE.configManager.scheduleSave();
               }

               return true;
            }
         }
      }

      if (LegacyClickGuiState.numberSetting != null) {
         NumberSetting numberSetting = LegacyClickGuiState.numberSetting;
         float floatValue7 = (intValue - LegacyClickGuiState.floatValue3) / LegacyClickGuiState.floatValue5;
         floatValue7 = Math.max(0.0F, Math.min(1.0F, floatValue7));
         numberSetting.value = numberSetting.minimum + (numberSetting.maximum - numberSetting.minimum) * floatValue7;
         if (WildClient.INSTANCE.configManager != null) {
            WildClient.INSTANCE.configManager.scheduleSave();
         }

         return true;
      } else {
         return false;
      }
   }
}
