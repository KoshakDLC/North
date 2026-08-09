package ru.metaculture.protection;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.LanternBlock;
import net.minecraft.block.LightningRodBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "Spider",
   category = Category.Movement,
   description = "Позволяет лазить по стенам",
   riskLevels = {ModuleRiskLevel.RISKY, ModuleRiskLevel.MATRIX}
)
public class Spider extends Module {
   public final ModeSetting rezhim = new ModeSetting("Режим", "FunTime", "FunTime");
   private final DualTimer dualTimer = new DualTimer();
   private final DualTimer dualTimer2 = new DualTimer();
   private final DualTimer dualTimer3 = new DualTimer();
   private final DualTimer dualTimer4 = new DualTimer();
   private final DualTimer dualTimer5 = new DualTimer();
   private boolean flag = true;

   public Spider() {
      this.addSettings(new Setting[]{this.rezhim});
   }

   @Override
   public void onDisable() {
      RotationController.rotationControllerState2 = RotationController.RotationControllerState2.IDLE;
      RotationController.intValue = 0;
      RotationController.rotation = null;
      FreeLookController.active = false;
      if (this.rezhim.is("SpookyTime") && CLIENT.options != null) {
         CLIENT.options.sneakKey.setPressed(false);
      }

      super.onDisable();
   }

   @EventHandler
   public void onPlayerMovePacket(PlayerMovePacketEvent playerMovePacketEvent) {
      if (!ServerModeDetector.check()) {
         boolean flag = CLIENT.player.horizontalCollision;
         boolean flag2 = flag && CLIENT.options.jumpKey.isPressed();
         if (this.rezhim.is("FunTime") || this.rezhim.is("FunTimeNew")) {
            this.invoke5(playerMovePacketEvent, flag2);
         }

         if (this.rezhim.is("FunTimeNew") && flag) {
            this.invoke6();
         }

         if (this.rezhim.is("FunTime v2") && flag2) {
            this.invoke7(playerMovePacketEvent);
         }

         if (this.rezhim.is("FunTime v3") && flag) {
            this.invoke(playerMovePacketEvent);
         }
      }
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (this.rezhim.is("SpookyTime")) {
         this.invoke3();
      }
   }

   private void invoke(PlayerMovePacketEvent playerMovePacketEvent2) {
      int intValue = this.compute(Items.SPRUCE_BUTTON);
      if (intValue != -1) {
         if (CLIENT.player.isOnGround()) {
            if (this.dualTimer4.check5(100L)) {
               CLIENT.player.jump();
               this.dualTimer4.invoke();
            }
         } else {
            if (CLIENT.player.fallDistance > 0.0 && CLIENT.player.fallDistance < 1.5) {
               playerMovePacketEvent2.setFlag(true);
               CLIENT.player.setOnGround(true);
               CLIENT.player.verticalCollision = true;
               this.invoke2(intValue);
               CLIENT.player.jump();
               CLIENT.player.fallDistance = 0.0;
            }
         }
      }
   }

   private void invoke2(int i) {
      float floatValue = Direction.getHorizontalDegreesOrThrow(CLIENT.player.getHorizontalFacing());
      float floatValue2 = 79.0F;
      Rotation rotation = new Rotation(floatValue, floatValue2);
      RotationController.invoke7(rotation, 360.0F, 360.0F, 10, 1);
      Vec3d vec3d = CLIENT.player.getCameraPosVec(1.0F);
      Vec3d vec3d2 = this.resolve(floatValue2, floatValue);
      Vec3d vec3d3 = vec3d.add(vec3d2.x * 4.0, vec3d2.y * 4.0, vec3d2.z * 4.0);
      BlockHitResult blockHitResult2 = CLIENT.world.raycast(new RaycastContext(vec3d, vec3d3, ShapeType.OUTLINE, FluidHandling.NONE, CLIENT.player));
      if (blockHitResult2 != null && blockHitResult2.getType() == Type.BLOCK) {
         this.invoke9(blockHitResult2, i);
      }
   }

   private void invoke3() {
      if (CLIENT.player != null && CLIENT.world != null) {
         if (!CLIENT.player.horizontalCollision) {
            if (CLIENT.options.sneakKey.isPressed()) {
               CLIENT.options.sneakKey.setPressed(false);
            }
         } else {
            int intValue2 = this.compute(Items.WATER_BUCKET);
            int intValue3 = this.compute(Items.BUCKET);
            if (intValue2 != -1 || intValue3 != -1) {
               if (CLIENT.player.isOnGround()) {
                  CLIENT.player.jump();
               } else {
                  Rotation rotation2 = new Rotation(CLIENT.player.getYaw(), 78.0F);
                  RotationController.invoke7(rotation2, 20.0F, 100.0F, 4, 1);
                  if (this.flag) {
                     this.invoke4(intValue2);
                     double doubleValue = 2.0 + Math.random() * 2.0;
                     Vec3d vec3d4 = CLIENT.player.getVelocity();
                     CLIENT.player.setVelocity(vec3d4.x, doubleValue, vec3d4.z);
                     this.flag = false;
                     this.dualTimer5.invoke();
                  }

                  if (this.dualTimer5.check5(200L)) {
                     if (CLIENT.player.isTouchingWater()) {
                        CLIENT.player.jump();
                        if (intValue3 != -1) {
                           this.invoke4(intValue3);
                        }
                     } else if (intValue2 != -1) {
                        this.invoke4(intValue2);
                     }

                     this.dualTimer5.invoke();
                  }

                  CLIENT.options.sneakKey.setPressed(true);
               }
            }
         }
      }
   }

   private void invoke4(int i) {
      int intValue4 = CLIENT.player.getInventory().getSelectedSlot();
      if (i != intValue4) {
         CLIENT.player.getInventory().setSelectedSlot(i);
         CLIENT.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(i));
      }

      CLIENT.interactionManager.interactItem(CLIENT.player, Hand.MAIN_HAND);
      CLIENT.player.swingHand(Hand.MAIN_HAND);
      if (i != intValue4) {
         CLIENT.player.getInventory().setSelectedSlot(intValue4);
         CLIENT.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(intValue4));
      }
   }

   private void invoke5(PlayerMovePacketEvent playerMovePacketEvent3, boolean bl) {
      BlockPos blockPos2 = BlockPos.ofFloored(CLIENT.player.getPos());
      BlockPos blockPos3 = blockPos2.offset(CLIENT.player.getHorizontalFacing());
      if (bl && (this.check(blockPos3) || this.check(blockPos2))) {
         playerMovePacketEvent3.setFlag(true);
         CLIENT.player.setOnGround(true);
         CLIENT.player.jump();
         CLIENT.player.fallDistance = 0.0;
         this.dualTimer.invoke();
      }
   }

   private boolean check(BlockPos blockPos) {
      BlockState blockState = CLIENT.world.getBlockState(blockPos);
      Block block = blockState.getBlock();
      boolean flag3 = block instanceof TrapdoorBlock && Boolean.TRUE.equals(blockState.get(Properties.OPEN)) && blockState.contains(Properties.HORIZONTAL_FACING);
      return block instanceof FenceBlock
         || blockState.isIn(BlockTags.WALLS)
         || block instanceof FenceGateBlock
         || block instanceof LanternBlock
         || block instanceof LightningRodBlock
         || flag3;
   }

   private void invoke6() {
      int intValue5 = this.compute(Items.LIGHTNING_ROD);
      if (intValue5 != -1) {
         Rotation rotation3 = new Rotation(CLIENT.player.getYaw(), 58.1F);
         RotationController.invoke7(rotation3, 80.0F, 80.0F, 10, 1);
         if (Math.abs(CLIENT.player.getPitch() - 57.1F) < 2.0F && CLIENT.crosshairTarget instanceof BlockHitResult blockHitResult3) {
            BlockPos blockPos4 = blockHitResult3.getBlockPos();
            if (blockHitResult3.getSide() == Direction.UP
               && !CLIENT.world.getBlockState(blockPos4).isReplaceable()
               && CLIENT.world.getBlockState(blockPos4.up()).isReplaceable()
               && this.dualTimer2.check5(50L)) {
               this.invoke9(blockHitResult3, intValue5);
               this.dualTimer2.invoke();
            }
         }
      }
   }

   private void invoke7(PlayerMovePacketEvent playerMovePacketEvent4) {
      if (this.dualTimer3.check5(400L)) {
         playerMovePacketEvent4.setFlag(true);
         CLIENT.player.setOnGround(true);
         CLIENT.player.verticalCollision = true;
         CLIENT.player.horizontalCollision = true;
         CLIENT.player.jump();
         this.dualTimer3.invoke();
         int intValue6 = this.compute(Items.COOKIE);
         if (intValue6 != -1 && CLIENT.player.fallDistance > 0.0 && CLIENT.player.fallDistance < 1.5) {
            this.invoke8(intValue6);
         }
      }
   }

   private void invoke8(int i) {
      float floatValue3 = Direction.getHorizontalDegreesOrThrow(CLIENT.player.getHorizontalFacing());
      float floatValue4 = 80.0F;
      Rotation rotation4 = new Rotation(floatValue3, floatValue4);
      RotationController.invoke7(rotation4, 100.0F, 100.0F, 10, 1);
      Vec3d vec3d5 = CLIENT.player.getCameraPosVec(1.0F);
      Vec3d vec3d6 = this.resolve(floatValue4, floatValue3);
      Vec3d vec3d7 = vec3d5.add(vec3d6.x * 4.0, vec3d6.y * 4.0, vec3d6.z * 4.0);
      BlockHitResult blockHitResult4 = CLIENT.world.raycast(new RaycastContext(vec3d5, vec3d7, ShapeType.OUTLINE, FluidHandling.NONE, CLIENT.player));
      if (blockHitResult4 != null && blockHitResult4.getType() == Type.BLOCK) {
         this.invoke9(blockHitResult4, i);
         CLIENT.player.fallDistance = 0.0;
      }
   }

   private void invoke9(BlockHitResult blockHitResult, int i) {
      int intValue7 = CLIENT.player.getInventory().getSelectedSlot();
      CLIENT.player.getInventory().setSelectedSlot(i);
      CLIENT.interactionManager.interactBlock(CLIENT.player, Hand.MAIN_HAND, blockHitResult);
      CLIENT.player.swingHand(Hand.MAIN_HAND);
      CLIENT.player.getInventory().setSelectedSlot(intValue7);
   }

   private int compute(Item item) {
      for (int intValue8 = 0; intValue8 < 9; intValue8++) {
         ItemStack itemStack = CLIENT.player.getInventory().getStack(intValue8);
         if (!itemStack.isEmpty() && itemStack.getItem() == item) {
            return intValue8;
         }
      }

      return -1;
   }

   private Vec3d resolve(float f, float g) {
      float floatValue5 = f * (float) (Math.PI / 180.0);
      float floatValue6 = -g * (float) (Math.PI / 180.0);
      float floatValue7 = MathHelper.cos(floatValue6);
      float floatValue8 = MathHelper.sin(floatValue6);
      float floatValue9 = MathHelper.cos(floatValue5);
      float floatValue10 = MathHelper.sin(floatValue5);
      return new Vec3d(floatValue8 * floatValue9, -floatValue10, floatValue7 * floatValue9);
   }
}
