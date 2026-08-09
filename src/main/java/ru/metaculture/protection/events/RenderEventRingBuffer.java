package ru.metaculture.protection;

import java.io.DataOutputStream;
import java.io.IOException;

final class RenderEventRingBuffer {
   private static final int INT_VALUE = 32;
   private final long[] longs = new long[32];
   private final int[] ints = new int[32];
   private final int[] ints2 = new int[32];
   private final int[] ints3 = new int[32];
   private final long[] longs2 = new long[32];
   private int intValue;
   private int intValue2;
   private int intValue3;

   void invoke(long l, int i, int j, int k, long m) {
      int intValue = this.intValue;
      this.longs[intValue] = l;
      this.ints[intValue] = i;
      this.ints2[intValue] = j;
      this.ints3[intValue] = k;
      this.longs2[intValue] = m;
      this.intValue = intValue + 1 & 31;
      if (this.intValue2 < 32) {
         this.intValue2++;
      }

      this.intValue3++;
   }

   int getIntValue3() {
      return this.intValue3;
   }

   int getIntValue2() {
      return this.intValue2;
   }

   int compute() {
      if (this.intValue2 <= 0) {
         return 0;
      } else {
         int intValue2 = this.intValue - 1 & 31;
         return this.ints[intValue2];
      }
   }

   int compute2() {
      if (this.intValue2 <= 0) {
         return 0;
      } else {
         int intValue3 = this.intValue - 1 & 31;
         return this.ints2[intValue3];
      }
   }

   void invoke2(DataOutputStream dataOutputStream) throws IOException {
      dataOutputStream.writeInt(this.intValue2);
      int intValue4 = this.intValue - this.intValue2 & 31;

      for (int intValue5 = 0; intValue5 < this.intValue2; intValue5++) {
         int intValue6 = intValue4 + intValue5 & 31;
         dataOutputStream.writeLong(this.longs[intValue6]);
         dataOutputStream.writeInt(this.ints[intValue6]);
         dataOutputStream.writeInt(this.ints2[intValue6]);
         dataOutputStream.writeInt(this.ints3[intValue6]);
         dataOutputStream.writeLong(this.longs2[intValue6]);
      }
   }
}
