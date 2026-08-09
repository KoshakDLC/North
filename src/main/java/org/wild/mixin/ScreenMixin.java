package org.wild.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import java.awt.Color;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.DownloadingTerrainScreen;
import net.minecraft.client.gui.screen.ProgressScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.text.Style;
import net.minecraft.text.ClickEvent.RunCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.lwjgl.opengl.GL11;
import ru.metaculture.protection.BlurRenderer;
import ru.metaculture.protection.RenderDiagnosticsTracker;
import ru.metaculture.protection.BackdropScreen;
import ru.metaculture.protection.WildScreenBackdrop;
import ru.metaculture.protection.ThemePalette;
import ru.metaculture.protection.WeakGlCompatibility;
import ru.metaculture.protection.ScreenRenderDiagnostics;
import ru.metaculture.protection.Theme;
import ru.metaculture.protection.WildClient;

@Mixin({Screen.class})
public class ScreenMixin {
   @Unique
   private static final ThemePalette wild$palette = ThemePalette.resolve2();
   @Unique
   private boolean wild$guiRippleCapture;
   @Unique
   private static boolean wild$panoramaNoticeLogged;

   @Inject(
      method = {"handleTextClick"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void litka$interceptClientCommands(Style style, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
      if (style != null && style.getClickEvent() instanceof RunCommand runCommand) {
         String text = runCommand.command();
         if (text != null && text.startsWith(WildClient.INSTANCE.getCommandPrefix())) {
            WildClient.INSTANCE.getCommandManager().execute(text);
            callbackInfoReturnable.setReturnValue(true);
         }
      }
   }

   @Inject(
      method = {"render"},
      at = {@At("HEAD")}
   )
   private void wild$diagRenderHead(DrawContext drawContext, int i, int j, float f, CallbackInfo callbackInfo) {
      RenderDiagnosticsTracker.getInstance().invoke5();
      Screen screen2 = (Screen)(Object)this;
      if (!(screen2 instanceof BackdropScreen)) {
         WeakGlCompatibility.invoke2(MinecraftClient.getInstance());
      }

      wild$probeGlErrors("screen-render-head");

      ScreenRenderDiagnostics.invoke2(screen2, "render.head");
   }

   @Inject(
      method = {"render"},
      at = {@At("TAIL")}
   )
   private void wild$diagRenderTail(DrawContext drawContext, int i, int j, float f, CallbackInfo callbackInfo) {
      wild$probeGlErrors("screen-render-tail");
      RenderDiagnosticsTracker.getInstance().invoke6();
      ScreenRenderDiagnostics.invoke2((Screen)(Object)this, "render.tail");
   }

   @Inject(
      method = {"renderBackground"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void wild$renderThemedVanillaBackdrop(DrawContext drawContext, int i, int j, float f, CallbackInfo callbackInfo) {
      Screen screen3 = (Screen)(Object)this;
      if (!wild$usesThemedBackdrop(screen3)) {
         ScreenRenderDiagnostics.invoke2(screen3, "renderBackground.vanilla");
      } else {
         MinecraftClient client = MinecraftClient.getInstance();
         if (client != null && client.getWindow() != null) {
            if (WildScreenBackdrop.getINSTANCE().check2(client, i, j, 1.0F, screen3)) {
               wild$probeGlErrors("wild-screen-backdrop");
               ScreenRenderDiagnostics.invoke3(screen3, "renderBackground.backdrop", "shader-backdrop");
               callbackInfo.cancel();
            } else {
               wild$drawThemedBackdrop(drawContext, client.getWindow().getScaledWidth(), client.getWindow().getScaledHeight());
               ScreenRenderDiagnostics.invoke3(screen3, "renderBackground.backdrop", "gradient-fallback");
               callbackInfo.cancel();
            }
         } else {
            ScreenRenderDiagnostics.invoke6("renderBackground", screen3, "client or window missing", null);
         }
      }
   }

   @WrapMethod(
      method = {"renderPanoramaBackground"}
   )
   private void wild$guardPanorama(DrawContext drawContext, float f, Operation<Void> operation) {
      try {
         operation.call(new Object[]{drawContext, f});
      } catch (Throwable exception) {
         if (!wild$panoramaNoticeLogged) {
            wild$panoramaNoticeLogged = true;
            ScreenRenderDiagnostics.invoke6("renderPanoramaBackground", (Screen)(Object)this, "vanilla panorama failed -> themed backdrop", exception);
         }

         MinecraftClient client2 = MinecraftClient.getInstance();
         if (client2 != null && client2.getWindow() != null) {
            wild$drawThemedBackdrop(drawContext, client2.getWindow().getScaledWidth(), client2.getWindow().getScaledHeight());
         }
      }
   }

   @Inject(
      method = {"renderWithTooltip"},
      at = {@At("HEAD")}
   )
   private void wild$beginGuiRipplePass(DrawContext drawContext, int i, int j, float f, CallbackInfo callbackInfo) {
      Screen screen4 = (Screen)(Object)this;
      MinecraftClient client3 = MinecraftClient.getInstance();
      if (client3 != null && client3.getWindow() != null) {
         BlurRenderer blurRenderer = BlurRenderer.getINSTANCE();
         this.wild$guiRippleCapture = blurRenderer.check(screen4) && blurRenderer.check3(client3.getWindow().getFramebufferWidth(), client3.getWindow().getFramebufferHeight());
         if (this.wild$guiRippleCapture) {
            ScreenRenderDiagnostics.invoke2(screen4, "renderWithTooltip.ripple.begin");
         }
      } else {
         this.wild$guiRippleCapture = false;
      }
   }

   @Inject(
      method = {"renderWithTooltip"},
      at = {@At("TAIL")}
   )
   private void wild$endGuiRipplePass(DrawContext drawContext, int i, int j, float f, CallbackInfo callbackInfo) {
      if (!this.wild$guiRippleCapture) {
         ScreenRenderDiagnostics.invoke2((Screen)(Object)this, "renderWithTooltip.tail");
      } else {
         this.wild$guiRippleCapture = false;

         try {
            BlurRenderer.getINSTANCE().invoke5();
            ScreenRenderDiagnostics.invoke2((Screen)(Object)this, "renderWithTooltip.ripple.end");
         } catch (Throwable exception2) {
            ScreenRenderDiagnostics.invoke6("gui-ripple", (Screen)(Object)this, "endPass failed", exception2);
         }

         ScreenRenderDiagnostics.invoke2((Screen)(Object)this, "renderWithTooltip.tail");
      }
   }

   @Unique
   private static boolean wild$usesThemedBackdrop(Screen screen) {
      return screen instanceof SelectWorldScreen
         || screen instanceof CreateWorldScreen
         || screen instanceof DownloadingTerrainScreen
         || screen instanceof ProgressScreen;
   }

   @Unique
   private static void wild$drawThemedBackdrop(DrawContext drawContext, int i, int j) {
      Theme theme = WildClient.INSTANCE != null && WildClient.INSTANCE.themeManager != null ? WildClient.INSTANCE.themeManager.getTheme() : Theme.AURORA;
      ThemePalette.Swatch swatch = wild$palette.resolve3(theme);
      boolean flag = false;
      int intValue;
      int intValue2;
      if (swatch != null) {
         intValue = swatch.getIntValue();
         intValue2 = swatch.getIntValue2();
      } else {
         Color color = theme.getColor();
         intValue = 0xFF000000 | color.getRGB() & 16777215;
         float[] floatValues = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
         intValue2 = 0xFF000000 | Color.HSBtoRGB((floatValues[0] + 0.075F) % 1.0F, Math.min(1.0F, floatValues[1] * 1.08F), Math.min(1.0F, floatValues[2] * 1.18F)) & 16777215;
      }

      int intValue3 = flag ? wild$mix(-197121, intValue, 0.055F) : wild$mix(-16447732, intValue, 0.035F);
      int intValue4 = flag ? wild$mix(-3853, intValue2, 0.09F) : wild$mix(-15658213, intValue2, 0.06F);
      drawContext.fillGradient(0, 0, i, j, intValue3, intValue4);
      int intValue5 = Math.max(5, Math.min(9, j / 72));

      for (int intValue6 = 0; intValue6 < intValue5; intValue6++) {
         float floatValue = (intValue6 + 1.0F) / (intValue5 + 1.0F);
         int intValue7 = Math.round(j * floatValue - j * 0.045F);
         int intValue8 = Math.max(18, Math.round(j * (flag ? 0.045F : 0.06F)));
         int intValue9 = wild$withAlpha(wild$mix(intValue, -1, flag ? 0.78F : 0.15F), flag ? 18 : 24);
         int intValue10 = wild$withAlpha(wild$mix(intValue2, -1, flag ? 0.72F : 0.12F), 0);
         drawContext.fillGradient(0, Math.max(0, intValue7), i, Math.min(j, intValue7 + intValue8), intValue9, intValue10);
      }

      if (flag) {
         drawContext.fillGradient(0, 0, i, Math.max(24, j / 8), 570425344, 0);
         drawContext.fillGradient(0, Math.max(0, j - j / 5), i, j, 0, 285212671);
      } else {
         drawContext.fillGradient(0, 0, i, j, 570425344, 1711276032);
      }
   }

   @Unique
   private static int wild$withAlpha(int i, int j) {
      return (Math.max(0, Math.min(255, j)) & 0xFF) << 24 | i & 16777215;
   }

   @Unique
   private static int wild$mix(int i, int j, float f) {
      float floatValue2 = Math.max(0.0F, Math.min(1.0F, f));
      int intValue11 = Math.round(wild$channel(i, 24) + (wild$channel(j, 24) - wild$channel(i, 24)) * floatValue2);
      int intValue12 = Math.round(wild$channel(i, 16) + (wild$channel(j, 16) - wild$channel(i, 16)) * floatValue2);
      int intValue13 = Math.round(wild$channel(i, 8) + (wild$channel(j, 8) - wild$channel(i, 8)) * floatValue2);
      int intValue14 = Math.round(wild$channel(i, 0) + (wild$channel(j, 0) - wild$channel(i, 0)) * floatValue2);
      return intValue11 << 24 | intValue12 << 16 | intValue13 << 8 | intValue14;
   }

   @Unique
   private static int wild$channel(int i, int j) {
      return i >> j & 0xFF;
   }

   @Unique
   private static void wild$probeGlErrors(String stage) {
      int error = GL11.glGetError();
      if (error != 0) {
         System.err.println("[WildGLProbe] stage=" + stage + " error=0x" + Integer.toHexString(error));
      }
   }
}
