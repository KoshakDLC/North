package ru.metaculture.protection;

public enum Category {
   Combat("Combat", "f"),
   Movement("Movement", "b"),
   Visuals("Visuals", "n"),
   Player("Player", "m"),
   Misc("Miscellaneous", "v");

   private final String displayName;
   private final String iconGlyph;

   private Category(String string2, String string3) {
      this.displayName = string2;
      this.iconGlyph = string3;
   }

   public String getIconGlyph() {
      return this.iconGlyph;
   }

   public String getDisplayName() {
      return this.displayName;
   }
}
