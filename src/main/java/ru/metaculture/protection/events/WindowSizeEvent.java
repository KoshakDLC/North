package ru.metaculture.protection;

public class WindowSizeEvent extends Event {
   private final int intValue;
   private final int intValue2;

   public WindowSizeEvent(int i, int j) {
      this.intValue = i;
      this.intValue2 = j;
   }

   public int getIntValue() {
      return this.intValue;
   }

   public int getIntValue2() {
      return this.intValue2;
   }
}
