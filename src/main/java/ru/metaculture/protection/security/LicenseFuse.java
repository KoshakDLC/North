package ru.metaculture.protection;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.HexFormat;
import java.util.concurrent.atomic.AtomicBoolean;

public final class LicenseFuse {
   private static final SecureRandom SECURE_RANDOM = new SecureRandom();
   private static final AtomicBoolean ATOMIC_BOOLEAN = new AtomicBoolean(false);
   private static final long TIMESTAMP = Long.getLong("wild.fuse.minDelaySeconds", 21600L);
   private static final long TIMESTAMP_2 = Long.getLong("wild.fuse.maxExtraDelaySeconds", 151200L);
   private static final int INT_VALUE = Integer.getInteger("wild.fuse.minLaunches", 3);
   private static final int INT_VALUE_2 = Integer.getInteger("wild.fuse.extraLaunches", 4);

   private LicenseFuse() {
   }

   public static void invoke(String string) {
   }

   public static void invoke2() {
   }

   public static boolean check() {
      return true;
   }

   public static void invoke3() {
   }

   public static void invoke4() {
   }

   public static boolean check2(long l) {
      return true;
   }

   private static String resolve(String string) {
      try {
         MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
         String text = "wild-1.21.8-1783538716222|" + string + "|wild-fuse-v1";
         return HexFormat.of().formatHex(messageDigest.digest(text.getBytes(StandardCharsets.UTF_8)));
      } catch (Throwable exception) {
         return "";
      }
   }
}
