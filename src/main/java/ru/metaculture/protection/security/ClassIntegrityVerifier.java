package ru.metaculture.protection;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.LinkedHashMap;
import java.util.Map;

public final class ClassIntegrityVerifier {
   private ClassIntegrityVerifier() {
   }

   public static boolean check() {
      return true;
   }

   public static Map<String, String> resolve() {
      LinkedHashMap linkedHashMap = new LinkedHashMap();
      String[] texts = new String[]{
         "org.wild.auth.BuildInfo",
         "org.wild.auth.LocalAccessGuard",
         "org.wild.auth.LocalLicenseService",
         "org.wild.auth.DelayedFuse",
         "org.wild.auth.FingerprintCrypto",
         "org.wild.auth.HeartbeatService",
         "org.wild.auth.HwidUtils"
      };

      for (String text : texts) {
         String text2 = resolve3(text);
         if (text2 != null) {
            linkedHashMap.put(text, text2);
         }
      }

      return linkedHashMap;
   }

   public static void invoke() {
   }

   private static Map<String, String> resolve2() {
      LinkedHashMap linkedHashMap2 = new LinkedHashMap();

      for (String text3 : BuildInfo.TEXT) {
         int intValue = text3.indexOf(58);
         if (intValue > 0 && intValue < text3.length() - 1) {
            linkedHashMap2.put(text3.substring(0, intValue).trim(), text3.substring(intValue + 1).trim());
         }
      }

      return linkedHashMap2;
   }

   private static String resolve3(String string) {
      String text4 = string.replace('.', '/') + ".class";

      try {
         String text5;
         try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(text4)) {
            if (inputStream == null) {
               return null;
            }

            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] byteValues = new byte[8192];

            int intValue2;
            while ((intValue2 = inputStream.read(byteValues)) >= 0) {
               if (intValue2 > 0) {
                  messageDigest.update(byteValues, 0, intValue2);
               }
            }

            text5 = HexFormat.of().formatHex(messageDigest.digest());
         }

         return text5;
      } catch (Throwable exception) {
         return null;
      }
   }

   private static boolean check2(String string, String string2) {
      if (string != null && string2 != null) {
         byte[] byteValues2 = string.getBytes(StandardCharsets.UTF_8);
         byte[] byteValues3 = string2.getBytes(StandardCharsets.UTF_8);
         if (byteValues2.length != byteValues3.length) {
            return false;
         } else {
            int intValue3 = 0;

            for (int intValue4 = 0; intValue4 < byteValues2.length; intValue4++) {
               intValue3 |= byteValues2[intValue4] ^ byteValues3[intValue4];
            }

            return intValue3 == 0;
         }
      } else {
         return false;
      }
   }
}
