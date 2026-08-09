package ru.metaculture.protection;

import net.minecraft.client.render.Camera;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.wild.mixin.acceser.GameRendererAccessor;

public final class AimPointCalculator implements MinecraftAccessor {
   private AimPointCalculator() {
   }

   public static double measure(LivingEntity livingEntity) {
      if (a_.player != null && livingEntity != null) {
         Vec3d vec3d4 = a_.player.getEyePos();
         Vec3d vec3d5 = resolve();
         Box box2 = livingEntity.getBoundingBox();
         double doubleValue = box2.minX;
         double doubleValue2 = box2.minY;
         double doubleValue3 = box2.minZ;
         double doubleValue4 = box2.maxX;
         double doubleValue5 = box2.maxY;
         double doubleValue6 = box2.maxZ;
         double doubleValue7 = (doubleValue + doubleValue4) * 0.5;
         double doubleValue8 = (doubleValue3 + doubleValue6) * 0.5;
         double doubleValue9 = 180.0;

         for (int intValue = 0; intValue < 2; intValue++) {
            double doubleValue10 = intValue == 0 ? doubleValue : doubleValue4;

            for (int intValue2 = 0; intValue2 < 2; intValue2++) {
               double doubleValue11 = intValue2 == 0 ? doubleValue2 : doubleValue5;

               for (int intValue3 = 0; intValue3 < 2; intValue3++) {
                  double doubleValue12 = intValue3 == 0 ? doubleValue3 : doubleValue6;
                  doubleValue9 = Math.min(doubleValue9, measure4(vec3d4, vec3d5, new Vec3d(doubleValue10, doubleValue11, doubleValue12)));
               }
            }
         }

         doubleValue9 = Math.min(doubleValue9, measure4(vec3d4, vec3d5, box2.getCenter()));
         doubleValue9 = Math.min(doubleValue9, measure4(vec3d4, vec3d5, new Vec3d(doubleValue7, livingEntity.getEyeY(), doubleValue8)));
         doubleValue9 = Math.min(doubleValue9, measure4(vec3d4, vec3d5, new Vec3d(doubleValue7, doubleValue5, doubleValue8)));
         doubleValue9 = Math.min(doubleValue9, measure4(vec3d4, vec3d5, new Vec3d(doubleValue7, doubleValue2, doubleValue8)));
         doubleValue9 = Math.min(doubleValue9, measure4(vec3d4, vec3d5, new Vec3d(doubleValue, measure3(box2), doubleValue8)));
         doubleValue9 = Math.min(doubleValue9, measure4(vec3d4, vec3d5, new Vec3d(doubleValue4, measure3(box2), doubleValue8)));
         doubleValue9 = Math.min(doubleValue9, measure4(vec3d4, vec3d5, new Vec3d(doubleValue7, measure3(box2), doubleValue3)));
         return Math.min(doubleValue9, measure4(vec3d4, vec3d5, new Vec3d(doubleValue7, measure3(box2), doubleValue6)));
      } else {
         return 180.0;
      }
   }

   public static boolean check(LivingEntity livingEntity, float f) {
      return livingEntity != null && !(f <= 0.0F) ? measure(livingEntity) <= f * 0.5F : false;
   }

   public static float measure2(float f, int i) {
      if (a_ != null && a_.gameRenderer != null && i > 0 && !(f <= 0.0F)) {
         Camera camera = a_.gameRenderer.getCamera();
         float floatValue = ((GameRendererAccessor)a_.gameRenderer).invokeGetFov(camera, 1.0F, true);
         float floatValue2 = i * 0.5F;
         float floatValue3 = (float)Math.toRadians(floatValue * 0.5F);
         float floatValue4 = (float)Math.toRadians(f * 0.5F);
         return floatValue3 <= 1.0E-4F ? 0.0F : floatValue2 / (float)Math.tan(floatValue3) * (float)Math.tan(floatValue4);
      } else {
         return 0.0F;
      }
   }

   public static void invoke(RenderManager renderManager, float f, int i, int j) {
      if (renderManager != null && i > 0 && j > 0) {
         float floatValue5 = measure2(f, j);
         if (!(floatValue5 <= 1.0F)) {
            float floatValue6 = i * 0.5F;
            float floatValue7 = j * 0.5F;
            int intValue4 = compute(255, 255, 255, 210);
            renderManager.invoke28(floatValue6 - floatValue5, floatValue7 - floatValue5, floatValue5 * 2.0F, floatValue5 * 2.0F, floatValue5, intValue4, 1.2F);
         }
      }
   }

   private static Vec3d resolve() {
      if (a_.gameRenderer != null && a_.gameRenderer.getCamera() != null) {
         Camera camera2 = a_.gameRenderer.getCamera();
         Vector3f vector3f = new Vector3f(0.0F, 0.0F, -1.0F);
         new Quaternionf(camera2.getRotation()).transform(vector3f);
         return new Vec3d(vector3f.x, vector3f.y, vector3f.z).normalize();
      } else {
         return a_.player != null ? a_.player.getRotationVec(1.0F).normalize() : new Vec3d(0.0, 0.0, 1.0);
      }
   }

   private static double measure3(Box box) {
      return (box.minY + box.maxY) * 0.5;
   }

   private static double measure4(Vec3d vec3d, Vec3d vec3d2, Vec3d vec3d3) {
      Vec3d vec3d6 = vec3d3.subtract(vec3d);
      double doubleValue13 = vec3d6.length();
      if (doubleValue13 < 1.0E-6) {
         return 0.0;
      } else {
         vec3d6 = vec3d6.multiply(1.0 / doubleValue13);
         double doubleValue14 = MathHelper.clamp(vec3d2.dotProduct(vec3d6), -1.0, 1.0);
         return Math.toDegrees(Math.acos(doubleValue14));
      }
   }

   private static int compute(int i, int j, int k, int l) {
      return (l & 0xFF) << 24 | (i & 0xFF) << 16 | (j & 0xFF) << 8 | k & 0xFF;
   }
}
