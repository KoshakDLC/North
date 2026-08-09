package ru.metaculture.protection;

import java.util.function.Supplier;

public class DynamicButtonSetting extends ButtonSetting {
   private final Supplier<String> supplier;
   private Runnable runnable;

   public DynamicButtonSetting(String string, int i, Supplier<String> supplier) {
      super(string, i);
      this.supplier = supplier;
   }

   @Override
   public String getRun() {
      String text = this.supplier == null ? null : this.supplier.get();
      return text != null && !text.isBlank() ? text : super.getRun();
   }

   @Override
   public void invoke8() {
      if (this.runnable != null) {
         this.runnable.run();
      }
   }

   public DynamicButtonSetting onClick(Runnable runnable) {
      this.runnable = runnable;
      return this;
   }

   public DynamicButtonSetting setVisibilityCondition(Supplier<Boolean> supplier) {
      this.visibilityCondition = supplier;
      return this;
   }
}
