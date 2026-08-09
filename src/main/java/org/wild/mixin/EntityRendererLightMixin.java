package org.wild.mixin;

import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.metaculture.protection.FullBright;

@Mixin({EntityRenderer.class})
public class EntityRendererLightMixin {
   @Inject(
      method = {"getLight(Lnet/minecraft/entity/Entity;F)I"},
      at = {@At("RETURN")},
      cancellable = true
   )
   private void wild$torchLight(Entity entity, float f, CallbackInfoReturnable<Integer> callbackInfoReturnable) {
      if (FullBright.flag) {
         int intValue = FullBright.compute2(entity.getX(), entity.getY() + entity.getHeight() * 0.5, entity.getZ());
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
