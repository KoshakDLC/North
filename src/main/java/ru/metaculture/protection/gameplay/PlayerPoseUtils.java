package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;
import lombok.Generated;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EntityPose;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;

public final class PlayerPoseUtils implements MinecraftAccessor {
   public static double measure(double d, double e, double f) {
      return Math.min(f, Math.max(d, e));
   }

   public static int compute(int i, int j, float f) {
      return i + (int)(f * (j - i));
   }

   public static double measure2(double d, double e, double f) {
      return d + f * (e - d);
   }

   public static HitResult resolve(Vec3d vec3d, Vec3d vec3d2, ShapeType shapeType, FluidHandling fluidHandling) {
      return a_.world.raycast(new RaycastContext(vec3d, vec3d2, shapeType, fluidHandling, a_.player));
   }

   private static double measure3(ClientPlayerEntity clientPlayerEntity, double d, double e) {
      double doubleValue = clientPlayerEntity.getX() - d;
      double doubleValue2 = clientPlayerEntity.getZ() - e;
      return MathHelper.sqrt((float)(doubleValue * doubleValue + doubleValue2 * doubleValue2));
   }

   private static boolean check(ClientPlayerEntity clientPlayerEntity, double d, double e, double f) {
      Vec3d vec3d3 = new Vec3d(d, e, f);
      return a_.world != null && resolve(clientPlayerEntity.getEyePos(), vec3d3, ShapeType.COLLIDER, FluidHandling.NONE).getType() != Type.BLOCK;
   }

   private static boolean check2(ClientPlayerEntity clientPlayerEntity, Vec3d vec3d) {
      Vec3d vec3d4 = new Vec3d(clientPlayerEntity.getX(), clientPlayerEntity.getEyeY(), clientPlayerEntity.getZ());
      return a_.world != null && resolve(vec3d4, vec3d, ShapeType.COLLIDER, FluidHandling.NONE).getType() != Type.BLOCK;
   }

   private static boolean check3(ClientPlayerEntity clientPlayerEntity, Vec3d vec3d, float f) {
      return f == 0.0F
         ? check(clientPlayerEntity, vec3d.x, vec3d.y, vec3d.z)
         : check(clientPlayerEntity, vec3d.x, vec3d.y, vec3d.z)
            && check(clientPlayerEntity, vec3d.x, vec3d.y + f, vec3d.z)
            && check(clientPlayerEntity, vec3d.x, vec3d.y - f, vec3d.z)
            && check(clientPlayerEntity, vec3d.x + f, vec3d.y, vec3d.z)
            && check(clientPlayerEntity, vec3d.x - f, vec3d.y, vec3d.z)
            && check(clientPlayerEntity, vec3d.x, vec3d.y, vec3d.z + f)
            && check(clientPlayerEntity, vec3d.x, vec3d.y, vec3d.z - f);
   }

   public static List<Vec3d> resolve2(Box box) {
      ArrayList arrayList = new ArrayList();
      double doubleValue3 = 0.01F;
      byte byteValue = 17;
      byte byteValue2 = 5;
      byte byteValue3 = 24;
      byte byteValue4 = 6;
      box = box.offset(-doubleValue3, -doubleValue3, -doubleValue3);
      double[] doubleValues = new double[]{box.maxX - box.minX, box.maxY - box.minY, (box.maxY - box.minY) / 1.05};
      double[] doubleValues2 = new double[]{box.minX + doubleValues[0] / 2.0, box.minY, box.minZ + doubleValues[0] / 2.0};
      double[] doubleValues3 = new double[]{box.minX, box.minY, box.minZ};
      double[] doubleValues4 = new double[]{box.maxX, box.maxY, box.maxZ};
      float floatValue = (float)Math.sqrt(doubleValues[0] * doubleValues[0] + doubleValues[0] * doubleValues[0] + doubleValues[0] * doubleValues[0]) / 2.0F;
      ClientPlayerEntity clientPlayerEntity2 = a_.player;
      if (clientPlayerEntity2 == null) {
         return null;
      } else {
         float floatValue2 = (float)(
            (1.0 - Math.min(clientPlayerEntity2.getPos().distanceTo(new Vec3d(doubleValues2[0], doubleValues2[1], doubleValues2[2])) / 5.0, 1.0))
               * Math.min(clientPlayerEntity2.getPos().distanceTo(new Vec3d(doubleValues2[0], clientPlayerEntity2.getY(), doubleValues2[2])) / 0.6F, 1.0)
         );
         int intValue = compute(byteValue2, byteValue, floatValue2);
         int intValue2 = compute(byteValue4, byteValue3, floatValue2);
         float floatValue3 = 0.0F;
         int[] intValues = IntStream.range(0, intValue).toArray();
         int intValue3 = intValues.length;

         for (int intValue4 = 0; intValue4 < intValue3; intValue4++) {
            Integer integerValue = intValues[intValue4];
            boolean flag = integerValue == 0 || integerValue == intValue - 1;
            double doubleValue4 = measure2(doubleValues3[0], doubleValues4[0], (float)integerValue.intValue() / (intValue - 1));
            int[] intValues2 = IntStream.range(0, intValue).toArray();
            int intValue5 = intValues2.length;

            for (int intValue6 = 0; intValue6 < intValue5; intValue6++) {
               Integer integerValue2 = intValues2[intValue6];
               boolean flag2 = integerValue2 == 0 || integerValue2 == intValue - 1;
               double doubleValue5 = measure2(doubleValues3[2], doubleValues4[2], (float)integerValue2.intValue() / (intValue - 1));
               int[] intValues3 = IntStream.range(0, intValue2).toArray();
               int intValue7 = intValues3.length;

               for (int intValue8 = 0; intValue8 < intValue7; intValue8++) {
                  Integer integerValue3 = intValues3[intValue8];
                  boolean flag3 = integerValue3 == 0 || integerValue3 == intValue2 - 1;
                  double doubleValue6 = measure2(doubleValues3[1], doubleValues4[1], (float)integerValue3.intValue() / (intValue2 - 1));
                  Vec3d vec3d5 = new Vec3d(doubleValue4, doubleValue6, doubleValue5);
                  if ((flag || flag2 || flag3)
                     && !(clientPlayerEntity2.getPos().distanceTo(vec3d5.add(0.0, -clientPlayerEntity2.getEyeHeight(EntityPose.STANDING), 0.0)) < floatValue)
                     && check3(clientPlayerEntity2, vec3d5, floatValue3)
                     && !arrayList.add(vec3d5)) {
                     break;
                  }
               }
            }
         }

         return arrayList;
      }
   }

   private static double measure4(Vec3d vec3d, Vec3d vec3d2) {
      double doubleValue7;
      double doubleValue8;
      double doubleValue9;
      return Math.sqrt((doubleValue7 = vec3d.x - vec3d2.x) * doubleValue7 + (doubleValue8 = vec3d.y - vec3d2.y) * doubleValue8 + (doubleValue9 = vec3d.z - vec3d2.z) * doubleValue9);
   }

   public static Vec3d resolve3(Box box) {
      return resolve4(box, true);
   }

   public static Vec3d resolve4(Box box, boolean bl) {
      if (box == null) {
         return a_.player.getEyePos();
      } else {
         double[] doubleValues5 = new double[]{box.maxX - box.minX, box.maxY - box.minY, (box.maxY - box.minY) / 1.1F};
         double[] doubleValues6 = new double[]{box.minX + doubleValues5[0] / 2.0, box.minY, box.minZ + doubleValues5[0] / 2.0};
         double[] doubleValues7 = new double[]{a_.player.getY() - doubleValues6[1], measure3(a_.player, doubleValues6[0], doubleValues6[2])};
         double doubleValue10 = measure(LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9.ease((doubleValues7[1] - doubleValues5[0] / 2.0) / (5.0 + doubleValues5[0] / 2.0)), 0.1, 0.95);
         double doubleValue11 = measure(doubleValue10 * doubleValue10, 0.0, 1.0);
         double doubleValue12 = measure(doubleValues5[2] / 2.0 * doubleValue11 + doubleValues5[2] / 2.0 * measure(doubleValues7[0] + doubleValue11, 0.0, 1.0), 0.0, doubleValues5[2]);
         Vec3d vec3d6 = new Vec3d(doubleValues6[0], doubleValues6[1] + doubleValue12, doubleValues6[2]);
         if (!bl && !check2(a_.player, vec3d6)) {
            vec3d6 = vec3d6.add(0.0, -doubleValue12 / 2.0, 0.0);
         }

         if (!(doubleValues5[1] <= 1.0) && (bl || !check2(a_.player, vec3d6))) {
            List items = resolve2(box);
            float floatValue4 = 1.0F - (float)Math.max(Math.min((doubleValues7[1] - 2.0) / 3.0, 1.0), 0.0);
            Vec3d vec3d7 = new Vec3d(a_.player.getX(), a_.player.getY() + 0.6F + measure2(doubleValue12, doubleValue12 / 2.5, floatValue4), a_.player.getZ());
            if (items != null && items.size() > 1) {
               items.sort(Comparator.comparing(vec3d2 -> measure4(vec3d7, (Vec3d)vec3d2)));
            }

            return items != null && !items.isEmpty() ? (Vec3d)items.get(0) : vec3d6;
         } else {
            return vec3d6;
         }
      }
   }

   @Generated
   private PlayerPoseUtils() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }
}
