package ru.metaculture.protection;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "ServerJoiner",
   category = Category.Misc,
   description = "Авто-заход на сервера (FunTime/SpookyTime)"
)
public class ServerJoiner extends Module {
   public final ModeSetting rezhim = new ModeSetting("Режим", "FunTime", "FunTime", "SpookyTime");
   private static final Pattern PATTERN = Pattern.compile("anarchy\\s*(\\d+)");
   public final TextSetting anarhiya = new TextSetting("Анархия", "101").setVisibilityCondition(() -> !this.rezhim.is("FunTime"));
   public final BooleanSetting vyklyuchatPosleVhoda = new BooleanSetting("Выключать после входа", true);
   private final DualTimer dualTimer = new DualTimer();
   private int intValue = -1;
   private boolean flag;
   private boolean flag2;

   public ServerJoiner() {
      this.addSettings(new Setting[]{this.rezhim, this.anarhiya, this.vyklyuchatPosleVhoda});
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.flag = false;
      this.flag2 = false;
      this.dualTimer.invoke();
      if (this.rezhim.is("FunTime")) {
         this.intValue = this.compute();
         if (this.intValue <= 0) {
            ChatUtil.sendClientMessage("[ServerJoiner] Укажи корректную анархию в настройке.");
            this.toggle();
            return;
         }

         this.invoke3();
      }
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player != null && CLIENT.world != null && !this.flag) {
         if (this.rezhim.is("FunTime")) {
            if (this.dualTimer.check5(50L)) {
               this.invoke3();
               this.dualTimer.invoke();
            }
         } else if (this.rezhim.is("SpookyTime")) {
            if (this.flag2 && !this.check2()) {
               this.invoke4("[ServerJoiner] Успешный вход на дуэли SpookyTime.");
               return;
            }

            this.invoke();
         }
      }
   }

   @EventHandler
   public void onPacket(PacketEvent packetEvent) {
      if (this.rezhim.is("FunTime") && packetEvent.getPacketEventState().equals(PacketEvent.PacketEventState.RECEIVE)) {
         if (packetEvent.getPacket() instanceof GameMessageS2CPacket gameMessageS2CPacket) {
            String text = this.resolve(gameMessageS2CPacket.content().getString());
            if (!text.isEmpty() && !text.contains("сервер заполнен") && !text.contains("были кикнуты при подключении")) {
               if (this.check3(text)) {
                  this.invoke4("[ServerJoiner] Уже подключен к этой анархии.");
               } else {
                  Matcher matcher = PATTERN.matcher(text);
                  if (matcher.find()) {
                     try {
                        if (Integer.parseInt(matcher.group(1)) == this.intValue) {
                           this.invoke4("[ServerJoiner] Зашёл на /an" + this.intValue + ".");
                        }
                     } catch (NumberFormatException numberFormatException) {
                     }
                  }
               }
            }
         }
      }
   }

   private void invoke() {
      if (CLIENT.currentScreen instanceof HandledScreen handledScreen2) {
         if (!this.check(handledScreen2)) {
            ChatUtil.sendClientMessage("[ServerJoiner] Открыт неверный экран для SpookyTime, модуль выключен.");
            this.setEnabled(false);
            return;
         }

         ScreenHandler screenHandler = handledScreen2.getScreenHandler();

         for (int intValue = 0; intValue < screenHandler.slots.size(); intValue++) {
            Slot slot = (Slot)screenHandler.slots.get(intValue);
            if (slot.getStack().isOf(Items.RESPAWN_ANCHOR)) {
               CLIENT.interactionManager.clickSlot(screenHandler.syncId, intValue, 0, SlotActionType.PICKUP, CLIENT.player);
               CLIENT.setScreen(null);
               this.flag2 = true;
               this.dualTimer.invoke();
               return;
            }
         }
      } else if (this.dualTimer.check5(500L)) {
         this.invoke2();
         this.dualTimer.invoke();
      }
   }

   private boolean check(HandledScreen<?> handledScreen) {
      return this.resolve(handledScreen.getTitle().getString()).equals("выберите режим: ");
   }

   private void invoke2() {
      PlayerInventory playerInventory = CLIENT.player.getInventory();

      for (int intValue2 = 0; intValue2 < 9; intValue2++) {
         if (playerInventory.getStack(intValue2).isOf(Items.COMPASS)) {
            if (playerInventory.getSelectedSlot() != intValue2) {
               playerInventory.setSelectedSlot(intValue2);
               CLIENT.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(intValue2));
            }

            CLIENT.interactionManager.interactItem(CLIENT.player, Hand.MAIN_HAND);
            return;
         }
      }
   }

   private boolean check2() {
      if (CLIENT.player == null) {
         return false;
      } else {
         PlayerInventory playerInventory2 = CLIENT.player.getInventory();

         for (int intValue3 = 0; intValue3 < 9; intValue3++) {
            if (playerInventory2.getStack(intValue3).isOf(Items.COMPASS)) {
               return true;
            }
         }

         return false;
      }
   }

   private void invoke3() {
      if (CLIENT.player != null && CLIENT.player.networkHandler != null) {
         CLIENT.player.networkHandler.sendChatMessage("/an" + this.intValue);
      }
   }

   private void invoke4(String string) {
      this.flag = true;
      ChatUtil.sendClientMessage(string);
      if (this.vyklyuchatPosleVhoda.isEnabled()) {
         this.toggle();
      }
   }

   private int compute() {
      String text2 = this.anarhiya.getValue();
      if (text2 == null) {
         return -1;
      } else {
         String text3 = text2.replaceAll("\\D+", "");
         return text3.isEmpty() ? -1 : Integer.parseInt(text3);
      }
   }

   private String resolve(String string) {
      return string == null ? "" : string.replaceAll("§.", "").toLowerCase(Locale.ROOT).trim();
   }

   private boolean check3(String string) {
      return string.contains("вы уже подключены к этому серверу") || string.contains("already connected to this server");
   }
}
