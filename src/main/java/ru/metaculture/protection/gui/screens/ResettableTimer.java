package ru.metaculture.protection;

import lombok.Generated;

public class ResettableTimer {
   public long timestamp = System.currentTimeMillis();

   public ResettableTimer() {
      this.invoke();
   }

   public void invoke() {
      this.timestamp = System.currentTimeMillis();
   }

   public boolean check(long l) {
      return System.currentTimeMillis() - this.timestamp > l;
   }

   public void setTimestamp(long l) {
      this.timestamp = System.currentTimeMillis() + l;
   }

   public void setTimestamp2(long l) {
      this.timestamp = l;
   }

   public long compute() {
      return System.currentTimeMillis() - this.timestamp;
   }

   public boolean check2() {
      return System.currentTimeMillis() - this.timestamp <= 0L;
   }

   public boolean check3(long l) {
      return System.currentTimeMillis() - this.timestamp > l;
   }

   public boolean check4() {
      return this.timestamp < System.currentTimeMillis();
   }

   public boolean check5(long l, boolean bl) {
      boolean flag = System.currentTimeMillis() - this.timestamp >= l;
      if (flag && bl) {
         this.invoke();
      }

      return flag;
   }

   @Generated
   public long getTimestamp() {
      return this.timestamp;
   }
}
