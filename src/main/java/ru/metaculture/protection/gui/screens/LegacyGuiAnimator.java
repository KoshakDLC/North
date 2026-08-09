package ru.metaculture.protection;

public class LegacyGuiAnimator extends LegacyClickGuiState {
   public static void invoke() {
      LegacyClickGuiState.flexibleAnimation = LegacyClickGuiState.flexibleAnimation.resolve(1.0, 0.2F);
      LegacyClickGuiState.easedAnimation.invoke(0.0);
      LegacyClickGuiState.easedAnimation.animateTo(1.0, 0.4F, Easings.EASING_FUNCTION_17);
      LegacyClickGuiState.flag6 = false;
      LegacyClickGuiState.directionalAnimation.invoke();
      LegacyClickGuiState.timedAnimation.setTimestamp2(1.0);
   }
}
