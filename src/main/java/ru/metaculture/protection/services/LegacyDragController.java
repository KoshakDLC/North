package ru.metaculture.protection;

public class LegacyDragController extends LegacyClickGuiState {
   public static void invoke() {
      if ((LegacyClickGuiState.numberSetting != null || LegacyClickGuiState.flag || LegacyClickGuiState.flag2 || LegacyClickGuiState.flag3)
         && WildClient.INSTANCE.configManager != null) {
         WildClient.INSTANCE.configManager.scheduleSave();
      }

      LegacyClickGuiState.flag = false;
      LegacyClickGuiState.flag2 = false;
      LegacyClickGuiState.flag3 = false;
      LegacyClickGuiState.numberSetting = null;
      LegacyClickGuiState.floatValue3 = 0.0F;
      LegacyClickGuiState.floatValue4 = 0.0F;
      LegacyClickGuiState.floatValue5 = 0.0F;
   }
}
