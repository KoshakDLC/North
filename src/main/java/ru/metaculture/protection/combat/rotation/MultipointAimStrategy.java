package ru.metaculture.protection;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class MultipointAimStrategy implements MinecraftAccessor {
   private static final int INT_VALUE = 15;
   private static final int INT_VALUE_2 = 6;
   private static final float FLOAT_VALUE = 0.28F;
   private static final float FLOAT_VALUE_2 = 0.4F;
   private static final float FLOAT_VALUE_3 = 0.24F;
   private static final float FLOAT_VALUE_4 = 20.0F;
   private static final float FLOAT_VALUE_5 = 42.0F;
   private static final float FLOAT_VALUE_6 = 5.0F;
   private static final float FLOAT_VALUE_7 = 10.0F;
   private static final float FLOAT_VALUE_8 = 1.6F;
   private static final float FLOAT_VALUE_9 = 0.95F;
   private static final float FLOAT_VALUE_10 = 0.85F;
   private static final float FLOAT_VALUE_11 = 0.65F;
   private static final float FLOAT_VALUE_12 = 65.0F;
   private static final float FLOAT_VALUE_13 = 0.34F;
   private static final float FLOAT_VALUE_14 = 0.7F;
   private static int intValue = Integer.MIN_VALUE;
   private static final float[] FLOATS = new float[6];
   private static final float[] FLOATS_2 = new float[6];
   private static final float[] FLOATS_3 = new float[6];
   private static int intValue2;
   private static int intValue3;
   private static float floatValue = 0.5F;
   private static float floatValue2 = 0.55F;
   private static float floatValue3 = 0.5F;
   private static float floatValue4 = 0.5F;
   private static float floatValue5 = 0.55F;
   private static float floatValue6 = 0.5F;
   private static float floatValue7 = 1.2F;
   private static long timestamp;
   private static float floatValue8 = 12.400001F;
   private static float floatValue9 = 4.5F;
   private static float floatValue10 = floatValue8;
   private static float floatValue11 = floatValue9;

   public static void invoke(LivingEntity livingEntity) {
      if (a_.player == null) {
         invoke8();
      } else if (a_.world != null && livingEntity != null) {
         long longValue = System.currentTimeMillis();
         Box box2 = livingEntity.getBoundingBox();
         Vec3d vec3d2 = a_.player.getEyePos();
         boolean flag = check(vec3d2, box2);
         if (intValue != livingEntity.getId()) {
            intValue = livingEntity.getId();
            invoke6();
            invoke2(box2, vec3d2, longValue, flag);
            floatValue = floatValue4;
            floatValue2 = floatValue5;
            floatValue3 = floatValue6;
            floatValue8 = floatValue10;
            floatValue9 = floatValue11;
         }

         if (longValue >= timestamp || measure() < 0.05F) {
            invoke2(box2, vec3d2, longValue, flag);
         }

         floatValue8 = floatValue8 + (floatValue10 - floatValue8) * 0.65F;
         floatValue9 = floatValue9 + (floatValue11 - floatValue9) * 0.65F;
         float floatValue = flag ? 1.36F : 1.6F;
         floatValue = floatValue + (floatValue4 - floatValue) * floatValue7;
         floatValue2 = floatValue2 + (floatValue5 - floatValue2) * (floatValue7 * floatValue);
         floatValue3 = floatValue3 + (floatValue6 - floatValue3) * floatValue7;
         Vec3d vec3d3 = resolve(box2);
         Vec3d vec3d4 = vec3d3.subtract(vec3d2);
         float floatValue2 = (float)Math.toDegrees(Math.atan2(-vec3d4.x, vec3d4.z));
         float floatValue3 = (float)MathHelper.clamp(-Math.toDegrees(Math.atan2(vec3d4.y, Math.hypot(vec3d4.x, vec3d4.z))), -89.0, 89.0);
         RotationController.invoke3(new Rotation(floatValue2, floatValue3), floatValue8, floatValue9, 30.0F, 30.0F, 1, 15, false);
      } else {
         invoke7();
      }
   }

   private static boolean check(Vec3d vec3d, Box box) {
      double doubleValue = vec3d.x - MathHelper.clamp(vec3d.x, box.minX, box.maxX);
      double doubleValue2 = vec3d.z - MathHelper.clamp(vec3d.z, box.minZ, box.maxZ);
      return Math.sqrt(doubleValue * doubleValue + doubleValue2 * doubleValue2) < 0.95F;
   }

   private static float measure() {
      float deltaX = floatValue4 - floatValue;
      float deltaY = floatValue5 - floatValue2;
      float deltaZ = floatValue6 - floatValue3;
      return (float)Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
   }

   private static Vec3d resolve(Box box) {
      double doubleValue3 = box.minX + (box.maxX - box.minX) * floatValue;
      double doubleValue4 = box.minY + (box.maxY - box.minY) * floatValue2;
      double doubleValue5 = box.minZ + (box.maxZ - box.minZ) * floatValue3;
      return new Vec3d(doubleValue3, doubleValue4, doubleValue5);
   }

   private static void invoke2(Box box, Vec3d vec3d, long l, boolean bl) {
      timestamp = l + compute(bl ? 360L : 280L, bl ? 760L : 640L);
      floatValue7 = (float)ThreadLocalRandom.current().nextDouble(bl ? 0.15 : 0.18, bl ? 0.28 : 0.36);
      float floatValue7 = bl ? 0.24F : 0.4F;
      invoke3(bl);
      List items = PlayerPoseUtils.resolve2(box);
      if (items != null && !items.isEmpty()) {
         double doubleValue6 = box.maxX - box.minX;
         double doubleValue7 = box.maxY - box.minY;
         double doubleValue8 = box.maxZ - box.minZ;
         if (bl) {
            float floatValue8 = a_.player.getYaw();
            float floatValue9 = a_.player.getPitch();
            float floatValue10 = 0.5F;
            float floatValue11 = 0.5F;
            float floatValue12 = 0.5F;
            float floatValue13 = Float.MAX_VALUE;
            boolean flag2 = false;

            for (int intValue = 0; intValue < 14; intValue++) {
               Vec3d vec3d5 = (Vec3d)items.get(ThreadLocalRandom.current().nextInt(items.size()));
               float floatValue14 = measure4(doubleValue6 <= 1.0E-4 ? 0.5F : (float)((vec3d5.x - box.minX) / doubleValue6), floatValue7);
               float floatValue15 = measure3(measure4(doubleValue7 <= 1.0E-4 ? 0.5F : (float)((vec3d5.y - box.minY) / doubleValue7), floatValue7));
               float floatValue16 = measure4(doubleValue8 <= 1.0E-4 ? 0.5F : (float)((vec3d5.z - box.minZ) / doubleValue8), floatValue7);
               float floatValue17 = measure2(box, vec3d, floatValue14, floatValue15, floatValue16, floatValue8, floatValue9);
               if (!check2(floatValue14, floatValue15, floatValue16) && floatValue17 < 65.0F) {
                  floatValue4 = floatValue14;
                  floatValue5 = floatValue15;
                  floatValue6 = floatValue16;
                  invoke5(floatValue14, floatValue15, floatValue16);
                  return;
               }

               if (floatValue17 < floatValue13) {
                  floatValue13 = floatValue17;
                  floatValue10 = floatValue14;
                  floatValue11 = floatValue15;
                  floatValue12 = floatValue16;
                  flag2 = true;
               }
            }

            if (flag2) {
               floatValue4 = floatValue10;
               floatValue5 = floatValue11;
               floatValue6 = floatValue12;
               invoke5(floatValue4, floatValue5, floatValue6);
               return;
            }
         } else {
            for (int intValue2 = 0; intValue2 < 12; intValue2++) {
               Vec3d vec3d6 = (Vec3d)items.get(ThreadLocalRandom.current().nextInt(items.size()));
               float floatValue18 = measure4(doubleValue6 <= 1.0E-4 ? 0.5F : (float)((vec3d6.x - box.minX) / doubleValue6), floatValue7);
               float floatValue19 = measure4(doubleValue7 <= 1.0E-4 ? 0.5F : (float)((vec3d6.y - box.minY) / doubleValue7), floatValue7);
               float floatValue20 = measure4(doubleValue8 <= 1.0E-4 ? 0.5F : (float)((vec3d6.z - box.minZ) / doubleValue8), floatValue7);
               if (!check2(floatValue18, floatValue19, floatValue20)) {
                  floatValue4 = floatValue18;
                  floatValue5 = floatValue19;
                  floatValue6 = floatValue20;
                  invoke5(floatValue18, floatValue19, floatValue20);
                  return;
               }
            }
         }

         invoke4(box, (Vec3d)items.get(ThreadLocalRandom.current().nextInt(items.size())), floatValue7, bl);
         invoke5(floatValue4, floatValue5, floatValue6);
      } else {
         invoke4(box, PlayerPoseUtils.resolve4(box, false), floatValue7, bl);
         invoke5(floatValue4, floatValue5, floatValue6);
      }
   }

   private static float measure2(Box box, Vec3d vec3d, float f, float g, float h, float i, float j) {
      double doubleValue9 = box.minX + (box.maxX - box.minX) * f;
      double doubleValue10 = box.minY + (box.maxY - box.minY) * g;
      double doubleValue11 = box.minZ + (box.maxZ - box.minZ) * h;
      double doubleValue12 = doubleValue9 - vec3d.x;
      double doubleValue13 = doubleValue10 - vec3d.y;
      double doubleValue14 = doubleValue11 - vec3d.z;
      float floatValue21 = (float)Math.toDegrees(Math.atan2(-doubleValue12, doubleValue14));
      float floatValue22 = (float)MathHelper.clamp(-Math.toDegrees(Math.atan2(doubleValue13, Math.hypot(doubleValue12, doubleValue14))), -89.0, 89.0);
      float floatValue23 = Math.abs(MathHelper.wrapDegrees(floatValue21 - i));
      float floatValue24 = Math.abs(floatValue22 - j);
      return floatValue23 * 1.25F + floatValue24 * 0.9F;
   }

   private static float measure3(float f) {
      return MathHelper.clamp(f, 0.34F, 0.7F);
   }

   private static void invoke3(boolean bl) {
      float floatValue25 = bl ? 0.85F : 1.0F;
      floatValue10 = (float)ThreadLocalRandom.current().nextDouble(20.0, 42.0) * floatValue25;
      floatValue11 = (float)ThreadLocalRandom.current().nextDouble(5.0, 10.0) * floatValue25;
   }

   private static float measure4(float f, float g) {
      double doubleValue15 = (ThreadLocalRandom.current().nextDouble() - ThreadLocalRandom.current().nextDouble()) * g;
      return MathHelper.clamp(f + (float)doubleValue15, 0.05F, 0.95F);
   }

   private static void invoke4(Box box, Vec3d vec3d, float f, boolean bl) {
      double doubleValue16 = box.maxX - box.minX;
      double doubleValue17 = box.maxY - box.minY;
      double doubleValue18 = box.maxZ - box.minZ;
      floatValue4 = measure4(doubleValue16 <= 1.0E-4 ? 0.5F : (float)MathHelper.clamp((vec3d.x - box.minX) / doubleValue16, 0.0, 1.0), f);
      float floatValue26 = measure4(doubleValue17 <= 1.0E-4 ? 0.5F : (float)MathHelper.clamp((vec3d.y - box.minY) / doubleValue17, 0.0, 1.0), f);
      floatValue5 = bl ? measure3(floatValue26) : floatValue26;
      floatValue6 = measure4(doubleValue18 <= 1.0E-4 ? 0.5F : (float)MathHelper.clamp((vec3d.z - box.minZ) / doubleValue18, 0.0, 1.0), f);
   }

   private static boolean check2(float f, float g, float h) {
      for (int intValue3 = 0; intValue3 < intValue2; intValue3++) {
         float floatValue27 = f - FLOATS[intValue3];
         float floatValue28 = g - FLOATS_2[intValue3];
         float floatValue29 = h - FLOATS_3[intValue3];
         if (floatValue27 * floatValue27 + floatValue28 * floatValue28 + floatValue29 * floatValue29 < 0.0784F) {
            return true;
         }
      }

      return false;
   }

   private static void invoke5(float f, float g, float h) {
      FLOATS[intValue3] = f;
      FLOATS_2[intValue3] = g;
      FLOATS_3[intValue3] = h;
      intValue3 = (intValue3 + 1) % 6;
      if (intValue2 < 6) {
         intValue2++;
      }
   }

   private static void invoke6() {
      intValue2 = 0;
      intValue3 = 0;
   }

   private static long compute(long l, long m) {
      return ThreadLocalRandom.current().nextLong(l, m);
   }

   public static void invoke7() {
      intValue = Integer.MIN_VALUE;
      invoke6();
   }

   private static void invoke8() {
      invoke7();
      floatValue8 = 31.0F;
      floatValue9 = 7.5F;
      floatValue10 = floatValue8;
      floatValue11 = floatValue9;
      timestamp = 0L;
   }
}
