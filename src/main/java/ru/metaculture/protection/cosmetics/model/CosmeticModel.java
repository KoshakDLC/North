package ru.metaculture.protection.cosmetics.model;

import com.google.gson.JsonObject;
import net.minecraft.util.Identifier;

public final class CosmeticModel {
   private final String name;
   private final int id;
   private final int category;
   private String rawModelJson;
   private Identifier textureId;
   private ModelPosition position = ModelPosition.HEAD;
   private float scale = 1.0F;
   private float x;
   private float y;
   private float z;
   private float yaw;
   private float pitch;
   private float roll;
   private JsonObject animationJson;

   public CosmeticModel(String name, int id, int category) {
      this.name = name;
      this.id = id;
      this.category = category;
   }

   public String getName() {
      return this.name;
   }

   public int getId() {
      return this.id;
   }

   public int getCategory() {
      return this.category;
   }

   public String getRawModelJson() {
      return this.rawModelJson;
   }

   public void setRawModelJson(String rawModelJson) {
      this.rawModelJson = rawModelJson;
   }

   public Identifier getTextureId() {
      return this.textureId;
   }

   public void setTextureId(Identifier textureId) {
      this.textureId = textureId;
   }

   public ModelPosition getPosition() {
      return this.position;
   }

   public void setPosition(ModelPosition position) {
      this.position = position;
   }

   public float getScale() {
      return this.scale;
   }

   public void setScale(float scale) {
      this.scale = scale;
   }

   public float getX() {
      return this.x;
   }

   public void setX(float x) {
      this.x = x;
   }

   public float getY() {
      return this.y;
   }

   public void setY(float y) {
      this.y = y;
   }

   public float getZ() {
      return this.z;
   }

   public void setZ(float z) {
      this.z = z;
   }

   public float getYaw() {
      return this.yaw;
   }

   public void setYaw(float yaw) {
      this.yaw = yaw;
   }

   public float getPitch() {
      return this.pitch;
   }

   public void setPitch(float pitch) {
      this.pitch = pitch;
   }

   public float getRoll() {
      return this.roll;
   }

   public void setRoll(float roll) {
      this.roll = roll;
   }

   public JsonObject getAnimationJson() {
      return this.animationJson;
   }

   public void setAnimationJson(JsonObject animationJson) {
      this.animationJson = animationJson;
   }
}
