package ru.metaculture.protection;

public final class ProtectionHandler {
   private ProtectionHandler() {
   }

   public static void checkAccess() {
      try {
         AccessGuard.invoke();
      } catch (GuardException guardException) {
         throw resolve(guardException);
      }
   }

   public static void invoke(Runnable runnable) {
      try {
         runnable.run();
      } catch (GuardException guardException2) {
         throw resolve(guardException2);
      }
   }

   public static RuntimeException resolve(GuardException guardException3) {
      Runtime.getRuntime();
      boolean flag = false;
      return guardException3;
   }
}
