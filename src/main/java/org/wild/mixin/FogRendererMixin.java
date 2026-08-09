package org.wild.mixin;

import net.minecraft.client.render.fog.FogRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import ru.metaculture.protection.AtmoDawnFog;
import ru.metaculture.protection.Removals;

@Mixin({FogRenderer.class})
public class FogRendererMixin {
   @ModifyArgs(
      method = {"applyFog(Lnet/minecraft/client/render/Camera;IZLnet/minecraft/client/render/RenderTickCounter;FLnet/minecraft/client/world/ClientWorld;)Lorg/joml/Vector4f;"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/render/fog/FogRenderer;applyFog(Ljava/nio/ByteBuffer;ILorg/joml/Vector4f;FFFFFF)V"
      )
   )
   private void wild$eraseWorldFog(Args args) {
      if (Removals.check("Туман") || AtmoDawnFog.check()) {
         args.set(3, Float.MAX_VALUE);
         args.set(4, Float.MAX_VALUE);
         args.set(5, Float.MAX_VALUE);
         args.set(6, Float.MAX_VALUE);
      }
   }
}
