package ru.metaculture.protection;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "ClickPearl",
   category = Category.Player,
   description = "Зажми бинд — превью траектории (Predictions), отпусти — бросок жемчуга"
)
public class ClickPearl extends Module {
   public static KeybindSetting knopkaZhemchuga = new KeybindSetting("Кнопка жемчуга", -1, true);
   public static boolean flag = false;
   private static final long TIMESTAMP = 100L;
   private static final String MIDDLECLICK_PEARL = "MiddleClick_Pearl";
   private static final long TIMESTAMP_2 = 900L;
   private static final int INT_VALUE = 3;
   private static final long TIMESTAMP_3 = 150L;
   private static ItemStack itemStack = ItemStack.EMPTY;
   public static boolean flag2 = false;
   private boolean flag3 = false;
   private boolean flag4 = false;
   private int intValue = -1;
   private int intValue2 = -1;
   private int intValue3 = -1;
   private Item item = null;
   private long timestamp = 0L;
   private int intValue4 = 0;
   private int intValue5 = 0;
   private boolean flag5 = false;
   private int intValue6 = 0;
   private long timestamp2 = 0L;
   private long timestamp3 = 0L;

   public ClickPearl() {
      this.addSettings(new Setting[]{knopkaZhemchuga});
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player != null && CLIENT.interactionManager != null && CLIENT.world != null) {
         this.invoke5();
         if (this.intValue4 > 0) {
            this.invoke2();
         } else {
            boolean flag = knopkaZhemchuga.getKeyCode() != -1 && KeybindSetting.isPressed(knopkaZhemchuga.getKeyCode()) && CLIENT.currentScreen == null;
            if (flag && this.check()) {
               this.flag3 = true;
               flag = true;
            } else if (this.flag3) {
               this.flag3 = false;
               flag = false;
               if (CLIENT.currentScreen == null) {
                  this.invoke();
               }
            } else {
               flag = false;
            }
         }
      } else {
         flag = false;
         this.invoke8();
      }
   }

   private boolean check() {
      int intValue = ItemStackUtils.compute(Items.ENDER_PEARL);
      if (intValue == -1) {
         return false;
      } else {
         ItemStack itemStack = this.resolve(intValue);
         return !itemStack.isEmpty() && !CLIENT.player.getItemCooldownManager().isCoolingDown(itemStack);
      }
   }

   private void invoke() {
      if (System.currentTimeMillis() - this.timestamp >= 100L) {
         int intValue2 = ItemStackUtils.compute(Items.ENDER_PEARL);
         if (intValue2 != -1) {
            ItemStack itemStack2 = this.resolve(intValue2);
            if (!itemStack2.isEmpty() && !CLIENT.player.getItemCooldownManager().isCoolingDown(itemStack2)) {
               itemStack = itemStack2.copy();
               this.intValue2 = CLIENT.player.getInventory().getSelectedSlot();
               this.flag5 = false;
               flag2 = true;
               if (this.check2(intValue2)) {
                  this.intValue = this.compute(intValue2);
                  this.flag4 = false;
               } else {
                  this.intValue3 = intValue2;
                  this.item = CLIENT.player.getInventory().getStack(this.intValue2).getItem();
                  this.flag4 = true;
               }

               this.intValue4 = 1;
               this.intValue5 = 0;
            }
         }
      }
   }

   private void invoke2() {
      this.invoke7();
      if (this.intValue5 > 0) {
         this.intValue5--;
      } else {
         if (!this.flag4) {
            switch (this.intValue4) {
               case 1:
                  if (this.intValue != this.intValue2) {
                     this.invoke6(this.intValue);
                  }

                  this.intValue4 = 2;
                  this.intValue5 = 0;
                  break;
               case 2:
                  this.invoke3();
                  this.intValue4 = 3;
                  this.intValue5 = 0;
                  break;
               case 3:
                  if (this.intValue != this.intValue2) {
                     this.invoke6(this.intValue2);
                  }

                  this.invoke4();
            }
         } else {
            switch (this.intValue4) {
               case 1:
                  this.intValue4 = 2;
                  this.intValue5 = 0;
                  break;
               case 2:
                  CLIENT.interactionManager
                     .clickSlot(CLIENT.player.playerScreenHandler.syncId, this.intValue3, this.intValue2, SlotActionType.SWAP, CLIENT.player);
                  this.intValue4 = 3;
                  this.intValue5 = 0;
                  break;
               case 3:
                  this.invoke3();
                  this.intValue4 = 4;
                  this.intValue5 = 0;
                  break;
               case 4:
                  CLIENT.interactionManager
                     .clickSlot(CLIENT.player.playerScreenHandler.syncId, this.intValue3, this.intValue2, SlotActionType.SWAP, CLIENT.player);
                  this.intValue4 = 5;
                  this.intValue5 = 1;
                  break;
               case 5:
                  if (CLIENT.getNetworkHandler() != null) {
                     CLIENT.getNetworkHandler().sendPacket(new CloseHandledScreenC2SPacket(CLIENT.player.playerScreenHandler.syncId));
                  }

                  this.flag5 = true;
                  this.intValue6 = 3;
                  this.timestamp2 = System.currentTimeMillis();
                  this.timestamp3 = this.timestamp2;
                  this.invoke4();
            }
         }
      }
   }

   private void invoke3() {
      CLIENT.interactionManager.interactItem(CLIENT.player, Hand.MAIN_HAND);
      CLIENT.player.swingHand(Hand.MAIN_HAND);
   }

   private void invoke4() {
      this.timestamp = System.currentTimeMillis();
      InputUtils.getINSTANCE().invoke2("MiddleClick_Pearl");
      this.intValue4 = 0;
      this.intValue5 = 0;
      this.flag4 = false;
      flag2 = false;
   }

   private void invoke5() {
      if (this.flag5) {
         if (CLIENT.player != null && CLIENT.interactionManager != null) {
            long longValue = System.currentTimeMillis();
            if (longValue - this.timestamp2 < 900L && this.intValue6 > 0 && this.item != null) {
               Item item = CLIENT.player.getInventory().getStack(this.intValue2).getItem();
               if (item != this.item) {
                  if (longValue - this.timestamp3 >= 150L) {
                     CLIENT.interactionManager
                        .clickSlot(
                           CLIENT.player.playerScreenHandler.syncId, this.intValue3, this.intValue2, SlotActionType.SWAP, CLIENT.player
                        );
                     this.intValue6--;
                     this.timestamp3 = longValue;
                  }
               }
            } else {
               this.flag5 = false;
            }
         } else {
            this.flag5 = false;
         }
      }
   }

   private void invoke6(int i) {
      if (i >= 0 && i <= 8 && CLIENT.player != null) {
         CLIENT.player.getInventory().setSelectedSlot(i);
      }
   }

   private void invoke7() {
      Sprint.intValue = 2;
      CLIENT.options.sprintKey.setPressed(false);
      CLIENT.player.setSprinting(false);
      InputUtils.getINSTANCE().invoke("MiddleClick_Pearl");
   }

   private void invoke8() {
      if (this.intValue4 > 0) {
         InputUtils.getINSTANCE().invoke2("MiddleClick_Pearl");
      }

      this.flag3 = false;
      this.flag4 = false;
      this.intValue4 = 0;
      this.intValue5 = 0;
      this.flag5 = false;
      flag2 = false;
   }

   private ItemStack resolve(int i) {
      if (CLIENT.player == null) {
         return ItemStack.EMPTY;
      } else if (i >= 36 && i <= 44) {
         return CLIENT.player.getInventory().getStack(i - 36);
      } else {
         return i >= 0 && i < 36 ? CLIENT.player.getInventory().getStack(i) : ItemStack.EMPTY;
      }
   }

   public static ItemStack resolve2() {
      return itemStack.copy();
   }

   @Override
   public void onDisable() {
      flag = false;
      this.invoke8();
      super.onDisable();
   }

   private boolean check2(int i) {
      return i >= 0 && i <= 8 || i >= 36 && i <= 44;
   }

   private int compute(int i) {
      if (i >= 0 && i <= 8) {
         return i;
      } else {
         return i >= 36 && i <= 44 ? i - 36 : -1;
      }
   }
}
