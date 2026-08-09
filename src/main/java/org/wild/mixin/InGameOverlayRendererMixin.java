package org.wild.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.metaculture.protection.Removals;

@Mixin({InGameOverlayRenderer.class})
public class InGameOverlayRendererMixin {
   @Inject(
      method = {"renderFireOverlay"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private static void onRenderFireOverlay(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, CallbackInfo callbackInfo) {
      if (Removals.check("Огонь")) {
         callbackInfo.cancel();
      }
   }

   @Inject(
      method = {"renderUnderwaterOverlay"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private static void onRenderUnderwaterOverlay(
      MinecraftClient minecraftClient, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, CallbackInfo callbackInfo
   ) {
      if (Removals.check("Вода")) {
         callbackInfo.cancel();
      }
   }

   @Inject(
      method = {"renderInWallOverlay"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private static void onRenderInWallOverlay(Sprite sprite, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, CallbackInfo callbackInfo) {
      if (Removals.check("Стена в глазах")) {
         callbackInfo.cancel();
      }
   }

   @Inject(
      method = {"renderFloatingItem"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 0
   )
   private void onRenderFloatingItem(MatrixStack matrixStack, float f, CallbackInfo callbackInfo) {
      if (Removals.check("Анимация тотема")) {
         callbackInfo.cancel();
      }
   }
}
