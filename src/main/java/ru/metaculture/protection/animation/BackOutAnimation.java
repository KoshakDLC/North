package ru.metaculture.protection;

public class BackOutAnimation extends DirectionalAnimation {
   private final float floatValue;

   public BackOutAnimation(int i, double d, float f) {
      super(i, d);
      this.floatValue = f;
   }

   public BackOutAnimation(int i, double d, float f, AnimationDirection animationDirection) {
      super(i, d, animationDirection);
      this.floatValue = f;
   }

   @Override
   protected boolean check() {
      return true;
   }

   @Override
   protected double measure(double d) {
      double doubleValue = d / this.intValue;
      float floatValue = this.floatValue + 1.0F;
      return Math.max(0.0, 1.0 + floatValue * Math.pow(doubleValue - 1.0, 3.0) + this.floatValue * Math.pow(doubleValue - 1.0, 2.0));
   }
}
