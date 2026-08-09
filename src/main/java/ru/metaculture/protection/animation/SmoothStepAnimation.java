package ru.metaculture.protection;

public class SmoothStepAnimation extends DirectionalAnimation {
   public SmoothStepAnimation(int i, double d) {
      super(i, d);
   }

   public SmoothStepAnimation(int i, double d, AnimationDirection animationDirection) {
      super(i, d, animationDirection);
   }

   @Override
   protected double measure(double d) {
      double doubleValue = d / this.intValue;
      return -2.0 * Math.pow(doubleValue, 3.0) + 3.0 * Math.pow(doubleValue, 2.0);
   }
}
