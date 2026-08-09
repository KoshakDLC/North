package ru.metaculture.protection;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ColorSetting extends Setting {
   public static final int INT_VALUE = 8;
   public float hueValue;
   public float minimumHue;
   public float maximumHue;
   public float floatValue;
   public float floatValue2;
   public boolean flag;
   public String text;
   public DirectionalAnimation directionalAnimation = new EaseInOutQuadAnimation(300, 1.0);
   public float saturation = 1.0F;
   public float brightness = 1.0F;
   public float floatValue3 = 1.0F;
   public final List<Integer> items = new ArrayList<>();
   protected float floatValue4;
   protected float floatValue5;
   protected float floatValue6;
   protected float floatValue7;

   public ColorSetting(String string, float f) {
      this.name = string;
      this.minimumHue = 0.0F;
      this.maximumHue = 106.0F;
      this.floatValue = 1.0F;
      if (!(f < this.minimumHue) && !(f > this.maximumHue)) {
         this.hueValue = f;
         this.saturation = 1.0F;
         this.brightness = 1.0F;
         this.floatValue3 = 1.0F;
      } else {
         this.invoke2((int)f);
      }

      this.invoke8();
   }

   public ColorSetting(String string, float f, float g, float h) {
      this(string, f, g, h, 1.0F);
   }

   public ColorSetting(String string, float f, float g, float h, float i) {
      this.name = string;
      this.minimumHue = 0.0F;
      this.hueValue = f;
      this.maximumHue = 106.0F;
      this.floatValue = 1.0F;
      this.saturation = measure3(g);
      this.brightness = measure3(h);
      this.floatValue3 = measure3(i);
      this.invoke8();
   }

   public ColorSetting setVisibilityCondition(Supplier<Boolean> supplier) {
      this.visibilityCondition = supplier;
      return this;
   }

   public Color getColor() {
      float floatValue = this.measure();
      Color color2 = Color.getHSBColor(floatValue, this.saturation, this.brightness);
      return new Color(color2.getRed(), color2.getGreen(), color2.getBlue(), Math.round(this.floatValue3 * 255.0F));
   }

   public void invoke(Color color) {
      float[] floatValues = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
      this.hueValue = floatValues[0] * this.maximumHue;
      this.saturation = floatValues[1];
      this.brightness = floatValues[2];
      this.floatValue3 = color.getAlpha() / 255.0F;
   }

   public void invoke2(int i) {
      int intValue = i >= 0 && i <= 16777215 ? 0xFF000000 | i : i;
      this.invoke(new Color(intValue, true));
   }

   public float measure() {
      return measure3(this.hueValue / this.maximumHue);
   }

   public float measure2() {
      return this.measure() * 360.0F;
   }

   public void invoke3(float f) {
      float floatValue2 = f % 360.0F;
      if (floatValue2 < 0.0F) {
         floatValue2 += 360.0F;
      }

      this.hueValue = floatValue2 / 360.0F * this.maximumHue;
   }

   public void setFloatValue3(float f) {
      this.floatValue3 = measure3(f);
   }

   public void invoke4(int i) {
      this.items.removeIf(integer -> integer == i);
      this.items.add(0, i);

      while (this.items.size() > 8) {
         this.items.remove(this.items.size() - 1);
      }
   }

   public void invoke5() {
      this.invoke4(this.compute2());
   }

   public void invoke6(int i) {
      if (i >= 0 && i < this.items.size()) {
         this.invoke2(this.items.get(i));
      }
   }

   public void invoke7(int i) {
      if (i >= 0 && i < this.items.size()) {
         this.items.remove(i);
      }
   }

   public int compute() {
      return this.getColor().getRGB();
   }

   public int compute2() {
      return this.getColor().getRGB();
   }

   public int compute3(int i) {
      Color color3 = this.getColor();
      return i << 24 | color3.getRed() << 16 | color3.getGreen() << 8 | color3.getBlue();
   }

   private static float measure3(float f) {
      return Float.isFinite(f) && !(f <= 0.0F) ? Math.min(f, 1.0F) : 0.0F;
   }

   protected void invoke8() {
      this.floatValue4 = this.hueValue;
      this.floatValue5 = this.saturation;
      this.floatValue6 = this.brightness;
      this.floatValue7 = this.floatValue3;
   }

   @Override
   public void resetToDefault() {
      this.hueValue = this.floatValue4;
      this.saturation = this.floatValue5;
      this.brightness = this.floatValue6;
      this.floatValue3 = this.floatValue7;
      this.flag = false;
   }
}
