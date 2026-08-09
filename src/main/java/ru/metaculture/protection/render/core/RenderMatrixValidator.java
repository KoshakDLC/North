package ru.metaculture.protection;

import com.mojang.blaze3d.systems.RenderSystem;
import java.io.DataOutputStream;
import java.io.IOException;
import org.joml.Matrix4f;

public final class RenderMatrixValidator {
   private RenderMatrixValidator() {
   }

   static boolean check(RenderStateHasher renderStateHasher) {
      try {
         Matrix4f matrix4f2 = RenderSystem.getModelViewMatrix();
         long longValue = compute(matrix4f2);
         if (renderStateHasher != null) {
            renderStateHasher.invoke2(longValue);
         }

         return check2(matrix4f2);
      } catch (Throwable exception) {
         if (renderStateHasher != null) {
            renderStateHasher.invoke(-1160725808);
         }

         return false;
      }
   }

   static void invoke(DataOutputStream dataOutputStream) throws IOException {
      try {
         Matrix4f matrix4f3 = RenderSystem.getModelViewMatrix();
         dataOutputStream.writeLong(compute(matrix4f3));
         dataOutputStream.writeBoolean(check2(matrix4f3));
         dataOutputStream.writeFloat(matrix4f3.m00());
         dataOutputStream.writeFloat(matrix4f3.m01());
         dataOutputStream.writeFloat(matrix4f3.m02());
         dataOutputStream.writeFloat(matrix4f3.m03());
         dataOutputStream.writeFloat(matrix4f3.m10());
         dataOutputStream.writeFloat(matrix4f3.m11());
         dataOutputStream.writeFloat(matrix4f3.m12());
         dataOutputStream.writeFloat(matrix4f3.m13());
         dataOutputStream.writeFloat(matrix4f3.m20());
         dataOutputStream.writeFloat(matrix4f3.m21());
         dataOutputStream.writeFloat(matrix4f3.m22());
         dataOutputStream.writeFloat(matrix4f3.m23());
         dataOutputStream.writeFloat(matrix4f3.m30());
         dataOutputStream.writeFloat(matrix4f3.m31());
         dataOutputStream.writeFloat(matrix4f3.m32());
         dataOutputStream.writeFloat(matrix4f3.m33());
      } catch (Throwable exception2) {
         dataOutputStream.writeLong(0L);
         dataOutputStream.writeBoolean(false);

         for (int intValue = 0; intValue < 16; intValue++) {
            dataOutputStream.writeFloat(Float.NaN);
         }
      }
   }

   static long compute(Matrix4f matrix4f) {
      if (matrix4f == null) {
         return 0L;
      } else {
         long longValue2 = -3750763034362895579L;
         longValue2 = compute2(longValue2, matrix4f.m00());
         longValue2 = compute2(longValue2, matrix4f.m01());
         longValue2 = compute2(longValue2, matrix4f.m02());
         longValue2 = compute2(longValue2, matrix4f.m03());
         longValue2 = compute2(longValue2, matrix4f.m10());
         longValue2 = compute2(longValue2, matrix4f.m11());
         longValue2 = compute2(longValue2, matrix4f.m12());
         longValue2 = compute2(longValue2, matrix4f.m13());
         longValue2 = compute2(longValue2, matrix4f.m20());
         longValue2 = compute2(longValue2, matrix4f.m21());
         longValue2 = compute2(longValue2, matrix4f.m22());
         longValue2 = compute2(longValue2, matrix4f.m23());
         longValue2 = compute2(longValue2, matrix4f.m30());
         longValue2 = compute2(longValue2, matrix4f.m31());
         longValue2 = compute2(longValue2, matrix4f.m32());
         return compute2(longValue2, matrix4f.m33());
      }
   }

   private static long compute2(long l, float f) {
      l ^= Float.floatToRawIntBits(f);
      return l * 1099511628211L;
   }

   private static boolean check2(Matrix4f matrix4f) {
      return matrix4f != null
         && check3(matrix4f.m00())
         && check3(matrix4f.m01())
         && check3(matrix4f.m02())
         && check3(matrix4f.m03())
         && check3(matrix4f.m10())
         && check3(matrix4f.m11())
         && check3(matrix4f.m12())
         && check3(matrix4f.m13())
         && check3(matrix4f.m20())
         && check3(matrix4f.m21())
         && check3(matrix4f.m22())
         && check3(matrix4f.m23())
         && check3(matrix4f.m30())
         && check3(matrix4f.m31())
         && check3(matrix4f.m32())
         && check3(matrix4f.m33());
   }

   private static boolean check3(float f) {
      return !Float.isNaN(f) && !Float.isInfinite(f);
   }
}
