package ru.metaculture.protection;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class GroupSetting extends Setting {
   public List<BooleanSetting> options;
   public boolean expanded;
   public Animation animation = new Animation();

   public GroupSetting(String string, BooleanSetting... booleanSettings) {
      this.name = string;
      this.options = Arrays.asList(booleanSettings);
   }

   public boolean isEnabled(String string) {
      for (BooleanSetting booleanSetting : this.options) {
         if (booleanSetting.name.equals(string)) {
            return booleanSetting.isEnabled();
         }
      }

      return false;
   }

   public boolean isEnabled(int i) {
      return i >= 0 && i < this.options.size() ? this.options.get(i).isEnabled() : false;
   }

   public GroupSetting visibleWhen(Supplier<Boolean> supplier) {
      this.visibilityCondition = supplier;
      return this;
   }

   @Override
   public void resetToDefault() {
      this.expanded = false;

      for (BooleanSetting booleanSetting2 : this.options) {
         if (booleanSetting2 != null) {
            booleanSetting2.resetToDefault();
         }
      }
   }
}
