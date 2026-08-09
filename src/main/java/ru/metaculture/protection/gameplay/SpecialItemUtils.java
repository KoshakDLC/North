package ru.metaculture.protection;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.component.type.AttributeModifiersComponent.Entry;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;

public class SpecialItemUtils {
   private static final List<StatusEffectInstance> ITEMS = List.of(
      new StatusEffectInstance(StatusEffects.SLOWNESS, 200, 9),
      new StatusEffectInstance(StatusEffects.SPEED, 400, 4),
      new StatusEffectInstance(StatusEffects.BLINDNESS, 100, 9),
      new StatusEffectInstance(StatusEffects.GLOWING, 3600, 0)
   );
   private static final List<StatusEffectInstance> ITEMS_2 = List.of(
      new StatusEffectInstance(StatusEffects.STRENGTH, 600, 4), new StatusEffectInstance(StatusEffects.SLOWNESS, 600, 3)
   );
   private static final List<StatusEffectInstance> ITEMS_3 = List.of(
      new StatusEffectInstance(StatusEffects.RESISTANCE, 12000, 0),
      new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 12000, 0),
      new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 1200, 2),
      new StatusEffectInstance(StatusEffects.INVISIBILITY, 18000, 0)
   );
   private static final List<StatusEffectInstance> ITEMS_4 = List.of(
      new StatusEffectInstance(StatusEffects.REGENERATION, 900, 1),
      new StatusEffectInstance(StatusEffects.INVISIBILITY, 12000, 1),
      new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 0, 1)
   );
   private static final List<StatusEffectInstance> ITEMS_5 = List.of(
      new StatusEffectInstance(StatusEffects.STRENGTH, 1200, 3),
      new StatusEffectInstance(StatusEffects.SPEED, 6000, 2),
      new StatusEffectInstance(StatusEffects.HASTE, 1200, 0),
      new StatusEffectInstance(StatusEffects.INSTANT_DAMAGE, 0, 1)
   );
   private static final List<StatusEffectInstance> ITEMS_6 = List.of(
      new StatusEffectInstance(StatusEffects.POISON, 1200, 1),
      new StatusEffectInstance(StatusEffects.WITHER, 1200, 1),
      new StatusEffectInstance(StatusEffects.SLOWNESS, 1800, 2),
      new StatusEffectInstance(StatusEffects.HUNGER, 1200, 4),
      new StatusEffectInstance(StatusEffects.GLOWING, 2400, 0)
   );
   private static final List<StatusEffectInstance> ITEMS_7 = List.of(
      new StatusEffectInstance(StatusEffects.WEAKNESS, 1800, 1),
      new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 200, 1),
      new StatusEffectInstance(StatusEffects.WITHER, 1800, 2),
      new StatusEffectInstance(StatusEffects.BLINDNESS, 200, 0)
   );
   private static final List<StatusEffectInstance> ITEMS_8 = List.of(
      new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 3600, 0),
      new StatusEffectInstance(StatusEffects.JUMP_BOOST, 3600, 1),
      new StatusEffectInstance(StatusEffects.LUCK, 3600, 0),
      new StatusEffectInstance(StatusEffects.HASTE, 3600, 1)
   );
   private static final List<SpecialItemUtils.SpecialItemUtilsEntry> ITEMS_9 = List.of(
      new SpecialItemUtils.SpecialItemUtilsEntry("Хлопушка", ITEMS),
      new SpecialItemUtils.SpecialItemUtilsEntry("Зелье Гнева", ITEMS_2),
      new SpecialItemUtils.SpecialItemUtilsEntry("Зелье Палладина", ITEMS_3),
      new SpecialItemUtils.SpecialItemUtilsEntry("Святая Вода", ITEMS_4),
      new SpecialItemUtils.SpecialItemUtilsEntry("Зелье Ассасина", ITEMS_5),
      new SpecialItemUtils.SpecialItemUtilsEntry("Зелье Радиации", ITEMS_6),
      new SpecialItemUtils.SpecialItemUtilsEntry("Снотворное", ITEMS_7)
   );

   public static List<SpecialItemUtils.SpecialItemUtilsEntry> getITEMS_9() {
      return ITEMS_9;
   }

   private static Map<RegistryEntry<EntityAttribute>, Double> resolve(ItemStack itemStack) {
      AttributeModifiersComponent attributeModifiersComponent = (AttributeModifiersComponent)itemStack.get(DataComponentTypes.ATTRIBUTE_MODIFIERS);
      HashMap hashMap = new HashMap();
      if (attributeModifiersComponent == null) {
         return hashMap;
      } else {
         for (Entry entry : attributeModifiersComponent.modifiers()) {
            EntityAttributeModifier entityAttributeModifier = entry.modifier();
            hashMap.put(entry.attribute(), entityAttributeModifier.value());
         }

         return hashMap;
      }
   }

   private static boolean check(Map<RegistryEntry<EntityAttribute>, Double> map, RegistryEntry<EntityAttribute> registryEntry, double d) {
      return Math.abs(map.getOrDefault(registryEntry, 0.0) - d) < 1.0E-4;
   }

   private static boolean check2(ItemStack itemStack, String string) {
      if (!itemStack.isOf(Items.PLAYER_HEAD)) {
         return false;
      } else {
         NbtComponent nbtComponent = (NbtComponent)itemStack.get(DataComponentTypes.CUSTOM_DATA);
         if (nbtComponent == null) {
            return false;
         } else {
            NbtCompound nbtCompound2 = nbtComponent.copyNbt();
            return nbtCompound2.getCompound("SkullOwner")
               .flatMap(nbtCompound -> nbtCompound.getCompound("Properties"))
               .flatMap(nbtCompound -> nbtCompound.getList("textures"))
               .filter(nbtList -> !nbtList.isEmpty())
               .flatMap(nbtList -> nbtList.getCompound(0))
               .flatMap(nbtCompound -> nbtCompound.getString("Value"))
               .map(string2 -> string2.equals(string))
               .orElse(false);
         }
      }
   }

   private static boolean check3(ItemStack itemStack, List<StatusEffectInstance> list) {
      PotionContentsComponent potionContentsComponent = (PotionContentsComponent)itemStack.get(DataComponentTypes.POTION_CONTENTS);
      if (potionContentsComponent == null) {
         return false;
      } else {
         List items = potionContentsComponent.customEffects();

         for (StatusEffectInstance statusEffectInstance : list) {
            boolean flag = false;

            for (StatusEffectInstance statusEffectInstance2 : (List<StatusEffectInstance>)items) {
               if (statusEffectInstance2.getEffectType().equals(statusEffectInstance.getEffectType()) && statusEffectInstance2.getAmplifier() == statusEffectInstance.getAmplifier()) {
                  flag = true;
                  break;
               }
            }

            if (!flag) {
               return false;
            }
         }

         return true;
      }
   }

   private static boolean check4(ItemStack itemStack, String string) {
      return itemStack.getName().getString().contains(string);
   }

   private static boolean check5(ItemStack itemStack, String string) {
      return itemStack.getName().getString().toLowerCase(Locale.ROOT).contains(string.toLowerCase(Locale.ROOT));
   }

   private static boolean check6(ItemStack itemStack, String string) {
      LoreComponent loreComponent = (LoreComponent)itemStack.get(DataComponentTypes.LORE);
      if (loreComponent == null) {
         return false;
      } else {
         for (Text text : loreComponent.lines()) {
            if (text.getString().contains(string)) {
               return true;
            }
         }

         return false;
      }
   }

   private static boolean check7(ItemStack itemStack, String string) {
      String text2 = string.toLowerCase(Locale.ROOT);
      if (itemStack.getName().getString().toLowerCase(Locale.ROOT).contains(text2)) {
         return true;
      } else {
         LoreComponent loreComponent2 = (LoreComponent)itemStack.get(DataComponentTypes.LORE);
         if (loreComponent2 == null) {
            return false;
         } else {
            for (Text text3 : loreComponent2.lines()) {
               if (text3.getString().toLowerCase(Locale.ROOT).contains(text2)) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   public static boolean check8(ItemStack itemStack) {
      Map valuesByKey = resolve(itemStack);
      return check(valuesByKey, EntityAttributes.MAX_HEALTH, -4.0)
         && check(valuesByKey, EntityAttributes.ARMOR, 1.5)
         && check(valuesByKey, EntityAttributes.ATTACK_DAMAGE, 2.5)
         && check(valuesByKey, EntityAttributes.MOVEMENT_SPEED, 0.07)
         && check(valuesByKey, EntityAttributes.ATTACK_SPEED, 0.13)
         && check(valuesByKey, EntityAttributes.GRAVITY, 0.09);
   }

   public static boolean check9(ItemStack itemStack) {
      Map valuesByKey2 = resolve(itemStack);
      return check(valuesByKey2, EntityAttributes.ARMOR, 2.5)
         && check(valuesByKey2, EntityAttributes.ARMOR_TOUGHNESS, 2.5)
         && check(valuesByKey2, EntityAttributes.MOVEMENT_SPEED, -0.15);
   }

   public static boolean check10(ItemStack itemStack) {
      Map valuesByKey3 = resolve(itemStack);
      return check(valuesByKey3, EntityAttributes.ATTACK_DAMAGE, 6.0)
         && check(valuesByKey3, EntityAttributes.ARMOR, -2.0)
         && check(valuesByKey3, EntityAttributes.MAX_HEALTH, -2.0);
   }

   public static boolean check11(ItemStack itemStack) {
      Map valuesByKey4 = resolve(itemStack);
      return check(valuesByKey4, EntityAttributes.ARMOR, 1.0)
         && check(valuesByKey4, EntityAttributes.MAX_HEALTH, 4.0)
         && check(valuesByKey4, EntityAttributes.MOVEMENT_SPEED, 0.1)
         && check(valuesByKey4, EntityAttributes.ATTACK_SPEED, 0.1);
   }

   public static boolean check12(ItemStack itemStack) {
      Map valuesByKey5 = resolve(itemStack);
      return check(valuesByKey5, EntityAttributes.MAX_HEALTH, 4.0)
         && check(valuesByKey5, EntityAttributes.ARMOR, 2.0)
         && check(valuesByKey5, EntityAttributes.SUBMERGED_MINING_SPEED, 0.5)
         && check(valuesByKey5, EntityAttributes.OXYGEN_BONUS, 0.5);
   }

   public static boolean check13(ItemStack itemStack) {
      Map valuesByKey6 = resolve(itemStack);
      return check(valuesByKey6, EntityAttributes.ATTACK_DAMAGE, 2.0) && check(valuesByKey6, EntityAttributes.MAX_HEALTH, 2.0);
   }

   public static boolean check14(ItemStack itemStack) {
      Map valuesByKey7 = resolve(itemStack);
      return check(valuesByKey7, EntityAttributes.LUCK, 1.0)
         && check(valuesByKey7, EntityAttributes.MAX_HEALTH, 2.0)
         && check(valuesByKey7, EntityAttributes.BLOCK_INTERACTION_RANGE, 1.0);
   }

   public static boolean check15(ItemStack itemStack) {
      Map valuesByKey8 = resolve(itemStack);
      return check(valuesByKey8, EntityAttributes.ATTACK_DAMAGE, 2.0)
         && check(valuesByKey8, EntityAttributes.JUMP_STRENGTH, -0.1)
         && check(valuesByKey8, EntityAttributes.ATTACK_SPEED, 0.15);
   }

   public static boolean check16(ItemStack itemStack) {
      return itemStack.isOf(Items.PLAYER_HEAD) && check4(itemStack, "Сфера Мороза") && check6(itemStack, "Вечная мерзлота");
   }

   public static boolean check17(ItemStack itemStack) {
      if (!itemStack.isOf(Items.TOTEM_OF_UNDYING)) {
         return false;
      } else {
         Map valuesByKey9 = resolve(itemStack);
         return check(valuesByKey9, EntityAttributes.ATTACK_DAMAGE, 2.5) && check(valuesByKey9, EntityAttributes.ATTACK_SPEED, 0.1);
      }
   }

   public static boolean check18(ItemStack itemStack) {
      if (!itemStack.isOf(Items.TOTEM_OF_UNDYING)) {
         return false;
      } else {
         Map valuesByKey10 = resolve(itemStack);
         return check(valuesByKey10, EntityAttributes.ATTACK_DAMAGE, 7.0)
            && check(valuesByKey10, EntityAttributes.MAX_HEALTH, -4.0)
            && check(valuesByKey10, EntityAttributes.MOVEMENT_SPEED, 0.1);
      }
   }

   public static boolean check19(ItemStack itemStack) {
      if (!itemStack.isOf(Items.TOTEM_OF_UNDYING)) {
         return false;
      } else {
         Map valuesByKey11 = resolve(itemStack);
         return check(valuesByKey11, EntityAttributes.ARMOR, 1.5) && check(valuesByKey11, EntityAttributes.MAX_HEALTH, 1.5);
      }
   }

   public static boolean check20(ItemStack itemStack) {
      if (!itemStack.isOf(Items.TOTEM_OF_UNDYING)) {
         return false;
      } else {
         Map valuesByKey12 = resolve(itemStack);
         return check(valuesByKey12, EntityAttributes.ATTACK_DAMAGE, 5.0) && check(valuesByKey12, EntityAttributes.MAX_HEALTH, -4.0);
      }
   }

   public static boolean check21(ItemStack itemStack) {
      if (!itemStack.isOf(Items.TOTEM_OF_UNDYING)) {
         return false;
      } else {
         Map valuesByKey13 = resolve(itemStack);
         return check(valuesByKey13, EntityAttributes.ATTACK_DAMAGE, 2.0)
            && check(valuesByKey13, EntityAttributes.ARMOR, 2.0)
            && check(valuesByKey13, EntityAttributes.MAX_HEALTH, -4.0);
      }
   }

   public static boolean check22(ItemStack itemStack) {
      if (!itemStack.isOf(Items.TOTEM_OF_UNDYING)) {
         return false;
      } else {
         Map valuesByKey14 = resolve(itemStack);
         return check(valuesByKey14, EntityAttributes.MAX_HEALTH, 4.0)
            && check(valuesByKey14, EntityAttributes.ATTACK_DAMAGE, 3.0)
            && check(valuesByKey14, EntityAttributes.ARMOR_TOUGHNESS, 2.0)
            && check(valuesByKey14, EntityAttributes.ARMOR, 2.0);
      }
   }

   public static boolean check23(ItemStack itemStack) {
      if (!itemStack.isOf(Items.TOTEM_OF_UNDYING)) {
         return false;
      } else {
         Map valuesByKey15 = resolve(itemStack);
         return check(valuesByKey15, EntityAttributes.ATTACK_DAMAGE, 4.0)
            && check(valuesByKey15, EntityAttributes.MAX_HEALTH, 2.0)
            && check(valuesByKey15, EntityAttributes.MOVEMENT_SPEED, 0.1)
            && check(valuesByKey15, EntityAttributes.ATTACK_SPEED, 0.1)
            && check(valuesByKey15, EntityAttributes.ARMOR, -3.0);
      }
   }

   public static boolean check24(ItemStack itemStack) {
      if (!itemStack.isOf(Items.TOTEM_OF_UNDYING)) {
         return false;
      } else {
         Map valuesByKey16 = resolve(itemStack);
         return check(valuesByKey16, EntityAttributes.MAX_HEALTH, 2.0);
      }
   }

   public static String resolve2(ItemStack itemStack) {
      if (check17(itemStack)) {
         return "Талисман Демона";
      } else if (check18(itemStack)) {
         return "Талисман Карателя";
      } else if (check19(itemStack)) {
         return "Талисман Мрака";
      } else if (check20(itemStack)) {
         return "Талисман Ярости";
      } else if (check21(itemStack)) {
         return "Талисман Тирана";
      } else if (check22(itemStack)) {
         return "Талисман Крушителя";
      } else if (check23(itemStack)) {
         return "Талисман Раздора";
      } else {
         return check24(itemStack) ? "Талисман Сары" : "";
      }
   }

   public static boolean check25(ItemStack itemStack) {
      if (!itemStack.isOf(Items.SPLASH_POTION)) {
         return false;
      } else {
         Map valuesByKey17 = resolve(itemStack);
         boolean flag2 = check(valuesByKey17, EntityAttributes.ATTACK_DAMAGE, 12.0)
            && check(valuesByKey17, EntityAttributes.MOVEMENT_SPEED, 0.6)
            && check(valuesByKey17, EntityAttributes.ATTACK_SPEED, 0.1);
         return flag2 || check3(itemStack, ITEMS_5);
      }
   }

   public static boolean check26(ItemStack itemStack) {
      if (!itemStack.isOf(Items.SPLASH_POTION)) {
         return false;
      } else {
         Map valuesByKey18 = resolve(itemStack);
         boolean flag3 = check(valuesByKey18, EntityAttributes.ATTACK_DAMAGE, 5.0);
         return flag3 && check3(itemStack, ITEMS_2);
      }
   }

   public static boolean check27(ItemStack itemStack) {
      return !itemStack.isOf(Items.SPLASH_POTION) ? false : check3(itemStack, ITEMS);
   }

   public static boolean check28(ItemStack itemStack) {
      return !itemStack.isOf(Items.SPLASH_POTION) ? false : check3(itemStack, ITEMS_4) || check4(itemStack, "Святая вода");
   }

   public static boolean check29(ItemStack itemStack) {
      return !itemStack.isOf(Items.SPLASH_POTION) ? false : check3(itemStack, ITEMS_3);
   }

   public static boolean check30(ItemStack itemStack) {
      return !itemStack.isOf(Items.SPLASH_POTION) ? false : check3(itemStack, ITEMS_6);
   }

   public static boolean check31(ItemStack itemStack) {
      return !itemStack.isOf(Items.SPLASH_POTION) ? false : check3(itemStack, ITEMS_7);
   }

   public static boolean check32(ItemStack itemStack) {
      return itemStack.isOf(Items.SUGAR) && check4(itemStack, "Явная пыль") && check6(itemStack, "Каст: Световая вспышка");
   }

   public static boolean check33(ItemStack itemStack) {
      return itemStack.isOf(Items.ENDER_EYE) && check4(itemStack, "Дезориентация") && check6(itemStack, "Чем ближе цель");
   }

   public static boolean check34(ItemStack itemStack) {
      return itemStack.isOf(Items.NETHERITE_SCRAP) && check4(itemStack, "Трапка") && check6(itemStack, "Каст: Нерушимая клетка");
   }

   public static boolean check35(ItemStack itemStack) {
      return itemStack.isOf(Items.TRIPWIRE_HOOK) && check4(itemStack, "Отмычка к Сферам") && check6(itemStack, "Открыть хранилище с Сферам");
   }

   public static boolean check36(ItemStack itemStack) {
      return itemStack.isOf(Items.DRIED_KELP) && check4(itemStack, "Пласт") && check6(itemStack, "Каст: Нерушимая стена");
   }

   public static boolean check37(ItemStack itemStack) {
      return itemStack.isOf(Items.EXPERIENCE_BOTTLE) && (check7(itemStack, "Опыт с уровнем 15") || check7(itemStack, "15 ур"));
   }

   public static boolean check38(ItemStack itemStack) {
      return itemStack.isOf(Items.EXPERIENCE_BOTTLE) && (check7(itemStack, "Опыт с уровнем 30") || check7(itemStack, "30 ур"));
   }

   public static boolean check39(ItemStack itemStack) {
      return itemStack.isOf(Items.EXPERIENCE_BOTTLE) && (check7(itemStack, "Опыт с уровнем 50") || check7(itemStack, "50 ур"));
   }

   public static boolean check40(ItemStack itemStack) {
      return itemStack.isOf(Items.EXPERIENCE_BOTTLE) && (check7(itemStack, "Опыт с уровнем 45") || check7(itemStack, "45 ур"));
   }

   public static boolean check41(ItemStack itemStack) {
      return itemStack.isOf(Items.TNT) && check4(itemStack, "WHITE") && check6(itemStack, "в 10 раз сильнее");
   }

   public static boolean check42(ItemStack itemStack) {
      return itemStack.isOf(Items.TNT) && check4(itemStack, "BLACK") && check6(itemStack, "взорвать обсидиан");
   }

   public static boolean check43(ItemStack itemStack) {
      return itemStack.isOf(Items.CAMPFIRE) && check4(itemStack, "Случайный") && check6(itemStack, "Уровень лута: Случайный");
   }

   public static boolean check44(ItemStack itemStack) {
      return itemStack.isOf(Items.CAMPFIRE) && check4(itemStack, "Обычный") && check6(itemStack, "Уровень лута: Обычный");
   }

   public static boolean check45(ItemStack itemStack) {
      return itemStack.isOf(Items.CAMPFIRE) && check4(itemStack, "Богатый") && check6(itemStack, "Уровень лута: Богатый");
   }

   public static boolean check46(ItemStack itemStack) {
      return itemStack.isOf(Items.SOUL_CAMPFIRE) && check4(itemStack, "Легендарный") && check6(itemStack, "Уровень лута: Легендарный");
   }

   public static boolean check47(ItemStack itemStack) {
      return itemStack.isOf(Items.JIGSAW) && check4(itemStack, "Блок дамагер") && check6(itemStack, "Каст: Нанесение урона");
   }

   public static boolean check48(ItemStack itemStack) {
      return itemStack.isOf(Items.STRUCTURE_BLOCK) && check4(itemStack, "1x1") && check6(itemStack, "(1x1)");
   }

   public static boolean check49(ItemStack itemStack) {
      return itemStack.isOf(Items.BEACON) && check4(itemStack, "Маяк") && check6(itemStack, "раздающий Монеты");
   }

   public static boolean check50(ItemStack itemStack) {
      return itemStack.isOf(Items.SOUL_LANTERN) && check4(itemStack, "Проклятая душа") && check6(itemStack, "Обменяй души");
   }

   public static boolean check51(ItemStack itemStack) {
      return itemStack.isOf(Items.PAPER) && check4(itemStack, "Драконий скин") && check6(itemStack, "Драконий скин взамен");
   }

   public static boolean check52(ItemStack itemStack) {
      return itemStack.isOf(Items.FIRE_CHARGE) && check4(itemStack, "Огненный смерч") && check6(itemStack, "Каст: Огненная волна");
   }

   public static boolean check53(ItemStack itemStack) {
      return itemStack.isOf(Items.SNOWBALL) && check4(itemStack, "Снежок заморозка") && check6(itemStack, "Каст: Ледяная сфера");
   }

   public static boolean check54(ItemStack itemStack) {
      return itemStack.isOf(Items.PHANTOM_MEMBRANE) && check4(itemStack, "Божья аура") && check6(itemStack, "Каст: Божественная аура");
   }

   public static boolean check55(ItemStack itemStack) {
      return itemStack.isOf(Items.IRON_NUGGET) && check4(itemStack, "Серебро");
   }

   public static boolean check56(ItemStack itemStack) {
      return itemStack.isOf(Items.GOLDEN_PICKAXE) && check5(itemStack, "Божье касание") && check6(itemStack, "Может добыть спавнер");
   }

   public static boolean check57(ItemStack itemStack) {
      return itemStack.isOf(Items.GOLDEN_PICKAXE) && check4(itemStack, "Мощный удар") && check6(itemStack, "Может разрушить бедрок");
   }

   public static boolean check58(ItemStack itemStack) {
      return itemStack.isOf(Items.NETHERITE_PICKAXE) && check4(itemStack, "мега-бульдозер") && check6(itemStack, "Вскапывает территорию");
   }

   public static boolean check59(ItemStack itemStack) {
      return itemStack.isOf(Items.ELYTRA) && check4(itemStack, "Нерушимые элитры") && check6(itemStack, "Нерушимый предмет");
   }

   public record SpecialItemUtilsEntry(String name, List<StatusEffectInstance> effects) {
   }
}
