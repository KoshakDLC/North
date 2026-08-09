package org.wild.mixin;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.metaculture.protection.EntityFramebufferCapture;

@Mixin({WorldRenderer.class})
public abstract class WorldRendererEntityCaptureMixin {
   @Inject(
      method = {"renderEntity"},
      at = {@At("HEAD")}
   )
   private void captureEntity(
      Entity entity, double d, double e, double f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, CallbackInfo callbackInfo
   ) {
      EntityFramebufferCapture entityFramebufferCapture = EntityFramebufferCapture.getInstance();
      if (entityFramebufferCapture.isCaptureFrameReady()) {
         entityFramebufferCapture.captureEntity(entity, d, e, f, g, matrixStack);
      }
   }
}
