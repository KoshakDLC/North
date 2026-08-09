package ru.metaculture.protection;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import ru.metaculture.sdk.Loader;

public class CryptoUtils {
   public static final String AES = "AES";
   public static final String GUHDVBZDE4XQ5F4BXKPVXV70VY44WSUH1O6S2NZ2F9U1W9Y1VVG1MXQCUFBJM2DDUCD8NVTM0L4O1T1NN8FWWAVYLCHNNCDAGIV9UR8FPLXXF8IMATLWY4MENYTLHPB3 = "gUhDvBzdE4xq5f4BxkPvxv70VY44WsuH1O6s2nZ2F9U1w9y1VVG1mXQcUfbJM2DDUCd8NvtM0L4O1t1nn8FwwAVYlChNncdagiv9UR8FpLXXF8iMAtlWY4mEnYtLHPB3";

   public static String resolve(String string, String string2) {
      if (string == null || string.isEmpty()) {
         return "";
      }

      try {
         byte[] byteValues = new byte[12];
         new SecureRandom().nextBytes(byteValues);
         Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
         cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(Arrays.copyOf(resolve3(string2), 32), "AES"), new GCMParameterSpec(128, byteValues));
         byte[] byteValues2 = cipher.doFinal(string.getBytes(StandardCharsets.UTF_8));
         byte[] byteValues3 = new byte[byteValues.length + byteValues2.length];
         System.arraycopy(byteValues, 0, byteValues3, 0, byteValues.length);
         System.arraycopy(byteValues2, 0, byteValues3, byteValues.length, byteValues2.length);
         return "v1:" + Base64.getEncoder().encodeToString(byteValues3);
      } catch (Exception exception) {
         throw new IllegalStateException("Unable to encrypt value", exception);
      }
   }

   public static String resolve2(String string, String string2) {
      if (string == null || string.isEmpty()) {
         return "";
      }

      try {
         if (string.startsWith("v1:")) {
            byte[] byteValues4 = Base64.getDecoder().decode(string.substring(3));
            if (byteValues4.length < 29) {
               return "";
            }
            byte[] byteValues5 = Arrays.copyOfRange(byteValues4, 0, 12);
            byte[] byteValues6 = Arrays.copyOfRange(byteValues4, 12, byteValues4.length);
            Cipher cipher2 = Cipher.getInstance("AES/GCM/NoPadding");
            cipher2.init(Cipher.DECRYPT_MODE, new SecretKeySpec(Arrays.copyOf(resolve3(string2), 32), "AES"), new GCMParameterSpec(128, byteValues5));
            return new String(cipher2.doFinal(byteValues6), StandardCharsets.UTF_8);
         }

         Cipher cipher3 = Cipher.getInstance("AES/ECB/PKCS5Padding");
         cipher3.init(Cipher.DECRYPT_MODE, new SecretKeySpec(Arrays.copyOf(resolve3(string2), 16), "AES"));
         return new String(cipher3.doFinal(Base64.getDecoder().decode(string)), StandardCharsets.UTF_8);
      } catch (Exception exception2) {
         return "";
      }
   }

   public static byte[] resolve3(String string) {
      try {
         return MessageDigest.getInstance("SHA-256").digest((string == null ? "" : string).getBytes(StandardCharsets.UTF_8));
      } catch (Exception exception3) {
         throw new IllegalStateException("SHA-256 is unavailable", exception3);
      }
   }

   private CryptoUtils() {
   }

   static {
      Loader.initialize();
   }
}
