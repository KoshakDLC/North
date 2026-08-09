package ru.metaculture.protection;

import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class SmoothAimStrategy implements MinecraftAccessor {
   public static void invoke(LivingEntity livingEntity) {
      if (livingEntity != null && a_.player != null) {
         Vec3d vec3d = RaycastUtils.resolve6(livingEntity);
         float floatValue = (float)Math.toDegrees(Math.atan2(-vec3d.x, vec3d.z));
         float floatValue2 = (float)MathHelper.clamp(-Math.toDegrees(Math.atan2(vec3d.y, Math.hypot(vec3d.x, vec3d.z))), -90.0, 70.0);
         float floatValue3 = a_.player.getYaw();
         float floatValue4 = a_.player.getPitch();
         float floatValue5 = MathHelper.wrapDegrees(floatValue - floatValue3);
         float floatValue6 = floatValue2 - floatValue4;
         float floatValue7 = floatValue5 / 3.0F;
         float floatValue8 = floatValue6 / 6.0F;
         float floatValue9 = 1.0F + (float)ThreadLocalRandom.current().nextDouble(-1.0, 1.5);
         float floatValue10 = 1.0F + (float)ThreadLocalRandom.current().nextDouble(-0.4, 1.333);
         float floatValue11 = floatValue7 * floatValue9;
         float floatValue12 = floatValue8 * floatValue10;
         if (a_.player.isSwimming()) {
            RotationController.invoke3(new Rotation(floatValue, floatValue2), 360.0F, 360.0F, 20.0F, 20.0F, 2, 15, false);
         } else {
            RotationController.invoke3(new Rotation(floatValue3 + floatValue11, floatValue4 + floatValue12), 360.0F, 360.0F, 20.0F, 20.0F, 2, 15, false);
         }
      }
   }
}
