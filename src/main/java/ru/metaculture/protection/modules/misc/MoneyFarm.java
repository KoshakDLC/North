package ru.metaculture.protection;

import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.ingame.CraftingScreen;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "MoneyFarm",
   category = Category.Misc,
   description = "Крафтит и продает изумрудные предметы"
)
public class MoneyFarm extends Module {
   private static final String IZUMRUDNYY_MECH = "Изумрудный меч";
   private static final String IZUMRUDNAYA_KIRKA = "Изумрудная кирка";
   private static final String DEREVO = "Дерево";
   private static final int INT_VALUE = 5;
   private static final long TIMESTAMP = 250L;
   private static final long TIMESTAMP_2 = 1200L;
   private static final long TIMESTAMP_3 = 7000L;
   private static final long TIMESTAMP_4 = 5000L;
   private static final int INT_VALUE_2 = 9;
   private static final int INT_VALUE_3 = 20;
   private final ModeSetting predmet = new ModeSetting("Предмет", "Изумрудный меч", "Изумрудный меч", "Изумрудная кирка");
   private final TextSetting tsenaProdazhi = new TextSetting("Цена продажи", "40000").resolve(32);
   private final TextSetting maksTsenaDereva = new TextSetting("Макс. цена дерева", "0").resolve(32);
   private final NumberSetting zaderzhkaMs = new NumberSetting("Задержка (мс)", 100.0F, 50.0F, 5000.0F, 50.0F, false);
   private final BooleanSetting avtoPokupka = new BooleanSetting("Авто-покупка", true);
   private final BooleanSetting avtoProdazha = new BooleanSetting("Авто-продажа", false);
   private final BooleanSetting uvedomleniya = new BooleanSetting("Уведомления", true);
   private MoneyFarm.MoneyFarmState2 moneyFarmState2 = MoneyFarm.MoneyFarmState2.IDLE;
   private final DualTimer dualTimer = new DualTimer();
   private final DualTimer dualTimer2 = new DualTimer();
   private final DualTimer dualTimer3 = new DualTimer();
   private final DualTimer dualTimer4 = new DualTimer();
   private final DualTimer dualTimer5 = new DualTimer();
   private BlockPos blockPos;
   private MoneyFarm.MoneyFarmState moneyFarmState = MoneyFarm.MoneyFarmState.ITEM;
   private int intValue = 0;
   private int intValue2 = 0;
   private int intValue3 = 0;
   private int intValue4 = 0;
   private int intValue5 = 0;
   private boolean flag = false;
   private boolean flag2 = false;
   private boolean flag3 = false;
   private boolean flag4 = false;
   private boolean flag5 = false;
   private boolean flag6 = false;
   private int intValue6 = 0;
   private int intValue7 = 0;
   private int intValue8 = 0;
   private int intValue9 = 0;
   private boolean flag7 = false;
   private int intValue10 = 0;
   private long timestamp = 0L;
   private long timestamp2 = 0L;

   public MoneyFarm() {
      this.addSettings(
         new Setting[]{
            this.predmet, this.tsenaProdazhi, this.maksTsenaDereva, this.zaderzhkaMs, this.avtoPokupka, this.avtoProdazha, this.uvedomleniya
         }
      );
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.invoke43();
   }

   @Override
   public void onDisable() {
      int intValue = this.intValue;
      this.invoke43();
      super.onDisable();
   }

   @EventHandler
   public void onPacket(PacketEvent packetEvent) {
      if (CLIENT.player != null && packetEvent.getPacketEventState().equals(PacketEvent.PacketEventState.RECEIVE)) {
         if (packetEvent.getPacket() instanceof GameMessageS2CPacket gameMessageS2CPacket) {
            String text = gameMessageS2CPacket.content().getString();
            String text2 = this.resolve6(text);
            if (text2.contains("не удалось выставить") && text2.contains("освободите хранилище")) {
               this.flag = true;
               this.flag2 = false;
               this.flag5 = true;
            } else {
               if (text2.contains("у вас купили") && text2.contains(this.resolve6(this.resolve4()))) {
                  this.flag3 = true;
                  this.intValue8++;
                  this.intValue9 = Math.max(0, this.intValue9 - 1);
                  this.intValue3 = 0;
                  this.intValue2 = 0;
                  this.flag7 = false;
                  this.timestamp2 = System.currentTimeMillis();
               }

               if ((text2.contains("выставлен") || text2.contains("выставлено") || text2.contains("успешно выстав")) && text2.contains("продаж")) {
                  int intValue2 = this.intValue7 > 0 ? this.intValue7 : 1;
                  this.flag = false;
                  this.flag2 = true;
                  this.intValue4 += intValue2;
                  this.intValue9 += intValue2;
                  this.intValue8 = Math.max(0, this.intValue8 - intValue2);
                  this.flag7 = true;
                  this.intValue7 = 0;
                  this.timestamp2 = System.currentTimeMillis();
               }

               if (text2.contains("вы успешно купили")
                  && (
                     this.moneyFarmState2 == MoneyFarm.MoneyFarmState2.BUY_WOOD_WAITING_CONFIRM
                        || this.moneyFarmState2 == MoneyFarm.MoneyFarmState2.BUY_WOOD_CONFIRMING
                        || this.moneyFarmState2 == MoneyFarm.MoneyFarmState2.BUY_WOOD_CLOSING
                  )) {
                  this.flag4 = true;
               }
            }
         }
      }
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player != null && CLIENT.world != null && CLIENT.interactionManager != null) {
         this.invoke42();
         if (this.flag && this.moneyFarmState2 == MoneyFarm.MoneyFarmState2.IDLE) {
            this.invoke33();
         }

         switch (this.moneyFarmState2) {
            case IDLE:
               this.invoke();
               break;
            case BUY_WOOD_SEARCHING:
               this.invoke2();
               break;
            case BUY_WOOD_WAITING_AUCTION:
               this.invoke3();
               break;
            case BUY_WOOD_READING_AUCTION:
               this.invoke4();
               break;
            case BUY_WOOD_WAITING_CONFIRM:
               this.invoke5();
               break;
            case BUY_WOOD_CONFIRMING:
               this.invoke6();
               break;
            case BUY_WOOD_CLOSING:
               this.invoke7();
               break;
            case BUY_OPENING_SHOP:
               this.invoke8();
               break;
            case BUY_WAITING_SHOP:
               this.invoke9();
               break;
            case BUY_FIND_GOLD_INGOT:
               this.invoke10();
               break;
            case BUY_WAITING_EMERALD_MENU:
               this.invoke11();
               break;
            case BUY_FIND_EMERALD:
               this.invoke12();
               break;
            case BUY_WAITING_CONFIRM:
               this.invoke13();
               break;
            case BUY_CLICK_LIME_PANE:
               this.invoke14();
               break;
            case BUY_CLOSING_SHOP:
               this.invoke15();
               break;
            case CHECK_SELL_GUI_OPENING:
               this.invoke16();
               break;
            case CHECK_SELL_GUI_WAITING:
               this.invoke17();
               break;
            case CHECK_SELL_GUI_READING:
               this.invoke18();
               break;
            case FINDING_CRAFTING_TABLE:
               this.invoke19();
               break;
            case AIMING_CRAFTING_TABLE:
               this.invoke20();
               break;
            case OPENING_CRAFTING_TABLE:
               this.invoke21();
               break;
            case PLACING_ITEMS:
               this.invoke22();
               break;
            case TAKING_RESULT:
               this.invoke23();
               break;
            case CLOSING_CRAFTING:
               this.invoke24();
               break;
            case SELLING:
               this.invoke25();
               break;
            case WAITING_SELL_RESULT:
               this.invoke26();
               break;
            case RESALE_SEARCH_OWN_AH:
               this.invoke27();
               break;
            case RESALE_WAITING_OWN_AH:
               this.invoke28();
               break;
            case RESALE_TAKE_ITEM:
               this.invoke29();
               break;
            case RESALE_CLOSING:
               this.invoke30();
               break;
            case RESALE_SELLING:
               this.invoke31();
               break;
            case RESALE_WAIT_SELL_RESULT:
               this.invoke32();
         }
      }
   }

   private void invoke() {
      if (this.dualTimer.check5(this.compute23())) {
         if (this.flag) {
            this.invoke33();
         } else {
            if (this.avtoProdazha.isEnabled() && this.intValue8 > 0) {
               this.flag3 = false;
               if (!this.flag7) {
                  this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.CHECK_SELL_GUI_OPENING;
                  this.dualTimer.invoke();
                  return;
               }

               if (this.compute5() > 0 && this.compute12() >= this.compute5()) {
                  this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.SELLING;
                  this.dualTimer.invoke();
                  return;
               }
            } else {
               if (this.avtoProdazha.isEnabled() && this.intValue9 > 0 && System.currentTimeMillis() - this.timestamp2 >= 5000L) {
                  this.invoke33();
                  return;
               }

               if (this.avtoProdazha.isEnabled() && this.intValue9 > 0) {
                  return;
               }

               if (this.avtoProdazha.isEnabled() && !this.flag7) {
                  this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.CHECK_SELL_GUI_OPENING;
                  this.dualTimer.invoke();
                  return;
               }
            }

            if (this.avtoProdazha.isEnabled() && this.intValue9 == 0 && this.compute5() > 0 && this.compute12() >= this.compute5()) {
               this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.SELLING;
               this.dualTimer.invoke();
            } else if (!this.avtoProdazha.isEnabled() || !this.flag7 || this.compute5() > 0) {
               if (this.avtoProdazha.isEnabled() && this.flag7 && this.compute5() > 0 && this.compute12() >= this.compute5()) {
                  if (this.compute12() > 0) {
                     this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.SELLING;
                  }

                  this.dualTimer.invoke();
               } else if (this.avtoPokupka.isEnabled() && this.check2()) {
                  this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.BUY_OPENING_SHOP;
                  this.dualTimer.invoke();
               } else if (this.check3()) {
                  if (this.check4()) {
                     this.moneyFarmState = MoneyFarm.MoneyFarmState.STICKS;
                     this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.FINDING_CRAFTING_TABLE;
                     this.dualTimer.invoke();
                  } else if (this.check5()) {
                     this.moneyFarmState = MoneyFarm.MoneyFarmState.PLANKS;
                     this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.FINDING_CRAFTING_TABLE;
                     this.dualTimer.invoke();
                  } else {
                     if (this.avtoPokupka.isEnabled() && this.compute21() > 0L) {
                        this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.BUY_WOOD_SEARCHING;
                        this.dualTimer.invoke();
                     }
                  }
               } else if (this.check()) {
                  this.moneyFarmState = MoneyFarm.MoneyFarmState.ITEM;
                  this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.FINDING_CRAFTING_TABLE;
                  this.dualTimer.invoke();
               }
            }
         }
      }
   }

   private void invoke2() {
      if (this.dualTimer.check5(this.compute23()) && CLIENT.currentScreen == null) {
         long longValue = this.compute21();
         if (longValue <= 0L) {
            this.invoke44("§cМаксимальная цена дерева не задана.");
         } else {
            this.invoke38("Дерево");
            this.intValue5 = 0;
            this.flag4 = false;
            this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.BUY_WOOD_WAITING_AUCTION;
            this.dualTimer.invoke();
            this.dualTimer3.invoke();
            this.dualTimer4.invoke();
            this.dualTimer5.invoke();
         }
      }
   }

   private void invoke3() {
      if (!(CLIENT.currentScreen instanceof GenericContainerScreen genericContainerScreen2 && (AhHelper.check(genericContainerScreen2) || this.check16(genericContainerScreen2)))) {
         if (this.dualTimer.check5(10000L)) {
            this.invoke44("§cТаймаут поиска дерева.");
         }
      } else {
         this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.BUY_WOOD_READING_AUCTION;
         this.dualTimer.invoke();
      }
   }

   private void invoke4() {
      if (this.dualTimer.check5(150L)) {
         if (!(CLIENT.currentScreen instanceof GenericContainerScreen genericContainerScreen3)) {
            if (this.dualTimer.check5(10000L)) {
               this.invoke45();
            }
         } else if (this.check16(genericContainerScreen3)) {
            this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.BUY_WOOD_CONFIRMING;
            this.dualTimer.invoke();
         } else if (!AhHelper.check(genericContainerScreen3)) {
            if (this.dualTimer.check5(10000L)) {
               this.invoke46();
               this.invoke44("§cОткрылся не экран аукциона при покупке дерева.");
            }
         } else {
            long longValue2 = this.compute21();
            boolean flag = this.check11(genericContainerScreen3);
            ScreenHandler screenHandler2 = genericContainerScreen3.getScreenHandler();
            boolean flag2 = false;

            for (int intValue3 = 0; intValue3 < Math.min(45, screenHandler2.slots.size()); intValue3++) {
               Slot slot2 = screenHandler2.getSlot(intValue3);
               if (this.check9(slot2) && (flag || this.check10(slot2))) {
                  long longValue3 = AuctionSellerParser.compute(slot2);
                  String text3 = AuctionSellerParser.resolve(slot2);
                  if ((CLIENT.player == null || text3 == null || !text3.equalsIgnoreCase(CLIENT.player.getName().getString()))
                     && longValue3 > 0L
                     && longValue3 <= longValue2) {
                     flag2 = true;
                     if (this.dualTimer4.check5(Math.max(50L, this.compute23()))) {
                        CLIENT.interactionManager.clickSlot(screenHandler2.syncId, intValue3, 0, SlotActionType.QUICK_MOVE, CLIENT.player);
                        this.dualTimer4.invoke();
                        this.dualTimer5.invoke();
                        this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.BUY_WOOD_WAITING_CONFIRM;
                        this.dualTimer.invoke();
                        return;
                     }
                     break;
                  }
               }
            }

            if (!flag2 && this.dualTimer3.check5(250L)) {
               if (screenHandler2.slots.size() > 49) {
                  CLIENT.interactionManager.clickSlot(screenHandler2.syncId, 49, 0, SlotActionType.PICKUP, CLIENT.player);
                  this.dualTimer3.invoke();
               } else {
                  this.invoke46();
                  this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.BUY_WOOD_SEARCHING;
                  this.dualTimer.invoke();
               }
            } else if (this.dualTimer.check5(10000L)) {
               this.invoke46();
               this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.BUY_WOOD_SEARCHING;
               this.dualTimer.invoke();
            } else {
               this.intValue5++;
            }
         }
      }
   }

   private void invoke5() {
      if (this.dualTimer.check5(50L)) {
         if (this.flag4) {
            this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.BUY_WOOD_CLOSING;
            this.dualTimer.invoke();
         } else if (CLIENT.currentScreen instanceof GenericContainerScreen genericContainerScreen4) {
            if (this.check16(genericContainerScreen4)) {
               this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.BUY_WOOD_CONFIRMING;
               this.dualTimer.invoke();
            } else {
               if (this.dualTimer.check5(4000L)) {
                  this.invoke46();
                  this.invoke44("§cПодтверждение покупки дерева не открылось.");
               }
            }
         } else {
            if (this.dualTimer.check5(4000L)) {
               this.invoke44("§cПодтверждение покупки дерева не открылось.");
            }
         }
      }
   }

   private void invoke6() {
      if (this.dualTimer.check5(50L)) {
         if (this.flag4) {
            this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.BUY_WOOD_CLOSING;
            this.dualTimer.invoke();
         } else if (CLIENT.currentScreen instanceof GenericContainerScreen genericContainerScreen5) {
            int intValue4 = this.compute18(genericContainerScreen5.getScreenHandler());
            if (intValue4 != -1 && this.dualTimer5.check5(Math.max(50L, this.compute23()))) {
               this.invoke37(genericContainerScreen5, intValue4, 0, SlotActionType.PICKUP);
               this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.BUY_WOOD_CLOSING;
               this.dualTimer.invoke();
               this.dualTimer5.invoke();
            } else {
               if (this.dualTimer.check5(5000L)) {
                  this.invoke46();
                  this.invoke44("§cКнопка подтверждения покупки дерева не найдена.");
               }
            }
         } else {
            this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.BUY_WOOD_CLOSING;
            this.dualTimer.invoke();
         }
      }
   }

   private void invoke7() {
      if (this.dualTimer.check5(300L)) {
         this.invoke46();
         if (this.flag4) {
         }

         this.invoke45();
      }
   }

   private void invoke8() {
      if (this.dualTimer.check5(this.compute23())) {
         if (this.check23(150L)) {
            CLIENT.player.networkHandler.sendChatCommand("shop");
            this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.BUY_WAITING_SHOP;
            this.dualTimer.invoke();
         }
      }
   }

   private void invoke9() {
      if (CLIENT.currentScreen instanceof GenericContainerScreen) {
         this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.BUY_FIND_GOLD_INGOT;
         this.dualTimer.invoke();
      } else {
         if (this.dualTimer.check5(10000L)) {
            this.invoke44("§cТаймаут магазина.");
         }
      }
   }

   private void invoke10() {
      if (this.dualTimer.check5(this.compute23())) {
         if (CLIENT.currentScreen instanceof GenericContainerScreen genericContainerScreen6) {
            int intValue5 = this.compute17(genericContainerScreen6, Items.GOLD_INGOT);
            if (intValue5 != -1) {
               this.invoke37(genericContainerScreen6, intValue5, 0, SlotActionType.PICKUP);
               this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.BUY_WAITING_EMERALD_MENU;
               this.dualTimer.invoke();
            } else {
               if (this.dualTimer.check5(5000L)) {
                  this.invoke46();
                  this.invoke44("§cЗолотой слиток не найден.");
               }
            }
         } else {
            this.invoke45();
         }
      }
   }

   private void invoke11() {
      if (this.dualTimer.check5(this.compute23())) {
         if (!(CLIENT.currentScreen instanceof GenericContainerScreen)) {
            this.invoke45();
         } else {
            this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.BUY_FIND_EMERALD;
            this.dualTimer.invoke();
         }
      }
   }

   private void invoke12() {
      if (this.dualTimer.check5(this.compute23())) {
         if (CLIENT.currentScreen instanceof GenericContainerScreen genericContainerScreen7) {
            int intValue6 = this.compute16(genericContainerScreen7);
            if (intValue6 != -1) {
               this.invoke37(genericContainerScreen7, intValue6, 1, SlotActionType.PICKUP);
               this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.BUY_WAITING_CONFIRM;
               this.dualTimer.invoke();
            } else {
               if (this.dualTimer.check5(5000L)) {
                  this.invoke46();
                  this.invoke44("§cИзумруд не найден.");
               }
            }
         } else {
            this.invoke45();
         }
      }
   }

   private void invoke13() {
      if (this.dualTimer.check5(this.compute23())) {
         if (!this.check2()) {
            this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.BUY_CLOSING_SHOP;
            this.dualTimer.invoke();
         } else if (CLIENT.currentScreen instanceof GenericContainerScreen genericContainerScreen8) {
            if (this.check16(genericContainerScreen8)) {
               this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.BUY_CLICK_LIME_PANE;
               this.dualTimer.invoke();
            } else {
               if (this.dualTimer.check5(5000L)) {
                  this.invoke46();
                  this.invoke44("§cПодтверждение покупки изумрудов не открылось.");
               }
            }
         } else {
            this.invoke45();
         }
      }
   }

   private void invoke14() {
      if (this.dualTimer.check5(this.compute23())) {
         if (!this.check2()) {
            this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.BUY_CLOSING_SHOP;
            this.dualTimer.invoke();
         } else if (CLIENT.currentScreen instanceof GenericContainerScreen genericContainerScreen9) {
            if (!this.check16(genericContainerScreen9)) {
               this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.BUY_WAITING_CONFIRM;
               this.dualTimer.invoke();
            } else {
               int intValue7 = this.compute18(genericContainerScreen9.getScreenHandler());
               if (intValue7 != -1) {
                  this.invoke37(genericContainerScreen9, intValue7, 0, SlotActionType.PICKUP);
                  this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.BUY_CLOSING_SHOP;
                  this.dualTimer.invoke();
               } else {
                  if (this.dualTimer.check5(5000L)) {
                     this.invoke46();
                     this.invoke44("§cЛаймовая панель не найдена.");
                  }
               }
            }
         } else {
            this.invoke45();
         }
      }
   }

   private void invoke15() {
      if (this.check23(150L)) {
         this.invoke45();
      }
   }

   private void invoke16() {
      if (this.dualTimer.check5(50L) && CLIENT.currentScreen == null) {
         long longValue4 = this.compute20();
         if (longValue4 <= 0L) {
            this.invoke44("§cЦена продажи не задана.");
         } else if (this.check21()) {
            this.invoke41();
            this.invoke40(longValue4);
            this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.CHECK_SELL_GUI_WAITING;
            this.dualTimer.invoke();
         }
      }
   }

   private void invoke17() {
      if (CLIENT.currentScreen instanceof GenericContainerScreen genericContainerScreen10 && this.check17(genericContainerScreen10)) {
         this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.CHECK_SELL_GUI_READING;
         this.dualTimer.invoke();
      } else {
         if (this.dualTimer.check5(7000L)) {
            this.invoke46();
            this.invoke44("§cSellgui не открылся для проверки слотов.");
         }
      }
   }

   private void invoke18() {
      if (this.dualTimer.check5(150L)) {
         if (CLIENT.currentScreen instanceof GenericContainerScreen genericContainerScreen11 && this.check17(genericContainerScreen11)) {
            int intValue8 = this.compute4(genericContainerScreen11);
            this.intValue3 = intValue8;
            if (this.intValue8 > 0) {
               this.intValue8 = intValue8;
            }

            this.flag7 = true;
            this.intValue2 = 0;
            this.invoke46();
            if (intValue8 <= 0) {
               this.invoke33();
            } else {
               this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.IDLE;
               this.dualTimer.invoke();
            }
         } else {
            this.invoke45();
         }
      }
   }

   private void invoke19() {
      if (this.check23(150L)) {
         if (this.dualTimer.check5(this.compute23())) {
            this.blockPos = this.resolve();
            if (this.blockPos == null) {
               this.invoke44("§cВерстак рядом не найден.");
            } else {
               this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.AIMING_CRAFTING_TABLE;
               this.dualTimer.invoke();
            }
         }
      }
   }

   private void invoke20() {
      if (this.blockPos != null && this.check19(this.blockPos)) {
         Rotation rotation = this.resolve2(Vec3d.ofCenter(this.blockPos));
         RotationController.invoke3(rotation, 45.0F, 45.0F, 30.0F, 30.0F, 4, 5, false);
         if (!(new Rotation(CLIENT.player).measure(rotation) > 4.0F) && this.dualTimer.check5(this.compute23())) {
            BlockHitResult blockHitResult = new BlockHitResult(Vec3d.ofCenter(this.blockPos), this.resolve3(this.blockPos), this.blockPos, false);
            CLIENT.interactionManager.interactBlock(CLIENT.player, Hand.MAIN_HAND, blockHitResult);
            CLIENT.player.swingHand(Hand.MAIN_HAND);
            this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.OPENING_CRAFTING_TABLE;
            this.dualTimer.invoke();
         }
      } else {
         this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.FINDING_CRAFTING_TABLE;
         this.dualTimer.invoke();
      }
   }

   private void invoke21() {
      if (this.dualTimer.check5(this.compute23())) {
         if (CLIENT.currentScreen instanceof CraftingScreen) {
            this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.PLACING_ITEMS;
            this.dualTimer.invoke();
         } else {
            if (this.dualTimer.check5(5000L)) {
               this.invoke44("§cВерстак не открылся.");
            }
         }
      }
   }

   private void invoke22() {
      if (this.dualTimer.check5(50L)) {
         if (CLIENT.currentScreen instanceof CraftingScreen craftingScreen) {
            CraftingScreenHandler craftingScreenHandler2 = (CraftingScreenHandler)craftingScreen.getScreenHandler();
            int intValue9 = craftingScreenHandler2.syncId;
            switch (this.moneyFarmState) {
               case ITEM:
                  if (this.check22()) {
                     this.invoke34(craftingScreenHandler2, intValue9, Items.EMERALD, 2);
                     this.invoke34(craftingScreenHandler2, intValue9, Items.EMERALD, 5);
                     this.invoke34(craftingScreenHandler2, intValue9, Items.STICK, 8);
                  } else {
                     this.invoke34(craftingScreenHandler2, intValue9, Items.EMERALD, 1);
                     this.invoke34(craftingScreenHandler2, intValue9, Items.EMERALD, 2);
                     this.invoke34(craftingScreenHandler2, intValue9, Items.EMERALD, 3);
                     this.invoke34(craftingScreenHandler2, intValue9, Items.STICK, 5);
                     this.invoke34(craftingScreenHandler2, intValue9, Items.STICK, 8);
                  }
                  break;
               case PLANKS:
                  this.invoke35(craftingScreenHandler2, intValue9, this::check14, 1);
                  break;
               case STICKS:
                  this.invoke35(craftingScreenHandler2, intValue9, this::check13, 2);
                  this.invoke35(craftingScreenHandler2, intValue9, this::check13, 5);
            }

            this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.TAKING_RESULT;
            this.dualTimer.invoke();
         } else {
            this.invoke45();
         }
      }
   }

   private void invoke23() {
      if (this.dualTimer.check5(50L)) {
         if (CLIENT.currentScreen instanceof CraftingScreen craftingScreen2) {
            CLIENT.interactionManager
               .clickSlot(((CraftingScreenHandler)craftingScreen2.getScreenHandler()).syncId, 0, 0, SlotActionType.QUICK_MOVE, CLIENT.player);
            if (this.moneyFarmState == MoneyFarm.MoneyFarmState.ITEM) {
               this.intValue++;
               this.intValue2++;
            } else if (this.moneyFarmState == MoneyFarm.MoneyFarmState.PLANKS) {
            }

            this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.CLOSING_CRAFTING;
            this.dualTimer.invoke();
         } else {
            this.invoke45();
         }
      }
   }

   private void invoke24() {
      if (this.dualTimer.check5(50L)) {
         this.invoke46();
         if (this.moneyFarmState == MoneyFarm.MoneyFarmState.ITEM && this.avtoProdazha.isEnabled()) {
            int intValue10 = this.compute5();
            int intValue11 = this.compute12();
            if (intValue10 > 0 && intValue11 < intValue10) {
               this.moneyFarmState2 = this.check() ? MoneyFarm.MoneyFarmState2.FINDING_CRAFTING_TABLE : MoneyFarm.MoneyFarmState2.IDLE;
            } else {
               this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.SELLING;
            }
         } else {
            this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.IDLE;
         }

         this.dualTimer.invoke();
      }
   }

   private void invoke25() {
      if (this.dualTimer.check5(50L)) {
         if (this.flag) {
            this.invoke33();
         } else {
            long longValue5 = this.compute20();
            if (longValue5 <= 0L) {
               this.invoke44("§cЦена продажи не задана.");
            } else if (!this.check6()) {
               this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.IDLE;
               this.dualTimer.invoke();
            } else if (CLIENT.currentScreen instanceof GenericContainerScreen genericContainerScreen12 && this.check17(genericContainerScreen12)) {
               this.invoke36(genericContainerScreen12, false);
            } else if (CLIENT.currentScreen != null) {
               this.invoke46();
               this.dualTimer.invoke();
            } else if (this.check21()) {
               this.invoke41();
               this.flag2 = false;
               this.flag = false;
               this.invoke40(longValue5);
               this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.WAITING_SELL_RESULT;
               this.dualTimer.invoke();
            }
         }
      }
   }

   private void invoke26() {
      if (this.flag) {
         this.invoke33();
      } else if (CLIENT.currentScreen instanceof GenericContainerScreen genericContainerScreen13 && this.check17(genericContainerScreen13)) {
         this.invoke36(genericContainerScreen13, false);
      } else if (this.flag2) {
         this.flag2 = false;
         this.intValue2 = 0;
         this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.IDLE;
         this.dualTimer.invoke();
      } else {
         if (this.dualTimer.check5(7000L)) {
            this.invoke46();
            this.moneyFarmState2 = this.check6() ? MoneyFarm.MoneyFarmState2.SELLING : MoneyFarm.MoneyFarmState2.IDLE;
            this.dualTimer.invoke();
         }
      }
   }

   private void invoke27() {
      if (this.dualTimer.check5(this.compute23()) && CLIENT.currentScreen == null) {
         if (!this.flag5 && this.check6()) {
            this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.RESALE_SELLING;
            this.dualTimer.invoke();
         } else {
            this.flag5 = false;
            String text4 = CLIENT.player.getName().getString();
            this.invoke39(text4);
            this.intValue5 = 0;
            this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.RESALE_WAITING_OWN_AH;
            this.dualTimer.invoke();
         }
      }
   }

   private void invoke28() {
      if (CLIENT.currentScreen instanceof GenericContainerScreen genericContainerScreen14 && this.check20(genericContainerScreen14)) {
         this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.RESALE_TAKE_ITEM;
         this.dualTimer.invoke();
      } else {
         if (this.dualTimer.check5(10000L)) {
            this.invoke44("§cТаймаут поиска своих товаров.");
         }
      }
   }

   private void invoke29() {
      if (this.dualTimer.check5(200L)) {
         if (CLIENT.currentScreen instanceof GenericContainerScreen genericContainerScreen15) {
            if (!this.check20(genericContainerScreen15)) {
               if (this.dualTimer.check5(10000L)) {
                  this.invoke46();
                  this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.RESALE_SEARCH_OWN_AH;
                  this.dualTimer.invoke();
               }
            } else {
               int intValue12 = this.compute13(genericContainerScreen15);
               if (intValue12 != -1) {
                  this.invoke37(genericContainerScreen15, intValue12, 0, SlotActionType.QUICK_MOVE);
                  this.intValue5 = 0;
                  this.dualTimer.invoke();
               } else if (this.intValue5++ < 2) {
                  this.dualTimer.invoke();
               } else {
                  this.invoke46();
                  if (this.check6()) {
                     this.intValue9 = 0;
                     this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.RESALE_SELLING;
                  } else {
                     this.flag = false;
                     this.intValue4 = 0;
                     this.intValue9 = 0;
                     this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.IDLE;
                  }

                  this.dualTimer.invoke();
               }
            }
         } else {
            this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.RESALE_SEARCH_OWN_AH;
            this.dualTimer.invoke();
         }
      }
   }

   private int compute(GenericContainerScreen genericContainerScreen) {
      int intValue13 = 0;
      int intValue14 = this.compute19(genericContainerScreen);

      for (int intValue15 = 0; intValue15 < intValue14; intValue15++) {
         Slot slot3 = ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).getSlot(intValue15);
         if (slot3.hasStack() && this.check8(slot3.getStack(), true)) {
            intValue13++;
         }
      }

      return intValue13;
   }

   private void invoke30() {
      if (this.dualTimer.check5(300L)) {
         this.invoke46();
         this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.RESALE_SELLING;
         this.dualTimer.invoke();
      }
   }

   private void invoke31() {
      if (this.dualTimer.check5(50L)) {
         if (this.flag) {
            this.invoke33();
         } else {
            long longValue6 = this.compute20();
            if (longValue6 <= 0L) {
               this.invoke44("§cЦена продажи не задана.");
            } else if (!this.check6()) {
               this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.RESALE_SEARCH_OWN_AH;
               this.dualTimer.invoke();
            } else if (CLIENT.currentScreen instanceof GenericContainerScreen genericContainerScreen16 && this.check17(genericContainerScreen16)) {
               this.invoke36(genericContainerScreen16, true);
            } else if (CLIENT.currentScreen != null) {
               this.invoke46();
               this.dualTimer.invoke();
            } else if (this.check21()) {
               this.invoke41();
               this.flag2 = false;
               this.flag = false;
               this.invoke40(longValue6);
               this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.RESALE_WAIT_SELL_RESULT;
               this.dualTimer.invoke();
            }
         }
      }
   }

   private void invoke32() {
      if (this.flag3) {
         this.flag3 = false;
      }

      if (this.flag) {
         this.invoke33();
      } else if (CLIENT.currentScreen instanceof GenericContainerScreen genericContainerScreen17 && this.check17(genericContainerScreen17)) {
         this.invoke36(genericContainerScreen17, true);
      } else if (this.flag2) {
         this.flag2 = false;
         this.intValue4 = 0;
         this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.IDLE;
         this.dualTimer.invoke();
      } else {
         if (this.dualTimer.check5(7000L)) {
            this.invoke46();
            this.moneyFarmState2 = this.check6() ? MoneyFarm.MoneyFarmState2.RESALE_SELLING : MoneyFarm.MoneyFarmState2.IDLE;
            this.dualTimer.invoke();
         }
      }
   }

   private void invoke33() {
      this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.RESALE_SEARCH_OWN_AH;
      this.flag = false;
      this.flag2 = false;
      this.flag5 = true;
      this.intValue2 = 0;
      this.intValue3 = 0;
      this.intValue9 = 0;
      this.intValue8 = 0;
      this.flag7 = false;
      this.dualTimer.invoke();
   }

   private void invoke34(CraftingScreenHandler craftingScreenHandler, int i, Item item, int j) {
      this.invoke35(craftingScreenHandler, i, (Predicate<ItemStack>)(itemStack -> itemStack.isOf(item)), j);
   }

   private void invoke35(CraftingScreenHandler craftingScreenHandler, int i, Predicate<ItemStack> predicate, int j) {
      int intValue16 = -1;

      for (int intValue17 = 10; intValue17 < craftingScreenHandler.slots.size(); intValue17++) {
         Slot slot4 = craftingScreenHandler.getSlot(intValue17);
         if (slot4.hasStack() && predicate.test(slot4.getStack())) {
            intValue16 = intValue17;
            break;
         }
      }

      if (intValue16 != -1) {
         CLIENT.interactionManager.clickSlot(i, intValue16, 0, SlotActionType.PICKUP, CLIENT.player);
         CLIENT.interactionManager.clickSlot(i, j, 1, SlotActionType.PICKUP, CLIENT.player);
         CLIENT.interactionManager.clickSlot(i, intValue16, 0, SlotActionType.PICKUP, CLIENT.player);
      }
   }

   private void invoke36(GenericContainerScreen genericContainerScreen, boolean bl) {
      ScreenHandler screenHandler3 = genericContainerScreen.getScreenHandler();
      int intValue18 = this.compute19(genericContainerScreen);
      if (this.flag6) {
         if (this.dualTimer.check5(1500L)) {
            this.invoke46();
         }
      } else {
         int intValue19 = 0;

         for (int intValue20 = this.compute2(bl); intValue19 < 4 && this.intValue6 < intValue20; intValue19++) {
            int intValue21 = this.compute3(genericContainerScreen);
            int intValue22 = this.compute6(screenHandler3, intValue18);
            if (intValue21 == -1 || intValue22 == -1) {
               break;
            }

            CLIENT.interactionManager.clickSlot(screenHandler3.syncId, intValue22, 0, SlotActionType.PICKUP, CLIENT.player);
            CLIENT.interactionManager.clickSlot(screenHandler3.syncId, intValue21, 0, SlotActionType.PICKUP, CLIENT.player);
            this.intValue6++;
         }

         if (intValue19 > 0) {
            this.dualTimer.invoke();
         } else if (this.intValue6 <= 0) {
            this.invoke46();
            this.moneyFarmState2 = bl ? MoneyFarm.MoneyFarmState2.RESALE_SEARCH_OWN_AH : MoneyFarm.MoneyFarmState2.IDLE;
            this.dualTimer.invoke();
         } else {
            int intValue23 = this.compute7(genericContainerScreen);
            if (intValue23 != -1) {
               this.invoke37(genericContainerScreen, intValue23, 0, SlotActionType.PICKUP);
               this.flag6 = true;
               this.intValue7 = this.intValue6;
               this.dualTimer.invoke();
            } else {
               if (this.dualTimer.check5(3000L)) {
                  this.invoke46();
                  this.moneyFarmState2 = bl ? MoneyFarm.MoneyFarmState2.RESALE_SELLING : MoneyFarm.MoneyFarmState2.SELLING;
                  this.dualTimer.invoke();
               }
            }
         }
      }
   }

   private int compute2(boolean bl) {
      if (bl) {
         return Integer.MAX_VALUE;
      } else {
         return this.intValue8 > 0 ? this.intValue8 : Math.max(0, this.intValue3);
      }
   }

   private int compute3(GenericContainerScreen genericContainerScreen) {
      int intValue24 = Math.min(9, this.compute19(genericContainerScreen));

      for (int intValue25 = 0; intValue25 < intValue24; intValue25++) {
         Slot slot5 = ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).getSlot(intValue25);
         if (!slot5.hasStack()) {
            return intValue25;
         }
      }

      return -1;
   }

   private int compute4(GenericContainerScreen genericContainerScreen) {
      int intValue26 = 0;
      int intValue27 = Math.min(9, this.compute19(genericContainerScreen));

      for (int intValue28 = 0; intValue28 < intValue27; intValue28++) {
         Slot slot6 = ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).getSlot(intValue28);
         if (!slot6.hasStack()) {
            intValue26++;
         }
      }

      return intValue26;
   }

   private int compute5() {
      return this.intValue8 > 0 ? this.intValue8 : this.intValue3;
   }

   private int compute6(ScreenHandler screenHandler, int i) {
      for (int intValue29 = i; intValue29 < screenHandler.slots.size(); intValue29++) {
         Slot slot7 = screenHandler.getSlot(intValue29);
         if (slot7.hasStack() && this.check8(slot7.getStack(), true)) {
            return intValue29;
         }
      }

      return -1;
   }

   private int compute7(GenericContainerScreen genericContainerScreen) {
      int intValue30 = this.compute19(genericContainerScreen);
      int intValue31 = -1;

      for (int intValue32 = intValue30 - 1; intValue32 >= 0; intValue32--) {
         Slot slot8 = ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).getSlot(intValue32);
         if (slot8.hasStack()) {
            ItemStack itemStack2 = slot8.getStack();
            String text5 = this.resolve6(itemStack2.getName().getString());
            if (itemStack2.isOf(Items.LIME_DYE)
               || itemStack2.isOf(Items.GREEN_DYE)
               || itemStack2.isOf(Items.LIME_STAINED_GLASS_PANE)
               || itemStack2.isOf(Items.GREEN_STAINED_GLASS_PANE)) {
               return intValue32;
            }

            if (text5.contains("выстав") || text5.contains("продать") || text5.contains("подтверд")) {
               intValue31 = intValue32;
            }
         }
      }

      return intValue31;
   }

   private boolean check() {
      return !this.check2() && !this.check3();
   }

   private boolean check2() {
      return this.compute11(Items.EMERALD) < this.compute9();
   }

   private boolean check3() {
      return this.compute11(Items.STICK) < this.compute10();
   }

   private boolean check4() {
      return this.compute8(this::check13) > 0;
   }

   private boolean check5() {
      return this.compute8(this::check14) > 0;
   }

   private int compute8(Predicate<ItemStack> predicate) {
      if (CLIENT.player == null) {
         return 0;
      } else {
         int intValue33 = 0;

         for (int intValue34 = 0; intValue34 < 36; intValue34++) {
            ItemStack itemStack3 = CLIENT.player.getInventory().getStack(intValue34);
            if (!itemStack3.isEmpty() && predicate.test(itemStack3)) {
               intValue33 += itemStack3.getCount();
            }
         }

         return intValue33;
      }
   }

   private int compute9() {
      return this.check22() ? 2 : 3;
   }

   private int compute10() {
      return this.check22() ? 1 : 2;
   }

   private int compute11(Item item) {
      if (CLIENT.player == null) {
         return 0;
      } else {
         int intValue35 = 0;

         for (int intValue36 = 0; intValue36 < 36; intValue36++) {
            ItemStack itemStack4 = CLIENT.player.getInventory().getStack(intValue36);
            if (!itemStack4.isEmpty() && itemStack4.isOf(item)) {
               intValue35 += itemStack4.getCount();
            }
         }

         return intValue35;
      }
   }

   private boolean check6() {
      return this.compute12() > 0;
   }

   private int compute12() {
      if (CLIENT.player == null) {
         return 0;
      } else {
         int intValue37 = 0;

         for (int intValue38 = 0; intValue38 < 36; intValue38++) {
            ItemStack itemStack5 = CLIENT.player.getInventory().getStack(intValue38);
            if (this.check8(itemStack5, true)) {
               intValue37 += Math.max(1, itemStack5.getCount());
            }
         }

         return intValue37;
      }
   }

   private boolean check7() {
      if (CLIENT.player != null && CLIENT.interactionManager != null && CLIENT.player.playerScreenHandler != null) {
         if (this.check8(CLIENT.player.getMainHandStack(), true)) {
            return true;
         } else {
            for (int intValue39 = 0; intValue39 < 9; intValue39++) {
               if (this.check8(CLIENT.player.getInventory().getStack(intValue39), true)) {
                  CLIENT.player.getInventory().setSelectedSlot(intValue39);
                  return true;
               }
            }

            int intValue40 = CLIENT.player.getInventory().getSelectedSlot();

            for (int intValue41 = 9; intValue41 < 36; intValue41++) {
               if (this.check8(CLIENT.player.getInventory().getStack(intValue41), true)) {
                  CLIENT.interactionManager.clickSlot(CLIENT.player.playerScreenHandler.syncId, intValue41, intValue40, SlotActionType.SWAP, CLIENT.player);
                  return true;
               }
            }

            return false;
         }
      } else {
         return false;
      }
   }

   private boolean check8(ItemStack itemStack, boolean bl) {
      if (itemStack != null && !itemStack.isEmpty()) {
         String text6 = this.resolve6(itemStack.getName().getString());
         if (text6.contains(this.resolve6(this.resolve4()))) {
            return true;
         } else if (!bl) {
            return false;
         } else {
            return this.check22() ? itemStack.isOf(Items.DIAMOND_SWORD) : itemStack.isOf(Items.DIAMOND_PICKAXE);
         }
      } else {
         return false;
      }
   }

   private int compute13(GenericContainerScreen genericContainerScreen) {
      int intValue42 = this.compute19(genericContainerScreen);

      for (int intValue43 = 0; intValue43 < intValue42; intValue43++) {
         Slot slot9 = ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).getSlot(intValue43);
         if (this.check9(slot9) && this.check8(slot9.getStack(), true)) {
            return intValue43;
         }
      }

      return -1;
   }

   private boolean check9(Slot slot) {
      return slot != null && slot.hasStack() ? !this.check15(slot.getStack()) : false;
   }

   private int compute14(Slot slot) {
      return slot != null && slot.hasStack() ? Math.max(1, slot.getStack().getCount()) : 1;
   }

   private boolean check10(Slot slot) {
      if (slot != null && slot.hasStack()) {
         ItemStack itemStack6 = slot.getStack();
         return this.check15(itemStack6) ? false : this.check12(itemStack6) || this.resolve6(itemStack6.getName().getString()).contains(this.resolve6("Дерево"));
      } else {
         return false;
      }
   }

   private boolean check11(GenericContainerScreen genericContainerScreen) {
      if (genericContainerScreen == null) {
         return false;
      } else {
         String text7 = this.resolve6(genericContainerScreen.getTitle().getString());
         return text7.contains(this.resolve6("Дерево"));
      }
   }

   private boolean check12(ItemStack itemStack) {
      if (itemStack == null || itemStack.isEmpty()) {
         return false;
      } else if (itemStack.isOf(Items.STICK)) {
         return true;
      } else {
         String text8 = Registries.ITEM.getId(itemStack.getItem()).getPath();
         String text9 = this.resolve6(itemStack.getName().getString());
         return text8.contains("log")
            || text8.contains("wood")
            || text8.contains("planks")
            || text9.contains("дерев")
            || text9.contains("древес")
            || text9.contains("палк");
      }
   }

   private boolean check13(ItemStack itemStack) {
      if (itemStack != null && !itemStack.isEmpty()) {
         String text10 = Registries.ITEM.getId(itemStack.getItem()).getPath();
         return text10.endsWith("_planks");
      } else {
         return false;
      }
   }

   private boolean check14(ItemStack itemStack) {
      if (itemStack != null && !itemStack.isEmpty()) {
         String text11 = Registries.ITEM.getId(itemStack.getItem()).getPath();
         return text11.endsWith("_log") || text11.endsWith("_wood") || text11.endsWith("_stem") || text11.endsWith("_hyphae");
      } else {
         return false;
      }
   }

   private boolean check15(ItemStack itemStack) {
      return itemStack.isOf(Items.GREEN_STAINED_GLASS_PANE)
         || itemStack.isOf(Items.BLACK_STAINED_GLASS_PANE)
         || itemStack.isOf(Items.LIME_STAINED_GLASS_PANE)
         || itemStack.isOf(Items.RED_STAINED_GLASS_PANE)
         || itemStack.isOf(Items.AIR);
   }

   private int compute15(GenericContainerScreen genericContainerScreen, Item item, String string) {
      String text12 = this.resolve6(string);

      for (int intValue44 = 0; intValue44 < ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).slots.size(); intValue44++) {
         Slot slot10 = ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).getSlot(intValue44);
         if (slot10.hasStack()) {
            ItemStack itemStack7 = slot10.getStack();
            if (itemStack7.isOf(item) && this.resolve6(itemStack7.getName().getString()).contains(text12)) {
               return intValue44;
            }
         }
      }

      return -1;
   }

   private int compute16(GenericContainerScreen genericContainerScreen) {
      int intValue45 = this.compute19(genericContainerScreen);

      for (int intValue46 = 0; intValue46 < intValue45; intValue46++) {
         Slot slot11 = ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).getSlot(intValue46);
         if (slot11.hasStack()) {
            ItemStack itemStack8 = slot11.getStack();
            String text13 = this.resolve6(itemStack8.getName().getString());
            if (itemStack8.isOf(Items.EMERALD)) {
               return intValue46;
            }

            if (itemStack8.isOf(Items.PAPER) && (text13.contains("изумруд") || text13.contains("emerald"))) {
               return intValue46;
            }
         }
      }

      return -1;
   }

   private int compute17(GenericContainerScreen genericContainerScreen, Item item) {
      int intValue47 = this.compute19(genericContainerScreen);

      for (int intValue48 = 0; intValue48 < intValue47; intValue48++) {
         Slot slot12 = ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).getSlot(intValue48);
         if (slot12.hasStack() && slot12.getStack().isOf(item)) {
            return intValue48;
         }
      }

      return -1;
   }

   private boolean check16(GenericContainerScreen genericContainerScreen) {
      if (genericContainerScreen == null) {
         return false;
      } else {
         String text14 = this.resolve6(genericContainerScreen.getTitle().getString());
         if (text14.contains("подтверждение покупки")) {
            return this.compute18(genericContainerScreen.getScreenHandler()) != -1;
         } else {
            ScreenHandler screenHandler4 = genericContainerScreen.getScreenHandler();
            return this.compute18(screenHandler4) != -1 && this.check18(screenHandler4);
         }
      }
   }

   private boolean check17(GenericContainerScreen genericContainerScreen) {
      if (genericContainerScreen == null) {
         return false;
      } else {
         String text15 = this.resolve6(genericContainerScreen.getTitle().getString());
         return text15.contains("продажа") || text15.contains("sellgui") || text15.contains("sell gui");
      }
   }

   private int compute18(ScreenHandler screenHandler) {
      int intValue49 = Math.min(screenHandler.slots.size(), Math.max(0, screenHandler.slots.size() - 36));

      for (int intValue50 = intValue49 - 1; intValue50 >= 0; intValue50--) {
         ItemStack itemStack9 = screenHandler.getSlot(intValue50).getStack();
         String text16 = this.resolve6(itemStack9.getName().getString());
         if (text16.contains("купить")
            || itemStack9.isOf(Items.LIME_STAINED_GLASS_PANE)
            || itemStack9.isOf(Items.GREEN_STAINED_GLASS_PANE)
            || itemStack9.isOf(Items.GREEN_CONCRETE)
            || itemStack9.isOf(Items.LIME_CONCRETE)) {
            return intValue50;
         }
      }

      return -1;
   }

   private boolean check18(ScreenHandler screenHandler) {
      int intValue51 = Math.min(screenHandler.slots.size(), Math.max(0, screenHandler.slots.size() - 36));

      for (int intValue52 = 0; intValue52 < intValue51; intValue52++) {
         ItemStack itemStack10 = screenHandler.getSlot(intValue52).getStack();
         if (itemStack10.isOf(Items.RED_STAINED_GLASS_PANE) || itemStack10.isOf(Items.RED_CONCRETE)) {
            return true;
         }
      }

      return false;
   }

   private void invoke37(GenericContainerScreen genericContainerScreen, int i, int j, SlotActionType slotActionType) {
      CLIENT.interactionManager
         .clickSlot(((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).syncId, i, j, slotActionType, CLIENT.player);
   }

   private int compute19(GenericContainerScreen genericContainerScreen) {
      int intValue53 = ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).getRows();
      int intValue54 = ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).slots.size();
      return Math.max(0, Math.min(intValue53 * 9, intValue54));
   }

   private BlockPos resolve() {
      if (CLIENT.player != null && CLIENT.world != null) {
         BlockPos blockPos2 = CLIENT.player.getBlockPos();
         BlockPos blockPos3 = null;
         double doubleValue = Double.MAX_VALUE;

         for (BlockPos blockPos4 : BlockPos.iterate(blockPos2.add(-5, -5, -5), blockPos2.add(5, 5, 5))) {
            BlockPos blockPos5 = blockPos4.toImmutable();
            if (this.check19(blockPos5)) {
               double doubleValue2 = CLIENT.player.squaredDistanceTo(Vec3d.ofCenter(blockPos5));
               if (doubleValue2 < doubleValue) {
                  doubleValue = doubleValue2;
                  blockPos3 = blockPos5;
               }
            }
         }

         return blockPos3;
      } else {
         return null;
      }
   }

   private boolean check19(BlockPos blockPos) {
      return CLIENT.world != null && CLIENT.world.getBlockState(blockPos).isOf(Blocks.CRAFTING_TABLE);
   }

   private Rotation resolve2(Vec3d vec3d) {
      if (CLIENT.player == null) {
         return new Rotation(0.0F, 0.0F);
      } else {
         Vec3d vec3d2 = CLIENT.player.getEyePos();
         double doubleValue3 = vec3d.x - vec3d2.x;
         double doubleValue4 = vec3d.y - vec3d2.y;
         double doubleValue5 = vec3d.z - vec3d2.z;
         float floatValue = (float)Math.toDegrees(Math.atan2(doubleValue5, doubleValue3)) - 90.0F;
         float floatValue2 = (float)(-Math.toDegrees(Math.atan2(doubleValue4, Math.sqrt(doubleValue3 * doubleValue3 + doubleValue5 * doubleValue5))));
         return new Rotation(floatValue, floatValue2);
      }
   }

   private Direction resolve3(BlockPos blockPos) {
      Vec3d vec3d3 = CLIENT.player.getPos();
      Vec3d vec3d4 = Vec3d.ofCenter(blockPos);
      double doubleValue6 = vec3d3.x - vec3d4.x;
      double doubleValue7 = vec3d3.z - vec3d4.z;
      if (Math.abs(doubleValue6) > Math.abs(doubleValue7)) {
         return doubleValue6 > 0.0 ? Direction.EAST : Direction.WEST;
      } else {
         return doubleValue7 > 0.0 ? Direction.SOUTH : Direction.NORTH;
      }
   }

   private void invoke38(String string) {
      if (CLIENT.player != null && string != null && !string.isBlank()) {
         CLIENT.player.networkHandler.sendChatCommand("ah search " + string.trim());
      }
   }

   private void invoke39(String string) {
      if (CLIENT.player != null && string != null && !string.isBlank()) {
         CLIENT.player.networkHandler.sendChatCommand("ah " + string.trim());
      }
   }

   private boolean check20(GenericContainerScreen genericContainerScreen) {
      if (genericContainerScreen != null && CLIENT.player != null) {
         String text17 = this.resolve6(genericContainerScreen.getTitle().getString());
         String text18 = this.resolve6(CLIENT.player.getName().getString());
         return AhHelper.check(genericContainerScreen)
            || text17.contains(text18)
            || text17.contains("мои товары")
            || text17.contains("мои предметы")
            || text17.contains("поиск:");
      } else {
         return false;
      }
   }

   private boolean check21() {
      return System.currentTimeMillis() - this.timestamp >= 1200L;
   }

   private void invoke40(long l) {
      CLIENT.player.networkHandler.sendChatCommand("ah sellgui " + l);
      this.timestamp = System.currentTimeMillis();
   }

   private void invoke41() {
      this.flag6 = false;
      this.intValue6 = 0;
      this.intValue7 = 0;
      if (this.intValue8 < 0) {
         this.intValue8 = 0;
      }
   }

   private String resolve4() {
      return this.check22() ? "Изумрудный меч" : "Изумрудная кирка";
   }

   private boolean check22() {
      return this.predmet.is("Изумрудный меч");
   }

   private long compute20() {
      return this.compute22(this.tsenaProdazhi.getValue());
   }

   private long compute21() {
      return this.compute22(this.maksTsenaDereva.getValue());
   }

   private long compute22(String string) {
      if (string == null) {
         return 0L;
      } else {
         String text19 = string.toLowerCase(Locale.ROOT).replace(" ", "").replace("_", "").replace(",", "").replace(".", "");
         long longValue7 = 1L;
         if (text19.endsWith("тысяч")) {
            longValue7 = 1000L;
            text19 = text19.substring(0, text19.length() - 5);
         } else if (text19.endsWith("тысячи")) {
            longValue7 = 1000L;
            text19 = text19.substring(0, text19.length() - 6);
         } else if (text19.endsWith("тысяча")) {
            longValue7 = 1000L;
            text19 = text19.substring(0, text19.length() - 6);
         } else if (text19.endsWith("тыс")) {
            longValue7 = 1000L;
            text19 = text19.substring(0, text19.length() - 3);
         } else if (text19.endsWith("k") || text19.endsWith("к")) {
            longValue7 = 1000L;
            text19 = text19.substring(0, text19.length() - 1);
         } else if (text19.endsWith("m") || text19.endsWith("м")) {
            longValue7 = 1000000L;
            text19 = text19.substring(0, text19.length() - 1);
         }

         String text20 = text19.replaceAll("[^0-9]", "");
         if (text20.isEmpty()) {
            return 0L;
         } else {
            try {
               return Math.multiplyExact(Long.parseLong(text20), longValue7);
            } catch (NumberFormatException | ArithmeticException exception) {
               return 0L;
            }
         }
      }
   }

   private String resolve5(long l) {
      return l <= 0L ? "0" : Long.toString(l);
   }

   private long compute23() {
      return Math.max(50L, (long)Math.round(this.zaderzhkaMs.getValue()));
   }

   private void invoke42() {
      if (this.dualTimer2.check5(10000L)) {
         ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
         float floatValue3 = threadLocalRandom.nextFloat() * 10.0F - 5.0F;
         float floatValue4 = threadLocalRandom.nextFloat() * 6.0F - 3.0F;
         CLIENT.player.setYaw(CLIENT.player.getYaw() + floatValue3);
         CLIENT.player.setPitch(Math.max(-90.0F, Math.min(90.0F, CLIENT.player.getPitch() + floatValue4)));
         this.dualTimer2.invoke();
      }
   }

   private void invoke43() {
      this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.IDLE;
      this.blockPos = null;
      this.moneyFarmState = MoneyFarm.MoneyFarmState.ITEM;
      this.intValue = 0;
      this.intValue2 = 0;
      this.intValue3 = 0;
      this.intValue4 = 0;
      this.intValue5 = 0;
      this.flag = false;
      this.flag2 = false;
      this.flag3 = false;
      this.flag4 = false;
      this.flag5 = false;
      this.flag6 = false;
      this.intValue6 = 0;
      this.intValue7 = 0;
      this.intValue8 = 0;
      this.intValue9 = 0;
      this.flag7 = false;
      this.intValue10 = 0;
      this.timestamp = 0L;
      this.timestamp2 = 0L;
      this.dualTimer.invoke();
      this.dualTimer2.invoke();
      this.dualTimer3.invoke();
      this.dualTimer4.invoke();
      this.dualTimer5.invoke();
   }

   private void invoke44(String string) {
      this.invoke47(string);
      this.invoke45();
   }

   private void invoke45() {
      this.moneyFarmState2 = MoneyFarm.MoneyFarmState2.IDLE;
      this.intValue10 = 0;
      this.dualTimer.invoke();
   }

   private void invoke46() {
      if (CLIENT.player != null) {
         CLIENT.player.closeHandledScreen();
      }
   }

   private boolean check23(long l) {
      if (CLIENT.currentScreen == null) {
         this.intValue10 = 0;
         return true;
      } else if (!this.dualTimer.check5(l)) {
         return false;
      } else {
         this.invoke46();
         this.intValue10++;
         if (this.intValue10 >= 20) {
            CLIENT.setScreen(null);
            this.intValue10 = 0;
            return true;
         } else {
            this.dualTimer.invoke();
            return false;
         }
      }
   }

   private String resolve6(String string) {
      return string == null ? "" : string.replaceAll("(?i)§.", "").replaceAll("(?i)&.", "").toLowerCase(Locale.ROOT).trim();
   }

   private void invoke47(String string) {
      if (this.uvedomleniya.isEnabled() && CLIENT.player != null) {
         ChatUtil.sendClientMessage("§8[§aMoneyFarm§8] §f" + string);
      }
   }

   static enum MoneyFarmState {
      ITEM,
      PLANKS,
      STICKS;
   }

   static enum MoneyFarmState2 {
      IDLE,
      BUY_WOOD_SEARCHING,
      BUY_WOOD_WAITING_AUCTION,
      BUY_WOOD_READING_AUCTION,
      BUY_WOOD_WAITING_CONFIRM,
      BUY_WOOD_CONFIRMING,
      BUY_WOOD_CLOSING,
      BUY_OPENING_SHOP,
      BUY_WAITING_SHOP,
      BUY_FIND_GOLD_INGOT,
      BUY_WAITING_EMERALD_MENU,
      BUY_FIND_EMERALD,
      BUY_WAITING_CONFIRM,
      BUY_CLICK_LIME_PANE,
      BUY_CLOSING_SHOP,
      CHECK_SELL_GUI_OPENING,
      CHECK_SELL_GUI_WAITING,
      CHECK_SELL_GUI_READING,
      FINDING_CRAFTING_TABLE,
      AIMING_CRAFTING_TABLE,
      OPENING_CRAFTING_TABLE,
      PLACING_ITEMS,
      TAKING_RESULT,
      CLOSING_CRAFTING,
      SELLING,
      WAITING_SELL_RESULT,
      RESALE_SEARCH_OWN_AH,
      RESALE_WAITING_OWN_AH,
      RESALE_TAKE_ITEM,
      RESALE_CLOSING,
      RESALE_SELLING,
      RESALE_WAIT_SELL_RESULT;
   }
}
