package ru.metaculture.protection;

import com.mojang.authlib.properties.Property;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.component.type.ProfileComponent;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.wild.mixin.acceser.HandledScreenAccessor;

public final class ItemRuleCodeGenerator {
   private ItemRuleCodeGenerator() {
   }

   public static String resolve(ItemStack itemStack) {
      if (itemStack != null && !itemStack.isEmpty()) {
         List items = resolve4(itemStack);
         List items2 = resolve5(itemStack);
         List items3 = resolve6(itemStack);
         String text = resolve7(itemStack);
         StringBuilder stringBuilder = new StringBuilder();
         stringBuilder.append("builder(\"")
            .append(resolve15(resolve11(itemStack.getName().getString(), resolve10(itemStack.getItem()))))
            .append("\", ")
            .append(resolve9(itemStack.getItem()))
            .append(")");
         if (!items2.isEmpty()) {
            stringBuilder.append("\n        .enchantments(").append(resolve13(items2)).append(")");
         }

         if (!items3.isEmpty()) {
            stringBuilder.append("\n        .attributes(").append(String.join(", ", items3)).append(")");
         }

         if (!items.isEmpty()) {
            stringBuilder.append("\n        .lore(").append(resolve13(items)).append(")");
         }

         if (!text.isBlank()) {
            stringBuilder.append("\n        .texture(\"").append(resolve15(text)).append("\")");
         }

         stringBuilder.append("\n        .build(),");
         return stringBuilder.toString();
      } else {
         return "";
      }
   }

   public static String resolve2(ItemStack itemStack) {
      String text2 = resolve(itemStack);
      if (text2.isBlank()) {
         return "";
      } else {
         StringBuilder stringBuilder2 = new StringBuilder(text2);
         stringBuilder2.append("\n\ncomponents=").append(itemStack.getComponents());
         return stringBuilder2.toString();
      }
   }

   public static boolean copyHoveredItemRule(MinecraftClient minecraftClient) {
      if (minecraftClient != null && minecraftClient.currentScreen instanceof HandledScreen handledScreen2) {
         Slot slot = resolve3(minecraftClient, handledScreen2);
         if (slot != null && slot.hasStack() && !slot.getStack().isEmpty()) {
            ItemStack itemStack2 = slot.getStack();
            String text3 = resolve(itemStack2);
            if (text3.isBlank()) {
               ChatUtil.sendClientMessage("§c[AutoBuy] §fНе удалось собрать код предмета.");
               return true;
            } else {
               minecraftClient.keyboard.setClipboard(text3);
               invoke(itemStack2, text3);
               ChatUtil.sendClientMessage("§a[AutoBuy] §fКод предмета скопирован в буфер и записан в configs/autobuy/dumps.");
               return true;
            }
         } else {
            ChatUtil.sendClientMessage("§c[AutoBuy] §fПод курсором нет предмета.");
            return true;
         }
      } else {
         return false;
      }
   }

   private static Slot resolve3(MinecraftClient minecraftClient, HandledScreen<?> handledScreen) {
      HandledScreenAccessor handledScreenAccessor = (HandledScreenAccessor)handledScreen;
      Slot slot2 = handledScreenAccessor.litka$getFocusedSlot();
      if (slot2 != null) {
         return slot2;
      } else if (minecraftClient.getWindow() == null) {
         return null;
      } else {
         double doubleValue = minecraftClient.mouse.getScaledX(minecraftClient.getWindow());
         double doubleValue2 = minecraftClient.mouse.getScaledY(minecraftClient.getWindow());
         return handledScreenAccessor.getSlotAtPosition(doubleValue, doubleValue2);
      }
   }

   private static void invoke(ItemStack itemStack, String string) {
      try {
         File file = WildClient.INSTANCE != null && WildClient.INSTANCE.file != null ? WildClient.INSTANCE.file : new File(".");
         File file2 = new File(file, "configs/autobuy/dumps");
         if (!file2.exists()) {
            file2.mkdirs();
         }

         File file3 = new File(file2, "DonatItemsHW-snippets.txt");

         try (FileWriter fileWriter = new FileWriter(file3, true)) {
            fileWriter.write("\n\n");
            fileWriter.write(string);
            fileWriter.write("\n");
         }
      } catch (Exception exception) {
         ChatUtil.sendClientMessage("§e[AutoBuy] §fКод скопирован, но файл дампа не записался: " + exception.getClass().getSimpleName());
      }
   }

   private static List<String> resolve4(ItemStack itemStack) {
      ArrayList arrayList = new ArrayList();
      LoreComponent loreComponent = (LoreComponent)itemStack.get(DataComponentTypes.LORE);
      if (loreComponent == null) {
         return arrayList;
      } else {
         for (Text text4 : loreComponent.lines()) {
            String text5 = resolve12(text4.getString()).trim();
            if (!text5.isBlank() && !check(text5)) {
               arrayList.add(text5);
            }
         }

         return arrayList;
      }
   }

   private static List<String> resolve5(ItemStack itemStack) {
      ArrayList arrayList2 = new ArrayList();
      ItemEnchantmentsComponent itemEnchantmentsComponent = (ItemEnchantmentsComponent)itemStack.get(DataComponentTypes.ENCHANTMENTS);
      if (itemEnchantmentsComponent != null && !itemEnchantmentsComponent.isEmpty()) {
         for (Entry entry : itemEnchantmentsComponent.getEnchantmentEntries()) {
            String text6 = (String)((RegistryEntry)entry.getKey()).getKey().map(registryKey -> ((net.minecraft.registry.RegistryKey)registryKey).getValue().toString()).orElse("");
            if (!text6.isBlank()) {
               arrayList2.add(text6 + ":" + entry.getIntValue());
            }
         }

         return arrayList2;
      } else {
         return arrayList2;
      }
   }

   private static List<String> resolve6(ItemStack itemStack) {
      ArrayList arrayList3 = new ArrayList();
      AttributeModifiersComponent attributeModifiersComponent = (AttributeModifiersComponent)itemStack.get(DataComponentTypes.ATTRIBUTE_MODIFIERS);
      if (attributeModifiersComponent == null) {
         return arrayList3;
      } else {
         for (net.minecraft.component.type.AttributeModifiersComponent.Entry entry2 : attributeModifiersComponent.modifiers()) {
            EntityAttributeModifier entityAttributeModifier = entry2.modifier();
            String text7 = resolve8(entry2.attribute());
            if (!text7.isBlank()) {
               arrayList3.add("attr(\"" + resolve15(text7) + "\", " + resolve14(entityAttributeModifier.value()) + ")");
            }
         }

         return arrayList3;
      }
   }

   private static String resolve7(ItemStack itemStack) {
      ProfileComponent profileComponent = (ProfileComponent)itemStack.get(DataComponentTypes.PROFILE);
      if (profileComponent != null && profileComponent.gameProfile() != null) {
         Collection items4 = profileComponent.gameProfile().getProperties().get("textures");
         if (items4 != null && !items4.isEmpty()) {
            Property property = (Property)items4.iterator().next();
            return property != null && property.value() != null ? property.value() : "";
         } else {
            return "";
         }
      } else {
         return "";
      }
   }

   private static String resolve8(RegistryEntry<EntityAttribute> registryEntry) {
      return registryEntry.getKey().map(registryKey -> registryKey.getValue().toString()).orElse("");
   }

   private static String resolve9(Item item) {
      Identifier identifier = Registries.ITEM.getId(item);
      return !"minecraft".equals(identifier.getNamespace())
         ? "Registries.ITEM.get(Identifier.of(\"" + resolve15(identifier.toString()) + "\"))"
         : "Items." + identifier.getPath().toUpperCase(Locale.ROOT);
   }

   private static String resolve10(Item item) {
      Identifier identifier2 = Registries.ITEM.getId(item);
      return identifier2.getPath().replace('_', ' ');
   }

   private static String resolve11(String string, String string2) {
      String text8 = resolve12(string).trim();
      return text8.isBlank() ? string2 : text8;
   }

   private static boolean check(String string) {
      String text9 = resolve12(string).toLowerCase(Locale.ROOT);
      return text9.contains("цена")
         || text9.contains("продавец")
         || text9.contains("купить")
         || text9.contains("нажмите")
         || text9.contains("лкм")
         || text9.contains("пкм")
         || text9.contains("shift")
         || text9.contains("страница")
         || text9.contains("истекает")
         || text9.contains("доступно")
         || text9.contains("аукцион");
   }

   private static String resolve12(String string) {
      return string == null ? "" : string.replaceAll("(?i)§[0-9A-FK-OR]", "");
   }

   private static String resolve13(List<String> list) {
      ArrayList arrayList4 = new ArrayList();

      for (String text10 : list) {
         arrayList4.add("\"" + resolve15(text10) + "\"");
      }

      return String.join(", ", arrayList4);
   }

   private static String resolve14(double d) {
      BigDecimal bigDecimal = BigDecimal.valueOf(d).stripTrailingZeros();
      String text11 = bigDecimal.toPlainString();
      return text11.contains(".") ? text11 : text11 + ".0";
   }

   private static String resolve15(String string) {
      return string.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");
   }
}
