package ru.metaculture.protection;

import net.minecraft.network.packet.c2s.play.CommandExecutionC2SPacket;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "ChatHelper",
   description = "Обновляет параметры чата",
   category = Category.Misc
)
public class ChatHelper extends Module {
   public static final BooleanSetting ANTISPAM_CHAT = new BooleanSetting("Антиспам чат", true);
   public static final BooleanSetting SOHRANYAT_CHAT = new BooleanSetting("Сохранять чат", true);
   public static final BooleanSetting ULUCHSHENNYE_KOMANDY = new BooleanSetting("Улучшенные команды", true);
   public static final BooleanSetting RASSHIRENNYY_PROSMOTR_CHATA = new BooleanSetting("Расширенный просмотр чата ", true);

   public ChatHelper() {
      this.addSettings(new Setting[]{ANTISPAM_CHAT, SOHRANYAT_CHAT, ULUCHSHENNYE_KOMANDY, RASSHIRENNYY_PROSMOTR_CHATA});
   }

   @EventHandler
   public void onPacket(PacketEvent packetEvent) {
      if (CLIENT.player != null) {
         if (packetEvent.getPacket() instanceof CommandExecutionC2SPacket commandExecutionC2SPacket) {
            String text = null;
            String text2 = commandExecutionC2SPacket.command();
            String text3 = text2.toLowerCase();
            int intValue = text3.indexOf("ah");
            int intValue2 = text3.indexOf(" me", intValue);
            if (intValue2 != -1 && intValue != -1) {
               String text4 = CLIENT.player.getName().getString();
               text = text2.substring(0, intValue2) + " " + text4 + text2.substring(intValue2 + 3);
            }

            if (text3.startsWith("clan")) {
               ServerStatsParser serverStatsParser = new ServerStatsParser();
               serverStatsParser.invoke2();
               String text5 = serverStatsParser.getText0();
               if (text3.endsWith(" all") || text3.contains(" all")) {
                  text = text2.replaceAll("(?i)\\ball\\b", text5);
               }
            }

            if (text != null) {
               packetEvent.invalidate();
               CLIENT.player.networkHandler.sendPacket(new CommandExecutionC2SPacket(text));
            }
         }
      }
   }
}
