package north.license;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HexFormat;

/** Ed25519 helpers for signing license payloads. */
final class Crypto {
   private Crypto() {
   }

   static KeyPair generate() throws Exception {
      return KeyPairGenerator.getInstance("Ed25519").generateKeyPair();
   }

   static void savePair(Path directory, KeyPair pair) throws Exception {
      Files.createDirectories(directory);
      Files.writeString(directory.resolve("private.key"), Base64.getEncoder().encodeToString(pair.getPrivate().getEncoded()), StandardCharsets.UTF_8);
      Files.writeString(directory.resolve("public.key"), Base64.getEncoder().encodeToString(pair.getPublic().getEncoded()), StandardCharsets.UTF_8);
      Files.writeString(
         directory.resolve("public.pem"),
         "-----BEGIN PUBLIC KEY-----\n" + Base64.getEncoder().encodeToString(pair.getPublic().getEncoded()) + "\n-----END PUBLIC KEY-----\n",
         StandardCharsets.UTF_8
      );
   }

   static PrivateKey loadPrivate(Path file) throws Exception {
      String raw = Files.readString(file, StandardCharsets.UTF_8).replaceAll("\\s+", "");
      return KeyFactory.getInstance("Ed25519").generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(raw)));
   }

   static PublicKey loadPublic(Path file) throws Exception {
      String raw = Files.readString(file, StandardCharsets.UTF_8)
         .replace("-----BEGIN PUBLIC KEY-----", "")
         .replace("-----END PUBLIC KEY-----", "")
         .replaceAll("\\s+", "");
      return KeyFactory.getInstance("Ed25519").generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(raw)));
   }

   static String signBase64Url(byte[] payload, PrivateKey key) throws Exception {
      Signature signature = Signature.getInstance("Ed25519");
      signature.initSign(key);
      signature.update(payload);
      return Base64.getUrlEncoder().withoutPadding().encodeToString(signature.sign());
   }

   static String sha256Hex(String value) {
      try {
         byte[] digest = MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8));
         return HexFormat.of().formatHex(digest);
      } catch (Exception exception) {
         throw new IllegalStateException(exception);
      }
   }

   static String encodePayload(byte[] payload) {
      return Base64.getUrlEncoder().withoutPadding().encodeToString(payload);
   }

   static String publicPem(PublicKey key) {
      return "-----BEGIN PUBLIC KEY-----\n" + Base64.getEncoder().encodeToString(key.getEncoded()) + "\n-----END PUBLIC KEY-----\n";
   }
}
