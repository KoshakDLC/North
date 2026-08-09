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
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Generated;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import org.joml.Matrix4f;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "ChorusFarm",
   category = Category.Misc,
   description = "Авто-ферма плодов хоруса с отстрелом луком"
)
public class ChorusFarm extends Module {
   private static BlockPos blockPos;
   private static BlockPos blockPos2;
   public final BooleanSetting sbivatPlody = new BooleanSetting("Сбивать плоды", true);
   public final NumberSetting vysotaDlyaSbora = new NumberSetting("Высота для сбора", 6.0F, 2.0F, 24.0F, 1.0F, false);
   public final BooleanSetting avtoPosadka = new BooleanSetting("Авто-посадка", true);
   public final BooleanSetting skladVSunduk = new BooleanSetting("Склад в сундук", true);
   public final BooleanSetting logi = new BooleanSetting("Логи", false);
   private static final double DOUBLE_VALUE = 4.6;
   private static final double DOUBLE_VALUE_2 = 3.6;
   private static final double DOUBLE_VALUE_3 = 28.0;
   private static final float FLOAT_VALUE = 140.0F;
   private static final float FLOAT_VALUE_2 = 34.0F;
   private static final float FLOAT_VALUE_3 = 1.35F;
   private static final float FLOAT_VALUE_4 = 4.0F;
   private static final float FLOAT_VALUE_5 = 2.6F;
   private static final int INT_VALUE = 20;
   private static final int INT_VALUE_2 = 8;
   private static final int INT_VALUE_3 = 3;
   private static final long TIMESTAMP = 90L;
   private static final int INT_VALUE_4 = 40;
   private static final int INT_VALUE_5 = 32;
   private static final int INT_VALUE_6 = 4;
   private static final long TIMESTAMP_2 = 150L;
   private static final long TIMESTAMP_3 = 1800L;
   private static final long TIMESTAMP_4 = 5000L;
   private static final long TIMESTAMP_5 = 2000L;
   private static final long TIMESTAMP_6 = 900L;
   private static final int INT_VALUE_7 = 4;
   private static final double DOUBLE_VALUE_4 = 1.62;
   private static final long TIMESTAMP_7 = 60L;
   private static final int INT_VALUE_8 = 6;
   private static final int INT_VALUE_9 = 4;
   private static final long TIMESTAMP_8 = 8000L;
   private static final long TIMESTAMP_9 = 6000L;
   private static final long TIMESTAMP_10 = 30000L;
   private static final double DOUBLE_VALUE_5 = 4.2;
   private static final int INT_VALUE_10 = 400;
   private static final long TIMESTAMP_11 = 4000L;
   private static final int INT_VALUE_11 = 2;
   private static final long TIMESTAMP_12 = 30000L;
   private static final long TIMESTAMP_13 = 3000L;
   private static final long TIMESTAMP_14 = 18000L;
   private static final int INT_VALUE_12 = 128;
   private final DualTimer dualTimer = new DualTimer();
   private final DualTimer dualTimer2 = new DualTimer();
   private final DualTimer dualTimer3 = new DualTimer();
   private final DualTimer dualTimer4 = new DualTimer();
   private final DualTimer dualTimer5 = new DualTimer();
   private final DualTimer dualTimer6 = new DualTimer();
   private final DualTimer dualTimer7 = new DualTimer();
   private final DualTimer dualTimer8 = new DualTimer();
   private final List<ChorusFarm.ChorusFarmState3> items = new ArrayList<>();
   private final HashMap<BlockPos, Long> hashMap = new HashMap<>();
   private final HashMap<BlockPos, Long> hashMap2 = new HashMap<>();
   private final HashMap<Integer, Long> hashMap3 = new HashMap<>();
   private final HashMap<BlockPos, long[]> hashMap4 = new HashMap<>();
   private final Set<BlockPos> values = new HashSet<>();
   private Set<BlockPos> values2;
   private BlockPos blockPos3;
   private int intValue = 20;
   private BlockPos blockPos4;
   private ChorusFarm.ChorusFarmState2 chorusFarmState2 = ChorusFarm.ChorusFarmState2.FARM;
   private ChorusFarm.ChorusFarmState3 chorusFarmState3;
   private BlockPos blockPos5;
   private BlockPos blockPos6;
   private BlockPos blockPos7;
   private BlockPos blockPos8;
   private int intValue2 = -1;
   private int intValue3;
   private int intValue4 = -1;
   private int intValue5;
   private long timestamp;
   private long timestamp2;
   private double doubleValue;
   private int intValue6;
   private int intValue7;
   private int intValue8;
   private BlockPos blockPos9;
   private boolean flag;
   private boolean flag2;
   private int intValue9;
   private int intValue10 = -1;
   private boolean flag3;
   private boolean flag4;
   private boolean flag5;
   private static final int INT_VALUE_13 = 4096;
   private static final int INT_VALUE_14 = 16;
   private static final RenderPipeline RENDER_PIPELINE = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.POSITION_COLOR_SNIPPET})
         .withLocation(Identifier.of("wild", "chorus_zone_fill"))
         .withVertexFormat(VertexFormats.POSITION_COLOR, DrawMode.QUADS)
         .withCull(false)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .withBlend(BlendFunction.TRANSLUCENT)
         .build()
   );
   private static final RenderPipeline RENDER_PIPELINE_2 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.POSITION_COLOR_SNIPPET})
         .withLocation(Identifier.of("wild", "chorus_zone_glow"))
         .withVertexFormat(VertexFormats.POSITION_COLOR, DrawMode.QUADS)
         .withCull(false)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .withBlend(BlendFunction.LIGHTNING)
         .build()
   );
   private static final RenderLayer RENDER_LAYER = RenderLayer.of(
      "chorus_zone_fill", 4096, false, true, RENDER_PIPELINE, MultiPhaseParameters.builder().build(false)
   );
   private static final RenderLayer RENDER_LAYER_2 = RenderLayer.of(
      "chorus_zone_glow", 4096, false, true, RENDER_PIPELINE_2, MultiPhaseParameters.builder().build(false)
   );
   private static final int INT_VALUE_15 = -2995201;
   private static final int INT_VALUE_16 = -9822240;
   private static final int INT_VALUE_17 = 18;

   public static void invoke() {
      blockPos = null;
      blockPos2 = null;
   }

   public ChorusFarm() {
      this.addSettings(new Setting[]{this.sbivatPlody, this.vysotaDlyaSbora, this.avtoPosadka, this.skladVSunduk, this.logi});

      try {
         BaritoneAPI.getSettings().chunkCaching.value = false;
      } catch (Throwable exception) {
      }
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.chorusFarmState2 = ChorusFarm.ChorusFarmState2.FARM;
      this.items.clear();
      this.hashMap.clear();
      this.chorusFarmState3 = null;
      this.blockPos5 = null;
      this.blockPos6 = null;
      this.blockPos7 = null;
      this.blockPos8 = null;
      this.intValue2 = -1;
      this.intValue3 = 0;
      this.intValue4 = -1;
      this.intValue5 = 0;
      this.timestamp = 0L;
      this.timestamp2 = 0L;
      this.doubleValue = Double.MAX_VALUE;
      this.intValue6 = 0;
      this.intValue7 = 0;
      this.intValue8 = 0;
      this.blockPos9 = null;
      this.flag = false;
      this.flag2 = false;
      this.intValue9 = 0;
      this.intValue10 = -1;
      this.hashMap3.clear();
      this.hashMap4.clear();
      this.values.clear();
      this.hashMap2.clear();
      this.values2 = null;
      this.blockPos3 = null;
      this.intValue = 20;
      this.blockPos4 = null;
      this.dualTimer2.invoke();
      this.dualTimer.invoke();
      this.dualTimer3.invoke();
      this.dualTimer4.invoke();
      this.dualTimer5.invoke();
      this.dualTimer6.invoke();
      this.dualTimer8.invoke();
      this.flag3 = (Boolean)BaritoneAPI.getSettings().allowBreak.value;
      this.flag4 = (Boolean)BaritoneAPI.getSettings().allowPlace.value;
      this.flag5 = (Boolean)BaritoneAPI.getSettings().allowSprint.value;
      BaritoneAPI.getSettings().allowBreak.value = false;
      BaritoneAPI.getSettings().allowPlace.value = false;
      BaritoneAPI.getSettings().chunkCaching.value = false;
      if (blockPos != null && blockPos2 != null) {
         this.invoke22("Запуск, площадь " + this.resolve20(blockPos) + " — " + this.resolve20(blockPos2));
      } else {
         ChatUtil.sendClientMessage("§d[ChorusFarm] §fСначала задайте зону: §e.chorus pos1 §fи §e.chorus pos2");
      }
   }

   @Override
   public void onDisable() {
      this.invoke14();
      this.invoke11();
      BaritoneAPI.getSettings().allowBreak.value = this.flag3;
      BaritoneAPI.getSettings().allowPlace.value = this.flag4;
      BaritoneAPI.getSettings().allowSprint.value = this.flag5;
      RotationController.rotationControllerState2 = RotationController.RotationControllerState2.IDLE;
      RotationController.intValue = 0;
      RotationController.rotation = null;
      FreeLookController.active = false;
      this.blockPos6 = null;
      this.values2 = null;
      this.blockPos3 = null;
      this.chorusFarmState3 = null;
      super.onDisable();
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player != null && CLIENT.world != null && CLIENT.interactionManager != null) {
         if (blockPos == null || blockPos2 == null) {
            this.invoke11();
         } else if (PlayerHelper.check()) {
            this.invoke11();
            this.invoke14();
         } else if (this.chorusFarmState2 != ChorusFarm.ChorusFarmState2.FARM && System.currentTimeMillis() > this.timestamp) {
            this.invoke22("Тайм-аут депозит-сессии, блокирую сундук");
            this.invoke15(true);
         } else {
            switch (this.chorusFarmState2) {
               case FARM:
                  this.invoke2();
                  break;
               case NAVIGATING:
                  this.invoke16();
                  break;
               case INTERACTING:
                  this.invoke17();
                  break;
               case WAITING_FOR_CONTAINER:
                  this.invoke18();
                  break;
               case DEPOSITING:
                  this.invoke19();
            }
         }
      }
   }

   private void invoke2() {
      this.invoke20();
      if (this.skladVSunduk.isEnabled() && System.currentTimeMillis() >= this.timestamp2 && this.check15()) {
         BlockPos blockPos2 = this.resolve12();
         if (blockPos2 != null) {
            this.invoke11();
            this.blockPos8 = blockPos2;
            this.chorusFarmState2 = ChorusFarm.ChorusFarmState2.NAVIGATING;
            this.dualTimer7.invoke();
            this.dualTimer3.invoke();
            this.doubleValue = Double.MAX_VALUE;
            this.invoke14();
            this.intValue3 = 0;
            this.intValue5 = 0;
            this.intValue4 = -1;
            this.timestamp = System.currentTimeMillis() + 30000L;
            this.invoke22("Инвентарь полон, иду к сундуку " + this.resolve20(blockPos2));
            return;
         }
      }

      if (this.chorusFarmState3 == null || !this.check7(this.chorusFarmState3)) {
         this.chorusFarmState3 = null;
         if (this.dualTimer2.check5(150L)) {
            this.invoke12();
            this.dualTimer2.invoke();
         }

         ChorusFarm.ChorusFarmState3 chorusFarmState3 = this.resolve6();
         if (chorusFarmState3 != null && this.check4(chorusFarmState3.blockPos)) {
            chorusFarmState3 = null;
         }

         if (chorusFarmState3 != null && !chorusFarmState3.blockPos.equals(this.blockPos5)) {
            this.blockPos5 = chorusFarmState3.blockPos;
            this.intValue6 = 0;
            this.intValue7 = 0;
            this.intValue8 = 0;
            this.blockPos9 = null;
            this.dualTimer3.invoke();
            this.dualTimer6.invoke();
            this.doubleValue = Double.MAX_VALUE;
         }

         this.chorusFarmState3 = chorusFarmState3;
      }

      if (this.chorusFarmState3 == null || this.chorusFarmState3.chorusFarmState != ChorusFarm.ChorusFarmState.SHOOT) {
         this.invoke11();
         this.blockPos9 = null;
         if (this.check9()) {
            return;
         }
      }

      if (this.chorusFarmState3 == null) {
         if (!this.flag2) {
            this.invoke22("Целей нет, жду роста");
            this.flag2 = true;
         }

         this.invoke14();
      } else {
         this.flag2 = false;
         switch (this.chorusFarmState3.chorusFarmState) {
            case SHOOT:
               this.invoke3(this.chorusFarmState3);
               break;
            case PLANT:
               this.invoke4(this.chorusFarmState3);
               break;
            case CLEAR:
               this.invoke5(this.chorusFarmState3);
         }
      }
   }

   private void invoke3(ChorusFarm.ChorusFarmState3 chorusFarmState32) {
      Vec3d vec3d3 = Vec3d.ofCenter(chorusFarmState32.blockPos);
      if (this.blockPos9 != null) {
         double doubleValue = CLIENT.player.getX() - (this.blockPos9.getX() + 0.5);
         double doubleValue2 = CLIENT.player.getZ() - (this.blockPos9.getZ() + 0.5);
         if (!(doubleValue * doubleValue + doubleValue2 * doubleValue2 <= 1.4) && !this.dualTimer3.check5(5000L)) {
            this.invoke11();
            this.invoke13(this.blockPos9, 0);
            return;
         }

         this.blockPos9 = null;
         this.blockPos4 = null;
         this.invoke14();
         this.dualTimer3.invoke();
         this.dualTimer6.invoke();
         this.doubleValue = Double.MAX_VALUE;
      }

      double doubleValue3 = CLIENT.player.getEyePos().distanceTo(vec3d3);
      if (doubleValue3 > 28.0) {
         this.invoke11();
         BlockPos blockPos3 = new BlockPos(chorusFarmState32.blockPos.getX(), this.compute2(), chorusFarmState32.blockPos.getZ());
         if (!this.check3(doubleValue3, blockPos3, 3)) {
            this.invoke6("не подойти к плоду " + this.resolve20(chorusFarmState32.blockPos));
         }
      } else {
         this.invoke14();
         if (!this.check20()) {
            this.invoke22("Нет стрел, пропускаю отстрел");
            this.invoke11();
            this.chorusFarmState3 = null;
         } else if (!this.check16()) {
            if (CLIENT.player.getMainHandStack().getItem() instanceof BowItem) {
               if (!chorusFarmState32.blockPos.equals(this.blockPos4)) {
                  this.blockPos4 = chorusFarmState32.blockPos;
                  this.intValue = this.compute(chorusFarmState32.blockPos, vec3d3);
               }

               float floatValue = this.measure(this.intValue);
               Rotation rotation = this.resolve3(vec3d3, floatValue);
               this.invoke21(rotation);
               this.invoke9();
               if (CLIENT.player.getItemUseTime() < this.intValue) {
                  this.dualTimer6.invoke();
               } else if (new Rotation(CLIENT.player).measure(rotation) > 2.6F) {
                  this.dualTimer6.invoke();
               } else {
                  Vec3d vec3d4 = CLIENT.player.getVelocity();
                  if (vec3d4.x * vec3d4.x + vec3d4.z * vec3d4.z > 0.0025) {
                     this.dualTimer6.invoke();
                  } else if (!this.check5(chorusFarmState32.blockPos, floatValue)) {
                     if (this.dualTimer6.check5(900L)) {
                        BlockPos blockPos4 = this.intValue8 < 4 ? this.resolve(chorusFarmState32.blockPos) : null;
                        if (blockPos4 != null) {
                           this.blockPos9 = blockPos4;
                           this.intValue8++;
                           this.dualTimer3.invoke();
                           this.doubleValue = Double.MAX_VALUE;
                           this.invoke11();
                           this.invoke22("Меняю позицию для отстрела " + this.resolve20(chorusFarmState32.blockPos));
                        } else {
                           this.hashMap.put(chorusFarmState32.blockPos, System.currentTimeMillis() + 6000L);
                           this.invoke6("не навестись на плод " + this.resolve20(chorusFarmState32.blockPos));
                        }
                     }
                  } else if (this.dualTimer5.check5(60L)) {
                     this.invoke10();
                     this.dualTimer5.invoke();
                     this.dualTimer6.invoke();
                     this.intValue7++;
                     if (this.intValue7 >= 6) {
                        this.hashMap.put(chorusFarmState32.blockPos, System.currentTimeMillis() + 6000L);
                        this.invoke6("плод " + this.resolve20(chorusFarmState32.blockPos) + " не сбивается за 6 выстрелов");
                     }
                  }
               }
            }
         }
      }
   }

   private BlockPos resolve(BlockPos blockPos) {
      BlockPos blockPos5 = new BlockPos(blockPos.getX(), this.compute2(), blockPos.getZ());
      Vec3d vec3d5 = Vec3d.ofCenter(blockPos);
      BlockPos blockPos6 = CLIENT.player.getBlockPos();
      BlockPos blockPos7 = null;
      double doubleValue4 = -Double.MAX_VALUE;

      for (int intValue = 0; intValue < 16; intValue++) {
         double doubleValue5 = intValue * Math.PI / 8.0;
         double doubleValue6 = Math.cos(doubleValue5);
         double doubleValue7 = Math.sin(doubleValue5);

         for (int intValue2 = 3; intValue2 <= 6; intValue2++) {
            int intValue3 = blockPos5.getX() + (int)Math.round(doubleValue6 * intValue2);
            int intValue4 = blockPos5.getZ() + (int)Math.round(doubleValue7 * intValue2);
            BlockPos blockPos8 = new BlockPos(intValue3, this.compute2(), intValue4);
            if (this.check8(blockPos8)) {
               BlockPos blockPos9 = this.resolve2(blockPos8);
               if (blockPos9 != null) {
                  double doubleValue8 = blockPos9.getX() - blockPos6.getX();
                  double doubleValue9 = blockPos9.getZ() - blockPos6.getZ();
                  if (!(doubleValue8 * doubleValue8 + doubleValue9 * doubleValue9 < 4.0)) {
                     Vec3d vec3d6 = new Vec3d(blockPos9.getX() + 0.5, blockPos9.getY() + 1.62, blockPos9.getZ() + 0.5);
                     if (this.check2(vec3d6, vec3d5, blockPos)) {
                        double doubleValue10 = Math.sqrt(
                           (blockPos9.getX() + 0.5 - (blockPos5.getX() + 0.5)) * (blockPos9.getX() + 0.5 - (blockPos5.getX() + 0.5))
                              + (blockPos9.getZ() + 0.5 - (blockPos5.getZ() + 0.5)) * (blockPos9.getZ() + 0.5 - (blockPos5.getZ() + 0.5))
                        );
                        double doubleValue11 = -Math.abs(doubleValue10 - 4.0);
                        if (doubleValue11 > doubleValue4) {
                           doubleValue4 = doubleValue11;
                           blockPos7 = blockPos9;
                        }
                     }
                  }
               }
            }
         }
      }

      return blockPos7;
   }

   private BlockPos resolve2(BlockPos blockPos) {
      int[] intValues = new int[]{0, -1, 1, -2, 2};

      for (int intValue5 : intValues) {
         BlockPos blockPos10 = new BlockPos(blockPos.getX(), this.compute2() + intValue5, blockPos.getZ());
         if (this.check(blockPos10)) {
            return blockPos10;
         }
      }

      return null;
   }

   private boolean check(BlockPos blockPos) {
      BlockState blockState2 = CLIENT.world.getBlockState(blockPos);
      BlockState blockState3 = CLIENT.world.getBlockState(blockPos.up());
      BlockState blockState4 = CLIENT.world.getBlockState(blockPos.down());
      boolean flag = blockState2.isAir() || blockState2.getCollisionShape(CLIENT.world, blockPos).isEmpty();
      boolean flag2 = blockState3.isAir() || blockState3.getCollisionShape(CLIENT.world, blockPos.up()).isEmpty();
      boolean flag3 = !blockState4.isAir() && !blockState4.getCollisionShape(CLIENT.world, blockPos.down()).isEmpty();
      return flag && flag2 && flag3;
   }

   private boolean check2(Vec3d vec3d, Vec3d vec3d2, BlockPos blockPos) {
      Vec3d vec3d7 = vec3d2.subtract(vec3d);
      double doubleValue12 = vec3d7.length();
      if (doubleValue12 < 1.0E-6) {
         return true;
      } else {
         Vec3d vec3d8 = vec3d7.multiply(1.0 / doubleValue12);

         for (double doubleValue13 = 0.2; doubleValue13 < doubleValue12; doubleValue13 += 0.2) {
            Vec3d vec3d9 = vec3d.add(vec3d8.multiply(doubleValue13));
            BlockPos blockPos11 = BlockPos.ofFloored(vec3d9.x, vec3d9.y, vec3d9.z);
            if (blockPos11.equals(blockPos)) {
               return true;
            }

            BlockState blockState5 = CLIENT.world.getBlockState(blockPos11);
            if (!blockState5.isAir() && !blockState5.getCollisionShape(CLIENT.world, blockPos11).isEmpty()) {
               return false;
            }
         }

         return true;
      }
   }

   private void invoke4(ChorusFarm.ChorusFarmState3 chorusFarmState33) {
      this.invoke11();
      if (!this.check19()) {
         this.invoke22("Цветы хоруса закончились, посадка недоступна");
         this.chorusFarmState3 = null;
      } else if (!this.check17()) {
         if (CLIENT.player.getMainHandStack().isOf(Items.CHORUS_FLOWER)) {
            if (this.intValue6 >= 4) {
               this.invoke7(chorusFarmState33.blockPos);
               this.invoke7(chorusFarmState33.blockPos.up());
               this.invoke6("посадка на " + this.resolve20(chorusFarmState33.blockPos) + " не проходит, ресинк фантома");
            } else {
               BlockHitResult blockHitResult = this.resolve13(chorusFarmState33.blockPos, Direction.UP);
               if (blockHitResult != null && !(CLIENT.player.getEyePos().distanceTo(blockHitResult.getPos()) > 4.2)) {
                  this.dualTimer3.invoke();
                  this.invoke14();
                  Rotation rotation2 = this.resolve19(blockHitResult.getPos());
                  this.invoke21(rotation2);
                  if (!(new Rotation(CLIENT.player).measure(rotation2) > 4.0F)) {
                     if (this.dualTimer.check5(90L)) {
                        BlockHitResult blockHitResult2 = this.resolve18();
                        BlockHitResult blockHitResult3 = blockHitResult2 != null && blockHitResult2.getBlockPos().equals(chorusFarmState33.blockPos) && blockHitResult2.getSide() == Direction.UP
                           ? blockHitResult2
                           : blockHitResult;
                        CLIENT.interactionManager.interactBlock(CLIENT.player, Hand.MAIN_HAND, blockHitResult3);
                        CLIENT.player.swingHand(Hand.MAIN_HAND);
                        this.intValue6++;
                        this.dualTimer.invoke();
                        this.blockPos6 = null;
                     }
                  }
               } else {
                  this.invoke8(chorusFarmState33);
               }
            }
         }
      }
   }

   private void invoke5(ChorusFarm.ChorusFarmState3 chorusFarmState34) {
      this.invoke11();
      double doubleValue14 = CLIENT.player.getEyePos().distanceTo(Vec3d.ofCenter(chorusFarmState34.blockPos));
      if (doubleValue14 > 4.6) {
         if (!this.check3(doubleValue14, chorusFarmState34.blockPos, 2)) {
            this.invoke6("не подойти к корню " + this.resolve20(chorusFarmState34.blockPos));
         }
      } else {
         BlockHitResult blockHitResult4 = this.resolve14(chorusFarmState34.blockPos);
         if (blockHitResult4 != null && !(CLIENT.player.getEyePos().distanceTo(blockHitResult4.getPos()) > 4.2)) {
            this.dualTimer3.invoke();
            this.invoke14();
            if (!this.check18()) {
               Rotation rotation3 = this.resolve19(blockHitResult4.getPos());
               this.invoke21(rotation3);
               if (!(new Rotation(CLIENT.player).measure(rotation3) > 4.0F)) {
                  BlockHitResult blockHitResult5 = this.resolve18();
                  BlockHitResult blockHitResult6 = blockHitResult5 != null && blockHitResult5.getBlockPos().equals(chorusFarmState34.blockPos) ? blockHitResult5 : blockHitResult4;
                  if (!chorusFarmState34.blockPos.equals(this.blockPos6)) {
                     if (!this.dualTimer.check5(90L)) {
                        return;
                     }

                     CLIENT.interactionManager.attackBlock(chorusFarmState34.blockPos, blockHitResult6.getSide());
                     this.blockPos6 = chorusFarmState34.blockPos;
                     if (CLIENT.world.getBlockState(chorusFarmState34.blockPos.down()).isOf(Blocks.END_STONE)) {
                        Set values = this.resolve4(List.of(chorusFarmState34.blockPos));
                        values.remove(chorusFarmState34.blockPos);
                        this.values2 = values;
                     } else {
                        this.values2 = null;
                     }

                     this.dualTimer4.invoke();
                     this.dualTimer.invoke();
                  } else {
                     if (this.dualTimer4.check5(2000L)) {
                        this.invoke7(chorusFarmState34.blockPos);
                        this.invoke6("корень " + this.resolve20(chorusFarmState34.blockPos) + " не ломается, ресинк фантома");
                        return;
                     }

                     CLIENT.interactionManager.updateBlockBreakingProgress(chorusFarmState34.blockPos, blockHitResult6.getSide());
                  }

                  CLIENT.player.swingHand(Hand.MAIN_HAND);
               }
            }
         } else {
            this.invoke8(chorusFarmState34);
         }
      }
   }

   private boolean check3(double d, BlockPos blockPos, int i) {
      if (d < this.doubleValue - 0.4) {
         this.doubleValue = d;
         this.dualTimer3.invoke();
      }

      if (this.dualTimer3.check5(5000L)) {
         return false;
      } else {
         this.invoke13(blockPos, i);
         return true;
      }
   }

   private void invoke6(String string) {
      this.invoke22("Пропуск: " + string);
      if (this.chorusFarmState3 != null) {
         this.hashMap.put(this.chorusFarmState3.blockPos, System.currentTimeMillis() + 8000L);
         this.invoke7(this.chorusFarmState3.blockPos);
      }

      this.chorusFarmState3 = null;
      this.blockPos6 = null;
      this.values2 = null;
      this.invoke14();
   }

   private void invoke7(BlockPos blockPos) {
      CLIENT.player.networkHandler.sendPacket(new PlayerActionC2SPacket(Action.ABORT_DESTROY_BLOCK, blockPos, Direction.DOWN));
   }

   private boolean check4(BlockPos blockPos) {
      long longValue = System.currentTimeMillis();
      long[] longValues = this.hashMap4.get(blockPos);
      if (longValues != null && longValue - longValues[1] <= 4000L) {
         longValues[0]++;
         longValues[1] = longValue;
         if (longValues[0] >= 2L) {
            this.hashMap4.remove(blockPos);
            this.hashMap.put(blockPos, longValue + 30000L);
            this.invoke7(blockPos);
            this.invoke22("Фантомный блок " + this.resolve20(blockPos) + ", ресинк и пропуск");
            return true;
         } else {
            return false;
         }
      } else {
         this.hashMap4.put(blockPos.toImmutable(), new long[]{1L, longValue});
         if (this.hashMap4.size() > 128) {
            this.hashMap4.entrySet().removeIf(entry -> longValue - entry.getValue()[1] > 4000L);
         }

         return false;
      }
   }

   private void invoke8(ChorusFarm.ChorusFarmState3 chorusFarmState35) {
      double doubleValue15 = CLIENT.player.getEyePos().distanceTo(Vec3d.ofCenter(chorusFarmState35.blockPos));
      if (doubleValue15 > 4.6) {
         if (!this.check3(doubleValue15, chorusFarmState35.blockPos, 1)) {
            this.invoke6("не подойти к " + this.resolve20(chorusFarmState35.blockPos));
         }
      } else {
         if (this.dualTimer3.check5(1800L)) {
            this.invoke6("нет прямой видимости " + this.resolve20(chorusFarmState35.blockPos));
         }
      }
   }

   private void invoke9() {
      CLIENT.options.useKey.setPressed(true);
      if (!CLIENT.player.isUsingItem() && CLIENT.interactionManager != null) {
         CLIENT.interactionManager.interactItem(CLIENT.player, Hand.MAIN_HAND);
      }

      this.flag = true;
   }

   private void invoke10() {
      CLIENT.options.useKey.setPressed(false);
      this.flag = false;
      if (CLIENT.interactionManager != null) {
         CLIENT.interactionManager.stopUsingItem(CLIENT.player);
      }

      CLIENT.player.swingHand(Hand.MAIN_HAND);
   }

   private void invoke11() {
      if (this.flag) {
         CLIENT.options.useKey.setPressed(false);
         this.flag = false;
      }

      if (CLIENT.player != null
         && CLIENT.interactionManager != null
         && CLIENT.player.isUsingItem()
         && CLIENT.player.getActiveItem().getItem() instanceof BowItem) {
         CLIENT.interactionManager.stopUsingItem(CLIENT.player);
      }
   }

   private Rotation resolve3(Vec3d vec3d, float f) {
      Vec3d vec3d10 = CLIENT.player.getEyePos().subtract(0.0, 0.1, 0.0);
      double doubleValue16 = vec3d.x - vec3d10.x;
      double doubleValue17 = vec3d.z - vec3d10.z;
      double doubleValue18 = Math.sqrt(doubleValue16 * doubleValue16 + doubleValue17 * doubleValue17);
      double doubleValue19 = vec3d.y - vec3d10.y;
      float floatValue2 = (float)Math.toDegrees(Math.atan2(-doubleValue16, doubleValue17));
      float floatValue3 = this.measure2(doubleValue18, doubleValue19, f);
      return new Rotation(floatValue2, floatValue3);
   }

   private float measure(int i) {
      float floatValue4 = i / 20.0F;
      floatValue4 = (floatValue4 * floatValue4 + floatValue4 * 2.0F) / 3.0F;
      if (floatValue4 > 1.0F) {
         floatValue4 = 1.0F;
      }

      return floatValue4 * 3.0F;
   }

   private int compute(BlockPos blockPos, Vec3d vec3d) {
      for (int intValue6 = 8; intValue6 < 20; intValue6++) {
         float floatValue5 = this.measure(intValue6);
         Rotation rotation4 = this.resolve3(vec3d, floatValue5);
         if (this.check6(rotation4.floatValue, rotation4.floatValue2, floatValue5, blockPos)) {
            return Math.min(20, intValue6 + 3);
         }
      }

      return 20;
   }

   private float measure2(double d, double e, float f) {
      if (d < 0.35) {
         return e >= 0.0 ? -75.0F : 75.0F;
      } else {
         float floatValue6 = -89.0F;
         float floatValue7 = 89.0F;

         for (int intValue7 = 0; intValue7 < 60; intValue7++) {
            float floatValue8 = (floatValue6 + floatValue7) / 2.0F;
            double doubleValue20 = this.measure3(d, floatValue8, f);
            if (doubleValue20 > e) {
               floatValue6 = floatValue8;
            } else {
               floatValue7 = floatValue8;
            }
         }

         return (floatValue6 + floatValue7) / 2.0F;
      }
   }

   private double measure3(double d, float f, float g) {
      double doubleValue21 = Math.toRadians(f);
      double doubleValue22 = g * Math.cos(doubleValue21);
      double doubleValue23 = -g * Math.sin(doubleValue21);
      double doubleValue24 = 0.0;
      double doubleValue25 = 0.0;

      for (int intValue8 = 0; intValue8 < 600; intValue8++) {
         double doubleValue26 = doubleValue24;
         double doubleValue27 = doubleValue25;
         doubleValue24 += doubleValue22;
         doubleValue25 += doubleValue23;
         doubleValue22 *= 0.99;
         doubleValue23 *= 0.99;
         doubleValue23 -= 0.05;
         if (doubleValue24 >= d) {
            double doubleValue28 = doubleValue24 - doubleValue26 > 0.001 ? (d - doubleValue26) / (doubleValue24 - doubleValue26) : 1.0;
            return doubleValue27 + (doubleValue25 - doubleValue27) * doubleValue28;
         }
      }

      return doubleValue25;
   }

   private boolean check5(BlockPos blockPos, float f) {
      return this.check6(CLIENT.player.getYaw(), CLIENT.player.getPitch(), f, blockPos);
   }

   private boolean check6(double d, double e, float f, BlockPos blockPos) {
      double doubleValue29 = Math.toRadians(d);
      double doubleValue30 = Math.toRadians(e);
      double doubleValue31 = Math.cos(doubleValue30);
      Vec3d vec3d11 = new Vec3d(-Math.sin(doubleValue29) * doubleValue31, -Math.sin(doubleValue30), Math.cos(doubleValue29) * doubleValue31);
      Vec3d vec3d12 = vec3d11.multiply(f);
      Vec3d vec3d13 = CLIENT.player.getMovement();
      vec3d12 = vec3d12.add(vec3d13.x, CLIENT.player.isOnGround() ? 0.0 : vec3d13.y, vec3d13.z);
      Vec3d vec3d14 = CLIENT.player.getEyePos().subtract(0.0, 0.1, 0.0);
      double doubleValue32 = this.compute2() - 6;

      for (int intValue9 = 0; intValue9 < 120; intValue9++) {
         Vec3d vec3d15 = vec3d14.add(vec3d12);
         BlockHitResult blockHitResult7 = CLIENT.world.raycast(new RaycastContext(vec3d14, vec3d15, ShapeType.COLLIDER, FluidHandling.NONE, CLIENT.player));
         if (blockHitResult7.getType() == Type.BLOCK) {
            return blockHitResult7.getBlockPos().equals(blockPos);
         }

         vec3d14 = vec3d15;
         vec3d12 = vec3d12.multiply(0.99).subtract(0.0, 0.05, 0.0);
         if (vec3d15.y < doubleValue32) {
            break;
         }
      }

      return false;
   }

   private void invoke12() {
      this.items.clear();
      long longValue2 = System.currentTimeMillis();
      this.hashMap2.entrySet().removeIf(entry -> longValue2 > entry.getValue());
      if (this.blockPos3 != null) {
         BlockState blockState6 = CLIENT.world.getBlockState(this.blockPos3);
         if (this.check13(this.blockPos3) || !blockState6.isOf(Blocks.CHORUS_PLANT) && !blockState6.isOf(Blocks.CHORUS_FLOWER)) {
            this.blockPos3 = null;
         }
      }

      boolean flag4 = this.avtoPosadka.isEnabled() && this.check19();
      boolean flag5 = this.sbivatPlody.isEnabled();
      int intValue10 = (int)this.vysotaDlyaSbora.getValue();
      int[] intValues2 = this.resolve11();
      ArrayList arrayList = new ArrayList();
      ArrayList arrayList2 = new ArrayList();
      int intValue11 = 0;
      int intValue12 = 0;
      int intValue13 = 0;

      for (BlockPos blockPos12 : BlockPos.iterate(intValues2[0], intValues2[1], intValues2[2], intValues2[3], intValues2[4], intValues2[5])) {
         BlockState blockState7 = CLIENT.world.getBlockState(blockPos12);
         boolean flag6 = blockState7.isOf(Blocks.CHORUS_FLOWER);
         boolean flag7 = blockState7.isOf(Blocks.CHORUS_PLANT);
         if (flag6 || flag7 || blockState7.isOf(Blocks.END_STONE)) {
            BlockPos blockPos13 = blockPos12.toImmutable();
            if (blockState7.isOf(Blocks.END_STONE)) {
               if (flag4) {
                  BlockPos blockPos14 = blockPos13.up();
                  if (this.check8(blockPos14) && !this.check13(blockPos14) && !this.check13(blockPos13)) {
                     BlockState blockState8 = CLIENT.world.getBlockState(blockPos14);
                     if (blockState8.isAir() || blockState8.isReplaceable()) {
                        this.items.add(new ChorusFarm.ChorusFarmState3(ChorusFarm.ChorusFarmState.PLANT, blockPos13, Direction.UP));
                        intValue12++;
                     }
                  }
               }
            } else {
               arrayList2.add(blockPos13);
               if (CLIENT.world.getBlockState(blockPos13.down()).isOf(Blocks.END_STONE) && !this.check13(blockPos13)) {
                  arrayList.add(blockPos13);
               }
            }
         }
      }

      Set values2 = this.resolve4((List<BlockPos>)arrayList);
      int intValue14 = this.compute2() + 4 + 1;

      for (BlockPos blockPos15 : (List<BlockPos>)arrayList2) {
         if (!values2.contains(blockPos15) && !this.check13(blockPos15) && blockPos15.getY() <= intValue14) {
            this.items.add(new ChorusFarm.ChorusFarmState3(ChorusFarm.ChorusFarmState.CLEAR, blockPos15, null));
            intValue13++;
         }
      }

      HashSet hashSet = new HashSet();

      for (BlockPos blockPos16 : (List<BlockPos>)arrayList) {
         ChorusFarm.ChorusFarmBounds chorusFarmBounds = this.resolve5(blockPos16);
         boolean flag8 = !chorusFarmBounds.flowers().isEmpty();
         boolean flag9 = this.blockPos3 != null && blockPos16.equals(this.blockPos3);
         if (!flag8) {
            if (!flag9 && !this.values.contains(blockPos16)) {
               hashSet.add(blockPos16);
            } else {
               this.items.add(new ChorusFarm.ChorusFarmState3(ChorusFarm.ChorusFarmState.CLEAR, blockPos16, null));
               intValue13++;
            }
         } else if (flag9 || chorusFarmBounds.height() >= intValue10) {
            if (flag5) {
               for (BlockPos blockPos17 : chorusFarmBounds.flowers()) {
                  if (!this.check13(blockPos17)) {
                     this.items.add(new ChorusFarm.ChorusFarmState3(ChorusFarm.ChorusFarmState.SHOOT, blockPos17, null));
                     intValue11++;
                  }
               }
            } else {
               this.items.add(new ChorusFarm.ChorusFarmState3(ChorusFarm.ChorusFarmState.CLEAR, blockPos16, null));
               intValue13++;
            }
         }
      }

      this.values.clear();
      this.values.addAll(hashSet);
      int intValue15 = intValue11 + intValue12 + intValue13;
      if (intValue15 > 0 && this.intValue9 == 0) {
         this.invoke22("Найдено: отстрел " + intValue11 + ", посадка " + intValue12 + ", очистка " + intValue13);
      }

      this.intValue9 = intValue15;
   }

   private Set<BlockPos> resolve4(List<BlockPos> list) {
      HashSet hashSet2 = new HashSet();
      ArrayDeque arrayDeque = new ArrayDeque();

      for (BlockPos blockPos18 : list) {
         if (hashSet2.add(blockPos18)) {
            arrayDeque.add(blockPos18);
         }
      }

      while (!arrayDeque.isEmpty() && hashSet2.size() < 1600) {
         BlockPos blockPos19 = (BlockPos)arrayDeque.poll();

         for (Direction direction2 : Direction.values()) {
            if (direction2 != Direction.DOWN) {
               BlockPos blockPos20 = blockPos19.offset(direction2);
               if (!hashSet2.contains(blockPos20)) {
                  BlockState blockState9 = CLIENT.world.getBlockState(blockPos20);
                  if (blockState9.isOf(Blocks.CHORUS_PLANT) || blockState9.isOf(Blocks.CHORUS_FLOWER)) {
                     hashSet2.add(blockPos20);
                     arrayDeque.add(blockPos20);
                  }
               }
            }
         }
      }

      return hashSet2;
   }

   private ChorusFarm.ChorusFarmBounds resolve5(BlockPos blockPos) {
      HashSet hashSet3 = new HashSet();
      ArrayDeque arrayDeque2 = new ArrayDeque();
      ArrayList arrayList3 = new ArrayList();
      arrayDeque2.add(blockPos);
      hashSet3.add(blockPos);
      int intValue16 = blockPos.getY();
      int intValue17 = intValue16;

      while (!arrayDeque2.isEmpty() && hashSet3.size() < 400) {
         BlockPos blockPos21 = (BlockPos)arrayDeque2.poll();
         if (blockPos21.getY() > intValue17) {
            intValue17 = blockPos21.getY();
         }

         if (CLIENT.world.getBlockState(blockPos21).isOf(Blocks.CHORUS_FLOWER)) {
            arrayList3.add(blockPos21);
         }

         for (Direction direction3 : Direction.values()) {
            if (direction3 != Direction.DOWN) {
               BlockPos blockPos22 = blockPos21.offset(direction3);
               if (!hashSet3.contains(blockPos22)) {
                  BlockState blockState10 = CLIENT.world.getBlockState(blockPos22);
                  if (blockState10.isOf(Blocks.CHORUS_PLANT) || blockState10.isOf(Blocks.CHORUS_FLOWER)) {
                     hashSet3.add(blockPos22);
                     arrayDeque2.add(blockPos22);
                  }
               }
            }
         }
      }

      return new ChorusFarm.ChorusFarmBounds(intValue17 - intValue16 + 1, arrayList3);
   }

   private ChorusFarm.ChorusFarmState3 resolve6() {
      Vec3d vec3d16 = CLIENT.player.getEyePos();
      ChorusFarm.ChorusFarmState3 chorusFarmState36 = null;
      double doubleValue33 = Double.MAX_VALUE;

      for (ChorusFarm.ChorusFarmState3 chorusFarmState37 : this.items) {
         if (this.check7(chorusFarmState37) && !this.check13(chorusFarmState37.blockPos) && !this.check14(chorusFarmState37.blockPos)) {
            double doubleValue34 = vec3d16.squaredDistanceTo(this.resolve7(chorusFarmState37));
            if (chorusFarmState37.chorusFarmState == ChorusFarm.ChorusFarmState.CLEAR) {
               doubleValue34 -= 64.0;
            } else if (chorusFarmState37.chorusFarmState == ChorusFarm.ChorusFarmState.PLANT) {
               doubleValue34 += 0.001;
            }

            if (this.blockPos3 != null && chorusFarmState37.blockPos.getSquaredDistance(this.blockPos3) < 64.0) {
               doubleValue34 -= 10000.0;
            }

            if (doubleValue34 < doubleValue33) {
               doubleValue33 = doubleValue34;
               chorusFarmState36 = chorusFarmState37;
            }
         }
      }

      return chorusFarmState36;
   }

   private boolean check7(ChorusFarm.ChorusFarmState3 chorusFarmState38) {
      if (chorusFarmState38 == null) {
         return false;
      } else {
         BlockState blockState11 = CLIENT.world.getBlockState(chorusFarmState38.blockPos);

         return switch (chorusFarmState38.chorusFarmState) {
            case SHOOT -> this.sbivatPlody.isEnabled() && blockState11.isOf(Blocks.CHORUS_FLOWER);
            case PLANT -> {
               if (!this.check19()) {
                  yield false;
               } else if (!blockState11.isOf(Blocks.END_STONE)) {
                  yield false;
               } else {
                  BlockState blockState12 = CLIENT.world.getBlockState(chorusFarmState38.blockPos.up());
                  yield blockState12.isAir() || blockState12.isReplaceable();
               }
            }
            case CLEAR -> blockState11.isOf(Blocks.CHORUS_PLANT) || blockState11.isOf(Blocks.CHORUS_FLOWER);
         };
      }
   }

   private Vec3d resolve7(ChorusFarm.ChorusFarmState3 chorusFarmState39) {
      return chorusFarmState39.chorusFarmState == ChorusFarm.ChorusFarmState.PLANT
         ? new Vec3d(chorusFarmState39.blockPos.getX() + 0.5, chorusFarmState39.blockPos.getY() + 1.0, chorusFarmState39.blockPos.getZ() + 0.5)
         : Vec3d.ofCenter(chorusFarmState39.blockPos);
   }

   private int compute2() {
      return Math.min(blockPos.getY(), blockPos2.getY());
   }

   private boolean check8(BlockPos blockPos) {
      int intValue18 = Math.min(blockPos.getX(), blockPos2.getX());
      int intValue19 = Math.max(blockPos.getX(), blockPos2.getX());
      int intValue20 = Math.min(blockPos.getZ(), blockPos2.getZ());
      int intValue21 = Math.max(blockPos.getZ(), blockPos2.getZ());
      return blockPos.getX() >= intValue18 && blockPos.getX() <= intValue19 && blockPos.getZ() >= intValue20 && blockPos.getZ() <= intValue21;
   }

   private void invoke13(BlockPos blockPos, int i) {
      IBaritone iBaritone = BaritoneAPI.getProvider().getPrimaryBaritone();
      boolean flag10 = !blockPos.equals(this.blockPos7);
      if (flag10 || !iBaritone.getCustomGoalProcess().isActive()) {
         iBaritone.getCustomGoalProcess().setGoalAndPath(new GoalNear(blockPos, i));
         if (flag10) {
            this.invoke22("Иду к " + this.resolve20(blockPos));
         }

         this.blockPos7 = blockPos;
      }
   }

   private void invoke14() {
      IBaritone iBaritone2 = BaritoneAPI.getProvider().getPrimaryBaritone();
      if (iBaritone2.getCustomGoalProcess().isActive()) {
         iBaritone2.getPathingBehavior().cancelEverything();
      }

      this.blockPos7 = null;
   }

   private boolean check9() {
      if (this.blockPos3 != null) {
         return false;
      } else if (!this.check10()) {
         return false;
      } else {
         ItemEntity itemEntity2 = this.resolve8();
         if (itemEntity2 == null) {
            this.intValue10 = -1;
            return false;
         } else {
            double doubleValue35 = CLIENT.player.getX() - itemEntity2.getX();
            double doubleValue36 = CLIENT.player.getZ() - itemEntity2.getZ();
            double doubleValue37 = doubleValue35 * doubleValue35 + doubleValue36 * doubleValue36;
            double doubleValue38 = Math.abs(CLIENT.player.getY() - itemEntity2.getY());
            if (doubleValue37 <= 0.8 && doubleValue38 < 1.3) {
               this.intValue10 = -1;
               return false;
            } else {
               if (itemEntity2.getId() != this.intValue10) {
                  this.intValue10 = itemEntity2.getId();
                  this.dualTimer8.invoke();
               }

               if (this.dualTimer8.check5(1500L)) {
                  BlockPos blockPos23 = this.resolve9(itemEntity2);
                  if (blockPos23 != null) {
                     this.blockPos3 = blockPos23;
                     this.intValue10 = -1;
                     this.invoke22("Плод завис на растении, харвест корня " + this.resolve20(blockPos23));
                     return false;
                  }
               }

               if (this.dualTimer8.check5(10000L)) {
                  this.hashMap3.put(itemEntity2.getId(), System.currentTimeMillis() + 18000L);
                  this.intValue10 = -1;
                  return false;
               } else {
                  this.invoke11();
                  this.invoke13(BlockPos.ofFloored(itemEntity2.getX(), itemEntity2.getY() + 0.1, itemEntity2.getZ()), 0);
                  return true;
               }
            }
         }
      }
   }

   private ItemEntity resolve8() {
      Box box = new Box(
            Math.min(blockPos.getX(), blockPos2.getX()),
            this.compute2() - 4,
            Math.min(blockPos.getZ(), blockPos2.getZ()),
            Math.max(blockPos.getX(), blockPos2.getX()) + 1,
            this.compute2() + 32,
            Math.max(blockPos.getZ(), blockPos2.getZ()) + 1
         )
         .expand(2.5);
      List items = CLIENT.world
         .getEntitiesByClass(
            ItemEntity.class,
            box,
            itemEntity -> itemEntity.isAlive() && (itemEntity.getStack().isOf(Items.CHORUS_FRUIT) || itemEntity.getStack().isOf(Items.CHORUS_FLOWER))
         );
      ItemEntity itemEntity3 = null;
      double doubleValue39 = Double.MAX_VALUE;
      long longValue3 = System.currentTimeMillis();

      for (ItemEntity itemEntity4 : (List<ItemEntity>)items) {
         Long longValue4 = this.hashMap3.get(itemEntity4.getId());
         if (longValue4 != null) {
            if (longValue3 <= longValue4) {
               continue;
            }

            this.hashMap3.remove(itemEntity4.getId());
         }

         double doubleValue40 = CLIENT.player.squaredDistanceTo(itemEntity4);
         if (doubleValue40 < doubleValue39) {
            doubleValue39 = doubleValue40;
            itemEntity3 = itemEntity4;
         }
      }

      return itemEntity3;
   }

   private BlockPos resolve9(ItemEntity itemEntity) {
      if (itemEntity.getY() - this.compute2() < 1.5) {
         return null;
      } else {
         BlockPos blockPos24 = BlockPos.ofFloored(itemEntity.getX(), itemEntity.getY() + 0.05, itemEntity.getZ());
         BlockPos blockPos25 = null;

         for (BlockPos blockPos26 : new BlockPos[]{blockPos24.down(), blockPos24, blockPos24.up()}) {
            BlockState blockState13 = CLIENT.world.getBlockState(blockPos26);
            if (blockState13.isOf(Blocks.CHORUS_PLANT) || blockState13.isOf(Blocks.CHORUS_FLOWER)) {
               blockPos25 = blockPos26;
               break;
            }
         }

         return blockPos25 == null ? null : this.resolve10(blockPos25);
      }
   }

   private BlockPos resolve10(BlockPos blockPos) {
      HashSet hashSet4 = new HashSet();
      ArrayDeque arrayDeque3 = new ArrayDeque();
      hashSet4.add(blockPos);
      arrayDeque3.add(blockPos);

      while (!arrayDeque3.isEmpty() && hashSet4.size() < 400) {
         BlockPos blockPos27 = (BlockPos)arrayDeque3.poll();
         BlockState blockState14 = CLIENT.world.getBlockState(blockPos27);
         if ((blockState14.isOf(Blocks.CHORUS_PLANT) || blockState14.isOf(Blocks.CHORUS_FLOWER))
            && CLIENT.world.getBlockState(blockPos27.down()).isOf(Blocks.END_STONE)
            && !this.check13(blockPos27)) {
            return blockPos27;
         }

         for (Direction direction4 : Direction.values()) {
            BlockPos blockPos28 = blockPos27.offset(direction4);
            if (!hashSet4.contains(blockPos28)) {
               BlockState blockState15 = CLIENT.world.getBlockState(blockPos28);
               if (blockState15.isOf(Blocks.CHORUS_PLANT) || blockState15.isOf(Blocks.CHORUS_FLOWER)) {
                  hashSet4.add(blockPos28);
                  arrayDeque3.add(blockPos28);
               }
            }
         }
      }

      return null;
   }

   private boolean check10() {
      for (int intValue22 = 0; intValue22 < 36; intValue22++) {
         ItemStack itemStack2 = CLIENT.player.getInventory().getStack(intValue22);
         if (itemStack2.isEmpty()) {
            return true;
         }

         if ((itemStack2.isOf(Items.CHORUS_FRUIT) || itemStack2.isOf(Items.CHORUS_FLOWER)) && itemStack2.getCount() < itemStack2.getMaxCount()) {
            return true;
         }
      }

      return false;
   }

   private int[] resolve11() {
      int intValue23 = Math.min(blockPos.getX(), blockPos2.getX());
      int intValue24 = Math.min(blockPos.getZ(), blockPos2.getZ());
      int intValue25 = Math.max(blockPos.getX(), blockPos2.getX());
      int intValue26 = Math.max(blockPos.getZ(), blockPos2.getZ());
      BlockPos blockPos29 = CLIENT.player.getBlockPos();
      intValue23 = Math.max(intValue23, blockPos29.getX() - 40);
      intValue24 = Math.max(intValue24, blockPos29.getZ() - 40);
      intValue25 = Math.min(intValue25, blockPos29.getX() + 40);
      intValue26 = Math.min(intValue26, blockPos29.getZ() + 40);
      int intValue27 = this.compute2() - 4;
      int intValue28 = this.compute2() + 32;
      return new int[]{intValue23, intValue27, intValue24, intValue25, intValue28, intValue26};
   }

   private BlockPos resolve12() {
      int[] intValues3 = this.resolve11();
      Vec3d vec3d17 = CLIENT.player.getEyePos();
      BlockPos blockPos30 = null;
      double doubleValue41 = Double.MAX_VALUE;

      for (BlockPos blockPos31 : BlockPos.iterate(intValues3[0], intValues3[1], intValues3[2], intValues3[3], intValues3[4], intValues3[5])) {
         if (this.check11(CLIENT.world.getBlockState(blockPos31))) {
            double doubleValue42 = vec3d17.squaredDistanceTo(Vec3d.ofCenter(blockPos31));
            if (doubleValue42 < doubleValue41) {
               doubleValue41 = doubleValue42;
               blockPos30 = blockPos31.toImmutable();
            }
         }
      }

      return blockPos30;
   }

   private boolean check11(BlockState blockState) {
      return blockState.isOf(Blocks.CHEST) || blockState.isOf(Blocks.TRAPPED_CHEST) || blockState.isOf(Blocks.BARREL);
   }

   private boolean check12() {
      return this.blockPos8 != null && this.check11(CLIENT.world.getBlockState(this.blockPos8));
   }

   private void invoke15(boolean bl) {
      if (bl) {
         this.timestamp2 = System.currentTimeMillis() + 30000L;
      }

      this.blockPos8 = null;
      this.intValue2 = -1;
      this.invoke14();
      this.chorusFarmState2 = ChorusFarm.ChorusFarmState2.FARM;
   }

   private void invoke16() {
      if (!this.check12()) {
         this.invoke15(false);
      } else if (this.dualTimer7.check5(15000L)) {
         this.invoke22("Не смог дойти до сундука, вернусь позже");
         this.invoke15(true);
      } else if (CLIENT.player.getEyePos().distanceTo(Vec3d.ofCenter(this.blockPos8)) <= 4.5) {
         this.invoke14();
         this.dualTimer.invoke();
         this.chorusFarmState2 = ChorusFarm.ChorusFarmState2.INTERACTING;
      } else {
         this.invoke13(this.blockPos8, 2);
      }
   }

   private void invoke17() {
      if (!this.check12()) {
         this.invoke15(false);
      } else if (this.intValue3 >= 3) {
         this.invoke22("Сундук не открывается, блокирую");
         this.invoke15(true);
      } else {
         this.invoke14();
         if (CLIENT.player.getEyePos().distanceTo(Vec3d.ofCenter(this.blockPos8)) > 4.6) {
            this.dualTimer7.invoke();
            this.chorusFarmState2 = ChorusFarm.ChorusFarmState2.NAVIGATING;
         } else {
            BlockHitResult blockHitResult8 = this.resolve14(this.blockPos8);
            Vec3d vec3d18 = blockHitResult8 != null ? blockHitResult8.getPos() : Vec3d.ofCenter(this.blockPos8);
            Rotation rotation5 = this.resolve19(vec3d18);
            this.invoke21(rotation5);
            if (!(new Rotation(CLIENT.player).measure(rotation5) > 4.0F)) {
               if (this.dualTimer.check5(90L)) {
                  BlockHitResult blockHitResult9 = blockHitResult8 != null ? blockHitResult8 : new BlockHitResult(vec3d18, Direction.UP, this.blockPos8, false);
                  CLIENT.interactionManager.interactBlock(CLIENT.player, Hand.MAIN_HAND, blockHitResult9);
                  CLIENT.player.swingHand(Hand.MAIN_HAND);
                  this.intValue3++;
                  this.intValue2 = -1;
                  this.dualTimer7.invoke();
                  this.dualTimer.invoke();
                  this.chorusFarmState2 = ChorusFarm.ChorusFarmState2.WAITING_FOR_CONTAINER;
               }
            }
         }
      }
   }

   private void invoke18() {
      this.invoke14();
      if (CLIENT.currentScreen instanceof GenericContainerScreen genericContainerScreen) {
         int intValue29 = ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).syncId;
         if (CLIENT.player.currentScreenHandler != null && CLIENT.player.currentScreenHandler.syncId == intValue29) {
            this.intValue2 = intValue29;
            this.intValue4 = -1;
            this.intValue5 = 0;
            this.dualTimer7.invoke();
            this.chorusFarmState2 = ChorusFarm.ChorusFarmState2.DEPOSITING;
            return;
         }
      }

      if (this.dualTimer7.check5(4000L)) {
         this.invoke22("Сундук не ответил открытием, повтор подхода");
         this.dualTimer7.invoke();
         this.chorusFarmState2 = ChorusFarm.ChorusFarmState2.NAVIGATING;
      }
   }

   private void invoke19() {
      if (!(
         CLIENT.currentScreen instanceof GenericContainerScreen genericContainerScreen2
            && CLIENT.player.currentScreenHandler != null
            && CLIENT.player.currentScreenHandler.syncId == this.intValue2
            && ((GenericContainerScreenHandler)genericContainerScreen2.getScreenHandler()).syncId == this.intValue2
      )) {
         this.invoke15(false);
      } else if (this.dualTimer7.check5(50L)) {
         int intValue30 = this.compute5();
         if (this.intValue4 >= 0 && intValue30 >= this.intValue4) {
            this.intValue5++;
         } else {
            this.intValue5 = 0;
         }

         this.intValue4 = intValue30;
         if (this.intValue5 >= 3) {
            ChatUtil.sendClientMessage("§d[ChorusFarm] §fСундук заполнен, освободите место");
            CLIENT.player.closeHandledScreen();
            this.invoke15(true);
         } else {
            GenericContainerScreenHandler genericContainerScreenHandler2 = (GenericContainerScreenHandler)genericContainerScreen2.getScreenHandler();
            int intValue31 = genericContainerScreenHandler2.getRows() * 9;
            int intValue32 = this.compute6(genericContainerScreenHandler2, intValue31);
            if (intValue32 == -1) {
               CLIENT.player.closeHandledScreen();
               this.invoke22("Депозит завершён");
               this.invoke15(false);
            } else {
               CLIENT.interactionManager.clickSlot(this.intValue2, intValue32, 0, SlotActionType.QUICK_MOVE, CLIENT.player);
               this.dualTimer7.invoke();
            }
         }
      }
   }

   private int compute3() {
      int intValue33 = 0;

      for (int intValue34 = 0; intValue34 < 36; intValue34++) {
         ItemStack itemStack3 = CLIENT.player.getInventory().getStack(intValue34);
         if (itemStack3.isOf(Items.CHORUS_FRUIT)) {
            intValue33 += itemStack3.getCount();
         }
      }

      return intValue33;
   }

   private int compute4() {
      int intValue35 = 0;

      for (int intValue36 = 0; intValue36 < 36; intValue36++) {
         ItemStack itemStack4 = CLIENT.player.getInventory().getStack(intValue36);
         if (itemStack4.isOf(Items.CHORUS_FLOWER)) {
            intValue35 += itemStack4.getCount();
         }
      }

      return intValue35;
   }

   private int compute5() {
      return this.compute3() + Math.max(0, this.compute4() - 128);
   }

   private int compute6(GenericContainerScreenHandler genericContainerScreenHandler, int i) {
      for (int intValue37 = i; intValue37 < genericContainerScreenHandler.slots.size(); intValue37++) {
         Slot slot = genericContainerScreenHandler.getSlot(intValue37);
         if (slot.hasStack() && slot.getStack().isOf(Items.CHORUS_FRUIT)) {
            return intValue37;
         }
      }

      if (this.compute4() > 128) {
         for (int intValue38 = i; intValue38 < genericContainerScreenHandler.slots.size(); intValue38++) {
            Slot slot2 = genericContainerScreenHandler.getSlot(intValue38);
            if (slot2.hasStack() && slot2.getStack().isOf(Items.CHORUS_FLOWER)) {
               return intValue38;
            }
         }
      }

      return -1;
   }

   private boolean check13(BlockPos blockPos) {
      Long longValue5 = this.hashMap.get(blockPos);
      if (longValue5 == null) {
         return false;
      } else if (System.currentTimeMillis() > longValue5) {
         this.hashMap.remove(blockPos);
         return false;
      } else {
         return true;
      }
   }

   private boolean check14(BlockPos blockPos) {
      Long longValue6 = this.hashMap2.get(blockPos);
      if (longValue6 == null) {
         return false;
      } else if (System.currentTimeMillis() > longValue6) {
         this.hashMap2.remove(blockPos);
         return false;
      } else {
         return true;
      }
   }

   private void invoke20() {
      if (this.blockPos6 != null) {
         BlockState blockState16 = CLIENT.world.getBlockState(this.blockPos6);
         if (!blockState16.isOf(Blocks.CHORUS_PLANT) && !blockState16.isOf(Blocks.CHORUS_FLOWER)) {
            if (this.values2 != null) {
               long longValue7 = System.currentTimeMillis() + 3000L;

               for (BlockPos blockPos32 : this.values2) {
                  this.hashMap2.put(blockPos32, longValue7);
               }

               this.values2 = null;
            }

            this.blockPos6 = null;
         }
      }
   }

   private boolean check15() {
      return this.compute7() >= 4 ? true : this.compute8() == 0 && (this.compute3() > 0 || this.compute4() > 128);
   }

   private boolean check16() {
      if (CLIENT.player.getMainHandStack().getItem() instanceof BowItem) {
         return false;
      } else {
         for (int intValue39 = 0; intValue39 < 9; intValue39++) {
            if (CLIENT.player.getInventory().getStack(intValue39).getItem() instanceof BowItem) {
               CLIENT.player.getInventory().setSelectedSlot(intValue39);
               return false;
            }
         }

         for (int intValue40 = 9; intValue40 < 36; intValue40++) {
            if (CLIENT.player.getInventory().getStack(intValue40).getItem() instanceof BowItem) {
               CLIENT.interactionManager
                  .clickSlot(
                     CLIENT.player.playerScreenHandler.syncId,
                     intValue40,
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

   private boolean check17() {
      if (CLIENT.player.getMainHandStack().isOf(Items.CHORUS_FLOWER)) {
         return false;
      } else {
         for (int intValue41 = 0; intValue41 < 9; intValue41++) {
            if (CLIENT.player.getInventory().getStack(intValue41).isOf(Items.CHORUS_FLOWER)) {
               CLIENT.player.getInventory().setSelectedSlot(intValue41);
               return false;
            }
         }

         for (int intValue42 = 9; intValue42 < 36; intValue42++) {
            if (CLIENT.player.getInventory().getStack(intValue42).isOf(Items.CHORUS_FLOWER)) {
               CLIENT.interactionManager
                  .clickSlot(
                     CLIENT.player.playerScreenHandler.syncId,
                     intValue42,
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

   private boolean check18() {
      ItemStack itemStack5 = CLIENT.player.getMainHandStack();
      if (!(itemStack5.getItem() instanceof BowItem) && !itemStack5.isOf(Items.CHORUS_FLOWER)) {
         return false;
      } else {
         for (int intValue43 = 0; intValue43 < 9; intValue43++) {
            ItemStack itemStack6 = CLIENT.player.getInventory().getStack(intValue43);
            if (!itemStack6.isEmpty()
               && !(itemStack6.getItem() instanceof BowItem)
               && !itemStack6.isOf(Items.CHORUS_FLOWER)
               && !itemStack6.isOf(Items.ARROW)
               && !itemStack6.isOf(Items.CHORUS_FRUIT)) {
               CLIENT.player.getInventory().setSelectedSlot(intValue43);
               return false;
            }
         }

         return false;
      }
   }

   private boolean check19() {
      for (int intValue44 = 0; intValue44 < 36; intValue44++) {
         if (CLIENT.player.getInventory().getStack(intValue44).isOf(Items.CHORUS_FLOWER)) {
            return true;
         }
      }

      return false;
   }

   private boolean check20() {
      ItemStack itemStack7 = CLIENT.player.getMainHandStack();
      if (itemStack7.getItem() instanceof BowItem && this.check21(itemStack7)) {
         return true;
      } else {
         for (int intValue45 = 0; intValue45 < 36; intValue45++) {
            ItemStack itemStack8 = CLIENT.player.getInventory().getStack(intValue45);
            if (itemStack8.isOf(Items.ARROW) || itemStack8.isOf(Items.SPECTRAL_ARROW) || itemStack8.isOf(Items.TIPPED_ARROW)) {
               return true;
            }
         }

         return false;
      }
   }

   private boolean check21(ItemStack itemStack) {
      if (itemStack != null && !itemStack.isEmpty()) {
         ItemEnchantmentsComponent itemEnchantmentsComponent = (ItemEnchantmentsComponent)itemStack.get(DataComponentTypes.ENCHANTMENTS);
         if (itemEnchantmentsComponent != null && !itemEnchantmentsComponent.isEmpty()) {
            for (Entry entry2 : itemEnchantmentsComponent.getEnchantmentEntries()) {
               if (((RegistryEntry)entry2.getKey()).matchesKey(Enchantments.INFINITY)) {
                  return entry2.getIntValue() > 0;
               }
            }

            return false;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private int compute7() {
      int intValue46 = 0;

      for (int intValue47 = 0; intValue47 < 36; intValue47++) {
         if (CLIENT.player.getInventory().getStack(intValue47).isOf(Items.CHORUS_FRUIT)) {
            intValue46++;
         }
      }

      return intValue46;
   }

   private int compute8() {
      int intValue48 = 0;

      for (int intValue49 = 0; intValue49 < 36; intValue49++) {
         if (CLIENT.player.getInventory().getStack(intValue49).isEmpty()) {
            intValue48++;
         }
      }

      return intValue48;
   }

   private BlockHitResult resolve13(BlockPos blockPos, Direction direction) {
      BlockHitResult blockHitResult10 = this.resolve15(blockPos, direction);
      if (blockHitResult10 != null) {
         return blockHitResult10;
      } else {
         Vec3d vec3d19 = CLIENT.player.getEyePos();
         Vec3d vec3d20 = Vec3d.of(direction.getVector());
         if (vec3d19.subtract(this.resolve17(blockPos, direction)).dotProduct(vec3d20) <= 0.05) {
            return null;
         } else {
            double[] doubleValues = new double[]{0.5, 0.3, 0.7};

            for (double doubleValue43 : doubleValues) {
               for (double doubleValue44 : doubleValues) {
                  Vec3d vec3d21 = this.resolve16(blockPos, direction, doubleValue43, doubleValue44);
                  if (vec3d19.distanceTo(vec3d21) <= 4.6 && this.check22(vec3d19, vec3d21, blockPos)) {
                     return new BlockHitResult(vec3d21, direction, blockPos, false);
                  }
               }
            }

            return null;
         }
      }
   }

   private boolean check22(Vec3d vec3d, Vec3d vec3d2, BlockPos blockPos) {
      Vec3d vec3d22 = vec3d2.subtract(vec3d);
      double doubleValue45 = vec3d22.length();
      if (doubleValue45 < 1.0E-6) {
         return true;
      } else {
         vec3d22 = vec3d22.multiply(1.0 / doubleValue45);

         for (double doubleValue46 = 0.25; doubleValue46 < doubleValue45 - 0.05; doubleValue46 += 0.25) {
            Vec3d vec3d23 = vec3d.add(vec3d22.multiply(doubleValue46));
            BlockPos blockPos33 = BlockPos.ofFloored(vec3d23.x, vec3d23.y, vec3d23.z);
            if (!blockPos33.equals(blockPos)) {
               BlockState blockState17 = CLIENT.world.getBlockState(blockPos33);
               if (!blockState17.isOf(Blocks.CHORUS_PLANT)
                  && !blockState17.isOf(Blocks.CHORUS_FLOWER)
                  && !blockState17.isAir()
                  && !blockState17.getCollisionShape(CLIENT.world, blockPos33).isEmpty()) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   private BlockHitResult resolve14(BlockPos blockPos) {
      Vec3d vec3d24 = CLIENT.player.getEyePos();
      double[] doubleValues2 = new double[]{0.5, 0.2, 0.8};

      for (double doubleValue47 : doubleValues2) {
         for (double doubleValue48 : doubleValues2) {
            for (double doubleValue49 : doubleValues2) {
               Vec3d vec3d25 = new Vec3d(blockPos.getX() + doubleValue47, blockPos.getY() + doubleValue48, blockPos.getZ() + doubleValue49);
               BlockHitResult blockHitResult11 = CLIENT.world.raycast(new RaycastContext(vec3d24, vec3d25, ShapeType.OUTLINE, FluidHandling.NONE, CLIENT.player));
               if (blockHitResult11.getType() == Type.BLOCK && blockHitResult11.getBlockPos().equals(blockPos)) {
                  return blockHitResult11;
               }
            }
         }
      }

      return null;
   }

   private BlockHitResult resolve15(BlockPos blockPos, Direction direction) {
      Vec3d vec3d26 = CLIENT.player.getEyePos();
      double[] doubleValues3 = new double[]{0.5, 0.3, 0.7};

      for (double doubleValue50 : doubleValues3) {
         for (double doubleValue51 : doubleValues3) {
            Vec3d vec3d27 = this.resolve16(blockPos, direction, doubleValue50, doubleValue51);
            BlockHitResult blockHitResult12 = CLIENT.world.raycast(new RaycastContext(vec3d26, vec3d27, ShapeType.OUTLINE, FluidHandling.NONE, CLIENT.player));
            if (blockHitResult12.getType() == Type.BLOCK && blockHitResult12.getBlockPos().equals(blockPos) && blockHitResult12.getSide() == direction) {
               return blockHitResult12;
            }
         }
      }

      return null;
   }

   private Vec3d resolve16(BlockPos blockPos, Direction direction, double d, double e) {
      double doubleValue52 = blockPos.getX();
      double doubleValue53 = blockPos.getY();
      double doubleValue54 = blockPos.getZ();

      return switch (direction) {
         case NORTH -> new Vec3d(doubleValue52 + d, doubleValue53 + e, doubleValue54);
         case SOUTH -> new Vec3d(doubleValue52 + d, doubleValue53 + e, doubleValue54 + 1.0);
         case WEST -> new Vec3d(doubleValue52, doubleValue53 + d, doubleValue54 + e);
         case EAST -> new Vec3d(doubleValue52 + 1.0, doubleValue53 + d, doubleValue54 + e);
         case DOWN -> new Vec3d(doubleValue52 + d, doubleValue53, doubleValue54 + e);
         case UP -> new Vec3d(doubleValue52 + d, doubleValue53 + 1.0, doubleValue54 + e);
         default -> throw new MatchException(null, null);
      };
   }

   private Vec3d resolve17(BlockPos blockPos, Direction direction) {
      return new Vec3d(
         blockPos.getX() + 0.5 + direction.getOffsetX() * 0.5,
         blockPos.getY() + 0.5 + direction.getOffsetY() * 0.5,
         blockPos.getZ() + 0.5 + direction.getOffsetZ() * 0.5
      );
   }

   private void invoke21(Rotation rotation6) {
      float floatValue9 = new Rotation(CLIENT.player).measure(rotation6);
      float floatValue10 = Math.max(34.0F, Math.min(140.0F, floatValue9 * 1.35F));
      RotationController.invoke3(rotation6, floatValue10, floatValue10, floatValue10, floatValue10, 2, 20, false);
   }

   private BlockHitResult resolve18() {
      double doubleValue55 = Math.toRadians(CLIENT.player.getYaw());
      double doubleValue56 = Math.toRadians(CLIENT.player.getPitch());
      double doubleValue57 = Math.cos(doubleValue56);
      Vec3d vec3d28 = new Vec3d(-Math.sin(doubleValue55) * doubleValue57, -Math.sin(doubleValue56), Math.cos(doubleValue55) * doubleValue57);
      Vec3d vec3d29 = CLIENT.player.getEyePos();
      Vec3d vec3d30 = vec3d29.add(vec3d28.multiply(5.0));
      BlockHitResult blockHitResult13 = CLIENT.world.raycast(new RaycastContext(vec3d29, vec3d30, ShapeType.OUTLINE, FluidHandling.NONE, CLIENT.player));
      return blockHitResult13.getType() == Type.BLOCK ? blockHitResult13 : null;
   }

   private Rotation resolve19(Vec3d vec3d) {
      Vec3d vec3d31 = CLIENT.player.getEyePos();
      double doubleValue58 = vec3d.x - vec3d31.x;
      double doubleValue59 = vec3d.y - vec3d31.y;
      double doubleValue60 = vec3d.z - vec3d31.z;
      double doubleValue61 = Math.sqrt(doubleValue58 * doubleValue58 + doubleValue60 * doubleValue60);
      float floatValue11 = (float)Math.toDegrees(Math.atan2(-doubleValue58, doubleValue60));
      float floatValue12 = (float)(-Math.toDegrees(Math.atan2(doubleValue59, doubleValue61)));
      return new Rotation(floatValue11, floatValue12);
   }

   private String resolve20(BlockPos blockPos) {
      return blockPos.getX() + " " + blockPos.getY() + " " + blockPos.getZ();
   }

   private void invoke22(String string) {
      if (this.logi.isEnabled()) {
         ChatUtil.sendClientMessage("§d[ChorusFarm] §7" + string);
      }
   }

   @EventHandler
   public void onRender3D(Render3DEvent render3DEvent) {
      if (CLIENT.world != null && CLIENT.player != null && blockPos != null && blockPos2 != null) {
         if (WorldRenderBuffer.check(CLIENT)) {
            Vec3d vec3d32 = CLIENT.gameRenderer.getCamera().getPos();
            Matrix4f matrix4f2 = render3DEvent.getMatrixStack().peek().getPositionMatrix();
            int intValue50 = this.compute2() - 1;
            float floatValue13 = (float)(Math.min(blockPos.getX(), blockPos2.getX()) - vec3d32.x);
            float floatValue14 = (float)(intValue50 - vec3d32.y);
            float floatValue15 = (float)(Math.min(blockPos.getZ(), blockPos2.getZ()) - vec3d32.z);
            float floatValue16 = (float)(Math.max(blockPos.getX(), blockPos2.getX()) + 1 - vec3d32.x);
            float floatValue17 = (float)(intValue50 + 16 - vec3d32.y);
            float floatValue18 = (float)(Math.max(blockPos.getZ(), blockPos2.getZ()) + 1 - vec3d32.z);
            float floatValue19 = (float)(System.nanoTime() / 1.0E9);
            Immediate immediate = WorldRenderBuffer.getIMMEDIATE();

            try {
               VertexConsumer vertexConsumer2 = immediate.getBuffer(RENDER_LAYER);
               VertexConsumer vertexConsumer3 = immediate.getBuffer(RENDER_LAYER_2);
               this.invoke25(vertexConsumer3, matrix4f2, floatValue13, floatValue14, floatValue15, floatValue16, floatValue17, floatValue18, floatValue19);
            } finally {
               WorldRenderBuffer.invoke();
            }
         }
      }
   }

   private void invoke23(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float g, float h, float i, float j, float k) {
      float floatValue20 = j - g;

      for (int intValue51 = 0; intValue51 < 18; intValue51++) {
         float floatValue21 = intValue51 / 18.0F;
         float floatValue22 = (intValue51 + 1) / 18.0F;
         float floatValue23 = g + floatValue20 * floatValue21;
         float floatValue24 = g + floatValue20 * floatValue22;
         int intValue52 = ColorUtils.compute2(ColorUtils.compute14(-2995201, -9822240, floatValue21), (int)(120.0F * (1.0F - 0.7F * floatValue21)));
         int intValue53 = ColorUtils.compute2(ColorUtils.compute14(-2995201, -9822240, floatValue22), (int)(120.0F * (1.0F - 0.7F * floatValue22)));
         this.invoke24(vertexConsumer, matrix4f, f, h, i, h, floatValue23, floatValue24, intValue52, intValue53);
         this.invoke24(vertexConsumer, matrix4f, i, k, f, k, floatValue23, floatValue24, intValue52, intValue53);
         this.invoke24(vertexConsumer, matrix4f, f, k, f, h, floatValue23, floatValue24, intValue52, intValue53);
         this.invoke24(vertexConsumer, matrix4f, i, h, i, k, floatValue23, floatValue24, intValue52, intValue53);
      }
   }

   private void invoke24(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float g, float h, float i, float j, float k, int l, int m) {
      int intValue54 = ColorUtils.compute5(l);
      int intValue55 = ColorUtils.compute6(l);
      int intValue56 = ColorUtils.compute7(l);
      int intValue57 = ColorUtils.compute4(l);
      int intValue58 = ColorUtils.compute5(m);
      int intValue59 = ColorUtils.compute6(m);
      int intValue60 = ColorUtils.compute7(m);
      int intValue61 = ColorUtils.compute4(m);
      vertexConsumer.vertex(matrix4f, f, j, g).color(intValue54, intValue55, intValue56, intValue57);
      vertexConsumer.vertex(matrix4f, h, j, i).color(intValue54, intValue55, intValue56, intValue57);
      vertexConsumer.vertex(matrix4f, h, k, i).color(intValue58, intValue59, intValue60, intValue61);
      vertexConsumer.vertex(matrix4f, f, k, g).color(intValue58, intValue59, intValue60, intValue61);
   }

   private void invoke25(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float g, float h, float i, float j, float k, float l) {
      float floatValue25 = l * 1.4F;
      int intValue62 = ColorUtils.compute2(-2995201, 200);
      float floatValue26 = 0.02F;
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
         this.invoke26(vertexConsumer, matrix4f, floatValues[0], floatValues[1], floatValues[2], floatValues[3], floatValues[4], floatValues[5], floatValue26, intValue62, floatValue25);
      }
   }

   private void invoke26(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float g, float h, float i, float j, float k, float l, int m, float n) {
      float floatValue27 = i - f;
      float floatValue28 = j - g;
      float floatValue29 = k - h;
      float floatValue30 = (float)Math.sqrt(floatValue27 * floatValue27 + floatValue28 * floatValue28 + floatValue29 * floatValue29);
      if (!(floatValue30 < 1.0E-4F)) {
         float floatValue31 = floatValue27 / floatValue30;
         float floatValue32 = floatValue28 / floatValue30;
         float floatValue33 = floatValue29 / floatValue30;
         float floatValue34 = 0.45F;
         float floatValue35 = 0.35F;
         float floatValue36 = Math.max(floatValue34 + floatValue35, floatValue30 / 40.0F);
         floatValue34 = floatValue36 * 0.56F;
         float floatValue37 = -((n % floatValue36 + floatValue36) % floatValue36);

         for (float floatValue38 = floatValue37; floatValue38 < floatValue30; floatValue38 += floatValue36) {
            float floatValue39 = Math.max(0.0F, floatValue38);
            float floatValue40 = Math.min(floatValue30, floatValue38 + floatValue34);
            if (!(floatValue40 <= floatValue39)) {
               float floatValue41 = f + floatValue31 * floatValue39;
               float floatValue42 = g + floatValue32 * floatValue39;
               float floatValue43 = h + floatValue33 * floatValue39;
               float floatValue44 = f + floatValue31 * floatValue40;
               float floatValue45 = g + floatValue32 * floatValue40;
               float floatValue46 = h + floatValue33 * floatValue40;
               WorldBoxRenderer.invoke5(
                  vertexConsumer,
                  matrix4f,
                  Math.min(floatValue41, floatValue44) - l,
                  Math.min(floatValue42, floatValue45) - l,
                  Math.min(floatValue43, floatValue46) - l,
                  Math.max(floatValue41, floatValue44) + l,
                  Math.max(floatValue42, floatValue45) + l,
                  Math.max(floatValue43, floatValue46) + l,
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
      ChorusFarm.blockPos = blockPos;
   }

   @Generated
   public static BlockPos getBlockPos2() {
      return blockPos2;
   }

   @Generated
   public static void setBlockPos2(BlockPos blockPos) {
      blockPos2 = blockPos;
   }

   static enum ChorusFarmState {
      SHOOT,
      PLANT,
      CLEAR;
   }

   record ChorusFarmBounds(int height, List<BlockPos> flowers) {
   }

   static enum ChorusFarmState2 {
      FARM,
      NAVIGATING,
      INTERACTING,
      WAITING_FOR_CONTAINER,
      DEPOSITING;
   }

   static final class ChorusFarmState3 {
      final ChorusFarm.ChorusFarmState chorusFarmState;
      final BlockPos blockPos;
      final Direction direction;

      ChorusFarmState3(ChorusFarm.ChorusFarmState chorusFarmState, BlockPos blockPos, Direction direction) {
         this.chorusFarmState = chorusFarmState;
         this.blockPos = blockPos;
         this.direction = direction;
      }
   }
}
