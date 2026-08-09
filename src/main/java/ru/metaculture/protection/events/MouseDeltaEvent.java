package ru.metaculture.protection;

public class MouseDeltaEvent extends Event {
   public double doubleValue;
   public double doubleValue2;

   public MouseDeltaEvent(double d, double e) {
      this.doubleValue = d;
      this.doubleValue2 = e;
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
}
