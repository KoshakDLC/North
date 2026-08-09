package ru.metaculture.protection;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

public final class AuctionClickTracker {
   public static final long TIMESTAMP = 4000L;
   private static final int INT_VALUE = 32;
   private static final int INT_VALUE_2 = 10;
   private static final int INT_VALUE_3 = 6;
   private static final int INT_VALUE_4 = 3;
   private static final int INT_VALUE_5 = 10;
   private static final long TIMESTAMP_2 = 140L;
   private static final long TIMESTAMP_3 = 90L;
   private static final double DOUBLE_VALUE = 3.0;
   private static final double DOUBLE_VALUE_2 = 1.8;
   private static final double DOUBLE_VALUE_3 = 0.15;
   private static final long TIMESTAMP_4 = 150L;
   private static final double DOUBLE_VALUE_4 = 2.5;
   private final AtomicLong atomicLong = new AtomicLong();
   private volatile int intValue = -1;
   private final long[] longs = new long[32];
   private final long[] longs2 = new long[10];
   private int intValue2;
   private int intValue3;
   private double doubleValue = -1.0;
   private long timestamp;
   private long timestamp2;
   private int intValue4;
   private int intValue5;
   private boolean flag;
   private long timestamp3;

   public synchronized void invoke() {
      this.atomicLong.set(0L);
      this.intValue = -1;
      this.intValue2 = 0;
      this.intValue3 = 0;
      this.doubleValue = -1.0;
      this.timestamp = 0L;
      this.timestamp2 = 0L;
      this.intValue4 = 0;
      this.intValue5 = 0;
      this.flag = false;
      this.timestamp3 = 0L;
   }

   public synchronized void invoke2() {
      this.atomicLong.set(0L);
      this.intValue = -1;
      this.intValue2 = 0;
      this.intValue3 = 0;
      this.timestamp = 0L;
      this.timestamp2 = 0L;
      this.intValue4 = 0;
      this.intValue5 = 0;
      this.flag = false;
      this.timestamp3 = 0L;
   }

   public void invoke3() {
      this.atomicLong.set(0L);
      this.intValue = -1;
   }

   public void invoke4(int i) {
      if (this.atomicLong.get() == 0L) {
         this.intValue = i;
         this.atomicLong.compareAndSet(0L, System.currentTimeMillis());
      }
   }

   public void invoke5(int i) {
      if (i > 0) {
         this.invoke9();
      }
   }

   public void invoke6(int i) {
      if (i > 0 && i == this.intValue) {
         this.invoke9();
      }
   }

   public void invoke7(int i) {
      if (i > 0) {
         this.invoke9();
      }
   }

   public void invoke8() {
      long longValue = this.atomicLong.get();
      if (longValue != 0L) {
         if (System.currentTimeMillis() - longValue >= 4000L) {
            if (this.atomicLong.compareAndSet(longValue, 0L)) {
               this.intValue = -1;
               this.invoke10(4000L, true);
            }
         }
      }
   }

   private void invoke9() {
      long longValue2 = this.atomicLong.get();
      if (longValue2 != 0L) {
         if (this.atomicLong.compareAndSet(longValue2, 0L)) {
            this.intValue = -1;
            this.invoke10(Math.max(1L, System.currentTimeMillis() - longValue2), false);
         }
      }
   }

   private synchronized void invoke10(long l, boolean bl) {
      this.longs[this.intValue3] = l;
      this.intValue3 = (this.intValue3 + 1) % 32;
      if (this.intValue2 < 32) {
         this.intValue2++;
      }

      this.timestamp = l;
      this.intValue4 = bl ? this.intValue4 + 1 : 0;
      if (!bl && !this.flag && this.intValue5 == 0) {
         if (this.doubleValue < 0.0) {
            this.doubleValue = l;
         } else if (l <= Math.max(150.0, this.doubleValue * 2.5)) {
            this.doubleValue = this.doubleValue * 0.85 + l * 0.15;
         }
      }

      this.timestamp2 = this.compute();
      this.invoke11();
   }

   private long compute() {
      int intValue = Math.min(10, this.intValue2);

      for (int intValue2 = 0; intValue2 < intValue; intValue2++) {
         this.longs2[intValue2] = this.longs[(this.intValue3 - 1 - intValue2 + 64) % 32];
      }

      Arrays.sort(this.longs2, 0, intValue);
      return this.longs2[intValue / 2];
   }

   private void invoke11() {
      if (!(this.doubleValue < 0.0) && this.intValue2 >= 6) {
         long longValue3 = Math.max(140L, (long)(this.doubleValue * 3.0));
         long longValue4 = Math.max(90L, (long)(this.doubleValue * 1.8));
         if (this.timestamp2 >= longValue3) {
            this.intValue5++;
         } else if (this.timestamp2 <= longValue4) {
            this.intValue5 = 0;
         }

         if (!this.flag) {
            if (this.intValue5 >= 10 || this.intValue4 >= 3) {
               this.flag = true;
               this.timestamp3 = System.currentTimeMillis();
            }
         } else if (this.timestamp2 <= longValue4 && this.intValue4 == 0) {
            this.flag = false;
            this.intValue5 = 0;
            this.timestamp3 = 0L;
         }
      }
   }

   public synchronized boolean isFlag() {
      return this.flag;
   }

   public synchronized long compute2() {
      return this.flag ? System.currentTimeMillis() - this.timestamp3 : 0L;
   }

   public synchronized long getTimestamp2() {
      return this.timestamp2;
   }

   public synchronized long compute3() {
      return this.doubleValue < 0.0 ? 0L : Math.round(this.doubleValue);
   }

   public synchronized long getTimestamp() {
      return this.timestamp;
   }

   public synchronized int getIntValue4() {
      return this.intValue4;
   }

   public synchronized int getIntValue2() {
      return this.intValue2;
   }
}
