package ru.metaculture.protection;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public final class ShaderPinRenderer {
   private static final ShaderPinRenderer INSTANCE = new ShaderPinRenderer();
   private static final String ASSETS_WILD_SHADERS_FOUNDRY_PIN_VERT = "assets/wild/shaders/foundry/pin.vert";
   private static final String ASSETS_WILD_SHADERS_FOUNDRY_PIN_FRAG = "assets/wild/shaders/foundry/pin.frag";
   private static final int INT_VALUE = 18;
   private static final int INT_VALUE_2 = 6;
   private static final int INT_VALUE_3 = 96;
   private static final int INT_VALUE_4 = 576;
   private static final int INT_VALUE_5 = 72;
   private GlShaderProgram glShaderProgram;
   private int intValue;
   private int intValue2;
   private FloatBuffer floatBuffer;
   private boolean flag;
   private boolean flag2;
   private boolean flag3;
   private int intValue3;
   private int intValue4;
   private int intValue5;
   private int intValue6 = -1;
   private int intValue7 = -1;

   private ShaderPinRenderer() {
   }

   public static ShaderPinRenderer getINSTANCE() {
      return INSTANCE;
   }

   public boolean check(RenderManager renderManager, int i, int j) {
      if (this.flag2 || renderManager == null || i <= 0 || j <= 0) {
         return false;
      } else if (!this.check2()) {
         return false;
      } else {
         renderManager.invoke20();
         this.intValue3 = i;
         this.intValue4 = j;
         this.intValue5 = 0;
         this.flag3 = true;
         this.floatBuffer.clear();
         return true;
      }
   }

   public void invoke(RenderManager renderManager2, float f, float g, float h, float i, int j, int k, float l, float m) {
      if (this.flag3 && renderManager2 != null && this.intValue5 + 6 <= 576 && !(h <= 0.001F) && !(i <= 0.001F)) {
         float floatValue = Math.max(0.0F, Math.min(1.0F, renderManager2.measure3()));
         if (!(floatValue <= 0.001F)) {
            float[] floatValues = renderManager2.getMatrix3Stack().resolve2();
            float floatValue2 = measure(floatValues, f, g);
            float floatValue3 = measure2(floatValues, f, g);
            float floatValue4 = measure3(floatValues);
            float floatValue5 = Math.max(1.0F, h * floatValue4);
            float floatValue6 = Math.max(0.35F, Math.min(floatValue5, i * floatValue4));
            float floatValue7 = floatValue5 + 10.0F + l * 9.0F;
            float floatValue8 = (j >>> 16 & 0xFF) / 255.0F;
            float floatValue9 = (j >>> 8 & 0xFF) / 255.0F;
            float floatValue10 = (j & 0xFF) / 255.0F;
            float floatValue11 = (j >>> 24 & 0xFF) / 255.0F * floatValue;
            float floatValue12 = (k >>> 16 & 0xFF) / 255.0F;
            float floatValue13 = (k >>> 8 & 0xFF) / 255.0F;
            float floatValue14 = (k & 0xFF) / 255.0F;
            float floatValue15 = (k >>> 24 & 0xFF) / 255.0F * floatValue;
            this.invoke3(floatValue2 - floatValue7, floatValue3 - floatValue7, floatValue2, floatValue3, floatValue5, floatValue6, floatValue8, floatValue9, floatValue10, floatValue11, floatValue12, floatValue13, floatValue14, floatValue15, l, m, -1.0F, -1.0F);
            this.invoke3(floatValue2 + floatValue7, floatValue3 - floatValue7, floatValue2, floatValue3, floatValue5, floatValue6, floatValue8, floatValue9, floatValue10, floatValue11, floatValue12, floatValue13, floatValue14, floatValue15, l, m, 1.0F, -1.0F);
            this.invoke3(floatValue2 + floatValue7, floatValue3 + floatValue7, floatValue2, floatValue3, floatValue5, floatValue6, floatValue8, floatValue9, floatValue10, floatValue11, floatValue12, floatValue13, floatValue14, floatValue15, l, m, 1.0F, 1.0F);
            this.invoke3(floatValue2 - floatValue7, floatValue3 - floatValue7, floatValue2, floatValue3, floatValue5, floatValue6, floatValue8, floatValue9, floatValue10, floatValue11, floatValue12, floatValue13, floatValue14, floatValue15, l, m, -1.0F, -1.0F);
            this.invoke3(floatValue2 + floatValue7, floatValue3 + floatValue7, floatValue2, floatValue3, floatValue5, floatValue6, floatValue8, floatValue9, floatValue10, floatValue11, floatValue12, floatValue13, floatValue14, floatValue15, l, m, 1.0F, 1.0F);
            this.invoke3(floatValue2 - floatValue7, floatValue3 + floatValue7, floatValue2, floatValue3, floatValue5, floatValue6, floatValue8, floatValue9, floatValue10, floatValue11, floatValue12, floatValue13, floatValue14, floatValue15, l, m, -1.0F, 1.0F);
         }
      }
   }

   public void invoke2() {
      if (this.flag3) {
         this.flag3 = false;
         if (this.intValue5 > 0) {
            this.floatBuffer.flip();
            FramebufferUtils.GlStateSnapshot glStateSnapshot = FramebufferUtils.captureGlState();
            boolean flag = false ;

            label75: {
               try {
                  flag = true;
                  GL11.glViewport(0, 0, this.intValue3, this.intValue4);
                  GL11.glDisable(3089);
                  GL11.glDisable(2929);
                  GL11.glDisable(2884);
                  GL11.glEnable(3042);
                  GL14.glBlendFuncSeparate(770, 771, 1, 771);
                  GL11.glDisable(36281);
                  this.glShaderProgram.invoke();
                  if (this.intValue6 >= 0) {
                     GL20.glUniform2f(this.intValue6, this.intValue3, this.intValue4);
                  }

                  if (this.intValue7 >= 0) {
                     GL20.glUniform1f(this.intValue7, ThemeShaderProgramCache.getINSTANCE().measure());
                  }

                  GL30.glBindVertexArray(this.intValue);
                  GL15.glBindBuffer(34962, this.intValue2);
                  GL15.glBufferSubData(34962, 0L, this.floatBuffer);
                  GL11.glDrawArrays(4, 0, this.intValue5);
                  flag = false;
                  break label75;
               } catch (Throwable exception) {
                  this.flag2 = true;
                  flag = false;
               } finally {
                  if (flag) {
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

   private boolean check2() {
      if (!this.flag) {
         this.flag = true;

         try {
            this.glShaderProgram = GlShaderProgram.resolve("assets/wild/shaders/foundry/pin.vert", "assets/wild/shaders/foundry/pin.frag");
            this.intValue6 = this.glShaderProgram.compute2("uViewport");
            this.intValue7 = this.glShaderProgram.compute2("uTime");
            this.intValue = GL30.glGenVertexArrays();
            this.intValue2 = GL15.glGenBuffers();
            GL30.glBindVertexArray(this.intValue);
            GL15.glBindBuffer(34962, this.intValue2);
            GL15.glBufferData(34962, 41472L, 35048);
            int intValue = 0;
            GL20.glEnableVertexAttribArray(0);
            GL20.glVertexAttribPointer(0, 2, 5126, false, 72, intValue);
            intValue += 8;
            GL20.glEnableVertexAttribArray(1);
            GL20.glVertexAttribPointer(1, 2, 5126, false, 72, intValue);
            intValue += 8;
            GL20.glEnableVertexAttribArray(2);
            GL20.glVertexAttribPointer(2, 2, 5126, false, 72, intValue);
            intValue += 8;
            GL20.glEnableVertexAttribArray(3);
            GL20.glVertexAttribPointer(3, 4, 5126, false, 72, intValue);
            intValue += 16;
            GL20.glEnableVertexAttribArray(4);
            GL20.glVertexAttribPointer(4, 4, 5126, false, 72, intValue);
            intValue += 16;
            GL20.glEnableVertexAttribArray(5);
            GL20.glVertexAttribPointer(5, 4, 5126, false, 72, intValue);
            GL15.glBindBuffer(34962, 0);
            GL30.glBindVertexArray(0);
            this.floatBuffer = BufferUtils.createFloatBuffer(10368);
            return true;
         } catch (Throwable exception2) {
            this.flag2 = true;
            this.glShaderProgram = null;
            return false;
         }
      } else {
         return this.glShaderProgram != null && this.intValue != 0;
      }
   }

   private void invoke3(
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
      float w
   ) {
      this.floatBuffer.put(f).put(g);
      this.floatBuffer.put(h).put(i);
      this.floatBuffer.put(j).put(k);
      this.floatBuffer.put(l).put(m).put(n).put(o);
      this.floatBuffer.put(p).put(q).put(r).put(s);
      this.floatBuffer.put(Math.max(0.0F, Math.min(1.0F, t))).put(u).put(v).put(w);
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
         float floatValue16 = (float)Math.sqrt(fs[0] * fs[0] + fs[3] * fs[3]);
         float floatValue17 = (float)Math.sqrt(fs[1] * fs[1] + fs[4] * fs[4]);
         return Math.max(0.001F, (floatValue16 + floatValue17) * 0.5F);
      } else {
         return 1.0F;
      }
   }
}
