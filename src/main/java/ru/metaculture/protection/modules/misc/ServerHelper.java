package ru.metaculture.protection;

import com.google.gson.JsonObject;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline.Snippet;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.vertex.VertexFormat.DrawMode;
import java.awt.Color;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.OptionalDouble;
import java.util.Queue;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.RenderLayer.MultiPhaseParameters;
import net.minecraft.client.render.RenderPhase.LineWidth;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.util.InputUtil;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos.Mutable;
import org.joml.Matrix4f;
import org.wild.mixin.acceser.ClientPlayerInteractionManagerAccessor;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "ServerHelper",
   description = "Позволяет юзать предметы по бинду",
   category = Category.Misc
)
public class ServerHelper extends Module {
   public static ServerHelper instance;
   private static final String KLAVISHA_TRAPKI = "Клавиша трапки";
   private static final String KLAVISHA_TRAPKI_FUNTIME = "Клавиша трапки [FunTime]";
   private static final String KLAVISHA_TRAPKI_HOLYWORLD = "Клавиша трапки [HolyWorld]";
   private static final String KLAVISHA_SNEZHKA_ZAMOROZKI = "Клавиша снежка заморозки";
   private static final String KLAVISHA_SNEZHKA_ZAMOROZKI_FUNTIME = "Клавиша снежка заморозки [FunTime]";
   private static final String KLAVISHA_SNEZHKA_ZAMOROZKI_HOLYWORLD = "Клавиша снежка заморозки [HolyWorld]";
   public final ModeSetting rezhimRaboty = new ModeSetting("Режим работы", "FunTime", "FunTime", "HolyWorld");
   public final ModeSetting opredeleniePredmeta = new ModeSetting("Определение предмета", "По атрибуту", "По атрибуту", "По названию")
      .setVisibilityCondition(() -> !this.rezhimRaboty.is("FunTime"));
   public final GroupSetting dopolnitelnyeNastroyki = new GroupSetting(
      "Дополнительные настройки",
      new BooleanSetting("Стопы", true),
      new BooleanSetting("Рендерить границы", true),
      new BooleanSetting("Рендерить границы сквозь стены", false),
      new BooleanSetting("Авто GPS на ивенты", true)
   );
   public final KeybindSetting klavishaDezorientatsii = new KeybindSetting("Клавиша дезориентации", -1, true).visibleWhen(() -> !this.rezhimRaboty.is("FunTime"));
   public final KeybindSetting klavishaYavnoyPyli = new KeybindSetting("Клавиша явной пыли", -1, true).visibleWhen(() -> !this.rezhimRaboty.is("FunTime"));
   public final KeybindSetting klavishaBozheyAury = new KeybindSetting("Клавиша божьей ауры", -1, true).visibleWhen(() -> !this.rezhimRaboty.is("FunTime"));
   public final KeybindSetting klavishaPlasta = new KeybindSetting("Клавиша пласта", -1, true).visibleWhen(() -> !this.rezhimRaboty.is("FunTime"));
   public final KeybindSetting klavishaTrapki = new KeybindSetting("Клавиша трапки", -1, true).visibleWhen(() -> !this.rezhimRaboty.is("FunTime"));
   public final KeybindSetting klavishaSnezhkaZamorozki = new KeybindSetting("Клавиша снежка заморозки", -1, true)
      .visibleWhen(() -> !this.rezhimRaboty.is("FunTime"));
   public final KeybindSetting klavishaZelyaAssasina = new KeybindSetting("Клавиша зелья ассасина", -1, true)
      .visibleWhen(() -> !this.rezhimRaboty.is("FunTime"));
   public final KeybindSetting klavishaZelyaPaladina = new KeybindSetting("Клавиша зелья паладина", -1, true)
      .visibleWhen(() -> !this.rezhimRaboty.is("FunTime"));
   public final KeybindSetting klavishaZelyaSnotvornogo = new KeybindSetting("Клавиша зелья снотворного", -1, true)
      .visibleWhen(() -> !this.rezhimRaboty.is("FunTime"));
   public final KeybindSetting klavishaZelyaGneva = new KeybindSetting("Клавиша зелья гнева", -1, true).visibleWhen(() -> !this.rezhimRaboty.is("FunTime"));
   public final KeybindSetting klavishaZelyaSvyatayaVoda = new KeybindSetting("Клавиша зелья святая вода", -1, true)
      .visibleWhen(() -> !this.rezhimRaboty.is("FunTime"));
   public final KeybindSetting klavishaZelyaRadiatsii = new KeybindSetting("Клавиша зелья радиации", -1, true).visibleWhen(() -> !this.rezhimRaboty.is("FunTime"));
   public final KeybindSetting klavishaZelyaHlopushki = new KeybindSetting("Клавиша зелья хлопушки", -1, true).visibleWhen(() -> !this.rezhimRaboty.is("FunTime"));
   public final KeybindSetting klavishaTrapki2 = new KeybindSetting("Клавиша трапки", -1, true).visibleWhen(() -> !this.rezhimRaboty.is("HolyWorld"));
   public final KeybindSetting klavishaSnezhkaZamorozki2 = new KeybindSetting("Клавиша снежка заморозки", -1, true)
      .visibleWhen(() -> !this.rezhimRaboty.is("HolyWorld"));
   public final KeybindSetting klavishaStana = new KeybindSetting("Клавиша стана", -1, true).visibleWhen(() -> !this.rezhimRaboty.is("HolyWorld"));
   public final KeybindSetting klavishaVzryvnoyTrapki = new KeybindSetting("Клавиша взрывной трапки", -1, true)
      .visibleWhen(() -> !this.rezhimRaboty.is("HolyWorld"));
   public final KeybindSetting klavishaShalkera = new KeybindSetting("Клавиша шалкера", -1, false);
   public final KeybindSetting klavishaVozduhana = new KeybindSetting("Клавиша воздухана", -1, false);
   public final BooleanSetting kidatPodSebya = new BooleanSetting("Кидать под себя", false);
   public final BooleanSetting proektsiyaMegaBuldozera = new BooleanSetting("Проекция Мега-бульдозера", true).visibleWhen(() -> !this.rezhimRaboty.is("FunTime"));
   public final KeybindSetting horus = new KeybindSetting("Хорус", -1, false);
   public final BooleanSetting taymeryStruktur = new BooleanSetting("Таймеры структур", false).visibleWhen(() -> !this.rezhimRaboty.is("FunTime"));
   public final ModeSetting tipIventa = new ModeSetting("Тип ивента", "Фантайм", "Фантайм", "Спуки тайм")
      .setVisibilityCondition(() -> !this.rezhimRaboty.is("FunTime") || !this.taymeryStruktur.isEnabled());
   public final BooleanSetting prevyu = new BooleanSetting("Превью", false)
      .visibleWhen(() -> !this.rezhimRaboty.is("FunTime") || !this.taymeryStruktur.isEnabled());
   public final BooleanSetting logZvukovDebag = new BooleanSetting("Лог звуков (дебаг)", false)
      .visibleWhen(() -> !this.rezhimRaboty.is("FunTime") || !this.taymeryStruktur.isEnabled());
   public final BooleanSetting logBlokaDebag = new BooleanSetting("Лог блока (дебаг)", false)
      .visibleWhen(() -> !this.rezhimRaboty.is("FunTime") || !this.taymeryStruktur.isEnabled());
   private long timestamp = 0L;
   private long timestamp2 = 0L;
   private static final long TIMESTAMP = 150L;
   private static final long TIMESTAMP_2 = 5000L;
   private static final double DOUBLE_VALUE = 0.25;
   private static final long TIMESTAMP_3 = 0L;
   private static final long TIMESTAMP_4 = 800L;
   private static final int INT_VALUE = 3;
   private static final long TIMESTAMP_5 = 150L;
   private static final int INT_VALUE_2 = 3;
   private static final int INT_VALUE_3 = 1;
   private static final float FLOAT_VALUE = 90.0F;
   private static final float FLOAT_VALUE_2 = 180.0F;
   private static final float FLOAT_VALUE_3 = 180.0F;
   private static final int INT_VALUE_4 = 1;
   private static final int INT_VALUE_5 = 30;
   private static final int INT_VALUE_6 = 15;
   private static final double DOUBLE_VALUE_2 = 0.28;
   private final Queue<ServerHelper.ServerHelperItemState> queue = new ArrayDeque<>();
   private int intValue = 0;
   private int intValue2 = 0;
   private int intValue3 = -1;
   private int intValue4 = -1;
   public static boolean flag = false;
   private static final Animation ANIMATION = new Animation();
   private static final Animation ANIMATION_2 = new Animation();
   private static final Animation ANIMATION_3 = new Animation();
   private static final Animation ANIMATION_4 = new Animation();
   private static boolean flag2;
   private float floatValue = 100.0F;
   private float floatValue2 = 100.0F;
   private ServerHelper.ServerHelperState6 serverHelperState6 = ServerHelper.ServerHelperState6.IDLE;
   private final RotationResetController rotationResetController = new RotationResetController();
   private final DualTimer dualTimer = new DualTimer();
   private final DualTimer dualTimer2 = new DualTimer();
   private int intValue5 = -1;
   private int intValue6 = -1;
   private boolean flag3 = false;
   private int intValue7 = -1;
   private int intValue8 = -1;
   private Item item = null;
   private int intValue9 = 0;
   private final DualTimer dualTimer3 = new DualTimer();
   private final DualTimer dualTimer4 = new DualTimer();
   private boolean flag4 = false;
   private boolean flag5 = false;
   private boolean flag6 = false;
   private boolean flag7 = false;
   private boolean flag8 = false;
   private boolean flag9 = false;
   private int intValue10 = 0;
   private boolean flag10 = false;
   private boolean flag11 = false;
   private Vec3d vec3d = Vec3d.ZERO;
   private ServerHelper.ServerHelperData serverHelperData;
   private static final long TIMESTAMP_6 = 15000L;
   private static final long TIMESTAMP_7 = 20000L;
   private static final long TIMESTAMP_8 = 60000L;
   private static final long TIMESTAMP_9 = 30000L;
   private static final long TIMESTAMP_10 = 20000L;
   private static final String BLOCK_PISTON_EXTEND = "block.piston.extend";
   private static final String BLOCK_ANVIL_PLACE = "block.anvil.place";
   private static final String ENTITY_ENDER_DRAGON_GROWL = "entity.ender_dragon.growl";
   private static final long TIMESTAMP_11 = 250L;
   private static final double DOUBLE_VALUE_3 = 16.0;
   private static final long TIMESTAMP_12 = 180L;
   private static final long TIMESTAMP_13 = 1500L;
   private static final int DynamicButtonSetting = 128;
   private static final ServerHelper.ServerHelperState[] SERVER_HELPER_STATES = new ServerHelper.ServerHelperState[]{
      new ServerHelper.ServerHelperState(
         "Драконий скин",
         30000L,
         0L,
         new ServerHelper.ServerHelperState3("entity.wither.break_block", 1.0F, 0.7F),
         new ServerHelper.ServerHelperState3("entity.ender_dragon.growl", 1.5F, 0.2F),
         new ServerHelper.ServerHelperState3("ui.toast.challenge_complete", 1.5F, 0.35F),
         new ServerHelper.ServerHelperState3("entity.evoker_fangs.attack", 0.85F, 0.5F)
      )
   };
   private static ItemStack itemStack;
   private static ItemStack itemStack2;
   private static ItemStack itemStack3;
   private static ItemStack SpacerSetting;
   private static final ServerHelper.ServerHelperState5[] FoundryShaderSetting = new ServerHelper.ServerHelperState5[]{
      ServerHelper.ServerHelperState5.TRAPKA, ServerHelper.ServerHelperState5.PLAST, ServerHelper.ServerHelperState5.DRAGON_TRAP, ServerHelper.ServerHelperState5.DRAGON_PLAST
   };
   private static final int[][] INTS = new int[][]{{1, 0, 0}, {-1, 0, 0}, {0, 1, 0}, {0, -1, 0}, {0, 0, 1}, {0, 0, -1}};
   private final List<ServerHelper.ServerHelperState4> items = new ArrayList<>();
   private final ArrayDeque<ServerHelper.ServerHelperState2> arrayDeque = new ArrayDeque<>();
   private int intValue11 = -1;
   private String text = "";
   private static final Set<Block> VALUES = Set.of(
      Blocks.GRASS_BLOCK,
      Blocks.DIRT,
      Blocks.COARSE_DIRT,
      Blocks.PODZOL,
      Blocks.ROOTED_DIRT,
      Blocks.MUD,
      Blocks.MYCELIUM,
      Blocks.MOSS_BLOCK,
      Blocks.DIRT_PATH,
      Blocks.FARMLAND,
      Blocks.STONE,
      Blocks.GRANITE,
      Blocks.DIORITE,
      Blocks.ANDESITE,
      Blocks.DEEPSLATE,
      Blocks.COBBLED_DEEPSLATE,
      Blocks.TUFF,
      Blocks.CALCITE,
      Blocks.COBBLESTONE,
      Blocks.MOSSY_COBBLESTONE,
      Blocks.GRAVEL,
      Blocks.SAND,
      Blocks.RED_SAND,
      Blocks.SANDSTONE,
      Blocks.CLAY,
      Blocks.BEDROCK,
      Blocks.DEAD_TUBE_CORAL_BLOCK,
      Blocks.SNOW_BLOCK,
      Blocks.ICE,
      Blocks.PACKED_ICE,
      Blocks.BLUE_ICE,
      Blocks.MAGMA_BLOCK,
      Blocks.NETHERRACK
   );
   private static final Pattern PATTERN = Pattern.compile("координатах\\s+(-?\\d+)\\s+(-?\\d+)\\s+(-?\\d+)");
   private static final int INT_VALUE_7 = 1024;
   private static final RenderPipeline RENDER_PIPELINE = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.POSITION_COLOR_SNIPPET})
         .withLocation(Identifier.of("wild", "helper_box"))
         .withVertexFormat(VertexFormats.POSITION_COLOR, DrawMode.QUADS)
         .withCull(false)
         .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
         .withDepthWrite(false)
         .withBlend(BlendFunction.LIGHTNING)
         .build()
   );
   private static final RenderLayer RENDER_LAYER = RenderLayer.of("helper_box", 1024, false, true, RENDER_PIPELINE, MultiPhaseParameters.builder().build(false));
   private static final RenderPipeline RENDER_PIPELINE_2 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.POSITION_COLOR_SNIPPET})
         .withLocation(Identifier.of("wild", "helper_lines"))
         .withVertexFormat(VertexFormats.POSITION_COLOR, DrawMode.DEBUG_LINES)
         .withCull(false)
         .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
         .withDepthWrite(false)
         .withBlend(BlendFunction.LIGHTNING)
         .build()
   );
   private static final RenderLayer RENDER_LAYER_2 = RenderLayer.of(
      "helper_lines", 1024, false, true, RENDER_PIPELINE_2, MultiPhaseParameters.builder().lineWidth(new LineWidth(OptionalDouble.of(10.0))).build(false)
   );
   private static final RenderPipeline RENDER_PIPELINE_3 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.POSITION_COLOR_SNIPPET})
         .withLocation(Identifier.of("wild", "helper_box_no_depth"))
         .withVertexFormat(VertexFormats.POSITION_COLOR, DrawMode.QUADS)
         .withCull(false)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .withBlend(BlendFunction.LIGHTNING)
         .build()
   );
   private static final RenderLayer RENDER_LAYER_3 = RenderLayer.of(
      "helper_box_no_depth", 1024, false, true, RENDER_PIPELINE_3, MultiPhaseParameters.builder().build(false)
   );
   private static final RenderPipeline RENDER_PIPELINE_4 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.POSITION_COLOR_SNIPPET})
         .withLocation(Identifier.of("wild", "helper_lines_no_depth"))
         .withVertexFormat(VertexFormats.POSITION_COLOR, DrawMode.DEBUG_LINES)
         .withCull(false)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .withBlend(BlendFunction.LIGHTNING)
         .build()
   );
   private static final RenderLayer RENDER_LAYER_4 = RenderLayer.of(
      "helper_lines_no_depth", 1024, false, true, RENDER_PIPELINE_4, MultiPhaseParameters.builder().lineWidth(new LineWidth(OptionalDouble.of(10.0))).build(false)
   );

   public ServerHelper() {
      instance = this;
      this.klavishaTrapki.withName("Клавиша трапки [FunTime]");
      this.klavishaTrapki2.withName("Клавиша трапки [HolyWorld]");
      this.klavishaSnezhkaZamorozki.withName("Клавиша снежка заморозки [FunTime]");
      this.klavishaSnezhkaZamorozki2.withName("Клавиша снежка заморозки [HolyWorld]");
      this.addSettings(
         new Setting[]{
            this.rezhimRaboty,
            this.opredeleniePredmeta,
            this.dopolnitelnyeNastroyki,
            this.klavishaDezorientatsii,
            this.klavishaYavnoyPyli,
            this.klavishaBozheyAury,
            this.klavishaPlasta,
            this.klavishaTrapki,
            this.klavishaSnezhkaZamorozki,
            this.klavishaZelyaAssasina,
            this.klavishaZelyaPaladina,
            this.klavishaZelyaSnotvornogo,
            this.klavishaZelyaGneva,
            this.klavishaZelyaSvyatayaVoda,
            this.klavishaZelyaRadiatsii,
            this.klavishaZelyaHlopushki,
            this.klavishaStana,
            this.klavishaTrapki2,
            this.klavishaSnezhkaZamorozki2,
            this.klavishaVzryvnoyTrapki,
            this.klavishaShalkera,
            this.klavishaVozduhana,
            this.kidatPodSebya,
            this.proektsiyaMegaBuldozera,
            this.horus,
            this.taymeryStruktur,
            this.tipIventa
         }
      );
   }

   @Override
   public void loadFromJson(JsonObject jsonObject) {
      JsonObject jsonObject2 = jsonObject == null ? null : jsonObject.deepCopy();
      if (jsonObject2 != null && jsonObject2.has("Settings")) {
         try {
            JsonObject jsonObject3 = jsonObject2.getAsJsonObject("Settings");
            if (jsonObject3.has("Клавиша трапки")) {
               int intValue = jsonObject3.get("Клавиша трапки").getAsInt();
               if (!jsonObject3.has("Клавиша трапки [FunTime]")) {
                  jsonObject3.addProperty("Клавиша трапки [FunTime]", intValue);
               }

               if (!jsonObject3.has("Клавиша трапки [HolyWorld]")) {
                  jsonObject3.addProperty("Клавиша трапки [HolyWorld]", intValue);
               }
            }

            if (jsonObject3.has("Клавиша снежка заморозки")) {
               int intValue2 = jsonObject3.get("Клавиша снежка заморозки").getAsInt();
               if (!jsonObject3.has("Клавиша снежка заморозки [FunTime]")) {
                  jsonObject3.addProperty("Клавиша снежка заморозки [FunTime]", intValue2);
               }

               if (!jsonObject3.has("Клавиша снежка заморозки [HolyWorld]")) {
                  jsonObject3.addProperty("Клавиша снежка заморозки [HolyWorld]", intValue2);
               }
            }
         } catch (Throwable exception) {
         }
      }

      super.loadFromJson(jsonObject2);
   }

   private boolean check(KeybindSetting keybindSetting, RawInputEvent rawInputEvent) {
      if (keybindSetting.getKeyCode() != -1 && rawInputEvent.getKeyCode() == keybindSetting.getKeyCode()) {
         return keybindSetting.allowMouseButtons ? rawInputEvent.getAction() == 0 : rawInputEvent.getAction() == 1;
      } else {
         return false;
      }
   }

   private boolean check2(ItemStack itemStack, String string) {
      return itemStack.getName().getString().toLowerCase(Locale.ROOT).contains(string.toLowerCase(Locale.ROOT));
   }

   private boolean check3(ItemStack itemStack, String... strings) {
      for (String text : strings) {
         if (this.check2(itemStack, text)) {
            return true;
         }
      }

      return false;
   }

   public Predicate<ItemStack> resolve(Predicate<ItemStack> predicate, String... strings) {
      return this.opredeleniePredmeta.is("По названию") ? itemStack -> this.check3(itemStack, strings) : predicate;
   }

   @EventHandler
   public void onRawInput(RawInputEvent rawInputEvent2) {
      if (CLIENT.currentScreen == null) {
         ServerHelper.ServerHelperItemState serverHelperItemState = null;
         if (this.rezhimRaboty.is("FunTime")) {
            if (this.check(this.klavishaDezorientatsii, rawInputEvent2)) {
               serverHelperItemState = new ServerHelper.ServerHelperItemState(this.resolve(SpecialItemUtils::check33, "Дезориентация"), false);
            } else if (this.check(this.klavishaYavnoyPyli, rawInputEvent2)) {
               serverHelperItemState = new ServerHelper.ServerHelperItemState(this.resolve(SpecialItemUtils::check32, "Явная пыль"), false);
            } else if (this.check(this.klavishaPlasta, rawInputEvent2)) {
               serverHelperItemState = new ServerHelper.ServerHelperItemState(this.resolve(SpecialItemUtils::check36, "Пласт"), false);
            } else if (this.check(this.klavishaBozheyAury, rawInputEvent2)) {
               serverHelperItemState = new ServerHelper.ServerHelperItemState(this.resolve(SpecialItemUtils::check54, "Божья аура"), false);
            } else if (this.check(this.klavishaTrapki, rawInputEvent2)) {
               serverHelperItemState = new ServerHelper.ServerHelperItemState(this.resolve(SpecialItemUtils::check34, "Трапка"), false);
            } else if (this.check(this.klavishaSnezhkaZamorozki, rawInputEvent2)) {
               serverHelperItemState = new ServerHelper.ServerHelperItemState(this.resolve(SpecialItemUtils::check53, "Снежок заморозка"), false);
            } else if (this.check(this.klavishaZelyaAssasina, rawInputEvent2)) {
               serverHelperItemState = new ServerHelper.ServerHelperItemState(this.resolve(SpecialItemUtils::check25, "Зелье Ассасина"), false);
            } else if (this.check(this.klavishaZelyaPaladina, rawInputEvent2)) {
               serverHelperItemState = new ServerHelper.ServerHelperItemState(this.resolve(SpecialItemUtils::check29, "Зелье Паладина", "Зелье Палладина"), false);
            } else if (this.check(this.klavishaZelyaSnotvornogo, rawInputEvent2)) {
               serverHelperItemState = new ServerHelper.ServerHelperItemState(this.resolve(SpecialItemUtils::check31, "Снотворное"), false);
            } else if (this.check(this.klavishaZelyaGneva, rawInputEvent2)) {
               serverHelperItemState = new ServerHelper.ServerHelperItemState(this.resolve(SpecialItemUtils::check26, "Зелье Гнева"), false);
            } else if (this.check(this.klavishaZelyaSvyatayaVoda, rawInputEvent2)) {
               serverHelperItemState = new ServerHelper.ServerHelperItemState(this.resolve(SpecialItemUtils::check28, "Святая вода"), false);
            } else if (this.check(this.klavishaZelyaRadiatsii, rawInputEvent2)) {
               serverHelperItemState = new ServerHelper.ServerHelperItemState(this.resolve(SpecialItemUtils::check30, "Зелье Радиации"), false);
            } else if (this.check(this.klavishaZelyaHlopushki, rawInputEvent2)) {
               serverHelperItemState = new ServerHelper.ServerHelperItemState(this.resolve(SpecialItemUtils::check27, "Хлопушка"), false);
            }
         }

         if (this.rezhimRaboty.is("HolyWorld")) {
            if (this.check(this.klavishaTrapki2, rawInputEvent2)) {
               serverHelperItemState = new ServerHelper.ServerHelperItemState(HolyWorldItemParser::check3, false);
            } else if (this.check(this.klavishaSnezhkaZamorozki2, rawInputEvent2)) {
               serverHelperItemState = new ServerHelper.ServerHelperItemState(HolyWorldItemParser::check4, false);
            } else if (this.check(this.klavishaStana, rawInputEvent2)) {
               serverHelperItemState = new ServerHelper.ServerHelperItemState(HolyWorldItemParser::check5, false);
            } else if (this.check(this.klavishaVzryvnoyTrapki, rawInputEvent2)) {
               serverHelperItemState = new ServerHelper.ServerHelperItemState(HolyWorldItemParser::check6, false);
            }
         }

         if (this.check(this.klavishaShalkera, rawInputEvent2)) {
            serverHelperItemState = new ServerHelper.ServerHelperItemState(itemStack -> itemStack.getItem().toString().contains("shulker_box"), true);
         }

         if (this.check(this.klavishaVozduhana, rawInputEvent2)) {
            serverHelperItemState = new ServerHelper.ServerHelperItemState(itemStack -> itemStack.isOf(Items.WIND_CHARGE), false, false, this.kidatPodSebya.isEnabled());
         }

         if (this.check(this.horus, rawInputEvent2)) {
            serverHelperItemState = new ServerHelper.ServerHelperItemState(itemStack -> itemStack.isOf(Items.CHORUS_FRUIT), false, true);
         }

         if (serverHelperItemState != null && System.currentTimeMillis() - this.timestamp2 >= 150L) {
            this.timestamp2 = System.currentTimeMillis();
            this.queue.add(serverHelperItemState);
         }
      }
   }

   @EventHandler
   private void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player != null && CLIENT.interactionManager != null) {
         this.invoke24();
         this.invoke10();
         if (this.serverHelperState6 == ServerHelper.ServerHelperState6.IDLE) {
            if (!this.queue.isEmpty()) {
               ServerHelper.ServerHelperItemState serverHelperItemState2 = this.queue.poll();
               int intValue3 = -1;

               for (int intValue4 = 0; intValue4 < 36; intValue4++) {
                  ItemStack itemStack2 = CLIENT.player.getInventory().getStack(intValue4);
                  if (itemStack2 != null && !itemStack2.isEmpty() && serverHelperItemState2.predicate.test(itemStack2)) {
                     intValue3 = intValue4;
                     break;
                  }
               }

               if (intValue3 == -1) {
                  return;
               }

               this.intValue5 = intValue3;
               this.intValue6 = CLIENT.player.getInventory().getSelectedSlot();
               this.flag4 = serverHelperItemState2.flag;
               this.flag5 = serverHelperItemState2.flag2;
               this.flag6 = serverHelperItemState2.flag3;
               this.flag7 = intValue3 >= 9 && !serverHelperItemState2.flag;
               if (this.flag7) {
                  this.intValue7 = this.intValue6;
                  this.intValue8 = intValue3;
                  this.item = CLIENT.player.getInventory().getStack(this.intValue6).getItem();
               }

               this.flag8 = false;
               this.flag9 = false;
               this.intValue10 = 0;
               this.flag10 = false;
               this.flag11 = false;
               this.vec3d = CLIENT.player.getPos();
               this.intValue2 = 0;
               this.dualTimer.invoke();
               this.dualTimer2.invoke();
               this.serverHelperState6 = ServerHelper.ServerHelperState6.PREPARE;
               flag = true;
               if (this.check4()) {
                  this.invoke6();
               } else {
                  this.invoke();
               }
            }
         } else {
            if (!this.check4()) {
               this.invoke();
               Sprint.intValue = 2;
               CLIENT.options.sprintKey.setPressed(false);
               CLIENT.player.setSprinting(false);
            }

            switch (this.serverHelperState6) {
               case PREPARE:
                  if (this.flag4) {
                     if (this.dualTimer.check(0L)) {
                        this.dualTimer.invoke();
                        int intValue5 = this.intValue5 < 9 ? 36 + this.intValue5 : this.intValue5;
                        CLIENT.interactionManager
                           .clickSlot(CLIENT.player.playerScreenHandler.syncId, intValue5, 1, SlotActionType.PICKUP, CLIENT.player);
                        this.serverHelperState6 = ServerHelper.ServerHelperState6.COOLDOWN;
                     }
                  } else if (this.check4()) {
                     this.invoke3();
                  } else {
                     this.serverHelperState6 = ServerHelper.ServerHelperState6.SWAP;
                  }
                  break;
               case PRE_SWAP_STOP:
                  this.invoke6();
                  if (!this.check5()) {
                     return;
                  }

                  this.invoke7();
                  if (this.flag5 && !this.check7()) {
                     this.invoke20();
                     return;
                  }

                  if (this.flag5) {
                     if (this.intValue5 < 9 && this.check7()) {
                        this.invoke15();
                     } else {
                        this.intValue2 = 0;
                        this.serverHelperState6 = ServerHelper.ServerHelperState6.WAIT_MAIN_HAND;
                     }
                  } else {
                     this.invoke11();
                     this.invoke4();
                  }
                  break;
               case WAIT_MAIN_HAND:
                  this.invoke6();
                  if (this.flag5 && this.check7()) {
                     this.invoke15();
                     return;
                  }

                  if (this.intValue2++ >= 3) {
                     this.invoke20();
                  }
                  break;
               case SWAP:
                  if (this.intValue5 >= 9) {
                     this.invoke3();
                     return;
                  }

                  CLIENT.player.getInventory().setSelectedSlot(this.intValue5);
                  ((ClientPlayerInteractionManagerAccessor)CLIENT.interactionManager).invokeSyncSelectedSlot();
                  if (this.flag5) {
                     this.invoke15();
                     this.serverHelperState6 = ServerHelper.ServerHelperState6.USE;
                  } else {
                     this.invoke11();
                     this.serverHelperState6 = ServerHelper.ServerHelperState6.RESTORE;
                  }
                  break;
               case USE:
                  if (this.flag5) {
                     if (this.flag9) {
                        this.invoke16();
                     } else {
                        this.invoke15();
                     }
                  } else {
                     this.serverHelperState6 = ServerHelper.ServerHelperState6.RESTORE;
                  }
                  break;
               case PRE_RESTORE_STOP:
                  this.invoke6();
                  if (!this.check5()) {
                     return;
                  }

                  this.invoke8();
                  this.serverHelperState6 = ServerHelper.ServerHelperState6.COOLDOWN;
                  break;
               case RESTORE:
                  if (this.flag5) {
                     this.invoke18();
                  }

                  if (this.intValue5 >= 9) {
                     this.invoke4();
                     return;
                  }

                  CLIENT.player.getInventory().setSelectedSlot(this.intValue6);
                  ((ClientPlayerInteractionManagerAccessor)CLIENT.interactionManager).invokeSyncSelectedSlot();
                  this.serverHelperState6 = ServerHelper.ServerHelperState6.COOLDOWN;
                  break;
               case COOLDOWN:
                  if (!this.flag4 && this.intValue5 >= 9) {
                     CLIENT.player.networkHandler.sendPacket(new CloseHandledScreenC2SPacket(CLIENT.player.playerScreenHandler.syncId));
                     CLIENT.player.closeHandledScreen();
                  }

                  if (this.dopolnitelnyeNastroyki.isEnabled("Стопы")) {
                     InputUtils.getINSTANCE().invoke2("ServerHelper_Lock");
                  }

                  this.invoke13();
                  if (this.flag7) {
                     this.flag3 = true;
                     this.intValue9 = 3;
                     this.dualTimer3.invoke();
                     this.dualTimer4.invoke();
                  }

                  this.serverHelperState6 = ServerHelper.ServerHelperState6.IDLE;
                  flag = false;
                  this.flag4 = false;
                  this.flag5 = false;
                  this.flag6 = false;
                  this.flag7 = false;
                  this.flag9 = false;
                  this.intValue10 = 0;
            }
         }
      }
   }

   private void invoke() {
      if (this.dopolnitelnyeNastroyki.isEnabled("Стопы")) {
         InputUtils.getINSTANCE().invoke("ServerHelper_Lock");
      }
   }

   private void invoke2() {
      if (this.dopolnitelnyeNastroyki.isEnabled("Стопы")) {
         InputUtils.getINSTANCE().invoke2("ServerHelper_Lock");
      }
   }

   private boolean check4() {
      return this.flag5 || this.flag7;
   }

   private void invoke3() {
      this.invoke18();
      this.serverHelperState6 = ServerHelper.ServerHelperState6.PRE_SWAP_STOP;
      this.invoke5();
      this.invoke6();
   }

   private void invoke4() {
      this.invoke18();
      this.serverHelperState6 = ServerHelper.ServerHelperState6.PRE_RESTORE_STOP;
      this.invoke5();
      this.invoke6();
   }

   private void invoke5() {
      this.dualTimer2.invoke();
   }

   private boolean check5() {
      return this.dualTimer2.check11(0L);
   }

   private void invoke6() {
      this.invoke();
      Sprint.intValue = Math.max(Sprint.intValue, 2);
      CLIENT.options.sprintKey.setPressed(false);
      CLIENT.options.useKey.setPressed(false);
      CLIENT.player.setSprinting(false);
   }

   private void invoke7() {
      if (this.intValue5 < 9) {
         CLIENT.player.getInventory().setSelectedSlot(this.intValue5);
         ((ClientPlayerInteractionManagerAccessor)CLIENT.interactionManager).invokeSyncSelectedSlot();
      } else {
         this.invoke9();
      }
   }

   private void invoke8() {
      if (this.intValue5 < 9) {
         CLIENT.player.getInventory().setSelectedSlot(this.intValue6);
         ((ClientPlayerInteractionManagerAccessor)CLIENT.interactionManager).invokeSyncSelectedSlot();
      } else {
         this.invoke9();
      }
   }

   private void invoke9() {
      CLIENT.interactionManager
         .clickSlot(CLIENT.player.playerScreenHandler.syncId, this.intValue5, this.intValue6, SlotActionType.SWAP, CLIENT.player);
   }

   private void invoke10() {
      if (this.flag3) {
         if (CLIENT.player == null || CLIENT.interactionManager == null) {
            this.flag3 = false;
         } else if (this.intValue9 <= 0 || this.dualTimer3.check11(800L)) {
            this.flag3 = false;
         } else if (this.serverHelperState6 == ServerHelper.ServerHelperState6.IDLE) {
            Item item = CLIENT.player.getInventory().getStack(this.intValue7).getItem();
            Item item2 = CLIENT.player.getInventory().getStack(this.intValue8).getItem();
            if (item != this.item && item2 == this.item) {
               if (this.dualTimer4.check11(150L)) {
                  this.dualTimer4.invoke();
                  CLIENT.interactionManager
                     .clickSlot(CLIENT.player.playerScreenHandler.syncId, this.intValue8, this.intValue7, SlotActionType.SWAP, CLIENT.player);
                  this.intValue9--;
               }
            }
         }
      }
   }

   private void invoke11() {
      if (this.flag6) {
         this.invoke12();
      }

      CLIENT.interactionManager.interactItem(CLIENT.player, Hand.MAIN_HAND);
   }

   private void invoke12() {
      this.rotationResetController.invoke3(new Rotation(CLIENT.player.getYaw(), 90.0F), 180.0F, 180.0F, 180.0F, 180.0F, 1, this.compute());
      CLIENT.options.jumpKey.setPressed(true);
      this.flag8 = true;
      if (CLIENT.player.isOnGround()) {
         CLIENT.player.jump();
      }
   }

   private int compute() {
      return AttackAura.livingEntity != null ? 30 : 15;
   }

   private void invoke13() {
      if (this.flag8) {
         this.invoke14();
         this.flag8 = false;
      }
   }

   private void invoke14() {
      if (CLIENT.options != null && CLIENT.getWindow() != null) {
         boolean flag = InputUtil.isKeyPressed(CLIENT.getWindow().getHandle(), CLIENT.options.jumpKey.getDefaultKey().getCode());
         CLIENT.options.jumpKey.setPressed(flag);
      }
   }

   private void invoke15() {
      this.vec3d = CLIENT.player.getPos();
      this.flag9 = true;
      this.flag10 = false;
      this.flag11 = false;
      this.dualTimer.invoke();
      CLIENT.options.useKey.setPressed(true);
      CLIENT.interactionManager.interactItem(CLIENT.player, Hand.MAIN_HAND);
      this.serverHelperState6 = ServerHelper.ServerHelperState6.USE;
   }

   private void invoke16() {
      if (CLIENT.player == null || CLIENT.interactionManager == null) {
         this.invoke17();
      } else if (this.check6()) {
         if (!this.flag10) {
            this.invoke2();
         }

         this.flag10 = true;
         this.intValue10 = 0;
         CLIENT.options.useKey.setPressed(true);
      } else {
         if (this.flag10 && !this.flag11) {
            this.flag11 = true;
            CLIENT.options.useKey.setPressed(false);
         }

         if (this.flag11 && this.check8()) {
            this.invoke17();
         } else if (!this.flag10) {
            this.invoke6();
            if (this.check7() && this.intValue10++ < 1) {
               this.invoke15();
            } else {
               this.invoke17();
            }
         } else {
            if (this.dualTimer.check11(5000L)) {
               this.invoke17();
            }
         }
      }
   }

   private void invoke17() {
      this.invoke18();
      this.invoke4();
   }

   private boolean check6() {
      return CLIENT.player != null
         && CLIENT.player.isUsingItem()
         && CLIENT.player.getActiveHand() == Hand.MAIN_HAND
         && CLIENT.player.getActiveItem().isOf(Items.CHORUS_FRUIT);
   }

   private boolean check7() {
      return CLIENT.player != null && CLIENT.player.getMainHandStack().isOf(Items.CHORUS_FRUIT);
   }

   private void invoke18() {
      if (CLIENT.options != null) {
         CLIENT.options.useKey.setPressed(false);
      }

      if (CLIENT.player != null && CLIENT.interactionManager != null && CLIENT.player.isUsingItem()) {
         CLIENT.interactionManager.stopUsingItem(CLIENT.player);
         CLIENT.player.stopUsingItem();
      }
   }

   private boolean check8() {
      return CLIENT.player != null && CLIENT.player.getPos().squaredDistanceTo(this.vec3d) >= 0.25;
   }

   private void invoke19(boolean bl) {
      if (CLIENT.getWindow() != null) {
         KeyBinding[] keyBindings = new KeyBinding[]{
            CLIENT.options.forwardKey, CLIENT.options.backKey, CLIENT.options.leftKey, CLIENT.options.rightKey, CLIENT.options.jumpKey
         };
         long longValue = CLIENT.getWindow().getHandle();

         for (KeyBinding keyBinding : keyBindings) {
            boolean flag2 = bl && InputUtil.isKeyPressed(longValue, keyBinding.getDefaultKey().getCode());
            keyBinding.setPressed(flag2);
         }
      }
   }

   @EventHandler
   public void onPacket(PacketEvent packetEvent) {
      if (packetEvent.getPacket() instanceof GameMessageS2CPacket gameMessageS2CPacket) {
         String text2 = gameMessageS2CPacket.content().getString();
         if (this.dopolnitelnyeNastroyki.isEnabled("Авто GPS на ивенты") && text2.contains("Появился на координатах")) {
            Matcher matcher = PATTERN.matcher(text2);
            if (matcher.find()) {
               try {
                  float floatValue = Float.parseFloat(matcher.group(1));
                  float floatValue2 = Float.parseFloat(matcher.group(3));
                  GpsCommand.invoke2(floatValue, floatValue2);
               } catch (NumberFormatException numberFormatException) {
               }
            }
         }
      } else if (packetEvent.getPacket() instanceof PlaySoundS2CPacket playSoundS2CPacket2) {
         this.invoke25(playSoundS2CPacket2);
      }
   }

   private void invoke20() {
      this.invoke13();
      if (this.serverHelperState6 != ServerHelper.ServerHelperState6.IDLE) {
         InputUtils.getINSTANCE().invoke2("ServerHelper_Lock");
      }

      if (CLIENT.options != null) {
         CLIENT.options.useKey.setPressed(false);
      }

      if (this.intValue > 0 && this.dopolnitelnyeNastroyki.isEnabled("Стопы")) {
         InputUtils.getINSTANCE().invoke2("ServerHelper_Lock");
      }

      this.intValue = 0;
      this.intValue2 = 0;
      this.intValue5 = -1;
      this.intValue6 = -1;
      this.flag4 = false;
      this.flag5 = false;
      this.flag6 = false;
      this.flag7 = false;
      this.flag9 = false;
      this.intValue10 = 0;
      this.flag10 = false;
      this.flag11 = false;
      this.dualTimer2.invoke();
      this.flag3 = false;
      this.serverHelperState6 = ServerHelper.ServerHelperState6.IDLE;
      flag = false;
   }

   @Override
   public void onDisable() {
      this.timestamp = 0L;
      this.timestamp2 = 0L;
      this.queue.clear();
      this.items.clear();
      this.arrayDeque.clear();
      this.intValue11 = -1;
      this.invoke20();
      super.onDisable();
   }

   @EventHandler
   public void onRender3D(Render3DEvent render3DEvent) {
      if (!ServerModeDetector.check()) {
         boolean flag3 = this.dopolnitelnyeNastroyki.isEnabled("Рендерить границы");
         if (flag3 || this.proektsiyaMegaBuldozera.isEnabled()) {
            RenderLayer renderLayer3 = this.dopolnitelnyeNastroyki.isEnabled("Рендерить границы сквозь стены") ? RENDER_LAYER_3 : RENDER_LAYER;
            RenderLayer renderLayer4 = this.dopolnitelnyeNastroyki.isEnabled("Рендерить границы сквозь стены") ? RENDER_LAYER_4 : RENDER_LAYER_2;
            if (this.check11()) {
               renderLayer3 = RENDER_LAYER_3;
               renderLayer4 = RENDER_LAYER_4;
            }

            if (this.proektsiyaMegaBuldozera.isEnabled()) {
               this.invoke21(render3DEvent, RENDER_LAYER_3, RENDER_LAYER_4);
            }

            if (flag3) {
               if (this.klavishaYavnoyPyli.getKeyCode() != -1 && KeybindSetting.isPressed(this.klavishaYavnoyPyli.getKeyCode())) {
                  double doubleValue = MathHelper.lerp(render3DEvent.getFloatValue(), CLIENT.player.lastRenderX, CLIENT.player.getX());
                  double doubleValue2 = MathHelper.lerp(render3DEvent.getFloatValue(), CLIENT.player.lastRenderY, CLIENT.player.getY());
                  double doubleValue3 = MathHelper.lerp(render3DEvent.getFloatValue(), CLIENT.player.lastRenderZ, CLIENT.player.getZ());
                  double doubleValue4 = 3.0;
                  double doubleValue5 = 1.0;
                  boolean flag4 = this.check10(doubleValue, doubleValue2, doubleValue3, doubleValue4, doubleValue5);
                  int intValue6 = flag4
                     ? ColorUtils.compute29(new Color(255, 50, 50).getRGB(), 10)
                     : ColorUtils.compute29(new Color(50, 150, 255).getRGB(), 255);
                  int intValue7 = flag4 ? new Color(255, 0, 0).getRGB() : new Color(0, 100, 255).getRGB();
                  Immediate immediate = WorldRenderBuffer.getIMMEDIATE();

                  try {
                     Vec3d vec3d2 = CLIENT.gameRenderer.getCamera().getPos();
                     Matrix4f matrix4f = render3DEvent.getMatrixStack().peek().getPositionMatrix();
                     VertexConsumer vertexConsumer = immediate.getBuffer(renderLayer3);
                     WorldBoxRenderer.invoke6(
                        vertexConsumer, matrix4f, (float)(doubleValue - vec3d2.x), (float)(doubleValue2 - vec3d2.y), (float)(doubleValue3 - vec3d2.z), (float)doubleValue4, (float)doubleValue5, intValue6, 40
                     );
                     VertexConsumer vertexConsumer2 = immediate.getBuffer(renderLayer4);
                     WorldBoxRenderer.invoke4(
                        vertexConsumer2,
                        matrix4f,
                        (float)(doubleValue - vec3d2.x),
                        (float)(doubleValue2 - 0.005F - vec3d2.y),
                        (float)(doubleValue3 - vec3d2.z),
                        (float)doubleValue4 + 0.005F,
                        (float)doubleValue5 + 0.01F,
                        intValue7,
                        40
                     );
                  } finally {
                     WorldRenderBuffer.invoke();
                  }
               }

               this.invoke22(this.klavishaTrapki, 2.0, 3.0, render3DEvent, renderLayer3, renderLayer4);
               this.invoke22(this.klavishaBozheyAury, 4.0, 2.0, render3DEvent, renderLayer3, renderLayer4);
               this.invoke22(this.klavishaPlasta, 2.0, 2.0, render3DEvent, renderLayer3, renderLayer4);
               this.invoke22(this.klavishaSnezhkaZamorozki, 2.0, 2.0, render3DEvent, renderLayer3, renderLayer4);
               this.invoke22(this.klavishaDezorientatsii, 2.0, 2.0, render3DEvent, renderLayer3, renderLayer4);
            }
         }
      }
   }

   private void invoke21(Render3DEvent render3DEvent2, RenderLayer renderLayer, RenderLayer renderLayer2) {
      if (CLIENT.world == null || !(CLIENT.crosshairTarget instanceof BlockHitResult blockHitResult && blockHitResult.getType() == Type.BLOCK)) {
         this.serverHelperData = null;
      } else if (!this.check9(CLIENT.player.getMainHandStack())) {
         this.serverHelperData = null;
      } else {
         BlockPos blockPos2 = blockHitResult.getBlockPos();
         if (CLIENT.world.getBlockState(blockPos2).isAir()) {
            this.serverHelperData = null;
         } else {
            ServerHelper.ServerHelperData serverHelperData = this.resolve2(blockPos2, blockHitResult.getSide());
            ServerHelper.ServerHelperData serverHelperData2 = this.serverHelperData == null ? serverHelperData : this.serverHelperData.lerp(serverHelperData, 0.28);
            this.serverHelperData = serverHelperData2;
            int intValue8 = new Color(40, 220, 170).getRGB();
            int intValue9 = ColorUtils.compute29(intValue8, 80);
            int intValue10 = ColorUtils.compute29(intValue8, 8);
            int intValue11 = new Color(40, 255, 180, 235).getRGB();
            Immediate immediate2 = WorldRenderBuffer.getIMMEDIATE();
            boolean flag5 = false ;

            try {
               flag5 = true;
               Vec3d vec3d3 = CLIENT.gameRenderer.getCamera().getPos();
               Matrix4f matrix4f2 = render3DEvent2.getMatrixStack().peek().getPositionMatrix();
               float floatValue3 = (float)(serverHelperData2.minX - vec3d3.x);
               float floatValue4 = (float)(serverHelperData2.minY - vec3d3.y);
               float floatValue5 = (float)(serverHelperData2.minZ - vec3d3.z);
               float floatValue6 = (float)(serverHelperData2.maxX - vec3d3.x);
               float floatValue7 = (float)(serverHelperData2.maxY - vec3d3.y);
               float floatValue8 = (float)(serverHelperData2.maxZ - vec3d3.z);
               VertexConsumer vertexConsumer3 = immediate2.getBuffer(renderLayer);
               WorldBoxRenderer.invoke7(vertexConsumer3, matrix4f2, floatValue3, floatValue4, floatValue5, floatValue6, floatValue7, floatValue8, intValue9, intValue10);
               VertexConsumer vertexConsumer4 = immediate2.getBuffer(renderLayer2);
               WorldBoxRenderer.invoke2(vertexConsumer4, matrix4f2, floatValue3 - 0.008F, floatValue4 - 0.008F, floatValue5 - 0.008F, floatValue6 + 0.008F, floatValue7 + 0.008F, floatValue8 + 0.008F, intValue11);
               flag5 = false;
            } finally {
               if (flag5) {
                  WorldRenderBuffer.invoke();
               }
            }

            WorldRenderBuffer.invoke();
         }
      }
   }

   private ServerHelper.ServerHelperData resolve2(BlockPos blockPos, Direction direction) {
      return switch (direction) {
         case EAST -> new ServerHelper.ServerHelperData(
            blockPos.getX() - 4, blockPos.getY() - 4, blockPos.getZ() - 4, blockPos.getX() + 1, blockPos.getY() + 5, blockPos.getZ() + 5
         );
         case WEST -> new ServerHelper.ServerHelperData(
            blockPos.getX(), blockPos.getY() - 4, blockPos.getZ() - 4, blockPos.getX() + 5, blockPos.getY() + 5, blockPos.getZ() + 5
         );
         case UP -> new ServerHelper.ServerHelperData(
            blockPos.getX() - 4, blockPos.getY() - 4, blockPos.getZ() - 4, blockPos.getX() + 5, blockPos.getY() + 1, blockPos.getZ() + 5
         );
         case DOWN -> new ServerHelper.ServerHelperData(
            blockPos.getX() - 4, blockPos.getY(), blockPos.getZ() - 4, blockPos.getX() + 5, blockPos.getY() + 5, blockPos.getZ() + 5
         );
         case SOUTH -> new ServerHelper.ServerHelperData(
            blockPos.getX() - 4, blockPos.getY() - 4, blockPos.getZ() - 4, blockPos.getX() + 5, blockPos.getY() + 5, blockPos.getZ() + 1
         );
         case NORTH -> new ServerHelper.ServerHelperData(
            blockPos.getX() - 4, blockPos.getY() - 4, blockPos.getZ(), blockPos.getX() + 5, blockPos.getY() + 5, blockPos.getZ() + 5
         );
         default -> throw new MatchException(null, null);
      };
   }

   private boolean check9(ItemStack itemStack) {
      if (itemStack == null || itemStack.isEmpty() || !itemStack.isOf(Items.NETHERITE_PICKAXE)) {
         return false;
      } else if (SpecialItemUtils.check58(itemStack)) {
         return true;
      } else {
         StringBuilder stringBuilder = new StringBuilder(itemStack.getName().getString());
         LoreComponent loreComponent = (LoreComponent)itemStack.get(DataComponentTypes.LORE);
         if (loreComponent != null) {
            for (Text text3 : loreComponent.lines()) {
               stringBuilder.append(' ').append(text3.getString());
            }
         }

         String text4 = stringBuilder.toString().replaceAll("§.", "").replace('ё', 'е').replace('Ё', 'Е').toLowerCase(Locale.ROOT);
         return text4.contains("мега-бульдозер") || text4.contains("мега бульдозер");
      }
   }

   private boolean check10(double d, double e, double f, double g, double h) {
      if (CLIENT.world == null) {
         return false;
      } else {
         Box box = new Box(d - g, e, f - g, d + g, e + h, f + g);

         for (PlayerEntity playerEntity : CLIENT.world.getPlayers()) {
            if (playerEntity != CLIENT.player && playerEntity.getBoundingBox().intersects(box)) {
               return true;
            }
         }

         return false;
      }
   }

   private void invoke22(KeybindSetting keybindSetting2, double d, double e, Render3DEvent render3DEvent3, RenderLayer renderLayer, RenderLayer renderLayer2) {
      if (keybindSetting2.getKeyCode() != -1 && KeybindSetting.isPressed(keybindSetting2.getKeyCode())) {
         this.invoke23(render3DEvent3, d, e, renderLayer, renderLayer2);
      }
   }

   private boolean check11() {
      if (CLIENT.world != null && CLIENT.gameRenderer != null) {
         Vec3d vec3d4 = CLIENT.gameRenderer.getCamera().getPos();
         BlockPos blockPos3 = BlockPos.ofFloored(vec3d4);
         BlockState blockState2 = CLIENT.world.getBlockState(blockPos3);
         return !blockState2.getCollisionShape(CLIENT.world, blockPos3).isEmpty();
      } else {
         return false;
      }
   }

   private void invoke23(Render3DEvent render3DEvent4, double d, double e, RenderLayer renderLayer, RenderLayer renderLayer2) {
      double doubleValue6 = MathHelper.lerp(render3DEvent4.getFloatValue(), CLIENT.player.lastRenderX, CLIENT.player.getX());
      double doubleValue7 = MathHelper.lerp(render3DEvent4.getFloatValue(), CLIENT.player.lastRenderY, CLIENT.player.getY());
      double doubleValue8 = MathHelper.lerp(render3DEvent4.getFloatValue(), CLIENT.player.lastRenderZ, CLIENT.player.getZ());
      boolean flag6 = this.check10(doubleValue6, doubleValue7, doubleValue8, d, e);
      int intValue12 = flag6 ? new Color(255, 30, 30).getRGB() : new Color(0, 130, 255).getRGB();
      int intValue13 = ColorUtils.compute29(intValue12, 60);
      int intValue14 = ColorUtils.compute29(intValue12, 0);
      int intValue15 = flag6 ? new Color(255, 0, 0, 255).getRGB() : new Color(0, 150, 255, 255).getRGB();
      Immediate immediate3 = WorldRenderBuffer.getIMMEDIATE();

      try {
         Vec3d vec3d5 = CLIENT.gameRenderer.getCamera().getPos();
         Matrix4f matrix4f3 = render3DEvent4.getMatrixStack().peek().getPositionMatrix();
         float floatValue9 = (float)(doubleValue6 - d - vec3d5.x);
         float floatValue10 = (float)(doubleValue7 - vec3d5.y);
         float floatValue11 = (float)(doubleValue8 - d - vec3d5.z);
         float floatValue12 = (float)(doubleValue6 + d - vec3d5.x);
         float floatValue13 = (float)(doubleValue7 + e - vec3d5.y);
         float floatValue14 = (float)(doubleValue8 + d - vec3d5.z);
         VertexConsumer vertexConsumer5 = immediate3.getBuffer(renderLayer);
         WorldBoxRenderer.invoke7(vertexConsumer5, matrix4f3, floatValue9, floatValue10, floatValue11, floatValue12, floatValue13, floatValue14, intValue13, intValue14);
         VertexConsumer vertexConsumer6 = immediate3.getBuffer(renderLayer2);
         WorldBoxRenderer.invoke2(vertexConsumer6, matrix4f3, floatValue9 - 0.005F, floatValue10 - 0.005F, floatValue11 - 0.005F, floatValue12 + 0.005F, floatValue13 + 0.005F, floatValue14 + 0.005F, intValue15);
      } finally {
         WorldRenderBuffer.invoke();
      }
   }

   public String resolve3(int i, String string) {
      if (i == -1 || i == 0) {
         return "-";
      } else if (i <= -100 && i >= -110) {
         int intValue16 = -(i + 100);

         return switch (intValue16) {
            case 0 -> "LMB";
            case 1 -> "RMB";
            case 2 -> "MMB";
            default -> "M" + (intValue16 + 1);
         };
      } else if (string != null && !string.isEmpty() && !string.equals("Неизвестно")) {
         String text5 = string.toUpperCase();
         text5 = text5.replace("KEY.KEYBOARD.", "")
            .replace("KEY.MOUSE.", "M")
            .replace("MOUSE ", "M")
            .replace("MOUSE", "M")
            .replace("BUTTON ", "M")
            .replace("BUTTON", "M")
            .replace("LEFT.SHIFT", "LSHIFT")
            .replace("LEFT SHIFT", "LSHIFT")
            .replace("RIGHT.SHIFT", "RSHIFT")
            .replace("RIGHT SHIFT", "RSHIFT")
            .replace("LEFT.ALT", "LALT")
            .replace("LEFT ALT", "LALT")
            .replace("RIGHT.ALT", "RALT")
            .replace("RIGHT ALT", "RALT")
            .replace("LEFT.CONTROL", "LCTRL")
            .replace("LEFT CONTROL", "LCTRL")
            .replace("RIGHT.CONTROL", "RCTRL")
            .replace("RIGHT CONTROL", "RCTRL")
            .replace("CONTROL", "CTRL")
            .replace("NUMPAD.", "N")
            .replace("NUMPAD ", "N")
            .replace("NUMPAD", "N")
            .replace("SPACE", "SPC")
            .replace("ПРОБЕЛ", "SPC")
            .replace("LEFT ", "L")
            .replace("RIGHT ", "R")
            .replace("ЛЕВАЯ ", "L")
            .replace("ПРАВАЯ ", "R")
            .replace("КНОПКА МЫШИ", "MB");
         if (text5.equals("M1") || text5.equals("LMB") || text5.equals("LEFT")) {
            return "LMB";
         } else if (text5.equals("M2") || text5.equals("RMB") || text5.equals("RIGHT")) {
            return "RMB";
         } else {
            return !text5.equals("M3") && !text5.equals("MMB") && !text5.equals("MIDDLE") ? text5 : "MMB";
         }
      } else {
         return String.valueOf(i);
      }
   }

   private void invoke24() {
      if (this.taymeryStruktur.isEnabled() && this.rezhimRaboty.is("FunTime")) {
         if (CLIENT.player != null && CLIENT.world != null) {
            long longValue2 = System.currentTimeMillis();
            this.invoke26(longValue2);
            this.invoke30();
            int intValue17 = this.compute4();
            if (this.intValue11 > 0 && intValue17 < this.intValue11) {
               this.invoke32(CLIENT.player.getPos(), ServerHelper.ServerHelperState5.TRAPKA, 15000L);
            }

            this.intValue11 = intValue17;

            for (int intValue18 = this.items.size() - 1; intValue18 >= 0; intValue18--) {
               ServerHelper.ServerHelperState4 serverHelperState4 = this.items.get(intValue18);
               long longValue3 = longValue2 - serverHelperState4.timestamp;
               if (serverHelperState4.flag && longValue3 >= 500L) {
                  if (this.check16(serverHelperState4.vec3d, Blocks.NETHERITE_BLOCK, 5, 9, 5)) {
                     serverHelperState4.serverHelperState5 = ServerHelper.ServerHelperState5.DRAGON_PLAST;
                     serverHelperState4.timestamp2 = 30000L;
                     serverHelperState4.flag = false;
                  } else {
                     serverHelperState4.serverHelperState5 = ServerHelper.ServerHelperState5.PLAST;
                     serverHelperState4.flag = false;
                     serverHelperState4.flag3 = true;
                     this.invoke36(serverHelperState4);
                  }
               } else if (serverHelperState4.flag4) {
                  if (longValue3 <= 450L) {
                     this.invoke29(serverHelperState4);
                  } else {
                     serverHelperState4.flag4 = false;
                  }
               } else if (serverHelperState4.flag3) {
                  if (longValue3 <= 450L) {
                     this.invoke36(serverHelperState4);
                  } else {
                     serverHelperState4.flag3 = false;
                  }
               } else if (serverHelperState4.flag2) {
                  if (longValue3 <= 350L) {
                     this.invoke37(serverHelperState4);
                  } else {
                     serverHelperState4.flag2 = false;
                  }
               }

               if (longValue3 > serverHelperState4.timestamp2) {
                  this.items.remove(intValue18);
               }
            }
         }
      } else {
         if (!this.items.isEmpty()) {
            this.items.clear();
         }

         if (!this.arrayDeque.isEmpty()) {
            this.arrayDeque.clear();
         }

         this.intValue11 = -1;
      }
   }

   private void invoke25(PlaySoundS2CPacket playSoundS2CPacket) {
      if (this.taymeryStruktur.isEnabled() && this.rezhimRaboty.is("FunTime") && CLIENT.world != null) {
         String text6 = ((SoundEvent)playSoundS2CPacket.getSound().value()).id().getPath();
         float floatValue15 = playSoundS2CPacket.getPitch();
         float floatValue16 = playSoundS2CPacket.getVolume();
         double doubleValue9 = playSoundS2CPacket.getX();
         double doubleValue10 = playSoundS2CPacket.getY();
         double doubleValue11 = playSoundS2CPacket.getZ();
         if (this.logZvukovDebag.isEnabled()) {
            ChatUtil.sendClientMessage(String.format(Locale.US, "§e%s§7 pitch=§f%.2f§7 vol=§f%.2f§7 @ §f%.0f %.0f %.0f", text6, floatValue15, floatValue16, doubleValue9, doubleValue10, doubleValue11));
         }

         this.arrayDeque.add(new ServerHelper.ServerHelperState2(text6, floatValue15, floatValue16, doubleValue9, doubleValue10, doubleValue11, System.currentTimeMillis()));

         while (this.arrayDeque.size() > 128) {
            this.arrayDeque.pollFirst();
         }
      }
   }

   private void invoke26(long l) {
      if (!this.arrayDeque.isEmpty()) {
         for (ServerHelper.ServerHelperState serverHelperState : SERVER_HELPER_STATES) {
            this.invoke27(serverHelperState);
         }

         for (ServerHelper.ServerHelperState2 serverHelperState2 : this.arrayDeque) {
            if (!serverHelperState2.flag && l - serverHelperState2.timestamp >= 180L) {
               this.invoke31(serverHelperState2);
               serverHelperState2.flag = true;
            }
         }

         this.arrayDeque.removeIf(serverHelperState22 -> l - serverHelperState22.timestamp > 1500L);
      }
   }

   private void invoke27(ServerHelper.ServerHelperState serverHelperState3) {
      for (ServerHelper.ServerHelperState2 serverHelperState23 : this.arrayDeque) {
         if (!serverHelperState23.flag && serverHelperState3.check(serverHelperState23)) {
            ServerHelper.ServerHelperState2[] w102s = new ServerHelper.ServerHelperState2[serverHelperState3.serverHelperState3s.length];
            boolean flag7 = true;

            for (int intValue19 = 0; intValue19 < serverHelperState3.serverHelperState3s.length; intValue19++) {
               ServerHelper.ServerHelperState2 serverHelperState24 = null;

               for (ServerHelper.ServerHelperState2 serverHelperState25 : this.arrayDeque) {
                  if (!serverHelperState25.flag
                     && !check12(w102s, serverHelperState25)
                     && serverHelperState3.serverHelperState3s[intValue19].check(serverHelperState25)
                     && Math.abs(serverHelperState25.timestamp - serverHelperState23.timestamp) <= 250L) {
                     double doubleValue12 = serverHelperState25.doubleValue - serverHelperState23.doubleValue;
                     double doubleValue13 = serverHelperState25.doubleValue2 - serverHelperState23.doubleValue2;
                     double doubleValue14 = serverHelperState25.doubleValue3 - serverHelperState23.doubleValue3;
                     if (!(doubleValue12 * doubleValue12 + doubleValue13 * doubleValue13 + doubleValue14 * doubleValue14 > 16.0)) {
                        serverHelperState24 = serverHelperState25;
                        break;
                     }
                  }
               }

               if (serverHelperState24 == null) {
                  flag7 = false;
                  break;
               }

               w102s[intValue19] = serverHelperState24;
            }

            if (flag7) {
               for (ServerHelper.ServerHelperState2 serverHelperState26 : w102s) {
                  serverHelperState26.flag = true;
               }

               this.invoke28(new Vec3d(serverHelperState23.doubleValue, serverHelperState23.doubleValue2, serverHelperState23.doubleValue3), serverHelperState3);
            }
         }
      }
   }

   private static boolean check12(ServerHelper.ServerHelperState2[] w102s2, ServerHelper.ServerHelperState2 serverHelperState27) {
      for (ServerHelper.ServerHelperState2 serverHelperState28 : w102s2) {
         if (serverHelperState28 == serverHelperState27) {
            return true;
         }
      }

      return false;
   }

   private void invoke28(Vec3d vec3d, ServerHelper.ServerHelperState serverHelperState5) {
      if (!this.check15(vec3d)) {
         ServerHelper.ServerHelperState4 serverHelperState42 = new ServerHelper.ServerHelperState4(vec3d, System.currentTimeMillis(), ServerHelper.ServerHelperState5.TRAPKA, serverHelperState5.timestamp);
         serverHelperState42.serverHelperState = serverHelperState5;
         serverHelperState42.flag4 = true;
         this.items.add(serverHelperState42);
         this.invoke29(serverHelperState42);
      }
   }

   private void invoke29(ServerHelper.ServerHelperState4 serverHelperState43) {
      int intValue20 = this.compute2(serverHelperState43);
      if (intValue20 != 0) {
         serverHelperState43.flag4 = false;
         if (intValue20 == 1) {
            serverHelperState43.serverHelperState5 = ServerHelper.ServerHelperState5.TRAPKA;
            serverHelperState43.timestamp2 = serverHelperState43.serverHelperState.timestamp;
            serverHelperState43.vec3d2 = null;
         } else {
            serverHelperState43.serverHelperState5 = ServerHelper.ServerHelperState5.PLAST;
            serverHelperState43.timestamp2 = serverHelperState43.serverHelperState.timestamp2 > 0L
               ? serverHelperState43.serverHelperState.timestamp2
               : (intValue20 == 3 ? 60000L : 20000L);
         }
      }
   }

   private int compute2(ServerHelper.ServerHelperState4 serverHelperState44) {
      if (CLIENT.world == null) {
         return 0;
      } else {
         int intValue21 = MathHelper.floor(serverHelperState44.vec3d.x);
         int intValue22 = MathHelper.floor(serverHelperState44.vec3d.y);
         int intValue23 = MathHelper.floor(serverHelperState44.vec3d.z);
         Mutable mutable = new Mutable();
         long longValue4 = Long.MIN_VALUE;
         double doubleValue15 = Double.MAX_VALUE;

         for (int intValue24 = -2; intValue24 <= 2; intValue24++) {
            for (int intValue25 = -2; intValue25 <= 2; intValue25++) {
               for (int intValue26 = -2; intValue26 <= 2; intValue26++) {
                  mutable.set(intValue21 + intValue24, intValue22 + intValue25, intValue23 + intValue26);
                  if (this.check13(CLIENT.world.getBlockState(mutable), mutable)) {
                     double doubleValue16 = intValue24 * intValue24 + intValue25 * intValue25 + intValue26 * intValue26;
                     if (doubleValue16 < doubleValue15) {
                        doubleValue15 = doubleValue16;
                        longValue4 = mutable.asLong();
                     }
                  }
               }
            }
         }

         if (longValue4 == Long.MIN_VALUE) {
            return 0;
         } else {
            short shortValue = 9000;
            byte byteValue = 18;
            ArrayDeque arrayDeque = new ArrayDeque();
            HashSet hashSet = new HashSet();
            arrayDeque.add(longValue4);
            hashSet.add(longValue4);
            int intValue27 = Integer.MAX_VALUE;
            int intValue28 = Integer.MAX_VALUE;
            int intValue29 = Integer.MAX_VALUE;
            int intValue30 = Integer.MIN_VALUE;
            int intValue31 = Integer.MIN_VALUE;
            int intValue32 = Integer.MIN_VALUE;
            int intValue33 = 0;

            while (!arrayDeque.isEmpty() && hashSet.size() <= shortValue) {
               long longValue5 = (Long)arrayDeque.poll();
               int intValue34 = BlockPos.unpackLongX(longValue5);
               int intValue35 = BlockPos.unpackLongY(longValue5);
               int intValue36 = BlockPos.unpackLongZ(longValue5);
               intValue33++;
               if (intValue34 < intValue27) {
                  intValue27 = intValue34;
               }

               if (intValue34 > intValue30) {
                  intValue30 = intValue34;
               }

               if (intValue35 < intValue28) {
                  intValue28 = intValue35;
               }

               if (intValue35 > intValue31) {
                  intValue31 = intValue35;
               }

               if (intValue36 < intValue29) {
                  intValue29 = intValue36;
               }

               if (intValue36 > intValue32) {
                  intValue32 = intValue36;
               }

               for (int intValue37 = 0; intValue37 < 6; intValue37++) {
                  int intValue38 = intValue34 + INTS[intValue37][0];
                  int intValue39 = intValue35 + INTS[intValue37][1];
                  int intValue40 = intValue36 + INTS[intValue37][2];
                  if (Math.abs(intValue38 - intValue21) <= byteValue && Math.abs(intValue39 - intValue22) <= byteValue && Math.abs(intValue40 - intValue23) <= byteValue) {
                     mutable.set(intValue38, intValue39, intValue40);
                     long longValue6 = mutable.asLong();
                     if (!hashSet.contains(longValue6) && this.check13(CLIENT.world.getBlockState(mutable), mutable)) {
                        hashSet.add(longValue6);
                        arrayDeque.add(longValue6);
                     }
                  }
               }
            }

            if (intValue33 < 12) {
               return 0;
            } else {
               int intValue41 = intValue30 - intValue27;
               int intValue42 = intValue31 - intValue28;
               int intValue43 = intValue32 - intValue29;
               int intValue44 = Math.min(intValue41, Math.min(intValue42, intValue43));
               int intValue45 = Math.max(intValue41, Math.max(intValue42, intValue43));
               if (intValue44 * 3 > intValue45) {
                  return 1;
               } else {
                  boolean flag8 = intValue42 <= intValue41 && intValue42 <= intValue43;
                  serverHelperState44.vec3d2 = new Vec3d((intValue27 + intValue30) / 2.0 + 0.5, (intValue28 + intValue31) / 2.0 + 0.5, (intValue29 + intValue32) / 2.0 + 0.5);
                  return flag8 ? 3 : 2;
               }
            }
         }
      }
   }

   private boolean check13(BlockState blockState, BlockPos blockPos) {
      return !blockState.isAir() && !VALUES.contains(blockState.getBlock())
         ? !blockState.getCollisionShape(CLIENT.world, blockPos).isEmpty()
         : false;
   }

   private void invoke30() {
      if (this.logBlokaDebag.isEnabled()) {
         if (CLIENT.crosshairTarget instanceof BlockHitResult blockHitResult2 && blockHitResult2.getType() == Type.BLOCK) {
            String text7 = Registries.BLOCK.getId(CLIENT.world.getBlockState(blockHitResult2.getBlockPos()).getBlock()).toString();
            if (!text7.equals(this.text)) {
               this.text = text7;
               ChatUtil.sendClientMessage("§bблок:§f " + text7);
            }
         }
      }
   }

   private void invoke31(ServerHelper.ServerHelperState2 serverHelperState29) {
      String text8 = serverHelperState29.text;
      float floatValue17 = serverHelperState29.floatValue;
      float floatValue18 = serverHelperState29.floatValue2;
      Vec3d vec3d6 = new Vec3d(serverHelperState29.doubleValue, serverHelperState29.doubleValue2, serverHelperState29.doubleValue3);
      if (!this.tipIventa.is("Спуки тайм")) {
         if (text8.equals("block.anvil.place") && check17(floatValue17, 1.1F) && check17(floatValue18, 0.7F)) {
            this.invoke35(vec3d6);
         } else if (text8.equals("block.piston.extend") && check17(floatValue17, 0.5F) && check17(floatValue18, 0.7F)) {
            this.invoke32(vec3d6, ServerHelper.ServerHelperState5.TRAPKA, 15000L);
         } else if (text8.equals("entity.ender_dragon.growl") && check17(floatValue17, 1.0F) && check17(floatValue18, 0.2F)) {
            this.invoke33(vec3d6);
         }
      } else if (text8.equals("block.piston.extend") && check17(floatValue17, 0.5F) && check17(floatValue18, 0.5F)) {
         this.invoke32(vec3d6, ServerHelper.ServerHelperState5.TRAPKA, 15000L);
      } else if (text8.equals("block.anvil.place") && check17(floatValue17, 0.5F) && check17(floatValue18, 0.5F)) {
         this.invoke34(vec3d6);
      } else if (text8.equals("entity.ender_dragon.growl") && check17(floatValue17, 0.7F) && check17(floatValue18, 0.5F)) {
         this.invoke33(vec3d6);
      }
   }

   private void invoke32(Vec3d vec3d, ServerHelper.ServerHelperState5 serverHelperState52, long l) {
      if (!this.check15(vec3d)) {
         this.items.add(new ServerHelper.ServerHelperState4(vec3d, System.currentTimeMillis(), serverHelperState52, l));
      }
   }

   private void invoke33(Vec3d vec3d) {
      if (!this.check15(vec3d)) {
         ServerHelper.ServerHelperState4 serverHelperState45 = new ServerHelper.ServerHelperState4(vec3d, System.currentTimeMillis(), ServerHelper.ServerHelperState5.DRAGON_PLAST, 20000L);
         serverHelperState45.flag2 = true;
         this.items.add(serverHelperState45);
         this.invoke37(serverHelperState45);
      }
   }

   private void invoke34(Vec3d vec3d) {
      if (!this.check15(vec3d)) {
         ServerHelper.ServerHelperState4 serverHelperState46 = new ServerHelper.ServerHelperState4(vec3d, System.currentTimeMillis(), ServerHelper.ServerHelperState5.PLAST, 20000L);
         serverHelperState46.flag = true;
         this.items.add(serverHelperState46);
      }
   }

   private void invoke35(Vec3d vec3d) {
      if (!this.check15(vec3d)) {
         ServerHelper.ServerHelperState4 serverHelperState47 = new ServerHelper.ServerHelperState4(vec3d, System.currentTimeMillis(), ServerHelper.ServerHelperState5.PLAST, 20000L);
         serverHelperState47.flag3 = true;
         this.items.add(serverHelperState47);
         this.invoke36(serverHelperState47);
      }
   }

   private void invoke36(ServerHelper.ServerHelperState4 serverHelperState48) {
      int intValue46 = this.compute3(serverHelperState48);
      if (intValue46 > 0) {
         serverHelperState48.timestamp2 = intValue46 == 2 ? 60000L : 20000L;
         serverHelperState48.flag3 = false;
      }
   }

   private int compute3(ServerHelper.ServerHelperState4 serverHelperState49) {
      if (CLIENT.world == null) {
         return 0;
      } else {
         Vec3d vec3d7 = serverHelperState49.vec3d;
         int intValue47 = MathHelper.floor(vec3d7.x);
         int intValue48 = MathHelper.floor(vec3d7.y);
         int intValue49 = MathHelper.floor(vec3d7.z);
         Mutable mutable2 = new Mutable();
         long longValue7 = Long.MIN_VALUE;
         double doubleValue17 = Double.MAX_VALUE;

         for (int intValue50 = -3; intValue50 <= 3; intValue50++) {
            for (int intValue51 = -3; intValue51 <= 3; intValue51++) {
               for (int intValue52 = -3; intValue52 <= 3; intValue52++) {
                  mutable2.set(intValue47 + intValue50, intValue48 + intValue51, intValue49 + intValue52);
                  if (this.check14(CLIENT.world.getBlockState(mutable2))) {
                     double doubleValue18 = intValue50 * intValue50 + intValue51 * intValue51 + intValue52 * intValue52;
                     if (doubleValue18 < doubleValue17) {
                        doubleValue17 = doubleValue18;
                        longValue7 = mutable2.asLong();
                     }
                  }
               }
            }
         }

         if (longValue7 == Long.MIN_VALUE) {
            return 0;
         } else {
            short shortValue2 = 6000;
            byte byteValue2 = 16;
            ArrayDeque arrayDeque2 = new ArrayDeque();
            HashSet hashSet2 = new HashSet();
            arrayDeque2.add(longValue7);
            hashSet2.add(longValue7);
            int intValue53 = Integer.MAX_VALUE;
            int intValue54 = Integer.MAX_VALUE;
            int intValue55 = Integer.MAX_VALUE;
            int intValue56 = Integer.MIN_VALUE;
            int intValue57 = Integer.MIN_VALUE;
            int intValue58 = Integer.MIN_VALUE;
            int intValue59 = 0;

            while (!arrayDeque2.isEmpty() && hashSet2.size() <= shortValue2) {
               long longValue8 = (Long)arrayDeque2.poll();
               int intValue60 = BlockPos.unpackLongX(longValue8);
               int intValue61 = BlockPos.unpackLongY(longValue8);
               int intValue62 = BlockPos.unpackLongZ(longValue8);
               mutable2.set(intValue60, intValue61, intValue62);
               if (CLIENT.world.getBlockState(mutable2).isOf(Blocks.DEAD_TUBE_CORAL_BLOCK)) {
                  intValue59++;
                  if (intValue60 < intValue53) {
                     intValue53 = intValue60;
                  }

                  if (intValue60 > intValue56) {
                     intValue56 = intValue60;
                  }

                  if (intValue61 < intValue54) {
                     intValue54 = intValue61;
                  }

                  if (intValue61 > intValue57) {
                     intValue57 = intValue61;
                  }

                  if (intValue62 < intValue55) {
                     intValue55 = intValue62;
                  }

                  if (intValue62 > intValue58) {
                     intValue58 = intValue62;
                  }
               }

               for (int intValue63 = 0; intValue63 < 6; intValue63++) {
                  int intValue64 = intValue60 + INTS[intValue63][0];
                  int intValue65 = intValue61 + INTS[intValue63][1];
                  int intValue66 = intValue62 + INTS[intValue63][2];
                  if (Math.abs(intValue64 - intValue47) <= byteValue2 && Math.abs(intValue65 - intValue48) <= byteValue2 && Math.abs(intValue66 - intValue49) <= byteValue2) {
                     mutable2.set(intValue64, intValue65, intValue66);
                     long longValue9 = mutable2.asLong();
                     if (!hashSet2.contains(longValue9) && this.check14(CLIENT.world.getBlockState(mutable2))) {
                        hashSet2.add(longValue9);
                        arrayDeque2.add(longValue9);
                     }
                  }
               }
            }

            if (intValue59 < 3) {
               return 0;
            } else {
               serverHelperState49.vec3d2 = new Vec3d((intValue53 + intValue56) / 2.0 + 0.5, (intValue54 + intValue57) / 2.0 + 0.5, (intValue55 + intValue58) / 2.0 + 0.5);
               int intValue67 = intValue56 - intValue53;
               int intValue68 = intValue57 - intValue54;
               int intValue69 = intValue58 - intValue55;
               boolean flag9 = intValue68 < intValue67 && intValue68 < intValue69;
               return flag9 ? 2 : 1;
            }
         }
      }
   }

   private boolean check14(BlockState blockState) {
      return blockState.isOf(Blocks.COBBLESTONE) || blockState.isOf(Blocks.ANDESITE) || blockState.isOf(Blocks.DEAD_TUBE_CORAL_BLOCK);
   }

   private boolean check15(Vec3d vec3d) {
      long longValue10 = System.currentTimeMillis();

      for (ServerHelper.ServerHelperState4 serverHelperState410 : this.items) {
         if (longValue10 - serverHelperState410.timestamp <= 500L && serverHelperState410.vec3d.squaredDistanceTo(vec3d) <= 2.25) {
            return true;
         }
      }

      return false;
   }

   private void invoke37(ServerHelper.ServerHelperState4 serverHelperState411) {
      if (CLIENT.world != null) {
         if (this.check16(serverHelperState411.vec3d, Blocks.RESPAWN_ANCHOR, 6, 3, 6)) {
            serverHelperState411.serverHelperState5 = ServerHelper.ServerHelperState5.DRAGON_TRAP;
            serverHelperState411.timestamp2 = 30000L;
            serverHelperState411.flag2 = false;
         }
      }
   }

   private boolean check16(Vec3d vec3d, Block block, int i, int j, int k) {
      if (CLIENT.world == null) {
         return false;
      } else {
         Mutable mutable3 = new Mutable();
         int intValue70 = MathHelper.floor(vec3d.x);
         int intValue71 = MathHelper.floor(vec3d.y);
         int intValue72 = MathHelper.floor(vec3d.z);

         for (int intValue73 = -i; intValue73 <= i; intValue73++) {
            for (int intValue74 = -k; intValue74 <= k; intValue74++) {
               for (int intValue75 = -j; intValue75 <= j; intValue75++) {
                  mutable3.set(intValue70 + intValue73, intValue71 + intValue75, intValue72 + intValue74);
                  if (CLIENT.world.getBlockState(mutable3).isOf(block)) {
                     return true;
                  }
               }
            }
         }

         return false;
      }
   }

   private int compute4() {
      if (CLIENT.player == null) {
         return 0;
      } else {
         int intValue76 = 0;
         PlayerInventory playerInventory = CLIENT.player.getInventory();
         int intValue77 = playerInventory.size();

         for (int intValue78 = 0; intValue78 < intValue77; intValue78++) {
            ItemStack itemStack3 = playerInventory.getStack(intValue78);
            if (itemStack3.isOf(Items.NETHERITE_HOE)) {
               intValue76 += itemStack3.getCount();
            }
         }

         return intValue76;
      }
   }

   @EventHandler
   public void onHudRender(HudRenderEvent hudRenderEvent) {
      if (this.taymeryStruktur.isEnabled() && this.rezhimRaboty.is("FunTime") && CLIENT.player != null && CLIENT.world != null) {
         RenderManager renderManager = hudRenderEvent.getRenderManager();
         if (renderManager != null && CLIENT.gameRenderer != null) {
            boolean flag10 = this.prevyu.isEnabled();
            if (!this.items.isEmpty() || flag10) {
               renderManager.invoke48(20.0F);
               if (flag10) {
                  this.invoke38(renderManager, hudRenderEvent.getIntValue(), hudRenderEvent.getIntValue2());
               }

               long longValue11 = System.currentTimeMillis();
               Vec3d vec3d8 = CLIENT.gameRenderer.getCamera().getPos();

               for (ServerHelper.ServerHelperState4 serverHelperState412 : this.items) {
                  long longValue12 = serverHelperState412.timestamp2 - (longValue11 - serverHelperState412.timestamp);
                  if (longValue12 > 0L) {
                     boolean flag11 = serverHelperState412.serverHelperState5 == ServerHelper.ServerHelperState5.PLAST && serverHelperState412.vec3d2 != null;
                     Vec3d vec3d9 = flag11 ? serverHelperState412.vec3d2 : new Vec3d(serverHelperState412.vec3d.x, serverHelperState412.vec3d.y + 1.4, serverHelperState412.vec3d.z);
                     double doubleValue19 = vec3d8.distanceTo(vec3d9);
                     if (!(doubleValue19 > 110.0)) {
                        Vec3d vec3d10 = MathUtils.resolve(vec3d9);
                        if (vec3d10 != null && !(vec3d10.z <= 0.001) && !(vec3d10.z > 1.0)) {
                           float floatValue19 = (float)MathHelper.clamp(1.0 - (doubleValue19 - 6.0) / 390.0, 0.667, 1.033);
                           float floatValue20 = MathHelper.clamp((float)longValue12 / (float)serverHelperState412.timestamp2, 0.0F, 1.0F);
                           this.invoke39(renderManager, (float)vec3d10.x, (float)vec3d10.y, floatValue19, serverHelperState412.serverHelperState5, longValue12, floatValue20, flag11);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private void invoke38(RenderManager renderManager2, int i, int j) {
      long longValue13 = System.currentTimeMillis();
      float floatValue21 = 0.9F;
      float floatValue22 = 62.0F * floatValue21;
      float floatValue23 = i * 0.5F;
      float floatValue24 = j * 0.36F;

      for (int intValue79 = 0; intValue79 < FoundryShaderSetting.length; intValue79++) {
         ServerHelper.ServerHelperState5 serverHelperState53 = FoundryShaderSetting[intValue79];
         long longValue14 = compute5(serverHelperState53);
         long longValue15 = longValue14 - longValue13 % longValue14;
         float floatValue25 = MathHelper.clamp((float)longValue15 / (float)longValue14, 0.0F, 1.0F);
         float floatValue26 = floatValue24 + intValue79 * floatValue22;
         this.invoke39(renderManager2, floatValue23, floatValue26, floatValue21, serverHelperState53, longValue15, floatValue25, false);
      }
   }

   private static long compute5(ServerHelper.ServerHelperState5 serverHelperState54) {
      return switch (serverHelperState54) {
         case TRAPKA -> 15000L;
         case PLAST -> 20000L;
         case DRAGON_TRAP -> 30000L;
         case DRAGON_PLAST -> 20000L;
      };
   }

   private void invoke39(RenderManager renderManager3, float f, float g, float h, ServerHelper.ServerHelperState5 serverHelperState55, long l, float i, boolean bl) {
      FontObject fontObject = FontRegistry.fontObject4;
      float floatValue27 = (float)l / 1000.0F;
      String text9 = String.format(Locale.US, "%.1f", floatValue27).replace('.', ',') + " Second";
      float floatValue28 = 26.0F * h;
      FontRenderer.FontRendererState fontRendererState = RenderManager.resolve7(fontObject, text9, floatValue28);
      float floatValue29 = fontRendererState.floatValue;
      float floatValue30 = fontRendererState.floatValue2;
      float floatValue31 = 19.0F * h;
      float floatValue32 = 3.6F * h;
      float floatValue33 = floatValue31 * 0.5F + 3.2F * h;
      float floatValue34 = floatValue33 + floatValue32;
      float floatValue35 = floatValue34 * 2.0F;
      float floatValue36 = 8.0F * h;
      float floatValue37 = 14.0F * h;
      float floatValue38 = 7.5F * h;
      float floatValue39 = 9.0F * h;
      float floatValue40 = Math.max(floatValue35, floatValue30);
      float floatValue41 = floatValue40 + floatValue38 * 2.0F;
      float floatValue42 = floatValue36 + floatValue35 + floatValue39 + floatValue29 + floatValue37;
      float floatValue43 = f - floatValue42 * 0.5F;
      float floatValue44 = bl ? g - floatValue41 * 0.5F : g - floatValue41 - 5.0F * h;
      float floatValue45 = MathHelper.clamp((float)l / 500.0F, 0.0F, 1.0F);
      renderManager3.invoke65(floatValue45);
      int intValue80 = compute6(serverHelperState55);
      float floatValue46 = floatValue41 * 0.5F;
      renderManager3.invoke44(floatValue43, floatValue44, floatValue42, floatValue41, floatValue46, 1.0F);
      renderManager3.invoke5(floatValue43, floatValue44, floatValue42, floatValue41, floatValue46, ColorUtils.compute43(15, 16, 22, 210));
      renderManager3.invoke28(floatValue43, floatValue44, floatValue42, floatValue41, floatValue46, ColorUtils.compute43(255, 255, 255, 28), 1.0F);
      float floatValue47 = floatValue43 + floatValue36 + floatValue34;
      float floatValue48 = floatValue44 + floatValue41 * 0.5F;
      renderManager3.invoke39(floatValue47, floatValue48, floatValue33, 0.0F, 1.0F, ColorUtils.compute43(12, 13, 18, 245));
      int intValue81 = ColorUtils.compute14(ColorUtils.compute44(255, 72, 72), intValue80, i);
      int intValue82 = ColorUtils.compute43(255, 255, 255, 40);
      float floatValue49 = (floatValue33 + floatValue34) * 0.5F;
      float floatValue50 = floatValue32 * 0.62F;
      byte byteValue3 = 46;
      int intValue83 = (int)Math.ceil(byteValue3 * i);

      for (int intValue84 = 0; intValue84 < byteValue3; intValue84++) {
         double doubleValue20 = (-Math.PI / 2) + (double)intValue84 / byteValue3 * Math.PI * 2.0;
         float floatValue51 = (float)(Math.cos(doubleValue20) * floatValue49);
         float floatValue52 = (float)(Math.sin(doubleValue20) * floatValue49);
         renderManager3.invoke39(floatValue47 + floatValue51, floatValue48 + floatValue52, floatValue50, 0.0F, 1.0F, intValue84 < intValue83 ? intValue81 : intValue82);
      }

      ItemRenderUtil.invoke3(renderManager3, resolve4(serverHelperState55), floatValue47 - floatValue31 * 0.5F, floatValue48 - floatValue31 * 0.5F, floatValue31 / 16.0F, 0, false, 0);
      float floatValue53 = floatValue43 + floatValue36 + floatValue35 + floatValue39;
      float floatValue54 = floatValue44 + (floatValue41 - floatValue30) * 0.5F + floatValue30 * 0.72F;
      renderManager3.invoke69(fontObject, floatValue53 + 1.0F, floatValue54 + 1.0F, floatValue28, text9, ColorUtils.compute43(0, 0, 0, 165));
      renderManager3.invoke69(fontObject, floatValue53, floatValue54, floatValue28, text9, ColorUtils.compute43(242, 244, 250, 255));
      renderManager3.invoke66();
   }

   static boolean check17(float f, float g) {
      return Math.abs(f - g) < 0.01F;
   }

   private static int compute6(ServerHelper.ServerHelperState5 serverHelperState56) {
      return switch (serverHelperState56) {
         case TRAPKA -> ColorUtils.compute44(255, 150, 60);
         case PLAST -> ColorUtils.compute44(90, 210, 150);
         case DRAGON_TRAP -> ColorUtils.compute44(190, 110, 255);
         case DRAGON_PLAST -> ColorUtils.compute44(225, 120, 210);
      };
   }

   private static ItemStack resolve4(ServerHelper.ServerHelperState5 serverHelperState57) {
      return switch (serverHelperState57) {
         case TRAPKA -> itemStack != null ? itemStack : (itemStack = new ItemStack(Items.NETHERITE_SCRAP));
         case PLAST -> itemStack2 != null ? itemStack2 : (itemStack2 = new ItemStack(Items.DRIED_KELP));
         case DRAGON_TRAP -> itemStack3 != null ? itemStack3 : (itemStack3 = new ItemStack(Items.DRAGON_EGG));
         case DRAGON_PLAST -> SpacerSetting != null ? SpacerSetting : (SpacerSetting = new ItemStack(Items.DRAGON_BREATH));
      };
   }

   record ServerHelperData(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {

      ServerHelper.ServerHelperData lerp(ServerHelper.ServerHelperData serverHelperData3, double d) {
         return new ServerHelper.ServerHelperData(
            MathHelper.lerp(d, this.minX, serverHelperData3.minX),
            MathHelper.lerp(d, this.minY, serverHelperData3.minY),
            MathHelper.lerp(d, this.minZ, serverHelperData3.minZ),
            MathHelper.lerp(d, this.maxX, serverHelperData3.maxX),
            MathHelper.lerp(d, this.maxY, serverHelperData3.maxY),
            MathHelper.lerp(d, this.maxZ, serverHelperData3.maxZ)
         );
      }
   }

   static final class ServerHelperState {
      final String text;
      final long timestamp;
      final long timestamp2;
      final ServerHelper.ServerHelperState3[] serverHelperState3s;

      ServerHelperState(String string, long l, long m, ServerHelper.ServerHelperState3... w103s) {
         this.text = string;
         this.timestamp = l;
         this.timestamp2 = m;
         this.serverHelperState3s = w103s;
      }

      boolean check(ServerHelper.ServerHelperState2 serverHelperState210) {
         for (ServerHelper.ServerHelperState3 serverHelperState32 : this.serverHelperState3s) {
            if (serverHelperState32.check(serverHelperState210)) {
               return true;
            }
         }

         return false;
      }
   }

   static final class ServerHelperState2 {
      final String text;
      final float floatValue;
      final float floatValue2;
      final double doubleValue;
      final double doubleValue2;
      final double doubleValue3;
      final long timestamp;
      boolean flag;

      ServerHelperState2(String string, float f, float g, double d, double e, double h, long l) {
         this.text = string;
         this.floatValue = f;
         this.floatValue2 = g;
         this.doubleValue = d;
         this.doubleValue2 = e;
         this.doubleValue3 = h;
         this.timestamp = l;
      }
   }

   static final class ServerHelperState3 {
      final String text;
      final float floatValue;
      final float floatValue2;

      ServerHelperState3(String string, float f, float g) {
         this.text = string;
         this.floatValue = f;
         this.floatValue2 = g;
      }

      boolean check(ServerHelper.ServerHelperState2 serverHelperState211) {
         return serverHelperState211.text.equals(this.text)
            && ServerHelper.check17(serverHelperState211.floatValue, this.floatValue)
            && ServerHelper.check17(serverHelperState211.floatValue2, this.floatValue2);
      }
   }

   static final class ServerHelperState4 {
      final Vec3d vec3d;
      final long timestamp;
      ServerHelper.ServerHelperState5 serverHelperState5;
      long timestamp2;
      boolean flag;
      boolean flag2;
      boolean flag3;
      boolean flag4;
      ServerHelper.ServerHelperState serverHelperState;
      Vec3d vec3d2;

      ServerHelperState4(Vec3d vec3d, long l, ServerHelper.ServerHelperState5 serverHelperState58, long m) {
         this.vec3d = vec3d;
         this.timestamp = l;
         this.serverHelperState5 = serverHelperState58;
         this.timestamp2 = m;
      }
   }

   static enum ServerHelperState5 {
      TRAPKA,
      PLAST,
      DRAGON_TRAP,
      DRAGON_PLAST;
   }

   static enum ServerHelperState6 {
      IDLE,
      PREPARE,
      PRE_SWAP_STOP,
      WAIT_MAIN_HAND,
      SWAP,
      USE,
      PRE_RESTORE_STOP,
      RESTORE,
      COOLDOWN;
   }

   static class ServerHelperItemState {
      public final Predicate<ItemStack> predicate;
      public final boolean flag;
      public final boolean flag2;
      public final boolean flag3;

      public ServerHelperItemState(Predicate<ItemStack> predicate, boolean bl) {
         this(predicate, bl, false);
      }

      public ServerHelperItemState(Predicate<ItemStack> predicate, boolean bl, boolean bl2) {
         this(predicate, bl, bl2, false);
      }

      public ServerHelperItemState(Predicate<ItemStack> predicate, boolean bl, boolean bl2, boolean bl3) {
         this.predicate = predicate;
         this.flag = bl;
         this.flag2 = bl2;
         this.flag3 = bl3;
      }
   }
}
