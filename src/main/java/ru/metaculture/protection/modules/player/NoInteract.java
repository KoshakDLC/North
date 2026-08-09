package ru.metaculture.protection;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "NoInteract",
   description = "Запрещает взаимодействие с выбранными блоками",
   category = Category.Player
)
public class NoInteract extends Module {
   public static GroupSetting bloki = new GroupSetting(
      "Блоки",
      new BooleanSetting("Стойки для брони", true),
      new BooleanSetting("Сундуки", true),
      new BooleanSetting("Двери", true),
      new BooleanSetting("Кнопки", true),
      new BooleanSetting("Воронки", true),
      new BooleanSetting("Раздатчики", true),
      new BooleanSetting("Нотные блоки", true),
      new BooleanSetting("Верстаки", true),
      new BooleanSetting("Люки", true),
      new BooleanSetting("Печи", true),
      new BooleanSetting("Калитки", true),
      new BooleanSetting("Наковальни", true),
      new BooleanSetting("Шалкеры", true),
      new BooleanSetting("Эндер-сундуки", true),
      new BooleanSetting("Варочные стойки", true),
      new BooleanSetting("Столы зачарования", true),
      new BooleanSetting("Пюпитры", true),
      new BooleanSetting("Рабочие столы", true),
      new BooleanSetting("Кровати", false),
      new BooleanSetting("Нажимные плиты", false),
      new BooleanSetting("Медные лампы", false),
      new BooleanSetting("Редстоун", false),
      new BooleanSetting("Прочее", false)
   );

   public NoInteract() {
      this.addSettings(new Setting[]{bloki});
   }

   public static Set<Block> resolve() {
      HashSet hashSet = new HashSet();
      invoke(hashSet, 2, Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.BARREL);
      invoke(
         hashSet,
         3,
         Blocks.OAK_DOOR,
         Blocks.SPRUCE_DOOR,
         Blocks.BIRCH_DOOR,
         Blocks.JUNGLE_DOOR,
         Blocks.ACACIA_DOOR,
         Blocks.DARK_OAK_DOOR,
         Blocks.MANGROVE_DOOR,
         Blocks.CHERRY_DOOR,
         Blocks.PALE_OAK_DOOR,
         Blocks.BAMBOO_DOOR,
         Blocks.CRIMSON_DOOR,
         Blocks.WARPED_DOOR,
         Blocks.IRON_DOOR,
         Blocks.COPPER_DOOR,
         Blocks.EXPOSED_COPPER_DOOR,
         Blocks.WEATHERED_COPPER_DOOR,
         Blocks.OXIDIZED_COPPER_DOOR,
         Blocks.WAXED_COPPER_DOOR,
         Blocks.WAXED_EXPOSED_COPPER_DOOR,
         Blocks.WAXED_WEATHERED_COPPER_DOOR,
         Blocks.WAXED_OXIDIZED_COPPER_DOOR
      );
      invoke(
         hashSet,
         4,
         Blocks.OAK_BUTTON,
         Blocks.SPRUCE_BUTTON,
         Blocks.BIRCH_BUTTON,
         Blocks.JUNGLE_BUTTON,
         Blocks.ACACIA_BUTTON,
         Blocks.DARK_OAK_BUTTON,
         Blocks.MANGROVE_BUTTON,
         Blocks.CHERRY_BUTTON,
         Blocks.PALE_OAK_BUTTON,
         Blocks.BAMBOO_BUTTON,
         Blocks.CRIMSON_BUTTON,
         Blocks.WARPED_BUTTON,
         Blocks.STONE_BUTTON,
         Blocks.POLISHED_BLACKSTONE_BUTTON
      );
      invoke(hashSet, 5, Blocks.HOPPER);
      invoke(hashSet, 6, Blocks.DISPENSER, Blocks.DROPPER);
      invoke(hashSet, 7, Blocks.NOTE_BLOCK);
      invoke(hashSet, 8, Blocks.CRAFTING_TABLE);
      invoke(
         hashSet,
         9,
         Blocks.OAK_TRAPDOOR,
         Blocks.SPRUCE_TRAPDOOR,
         Blocks.BIRCH_TRAPDOOR,
         Blocks.JUNGLE_TRAPDOOR,
         Blocks.ACACIA_TRAPDOOR,
         Blocks.DARK_OAK_TRAPDOOR,
         Blocks.MANGROVE_TRAPDOOR,
         Blocks.CHERRY_TRAPDOOR,
         Blocks.PALE_OAK_TRAPDOOR,
         Blocks.BAMBOO_TRAPDOOR,
         Blocks.CRIMSON_TRAPDOOR,
         Blocks.WARPED_TRAPDOOR,
         Blocks.IRON_TRAPDOOR,
         Blocks.COPPER_TRAPDOOR,
         Blocks.EXPOSED_COPPER_TRAPDOOR,
         Blocks.WEATHERED_COPPER_TRAPDOOR,
         Blocks.OXIDIZED_COPPER_TRAPDOOR,
         Blocks.WAXED_COPPER_TRAPDOOR,
         Blocks.WAXED_EXPOSED_COPPER_TRAPDOOR,
         Blocks.WAXED_WEATHERED_COPPER_TRAPDOOR,
         Blocks.WAXED_OXIDIZED_COPPER_TRAPDOOR
      );
      invoke(hashSet, 10, Blocks.FURNACE, Blocks.BLAST_FURNACE, Blocks.SMOKER);
      invoke(
         hashSet,
         11,
         Blocks.OAK_FENCE_GATE,
         Blocks.SPRUCE_FENCE_GATE,
         Blocks.BIRCH_FENCE_GATE,
         Blocks.JUNGLE_FENCE_GATE,
         Blocks.ACACIA_FENCE_GATE,
         Blocks.DARK_OAK_FENCE_GATE,
         Blocks.MANGROVE_FENCE_GATE,
         Blocks.CHERRY_FENCE_GATE,
         Blocks.PALE_OAK_FENCE_GATE,
         Blocks.BAMBOO_FENCE_GATE,
         Blocks.CRIMSON_FENCE_GATE,
         Blocks.WARPED_FENCE_GATE
      );
      invoke(hashSet, 12, Blocks.ANVIL, Blocks.CHIPPED_ANVIL, Blocks.DAMAGED_ANVIL);
      invoke(
         hashSet,
         13,
         Blocks.SHULKER_BOX,
         Blocks.WHITE_SHULKER_BOX,
         Blocks.ORANGE_SHULKER_BOX,
         Blocks.MAGENTA_SHULKER_BOX,
         Blocks.LIGHT_BLUE_SHULKER_BOX,
         Blocks.YELLOW_SHULKER_BOX,
         Blocks.LIME_SHULKER_BOX,
         Blocks.PINK_SHULKER_BOX,
         Blocks.GRAY_SHULKER_BOX,
         Blocks.LIGHT_GRAY_SHULKER_BOX,
         Blocks.CYAN_SHULKER_BOX,
         Blocks.PURPLE_SHULKER_BOX,
         Blocks.BLUE_SHULKER_BOX,
         Blocks.BROWN_SHULKER_BOX,
         Blocks.GREEN_SHULKER_BOX,
         Blocks.RED_SHULKER_BOX,
         Blocks.BLACK_SHULKER_BOX
      );
      invoke(hashSet, 14, Blocks.ENDER_CHEST);
      invoke(hashSet, 15, Blocks.BREWING_STAND);
      invoke(hashSet, 16, Blocks.ENCHANTING_TABLE);
      invoke(hashSet, 17, Blocks.LECTERN);
      invoke(hashSet, 18, Blocks.GRINDSTONE, Blocks.LOOM, Blocks.CARTOGRAPHY_TABLE, Blocks.SMITHING_TABLE, Blocks.STONECUTTER);
      invoke(
         hashSet,
         19,
         Blocks.WHITE_BED,
         Blocks.ORANGE_BED,
         Blocks.MAGENTA_BED,
         Blocks.LIGHT_BLUE_BED,
         Blocks.YELLOW_BED,
         Blocks.LIME_BED,
         Blocks.PINK_BED,
         Blocks.GRAY_BED,
         Blocks.LIGHT_GRAY_BED,
         Blocks.CYAN_BED,
         Blocks.PURPLE_BED,
         Blocks.BLUE_BED,
         Blocks.BROWN_BED,
         Blocks.GREEN_BED,
         Blocks.RED_BED,
         Blocks.BLACK_BED
      );
      invoke(
         hashSet,
         20,
         Blocks.OAK_PRESSURE_PLATE,
         Blocks.SPRUCE_PRESSURE_PLATE,
         Blocks.BIRCH_PRESSURE_PLATE,
         Blocks.JUNGLE_PRESSURE_PLATE,
         Blocks.ACACIA_PRESSURE_PLATE,
         Blocks.DARK_OAK_PRESSURE_PLATE,
         Blocks.MANGROVE_PRESSURE_PLATE,
         Blocks.CHERRY_PRESSURE_PLATE,
         Blocks.PALE_OAK_PRESSURE_PLATE,
         Blocks.BAMBOO_PRESSURE_PLATE,
         Blocks.CRIMSON_PRESSURE_PLATE,
         Blocks.WARPED_PRESSURE_PLATE,
         Blocks.STONE_PRESSURE_PLATE,
         Blocks.POLISHED_BLACKSTONE_PRESSURE_PLATE,
         Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE,
         Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE
      );
      invoke(
         hashSet,
         21,
         Blocks.COPPER_BULB,
         Blocks.EXPOSED_COPPER_BULB,
         Blocks.WEATHERED_COPPER_BULB,
         Blocks.OXIDIZED_COPPER_BULB,
         Blocks.WAXED_COPPER_BULB,
         Blocks.WAXED_EXPOSED_COPPER_BULB,
         Blocks.WAXED_WEATHERED_COPPER_BULB,
         Blocks.WAXED_OXIDIZED_COPPER_BULB
      );
      invoke(hashSet, 22, Blocks.REPEATER, Blocks.COMPARATOR, Blocks.DAYLIGHT_DETECTOR, Blocks.LEVER);
      invoke(
         hashSet,
         23,
         Blocks.JUKEBOX,
         Blocks.BELL,
         Blocks.DECORATED_POT,
         Blocks.CHISELED_BOOKSHELF,
         Blocks.RESPAWN_ANCHOR,
         Blocks.CAKE,
         Blocks.FLOWER_POT,
         Blocks.CAMPFIRE,
         Blocks.SOUL_CAMPFIRE
      );
      return hashSet;
   }

   private static void invoke(Set<Block> set, int i, Block... blocks) {
      if (bloki.isEnabled(i - 1)) {
         set.addAll(Arrays.asList(blocks));
      }
   }
}
