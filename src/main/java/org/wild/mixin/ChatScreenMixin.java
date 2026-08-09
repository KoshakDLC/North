package org.wild.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.metaculture.protection.Animations;
import ru.metaculture.protection.CommandManager;
import ru.metaculture.protection.DelayedFuse;
import ru.metaculture.protection.WildClient;

@Mixin({ChatScreen.class})
public abstract class ChatScreenMixin extends Screen {
   protected ChatScreenMixin(Text text) {
      super(text);
   }

   @Inject(
      method = {"sendMessage"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void wild$dispatchClientCommand(String message, boolean addToHistory, CallbackInfo callbackInfo) {
      if (message == null || WildClient.INSTANCE == null) {
         return;
      }

      CommandManager commandManager = WildClient.INSTANCE.getCommandManager();
      String prefix = WildClient.INSTANCE.getCommandPrefix();
      String normalizedMessage = message.trim();
      if (commandManager == null || prefix == null || prefix.isEmpty() || !normalizedMessage.startsWith(prefix)) {
         return;
      }

      MinecraftClient client = MinecraftClient.getInstance();
      if (addToHistory && client != null && client.inGameHud != null) {
         client.inGameHud.getChatHud().addToMessageHistory(normalizedMessage);
      }

      commandManager.execute(normalizedMessage);
      callbackInfo.cancel();
   }

   @Inject(
      method = {"render"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 0
   )
   private void wild$cancelChatScreenRenderDuringCorruption(DrawContext drawContext, int i, int j, float f, CallbackInfo callbackInfo) {
      if (DelayedFuse.check2()) {
         callbackInfo.cancel();
      }
   }

   @Inject(
      method = {"renderBackground"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 0
   )
   private void wild$cancelChatScreenBackgroundDuringCorruption(DrawContext drawContext, int i, int j, float f, CallbackInfo callbackInfo) {
      if (DelayedFuse.check2()) {
         callbackInfo.cancel();
      }
   }

   @Inject(
      method = {"render"},
      at = {@At("HEAD")}
   )
   private void litka$animateChat(DrawContext drawContext, int i, int j, float f, CallbackInfo callbackInfo) {
      Animations animations = animations();
      if (animations != null && animations.enabled && animations.animirovat.isEnabled("Чат")) {
         if (animations.timedAnimation2 == null) {
            animations.invoke();
         }

         if (!animations.isFlag2()) {
            animations.timedAnimation2.setTimestamp2(1.0);
         } else {
            animations.timedAnimation2.setTimestamp2(0.0);
         }

         float floatValue = (float)animations.timedAnimation2.getDoubleValue3();
         float floatValue2 = (1.0F - floatValue) * 30.0F;
         drawContext.getMatrices().pushMatrix();
         drawContext.getMatrices().translate(0.0F, floatValue2);
      }
   }

   @Inject(
      method = {"render"},
      at = {@At("TAIL")}
   )
   private void litka$endChatAnimate(DrawContext drawContext, int i, int j, float f, CallbackInfo callbackInfo) {
      Animations animations2 = animations();
      if (animations2 != null && animations2.enabled && animations2.animirovat.isEnabled("Чат")) {
         drawContext.getMatrices().popMatrix();
      }
   }

   @Inject(
      method = {"keyPressed"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void litka$interceptEscape(int i, int j, int k, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
      if (i == 256) {
         Animations animations3 = animations();
         if (animations3 != null && animations3.enabled && animations3.animirovat.isEnabled("Чат")) {
            if (!animations3.isFlag2()) {
               animations3.invoke2();
               callbackInfoReturnable.setReturnValue(true);
            } else if (!animations3.check()) {
               MinecraftClient client2 = MinecraftClient.getInstance();
               if (client2 != null) {
                  animations3.invoke3();
                  client2.setScreen(null);
               }

               callbackInfoReturnable.setReturnValue(true);
            }
         }
      }
   }

   @Redirect(
      method = {"keyPressed"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/MinecraftClient;setScreen(Lnet/minecraft/client/gui/screen/Screen;)V"
      ),
      require = 0
   )
   private void litka$deferEnterClose(MinecraftClient minecraftClient, Screen screen) {
      Animations animations4 = animations();
      if (animations4 != null && animations4.enabled && animations4.animirovat.isEnabled("Чат") && screen == null) {
         if (!animations4.isFlag2()) {
            animations4.invoke2();
         }
      } else {
         minecraftClient.setScreen(screen);
      }
   }

   @Inject(
      method = {"removed"},
      at = {@At("HEAD")}
   )
   private void litka$onChatClose(CallbackInfo callbackInfo) {
      Animations animations5 = animations();
      if (animations5 != null) {
         animations5.invoke3();
      }
   }

   private static Animations animations() {
      return WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null ? WildClient.INSTANCE.moduleManager.getModule(Animations.class) : null;
   }
}
