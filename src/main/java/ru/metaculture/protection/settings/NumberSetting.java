package ru.metaculture.protection;

import java.util.function.Supplier;

public class NumberSetting extends Setting {
   public float value;
   public float minimum;
   public float maximum;
   public float step;
   public float floatValue;
   public boolean flag;
   public boolean flag2;
   public String text;
   public String[] text2;
   private final float floatValue2;

   public NumberSetting(String string, float f, float g, float h, float i, boolean bl) {
      this.name = string;
      this.minimum = g;
      this.value = f;
      this.maximum = h;
      this.step = i;
      this.text = this.text;
      this.flag2 = bl;
      this.floatValue2 = f;
   }

   public float getValue() {
      return this.value;
   }

   public void invoke(float f) {
      if (!Float.isNaN(f) && !Float.isInfinite(f)) {
         this.value = Math.max(this.minimum, Math.min(this.maximum, f));
      }
   }

   public NumberSetting setVisibilityCondition(Supplier<Boolean> supplier) {
      this.visibilityCondition = supplier;
      return this;
   }

   @Override
   public void resetToDefault() {
      this.invoke(this.floatValue2);
      this.flag = false;
   }

   public NumberSetting setText2(String... strings) {
      this.text2 = strings;
      return this;
   }

   public boolean check() {
      return this.text2 != null && this.text2.length > 0;
   }

   public String resolve(float f) {
      if (!this.check()) {
         return null;
      } else {
         int intValue = Math.round(this.maximum - this.minimum);
         int intValue2 = Math.round(f - this.minimum);
         if (intValue > 0 && this.text2.length == intValue + 1) {
            intValue2 = Math.max(0, Math.min(this.text2.length - 1, intValue2));
            return this.text2[intValue2];
         } else {
            float floatValue = this.maximum - this.minimum <= 0.0F ? 0.0F : (f - this.minimum) / (this.maximum - this.minimum);
            int intValue3 = Math.max(0, Math.min(this.text2.length - 1, Math.round(floatValue * (this.text2.length - 1))));
            return this.text2[intValue3];
         }
      }
   }
}
