package ru.metaculture.protection;

public class TimedAnimation {
   private AnimationMode animationMode;
   private long timestamp;
   private long timestamp2;
   private long timestamp3;
   private double doubleValue;
   private double doubleValue2;
   private double doubleValue3;
   private boolean flag;

   public TimedAnimation(AnimationMode animationMode, long l) {
      this.animationMode = animationMode;
      this.timestamp3 = System.currentTimeMillis();
      this.timestamp = l;
   }

   public void setTimestamp2(double d) {
      this.timestamp2 = System.currentTimeMillis();
      if (this.timestamp <= 0L) {
         this.doubleValue2 = d;
         this.doubleValue = d;
         this.doubleValue3 = d;
         this.flag = true;
      } else {
         if (this.doubleValue2 != d) {
            this.doubleValue2 = d;
            this.invoke();
         } else {
            this.flag = this.timestamp2 - this.timestamp3 >= this.timestamp;
            if (this.flag) {
               this.doubleValue3 = d;
               return;
            }
         }

         double doubleValue = this.measure();
         double doubleValue2 = this.animationMode.getFunction().apply(doubleValue);
         if (this.doubleValue3 > d) {
            this.doubleValue3 = this.doubleValue - (this.doubleValue - d) * doubleValue2;
         } else {
            this.doubleValue3 = this.doubleValue + (d - this.doubleValue) * doubleValue2;
         }

         if (doubleValue >= 1.0) {
            this.doubleValue3 = d;
            this.flag = true;
         }
      }
   }

   public double measure() {
      if (this.timestamp <= 0L) {
         return 1.0;
      } else {
         double doubleValue3 = (double)(System.currentTimeMillis() - this.timestamp3) / this.timestamp;
         return Math.max(0.0, Math.min(1.0, doubleValue3));
      }
   }

   public void invoke() {
      this.timestamp3 = System.currentTimeMillis();
      this.doubleValue = this.doubleValue3;
      this.flag = false;
   }

   public AnimationMode getAnimationMode() {
      return this.animationMode;
   }

   public void setAnimationMode(AnimationMode animationMode2) {
      this.animationMode = animationMode2;
   }

   public long getTimestamp() {
      return this.timestamp;
   }

   public void setTimestamp(long l) {
      this.timestamp = l;
   }

   public long getTimestamp2() {
      return this.timestamp2;
   }

   public void setTimestamp22(long l) {
      this.timestamp2 = l;
   }

   public long getTimestamp3() {
      return this.timestamp3;
   }

   public void setTimestamp3(long l) {
      this.timestamp3 = l;
   }

   public double getDoubleValue() {
      return this.doubleValue;
   }

   public void setDoubleValue(double d) {
      this.doubleValue = d;
   }

   public double getDoubleValue2() {
      return this.doubleValue2;
   }

   public void setDoubleValue2(double d) {
      this.doubleValue2 = d;
   }

   public double getDoubleValue3() {
      return this.doubleValue3;
   }

   public void setDoubleValue3(double d) {
      this.doubleValue3 = d;
   }

   public boolean isFlag() {
      return this.flag;
   }

   public void setFlag(boolean bl) {
      this.flag = bl;
   }
}
