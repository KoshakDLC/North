package ru.metaculture.protection;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.client.MinecraftClient;
import org.json.JSONArray;
import org.json.JSONObject;

public final class ShaderPresetStore {
   private static final ShaderPresetStore INSTANCE = new ShaderPresetStore();
   private static final String ACTIVE_JSON = "active.json";
   private static final String THEME_JSON = ".theme.json";
   private static final String WIFD = ".wifd";
   private static final String JSON = ".json";
   private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
   private final File file;
   private final Map<ShaderSurface, String> valuesByKey = new EnumMap<>(ShaderSurface.class);
   private final Map<String, SavedShaderPreset> valuesByKey2 = new HashMap<>();
   private boolean flag;

   private ShaderPresetStore() {
      File file3 = WildClient.INSTANCE != null && WildClient.INSTANCE.file != null
         ? new File(WildClient.INSTANCE.file, "foundry")
         : new File(WildClient.getFILE(), "foundry");
      this.file = file3;
      if (!file3.exists() && !file3.mkdirs()) {
         System.out.println("[FoundryStorage] cannot create directory " + file3.getAbsolutePath());
      }
   }

   public static ShaderPresetStore getINSTANCE() {
      return INSTANCE;
   }

   public synchronized void invoke(ShaderNodeRegistry shaderNodeRegistry) {
      if (!this.flag) {
         this.flag = true;
         this.valuesByKey2.clear();
         if (this.file.isDirectory()) {
            this.invoke3();
            File[] files = this.file.listFiles((file, string) -> string.endsWith(".theme.json"));
            ArrayList arrayList = new ArrayList();
            if (files != null) {
               for (File file4 : files) {
                  if (!check4(file4.getName())) {
                     arrayList.add(file4);
                  } else {
                     try {
                        SavedShaderPreset savedShaderPreset = this.resolve15(file4, new JSONObject(resolve18(file4)));
                        this.valuesByKey2.put(savedShaderPreset.getText(), savedShaderPreset);
                     } catch (Throwable exception) {
                        System.out.println("[FoundryStorage] skip " + file4.getName() + ": " + exception.getMessage());
                     }
                  }
               }
            }

            boolean flag = false;

            for (File file5 : (List<File>)arrayList) {
               flag |= this.check2(file5, shaderNodeRegistry);
            }

            if (flag) {
               this.invoke4();
            }
         }
      }
   }

   public synchronized List<SavedShaderPreset> resolve() {
      ArrayList arrayList2 = new ArrayList<>(this.valuesByKey2.values());
      arrayList2.sort((object, object2) -> Long.compare(((SavedShaderPreset)object2).getTimestamp2(), ((SavedShaderPreset)object).getTimestamp2()));
      return arrayList2;
   }

   public synchronized List<SavedShaderPreset> resolve2(ShaderSurface shaderSurface) {
      if (shaderSurface == null) {
         return Collections.emptyList();
      } else {
         ArrayList arrayList3 = new ArrayList();

         for (SavedShaderPreset savedShaderPreset2 : this.valuesByKey2.values()) {
            if (shaderSurface.getText().equals(savedShaderPreset2.getText3())) {
               arrayList3.add(savedShaderPreset2);
            }
         }

         arrayList3.sort((object3, object4) -> Long.compare(((SavedShaderPreset)object4).getTimestamp2(), ((SavedShaderPreset)object3).getTimestamp2()));
         return arrayList3;
      }
   }

   public synchronized SavedShaderPreset resolve3(String string) {
      return string == null ? null : this.valuesByKey2.get(string);
   }

   public synchronized SavedShaderPreset resolve4(ShaderSurface shaderSurface2, ShaderNode shaderNode, String string, String string2) {
      if (shaderSurface2 != null && shaderNode != null) {
         long longValue = System.currentTimeMillis();
         String text = string != null && !string.isBlank() ? string.trim() : shaderNode.getShaderTemplate().getText();
         if (text == null || text.isBlank()) {
            text = ShaderStylePreset.resolve();
         }

         ShaderTemplate shaderTemplate = shaderNode.getShaderTemplate();
         shaderTemplate.invoke5(text, resolve19());
         shaderTemplate.setText(text);
         shaderTemplate.setTimestamp2(longValue);
         shaderTemplate.invoke3("local");
         String text2 = WildThemeCodec.resolve(shaderNode);
         SavedShaderPreset savedShaderPreset3 = string2 == null ? null : this.valuesByKey2.get(string2);
         if (savedShaderPreset3 == null) {
            savedShaderPreset3 = new SavedShaderPreset(
               this.resolve17(),
               text,
               shaderSurface2.getText(),
               text2,
               shaderTemplate.getText2(),
               shaderTemplate.getText3(),
               shaderTemplate.getCustom(),
               "user",
               "saved",
               shaderTemplate.getTimestamp(),
               longValue,
               shaderTemplate.isFlag()
            );
            this.valuesByKey2.put(savedShaderPreset3.getText(), savedShaderPreset3);
         } else {
            savedShaderPreset3.invoke(text);
            savedShaderPreset3.setText3(shaderSurface2.getText());
            savedShaderPreset3.setText4(text2);
            savedShaderPreset3.setText5(shaderTemplate.getText2());
            savedShaderPreset3.setText6(shaderTemplate.getText3());
            savedShaderPreset3.setText7(shaderTemplate.getCustom());
            savedShaderPreset3.setText8("user");
            savedShaderPreset3.setText9("saved");
            savedShaderPreset3.setTimestamp(shaderTemplate.getTimestamp());
            savedShaderPreset3.setTimestamp2(longValue);
            savedShaderPreset3.setFlag(shaderTemplate.isFlag());
         }

         try {
            this.invoke5(savedShaderPreset3, shaderTemplate);
         } catch (IOException ioException) {
            System.out.println("[FoundryStorage] save failed: " + ioException.getMessage());
         }

         return savedShaderPreset3;
      } else {
         return null;
      }
   }

   public synchronized SavedShaderPreset resolve5(ShaderSurface shaderSurface3, ShaderNode shaderNode2, String string) {
      if (shaderSurface3 != null && shaderNode2 != null) {
         SavedShaderPreset savedShaderPreset4 = string == null ? null : this.valuesByKey2.get(string);
         String text3 = savedShaderPreset4 == null ? shaderNode2.getShaderTemplate().getText() : savedShaderPreset4.getText2();
         return this.resolve4(shaderSurface3, shaderNode2, text3, string);
      } else {
         return null;
      }
   }

   public synchronized File resolve6() {
      File file6 = new File(this.file, "shaders");
      if (!file6.exists()) {
         file6.mkdirs();
      }

      return file6;
   }

   public synchronized File resolve7() {
      if (!this.file.exists()) {
         this.file.mkdirs();
      }

      return this.file;
   }

   public synchronized File resolve8(ShaderSurface shaderSurface4, ShaderNode shaderNode3, String string) {
      if (shaderNode3 == null) {
         return null;
      } else {
         ShaderSurface shaderSurface5 = shaderSurface4 == null ? ShaderSurface.resolve4(shaderNode3.getPreview()) : shaderSurface4;
         String text4 = string != null && !string.isBlank() ? string.trim() : shaderNode3.getShaderTemplate().getText();
         if (text4 == null || text4.isBlank()) {
            text4 = ShaderStylePreset.resolve();
         }

         ShaderTemplate shaderTemplate2 = shaderNode3.getShaderTemplate();
         shaderTemplate2.invoke5(text4, resolve19());
         shaderTemplate2.setText(text4);
         shaderTemplate2.setTimestamp2(System.currentTimeMillis());
         shaderTemplate2.invoke3("shared");
         String text5 = resolve20(text4);
         String text6 = LocalDateTime.now().format(DATE_TIME_FORMATTER);
         File file7 = new File(this.resolve6(), text5 + "_" + text6 + ".wifd");

         try {
            shaderNode3.invoke2(shaderSurface5.getText());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("version", 4);
            jsonObject.put("type", "wild_foundry");
            jsonObject.put("target", shaderSurface5.getText());
            jsonObject.put("metadata", WildThemeCodec.resolve5(shaderTemplate2));
            jsonObject.put("graph", WildThemeCodec.resolve3(shaderNode3));
            Files.write(file7.toPath(), jsonObject.toString(2).getBytes(StandardCharsets.UTF_8));
            return file7;
         } catch (Throwable exception2) {
            System.out.println("[FoundryStorage] shared export failed: " + exception2.getMessage());
            return null;
         }
      }
   }

   public synchronized List<File> resolve9() {
      File file8 = this.resolve6();
      File[] files2 = file8.listFiles((file, string) -> {
         if (string == null) {
            return false;
         } else {
            String var2x = string.toLowerCase(Locale.ROOT);
            return var2x.endsWith(".wifd") || var2x.endsWith(".json");
         }
      });
      if (files2 != null && files2.length != 0) {
         ArrayList arrayList4 = new ArrayList<>(List.of(files2));
         arrayList4.sort((file, file2) -> Long.compare(((File)file2).lastModified(), ((File)file).lastModified()));
         return arrayList4;
      } else {
         return List.of();
      }
   }

   public synchronized ShaderNode resolve10(File file, ShaderNodeRegistry shaderNodeRegistry2) {
      if (file != null && shaderNodeRegistry2 != null && file.isFile()) {
         try {
            String text7 = resolve18(file);
            JSONObject jsonObject2 = new JSONObject(text7);
            JSONObject jsonObject3 = jsonObject2.optJSONObject("graph");
            if (jsonObject3 != null) {
               ShaderNode shaderNode4 = WildThemeCodec.resolve4(jsonObject3, shaderNodeRegistry2);
               String text8 = jsonObject2.optString("target", "");
               if (!text8.isBlank()) {
                  shaderNode4.invoke2(text8);
               }

               ShaderTemplate shaderTemplate3 = WildThemeCodec.resolve6(jsonObject2.optJSONObject("metadata"), jsonObject2);
               shaderTemplate3.invoke3("imported");
               shaderTemplate3.invoke5(jsonObject2.optString("displayName", ShaderStylePreset.resolve()), jsonObject2.optString("author", resolve19()));
               shaderNode4.invoke(shaderTemplate3);
               return shaderNode4;
            }

            String text9 = jsonObject2.optString("wildTheme", "");
            if (!text9.isBlank()) {
               ShaderNode shaderNode5 = WildThemeCodec.resolve2(text9, shaderNodeRegistry2);
               ShaderTemplate shaderTemplate4 = WildThemeCodec.resolve6(jsonObject2.optJSONObject("metadata"), jsonObject2);
               shaderTemplate4.invoke3("imported");
               shaderTemplate4.invoke5(jsonObject2.optString("displayName", ShaderStylePreset.resolve()), jsonObject2.optString("author", resolve19()));
               shaderNode5.invoke(shaderTemplate4);
               return shaderNode5;
            }

            if (jsonObject2.has("nodes") && jsonObject2.has("connections")) {
               ShaderNode shaderNode6 = WildThemeCodec.resolve4(jsonObject2, shaderNodeRegistry2);
               shaderNode6.getShaderTemplate().invoke3("imported");
               shaderNode6.getShaderTemplate().invoke5(jsonObject2.optString("displayName", ShaderStylePreset.resolve()), jsonObject2.optString("author", resolve19()));
               return shaderNode6;
            }
         } catch (Throwable exception3) {
            System.out.println("[FoundryStorage] shared import failed: " + exception3.getMessage());
         }

         return null;
      } else {
         return null;
      }
   }

   public synchronized boolean check(String string) {
      if (string == null) {
         return false;
      } else {
         SavedShaderPreset savedShaderPreset5 = this.valuesByKey2.remove(string);
         if (savedShaderPreset5 == null) {
            return false;
         } else {
            for (Entry entry : new ArrayList<>(this.valuesByKey.entrySet())) {
               if (string.equals(entry.getValue())) {
                  this.valuesByKey.remove(entry.getKey());
               }
            }

            try {
               Files.deleteIfExists(new File(this.file, string).toPath());
            } catch (IOException ioException2) {
            }

            this.invoke4();
            return true;
         }
      }
   }

   public synchronized ShaderNode resolve11(String string, ShaderNodeRegistry shaderNodeRegistry3) {
      SavedShaderPreset savedShaderPreset6 = this.valuesByKey2.get(string);
      if (savedShaderPreset6 == null) {
         return null;
      } else {
         try {
            ShaderNode shaderNode7 = WildThemeCodec.resolve2(savedShaderPreset6.getText4(), shaderNodeRegistry3);
            shaderNode7.getShaderTemplate().invoke5(savedShaderPreset6.getText2(), savedShaderPreset6.getText5().isBlank() ? resolve19() : savedShaderPreset6.getText5());
            shaderNode7.getShaderTemplate().setText(savedShaderPreset6.getText2());
            if (!savedShaderPreset6.getText5().isBlank()) {
               shaderNode7.getShaderTemplate().setText2(savedShaderPreset6.getText5());
            }

            shaderNode7.getShaderTemplate().setText3(savedShaderPreset6.getText6());
            shaderNode7.getShaderTemplate().invoke2(savedShaderPreset6.getText7());
            shaderNode7.getShaderTemplate().setTimestamp(savedShaderPreset6.getTimestamp());
            shaderNode7.getShaderTemplate().setTimestamp2(savedShaderPreset6.getTimestamp2());
            shaderNode7.getShaderTemplate().setFlag(savedShaderPreset6.isFlag());
            return shaderNode7;
         } catch (Throwable exception4) {
            return null;
         }
      }
   }

   public synchronized void invoke2(ShaderSurface shaderSurface6, String string) {
      if (shaderSurface6 != null) {
         if (string == null || string.isBlank()) {
            this.valuesByKey.remove(shaderSurface6);
         } else if (this.valuesByKey2.containsKey(string)) {
            this.valuesByKey.put(shaderSurface6, string);
         }

         this.invoke4();
      }
   }

   public synchronized String resolve12(ShaderSurface shaderSurface7) {
      return this.valuesByKey.get(shaderSurface7);
   }

   public synchronized SavedShaderPreset resolve13(ShaderSurface shaderSurface8) {
      String text10 = this.valuesByKey.get(shaderSurface8);
      return text10 == null ? null : this.valuesByKey2.get(text10);
   }

   public synchronized JSONArray resolve14() {
      JSONArray jsonArray = new JSONArray();

      for (SavedShaderPreset savedShaderPreset7 : this.resolve()) {
         JSONObject jsonObject4 = new JSONObject();
         jsonObject4.put("fileName", savedShaderPreset7.getText());
         jsonObject4.put("displayName", savedShaderPreset7.getText2());
         jsonObject4.put("target", savedShaderPreset7.getText3());
         jsonObject4.put("author", savedShaderPreset7.getText5());
         jsonObject4.put("description", savedShaderPreset7.getText6());
         jsonObject4.put("complexity", savedShaderPreset7.getText7());
         jsonObject4.put("source", savedShaderPreset7.getText8());
         jsonObject4.put("compileStatus", savedShaderPreset7.getText9());
         jsonObject4.put("createdAt", savedShaderPreset7.getTimestamp());
         jsonObject4.put("updatedAt", savedShaderPreset7.getTimestamp2());
         jsonObject4.put("favorite", savedShaderPreset7.isFlag());
         jsonArray.put(jsonObject4);
      }

      return jsonArray;
   }

   private void invoke3() {
      File file9 = new File(this.file, "active.json");
      if (file9.exists()) {
         try {
            JSONObject jsonObject5 = new JSONObject(resolve18(file9));

            for (ShaderSurface shaderSurface9 : ShaderSurface.values()) {
               String text11 = jsonObject5.optString(shaderSurface9.getText(), null);
               if (text11 != null && !text11.isBlank()) {
                  this.valuesByKey.put(shaderSurface9, text11);
               }
            }
         } catch (Throwable exception5) {
            System.out.println("[FoundryStorage] cannot read active bindings: " + exception5.getMessage());
         }
      }
   }

   private void invoke4() {
      try {
         JSONObject jsonObject6 = new JSONObject();

         for (Entry entry2 : this.valuesByKey.entrySet()) {
            jsonObject6.put(((ShaderSurface)entry2.getKey()).getText(), entry2.getValue());
         }

         Files.write(new File(this.file, "active.json").toPath(), jsonObject6.toString(2).getBytes(StandardCharsets.UTF_8));
      } catch (IOException ioException3) {
         System.out.println("[FoundryStorage] cannot persist active bindings: " + ioException3.getMessage());
      }
   }

   private SavedShaderPreset resolve15(File file, JSONObject jSONObject) {
      String text12 = jSONObject.optString("wildTheme", "");
      ShaderTemplate shaderTemplate5 = WildThemeCodec.resolve6(jSONObject.optJSONObject("metadata"), jSONObject);
      if (shaderTemplate5.getText().isBlank()) {
         shaderTemplate5.setText(check4(file.getName()) ? ShaderStylePreset.resolve() : file.getName().replace(".theme.json", ""));
      }

      shaderTemplate5.invoke5(shaderTemplate5.getText(), resolve19());
      long longValue2 = shaderTemplate5.getTimestamp2() > 0L ? shaderTemplate5.getTimestamp2() : jSONObject.optLong("updatedAt", file.lastModified());
      String text13 = jSONObject.optString("target", "preview");
      String text14 = jSONObject.optString("source", shaderTemplate5.getLocal().isBlank() ? "user" : shaderTemplate5.getLocal());
      String text15 = jSONObject.optString("compileStatus", "saved");
      return new SavedShaderPreset(
         file.getName(),
         shaderTemplate5.getText(),
         text13,
         text12,
         shaderTemplate5.getText2(),
         shaderTemplate5.getText3(),
         shaderTemplate5.getCustom(),
         text14,
         text15,
         shaderTemplate5.getTimestamp(),
         longValue2,
         shaderTemplate5.isFlag()
      );
   }

   private boolean check2(File file, ShaderNodeRegistry shaderNodeRegistry4) {
      String text16 = file.getName();
      boolean flag2 = false;

      try {
         JSONObject jsonObject7 = new JSONObject(resolve18(file));
         SavedShaderPreset savedShaderPreset8 = this.resolve15(file, jsonObject7);
         if (!this.check3(savedShaderPreset8.getText4(), shaderNodeRegistry4)) {
            JSONObject jsonObject8 = jsonObject7.optJSONObject("graph");
            if (jsonObject8 != null) {
               ShaderNode shaderNode8 = WildThemeCodec.resolve4(jsonObject8, shaderNodeRegistry4);
               if (shaderNode8 != null) {
                  savedShaderPreset8.setText4(WildThemeCodec.resolve(shaderNode8));
               }
            }
         }

         if (!this.check3(savedShaderPreset8.getText4(), shaderNodeRegistry4)) {
            System.out.println("[FoundryStorage] keeping legacy file without loadable payload: " + text16);
            return false;
         } else {
            SavedShaderPreset savedShaderPreset9 = this.resolve16(savedShaderPreset8);
            String text17;
            if (savedShaderPreset9 != null) {
               text17 = savedShaderPreset9.getText();
            } else {
               text17 = this.resolve17();
               ShaderTemplate shaderTemplate6 = WildThemeCodec.resolve6(jsonObject7.optJSONObject("metadata"), jsonObject7);
               shaderTemplate6.invoke5(savedShaderPreset8.getText2(), resolve19());
               shaderTemplate6.setText(savedShaderPreset8.getText2());
               shaderTemplate6.setTimestamp(savedShaderPreset8.getTimestamp());
               shaderTemplate6.setTimestamp2(savedShaderPreset8.getTimestamp2());
               shaderTemplate6.setFlag(savedShaderPreset8.isFlag());
               SavedShaderPreset savedShaderPreset10 = new SavedShaderPreset(
                  text17,
                  savedShaderPreset8.getText2(),
                  savedShaderPreset8.getText3(),
                  savedShaderPreset8.getText4(),
                  savedShaderPreset8.getText5(),
                  savedShaderPreset8.getText6(),
                  savedShaderPreset8.getText7(),
                  savedShaderPreset8.getText8(),
                  savedShaderPreset8.getText9(),
                  savedShaderPreset8.getTimestamp(),
                  savedShaderPreset8.getTimestamp2(),
                  savedShaderPreset8.isFlag()
               );
               this.invoke5(savedShaderPreset10, shaderTemplate6);
               this.valuesByKey2.put(text17, savedShaderPreset10);
            }

            for (Entry entry3 : new ArrayList<>(this.valuesByKey.entrySet())) {
               if (text16.equals(entry3.getValue())) {
                  this.valuesByKey.put((ShaderSurface)entry3.getKey(), text17);
                  flag2 = true;
               }
            }

            if (flag2) {
               this.invoke4();
            }

            Files.deleteIfExists(file.toPath());
            return flag2;
         }
      } catch (Throwable exception6) {
         System.out.println("[FoundryStorage] legacy migration failed for " + text16 + ": " + exception6.getMessage());
         return flag2;
      }
   }

   private boolean check3(String string, ShaderNodeRegistry shaderNodeRegistry5) {
      if (string != null && !string.isBlank()) {
         try {
            return WildThemeCodec.resolve2(string, shaderNodeRegistry5) != null;
         } catch (Throwable exception7) {
            return false;
         }
      } else {
         return false;
      }
   }

   private SavedShaderPreset resolve16(SavedShaderPreset savedShaderPreset11) {
      for (SavedShaderPreset savedShaderPreset12 : this.valuesByKey2.values()) {
         if (savedShaderPreset12.getText2().equals(savedShaderPreset11.getText2())
            && savedShaderPreset12.getText3().equals(savedShaderPreset11.getText3())
            && savedShaderPreset12.getText4().equals(savedShaderPreset11.getText4())) {
            return savedShaderPreset12;
         }
      }

      return null;
   }

   private void invoke5(SavedShaderPreset savedShaderPreset13, ShaderTemplate shaderTemplate7) throws IOException {
      JSONObject jsonObject9 = new JSONObject();
      jsonObject9.put("version", 4);
      jsonObject9.put("target", savedShaderPreset13.getText3());
      jsonObject9.put("source", savedShaderPreset13.getText8());
      jsonObject9.put("compileStatus", savedShaderPreset13.getText9());
      jsonObject9.put("wildTheme", savedShaderPreset13.getText4());
      jsonObject9.put("metadata", WildThemeCodec.resolve5(shaderTemplate7));
      Files.write(new File(this.file, savedShaderPreset13.getText()).toPath(), jsonObject9.toString(2).getBytes(StandardCharsets.UTF_8));
   }

   private String resolve17() {
      String text18;
      do {
         text18 = UUID.randomUUID() + ".theme.json";
      } while (this.valuesByKey2.containsKey(text18) || new File(this.file, text18).exists());

      return text18;
   }

   private static boolean check4(String string) {
      if (string != null && string.endsWith(".theme.json")) {
         String text19 = string.substring(0, string.length() - ".theme.json".length());
         if (text19.length() != 36) {
            return false;
         } else {
            try {
               UUID.fromString(text19);
               return true;
            } catch (IllegalArgumentException illegalArgumentException) {
               return false;
            }
         }
      } else {
         return false;
      }
   }

   private static String resolve18(File file) throws IOException {
      return new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
   }

   public static String resolve19() {
      if (WildClient.text != null && !WildClient.text.isBlank()) {
         return WildClient.text.trim();
      } else {
         MinecraftClient client = MinecraftClient.getInstance();
         return client != null && client.getSession() != null && client.getSession().getUsername() != null && !client.getSession().getUsername().isBlank()
            ? client.getSession().getUsername().trim()
            : "Unknown";
      }
   }

   public synchronized int compute(Set<String> set) {
      if (set != null && !set.isEmpty()) {
         int intValue = 0;

         for (SavedShaderPreset savedShaderPreset14 : new ArrayList<>(this.valuesByKey2.values())) {
            if (set.contains(ShaderPresetRegistry.resolve21(savedShaderPreset14.getText2())) && this.check(savedShaderPreset14.getText())) {
               intValue++;
            }
         }

         return intValue;
      } else {
         return 0;
      }
   }

   private static String resolve20(String string) {
      String text20 = string == null ? "theme" : string.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]+", "_").replaceAll("^_+|_+$", "");
      return text20.isBlank() ? "theme" : text20;
   }
}
