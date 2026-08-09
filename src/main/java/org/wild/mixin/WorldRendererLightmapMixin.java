package org.wild.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.WorldRenderer.BrightnessGetter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.metaculture.protection.FullBright;

@Mixin({WorldRenderer.class})
public class WorldRendererLightmapMixin {
   @Inject(
      method = {"getLightmapCoordinates(Lnet/minecraft/client/render/WorldRenderer$BrightnessGetter;Lnet/minecraft/world/BlockRenderView;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;)I"},
      at = {@At("RETURN")},
      cancellable = true
   )
   private static void wild$torchLight(
      BrightnessGetter brightnessGetter,
      BlockRenderView blockRenderView,
      BlockState blockState,
      BlockPos blockPos,
      CallbackInfoReturnable<Integer> callbackInfoReturnable
   ) {
      if (FullBright.flag) {
         int intValue = FullBright.compute(blockPos.getX(), blockPos.getY(), blockPos.getZ());
         if (intValue > 0) {
            int intValue2 = callbackInfoReturnable.getReturnValueI();
            int intValue3 = LightmapTextureManager.getBlockLightCoordinates(intValue2);
            if (intValue > intValue3) {
               int intValue4 = LightmapTextureManager.getSkyLightCoordinates(intValue2);
               callbackInfoReturnable.setReturnValue(LightmapTextureManager.pack(intValue, intValue4));
            }
         }
      }
   }
}
