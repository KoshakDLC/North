package ru.metaculture.profile;

import com.google.gson.JsonObject;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import ru.metaculture.protection.HwidUtils;
import ru.metaculture.protection.LocalLicenseService;

public class Profile {
   private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("dd-MM-yyyy");

   public static String username = "Guest";
   public static int uid = 0;
   public static Role role = Role.DEFAULT;
   public static String hwid = "";
   public static String subscriptionEndDate = "";
   public static String avatarUrl = "";
   private static boolean loaded;

   private Profile() {
   }

   /** Loads signed license payload into the static profile fields. Safe to call multiple times. */
   public static synchronized void loadFromLicense() {
      hwid = HwidUtils.resolve();
      if (!LocalLicenseService.check()) {
         username = "Guest";
         uid = 0;
         role = Role.DEFAULT;
         subscriptionEndDate = "";
         loaded = true;
         return;
      }

      LocalLicenseService.payload().ifPresentOrElse(Profile::applyPayload, () -> {
         if ("1".equals(System.getProperty("north.license.dev"))) {
            username = System.getProperty("user.name", "Dev");
            uid = 1;
            role = Role.ADMIN;
            subscriptionEndDate = "lifetime";
         }
      });
      loaded = true;
   }

   private static void applyPayload(JsonObject payload) {
      if (payload.has("username")) {
         username = payload.get("username").getAsString();
      }

      if (payload.has("uid")) {
         uid = payload.get("uid").getAsInt();
      }

      if (payload.has("role")) {
         role = parseRole(payload.get("role").getAsString());
      }

      if (payload.has("subscriptionEndDate")) {
         subscriptionEndDate = payload.get("subscriptionEndDate").getAsString();
      } else if (payload.has("validUntil")) {
         long until = payload.get("validUntil").getAsLong();
         subscriptionEndDate = until <= 0L
            ? "lifetime"
            : LocalDate.ofInstant(Instant.ofEpochMilli(until), ZoneId.systemDefault()).format(DATE);
      }

      hwid = HwidUtils.resolve();
   }

   private static Role parseRole(String raw) {
      try {
         return Role.valueOf(raw.trim().toUpperCase(Locale.ROOT));
      } catch (Exception exception) {
         return Role.USER;
      }
   }

   public static String getUsername() {
      ensureLoaded();
      return username == null || username.isBlank() ? "Guest" : username;
   }

   public static int getUid() {
      ensureLoaded();
      return uid;
   }

   public static Role getRole() {
      ensureLoaded();
      return role == null ? Role.DEFAULT : role;
   }

   public static String getHwid() {
      ensureLoaded();
      return hwid == null || hwid.isBlank() ? HwidUtils.resolve() : hwid;
   }

   public static String getSubscriptionEndDate() {
      ensureLoaded();
      return subscriptionEndDate == null ? "" : subscriptionEndDate;
   }

   public static String getAvatarUrl() {
      return avatarUrl == null ? "" : avatarUrl;
   }

   public static boolean hasRole(Role... roles) {
      if (roles != null && roles.length != 0) {
         Role current = getRole();
         for (Role candidate : roles) {
            if (current == candidate) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public static boolean hasRoleAtLeast(Role minimum) {
      return getRole().isAtLeast(minimum);
   }

   public static boolean isUsername(String... names) {
      String current = getUsername();
      if (names != null && names.length != 0 && current != null) {
         current = current.trim();
         for (String candidate : names) {
            if (candidate != null && current.equalsIgnoreCase(candidate.trim())) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public static boolean isUid(int... ids) {
      if (ids != null && ids.length != 0) {
         int current = getUid();
         for (int candidate : ids) {
            if (current == candidate) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private static void ensureLoaded() {
      if (!loaded) {
         loadFromLicense();
      }
   }
}
