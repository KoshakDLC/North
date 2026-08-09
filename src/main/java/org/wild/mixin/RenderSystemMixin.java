package org.wild.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.util.tracy.TracyFrameCapturer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.metaculture.protection.GlStateInspector;
import ru.metaculture.protection.RenderDiagnosticsTracker;
import ru.metaculture.protection.RenderStateMaintenance;
import ru.metaculture.protection.PrismaticChamsRenderer;
import ru.metaculture.protection.WildClient;

@Mixin({RenderSystem.class})
public class RenderSystemMixin {
   @Inject(
      method = {"flipFrame(JLnet/minecraft/client/util/tracy/TracyFrameCapturer;)V"},
      at = {@At("HEAD")}
   )
   private static void flipFrame(long l, TracyFrameCapturer tracyFrameCapturer, CallbackInfo callbackInfo) {
      WildClient.invoke18();
   }

   @Inject(
      method = {"flipFrame(JLnet/minecraft/client/util/tracy/TracyFrameCapturer;)V"},
      at = {@At("TAIL")}
   )
   private static void wild$clearChamsUniforms(long l, TracyFrameCapturer tracyFrameCapturer, CallbackInfo callbackInfo) {
      PrismaticChamsRenderer.resetFrameState();
      RenderStateMaintenance.invoke4();
      int intValue = GlStateInspector.pollGlError();
      if (intValue != 0) {
         RenderDiagnosticsTracker.getInstance().invoke20("RenderSystem.flipFrame", intValue);
      }
   }
}
