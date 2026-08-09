package org.wild.mixin;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.vertex.VertexFormat.IndexType;
import java.util.function.Consumer;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.render.BuiltBuffer;
import net.minecraft.client.render.RenderLayer.MultiPhase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import ru.metaculture.protection.MultiPhaseRenderPass;

@Mixin({MultiPhase.class})
public abstract class RenderLayerMultiPhaseMixin implements MultiPhaseRenderPass {
   @Unique
   private Consumer<RenderPass> renderPassSetup;

   @Override
   public MultiPhase withRenderPassSetup(Consumer<RenderPass> consumer) {
      this.renderPassSetup = consumer;
      return (MultiPhase)(Object)this;
   }

   @Inject(
      method = {"draw"},
      at = {@At(
         value = "INVOKE",
         target = "Lcom/mojang/blaze3d/systems/RenderPass;drawIndexed(IIII)V"
      )},
      locals = LocalCapture.CAPTURE_FAILHARD
   )
   private void applyRenderPassSetup(
      BuiltBuffer builtBuffer,
      CallbackInfo callbackInfo,
      GpuBufferSlice gpuBufferSlice,
      BuiltBuffer builtBuffer2,
      GpuBuffer gpuBuffer,
      GpuBuffer gpuBuffer2,
      IndexType indexType,
      Framebuffer framebuffer,
      GpuTextureView gpuTextureView,
      GpuTextureView gpuTextureView2,
      RenderPass renderPass
   ) {
      if (this.renderPassSetup != null) {
         this.renderPassSetup.accept(renderPass);
      }
   }
}
