package ru.metaculture.protection;

import java.awt.Color;
import java.util.Objects;

public final class ColorPickerState {
   private final float floatValue;
   private final float floatValue2;
   private final float floatValue3;
   private final float floatValue4;

   private ColorPickerState(float f, float g, float h, float i) {
      this.floatValue = measure(f);
      this.floatValue2 = measure2(g);
      this.floatValue3 = measure2(h);
      this.floatValue4 = measure2(i);
   }

   public static ColorPickerState resolve(float f, float g, float h, float i) {
      return new ColorPickerState(f, g, h, i);
   }

   public static ColorPickerState resolve2(float f, float g, float h) {
      return new ColorPickerState(f, g, h, 1.0F);
   }

   public static ColorPickerState resolve3(int i) {
      int intValue = i >>> 16 & 0xFF;
      int intValue2 = i >>> 8 & 0xFF;
      int intValue3 = i & 0xFF;
      int intValue4 = i >>> 24 & 0xFF;
      float[] floatValues = Color.RGBtoHSB(intValue, intValue2, intValue3, null);
      return new ColorPickerState(floatValues[0] * 360.0F, floatValues[1], floatValues[2], intValue4 / 255.0F);
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

   public ColorPickerState resolve4(float f) {
      return new ColorPickerState(f, this.floatValue2, this.floatValue3, this.floatValue4);
   }

   public ColorPickerState resolve5(float f) {
      return new ColorPickerState(this.floatValue, f, this.floatValue3, this.floatValue4);
   }

   public ColorPickerState resolve6(float f) {
      return new ColorPickerState(this.floatValue, this.floatValue2, f, this.floatValue4);
   }

   public ColorPickerState resolve7(float f) {
      return new ColorPickerState(this.floatValue, this.floatValue2, this.floatValue3, f);
   }

   public ColorPickerState getThis() {
      return this;
   }

   public int compute() {
      float floatValue = this.floatValue / 360.0F;
      Color color = Color.getHSBColor(floatValue, this.floatValue2, this.floatValue3);
      int intValue5 = color.getRed();
      int intValue6 = color.getGreen();
      int intValue7 = color.getBlue();
      int intValue8 = Math.round(this.floatValue4 * 255.0F);
      return intValue8 << 24 | intValue5 << 16 | intValue6 << 8 | intValue7;
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else if (object != null && this.getClass() == object.getClass()) {
         ColorPickerState colorPickerState = (ColorPickerState)object;
         return Float.compare(colorPickerState.floatValue, this.floatValue) == 0
            && Float.compare(colorPickerState.floatValue2, this.floatValue2) == 0
            && Float.compare(colorPickerState.floatValue3, this.floatValue3) == 0
            && Float.compare(colorPickerState.floatValue4, this.floatValue4) == 0;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.floatValue, this.floatValue2, this.floatValue3, this.floatValue4);
   }

   private static float measure(float f) {
      if (!Float.isFinite(f)) {
         return 0.0F;
      } else {
         float floatValue2 = f % 360.0F;
         if (floatValue2 < 0.0F) {
            floatValue2 += 360.0F;
         }

         return floatValue2;
      }
   }

   private static float measure2(float f) {
      return !(f <= 0.0F) && !Float.isNaN(f) ? Math.min(f, 1.0F) : 0.0F;
   }
}
