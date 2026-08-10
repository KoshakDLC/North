package org.wild.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DownloadingTerrainScreen;
import net.minecraft.client.gui.screen.ProgressScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.DownloadingTerrainScreen.WorldEntryReason;
import net.minecraft.client.gui.screen.multiplayer.ConnectScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.world.LevelLoadingScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.metaculture.protection.BlurRenderer;
import ru.metaculture.protection.Event;
import ru.metaculture.protection.EventManager;
import ru.metaculture.protection.HitBox;
import ru.metaculture.protection.MainMenuScreen;
import ru.metaculture.protection.NoDelay;
import ru.metaculture.protection.RenderDiagnosticsTracker;
import ru.metaculture.protection.WorldJoinEvent;
import ru.metaculture.protection.ScreenOpenEvent;
import ru.metaculture.protection.WorldReadyEvent;
import ru.metaculture.protection.ClientTickEvent;
import ru.metaculture.protection.RotationSmoothing;
import ru.metaculture.protection.AltVaultScreen;
import ru.metaculture.protection.BackdropScreen;
import ru.metaculture.protection.ServerConnector;
import ru.metaculture.protection.WeakGlCompatibility;
import ru.metaculture.protection.ScreenRenderDiagnostics;
import ru.metaculture.protection.ScreenTransitionRenderer;
import ru.metaculture.protection.ProtectionHandler;
import ru.metaculture.protection.PvPSafe;
import ru.metaculture.protection.UnHook;
import ru.metaculture.protection.WildClient;
import ru.metaculture.protection.WildMultiplayerScreen;

@Environment(EnvType.CLIENT)
@Mixin({MinecraftClient.class})
public abstract class MinecraftClientMixin {
   @Shadow
   private int itemUseCooldown;
   @Shadow
   private int attackCooldown;
   @Unique
   private boolean wild$hideOpenedScreen;
   @Unique
   private Screen wild$diagPreviousScreen;

   @Inject(
      method = {"stop"},
      at = {@At("HEAD")}
   )
   private void wild$onStop(CallbackInfo callbackInfo) {
      if (WildClient.isInitialized()) {
         ScreenTransitionRenderer.getINSTANCE().invoke11();
         WildClient.invoke11();
      }
   }

   @Inject(
      method = {"tick"},
      at = {@At("HEAD")}
   )
   private void wild$coreTickHead(CallbackInfo callbackInfo) {
      RenderDiagnosticsTracker.getInstance().invoke();
   }

   @Inject(
      method = {"tick"},
      at = {@At("HEAD")}
   )
   private void initRenderer(CallbackInfo callbackInfo) {
      ProtectionHandler.checkAccess();
      MinecraftClient client = (MinecraftClient)(Object)this;
      if (WildClient.isInitialized()) {
         ServerConnector.invoke4(client);
         ServerConnector.invoke2(client);
         AltVaultScreen.invoke(client);
      }
   }

   @Inject(
      method = {"tick"},
      at = {@At("TAIL")}
   )
   private void wild$coreTickTail(CallbackInfo callbackInfo) {
      MinecraftClient.getInstance().getWindow().setTitle("North");
      RenderDiagnosticsTracker.getInstance().invoke2();
   }

   @Inject(
      method = {"tick"},
      at = {@At("TAIL")}
   )
   private void publishClientTick(CallbackInfo callbackInfo) {
      if (WildClient.isInitialized()) {
         MinecraftClient client2 = (MinecraftClient)(Object)this;
         if (!client2.isPaused()) {
            ClientPlayerEntity clientPlayerEntity = client2.player;
            ClientWorld clientWorld2 = client2.world;
            if (clientPlayerEntity != null && clientWorld2 != null) {
               try {
                  EventManager.post((Event)(new ClientTickEvent(client2)));
               } catch (Throwable exception) {
               }
            }
         }
      }
   }

   @Inject(
      method = {"joinWorld(Lnet/minecraft/client/world/ClientWorld;Lnet/minecraft/client/gui/screen/DownloadingTerrainScreen$WorldEntryReason;)V"},
      at = {@At("TAIL")}
   )
   public void loadWorld(ClientWorld clientWorld, WorldEntryReason worldEntryReason, CallbackInfo callbackInfo) {
      ServerConnector.invoke3();
      if (clientWorld != null) {
         try {
            EventManager.post((Event)(new WorldJoinEvent()));
            EventManager.post((Event)(new WorldReadyEvent()));
         } catch (Throwable exception2) {
         }
      }
   }

   @Inject(
      method = {"onResolutionChanged"},
      at = {@At("TAIL")}
   )
   private void wild$onResolutionChanged(CallbackInfo callbackInfo) {
      MinecraftClient client3 = (MinecraftClient)(Object)this;
      if (client3.getWindow() == null) {
         WildClient.invoke(0, 0);
      } else {
         WildClient.invoke(client3.getWindow().getFramebufferWidth(), client3.getWindow().getFramebufferHeight());
      }
   }

   @Inject(
      method = {"onWindowFocusChanged"},
      at = {@At("HEAD")}
   )
   private void wild$onWindowFocusChanged(boolean bl, CallbackInfo callbackInfo) {
      MinecraftClient client4 = (MinecraftClient)(Object)this;
      if (!bl && client4.mouse != null) {
         client4.mouse.unlockCursor();
      }

      WildClient.invoke2(bl);
   }

   @Inject(
      method = {"setScreen"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void wild$replaceTitleScreen(Screen screen, CallbackInfo callbackInfo) {
      if (WildClient.isInitialized() && !UnHook.active) {
         if (screen instanceof TitleScreen && !(screen instanceof MainMenuScreen)) {
            MainMenuScreen mainMenuScreen;
            try {
               mainMenuScreen = new MainMenuScreen();
            } catch (Throwable exception3) {
               return;
            }

            callbackInfo.cancel();
            ((MinecraftClient)(Object)this).setScreen(mainMenuScreen);
         } else if (screen instanceof MultiplayerScreen && !(screen instanceof WildMultiplayerScreen)) {
            MinecraftClient client5 = (MinecraftClient)(Object)this;

            WildMultiplayerScreen wildMultiplayerScreen;
            try {
               Object object = client5.currentScreen instanceof MainMenuScreen ? client5.currentScreen : new MainMenuScreen();
               wildMultiplayerScreen = new WildMultiplayerScreen((Screen)object);
            } catch (Throwable exception4) {
               return;
            }

            callbackInfo.cancel();
            client5.setScreen(wildMultiplayerScreen);
         } else {
            if (screen != null) {
               ScreenOpenEvent screenOpenEvent = new ScreenOpenEvent(screen);
               EventManager.post((Event)screenOpenEvent);
               if (screenOpenEvent.isInvalidated()) {
                  callbackInfo.cancel();
                  return;
               }

               if (screenOpenEvent.isFlag()) {
                  this.wild$hideOpenedScreen = true;
               }
            }
         }
      }
   }

   @Inject(
      method = {"setScreen"},
      at = {@At("TAIL")}
   )
   private void wild$logScreenTransition(Screen screen, CallbackInfo callbackInfo) {
      if (WildClient.isInitialized()) {
         ScreenRenderDiagnostics.invoke(this.wild$diagPreviousScreen, screen);
      }
   }

   @Inject(
      method = {"setScreen"},
      at = {@At("TAIL")}
   )
   private void wild$hideOpenedScreen(Screen screen, CallbackInfo callbackInfo) {
      if (this.wild$hideOpenedScreen) {
         this.wild$hideOpenedScreen = false;
         MinecraftClient client6 = (MinecraftClient)(Object)this;
         if (screen != null && client6.currentScreen == screen) {
            client6.currentScreen = null;
            if (client6.mouse != null) {
               client6.mouse.lockCursor();
            }
         }
      }
   }

   @Inject(
      method = {"setScreen"},
      at = {@At("HEAD")}
   )
   private void wild$captureScreenTransition(Screen screen, CallbackInfo callbackInfo) {
      this.wild$diagPreviousScreen = ((MinecraftClient)(Object)this).currentScreen;
      MinecraftClient client7 = (MinecraftClient)(Object)this;
      if (WildClient.isInitialized() && !UnHook.active) {
         BlurRenderer.getINSTANCE().invoke9();
      }

      if (WildClient.isInitialized() && !UnHook.active) {
         if (!(screen instanceof TitleScreen) || screen instanceof MainMenuScreen) {
            if (!(screen instanceof MultiplayerScreen) || screen instanceof WildMultiplayerScreen) {
               if (wild$isLoadingScreen(screen)) {
                  ScreenTransitionRenderer.getINSTANCE().invoke11();
               } else if (client7.world != null) {
                  ScreenTransitionRenderer.getINSTANCE().invoke11();
               } else if (wild$isLoadingScreen(client7.currentScreen)) {
                  ScreenTransitionRenderer.getINSTANCE().invoke11();
               } else {
                  Screen screen2 = client7.currentScreen;
                  if (!(screen2 instanceof BackdropScreen) && !(screen instanceof BackdropScreen)) {
                     try {
                        ScreenTransitionRenderer.getINSTANCE().invoke(screen2, screen);
                     } catch (Throwable exception5) {
                        ScreenTransitionRenderer.getINSTANCE().invoke11();
                     }
                  } else {
                     ScreenTransitionRenderer.getINSTANCE().invoke11();
                  }
               }
            }
         }
      }
   }

   @Inject(
      method = {"setScreen"},
      at = {@At("TAIL")}
   )
   private void wild$restoreFramebufferAfterSetScreen(Screen screen, CallbackInfo callbackInfo) {
      if (WildClient.isInitialized() && !UnHook.active && screen != null) {
         if (!(screen instanceof BackdropScreen)) {
            WeakGlCompatibility.invoke2((MinecraftClient)(Object)this);
         }
      }
   }

   private static boolean wild$isLoadingScreen(Screen screen) {
      return screen instanceof LevelLoadingScreen
         || screen instanceof DownloadingTerrainScreen
         || screen instanceof ConnectScreen
         || screen instanceof ProgressScreen;
   }

   @Inject(
      method = {"disconnect(Lnet/minecraft/client/gui/screen/Screen;Z)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void wild$blockPvpSafeDisconnect(Screen screen, boolean bl, CallbackInfo callbackInfo) {
      if (PvPSafe.check5(bl)) {
         callbackInfo.cancel();
      }
   }

   @Inject(
      method = {"doAttack"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onDoAttackHitbox(CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
      MinecraftClient client8 = MinecraftClient.getInstance();
      if (client8 != null && client8.player != null && client8.world != null && client8.interactionManager != null) {
         if (WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null) {
            HitBox hitBox = (HitBox)WildClient.INSTANCE.moduleManager.findModule(HitBox.class);
            if (hitBox != null && hitBox.enabled && HitBox.rezhim.is("Легит")) {
               LivingEntity livingEntity = hitBox.resolve();
               if (livingEntity != null) {
                  HitBox.setLivingEntity(livingEntity);
                  RotationSmoothing.invoke9(livingEntity, true, HitBox.rezhimSnapa.getValue());
                  callbackInfoReturnable.setReturnValue(true);
               }
            }
         }
      }
   }

   @Inject(
      method = {"doAttack"},
      at = {@At("RETURN")}
   )
   private void onDoAttackNoDelay(CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
      if (WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null) {
         NoDelay noDelay = (NoDelay)WildClient.INSTANCE.moduleManager.findModule(NoDelay.class);
         if (noDelay != null && noDelay.enabled && NoDelay.lkm.isEnabled()) {
            this.attackCooldown = (int)NoDelay.lkmZaderzhka.getValue();
         }
      }
   }

   @Inject(
      method = {"doItemUse"},
      at = {@At("RETURN")}
   )
   private void onDoItemUseNoDelay(CallbackInfo callbackInfo) {
      if (WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null) {
         NoDelay noDelay2 = (NoDelay)WildClient.INSTANCE.moduleManager.findModule(NoDelay.class);
         if (noDelay2 != null && noDelay2.enabled && NoDelay.pkm.isEnabled()) {
            this.itemUseCooldown = (int)NoDelay.pkmZaderzhka.getValue();
         }
      }
   }
}
