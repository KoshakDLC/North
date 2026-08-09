package ru.metaculture.protection;

public final class SpringConfig {
   private final float floatValue;
   private final float floatValue2;

   private SpringConfig(float f, float g) {
      if (f <= 0.0F) {
         throw new IllegalArgumentException("frequencyHz must be > 0");
      } else if (g <= 0.0F) {
         throw new IllegalArgumentException("dampingRatio must be > 0");
      } else {
         this.floatValue = f;
         this.floatValue2 = g;
      }
   }

   public static SpringConfig resolve(float f, float g) {
      return new SpringConfig(f, g);
   }

   public float getFloatValue() {
      return this.floatValue;
   }

   public float getFloatValue2() {
      return this.floatValue2;
   }
}
