package ru.metaculture.protection;

import java.security.SecureRandom;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public final class AttackRotationController implements MinecraftAccessor {
   private static final SecureRandom SECURE_RANDOM = new SecureRandom();
   private static final long TIMESTAMP = 3500L;
   private static final int INT_VALUE = 31;
   private static final long TIMESTAMP_2 = 250L;
   private static final long TIMESTAMP_3 = 238L;
   private static int intValue;
   private static int intValue2 = -1;
   private static boolean flag;

   private AttackRotationController() {
   }

   public static void invoke(LivingEntity livingEntity) {
      if (a_.player != null && livingEntity != null) {
         flag = true;
         Rotation rotation = new Rotation(a_.player);
         Rotation rotation2 = resolve4(livingEntity);
         float[] floatValues = AttackAura.resolve2(livingEntity);
         boolean flag = AttackUtils.check15(livingEntity, false, false, true, 0L, floatValues);
         boolean flag2 = AttackUtils.check15(livingEntity, false, false, true, -50L, floatValues);
         Rotation rotation3;
         if (a_.interactionManager == null) {
            rotation3 = NoiseAimStrategy.resolve(rotation, rotation2, livingEntity, true, flag2);
         } else {
            rotation3 = resolve(rotation, rotation2, livingEntity, flag, flag2);
         }

         invoke5(rotation3);
      }
   }

   public static void invoke2() {
      if (flag && a_.player != null) {
         Rotation rotation4 = new Rotation(a_.player);
         Rotation rotation5 = new Rotation(FreeLookController.floatValue, FreeLookController.floatValue2);
         if (rotation4.measure(rotation5) < 1.0F) {
            flag = false;
         } else {
            invoke5(resolve(rotation4, rotation5, null, false, false));
         }
      }
   }

   public static void invoke3() {
      intValue++;
   }

   public static void invoke4() {
      intValue = 0;
      intValue2 = -1;
      flag = false;
   }

   private static Rotation resolve(Rotation rotation6, Rotation rotation7, LivingEntity livingEntity, boolean bl, boolean bl2) {
      long longValue = (long)AttackUtils.measure5();
      Rotation rotation8 = resolve5(rotation6, rotation7);
      float floatValue = rotation8.floatValue;
      float floatValue2 = rotation8.floatValue2;
      float floatValue3 = (float)Math.hypot(Math.abs(floatValue), Math.abs(floatValue2));
      if (floatValue3 < 1.0E-4F) {
         floatValue3 = 1.0E-4F;
      }

      boolean flag3 = intValue > 0 && intValue % 31 == 0 && longValue < 250L;
      if (flag3) {
         if (longValue >= 238L && intValue2 != intValue) {
            a_.player.swingHand(Hand.MAIN_HAND);
            intValue2 = intValue;
         }

         float floatValue4 = rotation6.floatValue + MathHelper.clamp(floatValue, -22.0F, 22.0F);
         return new Rotation(floatValue4, -85.0F);
      } else {
         return livingEntity != null
            ? resolve2(rotation6, floatValue, floatValue2, floatValue3, livingEntity, bl, bl2, longValue)
            : resolve3(rotation6, floatValue, floatValue2, floatValue3, longValue);
      }
   }

   private static Rotation resolve2(Rotation rotation9, float f, float g, float h, LivingEntity livingEntity, boolean bl, boolean bl2, long l) {
      boolean flag4 = a_.player.distanceTo(livingEntity) <= AttackAura.measure(livingEntity);
      boolean flag5 = l < 180L;
      float floatValue5 = measure4(18.0F, 28.0F);
      float floatValue6 = measure4(2.8F, 6.2F);
      if (bl2) {
         floatValue5 = Math.max(floatValue5, measure4(34.0F, 52.0F));
         floatValue6 = Math.max(floatValue6, measure4(4.2F, 7.8F));
      }

      if (flag5) {
         floatValue5 = Math.max(floatValue5, measure4(44.0F, 72.0F));
         floatValue6 = Math.max(floatValue6, measure4(5.4F, 10.0F));
      }

      if (Math.abs(f) > 40.0F) {
         floatValue5 += measure4(10.0F, 18.0F);
      }

      if (Math.abs(f) > 75.0F) {
         floatValue5 += measure4(12.0F, 24.0F);
      }

      if (Math.abs(g) > 20.0F) {
         floatValue6 += measure4(1.4F, 3.2F);
      }

      if (Math.abs(g) > 35.0F) {
         floatValue6 += measure4(1.6F, 3.8F);
      }

      float floatValue7 = measure3(f, h, floatValue5);
      float floatValue8 = measure3(g, h, floatValue6);
      float floatValue9 = MathHelper.clamp(f, -floatValue7, floatValue7);
      float floatValue10 = MathHelper.clamp(g, -floatValue8, floatValue8);
      float floatValue11 = bl ? 1.0F : (bl2 ? measure4(0.88F, 0.97F) : (flag5 ? measure4(0.74F, 0.88F) : measure4(0.56F, 0.74F)));
      if (flag4 && !bl2 && !flag5) {
         floatValue11 = Math.max(floatValue11, measure4(0.68F, 0.82F));
      }

      float floatValue12 = flag4 ? 1.25F : 0.9F;
      if (bl2) {
         floatValue12 = Math.max(floatValue12, 1.4F);
      }

      if (flag5) {
         floatValue12 = Math.max(floatValue12, 1.55F);
      }

      float floatValue13 = measure(l, intValue, floatValue12, Math.abs(f));
      float floatValue14 = measure2(l, intValue, floatValue12, Math.abs(g));
      if (Math.abs(f) < 4.0F) {
         floatValue13 *= 0.35F;
      }

      if (Math.abs(g) < 2.5F) {
         floatValue14 *= 0.25F;
      }

      float floatValue15 = measure5(floatValue11, rotation9.floatValue, rotation9.floatValue + floatValue9) + floatValue13;
      float floatValue16 = measure5(floatValue11, rotation9.floatValue2, rotation9.floatValue2 + floatValue10) + floatValue14;
      return new Rotation(floatValue15, MathHelper.clamp(floatValue16, -90.0F, 90.0F));
   }

   private static Rotation resolve3(Rotation rotation10, float f, float g, float h, long l) {
      Rotation rotation11 = switch (intValue % 4) {
         case 0 -> new Rotation((float)Math.cos((float)l / 40.0F + intValue % 6), (float)Math.sin((float)l / 40.0F + intValue % 6));
         case 1 -> new Rotation((float)Math.sin((float)l / 40.0F + intValue % 6), (float)Math.cos((float)l / 40.0F + intValue % 6));
         case 2 -> new Rotation((float)Math.sin((float)l / 40.0F + intValue % 6), (float)(-Math.cos((float)l / 40.0F + intValue % 6)));
         default -> new Rotation((float)(-Math.cos((float)l / 40.0F + intValue % 6)), (float)Math.sin((float)l / 40.0F + intValue % 6));
      };
      float floatValue17 = MathHelper.clamp((float)l / 3500.0F, 0.0F, 1.0F);
      float floatValue18 = l >= 3500L ? 0.0F : 1.0F - floatValue17 * 0.55F;
      float floatValue19 = floatValue18 > 0.0F ? measure4(12.0F, 22.0F) * rotation11.floatValue * floatValue18 : 0.0F;
      float floatValue20 = measure4(0.35F, 1.35F) * (float)Math.cos(System.currentTimeMillis() / 420.0 + intValue);
      float floatValue21 = floatValue18 > 0.0F ? (measure4(2.2F, 5.8F) * rotation11.floatValue2 + floatValue20) * floatValue18 : 0.0F;
      float floatValue22 = l < 180L ? measure4(0.0F, 3.5F) : (l < 600L ? measure4(4.0F, 10.0F) : (l >= 3500L ? measure4(12.0F, 28.0F) : measure4(6.0F, 14.0F)));
      float floatValue23 = l < 180L ? measure4(0.0F, 1.0F) : (l < 600L ? measure4(1.2F, 3.0F) : (l >= 3500L ? measure4(3.0F, 6.8F) : measure4(1.5F, 4.2F)));
      float floatValue24 = measure3(f, h, floatValue22);
      float floatValue25 = measure3(g, h, floatValue23);
      float floatValue26 = MathHelper.clamp(f, -floatValue24, floatValue24);
      float floatValue27 = MathHelper.clamp(g, -floatValue25, floatValue25);
      float floatValue28 = l < 180L ? 0.0F : (l < 600L ? measure4(0.08F, 0.22F) : (l >= 3500L ? measure4(0.54F, 0.78F) : measure4(0.2F, 0.42F)));
      float floatValue29 = measure5(floatValue28, rotation10.floatValue, rotation10.floatValue + floatValue26) + floatValue19;
      float floatValue30 = measure5(floatValue28, rotation10.floatValue2, rotation10.floatValue2 + floatValue27) + floatValue21;
      return new Rotation(floatValue29, MathHelper.clamp(floatValue30, -90.0F, 90.0F));
   }

   private static Rotation resolve4(LivingEntity livingEntity) {
      Vec3d vec3d = PlayerPoseUtils.resolve3(livingEntity.getBoundingBox());
      Vec3d vec3d2 = vec3d.subtract(a_.player.getEyePos());
      return new Rotation(
         (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(vec3d2.z, vec3d2.x)) - 90.0),
         (float)MathHelper.wrapDegrees(Math.toDegrees(-Math.atan2(vec3d2.y, Math.hypot(vec3d2.x, vec3d2.z))))
      );
   }

   private static Rotation resolve5(Rotation rotation12, Rotation rotation13) {
      return new Rotation(
         MathHelper.wrapDegrees(rotation13.floatValue - rotation12.floatValue),
         MathHelper.clamp(MathHelper.wrapDegrees(rotation13.floatValue2 - rotation12.floatValue2), -90.0F, 90.0F)
      );
   }

   private static void invoke5(Rotation rotation14) {
      RotationController.invoke3(rotation14, 360.0F, 360.0F, 45.0F, 45.0F, 0, 15, false);
   }

   private static float measure(long l, int i, float f, float g) {
      float floatValue31 = (float)Math.sin((float)l / 38.0F + i * 0.37F) * measure4(0.45F, 1.25F)
         + (float)Math.cos((float)l / 71.0F + i * 0.18F) * measure4(0.18F, 0.55F);
      if (check(g > 24.0F ? 0.22F : 0.08F)) {
         floatValue31 += measure4(-1.55F, 1.55F);
      }

      return floatValue31 * f;
   }

   private static float measure2(long l, int i, float f, float g) {
      float floatValue32 = (float)Math.sin((float)l / 52.0F + i * 0.21F) * measure4(0.1F, 0.42F)
         + (float)Math.cos((float)l / 93.0F + i * 0.11F) * measure4(0.08F, 0.28F);
      if (check(g > 8.0F ? 0.18F : 0.06F)) {
         floatValue32 += measure4(-0.55F, 0.55F);
      }

      return floatValue32 * f;
   }

   private static float measure3(float f, float g, float h) {
      return Math.abs(f / g) * h;
   }

   private static boolean check(float f) {
      return SECURE_RANDOM.nextFloat() < f;
   }

   private static float measure4(float f, float g) {
      return measure5(SECURE_RANDOM.nextFloat(), f, g);
   }

   private static float measure5(float f, float g, float h) {
      return g + f * (h - g);
   }
}
