package ru.metaculture.protection;

public final class SavedShaderPreset {
   private final String text;
   private String text2;
   private String text3;
   private String text4;
   private String text5;
   private String text6;
   private String text7;
   private String text8;
   private String text9;
   private long timestamp;
   private long timestamp2;
   private boolean flag;

   public SavedShaderPreset(String string, String string2, String string3, String string4, long l) {
      this(string, string2, string3, string4, "", "", "Custom", "user", "saved", l, l, false);
   }

   public SavedShaderPreset(
      String string, String string2, String string3, String string4, String string5, String string6, String string7, long l, long m, boolean bl
   ) {
      this(string, string2, string3, string4, string5, string6, string7, "user", "saved", l, m, bl);
   }

   public SavedShaderPreset(
      String string,
      String string2,
      String string3,
      String string4,
      String string5,
      String string6,
      String string7,
      String string8,
      String string9,
      long l,
      long m,
      boolean bl
   ) {
      this.text = string;
      this.text2 = string2;
      this.text3 = string3;
      this.text4 = string4;
      this.text5 = string5 == null ? "" : string5;
      this.text6 = string6 == null ? "" : string6;
      this.text7 = string7 != null && !string7.isBlank() ? string7 : "Custom";
      this.text8 = string8 != null && !string8.isBlank() ? string8 : "user";
      this.text9 = string9 != null && !string9.isBlank() ? string9 : "saved";
      this.timestamp = l;
      this.timestamp2 = m;
      this.flag = bl;
   }

   public String getText() {
      return this.text;
   }

   public String getText2() {
      return this.text2;
   }

   public void invoke(String string) {
      if (string != null && !string.isBlank()) {
         this.text2 = string;
      }
   }

   public String getText3() {
      return this.text3;
   }

   public void setText3(String string) {
      this.text3 = string;
   }

   public String getText4() {
      return this.text4;
   }

   public void setText4(String string) {
      this.text4 = string;
   }

   public String getText5() {
      return this.text5;
   }

   public void setText5(String string) {
      this.text5 = string == null ? "" : string;
   }

   public String getText6() {
      return this.text6;
   }

   public void setText6(String string) {
      this.text6 = string == null ? "" : string;
   }

   public String getText7() {
      return this.text7;
   }

   public void setText7(String string) {
      this.text7 = string != null && !string.isBlank() ? string : "Custom";
   }

   public String getText8() {
      return this.text8;
   }

   public void setText8(String string) {
      this.text8 = string != null && !string.isBlank() ? string : "user";
   }

   public String getText9() {
      return this.text9;
   }

   public void setText9(String string) {
      this.text9 = string != null && !string.isBlank() ? string : "saved";
   }

   public long getTimestamp() {
      return this.timestamp;
   }

   public void setTimestamp(long l) {
      this.timestamp = l;
   }

   public long getTimestamp2() {
      return this.timestamp2;
   }

   public void setTimestamp2(long l) {
      this.timestamp2 = l;
   }

   public boolean isFlag() {
      return this.flag;
   }

   public void setFlag(boolean bl) {
      this.flag = bl;
   }
}
