package ru.metaculture.protection;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

public final class StudioAsset {
   private final String text;
   private final File file;
   private final File file2;
   private final File file3;
   private final String text2;
   private String text3;
   private final String text4;
   private final String text5;
   private StudioAssetCategory studioAssetCategory;
   private String text6 = "";
   private StudioModel studioModel;
   private boolean flag;
   private String text7;

   public StudioAsset(String string, File file, File file2, File file3, String string2, String string3, String string4, StudioAssetCategory studioAssetCategory) {
      this.text = string;
      this.file = file;
      this.file2 = file2;
      this.file3 = file3;
      this.text2 = string2;
      this.text3 = string2;
      this.text4 = string3;
      this.text5 = string4;
      this.studioAssetCategory = studioAssetCategory == null ? StudioAssetCategory.MODELS : studioAssetCategory;
   }

   public static StudioAsset resolve(String string, File file, StudioAssetCategory studioAssetCategory2) {
      if (file != null && file.isDirectory()) {
         File file4 = resolve2(file);
         if (file4 == null) {
            return null;
         } else {
            File file5 = new File(file, "avatar.png");
            if (!file5.isFile()) {
               file5 = null;
            }

            String text = resolve3(file.getName());
            String text2 = "";
            String text3 = "";
            File file6 = new File(file, "avatar.json");
            if (file6.isFile()) {
               try {
                  JSONObject jsonObject = new JSONObject(new String(Files.readAllBytes(file6.toPath()), StandardCharsets.UTF_8));
                  text = resolve3(jsonObject.optString("name", text));
                  text3 = jsonObject.optString("color", "");
                  JSONArray jsonArray = jsonObject.optJSONArray("authors");
                  if (jsonArray != null && jsonArray.length() > 0) {
                     StringBuilder stringBuilder = new StringBuilder();

                     for (int intValue = 0; intValue < jsonArray.length(); intValue++) {
                        if (intValue > 0) {
                           stringBuilder.append(", ");
                        }

                        stringBuilder.append(resolve3(jsonArray.optString(intValue, "")));
                     }

                     text2 = stringBuilder.toString();
                  } else {
                     text2 = resolve3(jsonObject.optString("author", ""));
                  }
               } catch (Exception exception) {
               }
            }

            return new StudioAsset(string, file, file4, file5, text.isEmpty() ? file.getName() : text, text2, text3, studioAssetCategory2);
         }
      } else {
         return null;
      }
   }

   private static File resolve2(File file) {
      File[] files = file.listFiles();
      if (files == null) {
         return null;
      } else {
         File file7 = null;
         File file8 = null;
         long longValue = -1L;

         for (File file9 : files) {
            if (file9.isFile()) {
               String text4 = file9.getName().toLowerCase();
               if (text4.endsWith(".bbmodel") && !text4.contains("hud")) {
                  if (text4.equals("model.bbmodel")) {
                     file7 = file9;
                  }

                  if (file9.length() > longValue) {
                     longValue = file9.length();
                     file8 = file9;
                  }
               }
            }
         }

         if (file7 != null) {
            return file7;
         } else {
            return file8 != null ? file8 : resolve4(file, ".bbmodel");
         }
      }
   }

   private static String resolve3(String string) {
      return string == null ? "" : string.replaceAll("§.", "").replaceAll("&[0-9A-Fa-fK-Ok-or]", "").trim();
   }

   private static File resolve4(File file, String string) {
      File[] files2 = file.listFiles();
      if (files2 == null) {
         return null;
      } else {
         for (File file10 : files2) {
            if (file10.isFile() && file10.getName().toLowerCase().endsWith(string)) {
               return file10;
            }
         }

         return null;
      }
   }

   public String getText() {
      return this.text;
   }

   public File getFile() {
      return this.file;
   }

   public File getFile3() {
      return this.file3;
   }

   public String getText3() {
      return this.text3;
   }

   public String getText2() {
      return this.text2;
   }

   void setText3(String string) {
      this.text3 = string != null && !string.trim().isEmpty() ? string.trim() : this.text2;
   }

   public String getText4() {
      return this.text4;
   }

   public String getText5() {
      return this.text5;
   }

   public StudioAssetCategory getStudioAssetCategory() {
      return this.studioAssetCategory;
   }

   void setStudioAssetCategory(StudioAssetCategory studioAssetCategory3) {
      this.studioAssetCategory = studioAssetCategory3 == null ? StudioAssetCategory.MODELS : studioAssetCategory3;
   }

   public String getText6() {
      return this.text6;
   }

   void setText6(String string) {
      this.text6 = string == null ? "" : string.trim();
   }

   public boolean check() {
      return this.resolve7() != null;
   }

   public StudioAssetCategory resolve5() {
      StudioModel studioModel = this.resolve7();
      if (studioModel == null) {
         return StudioAssetCategory.MODELS;
      } else {
         HashSet hashSet = new HashSet();

         for (StudioModel.StudioModelState studioModelState : studioModel.getItems2()) {
            invoke(studioModelState, hashSet);
         }

         boolean flag = hashSet.contains("body") || hashSet.contains("torso");
         boolean flag2 = hashSet.contains("leftleg") || hashSet.contains("rightleg") || hashSet.contains("left_leg") || hashSet.contains("right_leg");
         boolean flag3 = hashSet.contains("leftarm") || hashSet.contains("rightarm") || hashSet.contains("left_arm") || hashSet.contains("right_arm");
         boolean flag4 = hashSet.contains("head");
         if (!flag || !flag2 && !flag3) {
            if (flag4 && !flag && !flag2) {
               return StudioAssetCategory.ITEMS;
            } else {
               return !flag && !flag2 && !flag3 && !flag4 ? StudioAssetCategory.PETS : StudioAssetCategory.MODELS;
            }
         } else {
            return StudioAssetCategory.MODELS;
         }
      }
   }

   private static void invoke(StudioModel.StudioModelState studioModelState2, Set<String> set) {
      if (studioModelState2.getText() != null) {
         set.add(studioModelState2.getText().toLowerCase());
      }

      for (StudioModel.StudioModelState studioModelState3 : studioModelState2.getItems()) {
         invoke(studioModelState3, set);
      }
   }

   public String resolve6() {
      this.resolve7();
      return this.text7;
   }

   public StudioModel resolve7() {
      if (this.flag) {
         return this.studioModel;
      } else {
         this.flag = true;

         try {
            String text5 = new String(Files.readAllBytes(this.file2.toPath()), StandardCharsets.UTF_8);
            this.studioModel = StudioModelLoader.resolve(text5);
            if (this.studioModel.getItems2().isEmpty()) {
               this.text7 = "Пустая модель";
            }
         } catch (Throwable exception2) {
            this.text7 = exception2.getClass().getSimpleName() + ": " + exception2.getMessage();
            this.studioModel = null;
         }

         return this.studioModel;
      }
   }
}
