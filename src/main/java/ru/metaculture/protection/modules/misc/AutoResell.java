package ru.metaculture.protection;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "AutoResell",
   category = Category.Misc,
   description = "Автоматически перевыставляет товары"
)
public class AutoResell extends Module {
   public static AutoResell instance;
   private final DualTimer dualTimer = new DualTimer();
   private final DualTimer dualTimer2 = new DualTimer();
   private final DualTimer dualTimer3 = new DualTimer();
   private static final long TIMESTAMP = 900L;
   private static final long TIMESTAMP_2 = 9000L;
   private static final long TIMESTAMP_3 = 12000L;
   private final Pattern pattern = Pattern.compile("Подождите (\\d+) сек");
   private AutoResell.AutoResellState autoResellState = AutoResell.AutoResellState.WAITING;
   private long timestamp = 0L;

   public AutoResell() {
      instance = this;
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.autoResellState = AutoResell.AutoResellState.WAITING;
      this.dualTimer.invoke();
      this.dualTimer2.invoke();
      this.dualTimer3.invoke();
   }

   @Override
   public void onDisable() {
      super.onDisable();
      FunTimeAuctionHelper.invoke4(false);
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player != null && CLIENT.world != null && CLIENT.interactionManager != null) {
         switch (this.autoResellState) {
            case WAITING:
               if (this.dualTimer.check5(60000L) && FunTimeAuctionHelper.check5()) {
                  this.invoke();
               }
               break;
            case OPENING_MAIN_AH:
               if (this.check()) {
                  if (this.dualTimer2.check5(200L)) {
                     this.invoke3(AutoResell.AutoResellState.CLICKING_STORAGE);
                  }
               } else {
                  if (this.check5() && this.dualTimer3.check5(900L)) {
                     this.invoke4();
                     this.dualTimer3.invoke();
                  }

                  if (this.dualTimer2.check5(this.check5() ? 9000L : 5000L)) {
                     ChatUtil.sendClientMessage("§c[AutoResell] §fМеню аукциона не открылось.");
                     this.invoke2();
                  }
               }
               break;
            case CLICKING_STORAGE:
               if (this.check() && CLIENT.currentScreen instanceof GenericContainerScreen genericContainerScreen2) {
                  if (this.check3(genericContainerScreen2)) {
                     this.invoke3(AutoResell.AutoResellState.OPENING_STORAGE);
                  } else if (!this.check5() || this.dualTimer2.check5(3000L)) {
                     ChatUtil.sendClientMessage("§c[AutoResell] §fКнопка 'Хранилище' не найдена.");
                     this.invoke2();
                  }
                  break;
               }

               this.invoke2();
               break;
            case OPENING_STORAGE:
               if (this.check2()) {
                  if (this.dualTimer2.check5(200L)) {
                     this.invoke3(AutoResell.AutoResellState.CLICKING_CLOCK);
                  }
               } else {
                  if (this.check5()
                     && this.check()
                     && CLIENT.currentScreen instanceof GenericContainerScreen genericContainerScreen3
                     && this.dualTimer3.check5(900L)) {
                     this.check3(genericContainerScreen3);
                     this.dualTimer3.invoke();
                  }

                  if (this.dualTimer2.check5(this.check5() ? 9000L : 5000L)) {
                     ChatUtil.sendClientMessage("§c[AutoResell] §fХранилище не открылось.");
                     this.invoke2();
                  }
               }
               break;
            case CLICKING_CLOCK:
               if (this.check2() && CLIENT.currentScreen instanceof GenericContainerScreen genericContainerScreen4) {
                  if (this.check4(genericContainerScreen4)) {
                     ChatUtil.sendClientMessage("§d[AutoResell] §fПеревыставляем предметы...");
                     this.invoke3(AutoResell.AutoResellState.WAITING_RESULT);
                  } else if (!this.check5() || this.dualTimer2.check5(3500L)) {
                     FunTimeAuctionHelper.invoke10();
                     this.invoke2();
                  }
                  break;
               }

               this.invoke2();
               break;
            case WAITING_RESULT:
               if (this.check5()
                  && this.check2()
                  && CLIENT.currentScreen instanceof GenericContainerScreen genericContainerScreen5
                  && this.dualTimer3.check5(900L)) {
                  this.check4(genericContainerScreen5);
                  this.dualTimer3.invoke();
               }

               if (this.dualTimer2.check5(this.check5() ? 12000L : 10000L)) {
                  ChatUtil.sendClientMessage("§e[AutoResell] §fНет ответа от аукциона, возвращаю AutoBuy.");
                  this.invoke2();
               }
               break;
            case COOLDOWN_WAIT:
               if (this.dualTimer.check5(this.timestamp) && FunTimeAuctionHelper.check5()) {
                  ChatUtil.sendClientMessage("§d[AutoResell] §fПовторная попытка после ожидания...");
                  this.invoke();
               }
         }
      }
   }

   private void invoke() {
      if (this.check2()) {
         this.invoke3(AutoResell.AutoResellState.CLICKING_CLOCK);
      } else if (this.check()) {
         this.invoke3(AutoResell.AutoResellState.CLICKING_STORAGE);
      } else if (CLIENT.player != null) {
         this.invoke4();
         this.invoke3(AutoResell.AutoResellState.OPENING_MAIN_AH);
      }
   }

   private void invoke2() {
      this.autoResellState = AutoResell.AutoResellState.WAITING;
      this.dualTimer.invoke();
      this.dualTimer2.invoke();
      this.dualTimer3.invoke();
      FunTimeAuctionHelper.invoke4(true);
   }

   private void invoke3(AutoResell.AutoResellState autoResellState) {
      this.autoResellState = autoResellState;
      this.dualTimer2.invoke();
      this.dualTimer3.invoke();
   }

   private void invoke4() {
      if (CLIENT.player != null) {
         CLIENT.player.networkHandler.sendChatCommand("ah");
      }
   }

   private boolean check() {
      if (!(CLIENT.currentScreen instanceof GenericContainerScreen genericContainerScreen6)) {
         return false;
      } else {
         String text = genericContainerScreen6.getTitle().getString();
         return text != null && (text.contains("Аукцион") || text.contains("Auction"));
      }
   }

   private boolean check2() {
      if (!(CLIENT.currentScreen instanceof GenericContainerScreen genericContainerScreen7)) {
         return false;
      } else {
         String text2 = genericContainerScreen7.getTitle().getString();
         return text2 != null && text2.contains("Хранилище");
      }
   }

   private boolean check3(GenericContainerScreen genericContainerScreen) {
      int intValue = this.compute(genericContainerScreen, Items.ENDER_CHEST);
      if (intValue == -1) {
         return false;
      } else {
         CLIENT.interactionManager
            .clickSlot(((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).syncId, intValue, 0, SlotActionType.PICKUP, CLIENT.player);
         return true;
      }
   }

   private boolean check4(GenericContainerScreen genericContainerScreen) {
      int intValue2 = this.compute(genericContainerScreen, Items.CLOCK);
      if (intValue2 == -1) {
         return false;
      } else {
         CLIENT.interactionManager
            .clickSlot(((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).syncId, intValue2, 0, SlotActionType.PICKUP, CLIENT.player);
         return true;
      }
   }

   private boolean check5() {
      return AutoBuy.instance != null && AutoBuy.instance.rezhimServera.is("FunTime");
   }

   private int compute(GenericContainerScreen genericContainerScreen, Item item) {
      if (genericContainerScreen != null && genericContainerScreen.getScreenHandler() != null) {
         for (Slot slot : ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).slots) {
            if (slot.id < ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).slots.size() - 36
               && slot.hasStack()
               && slot.getStack().getItem() == item) {
               return slot.id;
            }
         }

         return -1;
      } else {
         return -1;
      }
   }

   @EventHandler
   public void onPacket(PacketEvent packetEvent) {
      if (packetEvent.getPacket() instanceof GameMessageS2CPacket gameMessageS2CPacket) {
         String text3 = gameMessageS2CPacket.content().getString();
         if (!text3.contains("Предметы успешно перевыставлены") && (!text3.contains("[✔]") || !text3.contains("перевыставлены"))) {
            if (this.check5() && text3.contains("В хранилище отсутствуют предметы для перевыставления")) {
               FunTimeAuctionHelper.invoke10();
               ChatUtil.sendClientMessage("§e[AutoResell] §fХранилище пустое, возвращаю AutoBuy.");
               this.invoke2();
            }
         } else {
            FunTimeAuctionHelper.invoke9();
            ChatUtil.sendClientMessage("§a[AutoResell] §fГотово.");
            this.invoke2();
         }

         if (this.autoResellState != AutoResell.AutoResellState.WAITING && text3.contains("Подождите") && text3.contains("сек")) {
            Matcher matcher = this.pattern.matcher(text3);
            if (matcher.find()) {
               try {
                  int intValue3 = Integer.parseInt(matcher.group(1));
                  ChatUtil.sendClientMessage("§e[AutoResell] §fЖдем " + intValue3 + " сек (кулдаун)...");
                  this.timestamp = (intValue3 + 1) * 1000L;
                  this.invoke3(AutoResell.AutoResellState.COOLDOWN_WAIT);
                  this.dualTimer.invoke();
                  FunTimeAuctionHelper.invoke4(true);
               } catch (Exception exception) {
               }
            }
         }

         if (text3.contains("Не удалось выставить") && text3.contains("освободите хранилище")) {
            FunTimeAuctionHelper.invoke7();
         } else if (text3.contains("У Вас купили") && text3.contains("на /ah")) {
            FunTimeAuctionHelper.invoke8();
         } else if (text3.contains("выставлен на продажу за")) {
            FunTimeAuctionHelper.invoke6();
         }
      }
   }

   static enum AutoResellState {
      WAITING,
      OPENING_MAIN_AH,
      CLICKING_STORAGE,
      OPENING_STORAGE,
      CLICKING_CLOCK,
      WAITING_RESULT,
      COOLDOWN_WAIT;
   }
}
