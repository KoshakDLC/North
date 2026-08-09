package org.wild.mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.metaculture.protection.AutoLoginManager;
import ru.metaculture.protection.MinecraftAccessor;
import ru.metaculture.protection.ProtectInfo;
import ru.metaculture.protection.ProtectionHandler;
import ru.metaculture.protection.PvPSafe;
import ru.metaculture.protection.UnHook;
import ru.metaculture.protection.WildClient;

@Mixin({ClientPlayNetworkHandler.class})
public class ClientPlayNetworkHandlerMixin implements MinecraftAccessor {
   @ModifyVariable(
      method = {"sendChatMessage"},
      at = @At("HEAD"),
      argsOnly = true,
      ordinal = 0
   )
   private String wild$protectOutgoingChatMessage(String string) {
      return ProtectInfo.resolve2(string);
   }

   @Inject(
      method = {"sendChatMessage"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onSendChatMessage(String string, CallbackInfo callbackInfo) {
      ProtectionHandler.checkAccess();
      if (WildClient.text != null && string.equalsIgnoreCase(WildClient.text)) {
         UnHook unHook = WildClient.INSTANCE.moduleManager.getModule(UnHook.class);
         if (unHook != null && unHook.enabled) {
            unHook.setEnabled(false);
            callbackInfo.cancel();
            return;
         }
      }

      if (PvPSafe.check3(string)) {
         callbackInfo.cancel();
      } else if (!UnHook.active) {
         String text = WildClient.INSTANCE.getCommandPrefix();
         if (string.startsWith(text)) {
            WildClient.INSTANCE.getCommandManager().execute(string);
            callbackInfo.cancel();
         }
      }
   }

   @Inject(
      method = {"sendChatCommand"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void wild$blockPvpSafeCommand(String string, CallbackInfo callbackInfo) {
      if (PvPSafe.check4(string)) {
         callbackInfo.cancel();
      }
   }

   @Inject(
      method = {"runClickEventCommand"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void wild$blockPvpSafeClickCommand(String string, Screen screen, CallbackInfo callbackInfo) {
      if (PvPSafe.check4(string)) {
         callbackInfo.cancel();
      }
   }

   @Inject(
      method = {"onGameJoin"},
      at = {@At("TAIL")}
   )
   private void onGameJoin(GameJoinS2CPacket gameJoinS2CPacket, CallbackInfo callbackInfo) {
      ProtectionHandler.checkAccess();
      AutoLoginManager.invoke3(a_);
   }

   @Redirect(
      method = {"onPlayerList"},
      at = @At(
         value = "INVOKE",
         target = "Lorg/slf4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V",
         remap = false
      )
   )
   private void suppressUnknownPlayerLog(Logger logger, String string, Object object, Object object2) {
      if (!string.startsWith("Ignoring player info update")) {
         logger.warn(string, object, object2);
      }
   }
}
