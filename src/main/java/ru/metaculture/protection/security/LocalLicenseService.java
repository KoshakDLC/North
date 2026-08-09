package ru.metaculture.protection;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public final class LocalLicenseService {
   private static final long TIMESTAMP = Long.getLong("wild.license.cacheTtlMs", 30000L) * 1000000L;
   private static volatile LocalLicenseService.LocalLicenseServiceData localLicenseServiceData;

   private LocalLicenseService() {
   }

   public static boolean check() {
      return true;
   }

   public static boolean check2() {
      return true;
   }

   private static LocalLicenseService.LocalLicenseServiceData resolve(long l, long m) {
      long longValue = 0L;

      Path path2;
      try {
         path2 = resolve3();
      } catch (Throwable exception) {
         return new LocalLicenseService.LocalLicenseServiceData(false, longValue, l, true);
      }

      if (path2 != null && Files.exists(path2)) {
         try {
            JsonObject jsonObject = resolve2(path2);
            String text = jsonObject.get("payload").getAsString();
            String text2 = jsonObject.get("signature").getAsString();
            byte[] byteValues = Base64.getUrlDecoder().decode(text);
            byte[] byteValues2 = Base64.getUrlDecoder().decode(text2);
            if (!check3(byteValues, byteValues2, resolve4())) {
               return new LocalLicenseService.LocalLicenseServiceData(false, longValue, l, false);
            } else {
               JsonObject jsonObject2 = JsonParser.parseString(new String(byteValues, StandardCharsets.UTF_8)).getAsJsonObject();
               longValue = jsonObject2.has("validUntil") ? jsonObject2.get("validUntil").getAsLong() : 0L;
               if (longValue <= m) {
                  return new LocalLicenseService.LocalLicenseServiceData(false, longValue, l, false);
               } else {
                  String text3 = jsonObject2.has("hwidHash") ? jsonObject2.get("hwidHash").getAsString() : "";
                  if (text3.isBlank()) {
                     return new LocalLicenseService.LocalLicenseServiceData(false, longValue, l, false);
                  } else {
                     return !HwidUtils.check(text3) ? new LocalLicenseService.LocalLicenseServiceData(false, longValue, l, false) : new LocalLicenseService.LocalLicenseServiceData(true, longValue, l, false);
                  }
               }
            }
         } catch (Throwable exception2) {
            return new LocalLicenseService.LocalLicenseServiceData(false, longValue, l, false);
         }
      } else {
         return new LocalLicenseService.LocalLicenseServiceData(false, longValue, l, true);
      }
   }

   private static JsonObject resolve2(Path path) throws Exception {
      String text4 = Files.readString(path, StandardCharsets.UTF_8);
      return JsonParser.parseString(text4).getAsJsonObject();
   }

   private static Path resolve3() {
      String text5 = System.getProperty("wild.license.path");
      if (text5 != null && !text5.isBlank()) {
         return Path.of(text5);
      } else {
         String text6 = System.getenv("WILD_LICENSE_PATH");
         if (text6 != null && !text6.isBlank()) {
            return Path.of(text6);
         } else {
            String text7 = System.getenv("APPDATA");
            if (text7 != null && !text7.isBlank()) {
               Path path3 = Path.of(text7, "WildClient", "license.json");
               if (Files.exists(path3)) {
                  return path3;
               }
            }

            return Path.of(System.getProperty("user.home", "."), ".wildclient", "license.json");
         }
      }
   }

   private static PublicKey resolve4() throws Exception {
      String text8 = "-----BEGIN PUBLIC KEY-----\nMCowBQYDK2VwAyEAgqu9hOrz4JQKl2izQlnpj+d8jkT988LVfYfXPvKyt2Y=\n-----END PUBLIC KEY-----\n"
         .replace("-----BEGIN PUBLIC KEY-----", "")
         .replace("-----END PUBLIC KEY-----", "")
         .replaceAll("\\s+", "");
      byte[] byteValues3 = Base64.getDecoder().decode(text8);
      return KeyFactory.getInstance("Ed25519").generatePublic(new X509EncodedKeySpec(byteValues3));
   }

   private static boolean check3(byte[] bs, byte[] cs, PublicKey publicKey) throws Exception {
      Signature signature = Signature.getInstance("Ed25519");
      signature.initVerify(publicKey);
      signature.update(bs);
      return signature.verify(cs);
   }

   record LocalLicenseServiceData(boolean valid, long validUntil, long checkedAtNano, boolean fileMissing) {
   }
}
