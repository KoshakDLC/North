package ru.metaculture.protection;

import net.minecraft.item.BowItem;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "FastBow",
   category = Category.Combat,
   description = "Автоматический спам стрелами"
)
public class FastBow extends Module {
   private final NumberSetting zaderzhka = new NumberSetting("Задержка", 10.0F, 1.0F, 10.0F, 1.0F, false);

   public FastBow() {
      this.addSettings(new Setting[]{this.zaderzhka});
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (!this.check()) {
         if (this.check2()) {
            this.invoke();
            CLIENT.player.stopUsingItem();
         }
      }
   }

   private boolean check() {
      return CLIENT.player == null || CLIENT.world == null || CLIENT.getNetworkHandler() == null;
   }

   private boolean check2() {
      boolean flag = CLIENT.player.getMainHandStack().getItem() instanceof BowItem;
      boolean flag2 = CLIENT.player.isUsingItem();
      return flag && flag2 ? CLIENT.player.getItemUseTime() >= this.zaderzhka.getValue() : false;
   }

   private void invoke() {
      PlayerActionC2SPacket playerActionC2SPacket = new PlayerActionC2SPacket(Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, Direction.DOWN);
      PlayerInteractItemC2SPacket playerInteractItemC2SPacket = new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, 0, CLIENT.player.getYaw(), CLIENT.player.getPitch());
      CLIENT.getNetworkHandler().sendPacket(playerActionC2SPacket);
      CLIENT.getNetworkHandler().sendPacket(playerInteractItemC2SPacket);
   }
}
