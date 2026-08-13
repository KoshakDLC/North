package ru.metaculture.protection;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public final class DeltaAimStrategy implements MinecraftAccessor {
   private static final float[] PITCH_HISTORY = new float[30];
   private static int b;
   private static float c2;
   private static float c3;
   private static float c5;
   private static float c8 = 2.0F;
   private static int targetId = Integer.MIN_VALUE;

   private DeltaAimStrategy() {
   }

   public static void invoke(LivingEntity livingEntity) {
      if (a_.player == null || a_.world == null || livingEntity == null) {
         return;
      }

      if (targetId != livingEntity.getId()) {
         targetId = livingEntity.getId();
         b = 0;
         c2 = 0.0F;
         c3 = 0.0F;
         c5 = 0.0F;
         c8 = 2.0F;
         java.util.Arrays.fill(PITCH_HISTORY, a_.player.getPitch());
      }

      float reach = AttackAura.measure(livingEntity);
      boolean funTime = AttackAura.deltaRezhim.getValue().contains("ФанТайм");
      boolean throughWalls = funTime || AttackAura.proverkiDoUdara.isEnabled("Бить через блоки");
      Vec3d targetPosition = DeltaAuraUtil.resolveAimPoint(a_.player.getEyePos(), livingEntity, reach, throughWalls);
      float yawToTarget = targetPosition == Vec3d.ZERO
         ? lookYaw()
         : (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(targetPosition.z, targetPosition.x)) - 90.0);
      float pitchToTarget = targetPosition == Vec3d.ZERO
         ? lookPitch()
         : (float)(-Math.toDegrees(Math.atan2(targetPosition.y, Math.hypot(targetPosition.x, targetPosition.z))));

      System.arraycopy(PITCH_HISTORY, 0, PITCH_HISTORY, 1, 29);
      PITCH_HISTORY[0] = pitchToTarget;

      boolean skip = isSkip();
      if (c3 <= 0.0F && q(livingEntity, reach) || DeltaAuraUtil.canHitSoon(b, livingEntity, skip)) {
         c3 = 1.0F;
      }

      if (!funTime && DeltaAuraUtil.isMaceHold()) {
         float t = a_.player.age + a_.getRenderTickCounter().getTickProgress(false);
         float smoothW = (float)((((Math.sin(t * 0.31F) * 0.5) + (Math.sin(t * 0.73F + 1.1F) * 0.3000000314327426) + (Math.sin(t * 1.7F + 2.6F) * 0.2000000098386085)) * 8.0) / 8.0F);
         float finalYaw = DeltaAuraUtil.lerpAngle(a_.player.getYaw(), yawToTarget, 0.8F);
         float finalPitch = DeltaAuraUtil.lerpAngle(a_.player.getPitch(), pitchToTarget, 0.8F);
         RotationController.invoke7(new Rotation(finalYaw + smoothW, finalPitch + smoothW), 180.0F, 180.0F, 1, 2);
      }

      if (AttackAura.deltaRezhim.is("Легит")) {
         invokeLegit(yawToTarget, pitchToTarget, reach, livingEntity);
      } else {
         invokeFunTime(yawToTarget, pitchToTarget, reach, livingEntity);
      }

      c3 -= 1.0F;
      c5 -= 1.0F;
      c8 -= 1.0F;
      b++;
   }

   public static void invoke2() {
      b = 0;
      c5 = (int)random(8.0F, 10.0F);
      c2 += 1.0F;
      c8 = 2.0F;
   }

   public static void invoke3() {
      b = 0;
      c2 = 0.0F;
      c3 = 0.0F;
      c5 = 0.0F;
      c8 = 2.0F;
      targetId = Integer.MIN_VALUE;
      java.util.Arrays.fill(PITCH_HISTORY, a_.player != null ? a_.player.getPitch() : 0.0F);
   }

   private static void invokeFunTime(float yawToTarget, float pitchToTarget, float reach, LivingEntity livingEntity) {
      float t = a_.player.age + a_.getRenderTickCounter().getTickProgress(false);
      float smoothW = (float)((Math.sin(t * 0.4000000008323731) * 3.0) + (Math.sin(t * 0.9500002390239708 + 1.4000004888461306) * 2.0));
      float smoothH = (float)((Math.cos(t * 0.5 + 0.7000001555309916) * 0.5) + (Math.cos(t * 0.7800000620494261 + 3.10000031689524) * 1.5));
      float finalPitch = DeltaAuraUtil.lerpAngle(
         a_.player.getPitch(), PITCH_HISTORY[MathHelper.clamp(10 - b, 0, 29)] + smoothH * 1.5F, random(0.1F, 0.5F)
      );
      float finalYaw = DeltaAuraUtil.lerpAngle(a_.player.getYaw(), yawToTarget + smoothW, random(0.1F, 0.4F));

      if (c3 >= 0.0F) {
         if (!DeltaAuraUtil.hits(a_.player.getYaw(), a_.player.getPitch(), reach, livingEntity, true) && c8 <= 0.0F) {
            finalYaw = yawToTarget;
         }

         if (!DeltaAuraUtil.hits(yawToTarget, finalPitch, reach, livingEntity, true) && c8 <= 0.0F) {
            finalPitch = pitchToTarget;
         }

         if (!DeltaAuraUtil.hits(a_.player.getYaw() + smoothW, a_.player.getYaw() + smoothH, reach, livingEntity, true)
            && DeltaAuraUtil.hits(a_.player.getYaw(), a_.player.getPitch(), reach, livingEntity, true)) {
            smoothW = MathHelper.clamp(smoothW, -0.05F, 0.05F);
            smoothH = MathHelper.clamp(smoothH, -0.05F, 0.05F);
         }
      }

      if (b <= 4 && c2 % 2.0F == 0.0F) {
         finalYaw = a_.player.getYaw();
      }

      float pitch = (AttackAura.deltaRezhim.is("ФанТайм") ? finalPitch : lookPitch()) + smoothH;
      RotationController.invoke7(new Rotation(finalYaw + smoothW, pitch), 220.0F, 220.0F, 1, 1);
   }

   private static void invokeLegit(float yawToTarget, float pitchToTarget, float reach, LivingEntity livingEntity) {
      float t = a_.player.age + a_.getRenderTickCounter().getTickProgress(false);
      float fSin = (float)(((Math.sin(t * 0.31F) * 0.5) + (Math.sin(t * 1.7F + 2.6F) * 0.2000000098386085)) * 8.0) / 4.0F;
      float smoothW = fSin;
      float smoothH = fSin;
      float finalYaw = DeltaAuraUtil.lerpAngle(a_.player.getYaw(), yawToTarget, random(0.2F, 0.35F));
      float finalPitch = DeltaAuraUtil.lerpAngle(a_.player.getPitch(), pitchToTarget, random(0.15F, 0.25F));

      if (c3 >= 0.0F) {
         finalPitch = DeltaAuraUtil.lerpAngle(a_.player.getPitch(), pitchToTarget, 0.35F);
         smoothH /= 3.0F;
         smoothW /= 3.0F;
         if (!DeltaAuraUtil.hits(a_.player.getYaw(), a_.player.getPitch(), reach, livingEntity, true)) {
            finalYaw = DeltaAuraUtil.lerpAngle(a_.player.getYaw(), yawToTarget, random(0.7F, 1.0F));
         }
      }

      if (!DeltaAuraUtil.hits(finalYaw + smoothW, finalPitch + smoothH, reach, livingEntity, true)
         && DeltaAuraUtil.hits(yawToTarget, pitchToTarget, reach, livingEntity, true)) {
         smoothW = MathHelper.clamp(smoothW, -0.15F, 0.15F);
         smoothH = MathHelper.clamp(smoothH, -0.15F, 0.15F);
      }

      if (c5 >= 0.0F) {
         smoothW *= 8.0F;
         if (b >= 1 && c2 % 5.0F == 0.0F) {
            finalPitch = DeltaAuraUtil.lerpAngle(a_.player.getPitch(), -pitchToTarget, 0.05F);
         }
      }

      RotationController.invoke7(new Rotation(finalYaw + smoothW, finalPitch + smoothH), 180.0F, 180.0F, 1, 1);
   }

   private static boolean q(LivingEntity livingEntity, float reach) {
      if (AttackAura.proverkiDoUdara.isEnabled("Не бить если кушаешь")
         && a_.player.isUsingItem()
         && a_.player.getItemUseTime() > 0
         && b >= 8) {
         b = 8;
         return false;
      }

      if (AttackAura.proverkiDoUdara.isEnabled("Не бить в контейнерах ") && a_.currentScreen != null || !DeltaAuraUtil.canAttack(livingEntity, reach)) {
         return false;
      }

      if (a_.player.fallDistance > 1.5F) {
         if (b <= 3) {
            return false;
         }
      } else if (DeltaAuraUtil.isMaceHold()) {
         if (a_.player.getAttackCooldownProgress(0.5F) < 0.9F) {
            return false;
         }
      } else if (a_.player.getAttackCooldownProgress(0.5F) < 0.9F || b < 10) {
         return false;
      }

      return DeltaAuraUtil.canCrit() || a_.player.isOnGround() && !isJumping() || !DeltaAuraUtil.needsCrit();
   }

   private static boolean isSkip() {
      return AttackAura.proverkiDoUdara.isEnabled("Не бить если кушаешь")
            && a_.player.isUsingItem()
            && a_.player.getItemUseTime() > 0
            && b >= 8
         || AttackAura.proverkiDoUdara.isEnabled("Не бить в контейнерах ") && a_.currentScreen != null;
   }

   private static boolean isJumping() {
      return a_.player != null && a_.player.input != null && a_.player.input.playerInput.jump();
   }

   private static float random(float min, float max) {
      return (float)(Math.random() * (max - min) + min);
   }

   private static float lookYaw() {
      return FreeLookController.active ? FreeLookController.floatValue : a_.player.getYaw();
   }

   private static float lookPitch() {
      return FreeLookController.active ? FreeLookController.floatValue2 : a_.player.getPitch();
   }
}
