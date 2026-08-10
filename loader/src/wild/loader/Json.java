package wild.loader;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Minimal JSON reader. The loader ships without dependencies, and the version metadata from Mojang
 * and Fabric is far too nested to pick apart with regular expressions.
 */
final class Json {
   private final String text;
   private int at;

   private Json(String text) {
      this.text = text;
   }

   static Object parse(String text) {
      if (text != null && !text.isEmpty() && text.charAt(0) == '\uFEFF') {
         text = text.substring(1);
      }

      Json reader = new Json(text);
      reader.space();
      Object value = reader.value();
      reader.space();
      return value;
   }

   private Object value() {
      char c = this.peek();
      switch (c) {
         case '{':
            return this.map();
         case '[':
            return this.list();
         case '"':
            return this.string();
         case 't':
            this.word("true");
            return Boolean.TRUE;
         case 'f':
            this.word("false");
            return Boolean.FALSE;
         case 'n':
            this.word("null");
            return null;
         default:
            return this.number();
      }
   }

   private Map<String, Object> map() {
      Map<String, Object> result = new LinkedHashMap<>();
      this.at++;
      this.space();
      if (this.peek() == '}') {
         this.at++;
         return result;
      }

      while (true) {
         this.space();
         String key = this.string();
         this.space();
         this.character(':');
         this.space();
         result.put(key, this.value());
         this.space();
         char c = this.next();
         if (c == '}') {
            return result;
         }

         if (c != ',') {
            throw new IllegalArgumentException("Ожидалась запятая на позиции " + this.at);
         }
      }
   }

   private List<Object> list() {
      List<Object> result = new ArrayList<>();
      this.at++;
      this.space();
      if (this.peek() == ']') {
         this.at++;
         return result;
      }

      while (true) {
         this.space();
         result.add(this.value());
         this.space();
         char c = this.next();
         if (c == ']') {
            return result;
         }

         if (c != ',') {
            throw new IllegalArgumentException("Ожидалась запятая на позиции " + this.at);
         }
      }
   }

   private String string() {
      this.character('"');
      StringBuilder builder = new StringBuilder();

      while (true) {
         char c = this.next();
         if (c == '"') {
            return builder.toString();
         }

         if (c != '\\') {
            builder.append(c);
         } else {
            char escape = this.next();
            switch (escape) {
               case '"':
               case '\\':
               case '/':
                  builder.append(escape);
                  break;
               case 'b':
                  builder.append('\b');
                  break;
               case 'f':
                  builder.append('\f');
                  break;
               case 'n':
                  builder.append('\n');
                  break;
               case 'r':
                  builder.append('\r');
                  break;
               case 't':
                  builder.append('\t');
                  break;
               case 'u':
                  builder.append((char)Integer.parseInt(this.text.substring(this.at, this.at + 4), 16));
                  this.at += 4;
                  break;
               default:
                  throw new IllegalArgumentException("Неизвестный escape \\" + escape);
            }
         }
      }
   }

   private Double number() {
      int start = this.at;

      while (this.at < this.text.length() && "+-.eE0123456789".indexOf(this.text.charAt(this.at)) >= 0) {
         this.at++;
      }

      return Double.valueOf(this.text.substring(start, this.at));
   }

   private void word(String expected) {
      if (!this.text.startsWith(expected, this.at)) {
         throw new IllegalArgumentException("Ожидалось " + expected + " на позиции " + this.at);
      }

      this.at += expected.length();
   }

   private void character(char expected) {
      if (this.next() != expected) {
         throw new IllegalArgumentException("Ожидался символ " + expected + " на позиции " + this.at);
      }
   }

   private void space() {
      while (this.at < this.text.length() && Character.isWhitespace(this.text.charAt(this.at))) {
         this.at++;
      }
   }

   private char peek() {
      if (this.at >= this.text.length()) {
         throw new IllegalArgumentException("Неожиданный конец JSON");
      }

      return this.text.charAt(this.at);
   }

   private char next() {
      char c = this.peek();
      this.at++;
      return c;
   }

   @SuppressWarnings("unchecked")
   static Map<String, Object> object(Object value) {
      return value instanceof Map ? (Map<String, Object>)value : Map.of();
   }

   @SuppressWarnings("unchecked")
   static List<Object> array(Object value) {
      return value instanceof List ? (List<Object>)value : List.of();
   }

   static Object get(Object value, String key) {
      return object(value).get(key);
   }

   /** Walks nested objects, returning null as soon as a key is missing. */
   static Object at(Object value, String... keys) {
      Object current = value;

      for (String key : keys) {
         current = get(current, key);
         if (current == null) {
            return null;
         }
      }

      return current;
   }

   static String text(Object value, String fallback) {
      return value instanceof String string ? string : fallback;
   }

   static long number(Object value, long fallback) {
      return value instanceof Double number ? number.longValue() : fallback;
   }

   static boolean flag(Object value, boolean fallback) {
      return value instanceof Boolean bool ? bool : fallback;
   }
}
