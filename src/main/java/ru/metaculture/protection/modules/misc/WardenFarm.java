package ru.metaculture.protection;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.pathing.goals.GoalNear;
import baritone.api.pathing.goals.GoalXZ;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline.Snippet;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.vertex.VertexFormat.DrawMode;
import java.awt.Color;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import net.minecraft.block.AbstractCandleBlock;
import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.block.AmethystClusterBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChainBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.LanternBlock;
import net.minecraft.block.PointedDripstoneBlock;
import net.minecraft.block.SeaPickleBlock;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.RenderLayer.MultiPhaseParameters;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import net.minecraft.world.chunk.WorldChunk;
import org.joml.Matrix4f;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "WardenFarm",
   category = Category.Misc,
   description = "Умный авто-фарм Варден данжа"
)
public class WardenFarm extends Module {
   public final ModeSetting rezhim = new ModeSetting("Режим", "Варден", "Варден", "Медный данж");
   public final GroupSetting predmetyDlyaLuta = new GroupSetting(
      "Предметы для лута",
      new BooleanSetting("Дон зелья", false),
      new BooleanSetting("Сферы", true),
      new BooleanSetting("Талисманы", true),
      new BooleanSetting("Стрелы", false),
      new BooleanSetting("Оружие", false),
      new BooleanSetting("Броня", false),
      new BooleanSetting("Ценные предметы", true),
      new BooleanSetting("Яйца", false)
   );
   public final BooleanSetting stelsRezhimVarden = new BooleanSetting("Стелс режим (Варден)", true);
   public final BooleanSetting othoditPosleLuta = new BooleanSetting("Отходить после лута", true);
   public final BooleanSetting podbiratLutPosleSmerti = new BooleanSetting("Подбирать лут после смерти", true);
   public final BooleanSetting osvobozhdatHotbar = new BooleanSetting("Освобождать хотбар", true);
   public final NumberSetting zhdatSundukDoSek = new NumberSetting("Ждать сундук до (сек)", 240.0F, 5.0F, 600.0F, 10.0F, false);
   public final BooleanSetting avtoEdaIInviz = new BooleanSetting("Авто еда и инвиз", true);
   public final BooleanSetting zelyaSkorostiBratIPit = new BooleanSetting("Зелья скорости (брать и пить)", true).visibleWhen(() -> !this.avtoEdaIInviz.isEnabled());
   public final BooleanSetting skladyvatDrop = new BooleanSetting("Складывать дроп", true);
   public final ModeSetting kudaSkladyvat = new ModeSetting("Куда складывать", "Ресы", "Ресы", "В клан").setVisibilityCondition(() -> !this.skladyvatDrop.isEnabled());
   public final BooleanSetting svapatAnarhii = new BooleanSetting("Свапать анархии", true);
   public final TextSetting anarhiiDlyaFarma = new TextSetting("Анархии для фарма", "903,102,504").setVisibilityCondition(() -> !this.svapatAnarhii.isEnabled());
   public final TextSetting bazovayaAnarhiya = new TextSetting("Базовая анархия", "109").setVisibilityCondition(() -> !this.svapatAnarhii.isEnabled());
   public final TextSetting homDlyaVardena = new TextSetting("Хом для вардена", "warden").setVisibilityCondition(() -> !this.svapatAnarhii.isEnabled());
   private final Map<String, Map<BlockPos, Long>> valuesByKey = new ConcurrentHashMap<>();
   private final Map<String, Map<BlockPos, Long>> valuesByKey2 = new ConcurrentHashMap<>();
   private final Map<String, Integer> valuesByKey3 = new HashMap<>();
   private final List<BlockPos> items = new ArrayList<>();
   private final Queue<Runnable> queue = new ArrayDeque<>();
   private String unknown = "UNKNOWN";
   private Map<BlockPos, Long> valuesByKey4 = new ConcurrentHashMap<>();
   private Map<BlockPos, Long> valuesByKey5 = new ConcurrentHashMap<>();
   private IBaritone iBaritone;
   private WardenFarm.WardenFarmState2 wardenFarmState2 = WardenFarm.WardenFarmState2.SEARCHING;
   private BlockPos blockPos = null;
   private BlockPos blockPos2 = null;
   private double doubleValue = -1.0;
   private final DualTimer dualTimer = new DualTimer();
   private final DualTimer dualTimer2 = new DualTimer();
   private final DualTimer dualTimer3 = new DualTimer();
   private final DualTimer dualTimer4 = new DualTimer();
   private final DualTimer dualTimer5 = new DualTimer();
   private final DualTimer dualTimer6 = new DualTimer();
   private final DualTimer dualTimer7 = new DualTimer();
   private final DualTimer dualTimer8 = new DualTimer();
   private final DualTimer dualTimer9 = new DualTimer();
   private boolean flag = false;
   private long timestamp = 0L;
   private String nA = "N/A";
   private int intValue = 0;
   private boolean flag2 = true;
   private BlockPos blockPos3 = null;
   private int intValue2 = 0;
   private int intValue3 = -1;
   private static final Pattern PATTERN = Pattern.compile("(\\d{1,2}):(\\d{1,2})");
   private static final Pattern PATTERN_2 = Pattern.compile("(\\d{1,2}):(\\d{2})(?::(\\d{2}))?");
   private static final Pattern PATTERN_3 = Pattern.compile("(\\d+)\\s*(с|s|сек|sec)");
   private static final Pattern PATTERN_4 = Pattern.compile(
      "Смерть на координатах \\[(-?\\d+(?:[.,]\\d+)?),\\s*(-?\\d+(?:[.,]\\d+)?),\\s*(-?\\d+(?:[.,]\\d+)?)]"
   );
   private static final double DOUBLE_VALUE = -2000.0;
   private static final double DOUBLE_VALUE_2 = -2000.0;
   private static final double DOUBLE_VALUE_3 = 2000.0;
   private static final double DOUBLE_VALUE_4 = 2000.0;
   private static final double DOUBLE_VALUE_5 = 62500.0;
   private static final double DOUBLE_VALUE_6 = -2068.0;
   private static final double DOUBLE_VALUE_7 = -1932.0;
   private static final double DOUBLE_VALUE_8 = -60.0;
   private static final double DOUBLE_VALUE_9 = -20.0;
   private static final double DOUBLE_VALUE_10 = -2066.0;
   private static final double DOUBLE_VALUE_11 = -1934.0;
   private static final long TIMESTAMP = 3000L;
   private static final double DOUBLE_VALUE_12 = 3.0;
   private static final long TIMESTAMP_2 = 5000L;
   private static final int INT_VALUE = 1;
   private static final int INT_VALUE_2 = 3;
   private static final int INT_VALUE_3 = 1;
   private static final int INT_VALUE_4 = 16;
   private static final double DOUBLE_VALUE_13 = 24.0;
   private static final double DOUBLE_VALUE_14 = 40.0;
   private static final double DOUBLE_VALUE_15 = 16.0;
   private static final long TIMESTAMP_3 = 270000L;
   private static final long TIMESTAMP_4 = 1200L;
   private static final long TIMESTAMP_5 = 4000L;
   private static final double DOUBLE_VALUE_16 = 4.0;
   private static final long TIMESTAMP_6 = 1500L;
   private static final long TIMESTAMP_7 = 250L;
   private static final double DOUBLE_VALUE_17 = 14.0;
   private static final long TIMESTAMP_8 = 15000L;
   private static final String[] RESY = new String[]{"ресы", "ресурс"};
   private static final String[] KIT = new String[]{"кит", "kit", "инвиз", "invis", "зель", "морков", "carrot", "припас", "скор", "speed"};
   private long timestamp2 = 0L;
   private Runnable runnable = null;
   private boolean flag3 = false;
   private final Set<BlockPos> values = new HashSet<>();
   private BlockPos blockPos4 = null;
   private int intValue4 = 0;
   private WardenFarm.WardenFarmState5 wardenFarmState5 = WardenFarm.WardenFarmState5.NONE;
   private int[] ints = null;
   private BlockPos blockPos5 = null;
   private long timestamp3 = 0L;
   private boolean flag4 = false;
   private long timestamp4 = 0L;
   private BlockPos blockPos6 = null;
   private long timestamp5 = 0L;
   private WardenFarm.WardenFarmState4 wardenFarmState4 = WardenFarm.WardenFarmState4.NONE;
   private WardenFarm.WardenFarmState3 wardenFarmState3 = WardenFarm.WardenFarmState3.FIND;
   private BlockPos blockPos7 = null;
   private String nA2 = "N/A";
   private boolean flag5 = false;
   private boolean flag6 = false;
   private boolean flag7 = false;
   private boolean flag8 = false;
   private boolean flag9 = false;
   private WardenFarm.WardenFarmState6 wardenFarmState6 = WardenFarm.WardenFarmState6.NONE;
   private boolean DynamicButtonSetting = false;
   private boolean flag10 = false;
   private int intValue5 = 0;
   private final Set<BlockPos> values2 = new HashSet<>();
   private int intValue6 = -1;
   private final RotationResetController SpacerSetting = new RotationResetController();
   private double FoundryShaderSetting;
   private double doubleValue2;
   private long timestamp6 = 0L;
   private List<Block> items2 = null;
   private Vec3d vec3d = null;
   private long timestamp7 = 0L;
   private long timestamp8 = 0L;
   private WardenFarm.WardenFarmState wardenFarmState = WardenFarm.WardenFarmState.NONE;
   private int intValue7 = -1;
   private int intValue8 = -1;
   private final DualTimer dualTimer10 = new DualTimer();
   private final DualTimer dualTimer11 = new DualTimer();
   private final DualTimer dualTimer12 = new DualTimer();
   private long timestamp9 = 0L;
   private boolean flag11 = false;
   private long timestamp10 = 0L;
   private boolean flag12 = false;
   private long timestamp11 = 0L;
   private static final int INT_VALUE_5 = 1024;
   private static final RenderPipeline RENDER_PIPELINE = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.POSITION_COLOR_SNIPPET})
         .withLocation(Identifier.of("wild", "block_esp_box"))
         .withVertexFormat(VertexFormats.POSITION_COLOR, DrawMode.QUADS)
         .withCull(false)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .withBlend(BlendFunction.LIGHTNING)
         .build()
   );
   private static final RenderLayer RENDER_LAYER = RenderLayer.of(
      "chest_esp_box", 1024, false, true, RENDER_PIPELINE, MultiPhaseParameters.builder().build(false)
   );

   public WardenFarm() {
      this.addSettings(
         new Setting[]{
            this.rezhim,
            this.predmetyDlyaLuta,
            this.stelsRezhimVarden,
            this.othoditPosleLuta,
            this.podbiratLutPosleSmerti,
            this.osvobozhdatHotbar,
            this.zhdatSundukDoSek,
            this.avtoEdaIInviz,
            this.zelyaSkorostiBratIPit,
            this.skladyvatDrop,
            this.kudaSkladyvat,
            this.svapatAnarhii,
            this.anarhiiDlyaFarma,
            this.bazovayaAnarhiya,
            this.homDlyaVardena
         }
      );
   }

   @Override
   public void onEnable() {
      FreeLookController.active = true;
      super.onEnable();
      this.iBaritone = BaritoneAPI.getProvider().getPrimaryBaritone();
      this.flag2 = (Boolean)BaritoneAPI.getSettings().allowSprint.value;
      this.invoke33();
      this.FoundryShaderSetting = Math.random() * Math.PI * 2.0;
      this.doubleValue2 = Math.random() * Math.PI * 2.0;
      this.dualTimer4.invoke();
      this.invoke2();
      ServerStatsParser.INSTANCE.invoke2();
      if (this.avtoEdaIInviz.isEnabled()
         && this.svapatAnarhii.isEnabled()
         && this.check43(ServerStatsParser.INSTANCE.getNA2())
         && this.check47()) {
         this.invoke51();
      }
   }

   @Override
   public void onDisable() {
      super.onDisable();
      if (this.iBaritone != null) {
         this.iBaritone.getPathingBehavior().cancelEverything();
         BaritoneAPI.getSettings().allowSprint.value = this.flag2;
      }

      this.invoke34();
      if (CLIENT.player != null) {
         CLIENT.player.setSneaking(false);
      }

      this.invoke60();
      this.invoke2();
      this.SpacerSetting.invoke4();
      RotationController.rotationControllerState2 = RotationController.RotationControllerState2.IDLE;
      RotationController.intValue = 0;
      RotationController.rotation = null;
      FreeLookController.active = false;
   }

   private void invoke() {
      if (this.dualTimer7.check5(1000L)) {
         ServerStatsParser.INSTANCE.invoke2();
         String text = ServerStatsParser.INSTANCE.getNA2();
         String text2 = text != null && !text.equals("N/A") ? text : "UNKNOWN";
         if (!text2.equals(this.unknown)) {
            this.unknown = text2;
            this.valuesByKey4 = this.valuesByKey.computeIfAbsent(this.unknown, string -> new ConcurrentHashMap<>());
            this.valuesByKey5 = this.valuesByKey2.computeIfAbsent(this.unknown, string -> new ConcurrentHashMap<>());
         }

         this.dualTimer7.invoke();
      }
   }

   private void invoke2() {
      this.items.clear();
      this.valuesByKey3.clear();
      this.queue.clear();
      this.values.clear();
      this.blockPos4 = null;
      this.intValue4 = 0;
      this.wardenFarmState5 = WardenFarm.WardenFarmState5.NONE;
      this.blockPos = null;
      this.blockPos2 = null;
      this.doubleValue = -1.0;
      this.timestamp10 = 0L;
      this.flag12 = false;
      this.ints = null;
      this.blockPos5 = null;
      this.timestamp3 = 0L;
      this.invoke29();
      this.blockPos6 = null;
      this.timestamp5 = 0L;
      this.wardenFarmState2 = WardenFarm.WardenFarmState2.SEARCHING;
      this.flag = false;
      this.timestamp2 = 0L;
      this.runnable = null;
      this.flag3 = false;
      this.flag11 = false;
      this.flag6 = false;
      this.invoke48();
      this.dualTimer6.invoke();
      RotationController.rotationControllerState2 = RotationController.RotationControllerState2.IDLE;
   }

   @EventHandler
   public void onWorldJoin(WorldJoinEvent worldJoinEvent) {
      this.items.clear();
      this.values.clear();
      this.blockPos = null;
      this.ints = null;
      this.doubleValue = -1.0;
      this.blockPos6 = null;
      this.timestamp5 = 0L;
      this.invoke29();
      this.dualTimer4.invoke();
      this.unknown = "UNKNOWN";
      if (this.wardenFarmState4 == WardenFarm.WardenFarmState4.NONE
         && this.wardenFarmState2 != WardenFarm.WardenFarmState2.HUB_WAITING_FOR_CHEST
         && this.wardenFarmState2 != WardenFarm.WardenFarmState2.SWAPPING_TO_SAVE_ANARCHY
         && this.wardenFarmState2 != WardenFarm.WardenFarmState2.GOING_TO_STASH
         && this.wardenFarmState2 != WardenFarm.WardenFarmState2.OPENING_STASH
         && this.wardenFarmState2 != WardenFarm.WardenFarmState2.ROTATING_STASH
         && this.wardenFarmState2 != WardenFarm.WardenFarmState2.OPENING_STASH_BLOCK
         && this.wardenFarmState2 != WardenFarm.WardenFarmState2.WAITING_FOR_GUI_STASH
         && this.wardenFarmState2 != WardenFarm.WardenFarmState2.STORING_IN_CHEST) {
         this.wardenFarmState2 = WardenFarm.WardenFarmState2.SEARCHING;
         this.dualTimer6.invoke();
      }
   }

   private boolean check(Vec3d vec3d) {
      return this.rezhim.is("Варден")
         ? (vec3d.x - -2000.0) * (vec3d.x - -2000.0) + (vec3d.z - -2000.0) * (vec3d.z - -2000.0) <= 62500.0
         : (vec3d.x - 2000.0) * (vec3d.x - 2000.0) + (vec3d.z - 2000.0) * (vec3d.z - 2000.0) <= 62500.0;
   }

   private boolean check2() {
      return CLIENT.player == null ? false : this.check(CLIENT.player.getPos());
   }

   private boolean check3(Vec3d vec3d) {
      return !this.rezhim.is("Варден")
         ? this.check(vec3d)
         : vec3d.x >= -2068.0 && vec3d.x <= -1932.0 && vec3d.y >= -60.0 && vec3d.y <= -20.0 && vec3d.z >= -2066.0 && vec3d.z <= -1934.0;
   }

   private int[] resolve() {
      if (this.rezhim.is("Варден")) {
         int intValue = (int)(-2068.0 + Math.random() * 136.0);
         int intValue2 = (int)(-2066.0 + Math.random() * 132.0);
         return new int[]{intValue, intValue2};
      } else {
         double doubleValue = Math.random() * Math.PI * 2.0;
         double doubleValue2 = Math.sqrt(Math.random()) * 240.0;
         int intValue3 = (int)(2000.0 + doubleValue2 * Math.cos(doubleValue));
         int intValue4 = (int)(2000.0 + doubleValue2 * Math.sin(doubleValue));
         return new int[]{intValue3, intValue4};
      }
   }

   private long compute(BlockPos blockPos) {
      long longValue = this.valuesByKey4.getOrDefault(blockPos, 0L);
      long longValue2 = 0L;

      try {
         longValue2 = ServerDHelper.VALUES_BY_KEY.getOrDefault(blockPos, 0L);
      } catch (Exception exception) {
      }

      return Math.max(longValue, longValue2);
   }

   private String resolve2() {
      String[] texts = this.anarhiiDlyaFarma.getValue().split(",");
      if (texts.length != 0 && !texts[0].trim().isEmpty()) {
         this.intValue = (this.intValue + 1) % texts.length;
         return texts[this.intValue].trim();
      } else {
         return this.bazovayaAnarhiya.getValue();
      }
   }

   private boolean check4() {
      boolean flag;
      if (ServerStatsParser.check()) {
         flag = true;
      } else if (PvPSafe.check2()) {
         if (this.timestamp11 == 0L) {
            this.timestamp11 = System.currentTimeMillis();
         }

         flag = System.currentTimeMillis() - this.timestamp11 <= 90000L;
      } else {
         this.timestamp11 = 0L;
         flag = false;
      }

      if (flag) {
         this.timestamp9 = System.currentTimeMillis();
      }

      return flag;
   }

   private boolean check5() {
      return this.timestamp9 > 0L && System.currentTimeMillis() - this.timestamp9 < 1500L;
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player != null && CLIENT.world != null) {
         if (this.dualTimer4.check5(1000L)) {
            this.invoke();
            this.invoke3();
            if (!CLIENT.player.isDead() && !(CLIENT.currentScreen instanceof DeathScreen)) {
               if (!(CLIENT.currentScreen instanceof GenericContainerScreen)) {
                  this.intValue3 = -1;
               }

               if (!this.check2()) {
                  this.invoke29();
               }

               if (!this.check55()) {
                  this.invoke35();
                  if (this.flag3 && !this.check7()) {
                     this.flag3 = false;
                     this.invoke43();
                  } else if (this.wardenFarmState4 != WardenFarm.WardenFarmState4.NONE) {
                     if (CLIENT.currentScreen instanceof GenericContainerScreen genericContainerScreen2) {
                        this.iBaritone.getPathingBehavior().cancelEverything();
                        if (this.check51(genericContainerScreen2)) {
                           this.wardenFarmState3 = WardenFarm.WardenFarmState3.WAIT_GUI;
                           this.invoke56((GenericContainerScreenHandler)genericContainerScreen2.getScreenHandler());
                        }
                     } else {
                        this.invoke54();
                     }
                  } else if (this.wardenFarmState6 == WardenFarm.WardenFarmState6.NONE || !this.check41()) {
                     if (this.flag5 && !CLIENT.player.isDead() && !(CLIENT.currentScreen instanceof DeathScreen)) {
                        this.flag5 = false;
                        this.invoke5();
                     } else if (this.flag) {
                        if (System.currentTimeMillis() >= this.timestamp) {
                           ServerStatsParser.INSTANCE.invoke2();
                           String text3 = ServerStatsParser.INSTANCE.getNA2();
                           String text4 = !"N/A".equals(this.nA) && this.nA != null ? this.nA : this.resolve20();
                           if ("N/A".equals(text3) || !text3.equals(text4) && !this.check43(text3)) {
                              if (!this.check7() && !"N/A".equals(text4)) {
                                 CLIENT.player.networkHandler.sendChatCommand("an" + text4);
                                 this.timestamp = System.currentTimeMillis() + 8000L;
                              } else {
                                 this.timestamp = System.currentTimeMillis() + 2000L;
                              }
                           } else {
                              this.flag = false;
                              this.dualTimer3.invoke();
                              this.wardenFarmState2 = WardenFarm.WardenFarmState2.SEARCHING;
                              this.dualTimer6.invoke();
                           }
                        }
                     } else if (this.wardenFarmState2 == WardenFarm.WardenFarmState2.HUB_WAITING_FOR_CHEST) {
                        if (this.blockPos2 != null) {
                           long longValue3 = this.compute(this.blockPos2) - System.currentTimeMillis();
                           if (longValue3 <= 2000L) {
                              this.wardenFarmState2 = WardenFarm.WardenFarmState2.SEARCHING;
                              this.dualTimer6.invoke();
                              this.flag = true;
                              this.timestamp = System.currentTimeMillis();
                           }
                        } else {
                           this.wardenFarmState2 = WardenFarm.WardenFarmState2.SEARCHING;
                           this.dualTimer6.invoke();
                        }
                     } else {
                        if (this.wardenFarmState4 == WardenFarm.WardenFarmState4.NONE && this.wardenFarmState6 == WardenFarm.WardenFarmState6.NONE && !this.check2()) {
                           if ("UNKNOWN".equals(this.unknown)) {
                              if (this.timestamp6 == 0L) {
                                 this.timestamp6 = System.currentTimeMillis();
                              } else if (System.currentTimeMillis() - this.timestamp6 > 15000L) {
                                 this.timestamp6 = 0L;
                                 this.flag = true;
                                 this.timestamp = System.currentTimeMillis();
                                 return;
                              }
                           } else {
                              this.timestamp6 = 0L;
                           }
                        }

                        boolean flag2 = this.wardenFarmState4 != WardenFarm.WardenFarmState4.NONE;
                        boolean flag3 = this.wardenFarmState2 == WardenFarm.WardenFarmState2.SWAPPING_TO_SAVE_ANARCHY
                           || this.wardenFarmState2 == WardenFarm.WardenFarmState2.GOING_TO_STASH
                           || this.wardenFarmState2 == WardenFarm.WardenFarmState2.OPENING_STASH
                           || this.wardenFarmState2 == WardenFarm.WardenFarmState2.ROTATING_STASH
                           || this.wardenFarmState2 == WardenFarm.WardenFarmState2.OPENING_STASH_BLOCK
                           || this.wardenFarmState2 == WardenFarm.WardenFarmState2.WAITING_FOR_GUI_STASH
                           || this.wardenFarmState2 == WardenFarm.WardenFarmState2.STORING_IN_CHEST;
                        boolean flag4 = CLIENT.currentScreen instanceof GenericContainerScreen;
                        if (this.iBaritone != null) {
                           if (!this.check2() && !flag3 && !flag2 && !flag4) {
                              if (!this.flag6 || !this.check42()) {
                                 if (this.check43(ServerStatsParser.INSTANCE.getNA2())
                                    && (this.wardenFarmState6 != WardenFarm.WardenFarmState6.NONE || this.check40())) {
                                    ;
                                 }
                              }
                           } else {
                              if (this.check2()) {
                                 this.flag6 = false;
                                 this.invoke28();
                                 this.invoke57();
                                 if (this.dualTimer8.check5(500L)) {
                                    this.invoke30();
                                    this.dualTimer8.invoke();
                                 }
                              }

                              if (this.wardenFarmState != WardenFarm.WardenFarmState.NONE && CLIENT.currentScreen == null) {
                                 if (this.check2()) {
                                    this.iBaritone.getPathingBehavior().cancelEverything();
                                    if (CLIENT.player.isSprinting()) {
                                       CLIENT.player.setSprinting(false);
                                    }

                                    return;
                                 }

                                 this.invoke60();
                              }

                              if (!(CLIENT.currentScreen instanceof GenericContainerScreen genericContainerScreen3)) {
                                 switch (this.wardenFarmState2) {
                                    case SEARCHING:
                                       this.invoke12();
                                       break;
                                    case GOING_TO_CHEST:
                                       this.invoke13();
                                       break;
                                    case ROTATING:
                                       this.invoke14();
                                       break;
                                    case OPENING:
                                       this.invoke15();
                                       break;
                                    case WAITING_FOR_GUI:
                                       this.invoke27();
                                       break;
                                    case RETREATING:
                                       this.invoke17();
                                       break;
                                    case GOING_TO_DEATH_LOOT:
                                       this.invoke18();
                                       break;
                                    case COLLECTING_DEATH_LOOT:
                                       this.invoke19();
                                    case HUB_WAITING_FOR_CHEST:
                                    default:
                                       break;
                                    case SWAPPING_TO_SAVE_ANARCHY:
                                       this.invoke21();
                                       break;
                                    case GOING_TO_STASH:
                                       this.invoke22();
                                       break;
                                    case OPENING_STASH:
                                       this.invoke26();
                                       break;
                                    case ROTATING_STASH:
                                       this.invoke23();
                                       break;
                                    case OPENING_STASH_BLOCK:
                                       this.invoke24();
                                       break;
                                    case WAITING_FOR_GUI_STASH:
                                       this.invoke25();
                                 }

                                 if (this.wardenFarmState4 == WardenFarm.WardenFarmState4.NONE) {
                                    this.invoke6();
                                 }
                              } else {
                                 this.iBaritone.getPathingBehavior().cancelEverything();
                                 String text5 = genericContainerScreen3.getTitle().getString().toLowerCase().replaceAll("§.", "").trim();
                                 boolean flag5 = text5.contains("клан") || text5.contains("clan") || text5.contains("хранилище");
                                 boolean flag6 = this.rezhim.is("Варден")
                                    ? text5.equals("сундук") || text5.equals("большой сундук") || text5.equals("chest") || text5.equals("large chest")
                                    : text5.equals("бочка") || text5.equals("barrel");
                                 boolean flag7 = this.wardenFarmState2 == WardenFarm.WardenFarmState2.WAITING_FOR_GUI_STASH
                                    || this.wardenFarmState2 == WardenFarm.WardenFarmState2.STORING_IN_CHEST
                                    || !this.check2() && this.skladyvatDrop.isEnabled() && this.check44() && this.check32();
                                 if (flag5 && this.kudaSkladyvat.is("В клан")) {
                                    this.invoke44((GenericContainerScreenHandler)genericContainerScreen3.getScreenHandler());
                                 } else if (flag7) {
                                    this.wardenFarmState2 = WardenFarm.WardenFarmState2.STORING_IN_CHEST;
                                    this.invoke44((GenericContainerScreenHandler)genericContainerScreen3.getScreenHandler());
                                 } else if (this.check2() && flag6) {
                                    this.invoke36((GenericContainerScreenHandler)genericContainerScreen3.getScreenHandler());
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            } else {
               this.invoke4();
            }
         }
      }
   }

   @EventHandler
   public void onPacket(PacketEvent packetEvent) {
      if (CLIENT.player != null && packetEvent.getPacketEventState() == ru.metaculture.protection.PacketEvent.PacketEventState.RECEIVE) {
         if (packetEvent.getPacket() instanceof GameMessageS2CPacket gameMessageS2CPacket) {
            String text6 = gameMessageS2CPacket.content().getString();
            if (this.podbiratLutPosleSmerti.isEnabled()) {
               Matcher matcher = PATTERN_4.matcher(text6);
               if (matcher.find()) {
                  try {
                     double doubleValue3 = Double.parseDouble(matcher.group(1).replace(',', '.'));
                     double doubleValue4 = Double.parseDouble(matcher.group(2).replace(',', '.'));
                     double doubleValue5 = Double.parseDouble(matcher.group(3).replace(',', '.'));
                     if (this.check3(new Vec3d(doubleValue3, doubleValue4, doubleValue5))) {
                        this.blockPos5 = BlockPos.ofFloored(doubleValue3, doubleValue4, doubleValue5);
                        this.timestamp3 = System.currentTimeMillis() + 270000L;
                     }
                  } catch (NumberFormatException numberFormatException) {
                  }
               }
            }

            if (this.wardenFarmState6 != WardenFarm.WardenFarmState6.NONE || this.wardenFarmState4 == WardenFarm.WardenFarmState4.TELEPORT_WARDEN) {
               if (this.check50(text6)) {
                  this.flag8 = true;
               }
            }
         }
      }
   }

   private void invoke3() {
      if (this.avtoEdaIInviz.isEnabled() && this.svapatAnarhii.isEnabled() && CLIENT.player != null) {
         if (CLIENT.currentScreen instanceof DeathScreen || CLIENT.player.isDead()) {
            if (this.check45()) {
               if (!this.flag5) {
                  ServerStatsParser.INSTANCE.invoke2();
                  String text7 = ServerStatsParser.INSTANCE.getNA2();
                  if (!"N/A".equals(text7)) {
                     this.nA2 = text7;
                  }

                  this.invoke49();
                  this.invoke48();
                  this.flag5 = true;
                  this.flag6 = true;
                  this.valuesByKey3.clear();
                  this.queue.clear();
                  this.flag11 = false;
                  this.runnable = null;
                  this.flag3 = false;
                  this.blockPos = null;
                  this.blockPos3 = null;
                  this.ints = null;
                  this.wardenFarmState2 = WardenFarm.WardenFarmState2.SEARCHING;
                  if (this.iBaritone != null) {
                     this.iBaritone.getPathingBehavior().cancelEverything();
                  }

                  this.dualTimer6.invoke();
                  this.dualTimer3.invoke();
               }
            }
         }
      }
   }

   private void invoke4() {
      if (this.iBaritone != null) {
         this.iBaritone.getPathingBehavior().cancelEverything();
      }

      this.invoke60();
      if (this.dualTimer12.check5(1000L)) {
         CLIENT.player.requestRespawn();
         if (CLIENT.currentScreen instanceof DeathScreen) {
            CLIENT.setScreen(null);
         }

         this.dualTimer12.invoke();
      }
   }

   private void invoke5() {
      this.invoke49();
      this.blockPos = null;
      this.wardenFarmState2 = WardenFarm.WardenFarmState2.SEARCHING;
      this.dualTimer6.invoke();
      this.dualTimer3.invoke();
      this.dualTimer.invoke();
      if (this.iBaritone != null) {
         this.iBaritone.getPathingBehavior().cancelEverything();
      }

      ServerStatsParser.INSTANCE.invoke2();
      if (this.check47()) {
         this.invoke52();
      } else {
         if (!this.check2()) {
            this.flag6 = true;
            this.dualTimer.invoke();
         }
      }
   }

   private boolean check6() {
      if (CLIENT.currentScreen instanceof GenericContainerScreen genericContainerScreen4) {
         String text8 = genericContainerScreen4.getTitle().getString().toLowerCase().replaceAll("§.", "").trim();
         boolean flag8 = this.rezhim.is("Варден")
            ? text8.equals("сундук") || text8.equals("большой сундук") || text8.equals("chest") || text8.equals("large chest")
            : text8.equals("бочка") || text8.equals("barrel");
         if (this.check2() && flag8) {
            return true;
         }
      }

      return this.wardenFarmState2 == WardenFarm.WardenFarmState2.ROTATING
         || this.wardenFarmState2 == WardenFarm.WardenFarmState2.OPENING
         || this.wardenFarmState2 == WardenFarm.WardenFarmState2.WAITING_FOR_GUI;
   }

   private void invoke6() {
      if (this.runnable != null && !this.check7() && !this.check6()) {
         Runnable runnable2 = this.runnable;
         this.runnable = null;
         runnable2.run();
      }
   }

   private boolean check7() {
      if (this.timestamp2 > 0L && System.currentTimeMillis() - this.timestamp2 < 3000L) {
         return true;
      } else {
         return this.check4() ? true : this.check5();
      }
   }

   private void invoke7() {
      this.timestamp2 = System.currentTimeMillis();
   }

   private void invoke8(Runnable runnable) {
      this.invoke10(runnable);
   }

   private void invoke9(Runnable runnable) {
      this.invoke10(runnable);
   }

   private void invoke10(Runnable runnable) {
      if (!this.check7()) {
         runnable.run();
      } else {
         this.runnable = runnable;
         this.invoke11();
      }
   }

   private void invoke11() {
      if (this.check2()) {
         if (this.wardenFarmState2 != WardenFarm.WardenFarmState2.SWAPPING_TO_SAVE_ANARCHY
            && this.wardenFarmState2 != WardenFarm.WardenFarmState2.GOING_TO_STASH
            && this.wardenFarmState2 != WardenFarm.WardenFarmState2.OPENING_STASH
            && this.wardenFarmState2 != WardenFarm.WardenFarmState2.ROTATING_STASH
            && this.wardenFarmState2 != WardenFarm.WardenFarmState2.OPENING_STASH_BLOCK
            && this.wardenFarmState2 != WardenFarm.WardenFarmState2.WAITING_FOR_GUI_STASH
            && this.wardenFarmState2 != WardenFarm.WardenFarmState2.STORING_IN_CHEST
            && this.wardenFarmState2 != WardenFarm.WardenFarmState2.HUB_WAITING_FOR_CHEST) {
            this.wardenFarmState2 = WardenFarm.WardenFarmState2.SEARCHING;
            this.dualTimer6.invoke();
         }
      }
   }

   private void invoke12() {
      if (!this.check2()) {
         if (this.check43(ServerStatsParser.INSTANCE.getNA2())) {
            if (this.wardenFarmState6 != WardenFarm.WardenFarmState6.NONE) {
               return;
            }

            this.check40();
         }
      } else if (this.avtoEdaIInviz.isEnabled()
         && this.svapatAnarhii.isEnabled()
         && this.check43(ServerStatsParser.INSTANCE.getNA2())
         && this.check47()) {
         this.invoke51();
      } else if (this.check10()) {
         this.iBaritone.getPathingBehavior().cancelEverything();
         this.wardenFarmState2 = WardenFarm.WardenFarmState2.GOING_TO_DEATH_LOOT;
         this.dualTimer.invoke();
         this.dualTimer3.invoke();
         this.dualTimer5.invoke();
      } else {
         boolean flag9 = this.runnable != null || this.flag3;
         if (flag9) {
            if (this.iBaritone.getCustomGoalProcess().isActive()) {
               this.iBaritone.getPathingBehavior().cancelEverything();
            }

            this.invoke16();
         } else if (this.check14()) {
            this.invoke42();
         } else {
            this.invoke31();
            this.blockPos = this.resolve7();
            if (this.blockPos != null) {
               this.wardenFarmState2 = WardenFarm.WardenFarmState2.GOING_TO_CHEST;
               this.doubleValue = CLIENT.player.getPos().distanceTo(Vec3d.ofCenter(this.blockPos));
               this.timestamp10 = 0L;
               this.flag12 = false;
               this.dualTimer3.invoke();
               this.dualTimer6.invoke();
            } else {
               boolean flag10 = false;
               if (!this.items.isEmpty() && this.dualTimer6.check5(20000L)) {
                  flag10 = this.check11();
               }

               if (!flag10) {
                  BlockPos blockPos2 = this.resolve3();
                  if (blockPos2 != null) {
                     double doubleValue6 = CLIENT.player.getPos().distanceTo(Vec3d.ofCenter(blockPos2));
                     if (doubleValue6 > 6.0) {
                        if (!this.iBaritone.getCustomGoalProcess().isActive() || this.dualTimer5.check5(3000L)) {
                           this.iBaritone.getCustomGoalProcess().setGoalAndPath(new GoalNear(blockPos2, 3));
                           this.dualTimer5.invoke();
                        }
                     } else if (this.iBaritone.getCustomGoalProcess().isActive()) {
                        this.iBaritone.getPathingBehavior().cancelEverything();
                     }
                  } else {
                     if (!this.iBaritone.getCustomGoalProcess().isActive() || this.dualTimer5.check5(6000L)) {
                        int[] intValues = this.resolve();
                        this.iBaritone.getCustomGoalProcess().setGoalAndPath(new GoalXZ(intValues[0], intValues[1]));
                        this.dualTimer5.invoke();
                     }
                  }
               }
            }
         }
      }
   }

   private BlockPos resolve3() {
      long longValue4 = System.currentTimeMillis();
      return this.items
         .stream()
         .filter(blockPos -> !this.values.contains(blockPos))
         .filter(blockPos -> this.compute6(blockPos) <= this.compute7())
         .filter(blockPos -> {
            Long longValue5 = this.valuesByKey5.get(blockPos);
            return longValue5 == null || longValue5 <= longValue4;
         })
         .min(
            Comparator.<BlockPos>comparingLong(blockPos -> Math.max(0L, this.compute6(blockPos)))
               .thenComparingDouble(blockPos -> CLIENT.player.getPos().distanceTo(Vec3d.ofCenter(blockPos)))
         )
         .orElse(null);
   }

   private void invoke13() {
      if (this.blockPos != null
         && !this.check13(this.blockPos)
         && CLIENT.world.getBlockState(this.blockPos).getBlock() == (this.rezhim.is("Варден") ? Blocks.CHEST : Blocks.BARREL)) {
         double doubleValue7 = CLIENT.player.getPos().distanceTo(Vec3d.ofCenter(this.blockPos));
         if (doubleValue7 < this.doubleValue - 1.0) {
            this.doubleValue = doubleValue7;
            this.dualTimer3.invoke();
         }

         boolean flag11 = doubleValue7 <= 4.0;
         boolean flag12 = flag11 && this.check16(this.blockPos);
         if (flag12) {
            this.blockPos6 = null;
            long longValue6 = this.compute(this.blockPos) - System.currentTimeMillis();
            if (this.flag12 || longValue6 > 15000L || longValue6 <= 2500L) {
               this.iBaritone.getPathingBehavior().cancelEverything();
               boolean flag13 = longValue6 <= -10L && !this.check12(this.blockPos);
               if (flag13) {
                  if (this.timestamp10 == 0L) {
                     this.timestamp10 = System.currentTimeMillis();
                  }

                  if (System.currentTimeMillis() - this.timestamp10 >= 250L) {
                     this.timestamp10 = 0L;
                     this.wardenFarmState2 = WardenFarm.WardenFarmState2.ROTATING;
                     this.dualTimer.invoke();
                  }
               } else {
                  this.timestamp10 = 0L;
               }
            } else if (this.check7()) {
               this.iBaritone.getPathingBehavior().cancelEverything();
            } else {
               ServerStatsParser.INSTANCE.invoke2();
               String text9 = ServerStatsParser.INSTANCE.getNA2();
               if (!"N/A".equals(text9)) {
                  this.nA = text9;
               }

               this.blockPos2 = this.blockPos;
               this.invoke9((Runnable)(() -> {
                  CLIENT.player.networkHandler.sendChatCommand("hub");
                  this.wardenFarmState2 = WardenFarm.WardenFarmState2.HUB_WAITING_FOR_CHEST;
               }));
            }
         } else if (!flag11) {
            this.blockPos6 = null;
            if (!this.iBaritone.getCustomGoalProcess().isActive()) {
               this.iBaritone.getCustomGoalProcess().setGoalAndPath(new GoalNear(this.blockPos, 1));
            }

            if (this.dualTimer3.check5(15000L)) {
               this.valuesByKey5.put(this.blockPos, System.currentTimeMillis() + 30000L);
               this.iBaritone.getPathingBehavior().cancelEverything();
               this.blockPos = null;
               this.blockPos6 = null;
               this.doubleValue = -1.0;
               this.dualTimer3.invoke();
               this.dualTimer5.invoke();
               this.wardenFarmState2 = WardenFarm.WardenFarmState2.SEARCHING;
               this.dualTimer6.invoke();
            }
         } else {
            if (this.blockPos6 == null || !this.blockPos6.equals(this.blockPos)) {
               this.blockPos6 = this.blockPos;
               this.timestamp5 = System.currentTimeMillis();
            }

            if (!this.iBaritone.getCustomGoalProcess().isActive()) {
               this.iBaritone.getCustomGoalProcess().setGoalAndPath(new GoalNear(this.blockPos, 1));
            }

            if (System.currentTimeMillis() - this.timestamp5 >= 4000L) {
               this.valuesByKey5.put(this.blockPos, System.currentTimeMillis() + 30000L);
               this.iBaritone.getPathingBehavior().cancelEverything();
               this.blockPos = null;
               this.blockPos6 = null;
               this.doubleValue = -1.0;
               this.dualTimer3.invoke();
               this.dualTimer5.invoke();
               this.wardenFarmState2 = WardenFarm.WardenFarmState2.SEARCHING;
               this.dualTimer6.invoke();
            }
         }
      } else {
         this.iBaritone.getPathingBehavior().cancelEverything();
         this.wardenFarmState2 = WardenFarm.WardenFarmState2.SEARCHING;
         this.dualTimer6.invoke();
      }
   }

   private void invoke14() {
      if (this.blockPos == null) {
         this.wardenFarmState2 = WardenFarm.WardenFarmState2.SEARCHING;
         this.dualTimer6.invoke();
      } else if (!this.check17(this.blockPos)) {
         this.doubleValue = CLIENT.player.getPos().distanceTo(Vec3d.ofCenter(this.blockPos));
         this.wardenFarmState2 = WardenFarm.WardenFarmState2.GOING_TO_CHEST;
         this.dualTimer3.invoke();
      } else {
         Rotation rotation = this.resolve12(this.resolve9(this.blockPos));
         this.SpacerSetting.invoke3(this.resolve11(rotation, this.measure2(rotation)), 35.0F, 35.0F, 35.0F, 35.0F, 20, 1);
         if (this.resolve10(this.blockPos) != null && this.dualTimer.check5(50L)) {
            this.wardenFarmState2 = WardenFarm.WardenFarmState2.OPENING;
            this.dualTimer.invoke();
         }
      }
   }

   private void invoke15() {
      if (this.blockPos == null) {
         this.wardenFarmState2 = WardenFarm.WardenFarmState2.SEARCHING;
         this.dualTimer6.invoke();
      } else if (!this.check17(this.blockPos)) {
         this.doubleValue = CLIENT.player.getPos().distanceTo(Vec3d.ofCenter(this.blockPos));
         this.wardenFarmState2 = WardenFarm.WardenFarmState2.GOING_TO_CHEST;
         this.dualTimer3.invoke();
      } else {
         int intValue5 = CLIENT.player.getInventory().getSelectedSlot();
         ItemStack itemStack2 = (ItemStack)CLIENT.player.getInventory().getMainStacks().get(intValue5);
         if (itemStack2.getItem() == Items.TRIPWIRE_HOOK || itemStack2.getName().getString().contains("[★]")) {
            for (int intValue6 = 0; intValue6 < 9; intValue6++) {
               ItemStack itemStack3 = (ItemStack)CLIENT.player.getInventory().getMainStacks().get(intValue6);
               if (itemStack3.isEmpty() || itemStack3.getItem() != Items.TRIPWIRE_HOOK && !itemStack3.getName().getString().contains("[★]")) {
                  CLIENT.player.getInventory().setSelectedSlot(intValue6);
                  this.dualTimer.invoke();
                  break;
               }
            }
         }

         Rotation rotation2 = this.resolve12(this.resolve9(this.blockPos));
         this.SpacerSetting.invoke3(this.resolve11(rotation2, 0.6F), 18.0F, 18.0F, 20.0F, 20.0F, 20, 1);
         BlockHitResult blockHitResult = this.resolve10(this.blockPos);
         if (blockHitResult == null) {
            if (this.dualTimer.check5(1200L)) {
               this.wardenFarmState2 = WardenFarm.WardenFarmState2.ROTATING;
               this.dualTimer.invoke();
            }
         } else {
            if (this.dualTimer.check5(10L)) {
               CLIENT.player.swingHand(Hand.MAIN_HAND);
               CLIENT.interactionManager.interactBlock(CLIENT.player, Hand.MAIN_HAND, blockHitResult);
               RotationController.rotationControllerState2 = RotationController.RotationControllerState2.IDLE;
               this.invoke7();
               this.valuesByKey5.put(this.blockPos, System.currentTimeMillis() + 5000L);
               this.wardenFarmState2 = WardenFarm.WardenFarmState2.WAITING_FOR_GUI;
               this.dualTimer.invoke();
            }
         }
      }
   }

   private boolean check8() {
      for (PlayerEntity playerEntity : CLIENT.world.getPlayers()) {
         if (playerEntity != CLIENT.player && !playerEntity.isDead() && CLIENT.player.distanceTo(playerEntity) <= 40.0) {
            return true;
         }
      }

      for (Entity entity : CLIENT.world.getEntities()) {
         if (entity instanceof WardenEntity && CLIENT.player.distanceTo(entity) <= 24.0) {
            return true;
         }
      }

      return false;
   }

   private Vec3d resolve4() {
      double doubleValue8 = CLIENT.player.getX();
      double doubleValue9 = CLIENT.player.getZ();
      double doubleValue10 = 0.0;
      double doubleValue11 = 0.0;

      for (PlayerEntity playerEntity2 : CLIENT.world.getPlayers()) {
         if (playerEntity2 != CLIENT.player && !playerEntity2.isDead()) {
            double doubleValue12 = doubleValue8 - playerEntity2.getX();
            double doubleValue13 = doubleValue9 - playerEntity2.getZ();
            double doubleValue14 = Math.max(1.0, Math.hypot(doubleValue12, doubleValue13));
            double doubleValue15 = 1.0 / (doubleValue14 * doubleValue14);
            doubleValue10 += doubleValue12 / doubleValue14 * doubleValue15;
            doubleValue11 += doubleValue13 / doubleValue14 * doubleValue15;
         }
      }

      for (Entity entity2 : CLIENT.world.getEntities()) {
         if (entity2 instanceof WardenEntity) {
            double doubleValue16 = doubleValue8 - entity2.getX();
            double doubleValue17 = doubleValue9 - entity2.getZ();
            double doubleValue18 = Math.max(1.0, Math.hypot(doubleValue16, doubleValue17));
            double doubleValue19 = 1.5 / (doubleValue18 * doubleValue18);
            doubleValue10 += doubleValue16 / doubleValue18 * doubleValue19;
            doubleValue11 += doubleValue17 / doubleValue18 * doubleValue19;
         }
      }

      double doubleValue20 = Math.hypot(doubleValue10, doubleValue11);
      if (doubleValue20 < 1.0E-6) {
         double doubleValue21 = Math.random() * Math.PI * 2.0;
         return new Vec3d(Math.cos(doubleValue21), 0.0, Math.sin(doubleValue21));
      } else {
         return new Vec3d(doubleValue10 / doubleValue20, 0.0, doubleValue11 / doubleValue20);
      }
   }

   private double measure(double d, double e) {
      double doubleValue22 = 0.0;

      for (PlayerEntity playerEntity3 : CLIENT.world.getPlayers()) {
         if (playerEntity3 != CLIENT.player && !playerEntity3.isDead()) {
            double doubleValue23 = Math.hypot(playerEntity3.getX() - d, playerEntity3.getZ() - e);
            doubleValue22 += 12.0 / (doubleValue23 + 2.0);
            if (doubleValue23 < 10.0) {
               doubleValue22 += (10.0 - doubleValue23) * 2.0;
            }
         }
      }

      for (Entity entity3 : CLIENT.world.getEntities()) {
         if (entity3 instanceof WardenEntity) {
            double doubleValue24 = Math.hypot(entity3.getX() - d, entity3.getZ() - e);
            doubleValue22 += 18.0 / (doubleValue24 + 2.0);
            if (doubleValue24 < 14.0) {
               doubleValue22 += (14.0 - doubleValue24) * 3.0;
            }
         }
      }

      return doubleValue22;
   }

   private boolean check9(double d, double e, double f, double g) {
      byte byteValue = 6;

      for (int intValue7 = 1; intValue7 <= byteValue; intValue7++) {
         double doubleValue25 = (double)intValue7 / byteValue;
         double doubleValue26 = d + (f - d) * doubleValue25;
         double doubleValue27 = e + (g - e) * doubleValue25;

         for (PlayerEntity playerEntity4 : CLIENT.world.getPlayers()) {
            if (playerEntity4 != CLIENT.player && !playerEntity4.isDead() && Math.hypot(playerEntity4.getX() - doubleValue26, playerEntity4.getZ() - doubleValue27) < 6.0) {
               return false;
            }
         }
      }

      return true;
   }

   private int[] resolve5() {
      Vec3d vec3d2 = this.resolve4();
      double doubleValue28 = Math.atan2(vec3d2.z, vec3d2.x);
      double doubleValue29 = CLIENT.player.getX();
      double doubleValue30 = CLIENT.player.getZ();
      double doubleValue31 = Double.MAX_VALUE;
      int[] intValues2 = null;
      int[] intValues3 = null;
      double doubleValue32 = Double.MAX_VALUE;

      for (int intValue8 = 0; intValue8 < 32; intValue8++) {
         double doubleValue33 = Math.toRadians(15.0 + Math.random() * 65.0);
         double doubleValue34 = Math.random() < 0.8 ? doubleValue28 + (Math.random() * 2.0 - 1.0) * doubleValue33 : Math.random() * Math.PI * 2.0;
         double doubleValue35 = 22.0 + Math.random() * 26.0;
         double doubleValue36 = doubleValue29 + doubleValue35 * Math.cos(doubleValue34);
         double doubleValue37 = doubleValue30 + doubleValue35 * Math.sin(doubleValue34);
         if (this.check3(new Vec3d(doubleValue36, CLIENT.player.getY(), doubleValue37))) {
            double doubleValue38 = this.measure(doubleValue36, doubleValue37);
            if (doubleValue38 < doubleValue32) {
               doubleValue32 = doubleValue38;
               intValues3 = new int[]{(int)doubleValue36, (int)doubleValue37};
            }

            if (this.check9(doubleValue29, doubleValue30, doubleValue36, doubleValue37) && doubleValue38 < doubleValue31) {
               doubleValue31 = doubleValue38;
               intValues2 = new int[]{(int)doubleValue36, (int)doubleValue37};
            }
         }
      }

      if (intValues2 != null) {
         return intValues2;
      } else if (intValues3 != null) {
         return intValues3;
      } else {
         double doubleValue39 = doubleValue29 + vec3d2.x * 26.0;
         double doubleValue40 = doubleValue30 + vec3d2.z * 26.0;
         return this.check3(new Vec3d(doubleValue39, CLIENT.player.getY(), doubleValue40)) ? new int[]{(int)doubleValue39, (int)doubleValue40} : null;
      }
   }

   private void invoke16() {
      if (this.othoditPosleLuta.isEnabled() && this.check2() && this.check8()) {
         int[] intValues4 = this.resolve5();
         if (intValues4 != null) {
            this.ints = intValues4;
            this.iBaritone.getPathingBehavior().cancelEverything();
            this.iBaritone.getCustomGoalProcess().setGoalAndPath(new GoalXZ(intValues4[0], intValues4[1]));
            this.wardenFarmState2 = WardenFarm.WardenFarmState2.RETREATING;
            this.dualTimer3.invoke();
            this.dualTimer5.invoke();
         }
      }
   }

   private void invoke17() {
      if (this.ints == null) {
         this.wardenFarmState2 = WardenFarm.WardenFarmState2.SEARCHING;
         this.dualTimer6.invoke();
      } else {
         boolean flag14 = Math.hypot(CLIENT.player.getX() - this.ints[0], CLIENT.player.getZ() - this.ints[1]) <= 3.0;
         if (!flag14 && this.check8() && !this.dualTimer3.check5(15000L)) {
            if (!this.iBaritone.getCustomGoalProcess().isActive() || this.dualTimer5.check5(2500L)) {
               this.iBaritone.getCustomGoalProcess().setGoalAndPath(new GoalXZ(this.ints[0], this.ints[1]));
               this.dualTimer5.invoke();
            }
         } else {
            this.iBaritone.getPathingBehavior().cancelEverything();
            this.ints = null;
            this.wardenFarmState2 = WardenFarm.WardenFarmState2.SEARCHING;
            this.dualTimer6.invoke();
         }
      }
   }

   private boolean check10() {
      if (this.blockPos5 == null) {
         return false;
      } else if (this.podbiratLutPosleSmerti.isEnabled() && System.currentTimeMillis() <= this.timestamp3) {
         return true;
      } else {
         this.blockPos5 = null;
         return false;
      }
   }

   private void invoke18() {
      if (!this.check10()) {
         this.wardenFarmState2 = WardenFarm.WardenFarmState2.SEARCHING;
         this.dualTimer6.invoke();
      } else {
         double doubleValue41 = CLIENT.player.getPos().distanceTo(Vec3d.ofCenter(this.blockPos5));
         if (doubleValue41 <= 6.0) {
            this.iBaritone.getPathingBehavior().cancelEverything();
            this.wardenFarmState2 = WardenFarm.WardenFarmState2.COLLECTING_DEATH_LOOT;
            this.dualTimer.invoke();
            this.dualTimer3.invoke();
            this.dualTimer5.invoke();
         } else {
            if (!this.iBaritone.getCustomGoalProcess().isActive() || this.dualTimer5.check5(2500L)) {
               this.iBaritone.getCustomGoalProcess().setGoalAndPath(new GoalNear(this.blockPos5, 2));
               this.dualTimer5.invoke();
            }

            if (this.dualTimer3.check5(40000L)) {
               this.invoke20();
            }
         }
      }
   }

   private void invoke19() {
      if (!this.check10()) {
         this.invoke20();
      } else {
         ItemEntity itemEntity = null;
         double doubleValue42 = Double.MAX_VALUE;

         for (Entity entity4 : CLIENT.world.getEntities()) {
            if (entity4 instanceof ItemEntity itemEntity2 && itemEntity2.isAlive() && !(itemEntity2.getPos().distanceTo(Vec3d.ofCenter(this.blockPos5)) > 16.0)) {
               double doubleValue43 = CLIENT.player.getPos().distanceTo(itemEntity2.getPos());
               if (doubleValue43 < doubleValue42) {
                  doubleValue42 = doubleValue43;
                  itemEntity = itemEntity2;
               }
            }
         }

         if (itemEntity == null) {
            if (this.dualTimer.check5(2000L)) {
               this.invoke20();
            }
         } else {
            this.dualTimer.invoke();
            if (this.dualTimer3.check5(90000L)) {
               this.invoke20();
            } else {
               if (!this.iBaritone.getCustomGoalProcess().isActive() || this.dualTimer5.check5(1500L)) {
                  this.iBaritone.getCustomGoalProcess().setGoalAndPath(new GoalNear(itemEntity.getBlockPos(), 1));
                  this.dualTimer5.invoke();
               }
            }
         }
      }
   }

   private void invoke20() {
      this.blockPos5 = null;
      this.timestamp3 = 0L;
      this.iBaritone.getPathingBehavior().cancelEverything();
      this.wardenFarmState2 = WardenFarm.WardenFarmState2.SEARCHING;
      this.dualTimer6.invoke();
      if (this.skladyvatDrop.isEnabled() && this.check33()) {
         this.invoke42();
      }
   }

   private void invoke21() {
      ServerStatsParser.INSTANCE.invoke2();
      if (ServerStatsParser.INSTANCE.getNA2().equals(this.bazovayaAnarhiya.getValue())) {
         if (this.dualTimer.check5(100L)) {
            this.wardenFarmState2 = WardenFarm.WardenFarmState2.GOING_TO_STASH;
            this.dualTimer.invoke();
            this.dualTimer3.invoke();
         }
      } else {
         this.dualTimer.invoke();
         if (!this.check7() && this.dualTimer3.check5(8000L)) {
            CLIENT.player.networkHandler.sendChatCommand("an" + this.bazovayaAnarhiya.getValue());
            this.dualTimer3.invoke();
         }
      }
   }

   private void invoke22() {
      if (this.kudaSkladyvat.is("В клан")) {
         if (this.dualTimer.check5(500L)) {
            this.wardenFarmState2 = WardenFarm.WardenFarmState2.OPENING_STASH;
            this.dualTimer.invoke();
         }
      } else {
         if (this.dualTimer.check5(1000L)) {
            this.invoke41();
            this.dualTimer.invoke();
         }

         if (this.blockPos3 != null && this.check20(this.blockPos3)) {
            double doubleValue44 = CLIENT.player.getPos().distanceTo(Vec3d.ofCenter(this.blockPos3));
            if (doubleValue44 <= 4.0 && this.check16(this.blockPos3)) {
               this.iBaritone.getPathingBehavior().cancelEverything();
               this.wardenFarmState2 = WardenFarm.WardenFarmState2.ROTATING_STASH;
               this.dualTimer.invoke();
            } else {
               if (!this.iBaritone.getCustomGoalProcess().isActive() || this.dualTimer5.check5(2500L)) {
                  this.iBaritone.getCustomGoalProcess().setGoalAndPath(new GoalNear(this.blockPos3, 1));
                  this.dualTimer5.invoke();
               }

               if (this.dualTimer3.check5(15000L)) {
                  this.iBaritone.getPathingBehavior().cancelEverything();
                  this.invoke45();
               }
            }
         } else {
            if (this.dualTimer3.check5(15000L)) {
               this.invoke45();
            }
         }
      }
   }

   private void invoke23() {
      if (this.blockPos3 == null) {
         this.wardenFarmState2 = WardenFarm.WardenFarmState2.GOING_TO_STASH;
         this.dualTimer.invoke();
         this.dualTimer3.invoke();
      } else if (!this.check17(this.blockPos3)) {
         this.wardenFarmState2 = WardenFarm.WardenFarmState2.GOING_TO_STASH;
         this.dualTimer.invoke();
         this.dualTimer3.invoke();
      } else {
         Rotation rotation3 = this.resolve12(this.resolve9(this.blockPos3));
         this.SpacerSetting.invoke3(this.resolve11(rotation3, this.measure2(rotation3)), 35.0F, 35.0F, 35.0F, 35.0F, 20, 1);
         if (this.resolve10(this.blockPos3) != null && this.dualTimer.check5(200L)) {
            this.wardenFarmState2 = WardenFarm.WardenFarmState2.OPENING_STASH_BLOCK;
            this.dualTimer.invoke();
         }
      }
   }

   private void invoke24() {
      int intValue9 = CLIENT.player.getInventory().getSelectedSlot();
      ItemStack itemStack4 = (ItemStack)CLIENT.player.getInventory().getMainStacks().get(intValue9);
      if (itemStack4.getItem() == Items.TRIPWIRE_HOOK || itemStack4.getName().getString().contains("[★]")) {
         for (int intValue10 = 0; intValue10 < 9; intValue10++) {
            ItemStack itemStack5 = (ItemStack)CLIENT.player.getInventory().getMainStacks().get(intValue10);
            if (itemStack5.isEmpty() || itemStack5.getItem() != Items.TRIPWIRE_HOOK && !itemStack5.getName().getString().contains("[★]")) {
               CLIENT.player.getInventory().setSelectedSlot(intValue10);
               this.dualTimer.invoke();
               break;
            }
         }
      }

      if (!this.check17(this.blockPos3)) {
         this.wardenFarmState2 = WardenFarm.WardenFarmState2.GOING_TO_STASH;
         this.dualTimer.invoke();
         this.dualTimer3.invoke();
      } else {
         Rotation rotation4 = this.resolve12(this.resolve9(this.blockPos3));
         this.SpacerSetting.invoke3(this.resolve11(rotation4, 0.6F), 18.0F, 18.0F, 20.0F, 20.0F, 20, 1);
         BlockHitResult blockHitResult2 = this.resolve10(this.blockPos3);
         if (blockHitResult2 == null) {
            if (this.dualTimer.check5(1200L)) {
               this.wardenFarmState2 = WardenFarm.WardenFarmState2.ROTATING_STASH;
               this.dualTimer.invoke();
            }
         } else {
            if (this.dualTimer.check5(150L)) {
               CLIENT.player.swingHand(Hand.MAIN_HAND);
               CLIENT.interactionManager.interactBlock(CLIENT.player, Hand.MAIN_HAND, blockHitResult2);
               RotationController.rotationControllerState2 = RotationController.RotationControllerState2.IDLE;
               this.wardenFarmState2 = WardenFarm.WardenFarmState2.WAITING_FOR_GUI_STASH;
               this.dualTimer.invoke();
            }
         }
      }
   }

   private void invoke25() {
      this.iBaritone.getPathingBehavior().cancelEverything();
      if (this.dualTimer.check5(4000L)) {
         if (this.intValue2 < 3) {
            this.intValue2++;
            this.wardenFarmState2 = WardenFarm.WardenFarmState2.GOING_TO_STASH;
            this.dualTimer.invoke();
            this.dualTimer3.invoke();
         } else {
            this.invoke45();
         }
      }
   }

   private void invoke26() {
      this.iBaritone.getPathingBehavior().cancelEverything();
      if (this.dualTimer.check5(1500L) && CLIENT.currentScreen == null) {
         CLIENT.player.networkHandler.sendChatCommand("clan storage");
         this.wardenFarmState2 = WardenFarm.WardenFarmState2.WAITING_FOR_GUI_STASH;
         this.dualTimer.invoke();
      }
   }

   private void invoke27() {
      this.iBaritone.getPathingBehavior().cancelEverything();
      if (this.dualTimer.check5(1000L)) {
         this.blockPos = null;
         this.wardenFarmState2 = WardenFarm.WardenFarmState2.SEARCHING;
         this.dualTimer6.invoke();
      }
   }

   private boolean check11() {
      long longValue7 = Long.MAX_VALUE;

      for (BlockPos blockPos3 : this.items) {
         long longValue8 = this.compute(blockPos3) - System.currentTimeMillis();
         if (longValue8 < longValue7) {
            longValue7 = longValue8;
         }
      }

      if (longValue7 <= this.compute7() || longValue7 == Long.MAX_VALUE) {
         return false;
      } else if (this.check7()) {
         return false;
      } else {
         ServerStatsParser.INSTANCE.invoke2();
         String text10 = ServerStatsParser.INSTANCE.getNA2();
         if (!"N/A".equals(text10)) {
            this.nA = text10;
         }

         this.iBaritone.getPathingBehavior().cancelEverything();
         this.blockPos = null;
         if (this.svapatAnarhii.isEnabled()) {
            String text11 = this.resolve2();
            this.nA = text11;
            this.invoke9((Runnable)(() -> {
               CLIENT.player.networkHandler.sendChatCommand("hub");
               this.flag = true;
               this.timestamp = System.currentTimeMillis() + 1700L;
            }));
         } else {
            long longValue9 = longValue7 - 25000L;
            this.invoke9((Runnable)(() -> {
               CLIENT.player.networkHandler.sendChatCommand("hub");
               this.flag = true;
               this.timestamp = System.currentTimeMillis() + longValue9;
               this.wardenFarmState2 = WardenFarm.WardenFarmState2.SEARCHING;
               this.dualTimer6.invoke();
            }));
         }

         return true;
      }
   }

   private void invoke28() {
      if (!this.stelsRezhimVarden.isEnabled()) {
         this.invoke29();
      } else {
         boolean flag15 = false;
         BlockPos blockPos4 = CLIENT.player.getBlockPos();

         for (BlockPos blockPos5 : BlockPos.iterate(blockPos4.add(-5, -5, -5), blockPos4.add(5, 5, 5))) {
            Block block = CLIENT.world.getBlockState(blockPos5).getBlock();
            if (block == Blocks.SCULK || block == Blocks.SCULK_SENSOR || block == Blocks.SCULK_SHRIEKER || block == Blocks.SCULK_CATALYST) {
               flag15 = true;
               break;
            }
         }

         long longValue10 = System.currentTimeMillis();
         if (flag15) {
            this.timestamp4 = longValue10;
            this.flag4 = true;
         }

         if (this.flag4) {
            if (!flag15 && longValue10 - this.timestamp4 >= 1200L) {
               this.invoke29();
            } else {
               if (!CLIENT.player.isSneaking()) {
                  CLIENT.player.setSneaking(true);
               }

               if ((Boolean)BaritoneAPI.getSettings().allowSprint.value) {
                  BaritoneAPI.getSettings().allowSprint.value = false;
               }
            }
         }
      }
   }

   private void invoke29() {
      if (this.flag4) {
         this.flag4 = false;
         this.timestamp4 = 0L;
         if (CLIENT.player != null) {
            CLIENT.player.setSneaking(false);
         }

         BaritoneAPI.getSettings().allowSprint.value = this.flag2;
      }
   }

   private long compute2(String string, boolean bl) {
      Matcher matcher2 = bl ? PATTERN.matcher(string) : PATTERN_2.matcher(string);
      if (matcher2.find()) {
         try {
            if (bl) {
               return (Integer.parseInt(matcher2.group(1)) * 60L + Integer.parseInt(matcher2.group(2))) * 1000L;
            }

            int intValue11 = Integer.parseInt(matcher2.group(1));
            int intValue12 = Integer.parseInt(matcher2.group(2));
            return matcher2.group(3) != null ? (intValue11 * 3600L + intValue12 * 60L + Integer.parseInt(matcher2.group(3))) * 1000L : (intValue11 * 60L + intValue12) * 1000L;
         } catch (NumberFormatException numberFormatException2) {
         }
      }

      Matcher matcher3 = PATTERN_3.matcher(string);
      if (matcher3.find()) {
         try {
            return Integer.parseInt(matcher3.group(1)) * 1000L;
         } catch (NumberFormatException numberFormatException3) {
         }
      }

      return -1L;
   }

   private void invoke30() {
      boolean flag16 = this.rezhim.is("Варден");
      Block block2 = flag16 ? Blocks.CHEST : Blocks.BARREL;

      for (Entity entity5 : CLIENT.world.getEntities()) {
         if (entity5 instanceof ArmorStandEntity) {
            long longValue11 = this.compute2(entity5.getName().getString(), flag16);
            if (longValue11 >= 0L) {
               BlockPos blockPos6 = new BlockPos(entity5.getBlockX(), entity5.getBlockY() - 1, entity5.getBlockZ());
               if (CLIENT.world.getBlockState(blockPos6).getBlock() == block2) {
                  this.valuesByKey4.put(blockPos6, System.currentTimeMillis() + longValue11);
               } else {
                  blockPos6 = blockPos6.down();
                  if (CLIENT.world.getBlockState(blockPos6).getBlock() == block2) {
                     this.valuesByKey4.put(blockPos6, System.currentTimeMillis() + longValue11);
                  }
               }
            }
         }
      }
   }

   private boolean check12(BlockPos blockPos) {
      if (CLIENT.world != null && blockPos != null) {
         boolean flag17 = this.rezhim.is("Варден");

         for (Entity entity6 : CLIENT.world.getEntities()) {
            if (entity6 instanceof ArmorStandEntity && this.compute2(entity6.getName().getString(), flag17) > 250L) {
               BlockPos blockPos7 = new BlockPos(entity6.getBlockX(), entity6.getBlockY() - 1, entity6.getBlockZ());
               if (blockPos7.equals(blockPos) || blockPos7.down().equals(blockPos)) {
                  return true;
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private boolean check13(BlockPos blockPos) {
      Long longValue12 = this.valuesByKey5.get(blockPos);
      return longValue12 != null && longValue12 > System.currentTimeMillis() ? true : this.compute6(blockPos) > this.compute4(blockPos);
   }

   private long compute3(BlockPos blockPos) {
      double doubleValue45 = CLIENT.player.getPos().distanceTo(Vec3d.ofCenter(blockPos));
      return (long)(doubleValue45 / 3.0 * 1000.0);
   }

   private long compute4(BlockPos blockPos) {
      return Math.max(20000L, this.compute3(blockPos) + 5000L);
   }

   private long compute5(BlockPos blockPos) {
      return Math.max(this.compute3(blockPos), Math.max(0L, this.compute6(blockPos)));
   }

   private void invoke31() {
      this.items.clear();
      BlockPos blockPos8 = CLIENT.player.getBlockPos();
      ChunkPos chunkPos = new ChunkPos(blockPos8);
      byte byteValue2 = 10;
      Block block3 = this.rezhim.is("Варден") ? Blocks.CHEST : Blocks.BARREL;

      for (int intValue13 = -byteValue2; intValue13 <= byteValue2; intValue13++) {
         for (int intValue14 = -byteValue2; intValue14 <= byteValue2; intValue14++) {
            WorldChunk worldChunk = CLIENT.world.getChunk(chunkPos.x + intValue13, chunkPos.z + intValue14);
            if (worldChunk != null) {
               for (BlockPos blockPos9 : worldChunk.getBlockEntities().keySet()) {
                  if (worldChunk.getBlockState(blockPos9).getBlock() == block3 && this.check3(Vec3d.ofCenter(blockPos9))) {
                     this.items.add(blockPos9);
                  }
               }
            }
         }
      }
   }

   private boolean check14() {
      return this.skladyvatDrop.isEnabled() && this.check33();
   }

   private void invoke32(BlockPos blockPos) {
      if (blockPos != null) {
         this.values.add(blockPos);
      }

      this.iBaritone.getPathingBehavior().cancelEverything();
      this.blockPos = null;
      this.blockPos6 = null;
      this.doubleValue = -1.0;
      this.timestamp10 = 0L;
      this.dualTimer3.invoke();
      this.dualTimer5.invoke();
      boolean flag18 = this.skladyvatDrop.isEnabled() && this.check33();
      if (flag18 && this.check15()) {
         BlockPos blockPos10 = this.resolve6();
         if (blockPos10 != null) {
            this.blockPos = blockPos10;
            this.doubleValue = CLIENT.player.getPos().distanceTo(Vec3d.ofCenter(blockPos10));
            this.wardenFarmState2 = WardenFarm.WardenFarmState2.GOING_TO_CHEST;
            this.timestamp10 = 0L;
            this.flag12 = true;
            this.dualTimer3.invoke();
            this.dualTimer6.invoke();
            return;
         }
      }

      if (!flag18) {
         this.valuesByKey3.clear();
         this.wardenFarmState2 = WardenFarm.WardenFarmState2.SEARCHING;
         this.dualTimer6.invoke();
         this.invoke16();
      } else {
         this.invoke42();
         if (this.wardenFarmState2 == WardenFarm.WardenFarmState2.SEARCHING) {
            this.invoke16();
         }
      }
   }

   private boolean check15() {
      for (int intValue15 = 0; intValue15 < 36; intValue15++) {
         if (CLIENT.player.getInventory().getStack(intValue15).isEmpty()) {
            return true;
         }
      }

      return false;
   }

   private BlockPos resolve6() {
      this.invoke31();
      long longValue13 = System.currentTimeMillis();
      return this.items
         .stream()
         .filter(blockPos -> !this.values.contains(blockPos))
         .filter(blockPos -> {
            Long longValue14 = this.valuesByKey5.get(blockPos);
            return longValue14 == null || longValue14 <= longValue13;
         })
         .filter(blockPos -> CLIENT.player.getPos().distanceTo(Vec3d.ofCenter(blockPos)) <= 14.0)
         .filter(blockPos -> this.compute6(blockPos) <= 15000L)
         .min(
            Comparator.<BlockPos>comparingLong(blockPos -> Math.max(0L, this.compute6(blockPos)))
               .thenComparingDouble(blockPos -> CLIENT.player.getPos().distanceTo(Vec3d.ofCenter(blockPos)))
         )
         .orElse(null);
   }

   private long compute6(BlockPos blockPos) {
      return this.compute(blockPos) - System.currentTimeMillis();
   }

   private long compute7() {
      return (long)(this.zhdatSundukDoSek.getValue() * 1000.0F);
   }

   private BlockPos resolve7() {
      return this.items
         .stream()
         .filter(blockPos -> !this.values.contains(blockPos))
         .filter(blockPos -> !this.check13(blockPos))
         .min(
            Comparator.<BlockPos>comparingLong(this::compute5)
               .thenComparingDouble(blockPos -> CLIENT.player.getPos().distanceTo(Vec3d.ofCenter(blockPos)))
         )
         .orElse(null);
   }

   private Vec3d resolve8(BlockPos blockPos) {
      Vec3d vec3d3 = CLIENT.player.getEyePos();
      double doubleValue46 = blockPos.getX();
      double doubleValue47 = blockPos.getY();
      double doubleValue48 = blockPos.getZ();
      Vec3d[] vec3ds = new Vec3d[]{
         new Vec3d(doubleValue46 + 0.5, doubleValue47 + 0.5, doubleValue48 + 0.5),
         new Vec3d(doubleValue46 + 0.5, doubleValue47 + 0.9, doubleValue48 + 0.5),
         new Vec3d(doubleValue46 + 0.5, doubleValue47 + 0.5, doubleValue48 + 0.05),
         new Vec3d(doubleValue46 + 0.5, doubleValue47 + 0.5, doubleValue48 + 0.95),
         new Vec3d(doubleValue46 + 0.05, doubleValue47 + 0.5, doubleValue48 + 0.5),
         new Vec3d(doubleValue46 + 0.95, doubleValue47 + 0.5, doubleValue48 + 0.5)
      };

      for (Vec3d vec3d4 : vec3ds) {
         BlockHitResult blockHitResult3 = CLIENT.world.raycast(new RaycastContext(vec3d3, vec3d4, ShapeType.OUTLINE, FluidHandling.NONE, CLIENT.player));
         if (blockHitResult3.getType() == Type.MISS || blockHitResult3.getBlockPos().equals(blockPos)) {
            return vec3d4;
         }
      }

      return null;
   }

   private boolean check16(BlockPos blockPos) {
      return this.resolve8(blockPos) != null;
   }

   private Vec3d resolve9(BlockPos blockPos) {
      Vec3d vec3d5 = this.resolve8(blockPos);
      return vec3d5 != null ? vec3d5 : Vec3d.ofCenter(blockPos);
   }

   private boolean check17(BlockPos blockPos) {
      return blockPos != null && CLIENT.player.getPos().distanceTo(Vec3d.ofCenter(blockPos)) <= 4.0 && this.check16(blockPos);
   }

   private BlockHitResult resolve10(BlockPos blockPos) {
      Vec3d vec3d6 = CLIENT.player.getEyePos();
      Vec3d vec3d7 = vec3d6.add(CLIENT.player.getRotationVec(1.0F).multiply(4.5));
      BlockHitResult blockHitResult4 = CLIENT.world.raycast(new RaycastContext(vec3d6, vec3d7, ShapeType.OUTLINE, FluidHandling.NONE, CLIENT.player));
      return blockHitResult4.getType() == Type.BLOCK && blockHitResult4.getBlockPos().equals(blockPos) ? blockHitResult4 : null;
   }

   private float measure2(Rotation rotation5) {
      float floatValue = new Rotation(CLIENT.player).measure(rotation5);
      return Math.min(2.0F, 0.45F + floatValue * 0.1F);
   }

   private void invoke33() {
      ArrayList arrayList = new ArrayList((Collection)BaritoneAPI.getSettings().blocksToAvoid.value);
      this.items2 = new ArrayList<>(arrayList);

      for (Block block4 : Registries.BLOCK) {
         if ((
               block4 instanceof AbstractCandleBlock
                  || block4 instanceof AbstractSkullBlock
                  || block4 instanceof FlowerPotBlock
                  || block4 instanceof LanternBlock
                  || block4 instanceof ChainBlock
                  || block4 instanceof SeaPickleBlock
                  || block4 instanceof AmethystClusterBlock
                  || block4 instanceof PointedDripstoneBlock
            )
            && !arrayList.contains(block4)) {
            arrayList.add(block4);
         }
      }

      BaritoneAPI.getSettings().blocksToAvoid.value = arrayList;
   }

   private void invoke34() {
      if (this.items2 != null) {
         BaritoneAPI.getSettings().blocksToAvoid.value = this.items2;
         this.items2 = null;
      }
   }

   private void invoke35() {
      if (CLIENT.player != null
         && this.iBaritone != null
         && this.iBaritone.getCustomGoalProcess().isActive()
         && CLIENT.currentScreen == null
         && this.wardenFarmState == WardenFarm.WardenFarmState.NONE) {
         long longValue15 = System.currentTimeMillis();
         Vec3d vec3d8 = CLIENT.player.getPos();
         if (this.vec3d != null && !(vec3d8.squaredDistanceTo(this.vec3d) > 0.36)) {
            if (longValue15 - this.timestamp7 > 3500L) {
               this.iBaritone.getPathingBehavior().cancelEverything();
               this.vec3d = null;
            }
         } else {
            this.vec3d = vec3d8;
            this.timestamp7 = longValue15;
         }
      } else {
         this.vec3d = null;
      }
   }

   private Rotation resolve11(Rotation rotation6, float f) {
      double doubleValue49 = System.currentTimeMillis() / 1000.0;
      float floatValue2 = (float)((Math.sin(doubleValue49 * 7.3 + this.FoundryShaderSetting) * 0.62 + Math.sin(doubleValue49 * 13.7 + this.doubleValue2) * 0.38) * f);
      float floatValue3 = (float)((Math.sin(doubleValue49 * 9.1 + this.doubleValue2) * 0.55 + Math.sin(doubleValue49 * 15.9 + this.FoundryShaderSetting) * 0.45) * f * 0.6);
      float floatValue4 = Math.max(-90.0F, Math.min(90.0F, rotation6.floatValue2 + floatValue3));
      return new Rotation(rotation6.floatValue + floatValue2, floatValue4);
   }

   private Rotation resolve12(Vec3d vec3d) {
      if (CLIENT.player == null) {
         return new Rotation(0.0F, 0.0F);
      } else {
         Vec3d vec3d9 = CLIENT.player.getEyePos();
         double doubleValue50 = vec3d.x - vec3d9.x;
         double doubleValue51 = vec3d.y - vec3d9.y;
         double doubleValue52 = vec3d.z - vec3d9.z;
         float floatValue5 = (float)Math.toDegrees(Math.atan2(doubleValue52, doubleValue50)) - 90.0F;
         float floatValue6 = (float)(-Math.toDegrees(Math.atan2(doubleValue51, Math.sqrt(doubleValue50 * doubleValue50 + doubleValue52 * doubleValue52))));
         return new Rotation(floatValue5, floatValue6);
      }
   }

   private void invoke36(GenericContainerScreenHandler genericContainerScreenHandler) {
      if (CLIENT.player != null && CLIENT.interactionManager != null) {
         this.invoke39(genericContainerScreenHandler);
         if (!this.queue.isEmpty()) {
            if (this.dualTimer2.check5(50L)) {
               this.queue.poll().run();
               this.dualTimer2.invoke();
            }
         } else {
            this.invoke37();
            boolean flag19 = false;
            int intValue16 = genericContainerScreenHandler.slots.size() - 36;

            for (int intValue17 = 0; intValue17 < intValue16; intValue17++) {
               Slot slot = (Slot)genericContainerScreenHandler.slots.get(intValue17);
               if (slot.hasStack()) {
                  ItemStack itemStack6 = slot.getStack();
                  if (this.check34(itemStack6) && this.dualTimer2.check5(50L)) {
                     ItemStack itemStack7 = itemStack6.copy();
                     String text12 = this.resolve19(itemStack7);
                     this.valuesByKey3.put(text12, this.valuesByKey3.getOrDefault(text12, 0) + itemStack7.getCount());
                     CLIENT.interactionManager.clickSlot(genericContainerScreenHandler.syncId, intValue17, 0, SlotActionType.QUICK_MOVE, CLIENT.player);
                     this.dualTimer2.invoke();
                     flag19 = true;
                     return;
                  }

                  if (this.check27(itemStack6) && this.dualTimer2.check5(50L)) {
                     int intValue18 = this.compute8(this.wardenFarmState5) - this.intValue4;
                     this.invoke38(genericContainerScreenHandler, intValue17, intValue18);
                     flag19 = true;
                     return;
                  }
               }
            }

            if (!flag19 && intValue16 > 0) {
               boolean flag20 = this.check18(genericContainerScreenHandler, intValue16);
               if (!flag20) {
                  CLIENT.player.closeHandledScreen();
                  this.invoke32(this.blockPos);
               }
            }
         }
      }
   }

   private void invoke37() {
      if (this.blockPos != null) {
         if (!this.blockPos.equals(this.blockPos4)) {
            this.blockPos4 = this.blockPos;
            this.intValue4 = 0;
            this.wardenFarmState5 = this.resolve13(this.blockPos);
         }
      }
   }

   private boolean check18(GenericContainerScreenHandler genericContainerScreenHandler, int i) {
      for (int intValue19 = 0; intValue19 < i; intValue19++) {
         ItemStack itemStack8 = ((Slot)genericContainerScreenHandler.slots.get(intValue19)).getStack();
         if (!itemStack8.isEmpty() && (this.check34(itemStack8) || this.check27(itemStack8))) {
            return true;
         }
      }

      return false;
   }

   private void invoke38(GenericContainerScreenHandler genericContainerScreenHandler, int i, int j) {
      if (j > 0) {
         Slot slot2 = (Slot)genericContainerScreenHandler.slots.get(i);
         if (slot2.hasStack()) {
            int intValue20 = slot2.getStack().getCount();
            CLIENT.interactionManager.clickSlot(genericContainerScreenHandler.syncId, i, 0, SlotActionType.QUICK_MOVE, CLIENT.player);
            this.intValue4 = this.intValue4 + Math.min(intValue20, j);
            this.dualTimer2.invoke();
         }
      }
   }

   private void invoke39(GenericContainerScreenHandler genericContainerScreenHandler) {
      if (genericContainerScreenHandler.syncId != this.intValue6) {
         this.intValue6 = genericContainerScreenHandler.syncId;
         this.dualTimer2.invoke();
         this.dualTimer.invoke();
      }
   }

   private WardenFarm.WardenFarmState5 resolve13(BlockPos blockPos) {
      if (this.avtoEdaIInviz.isEnabled() && blockPos != null && CLIENT.world != null) {
         String text13 = this.resolve14(blockPos).toLowerCase(Locale.ROOT);
         if (text13.contains("ресы") || text13.contains("ресурс")) {
            return WardenFarm.WardenFarmState5.NONE;
         } else if (text13.contains("инвиз")) {
            return WardenFarm.WardenFarmState5.INVIS;
         } else {
            return text13.contains("морков") ? WardenFarm.WardenFarmState5.CARROT : WardenFarm.WardenFarmState5.NONE;
         }
      } else {
         return WardenFarm.WardenFarmState5.NONE;
      }
   }

   private String resolve14(BlockPos blockPos) {
      if (blockPos != null && CLIENT.world != null) {
         SignBlockEntity signBlockEntity2 = null;
         double doubleValue53 = Double.MAX_VALUE;
         BlockPos blockPos11 = blockPos.add(-1, -1, -1);
         BlockPos blockPos12 = blockPos.add(1, 1, 1);

         for (BlockPos blockPos13 : BlockPos.iterate(blockPos11, blockPos12)) {
            if (CLIENT.world.getBlockEntity(blockPos13) instanceof SignBlockEntity signBlockEntity3) {
               double doubleValue54 = blockPos13.getSquaredDistance(blockPos);
               if (doubleValue54 < doubleValue53) {
                  doubleValue53 = doubleValue54;
                  signBlockEntity2 = signBlockEntity3;
               }
            }
         }

         return signBlockEntity2 == null ? "" : this.resolve18(signBlockEntity2);
      } else {
         return "";
      }
   }

   private boolean check19(BlockPos blockPos, String[] strings, String... strings2) {
      String text14 = this.resolve14(blockPos).toLowerCase(Locale.ROOT);
      if (text14.isEmpty()) {
         return false;
      } else {
         boolean flag21 = false;

         for (String text15 : strings) {
            if (text14.contains(text15.toLowerCase(Locale.ROOT))) {
               flag21 = true;
               break;
            }
         }

         if (!flag21) {
            return false;
         } else {
            for (String text16 : strings2) {
               if (text14.contains(text16.toLowerCase(Locale.ROOT))) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   private boolean check20(BlockPos blockPos) {
      return blockPos != null && this.check19(blockPos, RESY, "морков", "инвиз");
   }

   private boolean check21() {
      return this.compute11() < 1 && !this.check49();
   }

   private boolean check22() {
      return this.compute12() < 3;
   }

   private boolean check23() {
      return this.zelyaSkorostiBratIPit.isEnabled() && !this.flag10 && this.compute9() < 1;
   }

   private boolean check24(String string) {
      if (!string.isEmpty() && !string.contains("ресы") && !string.contains("ресурс")) {
         for (String text17 : KIT) {
            if (string.contains(text17)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private boolean check25(String string) {
      if (!string.contains("кит") && !string.contains("kit") && !string.contains("зель") && !string.contains("припас")) {
         if (!this.check21() || !string.contains("инвиз") && !string.contains("invis")) {
            return !this.check22() || !string.contains("морков") && !string.contains("carrot")
               ? this.check23() && (string.contains("скор") || string.contains("speed") || string.contains("инвиз") || string.contains("invis"))
               : true;
         } else {
            return true;
         }
      } else {
         return this.check21() || this.check22() || this.check23();
      }
   }

   private BlockPos resolve15() {
      if (CLIENT.world != null && CLIENT.player != null) {
         ChunkPos chunkPos2 = new ChunkPos(CLIENT.player.getBlockPos());
         byte byteValue3 = 10;
         BlockPos blockPos14 = null;
         double doubleValue55 = Double.MAX_VALUE;

         for (int intValue21 = -byteValue3; intValue21 <= byteValue3; intValue21++) {
            for (int intValue22 = -byteValue3; intValue22 <= byteValue3; intValue22++) {
               WorldChunk worldChunk2 = CLIENT.world.getChunk(chunkPos2.x + intValue21, chunkPos2.z + intValue22);
               if (worldChunk2 != null) {
                  for (BlockPos blockPos15 : worldChunk2.getBlockEntities().keySet()) {
                     if (this.check26(blockPos15) && !this.values2.contains(blockPos15)) {
                        String text18 = this.resolve14(blockPos15).toLowerCase(Locale.ROOT);
                        if (this.check24(text18) && this.check25(text18)) {
                           double doubleValue56 = CLIENT.player.getPos().distanceTo(Vec3d.ofCenter(blockPos15));
                           if (doubleValue56 < doubleValue55) {
                              doubleValue55 = doubleValue56;
                              blockPos14 = blockPos15;
                           }
                        }
                     }
                  }
               }
            }
         }

         return blockPos14;
      } else {
         return null;
      }
   }

   private void invoke40(BlockPos blockPos) {
      if (blockPos != null) {
         this.values2.add(blockPos);

         for (BlockPos blockPos16 : new BlockPos[]{blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west()}) {
            if (CLIENT.world.getBlockEntity(blockPos16) instanceof ChestBlockEntity) {
               this.values2.add(blockPos16.toImmutable());
            }
         }
      }
   }

   private void invoke41() {
      if (this.blockPos3 != null && !this.check20(this.blockPos3)) {
         this.blockPos3 = null;
      }

      BlockPos blockPos17 = this.resolve16();
      if (blockPos17 != null) {
         this.blockPos3 = blockPos17;
      }
   }

   private boolean check26(BlockPos blockPos) {
      if (CLIENT.world == null) {
         return false;
      } else {
         BlockEntity blockEntity = CLIENT.world.getBlockEntity(blockPos);
         return blockEntity instanceof ChestBlockEntity || blockEntity instanceof BarrelBlockEntity || blockEntity instanceof ShulkerBoxBlockEntity;
      }
   }

   private BlockPos resolve16() {
      return this.resolve17(RESY, "морков", "инвиз");
   }

   private BlockPos resolve17(String[] strings, String... strings2) {
      if (CLIENT.world != null && CLIENT.player != null) {
         BlockPos blockPos18 = CLIENT.player.getBlockPos();
         ChunkPos chunkPos3 = new ChunkPos(blockPos18);
         byte byteValue4 = 10;
         BlockPos blockPos19 = null;
         double doubleValue57 = Double.MAX_VALUE;

         for (int intValue23 = -byteValue4; intValue23 <= byteValue4; intValue23++) {
            for (int intValue24 = -byteValue4; intValue24 <= byteValue4; intValue24++) {
               WorldChunk worldChunk3 = CLIENT.world.getChunk(chunkPos3.x + intValue23, chunkPos3.z + intValue24);
               if (worldChunk3 != null) {
                  for (BlockPos blockPos20 : worldChunk3.getBlockEntities().keySet()) {
                     if (this.check26(blockPos20) && this.check19(blockPos20, strings, strings2)) {
                        double doubleValue58 = CLIENT.player.getPos().distanceTo(Vec3d.ofCenter(blockPos20));
                        if (doubleValue58 < doubleValue57) {
                           doubleValue57 = doubleValue58;
                           blockPos19 = blockPos20;
                        }
                     }
                  }
               }
            }
         }

         return blockPos19;
      } else {
         return null;
      }
   }

   private String resolve18(SignBlockEntity signBlockEntity) {
      StringBuilder stringBuilder = new StringBuilder();

      for (Text text19 : signBlockEntity.getFrontText().getMessages(false)) {
         stringBuilder.append(text19.getString()).append(' ');
      }

      for (Text text20 : signBlockEntity.getBackText().getMessages(false)) {
         stringBuilder.append(text20.getString()).append(' ');
      }

      return stringBuilder.toString().replaceAll("§.", "").trim();
   }

   private int compute8(WardenFarm.WardenFarmState5 wardenFarmState5) {
      return switch (wardenFarmState5) {
         case INVIS -> 1;
         case CARROT -> 3;
         default -> 0;
      };
   }

   private boolean check27(ItemStack itemStack) {
      if (this.avtoEdaIInviz.isEnabled() && this.wardenFarmState5 != WardenFarm.WardenFarmState5.NONE) {
         if (this.intValue4 >= this.compute8(this.wardenFarmState5)) {
            return false;
         } else {
            return switch (this.wardenFarmState5) {
               case INVIS -> this.check30(itemStack);
               case CARROT -> this.check48(itemStack);
               default -> false;
            };
         }
      } else {
         return false;
      }
   }

   private boolean check28(ItemStack itemStack) {
      if (itemStack.isEmpty()) {
         return false;
      } else {
         String text21 = itemStack.getName().getString().toLowerCase(Locale.ROOT);
         if (!text21.contains("invis") && !text21.contains("невид")) {
            PotionContentsComponent potionContentsComponent = (PotionContentsComponent)itemStack.get(DataComponentTypes.POTION_CONTENTS);
            if (potionContentsComponent == null) {
               return false;
            } else {
               for (StatusEffectInstance statusEffectInstance : potionContentsComponent.getEffects()) {
                  if (statusEffectInstance.getEffectType().equals(StatusEffects.INVISIBILITY)) {
                     return true;
                  }
               }

               return false;
            }
         } else {
            return true;
         }
      }
   }

   private boolean check29(ItemStack itemStack) {
      if (itemStack.isEmpty()) {
         return false;
      } else {
         String text22 = itemStack.getName().getString().toLowerCase(Locale.ROOT);
         if (!text22.contains("скорост") && !text22.contains("speed") && !text22.contains("swift")) {
            PotionContentsComponent potionContentsComponent2 = (PotionContentsComponent)itemStack.get(DataComponentTypes.POTION_CONTENTS);
            if (potionContentsComponent2 == null) {
               return false;
            } else {
               for (StatusEffectInstance statusEffectInstance2 : potionContentsComponent2.getEffects()) {
                  if (statusEffectInstance2.getEffectType().equals(StatusEffects.SPEED)) {
                     return true;
                  }
               }

               return false;
            }
         } else {
            return true;
         }
      }
   }

   private boolean check30(ItemStack itemStack) {
      return !itemStack.isEmpty() && itemStack.isOf(Items.POTION) && this.check28(itemStack) && !this.check34(itemStack);
   }

   private boolean check31(ItemStack itemStack) {
      return !itemStack.isEmpty() && itemStack.isOf(Items.POTION) && this.check29(itemStack) && !this.check34(itemStack);
   }

   private int compute9() {
      int intValue25 = 0;

      for (int intValue26 = 0; intValue26 < 36; intValue26++) {
         ItemStack itemStack9 = CLIENT.player.getInventory().getStack(intValue26);
         if (this.check31(itemStack9)) {
            intValue25 += itemStack9.getCount();
         }
      }

      return intValue25;
   }

   private void invoke42() {
      if (this.skladyvatDrop.isEnabled() && this.check33()) {
         ServerStatsParser.INSTANCE.invoke2();
         boolean flag22 = this.svapatAnarhii.isEnabled() && !ServerStatsParser.INSTANCE.getNA2().equals(this.bazovayaAnarhiya.getValue());
         if (flag22 && this.check7()) {
            this.runnable = this::invoke43;
            this.invoke11();
         } else {
            this.invoke43();
         }
      } else {
         this.valuesByKey3.clear();
         if (this.flag11) {
            this.flag11 = false;
            this.invoke52();
         }
      }
   }

   private void invoke43() {
      ServerStatsParser.INSTANCE.invoke2();
      this.blockPos3 = null;
      this.intValue2 = 0;
      this.intValue3 = -1;
      this.invoke41();
      if (this.svapatAnarhii.isEnabled() && !ServerStatsParser.INSTANCE.getNA2().equals(this.bazovayaAnarhiya.getValue())) {
         if (this.check7()) {
            this.flag3 = true;
            this.wardenFarmState2 = WardenFarm.WardenFarmState2.SEARCHING;
            this.dualTimer6.invoke();
            return;
         }

         String text23 = ServerStatsParser.INSTANCE.getNA2();
         if (!"N/A".equals(text23)) {
            this.nA = text23;
         }

         CLIENT.player.networkHandler.sendChatCommand("an" + this.bazovayaAnarhiya.getValue());
         this.wardenFarmState2 = WardenFarm.WardenFarmState2.SWAPPING_TO_SAVE_ANARCHY;
         this.dualTimer.invoke();
         this.dualTimer3.invoke();
      } else {
         this.wardenFarmState2 = WardenFarm.WardenFarmState2.GOING_TO_STASH;
         this.dualTimer.invoke();
         this.dualTimer3.invoke();
      }
   }

   private void invoke44(GenericContainerScreenHandler genericContainerScreenHandler) {
      if (CLIENT.player != null && CLIENT.interactionManager != null) {
         int intValue27 = genericContainerScreenHandler.slots.size() - 36;
         int intValue28 = this.compute10(genericContainerScreenHandler, intValue27);
         if (this.intValue3 >= 0 && intValue28 >= this.intValue3) {
            if (this.dualTimer9.check5(6000L)) {
               this.invoke45();
               return;
            }
         } else {
            this.intValue3 = intValue28;
            this.dualTimer9.invoke();
         }

         if (!this.queue.isEmpty()) {
            if (this.dualTimer2.check5(50L)) {
               this.queue.poll().run();
               this.dualTimer2.invoke();
            }
         } else {
            boolean flag23 = false;

            for (int intValue29 = intValue27; intValue29 < genericContainerScreenHandler.slots.size(); intValue29++) {
               Slot slot3 = (Slot)genericContainerScreenHandler.slots.get(intValue29);
               if (slot3.hasStack()) {
                  ItemStack itemStack10 = slot3.getStack();
                  String text24 = this.resolve19(itemStack10);
                  if (this.valuesByKey3.getOrDefault(text24, 0) > 0 || this.check34(itemStack10)) {
                     flag23 = true;
                     int intValue30 = intValue29;
                     this.queue
                        .add(
                           () -> CLIENT.interactionManager
                              .clickSlot(genericContainerScreenHandler.syncId, intValue30, 0, SlotActionType.QUICK_MOVE, CLIENT.player)
                        );
                     this.valuesByKey3.remove(text24);
                     return;
                  }
               }
            }

            if (!flag23) {
               this.invoke45();
            }
         }
      }
   }

   private int compute10(GenericContainerScreenHandler genericContainerScreenHandler, int i) {
      int intValue31 = 0;

      for (int intValue32 = i; intValue32 < genericContainerScreenHandler.slots.size(); intValue32++) {
         Slot slot4 = (Slot)genericContainerScreenHandler.slots.get(intValue32);
         if (slot4.hasStack()) {
            ItemStack itemStack11 = slot4.getStack();
            if (this.valuesByKey3.getOrDefault(this.resolve19(itemStack11), 0) > 0 || this.check34(itemStack11)) {
               intValue31++;
            }
         }
      }

      return intValue31;
   }

   private boolean check32() {
      for (int intValue33 = 0; intValue33 < 36; intValue33++) {
         if (this.check34(CLIENT.player.getInventory().getStack(intValue33))) {
            return true;
         }
      }

      return false;
   }

   private boolean check33() {
      for (int intValue34 = 0; intValue34 < 36; intValue34++) {
         ItemStack itemStack12 = CLIENT.player.getInventory().getStack(intValue34);
         if (!itemStack12.isEmpty() && (this.check34(itemStack12) || this.valuesByKey3.getOrDefault(this.resolve19(itemStack12), 0) > 0)) {
            return true;
         }
      }

      return false;
   }

   private void invoke45() {
      this.valuesByKey3.clear();
      this.values.clear();
      this.queue.clear();
      this.intValue2 = 0;
      this.intValue3 = -1;
      if (CLIENT.player != null) {
         CLIENT.player.closeHandledScreen();
      }

      this.wardenFarmState2 = WardenFarm.WardenFarmState2.SEARCHING;
      this.dualTimer6.invoke();
      if (!this.flag11) {
         if (this.skladyvatDrop.isEnabled()
            && this.svapatAnarhii.isEnabled()
            && !"N/A".equals(this.nA)
            && !this.nA.equals(this.bazovayaAnarhiya.getValue())) {
            this.invoke9((Runnable)(() -> {
               this.flag = true;
               this.timestamp = System.currentTimeMillis() + 500L;
            }));
         }
      } else {
         this.flag11 = false;
         if ("N/A".equals(this.nA2) || this.nA2 == null) {
            this.nA2 = "N/A".equals(this.nA) ? this.resolve20() : this.nA;
         }

         this.invoke52();
      }
   }

   private String resolve19(ItemStack itemStack) {
      return itemStack.getItem().toString() + "|" + itemStack.getName().getString();
   }

   private boolean check34(ItemStack itemStack) {
      if (itemStack.isEmpty()) {
         return false;
      } else {
         String text25 = itemStack.getName().getString();
         if (text25.contains("[★]")) {
            return true;
         } else {
            Item item2 = itemStack.getItem();
            if (this.predmetyDlyaLuta.isEnabled("Дон зелья") && this.check36(itemStack)) {
               return true;
            } else if (this.predmetyDlyaLuta.isEnabled("Сферы") && this.check37(itemStack)) {
               return true;
            } else if (this.predmetyDlyaLuta.isEnabled("Талисманы") && this.check38(itemStack)) {
               return true;
            } else if (!this.predmetyDlyaLuta.isEnabled("Стрелы") || item2 != Items.ARROW && item2 != Items.TIPPED_ARROW && item2 != Items.SPECTRAL_ARROW) {
               if (this.predmetyDlyaLuta.isEnabled("Оружие") && this.check35(item2)) {
                  return true;
               } else if (this.predmetyDlyaLuta.isEnabled("Броня") && AutoBuy.check37(item2)) {
                  return true;
               } else {
                  return this.predmetyDlyaLuta.isEnabled("Яйца") && item2 instanceof SpawnEggItem
                     ? true
                     : this.predmetyDlyaLuta.isEnabled("Ценные предметы") && this.check39(itemStack);
               }
            } else {
               return true;
            }
         }
      }
   }

   private boolean check35(Item item) {
      return item == Items.WOODEN_SWORD
         || item == Items.STONE_SWORD
         || item == Items.IRON_SWORD
         || item == Items.GOLDEN_SWORD
         || item == Items.DIAMOND_SWORD
         || item == Items.NETHERITE_SWORD
         || item == Items.WOODEN_AXE
         || item == Items.STONE_AXE
         || item == Items.IRON_AXE
         || item == Items.GOLDEN_AXE
         || item == Items.DIAMOND_AXE
         || item == Items.NETHERITE_AXE
         || item == Items.TRIDENT
         || item == Items.MACE
         || item == Items.BOW
         || item == Items.CROSSBOW;
   }

   private boolean check36(ItemStack itemStack) {
      return SpecialItemUtils.check25(itemStack)
         || SpecialItemUtils.check26(itemStack)
         || SpecialItemUtils.check27(itemStack)
         || SpecialItemUtils.check28(itemStack)
         || SpecialItemUtils.check29(itemStack)
         || SpecialItemUtils.check30(itemStack)
         || SpecialItemUtils.check31(itemStack);
   }

   private boolean check37(ItemStack itemStack) {
      return SpecialItemUtils.check8(itemStack)
         || SpecialItemUtils.check9(itemStack)
         || SpecialItemUtils.check10(itemStack)
         || SpecialItemUtils.check11(itemStack)
         || SpecialItemUtils.check12(itemStack)
         || SpecialItemUtils.check13(itemStack)
         || SpecialItemUtils.check14(itemStack)
         || SpecialItemUtils.check15(itemStack)
         || SpecialItemUtils.check16(itemStack);
   }

   private boolean check38(ItemStack itemStack) {
      return SpecialItemUtils.check17(itemStack)
         || SpecialItemUtils.check18(itemStack)
         || SpecialItemUtils.check19(itemStack)
         || SpecialItemUtils.check20(itemStack)
         || SpecialItemUtils.check21(itemStack)
         || SpecialItemUtils.check22(itemStack)
         || SpecialItemUtils.check23(itemStack)
         || SpecialItemUtils.check24(itemStack);
   }

   private boolean check39(ItemStack itemStack) {
      Item item3 = itemStack.getItem();
      if (item3 instanceof BlockItem blockItem && blockItem.getBlock() instanceof AbstractSkullBlock) {
         return true;
      } else if (item3 == Items.TOTEM_OF_UNDYING || item3 == Items.PAPER || item3 == Items.IRON_NUGGET || item3 == Items.TRIPWIRE_HOOK) {
         return true;
      } else if (item3 == Items.GUNPOWDER || item3 == Items.TNT || item3 == Items.NETHERITE_INGOT || item3 == Items.NETHER_STAR || item3 == Items.ENDER_EYE) {
         return true;
      } else if (item3 == Items.SNOWBALL || item3 == Items.SUGAR || item3 == Items.PHANTOM_MEMBRANE) {
         return true;
      } else {
         return item3 != Items.NETHERITE_SCRAP && item3 != Items.ELYTRA
            ? item3 == Items.CAMPFIRE
               || item3 == Items.SOUL_CAMPFIRE
               || item3 == Items.BEACON
               || item3 == Items.ENCHANTED_GOLDEN_APPLE
               || item3 == Items.GOLDEN_APPLE
               || item3 == Items.SPAWNER
            : true;
      }
   }

   @EventHandler
   public void onRender3D(Render3DEvent render3DEvent) {
      if (CLIENT.world != null && CLIENT.player != null) {
         Immediate immediate = WorldRenderBuffer.getIMMEDIATE();
         boolean flag24 = false ;

         label91: {
            try {
               flag24 = true;
               Vec3d vec3d10 = CLIENT.gameRenderer.getCamera().getPos();
               Matrix4f matrix4f2 = render3DEvent.getMatrixStack().peek().getPositionMatrix();
               VertexConsumer vertexConsumer2 = immediate.getBuffer(RENDER_LAYER);
               if (this.skladyvatDrop.isEnabled() && this.kudaSkladyvat.is("Ресы") && this.blockPos3 != null) {
                  this.invoke46(vertexConsumer2, matrix4f2, this.blockPos3, vec3d10, new Color(150, 50, 255, 120), new Color(150, 50, 255, 0));
               }

               if (this.blockPos5 != null) {
                  this.invoke46(vertexConsumer2, matrix4f2, this.blockPos5, vec3d10, new Color(255, 220, 0, 140), new Color(255, 220, 0, 0));
               }

               if (!this.check2()) {
                  flag24 = false;
                  break label91;
               }

               for (BlockPos blockPos21 : this.items.stream().sorted(Comparator.comparingLong(this::compute)).limit(5L).collect(Collectors.toList())) {
                  long longValue16 = this.compute(blockPos21) - System.currentTimeMillis();
                  Color color3;
                  Color color4;
                  if (blockPos21.equals(this.blockPos)) {
                     float floatValue7 = (float)(Math.sin(System.currentTimeMillis() / 60.0) * 0.5 + 0.5);
                     color3 = new Color(0, 150, 255, Math.min(255, (int)(80.0F + 150.0F * floatValue7)));
                     color4 = new Color(0, 150, 255, 0);
                  } else if (longValue16 <= 0L) {
                     color3 = new Color(0, 255, 150, 120);
                     color4 = new Color(0, 255, 150, 0);
                  } else if (longValue16 <= 20000L) {
                     float floatValue8 = (float)(Math.sin(System.currentTimeMillis() / 60.0) * 0.5 + 0.5);
                     color3 = new Color(255, 140, 0, Math.min(255, (int)(80.0F + 150.0F * floatValue8)));
                     color4 = new Color(255, 140, 0, 0);
                  } else {
                     color3 = new Color(255, 0, 0, 150);
                     color4 = new Color(255, 0, 0, 0);
                  }

                  this.invoke46(vertexConsumer2, matrix4f2, blockPos21, vec3d10, color3, color4);
               }

               flag24 = false;
            } finally {
               if (flag24) {
                  WorldRenderBuffer.invoke();
               }
            }

            WorldRenderBuffer.invoke();
            return;
         }

         WorldRenderBuffer.invoke();
      }
   }

   private void invoke46(VertexConsumer vertexConsumer, Matrix4f matrix4f, BlockPos blockPos, Vec3d vec3d, Color color, Color color2) {
      float floatValue9 = (float)(blockPos.getX() - vec3d.x);
      float floatValue10 = (float)(blockPos.getY() - vec3d.y);
      float floatValue11 = (float)(blockPos.getZ() - vec3d.z);
      float floatValue12 = (float)(blockPos.getX() + 1 - vec3d.x);
      float floatValue13 = (float)(blockPos.getY() + 1 - vec3d.y);
      float floatValue14 = (float)(blockPos.getZ() + 1 - vec3d.z);
      this.invoke47(vertexConsumer, matrix4f, floatValue9, floatValue10, floatValue11, floatValue12, floatValue13, floatValue14, color, color2);
   }

   private void invoke47(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float g, float h, float i, float j, float k, Color color, Color color2) {
      int intValue35 = color.getRed();
      int intValue36 = color.getGreen();
      int intValue37 = color.getBlue();
      int intValue38 = color.getAlpha();
      int intValue39 = color2.getRed();
      int intValue40 = color2.getGreen();
      int intValue41 = color2.getBlue();
      int intValue42 = color2.getAlpha();
      vertexConsumer.vertex(matrix4f, f, g, h).color(intValue35, intValue36, intValue37, intValue38);
      vertexConsumer.vertex(matrix4f, i, g, h).color(intValue35, intValue36, intValue37, intValue38);
      vertexConsumer.vertex(matrix4f, i, j, h).color(intValue39, intValue40, intValue41, intValue42);
      vertexConsumer.vertex(matrix4f, f, j, h).color(intValue39, intValue40, intValue41, intValue42);
      vertexConsumer.vertex(matrix4f, f, j, k).color(intValue39, intValue40, intValue41, intValue42);
      vertexConsumer.vertex(matrix4f, i, j, k).color(intValue39, intValue40, intValue41, intValue42);
      vertexConsumer.vertex(matrix4f, i, g, k).color(intValue35, intValue36, intValue37, intValue38);
      vertexConsumer.vertex(matrix4f, f, g, k).color(intValue35, intValue36, intValue37, intValue38);
      vertexConsumer.vertex(matrix4f, f, g, k).color(intValue35, intValue36, intValue37, intValue38);
      vertexConsumer.vertex(matrix4f, f, g, h).color(intValue35, intValue36, intValue37, intValue38);
      vertexConsumer.vertex(matrix4f, f, j, h).color(intValue39, intValue40, intValue41, intValue42);
      vertexConsumer.vertex(matrix4f, f, j, k).color(intValue39, intValue40, intValue41, intValue42);
      vertexConsumer.vertex(matrix4f, i, j, k).color(intValue39, intValue40, intValue41, intValue42);
      vertexConsumer.vertex(matrix4f, i, j, h).color(intValue39, intValue40, intValue41, intValue42);
      vertexConsumer.vertex(matrix4f, i, g, h).color(intValue35, intValue36, intValue37, intValue38);
      vertexConsumer.vertex(matrix4f, i, g, k).color(intValue35, intValue36, intValue37, intValue38);
      vertexConsumer.vertex(matrix4f, f, g, h).color(intValue35, intValue36, intValue37, intValue38);
      vertexConsumer.vertex(matrix4f, f, g, k).color(intValue35, intValue36, intValue37, intValue38);
      vertexConsumer.vertex(matrix4f, i, g, k).color(intValue35, intValue36, intValue37, intValue38);
      vertexConsumer.vertex(matrix4f, i, g, h).color(intValue35, intValue36, intValue37, intValue38);
      vertexConsumer.vertex(matrix4f, f, j, h).color(intValue39, intValue40, intValue41, intValue42);
      vertexConsumer.vertex(matrix4f, i, j, h).color(intValue39, intValue40, intValue41, intValue42);
      vertexConsumer.vertex(matrix4f, i, j, k).color(intValue39, intValue40, intValue41, intValue42);
      vertexConsumer.vertex(matrix4f, f, j, k).color(intValue39, intValue40, intValue41, intValue42);
   }

   private void invoke48() {
      this.wardenFarmState4 = WardenFarm.WardenFarmState4.NONE;
      this.wardenFarmState3 = WardenFarm.WardenFarmState3.FIND;
      this.blockPos7 = null;
      this.flag5 = false;
      this.invoke49();
      this.invoke60();
   }

   private void invoke49() {
      this.flag7 = false;
      this.flag8 = false;
      this.flag9 = false;
      this.wardenFarmState6 = WardenFarm.WardenFarmState6.NONE;
   }

   private boolean check40() {
      if (this.check2() || CLIENT.player == null) {
         this.invoke49();
         return false;
      } else if (this.wardenFarmState6 == WardenFarm.WardenFarmState6.WAITING) {
         return true;
      } else {
         if (!this.flag7) {
            CLIENT.player.networkHandler.sendChatCommand("home " + this.homDlyaVardena.getValue().trim());
            this.flag7 = true;
            this.wardenFarmState6 = WardenFarm.WardenFarmState6.WAITING;
            this.flag8 = false;
            this.flag9 = false;
            this.dualTimer.invoke();
            this.dualTimer3.invoke();
         }

         return true;
      }
   }

   private boolean check41() {
      if (this.wardenFarmState6 != WardenFarm.WardenFarmState6.WAITING) {
         return false;
      } else if (this.flag8) {
         if (!this.flag9) {
            this.flag9 = true;
         }

         if (this.dualTimer3.check5(5000L)) {
            this.invoke49();
         }

         return true;
      } else if (!this.check2() && !this.dualTimer.check5(2500L)) {
         return true;
      } else {
         this.invoke49();
         return false;
      }
   }

   private boolean check42() {
      if (!this.flag6 || CLIENT.player == null) {
         return false;
      } else if (!this.check2() && this.svapatAnarhii.isEnabled()) {
         ServerStatsParser.INSTANCE.invoke2();
         String text26 = ServerStatsParser.INSTANCE.getNA2();
         if (this.check43(text26)) {
            return this.wardenFarmState6 != WardenFarm.WardenFarmState6.NONE || this.check40();
         } else if (this.check7()) {
            return true;
         } else {
            if (this.dualTimer.check5(3000L)) {
               String text27 = this.nA2;
               if (!this.check43(text27)) {
                  text27 = this.resolve20();
               }

               if (!this.check43(text27)) {
                  this.flag6 = false;
                  return false;
               }

               CLIENT.player.networkHandler.sendChatCommand("an" + text27);
               this.dualTimer.invoke();
            }

            return true;
         }
      } else {
         this.flag6 = false;
         return false;
      }
   }

   private void invoke50() {
      if (this.check2()) {
         this.wardenFarmState4 = WardenFarm.WardenFarmState4.USE_INVIS;
         this.dualTimer.invoke();
      } else {
         if (this.check40()) {
            this.wardenFarmState4 = WardenFarm.WardenFarmState4.TELEPORT_WARDEN;
         } else {
            this.wardenFarmState4 = WardenFarm.WardenFarmState4.USE_INVIS;
            this.dualTimer.invoke();
         }
      }
   }

   private void invoke51() {
      this.nA2 = this.resolve20();
      if (this.skladyvatDrop.isEnabled() && this.check33()) {
         this.flag11 = true;
         this.invoke42();
      } else {
         this.valuesByKey3.clear();
         this.invoke52();
      }
   }

   private void invoke52() {
      if (this.avtoEdaIInviz.isEnabled() && this.svapatAnarhii.isEnabled()) {
         if (this.iBaritone != null) {
            this.iBaritone.getPathingBehavior().cancelEverything();
         }

         if (CLIENT.player != null) {
            CLIENT.player.closeHandledScreen();
         }

         if ("N/A".equals(this.nA2) || this.nA2 == null) {
            this.nA2 = this.resolve20();
         }

         this.DynamicButtonSetting = false;
         this.flag10 = false;
         this.values2.clear();
         this.invoke53();
         this.wardenFarmState4 = WardenFarm.WardenFarmState4.SWAP_TO_BASE;
         this.wardenFarmState2 = WardenFarm.WardenFarmState2.SEARCHING;
         this.blockPos = null;
         this.dualTimer.invoke();
         this.dualTimer3.invoke();
      }
   }

   private void invoke53() {
      this.wardenFarmState3 = WardenFarm.WardenFarmState3.FIND;
      this.blockPos7 = null;
      this.intValue5 = 0;
      this.blockPos4 = null;
      this.intValue4 = 0;
      this.wardenFarmState5 = WardenFarm.WardenFarmState5.NONE;
      this.queue.clear();
      this.intValue6 = -1;
   }

   private String resolve20() {
      ServerStatsParser.INSTANCE.invoke2();
      String text28 = ServerStatsParser.INSTANCE.getNA2();
      if (this.check43(text28)) {
         return text28;
      } else {
         String[] texts2 = this.anarhiiDlyaFarma.getValue().split(",");
         return texts2.length > 0 && !texts2[0].trim().isEmpty() ? texts2[0].trim() : text28;
      }
   }

   private boolean check43(String string) {
      if (string != null && !"N/A".equals(string) && !string.equals(this.bazovayaAnarhiya.getValue())) {
         for (String text29 : this.anarhiiDlyaFarma.getValue().split(",")) {
            if (text29.trim().equals(string)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private boolean check44() {
      ServerStatsParser.INSTANCE.invoke2();
      return ServerStatsParser.INSTANCE.getNA2().equals(this.bazovayaAnarhiya.getValue());
   }

   private boolean check45() {
      return !this.check43(ServerStatsParser.INSTANCE.getNA2())
         ? false
         : this.check2()
            || this.wardenFarmState2 == WardenFarm.WardenFarmState2.GOING_TO_CHEST
            || this.wardenFarmState2 == WardenFarm.WardenFarmState2.ROTATING
            || this.wardenFarmState2 == WardenFarm.WardenFarmState2.OPENING
            || this.wardenFarmState2 == WardenFarm.WardenFarmState2.WAITING_FOR_GUI;
   }

   private boolean check46() {
      if (!this.avtoEdaIInviz.isEnabled()) {
         return true;
      } else {
         boolean flag25 = this.compute11() >= 1 || this.check49();
         boolean flag26 = this.compute12() >= 3 || this.DynamicButtonSetting;
         return flag25 && flag26;
      }
   }

   private boolean check47() {
      if (!this.avtoEdaIInviz.isEnabled()) {
         return false;
      } else if (!this.check49() && this.compute11() == 0) {
         return true;
      } else {
         return this.zelyaSkorostiBratIPit.isEnabled()
               && !this.flag10
               && !CLIENT.player.hasStatusEffect(StatusEffects.SPEED)
               && this.compute9() == 0
            ? true
            : this.compute12() == 0 && !this.DynamicButtonSetting;
      }
   }

   private int compute11() {
      int intValue43 = 0;

      for (int intValue44 = 0; intValue44 < 36; intValue44++) {
         ItemStack itemStack13 = CLIENT.player.getInventory().getStack(intValue44);
         if (this.check30(itemStack13)) {
            intValue43 += itemStack13.getCount();
         }
      }

      return intValue43;
   }

   private boolean check48(ItemStack itemStack) {
      if (itemStack.isEmpty()) {
         return false;
      } else if (!itemStack.isOf(Items.GOLDEN_CARROT) && !itemStack.isOf(Items.CARROT)) {
         String text30 = itemStack.getName().getString().toLowerCase(Locale.ROOT);
         return text30.contains("морков") || text30.contains("carrot");
      } else {
         return true;
      }
   }

   private int compute12() {
      int intValue45 = 0;

      for (int intValue46 = 0; intValue46 < 36; intValue46++) {
         ItemStack itemStack14 = CLIENT.player.getInventory().getStack(intValue46);
         if (this.check48(itemStack14)) {
            intValue45 += itemStack14.getCount();
         }
      }

      return intValue45;
   }

   private boolean check49() {
      return CLIENT.player != null && CLIENT.player.hasStatusEffect(StatusEffects.INVISIBILITY);
   }

   private boolean check50(String string) {
      String text31 = string.toLowerCase(Locale.ROOT).replaceAll("§.", "");
      return text31.contains("не найден")
         || text31.contains("не существует")
         || text31.contains("нет дома")
         || text31.contains("нет точки")
         || text31.contains("not found")
         || text31.contains("unknown home")
         || text31.contains("home") && text31.contains("нет");
   }

   private void invoke54() {
      if (CLIENT.player != null && this.iBaritone != null) {
         ServerStatsParser.INSTANCE.invoke2();
         switch (this.wardenFarmState4) {
            case SWAP_TO_BASE:
               if (this.check44()) {
                  this.wardenFarmState4 = WardenFarm.WardenFarmState4.COLLECT_KIT;
                  this.invoke53();
                  this.dualTimer.invoke();
                  this.dualTimer3.invoke();
               } else if (this.check7()) {
                  this.dualTimer3.invoke();
               } else if (this.dualTimer.check5(700L)) {
                  CLIENT.player.networkHandler.sendChatCommand("an" + this.bazovayaAnarhiya.getValue());
                  this.wardenFarmState4 = WardenFarm.WardenFarmState4.WAIT_BASE;
                  this.dualTimer.invoke();
                  this.dualTimer3.invoke();
               }
               break;
            case WAIT_BASE:
               if (this.check44()) {
                  this.wardenFarmState4 = WardenFarm.WardenFarmState4.COLLECT_KIT;
                  this.invoke53();
                  this.dualTimer.invoke();
                  this.dualTimer3.invoke();
               } else if (this.dualTimer3.check5(20000L)) {
                  this.invoke48();
               }
               break;
            case COLLECT_KIT:
               this.invoke55();
               break;
            case SWAP_TO_FARM:
               if (this.check46()) {
                  String text32 = this.nA2;
                  if ("N/A".equals(text32) || text32 == null) {
                     text32 = this.resolve20();
                  }

                  if (ServerStatsParser.INSTANCE.getNA2().equals(text32)) {
                     this.invoke50();
                  } else if (this.check7()) {
                     this.dualTimer3.invoke();
                  } else if (this.dualTimer.check5(700L)) {
                     CLIENT.player.networkHandler.sendChatCommand("an" + text32);
                     this.wardenFarmState4 = WardenFarm.WardenFarmState4.WAIT_FARM;
                     this.dualTimer.invoke();
                     this.dualTimer3.invoke();
                  }
               } else if (this.dualTimer3.check5(15000L)) {
                  this.invoke48();
               } else {
                  this.wardenFarmState4 = WardenFarm.WardenFarmState4.COLLECT_KIT;
                  this.invoke53();
               }
               break;
            case WAIT_FARM:
               if (this.check43(ServerStatsParser.INSTANCE.getNA2())) {
                  this.invoke50();
                  this.dualTimer3.invoke();
               } else if (this.dualTimer3.check5(20000L)) {
                  this.invoke48();
               }
               break;
            case TELEPORT_WARDEN:
               if (this.check41()) {
                  return;
               }

               this.wardenFarmState4 = WardenFarm.WardenFarmState4.USE_INVIS;
               this.dualTimer.invoke();
               break;
            case USE_INVIS:
               if (!this.check49()) {
                  if (this.wardenFarmState == WardenFarm.WardenFarmState.NONE) {
                     if (this.compute14() == -1) {
                        this.invoke52();
                        return;
                     }

                     this.invoke58(WardenFarm.WardenFarmState.DRINK_INVIS);
                  } else {
                     this.invoke59();
                  }

                  return;
               }

               this.invoke60();
               this.invoke48();
               this.wardenFarmState2 = WardenFarm.WardenFarmState2.SEARCHING;
               this.dualTimer6.invoke();
         }
      }
   }

   private void invoke55() {
      if (this.check46() && !this.check23()) {
         this.invoke53();
         this.wardenFarmState4 = WardenFarm.WardenFarmState4.SWAP_TO_FARM;
         this.dualTimer.invoke();
         this.dualTimer3.invoke();
      } else {
         switch (this.wardenFarmState3) {
            case FIND:
               this.blockPos7 = this.resolve15();
               if (this.blockPos7 == null) {
                  boolean flag27 = this.compute11() >= 1 || this.check49();
                  if (flag27 && this.dualTimer3.check5(1500L)) {
                     this.DynamicButtonSetting = this.compute12() < 3;
                     this.flag10 = this.zelyaSkorostiBratIPit.isEnabled() && this.compute9() < 1;
                     this.invoke53();
                     this.wardenFarmState4 = WardenFarm.WardenFarmState4.SWAP_TO_FARM;
                     this.dualTimer.invoke();
                     this.dualTimer3.invoke();
                  } else if (!flag27 && this.dualTimer3.check5(12000L)) {
                     this.values2.clear();
                     this.dualTimer3.invoke();
                  }

                  return;
               }

               this.wardenFarmState3 = WardenFarm.WardenFarmState3.GOING;
               this.dualTimer.invoke();
               this.dualTimer3.invoke();
               break;
            case GOING:
               double doubleValue59 = CLIENT.player.getPos().distanceTo(Vec3d.ofCenter(this.blockPos7));
               if (doubleValue59 <= 4.0 && this.check16(this.blockPos7)) {
                  this.iBaritone.getPathingBehavior().cancelEverything();
                  this.wardenFarmState3 = WardenFarm.WardenFarmState3.ROTATING;
                  this.dualTimer.invoke();
               } else if (this.dualTimer3.check5(15000L)) {
                  this.invoke40(this.blockPos7);
                  this.blockPos7 = null;
                  this.wardenFarmState3 = WardenFarm.WardenFarmState3.FIND;
                  this.dualTimer3.invoke();
               } else if (!this.iBaritone.getCustomGoalProcess().isActive() || this.dualTimer5.check5(2500L)) {
                  this.iBaritone.getCustomGoalProcess().setGoalAndPath(new GoalNear(this.blockPos7, 1));
                  this.dualTimer5.invoke();
               }
               break;
            case ROTATING:
               if (!this.check17(this.blockPos7)) {
                  this.wardenFarmState3 = WardenFarm.WardenFarmState3.GOING;
                  this.dualTimer.invoke();
                  this.dualTimer3.invoke();
                  return;
               }

               Rotation rotation7 = this.resolve12(this.resolve9(this.blockPos7));
               this.SpacerSetting.invoke3(this.resolve11(rotation7, this.measure2(rotation7)), 35.0F, 35.0F, 35.0F, 35.0F, 20, 1);
               if (this.resolve10(this.blockPos7) != null && this.dualTimer.check5(100L)) {
                  this.wardenFarmState3 = WardenFarm.WardenFarmState3.OPENING;
                  this.dualTimer.invoke();
               }
               break;
            case OPENING:
               if (!this.check17(this.blockPos7)) {
                  this.wardenFarmState3 = WardenFarm.WardenFarmState3.GOING;
                  this.dualTimer.invoke();
                  this.dualTimer3.invoke();
                  return;
               }

               Rotation rotation8 = this.resolve12(this.resolve9(this.blockPos7));
               this.SpacerSetting.invoke3(this.resolve11(rotation8, 0.6F), 18.0F, 18.0F, 20.0F, 20.0F, 20, 1);
               BlockHitResult blockHitResult5 = this.resolve10(this.blockPos7);
               if (blockHitResult5 == null) {
                  if (this.dualTimer.check5(1200L)) {
                     this.wardenFarmState3 = WardenFarm.WardenFarmState3.ROTATING;
                     this.dualTimer.invoke();
                  }

                  return;
               }

               if (System.currentTimeMillis() - this.timestamp8 < 400L) {
                  return;
               }

               if (this.dualTimer.check5(100L)) {
                  CLIENT.player.swingHand(Hand.MAIN_HAND);
                  CLIENT.interactionManager.interactBlock(CLIENT.player, Hand.MAIN_HAND, blockHitResult5);
                  RotationController.rotationControllerState2 = RotationController.RotationControllerState2.IDLE;
                  this.wardenFarmState3 = WardenFarm.WardenFarmState3.WAIT_GUI;
                  this.dualTimer.invoke();
               }
               break;
            case WAIT_GUI:
               if (this.dualTimer.check5(1500L)) {
                  this.intValue5++;
                  if (this.intValue5 >= 4) {
                     this.intValue5 = 0;
                     this.blockPos7 = null;
                     this.wardenFarmState3 = WardenFarm.WardenFarmState3.FIND;
                  } else if (this.check17(this.blockPos7)) {
                     this.wardenFarmState3 = WardenFarm.WardenFarmState3.OPENING;
                  } else {
                     this.wardenFarmState3 = WardenFarm.WardenFarmState3.GOING;
                  }

                  this.dualTimer.invoke();
                  this.dualTimer3.invoke();
               }
         }
      }
   }

   private boolean check51(GenericContainerScreen genericContainerScreen) {
      String text33 = genericContainerScreen.getTitle().getString().toLowerCase(Locale.ROOT).replaceAll("§.", "").trim();
      return text33.contains("сундук") || text33.contains("chest") || text33.contains("бочка") || text33.contains("barrel") || text33.contains("шалкер");
   }

   private void invoke56(GenericContainerScreenHandler genericContainerScreenHandler) {
      if (CLIENT.player != null && CLIENT.interactionManager != null && this.blockPos7 != null) {
         this.invoke39(genericContainerScreenHandler);
         if (!this.queue.isEmpty()) {
            if (this.dualTimer2.check5(60L)) {
               this.queue.poll().run();
               this.dualTimer2.invoke();
            }
         } else {
            int intValue47 = genericContainerScreenHandler.slots.size() - 36;
            int intValue48 = this.check21() ? 1 - this.compute11() : 0;
            int intValue49 = this.zelyaSkorostiBratIPit.isEnabled() ? 1 - this.compute9() : 0;
            int intValue50 = 3 - this.compute12();
            if (intValue48 <= 0 && intValue49 <= 0 && intValue50 <= 0) {
               this.DynamicButtonSetting = false;
               this.flag10 = false;
               CLIENT.player.closeHandledScreen();
               this.invoke53();
               this.wardenFarmState4 = WardenFarm.WardenFarmState4.SWAP_TO_FARM;
               this.dualTimer.invoke();
               this.dualTimer3.invoke();
            } else if (!this.check52(genericContainerScreenHandler, intValue47, this::check30, intValue48)
               && !this.check52(genericContainerScreenHandler, intValue47, this::check31, intValue49)
               && !this.check52(genericContainerScreenHandler, intValue47, this::check48, intValue50)) {
               long longValue17 = this.check53(genericContainerScreenHandler, intValue47) ? 350L : 1500L;
               if (this.dualTimer.check5(longValue17)) {
                  this.invoke40(this.blockPos7);
                  CLIENT.player.closeHandledScreen();
                  this.timestamp8 = System.currentTimeMillis();
                  this.invoke53();
                  this.dualTimer.invoke();
                  this.dualTimer3.invoke();
               }
            } else {
               this.dualTimer.invoke();
            }
         }
      }
   }

   private boolean check52(GenericContainerScreenHandler genericContainerScreenHandler, int i, Predicate<ItemStack> predicate, int j) {
      if (j <= 0) {
         return false;
      } else {
         for (int intValue51 = 0; intValue51 < i; intValue51++) {
            Slot slot5 = (Slot)genericContainerScreenHandler.slots.get(intValue51);
            if (slot5.hasStack() && predicate.test(slot5.getStack())) {
               int intValue52 = intValue51;
               int intValue53 = slot5.getStack().getCount();
               int intValue54 = this.compute13(genericContainerScreenHandler, i);
               if (intValue53 > j && intValue54 != -1) {
                  this.queue
                     .add(
                        () -> CLIENT.interactionManager
                           .clickSlot(genericContainerScreenHandler.syncId, intValue52, 0, SlotActionType.PICKUP, CLIENT.player)
                     );

                  for (int intValue55 = 0; intValue55 < j; intValue55++) {
                     this.queue
                        .add(
                           () -> CLIENT.interactionManager
                              .clickSlot(genericContainerScreenHandler.syncId, intValue54, 1, SlotActionType.PICKUP, CLIENT.player)
                        );
                  }

                  this.queue
                     .add(
                        () -> CLIENT.interactionManager
                           .clickSlot(genericContainerScreenHandler.syncId, intValue52, 0, SlotActionType.PICKUP, CLIENT.player)
                     );
                  return true;
               }

               this.queue
                  .add(
                     () -> CLIENT.interactionManager
                        .clickSlot(genericContainerScreenHandler.syncId, intValue52, 0, SlotActionType.QUICK_MOVE, CLIENT.player)
                  );
               return true;
            }
         }

         return false;
      }
   }

   private boolean check53(GenericContainerScreenHandler genericContainerScreenHandler, int i) {
      for (int intValue56 = 0; intValue56 < i; intValue56++) {
         if (((Slot)genericContainerScreenHandler.slots.get(intValue56)).hasStack()) {
            return true;
         }
      }

      return false;
   }

   private int compute13(GenericContainerScreenHandler genericContainerScreenHandler, int i) {
      for (int intValue57 = i; intValue57 < genericContainerScreenHandler.slots.size(); intValue57++) {
         if (!((Slot)genericContainerScreenHandler.slots.get(intValue57)).hasStack()) {
            return intValue57;
         }
      }

      return -1;
   }

   private void invoke57() {
      if (!this.avtoEdaIInviz.isEnabled() || this.wardenFarmState4 != WardenFarm.WardenFarmState4.NONE || CLIENT.currentScreen != null) {
         this.invoke60();
      } else if (this.wardenFarmState2 == WardenFarm.WardenFarmState2.SWAPPING_TO_SAVE_ANARCHY
         || this.wardenFarmState2 == WardenFarm.WardenFarmState2.GOING_TO_STASH
         || this.wardenFarmState2 == WardenFarm.WardenFarmState2.ROTATING_STASH
         || this.wardenFarmState2 == WardenFarm.WardenFarmState2.OPENING_STASH_BLOCK
         || this.wardenFarmState2 == WardenFarm.WardenFarmState2.WAITING_FOR_GUI_STASH
         || this.wardenFarmState2 == WardenFarm.WardenFarmState2.STORING_IN_CHEST
         || this.wardenFarmState2 == WardenFarm.WardenFarmState2.OPENING_STASH) {
         this.invoke60();
      } else if (this.wardenFarmState != WardenFarm.WardenFarmState.NONE) {
         this.invoke59();
      } else if (CLIENT.player.getHungerManager().getFoodLevel() <= 16 && this.compute17() != -1) {
         this.invoke58(WardenFarm.WardenFarmState.EAT_CARROT);
      } else if (!this.check49()) {
         if (this.compute14() != -1) {
            this.invoke58(WardenFarm.WardenFarmState.DRINK_INVIS);
         } else if (this.check47()) {
            this.invoke51();
         }
      } else {
         if (this.zelyaSkorostiBratIPit.isEnabled() && !CLIENT.player.hasStatusEffect(StatusEffects.SPEED)) {
            if (this.compute16() != -1) {
               this.invoke58(WardenFarm.WardenFarmState.DRINK_SPEED);
            } else if (this.check47()) {
               this.invoke51();
            }
         }
      }
   }

   private int compute14() {
      for (int intValue58 = 0; intValue58 < 36; intValue58++) {
         if (this.check30(CLIENT.player.getInventory().getStack(intValue58))) {
            return intValue58;
         }
      }

      return -1;
   }

   private int compute15() {
      for (int intValue59 = 0; intValue59 < 36; intValue59++) {
         if (CLIENT.player.getInventory().getStack(intValue59).isOf(Items.GOLDEN_CARROT)) {
            return intValue59;
         }
      }

      for (int intValue60 = 0; intValue60 < 36; intValue60++) {
         ItemStack itemStack15 = CLIENT.player.getInventory().getStack(intValue60);
         if (this.check48(itemStack15)) {
            return intValue60;
         }
      }

      return -1;
   }

   private int compute16() {
      for (int intValue61 = 0; intValue61 < 36; intValue61++) {
         ItemStack itemStack16 = CLIENT.player.getInventory().getStack(intValue61);
         if (this.check31(itemStack16) && !this.check28(itemStack16)) {
            return intValue61;
         }
      }

      return -1;
   }

   private boolean check54(ItemStack itemStack) {
      if (itemStack.isEmpty()) {
         return false;
      } else if (this.check48(itemStack)) {
         return true;
      } else {
         return itemStack.get(DataComponentTypes.FOOD) == null ? false : !this.check34(itemStack);
      }
   }

   private int compute17() {
      int intValue62 = this.compute15();
      if (intValue62 != -1) {
         return intValue62;
      } else {
         for (int intValue63 = 0; intValue63 < 36; intValue63++) {
            ItemStack itemStack17 = CLIENT.player.getInventory().getStack(intValue63);
            if (this.check54(itemStack17)) {
               return intValue63;
            }
         }

         return -1;
      }
   }

   private void invoke58(WardenFarm.WardenFarmState wardenFarmState) {
      int intValue64 = switch (wardenFarmState) {
         case DRINK_INVIS -> this.compute14();
         case EAT_CARROT -> this.compute17();
         case DRINK_SPEED -> this.compute16();
         default -> -1;
      };
      if (intValue64 != -1) {
         this.intValue8 = CLIENT.player.getInventory().getSelectedSlot();
         this.intValue7 = intValue64;
         this.wardenFarmState = wardenFarmState;
         this.dualTimer10.invoke();
         this.invoke61(intValue64);
         CLIENT.options.useKey.setPressed(true);
      }
   }

   private void invoke59() {
      ItemStack itemStack18 = CLIENT.player.getMainHandStack();

      boolean flag28 = switch (this.wardenFarmState) {
         case DRINK_INVIS -> this.check30(itemStack18);
         case EAT_CARROT -> this.check54(itemStack18);
         case DRINK_SPEED -> this.check31(itemStack18);
         default -> false;
      };
      if (!flag28) {
         this.invoke60();
      } else {
         CLIENT.options.useKey.setPressed(true);

         boolean flag29 = switch (this.wardenFarmState) {
            case DRINK_INVIS -> this.check49();
            case EAT_CARROT -> CLIENT.player.getHungerManager().getFoodLevel() > 16;
            case DRINK_SPEED -> CLIENT.player.hasStatusEffect(StatusEffects.SPEED);
            default -> true;
         };
         if (flag29 || this.dualTimer10.check5(4500L)) {
            this.invoke60();
         }
      }
   }

   private void invoke60() {
      CLIENT.options.useKey.setPressed(false);
      if (CLIENT.player != null && this.intValue7 != -1 && this.intValue8 != -1) {
         CLIENT.player.getInventory().setSelectedSlot(this.intValue8);
      }

      this.wardenFarmState = WardenFarm.WardenFarmState.NONE;
      this.intValue7 = -1;
      this.intValue8 = -1;
   }

   private boolean check55() {
      if (!this.osvobozhdatHotbar.isEnabled() || CLIENT.player == null || CLIENT.interactionManager == null) {
         return false;
      } else if (CLIENT.player.isDead() || CLIENT.currentScreen != null || this.wardenFarmState != WardenFarm.WardenFarmState.NONE) {
         return false;
      } else if (this.check6()
         || this.wardenFarmState2 == WardenFarm.WardenFarmState2.ROTATING_STASH
         || this.wardenFarmState2 == WardenFarm.WardenFarmState2.OPENING_STASH_BLOCK
         || this.wardenFarmState2 == WardenFarm.WardenFarmState2.WAITING_FOR_GUI_STASH
         || this.wardenFarmState2 == WardenFarm.WardenFarmState2.STORING_IN_CHEST
         || this.wardenFarmState2 == WardenFarm.WardenFarmState2.OPENING_STASH) {
         return false;
      } else if (this.wardenFarmState4 != WardenFarm.WardenFarmState4.NONE && this.wardenFarmState3 != WardenFarm.WardenFarmState3.FIND && this.wardenFarmState3 != WardenFarm.WardenFarmState3.GOING) {
         return false;
      } else {
         int intValue65 = this.compute18();
         if (intValue65 == -1) {
            return false;
         } else {
            int intValue66 = this.compute19();
            if (intValue66 == -1) {
               return false;
            } else {
               if (this.iBaritone != null) {
                  this.iBaritone.getPathingBehavior().cancelEverything();
               }

               if (this.dualTimer11.check5(150L)) {
                  CLIENT.interactionManager.clickSlot(CLIENT.player.playerScreenHandler.syncId, intValue66, intValue65, SlotActionType.SWAP, CLIENT.player);
                  this.dualTimer11.invoke();
               }

               return true;
            }
         }
      }
   }

   private int compute18() {
      for (int intValue67 = 0; intValue67 <= 8; intValue67++) {
         if (!CLIENT.player.getInventory().getStack(intValue67).isEmpty()) {
            return intValue67;
         }
      }

      return -1;
   }

   private int compute19() {
      for (int intValue68 = 9; intValue68 < 36; intValue68++) {
         if (CLIENT.player.getInventory().getStack(intValue68).isEmpty()) {
            return intValue68;
         }
      }

      return -1;
   }

   private void invoke61(int i) {
      if (i <= 8) {
         CLIENT.player.getInventory().setSelectedSlot(i);
         this.intValue7 = i;
      } else {
         int intValue69 = this.compute20();
         if (intValue69 == -1) {
            intValue69 = this.intValue8;
         }

         CLIENT.interactionManager.clickSlot(CLIENT.player.playerScreenHandler.syncId, i, intValue69, SlotActionType.SWAP, CLIENT.player);
         CLIENT.player.getInventory().setSelectedSlot(intValue69);
         this.intValue7 = intValue69;
      }
   }

   private int compute20() {
      for (int intValue70 = 0; intValue70 <= 8; intValue70++) {
         if (CLIENT.player.getInventory().getStack(intValue70).isEmpty()) {
            return intValue70;
         }
      }

      return -1;
   }

   static enum WardenFarmState {
      NONE,
      DRINK_INVIS,
      EAT_CARROT,
      DRINK_SPEED;
   }

   static enum WardenFarmState2 {
      SEARCHING,
      GOING_TO_CHEST,
      ROTATING,
      OPENING,
      WAITING_FOR_GUI,
      RETREATING,
      GOING_TO_DEATH_LOOT,
      COLLECTING_DEATH_LOOT,
      HUB_WAITING_FOR_CHEST,
      SWAPPING_TO_SAVE_ANARCHY,
      GOING_TO_STASH,
      OPENING_STASH,
      ROTATING_STASH,
      OPENING_STASH_BLOCK,
      WAITING_FOR_GUI_STASH,
      STORING_IN_CHEST;
   }

   static enum WardenFarmState3 {
      FIND,
      GOING,
      ROTATING,
      OPENING,
      WAIT_GUI;
   }

   static enum WardenFarmState4 {
      NONE,
      SWAP_TO_BASE,
      WAIT_BASE,
      COLLECT_KIT,
      SWAP_TO_FARM,
      WAIT_FARM,
      TELEPORT_WARDEN,
      USE_INVIS;
   }

   static enum WardenFarmState5 {
      NONE,
      INVIS,
      CARROT;
   }

   static enum WardenFarmState6 {
      NONE,
      WAITING;
   }
}
