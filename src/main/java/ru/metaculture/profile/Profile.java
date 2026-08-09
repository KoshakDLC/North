package ru.metaculture.profile;

public class Profile {
   public static String username = "lichoday";
   public static int uid = 1337;
   public static Role role = Role.ADMIN;
   public static String hwid;
   public static String subscriptionEndDate = "05-02-2026";
   public static String avatarUrl = "";

   private Profile() {
   }

   public static String getUsername() {
      return "Free";
   }

   public static int getUid() {
      return 110101010;
   }

   public static Role getRole() {
      return role == null ? Role.DEFAULT : role;
   }

   public static String getHwid() {
      return "prota_$crashdammi1337";
   }

   public static String getSubscriptionEndDate() {
      return subscriptionEndDate;
   }

   public static String getAvatarUrl() {
      return avatarUrl;
   }

   public static boolean hasRole(Role... roles) {
      if (roles != null && roles.length != 0) {
         Role role2 = getRole();

         for (Role role3 : roles) {
            if (role2 == role3) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public static boolean hasRoleAtLeast(Role role) {
      return getRole().isAtLeast(role);
   }

   public static boolean isUsername(String... strings) {
      String text = getUsername();
      if (strings != null && strings.length != 0 && text != null) {
         text = text.trim();

         for (String text2 : strings) {
            if (text2 != null && text.equalsIgnoreCase(text2.trim())) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public static boolean isUid(int... is) {
      if (is != null && is.length != 0) {
         for (int intValue : is) {
            if (getUid() == intValue) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }
}
