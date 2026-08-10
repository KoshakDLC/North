package north.license;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Tiny JSON reader/writer helpers for the license server. */
final class MiniJson {
   private final String text;
   private int at;

   private MiniJson(String text) {
      this.text = text;
   }

   static Object parse(String text) {
      if (text != null && !text.isEmpty() && text.charAt(0) == '\uFEFF') {
         text = text.substring(1);
      }

      MiniJson reader = new MiniJson(text);
      reader.space();
      Object value = reader.value();
      reader.space();
      return value;
   }

   static Map<String, Object> object(Object value) {
      return value instanceof Map<?, ?> map ? castMap(map) : Map.of();
   }

   @SuppressWarnings("unchecked")
   private static Map<String, Object> castMap(Map<?, ?> map) {
      return (Map<String, Object>)map;
   }

   static List<Object> array(Object value) {
      return value instanceof List<?> list ? new ArrayList<>(list) : List.of();
   }

   static Object get(Object value, String key) {
      return object(value).get(key);
   }

   static String text(Object value, String fallback) {
      return value instanceof String string ? string : fallback;
   }

   static long number(Object value, long fallback) {
      if (value instanceof Number number) {
         return number.longValue();
      }

      if (value instanceof String string) {
         try {
            return Long.parseLong(string.trim());
         } catch (NumberFormatException ignored) {
         }
      }

      return fallback;
   }

   static boolean flag(Object value, boolean fallback) {
      return value instanceof Boolean bool ? bool : fallback;
   }

   static String quote(String value) {
      if (value == null) {
         return "null";
      }

      StringBuilder builder = new StringBuilder("\"");
      for (int i = 0; i < value.length(); i++) {
         char c = value.charAt(i);
         switch (c) {
            case '"' -> builder.append("\\\"");
            case '\\' -> builder.append("\\\\");
            case '\n' -> builder.append("\\n");
            case '\r' -> builder.append("\\r");
            case '\t' -> builder.append("\\t");
            default -> {
               if (c < 0x20) {
                  builder.append(String.format("\\u%04x", (int)c));
               } else {
                  builder.append(c);
               }
            }
         }
      }

      return builder.append('"').toString();
   }

   private Object value() {
      char c = this.peek();
      return switch (c) {
         case '{' -> this.map();
         case '[' -> this.list();
         case '"' -> this.string();
         case 't' -> {
            this.word("true");
            yield Boolean.TRUE;
         }
         case 'f' -> {
            this.word("false");
            yield Boolean.FALSE;
         }
         case 'n' -> {
            this.word("null");
            yield null;
         }
         default -> this.number();
      };
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
            throw new IllegalArgumentException("Expected comma at " + this.at);
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
            throw new IllegalArgumentException("Expected comma at " + this.at);
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

         if (c == '\\') {
            char escaped = this.next();
            builder.append(switch (escaped) {
               case '"' -> '"';
               case '\\' -> '\\';
               case '/' -> '/';
               case 'b' -> '\b';
               case 'f' -> '\f';
               case 'n' -> '\n';
               case 'r' -> '\r';
               case 't' -> '\t';
               case 'u' -> (char)Integer.parseInt("" + this.next() + this.next() + this.next() + this.next(), 16);
               default -> escaped;
            });
         } else {
            builder.append(c);
         }
      }
   }

   private Number number() {
      int start = this.at;
      if (this.peek() == '-') {
         this.at++;
      }

      while (Character.isDigit(this.peek())) {
         this.at++;
      }

      boolean decimal = false;
      if (this.peek() == '.') {
         decimal = true;
         this.at++;
         while (Character.isDigit(this.peek())) {
            this.at++;
         }
      }

      String raw = this.text.substring(start, this.at);
      return decimal ? Double.parseDouble(raw) : Long.parseLong(raw);
   }

   private void word(String expected) {
      for (int i = 0; i < expected.length(); i++) {
         if (this.next() != expected.charAt(i)) {
            throw new IllegalArgumentException("Expected " + expected);
         }
      }
   }

   private void character(char expected) {
      if (this.next() != expected) {
         throw new IllegalArgumentException("Expected " + expected);
      }
   }

   private void space() {
      while (this.at < this.text.length() && Character.isWhitespace(this.text.charAt(this.at))) {
         this.at++;
      }
   }

   private char peek() {
      return this.at < this.text.length() ? this.text.charAt(this.at) : '\0';
   }

   private char next() {
      if (this.at >= this.text.length()) {
         throw new IllegalArgumentException("Unexpected end of JSON");
      }

      return this.text.charAt(this.at++);
   }
}
