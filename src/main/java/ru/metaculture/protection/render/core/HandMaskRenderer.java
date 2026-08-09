package ru.metaculture.protection;

import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTextureView;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SequencedMap;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.SimpleFramebuffer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexConsumers;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.texture.GlTexture;
import net.minecraft.client.util.BufferAllocator;
import net.minecraft.util.Hand;

public final class HandMaskRenderer {
   private static final int INT_VALUE = 262144;
   private static final HandMaskRenderer INSTANCE = new HandMaskRenderer();
   private final Map<Hand, HandMaskRenderer.HandMaskRendererState2> valuesByKey = new EnumMap<>(Hand.class);
   private boolean flag;
   private int intValue = -1;
   private int intValue2 = -1;

   private HandMaskRenderer() {
      this.valuesByKey.put(Hand.MAIN_HAND, new HandMaskRenderer.HandMaskRendererState2("wild_hands_main"));
      this.valuesByKey.put(Hand.OFF_HAND, new HandMaskRenderer.HandMaskRendererState2("wild_hands_off"));
   }

   public static HandMaskRenderer getINSTANCE() {
      return INSTANCE;
   }

   public void invoke(boolean bl, boolean bl2, int i, int j) {
      this.flag = false;
      this.valuesByKey.values().forEach(HandMaskRenderer.HandMaskRendererState2::invoke2);
      if ((bl || bl2) && i > 0 && j > 0 && !IrisCompatibility.isRenderingShadowPass()) {
         this.intValue = i;
         this.intValue2 = j;
         HandMaskRenderer.HandMaskRendererState2 handMaskRendererState2 = this.valuesByKey.get(Hand.MAIN_HAND);
         HandMaskRenderer.HandMaskRendererState2 handMaskRendererState22 = this.valuesByKey.get(Hand.OFF_HAND);

         try {
            if (bl && handMaskRendererState2.check(i, j)) {
               handMaskRendererState2.invoke();
               this.flag = true;
            }

            if (bl2 && handMaskRendererState22.check(i, j)) {
               handMaskRendererState22.invoke();
               this.flag = true;
            }
         } catch (RuntimeException exception) {
            this.flag = false;
            this.valuesByKey.values().forEach(HandMaskRenderer.HandMaskRendererState2::invoke2);
         }
      }
   }

   public VertexConsumerProvider resolve(Hand hand, VertexConsumerProvider vertexConsumerProvider) {
      HandMaskRenderer.HandMaskRendererState2 handMaskRendererState23 = this.valuesByKey.get(hand);
      return this.flag && handMaskRendererState23 != null && handMaskRendererState23.flag && vertexConsumerProvider != null && !IrisCompatibility.isRenderingShadowPass()
         ? renderLayer -> VertexConsumers.union(vertexConsumerProvider.getBuffer(renderLayer), handMaskRendererState23.handMaskRendererState.handMaskRendererResources.resolve(renderLayer))
         : vertexConsumerProvider;
   }

   public VertexConsumerProvider resolve2(Hand hand, VertexConsumerProvider vertexConsumerProvider) {
      HandMaskRenderer.HandMaskRendererState2 handMaskRendererState24 = this.valuesByKey.get(hand);
      return this.flag && handMaskRendererState24 != null && handMaskRendererState24.flag && vertexConsumerProvider != null && !IrisCompatibility.isRenderingShadowPass()
         ? renderLayer -> VertexConsumers.union(vertexConsumerProvider.getBuffer(renderLayer), handMaskRendererState24.handMaskRendererState2.handMaskRendererResources.resolve(renderLayer))
         : vertexConsumerProvider;
   }

   public void invoke2(Hand hand) {
      HandMaskRenderer.HandMaskRendererState2 handMaskRendererState25 = this.valuesByKey.get(hand);
      if (this.flag && handMaskRendererState25 != null && handMaskRendererState25.flag) {
         handMaskRendererState25.handMaskRendererState.invoke2();
         handMaskRendererState25.handMaskRendererState2.invoke2();
      }
   }

   public boolean check(Hand hand) {
      HandMaskRenderer.HandMaskRendererState2 handMaskRendererState26 = this.valuesByKey.get(hand);
      return handMaskRendererState26 != null && handMaskRendererState26.handMaskRendererState.flag;
   }

   public int compute(Hand hand) {
      HandMaskRenderer.HandMaskRendererState2 handMaskRendererState27 = this.valuesByKey.get(hand);
      return handMaskRendererState27 != null && handMaskRendererState27.handMaskRendererState.flag ? compute4(handMaskRendererState27.handMaskRendererState.simpleFramebuffer, false) : 0;
   }

   public int compute2(Hand hand) {
      HandMaskRenderer.HandMaskRendererState2 handMaskRendererState28 = this.valuesByKey.get(hand);
      return handMaskRendererState28 != null && handMaskRendererState28.handMaskRendererState.flag ? compute4(handMaskRendererState28.handMaskRendererState.simpleFramebuffer, true) : 0;
   }

   public int compute3(Hand hand) {
      HandMaskRenderer.HandMaskRendererState2 handMaskRendererState29 = this.valuesByKey.get(hand);
      return handMaskRendererState29 != null && handMaskRendererState29.handMaskRendererState2.flag ? compute4(handMaskRendererState29.handMaskRendererState2.simpleFramebuffer, false) : 0;
   }

   public void invoke3() {
      this.flag = false;
      this.intValue = -1;
      this.intValue2 = -1;
      this.valuesByKey.values().forEach(HandMaskRenderer.HandMaskRendererState2::invoke3);
   }

   private static int compute4(Framebuffer framebuffer, boolean bl) {
      if (framebuffer == null) {
         return 0;
      } else {
         return (bl ? framebuffer.getDepthAttachment() : framebuffer.getColorAttachment()) instanceof GlTexture glTexture ? glTexture.getGlId() : 0;
      }
   }

   static final class HandMaskRendererState {
      private final String text;
      private final BufferAllocator bufferAllocator = new BufferAllocator(262144);
      private final SequencedMap<RenderLayer, BufferAllocator> valuesByKey = new LinkedHashMap<>();
      SimpleFramebuffer simpleFramebuffer;
      HandMaskRenderer.HandMaskRendererResources handMaskRendererResources;
      private int intValue = -1;
      private int intValue2 = -1;
      boolean flag;

      HandMaskRendererState(String string) {
         this.text = string;
      }

      boolean check(int i, int j) {
         if (this.simpleFramebuffer == null) {
            this.simpleFramebuffer = new SimpleFramebuffer(this.text, i, j, true);
            this.intValue = i;
            this.intValue2 = j;
         } else if (this.intValue != i || this.intValue2 != j) {
            this.simpleFramebuffer.resize(i, j);
            this.intValue = i;
            this.intValue2 = j;
         }

         GpuTextureView gpuTextureView = this.simpleFramebuffer.getColorAttachmentView();
         GpuTextureView gpuTextureView2 = this.simpleFramebuffer.getDepthAttachmentView();
         return gpuTextureView != null && !gpuTextureView.isClosed() && (gpuTextureView2 == null || !gpuTextureView2.isClosed());
      }

      void invoke() {
         this.invoke4();
         this.invoke3();
         this.bufferAllocator.clear();
         this.valuesByKey.values().forEach(BufferAllocator::clear);
         this.handMaskRendererResources = new HandMaskRenderer.HandMaskRendererResources(this.bufferAllocator, this.valuesByKey);
      }

      void invoke2() {
         HandMaskRenderer.HandMaskRendererResources handMaskRendererResources = this.handMaskRendererResources;
         if (handMaskRendererResources != null && handMaskRendererResources.flag && this.simpleFramebuffer != null) {
            GpuTextureView gpuTextureView3 = this.simpleFramebuffer.getColorAttachmentView();
            if (gpuTextureView3 != null && !gpuTextureView3.isClosed()) {
               GpuTextureView gpuTextureView4 = RenderSystem.outputColorTextureOverride;
               GpuTextureView gpuTextureView5 = RenderSystem.outputDepthTextureOverride;
               RenderSystem.outputColorTextureOverride = gpuTextureView3;
               RenderSystem.outputDepthTextureOverride = this.simpleFramebuffer.getDepthAttachmentView();

               try {
                  handMaskRendererResources.invoke();
                  this.flag = true;
               } finally {
                  RenderSystem.outputColorTextureOverride = gpuTextureView4;
                  RenderSystem.outputDepthTextureOverride = gpuTextureView5;
               }
            }
         }
      }

      private void invoke3() {
         if (this.simpleFramebuffer != null) {
            GpuTextureView gpuTextureView6 = this.simpleFramebuffer.getColorAttachmentView();
            GpuTextureView gpuTextureView7 = this.simpleFramebuffer.getDepthAttachmentView();
            if (gpuTextureView6 != null && !gpuTextureView6.isClosed()) {
               CommandEncoder commandEncoder = RenderSystem.getDevice().createCommandEncoder();
               if (gpuTextureView7 != null && !gpuTextureView7.isClosed()) {
                  commandEncoder.clearColorAndDepthTextures(gpuTextureView6.texture(), 0, gpuTextureView7.texture(), 1.0);
               } else {
                  commandEncoder.clearColorTexture(gpuTextureView6.texture(), 0);
               }
            }
         }
      }

      void invoke4() {
         this.flag = false;
         HandMaskRenderer.HandMaskRendererResources handMaskRendererResources2 = this.handMaskRendererResources;
         this.handMaskRendererResources = null;
         if (handMaskRendererResources2 != null) {
            handMaskRendererResources2.close();
         }
      }

      void invoke5() {
         this.invoke4();

         for (BufferAllocator bufferAllocator2 : this.valuesByKey.values()) {
            bufferAllocator2.clear();
         }

         this.bufferAllocator.clear();
         if (this.simpleFramebuffer != null) {
            this.simpleFramebuffer.delete();
            this.simpleFramebuffer = null;
         }

         this.intValue = -1;
         this.intValue2 = -1;
      }
   }

   static final class HandMaskRendererResources implements AutoCloseable {
      private final BufferAllocator bufferAllocator;
      private final SequencedMap<RenderLayer, BufferAllocator> valuesByKey;
      private final Immediate immediate;
      boolean flag;
      private boolean flag2;

      HandMaskRendererResources(BufferAllocator bufferAllocator, SequencedMap<RenderLayer, BufferAllocator> sequencedMap) {
         this.bufferAllocator = bufferAllocator;
         this.valuesByKey = sequencedMap;
         this.immediate = VertexConsumerProvider.immediate(sequencedMap, bufferAllocator);
      }

      VertexConsumer resolve(RenderLayer renderLayer) {
         this.valuesByKey
            .computeIfAbsent(renderLayer, renderLayerx -> new BufferAllocator(Math.max(4096, Math.min(renderLayerx.getExpectedBufferSize(), 262144))));
         this.flag = true;
         this.flag2 = false;
         return this.immediate.getBuffer(renderLayer);
      }

      void invoke() {
         if (!this.flag2) {
            this.immediate.draw();
            this.bufferAllocator.clear();
            this.valuesByKey.values().forEach(BufferAllocator::clear);
            this.flag2 = true;
         }
      }

      @Override
      public void close() {
         this.bufferAllocator.clear();
         this.valuesByKey.values().forEach(BufferAllocator::clear);
      }
   }

   static final class HandMaskRendererState2 {
      final HandMaskRenderer.HandMaskRendererState handMaskRendererState;
      final HandMaskRenderer.HandMaskRendererState handMaskRendererState2;
      boolean flag;

      HandMaskRendererState2(String string) {
         this.handMaskRendererState = new HandMaskRenderer.HandMaskRendererState(string + "_mask");
         this.handMaskRendererState2 = new HandMaskRenderer.HandMaskRendererState(string + "_item");
      }

      boolean check(int i, int j) {
         return this.handMaskRendererState.check(i, j) && this.handMaskRendererState2.check(i, j);
      }

      void invoke() {
         this.handMaskRendererState.invoke();
         this.handMaskRendererState2.invoke();
         this.flag = true;
      }

      private void invoke2() {
         this.flag = false;
         this.handMaskRendererState.invoke4();
         this.handMaskRendererState2.invoke4();
      }

      private void invoke3() {
         this.flag = false;
         this.handMaskRendererState.invoke5();
         this.handMaskRendererState2.invoke5();
      }
   }
}
