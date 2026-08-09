package org.wild.mixin;

import net.minecraft.client.util.NarratorManager;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.metaculture.protection.Removals;

@Mixin({NarratorManager.class})
public class NarratorManagerMixin {
   @Inject(
      method = {"narrateText(Lnet/minecraft/text/Text;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void wild$silenceNarrator(Text text, CallbackInfo callbackInfo) {
      if (Removals.shouldBlockNarratorHotkey()) {
         callbackInfo.cancel();
      }
   }
}
