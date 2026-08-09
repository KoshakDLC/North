package ru.metaculture.protection;

public class EaseInOutQuadAnimation extends DirectionalAnimation {
   public EaseInOutQuadAnimation(int i, double d) {
      super(i, d);
   }

   public EaseInOutQuadAnimation(int i, double d, AnimationDirection animationDirection) {
      super(i, d, animationDirection);
   }

   @Override
   protected double measure(double d) {
      double doubleValue = d / this.intValue;
      return doubleValue < 0.5 ? 2.0 * Math.pow(doubleValue, 2.0) : 1.0 - Math.pow(-2.0 * doubleValue + 2.0, 2.0) / 2.0;
   }
}
