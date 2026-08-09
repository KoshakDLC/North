package ru.metaculture.protection;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class HumanizedRotationPattern implements MinecraftAccessor {
   private static final int INT_VALUE = 15;
   private static final float FLOAT_VALUE = 20.0F;
   private static float floatValue = 50.0F;
   private static float floatValue2 = 70.0F;
   private static final float FLOAT_VALUE_2 = 120.0F;
   private static final String[] HUMAN_TRACK = new String[]{"Human Track", "Wave Drift", "Pulse Jerk", "Overstep", "Anchor Hold"};
   private static final String[] CLOSEST_BOX = new String[]{"Closest Box", "Upper Body", "Velocity Lead", "Side Sweep", "Sticky Point"};
   private static int intValue = -1;
   private static int intValue2 = -1;
   private static int intValue3 = -1;
   private static int intValue4;
   private static int intValue5;
   private static int intValue6;
   private static int intValue7;
   private static int intValue8;
   private static int intValue9 = 5;
   private static boolean flag;
   private static float floatValue3;
   private static float floatValue4;
   private static float floatValue5;
   private static float floatValue6;
   private static float floatValue7;
   private static float floatValue8;
   private static float floatValue9;
   private static float floatValue10;
   private static int intValue10 = 1;
   private static float floatValue11;
   private static float floatValue12;
   private static float floatValue13;
   private static float floatValue14;
   private static float floatValue15;
   private static int intValue11 = 1;

   public static void invoke(LivingEntity livingEntity) {
      if (a_.player == null) {
         invoke8();
      } else if (a_.world != null && livingEntity != null) {
         if (intValue != livingEntity.getId()) {
            invoke8();
            intValue = livingEntity.getId();
            invoke3();
            invoke4();
         }

         if (intValue2 < 0 || intValue4 >= intValue6) {
            invoke3();
         }

         if (intValue3 < 0 || intValue5 >= intValue7) {
            invoke4();
         }

         intValue4++;
         intValue5++;
         intValue8++;
         Vec3d vec3d = resolve(livingEntity).subtract(a_.player.getEyePos());
         float floatValue = (float)Math.toDegrees(Math.atan2(-vec3d.x, vec3d.z));
         float floatValue2 = (float)MathHelper.clamp(-Math.toDegrees(Math.atan2(vec3d.y, Math.hypot(vec3d.x, vec3d.z))), -90.0, 90.0);
         Rotation rotation = new Rotation(a_.player);
         float floatValue3 = MathHelper.wrapDegrees(floatValue - rotation.floatValue);
         float floatValue4 = floatValue2 - rotation.floatValue2;
         float floatValue5 = AttackAura.measure(livingEntity) + AttackAura.radiusObnaruzheniya.getValue();
         EntityHitResult entityHitResult = EntityRaycastUtils.resolve3(rotation.floatValue, rotation.floatValue2, floatValue5, livingEntity, false);
         boolean flag = entityHitResult != null && entityHitResult.getEntity() == livingEntity;
         float floatValue6 = intValue4 + intValue2 * 17.0F;
         float floatValue7;
         float floatValue8;
         switch (intValue2) {
            case 0:
               floatValue7 = measure3(20.0F, 50.0F, MathUtils.measure17(24.0F, 35.0F) * (flag ? 0.92F : 1.14F));
               floatValue8 = measure3(70.0F, 120.0F, MathUtils.measure17(76.0F, 96.0F) * (flag ? 0.96F : 1.1F));
               floatValue3 = floatValue3 * (flag ? 0.36F : 0.66F)
                  + floatValue7 * (flag ? 0.55F : 0.95F)
                  + measure4(floatValue6, 14.0F, 0.42F)
                  + MathUtils.measure17(-0.18F, 0.18F);
               floatValue4 = floatValue4 * (flag ? 0.4F : 0.7F) + floatValue8 * (flag ? 0.45F : 0.85F) + measure4(floatValue6, 17.0F, 0.24F);
               break;
            case 1:
               floatValue7 = measure3(20.0F, 50.0F, MathUtils.measure17(24.0F, 40.0F) * (flag ? 0.9F : 1.18F));
               floatValue8 = measure3(70.0F, 120.0F, MathUtils.measure17(90.0F, 118.0F) * (flag ? 0.9F : 1.0F));
               floatValue3 = floatValue3 * (flag ? 0.3F : 0.62F) + measure4(floatValue6, 3.5F, 3.1F) + measure4(floatValue6, 10.5F, 1.7F) + floatValue7;
               floatValue4 = floatValue4 * (flag ? 0.56F : 0.86F) + measure4(floatValue6, 4.8F, 2.4F) + floatValue8 * 0.7F;
               break;
            case 2:
               floatValue7 = measure3(20.0F, 50.0F, MathUtils.measure17(38.0F, 50.0F));
               floatValue8 = measure3(70.0F, 120.0F, MathUtils.measure17(72.0F, 96.0F));
               invoke5(0.42F, 0.66F, 5.6F, 1.6F);
               invoke7(4, 4.1F, 1.0F);
               floatValue3 = floatValue3 * (flag ? 0.28F : 0.84F) + floatValue3 + floatValue9;
               floatValue4 = floatValue4 * (flag ? 0.38F : 0.72F) + floatValue4 + floatValue10;
               break;
            case 3:
               floatValue7 = measure3(20.0F, 50.0F, MathUtils.measure17(30.0F, 46.0F) * (flag ? 0.92F : 1.12F));
               floatValue8 = measure3(70.0F, 120.0F, MathUtils.measure17(98.0F, 120.0F) * (flag ? 0.94F : 1.0F));
               floatValue3 = floatValue3 * (flag ? 0.54F : 0.88F) + measure6(floatValue3, 8.0F, 2.2F, 5.6F) - measure4(floatValue6, 6.5F, 1.1F);
               floatValue4 = floatValue4 * (flag ? 0.32F : 0.66F) + measure6(floatValue4, 5.0F, 1.0F, 2.9F) + measure4(floatValue6, 6.8F, 0.55F);
               break;
            case 4:
            default:
               floatValue7 = measure3(20.0F, 50.0F, flag ? MathUtils.measure17(20.0F, 29.0F) : MathUtils.measure17(34.0F, 49.0F));
               floatValue8 = measure3(70.0F, 120.0F, flag ? MathUtils.measure17(92.0F, 115.0F) : MathUtils.measure17(78.0F, 100.0F));
               invoke6(flag);
               floatValue3 = floatValue3 * (flag ? 0.2F : 0.78F) + floatValue5 + measure5(floatValue6, 18.0F, 1.15F);
               floatValue4 = floatValue4 * (flag ? 0.58F : 0.54F) + floatValue6 - measure5(floatValue6, 15.0F, 0.7F);
         }

         floatValue3 = measure2(floatValue3, floatValue11, flag, true, floatValue6);
         floatValue4 = measure2(floatValue4, floatValue12, flag, false, floatValue6);
         floatValue11 = floatValue3;
         floatValue12 = floatValue4;
         float floatValue9 = MathHelper.clamp(floatValue3, -floatValue7, floatValue7);
         float floatValue10 = MathHelper.clamp(floatValue4, -floatValue8, floatValue8);
         if (!flag) {
            floatValue9 = measure7(floatValue9, floatValue3, 2.2F);
            floatValue10 = measure7(floatValue10, floatValue4, 1.8F);
         }

         if (flag && Math.abs(floatValue9) < 0.18F) {
            floatValue9 = 0.0F;
         }

         if (flag && Math.abs(floatValue10) < 0.12F) {
            floatValue10 = 0.0F;
         }

         flag = RotationController.intValue <= 15;
         RotationController.invoke3(
            new Rotation(rotation.floatValue + floatValue9, MathHelper.clamp(rotation.floatValue2 + floatValue10, -90.0F, 90.0F)), floatValue7, floatValue8, 30.0F, 30.0F, 2, 15, false
         );
      } else {
         invoke2();
      }
   }

   public static void invoke2() {
      if (!flag) {
         invoke8();
      } else {
         if (a_.player != null) {
            floatValue = a_.player.getYaw();
            floatValue2 = a_.player.getPitch();
         }

         RotationController.rotationControllerState2 = RotationController.RotationControllerState2.IDLE;
         RotationController.intValue = 0;
         RotationController.flag = false;
         RotationController.rotation = null;
         RotationController.intValue3 = 0;
         FreeLookController.active = FreeLookController.flag;
         invoke8();
      }
   }

   private static void invoke3() {
      int intValue = intValue2;

      do {
         intValue2 = MathUtils.compute2(0, 4);
      } while (intValue2 == intValue && intValue >= 0);

      intValue4 = 0;

      intValue6 = switch (intValue2) {
         case 0 -> MathUtils.compute2(78, 128);
         case 1 -> MathUtils.compute2(62, 104);
         case 2 -> MathUtils.compute2(48, 78);
         case 3 -> MathUtils.compute2(56, 92);
         default -> MathUtils.compute2(74, 122);
      };
      intValue8 = 0;
      intValue9 = intValue2 == 4 ? MathUtils.compute2(6, 13) : MathUtils.compute2(2, 7);
      floatValue3 = 0.0F;
      floatValue4 = 0.0F;
      floatValue5 = MathUtils.measure17(-1.2F, 1.2F);
      floatValue6 = MathUtils.measure17(-0.75F, 0.75F);
      floatValue7 = MathUtils.measure17(-1.15F, 1.15F);
      floatValue8 = MathUtils.measure17(-0.65F, 0.65F);
      floatValue9 = 0.0F;
      floatValue10 = 0.0F;
      intValue10 = MathUtils.compute2(0, 1) == 0 ? -1 : 1;
      ChatUtil.sendClientMessage("[LonyGrief] rotate -> " + HUMAN_TRACK[intValue2]);
   }

   private static void invoke4() {
      int intValue2 = intValue3;

      do {
         intValue3 = MathUtils.compute2(0, 4);
      } while (intValue3 == intValue2 && intValue2 >= 0);

      intValue5 = 0;

      intValue7 = switch (intValue3) {
         case 0 -> MathUtils.compute2(90, 150);
         case 1 -> MathUtils.compute2(80, 136);
         case 2 -> MathUtils.compute2(58, 108);
         case 3 -> MathUtils.compute2(68, 118);
         default -> MathUtils.compute2(96, 168);
      };
      floatValue13 = MathUtils.measure17(0.34F, 0.66F);
      floatValue14 = MathUtils.measure17(0.38F, 0.78F);
      floatValue15 = MathUtils.measure17(0.34F, 0.66F);
      intValue11 = MathUtils.compute2(0, 1) == 0 ? -1 : 1;
      ChatUtil.sendClientMessage("[LonyGrief] vector -> " + CLOSEST_BOX[intValue3]);
   }

   private static void invoke5(float f, float g, float h, float i) {
      if (intValue8 >= intValue9) {
         floatValue3 = MathUtils.measure17(-h, h);
         floatValue4 = MathUtils.measure17(-i, i);
         intValue8 = 0;
         intValue9 = MathUtils.compute2(3, 8);
      } else {
         floatValue3 *= f;
         floatValue4 *= g;
      }
   }

   private static void invoke6(boolean bl) {
      if (intValue8 >= intValue9) {
         float floatValue11 = bl ? 2.4F : 3.8F;
         float floatValue12 = bl ? 1.6F : 2.6F;
         floatValue5 = MathUtils.measure17(-floatValue11, floatValue11);
         floatValue6 = MathUtils.measure17(-floatValue12, floatValue12);
         intValue8 = 0;
         intValue9 = MathUtils.compute2(5, 11);
      }
   }

   private static void invoke7(int i, float f, float g) {
      if (intValue4 % i == 0) {
         intValue10 = -intValue10;
         floatValue9 = f * intValue10;
         floatValue10 = MathUtils.measure17(-g, g);
      } else {
         floatValue9 *= 0.5F;
         floatValue10 *= 0.64F;
      }
   }

   private static Vec3d resolve(LivingEntity livingEntity) {
      Box box2 = livingEntity.getBoundingBox();
      float floatValue13 = intValue5 + intValue3 * 13.0F;

      return switch (intValue3) {
         case 0 -> PlayerPoseUtils.resolve4(box2, false).add(measure4(floatValue13, 18.0F, 0.025F), measure4(floatValue13, 21.0F, 0.035F), measure4(floatValue13, 20.0F, 0.025F));
         case 1 -> resolve3(box2, 0.5F + measure4(floatValue13, 24.0F, 0.13F), 0.72F + measure4(floatValue13, 31.0F, 0.08F), 0.5F + measure4(floatValue13, 27.0F, 0.13F));
         case 2 -> resolve3(box2, 0.5F + measure4(floatValue13, 30.0F, 0.08F), 0.52F + measure4(floatValue13, 25.0F, 0.1F), 0.5F + measure4(floatValue13, 34.0F, 0.08F))
            .add(livingEntity.getVelocity().multiply(MathUtils.measure17(1.1F, 2.4F)));
         case 3 -> resolve2(livingEntity, box2, floatValue13);
         default -> resolve3(
            box2,
            floatValue13 + measure4(floatValue13, 36.0F, 0.035F),
            floatValue14 + measure4(floatValue13, 29.0F, 0.045F),
            floatValue15 + measure4(floatValue13, 33.0F, 0.035F)
         );
      };
   }

   private static Vec3d resolve2(LivingEntity livingEntity, Box box, float f) {
      Vec3d vec3d2 = resolve3(box, 0.5F, 0.55F + measure4(f, 28.0F, 0.12F), 0.5F);
      Vec3d vec3d3 = a_.player.getPos().subtract(livingEntity.getPos());
      Vec3d vec3d4 = new Vec3d(-vec3d3.z, 0.0, vec3d3.x);
      if (vec3d4.lengthSquared() < 1.0E-4) {
         vec3d4 = new Vec3d(1.0, 0.0, 0.0);
      } else {
         vec3d4 = vec3d4.normalize();
      }

      double doubleValue = Math.max(livingEntity.getWidth() * 0.38, 0.12);
      double doubleValue2 = measure5(f, 42.0F, 1.0F) * doubleValue * intValue11;
      return vec3d2.add(vec3d4.multiply(doubleValue2));
   }

   private static Vec3d resolve3(Box box, float f, float g, float h) {
      float floatValue14 = MathHelper.clamp(f, 0.08F, 0.92F);
      float floatValue15 = MathHelper.clamp(g, 0.12F, 0.92F);
      float floatValue16 = MathHelper.clamp(h, 0.08F, 0.92F);
      return new Vec3d(measure(box.minX, box.maxX, floatValue14), measure(box.minY, box.maxY, floatValue15), measure(box.minZ, box.maxZ, floatValue16));
   }

   private static double measure(double d, double e, float f) {
      return d + (e - d) * f;
   }

   private static float measure2(float f, float g, boolean bl, boolean bl2, float h) {
      float floatValue17 = Math.abs(f);
      float floatValue18 = bl2 ? 55.0F : 42.0F;
      float floatValue19 = MathHelper.clamp((float)Math.pow(MathHelper.clamp(floatValue17 / floatValue18, 0.0F, 1.0F), 0.72), 0.22F, 1.0F);
      float floatValue20 = f * floatValue19;
      float floatValue21 = bl2 ? (bl ? 0.46F : 0.68F) : (bl ? 0.52F : 0.72F);
      float floatValue22 = bl2
         ? measure4(h, 19.0F, bl ? 0.22F : 0.48F) + MathUtils.measure17(-0.08F, 0.08F)
         : measure4(h, 23.0F, bl ? 0.16F : 0.34F) + MathUtils.measure17(-0.05F, 0.05F);
      return MathUtils.measure10(g, floatValue20, floatValue21) + floatValue22;
   }

   private static float measure3(float f, float g, float h) {
      return MathHelper.clamp(h, f, g);
   }

   private static float measure4(float f, float g, float h) {
      return (float)Math.sin(f / g) * h;
   }

   private static float measure5(float f, float g, float h) {
      float floatValue23 = f % g;
      return (floatValue23 / g * 2.0F - 1.0F) * h;
   }

   private static float measure6(float f, float g, float h, float i) {
      return Math.abs(f) <= g ? 0.0F : measure8(f) * MathUtils.measure17(h, i);
   }

   private static float measure7(float f, float g, float h) {
      return !(Math.abs(g) <= h) && !(Math.abs(f) >= h) ? measure8(g) * h : f;
   }

   private static float measure8(float f) {
      return f < 0.0F ? -1.0F : 1.0F;
   }

   private static void invoke8() {
      intValue = -1;
      intValue2 = -1;
      intValue3 = -1;
      intValue4 = 0;
      intValue5 = 0;
      intValue6 = 0;
      intValue7 = 0;
      intValue8 = 0;
      intValue9 = 5;
      floatValue3 = 0.0F;
      floatValue4 = 0.0F;
      floatValue5 = 0.0F;
      floatValue6 = 0.0F;
      floatValue7 = 0.0F;
      floatValue8 = 0.0F;
      floatValue9 = 0.0F;
      floatValue10 = 0.0F;
      intValue10 = 1;
      floatValue11 = 0.0F;
      floatValue12 = 0.0F;
      floatValue13 = 0.5F;
      floatValue14 = 0.55F;
      floatValue15 = 0.5F;
      intValue11 = 1;
      flag = false;
   }
}
