package ru.metaculture.protection;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class DirectAimStrategy implements MinecraftAccessor {
   private static long timestamp;

   public static void invoke(LivingEntity livingEntity, CameraRotationEvent cameraRotationEvent) {
      if (livingEntity != null && a_.player != null && a_.world != null) {
         Vec3d vec3d = livingEntity.getPos()
            .add(0.0, MathHelper.clamp(a_.player.getEyePos().y - livingEntity.getY(), 0.0, 1.0), 0.0)
            .subtract(a_.player.getEyePos())
            .normalize();
         float floatValue = (float)Math.toDegrees(Math.atan2(-vec3d.x, vec3d.z));
         float floatValue2 = a_.player.getYaw();
         float floatValue3 = MathHelper.wrapDegrees(floatValue - floatValue2);
         float floatValue4 = MathHelper.clamp(AttackAura.skorostLegit.getValue(), 0.02F, 0.4F);
         float floatValue5 = measure();
         float floatValue6 = 1.0F - (float)Math.pow(1.0F - floatValue4, floatValue5);
         float floatValue7 = floatValue2 + floatValue3 * floatValue6;
         a_.player.setYaw(floatValue7);
         a_.player.headYaw = floatValue7;
         cameraRotationEvent.setFloatValue(floatValue7);
      }
   }

   private static float measure() {
      long longValue = System.nanoTime();
      if (timestamp == 0L) {
         timestamp = longValue;
         return 1.0F;
      } else {
         float floatValue8 = (float)(longValue - timestamp) / 1.6666667E7F;
         timestamp = longValue;
         return MathHelper.clamp(floatValue8, 0.25F, 4.0F);
      }
   }
}
