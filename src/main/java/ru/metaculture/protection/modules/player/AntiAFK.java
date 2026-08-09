package ru.metaculture.protection;

import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "AntiAFK",
   category = Category.Player,
   description = "Убирает вылет при входе в режим AFK"
)
public class AntiAFK extends Module {
   public static BooleanSetting kruzhitsya = new BooleanSetting("Кружится", false);
   public static BooleanSetting prygat = new BooleanSetting("Прыгать", true);
   public static BooleanSetting otpravlyatSoobscheniya = new BooleanSetting("Отправлять сообщения", true);

   public AntiAFK() {
      this.addSettings(new Setting[]{otpravlyatSoobscheniya, prygat, kruzhitsya});
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player.getHealth() > 0.0F) {
         if (kruzhitsya.isEnabled() && CLIENT.player.age % 60 == 0) {
            CLIENT.player.setYaw(CLIENT.player.getYaw() + 300.0F);
         }

         if (prygat.isEnabled() && CLIENT.player.age % 40 == 0 && !CLIENT.options.jumpKey.isPressed() && CLIENT.player.isOnGround()) {
            CLIENT.player.jump();
         }

         if (otpravlyatSoobscheniya.isEnabled() && CLIENT.player.age % 400 == 0) {
            CLIENT.player.networkHandler.sendChatCommand("ak1");
         }
      }
   }
}
