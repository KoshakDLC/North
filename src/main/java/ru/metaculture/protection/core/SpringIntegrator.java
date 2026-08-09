package ru.metaculture.protection;

public final class SpringIntegrator {
   private final SpringSpec springSpec;
   private float floatValue;
   private float floatValue2;

   public SpringIntegrator(SpringSpec springSpec) {
      this.springSpec = springSpec;
   }

   public void setFloatValue(float f) {
      this.floatValue = f;
      this.floatValue2 = 0.0F;
   }

   public float measure(float f, float g) {
      if (Float.isNaN(f) || Float.isInfinite(f)) {
         f = this.floatValue;
      }

      if (!Float.isNaN(g) && !Float.isInfinite(g) && !(g <= 0.0F)) {
         float floatValue = Math.max(0.05F, Math.min(4.0F, g * 60.0F));
         this.floatValue2 = this.floatValue2 + (f - this.floatValue) * this.springSpec.getFloatValue() * floatValue;
         this.floatValue2 = this.floatValue2 * (float)Math.pow(this.springSpec.getFloatValue2(), floatValue);
         this.floatValue = this.floatValue + this.floatValue2 * floatValue;
         if (!Float.isNaN(this.floatValue) && !Float.isInfinite(this.floatValue) && !Float.isNaN(this.floatValue2) && !Float.isInfinite(this.floatValue2)) {
            if (Math.abs(f - this.floatValue) <= this.springSpec.getFloatValue3() && Math.abs(this.floatValue2) <= this.springSpec.getFloatValue4()) {
               this.floatValue = f;
               this.floatValue2 = 0.0F;
            }

            return this.floatValue;
         } else {
            this.floatValue = f;
            this.floatValue2 = 0.0F;
            return this.floatValue;
         }
      } else {
         return this.floatValue;
      }
   }

   public float getFloatValue() {
      return this.floatValue;
   }

   public float getFloatValue2() {
      return this.floatValue2;
   }
}
