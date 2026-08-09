package ru.metaculture.protection;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.InventoryS2CPacket;
import net.minecraft.network.packet.s2c.play.OpenScreenS2CPacket;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.PlayerInput;
import net.minecraft.util.math.MathHelper;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "AutoBuy",
   category = Category.Misc,
   description = "Автоматическая покупка предметов с аукциона"
)
public class AutoBuy extends Module {
   private static final Pattern PATTERN = Pattern.compile("Подождите\\s+(\\d+)\\s*сек", 66);
   private static final Pattern PATTERN_2 = Pattern.compile("подождите\\s+(\\d+)\\s*сек\\S*\\s+для\\s+использования\\s+этой\\s+команды", 66);
   private static final long TIMESTAMP = 9000L;
   private static final long TIMESTAMP_2 = 2000L;
   private static final long TIMESTAMP_3 = 250L;
   private static final long TIMESTAMP_4 = 2500L;
   private static final long TIMESTAMP_5 = 4500L;
   private static final long TIMESTAMP_6 = 12000L;
   private static final long TIMESTAMP_7 = 500L;
   private static final long TIMESTAMP_8 = 15000L;
   private static final long TIMESTAMP_9 = 20000L;
   private static final long TIMESTAMP_10 = 240000L;
   private static final long TIMESTAMP_11 = 4000L;
   private static final long TIMESTAMP_12 = 8000L;
   private static final int INT_VALUE = 3;
   private static final long TIMESTAMP_13 = 2000L;
   private static final long TIMESTAMP_14 = 4500L;
   private static final long TIMESTAMP_15 = 600L;
   private static final long TIMESTAMP_16 = 1400L;
   private static final long TIMESTAMP_17 = 750L;
   private static final long TIMESTAMP_18 = 15000L;
   private static final String WILD_FUNTIME_SHULKER = "__wild_funtime_shulker__";
   private static final int INT_VALUE_2 = 0;
   private static final int INT_VALUE_3 = 1;
   private static final int INT_VALUE_4 = 2;
   private static final int INT_VALUE_5 = 3;
   private static final int INT_VALUE_6 = 4;
   private static final long TIMESTAMP_19 = 1200L;
   private static final long TIMESTAMP_20 = 4500L;
   private static final long TIMESTAMP_21 = 90L;
   private static final float FLOAT_VALUE = (float) (Math.PI * 2);
   private static final long TIMESTAMP_22 = 75L;
   private static final double DOUBLE_VALUE = 1.0;
   private static final double DOUBLE_VALUE_2 = 4.0;
   private static final int INT_VALUE_7 = 3;
   private static final Pattern PATTERN_3 = Pattern.compile(
      "Вы\\s+купили\\s+(?:[-–—]\\s*)?(?:\\[([^\\]]+)]|(.+?))\\s*(?:[-–—]?\\s*[xхXХ](\\d+))?\\s+у\\s+(.+?)\\s+за\\s+([\\d\\s.,]+)\\s*[¤$]?", 66
   );
   private static final Set<String> VALUES = Set.of(
      "Сфера Хаоса",
      "Сфера Титана",
      "Сфера Ареса",
      "Сфера Бестии",
      "Сфера Гидры",
      "Сфера Икара",
      "Сфера Эрида",
      "Сфера Сатира",
      "Талисман Демона",
      "Талисман Карателя",
      "Талисман Мрака",
      "Талисман Ярости",
      "Талисман Тирана",
      "Талисман Крушителя",
      "Талисман Раздора",
      "Талисман Сара",
      "Талисман Сары",
      "Вещи Крушителя",
      "Набор Крушителя",
      "Броня Крушителя",
      "Броня Крушителя с шипами",
      "Броня Крушителя шип",
      "Броня Крушителя без шипов",
      "Броня Крушителя без шип",
      "Шлем Крушителя",
      "Нагрудник Крушителя",
      "Поножи Крушителя",
      "Ботинки Крушителя",
      "Меч Крушителя",
      "Кирка Крушителя",
      "Лук Крушителя",
      "Арбалет Крушителя",
      "Трезубец Крушителя",
      "Булава Крушителя",
      "Элитры Крушителя",
      "Удочка Крушителя",
      "Зелье Ассасина",
      "Зелье Гнева",
      "Хлопушка",
      "Святая Вода",
      "Зелье Палладина",
      "Зелье Радиации",
      "Снотворное",
      "Пласт",
      "Опыт 15",
      "Опыт 30",
      "Опыт 45",
      "Опыт 50",
      "Вайт",
      "Блек",
      "Блок дамагер",
      "Прогрузчик чанков",
      "Маяк",
      "Проклятая Душа",
      "Драконий Скин",
      "Огненный Смерч",
      "Снежок Заморозка",
      "Божья Аура",
      "Серебро",
      "Божье Касание",
      "Божье касание",
      "Мощный Удар",
      "Мега Бульдозер",
      "Нерушимые Элитры"
   );
   private static final Set<String> VALUES_2 = Set.of("Явная Пыль", "Дезориентация", "Трапка", "Отмычка к Сферам");
   public static AutoBuy instance;
   public static boolean flag = false;
   public static boolean flag2 = false;
   public static boolean flag3 = false;
   public final ModeSetting rezhimServera = new ModeSetting("Режим сервера", "FunTime", "FunTime", "SpookyTime", "HolyWorld");
   public final BooleanSetting autoParse = new BooleanSetting("Auto Parse", false);
   public final NumberSetting parsSkidka = new NumberSetting("Парс Скидка %", 20.0F, 1.0F, 100.0F, 1.0F, true)
      .setVisibilityCondition(() -> !this.autoParse.isEnabled());
   public final BooleanSetting autoReparse = new BooleanSetting("Auto ReParse", false);
   public final NumberSetting reparseKazhdyeMin = new NumberSetting("ReParse каждые (мин)", 30.0F, 5.0F, 240.0F, 5.0F, false)
      .setVisibilityCondition(() -> !this.autoReparse.isEnabled());
   public final BooleanSetting svapAnarhii510Min = new BooleanSetting("Свап анархии (5-10 мин)", false).visibleWhen(() -> !this.rezhimServera.is("FunTime"));
   public final NumberSetting kdObnovleniyaMs = new NumberSetting("Кд обновления (мс)", 100.0F, 100.0F, 5000.0F, 50.0F, false);
   public final NumberSetting kdPokupkiMs = new NumberSetting("Кд покупки (мс)", 100.0F, 100.0F, 5000.0F, 50.0F, false);
   public final NumberSetting kdPodtverzhdeniyaMs = new NumberSetting("Кд подтверждения (мс)", 50.0F, 0.0F, 1000.0F, 10.0F, false);
   public final BooleanSetting detektZamedleniyaAuka = new BooleanSetting("Детект замедления аука", true);
   public final BooleanSetting avtoFiksZamedleniya = new BooleanSetting("Авто-фикс замедления", true).visibleWhen(() -> !this.detektZamedleniyaAuka.isEnabled());
   public final BooleanSetting lagStatistikaVChat = new BooleanSetting("Лаг статистика в чат", true).visibleWhen(() -> !this.detektZamedleniyaAuka.isEnabled());
   public final KeybindSetting menuKey = new KeybindSetting("Бинд меню", -1);
   public final NumberSetting shulkerProfit = new NumberSetting("Shulker Profit %", 18.0F, 0.0F, 200.0F, 1.0F, true);
   public final NumberSetting shulkerProfit2 = new NumberSetting("Shulker Profit $", 50000.0F, 0.0F, 1.0E9F, 10000.0F, false);
   public final NumberSetting shulkerValue = new NumberSetting("Shulker Value $", 100000.0F, 0.0F, 1.0E9F, 10000.0F, false);
   public static final Map<String, Long> VALUES_BY_KEY = new LinkedHashMap<>();
   public static final Map<String, Integer> VALUES_BY_KEY_2 = new LinkedHashMap<>();
   public static final Map<String, Integer> VALUES_BY_KEY_3 = new LinkedHashMap<>();
   public static final Map<String, Set<String>> VALUES_BY_KEY_4 = new LinkedHashMap<>();
   public static final List<String> ITEMS = new ArrayList<>();
   public static final Set<String> VALUES_3 = new HashSet<>();
   public static final Map<String, String> VALUES_BY_KEY_5 = new LinkedHashMap<>();
   public static final List<AutoBuy.AutoBuyState> ITEMS_2 = new ArrayList<>();
   private final DualTimer dualTimer = new DualTimer();
   private final DualTimer dualTimer2 = new DualTimer();
   private final DualTimer dualTimer3 = new DualTimer();
   private final DualTimer dualTimer4 = new DualTimer();
   private final DualTimer dualTimer5 = new DualTimer();
   private final DualTimer dualTimer6 = new DualTimer();
   private final DualTimer dualTimer7 = new DualTimer();
   private final DualTimer dualTimer8 = new DualTimer();
   private final DualTimer dualTimer9 = new DualTimer();
   private final DualTimer dualTimer10 = new DualTimer();
   private final DualTimer dualTimer11 = new DualTimer();
   private boolean flag4 = false;
   public static long timestamp = 0L;
   public static long timestamp2 = 0L;
   public static long timestamp3 = 0L;
   public static long timestamp4 = 0L;
   private int intValue = -1;
   private boolean flag5 = false;
   private int intValue2 = 0;
   private boolean flag6 = false;
   private String text = "";
   private String text2 = "";
   private boolean flag7 = false;
   private final List<String> items = new ArrayList<>();
   private int intValue3 = 0;
   private boolean flag8 = false;
   private boolean flag9 = false;
   private String text3 = "";
   private String text4 = "";
   private int intValue4 = 0;
   private long timestamp5 = 0L;
   private long timestamp6 = 0L;
   private long timestamp7 = 0L;
   private long timestamp8 = 0L;
   private boolean flag10 = false;
   private boolean flag11 = false;
   private boolean flag12 = false;
   private boolean flag13 = false;
   private int DynamicButtonSetting = -1;
   private long timestamp9 = 0L;
   private long timestamp10 = 0L;
   private long timestamp11 = 200L;
   private long timestamp12 = 50L;
   private long SpacerSetting = 0L;
   private long FoundryShaderSetting = 0L;
   private long timestamp13 = 0L;
   private long timestamp14 = 0L;
   private int intValue5 = 0;
   private boolean flag14 = false;
   private boolean flag15 = false;
   private boolean flag16 = false;
   private float floatValue = 0.0F;
   private float floatValue2 = 0.0F;
   private float floatValue3 = 0.0F;
   private float floatValue4 = 1.0F;
   private float floatValue5 = 0.0F;
   private float floatValue6 = 0.0F;
   private float floatValue7 = 0.0F;
   private float floatValue8 = 0.0F;
   private float floatValue9 = 1.0F;
   private float floatValue10 = 0.12F;
   private long timestamp15 = 0L;
   private float floatValue11 = 0.0F;
   private boolean flag17 = false;
   private long timestamp16 = 0L;
   private double doubleValue = 0.0;
   private double doubleValue2 = 0.0;
   private int intValue6 = -1;
   private String text5 = "";
   private long timestamp17 = 0L;
   private String text6 = "";
   private long timestamp18 = 0L;
   private long timestamp19 = 0L;
   private int intValue7 = 0;
   private boolean flag18 = false;
   private long timestamp20 = 0L;
   private long timestamp21 = 0L;
   private long timestamp22 = 0L;
   private int intValue8 = 0;
   private final Map<Item, List<AutoBuy.AutoBuyItemData>> valuesByKey = new HashMap<>();
   private final List<AutoBuy.AutoBuyItemData> items2 = new ArrayList<>();
   private int intValue9 = Integer.MIN_VALUE;
   private final AuctionClickTracker auctionClickTracker = new AuctionClickTracker();
   private boolean flag19 = false;
   private long timestamp23 = 0L;
   private long timestamp24 = 0L;
   private boolean flag20 = false;
   private long timestamp25 = 0L;
   private int intValue10 = 0;
   private long timestamp26 = 0L;

   public AutoBuy() {
      instance = this;
      this.addSettings(
         new Setting[]{
            this.rezhimServera,
            this.autoParse,
            this.parsSkidka,
            this.autoReparse,
            this.reparseKazhdyeMin,
            this.svapAnarhii510Min,
            this.kdObnovleniyaMs,
            this.kdPokupkiMs,
            this.kdPodtverzhdeniyaMs,
            this.detektZamedleniyaAuka,
            this.avtoFiksZamedleniya,
            this.lagStatistikaVChat,
            this.shulkerProfit,
            this.shulkerProfit2,
            this.shulkerValue,
            this.menuKey
         }
      );
   }

   private long compute() {
      String text = this.rezhimServera.getValue();
      if (text.equals("FunTime")) {
         return ThreadLocalRandom.current().nextLong(100L, 201L);
      } else {
         return !text.equals("SpookyTime") && !text.equals("HolyWorld") ? (long)this.kdObnovleniyaMs.getValue() : this.timestamp11;
      }
   }

   private long compute2() {
      String text2 = this.rezhimServera.getValue();
      if (text2.equals("FunTime")) {
         return 10L;
      } else {
         return !text2.equals("SpookyTime") && !text2.equals("HolyWorld") ? (long)this.kdPokupkiMs.getValue() : this.timestamp12;
      }
   }

   private void invoke() {
      this.timestamp11 = ThreadLocalRandom.current().nextLong(200L, 401L);
      this.timestamp12 = ThreadLocalRandom.current().nextLong(30L, 81L);
   }

   public static long compute3(String string) {
      for (AutoBuy.AutoBuyState autoBuyState : ITEMS_2) {
         if (autoBuyState.text2.toLowerCase(Locale.ROOT).contains(string.toLowerCase(Locale.ROOT))) {
            int intValue = Math.max(1, autoBuyState.intValue);
            return Math.max(1L, (autoBuyState.timestamp + intValue - 1L) / intValue);
         }
      }

      return 0L;
   }

   public static boolean check(String string) {
      String text3 = resolve3(string);
      if (text3.isEmpty()) {
         return false;
      } else {
         VALUES_BY_KEY_5.put(resolve2(text3), text3);
         return true;
      }
   }

   public static boolean check2(String string) {
      String text4 = resolve2(string);
      return !text4.isEmpty() && VALUES_BY_KEY_5.remove(text4) != null;
   }

   public static boolean check3(String string) {
      String text5 = resolve2(string);
      return !text5.isEmpty() && VALUES_BY_KEY_5.containsKey(text5);
   }

   public static void invoke2() {
      VALUES_BY_KEY_5.clear();
   }

   public static List<String> resolve() {
      return new ArrayList<>(VALUES_BY_KEY_5.values());
   }

   private static String resolve2(String string) {
      return resolve3(string).toLowerCase(Locale.ROOT);
   }

   private static String resolve3(String string) {
      if (string == null) {
         return "";
      } else {
         String text6 = string.replaceAll("§.", "").replace(' ', ' ').trim();
         if (text6.startsWith("+")) {
            text6 = text6.substring(1).trim();
         }

         return text6;
      }
   }

   public static AutoBuy.AutoBuyState resolve4() {
      return ITEMS_2.isEmpty() ? null : ITEMS_2.get(0);
   }

   public static int compute4() {
      int intValue2 = 0;
      long longValue = timestamp;

      for (AutoBuy.AutoBuyState autoBuyState2 : ITEMS_2) {
         if (autoBuyState2.timestamp2 >= longValue) {
            intValue2++;
         }
      }

      return intValue2;
   }

   public static int compute5() {
      int intValue3 = 0;
      long longValue2 = timestamp;

      for (AutoBuy.AutoBuyState autoBuyState3 : ITEMS_2) {
         if (autoBuyState3.timestamp2 >= longValue2) {
            intValue3 += Math.max(1, autoBuyState3.intValue);
         }
      }

      return intValue3;
   }

   public static long compute6() {
      long longValue3 = 0L;
      long longValue4 = timestamp;

      for (AutoBuy.AutoBuyState autoBuyState4 : ITEMS_2) {
         if (autoBuyState4.timestamp2 >= longValue4) {
            longValue3 += Math.max(0L, autoBuyState4.timestamp);
         }
      }

      return longValue3;
   }

   public static long compute7() {
      return timestamp2 > 0L && timestamp3 > 0L ? timestamp3 - timestamp2 : 0L;
   }

   public File resolve5() {
      File file = new File(WildClient.INSTANCE.file, "configs/autobuy");
      if (!file.exists()) {
         file.mkdirs();
      }

      return file;
   }

   public void invoke3(String string) {
      try {
         File file2 = this.resolve5();
         File file3 = new File(file2, string + ".json");
         JsonObject jsonObject2 = this.toJson();

         try (FileWriter fileWriter = new FileWriter(file3)) {
            new GsonBuilder().setPrettyPrinting().create().toJson(jsonObject2, fileWriter);
         }
      } catch (Exception exception) {
         exception.printStackTrace();
      }
   }

   public void invoke4(String string) {
      try {
         File file4 = new File(this.resolve5(), string + ".json");
         if (!file4.exists()) {
            return;
         }

         try (FileReader fileReader = new FileReader(file4)) {
            JsonObject jsonObject3 = JsonParser.parseReader(fileReader).getAsJsonObject();
            this.loadFromJson(jsonObject3);
         }
      } catch (Exception exception2) {
         exception2.printStackTrace();
      }
   }

   public void invoke5(String string) {
      try {
         File file5 = new File(this.resolve5(), string + ".json");
         if (file5.exists()) {
            file5.delete();
         }
      } catch (Exception exception3) {
      }
   }

   public void invoke6(String string, String string2) {
      if (!string.equals(string2)) {
         try {
            File file6 = this.resolve5();
            File file7 = new File(file6, string + ".json");
            File file8 = new File(file6, string2 + ".json");
            if (file7.exists() && !file8.exists()) {
               file7.renameTo(file8);
            }
         } catch (Exception exception4) {
         }
      }
   }

   @Override
   public JsonObject toJson() {
      JsonObject jsonObject4 = super.toJson();
      JsonObject jsonObject5 = new JsonObject();
      JsonObject jsonObject6 = new JsonObject();

      for (Entry entry : VALUES_BY_KEY.entrySet()) {
         jsonObject6.addProperty((String)entry.getKey(), (Number)entry.getValue());
      }

      JsonObject jsonObject7 = new JsonObject();
      LinkedHashSet linkedHashSet = new LinkedHashSet();
      linkedHashSet.addAll(VALUES_BY_KEY_2.keySet());
      linkedHashSet.addAll(VALUES_BY_KEY_3.keySet());

      for (String text7 : (Set<String>)linkedHashSet) {
         int intValue4 = compute22(text7);
         int intValue5 = compute23(text7);
         if (intValue4 > 0 || intValue5 < 100) {
            JsonObject jsonObject8 = new JsonObject();
            jsonObject8.addProperty("min", intValue4);
            jsonObject8.addProperty("max", intValue5);
            jsonObject7.add(text7, jsonObject8);
         }
      }

      JsonObject jsonObject9 = new JsonObject();

      for (Entry entry2 : VALUES_BY_KEY_4.entrySet()) {
         if (entry2.getValue() != null && !((Set)entry2.getValue()).isEmpty()) {
            JsonArray jsonArray = new JsonArray();

            for (String text8 : (Set<String>)entry2.getValue()) {
               jsonArray.add(text8);
            }

            jsonObject9.add((String)entry2.getKey(), jsonArray);
         }
      }

      JsonArray jsonArray2 = new JsonArray();

      for (String text9 : ITEMS) {
         jsonArray2.add(text9);
      }

      JsonArray jsonArray3 = new JsonArray();

      for (String text10 : VALUES_3) {
         jsonArray3.add(text10);
      }

      JsonArray jsonArray4 = new JsonArray();

      for (String text11 : VALUES_BY_KEY_5.values()) {
         jsonArray4.add(text11);
      }

      JsonArray jsonArray5 = new JsonArray();

      for (AutoBuy.AutoBuyState autoBuyState5 : ITEMS_2) {
         JsonObject jsonObject10 = new JsonObject();
         jsonObject10.addProperty("original", autoBuyState5.text);
         jsonObject10.addProperty("clean", autoBuyState5.text2);
         jsonObject10.addProperty("qty", autoBuyState5.intValue);
         jsonObject10.addProperty("price", autoBuyState5.timestamp);
         jsonObject10.addProperty("time", autoBuyState5.timestamp2);
         jsonArray5.add(jsonObject10);
      }

      jsonObject5.add("Prices", jsonObject6);
      jsonObject5.add("DurabilityRanges", jsonObject7);
      jsonObject5.add("DisabledEnchantments", jsonObject9);
      jsonObject5.add("ParseItems", jsonArray2);
      jsonObject5.add("InactiveItems", jsonArray3);
      jsonObject5.add("IgnoredSellers", jsonArray4);
      jsonObject5.add("History", jsonArray5);
      jsonObject4.add("AutoBuyData", jsonObject5);
      return jsonObject4;
   }

   @Override
   public void loadFromJson(JsonObject jsonObject) {
      super.loadFromJson(jsonObject);
      if (!this.rezhimServera.options.contains(this.rezhimServera.value)) {
         this.rezhimServera.selectedIndex = 0;
         this.rezhimServera.value = this.rezhimServera.options.get(0);
      } else {
         this.rezhimServera.selectedIndex = this.rezhimServera.options.indexOf(this.rezhimServera.value);
      }

      if (jsonObject != null && jsonObject.has("AutoBuyData") && jsonObject.get("AutoBuyData").isJsonObject()) {
         JsonObject jsonObject11 = jsonObject.getAsJsonObject("AutoBuyData");
         VALUES_BY_KEY.clear();
         VALUES_BY_KEY_2.clear();
         VALUES_BY_KEY_3.clear();
         VALUES_BY_KEY_4.clear();
         ITEMS.clear();
         VALUES_3.clear();
         VALUES_BY_KEY_5.clear();
         ITEMS_2.clear();
         if (jsonObject11.has("Prices") && jsonObject11.get("Prices").isJsonObject()) {
            JsonObject jsonObject12 = jsonObject11.getAsJsonObject("Prices");

            for (String text12 : jsonObject12.keySet()) {
               try {
                  VALUES_BY_KEY.put(text12, jsonObject12.get(text12).getAsLong());
               } catch (Exception exception5) {
               }
            }
         }

         if (jsonObject11.has("DurabilityRanges") && jsonObject11.get("DurabilityRanges").isJsonObject()) {
            JsonObject jsonObject13 = jsonObject11.getAsJsonObject("DurabilityRanges");

            for (String text13 : jsonObject13.keySet()) {
               try {
                  JsonObject jsonObject14 = jsonObject13.getAsJsonObject(text13);
                  invoke46(text13, jsonObject14.has("min") ? jsonObject14.get("min").getAsInt() : 0, jsonObject14.has("max") ? jsonObject14.get("max").getAsInt() : 100);
               } catch (Exception exception6) {
               }
            }
         }

         if (jsonObject11.has("DurabilityThresholds") && jsonObject11.get("DurabilityThresholds").isJsonObject()) {
            JsonObject jsonObject15 = jsonObject11.getAsJsonObject("DurabilityThresholds");

            for (String text14 : jsonObject15.keySet()) {
               try {
                  invoke45(text14, jsonObject15.get(text14).getAsInt());
               } catch (Exception exception7) {
               }
            }
         }

         if (jsonObject11.has("DisabledEnchantments") && jsonObject11.get("DisabledEnchantments").isJsonObject()) {
            JsonObject jsonObject16 = jsonObject11.getAsJsonObject("DisabledEnchantments");

            for (String text15 : jsonObject16.keySet()) {
               try {
                  JsonArray jsonArray6 = jsonObject16.getAsJsonArray(text15);
                  LinkedHashSet linkedHashSet2 = new LinkedHashSet();

                  for (JsonElement jsonElement : jsonArray6) {
                     if (jsonElement.isJsonPrimitive()) {
                        linkedHashSet2.add(HolyWorldItemParser.resolve3(jsonElement.getAsString()));
                     }
                  }

                  if (!linkedHashSet2.isEmpty()) {
                     VALUES_BY_KEY_4.put(text15, linkedHashSet2);
                  }
               } catch (Exception exception8) {
               }
            }
         }

         if (jsonObject11.has("ParseItems") && jsonObject11.get("ParseItems").isJsonArray()) {
            for (JsonElement jsonElement2 : jsonObject11.getAsJsonArray("ParseItems")) {
               if (jsonElement2.isJsonPrimitive()) {
                  ITEMS.add(jsonElement2.getAsString());
               }
            }
         }

         if (jsonObject11.has("InactiveItems") && jsonObject11.get("InactiveItems").isJsonArray()) {
            for (JsonElement jsonElement3 : jsonObject11.getAsJsonArray("InactiveItems")) {
               if (jsonElement3.isJsonPrimitive()) {
                  VALUES_3.add(jsonElement3.getAsString());
               }
            }
         }

         if (jsonObject11.has("IgnoredSellers") && jsonObject11.get("IgnoredSellers").isJsonArray()) {
            for (JsonElement jsonElement4 : jsonObject11.getAsJsonArray("IgnoredSellers")) {
               if (jsonElement4.isJsonPrimitive()) {
                  check(jsonElement4.getAsString());
               }
            }
         }

         if (jsonObject11.has("History") && jsonObject11.get("History").isJsonArray()) {
            for (JsonElement jsonElement5 : jsonObject11.getAsJsonArray("History")) {
               if (jsonElement5.isJsonObject()) {
                  JsonObject jsonObject17 = jsonElement5.getAsJsonObject();
                  ITEMS_2.add(
                     new AutoBuy.AutoBuyState(
                        jsonObject17.get("original").getAsString(),
                        jsonObject17.get("clean").getAsString(),
                        jsonObject17.get("qty").getAsInt(),
                        jsonObject17.get("price").getAsLong(),
                        jsonObject17.get("time").getAsLong()
                     )
                  );
               }
            }
         }

         this.invoke49();
      }
   }

   @Override
   public void reset() {
      super.reset();
      VALUES_BY_KEY.clear();
      VALUES_BY_KEY_2.clear();
      VALUES_BY_KEY_3.clear();
      VALUES_BY_KEY_4.clear();
      ITEMS.clear();
      VALUES_3.clear();
      VALUES_BY_KEY_5.clear();
      ITEMS_2.clear();
      this.invoke49();
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.invoke();
      this.invoke21();
      this.invoke22();
      this.invoke30();
      this.invoke23();
      timestamp = System.currentTimeMillis();
      this.flag4 = false;
      timestamp2 = 0L;
      timestamp3 = 0L;
      timestamp4 = 0L;
      if (this.rezhimServera.is("FunTime")) {
         try {
            ServerStatsParser serverStatsParser = new ServerStatsParser();
            serverStatsParser.invoke2();
            String text16 = serverStatsParser.getText0();
            if (!text16.isEmpty() && !text16.equals("0")) {
               timestamp2 = Long.parseLong(text16);
               timestamp3 = timestamp2;
               this.flag4 = true;
            }
         } catch (Exception exception9) {
         }
      }

      this.dualTimer4.invoke();
      this.dualTimer9.invoke();
      this.dualTimer10.invoke();
      this.dualTimer11.invoke();
      this.flag5 = false;
      this.intValue2 = 0;
      this.text2 = "";
      this.timestamp5 = 0L;
      this.timestamp6 = 0L;
      this.flag10 = false;
      this.flag11 = false;
      this.flag13 = false;
      this.DynamicButtonSetting = -1;
      this.timestamp9 = 0L;
      this.timestamp10 = 0L;
      this.flag6 = false;
      this.flag7 = false;
      this.intValue3 = 0;
      this.flag9 = false;
      this.text3 = "";
      this.timestamp7 = 0L;
      this.timestamp8 = 0L;
      this.flag12 = false;
      this.invoke40();
      this.invoke41();
      this.invoke49();
      this.auctionClickTracker.invoke();
      this.flag19 = false;
      this.timestamp23 = 0L;
      this.timestamp24 = 0L;
      this.invoke9();
      FunTimeAuctionHelper.invoke();
      this.flag8 = this.autoParse.isEnabled();
   }

   private void invoke7() {
      if (!this.detektZamedleniyaAuka.isEnabled()) {
         this.auctionClickTracker.invoke3();
         this.flag19 = false;
      } else {
         this.auctionClickTracker.invoke8();
         long longValue5 = System.currentTimeMillis();
         boolean flag = this.auctionClickTracker.isFlag();
         if (flag && !this.flag19) {
            this.flag19 = true;
            this.timestamp24 = longValue5;
            if (ClientUtil.telegramNotifications.isEnabled()) {
               ru.metaculture.protection.TelegramApi.invoke2(
                  "[AutoBuy] Сервер замедлил аукцион: отклик ~"
                     + this.auctionClickTracker.getTimestamp2()
                     + "мс, норма ~"
                     + this.auctionClickTracker.compute3()
                     + "мс, пинг "
                     + this.resolve6()
               );
            }

            if (this.check4()) {
               this.invoke8();
            }
         } else if (!flag && this.flag19) {
            this.flag19 = false;
         }

         if (this.lagStatistikaVChat.isEnabled() && this.auctionClickTracker.getIntValue2() > 0 && longValue5 - this.timestamp23 >= 2000L) {
            this.timestamp23 = longValue5;
            ChatUtil.sendClientMessage("§7[AutoBuy] " + this.auctionClickTracker.getTimestamp());
         }
      }
   }

   private boolean check4() {
      return this.avtoFiksZamedleniya.isEnabled()
         && (this.rezhimServera.is("FunTime") || this.rezhimServera.is("HolyWorld"))
         && !flag
         && !flag2
         && !this.flag6
         && !this.autoParse.isEnabled()
         && !this.flag7
         && !this.check30()
         && !this.flag20;
   }

   private void invoke8() {
      long longValue6 = System.currentTimeMillis();
      this.intValue10 = longValue6 - this.timestamp26 <= 240000L ? Math.min(2, this.intValue10 + 1) : 0;
      this.timestamp26 = longValue6;
      this.auctionClickTracker.invoke2();
      this.flag19 = false;
      if (this.rezhimServera.is("FunTime") && this.intValue10 >= 1) {
         if (this.intValue10 >= 2) {
            this.invoke42();
         } else {
            this.compute30();
         }
      } else {
         this.flag20 = true;
         this.timestamp25 = longValue6 + ThreadLocalRandom.current().nextLong(4000L, 8001L);
         this.flag5 = false;
         this.flag10 = false;
         this.text2 = "";
         this.invoke40();
         if (CLIENT.player != null && CLIENT.currentScreen != null) {
            CLIENT.player.closeScreen();
         }
      }
   }

   private boolean check5() {
      if (!this.flag20) {
         return false;
      } else {
         if (CLIENT.player != null && CLIENT.currentScreen != null) {
            CLIENT.player.closeScreen();
         }

         if (System.currentTimeMillis() < this.timestamp25) {
            return true;
         } else {
            this.flag20 = false;
            this.timestamp25 = 0L;
            this.invoke57(0L, false);
            return true;
         }
      }
   }

   private void invoke9() {
      this.flag20 = false;
      this.timestamp25 = 0L;
      this.intValue10 = 0;
      this.timestamp26 = 0L;
   }

   private String resolve6() {
      int intValue6 = this.compute8();
      return intValue6 < 0 ? "?" : intValue6 + "мс";
   }

   private int compute8() {
      if (CLIENT.player != null && CLIENT.getNetworkHandler() != null) {
         PlayerListEntry playerListEntry = CLIENT.getNetworkHandler().getPlayerListEntry(CLIENT.player.getUuid());
         return playerListEntry == null ? -1 : playerListEntry.getLatency();
      } else {
         return -1;
      }
   }

   public boolean check6() {
      return this.detektZamedleniyaAuka.isEnabled() && this.auctionClickTracker.isFlag();
   }

   public AuctionClickTracker getAuctionClickTracker() {
      return this.auctionClickTracker;
   }

   private void invoke10() {
      if (this.rezhimServera.is("FunTime")) {
         try {
            long longValue7 = System.currentTimeMillis();
            if (longValue7 - this.timestamp10 < 1000L) {
               return;
            }

            this.timestamp10 = longValue7;
            ServerStatsParser serverStatsParser2 = new ServerStatsParser();
            serverStatsParser2.invoke2();
            String text17 = serverStatsParser2.getText0();
            if (text17.isEmpty() || text17.equals("0")) {
               return;
            }

            timestamp3 = Long.parseLong(text17);
            if (timestamp4 == 0L && timestamp2 > 0L && compute6() > 0L && timestamp3 >= timestamp2) {
               timestamp4 = System.currentTimeMillis();
            }
         } catch (Exception exception10) {
         }
      }
   }

   private void invoke11() {
      if (this.rezhimServera.is("FunTime")) {
         if (!this.flag4) {
            try {
               ServerStatsParser serverStatsParser3 = new ServerStatsParser();
               serverStatsParser3.invoke2();
               String text18 = serverStatsParser3.getText0();
               if (!text18.isEmpty() && !text18.equals("0")) {
                  timestamp2 = Long.parseLong(text18);
                  timestamp3 = timestamp2;
                  this.flag4 = true;
               }
            } catch (Exception exception11) {
            }
         } else {
            this.invoke10();
         }
      }
   }

   @Override
   public void onDisable() {
      boolean flag2 = this.autoParse.isEnabled();
      if (!flag2) {
         this.invoke63();
      }

      this.flag5 = false;
      this.intValue2 = 0;
      this.flag6 = false;
      this.text = "";
      this.text2 = "";
      this.timestamp5 = 0L;
      this.timestamp6 = 0L;
      this.flag10 = false;
      this.flag11 = false;
      this.flag13 = false;
      this.DynamicButtonSetting = -1;
      this.timestamp9 = 0L;
      this.invoke40();
      this.invoke41();
      this.auctionClickTracker.invoke3();
      this.flag19 = false;
      this.invoke9();
      this.flag4 = false;
      FunTimeAuctionHelper.invoke();
      this.invoke21();
      this.invoke22();
      this.invoke30();
      ReconnectCommand reconnectCommand = ReconnectCommand.getInstance();
      if (reconnectCommand != null) {
         reconnectCommand.invoke(false);
      }

      super.onDisable();
      if (flag2) {
         this.invoke65();
      }
   }

   private void invoke12() {
      ReconnectCommand reconnectCommand2 = ReconnectCommand.getInstance();
      if (reconnectCommand2 != null) {
         reconnectCommand2.invoke(this.enabled && this.svapAnarhii510Min.isEnabled() && this.rezhimServera.is("FunTime"));
      }
   }

   public static void invoke13() {
      AutoBuy autoBuy = instance;
      if (autoBuy != null) {
         autoBuy.invoke63();
         autoBuy.flag5 = false;
         autoBuy.intValue2 = 0;
         autoBuy.flag6 = false;
         autoBuy.text = "";
         autoBuy.text2 = "";
         autoBuy.timestamp5 = 0L;
         autoBuy.timestamp6 = 0L;
         autoBuy.flag10 = false;
         autoBuy.flag11 = false;
         autoBuy.flag13 = false;
         autoBuy.DynamicButtonSetting = -1;
         autoBuy.timestamp9 = 0L;
         autoBuy.flag7 = false;
         autoBuy.intValue3 = 0;
         autoBuy.flag9 = false;
         autoBuy.text3 = "";
         autoBuy.timestamp7 = 0L;
         autoBuy.timestamp8 = 0L;
         autoBuy.flag12 = false;
         autoBuy.invoke21();
         autoBuy.invoke22();
         autoBuy.invoke30();
         autoBuy.invoke9();
      }

      flag = false;
      flag2 = false;
      flag3 = false;
      FunTimeAuctionHelper.invoke();
   }

   @EventHandler
   public void onRawInput(RawInputEvent rawInputEvent) {
      if (this.enabled) {
         if (rawInputEvent.getAction() == 1 && rawInputEvent.getKeyCode() == this.menuKey.getKeyCode() && CLIENT.currentScreen == null) {
            CLIENT.setScreen(new AutoBuyScreen());
            rawInputEvent.invalidate();
         }
      }
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player != null && CLIENT.world != null && CLIENT.interactionManager != null) {
         this.invoke12();
         if (!this.enabled) {
            this.invoke64();
            this.invoke28();
         } else {
            this.invoke28();
            FunTimeAuctionHelper.invoke2();
            this.invoke7();
            if (!this.check9()) {
               if (!this.autoReparse.isEnabled() && !this.flag9) {
                  this.dualTimer9.invoke();
               }

               this.invoke61();
               if (this.flag6) {
                  if (this.dualTimer8.check5(1500L)) {
                     CLIENT.player.networkHandler.sendChatCommand("an" + this.text);
                     this.flag6 = false;
                     this.invoke54(this.text);
                  }
               } else if (!this.rezhimServera.is("FunTime") && !this.rezhimServera.is("HolyWorld")
                  || this.autoParse.isEnabled()
                  || !this.flag5
                  || !this.check54()) {
                  if (!flag && !flag2) {
                     if (!this.rezhimServera.is("FunTime") || this.autoParse.isEnabled() || !this.check62()) {
                        this.invoke11();
                        boolean flag3 = this.autoParse.isEnabled();
                        if (flag3 && !this.flag8) {
                           flag3 = this.check16(false);
                        } else if (!flag3 && this.flag8) {
                           this.invoke38();
                        }

                        this.flag8 = flag3;
                        if (flag3 && !this.items.isEmpty()) {
                           this.invoke34();
                           this.invoke28();
                        } else if (!this.check5()) {
                           if (this.check30()) {
                              this.invoke43();
                           } else {
                              if (this.rezhimServera.is("FunTime") && !this.autoParse.isEnabled()) {
                                 if (this.check17()) {
                                    return;
                                 }

                                 if (this.dualTimer4.check5(80000L)) {
                                    this.compute30();
                                    return;
                                 }

                                 if (this.flag5 && this.check54()) {
                                    return;
                                 }

                                 if (this.check56()) {
                                    return;
                                 }
                              }

                              if (CLIENT.currentScreen instanceof GenericContainerScreen genericContainerScreen2) {
                                 GenericContainerScreenHandler genericContainerScreenHandler = (GenericContainerScreenHandler)genericContainerScreen2.getScreenHandler();
                                 if (this.check19(genericContainerScreen2)) {
                                    if (this.dualTimer3.check5((long)this.kdPodtverzhdeniyaMs.getValue())) {
                                       int intValue7 = this.compute12(genericContainerScreenHandler);
                                       if (intValue7 != -1 && this.check32(genericContainerScreen2)) {
                                          CLIENT.interactionManager.clickSlot(genericContainerScreenHandler.syncId, intValue7, 0, SlotActionType.PICKUP, CLIENT.player);
                                       } else {
                                          this.invoke44(genericContainerScreenHandler);
                                       }

                                       this.dualTimer3.invoke();
                                    }

                                    return;
                                 }

                                 if (this.check21(genericContainerScreen2) && !this.flag7) {
                                    if (this.rezhimServera.is("HolyWorld")) {
                                       this.invoke14(genericContainerScreen2);
                                       return;
                                    }

                                    boolean flag4 = false;

                                    for (int intValue8 = 0; intValue8 < 45; intValue8++) {
                                       Slot slot2 = genericContainerScreenHandler.getSlot(intValue8);
                                       if (this.check25(slot2)) {
                                          String text19 = this.resolve14(slot2);
                                          if (text19 != null) {
                                             flag4 = true;
                                             if (this.dualTimer2.check5(this.compute2())) {
                                                if (!this.rezhimServera.is("FunTime") && !this.rezhimServera.is("SpookyTime")) {
                                                   this.invoke39(text19);
                                                   CLIENT.interactionManager.clickSlot(genericContainerScreenHandler.syncId, intValue8, 0, SlotActionType.PICKUP, CLIENT.player);
                                                } else {
                                                   this.timestamp19 = System.currentTimeMillis();
                                                   CLIENT.interactionManager
                                                      .clickSlot(genericContainerScreenHandler.syncId, intValue8, 0, SlotActionType.QUICK_MOVE, CLIENT.player);
                                                }

                                                this.dualTimer2.invoke();
                                                this.dualTimer3.invoke();
                                                this.invoke();
                                                return;
                                             }
                                             break;
                                          }
                                       }
                                    }

                                    if (!flag4 && this.dualTimer.check5(this.compute()) && genericContainerScreenHandler.slots.size() > 49) {
                                       this.auctionClickTracker.invoke4(genericContainerScreenHandler.syncId);
                                       CLIENT.interactionManager.clickSlot(genericContainerScreenHandler.syncId, 49, 0, SlotActionType.PICKUP, CLIENT.player);
                                       this.dualTimer.invoke();
                                       this.invoke();
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      } else {
         this.invoke30();
      }
   }

   @EventHandler
   public void onCameraRotation(CameraRotationEvent cameraRotationEvent) {
      if (CLIENT.player != null) {
         if (this.enabled && this.rezhimServera.is("HolyWorld") && this.intValue5 != 0) {
            this.invoke25(cameraRotationEvent);
         } else {
            if (this.check11()) {
               this.invoke24(cameraRotationEvent);
            }
         }
      }
   }

   private void invoke14(GenericContainerScreen genericContainerScreen) {
      ScreenHandler screenHandler2 = genericContainerScreen.getScreenHandler();
      boolean flag5 = false;
      int intValue9 = Math.min(45, screenHandler2.slots.size());

      for (int intValue10 = 0; intValue10 < intValue9; intValue10++) {
         Slot slot3 = screenHandler2.getSlot(intValue10);
         if (this.check25(slot3)) {
            String text20 = this.resolve14(slot3);
            if (text20 != null) {
               flag5 = true;
               if (this.check7(intValue10, text20) && this.dualTimer2.check5(this.compute2())) {
                  this.invoke17();
                  this.invoke39(text20);
                  CLIENT.interactionManager.clickSlot(screenHandler2.syncId, intValue10, 0, SlotActionType.PICKUP, CLIENT.player);
                  this.invoke15();
                  this.dualTimer2.invoke();
                  this.dualTimer3.invoke();
                  this.invoke();
                  return;
               }
               break;
            }
         }
      }

      if (!flag5 && this.dualTimer.check5(this.compute())) {
         int intValue11 = this.compute9(screenHandler2);
         if (intValue11 != -1) {
            this.auctionClickTracker.invoke4(screenHandler2.syncId);
            CLIENT.interactionManager.clickSlot(screenHandler2.syncId, intValue11, 0, SlotActionType.PICKUP, CLIENT.player);
            this.invoke15();
            this.dualTimer.invoke();
            this.invoke();
         }
      } else if (!flag5) {
         this.invoke15();
      }
   }

   private boolean check7(int i, String string) {
      long longValue8 = System.currentTimeMillis();
      if (this.intValue6 == i && Objects.equals(this.text5, string)) {
         return longValue8 - this.timestamp17 >= 90L;
      } else {
         this.intValue6 = i;
         this.text5 = string;
         this.timestamp17 = longValue8;
         return false;
      }
   }

   private void invoke15() {
      this.intValue6 = -1;
      this.text5 = "";
      this.timestamp17 = 0L;
   }

   private int compute9(ScreenHandler screenHandler) {
      int intValue12 = this.compute13(screenHandler);
      if (intValue12 <= 0) {
         intValue12 = Math.min(54, screenHandler.slots.size());
      }

      for (int intValue13 = Math.min(45, intValue12); intValue13 < intValue12; intValue13++) {
         if (this.check8(screenHandler.getSlot(intValue13).getStack())) {
            return intValue13;
         }
      }

      for (int intValue14 = 0; intValue14 < intValue12; intValue14++) {
         if (this.check8(screenHandler.getSlot(intValue14).getStack())) {
            return intValue14;
         }
      }

      return -1;
   }

   private boolean check8(ItemStack itemStack) {
      return itemStack != null && !itemStack.isEmpty() && itemStack.isOf(Items.EMERALD)
         ? this.resolve21(this.resolve20(itemStack)).contains("обновитьаукцион")
         : false;
   }

   private boolean check9() {
      if (!this.rezhimServera.is("HolyWorld")) {
         this.invoke21();
         return false;
      } else {
         long longValue9 = System.currentTimeMillis();
         if (this.intValue5 != 0 && longValue9 >= this.timestamp13) {
            this.invoke20();
            return true;
         } else if (this.intValue5 == 3 && this.flag15) {
            return true;
         } else {
            if (this.check10(longValue9)) {
               this.invoke16();
               this.invoke23();
            }

            return false;
         }
      }
   }

   private boolean check10(long l) {
      if (this.intValue5 != 0) {
         return false;
      } else if (l < this.SpacerSetting) {
         return false;
      } else if (!flag && !flag2 && !this.flag6 && !this.autoParse.isEnabled() && !this.flag7 && !this.flag5) {
         return CLIENT.currentScreen instanceof GenericContainerScreen genericContainerScreen3 ? this.check21(genericContainerScreen3) : false;
      } else {
         return false;
      }
   }

   private void invoke16() {
      double doubleValue = ThreadLocalRandom.current().nextDouble();
      if (doubleValue < 0.42) {
         this.invoke19(4, ThreadLocalRandom.current().nextLong(650L, 2200L));
      } else if (doubleValue < 0.7) {
         this.invoke19(1, ThreadLocalRandom.current().nextLong(1000L, 2800L));
      } else {
         this.invoke19(2, ThreadLocalRandom.current().nextLong(1200L, 3000L));
      }
   }

   private void invoke17() {
      if (this.intValue5 == 0 && CLIENT.player != null) {
         this.invoke19(2, ThreadLocalRandom.current().nextLong(900L, 1501L));
      }
   }

   private void invoke18(boolean bl) {
      boolean flag6 = !bl || FunTimeAuctionHelper.isFlag();
      this.flag15 = flag6;
      this.flag16 = bl;
      if (CLIENT.player != null) {
         this.invoke19(3, ThreadLocalRandom.current().nextLong(900L, 1601L));
      } else if (flag6) {
         FunTimeAuctionHelper.invoke5(bl);
         return;
      }

      if (!flag6) {
         FunTimeAuctionHelper.invoke5(true);
      }
   }

   private void invoke19(int i, long l) {
      this.intValue5 = i;
      this.FoundryShaderSetting = System.currentTimeMillis();
      this.timestamp13 = this.FoundryShaderSetting + l;
      this.timestamp14 = 0L;
      this.floatValue = CLIENT.player.getYaw();
      this.floatValue2 = CLIENT.player.getPitch();
      this.floatValue3 = (float)ThreadLocalRandom.current().nextDouble(0.0, Math.PI * 2);
      this.floatValue4 = ThreadLocalRandom.current().nextBoolean() ? 1.0F : -1.0F;
      this.floatValue9 = 1.0F;
      this.floatValue10 = (float)ThreadLocalRandom.current().nextDouble(0.06, 0.32);
      if (i == 4) {
         int intValue15 = ThreadLocalRandom.current().nextInt(1, 4);
         this.floatValue5 = 360.0F * intValue15 + (float)ThreadLocalRandom.current().nextDouble(-90.0, 90.0);
         this.floatValue6 = (float)ThreadLocalRandom.current().nextDouble(8.0, 45.0);
         this.floatValue7 = 0.0F;
         this.floatValue8 = (float)ThreadLocalRandom.current().nextDouble(-15.0, 15.0);
         this.floatValue9 = (float)ThreadLocalRandom.current().nextDouble(1.0, 4.0);
      } else if (i == 1) {
         this.floatValue5 = (float)ThreadLocalRandom.current().nextDouble(18.0, 55.0);
         this.floatValue6 = (float)ThreadLocalRandom.current().nextDouble(5.0, 18.0);
         this.floatValue7 = this.floatValue4 * (float)ThreadLocalRandom.current().nextDouble(8.0, 40.0);
         this.floatValue8 = (float)ThreadLocalRandom.current().nextDouble(-6.0, 6.0);
      } else if (i == 2) {
         this.floatValue5 = (float)ThreadLocalRandom.current().nextDouble(8.0, 32.0);
         this.floatValue6 = (float)ThreadLocalRandom.current().nextDouble(3.0, 13.0);
         this.floatValue7 = this.floatValue4 * (float)ThreadLocalRandom.current().nextDouble(4.0, 20.0);
         this.floatValue8 = (float)ThreadLocalRandom.current().nextDouble(-5.0, 5.0);
      } else {
         this.floatValue5 = (float)ThreadLocalRandom.current().nextDouble(12.0, 40.0);
         this.floatValue6 = (float)ThreadLocalRandom.current().nextDouble(-8.0, 8.0);
         this.floatValue7 = this.floatValue4 * (float)ThreadLocalRandom.current().nextDouble(10.0, 30.0);
         this.floatValue8 = (float)ThreadLocalRandom.current().nextDouble(-6.0, 6.0);
      }
   }

   private void invoke20() {
      boolean flag7 = this.flag14;
      boolean flag8 = this.flag15;
      boolean flag9 = this.flag16;
      this.invoke21();
      if (flag8) {
         FunTimeAuctionHelper.invoke5(flag9);
      } else {
         if (flag7 && CLIENT.player != null) {
            CLIENT.player.networkHandler.sendChatCommand("ah");
         }
      }
   }

   private void invoke21() {
      this.intValue5 = 0;
      this.FoundryShaderSetting = 0L;
      this.timestamp13 = 0L;
      this.timestamp14 = 0L;
      this.flag14 = false;
      this.flag15 = false;
      this.flag16 = false;
      this.floatValue7 = 0.0F;
      this.floatValue8 = 0.0F;
      this.invoke15();
   }

   private void invoke22() {
      this.timestamp15 = 0L;
      this.floatValue11 = 0.0F;
   }

   private void invoke23() {
      this.SpacerSetting = System.currentTimeMillis() + ThreadLocalRandom.current().nextLong(1200L, 4501L);
   }

   private boolean check11() {
      if (!this.rezhimServera.is("FunTime") || CLIENT.world == null || CLIENT.player == null) {
         this.invoke22();
         return false;
      } else if (!this.enabled && !this.autoParse.isEnabled() && !this.flag7 && !this.flag9) {
         this.invoke22();
         return false;
      } else {
         return true;
      }
   }

   private void invoke24(CameraRotationEvent cameraRotationEvent2) {
      float floatValue = this.measure2();
      this.floatValue11 += 0.185F * floatValue;
      if (this.floatValue11 > (float) (Math.PI * 2)) {
         this.floatValue11 = this.floatValue11 - (float) (Math.PI * 2) * (float)Math.floor(this.floatValue11 / (float) (Math.PI * 2));
      }

      float floatValue2 = this.floatValue11;
      float floatValue3 = (float)Math.sin(floatValue2) * 0.82F + (float)Math.sin(floatValue2 * 2.25F + 0.75F) * 0.16F + (float)Math.cos(floatValue2 * 2.35F) * 0.06F;
      float floatValue4 = (float)Math.cos(floatValue2 * 1.18F + 0.45F) * 0.28F + (float)Math.sin(floatValue2 * 2.05F) * 0.07F;
      cameraRotationEvent2.setFloatValue(cameraRotationEvent2.getFloatValue() + floatValue3);
      cameraRotationEvent2.setFloatValue2(MathHelper.clamp(cameraRotationEvent2.getFloatValue2() + floatValue4, -89.0F, 89.0F));
   }

   private void invoke25(CameraRotationEvent cameraRotationEvent3) {
      long longValue10 = System.currentTimeMillis();
      float floatValue5 = Math.max(1.0F, (float)(this.timestamp13 - this.FoundryShaderSetting));
      float floatValue6 = MathHelper.clamp((float)(longValue10 - this.FoundryShaderSetting) / floatValue5, 0.0F, 1.0F);
      float floatValue7 = floatValue6 * floatValue6 * (3.0F - 2.0F * floatValue6);
      if (this.intValue5 == 4) {
         float floatValue8 = this.floatValue + this.floatValue4 * this.floatValue5 * floatValue7;
         float floatValue9 = this.floatValue2
            + (float)Math.sin(this.floatValue3 + floatValue7 * (float) (Math.PI * 2) * this.floatValue9) * this.floatValue6
            + this.floatValue8 * floatValue7;
         this.invoke27(floatValue8, floatValue9, cameraRotationEvent3);
      } else {
         float floatValue10 = this.floatValue;
         float floatValue11 = this.floatValue2;
         if (this.intValue5 == 1) {
            float floatValue12 = this.floatValue3 + this.floatValue4 * floatValue7 * (float) (Math.PI * 11.0 / 5.0);
            floatValue10 += (float)Math.sin(floatValue12) * this.floatValue5 + this.floatValue4 * floatValue7 * 4.0F;
            floatValue11 += (float)Math.cos(floatValue12 * 0.85F) * this.floatValue6;
         } else if (this.intValue5 == 2) {
            float floatValue13 = this.floatValue3 + floatValue7 * 5.3407073F;
            floatValue10 += (float)Math.sin(floatValue13) * this.floatValue5;
            floatValue11 += (float)Math.sin(floatValue13 * 0.55F) * this.floatValue6;
         } else if (this.intValue5 == 3) {
            floatValue10 += this.floatValue4 * this.floatValue5 * floatValue7 + (float)Math.sin(this.floatValue3 + floatValue7 * Math.PI) * 1.8F;
            floatValue11 += this.floatValue6 * floatValue7;
         }

         floatValue10 += this.floatValue7 * floatValue7;
         floatValue11 += this.floatValue8 * floatValue7;
         this.invoke26(floatValue10, floatValue11, cameraRotationEvent3);
      }
   }

   private void invoke26(float f, float g, CameraRotationEvent cameraRotationEvent4) {
      float floatValue14 = CLIENT.player.getYaw();
      float floatValue15 = CLIENT.player.getPitch();
      float floatValue16 = this.measure();
      float floatValue17 = this.floatValue10;
      float floatValue18 = 1.0F - (float)Math.pow(1.0F - floatValue17, floatValue16);
      float floatValue19 = floatValue14 + MathHelper.wrapDegrees(f - floatValue14) * floatValue18;
      float floatValue20 = floatValue15 + (MathHelper.clamp(g, -89.0F, 89.0F) - floatValue15) * floatValue18;
      CLIENT.player.setYaw(floatValue19);
      CLIENT.player.setPitch(floatValue20);
      CLIENT.player.headYaw = floatValue19;
      cameraRotationEvent4.setFloatValue(floatValue19);
      cameraRotationEvent4.setFloatValue2(floatValue20);
   }

   private void invoke27(float f, float g, CameraRotationEvent cameraRotationEvent5) {
      float floatValue21 = MathHelper.clamp(g, -89.0F, 89.0F);
      CLIENT.player.setYaw(f);
      CLIENT.player.setPitch(floatValue21);
      CLIENT.player.headYaw = f;
      cameraRotationEvent5.setFloatValue(f);
      cameraRotationEvent5.setFloatValue2(floatValue21);
   }

   private float measure() {
      long longValue11 = System.nanoTime();
      if (this.timestamp14 == 0L) {
         this.timestamp14 = longValue11;
         return 1.0F;
      } else {
         float floatValue22 = (float)(longValue11 - this.timestamp14) / 1.6666667E7F;
         this.timestamp14 = longValue11;
         return MathHelper.clamp(floatValue22, 0.25F, 4.0F);
      }
   }

   private float measure2() {
      long longValue12 = System.nanoTime();
      if (this.timestamp15 == 0L) {
         this.timestamp15 = longValue12;
         this.floatValue11 = (float)ThreadLocalRandom.current().nextDouble(0.0, (float) (Math.PI * 2));
         return 1.0F;
      } else {
         float floatValue23 = (float)(longValue12 - this.timestamp15) / 1.6666667E7F;
         this.timestamp15 = longValue12;
         return MathHelper.clamp(floatValue23, 0.25F, 4.0F);
      }
   }

   private void invoke28() {
      if (this.check12()) {
         this.invoke31();
      } else if (!this.check13()) {
         this.invoke30();
      } else {
         if (!this.flag17) {
            this.flag17 = true;
            this.timestamp16 = System.currentTimeMillis();
            this.doubleValue = CLIENT.player.getX();
            this.doubleValue2 = CLIENT.player.getZ();
         }

         long longValue13 = Math.max(0L, System.currentTimeMillis() - this.timestamp16);
         boolean flag10 = (longValue13 / 75L & 1L) == 0L;
         Boolean booleanValue = this.resolve7(flag10);
         if (booleanValue == null) {
            this.invoke31();
         } else {
            this.invoke29(booleanValue, !booleanValue);
         }
      }
   }

   private boolean check12() {
      return this.rezhimServera.is("FunTime")
         && CLIENT.world != null
         && CLIENT.player != null
         && CLIENT.currentScreen != null
         && (this.autoParse.isEnabled() || this.flag7 || this.flag9);
   }

   private boolean check13() {
      return this.rezhimServera.is("FunTime")
         && CLIENT.world != null
         && CLIENT.player != null
         && CLIENT.currentScreen == null
         && (
            this.autoParse.isEnabled()
               || this.flag7
               || this.flag9
               || this.check30()
               || this.flag20 && this.enabled
         )
         && !flag
         && !flag2
         && !this.flag6;
   }

   private void invoke29(boolean bl, boolean bl2) {
      if (CLIENT.options != null && CLIENT.player != null) {
         CLIENT.options.forwardKey.setPressed(false);
         CLIENT.options.backKey.setPressed(false);
         CLIENT.options.leftKey.setPressed(bl);
         CLIENT.options.rightKey.setPressed(bl2);
         this.invoke32(false, false, bl, bl2);
      }
   }

   private Boolean resolve7(boolean bl) {
      if (CLIENT.player == null) {
         return bl;
      } else {
         double doubleValue2 = CLIENT.player.getX() - this.doubleValue;
         double doubleValue3 = CLIENT.player.getZ() - this.doubleValue2;
         double doubleValue4 = doubleValue2 * doubleValue2 + doubleValue3 * doubleValue3;
         if (doubleValue4 <= 1.0) {
            return bl;
         } else {
            double doubleValue5 = Math.toRadians(CLIENT.player.getYaw());
            double doubleValue6 = Math.cos(doubleValue5);
            double doubleValue7 = Math.sin(doubleValue5);
            double doubleValue8 = -doubleValue2;
            double doubleValue9 = -doubleValue3;
            double doubleValue10 = doubleValue6 * doubleValue8 + doubleValue7 * doubleValue9;
            if (Math.abs(doubleValue10) > 0.0025) {
               return doubleValue10 > 0.0;
            } else {
               return doubleValue4 >= 4.0 ? null : bl;
            }
         }
      }
   }

   private void invoke30() {
      if (this.flag17) {
         this.flag17 = false;
         this.timestamp16 = 0L;
         this.doubleValue = 0.0;
         this.doubleValue2 = 0.0;
         if (CLIENT.options != null) {
            this.invoke33(CLIENT.options.forwardKey);
            this.invoke33(CLIENT.options.backKey);
            this.invoke33(CLIENT.options.leftKey);
            this.invoke33(CLIENT.options.rightKey);
            this.invoke32(
               CLIENT.options.forwardKey.isPressed(),
               CLIENT.options.backKey.isPressed(),
               CLIENT.options.leftKey.isPressed(),
               CLIENT.options.rightKey.isPressed()
            );
         }
      }
   }

   private void invoke31() {
      if (CLIENT.options != null) {
         CLIENT.options.forwardKey.setPressed(false);
         CLIENT.options.backKey.setPressed(false);
         CLIENT.options.leftKey.setPressed(false);
         CLIENT.options.rightKey.setPressed(false);
         this.invoke32(false, false, false, false);
      }
   }

   private void invoke32(boolean bl, boolean bl2, boolean bl3, boolean bl4) {
      if (CLIENT.player != null && CLIENT.player.input != null && CLIENT.player.input.playerInput != null) {
         PlayerInput playerInput = CLIENT.player.input.playerInput;
         CLIENT.player.input.playerInput = new PlayerInput(bl, bl2, bl3, bl4, playerInput.jump(), playerInput.sneak(), playerInput.sprint());
      }
   }

   private void invoke33(KeyBinding keyBinding) {
      if (keyBinding != null) {
         keyBinding.setPressed(this.check14(keyBinding));
      }
   }

   private boolean check14(KeyBinding keyBinding) {
      return CLIENT.getWindow() != null
         && keyBinding != null
         && InputUtil.isKeyPressed(CLIENT.getWindow().getHandle(), keyBinding.getDefaultKey().getCode());
   }

   public void invoke34() {
      if (!this.autoParse.isEnabled()) {
         this.invoke63();
      } else if (!this.items.isEmpty()) {
         if (this.intValue3 >= this.items.size()) {
            this.invoke37();
         } else {
            if (!this.flag7) {
               if (!this.check18()) {
                  return;
               }

               if (this.dualTimer6.check5(1000L) && CLIENT.player != null) {
                  String text21 = this.items.get(this.intValue3);
                  this.invoke35(text21);
                  String text22 = this.resolve9(text21);
                  CLIENT.player.networkHandler.sendChatCommand("ah search " + text22);
                  this.flag7 = true;
                  this.dualTimer7.invoke();
               }
            } else {
               if (!this.dualTimer7.check5(600L)) {
                  return;
               }

               Screen screen = CLIENT.currentScreen;
               if (!(screen instanceof GenericContainerScreen) && this.dualTimer7.check5(2500L)) {
                  String text23 = this.items.get(this.intValue3);
                  boolean flag11 = this.check15(text23, this.resolve8(text23), "не открылся результат поиска");
                  if (flag11) {
                     this.intValue3++;
                  }

                  if (!flag11) {
                     ChatUtil.sendClientMessage("§e[AutoParse] Ожидание кулдауна, повторный поиск: " + this.resolve8(text23));
                  }

                  this.flag7 = false;
                  this.dualTimer6.invoke();
                  if (CLIENT.player != null && CLIENT.currentScreen != null) {
                     CLIENT.player.closeScreen();
                  }

                  return;
               }

               if (screen instanceof GenericContainerScreen genericContainerScreen4) {
                  String text24 = this.items.get(this.intValue3);
                  if (this.check21(genericContainerScreen4) || this.check24(genericContainerScreen4, text24)) {
                     String text25 = this.resolve8(text24);
                     int intValue16 = (int)this.parsSkidka.getValue();
                     AutoBuy.AutoBuyPriceData2 autoBuyPriceData2 = this.resolve11(genericContainerScreen4, text24);
                     boolean flag12 = false;
                     if (autoBuyPriceData2 != null) {
                        long longValue14 = this.compute11(autoBuyPriceData2.unitPrice(), intValue16);
                        VALUES_BY_KEY.put(text24, longValue14);
                        this.invoke36();
                        flag12 = true;
                        ChatUtil.sendClientMessage(
                           "§d[AutoParse] §f"
                              + text25
                              + ": мин. за 1 шт. §e"
                              + autoBuyPriceData2.unitPrice()
                              + "$ §7(лот "
                              + autoBuyPriceData2.lotPrice()
                              + "$ x"
                              + autoBuyPriceData2.count()
                              + ") §f(-"
                              + intValue16
                              + "%) -> ставим §a"
                              + longValue14
                              + "$"
                        );
                     } else {
                        flag12 = this.check15(text24, text25, "не найден на странице");
                        if (!flag12) {
                           ChatUtil.sendClientMessage("§c[AutoParse] §f" + text25 + " не найден на странице.");
                        }
                     }

                     this.flag7 = false;
                     if (flag12) {
                        this.intValue3++;
                     }

                     this.dualTimer6.invoke();
                     if (CLIENT.player != null) {
                        CLIENT.player.closeScreen();
                     }
                  }
               }
            }
         }
      }
   }

   private void invoke35(String string) {
      if (!Objects.equals(this.text4, string)) {
         this.text4 = string == null ? "" : string;
         this.intValue4 = 0;
      }
   }

   private boolean check15(String string, String string2, String string3) {
      this.invoke35(string);
      if (this.intValue4 >= 3) {
         ChatUtil.sendClientMessage("§c[AutoParse] §f" + string2 + " пропущен: " + string3 + " после 3 повторных поисков.");
         this.invoke36();
         return true;
      } else {
         this.intValue4++;
         ChatUtil.sendClientMessage("§e[AutoParse] §f" + string2 + ": " + string3 + ", повторный поиск " + this.intValue4 + "/3.");
         return false;
      }
   }

   private void invoke36() {
      this.text4 = "";
      this.intValue4 = 0;
   }

   private boolean check16(boolean bl) {
      String text26 = this.rezhimServera.getValue();
      LinkedHashSet linkedHashSet3 = new LinkedHashSet();

      for (String text27 : ITEMS) {
         if (this.check38(text27, text26)) {
            linkedHashSet3.add(text27);
         }
      }

      for (String text28 : VALUES_BY_KEY.keySet()) {
         if (this.check38(text28, text26)) {
            linkedHashSet3.add(text28);
         }
      }

      this.items.clear();
      this.items.addAll(linkedHashSet3);
      this.intValue3 = 0;
      this.flag7 = false;
      this.text3 = "";
      this.timestamp7 = 0L;
      this.timestamp8 = 0L;
      this.flag12 = false;
      this.invoke36();
      this.dualTimer7.invoke();
      if (this.items.isEmpty()) {
         ChatUtil.sendClientMessage("§c[AutoBuy] Список предметов для парсинга пуст!");
         this.autoParse.setValue(false);
         this.flag9 = false;
         return false;
      } else {
         this.flag9 = bl;
         this.autoParse.setValue(true);
         this.flag8 = true;
         this.dualTimer6.invoke();
         ChatUtil.sendClientMessage((bl ? "§e[AutoBuy] Авто-репарс: " : "§a[AutoBuy] ") + "Начинаем парсинг " + this.items.size() + " предметов...");
         return true;
      }
   }

   private void invoke37() {
      this.autoParse.setValue(false);
      this.flag8 = false;
      this.flag7 = false;
      this.text3 = "";
      this.timestamp7 = 0L;
      this.timestamp8 = 0L;
      this.invoke36();
      this.invoke30();
      this.invoke66();
      if (this.flag9) {
         this.flag9 = false;
         this.dualTimer9.invoke();
         ChatUtil.sendClientMessage("§a[AutoBuy] Авто-репарс завершён. Меняем анархию и возвращаем покупки.");
         this.compute31(true);
      } else {
         ChatUtil.sendClientMessage("§a[AutoBuy] Авто-парс успешно завершён! Цены обновлены.");
      }
   }

   private void invoke38() {
      this.flag7 = false;
      this.flag9 = false;
      this.text3 = "";
      this.timestamp7 = 0L;
      this.timestamp8 = 0L;
      this.invoke36();
      this.dualTimer6.invoke();
      this.dualTimer7.invoke();
      this.dualTimer9.invoke();
      this.invoke30();
   }

   private boolean check17() {
      if (!this.autoReparse.isEnabled() || !this.rezhimServera.is("FunTime")) {
         return false;
      } else if (!this.autoParse.isEnabled() && !this.flag9 && !this.flag5 && !this.flag6) {
         if (CLIENT.currentScreen instanceof GenericContainerScreen genericContainerScreen5 && this.check19(genericContainerScreen5)) {
            return false;
         } else if (VALUES_BY_KEY.isEmpty()) {
            this.dualTimer9.invoke();
            return false;
         } else if (!this.dualTimer9.check5(this.compute10())) {
            return false;
         } else if (!this.check16(true)) {
            this.dualTimer9.invoke();
            return false;
         } else {
            int intValue17 = this.compute31(false);
            if (intValue17 == -1) {
               this.invoke63();
               this.dualTimer9.invoke();
               return false;
            } else {
               this.text3 = String.valueOf(intValue17);
               this.timestamp7 = System.currentTimeMillis();
               this.timestamp8 = System.currentTimeMillis() + 2500L;
               this.flag12 = false;
               return true;
            }
         }
      } else {
         return false;
      }
   }

   private long compute10() {
      return Math.max(1L, (long)this.reparseKazhdyeMin.getValue()) * 60000L;
   }

   private boolean check18() {
      long longValue15 = System.currentTimeMillis();
      if (!this.text3.isEmpty()) {
         long longValue16 = longValue15 - this.timestamp7;
         String text29 = this.resolve29();
         boolean flag13 = this.text3.equals(text29) && longValue16 >= 2500L;
         if (!flag13 && !this.flag12 && longValue16 >= 4500L && CLIENT.player != null) {
            CLIENT.player.networkHandler.sendChatCommand("an" + this.text3);
            this.flag12 = true;
            this.timestamp7 = longValue15;
            return false;
         }

         longValue16 = longValue15 - this.timestamp7;
         boolean flag14 = longValue16 >= 12000L;
         if (!flag13 && !flag14) {
            return false;
         }

         this.text3 = "";
         this.flag12 = false;
      }

      return longValue15 >= this.timestamp8;
   }

   private long compute11(long l, int i) {
      double doubleValue11 = i / 100.0;
      long longValue17 = (long)(l * doubleValue11);
      return Math.max(1L, l - longValue17);
   }

   private String resolve8(String string) {
      if (HolyWorldItemParser.check2(string)) {
         return HolyWorldItemParser.resolve2(string);
      } else {
         if (string != null && string.startsWith("minecraft:")) {
            Identifier identifier = Identifier.tryParse(string);
            if (identifier != null) {
               Item item2 = (Item)Registries.ITEM.get(identifier);
               if (item2 != Items.AIR) {
                  return item2.getDefaultStack().getName().getString();
               }
            }
         }

         return string;
      }
   }

   private String resolve9(String string) {
      if (string == null) {
         return "";
      } else {
         return switch (string) {
            case "Опыт 15" -> "Опыт с уровнем 15";
            case "Опыт 30" -> "Опыт с уровнем 30";
            case "Опыт 45" -> "Опыт с уровнем 45";
            case "Опыт 50" -> "Опыт с уровнем 50";
            default -> this.resolve8(string);
         };
      }
   }

   private boolean check19(GenericContainerScreen genericContainerScreen) {
      if (genericContainerScreen == null) {
         return false;
      } else {
         String text30 = this.resolve10(genericContainerScreen.getTitle().getString());
         if (text30.contains("подтверждение покупки")) {
            return this.compute12(genericContainerScreen.getScreenHandler()) != -1;
         } else {
            return !this.check31() ? false : this.check20(genericContainerScreen.getScreenHandler());
         }
      }
   }

   private boolean check20(ScreenHandler screenHandler) {
      return screenHandler.slots.size() < 27 ? false : this.compute12(screenHandler) != -1;
   }

   private int compute12(ScreenHandler screenHandler) {
      int intValue18 = this.compute13(screenHandler);

      for (int intValue19 = intValue18 - 1; intValue19 >= 0; intValue19--) {
         ItemStack itemStack3 = screenHandler.getSlot(intValue19).getStack();
         String text31 = this.resolve10(itemStack3.getName().getString());
         if (text31.contains("купить")) {
            return intValue19;
         }

         if (itemStack3.getItem() == Items.LIME_STAINED_GLASS_PANE
            || itemStack3.getItem() == Items.GREEN_STAINED_GLASS_PANE
            || itemStack3.getItem() == Items.GREEN_CONCRETE
            || itemStack3.getItem() == Items.LIME_CONCRETE) {
            return intValue19;
         }
      }

      return -1;
   }

   private int compute13(ScreenHandler screenHandler) {
      return Math.max(0, Math.min(54, screenHandler.slots.size() - 36));
   }

   private String resolve10(String string) {
      return string == null ? "" : string.replaceAll("§.", "").toLowerCase(Locale.ROOT).trim();
   }

   private boolean check21(GenericContainerScreen genericContainerScreen) {
      return this.rezhimServera.is("HolyWorld") ? this.check23(genericContainerScreen) : AhHelper.check(genericContainerScreen);
   }

   public boolean check22(GenericContainerScreen genericContainerScreen) {
      return this.check21(genericContainerScreen);
   }

   private boolean check23(GenericContainerScreen genericContainerScreen) {
      if (genericContainerScreen == null) {
         return false;
      } else {
         String text32 = this.resolve10(genericContainerScreen.getTitle().getString());
         return !text32.contains("аукцион") && !text32.contains("auction") ? this.compute9(genericContainerScreen.getScreenHandler()) != -1 : true;
      }
   }

   private boolean check24(GenericContainerScreen genericContainerScreen, String string) {
      if (genericContainerScreen != null && string != null) {
         if (((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).slots.size() < 54) {
            return false;
         } else {
            String text33 = this.resolve10(genericContainerScreen.getTitle().getString());
            String text34 = this.resolve10(this.resolve8(string));
            String text35 = this.resolve10(this.resolve9(string));
            if (!text33.contains(text34) && !text33.contains(text35)) {
               return false;
            } else {
               boolean flag15 = false;
               int intValue20 = Math.min(54, ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).slots.size());

               for (int intValue21 = 45; intValue21 < intValue20; intValue21++) {
                  ItemStack itemStack4 = ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).getSlot(intValue21).getStack();
                  if (itemStack4.getItem() == Items.ARROW
                     || itemStack4.getItem() == Items.PAPER
                     || itemStack4.getItem() == Items.SPECTRAL_ARROW
                     || itemStack4.getItem() == Items.LIME_STAINED_GLASS_PANE) {
                     flag15 = true;
                     break;
                  }
               }

               if (!flag15) {
                  return false;
               } else {
                  for (int intValue22 = 0; intValue22 < Math.min(45, ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).slots.size()); intValue22++) {
                     Slot slot4 = ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).getSlot(intValue22);
                     if (this.check25(slot4) && this.compute29(slot4, this.rezhimServera.getValue()) > 0L) {
                        return false;
                     }
                  }

                  return true;
               }
            }
         }
      } else {
         return false;
      }
   }

   private AutoBuy.AutoBuyPriceData2 resolve11(GenericContainerScreen genericContainerScreen, String string) {
      long longValue18 = Long.MAX_VALUE;
      long longValue19 = Long.MAX_VALUE;
      int intValue23 = 1;
      boolean flag16 = false;
      String text36 = this.rezhimServera.getValue();

      for (int intValue24 = 0; intValue24 < Math.min(45, ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).slots.size()); intValue24++) {
         Slot slot5 = ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).getSlot(intValue24);
         if (this.check25(slot5) && this.check42(string, slot5.getStack(), text36)) {
            long longValue20 = this.compute29(slot5, text36);
            if (longValue20 > 0L) {
               int intValue25 = this.compute14(slot5);
               long longValue21 = this.compute15(longValue20, intValue25);
               if (longValue21 < longValue18 || longValue21 == longValue18 && longValue20 < longValue19) {
                  longValue18 = longValue21;
                  longValue19 = longValue20;
                  intValue23 = intValue25;
                  flag16 = true;
               }
            }
         }
      }

      return flag16 ? new AutoBuy.AutoBuyPriceData2(longValue18, longValue19, intValue23) : null;
   }

   private int compute14(Slot slot) {
      return slot != null && slot.hasStack() ? Math.max(1, slot.getStack().getCount()) : 1;
   }

   private long compute15(long l, int i) {
      int intValue26 = Math.max(1, i);
      return Math.max(1L, (l + intValue26 - 1L) / intValue26);
   }

   private boolean check25(Slot slot) {
      if (slot != null && slot.hasStack()) {
         ItemStack itemStack5 = slot.getStack();
         return !this.check26(itemStack5);
      } else {
         return false;
      }
   }

   private boolean check26(ItemStack itemStack) {
      if (itemStack != null && !itemStack.isEmpty() && itemStack.getItem() != Items.AIR) {
         Item item3 = itemStack.getItem();
         return item3 == Items.GREEN_STAINED_GLASS_PANE
            || item3 == Items.BLACK_STAINED_GLASS_PANE
            || item3 == Items.LIME_STAINED_GLASS_PANE
            || item3 == Items.RED_STAINED_GLASS_PANE
            || item3 == Items.GRAY_STAINED_GLASS_PANE
            || item3 == Items.WHITE_STAINED_GLASS_PANE
            || item3 == Items.ORANGE_STAINED_GLASS_PANE
            || item3 == Items.YELLOW_STAINED_GLASS_PANE
            || item3 == Items.ARROW
            || item3 == Items.SPECTRAL_ARROW
            || item3 == Items.PAPER
            || item3 == Items.BARRIER
            || item3 == Items.CHEST
            || item3 == Items.ENDER_CHEST
            || item3 == Items.HOPPER
            || item3 == Items.COMPASS;
      } else {
         return true;
      }
   }

   private boolean check27(Slot slot) {
      if (slot != null && slot.hasStack() && !this.check26(slot.getStack())) {
         long longValue22 = AuctionSellerParser.compute(slot);
         if (longValue22 <= 0L) {
            return false;
         } else {
            String text37 = AuctionSellerParser.resolve(slot);
            if (text37 == null || text37.isBlank()) {
               return false;
            } else {
               return CLIENT.player != null && text37.equalsIgnoreCase(CLIENT.player.getName().getString()) ? false : !check3(text37);
            }
         }
      } else {
         return false;
      }
   }

   private boolean check28(ScreenHandler screenHandler) {
      int intValue27 = this.compute13(screenHandler);
      int intValue28 = this.compute12(screenHandler);

      for (int intValue29 = 0; intValue29 < intValue27; intValue29++) {
         if (intValue29 != intValue28) {
            Slot slot6 = screenHandler.getSlot(intValue29);
            if (this.check25(slot6) && this.check27(slot6)) {
               AutoBuy.AutoBuyPriceData3 autoBuyPriceData3 = this.resolve12(slot6, (long)AuctionSellerParser.compute(slot6));
               if (autoBuyPriceData3 != null
                  && autoBuyPriceData3.buyable()
                  && (this.intValue8 == 0 || autoBuyPriceData3.fingerprint() == this.intValue8)
                  && (this.timestamp21 <= 0L || autoBuyPriceData3.lotPrice() <= this.timestamp21)
                  && (this.timestamp22 <= 0L || autoBuyPriceData3.estimatedValue() >= this.timestamp22)) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   private AutoBuy.AutoBuyPriceData3 resolve12(Slot slot, long l) {
      if (slot != null && slot.hasStack() && l > 0L) {
         ItemStack itemStack6 = slot.getStack();
         if (!this.check29(itemStack6.getItem())) {
            return null;
         } else {
            ContainerComponent containerComponent2 = (ContainerComponent)itemStack6.get(DataComponentTypes.CONTAINER);
            if (containerComponent2 == null) {
               return null;
            } else {
               long longValue23 = this.compute16(containerComponent2, 0);
               if (longValue23 <= 0L) {
                  return null;
               } else {
                  long longValue24 = longValue23 - l;
                  long longValue25 = Math.max(0L, (long)this.shulkerProfit2.getValue());
                  long longValue26 = Math.max(0L, (long)this.shulkerValue.getValue());
                  long longValue27 = (long)Math.ceil(l * (Math.max(0.0F, this.shulkerProfit.getValue()) / 100.0));
                  boolean flag17 = longValue23 >= longValue26 && longValue24 >= longValue25 && longValue24 >= longValue27;
                  return new AutoBuy.AutoBuyPriceData3(l, longValue23, longValue24, this.compute18(itemStack6), flag17);
               }
            }
         }
      } else {
         return null;
      }
   }

   private long compute16(ContainerComponent containerComponent, int i) {
      if (containerComponent != null && i <= 2) {
         long longValue28 = 0L;
         boolean flag18 = false;

         for (ItemStack itemStack7 : containerComponent.iterateNonEmptyCopy()) {
            if (itemStack7 != null && !itemStack7.isEmpty()) {
               flag18 = true;
               long longValue29 = this.compute17(itemStack7, i);
               if (longValue29 > 0L) {
                  longValue28 = this.compute19(longValue28, longValue29);
               }
            }
         }

         return flag18 ? longValue28 : 0L;
      } else {
         return 0L;
      }
   }

   private long compute17(ItemStack itemStack, int i) {
      long longValue30 = 0L;

      for (Entry entry3 : VALUES_BY_KEY.entrySet()) {
         String text38 = (String)entry3.getKey();
         Long longValue31 = (Long)entry3.getValue();
         if (text38 != null
            && longValue31 != null
            && longValue31 > 0L
            && !VALUES_3.contains(text38)
            && this.check38(text38, "FunTime")
            && this.check42(text38, itemStack, "FunTime")
            && this.check35(text38, itemStack)) {
            longValue30 = Math.max(longValue30, this.compute20(longValue31, Math.max(1, itemStack.getCount())));
         }
      }

      if (this.check29(itemStack.getItem()) && i < 2) {
         ContainerComponent containerComponent3 = (ContainerComponent)itemStack.get(DataComponentTypes.CONTAINER);
         if (containerComponent3 != null) {
            longValue30 = Math.max(longValue30, this.compute16(containerComponent3, i + 1));
         }
      }

      return longValue30;
   }

   private boolean check29(Item item) {
      return item == Items.SHULKER_BOX
         || item == Items.WHITE_SHULKER_BOX
         || item == Items.ORANGE_SHULKER_BOX
         || item == Items.MAGENTA_SHULKER_BOX
         || item == Items.LIGHT_BLUE_SHULKER_BOX
         || item == Items.YELLOW_SHULKER_BOX
         || item == Items.LIME_SHULKER_BOX
         || item == Items.PINK_SHULKER_BOX
         || item == Items.GRAY_SHULKER_BOX
         || item == Items.LIGHT_GRAY_SHULKER_BOX
         || item == Items.CYAN_SHULKER_BOX
         || item == Items.PURPLE_SHULKER_BOX
         || item == Items.BLUE_SHULKER_BOX
         || item == Items.BROWN_SHULKER_BOX
         || item == Items.GREEN_SHULKER_BOX
         || item == Items.RED_SHULKER_BOX
         || item == Items.BLACK_SHULKER_BOX;
   }

   private int compute18(ItemStack itemStack) {
      if (itemStack != null && !itemStack.isEmpty()) {
         Identifier identifier2 = Registries.ITEM.getId(itemStack.getItem());
         return Objects.hash(identifier2, itemStack.getCount(), itemStack.getName().getString(), itemStack.getComponents().hashCode());
      } else {
         return 0;
      }
   }

   private long compute19(long l, long m) {
      try {
         return Math.addExact(l, m);
      } catch (ArithmeticException arithmeticException) {
         return Long.MAX_VALUE;
      }
   }

   private long compute20(long l, int i) {
      try {
         return Math.multiplyExact(l, Math.max(1, i));
      } catch (ArithmeticException arithmeticException2) {
         return Long.MAX_VALUE;
      }
   }

   private void invoke39(String string) {
      this.text6 = string == null ? "" : string;
      this.timestamp18 = System.currentTimeMillis();
      if (!"__wild_funtime_shulker__".equals(this.text6)) {
         this.timestamp21 = 0L;
         this.timestamp22 = 0L;
         this.intValue8 = 0;
      }
   }

   private void invoke40() {
      this.text6 = "";
      this.timestamp18 = 0L;
      this.timestamp21 = 0L;
      this.timestamp22 = 0L;
      this.intValue8 = 0;
   }

   private void invoke41() {
      this.timestamp19 = 0L;
      this.intValue7 = 0;
      this.flag18 = false;
      this.timestamp20 = 0L;
   }

   private boolean check30() {
      return this.flag18 && this.rezhimServera.is("FunTime");
   }

   private void invoke42() {
      this.flag18 = true;
      this.timestamp20 = System.currentTimeMillis() + ThreadLocalRandom.current().nextLong(15000L, 20001L);
      this.flag5 = false;
      this.text2 = "";
      this.invoke40();
      if (CLIENT.player != null && CLIENT.currentScreen != null) {
         CLIENT.player.closeScreen();
      }
   }

   private void invoke43() {
      if (System.currentTimeMillis() >= this.timestamp20) {
         this.flag18 = false;
         this.timestamp20 = 0L;
         this.compute30();
      }
   }

   private boolean check31() {
      if (this.text6.isEmpty()) {
         return false;
      } else if (System.currentTimeMillis() - this.timestamp18 > 15000L) {
         this.invoke40();
         return false;
      } else {
         return true;
      }
   }

   private boolean check32(GenericContainerScreen genericContainerScreen) {
      if (genericContainerScreen != null && this.check31()) {
         String text39 = this.text6;
         String text40 = this.rezhimServera.getValue();
         if (!"__wild_funtime_shulker__".equals(text39)) {
            Long longValue32 = VALUES_BY_KEY.get(text39);
            if (longValue32 == null || longValue32 <= 0L || VALUES_3.contains(text39)) {
               return false;
            } else {
               return !this.check38(text39, text40) ? false : this.check33(genericContainerScreen.getScreenHandler(), text39, longValue32, text40);
            }
         } else {
            return text40.equals("FunTime") && this.check28(genericContainerScreen.getScreenHandler());
         }
      } else {
         return false;
      }
   }

   private boolean check33(ScreenHandler screenHandler, String string, long l, String string2) {
      int intValue30 = this.compute13(screenHandler);
      int intValue31 = this.compute12(screenHandler);

      for (int intValue32 = 0; intValue32 < intValue30; intValue32++) {
         if (intValue32 != intValue31) {
            Slot slot7 = screenHandler.getSlot(intValue32);
            if (this.check25(slot7) && (!string2.equals("FunTime") || this.check27(slot7))) {
               ItemStack itemStack8 = slot7.getStack();
               if (this.check42(string, itemStack8, string2) && this.check35(string, itemStack8)) {
                  long longValue33 = this.compute29(slot7, string2);
                  if (longValue33 <= 0L || this.compute15(longValue33, this.compute14(slot7)) <= l) {
                     return true;
                  }
               }
            }
         }
      }

      return false;
   }

   private void invoke44(ScreenHandler screenHandler) {
      if (CLIENT.player != null) {
         CLIENT.player.closeScreen();
      }

      this.invoke40();
   }

   public static int compute21(String string) {
      return compute22(string);
   }

   public static int compute22(String string) {
      return string == null ? 0 : Math.max(0, Math.min(100, VALUES_BY_KEY_2.getOrDefault(string, 0)));
   }

   public static int compute23(String string) {
      return string == null ? 100 : Math.max(0, Math.min(100, VALUES_BY_KEY_3.getOrDefault(string, 100)));
   }

   public static void invoke45(String string, int i) {
      invoke46(string, i, compute23(string));
   }

   public static void invoke46(String string, int i, int j) {
      if (string != null && !string.isBlank()) {
         int intValue33 = Math.max(0, Math.min(100, i));
         int intValue34 = Math.max(0, Math.min(100, j));
         if (intValue33 > intValue34) {
            int intValue35 = intValue33;
            intValue33 = intValue34;
            intValue34 = intValue35;
         }

         if (intValue33 <= 0) {
            VALUES_BY_KEY_2.remove(string);
         } else {
            VALUES_BY_KEY_2.put(string, intValue33);
         }

         if (intValue34 >= 100) {
            VALUES_BY_KEY_3.remove(string);
         } else {
            VALUES_BY_KEY_3.put(string, intValue34);
         }
      }
   }

   public static boolean check34(String string, String string2) {
      Set values = VALUES_BY_KEY_4.get(string);
      return values == null || !values.contains(HolyWorldItemParser.resolve3(string2));
   }

   public static void invoke47(String string, String string2, boolean bl) {
      if (string != null && !string.isBlank()) {
         String text41 = HolyWorldItemParser.resolve3(string2);
         if (!text41.isBlank()) {
            if (bl) {
               Set values2 = VALUES_BY_KEY_4.get(string);
               if (values2 != null) {
                  values2.remove(text41);
                  if (values2.isEmpty()) {
                     VALUES_BY_KEY_4.remove(string);
                  }
               }
            } else {
               VALUES_BY_KEY_4.computeIfAbsent(string, stringx -> new LinkedHashSet<>()).add(text41);
            }
         }
      }
   }

   public static Set<String> resolve13(String string, List<String> list) {
      LinkedHashSet linkedHashSet4 = new LinkedHashSet();
      if (list == null) {
         return linkedHashSet4;
      } else {
         for (String text42 : list) {
            String text43 = HolyWorldItemParser.resolve3(text42);
            if (!text43.isBlank() && check34(string, text43)) {
               linkedHashSet4.add(text43);
            }
         }

         return linkedHashSet4;
      }
   }

   public static int compute24(ItemStack itemStack) {
      if (itemStack != null && !itemStack.isEmpty() && itemStack.isDamageable()) {
         int intValue36 = itemStack.getMaxDamage();
         if (intValue36 <= 0) {
            return 100;
         } else {
            int intValue37 = Math.max(0, intValue36 - itemStack.getDamage());
            return Math.max(0, Math.min(100, (int)(intValue37 * 100L / intValue36)));
         }
      } else {
         return 100;
      }
   }

   private boolean check35(String string, ItemStack itemStack) {
      if (itemStack != null && check36(itemStack.getItem())) {
         int intValue38 = compute22(string);
         int intValue39 = compute23(string);
         int intValue40 = compute24(itemStack);
         return intValue40 >= intValue38 && intValue40 <= intValue39;
      } else {
         return true;
      }
   }

   public static boolean check36(Item item) {
      return item == null ? false : check37(item) || new ItemStack(item).isDamageable();
   }

   public static boolean check37(Item item) {
      return item == Items.NETHERITE_HELMET
         || item == Items.DIAMOND_HELMET
         || item == Items.IRON_HELMET
         || item == Items.CHAINMAIL_HELMET
         || item == Items.GOLDEN_HELMET
         || item == Items.LEATHER_HELMET
         || item == Items.TURTLE_HELMET
         || item == Items.NETHERITE_CHESTPLATE
         || item == Items.DIAMOND_CHESTPLATE
         || item == Items.IRON_CHESTPLATE
         || item == Items.CHAINMAIL_CHESTPLATE
         || item == Items.GOLDEN_CHESTPLATE
         || item == Items.LEATHER_CHESTPLATE
         || item == Items.NETHERITE_LEGGINGS
         || item == Items.DIAMOND_LEGGINGS
         || item == Items.IRON_LEGGINGS
         || item == Items.CHAINMAIL_LEGGINGS
         || item == Items.GOLDEN_LEGGINGS
         || item == Items.LEATHER_LEGGINGS
         || item == Items.NETHERITE_BOOTS
         || item == Items.DIAMOND_BOOTS
         || item == Items.IRON_BOOTS
         || item == Items.CHAINMAIL_BOOTS
         || item == Items.GOLDEN_BOOTS
         || item == Items.LEATHER_BOOTS;
   }

   private String resolve14(Slot slot) {
      String text44 = this.rezhimServera.getValue();
      if (slot != null && slot.hasStack()) {
         if (text44.equals("HolyWorld")) {
            return this.resolve15(slot);
         } else {
            ItemStack itemStack9 = slot.getStack();
            long longValue34 = this.compute29(slot, text44);
            String text45 = this.resolve27(slot, text44);
            if (longValue34 <= 0L) {
               return null;
            } else if (text44.equals("FunTime") && !this.check27(slot)) {
               return null;
            } else if (CLIENT.player != null && text45 != null && text45.equalsIgnoreCase(CLIENT.player.getName().getString())) {
               return null;
            } else if (check3(text45)) {
               return null;
            } else {
               if (text44.equals("FunTime")) {
                  AutoBuy.AutoBuyPriceData3 autoBuyPriceData32 = this.resolve12(slot, longValue34);
                  if (autoBuyPriceData32 != null && autoBuyPriceData32.buyable()) {
                     this.timestamp21 = autoBuyPriceData32.lotPrice();
                     this.timestamp22 = autoBuyPriceData32.estimatedValue();
                     this.intValue8 = autoBuyPriceData32.fingerprint();
                     return "__wild_funtime_shulker__";
                  }
               }

               long longValue35 = this.compute15(longValue34, this.compute14(slot));

               for (Entry entry4 : VALUES_BY_KEY.entrySet()) {
                  String text46 = (String)entry4.getKey();
                  Long longValue36 = (Long)entry4.getValue();
                  if (!VALUES_3.contains(text46)
                     && longValue36 != null
                     && this.check38(text46, text44)
                     && this.check42(text46, itemStack9, text44)
                     && longValue35 <= longValue36
                     && this.check35(text46, itemStack9)) {
                     return text46;
                  }
               }

               return null;
            }
         }
      } else {
         return null;
      }
   }

   private boolean check38(String string, String string2) {
      if (string == null || string2 == null) {
         return false;
      } else if (string.startsWith("minecraft:")) {
         return true;
      } else if (HolyWorldItemParser.check(string)) {
         return string2.equals("HolyWorld");
      } else if (string2.equals("FunTime")) {
         return VALUES.contains(string);
      } else if (string2.equals("SpookyTime")) {
         return VALUES.contains(string) || VALUES_2.contains(string);
      } else {
         return string2.equals("HolyWorld") ? HolyWorldItemParser.check2(string) : false;
      }
   }

   private String resolve15(Slot slot) {
      ItemStack itemStack10 = slot.getStack();
      if (this.check8(itemStack10)) {
         return null;
      } else if (!itemStack10.isOf(Items.BARRIER) && !itemStack10.isOf(Items.CHEST) && !itemStack10.isOf(Items.ENDER_CHEST)) {
         AutoBuy.AutoBuyPriceData autoBuyPriceData = this.resolve24(slot);
         if (autoBuyPriceData.price() > 0L && autoBuyPriceData.seller() != null) {
            if (CLIENT.player != null && autoBuyPriceData.seller().equalsIgnoreCase(CLIENT.player.getName().getString())) {
               return null;
            } else if (check3(autoBuyPriceData.seller())) {
               return null;
            } else {
               long longValue37 = this.compute15(autoBuyPriceData.price(), this.compute14(slot));
               this.invoke48();
               AutoBuy.AutoBuyItemState autoBuyItemState = new AutoBuy.AutoBuyItemState(itemStack10);
               String text47 = this.resolve17(itemStack10, longValue37, this.valuesByKey.get(itemStack10.getItem()), autoBuyItemState);
               return text47 != null ? text47 : this.resolve17(itemStack10, longValue37, this.items2, autoBuyItemState);
            }
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   private void invoke48() {
      int intValue41 = this.compute25();
      if (intValue41 != this.intValue9) {
         this.intValue9 = intValue41;
         this.valuesByKey.clear();
         this.items2.clear();

         for (Entry entry5 : VALUES_BY_KEY.entrySet()) {
            String text48 = (String)entry5.getKey();
            Long longValue38 = (Long)entry5.getValue();
            if (text48 != null && !VALUES_3.contains(text48) && longValue38 != null) {
               HolyWorldItemParser.HolyWorldItemParserDisplayEntry holyWorldItemParserDisplayEntry = HolyWorldItemParser.resolve(text48);
               Item item4 = this.resolve16(text48, holyWorldItemParserDisplayEntry);
               AutoBuy.AutoBuyItemData autoBuyItemData = new AutoBuy.AutoBuyItemData(text48, longValue38, holyWorldItemParserDisplayEntry, item4, this.resolve23(text48));
               if (item4 != null && item4 != Items.AIR) {
                  this.valuesByKey.computeIfAbsent(item4, item -> new ArrayList<>()).add(autoBuyItemData);
               } else {
                  this.items2.add(autoBuyItemData);
               }
            }
         }
      }
   }

   private int compute25() {
      int intValue42 = 1;

      for (Entry entry6 : VALUES_BY_KEY.entrySet()) {
         intValue42 = 31 * intValue42 + Objects.hashCode(entry6.getKey());
         intValue42 = 31 * intValue42 + Objects.hashCode(entry6.getValue());
      }

      for (String text49 : VALUES_3) {
         intValue42 += Objects.hashCode(text49);
      }

      return intValue42;
   }

   private void invoke49() {
      this.intValue9 = Integer.MIN_VALUE;
   }

   private Item resolve16(String string, HolyWorldItemParser.HolyWorldItemParserDisplayEntry holyWorldItemParserDisplayEntry2) {
      if (holyWorldItemParserDisplayEntry2 != null) {
         return holyWorldItemParserDisplayEntry2.item();
      } else if (string != null && string.startsWith("minecraft:")) {
         Identifier identifier3 = Identifier.tryParse(string);
         if (identifier3 == null) {
            return null;
         } else {
            Item item5 = (Item)Registries.ITEM.get(identifier3);
            return item5 == Items.AIR ? null : item5;
         }
      } else {
         return null;
      }
   }

   private String resolve17(ItemStack itemStack, long l, List<AutoBuy.AutoBuyItemData> list, AutoBuy.AutoBuyItemState autoBuyItemState2) {
      if (list != null && !list.isEmpty()) {
         for (AutoBuy.AutoBuyItemData autoBuyItemData2 : list) {
            if (l <= autoBuyItemData2.maxPrice()) {
               HolyWorldItemParser.HolyWorldItemParserDisplayEntry holyWorldItemParserDisplayEntry3 = autoBuyItemData2.holyWorldEntry();
               if (holyWorldItemParserDisplayEntry3 != null) {
                  if (itemStack.isOf(holyWorldItemParserDisplayEntry3.item())
                     && this.check40(autoBuyItemData2.itemName(), holyWorldItemParserDisplayEntry3, itemStack, autoBuyItemState2.resolve(), autoBuyItemState2.resolve2())
                     && this.check35(autoBuyItemData2.itemName(), itemStack)) {
                     return autoBuyItemData2.itemName();
                  }
               } else if (this.check39(autoBuyItemData2, itemStack, autoBuyItemState2) && this.check35(autoBuyItemData2.itemName(), itemStack)) {
                  return autoBuyItemData2.itemName();
               }
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private boolean check39(AutoBuy.AutoBuyItemData autoBuyItemData3, ItemStack itemStack, AutoBuy.AutoBuyItemState autoBuyItemState3) {
      Item item6 = autoBuyItemData3.item();
      if (item6 != null && item6 != Items.AIR && !itemStack.isOf(item6)) {
         return false;
      } else {
         Identifier identifier4 = autoBuyItemState3.resolve5();
         String text50 = autoBuyItemData3.itemName();
         if (identifier4 == null || !text50.equalsIgnoreCase(identifier4.toString()) && !text50.equalsIgnoreCase(identifier4.getPath())) {
            String text51 = autoBuyItemState3.resolve3();
            return text51.equalsIgnoreCase(text50) || autoBuyItemState3.resolve4().equals(autoBuyItemData3.normalizedName());
         } else {
            return true;
         }
      }
   }

   private boolean check40(String string, HolyWorldItemParser.HolyWorldItemParserDisplayEntry holyWorldItemParserDisplayEntry4, ItemStack itemStack, String string2, String string3) {
      Set values3 = check37(holyWorldItemParserDisplayEntry4.item()) ? resolve13(string, holyWorldItemParserDisplayEntry4.enchantments()) : null;
      return HolyWorldItemParser.check11(holyWorldItemParserDisplayEntry4, itemStack, string2, string3, values3);
   }

   private boolean check41(Slot slot) {
      if (slot != null && slot.hasStack()) {
         ItemStack itemStack11 = slot.getStack();
         if (this.check8(itemStack11)) {
            return false;
         } else if (!itemStack11.isOf(Items.BARRIER) && !itemStack11.isOf(Items.CHEST) && !itemStack11.isOf(Items.ENDER_CHEST)) {
            AutoBuy.AutoBuyPriceData autoBuyPriceData4 = this.resolve24(slot);
            return autoBuyPriceData4.price() > 0L && autoBuyPriceData4.seller() != null;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private boolean check42(String string, ItemStack itemStack, String string2) {
      if (string == null || itemStack == null || itemStack.isEmpty()) {
         return false;
      } else if (string2.equals("HolyWorld") && HolyWorldItemParser.check2(string)) {
         HolyWorldItemParser.HolyWorldItemParserDisplayEntry holyWorldItemParserDisplayEntry5 = HolyWorldItemParser.resolve(string);
         if (holyWorldItemParserDisplayEntry5 != null && itemStack.isOf(holyWorldItemParserDisplayEntry5.item())) {
            String text52 = HolyWorldItemParser.resolve5(itemStack);
            String text53 = HolyWorldItemParser.resolve6(itemStack);
            return this.check40(string, holyWorldItemParserDisplayEntry5, itemStack, text52, text53);
         } else {
            return false;
         }
      } else if (!string2.equals("FunTime") && !string2.equals("SpookyTime") && !string2.equals("HolyWorld")) {
         return this.check53(string, itemStack);
      } else {
         return switch (string) {
            case "Сфера Хаоса" -> this.check52(
               itemStack,
               Items.PLAYER_HEAD,
               this.resolve22(EntityAttributes.ATTACK_DAMAGE, 2.5),
               this.resolve22(EntityAttributes.MOVEMENT_SPEED, 0.07),
               this.resolve22(EntityAttributes.ATTACK_SPEED, 0.13),
               this.resolve22(EntityAttributes.ARMOR, 1.5),
               this.resolve22(EntityAttributes.MAX_HEALTH, -4.0),
               this.resolve22(EntityAttributes.GRAVITY, 0.09)
            );
            case "Сфера Титана" -> this.check52(
               itemStack,
               Items.PLAYER_HEAD,
               this.resolve22(EntityAttributes.ARMOR, 2.5),
               this.resolve22(EntityAttributes.ARMOR_TOUGHNESS, 2.5),
               this.resolve22(EntityAttributes.MOVEMENT_SPEED, -0.15)
            );
            case "Сфера Ареса" -> this.check52(
               itemStack,
               Items.PLAYER_HEAD,
               this.resolve22(EntityAttributes.ATTACK_DAMAGE, 6.0),
               this.resolve22(EntityAttributes.ARMOR, -2.0),
               this.resolve22(EntityAttributes.MAX_HEALTH, -2.0)
            );
            case "Сфера Бестии" -> this.check52(
               itemStack,
               Items.PLAYER_HEAD,
               this.resolve22(EntityAttributes.ARMOR, 1.0),
               this.resolve22(EntityAttributes.MAX_HEALTH, 4.0),
               this.resolve22(EntityAttributes.MOVEMENT_SPEED, 0.1),
               this.resolve22(EntityAttributes.ATTACK_SPEED, 0.1)
            );
            case "Сфера Гидры" -> this.check52(
               itemStack,
               Items.PLAYER_HEAD,
               this.resolve22(EntityAttributes.ARMOR, 2.0),
               this.resolve22(EntityAttributes.MAX_HEALTH, 4.0),
               this.resolve22(EntityAttributes.SUBMERGED_MINING_SPEED, 0.5),
               this.resolve22(EntityAttributes.OXYGEN_BONUS, 0.5)
            );
            case "Сфера Икара" -> this.check52(
               itemStack, Items.PLAYER_HEAD, this.resolve22(EntityAttributes.ATTACK_DAMAGE, 2.0), this.resolve22(EntityAttributes.MAX_HEALTH, 2.0)
            );
            case "Сфера Эрида" -> this.check52(
               itemStack,
               Items.PLAYER_HEAD,
               this.resolve22(EntityAttributes.LUCK, 1.0),
               this.resolve22(EntityAttributes.MAX_HEALTH, 2.0),
               this.resolve22(EntityAttributes.BLOCK_INTERACTION_RANGE, 1.0)
            );
            case "Сфера Сатира" -> this.check52(
               itemStack,
               Items.PLAYER_HEAD,
               this.resolve22(EntityAttributes.ATTACK_DAMAGE, 2.0),
               this.resolve22(EntityAttributes.JUMP_STRENGTH, -0.1),
               this.resolve22(EntityAttributes.ATTACK_SPEED, 0.15)
            );
            case "Вещи Крушителя", "Набор Крушителя", "Броня Крушителя", "Броня Крушителя с шипами", "Броня Крушителя шип", "Броня Крушителя без шипов", "Броня Крушителя без шип", "Шлем Крушителя", "Нагрудник Крушителя", "Поножи Крушителя", "Ботинки Крушителя", "Меч Крушителя", "Кирка Крушителя", "Лук Крушителя", "Арбалет Крушителя", "Трезубец Крушителя", "Булава Крушителя", "Элитры Крушителя", "Удочка Крушителя" -> (
                  string2.equals("FunTime") || string2.equals("SpookyTime")
               )
               && this.check43(string, itemStack);
            case "Талисман Демона" -> this.check52(
               itemStack, Items.TOTEM_OF_UNDYING, this.resolve22(EntityAttributes.ATTACK_DAMAGE, 2.5), this.resolve22(EntityAttributes.ATTACK_SPEED, 0.1)
            );
            case "Талисман Карателя" -> this.check52(
               itemStack,
               Items.TOTEM_OF_UNDYING,
               this.resolve22(EntityAttributes.ATTACK_DAMAGE, 7.0),
               this.resolve22(EntityAttributes.MAX_HEALTH, -4.0),
               this.resolve22(EntityAttributes.MOVEMENT_SPEED, 0.1)
            );
            case "Талисман Мрака" -> this.check52(
               itemStack, Items.TOTEM_OF_UNDYING, this.resolve22(EntityAttributes.ARMOR, 1.5), this.resolve22(EntityAttributes.MAX_HEALTH, 1.5)
            );
            case "Талисман Ярости" -> this.check52(
               itemStack, Items.TOTEM_OF_UNDYING, this.resolve22(EntityAttributes.ATTACK_DAMAGE, 5.0), this.resolve22(EntityAttributes.MAX_HEALTH, -4.0)
            );
            case "Талисман Тирана" -> this.check52(
               itemStack,
               Items.TOTEM_OF_UNDYING,
               this.resolve22(EntityAttributes.ATTACK_DAMAGE, 2.0),
               this.resolve22(EntityAttributes.ARMOR, 2.0),
               this.resolve22(EntityAttributes.MAX_HEALTH, -4.0)
            );
            case "Талисман Крушителя" -> this.check52(
               itemStack,
               Items.TOTEM_OF_UNDYING,
               this.resolve22(EntityAttributes.MAX_HEALTH, 4.0),
               this.resolve22(EntityAttributes.ATTACK_DAMAGE, 3.0),
               this.resolve22(EntityAttributes.ARMOR_TOUGHNESS, 2.0),
               this.resolve22(EntityAttributes.ARMOR, 2.0)
            );
            case "Талисман Раздора" -> this.check52(
               itemStack,
               Items.TOTEM_OF_UNDYING,
               this.resolve22(EntityAttributes.ATTACK_DAMAGE, 4.0),
               this.resolve22(EntityAttributes.MAX_HEALTH, 2.0),
               this.resolve22(EntityAttributes.MOVEMENT_SPEED, 0.1),
               this.resolve22(EntityAttributes.ATTACK_SPEED, 0.1),
               this.resolve22(EntityAttributes.ARMOR, -3.0)
            );
            case "Зелье Ассасина" -> SpecialItemUtils.check25(itemStack);
            case "Зелье Гнева" -> this.check52(itemStack, Items.SPLASH_POTION, this.resolve22(EntityAttributes.ATTACK_DAMAGE, 5.0))
               && SpecialItemUtils.check26(itemStack);
            case "Талисман Сара", "Талисман Сары" -> this.check52(itemStack, Items.TOTEM_OF_UNDYING, this.resolve22(EntityAttributes.MAX_HEALTH, 2.0));
            case "Хлопушка" -> SpecialItemUtils.check27(itemStack);
            case "Святая Вода" -> SpecialItemUtils.check28(itemStack);
            case "Зелье Палладина" -> SpecialItemUtils.check29(itemStack);
            case "Зелье Радиации" -> SpecialItemUtils.check30(itemStack);
            case "Снотворное" -> SpecialItemUtils.check31(itemStack);
            case "Пласт" -> SpecialItemUtils.check36(itemStack);
            case "Вайт" -> SpecialItemUtils.check41(itemStack);
            case "Блек" -> SpecialItemUtils.check42(itemStack);
            case "Блок дамагер" -> SpecialItemUtils.check47(itemStack);
            case "Прогрузчик чанков" -> SpecialItemUtils.check48(itemStack);
            case "Маяк" -> SpecialItemUtils.check49(itemStack);
            case "Проклятая Душа" -> SpecialItemUtils.check50(itemStack);
            case "Драконий Скин" -> SpecialItemUtils.check51(itemStack);
            case "Огненный Смерч" -> SpecialItemUtils.check52(itemStack);
            case "Снежок Заморозка" -> SpecialItemUtils.check53(itemStack);
            case "Божья Аура" -> SpecialItemUtils.check54(itemStack);
            case "Серебро" -> SpecialItemUtils.check55(itemStack);
            case "Божье Касание", "Божье касание" -> SpecialItemUtils.check56(itemStack);
            case "Мощный Удар" -> SpecialItemUtils.check57(itemStack);
            case "Мега Бульдозер" -> SpecialItemUtils.check58(itemStack);
            case "Нерушимые Элитры" -> SpecialItemUtils.check59(itemStack);
            case "Опыт 15" -> SpecialItemUtils.check37(itemStack);
            case "Опыт 30" -> SpecialItemUtils.check38(itemStack);
            case "Опыт 45" -> SpecialItemUtils.check40(itemStack);
            case "Опыт 50" -> SpecialItemUtils.check39(itemStack);
            default -> this.check53(string, itemStack);
         };
      }
   }

   private boolean check43(String string, ItemStack itemStack) {
      if (string != null && itemStack != null && !itemStack.isEmpty()) {
         return switch (string) {
            case "Вещи Крушителя", "Набор Крушителя" -> this.check44(itemStack);
            case "Броня Крушителя", "Броня Крушителя с шипами", "Броня Крушителя шип", "Броня Крушителя без шипов", "Броня Крушителя без шип" -> this.check45(
               itemStack
            );
            case "Шлем Крушителя" -> this.check46(string, SpecialItemCatalog.resolve(), itemStack);
            case "Нагрудник Крушителя" -> this.check46(string, SpecialItemCatalog.resolve2(), itemStack);
            case "Поножи Крушителя" -> this.check46(string, SpecialItemCatalog.resolve3(), itemStack);
            case "Ботинки Крушителя" -> this.check46(string, SpecialItemCatalog.resolve4(), itemStack);
            case "Меч Крушителя" -> this.check46(string, SpecialItemCatalog.resolve5(), itemStack);
            case "Кирка Крушителя" -> this.check46(string, SpecialItemCatalog.resolve6(), itemStack);
            case "Арбалет Крушителя" -> this.check46(string, SpecialItemCatalog.resolve7(), itemStack);
            case "Трезубец Крушителя" -> this.check46(string, SpecialItemCatalog.resolve8(), itemStack);
            case "Булава Крушителя" -> this.check46(string, SpecialItemCatalog.resolve9(), itemStack);
            case "Лук Крушителя" -> this.check49(itemStack, Items.BOW);
            case "Элитры Крушителя" -> this.check49(itemStack, Items.ELYTRA);
            case "Удочка Крушителя" -> this.check49(itemStack, Items.FISHING_ROD);
            default -> false;
         };
      } else {
         return false;
      }
   }

   private boolean check44(ItemStack itemStack) {
      return this.check45(itemStack)
         || this.check46("Вещи Крушителя", SpecialItemCatalog.resolve5(), itemStack)
         || this.check46("Вещи Крушителя", SpecialItemCatalog.resolve6(), itemStack)
         || this.check46("Вещи Крушителя", SpecialItemCatalog.resolve7(), itemStack)
         || this.check46("Вещи Крушителя", SpecialItemCatalog.resolve8(), itemStack)
         || this.check46("Вещи Крушителя", SpecialItemCatalog.resolve9(), itemStack);
   }

   private boolean check45(ItemStack itemStack) {
      return this.check46("Броня Крушителя", SpecialItemCatalog.resolve(), itemStack)
         || this.check46("Броня Крушителя", SpecialItemCatalog.resolve2(), itemStack)
         || this.check46("Броня Крушителя", SpecialItemCatalog.resolve3(), itemStack)
         || this.check46("Броня Крушителя", SpecialItemCatalog.resolve4(), itemStack);
   }

   private boolean check46(String string, ItemStack itemStack, ItemStack itemStack2) {
      if (itemStack == null || itemStack.isEmpty() || itemStack2 == null || itemStack2.isEmpty()) {
         return false;
      } else if (!itemStack2.isOf(itemStack.getItem())) {
         return false;
      } else {
         String text54 = this.resolve21(itemStack.getName().getString());
         if (!text54.isEmpty() && !this.resolve21(this.resolve19(itemStack2)).contains(text54)) {
            return false;
         } else {
            ItemEnchantmentsComponent itemEnchantmentsComponent2 = (ItemEnchantmentsComponent)itemStack.get(DataComponentTypes.ENCHANTMENTS);
            if (itemEnchantmentsComponent2 != null && !itemEnchantmentsComponent2.isEmpty() && !this.check47(string, itemStack2, itemEnchantmentsComponent2)) {
               return false;
            } else {
               LoreComponent loreComponent = (LoreComponent)itemStack.get(DataComponentTypes.LORE);
               if (loreComponent != null) {
                  String text55 = this.resolve21(this.resolve19(itemStack2));

                  for (Text text56 : loreComponent.lines()) {
                     String text57 = this.resolve21(text56.getString());
                     if (!text57.isEmpty() && !text55.contains(text57)) {
                        return false;
                     }
                  }
               }

               return true;
            }
         }
      }
   }

   private boolean check47(String string, ItemStack itemStack, ItemEnchantmentsComponent itemEnchantmentsComponent) {
      ItemEnchantmentsComponent itemEnchantmentsComponent3 = (ItemEnchantmentsComponent)itemStack.get(DataComponentTypes.ENCHANTMENTS);

      for (it.unimi.dsi.fastutil.objects.Object2IntMap.Entry entry7 : itemEnchantmentsComponent.getEnchantmentEntries()) {
         String text58 = this.resolve18((RegistryEntry<Enchantment>)entry7.getKey());
         if (text58.isBlank() || check34(string, text58)) {
            if (itemEnchantmentsComponent3 == null || itemEnchantmentsComponent3.isEmpty()) {
               return false;
            }

            if (this.compute26(itemEnchantmentsComponent3, (RegistryEntry<Enchantment>)entry7.getKey()) < entry7.getIntValue()) {
               return false;
            }
         }
      }

      return true;
   }

   private String resolve18(RegistryEntry<Enchantment> registryEntry) {
      return registryEntry.getKey().map(registryKey -> HolyWorldItemParser.resolve3(registryKey.getValue().toString())).orElse("");
   }

   private int compute26(ItemEnchantmentsComponent itemEnchantmentsComponent, RegistryEntry<Enchantment> registryEntry) {
      for (it.unimi.dsi.fastutil.objects.Object2IntMap.Entry entry8 : itemEnchantmentsComponent.getEnchantmentEntries()) {
         if (((RegistryEntry)entry8.getKey()).equals(registryEntry)) {
            return entry8.getIntValue();
         }
      }

      return 0;
   }

   private boolean check48(ItemStack itemStack) {
      return this.check49(itemStack, Items.BOW) || this.check49(itemStack, Items.ELYTRA) || this.check49(itemStack, Items.FISHING_ROD);
   }

   private boolean check49(ItemStack itemStack, Item item) {
      return itemStack != null && !itemStack.isEmpty() && itemStack.isOf(item) ? this.check50(itemStack, "крушител") && this.check51(itemStack) : false;
   }

   private boolean check50(ItemStack itemStack, String string) {
      return this.resolve21(this.resolve19(itemStack)).contains(this.resolve21(string));
   }

   private boolean check51(ItemStack itemStack) {
      return itemStack.hasEnchantments()
         || itemStack.hasGlint()
         || itemStack.contains(DataComponentTypes.CUSTOM_NAME)
         || itemStack.contains(DataComponentTypes.LORE)
         || itemStack.contains(DataComponentTypes.CUSTOM_DATA);
   }

   private String resolve19(ItemStack itemStack) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(itemStack.getName().getString()).append(' ');
      LoreComponent loreComponent2 = (LoreComponent)itemStack.get(DataComponentTypes.LORE);
      if (loreComponent2 != null) {
         for (Text text59 : loreComponent2.lines()) {
            stringBuilder.append(text59.getString()).append(' ');
         }
      }

      stringBuilder.append(itemStack.getComponents());
      return stringBuilder.toString();
   }

   private String resolve20(ItemStack itemStack) {
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append(itemStack.getName().getString()).append(' ');
      LoreComponent loreComponent3 = (LoreComponent)itemStack.get(DataComponentTypes.LORE);
      if (loreComponent3 != null) {
         for (Text text60 : loreComponent3.lines()) {
            stringBuilder2.append(text60.getString()).append(' ');
         }
      }

      return stringBuilder2.toString();
   }

   private String resolve21(String string) {
      return string == null ? "" : string.replaceAll("(?i)§[0-9A-FK-OR]", "").toLowerCase(Locale.ROOT).replaceAll("[^\\p{L}\\p{N}]+", "");
   }

   private boolean check52(ItemStack itemStack, Item item, AutoBuy.AutoBuyData... w57s) {
      if (itemStack != null && !itemStack.isEmpty() && itemStack.isOf(item)) {
         AttributeModifiersComponent attributeModifiersComponent = (AttributeModifiersComponent)itemStack.get(DataComponentTypes.ATTRIBUTE_MODIFIERS);
         if (attributeModifiersComponent == null) {
            return w57s.length == 0;
         } else {
            HashMap hashMap = new HashMap();
            int intValue43 = 0;

            for (net.minecraft.component.type.AttributeModifiersComponent.Entry entry9 : attributeModifiersComponent.modifiers()) {
               EntityAttributeModifier entityAttributeModifier = entry9.modifier();
               intValue43++;
               hashMap.put(entry9.attribute(), entityAttributeModifier.value());
            }

            if (intValue43 == w57s.length && hashMap.size() == w57s.length) {
               for (AutoBuy.AutoBuyData autoBuyData : w57s) {
                  Double doubleValue12 = (Double)hashMap.get(autoBuyData.attribute());
                  if (doubleValue12 == null || Math.abs(doubleValue12 - autoBuyData.value()) > 1.0E-4) {
                     return false;
                  }
               }

               return true;
            } else {
               return false;
            }
         }
      } else {
         return false;
      }
   }

   private AutoBuy.AutoBuyData resolve22(RegistryEntry<EntityAttribute> registryEntry, double d) {
      return new AutoBuy.AutoBuyData(registryEntry, d);
   }

   private boolean check53(String string, ItemStack itemStack) {
      if (string != null && itemStack != null && !itemStack.isEmpty()) {
         if (string.startsWith("minecraft:") || !string.contains(":") && Identifier.tryParse("minecraft:" + string) != null) {
            Identifier identifier5 = Identifier.tryParse(string.contains(":") ? string : "minecraft:" + string);
            if (identifier5 != null) {
               Item item7 = (Item)Registries.ITEM.get(identifier5);
               if (item7 != Items.AIR && itemStack.isOf(item7)) {
                  String text61 = item7.getDefaultStack().getName().getString();
                  String text62 = itemStack.getName().getString();
                  return text62.equalsIgnoreCase(text61) || this.resolve23(text62).equals(this.resolve23(text61));
               }
            }

            return false;
         } else {
            String text63 = itemStack.getName().getString();
            return text63.equalsIgnoreCase(string) || this.resolve23(text63).equals(this.resolve23(string));
         }
      } else {
         return false;
      }
   }

   String resolve23(String string) {
      return string == null ? "" : string.toLowerCase(Locale.ROOT).replaceAll("[^\\p{L}\\p{N}]+", "");
   }

   private AutoBuy.AutoBuyPriceData resolve24(Slot slot) {
      if (slot != null && slot.hasStack()) {
         LoreComponent loreComponent4 = (LoreComponent)slot.getStack().get(DataComponentTypes.LORE);
         if (loreComponent4 == null) {
            return new AutoBuy.AutoBuyPriceData(0L, null);
         } else {
            long longValue39 = 0L;
            String text64 = null;

            for (Text text65 : loreComponent4.lines()) {
               String text66 = this.resolve26(text65.getString());
               String text67 = text66.toLowerCase(Locale.ROOT);
               if (text64 == null) {
                  int intValue44 = text67.indexOf("продавец:");
                  if (intValue44 != -1) {
                     text64 = text66.substring(intValue44 + "продавец:".length()).trim();
                  } else {
                     intValue44 = text67.indexOf("seller:");
                     if (intValue44 != -1) {
                        text64 = text66.substring(intValue44 + "seller:".length()).trim();
                     }
                  }
               }

               if (longValue39 <= 0L && (text66.contains("$") || text66.contains("¤") || text67.contains("цена") || text67.contains("стоимость"))) {
                  longValue39 = this.compute28(text66);
               }
            }

            return new AutoBuy.AutoBuyPriceData(longValue39, text64);
         }
      } else {
         return new AutoBuy.AutoBuyPriceData(0L, null);
      }
   }

   private String resolve25(Slot slot) {
      if (slot != null && slot.hasStack()) {
         LoreComponent loreComponent5 = (LoreComponent)slot.getStack().get(DataComponentTypes.LORE);
         if (loreComponent5 == null) {
            return null;
         } else {
            for (Text text68 : loreComponent5.lines()) {
               String text69 = this.resolve26(text68.getString());
               String text70 = text69.toLowerCase(Locale.ROOT);
               int intValue45 = text70.indexOf("продавец:");
               if (intValue45 != -1) {
                  return text69.substring(intValue45 + "продавец:".length()).trim();
               }

               intValue45 = text70.indexOf("seller:");
               if (intValue45 != -1) {
                  return text69.substring(intValue45 + "seller:".length()).trim();
               }
            }

            return null;
         }
      } else {
         return null;
      }
   }

   private long compute27(Slot slot) {
      if (slot != null && slot.hasStack()) {
         LoreComponent loreComponent6 = (LoreComponent)slot.getStack().get(DataComponentTypes.LORE);
         if (loreComponent6 == null) {
            return 0L;
         } else {
            for (Text text71 : loreComponent6.lines()) {
               String text72 = this.resolve26(text71.getString());
               String text73 = text72.toLowerCase(Locale.ROOT);
               if (text72.contains("$") || text72.contains("¤") || text73.contains("цена") || text73.contains("стоимость")) {
                  long longValue40 = this.compute28(text72);
                  if (longValue40 > 0L) {
                     return longValue40;
                  }
               }
            }

            return 0L;
         }
      } else {
         return 0L;
      }
   }

   private long compute28(String string) {
      if (string == null) {
         return 0L;
      } else {
         String text74 = string.replace(' ', ' ').toLowerCase(Locale.ROOT).trim();
         long longValue41 = 1L;
         if (text74.contains("млн") || text74.endsWith("m") || text74.endsWith("м")) {
            longValue41 = 1000000L;
         } else if (text74.contains("тыс") || text74.endsWith("k") || text74.endsWith("к")) {
            longValue41 = 1000L;
         }

         String text75 = text74.replaceAll("[^0-9]", "");
         if (text75.isEmpty()) {
            return 0L;
         } else {
            try {
               return Math.multiplyExact(Long.parseLong(text75), longValue41);
            } catch (NumberFormatException | ArithmeticException exception12) {
               return 0L;
            }
         }
      }
   }

   private String resolve26(String string) {
      return string == null ? "" : string.replaceAll("§.", "").replace(' ', ' ').trim();
   }

   private String resolve27(Slot slot, String string) {
      return switch (string) {
         case "FunTime" -> AuctionSellerParser.resolve(slot);
         case "SpookyTime" -> AuctionPriceParser.resolve(slot);
         case "HolyWorld" -> this.resolve25(slot);
         default -> null;
      };
   }

   private long compute29(Slot slot, String string) {
      return switch (string) {
         case "FunTime" -> AuctionSellerParser.compute(slot);
         case "SpookyTime" -> AuctionPriceParser.compute(slot);
         case "HolyWorld" -> this.compute27(slot);
         default -> 0L;
      };
   }

   private void invoke50(String string) {
      if (string.contains("Вы успешно купили")) {
         this.invoke51(string);
      } else {
         this.invoke52(string);
      }
   }

   private void invoke51(String string) {
      String text76 = "Вы успешно купили ";
      String text77 = " за ";
      int intValue46 = string.indexOf(text76);
      int intValue47 = string.indexOf(text77);
      if (intValue46 != -1 && intValue47 != -1) {
         String text78 = string.substring(intValue46 + text76.length(), intValue47).replace(' ', ' ').trim();
         String text79 = string.substring(intValue47 + text77.length()).replaceAll("[^\\d]", "").trim();
         if (!text79.isEmpty()) {
            this.invoke53(text78, Long.parseLong(text79));
         }
      }
   }

   private void invoke52(String string) {
      Matcher matcher = PATTERN_3.matcher(this.resolve26(string));
      if (matcher.find()) {
         String text80 = matcher.group(1) != null ? matcher.group(1) : matcher.group(2);
         if (text80 != null && !text80.isBlank()) {
            String text81 = matcher.group(3);
            String text82 = matcher.group(5);
            String text83 = text82 == null ? "" : text82.replaceAll("[^\\d]", "");
            if (!text83.isEmpty()) {
               long longValue42 = Long.parseLong(text83);
               String text84 = this.resolve28(text80);
               if (text81 != null && !text81.isBlank()) {
                  text84 = text84 + " x" + text81.replaceAll("[^\\d]", "");
               }

               this.invoke53(text84, longValue42);
            }
         }
      }
   }

   private String resolve28(String string) {
      String text85 = this.resolve26(string).replace(' ', ' ').replaceAll("^[\\s\\-–—:]+", "").replaceAll("[\\s\\-–—:]+$", "").trim();
      text85 = HolyWorldItemParser.resolve7(text85);
      HolyWorldItemParser.HolyWorldItemParserDisplayEntry holyWorldItemParserDisplayEntry6 = HolyWorldItemParser.resolve(text85);
      return holyWorldItemParserDisplayEntry6 == null ? text85 : holyWorldItemParserDisplayEntry6.label();
   }

   private void invoke53(String string, long l) {
      int intValue48 = 1;
      String text86 = string;
      if (string.matches("(?i)^[xхXХ]?\\d+[xхXХ]?\\s+.*")) {
         String[] texts = string.split("\\s+", 2);
         String text87 = texts[0].replaceAll("[^\\d]", "");
         if (!text87.isEmpty()) {
            intValue48 = Integer.parseInt(text87);
         }

         text86 = texts[1].trim();
      } else if (string.matches("(?i).*\\s+[xхXХ]?\\d+[xхXХ]?$")) {
         int intValue49 = string.lastIndexOf(32);
         String text88 = string.substring(intValue49 + 1).replaceAll("[^\\d]", "");
         if (!text88.isEmpty()) {
            intValue48 = Integer.parseInt(text88);
         }

         text86 = string.substring(0, intValue49).trim();
      }

      ITEMS_2.add(0, new AutoBuy.AutoBuyState(string, text86, intValue48, l, System.currentTimeMillis()));
      if (ITEMS_2.size() > 200) {
         ITEMS_2.remove(ITEMS_2.size() - 1);
      }

      if (ClientUtil.telegramNotifications.isEnabled()) {
         ru.metaculture.protection.TelegramApi.invoke2("[AutoBuy] Успешно куплено: " + string + " за " + l);
      }
   }

   @EventHandler
   public void onPacket(PacketEvent packetEvent) {
      boolean flag19 = this.autoParse.isEnabled() || this.flag7 || this.flag9;
      if (this.enabled || flag19) {
         if (this.enabled && !packetEvent.check()) {
            if (packetEvent.getPacket() instanceof InventoryS2CPacket inventoryS2CPacket) {
               this.auctionClickTracker.invoke5(inventoryS2CPacket.syncId());
            } else if (packetEvent.getPacket() instanceof ScreenHandlerSlotUpdateS2CPacket screenHandlerSlotUpdateS2CPacket) {
               this.auctionClickTracker.invoke6(screenHandlerSlotUpdateS2CPacket.getSyncId());
            } else if (packetEvent.getPacket() instanceof OpenScreenS2CPacket openScreenS2CPacket) {
               this.auctionClickTracker.invoke7(openScreenS2CPacket.getSyncId());
            }
         }

         if (packetEvent.getPacket() instanceof GameMessageS2CPacket gameMessageS2CPacket) {
            String text89 = gameMessageS2CPacket.content().getString();
            if (flag19 && this.check65(text89)) {
               return;
            }

            if (!this.enabled) {
               return;
            }

            if (text89.contains("Вы успешно купили") || text89.contains("Вы купили")) {
               long longValue43 = this.timestamp19;
               boolean flag20 = this.check31()
                  || (this.rezhimServera.is("FunTime") || this.rezhimServera.is("SpookyTime"))
                     && longValue43 != 0L
                     && System.currentTimeMillis() - longValue43 <= 15000L;
               this.timestamp19 = 0L;
               this.auctionClickTracker.invoke3();
               this.invoke40();
               if (!flag20) {
                  return;
               }

               boolean flag21 = AutoSell.instance != null && AutoSell.instance.enabled && AutoSell.instance.check();
               if (CLIENT.player != null && CLIENT.currentScreen != null) {
                  CLIENT.player.closeScreen();
               }

               try {
                  this.invoke50(text89);
               } catch (Exception exception13) {
               }

               if (this.rezhimServera.is("HolyWorld")) {
                  this.invoke18(flag21);
               } else {
                  FunTimeAuctionHelper.invoke5(flag21);
               }
            } else if (text89.contains("Не удалось выставить") && text89.contains("освободите хранилище")) {
               FunTimeAuctionHelper.invoke7();
               if (!this.check60()) {
                  AutoSell.invoke2();
                  ChatUtil.sendClientMessage("§c[AutoBuy] Хранилище заполнено. Продажа приостановлена.");
                  FunTimeAuctionHelper.invoke11(true);
               }
            } else if (this.check61(text89)) {
               FunTimeAuctionHelper.invoke8();
               ChatUtil.sendClientMessage("§a[AutoBuy] Товар продан! Хранилище освободилось.");
               if (!this.check60()) {
                  FunTimeAuctionHelper.invoke11(true);
               }
            } else if (this.rezhimServera.is("FunTime") && !flag && !flag2 && this.check58(text89)) {
               this.invoke59(text89);
            } else if ((this.rezhimServera.is("HolyWorld") || this.rezhimServera.is("FunTime"))
               && !flag
               && !flag2
               && this.check59(text89)) {
               this.invoke58(text89);
            } else if (this.check63(text89)) {
               this.invoke40();
               if (CLIENT.player != null) {
                  if (CLIENT.currentScreen != null) {
                     CLIENT.player.closeScreen();
                  }

                  this.invoke57(500L, true);
               }
            } else if (text89.contains("Предмет уже продан") || text89.contains("уже купили") || text89.contains("Недостаточно")) {
               this.invoke40();
               ChatUtil.sendClientMessage("§c[AutoBuy] §fНе удалось купить! (Предмет продан или ошибка)");
               this.invoke57(500L, true);
            } else if (text89.contains("Такого предмета Не существует")) {
               if (this.flag7) {
                  this.flag7 = false;
                  this.intValue3++;
                  this.dualTimer6.invoke();
                  ChatUtil.sendClientMessage("§e[AutoParse] §fПредмет не существует на сервере, скип.");
               }
            } else if (text89.contains("выставлен на продажу за")) {
               FunTimeAuctionHelper.invoke6();
               if (AutoSell.instance == null || !AutoSell.instance.enabled) {
                  FunTimeAuctionHelper.invoke3(true);
               }
            } else if (this.rezhimServera.is("FunTime") && text89.contains("Вы уже подключены к этому серверу")) {
               this.compute30();
            } else if (this.rezhimServera.is("FunTime") && this.check64(text89)) {
               this.compute30();
            } else if (this.rezhimServera.is("FunTime")
               && (text89.contains("Недопустимо нажимать в режиме AFK") || text89.contains("Данная команда недоступна в режиме AFK"))) {
               this.invoke60();
            }
         }
      }
   }

   private int compute30() {
      return this.compute31(true);
   }

   private int compute31(boolean bl) {
      if (CLIENT.player != null && this.rezhimServera.is("FunTime")) {
         int intValue50 = this.compute38();
         int intValue51 = intValue50 != -1 ? intValue50 : this.intValue;

         int intValue52;
         do {
            intValue52 = ThreadLocalRandom.current().nextInt(901, 904);
         } while (intValue52 == intValue51);

         this.intValue = intValue52;
         this.auctionClickTracker.invoke3();
         if (CLIENT.currentScreen != null) {
            CLIENT.player.closeScreen();
         }

         CLIENT.player.networkHandler.sendChatCommand("an" + intValue52);
         this.dualTimer4.invoke();
         if (bl) {
            this.intValue7++;
            if (this.intValue7 > 3) {
               this.intValue7 = 0;
               this.invoke42();
            } else {
               this.invoke54(String.valueOf(intValue52));
            }
         }

         return intValue52;
      } else {
         return -1;
      }
   }

   private long compute32() {
      if (this.intValue2 == 0) {
         return ThreadLocalRandom.current().nextLong(9000L, 12000L);
      } else {
         return this.intValue2 < 5 ? ThreadLocalRandom.current().nextLong(1200L, 2200L) : ThreadLocalRandom.current().nextLong(3000L, 4500L);
      }
   }

   private long compute33() {
      return ThreadLocalRandom.current().nextLong(2000L, 4501L);
   }

   private long compute34() {
      return ThreadLocalRandom.current().nextLong(600L, 1401L);
   }

   private void invoke54(String string) {
      this.invoke40();
      this.auctionClickTracker.invoke3();
      this.text2 = string == null ? "" : string;
      this.flag5 = true;
      this.intValue2 = 0;
      this.timestamp5 = System.currentTimeMillis();
      this.timestamp6 = System.currentTimeMillis() + this.compute33();
      this.flag10 = false;
      this.flag11 = false;
      this.DynamicButtonSetting = -1;
      this.timestamp9 = 0L;
      this.flag18 = false;
      this.timestamp20 = 0L;
      this.dualTimer5.invoke();
   }

   void invoke55() {
      this.invoke57(0L, false);
   }

   private void invoke56(long l) {
      this.invoke57(l, false);
   }

   private void invoke57(long l, boolean bl) {
      if (CLIENT.player != null) {
         this.invoke40();
         this.auctionClickTracker.invoke3();
         this.DynamicButtonSetting = this.compute35();
         if (bl && CLIENT.currentScreen != null) {
            CLIENT.player.closeScreen();
         }

         this.flag5 = true;
         this.intValue2 = 0;
         this.text2 = "";
         this.timestamp5 = System.currentTimeMillis();
         this.timestamp6 = System.currentTimeMillis() + Math.max(0L, l);
         this.flag10 = bl;
         this.flag11 = false;
         this.timestamp9 = this.timestamp6 + this.compute34();
         this.flag18 = false;
         this.timestamp20 = 0L;
         this.dualTimer5.invoke();
      }
   }

   private boolean check54() {
      if (!this.flag10 && CLIENT.currentScreen instanceof GenericContainerScreen genericContainerScreen6 && this.check57(genericContainerScreen6)) {
         this.flag5 = false;
         this.intValue2 = 0;
         this.text2 = "";
         this.timestamp5 = 0L;
         this.timestamp6 = 0L;
         this.flag11 = false;
         this.DynamicButtonSetting = -1;
         this.timestamp9 = 0L;
         this.dualTimer11.invoke();
         return false;
      } else if (!this.check55()) {
         return true;
      } else if (System.currentTimeMillis() < this.timestamp6) {
         return true;
      } else {
         if (this.intValue2 == 0 || this.dualTimer5.check5(this.compute32())) {
            if (this.flag10 && CLIENT.currentScreen != null) {
               CLIENT.player.closeScreen();
            }

            CLIENT.player.networkHandler.sendChatCommand("ah");
            this.intValue2++;
            this.flag10 = false;
            this.timestamp9 = System.currentTimeMillis() + this.compute34();
            this.dualTimer5.invoke();
         }

         return true;
      }
   }

   private boolean check55() {
      if (this.text2.isEmpty()) {
         return true;
      } else {
         String text90 = this.resolve29();
         long longValue44 = System.currentTimeMillis() - this.timestamp5;
         boolean flag22 = this.text2.equals(text90) && longValue44 >= 2500L;
         if (!flag22 && !this.flag11 && longValue44 >= 4500L && CLIENT.player != null) {
            CLIENT.player.networkHandler.sendChatCommand("an" + this.text2);
            this.flag11 = true;
            this.timestamp5 = System.currentTimeMillis();
            return false;
         } else {
            longValue44 = System.currentTimeMillis() - this.timestamp5;
            if (!flag22 && longValue44 < 12000L) {
               return false;
            } else {
               this.text2 = "";
               this.flag11 = false;
               return true;
            }
         }
      }
   }

   private boolean check56() {
      if (CLIENT.player != null && !flag && !flag2 && !this.flag6 && !this.autoParse.isEnabled() && !this.flag7) {
         Screen screen2 = CLIENT.currentScreen;
         if (!(screen2 instanceof AutoBuyScreen) && !(screen2 instanceof ModernClickGuiScreen)) {
            if (screen2 instanceof GenericContainerScreen genericContainerScreen7) {
               if (this.check19(genericContainerScreen7)) {
                  this.dualTimer11.invoke();
                  return false;
               }

               if (this.check21(genericContainerScreen7)) {
                  this.dualTimer11.invoke();
                  return false;
               }
            }

            if (!this.dualTimer11.check5(750L)) {
               return false;
            } else {
               this.invoke57(0L, true);
               this.dualTimer11.invoke();
               return true;
            }
         } else {
            this.dualTimer11.invoke();
            return false;
         }
      } else {
         this.dualTimer11.invoke();
         return false;
      }
   }

   private boolean check57(GenericContainerScreen genericContainerScreen) {
      if (!this.check21(genericContainerScreen)) {
         return false;
      } else if (System.currentTimeMillis() < this.timestamp9) {
         return false;
      } else {
         int intValue53 = ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).syncId;
         return this.DynamicButtonSetting == -1 || intValue53 != this.DynamicButtonSetting;
      }
   }

   private int compute35() {
      return CLIENT.currentScreen instanceof GenericContainerScreen genericContainerScreen8 && this.check21(genericContainerScreen8)
         ? ((GenericContainerScreenHandler)genericContainerScreen8.getScreenHandler()).syncId
         : -1;
   }

   private boolean check58(String string) {
      if (string == null) {
         return false;
      } else {
         String text91 = string.replaceAll("§.", "").toLowerCase(Locale.ROOT);
         return text91.contains("после входа на режим") && text91.contains("аукцион") && PATTERN.matcher(string).find();
      }
   }

   private boolean check59(String string) {
      return string == null ? false : PATTERN_2.matcher(this.resolve26(string).toLowerCase(Locale.ROOT)).find();
   }

   private void invoke58(String string) {
      long longValue45 = this.compute37(string);
      if (CLIENT.player != null && CLIENT.currentScreen != null) {
         CLIENT.player.closeScreen();
      }

      this.invoke57(longValue45, true);
   }

   private void invoke59(String string) {
      long longValue46 = this.compute36(string);
      if (CLIENT.player != null && CLIENT.currentScreen != null) {
         CLIENT.player.closeScreen();
      }

      if (this.autoParse.isEnabled()) {
         this.flag7 = false;
         this.timestamp8 = System.currentTimeMillis() + longValue46;
         this.dualTimer6.invoke();
         this.dualTimer7.invoke();
      } else {
         this.invoke56(longValue46);
      }
   }

   private long compute36(String string) {
      Matcher matcher2 = PATTERN.matcher(string == null ? "" : string);
      if (!matcher2.find()) {
         return 9000L;
      } else {
         try {
            int intValue54 = Integer.parseInt(matcher2.group(1));
            return Math.max(9000L, intValue54 * 1000L + 2000L);
         } catch (NumberFormatException numberFormatException) {
            return 9000L;
         }
      }
   }

   private long compute37(String string) {
      Matcher matcher3 = PATTERN_2.matcher(this.resolve26(string).toLowerCase(Locale.ROOT));
      if (!matcher3.find()) {
         return 1250L;
      } else {
         try {
            int intValue55 = Integer.parseInt(matcher3.group(1));
            return Math.max(250L, intValue55 * 1000L + 250L);
         } catch (NumberFormatException numberFormatException2) {
            return 1250L;
         }
      }
   }

   private boolean check60() {
      return AutoSell.instance != null
         && AutoSell.instance.enabled
         && AutoSell.instance.check()
         && AutoSell.instance.check2();
   }

   private boolean check61(String string) {
      if (string == null) {
         return false;
      } else if (string.contains("У Вас купили") && string.contains("на /ah")) {
         return true;
      } else {
         String text92 = this.resolve26(string).toLowerCase(Locale.ROOT);
         return text92.contains("купил у вас") && text92.contains(" за ") && (text92.contains("¤") || text92.contains("$"));
      }
   }

   private void invoke60() {
      if (CLIENT.player != null && this.rezhimServera.is("FunTime")) {
         String text93 = this.resolve29();
         if ("N/A".equals(text93) && !this.text.isEmpty()) {
            text93 = this.text;
         }

         if ("N/A".equals(text93) && this.intValue != -1) {
            text93 = String.valueOf(this.intValue);
         }

         if (!"N/A".equals(text93)) {
            this.text = text93;
            CLIENT.player.networkHandler.sendChatCommand("hub");
            this.flag6 = true;
            this.dualTimer8.invoke();
            ChatUtil.sendClientMessage("§e[AutoBuy] §fAFK заблокировал команду. Переподключаемся через /hub -> /an" + this.text + "...");
         }
      }
   }

   private void invoke61() {
      if (this.rezhimServera.is("FunTime")) {
         int intValue56 = this.compute38();
         if (intValue56 != -1) {
            this.intValue = intValue56;
            this.flag13 = false;
            this.dualTimer10.invoke();
         }
      }
   }

   private boolean check62() {
      if (CLIENT.player != null && !this.flag5 && !this.flag6 && this.intValue != -1 && !this.flag13) {
         if (this.compute38() != -1) {
            return false;
         } else if (!this.dualTimer10.check5(2500L)) {
            return false;
         } else {
            CLIENT.player.networkHandler.sendChatCommand("an" + this.intValue);
            this.flag13 = true;
            this.invoke54(String.valueOf(this.intValue));
            ChatUtil.sendClientMessage("§e[AutoBuy] §fПохоже, нас выкинуло в хаб. Повторно заходим на " + this.intValue + "...");
            return true;
         }
      } else {
         return false;
      }
   }

   private int compute38() {
      String text94 = this.resolve29();
      if ("N/A".equals(text94)) {
         return -1;
      } else {
         try {
            return Integer.parseInt(text94);
         } catch (NumberFormatException numberFormatException3) {
            return -1;
         }
      }
   }

   private String resolve29() {
      try {
         ServerStatsParser.INSTANCE.invoke2();
         String text95 = ServerStatsParser.INSTANCE.getNA2();
         return text95 != null && !text95.isEmpty() ? text95 : "N/A";
      } catch (Exception exception14) {
         return "N/A";
      }
   }

   private boolean check63(String string) {
      if (string == null) {
         return false;
      } else if (string.contains("Этот товар уже Купили!")) {
         return true;
      } else if (!this.rezhimServera.is("FunTime")) {
         return false;
      } else {
         String text96 = string.toLowerCase(Locale.ROOT);
         return text96.contains("ошибка! этот товар уже купили") || text96.contains("ошибка") && text96.contains("товар уже купили");
      }
   }

   private boolean check64(String string) {
      if (string == null) {
         return false;
      } else {
         String text97 = string.replaceAll("§.", "").toLowerCase(Locale.ROOT);
         return text97.contains("были кикнуты при подключении") && text97.contains("сервер заполнен");
      }
   }

   public void invoke62() {
      if (this.autoParse.isEnabled()) {
         this.invoke63();
      } else {
         if (this.check16(false) && !this.enabled) {
            this.invoke65();
         }
      }
   }

   private void invoke63() {
      this.autoParse.setValue(false);
      this.flag7 = false;
      this.intValue3 = 0;
      this.flag8 = false;
      this.flag9 = false;
      this.text3 = "";
      this.timestamp7 = 0L;
      this.timestamp8 = 0L;
      this.dualTimer6.invoke();
      this.dualTimer7.invoke();
      this.invoke30();
      this.invoke66();
   }

   private void invoke64() {
      if (!this.autoParse.isEnabled()) {
         this.invoke66();
      } else if (!this.flag8 && !this.check16(false)) {
         this.invoke66();
      } else {
         if (!this.items.isEmpty()) {
            this.invoke34();
         }
      }
   }

   private boolean check65(String string) {
      if (string == null) {
         return false;
      } else if (this.rezhimServera.is("FunTime") && this.check58(string)) {
         this.invoke59(string);
         return true;
      } else if (string.contains("Такого предмета Не существует") && this.flag7) {
         this.flag7 = false;
         this.intValue3++;
         this.dualTimer6.invoke();
         ChatUtil.sendClientMessage("§e[AutoParse] §fПредмет не существует на сервере, скип.");
         return true;
      } else {
         return false;
      }
   }

   private void invoke65() {
      if (!this.enabled) {
         EventManager.register(this);
      }
   }

   private void invoke66() {
      if (!this.enabled) {
         EventManager.unregister(this);
      }
   }

   record AutoBuyData(RegistryEntry<EntityAttribute> attribute, double value) {
   }

   record AutoBuyItemData(String itemName, long maxPrice, HolyWorldItemParser.HolyWorldItemParserDisplayEntry holyWorldEntry, Item item, String normalizedName) {
   }

   record AutoBuyPriceData(long price, String seller) {
   }

   final class AutoBuyItemState {
      private final ItemStack itemStack;
      private String text;
      private String text2;
      private String text3;
      private String text4;
      private Identifier identifier;

      AutoBuyItemState(ItemStack itemStack) {
         this.itemStack = itemStack;
      }

      String resolve() {
         if (this.text == null) {
            this.text = HolyWorldItemParser.resolve5(this.itemStack);
         }

         return this.text;
      }

      String resolve2() {
         if (this.text2 == null) {
            this.text2 = HolyWorldItemParser.resolve6(this.itemStack);
         }

         return this.text2;
      }

      String resolve3() {
         if (this.text3 == null) {
            this.text3 = this.itemStack.getName().getString();
         }

         return this.text3;
      }

      String resolve4() {
         if (this.text4 == null) {
            this.text4 = AutoBuy.this.resolve23(this.resolve3());
         }

         return this.text4;
      }

      Identifier resolve5() {
         if (this.identifier == null) {
            this.identifier = Registries.ITEM.getId(this.itemStack.getItem());
         }

         return this.identifier;
      }
   }

   record AutoBuyPriceData2(long unitPrice, long lotPrice, int count) {
   }

   public static class AutoBuyState {
      public String text;
      public String text2;
      public int intValue;
      public long timestamp;
      public long timestamp2;

      public AutoBuyState(String string, String string2, int i, long l, long m) {
         this.text = string;
         this.text2 = string2;
         this.intValue = i;
         this.timestamp = l;
         this.timestamp2 = m;
      }
   }

   record AutoBuyPriceData3(long lotPrice, long estimatedValue, long profit, int fingerprint, boolean buyable) {
   }
}
