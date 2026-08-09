package ru.metaculture.protection;

public abstract class DirectionalAnimation {
   public AnimationTimer animationTimer = new AnimationTimer();
   protected int intValue;
   protected double doubleValue;
   protected AnimationDirection animationDirection;

   public DirectionalAnimation(int i, double d) {
      this.intValue = i;
      this.doubleValue = d;
      this.animationDirection = AnimationDirection.FORWARDS;
   }

   public DirectionalAnimation(int i, double d, AnimationDirection animationDirection) {
      this.intValue = i;
      this.doubleValue = d;
      this.animationDirection = animationDirection;
   }

   public boolean check2(AnimationDirection animationDirection2) {
      return this.check3() && this.animationDirection.equals(animationDirection2);
   }

   public double measure2() {
      return 1.0 - (double)this.animationTimer.compute() / this.intValue * this.doubleValue;
   }

   public double getDoubleValue() {
      return this.doubleValue;
   }

   public void setDoubleValue(double d) {
      this.doubleValue = d;
   }

   public void invoke() {
      this.animationTimer.invoke();
   }

   public boolean check3() {
      return this.animationTimer.check((double)this.intValue);
   }

   public void invoke2() {
      this.invoke3(this.animationDirection.resolve());
   }

   public AnimationDirection getAnimationDirection() {
      return this.animationDirection;
   }

   public void invoke3(AnimationDirection animationDirection3) {
      if (this.animationDirection != animationDirection3) {
         this.animationDirection = animationDirection3;
         this.animationTimer.setTimestamp(System.currentTimeMillis() - (this.intValue - Math.min((long)this.intValue, this.animationTimer.compute())));
      }
   }

   public void setIntValue(int i) {
      this.intValue = i;
   }

   protected boolean check() {
      return false;
   }

   public long compute() {
      return this.animationTimer.compute();
   }

   public float measure3() {
      if (this.animationDirection == AnimationDirection.FORWARDS) {
         return this.check3() ? (float)this.doubleValue : (float)(this.measure(this.animationTimer.compute()) * this.doubleValue);
      } else if (this.check3()) {
         return 0.0F;
      } else if (this.check()) {
         double doubleValue = Math.min((long)this.intValue, Math.max(0L, this.intValue - this.animationTimer.compute()));
         return (float)(this.measure(doubleValue) * this.doubleValue);
      } else {
         return (float)((1.0 - this.measure(this.animationTimer.compute())) * this.doubleValue);
      }
   }

   protected abstract double measure(double d);
}
