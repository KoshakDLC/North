package ru.metaculture.protection;

import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class WaveAimStrategy implements MinecraftAccessor {
   private static float floatValue;
   private static float floatValue2;
   private static int intValue;
   private static boolean flag;
   private static long timestamp;
   private static float floatValue3 = 2.5F;
   private static float floatValue4 = 1.2F;
   private static boolean flag2;
   private static long timestamp2;
   private static long timestamp3 = System.currentTimeMillis() + ThreadLocalRandom.current().nextLong(8500L, 14000L);

   public static void invoke() {
      flag = false;
      intValue = 0;
      flag2 = false;
      timestamp = 0L;
      timestamp3 = System.currentTimeMillis() + ThreadLocalRandom.current().nextLong(8500L, 14000L);
   }

   public static void invoke2(LivingEntity livingEntity) {
      if (a_.player != null) {
         long longValue = System.currentTimeMillis();
         if (!flag) {
            floatValue = a_.player.getYaw();
            floatValue2 = a_.player.getPitch();
            flag = true;
         }

         if (longValue >= timestamp) {
            floatValue3 = MathUtils.measure19(1.6F, 4.6F);
            floatValue4 = MathUtils.measure19(0.8F, 2.4F);
            timestamp = longValue + ThreadLocalRandom.current().nextLong(140L, 260L);
         }

         if (!flag2 && longValue >= timestamp3) {
            flag2 = true;
            timestamp2 = longValue + ThreadLocalRandom.current().nextLong(170L, 290L);
            timestamp3 = longValue + ThreadLocalRandom.current().nextLong(7800L, 13500L);
         }

         if (flag2 && longValue >= timestamp2) {
            flag2 = false;
         }

         Vec3d vec3d = RaycastUtils.resolve6(livingEntity);
         float floatValue = (float)Math.toDegrees(Math.atan2(-vec3d.x, vec3d.z));
         float floatValue2 = (float)MathHelper.clamp(-Math.toDegrees(Math.atan2(vec3d.y, Math.hypot(vec3d.x, vec3d.z))), -90.0, 90.0);
         float[] floatValues = AttackAura.resolve2(livingEntity);
         float[] floatValues2 = new float[]{floatValues[0], floatValues[1], floatValues[0] + floatValues[1]};
         boolean flag = AttackUtils.check15(livingEntity, false, true, true, -45L, floatValues2);
         if (flag) {
            intValue = 2;
         }

         boolean flag2 = intValue > 0;
         if (intValue > 0) {
            intValue--;
         }

         float floatValue3 = MathHelper.wrapDegrees(floatValue - floatValue);
         float floatValue4 = floatValue2 - floatValue2;
         float floatValue5 = flag2 ? MathHelper.clamp(floatValue3 * 0.92F, -56.0F, 56.0F) : MathHelper.clamp(floatValue3 * 0.34F, -17.0F, 17.0F);
         float floatValue6 = flag2 ? MathHelper.clamp(floatValue4 * 0.84F, -46.0F, 46.0F) : MathHelper.clamp(floatValue4 * 0.3F, -12.0F, 12.0F);
         floatValue += floatValue5;
         floatValue2 += floatValue6;
         float floatValue7 = (float)Math.sin(longValue / 65.0);
         float floatValue8 = (float)Math.cos(longValue / 48.0);
         floatValue = floatValue + floatValue7 * floatValue3;
         floatValue2 = floatValue2 + floatValue8 * floatValue4;
         if (flag2) {
            floatValue2 = MathHelper.clamp(floatValue2 - MathUtils.measure19(7.5F, 12.5F), -89.0F, 89.0F);
         }

         float floatValue9 = floatValue;
         float floatValue10 = MathHelper.clamp(floatValue2, -89.5F, 89.5F);
         float floatValue11 = flag2 ? MathUtils.measure19(66.0F, 94.0F) : MathUtils.measure19(26.0F, 44.0F);
         float floatValue12 = flag2 ? MathUtils.measure19(104.0F, 146.0F) : MathUtils.measure19(34.0F, 58.0F);
         RotationController.invoke3(
            new Rotation(floatValue9, floatValue10),
            floatValue11,
            floatValue12,
            MathUtils.compute2(30, 48),
            MathUtils.compute2(16, 34),
            MathUtils.compute2(0, 3),
            15,
            false
         );
      }
   }
}
