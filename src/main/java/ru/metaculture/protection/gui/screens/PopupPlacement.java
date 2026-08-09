package ru.metaculture.protection;

public final class PopupPlacement {
   private static final float FLOAT_VALUE = 0.001F;
   private final float floatValue;
   private final float floatValue2;
   private final float floatValue3;

   public PopupPlacement(float f, float g, float h) {
      if (!Float.isFinite(f) || f < 0.0F) {
         throw new IllegalArgumentException("positionMargin must be a non-negative finite value");
      } else if (!Float.isFinite(g) || g < 0.0F) {
         throw new IllegalArgumentException("cursorHorizontalOffset must be a non-negative finite value");
      } else if (Float.isFinite(h) && !(h < 0.0F)) {
         this.floatValue = f;
         this.floatValue2 = g;
         this.floatValue3 = h;
      } else {
         throw new IllegalArgumentException("cursorVerticalOffset must be a non-negative finite value");
      }
   }

   public PopupPlacement.PopupPlacementData resolve(double d, double e, float f, float g, int i, int j) {
      return this.resolve2(d, e, f, g, i, j, 1.0F);
   }

   public PopupPlacement.PopupPlacementData resolve2(double d, double e, float f, float g, int i, int j, float h) {
      float floatValue = Math.max(1, i);
      float floatValue2 = Math.max(1, j);
      float floatValue3 = Math.max(0.0F, f);
      float floatValue4 = Math.max(0.0F, g);
      float floatValue5 = measure8(h);
      float floatValue6 = this.measure6(d, floatValue);
      float floatValue7 = this.measure6(e, floatValue2);
      float floatValue8 = this.measure(floatValue6, floatValue3, floatValue, floatValue5);
      float floatValue9 = this.measure2(floatValue7, floatValue4, floatValue2, floatValue5);
      return new PopupPlacement.PopupPlacementData(floatValue8, floatValue9);
   }

   private float measure(float f, float g, float h, float i) {
      float floatValue10 = this.floatValue;
      float floatValue11 = measure9(f, floatValue10, Math.max(floatValue10, h - floatValue10));
      float floatValue12 = this.measure3(h, floatValue10, i);
      float floatValue13 = this.measure4(g, h, floatValue10, i);
      if (floatValue13 < floatValue12) {
         float floatValue14 = Math.max(floatValue10, h - floatValue10 - g);
         float floatValue15 = h * 0.5F - g * 0.5F;
         return measure9(floatValue15, floatValue10, floatValue14);
      } else {
         float floatValue16 = floatValue11 + this.floatValue2;
         if (floatValue16 >= floatValue12 && floatValue16 <= floatValue13) {
            return floatValue16;
         } else {
            float floatValue17 = floatValue11 - this.floatValue2 - g;
            if (floatValue17 >= floatValue12 && floatValue17 <= floatValue13) {
               return floatValue17;
            } else {
               float floatValue18 = floatValue11 - g * 0.5F;
               return measure9(floatValue18, floatValue12, floatValue13);
            }
         }
      }
   }

   private float measure2(float f, float g, float h, float i) {
      float floatValue19 = this.floatValue;
      float floatValue20 = measure9(f, floatValue19, Math.max(floatValue19, h - floatValue19));
      float floatValue21 = this.measure3(h, floatValue19, i);
      float floatValue22 = this.measure5(g, h, floatValue19, i);
      if (floatValue22 < floatValue21) {
         float floatValue23 = Math.max(floatValue19, h - floatValue19 - g);
         float floatValue24 = h * 0.5F - g * 0.5F;
         return measure9(floatValue24, floatValue19, floatValue23);
      } else {
         float floatValue25 = floatValue20 + this.floatValue3;
         if (floatValue25 >= floatValue21 && floatValue25 <= floatValue22) {
            return floatValue25;
         } else {
            float floatValue26 = floatValue20 - this.floatValue3 - g;
            if (floatValue26 >= floatValue21 && floatValue26 <= floatValue22) {
               return floatValue26;
            } else {
               float floatValue27 = floatValue20 - g * 0.5F;
               return measure9(floatValue27, floatValue21, floatValue22);
            }
         }
      }
   }

   private float measure3(float f, float g, float h) {
      float floatValue28 = measure8(h);
      if (Float.isFinite(floatValue28) && !(floatValue28 <= 0.001F)) {
         float floatValue29 = f * 0.5F;
         return floatValue29 + (g - floatValue29) / floatValue28;
      } else {
         return g;
      }
   }

   private float measure4(float f, float g, float h, float i) {
      float floatValue30 = measure8(i);
      if (Float.isFinite(floatValue30) && !(floatValue30 <= 0.001F)) {
         float floatValue31 = g * 0.5F;
         float floatValue32 = g - h;
         return floatValue31 + (floatValue32 - floatValue31) / floatValue30 - f;
      } else {
         return g - h - f;
      }
   }

   private float measure5(float f, float g, float h, float i) {
      float floatValue33 = measure8(i);
      if (Float.isFinite(floatValue33) && !(floatValue33 <= 0.001F)) {
         float floatValue34 = g * 0.5F;
         float floatValue35 = g - h;
         return floatValue34 + (floatValue35 - floatValue34) / floatValue33 - f;
      } else {
         return g - h - f;
      }
   }

   private float measure6(double d, float f) {
      float floatValue36 = measure7(d);
      if (Float.isNaN(floatValue36)) {
         return f * 0.5F;
      } else {
         float floatValue37 = this.floatValue;
         return measure9(floatValue36, floatValue37, Math.max(floatValue37, f - floatValue37));
      }
   }

   private static float measure7(double d) {
      if (!Double.isFinite(d)) {
         return Float.NaN;
      } else if (d > Float.MAX_VALUE) {
         return Float.MAX_VALUE;
      } else {
         return d < -Float.MAX_VALUE ? -Float.MAX_VALUE : (float)d;
      }
   }

   private static float measure8(float f) {
      if (!Float.isFinite(f)) {
         return 1.0F;
      } else {
         return f <= 0.001F ? 1.0F : f;
      }
   }

   private static float measure9(float f, float g, float h) {
      if (f < g) {
         return g;
      } else {
         return f > h ? h : f;
      }
   }

   public record PopupPlacementData(float x, float y) {
      public PopupPlacementData(float x, float y) {
         if (Float.isFinite(x) && Float.isFinite(y)) {
            this.x = x;
            this.y = y;
         } else {
            throw new IllegalArgumentException("Popup placement coordinates must be finite");
         }
      }
   }
}
