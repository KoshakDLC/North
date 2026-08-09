package ru.metaculture.protection;

@FunctionalInterface
public interface FloatEasing {
   float ease(float f);

   static FloatEasing resolve() {
      return f -> f;
   }

   default FloatEasing resolve2(FloatEasing floatEasing) {
      return floatEasing == null ? this : f -> floatEasing.ease(this.ease(f));
   }
}
