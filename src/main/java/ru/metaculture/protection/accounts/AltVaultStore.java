package ru.metaculture.protection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

final class AltVaultStore {
   private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
   private static final String WILD_ALT_VAULT = "wild-alt-vault";
   private static final int INT_VALUE = 3;
   private static final int INT_VALUE_2 = 180000;
   private static final int INT_VALUE_3 = 128;
   private static final SecureRandom SECURE_RANDOM = new SecureRandom();

   private AltVaultStore() {
   }

   static List<AltVaultStore.AltVaultStoreTimedEntry> resolve(File file) {
      List items = resolve3(file);
      if (items != null) {
         return resolve8(items);
      } else {
         List items2 = resolve3(resolve15(file));
         return items2 != null ? resolve8(items2) : List.of();
      }
   }

   static void invoke(File file, List<AltVaultStore.AltVaultStoreTimedEntry> list) {
      invoke2(file, list, resolve2(file));
   }

   static void invoke2(File file, List<AltVaultStore.AltVaultStoreTimedEntry> list, String string) {
      try {
         File file2 = file.getParentFile();
         if (file2 != null) {
            file2.mkdirs();
         }

         List items3 = resolve8(list);
         String text = resolve9(items3, string);
         byte[] byteValues = resolve14(16);
         byte[] byteValues2 = resolve14(12);
         JsonObject jsonObject2 = new JsonObject();
         jsonObject2.addProperty("version", 3);
         jsonObject2.addProperty("savedAt", System.currentTimeMillis());
         if (!text.isEmpty()) {
            jsonObject2.addProperty("lastSelectedId", text);
         }

         JsonArray jsonArray2 = new JsonArray();

         for (AltVaultStore.AltVaultStoreTimedEntry altVaultStoreTimedEntry : (List<AltVaultStore.AltVaultStoreTimedEntry>)items3) {
            JsonObject jsonObject3 = new JsonObject();
            jsonObject3.addProperty("id", altVaultStoreTimedEntry.id());
            jsonObject3.addProperty("name", altVaultStoreTimedEntry.name());
            jsonObject3.addProperty("type", altVaultStoreTimedEntry.type());
            jsonObject3.addProperty("password", altVaultStoreTimedEntry.password());
            jsonObject3.addProperty("createdAt", altVaultStoreTimedEntry.createdAt());
            jsonObject3.addProperty("lastUsedAt", altVaultStoreTimedEntry.lastUsedAt());
            jsonArray2.add(jsonObject3);
         }

         jsonObject2.add("accounts", jsonArray2);
         byte[] byteValues3 = resolve10(file, GSON.toJson(jsonObject2).getBytes(StandardCharsets.UTF_8), byteValues, byteValues2);
         JsonObject jsonObject4 = new JsonObject();
         jsonObject4.addProperty("format", "wild-alt-vault");
         jsonObject4.addProperty("version", 3);
         jsonObject4.addProperty("cipher", "AES/GCM/NoPadding");
         jsonObject4.addProperty("kdf", "PBKDF2WithHmacSHA256");
         jsonObject4.addProperty("iterations", 180000);
         jsonObject4.addProperty("salt", Base64.getEncoder().encodeToString(byteValues));
         jsonObject4.addProperty("iv", Base64.getEncoder().encodeToString(byteValues2));
         jsonObject4.addProperty("payload", Base64.getEncoder().encodeToString(byteValues3));
         if (!text.isEmpty()) {
            jsonObject4.addProperty("lastSelectedId", text);
         }

         invoke3(file, GSON.toJson(jsonObject4).getBytes(StandardCharsets.UTF_8));
      } catch (Throwable exception) {
      }
   }

   static String resolve2(File file) {
      if (file == null) {
         return "";
      } else {
         String text2 = resolve4(file);
         return !text2.isEmpty() ? text2 : resolve4(resolve15(file));
      }
   }

   static boolean check(File file) {
      if (file != null && file.exists() && file.isFile()) {
         try {
            boolean flag;
            try (FileReader fileReader = new FileReader(file, StandardCharsets.UTF_8)) {
               JsonElement jsonElement = JsonParser.parseReader(fileReader);
               if (jsonElement == null || !jsonElement.isJsonObject()) {
                  return false;
               }

               JsonObject jsonObject5 = jsonElement.getAsJsonObject();
               flag = "wild-alt-vault".equals(resolve19(jsonObject5, "format", "")) && compute2(jsonObject5, "version", 0) >= 3;
            }

            return flag;
         } catch (Throwable exception2) {
            return false;
         }
      } else {
         return false;
      }
   }

   private static List<AltVaultStore.AltVaultStoreTimedEntry> resolve3(File file) {
      if (file != null && file.exists() && file.isFile()) {
         try {
            List items4;
            try (FileReader fileReader2 = new FileReader(file, StandardCharsets.UTF_8)) {
               JsonElement jsonElement2 = JsonParser.parseReader(fileReader2);
               if (jsonElement2 == null || jsonElement2.isJsonNull()) {
                  return null;
               }

               if (jsonElement2.isJsonArray()) {
                  return resolve7(jsonElement2.getAsJsonArray());
               }

               if (!jsonElement2.isJsonObject()) {
                  return null;
               }

               JsonObject jsonObject6 = jsonElement2.getAsJsonObject();
               if ("wild-alt-vault".equals(resolve19(jsonObject6, "format", ""))) {
                  return resolve5(file, jsonObject6);
               }

               if (!jsonObject6.has("accounts") || !jsonObject6.get("accounts").isJsonArray()) {
                  return null;
               }

               items4 = resolve7(jsonObject6.getAsJsonArray("accounts"));
            }

            return items4;
         } catch (Throwable exception3) {
            return null;
         }
      } else {
         return null;
      }
   }

   private static String resolve4(File file) {
      if (file != null && file.exists() && file.isFile()) {
         try {
            String text3;
            try (FileReader fileReader3 = new FileReader(file, StandardCharsets.UTF_8)) {
               JsonElement jsonElement3 = JsonParser.parseReader(fileReader3);
               if (jsonElement3 == null || !jsonElement3.isJsonObject()) {
                  return "";
               }

               JsonObject jsonObject7 = jsonElement3.getAsJsonObject();
               String text4 = resolve19(jsonObject7, "lastSelectedId", "");
               if (!text4.isBlank()) {
                  return text4.trim();
               }

               if ("wild-alt-vault".equals(resolve19(jsonObject7, "format", ""))) {
                  JsonObject jsonObject8 = resolve6(file, jsonObject7);
                  return jsonObject8 == null ? "" : resolve19(jsonObject8, "lastSelectedId", "").trim();
               }

               text3 = resolve19(jsonObject7, "lastSelectedId", "").trim();
            }

            return text3;
         } catch (Throwable exception4) {
            return "";
         }
      } else {
         return "";
      }
   }

   private static List<AltVaultStore.AltVaultStoreTimedEntry> resolve5(File file, JsonObject jsonObject) throws Exception {
      JsonObject jsonObject9 = resolve6(file, jsonObject);
      if (jsonObject9 == null) {
         return null;
      } else {
         return jsonObject9.has("accounts") && jsonObject9.get("accounts").isJsonArray() ? resolve7(jsonObject9.getAsJsonArray("accounts")) : List.of();
      }
   }

   private static JsonObject resolve6(File file, JsonObject jsonObject) throws Exception {
      int intValue = Math.max(60000, compute2(jsonObject, "iterations", 180000));
      byte[] byteValues4 = Base64.getDecoder().decode(resolve19(jsonObject, "salt", ""));
      byte[] byteValues5 = Base64.getDecoder().decode(resolve19(jsonObject, "iv", ""));
      byte[] byteValues6 = Base64.getDecoder().decode(resolve19(jsonObject, "payload", ""));
      if (byteValues4.length >= 12 && byteValues5.length == 12 && byteValues6.length >= 24) {
         byte[] byteValues7 = resolve11(file, byteValues6, byteValues4, byteValues5, intValue);
         JsonElement jsonElement4 = JsonParser.parseString(new String(byteValues7, StandardCharsets.UTF_8));
         return !jsonElement4.isJsonObject() ? null : jsonElement4.getAsJsonObject();
      } else {
         return null;
      }
   }

   private static List<AltVaultStore.AltVaultStoreTimedEntry> resolve7(JsonArray jsonArray) {
      ArrayList arrayList = new ArrayList();

      for (JsonElement jsonElement5 : jsonArray) {
         if (jsonElement5.isJsonObject()) {
            JsonObject jsonObject10 = jsonElement5.getAsJsonObject();
            String text5 = resolve19(jsonObject10, "name", "");
            String text6 = resolve19(jsonObject10, "type", "CRACKED");
            String text7 = resolve19(jsonObject10, "password", "");
            String text8 = resolve19(jsonObject10, "id", "");
            long longValue = compute(jsonObject10, "createdAt", 0L);
            long longValue2 = compute(jsonObject10, "lastUsedAt", 0L);
            arrayList.add(new AltVaultStore.AltVaultStoreTimedEntry(text8, text5, text6, text7, longValue, longValue2));
         }
      }

      return arrayList;
   }

   private static List<AltVaultStore.AltVaultStoreTimedEntry> resolve8(List<AltVaultStore.AltVaultStoreTimedEntry> list) {
      long longValue3 = System.currentTimeMillis();
      LinkedHashMap linkedHashMap = new LinkedHashMap();

      for (AltVaultStore.AltVaultStoreTimedEntry altVaultStoreTimedEntry2 : list) {
         if (linkedHashMap.size() >= 128) {
            break;
         }

         String text9 = resolve16(altVaultStoreTimedEntry2.name());
         if (!text9.isBlank()) {
            String text10 = text9.toLowerCase(Locale.ROOT);
            if (!linkedHashMap.containsKey(text10)) {
               String text11 = resolve17(altVaultStoreTimedEntry2.type());
               String text12 = resolve20(altVaultStoreTimedEntry2.password());
               String text13 = resolve18(altVaultStoreTimedEntry2.id(), text9, text11);
               long longValue4 = altVaultStoreTimedEntry2.createdAt() > 0L ? altVaultStoreTimedEntry2.createdAt() : longValue3;
               long longValue5 = Math.max(0L, Math.min(altVaultStoreTimedEntry2.lastUsedAt(), longValue3 + 86400000L));
               linkedHashMap.put(text10, new AltVaultStore.AltVaultStoreTimedEntry(text13, text9, text11, text12, longValue4, longValue5));
            }
         }
      }

      return new ArrayList<>(linkedHashMap.values());
   }

   private static String resolve9(List<AltVaultStore.AltVaultStoreTimedEntry> list, String string) {
      String text14 = string == null ? "" : string.trim();
      if (text14.isEmpty()) {
         return "";
      } else {
         for (AltVaultStore.AltVaultStoreTimedEntry altVaultStoreTimedEntry3 : list) {
            if (text14.equals(altVaultStoreTimedEntry3.id())) {
               return text14;
            }
         }

         return "";
      }
   }

   private static byte[] resolve10(File file, byte[] bs, byte[] cs, byte[] ds) throws Exception {
      Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
      cipher.init(1, resolve12(file, cs, 180000), new GCMParameterSpec(128, ds));
      cipher.updateAAD("wild-alt-vault".getBytes(StandardCharsets.UTF_8));
      return cipher.doFinal(bs);
   }

   private static byte[] resolve11(File file, byte[] bs, byte[] cs, byte[] ds, int i) throws Exception {
      Cipher cipher2 = Cipher.getInstance("AES/GCM/NoPadding");
      cipher2.init(2, resolve12(file, cs, i), new GCMParameterSpec(128, ds));
      cipher2.updateAAD("wild-alt-vault".getBytes(StandardCharsets.UTF_8));
      return cipher2.doFinal(bs);
   }

   private static SecretKeySpec resolve12(File file, byte[] bs, int i) throws Exception {
      char[] characters = resolve13(file).toCharArray();

      SecretKeySpec secretKeySpec;
      try {
         SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
         PBEKeySpec pbeKeySpec = new PBEKeySpec(characters, bs, i, 256);
         secretKeySpec = new SecretKeySpec(secretKeyFactory.generateSecret(pbeKeySpec).getEncoded(), "AES");
      } finally {
         Arrays.fill(characters, '\u0000');
      }

      return secretKeySpec;
   }

   private static String resolve13(File file) {
      String text15 = file != null && file.getParentFile() != null ? file.getParentFile().getAbsolutePath() : "";
      return "wild-alt-vault\n"
         + resolve20(System.getProperty("user.name"))
         + "\n"
         + resolve20(System.getProperty("user.home"))
         + "\n"
         + resolve20(System.getProperty("os.name"))
         + "\n"
         + resolve20(System.getenv("COMPUTERNAME"))
         + "\n"
         + text15;
   }

   private static void invoke3(File file, byte[] bs) throws Exception {
      Path path2 = file.toPath();
      Path path3 = path2.getParent();
      if (path3 != null) {
         Files.createDirectories(path3);
      }

      Path path4 = path2.resolveSibling(path2.getFileName() + ".tmp");

      try (FileChannel fileChannel = FileChannel.open(path4, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)) {
         ByteBuffer byteBuffer = ByteBuffer.wrap(bs);

         while (byteBuffer.hasRemaining()) {
            fileChannel.write(byteBuffer);
         }

         fileChannel.force(true);
      }

      if (Files.exists(path2)) {
         Files.copy(path2, resolve15(file).toPath(), StandardCopyOption.REPLACE_EXISTING);
      }

      try {
         Files.move(path4, path2, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
      } catch (AtomicMoveNotSupportedException atomicMoveNotSupportedException) {
         Files.move(path4, path2, StandardCopyOption.REPLACE_EXISTING);
      }

      invoke4(path3);
   }

   private static void invoke4(Path path) {
      if (path != null) {
         try (FileChannel fileChannel2 = FileChannel.open(path, StandardOpenOption.READ)) {
            fileChannel2.force(true);
         } catch (Throwable exception5) {
         }
      }
   }

   private static byte[] resolve14(int i) {
      byte[] byteValues8 = new byte[i];
      SECURE_RANDOM.nextBytes(byteValues8);
      return byteValues8;
   }

   private static File resolve15(File file) {
      return new File(file.getParentFile(), file.getName() + ".bak");
   }

   private static String resolve16(String string) {
      if (string == null) {
         return "";
      } else {
         String text16 = string.trim();
         int intValue2 = text16.indexOf(64);
         if (intValue2 > 0) {
            text16 = text16.substring(0, intValue2);
         }

         text16 = text16.replaceAll("[^A-Za-z0-9_]", "");
         if (text16.length() > 16) {
            text16 = text16.substring(0, 16);
         }

         return text16;
      }
   }

   private static String resolve17(String string) {
      String text17 = string == null ? "" : string.trim().toUpperCase(Locale.ROOT);
      return "PREMIUM".equals(text17) ? "PREMIUM" : "CRACKED";
   }

   private static String resolve18(String string, String string2, String string3) {
      String text18 = string == null ? "" : string.trim();
      return text18.length() >= 16 && text18.length() <= 64 && text18.matches("[A-Za-z0-9_\\-]+")
         ? text18
         : UUID.nameUUIDFromBytes(("wild-alt-vault:" + string3 + ":" + string2.toLowerCase(Locale.ROOT)).getBytes(StandardCharsets.UTF_8)).toString();
   }

   private static String resolve19(JsonObject jsonObject, String string, String string2) {
      try {
         JsonElement jsonElement6 = jsonObject.get(string);
         return jsonElement6 != null && !jsonElement6.isJsonNull() ? jsonElement6.getAsString() : string2;
      } catch (Throwable exception6) {
         return string2;
      }
   }

   private static long compute(JsonObject jsonObject, String string, long l) {
      try {
         JsonElement jsonElement7 = jsonObject.get(string);
         return jsonElement7 != null && !jsonElement7.isJsonNull() ? jsonElement7.getAsLong() : l;
      } catch (Throwable exception7) {
         return l;
      }
   }

   private static int compute2(JsonObject jsonObject, String string, int i) {
      try {
         JsonElement jsonElement8 = jsonObject.get(string);
         return jsonElement8 != null && !jsonElement8.isJsonNull() ? jsonElement8.getAsInt() : i;
      } catch (Throwable exception8) {
         return i;
      }
   }

   private static String resolve20(String string) {
      return string == null ? "" : string;
   }

   record AltVaultStoreTimedEntry(String id, String name, String type, String password, long createdAt, long lastUsedAt) {
   }
}
