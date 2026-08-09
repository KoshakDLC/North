package org.wild.mixin;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.font.TextRenderer.TextLayerType;
import net.minecraft.client.render.VertexConsumerProvider;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.metaculture.protection.RenderStateMaintenance;
import ru.metaculture.protection.ProtectInfo;

@Mixin({TextRenderer.class})
public class TextRendererMixin {
   @Inject(
      method = {"draw(Ljava/lang/String;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;II)V"},
      at = {@At("HEAD")}
   )
   private void wild$guardVanillaFontState(
      String string,
      float f,
      float g,
      int i,
      boolean bl,
      Matrix4f matrix4f,
      VertexConsumerProvider vertexConsumerProvider,
      TextLayerType textLayerType,
      int j,
      int k,
      CallbackInfo callbackInfo
   ) {
      RenderStateMaintenance.invoke();
   }

   @Inject(
      method = {"draw(Ljava/lang/String;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;II)V"},
      at = {@At("RETURN")}
   )
   private void wild$restoreVanillaFontState(
      String string,
      float f,
      float g,
      int i,
      boolean bl,
      Matrix4f matrix4f,
      VertexConsumerProvider vertexConsumerProvider,
      TextLayerType textLayerType,
      int j,
      int k,
      CallbackInfo callbackInfo
   ) {
      RenderStateMaintenance.invoke2();
   }

   @ModifyVariable(
      method = {"draw(Ljava/lang/String;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;II)V"},
      at = @At("HEAD"),
      argsOnly = true,
      ordinal = 0
   )
   private String litka$maskGlobalString(String string) {
      return string == null ? null : ProtectInfo.resolve7(string);
   }

   @ModifyVariable(
      method = {"getWidth(Ljava/lang/String;)I"},
      at = @At("HEAD"),
      argsOnly = true,
      ordinal = 0
   )
   private String litka$maskWidthString(String string) {
      return string == null ? null : ProtectInfo.resolve7(string);
   }
}
