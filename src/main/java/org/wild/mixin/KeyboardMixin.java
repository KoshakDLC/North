package org.wild.mixin;

import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.metaculture.protection.AutoBuy;
import ru.metaculture.protection.AutoBuyScreen;
import ru.metaculture.protection.Event;
import ru.metaculture.protection.EventManager;
import ru.metaculture.protection.MenuModule;
import ru.metaculture.protection.ClickGuiScreen;
import ru.metaculture.protection.RawInputEvent;
import ru.metaculture.protection.FreeLookController;
import ru.metaculture.protection.ItemRuleCodeGenerator;
import ru.metaculture.protection.ProtectionHandler;
import ru.metaculture.protection.Removals;
import ru.metaculture.protection.UnHook;
import ru.metaculture.protection.WildClient;

@Mixin({Keyboard.class})
public class KeyboardMixin {
   @Inject(
      method = {"onKey"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void wild$handleKeyInput(long windowHandle, int keyCode, int scanCode, int action, int modifiers, CallbackInfo callbackInfo) {
      ProtectionHandler.checkAccess();
      if (UnHook.active) {
         if (action == GLFW.GLFW_PRESS && keyCode == GLFW.GLFW_KEY_RIGHT_SHIFT && (modifiers & GLFW.GLFW_MOD_CONTROL) != 0) {
            callbackInfo.cancel();
         }
      } else if (Removals.shouldBlockNarratorHotkey()
         && action == GLFW.GLFW_PRESS
         && keyCode == GLFW.GLFW_KEY_B
         && (modifiers & GLFW.GLFW_MOD_CONTROL) != 0) {
         callbackInfo.cancel();
      } else if (WildClient.isInitialized() && WildClient.INSTANCE != null) {
         MinecraftClient client = MinecraftClient.getInstance();
         if (client != null && client.getWindow() != null) {
            if (action == GLFW.GLFW_PRESS
               && client.options != null
               && FreeLookController.active
               && client.options.togglePerspectiveKey.matchesKey(keyCode, scanCode)) {
               callbackInfo.cancel();
            } else if (action == GLFW.GLFW_PRESS
               && keyCode == GLFW.GLFW_KEY_C
               && (modifiers & GLFW.GLFW_MOD_CONTROL) != 0
               && (modifiers & GLFW.GLFW_MOD_ALT) != 0
               && ItemRuleCodeGenerator.copyHoveredItemRule(client)) {
               callbackInfo.cancel();
            } else if (client.currentScreen == null && isWindowInputUsable(client, windowHandle)) {
               RawInputEvent inputEvent = new RawInputEvent(windowHandle, keyCode, scanCode, action, modifiers);
               EventManager.post((Event)inputEvent);
               if (inputEvent.getAction() == GLFW.GLFW_PRESS && client.currentScreen == null) {
                  AutoBuy autoBuy = WildClient.INSTANCE.moduleManager.getModule(AutoBuy.class);
                  if (autoBuy != null && autoBuy.menuKey.getKeyCode() != -1 && inputEvent.getKeyCode() == autoBuy.menuKey.getKeyCode()) {
                     client.setScreen(new AutoBuyScreen());
                     if (client.mouse != null) {
                        client.mouse.unlockCursor();
                     }

                     inputEvent.invalidate();
                  }

                  MenuModule menuModule = MenuModule.getInstance();
                  int menuKey = menuModule != null && menuModule.bindKey != -1 ? menuModule.bindKey : GLFW.GLFW_KEY_RIGHT_SHIFT;
                  if (menuKey != -1 && inputEvent.getKeyCode() == menuKey) {
                     ClickGuiScreen clickGui = WildClient.INSTANCE.getClickGuiScreen();
                     if (clickGui != null) {
                        client.setScreen(clickGui);
                        if (client.mouse != null) {
                           client.mouse.unlockCursor();
                        }

                        inputEvent.invalidate();
                     }
                  }
               }

               if (inputEvent.isInvalidated()) {
                  callbackInfo.cancel();
               }
            }
         }
      }
   }

   private static boolean isWindowInputUsable(MinecraftClient client, long windowHandle) {
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
