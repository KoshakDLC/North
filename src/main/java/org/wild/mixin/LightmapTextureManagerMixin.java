package org.wild.mixin;

import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import ru.metaculture.protection.FullBright;
import ru.metaculture.protection.WildClient;

@Mixin({LightmapTextureManager.class})
public class LightmapTextureManagerMixin {
   @Redirect(
      method = {"update"},
      at = @At(
         value = "INVOKE",
         target = "Ljava/lang/Double;floatValue()F",
         ordinal = 1
      )
   )
   private float getGammaValue(Double double_) {
      if (WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null) {
         FullBright fullBright = WildClient.INSTANCE.moduleManager.getModule(FullBright.class);
         if (fullBright != null && fullBright.enabled) {
            if (fullBright.check()) {
               return 200.0F;
            }

            if (fullBright.check2()) {
               return fullBright.measure3();
            }

            if (fullBright.check3()) {
               return fullBright.measure2();
            }
         }
      }

      return double_.floatValue();
   }

   @Redirect(
      method = {"update"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/dimension/DimensionType;ambientLight()F"
      )
   )
   private float getAmbientFloor(DimensionType dimensionType) {
      float floatValue = dimensionType.ambientLight();
      if (WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null) {
         FullBright fullBright2 = WildClient.INSTANCE.moduleManager.getModule(FullBright.class);
         if (fullBright2 != null && fullBright2.enabled && fullBright2.check3()) {
            return Math.max(floatValue, fullBright2.measure());
         }
      }

      return floatValue;
   }
}
