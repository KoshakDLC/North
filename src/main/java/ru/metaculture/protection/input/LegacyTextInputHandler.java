package ru.metaculture.protection;

public class LegacyTextInputHandler extends LegacyClickGuiState {
   public static boolean check(char c, int i) {
      if (LegacyClickGuiState.textSetting != null) {
         if (c == '\b') {
            if (!LegacyClickGuiState.textSetting.value.isEmpty()) {
               LegacyClickGuiState.textSetting.value = LegacyClickGuiState.textSetting
                  .value
                  .substring(0, LegacyClickGuiState.textSetting.value.length() - 1);
               if (WildClient.INSTANCE.configManager != null) {
                  WildClient.INSTANCE.configManager.scheduleSave();
               }
            }

            return true;
         }

         if (c >= ' ' && c != 127) {
            if (LegacyClickGuiState.textSetting.value.length() < 16) {
               LegacyClickGuiState.textSetting.value = LegacyClickGuiState.textSetting.value + c;
               if (WildClient.INSTANCE.configManager != null) {
                  WildClient.INSTANCE.configManager.scheduleSave();
               }
            }

            return true;
         }
      }

      if (LegacyClickGuiState.flag4) {
         if (c == '\b') {
            return true;
         }

         if (c >= ' ' && c != 127 && (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c >= '0' && c <= '9' || c == ' ')) {
            if (LegacyClickGuiState.text.length() < 50) {
               LegacyClickGuiState.text = LegacyClickGuiState.text + c;
            }

            return true;
         }
      }

      return false;
   }
}
