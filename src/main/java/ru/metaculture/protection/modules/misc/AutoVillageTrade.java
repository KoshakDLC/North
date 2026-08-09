package ru.metaculture.protection;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.pathing.goals.GoalBlock;
import baritone.api.pathing.goals.GoalNear;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import lombok.Generated;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.MerchantScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.SelectMerchantTradeC2SPacket;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.MerchantScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "AutoVillageTrade",
   category = Category.Misc,
   description = "Автоматически покупает товары у жителей",
   riskLevels = {ModuleRiskLevel.VIP}
)
public class AutoVillageTrade extends Module {
   private static final String ZOLOTOY_SLITOK = "Золотой слиток";
   private static final String REDSTOUN = "Редстоун";
   private static final String LAZURIT = "Лазурит";
   private static final String ZHEMCHUG_ENDERA = "Жемчуг Эндера";
   private static final String BUTYLOCHKA_OPYTA = "Бутылочка опыта";
   private static final String STEKLO = "Стекло";
   private static final String BIRKA = "Бирка";
   private static final String STRELY = "Стрелы";
   private static final String HLEB = "Хлеб";
   private static final String ZOLOTAYA_MORKOV = "Золотая морковь";
   private static final String KVARTSEVYY_BLOK = "Кварцевый блок";
   private static final String SEDLO = "Седло";
   private static final long TIMESTAMP = 10000L;
   private static final int INT_VALUE = 2;
   private static final int INT_VALUE_2 = 64;
   private static final long TIMESTAMP_2 = 2500L;
   private static final long TIMESTAMP_3 = 10000L;
   public final ModeSetting chtoPokupat = new ModeSetting(
      "Что покупать",
      "Золотой слиток",
      "Золотой слиток",
      "Редстоун",
      "Лазурит",
      "Жемчуг Эндера",
      "Бутылочка опыта",
      "Стекло",
      "Бирка",
      "Стрелы",
      "Хлеб",
      "Золотая морковь",
      "Кварцевый блок",
      "Седло"
   );
   private final NumberSetting maksTsena = new NumberSetting("Макс. цена", 64.0F, 1.0F, 64.0F, 1.0F, false);
   private final NumberSetting zapasIzumrudov = new NumberSetting("Запас изумрудов", 64.0F, 0.0F, 2304.0F, 64.0F, false);
   private final NumberSetting radiusZhitelya = new NumberSetting("Радиус жителя", 4.0F, 2.0F, 8.0F, 0.5F, false);
   private final NumberSetting zaderzhkaMs = new NumberSetting("Задержка (мс)", 120.0F, 50.0F, 1000.0F, 10.0F, false);
   private final NumberSetting kdReskanaSek = new NumberSetting("КД рескана (сек)", 45.0F, 5.0F, 300.0F, 5.0F, false);
   private final BooleanSetting avtoIzumrudy = new BooleanSetting("Авто-изумруды", true);
   private final KeybindSetting tochka = new KeybindSetting("Точка", -1);
   private final KeybindSetting sunduk = new KeybindSetting("Сундук", -1);
   private final DynamicButtonSetting sbrosTochek = new DynamicButtonSetting("Сброс точек", 0, this::resolve2) {
      @Override
      public void invoke8() {
         AutoVillageTrade.this.invoke2();
      }
   };
   public final BooleanSetting neOtobrazhatEkran = new BooleanSetting("Не отображать экран", false);
   private static BlockPos blockPos;
   private static BlockPos blockPos2;
   private static BlockPos blockPos3;
   private final RotationResetController rotationResetController = new RotationResetController();
   private final DualTimer dualTimer = new DualTimer();
   private final ResettableTimer resettableTimer = new ResettableTimer();
   private final Map<UUID, AutoVillageTrade.AutoVillageTradeState2> valuesByKey = new HashMap<>();
   private final List<BlockPos> items = new ArrayList<>();
   private AutoVillageTrade.AutoVillageTradeState autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.IDLE;
   private UUID uUID;
   private int intValue = -1;
   private int intValue2;
   private int intValue3;
   private int intValue4;
   private long timestamp;
   private long timestamp2;
   private BlockPos blockPos4;
   private int intValue5 = -1;
   private Boolean booleanValue;
   private Boolean booleanValue2;
   private String zolotoySlitok = "Золотой слиток";
   private int intValue6 = -1;
   private int intValue7;
   private int intValue8 = -1;
   private long timestamp3;
   private Screen screen;

   public AutoVillageTrade() {
      this.addSettings(
         new Setting[]{
            this.tochka,
            this.sunduk,
            this.sbrosTochek,
            this.chtoPokupat,
            this.maksTsena,
            this.zapasIzumrudov,
            this.radiusZhitelya,
            this.zaderzhkaMs,
            this.kdReskanaSek,
            this.avtoIzumrudy,
            this.neOtobrazhatEkran
         }
      );
   }

   public AutoVillageTrade.AutoVillageTradePriceData resolve(VillagerEntity villagerEntity) {
      if (this.enabled && villagerEntity != null) {
         AutoVillageTrade.AutoVillageTradeState2 autoVillageTradeState2 = this.valuesByKey.get(villagerEntity.getUuid());
         if (autoVillageTradeState2 != null && autoVillageTradeState2.flag && autoVillageTradeState2.intValue4 != Integer.MAX_VALUE) {
            int intValue = Math.max(0, (autoVillageTradeState2.intValue7 - autoVillageTradeState2.intValue6) * autoVillageTradeState2.intValue5);
            boolean flag = !autoVillageTradeState2.flag2 && autoVillageTradeState2.intValue4 <= this.compute10() && intValue > 0;
            ItemStack itemStack2 = new ItemStack(this.resolve10(), Math.max(1, Math.min(99, intValue)));
            return new AutoVillageTrade.AutoVillageTradePriceData(itemStack2, autoVillageTradeState2.intValue4, autoVillageTradeState2.intValue5, intValue, flag);
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.invoke37(false);
      this.zolotoySlitok = this.chtoPokupat.getValue();
      this.invoke31();
      this.invoke28();
      if (this.items.isEmpty()) {
         this.invoke38("Установи две точки маршрута через бинд «Точка».");
      } else {
         this.invoke25();
      }
   }

   @Override
   public void onDisable() {
      this.invoke30();
      this.invoke32();
      this.rotationResetController.invoke4();
      this.invoke37(true);
      super.onDisable();
   }

   @EventHandler
   public void onRawInput(RawInputEvent rawInputEvent) {
      if (CLIENT.player != null && this.resettableTimer.check3(300L)) {
         if (rawInputEvent.getKeyCode() == this.sunduk.getKeyCode()) {
            this.invoke();
            this.resettableTimer.invoke();
         } else if (rawInputEvent.getKeyCode() == this.tochka.getKeyCode()) {
            if (this.intValue4 == 0) {
               blockPos = CLIENT.player.getBlockPos();
               blockPos2 = null;
               this.intValue4 = 1;
               this.invoke38("Точка 1: " + blockPos.toShortString());
            } else if (this.intValue4 == 1) {
               blockPos2 = CLIENT.player.getBlockPos();
               this.intValue4 = 2;
               this.invoke38("Точка 2: " + blockPos2.toShortString());
            } else {
               blockPos = CLIENT.player.getBlockPos();
               blockPos2 = null;
               this.intValue4 = 1;
               this.invoke38("Точки сброшены. Точка 1: " + blockPos.toShortString());
            }

            this.invoke28();
            if (this.enabled && !this.items.isEmpty()) {
               this.invoke25();
            }

            this.resettableTimer.invoke();
         }
      }
   }

   @EventHandler
   public void onScreenOpen(ScreenOpenEvent screenOpenEvent) {
      if (this.neOtobrazhatEkran.isEnabled() && this.check6(screenOpenEvent.getScreen())) {
         this.screen = screenOpenEvent.getScreen();
         screenOpenEvent.invoke();
      }
   }

   private void invoke() {
      if (CLIENT.crosshairTarget instanceof BlockHitResult blockHitResult && CLIENT.world != null) {
         BlockPos blockPos2 = blockHitResult.getBlockPos();
         if (!this.check2(blockPos2)) {
            this.invoke38("Это не сундук и не бочка.");
         } else {
            blockPos3 = blockPos2;
            this.invoke38("Сундук для складирования установлен: " + blockPos2.toShortString());
         }
      } else {
         this.invoke38("Наведи прицел на сундук или бочку.");
      }
   }

   void invoke2() {
      blockPos = null;
      blockPos2 = null;
      this.intValue4 = 0;
      this.items.clear();
      this.valuesByKey.clear();
      this.uUID = null;
      this.intValue = -1;
      this.intValue2 = 0;
      this.blockPos4 = null;
      this.intValue5 = -1;
      this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.IDLE;
      this.invoke35();
      this.invoke30();
      this.dualTimer.invoke();
      this.invoke38("Точки маршрута сброшены.");
   }

   private String resolve2() {
      if (blockPos == null && blockPos2 == null) {
         return "Точки не заданы";
      } else {
         return blockPos2 == null ? "Сбросить 1 точку" : "Сбросить 2 точки";
      }
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player != null && CLIENT.world != null && CLIENT.interactionManager != null && CLIENT.getNetworkHandler() != null) {
         if (PlayerHelper.check()) {
            this.invoke30();
            this.rotationResetController.invoke4();
         } else {
            if (this.items.isEmpty()) {
               this.invoke28();
               if (this.items.isEmpty()) {
                  return;
               }

               this.invoke25();
            }

            if (!this.chtoPokupat.getValue().equals(this.zolotoySlitok)) {
               this.zolotoySlitok = this.chtoPokupat.getValue();
               this.valuesByKey.clear();
               this.invoke35();
               this.invoke25();
            } else {
               switch (this.autoVillageTradeState) {
                  case IDLE:
                     this.invoke3();
                     break;
                  case SCAN_ROUTE:
                     this.invoke4();
                     break;
                  case OPEN_SCAN:
                     this.invoke6(AutoVillageTrade.AutoVillageTradeState.WAIT_SCAN_SCREEN);
                     break;
                  case WAIT_SCAN_SCREEN:
                     this.invoke7(AutoVillageTrade.AutoVillageTradeState.READ_SCAN_SCREEN, AutoVillageTrade.AutoVillageTradeState.SCAN_ROUTE);
                     break;
                  case READ_SCAN_SCREEN:
                     this.invoke8();
                     break;
                  case CLOSE_SCAN_SCREEN:
                     this.invoke10(AutoVillageTrade.AutoVillageTradeState.SCAN_ROUTE);
                     break;
                  case MOVE_TO_TRADE:
                     this.invoke5();
                     break;
                  case OPEN_TRADE:
                     this.invoke6(AutoVillageTrade.AutoVillageTradeState.WAIT_TRADE_SCREEN);
                     break;
                  case WAIT_TRADE_SCREEN:
                     this.invoke7(AutoVillageTrade.AutoVillageTradeState.BUY_TRADE, AutoVillageTrade.AutoVillageTradeState.IDLE);
                     break;
                  case BUY_TRADE:
                     this.invoke9();
                     break;
                  case CLOSE_TRADE_SCREEN:
                     this.invoke10(AutoVillageTrade.AutoVillageTradeState.IDLE);
                     break;
                  case MOVE_TO_STORAGE:
                     this.invoke12();
                     break;
                  case OPEN_STORAGE:
                     this.invoke13();
                     break;
                  case WAIT_STORAGE_SCREEN:
                     this.invoke14();
                     break;
                  case PUT_STORAGE:
                     this.invoke15();
                     break;
                  case BUY_EMERALDS_OPEN_SHOP:
                     this.invoke17();
                     break;
                  case BUY_EMERALDS_WAIT_SHOP:
                     this.invoke18();
                     break;
                  case BUY_EMERALDS_FIND_GOLD:
                     this.invoke19();
                     break;
                  case BUY_EMERALDS_WAIT_MENU:
                     this.invoke20();
                     break;
                  case BUY_EMERALDS_FIND_EMERALD:
                     this.invoke21();
                     break;
                  case BUY_EMERALDS_WAIT_CONFIRM:
                     this.invoke22();
                     break;
                  case BUY_EMERALDS_CONFIRM:
                     this.invoke23();
                     break;
                  case BUY_EMERALDS_CLOSE:
                     this.invoke24();
                     break;
                  case WAIT_RESTOCK:
                     this.invoke16();
               }
            }
         }
      }
   }

   private void invoke3() {
      if (this.check7()) {
         this.invoke11();
      } else if (!this.avtoIzumrudy.isEnabled() || !this.check10()) {
         AutoVillageTrade.AutoVillageTradeState2 autoVillageTradeState22 = this.resolve4();
         if (autoVillageTradeState22 != null) {
            this.uUID = autoVillageTradeState22.uUID;
            this.intValue = autoVillageTradeState22.intValue3;
            this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.MOVE_TO_TRADE;
            this.dualTimer.invoke();
         } else if (this.check()) {
            this.invoke25();
         } else {
            this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.WAIT_RESTOCK;
            this.dualTimer.invoke();
         }
      } else if (!this.check8()) {
         this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.BUY_EMERALDS_OPEN_SHOP;
         this.dualTimer.invoke();
      }
   }

   private void invoke4() {
      if (this.intValue2 >= this.items.size()) {
         this.invoke26();
         this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.IDLE;
         this.dualTimer.invoke();
      } else {
         BlockPos blockPos3 = this.items.get(this.intValue2);
         this.invoke29(blockPos3, 0);
         if (this.check14(blockPos3, 1.2)) {
            VillagerEntity villagerEntity2 = this.resolve5(blockPos3);
            if (villagerEntity2 == null) {
               this.intValue2++;
            } else {
               this.uUID = villagerEntity2.getUuid();
               this.intValue = -1;
               this.invoke30();
               this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.OPEN_SCAN;
               this.dualTimer.invoke();
            }
         }
      }
   }

   private void invoke5() {
      AutoVillageTrade.AutoVillageTradeState2 autoVillageTradeState23 = this.resolve7();
      if (autoVillageTradeState23 == null) {
         this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.IDLE;
      } else if (this.compute8(Items.EMERALD) < Math.max(1, autoVillageTradeState23.intValue4)) {
         if (!this.check11()) {
            this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.IDLE;
            this.dualTimer.invoke();
         } else if (!this.check8()) {
            this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.BUY_EMERALDS_OPEN_SHOP;
            this.dualTimer.invoke();
         }
      } else {
         VillagerEntity villagerEntity3 = this.resolve6(autoVillageTradeState23.uUID);
         if (villagerEntity3 != null) {
            autoVillageTradeState23.blockPos = villagerEntity3.getBlockPos();
            autoVillageTradeState23.intValue = villagerEntity3.getId();
         }

         this.invoke29(autoVillageTradeState23.blockPos, 2);
         if (this.check14(autoVillageTradeState23.blockPos, (double)(this.radiusZhitelya.getValue() + 0.5F))) {
            this.invoke30();
            this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.OPEN_TRADE;
            this.dualTimer.invoke();
         }
      }
   }

   private void invoke6(AutoVillageTrade.AutoVillageTradeState autoVillageTradeState) {
      VillagerEntity villagerEntity4 = this.resolve6(this.uUID);
      if (villagerEntity4 != null && villagerEntity4.isAlive()) {
         if (CLIENT.player.squaredDistanceTo(villagerEntity4.getPos()) > this.measure3(this.radiusZhitelya.getValue() + 1.0F)) {
            this.invoke27();
            this.autoVillageTradeState = autoVillageTradeState == AutoVillageTrade.AutoVillageTradeState.WAIT_SCAN_SCREEN ? AutoVillageTrade.AutoVillageTradeState.SCAN_ROUTE : AutoVillageTrade.AutoVillageTradeState.IDLE;
            this.dualTimer.invoke();
         } else {
            Rotation rotation = this.resolve11(villagerEntity4.getEyePos());
            this.rotationResetController.invoke2(rotation, 45.0F, 45.0F, 2, 15);
            if (!(new Rotation(CLIENT.player).measure(rotation) > 7.0F)) {
               if (this.dualTimer.check5(this.compute9())) {
                  CLIENT.interactionManager.interactEntity(CLIENT.player, villagerEntity4, Hand.MAIN_HAND);
                  CLIENT.player.swingHand(Hand.MAIN_HAND);
                  this.autoVillageTradeState = autoVillageTradeState;
                  this.dualTimer.invoke();
               }
            }
         }
      } else {
         this.invoke27();
         this.autoVillageTradeState = autoVillageTradeState == AutoVillageTrade.AutoVillageTradeState.WAIT_SCAN_SCREEN ? AutoVillageTrade.AutoVillageTradeState.SCAN_ROUTE : AutoVillageTrade.AutoVillageTradeState.IDLE;
         this.dualTimer.invoke();
      }
   }

   private void invoke7(AutoVillageTrade.AutoVillageTradeState autoVillageTradeState3, AutoVillageTrade.AutoVillageTradeState autoVillageTradeState4) {
      if (CLIENT.player.currentScreenHandler instanceof MerchantScreenHandler) {
         this.autoVillageTradeState = autoVillageTradeState3;
         this.dualTimer.invoke();
      } else {
         if (this.dualTimer.check5(2500L)) {
            this.invoke27();
            this.autoVillageTradeState = autoVillageTradeState4;
            this.dualTimer.invoke();
         }
      }
   }

   private void invoke8() {
      if (CLIENT.player.currentScreenHandler instanceof MerchantScreenHandler merchantScreenHandler) {
         this.resolve3(this.uUID, merchantScreenHandler.getRecipes());
         this.invoke35();
         this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.CLOSE_SCAN_SCREEN;
         this.dualTimer.invoke();
      } else {
         this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.SCAN_ROUTE;
         this.dualTimer.invoke();
      }
   }

   private void invoke9() {
      if (!(CLIENT.player.currentScreenHandler instanceof MerchantScreenHandler merchantScreenHandler2)) {
         this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.IDLE;
         this.dualTimer.invoke();
      } else {
         AutoVillageTrade.AutoVillageTradeState2 autoVillageTradeState24 = this.resolve3(this.uUID, merchantScreenHandler2.getRecipes());
         if (autoVillageTradeState24 != null && autoVillageTradeState24.flag && autoVillageTradeState24.intValue3 >= 0 && !autoVillageTradeState24.flag2 && autoVillageTradeState24.intValue4 <= this.compute10()) {
            this.intValue = autoVillageTradeState24.intValue3;
            if (this.compute8(Items.EMERALD) < autoVillageTradeState24.intValue4) {
               this.invoke35();
               if (!this.check11()) {
                  this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.CLOSE_TRADE_SCREEN;
                  this.dualTimer.invoke();
               } else if (!this.check8()) {
                  this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.BUY_EMERALDS_OPEN_SHOP;
                  this.dualTimer.invoke();
               }
            } else if (this.check13(this.resolve10())) {
               if (this.dualTimer.check5(this.compute9())) {
                  merchantScreenHandler2.setRecipeIndex(this.intValue);
                  merchantScreenHandler2.switchTo(this.intValue);
                  CLIENT.getNetworkHandler().sendPacket(new SelectMerchantTradeC2SPacket(this.intValue));
                  Slot slot = merchantScreenHandler2.getSlot(2);
                  if (slot.hasStack() && slot.getStack().isOf(this.resolve10())) {
                     int intValue2 = Math.max(1, slot.getStack().getCount());
                     CLIENT.interactionManager.clickSlot(merchantScreenHandler2.syncId, 2, 0, SlotActionType.QUICK_MOVE, CLIENT.player);
                     autoVillageTradeState24.intValue8 += intValue2;
                     autoVillageTradeState24.intValue9 = autoVillageTradeState24.intValue9 + autoVillageTradeState24.intValue4;
                     autoVillageTradeState24.timestamp2 = System.currentTimeMillis();
                     this.dualTimer.invoke();
                  } else {
                     this.dualTimer.invoke();
                  }
               }
            } else {
               if (blockPos3 != null && this.compute8(this.resolve10()) > 0) {
                  this.invoke35();
                  this.invoke11();
               } else {
                  this.invoke38("Инвентарь заполнен. Установи сундук для складирования.");
                  this.invoke35();
                  this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.CLOSE_TRADE_SCREEN;
               }

               this.dualTimer.invoke();
            }
         } else {
            this.invoke35();
            this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.CLOSE_TRADE_SCREEN;
            this.dualTimer.invoke();
         }
      }
   }

   private void invoke10(AutoVillageTrade.AutoVillageTradeState autoVillageTradeState5) {
      if (this.dualTimer.check5(150L)) {
         if (this.check5()) {
            this.invoke35();
            this.dualTimer.invoke();
         } else {
            this.autoVillageTradeState = autoVillageTradeState5;
            this.dualTimer.invoke();
         }
      }
   }

   private void invoke11() {
      if (blockPos3 == null) {
         this.invoke38("Сундук для складирования не установлен.");
         this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.IDLE;
         this.dualTimer.invoke();
      } else if (!this.check2(blockPos3)) {
         this.invoke38("Сундук для складирования недоступен.");
         this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.IDLE;
         this.dualTimer.invoke();
      } else {
         this.invoke30();
         this.invoke35();
         this.intValue6 = -1;
         this.intValue7 = 0;
         this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.MOVE_TO_STORAGE;
         this.dualTimer.invoke();
      }
   }

   private void invoke12() {
      if (blockPos3 != null && this.check2(blockPos3)) {
         this.invoke29(blockPos3, 2);
         if (this.check14(blockPos3, 3.5)) {
            this.invoke30();
            this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.OPEN_STORAGE;
            this.dualTimer.invoke();
         }
      } else {
         this.invoke38("Сундук для складирования недоступен.");
         this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.IDLE;
         this.dualTimer.invoke();
      }
   }

   private void invoke13() {
      if (blockPos3 == null || !this.check2(blockPos3)) {
         this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.IDLE;
         this.dualTimer.invoke();
      } else if (this.resolve9() != null) {
         this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.PUT_STORAGE;
         this.dualTimer.invoke();
      } else if (this.check3()) {
         this.dualTimer.invoke();
      } else {
         Rotation rotation2 = this.resolve11(Vec3d.ofCenter(blockPos3));
         this.rotationResetController.invoke2(rotation2, 35.0F, 35.0F, 4, 15);
         if (!(new Rotation(CLIENT.player).measure(rotation2) > 4.0F)) {
            if (this.dualTimer.check5(this.compute9())) {
               this.invoke34(blockPos3);
               this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.WAIT_STORAGE_SCREEN;
               this.dualTimer.invoke();
            }
         }
      }
   }

   private void invoke14() {
      if (this.resolve9() != null) {
         this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.PUT_STORAGE;
         this.dualTimer.invoke();
      } else {
         if (this.dualTimer.check5(2500L)) {
            this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.OPEN_STORAGE;
            this.dualTimer.invoke();
         }
      }
   }

   private void invoke15() {
      GenericContainerScreen genericContainerScreen2 = this.resolve9();
      if (genericContainerScreen2 == null) {
         this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.IDLE;
         this.dualTimer.invoke();
      } else {
         int intValue3 = this.compute8(this.resolve10());
         if (intValue3 <= 0) {
            this.invoke35();
            this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.IDLE;
            this.dualTimer.invoke();
         } else if (this.dualTimer.check5(150L)) {
            if (this.intValue6 == intValue3) {
               this.intValue7++;
               if (this.intValue7 >= 5) {
                  this.invoke38("Сундук заполнен или предмет не перекладывается.");
                  this.invoke35();
                  this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.IDLE;
                  this.dualTimer.invoke();
                  return;
               }
            } else {
               this.intValue7 = 0;
            }

            int intValue4 = this.compute3(genericContainerScreen2);
            if (intValue4 == -1) {
               this.invoke35();
               this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.IDLE;
               this.dualTimer.invoke();
            } else {
               this.intValue6 = intValue3;
               CLIENT.interactionManager
                  .clickSlot(((GenericContainerScreenHandler)genericContainerScreen2.getScreenHandler()).syncId, intValue4, 0, SlotActionType.QUICK_MOVE, CLIENT.player);
               this.dualTimer.invoke();
            }
         }
      }
   }

   private void invoke16() {
      BlockPos blockPos4 = this.resolve12();
      if (blockPos4 != null && !this.check14(blockPos4, 1.5)) {
         this.invoke29(blockPos4, 0);
      } else {
         this.invoke30();
      }

      if (this.dualTimer.check5(this.compute12())) {
         this.invoke25();
      }
   }

   private void invoke17() {
      if (!this.check11()) {
         this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.IDLE;
      } else if (!this.check8()) {
         if (this.check5()) {
            this.invoke35();
            this.dualTimer.invoke();
         } else if (this.dualTimer.check5(this.compute9())) {
            CLIENT.player.networkHandler.sendChatCommand("shop");
            this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.BUY_EMERALDS_WAIT_SHOP;
            this.dualTimer.invoke();
         }
      }
   }

   private void invoke18() {
      if (!this.check8()) {
         if (this.resolve9() != null) {
            this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.BUY_EMERALDS_FIND_GOLD;
            this.dualTimer.invoke();
         } else {
            if (this.dualTimer.check5(10000L)) {
               this.invoke38("Таймаут открытия /shop.");
               this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.IDLE;
               this.dualTimer.invoke();
            }
         }
      }
   }

   private void invoke19() {
      if (!this.check8()) {
         if (this.dualTimer.check5(this.compute9())) {
            GenericContainerScreen genericContainerScreen3 = this.resolve9();
            if (genericContainerScreen3 == null) {
               this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.IDLE;
               this.dualTimer.invoke();
            } else {
               int intValue5 = this.compute2(genericContainerScreen3, Items.GOLD_INGOT);
               if (intValue5 != -1) {
                  this.invoke33(genericContainerScreen3, intValue5, 0, SlotActionType.PICKUP);
                  this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.BUY_EMERALDS_WAIT_MENU;
                  this.dualTimer.invoke();
               } else {
                  if (this.dualTimer.check5(5000L)) {
                     this.invoke38("В /shop не найден раздел золотого слитка.");
                     this.invoke35();
                     this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.IDLE;
                     this.dualTimer.invoke();
                  }
               }
            }
         }
      }
   }

   private void invoke20() {
      if (!this.check8()) {
         if (this.dualTimer.check5(this.compute9())) {
            if (this.resolve9() == null) {
               this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.IDLE;
               this.dualTimer.invoke();
            } else {
               this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.BUY_EMERALDS_FIND_EMERALD;
               this.dualTimer.invoke();
            }
         }
      }
   }

   private void invoke21() {
      if (!this.check8()) {
         if (this.dualTimer.check5(this.compute9())) {
            GenericContainerScreen genericContainerScreen4 = this.resolve9();
            if (genericContainerScreen4 == null) {
               this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.IDLE;
               this.dualTimer.invoke();
            } else {
               int intValue6 = this.compute4(genericContainerScreen4);
               if (intValue6 != -1) {
                  this.invoke33(genericContainerScreen4, intValue6, 1, SlotActionType.PICKUP);
                  this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.BUY_EMERALDS_WAIT_CONFIRM;
                  this.dualTimer.invoke();
               } else {
                  if (this.dualTimer.check5(5000L)) {
                     this.invoke38("В /shop не найден слот изумрудов.");
                     this.invoke35();
                     this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.IDLE;
                     this.dualTimer.invoke();
                  }
               }
            }
         }
      }
   }

   private void invoke22() {
      if (!this.check8()) {
         if (this.dualTimer.check5(this.compute9())) {
            if (this.resolve9() == null) {
               this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.IDLE;
               this.dualTimer.invoke();
            } else {
               this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.BUY_EMERALDS_CONFIRM;
               this.dualTimer.invoke();
            }
         }
      }
   }

   private void invoke23() {
      if (!this.check8()) {
         if (this.dualTimer.check5(this.compute9())) {
            GenericContainerScreen genericContainerScreen5 = this.resolve9();
            if (genericContainerScreen5 == null) {
               this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.IDLE;
               this.dualTimer.invoke();
            } else {
               int intValue7 = this.compute5(genericContainerScreen5.getScreenHandler());
               if (intValue7 != -1) {
                  this.intValue8 = this.compute8(Items.EMERALD);
                  this.invoke33(genericContainerScreen5, intValue7, 0, SlotActionType.PICKUP);
                  this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.BUY_EMERALDS_CLOSE;
                  this.dualTimer.invoke();
               } else {
                  if (this.dualTimer.check5(5000L)) {
                     this.invoke38("Не найден слот подтверждения покупки изумрудов.");
                     this.invoke35();
                     this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.IDLE;
                     this.dualTimer.invoke();
                  }
               }
            }
         }
      }
   }

   private void invoke24() {
      if (this.dualTimer.check5(250L)) {
         this.invoke35();
         if (this.intValue8 < 0 || this.compute8(Items.EMERALD) > this.intValue8) {
            this.intValue8 = -1;
            if (blockPos3 != null && this.compute8(this.resolve10()) > 0) {
               this.invoke11();
            } else {
               this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.IDLE;
               this.dualTimer.invoke();
            }
         } else if (this.dualTimer.check5(2500L)) {
            this.invoke38("Покупка изумрудов не изменила инвентарь. Повтор временно остановлен.");
            this.intValue8 = -1;
            this.timestamp3 = System.currentTimeMillis() + 10000L;
            this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.IDLE;
            this.dualTimer.invoke();
         }
      }
   }

   private void invoke25() {
      this.intValue3++;
      this.intValue2 = 0;
      this.uUID = null;
      this.intValue = -1;
      this.blockPos4 = null;
      this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.SCAN_ROUTE;
      this.dualTimer.invoke();
      this.invoke38("Сканирую жителей: " + this.chtoPokupat.getValue() + ".");
   }

   private void invoke26() {
      this.timestamp = System.currentTimeMillis();
      this.invoke36(false);
   }

   private boolean check() {
      if (this.valuesByKey.isEmpty()) {
         return true;
      } else {
         long longValue = System.currentTimeMillis();
         return longValue - this.timestamp >= this.compute12()
            ? true
            : this.valuesByKey
               .values()
               .stream()
               .anyMatch(autoVillageTradeState25 -> autoVillageTradeState25.flag2 && longValue - autoVillageTradeState25.timestamp >= this.compute12());
      }
   }

   private AutoVillageTrade.AutoVillageTradeState2 resolve3(UUID uUID, TradeOfferList tradeOfferList) {
      if (uUID != null && tradeOfferList != null) {
         AutoVillageTrade.AutoVillageTradeState2 autoVillageTradeState26 = this.valuesByKey.computeIfAbsent(uUID, AutoVillageTrade.AutoVillageTradeState2::new);
         VillagerEntity villagerEntity5 = this.resolve6(uUID);
         if (villagerEntity5 != null) {
            autoVillageTradeState26.intValue = villagerEntity5.getId();
            autoVillageTradeState26.blockPos = villagerEntity5.getBlockPos();
         }

         autoVillageTradeState26.intValue2 = this.intValue3;
         autoVillageTradeState26.timestamp = System.currentTimeMillis();
         autoVillageTradeState26.flag = false;
         autoVillageTradeState26.intValue3 = -1;
         autoVillageTradeState26.intValue4 = Integer.MAX_VALUE;
         autoVillageTradeState26.intValue5 = 1;
         autoVillageTradeState26.flag2 = true;
         Item item2 = this.resolve10();

         for (int intValue8 = 0; intValue8 < tradeOfferList.size(); intValue8++) {
            TradeOffer tradeOffer2 = (TradeOffer)tradeOfferList.get(intValue8);
            ItemStack itemStack3 = tradeOffer2.getSellItem();
            if (!itemStack3.isEmpty() && itemStack3.isOf(item2)) {
               int intValue9 = this.compute(tradeOffer2);
               int intValue10 = Math.max(1, itemStack3.getCount());
               boolean flag2 = !autoVillageTradeState26.flag || this.measure2(intValue9, intValue10) < this.measure2(autoVillageTradeState26.intValue4, autoVillageTradeState26.intValue5);
               if (flag2) {
                  autoVillageTradeState26.flag = true;
                  autoVillageTradeState26.intValue3 = intValue8;
                  autoVillageTradeState26.intValue4 = intValue9;
                  autoVillageTradeState26.intValue5 = intValue10;
                  autoVillageTradeState26.intValue6 = tradeOffer2.getUses();
                  autoVillageTradeState26.intValue7 = tradeOffer2.getMaxUses();
                  autoVillageTradeState26.flag2 = tradeOffer2.isDisabled() || intValue9 > this.compute10();
               }
            }
         }

         return autoVillageTradeState26;
      } else {
         return null;
      }
   }

   private int compute(TradeOffer tradeOffer) {
      int intValue11 = 0;
      ItemStack itemStack4 = tradeOffer.getDisplayedFirstBuyItem();
      ItemStack itemStack5 = tradeOffer.getDisplayedSecondBuyItem();
      if (!itemStack4.isEmpty() && itemStack4.isOf(Items.EMERALD)) {
         intValue11 += itemStack4.getCount();
      }

      if (!itemStack5.isEmpty() && itemStack5.isOf(Items.EMERALD)) {
         intValue11 += itemStack5.getCount();
      }

      return intValue11 <= 0 ? Integer.MAX_VALUE : intValue11;
   }

   private AutoVillageTrade.AutoVillageTradeState2 resolve4() {
      int intValue12 = this.compute8(Items.EMERALD);
      boolean flag3 = this.check11();
      return this.valuesByKey
         .values()
         .stream()
         .filter(
            autoVillageTradeState27 -> autoVillageTradeState27.flag
               && !autoVillageTradeState27.flag2
               && autoVillageTradeState27.intValue3 >= 0
               && autoVillageTradeState27.intValue4 <= this.compute10()
         )
         .filter(autoVillageTradeState28 -> intValue12 >= autoVillageTradeState28.intValue4 || flag3)
         .min(
            Comparator.<AutoVillageTrade.AutoVillageTradeState2>comparingDouble(autoVillageTradeState29 -> this.measure2(autoVillageTradeState29.intValue4, autoVillageTradeState29.intValue5))
               .thenComparingDouble(autoVillageTradeState210 -> this.measure(autoVillageTradeState210.blockPos))
         )
         .orElse(null);
   }

   private VillagerEntity resolve5(BlockPos blockPos) {
      VillagerEntity villagerEntity6 = null;
      double doubleValue = Double.MAX_VALUE;
      double doubleValue2 = this.measure3(this.radiusZhitelya.getValue());

      for (Entity entity : CLIENT.world.getEntities()) {
         if (entity instanceof VillagerEntity villagerEntity7 && villagerEntity7.isAlive()) {
            AutoVillageTrade.AutoVillageTradeState2 autoVillageTradeState211 = this.valuesByKey.get(villagerEntity7.getUuid());
            if (autoVillageTradeState211 == null || autoVillageTradeState211.intValue2 != this.intValue3) {
               double doubleValue3 = this.measure3(blockPos.getX() + 0.5 - villagerEntity7.getX())
                  + this.measure3(blockPos.getY() + 0.5 - villagerEntity7.getY())
                  + this.measure3(blockPos.getZ() + 0.5 - villagerEntity7.getZ());
               if (!(doubleValue3 > doubleValue2) && !(doubleValue3 >= doubleValue)) {
                  villagerEntity6 = villagerEntity7;
                  doubleValue = doubleValue3;
               }
            }
         }
      }

      return villagerEntity6;
   }

   private VillagerEntity resolve6(UUID uUID) {
      if (uUID != null && CLIENT.world != null) {
         for (Entity entity2 : CLIENT.world.getEntities()) {
            if (entity2 instanceof VillagerEntity villagerEntity8 && uUID.equals(villagerEntity8.getUuid())) {
               return villagerEntity8;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private AutoVillageTrade.AutoVillageTradeState2 resolve7() {
      return this.uUID == null ? null : this.valuesByKey.get(this.uUID);
   }

   private void invoke27() {
      if (this.uUID != null) {
         AutoVillageTrade.AutoVillageTradeState2 autoVillageTradeState212 = this.valuesByKey.computeIfAbsent(this.uUID, AutoVillageTrade.AutoVillageTradeState2::new);
         autoVillageTradeState212.flag2 = true;
         autoVillageTradeState212.timestamp = System.currentTimeMillis();
         autoVillageTradeState212.intValue2 = this.intValue3;
      }
   }

   private void invoke28() {
      this.items.clear();
      if (blockPos != null && blockPos2 != null) {
         int intValue13 = blockPos2.getX() - blockPos.getX();
         int intValue14 = blockPos2.getZ() - blockPos.getZ();
         int intValue15 = Math.max(Math.abs(intValue13), Math.abs(intValue14));
         if (intValue15 == 0) {
            this.items.add(blockPos);
         } else {
            for (int intValue16 = 0; intValue16 <= intValue15; intValue16++) {
               int intValue17 = blockPos.getX() + Math.round(intValue13 * ((float)intValue16 / intValue15));
               int intValue18 = blockPos.getZ() + Math.round(intValue14 * ((float)intValue16 / intValue15));
               BlockPos blockPos5 = new BlockPos(intValue17, blockPos.getY(), intValue18);
               if (this.items.isEmpty() || !this.items.get(this.items.size() - 1).equals(blockPos5)) {
                  this.items.add(blockPos5);
               }
            }
         }
      }
   }

   private void invoke29(BlockPos blockPos, int i) {
      IBaritone iBaritone = BaritoneAPI.getProvider().getPrimaryBaritone();
      if (!blockPos.equals(this.blockPos4) || i != this.intValue5 || !iBaritone.getCustomGoalProcess().isActive()) {
         this.blockPos4 = blockPos;
         this.intValue5 = i;
         if (i > 0) {
            iBaritone.getCustomGoalProcess().setGoalAndPath(new GoalNear(blockPos, i));
         } else {
            iBaritone.getCustomGoalProcess().setGoalAndPath(new GoalBlock(blockPos));
         }
      }
   }

   private void invoke30() {
      this.blockPos4 = null;
      this.intValue5 = -1;

      try {
         BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().cancelEverything();
      } catch (Throwable exception) {
      }
   }

   private void invoke31() {
      this.booleanValue = (Boolean)BaritoneAPI.getSettings().allowBreak.value;
      this.booleanValue2 = (Boolean)BaritoneAPI.getSettings().allowPlace.value;
      BaritoneAPI.getSettings().allowBreak.value = false;
      BaritoneAPI.getSettings().allowPlace.value = false;
   }

   private void invoke32() {
      if (this.booleanValue != null) {
         BaritoneAPI.getSettings().allowBreak.value = this.booleanValue;
      }

      if (this.booleanValue2 != null) {
         BaritoneAPI.getSettings().allowPlace.value = this.booleanValue2;
      }

      this.booleanValue = null;
      this.booleanValue2 = null;
   }

   private int compute2(GenericContainerScreen genericContainerScreen, Item item) {
      int intValue19 = this.compute6(genericContainerScreen);

      for (int intValue20 = 0; intValue20 < intValue19; intValue20++) {
         Slot slot2 = ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).getSlot(intValue20);
         if (slot2.hasStack() && slot2.getStack().isOf(item)) {
            return intValue20;
         }
      }

      return -1;
   }

   private int compute3(GenericContainerScreen genericContainerScreen) {
      int intValue21 = this.compute6(genericContainerScreen);
      DefaultedList defaultedList = ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).slots;

      for (int intValue22 = intValue21; intValue22 < defaultedList.size(); intValue22++) {
         Slot slot3 = (Slot)defaultedList.get(intValue22);
         if (slot3.hasStack() && slot3.getStack().isOf(this.resolve10())) {
            return intValue22;
         }
      }

      return -1;
   }

   private int compute4(GenericContainerScreen genericContainerScreen) {
      int intValue23 = this.compute6(genericContainerScreen);

      for (int intValue24 = 0; intValue24 < intValue23; intValue24++) {
         Slot slot4 = ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).getSlot(intValue24);
         if (slot4.hasStack()) {
            ItemStack itemStack6 = slot4.getStack();
            String text = this.resolve13(itemStack6.getName().getString());
            if (itemStack6.isOf(Items.EMERALD)) {
               return intValue24;
            }

            if (itemStack6.isOf(Items.PAPER) && (text.contains("изумруд") || text.contains("emerald"))) {
               return intValue24;
            }
         }
      }

      return -1;
   }

   private int compute5(ScreenHandler screenHandler) {
      int intValue25 = Math.min(screenHandler.slots.size(), Math.max(0, screenHandler.slots.size() - 36));

      for (int intValue26 = intValue25 - 1; intValue26 >= 0; intValue26--) {
         ItemStack itemStack7 = screenHandler.getSlot(intValue26).getStack();
         String text2 = this.resolve13(itemStack7.getName().getString());
         if (text2.contains("купить")
            || itemStack7.isOf(Items.LIME_STAINED_GLASS_PANE)
            || itemStack7.isOf(Items.GREEN_STAINED_GLASS_PANE)
            || itemStack7.isOf(Items.GREEN_CONCRETE)
            || itemStack7.isOf(Items.LIME_CONCRETE)) {
            return intValue26;
         }
      }

      return -1;
   }

   private void invoke33(GenericContainerScreen genericContainerScreen, int i, int j, SlotActionType slotActionType) {
      CLIENT.interactionManager
         .clickSlot(((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).syncId, i, j, slotActionType, CLIENT.player);
   }

   private int compute6(GenericContainerScreen genericContainerScreen) {
      int intValue27 = ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).getRows();
      int intValue28 = ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).slots.size();
      return Math.max(0, Math.min(intValue27 * 9, intValue28));
   }

   private boolean check2(BlockPos blockPos) {
      return CLIENT.world != null && blockPos != null
         ? CLIENT.world.getBlockEntity(blockPos) instanceof ChestBlockEntity || CLIENT.world.getBlockEntity(blockPos) instanceof BarrelBlockEntity
         : false;
   }

   private void invoke34(BlockPos blockPos) {
      if (CLIENT.player != null && CLIENT.interactionManager != null && blockPos != null) {
         Direction direction = this.resolve8(blockPos);
         Vec3d vec3d2 = new Vec3d(
            blockPos.getX() + 0.5 + direction.getOffsetX() * 0.5, blockPos.getY() + 0.5 + direction.getOffsetY() * 0.5, blockPos.getZ() + 0.5 + direction.getOffsetZ() * 0.5
         );
         BlockHitResult blockHitResult2 = new BlockHitResult(vec3d2, direction, blockPos, false);
         CLIENT.player.swingHand(Hand.MAIN_HAND);
         CLIENT.interactionManager.interactBlock(CLIENT.player, Hand.MAIN_HAND, blockHitResult2);
      }
   }

   private Direction resolve8(BlockPos blockPos) {
      Vec3d vec3d3 = Vec3d.ofCenter(blockPos);
      Vec3d vec3d4 = CLIENT.player.getEyePos().subtract(vec3d3);
      return Direction.getFacing(vec3d4.x, vec3d4.y, vec3d4.z);
   }

   private boolean check3() {
      int intValue29 = CLIENT.player.getInventory().getSelectedSlot();
      ItemStack itemStack8 = (ItemStack)CLIENT.player.getInventory().getMainStacks().get(intValue29);
      if (!this.check4(itemStack8)) {
         return false;
      } else {
         for (int intValue30 = 0; intValue30 < 9; intValue30++) {
            ItemStack itemStack9 = (ItemStack)CLIENT.player.getInventory().getMainStacks().get(intValue30);
            if (itemStack9.isEmpty() || !this.check4(itemStack9)) {
               CLIENT.player.getInventory().setSelectedSlot(intValue30);
               return true;
            }
         }

         return false;
      }
   }

   private boolean check4(ItemStack itemStack) {
      if (itemStack != null && !itemStack.isEmpty()) {
         String text3 = itemStack.getName().getString();
         return itemStack.getItem() == Items.TRIPWIRE_HOOK || text3.contains("[★]");
      } else {
         return false;
      }
   }

   private void invoke35() {
      if (CLIENT.player != null && (CLIENT.currentScreen != null || ScreenUtil.check2(CLIENT))) {
         CLIENT.player.closeHandledScreen();
      }

      this.screen = null;
   }

   private GenericContainerScreen resolve9() {
      GenericContainerScreen genericContainerScreen6 = ScreenUtil.resolve(CLIENT, this.screen, GenericContainerScreen.class);
      if (genericContainerScreen6 == null && this.screen instanceof GenericContainerScreen) {
         this.screen = null;
      }

      return genericContainerScreen6;
   }

   private boolean check5() {
      return ScreenUtil.check3(CLIENT, this.screen) || ScreenUtil.check2(CLIENT);
   }

   private boolean check6(Screen screen) {
      return !(screen instanceof GenericContainerScreen) && !(screen instanceof MerchantScreen)
         ? false
         : this.autoVillageTradeState != AutoVillageTrade.AutoVillageTradeState.IDLE
            && this.autoVillageTradeState != AutoVillageTrade.AutoVillageTradeState.WAIT_RESTOCK
            && this.autoVillageTradeState != AutoVillageTrade.AutoVillageTradeState.SCAN_ROUTE
            && this.autoVillageTradeState != AutoVillageTrade.AutoVillageTradeState.MOVE_TO_TRADE
            && this.autoVillageTradeState != AutoVillageTrade.AutoVillageTradeState.MOVE_TO_STORAGE;
   }

   private boolean check7() {
      return blockPos3 != null && this.compute8(this.resolve10()) > 0 && !this.check13(this.resolve10());
   }

   private boolean check8() {
      if (this.check9()) {
         return false;
      } else if (blockPos3 != null && this.compute8(this.resolve10()) > 0) {
         this.invoke35();
         this.invoke11();
         return true;
      } else {
         this.invoke38("Недостаточно места для покупки изумрудов.");
         this.timestamp3 = System.currentTimeMillis() + 10000L;
         this.invoke35();
         this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.IDLE;
         this.dualTimer.invoke();
         return true;
      }
   }

   private boolean check9() {
      return this.compute7(Items.EMERALD) >= 64;
   }

   private boolean check10() {
      return this.check12() ? false : this.compute8(Items.EMERALD) < this.compute11();
   }

   private boolean check11() {
      return this.avtoIzumrudy.isEnabled() && !this.check12();
   }

   private boolean check12() {
      return System.currentTimeMillis() < this.timestamp3;
   }

   private int compute7(Item item) {
      if (CLIENT.player == null) {
         return 0;
      } else {
         int intValue31 = 0;
         int intValue32 = new ItemStack(item).getMaxCount();

         for (int intValue33 = 0; intValue33 < 36; intValue33++) {
            ItemStack itemStack10 = CLIENT.player.getInventory().getStack(intValue33);
            if (itemStack10.isEmpty()) {
               intValue31 += intValue32;
            } else if (itemStack10.isOf(item)) {
               intValue31 += Math.max(0, itemStack10.getMaxCount() - itemStack10.getCount());
            }
         }

         return intValue31;
      }
   }

   private int compute8(Item item) {
      if (CLIENT.player == null) {
         return 0;
      } else {
         int intValue34 = 0;

         for (int intValue35 = 0; intValue35 < 36; intValue35++) {
            ItemStack itemStack11 = CLIENT.player.getInventory().getStack(intValue35);
            if (!itemStack11.isEmpty() && itemStack11.isOf(item)) {
               intValue34 += itemStack11.getCount();
            }
         }

         return intValue34;
      }
   }

   private boolean check13(Item item) {
      for (int intValue36 = 0; intValue36 < 36; intValue36++) {
         ItemStack itemStack12 = CLIENT.player.getInventory().getStack(intValue36);
         if (itemStack12.isEmpty()) {
            return true;
         }

         if (itemStack12.isOf(item) && itemStack12.getCount() < itemStack12.getMaxCount()) {
            return true;
         }
      }

      return false;
   }

   private Item resolve10() {
      String text4 = this.chtoPokupat.getValue();

      return switch (text4) {
         case "Редстоун" -> Items.REDSTONE;
         case "Лазурит" -> Items.LAPIS_LAZULI;
         case "Жемчуг Эндера" -> Items.ENDER_PEARL;
         case "Бутылочка опыта" -> Items.EXPERIENCE_BOTTLE;
         case "Стекло" -> Items.GLASS;
         case "Бирка" -> Items.NAME_TAG;
         case "Стрелы" -> Items.ARROW;
         case "Хлеб" -> Items.BREAD;
         case "Золотая морковь" -> Items.GOLDEN_CARROT;
         case "Кварцевый блок" -> Items.QUARTZ_BLOCK;
         case "Седло" -> Items.SADDLE;
         default -> Items.GOLD_INGOT;
      };
   }

   private void invoke36(boolean bl) {
      long longValue2 = System.currentTimeMillis();
      if (bl || longValue2 - this.timestamp2 >= 1000L) {
         this.timestamp2 = longValue2;
         List items = this.valuesByKey
            .values()
            .stream()
            .filter(autoVillageTradeState213 -> autoVillageTradeState213.flag)
            .sorted(
               Comparator.<AutoVillageTrade.AutoVillageTradeState2>comparingDouble(autoVillageTradeState214 -> this.measure2(autoVillageTradeState214.intValue4, autoVillageTradeState214.intValue5))
                  .thenComparingInt(autoVillageTradeState215 -> autoVillageTradeState215.intValue)
            )
            .toList();
         if (items.isEmpty()) {
            this.invoke38("Скан завершен: подходящих сделок нет.");
         } else {
            AutoVillageTrade.AutoVillageTradeState2 autoVillageTradeState216 = (AutoVillageTrade.AutoVillageTradeState2)items.get(0);
            long longValue3 = items.stream().filter(object -> !((AutoVillageTrade.AutoVillageTradeState2)object).flag2 && ((AutoVillageTrade.AutoVillageTradeState2)object).intValue4 <= this.compute10()).count();
            this.invoke38(
               "Скан завершен: найдено "
                  + items.size()
                  + ", доступно сейчас "
                  + longValue3
                  + ", лучший курс "
                  + autoVillageTradeState216.intValue4
                  + " изумр. за "
                  + autoVillageTradeState216.intValue5
                  + " шт."
            );
         }
      }
   }

   private Rotation resolve11(Vec3d vec3d) {
      Vec3d vec3d5 = CLIENT.player.getEyePos();
      double doubleValue4 = vec3d.x - vec3d5.x;
      double doubleValue5 = vec3d.y - vec3d5.y;
      double doubleValue6 = vec3d.z - vec3d5.z;
      double doubleValue7 = Math.sqrt(doubleValue4 * doubleValue4 + doubleValue6 * doubleValue6);
      float floatValue = (float)Math.toDegrees(Math.atan2(-doubleValue4, doubleValue6));
      float floatValue2 = (float)(-Math.toDegrees(Math.atan2(doubleValue5, doubleValue7)));
      return new Rotation(floatValue, MathHelper.clamp(floatValue2, -90.0F, 90.0F));
   }

   private boolean check14(BlockPos blockPos, double d) {
      return this.measure(blockPos) <= d * d;
   }

   private double measure(BlockPos blockPos) {
      return CLIENT.player != null && blockPos != null
         ? CLIENT.player.getPos().squaredDistanceTo(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5)
         : Double.MAX_VALUE;
   }

   private BlockPos resolve12() {
      return blockPos != null && blockPos2 != null
         ? new BlockPos((blockPos.getX() + blockPos2.getX()) / 2, blockPos.getY(), (blockPos.getZ() + blockPos2.getZ()) / 2)
         : blockPos;
   }

   private double measure2(int i, int j) {
      return (double)i / Math.max(1, j);
   }

   private double measure3(double d) {
      return d * d;
   }

   private long compute9() {
      return Math.max(50L, (long)Math.round(this.zaderzhkaMs.getValue()));
   }

   private int compute10() {
      return Math.max(1, Math.round(this.maksTsena.getValue()));
   }

   private int compute11() {
      return Math.max(0, Math.round(this.zapasIzumrudov.getValue()));
   }

   private long compute12() {
      return Math.max(1000L, (long)Math.round(this.kdReskanaSek.getValue() * 1000.0F));
   }

   private String resolve13(String string) {
      return string == null ? "" : string.toLowerCase(Locale.ROOT).replace("§", "");
   }

   private void invoke37(boolean bl) {
      this.autoVillageTradeState = AutoVillageTrade.AutoVillageTradeState.IDLE;
      this.uUID = null;
      this.intValue = -1;
      this.intValue2 = 0;
      this.blockPos4 = null;
      this.intValue5 = -1;
      this.intValue6 = -1;
      this.intValue7 = 0;
      this.intValue8 = -1;
      this.timestamp3 = 0L;
      this.screen = null;
      if (bl) {
         this.items.clear();
      }

      this.dualTimer.invoke();
   }

   private void invoke38(String string) {
      ChatUtil.sendClientMessage("§8[§aAutoVillageTrade§8] §f" + string);
   }

   @Generated
   public static BlockPos getBlockPos() {
      return blockPos;
   }

   @Generated
   public static void setBlockPos(BlockPos blockPos) {
      AutoVillageTrade.blockPos = blockPos;
   }

   @Generated
   public static BlockPos getBlockPos2() {
      return blockPos2;
   }

   @Generated
   public static void setBlockPos2(BlockPos blockPos) {
      blockPos2 = blockPos;
   }

   @Generated
   public static BlockPos getBlockPos3() {
      return blockPos3;
   }

   @Generated
   public static void setBlockPos3(BlockPos blockPos) {
      blockPos3 = blockPos;
   }

   static enum AutoVillageTradeState {
      IDLE,
      SCAN_ROUTE,
      OPEN_SCAN,
      WAIT_SCAN_SCREEN,
      READ_SCAN_SCREEN,
      CLOSE_SCAN_SCREEN,
      MOVE_TO_TRADE,
      OPEN_TRADE,
      WAIT_TRADE_SCREEN,
      BUY_TRADE,
      CLOSE_TRADE_SCREEN,
      MOVE_TO_STORAGE,
      OPEN_STORAGE,
      WAIT_STORAGE_SCREEN,
      PUT_STORAGE,
      BUY_EMERALDS_OPEN_SHOP,
      BUY_EMERALDS_WAIT_SHOP,
      BUY_EMERALDS_FIND_GOLD,
      BUY_EMERALDS_WAIT_MENU,
      BUY_EMERALDS_FIND_EMERALD,
      BUY_EMERALDS_WAIT_CONFIRM,
      BUY_EMERALDS_CONFIRM,
      BUY_EMERALDS_CLOSE,
      WAIT_RESTOCK;
   }

   public record AutoVillageTradePriceData(ItemStack itemStack, int price, int itemCount, int availableAmount, boolean ready) {
   }

   static final class AutoVillageTradeState2 {
      final UUID uUID;
      BlockPos blockPos;
      int intValue;
      int intValue2 = -1;
      int intValue3 = -1;
      int intValue4 = Integer.MAX_VALUE;
      int intValue5 = 1;
      int intValue6;
      int intValue7;
      boolean flag;
      boolean flag2 = true;
      long timestamp;
      long timestamp2;
      int intValue8;
      int intValue9;

      private AutoVillageTradeState2(UUID uUID) {
         this.uUID = uUID;
      }
   }
}
