package ru.metaculture.protection;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.pathing.goals.GoalBlock;
import lombok.Generated;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleAccess(
   usernames = {"lichoday", "bitrixtime", "oblamovvv"}
)
@ModuleRegister(
   name = "Test",
   category = Category.Player,
   description = "..."
)
public class TestModule extends Module {
   private final KeybindSetting ustanovkaTochki = new KeybindSetting("Установка точки", -1);
   private static BlockPos blockPos;
   private static BlockPos blockPos2;
   private BlockPos[] blockPosValues;
   private int intValue = 0;
   private int intValue2 = 0;
   private final ResettableTimer resettableTimer = new ResettableTimer();

   public TestModule() {
      this.addSettings(new Setting[]{this.ustanovkaTochki});
   }

   @Override
   public void onEnable() {
      this.invoke2();
      this.intValue = 0;
      super.onEnable();
   }

   @EventHandler
   public void onRawInput(RawInputEvent rawInputEvent) {
      if (rawInputEvent.getKeyCode() == this.ustanovkaTochki.getKeyCode() && this.resettableTimer.check3(300L)) {
         if (this.intValue2 == 0) {
            blockPos = CLIENT.player.getBlockPos();
            blockPos2 = null;
            this.blockPosValues = null;
            this.invoke3("Точка 1: " + blockPos.toShortString());
            this.intValue2 = 1;
         } else if (this.intValue2 == 1) {
            blockPos2 = CLIENT.player.getBlockPos();
            this.invoke3("Точка 2: " + blockPos2.toShortString());
            this.invoke2();
            this.intValue2 = 2;
         } else {
            blockPos = CLIENT.player.getBlockPos();
            blockPos2 = null;
            this.blockPosValues = null;
            BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().cancelEverything();
            this.invoke3("Сброс. Точка 1: " + blockPos.toShortString());
            this.intValue2 = 1;
         }

         this.resettableTimer.invoke();
      }
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player != null && CLIENT.world != null && this.blockPosValues != null && this.blockPosValues.length != 0) {
         IBaritone iBaritone2 = BaritoneAPI.getProvider().getPrimaryBaritone();
         this.invoke(iBaritone2);
      }
   }

   private void invoke(IBaritone iBaritone) {
      BlockPos blockPos2 = this.blockPosValues[this.intValue];
      double doubleValue = CLIENT.player.squaredDistanceTo(blockPos2.getX() + 0.5, blockPos2.getY(), blockPos2.getZ() + 0.5);
      if (doubleValue < 2.0) {
         this.intValue = (this.intValue + 1) % this.blockPosValues.length;
         blockPos2 = this.blockPosValues[this.intValue];
      }

      iBaritone.getCustomGoalProcess().setGoalAndPath(new GoalBlock(blockPos2));
   }

   private void invoke2() {
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

   private void invoke3(String string) {
      if (CLIENT.player != null) {
         CLIENT.player.sendMessage(Text.of("§7[§bTestModule§7] §f" + string), false);
      }
   }

   @Override
   public void onDisable() {
      BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().cancelEverything();
      super.onDisable();
   }

   @Generated
   public static BlockPos getBlockPos() {
      return blockPos;
   }

   @Generated
   public static void setBlockPos(BlockPos blockPos) {
      TestModule.blockPos = blockPos;
   }

   @Generated
   public static BlockPos getBlockPos2() {
      return blockPos2;
   }

   @Generated
   public static void setBlockPos2(BlockPos blockPos) {
      blockPos2 = blockPos;
   }
}
