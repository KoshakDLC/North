package ru.metaculture.protection.cosmetics.geckolib;

import net.minecraft.client.util.math.MatrixStack;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import ru.metaculture.protection.cosmetics.geo.GeoBone;
import ru.metaculture.protection.cosmetics.geo.GeoCube;

public final class GeckoRenderHelper {
   private static final Vector3f POSITIVE_X = new Vector3f(1.0F, 0.0F, 0.0F);
   private static final Vector3f POSITIVE_Y = new Vector3f(0.0F, 1.0F, 0.0F);
   private static final Vector3f POSITIVE_Z = new Vector3f(0.0F, 0.0F, 1.0F);

   private GeckoRenderHelper() {
   }

   public static void translate(GeoBone bone, MatrixStack matrices) {
      matrices.translate(-bone.getPositionX() / 16.0F, bone.getPositionY() / 16.0F, bone.getPositionZ() / 16.0F);
   }

   public static void moveToPivot(GeoBone bone, MatrixStack matrices) {
      matrices.translate(bone.getPivotX() / 16.0F, bone.getPivotY() / 16.0F, bone.getPivotZ() / 16.0F);
   }

   public static void moveBackFromPivot(GeoBone bone, MatrixStack matrices) {
      matrices.translate(-bone.getPivotX() / 16.0F, -bone.getPivotY() / 16.0F, -bone.getPivotZ() / 16.0F);
   }

   public static void rotate(GeoBone bone, MatrixStack matrices) {
      if (bone.getRotationZ() != 0.0F) {
         matrices.multiply(radial(POSITIVE_Z, bone.getRotationZ()));
      }

      if (bone.getRotationY() != 0.0F) {
         matrices.multiply(radial(POSITIVE_Y, bone.getRotationY()));
      }

      if (bone.getRotationX() != 0.0F) {
         matrices.multiply(radial(POSITIVE_X, bone.getRotationX()));
      }
   }

   public static void scale(GeoBone bone, MatrixStack matrices) {
      matrices.scale(bone.getScaleX(), bone.getScaleY(), bone.getScaleZ());
   }

   public static void moveToPivot(GeoCube cube, MatrixStack matrices) {
      matrices.translate(cube.pivot.getX() / 16.0F, cube.pivot.getY() / 16.0F, cube.pivot.getZ() / 16.0F);
   }

   public static void moveBackFromPivot(GeoCube cube, MatrixStack matrices) {
      matrices.translate(-cube.pivot.getX() / 16.0F, -cube.pivot.getY() / 16.0F, -cube.pivot.getZ() / 16.0F);
   }

   public static void rotate(GeoCube cube, MatrixStack matrices) {
      if (cube.rotation.getZ() != 0.0F) {
         matrices.multiply(radial(POSITIVE_Z, cube.rotation.getZ()));
      }

      if (cube.rotation.getY() != 0.0F) {
         matrices.multiply(radial(POSITIVE_Y, cube.rotation.getY()));
      }

      if (cube.rotation.getX() != 0.0F) {
         matrices.multiply(radial(POSITIVE_X, cube.rotation.getX()));
      }
   }

   private static Quaternionf radial(Vector3f axis, float angle) {
      float sin = (float)Math.sin(angle / 2.0F);
      return new Quaternionf(axis.x() * sin, axis.y() * sin, axis.z() * sin, (float)Math.cos(angle / 2.0F));
   }
}
