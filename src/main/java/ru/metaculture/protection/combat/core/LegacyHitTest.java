package ru.metaculture.protection;

public class LegacyHitTest extends LegacyClickGuiState {
   public static boolean check(double d, double e, double f, double g) {
      float[] floatValues = LegacyMatrixScaleUtils.resolve((float)d, (float)e);
      float floatValue = floatValues[0];
      float floatValue2 = floatValues[1];
      float floatValue3 = LegacyClickGuiState.floatValue6 + 104.735F + 5.0F;
      float floatValue4 = LegacyClickGuiState.floatValue7 + 34.025F + 5.0F;
      float floatValue5 = 251.5F;
      float floatValue6 = 199.5F;
      if (!LegacyClickGuiState.flag6 && LegacySearchOverlay.check(floatValue, floatValue2, floatValue3, floatValue4, floatValue5, floatValue6)) {
         LegacyClickGuiState.resolve().invoke2(g);
         return true;
      } else {
         return false;
      }
   }
}
