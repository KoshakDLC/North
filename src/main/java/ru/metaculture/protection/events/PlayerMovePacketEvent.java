package ru.metaculture.protection;

import lombok.Generated;

public class PlayerMovePacketEvent extends Event {
   private double doubleValue;
   private double doubleValue2;
   private double doubleValue3;
   private double doubleValue4;
   private double doubleValue5;
   private boolean flag;

   @Generated
   public double getDoubleValue() {
      return this.doubleValue;
   }

   @Generated
   public double getDoubleValue2() {
      return this.doubleValue2;
   }

   @Generated
   public double getDoubleValue3() {
      return this.doubleValue3;
   }

   @Generated
   public double getDoubleValue4() {
      return this.doubleValue4;
   }

   @Generated
   public double getDoubleValue5() {
      return this.doubleValue5;
   }

   @Generated
   public boolean isFlag() {
      return this.flag;
   }

   @Generated
   public PlayerMovePacketEvent setDoubleValue(double d) {
      this.doubleValue = d;
      return this;
   }

   @Generated
   public PlayerMovePacketEvent setDoubleValue2(double d) {
      this.doubleValue2 = d;
      return this;
   }

   @Generated
   public PlayerMovePacketEvent setDoubleValue3(double d) {
      this.doubleValue3 = d;
      return this;
   }

   @Generated
   public PlayerMovePacketEvent setDoubleValue4(double d) {
      this.doubleValue4 = d;
      return this;
   }

   @Generated
   public PlayerMovePacketEvent setDoubleValue5(double d) {
      this.doubleValue5 = d;
      return this;
   }

   @Generated
   public PlayerMovePacketEvent setFlag(boolean bl) {
      this.flag = bl;
      return this;
   }

   @Generated
   public PlayerMovePacketEvent(double d, double e, double f, double g, double h, boolean bl) {
      this.doubleValue = d;
      this.doubleValue2 = e;
      this.doubleValue3 = f;
      this.doubleValue4 = g;
      this.doubleValue5 = h;
      this.flag = bl;
   }
}
