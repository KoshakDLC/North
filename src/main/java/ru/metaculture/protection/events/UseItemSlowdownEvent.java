package ru.metaculture.protection;

import lombok.Generated;

public class UseItemSlowdownEvent extends Event {
   byte byteValue;

   @Generated
   public byte getByteValue() {
      return this.byteValue;
   }

   @Generated
   public void setByteValue(byte b) {
      this.byteValue = b;
   }

   @Generated
   public UseItemSlowdownEvent(byte b) {
      this.byteValue = b;
   }
}
