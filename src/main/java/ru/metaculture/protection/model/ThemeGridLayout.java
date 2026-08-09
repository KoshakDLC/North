package ru.metaculture.protection;

public final class ThemeGridLayout {
   private final float floatValue;
   private final float floatValue2;
   private final float floatValue3;
   private final float floatValue4;
   private final float floatValue5;
   private final float floatValue6;
   private final float floatValue7;
   private final float floatValue8;
   private final float floatValue9;
   private final float floatValue10;
   private final float floatValue11;
   private final float floatValue12;
   private final float floatValue13;
   private final float floatValue14;

   private ThemeGridLayout(float f, float g, float h, float i, float j, float k, float l, float m, float n, float o, float p, float q, float r, float s) {
      this.floatValue = f;
      this.floatValue2 = g;
      this.floatValue3 = h;
      this.floatValue4 = i;
      this.floatValue5 = j;
      this.floatValue6 = k;
      this.floatValue7 = l;
      this.floatValue8 = m;
      this.floatValue9 = n;
      this.floatValue10 = o;
      this.floatValue11 = p;
      this.floatValue12 = q;
      this.floatValue13 = r;
      this.floatValue14 = s;
   }

   public static ThemeGridLayout resolve(ClickGuiGeometry clickGuiGeometry, Metrics metrics) {
      float floatValue = metrics.measure2(8.0F);
      float floatValue2 = metrics.measure2(26.0F);
      float floatValue3 = metrics.measure2(6.0F);
      float floatValue4 = clickGuiGeometry.getFloatValue23() + floatValue + metrics.measure2(44.0F) + floatValue3;
      float floatValue5 = floatValue4 + floatValue2 + floatValue3;
      float floatValue6 = clickGuiGeometry.getFloatValue23() + metrics.getFloatValue19() - floatValue - floatValue5;
      float floatValue7 = metrics.getFloatValue18() - floatValue * 2.0F;
      float floatValue8 = metrics.measure2(18.0F);
      float floatValue9 = floatValue7 - floatValue8;
      float floatValue10 = metrics.measure2(8.0F);
      float floatValue11 = (floatValue9 - floatValue10) * 0.5F;
      float floatValue12 = metrics.measure2(34.0F);
      float floatValue13 = metrics.measure2(4.0F);
      float floatValue14 = clickGuiGeometry.getFloatValue24() + metrics.measure2(7.5F);
      return new ThemeGridLayout(
         clickGuiGeometry.getFloatValue22() + floatValue,
         floatValue5,
         floatValue7,
         floatValue6,
         clickGuiGeometry.getFloatValue22() + floatValue,
         floatValue4,
         floatValue7,
         floatValue2,
         floatValue14,
         floatValue11,
         floatValue12,
         floatValue10,
         metrics.measure2(6.0F),
         floatValue13
      );
   }

   public ThemeGridLayout.Cell resolve2(int i, float f) {
      int intValue = i % 2;
      int intValue2 = i / 2;
      float floatValue15 = this.floatValue9 + intValue * (this.floatValue10 + this.floatValue12);
      float floatValue16 = this.floatValue2 + this.floatValue14 + f + intValue2 * (this.floatValue11 + this.floatValue13);
      return new ThemeGridLayout.Cell(floatValue15, floatValue16, this.floatValue10, this.floatValue11);
   }

   public float measure(int i) {
      int intValue3 = (i + 1) / 2;
      return this.floatValue14 * 2.0F + intValue3 * this.floatValue11 + Math.max(0, intValue3 - 1) * this.floatValue13;
   }

   public boolean check(ThemeGridLayout.Cell cell, float f) {
      float floatValue17 = this.floatValue2 - Math.max(0.0F, f);
      float floatValue18 = this.floatValue2 + this.floatValue4 + Math.max(0.0F, f);
      return cell.y + cell.height >= floatValue17 && cell.y <= floatValue18;
   }

   public float getFloatValue() {
      return this.floatValue;
   }

   public float getFloatValue2() {
      return this.floatValue2;
   }

   public float getFloatValue3() {
      return this.floatValue3;
   }

   public float getFloatValue4() {
      return this.floatValue4;
   }

   public float getFloatValue5() {
      return this.floatValue5;
   }

   public float getFloatValue6() {
      return this.floatValue6;
   }

   public float getFloatValue7() {
      return this.floatValue7;
   }

   public float getFloatValue8() {
      return this.floatValue8;
   }

   public float measure2() {
      return this.floatValue5 + this.floatValue7 - this.floatValue8;
   }

   public float getFloatValue82() {
      return this.floatValue8;
   }

   public float getFloatValue10() {
      return this.floatValue10;
   }

   public float getFloatValue11() {
      return this.floatValue11;
   }

   public record Cell(float x, float y, float width, float height) {
   }
}
