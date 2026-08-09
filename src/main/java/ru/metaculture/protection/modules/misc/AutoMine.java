package ru.metaculture.protection;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.pathing.goals.GoalNear;
import baritone.api.utils.BetterBlockPos;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline.Snippet;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.vertex.VertexFormat.DrawMode;
import java.awt.Color;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.RenderLayer.MultiPhaseParameters;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import org.joml.Matrix4f;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "AutoMine",
   description = "Полная автоматизация шахты",
   category = Category.Misc
)
public class AutoMine extends Module {
   public final TextSetting anarhiyaDlyaSbrosa = new TextSetting("Анархия для сброса", "903");
   public final KeybindSetting bindNaSunduk = new KeybindSetting("Бинд на сундук", -1);
   public final BooleanSetting neOtobrazhatEkran = new BooleanSetting("Не отображать экран", false);
   private final TextSetting autominelayoutdata = new TextSetting("AutoMineLayoutData", "").setVisibilityCondition(() -> true);
   private final TextSetting autominedropchest = new TextSetting("AutoMineDropChest", "").setVisibilityCondition(() -> true);
   private final DualTimer dualTimer = new DualTimer();
   private final DualTimer dualTimer2 = new DualTimer();
   private final DualTimer dualTimer3 = new DualTimer();
   private final DualTimer dualTimer4 = new DualTimer();
   private final DualTimer dualTimer5 = new DualTimer();
   private final DualTimer dualTimer6 = new DualTimer();
   private int intValue = 0;
   private final List<String> items = Arrays.asList(
      "405", "503", "504", "505", "304", "902", "901", "404", "402", "401", "903", "201", "202", "203", "204", "205", "206", "207", "208", "209", "210"
   );
   private static final BlockPos BLOCK_POS = new BlockPos(-55, 93, 30);
   private static final BlockPos BLOCK_POS_2 = new BlockPos(-73, 84, 48);
   private static final double DOUBLE_VALUE = 4.0;
   private static final double DOUBLE_VALUE_2 = 3.5;
   private static final int INT_VALUE = 2500;
   private static final int INT_VALUE_2 = 2500;
   private AutoMine.AutoMineState autoMineState = AutoMine.AutoMineState.IDLE;
   private boolean flag = false;
   private BlockPos blockPos = null;
   private BlockPos blockPos2 = null;
   private BlockPos blockPos3 = null;
   private BlockPos blockPos4 = null;
   private BlockPos blockPos5 = null;
   private boolean flag2 = false;
   private double doubleValue = -1.0;
   private double doubleValue2 = -1.0;
   private boolean flag3;
   private boolean flag4;
   private boolean flag5;
   private boolean flag6;
   private List<Block> items2 = List.of();
   private final Queue<Runnable> queue = new ArrayDeque<>();
   private final Map<Integer, AutoMine.AutoMineEntry> valuesByKey = new HashMap<>();
   private static final AutoMine.AutoMineEntry AUTO_MINE_ENTRY = new AutoMine.AutoMineEntry("", 0);
   private GenericContainerScreen genericContainerScreen;
   private static final int INT_VALUE_3 = 1024;
   private static final RenderPipeline RENDER_PIPELINE = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.POSITION_COLOR_SNIPPET})
         .withLocation(Identifier.of("wild", "automine_block_box"))
         .withVertexFormat(VertexFormats.POSITION_COLOR, DrawMode.QUADS)
         .withCull(false)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .withBlend(BlendFunction.LIGHTNING)
         .build()
   );
   private static final RenderLayer RENDER_LAYER = RenderLayer.of(
      "automine_block_box", 1024, false, true, RENDER_PIPELINE, MultiPhaseParameters.builder().build(false)
   );

   public AutoMine() {
      this.addSettings(new Setting[]{this.anarhiyaDlyaSbrosa, this.bindNaSunduk, this.neOtobrazhatEkran, this.autominelayoutdata, this.autominedropchest});
   }

   @Override
   public void onEnable() {
      if (WildClient.INSTANCE.moduleManager.getModule(AttackAura.class).enabled) {
         ChatUtil.sendClientMessage("Отключите ауру для включения модуля");
         this.toggle();
      } else {
         super.onEnable();
         if (CLIENT.options != null) {
            CLIENT.options.setPerspective(Perspective.FIRST_PERSON);
         }

         FreeLookController.active = true;
         this.autoMineState = AutoMine.AutoMineState.IDLE;
         this.flag = false;
         this.blockPos = null;
         this.blockPos2 = null;
         this.blockPos3 = null;
         this.blockPos5 = null;
         this.flag2 = false;
         this.doubleValue = -1.0;
         this.doubleValue2 = -1.0;
         this.queue.clear();
         this.invoke20();
         this.invoke23();
         this.flag3 = (Boolean)BaritoneAPI.getSettings().allowPlace.value;
         this.flag4 = (Boolean)BaritoneAPI.getSettings().allowBreak.value;
         this.flag5 = (Boolean)BaritoneAPI.getSettings().legitMine.value;
         this.flag6 = (Boolean)BaritoneAPI.getSettings().walkWhileBreaking.value;
         List items = (List)BaritoneAPI.getSettings().blocksToAvoidBreaking.value;
         this.items2 = (List<Block>)(Object)(items == null ? List.of() : new ArrayList<>(items));
         BaritoneAPI.getSettings().allowPlace.value = false;
         BaritoneAPI.getSettings().allowBreak.value = true;
         BaritoneAPI.getSettings().legitMine.value = false;
         BaritoneAPI.getSettings().walkWhileBreaking.value = false;
         List items2 = Arrays.asList(
            Blocks.SPRUCE_LOG,
            Blocks.SPRUCE_WOOD,
            Blocks.SPRUCE_PLANKS,
            Blocks.STRIPPED_SPRUCE_LOG,
            Blocks.STRIPPED_SPRUCE_WOOD,
            Blocks.OAK_LOG,
            Blocks.OAK_WOOD,
            Blocks.OAK_PLANKS,
            Blocks.DIRT,
            Blocks.GRASS_BLOCK,
            Blocks.COARSE_DIRT,
            Blocks.PODZOL,
            Blocks.STONE_BRICKS,
            Blocks.CRACKED_STONE_BRICKS,
            Blocks.MOSSY_STONE_BRICKS,
            Blocks.LADDER,
            Blocks.BEDROCK,
            Blocks.BARREL,
            Blocks.CHEST,
            Blocks.TRAPPED_CHEST
         );
         List items3 = (List)BaritoneAPI.getSettings().blocksToAvoidBreaking.value;
         if (items3 != null) {
            for (Block block : (List<Block>)items2) {
               if (!items3.contains(block)) {
                  items3.add(block);
               }
            }
         }

         if (CLIENT.player != null && CLIENT.world != null) {
            if (this.check5()) {
               this.check3();
            } else if (this.check11()) {
               this.invoke();
            } else if (this.check10()) {
               this.invoke2();
            } else {
               CLIENT.player.networkHandler.sendChatCommand("warp mine");
               this.autoMineState = AutoMine.AutoMineState.WAITING_FOR_TP;
               this.dualTimer.invoke();
            }
         }
      }
   }

   @Override
   public void onDisable() {
      super.onDisable();
      if (CLIENT.options != null) {
         CLIENT.options.setPerspective(Perspective.FIRST_PERSON);
         if (this.flag2) {
            CLIENT.options.sneakKey.setPressed(false);
            this.flag2 = false;
         }
      }

      FreeLookController.active = false;
      this.invoke6();
      this.queue.clear();
      BaritoneAPI.getSettings().allowPlace.value = this.flag3;
      BaritoneAPI.getSettings().allowBreak.value = this.flag4;
      BaritoneAPI.getSettings().legitMine.value = this.flag5;
      BaritoneAPI.getSettings().walkWhileBreaking.value = this.flag6;
      List items4 = (List)BaritoneAPI.getSettings().blocksToAvoidBreaking.value;
      if (items4 != null) {
         items4.clear();
         items4.addAll(this.items2);
      }

      IBaritone iBaritone2 = BaritoneAPI.getProvider().getPrimaryBaritone();
      if (this.flag) {
         iBaritone2.getCommandManager().execute("resume");
         this.flag = false;
      }

      iBaritone2.getCommandManager().execute("stop");
      iBaritone2.getSelectionManager().removeAllSelections();
      RotationController.rotationControllerState2 = RotationController.RotationControllerState2.IDLE;
      RotationController.intValue = 0;
      RotationController.rotation = null;
      this.genericContainerScreen = null;
   }

   @EventHandler
   public void onScreenOpen(ScreenOpenEvent screenOpenEvent) {
      if (this.neOtobrazhatEkran.isEnabled() && screenOpenEvent.getScreen() instanceof GenericContainerScreen genericContainerScreen) {
         if (this.autoMineState == AutoMine.AutoMineState.OPENING_DROP_CHEST
            || this.autoMineState == AutoMine.AutoMineState.WAITING_FOR_DROP_GUI
            || this.autoMineState == AutoMine.AutoMineState.DROPPING) {
            this.genericContainerScreen = genericContainerScreen;
            screenOpenEvent.invoke();
         }
      }
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player != null && CLIENT.world != null) {
         IBaritone iBaritone3 = BaritoneAPI.getProvider().getPrimaryBaritone();
         boolean flag = PlayerHelper.check();
         if (flag) {
            if (!this.flag) {
               iBaritone3.getCommandManager().execute("pause");
               this.flag = true;
               this.invoke6();
            }
         } else {
            if (this.flag) {
               iBaritone3.getCommandManager().execute("resume");
               this.flag = false;
            }

            if (this.autoMineState == AutoMine.AutoMineState.MINING && this.blockPos != null) {
               if (CLIENT.player.isClimbing() && CLIENT.options.attackKey.isPressed()) {
                  CLIENT.options.sneakKey.setPressed(true);
                  this.flag2 = true;
               } else if (this.flag2) {
                  CLIENT.options.sneakKey.setPressed(false);
                  this.flag2 = false;
               }
            } else if (this.flag2) {
               CLIENT.options.sneakKey.setPressed(false);
               this.flag2 = false;
            }

            if (!this.check5() || this.autoMineState != AutoMine.AutoMineState.MINING && this.autoMineState != AutoMine.AutoMineState.GOING_TO_MINE) {
               GenericContainerScreen genericContainerScreen2 = this.resolve9();
               if (this.autoMineState == AutoMine.AutoMineState.DROPPING && genericContainerScreen2 != null) {
                  this.invoke12((GenericContainerScreenHandler)genericContainerScreen2.getScreenHandler());
               } else {
                  switch (this.autoMineState) {
                     case WAITING_FOR_TP:
                        if (this.dualTimer.check5(5500L)) {
                           this.invoke2();
                        }
                        break;
                     case GOING_TO_MINE:
                        this.invoke3(iBaritone3);
                        break;
                     case MINING:
                        this.invoke5();
                        if (this.blockPos == null || this.check2(this.blockPos)) {
                           this.invoke4();
                        }

                        if (this.dualTimer.check5(2000L)) {
                           if (!this.check12()) {
                              iBaritone3.getCommandManager().execute("stop");
                              this.invoke7();
                           }

                           this.dualTimer.invoke();
                        }
                        break;
                     case TELEPORTING_TO_DROP:
                        if (this.dualTimer.check5(6000L)) {
                           this.invoke8();
                        }
                        break;
                     case GOING_TO_DROP_CHEST:
                        this.invoke9(iBaritone3);
                        break;
                     case ROTATING_DROP_CHEST:
                        this.invoke10();
                        break;
                     case OPENING_DROP_CHEST:
                        this.invoke11();
                        break;
                     case WAITING_FOR_DROP_GUI:
                        if (this.resolve9() != null) {
                           this.autoMineState = AutoMine.AutoMineState.DROPPING;
                           this.dualTimer2.invoke();
                        } else if (this.dualTimer4.check5(3000L)) {
                           this.autoMineState = AutoMine.AutoMineState.OPENING_DROP_CHEST;
                           this.dualTimer4.invoke();
                        }
                     case DROPPING:
                     default:
                        break;
                     case CHANGING_ANARCHY:
                        if (this.dualTimer.check5(2000L)) {
                           if (this.check11()) {
                              this.invoke();
                           } else if (this.check10()) {
                              this.invoke2();
                           } else {
                              CLIENT.player.networkHandler.sendChatCommand("warp mine");
                              this.autoMineState = AutoMine.AutoMineState.WAITING_FOR_TP;
                              this.dualTimer.invoke();
                           }
                        }
                  }
               }
            } else {
               iBaritone3.getCommandManager().execute("stop");
               this.check3();
            }
         }
      }
   }

   @EventHandler
   public void onPacket(PacketEvent packetEvent) {
      if (packetEvent.getPacket() instanceof GameMessageS2CPacket gameMessageS2CPacket) {
         String text = gameMessageS2CPacket.content().getString();
         if ((text.contains("Телепорт") || text.contains("teleport") || text.contains("Teleport"))
            && (
               this.autoMineState == AutoMine.AutoMineState.WAITING_FOR_TP
                  || this.autoMineState == AutoMine.AutoMineState.TELEPORTING_TO_DROP
                  || this.autoMineState == AutoMine.AutoMineState.CHANGING_ANARCHY
            )) {
            this.dualTimer.invoke();
         }
      }
   }

   @EventHandler
   public void onRawInput(RawInputEvent rawInputEvent) {
      if (CLIENT.player != null && CLIENT.world != null) {
         if (this.bindNaSunduk.getKeyCode() != -1 && rawInputEvent.getKeyCode() == this.bindNaSunduk.getKeyCode()) {
            if (CLIENT.crosshairTarget instanceof BlockHitResult blockHitResult) {
               BlockPos blockPos3 = blockHitResult.getBlockPos();
               if (this.check16(blockPos3)) {
                  this.invoke22(blockPos3);
                  ChatUtil.sendClientMessage("§8[§6AutoMine§8] §aСундук для сброса установлен: " + blockPos3.toShortString());
               } else {
                  ChatUtil.sendClientMessage("§8[§6AutoMine§8] §cСмотрите на сундук, бочку или шалкер.");
               }
            }
         }
      }
   }

   private void invoke() {
      if (this.check12()) {
         this.autoMineState = AutoMine.AutoMineState.MINING;
         this.dualTimer.invoke();
         this.invoke4();
      } else {
         this.invoke7();
      }
   }

   private void invoke2() {
      IBaritone iBaritone4 = BaritoneAPI.getProvider().getPrimaryBaritone();
      this.invoke6();
      iBaritone4.getSelectionManager().removeAllSelections();
      iBaritone4.getCommandManager().execute("stop");
      this.blockPos3 = this.resolve5();
      if (this.blockPos3 == null) {
         this.blockPos3 = this.resolve10();
      }

      this.autoMineState = AutoMine.AutoMineState.GOING_TO_MINE;
      this.doubleValue = CLIENT.player.getPos().distanceTo(Vec3d.ofCenter(this.blockPos3));
      this.dualTimer.invoke();
      this.dualTimer5.invoke();
      this.dualTimer6.invoke();
      iBaritone4.getCustomGoalProcess().setGoalAndPath(new GoalNear(this.blockPos3, 2));
   }

   private void invoke3(IBaritone iBaritone) {
      if (this.check11()) {
         iBaritone.getPathingBehavior().cancelEverything();
         this.blockPos3 = null;
         this.invoke();
      } else {
         if (this.blockPos3 == null || this.dualTimer5.check5(10000L)) {
            this.blockPos3 = this.resolve5();
            if (this.blockPos3 == null) {
               this.blockPos3 = this.resolve10();
            }
         }

         double doubleValue = CLIENT.player.getPos().distanceTo(Vec3d.ofCenter(this.blockPos3));
         if (this.doubleValue < 0.0 || doubleValue < this.doubleValue - 1.0) {
            this.doubleValue = doubleValue;
            this.dualTimer6.invoke();
         }

         if (!iBaritone.getCustomGoalProcess().isActive() || this.dualTimer5.check5(2500L)) {
            iBaritone.getCustomGoalProcess().setGoalAndPath(new GoalNear(this.blockPos3, 2));
            this.dualTimer5.invoke();
         }

         if (this.dualTimer6.check5(45000L)) {
            iBaritone.getPathingBehavior().cancelEverything();
            this.invoke7();
         }
      }
   }

   private void invoke4() {
      BlockPos blockPos4 = this.resolve3();
      if (blockPos4 != null) {
         this.blockPos = blockPos4;
         this.blockPos2 = null;
         this.invoke6();
         IBaritone iBaritone5 = BaritoneAPI.getProvider().getPrimaryBaritone();
         iBaritone5.getCommandManager().execute("stop");
         iBaritone5.getSelectionManager().removeAllSelections();
         iBaritone5.getSelectionManager().addSelection(new BetterBlockPos(blockPos4), new BetterBlockPos(blockPos4));
         iBaritone5.getCommandManager().execute("sel cleararea");
      } else {
         this.blockPos = null;
         this.blockPos2 = null;
         this.invoke6();
      }
   }

   private void invoke5() {
      if (this.blockPos != null && CLIENT.player != null && CLIENT.world != null && CLIENT.interactionManager != null) {
         if (!this.check2(this.blockPos)
            && !(CLIENT.player.squaredDistanceTo(this.blockPos.getX() + 0.5, this.blockPos.getY() + 0.5, this.blockPos.getZ() + 0.5) > 36.0)
            )
          {
            Rotation rotation = this.resolve7(Vec3d.ofCenter(this.blockPos));
            RotationController.invoke3(rotation, 65.0F, 65.0F, 65.0F, 65.0F, 2, 20, false);
            if (new Rotation(CLIENT.player).measure(rotation) > 6.0F) {
               if (CLIENT.options != null) {
                  CLIENT.options.attackKey.setPressed(false);
               }
            } else {
               BlockHitResult blockHitResult2 = this.resolve(this.blockPos);
               if (blockHitResult2 == null) {
                  this.invoke6();
               } else {
                  CLIENT.options.attackKey.setPressed(true);
                  if (!this.blockPos.equals(this.blockPos2)) {
                     CLIENT.interactionManager.attackBlock(this.blockPos, blockHitResult2.getSide());
                     this.blockPos2 = this.blockPos;
                     this.dualTimer3.invoke();
                  } else if (this.dualTimer3.check5(45L)) {
                     CLIENT.interactionManager.updateBlockBreakingProgress(this.blockPos, blockHitResult2.getSide());
                     CLIENT.player.swingHand(Hand.MAIN_HAND);
                     this.dualTimer3.invoke();
                  }
               }
            }
         } else {
            this.invoke6();
         }
      } else {
         this.invoke6();
      }
   }

   private BlockHitResult resolve(BlockPos blockPos) {
      Vec3d vec3d2 = CLIENT.player.getEyePos();
      double[] doubleValues = new double[]{0.5, 0.2, 0.8};

      for (double doubleValue2 : doubleValues) {
         for (double doubleValue3 : doubleValues) {
            for (double doubleValue4 : doubleValues) {
               Vec3d vec3d3 = new Vec3d(blockPos.getX() + doubleValue2, blockPos.getY() + doubleValue3, blockPos.getZ() + doubleValue4);
               BlockHitResult blockHitResult3 = CLIENT.world.raycast(new RaycastContext(vec3d2, vec3d3, ShapeType.OUTLINE, FluidHandling.NONE, CLIENT.player));
               if (blockHitResult3.getType() == Type.BLOCK && blockHitResult3.getBlockPos().equals(blockPos)) {
                  return blockHitResult3;
               }
            }
         }
      }

      return this.check(blockPos) ? new BlockHitResult(Vec3d.ofCenter(blockPos), this.resolve2(blockPos), blockPos, false) : null;
   }

   private boolean check(BlockPos blockPos) {
      for (Direction direction : Direction.values()) {
         if (this.check2(blockPos.offset(direction))) {
            return true;
         }
      }

      return false;
   }

   private Direction resolve2(BlockPos blockPos) {
      Vec3d vec3d4 = CLIENT.player.getEyePos().subtract(Vec3d.ofCenter(blockPos));
      double doubleValue5 = Math.abs(vec3d4.x);
      double doubleValue6 = Math.abs(vec3d4.y);
      double doubleValue7 = Math.abs(vec3d4.z);
      if (doubleValue6 >= doubleValue5 && doubleValue6 >= doubleValue7) {
         return vec3d4.y > 0.0 ? Direction.UP : Direction.DOWN;
      } else if (doubleValue5 >= doubleValue7) {
         return vec3d4.x > 0.0 ? Direction.EAST : Direction.WEST;
      } else {
         return vec3d4.z > 0.0 ? Direction.SOUTH : Direction.NORTH;
      }
   }

   private void invoke6() {
      if (CLIENT.options != null) {
         CLIENT.options.attackKey.setPressed(false);
      }

      this.blockPos2 = null;
   }

   private BlockPos resolve3() {
      int intValue = this.compute5();
      int intValue2 = this.compute6();
      int intValue3 = this.compute7();
      int intValue4 = this.compute8();
      int intValue5 = this.compute9();
      int intValue6 = this.compute10();

      for (int intValue7 = intValue4; intValue7 >= intValue3; intValue7--) {
         BlockPos blockPos5 = null;

         for (int intValue8 = intValue; intValue8 <= intValue2; intValue8++) {
            for (int intValue9 = intValue5; intValue9 <= intValue6; intValue9++) {
               BlockPos blockPos6 = new BlockPos(intValue8, intValue7, intValue9);
               Block block2 = CLIENT.world.getBlockState(blockPos6).getBlock();
               if ((block2 == Blocks.DIAMOND_ORE || block2 == Blocks.DEEPSLATE_DIAMOND_ORE)
                  && (blockPos5 == null || CLIENT.player.squaredDistanceTo(Vec3d.ofCenter(blockPos6)) < CLIENT.player.squaredDistanceTo(Vec3d.ofCenter(blockPos5)))
                  )
                {
                  blockPos5 = blockPos6;
               }
            }
         }

         if (blockPos5 != null) {
            return blockPos5;
         }
      }

      return null;
   }

   private boolean check2(BlockPos blockPos) {
      Block block3 = CLIENT.world.getBlockState(blockPos).getBlock();
      return block3 == Blocks.AIR || block3 == Blocks.CAVE_AIR || block3 == Blocks.VOID_AIR;
   }

   private void invoke7() {
      if (this.intValue >= this.items.size()) {
         this.intValue = 0;
      }

      String text2 = this.resolve8();
      String text3 = this.items.get(this.intValue);
      if (text2 != null && text3.equals(text2)) {
         this.intValue++;
         if (this.intValue >= this.items.size()) {
            this.intValue = 0;
         }

         text3 = this.items.get(this.intValue);
      }

      CLIENT.player.networkHandler.sendChatCommand("an" + text3);
      this.intValue++;
      this.autoMineState = AutoMine.AutoMineState.CHANGING_ANARCHY;
      this.dualTimer.invoke();
   }

   private boolean check3() {
      String text4 = this.resolve8();
      if (text4 == null) {
         this.invoke24("Укажите анархию для сброса.");
         return false;
      } else if (this.blockPos4 == null) {
         this.invoke24("Установите сундук для сброса через бинд.");
         return false;
      } else {
         this.invoke6();
         this.queue.clear();
         CLIENT.player.networkHandler.sendChatCommand("an" + text4);
         this.autoMineState = AutoMine.AutoMineState.TELEPORTING_TO_DROP;
         this.dualTimer.invoke();
         return true;
      }
   }

   private void invoke8() {
      if (this.blockPos4 == null) {
         this.invoke24("Сундук для сброса не установлен.");
      } else {
         this.invoke6();
         this.queue.clear();
         IBaritone iBaritone6 = BaritoneAPI.getProvider().getPrimaryBaritone();
         iBaritone6.getSelectionManager().removeAllSelections();
         iBaritone6.getCommandManager().execute("stop");
         this.blockPos5 = this.resolve6(this.blockPos4);
         this.autoMineState = AutoMine.AutoMineState.GOING_TO_DROP_CHEST;
         this.doubleValue2 = CLIENT.player.getPos().distanceTo(Vec3d.ofCenter(this.blockPos4));
         this.dualTimer4.invoke();
         this.dualTimer5.invoke();
         this.dualTimer6.invoke();
      }
   }

   private void invoke9(IBaritone iBaritone) {
      if (this.blockPos4 == null) {
         this.invoke24("Сундук для сброса не установлен.");
      } else {
         double doubleValue8 = CLIENT.player.getPos().distanceTo(Vec3d.ofCenter(this.blockPos4));
         if (doubleValue8 <= 3.5 && this.check15(this.blockPos4)) {
            iBaritone.getPathingBehavior().cancelEverything();
            this.autoMineState = AutoMine.AutoMineState.ROTATING_DROP_CHEST;
            this.dualTimer4.invoke();
         } else {
            if (this.blockPos5 == null || this.dualTimer5.check5(10000L)) {
               this.blockPos5 = this.resolve6(this.blockPos4);
            }

            BlockPos blockPos7 = this.blockPos5 == null ? this.blockPos4 : this.blockPos5;
            int intValue10 = this.blockPos5 == null ? 3 : 1;
            if (!iBaritone.getCustomGoalProcess().isActive() || this.dualTimer5.check5(2500L)) {
               iBaritone.getCustomGoalProcess().setGoalAndPath(new GoalNear(blockPos7, intValue10));
               this.dualTimer5.invoke();
            }

            if (this.doubleValue2 < 0.0 || doubleValue8 < this.doubleValue2 - 1.0) {
               this.doubleValue2 = doubleValue8;
               this.dualTimer6.invoke();
            }

            if (this.dualTimer6.check5(45000L)) {
               this.invoke24("Не удалось дойти до сундука для сброса.");
            }
         }
      }
   }

   private void invoke10() {
      if (this.blockPos4 != null && CLIENT.player != null) {
         Rotation rotation2 = this.resolve7(Vec3d.ofCenter(this.blockPos4));
         RotationController.invoke3(rotation2, 35.0F, 35.0F, 35.0F, 35.0F, 20, 1, false);
         if (new Rotation(CLIENT.player).measure(rotation2) < 5.0F && this.dualTimer4.check5(150L)) {
            RotationController.rotationControllerState2 = RotationController.RotationControllerState2.IDLE;
            this.autoMineState = AutoMine.AutoMineState.OPENING_DROP_CHEST;
            this.dualTimer4.invoke();
         }
      } else {
         this.invoke24("Сундук для сброса потерян.");
      }
   }

   private void invoke11() {
      if (this.blockPos4 != null && CLIENT.interactionManager != null) {
         this.invoke21();
         if (this.dualTimer4.check5(150L)) {
            BlockHitResult blockHitResult4 = new BlockHitResult(Vec3d.ofCenter(this.blockPos4), Direction.UP, this.blockPos4, false);
            CLIENT.player.swingHand(Hand.MAIN_HAND);
            CLIENT.interactionManager.interactBlock(CLIENT.player, Hand.MAIN_HAND, blockHitResult4);
            this.autoMineState = AutoMine.AutoMineState.WAITING_FOR_DROP_GUI;
            this.dualTimer4.invoke();
         }
      } else {
         this.invoke24("Сундук для сброса потерян.");
      }
   }

   private void invoke12(GenericContainerScreenHandler genericContainerScreenHandler) {
      if (CLIENT.player != null && CLIENT.interactionManager != null) {
         if (!this.queue.isEmpty()) {
            if (this.dualTimer2.check5(50L)) {
               this.queue.poll().run();
               this.dualTimer2.invoke();
            }
         } else {
            int intValue11 = genericContainerScreenHandler.slots.size() - 36;
            if (intValue11 <= 0) {
               this.invoke24("Открыт не контейнер для сброса.");
            } else if (this.check9()) {
               this.invoke13(genericContainerScreenHandler, intValue11);
            } else {
               this.invoke14(genericContainerScreenHandler, intValue11);
            }
         }
      }
   }

   private void invoke13(GenericContainerScreenHandler genericContainerScreenHandler, int i) {
      for (int intValue12 = i; intValue12 < genericContainerScreenHandler.slots.size(); intValue12++) {
         Slot slot = (Slot)genericContainerScreenHandler.slots.get(intValue12);
         int intValue13 = this.compute3(intValue12, i);
         AutoMine.AutoMineEntry autoMineEntry = this.valuesByKey.getOrDefault(intValue13, AUTO_MINE_ENTRY);
         if (!slot.hasStack()) {
            if (autoMineEntry.count > 0) {
               if (!this.check4(genericContainerScreenHandler, i, autoMineEntry, intValue12, 0)) {
                  this.invoke24("В сундуке нет предметов для восстановления раскладки.");
               }

               return;
            }
         } else {
            ItemStack itemStack3 = slot.getStack();
            boolean flag2 = autoMineEntry.matches(itemStack3);
            if (autoMineEntry.count <= 0 || !flag2) {
               if (!this.check7(genericContainerScreenHandler, i, itemStack3)) {
                  this.invoke24("Сундук для сброса заполнен.");
                  return;
               } else {
                  int intValue14 = intValue12;
                  int intValue15 = genericContainerScreenHandler.syncId;
                  this.queue.add(() -> CLIENT.interactionManager.clickSlot(intValue15, intValue14, 0, SlotActionType.QUICK_MOVE, CLIENT.player));
                  return;
               }
            }

            if (itemStack3.getCount() > autoMineEntry.count) {
               int intValue16 = itemStack3.getCount() - autoMineEntry.count;
               int intValue17 = this.compute4(genericContainerScreenHandler, i, itemStack3, intValue16);
               if (intValue17 == -1) {
                  this.invoke24("Сундук для сброса заполнен.");
                  return;
               }

               this.invoke15(genericContainerScreenHandler.syncId, intValue12, intValue17, autoMineEntry.count, intValue16);
               return;
            }

            if (itemStack3.getCount() < autoMineEntry.count) {
               if (!this.check4(genericContainerScreenHandler, i, autoMineEntry, intValue12, itemStack3.getCount())) {
                  this.invoke24("В сундуке нет предметов для восстановления раскладки.");
               }

               return;
            }
         }
      }

      this.invoke17();
   }

   private void invoke14(GenericContainerScreenHandler genericContainerScreenHandler, int i) {
      for (int intValue18 = i; intValue18 < genericContainerScreenHandler.slots.size(); intValue18++) {
         Slot slot2 = (Slot)genericContainerScreenHandler.slots.get(intValue18);
         if (slot2.hasStack()) {
            ItemStack itemStack4 = slot2.getStack();
            int intValue19 = this.compute3(intValue18, i);
            int intValue20 = this.compute2(intValue19, itemStack4);
            int intValue21 = itemStack4.getCount() - intValue20;
            if (intValue21 > 0) {
               if (intValue20 <= 0) {
                  if (!this.check7(genericContainerScreenHandler, i, itemStack4)) {
                     this.invoke24("Сундук для сброса заполнен.");
                     return;
                  }

                  int intValue22 = intValue18;
                  int intValue23 = genericContainerScreenHandler.syncId;
                  this.queue.add(() -> CLIENT.interactionManager.clickSlot(intValue23, intValue22, 0, SlotActionType.QUICK_MOVE, CLIENT.player));
                  return;
               }

               int intValue24 = this.compute4(genericContainerScreenHandler, i, itemStack4, intValue21);
               if (intValue24 == -1) {
                  this.invoke24("Сундук для сброса заполнен.");
                  return;
               }

               this.invoke15(genericContainerScreenHandler.syncId, intValue18, intValue24, intValue20, intValue21);
               return;
            }
         }
      }

      this.invoke17();
   }

   private void invoke15(int i, int j, int k, int l, int m) {
      this.queue.add(() -> CLIENT.interactionManager.clickSlot(i, j, 0, SlotActionType.PICKUP, CLIENT.player));
      if (l <= m) {
         for (int intValue25 = 0; intValue25 < l; intValue25++) {
            this.queue.add(() -> CLIENT.interactionManager.clickSlot(i, j, 1, SlotActionType.PICKUP, CLIENT.player));
         }

         this.queue.add(() -> CLIENT.interactionManager.clickSlot(i, k, 0, SlotActionType.PICKUP, CLIENT.player));
      } else {
         for (int intValue26 = 0; intValue26 < m; intValue26++) {
            this.queue.add(() -> CLIENT.interactionManager.clickSlot(i, k, 1, SlotActionType.PICKUP, CLIENT.player));
         }

         this.queue.add(() -> CLIENT.interactionManager.clickSlot(i, j, 0, SlotActionType.PICKUP, CLIENT.player));
      }
   }

   private boolean check4(GenericContainerScreenHandler genericContainerScreenHandler, int i, AutoMine.AutoMineEntry autoMineEntry2, int j, int k) {
      int intValue27 = autoMineEntry2.count - k;
      if (intValue27 <= 0) {
         return true;
      } else {
         int intValue28 = this.compute(genericContainerScreenHandler, i, autoMineEntry2);
         if (intValue28 == -1) {
            return false;
         } else {
            int intValue29 = ((Slot)genericContainerScreenHandler.slots.get(intValue28)).getStack().getCount();
            int intValue30 = Math.min(intValue27, intValue29);
            this.invoke16(genericContainerScreenHandler.syncId, intValue28, j, intValue29, intValue30);
            return true;
         }
      }
   }

   private void invoke16(int i, int j, int k, int l, int m) {
      this.queue.add(() -> CLIENT.interactionManager.clickSlot(i, j, 0, SlotActionType.PICKUP, CLIENT.player));
      if (m >= l) {
         this.queue.add(() -> CLIENT.interactionManager.clickSlot(i, k, 0, SlotActionType.PICKUP, CLIENT.player));
      } else if (m <= l / 2) {
         for (int intValue31 = 0; intValue31 < m; intValue31++) {
            this.queue.add(() -> CLIENT.interactionManager.clickSlot(i, k, 1, SlotActionType.PICKUP, CLIENT.player));
         }

         this.queue.add(() -> CLIENT.interactionManager.clickSlot(i, j, 0, SlotActionType.PICKUP, CLIENT.player));
      } else {
         int intValue32 = l - m;

         for (int intValue33 = 0; intValue33 < intValue32; intValue33++) {
            this.queue.add(() -> CLIENT.interactionManager.clickSlot(i, j, 1, SlotActionType.PICKUP, CLIENT.player));
         }

         this.queue.add(() -> CLIENT.interactionManager.clickSlot(i, k, 0, SlotActionType.PICKUP, CLIENT.player));
      }
   }

   private int compute(GenericContainerScreenHandler genericContainerScreenHandler, int i, AutoMine.AutoMineEntry autoMineEntry3) {
      for (int intValue34 = 0; intValue34 < i; intValue34++) {
         Slot slot3 = (Slot)genericContainerScreenHandler.slots.get(intValue34);
         if (slot3.hasStack() && autoMineEntry3.matches(slot3.getStack())) {
            return intValue34;
         }
      }

      return -1;
   }

   private void invoke17() {
      this.queue.clear();
      this.genericContainerScreen = null;
      if (CLIENT.player != null) {
         CLIENT.player.closeHandledScreen();
      }

      this.autoMineState = AutoMine.AutoMineState.IDLE;
      this.blockPos5 = null;
      this.invoke7();
   }

   private boolean check5() {
      return CLIENT.player.getInventory().getEmptySlot() != -1 ? false : this.check6();
   }

   private boolean check6() {
      for (int intValue35 = 0; intValue35 < 36; intValue35++) {
         ItemStack itemStack5 = CLIENT.player.getInventory().getStack(intValue35);
         if (!itemStack5.isEmpty() && itemStack5.getCount() > this.compute2(intValue35, itemStack5)) {
            return true;
         }
      }

      return false;
   }

   private int compute2(int i, ItemStack itemStack) {
      AutoMine.AutoMineEntry autoMineEntry4 = this.valuesByKey.get(i);
      if (autoMineEntry4 != null) {
         return autoMineEntry4.matches(itemStack) ? Math.min(autoMineEntry4.count, itemStack.getCount()) : 0;
      } else {
         return 1;
      }
   }

   private int compute3(int i, int j) {
      int intValue36 = i - j;
      return intValue36 >= 27 ? intValue36 - 27 : intValue36 + 9;
   }

   private int compute4(GenericContainerScreenHandler genericContainerScreenHandler, int i, ItemStack itemStack, int j) {
      int intValue37 = -1;

      for (int intValue38 = 0; intValue38 < i; intValue38++) {
         Slot slot4 = (Slot)genericContainerScreenHandler.slots.get(intValue38);
         if (!slot4.hasStack()) {
            if (intValue37 == -1) {
               intValue37 = intValue38;
            }
         } else {
            ItemStack itemStack6 = slot4.getStack();
            if (this.check8(itemStack, itemStack6) && itemStack6.getCount() + j <= itemStack6.getMaxCount()) {
               return intValue38;
            }
         }
      }

      return intValue37;
   }

   private boolean check7(GenericContainerScreenHandler genericContainerScreenHandler, int i, ItemStack itemStack) {
      for (int intValue39 = 0; intValue39 < i; intValue39++) {
         Slot slot5 = (Slot)genericContainerScreenHandler.slots.get(intValue39);
         if (!slot5.hasStack()) {
            return true;
         }

         ItemStack itemStack7 = slot5.getStack();
         if (this.check8(itemStack, itemStack7) && itemStack7.getCount() < itemStack7.getMaxCount()) {
            return true;
         }
      }

      return false;
   }

   private boolean check8(ItemStack itemStack, ItemStack itemStack2) {
      return ItemStack.areItemsAndComponentsEqual(itemStack, itemStack2);
   }

   public void invoke18() {
      this.invoke19();
   }

   private boolean check9() {
      return this.valuesByKey.size() >= 36;
   }

   private void invoke19() {
      if (CLIENT.player == null) {
         ChatUtil.sendClientMessage("§8[§6AutoMine§8] §cИгрок не загружен.");
      } else {
         this.valuesByKey.clear();
         StringBuilder stringBuilder = new StringBuilder();
         Encoder encoder = Base64.getEncoder();

         for (int intValue40 = 0; intValue40 < 36; intValue40++) {
            ItemStack itemStack8 = CLIENT.player.getInventory().getStack(intValue40);
            String text5 = itemStack8.isEmpty() ? "" : this.resolve4(itemStack8);
            int intValue41 = itemStack8.isEmpty() ? 0 : itemStack8.getCount();
            this.valuesByKey.put(intValue40, new AutoMine.AutoMineEntry(text5, intValue41));
            if (intValue40 > 0) {
               stringBuilder.append(';');
            }

            stringBuilder.append(intValue41).append(',').append(encoder.encodeToString(text5.getBytes(StandardCharsets.UTF_8)));
         }

         this.autominelayoutdata.setValue(stringBuilder.toString());
         if (WildClient.INSTANCE.configManager != null) {
            WildClient.INSTANCE.configManager.scheduleSave();
         }

         ChatUtil.sendClientMessage("§8[§6AutoMine§8] §aРаскладка инвентаря сохранена.");
      }
   }

   private void invoke20() {
      this.valuesByKey.clear();
      String text6 = this.autominelayoutdata.getValue();
      if (text6 != null && !text6.isBlank()) {
         String[] texts = text6.split(";", -1);
         Decoder decoder = Base64.getDecoder();

         for (int intValue42 = 0; intValue42 < Math.min(36, texts.length); intValue42++) {
            String[] texts2 = texts[intValue42].split(",", 2);
            if (texts2.length == 2) {
               try {
                  int intValue43 = Integer.parseInt(texts2[0]);
                  String text7 = new String(decoder.decode(texts2[1]), StandardCharsets.UTF_8);
                  this.valuesByKey.put(intValue42, new AutoMine.AutoMineEntry(text7, intValue43));
               } catch (IllegalArgumentException illegalArgumentException) {
               }
            }
         }
      }
   }

   private String resolve4(ItemStack itemStack) {
      return itemStack.getItem().toString() + "|" + itemStack.getName().getString();
   }

   private boolean check10() {
      if (CLIENT.player == null) {
         return false;
      } else {
         double doubleValue9 = CLIENT.player.getX() - BLOCK_POS.getX();
         double doubleValue10 = CLIENT.player.getZ() - BLOCK_POS.getZ();
         return Math.sqrt(doubleValue9 * doubleValue9 + doubleValue10 * doubleValue10) < 300.0;
      }
   }

   private boolean check11() {
      if (CLIENT.player == null) {
         return false;
      } else {
         BlockPos blockPos8 = CLIENT.player.getBlockPos();
         return blockPos8.getX() >= this.compute5() - 4.0
            && blockPos8.getX() <= this.compute6() + 4.0
            && blockPos8.getY() >= this.compute7() - 6
            && blockPos8.getY() <= this.compute8() + 8
            && blockPos8.getZ() >= this.compute9() - 4.0
            && blockPos8.getZ() <= this.compute10() + 4.0;
      }
   }

   private boolean check12() {
      return this.resolve3() != null;
   }

   private BlockPos resolve5() {
      if (CLIENT.world != null && CLIENT.player != null) {
         ArrayList arrayList = new ArrayList();

         for (int intValue44 = this.compute7() - 2; intValue44 <= this.compute8() + 2; intValue44++) {
            for (int intValue45 = this.compute5() - 3; intValue45 <= this.compute6() + 3; intValue45++) {
               for (int intValue46 = this.compute9() - 3; intValue46 <= this.compute10() + 3; intValue46++) {
                  BlockPos blockPos9 = new BlockPos(intValue45, intValue44, intValue46);
                  if (this.check13(blockPos9)) {
                     arrayList.add(blockPos9);
                  }
               }
            }
         }

         BlockPos blockPos10 = this.resolve10();
         return (BlockPos)arrayList.stream()
            .min(
               Comparator.<BlockPos>comparingDouble(blockPos2 -> Vec3d.ofCenter(blockPos2).squaredDistanceTo(Vec3d.ofCenter(blockPos10)))
                  .thenComparingDouble(blockPos -> CLIENT.player.squaredDistanceTo(Vec3d.ofCenter(blockPos)))
            )
            .orElse(blockPos10);
      } else {
         return null;
      }
   }

   private BlockPos resolve6(BlockPos blockPos) {
      if (CLIENT.world != null && CLIENT.player != null && blockPos != null) {
         ArrayList arrayList2 = new ArrayList();

         for (int intValue47 = -1; intValue47 <= 1; intValue47++) {
            for (int intValue48 = 1; intValue48 <= 3; intValue48++) {
               for (int intValue49 = -intValue48; intValue49 <= intValue48; intValue49++) {
                  for (int intValue50 = -intValue48; intValue50 <= intValue48; intValue50++) {
                     if (Math.max(Math.abs(intValue49), Math.abs(intValue50)) == intValue48) {
                        arrayList2.add(blockPos.add(intValue49, intValue47, intValue50));
                     }
                  }
               }
            }
         }

         arrayList2.sort(Comparator.<BlockPos>comparingDouble(blockPosx -> CLIENT.player.getPos().squaredDistanceTo(Vec3d.ofCenter(blockPosx))));
         BlockPos blockPos11 = null;

         for (BlockPos blockPos12 : (List<BlockPos>)arrayList2) {
            if (this.check13(blockPos12)) {
               if (this.check14(blockPos12, blockPos)) {
                  return blockPos12;
               }

               if (blockPos11 == null) {
                  blockPos11 = blockPos12;
               }
            }
         }

         return blockPos11;
      } else {
         return null;
      }
   }

   private boolean check13(BlockPos blockPos) {
      if (CLIENT.world == null) {
         return false;
      } else {
         BlockState blockState = CLIENT.world.getBlockState(blockPos);
         BlockState blockState2 = CLIENT.world.getBlockState(blockPos.up());
         BlockState blockState3 = CLIENT.world.getBlockState(blockPos.down());
         return blockState.getCollisionShape(CLIENT.world, blockPos).isEmpty()
            && blockState2.getCollisionShape(CLIENT.world, blockPos.up()).isEmpty()
            && !blockState3.getCollisionShape(CLIENT.world, blockPos.down()).isEmpty();
      }
   }

   private boolean check14(BlockPos blockPos, BlockPos blockPos2) {
      Vec3d vec3d5 = Vec3d.ofCenter(blockPos).add(0.0, 1.2, 0.0);
      Vec3d vec3d6 = Vec3d.ofCenter(blockPos2);
      BlockHitResult blockHitResult5 = CLIENT.world.raycast(new RaycastContext(vec3d5, vec3d6, ShapeType.OUTLINE, FluidHandling.NONE, CLIENT.player));
      return blockHitResult5.getType() == Type.MISS || blockHitResult5.getBlockPos().equals(blockPos2);
   }

   private boolean check15(BlockPos blockPos) {
      Vec3d vec3d7 = CLIENT.player.getEyePos();
      Vec3d vec3d8 = Vec3d.ofCenter(blockPos);
      BlockHitResult blockHitResult6 = CLIENT.world.raycast(new RaycastContext(vec3d7, vec3d8, ShapeType.OUTLINE, FluidHandling.NONE, CLIENT.player));
      return blockHitResult6.getType() == Type.MISS || blockHitResult6.getBlockPos().equals(blockPos);
   }

   private Rotation resolve7(Vec3d vec3d) {
      if (CLIENT.player == null) {
         return new Rotation(0.0F, 0.0F);
      } else {
         Vec3d vec3d9 = CLIENT.player.getEyePos();
         double doubleValue11 = vec3d.x - vec3d9.x;
         double doubleValue12 = vec3d.y - vec3d9.y;
         double doubleValue13 = vec3d.z - vec3d9.z;
         float floatValue = (float)Math.toDegrees(Math.atan2(doubleValue13, doubleValue11)) - 90.0F;
         float floatValue2 = (float)(-Math.toDegrees(Math.atan2(doubleValue12, Math.sqrt(doubleValue11 * doubleValue11 + doubleValue13 * doubleValue13))));
         return new Rotation(floatValue, floatValue2);
      }
   }

   private void invoke21() {
      int intValue51 = CLIENT.player.getInventory().getSelectedSlot();
      ItemStack itemStack9 = (ItemStack)CLIENT.player.getInventory().getMainStacks().get(intValue51);
      if (itemStack9.getItem() == Items.TRIPWIRE_HOOK || itemStack9.getName().getString().contains("[★]")) {
         for (int intValue52 = 0; intValue52 < 9; intValue52++) {
            ItemStack itemStack10 = (ItemStack)CLIENT.player.getInventory().getMainStacks().get(intValue52);
            if (itemStack10.isEmpty() || itemStack10.getItem() != Items.TRIPWIRE_HOOK && !itemStack10.getName().getString().contains("[★]")) {
               CLIENT.player.getInventory().setSelectedSlot(intValue52);
               this.dualTimer4.invoke();
               break;
            }
         }
      }
   }

   private boolean check16(BlockPos blockPos) {
      return CLIENT.world != null && blockPos != null
         ? CLIENT.world.getBlockEntity(blockPos) instanceof ChestBlockEntity
            || CLIENT.world.getBlockEntity(blockPos) instanceof BarrelBlockEntity
            || CLIENT.world.getBlockEntity(blockPos) instanceof ShulkerBoxBlockEntity
         : false;
   }

   private void invoke22(BlockPos blockPos) {
      this.blockPos4 = blockPos.toImmutable();
      this.blockPos5 = this.resolve6(this.blockPos4);
      this.autominedropchest.setValue(blockPos.getX() + "," + blockPos.getY() + "," + blockPos.getZ());
      if (WildClient.INSTANCE.configManager != null) {
         WildClient.INSTANCE.configManager.scheduleSave();
      }
   }

   private void invoke23() {
      String text8 = this.autominedropchest.getValue();
      if (text8 != null && !text8.isBlank()) {
         String[] texts3 = text8.split(",");
         if (texts3.length == 3) {
            try {
               this.blockPos4 = new BlockPos(Integer.parseInt(texts3[0]), Integer.parseInt(texts3[1]), Integer.parseInt(texts3[2]));
            } catch (NumberFormatException numberFormatException) {
               this.blockPos4 = null;
            }
         }
      }
   }

   private String resolve8() {
      String text9 = this.anarhiyaDlyaSbrosa.getValue();
      if (text9 == null) {
         return null;
      } else {
         String text10 = text9.trim()
            .toLowerCase()
            .replace("/", "")
            .replace("anarchy", "")
            .replace("анархия", "")
            .replace("an", "")
            .replace("аn", "")
            .replace(" ", "");
         return text10.isBlank() ? null : text10;
      }
   }

   private void invoke24(String string) {
      ChatUtil.sendClientMessage("§8[§6AutoMine§8] §c" + string);
      this.queue.clear();
      this.genericContainerScreen = null;
      this.invoke6();
      this.autoMineState = AutoMine.AutoMineState.IDLE;
      if (CLIENT.player != null) {
         CLIENT.player.closeHandledScreen();
      }

      if (this.enabled) {
         this.toggle();
      }
   }

   private GenericContainerScreen resolve9() {
      GenericContainerScreen genericContainerScreen3 = ScreenUtil.resolve(CLIENT, this.genericContainerScreen, GenericContainerScreen.class);
      if (genericContainerScreen3 == null) {
         this.genericContainerScreen = null;
      }

      return genericContainerScreen3;
   }

   private BlockPos resolve10() {
      return new BlockPos((this.compute5() + this.compute6()) / 2, this.compute7(), (this.compute9() + this.compute10()) / 2);
   }

   private int compute5() {
      return Math.min(BLOCK_POS.getX(), BLOCK_POS_2.getX());
   }

   private int compute6() {
      return Math.max(BLOCK_POS.getX(), BLOCK_POS_2.getX());
   }

   private int compute7() {
      return Math.min(BLOCK_POS.getY(), BLOCK_POS_2.getY());
   }

   private int compute8() {
      return Math.max(BLOCK_POS.getY(), BLOCK_POS_2.getY());
   }

   private int compute9() {
      return Math.min(BLOCK_POS.getZ(), BLOCK_POS_2.getZ());
   }

   private int compute10() {
      return Math.max(BLOCK_POS.getZ(), BLOCK_POS_2.getZ());
   }

   @EventHandler
   public void onRender3D(Render3DEvent render3DEvent) {
      if (CLIENT.world != null && CLIENT.player != null) {
         if (this.blockPos4 != null || this.blockPos != null) {
            Immediate immediate = WorldRenderBuffer.getIMMEDIATE();

            try {
               Vec3d vec3d10 = CLIENT.gameRenderer.getCamera().getPos();
               Matrix4f matrix4f2 = render3DEvent.getMatrixStack().peek().getPositionMatrix();
               VertexConsumer vertexConsumer2 = immediate.getBuffer(RENDER_LAYER);
               if (this.blockPos4 != null) {
                  this.invoke25(vertexConsumer2, matrix4f2, this.blockPos4, vec3d10, new Color(150, 50, 255, 120), new Color(150, 50, 255, 0));
               }

               if (this.blockPos != null && !this.check2(this.blockPos)) {
                  this.invoke25(vertexConsumer2, matrix4f2, this.blockPos, vec3d10, new Color(0, 180, 255, 130), new Color(0, 180, 255, 0));
               }
            } finally {
               WorldRenderBuffer.invoke();
            }
         }
      }
   }

   private void invoke25(VertexConsumer vertexConsumer, Matrix4f matrix4f, BlockPos blockPos, Vec3d vec3d, Color color, Color color2) {
      float floatValue3 = (float)(blockPos.getX() - vec3d.x);
      float floatValue4 = (float)(blockPos.getY() - vec3d.y);
      float floatValue5 = (float)(blockPos.getZ() - vec3d.z);
      float floatValue6 = (float)(blockPos.getX() + 1 - vec3d.x);
      float floatValue7 = (float)(blockPos.getY() + 1 - vec3d.y);
      float floatValue8 = (float)(blockPos.getZ() + 1 - vec3d.z);
      this.invoke26(vertexConsumer, matrix4f, floatValue3, floatValue4, floatValue5, floatValue6, floatValue7, floatValue8, color, color2);
   }

   private void invoke26(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float g, float h, float i, float j, float k, Color color, Color color2) {
      int intValue53 = color.getRed();
      int intValue54 = color.getGreen();
      int intValue55 = color.getBlue();
      int intValue56 = color.getAlpha();
      int intValue57 = color2.getRed();
      int intValue58 = color2.getGreen();
      int intValue59 = color2.getBlue();
      int intValue60 = color2.getAlpha();
      vertexConsumer.vertex(matrix4f, f, g, h).color(intValue53, intValue54, intValue55, intValue56);
      vertexConsumer.vertex(matrix4f, i, g, h).color(intValue53, intValue54, intValue55, intValue56);
      vertexConsumer.vertex(matrix4f, i, j, h).color(intValue57, intValue58, intValue59, intValue60);
      vertexConsumer.vertex(matrix4f, f, j, h).color(intValue57, intValue58, intValue59, intValue60);
      vertexConsumer.vertex(matrix4f, f, j, k).color(intValue57, intValue58, intValue59, intValue60);
      vertexConsumer.vertex(matrix4f, i, j, k).color(intValue57, intValue58, intValue59, intValue60);
      vertexConsumer.vertex(matrix4f, i, g, k).color(intValue53, intValue54, intValue55, intValue56);
      vertexConsumer.vertex(matrix4f, f, g, k).color(intValue53, intValue54, intValue55, intValue56);
      vertexConsumer.vertex(matrix4f, f, g, k).color(intValue53, intValue54, intValue55, intValue56);
      vertexConsumer.vertex(matrix4f, f, g, h).color(intValue53, intValue54, intValue55, intValue56);
      vertexConsumer.vertex(matrix4f, f, j, h).color(intValue57, intValue58, intValue59, intValue60);
      vertexConsumer.vertex(matrix4f, f, j, k).color(intValue57, intValue58, intValue59, intValue60);
      vertexConsumer.vertex(matrix4f, i, j, k).color(intValue57, intValue58, intValue59, intValue60);
      vertexConsumer.vertex(matrix4f, i, j, h).color(intValue57, intValue58, intValue59, intValue60);
      vertexConsumer.vertex(matrix4f, i, g, h).color(intValue53, intValue54, intValue55, intValue56);
      vertexConsumer.vertex(matrix4f, i, g, k).color(intValue53, intValue54, intValue55, intValue56);
      vertexConsumer.vertex(matrix4f, f, g, h).color(intValue53, intValue54, intValue55, intValue56);
      vertexConsumer.vertex(matrix4f, f, g, k).color(intValue53, intValue54, intValue55, intValue56);
      vertexConsumer.vertex(matrix4f, i, g, k).color(intValue53, intValue54, intValue55, intValue56);
      vertexConsumer.vertex(matrix4f, i, g, h).color(intValue53, intValue54, intValue55, intValue56);
      vertexConsumer.vertex(matrix4f, f, j, h).color(intValue57, intValue58, intValue59, intValue60);
      vertexConsumer.vertex(matrix4f, i, j, h).color(intValue57, intValue58, intValue59, intValue60);
      vertexConsumer.vertex(matrix4f, i, j, k).color(intValue57, intValue58, intValue59, intValue60);
      vertexConsumer.vertex(matrix4f, f, j, k).color(intValue57, intValue58, intValue59, intValue60);
   }

   record AutoMineEntry(String key, int count) {

      boolean matches(ItemStack itemStack) {
         return !itemStack.isEmpty() && this.count > 0 && this.key.equals(itemStack.getItem().toString() + "|" + itemStack.getName().getString());
      }
   }

   static enum AutoMineState {
      IDLE,
      WAITING_FOR_TP,
      GOING_TO_MINE,
      MINING,
      TELEPORTING_TO_DROP,
      GOING_TO_DROP_CHEST,
      ROTATING_DROP_CHEST,
      OPENING_DROP_CHEST,
      WAITING_FOR_DROP_GUI,
      DROPPING,
      CHANGING_ANARCHY;
   }
}
