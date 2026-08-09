package ru.metaculture.protection;

import java.util.Locale;

public enum StardustSkyPreset {
   AURORA("Aurora", "Полярное сияние"),
   STARDUST("Stardust", "Звездная пыль", "Stardust Field"),
   TWILIGHT_RAYLEIGH("Twilight Rayleigh", "Солнечная буря", "Зодиакальный рассвет", "Серебристые мезосферные облака"),
   QUANTUM_NEBULA("Quantum Nebula", "Туманность"),
   CHRONOS_SINGULARITY("Chronos Singularity", "Галактическая вуаль", "Сверхячейка на горизонте");

   private final String text;
   private final String[] text2;

   private StardustSkyPreset(String string2, String... strings) {
      this.text = string2;
      this.text2 = strings;
   }

   public String getText() {
      return this.text;
   }

   public int compute() {
      return this.ordinal();
   }

   public static String[] resolve() {
      StardustSkyPreset[] stardustSkyPresets = values();
      String[] texts = new String[stardustSkyPresets.length];

      for (int intValue = 0; intValue < stardustSkyPresets.length; intValue++) {
         texts[intValue] = stardustSkyPresets[intValue].text;
      }

      return texts;
   }

   public static StardustSkyPreset resolve2(String string) {
      if (string != null && !string.isBlank()) {
         String text = resolve3(string);

         for (StardustSkyPreset stardustSkyPreset : values()) {
            if (resolve3(stardustSkyPreset.text).equals(text) || resolve3(stardustSkyPreset.name()).equals(text)) {
               return stardustSkyPreset;
            }

            for (String text2 : stardustSkyPreset.text2) {
               if (resolve3(text2).equals(text)) {
                  return stardustSkyPreset;
               }
            }
         }

         return AURORA;
      } else {
         return AURORA;
      }
   }

   private static String resolve3(String string) {
      return string.trim().replace('_', ' ').toLowerCase(Locale.ROOT);
   }
}
