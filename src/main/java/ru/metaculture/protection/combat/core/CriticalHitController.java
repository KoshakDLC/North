package ru.metaculture.protection;

import net.minecraft.block.Blocks;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.AttributeModifiersComponent.Entry;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos.Mutable;

public final class CriticalHitController implements MinecraftAccessor {
   private static final long TIMESTAMP = 624L;
   private static final float FLOAT_VALUE = 10.0F;
   private static final float FLOAT_VALUE_2 = 8.0F;
   private static final float FLOAT_VALUE_3 = 13.0F;
   private static long timestamp = System.currentTimeMillis();
   private static long timestamp2;
   private static int intValue;
   private static int intValue2 = -1;

   private CriticalHitController() {
   }

   public static void invoke() {
      timestamp2 = System.currentTimeMillis();
      intValue2 = -1;
   }

   public static void invoke2() {
      intValue2 = -1;
   }

   public static void invoke3() {
      intValue++;
   }

   public static void invoke4() {
      timestamp = System.currentTimeMillis();
   }

   public static void invoke5() {
      timestamp = System.currentTimeMillis();
   }

   public static boolean check(LivingEntity livingEntity, int i) {
      if (a_.player == null || a_.world == null || a_.options == null || livingEntity == null || !livingEntity.isAlive()) {
         return false;
      } else if (!check5(i)) {
         return false;
      } else if (!a_.player.isGliding() && !a_.player.getAbilities().flying) {
         CriticalHitController.CriticalHitControllerData criticalHitControllerData = resolve(i);
         if (check6(criticalHitControllerData)) {
            return true;
         } else {
            boolean flag = check11(criticalHitControllerData);
            boolean flag2 = AttackAura.dopolnitelnyeNastroyki.isEnabled("Умные криты");
            boolean flag3 = check9(criticalHitControllerData);
            boolean flag4 = check8();
            if (check10(criticalHitControllerData, flag2, flag3)) {
               return false;
            } else if (flag4) {
               return true;
            } else if (i <= 0) {
               return check14(criticalHitControllerData);
            } else {
               return flag ? check12(criticalHitControllerData) : check13(criticalHitControllerData, i);
            }
         }
      } else {
         return true;
      }
   }

   public static boolean check2(LivingEntity livingEntity) {
      return a_.player != null && a_.player.isSprinting() && !a_.player.isSwimming() && !a_.player.isGliding() ? check(livingEntity, 1) : false;
   }

   public static boolean check3(LivingEntity livingEntity) {
      return a_.player == null || !a_.player.isSprinting() || a_.player.isSwimming() || a_.player.isGliding();
   }

   public static boolean check4(boolean bl) {
      if (!bl || a_.player == null || a_.interactionManager == null) {
         return true;
      } else if (intValue2 == intValue) {
         return false;
      } else if (a_.player.isUsingItem() && a_.player.getActiveItem().getItem() == Items.SHIELD) {
         a_.interactionManager.stopUsingItem(a_.player);
         a_.player.stopUsingItem();
         intValue2 = intValue;
         return false;
      } else {
         return true;
      }
   }

   private static boolean check5(int i) {
      float floatValue = Math.max(0.0F, (float)i);
      float floatValue2 = (float)Math.max(1.0, TpsTracker.getDoubleValue());
      long longValue = Math.max(0L, (long)Math.round(Math.max(0.0F, measure() - floatValue) * 50.0F * (20.0F / floatValue2)));
      return compute() >= longValue;
   }

   private static float measure() {
      double doubleValue = measure2();
      return MathHelper.clamp((float)(10.0 * (1.0 - doubleValue)), 8.0F, 13.0F);
   }

   private static double measure2() {
      if (a_.player == null) {
         return 0.0;
      } else {
         double doubleValue2 = 0.0;
         double doubleValue3 = 1.0;

         for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            ItemStack itemStack = a_.player.getEquippedStack(equipmentSlot);
            if (!itemStack.isEmpty()) {
               AttributeModifiersComponent attributeModifiersComponent = (AttributeModifiersComponent)itemStack.get(DataComponentTypes.ATTRIBUTE_MODIFIERS);
               if (attributeModifiersComponent != null) {
                  for (Entry entry : attributeModifiersComponent.modifiers()) {
                     if (entry.attribute() == EntityAttributes.ATTACK_SPEED && entry.slot().matches(equipmentSlot)) {
                        EntityAttributeModifier entityAttributeModifier = entry.modifier();
                        if (entityAttributeModifier.operation() == Operation.ADD_MULTIPLIED_BASE) {
                           doubleValue2 += entityAttributeModifier.value();
                        } else if (entityAttributeModifier.operation() == Operation.ADD_MULTIPLIED_TOTAL) {
                           doubleValue3 *= 1.0 + entityAttributeModifier.value();
                        }
                     }
                  }
               }
            }
         }

         return (1.0 + doubleValue2) * doubleValue3 - 1.0;
      }
   }

   private static long compute() {
      return System.currentTimeMillis() - timestamp;
   }

   private static CriticalHitController.CriticalHitControllerData resolve(int i) {
      ClientPlayerEntity clientPlayerEntity = a_.player;
      boolean flag5 = AttackPacketController.isFlag();
      boolean flag6 = flag5 ? AttackPacketController.isFlag2() : clientPlayerEntity.isOnGround();
      float floatValue3 = flag5 ? AttackPacketController.getFloatValue() : (float)clientPlayerEntity.fallDistance;
      double doubleValue4 = flag5 ? AttackPacketController.getDoubleValue4() : clientPlayerEntity.getVelocity().y;
      double doubleValue5 = Math.max(0.0, clientPlayerEntity.getAttributeValue(EntityAttributes.GRAVITY));
      Box box2 = clientPlayerEntity.getBoundingBox();

      for (int intValue = 0; intValue < Math.max(0, i); intValue++) {
         if (flag6 && a_.options.jumpKey.isPressed()) {
            flag6 = false;
            doubleValue4 = 0.42;
         } else if (!flag6) {
            doubleValue4 = (doubleValue4 - doubleValue5) * 0.98;
            if (doubleValue4 < 0.0) {
               floatValue3 += (float)(-doubleValue4);
            }
         }

         box2 = box2.offset(0.0, doubleValue4, 0.0);
      }

      return new CriticalHitController.CriticalHitControllerData(flag6, floatValue3, doubleValue4, box2, clientPlayerEntity.horizontalCollision);
   }

   private static boolean check6(CriticalHitController.CriticalHitControllerData criticalHitControllerData2) {
      return a_.player.hasStatusEffect(StatusEffects.BLINDNESS)
         || a_.player.hasStatusEffect(StatusEffects.LEVITATION)
         || check20(criticalHitControllerData2.box)
         || a_.player.isSubmergedIn(FluidTags.WATER)
         || a_.player.isInLava()
         || a_.player.isClimbing()
         || a_.player.getAbilities().flying;
   }

   private static boolean check7(CriticalHitController.CriticalHitControllerData criticalHitControllerData3) {
      return a_.player.hasStatusEffect(StatusEffects.BLINDNESS)
         || a_.player.hasStatusEffect(StatusEffects.LEVITATION)
         || a_.player.isSubmergedIn(FluidTags.WATER)
         || a_.player.isInLava()
         || a_.player.isClimbing()
         || a_.player.isSwimming()
         || a_.player.isGliding()
         || a_.player.getAbilities().flying;
   }

   private static boolean check8() {
      return a_.player.hasStatusEffect(StatusEffects.BLINDNESS) || a_.player.hasStatusEffect(StatusEffects.LEVITATION);
   }

   private static boolean check9(CriticalHitController.CriticalHitControllerData criticalHitControllerData4) {
      return a_.options.jumpKey.isPressed() || !criticalHitControllerData4.onGround && criticalHitControllerData4.velocityY > 0.08;
   }

   private static boolean check10(CriticalHitController.CriticalHitControllerData criticalHitControllerData5, boolean bl, boolean bl2) {
      if (timestamp2 <= 0L || System.currentTimeMillis() - timestamp2 > 624L) {
         return false;
      } else {
         return bl && !bl2 ? false : !criticalHitControllerData5.onGround && criticalHitControllerData5.fallDistance <= 0.0F && criticalHitControllerData5.velocityY > -0.03;
      }
   }

   private static boolean check11(CriticalHitController.CriticalHitControllerData criticalHitControllerData6) {
      return criticalHitControllerData6.horizontalCollision || check17(criticalHitControllerData6) || compute2(criticalHitControllerData6) >= 2;
   }

   private static boolean check12(CriticalHitController.CriticalHitControllerData criticalHitControllerData7) {
      if (check15(criticalHitControllerData7)) {
         return true;
      } else {
         float floatValue4 = measure3(criticalHitControllerData7, true);
         double doubleValue6 = measure4(criticalHitControllerData7, true);
         if (criticalHitControllerData7.horizontalCollision || check17(criticalHitControllerData7)) {
            floatValue4 = Math.min(floatValue4, 0.004F);
            doubleValue6 = Math.max(doubleValue6, -0.01);
         }

         return !criticalHitControllerData7.onGround && criticalHitControllerData7.fallDistance > floatValue4 && criticalHitControllerData7.velocityY < doubleValue6;
      }
   }

   private static boolean check13(CriticalHitController.CriticalHitControllerData criticalHitControllerData8, int i) {
      if (criticalHitControllerData8.onGround || check7(criticalHitControllerData8)) {
         return false;
      } else if (check15(criticalHitControllerData8)) {
         return true;
      } else {
         boolean flag7 = criticalHitControllerData8.fallDistance > measure3(criticalHitControllerData8, false);
         boolean flag8 = criticalHitControllerData8.velocityY < measure4(criticalHitControllerData8, false);
         boolean flag9 = i <= 0 || !criticalHitControllerData8.onGround;
         return flag7 && flag8 && flag9;
      }
   }

   private static boolean check14(CriticalHitController.CriticalHitControllerData criticalHitControllerData9) {
      float floatValue5 = measure3(criticalHitControllerData9, false);
      double doubleValue7 = measure4(criticalHitControllerData9, false);
      if (a_.player.isOnGround()) {
         return false;
      } else if (!criticalHitControllerData9.onGround && !check7(criticalHitControllerData9)) {
         return !((float)a_.player.fallDistance <= floatValue5) && !(a_.player.getVelocity().y >= doubleValue7)
            ? !AttackPacketController.isFlag() || AttackPacketController.getFloatValue() > floatValue5 && AttackPacketController.getDoubleValue4() < doubleValue7
            : false;
      } else {
         return false;
      }
   }

   private static boolean check15(CriticalHitController.CriticalHitControllerData criticalHitControllerData10) {
      return check16() && !criticalHitControllerData10.onGround && criticalHitControllerData10.fallDistance > 0.0F && criticalHitControllerData10.velocityY < -0.01;
   }

   private static boolean check16() {
      return a_.player.hurtTime > 0 || a_.player.hasStatusEffect(StatusEffects.SLOWNESS);
   }

   private static float measure3(CriticalHitController.CriticalHitControllerData criticalHitControllerData11, boolean bl) {
      float floatValue6 = bl ? 0.01F : 0.03F;
      if (check16()) {
         floatValue6 = Math.min(floatValue6, bl ? 0.008F : 0.012F);
      }

      if (criticalHitControllerData11.horizontalCollision) {
         floatValue6 = Math.min(floatValue6, 0.012F);
      }

      return floatValue6;
   }

   private static double measure4(CriticalHitController.CriticalHitControllerData criticalHitControllerData12, boolean bl) {
      double doubleValue8 = bl ? -0.02 : -0.03;
      if (check16()) {
         doubleValue8 = Math.max(doubleValue8, bl ? -0.012 : -0.018);
      }

      if (criticalHitControllerData12.horizontalCollision) {
         doubleValue8 = Math.max(doubleValue8, -0.015);
      }

      return doubleValue8;
   }

   private static boolean check17(CriticalHitController.CriticalHitControllerData criticalHitControllerData13) {
      return !a_.world.isSpaceEmpty(a_.player, criticalHitControllerData13.box.expand(0.22, 0.0, 0.22).contract(1.0E-7));
   }

   private static int compute2(CriticalHitController.CriticalHitControllerData criticalHitControllerData14) {
      Vec3d vec3d = criticalHitControllerData14.box.getCenter();
      double doubleValue9 = criticalHitControllerData14.box.minY + 0.1;
      double doubleValue10 = Math.min(criticalHitControllerData14.box.maxY - 0.1, criticalHitControllerData14.box.minY + 0.95);
      int intValue2 = 0;
      intValue2 += check18(vec3d.x + 0.72, vec3d.z, doubleValue9, doubleValue10) ? 1 : 0;
      intValue2 += check18(vec3d.x - 0.72, vec3d.z, doubleValue9, doubleValue10) ? 1 : 0;
      intValue2 += check18(vec3d.x, vec3d.z + 0.72, doubleValue9, doubleValue10) ? 1 : 0;
      return intValue2 + (check18(vec3d.x, vec3d.z - 0.72, doubleValue9, doubleValue10) ? 1 : 0);
   }

   private static boolean check18(double d, double e, double f, double g) {
      return check19(BlockPos.ofFloored(d, f, e)) || check19(BlockPos.ofFloored(d, g, e));
   }

   private static boolean check19(BlockPos blockPos) {
      return !a_.world.getBlockState(blockPos).isAir() && !a_.world.getBlockState(blockPos).getCollisionShape(a_.world, blockPos).isEmpty();
   }

   private static boolean check20(Box box) {
      int intValue3 = (int)Math.floor(box.minX);
      int intValue4 = (int)Math.floor(box.maxX);
      int intValue5 = (int)Math.floor(box.minY);
      int intValue6 = (int)Math.floor(box.maxY);
      int intValue7 = (int)Math.floor(box.minZ);
      int intValue8 = (int)Math.floor(box.maxZ);
      Mutable mutable = new Mutable();

      for (int intValue9 = intValue3; intValue9 <= intValue4; intValue9++) {
         for (int intValue10 = intValue5; intValue10 <= intValue6; intValue10++) {
            for (int intValue11 = intValue7; intValue11 <= intValue8; intValue11++) {
               mutable.set(intValue9, intValue10, intValue11);
               if (a_.world.getBlockState(mutable).isOf(Blocks.COBWEB)) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   record CriticalHitControllerData(boolean onGround, float fallDistance, double velocityY, Box box, boolean horizontalCollision) {
   }
}
