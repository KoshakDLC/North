package ru.metaculture.protection;

import java.util.function.Supplier;

public class Setting extends SettingContainer {
   public String name;
   private String customName;
   public Supplier<Boolean> visibilityCondition = () -> false;
   public boolean configTransient = false;

   public String getName() {
      return this.customName != null && !this.customName.isBlank() ? this.customName : this.name;
   }

   public Setting withName(String string) {
      this.customName = string;
      return this;
   }

   public Setting setConfigTransient(boolean bl) {
      this.configTransient = bl;
      return this;
   }

   public void resetToDefault() {
   }
}
