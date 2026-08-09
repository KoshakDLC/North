package org.wild.mixin;

import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.metaculture.protection.Removals;

@Mixin({ToastManager.class})
public class ToastManagerMixin {
   @Inject(
      method = {"add(Lnet/minecraft/client/toast/Toast;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void wild$filterToast(Toast toast, CallbackInfo callbackInfo) {
      if (Removals.check7(toast)) {
         callbackInfo.cancel();
      }
   }
}
