package ru.metaculture.protection;

import java.util.Arrays;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

final class ScreenTransitionShader implements AutoCloseable {
   private final int intValue;
   private final int intValue2;
   private final int[] ints;
   private final int[] ints2;
   private boolean flag;

   private ScreenTransitionShader(int i, int j, int[] is, int[] js) {
      this.intValue = i;
      this.intValue2 = j;
      this.ints = is;
      this.ints2 = js;
   }

   static ScreenTransitionShader resolve(int i, int... is) {
      int intValue = Math.max(0, GL11.glGetInteger(34016) - 33984);
      int[] intValues = resolve2(is);
      int[] intValues2 = new int[intValues.length];
      if (intValues.length > 0) {
         GL13.glActiveTexture(33984 + i);

         for (int intValue2 = 0; intValue2 < intValues.length; intValue2++) {
            intValues2[intValue2] = GL11.glGetInteger(compute(intValues[intValue2]));
         }

         GL13.glActiveTexture(33984 + intValue);
      }

      return new ScreenTransitionShader(i, intValue, intValues, intValues2);
   }

   @Override
   public void close() {
      if (!this.flag) {
         this.flag = true;
         if (this.ints.length > 0) {
            GL13.glActiveTexture(33984 + this.intValue);

            for (int intValue3 = 0; intValue3 < this.ints.length; intValue3++) {
               GL11.glBindTexture(this.ints[intValue3], this.ints2[intValue3]);
            }
         }

         GL13.glActiveTexture(33984 + this.intValue2);
      }
   }

   private static int[] resolve2(int[] is) {
      if (is != null && is.length != 0) {
         int[] intValues3 = Arrays.copyOf(is, is.length);
         int intValue4 = 0;

         for (int intValue5 : intValues3) {
            if (intValue5 > 0) {
               boolean flag = false;

               for (int intValue6 = 0; intValue6 < intValue4; intValue6++) {
                  if (intValues3[intValue6] == intValue5) {
                     flag = true;
                     break;
                  }
               }

               if (!flag) {
                  intValues3[intValue4++] = intValue5;
               }
            }
         }

         return Arrays.copyOf(intValues3, intValue4);
      } else {
         return new int[0];
      }
   }

   private static int compute(int i) {
      switch (i) {
         case 3552:
            return 32872;
         case 3553:
            return 32873;
         case 32879:
            return 32874;
         case 34037:
            return 34038;
         case 34067:
            return 34068;
         case 35864:
            return 35868;
         case 35866:
            return 35869;
         case 35882:
            return 35884;
         case 36873:
            return 36874;
         default:
            return 32873;
      }
   }
}
