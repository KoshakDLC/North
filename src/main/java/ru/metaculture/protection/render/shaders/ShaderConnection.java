package ru.metaculture.protection;

import java.util.Objects;

public final class ShaderConnection {
   private final String text;
   private final String text2;
   private final String text3;
   private final String text4;

   public ShaderConnection(String string, String string2, String string3, String string4) {
      this.text = Objects.requireNonNull(string, "fromNodeId");
      this.text2 = Objects.requireNonNull(string2, "fromPinId");
      this.text3 = Objects.requireNonNull(string3, "toNodeId");
      this.text4 = Objects.requireNonNull(string4, "toPinId");
   }

   public String getText() {
      return this.text;
   }

   public String getText2() {
      return this.text2;
   }

   public String getText3() {
      return this.text3;
   }

   public String getText4() {
      return this.text4;
   }

   public String resolve() {
      return this.text + "." + this.text2;
   }

   public String resolve2() {
      return this.text3 + "." + this.text4;
   }
}
