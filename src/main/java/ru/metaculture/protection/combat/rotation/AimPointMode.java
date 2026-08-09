package ru.metaculture.protection;

import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public final class AimPointMode implements MinecraftAccessor {
   private static int intValue;
   private static int intValue2 = -1;
   private static int intValue3;
   private static long timestamp;
   private static boolean flag;
   private static long timestamp2;
   private static int intValue4;
   private static long timestamp3;

   private AimPointMode() {
   }

   public static void invoke(LivingEntity livingEntity) {
      if (a_.player != null && a_.world != null && livingEntity != null) {
         RotationPresetManager rotationPresetManager = RotationPresetManager.resolve();
         long longValue = System.currentTimeMillis();
         if (intValue2 != livingEntity.getId()) {
            intValue2 = livingEntity.getId();
            intValue = 0;
            intValue3 = 0;
            timestamp = 0L;
         }

         if (rotationPresetManager.funtime2 != null && !rotationPresetManager.funtime2.equals("Custom")) {
            invoke2(rotationPresetManager, livingEntity, longValue);

            try {
               boolean flag = check2(livingEntity);
               float[] floatValues = resolve7(rotationPresetManager, flag);
               int intValue = Math.round(rotationPresetManager.floatValue13);
               RotationController.invoke(
                  new RotationController.RotationControllerState3(floatValues[0], floatValues[1], intValue, intValue, rotationPresetManager.flag), () -> invoke3(rotationPresetManager.funtime2, livingEntity)
               );
            } finally {
               RotationOffsetState.invoke3();
            }
         } else {
            Vec3d vec3d2 = resolve3(livingEntity, rotationPresetManager, longValue);
            Vec3d vec3d3 = vec3d2.subtract(a_.player.getEyePos());
            float floatValue = (float)Math.toDegrees(Math.atan2(-vec3d3.x, vec3d3.z));
            float floatValue2 = (float)MathHelper.clamp(-Math.toDegrees(Math.atan2(vec3d3.y, Math.hypot(vec3d3.x, vec3d3.z))), -90.0, 90.0);
            boolean flag2 = check2(livingEntity);
            if ("Static".equals(rotationPresetManager.smooth)) {
               floatValue2 *= 0.4F;
            } else if ("Locked".equals(rotationPresetManager.smooth)) {
               floatValue2 = 0.0F;
            }

            if (rotationPresetManager.flag2 && check(longValue, rotationPresetManager)) {
               floatValue2 = -rotationPresetManager.floatValue23;
            }

            float floatValue3 = (float)(Math.cos(longValue / 40.0) * rotationPresetManager.floatValue7) + measure(-rotationPresetManager.floatValue7, rotationPresetManager.floatValue7) * 0.5F;
            float floatValue4 = (float)(Math.sin(longValue / 70.0) * rotationPresetManager.floatValue8) + measure(-rotationPresetManager.floatValue8, rotationPresetManager.floatValue8) * 0.5F;
            float floatValue5 = floatValue + rotationPresetManager.floatValue15 + floatValue3;
            float floatValue6 = floatValue2 + rotationPresetManager.floatValue16 + floatValue4;
            floatValue6 = MathHelper.clamp(floatValue6, rotationPresetManager.floatValue17, rotationPresetManager.floatValue18);
            floatValue6 = MathHelper.clamp(floatValue6, -90.0F, 90.0F);
            float[] floatValues2 = resolve7(rotationPresetManager, flag2);
            float floatValue7 = floatValues2[0];
            float floatValue8 = floatValues2[1];
            Rotation rotation = new Rotation(floatValue5, floatValue6);
            int intValue2 = Math.round(rotationPresetManager.floatValue13);
            RotationController.invoke3(rotation, floatValue7, floatValue8, intValue2, intValue2, ThreadLocalRandom.current().nextInt(1, 3), 5, rotationPresetManager.flag);
         }
      }
   }

   private static void invoke2(RotationPresetManager rotationPresetManager2, LivingEntity livingEntity, long l) {
      RotationOffsetState.invoke3();
      RotationOffsetState.flag = true;
      float floatValue9 = rotationPresetManager2.floatValue15;
      float floatValue10 = rotationPresetManager2.floatValue16;
      boolean flag3 = rotationPresetManager2.items != null && !rotationPresetManager2.items.isEmpty()
         || !"Multipoint".equals(rotationPresetManager2.multipoint)
         || rotationPresetManager2.floatValue12 > 0.001F
         || rotationPresetManager2.floatValue19 > 0.001F;
      if (flag3) {
         Box box2 = livingEntity.getBoundingBox();
         Vec3d vec3d4 = PlayerPoseUtils.resolve4(box2, false);
         Vec3d vec3d5 = resolve2(livingEntity, box2, rotationPresetManager2, l);
         float[] floatValues3 = resolve(vec3d4);
         float[] floatValues4 = resolve(vec3d5);
         floatValue9 += MathHelper.wrapDegrees(floatValues4[0] - floatValues3[0]);
         floatValue10 += floatValues4[1] - floatValues3[1];
      }

      RotationOffsetState.floatValue3 = rotationPresetManager2.floatValue17;
      RotationOffsetState.floatValue4 = rotationPresetManager2.floatValue18;
      RotationOffsetState.invoke(floatValue9 * rotationPresetManager2.floatValue21, floatValue10 * rotationPresetManager2.floatValue21, rotationPresetManager2.floatValue20);
   }

   private static float[] resolve(Vec3d vec3d) {
      Vec3d vec3d6 = vec3d.subtract(a_.player.getEyePos());
      float floatValue11 = (float)Math.toDegrees(Math.atan2(-vec3d6.x, vec3d6.z));
      float floatValue12 = (float)MathHelper.clamp(-Math.toDegrees(Math.atan2(vec3d6.y, Math.hypot(vec3d6.x, vec3d6.z))), -90.0, 90.0);
      return new float[]{floatValue11, floatValue12};
   }

   private static Vec3d resolve2(LivingEntity livingEntity, Box box, RotationPresetManager rotationPresetManager3, long l) {
      Vec3d vec3d7;
      if (rotationPresetManager3.items != null && !rotationPresetManager3.items.isEmpty()) {
         vec3d7 = resolve4(livingEntity, box, rotationPresetManager3, l);
      } else {
         String text = rotationPresetManager3.multipoint;

         vec3d7 = switch (text) {
            case "Center" -> box.getCenter();
            case "Eyes" -> new Vec3d(livingEntity.getX(), livingEntity.getEyeY(), livingEntity.getZ());
            case "Closest" -> RaycastUtils.resolve4(a_.player.getEyePos(), livingEntity);
            default -> PlayerPoseUtils.resolve4(box, false);
         };
      }

      double doubleValue = 0.0;
      double doubleValue2 = 0.0;
      if (rotationPresetManager3.floatValue12 > 0.001F) {
         Vec3d vec3d8 = resolve6(livingEntity);
         double doubleValue3 = rotationPresetManager3.floatValue12 * Math.sin(l / 300.0);
         doubleValue = vec3d8.x * doubleValue3;
         doubleValue2 = vec3d8.z * doubleValue3;
      }

      Vec3d vec3d9 = vec3d7.add(doubleValue, 0.0, doubleValue2);
      if (rotationPresetManager3.floatValue19 > 0.001F) {
         Vec3d vec3d10 = livingEntity.getVelocity();
         vec3d9 = vec3d9.add(vec3d10.x * rotationPresetManager3.floatValue19 * 3.0, 0.0, vec3d10.z * rotationPresetManager3.floatValue19 * 3.0);
      }

      return vec3d9;
   }

   private static void invoke3(String string, LivingEntity livingEntity) {
      switch (string) {
         case "FunTime":
            RandomAimStrategy.invoke(livingEntity);
            break;
         case "Smooth":
            SmoothAimStrategy.invoke(livingEntity);
            break;
         default:
            float[] floatValues5 = AttackAura.resolve2(livingEntity);
            float[] floatValues6 = new float[]{floatValues5[0], floatValues5[1], floatValues5[0] + floatValues5[1]};
            boolean flag4 = AttackUtils.check15(livingEntity, false, true, true, -50L, floatValues6);
            switch (string) {
               case "Snap":
                  RotationSmoothing.invoke9(livingEntity, flag4, "Fast");
                  break;
               case "Holy":
                  RotationSmoothing.invoke5(livingEntity, flag4);
                  break;
               case "Spooky":
                  RotationSmoothing.invoke4(livingEntity, flag4);
                  break;
               case "Matrix":
                  if (ServerBlockUtils.check6("spookytime")) {
                     RotationSmoothing.invoke4(livingEntity, flag4);
                  } else if (ServerBlockUtils.check6("holy")) {
                     RotationSmoothing.invoke5(livingEntity, flag4);
                  } else if (ServerBlockUtils.check6("ares")) {
                     RotationSmoothing.invoke6(livingEntity, flag4);
                  } else {
                     RotationSmoothing.invoke4(livingEntity, flag4);
                  }
            }
      }
   }

   private static boolean check(long l, RotationPresetManager rotationPresetManager4) {
      long longValue2 = (long)(rotationPresetManager4.floatValue24 * 1000.0F);
      if (!flag && l - timestamp3 >= longValue2) {
         flag = true;
         timestamp2 = l;
         intValue4 = ThreadLocalRandom.current().nextInt(200, 320);
         timestamp3 = l;
      }

      if (flag && l - timestamp2 >= intValue4) {
         flag = false;
      }

      return flag;
   }

   private static Vec3d resolve3(LivingEntity livingEntity, RotationPresetManager rotationPresetManager5, long l) {
      Box box3 = livingEntity.getBoundingBox();
      Vec3d vec3d11;
      if (rotationPresetManager5.items != null && !rotationPresetManager5.items.isEmpty()) {
         vec3d11 = resolve4(livingEntity, box3, rotationPresetManager5, l);
      } else {
         String text2 = rotationPresetManager5.multipoint;

         vec3d11 = switch (text2) {
            case "Center" -> box3.getCenter();
            case "Eyes" -> new Vec3d(livingEntity.getX(), livingEntity.getEyeY(), livingEntity.getZ());
            case "Closest" -> RaycastUtils.resolve4(a_.player.getEyePos(), livingEntity);
            default -> PlayerPoseUtils.resolve4(box3, false);
         };
      }

      double doubleValue4 = Math.max(0.2, (double)rotationPresetManager5.floatValue11);
      double doubleValue5 = rotationPresetManager5.floatValue9 * Math.sin(l / (250.0 / doubleValue4));
      double doubleValue6 = rotationPresetManager5.floatValue10 * Math.cos(l / (520.0 / doubleValue4));
      double doubleValue7 = 0.0;
      double doubleValue8 = 0.0;
      if (rotationPresetManager5.floatValue12 > 0.001F) {
         Vec3d vec3d12 = resolve6(livingEntity);
         double doubleValue9 = rotationPresetManager5.floatValue12 * Math.sin(l / 300.0);
         doubleValue7 = vec3d12.x * doubleValue9;
         doubleValue8 = vec3d12.z * doubleValue9;
      }

      Vec3d vec3d13 = vec3d11.add(doubleValue5 + doubleValue7, doubleValue6, doubleValue8);
      if (rotationPresetManager5.floatValue19 > 0.001F) {
         Vec3d vec3d14 = livingEntity.getVelocity();
         vec3d13 = vec3d13.add(vec3d14.x * rotationPresetManager5.floatValue19 * 3.0, 0.0, vec3d14.z * rotationPresetManager5.floatValue19 * 3.0);
      }

      return vec3d13;
   }

   private static Vec3d resolve4(LivingEntity livingEntity, Box box, RotationPresetManager rotationPresetManager6, long l) {
      int intValue3 = rotationPresetManager6.items.size();
      long longValue3 = (long)(rotationPresetManager6.floatValue14 * 1000.0F / Math.max(0.1F, rotationPresetManager6.floatValue22));
      if ("Cycle".equals(rotationPresetManager6.cycle)) {
         if (l >= timestamp) {
            intValue3 = (intValue3 + 1) % intValue3;
            timestamp = l + longValue3;
         }

         return resolve5(livingEntity, box, rotationPresetManager6.items.get(Math.min(intValue3, intValue3 - 1)));
      } else if ("Random".equals(rotationPresetManager6.cycle)) {
         if (l >= timestamp) {
            intValue3 = ThreadLocalRandom.current().nextInt(intValue3);
            timestamp = l + longValue3;
         }

         return resolve5(livingEntity, box, rotationPresetManager6.items.get(Math.min(intValue3, intValue3 - 1)));
      } else {
         Vec3d vec3d15 = a_.player.getEyePos();
         Vec3d vec3d16 = a_.player.getRotationVec(1.0F).normalize();
         Vec3d vec3d17 = null;
         double doubleValue10 = Double.MAX_VALUE;

         for (RotationPresetManager.RotationPresetManagerState rotationPresetManagerState : rotationPresetManager6.items) {
            Vec3d vec3d18 = resolve5(livingEntity, box, rotationPresetManagerState);
            Vec3d vec3d19 = vec3d18.subtract(vec3d15).normalize();
            double doubleValue11 = Math.acos(MathHelper.clamp(vec3d16.dotProduct(vec3d19), -1.0, 1.0));
            if (doubleValue11 < doubleValue10) {
               doubleValue10 = doubleValue11;
               vec3d17 = vec3d18;
            }
         }

         return vec3d17 != null ? vec3d17 : box.getCenter();
      }
   }

   private static Vec3d resolve5(LivingEntity livingEntity, Box box, RotationPresetManager.RotationPresetManagerState rotationPresetManagerState2) {
      Vec3d vec3d20 = resolve6(livingEntity);
      double doubleValue12 = (box.minX + box.maxX) * 0.5;
      double doubleValue13 = (box.minZ + box.maxZ) * 0.5;
      double doubleValue14 = box.maxX - box.minX;
      double doubleValue15 = box.maxY - box.minY;
      return new Vec3d(doubleValue12 + vec3d20.x * (rotationPresetManagerState2.floatValue * doubleValue14), box.minY + rotationPresetManagerState2.floatValue2 * doubleValue15, doubleValue13 + vec3d20.z * (rotationPresetManagerState2.floatValue * doubleValue14));
   }

   private static Vec3d resolve6(LivingEntity livingEntity) {
      Vec3d vec3d21 = livingEntity.getPos().subtract(a_.player.getPos());
      double doubleValue16 = Math.hypot(vec3d21.x, vec3d21.z);
      return doubleValue16 < 1.0E-4 ? new Vec3d(1.0, 0.0, 0.0) : new Vec3d(-vec3d21.z / doubleValue16, 0.0, vec3d21.x / doubleValue16);
   }

   private static boolean check2(LivingEntity livingEntity) {
      float[] floatValues7 = AttackAura.resolve2(livingEntity);
      float[] floatValues8 = new float[]{floatValues7[0], floatValues7[1], floatValues7[0] + floatValues7[1]};
      boolean flag5 = AttackUtils.check15(livingEntity, false, true, true, -50L, floatValues8);
      if (flag5 && RaycastUtils.measure((Entity)livingEntity) < AttackAura.measure(livingEntity)) {
         intValue = 2;
      }

      if (intValue <= 0) {
         return false;
      } else {
         intValue--;
         return true;
      }
   }

   private static float[] resolve7(RotationPresetManager rotationPresetManager7, boolean bl) {
      float floatValue13 = bl ? rotationPresetManager7.floatValue5 : measure(rotationPresetManager7.floatValue, rotationPresetManager7.floatValue2);
      float floatValue14 = bl ? rotationPresetManager7.floatValue6 : measure(rotationPresetManager7.floatValue3, rotationPresetManager7.floatValue4);
      if ("Static".equals(rotationPresetManager7.smooth)) {
         floatValue14 *= 0.3F;
      }

      return new float[]{floatValue13, floatValue14};
   }

   private static float measure(float f, float g) {
      if (g < f) {
         float floatValue15 = f;
         f = g;
         g = floatValue15;
      }

      return g == f ? f : f + ThreadLocalRandom.current().nextFloat() * (g - f);
   }
}
