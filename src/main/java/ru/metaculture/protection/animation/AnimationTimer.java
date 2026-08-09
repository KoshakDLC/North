package ru.metaculture.protection;

public class AnimationTimer {
   private long timestamp = -1L;

   public AnimationTimer() {
      this.timestamp = System.currentTimeMillis();
   }

   public boolean check(double d) {
      return System.currentTimeMillis() - this.timestamp >= d;
   }

   public boolean check2(boolean bl, double d) {
      return bl || this.check(d);
   }

   public long getTimestamp() {
      return this.timestamp;
   }

   public void invoke() {
      this.timestamp = System.currentTimeMillis();
   }

   public long compute() {
      return System.currentTimeMillis() - this.timestamp;
   }

   public long compute2() {
      return System.nanoTime() / 1000000L;
   }

   public void setTimestamp(long l) {
      this.timestamp = l;
   }
}
