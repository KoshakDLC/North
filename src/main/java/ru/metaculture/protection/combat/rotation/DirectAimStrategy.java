package ru.metaculture.protection;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class DirectAimStrategy implements MinecraftAccessor {
   private static long timestamp;

   public static void invoke(LivingEntity livingEntity, CameraRotationEvent cameraRotationEvent) {
      if (livingEntity != null && a_.player != null && a_.world != null) {
         Vec3d eye = a_.player.getEyePos();
         Vec3d aimPoint = AttackAura.lipnutKIgroku.isEnabled() ? resolveInside(livingEntity, eye) : resolveSurface(livingEntity, eye);
         Vec3d dir = aimPoint.subtract(eye);
         if (dir.lengthSquared() < 1.0E-6) {
            return;
         }

         dir = dir.normalize();
         float targetYaw = (float)Math.toDegrees(Math.atan2(-dir.x, dir.z));
         float targetPitch = (float)MathHelper.clamp(-Math.toDegrees(Math.atan2(dir.y, Math.hypot(dir.x, dir.z))), -90.0, 90.0);
         float currentYaw = a_.player.getYaw();
         float yawDelta = MathHelper.wrapDegrees(targetYaw - currentYaw);
         float speed = MathHelper.clamp(AttackAura.skorostLegit.getValue(), 0.02F, 0.4F);
         float dt = measure();
         float factor = 1.0F - (float)Math.pow(1.0F - speed, dt);

         if (AttackAura.lipnutKIgroku.isEnabled()) {
            float currentPitch = a_.player.getPitch();
            float pitchDelta = targetPitch - currentPitch;
            float newYaw = currentYaw + yawDelta * factor;
            float newPitch = MathHelper.clamp(currentPitch + pitchDelta * factor, -90.0F, 90.0F);
            a_.player.setYaw(newYaw);
            a_.player.setPitch(newPitch);
            a_.player.headYaw = newYaw;
            cameraRotationEvent.setFloatValue(newYaw);
            cameraRotationEvent.setFloatValue2(newPitch);
         } else {
            float newYaw = currentYaw + yawDelta * factor;
            a_.player.setYaw(newYaw);
            a_.player.headYaw = newYaw;
            cameraRotationEvent.setFloatValue(newYaw);
         }
      }
   }

   /** Aim at a point inside the hitbox so the crosshair sits within the body. */
   private static Vec3d resolveInside(LivingEntity livingEntity, Vec3d eye) {
      Box box = livingEntity.getBoundingBox();
      double insetX = Math.min(0.22, box.getLengthX() * 0.28);
      double insetY = Math.min(0.28, box.getLengthY() * 0.22);
      double insetZ = Math.min(0.22, box.getLengthZ() * 0.28);
      Box inner = new Box(
         box.minX + insetX,
         box.minY + insetY,
         box.minZ + insetZ,
         box.maxX - insetX,
         box.maxY - insetY,
         box.maxZ - insetZ
      );
      if (!(inner.minX < inner.maxX) || !(inner.minY < inner.maxY) || !(inner.minZ < inner.maxZ)) {
         return box.getCenter();
      }

      return new Vec3d(
         MathHelper.clamp(eye.x, inner.minX, inner.maxX),
         MathHelper.clamp(eye.y, inner.minY, inner.maxY),
         MathHelper.clamp(eye.z, inner.minZ, inner.maxZ)
      );
   }

   private static Vec3d resolveSurface(LivingEntity livingEntity, Vec3d eye) {
      return livingEntity.getPos().add(0.0, MathHelper.clamp(eye.y - livingEntity.getY(), 0.0, livingEntity.getHeight()), 0.0);
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
