package ru.metaculture.protection;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.Settings;
import baritone.api.pathing.goals.GoalBlock;
import baritone.api.pathing.goals.GoalXZ;
import java.util.List;
import lombok.Generated;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "CreeperFarm",
   category = Category.Misc,
   description = "Автоматический фарм криперов"
)
public class CreeperFarm extends Module {
   private static BlockPos blockPos;
   private static BlockPos blockPos2;
   private static final double DOUBLE_VALUE = 3.5;
   private static final double DOUBLE_VALUE_2 = 15.0;
   private static final double DOUBLE_VALUE_3 = 4.0;
   private static final long TIMESTAMP = 500L;
   private CreeperFarm.CreeperFarmState creeperFarmState = CreeperFarm.CreeperFarmState.SEARCH;
   private final Stopwatch stopwatch = new Stopwatch();
   private BlockPos[] blockPosValues;
   private int intValue = 0;

   public static void invoke() {
      blockPos = null;
      blockPos2 = null;
   }

   @Override
   public void onEnable() {
      if (CLIENT.player != null && CLIENT.world != null) {
         Settings settings = BaritoneAPI.getSettings();
         settings.allowPlace.value = false;
         settings.allowBreak.value = false;
         settings.legitMine.value = true;
         this.invoke3();
         this.intValue = 0;
         this.creeperFarmState = CreeperFarm.CreeperFarmState.SEARCH;
         this.stopwatch.invoke();
         super.onEnable();
      }
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player != null && CLIENT.world != null && blockPos != null && blockPos2 != null) {
         IBaritone iBaritone2 = BaritoneAPI.getProvider().getPrimaryBaritone();
         if (PlayerHelper.check()) {
            iBaritone2.getPathingBehavior().cancelEverything();
         } else {
            CreeperEntity creeperEntity2 = this.resolve2();
            if (creeperEntity2 != null) {
               Vec3d vec3d2 = CLIENT.player.getPos().subtract(creeperEntity2.getPos()).normalize();
               Vec3d vec3d3 = CLIENT.player.getPos().add(vec3d2.multiply(15.0));
               this.creeperFarmState = CreeperFarm.CreeperFarmState.RETREAT;
               this.invoke4(iBaritone2, vec3d3);
               this.invoke5(vec3d3);
            } else {
               ItemEntity itemEntity2 = this.resolve();
               if (itemEntity2 != null) {
                  this.creeperFarmState = CreeperFarm.CreeperFarmState.LOOTING;
                  Vec3d vec3d4 = itemEntity2.getPos();
                  this.invoke4(iBaritone2, vec3d4);
                  this.invoke5(vec3d4);
               } else {
                  CreeperEntity creeperEntity3 = this.resolve3();
                  if (creeperEntity3 != null) {
                     double doubleValue = CLIENT.player.distanceTo(creeperEntity3);
                     if (doubleValue <= 3.5) {
                        this.creeperFarmState = CreeperFarm.CreeperFarmState.ATTACK;
                        iBaritone2.getPathingBehavior().cancelEverything();
                        if (this.stopwatch.check(500.0)) {
                           CLIENT.interactionManager.attackEntity(CLIENT.player, creeperEntity3);
                           CLIENT.player.swingHand(Hand.MAIN_HAND);
                           this.stopwatch.invoke();
                        }
                     } else {
                        this.creeperFarmState = CreeperFarm.CreeperFarmState.APPROACH;
                        Vec3d vec3d5 = creeperEntity3.getPos();
                        this.invoke4(iBaritone2, vec3d5);
                        this.invoke5(vec3d5);
                     }
                  } else {
                     this.invoke2(iBaritone2);
                  }
               }
            }
         }
      }
   }

   private ItemEntity resolve() {
      ItemEntity itemEntity3 = this.resolve4(Items.GUNPOWDER);
      if (itemEntity3 == null) {
         itemEntity3 = this.resolve4(Items.EXPERIENCE_BOTTLE);
      }

      if (itemEntity3 != null) {
         List items = CLIENT.world.getEntitiesByClass(CreeperEntity.class, itemEntity3.getBoundingBox().expand(4.0), creeperEntity -> true);
         if (items.isEmpty()) {
            return itemEntity3;
         }
      }

      return null;
   }

   private void invoke2(IBaritone iBaritone) {
      if (this.blockPosValues != null && this.blockPosValues.length != 0) {
         BlockPos blockPos2 = this.blockPosValues[this.intValue];
         double doubleValue2 = CLIENT.player.squaredDistanceTo(blockPos2.getX() + 0.5, blockPos2.getY(), blockPos2.getZ() + 0.5);
         if (doubleValue2 < 2.0) {
            this.intValue = (this.intValue + 1) % this.blockPosValues.length;
            blockPos2 = this.blockPosValues[this.intValue];
         }

         this.creeperFarmState = CreeperFarm.CreeperFarmState.PATROL;
         iBaritone.getCustomGoalProcess().setGoalAndPath(new GoalBlock(blockPos2));
         this.invoke5(new Vec3d(blockPos2.getX() + 0.5, blockPos2.getY(), blockPos2.getZ() + 0.5));
      }
   }

   private void invoke3() {
      if (blockPos != null && blockPos2 != null) {
         int intValue = Math.min(blockPos.getX(), blockPos2.getX());
         int intValue2 = Math.max(blockPos.getX(), blockPos2.getX());
         int intValue3 = Math.min(blockPos.getZ(), blockPos2.getZ());
         int intValue4 = Math.max(blockPos.getZ(), blockPos2.getZ());
         int intValue5 = (int)CLIENT.player.getY();
         this.blockPosValues = new BlockPos[]{
            new BlockPos(intValue, intValue5, intValue3), new BlockPos(intValue2, intValue5, intValue3), new BlockPos(intValue2, intValue5, intValue4), new BlockPos(intValue, intValue5, intValue4)
         };
      }
   }

   private void invoke4(IBaritone iBaritone, Vec3d vec3d) {
      iBaritone.getCustomGoalProcess().setGoalAndPath(new GoalXZ((int)vec3d.x, (int)vec3d.z));
   }

   private void invoke5(Vec3d vec3d) {
      if (vec3d != null) {
         double doubleValue3 = vec3d.x - CLIENT.player.getX();
         double doubleValue4 = vec3d.z - CLIENT.player.getZ();
         float floatValue = Math.abs(doubleValue3) > Math.abs(doubleValue4) ? (doubleValue3 > 0.0 ? -90.0F : 90.0F) : (doubleValue4 > 0.0 ? 0.0F : 180.0F);
         float floatValue2 = floatValue + (float)(Math.random() * 4.0 - 2.0);
      }
   }

   private void invoke6() {
      BlockPos blockPos3 = BlockPos.ofFloored(
         CLIENT.player.getX(), CLIENT.player.getY() + CLIENT.player.getStandingEyeHeight(), CLIENT.player.getZ()
      );
      if ((
            CLIENT.world.getBlockState(blockPos3).getBlock() instanceof TrapdoorBlock
               || CLIENT.world.getBlockState(blockPos3.up()).getBlock() instanceof TrapdoorBlock
         )
         && CLIENT.player.isOnGround()) {
      }
   }

   private CreeperEntity resolve2() {
      for (CreeperEntity creeperEntity4 : CLIENT.world
         .getEntitiesByClass(CreeperEntity.class, CLIENT.player.getBoundingBox().expand(15.0), creeperEntity -> true)) {
         if (creeperEntity4.isAlive() && creeperEntity4.getFuseSpeed() > 0) {
            return creeperEntity4;
         }
      }

      return null;
   }

   private CreeperEntity resolve3() {
      Box box = Box.enclosing(blockPos, blockPos2).expand(1.0);
      List items2 = CLIENT.world.getEntitiesByClass(CreeperEntity.class, box, creeperEntity -> true);
      CreeperEntity creeperEntity5 = null;
      double doubleValue5 = Double.MAX_VALUE;

      for (CreeperEntity creeperEntity6 : (List<CreeperEntity>)items2) {
         if (creeperEntity6.isAlive()) {
            double doubleValue6 = CLIENT.player.distanceTo(creeperEntity6);
            if (doubleValue6 < doubleValue5) {
               doubleValue5 = doubleValue6;
               creeperEntity5 = creeperEntity6;
            }
         }
      }

      return creeperEntity5;
   }

   private ItemEntity resolve4(Item item) {
      Box box2 = Box.enclosing(blockPos, blockPos2).expand(1.0);
      List items3 = CLIENT.world.getEntitiesByClass(ItemEntity.class, box2, itemEntity -> true);
      ItemEntity itemEntity4 = null;
      double doubleValue7 = Double.MAX_VALUE;

      for (ItemEntity itemEntity5 : (List<ItemEntity>)items3) {
         if (itemEntity5.isAlive() && itemEntity5.getStack().getItem() == item) {
            double doubleValue8 = CLIENT.player.distanceTo(itemEntity5);
            if (doubleValue8 < doubleValue7) {
               doubleValue7 = doubleValue8;
               itemEntity4 = itemEntity5;
            }
         }
      }

      return itemEntity4;
   }

   @Override
   public void onDisable() {
      BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().cancelEverything();
      Settings settings2 = BaritoneAPI.getSettings();
      settings2.allowPlace.value = true;
      settings2.allowBreak.value = true;
      settings2.legitMine.value = false;
      super.onDisable();
   }

   @Generated
   public static BlockPos getBlockPos() {
      return blockPos;
   }

   @Generated
   public static void setBlockPos(BlockPos blockPos) {
      CreeperFarm.blockPos = blockPos;
   }

   @Generated
   public static BlockPos getBlockPos2() {
      return blockPos2;
   }

   @Generated
   public static void setBlockPos2(BlockPos blockPos) {
      blockPos2 = blockPos;
   }

   static enum CreeperFarmState {
      SEARCH,
      APPROACH,
      ATTACK,
      RETREAT,
      PATROL,
      LOOTING;
   }
}
