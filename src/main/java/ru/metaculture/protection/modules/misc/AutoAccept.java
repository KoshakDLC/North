package ru.metaculture.protection;

import java.util.Arrays;
import java.util.List;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Text;
import net.minecraft.text.ClickEvent.RunCommand;
import net.minecraft.text.ClickEvent.SuggestCommand;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "AutoAccept",
   category = Category.Misc,
   description = "Автоматически принимает запросы на телепортацию и в клан"
)
public class AutoAccept extends Module {
   public final GroupSetting prinimat = new GroupSetting("Принимать", new BooleanSetting("Запрос на ТП", true), new BooleanSetting("Запрос в клан", true));
   public final ModeSetting prinimatTpOt = new ModeSetting("Принимать ТП от", "Друзей", "Друзей", "Всех")
      .setVisibilityCondition(() -> !this.prinimat.isEnabled("Запрос на ТП"));
   public final BooleanSetting prinimatZaprosVKlanTolkoOtDruzey = new BooleanSetting("Принимать запрос в клан только от друзей", true)
      .visibleWhen(() -> !this.prinimat.isEnabled("Запрос в клан"));
   private boolean flag;
   private boolean flag2 = false;
   private long timestamp = 0L;
   private final String[] hasRequestedTeleport = new String[]{
      "has requested teleport", "просит телепортироваться", "хочет телепортироваться к вам", "просит к вам телепортироваться"
   };
   private final String[] priglashaetVasVKlan = new String[]{"приглашает вас в клан", "приглашает Вас в клан", "invited you to clan"};

   public AutoAccept() {
      this.addSettings(new Setting[]{this.prinimat, this.prinimatTpOt, this.prinimatZaprosVKlanTolkoOtDruzey});
   }

   @EventHandler
   public void onPacket(PacketEvent packetEvent) {
      ServerModeDetector.check();
      if (packetEvent.getPacket() instanceof GameMessageS2CPacket gameMessageS2CPacket) {
         Text text2 = gameMessageS2CPacket.content();
         String text3 = text2.getString();
         if (this.flag2) {
            if (System.currentTimeMillis() - this.timestamp > 5000L) {
               this.flag2 = false;
            } else {
               String text4 = this.resolve(text2, "Вступить");
               if (text4 != null) {
                  this.invoke(text4);
                  this.flag2 = false;
                  return;
               }
            }
         }

         if (this.prinimat.isEnabled("Запрос на ТП") && this.check2(text3)) {
            if (this.prinimatTpOt.is("Всех")) {
               this.flag = true;
            } else {
               String text5 = text3.toLowerCase();

               for (String text6 : FriendCommand.resolve()) {
                  if (text5.contains(text6.toLowerCase())) {
                     this.flag = true;
                     break;
                  }
               }
            }
         }

         if (this.prinimat.isEnabled("Запрос в клан") && this.check3(text3)) {
            String text7 = this.resolve2(text3, "приглашает");
            if (text7 != null && !this.check(text7)) {
               return;
            }

            String text8 = this.resolve(text2, "Вступить");
            if (text8 != null) {
               this.invoke(text8);
            } else {
               this.flag2 = true;
               this.timestamp = System.currentTimeMillis();
            }
         }
      }
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (this.flag && CLIENT.player != null) {
         CLIENT.player.networkHandler.sendChatCommand("tpaccept");
         this.flag = false;
      }
   }

   private String resolve(Text text, String string) {
      if (text == null) {
         return null;
      } else {
         if (text.getStyle() != null && text.getStyle().getClickEvent() != null) {
            ClickEvent clickEvent = text.getStyle().getClickEvent();
            if (text.getString().contains(string)) {
               if (clickEvent instanceof RunCommand runCommand) {
                  return runCommand.command();
               }

               if (clickEvent instanceof SuggestCommand suggestCommand) {
                  return suggestCommand.command();
               }
            }
         }

         List items = text.getSiblings();
         if (items != null) {
            for (Text text9 : (List<Text>)items) {
               String text10 = this.resolve(text9, string);
               if (text10 != null) {
                  return text10;
               }
            }
         }

         return null;
      }
   }

   private void invoke(String string) {
      if (CLIENT.player != null && CLIENT.player.networkHandler != null) {
         if (string.startsWith("/")) {
            CLIENT.player.networkHandler.sendChatCommand(string.substring(1));
         } else {
            CLIENT.player.networkHandler.sendChatCommand(string);
         }
      }
   }

   private boolean check(String string) {
      return !this.prinimatZaprosVKlanTolkoOtDruzey.isEnabled() ? true : FriendCommand.check(string);
   }

   private String resolve2(String string, String string2) {
      String text11 = string.replaceAll("§.", "");
      int intValue = text11.indexOf(string2);
      if (intValue <= 0) {
         return null;
      } else {
         String text12 = text11.substring(0, intValue).trim();
         int intValue2 = text12.lastIndexOf(32);
         return intValue2 >= 0 ? text12.substring(intValue2 + 1).trim() : text12.trim();
      }
   }

   private boolean check2(String string) {
      String text13 = string.toLowerCase();
      return Arrays.stream(this.hasRequestedTeleport).anyMatch(string2 -> text13.contains(string2.toLowerCase()));
   }

   private boolean check3(String string) {
      String text14 = string.toLowerCase();
      return Arrays.stream(this.priglashaetVasVKlan).anyMatch(string2 -> text14.contains(string2.toLowerCase()));
   }
}
