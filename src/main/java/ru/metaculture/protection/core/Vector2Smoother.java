package ru.metaculture.protection;

public final class Vector2Smoother {
   private float floatValue;
   private float floatValue2;

   public Vector2Smoother(float f, float g) {
      this.floatValue = f;
      this.floatValue2 = g;
   }

   public void invoke(float f, float g, float h) {
      this.floatValue = this.measure(f, this.floatValue, h);
      this.floatValue2 = this.measure(g, this.floatValue2, h);
   }

   public void invoke2(float f, float g) {
      this.floatValue = this.measure(this.floatValue, f, 1.0F);
      this.floatValue2 = this.measure(this.floatValue2, g, 1.0F);
   }

   public float measure(float f, float g, float h) {
      if (h < 0.0F) {
         h = 0.0F;
      }

      if (h > 1.0F) {
         h = 1.0F;
      }

      float floatValue = f - g;
      float floatValue2 = Math.abs(floatValue) * h;
      return floatValue2 < 0.1F ? f : g + (floatValue > 0.0F ? floatValue2 : -floatValue2);
   }

   public float getFloatValue() {
      return this.floatValue;
   }

   public void setFloatValue(float f) {
      this.floatValue = f;
   }

   public float getFloatValue2() {
      return this.floatValue2;
   }

   public void setFloatValue2(float f) {
      this.floatValue2 = f;
   }
}
