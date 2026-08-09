package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.List;

public final class StudioModel {
   private final int intValue;
   private final int intValue2;
   private final List<StudioModel.StudioModelState6> items;
   private final List<StudioModel.StudioModelState> items2;
   private float floatValue = Float.MAX_VALUE;
   private float floatValue2 = Float.MAX_VALUE;
   private float floatValue3 = Float.MAX_VALUE;
   private float floatValue4 = -Float.MAX_VALUE;
   private float floatValue5 = -Float.MAX_VALUE;
   private float floatValue6 = -Float.MAX_VALUE;
   private boolean flag;

   public StudioModel(int i, int j, List<StudioModel.StudioModelState6> list, List<StudioModel.StudioModelState> list2) {
      this.intValue = Math.max(1, i);
      this.intValue2 = Math.max(1, j);
      this.items = (List<StudioModel.StudioModelState6>)(list == null ? new ArrayList<>() : list);
      this.items2 = (List<StudioModel.StudioModelState>)(list2 == null ? new ArrayList<>() : list2);
   }

   public int getIntValue() {
      return this.intValue;
   }

   public int getIntValue2() {
      return this.intValue2;
   }

   public List<StudioModel.StudioModelState6> getItems() {
      return this.items;
   }

   public List<StudioModel.StudioModelState> getItems2() {
      return this.items2;
   }

   public StudioModel.StudioModelState6 resolve(int i) {
      if (i >= 0 && i < this.items.size()) {
         return this.items.get(i);
      } else {
         return this.items.isEmpty() ? null : this.items.get(0);
      }
   }

   public float measure() {
      this.invoke2();
      return this.floatValue2;
   }

   public float measure2() {
      this.invoke2();
      return this.floatValue5;
   }

   public float measure3() {
      this.invoke2();
      return (this.floatValue + this.floatValue4) * 0.5F;
   }

   public float measure4() {
      this.invoke2();
      return (this.floatValue2 + this.floatValue5) * 0.5F;
   }

   public float measure5() {
      this.invoke2();
      return (this.floatValue3 + this.floatValue6) * 0.5F;
   }

   public float measure6() {
      this.invoke2();
      float floatValue = this.floatValue5 - this.floatValue2;
      return floatValue <= 0.0F ? 32.0F : floatValue;
   }

   public int compute() {
      int[] intValues = new int[]{0};

      for (StudioModel.StudioModelState studioModelState : this.items2) {
         this.invoke(studioModelState, intValues);
      }

      return intValues[0];
   }

   private void invoke(StudioModel.StudioModelState studioModelState2, int[] is) {
      is[0] += studioModelState2.getItems2().size();

      for (StudioModel.StudioModelState studioModelState3 : studioModelState2.getItems()) {
         this.invoke(studioModelState3, is);
      }
   }

   public float measure7() {
      this.invoke2();
      float floatValue2 = this.floatValue4 - this.floatValue;
      return floatValue2 <= 0.0F ? 16.0F : floatValue2;
   }

   public float measure8() {
      this.invoke2();
      float floatValue3 = this.floatValue6 - this.floatValue3;
      return floatValue3 <= 0.0F ? 16.0F : floatValue3;
   }

   private void invoke2() {
      if (!this.flag) {
         this.flag = true;

         for (StudioModel.StudioModelState studioModelState4 : this.items2) {
            this.invoke3(studioModelState4);
         }

         if (this.floatValue2 > this.floatValue5) {
            this.floatValue = this.floatValue2 = this.floatValue3 = 0.0F;
            this.floatValue4 = this.floatValue5 = this.floatValue6 = 32.0F;
         }
      }
   }

   private void invoke3(StudioModel.StudioModelState studioModelState5) {
      for (StudioModel.StudioModelState2 studioModelState22 : studioModelState5.getItems2()) {
         this.invoke4(studioModelState22.getFloatValue(), studioModelState22.getFloatValue2(), studioModelState22.getFloatValue3());
         this.invoke4(studioModelState22.getFloatValue4(), studioModelState22.getFloatValue5(), studioModelState22.getFloatValue6());
      }

      for (StudioModel.StudioModelState4 studioModelState42 : studioModelState5.getItems3()) {
         float[] floatValues = studioModelState42.getFloats();

         for (byte byteValue = 0; byteValue + 2 < floatValues.length; byteValue += 3) {
            this.invoke4(studioModelState42.getFloatValue() + floatValues[byteValue], studioModelState42.getFloatValue2() + floatValues[byteValue + 1], studioModelState42.getFloatValue3() + floatValues[byteValue + 2]);
         }
      }

      for (StudioModel.StudioModelState studioModelState6 : studioModelState5.getItems()) {
         this.invoke3(studioModelState6);
      }
   }

   private void invoke4(float f, float g, float h) {
      if (f < this.floatValue) {
         this.floatValue = f;
      }

      if (g < this.floatValue2) {
         this.floatValue2 = g;
      }

      if (h < this.floatValue3) {
         this.floatValue3 = h;
      }

      if (f > this.floatValue4) {
         this.floatValue4 = f;
      }

      if (g > this.floatValue5) {
         this.floatValue5 = g;
      }

      if (h > this.floatValue6) {
         this.floatValue6 = h;
      }
   }

   public static final class StudioModelState {
      private final String text;
      private final float floatValue;
      private final float floatValue2;
      private final float floatValue3;
      private final float floatValue4;
      private final float floatValue5;
      private final float floatValue6;
      private final List<StudioModel.StudioModelState> items = new ArrayList<>();
      private final List<StudioModel.StudioModelState2> items2 = new ArrayList<>();
      private final List<StudioModel.StudioModelState4> items3 = new ArrayList<>();

      public StudioModelState(String string, float f, float g, float h, float i, float j, float k) {
         this.text = string == null ? "" : string;
         this.floatValue = f;
         this.floatValue2 = g;
         this.floatValue3 = h;
         this.floatValue4 = i;
         this.floatValue5 = j;
         this.floatValue6 = k;
      }

      public String getText() {
         return this.text;
      }

      public float getFloatValue() {
         return this.floatValue;
      }

      public float getFloatValue2() {
         return this.floatValue2;
      }

      public float getFloatValue3() {
         return this.floatValue3;
      }

      public float getFloatValue4() {
         return this.floatValue4;
      }

      public float getFloatValue5() {
         return this.floatValue5;
      }

      public float getFloatValue6() {
         return this.floatValue6;
      }

      public boolean check() {
         return this.floatValue4 != 0.0F || this.floatValue5 != 0.0F || this.floatValue6 != 0.0F;
      }

      public List<StudioModel.StudioModelState> getItems() {
         return this.items;
      }

      public List<StudioModel.StudioModelState2> getItems2() {
         return this.items2;
      }

      public List<StudioModel.StudioModelState4> getItems3() {
         return this.items3;
      }
   }

   public static final class StudioModelState2 {
      private final float floatValue;
      private final float floatValue2;
      private final float floatValue3;
      private final float floatValue4;
      private final float floatValue5;
      private final float floatValue6;
      private final float floatValue7;
      private final float floatValue8;
      private final float floatValue9;
      private final float floatValue10;
      private final float floatValue11;
      private final float floatValue12;
      private final float floatValue13;
      private final StudioModel.StudioModelState3[] studioModelState3s;

      public StudioModelState2(
         float f, float g, float h, float i, float j, float k, float l, float m, float n, float o, float p, float q, float r, StudioModel.StudioModelState3[] w198s
      ) {
         this.floatValue = f;
         this.floatValue2 = g;
         this.floatValue3 = h;
         this.floatValue4 = i;
         this.floatValue5 = j;
         this.floatValue6 = k;
         this.floatValue7 = l;
         this.floatValue8 = m;
         this.floatValue9 = n;
         this.floatValue10 = o;
         this.floatValue11 = p;
         this.floatValue12 = q;
         this.floatValue13 = r;
         this.studioModelState3s = w198s;
      }

      public float getFloatValue() {
         return this.floatValue;
      }

      public float getFloatValue2() {
         return this.floatValue2;
      }

      public float getFloatValue3() {
         return this.floatValue3;
      }

      public float getFloatValue4() {
         return this.floatValue4;
      }

      public float getFloatValue5() {
         return this.floatValue5;
      }

      public float getFloatValue6() {
         return this.floatValue6;
      }

      public float getFloatValue7() {
         return this.floatValue7;
      }

      public float getFloatValue8() {
         return this.floatValue8;
      }

      public float getFloatValue9() {
         return this.floatValue9;
      }

      public float getFloatValue10() {
         return this.floatValue10;
      }

      public float getFloatValue11() {
         return this.floatValue11;
      }

      public float getFloatValue12() {
         return this.floatValue12;
      }

      public float getFloatValue13() {
         return this.floatValue13;
      }

      public boolean check() {
         return this.floatValue10 != 0.0F || this.floatValue11 != 0.0F || this.floatValue12 != 0.0F;
      }

      public StudioModel.StudioModelState3 resolve(int i) {
         return i >= 0 && i < this.studioModelState3s.length ? this.studioModelState3s[i] : null;
      }
   }

   public static final class StudioModelState3 {
      private final int intValue;
      private final float floatValue;
      private final float floatValue2;
      private final float floatValue3;
      private final float floatValue4;

      public StudioModelState3(int i, float f, float g, float h, float j) {
         this.intValue = i;
         this.floatValue = f;
         this.floatValue2 = g;
         this.floatValue3 = h;
         this.floatValue4 = j;
      }

      public int getIntValue() {
         return this.intValue;
      }

      public float getFloatValue() {
         return this.floatValue;
      }

      public float getFloatValue2() {
         return this.floatValue2;
      }

      public float getFloatValue3() {
         return this.floatValue3;
      }

      public float getFloatValue4() {
         return this.floatValue4;
      }
   }

   public static final class StudioModelState4 {
      private final float floatValue;
      private final float floatValue2;
      private final float floatValue3;
      private final float floatValue4;
      private final float floatValue5;
      private final float floatValue6;
      private final float[] floats;
      private final StudioModel.StudioModelState5[] studioModelState5s;

      public StudioModelState4(float f, float g, float h, float i, float j, float k, float[] fs, StudioModel.StudioModelState5[] w200s) {
         this.floatValue = f;
         this.floatValue2 = g;
         this.floatValue3 = h;
         this.floatValue4 = i;
         this.floatValue5 = j;
         this.floatValue6 = k;
         this.floats = fs;
         this.studioModelState5s = w200s;
      }

      public float getFloatValue() {
         return this.floatValue;
      }

      public float getFloatValue2() {
         return this.floatValue2;
      }

      public float getFloatValue3() {
         return this.floatValue3;
      }

      public float getFloatValue4() {
         return this.floatValue4;
      }

      public float getFloatValue5() {
         return this.floatValue5;
      }

      public float getFloatValue6() {
         return this.floatValue6;
      }

      public float[] getFloats() {
         return this.floats;
      }

      public StudioModel.StudioModelState5[] getStudioModelState5s() {
         return this.studioModelState5s;
      }

      public boolean check() {
         return this.floatValue4 != 0.0F || this.floatValue5 != 0.0F || this.floatValue6 != 0.0F;
      }

      public float measure(int i) {
         return this.floats[i * 3];
      }

      public float measure2(int i) {
         return this.floats[i * 3 + 1];
      }

      public float measure3(int i) {
         return this.floats[i * 3 + 2];
      }
   }

   public static final class StudioModelState5 {
      private final int intValue;
      private final int[] ints;
      private final float[] floats;
      private final float[] floats2;
      private final int intValue2;

      public StudioModelState5(int i, int[] is, float[] fs, float[] gs, int j) {
         this.intValue = i;
         this.ints = is;
         this.floats = fs;
         this.floats2 = gs;
         this.intValue2 = j;
      }

      public int getIntValue() {
         return this.intValue;
      }

      public int compute(int i) {
         return this.ints[i];
      }

      public float measure(int i) {
         return this.floats[i];
      }

      public float measure2(int i) {
         return this.floats2[i];
      }

      public int getIntValue2() {
         return this.intValue2;
      }
   }

   public static final class StudioModelState6 {
      private final String text;
      private final byte[] bytes;
      private final int intValue;
      private final int intValue2;

      public StudioModelState6(String string, byte[] bs, int i, int j) {
         this.text = string == null ? "texture" : string;
         this.bytes = bs == null ? new byte[0] : bs;
         this.intValue = Math.max(1, i);
         this.intValue2 = Math.max(1, j);
      }

      public String getText() {
         return this.text;
      }

      public byte[] getBytes() {
         return this.bytes;
      }

      public int getIntValue() {
         return this.intValue;
      }

      public int getIntValue2() {
         return this.intValue2;
      }
   }
}
