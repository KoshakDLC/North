package ru.metaculture.protection;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public final class ShaderNodeSurfaceRenderer {
   private static final ShaderNodeSurfaceRenderer INSTANCE = new ShaderNodeSurfaceRenderer();
   private static final String ASSETS_WILD_SHADERS_FOUNDRY_NODE_SURFACE_VERT = "assets/wild/shaders/foundry/node_surface.vert";
   private static final String ASSETS_WILD_SHADERS_FOUNDRY_NODE_SURFACE_FRAG = "assets/wild/shaders/foundry/node_surface.frag";
   private static final int INT_VALUE = 26;
   private static final int INT_VALUE_2 = 6;
   private static final int INT_VALUE_3 = 104;
   private GlShaderProgram glShaderProgram;
   private int intValue;
   private int intValue2;
   private int intValue3 = -1;
   private int intValue4 = -1;
   private FloatBuffer floatBuffer;
   private boolean flag;
   private boolean flag2;

   private ShaderNodeSurfaceRenderer() {
   }

   public static ShaderNodeSurfaceRenderer getINSTANCE() {
      return INSTANCE;
   }

   public boolean check(
      RenderManager renderManager,
      float f,
      float g,
      float h,
      float i,
      float j,
      float k,
      float l,
      float m,
      ColorScheme colorScheme,
      float n,
      float o,
      int p,
      int q,
      boolean bl
   ) {
      if (!this.flag2 && renderManager != null && !(h <= 1.0F) && !(i <= 1.0F) && p > 0 && q > 0) {
         float floatValue = measure10(renderManager.measure3(), 0.0F, 1.0F);
         if (!(floatValue <= 0.001F) && this.check2()) {
            float[] floatValues = renderManager.getMatrix3Stack().resolve2();
            float floatValue2 = measure(floatValues, f, g);
            float floatValue3 = measure2(floatValues, f, g);
            float floatValue4 = measure(floatValues, f + h, g);
            float floatValue5 = measure2(floatValues, f + h, g);
            float floatValue6 = measure(floatValues, f + h, g + i);
            float floatValue7 = measure2(floatValues, f + h, g + i);
            float floatValue8 = measure(floatValues, f, g + i);
            float floatValue9 = measure2(floatValues, f, g + i);
            float floatValue10 = measure4(floatValue2, floatValue4, floatValue6, floatValue8);
            float floatValue11 = measure4(floatValue3, floatValue5, floatValue7, floatValue9);
            float floatValue12 = Math.max(1.0F, measure5(floatValue2, floatValue4, floatValue6, floatValue8) - floatValue10);
            float floatValue13 = Math.max(1.0F, measure5(floatValue3, floatValue5, floatValue7, floatValue9) - floatValue11);
            float floatValue14 = measure3(floatValues);
            float floatValue15 = Math.max(1.0F, j * floatValue14);
            float floatValue16 = Math.max(9.0F, Math.min(34.0F, (15.0F + l * 11.0F + k * 5.0F) * floatValue14));
            float floatValue17 = floatValue16 * 2.18F + 4.0F;
            float floatValue18 = floatValue10 - floatValue17;
            float floatValue19 = floatValue11 - floatValue17;
            float floatValue20 = floatValue12 + floatValue17 * 2.0F;
            float floatValue21 = floatValue13 + floatValue17 * 2.0F;
            int intValue = colorScheme == null ? -36966 : colorScheme.getIntValue14();
            int intValue2 = colorScheme == null ? -8462337 : colorScheme.getIntValue15();
            int intValue3 = bl ? ColorScheme.compute5(238, 242, 250, 214) : ColorScheme.compute5(7, 9, 14, 218);
            this.floatBuffer.clear();
            this.invoke(floatValue18, floatValue19, floatValue18 - floatValue10, floatValue19 - floatValue11, floatValue12, floatValue13, floatValue15, floatValue16, intValue, intValue2, intValue3, k, l, m, floatValue, n - floatValue10, o - floatValue11);
            this.invoke(
               floatValue18 + floatValue20,
               floatValue19,
               floatValue18 + floatValue20 - floatValue10,
               floatValue19 - floatValue11,
               floatValue12,
               floatValue13,
               floatValue15,
               floatValue16,
               intValue,
               intValue2,
               intValue3,
               k,
               l,
               m,
               floatValue,
               n - floatValue10,
               o - floatValue11
            );
            this.invoke(
               floatValue18 + floatValue20,
               floatValue19 + floatValue21,
               floatValue18 + floatValue20 - floatValue10,
               floatValue19 + floatValue21 - floatValue11,
               floatValue12,
               floatValue13,
               floatValue15,
               floatValue16,
               intValue,
               intValue2,
               intValue3,
               k,
               l,
               m,
               floatValue,
               n - floatValue10,
               o - floatValue11
            );
            this.invoke(floatValue18, floatValue19, floatValue18 - floatValue10, floatValue19 - floatValue11, floatValue12, floatValue13, floatValue15, floatValue16, intValue, intValue2, intValue3, k, l, m, floatValue, n - floatValue10, o - floatValue11);
            this.invoke(
               floatValue18 + floatValue20,
               floatValue19 + floatValue21,
               floatValue18 + floatValue20 - floatValue10,
               floatValue19 + floatValue21 - floatValue11,
               floatValue12,
               floatValue13,
               floatValue15,
               floatValue16,
               intValue,
               intValue2,
               intValue3,
               k,
               l,
               m,
               floatValue,
               n - floatValue10,
               o - floatValue11
            );
            this.invoke(
               floatValue18,
               floatValue19 + floatValue21,
               floatValue18 - floatValue10,
               floatValue19 + floatValue21 - floatValue11,
               floatValue12,
               floatValue13,
               floatValue15,
               floatValue16,
               intValue,
               intValue2,
               intValue3,
               k,
               l,
               m,
               floatValue,
               n - floatValue10,
               o - floatValue11
            );
            this.floatBuffer.flip();
            renderManager.invoke20();
            FramebufferUtils.GlStateSnapshot glStateSnapshot = FramebufferUtils.captureGlState();
            boolean flag = false ;

            boolean flag2;
            label90: {
               boolean flag3;
               try {
                  flag = true;
                  GL11.glViewport(0, 0, Math.max(0, p), Math.max(0, q));
                  GL11.glDisable(3089);
                  GL11.glDisable(2929);
                  GL11.glDisable(2884);
                  GL11.glDepthMask(false);
                  GL11.glEnable(3042);
                  GL14.glBlendFuncSeparate(770, 771, 1, 771);
                  GL11.glDisable(36281);
                  this.glShaderProgram.invoke();
                  if (this.intValue3 >= 0) {
                     GL20.glUniform2f(this.intValue3, p, q);
                  }

                  if (this.intValue4 >= 0) {
                     GL20.glUniform1f(this.intValue4, ThemeShaderProgramCache.getINSTANCE().measure());
                  }

                  GL30.glBindVertexArray(this.intValue);
                  GL15.glBindBuffer(34962, this.intValue2);
                  GL15.glBufferSubData(34962, 0L, this.floatBuffer);
                  GL11.glDrawArrays(4, 0, 6);
                  flag2 = true;
                  flag = false;
                  break label90;
               } catch (Throwable exception) {
                  this.flag2 = true;
                  flag3 = false;
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
               return flag3;
            }

            GL20.glUseProgram(0);
            GL30.glBindVertexArray(0);
            GL15.glBindBuffer(34962, 0);
            FramebufferUtils.restoreGlState(glStateSnapshot);
            return flag2;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private boolean check2() {
      if (!this.flag) {
         this.flag = true;

         try {
            this.glShaderProgram = GlShaderProgram.resolve("assets/wild/shaders/foundry/node_surface.vert", "assets/wild/shaders/foundry/node_surface.frag");
            this.intValue3 = this.glShaderProgram.compute2("uViewport");
            this.intValue4 = this.glShaderProgram.compute2("uTime");
            this.intValue = GL30.glGenVertexArrays();
            this.intValue2 = GL15.glGenBuffers();
            GL30.glBindVertexArray(this.intValue);
            GL15.glBindBuffer(34962, this.intValue2);
            GL15.glBufferData(34962, 624L, 35048);
            int intValue4 = 0;
            GL20.glEnableVertexAttribArray(0);
            GL20.glVertexAttribPointer(0, 2, 5126, false, 104, intValue4);
            intValue4 += 8;
            GL20.glEnableVertexAttribArray(1);
            GL20.glVertexAttribPointer(1, 2, 5126, false, 104, intValue4);
            intValue4 += 8;
            GL20.glEnableVertexAttribArray(2);
            GL20.glVertexAttribPointer(2, 4, 5126, false, 104, intValue4);
            intValue4 += 16;
            GL20.glEnableVertexAttribArray(3);
            GL20.glVertexAttribPointer(3, 4, 5126, false, 104, intValue4);
            intValue4 += 16;
            GL20.glEnableVertexAttribArray(4);
            GL20.glVertexAttribPointer(4, 4, 5126, false, 104, intValue4);
            intValue4 += 16;
            GL20.glEnableVertexAttribArray(5);
            GL20.glVertexAttribPointer(5, 4, 5126, false, 104, intValue4);
            intValue4 += 16;
            GL20.glEnableVertexAttribArray(6);
            GL20.glVertexAttribPointer(6, 4, 5126, false, 104, intValue4);
            intValue4 += 16;
            GL20.glEnableVertexAttribArray(7);
            GL20.glVertexAttribPointer(7, 2, 5126, false, 104, intValue4);
            GL15.glBindBuffer(34962, 0);
            GL30.glBindVertexArray(0);
            this.floatBuffer = BufferUtils.createFloatBuffer(156);
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

   private void invoke(
      float f, float g, float h, float i, float j, float k, float l, float m, int n, int o, int p, float q, float r, float s, float t, float u, float v
   ) {
      this.floatBuffer.put(f).put(g);
      this.floatBuffer.put(h).put(i);
      this.floatBuffer.put(j).put(k).put(l).put(m);
      this.invoke2(n);
      this.invoke2(o);
      this.invoke2(p);
      this.floatBuffer.put(measure10(q, 0.0F, 1.0F)).put(measure10(r, 0.0F, 1.0F)).put(measure10(s, 0.0F, 1.0F)).put(t);
      this.floatBuffer.put(u).put(v);
   }

   private void invoke2(int i) {
      this.floatBuffer.put(measure6(i)).put(measure7(i)).put(measure8(i)).put(measure9(i));
   }

   private static float measure(float[] fs, float f, float g) {
      return fs != null && fs.length >= 9 ? fs[0] * f + fs[1] * g + fs[2] : f;
   }

   private static float measure2(float[] fs, float f, float g) {
      return fs != null && fs.length >= 9 ? fs[3] * f + fs[4] * g + fs[5] : g;
   }

   private static float measure3(float[] fs) {
      if (fs != null && fs.length >= 9) {
         float floatValue22 = (float)Math.sqrt(fs[0] * fs[0] + fs[3] * fs[3]);
         float floatValue23 = (float)Math.sqrt(fs[1] * fs[1] + fs[4] * fs[4]);
         return Math.max(0.001F, (floatValue22 + floatValue23) * 0.5F);
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

   private static float measure6(int i) {
      return (i >>> 16 & 0xFF) / 255.0F;
   }

   private static float measure7(int i) {
      return (i >>> 8 & 0xFF) / 255.0F;
   }

   private static float measure8(int i) {
      return (i & 0xFF) / 255.0F;
   }

   private static float measure9(int i) {
      return (i >>> 24 & 0xFF) / 255.0F;
   }

   private static float measure10(float f, float g, float h) {
      return f < g ? g : Math.min(f, h);
   }
}
