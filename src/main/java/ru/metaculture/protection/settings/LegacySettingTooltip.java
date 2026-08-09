package ru.metaculture.protection;

public class LegacySettingTooltip extends LegacyClickGuiState {
   public static boolean check(RenderManager renderManager, double d, double e, int i) {
      int intValue = (int)LegacyMatrixScaleUtils.resolve((float)d, (float)e)[0];
      int intValue2 = (int)LegacyMatrixScaleUtils.resolve((float)d, (float)e)[1];
      ProjectionUtils projectionUtils = new ProjectionUtils(LegacyClickGuiState.client);
      LegacyClickGuiState.floatValue6 = (int)RenderMath.measure49(
         LegacyClickGuiState.floatValue6, 0.0F, LegacyMatrixScaleUtils.compute(projectionUtils.getIntValue()) - LegacyClickGuiState.floatValue8
      );
      LegacyClickGuiState.floatValue7 = (int)RenderMath.measure49(
         LegacyClickGuiState.floatValue7, 0.0F, LegacyMatrixScaleUtils.compute(projectionUtils.getIntValue2()) - LegacyClickGuiState.floatValue9
      );
      if (!LegacyClickGuiState.flag6) {
         float floatValue = LegacyClickGuiState.floatValue6 + 111.885F;
         float floatValue2 = LegacyClickGuiState.floatValue7 + 6.185F;
         float floatValue3 = 124.04F;
         float floatValue4 = 21.325F;
         if (i == 0 && LegacySearchOverlay.check(intValue, intValue2, floatValue, floatValue2, floatValue3, floatValue4)) {
            LegacyClickGuiState.flag4 = true;
            return true;
         }

         LegacyScrollController.invoke(intValue, intValue2);
         if (LegacyColorPickerController.check(intValue, intValue2, i)) {
            return true;
         }

         if (LegacyColorSettingRenderer.check(renderManager, intValue, intValue2, i)) {
            return true;
         }

         LegacyClickGuiOverlay.invoke2(d, e, i);
      }

      if (LegacyClickGuiState.keybindSetting != null && i >= 0 && i <= 2) {
         int intValue3 = -100 - i;
         LegacyClickGuiState.keybindSetting.keyCode = intValue3;
         LegacyClickGuiState.keybindSetting.waitingForBind = false;
         LegacyClickGuiState.keybindSetting = null;
         return true;
      } else {
         return false;
      }
   }
}
