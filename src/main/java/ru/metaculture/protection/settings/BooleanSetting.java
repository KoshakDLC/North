package ru.metaculture.protection;

import java.util.function.Supplier;

public class BooleanSetting extends Setting {
   private boolean value;
   private final boolean defaultValue;
   public String description;
   public Animation animation = new Animation();
   public int bindKey = -1;
   public boolean holdToEnable = false;
   public boolean waitingForBind = false;

   public BooleanSetting(String string, boolean bl) {
      this.name = string;
      this.value = bl;
      this.defaultValue = bl;
      this.description = this.description;
   }

   public boolean isEnabled() {
      return this.bindKey != -1 && this.holdToEnable ? this.value || KeybindSetting.isPressed(this.bindKey) : this.value;
   }

   public boolean getValue() {
      return this.value;
   }

   public void setValue(boolean bl) {
      this.value = bl;
   }

   public BooleanSetting visibleWhen(Supplier<Boolean> supplier) {
      this.visibilityCondition = supplier;
      return this;
   }

   @Override
   public void resetToDefault() {
      this.setValue(this.defaultValue);
      this.bindKey = -1;
      this.holdToEnable = false;
      this.waitingForBind = false;
   }
}
