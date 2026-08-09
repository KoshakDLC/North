package ru.metaculture.protection;

public class MovementInputEvent extends Event {
   private float floatValue;
   private float floatValue2;
   private boolean flag;
   private boolean flag2;
   private boolean flag3;
   private double doubleValue;

   public MovementInputEvent(float f, float g, boolean bl, boolean bl2, double d) {
      this(f, g, bl, bl2, false, d);
   }

   public MovementInputEvent(float f, float g, boolean bl, boolean bl2, boolean bl3, double d) {
      this.floatValue = f;
      this.floatValue2 = g;
      this.flag = bl;
      this.flag2 = bl2;
      this.flag3 = bl3;
      this.doubleValue = d;
   }

   public float getFloatValue() {
      return this.floatValue;
   }

   public void setFloatValue(float f) {
      this.floatValue = f;
   }

   public float getFloatValue2() {
      return this.floatValue2;
   }

   public void setFloatValue2(float f) {
      this.floatValue2 = f;
   }

   public boolean isFlag() {
      return this.flag;
   }

   public void setFlag(boolean bl) {
      this.flag = bl;
   }

   public boolean isFlag2() {
      return this.flag2;
   }

   public void setFlag2(boolean bl) {
      this.flag2 = bl;
   }

   public boolean isFlag3() {
      return this.flag3;
   }

   public void setFlag3(boolean bl) {
      this.flag3 = bl;
   }

   public double getDoubleValue() {
      return this.doubleValue;
   }

   public void setDoubleValue(double d) {
      this.doubleValue = d;
   }
}
