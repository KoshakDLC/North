package ru.metaculture.protection;

import lombok.Generated;

public enum LegacyEasingType {
   LINEAR(LegacyEasingFunctions.LEGACY_EASING_FUNCTION),
   QUAD_OUT(LegacyEasingFunctions.LEGACY_EASING_FUNCTION_3),
   CUBIC_OUT(LegacyEasingFunctions.LEGACY_EASING_FUNCTION_6),
   QUART_OUT(LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9),
   QUINT_OUT(LegacyEasingFunctions.LEGACY_EASING_FUNCTION_12),
   SINE_OUT(LegacyEasingFunctions.LEGACY_EASING_FUNCTION_15),
   CIRC_OUT(LegacyEasingFunctions.LEGACY_EASING_FUNCTION_18),
   ELASTIC_OUT(LegacyEasingFunctions.LEGACY_EASING_FUNCTION_21),
   EXPO_OUT(LegacyEasingFunctions.LEGACY_EASING_FUNCTION_24),
   BACK_OUT(LegacyEasingFunctions.LEGACY_EASING_FUNCTION_27),
   BOUNCE_OUT(LegacyEasingFunctions.LEGACY_EASING_FUNCTION_29);

   private final LegacyEasingFunction legacyEasingFunction;

   @Override
   public String toString() {
      String text = this.name().toLowerCase();
      String[] texts = text.split("_");
      StringBuilder stringBuilder = new StringBuilder();

      for (String text2 : texts) {
         stringBuilder.append(Character.toUpperCase(text2.charAt(0))).append(text2.substring(1)).append(" ");
      }

      return stringBuilder.toString().trim();
   }

   @Generated
   public LegacyEasingFunction getLegacyEasingFunction() {
      return this.legacyEasingFunction;
   }

   @Generated
   private LegacyEasingType(LegacyEasingFunction legacyEasingFunction) {
      this.legacyEasingFunction = legacyEasingFunction;
   }
}
