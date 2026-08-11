package org.wild.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.state.ArmedEntityRenderState;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.metaculture.protection.cosmetics.render.MaceKosaRenderer;

@Environment(EnvType.CLIENT)
@Mixin(HeldItemFeatureRenderer.class)
public abstract class HeldItemFeatureRendererMixin {
   @Inject(
      method = "renderItem(Lnet/minecraft/client/render/entity/state/ArmedEntityRenderState;Lnet/minecraft/client/render/item/ItemRenderState;Lnet/minecraft/util/Arm;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
      at = @At("HEAD"),
      cancellable = true
   )
   private void wild$replaceMaceKosa(
      ArmedEntityRenderState state,
      ItemRenderState itemState,
      Arm arm,
      MatrixStack matrices,
      VertexConsumerProvider buffers,
      int light,
      CallbackInfo callbackInfo
   ) {
      if (itemState != null && !itemState.isEmpty() && MaceKosaRenderer.tryRenderThirdPerson(state, arm, matrices, buffers, light)) {
         callbackInfo.cancel();
      }
   }
}
