package ru.metaculture.protection;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.UUID;
import javax.crypto.Cipher;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource.PSpecified;

public final class FingerprintCrypto {
   public static final String SERVER_KEY1 = "server-key-1";
   static final int INT_VALUE = 446;
   private static final String BEGIN_PUBLIC_KEY_MIICIJANBGKQHKIG9W0BAQEFAAOCAG8AMIICCGKCAGEA0QOLNE_HVCXSFNKWPKOD_DOHO6OOAKOXKVLWMTSBRFZPJJKEFDIW_SFDW0YJTQIZUEKMF5EZGQZTLXKBI8JQ_GK_PX6QGMNEARJF2V5W1IST2XTXAAS5LEFBWTDTUT2VLTDL5LCG3KZA3PNAOIAA32E8GYE_ME7LEI6LZXHMVIPMLBWYODB4O9QBKS1IX8TDBWQNTG30UIJYWAQ9ZOEF_KGE0AMY9SNHTENHI_NVQCT486UOHVLSSQEGGDGJJ5JEAWJICUXVWALNJRDRN_RFJ4VPUPAYIK12TIIBU4JEH5KVIEWTMVY4OR0Q9RXLMZBHBF0S6NELCAXY2CMOCL8LK_IPCHHIAKYG1WECBTFA_YQW_F6IEZI3ME5ESB_WAHPCFLRJI0H17CGBBINR4S3_DF_LCEXVNER33WKNCRAUNVSA0NWBQIFJJBS3DFKODRG11CVT1NWJXFP1MOVJZXXXOHK_JGLMEUKPGYBC5IDIDWV5_IGFXOOUZHKPPDAUNTDRCQQS4F9ES0DSUQ1Z04X8Z_YG_UQ2EFBRA_K3H8SLZ4ME8D9XH6FQJLGCPZZNHP72JKOUPTFXWPNUKL8YSI7XF57HTZCIGCZPZ1DXXYPNNJXIYGZD9AKNPXWAUEZQXTVZVBJDCL_UGDMYPWEJLCFXZG_OJWLNWSM4E_MDVE0DS8V_PKCAWEAAQ_END_PUBLIC_KEY = "-----BEGIN PUBLIC KEY-----\nMIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEA0QoLnE+hVCxsFnkwpKOD\nDOho6OoakoXkVlWMTSBRFzPJJkeFdiw++SfdW0YJtQIZuekmf5eZGqZTLXKBI8Jq\ngk/pX6qGmNeARjF2V5W1isT2xTxAAS5LefbWTDTuT2vLtdL5lcG3KZa3PnAoiaA3\n2E8gyE/ME/7LEI6lZXhmVIPMLBWyOdB4O9QBks1iX8tDbwQNTG30UIjYWAq9ZoeF\nkGe0amY9snhTEnhI+NvqCT486uOhVLsSQeggDgjj5jEAwJicUxVwALnJRDRn+rfJ\n4vPUpaYik12tIIbu4jEH5KVieWtMvY4or0Q9RxlMzBhbf0s6nElcAXY2cmocl8LK\niPCHhiaKyG1wEcbTFA+YqW/f6iEzi3Me5eSb/WAhpcFLRJi0H17cgBbINr4S3+DF\nLCEXVNEr33WKncrauNvsa0nwBQIfjJBS3DfKODRg11cvT1NWJxFP1MoVJzxxXoHk\nJGLMEuKPGYbC5IdidWV5+iGfxOoUzhKppDauntDRCqqS4F9eS0DsuQ1Z04x8Z/YG\nUq+2eFbrA/k3H8SLz4me8D9XH6fQJlGcpzZnhP7/2jKOuptfxWpnukL8Ysi7xF5+\n7htZciGCZpZ1DXxYpnNjxIYGzD9aKNpXWAUeZqXtvzvBjDcl/UGdMyPWeJlCfXZg\noJwlnWSm4E/mdVe0DS8V/PkCAwEAAQ==\n-----END PUBLIC KEY-----";
   public static final boolean FLAG = check();
   private static volatile PublicKey publicKey;

   private FingerprintCrypto() {
   }

   public static FingerprintCrypto.FingerprintCryptoTimedEntry resolve(String string) {
      byte[] byteValues = string.getBytes(StandardCharsets.UTF_8);
      if (byteValues.length > 446) {
         throw new FingerprintCrypto.FingerprintCryptoVariant3(byteValues.length, 446);
      } else {
         byte[] byteValues2 = resolve4(byteValues, resolve2());
         String text = Base64.getUrlEncoder().withoutPadding().encodeToString(byteValues2);
         return new FingerprintCrypto.FingerprintCryptoTimedEntry(1, "server-key-1", text, System.currentTimeMillis() / 1000L, UUID.randomUUID().toString());
      }
   }

   public static void invoke() {
      if (FLAG) {
         String text2 = "FAKE-SMBIOS|FAKE-DISK|FAKE-BOARD|FAKE-CPU|FAKE-DEVICE";
         System.out.println("[FingerprintCrypto] ── self-test ──────────────────────────");

         boolean flag;
         try {
            resolve2();
            flag = true;
         } catch (Exception exception) {
            flag = false;
            System.out.println("[FingerprintCrypto] RSA public key loaded: false — " + exception.getMessage());
            System.out.println("[FingerprintCrypto] ── self-test FAILED ──────────────────");
            return;
         }

         System.out.println("[FingerprintCrypto] RSA public key loaded: " + flag);
         System.out.println("[FingerprintCrypto] RSA algorithm: RSA-OAEP-SHA256");

         FingerprintCrypto.FingerprintCryptoTimedEntry fingerprintCryptoTimedEntry;
         try {
            fingerprintCryptoTimedEntry = resolve(text2);
         } catch (Exception exception2) {
            System.out.println("[FingerprintCrypto] encrypt() FAILED — " + exception2.getMessage());
            System.out.println("[FingerprintCrypto] ── self-test FAILED ──────────────────");
            return;
         }

         System.out.println("[FingerprintCrypto] encryptedPayload length: " + fingerprintCryptoTimedEntry.encryptedPayload().length());
         System.out.println("[FingerprintCrypto] requestId:               " + fingerprintCryptoTimedEntry.requestId());
         System.out.println("[FingerprintCrypto] timestamp:               " + fingerprintCryptoTimedEntry.timestamp());
         System.out
            .printf(
               "[FingerprintCrypto] DTO preview: {\"v\":%d,\"kid\":\"%s\",\"encryptedPayload\":\"%s...\",\"timestamp\":%d,\"requestId\":\"%s\"}%n",
               fingerprintCryptoTimedEntry.v(),
               fingerprintCryptoTimedEntry.kid(),
               fingerprintCryptoTimedEntry.encryptedPayload().substring(0, Math.min(24, fingerprintCryptoTimedEntry.encryptedPayload().length())),
               fingerprintCryptoTimedEntry.timestamp(),
               fingerprintCryptoTimedEntry.requestId()
            );
         System.out.println("[FingerprintCrypto] ── self-test OK ─────────────────────────");
      }
   }

   static PublicKey resolve2() {
      PublicKey publicKey2 = publicKey;
      if (publicKey2 != null) {
         return publicKey2;
      } else {
         synchronized (FingerprintCrypto.class) {
            publicKey2 = publicKey;
            if (publicKey2 != null) {
               return publicKey2;
            } else {
               publicKey = resolve3(
                  "-----BEGIN PUBLIC KEY-----\nMIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEA0QoLnE+hVCxsFnkwpKOD\nDOho6OoakoXkVlWMTSBRFzPJJkeFdiw++SfdW0YJtQIZuekmf5eZGqZTLXKBI8Jq\ngk/pX6qGmNeARjF2V5W1isT2xTxAAS5LefbWTDTuT2vLtdL5lcG3KZa3PnAoiaA3\n2E8gyE/ME/7LEI6lZXhmVIPMLBWyOdB4O9QBks1iX8tDbwQNTG30UIjYWAq9ZoeF\nkGe0amY9snhTEnhI+NvqCT486uOhVLsSQeggDgjj5jEAwJicUxVwALnJRDRn+rfJ\n4vPUpaYik12tIIbu4jEH5KVieWtMvY4or0Q9RxlMzBhbf0s6nElcAXY2cmocl8LK\niPCHhiaKyG1wEcbTFA+YqW/f6iEzi3Me5eSb/WAhpcFLRJi0H17cgBbINr4S3+DF\nLCEXVNEr33WKncrauNvsa0nwBQIfjJBS3DfKODRg11cvT1NWJxFP1MoVJzxxXoHk\nJGLMEuKPGYbC5IdidWV5+iGfxOoUzhKppDauntDRCqqS4F9eS0DsuQ1Z04x8Z/YG\nUq+2eFbrA/k3H8SLz4me8D9XH6fQJlGcpzZnhP7/2jKOuptfxWpnukL8Ysi7xF5+\n7htZciGCZpZ1DXxYpnNjxIYGzD9aKNpXWAUeZqXtvzvBjDcl/UGdMyPWeJlCfXZg\noJwlnWSm4E/mdVe0DS8V/PkCAwEAAQ==\n-----END PUBLIC KEY-----"
               );
               return publicKey;
            }
         }
      }
   }

   private static PublicKey resolve3(String string) {
      String text3 = string.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "").replaceAll("\\s+", "");

      byte[] byteValues3;
      try {
         byteValues3 = Base64.getDecoder().decode(text3);
      } catch (IllegalArgumentException illegalArgumentException) {
         throw new FingerprintCrypto.FingerprintCryptoVariant2("PEM contains invalid Base64", illegalArgumentException);
      }

      try {
         return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(byteValues3));
      } catch (Exception exception3) {
         throw new FingerprintCrypto.FingerprintCryptoVariant2("Cannot parse RSA-4096 public key: " + exception3.getMessage(), exception3);
      }
   }

   private static byte[] resolve4(byte[] bs, PublicKey publicKey) {
      try {
         Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPPadding");
         OAEPParameterSpec oaepParameterSpec = new OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256, PSpecified.DEFAULT);
         cipher.init(1, publicKey, oaepParameterSpec);
         return cipher.doFinal(bs);
      } catch (Exception exception4) {
         throw new FingerprintCrypto.FingerprintCryptoVariant("RSA-OAEP-SHA256 encryption failed: " + exception4.getMessage(), exception4);
      }
   }

   private static boolean check() {
      String text4 = System.getProperty("wild.crypto.selftest");
      return text4 != null ? Boolean.parseBoolean(text4.trim()) : "true".equalsIgnoreCase(System.getenv("WILD_CRYPTO_SELFTEST"));
   }

   public record FingerprintCryptoTimedEntry(int v, String kid, String encryptedPayload, long timestamp, String requestId) {
   }

   public static final class FingerprintCryptoVariant extends RuntimeException {
      public FingerprintCryptoVariant(String string, Throwable throwable) {
         super(string, throwable);
      }
   }

   public static final class FingerprintCryptoVariant2 extends RuntimeException {
      public FingerprintCryptoVariant2(String string, Throwable throwable) {
         super(string, throwable);
      }
   }

   public static final class FingerprintCryptoVariant3 extends RuntimeException {
      public FingerprintCryptoVariant3(int i, int j) {
         super("Payload too large for RSA-4096-OAEP-SHA256: " + i + " bytes (max " + j + ")");
      }
   }
}
