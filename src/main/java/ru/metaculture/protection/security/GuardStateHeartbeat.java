package ru.metaculture.protection;

public final class GuardStateHeartbeat {
   private static final long TIMESTAMP = 300L;
   private static final long TIMESTAMP_2 = Long.getLong("wild.guard.stateSaveIntervalSeconds", 60L);
   private static volatile long timestamp;

   private GuardStateHeartbeat() {
   }

   public static void invoke(long l) {
      GuardState guardState = GuardStateStore.resolve();
      if (guardState.timestamp <= 0L || l + 300L >= guardState.timestamp) {
         if (l > guardState.timestamp && l - Math.max(guardState.timestamp, timestamp) >= TIMESTAMP_2) {
            guardState.timestamp = l;
            guardState.text2 = "wild-1.21.8-1783538716222";
            GuardStateStore.invoke(guardState);
            timestamp = l;
         }
      }
   }
}
