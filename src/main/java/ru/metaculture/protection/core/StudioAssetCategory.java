package ru.metaculture.protection;

public enum StudioAssetCategory {
   MODELS("models", "Модели"),
   ITEMS("items", "Предметы"),
   PETS("pets", "Питомцы");

   private final String text;
   private final String text2;

   private StudioAssetCategory(String string2, String string3) {
      this.text = string2;
      this.text2 = string3;
   }

   public String getText() {
      return this.text;
   }

   public String getText2() {
      return this.text2;
   }

   public static StudioAssetCategory resolve(String string) {
      if (string != null) {
         for (StudioAssetCategory studioAssetCategory : values()) {
            if (studioAssetCategory.text.equalsIgnoreCase(string)) {
               return studioAssetCategory;
            }
         }
      }

      return MODELS;
   }
}
