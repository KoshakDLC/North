package ru.metaculture.protection;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;
import lombok.Generated;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;

public final class MathUtils implements MinecraftAccessor {
   public static final Matrix4f MATRIX4F = new Matrix4f();
   public static final Matrix4f MATRIX4F_2 = new Matrix4f();
   public static final Matrix4f MATRIX4F_3 = new Matrix4f();

   public static float measure(float f, float g) {
      return f - g / 2.0F;
   }

   public static float measure2(float f) {
      f %= 360.0F;
      if (f >= 180.0F) {
         f -= 360.0F;
      }

      if (f < -180.0F) {
         f += 360.0F;
      }

      return f;
   }

   public static Vec3d resolve(Vec3d vec3d) {
      Camera camera = a_.gameRenderer == null ? null : a_.gameRenderer.getCamera();
      if (camera == null && a_.getEntityRenderDispatcher() != null) {
         camera = a_.getEntityRenderDispatcher().camera;
      }

      if (vec3d != null && camera != null && a_.getWindow() != null) {
         int intValue = a_.getWindow().getHeight();
         int[] intValues = new int[4];
         GL11.glGetIntegerv(2978, intValues);
         Vector3f vector3f = new Vector3f();
         double doubleValue = vec3d.x - camera.getPos().x;
         double doubleValue2 = vec3d.y - camera.getPos().y;
         double doubleValue3 = vec3d.z - camera.getPos().z;
         Vector4f vector4f = new Vector4f((float)doubleValue, (float)doubleValue2, (float)doubleValue3, 1.0F).mul(MATRIX4F_3);
         Matrix4f matrix4f = new Matrix4f(MATRIX4F);
         Matrix4f matrix4f2 = new Matrix4f(MATRIX4F_2);
         matrix4f.mul(matrix4f2).project(vector4f.x(), vector4f.y(), vector4f.z(), intValues, vector3f);
         return new Vec3d(vector3f.x, intValue - vector3f.y, vector3f.z);
      } else {
         return new Vec3d(0.0, 0.0, 2.0);
      }
   }

   public static double measure3() {
      return a_.getWindow().getScaleFactor();
   }

   public static float measure4(float f, float g, float h, float i, float j) {
      return h - g == 0.0F ? i : i + (j - i) * ((f - g) / (h - g));
   }

   private static void invoke(double d, double e) {
      if (e < d) {
         throw new IllegalArgumentException("max не может быть меньше min.");
      }
   }

   public static double measure5(double d, int i) {
      return Math.round(d * Math.pow(10.0, i)) / Math.pow(10.0, i);
   }

   public static float measure6(float f, float g, float h) {
      return (f - g) / (h - g);
   }

   public static Vector2f resolve2(Entity entity) {
      Vec3d vec3d3 = entity.getPos().subtract(MinecraftClient.getInstance().player.getPos());
      double doubleValue4 = Math.hypot(vec3d3.x, vec3d3.z);
      return new Vector2f((float)Math.toDegrees(Math.atan2(vec3d3.z, vec3d3.x)) - 90.0F, (float)(-Math.toDegrees(Math.atan2(vec3d3.y, doubleValue4))));
   }

   static float measure7(float f) {
      if ((f = f % 360.0F) >= 180.0F) {
         f -= 360.0F;
      }

      if (f < -180.0F) {
         f += 360.0F;
      }

      return f;
   }

   public static float measure8(float f) {
      return (f > 0.5 ? 1.0F - f : f) * 2.0F;
   }

   public static double measure9(double d, double e, double f) {
      return d + f * (e - d);
   }

   public static int compute(int i, int j, float f) {
      return i + (int)(f * (j - i));
   }

   public static float measure10(float f, float g, float h) {
      return f + h * (g - f);
   }

   public static boolean check(double d, double e, float f, float g, float h, float i) {
      return d >= f && d <= f + h && e >= g && e <= g + i;
   }

   public static double measure11(double d, double e, double f) {
      return e + (d - e) * f;
   }

   public static Vec3d resolve3(Vec3d vec3d, Vec3d vec3d2, float f) {
      return new Vec3d(
         measure11(vec3d.getX(), vec3d2.getX(), (double)f),
         measure11(vec3d.getY(), vec3d2.getY(), (double)f),
         measure11(vec3d.getZ(), vec3d2.getZ(), (double)f)
      );
   }

   public static double measure12(double d, double e, double f) {
      return e + d * (f - e);
   }

   public static int compute2(int i, int j) {
      return i + (int)(Math.random() * (j - i + 1));
   }

   public static boolean check2(float f, float g, float h, float i, float j, float k) {
      return f > h && f < h + j && g > i && g < i + k;
   }

   public static float measure13(float f, float g) {
      return (float)(Math.random() * (g - f) + f);
   }

   public static double measure14(double d, double e, long l, DualTimer dualTimer) {
      double doubleValue5 = 0.0;
      if (dualTimer.check(l)) {
         doubleValue5 = measure13((float)d, (float)e);
         dualTimer.invoke();
      }

      return doubleValue5;
   }

   public static float measure15(float f, float g, float h) {
      return (1.0F - MathHelper.clamp(measure21() * h, 0.0F, 1.0F)) * f + MathHelper.clamp(measure21() * h, 0.0F, 1.0F) * g;
   }

   public static double measure16(double d, double e) {
      if (d == e) {
         return d;
      } else {
         if (d > e) {
            double doubleValue6 = d;
            d = e;
            e = doubleValue6;
         }

         return ThreadLocalRandom.current().nextDouble() * (e - d) + d;
      }
   }

   public static float measure17(float f, float g) {
      return (float)(Math.random() * (g - f) + f);
   }

   public static double measure18(double d, double e) {
      invoke(d, e);
      return d + ThreadLocalRandom.current().nextDouble() * (e - d);
   }

   public static float measure19(float f, float g) {
      invoke(f, g);
      return f + ThreadLocalRandom.current().nextFloat() * (g - f);
   }

   public static double measure20(double d, double e) {
      return d - e;
   }

   public static float measure21() {
      float floatValue = a_.getCurrentFps();
      return floatValue > 0.0F ? 1.0F / floatValue : 1.0F;
   }

   public static String resolve4(long l) {
      long longValue = l / 3600000L;
      long longValue2 = l % 3600000L / 60000L;
      long longValue3 = l % 360000L % 60000L / 1000L;
      return String.format("%02d:%02d:%02d", longValue, longValue2, longValue3);
   }

   public static double measure22(double d, int i) {
      double doubleValue7 = Math.pow(10.0, i);
      return Math.round(d * doubleValue7) / doubleValue7;
   }

   public static double measure23(double d, double e) {
      double doubleValue8 = Math.round(d / e) * e;
      BigDecimal bigDecimal = new BigDecimal(doubleValue8);
      bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
      return bigDecimal.doubleValue();
   }

   public static double measure24(double d) {
      return Math.round(d * 100.0) / 100.0;
   }

   public static double measure25(double d, double e) {
      double doubleValue9 = Math.round(d / e) * e;
      return Math.round(doubleValue9 * 100.0) / 100.0;
   }

   public static double measure26(double d, double e, double f) {
      return Math.max(d, Math.min(e, f));
   }

   public static float measure27(float f, float g, float h) {
      return Math.max(f, Math.min(g, h));
   }

   public static int compute3(int i, int j, int k) {
      return Math.max(i, Math.min(j, k));
   }

   public static double measure28(double d) {
      return measure26(0.0, 1.0, d);
   }

   public static float measure29(float f) {
      return measure27(0.0F, 1.0F, f);
   }

   public static double measure30(double d, double e, double f, double g, double h, double i) {
      double doubleValue10 = measure20(g, d);
      double doubleValue11 = measure20(h, e);
      double doubleValue12 = measure20(i, f);
      return MathHelper.sqrt((float)(doubleValue10 * doubleValue10 + doubleValue11 * doubleValue11 + doubleValue12 * doubleValue12));
   }

   public static double measure31(BlockPos blockPos, BlockPos blockPos2) {
      double doubleValue13 = measure20((double)blockPos.getX(), (double)blockPos2.getX());
      double doubleValue14 = measure20((double)blockPos.getY(), (double)blockPos2.getY());
      double doubleValue15 = measure20((double)blockPos.getZ(), (double)blockPos2.getZ());
      return MathHelper.sqrt((float)(doubleValue13 * doubleValue13 + doubleValue14 * doubleValue14 + doubleValue15 * doubleValue15));
   }

   public static float measure32(float f, float g, float h, float i, float j) {
      f = measure27(g, h, f);
      float floatValue2 = (f - g) / (h - g);
      return NumberUtils.resolve(i, j, floatValue2);
   }

   @Generated
   private MathUtils() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }
}
