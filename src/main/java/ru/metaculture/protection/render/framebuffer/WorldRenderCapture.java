package ru.metaculture.protection;

import java.util.Objects;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.util.BufferAllocator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

public final class WorldRenderCapture implements AutoCloseable {
   private static final int INT_VALUE = 262144;
   private static final BufferAllocator BUFFER_ALLOCATOR = new BufferAllocator(262144);
   private static final Immediate IMMEDIATE = VertexConsumerProvider.immediate(BUFFER_ALLOCATOR);
   private final Camera camera;
   private final MatrixStack matrixStack;
   private final Matrix4f matrix4f;
   private final Matrix4f matrix4f2;
   private final Matrix4f matrix4f3;
   private final BufferAllocator bufferAllocator;
   private final Immediate immediate;
   private final float floatValue;
   private boolean flag;

   private WorldRenderCapture(
      Camera camera,
      MatrixStack matrixStack,
      Matrix4f matrix4f,
      Matrix4f matrix4f2,
      Matrix4f matrix4f3,
      BufferAllocator bufferAllocator,
      Immediate immediate,
      float f
   ) {
      this.camera = camera;
      this.matrixStack = matrixStack;
      this.matrix4f = matrix4f;
      this.matrix4f2 = matrix4f2;
      this.matrix4f3 = matrix4f3;
      this.bufferAllocator = bufferAllocator;
      this.immediate = immediate;
      this.floatValue = f;
   }

   public static WorldRenderCapture resolve(
      MinecraftClient minecraftClient, RenderTickCounter renderTickCounter, Camera camera, Matrix4f matrix4f, Matrix4f matrix4f2
   ) {
      Objects.requireNonNull(minecraftClient, "client");
      Objects.requireNonNull(renderTickCounter, "tickCounter");
      Objects.requireNonNull(camera, "camera");
      Objects.requireNonNull(matrix4f, "positionMatrix");
      Objects.requireNonNull(matrix4f2, "projectionMatrix");
      MatrixStack matrices = new MatrixStack();
      Matrix4f matrix4f4 = new Matrix4f(matrix4f);
      Matrix4f matrix4f5 = new Matrix4f(matrix4f4);
      matrices.multiplyPositionMatrix(new Matrix4f(matrix4f4));
      BUFFER_ALLOCATOR.clear();
      BufferAllocator bufferAllocator2 = BUFFER_ALLOCATOR;
      Immediate immediate2 = IMMEDIATE;
      float floatValue = renderTickCounter.getTickProgress(false);
      return new WorldRenderCapture(camera, matrices, matrix4f4, matrix4f5, new Matrix4f(matrix4f2), bufferAllocator2, immediate2, floatValue);
   }

   public Camera getCamera() {
      return this.camera;
   }

   public MatrixStack getMatrixStack() {
      return this.matrixStack;
   }

   public Matrix4f resolve2() {
      return new Matrix4f(this.matrix4f);
   }

   public Matrix4f resolve3() {
      return new Matrix4f(this.matrix4f2);
   }

   public Matrix4f resolve4() {
      return new Matrix4f(this.matrix4f3);
   }

   public float getFloatValue() {
      return this.floatValue;
   }

   public Immediate resolve5() {
      if (this.flag) {
         throw new IllegalStateException("Cannot access buffers after the world renderer has been closed.");
      } else {
         return this.immediate;
      }
   }

   public VertexConsumer resolve6(RenderLayer renderLayer) {
      Objects.requireNonNull(renderLayer, "layer");
      if (this.flag) {
         throw new IllegalStateException("Cannot request buffers after the world renderer has been closed.");
      } else {
         return this.immediate.getBuffer(renderLayer);
      }
   }

   public void invoke(Vec3d vec3d, Vec3d vec3d2, Vec3d vec3d3, Vec3d vec3d4, int i, boolean bl) {
      Objects.requireNonNull(vec3d, "v0");
      Objects.requireNonNull(vec3d2, "v1");
      Objects.requireNonNull(vec3d3, "v2");
      Objects.requireNonNull(vec3d4, "v3");
      RenderLayer renderLayer2 = bl ? WorldRenderPipelines.getRENDER_LAYER() : WorldRenderPipelines.getRENDER_LAYER_2();
      WorldRenderContext worldRenderContext = new WorldRenderContext(this, this.matrixStack.peek(), this.resolve6(renderLayer2));
      worldRenderContext.invoke(vec3d, vec3d2, vec3d3, vec3d4, i);
   }

   public void invoke2(Vec3d vec3d, Vec3d vec3d2, Vec3d vec3d3, Vec3d vec3d4, int i, int j, int k, int l) {
      this.invoke3(vec3d, vec3d2, vec3d3, vec3d4, i, j, k, l, true);
   }

   public void invoke3(Vec3d vec3d, Vec3d vec3d2, Vec3d vec3d3, Vec3d vec3d4, int i, int j, int k, int l, boolean bl) {
      Objects.requireNonNull(vec3d, "v0");
      Objects.requireNonNull(vec3d2, "v1");
      Objects.requireNonNull(vec3d3, "v2");
      Objects.requireNonNull(vec3d4, "v3");
      RenderLayer renderLayer3 = bl ? WorldRenderPipelines.getRENDER_LAYER_4() : WorldRenderPipelines.getRENDER_LAYER_5();
      WorldRenderContext worldRenderContext2 = new WorldRenderContext(this, this.matrixStack.peek(), this.resolve6(renderLayer3));
      worldRenderContext2.invoke2(vec3d, vec3d2, vec3d3, vec3d4, i, j, k, l);
   }

   public void invoke4(Vec3d vec3d, Vec3d vec3d2, int i, boolean bl) {
      Objects.requireNonNull(vec3d, "min");
      Objects.requireNonNull(vec3d2, "max");
      RenderLayer renderLayer4 = bl ? WorldRenderPipelines.getRENDER_LAYER() : WorldRenderPipelines.getRENDER_LAYER_3();
      WorldRenderContext worldRenderContext3 = new WorldRenderContext(this, this.matrixStack.peek(), this.resolve6(renderLayer4));
      worldRenderContext3.invoke3(vec3d, vec3d2, i);
   }

   public void invoke5(Vec3d vec3d, Vec3d vec3d2, double d, int i, boolean bl) {
      Objects.requireNonNull(vec3d, "start");
      Objects.requireNonNull(vec3d2, "end");
      if (!Double.isFinite(d)) {
         throw new IllegalArgumentException("Line width must be finite.");
      } else if (d < 0.0) {
         throw new IllegalArgumentException("Line width cannot be negative.");
      } else {
         RenderLayer renderLayer5 = bl ? WorldRenderPipelines.resolve5(d) : WorldRenderPipelines.resolve6(d);
         WorldRenderContext worldRenderContext4 = new WorldRenderContext(this, this.matrixStack.peek(), this.resolve6(renderLayer5));
         worldRenderContext4.invoke4(vec3d, vec3d2, i);
      }
   }

   public void invoke6(Vec3d vec3d, Vec3d vec3d2, Vec3d vec3d3, Vec3d vec3d4, float f, float g, float h, float i, float j, float k, float l, float m, int n) {
      Objects.requireNonNull(vec3d, "v0");
      Objects.requireNonNull(vec3d2, "v1");
      Objects.requireNonNull(vec3d3, "v2");
      Objects.requireNonNull(vec3d4, "v3");
      RenderLayer renderLayer6 = WorldRenderPipelines.getRENDER_LAYER_6();
      WorldRenderContext worldRenderContext5 = new WorldRenderContext(this, this.matrixStack.peek(), this.resolve6(renderLayer6));
      worldRenderContext5.invoke6(vec3d, vec3d2, vec3d3, vec3d4, f, g, h, i, j, k, l, m, n);
   }

   public void invoke7() {
      if (!this.flag) {
         this.immediate.draw();
      }
   }

   @Override
   public void close() {
      if (!this.flag) {
         this.flag = true;
         this.immediate.draw();
         this.bufferAllocator.clear();
      }
   }
}
