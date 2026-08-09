package ru.metaculture.protection;

public class MouseSensitivityUtils implements MinecraftAccessor {
   public static float measure(float f) {
      return measure4(f) * measure2();
   }

   public static float measure2() {
      return (float)(measure3() * 0.15);
   }

   public static float measure3() {
      float floatValue;
      return (floatValue = (float)((Double)a_.options.getMouseSensitivity().getValue() * 0.6 + 0.2)) * floatValue * floatValue * 8.0F;
   }

   public static float measure4(float f) {
      return Math.round(f / measure2());
   }
}
