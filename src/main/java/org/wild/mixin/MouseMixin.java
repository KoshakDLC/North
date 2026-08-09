package org.wild.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.util.Window;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import ru.metaculture.protection.Event;
import ru.metaculture.protection.EventManager;
import ru.metaculture.protection.MouseUpdateEvent;
import ru.metaculture.protection.MouseDeltaEvent;
import ru.metaculture.protection.RenderMath;
import ru.metaculture.protection.PlayerHelper;
import ru.metaculture.protection.ProtectionHandler;

@Mixin({Mouse.class})
public abstract class MouseMixin {
   @Inject(
      method = {"updateMouse"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void cancelCameraMovement(CallbackInfo callbackInfo) {
      ProtectionHandler.checkAccess();
      MinecraftClient client = MinecraftClient.getInstance();
      if (!isMouseWindowUsable(client)) {
         callbackInfo.cancel();
      } else {
         MouseUpdateEvent mouseUpdateEvent = new MouseUpdateEvent(client);
         EventManager.post((Event)mouseUpdateEvent);
         if (mouseUpdateEvent.isInvalidated()) {
            callbackInfo.cancel();
         }
      }
   }

   @Inject(
      method = {"updateMouse"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/network/ClientPlayerEntity;changeLookDirection(DD)V"
      )},
      locals = LocalCapture.CAPTURE_FAILHARD,
      cancellable = true
   )
   private void onLook(double d, CallbackInfo callbackInfo, double e, double f, double g, double h, double i, int j) {
      MinecraftClient client2 = MinecraftClient.getInstance();
      if (!isMouseWindowUsable(client2)) {
         callbackInfo.cancel();
      } else {
         if (client2 != null && client2.player != null) {
            MouseDeltaEvent mouseDeltaEvent = new MouseDeltaEvent(e, f * j);
            EventManager.post((Event)mouseDeltaEvent);
            if (!mouseDeltaEvent.isInvalidated()) {
               client2.player.changeLookDirection(mouseDeltaEvent.doubleValue, mouseDeltaEvent.doubleValue2);
            }

            callbackInfo.cancel();
         }
      }
   }

   @Inject(
      method = {"onMouseScroll"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onMouseScroll(long l, double d, double e, CallbackInfo callbackInfo) {
      ProtectionHandler.checkAccess();
      MinecraftClient client3 = MinecraftClient.getInstance();
      if (isMouseWindowUsable(client3)) {
         if (PlayerHelper.flag3 && e != 0.0) {
            PlayerHelper.floatValue2 -= (float)(e * 0.075F);
            PlayerHelper.floatValue2 = RenderMath.measure49(PlayerHelper.floatValue2, 0.02F, 2.0F);
            callbackInfo.cancel();
         }
      }
   }

   private static boolean isMouseWindowUsable(MinecraftClient minecraftClient) {
      if (minecraftClient != null && minecraftClient.getWindow() != null && minecraftClient.isWindowFocused()) {
         Window window = minecraftClient.getWindow();
         return !window.hasZeroWidthOrHeight() && window.getFramebufferWidth() > 0 && window.getFramebufferHeight() > 0;
      } else {
         return false;
      }
   }
}
