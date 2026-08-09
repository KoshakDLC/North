package ru.metaculture.protection;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public final class ShaderWireRenderer {
   private static final ShaderWireRenderer INSTANCE = new ShaderWireRenderer();
   private static final String ASSETS_WILD_SHADERS_FOUNDRY_WIRE_VERT = "assets/wild/shaders/foundry/wire.vert";
   private static final String ASSETS_WILD_SHADERS_FOUNDRY_WIRE_FRAG = "assets/wild/shaders/foundry/wire.frag";
   private static final int INT_VALUE = 6;
   private static final int INT_VALUE_2 = 26;
   private static final int INT_VALUE_3 = 104;
   private static final int INT_VALUE_4 = 256;
   private static final int INT_VALUE_5 = 1536;
   private GlShaderProgram glShaderProgram;
   private int intValue;
   private int intValue2;
   private FloatBuffer floatBuffer;
   private boolean flag;
   private boolean flag2;
   private boolean flag3;
   private int intValue3;
   private int intValue4;
   private float floatValue;
   private int intValue5;
   private int intValue6;
   private int intValue7;
   private int intValue8;
   private float[] floats;

   private ShaderWireRenderer() {
   }

   public static ShaderWireRenderer getINSTANCE() {
      return INSTANCE;
   }

   public boolean check(RenderManager renderManager, int i, int j, float f) {
      if (!this.flag2 && renderManager != null && i > 0 && j > 0) {
         float floatValue = measure6(f, 0.0F, 1.0F) * measure6(renderManager.measure3(), 0.0F, 1.0F);
         if (!(floatValue <= 0.001F) && this.check3()) {
            renderManager.invoke20();
            this.intValue3 = i;
            this.intValue4 = j;
            this.floatValue = floatValue;
            this.intValue5 = 0;
            this.flag3 = true;
            this.floats = renderManager.getMatrix3Stack().resolve2();
            this.floatBuffer.clear();
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public void invoke(float f, float g, float h, float i, float j, int k, int l, float m, boolean bl, float n, float o) {
      this.invoke2(f, g, h, i, j, k, l, m, bl, n, o, 0.0F, 0.0F, 0.0F, 0.0F);
   }

   public void invoke2(float f, float g, float h, float i, float j, int k, int l, float m, boolean bl, float n, float o, float p, float q, float r, float s) {
      if (this.flag3 && this.intValue5 + 6 <= 1536) {
         float floatValue2 = (k >>> 24 & 0xFF) / 255.0F;
         float floatValue3 = (l >>> 24 & 0xFF) / 255.0F;
         float floatValue4 = (float)Math.hypot(h - f, i - g);
         if ((!(floatValue2 <= 0.001F) || !(floatValue3 <= 0.001F)) && !(floatValue4 < 0.5F)) {
            float floatValue5 = measure6((i - g) * 0.035F, -18.0F, 18.0F) + measure6((q + s) * 0.24F, -26.0F, 26.0F);
            float floatValue6 = f + j + p * 0.34F;
            float floatValue7 = g + floatValue5 + q * 0.18F;
            float floatValue8 = h - j + r * 0.34F;
            float floatValue9 = i + floatValue5 + s * 0.18F;
            float floatValue10 = measure(this.floats, f, g);
            float floatValue11 = measure2(this.floats, f, g);
            float floatValue12 = measure(this.floats, floatValue6, floatValue7);
            float floatValue13 = measure2(this.floats, floatValue6, floatValue7);
            float floatValue14 = measure(this.floats, floatValue8, floatValue9);
            float floatValue15 = measure2(this.floats, floatValue8, floatValue9);
            float floatValue16 = measure(this.floats, h, i);
            float floatValue17 = measure2(this.floats, h, i);
            float floatValue18 = measure3(this.floats);
            float floatValue19 = Math.min(1.86F, Math.max(0.96F, m * 0.88F * floatValue18));
            float floatValue20 = floatValue19 + Math.max(3.1F, 3.7F * floatValue18);
            float floatValue21 = floatValue19 + Math.max(9.0F, 10.6F * floatValue18);
            float floatValue22 = floatValue21 + 4.5F;
            float floatValue23 = measure4(floatValue10, floatValue12, floatValue14, floatValue16) - floatValue22;
            float floatValue24 = measure4(floatValue11, floatValue13, floatValue15, floatValue17) - floatValue22;
            float floatValue25 = measure5(floatValue10, floatValue12, floatValue14, floatValue16) + floatValue22;
            float floatValue26 = measure5(floatValue11, floatValue13, floatValue15, floatValue17) + floatValue22;
            float floatValue27 = (k >> 16 & 0xFF) / 255.0F;
            float floatValue28 = (k >> 8 & 0xFF) / 255.0F;
            float floatValue29 = (k & 0xFF) / 255.0F;
            float floatValue30 = (l >> 16 & 0xFF) / 255.0F;
            float floatValue31 = (l >> 8 & 0xFF) / 255.0F;
            float floatValue32 = (l & 0xFF) / 255.0F;
            boolean flag = o >= 0.0F && n > 0.001F;
            float floatValue33 = flag ? Math.min(1.0F, 0.7F + Math.max(0.0F, n) * 1.15F) : 0.0F;
            float floatValue34 = flag ? 1.0F : 0.0F;
            float floatValue35 = o < 0.0F ? 0.0F : o;
            this.invoke4(
               floatValue23,
               floatValue24,
               floatValue10,
               floatValue11,
               floatValue12,
               floatValue13,
               floatValue14,
               floatValue15,
               floatValue16,
               floatValue17,
               floatValue27,
               floatValue28,
               floatValue29,
               floatValue2,
               floatValue30,
               floatValue31,
               floatValue32,
               floatValue3,
               floatValue19,
               floatValue20,
               floatValue21,
               floatValue4 * floatValue18,
               n,
               floatValue33,
               floatValue34,
               floatValue35
            );
            this.invoke4(
               floatValue25,
               floatValue24,
               floatValue10,
               floatValue11,
               floatValue12,
               floatValue13,
               floatValue14,
               floatValue15,
               floatValue16,
               floatValue17,
               floatValue27,
               floatValue28,
               floatValue29,
               floatValue2,
               floatValue30,
               floatValue31,
               floatValue32,
               floatValue3,
               floatValue19,
               floatValue20,
               floatValue21,
               floatValue4 * floatValue18,
               n,
               floatValue33,
               floatValue34,
               floatValue35
            );
            this.invoke4(
               floatValue25,
               floatValue26,
               floatValue10,
               floatValue11,
               floatValue12,
               floatValue13,
               floatValue14,
               floatValue15,
               floatValue16,
               floatValue17,
               floatValue27,
               floatValue28,
               floatValue29,
               floatValue2,
               floatValue30,
               floatValue31,
               floatValue32,
               floatValue3,
               floatValue19,
               floatValue20,
               floatValue21,
               floatValue4 * floatValue18,
               n,
               floatValue33,
               floatValue34,
               floatValue35
            );
            this.invoke4(
               floatValue23,
               floatValue24,
               floatValue10,
               floatValue11,
               floatValue12,
               floatValue13,
               floatValue14,
               floatValue15,
               floatValue16,
               floatValue17,
               floatValue27,
               floatValue28,
               floatValue29,
               floatValue2,
               floatValue30,
               floatValue31,
               floatValue32,
               floatValue3,
               floatValue19,
               floatValue20,
               floatValue21,
               floatValue4 * floatValue18,
               n,
               floatValue33,
               floatValue34,
               floatValue35
            );
            this.invoke4(
               floatValue25,
               floatValue26,
               floatValue10,
               floatValue11,
               floatValue12,
               floatValue13,
               floatValue14,
               floatValue15,
               floatValue16,
               floatValue17,
               floatValue27,
               floatValue28,
               floatValue29,
               floatValue2,
               floatValue30,
               floatValue31,
               floatValue32,
               floatValue3,
               floatValue19,
               floatValue20,
               floatValue21,
               floatValue4 * floatValue18,
               n,
               floatValue33,
               floatValue34,
               floatValue35
            );
            this.invoke4(
               floatValue23,
               floatValue26,
               floatValue10,
               floatValue11,
               floatValue12,
               floatValue13,
               floatValue14,
               floatValue15,
               floatValue16,
               floatValue17,
               floatValue27,
               floatValue28,
               floatValue29,
               floatValue2,
               floatValue30,
               floatValue31,
               floatValue32,
               floatValue3,
               floatValue19,
               floatValue20,
               floatValue21,
               floatValue4 * floatValue18,
               n,
               floatValue33,
               floatValue34,
               floatValue35
            );
         }
      }
   }

   public void invoke3() {
      if (this.flag3) {
         this.flag3 = false;
         this.floats = null;
         if (this.intValue5 > 0) {
            this.floatBuffer.flip();
            FramebufferUtils.GlStateSnapshot glStateSnapshot = FramebufferUtils.captureGlState();
            boolean flag2 = false ;

            label54: {
               try {
                  flag2 = true;
                  GL11.glViewport(0, 0, this.intValue3, this.intValue4);
                  GL11.glDisable(2929);
                  GL11.glDisable(2884);
                  GL11.glDisable(3089);
                  GL11.glDepthMask(false);
                  GL11.glEnable(3042);
                  GL14.glBlendFuncSeparate(770, 771, 1, 771);
                  GL11.glDisable(36281);
                  this.glShaderProgram.invoke();
                  GL30.glBindVertexArray(this.intValue);
                  GL15.glBindBuffer(34962, this.intValue2);
                  GL15.glBufferSubData(34962, 0L, this.floatBuffer);
                  GL20.glUniform2f(this.intValue6, this.intValue3, this.intValue4);
                  GL20.glUniform1f(this.intValue7, this.floatValue);
                  GL20.glUniform1f(this.intValue8, ThemeShaderProgramCache.getINSTANCE().measure());
                  GL11.glDrawArrays(4, 0, this.intValue5);
                  flag2 = false;
                  break label54;
               } catch (Throwable exception) {
                  flag2 = false;
               } finally {
                  if (flag2) {
                     GL20.glUseProgram(0);
                     GL30.glBindVertexArray(0);
                     GL15.glBindBuffer(34962, 0);
                     FramebufferUtils.restoreGlState(glStateSnapshot);
                  }
               }

               GL20.glUseProgram(0);
               GL30.glBindVertexArray(0);
               GL15.glBindBuffer(34962, 0);
               FramebufferUtils.restoreGlState(glStateSnapshot);
               return;
            }

            GL20.glUseProgram(0);
            GL30.glBindVertexArray(0);
            GL15.glBindBuffer(34962, 0);
            FramebufferUtils.restoreGlState(glStateSnapshot);
         }
      }
   }

   public boolean check2(
      RenderManager renderManager2, float f, float g, float h, float i, float j, int k, int l, float m, boolean bl, float n, float o, int p, int q
   ) {
      if (!this.check(renderManager2, p, q, o)) {
         return false;
      } else {
         this.invoke(f, g, h, i, j, k, l, m, bl, n, -1.0F);
         this.invoke3();
         return true;
      }
   }

   private boolean check3() {
      if (this.flag) {
         return this.glShaderProgram != null;
      } else {
         this.flag = true;

         try {
            this.glShaderProgram = GlShaderProgram.resolve("assets/wild/shaders/foundry/wire.vert", "assets/wild/shaders/foundry/wire.frag");
            this.intValue6 = this.glShaderProgram.compute2("uViewport");
            this.intValue7 = this.glShaderProgram.compute2("uAlpha");
            this.intValue8 = this.glShaderProgram.compute2("u_Time");
            this.intValue = GL30.glGenVertexArrays();
            this.intValue2 = GL15.glGenBuffers();
            GL30.glBindVertexArray(this.intValue);
            GL15.glBindBuffer(34962, this.intValue2);
            GL15.glBufferData(34962, 159744L, 35048);
            int intValue = 0;
            GL20.glEnableVertexAttribArray(0);
            GL20.glVertexAttribPointer(0, 2, 5126, false, 104, intValue);
            intValue += 8;
            GL20.glEnableVertexAttribArray(1);
            GL20.glVertexAttribPointer(1, 2, 5126, false, 104, intValue);
            intValue += 8;
            GL20.glEnableVertexAttribArray(2);
            GL20.glVertexAttribPointer(2, 2, 5126, false, 104, intValue);
            intValue += 8;
            GL20.glEnableVertexAttribArray(3);
            GL20.glVertexAttribPointer(3, 2, 5126, false, 104, intValue);
            intValue += 8;
            GL20.glEnableVertexAttribArray(4);
            GL20.glVertexAttribPointer(4, 2, 5126, false, 104, intValue);
            intValue += 8;
            GL20.glEnableVertexAttribArray(5);
            GL20.glVertexAttribPointer(5, 4, 5126, false, 104, intValue);
            intValue += 16;
            GL20.glEnableVertexAttribArray(6);
            GL20.glVertexAttribPointer(6, 4, 5126, false, 104, intValue);
            intValue += 16;
            GL20.glEnableVertexAttribArray(7);
            GL20.glVertexAttribPointer(7, 4, 5126, false, 104, intValue);
            intValue += 16;
            GL20.glEnableVertexAttribArray(8);
            GL20.glVertexAttribPointer(8, 4, 5126, false, 104, intValue);
            GL15.glBindBuffer(34962, 0);
            GL30.glBindVertexArray(0);
            this.floatBuffer = BufferUtils.createFloatBuffer(39936);
            return true;
         } catch (Throwable exception2) {
            this.flag2 = true;
            this.glShaderProgram = null;
            return false;
         }
      }
   }

   private void invoke4(
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
      float ae
   ) {
      this.floatBuffer.put(f).put(g);
      this.floatBuffer.put(h).put(i);
      this.floatBuffer.put(j).put(k);
      this.floatBuffer.put(l).put(m);
      this.floatBuffer.put(n).put(o);
      this.floatBuffer.put(p).put(q).put(r).put(s);
      this.floatBuffer.put(t).put(u).put(v).put(w);
      this.floatBuffer.put(x).put(y).put(z).put(aa);
      this.floatBuffer.put(ab).put(ac).put(ad).put(ae);
      this.intValue5++;
   }

   private static float measure(float[] fs, float f, float g) {
      return fs != null && fs.length >= 9 ? fs[0] * f + fs[1] * g + fs[2] : f;
   }

   private static float measure2(float[] fs, float f, float g) {
      return fs != null && fs.length >= 9 ? fs[3] * f + fs[4] * g + fs[5] : g;
   }

   private static float measure3(float[] fs) {
      if (fs != null && fs.length >= 9) {
         float floatValue36 = (float)Math.sqrt(fs[0] * fs[0] + fs[3] * fs[3]);
         float floatValue37 = (float)Math.sqrt(fs[1] * fs[1] + fs[4] * fs[4]);
         return Math.max(0.001F, (floatValue36 + floatValue37) * 0.5F);
      } else {
         return 1.0F;
      }
   }

   private static float measure4(float f, float g, float h, float i) {
      return Math.min(Math.min(f, g), Math.min(h, i));
   }

   private static float measure5(float f, float g, float h, float i) {
      return Math.max(Math.max(f, g), Math.max(h, i));
   }

   private static float measure6(float f, float g, float h) {
      return f < g ? g : Math.min(f, h);
   }
}
