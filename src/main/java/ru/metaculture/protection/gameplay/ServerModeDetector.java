package ru.metaculture.protection;

import lombok.Generated;

public final class ServerModeDetector implements MinecraftAccessor {
   public static String vanilla = "Vanilla";

   public static boolean check() {
      return a_.player == null || a_.world == null || a_.interactionManager == null;
   }

   public static boolean check2() {
      return vanilla.equals("CopyTime") || vanilla.equals("SpookyTime") || vanilla.equals("FunTime");
   }

   public static boolean check3() {
      return vanilla.equals("FunTime");
   }

   public static boolean check4() {
      return vanilla.equals("ReallyWorld");
   }

   public static boolean check5() {
      return vanilla.equals("HolyWorld");
   }

   public static boolean check6() {
      return vanilla.equals("Vanilla");
   }

   public static boolean check7() {
      return vanilla.equals("AresMine");
   }

   @Generated
   private ServerModeDetector() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }
}
