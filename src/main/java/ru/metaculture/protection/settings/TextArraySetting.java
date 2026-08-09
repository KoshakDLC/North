package ru.metaculture.protection;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.util.Arrays;
import java.util.function.Supplier;

public class TextArraySetting extends Setting {
   private static final int INT_VALUE = 9;
   private final String[] text = new String[9];
   private final String[] text2 = new String[9];

   public TextArraySetting(String string) {
      this.name = string;
      Arrays.fill(this.text, "");
      Arrays.fill(this.text2, "");
   }

   public String resolve(int i) {
      if (i >= 0 && i < 9) {
         return this.text[i] == null ? "" : this.text[i];
      } else {
         return "";
      }
   }

   public void invoke(int i, String string) {
      if (i >= 0 && i < 9) {
         this.text[i] = string == null ? "" : string.trim();
      }
   }

   public void invoke2(int i) {
      this.invoke(i, "");
   }

   public void invoke3() {
      Arrays.fill(this.text, "");
   }

   public boolean check() {
      for (String text : this.text) {
         if (text != null && !text.isBlank()) {
            return false;
         }
      }

      return true;
   }

   public String[] resolve2() {
      return Arrays.copyOf(this.text, this.text.length);
   }

   public JsonArray toJson() {
      JsonArray jsonArray = new JsonArray();

      for (String text2 : this.text) {
         jsonArray.add(text2 == null ? "" : text2);
      }

      return jsonArray;
   }

   public void loadFromJson(JsonElement jsonElement) {
      Arrays.fill(this.text, "");
      if (jsonElement != null && jsonElement.isJsonArray()) {
         JsonArray jsonArray2 = jsonElement.getAsJsonArray();

         for (int intValue = 0; intValue < Math.min(9, jsonArray2.size()); intValue++) {
            try {
               this.text[intValue] = jsonArray2.get(intValue).getAsString();
            } catch (Throwable exception) {
               this.text[intValue] = "";
            }
         }
      }
   }

   public TextArraySetting setVisibilityCondition(Supplier<Boolean> supplier) {
      this.visibilityCondition = supplier;
      return this;
   }

   @Override
   public void resetToDefault() {
      System.arraycopy(this.text2, 0, this.text, 0, 9);
   }
}
