package org.wild.mixin;

import baritone.api.BaritoneAPI;
import baritone.pathing.movement.MovementHelper;
import baritone.pathing.precompute.Ternary;
import java.util.List;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.block.AirBlock;
import net.minecraft.block.AmethystClusterBlock;
import net.minecraft.block.AzaleaBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarpetBlock;
import net.minecraft.block.CauldronBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SnowBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin({MovementHelper.class})
public interface MovementHelperMixin {
   @Overwrite
   static Ternary a(BlockState blockState) {
      Block block = blockState.getBlock();
      if (block instanceof AirBlock || block instanceof TrapdoorBlock) {
         return Ternary.a;
      } else if (block instanceof AbstractFireBlock
         || block == Blocks.TRIPWIRE
         || block == Blocks.COBWEB
         || block == Blocks.END_PORTAL
         || block == Blocks.COCOA
         || block instanceof AbstractSkullBlock
         || block == Blocks.BUBBLE_COLUMN
         || block instanceof ShulkerBoxBlock
         || block instanceof SlabBlock
         || block == Blocks.HONEY_BLOCK
         || block == Blocks.END_ROD
         || block == Blocks.SWEET_BERRY_BUSH
         || block == Blocks.POINTED_DRIPSTONE
         || block instanceof AmethystClusterBlock
         || block instanceof AzaleaBlock) {
         return Ternary.c;
      } else if (block == Blocks.BIG_DRIPLEAF) {
         return Ternary.c;
      } else if (block == Blocks.POWDER_SNOW) {
         return Ternary.c;
      } else if (((List)BaritoneAPI.getSettings().blocksToAvoid.value).contains(block)) {
         return Ternary.c;
      } else if (!(block instanceof DoorBlock) && !(block instanceof FenceGateBlock)) {
         if (block instanceof CarpetBlock) {
            return Ternary.b;
         } else if (block instanceof SnowBlock) {
            return Ternary.b;
         } else {
            FluidState fluidState = blockState.getFluidState();
            if (!fluidState.isEmpty()) {
               return fluidState.getFluid().getLevel(fluidState) != 8 ? Ternary.c : Ternary.b;
            } else if (block instanceof CauldronBlock) {
               return Ternary.c;
            } else {
               return blockState.canPathfindThrough(NavigationType.LAND) ? Ternary.a : Ternary.c;
            }
         }
      } else {
         return block == Blocks.IRON_DOOR ? Ternary.c : Ternary.a;
      }
   }
}
