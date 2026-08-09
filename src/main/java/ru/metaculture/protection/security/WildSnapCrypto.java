package ru.metaculture.protection;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.NamedParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.Mac;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public final class WildSnapCrypto {
   private static final byte[] BYTES = new byte[]{87, 83, 78, 49};
   private static final byte[] BYTES_2 = "WildSnap-v1".getBytes(StandardCharsets.UTF_8);
   private static final byte[] BYTES_3 = "wildsnap/aes-gcm".getBytes(StandardCharsets.UTF_8);
   private static final String MCOWBQYDK2VUAYEAKIMZDBTOBE4IJOYMUCYJJRR36RPEC_PSXOYJ9NSDR38 = "MCowBQYDK2VuAyEAKimzdBToBe4IjoYMuCYjJrr36rpeC+pSXoyJ9NSdR38=";
   private static final SecureRandom SECURE_RANDOM = new SecureRandom();

   private WildSnapCrypto() {
   }

   public static byte[] resolve(byte[] bs) throws Exception {
      KeyFactory keyFactory = KeyFactory.getInstance("X25519");
      PublicKey publicKey2 = keyFactory.generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode("MCowBQYDK2VuAyEAKimzdBToBe4IjoYMuCYjJrr36rpeC+pSXoyJ9NSdR38=")));
      return resolve2(bs, publicKey2);
   }

   static byte[] resolve2(byte[] bs, PublicKey publicKey) throws Exception {
      KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("X25519");
      keyPairGenerator.initialize(NamedParameterSpec.X25519);
      KeyPair keyPair = keyPairGenerator.generateKeyPair();
      KeyAgreement keyAgreement = KeyAgreement.getInstance("X25519");
      keyAgreement.init(keyPair.getPrivate());
      keyAgreement.doPhase(publicKey, true);
      byte[] byteValues = keyAgreement.generateSecret();
      byte[] byteValues2 = resolve4(byteValues, keyPair.getPublic().getEncoded());
      byte[] byteValues3 = new byte[12];
      SECURE_RANDOM.nextBytes(byteValues3);
      Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
      cipher.init(1, new SecretKeySpec(byteValues2, "AES"), new GCMParameterSpec(128, byteValues3));
      byte[] byteValues4 = cipher.doFinal(bs);
      ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream(byteValues4.length + 128);
      byteArrayOutputStream2.writeBytes(BYTES);
      invoke(byteArrayOutputStream2, keyPair.getPublic().getEncoded().length);
      byteArrayOutputStream2.writeBytes(keyPair.getPublic().getEncoded());
      byteArrayOutputStream2.write(byteValues3.length);
      byteArrayOutputStream2.writeBytes(byteValues3);
      invoke2(byteArrayOutputStream2, byteValues4.length);
      byteArrayOutputStream2.writeBytes(byteValues4);
      Arrays.fill(byteValues, (byte)0);
      Arrays.fill(byteValues2, (byte)0);
      return byteArrayOutputStream2.toByteArray();
   }

   public static byte[] resolve3(byte[] bs, byte[] cs) throws Exception {
      ByteBuffer byteBuffer = ByteBuffer.wrap(bs);

      for (byte byteValue : BYTES) {
         if (byteBuffer.get() != byteValue) {
            throw new IllegalArgumentException("bad wildsnap magic");
         }
      }

      int intValue = Short.toUnsignedInt(byteBuffer.getShort());
      byte[] byteValues5 = new byte[intValue];
      byteBuffer.get(byteValues5);
      int intValue2 = Byte.toUnsignedInt(byteBuffer.get());
      byte[] byteValues6 = new byte[intValue2];
      byteBuffer.get(byteValues6);
      int intValue3 = byteBuffer.getInt();
      byte[] byteValues7 = new byte[intValue3];
      byteBuffer.get(byteValues7);
      KeyFactory keyFactory2 = KeyFactory.getInstance("X25519");
      PublicKey publicKey3 = keyFactory2.generatePublic(new X509EncodedKeySpec(byteValues5));
      PrivateKey privateKey = keyFactory2.generatePrivate(new PKCS8EncodedKeySpec(cs));
      KeyAgreement keyAgreement2 = KeyAgreement.getInstance("X25519");
      keyAgreement2.init(privateKey);
      keyAgreement2.doPhase(publicKey3, true);
      byte[] byteValues8 = keyAgreement2.generateSecret();
      byte[] byteValues9 = resolve4(byteValues8, byteValues5);
      Cipher cipher2 = Cipher.getInstance("AES/GCM/NoPadding");
      cipher2.init(2, new SecretKeySpec(byteValues9, "AES"), new GCMParameterSpec(128, byteValues6));
      byte[] byteValues10 = cipher2.doFinal(byteValues7);
      Arrays.fill(byteValues8, (byte)0);
      Arrays.fill(byteValues9, (byte)0);
      return byteValues10;
   }

   private static byte[] resolve4(byte[] bs, byte[] cs) throws Exception {
      Mac mac = Mac.getInstance("HmacSHA256");
      mac.init(new SecretKeySpec(BYTES_2, "HmacSHA256"));
      byte[] byteValues11 = mac.doFinal(bs);
      mac.init(new SecretKeySpec(byteValues11, "HmacSHA256"));
      mac.update(BYTES_3);
      mac.update(cs);
      mac.update((byte)1);
      byte[] byteValues12 = mac.doFinal();
      Arrays.fill(byteValues11, (byte)0);
      return Arrays.copyOf(byteValues12, 32);
   }

   private static void invoke(ByteArrayOutputStream byteArrayOutputStream, int i) {
      byteArrayOutputStream.write(i >>> 8 & 0xFF);
      byteArrayOutputStream.write(i & 0xFF);
   }

   private static void invoke2(ByteArrayOutputStream byteArrayOutputStream, int i) {
      byteArrayOutputStream.write(i >>> 24 & 0xFF);
      byteArrayOutputStream.write(i >>> 16 & 0xFF);
      byteArrayOutputStream.write(i >>> 8 & 0xFF);
      byteArrayOutputStream.write(i & 0xFF);
   }
}
