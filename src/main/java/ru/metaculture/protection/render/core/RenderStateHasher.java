package ru.metaculture.protection;

public final class RenderStateHasher {
   private static final long TIMESTAMP = -3750763034362895579L;
   private static final long TIMESTAMP_2 = 1099511628211L;
   private long timestamp;

   public RenderStateHasher() {
      this.setTimestamp(System.nanoTime() ^ compute("wild-1.21.8-1783538716222"));
   }

   public void setTimestamp(long l) {
      this.timestamp = -3750763034362895579L;
      this.invoke2(l);
   }

   public void invoke(int i) {
      this.timestamp ^= i & 255L;
      this.timestamp *= 1099511628211L;
      this.timestamp ^= i >>> 8 & 255L;
      this.timestamp *= 1099511628211L;
      this.timestamp ^= i >>> 16 & 255L;
      this.timestamp *= 1099511628211L;
      this.timestamp ^= i >>> 24 & 255L;
      this.timestamp *= 1099511628211L;
   }

   public void invoke2(long l) {
      this.invoke((int)l);
      this.invoke((int)(l >>> 32));
   }

   public void invoke3(float f) {
      this.invoke(Float.floatToRawIntBits(f));
   }

   public long getTimestamp() {
      return this.timestamp;
   }

   static long compute(String string) {
      long longValue = -3750763034362895579L;
      if (string == null) {
         return longValue;
      } else {
         for (int intValue = 0; intValue < string.length(); intValue++) {
            longValue ^= string.charAt(intValue);
            longValue *= 1099511628211L;
         }

         return longValue;
      }
   }
}
