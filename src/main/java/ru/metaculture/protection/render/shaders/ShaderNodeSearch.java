package ru.metaculture.protection;

import java.util.Locale;

public final class ShaderNodeSearch {
   private ShaderNodeSearch() {
   }

   public static ShaderNodeSearch.ShaderNodeSearchData resolve(ShaderNodeDefinition shaderNodeDefinition, String string) {
      String text = shaderNodeDefinition.getText2().toLowerCase(Locale.ROOT);
      int intValue = 0;
      int[] intValues = resolve2(text, string);
      if (intValues != null) {
         intValue += 30;
         if (text.startsWith(string)) {
            intValue += 90;
         } else if (text.contains(string)) {
            intValue += 48;
         }

         int intValue2 = -2;

         for (int intValue3 : intValues) {
            if (intValue3 == intValue2 + 1) {
               intValue += 10;
            } else if (intValue3 > intValue2 + 1 && intValue2 >= 0) {
               intValue -= Math.min(intValue3 - intValue2 - 1, 6);
            }

            if (intValue3 == 0 || check(text.charAt(intValue3 - 1))) {
               intValue += 14;
            }

            intValue2 = intValue3;
         }
      }

      String text2 = shaderNodeDefinition.getText().toLowerCase(Locale.ROOT);
      String text3 = shaderNodeDefinition.getText3().toLowerCase(Locale.ROOT);
      if (text2.contains(string)) {
         intValue += 22;
      }

      if (text3.contains(string)) {
         intValue += 10;
      }

      return intValue <= 0 ? null : new ShaderNodeSearch.ShaderNodeSearchData(shaderNodeDefinition, intValue, intValues == null ? new int[0] : intValues);
   }

   public static int[] resolve2(String string, String string2) {
      int[] intValues2 = new int[string2.length()];
      int intValue4 = 0;

      for (int intValue5 = 0; intValue5 < string2.length(); intValue5++) {
         int intValue6 = string.indexOf(string2.charAt(intValue5), intValue4);
         if (intValue6 < 0) {
            return null;
         }

         intValues2[intValue5] = intValue6;
         intValue4 = intValue6 + 1;
      }

      return intValues2;
   }

   public static boolean check(char c) {
      return c == ' ' || c == '_' || c == '.' || c == '-' || c == '(' || c == '/';
   }

   public record ShaderNodeSearchData(ShaderNodeDefinition def, int score, int[] titlePositions) {
   }
}
