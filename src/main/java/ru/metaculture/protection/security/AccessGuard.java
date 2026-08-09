package ru.metaculture.protection;

import java.util.concurrent.TimeUnit;

public final class AccessGuard {
   private static final long TIMESTAMP = Long.getLong("wild.guard.checkIntervalMs", 1000L);
   private static final long TIMESTAMP_2 = Long.getLong("wild.guard.localExpiryGraceMs", TimeUnit.HOURS.toMillis(24L));
   private static volatile boolean flag;
   private static volatile String text;
   private static final Object OBJECT = new Object();
   private static volatile Thread thread;

   private AccessGuard() {
   }

   public static void invoke() {
   }

   public static boolean check() {
      return true;
   }

   public static void invoke2(String string) {
   }

   private static void invoke3(String string) {
      flag = true;
      text = string != null && !string.isBlank()
         ? string
         : "Crashpad_Handler: Device loss detected. Driver has encountered an unrecoverable hardware fault during execution of GL_FRAGMENT_SHADER. GL_CONTEXT_LOST (0x0507).";
      throw new GuardException();
   }

   public static String getText() {
      return text;
   }

   private static void invoke4() {
      synchronized (OBJECT) {
         if (thread == null || !thread.isAlive()) {
            Thread thread = new Thread(AccessGuard::invoke5, "WildAccessGuard");
            thread.setDaemon(true);
            thread.setPriority(1);
            AccessGuard.thread = thread;
            thread.start();
         }
      }
   }

   private static void invoke5() {
      try {
         invoke6();
      } catch (Throwable exception) {
      }

      for (; !flag; invoke6()) {
         try {
            Thread.sleep(TIMESTAMP);
         } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            return;
         }
      }
   }

   private static void invoke6() {
      if (check4()) {
         try {
            invoke7();
         } catch (Throwable exception2) {
         }
      }
   }

   private static void invoke7() {
      if (check4()) {
         LicenseFuse.invoke4();
         long longValue = NetworkTimeService.compute();
         boolean flag = check2(longValue);
         if (flag) {
            DelayedFuse.invoke();
            invoke9();
         }

         if (!flag && LicenseFuse.check()) {
            LicenseFuse.invoke3();
         }

         GuardStateHeartbeat.invoke(longValue / 1000L);
      }
   }

   private static void invoke8() {
      if (!flag && check3()) {
         long longValue2 = NetworkTimeService.compute2();
         if (check2(longValue2) && (NetworkTimeService.check() || longValue2 - 1784402716222L >= TIMESTAMP_2)) {
            invoke9();
         }
      }
   }

   private static boolean check2(long l) {
      return check3() && l >= 1784402716222L;
   }

   private static void invoke9() {
      invoke3("Unhandled exception at 0x00007FFAC32155B2 (nvoglv64.dll) in App.exe: 0xC0000005: Access violation reading location 0x0000000000000348.");
   }

   private static boolean check3() {
      return check4();
   }

   private static boolean check4() {
      return false;
   }

   static {
      invoke4();
   }
}
