package ru.metaculture.protection;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline.Snippet;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.vertex.VertexFormat.DrawMode;
import java.awt.Color;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.RenderLayer.MultiPhaseParameters;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.ChestMinecartEntity;
import net.minecraft.entity.vehicle.HopperMinecartEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import net.minecraft.world.chunk.WorldChunk;
import org.joml.Matrix4f;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "BaseFinder",
   category = Category.Misc,
   description = "Ищет базы, пишет в ТГ и копает"
)
public class BaseFinder extends Module {
   public final GroupSetting bloki = new GroupSetting(
      "Блоки",
      new BooleanSetting("Сундуки", true),
      new BooleanSetting("Шалкера", true),
      new BooleanSetting("Бочки", true),
      new BooleanSetting("Наковальни", true),
      new BooleanSetting("Печка", false),
      new BooleanSetting("Эндер сундук", true)
   );
   public final BooleanSetting iskatVagonetki = new BooleanSetting("Искать вагонетки", true);
   public final BooleanSetting avtoTunnel = new BooleanSetting("Авто-туннель (#)", true);
   public final BooleanSetting kopatKNahodke = new BooleanSetting("Копать к находке", true).visibleWhen(() -> !this.avtoTunnel.isEnabled());
   public final BooleanSetting vyklPriIgroke = new BooleanSetting("Выкл при игроке", true);
   public final BooleanSetting proverkiNaSvet = new BooleanSetting("Проверки на свет", false);
   public final BooleanSetting izbegatMobov = new BooleanSetting("Избегать мобов", false);
   public final BooleanSetting renderitNahodki = new BooleanSetting("Рендерить находки", true);
   public final NumberSetting radiusChankov = new NumberSetting("Радиус чанков", 4.0F, 1.0F, 8.0F, 1.0F, true);
   public final ModeSetting rezhimRaboty = new ModeSetting("Режим работы", "Tonnel", "Поиск приватом", "Tonnel");
   private final Set<BlockPos> values = Collections.newSetFromMap(new ConcurrentHashMap<>());
   private final Map<BlockPos, BlockEntityType<?>> valuesByKey = new ConcurrentHashMap<>();
   private final Set<Integer> values2 = Collections.newSetFromMap(new ConcurrentHashMap<>());
   private static final int INT_VALUE = 8;
   private static final int INT_VALUE_2 = 8;
   private int intValue = 0;
   private boolean flag = false;
   private BaseFinder.BaseFinderState baseFinderState = BaseFinder.BaseFinderState.CHECK_SUPPLIES;
   private int intValue2 = 0;
   private BlockPos blockPos = null;
   private BlockPos blockPos2 = null;
   private int intValue3 = 30;
   private int intValue4 = -1;
   public static final Map<Object, Integer> VALUES_BY_KEY = new HashMap<>();
   private static final int INT_VALUE_3 = 1024;
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
      "block_esp_box", 1024, false, true, RENDER_PIPELINE, MultiPhaseParameters.builder().build(false)
   );

   public BaseFinder() {
      this.addSettings(
         new Setting[]{
            this.bloki,
            this.iskatVagonetki,
            this.avtoTunnel,
            this.kopatKNahodke,
            this.vyklPriIgroke,
            this.proverkiNaSvet,
            this.izbegatMobov,
            this.renderitNahodki,
            this.radiusChankov,
            this.rezhimRaboty
         }
      );
      VALUES_BY_KEY.put(BlockEntityType.CHEST, ColorUtils.compute29(new Color(255, 194, 84).getRGB(), 100));
      VALUES_BY_KEY.put(BlockEntityType.TRAPPED_CHEST, ColorUtils.compute29(new Color(143, 109, 62).getRGB(), 100));
      VALUES_BY_KEY.put(BlockEntityType.ENDER_CHEST, ColorUtils.compute29(new Color(153, 49, 238).getRGB(), 100));
      VALUES_BY_KEY.put(BlockEntityType.BARREL, ColorUtils.compute29(new Color(250, 225, 62).getRGB(), 100));
      VALUES_BY_KEY.put(BlockEntityType.FURNACE, ColorUtils.compute29(new Color(115, 115, 115).getRGB(), 100));
      VALUES_BY_KEY.put(BlockEntityType.SHULKER_BOX, ColorUtils.compute29(new Color(246, 123, 123).getRGB(), 100));
      VALUES_BY_KEY.put(ChestMinecartEntity.class, ColorUtils.compute29(new Color(255, 100, 0).getRGB(), 100));
      VALUES_BY_KEY.put(HopperMinecartEntity.class, ColorUtils.compute29(new Color(100, 100, 100).getRGB(), 100));
   }

   @Override
   public void onEnable() {
      FreeLookController.active = true;
      super.onEnable();
      this.values.clear();
      this.valuesByKey.clear();
      this.values2.clear();
      this.intValue = 0;
      this.flag = false;
      this.baseFinderState = BaseFinder.BaseFinderState.CHECK_SUPPLIES;
      this.intValue2 = 0;
      this.blockPos = null;
      this.blockPos2 = null;
      this.intValue4 = -1;
      if (CLIENT.player != null) {
         if (this.rezhimRaboty.is("Tonnel") && this.avtoTunnel.isEnabled()) {
            try {
               CLIENT.player.networkHandler.sendChatMessage("#tunnel");
            } catch (Exception exception) {
               exception.printStackTrace();
            }
         }

         if (!ClientUtil.telegramNotifications.isEnabled()) {
            if (!TelegramApi.check()) {
               this.invoke10("§cВнимание! Telegram не настроен. Используйте .tapi");
            } else {
               this.invoke10("§aУведомления в Telegram включены.");
            }
         }
      }
   }

   @Override
   public void onDisable() {
      FreeLookController.active = false;
      super.onDisable();
      if (CLIENT.player != null && this.avtoTunnel.isEnabled()) {
         try {
            CLIENT.player.networkHandler.sendChatMessage("#stop");
         } catch (Exception exception2) {
            exception2.printStackTrace();
         }
      }

      this.flag = false;
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.world != null && CLIENT.player != null) {
         if (this.vyklPriIgroke.isEnabled()) {
            this.invoke3();
            if (!this.enabled) {
               return;
            }
         }

         if (this.rezhimRaboty.is("Поиск приватом")) {
            this.invoke();
         }

         if (this.intValue++ >= 10) {
            this.intValue = 0;
            if (!this.izbegatMobov.isEnabled() || !this.check4()) {
               this.invoke4();
               if (this.iskatVagonetki.isEnabled()) {
                  this.invoke5();
               }
            }
         }
      }
   }

   private void invoke() {
      if (this.intValue2 > 0) {
         this.intValue2--;
      } else {
         switch (this.baseFinderState) {
            case CHECK_SUPPLIES:
               int intValue = this.compute();
               if (intValue == -1) {
                  return;
               }

               this.blockPos = CLIENT.player.getBlockPos();
               this.intValue3 = ThreadLocalRandom.current().nextInt(20, 30);

               try {
                  CLIENT.player.networkHandler.sendChatMessage("#tunnel");
               } catch (Exception exception3) {
                  exception3.printStackTrace();
               }

               this.baseFinderState = BaseFinder.BaseFinderState.TUNNELING;
               break;
            case TUNNELING:
               if (this.blockPos != null && CLIENT.player.getBlockPos().getManhattanDistance(this.blockPos) >= this.intValue3) {
                  CLIENT.player.networkHandler.sendChatMessage("#stop");
                  this.baseFinderState = BaseFinder.BaseFinderState.STOPPING;
                  this.intValue2 = 10;
               }
               break;
            case STOPPING:
               this.intValue4 = CLIENT.player.getInventory().getSelectedSlot();
               int intValue2 = this.compute();
               if (intValue2 != -1) {
                  CLIENT.player.getInventory().setSelectedSlot(intValue2);
                  this.baseFinderState = BaseFinder.BaseFinderState.PLACING;
                  this.intValue2 = 5;
               } else {
                  this.invoke10("§cРуда закончилась! Жду пополнения...");
                  this.baseFinderState = BaseFinder.BaseFinderState.CHECK_SUPPLIES;
               }
               break;
            case PLACING:
               Direction direction = CLIENT.player.getHorizontalFacing();
               BlockPos blockPos2 = CLIENT.player.getBlockPos();
               BlockPos blockPos3 = blockPos2.offset(direction.rotateYCounterclockwise());
               BlockPos blockPos4 = blockPos2.offset(direction.rotateYClockwise());
               BlockPos blockPos5 = blockPos2.offset(direction);
               BlockPos blockPos6 = blockPos3.up();
               BlockPos blockPos7 = blockPos4.up();
               BlockPos blockPos8 = blockPos5.up();
               boolean flag = false;
               if (this.check(blockPos6)) {
                  this.blockPos2 = blockPos6;
                  flag = true;
               } else if (this.check(blockPos7)) {
                  this.blockPos2 = blockPos7;
                  flag = true;
               } else if (this.check(blockPos3)) {
                  this.blockPos2 = blockPos3;
                  flag = true;
               } else if (this.check(blockPos4)) {
                  this.blockPos2 = blockPos4;
                  flag = true;
               } else if (this.check(blockPos8)) {
                  this.blockPos2 = blockPos8;
                  flag = true;
               } else if (this.check(blockPos5)) {
                  this.blockPos2 = blockPos5;
                  flag = true;
               }

               if (flag) {
                  this.baseFinderState = BaseFinder.BaseFinderState.WAITING_CHAT;
                  this.intValue2 = 10;
               } else {
                  this.invoke10("§7Некуда поставить блок. Пропуск.");
                  if (this.intValue4 != -1) {
                     CLIENT.player.getInventory().setSelectedSlot(this.intValue4);
                  }

                  this.baseFinderState = BaseFinder.BaseFinderState.RESUMING;
                  this.intValue2 = 5;
               }
               break;
            case WAITING_CHAT:
               this.baseFinderState = BaseFinder.BaseFinderState.BREAKING;
               break;
            case BREAKING:
               if (this.blockPos2 != null) {
                  if (this.intValue4 != -1 && CLIENT.player.getInventory().getSelectedSlot() != this.intValue4) {
                     CLIENT.player.getInventory().setSelectedSlot(this.intValue4);
                  }

                  if (!CLIENT.world.getBlockState(this.blockPos2).isAir()) {
                     this.invoke2(Vec3d.ofCenter(this.blockPos2));
                     CLIENT.interactionManager.updateBlockBreakingProgress(this.blockPos2, Direction.UP);
                     CLIENT.player.swingHand(Hand.MAIN_HAND);
                     return;
                  }
               }

               this.baseFinderState = BaseFinder.BaseFinderState.RESUMING;
               this.intValue2 = 5;
               break;
            case RESUMING:
               this.blockPos = CLIENT.player.getBlockPos();
               this.intValue3 = ThreadLocalRandom.current().nextInt(20, 30);
               CLIENT.player.networkHandler.sendChatMessage("#tunnel");
               this.baseFinderState = BaseFinder.BaseFinderState.TUNNELING;
         }
      }
   }

   private void invoke2(Vec3d vec3d) {
      double doubleValue = vec3d.x - CLIENT.player.getX();
      double doubleValue2 = vec3d.y - CLIENT.player.getEyeY();
      double doubleValue3 = vec3d.z - CLIENT.player.getZ();
      double doubleValue4 = Math.sqrt(doubleValue * doubleValue + doubleValue3 * doubleValue3);
      float floatValue = (float)(Math.toDegrees(Math.atan2(doubleValue3, doubleValue)) - 90.0);
      float floatValue2 = (float)Math.toDegrees(-Math.atan2(doubleValue2, doubleValue4));
      CLIENT.player.setYaw(floatValue);
      CLIENT.player.setPitch(floatValue2);
   }

   private boolean check(BlockPos blockPos) {
      if (!CLIENT.world.getBlockState(blockPos).isAir()) {
         return false;
      } else {
         for (Direction direction2 : Direction.values()) {
            BlockPos blockPos9 = blockPos.offset(direction2);
            if (!CLIENT.world.getBlockState(blockPos9).isAir()) {
               Direction direction3 = direction2.getOpposite();
               Vec3d vec3d2 = new Vec3d(
                  blockPos9.getX() + 0.5 + direction3.getOffsetX() * 0.5, blockPos9.getY() + 0.5 + direction3.getOffsetY() * 0.5, blockPos9.getZ() + 0.5 + direction3.getOffsetZ() * 0.5
               );
               BlockHitResult blockHitResult = new BlockHitResult(vec3d2, direction3, blockPos9, false);
               this.invoke2(vec3d2);
               CLIENT.interactionManager.interactBlock(CLIENT.player, Hand.MAIN_HAND, blockHitResult);
               CLIENT.player.swingHand(Hand.MAIN_HAND);
               return true;
            }
         }

         return false;
      }
   }

   private int compute() {
      for (int intValue3 = 0; intValue3 < 9; intValue3++) {
         if (CLIENT.player.getInventory().getStack(intValue3).getItem() == Items.EMERALD_ORE
            || CLIENT.player.getInventory().getStack(intValue3).getItem() == Items.DEEPSLATE_EMERALD_ORE) {
            return intValue3;
         }
      }

      return -1;
   }

   @EventHandler
   public void onPacket(PacketEvent packetEvent) {
      if (CLIENT.player != null && this.rezhimRaboty.is("Поиск приватом")) {
         if (packetEvent.getPacket() instanceof GameMessageS2CPacket gameMessageS2CPacket) {
            String text = gameMessageS2CPacket.content().getString();
            if (this.baseFinderState == BaseFinder.BaseFinderState.WAITING_CHAT) {
               if (!text.contains("Ваш регион пересекается") && (!text.contains("[✠]") || !text.contains("пересекается"))) {
                  if (text.contains("Регион успешно создан") || text.contains("[✠]") && text.contains("успешно")) {
                     this.baseFinderState = BaseFinder.BaseFinderState.BREAKING;
                     this.intValue2 = 2;
                  }
               } else {
                  this.invoke10("§d!!! НАЙДЕНО ПЕРЕСЕЧЕНИЕ РЕГИОНОВ !!!");
                  if (ClientUtil.telegramNotifications.isEnabled()) {
                     this.invoke7("ПРИВАТ (Emerald Check)", CLIENT.player.getBlockX(), CLIENT.player.getBlockY(), CLIENT.player.getBlockZ());
                  }

                  CLIENT.player.networkHandler.sendChatMessage("#stop");
                  this.toggle();
               }
            }
         }
      }
   }

   private void invoke3() {
      for (PlayerEntity playerEntity : CLIENT.world.getPlayers()) {
         if (playerEntity != CLIENT.player && !FriendCommand.check(playerEntity.getName().getString())) {
            String text2 = playerEntity.getName().getString();
            int intValue4 = playerEntity.getBlockX();
            int intValue5 = playerEntity.getBlockY();
            int intValue6 = playerEntity.getBlockZ();
            ChatUtil.sendClientMessage("§4[BaseFinder] §cОБНАРУЖЕН ИГРОК: §f" + text2);
            if (ClientUtil.telegramNotifications.isEnabled()) {
               this.invoke8(text2, intValue4, intValue5, intValue6);
            }

            if (this.avtoTunnel.isEnabled()) {
               CLIENT.player.networkHandler.sendChatMessage("#stop");
            }

            this.toggle();
            return;
         }
      }
   }

   private void invoke4() {
      ChunkPos chunkPos = CLIENT.player.getChunkPos();
      int intValue7 = (int)this.radiusChankov.getValue();

      for (int intValue8 = chunkPos.x - intValue7; intValue8 <= chunkPos.x + intValue7; intValue8++) {
         for (int intValue9 = chunkPos.z - intValue7; intValue9 <= chunkPos.z + intValue7; intValue9++) {
            WorldChunk worldChunk = CLIENT.world.getChunk(intValue8, intValue9);
            if (worldChunk != null) {
               for (BlockEntity blockEntity2 : worldChunk.getBlockEntities().values()) {
                  BlockEntityType blockEntityType = blockEntity2.getType();
                  if (VALUES_BY_KEY.containsKey(blockEntityType) && this.check2(blockEntity2)) {
                     BlockPos blockPos10 = blockEntity2.getPos();
                     if (!this.values.contains(blockPos10) && (!this.proverkiNaSvet.isEnabled() || this.check3(blockPos10))) {
                        this.values.add(blockPos10);
                        this.valuesByKey.put(blockPos10, blockEntityType);
                        String text3 = this.resolve(blockEntity2);
                        this.invoke6(text3, blockPos10.getX(), blockPos10.getY(), blockPos10.getZ());
                     }
                  }
               }
            }
         }
      }
   }

   private void invoke5() {
      for (Entity entity : CLIENT.world.getEntities()) {
         if ((entity instanceof ChestMinecartEntity || entity instanceof HopperMinecartEntity)
            && !this.values2.contains(entity.getId())
            && !(entity.distanceTo(CLIENT.player) > this.radiusChankov.getValue() * 16.0F)) {
            this.values2.add(entity.getId());
            String text4 = entity instanceof ChestMinecartEntity ? "Грузовая вагонетка" : "Вагонетка с воронкой";
            BlockPos blockPos11 = entity.getBlockPos();
            this.invoke6(text4, blockPos11.getX(), blockPos11.getY(), blockPos11.getZ());
         }
      }
   }

   private void invoke6(String string, int i, int j, int k) {
      ChatUtil.sendClientMessage(String.format("§5[BaseFinder] §aНайден §f%s §aна XYZ: §f%d %d %d", string, i, j, k));
      if (ClientUtil.telegramNotifications.isEnabled()) {
         this.invoke7(string, i, j, k);
      }

      if (!this.rezhimRaboty.is("Поиск приватом") && this.avtoTunnel.isEnabled() && this.kopatKNahodke.isEnabled() && !this.flag) {
         this.flag = true;
         ChatUtil.sendClientMessage("§5[BaseFinder] §aНайдена цель! Перенаправляю Baritone...");

         try {
            if (CLIENT.player != null) {
               CLIENT.player.networkHandler.sendChatMessage("#tunnel " + i + " " + j + " " + k);
            }
         } catch (Exception exception4) {
            exception4.printStackTrace();
         }
      }
   }

   private void invoke7(String string, int i, int j, int k) {
      if (TelegramApi.check()) {
         ClientUtil clientUtil = (ClientUtil)WildClient.INSTANCE.moduleManager.findModule(ClientUtil.class);
         if (clientUtil == null || ClientUtil.telegramNotifications.isEnabled()) {
            Thread thread = new Thread(() -> {
               try {
                  String text5 = CLIENT.getCurrentServerEntry() != null ? CLIENT.getCurrentServerEntry().address : "Singleplayer";
                  String var5x = String.format("База найдена!\n\nТип: %s\nКоординаты: %d %d %d\nСервер: %s\n", string, i, j, k, text5);
                  TelegramApi.invoke2(var5x);
               } catch (Exception var6x) {
                  ChatUtil.sendClientMessage("§cОшибка отправки в Telegram: " + var6x.getMessage());
               }
            }, "Wild-BaseFinder-Telegram");
            thread.setDaemon(true);
            thread.start();
         }
      }
   }

   private void invoke8(String string, int i, int j, int k) {
      if (TelegramApi.check()) {
         Thread thread2 = new Thread(() -> {
            try {
               String text6 = CLIENT.getCurrentServerEntry() != null ? CLIENT.getCurrentServerEntry().address : "Singleplayer";
               String var5x = String.format("Был обнаружен игрокНик: %s\nКоординаты: %d %d %d\nСервер: %s\n", string, i, j, k, text6);
               TelegramApi.invoke2(var5x);
            } catch (Exception exception5) {
               exception5.printStackTrace();
            }
         }, "Wild-BaseFinder-PlayerAlert");
         thread2.setDaemon(true);
         thread2.start();
      }
   }

   private boolean check2(BlockEntity blockEntity) {
      if (blockEntity instanceof ChestBlockEntity && !this.bloki.isEnabled("Сундуки")) {
         return false;
      } else if (blockEntity instanceof EnderChestBlockEntity && !this.bloki.isEnabled("Эндер сундук")) {
         return false;
      } else if (blockEntity instanceof BarrelBlockEntity && !this.bloki.isEnabled("Бочки")) {
         return false;
      } else {
         return blockEntity instanceof FurnaceBlockEntity && !this.bloki.isEnabled("Печка")
            ? false
            : !(blockEntity instanceof ShulkerBoxBlockEntity) || this.bloki.isEnabled("Шалкера");
      }
   }

   private boolean check3(BlockPos blockPos) {
      return CLIENT.world == null ? false : CLIENT.world.getLightLevel(LightType.BLOCK, blockPos) >= 8;
   }

   private boolean check4() {
      Box box = new Box(CLIENT.player.getBlockPos()).expand(8.0);

      for (Entity entity2 : CLIENT.world.getOtherEntities(CLIENT.player, box)) {
         if (entity2 instanceof HostileEntity && entity2.isAlive()) {
            return true;
         }
      }

      return false;
   }

   private String resolve(BlockEntity blockEntity) {
      if (blockEntity instanceof ChestBlockEntity) {
         return "Сундук";
      } else if (blockEntity instanceof EnderChestBlockEntity) {
         return "Эндер сундук";
      } else if (blockEntity instanceof BarrelBlockEntity) {
         return "Бочка";
      } else if (blockEntity instanceof FurnaceBlockEntity) {
         return "Печка";
      } else {
         return blockEntity instanceof ShulkerBoxBlockEntity ? "Шалкер" : "Неизвестный блок";
      }
   }

   @EventHandler
   public void onRender3D(Render3DEvent render3DEvent) {
      if (CLIENT.world != null && CLIENT.player != null && this.renderitNahodki.isEnabled()) {
         Immediate immediate = WorldRenderBuffer.getIMMEDIATE();

         try {
            Vec3d vec3d3 = CLIENT.gameRenderer.getCamera().getPos();
            Matrix4f matrix4f2 = render3DEvent.getMatrixStack().peek().getPositionMatrix();
            VertexConsumer vertexConsumer2 = immediate.getBuffer(RENDER_LAYER);

            for (BlockPos blockPos12 : this.values) {
               BlockEntityType blockEntityType2 = this.valuesByKey.get(blockPos12);
               if (blockEntityType2 != null && VALUES_BY_KEY.containsKey(blockEntityType2)) {
                  this.invoke9(vertexConsumer2, matrix4f2, vec3d3, blockPos12, VALUES_BY_KEY.get(blockEntityType2));
               }
            }

            if (this.iskatVagonetki.isEnabled()) {
               for (Entity entity3 : CLIENT.world.getEntities()) {
                  if (this.values2.contains(entity3.getId())) {
                     int intValue10 = -1;
                     if (entity3 instanceof ChestMinecartEntity) {
                        intValue10 = VALUES_BY_KEY.get(ChestMinecartEntity.class);
                     } else if (entity3 instanceof HopperMinecartEntity) {
                        intValue10 = VALUES_BY_KEY.get(HopperMinecartEntity.class);
                     }

                     if (intValue10 != -1) {
                        EspBoxVertexWriter.invoke(
                           vertexConsumer2,
                           matrix4f2,
                           (float)(entity3.getX() - 0.5 - vec3d3.x),
                           (float)(entity3.getY() - vec3d3.y),
                           (float)(entity3.getZ() - 0.5 - vec3d3.z),
                           (float)(entity3.getX() + 0.5 - vec3d3.x),
                           (float)(entity3.getY() + 0.5 - vec3d3.y),
                           (float)(entity3.getZ() + 0.5 - vec3d3.z),
                           intValue10
                        );
                     }
                  }
               }
            }
         } finally {
            WorldRenderBuffer.invoke();
         }
      }
   }

   private void invoke9(VertexConsumer vertexConsumer, Matrix4f matrix4f, Vec3d vec3d, BlockPos blockPos, int i) {
      float floatValue3 = (float)(blockPos.getX() - vec3d.x);
      float floatValue4 = (float)(blockPos.getY() - vec3d.y);
      float floatValue5 = (float)(blockPos.getZ() - vec3d.z);
      float floatValue6 = (float)(blockPos.getX() + 1 - vec3d.x);
      float floatValue7 = (float)(blockPos.getY() + 1 - vec3d.y);
      float floatValue8 = (float)(blockPos.getZ() + 1 - vec3d.z);
      EspBoxVertexWriter.invoke(vertexConsumer, matrix4f, floatValue3, floatValue4, floatValue5, floatValue6, floatValue7, floatValue8, i);
   }

   private void invoke10(String string) {
      ChatUtil.sendClientMessage("§5[BaseFinder] " + string);
   }

   static enum BaseFinderState {
      CHECK_SUPPLIES,
      TUNNELING,
      STOPPING,
      PLACING,
      WAITING_CHAT,
      BREAKING,
      RESUMING;
   }
}
