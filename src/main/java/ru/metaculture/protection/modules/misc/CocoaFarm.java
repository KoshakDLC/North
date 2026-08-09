package ru.metaculture.protection;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.pathing.goals.GoalNear;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline.Snippet;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.vertex.VertexFormat.DrawMode;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lombok.Generated;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CocoaBlock;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.RenderLayer.MultiPhaseParameters;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Direction.Type;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import org.joml.Matrix4f;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "CocoaFarm",
   category = Category.Misc,
   description = "Авто-ферма какао-бобов на тропических брёвнах"
)
public class CocoaFarm extends Module {
   private static BlockPos blockPos;
   private static BlockPos blockPos2;
   public final BooleanSetting avtoPosadka = new BooleanSetting("Авто-посадка", true);
   public final BooleanSetting skladVSunduk = new BooleanSetting("Склад в сундук", true);
   public final BooleanSetting logi = new BooleanSetting("Логи", true);
   private static final double DOUBLE_VALUE = 4.6;
   private static final double DOUBLE_VALUE_2 = 3.6;
   private static final float FLOAT_VALUE = 60.0F;
   private static final float FLOAT_VALUE_2 = 5.0F;
   private static final float FLOAT_VALUE_3 = 0.5F;
   private static final float FLOAT_VALUE_4 = 4.0F;
   private static final long TIMESTAMP = 300L;
   private static final int INT_VALUE = 32;
   private static final long TIMESTAMP_2 = 300L;
   private static final long TIMESTAMP_3 = 2500L;
   private static final long TIMESTAMP_4 = 5000L;
   private static final long TIMESTAMP_5 = 4000L;
   private static final long TIMESTAMP_6 = 12000L;
   private static final long TIMESTAMP_7 = 30000L;
   private static final int INT_VALUE_2 = 6;
   private static final double DOUBLE_VALUE_3 = 4.2;
   private static final long TIMESTAMP_8 = 6000L;
   private static final int INT_VALUE_3 = 3;
   private static final long TIMESTAMP_9 = 30000L;
   private final DualTimer dualTimer = new DualTimer();
   private final DualTimer dualTimer2 = new DualTimer();
   private final DualTimer dualTimer3 = new DualTimer();
   private final DualTimer dualTimer4 = new DualTimer();
   private final DualTimer dualTimer5 = new DualTimer();
   private final DualTimer dualTimer6 = new DualTimer();
   private final List<CocoaFarm.CocoaFarmState3> items = new ArrayList<>();
   private final HashMap<BlockPos, Long> hashMap = new HashMap<>();
   private final HashMap<Integer, Long> hashMap2 = new HashMap<>();
   private final HashMap<BlockPos, long[]> hashMap3 = new HashMap<>();
   private CocoaFarm.CocoaFarmState2 cocoaFarmState2 = CocoaFarm.CocoaFarmState2.FARM;
   private CocoaFarm.CocoaFarmState3 cocoaFarmState3;
   private BlockPos blockPos3;
   private BlockPos blockPos4;
   private BlockPos blockPos5;
   private BlockPos blockPos6;
   private int intValue = -1;
   private int intValue2;
   private int intValue3 = -1;
   private int intValue4;
   private long timestamp;
   private long timestamp2;
   private double doubleValue;
   private int intValue5;
   private boolean flag;
   private int intValue6;
   private int intValue7 = -1;
   private boolean flag2;
   private boolean flag3;
   private boolean flag4;
   private static final int[] INTS = new int[]{0, -1, 1, -2, 2, -3, -4};
   private static final int INT_VALUE_4 = 4096;
   private static final RenderPipeline RENDER_PIPELINE = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.POSITION_COLOR_SNIPPET})
         .withLocation(Identifier.of("wild", "cocoa_zone_fill"))
         .withVertexFormat(VertexFormats.POSITION_COLOR, DrawMode.QUADS)
         .withCull(false)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .withBlend(BlendFunction.TRANSLUCENT)
         .build()
   );
   private static final RenderPipeline RENDER_PIPELINE_2 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.POSITION_COLOR_SNIPPET})
         .withLocation(Identifier.of("wild", "cocoa_zone_glow"))
         .withVertexFormat(VertexFormats.POSITION_COLOR, DrawMode.QUADS)
         .withCull(false)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .withBlend(BlendFunction.LIGHTNING)
         .build()
   );
   private static final RenderLayer RENDER_LAYER = RenderLayer.of(
      "cocoa_zone_fill", 4096, false, true, RENDER_PIPELINE, MultiPhaseParameters.builder().build(false)
   );
   private static final RenderLayer RENDER_LAYER_2 = RenderLayer.of(
      "cocoa_zone_glow", 4096, false, true, RENDER_PIPELINE_2, MultiPhaseParameters.builder().build(false)
   );
   private static final int INT_VALUE_5 = -65409;
   private static final int INT_VALUE_6 = -8781569;
   private static final int INT_VALUE_7 = 657938;
   private static final int INT_VALUE_8 = 20;

   public static void invoke() {
      blockPos = null;
      blockPos2 = null;
   }

   public CocoaFarm() {
      this.addSettings(new Setting[]{this.avtoPosadka, this.skladVSunduk});

      try {
         BaritoneAPI.getSettings().chunkCaching.value = false;
      } catch (Throwable exception) {
      }
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.cocoaFarmState2 = CocoaFarm.CocoaFarmState2.FARM;
      this.items.clear();
      this.hashMap.clear();
      this.cocoaFarmState3 = null;
      this.blockPos3 = null;
      this.blockPos4 = null;
      this.blockPos5 = null;
      this.blockPos6 = null;
      this.intValue = -1;
      this.intValue2 = 0;
      this.intValue3 = -1;
      this.intValue4 = 0;
      this.timestamp = 0L;
      this.timestamp2 = 0L;
      this.doubleValue = Double.MAX_VALUE;
      this.intValue5 = 0;
      this.flag = false;
      this.intValue6 = 0;
      this.intValue7 = -1;
      this.hashMap2.clear();
      this.hashMap3.clear();
      this.dualTimer2.invoke();
      this.dualTimer.invoke();
      this.dualTimer3.invoke();
      this.dualTimer4.invoke();
      this.dualTimer6.invoke();
      this.flag2 = (Boolean)BaritoneAPI.getSettings().allowBreak.value;
      this.flag3 = (Boolean)BaritoneAPI.getSettings().allowPlace.value;
      this.flag4 = (Boolean)BaritoneAPI.getSettings().allowSprint.value;
      BaritoneAPI.getSettings().allowBreak.value = false;
      BaritoneAPI.getSettings().allowPlace.value = false;
      BaritoneAPI.getSettings().chunkCaching.value = false;
      if (blockPos != null && blockPos2 != null) {
         this.invoke18("Запуск, зона " + this.resolve16(blockPos) + " — " + this.resolve16(blockPos2));
      } else {
         ChatUtil.sendClientMessage("§c[CocoaFarm] §fСначала задайте зону: §e.cocoa pos1 §fи §e.cocoa pos2");
      }
   }

   @Override
   public void onDisable() {
      this.invoke15();
      BaritoneAPI.getSettings().allowBreak.value = this.flag2;
      BaritoneAPI.getSettings().allowPlace.value = this.flag3;
      BaritoneAPI.getSettings().allowSprint.value = this.flag4;
      RotationController.rotationControllerState2 = RotationController.RotationControllerState2.IDLE;
      RotationController.intValue = 0;
      RotationController.rotation = null;
      FreeLookController.active = false;
      this.blockPos4 = null;
      this.cocoaFarmState3 = null;
      super.onDisable();
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player != null && CLIENT.world != null && CLIENT.interactionManager != null) {
         if (blockPos != null && blockPos2 != null) {
            if (PlayerHelper.check()) {
               this.invoke15();
            } else if (this.cocoaFarmState2 != CocoaFarm.CocoaFarmState2.FARM && System.currentTimeMillis() > this.timestamp) {
               this.invoke18("Тайм-аут депозит-сессии, блокирую сундук");
               this.invoke8(true);
            } else {
               switch (this.cocoaFarmState2) {
                  case FARM:
                     this.invoke2();
                     break;
                  case NAVIGATING:
                     this.invoke9();
                     break;
                  case INTERACTING:
                     this.invoke10();
                     break;
                  case WAITING_FOR_CONTAINER:
                     this.invoke11();
                     break;
                  case DEPOSITING:
                     this.invoke12();
               }
            }
         }
      }
   }

   private void invoke2() {
      if (this.skladVSunduk.isEnabled()
         && System.currentTimeMillis() >= this.timestamp2
         && (this.compute5() == 0 || this.compute4() >= 4)
         && this.compute4() > 1) {
         BlockPos blockPos2 = this.resolve7();
         if (blockPos2 != null) {
            this.blockPos6 = blockPos2;
            this.cocoaFarmState2 = CocoaFarm.CocoaFarmState2.NAVIGATING;
            this.dualTimer5.invoke();
            this.dualTimer3.invoke();
            this.doubleValue = Double.MAX_VALUE;
            this.invoke15();
            this.intValue2 = 0;
            this.intValue4 = 0;
            this.intValue3 = -1;
            this.timestamp = System.currentTimeMillis() + 30000L;
            this.invoke18("Инвентарь полон, иду к сундуку " + this.resolve16(blockPos2));
            return;
         }
      }

      if (this.cocoaFarmState3 == null || !this.check4(this.cocoaFarmState3)) {
         this.cocoaFarmState3 = null;
         if (this.dualTimer2.check5(300L)) {
            this.invoke13();
            this.dualTimer2.invoke();
         }

         CocoaFarm.CocoaFarmState3 cocoaFarmState3 = this.resolve();
         if (cocoaFarmState3 != null && this.check2(cocoaFarmState3.blockPos)) {
            cocoaFarmState3 = null;
         }

         if (cocoaFarmState3 != null && !cocoaFarmState3.blockPos.equals(this.blockPos3)) {
            this.blockPos3 = cocoaFarmState3.blockPos;
            this.intValue5 = 0;
            this.dualTimer3.invoke();
            this.doubleValue = Double.MAX_VALUE;
         }

         this.cocoaFarmState3 = cocoaFarmState3;
      }

      if (!this.check7()) {
         if (this.cocoaFarmState3 == null) {
            if (!this.flag) {
               this.invoke18("Целей нет, жду созревания");
               this.flag = true;
            }

            this.invoke15();
         } else {
            this.flag = false;
            BlockPos blockPos3 = this.resolve3(this.cocoaFarmState3);
            double doubleValue = CLIENT.player.getX() - (blockPos3.getX() + 0.5);
            double doubleValue2 = CLIENT.player.getZ() - (blockPos3.getZ() + 0.5);
            boolean flag = doubleValue * doubleValue + doubleValue2 * doubleValue2 <= 1.44;
            double doubleValue3 = CLIENT.player.getEyePos().distanceTo(this.resolve2(this.cocoaFarmState3));
            if (doubleValue3 > 4.6 || !flag && doubleValue3 > 3.6) {
               if (!this.check(doubleValue3, blockPos3)) {
                  this.invoke3("не могу дойти до " + this.resolve16(this.cocoaFarmState3.blockPos) + " (дист " + Math.round(doubleValue3 * 10.0) / 10.0 + ")");
               }
            } else {
               if (this.cocoaFarmState3.cocoaFarmState == CocoaFarm.CocoaFarmState.HARVEST) {
                  this.invoke5(this.cocoaFarmState3);
               } else {
                  this.invoke6(this.cocoaFarmState3);
               }
            }
         }
      }
   }

   private boolean check(double d, BlockPos blockPos) {
      if (d < this.doubleValue - 0.4) {
         this.doubleValue = d;
         this.dualTimer3.invoke();
      }

      if (this.dualTimer3.check5(5000L)) {
         return false;
      } else {
         this.invoke14(blockPos);
         return true;
      }
   }

   private void invoke3(String string) {
      this.invoke18("Пропуск: " + string);
      this.hashMap.put(this.cocoaFarmState3.blockPos, System.currentTimeMillis() + 12000L);
      this.cocoaFarmState3 = null;
      this.blockPos4 = null;
      this.invoke15();
   }

   private void invoke4(BlockPos blockPos) {
      CLIENT.player.networkHandler.sendPacket(new PlayerActionC2SPacket(Action.ABORT_DESTROY_BLOCK, blockPos, Direction.DOWN));
   }

   private boolean check2(BlockPos blockPos) {
      long longValue = System.currentTimeMillis();
      long[] longValues = this.hashMap3.get(blockPos);
      if (longValues != null && longValue - longValues[1] <= 6000L) {
         longValues[0]++;
         longValues[1] = longValue;
         if (longValues[0] >= 3L) {
            this.hashMap3.remove(blockPos);
            this.hashMap.put(blockPos, longValue + 30000L);
            this.invoke4(blockPos);
            this.invoke18("Фантомный блок " + this.resolve16(blockPos) + ", ресинк и пропуск");
            return true;
         } else {
            return false;
         }
      } else {
         this.hashMap3.put(blockPos.toImmutable(), new long[]{1L, longValue});
         if (this.hashMap3.size() > 128) {
            this.hashMap3.entrySet().removeIf(entry -> longValue - entry.getValue()[1] > 6000L);
         }

         return false;
      }
   }

   private void invoke5(CocoaFarm.CocoaFarmState3 cocoaFarmState32) {
      BlockHitResult blockHitResult = this.resolve8(cocoaFarmState32);
      if (blockHitResult != null && !(CLIENT.player.getEyePos().distanceTo(blockHitResult.getPos()) > 4.2)) {
         this.dualTimer3.invoke();
         this.invoke15();
         Rotation rotation = this.resolve15(blockHitResult.getPos());
         this.invoke17(rotation);
         if (!(new Rotation(CLIENT.player).measure(rotation) > 4.0F)) {
            if (!this.check11()) {
               BlockHitResult blockHitResult2 = this.resolve14();
               BlockHitResult blockHitResult3 = blockHitResult2 != null && blockHitResult2.getBlockPos().equals(cocoaFarmState32.blockPos) ? blockHitResult2 : blockHitResult;
               if (!cocoaFarmState32.blockPos.equals(this.blockPos4)) {
                  if (!this.dualTimer.check5(300L)) {
                     return;
                  }

                  CLIENT.interactionManager.attackBlock(cocoaFarmState32.blockPos, blockHitResult3.getSide());
                  this.blockPos4 = cocoaFarmState32.blockPos;
                  this.dualTimer4.invoke();
                  this.dualTimer.invoke();
               } else {
                  if (this.dualTimer4.check5(4000L)) {
                     this.invoke4(cocoaFarmState32.blockPos);
                     this.invoke3("какао " + this.resolve16(cocoaFarmState32.blockPos) + " не ломается, ресинк фантома");
                     return;
                  }

                  CLIENT.interactionManager.updateBlockBreakingProgress(cocoaFarmState32.blockPos, blockHitResult3.getSide());
               }

               CLIENT.player.swingHand(Hand.MAIN_HAND);
            }
         }
      } else {
         this.invoke7(cocoaFarmState32);
      }
   }

   private void invoke6(CocoaFarm.CocoaFarmState3 cocoaFarmState33) {
      if (!this.check13()) {
         this.invoke18("Бобы закончились, посадка недоступна");
         this.cocoaFarmState3 = null;
      } else if (!this.check12()) {
         if (CLIENT.player.getMainHandStack().isOf(Items.COCOA_BEANS)) {
            if (this.intValue5 >= 6) {
               this.invoke4(cocoaFarmState33.blockPos);
               this.invoke4(cocoaFarmState33.blockPos.offset(cocoaFarmState33.direction));
               this.invoke3("посадка " + this.resolve16(cocoaFarmState33.blockPos.offset(cocoaFarmState33.direction)) + " не проходит, ресинк фантома");
            } else {
               BlockHitResult blockHitResult4 = this.resolve9(cocoaFarmState33.blockPos, cocoaFarmState33.direction);
               if (blockHitResult4 != null && !(CLIENT.player.getEyePos().distanceTo(blockHitResult4.getPos()) > 4.2)) {
                  this.dualTimer3.invoke();
                  this.invoke15();
                  Rotation rotation2 = this.resolve15(blockHitResult4.getPos());
                  this.invoke17(rotation2);
                  if (!(new Rotation(CLIENT.player).measure(rotation2) > 4.0F)) {
                     if (this.dualTimer.check5(300L)) {
                        BlockHitResult blockHitResult5 = this.resolve14();
                        BlockHitResult blockHitResult6 = blockHitResult5 != null && blockHitResult5.getBlockPos().equals(cocoaFarmState33.blockPos) && blockHitResult5.getSide() == cocoaFarmState33.direction
                           ? blockHitResult5
                           : blockHitResult4;
                        CLIENT.interactionManager.interactBlock(CLIENT.player, Hand.MAIN_HAND, blockHitResult6);
                        CLIENT.player.swingHand(Hand.MAIN_HAND);
                        this.intValue5++;
                        this.dualTimer.invoke();
                        this.blockPos4 = null;
                     }
                  }
               } else {
                  this.invoke7(cocoaFarmState33);
               }
            }
         }
      }
   }

   private void invoke7(CocoaFarm.CocoaFarmState3 cocoaFarmState34) {
      BlockPos blockPos4 = this.resolve3(cocoaFarmState34);
      double doubleValue4 = CLIENT.player.getX() - (blockPos4.getX() + 0.5);
      double doubleValue5 = CLIENT.player.getZ() - (blockPos4.getZ() + 0.5);
      double doubleValue6 = doubleValue4 * doubleValue4 + doubleValue5 * doubleValue5;
      if (doubleValue6 > 2.5) {
         if (!this.check(Math.sqrt(doubleValue6), blockPos4)) {
            this.invoke3("не могу подойти к " + this.resolve16(cocoaFarmState34.blockPos));
         }
      } else {
         if (this.dualTimer3.check5(2500L)) {
            this.invoke3("нет прямой видимости " + this.resolve16(cocoaFarmState34.blockPos));
         }
      }
   }

   private boolean check3() {
      return this.blockPos6 != null && this.check9(CLIENT.world.getBlockState(this.blockPos6));
   }

   private void invoke8(boolean bl) {
      if (bl) {
         this.timestamp2 = System.currentTimeMillis() + 30000L;
      }

      this.blockPos6 = null;
      this.intValue = -1;
      this.invoke15();
      this.cocoaFarmState2 = CocoaFarm.CocoaFarmState2.FARM;
   }

   private void invoke9() {
      if (!this.check3()) {
         this.invoke8(false);
      } else if (this.dualTimer5.check5(15000L)) {
         this.invoke18("Не смог дойти до сундука, вернусь позже");
         this.invoke8(true);
      } else if (CLIENT.player.getEyePos().distanceTo(Vec3d.ofCenter(this.blockPos6)) <= 4.5) {
         this.invoke15();
         this.dualTimer.invoke();
         this.cocoaFarmState2 = CocoaFarm.CocoaFarmState2.INTERACTING;
      } else {
         this.invoke14(this.blockPos6);
      }
   }

   private void invoke10() {
      if (!this.check3()) {
         this.invoke8(false);
      } else if (this.intValue2 >= 3) {
         this.invoke18("Сундук не открывается, блокирую");
         this.invoke8(true);
      } else {
         this.invoke15();
         if (CLIENT.player.getEyePos().distanceTo(Vec3d.ofCenter(this.blockPos6)) > 4.6) {
            this.dualTimer5.invoke();
            this.cocoaFarmState2 = CocoaFarm.CocoaFarmState2.NAVIGATING;
         } else {
            BlockHitResult blockHitResult7 = this.resolve10(this.blockPos6);
            Vec3d vec3d3 = blockHitResult7 != null ? blockHitResult7.getPos() : Vec3d.ofCenter(this.blockPos6);
            Rotation rotation3 = this.resolve15(vec3d3);
            this.invoke17(rotation3);
            if (!(new Rotation(CLIENT.player).measure(rotation3) > 4.0F)) {
               if (this.dualTimer.check5(300L)) {
                  BlockHitResult blockHitResult8 = blockHitResult7 != null ? blockHitResult7 : new BlockHitResult(vec3d3, Direction.UP, this.blockPos6, false);
                  CLIENT.interactionManager.interactBlock(CLIENT.player, Hand.MAIN_HAND, blockHitResult8);
                  CLIENT.player.swingHand(Hand.MAIN_HAND);
                  this.intValue2++;
                  this.intValue = -1;
                  this.dualTimer5.invoke();
                  this.dualTimer.invoke();
                  this.cocoaFarmState2 = CocoaFarm.CocoaFarmState2.WAITING_FOR_CONTAINER;
               }
            }
         }
      }
   }

   private void invoke11() {
      this.invoke15();
      if (CLIENT.currentScreen instanceof GenericContainerScreen genericContainerScreen) {
         int intValue = ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).syncId;
         if (CLIENT.player.currentScreenHandler != null && CLIENT.player.currentScreenHandler.syncId == intValue) {
            this.intValue = intValue;
            this.intValue3 = -1;
            this.intValue4 = 0;
            this.dualTimer5.invoke();
            this.cocoaFarmState2 = CocoaFarm.CocoaFarmState2.DEPOSITING;
            return;
         }
      }

      if (this.dualTimer5.check5(4000L)) {
         this.invoke18("Сундук не ответил открытием, повтор подхода");
         this.dualTimer5.invoke();
         this.cocoaFarmState2 = CocoaFarm.CocoaFarmState2.NAVIGATING;
      }
   }

   private void invoke12() {
      if (!(
         CLIENT.currentScreen instanceof GenericContainerScreen genericContainerScreen2
            && CLIENT.player.currentScreenHandler != null
            && CLIENT.player.currentScreenHandler.syncId == this.intValue
            && ((GenericContainerScreenHandler)genericContainerScreen2.getScreenHandler()).syncId == this.intValue
      )) {
         this.invoke8(false);
      } else if (this.dualTimer5.check5(50L)) {
         int intValue2 = this.compute();
         if (this.intValue3 >= 0 && intValue2 >= this.intValue3) {
            this.intValue4++;
         } else {
            this.intValue4 = 0;
         }

         this.intValue3 = intValue2;
         if (this.intValue4 >= 3) {
            ChatUtil.sendClientMessage("§c[CocoaFarm] §fСундук заполнен, освободите место");
            CLIENT.player.closeHandledScreen();
            this.invoke8(true);
         } else {
            GenericContainerScreenHandler genericContainerScreenHandler2 = (GenericContainerScreenHandler)genericContainerScreen2.getScreenHandler();
            int intValue3 = genericContainerScreenHandler2.getRows() * 9;
            int intValue4 = this.compute2(genericContainerScreenHandler2, intValue3);
            if (intValue4 == -1) {
               CLIENT.player.closeHandledScreen();
               this.invoke18("Депозит завершён");
               this.invoke8(false);
            } else {
               CLIENT.interactionManager.clickSlot(this.intValue, intValue4, 0, SlotActionType.QUICK_MOVE, CLIENT.player);
               this.dualTimer5.invoke();
            }
         }
      }
   }

   private int compute() {
      int intValue5 = 0;

      for (int intValue6 = 0; intValue6 < 36; intValue6++) {
         ItemStack itemStack2 = CLIENT.player.getInventory().getStack(intValue6);
         if (itemStack2.isOf(Items.COCOA_BEANS)) {
            intValue5 += itemStack2.getCount();
         }
      }

      return intValue5;
   }

   private int compute2(GenericContainerScreenHandler genericContainerScreenHandler, int i) {
      boolean flag2 = this.avtoPosadka.isEnabled();
      boolean flag3 = false;

      for (int intValue7 = i; intValue7 < genericContainerScreenHandler.slots.size(); intValue7++) {
         Slot slot = genericContainerScreenHandler.getSlot(intValue7);
         if (slot.hasStack() && slot.getStack().isOf(Items.COCOA_BEANS)) {
            if (!flag2 || flag3) {
               return intValue7;
            }

            flag3 = true;
         }
      }

      return -1;
   }

   private void invoke13() {
      this.items.clear();
      boolean flag4 = this.avtoPosadka.isEnabled() && this.check13();
      int[] intValues = this.resolve6();
      int intValue8 = 0;
      int intValue9 = 0;

      for (BlockPos blockPos5 : BlockPos.iterate(intValues[0], intValues[1], intValues[2], intValues[3], intValues[4], intValues[5])) {
         BlockState blockState2 = CLIENT.world.getBlockState(blockPos5);
         if (blockState2.isIn(BlockTags.JUNGLE_LOGS)) {
            BlockPos blockPos6 = blockPos5.toImmutable();

            for (Direction direction2 : Type.HORIZONTAL) {
               BlockPos blockPos7 = blockPos6.offset(direction2);
               if (!this.check10(blockPos7) && !this.check10(blockPos6)) {
                  BlockState blockState3 = CLIENT.world.getBlockState(blockPos7);
                  if (blockState3.isOf(Blocks.COCOA)) {
                     if ((Integer)blockState3.get(CocoaBlock.AGE) >= 2) {
                        this.items.add(new CocoaFarm.CocoaFarmState3(CocoaFarm.CocoaFarmState.HARVEST, blockPos7, direction2));
                        intValue8++;
                     }
                  } else if (flag4 && (blockState3.isAir() || blockState3.isReplaceable())) {
                     this.items.add(new CocoaFarm.CocoaFarmState3(CocoaFarm.CocoaFarmState.PLANT, blockPos6, direction2));
                     intValue9++;
                  }
               }
            }
         }
      }

      int intValue10 = intValue8 + intValue9;
      if (intValue10 > 0 && this.intValue6 == 0) {
         this.invoke18("Найдено целей: сбор " + intValue8 + ", посадка " + intValue9);
      }

      this.intValue6 = intValue10;
   }

   private CocoaFarm.CocoaFarmState3 resolve() {
      Vec3d vec3d4 = CLIENT.player.getEyePos();
      CocoaFarm.CocoaFarmState3 cocoaFarmState35 = null;
      double doubleValue7 = Double.MAX_VALUE;

      for (CocoaFarm.CocoaFarmState3 cocoaFarmState36 : this.items) {
         if (this.check4(cocoaFarmState36) && !this.check10(cocoaFarmState36.blockPos)) {
            double doubleValue8 = vec3d4.squaredDistanceTo(this.resolve2(cocoaFarmState36));
            if (cocoaFarmState36.cocoaFarmState == CocoaFarm.CocoaFarmState.PLANT) {
               doubleValue8 += 0.001;
            }

            if (doubleValue8 < doubleValue7) {
               doubleValue7 = doubleValue8;
               cocoaFarmState35 = cocoaFarmState36;
            }
         }
      }

      return cocoaFarmState35;
   }

   private boolean check4(CocoaFarm.CocoaFarmState3 cocoaFarmState37) {
      if (cocoaFarmState37 == null) {
         return false;
      } else if (cocoaFarmState37.cocoaFarmState == CocoaFarm.CocoaFarmState.HARVEST) {
         BlockState blockState4 = CLIENT.world.getBlockState(cocoaFarmState37.blockPos);
         return blockState4.isOf(Blocks.COCOA) && (Integer)blockState4.get(CocoaBlock.AGE) >= 2;
      } else if (!this.check13()) {
         return false;
      } else {
         BlockState blockState5 = CLIENT.world.getBlockState(cocoaFarmState37.blockPos);
         if (!blockState5.isIn(BlockTags.JUNGLE_LOGS)) {
            return false;
         } else {
            BlockState blockState6 = CLIENT.world.getBlockState(cocoaFarmState37.blockPos.offset(cocoaFarmState37.direction));
            return blockState6.isAir() || blockState6.isReplaceable();
         }
      }
   }

   private Vec3d resolve2(CocoaFarm.CocoaFarmState3 cocoaFarmState38) {
      return cocoaFarmState38.cocoaFarmState == CocoaFarm.CocoaFarmState.HARVEST
         ? Vec3d.ofCenter(cocoaFarmState38.blockPos)
         : this.resolve13(cocoaFarmState38.blockPos, cocoaFarmState38.direction);
   }

   private BlockPos resolve3(CocoaFarm.CocoaFarmState3 cocoaFarmState39) {
      BlockPos blockPos8 = cocoaFarmState39.cocoaFarmState == CocoaFarm.CocoaFarmState.HARVEST ? cocoaFarmState39.blockPos : cocoaFarmState39.blockPos.offset(cocoaFarmState39.direction);
      BlockPos blockPos9 = cocoaFarmState39.cocoaFarmState == CocoaFarm.CocoaFarmState.HARVEST
         ? cocoaFarmState39.blockPos.offset(cocoaFarmState39.direction)
         : cocoaFarmState39.blockPos.offset(cocoaFarmState39.direction, 2);
      int intValue11 = CLIENT.player.getBlockPos().getY();
      BlockPos[] blockPosValues = new BlockPos[]{blockPos8, blockPos9};

      for (BlockPos blockPos10 : blockPosValues) {
         for (int intValue12 : INTS) {
            BlockPos blockPos11 = new BlockPos(blockPos10.getX(), intValue11 + intValue12, blockPos10.getZ());
            if (this.check5(blockPos11) && this.check6(blockPos11)) {
               return blockPos11;
            }
         }
      }

      return new BlockPos(blockPos8.getX(), intValue11, blockPos8.getZ());
   }

   private boolean check5(BlockPos blockPos) {
      int intValue13 = Math.min(blockPos.getX(), blockPos2.getX());
      int intValue14 = Math.max(blockPos.getX(), blockPos2.getX());
      int intValue15 = Math.min(blockPos.getY(), blockPos2.getY());
      int intValue16 = Math.max(blockPos.getY(), blockPos2.getY());
      int intValue17 = Math.min(blockPos.getZ(), blockPos2.getZ());
      int intValue18 = Math.max(blockPos.getZ(), blockPos2.getZ());
      return blockPos.getX() >= intValue13
         && blockPos.getX() <= intValue14
         && blockPos.getY() >= intValue15
         && blockPos.getY() <= intValue16
         && blockPos.getZ() >= intValue17
         && blockPos.getZ() <= intValue18;
   }

   private boolean check6(BlockPos blockPos) {
      BlockState blockState7 = CLIENT.world.getBlockState(blockPos);
      BlockState blockState8 = CLIENT.world.getBlockState(blockPos.up());
      BlockState blockState9 = CLIENT.world.getBlockState(blockPos.down());
      boolean flag5 = blockState7.isAir() || blockState7.getCollisionShape(CLIENT.world, blockPos).isEmpty();
      boolean flag6 = blockState8.isAir() || blockState8.getCollisionShape(CLIENT.world, blockPos.up()).isEmpty();
      boolean flag7 = !blockState9.isAir() && !blockState9.isOf(Blocks.COCOA) && !blockState9.getCollisionShape(CLIENT.world, blockPos.down()).isEmpty();
      return flag5 && flag6 && flag7;
   }

   private void invoke14(BlockPos blockPos) {
      IBaritone iBaritone = BaritoneAPI.getProvider().getPrimaryBaritone();
      boolean flag8 = !blockPos.equals(this.blockPos5);
      if (flag8 || !iBaritone.getCustomGoalProcess().isActive()) {
         iBaritone.getCustomGoalProcess().setGoalAndPath(new GoalNear(blockPos, 1));
         if (flag8) {
            this.invoke18("Иду к " + this.resolve16(blockPos));
         }

         this.blockPos5 = blockPos;
      }
   }

   private void invoke15() {
      IBaritone iBaritone2 = BaritoneAPI.getProvider().getPrimaryBaritone();
      if (iBaritone2.getCustomGoalProcess().isActive()) {
         iBaritone2.getPathingBehavior().cancelEverything();
      }

      this.blockPos5 = null;
   }

   private boolean check7() {
      if (!this.check8()) {
         return false;
      } else {
         ItemEntity itemEntity2 = this.resolve5();
         if (itemEntity2 == null) {
            this.intValue7 = -1;
            return false;
         } else {
            double doubleValue9 = CLIENT.player.getX() - itemEntity2.getX();
            double doubleValue10 = CLIENT.player.getZ() - itemEntity2.getZ();
            double doubleValue11 = doubleValue9 * doubleValue9 + doubleValue10 * doubleValue10;
            boolean flag9 = this.cocoaFarmState3 == null;
            if (doubleValue11 > 36.0 && !flag9) {
               return false;
            } else if (doubleValue11 <= 1.7) {
               this.intValue7 = -1;
               return false;
            } else {
               if (itemEntity2.getId() != this.intValue7) {
                  this.intValue7 = itemEntity2.getId();
                  this.dualTimer6.invoke();
               }

               if (this.dualTimer6.check5(8000L)) {
                  this.hashMap2.put(itemEntity2.getId(), System.currentTimeMillis() + 45000L);
                  this.intValue7 = -1;
                  return false;
               } else {
                  this.invoke16(this.resolve4(itemEntity2));
                  return true;
               }
            }
         }
      }
   }

   private BlockPos resolve4(ItemEntity itemEntity) {
      BlockPos blockPos12 = BlockPos.ofFloored(itemEntity.getX(), itemEntity.getY() + 0.1, itemEntity.getZ());
      int intValue19 = CLIENT.player.getBlockPos().getY();

      for (int intValue20 : INTS) {
         BlockPos blockPos13 = new BlockPos(blockPos12.getX(), intValue19 + intValue20, blockPos12.getZ());
         if (this.check5(blockPos13) && this.check6(blockPos13)) {
            return blockPos13;
         }
      }

      return new BlockPos(blockPos12.getX(), intValue19, blockPos12.getZ());
   }

   private ItemEntity resolve5() {
      Box box = Box.enclosing(blockPos, blockPos2).expand(1.0);
      List items = CLIENT.world
         .getEntitiesByClass(ItemEntity.class, box, itemEntity -> itemEntity.isAlive() && itemEntity.getStack().isOf(Items.COCOA_BEANS));
      ItemEntity itemEntity3 = null;
      double doubleValue12 = Double.MAX_VALUE;
      long longValue2 = System.currentTimeMillis();

      for (ItemEntity itemEntity4 : (Iterable<ItemEntity>)items) {
         Long longValue3 = this.hashMap2.get(itemEntity4.getId());
         if (longValue3 != null) {
            if (longValue2 <= longValue3) {
               continue;
            }

            this.hashMap2.remove(itemEntity4.getId());
         }

         double doubleValue13 = CLIENT.player.squaredDistanceTo(itemEntity4);
         if (doubleValue13 < doubleValue12) {
            doubleValue12 = doubleValue13;
            itemEntity3 = itemEntity4;
         }
      }

      return itemEntity3;
   }

   private boolean check8() {
      for (int intValue21 = 0; intValue21 < 36; intValue21++) {
         ItemStack itemStack3 = CLIENT.player.getInventory().getStack(intValue21);
         if (itemStack3.isEmpty()) {
            return true;
         }

         if (itemStack3.isOf(Items.COCOA_BEANS) && itemStack3.getCount() < itemStack3.getMaxCount()) {
            return true;
         }
      }

      return false;
   }

   private void invoke16(BlockPos blockPos) {
      IBaritone iBaritone3 = BaritoneAPI.getProvider().getPrimaryBaritone();
      boolean flag10 = !blockPos.equals(this.blockPos5);
      if (flag10 || !iBaritone3.getCustomGoalProcess().isActive()) {
         iBaritone3.getCustomGoalProcess().setGoalAndPath(new GoalNear(blockPos, 1));
         if (flag10) {
            this.invoke18("Подбираю лут " + this.resolve16(blockPos));
         }

         this.blockPos5 = blockPos;
      }
   }

   private int[] resolve6() {
      int intValue22 = Math.min(blockPos.getX(), blockPos2.getX());
      int intValue23 = Math.min(blockPos.getY(), blockPos2.getY());
      int intValue24 = Math.min(blockPos.getZ(), blockPos2.getZ());
      int intValue25 = Math.max(blockPos.getX(), blockPos2.getX());
      int intValue26 = Math.max(blockPos.getY(), blockPos2.getY());
      int intValue27 = Math.max(blockPos.getZ(), blockPos2.getZ());
      BlockPos blockPos14 = CLIENT.player.getBlockPos();
      intValue22 = Math.max(intValue22, blockPos14.getX() - 32);
      intValue24 = Math.max(intValue24, blockPos14.getZ() - 32);
      intValue25 = Math.min(intValue25, blockPos14.getX() + 32);
      intValue27 = Math.min(intValue27, blockPos14.getZ() + 32);
      return new int[]{intValue22, intValue23, intValue24, intValue25, intValue26, intValue27};
   }

   private BlockPos resolve7() {
      int[] intValues2 = this.resolve6();
      Vec3d vec3d5 = CLIENT.player.getEyePos();
      BlockPos blockPos15 = null;
      double doubleValue14 = Double.MAX_VALUE;

      for (BlockPos blockPos16 : BlockPos.iterate(intValues2[0], intValues2[1], intValues2[2], intValues2[3], intValues2[4], intValues2[5])) {
         if (this.check9(CLIENT.world.getBlockState(blockPos16))) {
            double doubleValue15 = vec3d5.squaredDistanceTo(Vec3d.ofCenter(blockPos16));
            if (doubleValue15 < doubleValue14) {
               doubleValue14 = doubleValue15;
               blockPos15 = blockPos16.toImmutable();
            }
         }
      }

      return blockPos15;
   }

   private boolean check9(BlockState blockState) {
      return blockState.isOf(Blocks.CHEST) || blockState.isOf(Blocks.TRAPPED_CHEST) || blockState.isOf(Blocks.BARREL);
   }

   private boolean check10(BlockPos blockPos) {
      Long longValue4 = this.hashMap.get(blockPos);
      if (longValue4 == null) {
         return false;
      } else if (System.currentTimeMillis() > longValue4) {
         this.hashMap.remove(blockPos);
         return false;
      } else {
         return true;
      }
   }

   private int compute3(ItemStack itemStack) {
      if (itemStack != null && !itemStack.isEmpty()) {
         ItemEnchantmentsComponent itemEnchantmentsComponent = (ItemEnchantmentsComponent)itemStack.get(DataComponentTypes.ENCHANTMENTS);
         if (itemEnchantmentsComponent != null && !itemEnchantmentsComponent.isEmpty()) {
            for (Entry entry2 : itemEnchantmentsComponent.getEnchantmentEntries()) {
               if (((RegistryEntry)entry2.getKey()).matchesKey(Enchantments.FORTUNE)) {
                  return entry2.getIntValue();
               }
            }

            return 0;
         } else {
            return 0;
         }
      } else {
         return 0;
      }
   }

   private boolean check11() {
      if (this.compute3(CLIENT.player.getMainHandStack()) > 0) {
         return false;
      } else {
         int intValue28 = -1;
         int intValue29 = 0;

         for (int intValue30 = 0; intValue30 < 9; intValue30++) {
            int intValue31 = this.compute3(CLIENT.player.getInventory().getStack(intValue30));
            if (intValue31 > intValue29) {
               intValue29 = intValue31;
               intValue28 = intValue30;
            }
         }

         if (intValue28 != -1) {
            CLIENT.player.getInventory().setSelectedSlot(intValue28);
            return false;
         } else {
            for (int intValue32 = 9; intValue32 < 36; intValue32++) {
               int intValue33 = this.compute3(CLIENT.player.getInventory().getStack(intValue32));
               if (intValue33 > intValue29) {
                  intValue29 = intValue33;
                  intValue28 = intValue32;
               }
            }

            if (intValue28 != -1) {
               CLIENT.interactionManager
                  .clickSlot(
                     CLIENT.player.playerScreenHandler.syncId,
                     intValue28,
                     CLIENT.player.getInventory().getSelectedSlot(),
                     SlotActionType.SWAP,
                     CLIENT.player
                  );
               return true;
            } else {
               return false;
            }
         }
      }
   }

   private boolean check12() {
      if (CLIENT.player.getMainHandStack().isOf(Items.COCOA_BEANS)) {
         return false;
      } else {
         for (int intValue34 = 0; intValue34 < 9; intValue34++) {
            if (CLIENT.player.getInventory().getStack(intValue34).isOf(Items.COCOA_BEANS)) {
               CLIENT.player.getInventory().setSelectedSlot(intValue34);
               return false;
            }
         }

         for (int intValue35 = 9; intValue35 < 36; intValue35++) {
            if (CLIENT.player.getInventory().getStack(intValue35).isOf(Items.COCOA_BEANS)) {
               CLIENT.interactionManager
                  .clickSlot(
                     CLIENT.player.playerScreenHandler.syncId,
                     intValue35,
                     CLIENT.player.getInventory().getSelectedSlot(),
                     SlotActionType.SWAP,
                     CLIENT.player
                  );
               return true;
            }
         }

         return false;
      }
   }

   private boolean check13() {
      for (int intValue36 = 0; intValue36 < 36; intValue36++) {
         if (CLIENT.player.getInventory().getStack(intValue36).isOf(Items.COCOA_BEANS)) {
            return true;
         }
      }

      return false;
   }

   private int compute4() {
      int intValue37 = 0;

      for (int intValue38 = 0; intValue38 < 36; intValue38++) {
         if (CLIENT.player.getInventory().getStack(intValue38).isOf(Items.COCOA_BEANS)) {
            intValue37++;
         }
      }

      return intValue37;
   }

   private int compute5() {
      int intValue39 = 0;

      for (int intValue40 = 0; intValue40 < 36; intValue40++) {
         if (CLIENT.player.getInventory().getStack(intValue40).isEmpty()) {
            intValue39++;
         }
      }

      return intValue39;
   }

   private BlockHitResult resolve8(CocoaFarm.CocoaFarmState3 cocoaFarmState310) {
      BlockHitResult blockHitResult9 = this.resolve10(cocoaFarmState310.blockPos);
      if (blockHitResult9 != null) {
         return blockHitResult9;
      } else {
         Vec3d vec3d6 = CLIENT.player.getEyePos();
         Vec3d vec3d7 = Vec3d.ofCenter(cocoaFarmState310.blockPos);
         return vec3d6.distanceTo(vec3d7) <= 4.6 && this.check14(vec3d6, vec3d7, cocoaFarmState310.blockPos)
            ? new BlockHitResult(vec3d7, cocoaFarmState310.direction, cocoaFarmState310.blockPos, false)
            : null;
      }
   }

   private BlockHitResult resolve9(BlockPos blockPos, Direction direction) {
      BlockHitResult blockHitResult10 = this.resolve11(blockPos, direction);
      if (blockHitResult10 != null) {
         return blockHitResult10;
      } else {
         Vec3d vec3d8 = CLIENT.player.getEyePos();
         Vec3d vec3d9 = Vec3d.of(direction.getVector());
         if (vec3d8.subtract(this.resolve13(blockPos, direction)).dotProduct(vec3d9) <= 0.05) {
            return null;
         } else {
            double[] doubleValues = new double[]{0.5, 0.3, 0.7};

            for (double doubleValue16 : doubleValues) {
               for (double doubleValue17 : doubleValues) {
                  Vec3d vec3d10 = this.resolve12(blockPos, direction, doubleValue16, doubleValue17);
                  if (vec3d8.distanceTo(vec3d10) <= 4.6 && this.check14(vec3d8, vec3d10, blockPos)) {
                     return new BlockHitResult(vec3d10, direction, blockPos, false);
                  }
               }
            }

            return null;
         }
      }
   }

   private boolean check14(Vec3d vec3d, Vec3d vec3d2, BlockPos blockPos) {
      Vec3d vec3d11 = vec3d2.subtract(vec3d);
      double doubleValue18 = vec3d11.length();
      if (doubleValue18 < 1.0E-6) {
         return true;
      } else {
         vec3d11 = vec3d11.multiply(1.0 / doubleValue18);

         for (double doubleValue19 = 0.25; doubleValue19 < doubleValue18 - 0.05; doubleValue19 += 0.25) {
            Vec3d vec3d12 = vec3d.add(vec3d11.multiply(doubleValue19));
            BlockPos blockPos17 = BlockPos.ofFloored(vec3d12.x, vec3d12.y, vec3d12.z);
            if (!blockPos17.equals(blockPos)) {
               BlockState blockState10 = CLIENT.world.getBlockState(blockPos17);
               if (!blockState10.isOf(Blocks.COCOA) && !blockState10.isAir() && !blockState10.getCollisionShape(CLIENT.world, blockPos17).isEmpty()) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   private BlockHitResult resolve10(BlockPos blockPos) {
      Vec3d vec3d13 = CLIENT.player.getEyePos();
      double[] doubleValues2 = new double[]{0.5, 0.2, 0.8};

      for (double doubleValue20 : doubleValues2) {
         for (double doubleValue21 : doubleValues2) {
            for (double doubleValue22 : doubleValues2) {
               Vec3d vec3d14 = new Vec3d(blockPos.getX() + doubleValue20, blockPos.getY() + doubleValue21, blockPos.getZ() + doubleValue22);
               BlockHitResult blockHitResult11 = CLIENT.world.raycast(new RaycastContext(vec3d13, vec3d14, ShapeType.OUTLINE, FluidHandling.NONE, CLIENT.player));
               if (blockHitResult11.getType() == net.minecraft.util.hit.HitResult.Type.BLOCK && blockHitResult11.getBlockPos().equals(blockPos)) {
                  return blockHitResult11;
               }
            }
         }
      }

      return null;
   }

   private BlockHitResult resolve11(BlockPos blockPos, Direction direction) {
      Vec3d vec3d15 = CLIENT.player.getEyePos();
      double[] doubleValues3 = new double[]{0.5, 0.3, 0.7};

      for (double doubleValue23 : doubleValues3) {
         for (double doubleValue24 : doubleValues3) {
            Vec3d vec3d16 = this.resolve12(blockPos, direction, doubleValue23, doubleValue24);
            BlockHitResult blockHitResult12 = CLIENT.world.raycast(new RaycastContext(vec3d15, vec3d16, ShapeType.OUTLINE, FluidHandling.NONE, CLIENT.player));
            if (blockHitResult12.getType() == net.minecraft.util.hit.HitResult.Type.BLOCK && blockHitResult12.getBlockPos().equals(blockPos) && blockHitResult12.getSide() == direction) {
               return blockHitResult12;
            }
         }
      }

      return null;
   }

   private Vec3d resolve12(BlockPos blockPos, Direction direction, double d, double e) {
      double doubleValue25 = blockPos.getX();
      double doubleValue26 = blockPos.getY();
      double doubleValue27 = blockPos.getZ();

      return switch (direction) {
         case NORTH -> new Vec3d(doubleValue25 + d, doubleValue26 + e, doubleValue27);
         case SOUTH -> new Vec3d(doubleValue25 + d, doubleValue26 + e, doubleValue27 + 1.0);
         case WEST -> new Vec3d(doubleValue25, doubleValue26 + d, doubleValue27 + e);
         case EAST -> new Vec3d(doubleValue25 + 1.0, doubleValue26 + d, doubleValue27 + e);
         default -> Vec3d.ofCenter(blockPos);
      };
   }

   private Vec3d resolve13(BlockPos blockPos, Direction direction) {
      return new Vec3d(
         blockPos.getX() + 0.5 + direction.getOffsetX() * 0.5,
         blockPos.getY() + 0.5 + direction.getOffsetY() * 0.5,
         blockPos.getZ() + 0.5 + direction.getOffsetZ() * 0.5
      );
   }

   private void invoke17(Rotation rotation4) {
      float floatValue = new Rotation(CLIENT.player).measure(rotation4);
      float floatValue2 = Math.max(5.0F, Math.min(60.0F, floatValue * 0.5F));
      RotationController.invoke3(rotation4, floatValue2, floatValue2, floatValue2, floatValue2, 2, 20, false);
   }

   private BlockHitResult resolve14() {
      double doubleValue28 = Math.toRadians(CLIENT.player.getYaw());
      double doubleValue29 = Math.toRadians(CLIENT.player.getPitch());
      double doubleValue30 = Math.cos(doubleValue29);
      Vec3d vec3d17 = new Vec3d(-Math.sin(doubleValue28) * doubleValue30, -Math.sin(doubleValue29), Math.cos(doubleValue28) * doubleValue30);
      Vec3d vec3d18 = CLIENT.player.getEyePos();
      Vec3d vec3d19 = vec3d18.add(vec3d17.multiply(5.0));
      BlockHitResult blockHitResult13 = CLIENT.world.raycast(new RaycastContext(vec3d18, vec3d19, ShapeType.OUTLINE, FluidHandling.NONE, CLIENT.player));
      return blockHitResult13.getType() == net.minecraft.util.hit.HitResult.Type.BLOCK ? blockHitResult13 : null;
   }

   private Rotation resolve15(Vec3d vec3d) {
      Vec3d vec3d20 = CLIENT.player.getEyePos();
      double doubleValue31 = vec3d.x - vec3d20.x;
      double doubleValue32 = vec3d.y - vec3d20.y;
      double doubleValue33 = vec3d.z - vec3d20.z;
      double doubleValue34 = Math.sqrt(doubleValue31 * doubleValue31 + doubleValue33 * doubleValue33);
      float floatValue3 = (float)Math.toDegrees(Math.atan2(-doubleValue31, doubleValue33));
      float floatValue4 = (float)(-Math.toDegrees(Math.atan2(doubleValue32, doubleValue34)));
      return new Rotation(floatValue3, floatValue4);
   }

   private String resolve16(BlockPos blockPos) {
      return blockPos.getX() + " " + blockPos.getY() + " " + blockPos.getZ();
   }

   private void invoke18(String string) {
   }

   @EventHandler
   public void onRender3D(Render3DEvent render3DEvent) {
      if (CLIENT.world != null && CLIENT.player != null && blockPos != null && blockPos2 != null) {
         if (WorldRenderBuffer.check(CLIENT)) {
            Vec3d vec3d21 = CLIENT.gameRenderer.getCamera().getPos();
            Matrix4f matrix4f2 = render3DEvent.getMatrixStack().peek().getPositionMatrix();
            float floatValue5 = (float)(Math.min(blockPos.getX(), blockPos2.getX()) - vec3d21.x);
            float floatValue6 = (float)(Math.min(blockPos.getY(), blockPos2.getY()) - vec3d21.y);
            float floatValue7 = (float)(Math.min(blockPos.getZ(), blockPos2.getZ()) - vec3d21.z);
            float floatValue8 = (float)(Math.max(blockPos.getX(), blockPos2.getX()) + 1 - vec3d21.x);
            float floatValue9 = (float)(Math.max(blockPos.getY(), blockPos2.getY()) + 1 - vec3d21.y);
            float floatValue10 = (float)(Math.max(blockPos.getZ(), blockPos2.getZ()) + 1 - vec3d21.z);
            float floatValue11 = (float)(System.nanoTime() / 1.0E9);
            Immediate immediate = WorldRenderBuffer.getIMMEDIATE();

            try {
               VertexConsumer vertexConsumer2 = immediate.getBuffer(RENDER_LAYER);
               VertexConsumer vertexConsumer3 = immediate.getBuffer(RENDER_LAYER_2);
               this.invoke24(vertexConsumer3, matrix4f2, floatValue5, floatValue6, floatValue7, floatValue8, floatValue9, floatValue10, floatValue11);
            } finally {
               WorldRenderBuffer.invoke();
            }
         }
      }
   }

   private void invoke19(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float g, float h, float i, float j, float k) {
      float floatValue12 = j - g;

      for (int intValue41 = 0; intValue41 < 20; intValue41++) {
         float floatValue13 = intValue41 / 20.0F;
         float floatValue14 = (intValue41 + 1) / 20.0F;
         float floatValue15 = g + floatValue12 * floatValue13;
         float floatValue16 = g + floatValue12 * floatValue14;
         int intValue42 = ColorUtils.compute2(ColorUtils.compute14(-65409, -8781569, floatValue13), (int)(140.0F * (1.0F - 0.55F * floatValue13)));
         int intValue43 = ColorUtils.compute2(ColorUtils.compute14(-65409, -8781569, floatValue14), (int)(140.0F * (1.0F - 0.55F * floatValue14)));
         this.invoke20(vertexConsumer, matrix4f, f, h, i, h, floatValue15, floatValue16, intValue42, intValue43);
         this.invoke20(vertexConsumer, matrix4f, i, k, f, k, floatValue15, floatValue16, intValue42, intValue43);
         this.invoke20(vertexConsumer, matrix4f, f, k, f, h, floatValue15, floatValue16, intValue42, intValue43);
         this.invoke20(vertexConsumer, matrix4f, i, h, i, k, floatValue15, floatValue16, intValue42, intValue43);
      }
   }

   private void invoke20(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float g, float h, float i, float j, float k, int l, int m) {
      int intValue44 = ColorUtils.compute5(l);
      int intValue45 = ColorUtils.compute6(l);
      int intValue46 = ColorUtils.compute7(l);
      int intValue47 = ColorUtils.compute4(l);
      int intValue48 = ColorUtils.compute5(m);
      int intValue49 = ColorUtils.compute6(m);
      int intValue50 = ColorUtils.compute7(m);
      int intValue51 = ColorUtils.compute4(m);
      vertexConsumer.vertex(matrix4f, f, j, g).color(intValue44, intValue45, intValue46, intValue47);
      vertexConsumer.vertex(matrix4f, h, j, i).color(intValue44, intValue45, intValue46, intValue47);
      vertexConsumer.vertex(matrix4f, h, k, i).color(intValue48, intValue49, intValue50, intValue51);
      vertexConsumer.vertex(matrix4f, f, k, g).color(intValue48, intValue49, intValue50, intValue51);
   }

   private void invoke21(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float g, float h, float i, float j, float k, float l) {
      float floatValue17 = j - g;
      if (!(floatValue17 <= 0.01F)) {
         float floatValue18 = (l * 0.35F % 1.0F + 1.0F) % 1.0F;
         float floatValue19 = floatValue18 < 0.5F ? floatValue18 * 2.0F : (1.0F - floatValue18) * 2.0F;
         float floatValue20 = g + floatValue17 * floatValue19;
         int intValue52 = ColorUtils.compute2(-65409, 38);
         this.invoke22(vertexConsumer, matrix4f, f, h, i, k, floatValue20, intValue52);
         int intValue53 = ColorUtils.compute2(-16719617, 90);
         float floatValue21 = i - f;
         float floatValue22 = k - h;
         int intValue54 = Math.min(10, Math.max(1, Math.round(floatValue21 / 3.0F)));
         int intValue55 = Math.min(10, Math.max(1, Math.round(floatValue22 / 3.0F)));
         float floatValue23 = 0.015F;

         for (int intValue56 = 0; intValue56 <= intValue54; intValue56++) {
            float floatValue24 = f + floatValue21 * ((float)intValue56 / intValue54);
            this.invoke23(vertexConsumer, matrix4f, floatValue24 - floatValue23, h, floatValue24 + floatValue23, k, floatValue20, intValue53);
         }

         for (int intValue57 = 0; intValue57 <= intValue55; intValue57++) {
            float floatValue25 = h + floatValue22 * ((float)intValue57 / intValue55);
            this.invoke23(vertexConsumer, matrix4f, f, floatValue25 - floatValue23, i, floatValue25 + floatValue23, floatValue20, intValue53);
         }
      }
   }

   private void invoke22(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float g, float h, float i, float j, int k) {
      int intValue58 = ColorUtils.compute5(k);
      int intValue59 = ColorUtils.compute6(k);
      int intValue60 = ColorUtils.compute7(k);
      int intValue61 = ColorUtils.compute4(k);
      vertexConsumer.vertex(matrix4f, f, j, g).color(intValue58, intValue59, intValue60, intValue61);
      vertexConsumer.vertex(matrix4f, h, j, g).color(intValue58, intValue59, intValue60, intValue61);
      vertexConsumer.vertex(matrix4f, h, j, i).color(intValue58, intValue59, intValue60, intValue61);
      vertexConsumer.vertex(matrix4f, f, j, i).color(intValue58, intValue59, intValue60, intValue61);
   }

   private void invoke23(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float g, float h, float i, float j, int k) {
      int intValue62 = ColorUtils.compute5(k);
      int intValue63 = ColorUtils.compute6(k);
      int intValue64 = ColorUtils.compute7(k);
      int intValue65 = ColorUtils.compute4(k);
      vertexConsumer.vertex(matrix4f, f, j, g).color(intValue62, intValue63, intValue64, intValue65);
      vertexConsumer.vertex(matrix4f, h, j, g).color(intValue62, intValue63, intValue64, intValue65);
      vertexConsumer.vertex(matrix4f, h, j, i).color(intValue62, intValue63, intValue64, intValue65);
      vertexConsumer.vertex(matrix4f, f, j, i).color(intValue62, intValue63, intValue64, intValue65);
   }

   private void invoke24(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float g, float h, float i, float j, float k, float l) {
      float floatValue26 = l * 1.4F;
      int intValue66 = ColorUtils.compute2(-65409, 190);
      float floatValue27 = 0.02F;
      float[][] floatValuesValues = new float[][]{
         {f, g, h, i, g, h},
         {i, g, h, i, g, k},
         {i, g, k, f, g, k},
         {f, g, k, f, g, h},
         {f, j, h, i, j, h},
         {i, j, h, i, j, k},
         {i, j, k, f, j, k},
         {f, j, k, f, j, h},
         {f, g, h, f, j, h},
         {i, g, h, i, j, h},
         {i, g, k, i, j, k},
         {f, g, k, f, j, k}
      };

      for (float[] floatValues : floatValuesValues) {
         this.invoke25(vertexConsumer, matrix4f, floatValues[0], floatValues[1], floatValues[2], floatValues[3], floatValues[4], floatValues[5], floatValue27, intValue66, floatValue26);
      }
   }

   private void invoke25(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float g, float h, float i, float j, float k, float l, int m, float n) {
      float floatValue28 = i - f;
      float floatValue29 = j - g;
      float floatValue30 = k - h;
      float floatValue31 = (float)Math.sqrt(floatValue28 * floatValue28 + floatValue29 * floatValue29 + floatValue30 * floatValue30);
      if (!(floatValue31 < 1.0E-4F)) {
         float floatValue32 = floatValue28 / floatValue31;
         float floatValue33 = floatValue29 / floatValue31;
         float floatValue34 = floatValue30 / floatValue31;
         float floatValue35 = 0.45F;
         float floatValue36 = 0.35F;
         float floatValue37 = Math.max(floatValue35 + floatValue36, floatValue31 / 40.0F);
         floatValue35 = floatValue37 * 0.56F;
         float floatValue38 = -((n % floatValue37 + floatValue37) % floatValue37);

         for (float floatValue39 = floatValue38; floatValue39 < floatValue31; floatValue39 += floatValue37) {
            float floatValue40 = Math.max(0.0F, floatValue39);
            float floatValue41 = Math.min(floatValue31, floatValue39 + floatValue35);
            if (!(floatValue41 <= floatValue40)) {
               float floatValue42 = f + floatValue32 * floatValue40;
               float floatValue43 = g + floatValue33 * floatValue40;
               float floatValue44 = h + floatValue34 * floatValue40;
               float floatValue45 = f + floatValue32 * floatValue41;
               float floatValue46 = g + floatValue33 * floatValue41;
               float floatValue47 = h + floatValue34 * floatValue41;
               WorldBoxRenderer.invoke5(
                  vertexConsumer,
                  matrix4f,
                  Math.min(floatValue42, floatValue45) - l,
                  Math.min(floatValue43, floatValue46) - l,
                  Math.min(floatValue44, floatValue47) - l,
                  Math.max(floatValue42, floatValue45) + l,
                  Math.max(floatValue43, floatValue46) + l,
                  Math.max(floatValue44, floatValue47) + l,
                  m
               );
            }
         }
      }
   }

   @Generated
   public static BlockPos getBlockPos() {
      return blockPos;
   }

   @Generated
   public static void setBlockPos(BlockPos blockPos) {
      CocoaFarm.blockPos = blockPos;
   }

   @Generated
   public static BlockPos getBlockPos2() {
      return blockPos2;
   }

   @Generated
   public static void setBlockPos2(BlockPos blockPos) {
      blockPos2 = blockPos;
   }

   static enum CocoaFarmState {
      HARVEST,
      PLANT;
   }

   static enum CocoaFarmState2 {
      FARM,
      NAVIGATING,
      INTERACTING,
      WAITING_FOR_CONTAINER,
      DEPOSITING;
   }

   static final class CocoaFarmState3 {
      final CocoaFarm.CocoaFarmState cocoaFarmState;
      final BlockPos blockPos;
      final Direction direction;

      CocoaFarmState3(CocoaFarm.CocoaFarmState cocoaFarmState, BlockPos blockPos, Direction direction) {
         this.cocoaFarmState = cocoaFarmState;
         this.blockPos = blockPos;
         this.direction = direction;
      }
   }
}
