package org.wild.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import ru.metaculture.protection.Stardust;

@Mixin({ClientWorld.class})
public abstract class ClientWorldMixin {
   @ModifyReturnValue(
      method = {"getCloudsColor(F)I"},
      at = {@At("RETURN")}
   )
   private int wild$modifyStardustCloudColor(int i, float f) {
      return Stardust.compute2(i);
   }
}
