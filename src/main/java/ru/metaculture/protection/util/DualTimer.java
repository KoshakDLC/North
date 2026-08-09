package ru.metaculture.protection;

import lombok.Generated;

public class DualTimer {
   private long timestamp;
   public long timestamp2 = System.currentTimeMillis();

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

   public boolean check3(double d) {
      boolean flag = this.check2(d);
      if (flag) {
         this.invoke();
      }

      return flag;
   }

   public long compute() {
      return System.currentTimeMillis() - this.timestamp;
   }

   public void setTimestamp(long l) {
      this.timestamp = System.currentTimeMillis() - l;
   }

   public long compute2() {
      return System.currentTimeMillis() - this.timestamp2;
   }

   public boolean check4() {
      return System.currentTimeMillis() - this.timestamp2 <= 0L;
   }

   public boolean check5(long l) {
      return System.currentTimeMillis() - this.timestamp2 > l;
   }

   public boolean check6() {
      return this.timestamp2 < System.currentTimeMillis();
   }

   public boolean check7(long l, boolean bl) {
      if (System.currentTimeMillis() - this.timestamp2 > l) {
         if (bl) {
            this.invoke();
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean check8(double d) {
      return this.compute3() >= d;
   }

   public long compute3() {
      return System.currentTimeMillis() - this.timestamp2;
   }

   public long compute4(int i) {
      return System.currentTimeMillis() + i;
   }

   public long getTimestamp2() {
      return this.timestamp2;
   }

   public void invoke2() {
      this.timestamp2 = System.currentTimeMillis();
   }

   public boolean check9(long l) {
      return System.currentTimeMillis() - this.timestamp2 >= l;
   }

   public boolean check10(long l) {
      if (System.currentTimeMillis() - this.timestamp2 >= l) {
         this.invoke();
         return true;
      } else {
         return false;
      }
   }

   public boolean check11(long l) {
      return System.currentTimeMillis() - this.timestamp2 >= l;
   }

   @Generated
   public long getTimestamp() {
      return this.timestamp;
   }

   public static class DualTimerState {
      private long timestamp;

      private DualTimerState() {
         this.invoke();
      }

      public static DualTimer.DualTimerState resolve() {
         return new DualTimer.DualTimerState();
      }

      public void invoke() {
         this.timestamp = System.currentTimeMillis();
      }

      public long compute() {
         return System.currentTimeMillis() - this.timestamp;
      }

      public boolean check(long l) {
         return this.compute() >= l;
      }

      public boolean check2(long l, boolean bl) {
         boolean flag2 = this.compute() >= l;
         if (flag2 && bl) {
            this.invoke();
         }

         return flag2;
      }

      public boolean check3(double d) {
         return this.compute() >= d;
      }

      public boolean check4(long l) {
         boolean flag3 = this.compute() - l >= 0L;
         if (flag3) {
            this.invoke();
         }

         return flag3;
      }

      @Generated
      public void setTimestamp(long l) {
         this.timestamp = l;
      }
   }

   public static class DualTimerState2 {
      public long timestamp;
      public long timestamp2 = System.currentTimeMillis();

      public void invoke() {
         this.timestamp2 = System.currentTimeMillis();
      }

      public long compute() {
         return System.currentTimeMillis() - this.timestamp2;
      }

      public boolean check(long l) {
         return System.currentTimeMillis() - this.timestamp2 > l;
      }

      public boolean check2(float f) {
         long longValue = (long)(f * 1000.0F);
         return System.currentTimeMillis() - this.timestamp2 > longValue;
      }

      public boolean check3(double d) {
         return System.currentTimeMillis() - d >= this.timestamp;
      }

      public boolean check4(long l, boolean bl) {
         boolean flag4 = this.compute() >= l;
         if (flag4 && bl) {
            this.invoke();
         }

         return flag4;
      }

      private long compute2() {
         return System.currentTimeMillis();
      }

      public long compute3() {
         return this.compute2() - this.timestamp2;
      }

      @Generated
      public void setTimestamp(long l) {
         this.timestamp = l;
      }

      @Generated
      public void setTimestamp2(long l) {
         this.timestamp2 = l;
      }
   }
}
