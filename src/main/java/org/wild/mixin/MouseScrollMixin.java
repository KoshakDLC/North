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
import ru.metaculture.protection.RawInputEvent;
import ru.metaculture.protection.MouseScrollEvent;
import ru.metaculture.protection.ProtectionHandler;

@Mixin({Mouse.class})
public class MouseScrollMixin {
   @Unique
   private static final int SCROLL_UP_BIND = -200;
   @Unique
   private static final int SCROLL_DOWN_BIND = -201;

   @Inject(
      method = {"onMouseScroll"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void wild$handleMouseScroll(long windowHandle, double horizontalOffset, double verticalOffset, CallbackInfo callbackInfo) {
      ProtectionHandler.checkAccess();
      MinecraftClient client = MinecraftClient.getInstance();
      if (!wild$isWindowInputUsable(client, windowHandle)) {
         callbackInfo.cancel();
      } else {
         double[] cursorX = new double[1];
         double[] cursorY = new double[1];
         GLFW.glfwGetCursorPos(windowHandle, cursorX, cursorY);
         MouseScrollEvent scrollEvent = new MouseScrollEvent(
            windowHandle, horizontalOffset, verticalOffset, cursorX[0], cursorY[0]
         );
         EventManager.post((Event)scrollEvent);
         if (Math.abs(verticalOffset) > 1.0E-4) {
            int bindCode = verticalOffset > 0.0 ? SCROLL_UP_BIND : SCROLL_DOWN_BIND;
            EventManager.post((Event)(new RawInputEvent(windowHandle, bindCode, 0, GLFW.GLFW_PRESS, 0)));
         }

         if (scrollEvent.isInvalidated()) {
            callbackInfo.cancel();
         }
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
