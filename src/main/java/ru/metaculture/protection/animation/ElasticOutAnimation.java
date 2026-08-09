package ru.metaculture.protection;

public class ElasticOutAnimation extends DirectionalAnimation {
   float floatValue;
   float floatValue2;
   boolean flag;

   public ElasticOutAnimation(int i, double d, float f, float g, boolean bl) {
      super(i, d);
      this.floatValue = f;
      this.floatValue2 = g;
      this.flag = bl;
   }

   public ElasticOutAnimation(int i, double d, float f, float g, boolean bl, AnimationDirection animationDirection) {
      super(i, d, animationDirection);
      this.floatValue = f;
      this.floatValue2 = g;
      this.flag = bl;
   }

   @Override
   protected double measure(double d) {
      double doubleValue = Math.pow(d / this.intValue, this.floatValue2);
      double doubleValue2 = this.floatValue * 0.1F;
      return Math.pow(2.0, -10.0 * (this.flag ? Math.sqrt(doubleValue) : doubleValue)) * Math.sin((doubleValue - doubleValue2 / 4.0) * ((Math.PI * 2) / doubleValue2)) + 1.0;
   }
}
