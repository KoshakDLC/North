package ru.metaculture.protection;

import lombok.Generated;

public class SlowdownEvent extends Event {
   private float floatValue;
   private float floatValue2;

   public SlowdownEvent(float f, float g) {
      this.floatValue = f;
      this.floatValue2 = g;
   }

   public void invoke() {
   }

   @Generated
   public float getFloatValue() {
      return this.floatValue;
   }

   @Generated
   public float getFloatValue2() {
      return this.floatValue2;
   }

   @Generated
   public void setFloatValue(float f) {
      this.floatValue = f;
   }

   @Generated
   public void setFloatValue2(float f) {
      this.floatValue2 = f;
   }
}
