package ru.metaculture.protection;

public final class FloatEasings {
   public static final FloatEasing FLOAT_EASING = f -> {
      float floatValue = measure(f);
      float floatValue2 = 1.0F - floatValue;
      return 1.0F - floatValue2 * floatValue2 * floatValue2;
   };
   public static final FloatEasing FLOAT_EASING_2 = f -> {
      float floatValue3 = measure(f);
      if (floatValue3 < 0.5F) {
         float floatValue4 = floatValue3 * 2.0F;
         return 0.5F * floatValue4 * floatValue4 * floatValue4 * floatValue4 * floatValue4;
      } else {
         float floatValue5 = (floatValue3 - 0.5F) * 2.0F;
         float floatValue6 = 1.0F - floatValue5;
         return 1.0F - 0.5F * floatValue6 * floatValue6 * floatValue6 * floatValue6 * floatValue6;
      }
   };
   public static final FloatEasing FLOAT_EASING_3 = f -> {
      float floatValue7 = measure(f);
      return floatValue7 * floatValue7 * (3.0F - 2.0F * floatValue7);
   };

   private FloatEasings() {
   }

   private static float measure(float f) {
      if (f <= 0.0F) {
         return 0.0F;
      } else {
         return f >= 1.0F ? 1.0F : f;
      }
   }
}
