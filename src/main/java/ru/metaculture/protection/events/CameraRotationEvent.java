package ru.metaculture.protection;

public class CameraRotationEvent extends Event {
   public float floatValue;
   public float floatValue2;
   public float floatValue3;

   public CameraRotationEvent(float f, float g, float h) {
      this.floatValue = f;
      this.floatValue2 = g;
      this.floatValue3 = h;
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

   public float getFloatValue3() {
      return this.floatValue3;
   }

   public void setFloatValue3(float f) {
      this.floatValue3 = f;
   }
}
