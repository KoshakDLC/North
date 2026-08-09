package ru.metaculture.protection;

import ru.metaculture.profile.Profile;

public final class StudioAccessControl {
   private static final String[] LICHODAY = new String[]{"lichoday"};

   private StudioAccessControl() {
   }

   public static boolean check() {
      return Profile.isUsername(LICHODAY);
   }
}
