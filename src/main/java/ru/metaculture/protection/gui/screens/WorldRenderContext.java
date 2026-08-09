package ru.metaculture.protection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack.Entry;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public final class WorldRenderContext {
   private static final float FLOAT_VALUE = 1.0E-6F;
   private final Camera camera;
   private final Matrix4f matrix4f;
   private final Matrix3f matrix3f;
   private final VertexConsumer vertexConsumer;
   private final Vec3d vec3d;

   public WorldRenderContext(WorldRenderCapture worldRenderCapture, Entry entry, VertexConsumer vertexConsumer) {
      this(Objects.requireNonNull(worldRenderCapture, "renderer").getCamera(), entry, vertexConsumer);
   }

   public WorldRenderContext(Camera camera, Entry entry, VertexConsumer vertexConsumer) {
      this.camera = Objects.requireNonNull(camera, "camera");
      Objects.requireNonNull(entry, "entry");
      this.vertexConsumer = Objects.requireNonNull(vertexConsumer, "consumer");
      this.vec3d = this.camera.getPos();
      this.matrix4f = new Matrix4f(entry.getPositionMatrix());
      this.matrix3f = new Matrix3f(entry.getNormalMatrix());
   }

   public void invoke(Vec3d vec3d, Vec3d vec3d2, Vec3d vec3d3, Vec3d vec3d4, int i) {
      this.invoke2(vec3d, vec3d2, vec3d3, vec3d4, i, i, i, i);
   }

   public void invoke2(Vec3d vec3d, Vec3d vec3d2, Vec3d vec3d3, Vec3d vec3d4, int i, int j, int k, int l) {
      Objects.requireNonNull(vec3d, "v0");
      Objects.requireNonNull(vec3d2, "v1");
      Objects.requireNonNull(vec3d3, "v2");
      Objects.requireNonNull(vec3d4, "v3");
      this.invoke8(vec3d, i);
      this.invoke8(vec3d2, j);
      this.invoke8(vec3d3, k);
      this.invoke8(vec3d4, l);
   }

   public void invoke3(Vec3d vec3d, Vec3d vec3d2, int i) {
      Objects.requireNonNull(vec3d, "min");
      Objects.requireNonNull(vec3d2, "max");
      if (!(vec3d.x > vec3d2.x) && !(vec3d.y > vec3d2.y) && !(vec3d.z > vec3d2.z)) {
         Vec3d vec3d5 = new Vec3d(vec3d.x, vec3d.y, vec3d.z);
         Vec3d vec3d6 = new Vec3d(vec3d.x, vec3d.y, vec3d2.z);
         Vec3d vec3d7 = new Vec3d(vec3d.x, vec3d2.y, vec3d.z);
         Vec3d vec3d8 = new Vec3d(vec3d.x, vec3d2.y, vec3d2.z);
         Vec3d vec3d9 = new Vec3d(vec3d2.x, vec3d.y, vec3d.z);
         Vec3d vec3d10 = new Vec3d(vec3d2.x, vec3d.y, vec3d2.z);
         Vec3d vec3d11 = new Vec3d(vec3d2.x, vec3d2.y, vec3d.z);
         Vec3d vec3d12 = new Vec3d(vec3d2.x, vec3d2.y, vec3d2.z);
         this.invoke(vec3d5, vec3d9, vec3d11, vec3d7, i);
         this.invoke(vec3d6, vec3d8, vec3d12, vec3d10, i);
         this.invoke(vec3d5, vec3d6, vec3d10, vec3d9, i);
         this.invoke(vec3d7, vec3d11, vec3d12, vec3d8, i);
         this.invoke(vec3d5, vec3d7, vec3d8, vec3d6, i);
         this.invoke(vec3d9, vec3d10, vec3d12, vec3d11, i);
      } else {
         throw new IllegalArgumentException("Minimum corner must be less than or equal to maximum corner.");
      }
   }

   public void invoke4(Vec3d vec3d, Vec3d vec3d2, int i) {
      this.invoke5(vec3d, vec3d2, i, i);
   }

   public void invoke5(Vec3d vec3d, Vec3d vec3d2, int i, int j) {
      Objects.requireNonNull(vec3d, "start");
      Objects.requireNonNull(vec3d2, "end");
      Vector3f vector3f2 = this.resolve2(vec3d, vec3d2);
      this.invoke10(vec3d, i, vector3f2);
      this.invoke10(vec3d2, j, vector3f2);
   }

   public void invoke6(Vec3d vec3d, Vec3d vec3d2, Vec3d vec3d3, Vec3d vec3d4, float f, float g, float h, float i, float j, float k, float l, float m, int n) {
      this.invoke7(vec3d, vec3d2, vec3d3, vec3d4, f, g, h, i, j, k, l, m, n, n, n, n);
   }

   public void invoke7(
      Vec3d vec3d, Vec3d vec3d2, Vec3d vec3d3, Vec3d vec3d4, float f, float g, float h, float i, float j, float k, float l, float m, int n, int o, int p, int q
   ) {
      Objects.requireNonNull(vec3d, "v0");
      Objects.requireNonNull(vec3d2, "v1");
      Objects.requireNonNull(vec3d3, "v2");
      Objects.requireNonNull(vec3d4, "v3");
      this.invoke9(vec3d, f, g, n);
      this.invoke9(vec3d2, h, i, o);
      this.invoke9(vec3d3, j, k, p);
      this.invoke9(vec3d4, l, m, q);
   }

   private void invoke8(Vec3d vec3d, int i) {
      Vec3d vec3d13 = this.resolve(vec3d);
      VertexConsumer vertexConsumer2 = this.vertexConsumer.vertex(this.matrix4f, (float)vec3d13.x, (float)vec3d13.y, (float)vec3d13.z);
      vertexConsumer2.color(ColorInterpolator.compute7(i), ColorInterpolator.compute8(i), ColorInterpolator.compute9(i), ColorInterpolator.compute6(i));
      this.invoke11(vertexConsumer2);
   }

   private void invoke9(Vec3d vec3d, float f, float g, int i) {
      Vec3d vec3d14 = this.resolve(vec3d);
      VertexConsumer vertexConsumer3 = this.vertexConsumer.vertex(this.matrix4f, (float)vec3d14.x, (float)vec3d14.y, (float)vec3d14.z);
      vertexConsumer3.texture(f, g);
      vertexConsumer3.color(ColorInterpolator.compute7(i), ColorInterpolator.compute8(i), ColorInterpolator.compute9(i), ColorInterpolator.compute6(i));
      this.invoke11(vertexConsumer3);
   }

   private void invoke10(Vec3d vec3d, int i, Vector3f vector3f) {
      Vec3d vec3d15 = this.resolve(vec3d);
      VertexConsumer vertexConsumer4 = this.vertexConsumer.vertex(this.matrix4f, (float)vec3d15.x, (float)vec3d15.y, (float)vec3d15.z);
      vertexConsumer4.color(ColorInterpolator.compute7(i), ColorInterpolator.compute8(i), ColorInterpolator.compute9(i), ColorInterpolator.compute6(i));
      vertexConsumer4.normal(vector3f.x, vector3f.y, vector3f.z);
      this.invoke11(vertexConsumer4);
   }

   private void invoke11(VertexConsumer vertexConsumer) {
      Objects.requireNonNull(vertexConsumer, "vertex");

      try {
         Method method = vertexConsumer.getClass().getMethod("next");
         method.invoke(vertexConsumer);
      } catch (NoSuchMethodException noSuchMethodException) {
      } catch (IllegalAccessException illegalAccessException) {
         throw new IllegalStateException("Unable to access vertex finalization method", illegalAccessException);
      } catch (InvocationTargetException invocationTargetException) {
         Throwable exception = invocationTargetException.getCause();
         if (exception instanceof RuntimeException exception2) {
            throw exception2;
         }

         if (exception instanceof Error error) {
            throw error;
         }

         throw new IllegalStateException("Vertex finalization failed", exception);
      }
   }

   private Vec3d resolve(Vec3d vec3d) {
      return vec3d.subtract(this.vec3d);
   }

   private Vector3f resolve2(Vec3d vec3d, Vec3d vec3d2) {
      Vec3d vec3d16 = vec3d2.subtract(vec3d);
      Vector3f vector3f3 = new Vector3f((float)vec3d16.x, (float)vec3d16.y, (float)vec3d16.z);
      if (vector3f3.lengthSquared() <= 1.0E-6F) {
         vector3f3.set(0.0F, 1.0F, 0.0F);
      }

      vector3f3.normalize();
      this.matrix3f.transform(vector3f3);
      if (vector3f3.lengthSquared() <= 1.0E-6F) {
         vector3f3.set(0.0F, 1.0F, 0.0F);
      }

      vector3f3.normalize();
      return vector3f3;
   }
}
