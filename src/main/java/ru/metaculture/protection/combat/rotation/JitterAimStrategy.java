package ru.metaculture.protection;

import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class JitterAimStrategy implements MinecraftAccessor {
   static float floatValue;
   static float floatValue2;
   static float floatValue3;
   public static long timestamp = 0L;
   public static long timestamp2 = ThreadLocalRandom.current().nextLong(90000L, 180000L);
   public static boolean flag = false;
   public static long timestamp3 = 0L;
   public static int intValue = 0;

   public static void invoke(LivingEntity livingEntity) {
      long longValue = System.currentTimeMillis();
      if (!flag && longValue - timestamp >= timestamp2) {
         flag = true;
         timestamp3 = longValue;
         intValue = ThreadLocalRandom.current().nextInt(300, 400);
         timestamp = longValue;
         timestamp2 = ThreadLocalRandom.current().nextLong(9100L, 11200L);
      }

      boolean flag = false;
      if (flag && longValue - timestamp3 >= intValue) {
         flag = false;
      }

      if (longValue - timestamp3 >= intValue + 70L) {
         flag = true;
      }

      Vec3d vec3d = RaycastUtils.resolve6(livingEntity);
      float floatValue = (float)Math.toDegrees(Math.atan2(-vec3d.x, vec3d.z));
      float floatValue2 = (float)MathHelper.clamp(-Math.toDegrees(Math.atan2(vec3d.y, Math.hypot(vec3d.x, vec3d.z))), -90.0, 90.0);
      float[] floatValues = AttackAura.resolve2(livingEntity);
      float[] floatValues2 = new float[]{floatValues[0], floatValues[1], floatValues[0] + floatValues[1]};
      boolean flag2 = AttackUtils.check15(livingEntity, false, true, true, -50L, floatValues2);
      float floatValue3 = a_.player.getYaw();
      float floatValue4 = Math.abs(MathHelper.wrapDegrees(floatValue3 - floatValue));
      float floatValue5 = MathUtils.measure19(62.0F, 84.0F);
      float floatValue6 = !flag ? MathUtils.measure19(120.0F, 170.0F) : MathUtils.measure19(9.0F, 13.0F);
      if (flag2) {
         floatValue = 2.0F;
      }

      boolean flag3 = false;
      if (floatValue > 0.0F) {
         flag3 = true;
         floatValue--;
      }

      float floatValue7 = (float)Math.cos(System.currentTimeMillis() / 40.0);
      float floatValue8 = (float)Math.sin(System.currentTimeMillis() / 70.0);
      if (flag3) {
         floatValue2 = floatValue;
         floatValue3 = floatValue2;
      }

      float floatValue9 = floatValue7 * MathUtils.measure19(9.0F, 17.0F);
      float floatValue10 = floatValue8 * MathUtils.measure19(4.0F, 13.0F);
      float floatValue11 = flag ? -MathUtils.measure19(85.0F, 90.0F) : floatValue3;
      RotationController.invoke3(
         new Rotation(floatValue2 + floatValue9, floatValue11 + floatValue10),
         floatValue5,
         floatValue6,
         MathUtils.compute2(35, 45),
         MathUtils.compute2(19, 45),
         MathUtils.compute2(0, 3),
         15,
         false
      );
   }
}
