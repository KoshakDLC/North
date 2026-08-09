package ru.metaculture.protection;

public class LegacyColorPickerController extends LegacyClickGuiState {
   public static boolean check(int i, int j, int k) {
      ColorSetting colorSetting = LegacyClickGuiState.colorSetting;
      if (colorSetting instanceof ColorSetting && (LegacyClickGuiState.floatValue != 0.0F || LegacyClickGuiState.floatValue2 != 0.0F)) {
         float floatValue = LegacyColorPickerRenderer.measure(LegacyClickGuiState.floatValue);
         float floatValue2 = LegacyClickGuiState.floatValue2;
         float floatValue3 = LegacyColorPickerRenderer.measure2(floatValue);
         float floatValue4 = LegacyColorPickerRenderer.measure3(floatValue2);
         float floatValue5 = LegacyColorPickerRenderer.measure4(floatValue);
         float floatValue6 = LegacyColorPickerRenderer.measure5(floatValue2);
         float floatValue7 = LegacyColorPickerRenderer.measure6(floatValue2);
         float floatValue8 = LegacyColorPickerRenderer.measure7(floatValue2);
         float floatValue9 = 148.0F;
         if (k == 0 && LegacySearchOverlay.check(i, j, floatValue3, floatValue4, 132.0F, 62.0F)) {
            LegacyClickGuiState.flag = true;
            LegacyClickGuiState.flag2 = false;
            LegacyClickGuiState.flag3 = false;
            invoke(colorSetting, i, j, floatValue3, floatValue4);
            invoke6();
            return true;
         } else if (k == 0 && LegacySearchOverlay.check(i, j, floatValue5, floatValue4, 10.0F, 62.0F)) {
            LegacyClickGuiState.flag2 = true;
            LegacyClickGuiState.flag = false;
            LegacyClickGuiState.flag3 = false;
            invoke2(colorSetting, j, floatValue4);
            invoke6();
            return true;
         } else if (k == 0 && LegacySearchOverlay.check(i, j, floatValue3, floatValue6, floatValue9, 7.0F)) {
            LegacyClickGuiState.flag3 = true;
            LegacyClickGuiState.flag2 = false;
            LegacyClickGuiState.flag = false;
            invoke3(colorSetting, i, floatValue3, floatValue9);
            invoke6();
            return true;
         } else if (k == 0 && LegacySearchOverlay.check(i, j, floatValue3, floatValue7, floatValue9, 10.0F)) {
            invoke4(colorSetting, i, floatValue3, floatValue9);
            invoke6();
            return true;
         } else if ((k == 0 || k == 1) && LegacySearchOverlay.check(i, j, floatValue3, floatValue8, floatValue9, 10.0F)) {
            invoke5(colorSetting, i, floatValue3, floatValue9, k == 1);
            invoke6();
            return true;
         } else {
            return LegacySearchOverlay.check(i, j, floatValue, floatValue2, 160.0F, 119.0F);
         }
      } else {
         return false;
      }
   }

   public static void invoke(ColorSetting colorSetting2, int i, int j, float f, float g) {
      float floatValue10 = Math.max(0.0F, Math.min(i - f, 132.0F));
      float floatValue11 = Math.max(0.0F, Math.min(j - g, 62.0F));
      colorSetting2.saturation = floatValue10 / 132.0F;
      colorSetting2.brightness = 1.0F - floatValue11 / 62.0F;
   }

   public static void invoke2(ColorSetting colorSetting3, int i, float f) {
      float floatValue12 = Math.max(0.0F, Math.min(i - f, 62.0F));
      colorSetting3.invoke3(floatValue12 / 62.0F * 360.0F);
   }

   public static void invoke3(ColorSetting colorSetting4, int i, float f, float g) {
      colorSetting4.setFloatValue3((i - f) / g);
   }

   private static void invoke4(ColorSetting colorSetting5, int i, float f, float g) {
      byte byteValue = 5;
      int intValue = Math.max(0, Math.min(byteValue - 1, (int)((i - f) / g * byteValue)));
      float[] floatValues = new float[]{0.0F, 180.0F, -30.0F, 30.0F, 120.0F};
      colorSetting5.invoke3(colorSetting5.measure2() + floatValues[intValue]);
      if (colorSetting5.saturation < 0.05F) {
         colorSetting5.saturation = 0.65F;
      }

      if (colorSetting5.brightness < 0.08F) {
         colorSetting5.brightness = 0.85F;
      }
   }

   private static void invoke5(ColorSetting colorSetting6, int i, float f, float g, boolean bl) {
      byte byteValue2 = 9;
      int intValue2 = Math.max(0, Math.min(byteValue2 - 1, (int)((i - f) / g * byteValue2)));
      if (intValue2 == 8) {
         if (!bl) {
            colorSetting6.invoke5();
         }
      } else {
         if (bl) {
            colorSetting6.invoke7(intValue2);
         } else {
            colorSetting6.invoke6(intValue2);
         }
      }
   }

   private static void invoke6() {
      if (WildClient.INSTANCE != null && WildClient.INSTANCE.configManager != null) {
         WildClient.INSTANCE.configManager.scheduleSave();
      }
   }
}
