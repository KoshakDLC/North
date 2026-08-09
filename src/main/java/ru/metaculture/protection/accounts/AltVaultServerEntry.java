package ru.metaculture.protection;

final class AltVaultServerEntry {
   final String text;
   String text2;
   String text3;
   byte[] bytes;
   long timestamp;
   long timestamp2;

   AltVaultServerEntry(String string, String string2, String string3) {
      this.text = string;
      this.text2 = string2;
      this.text3 = string3;
   }

   String resolve() {
      String text = AltVaultScreen.AltVaultScreenState4.resolve12(this.text2).trim();
      return text.isBlank() ? AltVaultScreen.AltVaultScreenState4.resolve10(this.text3) : text;
   }
}
