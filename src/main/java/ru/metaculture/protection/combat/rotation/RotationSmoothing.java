package ru.metaculture.protection;

import java.security.SecureRandom;
import java.util.concurrent.ThreadLocalRandom;
import lombok.Generated;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Direction.Axis;

public final class RotationSmoothing implements MinecraftAccessor {
   static int intValue;
   static float floatValue;
   static DualTimer dualTimer = new DualTimer();
   static DualTimer dualTimer2 = new DualTimer();

   public static void invoke(LivingEntity livingEntity, boolean bl, float f, boolean bl2) {
      long longValue = System.currentTimeMillis();
      if (!AttackAura.flag9 && longValue - AttackAura.timestamp6 >= AttackAura.timestamp7) {
         AttackAura.flag9 = true;
         AttackAura.timestamp8 = longValue;
         AttackAura.intValue9 = ThreadLocalRandom.current().nextInt(270, 390);
         AttackAura.timestamp6 = longValue;
         AttackAura.timestamp7 = ThreadLocalRandom.current().nextLong(6500L, 7200L);
      }

      boolean flag = false;
      if (AttackAura.flag9 && longValue - AttackAura.timestamp8 >= AttackAura.intValue9) {
         AttackAura.flag9 = false;
      }

      if (longValue - AttackAura.timestamp8 >= AttackAura.intValue9 + 40L) {
         flag = true;
      }

      Vec3d vec3d = a_.player.getEyePos();
      float floatValue = (float)Math.cos(System.currentTimeMillis() / 450.0);
      float floatValue2 = 0.06F * floatValue;
      float floatValue3 = (float)Math.cos(System.currentTimeMillis() / 500.0);
      float floatValue4 = 0.06F * floatValue3;
      float floatValue5 = (float)Math.cos(System.currentTimeMillis() / 14000.0);
      float floatValue6 = (float)Math.cos(System.currentTimeMillis() / 2500L);
      float floatValue7 = 0.5F * floatValue5;
      Vec3d vec3d2 = RaycastUtils.resolve6(livingEntity);
      float floatValue8 = FreeLookController.floatValue;
      if (bl && RaycastUtils.measure((Entity)livingEntity) < f && !bl2) {
         floatValue = MathUtils.measure19(6.0F, 7.0F);
      }

      float floatValue9 = MathUtils.measure19(22.0F, 28.0F);
      float floatValue10 = 0.0F;
      float floatValue11 = MathUtils.measure19(0.0F, 3.5F);
      float floatValue12 = (float)Math.cos(System.currentTimeMillis() / 40.0);
      float floatValue13 = (float)Math.sin(System.currentTimeMillis() / 70.0);
      if (floatValue > 0.0F) {
         floatValue9 = MathUtils.measure19(70.0F, 120.0F);
         floatValue8 = (float)Math.toDegrees(Math.atan2(-vec3d2.x, vec3d2.z));
         floatValue10 = (floatValue12 + floatValue13) * measure(1.0F, 2.0F);
         floatValue--;
      }

      float floatValue14 = (float)MathHelper.clamp(-Math.toDegrees(Math.atan2(vec3d2.y, Math.hypot(vec3d2.x, vec3d2.z))), -90.0, 90.0);
      float floatValue15 = floatValue12 * measure(13.0F, 15.0F) + floatValue10;
      float floatValue16 = floatValue13 * measure(5.0F, 7.0F) + floatValue10;
      float floatValue17 = AttackAura.flag9 ? -MathUtils.measure19(80.0F, 90.0F) : floatValue14;
      Rotation rotation = new Rotation(floatValue8 + floatValue15, floatValue17 + floatValue16);
      RotationController.invoke3(
         rotation,
         floatValue9,
         AttackAura.flag9 ? measure(120.0F, 170.0F) : (flag ? measure(120.0F, 170.0F) : measure(6.0F, 8.0F)),
         25.0F,
         25.0F,
         0,
         15,
         false
      );
   }

   public static void invoke2(BlockPos blockPos, Direction direction) {
      Vec3d vec3d3 = a_.player.getEyePos();
      double doubleValue = blockPos.getX() + 0.5 + direction.getOffsetX() * 0.5;
      double doubleValue2 = blockPos.getY() + 0.5 + direction.getOffsetY() * 0.5;
      double doubleValue3 = blockPos.getZ() + 0.5 + direction.getOffsetZ() * 0.5;
      if (direction.getAxis() != Axis.X) {
         doubleValue = MathHelper.clamp(vec3d3.x, blockPos.getX() + 0.15, blockPos.getX() + 0.85);
      }

      if (direction.getAxis() != Axis.Y) {
         doubleValue2 = MathHelper.clamp(vec3d3.y - 1.2, blockPos.getY() + 0.15, blockPos.getY() + 0.85);
      }

      if (direction.getAxis() != Axis.Z) {
         doubleValue3 = MathHelper.clamp(vec3d3.z, blockPos.getZ() + 0.15, blockPos.getZ() + 0.85);
      }

      Vec3d vec3d4 = new Vec3d(doubleValue, doubleValue2, doubleValue3).subtract(vec3d3);
      float floatValue18 = (float)Math.toDegrees(Math.atan2(-vec3d4.x, vec3d4.z));
      float floatValue19 = (float)MathHelper.clamp(-Math.toDegrees(Math.atan2(vec3d4.y, Math.hypot(vec3d4.x, vec3d4.z))), -90.0, 90.0);
      RotationController.invoke3(
         new Rotation(floatValue18, floatValue19),
         MathUtils.measure19(250.0F, 360.0F),
         MathUtils.measure19(250.0F, 360.0F),
         180.0F,
         180.0F,
         0,
         5,
         false
      );
   }

   public static void invoke3(LivingEntity livingEntity) {
      Vec3d vec3d5 = a_.player.getEyePos();
      Vec3d vec3d6 = livingEntity.getPos().add(0.0, livingEntity.getHeight() * 0.8, 0.0);
      Vec3d vec3d7 = vec3d6.subtract(vec3d5).normalize();
      float floatValue20 = (float)Math.toDegrees(Math.atan2(-vec3d7.x, vec3d7.z));
      float floatValue21 = (float)MathHelper.clamp(-Math.toDegrees(Math.atan2(vec3d7.y, Math.hypot(vec3d7.x, vec3d7.z))), -90.0, 90.0);
      double doubleValue4 = Math.max(0.0, a_.player.getY() - livingEntity.getY());
      float floatValue22;
      float floatValue23;
      if (doubleValue4 > 2.5) {
         floatValue22 = 15.0F;
         floatValue23 = 10.0F;
      } else if (doubleValue4 > 1.0) {
         floatValue22 = 45.0F;
         floatValue23 = 35.0F;
      } else {
         floatValue22 = 90.0F;
         floatValue23 = 80.0F;
      }

      float floatValue24 = ThreadLocalRandom.current().nextFloat(-1.0F, 1.0F);
      RotationController.invoke3(new Rotation(floatValue20 + floatValue24, floatValue21 + floatValue24), floatValue22, floatValue23, 30.0F, 30.0F, 1, 15, false);
   }

   public static float measure(float f, float g) {
      return NumberUtils.resolve(g, f, new SecureRandom().nextFloat());
   }

   public static void invoke4(LivingEntity livingEntity, boolean bl) {
      Vec3d vec3d8 = PlayerPoseUtils.resolve4(livingEntity.getBoundingBox(), false);
      Vec3d vec3d9 = vec3d8.subtract(a_.player.getEyePos());
      float floatValue25 = (float)Math.toDegrees(Math.atan2(-vec3d9.x, vec3d9.z));
      float floatValue26 = (float)MathHelper.clamp(-Math.toDegrees(Math.atan2(vec3d9.y, Math.hypot(vec3d9.x, vec3d9.z))), -89.0, 89.0);
      Rotation rotation2 = new Rotation(floatValue25, floatValue26);
      RotationController.invoke3(rotation2, MathUtils.compute2(120, 180), MathUtils.measure19(30.0F, 63.0F), 30.0F, 30.0F, 1, 15, false);
   }

   public static void invoke5(LivingEntity livingEntity, boolean bl) {
      float floatValue27 = 0.3F * (float)Math.cos(System.currentTimeMillis() / 2200.0);
      float floatValue28 = 0.03F * (float)Math.sin(System.currentTimeMillis() / 900.0) + 0.06F * (float)Math.cos(System.currentTimeMillis() / 1200.0);
      float floatValue29 = 0.2F * (float)Math.cos(System.currentTimeMillis() / 700.0) + 0.04F * (float)Math.sin(System.currentTimeMillis() / 900.0);
      Vec3d vec3d10 = a_.player.getEyePos();
      Vec3d vec3d11 = livingEntity.getPos().add(floatValue29, livingEntity.getHeight() - 0.35F - floatValue27, floatValue28).subtract(vec3d10).normalize();
      boolean flag2 = false;
      if (bl) {
         intValue = 4;
      }

      if (intValue > 0) {
         flag2 = true;
         intValue--;
      }

      float floatValue30 = (float)Math.toDegrees(Math.atan2(-vec3d11.x, vec3d11.z));
      float floatValue31 = (float)MathHelper.clamp(-Math.toDegrees(Math.atan2(vec3d11.y, Math.hypot(vec3d11.x, vec3d11.z))), -90.0, 90.0);
      float floatValue32 = 0.0F;
      if (flag2) {
         floatValue32 = (float)(3.0 * Math.sin(System.currentTimeMillis() / 30.0))
            + (float)(MathUtils.compute2(3, 4) * Math.cos(System.currentTimeMillis() / 60.0));
      }

      Rotation rotation3 = new Rotation(
         floatValue30 + floatValue32 + ThreadLocalRandom.current().nextFloat(-2.0F, 2.0F), floatValue31 + ThreadLocalRandom.current().nextFloat(-2.0F, 2.0F) + floatValue32
      );
      RotationController.invoke3(
         rotation3,
         (float)MathUtils.measure14(50.0, 70.0, 70L, dualTimer),
         (float)MathUtils.measure14(10.0, 20.0, 65L, dualTimer2),
         30.0F,
         30.0F,
         1,
         15,
         false
      );
   }

   public static void invoke6(LivingEntity livingEntity, boolean bl) {
      Vec3d vec3d12 = a_.player.getEyePos();
      Vec3d vec3d13 = livingEntity.getPos().add(0.0, livingEntity.getHeight() / 2.0F, 0.0).subtract(vec3d12).normalize();
      float floatValue33 = (float)Math.toDegrees(Math.atan2(-vec3d13.x, vec3d13.z));
      float floatValue34 = (float)MathHelper.clamp(-Math.toDegrees(Math.atan2(vec3d13.y, Math.hypot(vec3d13.x, vec3d13.z))), -90.0, 90.0);
      float floatValue35 = 180.0F;
      float floatValue36 = 45.0F;
      Rotation rotation4 = new Rotation(
         floatValue33 + ThreadLocalRandom.current().nextFloat(-2.0F, 2.0F), floatValue34 + ThreadLocalRandom.current().nextFloat(-1.0F, 1.0F)
      );
      RotationController.invoke3(rotation4, floatValue36, floatValue35, floatValue36, floatValue35, 0, 15, false);
   }

   public static void invoke7(LivingEntity livingEntity, boolean bl) {
      float floatValue37 = 0.02F * (float)Math.sin(System.currentTimeMillis() / 1200.0);
      float floatValue38 = 0.03F * (float)Math.sin(System.currentTimeMillis() / 900.0) + 0.02F * (float)Math.cos(System.currentTimeMillis() / 1200.0);
      float floatValue39 = 0.4F * (float)Math.cos(System.currentTimeMillis() / 700L) + 0.04F * (float)Math.sin(System.currentTimeMillis() / 900.0);
      Vec3d vec3d14 = RaycastUtils.resolve7(livingEntity).add(floatValue38, 0.0, floatValue39);
      boolean flag3 = false;
      if (bl) {
         intValue = 2;
      }

      if (intValue > 0) {
         flag3 = true;
         intValue--;
      }

      float floatValue40 = (float)Math.toDegrees(Math.atan2(-vec3d14.x, vec3d14.z));
      float floatValue41 = (float)MathHelper.clamp(-Math.toDegrees(Math.atan2(vec3d14.y, Math.hypot(vec3d14.x, vec3d14.z))), -90.0, 90.0);
      float floatValue42 = 0.0F;
      if (flag3) {
         floatValue42 = MathUtils.measure17(-3.0F, 4.0F) + (float)(2.0 * Math.sin(System.currentTimeMillis() / 30.0));
      }

      float floatValue43 = MathUtils.measure17(-3.0F, 3.0F) + (float)(3.0 * Math.cos(System.currentTimeMillis() / 40.0));
      float floatValue44 = MathUtils.measure17(-1.0F, 1.0F) + (float)(4.0 * Math.sin(System.currentTimeMillis() / 240.0));
      Rotation rotation5 = new Rotation(floatValue40 + floatValue43 + floatValue42, floatValue41 + floatValue44);
      RotationController.invoke3(rotation5, MathUtils.compute2(38, 43), MathUtils.measure19(3.0F, 5.0F), 30.0F, 30.0F, 1, 15, false);
   }

   public static void invoke8(LivingEntity livingEntity, boolean bl) {
   }

   public static void invoke9(LivingEntity livingEntity, boolean bl, String string) {
      float floatValue45 = 0.25F * (float)Math.cos(System.currentTimeMillis() / 1500L);
      float floatValue46 = 0.2F * (float)Math.cos(System.currentTimeMillis() / 700L);
      float floatValue47 = 0.2F * (float)Math.cos(System.currentTimeMillis() / 900L);
      Vec3d vec3d15 = a_.player.getEyePos();
      Vec3d vec3d16 = livingEntity.getPos().add(floatValue47, MathHelper.clamp(vec3d15.y - livingEntity.getPos().y, 0.0, 0.8) - floatValue45, floatValue46).subtract(vec3d15).normalize();
      if (string.contains("Fast")) {
         float floatValue48 = FreeLookController.floatValue;
         float floatValue49 = FreeLookController.floatValue2;
         float floatValue50 = MathUtils.measure17(190.0F, 245.0F);
         if (bl) {
            floatValue48 = (float)Math.toDegrees(Math.atan2(-vec3d16.x, vec3d16.z));
            floatValue49 = (float)MathHelper.clamp(-Math.toDegrees(Math.atan2(vec3d16.y, Math.hypot(vec3d16.x, vec3d16.z))), -90.0, 90.0);
         }

         float floatValue51 = 0.0F;
         float floatValue52 = 0.0F;
         RotationController.invoke3(new Rotation(floatValue48 + floatValue51, floatValue49 + floatValue52), floatValue50, floatValue50, 40.0F, 40.0F, 1, 7, false);
      } else if (string.contains("Smooth")) {
         float floatValue53 = FreeLookController.floatValue;
         float floatValue54 = FreeLookController.floatValue2;
         float floatValue55 = 24.0F;
         if (bl) {
            intValue = 3;
            floatValue55 = 88.0F;
         }

         if (intValue > 0) {
            floatValue53 = (float)Math.toDegrees(Math.atan2(-vec3d16.x, vec3d16.z));
            floatValue54 = (float)MathHelper.clamp(-Math.toDegrees(Math.atan2(vec3d16.y, Math.hypot(vec3d16.x, vec3d16.z))), -90.0, 90.0);
            intValue--;
         }

         float floatValue56 = 0.0F;
         float floatValue57 = 0.0F;
         RotationController.invoke3(new Rotation(floatValue53 + floatValue56, floatValue54 + floatValue57), floatValue55, floatValue55, 40.0F, 40.0F, 1, 7, false);
      } else if (string.contains("Random")) {
         float floatValue58 = FreeLookController.floatValue;
         float floatValue59 = FreeLookController.floatValue2;
         float floatValue60 = MathUtils.measure17(30.0F, 35.0F);
         if (bl) {
            intValue = MathUtils.compute2(2, 4);
         }

         if (intValue > 0) {
            floatValue60 = MathUtils.measure17(140.0F, 220.0F);
            floatValue58 = (float)Math.toDegrees(Math.atan2(-vec3d16.x, vec3d16.z));
            floatValue59 = (float)MathHelper.clamp(-Math.toDegrees(Math.atan2(vec3d16.y, Math.hypot(vec3d16.x, vec3d16.z))), -90.0, 90.0);
            intValue--;
         }

         float floatValue61 = ThreadLocalRandom.current().nextFloat(-3.0F, 3.0F)
            + (float)(MathUtils.measure17(4.0F, 5.0F) * Math.cos(System.currentTimeMillis() / 150.0))
            + (float)(MathUtils.measure17(4.0F, 5.0F) * Math.sin(System.currentTimeMillis() / 50.0))
            + (float)(MathUtils.measure17(5.0F, 8.0F) * Math.sin(System.currentTimeMillis() / 130.0))
               * (float)(MathUtils.measure17(4.0F, 7.0F) * Math.cos(System.currentTimeMillis() / 650.0))
            + (float)(MathUtils.measure17(12.0F, 18.0F) * Math.sin(System.currentTimeMillis() / 80.0))
               * (float)(MathUtils.measure17(2.0F, 3.0F) * Math.cos(System.currentTimeMillis() / 2650.0));
         float floatValue62 = ThreadLocalRandom.current().nextFloat(-1.0F, 1.0F)
            + (float)(MathUtils.measure17(2.0F, 3.0F) * Math.cos(System.currentTimeMillis() / 170.0))
            + (float)(MathUtils.measure17(3.0F, 4.0F) * Math.sin(System.currentTimeMillis() / 70.0))
            + (float)(MathUtils.measure17(1.0F, 2.0F) * Math.sin(System.currentTimeMillis() / 110.0))
               * (float)(MathUtils.measure17(1.0F, 2.0F) * Math.cos(System.currentTimeMillis() / 350.0));
         RotationController.invoke3(new Rotation(floatValue58 + floatValue61 / 4.0F, floatValue59 + floatValue62), floatValue60, floatValue60, 40.0F, 40.0F, 1, 7, false);
      }
   }

   public static void invoke10(LivingEntity livingEntity, boolean bl, String string) {
      float floatValue63 = 0.25F * (float)Math.cos(System.currentTimeMillis() / 1500L);
      float floatValue64 = 0.2F * (float)Math.cos(System.currentTimeMillis() / 700L);
      float floatValue65 = 0.2F * (float)Math.cos(System.currentTimeMillis() / 900L);
      Vec3d vec3d17 = a_.player.getEyePos();
      Vec3d vec3d18 = livingEntity.getPos().add(floatValue65, MathHelper.clamp(vec3d17.y - livingEntity.getPos().y, 0.0, 0.8) - floatValue63, floatValue64).subtract(vec3d17).normalize();
      if (string.contains("Fast")) {
         float floatValue66 = FreeLookController.floatValue;
         float floatValue67 = FreeLookController.floatValue2;
         float floatValue68 = MathUtils.measure17(280.0F, 360.0F);
         if (bl) {
            floatValue66 = (float)Math.toDegrees(Math.atan2(-vec3d18.x, vec3d18.z));
            floatValue67 = (float)MathHelper.clamp(-Math.toDegrees(Math.atan2(vec3d18.y, Math.hypot(vec3d18.x, vec3d18.z))), -90.0, 90.0);
         }

         RotationController.invoke3(new Rotation(floatValue66, floatValue67), floatValue68, floatValue68, 40.0F, 40.0F, 1, 7, false);
      } else if (string.contains("Smooth")) {
         float floatValue69 = FreeLookController.floatValue;
         float floatValue70 = FreeLookController.floatValue2;
         float floatValue71 = 24.0F;
         if (bl) {
            intValue = 2;
            floatValue71 = 130.0F;
         }

         if (intValue > 0) {
            floatValue69 = (float)Math.toDegrees(Math.atan2(-vec3d18.x, vec3d18.z));
            floatValue70 = (float)MathHelper.clamp(-Math.toDegrees(Math.atan2(vec3d18.y, Math.hypot(vec3d18.x, vec3d18.z))), -90.0, 90.0);
            intValue--;
         }

         RotationController.invoke3(new Rotation(floatValue69, floatValue70), floatValue71, floatValue71, 40.0F, 40.0F, 1, 7, false);
      } else if (string.contains("Random")) {
         float floatValue72 = FreeLookController.floatValue;
         float floatValue73 = FreeLookController.floatValue2;
         float floatValue74 = MathUtils.measure17(30.0F, 35.0F);
         if (bl) {
            intValue = 2;
         }

         if (intValue > 0) {
            floatValue74 = MathUtils.measure17(200.0F, 280.0F);
            floatValue72 = (float)Math.toDegrees(Math.atan2(-vec3d18.x, vec3d18.z));
            floatValue73 = (float)MathHelper.clamp(-Math.toDegrees(Math.atan2(vec3d18.y, Math.hypot(vec3d18.x, vec3d18.z))), -90.0, 90.0);
            intValue--;
         }

         float floatValue75 = ThreadLocalRandom.current().nextFloat(-3.0F, 3.0F)
            + (float)(MathUtils.measure17(4.0F, 5.0F) * Math.cos(System.currentTimeMillis() / 150.0))
            + (float)(MathUtils.measure17(4.0F, 5.0F) * Math.sin(System.currentTimeMillis() / 50.0))
            + (float)(MathUtils.measure17(5.0F, 8.0F) * Math.sin(System.currentTimeMillis() / 130.0))
               * (float)(MathUtils.measure17(4.0F, 7.0F) * Math.cos(System.currentTimeMillis() / 650.0))
            + (float)(MathUtils.measure17(12.0F, 18.0F) * Math.sin(System.currentTimeMillis() / 80.0))
               * (float)(MathUtils.measure17(2.0F, 3.0F) * Math.cos(System.currentTimeMillis() / 2650.0));
         float floatValue76 = ThreadLocalRandom.current().nextFloat(-1.0F, 1.0F)
            + (float)(MathUtils.measure17(2.0F, 3.0F) * Math.cos(System.currentTimeMillis() / 170.0))
            + (float)(MathUtils.measure17(3.0F, 4.0F) * Math.sin(System.currentTimeMillis() / 70.0))
            + (float)(MathUtils.measure17(1.0F, 2.0F) * Math.sin(System.currentTimeMillis() / 110.0))
               * (float)(MathUtils.measure17(1.0F, 2.0F) * Math.cos(System.currentTimeMillis() / 350.0));
         RotationController.invoke3(new Rotation(floatValue72 + floatValue75 / 4.0F, floatValue73 + floatValue76), floatValue74, floatValue74, 40.0F, 40.0F, 1, 7, false);
      }
   }

   @Generated
   private RotationSmoothing() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }
}
