package ru.metaculture.protection;

import java.util.function.Supplier;

public class SpacerSetting extends Setting {
   public float floatValue;
   private final float floatValue2;

   public SpacerSetting(float f) {
      this.floatValue = f;
      this.floatValue2 = f;
   }

   public SpacerSetting() {
      this.floatValue = 15.0F;
      this.floatValue2 = 15.0F;
   }

   public SpacerSetting setVisibilityCondition(Supplier<Boolean> supplier) {
      this.visibilityCondition = supplier;
      return this;
   }

   public float getFloatValue() {
      return this.floatValue;
   }

   @Override
   public void resetToDefault() {
      this.floatValue = this.floatValue2;
   }
}
