package ru.metaculture.protection;

import java.util.UUID;

public final class GuardState {
   public String text;
   public String text2;
   public long timestamp;
   public boolean flag;
   public long timestamp2;
   public int intValue;
   public int intValue2;
   public String text3;

   public static GuardState resolve() {
      GuardState guardState = new GuardState();
      guardState.text = UUID.randomUUID().toString();
      guardState.text2 = "wild-1.21.8-1783538716222";
      guardState.timestamp = System.currentTimeMillis() / 1000L;
      guardState.flag = false;
      guardState.timestamp2 = 0L;
      guardState.intValue = 0;
      guardState.intValue2 = 0;
      guardState.text3 = "";
      return guardState;
   }
}
