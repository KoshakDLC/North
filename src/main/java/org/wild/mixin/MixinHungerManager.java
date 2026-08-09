package org.wild.mixin;

import net.minecraft.entity.player.HungerManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.metaculture.protection.Sprint;
import ru.metaculture.protection.WildClient;

@Mixin({HungerManager.class})
public class MixinHungerManager {
   @Inject(
      method = {"getFoodLevel"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onGetFoodLevel(CallbackInfoReturnable<Integer> callbackInfoReturnable) {
      Sprint sprint = WildClient.INSTANCE.moduleManager.getModule(Sprint.class);
      if (sprint != null && sprint.enabled && sprint.ignorirovatGolod.isEnabled()) {
         callbackInfoReturnable.setReturnValue(8);
      }
   }
}
