package ru.metaculture.protection;

import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Direction.Axis;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "Scaffold",
   description = "Ставит блоки под себя, пойдет под сервера с мини играми",
   category = Category.Misc
)
public class Scaffold extends Module {
   private BlockPos blockPos = null;
   private Direction direction = null;

   @Override
   public void onEnable() {
      this.blockPos = null;
      this.direction = null;
      super.onEnable();
   }

   @Override
   public void onDisable() {
      CLIENT.options.leftKey.setPressed(false);
      CLIENT.options.rightKey.setPressed(false);
      CLIENT.options.sneakKey.setPressed(false);
      super.onDisable();
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player != null && CLIENT.world != null) {
         this.invoke();
         this.invoke3();
         if (this.blockPos != null && this.direction != null && this.check2()) {
            RotationSmoothing.invoke2(this.blockPos, this.direction);
            if (this.check(this.blockPos, this.direction)) {
               this.invoke2(this.blockPos, this.direction);
            }
         }
      }
   }

   private void invoke() {
      BlockPos blockPos2 = BlockPos.ofFloored(CLIENT.player.getPos().add(0.0, -1.0, 0.0));
      if (!CLIENT.world.getBlockState(blockPos2).isReplaceable()) {
         this.blockPos = null;
         this.direction = null;
      } else {
         Direction[] directions = new Direction[]{Direction.DOWN, Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};

         for (Direction direction2 : directions) {
            BlockPos blockPos3 = blockPos2.offset(direction2);
            BlockState blockState = CLIENT.world.getBlockState(blockPos3);
            if (!blockState.isReplaceable() && blockState.getFluidState().isEmpty()) {
               this.blockPos = blockPos3;
               this.direction = direction2.getOpposite();
               return;
            }
         }
      }
   }

   private void invoke2(BlockPos blockPos, Direction direction) {
      Vec3d vec3d = CLIENT.player.getEyePos();
      double doubleValue = blockPos.getX() + 0.5 + direction.getOffsetX() * 0.5;
      double doubleValue2 = blockPos.getY() + 0.5 + direction.getOffsetY() * 0.5;
      double doubleValue3 = blockPos.getZ() + 0.5 + direction.getOffsetZ() * 0.5;
      if (direction.getAxis() != Axis.X) {
         doubleValue = MathHelper.clamp(vec3d.x, blockPos.getX() + 0.15, blockPos.getX() + 0.85);
      }

      if (direction.getAxis() != Axis.Y) {
         doubleValue2 = MathHelper.clamp(vec3d.y - 1.2, blockPos.getY() + 0.15, blockPos.getY() + 0.85);
      }

      if (direction.getAxis() != Axis.Z) {
         doubleValue3 = MathHelper.clamp(vec3d.z, blockPos.getZ() + 0.15, blockPos.getZ() + 0.85);
      }

      Vec3d vec3d2 = new Vec3d(doubleValue, doubleValue2, doubleValue3);
      BlockHitResult blockHitResult = new BlockHitResult(vec3d2, direction, blockPos, false);
      CLIENT.interactionManager.interactBlock(CLIENT.player, Hand.MAIN_HAND, blockHitResult);
      CLIENT.player.swingHand(Hand.MAIN_HAND);
      this.blockPos = null;
      this.direction = null;
   }

   private boolean check(BlockPos blockPos, Direction direction) {
      float floatValue = FreeLookController.floatValue;
      float floatValue2 = FreeLookController.floatValue2;
      Vec3d vec3d3 = CLIENT.player.getEyePos();
      Vec3d vec3d4 = this.resolve(floatValue2, floatValue);
      double doubleValue4 = blockPos.getX() + 0.5 + direction.getOffsetX() * 0.5;
      double doubleValue5 = blockPos.getY() + 0.5 + direction.getOffsetY() * 0.5;
      double doubleValue6 = blockPos.getZ() + 0.5 + direction.getOffsetZ() * 0.5;
      if (direction.getAxis() != Axis.X) {
         doubleValue4 = MathHelper.clamp(vec3d3.x, blockPos.getX() + 0.15, blockPos.getX() + 0.85);
      }

      if (direction.getAxis() != Axis.Y) {
         doubleValue5 = MathHelper.clamp(vec3d3.y - 1.2, blockPos.getY() + 0.15, blockPos.getY() + 0.85);
      }

      if (direction.getAxis() != Axis.Z) {
         doubleValue6 = MathHelper.clamp(vec3d3.z, blockPos.getZ() + 0.15, blockPos.getZ() + 0.85);
      }

      Vec3d vec3d5 = new Vec3d(doubleValue4, doubleValue5, doubleValue6).subtract(vec3d3).normalize();
      double doubleValue7 = vec3d4.dotProduct(vec3d5);
      return doubleValue7 > 0.95;
   }

   private void invoke3() {
      if (CLIENT.options.backKey.isPressed() && this.check2() && !CLIENT.options.jumpKey.isPressed()) {
         CLIENT.options.leftKey.setPressed(false);
         CLIENT.options.rightKey.setPressed(false);
         BlockPos blockPos4 = BlockPos.ofFloored(CLIENT.player.getX(), CLIENT.player.getY() - 0.5, CLIENT.player.getZ());
         boolean flag = CLIENT.world.getBlockState(blockPos4).isReplaceable();
         CLIENT.options.sneakKey.setPressed(flag);
      } else {
         CLIENT.options.leftKey.setPressed(CLIENT.options.leftKey.isPressed());
         CLIENT.options.rightKey.setPressed(CLIENT.options.rightKey.isPressed());
         CLIENT.options.sneakKey.setPressed(CLIENT.options.sneakKey.isPressed());
      }
   }

   private boolean check2() {
      return CLIENT.player.getMainHandStack().getItem() instanceof BlockItem || CLIENT.player.getOffHandStack().getItem() instanceof BlockItem;
   }

   private Vec3d resolve(float f, float g) {
      float floatValue3 = f * (float) (Math.PI / 180.0);
      float floatValue4 = -g * (float) (Math.PI / 180.0);
      float floatValue5 = MathHelper.cos(floatValue4);
      float floatValue6 = MathHelper.sin(floatValue4);
      float floatValue7 = MathHelper.cos(floatValue3);
      float floatValue8 = MathHelper.sin(floatValue3);
      return new Vec3d(floatValue6 * floatValue7, -floatValue8, floatValue5 * floatValue7);
   }
}
