package ru.metaculture.protection;

public class LegacyPopupState extends LegacyClickGuiState {
   public static boolean check() {
      if (!LegacyClickGuiState.flag6 && LegacyClickGuiState.easedAnimation.getDoubleValue4() > 0.0) {
         LegacyClickGuiState.easedAnimation.animateTo(0.0, 0.4F, Easings.EASING_FUNCTION_17);
         LegacyClickGuiState.flag6 = true;
      }

      return false;
   }
}
