package ru.metaculture.protection;

import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public final class NoiseAimStrategy implements MinecraftAccessor {
   private static final int INT_VALUE = 2;
   private static long timestamp = -1L;
   private static boolean flag;
   private static final RotationController.RotationControllerProvider ROTATION_CONTROLLER_PROVIDER = NoiseAimStrategy::resolve2;

   private NoiseAimStrategy() {
   }

   public static void invoke(LivingEntity livingEntity) {
      if (a_.player != null && livingEntity != null) {
         flag = true;
         Rotation rotation = new Rotation(a_.player);
         Rotation rotation2 = resolve3(livingEntity);
         boolean flag = CriticalHitController.check(livingEntity, 2);
         invoke4(resolve(rotation, rotation2, livingEntity, true, flag));
      }
   }

   public static void invoke2() {
      if (flag) {
         RotationController.invoke8(ROTATION_CONTROLLER_PROVIDER);
      }
   }

   public static Rotation resolve(Rotation rotation3, Rotation rotation4, LivingEntity livingEntity, boolean bl, boolean bl2) {
      if (a_.player == null) {
         return rotation3;
      } else if (bl && livingEntity != null && bl2) {
         timestamp = -1L;
         return resolve4(rotation3, rotation4, 130.0F, 130.0F, 0.85F, 0.0F, 0.0F);
      } else {
         Rotation rotation5 = new Rotation(FreeLookController.floatValue, FreeLookController.floatValue2);
         Rotation rotation6 = resolve5(rotation3, rotation5);
         float floatValue = Math.max((float)Math.hypot(Math.abs(rotation6.floatValue), Math.abs(rotation6.floatValue2)), 1.0E-4F);
         float floatValue2 = (float)(measure(4.0F, 15.0F) * Math.sin(System.currentTimeMillis() / 95.0));
         float floatValue3 = (float)(measure(6.0F, 7.0F) * Math.cos(System.currentTimeMillis() / 45.0));
         if (bl && livingEntity != null) {
            timestamp = -1L;
         } else {
            if (timestamp < 0L) {
               timestamp = System.currentTimeMillis();
            }

            float floatValue4 = 1.0F - MathHelper.clamp((float)(System.currentTimeMillis() - timestamp) / 1000.0F, 0.0F, 1.0F);
            floatValue2 *= floatValue4;
            floatValue3 *= floatValue4;
         }

         float floatValue5 = Math.abs(rotation6.floatValue / floatValue) * (!AttackUtils.check12(535L) ? 0.0F : 45.0F);
         float floatValue6 = Math.abs(rotation6.floatValue2 / floatValue) * (!AttackUtils.check12(535L) ? 0.0F : 45.0F);
         return new Rotation(
            measure2(0.85F, rotation3.floatValue, rotation3.floatValue + MathHelper.clamp(rotation6.floatValue, -floatValue5, floatValue5) + floatValue2),
            MathHelper.clamp(
               measure2(0.85F, rotation3.floatValue2, rotation3.floatValue2 + MathHelper.clamp(rotation6.floatValue2, -floatValue6, floatValue6) + floatValue3), -90.0F, 90.0F
            )
         );
      }
   }

   public static void invoke3() {
      timestamp = -1L;
      flag = false;
      RotationController.invoke9(ROTATION_CONTROLLER_PROVIDER);
   }

   private static RotationController.RotationControllerState resolve2() {
      if (flag && a_.player != null) {
         Rotation rotation7 = new Rotation(a_.player);
         Rotation rotation8 = new Rotation(FreeLookController.floatValue, FreeLookController.floatValue2);
         if (rotation7.measure(rotation8) < 1.0F) {
            invoke3();
            return RotationController.RotationControllerState.resolve();
         } else {
            Rotation rotation9 = resolve(rotation7, rotation8, null, false, false);
            return new RotationController.RotationControllerState(rotation9, 360.0F, 360.0F, false);
         }
      } else {
         invoke3();
         return RotationController.RotationControllerState.resolve();
      }
   }

   private static Rotation resolve3(LivingEntity livingEntity) {
      Vec3d vec3d = PlayerPoseUtils.resolve3(livingEntity.getBoundingBox());
      Vec3d vec3d2 = vec3d.subtract(a_.player.getEyePos());
      return new Rotation(
         (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(vec3d2.z, vec3d2.x)) - 90.0),
         (float)MathHelper.clamp(-Math.toDegrees(Math.atan2(vec3d2.y, Math.hypot(vec3d2.x, vec3d2.z))), -90.0, 90.0)
      );
   }

   private static void invoke4(Rotation rotation10) {
      RotationController.invoke4(rotation10, 360.0F, 360.0F, 45.0F, 45.0F, 0, 15, false, ROTATION_CONTROLLER_PROVIDER);
   }

   private static Rotation resolve4(Rotation rotation11, Rotation rotation12, float f, float g, float h, float i, float j) {
      Rotation rotation13 = resolve5(rotation11, rotation12);
      float floatValue7 = Math.max((float)Math.hypot(Math.abs(rotation13.floatValue), Math.abs(rotation13.floatValue2)), 1.0E-4F);
      float floatValue8 = Math.abs(rotation13.floatValue / floatValue7) * f;
      float floatValue9 = Math.abs(rotation13.floatValue2 / floatValue7) * g;
      return new Rotation(
         measure2(h, rotation11.floatValue, rotation11.floatValue + MathHelper.clamp(rotation13.floatValue, -floatValue8, floatValue8) + i),
         MathHelper.clamp(
            measure2(h, rotation11.floatValue2, rotation11.floatValue2 + MathHelper.clamp(rotation13.floatValue2, -floatValue9, floatValue9) + j), -90.0F, 90.0F
         )
      );
   }

   private static Rotation resolve5(Rotation rotation14, Rotation rotation15) {
      return new Rotation(
         MathHelper.wrapDegrees(rotation15.floatValue - rotation14.floatValue),
         MathHelper.clamp(MathHelper.wrapDegrees(rotation15.floatValue2 - rotation14.floatValue2), -90.0F, 90.0F)
      );
   }

   private static float measure(float f, float g) {
      return (float)ThreadLocalRandom.current().nextDouble(f, g);
   }

   private static float measure2(float f, float g, float h) {
      return g + f * (h - g);
   }
}
