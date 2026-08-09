package ru.metaculture.protection;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.json.JSONArray;
import org.json.JSONObject;

public final class StudioLibrary {
   private static final String ASSETS_WILD_STUDIO_PRESETS = "assets/wild/studio/presets/";
   private static StudioLibrary instance;
   private final File file;
   private final List<StudioAsset> items = new ArrayList<>();
   private final Map<String, StudioAssetCategory> valuesByKey = new HashMap<>();
   private final Map<String, String> valuesByKey2 = new HashMap<>();
   private final Map<String, String> valuesByKey3 = new HashMap<>();
   private final Map<String, StudioAssetCategory> valuesByKey4 = new LinkedHashMap<>();
   private String text = "";
   private boolean flag;
   private boolean flag2;

   private StudioLibrary() {
      this.file = new File(WildClient.getFILE(), "avatars");
   }

   public static StudioLibrary resolve() {
      if (instance == null) {
         instance = new StudioLibrary();
      }

      return instance;
   }

   public File getFile() {
      return this.file;
   }

   public synchronized void invoke() {
      if (!this.flag2) {
         this.flag2 = true;

         try {
            if (!this.file.exists()) {
               this.file.mkdirs();
            }

            this.invoke13();
            this.invoke10();
            this.invoke7();
         } catch (Throwable exception) {
            System.out.println("[Studio] library init failed: " + exception.getClass().getSimpleName() + ": " + exception.getMessage());
         }
      }
   }

   public synchronized void invoke2() {
      this.invoke13();
      this.invoke7();
   }

   public synchronized List<StudioAsset> resolve2() {
      return new ArrayList<>(this.items);
   }

   public synchronized List<StudioAsset> resolve3(StudioAssetCategory studioAssetCategory) {
      ArrayList arrayList = new ArrayList();

      for (StudioAsset studioAsset : this.items) {
         if (studioAsset.getStudioAssetCategory() == studioAssetCategory) {
            arrayList.add(studioAsset);
         }
      }

      return arrayList;
   }

   public synchronized StudioAsset resolve4() {
      for (StudioAsset studioAsset2 : this.items) {
         if (studioAsset2.getText().equals(this.text)) {
            return studioAsset2;
         }
      }

      return null;
   }

   public synchronized void invoke3(StudioAsset studioAsset3) {
      this.text = studioAsset3 == null ? "" : studioAsset3.getText();
      this.flag = studioAsset3 != null;
      this.invoke14();
   }

   public synchronized boolean check() {
      return this.flag && !this.text.isEmpty();
   }

   public synchronized void setFlag(boolean bl) {
      this.flag = bl && !this.text.isEmpty();
      this.invoke14();
   }

   public synchronized void invoke4(StudioAsset studioAsset4, StudioAssetCategory studioAssetCategory2) {
      if (studioAsset4 != null && studioAssetCategory2 != null) {
         studioAsset4.setStudioAssetCategory(studioAssetCategory2);
         this.valuesByKey.put(studioAsset4.getText(), studioAssetCategory2);
         this.invoke14();
      }
   }

   public synchronized void invoke5(StudioAsset studioAsset5, String string) {
      if (studioAsset5 != null) {
         String text = string == null ? "" : string.trim();
         if (text.isEmpty()) {
            this.valuesByKey2.remove(studioAsset5.getText());
            studioAsset5.setText3((String)null);
         } else {
            this.valuesByKey2.put(studioAsset5.getText(), text);
            studioAsset5.setText3(text);
         }

         this.items.sort((studioAsset6, studioAsset7) -> studioAsset6.getText3().compareToIgnoreCase(studioAsset7.getText3()));
         this.invoke14();
      }
   }

   public synchronized void invoke6(StudioAsset studioAsset8, String string) {
      if (studioAsset8 != null) {
         String text2 = string == null ? "" : string.trim();
         studioAsset8.setText6(text2);
         if (text2.isEmpty()) {
            this.valuesByKey3.remove(studioAsset8.getText());
         } else {
            this.valuesByKey3.put(studioAsset8.getText(), text2);
         }

         this.invoke14();
      }
   }

   public synchronized boolean check2(StudioAsset studioAsset9) {
      if (studioAsset9 == null) {
         return false;
      } else {
         boolean flag = check3(studioAsset9.getFile());
         this.items.remove(studioAsset9);
         this.valuesByKey2.remove(studioAsset9.getText());
         this.valuesByKey3.remove(studioAsset9.getText());
         this.valuesByKey.remove(studioAsset9.getText());
         if (studioAsset9.getText().equals(this.text)) {
            this.text = "";
            this.flag = false;
         }

         this.invoke14();
         return flag;
      }
   }

   private static boolean check3(File file) {
      if (file == null) {
         return false;
      } else {
         File[] files = file.listFiles();
         if (files != null) {
            for (File file2 : files) {
               check3(file2);
            }
         }

         return file.delete();
      }
   }

   public synchronized String resolve5(File file, StudioAssetCategory studioAssetCategory3) {
      if (file != null && file.exists()) {
         try {
            String text3 = resolve11(resolve10(file.getName()));
            if (text3.isEmpty()) {
               text3 = "import";
            }

            File file3 = this.resolve9(text3);
            if (file.isDirectory()) {
               this.invoke12(file.toPath(), file3.toPath());
            } else {
               String text4 = resolve8(file);
               if ("rar".equals(text4) || "7z".equals(text4)) {
                  return "Это " + text4.toUpperCase(Locale.ROOT) + ", не .zip — распакуйте вручную";
               }

               if (!"zip".equals(text4)) {
                  return "Нужен .zip или папка";
               }

               try (InputStream inputStream2 = Files.newInputStream(file.toPath())) {
                  this.invoke11(inputStream2, file3);
               }
            }

            String text5 = this.resolve7(file3) + "/";
            int intValue = this.items.size();
            this.invoke7();
            int intValue2 = 0;

            for (StudioAsset studioAsset10 : this.items) {
               if (studioAsset10.getText().startsWith(text5)) {
                  if (this.valuesByKey.get(studioAsset10.getText()) == null) {
                     studioAsset10.setStudioAssetCategory(studioAssetCategory3);
                     this.valuesByKey.put(studioAsset10.getText(), studioAssetCategory3);
                  }

                  intValue2++;
               }
            }

            this.invoke14();
            return intValue2 == 0 ? "Аватары не найдены (нет avatar.json)" : "Импортировано: " + intValue2 + (this.items.size() > intValue ? "" : "");
         } catch (Throwable exception2) {
            return "Ошибка: " + exception2.getClass().getSimpleName();
         }
      } else {
         return "Файл не найден";
      }
   }

   private void invoke7() {
      this.items.clear();
      if (this.file.isDirectory()) {
         this.invoke9();
         ArrayList arrayList2 = new ArrayList();
         this.invoke8(this.file, arrayList2, 0);

         for (File file4 : (Iterable<File>)arrayList2) {
            String text6 = this.resolve7(file4);
            StudioAssetCategory studioAssetCategory4 = this.valuesByKey.get(text6);
            StudioAsset studioAsset11 = StudioAsset.resolve(text6, file4, studioAssetCategory4 != null ? studioAssetCategory4 : this.resolve6(text6));
            if (studioAsset11 != null) {
               if (studioAssetCategory4 == null) {
                  try {
                     studioAsset11.setStudioAssetCategory(studioAsset11.resolve5());
                  } catch (Throwable exception3) {
                  }
               }

               String text7 = this.valuesByKey2.get(text6);
               if (text7 != null && !text7.isEmpty()) {
                  studioAsset11.setText3(text7);
               }

               String text8 = this.valuesByKey3.get(text6);
               if (text8 != null && !text8.isEmpty()) {
                  studioAsset11.setText6(text8);
               }

               this.items.add(studioAsset11);
            }
         }

         this.items.sort((studioAsset12, studioAsset13) -> studioAsset12.getText3().compareToIgnoreCase(studioAsset13.getText3()));
      }
   }

   private void invoke8(File file, List<File> list, int i) {
      if (file != null && file.isDirectory() && i <= 8) {
         if (this.check4(file)) {
            list.add(file);
         } else {
            File[] files2 = file.listFiles();
            if (files2 != null) {
               for (File file5 : files2) {
                  if (file5.isDirectory()) {
                     this.invoke8(file5, list, i + 1);
                  }
               }
            }
         }
      }
   }

   private void invoke9() {
      File[] files3 = this.file.listFiles();
      if (files3 != null) {
         for (File file6 : files3) {
            if (file6.isFile() && file6.getName().toLowerCase(Locale.ROOT).endsWith(".zip")) {
               if (!"zip".equals(resolve8(file6))) {
                  System.out.println("[Studio] skipping non-zip archive (RAR/7z?): " + file6.getName());
               } else {
                  try {
                     String text9 = resolve11(resolve10(file6.getName()));
                     if (text9.isEmpty()) {
                        text9 = "import";
                     }

                     File file7 = this.resolve9(text9);

                     try (InputStream inputStream3 = Files.newInputStream(file6.toPath())) {
                        this.invoke11(inputStream3, file7);
                     }

                     file6.delete();
                  } catch (Throwable exception4) {
                     System.out.println("[Studio] loose import failed " + file6.getName() + ": " + exception4.getMessage());
                  }
               }
            }
         }
      }
   }

   private boolean check4(File file) {
      File[] files4 = file.listFiles();
      if (files4 == null) {
         return false;
      } else {
         for (File file8 : files4) {
            if (file8.isFile() && file8.getName().toLowerCase(Locale.ROOT).endsWith(".bbmodel")) {
               return true;
            }
         }

         return false;
      }
   }

   private StudioAssetCategory resolve6(String string) {
      for (Entry entry : this.valuesByKey4.entrySet()) {
         if (string.startsWith((String)entry.getKey() + "/") || string.equals(entry.getKey())) {
            return (StudioAssetCategory)entry.getValue();
         }
      }

      return StudioAssetCategory.MODELS;
   }

   private String resolve7(File file) {
      String text10 = this.file.getAbsolutePath();
      String text11 = file.getAbsolutePath();
      String text12 = text11.length() > text10.length() ? text11.substring(text10.length()) : text11;
      text12 = text12.replace('\\', '/');

      while (text12.startsWith("/")) {
         text12 = text12.substring(1);
      }

      return text12;
   }

   private void invoke10() {
      String text13 = this.resolve12("assets/wild/studio/presets/index.json");
      if (text13 != null) {
         JSONObject jsonObject = new JSONObject(text13);
         JSONArray jsonArray = jsonObject.optJSONArray("presets");
         if (jsonArray != null) {
            for (int intValue3 = 0; intValue3 < jsonArray.length(); intValue3++) {
               JSONObject jsonObject2 = jsonArray.optJSONObject(intValue3);
               if (jsonObject2 != null) {
                  String text14 = jsonObject2.optString("file", "");
                  StudioAssetCategory studioAssetCategory5 = StudioAssetCategory.resolve(jsonObject2.optString("category", "models"));
                  if (!text14.isEmpty()) {
                     String text15 = resolve11(resolve10(text14));
                     this.valuesByKey4.put(text15, studioAssetCategory5);
                     File file9 = new File(this.file, text15);
                     if (!file9.isDirectory()) {
                        byte[] byteValues = this.resolve13("assets/wild/studio/presets/" + text14);
                        if (byteValues != null) {
                           try {
                              this.invoke11(new ByteArrayInputStream(byteValues), file9);
                           } catch (IOException ioException) {
                              System.out.println("[Studio] preset seed failed " + text14 + ": " + ioException.getMessage());
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private static String resolve8(File file) {
      try {
         String text16;
         try (InputStream inputStream4 = Files.newInputStream(file.toPath())) {
            byte[] byteValues2 = inputStream4.readNBytes(4);
            if (byteValues2.length < 2 || byteValues2[0] != 80 || byteValues2[1] != 75) {
               if (byteValues2.length >= 4 && (byteValues2[0] & 255) == 82 && (byteValues2[1] & 255) == 97 && (byteValues2[2] & 255) == 114 && (byteValues2[3] & 255) == 33) {
                  return "rar";
               }

               if (byteValues2.length >= 4 && (byteValues2[0] & 255) == 55 && (byteValues2[1] & 255) == 122 && (byteValues2[2] & 255) == 188 && (byteValues2[3] & 255) == 175) {
                  return "7z";
               }

               return "unknown";
            }

            text16 = "zip";
         }

         return text16;
      } catch (IOException ioException2) {
         return "unknown";
      }
   }

   private void invoke11(InputStream inputStream, File file) throws IOException {
      if (!file.exists()) {
         file.mkdirs();
      }

      Path path4 = file.toPath().normalize();

      ZipEntry zipEntry;
      try (ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {
         while ((zipEntry = zipInputStream.getNextEntry()) != null) {
            Path path5 = path4.resolve(zipEntry.getName()).normalize();
            if (path5.startsWith(path4)) {
               if (zipEntry.isDirectory()) {
                  Files.createDirectories(path5);
               } else {
                  Files.createDirectories(path5.getParent());
                  Files.copy(zipInputStream, path5, StandardCopyOption.REPLACE_EXISTING);
               }

               zipInputStream.closeEntry();
            }
         }
      }
   }

   private void invoke12(Path path, Path path2) throws IOException {
      Files.walk(path).forEach(path3 -> {
         try {
            Path path6 = path2.resolve(path.relativize(path3).toString());
            if (Files.isDirectory(path3)) {
               Files.createDirectories(path6);
            } else {
               Files.createDirectories(path6.getParent());
               Files.copy(path3, path6, StandardCopyOption.REPLACE_EXISTING);
            }
         } catch (IOException ioException3) {
            throw new RuntimeException(ioException3);
         }
      });
   }

   private File resolve9(String string) {
      File file10 = new File(this.file, string);

      for (int intValue4 = 2; file10.exists(); intValue4++) {
         file10 = new File(this.file, string + "-" + intValue4);
      }

      return file10;
   }

   private static String resolve10(String string) {
      int intValue5 = string.lastIndexOf(46);
      return intValue5 > 0 ? string.substring(0, intValue5) : string;
   }

   private static String resolve11(String string) {
      return string.trim().replaceAll("[^a-zA-Z0-9._ -]", "_");
   }

   private void invoke13() {
      this.valuesByKey.clear();
      this.valuesByKey2.clear();
      this.valuesByKey3.clear();
      this.text = "";
      this.flag = false;
      File file11 = new File(this.file, "index.json");
      if (file11.isFile()) {
         try {
            JSONObject jsonObject3 = new JSONObject(new String(Files.readAllBytes(file11.toPath()), StandardCharsets.UTF_8));
            this.text = jsonObject3.optString("selected", "");
            this.flag = jsonObject3.optBoolean("equipped", !this.text.isEmpty()) && !this.text.isEmpty();
            JSONObject jsonObject4 = jsonObject3.optJSONObject("tabs");
            if (jsonObject4 != null) {
               for (String text17 : jsonObject4.keySet()) {
                  this.valuesByKey.put(text17, StudioAssetCategory.resolve(jsonObject4.optString(text17, "models")));
               }
            }

            JSONObject jsonObject5 = jsonObject3.optJSONObject("names");
            if (jsonObject5 != null) {
               for (String text18 : jsonObject5.keySet()) {
                  this.valuesByKey2.put(text18, jsonObject5.optString(text18, ""));
               }
            }

            JSONObject jsonObject6 = jsonObject3.optJSONObject("prefixes");
            if (jsonObject6 != null) {
               for (String text19 : jsonObject6.keySet()) {
                  this.valuesByKey3.put(text19, jsonObject6.optString(text19, ""));
               }
            }
         } catch (Exception exception5) {
         }
      }
   }

   private void invoke14() {
      try {
         JSONObject jsonObject7 = new JSONObject();
         jsonObject7.put("selected", this.text);
         jsonObject7.put("equipped", this.flag);
         JSONObject jsonObject8 = new JSONObject();

         for (Entry entry2 : this.valuesByKey.entrySet()) {
            jsonObject8.put((String)entry2.getKey(), ((StudioAssetCategory)entry2.getValue()).getText());
         }

         jsonObject7.put("tabs", jsonObject8);
         JSONObject jsonObject9 = new JSONObject();

         for (Entry entry3 : this.valuesByKey2.entrySet()) {
            jsonObject9.put((String)entry3.getKey(), entry3.getValue());
         }

         jsonObject7.put("names", jsonObject9);
         JSONObject jsonObject10 = new JSONObject();

         for (Entry entry4 : this.valuesByKey3.entrySet()) {
            jsonObject10.put((String)entry4.getKey(), entry4.getValue());
         }

         jsonObject7.put("prefixes", jsonObject10);
         File file12 = new File(this.file, "index.json");
         Files.write(file12.toPath(), jsonObject7.toString(2).getBytes(StandardCharsets.UTF_8));
      } catch (Exception exception6) {
      }
   }

   private String resolve12(String string) {
      byte[] byteValues3 = this.resolve13(string);
      return byteValues3 == null ? null : new String(byteValues3, StandardCharsets.UTF_8);
   }

   private byte[] resolve13(String string) {
      ClassLoader classLoader = StudioLibrary.class.getClassLoader();

      try {
         byte[] byteValues4;
         try (InputStream inputStream5 = classLoader.getResourceAsStream(string)) {
            if (inputStream5 == null) {
               return null;
            }

            byteValues4 = inputStream5.readAllBytes();
         }

         return byteValues4;
      } catch (IOException ioException4) {
         return null;
      }
   }
}
