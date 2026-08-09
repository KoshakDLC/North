package ru.metaculture.protection;

import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "AutoAuth",
   description = "Авто регистр/логин на серверах",
   category = Category.Misc
)
public class AutoAuth extends Module {
   public final TextSetting pishiteSyudaVashPorol = new TextSetting("Пишите сюда ваш пороль", "");

   public AutoAuth() {
      this.addSettings(new Setting[]{this.pishiteSyudaVashPorol});
   }

   @EventHandler
   public void onPacket(PacketEvent packetEvent) {
      if (!ServerModeDetector.check() && CLIENT.world != null) {
         if (packetEvent.getPacket() instanceof GameMessageS2CPacket gameMessageS2CPacket) {
            String text = gameMessageS2CPacket.content().getString();
            String text2 = this.pishiteSyudaVashPorol.getValue();
            if ((text.contains("Войдите") || text.contains("/login")) && CLIENT.player.networkHandler != null) {
               CLIENT.player.networkHandler.sendChatCommand("login " + text2);
            }

            if ((text.contains("Зарегистрируйтесь") || text.contains("/reg"))
               && text2 != null
               && text2.length() >= 4
               && CLIENT.player.networkHandler != null) {
               CLIENT.player.networkHandler.sendChatCommand("reg " + text2 + " " + text2);
            }
         }
      }
   }
}
