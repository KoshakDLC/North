package ru.metaculture.protection;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;

public class AuctionPriceParser {
   public static String resolve(Slot slot) {
      if (!slot.hasStack()) {
         return null;
      } else {
         ItemStack itemStack = slot.getStack();
         LoreComponent loreComponent = (LoreComponent)itemStack.getComponents().get(DataComponentTypes.LORE);
         if (loreComponent != null) {
            for (Text text : loreComponent.lines()) {
               String text2 = text.getString();
               if (text2.contains("Продавец:")) {
                  return text2.replaceFirst(".*?Продавец:\\s*", "").trim();
               }
            }
         }

         return null;
      }
   }

   public static long compute(Slot slot) {
      if (!slot.hasStack()) {
         return 0L;
      } else {
         ItemStack itemStack2 = slot.getStack();
         LoreComponent loreComponent2 = (LoreComponent)itemStack2.getComponents().get(DataComponentTypes.LORE);
         if (loreComponent2 != null) {
            for (Text text3 : loreComponent2.lines()) {
               String text4 = text3.getString();
               if (text4.contains("$")) {
                  String text5 = text4.replaceAll("[^0-9]", "");
                  if (!text5.isEmpty()) {
                     try {
                        return Long.parseLong(text5);
                     } catch (NumberFormatException numberFormatException) {
                     }
                  }
               }
            }
         }

         return 0L;
      }
   }
}
