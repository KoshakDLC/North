package ru.metaculture.protection;

import java.util.Locale;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "AutoSell",
   category = Category.Misc,
   description = "Автоматически выставляет предметы на продажу"
)
public class AutoSell extends Module {
   public static AutoSell instance;
   private static final String PO_ODNOY_SHTUKE = "По одной штуке";
   private static final String VSE_SRAZU = "Все сразу";
   private static final String ODNA_TSENA = "Одна цена";
   private static final int INT_VALUE = 9;
   private static final long TIMESTAMP = 5000L;
   private static final long TIMESTAMP_2 = 12000L;
   private static final long TIMESTAMP_3 = 5000L;
   private ModeSetting server = new ModeSetting("Сервер", "FunTime", "HolyWorld", "FunTime");
   public final ModeSetting rezhim = new ModeSetting("Режим", "По бинду", "По бинду", "Авто");
   public final ModeSetting rezhimProdazhi = new ModeSetting("Режим продажи", "По одной штуке", "По одной штуке", "Все сразу", "Одна цена");
   public final NumberSetting natsenka = new NumberSetting("Наценка %", 10.0F, 0.0F, 100.0F, 10.0F, false)
      .setVisibilityCondition(() -> !this.rezhim.is("По бинду") || this.rezhimProdazhi.is("Одна цена"));
   public final KeybindSetting bindProdazhi = new KeybindSetting("Бинд продажи", -1).visibleWhen(() -> !this.rezhim.is("По бинду"));
   public final TextSetting tsenaOdnoyProdazhi = new TextSetting("Цена одной продажи", "1000")
      .resolve(32)
      .setVisibilityCondition(() -> !this.rezhimProdazhi.is("Одна цена"));
   public final ModeSetting vykladkaOdnoyTseny = new ModeSetting("Выкладка одной цены", "По одной штуке", "По одной штуке", "Все сразу")
      .setVisibilityCondition(() -> !this.rezhimProdazhi.is("Одна цена"));
   private final DualTimer dualTimer = new DualTimer();
   private final DualTimer dualTimer2 = new DualTimer();
   private final DualTimer dualTimer3 = new DualTimer();
   private static final long TIMESTAMP_4 = 1000L;
   private static final long TIMESTAMP_5 = 4000L;
   private static final long TIMESTAMP_6 = 1000L;
   private AutoSell.AutoSellState autoSellState = AutoSell.AutoSellState.IDLE;
   private boolean flag = false;
   private long timestamp = 0L;
   private String text = "";
   private boolean flag2 = false;
   private String text2 = "";
   private String text3 = "";
   private Item item = Items.AIR;
   private int intValue = 0;
   private boolean flag3 = false;
   private String text4 = "";
   private String text5 = "";
   private Item item2 = Items.AIR;
   private boolean flag4 = false;
   private int intValue2 = 0;
   private boolean flag5 = false;
   private boolean flag6 = false;
   private boolean flag7 = false;
   private int intValue3 = 0;
   private int intValue4 = 0;
   private int intValue5 = 0;
   private int intValue6 = 0;
   private int intValue7 = 9;
   private boolean flag8 = false;
   private boolean flag9 = false;
   private boolean flag10 = false;
   private int intValue8 = 0;
   private long timestamp2 = 0L;
   private long timestamp3 = 0L;
   private long timestamp4 = 0L;
   private long timestamp5 = 0L;
   private int intValue9 = -1;

   public AutoSell() {
      instance = this;
      this.addSettings(
         new Setting[]{
            this.server, this.rezhim, this.natsenka, this.bindProdazhi, this.rezhimProdazhi, this.tsenaOdnoyProdazhi, this.vykladkaOdnoyTseny
         }
      );
   }

   public boolean check() {
      return this.rezhim.is("Авто");
   }

   public boolean check2() {
      return this.check5();
   }

   @Override
   public void onEnable() {
      super.onEnable();
      FunTimeAuctionHelper.invoke3(false);
      FunTimeAuctionHelper.invoke4(false);
      FunTimeAuctionHelper.invoke();
      this.invoke();
      this.dualTimer.invoke();
   }

   @Override
   public void onDisable() {
      super.onDisable();
      if (this.flag) {
         FunTimeAuctionHelper.invoke3(false);
      }

      if (FunTimeAuctionHelper.check2()) {
         FunTimeAuctionHelper.invoke4(false);
      }

      FunTimeAuctionHelper.invoke();
      this.invoke();
   }

   public void invoke() {
      this.autoSellState = AutoSell.AutoSellState.IDLE;
      this.flag = false;
      this.timestamp = 0L;
      this.text = "";
      this.flag2 = false;
      this.text2 = "";
      this.text3 = "";
      this.item = Items.AIR;
      this.intValue = 0;
      this.flag3 = false;
      this.text4 = "";
      this.text5 = "";
      this.item2 = Items.AIR;
      this.flag4 = false;
      this.intValue2 = 0;
      this.flag5 = false;
      this.flag6 = false;
      this.flag7 = false;
      this.intValue3 = 0;
      this.intValue4 = 0;
      this.intValue5 = 0;
      this.intValue6 = 0;
      this.intValue7 = 9;
      this.flag8 = false;
      this.flag9 = false;
      this.flag10 = false;
      this.intValue8 = 0;
      this.timestamp3 = 0L;
      this.timestamp4 = 0L;
      this.timestamp5 = 0L;
      this.intValue9 = -1;
   }

   public static void invoke2() {
      if (instance != null) {
         instance.invoke();
      }
   }

   private void invoke3() {
      this.invoke4(true);
   }

   private void invoke4(boolean bl) {
      this.invoke();
      this.dualTimer.invoke();
      if (this.check5()
         || this.check15()
         || !bl
         || !this.check()
         || !FunTimeAuctionHelper.check3()
         || !this.check18()
         || !this.check19()
         || !this.check3()) {
         FunTimeAuctionHelper.invoke3(true);
      }
   }

   @EventHandler
   public void onRawInput(RawInputEvent rawInputEvent) {
      if (this.rezhim.is("По бинду")
         && rawInputEvent.getKeyCode() == this.bindProdazhi.getKeyCode()
         && !this.flag
         && CLIENT.player != null) {
         if (!this.check21(CLIENT.player.getMainHandStack())) {
            ChatUtil.sendClientMessage("§3[AutoSell] §fВозьмите предмет в руку");
            return;
         }

         this.check3();
      }
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player != null && CLIENT.world != null && CLIENT.interactionManager != null) {
         boolean flag = FunTimeAuctionHelper.check();
         boolean flag2 = FunTimeAuctionHelper.check3();
         if (this.check() && this.check5() && !this.flag) {
            if (this.dualTimer.check5(500L)) {
               if (FunTimeAuctionHelper.isFlag()) {
                  this.flag = true;
                  this.invoke18();
                  return;
               }

               if (flag2) {
                  if (!this.flag3 && !this.check21(CLIENT.player.getMainHandStack())) {
                     this.check19();
                  }

                  if (!this.check3()) {
                     this.invoke20("§c[AutoSell] §fДля режима одной цены возьмите нужный предмет в руку или дождитесь покупки AutoBuy.");
                     this.dualTimer.invoke();
                  }
               }
            }
         } else if (this.check() && !this.flag && (!this.check15() || flag) && this.dualTimer.check5(500L) && flag2) {
            if (this.check15()) {
               if (!this.check3() && flag) {
                  FunTimeAuctionHelper.invoke3(true);
               }
            } else if (this.check19()) {
               this.check3();
            } else if (flag) {
               FunTimeAuctionHelper.invoke3(true);
            }
         }

         if (this.flag) {
            if (this.check5() && this.autoSellState == AutoSell.AutoSellState.IDLE) {
               this.invoke11();
               if (!this.flag || this.autoSellState == AutoSell.AutoSellState.IDLE) {
                  return;
               }
            }

            switch (this.autoSellState) {
               case PREPARING:
                  if (this.check5()) {
                     if (this.check15()) {
                        ChatUtil.sendClientMessage("§c[AutoSell] §fРежим одной цены через sellgui доступен только для FunTime.");
                        this.invoke4(false);
                        return;
                     }

                     if (CLIENT.currentScreen != null) {
                        this.invoke26();
                        this.dualTimer2.invoke();
                        return;
                     }

                     if (!this.check6()) {
                        this.invoke3();
                        return;
                     }

                     if (!this.check7()) {
                        this.invoke3();
                        return;
                     }

                     this.autoSellState = AutoSell.AutoSellState.SELLGUI_SELLING;
                     this.dualTimer2.invoke();
                  } else if (this.check15()) {
                     if (CLIENT.currentScreen != null) {
                        this.invoke26();
                     }

                     this.check16();
                     if ((!this.check21(CLIENT.player.getMainHandStack()) || !this.check22(CLIENT.player.getMainHandStack()))
                        && !this.check19()) {
                        if (this.dualTimer3.check5(4000L)) {
                           this.invoke7(true);
                        }

                        return;
                     }

                     this.intValue = 0;
                     this.autoSellState = AutoSell.AutoSellState.HOLY_SELLING;
                     this.dualTimer2.invoke();
                     this.dualTimer3.invoke();
                  } else {
                     if (this.check()) {
                        if (!this.check21(CLIENT.player.getMainHandStack()) && !this.check19()) {
                           this.invoke3();
                           return;
                        }
                     } else if (!this.check21(CLIENT.player.getMainHandStack())) {
                        this.invoke3();
                        return;
                     }

                     if (this.rezhimProdazhi.is("По одной штуке")) {
                        this.autoSellState = AutoSell.AutoSellState.SPLITTING;
                     } else {
                        this.autoSellState = AutoSell.AutoSellState.SEARCHING;
                     }

                     this.dualTimer2.invoke();
                  }
                  break;
               case SPLITTING:
                  if (this.dualTimer2.check5(150L)) {
                     if (this.check17()) {
                        this.autoSellState = AutoSell.AutoSellState.SEARCHING;
                     } else {
                        this.invoke3();
                     }

                     this.dualTimer2.invoke();
                  }
                  break;
               case SEARCHING:
                  if (this.dualTimer2.check5(50L)) {
                     ItemStack itemStack2 = CLIENT.player.getMainHandStack();
                     if (itemStack2.isEmpty()) {
                        this.autoSellState = AutoSell.AutoSellState.PREPARING;
                        return;
                     }

                     if (this.rezhimProdazhi.is("По одной штуке") && itemStack2.getCount() > 1) {
                        this.autoSellState = AutoSell.AutoSellState.SPLITTING;
                        return;
                     }

                     String text = this.resolve(itemStack2);
                     if (text.isEmpty()) {
                        ChatUtil.sendClientMessage("§3[AutoSell] Не удалось определить имя предмета");
                        this.invoke3();
                        return;
                     }

                     this.text = text;
                     if (CLIENT.currentScreen != null) {
                        if (CLIENT.currentScreen instanceof GenericContainerScreen genericContainerScreen2) {
                           this.intValue9 = ((GenericContainerScreenHandler)genericContainerScreen2.getScreenHandler()).syncId;
                        }

                        this.invoke26();
                        this.dualTimer2.invoke();
                        return;
                     }

                     this.invoke5();
                     this.autoSellState = AutoSell.AutoSellState.SCANNING;
                     this.dualTimer2.invoke();
                     this.dualTimer3.invoke();
                  }
                  break;
               case SCANNING:
                  if (CLIENT.currentScreen instanceof GenericContainerScreen genericContainerScreen3 && this.check10(genericContainerScreen3)) {
                     if (this.dualTimer2.check5(350L)) {
                        try {
                           this.invoke28(genericContainerScreen3);
                        } catch (Exception exception) {
                           this.invoke3();
                        }
                     }
                  } else {
                     if (this.check14() && this.dualTimer3.check5(900L)) {
                        if (CLIENT.currentScreen != null) {
                           this.invoke26();
                        } else {
                           this.invoke5();
                        }

                        this.dualTimer3.invoke();
                     }

                     if (this.dualTimer2.check5(this.check14() ? 6500L : 2000L)) {
                        this.invoke3();
                     }
                  }
                  break;
               case SELLING:
                  if (this.dualTimer2.check5(50L)) {
                     if (this.timestamp > 0L) {
                        this.invoke6();
                     }

                     this.autoSellState = AutoSell.AutoSellState.FINISHING;
                     this.dualTimer2.invoke();
                     this.dualTimer3.invoke();
                  }
                  break;
               case FINISHING:
                  if (this.check14() && this.timestamp > 0L && this.dualTimer3.check5(900L)) {
                     this.invoke6();
                     this.dualTimer3.invoke();
                  }

                  if (this.dualTimer2.check5(this.check14() ? 10000L : 8000L)) {
                     ChatUtil.sendClientMessage("§e[AutoSell] §fНет ответа от аукциона, возвращаю AutoBuy.");
                     this.invoke4(false);
                  }
                  break;
               case SELLGUI_SELLING:
                  this.invoke12(false);
                  break;
               case SELLGUI_WAITING_RESULT:
                  this.invoke13(false);
                  break;
               case RESALE_SEARCH_OWN_AH:
                  this.invoke14();
                  break;
               case RESALE_WAITING_OWN_AH:
                  this.invoke15();
                  break;
               case RESALE_TAKE_ITEM:
                  this.invoke16();
                  break;
               case RESALE_SELLING:
                  this.invoke12(true);
                  break;
               case RESALE_WAIT_SELL_RESULT:
                  this.invoke13(true);
                  break;
               case HOLY_SELLING:
                  if (this.dualTimer3.check5(4000L)) {
                     this.invoke7(true);
                  } else if (!this.check21(CLIENT.player.getMainHandStack()) || !this.check22(CLIENT.player.getMainHandStack())) {
                     this.intValue = 0;
                     this.autoSellState = AutoSell.AutoSellState.PREPARING;
                     this.dualTimer2.invoke();
                  } else if (this.intValue == 0) {
                     this.invoke8();
                     this.intValue = 1;
                     this.dualTimer2.invoke();
                  } else if (this.intValue == 1 && this.dualTimer2.check5(1000L)) {
                     this.invoke9();
                     this.intValue = 2;
                     this.dualTimer2.invoke();
                  }
                  break;
               case HOLY_OPENING_AUCTION:
                  if (CLIENT.currentScreen instanceof GenericContainerScreen genericContainerScreen4 && this.check4(genericContainerScreen4)) {
                     this.invoke7(false);
                  } else if (this.dualTimer2.check5(1000L)) {
                     this.invoke10();
                     this.dualTimer2.invoke();
                  }
            }
         }
      }
   }

   @EventHandler
   public void onPacket(PacketEvent packetEvent) {
      if (packetEvent.getPacket() instanceof GameMessageS2CPacket gameMessageS2CPacket) {
         String text2 = gameMessageS2CPacket.content().getString();
         if (!this.check24(text2)) {
            if (this.check23(text2)) {
               FunTimeAuctionHelper.invoke6();
               if (this.flag) {
                  if (this.check5()) {
                     int intValue = Math.max(1, this.intValue4);
                     this.intValue5 += intValue;
                     if (this.flag9) {
                        this.intValue7 = Math.max(1, Math.min(this.intValue7, this.intValue5));
                     }

                     this.intValue6 = Math.max(0, this.intValue6 - intValue);
                     this.intValue4 = 0;
                     this.flag9 = false;
                     this.flag6 = false;
                     this.flag5 = true;
                     this.timestamp4 = System.currentTimeMillis();
                     return;
                  }

                  if (this.check15()) {
                     this.invoke10();
                     this.autoSellState = AutoSell.AutoSellState.HOLY_OPENING_AUCTION;
                     this.dualTimer2.invoke();
                     this.dualTimer3.invoke();
                  } else {
                     this.invoke4(true);
                  }
               }
            } else if (text2.contains("Не удалось выставить") && text2.contains("освободите хранилище")) {
               FunTimeAuctionHelper.invoke7();
               if (this.flag) {
                  if (this.check5()) {
                     this.flag6 = true;
                     this.intValue4 = 0;
                     this.flag9 = false;
                     return;
                  }

                  ChatUtil.sendClientMessage("§c[AutoSell] Хранилище заполнено. Продажа приостановлена.");
                  this.invoke4(false);
               }
            }
         } else {
            FunTimeAuctionHelper.invoke8();
            if (this.check5() && (this.flag || this.intValue5 > 0)) {
               int intValue2 = this.compute8(text2);
               this.intValue5 = Math.max(0, this.intValue5 - intValue2);
               this.intValue6 += intValue2;
               this.timestamp4 = System.currentTimeMillis();
               if (this.flag) {
                  this.autoSellState = AutoSell.AutoSellState.IDLE;
                  this.dualTimer2.invoke();
               }

               return;
            }

            if (this.flag) {
               if (this.check15()) {
                  this.invoke7(true);
               } else {
                  this.invoke4(false);
               }
            }
         }
      }
   }

   private boolean check3() {
      if (this.check5()) {
         if (this.check15()) {
            ChatUtil.sendClientMessage("§c[AutoSell] §fРежим одной цены через sellgui доступен только для FunTime.");
            return false;
         }

         if (this.compute6() <= 0L) {
            ChatUtil.sendClientMessage("§c[AutoSell] §fЦена одной продажи не задана.");
            return false;
         }

         if (!this.check6()) {
            return false;
         }
      } else if (this.check15() && !this.check16()) {
         return false;
      }

      if (!FunTimeAuctionHelper.check4()) {
         return false;
      } else {
         if (this.check5()) {
            this.intValue5 = 0;
            this.intValue6 = 0;
            this.intValue7 = 9;
            this.timestamp4 = 0L;
            this.intValue4 = 0;
            this.flag9 = false;
         }

         this.flag = true;
         this.autoSellState = AutoSell.AutoSellState.PREPARING;
         this.dualTimer2.invoke();
         this.dualTimer3.invoke();
         return true;
      }
   }

   private void invoke5() {
      if (CLIENT.player != null && !this.text.isEmpty()) {
         CLIENT.player.networkHandler.sendChatCommand("ah search " + this.text);
      }
   }

   private void invoke6() {
      if (CLIENT.player != null && this.timestamp > 0L) {
         CLIENT.player.networkHandler.sendChatCommand("ah sell " + this.timestamp);
      }
   }

   private void invoke7(boolean bl) {
      this.invoke();
      this.dualTimer.invoke();
      FunTimeAuctionHelper.invoke3(bl);
   }

   private void invoke8() {
      if (CLIENT.player != null) {
         if (CLIENT.currentScreen != null) {
            CLIENT.player.closeScreen();
         }

         CLIENT.player.networkHandler.sendChatCommand("ah sell auto");
      }
   }

   private void invoke9() {
      if (CLIENT.player != null) {
         CLIENT.player.networkHandler.sendChatCommand("ah sell auto confirm");
      }
   }

   private void invoke10() {
      if (CLIENT.player != null) {
         CLIENT.player.networkHandler.sendChatCommand("ah");
      }
   }

   private boolean check4(GenericContainerScreen genericContainerScreen) {
      if (genericContainerScreen == null) {
         return false;
      } else if (AutoBuy.instance != null && AutoBuy.instance.check22(genericContainerScreen)) {
         return true;
      } else {
         String text3 = this.resolve3(genericContainerScreen.getTitle().getString()).toLowerCase(Locale.ROOT);
         return text3.contains("аукцион") || text3.contains("auction");
      }
   }

   private void invoke11() {
      if (this.flag6) {
         this.invoke18();
      } else if (this.intValue6 > 0 && this.check7()) {
         if (this.intValue5 < this.intValue7) {
            this.autoSellState = AutoSell.AutoSellState.SELLGUI_SELLING;
            this.dualTimer2.invoke();
         } else {
            if (System.currentTimeMillis() - this.timestamp4 >= 5000L) {
               this.invoke18();
            }
         }
      } else if (this.intValue5 > 0) {
         if (this.check7() && this.intValue5 < this.intValue7) {
            this.autoSellState = AutoSell.AutoSellState.SELLGUI_SELLING;
            this.dualTimer2.invoke();
         } else {
            if (System.currentTimeMillis() - this.timestamp4 >= 5000L) {
               this.invoke18();
            }
         }
      } else if (this.check7()) {
         this.autoSellState = AutoSell.AutoSellState.SELLGUI_SELLING;
         this.dualTimer2.invoke();
      } else {
         this.invoke19(true);
      }
   }

   private void invoke12(boolean bl) {
      if (this.dualTimer2.check5(50L)) {
         if (this.flag6) {
            this.invoke18();
         } else {
            long longValue = this.compute6();
            if (longValue <= 0L) {
               ChatUtil.sendClientMessage("§c[AutoSell] §fЦена одной продажи не задана.");
               this.invoke19(false);
            } else if (this.check6() && this.check7()) {
               if (CLIENT.currentScreen instanceof GenericContainerScreen genericContainerScreen5 && this.check9(genericContainerScreen5)) {
                  this.invoke17(genericContainerScreen5, bl);
               } else if (CLIENT.currentScreen != null) {
                  this.invoke26();
                  this.dualTimer2.invoke();
               } else if (this.check13()) {
                  this.invoke25();
                  this.flag5 = false;
                  this.flag6 = false;
                  this.invoke24(longValue);
                  this.autoSellState = bl ? AutoSell.AutoSellState.RESALE_WAIT_SELL_RESULT : AutoSell.AutoSellState.SELLGUI_WAITING_RESULT;
                  this.dualTimer2.invoke();
                  this.dualTimer3.invoke();
               }
            } else {
               this.autoSellState = AutoSell.AutoSellState.IDLE;
               this.dualTimer2.invoke();
            }
         }
      }
   }

   private void invoke13(boolean bl) {
      if (this.flag6) {
         this.invoke18();
      } else if (CLIENT.currentScreen instanceof GenericContainerScreen genericContainerScreen6 && this.check9(genericContainerScreen6)) {
         this.invoke17(genericContainerScreen6, bl);
      } else if (this.flag5) {
         this.flag5 = false;
         this.invoke25();
         this.autoSellState = this.intValue5 > 0
            ? AutoSell.AutoSellState.IDLE
            : (this.check7() ? (bl ? AutoSell.AutoSellState.RESALE_SELLING : AutoSell.AutoSellState.SELLGUI_SELLING) : AutoSell.AutoSellState.IDLE);
         this.dualTimer2.invoke();
      } else {
         if (this.dualTimer2.check5(12000L)) {
            this.invoke26();
            this.intValue4 = 0;
            this.autoSellState = this.intValue5 > 0
               ? AutoSell.AutoSellState.IDLE
               : (this.check7() ? (bl ? AutoSell.AutoSellState.RESALE_SELLING : AutoSell.AutoSellState.SELLGUI_SELLING) : AutoSell.AutoSellState.IDLE);
            if (this.autoSellState == AutoSell.AutoSellState.IDLE && this.intValue5 <= 0) {
               this.invoke19(false);
            }

            this.dualTimer2.invoke();
         }
      }
   }

   private void invoke14() {
      long longValue2 = System.currentTimeMillis();
      if (longValue2 >= this.timestamp5) {
         if (CLIENT.currentScreen != null) {
            this.invoke26();
            this.timestamp5 = longValue2 + 350L;
         } else if (!this.flag10 && !this.flag7 && this.check7()) {
            this.autoSellState = AutoSell.AutoSellState.RESALE_SELLING;
            this.dualTimer2.invoke();
         } else {
            this.flag7 = false;
            if (CLIENT.player == null) {
               this.invoke19(false);
            } else {
               this.intValue8++;
               this.invoke23(CLIENT.player.getName().getString(), this.intValue8);
               this.intValue3 = 0;
               this.autoSellState = AutoSell.AutoSellState.RESALE_WAITING_OWN_AH;
               this.dualTimer2.invoke();
               this.dualTimer3.invoke();
            }
         }
      }
   }

   private void invoke15() {
      if (CLIENT.currentScreen instanceof GenericContainerScreen genericContainerScreen7 && this.check11(genericContainerScreen7)) {
         this.autoSellState = AutoSell.AutoSellState.RESALE_TAKE_ITEM;
         this.dualTimer2.invoke();
      } else if (this.dualTimer3.check5(18000L)) {
         ChatUtil.sendClientMessage("§c[AutoSell] §fТаймаут поиска своих товаров.");
         this.invoke19(false);
      } else if (CLIENT.currentScreen != null && this.dualTimer2.check5(1200L)) {
         this.invoke26();
         this.autoSellState = AutoSell.AutoSellState.RESALE_SEARCH_OWN_AH;
         this.timestamp5 = System.currentTimeMillis() + 350L;
         this.dualTimer2.invoke();
      } else if (this.dualTimer2.check5(4000L)) {
         this.autoSellState = AutoSell.AutoSellState.RESALE_SEARCH_OWN_AH;
         this.timestamp5 = System.currentTimeMillis();
         this.dualTimer2.invoke();
      }
   }

   private void invoke16() {
      if (this.dualTimer2.check5(200L)) {
         if (CLIENT.currentScreen instanceof GenericContainerScreen genericContainerScreen8) {
            if (!this.check11(genericContainerScreen8)) {
               if (this.dualTimer2.check5(10000L)) {
                  this.invoke26();
                  this.autoSellState = AutoSell.AutoSellState.RESALE_SEARCH_OWN_AH;
                  this.dualTimer2.invoke();
               }
            } else {
               int intValue3 = this.compute2(genericContainerScreen8);
               if (intValue3 != -1) {
                  this.invoke22(genericContainerScreen8, intValue3, 0, SlotActionType.QUICK_MOVE);
                  this.intValue3 = 0;
                  this.dualTimer2.invoke();
               } else if (this.intValue3++ < 2) {
                  this.dualTimer2.invoke();
               } else {
                  this.invoke26();
                  this.flag6 = false;
                  if (!this.check7()) {
                     ChatUtil.sendClientMessage("§e[AutoSell] §fСвои лоты не найдены, предметов в инвентаре нет.");
                     this.invoke19(true);
                  } else {
                     this.flag10 = false;
                     this.autoSellState = AutoSell.AutoSellState.RESALE_SELLING;
                     this.dualTimer2.invoke();
                  }
               }
            }
         } else {
            this.autoSellState = AutoSell.AutoSellState.RESALE_SEARCH_OWN_AH;
            this.dualTimer2.invoke();
         }
      }
   }

   private void invoke17(GenericContainerScreen genericContainerScreen, boolean bl) {
      ScreenHandler screenHandler2 = genericContainerScreen.getScreenHandler();
      int intValue4 = this.compute5(genericContainerScreen);
      if (this.flag4) {
         if (this.dualTimer2.check5(1500L)) {
            this.invoke26();
         }
      } else {
         int intValue5 = 0;

         for (int intValue6 = this.compute7(bl); intValue5 < 4 && this.intValue2 < intValue6; intValue5++) {
            int intValue7 = this.compute3(genericContainerScreen);
            int intValue8 = this.compute(screenHandler2, intValue4);
            if (intValue7 == -1) {
               this.flag8 = true;
               break;
            }

            if (intValue8 == -1) {
               break;
            }

            if (this.vykladkaOdnoyTseny.is("По одной штуке")) {
               if (!this.check12(screenHandler2, intValue8, intValue7)) {
                  break;
               }
            } else {
               CLIENT.interactionManager.clickSlot(screenHandler2.syncId, intValue8, 0, SlotActionType.PICKUP, CLIENT.player);
               CLIENT.interactionManager.clickSlot(screenHandler2.syncId, intValue7, 0, SlotActionType.PICKUP, CLIENT.player);
            }

            this.intValue2++;
         }

         if (intValue5 > 0) {
            this.dualTimer2.invoke();
         } else if (this.intValue2 > 0) {
            int intValue9 = this.compute4(genericContainerScreen);
            if (intValue9 != -1) {
               this.invoke22(genericContainerScreen, intValue9, 0, SlotActionType.PICKUP);
               this.flag4 = true;
               this.intValue4 = this.intValue2;
               this.flag9 = this.flag8;
               if (this.flag8) {
                  this.intValue7 = Math.max(1, this.intValue2);
               }

               this.dualTimer2.invoke();
            } else {
               if (this.dualTimer2.check5(3000L)) {
                  this.invoke26();
                  this.autoSellState = bl ? AutoSell.AutoSellState.RESALE_SELLING : AutoSell.AutoSellState.SELLGUI_SELLING;
                  this.dualTimer2.invoke();
               }
            }
         } else {
            this.invoke26();
            if (this.flag8 && this.intValue5 > 0) {
               this.intValue7 = Math.max(1, Math.min(this.intValue7, this.intValue5));
               this.invoke20("§e[AutoSell] §fВ sellgui нет свободных слотов, жду перевыставление.");
            }

            this.autoSellState = bl ? AutoSell.AutoSellState.RESALE_SEARCH_OWN_AH : AutoSell.AutoSellState.IDLE;
            if (this.autoSellState == AutoSell.AutoSellState.IDLE && this.intValue5 <= 0) {
               this.invoke19(true);
            } else {
               this.dualTimer2.invoke();
            }
         }
      }
   }

   private void invoke18() {
      this.flag6 = false;
      this.flag5 = false;
      this.flag7 = true;
      this.flag10 = true;
      this.intValue3 = 0;
      this.intValue5 = 0;
      this.intValue6 = 0;
      this.flag8 = false;
      this.flag9 = false;
      this.intValue8 = 0;
      this.timestamp5 = System.currentTimeMillis() + 350L;
      this.invoke25();
      this.autoSellState = AutoSell.AutoSellState.RESALE_SEARCH_OWN_AH;
      this.dualTimer2.invoke();
      this.dualTimer3.invoke();
      this.invoke26();
      FunTimeAuctionHelper.invoke3(false);
      FunTimeAuctionHelper.check5();
   }

   private void invoke19(boolean bl) {
      if (FunTimeAuctionHelper.check2()) {
         FunTimeAuctionHelper.invoke4(bl);
      }

      this.invoke4(bl);
   }

   private boolean check5() {
      return this.rezhimProdazhi.is("Одна цена");
   }

   private void invoke20(String string) {
      long longValue3 = System.currentTimeMillis();
      if (longValue3 - this.timestamp3 >= 3000L) {
         this.timestamp3 = longValue3;
         ChatUtil.sendClientMessage(string);
      }
   }

   private boolean check6() {
      if (this.flag3) {
         return true;
      } else if (CLIENT.player != null && this.check21(CLIENT.player.getMainHandStack())) {
         this.invoke21(CLIENT.player.getMainHandStack());
         return true;
      } else {
         AutoBuy.AutoBuyState autoBuyState = AutoBuy.resolve4();
         if (autoBuyState != null) {
            this.flag3 = true;
            this.text4 = autoBuyState.text2 == null ? "" : autoBuyState.text2;
            this.text5 = autoBuyState.text == null ? "" : autoBuyState.text;
            this.item2 = Items.AIR;
            return true;
         } else {
            return false;
         }
      }
   }

   private void invoke21(ItemStack itemStack) {
      if (this.check21(itemStack)) {
         this.flag3 = true;
         this.text4 = this.resolve(itemStack);
         this.text5 = itemStack.getName().getString();
         this.item2 = itemStack.getItem();
      }
   }

   private boolean check7() {
      if (CLIENT.player == null) {
         return false;
      } else if (this.check8(CLIENT.player.getMainHandStack())) {
         return true;
      } else {
         for (int intValue10 = 0; intValue10 < 36; intValue10++) {
            if (this.check8(CLIENT.player.getInventory().getStack(intValue10))) {
               return true;
            }
         }

         return false;
      }
   }

   private boolean check8(ItemStack itemStack) {
      if (this.check21(itemStack) && this.flag3) {
         if (this.item2 != Items.AIR && itemStack.getItem() != this.item2) {
            return false;
         } else {
            String text4 = this.resolve2(this.resolve(itemStack));
            String text5 = this.resolve2(this.text4);
            String text6 = this.resolve2(this.text5);
            if (text5.isEmpty() && text6.isEmpty()) {
               return true;
            } else {
               if (!text4.isEmpty()) {
                  if (text4.equals(text5) || text4.equals(text6) || !text5.isEmpty() && (text4.contains(text5) || text5.contains(text4))) {
                     return true;
                  }

                  if (!text6.isEmpty()) {
                     if (text4.contains(text6)) {
                        return true;
                     }

                     if (text6.contains(text4)) {
                        return true;
                     }
                  }
               }

               return false;
            }
         }
      } else {
         return false;
      }
   }

   private int compute(ScreenHandler screenHandler, int i) {
      for (int intValue11 = i; intValue11 < screenHandler.slots.size(); intValue11++) {
         Slot slot2 = screenHandler.getSlot(intValue11);
         if (slot2.hasStack() && this.check8(slot2.getStack())) {
            return intValue11;
         }
      }

      return -1;
   }

   private int compute2(GenericContainerScreen genericContainerScreen) {
      int intValue12 = this.compute5(genericContainerScreen);

      for (int intValue13 = 0; intValue13 < intValue12; intValue13++) {
         Slot slot3 = ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).getSlot(intValue13);
         if (slot3.hasStack() && this.check8(slot3.getStack())) {
            return intValue13;
         }
      }

      return -1;
   }

   private int compute3(GenericContainerScreen genericContainerScreen) {
      int intValue14 = Math.min(9, this.compute5(genericContainerScreen));

      for (int intValue15 = 0; intValue15 < intValue14; intValue15++) {
         Slot slot4 = ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).getSlot(intValue15);
         if (!slot4.hasStack()) {
            return intValue15;
         }
      }

      return -1;
   }

   private int compute4(GenericContainerScreen genericContainerScreen) {
      int intValue16 = this.compute5(genericContainerScreen);
      int intValue17 = -1;

      for (int intValue18 = intValue16 - 1; intValue18 >= 0; intValue18--) {
         Slot slot5 = ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).getSlot(intValue18);
         if (slot5.hasStack()) {
            ItemStack itemStack3 = slot5.getStack();
            String text7 = this.resolve3(itemStack3.getName().getString()).toLowerCase(Locale.ROOT);
            if (itemStack3.isOf(Items.LIME_DYE)
               || itemStack3.isOf(Items.GREEN_DYE)
               || itemStack3.isOf(Items.LIME_STAINED_GLASS_PANE)
               || itemStack3.isOf(Items.GREEN_STAINED_GLASS_PANE)) {
               return intValue18;
            }

            if (text7.contains("выстав") || text7.contains("продать") || text7.contains("подтверд")) {
               intValue17 = intValue18;
            }
         }
      }

      return intValue17;
   }

   private boolean check9(GenericContainerScreen genericContainerScreen) {
      if (genericContainerScreen == null) {
         return false;
      } else {
         String text8 = this.resolve3(genericContainerScreen.getTitle().getString()).toLowerCase(Locale.ROOT);
         return text8.contains("продажа") || text8.contains("sellgui") || text8.contains("sell gui");
      }
   }

   private boolean check10(GenericContainerScreen genericContainerScreen) {
      if (genericContainerScreen != null && ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).syncId != this.intValue9) {
         if (AhHelper.check(genericContainerScreen)) {
            return true;
         } else {
            int intValue19 = Math.min(45, ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).slots.size());

            for (int intValue20 = 0; intValue20 < intValue19; intValue20++) {
               if (compute14(((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).getSlot(intValue20)) > 0L) {
                  return true;
               }
            }

            return false;
         }
      } else {
         return false;
      }
   }

   private boolean check11(GenericContainerScreen genericContainerScreen) {
      if (genericContainerScreen != null && CLIENT.player != null) {
         String text9 = this.resolve3(genericContainerScreen.getTitle().getString()).toLowerCase(Locale.ROOT);
         String text10 = this.resolve3(CLIENT.player.getName().getString()).toLowerCase(Locale.ROOT);
         return AhHelper.check(genericContainerScreen)
            || text9.contains(text10)
            || text9.contains("мои товары")
            || text9.contains("мои предметы")
            || text9.contains("поиск:");
      } else {
         return false;
      }
   }

   private int compute5(GenericContainerScreen genericContainerScreen) {
      int intValue21 = ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).getRows();
      int intValue22 = ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).slots.size();
      return Math.max(0, Math.min(intValue21 * 9, intValue22));
   }

   private void invoke22(GenericContainerScreen genericContainerScreen, int i, int j, SlotActionType slotActionType) {
      CLIENT.interactionManager
         .clickSlot(((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).syncId, i, j, slotActionType, CLIENT.player);
   }

   private boolean check12(ScreenHandler screenHandler, int i, int j) {
      if (CLIENT.interactionManager != null && CLIENT.player != null) {
         CLIENT.interactionManager.clickSlot(screenHandler.syncId, i, 0, SlotActionType.PICKUP, CLIENT.player);
         if (screenHandler.getCursorStack().isEmpty()) {
            return false;
         } else {
            CLIENT.interactionManager.clickSlot(screenHandler.syncId, j, 1, SlotActionType.PICKUP, CLIENT.player);
            if (!screenHandler.getCursorStack().isEmpty()) {
               CLIENT.interactionManager.clickSlot(screenHandler.syncId, i, 0, SlotActionType.PICKUP, CLIENT.player);
            }

            return true;
         }
      } else {
         return false;
      }
   }

   private void invoke23(String string, int i) {
      if (CLIENT.player != null && string != null && !string.isBlank()) {
         String text11 = "ah " + string.trim();
         CLIENT.player.networkHandler.sendChatCommand(text11);
      }
   }

   private boolean check13() {
      return System.currentTimeMillis() - this.timestamp2 >= 5000L;
   }

   private void invoke24(long l) {
      if (CLIENT.player != null) {
         CLIENT.player.networkHandler.sendChatCommand("ah sellgui " + l);
         this.timestamp2 = System.currentTimeMillis();
      }
   }

   private void invoke25() {
      this.flag4 = false;
      this.intValue2 = 0;
      this.intValue4 = 0;
      this.flag8 = false;
      this.flag9 = false;
   }

   private long compute6() {
      return this.compute12(this.tsenaOdnoyProdazhi.getValue());
   }

   private int compute7(boolean bl) {
      if (bl) {
         return Integer.MAX_VALUE;
      } else {
         int intValue23 = Math.max(1, this.intValue7 - this.intValue5);
         if (this.intValue6 > 0) {
            return Math.max(1, Math.min(this.intValue6, intValue23));
         } else {
            return this.intValue5 > 0 ? intValue23 : Integer.MAX_VALUE;
         }
      }
   }

   private int compute8(String string) {
      if (string == null) {
         return 1;
      } else {
         String text12 = this.resolve3(string).toLowerCase(Locale.ROOT);
         int intValue24 = text12.indexOf(" за ");
         if (intValue24 > 0) {
            text12 = text12.substring(0, intValue24);
         }

         int intValue25 = this.compute9(text12, "x");
         if (intValue25 > 0) {
            return intValue25;
         } else {
            intValue25 = this.compute9(text12, "х");
            if (intValue25 > 0) {
               return intValue25;
            } else {
               intValue25 = this.compute10(text12, "предмет");
               if (intValue25 > 0) {
                  return intValue25;
               } else {
                  intValue25 = this.compute10(text12, "шт");
                  return intValue25 > 0 ? intValue25 : 1;
               }
            }
         }
      }
   }

   private int compute9(String string, String string2) {
      int intValue26 = string.indexOf(string2);
      if (intValue26 < 0) {
         return 0;
      } else {
         String text13 = string.substring(intValue26 + string2.length()).replaceFirst("[^0-9]*", "").replaceFirst("[^0-9].*$", "");
         return this.compute11(text13);
      }
   }

   private int compute10(String string, String string2) {
      int intValue27 = string.indexOf(string2);
      if (intValue27 <= 0) {
         return 0;
      } else {
         String text14 = string.substring(0, intValue27).trim();
         String text15 = text14.replaceFirst("^.*?([0-9]+)\\s*$", "$1");
         return text15.equals(text14) && !text15.matches("[0-9]+") ? 0 : this.compute11(text15);
      }
   }

   private int compute11(String string) {
      if (string != null && !string.isBlank()) {
         try {
            return Math.max(0, Integer.parseInt(string));
         } catch (NumberFormatException numberFormatException) {
            return 0;
         }
      } else {
         return 0;
      }
   }

   private long compute12(String string) {
      if (string == null) {
         return 0L;
      } else {
         String text16 = string.toLowerCase(Locale.ROOT).replace(" ", "").replace("_", "").replace(",", "").replace(".", "");
         long longValue4 = 1L;
         if (text16.endsWith("тысяч")) {
            longValue4 = 1000L;
            text16 = text16.substring(0, text16.length() - 5);
         } else if (text16.endsWith("тысячи")) {
            longValue4 = 1000L;
            text16 = text16.substring(0, text16.length() - 6);
         } else if (text16.endsWith("тысяча")) {
            longValue4 = 1000L;
            text16 = text16.substring(0, text16.length() - 6);
         } else if (text16.endsWith("тыс")) {
            longValue4 = 1000L;
            text16 = text16.substring(0, text16.length() - 3);
         } else if (text16.endsWith("k") || text16.endsWith("к")) {
            longValue4 = 1000L;
            text16 = text16.substring(0, text16.length() - 1);
         } else if (text16.endsWith("m") || text16.endsWith("м")) {
            longValue4 = 1000000L;
            text16 = text16.substring(0, text16.length() - 1);
         }

         String text17 = text16.replaceAll("[^0-9]", "");
         if (text17.isEmpty()) {
            return 0L;
         } else {
            try {
               return Math.multiplyExact(Long.parseLong(text17), longValue4);
            } catch (NumberFormatException | ArithmeticException exception2) {
               return 0L;
            }
         }
      }
   }

   private void invoke26() {
      if (CLIENT.player != null) {
         CLIENT.player.closeHandledScreen();
      }
   }

   private boolean check14() {
      return AutoBuy.instance != null && AutoBuy.instance.rezhimServera.is("FunTime");
   }

   private boolean check15() {
      return this.check5()
         ? this.server.is("HolyWorld")
         : this.server.is("HolyWorld") || AutoBuy.instance != null && AutoBuy.instance.rezhimServera.is("HolyWorld");
   }

   private boolean check16() {
      if (!this.check15()) {
         return true;
      } else if (this.flag2) {
         return true;
      } else {
         AutoBuy.AutoBuyState autoBuyState2 = AutoBuy.resolve4();
         if (autoBuyState2 != null) {
            this.flag2 = true;
            this.text2 = autoBuyState2.text2 == null ? "" : autoBuyState2.text2;
            this.text3 = autoBuyState2.text == null ? "" : autoBuyState2.text;
            this.item = Items.AIR;
            return true;
         } else if (CLIENT.player != null && this.check21(CLIENT.player.getMainHandStack())) {
            this.invoke27(CLIENT.player.getMainHandStack());
            return true;
         } else {
            return false;
         }
      }
   }

   private void invoke27(ItemStack itemStack) {
      if (this.check21(itemStack)) {
         this.flag2 = true;
         this.text2 = this.resolve(itemStack);
         this.text3 = itemStack.getName().getString();
         this.item = itemStack.getItem();
      }
   }

   private boolean check17() {
      if (CLIENT.player != null && CLIENT.interactionManager != null && CLIENT.player.playerScreenHandler != null) {
         ItemStack itemStack4 = CLIENT.player.getMainHandStack();
         if (itemStack4.isEmpty()) {
            return false;
         } else if (itemStack4.getCount() == 1) {
            return true;
         } else {
            int intValue28 = this.compute13();
            if (intValue28 == -1) {
               ChatUtil.sendClientMessage("§3[AutoSell] Нет места для стака");
               return false;
            } else {
               int intValue29 = CLIENT.player.getInventory().getSelectedSlot() + 36;
               CLIENT.interactionManager.clickSlot(CLIENT.player.playerScreenHandler.syncId, intValue29, 0, SlotActionType.PICKUP, CLIENT.player);
               CLIENT.interactionManager.clickSlot(CLIENT.player.playerScreenHandler.syncId, intValue29, 1, SlotActionType.PICKUP, CLIENT.player);
               CLIENT.interactionManager.clickSlot(CLIENT.player.playerScreenHandler.syncId, intValue28, 0, SlotActionType.PICKUP, CLIENT.player);
               return true;
            }
         }
      } else {
         return false;
      }
   }

   private int compute13() {
      if (CLIENT.player == null) {
         return -1;
      } else {
         for (int intValue30 = 9; intValue30 < 36; intValue30++) {
            if (CLIENT.player.getInventory().getStack(intValue30).isEmpty()) {
               return intValue30;
            }
         }

         for (int intValue31 = 36; intValue31 < 45; intValue31++) {
            if (intValue31 != CLIENT.player.getInventory().getSelectedSlot() + 36 && CLIENT.player.getInventory().getStack(intValue31 - 36).isEmpty()) {
               return intValue31;
            }
         }

         return -1;
      }
   }

   private boolean check18() {
      if (CLIENT.player == null) {
         return false;
      } else if (this.check21(CLIENT.player.getMainHandStack())) {
         return true;
      } else {
         for (int intValue32 = 0; intValue32 < 36; intValue32++) {
            if (this.check21(CLIENT.player.getInventory().getStack(intValue32))) {
               return true;
            }
         }

         return false;
      }
   }

   private boolean check19() {
      if (CLIENT.player != null && CLIENT.interactionManager != null && CLIENT.player.playerScreenHandler != null) {
         if (this.check15()) {
            if (!this.check16()) {
               return false;
            }

            if (this.flag2) {
               return this.check20();
            }
         }

         if (this.check21(CLIENT.player.getMainHandStack())) {
            return true;
         } else {
            for (int intValue33 = 0; intValue33 < 9; intValue33++) {
               if (this.check21(CLIENT.player.getInventory().getStack(intValue33))) {
                  ItemStackUtils.invoke(intValue33);
                  this.dualTimer2.invoke();
                  return true;
               }
            }

            for (int intValue34 = 9; intValue34 < 36; intValue34++) {
               if (this.check21(CLIENT.player.getInventory().getStack(intValue34))) {
                  CLIENT.interactionManager
                     .clickSlot(
                        CLIENT.player.playerScreenHandler.syncId,
                        intValue34,
                        CLIENT.player.getInventory().getSelectedSlot(),
                        SlotActionType.SWAP,
                        CLIENT.player
                     );
                  this.dualTimer2.invoke();
                  return true;
               }
            }

            return false;
         }
      } else {
         return false;
      }
   }

   private boolean check20() {
      if (CLIENT.player != null && CLIENT.interactionManager != null && CLIENT.player.playerScreenHandler != null) {
         ItemStack itemStack5 = CLIENT.player.getMainHandStack();
         if (this.check22(itemStack5)) {
            this.invoke27(itemStack5);
            return true;
         } else {
            for (int intValue35 = 0; intValue35 < 9; intValue35++) {
               ItemStack itemStack6 = CLIENT.player.getInventory().getStack(intValue35);
               if (this.check22(itemStack6)) {
                  this.invoke27(itemStack6);
                  ItemStackUtils.invoke(intValue35);
                  this.dualTimer2.invoke();
                  return true;
               }
            }

            int intValue36 = CLIENT.player.getInventory().getSelectedSlot();

            for (int intValue37 = 9; intValue37 < 36; intValue37++) {
               ItemStack itemStack7 = CLIENT.player.getInventory().getStack(intValue37);
               if (this.check22(itemStack7)) {
                  this.invoke27(itemStack7);
                  CLIENT.interactionManager.clickSlot(CLIENT.player.playerScreenHandler.syncId, intValue37, intValue36, SlotActionType.SWAP, CLIENT.player);
                  this.dualTimer2.invoke();
                  return true;
               }
            }

            return false;
         }
      } else {
         return false;
      }
   }

   private boolean check21(ItemStack itemStack) {
      return itemStack != null && !itemStack.isEmpty() && itemStack.getItem() != Items.AIR;
   }

   private boolean check22(ItemStack itemStack) {
      if (this.check21(itemStack) && this.flag2) {
         if (this.item != Items.AIR && itemStack.getItem() != this.item) {
            return false;
         } else {
            HolyWorldItemParser.HolyWorldItemParserDisplayEntry holyWorldItemParserDisplayEntry = HolyWorldItemParser.resolve(this.text2);
            if (holyWorldItemParserDisplayEntry == null) {
               holyWorldItemParserDisplayEntry = HolyWorldItemParser.resolve(this.text3);
            }

            if (holyWorldItemParserDisplayEntry != null && HolyWorldItemParser.check9(holyWorldItemParserDisplayEntry, itemStack, HolyWorldItemParser.resolve5(itemStack), HolyWorldItemParser.resolve6(itemStack))) {
               return true;
            } else {
               String text18 = this.resolve2(this.resolve(itemStack));
               String text19 = this.resolve2(this.text2);
               String text20 = this.resolve2(this.text3);
               return text19.isEmpty() && text20.isEmpty()
                  ? true
                  : !text18.isEmpty()
                     && (
                        text18.equals(text19)
                           || text18.equals(text20)
                           || !text19.isEmpty() && (text18.contains(text19) || text19.contains(text18))
                           || !text20.isEmpty() && (text18.contains(text20) || text20.contains(text18))
                     );
            }
         }
      } else {
         return false;
      }
   }

   private void invoke28(GenericContainerScreen genericContainerScreen) {
      double doubleValue = Double.MAX_VALUE;
      boolean flag3 = false;
      int intValue38 = Math.min(45, ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).slots.size());

      for (int intValue39 = 0; intValue39 < intValue38; intValue39++) {
         Slot slot6 = ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).getSlot(intValue39);
         if (slot6.hasStack()) {
            long longValue5 = compute14(slot6);
            if (longValue5 > 0L) {
               int intValue40 = Math.max(1, slot6.getStack().getCount());
               double doubleValue2 = (double)longValue5 / intValue40;
               if (doubleValue2 < doubleValue) {
                  doubleValue = doubleValue2;
                  flag3 = true;
               }
            }
         }
      }

      String text21 = CLIENT.player != null ? this.resolve(CLIENT.player.getMainHandStack()) : "";
      int intValue41 = CLIENT.player != null ? Math.max(1, CLIENT.player.getMainHandStack().getCount()) : 1;
      int intValue42 = this.rezhimProdazhi.is("Все сразу") ? intValue41 : 1;
      long longValue6 = AutoBuy.compute3(text21);
      long longValue7 = longValue6 > 0L ? Math.max(1L, (long)Math.ceil(longValue6 * 1.02 * intValue42)) : 1L;
      if (CLIENT.player != null) {
         CLIENT.player.closeScreen();
      }

      if (flag3) {
         long longValue8 = this.rezhimProdazhi.is("Все сразу") ? (long)(doubleValue * intValue41) : (long)doubleValue;
         long longValue9;
         if (this.check()) {
            longValue9 = longValue8 - 1L;
            if (longValue6 > 0L && longValue9 < longValue7) {
               longValue9 = longValue7;
            }

            if (longValue9 < 1L) {
               longValue9 = 1L;
            }
         } else {
            double doubleValue3 = 1.0 + this.natsenka.getValue() / 100.0;
            longValue9 = (long)(longValue8 * doubleValue3);
         }

         this.timestamp = longValue9;
         this.autoSellState = AutoSell.AutoSellState.SELLING;
         this.dualTimer2.invoke();
      } else if (this.check() && longValue6 > 0L) {
         this.timestamp = longValue7;
         this.autoSellState = AutoSell.AutoSellState.SELLING;
         this.dualTimer2.invoke();
      } else {
         ChatUtil.sendClientMessage("§3[AutoSell] Конкурентов нет, ставьте вручную");
         this.invoke4(false);
      }
   }

   private String resolve(ItemStack itemStack) {
      String text22 = itemStack.getName().getString();
      if (text22.contains("TIER WHITE")) {
         return "вайт";
      } else if (text22.contains("TIER BLACK")) {
         return "блэк";
      } else if (text22.contains("Рассадник монстров")) {
         return "Спавнер";
      } else if (text22.contains("Прогрузчик чанков [1x1]")) {
         return "Прогрузчик чанков";
      } else if (text22.contains("Яйцо призыва зомби-крестьянина")) {
         return "Яйцо зомби-крестьянина";
      } else {
         String text23 = text22.replaceAll("(?i)§.", "")
            .replaceAll("(?i)&.", "")
            .replace(' ', ' ')
            .replaceAll("\\[[^\\]]*]", " ")
            .replaceAll("[★✦✧✪✫✬✭✮✯✰❄☃⚒☠❤❣♕♛♜♞♟\ud83c\udf79]", " ")
            .replace("xxx", " ")
            .replaceAll("\\s+", " ")
            .trim();
         if (text23.isEmpty()) {
            text23 = this.resolve3(itemStack.getItem().getName().getString());
         }

         return text23;
      }
   }

   private String resolve2(String string) {
      return HolyWorldItemParser.resolve7(this.resolve3(string)).toLowerCase(Locale.ROOT).replaceAll("[^\\p{L}\\p{N}]+", "");
   }

   private String resolve3(String string) {
      return string == null ? "" : string.replaceAll("§.", "").replace(' ', ' ').trim();
   }

   private boolean check23(String string) {
      if (string == null) {
         return false;
      } else {
         String text24 = this.resolve3(string).toLowerCase(Locale.ROOT);
         return text24.contains("выстав") && text24.contains("продаж");
      }
   }

   private boolean check24(String string) {
      if (string == null) {
         return false;
      } else {
         String text25 = this.resolve3(string).toLowerCase(Locale.ROOT);
         return !text25.contains("у вас купили") || !text25.contains("на /ah") && !text25.contains(" за ")
            ? text25.contains("купил у вас") && text25.contains(" за ") && (text25.contains("¤") || text25.contains("$"))
            : true;
      }
   }

   public static long compute14(Slot slot) {
      if (!slot.hasStack()) {
         return 0L;
      } else {
         ItemStack itemStack8 = slot.getStack();
         LoreComponent loreComponent = (LoreComponent)itemStack8.getComponents().get(DataComponentTypes.LORE);
         if (loreComponent != null) {
            for (Text text26 : loreComponent.lines()) {
               String text27 = text26.getString();
               if (text27.contains("$") || text27.contains("Цена")) {
                  String text28 = text27.replaceAll("[^0-9]", "");
                  if (!text28.isEmpty()) {
                     try {
                        return Long.parseLong(text28);
                     } catch (NumberFormatException numberFormatException2) {
                     }
                  }
               }
            }
         }

         return 0L;
      }
   }

   static enum AutoSellState {
      IDLE,
      PREPARING,
      SPLITTING,
      SEARCHING,
      SCANNING,
      SELLING,
      FINISHING,
      SELLGUI_SELLING,
      SELLGUI_WAITING_RESULT,
      RESALE_SEARCH_OWN_AH,
      RESALE_WAITING_OWN_AH,
      RESALE_TAKE_ITEM,
      RESALE_SELLING,
      RESALE_WAIT_SELL_RESULT,
      HOLY_SELLING,
      HOLY_OPENING_AUCTION;
   }
}
