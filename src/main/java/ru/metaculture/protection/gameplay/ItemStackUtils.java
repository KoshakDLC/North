package ru.metaculture.protection;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.screen.slot.SlotActionType;

public class ItemStackUtils implements MinecraftAccessor {
   public static void invoke(int i) {
      if (a_.player != null && i >= 0 && i <= 8) {
         if (a_.player.getInventory().getSelectedSlot() != i) {
            a_.player.getInventory().setSelectedSlot(i);
            a_.player.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(i));
         }
      }
   }

   public static void invoke2(int i, int j) {
      if (a_.player != null && a_.interactionManager != null) {
         int intValue = a_.player.playerScreenHandler.syncId;
         if (i >= 36 && i <= 44) {
            a_.interactionManager.clickSlot(intValue, j, i % 9, SlotActionType.SWAP, a_.player);
         } else {
            int intValue2 = a_.player.getInventory().getSelectedSlot();
            a_.interactionManager.clickSlot(intValue, i, intValue2, SlotActionType.SWAP, a_.player);
            a_.interactionManager.clickSlot(intValue, j, intValue2, SlotActionType.SWAP, a_.player);
            a_.interactionManager.clickSlot(intValue, i, intValue2, SlotActionType.SWAP, a_.player);
         }
      }
   }

   public static int compute(Item item) {
      if (a_.player == null) {
         return -1;
      } else {
         int intValue3 = -1;

         for (int intValue4 = 0; intValue4 < 36; intValue4++) {
            ItemStack itemStack = a_.player.getInventory().getStack(intValue4);
            if (!itemStack.isEmpty() && itemStack.getItem() == item) {
               intValue3 = intValue4;
               break;
            }
         }

         if (intValue3 < 9 && intValue3 != -1) {
            intValue3 += 36;
         }

         return intValue3;
      }
   }

   public static int compute2(Item item, boolean bl) {
      return compute3(item, bl, false);
   }

   public static int compute3(Item item, boolean bl, boolean bl2) {
      if (a_.player == null) {
         return -1;
      } else {
         int intValue5 = -1;
         if (bl2) {
            for (int intValue6 = 0; intValue6 < 36; intValue6++) {
               ItemStack itemStack2 = a_.player.getInventory().getStack(intValue6);
               if (!itemStack2.isEmpty() && itemStack2.getItem() == item && itemStack2.hasEnchantments()) {
                  intValue5 = intValue6;
                  break;
               }
            }
         } else {
            for (int intValue7 = 0; intValue7 < 36; intValue7++) {
               ItemStack itemStack3 = a_.player.getInventory().getStack(intValue7);
               if (!itemStack3.isEmpty() && itemStack3.getItem() == item && !itemStack3.hasEnchantments()) {
                  intValue5 = intValue7;
                  break;
               }
            }

            if (intValue5 == -1 && !bl) {
               for (int intValue8 = 0; intValue8 < 36; intValue8++) {
                  ItemStack itemStack4 = a_.player.getInventory().getStack(intValue8);
                  if (!itemStack4.isEmpty() && itemStack4.getItem() == item) {
                     intValue5 = intValue8;
                     break;
                  }
               }
            }
         }

         if (intValue5 < 9 && intValue5 != -1) {
            intValue5 += 36;
         }

         return intValue5;
      }
   }
}
