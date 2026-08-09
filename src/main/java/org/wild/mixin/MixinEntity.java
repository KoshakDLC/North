package org.wild.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.metaculture.protection.SeeInvisibles;
import ru.metaculture.protection.WildClient;

@Mixin({Entity.class})
public abstract class MixinEntity {
   @Inject(
      method = {"isInvisibleTo"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onIsInvisibleTo(PlayerEntity playerEntity, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
      SeeInvisibles seeInvisibles = WildClient.INSTANCE.moduleManager.getModule(SeeInvisibles.class);
      if (seeInvisibles != null && seeInvisibles.enabled && !((Object)this instanceof ArmorStandEntity)) {
         callbackInfoReturnable.setReturnValue(false);
      }
   }
}
