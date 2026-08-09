package ru.metaculture.protection;

import java.util.function.Supplier;

public class ButtonSetting extends Setting {
   public int intValue;
   public String text;
   private String run = "Run";
   private Runnable runnable;
   private final int intValue2;

   public ButtonSetting(String string, int i) {
      this.name = string;
      this.intValue = i;
      this.intValue2 = i;
   }

   public int getIntValue() {
      return this.intValue;
   }

   public void setIntValue(int i) {
      this.intValue = i;
   }

   public void invoke8() {
      this.intValue++;
      if (this.runnable != null) {
         this.runnable.run();
      }
   }

   public String getRun() {
      return this.run;
   }

   public ButtonSetting setRun(String string) {
      this.run = string;
      return this;
   }

   public ButtonSetting setRunnable(Runnable runnable) {
      this.runnable = runnable;
      return this;
   }

   public ButtonSetting setVisibilityCondition(Supplier<Boolean> supplier) {
      this.visibilityCondition = supplier;
      return this;
   }

   @Override
   public void resetToDefault() {
      this.intValue = this.intValue2;
   }
}
