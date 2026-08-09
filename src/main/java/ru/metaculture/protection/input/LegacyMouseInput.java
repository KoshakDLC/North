package ru.metaculture.protection;

public class LegacyMouseInput extends LegacyClickGuiState {
   public static boolean check(int i, int j, int k) {
      boolean flag = (k & 2) != 0;
      if (flag && i == 70) {
         LegacyClickGuiState.flag4 = !LegacyClickGuiState.flag4;
         if (!LegacyClickGuiState.flag4 && LegacyClickGuiState.text == null) {
            LegacyClickGuiState.text = "";
         }

         return true;
      } else if (LegacyClickGuiState.module != null) {
         if (i == 256) {
            LegacyClickGuiState.module.expanded = false;
            LegacyClickGuiState.module = null;
         } else if (i == 261) {
            LegacyClickGuiState.module.bindKey = -1;
            LegacyClickGuiState.module.expanded = false;
            LegacyClickGuiState.resolve4(LegacyClickGuiState.module).animateTo(0.0, 0.2F, Easings.EASING_FUNCTION_14);
            LegacyClickGuiState.module = null;
            if (WildClient.INSTANCE.configManager != null) {
               WildClient.INSTANCE.configManager.scheduleSave();
            }
         } else {
            LegacyClickGuiState.module.bindKey = i;
            LegacyClickGuiState.module.expanded = false;
            LegacyClickGuiState.resolve4(LegacyClickGuiState.module).animateTo(1.0, 0.2F, Easings.EASING_FUNCTION_14);
            LegacyClickGuiState.module = null;
            if (WildClient.INSTANCE.configManager != null) {
               WildClient.INSTANCE.configManager.scheduleSave();
            }
         }

         return true;
      } else if (LegacyClickGuiState.keybindSetting != null) {
         if (i == 256) {
            LegacyClickGuiState.keybindSetting.waitingForBind = false;
            LegacyClickGuiState.keybindSetting = null;
         } else if (i == 261) {
            LegacyClickGuiState.keybindSetting.keyCode = -1;
            LegacyClickGuiState.keybindSetting.waitingForBind = false;
            LegacyClickGuiState.keybindSetting = null;
            if (WildClient.INSTANCE.configManager != null) {
               WildClient.INSTANCE.configManager.scheduleSave();
            }
         } else {
            LegacyClickGuiState.keybindSetting.keyCode = i;
            LegacyClickGuiState.keybindSetting.waitingForBind = false;
            LegacyClickGuiState.keybindSetting = null;
            if (WildClient.INSTANCE.configManager != null) {
               WildClient.INSTANCE.configManager.scheduleSave();
            }
         }

         return true;
      } else {
         if (LegacyClickGuiState.textSetting != null) {
            if (i == 256) {
               LegacyClickGuiState.textSetting.flag = false;
               LegacyClickGuiState.textSetting = null;
               if (WildClient.INSTANCE.configManager != null) {
                  WildClient.INSTANCE.configManager.scheduleSave();
               }

               return true;
            }

            if (i == 259) {
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
         }

         if (LegacyClickGuiState.flag4) {
            if (i == 256) {
               LegacyClickGuiState.flag4 = false;
               LegacyClickGuiState.text = "";
               return true;
            }

            if (i == 261) {
               LegacyClickGuiState.text = "";
               return true;
            }

            if (i == 259) {
               if (LegacyClickGuiState.text != null && !LegacyClickGuiState.text.isEmpty()) {
                  if (flag) {
                     int intValue = LegacyClickGuiState.text.lastIndexOf(32);
                     LegacyClickGuiState.text = intValue < 0 ? "" : LegacyClickGuiState.text.substring(0, intValue);
                  } else {
                     LegacyClickGuiState.text = LegacyClickGuiState.text.substring(0, LegacyClickGuiState.text.length() - 1);
                  }

                  return true;
               }

               LegacyClickGuiState.text = "";
               return true;
            }
         }

         return false;
      }
   }
}
