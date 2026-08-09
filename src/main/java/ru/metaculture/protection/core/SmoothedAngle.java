package ru.metaculture.protection;

public class SmoothedAngle {
   long timestamp;
   public float floatValue;
   public float floatValue2;
   public float floatValue3;

   public SmoothedAngle(float f, float g, float h) {
      this.floatValue = f;
      this.floatValue2 = g;
      this.floatValue3 = h;
      this.timestamp = System.currentTimeMillis();
   }

   public float measure() {
      if (Math.abs(this.floatValue - this.floatValue2) < 1.0E-4) {
         this.floatValue = this.floatValue2;
      }

      int intValue;
      if ((intValue = (int)(Math.min((float)(System.currentTimeMillis() - this.timestamp), 400.0F) / 5.0F)) > 0) {
         this.timestamp = System.currentTimeMillis();
      }

      for (int intValue2 = 0; intValue2 < intValue; intValue2++) {
         this.floatValue = RenderMath.measure43(this.floatValue, this.floatValue2, this.floatValue3);
      }

      return this.floatValue;
   }

   public float measure2() {
      if (Math.abs(this.floatValue - this.floatValue2) > 1.0E-4) {
         int intValue3 = (int)(Math.min((float)(System.currentTimeMillis() - this.timestamp), 400.0F) / 5.0F);
         if (intValue3 > 0) {
            this.timestamp = System.currentTimeMillis();
         }

         for (int intValue4 = 0; intValue4 < intValue3; intValue4++) {
            this.floatValue = (float)this.measure3(this.floatValue, this.floatValue2, this.floatValue3);
         }
      }

      return RenderMath.measure8(this.floatValue);
   }

   public void setFloatValue(float f) {
      this.floatValue = f;
      this.timestamp = System.currentTimeMillis();
   }

   double measure3(float f, float g, float h) {
      float floatValue = (g - f + 180.0F) % 360.0F - 180.0F;
      return floatValue * h + f;
   }
}
