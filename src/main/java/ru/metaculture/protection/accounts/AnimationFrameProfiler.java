package ru.metaculture.protection;

import java.util.Arrays;

public final class AnimationFrameProfiler implements AnimationSystem.AnimationSystemPredicate {
   public static final AnimationFrameProfiler INSTANCE = new AnimationFrameProfiler();
   private static final int INT_VALUE = 128;
   private static final int INT_VALUE_2 = -1;
   private static final float FLOAT_VALUE = 0.004166667F;
   private static final int INT_VALUE_3 = 60;
   private static final float FLOAT_VALUE_2 = 1.0E-4F;
   private static final float FLOAT_VALUE_3 = 0.25F;
   private static final float FLOAT_VALUE_4 = 0.016666668F;
   private float[] floats;
   private float[] floats2;
   private float[] floats3;
   private float[] floats4;
   private float[] floats5;
   private float[] floats6;
   private float[] floats7;
   private int[] ints;
   private int[] ints2;
   private int[] ints3;
   private int intValue = 128;
   private int intValue2;
   private int intValue3;
   private float floatValue;
   private boolean flag;

   private AnimationFrameProfiler() {
      this.floats = new float[this.intValue];
      this.floats2 = new float[this.intValue];
      this.floats3 = new float[this.intValue];
      this.floats4 = new float[this.intValue];
      this.floats5 = new float[this.intValue];
      this.floats6 = new float[this.intValue];
      this.floats7 = new float[this.intValue];
      this.ints = new int[this.intValue];
      this.ints2 = new int[this.intValue];
      this.ints3 = new int[this.intValue];
      Arrays.fill(this.ints, -1);
      Arrays.fill(this.ints2, -1);

      for (int intValue = 0; intValue < this.intValue; intValue++) {
         this.ints3[intValue] = this.intValue - 1 - intValue;
      }

      this.intValue3 = this.intValue;
   }

   public int compute(float f, SpringSpec springSpec) {
      SpringSpec springSpec2 = springSpec == null ? SpringSpec.resolve2() : springSpec;
      return this.compute2(f, springSpec2.getFloatValue(), springSpec2.getFloatValue2(), springSpec2.getFloatValue3(), springSpec2.getFloatValue4());
   }

   public int compute2(float f, float g, float h, float i, float j) {
      if (this.intValue2 == this.intValue) {
         this.invoke5();
      }

      int intValue2 = this.ints3[--this.intValue3];
      int intValue3 = this.intValue2++;
      this.floats[intValue3] = f;
      this.floats2[intValue3] = f;
      this.floats3[intValue3] = 0.0F;
      this.floats4[intValue3] = g;
      this.floats5[intValue3] = h;
      this.floats6[intValue3] = i;
      this.floats7[intValue3] = j;
      this.ints2[intValue3] = intValue2;
      this.ints[intValue2] = intValue3;
      this.invoke4();
      return intValue2;
   }

   public void invoke(int i) {
      if (i >= 0 && i < this.intValue) {
         int intValue4 = this.ints[i];
         if (intValue4 != -1) {
            int intValue5 = --this.intValue2;
            if (intValue4 != intValue5) {
               this.floats[intValue4] = this.floats[intValue5];
               this.floats2[intValue4] = this.floats2[intValue5];
               this.floats3[intValue4] = this.floats3[intValue5];
               this.floats4[intValue4] = this.floats4[intValue5];
               this.floats5[intValue4] = this.floats5[intValue5];
               this.floats6[intValue4] = this.floats6[intValue5];
               this.floats7[intValue4] = this.floats7[intValue5];
               int intValue6 = this.ints2[intValue5];
               this.ints2[intValue4] = intValue6;
               this.ints[intValue6] = intValue4;
            }

            this.ints2[intValue5] = -1;
            this.ints[i] = -1;
            this.ints3[this.intValue3++] = i;
         }
      }
   }

   public void invoke2(int i, float f) {
      int intValue7 = this.compute3(i);
      if (intValue7 != -1) {
         this.floats2[intValue7] = f;
      }
   }

   public void invoke3(int i, float f) {
      int intValue8 = this.compute3(i);
      if (intValue8 != -1) {
         this.floats[intValue8] = f;
         this.floats2[intValue8] = f;
         this.floats3[intValue8] = 0.0F;
      }
   }

   public float measure(int i) {
      int intValue9 = this.compute3(i);
      return intValue9 == -1 ? 0.0F : this.floats[intValue9];
   }

   public float measure2(int i) {
      int intValue10 = this.compute3(i);
      return intValue10 == -1 ? 0.0F : this.floats2[intValue10];
   }

   public boolean check(int i) {
      int intValue11 = this.compute3(i);
      return intValue11 == -1
         ? true
         : Math.abs(this.floats2[intValue11] - this.floats[intValue11]) <= this.floats6[intValue11]
            && Math.abs(this.floats3[intValue11]) <= this.floats7[intValue11];
   }

   public int getIntValue2() {
      return this.intValue2;
   }

   @Override
   public boolean check2(float f) {
      int intValue12 = this.intValue2;
      if (intValue12 == 0) {
         this.floatValue = 0.0F;
         return true;
      } else {
         float floatValue = f;
         if (!Float.isFinite(f) || f <= 0.0F) {
            floatValue = 0.016666668F;
         } else if (f < 1.0E-4F) {
            floatValue = 1.0E-4F;
         } else if (f > 0.25F) {
            floatValue = 0.25F;
         }

         this.floatValue += floatValue;
         float[] floatValues = this.floats;
         float[] floatValues2 = this.floats2;
         float[] floatValues3 = this.floats3;
         float[] floatValues4 = this.floats4;
         float[] floatValues5 = this.floats5;
         float[] floatValues6 = this.floats6;
         float[] floatValues7 = this.floats7;

         int intValue13;
         for (intValue13 = 0; this.floatValue >= 0.004166667F && intValue13 < 60; intValue13++) {
            for (int intValue14 = 0; intValue14 < intValue12; intValue14++) {
               float floatValue2 = floatValues[intValue14];
               float floatValue3 = floatValues2[intValue14];
               float floatValue4 = floatValues3[intValue14] + (floatValue3 - floatValue2) * floatValues4[intValue14] - floatValues3[intValue14] * floatValues5[intValue14];
               floatValue2 += floatValue4;
               if (Math.abs(floatValue3 - floatValue2) <= floatValues6[intValue14] && Math.abs(floatValue4) <= floatValues7[intValue14]) {
                  floatValue2 = floatValue3;
                  floatValue4 = 0.0F;
               }

               floatValues[intValue14] = floatValue2;
               floatValues3[intValue14] = floatValue4;
            }

            this.floatValue -= 0.004166667F;
         }

         if (intValue13 == 60) {
            this.floatValue = 0.0F;
         }

         return true;
      }
   }

   private int compute3(int i) {
      return i >= 0 && i < this.intValue ? this.ints[i] : -1;
   }

   private void invoke4() {
      if (!this.flag) {
         this.flag = true;
         AnimationSystem.getINSTANCE().invoke3(this);
      }
   }

   private void invoke5() {
      int intValue15 = this.intValue << 1;
      this.floats = Arrays.copyOf(this.floats, intValue15);
      this.floats2 = Arrays.copyOf(this.floats2, intValue15);
      this.floats3 = Arrays.copyOf(this.floats3, intValue15);
      this.floats4 = Arrays.copyOf(this.floats4, intValue15);
      this.floats5 = Arrays.copyOf(this.floats5, intValue15);
      this.floats6 = Arrays.copyOf(this.floats6, intValue15);
      this.floats7 = Arrays.copyOf(this.floats7, intValue15);
      this.ints = Arrays.copyOf(this.ints, intValue15);
      this.ints2 = Arrays.copyOf(this.ints2, intValue15);
      this.ints3 = Arrays.copyOf(this.ints3, intValue15);

      for (int intValue16 = this.intValue; intValue16 < intValue15; this.ints3[this.intValue3++] = intValue16++) {
         this.ints[intValue16] = -1;
         this.ints2[intValue16] = -1;
      }

      this.intValue = intValue15;
   }
}
