package ru.metaculture.protection;

import java.util.Optional;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.block.entity.DropperBlockEntity;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.chunk.WorldChunk;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "OpenWalls",
   category = Category.Player,
   description = "Открывает контейнеры через стены"
)
public class OpenWalls extends Module {
   private final NumberSetting distantsiya = new NumberSetting("Дистанция", 4.6F, 2.0F, 6.0F, 0.1F, false);
   private long timestamp;

   public OpenWalls() {
      this.addSettings(new Setting[]{this.distantsiya});
   }

   @EventHandler
   public void onMouseButtonPosition(MouseButtonPositionEvent mouseButtonPositionEvent) {
      if (mouseButtonPositionEvent.isPress() && mouseButtonPositionEvent.getButton() == 1) {
         if (CLIENT.player != null && CLIENT.world != null && CLIENT.interactionManager != null && CLIENT.currentScreen == null) {
            if (System.currentTimeMillis() - this.timestamp >= 120L) {
               BlockPos blockPos2 = this.resolve();
               if (blockPos2 != null) {
                  ActionResult actionResult = this.resolve2(blockPos2);
                  if (actionResult != ActionResult.FAIL) {
                     this.timestamp = System.currentTimeMillis();
                     mouseButtonPositionEvent.invalidate();
                  }
               }
            }
         }
      }
   }

   private BlockPos resolve() {
      Vec3d vec3d = CLIENT.player.getEyePos();
      Vec3d vec3d2 = CLIENT.player.getRotationVec(1.0F).normalize();
      Vec3d vec3d3 = vec3d.add(vec3d2.multiply(this.distantsiya.getValue()));
      ChunkPos chunkPos = CLIENT.player.getChunkPos();
      int intValue = Math.max(1, (int)Math.ceil(this.distantsiya.getValue() / 16.0F) + 1);
      BlockPos blockPos3 = null;
      double doubleValue = Double.MAX_VALUE;

      for (int intValue2 = chunkPos.x - intValue; intValue2 <= chunkPos.x + intValue; intValue2++) {
         for (int intValue3 = chunkPos.z - intValue; intValue3 <= chunkPos.z + intValue; intValue3++) {
            WorldChunk worldChunk = CLIENT.world.getChunk(intValue2, intValue3);
            if (worldChunk != null) {
               for (BlockEntity blockEntity2 : worldChunk.getBlockEntities().values()) {
                  if (this.check(blockEntity2)) {
                     BlockPos blockPos4 = blockEntity2.getPos();
                     if (!(CLIENT.player.squaredDistanceTo(Vec3d.ofCenter(blockPos4)) > this.distantsiya.getValue() * this.distantsiya.getValue())) {
                        Optional optional = new Box(blockPos4).expand(0.01).raycast(vec3d, vec3d3);
                        if (!optional.isEmpty()) {
                           double doubleValue2 = vec3d.squaredDistanceTo((Vec3d)optional.get());
                           if (doubleValue2 < doubleValue) {
                              doubleValue = doubleValue2;
                              blockPos3 = blockPos4.toImmutable();
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      return blockPos3;
   }

   private ActionResult resolve2(BlockPos blockPos) {
      Direction direction = this.resolve3(blockPos);
      Vec3d vec3d4 = new Vec3d(
         blockPos.getX() + 0.5 + direction.getOffsetX() * 0.5, blockPos.getY() + 0.5 + direction.getOffsetY() * 0.5, blockPos.getZ() + 0.5 + direction.getOffsetZ() * 0.5
      );
      BlockHitResult blockHitResult = new BlockHitResult(vec3d4, direction, blockPos, false);
      ActionResult actionResult2 = CLIENT.interactionManager.interactBlock(CLIENT.player, Hand.MAIN_HAND, blockHitResult);
      if (actionResult2 != ActionResult.FAIL) {
         CLIENT.player.swingHand(Hand.MAIN_HAND);
      }

      return actionResult2;
   }

   private Direction resolve3(BlockPos blockPos) {
      Vec3d vec3d5 = Vec3d.ofCenter(blockPos);
      Vec3d vec3d6 = CLIENT.player.getEyePos().subtract(vec3d5);
      return Direction.getFacing(vec3d6.x, vec3d6.y, vec3d6.z);
   }

   private boolean check(BlockEntity blockEntity) {
      return blockEntity instanceof ChestBlockEntity
         || blockEntity instanceof BarrelBlockEntity
         || blockEntity instanceof EnderChestBlockEntity
         || blockEntity instanceof ShulkerBoxBlockEntity
         || blockEntity instanceof HopperBlockEntity
         || blockEntity instanceof DispenserBlockEntity
         || blockEntity instanceof DropperBlockEntity
         || blockEntity instanceof AbstractFurnaceBlockEntity;
   }
}
