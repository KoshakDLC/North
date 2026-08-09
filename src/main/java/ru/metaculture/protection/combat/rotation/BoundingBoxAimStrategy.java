package ru.metaculture.protection;

import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class BoundingBoxAimStrategy implements MinecraftAccessor {
   private static final int INT_VALUE = 15;
   private static boolean flag;
   private static int intValue = -1;
   private static float floatValue;
   private static float floatValue2;

   public static void invoke(LivingEntity livingEntity) {
      if (a_.player == null) {
         invoke5();
      } else if (a_.world != null && livingEntity != null) {
         Box box2 = livingEntity.getBoundingBox();
         Vec3d vec3d2 = a_.player.getEyePos();
         boolean flag = AttackAura.sidepointExtraChecks.isEnabled();
         if (intValue != livingEntity.getId()) {
            intValue = livingEntity.getId();
            floatValue = FreeLookController.floatValue;
            floatValue2 = FreeLookController.floatValue2;
         }

         Vec3d vec3d3 = livingEntity.getPos()
            .add(0.0, MathHelper.clamp(vec3d2.y - livingEntity.getY(), 0.0, livingEntity.getHeight()), 0.0)
            .subtract(vec3d2)
            .normalize();
         float floatValue = (float)Math.toDegrees(Math.atan2(-vec3d3.x, vec3d3.z));
         float floatValue2 = (float)MathHelper.clamp(Math.toDegrees(Math.asin(-vec3d3.y)), -90.0, 90.0);
         float floatValue3 = Float.MAX_VALUE;
         float floatValue4 = -Float.MAX_VALUE;
         float floatValue5 = Float.MAX_VALUE;
         float floatValue6 = -Float.MAX_VALUE;
         double[][] doubleValuesValues = new double[][]{
            {box2.minX, box2.minY, box2.minZ},
            {box2.minX, box2.minY, box2.maxZ},
            {box2.minX, box2.maxY, box2.minZ},
            {box2.minX, box2.maxY, box2.maxZ},
            {box2.maxX, box2.minY, box2.minZ},
            {box2.maxX, box2.minY, box2.maxZ},
            {box2.maxX, box2.maxY, box2.minZ},
            {box2.maxX, box2.maxY, box2.maxZ}
         };

         for (double[] doubleValues : doubleValuesValues) {
            Vec3d vec3d4 = new Vec3d(doubleValues[0], doubleValues[1], doubleValues[2]).subtract(vec3d2);
            float floatValue7 = (float)Math.toDegrees(Math.atan2(-vec3d4.x, vec3d4.z));
            float floatValue8 = (float)MathHelper.clamp(-Math.toDegrees(Math.atan2(vec3d4.y, Math.hypot(vec3d4.x, vec3d4.z))), -90.0, 90.0);
            float floatValue9 = MathHelper.wrapDegrees(floatValue7 - floatValue);
            float floatValue10 = floatValue8 - floatValue2;
            floatValue3 = Math.min(floatValue3, floatValue9);
            floatValue4 = Math.max(floatValue4, floatValue9);
            floatValue5 = Math.min(floatValue5, floatValue10);
            floatValue6 = Math.max(floatValue6, floatValue10);
         }

         float floatValue11 = 22.0F;
         float floatValue12 = MathHelper.clamp(MathHelper.wrapDegrees(FreeLookController.floatValue - floatValue), floatValue3 - floatValue11, floatValue4 + floatValue11);
         float floatValue13;
         if (flag) {
            float floatValue14 = (float)(System.currentTimeMillis() % 2000L) / 2000.0F;
            float floatValue15 = (float)Math.sin(floatValue14 * Math.PI * 2.0);
            float floatValue16 = (floatValue6 - floatValue5) * 0.75F + MathUtils.measure19(15.0F, 30.0F);
            float floatValue17 = (floatValue5 + floatValue6) * 0.5F;
            floatValue13 = floatValue17 + floatValue15 * floatValue16 + MathUtils.measure19(-4.0F, 4.0F);
         } else {
            floatValue13 = MathHelper.clamp(FreeLookController.floatValue2 - floatValue2, floatValue5 - floatValue11, floatValue6 + floatValue11);
         }

         float floatValue18 = floatValue + floatValue12;
         float floatValue19 = MathHelper.clamp(floatValue2 + floatValue13, -90.0F, 90.0F);
         float floatValue20 = FreeLookController.floatValue;
         float floatValue21 = FreeLookController.floatValue2;
         float floatValue22 = Math.abs(MathHelper.wrapDegrees(floatValue20 - floatValue));
         float floatValue23 = Math.abs(floatValue21 - floatValue2);
         floatValue = floatValue20;
         floatValue2 = floatValue21;
         float[] floatValues = AttackAura.resolve2(livingEntity);
         float[] floatValues2 = new float[]{floatValues[0], floatValues[1], floatValues[0] + floatValues[1]};
         boolean flag2 = AttackUtils.check15(livingEntity, false, true, true, -50L, floatValues2)
            && RaycastUtils.measure((Entity)livingEntity) < AttackAura.measure(livingEntity);
         if (!flag2) {
            float floatValue24 = MathUtils.measure19(0.3F, 0.6F);
            float floatValue25 = MathUtils.measure19(0.2F, 0.4F);
            float floatValue26 = floatValue24 + floatValue22 * 3.5F;
            float floatValue27 = floatValue25 + floatValue23 * 3.5F;
            float floatValue28 = MathHelper.wrapDegrees(floatValue18 - a_.player.getYaw());
            float floatValue29 = floatValue19 - a_.player.getPitch();
            float floatValue30 = MathHelper.clamp(floatValue28, -floatValue26, floatValue26);
            float floatValue31 = flag ? MathHelper.clamp(floatValue29, -floatValue27 * 5.0F, floatValue27 * 5.0F) : MathHelper.clamp(floatValue29, -floatValue27, floatValue27);
            float floatValue32 = a_.player.getYaw() + floatValue30;
            float floatValue33 = MathHelper.clamp(a_.player.getPitch() + floatValue31, -90.0F, 90.0F);
            float floatValue34 = Math.max(floatValue26 * 18.5F, 6.0F);
            float floatValue35 = Math.max(floatValue27 * 18.5F, 4.0F);
            invoke4(new Rotation(floatValue32, floatValue33), floatValue34, floatValue35);
         } else {
            float floatValue36 = a_.player.getYaw();
            float floatValue37 = a_.player.getPitch();
            float floatValue38 = MathHelper.wrapDegrees(floatValue36 - floatValue);
            float floatValue39 = floatValue37 - floatValue2;
            boolean flag3 = floatValue38 >= floatValue3 && floatValue38 <= floatValue4 && floatValue39 >= floatValue5 && floatValue39 <= floatValue6;
            if (!flag3) {
               invoke3(box2, vec3d2, floatValue36, floatValue37);
            } else {
               invoke4(new Rotation(floatValue36, floatValue37), 6.0F, 4.0F);
            }
         }
      } else {
         invoke2();
      }
   }

   public static void invoke2() {
      if (!flag) {
         invoke5();
      } else {
         if (a_.player != null) {
            FreeLookController.floatValue = a_.player.getYaw();
            FreeLookController.floatValue2 = a_.player.getPitch();
         }

         RotationController.rotationControllerState2 = RotationController.RotationControllerState2.IDLE;
         RotationController.intValue = 0;
         RotationController.flag = false;
         RotationController.rotation = null;
         RotationController.intValue3 = 0;
         FreeLookController.active = FreeLookController.flag;
         invoke5();
      }
   }

   private static void invoke3(Box box, Vec3d vec3d, float f, float g) {
      boolean flag4 = ThreadLocalRandom.current().nextBoolean();
      float floatValue40 = flag4 ? MathUtils.measure19(0.05F, 0.2F) : MathUtils.measure19(0.8F, 0.95F);
      float floatValue41 = (float)(ThreadLocalRandom.current().nextDouble() * ThreadLocalRandom.current().nextDouble());
      float floatValue42 = 0.5F + floatValue41 * 0.5F;
      float floatValue43 = MathUtils.measure19(-1.0F, 1.0F) * MathUtils.measure19(0.0F, 1.0F);
      float floatValue44 = (floatValue43 + 1.0F) / 2.0F;
      double doubleValue = box.minX + (box.maxX - box.minX) * floatValue40;
      double doubleValue2 = box.minY + (box.maxY - box.minY) * floatValue42;
      double doubleValue3 = box.minZ + (box.maxZ - box.minZ) * floatValue44;
      Vec3d vec3d5 = new Vec3d(doubleValue, doubleValue2, doubleValue3).subtract(vec3d);
      float floatValue45 = (float)Math.toDegrees(Math.atan2(-vec3d5.x, vec3d5.z));
      float floatValue46 = (float)MathHelper.clamp(-Math.toDegrees(Math.atan2(vec3d5.y, Math.hypot(vec3d5.x, vec3d5.z))), -90.0, 90.0);
      float floatValue47 = Math.abs(MathHelper.wrapDegrees(floatValue45 - f));
      float floatValue48 = Math.abs(floatValue46 - g);
      float floatValue49 = MathUtils.measure19(15.0F, 25.0F) + floatValue47 / 90.0F * MathUtils.measure19(30.0F, 40.0F);
      float floatValue50 = MathUtils.measure19(10.0F, 18.0F) + floatValue48 / 90.0F * MathUtils.measure19(20.0F, 27.0F);
      invoke4(new Rotation(floatValue45, floatValue46), floatValue49, floatValue50);
   }

   private static void invoke4(Rotation rotation, float f, float g) {
      flag = RotationController.intValue <= 15;
      RotationController.invoke3(
         rotation, f, g, MathUtils.compute2(25, 45), MathUtils.compute2(10, 25), MathUtils.compute2(0, 2), 15, false
      );
   }

   private static void invoke5() {
      flag = false;
      intValue = -1;
      floatValue = 0.0F;
      floatValue2 = 0.0F;
   }
}
