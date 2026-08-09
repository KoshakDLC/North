package ru.metaculture.protection;

import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos.Mutable;

public final class CriticalAttackReadiness {
   private static final float FLOAT_VALUE = 0.9001F;
   private static final float FLOAT_VALUE_2 = 1.0E-6F;
   private static final double DOUBLE_VALUE = 0.015;
   private static final long TIMESTAMP = 260L;
   private static final long TIMESTAMP_2 = 70L;
   private static volatile boolean flag = true;
   private static volatile boolean flag2 = false;
   private static volatile boolean flag3 = false;
   private static volatile boolean flag4 = false;
   private static volatile long timestamp = 0L;
   private static volatile long timestamp2 = 0L;
   private static volatile long timestamp3 = 0L;
   private static volatile long timestamp4 = 0L;
   private static volatile long timestamp5 = 0L;
   private static volatile AttackReadiness attackReadiness = AttackReadiness.UNAVAILABLE;

   private CriticalAttackReadiness() {
   }

   public static void invoke() {
      flag = true;
      invoke5();
   }

   public static void invoke2() {
      invoke3(resolve() == AttackReadiness.READY);
   }

   public static void invoke3(boolean bl) {
      long longValue = System.currentTimeMillis();
      if (bl) {
         attackReadiness = AttackReadiness.READY;
         timestamp5 = longValue;
         invoke4();
      } else {
         attackReadiness = AttackReadiness.UNAVAILABLE;
         timestamp4 = longValue;
      }
   }

   public static boolean check() {
      return check6() && resolve() == AttackReadiness.WAITING;
   }

   public static AttackReadiness resolve() {
      MinecraftClient client = MinecraftClient.getInstance();
      if (client.player != null && client.world != null) {
         ClientPlayerEntity clientPlayerEntity2 = client.player;
         if (check16(clientPlayerEntity2, client)) {
            invoke4();
            return AttackReadiness.UNAVAILABLE;
         } else if (!check7(clientPlayerEntity2, client)) {
            return AttackReadiness.UNAVAILABLE;
         } else {
            boolean flag = AttackPacketController.isFlag2();
            boolean flag2 = clientPlayerEntity2.isOnGround();
            float floatValue = AttackPacketController.getFloatValue();
            double doubleValue = AttackPacketController.getDoubleValue4();
            boolean flag3 = !flag && (floatValue > 1.0E-6F || doubleValue < -1.0E-6 || AttackPacketController.isFlag5());
            if (flag3) {
               if (check17(clientPlayerEntity2)) {
                  return AttackReadiness.WAITING;
               } else {
                  return check15(clientPlayerEntity2) ? AttackReadiness.READY : AttackReadiness.WAITING;
               }
            } else if (!flag || !flag2) {
               return AttackReadiness.WAITING;
            } else if (!check18(clientPlayerEntity2, client)) {
               invoke6();
               return AttackReadiness.UNAVAILABLE;
            } else if (check14()) {
               invoke6();
               return AttackReadiness.UNAVAILABLE;
            } else {
               return AttackReadiness.WAITING;
            }
         }
      } else {
         return AttackReadiness.UNAVAILABLE;
      }
   }

   public static boolean check2() {
      return resolve() == AttackReadiness.READY;
   }

   public static boolean check3() {
      return !check4();
   }

   public static boolean check4() {
      return check5();
   }

   public static boolean check5() {
      return !check6() ? false : resolve() == AttackReadiness.WAITING;
   }

   public static boolean check6() {
      MinecraftClient client2 = MinecraftClient.getInstance();
      if (client2.player != null && client2.world != null) {
         return check7(client2.player, client2);
      } else {
         invoke4();
         return false;
      }
   }

   private static boolean check7(ClientPlayerEntity clientPlayerEntity, MinecraftClient minecraftClient) {
      if (!flag) {
         invoke5();
         return false;
      } else if (check16(clientPlayerEntity, minecraftClient)) {
         invoke4();
         return false;
      } else {
         long longValue2 = System.currentTimeMillis();
         boolean flag4 = minecraftClient.options.jumpKey.isPressed();
         boolean flag5 = clientPlayerEntity.isOnGround();
         boolean flag6 = AttackPacketController.isFlag2();
         boolean flag7 = !flag5 || !flag6;
         if (!flag4) {
            flag4 = false;
         }

         if (flag4) {
            return false;
         } else if (flag4) {
            if (!flag2) {
               flag2 = true;
               flag3 = false;
               timestamp = longValue2;
               timestamp2 = longValue2;
               timestamp3 = 0L;
            }

            return true;
         } else if (flag2 && flag7) {
            flag3 = true;
            timestamp2 = longValue2;
            timestamp3 = longValue2 + 70L;
            return true;
         } else if (flag2 && flag5 && flag6) {
            if (!flag3) {
               if (longValue2 - timestamp <= 260L) {
                  return true;
               } else {
                  invoke6();
                  return false;
               }
            } else if (longValue2 <= timestamp3) {
               return true;
            } else {
               invoke4();
               return false;
            }
         } else if (flag2 && longValue2 - timestamp2 <= 260L) {
            return true;
         } else {
            invoke4();
            return false;
         }
      }
   }

   public static boolean check8() {
      MinecraftClient client3 = MinecraftClient.getInstance();
      if (client3.player != null && client3.world != null) {
         ClientPlayerEntity clientPlayerEntity3 = client3.player;
         if (!check7(clientPlayerEntity3, client3)) {
            return false;
         } else if (check16(clientPlayerEntity3, client3)) {
            return false;
         } else {
            AttackReadiness attackReadiness = resolve();
            return attackReadiness == AttackReadiness.UNAVAILABLE ? false : clientPlayerEntity3.isSprinting() || AttackPacketController.isFlag3();
         }
      } else {
         return false;
      }
   }

   public static boolean check9() {
      return check8();
   }

   public static boolean check10() {
      return false;
   }

   public static boolean check11() {
      MinecraftClient client4 = MinecraftClient.getInstance();
      if (client4.player != null && client4.world != null) {
         ClientPlayerEntity clientPlayerEntity4 = client4.player;
         if (!flag) {
            return false;
         } else if (check16(clientPlayerEntity4, client4)) {
            return false;
         } else {
            return check17(clientPlayerEntity4) ? false : client4.options.jumpKey.isPressed() && AttackPacketController.isFlag2() && clientPlayerEntity4.isOnGround() && check18(clientPlayerEntity4, client4);
         }
      } else {
         return false;
      }
   }

   public static boolean check12() {
      return check6() && resolve() == AttackReadiness.WAITING && !AttackPacketController.isFlag2();
   }

   public static boolean check13() {
      MinecraftClient client5 = MinecraftClient.getInstance();
      return client5.player != null && client5.world != null ? !client5.player.isOnGround() || !AttackPacketController.isFlag2() : false;
   }

   public static void setFlag(boolean bl) {
      flag = bl;
      if (!bl) {
         invoke5();
      }
   }

   public static boolean isFlag() {
      return flag;
   }

   public static long getTimestamp4() {
      return timestamp4;
   }

   public static long getTimestamp5() {
      return timestamp5;
   }

   public static AttackReadiness getAttackReadiness() {
      return attackReadiness;
   }

   public static float measure() {
      MinecraftClient client6 = MinecraftClient.getInstance();
      if (client6.player != null && client6.world != null) {
         ClientPlayerEntity clientPlayerEntity5 = client6.player;
         float floatValue2 = measure2(clientPlayerEntity5, 0.0F);
         float floatValue3 = measure4(clientPlayerEntity5);
         float floatValue4 = measure3();
         return floatValue2 >= floatValue4 ? 0.0F : Math.max(0.0F, (floatValue4 - floatValue2) * floatValue3);
      } else {
         return Float.POSITIVE_INFINITY;
      }
   }

   public static void invoke4() {
      flag2 = false;
      flag3 = false;
      timestamp = 0L;
      timestamp2 = 0L;
      timestamp3 = 0L;
   }

   public static void invoke5() {
      invoke4();
      flag4 = false;
   }

   private static void invoke6() {
      invoke4();
      flag4 = true;
   }

   private static boolean check14() {
      if (!flag2) {
         return true;
      } else {
         return flag3 ? false : System.currentTimeMillis() - timestamp > 260L;
      }
   }

   private static boolean check15(ClientPlayerEntity clientPlayerEntity) {
      return measure2(clientPlayerEntity, 0.0F) >= measure3();
   }

   private static float measure2(ClientPlayerEntity clientPlayerEntity, float f) {
      float floatValue5 = Math.max(0.0F, f);
      double doubleValue2 = TpsTracker.getDoubleValue();
      if (doubleValue2 > 0.0 && doubleValue2 < 19.95) {
         floatValue5 *= (float)(doubleValue2 / 20.0);
      }

      return clientPlayerEntity.getAttackCooldownProgress(0.5F + floatValue5);
   }

   private static float measure3() {
      double doubleValue3 = TpsTracker.getDoubleValue();
      return !(doubleValue3 <= 0.0) && !(doubleValue3 >= 19.95) ? MathHelper.clamp(0.9001F * (20.0F / (float)doubleValue3), 0.9001F, 0.995F) : 0.9001F;
   }

   private static float measure4(ClientPlayerEntity clientPlayerEntity) {
      double doubleValue4 = clientPlayerEntity.getAttributeValue(EntityAttributes.ATTACK_SPEED);
      return !(doubleValue4 <= 0.0) && !Double.isNaN(doubleValue4) && !Double.isInfinite(doubleValue4) ? (float)(20.0 / doubleValue4) : 20.0F;
   }

   private static boolean check16(ClientPlayerEntity clientPlayerEntity, MinecraftClient minecraftClient) {
      if (clientPlayerEntity.isSpectator()) {
         return true;
      } else if (clientPlayerEntity.isTouchingWater()) {
         return true;
      } else if (clientPlayerEntity.isInLava()) {
         return true;
      } else if (clientPlayerEntity.isSubmergedIn(FluidTags.WATER)) {
         return true;
      } else if (clientPlayerEntity.isSubmergedIn(FluidTags.LAVA)) {
         return true;
      } else if (clientPlayerEntity.isSwimming()) {
         return true;
      } else if (clientPlayerEntity.isClimbing()) {
         return true;
      } else if (check19(clientPlayerEntity, minecraftClient)) {
         return true;
      } else if (clientPlayerEntity.hasStatusEffect(StatusEffects.BLINDNESS)) {
         return true;
      } else if (clientPlayerEntity.hasStatusEffect(StatusEffects.SLOW_FALLING)) {
         return true;
      } else if (clientPlayerEntity.hasStatusEffect(StatusEffects.LEVITATION)) {
         return true;
      } else if (clientPlayerEntity.hasVehicle()) {
         return true;
      } else {
         return clientPlayerEntity.getAbilities().flying ? true : clientPlayerEntity.isGliding();
      }
   }

   private static boolean check17(ClientPlayerEntity clientPlayerEntity) {
      return clientPlayerEntity.isSprinting() || AttackPacketController.isFlag3();
   }

   private static boolean check18(ClientPlayerEntity clientPlayerEntity, MinecraftClient minecraftClient) {
      if (minecraftClient.world == null) {
         return false;
      } else {
         Box box = clientPlayerEntity.getBoundingBox().offset(0.0, 0.015, 0.0).contract(1.0E-7);
         return minecraftClient.world.isSpaceEmpty(clientPlayerEntity, box);
      }
   }

   private static boolean check19(ClientPlayerEntity clientPlayerEntity, MinecraftClient minecraftClient) {
      if (minecraftClient.world == null) {
         return false;
      } else {
         Box box2 = clientPlayerEntity.getBoundingBox().contract(1.0E-7);
         int intValue = MathHelper.floor(box2.minX);
         int intValue2 = MathHelper.floor(box2.maxX);
         int intValue3 = MathHelper.floor(box2.minY);
         int intValue4 = MathHelper.floor(box2.maxY);
         int intValue5 = MathHelper.floor(box2.minZ);
         int intValue6 = MathHelper.floor(box2.maxZ);
         Mutable mutable = new Mutable();

         for (int intValue7 = intValue; intValue7 <= intValue2; intValue7++) {
            for (int intValue8 = intValue3; intValue8 <= intValue4; intValue8++) {
               for (int intValue9 = intValue5; intValue9 <= intValue6; intValue9++) {
                  mutable.set(intValue7, intValue8, intValue9);
                  if (minecraftClient.world.getBlockState(mutable).isOf(Blocks.COBWEB)) {
                     return true;
                  }
               }
            }
         }

         return false;
      }
   }
}
