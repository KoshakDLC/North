package ru.metaculture.protection;

import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.Locale;

/**
 * Machine fingerprint shared with NorthLoader. The short id is shown in the UI; licenses bind to
 * {@code SHA-256(shortId)}.
 */
public final class HwidUtils {
   private static volatile String shortId;
   private static volatile String hash;

   private HwidUtils() {
   }

   /** Short stable id (16 hex chars), same algorithm as {@code Config.hardwareId()} in the loader. */
   public static String resolve() {
      String cached = shortId;
      if (cached != null) {
         return cached;
      }

      synchronized (HwidUtils.class) {
         if (shortId == null) {
            shortId = computeShortId();
         }

         return shortId;
      }
   }

   public static String resolve3() {
      return resolve();
   }

   public static String resolve4() {
      return hashOf(resolve());
   }

   public static boolean check(String expectedHash) {
      if (expectedHash == null || expectedHash.isBlank()) {
         return false;
      }

      String actual = hashOf(resolve());
      return constantEquals(actual, expectedHash.trim().toLowerCase(Locale.ROOT));
   }

   private static String computeShortId() {
      try {
         String seed = System.getProperty("user.name", "?") + "|" + hostName() + "|" + System.getProperty("os.arch", "?");
         byte[] digest = MessageDigest.getInstance("SHA-256").digest(seed.getBytes(StandardCharsets.UTF_8));
         StringBuilder builder = new StringBuilder();
         for (int i = 0; i < 8; i++) {
            builder.append(String.format("%02x", digest[i]));
         }

         return builder.toString();
      } catch (Exception exception) {
         return "unknown";
      }
   }

   private static String hostName() {
      String name = System.getenv("COMPUTERNAME");
      if (name == null || name.isBlank()) {
         name = System.getenv("HOSTNAME");
      }

      if (name == null || name.isBlank()) {
         try {
            name = InetAddress.getLocalHost().getHostName();
         } catch (Exception exception) {
            name = "host";
         }
      }

      return name;
   }

   private static String hashOf(String value) {
      String cached = hash;
      if (cached != null && value.equals(resolve())) {
         return cached;
      }

      try {
         byte[] digest = MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8));
         String hex = HexFormat.of().formatHex(digest);
         if (value.equals(resolve())) {
            hash = hex;
         }

         return hex;
      } catch (Exception exception) {
         throw new IllegalStateException("hwid hash failed", exception);
      }
   }

   private static boolean constantEquals(String left, String right) {
      if (left == null || right == null || left.length() != right.length()) {
         return false;
      }

      int mix = 0;
      for (int i = 0; i < left.length(); i++) {
         mix |= left.charAt(i) ^ right.charAt(i);
      }

      return mix == 0;
   }
}
