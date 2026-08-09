package ru.metaculture.protection;

import lombok.Generated;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector2f;
import org.joml.Vector4f;

public final class RaycastUtils implements MinecraftAccessor {
   public static double measure(Entity entity) {
      return resolve2(entity).length();
   }

   public static Rotation resolve() {
      return new Rotation(a_.player.getYaw(), a_.player.getPitch());
   }

   public static boolean check(Entity entity, float f, boolean bl) {
      return measure(entity) < f;
   }

   public static Vec3d resolve2(Entity entity) {
      Vec3d vec3d3 = a_.player.getEyePos();
      return resolve4(vec3d3, entity).subtract(vec3d3);
   }

   public static Vec3d resolve3(Vec3d vec3d, Box box) {
      return new Vec3d(
         RenderMath.measure58(vec3d.x, box.minX, box.maxX),
         RenderMath.measure58(vec3d.y, box.minY, box.maxY),
         RenderMath.measure58(vec3d.z, box.minZ, box.maxZ)
      );
   }

   public static Vec3d resolve4(Vec3d vec3d, Entity entity) {
      return resolve3(vec3d, entity.getBoundingBox());
   }

   public static Vec3d resolve5(LivingEntity livingEntity) {
      double doubleValue = livingEntity.getWidth() / 2.0F;
      double doubleValue2 = MathHelper.clamp(livingEntity.getY() - 6.0, 0.0, livingEntity.getHeight());
      double doubleValue3 = MathHelper.clamp(a_.player.getX() - livingEntity.getX(), -doubleValue, doubleValue);
      double doubleValue4 = MathHelper.clamp(a_.player.getZ() - livingEntity.getZ(), -doubleValue, doubleValue);
      return new Vec3d(
         livingEntity.getX() - a_.player.getX() + doubleValue3, livingEntity.getY() - a_.player.getY() - 0.8F, livingEntity.getZ() - a_.player.getZ() + doubleValue4
      );
   }

   public static Vec3d resolve6(LivingEntity livingEntity) {
      double doubleValue5 = MathHelper.clamp(livingEntity.getY() - livingEntity.getY(), 0.0, livingEntity.getHeight());
      double doubleValue6 = MathHelper.clamp(a_.player.getX() - livingEntity.getX(), 0.0, 0.0);
      double doubleValue7 = MathHelper.clamp(a_.player.getZ() - livingEntity.getZ(), 0.0, 0.0);
      return new Vec3d(
         livingEntity.getX() - a_.player.getX() + doubleValue6, livingEntity.getY() - a_.player.getY() - 0.8F, livingEntity.getZ() - a_.player.getZ() + doubleValue7
      );
   }

   public static Vec3d resolve7(LivingEntity livingEntity) {
      double doubleValue8 = MathHelper.clamp(livingEntity.getEyeY() - livingEntity.getY(), 0.0, livingEntity.getHeight());
      double doubleValue9 = MathHelper.clamp(a_.player.getX() - livingEntity.getX(), 0.0, 0.0);
      double doubleValue10 = MathHelper.clamp(a_.player.getZ() - livingEntity.getZ(), 0.0, 0.0);
      return new Vec3d(
         livingEntity.getX() - a_.player.getX() + doubleValue9, livingEntity.getY() - a_.player.getEyeY() + doubleValue8, livingEntity.getZ() - a_.player.getZ() + doubleValue10
      );
   }

   public static Vec3d resolve8(LivingEntity livingEntity) {
      double doubleValue11 = livingEntity.getWidth() / 2.0F;
      double doubleValue12 = MathHelper.clamp(livingEntity.getEyeY() - livingEntity.getY(), 0.0, livingEntity.getHeight());
      double doubleValue13 = MathHelper.clamp(a_.player.getX() - livingEntity.getX(), -doubleValue11, doubleValue11);
      double doubleValue14 = MathHelper.clamp(a_.player.getZ() - livingEntity.getZ(), -doubleValue11, doubleValue11);
      return new Vec3d(
         livingEntity.getX() - a_.player.getX() + doubleValue13, livingEntity.getY() - a_.player.getEyeY() + doubleValue12, livingEntity.getZ() - a_.player.getZ() + doubleValue14
      );
   }

   public static double measure2(float f, float g, float h) {
      if (g < 0.0F) {
         f += 180.0F;
      }

      float floatValue = 1.0F;
      if (g < 0.0F) {
         floatValue = -0.5F;
      }

      if (g > 0.0F) {
         floatValue = 0.5F;
      }

      if (h > 0.0F) {
         f -= 90.0F * floatValue;
      }

      if (h < 0.0F) {
         f += 90.0F * floatValue;
      }

      return Math.toRadians(f);
   }

   public static Vec3d resolve9(Vec3d vec3d, Entity entity, float f) {
      if (entity == null) {
         return Vec3d.ZERO;
      } else {
         Box box2 = entity.getBoundingBox().expand(-f);
         Vec3d vec3d4 = box2.getCenter();
         Vec3d vec3d5 = null;
         double doubleValue15 = Double.MAX_VALUE;

         for (double doubleValue16 = 0.0; doubleValue16 <= (box2.maxX - box2.minX) / 2.0; doubleValue16 += 0.1) {
            for (double doubleValue17 = 0.0; doubleValue17 <= (box2.maxY - box2.minY) / 2.0; doubleValue17 += 0.1) {
               for (double doubleValue18 = 0.0; doubleValue18 <= (box2.maxZ - box2.minZ) / 2.0; doubleValue18 += 0.1) {
                  for (int intValue : new int[]{-1, 1}) {
                     for (int intValue2 : new int[]{-1, 1}) {
                        for (int intValue3 : new int[]{-1, 1}) {
                           double doubleValue19 = vec3d4.x + intValue * doubleValue16;
                           double doubleValue20 = vec3d4.y + intValue2 * doubleValue17;
                           double doubleValue21 = vec3d4.z + intValue3 * doubleValue18;
                           Vec3d vec3d6 = new Vec3d(doubleValue19, doubleValue20, doubleValue21);
                           Vector2f vector2f = resolve10(vec3d6);
                           if (EntityRaycastUtils.resolve2(6.0, vector2f.x, vector2f.y, a_.player, false) instanceof EntityHitResult entityHitResult
                              && entityHitResult.getEntity().equals(entity)) {
                              double doubleValue22 = vec3d.distanceTo(vec3d6);
                              if (doubleValue22 < doubleValue15) {
                                 doubleValue15 = doubleValue22;
                                 vec3d5 = vec3d6;
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         if (vec3d5 != null) {
            return vec3d5;
         } else {
            double doubleValue23 = RenderMath.measure58(vec3d.x, box2.minX, box2.maxX);
            double doubleValue24 = RenderMath.measure58(vec3d.y, box2.minY, box2.maxY);
            double doubleValue25 = RenderMath.measure58(vec3d.z, box2.minZ, box2.maxZ);
            return new Vec3d(doubleValue23, doubleValue24, doubleValue25);
         }
      }
   }

   public static Vector2f resolve10(Vec3d vec3d) {
      return resolve11(a_.player.getPos().add(0.0, a_.player.getEyeY(), 0.0), vec3d);
   }

   public static Vector2f resolve11(Vec3d vec3d, Vec3d vec3d2) {
      double doubleValue26 = 180.0 / Math.PI;
      Vec3d vec3d7 = vec3d2.subtract(vec3d);
      double doubleValue27 = Math.hypot(vec3d7.x, vec3d7.z);
      float floatValue2 = (float)(RenderMath.measure14(vec3d7.z, vec3d7.x) * (180.0 / Math.PI)) - 90.0F;
      float floatValue3 = (float)(-(RenderMath.measure14(vec3d7.y, doubleValue27) * (180.0 / Math.PI)));
      return new Vector2f(floatValue2, floatValue3);
   }

   public static Vec3d resolve12(Entity entity) {
      float floatValue4 = a_.getRenderTickCounter().getTickProgress(false);
      return resolve9(a_.player.getCameraPosVec(floatValue4), entity, Math.min(entity.getWidth(), entity.getHeight()) / 4.0F);
   }

   public static Vector4f resolve13(LivingEntity livingEntity) {
      float floatValue5 = a_.getRenderTickCounter().getTickProgress(false);
      Vec3d vec3d8 = a_.player.getCameraPosVec(floatValue5);
      Vec3d vec3d9 = resolve12((Entity)livingEntity).subtract(vec3d8);
      float floatValue6 = RenderMath.measure27((float)(Math.toDegrees(Math.atan2(vec3d9.z, vec3d9.x)) - 90.0));
      float floatValue7 = (float)(-Math.toDegrees(Math.atan2(vec3d9.y, Math.sqrt(vec3d9.x * vec3d9.x + vec3d9.z * vec3d9.z))));
      float floatValue8 = RenderMath.measure27(floatValue6 - a_.player.getYaw());
      float floatValue9 = floatValue7 - a_.player.getPitch();
      return new Vector4f(floatValue6, floatValue7, floatValue8, floatValue9);
   }

   public static double measure3(LivingEntity livingEntity) {
      Vector4f vector4f = resolve13(livingEntity);
      float floatValue10 = vector4f.z;
      float floatValue11 = vector4f.w;
      return Math.sqrt(floatValue10 * floatValue10 + floatValue11 * floatValue11);
   }

   @Generated
   private RaycastUtils() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }
}
