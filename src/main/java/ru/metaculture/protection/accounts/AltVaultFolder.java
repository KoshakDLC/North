package ru.metaculture.protection;

import java.util.HashMap;
import java.util.Map;

final class AltVaultFolder {
   final String text;
   String text2;
   final Map<String, AltVaultServerEntry> valuesByKey = new HashMap<>();

   AltVaultFolder(String string, String string2) {
      this.text = string;
      this.text2 = string2;
   }
}
