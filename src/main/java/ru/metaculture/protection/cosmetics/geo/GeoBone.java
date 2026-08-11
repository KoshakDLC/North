package ru.metaculture.protection.cosmetics.geo;

import java.util.ArrayList;
import java.util.List;

public final class GeoBone {
   public GeoBone parent;
   public final List<GeoBone> childBones = new ArrayList<>();
   public final List<GeoCube> childCubes = new ArrayList<>();
   public final String name;
   public boolean isHidden;
   public float rotationPointX;
   public float rotationPointY;
   public float rotationPointZ;
   private float rotateX;
   private float rotateY;
   private float rotateZ;
   private float positionX;
   private float positionY;
   private float positionZ;
   private float scaleX = 1.0F;
   private float scaleY = 1.0F;
   private float scaleZ = 1.0F;

   public GeoBone(String name) {
      this.name = name;
   }

   public float getRotationX() {
      return this.rotateX;
   }

   public float getRotationY() {
      return this.rotateY;
   }

   public float getRotationZ() {
      return this.rotateZ;
   }

   public void setRotationX(float value) {
      this.rotateX = value;
   }

   public void setRotationY(float value) {
      this.rotateY = value;
   }

   public void setRotationZ(float value) {
      this.rotateZ = value;
   }

   public float getPositionX() {
      return this.positionX;
   }

   public float getPositionY() {
      return this.positionY;
   }

   public float getPositionZ() {
      return this.positionZ;
   }

   public void setPositionX(float value) {
      this.positionX = value;
   }

   public void setPositionY(float value) {
      this.positionY = value;
   }

   public void setPositionZ(float value) {
      this.positionZ = value;
   }

   public float getScaleX() {
      return this.scaleX;
   }

   public float getScaleY() {
      return this.scaleY;
   }

   public float getScaleZ() {
      return this.scaleZ;
   }

   public void setScaleX(float value) {
      this.scaleX = value;
   }

   public void setScaleY(float value) {
      this.scaleY = value;
   }

   public void setScaleZ(float value) {
      this.scaleZ = value;
   }

   public float getPivotX() {
      return this.rotationPointX;
   }

   public float getPivotY() {
      return this.rotationPointY;
   }

   public float getPivotZ() {
      return this.rotationPointZ;
   }
}
