package org.wild.mixin;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.ColorHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.metaculture.protection.Chams;
import ru.metaculture.protection.ChinaHat;
import ru.metaculture.protection.Cosmetics;
import ru.metaculture.protection.DeadEffect;
import ru.metaculture.protection.NameTags;
import ru.metaculture.protection.EntityFramebufferCapture;
import ru.metaculture.protection.PrismaticChamsRenderer;
import ru.metaculture.protection.SeeInvisibles;
import ru.metaculture.protection.WildClient;

@Mixin({LivingEntityRenderer.class})
public abstract class MixinLivingEntityRenderer {
   @Shadow
   protected EntityModel<? super LivingEntityRenderState> model;
   @Unique
   private PlayerEntityRenderState wild$lastPlayerState;

   @Inject(
      method = {"getRenderLayer"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void wild$usePrismaticChams(
      LivingEntityRenderState state,
      boolean showBody,
      boolean translucent,
      boolean showOutline,
      CallbackInfoReturnable<RenderLayer> callback
   ) {
      if (!EntityFramebufferCapture.getInstance().isCapturePassActive()) {
         Chams chams = Chams.getActive();
         if (chams != null && chams.shouldRender(state)) {
            callback.setReturnValue(PrismaticChamsRenderer.selectLayer(chams));
         }
      }
   }

   @Inject(
      method = {"shouldRenderFeatures"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void wild$skipFeaturesForPrismaticChams(LivingEntityRenderState state, CallbackInfoReturnable<Boolean> callback) {
      if (EntityFramebufferCapture.getInstance().isRenderingCapturedEntity()) {
         callback.setReturnValue(false);
      } else if (!EntityFramebufferCapture.getInstance().isCapturePassActive()) {
         Chams chams = Chams.getActive();
         if (chams != null && chams.shouldHideFeatures(state)) {
            callback.setReturnValue(false);
         }
      }
   }

   @Inject(
      method = {"getShadowRadius(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;)F"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void wild$hidePrismaticChamsShadow(LivingEntityRenderState state, CallbackInfoReturnable<Float> callback) {
      if (!EntityFramebufferCapture.getInstance().isCapturePassActive()) {
         Chams chams = Chams.getActive();
         if (chams != null && chams.shouldHideVanillaShadow(state)) {
            callback.setReturnValue(0.0F);
         }
      }
   }

   @Redirect(
      method = {"render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/render/VertexConsumerProvider;getBuffer(Lnet/minecraft/client/render/RenderLayer;)Lnet/minecraft/client/render/VertexConsumer;"
      )
   )
   private VertexConsumer wild$captureBaseLayer(
      VertexConsumerProvider buffers,
      RenderLayer renderLayer,
      LivingEntityRenderState state,
      MatrixStack renderMatrices,
      VertexConsumerProvider renderBuffers,
      int light
   ) {
      VertexConsumer baseConsumer = buffers.getBuffer(renderLayer);
      Chams chams = Chams.getActive();
      return chams != null && chams.shouldRender(state)
         ? baseConsumer
         : EntityFramebufferCapture.getInstance().captureLivingLayer(baseConsumer, renderLayer, state);
   }

   @Redirect(
      method = {"render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/render/entity/model/EntityModel;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;III)V"
      )
   )
   private void wild$renderModelWithPrismaticChams(
      EntityModel<LivingEntityRenderState> model,
      MatrixStack matrices,
      VertexConsumer vertexConsumer,
      int light,
      int overlay,
      int color,
      LivingEntityRenderState state,
      MatrixStack renderMatrices,
      VertexConsumerProvider buffers,
      int renderLight
   ) {
      if (EntityFramebufferCapture.getInstance().isCapturePassActive()) {
         model.render(matrices, vertexConsumer, light, overlay, color);
         this.wild$lastPlayerState = null;
      } else {
         Chams chams = Chams.getActive();
         if (chams != null && chams.shouldRender(state)) {
            float transitionAlpha = chams.getTransitionAlpha(state);
            PrismaticChamsRenderer.captureScreenTexture();
            if (chams.isHybridDepthMode()) {
               this.wild$renderChamsPass(
                  model, matrices, buffers, state, light, overlay, color, PrismaticChamsRenderer.getDepthLayer(), 1.0F, transitionAlpha
               );
               this.wild$renderChamsPass(
                  model, matrices, buffers, state, light, overlay, color, PrismaticChamsRenderer.getVisibleLayer(), 0.0F, transitionAlpha
               );
            } else {
               RenderLayer chamsLayer = chams.usesVisiblePassOnly() ? PrismaticChamsRenderer.getVisibleLayer() : PrismaticChamsRenderer.getDepthLayer();
               float depthPass = chams.usesVisiblePassOnly() ? 0.0F : 1.0F;
               this.wild$renderChamsPass(model, matrices, buffers, state, light, overlay, color, chamsLayer, depthPass, transitionAlpha);
            }

            if (state instanceof PlayerEntityRenderState playerState && (Object)model instanceof PlayerEntityModel playerModel) {
               DeadEffect.capturePlayerModelState(playerState, playerModel, matrices, buffers, light, overlay);
            }

            this.wild$lastPlayerState = state instanceof PlayerEntityRenderState playerState ? playerState : null;
         } else {
            model.render(matrices, vertexConsumer, light, overlay, this.wild$applySeeInvisiblesAlpha(state, color));
            if (state instanceof PlayerEntityRenderState playerState && (Object)model instanceof PlayerEntityModel playerModel) {
               DeadEffect.capturePlayerModelState(playerState, playerModel, matrices, buffers, light, overlay);
            }

            this.wild$lastPlayerState = state instanceof PlayerEntityRenderState playerState ? playerState : null;
         }
      }
   }

   @Unique
   private int wild$applySeeInvisiblesAlpha(LivingEntityRenderState state, int color) {
      if (state instanceof PlayerEntityRenderState && state.invisible) {
         SeeInvisibles seeInvisibles = WildClient.INSTANCE.moduleManager.getModule(SeeInvisibles.class);
         return seeInvisibles != null && seeInvisibles.enabled
            ? ColorHelper.withAlpha(Math.round(seeInvisibles.opacity.getValue() * 255.0F), color)
            : color;
      } else {
         return color;
      }
   }

   @Redirect(
      method = {"render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/render/entity/feature/FeatureRenderer;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/EntityRenderState;FF)V"
      )
   )
   private void wild$captureFeatureRender(
      FeatureRenderer<?, ?> featureRenderer,
      MatrixStack matrices,
      VertexConsumerProvider buffers,
      int light,
      EntityRenderState state,
      float limbAngle,
      float limbDistance
   ) {
      if (state instanceof LivingEntityRenderState livingState) {
         EntityFramebufferCapture.getInstance().renderFeatureIntoCapture(featureRenderer, matrices, buffers, light, livingState, limbAngle, limbDistance);
      } else {
         ((FeatureRenderer)featureRenderer).render(matrices, buffers, light, state, limbAngle, limbDistance);
      }
   }

   @Inject(
      method = {"render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/util/math/MatrixStack;pop()V"
      )}
   )
   private void wild$capturePlayerModelOverlays(
      LivingEntityRenderState state, MatrixStack matrices, VertexConsumerProvider buffers, int light, CallbackInfo callbackInfo
   ) {
      try {
         if (EntityFramebufferCapture.getInstance().isCapturePassActive()) {
            this.wild$lastPlayerState = null;
            return;
         }

         PlayerEntityRenderState capturedPlayerState = this.wild$lastPlayerState;
         this.wild$lastPlayerState = null;
         if (capturedPlayerState == null || !(state instanceof PlayerEntityRenderState currentPlayerState) || currentPlayerState != capturedPlayerState) {
            return;
         }

         if (!((Object)this.model instanceof PlayerEntityModel playerModel)) {
            return;
         }

         NameTags.capturePlayerSkeleton(capturedPlayerState, playerModel, matrices);
         Chams chams = Chams.getActive();
         if (chams == null || !chams.shouldHideFeatures(state)) {
            ChinaHat.renderForPlayer(capturedPlayerState, playerModel, matrices, buffers, light);
            Cosmetics.renderForPlayer(capturedPlayerState, playerModel, matrices, buffers, light);
            return;
         }
      } catch (Throwable ignored) {
         return;
      } finally {
         EntityFramebufferCapture.getInstance().finishLivingEntityCapture(state);
      }
   }

   @Unique
   private void wild$renderChamsPass(
      EntityModel<LivingEntityRenderState> model,
      MatrixStack matrices,
      VertexConsumerProvider buffers,
      LivingEntityRenderState state,
      int light,
      int overlay,
      int color,
      RenderLayer renderLayer,
      float depthPass,
      float transitionAlpha
   ) {
      Chams chams = Chams.getActive();
      if (chams != null) {
         PrismaticChamsRenderer.updateUniforms(chams, state, depthPass, transitionAlpha);
         VertexConsumer consumer = EntityFramebufferCapture.getInstance().captureLivingLayer(buffers.getBuffer(renderLayer), renderLayer, state);
         model.render(matrices, consumer, light, overlay, ColorHelper.withAlpha(255, color));
         if (buffers instanceof Immediate immediate) {
            immediate.draw(renderLayer);
         }
      }
   }
}
