package ru.metaculture.protection;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.buffers.Std140Builder;
import com.mojang.blaze3d.buffers.Std140SizeCalculator;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline.Snippet;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.platform.DestFactor;
import com.mojang.blaze3d.platform.SourceFactor;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.vertex.VertexFormat.DrawMode;
import java.nio.ByteBuffer;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.DynamicUniformStorage;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gl.UniformType;
import net.minecraft.client.gl.DynamicUniformStorage.Uploadable;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BuiltBuffer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.Window;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.joml.Vector4fc;

public final class StardustSkyRenderer {
   private static final Identifier IDENTIFIER = Identifier.of("minecraft", "core/stardust_sky");
   private static final BlendFunction BLEND_FUNCTION = new BlendFunction(SourceFactor.SRC_ALPHA, DestFactor.ONE);
   private static final int INT_VALUE = new Std140SizeCalculator()
      .putVec4()
      .putVec4()
      .putVec4()
      .putVec4()
      .putVec4()
      .putVec4()
      .putVec4()
      .putVec4()
      .putVec4()
      .putIVec4()
      .get();
   private static final RenderPipeline RENDER_PIPELINE = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.TRANSFORMS_AND_PROJECTION_SNIPPET, RenderPipelines.GLOBALS_SNIPPET})
         .withLocation(Identifier.of("wild", "pipeline/stardust_sky"))
         .withVertexShader(IDENTIFIER)
         .withFragmentShader(IDENTIFIER)
         .withUniform("StardustSky", UniformType.UNIFORM_BUFFER)
         .withVertexFormat(VertexFormats.POSITION, DrawMode.TRIANGLES)
         .withCull(false)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .withBlend(BLEND_FUNCTION)
         .build()
   );
   private static final int INT_VALUE_2 = 48;
   private static final int INT_VALUE_3 = 14;
   private static final float FLOAT_VALUE = 128.0F;
   private static final Vector4f VECTOR4F = new Vector4f();
   private static final Vector3f VECTOR3F = new Vector3f();
   private static final Matrix4f MATRIX4F = new Matrix4f();
   private static final Matrix4f MATRIX4F_2 = new Matrix4f();
   private static final Vector3f VECTOR3F_2 = new Vector3f();
   private static final Matrix4f MATRIX4F_3 = new Matrix4f();
   private static final long TIMESTAMP = System.nanoTime();
   private static final long TIMESTAMP_2 = 4096000000000L;
   private static DynamicUniformStorage<StardustSkyRenderer.StardustSkyRendererData> dynamicUniformStorage;
   private static GpuBuffer gpuBuffer;
   private static int intValue;
   private static boolean flag;

   private StardustSkyRenderer() {
   }

   public static void invoke() {
   }

   public static void invoke2(Matrix4f matrix4f, Matrix4f matrix4f2) {
      try {
         if (matrix4f == null || matrix4f2 == null) {
            MATRIX4F_2.identity();
            return;
         }

         MATRIX4F_3.set(matrix4f2).mul(matrix4f);
         if (Math.abs(MATRIX4F_3.determinant()) <= 1.0E-8F) {
            MATRIX4F_2.identity();
            return;
         }

         MATRIX4F_2.set(MATRIX4F_3).invert();
      } catch (Throwable exception) {
         MATRIX4F_2.identity();
      }
   }

   public static void invoke3(Camera camera, float f, float g) {
      if (!flag && !(g <= 0.001F)) {
         try {
            invoke4();
            if (gpuBuffer == null || intValue <= 0) {
               return;
            }

            MinecraftClient client = MinecraftClient.getInstance();
            if (client == null || client.getFramebuffer() == null) {
               return;
            }

            if (camera == null) {
               return;
            }

            GpuTextureView gpuTextureView = client.getFramebuffer().getColorAttachmentView();
            GpuTextureView gpuTextureView2 = client.getFramebuffer().getDepthAttachmentView();
            int intValue = Stardust.getIntValue7();
            int intValue2 = Stardust.getIntValue8();
            VECTOR4F.set((intValue >>> 16 & 0xFF) / 255.0F, (intValue >>> 8 & 0xFF) / 255.0F, (intValue & 0xFF) / 255.0F, g);
            VECTOR3F.set((intValue2 >>> 16 & 0xFF) / 255.0F, (intValue2 >>> 8 & 0xFF) / 255.0F, (intValue2 & 0xFF) / 255.0F);
            MATRIX4F.identity();
            GpuBufferSlice gpuBufferSlice = RenderSystem.getDynamicUniforms()
               .write(RenderSystem.getModelViewMatrix(), VECTOR4F, VECTOR3F, MATRIX4F, Stardust.compute());
            GpuBufferSlice gpuBufferSlice2 = resolve().write(resolve2(client, camera, f, g, intValue, intValue2));
            RenderPass renderPass = RenderSystem.getDevice()
               .createCommandEncoder()
               .createRenderPass(() -> "Wild Stardust Sky", gpuTextureView, OptionalInt.empty(), gpuTextureView2, OptionalDouble.empty());

            try {
               renderPass.setPipeline(RENDER_PIPELINE);
               RenderSystem.bindDefaultUniforms(renderPass);
               renderPass.setUniform("DynamicTransforms", gpuBufferSlice);
               renderPass.setUniform("StardustSky", gpuBufferSlice2);
               renderPass.setVertexBuffer(0, gpuBuffer);
               renderPass.draw(0, intValue);
            } catch (Throwable exception2) {
               if (renderPass != null) {
                  try {
                     renderPass.close();
                  } catch (Throwable exception3) {
                     exception2.addSuppressed(exception3);
                  }
               }

               throw exception2;
            }

            if (renderPass != null) {
               renderPass.close();
            }
         } catch (Throwable exception4) {
            flag = true;
            System.err.println("[Stardust] sky renderer disabled: " + exception4.getMessage());
         }
      }
   }

   private static DynamicUniformStorage<StardustSkyRenderer.StardustSkyRendererData> resolve() {
      if (dynamicUniformStorage == null) {
         dynamicUniformStorage = new DynamicUniformStorage("Wild Stardust Sky UBO", INT_VALUE, 4);
      }

      return dynamicUniformStorage;
   }

   private static StardustSkyRenderer.StardustSkyRendererData resolve2(MinecraftClient minecraftClient, Camera camera, float f, float g, int i, int j) {
      Window window = minecraftClient.getWindow();
      int intValue3 = window == null ? 1 : Math.max(1, window.getFramebufferWidth());
      int intValue4 = window == null ? 1 : Math.max(1, window.getFramebufferHeight());
      VECTOR3F_2.set(0.0F, 0.0F, -1.0F);
      camera.getRotation().transform(VECTOR3F_2);
      VECTOR3F_2.normalize();
      float floatValue = minecraftClient.world == null ? 0.0F : minecraftClient.world.getRainGradient(f);
      float floatValue2 = measure(minecraftClient);
      float floatValue3 = (float)((System.nanoTime() - TIMESTAMP) % 4096000000000L) / 1.0E9F;
      float floatValue4 = Math.max(0.0F, Math.min(1.0F, Stardust.PLOTNOST_ZVYOZD.getValue() / 3600.0F));
      return new StardustSkyRenderer.StardustSkyRendererData(
         new Vector4f((i >>> 16 & 0xFF) / 255.0F, (i >>> 8 & 0xFF) / 255.0F, (i & 0xFF) / 255.0F, g),
         new Vector4f((j >>> 16 & 0xFF) / 255.0F, (j >>> 8 & 0xFF) / 255.0F, (j & 0xFF) / 255.0F, 1.0F),
         new Vector4f(VECTOR3F_2.x, VECTOR3F_2.y, VECTOR3F_2.z, floatValue),
         new Vector4f(intValue3, intValue4, floatValue3, floatValue2),
         new Vector4f(g, floatValue4, f, 0.0F),
         new Matrix4f(MATRIX4F_2),
         Stardust.resolve().compute(),
         0,
         0,
         0
      );
   }

   private static float measure(MinecraftClient minecraftClient) {
      if (minecraftClient != null && minecraftClient.world != null) {
         long longValue = Stardust.check() ? Stardust.timestamp : minecraftClient.world.getTimeOfDay();
         long longValue2 = Math.floorMod(longValue, 24000L);
         return (float)longValue2 / 24000.0F;
      } else {
         return 0.75F;
      }
   }

   private static void invoke4() {
      if (gpuBuffer == null && !flag) {
         try {
            BufferBuilder bufferBuilder2 = Tessellator.getInstance().begin(DrawMode.TRIANGLES, VertexFormats.POSITION);
            float floatValue5 = -0.24F;
            float floatValue6 = 1.0F;

            for (int intValue5 = 0; intValue5 < 14; intValue5++) {
               float floatValue7 = intValue5 / 14.0F;
               float floatValue8 = (intValue5 + 1) / 14.0F;
               float floatValue9 = floatValue5 + (floatValue6 - floatValue5) * floatValue7;
               float floatValue10 = floatValue5 + (floatValue6 - floatValue5) * floatValue8;

               for (int intValue6 = 0; intValue6 < 48; intValue6++) {
                  float floatValue11 = intValue6 / 48.0F;
                  float floatValue12 = (intValue6 + 1) / 48.0F;
                  invoke5(bufferBuilder2, floatValue11, floatValue9);
                  invoke5(bufferBuilder2, floatValue12, floatValue9);
                  invoke5(bufferBuilder2, floatValue12, floatValue10);
                  invoke5(bufferBuilder2, floatValue12, floatValue10);
                  invoke5(bufferBuilder2, floatValue11, floatValue10);
                  invoke5(bufferBuilder2, floatValue11, floatValue9);
               }
            }

            BuiltBuffer builtBuffer = bufferBuilder2.end();

            try {
               intValue = builtBuffer.getDrawParameters().vertexCount();
               gpuBuffer = RenderSystem.getDevice().createBuffer(() -> "Wild Stardust Sky Dome", 32, builtBuffer.getBuffer());
            } catch (Throwable exception5) {
               if (builtBuffer != null) {
                  try {
                     builtBuffer.close();
                  } catch (Throwable exception6) {
                     exception5.addSuppressed(exception6);
                  }
               }

               throw exception5;
            }

            if (builtBuffer != null) {
               builtBuffer.close();
            }
         } catch (Throwable exception7) {
            flag = true;
         }
      }
   }

   private static void invoke5(BufferBuilder bufferBuilder, float f, float g) {
      float floatValue13 = f * (float) (Math.PI * 2);
      float floatValue14 = (float)Math.sqrt(Math.max(0.0F, 1.0F - g * g));
      float floatValue15 = (float)Math.cos(floatValue13) * floatValue14 * 128.0F;
      float floatValue16 = g * 128.0F;
      float floatValue17 = (float)Math.sin(floatValue13) * floatValue14 * 128.0F;
      bufferBuilder.vertex(floatValue15, floatValue16, floatValue17);
   }

   record StardustSkyRendererData(
      Vector4fc primary,
      Vector4fc secondary,
      Vector4fc cameraWeather,
      Vector4fc resolutionTime,
      Vector4fc params,
      Matrix4fc inverseViewProjection,
      int mode,
      int flagA,
      int flagB,
      int flagC
   ) implements Uploadable {
      public void write(ByteBuffer buffer) {
         Std140Builder.intoBuffer(buffer)
            .putVec4(this.primary)
            .putVec4(this.secondary)
            .putVec4(this.cameraWeather)
            .putVec4(this.resolutionTime)
            .putVec4(this.params)
            .putMat4f(this.inverseViewProjection)
            .putIVec4(this.mode, this.flagA, this.flagB, this.flagC);
      }
   }
}
