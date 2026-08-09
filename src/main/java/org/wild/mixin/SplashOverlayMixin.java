package org.wild.mixin;

import java.util.Optional;
import java.util.function.Consumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Overlay;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SplashOverlay;
import net.minecraft.resource.ResourceReload;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.metaculture.protection.MenuBackdropRenderer;
import ru.metaculture.protection.UnHook;
import ru.metaculture.protection.WildClient;

@Environment(EnvType.CLIENT)
@Mixin({SplashOverlay.class})
public abstract class SplashOverlayMixin extends Overlay {
   @Shadow
   @Final
   private MinecraftClient client;
   @Shadow
   @Final
   private ResourceReload reload;
   @Shadow
   @Final
   private Consumer<Optional<Throwable>> exceptionHandler;
   @Shadow
   @Final
   private boolean reloading;
   @Shadow
   private float progress;
   @Shadow
   private long reloadCompleteTime;
   @Shadow
   private long reloadStartTime;

   @Inject(
      method = {"render"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void wild$renderCustomLoadingOverlay(DrawContext drawContext, int i, int j, float f, CallbackInfo callbackInfo) {
      WildClient.invoke5(this.client);
      if (UnHook.active) {
         MenuBackdropRenderer.getINSTANCE().invoke2();
      } else {
         long longValue = Util.getMeasuringTimeMs();
         if (this.reloading && this.reloadStartTime == -1L) {
            this.reloadStartTime = longValue;
         }

         float floatValue = this.reloadCompleteTime > -1L ? (float)(longValue - this.reloadCompleteTime) / 1000.0F : -1.0F;
         float floatValue2 = this.reloadStartTime > -1L ? (float)(longValue - this.reloadStartTime) / 500.0F : -1.0F;
         float floatValue3 = this.reload.getProgress();
         this.progress = MathHelper.clamp(this.progress * 0.95F + floatValue3 * 0.050000012F, 0.0F, 1.0F);
         float floatValue4 = this.wild$overlayAlpha(floatValue, floatValue2);
         if (floatValue >= 0.0F && this.client.currentScreen != null) {
            this.client.currentScreen.renderWithTooltip(drawContext, i, j, f);
         } else if (this.reloading && this.client.currentScreen != null && floatValue2 < 1.0F) {
            this.client.currentScreen.renderWithTooltip(drawContext, i, j, f);
         }

         MenuBackdropRenderer menuBackdropRenderer = MenuBackdropRenderer.getINSTANCE();
         if (!menuBackdropRenderer.check()) {
            menuBackdropRenderer.invoke(this.progress, floatValue4);
            callbackInfo.cancel();
            if (floatValue >= 1.5F) {
               this.client.setOverlay(null);
               menuBackdropRenderer.invoke2();
            } else {
               if (this.reloadCompleteTime == -1L && this.reload.isComplete() && (!this.reloading || floatValue2 >= 2.0F)) {
                  this.wild$finishReload(drawContext);
               }
            }
         }
      }
   }

   @Unique
   private float wild$overlayAlpha(float f, float g) {
      if (f >= 0.0F) {
         return 1.0F - wild$smoother(MathHelper.clamp(f / 1.35F, 0.0F, 1.0F));
      } else {
         return this.reloading ? wild$smoother(MathHelper.clamp(g, 0.15F, 1.0F)) : 1.0F;
      }
   }

   @Unique
   private static float wild$smoother(float f) {
      float floatValue5 = MathHelper.clamp(f, 0.0F, 1.0F);
      return floatValue5 * floatValue5 * floatValue5 * (floatValue5 * (floatValue5 * 6.0F - 15.0F) + 10.0F);
   }

   @Unique
   private void wild$finishReload(DrawContext drawContext) {
      try {
         this.reload.throwException();
         this.exceptionHandler.accept(Optional.empty());
      } catch (Throwable exception) {
         this.exceptionHandler.accept(Optional.of(exception));
      }

      this.reloadCompleteTime = Util.getMeasuringTimeMs();
      Screen screen = this.client.currentScreen;
      if (screen != null) {
         screen.init(this.client, drawContext.getScaledWindowWidth(), drawContext.getScaledWindowHeight());
      }
   }
}
