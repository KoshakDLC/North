package ru.metaculture.protection;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.component.type.ProfileComponent;
import net.minecraft.component.type.AttributeModifiersComponent.Entry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class HolyWorldItemParser {
   private static final String HOLYWORLD = "holyworld:";
   private static final double DOUBLE_VALUE = 1.0E-4;
   private static final Map<String, String> VALUES_BY_KEY = Map.of("sweeping", "sweeping_edge");
   private static final Map<String, List<String>> VALUES_BY_KEY_2 = Map.ofEntries(
      Map.entry("spawner-getter-enchant", List.of("спавнер", "добытьспавнер", "spawnergetter")),
      Map.entry("impenetrable-enchant-custom", List.of("непробиваем", "impenetrable")),
      Map.entry("drill-enchant-custom", List.of("бур", "бульдозер", "drill")),
      Map.entry("exp-enchant-custom", List.of("опытный", "опыт", "exp")),
      Map.entry("foundry-enchant-custom", List.of("автоплавка", "автоплав", "foundry")),
      Map.entry("internal-enchant-custom", List.of("internal", "встроен")),
      Map.entry("magnet-enchant-custom", List.of("магнит", "magnet")),
      Map.entry("critical-enchant-custom", List.of("крит", "critical")),
      Map.entry("destroyer-enchant-custom", List.of("разрушитель", "destroyer")),
      Map.entry("rich-enchant-custom", List.of("богач", "rich")),
      Map.entry("mob-farmer-enchant", List.of("фармер", "фермер", "mobfarmer"))
   );
   static final Map<String, Integer> VALUES_BY_KEY_3 = new HashMap<>();
   private static final List<HolyWorldItemParser.HolyWorldItemParserDisplayEntry> ITEMS = List.of(
      resolve14("Шлем инфинити", Items.NETHERITE_HELMET)
         .resolve2(
            "minecraft:blast_protection:5",
            "minecraft:projectile_protection:5",
            "minecraft:aqua_affinity:1",
            "minecraft:fire_protection:5",
            "minecraft:unbreaking:5",
            "minecraft:respiration:3",
            "minecraft:protection:5"
         )
         .resolve4(resolve16("minecraft:armor", 3.0), resolve16("minecraft:armor_toughness", 3.0), resolve16("minecraft:knockback_resistance", 0.1F))
         .setText2("Непробиваемый II")
         .resolve6(),
      resolve14("Нагрудник инфинити", Items.NETHERITE_CHESTPLATE)
         .resolve2(
            "minecraft:blast_protection:5",
            "minecraft:fire_protection:5",
            "minecraft:projectile_protection:5",
            "minecraft:unbreaking:5",
            "minecraft:protection:5"
         )
         .resolve4(resolve16("minecraft:armor", 8.0), resolve16("minecraft:armor_toughness", 3.0), resolve16("minecraft:knockback_resistance", 0.1F))
         .setText2("Непробиваемый II")
         .resolve6(),
      resolve14("Поножи инфинити", Items.NETHERITE_LEGGINGS)
         .resolve2(
            "minecraft:blast_protection:5",
            "minecraft:fire_protection:5",
            "minecraft:projectile_protection:5",
            "minecraft:unbreaking:5",
            "minecraft:protection:5"
         )
         .resolve4(resolve16("minecraft:armor", 6.0), resolve16("minecraft:armor_toughness", 3.0), resolve16("minecraft:knockback_resistance", 0.1F))
         .setText2("Непробиваемый II")
         .resolve6(),
      resolve14("Ботинки инфинити", Items.NETHERITE_BOOTS)
         .resolve2(
            "minecraft:blast_protection:5",
            "minecraft:projectile_protection:5",
            "minecraft:feather_falling:4",
            "minecraft:depth_strider:3",
            "minecraft:fire_protection:5",
            "minecraft:unbreaking:5",
            "minecraft:protection:5",
            "minecraft:soul_speed:3"
         )
         .resolve4(resolve16("minecraft:armor", 3.0), resolve16("minecraft:armor_toughness", 3.0), resolve16("minecraft:knockback_resistance", 0.1F))
         .setText2("Непробиваемый II")
         .resolve6(),
      resolve14("Талисман инфинити", Items.TOTEM_OF_UNDYING)
         .resolve2("minecraft:unbreaking:1")
         .resolve4(resolve16("minecraft:armor", 2.0))
         .resolve("• Макс. здоровье II", "• Броня II", "• Урон II", "• Скорость II")
         .resolve6(),
      resolve14("Кирка этернити", Items.NETHERITE_PICKAXE)
         .resolve2("minecraft:efficiency:10", "minecraft:fortune:5", "minecraft:unbreaking:5", "minecraft:mending:1")
         .resolve4(resolve16("minecraft:attack_damage", 5.0), resolve16("minecraft:attack_speed", -2.8F))
         .resolve("Магнетизм I", "Неразрушимость I", "Автоплавка", "Опытный III", "Бур II")
         .resolve6(),
      resolve14("Шлем этернити", Items.NETHERITE_HELMET)
         .resolve2(
            "minecraft:blast_protection:5",
            "minecraft:projectile_protection:5",
            "minecraft:aqua_affinity:1",
            "minecraft:fire_protection:5",
            "minecraft:unbreaking:5",
            "minecraft:respiration:3",
            "minecraft:protection:5"
         )
         .resolve4(resolve16("minecraft:armor", 3.0), resolve16("minecraft:armor_toughness", 3.0), resolve16("minecraft:knockback_resistance", 0.1F))
         .setText2("Непробиваемый I")
         .resolve6(),
      resolve14("Нагрудник этернити", Items.NETHERITE_CHESTPLATE)
         .resolve2(
            "minecraft:blast_protection:5",
            "minecraft:fire_protection:5",
            "minecraft:projectile_protection:5",
            "minecraft:unbreaking:5",
            "minecraft:protection:5"
         )
         .resolve4(resolve16("minecraft:armor", 8.0), resolve16("minecraft:armor_toughness", 3.0), resolve16("minecraft:knockback_resistance", 0.1F))
         .setText2("Непробиваемый I")
         .resolve6(),
      resolve14("Штаны этернити", Items.NETHERITE_LEGGINGS)
         .resolve2(
            "minecraft:blast_protection:5",
            "minecraft:fire_protection:5",
            "minecraft:projectile_protection:5",
            "minecraft:unbreaking:5",
            "minecraft:protection:5"
         )
         .resolve4(resolve16("minecraft:armor", 6.0), resolve16("minecraft:armor_toughness", 3.0), resolve16("minecraft:knockback_resistance", 0.1F))
         .setText2("Непробиваемый I")
         .resolve6(),
      resolve14("Ботинки этернити", Items.NETHERITE_BOOTS)
         .resolve2(
            "minecraft:fire_protection:5",
            "minecraft:soul_speed:3",
            "minecraft:blast_protection:5",
            "minecraft:unbreaking:5",
            "minecraft:protection:5",
            "minecraft:projectile_protection:5",
            "minecraft:depth_strider:3",
            "minecraft:feather_falling:4"
         )
         .resolve4(resolve16("minecraft:armor", 3.0), resolve16("minecraft:armor_toughness", 3.0), resolve16("minecraft:knockback_resistance", 0.1F))
         .setText2("Непробиваемый I")
         .resolve6(),
      resolve14("Меч этернити", Items.NETHERITE_SWORD)
         .resolve2(
            "minecraft:smite:7",
            "minecraft:bane_of_arthropods:7",
            "minecraft:fire_aspect:2",
            "minecraft:mending:1",
            "minecraft:sweeping_edge:3",
            "minecraft:unbreaking:5",
            "minecraft:looting:5",
            "minecraft:sharpness:7"
         )
         .resolve4(resolve16("minecraft:attack_damage", 7.0), resolve16("minecraft:attack_speed", -2.4F))
         .resolve("Разрушитель II", "Богач I", "Критический II")
         .resolve6(),
      resolve14("Талисман этернити", Items.TOTEM_OF_UNDYING)
         .resolve2("minecraft:unbreaking:1")
         .resolve4(resolve16("minecraft:armor", 2.0))
         .resolve("• Скорость II", "• Урон II", "• Броня II")
         .resolve6(),
      resolve14("Сфера этернити", Items.PLAYER_HEAD)
         .resolve4(resolve16("minecraft:armor", 2.0))
         .resolve("• Броня II", "• Скорость II", "• Урон II")
         .setText2(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGM5MzY1NjQyYzZlZGRjZmVkZjViNWUxNGUyYmM3MTI1N2Q5ZTRhMzM2M2QxMjNjNmYzM2M1NWNhZmJmNmQifX19"
         )
         .resolve6(),
      resolve14("Кирка стингер", Items.NETHERITE_PICKAXE)
         .resolve2("minecraft:efficiency:8", "minecraft:unbreaking:4", "minecraft:mending:1", "minecraft:fortune:4")
         .resolve4(resolve16("minecraft:attack_damage", 5.0), resolve16("minecraft:attack_speed", -2.8F))
         .resolve("Неразрушимость I", "Автоплавка", "Опытный III", "Бур I")
         .resolve6(),
      resolve14("Шлем стингер", Items.NETHERITE_HELMET)
         .resolve2(
            "minecraft:fire_protection:4",
            "minecraft:blast_protection:4",
            "minecraft:aqua_affinity:1",
            "minecraft:unbreaking:4",
            "minecraft:protection:5",
            "minecraft:projectile_protection:4",
            "minecraft:respiration:3"
         )
         .resolve4(resolve16("minecraft:armor", 3.0), resolve16("minecraft:armor_toughness", 3.0), resolve16("minecraft:knockback_resistance", 0.1F))
         .resolve6(),
      resolve14("Нагрудник стингер", Items.NETHERITE_CHESTPLATE)
         .resolve2(
            "minecraft:blast_protection:4",
            "minecraft:fire_protection:4",
            "minecraft:unbreaking:4",
            "minecraft:protection:5",
            "minecraft:projectile_protection:4"
         )
         .resolve4(resolve16("minecraft:armor", 8.0), resolve16("minecraft:armor_toughness", 3.0), resolve16("minecraft:knockback_resistance", 0.1F))
         .setText2("Непробиваемый I")
         .resolve6(),
      resolve14("Штаны стингер", Items.NETHERITE_LEGGINGS)
         .resolve2(
            "minecraft:blast_protection:4",
            "minecraft:fire_protection:4",
            "minecraft:unbreaking:4",
            "minecraft:protection:4",
            "minecraft:projectile_protection:4"
         )
         .resolve4(resolve16("minecraft:armor", 6.0), resolve16("minecraft:armor_toughness", 3.0), resolve16("minecraft:knockback_resistance", 0.1F))
         .setText2("Непробиваемый I")
         .resolve6(),
      resolve14("Ботинки стингер", Items.NETHERITE_BOOTS)
         .resolve2(
            "minecraft:fire_protection:4",
            "minecraft:soul_speed:3",
            "minecraft:blast_protection:4",
            "minecraft:unbreaking:4",
            "minecraft:protection:4",
            "minecraft:projectile_protection:4",
            "minecraft:depth_strider:3",
            "minecraft:feather_falling:4"
         )
         .resolve4(resolve16("minecraft:armor", 3.0), resolve16("minecraft:armor_toughness", 3.0), resolve16("minecraft:knockback_resistance", 0.1F))
         .resolve6(),
      resolve14("Меч стингер", Items.NETHERITE_SWORD)
         .resolve2(
            "minecraft:smite:7",
            "minecraft:bane_of_arthropods:7",
            "minecraft:fire_aspect:2",
            "minecraft:mending:1",
            "minecraft:sweeping_edge:3",
            "minecraft:unbreaking:4",
            "minecraft:looting:5",
            "minecraft:sharpness:6"
         )
         .resolve4(resolve16("minecraft:attack_damage", 7.0), resolve16("minecraft:attack_speed", -2.4F))
         .resolve("Богач I", "Критический II")
         .resolve6(),
      resolve14("Талисман стингер", Items.TOTEM_OF_UNDYING)
         .resolve2("minecraft:unbreaking:1")
         .resolve4(resolve16("minecraft:armor", 2.0))
         .resolve("• Скорость I", "• Броня II", "• Урон II")
         .resolve6(),
      resolve14("Сфера стингер", Items.PLAYER_HEAD)
         .resolve4(resolve16("minecraft:armor", 2.0))
         .resolve("• Броня II", "• Скорость I", "• Урон II")
         .setText2(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGM5MzY1NjQyYzZlZGRjZmVkZjViNWUxNGUyYmM3MTI1N2Q5ZTRhMzM2M2QxMjNjNmYzM2M1NWNhZmJmNmQifX19"
         )
         .resolve6(),
      resolve14("Сфера Цербера", Items.PLAYER_HEAD)
         .resolve4(resolve16("minecraft:waypoint_transmit_range", -1.0))
         .resolve("Проклятие утраты", "• Спешка I", "• Урон V")
         .setText2(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjA5NWE3ZmQ5MGRhYTFiYmU3MDY5MDg5NzQwZTA1ZDBiZmM2NjI5NmVlM2M0MGVlNzFhNGUwYTY2MTZiMmJiYyJ9fX0="
         )
         .resolve6(),
      resolve14("Сфера Флеша", Items.PLAYER_HEAD)
         .resolve4(resolve16("minecraft:armor", 1.0))
         .resolve("Проклятие утраты", "• Броня I", "• Скорость III")
         .setText2(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzc0MDBlYTE5ZGJkODRmNzVjMzlhZDY4MjNhYzRlZjc4NmYzOWY0OGZjNmY4NDYwMjM2NmFjMjliODM3NDIyIn19fQ=="
         )
         .resolve6(),
      resolve14("Легендарная сфера", Items.PLAYER_HEAD)
         .resolve4(resolve16("minecraft:waypoint_transmit_range", -1.0))
         .setText2("• Урон III")
         .setText2(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGM5MzY1NjQyYzZlZGRjZmVkZjViNWUxNGUyYmM3MTI1N2Q5ZTRhMzM2M2QxMjNjNmYzM2M1NWNhZmJmNmQifX19"
         )
         .resolve6(),
      resolve14("Мифическая сфера", Items.PLAYER_HEAD)
         .resolve4(resolve16("minecraft:armor", 2.0))
         .resolve("• Броня II", "• Урон III")
         .setText2(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmFmZjJlYjQ5OGU1YzZhMDQ0ODRmMGM5Zjc4NWI0NDg0NzlhYjIxM2RmOTVlYzkxMTc2YTMwOGExMmFkZDcwIn19fQ=="
         )
         .resolve6(),
      resolve14("Мифическая сфера", Items.PLAYER_HEAD)
         .resolve4(resolve16("minecraft:armor", 3.0))
         .resolve("• Скорость II", "• Броня III")
         .setText2(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmFmZjJlYjQ5OGU1YzZhMDQ0ODRmMGM5Zjc4NWI0NDg0NzlhYjIxM2RmOTVlYzkxMTc2YTMwOGExMmFkZDcwIn19fQ=="
         )
         .resolve6(),
      resolve14("Золотой Спавнер", Items.SPAWNER)
         .resolve(
            "Особенности:",
            "виртуально фармит мобов",
            ".  без спавна сущностей;",
            "лут и опыт копятся",
            ".  во внутреннем хранилище;",
            "вставка яйца может",
            ".  сломать спавнер.",
            "Шанс уничтожения: 50.6%"
         )
         .resolve6(),
      resolve14("Взрывчатое вещество", Items.CLAY)
         .resolve("Особенности:", "используется только для крафта", ".   взрывных предметов;", "можно перекрафтить в 9 пороха.")
         .resolve6(),
      resolve14("100", Items.EXPERIENCE_BOTTLE).resolve("В пузырьке 30971 опыта (100 ур.)", "Киньте пузырек, чтобы получить опыт").resolve6(),
      resolve14("Загадочный спавнер", Items.SPAWNER)
         .resolve(
            "Потенциальное содержание:",
            "• Брутальный пиглин — 25.0%",
            "• Ведьма — 7.0%",
            "• Блейз — 20.0%",
            "• Зомби — 18.0%",
            "• Скелет — 30.0%",
            "▍ Может вмещать в себе случайного моба,",
            "▍ с шансом из списка, указанного выше."
         )
         .resolve6(),
      resolve14("Загадочное яйцо призыва", Items.WITCH_SPAWN_EGG)
         .resolve(
            "Потенциальное содержание:",
            "• Брутальный пиглин — 25.0%",
            "• Ведьма — 7.0%",
            "• Блейз — 20.0%",
            "• Зомби — 18.0%",
            "• Скелет — 30.0%",
            "▍ Может вмещать в себе случайного моба,",
            "▍ с шансом из списка, указанного выше."
         )
         .resolve6(),
      resolve14("Загадочное яйцо призыва", Items.CREEPER_SPAWN_EGG)
         .resolve(
            "Потенциальное содержание:",
            "• Брутальный пиглин — 33.0%",
            "• Крипер — 2.0%",
            "• Блейз — 17.5%",
            "• Зомби — 17.5%",
            "• Скелет — 30.0%",
            "▍ Может вмещать в себе случайного моба,",
            "▍ с шансом из списка, указанного выше."
         )
         .resolve6(),
      resolve14("Загадочное яйцо призыва", Items.PIGLIN_BRUTE_SPAWN_EGG)
         .resolve(
            "Потенциальное содержание:",
            "• Брутальный пиглин — 50.0%",
            "• Ведьма — 4.0%",
            "• Мини-зомби — 20.0%",
            "• Крипер — 1.0%",
            "• Блейз — 25.0%",
            "▍ Может вмещать в себе случайного моба,",
            "▍ с шансом из списка, указанного выше."
         )
         .resolve6(),
      resolve14("Трапка", Items.POPPED_CHORUS_FRUIT).resolve6(),
      resolve14("Ком снега", Items.SNOWBALL, "Снежок заморозки", "Снежок заморозка").resolve6(),
      resolve14("Стан", Items.NETHER_STAR).resolve6(),
      resolve14("Взрывная трапка", Items.PRISMARINE_SHARD, "Взрывная").resolve6(),
      resolve14("С4", Items.TNT).resolve("Особенности:", "разрушает блок незеритового привата;", "взрывает блоки обсидиана.").resolve6(),
      resolve14("Справедливость", Items.POTION)
         .resolve(
            "Особенности:",
            "когда предмет в инвентаре, вы получаете",
            ".   защиту от различных дебафов слепота",
            ".   прыгучесть, отравление, иссушение",
            ".   медлительность и слабость."
         )
         .resolve6(),
      resolve14("Броневая элитра", Items.ELYTRA)
         .resolve4(resolve16("minecraft:armor", 8.0))
         .resolve("Особенности:", "имеет свойства алмазного нагрудника;", "позволяет летать как обычная элитра;", "возможно накладывать зачарования.")
         .resolve6(),
      resolve14("Арбалет этернити", Items.CROSSBOW)
         .resolve2("minecraft:piercing:5", "minecraft:multishot:1", "minecraft:unbreaking:3", "minecraft:quick_charge:3")
         .setText2("Оглушение II")
         .resolve6(),
      resolve14("Сфера ᴀʀᴍᴏʀᴛᴀʟɪᴛʏ", Items.PLAYER_HEAD, "Сфера armortlity", "Сфера armortality")
         .resolve4(resolve16("minecraft:armor", 2.0))
         .resolve("• Броня II", "• Макс. здоровье II", "• Урон II")
         .setText2(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWE2MmI5ZGU2YTI2Yjg2ODY5Y2EyMmVhNDBmMWJkZTgwYTA0MzBhNTQ1NDdiZWNjZThmZGE4NzA3Nzc3MjU4ZiJ9fX0="
         )
         .resolve6(),
      resolve14("Сфера immortality", Items.PLAYER_HEAD)
         .resolve4(resolve16("minecraft:waypoint_transmit_range", -1.0))
         .resolve("• Скорость II", "• Урон III")
         .setText2(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODNlZDRjZTIzOTMzZTY2ZTA0ZGYxNjA3MDY0NGY3NTk5ZWViNTUzMDdmN2VhZmU4ZDkyZjQwZmIzNTIwODYzYyJ9fX0="
         )
         .resolve6(),
      resolve14("15", Items.EXPERIENCE_BOTTLE).resolve("В пузырьке 315 опыта (15 ур.)", "Киньте пузырек, чтобы получить опыт").resolve6(),
      resolve14("50", Items.EXPERIENCE_BOTTLE).resolve("В пузырьке 5345 опыта (50 ур.)", "Киньте пузырек, чтобы получить опыт").resolve6(),
      resolve14("Особый компас", Items.COMPASS)
         .resolve2("minecraft:luck_of_the_sea:1")
         .resolve("Особенности:", "- ведёт к ближайшему или случайному", "- можно использовать раз в 8 часов.")
         .resolve6(),
      resolve14("Тнт-Пушка", Items.DISPENSER)
         .resolve2("minecraft:soul_speed:10")
         .resolve(
            "Особенности:",
            "- запускает летящий динамит",
            ".   со скоростью до 5 блоков за секунду;",
            "- при запуске сохраняет свойства",
            ".   особых динамитов и пиротехники;",
            "- можно сломать в чужом привате.",
            "● Данный товар можно"
         )
         .resolve6(),
      resolve14("Меч инфинити", Items.NETHERITE_SWORD)
         .resolve2(
            "minecraft:sharpness:8",
            "minecraft:unbreaking:5",
            "minecraft:mending:1",
            "minecraft:fire_aspect:2",
            "minecraft:bane_of_arthropods:7",
            "minecraft:sweeping_edge:3",
            "minecraft:smite:7",
            "minecraft:looting:5"
         )
         .resolve4(resolve16("minecraft:attack_damage", 7.0), resolve16("minecraft:attack_speed", -2.4F))
         .resolve("Богач VI", "Разрушитель II", "Критический II")
         .resolve6(),
      resolve14("Меч Цербера ", Items.NETHERITE_SWORD)
         .resolve2(
            "minecraft:sharpness:9",
            "minecraft:unbreaking:5",
            "minecraft:mending:1",
            "minecraft:fire_aspect:2",
            "minecraft:bane_of_arthropods:7",
            "minecraft:sweeping_edge:3",
            "minecraft:smite:7",
            "minecraft:looting:5"
         )
         .resolve4(resolve16("minecraft:attack_damage", 7.0), resolve16("minecraft:attack_speed", -2.4F))
         .resolve("Богач VI", "Разрушитель III", "Критический II", "● Данный товар можно")
         .resolve6(),
      resolve14("Нерушимые элитры", Items.ELYTRA).resolve6(),
      resolve14("Меч Выгодный фарм", Items.NETHERITE_SWORD)
         .resolve4(resolve16("minecraft:attack_damage", 7.0), resolve16("minecraft:attack_speed", -2.4F))
         .resolve("Фармер II", "● Данный товар можно")
         .resolve6(),
      resolve14("Рюкзак инфинити", Items.LIME_SHULKER_BOX, "- Рюкзак Iɴғɪɴɪᴛʏ -")
         .resolve("Особенности:", "- нельзя поставить на землю;", "- вместимость 36 слотов;", "● Данный товар можно")
         .resolve6(),
      resolve14("Рюкзак 1 уровень", Items.PINK_SHULKER_BOX, "Рюкзак I уровень", "Рюкзак (I уровень)")
         .resolve("Особенности:", "- нельзя поставить на землю;", "- вместимость 9 слотов;")
         .resolve6(),
      resolve14("Рюкзак 2 уровень", Items.LIGHT_BLUE_SHULKER_BOX, "Рюкзак II уровень", "Рюкзак (II уровень)")
         .resolve("Особенности:", "- нельзя поставить на землю;", "- вместимость 15 слотов;")
         .resolve6(),
      resolve14("Рюкзак 3 уровень", Items.RED_SHULKER_BOX, "Рюкзак III уровень", "Рюкзак (III уровень)")
         .resolve("Особенности:", "- нельзя поставить на землю;", "- вместимость 21 слот;", "● Данный товар можно")
         .resolve6(),
      resolve14("Рюкзак 4 уровень", Items.MAGENTA_SHULKER_BOX, "Рюкзак IV уровень", "Рюкзак (IV уровень)")
         .resolve("Особенности:", "- нельзя поставить на землю;", "- вместимость 27 слотов;", "● Данный товар можно")
         .resolve6(),
      resolve14("Руна Бессмертие", Items.ORANGE_DYE)
         .resolve2("minecraft:luck_of_the_sea:1")
         .resolve(
            "Эффект руны",
            "Особенности:",
            "после активации тотема с этим эффектом,",
            ".   Вы получите неуязвимость к урону",
            ".   продолжительностью 3 секунды;",
            "возможность наложить данный эффект",
            ".   на тотем через наковальню;"
         )
         .resolve6(),
      resolve14("Зелье исцеление", Items.POTION).resolve6(),
      resolve14("Зелье черепашьей мощи", Items.POTION).resolve6(),
      resolve14("Зелье черепашьей мощи", Items.POTION).resolve6(),
      resolve14("Эндер-жемчуг", Items.ENDER_PEARL).resolve6(),
      resolve14("Динамит а", Items.TNT).resolve("Особенности:", "имеет в 3 раза больший радиус взрыва.").resolve6(),
      resolve14("Динамит б", Items.TNT).resolve("Особенности:", "имеет в 10 раз больший радиус взрыва.").resolve6(),
      resolve14("Динамит б2", Items.TNT)
         .resolve(
            "Особенности:", "взрывает практически все блоки", ".   в радиусе 12 блоков;", "не работает на всех стандартных", ".   заприваченных территориях;"
         )
         .resolve6(),
      resolve14("С4 взрывчатка", Items.TNT).resolve("Особенности:", "разрушает блок незеритового привата;", "взрывает блоки обсидиана.").resolve6()
   );
   private static final Map<String, HolyWorldItemParser.HolyWorldItemParserDisplayEntry> VALUES_BY_KEY_4 = resolve12();

   public static List<HolyWorldItemParser.HolyWorldItemParserDisplayEntry> getITEMS() {
      return ITEMS;
   }

   public static boolean check(String string) {
      return string != null && string.startsWith("holyworld:");
   }

   public static boolean check2(String string) {
      return resolve(string) != null;
   }

   public static HolyWorldItemParser.HolyWorldItemParserDisplayEntry resolve(String string) {
      if (string != null && !string.isBlank()) {
         HolyWorldItemParser.HolyWorldItemParserDisplayEntry holyWorldItemParserDisplayEntry = VALUES_BY_KEY_4.get(string);
         return holyWorldItemParserDisplayEntry != null ? holyWorldItemParserDisplayEntry : VALUES_BY_KEY_4.get(resolve19(resolve18(string)));
      } else {
         return null;
      }
   }

   public static String resolve2(String string) {
      HolyWorldItemParser.HolyWorldItemParserDisplayEntry holyWorldItemParserDisplayEntry2 = resolve(string);
      return holyWorldItemParserDisplayEntry2 == null ? string : holyWorldItemParserDisplayEntry2.label();
   }

   public static String resolve3(String string) {
      if (string != null && !string.isBlank()) {
         String[] texts = string.split(":");
         return texts.length >= 2 ? resolve9(texts[0] + ":" + texts[1]) : resolve9(string);
      } else {
         return "";
      }
   }

   public static ItemStack resolve4(String string) {
      HolyWorldItemParser.HolyWorldItemParserDisplayEntry holyWorldItemParserDisplayEntry3 = resolve(string);
      if (holyWorldItemParserDisplayEntry3 == null) {
         return ItemStack.EMPTY;
      } else {
         return holyWorldItemParserDisplayEntry3.item() == Items.PLAYER_HEAD && holyWorldItemParserDisplayEntry3.texture() != null && !holyWorldItemParserDisplayEntry3.texture().isBlank()
            ? resolve17(holyWorldItemParserDisplayEntry3.texture(), holyWorldItemParserDisplayEntry3.label())
            : new ItemStack(holyWorldItemParserDisplayEntry3.item());
      }
   }

   public static boolean check3(ItemStack itemStack) {
      return check8("Трапка", itemStack);
   }

   public static boolean check4(ItemStack itemStack) {
      return check8("Ком снега", itemStack);
   }

   public static boolean check5(ItemStack itemStack) {
      return check8("Стан", itemStack);
   }

   public static boolean check6(ItemStack itemStack) {
      return check8("Взрывная трапка", itemStack);
   }

   public static boolean check7(String string, ItemStack itemStack, String string2) {
      HolyWorldItemParser.HolyWorldItemParserDisplayEntry holyWorldItemParserDisplayEntry4 = resolve(string);
      if (holyWorldItemParserDisplayEntry4 != null && itemStack != null && !itemStack.isEmpty() && itemStack.isOf(holyWorldItemParserDisplayEntry4.item())) {
         String text = resolve19(string2);
         if (text.isEmpty()) {
            text = resolve19(itemStack.getName().getString());
         }

         String text2 = resolve19(resolve22(itemStack));
         return check12(holyWorldItemParserDisplayEntry4, itemStack, text, text2);
      } else {
         return false;
      }
   }

   private static boolean check8(String string, ItemStack itemStack) {
      HolyWorldItemParser.HolyWorldItemParserDisplayEntry holyWorldItemParserDisplayEntry5 = resolve(string);
      return holyWorldItemParserDisplayEntry5 != null && check9(holyWorldItemParserDisplayEntry5, itemStack, resolve5(itemStack), resolve6(itemStack));
   }

   public static boolean check9(HolyWorldItemParser.HolyWorldItemParserDisplayEntry holyWorldItemParserDisplayEntry6, ItemStack itemStack, String string, String string2) {
      return check10(holyWorldItemParserDisplayEntry6, itemStack, string, string2, true, true, true, true);
   }

   public static boolean check10(
      HolyWorldItemParser.HolyWorldItemParserDisplayEntry holyWorldItemParserDisplayEntry7, ItemStack itemStack, String string, String string2, boolean bl, boolean bl2, boolean bl3, boolean bl4
   ) {
      if (holyWorldItemParserDisplayEntry7 != null && itemStack != null && !itemStack.isEmpty() && itemStack.isOf(holyWorldItemParserDisplayEntry7.item())) {
         String text3 = string == null ? "" : string;
         if (text3.isEmpty()) {
            text3 = resolve19(itemStack.getName().getString());
         }

         String text4 = string2 == null ? "" : string2;
         if (text4.isEmpty()) {
            text4 = text3;
         }

         return check13(holyWorldItemParserDisplayEntry7, itemStack, text3, text4, bl, bl2, bl3, bl4);
      } else {
         return false;
      }
   }

   public static boolean check11(HolyWorldItemParser.HolyWorldItemParserDisplayEntry holyWorldItemParserDisplayEntry8, ItemStack itemStack, String string, String string2, Set<String> set) {
      if (holyWorldItemParserDisplayEntry8 != null && itemStack != null && !itemStack.isEmpty() && itemStack.isOf(holyWorldItemParserDisplayEntry8.item())) {
         String text5 = string == null ? "" : string;
         if (text5.isEmpty()) {
            text5 = resolve19(itemStack.getName().getString());
         }

         String text6 = string2 == null ? "" : string2;
         if (text6.isEmpty()) {
            text6 = text5;
         }

         return check14(holyWorldItemParserDisplayEntry8, itemStack, text5, text6, set);
      } else {
         return false;
      }
   }

   public static String resolve5(ItemStack itemStack) {
      if (itemStack != null && !itemStack.isEmpty()) {
         StringBuilder stringBuilder = new StringBuilder();
         stringBuilder.append(itemStack.getName().getString()).append(' ');
         LoreComponent loreComponent = (LoreComponent)itemStack.get(DataComponentTypes.LORE);
         if (loreComponent != null) {
            for (Text text7 : loreComponent.lines()) {
               stringBuilder.append(text7.getString()).append(' ');
            }
         }

         return resolve19(stringBuilder.toString());
      } else {
         return "";
      }
   }

   public static String resolve6(ItemStack itemStack) {
      return itemStack != null && !itemStack.isEmpty() ? resolve19(resolve22(itemStack)) : "";
   }

   public static String resolve7(String string) {
      return string == null ? "" : resolve21(resolve20(string)).trim();
   }

   private static boolean check12(HolyWorldItemParser.HolyWorldItemParserDisplayEntry holyWorldItemParserDisplayEntry9, ItemStack itemStack, String string, String string2) {
      if (check15(holyWorldItemParserDisplayEntry9, string)) {
         return false;
      } else {
         if (string2.isEmpty()) {
            string2 = string;
         }

         boolean flag = check31(string, holyWorldItemParserDisplayEntry9.aliases());
         if (!flag) {
            return false;
         } else {
            return !holyWorldItemParserDisplayEntry9.hasRequirements() ? true : check13(holyWorldItemParserDisplayEntry9, itemStack, string, string2, true, true, true, true);
         }
      }
   }

   private static boolean check13(
      HolyWorldItemParser.HolyWorldItemParserDisplayEntry holyWorldItemParserDisplayEntry10, ItemStack itemStack, String string, String string2, boolean bl, boolean bl2, boolean bl3, boolean bl4
   ) {
      if (check15(holyWorldItemParserDisplayEntry10, string)) {
         return false;
      } else {
         if (string2.isEmpty()) {
            string2 = string;
         }

         boolean flag2 = check31(string, holyWorldItemParserDisplayEntry10.aliases());
         if (!flag2) {
            return false;
         } else {
            return !holyWorldItemParserDisplayEntry10.hasRequirements()
               ? true
               : (!bl || check16(holyWorldItemParserDisplayEntry10, string2))
                  && (!bl2 || check17(holyWorldItemParserDisplayEntry10, itemStack, string2))
                  && (!bl3 || check18(holyWorldItemParserDisplayEntry10, itemStack, string2))
                  && (!bl4 || check20(holyWorldItemParserDisplayEntry10, itemStack, string2));
         }
      }
   }

   private static boolean check14(HolyWorldItemParser.HolyWorldItemParserDisplayEntry holyWorldItemParserDisplayEntry11, ItemStack itemStack, String string, String string2, Set<String> set) {
      if (check15(holyWorldItemParserDisplayEntry11, string)) {
         return false;
      } else {
         if (string2.isEmpty()) {
            string2 = string;
         }

         boolean flag3 = check31(string, holyWorldItemParserDisplayEntry11.aliases());
         if (!flag3) {
            return false;
         } else {
            return !holyWorldItemParserDisplayEntry11.hasRequirements()
               ? true
               : check16(holyWorldItemParserDisplayEntry11, string2)
                  && check17(holyWorldItemParserDisplayEntry11, itemStack, string2)
                  && check19(holyWorldItemParserDisplayEntry11, itemStack, string2, set)
                  && check20(holyWorldItemParserDisplayEntry11, itemStack, string2);
         }
      }
   }

   private static boolean check15(HolyWorldItemParser.HolyWorldItemParserDisplayEntry holyWorldItemParserDisplayEntry12, String string) {
      String text8 = resolve19(holyWorldItemParserDisplayEntry12.label());
      return text8.equals("элитры") && string.contains("броневаяэлитра")
         || text8.equals("динамитb") && string.contains("динамитb2")
         || text8.equals("зельечерепашьеймощи")
            && (string.contains("зельечерепашьеймощиii") || string.contains("черепашьямощьii") || string.contains("черепашьямощь2"));
   }

   private static boolean check16(HolyWorldItemParser.HolyWorldItemParserDisplayEntry holyWorldItemParserDisplayEntry13, String string) {
      for (String text9 : holyWorldItemParserDisplayEntry13.lore()) {
         String text10 = resolve19(text9);
         if (!text10.isEmpty() && !string.contains(text10)) {
            return false;
         }
      }

      return true;
   }

   private static boolean check17(HolyWorldItemParser.HolyWorldItemParserDisplayEntry holyWorldItemParserDisplayEntry14, ItemStack itemStack, String string) {
      for (HolyWorldItemParser.HolyWorldItemParserEntry holyWorldItemParserEntry : holyWorldItemParserDisplayEntry14.attributes()) {
         if (!check26(itemStack, holyWorldItemParserEntry) && !check25(holyWorldItemParserEntry, string)) {
            return false;
         }
      }

      return true;
   }

   private static boolean check18(HolyWorldItemParser.HolyWorldItemParserDisplayEntry holyWorldItemParserDisplayEntry15, ItemStack itemStack, String string) {
      for (String text11 : holyWorldItemParserDisplayEntry15.enchantments()) {
         HolyWorldItemParser.HolyWorldItemParserEntry3 holyWorldItemParserEntry3 = resolve10(text11);
         if (holyWorldItemParserEntry3 != null) {
            if (check30(holyWorldItemParserEntry3.id())) {
               if (!check29(itemStack, holyWorldItemParserEntry3.id(), holyWorldItemParserEntry3.level()) && !check24(string, holyWorldItemParserEntry3.raw())) {
                  return false;
               }
            } else {
               boolean flag4 = check24(string, holyWorldItemParserEntry3.raw()) || check23(string, holyWorldItemParserEntry3);
               if (holyWorldItemParserDisplayEntry15.strictCheck() && !flag4) {
                  return false;
               }
            }
         }
      }

      return true;
   }

   private static boolean check19(HolyWorldItemParser.HolyWorldItemParserDisplayEntry holyWorldItemParserDisplayEntry16, ItemStack itemStack, String string, Set<String> set) {
      for (String text12 : holyWorldItemParserDisplayEntry16.enchantments()) {
         if (set == null || set.contains(resolve3(text12))) {
            HolyWorldItemParser.HolyWorldItemParserEntry3 holyWorldItemParserEntry32 = resolve10(text12);
            if (holyWorldItemParserEntry32 != null) {
               if (check30(holyWorldItemParserEntry32.id())) {
                  if (!check29(itemStack, holyWorldItemParserEntry32.id(), holyWorldItemParserEntry32.level()) && !check24(string, holyWorldItemParserEntry32.raw())) {
                     return false;
                  }
               } else {
                  boolean flag5 = check24(string, holyWorldItemParserEntry32.raw()) || check23(string, holyWorldItemParserEntry32);
                  if (holyWorldItemParserDisplayEntry16.strictCheck() && !flag5) {
                     return false;
                  }
               }
            }
         }
      }

      return true;
   }

   private static boolean check20(HolyWorldItemParser.HolyWorldItemParserDisplayEntry holyWorldItemParserDisplayEntry17, ItemStack itemStack, String string) {
      if (holyWorldItemParserDisplayEntry17.effects().isEmpty()) {
         return true;
      } else {
         boolean flag6 = string.contains("hms")
            || itemStack.get(DataComponentTypes.ATTRIBUTE_MODIFIERS) != null
            || check31(string, List.of("урон", "брон", "скор", "здоров", "damage", "armor", "speed", "health"));
         if (!flag6) {
            return true;
         } else {
            for (String text13 : holyWorldItemParserDisplayEntry17.effects()) {
               if (!check21(text13, itemStack, string)) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   private static boolean check21(String string, ItemStack itemStack, String string2) {
      String text14 = resolve19(string);
      if (!text14.isEmpty() && string2.contains(text14)) {
         return true;
      } else {
         HolyWorldItemParser.HolyWorldItemParserEntry2 holyWorldItemParserEntry2 = resolve11(string);
         if (holyWorldItemParserEntry2 == null) {
            return true;
         } else {
            String text15 = holyWorldItemParserEntry2.type();

            RegistryEntry registryEntry2 = switch (text15) {
               case "damage" -> EntityAttributes.ATTACK_DAMAGE;
               case "armor" -> EntityAttributes.ARMOR;
               case "speed" -> EntityAttributes.MOVEMENT_SPEED;
               case "health" -> EntityAttributes.MAX_HEALTH;
               default -> null;
            };
            return registryEntry2 != null && check27(itemStack, registryEntry2, holyWorldItemParserEntry2.level()) ? true : check22(string2, holyWorldItemParserEntry2.type(), holyWorldItemParserEntry2.level());
         }
      }
   }

   private static boolean check22(String string, String string2, double d) {
      String text16 = resolve26(d);
      String text17 = resolve28((int)d);

      for (String text18 : switch (string2) {
         case "damage" -> List.of("урон", "damage");
         case "armor" -> List.of("брон", "armor");
         case "speed" -> List.of("скор", "speed");
         case "health" -> List.of("здоров", "health");
         default -> List.of(string2);
      }) {
         String text19 = resolve19(text18);
         if (string.contains(text19 + text16) || string.contains(text16 + text19) || !text17.isEmpty() && (string.contains(text19 + text17) || string.contains(text17 + text19))) {
            return true;
         }
      }

      return false;
   }

   private static boolean check23(String string, HolyWorldItemParser.HolyWorldItemParserEntry3 holyWorldItemParserEntry33) {
      List items = VALUES_BY_KEY_2.getOrDefault(holyWorldItemParserEntry33.id(), List.of());
      if (items.isEmpty()) {
         return false;
      } else {
         String text20 = resolve26((double)holyWorldItemParserEntry33.level());
         String text21 = resolve28(holyWorldItemParserEntry33.level());

         for (String text22 : (List<String>)items) {
            String text23 = resolve19(text22);
            if (!text23.isEmpty()) {
               if (!string.contains(text23 + text20) && !string.contains(text20 + text23)) {
                  if (text21.isEmpty() || !string.contains(text23 + text21) && !string.contains(text21 + text23)) {
                     if (holyWorldItemParserEntry33.level() <= 1 && string.contains(text23)) {
                        return true;
                     }
                     continue;
                  }

                  return true;
               }

               return true;
            }
         }

         return false;
      }
   }

   private static boolean check24(String string, String string2) {
      String text24 = resolve19(string2);
      return !text24.isEmpty() && string.contains(text24);
   }

   private static boolean check25(HolyWorldItemParser.HolyWorldItemParserEntry holyWorldItemParserEntry4, String string) {
      String text25 = resolve27(holyWorldItemParserEntry4.value());
      String text26 = resolve23(holyWorldItemParserEntry4);
      return !text26.isEmpty() && (string.contains(text26 + text25) || string.contains(text25 + text26));
   }

   private static boolean check26(ItemStack itemStack, HolyWorldItemParser.HolyWorldItemParserEntry holyWorldItemParserEntry5) {
      AttributeModifiersComponent attributeModifiersComponent = (AttributeModifiersComponent)itemStack.get(DataComponentTypes.ATTRIBUTE_MODIFIERS);
      if (attributeModifiersComponent == null) {
         return false;
      } else {
         for (Entry entry : attributeModifiersComponent.modifiers()) {
            EntityAttributeModifier entityAttributeModifier = entry.modifier();
            if (check28(holyWorldItemParserEntry5, entry.attribute()) && Math.abs(entityAttributeModifier.value() - holyWorldItemParserEntry5.value()) <= 1.0E-4) {
               return true;
            }
         }

         return false;
      }
   }

   private static boolean check27(ItemStack itemStack, RegistryEntry<EntityAttribute> registryEntry, double d) {
      AttributeModifiersComponent attributeModifiersComponent2 = (AttributeModifiersComponent)itemStack.get(DataComponentTypes.ATTRIBUTE_MODIFIERS);
      if (attributeModifiersComponent2 == null) {
         return false;
      } else {
         for (Entry entry2 : attributeModifiersComponent2.modifiers()) {
            EntityAttributeModifier entityAttributeModifier2 = entry2.modifier();
            if (entry2.attribute().equals(registryEntry) && Math.abs(entityAttributeModifier2.value() - d) <= 1.0E-4) {
               return true;
            }
         }

         return false;
      }
   }

   private static boolean check28(HolyWorldItemParser.HolyWorldItemParserEntry holyWorldItemParserEntry6, RegistryEntry<EntityAttribute> registryEntry) {
      if (holyWorldItemParserEntry6.attribute() != null && holyWorldItemParserEntry6.attribute().equals(registryEntry)) {
         return true;
      } else {
         String text27 = resolve25(holyWorldItemParserEntry6.id());
         String text28 = resolve25(resolve24(registryEntry));
         return !text27.isEmpty() && text27.equals(text28);
      }
   }

   private static boolean check29(ItemStack itemStack, String string, int i) {
      ItemEnchantmentsComponent itemEnchantmentsComponent = (ItemEnchantmentsComponent)itemStack.get(DataComponentTypes.ENCHANTMENTS);
      if (itemEnchantmentsComponent != null && !itemEnchantmentsComponent.isEmpty()) {
         String text29 = resolve9(string);

         for (it.unimi.dsi.fastutil.objects.Object2IntMap.Entry entry3 : itemEnchantmentsComponent.getEnchantmentEntries()) {
            String text30 = resolve8((RegistryEntry<Enchantment>)entry3.getKey());
            if (text29.equals(resolve9(text30)) && entry3.getIntValue() >= i) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private static String resolve8(RegistryEntry<Enchantment> registryEntry) {
      Optional optional = registryEntry.getKey().map(registryKey -> registryKey.getValue());
      return ((Optional<Identifier>)optional).<String>map(Identifier::toString).orElse("");
   }

   private static boolean check30(String string) {
      String text31 = resolve9(string);

      return switch (text31) {
         case "aqua_affinity", "blast_protection", "depth_strider", "efficiency", "feather_falling", "fire_aspect", "fire_protection", "fortune", "luck_of_the_sea", "looting", "mending", "projectile_protection", "protection", "respiration", "sharpness", "smite", "soul_speed", "sweeping_edge", "thorns", "unbreaking", "bane_of_arthropods" -> true;
         default -> false;
      };
   }

   private static String resolve9(String string) {
      String text32 = string == null ? "" : string.toLowerCase(Locale.ROOT).trim();
      int intValue = text32.indexOf(58);
      if (intValue >= 0 && text32.substring(0, intValue).indexOf(45) < 0) {
         text32 = text32.substring(intValue + 1);
      }

      return VALUES_BY_KEY.getOrDefault(text32, text32);
   }

   private static HolyWorldItemParser.HolyWorldItemParserEntry3 resolve10(String string) {
      if (string != null && !string.isBlank()) {
         String text33 = string.trim();
         int intValue2 = text33.lastIndexOf(58);
         String text34 = intValue2 > 0 ? text33.substring(0, intValue2).trim().toLowerCase(Locale.ROOT) : text33.toLowerCase(Locale.ROOT);
         int intValue3 = 1;
         if (intValue2 > 0 && intValue2 < text33.length() - 1) {
            try {
               intValue3 = Integer.parseInt(text33.substring(intValue2 + 1).replaceAll("[^0-9]", ""));
            } catch (NumberFormatException numberFormatException) {
               intValue3 = 1;
            }
         }

         return new HolyWorldItemParser.HolyWorldItemParserEntry3(string, text34, Math.max(1, intValue3));
      } else {
         return null;
      }
   }

   private static HolyWorldItemParser.HolyWorldItemParserEntry2 resolve11(String string) {
      if (string != null && !string.isBlank()) {
         String[] texts2 = string.split(":", 2);
         if (texts2.length != 2) {
            return null;
         } else {
            String text35 = texts2[0].toLowerCase(Locale.ROOT).replace("hms-", "").trim();

            try {
               return new HolyWorldItemParser.HolyWorldItemParserEntry2(text35, Double.parseDouble(texts2[1].replace(',', '.')));
            } catch (NumberFormatException numberFormatException2) {
               return null;
            }
         }
      } else {
         return null;
      }
   }

   private static Map<String, HolyWorldItemParser.HolyWorldItemParserDisplayEntry> resolve12() {
      HashMap hashMap = new HashMap();

      for (HolyWorldItemParser.HolyWorldItemParserDisplayEntry holyWorldItemParserDisplayEntry18 : ITEMS) {
         hashMap.put(holyWorldItemParserDisplayEntry18.key(), holyWorldItemParserDisplayEntry18);
         hashMap.put(resolve19(holyWorldItemParserDisplayEntry18.label()), holyWorldItemParserDisplayEntry18);

         for (String text36 : holyWorldItemParserDisplayEntry18.aliases()) {
            if (!text36.isEmpty()) {
               hashMap.putIfAbsent(text36, holyWorldItemParserDisplayEntry18);
            }
         }
      }

      return Map.copyOf(hashMap);
   }

   private static HolyWorldItemParser.HolyWorldItemParserDisplayEntry resolve13(String string, Item item, String... strings) {
      return resolve14(string, item, strings).resolve6();
   }

   private static HolyWorldItemParser.HolyWorldItemParserItemState resolve14(String string, Item item, String... strings) {
      return new HolyWorldItemParser.HolyWorldItemParserItemState(string, item, strings);
   }

   private static HolyWorldItemParser.HolyWorldItemParserEntry resolve15(RegistryEntry<EntityAttribute> registryEntry, double d) {
      return new HolyWorldItemParser.HolyWorldItemParserEntry(registryEntry, resolve24(registryEntry), d);
   }

   private static HolyWorldItemParser.HolyWorldItemParserEntry resolve16(String string, double d) {
      return new HolyWorldItemParser.HolyWorldItemParserEntry(null, string, d);
   }

   private static ItemStack resolve17(String string, String string2) {
      ItemStack itemStack2 = new ItemStack(Items.PLAYER_HEAD);
      UUID uuid = UUID.nameUUIDFromBytes(("holyworld:" + string2 + string).getBytes(StandardCharsets.UTF_8));
      GameProfile gameProfile = new GameProfile(uuid, "");
      gameProfile.getProperties().put("textures", new Property("textures", string));
      itemStack2.set(DataComponentTypes.PROFILE, new ProfileComponent(gameProfile));
      return itemStack2;
   }

   private static String resolve18(String string) {
      return check(string) ? string.substring("holyworld:".length()) : string;
   }

   static String resolve19(String string) {
      return string == null
         ? ""
         : resolve21(resolve20(string).replaceAll("(?i)§[0-9A-FK-OR]", "").toLowerCase(Locale.ROOT)).replaceAll("[^\\p{L}\\p{N}]+", "");
   }

   private static String resolve20(String string) {
      return string.replace("ᴀ", "a")
         .replace("ʙ", "b")
         .replace("ᴄ", "c")
         .replace("ᴅ", "d")
         .replace("ᴇ", "e")
         .replace("ғ", "f")
         .replace("ɢ", "g")
         .replace("ʜ", "h")
         .replace("ɪ", "i")
         .replace("ᴊ", "j")
         .replace("ᴋ", "k")
         .replace("ʟ", "l")
         .replace("ᴍ", "m")
         .replace("ɴ", "n")
         .replace("ᴏ", "o")
         .replace("ᴘ", "p")
         .replace("ǫ", "q")
         .replace("ʀ", "r")
         .replace("ѕ", "s")
         .replace("ᴛ", "t")
         .replace("ᴜ", "u")
         .replace("ᴠ", "v")
         .replace("ᴡ", "w")
         .replace("х", "x")
         .replace("ʏ", "y")
         .replace("ᴢ", "z");
   }

   private static String resolve21(String string) {
      return string.replace("инфинити", "infinity").replace("этернити", "eternity").replace("етернити", "eternity").replace("стингер", "stinger");
   }

   private static boolean check31(String string, List<String> list) {
      if (string != null && !string.isEmpty()) {
         for (String text37 : list) {
            if (text37 != null && !text37.isEmpty() && string.contains(text37)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private static String resolve22(ItemStack itemStack) {
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append(itemStack.getName().getString()).append(' ');
      LoreComponent loreComponent2 = (LoreComponent)itemStack.get(DataComponentTypes.LORE);
      if (loreComponent2 != null) {
         for (Text text38 : loreComponent2.lines()) {
            stringBuilder2.append(text38.getString()).append(' ');
         }
      }

      stringBuilder2.append(itemStack.getComponents());
      return stringBuilder2.toString();
   }

   private static String resolve23(HolyWorldItemParser.HolyWorldItemParserEntry holyWorldItemParserEntry7) {
      String text39 = resolve25(holyWorldItemParserEntry7.id());
      if (!text39.isEmpty()) {
         return resolve19(text39);
      } else {
         RegistryEntry registryEntry3 = holyWorldItemParserEntry7.attribute();
         if (registryEntry3 == null) {
            return "";
         } else if (registryEntry3.equals(EntityAttributes.ATTACK_DAMAGE)) {
            return "attackdamage";
         } else if (registryEntry3.equals(EntityAttributes.ARMOR)) {
            return "armor";
         } else if (registryEntry3.equals(EntityAttributes.MOVEMENT_SPEED)) {
            return "movementspeed";
         } else {
            return registryEntry3.equals(EntityAttributes.MAX_HEALTH) ? "maxhealth" : "";
         }
      }
   }

   private static String resolve24(RegistryEntry<EntityAttribute> registryEntry) {
      return registryEntry == null ? "" : registryEntry.getKey().map(registryKey -> registryKey.getValue().toString()).orElse("");
   }

   private static String resolve25(String string) {
      if (string == null) {
         return "";
      } else {
         String text40 = string.toLowerCase(Locale.ROOT).trim();
         if (text40.startsWith("minecraft:")) {
            text40 = text40.substring("minecraft:".length());
         }

         if (text40.startsWith("generic.")) {
            text40 = text40.substring("generic.".length());
         }

         return text40.replace('.', '_');
      }
   }

   private static String resolve26(double d) {
      return d == Math.rint(d) ? String.valueOf((int)d) : resolve27(d);
   }

   private static String resolve27(double d) {
      return d == Math.rint(d) ? String.valueOf((int)d) : String.valueOf(d).replace(".", "");
   }

   private static String resolve28(int i) {
      return switch (i) {
         case 1 -> "i";
         case 2 -> "ii";
         case 3 -> "iii";
         case 4 -> "iv";
         case 5 -> "v";
         case 6 -> "vi";
         case 7 -> "vii";
         case 8 -> "viii";
         case 9 -> "ix";
         case 10 -> "x";
         default -> "";
      };
   }

   public record HolyWorldItemParserEntry(RegistryEntry<EntityAttribute> attribute, String id, double value) {
   }

   static final class HolyWorldItemParserItemState {
      private final String text;
      private final Item item;
      private final List<String> items = new ArrayList<>();
      private final List<String> items2 = new ArrayList<>();
      private final List<String> items3 = new ArrayList<>();
      private final List<String> items4 = new ArrayList<>();
      private final List<HolyWorldItemParser.HolyWorldItemParserEntry> items5 = new ArrayList<>();
      private String text2;
      private boolean flag;

      HolyWorldItemParserItemState(String string, Item item, String... strings) {
         this.text = string;
         this.item = item;
         this.items.add(HolyWorldItemParser.resolve19(string));

         for (String text41 : strings) {
            this.items.add(HolyWorldItemParser.resolve19(text41));
         }
      }

      HolyWorldItemParser.HolyWorldItemParserItemState resolve(String... strings) {
         this.items2.addAll(List.of(strings));
         return this;
      }

      HolyWorldItemParser.HolyWorldItemParserItemState resolve2(String... strings) {
         this.items3.addAll(List.of(strings));
         return this;
      }

      private HolyWorldItemParser.HolyWorldItemParserItemState resolve3(String... strings) {
         this.items4.addAll(List.of(strings));
         return this;
      }

      HolyWorldItemParser.HolyWorldItemParserItemState resolve4(HolyWorldItemParser.HolyWorldItemParserEntry... w116s) {
         this.items5.addAll(List.of(w116s));
         return this;
      }

      HolyWorldItemParser.HolyWorldItemParserItemState setText2(String string) {
         this.text2 = string;
         return this;
      }

      private HolyWorldItemParser.HolyWorldItemParserItemState resolve5() {
         this.flag = true;
         return this;
      }

      HolyWorldItemParser.HolyWorldItemParserDisplayEntry resolve6() {
         String text42 = "holyworld:" + HolyWorldItemParser.resolve19(this.text);
         int intValue4 = HolyWorldItemParser.VALUES_BY_KEY_3.merge(text42, 1, Integer::sum);
         return new HolyWorldItemParser.HolyWorldItemParserDisplayEntry(
            intValue4 == 1 ? text42 : text42 + ":" + intValue4,
            this.text,
            this.item,
            List.copyOf(this.items),
            List.copyOf(this.items2),
            List.copyOf(this.items3),
            List.copyOf(this.items4),
            List.copyOf(this.items5),
            this.text2,
            this.flag
         );
      }
   }

   record HolyWorldItemParserEntry2(String type, double level) {
   }

   record HolyWorldItemParserEntry3(String raw, String id, int level) {
   }

   public record HolyWorldItemParserDisplayEntry(
      String key,
      String label,
      Item item,
      List<String> aliases,
      List<String> lore,
      List<String> enchantments,
      List<String> effects,
      List<HolyWorldItemParser.HolyWorldItemParserEntry> attributes,
      String texture,
      boolean strictCheck
   ) {
      boolean hasRequirements() {
         return !this.lore.isEmpty() || !this.enchantments.isEmpty() || !this.effects.isEmpty() || !this.attributes.isEmpty();
      }
   }
}
