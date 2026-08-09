package ru.metaculture.protection;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModels;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.SkinTextures.Model;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "DeadEffect",
   description = "Плавный выход души из модели игрока при потере тотема или смерти.",
   category = Category.Visuals
)
public final class DeadEffect extends Module {
   private static final String TOTEM = "Тотем";
   private static final String SMERT = "Смерть";
   private static final String SEBYA = "Себя";
   private static final String IGROKI = "Игроки";
   private static final long TIMESTAMP = 180000000L;
   private static final long TIMESTAMP_2 = 700000000L;
   private static final long TIMESTAMP_3 = 5000000000L;
   private static final float FLOAT_VALUE = (float) (Math.PI * 2);
   private static final int INT_VALUE = 96;
   private static final int INT_VALUE_2 = 36;
   private final GroupSetting sobytiya = new GroupSetting("События", new BooleanSetting("Тотем", true), new BooleanSetting("Смерть", true));
   private final GroupSetting tseli = new GroupSetting("Цели", new BooleanSetting("Себя", true), new BooleanSetting("Игроки", true));
   private final NumberSetting dlitelnost = new NumberSetting("Длительность", 1.65F, 0.55F, 4.0F, 0.05F, false);
   private final NumberSetting podyom = new NumberSetting("Подъём", 1.85F, 0.6F, 4.0F, 0.05F, false);
   private final NumberSetting prozrachnost = new NumberSetting("Прозрачность", 0.74F, 0.15F, 1.0F, 0.01F, true);
   private final NumberSetting svechenie = new NumberSetting("Свечение", 1.15F, 0.0F, 2.4F, 0.05F, false);
   private final NumberSetting chastitsy = new NumberSetting("Частицы", 34.0F, 0.0F, 90.0F, 1.0F, false);
   private final ColorSetting tsvetTotema = new ColorSetting("Цвет тотема", 31.0F, 0.82F, 1.0F);
   private final ColorSetting tsvetSmerti = new ColorSetting("Цвет смерти", 74.0F, 0.68F, 1.0F);
   private final Map<Integer, DeadEffect.DeadEffectState2> valuesByKey = new ConcurrentHashMap<>();
   private final Map<Integer, DeadEffect.DeadEffectData2> valuesByKey2 = new ConcurrentHashMap<>();
   private final Set<Integer> values = ConcurrentHashMap.newKeySet();
   private LoadedEntityModels loadedEntityModels;
   private PlayerEntityModel playerEntityModel;
   private PlayerEntityModel playerEntityModel2;
   private DeadEffect.DeadEffectData2 deadEffectData2;
   private DeadEffect.DeadEffectData2 deadEffectData22;

   public DeadEffect() {
      this.addSettings(
         new Setting[]{
            this.sobytiya,
            this.tseli,
            this.dlitelnost,
            this.podyom,
            this.prozrachnost,
            this.svechenie,
            this.chastitsy,
            this.tsvetTotema,
            this.tsvetSmerti
         }
      );
   }

   @Override
   public void onEnable() {
      this.valuesByKey.clear();
      this.valuesByKey2.clear();
      this.values.clear();
      super.onEnable();
   }

   @Override
   public void onDisable() {
      this.valuesByKey.clear();
      this.valuesByKey2.clear();
      this.values.clear();
      super.onDisable();
   }

   @EventHandler
   public void onWorldJoin(WorldJoinEvent worldJoinEvent) {
      this.valuesByKey.clear();
      this.valuesByKey2.clear();
      this.values.clear();
   }

   @EventHandler
   public void onPacket(PacketEvent packetEvent) {
      if (CLIENT.world != null && packetEvent != null && !packetEvent.check()) {
         if (packetEvent.getPacket() instanceof EntityStatusS2CPacket entityStatusS2CPacket) {
            if (entityStatusS2CPacket.getEntity(CLIENT.world) instanceof PlayerEntity playerEntity2 && this.check2(playerEntity2)) {
               byte byteValue = entityStatusS2CPacket.getStatus();
               if (byteValue == 35 && this.sobytiya.isEnabled("Тотем")) {
                  this.invoke4(playerEntity2, DeadEffect.DeadEffectState.TOTEM);
               } else if (byteValue == 3 && this.sobytiya.isEnabled("Смерть") && check3(playerEntity2)) {
                  this.invoke4(playerEntity2, DeadEffect.DeadEffectState.DEATH);
               }
            }
         }
      }
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.world != null && CLIENT.player != null) {
         long longValue = System.nanoTime();
         this.invoke6(longValue);

         for (PlayerEntity playerEntity3 : CLIENT.world.getPlayers()) {
            if (this.check2(playerEntity3)) {
               int intValue = playerEntity3.getId();
               if (check3(playerEntity3)) {
                  if (this.values.add(intValue) && this.sobytiya.isEnabled("Смерть")) {
                     this.invoke4(playerEntity3, DeadEffect.DeadEffectState.DEATH);
                  }
               } else {
                  this.values.remove(intValue);
               }
            }
         }
      } else {
         this.valuesByKey.clear();
         this.values.clear();
      }
   }

   public static void capturePlayerModelState(
      PlayerEntityRenderState playerEntityRenderState,
      PlayerEntityModel playerEntityModel,
      MatrixStack matrixStack,
      VertexConsumerProvider vertexConsumerProvider,
      int i,
      int j
   ) {
      DeadEffect deadEffect = resolve3();
      if (deadEffect != null) {
         deadEffect.invoke(playerEntityRenderState, playerEntityModel);
      }
   }

   private void invoke(PlayerEntityRenderState playerEntityRenderState, PlayerEntityModel playerEntityModel) {
      if (this.enabled && playerEntityRenderState != null && playerEntityModel != null && playerEntityRenderState.skinTextures != null) {
         if (!playerEntityRenderState.spectator && !playerEntityRenderState.invisible && !playerEntityRenderState.invisibleToPlayer) {
            DeadEffect.DeadEffectData2 deadEffectData2 = DeadEffect.DeadEffectData2.capture(playerEntityModel, System.nanoTime());
            this.valuesByKey2.put(playerEntityRenderState.id, deadEffectData2);
            DeadEffect.DeadEffectState2 deadEffectState2 = this.valuesByKey.get(playerEntityRenderState.id);
            if (deadEffectState2 != null) {
               if (deadEffectState2.deadEffectData2 == null) {
                  deadEffectState2.deadEffectData2 = deadEffectData2;
               }

               if (deadEffectState2.deadEffectData3 == null) {
                  deadEffectState2.deadEffectData3 = DeadEffect.DeadEffectData3.fromState(playerEntityRenderState);
               }

               if (deadEffectState2.identifier == null) {
                  deadEffectState2.identifier = playerEntityRenderState.skinTextures.texture();
               }

               deadEffectState2.flag = check5(playerEntityRenderState);
            }
         }
      }
   }

   @EventHandler
   public void onRender3D(Render3DEvent render3DEvent) {
      if (this.enabled && CLIENT.world != null && CLIENT.player != null && render3DEvent != null && !this.valuesByKey.isEmpty()) {
         long longValue2 = System.nanoTime();
         this.invoke6(longValue2);
         if (!this.valuesByKey.isEmpty()) {
            Immediate immediate = WorldRenderBuffer.getIMMEDIATE();

            try {
               for (Entry entry2 : this.valuesByKey.entrySet()) {
                  int intValue2 = (Integer)entry2.getKey();
                  if (intValue2 != CLIENT.player.getId() || CLIENT.options == null || CLIENT.options.getPerspective() != Perspective.FIRST_PERSON) {
                     DeadEffect.DeadEffectState2 deadEffectState22 = (DeadEffect.DeadEffectState2)entry2.getValue();
                     if (!this.check(deadEffectState22, render3DEvent.getMatrixStack(), immediate, longValue2)) {
                        this.valuesByKey.remove(intValue2, deadEffectState22);
                     }
                  }
               }
            } finally {
               WorldRenderBuffer.invoke();
            }
         }
      }
   }

   private boolean check(DeadEffect.DeadEffectState2 deadEffectState23, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, long l) {
      if (deadEffectState23 != null && matrixStack != null && vertexConsumerProvider != null && deadEffectState23.deadEffectData3 != null) {
         float floatValue = (float)(l - deadEffectState23.timestamp) / 1.0E9F;
         float floatValue2 = this.measure2(deadEffectState23.deadEffectState);
         float floatValue3 = this.measure(deadEffectState23.deadEffectState);
         if (floatValue >= floatValue3) {
            return false;
         } else {
            float floatValue4 = measure5(floatValue / floatValue3);
            float floatValue5 = measure5(floatValue / floatValue2);
            float floatValue6 = measure4(floatValue2 * 0.58F, floatValue3, floatValue);
            float floatValue7 = measure4(0.0F, 0.11F, floatValue4);
            float floatValue8 = 1.0F - floatValue6;
            float floatValue9 = measure5(floatValue7 * floatValue8 * this.prozrachnost.getValue());
            if (floatValue9 <= 0.002F) {
               return true;
            } else {
               float floatValue10 = measure3(floatValue5);
               float floatValue11 = this.podyom.getValue() * deadEffectState23.deadEffectState.floatValue3 * (floatValue10 + floatValue6 * 0.075F);
               float floatValue12 = deadEffectState23.floatValue + floatValue * 2.15F;
               float floatValue13 = (float)Math.sin(floatValue12 * 1.7F) * (0.025F + floatValue6 * 0.045F) * floatValue10;
               float floatValue14 = (float)Math.cos(floatValue12 * 1.3F) * (0.025F + floatValue6 * 0.045F) * floatValue10;
               float floatValue15 = 1.0F + (float)Math.sin(floatValue4 * Math.PI * 3.0) * 0.022F * (1.0F - floatValue4);
               float floatValue16 = (floatValue15 + deadEffectState23.deadEffectState.floatValue4 * floatValue10) * (1.0F + floatValue6 * 0.055F);
               int intValue3 = this.compute(deadEffectState23.deadEffectState);
               this.invoke7(matrixStack, vertexConsumerProvider, deadEffectState23, floatValue4, floatValue9, floatValue11, floatValue12, floatValue6);
               Identifier identifier2 = deadEffectState23.identifier;
               if (identifier2 == null) {
                  return true;
               } else {
                  PlayerEntityModel playerEntityModel2 = this.resolve(deadEffectState23.flag);
                  if (playerEntityModel2 == null) {
                     return true;
                  } else {
                     RenderLayer renderLayer2 = RenderLayer.getEntityTranslucentEmissive(identifier2);
                     int intValue4 = compute2(
                        compute3(16777215, intValue3, deadEffectState23.deadEffectState.floatValue9),
                        floatValue9 * deadEffectState23.deadEffectState.floatValue8 * (1.0F - floatValue6 * 0.22F)
                     );
                     DeadEffect.DeadEffectData2 deadEffectData22 = deadEffectState23.deadEffectData2 != null ? deadEffectState23.deadEffectData2 : this.resolve2(deadEffectState23.flag);
                     if (deadEffectData22 != null) {
                        deadEffectData22.apply(playerEntityModel2);
                     }

                     this.invoke2(
                        playerEntityModel2,
                        matrixStack,
                        vertexConsumerProvider,
                        renderLayer2,
                        deadEffectState23.deadEffectData3,
                        15728880,
                        OverlayTexture.DEFAULT_UV,
                        floatValue13,
                        floatValue11,
                        floatValue14,
                        floatValue16,
                        intValue4
                     );
                     return true;
                  }
               }
            }
         }
      } else {
         return false;
      }
   }

   private void invoke2(
      PlayerEntityModel playerEntityModel,
      MatrixStack matrixStack,
      VertexConsumerProvider vertexConsumerProvider,
      RenderLayer renderLayer,
      DeadEffect.DeadEffectData3 deadEffectData3,
      int i,
      int j,
      float f,
      float g,
      float h,
      float k,
      int l
   ) {
      if ((l >>> 24 & 0xFF) != 0) {
         matrixStack.push();
         this.invoke3(matrixStack, deadEffectData3, f, g, h, k);
         VertexConsumer vertexConsumer2 = vertexConsumerProvider.getBuffer(renderLayer);
         playerEntityModel.render(matrixStack, vertexConsumer2, i, j, l);
         matrixStack.pop();
         if (vertexConsumerProvider instanceof Immediate immediate2) {
            immediate2.draw(renderLayer);
         }
      }
   }

   private void invoke3(MatrixStack matrixStack, DeadEffect.DeadEffectData3 deadEffectData32, float f, float g, float h, float i) {
      Camera camera = CLIENT.gameRenderer.getCamera();
      Vec3d vec3d = camera == null ? Vec3d.ZERO : camera.getPos();
      matrixStack.translate(deadEffectData32.x - vec3d.x + f, deadEffectData32.y - vec3d.y + g, deadEffectData32.z - vec3d.z + h);
      matrixStack.scale(deadEffectData32.baseScale, deadEffectData32.baseScale, deadEffectData32.baseScale);
      matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F - deadEffectData32.bodyYaw));
      matrixStack.scale(-i, -i, i);
      matrixStack.translate(0.0F, -1.501F, 0.0F);
   }

   private PlayerEntityModel resolve(boolean bl) {
      if (this.loadedEntityModels == null) {
         this.loadedEntityModels = new LoadedEntityModels(EntityModels.getModels());
      }

      if (bl) {
         if (this.playerEntityModel2 == null) {
            this.playerEntityModel2 = new PlayerEntityModel(this.loadedEntityModels.getModelPart(EntityModelLayers.PLAYER_SLIM), true);
            this.deadEffectData22 = DeadEffect.DeadEffectData2.capture(this.playerEntityModel2, 0L);
         }

         return this.playerEntityModel2;
      } else {
         if (this.playerEntityModel == null) {
            this.playerEntityModel = new PlayerEntityModel(this.loadedEntityModels.getModelPart(EntityModelLayers.PLAYER), false);
            this.deadEffectData2 = DeadEffect.DeadEffectData2.capture(this.playerEntityModel, 0L);
         }

         return this.playerEntityModel;
      }
   }

   private DeadEffect.DeadEffectData2 resolve2(boolean bl) {
      this.resolve(bl);
      return bl ? this.deadEffectData22 : this.deadEffectData2;
   }

   private void invoke4(PlayerEntity playerEntity, DeadEffect.DeadEffectState deadEffectState) {
      if (playerEntity != null && this.check2(playerEntity)) {
         int intValue5 = playerEntity.getId();
         long longValue3 = System.nanoTime();
         DeadEffect.DeadEffectState2 deadEffectState24 = this.valuesByKey.get(intValue5);
         if (deadEffectState24 == null || deadEffectState24.deadEffectState != deadEffectState || longValue3 - deadEffectState24.timestamp >= 180000000L) {
            DeadEffect.DeadEffectData2 deadEffectData23 = this.valuesByKey2.get(intValue5);
            if (deadEffectData23 != null && longValue3 - deadEffectData23.capturedNanos > 700000000L) {
               deadEffectData23 = null;
            }

            this.valuesByKey
               .put(
                  intValue5,
                  new DeadEffect.DeadEffectState2(
                     deadEffectState,
                     longValue3,
                     (float)(ThreadLocalRandom.current().nextDouble() * Math.PI * 2.0),
                     deadEffectData23,
                     DeadEffect.DeadEffectData3.fromPlayer(playerEntity),
                     resolve4(playerEntity),
                     check4(playerEntity)
                  )
               );
            if (this.chastitsy.getValue() > 0.5F) {
               double doubleValue = playerEntity.getX();
               double doubleValue2 = playerEntity.getY();
               double doubleValue3 = playerEntity.getZ();
               double doubleValue4 = Math.max(1.0, (double)playerEntity.getHeight());
               CLIENT.execute(() -> this.invoke5(doubleValue, doubleValue2, doubleValue3, doubleValue4, deadEffectState));
            }
         }
      }
   }

   private void invoke5(double d, double e, double f, double g, DeadEffect.DeadEffectState deadEffectState3) {
      if (CLIENT.particleManager != null) {
         ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
         int intValue6 = Math.max(0, Math.round(this.chastitsy.getValue()));

         for (int intValue7 = 0; intValue7 < intValue6; intValue7++) {
            double doubleValue5 = threadLocalRandom.nextDouble(Math.PI * 2);
            double doubleValue6 = threadLocalRandom.nextDouble(0.08, 0.56);
            double doubleValue7 = d + Math.cos(doubleValue5) * doubleValue6;
            double doubleValue8 = e + threadLocalRandom.nextDouble(0.08, g + 0.42);
            double doubleValue9 = f + Math.sin(doubleValue5) * doubleValue6;
            double doubleValue10 = threadLocalRandom.nextDouble(0.018, 0.07);
            double doubleValue11 = Math.cos(doubleValue5) * doubleValue10;
            double doubleValue12 = threadLocalRandom.nextDouble(0.045, 0.145) * deadEffectState3.floatValue13;
            double doubleValue13 = Math.sin(doubleValue5) * doubleValue10;
            CLIENT.particleManager.addParticle(deadEffectState3.resolve(intValue7), doubleValue7, doubleValue8, doubleValue9, doubleValue11, doubleValue12, doubleValue13);
         }
      }
   }

   private void invoke6(long l) {
      Iterator iterator = this.valuesByKey.entrySet().iterator();

      while (iterator.hasNext()) {
         Entry entry3 = (Entry)iterator.next();
         DeadEffect.DeadEffectState2 deadEffectState25 = (DeadEffect.DeadEffectState2)entry3.getValue();
         float floatValue17 = this.measure(deadEffectState25.deadEffectState) + 0.25F;
         if ((float)(l - deadEffectState25.timestamp) > floatValue17 * 1.0E9F) {
            iterator.remove();
         }
      }

      this.valuesByKey2.entrySet().removeIf(entry -> l - entry.getValue().capturedNanos > 5000000000L);
   }

   private void invoke7(
      MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, DeadEffect.DeadEffectState2 deadEffectState26, float f, float g, float h, float i, float j
   ) {
      if (matrixStack != null && deadEffectState26.deadEffectData3 != null && !(g <= 0.01F) && CLIENT.gameRenderer != null) {
         Camera camera2 = CLIENT.gameRenderer.getCamera();
         if (camera2 != null) {
            RenderLayer renderLayer3 = WorldRenderPipelines.getRENDER_LAYER_3();
            VertexConsumer vertexConsumer3 = vertexConsumerProvider.getBuffer(renderLayer3);
            Matrix4f matrix4f2 = matrixStack.peek().getPositionMatrix();
            Vec3d vec3d2 = camera2.getPos();
            DeadEffect.DeadEffectData3 deadEffectData33 = deadEffectState26.deadEffectData3;
            float floatValue18 = (float)(deadEffectData33.x - vec3d2.x);
            float floatValue19 = (float)(deadEffectData33.y - vec3d2.y);
            float floatValue20 = (float)(deadEffectData33.z - vec3d2.z);
            this.invoke8(vertexConsumer3, matrix4f2, deadEffectState26, floatValue18, floatValue19, floatValue20, f, g, h);
            this.invoke9(vertexConsumer3, matrix4f2, deadEffectState26, floatValue18, floatValue19, floatValue20, f, g, h, i);
            this.invoke10(vertexConsumer3, matrix4f2, deadEffectState26, floatValue18, floatValue19, floatValue20, f, g, h);
            this.invoke11(vertexConsumer3, matrix4f2, deadEffectState26, floatValue18, floatValue19, floatValue20, f, g, h, i, j);
            if (vertexConsumerProvider instanceof Immediate immediate3) {
               immediate3.draw(renderLayer3);
            }
         }
      }
   }

   private void invoke8(VertexConsumer vertexConsumer, Matrix4f matrix4f, DeadEffect.DeadEffectState2 deadEffectState27, float f, float g, float h, float i, float j, float k) {
      for (int intValue8 = 0; intValue8 < 3; intValue8++) {
         float floatValue21 = measure6(i * (1.22F + deadEffectState27.deadEffectState.floatValue12) + intValue8 * 0.31F);
         float floatValue22 = (1.0F - floatValue21) * measure4(0.0F, 0.18F, floatValue21);
         float floatValue23 = deadEffectState27.deadEffectState.floatValue10 + floatValue21 * deadEffectState27.deadEffectState.floatValue11;
         float floatValue24 = 0.02F + (1.0F - floatValue21) * 0.045F;
         float floatValue25 = g + 0.08F + k * (0.1F + intValue8 * 0.075F) + intValue8 * 0.16F;
         int intValue9 = compute2(
            compute3(this.compute(deadEffectState27.deadEffectState), deadEffectState27.deadEffectState.intValue, 0.35F + intValue8 * 0.18F), j * floatValue22 * 0.74F
         );
         this.invoke12(vertexConsumer, matrix4f, f, floatValue25, h, floatValue23, floatValue24, intValue9);
      }
   }

   private void invoke9(
      VertexConsumer vertexConsumer, Matrix4f matrix4f, DeadEffect.DeadEffectState2 deadEffectState28, float f, float g, float h, float i, float j, float k, float l
   ) {
      float floatValue26 = 1.38F + k * 0.72F;

      for (int intValue10 = 0; intValue10 < 2; intValue10++) {
         float floatValue27 = intValue10 == 0 ? 1.0F : -1.0F;
         int intValue11 = intValue10 == 0 ? deadEffectState28.deadEffectState.intValue3 : deadEffectState28.deadEffectState.intValue4;

         for (int intValue12 = 0; intValue12 < 36; intValue12++) {
            float floatValue28 = intValue12 / 36.0F;
            float floatValue29 = (intValue12 + 1) / 36.0F;
            float floatValue30 = l + floatValue27 * (floatValue28 * (float) (Math.PI * 2) * 1.72F + i * (float) (Math.PI * 2) * 1.15F);
            float floatValue31 = l + floatValue27 * (floatValue29 * (float) (Math.PI * 2) * 1.72F + i * (float) (Math.PI * 2) * 1.15F);
            float floatValue32 = 0.34F + 0.13F * (float)Math.sin(floatValue28 * Math.PI + i * 3.0F);
            float floatValue33 = 0.34F + 0.13F * (float)Math.sin(floatValue29 * Math.PI + i * 3.0F);
            float floatValue34 = 0.032F + 0.018F * (1.0F - i);
            float floatValue35 = g + 0.12F + floatValue28 * floatValue26;
            float floatValue36 = g + 0.12F + floatValue29 * floatValue26;
            float floatValue37 = (float)Math.sin(floatValue28 * Math.PI) * (1.0F - measure4(0.86F, 1.0F, i));
            int intValue13 = compute2(compute3(intValue11, deadEffectState28.deadEffectState.intValue2, floatValue28), j * floatValue37 * 0.54F);
            int intValue14 = compute2(compute3(intValue11, deadEffectState28.deadEffectState.intValue2, floatValue29), j * floatValue37 * 0.54F);
            this.invoke13(vertexConsumer, matrix4f, f, h, floatValue35, floatValue36, floatValue30, floatValue31, floatValue32, floatValue33, floatValue34, intValue13, intValue14);
         }
      }
   }

   private void invoke10(
      VertexConsumer vertexConsumer, Matrix4f matrix4f, DeadEffect.DeadEffectState2 deadEffectState29, float f, float g, float h, float i, float j, float k
   ) {
      float floatValue38 = g + 0.08F + k * 0.08F;
      float floatValue39 = g + 1.7F + k * 0.92F;
      float floatValue40 = 0.1F + 0.055F * (1.0F - i);
      int intValue15 = compute2(deadEffectState29.deadEffectState.intValue, j * 0.04F);
      int intValue16 = compute2(compute3(this.compute(deadEffectState29.deadEffectState), 16777215, 0.38F), j * 0.3F * (1.0F - i * 0.34F));
      this.invoke14(
         vertexConsumer,
         matrix4f,
         f - floatValue40,
         floatValue38,
         h,
         f + floatValue40,
         floatValue38,
         h,
         f + floatValue40 * 0.32F,
         floatValue39,
         h,
         f - floatValue40 * 0.32F,
         floatValue39,
         h,
         intValue15,
         intValue15,
         intValue16,
         intValue16
      );
      this.invoke14(
         vertexConsumer,
         matrix4f,
         f,
         floatValue38,
         h - floatValue40,
         f,
         floatValue38,
         h + floatValue40,
         f,
         floatValue39,
         h + floatValue40 * 0.32F,
         f,
         floatValue39,
         h - floatValue40 * 0.32F,
         intValue15,
         intValue15,
         intValue16,
         intValue16
      );
   }

   private void invoke11(
      VertexConsumer vertexConsumer, Matrix4f matrix4f, DeadEffect.DeadEffectState2 deadEffectState210, float f, float g, float h, float i, float j, float k, float l, float m
   ) {
      if (!(m <= 0.02F)) {
         byte byteValue2 = 18;

         for (int intValue17 = 0; intValue17 < byteValue2; intValue17++) {
            float floatValue41 = measure6(deadEffectState210.floatValue * 0.137F + intValue17 * 0.6180339F);
            float floatValue42 = measure6(i * (0.78F + floatValue41 * 0.22F) + floatValue41);
            float floatValue43 = l * 0.78F + intValue17 * 2.399963F + floatValue42 * (float) (Math.PI * 2) * 0.62F;
            float floatValue44 = 0.18F + floatValue42 * (0.38F + floatValue41 * 0.34F);
            float floatValue45 = f + (float)Math.cos(floatValue43) * floatValue44;
            float floatValue46 = h + (float)Math.sin(floatValue43) * floatValue44;
            float floatValue47 = g + 0.52F + k * (0.45F + floatValue41 * 0.28F) + floatValue42 * (0.92F + floatValue41 * 0.42F);
            float floatValue48 = (0.025F + floatValue41 * 0.035F) * (1.0F - floatValue42 * 0.42F);
            float floatValue49 = j * m * (1.0F - floatValue42) * (0.3F + floatValue41 * 0.28F) * this.svechenie.getValue();
            int intValue18 = compute2(compute3(deadEffectState210.deadEffectState.intValue, deadEffectState210.deadEffectState.intValue2, floatValue41), floatValue49);
            this.invoke14(
               vertexConsumer,
               matrix4f,
               floatValue45,
               floatValue47 + floatValue48,
               floatValue46,
               floatValue45 + floatValue48,
               floatValue47,
               floatValue46,
               floatValue45,
               floatValue47 - floatValue48,
               floatValue46,
               floatValue45 - floatValue48,
               floatValue47,
               floatValue46,
               intValue18,
               intValue18,
               intValue18,
               intValue18
            );
         }
      }
   }

   private void invoke12(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float g, float h, float i, float j, int k) {
      if ((k >>> 24 & 0xFF) != 0) {
         float floatValue50 = Math.max(0.02F, i - j);
         float floatValue51 = i + j;

         for (int intValue19 = 0; intValue19 < 96; intValue19++) {
            float floatValue52 = (float) (Math.PI * 2) * intValue19 / 96.0F;
            float floatValue53 = (float) (Math.PI * 2) * (intValue19 + 1) / 96.0F;
            float floatValue54 = (float)Math.cos(floatValue52);
            float floatValue55 = (float)Math.sin(floatValue52);
            float floatValue56 = (float)Math.cos(floatValue53);
            float floatValue57 = (float)Math.sin(floatValue53);
            this.invoke14(
               vertexConsumer,
               matrix4f,
               f + floatValue54 * floatValue50,
               g,
               h + floatValue55 * floatValue50,
               f + floatValue56 * floatValue50,
               g,
               h + floatValue57 * floatValue50,
               f + floatValue56 * floatValue51,
               g,
               h + floatValue57 * floatValue51,
               f + floatValue54 * floatValue51,
               g,
               h + floatValue55 * floatValue51,
               k,
               k,
               k,
               k
            );
         }
      }
   }

   private void invoke13(
      VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float g, float h, float i, float j, float k, float l, float m, float n, int o, int p
   ) {
      if ((o >>> 24 & 0xFF | p >>> 24 & 0xFF) != 0) {
         float floatValue58 = (float)Math.cos(j);
         float floatValue59 = (float)Math.sin(j);
         float floatValue60 = (float)Math.cos(k);
         float floatValue61 = (float)Math.sin(k);
         this.invoke14(
            vertexConsumer,
            matrix4f,
            f + floatValue58 * (l - n),
            h,
            g + floatValue59 * (l - n),
            f + floatValue58 * (l + n),
            h,
            g + floatValue59 * (l + n),
            f + floatValue60 * (m + n),
            i,
            g + floatValue61 * (m + n),
            f + floatValue60 * (m - n),
            i,
            g + floatValue61 * (m - n),
            o,
            o,
            p,
            p
         );
      }
   }

   private void invoke14(
      VertexConsumer vertexConsumer,
      Matrix4f matrix4f,
      float f,
      float g,
      float h,
      float i,
      float j,
      float k,
      float l,
      float m,
      float n,
      float o,
      float p,
      float q,
      int r,
      int s,
      int t,
      int u
   ) {
      this.invoke15(vertexConsumer, matrix4f, f, g, h, r);
      this.invoke15(vertexConsumer, matrix4f, i, j, k, s);
      this.invoke15(vertexConsumer, matrix4f, l, m, n, t);
      this.invoke15(vertexConsumer, matrix4f, o, p, q, u);
   }

   private void invoke15(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float g, float h, int i) {
      vertexConsumer.vertex(matrix4f, f, g, h).color(compute4(i), compute5(i), compute6(i), i >>> 24 & 0xFF);
   }

   private boolean check2(PlayerEntity playerEntity) {
      if (playerEntity == null) {
         return false;
      } else {
         return CLIENT.player != null && playerEntity.getId() == CLIENT.player.getId()
            ? this.tseli.isEnabled("Себя")
            : this.tseli.isEnabled("Игроки");
      }
   }

   private static boolean check3(PlayerEntity playerEntity) {
      return playerEntity == null || playerEntity.isRemoved() || !playerEntity.isAlive() || playerEntity.getHealth() <= 0.0F || playerEntity.deathTime > 0;
   }

   private float measure(DeadEffect.DeadEffectState deadEffectState4) {
      return this.measure2(deadEffectState4) + deadEffectState4.floatValue2;
   }

   private float measure2(DeadEffect.DeadEffectState deadEffectState5) {
      return Math.max(0.15F, this.dlitelnost.getValue() * deadEffectState5.floatValue);
   }

   private int compute(DeadEffect.DeadEffectState deadEffectState6) {
      return deadEffectState6 == DeadEffect.DeadEffectState.TOTEM ? this.tsvetTotema.compute() : this.tsvetSmerti.compute();
   }

   private static DeadEffect resolve3() {
      if (WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null) {
         DeadEffect deadEffect2 = WildClient.INSTANCE.moduleManager.getModule(DeadEffect.class);
         return deadEffect2 != null && deadEffect2.enabled ? deadEffect2 : null;
      } else {
         return null;
      }
   }

   private static Identifier resolve4(PlayerEntity playerEntity) {
      return playerEntity instanceof AbstractClientPlayerEntity abstractClientPlayerEntity ? abstractClientPlayerEntity.getSkinTextures().texture() : null;
   }

   private static boolean check4(PlayerEntity playerEntity) {
      return playerEntity instanceof AbstractClientPlayerEntity abstractClientPlayerEntity2 && abstractClientPlayerEntity2.getSkinTextures().model() == Model.SLIM;
   }

   private static boolean check5(PlayerEntityRenderState playerEntityRenderState) {
      return playerEntityRenderState != null && playerEntityRenderState.skinTextures != null && playerEntityRenderState.skinTextures.model() == Model.SLIM;
   }

   private static int compute2(int i, float f) {
      return ColorHelper.getArgb(compute7(Math.round(measure5(f) * 255.0F)), compute4(i), compute5(i), compute6(i));
   }

   private static int compute3(int i, int j, float f) {
      float floatValue62 = measure5(f);
      int intValue20 = Math.round(compute4(i) + (compute4(j) - compute4(i)) * floatValue62);
      int intValue21 = Math.round(compute5(i) + (compute5(j) - compute5(i)) * floatValue62);
      int intValue22 = Math.round(compute6(i) + (compute6(j) - compute6(i)) * floatValue62);
      return intValue20 << 16 | intValue21 << 8 | intValue22;
   }

   private static int compute4(int i) {
      return i >> 16 & 0xFF;
   }

   private static int compute5(int i) {
      return i >> 8 & 0xFF;
   }

   private static int compute6(int i) {
      return i & 0xFF;
   }

   private static int compute7(int i) {
      return Math.max(0, Math.min(255, i));
   }

   private static float measure3(float f) {
      float floatValue63 = 1.0F - measure5(f);
      return 1.0F - floatValue63 * floatValue63 * floatValue63;
   }

   private static float measure4(float f, float g, float h) {
      float floatValue64 = MathHelper.clamp((h - f) / Math.max(1.0E-4F, g - f), 0.0F, 1.0F);
      return floatValue64 * floatValue64 * (3.0F - 2.0F * floatValue64);
   }

   private static float measure5(float f) {
      return !Float.isFinite(f) ? 0.0F : MathHelper.clamp(f, 0.0F, 1.0F);
   }

   private static float measure6(float f) {
      return f - (float)Math.floor(f);
   }

   static enum DeadEffectState {
      TOTEM(0.9F, 0.58F, 0.92F, 0.035F, 0.82F, 0.44F, 0.42F, 0.66F, 0.2F, 0.58F, 1.18F, 0.22F, 1.1F, 6815716, 16777215, 16773226, 3669974, 0),
      DEATH(1.22F, 0.78F, 1.26F, 0.085F, 0.72F, 0.6F, 0.52F, 0.58F, 0.34F, 0.44F, 1.55F, 0.1F, 0.82F, 8693759, 15922687, 16739278, 6484991, 1);

      final float floatValue;
      final float floatValue2;
      final float floatValue3;
      final float floatValue4;
      final float floatValue5;
      final float floatValue6;
      final float floatValue7;
      final float floatValue8;
      final float floatValue9;
      final float floatValue10;
      final float floatValue11;
      final float floatValue12;
      final float floatValue13;
      final int intValue;
      final int intValue2;
      final int intValue3;
      final int intValue4;
      final int intValue5;

      private DeadEffectState(
         float f, float g, float h, float j, float k, float l, float m, float n, float o, float p, float q, float r, float s, int t, int u, int v, int w, int x
      ) {
         this.floatValue = f;
         this.floatValue2 = g;
         this.floatValue3 = h;
         this.floatValue4 = j;
         this.floatValue5 = k;
         this.floatValue6 = l;
         this.floatValue7 = m;
         this.floatValue8 = n;
         this.floatValue9 = o;
         this.floatValue10 = p;
         this.floatValue11 = q;
         this.floatValue12 = r;
         this.floatValue13 = s;
         this.intValue = t;
         this.intValue2 = u;
         this.intValue3 = v;
         this.intValue4 = w;
         this.intValue5 = x;
      }

      ParticleEffect resolve(int i) {
         int intValue23 = (i + this.intValue5) % 5;
         if (this == TOTEM) {
            return switch (intValue23) {
               case 0 -> ParticleTypes.TOTEM_OF_UNDYING;
               case 1 -> ParticleTypes.END_ROD;
               case 2 -> ParticleTypes.GLOW;
               case 3 -> ParticleTypes.ELECTRIC_SPARK;
               default -> ParticleTypes.WITCH;
            };
         } else {
            return switch (intValue23) {
               case 0 -> ParticleTypes.SOUL;
               case 1 -> ParticleTypes.SOUL_FIRE_FLAME;
               case 2 -> ParticleTypes.REVERSE_PORTAL;
               case 3 -> ParticleTypes.WHITE_ASH;
               default -> ParticleTypes.SCULK_SOUL;
            };
         }
      }
   }

   record DeadEffectData(
      float originX,
      float originY,
      float originZ,
      float pitch,
      float yaw,
      float roll,
      float xScale,
      float yScale,
      float zScale,
      boolean visible,
      boolean hidden
   ) {
      static DeadEffect.DeadEffectData capture(ModelPart modelPart) {
         return new DeadEffect.DeadEffectData(
            modelPart.originX,
            modelPart.originY,
            modelPart.originZ,
            modelPart.pitch,
            modelPart.yaw,
            modelPart.roll,
            modelPart.xScale,
            modelPart.yScale,
            modelPart.zScale,
            modelPart.visible,
            modelPart.hidden
         );
      }

      void apply(ModelPart modelPart) {
         modelPart.originX = this.originX;
         modelPart.originY = this.originY;
         modelPart.originZ = this.originZ;
         modelPart.pitch = this.pitch;
         modelPart.yaw = this.yaw;
         modelPart.roll = this.roll;
         modelPart.xScale = this.xScale;
         modelPart.yScale = this.yScale;
         modelPart.zScale = this.zScale;
         modelPart.visible = this.visible;
         modelPart.hidden = this.hidden;
      }
   }

   record DeadEffectData2(
      long capturedNanos,
      DeadEffect.DeadEffectData head,
      DeadEffect.DeadEffectData hat,
      DeadEffect.DeadEffectData body,
      DeadEffect.DeadEffectData rightArm,
      DeadEffect.DeadEffectData leftArm,
      DeadEffect.DeadEffectData rightLeg,
      DeadEffect.DeadEffectData leftLeg,
      DeadEffect.DeadEffectData leftSleeve,
      DeadEffect.DeadEffectData rightSleeve,
      DeadEffect.DeadEffectData leftPants,
      DeadEffect.DeadEffectData rightPants,
      DeadEffect.DeadEffectData jacket
   ) {

      static DeadEffect.DeadEffectData2 capture(PlayerEntityModel playerEntityModel, long l) {
         return new DeadEffect.DeadEffectData2(
            l,
            DeadEffect.DeadEffectData.capture(playerEntityModel.head),
            DeadEffect.DeadEffectData.capture(playerEntityModel.hat),
            DeadEffect.DeadEffectData.capture(playerEntityModel.body),
            DeadEffect.DeadEffectData.capture(playerEntityModel.rightArm),
            DeadEffect.DeadEffectData.capture(playerEntityModel.leftArm),
            DeadEffect.DeadEffectData.capture(playerEntityModel.rightLeg),
            DeadEffect.DeadEffectData.capture(playerEntityModel.leftLeg),
            DeadEffect.DeadEffectData.capture(playerEntityModel.leftSleeve),
            DeadEffect.DeadEffectData.capture(playerEntityModel.rightSleeve),
            DeadEffect.DeadEffectData.capture(playerEntityModel.leftPants),
            DeadEffect.DeadEffectData.capture(playerEntityModel.rightPants),
            DeadEffect.DeadEffectData.capture(playerEntityModel.jacket)
         );
      }

      void apply(PlayerEntityModel playerEntityModel) {
         this.head.apply(playerEntityModel.head);
         this.hat.apply(playerEntityModel.hat);
         this.body.apply(playerEntityModel.body);
         this.rightArm.apply(playerEntityModel.rightArm);
         this.leftArm.apply(playerEntityModel.leftArm);
         this.rightLeg.apply(playerEntityModel.rightLeg);
         this.leftLeg.apply(playerEntityModel.leftLeg);
         this.leftSleeve.apply(playerEntityModel.leftSleeve);
         this.rightSleeve.apply(playerEntityModel.rightSleeve);
         this.leftPants.apply(playerEntityModel.leftPants);
         this.rightPants.apply(playerEntityModel.rightPants);
         this.jacket.apply(playerEntityModel.jacket);
      }
   }

   record DeadEffectData3(double x, double y, double z, float bodyYaw, float baseScale) {

      static DeadEffect.DeadEffectData3 fromPlayer(PlayerEntity playerEntity) {
         float floatValue65 = Module.CLIENT.getRenderTickCounter().getTickProgress(true);
         double doubleValue14 = MathHelper.lerp(floatValue65, playerEntity.lastX, playerEntity.getX());
         double doubleValue15 = MathHelper.lerp(floatValue65, playerEntity.lastY, playerEntity.getY());
         double doubleValue16 = MathHelper.lerp(floatValue65, playerEntity.lastZ, playerEntity.getZ());
         float floatValue66 = MathHelper.lerpAngleDegrees(floatValue65, playerEntity.lastBodyYaw, playerEntity.bodyYaw);
         return new DeadEffect.DeadEffectData3(doubleValue14, doubleValue15, doubleValue16, floatValue66, Math.max(0.01F, playerEntity.getScale()));
      }

      static DeadEffect.DeadEffectData3 fromState(PlayerEntityRenderState playerEntityRenderState) {
         return new DeadEffect.DeadEffectData3(
            playerEntityRenderState.x,
            playerEntityRenderState.y,
            playerEntityRenderState.z,
            playerEntityRenderState.bodyYaw,
            Math.max(0.01F, playerEntityRenderState.baseScale)
         );
      }
   }

   static final class DeadEffectState2 {
      final DeadEffect.DeadEffectState deadEffectState;
      final long timestamp;
      final float floatValue;
      DeadEffect.DeadEffectData2 deadEffectData2;
      DeadEffect.DeadEffectData3 deadEffectData3;
      Identifier identifier;
      boolean flag;

      DeadEffectState2(DeadEffect.DeadEffectState deadEffectState7, long l, float f, DeadEffect.DeadEffectData2 deadEffectData24, DeadEffect.DeadEffectData3 deadEffectData34, Identifier identifier, boolean bl) {
         this.deadEffectState = deadEffectState7;
         this.timestamp = l;
         this.floatValue = f;
         this.deadEffectData2 = deadEffectData24;
         this.deadEffectData3 = deadEffectData34;
         this.identifier = identifier;
         this.flag = bl;
      }
   }
}
