package ru.metaculture.protection;

import java.io.Serializable;
import ru.metaculture.sdk.Loader;

public class SerializableStringPair implements Serializable {
   private final String text;
   private final String text2;

   public String resolve() {
      return null;
   }

   public String resolve2() {
      return null;
   }

   public static SerializableStringPair resolve3(String string, String string2) {
      return null;
   }

   protected SerializableStringPair(String string, String string2) {
      this.text2 = string;
      this.text = string2;
   }

   static {
      Loader.initialize();
   }
}
