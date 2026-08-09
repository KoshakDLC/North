package ru.metaculture.protection;

import lombok.Generated;

public class DeadlineTimer {
   private long timestamp;
   public long timestamp2 = System.currentTimeMillis();

   public DeadlineTimer() {
      this.invoke();
   }

   public void invoke() {
      this.timestamp2 = System.currentTimeMillis();
   }

   public boolean check(long l) {
      return System.currentTimeMillis() - this.timestamp2 > l;
   }

   public void setTimestamp2(long l) {
      this.timestamp2 = System.currentTimeMillis() + l;
   }

   public void setTimestamp22(long l) {
      this.timestamp2 = l;
   }

   public boolean check2(double d) {
      return System.currentTimeMillis() - d >= this.timestamp;
   }

   public long compute() {
      return System.currentTimeMillis() - this.timestamp2;
   }

   public boolean check3() {
      return System.currentTimeMillis() - this.timestamp2 <= 0L;
   }

   public boolean check4(long l) {
      return System.currentTimeMillis() - this.timestamp2 > l;
   }

   public boolean check5() {
      return this.timestamp2 < System.currentTimeMillis();
   }

   public boolean check6(long l, boolean bl) {
      boolean flag = System.currentTimeMillis() - this.timestamp2 >= l;
      if (flag && bl) {
         this.invoke();
      }

      return flag;
   }

   @Generated
   public long getTimestamp() {
      return this.timestamp;
   }

   @Generated
   public long getTimestamp2() {
      return this.timestamp2;
   }
}
