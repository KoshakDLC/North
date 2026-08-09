package ru.metaculture.protection;

import java.util.Objects;

public final class FontObject {
   public final String text;

   public FontObject(String string) {
      this.text = Objects.requireNonNull(string, "id");
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else if (object != null && this.getClass() == object.getClass()) {
         FontObject fontObject = (FontObject)object;
         return this.text.equals(fontObject.text);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return this.text.hashCode();
   }

   @Override
   public String toString() {
      return "FontObject(" + this.text + ")";
   }
}
