package ru.metaculture.protection;

import java.security.SecureRandom;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.LockSupport;
import lombok.Generated;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket.Mode;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public final class AttackUtils implements MinecraftAccessor {
   private static final SecureRandom SECURE_RANDOM = new SecureRandom();
   private static final MinecraftClient CLIENT = MinecraftClient.getInstance();
   public static long timestamp;
   public static int intValue;
   private static final Stopwatch STOPWATCH = new Stopwatch();
   private static boolean flag;
   private static int intValue2;

   public static void invoke() {
      timestamp++;
   }

   public static void invoke2() {
      timestamp = 0L;
   }

   public static boolean check() {
      return timestamp % 7L == 3L;
   }

   public static PlayerEntity getPlayer() {
      return CLIENT.player;
   }

   public static World getWorld() {
      return CLIENT.world;
   }

   public static float measure(float f) {
      float floatValue = 0.2F;
      return (float)(f + (SECURE_RANDOM.nextGaussian() * 0.2F * 2.0 - 0.2F));
   }

   public static boolean check2(int i) {
      return SECURE_RANDOM.nextInt(i + 1) >= 1.0F * (1.0F / Math.max((float)i, 1.0F));
   }

   public static boolean check3() {
      return SECURE_RANDOM.nextInt(2) == 1;
   }

   public static float measure2(float f, float g) {
      return SECURE_RANDOM.nextFloat(f, g);
   }

   public static float measure3() {
      return measure2(0.0F, 1.0F);
   }

   public static int compute() {
      return check3() ? 1 : -1;
   }

   public static int compute2() {
      if (CLIENT.player == null) {
         return -1;
      } else {
         for (int intValue = 0; intValue < 9; intValue++) {
            if (CLIENT.player.getInventory().getStack(intValue).getItem() instanceof AxeItem) {
               return intValue;
            }
         }

         return -1;
      }
   }

   public static Runnable[] resolve(LivingEntity livingEntity, boolean bl) {
      Runnable[] runnables = new Runnable[]{() -> {}, () -> {}};
      if (bl && CLIENT.player != null) {
         if (livingEntity instanceof PlayerEntity playerEntity) {
            if (!playerEntity.isBlocking()) {
               return runnables;
            }

            ItemStack itemStack = playerEntity.getMainHandStack();
            ItemStack itemStack2 = playerEntity.getOffHandStack();
            Item item = itemStack.isEmpty() ? null : itemStack.getItem();
            Item item2 = itemStack2.isEmpty() ? null : itemStack2.getItem();
            if (item == Items.SHIELD || item2 == Items.SHIELD) {
               int intValue2 = CLIENT.player.getInventory().getSelectedSlot();
               int intValue3;
               if ((intValue3 = compute2()) != -1 && intValue3 != intValue2) {
                  runnables[0] = () -> {
                     if (CLIENT.getNetworkHandler() != null) {
                        CLIENT.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(intValue3));
                     }
                  };
                  runnables[1] = () -> {
                     if (CLIENT.getNetworkHandler() != null) {
                        CLIENT.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(intValue2));
                     }
                  };
               }
            }
         }

         return runnables;
      } else {
         return runnables;
      }
   }

   public static Runnable[] resolve2(boolean bl) {
      Runnable[] runnables2 = new Runnable[]{() -> {}, () -> {}};
      if (bl && CLIENT.player != null) {
         if (CLIENT.player.isBlocking()) {
            Hand hand2 = CLIENT.player.getActiveHand();
            if (hand2 == null) {
               return runnables2;
            }

            runnables2[0] = () -> CLIENT.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, Direction.DOWN));
            runnables2[1] = () -> CLIENT.getNetworkHandler()
               .sendPacket(new PlayerInteractItemC2SPacket(hand2, 0, CLIENT.player.getYaw(), CLIENT.player.getPitch()));
         }

         return runnables2;
      } else {
         return runnables2;
      }
   }

   public static Runnable[] resolve3(boolean bl) {
      Runnable[] runnables3 = new Runnable[]{() -> {}, () -> {}};
      if (bl && CLIENT.player != null) {
         if (CLIENT.player.isSprinting() && !CLIENT.player.isOnGround() && !CLIENT.player.isSubmergedIn(FluidTags.WATER)) {
            runnables3[0] = () -> {
               CLIENT.player.setSprinting(false);
               CLIENT.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(CLIENT.player, Mode.STOP_SPRINTING));
            };
            runnables3[1] = () -> {
               CLIENT.player.setSprinting(true);
               CLIENT.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(CLIENT.player, Mode.START_SPRINTING));
            };
         }

         return runnables3;
      } else {
         return runnables3;
      }
   }

   public static boolean check4(boolean bl) {
      if (!bl || CLIENT.player == null) {
         return true;
      } else if (CLIENT.player.isSwimming()) {
         return true;
      } else {
         if (!(CLIENT.player.fallDistance > 0.0)) {
            intValue = 2;
         }

         if (intValue > 0) {
            intValue--;
         }

         if (intValue == 0 && CLIENT.player.fallDistance > 0.0) {
            return true;
         } else {
            boolean flag = CLIENT.world.getBlockState(CLIENT.player.getBlockPos()).isOf(Blocks.COBWEB);
            boolean flag2 = !CLIENT.player.isJumping() && (CLIENT.player.isTouchingWater() || CLIENT.player.isInLava())
               || CLIENT.player.isSubmergedIn(FluidTags.WATER)
               || CLIENT.player.isSubmergedIn(FluidTags.LAVA)
               || flag;
            return flag2
               || !CLIENT.player.isJumping() && CLIENT.player.age > 6 && AttackAura.dopolnitelnyeNastroyki.isEnabled("Умные криты")
               || CLIENT.player.isClimbing()
               || CLIENT.player.hasVehicle()
               || CLIENT.player.hasStatusEffect(StatusEffects.BLINDNESS)
               || CLIENT.player.hasStatusEffect(StatusEffects.LEVITATION)
               || CLIENT.player.hasStatusEffect(StatusEffects.SLOW_FALLING)
               || CLIENT.player.getAbilities().flying;
         }
      }
   }

   public static boolean check5(LivingEntity livingEntity, Runnable runnable, Runnable runnable2, Hand hand, boolean bl) {
      return check8(livingEntity, runnable, runnable2, hand, bl, null, false);
   }

   public static boolean check6(LivingEntity livingEntity, Runnable runnable, Runnable runnable2, Hand hand, boolean bl, Runnable runnable3) {
      return AttackUtils.AttackUtilsAction.check(livingEntity, runnable, runnable2, hand, bl, runnable3);
   }

   public static void invoke3() {
      AttackUtils.AttackUtilsAction.invoke();
   }

   public static void invoke4() {
      AttackUtils.AttackUtilsAction.invoke3();
   }

   public static boolean check7(LivingEntity livingEntity) {
      return AttackUtils.AttackUtilsAction.check2(livingEntity);
   }

   static boolean check8(LivingEntity livingEntity, Runnable runnable, Runnable runnable2, Hand hand, boolean bl, Runnable runnable3, boolean bl2) {
      if (bl2 && !check9(livingEntity)) {
         return false;
      } else {
         if (runnable != null) {
            runnable.run();
         }

         boolean flag3 = !bl2 || check9(livingEntity);
         if (flag3 && livingEntity != null && CLIENT.interactionManager != null && CLIENT.player != null) {
            CLIENT.interactionManager.attackEntity(CLIENT.player, livingEntity);
            if (hand != null) {
               CLIENT.player.swingHand(hand);
            }

            if (bl) {
               invoke();
            } else {
               invoke2();
            }

            STOPWATCH.invoke();
         }

         if (runnable2 != null) {
            runnable2.run();
         }

         if (flag3 && livingEntity != null && runnable3 != null) {
            runnable3.run();
         }

         return flag3 && livingEntity != null;
      }
   }

   private static boolean check9(LivingEntity livingEntity) {
      return livingEntity != null
         && livingEntity == AttackAura.livingEntity
         && livingEntity.isAlive()
         && !livingEntity.isRemoved()
         && CLIENT.player != null
         && CLIENT.player.isAlive()
         && CLIENT.world != null
         && CLIENT.interactionManager != null;
   }

   public static long compute3() {
      if (CLIENT.player == null) {
         return 500L;
      } else {
         double doubleValue = CLIENT.player.getAttributeValue(EntityAttributes.ATTACK_SPEED);
         float floatValue2 = 0.2F;
         long longValue = (long)(1.0 / doubleValue * 1000.0 * (1.0F - floatValue2));
         if (AttackAura.taymingUdara.is("Динамичный")) {
            longValue += ThreadLocalRandom.current().nextLong(40L, 60L);
         } else {
            longValue += 30L;
         }

         return Math.max(longValue, 400L);
      }
   }

   public static boolean check10(long l) {
      return STOPWATCH.check((double)(compute3() + l));
   }

   public static boolean check11() {
      return check10(0L);
   }

   public static boolean check12(long l) {
      return STOPWATCH.check((double)l);
   }

   public static float measure4() {
      return Math.min((float)STOPWATCH.compute() / (float)compute3(), 1.0F);
   }

   public static float measure5() {
      return (float)STOPWATCH.compute();
   }

   public static boolean check13(LivingEntity livingEntity, double d) {
      return livingEntity != null
         && EntityRaycastUtils.check(MathHelper.wrapDegrees(CLIENT.player.getYaw()), CLIENT.player.getPitch(), (float)d, livingEntity);
   }

   public static boolean check14(LivingEntity livingEntity, double d, boolean bl) {
      return livingEntity != null && CLIENT.player != null && AttackPacketController.isFlag4()
         ? EntityRaycastUtils.check4(
            MathHelper.wrapDegrees(AttackPacketController.measure(CLIENT.player.getYaw())),
            AttackPacketController.measure2(CLIENT.player.getPitch()),
            d,
            livingEntity,
            bl
         )
         : false;
   }

   public static boolean check15(LivingEntity livingEntity, boolean bl, boolean bl2, boolean bl3, long l, float[] fs) {
      if (bl2 && livingEntity != null && !RaycastUtils.check(livingEntity, fs[0], true)) {
         return false;
      } else if (!check10(l)) {
         return false;
      } else {
         boolean flag4 = check4(bl3);
         if (flag4 && bl && !check13(livingEntity, fs[0])) {
            flag4 = false;
         }

         return flag4;
      }
   }

   public static boolean check16(LivingEntity livingEntity, boolean bl, boolean bl2, long l, float[] fs) {
      return check15(livingEntity, bl, true, bl2, l, fs);
   }

   public static boolean check17(LivingEntity livingEntity, float[] fs) {
      return livingEntity != null
         && check16(livingEntity, false, false, -80L, fs)
         && !CLIENT.player.isOnGround()
         && !CLIENT.player.isSubmergedIn(FluidTags.WATER)
         && CLIENT.player.getVelocity().y <= 0.0030162615090425808;
   }

   public static boolean check18(LivingEntity livingEntity, float[] fs) {
      return livingEntity != null
         && check16(livingEntity, false, false, -60L, fs)
         && !CLIENT.player.isOnGround()
         && !CLIENT.player.isSubmergedIn(FluidTags.WATER)
         && CLIENT.player.getVelocity().y <= 0.16477328182606651;
   }

   private static int compute4() {
      return 3;
   }

   public static void invoke5() {
      flag = false;
      intValue2 = 0;
   }

   public static void invoke6(LivingEntity livingEntity, boolean bl, boolean bl2, boolean bl3) {
      if (livingEntity == null || intValue2 == 0 || !bl3 || livingEntity.hurtTime != 0) {
         invoke5();
      }

      if (bl3 && livingEntity != null && check12(check() ? 250L : 150L) && CLIENT.player.handSwinging) {
         if (!flag && intValue2 == 0 && livingEntity.hurtTime == 0) {
            flag = true;
            intValue2 = compute4();
         }

         if (flag && intValue2 > 0 && (!bl2 || check13(livingEntity, 6.0)) && check5(livingEntity, () -> {}, () -> {}, Hand.MAIN_HAND, bl)) {
            intValue2--;
         }
      }
   }

   @Generated
   private AttackUtils() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }

   @Generated
   public static Stopwatch getSTOPWATCH() {
      return STOPWATCH;
   }

   static final class AttackUtilsAction {
      private static final double DOUBLE_VALUE = 45.0;
      private static final double DOUBLE_VALUE_2 = 49.2;
      private static final double DOUBLE_VALUE_3 = 4.5;
      private static final long TIMESTAMP = 250000L;
      private static final long TIMESTAMP_2 = 120000L;
      private static final Object OBJECT = new Object();
      private static final Thread THREAD = new Thread(AttackUtils.AttackUtilsAction::invoke4, "Wild Adaptive Tick Edge");
      private static volatile boolean flag;
      private static volatile boolean flag2;
      private static volatile long timestamp;
      private static volatile LivingEntity livingEntity;
      private static volatile Runnable runnable;
      private static volatile Runnable runnable2;
      private static volatile Runnable runnable3;
      private static volatile Hand hand;
      private static volatile boolean flag3;

      private AttackUtilsAction() {
      }

      static void invoke() {
         if (!flag) {
            synchronized (OBJECT) {
               if (!flag) {
                  flag = true;
                  THREAD.start();
               }
            }
         }
      }

      static boolean check(LivingEntity livingEntity, Runnable runnable, Runnable runnable2, Hand hand, boolean bl, Runnable runnable3) {
         invoke();
         long longValue2 = System.nanoTime();
         if (!TpsTracker.check3(longValue2)) {
            return AttackUtils.check8(livingEntity, runnable, runnable2, hand, bl, runnable3, false);
         } else if (flag2 && livingEntity == livingEntity) {
            return false;
         } else {
            double doubleValue2 = TpsTracker.measure3(longValue2);
            double doubleValue3 = TpsTracker.measure2(longValue2);
            if (!(doubleValue3 <= 4.5) && (!(doubleValue2 >= 45.0) || !(doubleValue2 <= 49.2))) {
               long longValue3 = TpsTracker.compute(longValue2, 45.0);
               if (longValue3 <= 250000L) {
                  return AttackUtils.check8(livingEntity, runnable, runnable2, hand, bl, runnable3, true);
               } else {
                  invoke2(longValue2 + longValue3, livingEntity, runnable, runnable2, hand, bl, runnable3);
                  return false;
               }
            } else {
               return AttackUtils.check8(livingEntity, runnable, runnable2, hand, bl, runnable3, true);
            }
         }
      }

      static boolean check2(LivingEntity livingEntity) {
         return flag2 && livingEntity == livingEntity;
      }

      private static void invoke2(long l, LivingEntity livingEntity, Runnable runnable, Runnable runnable2, Hand hand, boolean bl, Runnable runnable3) {
         synchronized (OBJECT) {
            AttackUtilsAction.livingEntity = livingEntity;
            AttackUtilsAction.runnable = runnable;
            AttackUtilsAction.runnable2 = runnable2;
            AttackUtilsAction.hand = hand;
            flag3 = bl;
            AttackUtilsAction.runnable3 = runnable3;
            timestamp = l;
            flag2 = true;
         }

         LockSupport.unpark(THREAD);
      }

      static void invoke3() {
         synchronized (OBJECT) {
            flag2 = false;
            livingEntity = null;
            runnable = null;
            runnable2 = null;
            runnable3 = null;
            hand = null;
            flag3 = false;
            timestamp = 0L;
         }
      }

      private static void invoke4() {
         while (true) {
            if (!flag2) {
               LockSupport.park();
            } else {
               long longValue4 = timestamp;
               long longValue5 = longValue4 - System.nanoTime();
               if (longValue5 > 120000L) {
                  LockSupport.parkNanos(longValue5 - 120000L);
               } else {
                  long longValue6;
                  while ((longValue6 = System.nanoTime()) < longValue4 && flag2 && timestamp == longValue4) {
                     Thread.onSpinWait();
                  }

                  if (longValue6 >= longValue4) {
                     invoke5(longValue4);
                  }
               }
            }
         }
      }

      private static void invoke5(long l) {
         LivingEntity livingEntity2;
         Runnable runnable4;
         Runnable runnable5;
         Runnable runnable6;
         Hand hand3;
         boolean flag5;
         synchronized (OBJECT) {
            if (!flag2 || timestamp != l) {
               return;
            }

            livingEntity2 = livingEntity;
            runnable4 = runnable;
            runnable5 = runnable2;
            hand3 = hand;
            flag5 = flag3;
            runnable6 = runnable3;
            flag2 = false;
            livingEntity = null;
            runnable = null;
            runnable2 = null;
            runnable3 = null;
            hand = null;
            flag3 = false;
            timestamp = 0L;
         }

         try {
            AttackUtils.check8(livingEntity2, runnable4, runnable5, hand3, flag5, runnable6, true);
         } catch (Throwable exception) {
         }
      }

      static {
         THREAD.setDaemon(true);
      }
   }
}
