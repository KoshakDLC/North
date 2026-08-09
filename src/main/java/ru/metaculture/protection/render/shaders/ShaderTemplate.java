package ru.metaculture.protection;

public final class ShaderTemplate {
   private String text = "";
   private String text2 = "";
   private String text3 = "";
   private String custom = "Custom";
   private String local = "local";
   private String hostRectangle = "Host Rectangle";
   private String text4 = "";
   private long timestamp;
   private long timestamp2;
   private boolean flag;

   public ShaderTemplate() {
      long longValue = System.currentTimeMillis();
      this.timestamp = longValue;
      this.timestamp2 = longValue;
   }

   public ShaderTemplate resolve() {
      ShaderTemplate shaderTemplate = new ShaderTemplate();
      shaderTemplate.invoke(this);
      return shaderTemplate;
   }

   public void invoke(ShaderTemplate shaderTemplate2) {
      if (shaderTemplate2 != null) {
         this.text = shaderTemplate2.text;
         this.text2 = shaderTemplate2.text2;
         this.text3 = shaderTemplate2.text3;
         this.custom = shaderTemplate2.custom;
         this.local = shaderTemplate2.local;
         this.hostRectangle = shaderTemplate2.hostRectangle;
         this.text4 = shaderTemplate2.text4;
         this.timestamp = shaderTemplate2.timestamp;
         this.timestamp2 = shaderTemplate2.timestamp2;
         this.flag = shaderTemplate2.flag;
      }
   }

   public String getText() {
      return this.text;
   }

   public void setText(String string) {
      this.text = resolve2(string);
   }

   public String getText2() {
      return this.text2;
   }

   public void setText2(String string) {
      this.text2 = resolve2(string);
   }

   public String getText3() {
      return this.text3;
   }

   public void setText3(String string) {
      this.text3 = resolve2(string);
   }

   public String getCustom() {
      return this.custom;
   }

   public void invoke2(String string) {
      String text = resolve2(string);
      this.custom = text.isBlank() ? "Custom" : text;
   }

   public String getLocal() {
      return this.local;
   }

   public void invoke3(String string) {
      String text2 = resolve2(string);
      this.local = text2.isBlank() ? "local" : text2;
   }

   public String getHostRectangle() {
      return this.hostRectangle;
   }

   public void invoke4(String string) {
      String text3 = resolve2(string);
      if (!"Inset Shape".equals(text3) && !"Full Quad".equals(text3)) {
         this.hostRectangle = "Host Rectangle";
      } else {
         this.hostRectangle = text3;
      }
   }

   public String getText4() {
      return this.text4;
   }

   public void setText4(String string) {
      this.text4 = resolve2(string);
   }

   public long getTimestamp() {
      return this.timestamp;
   }

   public void setTimestamp(long l) {
      this.timestamp = Math.max(0L, l);
   }

   public long getTimestamp2() {
      return this.timestamp2;
   }

   public void setTimestamp2(long l) {
      this.timestamp2 = Math.max(0L, l);
   }

   public boolean isFlag() {
      return this.flag;
   }

   public void setFlag(boolean bl) {
      this.flag = bl;
   }

   public void invoke5(String string, String string2) {
      long longValue2 = System.currentTimeMillis();
      if (this.timestamp <= 0L) {
         this.timestamp = longValue2;
      }

      if (this.timestamp2 <= 0L) {
         this.timestamp2 = longValue2;
      }

      if (this.text.isBlank()) {
         this.setText(string);
      }

      if (this.text2.isBlank()) {
         this.setText2(string2);
      }

      if (this.custom.isBlank()) {
         this.custom = "Custom";
      }

      if (this.local.isBlank()) {
         this.local = "local";
      }

      if (this.hostRectangle.isBlank()) {
         this.hostRectangle = "Host Rectangle";
      }
   }

   private static String resolve2(String string) {
      if (string == null) {
         return "";
      } else {
         String text4 = string.trim().replaceAll("\\s+", " ");
         return text4.length() > 128 ? text4.substring(0, 128) : text4;
      }
   }
}
