package ru.metaculture.protection;

import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public final class RandomAimStrategy implements MinecraftAccessor {
   private static final long TIMESTAMP = 520L;
   private static float floatValue;
   private static float floatValue2;
   private static float floatValue3;
   private static float floatValue4;
   private static float floatValue5;
   private static Vec3d vec3d;
   private static Vec3d vec3d2;
   private static int intValue;
   private static boolean flag;
   private static long timestamp;
   private static int intValue2;
   private static int intValue3 = ThreadLocalRandom.current().nextInt(7, 15);
   private static boolean flag2;
   private static long timestamp2;
   private static int intValue4 = Integer.MIN_VALUE;
   private static boolean flag3;
   private static long timestamp3;

   public static void invoke(LivingEntity livingEntity) {
      if (a_.player != null && a_.world != null && livingEntity != null) {
         flag3 = false;
         long longValue = System.currentTimeMillis();
         invoke7(livingEntity);
         vec3d = livingEntity.getPos()
            .add(
               Math.sin(longValue / 900.0) * 0.2F,
               livingEntity.getHeight() / 2.0F + livingEntity.getHeight() / 2.5F * Math.sin(longValue / 700.0),
               Math.cos(longValue / 700.0) * 0.12F
            );
         vec3d2 = a_.player.getEyePos();
         Vec3d direction = vec3d.subtract(vec3d2).normalize();
         float floatValue = (float)Math.toDegrees(Math.atan2(-direction.x, direction.z));
         float floatValue2 = (float)MathHelper.clamp(-Math.toDegrees(Math.atan2(direction.y, Math.hypot(direction.x, direction.z))), -90.0, 90.0);
         float floatValue3 = measure3(3.0, 11.0, 90.0);
         float floatValue4 = measure4(3.0, 11.0, 100.0);
         floatValue4 = floatValue3;
         floatValue5 = floatValue4;
         float[] floatValues = AttackAura.resolve2(livingEntity);
         float[] floatValues2 = new float[]{floatValues[0], floatValues[1], floatValues[0] + floatValues[1]};
         boolean flag = AttackUtils.check15(livingEntity, false, true, true, -200L, floatValues2);
         if (flag) {
            floatValue = 1.0F;
         }

         if (flag2) {
            if (longValue > timestamp2) {
               flag2 = false;
            } else {
               floatValue2 = a_.player.getYaw() - (ThreadLocalRandom.current().nextBoolean() ? -measure(20.0F, 40.0F) : measure(20.0F, 30.0F));
               floatValue3 = measure(85.0F, 90.0F);
            }
         }

         if (floatValue != 0.0F) {
            if (!flag2) {
               floatValue2 = floatValue;
               floatValue3 = floatValue2;
            }

            floatValue--;
         }

         if (flag && longValue >= timestamp) {
            flag = false;
         }

         RotationController.invoke3(
            new Rotation(floatValue2 + floatValue3, MathHelper.clamp(floatValue3 + floatValue4, -90.0F, 90.0F)),
            measure(40.124813F, 55.41284F),
            flag2 ? 360.0F : measure(4.412848F, 12.412894F),
            measure(40.124813F, 140.41284F),
            measure(40.124813F, 140.41284F),
            0,
            1,
            false
         );
      }
   }

   public static void invoke2() {
      flag = true;
      timestamp = System.currentTimeMillis() + 150L;
      intValue = (intValue + 1) % 2;
      intValue2++;
      if (intValue2 >= intValue3) {
         intValue2 = 0;
         intValue3 = ThreadLocalRandom.current().nextInt(4, 6);
         flag2 = true;
         timestamp2 = System.currentTimeMillis() + ThreadLocalRandom.current().nextInt(60, 110);
      }
   }

   public static void invoke3() {
      if (a_.player != null && (check() || flag3)) {
         if (!flag3) {
            flag3 = true;
            timestamp3 = System.currentTimeMillis();
         }

         invoke5();
         invoke6(false);
      } else {
         invoke6(true);
      }
   }

   public static void invoke4() {
      if (flag3) {
         if (a_.player == null) {
            invoke6(true);
         } else {
            invoke5();
         }
      }
   }

   private static void invoke5() {
      long longValue2 = System.currentTimeMillis();
      float floatValue5 = MathHelper.clamp((float)(longValue2 - timestamp3) / 520.0F, 0.0F, 1.0F);
      float floatValue6 = 1.0F - floatValue5;
      float floatValue7 = floatValue6 * floatValue6;
      float floatValue8 = measure3(3.0, 11.0, 90.0) * floatValue7;
      float floatValue9 = measure4(3.0, 11.0, 100.0) * floatValue7;
      float floatValue10 = measure2(5.0F, 42.0F, floatValue7);
      float floatValue11 = measure2(3.0F, 18.0F, floatValue7);
      float floatValue12 = measure2(4.0F, 32.0F, floatValue7);
      RotationController.invoke3(
         new Rotation(FreeLookController.floatValue + floatValue8, MathHelper.clamp(FreeLookController.floatValue2 + floatValue9, -90.0F, 90.0F)),
         floatValue10,
         floatValue11,
         floatValue12,
         floatValue12,
         0,
         1,
         false
      );
      if (floatValue5 >= 1.0F) {
         invoke6(true);
      }
   }

   private static void invoke6(boolean bl) {
      floatValue = 0.0F;
      floatValue4 = 0.0F;
      floatValue5 = 0.0F;
      vec3d = null;
      vec3d2 = null;
      intValue = 0;
      flag = false;
      timestamp = 0L;
      intValue2 = 0;
      intValue3 = ThreadLocalRandom.current().nextInt(7, 15);
      flag2 = false;
      timestamp2 = 0L;
      intValue4 = Integer.MIN_VALUE;
      if (bl) {
         flag3 = false;
         timestamp3 = 0L;
         if (a_.player != null) {
            floatValue2 = a_.player.getYaw();
            floatValue3 = a_.player.getPitch();
         }
      }
   }

   private static void invoke7(LivingEntity livingEntity) {
      if (intValue4 != livingEntity.getId()) {
         intValue4 = livingEntity.getId();
         floatValue = 0.0F;
         flag2 = false;
         floatValue2 = a_.player.getYaw();
         floatValue3 = a_.player.getPitch();
      }
   }

   private static boolean check() {
      return intValue4 != Integer.MIN_VALUE
         || vec3d != null
         || vec3d2 != null
         || floatValue != 0.0F
         || flag
         || flag2
         || floatValue4 != 0.0F
         || floatValue5 != 0.0F;
   }

   private static float measure(float f, float g) {
      return ThreadLocalRandom.current().nextFloat(f, g);
   }

   private static float measure2(float f, float g, float h) {
      return f + (g - f) * h;
   }

   private static float measure3(double d, double e, double f) {
      return (float)(Math.sin(System.currentTimeMillis() / f) * measure((float)d, (float)e));
   }

   private static float measure4(double d, double e, double f) {
      return (float)(Math.cos(System.currentTimeMillis() / f) * measure((float)d, (float)e));
   }
}
