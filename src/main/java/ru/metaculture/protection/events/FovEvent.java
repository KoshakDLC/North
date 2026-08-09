package ru.metaculture.protection;

public class FovEvent extends Event {
   float floatValue;

   public float getFloatValue() {
      return this.floatValue;
   }

   public float measure(float f) {
      return this.floatValue = f;
   }
}
