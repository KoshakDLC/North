package ru.metaculture.protection;

public class EaseOutQuadAnimation extends DirectionalAnimation {
   public EaseOutQuadAnimation(int i, double d) {
      super(i, d);
   }

   public EaseOutQuadAnimation(int i, double d, AnimationDirection animationDirection) {
      super(i, d, animationDirection);
   }

   @Override
   protected double measure(double d) {
      double doubleValue = d / this.intValue;
      return 1.0 - (doubleValue - 1.0) * (doubleValue - 1.0);
   }
}
