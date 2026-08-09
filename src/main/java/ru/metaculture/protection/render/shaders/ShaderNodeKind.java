package ru.metaculture.protection;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public final class ShaderNodeKind {
   private final String text;
   private final String text2;
   private float floatValue;
   private float floatValue2;
   private float floatValue3;
   private final Map<String, Float> valuesByKey = new LinkedHashMap<>();
   private final Map<String, String> valuesByKey2 = new LinkedHashMap<>();

   public ShaderNodeKind(String string, String string2, float f, float g) {
      this.text = Objects.requireNonNull(string, "id");
      this.text2 = Objects.requireNonNull(string2, "kind");
      this.floatValue = f;
      this.floatValue2 = g;
      this.floatValue3 = 188.0F;
   }

   public String getText() {
      return this.text;
   }

   public String getText2() {
      return this.text2;
   }

   public float getFloatValue() {
      return this.floatValue;
   }

   public float getFloatValue2() {
      return this.floatValue2;
   }

   public void invoke(float f, float g) {
      this.floatValue = f;
      this.floatValue2 = g;
   }

   public float getFloatValue3() {
      return this.floatValue3;
   }

   public void setFloatValue3(float f) {
      this.floatValue3 = Math.max(132.0F, f);
   }

   public Map<String, Float> getValuesByKey() {
      return this.valuesByKey;
   }

   public Map<String, String> getValuesByKey2() {
      return this.valuesByKey2;
   }

   public float measure(String string, float f) {
      Float floatValue = this.valuesByKey.get(string);
      return floatValue != null && Float.isFinite(floatValue) ? floatValue : f;
   }

   public void invoke2(String string, float f) {
      if (string != null && Float.isFinite(f)) {
         this.valuesByKey.put(string, f);
      }
   }

   public String resolve(String string, String string2) {
      String text = this.valuesByKey2.get(string);
      return text != null && !text.isBlank() ? text : string2;
   }

   public void invoke3(String string, String string2) {
      if (string != null) {
         this.valuesByKey2.put(string, string2 == null ? "" : string2);
      }
   }
}
