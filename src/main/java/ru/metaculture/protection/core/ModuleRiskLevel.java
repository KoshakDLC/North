package ru.metaculture.protection;

import java.util.Locale;

public enum ModuleRiskLevel {
   NEW("New", -14494738, "E", 10),
   RISKY("Risky", -50340, "I", 20),
   PATCHED("Patched", -20448, "O", 30),
   GRIM("Grim", -15681151, "Q", 40),
   MATRIX("Matrix", -5083905, "W", 50),
   VIP("VIP", -6511697, "T", 60),
   COMBAT("Combat", -45709, "f", 200),
   MOVEMENT("Movement", -10034009, "b", 210),
   VISUALS("Visuals", -8861697, "n", 220),
   PLAYER("Player", -11930, "m", 230),
   MISC("Misc", -3889153, "v", 240);

   private final String text;
   private final int intValue;
   private final String text2;
   private final int intValue2;

   private ModuleRiskLevel(String string2, int j, String string3, int k) {
      this.text = string2;
      this.intValue = j;
      this.text2 = string3;
      this.intValue2 = k;
   }

   public String getText() {
      return this.text;
   }

   public int getIntValue() {
      return this.intValue;
   }

   public String getText2() {
      return this.text2;
   }

   public int getIntValue2() {
      return this.intValue2;
   }

   public static ModuleRiskLevel resolve(String string) {
      if (string == null) {
         return null;
      } else {
         String text = resolve3(string);
         if (text.isEmpty()) {
            return null;
         } else {
            for (ModuleRiskLevel moduleRiskLevel : values()) {
               if (resolve3(moduleRiskLevel.name()).equals(text) || resolve3(moduleRiskLevel.text).equals(text)) {
                  return moduleRiskLevel;
               }
            }

            return null;
         }
      }
   }

   public static ModuleRiskLevel resolve2(Category category) {
      if (category == null) {
         return null;
      } else {
         return switch (category) {
            case Combat -> COMBAT;
            case Movement -> MOVEMENT;
            case Visuals -> VISUALS;
            case Player -> PLAYER;
            case Misc -> MISC;
         };
      }
   }

   private static String resolve3(String string) {
      String text2 = string == null ? "" : string.trim().toLowerCase(Locale.ROOT);
      if (text2.startsWith("#")) {
         text2 = text2.substring(1);
      }

      return text2.replace("-", "").replace("_", "").replace(" ", "");
   }
}
