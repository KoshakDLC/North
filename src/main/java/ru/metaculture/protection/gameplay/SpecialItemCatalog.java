package ru.metaculture.protection;

import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.component.type.ItemEnchantmentsComponent.Builder;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper.Impl;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public final class SpecialItemCatalog {
   public static ItemStack resolve() {
      ItemStack itemStack2 = new ItemStack(Items.NETHERITE_HELMET);
      invoke(
         itemStack2,
         Enchantments.UNBREAKING,
         5,
         Enchantments.MENDING,
         1,
         Enchantments.FIRE_PROTECTION,
         5,
         Enchantments.PROJECTILE_PROTECTION,
         5,
         Enchantments.BLAST_PROTECTION,
         5,
         Enchantments.AQUA_AFFINITY,
         1,
         Enchantments.RESPIRATION,
         3,
         Enchantments.PROTECTION,
         5
      );
      invoke2(itemStack2, resolve10("Шлем Крушителя"), List.of(Text.literal("[★] Оригинальный предмет").formatted(Formatting.GRAY)));
      return itemStack2;
   }

   public static ItemStack resolve2() {
      ItemStack itemStack3 = new ItemStack(Items.NETHERITE_CHESTPLATE);
      invoke(
         itemStack3,
         Enchantments.BLAST_PROTECTION,
         5,
         Enchantments.MENDING,
         1,
         Enchantments.FIRE_PROTECTION,
         5,
         Enchantments.PROJECTILE_PROTECTION,
         5,
         Enchantments.PROTECTION,
         5,
         Enchantments.UNBREAKING,
         5
      );
      invoke2(itemStack3, resolve10("Нагрудник Крушителя"), List.of(Text.literal("[★] Оригинальный предмет").formatted(Formatting.GRAY)));
      return itemStack3;
   }

   public static ItemStack resolve3() {
      ItemStack itemStack4 = new ItemStack(Items.NETHERITE_LEGGINGS);
      invoke(
         itemStack4,
         Enchantments.BLAST_PROTECTION,
         5,
         Enchantments.MENDING,
         1,
         Enchantments.FIRE_PROTECTION,
         5,
         Enchantments.PROJECTILE_PROTECTION,
         5,
         Enchantments.PROTECTION,
         5,
         Enchantments.UNBREAKING,
         5
      );
      invoke2(itemStack4, resolve10("Поножи Крушителя"), List.of(Text.literal("[★] Оригинальный предмет").formatted(Formatting.GRAY)));
      return itemStack4;
   }

   public static ItemStack resolve4() {
      ItemStack itemStack5 = new ItemStack(Items.NETHERITE_BOOTS);
      invoke(
         itemStack5,
         Enchantments.MENDING,
         1,
         Enchantments.FIRE_PROTECTION,
         5,
         Enchantments.DEPTH_STRIDER,
         3,
         Enchantments.PROJECTILE_PROTECTION,
         5,
         Enchantments.FEATHER_FALLING,
         4,
         Enchantments.SOUL_SPEED,
         3,
         Enchantments.BLAST_PROTECTION,
         5,
         Enchantments.PROTECTION,
         5,
         Enchantments.UNBREAKING,
         5
      );
      invoke2(itemStack5, resolve10("Ботинки Крушителя"), List.of(Text.literal("[★] Оригинальный предмет").formatted(Formatting.GRAY)));
      return itemStack5;
   }

   public static ItemStack resolve5() {
      ItemStack itemStack6 = new ItemStack(Items.NETHERITE_SWORD);
      invoke(
         itemStack6,
         Enchantments.UNBREAKING,
         5,
         Enchantments.MENDING,
         1,
         Enchantments.SMITE,
         7,
         Enchantments.SWEEPING_EDGE,
         3,
         Enchantments.FIRE_ASPECT,
         2,
         Enchantments.BANE_OF_ARTHROPODS,
         7,
         Enchantments.SHARPNESS,
         7,
         Enchantments.LOOTING,
         5
      );
      invoke2(
         itemStack6,
         resolve10("Меч Крушителя"),
         List.of(
            Text.literal("Опытный III").formatted(Formatting.GRAY),
            Text.literal("Вампиризм II").formatted(Formatting.GRAY),
            Text.literal("Окисление II").formatted(Formatting.GRAY),
            Text.literal("Яд III").formatted(Formatting.GRAY),
            Text.literal("Детекция III").formatted(Formatting.GRAY),
            Text.literal("[★] Оригинальный предмет").formatted(Formatting.GRAY)
         )
      );
      return itemStack6;
   }

   public static ItemStack resolve6() {
      ItemStack itemStack7 = new ItemStack(Items.NETHERITE_PICKAXE);
      invoke(itemStack7, Enchantments.UNBREAKING, 5, Enchantments.MENDING, 1, Enchantments.EFFICIENCY, 10, Enchantments.FORTUNE, 5);
      invoke2(
         itemStack7,
         resolve10("Кирка Крушителя"),
         List.of(
            Text.literal("Бульдозер II").formatted(Formatting.GRAY),
            Text.literal("Опытный III").formatted(Formatting.GRAY),
            Text.literal("Магнит").formatted(Formatting.GRAY),
            Text.literal("Авто-Плавка").formatted(Formatting.GRAY),
            Text.literal("Паутина").formatted(Formatting.GRAY),
            Text.literal("Пингер").formatted(Formatting.GRAY),
            Text.literal("[★] Оригинальный предмет").formatted(Formatting.GRAY)
         )
      );
      return itemStack7;
   }

   public static ItemStack resolve7() {
      ItemStack itemStack8 = new ItemStack(Items.CROSSBOW);
      invoke(itemStack8, Enchantments.QUICK_CHARGE, 3, Enchantments.MENDING, 1, Enchantments.PIERCING, 5, Enchantments.UNBREAKING, 3, Enchantments.MULTISHOT, 1);
      invoke2(itemStack8, resolve10("Арбалет Крушителя"), List.of(Text.literal("[★] Оригинальный предмет").formatted(Formatting.GRAY)));
      return itemStack8;
   }

   public static ItemStack resolve8() {
      ItemStack itemStack9 = new ItemStack(Items.TRIDENT);
      invoke(
         itemStack9,
         Enchantments.UNBREAKING,
         5,
         Enchantments.MENDING,
         1,
         Enchantments.CHANNELING,
         1,
         Enchantments.FIRE_ASPECT,
         2,
         Enchantments.IMPALING,
         5,
         Enchantments.SHARPNESS,
         7,
         Enchantments.LOYALTY,
         3
      );
      invoke2(
         itemStack9,
         resolve10("Трезубец Крушителя"),
         List.of(
            Text.literal("Скаут III").formatted(Formatting.GRAY),
            Text.literal("Опытный III").formatted(Formatting.GRAY),
            Text.literal("Вампиризм II").formatted(Formatting.GRAY),
            Text.literal("Ступор III").formatted(Formatting.GRAY),
            Text.literal("Притяжение II").formatted(Formatting.GRAY),
            Text.literal("Окисление II").formatted(Formatting.GRAY),
            Text.literal("Возвращение").formatted(Formatting.GRAY),
            Text.literal("Подрывник").formatted(Formatting.GRAY),
            Text.literal("Яд III").formatted(Formatting.GRAY),
            Text.literal("Детекция III").formatted(Formatting.GRAY),
            Text.literal("[★] Оригинальный предмет").formatted(Formatting.GRAY)
         )
      );
      return itemStack9;
   }

   public static ItemStack resolve9() {
      ItemStack itemStack10 = new ItemStack(Items.MACE);
      invoke(
         itemStack10,
         Enchantments.SHARPNESS,
         7,
         Enchantments.SMITE,
         7,
         Enchantments.BANE_OF_ARTHROPODS,
         7,
         Enchantments.DENSITY,
         5,
         Enchantments.BREACH,
         3,
         Enchantments.SWEEPING_EDGE,
         3,
         Enchantments.FIRE_ASPECT,
         2,
         Enchantments.LOOTING,
         5,
         Enchantments.UNBREAKING,
         5,
         Enchantments.MENDING,
         1
      );
      invoke2(
         itemStack10,
         resolve10("Булава Крушителя"),
         List.of(
            Text.literal("Опытный III").formatted(Formatting.GRAY),
            Text.literal("Вампиризм II").formatted(Formatting.GRAY),
            Text.literal("Окисление II").formatted(Formatting.GRAY),
            Text.literal("Яд III").formatted(Formatting.GRAY),
            Text.literal("Детекция III").formatted(Formatting.GRAY),
            Text.literal("[★] Оригинальный предмет").formatted(Formatting.GRAY)
         )
      );
      return itemStack10;
   }

   private static void invoke(ItemStack itemStack, Object... objects) {
      MinecraftClient client = MinecraftClient.getInstance();
      if (client != null && client.world != null) {
         DynamicRegistryManager dynamicRegistryManager = client.world.getRegistryManager();
         Impl impl = dynamicRegistryManager.getOrThrow(RegistryKeys.ENCHANTMENT);
         Builder builder = new Builder((ItemEnchantmentsComponent)itemStack.getOrDefault(DataComponentTypes.ENCHANTMENTS, ItemEnchantmentsComponent.DEFAULT));

         for (byte byteValue = 0; byteValue < objects.length; byteValue += 2) {
            RegistryKey registryKey = (RegistryKey)objects[byteValue];
            int intValue = (Integer)objects[byteValue + 1];
            impl.getOptional(registryKey).ifPresent(reference -> builder.add((net.minecraft.registry.entry.RegistryEntry<net.minecraft.enchantment.Enchantment>)reference, intValue));
         }

         itemStack.set(DataComponentTypes.ENCHANTMENTS, builder.build());
      }
   }

   private static void invoke2(ItemStack itemStack, Text text, List<Text> list) {
      itemStack.set(DataComponentTypes.CUSTOM_NAME, text);
      NbtCompound nbtCompound = new NbtCompound();
      nbtCompound.putInt("HideFlags", 127);
      nbtCompound.putBoolean("Unbreakable", true);
      itemStack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbtCompound));
      if (!list.isEmpty()) {
         itemStack.set(DataComponentTypes.LORE, new LoreComponent(list));
      }
   }

   private static Text resolve10(String string) {
      return Text.literal(string).formatted(new Formatting[]{Formatting.BOLD, Formatting.DARK_RED});
   }
}
