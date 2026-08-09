package ru.metaculture.protection;

import java.util.ArrayDeque;
import java.util.Arrays;

public final class Matrix3Stack {
   private final ArrayDeque<float[]> arrayDeque = new ArrayDeque<>();

   public Matrix3Stack() {
      this.invoke2();
   }

   public void invoke() {
      this.arrayDeque.clear();
      this.invoke2();
   }

   public void invoke2() {
      this.arrayDeque.push(new float[]{1.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 1.0F});
   }

   public void invoke3(float f) {
      float floatValue = (float)Math.toRadians(f);
      float floatValue2 = (float)Math.cos(floatValue);
      float floatValue3 = (float)Math.sin(floatValue);
      float[] floatValues = new float[]{floatValue2, -floatValue3, 0.0F, floatValue3, floatValue2, 0.0F, 0.0F, 0.0F, 1.0F};
      float[] floatValues2 = this.arrayDeque.peek();
      this.arrayDeque.push(resolve3(floatValues2, floatValues));
   }

   public void invoke4(float f, float g) {
      float[] floatValues3 = new float[]{1.0F, 0.0F, f, 0.0F, 1.0F, g, 0.0F, 0.0F, 1.0F};
      float[] floatValues4 = this.arrayDeque.peek();
      this.arrayDeque.push(resolve3(floatValues4, floatValues3));
   }

   public void invoke5(float f, float g) {
      this.invoke4(-f, -g);
   }

   public void invoke6(float f, float g, float h, float i) {
      float floatValue4 = h - h * f;
      float floatValue5 = i - i * g;
      float[] floatValues5 = new float[]{f, 0.0F, floatValue4, 0.0F, g, floatValue5, 0.0F, 0.0F, 1.0F};
      float[] floatValues6 = this.arrayDeque.peek();
      this.arrayDeque.push(resolve3(floatValues6, floatValues5));
   }

   public void invoke7(float f, float g, float h) {
      this.invoke6(f, f, g, h);
   }

   public void invoke8(float[] fs) {
      if (fs != null && fs.length == 9) {
         for (float floatValue6 : fs) {
            if (!Float.isFinite(floatValue6)) {
               throw new IllegalArgumentException("matrix entries must be finite");
            }
         }

         this.arrayDeque.push(resolve3(this.arrayDeque.peek(), fs));
      } else {
         throw new IllegalArgumentException("matrix must have length 9");
      }
   }

   public void invoke9(float[] fs) {
      if (fs == null) {
         throw new IllegalArgumentException("matrix must not be null");
      } else if (fs.length != 9) {
         throw new IllegalArgumentException("matrix must have length 9");
      } else {
         for (float floatValue7 : fs) {
            if (!Float.isFinite(floatValue7)) {
               throw new IllegalArgumentException("matrix entries must be finite");
            }
         }

         if (this.arrayDeque.isEmpty()) {
            throw new IllegalStateException("cannot replace top matrix on an empty stack");
         } else {
            float[] floatValues7 = Arrays.copyOf(fs, fs.length);
            this.arrayDeque.pop();
            this.arrayDeque.push(floatValues7);
         }
      }
   }

   public ArrayDeque<float[]> resolve() {
      ArrayDeque arrayDeque2 = new ArrayDeque();

      for (float[] floatValues8 : this.arrayDeque) {
         arrayDeque2.addLast(Arrays.copyOf(floatValues8, floatValues8.length));
      }

      return arrayDeque2;
   }

   public void invoke10(ArrayDeque<float[]> arrayDeque) {
      this.arrayDeque.clear();
      if (arrayDeque != null) {
         for (float[] floatValues9 : arrayDeque) {
            if (floatValues9 != null && floatValues9.length == 9) {
               this.arrayDeque.addLast(Arrays.copyOf(floatValues9, floatValues9.length));
            }
         }
      }

      if (this.arrayDeque.isEmpty()) {
         this.invoke2();
      }
   }

   public void invoke11() {
      if (this.arrayDeque.size() > 1) {
         this.arrayDeque.pop();
      }
   }

   public void invoke12(int i) {
      for (int intValue = 0; intValue < i; intValue++) {
         if (this.arrayDeque.size() > 1) {
            this.arrayDeque.pop();
         }
      }
   }

   public float[] resolve2() {
      return this.arrayDeque.peek();
   }

   private static float[] resolve3(float[] fs, float[] gs) {
      return new float[]{
         fs[0] * gs[0] + fs[1] * gs[3] + fs[2] * gs[6],
         fs[0] * gs[1] + fs[1] * gs[4] + fs[2] * gs[7],
         fs[0] * gs[2] + fs[1] * gs[5] + fs[2] * gs[8],
         fs[3] * gs[0] + fs[4] * gs[3] + fs[5] * gs[6],
         fs[3] * gs[1] + fs[4] * gs[4] + fs[5] * gs[7],
         fs[3] * gs[2] + fs[4] * gs[5] + fs[5] * gs[8],
         fs[6] * gs[0] + fs[7] * gs[3] + fs[8] * gs[6],
         fs[6] * gs[1] + fs[7] * gs[4] + fs[8] * gs[7],
         fs[6] * gs[2] + fs[7] * gs[5] + fs[8] * gs[8]
      };
   }
}
