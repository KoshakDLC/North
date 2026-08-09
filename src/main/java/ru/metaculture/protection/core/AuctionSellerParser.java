package ru.metaculture.protection;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;

public class AuctionSellerParser {
   private static final Pattern PATTERN = Pattern.compile("Продавец:\\s*(.+)");
   private static final Pattern PATTERN_2 = Pattern.compile("\\$(?:[^\\d]*?Цена)?[^\\d]*?([0-9][\\d,]*)");

   public static String resolve(Slot slot) {
      if (!slot.hasStack()) {
         return null;
      } else {
         ItemStack itemStack = slot.getStack();
         LoreComponent loreComponent = (LoreComponent)itemStack.getComponents().get(DataComponentTypes.LORE);
         if (loreComponent != null) {
            for (Text text : loreComponent.lines()) {
               String text2 = text.getString().replaceAll("(?i)§[0-9A-FK-OR]", "");
               Matcher matcher = PATTERN.matcher(text2);
               if (matcher.find()) {
                  return matcher.group(1).trim();
               }
            }
         }

         return null;
      }
   }

   public static int compute(Slot slot) {
      if (!slot.hasStack()) {
         return 0;
      } else {
         ItemStack itemStack2 = slot.getStack();
         LoreComponent loreComponent2 = (LoreComponent)itemStack2.getComponents().get(DataComponentTypes.LORE);
         if (loreComponent2 != null) {
            for (Text text3 : loreComponent2.lines()) {
               String text4 = text3.getString();
               if (text4.contains("$") || text4.contains("Цена")) {
                  String text5 = text4.replaceAll("[^0-9]", "");
                  if (!text5.isEmpty()) {
                     try {
                        return Math.toIntExact(Long.parseLong(text5));
                     } catch (NumberFormatException numberFormatException) {
                     }
                  }
               }
            }
         }

         return 0;
      }
   }
}
