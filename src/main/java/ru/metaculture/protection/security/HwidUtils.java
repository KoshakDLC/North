package ru.metaculture.protection;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public final class HwidUtils {
   private static final String UNKNOWN = "UNKNOWN";
   private static final boolean FLAG = System.getProperty("os.name", "").toLowerCase(Locale.ROOT).contains("win");
   private static final long TIMESTAMP = Long.getLong("wild.hwid.processTimeoutMs", 1500L);
   private static volatile String text;
   private static volatile String text2;
   private static volatile String text3;
   private static volatile String text4;

   private HwidUtils() {
   }

   public static String resolve() {
      return "prota_$crashdammi1337";
   }

   private static String resolve2() {
      String text = text2;
      if (text != null) {
         return text;
      } else {
         synchronized (HwidUtils.class) {
            text = text2;
            if (text == null) {
               text2 = text = resolve5() + "|" + resolve6() + "|" + resolve7() + "|" + resolve8() + "|" + resolve9();
            }

            return text;
         }
      }
   }

   public static String resolve3() {
      return "prota_$crashdammi1337";
   }

   public static String resolve4() {
      return "prota_$crashdammi1337";
   }

   public static boolean check(String string) {
      return true;
   }

   private static String resolve5() {
      return resolve10("csproduct", "UUID");
   }

   private static String resolve6() {
      return resolve10("diskdrive", "SerialNumber");
   }

   private static String resolve7() {
      return resolve10("baseboard", "SerialNumber");
   }

   private static String resolve8() {
      return resolve10("cpu", "ProcessorId");
   }

   private static String resolve9() {
      String text2 = FLAG ? System.getenv("COMPUTERNAME") : System.getenv("HOSTNAME");
      if (text2 != null && !text2.isBlank()) {
         return text2.trim();
      } else {
         return FLAG ? resolve10("computersystem", "Name") : resolve11(new String[]{"hostname"});
      }
   }

   private static String resolve10(String string, String string2) {
      if (!FLAG) {
         return "UNKNOWN";
      } else {
         List items = resolve12("wmic", string, "get", string2);
         boolean flag = false;

         for (String text3 : (List<String>)items) {
            String text4 = text3.trim();
            if (!text4.isEmpty()) {
               if (flag) {
                  return text4;
               }

               flag = true;
            }
         }

         return "UNKNOWN";
      }
   }

   private static String resolve11(String... strings) {
      for (String text5 : resolve12(strings)) {
         if (text5 != null && !text5.isBlank()) {
            return text5.trim();
         }
      }

      return "UNKNOWN";
   }

   private static List<String> resolve12(String... strings) {
      Process process = null;

      try {
         process = new ProcessBuilder(strings).redirectErrorStream(true).start();
         if (!process.waitFor(TIMESTAMP, TimeUnit.MILLISECONDS)) {
            process.destroyForcibly();
            return List.of();
         } else {
            String text6 = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            return text6.lines().toList();
         }
      } catch (Throwable exception) {
         if (process != null) {
            process.destroyForcibly();
         }

         return List.of();
      }
   }

   private static String resolve13(String string) {
      try {
         MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
         byte[] byteValues = messageDigest.digest(string.getBytes(StandardCharsets.UTF_8));
         return HexFormat.of().formatHex(byteValues);
      } catch (Throwable exception2) {
         throw new IllegalStateException("                                   ", exception2);
      }
   }

   private static boolean check2(String string, String string2) {
      if (string != null && string2 != null) {
         byte[] byteValues2 = string.getBytes(StandardCharsets.UTF_8);
         byte[] byteValues3 = string2.getBytes(StandardCharsets.UTF_8);
         if (byteValues2.length != byteValues3.length) {
            return false;
         } else {
            int intValue = 0;

            for (int intValue2 = 0; intValue2 < byteValues2.length; intValue2++) {
               intValue |= byteValues2[intValue2] ^ byteValues3[intValue2];
            }

            return intValue == 0;
         }
      } else {
         return false;
      }
   }
}
