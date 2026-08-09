package org.wild.mixin;

import java.util.Locale;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientCommonNetworkHandler;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.network.ServerInfo.ResourcePackPolicy;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.DisconnectionInfo;
import net.minecraft.network.packet.s2c.common.ResourcePackSendS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.metaculture.protection.ServerConnector;
import ru.metaculture.protection.PlayerHelper;
import ru.metaculture.protection.WildClient;

@Mixin({ClientCommonNetworkHandler.class})
public abstract class ClientCommonNetworkHandlerMixin {
   @Shadow
   protected ServerInfo serverInfo;
   @Shadow
   protected MinecraftClient client;
   @Shadow
   protected ClientConnection connection;

   @Inject(
      method = {"onDisconnected"},
      at = {@At("TAIL")}
   )
   private void onDisconnected(DisconnectionInfo disconnectionInfo, CallbackInfo callbackInfo) {
      ServerConnector.invoke(this.serverInfo, disconnectionInfo);
   }

   @Inject(
      method = {"onResourcePackSend"},
      at = {@At("HEAD")}
   )
   private void wild$preferVanillaServerResourcePack(ResourcePackSendS2CPacket resourcePackSendS2CPacket, CallbackInfo callbackInfo) {
      if (resourcePackSendS2CPacket != null && this.client != null && this.wild$playerHelperWantsLoad()) {
         if ((this.wild$isFunTimeEndpoint(resourcePackSendS2CPacket.url()) || this.wild$isFunTimeEndpoint(this.wild$currentServerAddress()))
            && this.serverInfo != null) {
            this.serverInfo.setResourcePackPolicy(ResourcePackPolicy.ENABLED);
         }
      }
   }

   private boolean wild$playerHelperWantsLoad() {
      try {
         if (WildClient.isInitialized() && WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null) {
            PlayerHelper playerHelper = (PlayerHelper)WildClient.INSTANCE.moduleManager.findModule(PlayerHelper.class);
            return playerHelper != null && playerHelper.rezhimResursPakov.is("Load");
         } else {
            return false;
         }
      } catch (Throwable exception) {
         return false;
      }
   }

   private String wild$currentServerAddress() {
      try {
         if (this.serverInfo != null && this.serverInfo.address != null) {
            return this.serverInfo.address;
         } else {
            ServerInfo serverInfo = this.client.getCurrentServerEntry();
            return serverInfo == null ? "" : serverInfo.address;
         }
      } catch (Throwable exception2) {
         return "";
      }
   }

   private boolean wild$isFunTimeEndpoint(String string) {
      if (string != null && !string.isBlank()) {
         String text = string.toLowerCase(Locale.ROOT);
         return text.contains("funtime") || text.contains("fun-time") || text.contains("ftmc");
      } else {
         return false;
      }
   }
}
