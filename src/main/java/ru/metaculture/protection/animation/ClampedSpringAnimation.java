package ru.metaculture.protection;

public final class ClampedSpringAnimation implements AnimationSystem.AnimationSystemPredicate {
   private static final float FLOAT_VALUE = 1.0E-4F;
   private static final float FLOAT_VALUE_2 = 0.016666668F;
   private static final float FLOAT_VALUE_3 = 0.1F;
   private final AnimationSystem animationSystem;
   private final SpringConfig springConfig;
   private final float floatValue;
   private final float floatValue2;
   private final float floatValue3;
   private final float floatValue4;
   private float floatValue5;
   private float floatValue6;
   private float floatValue7;
   private FloatEasing floatEasing = FloatEasing.resolve();

   public ClampedSpringAnimation(AnimationSystem animationSystem, SpringConfig springConfig, float f, float g, float h, float i, float j) {
      if (animationSystem == null) {
         throw new IllegalArgumentException("animationSystem must not be null");
      } else if (springConfig == null) {
         throw new IllegalArgumentException("config must not be null");
      } else if (g > h) {
         throw new IllegalArgumentException("minValue must be <= maxValue");
      } else if (!(i <= 0.0F) && !(j <= 0.0F)) {
         this.animationSystem = animationSystem;
         this.springConfig = springConfig;
         this.floatValue = g;
         this.floatValue2 = h;
         this.floatValue3 = i;
         this.floatValue4 = j;
         float floatValue = this.measure2(f);
         this.floatValue5 = floatValue;
         this.floatValue6 = floatValue;
         this.floatValue7 = 0.0F;
      } else {
         throw new IllegalArgumentException("tolerances must be > 0");
      }
   }

   public void setFloatEasing(FloatEasing floatEasing) {
      this.floatEasing = floatEasing == null ? FloatEasing.resolve() : floatEasing;
   }

   public void invoke(float f) {
      float floatValue2 = this.measure2(f);
      this.floatValue5 = floatValue2;
      this.floatValue6 = floatValue2;
      this.floatValue7 = 0.0F;
      this.animationSystem.invoke4(this);
   }

   public void invoke2(float f) {
      float floatValue3 = this.measure2(f);
      if (Math.abs(floatValue3 - this.floatValue6) <= this.floatValue3 * 0.25F) {
         this.floatValue6 = floatValue3;
         if (this.check()) {
            this.invoke(floatValue3);
         }
      } else {
         this.floatValue6 = floatValue3;
         this.animationSystem.invoke3(this);
      }
   }

   public float measure() {
      float floatValue4 = 0.0F;
      float floatValue5 = this.floatValue2 - this.floatValue;
      if (floatValue5 > 0.0F) {
         floatValue4 = (this.floatValue5 - this.floatValue) / floatValue5;
      }

      float floatValue6 = this.floatEasing.ease(measure3(floatValue4));
      return this.floatValue + floatValue6 * floatValue5;
   }

   public float getFloatValue5() {
      return this.floatValue5;
   }

   public float getFloatValue6() {
      return this.floatValue6;
   }

   public boolean check() {
      float floatValue7 = Math.abs(this.floatValue6 - this.floatValue5);
      return floatValue7 <= this.floatValue3 && Math.abs(this.floatValue7) <= this.floatValue4;
   }

   @Override
   public boolean check2(float f) {
      float floatValue8 = f;
      if (f < 1.0E-4F) {
         floatValue8 = 1.0E-4F;
      } else if (f > 0.1F) {
         floatValue8 = 0.1F;
      }

      boolean flag = true;

      while (floatValue8 > 0.0F && flag) {
         float floatValue9 = Math.min(floatValue8, 0.016666668F);
         flag = this.check3(floatValue9);
         floatValue8 -= floatValue9;
      }

      return flag;
   }

   private boolean check3(float f) {
      float floatValue10 = (float)((Math.PI * 2) * this.springConfig.getFloatValue());
      float floatValue11 = 2.0F * this.springConfig.getFloatValue2() * floatValue10;
      float floatValue12 = floatValue10 * floatValue10;
      float floatValue13 = this.floatValue5 - this.floatValue6;
      float floatValue14 = -floatValue12 * floatValue13 - floatValue11 * this.floatValue7;
      this.floatValue7 += floatValue14 * f;
      this.floatValue5 = this.floatValue5 + this.floatValue7 * f;
      if (Float.isNaN(this.floatValue5) || Float.isInfinite(this.floatValue5) || Float.isNaN(this.floatValue7) || Float.isInfinite(this.floatValue7)) {
         this.floatValue5 = this.floatValue6;
         this.floatValue7 = 0.0F;
         return false;
      } else if (this.floatValue5 < this.floatValue) {
         this.floatValue5 = this.floatValue;
         this.floatValue7 = 0.0F;
         return false;
      } else if (this.floatValue5 > this.floatValue2) {
         this.floatValue5 = this.floatValue2;
         this.floatValue7 = 0.0F;
         return false;
      } else {
         float floatValue15 = this.floatValue5 - this.floatValue6;
         if ((!(floatValue13 > 0.0F) || !(floatValue15 < 0.0F)) && (!(floatValue13 < 0.0F) || !(floatValue15 > 0.0F))) {
            if (this.check()) {
               this.floatValue5 = this.floatValue6;
               this.floatValue7 = 0.0F;
               return false;
            } else {
               return true;
            }
         } else {
            this.floatValue5 = this.floatValue6;
            this.floatValue7 = 0.0F;
            return false;
         }
      }
   }

   private float measure2(float f) {
      if (f <= this.floatValue) {
         return this.floatValue;
      } else {
         return f >= this.floatValue2 ? this.floatValue2 : f;
      }
   }

   private static float measure3(float f) {
      if (f <= 0.0F) {
         return 0.0F;
      } else {
         return f >= 1.0F ? 1.0F : f;
      }
   }
}
