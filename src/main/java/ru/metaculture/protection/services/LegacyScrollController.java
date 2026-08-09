package ru.metaculture.protection;

public class LegacyScrollController extends LegacyClickGuiState {
   public static void invoke(int i, int j) {
      float floatValue = LegacyClickGuiState.floatValue6;
      float floatValue2 = LegacyClickGuiState.floatValue7;
      float floatValue3 = 0.0F;

      for (Category category : LegacyClickGuiState.categorys) {
         if (LegacySearchOverlay.check(i, j, floatValue, floatValue2 + 43.365F + floatValue3 - 2.0F, 104.34F, 21.325F) && LegacyClickGuiState.category != category) {
            LegacyClickGuiState.directionalAnimation5.invoke3(AnimationDirection.BACKWARDS);
            LegacyClickGuiState.colorSetting = null;
            LegacyClickGuiState.category = category;
            LegacyClickGuiState.items = WildClient.INSTANCE.moduleManager.getModules(LegacyClickGuiState.category);
            LegacyClickGuiState.directionalAnimation2.invoke();
            LegacyClickGuiState.directionalAnimation3.invoke();
            LegacyClickGuiState.resolve().invoke6();
            WildClient.INSTANCE.themeManager.invoke3(category);
         }

         floatValue3 += 24.0F;
      }
   }
}
