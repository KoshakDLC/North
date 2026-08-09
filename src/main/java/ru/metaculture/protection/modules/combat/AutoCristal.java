package ru.metaculture.protection;

import java.util.Comparator;
import java.util.stream.StreamSupport;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "AutoCristal",
   category = Category.Combat,
   description = "Автоматическая установка и взрыв кристаллов",
   riskLevels = {ModuleRiskLevel.RISKY, ModuleRiskLevel.VIP}
)
public class AutoCristal extends Module {
   public final ModeSetting rezhim = new ModeSetting("Режим", "Авто", "Авто", "Недоразвитый");
   public final NumberSetting skorost = new NumberSetting("Скорость", 50.0F, 0.0F, 1000.0F, 10.0F, false);
   public final NumberSetting radiusPoiska = new NumberSetting("Радиус поиска", 4.0F, 1.0F, 6.0F, 1.0F, false);
   public final Stopwatch stopwatch = new Stopwatch();

   public AutoCristal() {
      this.addSettings(new Setting[]{this.rezhim, this.skorost, this.radiusPoiska});
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (!ServerModeDetector.check()) {
         if (this.stopwatch.check((double)((int)this.skorost.getValue()))) {
            int intValue = this.compute(Items.END_CRYSTAL);
            int intValue2 = this.compute(Items.OBSIDIAN);
            EndCrystalEntity endCrystalEntity2 = this.resolve();
            if (endCrystalEntity2 != null) {
               this.invoke3(endCrystalEntity2.getPos());
               CLIENT.interactionManager.attackEntity(CLIENT.player, endCrystalEntity2);
               CLIENT.player.swingHand(Hand.MAIN_HAND);
               this.stopwatch.invoke();
            } else if (intValue != -1) {
               BlockPos blockPos2 = this.resolve2();
               if (blockPos2 != null) {
                  this.invoke3(blockPos2.toCenterPos().add(0.0, 0.5, 0.0));
                  this.invoke(blockPos2, intValue, Direction.UP);
                  this.stopwatch.invoke();
               } else {
                  if (this.rezhim.is("Авто") && intValue2 != -1) {
                     BlockPos blockPos3 = this.resolve3();
                     if (blockPos3 != null) {
                        this.invoke3(blockPos3.toCenterPos());
                        this.invoke(blockPos3.down(), intValue2, Direction.UP);
                        this.stopwatch.invoke();
                     }
                  }
               }
            }
         }
      }
   }

   private void invoke(BlockPos blockPos, int i, Direction direction) {
      this.invoke2(i);
      Vec3d vec3d2 = blockPos.toCenterPos().add(direction.getOffsetX() * 0.5, direction.getOffsetY() * 0.5, direction.getOffsetZ() * 0.5);
      BlockHitResult blockHitResult = new BlockHitResult(vec3d2, direction, blockPos, false);
      CLIENT.interactionManager.interactBlock(CLIENT.player, Hand.MAIN_HAND, blockHitResult);
      CLIENT.player.swingHand(Hand.MAIN_HAND);
   }

   private EndCrystalEntity resolve() {
      double doubleValue = this.radiusPoiska.getValue();
      return StreamSupport.<Entity>stream(CLIENT.world.getEntities().spliterator(), false)
         .filter(entity -> entity instanceof EndCrystalEntity)
         .map(entity -> (EndCrystalEntity)entity)
         .filter(endCrystalEntity -> CLIENT.player.distanceTo(endCrystalEntity) <= doubleValue)
         .min(Comparator.comparingDouble(endCrystalEntity -> CLIENT.player.distanceTo(endCrystalEntity)))
         .orElse(null);
   }

   private BlockPos resolve2() {
      double doubleValue2 = this.radiusPoiska.getValue();
      BlockPos blockPos4 = CLIENT.player.getBlockPos();
      int intValue3 = (int)doubleValue2;

      for (int intValue4 = -intValue3; intValue4 <= intValue3; intValue4++) {
         for (int intValue5 = -intValue3; intValue5 <= intValue3; intValue5++) {
            for (int intValue6 = -intValue3; intValue6 <= intValue3; intValue6++) {
               BlockPos blockPos5 = blockPos4.add(intValue4, intValue5, intValue6);
               if (!(CLIENT.player.squaredDistanceTo(blockPos5.toCenterPos()) > doubleValue2 * doubleValue2)
                  && (CLIENT.world.getBlockState(blockPos5).isOf(Blocks.OBSIDIAN) || CLIENT.world.getBlockState(blockPos5).isOf(Blocks.BEDROCK))
                  && CLIENT.world.isAir(blockPos5.up())
                  && CLIENT.world.getOtherEntities(null, new Box(blockPos5.up())).isEmpty()) {
                  return blockPos5;
               }
            }
         }
      }

      return null;
   }

   private BlockPos resolve3() {
      double doubleValue3 = this.radiusPoiska.getValue();
      BlockPos blockPos6 = CLIENT.player.getBlockPos();
      int intValue7 = (int)doubleValue3;

      for (int intValue8 = -intValue7; intValue8 <= intValue7; intValue8++) {
         for (int intValue9 = -intValue7; intValue9 <= intValue7; intValue9++) {
            for (int intValue10 = -intValue7; intValue10 <= intValue7; intValue10++) {
               BlockPos blockPos7 = blockPos6.add(intValue8, intValue9, intValue10);
               if (!(CLIENT.player.squaredDistanceTo(blockPos7.toCenterPos()) > doubleValue3 * doubleValue3)
                  && CLIENT.world.isAir(blockPos7)
                  && CLIENT.world.getBlockState(blockPos7.down()).isSolidBlock(CLIENT.world, blockPos7.down())
                  && CLIENT.world.isAir(blockPos7.up())
                  && CLIENT.world.getOtherEntities(null, new Box(blockPos7)).isEmpty()
                  && CLIENT.world.getOtherEntities(null, new Box(blockPos7.up())).isEmpty()) {
                  return blockPos7;
               }
            }
         }
      }

      return null;
   }

   private int compute(Item item) {
      for (int intValue11 = 0; intValue11 < 9; intValue11++) {
         if (CLIENT.player.getInventory().getStack(intValue11).isOf(item)) {
            return intValue11;
         }
      }

      return -1;
   }

   private void invoke2(int i) {
      if (i != CLIENT.player.getInventory().getSelectedSlot() && i >= 0 && i < 9) {
         CLIENT.player.getInventory().setSelectedSlot(i);
         CLIENT.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(i));
      }
   }

   private void invoke3(Vec3d vec3d) {
      Vec3d vec3d3 = vec3d.subtract(CLIENT.player.getEyePos());
      float floatValue = (float)Math.toDegrees(Math.atan2(-vec3d3.x, vec3d3.z));
      float floatValue2 = (float)(-Math.toDegrees(Math.atan2(vec3d3.y, Math.hypot(vec3d3.x, vec3d3.z))));
      RotationController.invoke3(new Rotation(floatValue, floatValue2), 180.0F, 180.0F, 180.0F, 180.0F, 1, 10, false);
   }

   @Override
   public void onDisable() {
      RotationController.rotationControllerState2 = RotationController.RotationControllerState2.IDLE;
      RotationController.intValue = 0;
      RotationController.rotation = null;
      FreeLookController.active = false;
      super.onDisable();
   }
}
