package ru.metaculture.protection;

public class ColorInterpolator {
   public int compute(int i, int j, double d) {
      if (d < 0.0) {
         d = 0.0;
      }

      if (d > 1.0) {
         d = 1.0;
      }

      int intValue = i >> 24 & 0xFF;
      int intValue2 = j >> 24 & 0xFF;
      if (intValue == 0) {
         intValue = 255;
      }

      if (intValue2 == 0) {
         intValue2 = 255;
      }

      int intValue3 = (int)Math.round(intValue + (intValue2 - intValue) * d);
      return intValue3 << 24 | ColorUtils.compute16(i, j, (float)d);
   }

   public int compute2(int i, int j, int k) {
      return this.compute4(i, j, k, 255);
   }

   public static int compute3(int i, double d) {
      int intValue4 = (int)Math.round(d * 255.0);
      int intValue5 = i & 16777215;
      return intValue4 << 24 | intValue5;
   }

   public int compute4(int i, int j, int k, int l) {
      return (l & 0xFF) << 24 | (i & 0xFF) << 16 | (j & 0xFF) << 8 | k & 0xFF;
   }

   public static int compute5(int i, int j, int k, int l) {
      return (l & 0xFF) << 24 | (i & 0xFF) << 16 | (j & 0xFF) << 8 | k & 0xFF;
   }

   public static int compute6(int i) {
      return i >>> 24 & 0xFF;
   }

   public static int compute7(int i) {
      return i >>> 16 & 0xFF;
   }

   public static int compute8(int i) {
      return i >>> 8 & 0xFF;
   }

   public static int compute9(int i) {
      return i & 0xFF;
   }

   public static int compute10(int i, int j, float f) {
      if (f <= 0.0F) {
         return i;
      } else if (f >= 1.0F) {
         return j;
      } else {
         int intValue6 = i >>> 24 & 0xFF;
         int intValue7 = j >>> 24 & 0xFF;
         int intValue8 = Math.round(intValue6 + (intValue7 - intValue6) * f);
         return (intValue8 & 0xFF) << 24 | ColorUtils.compute16(i, j, f);
      }
   }
}
