package org.wild.mixin;

import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.sound.SoundSystem.PlayResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.metaculture.protection.Removals;

@Mixin({SoundManager.class})
public class SoundManagerMixin {
   @Inject(
      method = {"play(Lnet/minecraft/client/sound/SoundInstance;)Lnet/minecraft/client/sound/SoundSystem$PlayResult;"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void wild$muteSound(SoundInstance soundInstance, CallbackInfoReturnable<PlayResult> callbackInfoReturnable) {
      if (soundInstance != null) {
         if (Removals.check5(soundInstance.getId())) {
            callbackInfoReturnable.setReturnValue(PlayResult.NOT_STARTED);
         }
      }
   }
}
