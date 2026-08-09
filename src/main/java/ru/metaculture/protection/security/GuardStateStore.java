package ru.metaculture.protection;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HexFormat;
import java.util.List;
import java.util.UUID;

public final class GuardStateStore {
   private static final Gson GSON = new Gson();
   private static volatile boolean flag;

   private GuardStateStore() {
   }

   public static GuardState resolve() {
      flag = false;
      GuardState guardState = null;
      boolean flag = false;
      boolean flag2 = false;

      for (Path path : resolve4()) {
         if (Files.exists(path)) {
            flag = true;

            try {
               String text = Files.readString(path, StandardCharsets.UTF_8);
               GuardStateStore.GuardStateStoreData guardStateStoreData = resolve3(text);
               if (guardStateStoreData != null && guardStateStoreData.state != null) {
                  GuardState guardState2 = resolve7(guardStateStoreData.state, guardStateStoreData.legacy);
                  if (guardState == null || compute(guardState2) > compute(guardState)) {
                     guardState = guardState2;
                  }
               } else {
                  flag2 = true;
               }
            } catch (Throwable exception) {
               flag2 = true;
            }
         }
      }

      if (guardState == null) {
         flag = flag && flag2;
         guardState = GuardState.resolve();
         invoke(guardState);
      } else {
         invoke(guardState);
      }

      return resolve7(guardState, false);
   }

   public static void invoke(GuardState guardState3) {
      resolve7(guardState3, false);
      String text2 = resolve2(guardState3);

      for (Path path2 : resolve5()) {
         try {
            Files.createDirectories(path2.getParent());
            Path path3 = path2.resolveSibling(
               path2.getFileName() + "." + ProcessHandle.current().pid() + "." + Thread.currentThread().getId() + "." + System.nanoTime() + ".tmp"
            );
            Files.writeString(path3, text2, StandardCharsets.UTF_8);

            try {
               Files.move(path3, path2, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            } catch (IOException ioException) {
               Files.move(path3, path2, StandardCopyOption.REPLACE_EXISTING);
            }
         } catch (IOException ioException2) {
         }
      }
   }

   public static boolean isFlag() {
      return flag;
   }

   private static long compute(GuardState guardState4) {
      long longValue = guardState4.flag ? Math.max(guardState4.timestamp2, 1L) : 0L;
      return Math.max(guardState4.timestamp, longValue);
   }

   private static String resolve2(GuardState guardState5) {
      String text3 = GSON.toJson(guardState5);
      String text4 = Base64.getUrlEncoder().withoutPadding().encodeToString(text3.getBytes(StandardCharsets.UTF_8));
      String text5 = resolve6(text4 + "|" + resolve8() + "|" + resolve11(2));
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("v", 2);
      jsonObject.addProperty("data", text4);
      jsonObject.addProperty("sum", text5);
      return GSON.toJson(jsonObject);
   }

   private static GuardStateStore.GuardStateStoreData resolve3(String string) {
      JsonObject jsonObject2 = JsonParser.parseString(string).getAsJsonObject();
      String text6 = jsonObject2.get("data").getAsString();
      String text7 = jsonObject2.get("sum").getAsString();
      byte[] byteValues = Base64.getUrlDecoder().decode(text6);
      GuardState guardState6 = (GuardState)GSON.fromJson(new String(byteValues, StandardCharsets.UTF_8), GuardState.class);
      String text8 = resolve6(text6 + "|" + resolve8() + "|" + resolve11(2));
      if (check(text7, text8)) {
         return new GuardStateStore.GuardStateStoreData(guardState6, false);
      } else {
         String text9 = resolve6(text6 + "|" + resolve9() + "|" + resolve11(2));
         if (check(text7, text9)) {
            return new GuardStateStore.GuardStateStoreData(guardState6, false);
         } else {
            String text10 = resolve6(text6 + "|" + resolve10() + "|" + resolve11(2));
            if (check(text7, text10)) {
               return new GuardStateStore.GuardStateStoreData(guardState6, false);
            } else {
               String text11 = guardState6 == null ? "" : String.valueOf(guardState6.text2);
               if (!text11.isBlank()) {
                  String text12 = resolve6(text6 + "|" + text11 + "|" + resolve11(1));
                  if (check(text7, text12)) {
                     return new GuardStateStore.GuardStateStoreData(guardState6, true);
                  }
               }

               return null;
            }
         }
      }
   }

   private static List<Path> resolve4() {
      ArrayList arrayList = new ArrayList();
      arrayList.addAll(resolve5());
      String text13 = System.getenv("APPDATA");
      String text14 = System.getenv("LOCALAPPDATA");
      String text15 = System.getProperty("user.home", ".");
      if (text13 != null && !text13.isBlank()) {
         arrayList.add(Path.of(text13, "WildClient", "state.dat"));
      }

      if (text14 != null && !text14.isBlank()) {
         arrayList.add(Path.of(text14, "WildClient", "cache.dat"));
      }

      arrayList.add(Path.of(text15, ".wildclient", "state.dat"));
      arrayList.add(Path.of(text15, ".minecraft", "wildclient", "state.dat"));
      return arrayList;
   }

   private static List<Path> resolve5() {
      return List.of(WildClient.getFILE().toPath().resolve("auth").resolve("state.dat"));
   }

   private static String resolve6(String string) {
      try {
         MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
         return HexFormat.of().formatHex(messageDigest.digest(string.getBytes(StandardCharsets.UTF_8)));
      } catch (Throwable exception2) {
         throw new IllegalStateException(exception2);
      }
   }

   private static boolean check(String string, String string2) {
      if (string != null && string2 != null) {
         byte[] byteValues2 = string.getBytes(StandardCharsets.UTF_8);
         byte[] byteValues3 = string2.getBytes(StandardCharsets.UTF_8);
         if (byteValues2.length != byteValues3.length) {
            return false;
         } else {
            int intValue = 0;

            for (int intValue2 = 0; intValue2 < byteValues2.length; intValue2++) {
               intValue |= byteValues2[intValue2] ^ byteValues3[intValue2];
            }

            return intValue == 0;
         }
      } else {
         return false;
      }
   }

   private static GuardState resolve7(GuardState guardState7, boolean bl) {
      if (guardState7 == null) {
         guardState7 = GuardState.resolve();
      }

      if (guardState7.text == null || guardState7.text.isBlank()) {
         guardState7.text = UUID.randomUUID().toString();
      }

      String text16 = guardState7.text2 == null ? "" : guardState7.text2;
      if (!text16.isBlank() && !text16.equals("wild-1.21.8-1783538716222")) {
         guardState7.flag = false;
         guardState7.timestamp2 = 0L;
         guardState7.intValue = 0;
         guardState7.intValue2 = 0;
         guardState7.text3 = "";
      }

      if (bl && !text16.isBlank() && !text16.equals("wild-1.21.8-1783538716222") && check2(guardState7, "E4", text16)) {
         guardState7.flag = false;
         guardState7.timestamp2 = 0L;
         guardState7.intValue = 0;
         guardState7.intValue2 = 0;
         guardState7.text3 = "";
      }

      guardState7.text2 = "wild-1.21.8-1783538716222";
      guardState7.timestamp = Math.max(0L, guardState7.timestamp);
      guardState7.timestamp2 = Math.max(0L, guardState7.timestamp2);
      guardState7.intValue = Math.max(0, guardState7.intValue);
      guardState7.intValue2 = Math.max(0, guardState7.intValue2);
      if (guardState7.text3 == null) {
         guardState7.text3 = "";
      }

      return guardState7;
   }

   private static boolean check2(GuardState guardState8, String string, String string2) {
      String text17 = resolve6(string2 + "|" + string + "|wild-fuse-v1");
      return check(String.valueOf(guardState8.text3), text17);
   }

   private static String resolve8() {
      return resolve6("wild|state|seal|2");
   }

   private static String resolve9() {
      String text18 = "-----BEGIN PUBLIC KEY-----\nMCowBQYDK2VwAyEAgqu9hOrz4JQKl2izQlnpj+d8jkT988LVfYfXPvKyt2Y=\n-----END PUBLIC KEY-----\n"
         .replace("-----BEGIN PUBLIC KEY-----", "")
         .replace("-----END PUBLIC KEY-----", "")
         .replaceAll("\\s+", "");
      return resolve6(text18 + "|state|2");
   }

   private static String resolve10() {
      String text19 = "-----BEGIN PUBLIC KEY-----\nMCowBQYDK2VwAyEAgqu9hOrz4JQKl2izQlnpj+d8jkT988LVfYfXPvKyt2Y=\n-----END PUBLIC KEY-----\n"
         .replace("-----BEGIN PUBLIC KEY-----", "")
         .replace("-----END PUBLIC KEY-----", "")
         .replaceAll("\\s+", "");
      return resolve6(text19 + "|state|1.21.8");
   }

   private static String resolve11(int i) {
      return "wild-state-v" + i;
   }

   record GuardStateStoreData(GuardState state, boolean legacy) {
   }
}
