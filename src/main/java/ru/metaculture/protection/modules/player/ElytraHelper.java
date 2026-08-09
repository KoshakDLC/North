package ru.metaculture.protection;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "ElytraHelper",
   description = "Автоматически юзает фейерверки/свапает на элики",
   category = Category.Player
)
public class ElytraHelper extends Module {
   public static ElytraHelper instance;
   public static KeybindSetting svapNaNagrudnik = new KeybindSetting("Свап на нагрудник", -1);
   public static KeybindSetting feyerverk = new KeybindSetting("Фейерверк", -1);
   public final BooleanSetting svapatPriKd = new BooleanSetting("Свапать при КД", false);
   private int intValue = 0;
   private int intValue2 = 0;
   private int intValue3 = -1;
   private int intValue4 = -1;
   private boolean flag = false;

   public ElytraHelper() {
      this.addSettings(new Setting[]{svapNaNagrudnik, feyerverk, this.svapatPriKd});
      instance = this;
   }

   @EventHandler
   private void onRawInput(RawInputEvent rawInputEvent) {
      if (rawInputEvent.getAction() == 1 && CLIENT.player != null) {
         if (rawInputEvent.getKeyCode() == svapNaNagrudnik.getKeyCode()
            && this.intValue == 0
            && (!CLIENT.player.isUsingItem() || CLIENT.player.getOffHandStack().getItem() == Items.SHIELD)) {
            ItemStack itemStack = CLIENT.player.getEquippedStack(EquipmentSlot.CHEST);
            int intValue = itemStack.getItem() == Items.ELYTRA ? this.compute() : ItemStackUtils.compute(Items.ELYTRA);
            if (intValue >= 0) {
               this.intValue3 = intValue;
               this.flag = false;
               this.intValue = 1;
               this.invoke();
            }
         }

         if (rawInputEvent.getKeyCode() == feyerverk.getKeyCode() && this.intValue == 0) {
            int intValue2 = ItemStackUtils.compute(Items.FIREWORK_ROCKET);
            if (intValue2 != -1) {
               this.intValue3 = intValue2;
               this.flag = true;
               this.intValue = 1;
               this.invoke();
            }
         }
      }
   }

   @EventHandler
   private void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player == null || CLIENT.currentScreen != null) {
         this.invoke4();
      } else if (this.intValue > 0) {
         if (this.intValue2 > 0) {
            this.intValue2--;
         } else {
            this.invoke();
         }
      } else {
         if (this.svapatPriKd.isEnabled()) {
            boolean flag = CLIENT.player.getEquippedStack(EquipmentSlot.CHEST).getItem() == Items.ELYTRA;
            if (flag && ServerStatsParser.check()) {
               int intValue3 = this.compute();
               if (intValue3 != -1) {
                  this.intValue3 = intValue3;
                  this.flag = false;
                  this.intValue = 1;
                  this.invoke();
               }
            }
         }
      }
   }

   private void invoke() {
      if (this.flag) {
         this.invoke2();
      } else {
         this.invoke3();
      }
   }

   private void invoke2() {
      switch (this.intValue) {
         case 1:
            InputUtils.getINSTANCE().invoke("ElytraHelper_FW");
            if (CLIENT.player.isSprinting()) {
               CLIENT.player.setSprinting(false);
            }

            this.intValue = 2;
            this.intValue2 = 1;
            break;
         case 2:
            this.intValue4 = CLIENT.player.getInventory().getSelectedSlot();
            if (this.intValue3 < 9) {
               ItemStackUtils.invoke(this.intValue3);
            } else {
               CLIENT.interactionManager
                  .clickSlot(CLIENT.player.playerScreenHandler.syncId, this.intValue3, this.intValue4, SlotActionType.SWAP, CLIENT.player);
            }

            this.intValue = 3;
            this.intValue2 = 1;
            break;
         case 3:
            CLIENT.interactionManager.interactItem(CLIENT.player, Hand.MAIN_HAND);
            CLIENT.player.swingHand(Hand.MAIN_HAND);
            this.intValue = 4;
            this.intValue2 = 1;
            break;
         case 4:
            if (this.intValue3 < 9) {
               ItemStackUtils.invoke(this.intValue4);
            } else {
               CLIENT.interactionManager
                  .clickSlot(CLIENT.player.playerScreenHandler.syncId, this.intValue3, this.intValue4, SlotActionType.SWAP, CLIENT.player);
            }

            InputUtils.getINSTANCE().invoke2("ElytraHelper_FW");
            this.intValue = 0;
      }
   }

   private void invoke3() {
      switch (this.intValue) {
         case 1:
            InputUtils.getINSTANCE().invoke("ElytraHelper");
            if (CLIENT.player.isSprinting()) {
               CLIENT.player.setSprinting(false);
            }

            this.intValue = 2;
            this.intValue2 = 1;
            break;
         case 2:
            ItemStackUtils.invoke2(this.intValue3, 6);
            if (CLIENT.getNetworkHandler() != null) {
               CLIENT.getNetworkHandler().sendPacket(new CloseHandledScreenC2SPacket(CLIENT.player.playerScreenHandler.syncId));
            }

            this.intValue = 3;
            this.intValue2 = 1;
            break;
         case 3:
            InputUtils.getINSTANCE().invoke2("ElytraHelper");
            this.intValue = 0;
      }
   }

   private void invoke4() {
      if (this.intValue > 0) {
         InputUtils.getINSTANCE().invoke2(this.flag ? "ElytraHelper_FW" : "ElytraHelper");
      }

      this.intValue = 0;
      this.intValue2 = 0;
      this.intValue3 = -1;
   }

   private int compute() {
      Item[] items = new Item[]{
         Items.NETHERITE_CHESTPLATE,
         Items.DIAMOND_CHESTPLATE,
         Items.CHAINMAIL_CHESTPLATE,
         Items.GOLDEN_CHESTPLATE,
         Items.IRON_CHESTPLATE,
         Items.LEATHER_CHESTPLATE
      };

      for (Item item2 : items) {
         int intValue4 = ItemStackUtils.compute(item2);
         if (intValue4 != -1) {
            return intValue4;
         }
      }

      return -1;
   }

   public static boolean check() {
      if (CLIENT.player != null && CLIENT.interactionManager != null) {
         if (CLIENT.player.getEquippedStack(EquipmentSlot.CHEST).getItem() == Items.ELYTRA) {
            return true;
         } else {
            int intValue5 = ItemStackUtils.compute(Items.ELYTRA);
            if (intValue5 == -1) {
               return false;
            } else {
               ItemStackUtils.invoke2(intValue5, 6);
               if (CLIENT.getNetworkHandler() != null) {
                  CLIENT.getNetworkHandler().sendPacket(new CloseHandledScreenC2SPacket(CLIENT.player.playerScreenHandler.syncId));
               }

               return true;
            }
         }
      } else {
         return false;
      }
   }

   public static boolean check2() {
      if (CLIENT.player != null && CLIENT.interactionManager != null) {
         Item[] items2 = new Item[]{
            Items.NETHERITE_CHESTPLATE,
            Items.DIAMOND_CHESTPLATE,
            Items.IRON_CHESTPLATE,
            Items.CHAINMAIL_CHESTPLATE,
            Items.GOLDEN_CHESTPLATE,
            Items.LEATHER_CHESTPLATE
         };

         for (Item item3 : items2) {
            int intValue6 = ItemStackUtils.compute(item3);
            if (intValue6 != -1) {
               ItemStackUtils.invoke2(intValue6, 6);
               if (CLIENT.getNetworkHandler() != null) {
                  CLIENT.getNetworkHandler().sendPacket(new CloseHandledScreenC2SPacket(CLIENT.player.playerScreenHandler.syncId));
               }

               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public static boolean check3() {
      return check4(Items.FIREWORK_ROCKET);
   }

   private static boolean check4(Item item) {
      if (CLIENT.player != null && CLIENT.interactionManager != null) {
         int intValue7 = ItemStackUtils.compute(item);
         if (intValue7 == -1) {
            return false;
         } else {
            int intValue8 = CLIENT.player.getInventory().getSelectedSlot();
            boolean flag2 = intValue7 >= 36 && intValue7 <= 44;
            if (flag2) {
               ItemStackUtils.invoke(intValue7 - 36);
            } else {
               CLIENT.interactionManager.clickSlot(CLIENT.player.playerScreenHandler.syncId, intValue7, intValue8, SlotActionType.SWAP, CLIENT.player);
            }

            CLIENT.interactionManager.interactItem(CLIENT.player, Hand.MAIN_HAND);
            CLIENT.player.swingHand(Hand.MAIN_HAND);
            if (flag2) {
               ItemStackUtils.invoke(intValue8);
            } else {
               CLIENT.interactionManager.clickSlot(CLIENT.player.playerScreenHandler.syncId, intValue7, intValue8, SlotActionType.SWAP, CLIENT.player);
            }

            return true;
         }
      } else {
         return false;
      }
   }

   @Override
   public void onDisable() {
      this.invoke4();
      super.onDisable();
   }
}
