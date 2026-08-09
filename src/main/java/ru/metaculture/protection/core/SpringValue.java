package ru.metaculture.protection;

import lombok.Generated;

public final class SpringValue {
   private static final float FLOAT_VALUE = 0.004166667F;
   private static final float FLOAT_VALUE_2 = 0.25F;
   private static final int INT_VALUE = 60;
   private float floatValue;
   private float floatValue2;
   private float floatValue3;
   private long timestamp = Long.MIN_VALUE;

   public SpringValue(float f) {
      this.floatValue = f;
   }

   public float measure(float f, SpringSpec springSpec) {
      AnimationSystem animationSystem = AnimationSystem.getINSTANCE();
      long longValue = animationSystem.getTimestamp2();
      if (longValue == this.timestamp) {
         return this.floatValue;
      } else {
         this.timestamp = longValue;
         float floatValue = animationSystem.getFloatValue();
         if (!Float.isFinite(floatValue) || floatValue <= 0.0F) {
            floatValue = 0.004166667F;
         } else if (floatValue > 0.25F) {
            floatValue = 0.25F;
         }

         this.floatValue3 += floatValue;
         int intValue = 0;

         while (this.floatValue3 >= 0.004166667F && intValue < 60) {
            this.invoke(f, springSpec);
            this.floatValue3 -= 0.004166667F;
            intValue++;
            if (this.check(f, springSpec)) {
               this.invoke2(f);
               break;
            }
         }

         if (intValue == 60) {
            this.floatValue3 = 0.0F;
         }

         return this.floatValue;
      }
   }

   private void invoke(float f, SpringSpec springSpec2) {
      this.floatValue2 = this.floatValue2 + ((f - this.floatValue) * springSpec2.getFloatValue() - this.floatValue2 * springSpec2.getFloatValue2());
      this.floatValue = this.floatValue + this.floatValue2;
   }

   public void invoke2(float f) {
      this.floatValue = f;
      this.floatValue2 = 0.0F;
      this.floatValue3 = 0.0F;
   }

   public boolean check(float f, SpringSpec springSpec3) {
      return Math.abs(f - this.floatValue) <= springSpec3.getFloatValue3() && Math.abs(this.floatValue2) <= springSpec3.getFloatValue4();
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
   public float getFloatValue3() {
      return this.floatValue3;
   }

   @Generated
   public long getTimestamp() {
      return this.timestamp;
   }

   @Generated
   public void setFloatValue(float f) {
      this.floatValue = f;
   }

   @Generated
   public void setFloatValue2(float f) {
      this.floatValue2 = f;
   }

   @Generated
   public void setFloatValue3(float f) {
      this.floatValue3 = f;
   }

   @Generated
   public void setTimestamp(long l) {
      this.timestamp = l;
   }
}
