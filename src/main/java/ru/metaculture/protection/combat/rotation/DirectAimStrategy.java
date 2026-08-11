package ru.metaculture.protection;

import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class DirectAimStrategy implements MinecraftAccessor {
   private static int targetId = -1;
   private static float aimFracX = 0.5F;
   private static float aimFracY = 0.58F;
   private static float aimFracZ = 0.5F;
   private static int retargetTicks;
   private static int pauseTicks;
   private static float yawVel;
   private static float pitchVel;

   public static void invoke(LivingEntity livingEntity) {
      if (livingEntity == null || a_.player == null || a_.world == null) {
         invoke2();
         return;
      }

      if (livingEntity.getId() != targetId) {
         invoke2();
         targetId = livingEntity.getId();
         randomizeAimPoint(true);
      }

      if (--retargetTicks <= 0) {
         randomizeAimPoint(false);
      }

      if (pauseTicks > 0) {
         pauseTicks--;
         yawVel *= 0.35F;
         pitchVel *= 0.35F;
         return;
      }

      Vec3d eye = a_.player.getEyePos();
      Box box = livingEntity.getBoundingBox();
      Vec3d aimPoint = resolveAimPoint(livingEntity, box, eye);
      Vec3d dir = aimPoint.subtract(eye);
      if (dir.lengthSquared() < 1.0E-6) {
         return;
      }

      dir = dir.normalize();
      float targetYaw = (float)Math.toDegrees(Math.atan2(-dir.x, dir.z));
      float targetPitch = (float)MathHelper.clamp(-Math.toDegrees(Math.atan2(dir.y, Math.hypot(dir.x, dir.z))), -90.0, 90.0);
      float currentYaw = a_.player.getYaw();
      float currentPitch = a_.player.getPitch();
      float yawDelta = MathHelper.wrapDegrees(targetYaw - currentYaw);
      float pitchDelta = targetPitch - currentPitch;
      float reach = AttackAura.measure(livingEntity) + AttackAura.radiusObnaruzheniya.getValue();
      boolean onTarget = EntityRaycastUtils.check(MathHelper.wrapDegrees(currentYaw), currentPitch, reach, livingEntity);
      boolean stick = AttackAura.lipnutKIgroku.isEnabled();
      float speed = MathHelper.clamp(AttackAura.skorostLegit.getValue(), 0.02F, 0.4F);

      if (onTarget && !stick) {
         yawVel *= 0.42F;
         pitchVel *= 0.42F;
         if (ThreadLocalRandom.current().nextFloat() < 0.22F) {
            pauseTicks = ThreadLocalRandom.current().nextInt(1, 4);
         }

         if (Math.abs(yawVel) < 0.08F && Math.abs(pitchVel) < 0.08F) {
            return;
         }
      } else {
         float accel = speed * (onTarget ? 0.28F : 0.72F);
         float maxYaw = MathHelper.lerp(speed, 3.2F, 22.0F);
         float maxPitch = MathHelper.lerp(speed, 2.0F, 14.0F);
         if (Math.abs(yawDelta) > 45.0F) {
            maxYaw += MathHelper.lerp(speed, 4.0F, 10.0F);
         }

         yawVel = yawVel * 0.58F + MathHelper.clamp(yawDelta, -maxYaw, maxYaw) * accel;
         pitchVel = pitchVel * 0.58F + MathHelper.clamp(pitchDelta, -maxPitch, maxPitch) * accel;
         if (Math.abs(yawDelta) < 8.0F && ThreadLocalRandom.current().nextFloat() < 0.12F) {
            yawVel += ThreadLocalRandom.current().nextFloat(-0.35F, 0.35F);
         }

         if (!onTarget && Math.abs(yawDelta) > 2.5F && ThreadLocalRandom.current().nextFloat() < 0.08F) {
            yawVel += Math.signum(yawDelta) * ThreadLocalRandom.current().nextFloat(0.4F, 1.1F);
         }
      }

      float maxStepYaw = MathHelper.lerp(speed, 4.5F, 24.0F);
      float maxStepPitch = MathHelper.lerp(speed, 2.8F, 16.0F);
      if (!stick && !onTarget) {
         maxStepPitch *= 0.72F;
      }

      float stepYaw = MouseSensitivityUtils.measure(MathHelper.clamp(yawVel, -maxStepYaw, maxStepYaw));
      float stepPitch = MouseSensitivityUtils.measure(MathHelper.clamp(pitchVel, -maxStepPitch, maxStepPitch));
      if (stepYaw == 0.0F && stepPitch == 0.0F) {
         return;
      }

      if (!stick && onTarget && Math.abs(stepYaw) + Math.abs(stepPitch) < MouseSensitivityUtils.measure2() * 1.5F) {
         return;
      }

      a_.player.setYaw(currentYaw + stepYaw);
      a_.player.setPitch(MathHelper.clamp(currentPitch + stepPitch, -90.0F, 90.0F));
      a_.player.headYaw = a_.player.getYaw();
   }

   public static void invoke2() {
      targetId = -1;
      retargetTicks = 0;
      pauseTicks = 0;
      yawVel = 0.0F;
      pitchVel = 0.0F;
      aimFracX = 0.5F;
      aimFracY = 0.58F;
      aimFracZ = 0.5F;
   }

   private static Vec3d resolveAimPoint(LivingEntity livingEntity, Box box, Vec3d eye) {
      if (AttackAura.lipnutKIgroku.isEnabled()) {
         return resolveInside(livingEntity, eye);
      }

      return new Vec3d(
         MathHelper.lerp(aimFracX, box.minX, box.maxX),
         MathHelper.lerp(aimFracY, box.minY, box.maxY),
         MathHelper.lerp(aimFracZ, box.minZ, box.maxZ)
      );
   }

   private static void randomizeAimPoint(boolean fresh) {
      ThreadLocalRandom random = ThreadLocalRandom.current();
      if (fresh) {
         aimFracX = random.nextFloat(0.32F, 0.68F);
         aimFracY = random.nextFloat(0.42F, 0.78F);
         aimFracZ = random.nextFloat(0.32F, 0.68F);
      } else {
         aimFracX = MathHelper.clamp(aimFracX + random.nextFloat(-0.08F, 0.08F), 0.28F, 0.72F);
         aimFracY = MathHelper.clamp(aimFracY + random.nextFloat(-0.06F, 0.06F), 0.38F, 0.82F);
         aimFracZ = MathHelper.clamp(aimFracZ + random.nextFloat(-0.08F, 0.08F), 0.28F, 0.72F);
      }

      retargetTicks = random.nextInt(5, 13);
   }

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
}
