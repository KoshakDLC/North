package ru.metaculture.protection;

import net.minecraft.client.util.math.Vector2f;
import net.minecraft.util.math.Box;

public class PlayerMotionEvent extends Event {
   private float floatValue;
   private float floatValue2;
   private double doubleValue;
   private double doubleValue2;
   private double doubleValue3;
   private boolean flag;
   private Box box;
   Runnable runnable;

   public PlayerMotionEvent(float f, float g, double d, double e, double h, boolean bl, Box box, Runnable runnable) {
      this.floatValue = f;
      this.floatValue2 = g;
      this.doubleValue = d;
      this.doubleValue2 = e;
      this.doubleValue3 = h;
      this.flag = bl;
      this.box = box;
      this.runnable = runnable;
   }

   public void invoke(Vector2f vector2f) {
      this.setFloatValue(vector2f.getX());
      this.setFloatValue2(vector2f.getY());
   }

   public Box getBox() {
      return this.box;
   }

   public void setBox(Box box) {
      this.box = box;
   }

   public Runnable getRunnable() {
      return this.runnable;
   }

   public float getFloatValue() {
      return this.floatValue;
   }

   public float getFloatValue2() {
      return this.floatValue2;
   }

   public double getDoubleValue() {
      return this.doubleValue;
   }

   public double getDoubleValue2() {
      return this.doubleValue2;
   }

   public double getDoubleValue3() {
      return this.doubleValue3;
   }

   public boolean isFlag() {
      return this.flag;
   }

   public void setRunnable(Runnable runnable) {
      this.runnable = runnable;
   }

   public void setFloatValue(float f) {
      this.floatValue = f;
   }

   public void setFloatValue2(float f) {
      this.floatValue2 = f;
   }

   public void setDoubleValue(double d) {
      this.doubleValue = d;
   }

   public void setDoubleValue2(double d) {
      this.doubleValue2 = d;
   }

   public void setDoubleValue3(double d) {
      this.doubleValue3 = d;
   }

   public void setFlag(boolean bl) {
      this.flag = bl;
   }
}
