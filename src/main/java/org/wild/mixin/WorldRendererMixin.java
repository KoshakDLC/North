package org.wild.mixin;

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.enums.CameraSubmersionType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.DefaultFramebufferSet;
import net.minecraft.client.render.FrameGraphBuilder;
import net.minecraft.client.render.FramePass;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.ObjectAllocator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.metaculture.protection.Event;
import ru.metaculture.protection.EventManager;
import ru.metaculture.protection.Render3DEvent;
import ru.metaculture.protection.WorldRenderContextEvent;
import ru.metaculture.protection.StardustSkyRenderer;
import ru.metaculture.protection.WorldRenderBuffer;
import ru.metaculture.protection.FramebufferUtils;
import ru.metaculture.protection.EntityFramebufferCapture;
import ru.metaculture.protection.WorldRenderCapture;
import ru.metaculture.protection.Removals;
import ru.metaculture.protection.Stardust;
import ru.metaculture.protection.WorldTweaks;

@Mixin({WorldRenderer.class})
public class WorldRendererMixin {
   @Shadow
   @Final
   private DefaultFramebufferSet framebufferSet;
   @Shadow
   @Nullable
   private ClientWorld world;

   @Inject(
      method = {"renderSky"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void renderStardustSky(FrameGraphBuilder frameGraphBuilder, Camera camera, float f, GpuBufferSlice gpuBufferSlice, CallbackInfo callbackInfo) {
      if (Stardust.isFlag()) {
         callbackInfo.cancel();
         if (this.world != null && camera != null) {
            CameraSubmersionType cameraSubmersionType = camera.getSubmersionType();
            if (cameraSubmersionType != CameraSubmersionType.POWDER_SNOW && cameraSubmersionType != CameraSubmersionType.LAVA && !this.wild$hasBlindnessOrDarkness(camera)) {
               FramePass framePass = frameGraphBuilder.createPass("wild_stardust_sky");
               this.framebufferSet.mainFramebuffer = framePass.transfer(this.framebufferSet.mainFramebuffer);
               framePass.setRenderer(() -> {
                  RenderSystem.setShaderFog(gpuBufferSlice);
                  StardustSkyRenderer.invoke3(camera, f, Stardust.measure2());
               });
            }
         }
      }
   }

   @Unique
   private boolean wild$hasBlindnessOrDarkness(Camera camera) {
      return !(camera.getFocusedEntity() instanceof LivingEntity livingEntity)
         ? false
         : livingEntity.hasStatusEffect(StatusEffects.BLINDNESS) || livingEntity.hasStatusEffect(StatusEffects.DARKNESS);
   }

   @Inject(
      method = {"renderWeather"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void wild$suppressWeather(FrameGraphBuilder frameGraphBuilder, Vec3d vec3d, float f, GpuBufferSlice gpuBufferSlice, CallbackInfo callbackInfo) {
      if (WorldTweaks.check() || Removals.check2("Погода (дождь/снег)")) {
         callbackInfo.cancel();
      }
   }

   @Inject(
      method = {"addWeatherParticlesAndSound"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void wild$suppressWeatherFx(Camera camera, CallbackInfo callbackInfo) {
      if (Removals.check2("Погода (дождь/снег)")) {
         callbackInfo.cancel();
      }
   }

   @Inject(
      method = {"render"},
      at = {@At("HEAD")}
   )
   private void beginEntityCapture(
      ObjectAllocator objectAllocator,
      RenderTickCounter renderTickCounter,
      boolean bl,
      Camera camera,
      Matrix4f matrix4f,
      Matrix4f matrix4f2,
      GpuBufferSlice gpuBufferSlice,
      Vector4f vector4f,
      boolean bl2,
      CallbackInfo callbackInfo
   ) {
      StardustSkyRenderer.invoke2(matrix4f, matrix4f2);
      EntityFramebufferCapture.getInstance().beginCaptureFrame((WorldRenderer)(Object)this, renderTickCounter, camera);
   }

   @Inject(
      method = {"render"},
      at = {@At("RETURN")}
   )
   private void publishWorldRenderEvent(
      ObjectAllocator objectAllocator,
      RenderTickCounter renderTickCounter,
      boolean bl,
      Camera camera,
      Matrix4f matrix4f,
      Matrix4f matrix4f2,
      GpuBufferSlice gpuBufferSlice,
      Vector4f vector4f,
      boolean bl2,
      CallbackInfo callbackInfo
   ) {
      MinecraftClient client = MinecraftClient.getInstance();
      if (!WorldRenderBuffer.check(client)) {
         EntityFramebufferCapture.getInstance().endCaptureFrame();
      } else {
         MatrixStack matrices = new MatrixStack();
         matrices.multiplyPositionMatrix(new Matrix4f(matrix4f));
         EventManager.post((Event)(new Render3DEvent(matrices, renderTickCounter.getTickProgress(true))));
         EntityFramebufferCapture.getInstance().endCaptureFrame();
         GameRenderer gameRenderer = client.gameRenderer;
         if (gameRenderer != null && camera != null) {
            FramebufferUtils.GlStateSnapshot glStateSnapshot = FramebufferUtils.captureGlState();
            WorldRenderCapture worldRenderCapture = null;

            try {
               worldRenderCapture = WorldRenderCapture.resolve(client, renderTickCounter, camera, matrix4f, matrix4f2);
               float floatValue = worldRenderCapture.getFloatValue();

               try {
                  EventManager.post((Event)(new WorldRenderContextEvent(client, gameRenderer, worldRenderCapture, floatValue)));
               } finally {
                  if (worldRenderCapture != null) {
                     try {
                        worldRenderCapture.invoke7();
                     } finally {
                        worldRenderCapture.close();
                     }
                  }
               }
            } finally {
               FramebufferUtils.restoreGlState(glStateSnapshot);
            }
         }
      }
   }
}
