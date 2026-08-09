package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public final class StudioModelRenderer {
   private static final float FLOAT_VALUE = 1.0F;
   private static final float FLOAT_VALUE_2 = 0.5F;
   private static final float FLOAT_VALUE_3 = 0.82F;
   private static final float FLOAT_VALUE_4 = 0.66F;
   private static final float FLOAT_VALUE_5 = 0.86F;
   private static final int INT_VALUE = 1;
   private final Vector3f vector3f = new Vector3f();
   private final Vector3f vector3f2 = new Vector3f();
   private final Vector3f vector3f3 = new Vector3f();
   private final Matrix4f matrix4f = new Matrix4f();
   private final float[] floats = new float[3];
   private final List<StudioModelRenderer.StudioModelRendererState> items = new ArrayList<>(256);
   private float floatValue;
   private boolean flag;
   private StudioModel studioModel;
   private String text;
   private StudioTextureCache studioTextureCache;
   private float floatValue2;
   private float floatValue3;
   private float floatValue4;
   private float floatValue5;
   private float floatValue6;
   private float floatValue7;
   public int intValue;
   public int intValue2;

   public void invoke(
      RenderManager renderManager, StudioModel studioModel, String string, float f, float g, float h, float i, float j, float k, float l, boolean bl
   ) {
      if (renderManager != null && studioModel != null) {
         this.floatValue = l;
         this.flag = bl;
         this.studioModel = studioModel;
         this.text = string;
         this.studioTextureCache = StudioTextureCache.getINSTANCE();
         this.floatValue2 = f;
         this.floatValue3 = g;
         this.floatValue4 = h;
         this.floatValue5 = studioModel.measure3();
         this.floatValue6 = studioModel.measure4();
         this.floatValue7 = studioModel.measure5();
         this.matrix4f.identity().rotateX((float)Math.toRadians(j)).rotateY((float)Math.toRadians(i));
         this.items.clear();
         Matrix4f matrix4f2 = new Matrix4f();

         for (StudioModel.StudioModelState studioModelState : studioModel.getItems2()) {
            this.invoke2(studioModelState, matrix4f2);
         }

         this.items.sort(Comparator.comparingDouble(studioModelRendererState -> studioModelRendererState.floatValue));
         this.intValue = this.items.size();
         int intValue = 0;

         for (StudioModelRenderer.StudioModelRendererState studioModelRendererState2 : this.items) {
            if (this.check2(renderManager, studioModelRendererState2, k)) {
               intValue++;
            }
         }

         this.intValue2 = intValue;
         this.items.clear();
      }
   }

   private void invoke2(StudioModel.StudioModelState studioModelState2, Matrix4f matrix4f) {
      Matrix4f matrix4f3 = new Matrix4f(matrix4f);
      if (studioModelState2.check()) {
         invoke3(
            matrix4f3,
            studioModelState2.getFloatValue(),
            studioModelState2.getFloatValue2(),
            studioModelState2.getFloatValue3(),
            studioModelState2.getFloatValue6(),
            studioModelState2.getFloatValue5(),
            studioModelState2.getFloatValue4()
         );
      }

      if (this.flag && this.check(studioModelState2.getText())) {
         matrix4f3.translate(studioModelState2.getFloatValue(), studioModelState2.getFloatValue2(), studioModelState2.getFloatValue3())
            .rotateZYX(this.floats[2], this.floats[1], this.floats[0])
            .translate(-studioModelState2.getFloatValue(), -studioModelState2.getFloatValue2(), -studioModelState2.getFloatValue3());
      }

      for (StudioModel.StudioModelState2 studioModelState22 : studioModelState2.getItems2()) {
         this.invoke4(studioModelState22, matrix4f3);
      }

      for (StudioModel.StudioModelState4 studioModelState4 : studioModelState2.getItems3()) {
         this.invoke6(studioModelState4, matrix4f3);
      }

      for (StudioModel.StudioModelState studioModelState3 : studioModelState2.getItems()) {
         this.invoke2(studioModelState3, matrix4f3);
      }
   }

   private static void invoke3(Matrix4f matrix4f, float f, float g, float h, float i, float j, float k) {
      matrix4f.translate(f, g, h).rotateZYX((float)Math.toRadians(i), (float)Math.toRadians(j), (float)Math.toRadians(k)).translate(-f, -g, -h);
   }

   private boolean check(String string) {
      if (string != null && !string.isEmpty()) {
         String text = string.toLowerCase();
         float floatValue = Math.abs(string.hashCode()) % 1000 * 0.0123F;
         this.floats[0] = this.floats[1] = this.floats[2] = 0.0F;
         if (text.contains("tail") || text.startsWith("seg")) {
            this.floats[0] = (float)Math.sin(this.floatValue * 1.9F + floatValue) * 0.16F;
            this.floats[1] = (float)Math.sin(this.floatValue * 1.3F + floatValue) * 0.1F;
            return true;
         } else if (text.contains("ear")) {
            float floatValue2 = text.contains("left") ? 1.0F : -1.0F;
            this.floats[2] = floatValue2 * (0.05F + (float)Math.sin(this.floatValue * 2.4F + floatValue) * 0.08F);
            return true;
         } else if (text.contains("cape") || text.contains("wing")) {
            this.floats[0] = -0.08F + (float)Math.sin(this.floatValue * 1.6F + floatValue) * 0.13F;
            return true;
         } else if (text.equals("head")) {
            this.floats[1] = (float)Math.sin(this.floatValue * 0.5F) * 0.1F;
            this.floats[0] = (float)Math.sin(this.floatValue * 0.4F) * 0.04F;
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private void invoke4(StudioModel.StudioModelState2 studioModelState23, Matrix4f matrix4f) {
      Matrix4f matrix4f4 = new Matrix4f(matrix4f);
      if (studioModelState23.check()) {
         invoke3(
            matrix4f4,
            studioModelState23.getFloatValue7(),
            studioModelState23.getFloatValue8(),
            studioModelState23.getFloatValue9(),
            studioModelState23.getFloatValue12(),
            studioModelState23.getFloatValue11(),
            studioModelState23.getFloatValue10()
         );
      }

      float floatValue3 = studioModelState23.getFloatValue13();
      float floatValue4 = studioModelState23.getFloatValue() - floatValue3;
      float floatValue5 = studioModelState23.getFloatValue2() - floatValue3;
      float floatValue6 = studioModelState23.getFloatValue3() - floatValue3;
      float floatValue7 = studioModelState23.getFloatValue4() + floatValue3;
      float floatValue8 = studioModelState23.getFloatValue5() + floatValue3;
      float floatValue9 = studioModelState23.getFloatValue6() + floatValue3;
      this.invoke5(studioModelState23.resolve(0), matrix4f4, 0.82F, 0.0F, 0.0F, -1.0F, floatValue7, floatValue8, floatValue6, floatValue4, floatValue8, floatValue6, floatValue4, floatValue5, floatValue6, floatValue7, floatValue5, floatValue6);
      this.invoke5(studioModelState23.resolve(2), matrix4f4, 0.82F, 0.0F, 0.0F, 1.0F, floatValue4, floatValue8, floatValue9, floatValue7, floatValue8, floatValue9, floatValue7, floatValue5, floatValue9, floatValue4, floatValue5, floatValue9);
      this.invoke5(studioModelState23.resolve(1), matrix4f4, 0.66F, 1.0F, 0.0F, 0.0F, floatValue7, floatValue8, floatValue9, floatValue7, floatValue8, floatValue6, floatValue7, floatValue5, floatValue6, floatValue7, floatValue5, floatValue9);
      this.invoke5(studioModelState23.resolve(3), matrix4f4, 0.66F, -1.0F, 0.0F, 0.0F, floatValue4, floatValue8, floatValue6, floatValue4, floatValue8, floatValue9, floatValue4, floatValue5, floatValue9, floatValue4, floatValue5, floatValue6);
      this.invoke5(studioModelState23.resolve(4), matrix4f4, 1.0F, 0.0F, 1.0F, 0.0F, floatValue4, floatValue8, floatValue6, floatValue7, floatValue8, floatValue6, floatValue7, floatValue8, floatValue9, floatValue4, floatValue8, floatValue9);
      this.invoke5(studioModelState23.resolve(5), matrix4f4, 0.5F, 0.0F, -1.0F, 0.0F, floatValue4, floatValue5, floatValue9, floatValue7, floatValue5, floatValue9, floatValue7, floatValue5, floatValue6, floatValue4, floatValue5, floatValue6);
   }

   private void invoke5(
      StudioModel.StudioModelState3 studioModelState32,
      Matrix4f matrix4f,
      float f,
      float g,
      float h,
      float i,
      float j,
      float k,
      float l,
      float m,
      float n,
      float o,
      float p,
      float q,
      float r,
      float s,
      float t,
      float u
   ) {
      if (studioModelState32 != null) {
         this.invoke7(
            matrix4f,
            studioModelState32.getIntValue(),
            f,
            g,
            h,
            i,
            studioModelState32.getFloatValue(),
            studioModelState32.getFloatValue2(),
            studioModelState32.getFloatValue3(),
            studioModelState32.getFloatValue2(),
            studioModelState32.getFloatValue3(),
            studioModelState32.getFloatValue4(),
            studioModelState32.getFloatValue(),
            studioModelState32.getFloatValue4(),
            j,
            k,
            l,
            m,
            n,
            o,
            p,
            q,
            r,
            s,
            t,
            u,
            false
         );
      }
   }

   private void invoke6(StudioModel.StudioModelState4 studioModelState42, Matrix4f matrix4f) {
      Matrix4f matrix4f5 = new Matrix4f(matrix4f);
      matrix4f5.translate(studioModelState42.getFloatValue(), studioModelState42.getFloatValue2(), studioModelState42.getFloatValue3());
      if (studioModelState42.check()) {
         matrix4f5.rotateZYX(
            (float)Math.toRadians(studioModelState42.getFloatValue6()),
            (float)Math.toRadians(studioModelState42.getFloatValue5()),
            (float)Math.toRadians(studioModelState42.getFloatValue4())
         );
      }

      for (StudioModel.StudioModelState5 studioModelState5 : studioModelState42.getStudioModelState5s()) {
         int intValue2 = studioModelState5.compute(0);
         int intValue3 = studioModelState5.compute(1);
         int intValue4 = studioModelState5.compute(2);
         int intValue5 = studioModelState5.getIntValue() >= 4 ? studioModelState5.compute(3) : intValue4;
         float floatValue10 = studioModelState42.measure(intValue2);
         float floatValue11 = studioModelState42.measure2(intValue2);
         float floatValue12 = studioModelState42.measure3(intValue2);
         float floatValue13 = studioModelState42.measure(intValue3);
         float floatValue14 = studioModelState42.measure2(intValue3);
         float floatValue15 = studioModelState42.measure3(intValue3);
         float floatValue16 = studioModelState42.measure(intValue4);
         float floatValue17 = studioModelState42.measure2(intValue4);
         float floatValue18 = studioModelState42.measure3(intValue4);
         float floatValue19 = studioModelState42.measure(intValue5);
         float floatValue20 = studioModelState42.measure2(intValue5);
         float floatValue21 = studioModelState42.measure3(intValue5);
         this.vector3f2.set(floatValue13 - floatValue10, floatValue14 - floatValue11, floatValue15 - floatValue12);
         this.vector3f3.set(floatValue16 - floatValue10, floatValue17 - floatValue11, floatValue18 - floatValue12);
         this.vector3f2.cross(this.vector3f3);
         float floatValue22 = studioModelState5.getIntValue() >= 4 ? 3.0F : 2.0F;
         this.invoke7(
            matrix4f5,
            studioModelState5.getIntValue2(),
            0.86F,
            this.vector3f2.x,
            this.vector3f2.y,
            this.vector3f2.z,
            studioModelState5.measure(0),
            studioModelState5.measure2(0),
            studioModelState5.measure(1),
            studioModelState5.measure2(1),
            studioModelState5.measure((int)floatValue22),
            studioModelState5.measure2((int)floatValue22),
            studioModelState5.measure(studioModelState5.getIntValue() >= 4 ? 3 : 2),
            studioModelState5.measure2(studioModelState5.getIntValue() >= 4 ? 3 : 2),
            floatValue10,
            floatValue11,
            floatValue12,
            floatValue13,
            floatValue14,
            floatValue15,
            floatValue16,
            floatValue17,
            floatValue18,
            floatValue19,
            floatValue20,
            floatValue21,
            true
         );
      }
   }

   private void invoke7(
      Matrix4f matrix4f,
      int i,
      float f,
      float g,
      float h,
      float j,
      float k,
      float l,
      float m,
      float n,
      float o,
      float p,
      float q,
      float r,
      float s,
      float t,
      float u,
      float v,
      float w,
      float x,
      float y,
      float z,
      float aa,
      float ab,
      float ac,
      float ad,
      boolean bl
   ) {
      this.vector3f.set(g, h, j);
      matrix4f.transformDirection(this.vector3f);
      this.matrix4f.transformDirection(this.vector3f);
      boolean flag = this.vector3f.z * 1.0F > 0.02F;
      if (flag || bl) {
         if (!flag && bl) {
            f *= 0.78F;
         }

         StudioModelRenderer.StudioModelRendererState studioModelRendererState3 = new StudioModelRenderer.StudioModelRendererState();
         float floatValue23 = 0.0F;
         floatValue23 += this.measure(matrix4f, s, t, u, studioModelRendererState3, 0);
         floatValue23 += this.measure(matrix4f, v, w, x, studioModelRendererState3, 1);
         floatValue23 += this.measure(matrix4f, y, z, aa, studioModelRendererState3, 2);
         floatValue23 += this.measure(matrix4f, ab, ac, ad, studioModelRendererState3, 3);
         studioModelRendererState3.floatValue = floatValue23 * 0.25F * 1.0F;
         studioModelRendererState3.floatValue2 = f;
         StudioModel.StudioModelState6 studioModelState6 = this.studioModel.resolve(i);
         float floatValue24 = studioModelState6 == null ? this.studioModel.getIntValue() : studioModelState6.getIntValue();
         float floatValue25 = studioModelState6 == null ? this.studioModel.getIntValue2() : studioModelState6.getIntValue2();
         studioModelRendererState3.floatValue3 = k / floatValue24;
         studioModelRendererState3.floatValue4 = l / floatValue25;
         studioModelRendererState3.floatValue5 = o / floatValue24;
         studioModelRendererState3.floatValue6 = p / floatValue25;
         studioModelRendererState3.intValue = this.studioTextureCache.compute(this.text, i, this.studioModel);
         this.items.add(studioModelRendererState3);
      }
   }

   private float measure(Matrix4f matrix4f, float f, float g, float h, StudioModelRenderer.StudioModelRendererState studioModelRendererState4, int i) {
      this.vector3f.set(f, g, h);
      matrix4f.transformPosition(this.vector3f);
      this.vector3f.sub(this.floatValue5, this.floatValue6, this.floatValue7);
      this.matrix4f.transformPosition(this.vector3f);
      studioModelRendererState4.floats[i] = this.floatValue2 + this.vector3f.x * this.floatValue4;
      studioModelRendererState4.floats2[i] = this.floatValue3 - this.vector3f.y * this.floatValue4;
      return this.vector3f.z;
   }

   private boolean check2(RenderManager renderManager2, StudioModelRenderer.StudioModelRendererState studioModelRendererState5, float f) {
      float floatValue26 = studioModelRendererState5.floats[0];
      float floatValue27 = studioModelRendererState5.floats2[0];
      float floatValue28 = studioModelRendererState5.floats[1] - floatValue26;
      float floatValue29 = studioModelRendererState5.floats2[1] - floatValue27;
      float floatValue30 = studioModelRendererState5.floats[3] - floatValue26;
      float floatValue31 = studioModelRendererState5.floats2[3] - floatValue27;
      if (Math.abs(floatValue28 * floatValue31 - floatValue29 * floatValue30) < 0.05F) {
         return false;
      } else {
         float[] floatValues = new float[]{floatValue28, floatValue30, floatValue26, floatValue29, floatValue31, floatValue27, 0.0F, 0.0F, 1.0F};
         renderManager2.invoke53(floatValues);

         try {
            if (studioModelRendererState5.intValue > 0) {
               renderManager2.invoke11(
                  studioModelRendererState5.intValue,
                  0.0F,
                  0.0F,
                  1.0F,
                  1.0F,
                  studioModelRendererState5.floatValue3,
                  studioModelRendererState5.floatValue4,
                  studioModelRendererState5.floatValue5,
                  studioModelRendererState5.floatValue6
               );
               float floatValue32 = (1.0F - studioModelRendererState5.floatValue2) * 0.55F;
               if (floatValue32 > 0.01F) {
                  renderManager2.invoke5(0.0F, 0.0F, 1.0F, 1.0F, 0.0F, compute(0, 0, 0, Math.round(floatValue32 * 255.0F)));
               }
            } else {
               int intValue6 = Math.round(205.0F * studioModelRendererState5.floatValue2);
               renderManager2.invoke5(0.0F, 0.0F, 1.0F, 1.0F, 0.0F, compute(intValue6, intValue6, Math.min(255, intValue6 + 12), 255));
            }
         } finally {
            renderManager2.invoke57();
         }

         return true;
      }
   }

   private static int compute(int i, int j, int k, int l) {
      return l << 24 | i << 16 | j << 8 | k;
   }

   static final class StudioModelRendererState {
      final float[] floats = new float[4];
      final float[] floats2 = new float[4];
      float floatValue;
      float floatValue2;
      int intValue;
      float floatValue3;
      float floatValue4;
      float floatValue5;
      float floatValue6;
   }
}
