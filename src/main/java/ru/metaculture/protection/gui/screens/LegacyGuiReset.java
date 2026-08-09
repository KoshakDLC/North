package ru.metaculture.protection;

public class LegacyGuiReset extends LegacyClickGuiState {
   public static void invoke() {
      if (LegacyClickGuiState.flag6 && LegacyClickGuiState.easedAnimation.check3()) {
         LegacyClickGuiState.flag6 = false;
      }
   }
}
