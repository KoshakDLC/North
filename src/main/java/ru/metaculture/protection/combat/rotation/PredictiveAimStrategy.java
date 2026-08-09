package ru.metaculture.protection;

import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class PredictiveAimStrategy implements MinecraftAccessor {
   static float floatValue;
   static float floatValue2 = 0.0F;

   public static void invoke(LivingEntity livingEntity, boolean bl, float f, boolean bl2) {
      long longValue = System.currentTimeMillis();
      if (!AttackAura.flag9 && longValue - AttackAura.timestamp6 >= AttackAura.timestamp7) {
         AttackAura.flag9 = true;
         AttackAura.timestamp8 = longValue;
         AttackAura.intValue9 = ThreadLocalRandom.current().nextInt(270, 390);
         AttackAura.timestamp6 = longValue;
         AttackAura.timestamp7 = ThreadLocalRandom.current().nextLong(16500L, 23200L);
      }

      boolean flag = false;
      if (AttackAura.flag9 && longValue - AttackAura.timestamp8 >= AttackAura.intValue9) {
         AttackAura.flag9 = false;
      }

      if (longValue - AttackAura.timestamp8 >= AttackAura.intValue9 + 60L) {
         flag = true;
      }

      Vec3d vec3d = PlayerPoseUtils.resolve3(livingEntity.getBoundingBox()).subtract(a_.player.getEyePos());
      float floatValue = FreeLookController.floatValue;
      if (bl && RaycastUtils.measure((Entity)livingEntity) < f && !bl2) {
         floatValue = MathUtils.measure19(6.0F, 7.0F);
      }

      float floatValue2 = (float)RaycastUtils.measure3(livingEntity);
      float floatValue3 = 360.0F;
      float floatValue4 = MathUtils.measure19(22.0F, 29.0F);
      float floatValue5 = 0.0F;
      float floatValue6 = MathUtils.measure19(0.0F, 3.5F);
      float floatValue7 = (float)Math.cos(System.currentTimeMillis() / 30.0);
      float floatValue8 = (float)Math.sin(System.currentTimeMillis() / 50.0);
      if (floatValue > 0.0F && Math.abs(floatValue2) < floatValue3) {
         floatValue4 = MathUtils.measure19(90.0F, 120.0F);
         floatValue = (float)Math.toDegrees(Math.atan2(-vec3d.x, vec3d.z));
         floatValue5 = (floatValue7 + floatValue8) * RotationSmoothing.measure(1.0F, 6.0F);
         floatValue--;
      }

      float floatValue9 = (float)MathHelper.clamp(-Math.toDegrees(Math.atan2(vec3d.y, Math.hypot(vec3d.x, vec3d.z))), -90.0, 90.0);
      float floatValue10 = floatValue7 * RotationSmoothing.measure(11.0F, 24.0F) + floatValue5;
      float floatValue11 = floatValue8 * RotationSmoothing.measure(4.0F, 17.0F) + floatValue5;
      if (bl && RaycastUtils.measure((Entity)livingEntity) < f && !bl2) {
         floatValue2 = floatValue9;
      }

      float floatValue12 = AttackAura.flag9 ? -MathUtils.measure19(85.0F, 90.0F) : floatValue9;
      Rotation rotation = new Rotation(floatValue + floatValue10, floatValue12 + floatValue11);
      RotationController.invoke3(
         rotation,
         floatValue4,
         AttackAura.flag9
            ? RotationSmoothing.measure(120.0F, 170.0F)
            : (flag ? RotationSmoothing.measure(120.0F, 170.0F) : RotationSmoothing.measure(6.0F, 8.0F)),
         25.0F,
         25.0F,
         0,
         15,
         false
      );
   }
}
