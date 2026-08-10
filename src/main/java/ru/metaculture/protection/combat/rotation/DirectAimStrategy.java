package ru.metaculture.protection;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class DirectAimStrategy implements MinecraftAccessor {
   private static long timestamp;

   public static void invoke(LivingEntity livingEntity, CameraRotationEvent cameraRotationEvent) {
      if (livingEntity != null && a_.player != null && a_.world != null) {
         Vec3d vec3d = livingEntity.getPos()
            .add(0.0, MathHelper.clamp(a_.player.getEyePos().y - livingEntity.getY(), 0.0, livingEntity.getHeight()), 0.0)
            .subtract(a_.player.getEyePos())
            .normalize();
         float floatValue = (float)Math.toDegrees(Math.atan2(-vec3d.x, vec3d.z));
         float floatValue2 = (float)MathHelper.clamp(-Math.toDegrees(Math.atan2(vec3d.y, Math.hypot(vec3d.x, vec3d.z))), -90.0, 90.0);
         if (AttackAura.lipnutKIgroku.isEnabled()) {
            a_.player.setYaw(floatValue);
            a_.player.setPitch(floatValue2);
            a_.player.headYaw = floatValue;
            a_.player.bodyYaw = floatValue;
            cameraRotationEvent.setFloatValue(floatValue);
            cameraRotationEvent.setFloatValue2(floatValue2);
            return;
         }

         float floatValue3 = a_.player.getYaw();
         float floatValue4 = MathHelper.wrapDegrees(floatValue - floatValue3);
         float floatValue5 = MathHelper.clamp(AttackAura.skorostLegit.getValue(), 0.02F, 0.4F);
         float floatValue6 = measure();
         float floatValue7 = 1.0F - (float)Math.pow(1.0F - floatValue5, floatValue6);
         float floatValue8 = floatValue3 + floatValue4 * floatValue7;
         a_.player.setYaw(floatValue8);
         a_.player.headYaw = floatValue8;
         cameraRotationEvent.setFloatValue(floatValue8);
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
