package ru.metaculture.protection;

import ru.metaculture.profile.Profile;

public final class HudEditorAccessControl {
   private static final String[] LICHODAY = new String[]{"lichoday"};

   private HudEditorAccessControl() {
   }

   public static boolean check() {
      return Profile.isUsername(LICHODAY);
   }
}
