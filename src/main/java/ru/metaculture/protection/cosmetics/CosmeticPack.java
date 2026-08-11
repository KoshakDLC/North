package ru.metaculture.protection.cosmetics;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import ru.metaculture.protection.cosmetics.loader.CosmeticLoader;
import ru.metaculture.protection.cosmetics.model.CosmeticModel;

public final class CosmeticPack {
   private static final String INDEX = "assets/wild/cosmetics/index.json";
   private static final String NONE = "Нет";
   private static final List<CosmeticEntry> ENTRIES = new ArrayList<>();
   private static final Map<Integer, CosmeticModel> MODELS = new ConcurrentHashMap<>();
   private static boolean loaded;

   private CosmeticPack() {
   }

   public static synchronized void loadIndex() {
      if (loaded) {
         return;
      }

      loaded = true;
      String json = readResource(INDEX);
      if (json == null) {
         return;
      }

      JsonObject root = JsonParser.parseString(json).getAsJsonObject();
      JsonArray cosmetics = root.getAsJsonArray("cosmetics");
      if (cosmetics == null) {
         return;
      }

      for (JsonElement element : cosmetics) {
         JsonObject object = element.getAsJsonObject();
         ENTRIES.add(
            new CosmeticEntry(
               object.get("id").getAsInt(),
               object.get("type").getAsString(),
               object.get("name").getAsString(),
               object.get("path").getAsString()
            )
         );
      }

      ENTRIES.sort(Comparator.comparing(entry -> entry.name, String.CASE_INSENSITIVE_ORDER));
   }

   public static String[] options(String type) {
      loadIndex();
      List<String> names = new ArrayList<>();
      names.add(NONE);
      for (CosmeticEntry entry : ENTRIES) {
         if (type.equals(entry.type)) {
            names.add(entry.name);
         }
      }

      return names.toArray(String[]::new);
   }

   public static boolean isNone(String name) {
      return name == null || name.isEmpty() || NONE.equalsIgnoreCase(name);
   }

   public static CosmeticModel resolve(String type, String name) {
      if (isNone(name)) {
         return null;
      }

      loadIndex();
      for (CosmeticEntry entry : ENTRIES) {
         if (type.equals(entry.type) && entry.name.equalsIgnoreCase(name)) {
            return loadModel(entry);
         }
      }

      return null;
   }

   private static CosmeticModel loadModel(CosmeticEntry entry) {
      CosmeticModel cached = MODELS.get(entry.id);
      if (cached != null) {
         return cached;
      }

      String json = readResource("assets/wild/cosmetics/" + entry.path);
      if (json == null) {
         return null;
      }

      CosmeticModel model = CosmeticLoader.getInstance().loadFromJson(json, null, entry.id);
      if (model != null) {
         MODELS.put(entry.id, model);
      }

      return model;
   }

   private static String readResource(String path) {
      ClassLoader classLoader = CosmeticPack.class.getClassLoader();
      try (InputStream inputStream = classLoader.getResourceAsStream(path)) {
         if (inputStream == null) {
            return null;
         }

         return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
      } catch (Exception exception) {
         System.out.println("[Cosmetics] Failed to read " + path + ": " + exception.getMessage());
         return null;
      }
   }

   public record CosmeticEntry(int id, String type, String name, String path) {
      public String key() {
         return this.type.toLowerCase(Locale.ROOT) + "/" + this.id;
      }
   }
}
