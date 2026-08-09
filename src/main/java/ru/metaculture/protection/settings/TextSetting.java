package ru.metaculture.protection;

import java.util.function.Supplier;

public class TextSetting extends Setting {
   public static final int INT_VALUE = 256;
   public String value;
   public String description;
   public int maxLength = 256;
   private final String defaultValue;
   public boolean flag;

   public TextSetting(String string, String string2) {
      this.name = string;
      this.value = resolve2(string2, 256);
      this.defaultValue = this.value;
   }

   public String getValue() {
      return this.value;
   }

   public void setValue(String string) {
      this.value = resolve2(string, this.maxLength);
   }

   public TextSetting setVisibilityCondition(Supplier<Boolean> supplier) {
      this.visibilityCondition = supplier;
      return this;
   }

   @Override
   public void resetToDefault() {
      this.value = resolve2(this.defaultValue, this.maxLength);
      this.flag = false;
   }

   public TextSetting resolve(int i) {
      this.maxLength = Math.max(1, i);
      if (this.value != null && this.value.length() > this.maxLength) {
         this.value = this.value.substring(0, this.maxLength);
      }

      return this;
   }

   private static String resolve2(String string, int i) {
      if (string == null) {
         return "";
      } else {
         return string.length() > i ? string.substring(0, i) : string;
      }
   }
}
