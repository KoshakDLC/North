package org.wild.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.LevelLoadingScreen;
import net.minecraft.server.WorldGenerationProgressTracker;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.metaculture.protection.WildScreenBackdrop;
import ru.metaculture.protection.ScreenRenderDiagnostics;

@Mixin({LevelLoadingScreen.class})
public abstract class LevelLoadingScreenMixin extends Screen {
   @Shadow
   @Final
   private WorldGenerationProgressTracker progressProvider;
   @Shadow
   private long lastNarrationTime;

   protected LevelLoadingScreenMixin(Text text) {
      super(text);
   }

   @Inject(
      method = {"render"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void wild$renderPremiumLevelLoading(DrawContext drawContext, int i, int j, float f, CallbackInfo callbackInfo) {
      MinecraftClient client = MinecraftClient.getInstance();
      LevelLoadingScreen levelLoadingScreen = (LevelLoadingScreen)(Object)this;
      if (client == null) {
         ScreenRenderDiagnostics.invoke6("LevelLoadingScreen.render", levelLoadingScreen, "client missing", null);
      } else if (!WildScreenBackdrop.getINSTANCE().check2(client, i, j, 1.0F, levelLoadingScreen)) {
         ScreenRenderDiagnostics.invoke3(levelLoadingScreen, "render.vanilla-fallback", "backdrop unavailable");
      } else {
         long longValue = Util.getMeasuringTimeMs();
         if (longValue - this.lastNarrationTime > 2000L) {
            this.lastNarrationTime = longValue;
            this.narrateScreenIfNarrationEnabled(true);
         }

         WildScreenBackdrop.getINSTANCE().invoke2(client, this.progressProvider);
         ScreenRenderDiagnostics.invoke3(levelLoadingScreen, "render.custom", "level-loading overlay");
         callbackInfo.cancel();
      }
   }
}
