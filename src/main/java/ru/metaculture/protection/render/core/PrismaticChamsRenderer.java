package ru.metaculture.protection;

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.buffers.Std140Builder;
import com.mojang.blaze3d.buffers.Std140SizeCalculator;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline.Snippet;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.AddressMode;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.textures.TextureFormat;
import com.mojang.blaze3d.vertex.VertexFormat.DrawMode;
import java.nio.ByteBuffer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.DynamicUniformStorage;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gl.UniformType;
import net.minecraft.client.gl.DynamicUniformStorage.Uploadable;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.RenderLayer.MultiPhaseParameters;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.util.Window;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector4f;
import org.joml.Vector4fc;
import org.lwjgl.glfw.GLFW;

public final class PrismaticChamsRenderer {
   private static final int BUFFER_SIZE = 1048576;
   private static final int SCREEN_TEXTURE_USAGE = 5;
   private static final long START_TIME_NANOS = System.nanoTime();
   private static final Identifier SHADER_ID = Identifier.of("wild", "core/prismatic_chams");
   private static final int UNIFORM_SIZE = new Std140SizeCalculator().putVec4().putVec4().putVec4().putVec4().putVec4().putVec4().putIVec4().get();
   private static final RenderPipeline VISIBLE_PIPELINE = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.TRANSFORMS_AND_PROJECTION_SNIPPET})
         .withLocation(Identifier.of("wild", "pipeline/sss_chams_visible"))
         .withVertexShader(SHADER_ID)
         .withFragmentShader(SHADER_ID)
         .withSampler("u_ScreenTexture")
         .withUniform("PrismaticChams", UniformType.UNIFORM_BUFFER)
         .withVertexFormat(VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, DrawMode.QUADS)
         .withCull(false)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withColorWrite(true, true)
         .withDepthWrite(false)
         .withBlend(BlendFunction.TRANSLUCENT)
         .build()
   );
   private static final RenderPipeline DEPTH_PIPELINE = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.TRANSFORMS_AND_PROJECTION_SNIPPET})
         .withLocation(Identifier.of("wild", "pipeline/sss_chams_depth"))
         .withVertexShader(SHADER_ID)
         .withFragmentShader(SHADER_ID)
         .withSampler("u_ScreenTexture")
         .withUniform("PrismaticChams", UniformType.UNIFORM_BUFFER)
         .withVertexFormat(VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, DrawMode.QUADS)
         .withCull(false)
         .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
         .withColorWrite(true, true)
         .withDepthWrite(true)
         .withBlend(BlendFunction.TRANSLUCENT)
         .build()
   );
   private static final RenderLayer VISIBLE_LAYER = WorldRenderPipelines.withRenderPassSetup(
      RenderLayer.of(
         "wild/sss_chams_visible",
         BUFFER_SIZE,
         false,
         true,
         VISIBLE_PIPELINE,
         MultiPhaseParameters.builder()
            .texture(RenderPhase.NO_TEXTURE)
            .lightmap(RenderPhase.ENABLE_LIGHTMAP)
            .overlay(RenderPhase.ENABLE_OVERLAY_COLOR)
            .build(false)
      ),
      PrismaticChamsRenderer::configureRenderPass
   );
   private static final RenderLayer DEPTH_LAYER = WorldRenderPipelines.withRenderPassSetup(
      RenderLayer.of(
         "wild/sss_chams_depth",
         BUFFER_SIZE,
         false,
         true,
         DEPTH_PIPELINE,
         MultiPhaseParameters.builder()
            .texture(RenderPhase.NO_TEXTURE)
            .lightmap(RenderPhase.ENABLE_LIGHTMAP)
            .overlay(RenderPhase.ENABLE_OVERLAY_COLOR)
            .build(false)
      ),
      PrismaticChamsRenderer::configureRenderPass
   );
   private static final UniformData DEFAULT_UNIFORMS = new UniformData(
      new Vector4f(0.12F, 0.82F, 1.0F, 1.0F),
      new Vector4f(0.82F, 0.18F, 1.0F, 1.0F),
      new Vector4f(0.0F, 0.0F, 0.0F, 0.0F),
      new Vector4f(1.35F, 1.0F, 0.72F, 0.0F),
      new Vector4f(1.0F, 0.0F, 0.0F, 0.0F),
      new Vector4f(1.0F, 1.0F, 1.0F, 1.0F),
      0,
      0,
      0,
      0
   );
   private static DynamicUniformStorage<UniformData> uniformStorage;
   private static UniformData currentUniforms = DEFAULT_UNIFORMS;
   private static GpuBufferSlice uniformSlice;
   private static GpuTexture screenCopyTexture;
   private static GpuTextureView screenCopyView;
   private static TextureFormat screenCopyFormat;
   private static int screenCopyWidth;
   private static int screenCopyHeight;
   private static boolean screenCopyReady;

   private PrismaticChamsRenderer() {
   }

   public static void initialize() {
      if (VISIBLE_LAYER == null || DEPTH_LAYER == null) {
         RenderDiagnosticsTracker.getInstance().fail("PrismaticChamsShaderRegistry.init", new IllegalStateException("SSS chams shader registry failed"));
      }
   }

   public static RenderLayer getVisibleLayer() {
      return VISIBLE_LAYER;
   }

   public static RenderLayer getDepthLayer() {
      return DEPTH_LAYER;
   }

   public static RenderLayer selectLayer(Chams chams) {
      return chams != null && !chams.usesVisiblePassOnly() ? DEPTH_LAYER : VISIBLE_LAYER;
   }

   public static void captureScreenTexture() {
      screenCopyReady = false;
      if (canUseGpuResources()) {
         MinecraftClient client = MinecraftClient.getInstance();
         if (client != null) {
            Framebuffer framebuffer = client.getFramebuffer();
            if (framebuffer != null) {
               GpuTexture colorTexture = framebuffer.getColorAttachment();
               if (colorTexture != null && !colorTexture.isClosed()) {
                  int width = Math.max(1, colorTexture.getWidth(0));
                  int height = Math.max(1, colorTexture.getHeight(0));
                  ensureScreenCopy(colorTexture, width, height);
                  if (screenCopyTexture != null && screenCopyView != null && !screenCopyTexture.isClosed() && !screenCopyView.isClosed()) {
                     RenderSystem.getDevice().createCommandEncoder().copyTextureToTexture(colorTexture, screenCopyTexture, 0, 0, 0, 0, 0, width, height);
                     screenCopyWidth = width;
                     screenCopyHeight = height;
                     screenCopyReady = true;
                     RenderSystem.setShaderTexture(1, screenCopyView);
                  }
               }
            }
         }
      }
   }

   public static void updateUniforms(Chams chams, LivingEntityRenderState state, float depthPass, float transitionAlpha) {
      if (chams == null) {
         currentUniforms = DEFAULT_UNIFORMS;
      } else {
         float[] topAccent = chams.getTopAccentRgba();
         float[] bottomAccent = chams.getBottomAccentRgba();
         Vec3d cameraPosition = getCameraPosition();
         float elapsedSeconds = (float)(System.nanoTime() - START_TIME_NANOS) / 1.0E9F;
         float entityNoise = getEntityNoise(state);
         float depthMode = chams.usesVisiblePassOnly() ? 0.0F : (chams.isHybridDepthMode() ? 1.0F : 2.0F);
         Vector4f resolution = getResolution();
         currentUniforms = new UniformData(
            new Vector4f(topAccent[0], topAccent[1], topAccent[2], topAccent[3]),
            new Vector4f(bottomAccent[0], bottomAccent[1], bottomAccent[2], bottomAccent[3]),
            new Vector4f((float)cameraPosition.x, (float)cameraPosition.y, (float)cameraPosition.z, elapsedSeconds),
            new Vector4f(chams.intensity.getValue(), chams.opacity.getValue(), chams.refraction.getValue(), 0.0F),
            new Vector4f(transitionAlpha, depthPass, entityNoise, depthMode),
            resolution,
            chams.getShaderModeId(),
            0,
            0,
            0
         );
         uploadUniforms();
      }
   }

   public static void resetFrameState() {
      if (uniformStorage != null && canUseGpuResources()) {
         uniformStorage.clear();
      }

      uniformSlice = null;
      screenCopyReady = false;
   }

   public static void close() {
      DynamicUniformStorage<UniformData> storage = uniformStorage;
      uniformStorage = null;
      uniformSlice = null;
      screenCopyReady = false;
      if (storage != null && canUseGpuResources()) {
         storage.close();
      }

      releaseScreenCopy();
   }

   private static void configureRenderPass(RenderPass renderPass) {
      GpuBufferSlice preparedUniforms = uniformSlice;
      if (preparedUniforms == null) {
         RenderDiagnosticsTracker.getInstance()
            .fail("PrismaticChamsShaderRegistry.uniform", new IllegalStateException("PrismaticChams uniform slice is not prepared"));
      }

      renderPass.setUniform("PrismaticChams", preparedUniforms);
      GpuTextureView screenTextureView = getScreenTextureView();
      if (screenTextureView == null || screenTextureView.isClosed()) {
         RenderDiagnosticsTracker.getInstance().fail("PrismaticChamsShaderRegistry.sampler", new IllegalStateException("u_ScreenTexture sampler is unavailable"));
      }

      renderPass.bindSampler("u_ScreenTexture", screenTextureView);
   }

   private static void uploadUniforms() {
      uniformSlice = canUseGpuResources() ? getUniformStorage().write(currentUniforms == null ? DEFAULT_UNIFORMS : currentUniforms) : null;
   }

   private static DynamicUniformStorage<UniformData> getUniformStorage() {
      if (uniformStorage == null) {
         uniformStorage = new DynamicUniformStorage("SSS Chams UBO", UNIFORM_SIZE, 4);
      }

      return uniformStorage;
   }

   private static void ensureScreenCopy(GpuTexture sourceTexture, int width, int height) {
      TextureFormat format = sourceTexture.getFormat();
      if (screenCopyTexture == null
         || screenCopyView == null
         || screenCopyTexture.isClosed()
         || screenCopyView.isClosed()
         || screenCopyWidth != width
         || screenCopyHeight != height
         || screenCopyFormat != format) {
         releaseScreenCopy();
         screenCopyTexture = RenderSystem.getDevice().createTexture("Wild SSS Chams Screen", SCREEN_TEXTURE_USAGE, format, width, height, 1, 1);
         screenCopyView = RenderSystem.getDevice().createTextureView(screenCopyTexture);
         screenCopyFormat = format;
         screenCopyWidth = width;
         screenCopyHeight = height;
         screenCopyTexture.setAddressMode(AddressMode.CLAMP_TO_EDGE);
         screenCopyTexture.setTextureFilter(FilterMode.LINEAR, false);
      }
   }

   private static void releaseScreenCopy() {
      GpuTextureView textureView = screenCopyView;
      GpuTexture texture = screenCopyTexture;
      screenCopyView = null;
      screenCopyTexture = null;
      screenCopyFormat = null;
      screenCopyWidth = 0;
      screenCopyHeight = 0;
      if (textureView != null && !textureView.isClosed()) {
         textureView.close();
      }

      if (texture != null && !texture.isClosed()) {
         texture.close();
      }
   }

   private static GpuTextureView getScreenTextureView() {
      if (screenCopyReady && screenCopyView != null && !screenCopyView.isClosed()) {
         return screenCopyView;
      } else {
         MinecraftClient client = MinecraftClient.getInstance();
         if (client != null && client.getFramebuffer() != null) {
            GpuTextureView framebufferView = client.getFramebuffer().getColorAttachmentView();
            return framebufferView != null && !framebufferView.isClosed()
               ? framebufferView
               : failSampler("framebuffer color attachment view is unavailable");
         } else {
            return failSampler("client framebuffer is unavailable");
         }
      }
   }

   private static GpuTextureView failSampler(String message) {
      IllegalStateException failure = new IllegalStateException(message);
      RenderDiagnosticsTracker.getInstance().fail("PrismaticChamsShaderRegistry.screenSampler", failure);
      throw failure;
   }

   private static Vector4f getResolution() {
      int width = screenCopyReady && screenCopyWidth > 0 ? screenCopyWidth : 0;
      int height = screenCopyReady && screenCopyHeight > 0 ? screenCopyHeight : 0;
      if (width <= 0 || height <= 0) {
         MinecraftClient client = MinecraftClient.getInstance();
         Window window = client == null ? null : client.getWindow();
         if (window != null) {
            width = window.getFramebufferWidth();
            height = window.getFramebufferHeight();
         }
      }

      width = Math.max(1, width);
      height = Math.max(1, height);
      return new Vector4f(width, height, 1.0F / width, 1.0F / height);
   }

   private static Vec3d getCameraPosition() {
      MinecraftClient client = MinecraftClient.getInstance();
      return client != null && client.gameRenderer != null && client.gameRenderer.getCamera() != null
         ? client.gameRenderer.getCamera().getPos()
         : Vec3d.ZERO;
   }

   private static boolean canUseGpuResources() {
      return RenderSystem.isOnRenderThread() && GLFW.glfwGetCurrentContext() != 0L;
   }

   private static float getEntityNoise(LivingEntityRenderState state) {
      if (state == null) {
         return 0.0F;
      } else {
         int entityId = ((ChamsRenderState)state).wild$getEntityId();
         int hash = entityId == Integer.MIN_VALUE
            ? Float.floatToIntBits((float)state.x * 17.0F + (float)state.z * 31.0F)
            : entityId;
         hash ^= hash << 13;
         hash ^= hash >>> 17;
         hash ^= hash << 5;
         return (hash & 65535) / 65535.0F;
      }
   }

   record UniformData(
      Vector4fc accentTop,
      Vector4fc accentBottom,
      Vector4fc cameraAndTime,
      Vector4fc params,
      Vector4fc state,
      Vector4fc resolution,
      int mode,
      int flagA,
      int flagB,
      int flagC
   ) implements Uploadable {
      public void write(ByteBuffer buffer) {
         Std140Builder.intoBuffer(buffer)
            .putVec4(this.accentTop)
            .putVec4(this.accentBottom)
            .putVec4(this.cameraAndTime)
            .putVec4(this.params)
            .putVec4(this.state)
            .putVec4(this.resolution)
            .putIVec4(this.mode, this.flagA, this.flagB, this.flagC);
      }
   }
}
