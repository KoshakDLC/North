package org.wild.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.ConnectScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.metaculture.protection.MainMenuScreen;
import ru.metaculture.protection.ServerConnector;
import ru.metaculture.protection.PvPSafe;
import ru.metaculture.protection.UnHook;
import ru.metaculture.protection.WildMultiplayerScreen;

@Mixin({GameMenuScreen.class})
public abstract class GameMenuScreenMixin extends Screen {
   @Shadow
   private ButtonWidget exitButton;
   @Unique
   private ButtonWidget wild$reconnectButton;

   protected GameMenuScreenMixin(Text text) {
      super(text);
   }

   @Inject(
      method = {"init"},
      at = {@At("TAIL")}
   )
   private void wild$disablePvpSafeDisconnectButton(CallbackInfo callbackInfo) {
      this.wild$initReconnectButton();
      if (this.exitButton != null && PvPSafe.check()) {
         this.exitButton.active = false;
      }

      if (this.wild$reconnectButton != null && PvPSafe.check()) {
         this.wild$reconnectButton.active = false;
      }
   }

   @Inject(
      method = {"render"},
      at = {@At("HEAD")}
   )
   private void wild$keepPvpSafeDisconnectButtonDisabled(DrawContext drawContext, int i, int j, float f, CallbackInfo callbackInfo) {
      if (this.exitButton != null && PvPSafe.check()) {
         this.exitButton.active = false;
      }

      if (this.wild$reconnectButton != null) {
         boolean flag = this.wild$hasReconnectTarget();
         this.wild$reconnectButton.visible = flag;
         this.wild$reconnectButton.active = flag && this.wild$canReconnect();
      }
   }

   @Unique
   private void wild$initReconnectButton() {
      if (this.exitButton != null && this.wild$hasReconnectTarget()) {
         int intValue = this.exitButton.getX();
         int intValue2 = this.exitButton.getY();
         this.exitButton.setDimensionsAndPosition(100, this.exitButton.getHeight(), intValue, intValue2);
         this.wild$reconnectButton = ButtonWidget.builder(Text.literal("Перезаход"), buttonWidget -> this.wild$reconnect())
            .dimensions(intValue + 104, intValue2, 100, this.exitButton.getHeight())
            .build();
         this.addDrawableChild(this.wild$reconnectButton);
      }
   }

   @Unique
   private boolean wild$canReconnect() {
      return this.wild$hasReconnectTarget() && !PvPSafe.check();
   }

   @Unique
   private boolean wild$hasReconnectTarget() {
      MinecraftClient client = this.client;
      if (!UnHook.active && client != null && !client.isInSingleplayer()) {
         ServerInfo serverInfo = client.getCurrentServerEntry();
         return serverInfo != null && serverInfo.address != null && !serverInfo.address.isBlank();
      } else {
         return false;
      }
   }

   @Unique
   private void wild$reconnect() {
      MinecraftClient client2 = this.client;
      if (this.wild$canReconnect()) {
         ServerInfo serverInfo2 = client2.getCurrentServerEntry();
         if (serverInfo2 != null && serverInfo2.address != null && !serverInfo2.address.isBlank()) {
            ServerInfo serverInfo3 = new ServerInfo(serverInfo2.name, serverInfo2.address, serverInfo2.getServerType());
            serverInfo3.copyWithSettingsFrom(serverInfo2);
            ServerAddress serverAddress = ServerAddress.parse(serverInfo3.address);
            ServerConnector.invoke3();
            GameMenuScreen.disconnect(client2, ClientWorld.QUITTING_MULTIPLAYER_TEXT);
            ConnectScreen.connect(new WildMultiplayerScreen(new MainMenuScreen()), client2, serverAddress, serverInfo3, false, null);
         }
      }
   }
}
