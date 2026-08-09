package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "AutoClan",
   category = Category.Misc,
   description = "Автоматически создает клан а так же приглашает в него ваших друзей (если такие есть)"
)
public class AutoClan extends Module {
   public final KeybindSetting bind = new KeybindSetting("Бинд", -1);
   private AutoClan.AutoClanState autoClanState = AutoClan.AutoClanState.IDLE;
   private int intValue = 0;
   private int intValue2 = 0;
   private int intValue3 = 0;
   private List<String> items = new ArrayList<>();
   private static final String ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 = "abcdefghijklmnopqrstuvwxyz0123456789";

   public AutoClan() {
      this.addSettings(new Setting[]{this.bind});
   }

   @EventHandler
   public void onRawInput(RawInputEvent rawInputEvent) {
      if (!ServerModeDetector.check()) {
         if (rawInputEvent.getKeyCode() == this.bind.getKeyCode()) {
         }
      }
   }

   private void invoke() {
      if (CLIENT.player != null) {
         this.invoke2();
      }
   }

   private void invoke2() {
      String text = this.resolve();
      ChatUtil.sendClientMessage("§7Создание клана с названием: §f" + text);
      CLIENT.player.networkHandler.sendChatMessage("/clan create " + text);
      this.autoClanState = AutoClan.AutoClanState.WAITING_CREATE_RESPONSE;
      this.intValue = 0;
   }

   private String resolve() {
      ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
      int intValue = threadLocalRandom.nextInt(3, 6);
      StringBuilder stringBuilder = new StringBuilder(intValue);

      for (int intValue2 = 0; intValue2 < intValue; intValue2++) {
         stringBuilder.append("abcdefghijklmnopqrstuvwxyz0123456789".charAt(threadLocalRandom.nextInt("abcdefghijklmnopqrstuvwxyz0123456789".length())));
      }

      return stringBuilder.toString();
   }

   @EventHandler
   public void onPacket(PacketEvent packetEvent) {
      if (CLIENT.player != null && this.autoClanState != AutoClan.AutoClanState.IDLE) {
         if (packetEvent.getPacket() instanceof GameMessageS2CPacket gameMessageS2CPacket) {
            String text2 = gameMessageS2CPacket.content().getString();
            this.invoke3(text2);
         }
      }
   }

   private void invoke3(String string) {
      if (this.autoClanState == AutoClan.AutoClanState.WAITING_CREATE_RESPONSE) {
         if (string.contains("Ошибка, клан с таким названием уже существует")) {
            ChatUtil.sendClientMessage("§cНазвание занято, пробую другое...");
            this.intValue = 0;
            this.autoClanState = AutoClan.AutoClanState.CREATING_CLAN;
            return;
         }

         if (string.contains("Супер! Вы успешно создали клан")) {
            ChatUtil.sendClientMessage("§aКлан успешно создан!");
            this.invoke4();
            return;
         }

         if (string.contains("Ошибка: Ты уже состоишь в клане")) {
            ChatUtil.sendClientMessage("§eТы уже в клане, начинаю инвайтить друзей...");
            this.invoke4();
            return;
         }
      }

      if (this.autoClanState == AutoClan.AutoClanState.INVITING_FRIENDS) {
      }
   }

   private void invoke4() {
      this.items = this.resolve2();
      if (this.items.isEmpty()) {
         ChatUtil.sendClientMessage("§cНет друзей онлайн на этом сервере!");
         this.invoke5();
      } else {
         ChatUtil.sendClientMessage("§aНайдено §l" + this.items.size() + "§a друзей онлайн. Начинаю инвайт...");
         this.intValue2 = 0;
         this.intValue3 = 0;
         this.autoClanState = AutoClan.AutoClanState.INVITING_FRIENDS;
      }
   }

   private List<String> resolve2() {
      ArrayList arrayList = new ArrayList();
      if (CLIENT.player != null && CLIENT.getNetworkHandler() != null) {
         List items = FriendCommand.resolve();
         Collection items2 = CLIENT.getNetworkHandler().getPlayerList();
         HashSet hashSet = new HashSet();

         for (PlayerListEntry playerListEntry : (Collection<PlayerListEntry>)items2) {
            if (playerListEntry.getProfile() != null && playerListEntry.getProfile().getName() != null) {
               hashSet.add(playerListEntry.getProfile().getName());
            }
         }

         String text3 = CLIENT.player.getName().getString();

         for (String text4 : (List<String>)items) {
            if (hashSet.contains(text4) && !text4.equalsIgnoreCase(text3)) {
               arrayList.add(text4);
            }
         }

         return arrayList;
      } else {
         return arrayList;
      }
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player != null && this.autoClanState != AutoClan.AutoClanState.IDLE) {
         this.intValue++;
         switch (this.autoClanState) {
            case CREATING_CLAN:
               if (this.intValue > 20) {
                  this.invoke2();
               }
               break;
            case WAITING_CREATE_RESPONSE:
               if (this.intValue > 100) {
                  ChatUtil.sendClientMessage("§cТаймаут ожидания ответа сервера.");
                  this.invoke5();
               }
               break;
            case INVITING_FRIENDS:
               this.intValue3++;
               if (this.intValue3 >= 25) {
                  this.intValue3 = 0;
                  if (this.intValue2 < this.items.size()) {
                     String text5 = this.items.get(this.intValue2);
                     CLIENT.player.networkHandler.sendChatCommand("clan invite " + text5);
                     ChatUtil.sendClientMessage("§7Инвайт: §f" + text5 + " §7(" + (this.intValue2 + 1) + "/" + this.items.size() + ")");
                     this.intValue2++;
                  } else {
                     ChatUtil.sendClientMessage("§aВсе друзья приглашены! (" + this.items.size() + " шт.)");
                     this.invoke5();
                  }
               }
         }
      }
   }

   private void invoke5() {
      this.autoClanState = AutoClan.AutoClanState.IDLE;
      this.intValue = 0;
      this.intValue2 = 0;
      this.intValue3 = 0;
      this.items.clear();
   }

   @Override
   public void onDisable() {
      this.invoke5();
      super.onDisable();
   }

   static enum AutoClanState {
      IDLE,
      CREATING_CLAN,
      WAITING_CREATE_RESPONSE,
      INVITING_FRIENDS;
   }
}
