package ru.metaculture.protection;

public final class MenuVisualPreferences {
   private MenuVisualPreferences() {
   }

   public static boolean check() {
      try {
         return !MenuModule.OTKLYUCHIT_BLYUR.isEnabled();
      } catch (Throwable exception) {
         return true;
      }
   }

   public static boolean check2() {
      try {
         return !MenuModule.UPROSCHYONNYE_TENI_HUD.isEnabled();
      } catch (Throwable exception2) {
         return true;
      }
   }

   public static boolean check3() {
      try {
         return !MenuModule.PROPUSKAT_CHASTITSY_KLIENTA.isEnabled();
      } catch (Throwable exception3) {
         return true;
      }
   }

   public static boolean check4() {
      try {
         return MenuModule.BYSTRYE_ANIMATSII.isEnabled();
      } catch (Throwable exception4) {
         return false;
      }
   }

   public static double measure() {
      return check4() ? 0.55 : 1.0;
   }

   public static float measure2() {
      return check2() ? 1.0F : 0.45F;
   }

   public static float measure3() {
      return check2() ? 1.0F : 0.6F;
   }
}
