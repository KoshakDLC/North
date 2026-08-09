package org.wild.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.util.Window;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.metaculture.protection.Event;
import ru.metaculture.protection.EventManager;
import ru.metaculture.protection.MouseButtonEvent;
import ru.metaculture.protection.RawInputEvent;
import ru.metaculture.protection.MouseButtonPositionEvent;
import ru.metaculture.protection.ProtectionHandler;

@Mixin({Mouse.class})
public class MouseClickMixin {
   @Inject(
      method = {"onMouseButton"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void wild$handleMouseButton(long windowHandle, int button, int action, int modifiers, CallbackInfo callbackInfo) {
      ProtectionHandler.checkAccess();
      MinecraftClient client = MinecraftClient.getInstance();
      if (!wild$isWindowInputUsable(client, windowHandle)) {
         callbackInfo.cancel();
      } else {
         double[] cursorX = new double[1];
         double[] cursorY = new double[1];
         GLFW.glfwGetCursorPos(windowHandle, cursorX, cursorY);
         MouseButtonPositionEvent mouseEvent = new MouseButtonPositionEvent(
            windowHandle, button, action, modifiers, cursorX[0], cursorY[0]
         );
         EventManager.post((Event)mouseEvent);
         EventManager.post((Event)(new RawInputEvent(windowHandle, -100 - button, 0, action, modifiers)));
         if (mouseEvent.isInvalidated()) {
            callbackInfo.cancel();
         }
      }
   }

   @Inject(
      method = {"lockCursor"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void wild$preventCursorLock(CallbackInfo callbackInfo) {
      ProtectionHandler.checkAccess();
      MinecraftClient client = MinecraftClient.getInstance();
      long windowHandle = 0L;
      if (client != null && client.getWindow() != null) {
         windowHandle = client.getWindow().getHandle();
      }

      if (wild$isWindowInputUsable(client, windowHandle) && client.currentScreen == null) {
         MouseButtonEvent cursorLockEvent = new MouseButtonEvent(client, windowHandle);
         EventManager.post((Event)cursorLockEvent);
         if (cursorLockEvent.isInvalidated()) {
            callbackInfo.cancel();
         }
      } else {
         callbackInfo.cancel();
      }
   }

   @Unique
   private static boolean wild$isWindowInputUsable(MinecraftClient client, long windowHandle) {
      if (client != null && client.getWindow() != null && windowHandle != 0L && client.isWindowFocused()) {
         Window window = client.getWindow();
         return windowHandle == window.getHandle()
            && !window.hasZeroWidthOrHeight()
            && window.getFramebufferWidth() > 0
            && window.getFramebufferHeight() > 0;
      } else {
         return false;
      }
   }
}
