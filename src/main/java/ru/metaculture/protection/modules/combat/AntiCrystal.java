package ru.metaculture.protection;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ChunkDeltaUpdateS2CPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleAccess(
   usernames = {"lichoday"}
)
@ModuleRegister(
   name = "AntiCrystal",
   category = Category.Combat,
   description = "Перекрывает блоками опасные базы под кристаллы рядом с вами",
   riskLevels = {ModuleRiskLevel.RISKY}
)
public class AntiCrystal extends Module {
   private final NumberSetting radius = new NumberSetting("Radius", 4.2F, 2.0F, 6.0F, 0.1F, false);
   private final NumberSetting delay = new NumberSetting("Delay", 55.0F, 0.0F, 220.0F, 5.0F, false);
   private final NumberSetting reaction = new NumberSetting("Reaction", 25.0F, 0.0F, 150.0F, 5.0F, false);
   private final NumberSetting yawSpeed = new NumberSetting("Yaw Speed", 180.0F, 45.0F, 360.0F, 5.0F, false);
   private final NumberSetting pitchSpeed = new NumberSetting("Pitch Speed", 170.0F, 45.0F, 360.0F, 5.0F, false);
   private final BooleanSetting inventorySwap = new BooleanSetting("Inventory Swap", true);
   private final BooleanSetting restoreSlot = new BooleanSetting("Restore Slot", false);
   private final BooleanSetting packetTrigger = new BooleanSetting("Packet Trigger", true);
   private final BooleanSetting rescan = new BooleanSetting("Rescan", true);
   private final ArrayDeque<AntiCrystal.AntiCrystalPositionData> arrayDeque = new ArrayDeque<>();
   private final Set<Long> values = new HashSet<>();
   private long timestamp;

   public AntiCrystal() {
      this.addSettings(
         new Setting[]{
            this.radius,
            this.delay,
            this.reaction,
            this.yawSpeed,
            this.pitchSpeed,
            this.inventorySwap,
            this.restoreSlot,
            this.packetTrigger,
            this.rescan
         }
      );
   }

   @EventHandler
   public void onPacket(PacketEvent packetEvent) {
      if (this.packetTrigger.isEnabled()
         && !ServerModeDetector.check()
         && packetEvent != null
         && packetEvent.getPacketEventState().equals(PacketEvent.PacketEventState.RECEIVE)) {
         if (packetEvent.getPacket() instanceof BlockUpdateS2CPacket blockUpdateS2CPacket) {
            this.invoke(blockUpdateS2CPacket.getPos(), blockUpdateS2CPacket.getState());
         } else if (packetEvent.getPacket() instanceof ChunkDeltaUpdateS2CPacket chunkDeltaUpdateS2CPacket) {
            chunkDeltaUpdateS2CPacket.visitUpdates(this::invoke);
         }
      }
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (!ServerModeDetector.check() && CLIENT.interactionManager != null && CLIENT.getNetworkHandler() != null) {
         if (this.rescan.isEnabled()) {
            this.invoke2();
         }

         this.invoke6();
         long longValue = System.currentTimeMillis();
         if (!((float)(longValue - this.timestamp) < this.delay.getValue())) {
            AntiCrystal.AntiCrystalPositionData antiCrystalPositionData = this.resolve(longValue);
            if (antiCrystalPositionData != null) {
               int intValue = this.compute2();
               if (intValue >= 0) {
                  if (this.check3(antiCrystalPositionData.base(), intValue)) {
                     this.timestamp = longValue;
                     this.values.remove(antiCrystalPositionData.base().asLong());
                     this.arrayDeque.remove(antiCrystalPositionData);
                  }
               }
            }
         }
      } else {
         this.invoke7();
      }
   }

   @Override
   public void onDisable() {
      this.invoke7();
      RotationController.rotationControllerState2 = RotationController.RotationControllerState2.IDLE;
      RotationController.intValue = 0;
      RotationController.rotation = null;
      FreeLookController.active = false;
      super.onDisable();
   }

   private void invoke(BlockPos blockPos, BlockState blockState) {
      if (blockPos != null && blockState != null && this.check5(blockState.getBlock()) && this.check(blockPos)) {
         this.invoke3(blockPos);
      }
   }

   private void invoke2() {
      BlockPos blockPos2 = CLIENT.player.getBlockPos();
      int intValue2 = MathHelper.ceil(this.radius.getValue());

      for (int intValue3 = -intValue2; intValue3 <= intValue2; intValue3++) {
         for (int intValue4 = -2; intValue4 <= 2; intValue4++) {
            for (int intValue5 = -intValue2; intValue5 <= intValue2; intValue5++) {
               BlockPos blockPos3 = blockPos2.add(intValue3, intValue4, intValue5);
               if (this.check(blockPos3) && this.check5(CLIENT.world.getBlockState(blockPos3).getBlock())) {
                  this.invoke3(blockPos3);
               }
            }
         }
      }
   }

   private void invoke3(BlockPos blockPos) {
      if (this.check2(blockPos)) {
         long longValue2 = blockPos.asLong();
         if (this.values.add(longValue2)) {
            this.arrayDeque.addLast(new AntiCrystal.AntiCrystalPositionData(blockPos.toImmutable(), System.currentTimeMillis()));
         }
      }
   }

   private AntiCrystal.AntiCrystalPositionData resolve(long l) {
      AntiCrystal.AntiCrystalPositionData antiCrystalPositionData2 = null;
      double doubleValue = Double.MAX_VALUE;

      for (AntiCrystal.AntiCrystalPositionData antiCrystalPositionData3 : this.arrayDeque) {
         if (!((float)(l - antiCrystalPositionData3.createdAt()) < this.reaction.getValue()) && this.check2(antiCrystalPositionData3.base())) {
            double doubleValue2 = this.measure(antiCrystalPositionData3.base());
            if (doubleValue2 < doubleValue) {
               doubleValue = doubleValue2;
               antiCrystalPositionData2 = antiCrystalPositionData3;
            }
         }
      }

      return antiCrystalPositionData2;
   }

   private double measure(BlockPos blockPos) {
      Vec3d vec3d2 = blockPos.toCenterPos();
      Vec3d vec3d3 = CLIENT.player.getPos();
      Vec3d vec3d4 = vec3d2.subtract(vec3d3);
      double doubleValue3 = vec3d4.length();
      Vec3d vec3d5 = CLIENT.player.getVelocity();
      double doubleValue4 = 0.0;
      if (vec3d5.horizontalLengthSquared() > 1.0E-5 && vec3d4.horizontalLengthSquared() > 1.0E-5) {
         doubleValue4 = -vec3d5.normalize().dotProduct(new Vec3d(vec3d4.x, 0.0, vec3d4.z).normalize()) * 0.42;
      }

      Vec3d vec3d6 = CLIENT.player.getRotationVec(1.0F);
      double doubleValue5 = vec3d4.lengthSquared() <= 1.0E-5 ? 0.0 : -vec3d6.normalize().dotProduct(vec3d4.normalize()) * 0.22;
      double doubleValue6 = Math.abs(vec3d2.y - CLIENT.player.getY()) * 0.18;
      return doubleValue3 + doubleValue4 + doubleValue5 + doubleValue6;
   }

   private boolean check(BlockPos blockPos) {
      return blockPos != null && CLIENT.player != null
         ? CLIENT.player.squaredDistanceTo(blockPos.toCenterPos()) <= this.radius.getValue() * this.radius.getValue()
         : false;
   }

   private boolean check2(BlockPos blockPos) {
      if (blockPos != null && CLIENT.world != null) {
         BlockState blockState2 = CLIENT.world.getBlockState(blockPos);
         BlockPos blockPos4 = blockPos.up();
         return this.check5(blockState2.getBlock())
            && CLIENT.world.getBlockState(blockPos4).isAir()
            && CLIENT.world.getOtherEntities(null, Box.of(blockPos4.toCenterPos(), 0.86, 0.86, 0.86)).isEmpty();
      } else {
         return false;
      }
   }

   private boolean check3(BlockPos blockPos, int i) {
      int intValue6 = CLIENT.player.getInventory().getSelectedSlot();
      int intValue7 = this.compute(i, intValue6);
      if (intValue7 < 0) {
         return false;
      } else {
         this.invoke4(intValue7);
         Vec3d vec3d7 = blockPos.toCenterPos().add(0.0, 0.5, 0.0);
         this.invoke5(vec3d7);
         BlockHitResult blockHitResult = new BlockHitResult(vec3d7, Direction.UP, blockPos, false);
         CLIENT.interactionManager.interactBlock(CLIENT.player, Hand.MAIN_HAND, blockHitResult);
         CLIENT.player.swingHand(Hand.MAIN_HAND);
         if (this.restoreSlot.isEnabled() && intValue6 != intValue7) {
            this.invoke4(intValue6);
         }

         return true;
      }
   }

   private int compute(int i, int j) {
      if (i >= 0 && i < 9) {
         return i;
      } else if (this.inventorySwap.isEnabled() && i >= 9 && i <= 35) {
         int intValue8 = j >= 0 && j < 9 ? j : 0;
         CLIENT.interactionManager.clickSlot(CLIENT.player.currentScreenHandler.syncId, i, intValue8, SlotActionType.SWAP, CLIENT.player);
         return intValue8;
      } else {
         return -1;
      }
   }

   private void invoke4(int i) {
      if (i >= 0 && i <= 8 && i != CLIENT.player.getInventory().getSelectedSlot()) {
         CLIENT.player.getInventory().setSelectedSlot(i);
         CLIENT.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(i));
      }
   }

   private int compute2() {
      int intValue9 = -1;
      int intValue10 = Integer.MIN_VALUE;

      for (int intValue11 = 0; intValue11 < 36; intValue11++) {
         ItemStack itemStack2 = CLIENT.player.getInventory().getStack(intValue11);
         if (this.check4(itemStack2)) {
            int intValue12 = this.compute3(itemStack2, intValue11);
            if (intValue12 > intValue10) {
               intValue10 = intValue12;
               intValue9 = intValue11;
            }
         }
      }

      return intValue9;
   }

   private boolean check4(ItemStack itemStack) {
      if (itemStack != null && !itemStack.isEmpty() && itemStack.getItem() instanceof BlockItem blockItem) {
         Block block2 = blockItem.getBlock();
         if (block2 != Blocks.AIR && block2 != Blocks.SAND && block2 != Blocks.RED_SAND && block2 != Blocks.GRAVEL && block2 != Blocks.ANVIL) {
            BlockState blockState3 = block2.getDefaultState();
            return blockState3.isSolidBlock(CLIENT.world, BlockPos.ORIGIN);
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private int compute3(ItemStack itemStack, int i) {
      Block block3 = ((BlockItem)itemStack.getItem()).getBlock();
      int intValue13 = i < 9 ? 1000 : 0;
      if (block3 == Blocks.OBSIDIAN) {
         intValue13 += 80;
      } else if (block3 == Blocks.COBBLESTONE || block3 == Blocks.STONE || block3 == Blocks.DEEPSLATE) {
         intValue13 += 65;
      } else if (block3 == Blocks.NETHERRACK || block3 == Blocks.DIRT) {
         intValue13 += 40;
      }

      return intValue13 + Math.min(64, itemStack.getCount());
   }

   private void invoke5(Vec3d vec3d) {
      Vec3d vec3d8 = vec3d.subtract(CLIENT.player.getEyePos());
      float floatValue = (float)Math.toDegrees(Math.atan2(-vec3d8.x, vec3d8.z));
      float floatValue2 = (float)(-Math.toDegrees(Math.atan2(vec3d8.y, Math.hypot(vec3d8.x, vec3d8.z))));
      RotationController.invoke3(
         new Rotation(floatValue, MathHelper.clamp(floatValue2, -90.0F, 90.0F)),
         this.yawSpeed.getValue(),
         this.pitchSpeed.getValue(),
         this.yawSpeed.getValue(),
         this.pitchSpeed.getValue(),
         2,
         16,
         false
      );
   }

   private boolean check5(Block block) {
      return block == Blocks.OBSIDIAN || block == Blocks.BEDROCK;
   }

   private void invoke6() {
      Iterator iterator = this.arrayDeque.iterator();
      long longValue3 = System.currentTimeMillis();

      while (iterator.hasNext()) {
         AntiCrystal.AntiCrystalPositionData antiCrystalPositionData4 = (AntiCrystal.AntiCrystalPositionData)iterator.next();
         if (longValue3 - antiCrystalPositionData4.createdAt() > 2500L || !this.check2(antiCrystalPositionData4.base())) {
            this.values.remove(antiCrystalPositionData4.base().asLong());
            iterator.remove();
         }
      }
   }

   private void invoke7() {
      this.arrayDeque.clear();
      this.values.clear();
   }

   record AntiCrystalPositionData(BlockPos base, long createdAt) {
   }
}
