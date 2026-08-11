package ru.metaculture.protection.cosmetics.loader;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.util.Identifier;
import ru.metaculture.protection.cosmetics.model.CosmeticModel;
import ru.metaculture.protection.cosmetics.model.ModelPosition;

public final class CosmeticLoader {
   private static CosmeticLoader instance;
   private final Map<Integer, CosmeticModel> loadedCosmetics = new ConcurrentHashMap<>();
   private final Map<String, Identifier> textureCache = new ConcurrentHashMap<>();

   public static CosmeticLoader getInstance() {
      if (instance == null) {
         instance = new CosmeticLoader();
      }

      return instance;
   }

   public CosmeticModel loadFromJson(String json, Identifier textureId, int idOverride) {
      try {
         return this.loadFromJson(JsonParser.parseString(json).getAsJsonObject(), textureId, idOverride);
      } catch (Exception exception) {
         System.out.println("[Cosmetics] Failed to parse cosmetic JSON: " + exception.getMessage());
         return null;
      }
   }

   public CosmeticModel loadFromJson(JsonObject object, Identifier textureId, int idOverride) {
      try {
         if (!object.has("name") || !object.has("model")) {
            return null;
         }

         String name = object.get("name").getAsString();
         int id = idOverride > 0 ? idOverride : (object.has("id") ? object.get("id").getAsInt() : name.hashCode());
         int category = object.has("category") ? object.get("category").getAsInt() : 1;
         CosmeticModel model = new CosmeticModel(name, id, category);
         model.setRawModelJson(object.getAsJsonObject("model").toString());
         if (textureId != null) {
            model.setTextureId(textureId);
         } else if (object.has("texture")) {
            model.setTextureId(this.loadTextureFromBase64(name, id, object.get("texture").getAsString()));
         }

         this.parseModelPosition(object, model);
         if (object.has("animation") && object.get("animation").isJsonObject()) {
            model.setAnimationJson(object.getAsJsonObject("animation"));
         }

         this.loadedCosmetics.put(id, model);
         return model;
      } catch (Exception exception) {
         System.out.println("[Cosmetics] Failed to load cosmetic: " + exception.getMessage());
         return null;
      }
   }

   private void parseModelPosition(JsonObject object, CosmeticModel model) {
      if (object.has("pos")) {
         model.setPosition(ModelPosition.getById(object.get("pos").getAsInt()));
      }

      if (object.has("scale")) {
         model.setScale(object.get("scale").getAsFloat());
      }

      if (object.has("x")) {
         model.setX(object.get("x").getAsFloat());
      }

      if (object.has("y")) {
         model.setY(object.get("y").getAsFloat());
      }

      if (object.has("z")) {
         model.setZ(object.get("z").getAsFloat());
      }

      if (object.has("yaw")) {
         model.setYaw(object.get("yaw").getAsFloat());
      }

      if (object.has("pitch")) {
         model.setPitch(object.get("pitch").getAsFloat());
      }

      if (object.has("roll")) {
         model.setRoll(object.get("roll").getAsFloat());
      }
   }

   private Identifier loadTextureFromBase64(String name, int id, String texture) {
      try {
         String key = "cosmetic_" + name.replace(" ", "").toLowerCase() + "_" + id;
         Identifier cached = this.textureCache.get(key);
         if (cached != null) {
            return cached;
         }

         byte[] bytes = Base64.getDecoder().decode(texture);
         NativeImage image = NativeImage.read(new ByteArrayInputStream(bytes));
         Identifier identifier = Identifier.of("wild", "cosmetics/" + id);
         Runnable register = () -> {
            NativeImageBackedTexture nativeTexture = new NativeImageBackedTexture(() -> "wild_cosmetic_" + id, image);
            MinecraftClient.getInstance().getTextureManager().registerTexture(identifier, nativeTexture);
         };
         if (MinecraftClient.getInstance().isOnThread()) {
            register.run();
         } else {
            MinecraftClient.getInstance().execute(register);
         }

         this.textureCache.put(key, identifier);
         return identifier;
      } catch (Exception exception) {
         System.out.println("[Cosmetics] Failed to register texture for " + name + ": " + exception.getMessage());
         return null;
      }
   }

   public CosmeticModel getCosmetic(int id) {
      return this.loadedCosmetics.get(id);
   }
}
