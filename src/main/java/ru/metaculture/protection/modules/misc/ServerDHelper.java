package ru.metaculture.protection;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline.Snippet;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.vertex.VertexFormat.DrawMode;
import java.awt.Color;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.RenderLayer.MultiPhaseParameters;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.chunk.WorldChunk;
import org.joml.Matrix4f;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "ServerDHelper",
   category = Category.Misc,
   description = "Удобный модуль для данжа варден, подсветка а так же автоматический лут сундуков"
)
public class ServerDHelper extends Module {
   public final ModeSetting rezhim = new ModeSetting("Режим", "Варден", "Варден", "Медный данж");
   public final ModeSetting rezhimRaboty = new ModeSetting("Режим работы", "Лутающий", "Лутающий", "Складывающий");
   public final BooleanSetting skladyvatDropVKlan = new BooleanSetting("Складывать дроп в клан", false);
   public final ModeSetting rezhim2 = new ModeSetting("Режим", "Авто", "Авто", "По бинду").setVisibilityCondition(() -> !this.skladyvatDropVKlan.isEnabled());
   public final KeybindSetting bind = new KeybindSetting("Бинд", -1).visibleWhen(() -> !this.rezhim2.is("По бинду"));
   public final GroupSetting predmetyDlyaLuta = new GroupSetting(
      "Предметы для лута",
      new BooleanSetting("Дон зелья", false),
      new BooleanSetting("Сферы", false),
      new BooleanSetting("Талисманы", false),
      new BooleanSetting("Стрелы", false),
      new BooleanSetting("Ценные предметы", false),
      new BooleanSetting("Яйца", false).visibleWhen(() -> !this.skladyvatDropVKlan.isEnabled())
   );
   public final BooleanSetting ustanovkaTochkiNaSunduk = new BooleanSetting("Установка точки на сундук", true);
   public final BooleanSetting rotatsiyaNaSunduk = new BooleanSetting("Ротация на сундук", false);
   public final KeybindSetting bindNaUstSunduka = new KeybindSetting("Бинд на уст. сундука", -1).visibleWhen(() -> !this.rezhimRaboty.is("Складывающий"));
   public final BooleanSetting neOtobrazhatEkran = new BooleanSetting("Не отображать экран", false);
   public static final Map<BlockPos, Long> VALUES_BY_KEY = new HashMap<>();
   public static final Map<BlockPos, Long> VALUES_BY_KEY_2 = new HashMap<>();
   private final Queue<Runnable> queue = new ArrayDeque<>();
   private final Set<BlockPos> values = new HashSet<>();
   private final Set<BlockPos> values2 = new HashSet<>();
   private final Map<BlockPos, ServerDHelper.ServerDHelperState2> valuesByKey = new HashMap<>();
   private final Map<BlockPos, Long> valuesByKey2 = new HashMap<>();
   private final Map<String, Integer> valuesByKey3 = new HashMap<>();
   private BlockPos blockPos = null;
   private BlockPos blockPos2 = null;
   private ServerDHelper.ServerDHelperState3 serverDHelperState3 = ServerDHelper.ServerDHelperState3.IDLE;
   private ServerDHelper.ServerDHelperState serverDHelperState = ServerDHelper.ServerDHelperState.IDLE;
   private final DualTimer dualTimer = new DualTimer();
   private final DualTimer dualTimer2 = new DualTimer();
   private final DualTimer dualTimer3 = new DualTimer();
   private final DualTimer dualTimer4 = new DualTimer();
   private final Set<BlockPos> values3 = new HashSet<>();
   private final Map<BlockPos, Long> valuesByKey4 = new HashMap<>();
   private int intValue = 0;
   private boolean flag = true;
   private boolean flag2 = false;
   private final DualTimer dualTimer5 = new DualTimer();
   private String nA = "N/A";
   private long timestamp = 500L;
   private static final long TIMESTAMP = 45000L;
   private boolean flag3 = false;
   private long timestamp2 = 0L;
   private GenericContainerScreen genericContainerScreen;
   private static final int[] INTS = new int[]{10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34};
   private static final Pattern PATTERN = Pattern.compile("(\\d{1,2}):(\\d{1,2})");
   private static final Pattern PATTERN_2 = Pattern.compile("(\\d{1,2}):(\\d{2})(?::(\\d{2}))?");
   private static final Pattern PATTERN_3 = Pattern.compile("(\\d+)\\s*(с|s|сек|sec)");
   private static final double DOUBLE_VALUE = 2000.0;
   private static final double DOUBLE_VALUE_2 = 2000.0;
   private static final double DOUBLE_VALUE_3 = 62500.0;
   private static final int INT_VALUE = 1024;
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
   private static final RenderLayer RENDER_LAYER = RenderLayer.of("chest_esp_box", 1024, false, true, RENDER_PIPELINE, MultiPhaseParameters.builder().build(false));

   public ServerDHelper() {
      this.addSettings(
         new Setting[]{
            this.rezhim,
            this.rezhimRaboty,
            this.skladyvatDropVKlan,
            this.rezhim2,
            this.bind,
            this.bindNaUstSunduka,
            this.predmetyDlyaLuta,
            this.ustanovkaTochkiNaSunduk,
            this.rotatsiyaNaSunduk,
            this.neOtobrazhatEkran
         }
      );
   }

   @Override
   public void onEnable() {
      super.onEnable();
      if (CLIENT.options != null) {
         this.flag = CLIENT.options.pauseOnLostFocus;
         CLIENT.options.pauseOnLostFocus = false;
      }
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.queue.clear();
      this.valuesByKey.clear();
      this.valuesByKey2.clear();
      if (CLIENT.options != null) {
         CLIENT.options.pauseOnLostFocus = this.flag;
      }

      if (!this.values2.isEmpty()) {
         GpsCommand.vector2f = new Vector2f(Float.MAX_VALUE, Float.MAX_VALUE);
         this.values2.clear();
      }

      this.serverDHelperState3 = ServerDHelper.ServerDHelperState3.IDLE;
      this.serverDHelperState = ServerDHelper.ServerDHelperState.IDLE;
      this.blockPos2 = null;
      this.intValue = 0;
      this.genericContainerScreen = null;
      this.valuesByKey4.clear();
      this.invoke6();
      this.flag2 = false;
   }

   @EventHandler
   public void onScreenOpen(ScreenOpenEvent screenOpenEvent) {
      if (this.neOtobrazhatEkran.isEnabled() && screenOpenEvent.getScreen() instanceof GenericContainerScreen genericContainerScreen) {
         this.genericContainerScreen = genericContainerScreen;
         screenOpenEvent.invoke();
      }
   }

   @EventHandler
   public void onWorldJoin(WorldJoinEvent worldJoinEvent) {
      this.values.clear();
      this.values2.clear();
      this.values3.clear();
      this.valuesByKey4.clear();
      this.blockPos2 = null;
      this.serverDHelperState = ServerDHelper.ServerDHelperState.IDLE;
      this.intValue = 0;
      this.genericContainerScreen = null;
      this.queue.clear();
      this.valuesByKey.clear();
      this.valuesByKey2.clear();
      this.invoke6();
   }

   private boolean check() {
      if (CLIENT.player == null) {
         return false;
      } else {
         double doubleValue = CLIENT.player.getX();
         double doubleValue2 = CLIENT.player.getY();
         double doubleValue3 = CLIENT.player.getZ();
         return !this.rezhim.is("Варден")
            ? (doubleValue - 2000.0) * (doubleValue - 2000.0) + (doubleValue3 - 2000.0) * (doubleValue3 - 2000.0) <= 62500.0
            : doubleValue >= -2072.0 && doubleValue <= -1928.0 && doubleValue2 >= -56.0 && doubleValue2 <= -29.0 && doubleValue3 >= -2071.0 && doubleValue3 <= -1929.0;
      }
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player != null && CLIENT.world != null) {
         if (this.flag2) {
            if (this.dualTimer5.check5(2000L)) {
               if (!"N/A".equals(this.nA)) {
                  CLIENT.player.networkHandler.sendChatCommand("an" + this.nA);
                  ChatUtil.sendClientMessage("§8[§6ServerDHelper§8] §aВозвращаемся на Анархию-" + this.nA);
               } else {
                  ChatUtil.sendClientMessage("§8[§6ServerDHelper§8] §cНе удалось определить номер анархии для реконнекта!");
               }

               this.flag2 = false;
               if (this.rezhimRaboty.is("Складывающий")) {
                  this.timestamp = 4000L;
                  this.serverDHelperState3 = ServerDHelper.ServerDHelperState3.REOPEN_CLAN;
                  this.dualTimer.invoke();
               }
            }
         } else {
            boolean flag = this.check();
            if (!this.rezhimRaboty.is("Лутающий") || flag) {
               if (this.rezhimRaboty.is("Лутающий")) {
                  this.invoke();
                  if (this.flag3 && !this.check3() && System.currentTimeMillis() > this.timestamp2) {
                     CLIENT.player.networkHandler.sendChatCommand("clan storage");
                     this.flag3 = false;
                  }
               } else if (this.rezhimRaboty.is("Складывающий") && this.blockPos != null) {
                  this.invoke2();
               }

               GenericContainerScreen genericContainerScreen2 = this.resolve();
               if (genericContainerScreen2 != null) {
                  GenericContainerScreenHandler genericContainerScreenHandler2 = (GenericContainerScreenHandler)genericContainerScreen2.getScreenHandler();
                  String text = genericContainerScreen2.getTitle().getString().toLowerCase().replaceAll("§.", "").trim();
                  boolean flag2 = text.contains("клан") || text.contains("clan") || text.contains("хранилище");
                  if (this.rezhimRaboty.is("Лутающий")) {
                     if (flag2) {
                        this.invoke11(genericContainerScreenHandler2);
                     } else {
                        boolean flag3 = this.rezhim.is("Варден")
                           ? text.equals("сундук") || text.equals("большой сундук") || text.equals("chest") || text.equals("large chest")
                           : text.equals("бочка") || text.equals("barrel");
                        if (flag3) {
                           this.invoke10(genericContainerScreenHandler2);
                        }
                     }
                  } else if (this.rezhimRaboty.is("Складывающий")) {
                     if (flag2) {
                        this.invoke8(genericContainerScreenHandler2);
                     } else {
                        this.invoke9(genericContainerScreenHandler2);
                     }
                  }
               }
            }
         }
      }
   }

   private GenericContainerScreen resolve() {
      GenericContainerScreen genericContainerScreen3 = ScreenUtil.resolve(CLIENT, this.genericContainerScreen, GenericContainerScreen.class);
      if (genericContainerScreen3 == null) {
         this.genericContainerScreen = null;
      }

      return genericContainerScreen3;
   }

   private boolean check2() {
      return this.resolve() != null;
   }

   private boolean check3() {
      return ScreenUtil.check3(CLIENT, this.genericContainerScreen) || ScreenUtil.check2(CLIENT);
   }

   private void invoke() {
      if (!this.rotatsiyaNaSunduk.isEnabled()
         || !this.rezhimRaboty.is("Лутающий")
         || CLIENT.player == null
         || CLIENT.world == null
         || CLIENT.interactionManager == null) {
         this.invoke3();
      } else if (this.check2()) {
         if (this.blockPos2 != null) {
            this.invoke4(this.blockPos2);
         }

         this.invoke3();
      } else {
         if (this.serverDHelperState == ServerDHelper.ServerDHelperState.IDLE) {
            BlockPos blockPos2 = this.resolve2();
            if (blockPos2 == null) {
               return;
            }

            this.blockPos2 = blockPos2;
            this.serverDHelperState = ServerDHelper.ServerDHelperState.ROTATING;
            this.intValue = 0;
            this.dualTimer2.invoke();
         }

         if (this.blockPos2 != null && this.check7(this.blockPos2)) {
            switch (this.serverDHelperState) {
               case IDLE:
               default:
                  break;
               case ROTATING:
                  Rotation rotation = this.resolve5(Vec3d.ofCenter(this.blockPos2));
                  RotationController.invoke3(rotation, 999.0F, 999.0F, 60.0F, 60.0F, 2, 3, false);
                  if (new Rotation(CLIENT.player).measure(rotation) < 3.0F || this.dualTimer2.check5(120L)) {
                     this.invoke6();
                     this.serverDHelperState = ServerDHelper.ServerDHelperState.OPENING;
                     this.dualTimer2.invoke();
                  }
                  break;
               case OPENING:
                  if (this.check5()) {
                     this.dualTimer2.invoke();
                     return;
                  }

                  if (this.dualTimer2.check5(90L)) {
                     this.invoke7(this.blockPos2);
                     this.intValue++;
                     this.serverDHelperState = ServerDHelper.ServerDHelperState.WAITING_SCREEN;
                     this.dualTimer2.invoke();
                  }
                  break;
               case WAITING_SCREEN:
                  if (this.dualTimer2.check5(1400L)) {
                     if (this.intValue >= 2) {
                        this.invoke4(this.blockPos2);
                        this.invoke3();
                     } else {
                        this.serverDHelperState = ServerDHelper.ServerDHelperState.ROTATING;
                        this.dualTimer2.invoke();
                     }
                  }
            }
         } else {
            this.invoke3();
         }
      }
   }

   @EventHandler
   public void onRawInput(RawInputEvent rawInputEvent) {
      if (CLIENT.player != null) {
         if (this.rezhimRaboty.is("Складывающий") && rawInputEvent.getKeyCode() == this.bindNaUstSunduka.getKeyCode()) {
            if (CLIENT.crosshairTarget instanceof BlockHitResult blockHitResult) {
               BlockPos blockPos3 = blockHitResult.getBlockPos();
               if (CLIENT.world == null
                  || !(CLIENT.world.getBlockEntity(blockPos3) instanceof ChestBlockEntity)
                     && !(CLIENT.world.getBlockEntity(blockPos3) instanceof BarrelBlockEntity)) {
                  ChatUtil.sendClientMessage("§8[§6ServerDHelper§8] §cСмотрите на сундук или бочку!");
               } else {
                  this.blockPos = blockPos3;
                  ChatUtil.sendClientMessage("§8[§6ServerDHelper§8] §aБазовый сундук установлен: " + blockPos3.toShortString());
               }
            }
         } else if (!this.rezhimRaboty.is("Лутающий") || this.check()) {
            if (this.rezhimRaboty.is("Лутающий")
               && this.skladyvatDropVKlan.isEnabled()
               && this.rezhim2.is("По бинду")
               && rawInputEvent.getKeyCode() == this.bind.getKeyCode()
               && !this.valuesByKey3.isEmpty()) {
               CLIENT.player.networkHandler.sendChatCommand("clan storage");
            }
         }
      }
   }

   private void invoke2() {
      if (!this.check3() && CLIENT.player != null && CLIENT.interactionManager != null) {
         switch (this.serverDHelperState3) {
            case IDLE:
            default:
               break;
            case ROTATING:
               Rotation rotation2 = this.resolve5(
                  new Vec3d(this.blockPos.getX() + 0.5, this.blockPos.getY() + 0.5, this.blockPos.getZ() + 0.5)
               );
               RotationController.invoke3(rotation2, 35.0F, 35.0F, 35.0F, 35.0F, 20, 1, false);
               if (new Rotation(CLIENT.player).measure(rotation2) < 4.0F) {
                  this.invoke6();
                  this.serverDHelperState3 = ServerDHelper.ServerDHelperState3.OPENING;
                  this.dualTimer.invoke();
               }
               break;
            case OPENING:
               if (this.check5()) {
                  this.dualTimer.invoke();
               } else if (this.dualTimer.check5(100L)) {
                  this.invoke7(this.blockPos);
                  this.serverDHelperState3 = ServerDHelper.ServerDHelperState3.IDLE;
               }
               break;
            case REOPEN_CLAN:
               if (this.dualTimer.check5(this.timestamp)) {
                  CLIENT.player.networkHandler.sendChatCommand("clan storage");
                  this.serverDHelperState3 = ServerDHelper.ServerDHelperState3.IDLE;
                  this.timestamp = 500L;
               }
         }
      }
   }

   private BlockPos resolve2() {
      long longValue = System.currentTimeMillis();
      this.invoke5(longValue);
      BlockPos blockPos4 = null;
      double doubleValue4 = Double.MAX_VALUE;

      for (Entry entry2 : new HashMap<>(VALUES_BY_KEY_2).entrySet()) {
         BlockPos blockPos5 = (BlockPos)entry2.getKey();
         if ((Long)entry2.getValue() > longValue && !this.values3.contains(blockPos5) && !this.check4(blockPos5, longValue) && this.check7(blockPos5)) {
            double doubleValue5 = CLIENT.player.squaredDistanceTo(Vec3d.ofCenter(blockPos5));
            if (!(doubleValue5 > 36.0) && doubleValue5 < doubleValue4) {
               doubleValue4 = doubleValue5;
               blockPos4 = blockPos5.toImmutable();
            }
         }
      }

      return blockPos4;
   }

   private void invoke3() {
      boolean flag4 = this.serverDHelperState != ServerDHelper.ServerDHelperState.IDLE || this.blockPos2 != null || this.intValue != 0;
      this.blockPos2 = null;
      this.serverDHelperState = ServerDHelper.ServerDHelperState.IDLE;
      this.intValue = 0;
      if (flag4) {
         this.invoke6();
      }
   }

   private void invoke4(BlockPos blockPos) {
      if (blockPos != null) {
         BlockPos blockPos6 = blockPos.toImmutable();
         this.values3.add(blockPos6);
         this.valuesByKey4.put(blockPos6, System.currentTimeMillis() + 45000L);
         VALUES_BY_KEY_2.remove(blockPos6);
         this.values2.remove(blockPos6);
      }
   }

   private boolean check4(BlockPos blockPos, long l) {
      Long longValue2 = this.valuesByKey4.get(blockPos);
      if (longValue2 == null) {
         return false;
      } else if (longValue2 <= l) {
         this.valuesByKey4.remove(blockPos);
         return false;
      } else {
         return true;
      }
   }

   private void invoke5(long l) {
      this.valuesByKey4.entrySet().removeIf(entry -> entry.getValue() <= l);
   }

   private void invoke6() {
      RotationController.rotationControllerState2 = RotationController.RotationControllerState2.IDLE;
      RotationController.intValue = 0;
      RotationController.rotation = null;
      FreeLookController.active = false;
   }

   private boolean check5() {
      int intValue = CLIENT.player.getInventory().getSelectedSlot();
      ItemStack itemStack2 = (ItemStack)CLIENT.player.getInventory().getMainStacks().get(intValue);
      if (!this.check6(itemStack2)) {
         return false;
      } else {
         for (int intValue2 = 0; intValue2 < 9; intValue2++) {
            ItemStack itemStack3 = (ItemStack)CLIENT.player.getInventory().getMainStacks().get(intValue2);
            if (itemStack3.isEmpty() || !this.check6(itemStack3)) {
               CLIENT.player.getInventory().setSelectedSlot(intValue2);
               return true;
            }
         }

         return false;
      }
   }

   private boolean check6(ItemStack itemStack) {
      if (itemStack.isEmpty()) {
         return false;
      } else {
         String text2 = itemStack.getName().getString();
         return itemStack.getItem() == Items.TRIPWIRE_HOOK || text2.contains("[★]") || text2.contains("[в\u0098…]");
      }
   }

   private void invoke7(BlockPos blockPos) {
      if (CLIENT.player != null && CLIENT.interactionManager != null) {
         Direction direction = this.resolve3(blockPos);
         Vec3d vec3d2 = new Vec3d(
            blockPos.getX() + 0.5 + direction.getOffsetX() * 0.5, blockPos.getY() + 0.5 + direction.getOffsetY() * 0.5, blockPos.getZ() + 0.5 + direction.getOffsetZ() * 0.5
         );
         BlockHitResult blockHitResult2 = new BlockHitResult(vec3d2, direction, blockPos, false);
         CLIENT.player.swingHand(Hand.MAIN_HAND);
         CLIENT.interactionManager.interactBlock(CLIENT.player, Hand.MAIN_HAND, blockHitResult2);
      }
   }

   private Direction resolve3(BlockPos blockPos) {
      Vec3d vec3d3 = Vec3d.ofCenter(blockPos);
      Vec3d vec3d4 = CLIENT.player.getEyePos().subtract(vec3d3);
      return Direction.getFacing(vec3d4.x, vec3d4.y, vec3d4.z);
   }

   private boolean check7(BlockPos blockPos) {
      if (CLIENT.world == null) {
         return false;
      } else {
         BlockEntity blockEntity = CLIENT.world.getBlockEntity(blockPos);
         return this.rezhim.is("Варден") ? blockEntity instanceof ChestBlockEntity : blockEntity instanceof BarrelBlockEntity;
      }
   }

   private void invoke8(GenericContainerScreenHandler genericContainerScreenHandler) {
      if (CLIENT.player != null && CLIENT.interactionManager != null) {
         if (this.dualTimer3.check5(100L)) {
            boolean flag5 = true;
            boolean flag6 = false;

            for (int intValue3 = 0; intValue3 < 36; intValue3++) {
               ItemStack itemStack4 = (ItemStack)CLIENT.player.getInventory().getMainStacks().get(intValue3);
               if (itemStack4.isEmpty()) {
                  flag5 = false;
               } else {
                  flag6 = true;
               }
            }

            boolean flag7 = false;
            boolean flag8 = false;

            for (int intValue4 : INTS) {
               if (intValue4 < genericContainerScreenHandler.slots.size()) {
                  Slot slot = (Slot)genericContainerScreenHandler.slots.get(intValue4);
                  if (slot.hasStack() && slot.getStack().getItem() != Items.AIR) {
                     flag8 = true;
                     if (!flag5) {
                        CLIENT.interactionManager.clickSlot(genericContainerScreenHandler.syncId, intValue4, 0, SlotActionType.QUICK_MOVE, CLIENT.player);
                        flag7 = true;
                     }
                  }
               }
            }

            if (flag7) {
               this.dualTimer3.invoke();
            } else if (flag6 && (flag5 || !flag8)) {
               CLIENT.player.closeHandledScreen();
               this.serverDHelperState3 = ServerDHelper.ServerDHelperState3.ROTATING;
            }
         }
      }
   }

   private void invoke9(GenericContainerScreenHandler genericContainerScreenHandler) {
      if (CLIENT.player != null && CLIENT.interactionManager != null) {
         int intValue5 = genericContainerScreenHandler.slots.size() - 36;

         for (int intValue6 = intValue5; intValue6 < genericContainerScreenHandler.slots.size(); intValue6++) {
            Slot slot2 = (Slot)genericContainerScreenHandler.slots.get(intValue6);
            if (slot2.hasStack() && slot2.getStack().getItem() != Items.AIR) {
               if (this.dualTimer4.check5(150L)) {
                  CLIENT.interactionManager.clickSlot(genericContainerScreenHandler.syncId, intValue6, 0, SlotActionType.QUICK_MOVE, CLIENT.player);
                  this.dualTimer4.invoke();
               }

               return;
            }
         }

         CLIENT.player.closeHandledScreen();
         this.serverDHelperState3 = ServerDHelper.ServerDHelperState3.REOPEN_CLAN;
         this.dualTimer.invoke();
      }
   }

   private void invoke10(GenericContainerScreenHandler genericContainerScreenHandler) {
      if (CLIENT.player != null && CLIENT.interactionManager != null) {
         int intValue7 = genericContainerScreenHandler.slots.size() - 36;

         for (int intValue8 = 0; intValue8 < intValue7; intValue8++) {
            Slot slot3 = (Slot)genericContainerScreenHandler.slots.get(intValue8);
            if (slot3.hasStack() && this.check8(slot3.getStack())) {
               if (this.dualTimer3.check5(50L)) {
                  ItemStack itemStack5 = slot3.getStack().copy();
                  String text3 = this.resolve6(itemStack5);
                  this.valuesByKey3.put(text3, this.valuesByKey3.getOrDefault(text3, 0) + itemStack5.getCount());
                  CLIENT.interactionManager.clickSlot(genericContainerScreenHandler.syncId, intValue8, 0, SlotActionType.QUICK_MOVE, CLIENT.player);
                  this.dualTimer3.invoke();
               }

               return;
            }
         }

         CLIENT.player.closeHandledScreen();
         this.invoke4(this.blockPos2);
         this.invoke3();
         if (this.skladyvatDropVKlan.isEnabled() && this.rezhim2.is("Авто") && !this.valuesByKey3.isEmpty()) {
            this.flag3 = true;
            this.timestamp2 = System.currentTimeMillis() + 400L;
         }
      }
   }

   private void invoke11(GenericContainerScreenHandler genericContainerScreenHandler) {
      if (CLIENT.player != null && CLIENT.interactionManager != null) {
         if (!this.queue.isEmpty()) {
            if (this.dualTimer3.check5(50L)) {
               this.queue.poll().run();
               this.dualTimer3.invoke();
            }
         } else if (this.valuesByKey3.isEmpty()) {
            CLIENT.player.closeHandledScreen();
         } else {
            int intValue9 = genericContainerScreenHandler.slots.size() - 36;
            boolean flag9 = false;

            for (int intValue10 = intValue9; intValue10 < genericContainerScreenHandler.slots.size(); intValue10++) {
               Slot slot4 = (Slot)genericContainerScreenHandler.slots.get(intValue10);
               if (slot4.hasStack()) {
                  String text4 = this.resolve6(slot4.getStack());
                  int intValue11 = this.valuesByKey3.getOrDefault(text4, 0);
                  if (intValue11 > 0) {
                     flag9 = true;
                     int intValue12 = slot4.getStack().getCount();
                     int var4Copy = intValue10;
                     if (intValue12 <= intValue11) {
                        this.queue
                           .add(
                              () -> CLIENT.interactionManager
                                 .clickSlot(genericContainerScreenHandler.syncId, var4Copy, 0, SlotActionType.QUICK_MOVE, CLIENT.player)
                           );
                        int intValue13 = intValue11 - intValue12;
                        if (intValue13 <= 0) {
                           this.valuesByKey3.remove(text4);
                        } else {
                           this.valuesByKey3.put(text4, intValue13);
                        }
                     } else {
                        int intValue14 = -1;

                        for (int intValue15 = 0; intValue15 < intValue9; intValue15++) {
                           if (!((Slot)genericContainerScreenHandler.slots.get(intValue15)).hasStack()) {
                              intValue14 = intValue15;
                              break;
                           }
                        }

                        if (intValue14 == -1) {
                           this.valuesByKey3.clear();
                           this.queue.clear();
                           CLIENT.player.closeHandledScreen();
                           return;
                        }

                        int intValue16 = intValue14;
                        int intValue17 = intValue10;
                        this.queue
                           .add(
                              () -> CLIENT.interactionManager
                                 .clickSlot(genericContainerScreenHandler.syncId, intValue17, 0, SlotActionType.PICKUP, CLIENT.player)
                           );
                        if (intValue11 <= intValue12 / 2) {
                           for (int intValue18 = 0; intValue18 < intValue11; intValue18++) {
                              this.queue
                                 .add(
                                    () -> CLIENT.interactionManager
                                       .clickSlot(genericContainerScreenHandler.syncId, intValue16, 1, SlotActionType.PICKUP, CLIENT.player)
                                 );
                           }

                           this.queue
                              .add(
                                 () -> CLIENT.interactionManager
                                    .clickSlot(genericContainerScreenHandler.syncId, intValue17, 0, SlotActionType.PICKUP, CLIENT.player)
                              );
                        } else {
                           int intValue19 = intValue12 - intValue11;

                           for (int intValue20 = 0; intValue20 < intValue19; intValue20++) {
                              this.queue
                                 .add(
                                    () -> CLIENT.interactionManager
                                       .clickSlot(genericContainerScreenHandler.syncId, intValue17, 1, SlotActionType.PICKUP, CLIENT.player)
                                 );
                           }

                           this.queue
                              .add(
                                 () -> CLIENT.interactionManager
                                    .clickSlot(genericContainerScreenHandler.syncId, intValue16, 0, SlotActionType.PICKUP, CLIENT.player)
                              );
                        }

                        this.valuesByKey3.remove(text4);
                     }

                     return;
                  }
               }
            }

            if (!flag9) {
               this.valuesByKey3.clear();
               this.queue.clear();
               CLIENT.player.closeHandledScreen();
            }
         }
      }
   }

   @EventHandler
   public void onRender3D(Render3DEvent render3DEvent) {
      if (CLIENT.world != null && CLIENT.player != null) {
         boolean flag10 = this.check();
         if (!this.rezhimRaboty.is("Лутающий") || flag10) {
            Immediate immediate = WorldRenderBuffer.getIMMEDIATE();

            try {
               Vec3d vec3d5 = CLIENT.gameRenderer.getCamera().getPos();
               Matrix4f matrix4f2 = render3DEvent.getMatrixStack().peek().getPositionMatrix();
               VertexConsumer vertexConsumer2 = immediate.getBuffer(RENDER_LAYER);
               if (!flag10) {
                  if (this.blockPos != null) {
                     this.invoke14(vertexConsumer2, matrix4f2, this.blockPos, vec3d5, new Color(150, 50, 255, 120), new Color(150, 50, 255, 0));
                  }

                  return;
               }

               ChunkPos chunkPos = CLIENT.player.getChunkPos();
               int intValue21 = (Integer)CLIENT.options.getViewDistance().getValue();
               HashSet hashSet = new HashSet();
               boolean flag11 = this.rezhim.is("Варден");

               for (int intValue22 = chunkPos.x - intValue21; intValue22 <= chunkPos.x + intValue21; intValue22++) {
                  for (int intValue23 = chunkPos.z - intValue21; intValue23 <= chunkPos.z + intValue21; intValue23++) {
                     WorldChunk worldChunk = CLIENT.world.getChunk(intValue22, intValue23);
                     if (worldChunk != null) {
                        for (BlockEntity blockEntity2 : worldChunk.getBlockEntities().values()) {
                           BlockPos blockPos7 = blockEntity2.getPos();
                           if (this.blockPos != null && blockPos7.equals(this.blockPos)) {
                              this.invoke14(vertexConsumer2, matrix4f2, this.blockPos, vec3d5, new Color(150, 50, 255, 120), new Color(150, 50, 255, 0));
                           } else {
                              boolean flag12 = flag11 ? blockEntity2 instanceof ChestBlockEntity : blockEntity2 instanceof BarrelBlockEntity;
                              if (flag12) {
                                 double doubleValue6 = blockPos7.getX() + 0.5;
                                 double doubleValue7 = blockPos7.getY() + 0.5;
                                 double doubleValue8 = blockPos7.getZ() + 0.5;
                                 Iterator iterator = CLIENT.world.getEntities().iterator();

                                 while (true) {
                                    if (iterator.hasNext()) {
                                       Entity entity = (Entity)iterator.next();
                                       if (!(entity instanceof ArmorStandEntity) || !(entity.squaredDistanceTo(doubleValue6, doubleValue7, doubleValue8) <= 4.0)) {
                                          continue;
                                       }

                                       long longValue3 = this.compute3(entity.getName().getString(), flag11);
                                       if (longValue3 == -1L) {
                                          continue;
                                       }

                                       VALUES_BY_KEY.put(blockPos7, System.currentTimeMillis() + longValue3);
                                       this.valuesByKey2.merge(blockPos7, longValue3, Long::max);
                                       if (!this.check4(blockPos7, System.currentTimeMillis())) {
                                          this.values3.remove(blockPos7);
                                       }
                                    }

                                    boolean flag13 = false;
                                    long longValue4 = 0L;
                                    if (VALUES_BY_KEY.containsKey(blockPos7)) {
                                       longValue4 = VALUES_BY_KEY.get(blockPos7) - System.currentTimeMillis();
                                       if (longValue4 > 0L) {
                                          flag13 = true;
                                          hashSet.add(blockPos7);
                                          this.values.add(blockPos7);
                                          if (longValue4 <= 5000L && this.ustanovkaTochkiNaSunduk.isEnabled() && !this.values2.contains(blockPos7)) {
                                             GpsCommand.invoke2(blockPos7.getX(), blockPos7.getZ());
                                             this.values2.add(blockPos7);
                                          }
                                       } else {
                                          VALUES_BY_KEY.remove(blockPos7);
                                          this.valuesByKey2.remove(blockPos7);
                                          VALUES_BY_KEY_2.put(blockPos7, System.currentTimeMillis() + 45000L);
                                       }
                                    }

                                    if (VALUES_BY_KEY_2.containsKey(blockPos7)) {
                                       if (VALUES_BY_KEY_2.get(blockPos7) - System.currentTimeMillis() > 0L) {
                                          hashSet.add(blockPos7);
                                          this.values.add(blockPos7);
                                          if (this.values2.contains(blockPos7) && CLIENT.player.squaredDistanceTo(doubleValue6, doubleValue7, doubleValue8) < 20.25) {
                                             this.invoke13(blockPos7, "§aВы у цели. Метка снята.");
                                          }
                                       } else {
                                          VALUES_BY_KEY_2.remove(blockPos7);
                                          if (this.values2.contains(blockPos7)) {
                                             this.invoke13(blockPos7, "§cВремя вышло. Метка снята.");
                                          }
                                       }
                                    }

                                    Color color3;
                                    Color color4;
                                    if (flag13) {
                                       float floatValue = (float)(Math.sin(System.currentTimeMillis() / 150.0) * 0.15 + 0.85);
                                       if (longValue4 <= 20000L) {
                                          float floatValue2 = (float)(Math.sin(System.currentTimeMillis() / 60.0) * 0.5 + 0.5);
                                          color3 = new Color(255, 140, 0, Math.min(255, (int)((80.0F + 150.0F * floatValue2) * floatValue)));
                                          color4 = new Color(255, 140, 0, 0);
                                       } else {
                                          color3 = new Color(255, 0, 0, Math.min(255, (int)(150.0F * floatValue)));
                                          color4 = new Color(255, 0, 0, 0);
                                       }
                                    } else {
                                       color3 = new Color(0, 255, 150, 120);
                                       color4 = new Color(0, 255, 150, 0);
                                    }

                                    this.invoke14(vertexConsumer2, matrix4f2, blockPos7, vec3d5, color3, color4);
                                    break;
                                 }
                              }
                           }
                        }
                     }
                  }
               }

               Iterator iterator2 = this.values.iterator();

               while (iterator2.hasNext()) {
                  BlockPos blockPos8 = (BlockPos)iterator2.next();
                  if (!hashSet.contains(blockPos8)) {
                     iterator2.remove();
                     if (this.values2.contains(blockPos8)) {
                        this.values2.remove(blockPos8);
                        if (GpsCommand.vector2f.getX() == blockPos8.getX() && GpsCommand.vector2f.getY() == blockPos8.getZ()) {
                           GpsCommand.vector2f = new Vector2f(Float.MAX_VALUE, Float.MAX_VALUE);
                        }
                     }
                  }
               }
            } finally {
               WorldRenderBuffer.invoke();
            }
         }
      }
   }

   @EventHandler
   public void onHudRender(HudRenderEvent hudRenderEvent) {
      if (CLIENT.world != null && CLIENT.player != null && this.check()) {
         RenderManager renderManager = hudRenderEvent.getRenderManager();
         Camera camera = CLIENT.gameRenderer.getCamera();
         Vec3d vec3d6 = camera.getPos();
         long longValue5 = System.currentTimeMillis();
         HashSet hashSet2 = new HashSet();

         for (Entry entry3 : new HashMap<>(VALUES_BY_KEY).entrySet()) {
            BlockPos blockPos9 = (BlockPos)entry3.getKey();
            long longValue6 = (Long)entry3.getValue() - longValue5;
            if (longValue6 > 0L) {
               BlockEntity blockEntity3 = CLIENT.world.getBlockEntity(blockPos9);
               boolean flag14 = this.rezhim.options.isEmpty() || this.rezhim.getValue().equalsIgnoreCase(this.rezhim.options.get(0));
               boolean flag15 = flag14 ? blockEntity3 instanceof ChestBlockEntity : blockEntity3 instanceof BarrelBlockEntity;
               if (flag15) {
                  Vec3d vec3d7 = new Vec3d(blockPos9.getX() + 0.5, blockPos9.getY() + 1.28, blockPos9.getZ() + 0.5);
                  if (!(vec3d7.squaredDistanceTo(vec3d6) < 1.0E-6)) {
                     Vec3d vec3d8 = MathUtils.resolve(vec3d7);
                     if (vec3d8 != null && !(vec3d8.z <= 0.001F) && !(vec3d8.z > 1.0)) {
                        double doubleValue9 = vec3d6.distanceTo(vec3d7);
                        long longValue7 = Math.max(longValue6, this.valuesByKey2.getOrDefault(blockPos9, longValue6));
                        float floatValue3 = MathHelper.clamp((float)longValue6 / (float)Math.max(1L, longValue7), 0.0F, 1.0F);
                        ServerDHelper.ServerDHelperState2 serverDHelperState2 = this.valuesByKey.computeIfAbsent(blockPos9, blockPos -> new ServerDHelper.ServerDHelperState2(floatValue3));
                        serverDHelperState2.invoke(true, floatValue3);
                        hashSet2.add(blockPos9);
                        this.invoke12(renderManager, serverDHelperState2, (float)vec3d8.x, (float)vec3d8.y, (float)doubleValue9, longValue6);
                     }
                  }
               }
            }
         }

         Iterator iterator3 = this.valuesByKey.entrySet().iterator();

         while (iterator3.hasNext()) {
            Entry entry4 = (Entry)iterator3.next();
            if (!hashSet2.contains(entry4.getKey())) {
               ((ServerDHelper.ServerDHelperState2)entry4.getValue()).invoke(false, 0.0F);
               if (((ServerDHelper.ServerDHelperState2)entry4.getValue()).floatValue <= 0.02F) {
                  iterator3.remove();
               }
            }
         }

         this.valuesByKey2.keySet().removeIf(blockPos -> !VALUES_BY_KEY.containsKey(blockPos));
      } else {
         this.valuesByKey.clear();
      }
   }

   private void invoke12(RenderManager renderManager2, ServerDHelper.ServerDHelperState2 serverDHelperState22, float f, float g, float h, long l) {
      float floatValue4 = this.measure(serverDHelperState22.floatValue);
      if (!(floatValue4 <= 0.03F)) {
         float floatValue5 = (float)MathHelper.clamp(16.0 / Math.max((double)h, 12.0), 0.75, 1.15);
         float floatValue6 = 6.0F * floatValue5;
         float floatValue7 = 4.0F * floatValue5;
         float floatValue8 = 23.0F * floatValue5;
         float floatValue9 = 22.0F * floatValue5;
         float floatValue10 = 18.0F * floatValue5;
         float floatValue11 = 4.0F * floatValue5;
         String text5 = "КД";
         String text6 = this.resolve4(l);
         float floatValue12 = RenderManager.resolve7(FontRegistry.fontObject, text5, floatValue10).floatValue;
         float floatValue13 = RenderManager.resolve7(FontRegistry.fontObject4, text6, floatValue9).floatValue;
         float floatValue14 = Math.max(48.0F * floatValue5, floatValue11 + floatValue13 + floatValue7 * 2.0F);
         float floatValue15 = 0.88F + 0.12F * floatValue4;
         float floatValue16 = f - floatValue14 / 2.0F;
         float floatValue17 = g - floatValue8 - 8.0F * floatValue5 - (1.0F - floatValue4) * 7.0F * floatValue5;
         float floatValue18 = floatValue16 + floatValue14 / 2.0F;
         float floatValue19 = floatValue17 + floatValue8 / 2.0F;
         float floatValue20 = 1.0F - MathHelper.clamp((float)l / 20000.0F, 0.0F, 1.0F);
         float floatValue21 = floatValue20 * (0.5F + 0.5F * (float)Math.sin(System.currentTimeMillis() / 90.0));
         int intValue24 = this.compute(RenderManager.RenderManagerState.compute32(255, 70, 70, 255), RenderManager.RenderManagerState.compute32(255, 175, 60, 255), floatValue21);
         int intValue25 = this.compute2(RenderManager.RenderManagerState.compute32(25, 25, 26, 235), floatValue4);
         int intValue26 = this.compute2(RenderManager.RenderManagerState.compute32(78, 78, 78, 176), floatValue4);
         int intValue27 = this.compute2(RenderManager.RenderManagerState.compute32(160, 160, 165, 255), floatValue4);
         int intValue28 = this.compute2(RenderManager.RenderManagerState.compute32(245, 245, 245, 255), floatValue4);
         int intValue29 = this.compute2(RenderManager.RenderManagerState.compute32(0, 0, 0, 105), floatValue4);
         int intValue30 = this.compute2(intValue24, floatValue4);
         renderManager2.invoke63(floatValue15, floatValue15, floatValue18, floatValue19);
         renderManager2.invoke5(floatValue16, floatValue17, floatValue14, floatValue8, floatValue6, intValue25);
         renderManager2.invoke28(floatValue16, floatValue17, floatValue14, floatValue8, floatValue6, intValue26, Math.max(1.0F, 0.8F * floatValue5));
         float floatValue22 = floatValue17 + 15.3F * floatValue5;
         float floatValue23 = floatValue16 + floatValue7;
         renderManager2.invoke69(FontRegistry.fontObject4, floatValue23 + floatValue11, floatValue22, floatValue9, text6, intValue28);
         renderManager2.invoke64();
      }
   }

   private String resolve4(long l) {
      long longValue8 = Math.max(0L, (l + 999L) / 1000L);
      long longValue9 = longValue8 / 3600L;
      long longValue10 = longValue8 % 3600L / 60L;
      long longValue11 = longValue8 % 60L;
      return longValue9 > 0L ? String.format(Locale.ROOT, "%d:%02d:%02d", longValue9, longValue10, longValue11) : String.format(Locale.ROOT, "%02d:%02d", longValue10, longValue11);
   }

   private float measure(float f) {
      float floatValue24 = MathHelper.clamp(f, 0.0F, 1.0F);
      return 1.0F - (float)Math.pow(1.0F - floatValue24, 3.0);
   }

   private int compute(int i, int j, float f) {
      float floatValue25 = MathHelper.clamp(f, 0.0F, 1.0F);
      int intValue31 = i >> 24 & 0xFF;
      int intValue32 = i >> 16 & 0xFF;
      int intValue33 = i >> 8 & 0xFF;
      int intValue34 = i & 0xFF;
      int intValue35 = j >> 24 & 0xFF;
      int intValue36 = j >> 16 & 0xFF;
      int intValue37 = j >> 8 & 0xFF;
      int intValue38 = j & 0xFF;
      int intValue39 = (int)(intValue31 + (intValue35 - intValue31) * floatValue25);
      int intValue40 = (int)(intValue32 + (intValue36 - intValue32) * floatValue25);
      int intValue41 = (int)(intValue33 + (intValue37 - intValue33) * floatValue25);
      int intValue42 = (int)(intValue34 + (intValue38 - intValue34) * floatValue25);
      return RenderManager.RenderManagerState.compute32(intValue40, intValue41, intValue42, intValue39);
   }

   private int compute2(int i, float f) {
      int intValue43 = i >> 24 & 0xFF;
      int intValue44 = i >> 16 & 0xFF;
      int intValue45 = i >> 8 & 0xFF;
      int intValue46 = i & 0xFF;
      int intValue47 = (int)MathHelper.clamp(intValue43 * f, 0.0F, 255.0F);
      return RenderManager.RenderManagerState.compute32(intValue44, intValue45, intValue46, intValue47);
   }

   private void invoke13(BlockPos blockPos, String string) {
      VALUES_BY_KEY_2.remove(blockPos);
      this.values2.remove(blockPos);
      if (GpsCommand.vector2f.getX() == blockPos.getX() && GpsCommand.vector2f.getY() == blockPos.getZ()) {
         GpsCommand.vector2f = new Vector2f(Float.MAX_VALUE, Float.MAX_VALUE);
         ChatUtil.sendClientMessage("§8[§6ServerDHelper§8] " + string);
      }
   }

   private Rotation resolve5(Vec3d vec3d) {
      if (CLIENT.player == null) {
         return new Rotation(0.0F, 0.0F);
      } else {
         Vec3d vec3d9 = CLIENT.player.getEyePos();
         double doubleValue10 = vec3d.x - vec3d9.x;
         double doubleValue11 = vec3d.y - vec3d9.y;
         double doubleValue12 = vec3d.z - vec3d9.z;
         float floatValue26 = (float)Math.toDegrees(Math.atan2(doubleValue12, doubleValue10)) - 90.0F;
         float floatValue27 = (float)(-Math.toDegrees(Math.atan2(doubleValue11, Math.sqrt(doubleValue10 * doubleValue10 + doubleValue12 * doubleValue12))));
         return new Rotation(floatValue26, floatValue27);
      }
   }

   private long compute3(String string, boolean bl) {
      if (bl) {
         Matcher matcher = PATTERN.matcher(string);
         if (matcher.find()) {
            try {
               return (Integer.parseInt(matcher.group(1)) * 60L + Integer.parseInt(matcher.group(2))) * 1000L;
            } catch (NumberFormatException numberFormatException) {
            }
         }
      } else {
         Matcher matcher2 = PATTERN_2.matcher(string);
         if (matcher2.find()) {
            try {
               int intValue48 = Integer.parseInt(matcher2.group(1));
               int intValue49 = Integer.parseInt(matcher2.group(2));
               return matcher2.group(3) != null ? (intValue48 * 3600L + intValue49 * 60L + Integer.parseInt(matcher2.group(3))) * 1000L : (intValue48 * 60L + intValue49) * 1000L;
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
      }

      return -1L;
   }

   private void invoke14(VertexConsumer vertexConsumer, Matrix4f matrix4f, BlockPos blockPos, Vec3d vec3d, Color color, Color color2) {
      float floatValue28 = (float)(blockPos.getX() - vec3d.x);
      float floatValue29 = (float)(blockPos.getY() - vec3d.y);
      float floatValue30 = (float)(blockPos.getZ() - vec3d.z);
      float floatValue31 = (float)(blockPos.getX() + 1 - vec3d.x);
      float floatValue32 = (float)(blockPos.getY() + 1 - vec3d.y);
      float floatValue33 = (float)(blockPos.getZ() + 1 - vec3d.z);
      this.invoke15(vertexConsumer, matrix4f, floatValue28, floatValue29, floatValue30, floatValue31, floatValue32, floatValue33, color, color2);
   }

   private String resolve6(ItemStack itemStack) {
      return itemStack.getItem().toString() + "|" + itemStack.getName().getString();
   }

   private void invoke15(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float g, float h, float i, float j, float k, Color color, Color color2) {
      int intValue50 = color.getRed();
      int intValue51 = color.getGreen();
      int intValue52 = color.getBlue();
      int intValue53 = color.getAlpha();
      int intValue54 = color2.getRed();
      int intValue55 = color2.getGreen();
      int intValue56 = color2.getBlue();
      int intValue57 = color2.getAlpha();
      vertexConsumer.vertex(matrix4f, f, g, h).color(intValue50, intValue51, intValue52, intValue53);
      vertexConsumer.vertex(matrix4f, i, g, h).color(intValue50, intValue51, intValue52, intValue53);
      vertexConsumer.vertex(matrix4f, i, j, h).color(intValue54, intValue55, intValue56, intValue57);
      vertexConsumer.vertex(matrix4f, f, j, h).color(intValue54, intValue55, intValue56, intValue57);
      vertexConsumer.vertex(matrix4f, f, j, k).color(intValue54, intValue55, intValue56, intValue57);
      vertexConsumer.vertex(matrix4f, i, j, k).color(intValue54, intValue55, intValue56, intValue57);
      vertexConsumer.vertex(matrix4f, i, g, k).color(intValue50, intValue51, intValue52, intValue53);
      vertexConsumer.vertex(matrix4f, f, g, k).color(intValue50, intValue51, intValue52, intValue53);
      vertexConsumer.vertex(matrix4f, f, g, k).color(intValue50, intValue51, intValue52, intValue53);
      vertexConsumer.vertex(matrix4f, f, g, h).color(intValue50, intValue51, intValue52, intValue53);
      vertexConsumer.vertex(matrix4f, f, j, h).color(intValue54, intValue55, intValue56, intValue57);
      vertexConsumer.vertex(matrix4f, f, j, k).color(intValue54, intValue55, intValue56, intValue57);
      vertexConsumer.vertex(matrix4f, i, j, k).color(intValue54, intValue55, intValue56, intValue57);
      vertexConsumer.vertex(matrix4f, i, j, h).color(intValue54, intValue55, intValue56, intValue57);
      vertexConsumer.vertex(matrix4f, i, g, h).color(intValue50, intValue51, intValue52, intValue53);
      vertexConsumer.vertex(matrix4f, i, g, k).color(intValue50, intValue51, intValue52, intValue53);
      vertexConsumer.vertex(matrix4f, f, g, h).color(intValue50, intValue51, intValue52, intValue53);
      vertexConsumer.vertex(matrix4f, f, g, k).color(intValue50, intValue51, intValue52, intValue53);
      vertexConsumer.vertex(matrix4f, i, g, k).color(intValue50, intValue51, intValue52, intValue53);
      vertexConsumer.vertex(matrix4f, i, g, h).color(intValue50, intValue51, intValue52, intValue53);
      vertexConsumer.vertex(matrix4f, f, j, h).color(intValue54, intValue55, intValue56, intValue57);
      vertexConsumer.vertex(matrix4f, i, j, h).color(intValue54, intValue55, intValue56, intValue57);
      vertexConsumer.vertex(matrix4f, i, j, k).color(intValue54, intValue55, intValue56, intValue57);
      vertexConsumer.vertex(matrix4f, f, j, k).color(intValue54, intValue55, intValue56, intValue57);
   }

   private boolean check8(ItemStack itemStack) {
      if (itemStack.isEmpty()) {
         return false;
      } else {
         String text7 = itemStack.getName().getString();
         if (text7.contains("[★]")) {
            return true;
         } else {
            Item item = itemStack.getItem();
            if (this.predmetyDlyaLuta.isEnabled("Дон зелья") && this.check9(itemStack)) {
               return true;
            } else if (this.predmetyDlyaLuta.isEnabled("Сферы") && this.check10(itemStack)) {
               return true;
            } else if (this.predmetyDlyaLuta.isEnabled("Талисманы") && this.check11(itemStack)) {
               return true;
            } else if (!this.predmetyDlyaLuta.isEnabled("Стрелы") || item != Items.ARROW && item != Items.TIPPED_ARROW && item != Items.SPECTRAL_ARROW) {
               return this.predmetyDlyaLuta.isEnabled("Яйца") && item instanceof SpawnEggItem
                  ? true
                  : this.predmetyDlyaLuta.isEnabled("Ценные предметы") && this.check12(itemStack);
            } else {
               return true;
            }
         }
      }
   }

   private boolean check9(ItemStack itemStack) {
      return SpecialItemUtils.check25(itemStack)
         || SpecialItemUtils.check26(itemStack)
         || SpecialItemUtils.check27(itemStack)
         || SpecialItemUtils.check28(itemStack)
         || SpecialItemUtils.check29(itemStack)
         || SpecialItemUtils.check30(itemStack)
         || SpecialItemUtils.check31(itemStack);
   }

   private boolean check10(ItemStack itemStack) {
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

   private boolean check11(ItemStack itemStack) {
      return SpecialItemUtils.check17(itemStack)
         || SpecialItemUtils.check18(itemStack)
         || SpecialItemUtils.check19(itemStack)
         || SpecialItemUtils.check20(itemStack)
         || SpecialItemUtils.check21(itemStack)
         || SpecialItemUtils.check22(itemStack)
         || SpecialItemUtils.check23(itemStack)
         || SpecialItemUtils.check24(itemStack);
   }

   private boolean check12(ItemStack itemStack) {
      Item item2 = itemStack.getItem();
      if (item2 instanceof BlockItem blockItem && blockItem.getBlock() instanceof AbstractSkullBlock) {
         return true;
      } else if (item2 == Items.TOTEM_OF_UNDYING || item2 == Items.PAPER || item2 == Items.IRON_NUGGET || item2 == Items.TRIPWIRE_HOOK) {
         return true;
      } else if (item2 == Items.GUNPOWDER || item2 == Items.TNT || item2 == Items.NETHERITE_INGOT || item2 == Items.NETHER_STAR || item2 == Items.ENDER_EYE) {
         return true;
      } else if (item2 == Items.SNOWBALL || item2 == Items.SUGAR || item2 == Items.PHANTOM_MEMBRANE) {
         return true;
      } else {
         return item2 != Items.NETHERITE_SCRAP && item2 != Items.ELYTRA
            ? item2 == Items.CAMPFIRE
               || item2 == Items.SOUL_CAMPFIRE
               || item2 == Items.BEACON
               || item2 == Items.ENCHANTED_GOLDEN_APPLE
               || item2 == Items.GOLDEN_APPLE
               || item2 == Items.SPAWNER
            : true;
      }
   }

   @EventHandler
   public void onPacket(PacketEvent packetEvent) {
      if (packetEvent.getPacket() instanceof GameMessageS2CPacket gameMessageS2CPacket) {
         String text8 = gameMessageS2CPacket.content().getString();
         if (text8.contains("Данная команда недоступна в режиме AFK")
            && !this.flag2
            && CLIENT.player != null
            && CLIENT.player.networkHandler != null) {
            ServerStatsParser.INSTANCE.invoke2();
            this.nA = ServerStatsParser.INSTANCE.getNA2();
            CLIENT.player.networkHandler.sendChatCommand("hub");
            this.flag2 = true;
            this.dualTimer5.invoke();
            if (this.check3()) {
               CLIENT.player.closeHandledScreen();
            }

            this.serverDHelperState3 = ServerDHelper.ServerDHelperState3.IDLE;
         }
      }
   }

   static enum ServerDHelperState {
      IDLE,
      ROTATING,
      OPENING,
      WAITING_SCREEN;
   }

   static class ServerDHelperState2 {
      float floatValue;
      private float floatValue2;
      private long timestamp;

      ServerDHelperState2(float f) {
         this.floatValue2 = f;
         this.timestamp = System.currentTimeMillis();
      }

      void invoke(boolean bl, float f) {
         long longValue12 = System.currentTimeMillis();
         float floatValue34 = MathHelper.clamp((float)(longValue12 - this.timestamp) / 16.666F, 0.5F, 3.0F);
         this.timestamp = longValue12;
         this.floatValue = this.floatValue + ((bl ? 1.0F : 0.0F) - this.floatValue) * MathHelper.clamp(0.18F * floatValue34, 0.0F, 1.0F);
         this.floatValue2 = this.floatValue2 + (MathHelper.clamp(f, 0.0F, 1.0F) - this.floatValue2) * MathHelper.clamp(0.12F * floatValue34, 0.0F, 1.0F);
      }
   }

   static enum ServerDHelperState3 {
      IDLE,
      ROTATING,
      OPENING,
      REOPEN_CLAN;
   }
}
