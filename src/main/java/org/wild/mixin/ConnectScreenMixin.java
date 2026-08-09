package org.wild.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.ConnectScreen;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.metaculture.protection.WildScreenBackdrop;
import ru.metaculture.protection.ScreenRenderDiagnostics;

@Mixin({ConnectScreen.class})
public abstract class ConnectScreenMixin extends Screen {
   @Shadow
   private Text status;
   @Shadow
   private long lastNarrationTime;

   protected ConnectScreenMixin(Text text) {
      super(text);
   }

   @Inject(
      method = {"render"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void wild$renderPremiumConnect(DrawContext drawContext, int i, int j, float f, CallbackInfo callbackInfo) {
      MinecraftClient client = MinecraftClient.getInstance();
      ConnectScreen connectScreen = (ConnectScreen)(Object)this;
      if (client == null) {
         ScreenRenderDiagnostics.invoke6("ConnectScreen.render", connectScreen, "client missing", null);
      } else {
         if (!WildScreenBackdrop.getINSTANCE().check2(client, i, j, 1.0F, connectScreen)) {
            int intValue = client.getWindow() != null ? client.getWindow().getScaledWidth() : this.width;
            int intValue2 = client.getWindow() != null ? client.getWindow().getScaledHeight() : this.height;
            drawContext.fillGradient(0, 0, intValue, intValue2, -16447732, -15658213);
            ScreenRenderDiagnostics.invoke3(connectScreen, "render.safe-fallback", "backdrop unavailable");
         } else {
            ScreenRenderDiagnostics.invoke3(connectScreen, "render.custom", "connect-status overlay");
         }

         long longValue = Util.getMeasuringTimeMs();
         if (longValue - this.lastNarrationTime > 2000L && client.getNarratorManager() != null) {
            this.lastNarrationTime = longValue;
            client.getNarratorManager().narrateSystemImmediately(Text.translatable("narrator.joining"));
         }

         super.render(drawContext, i, j, f);
         WildScreenBackdrop.getINSTANCE().invoke(client, this.status);
         callbackInfo.cancel();
      }
   }
}
