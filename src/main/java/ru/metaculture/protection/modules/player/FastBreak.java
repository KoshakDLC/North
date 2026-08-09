package ru.metaculture.protection;

import net.minecraft.block.BlockState;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "FastBreak",
   category = Category.Player,
   description = "Быстрая поломка блоков"
)
public class FastBreak extends Module {
   public final NumberSetting skorost = new NumberSetting("Скорость", 0.5F, 0.1F, 1.0F, 0.1F, false);

   public FastBreak() {
      this.addSettings(new Setting[]{this.skorost});
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player != null && CLIENT.world != null && CLIENT.interactionManager != null) {
         if (CLIENT.options.attackKey.isPressed()) {
            if (CLIENT.crosshairTarget instanceof BlockHitResult blockHitResult) {
               BlockPos blockPos = blockHitResult.getBlockPos();
               BlockState blockState = CLIENT.world.getBlockState(blockPos);
               if (blockState != null && !blockState.isAir()) {
                  Direction direction = blockHitResult.getSide();
                  float floatValue = this.skorost.getValue();
                  if (floatValue > 4.0F) {
                     CLIENT.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(Action.START_DESTROY_BLOCK, blockPos, direction));
                     CLIENT.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(Action.STOP_DESTROY_BLOCK, blockPos, direction));
                     CLIENT.player.swingHand(Hand.MAIN_HAND);
                     CLIENT.interactionManager.cancelBlockBreaking();
                  } else {
                     int intValue = (int)(floatValue * 50.85F);

                     for (int intValue2 = 0; intValue2 < intValue; intValue2++) {
                        CLIENT.interactionManager.updateBlockBreakingProgress(blockPos, direction);
                     }

                     CLIENT.player.swingHand(Hand.MAIN_HAND);
                  }
               }
            }
         }
      }
   }
}
