package ru.metaculture.protection;

public class NumberUtils {
   public static <T extends Number> T resolve(T number, T number2, double d) {
      double doubleValue = number.doubleValue();
      double doubleValue2 = number2.doubleValue();
      double doubleValue3 = doubleValue + d * (doubleValue2 - doubleValue);
      if (number instanceof Integer) {
         return (T)(Object)(int)Math.round(doubleValue3);
      } else if (number instanceof Double) {
         return (T)(Object)doubleValue3;
      } else if (number instanceof Float) {
         return (T)(Object)(float)doubleValue3;
      } else if (number instanceof Long) {
         return (T)(Object)Math.round(doubleValue3);
      } else if (number instanceof Short) {
         return (T)(Object)(short)Math.round(doubleValue3);
      } else if (number instanceof Byte) {
         return (T)(Object)(byte)Math.round(doubleValue3);
      } else {
         throw new IllegalArgumentException("Unsupported type: " + number.getClass().getSimpleName());
      }
   }
}
