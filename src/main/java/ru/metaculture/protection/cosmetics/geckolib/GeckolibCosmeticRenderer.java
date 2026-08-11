package ru.metaculture.protection.cosmetics.geckolib;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.opengl.GlStateManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import ru.metaculture.protection.cosmetics.geo.GeoBone;
import ru.metaculture.protection.cosmetics.geo.GeoCube;
import ru.metaculture.protection.cosmetics.geo.GeoModel;
import ru.metaculture.protection.cosmetics.geo.GeoQuad;
import ru.metaculture.protection.cosmetics.geo.GeoVertex;
import ru.metaculture.protection.cosmetics.model.CosmeticModel;

public final class GeckolibCosmeticRenderer {
   private static GeckolibCosmeticRenderer instance;
   private final Map<Integer, GeoModel> modelCache = new ConcurrentHashMap<>();
   private final Map<Integer, CosmeticAnimationData> animationCache = new ConcurrentHashMap<>();
   private final Set<Integer> noAnimationSet = ConcurrentHashMap.newKeySet();
   private final Map<Integer, Long> animationStartTime = new ConcurrentHashMap<>();
   private final Map<Integer, Map<String, float[]>> initialBoneTransforms = new ConcurrentHashMap<>();
   private final GeckolibModelParser modelParser = new GeckolibModelParser();

   public static GeckolibCosmeticRenderer getInstance() {
      if (instance == null) {
         instance = new GeckolibCosmeticRenderer();
      }

      return instance;
   }

   public void renderCosmetic(CosmeticModel cosmetic, MatrixStack matrices, VertexConsumerProvider buffers, int light) {
      if (cosmetic == null || cosmetic.getTextureId() == null) {
         return;
      }

      GeoModel model = this.getOrParseModel(cosmetic);
      if (model == null) {
         return;
      }

      CosmeticAnimationData animation = this.getOrParseAnimation(cosmetic);
      if (animation != null) {
         this.applyAnimations(model, animation, cosmetic.getId());
      }

      VertexConsumer consumer = buffers.getBuffer(RenderLayer.getEntityCutoutNoCull(cosmetic.getTextureId()));
      GlStateManager._disableCull();
      for (GeoBone bone : model.topLevelBones) {
         this.renderBone(bone, matrices, consumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
      }

      GlStateManager._enableCull();
   }

   private GeoModel getOrParseModel(CosmeticModel cosmetic) {
      GeoModel cached = this.modelCache.get(cosmetic.getId());
      if (cached != null) {
         return cached;
      }

      GeoModel parsed = this.modelParser.parseModel(cosmetic);
      if (parsed != null) {
         this.saveInitialBoneTransforms(cosmetic.getId(), parsed);
         this.modelCache.put(cosmetic.getId(), parsed);
      }

      return parsed;
   }

   private void saveInitialBoneTransforms(int id, GeoModel model) {
      Map<String, float[]> transforms = new HashMap<>();
      for (GeoBone bone : model.topLevelBones) {
         this.saveBonesRecursive(bone, transforms);
      }

      this.initialBoneTransforms.put(id, transforms);
   }

   private void saveBonesRecursive(GeoBone bone, Map<String, float[]> transforms) {
      transforms.put(
         bone.name,
         new float[]{
            bone.getRotationX(),
            bone.getRotationY(),
            bone.getRotationZ(),
            bone.getPositionX(),
            bone.getPositionY(),
            bone.getPositionZ(),
            bone.getScaleX(),
            bone.getScaleY(),
            bone.getScaleZ()
         }
      );
      for (GeoBone child : bone.childBones) {
         this.saveBonesRecursive(child, transforms);
      }
   }

   private void renderBone(GeoBone bone, MatrixStack matrices, VertexConsumer consumer, int light, int overlay, float r, float g, float b, float a) {
      if (bone.isHidden) {
         return;
      }

      matrices.push();
      GeckoRenderHelper.translate(bone, matrices);
      GeckoRenderHelper.moveToPivot(bone, matrices);
      GeckoRenderHelper.rotate(bone, matrices);
      GeckoRenderHelper.scale(bone, matrices);
      GeckoRenderHelper.moveBackFromPivot(bone, matrices);
      for (GeoCube cube : bone.childCubes) {
         this.renderCube(cube, matrices, consumer, light, overlay, r, g, b, a);
      }

      for (GeoBone child : bone.childBones) {
         this.renderBone(child, matrices, consumer, light, overlay, r, g, b, a);
      }

      matrices.pop();
   }

   private void renderCube(GeoCube cube, MatrixStack matrices, VertexConsumer consumer, int light, int overlay, float r, float g, float b, float a) {
      matrices.push();
      GeckoRenderHelper.moveToPivot(cube, matrices);
      GeckoRenderHelper.rotate(cube, matrices);
      GeckoRenderHelper.moveBackFromPivot(cube, matrices);
      Matrix4f position = matrices.peek().getPositionMatrix();
      Matrix3f normal = matrices.peek().getNormalMatrix();
      for (GeoQuad quad : cube.quads) {
         if (quad == null) {
            continue;
         }

         Vector3f transformed = new Vector3f(quad.normal.getX(), quad.normal.getY(), quad.normal.getZ());
         normal.transform(transformed);
         float nx = transformed.x();
         float ny = transformed.y();
         float nz = transformed.z();
         if ((cube.size.getY() == 0.0F || cube.size.getZ() == 0.0F) && nx < 0.0F) {
            nx = -nx;
         }

         if ((cube.size.getX() == 0.0F || cube.size.getZ() == 0.0F) && ny < 0.0F) {
            ny = -ny;
         }

         if ((cube.size.getX() == 0.0F || cube.size.getY() == 0.0F) && nz < 0.0F) {
            nz = -nz;
         }

         for (GeoVertex vertex : quad.vertices) {
            consumer.vertex(position, vertex.position.getX(), vertex.position.getY(), vertex.position.getZ())
               .color(r, g, b, a)
               .texture(vertex.textureU, vertex.textureV)
               .overlay(overlay)
               .light(light)
               .normal(nx, ny, nz);
         }
      }

      matrices.pop();
   }

   private CosmeticAnimationData getOrParseAnimation(CosmeticModel cosmetic) {
      int id = cosmetic.getId();
      if (this.animationCache.containsKey(id)) {
         return this.animationCache.get(id);
      }

      if (this.noAnimationSet.contains(id)) {
         return null;
      }

      JsonObject animation = cosmetic.getAnimationJson();
      if (animation == null) {
         this.noAnimationSet.add(id);
         return null;
      }

      try {
         CosmeticAnimationData parsed = this.parseAnimationData(animation);
         if (parsed == null) {
            this.noAnimationSet.add(id);
            return null;
         }

         this.animationCache.put(id, parsed);
         return parsed;
      } catch (Exception exception) {
         this.noAnimationSet.add(id);
         return null;
      }
   }

   private CosmeticAnimationData parseAnimationData(JsonObject root) {
      if (!root.has("animations")) {
         return null;
      }

      JsonObject animations = root.getAsJsonObject("animations");
      if (animations.entrySet().isEmpty()) {
         return null;
      }

      Entry<String, JsonElement> first = animations.entrySet().iterator().next();
      JsonObject animation = first.getValue().getAsJsonObject();
      CosmeticAnimationData data = new CosmeticAnimationData();
      data.loop = animation.has("loop") && animation.get("loop").getAsBoolean();
      data.length = animation.has("animation_length") ? animation.get("animation_length").getAsFloat() : 1.0F;
      if (animation.has("bones")) {
         for (Entry<String, JsonElement> boneEntry : animation.getAsJsonObject("bones").entrySet()) {
            JsonObject bone = boneEntry.getValue().getAsJsonObject();
            BoneAnimationData boneData = new BoneAnimationData();
            if (bone.has("rotation")) {
               boneData.rotationKeyframes = this.parseKeyframes(bone.get("rotation"));
            }

            if (bone.has("position")) {
               boneData.positionKeyframes = this.parseKeyframes(bone.get("position"));
            }

            if (bone.has("scale")) {
               boneData.scaleKeyframes = this.parseKeyframes(bone.get("scale"));
            }

            data.boneAnimations.put(boneEntry.getKey(), boneData);
         }
      }

      return data;
   }

   private Map<Float, float[]> parseKeyframes(JsonElement element) {
      Map<Float, float[]> frames = new HashMap<>();
      if (!element.isJsonObject()) {
         return frames;
      }

      for (Entry<String, JsonElement> entry : element.getAsJsonObject().entrySet()) {
         try {
            float time = Float.parseFloat(entry.getKey());
            JsonElement value = entry.getValue();
            float[] vector = new float[3];
            if (value.isJsonObject() && value.getAsJsonObject().has("vector")) {
               JsonArray array = value.getAsJsonObject().getAsJsonArray("vector");
               vector[0] = array.get(0).getAsFloat();
               vector[1] = array.get(1).getAsFloat();
               vector[2] = array.get(2).getAsFloat();
            } else if (value.isJsonArray()) {
               JsonArray array = value.getAsJsonArray();
               vector[0] = array.get(0).getAsFloat();
               vector[1] = array.get(1).getAsFloat();
               vector[2] = array.get(2).getAsFloat();
            }

            frames.put(time, vector);
         } catch (NumberFormatException ignored) {
         }
      }

      return frames;
   }

   private void applyAnimations(GeoModel model, CosmeticAnimationData animation, int id) {
      Map<String, float[]> initials = this.initialBoneTransforms.get(id);
      if (initials == null) {
         return;
      }

      long started = this.animationStartTime.computeIfAbsent(id, ignored -> System.currentTimeMillis());
      float elapsed = (System.currentTimeMillis() - started) / 1000.0F;
      float time = animation.loop && animation.length > 0.0F ? elapsed % animation.length : Math.min(elapsed, animation.length);
      for (GeoBone bone : model.topLevelBones) {
         this.resetBoneRecursive(bone, initials);
      }

      for (Entry<String, BoneAnimationData> entry : animation.boneAnimations.entrySet()) {
         GeoBone bone = this.findBone(model, entry.getKey());
         if (bone == null) {
            continue;
         }

         float[] base = initials.getOrDefault(entry.getKey(), new float[]{0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F});
         BoneAnimationData data = entry.getValue();
         if (!data.rotationKeyframes.isEmpty()) {
            float[] rotation = this.interpolateKeyframes(data.rotationKeyframes, time);
            bone.setRotationX(base[0] + (float)Math.toRadians(-rotation[0]));
            bone.setRotationY(base[1] + (float)Math.toRadians(-rotation[1]));
            bone.setRotationZ(base[2] + (float)Math.toRadians(rotation[2]));
         }

         if (!data.positionKeyframes.isEmpty()) {
            float[] position = this.interpolateKeyframes(data.positionKeyframes, time);
            bone.setPositionX(base[3] + position[0]);
            bone.setPositionY(base[4] + position[1]);
            bone.setPositionZ(base[5] + position[2]);
         }

         if (!data.scaleKeyframes.isEmpty()) {
            float[] scale = this.interpolateKeyframes(data.scaleKeyframes, time);
            bone.setScaleX(base[6] * scale[0]);
            bone.setScaleY(base[7] * scale[1]);
            bone.setScaleZ(base[8] * scale[2]);
         }
      }
   }

   private void resetBoneRecursive(GeoBone bone, Map<String, float[]> initials) {
      float[] values = initials.get(bone.name);
      if (values != null) {
         bone.setRotationX(values[0]);
         bone.setRotationY(values[1]);
         bone.setRotationZ(values[2]);
         bone.setPositionX(values[3]);
         bone.setPositionY(values[4]);
         bone.setPositionZ(values[5]);
         bone.setScaleX(values[6]);
         bone.setScaleY(values[7]);
         bone.setScaleZ(values[8]);
      }

      for (GeoBone child : bone.childBones) {
         this.resetBoneRecursive(child, initials);
      }
   }

   private GeoBone findBone(GeoModel model, String name) {
      for (GeoBone bone : model.topLevelBones) {
         GeoBone found = this.findBoneRecursive(bone, name);
         if (found != null) {
            return found;
         }
      }

      return null;
   }

   private GeoBone findBoneRecursive(GeoBone bone, String name) {
      if (bone.name.equals(name)) {
         return bone;
      }

      for (GeoBone child : bone.childBones) {
         GeoBone found = this.findBoneRecursive(child, name);
         if (found != null) {
            return found;
         }
      }

      return null;
   }

   private float[] interpolateKeyframes(Map<Float, float[]> frames, float time) {
      if (frames.isEmpty()) {
         return new float[]{0.0F, 0.0F, 0.0F};
      }

      Float previousTime = null;
      Float nextTime = null;
      float[] previous = null;
      float[] next = null;
      for (Entry<Float, float[]> entry : frames.entrySet()) {
         float key = entry.getKey();
         if (key <= time && (previousTime == null || key > previousTime)) {
            previousTime = key;
            previous = entry.getValue();
         }

         if (key >= time && (nextTime == null || key < nextTime)) {
            nextTime = key;
            next = entry.getValue();
         }
      }

      if (previous == null) {
         return next == null ? new float[]{0.0F, 0.0F, 0.0F} : next;
      }

      if (next == null || previousTime.equals(nextTime)) {
         return previous;
      }

      float delta = (time - previousTime) / (nextTime - previousTime);
      return new float[]{
         previous[0] + delta * (next[0] - previous[0]),
         previous[1] + delta * (next[1] - previous[1]),
         previous[2] + delta * (next[2] - previous[2])
      };
   }

   private static final class BoneAnimationData {
      Map<Float, float[]> rotationKeyframes = new HashMap<>();
      Map<Float, float[]> positionKeyframes = new HashMap<>();
      Map<Float, float[]> scaleKeyframes = new HashMap<>();
   }

   private static final class CosmeticAnimationData {
      boolean loop;
      float length;
      final Map<String, BoneAnimationData> boneAnimations = new HashMap<>();
   }
}
