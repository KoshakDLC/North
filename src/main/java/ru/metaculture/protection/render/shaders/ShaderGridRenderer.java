package ru.metaculture.protection;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public final class ShaderGridRenderer {
   private static final ShaderGridRenderer INSTANCE = new ShaderGridRenderer();
   private static final String ASSETS_WILD_SHADERS_BLUR_BLUR_FULLSCREEN_VERT = "assets/wild/shaders/blur/blur_fullscreen.vert";
   private static final String ASSETS_WILD_SHADERS_FOUNDRY_GRID_FRAG = "assets/wild/shaders/foundry/grid.frag";
   private static final String ASSETS_WILD_SHADERS_FOUNDRY_GRID_COMPOSITE_FRAG = "assets/wild/shaders/foundry/grid_composite.frag";
   private static final float FLOAT_VALUE = 1.0F;
   private static final float FLOAT_VALUE_2 = 310.0F;
   private static final float FLOAT_VALUE_3 = 34.0F;
   private static final float FLOAT_VALUE_4 = 92.0F;
   private static final float FLOAT_VALUE_5 = 18.0F;
   private final OffscreenFramebuffer offscreenFramebuffer = new OffscreenFramebuffer();
   private GlShaderProgram glShaderProgram;
   private GlShaderProgram glShaderProgram2;
   private int intValue;
   private int intValue2;
   private int intValue3 = -1;
   private int intValue4 = -1;
   private int intValue5 = -1;
   private int intValue6 = -1;
   private int intValue7 = -1;
   private int intValue8 = -1;
   private int intValue9 = -1;
   private int intValue10 = -1;
   private int intValue11 = -1;
   private int intValue12 = -1;
   private int intValue13 = -1;
   private int intValue14 = -1;
   private int intValue15 = -1;
   private int intValue16 = -1;
   private int intValue17 = -1;
   private boolean flag;
   private boolean flag2;
   private boolean flag3;
   private long timestamp;
   private float floatValue;
   private float floatValue2;
   private float floatValue3;
   private float floatValue4;
   private float floatValue5;
   private float floatValue6;

   private ShaderGridRenderer() {
   }

   public static ShaderGridRenderer getINSTANCE() {
      return INSTANCE;
   }

   public boolean check(
      RenderManager renderManager, int i, int j, float f, float g, float h, float k, float l, float m, float n, ColorScheme colorScheme, boolean bl
   ) {
      if (this.flag2 || renderManager == null || i <= 0 || j <= 0 || m <= 0.001F) {
         return false;
      } else if (!this.check2()) {
         return false;
      } else {
         this.invoke(i, j, k, l);
         renderManager.invoke20();
         int intValue = colorScheme == null ? -29969 : colorScheme.getIntValue14();
         int intValue2 = colorScheme == null ? -8128257 : colorScheme.getIntValue15();
         FramebufferUtils.GlStateSnapshot glStateSnapshot = FramebufferUtils.captureGlState();

         boolean flag;
         try {
            this.offscreenFramebuffer.invoke(i, j);
            if (this.offscreenFramebuffer.check()) {
               this.offscreenFramebuffer.invoke2();
               GL11.glDrawBuffer(36064);
               GL11.glViewport(0, 0, Math.max(0, i), Math.max(0, j));
               GL11.glDisable(3089);
               GL11.glDisable(2929);
               GL11.glDisable(2884);
               GL11.glDisable(3042);
               GL11.glDepthMask(false);
               GL11.glColorMask(true, true, true, true);
               GL11.glDisable(36281);
               GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
               GL11.glClear(16384);
               this.glShaderProgram.invoke();
               invoke4(this.intValue3, (float)i, (float)j);
               invoke4(this.intValue4, f, g);
               invoke3(this.intValue5, h);
               invoke4(this.intValue6, k, l);
               invoke4(this.intValue7, this.floatValue, this.floatValue2);
               invoke4(this.intValue8, this.floatValue3, this.floatValue4);
               invoke3(this.intValue9, this.floatValue5);
               invoke3(this.intValue10, n);
               invoke3(this.intValue11, m);
               invoke5(this.intValue12, measure(intValue), measure2(intValue), measure3(intValue));
               invoke5(this.intValue13, measure(intValue2), measure2(intValue2), measure3(intValue2));
               invoke3(this.intValue14, bl ? 1.0F : 0.0F);
               GL30.glBindVertexArray(this.intValue);
               GL11.glDrawArrays(4, 0, 6);
               GL30.glBindFramebuffer(36160, glStateSnapshot.intValue);
               GL11.glDrawBuffer(glStateSnapshot.intValue3);
               GL11.glViewport(0, 0, Math.max(0, i), Math.max(0, j));
               GL11.glEnable(3042);
               GL14.glBlendFuncSeparate(770, 771, 1, 771);
               this.glShaderProgram2.invoke();
               GL13.glActiveTexture(33984);
               GL11.glBindTexture(3553, this.offscreenFramebuffer.getIntValue2());
               invoke2(this.intValue15, 0);
               invoke4(this.intValue16, (float)i, (float)j);
               invoke3(this.intValue17, 1.0F);
               GL11.glDrawArrays(4, 0, 6);
               GL30.glBindVertexArray(0);
               return true;
            }

            flag = false;
         } catch (Throwable exception) {
            this.flag2 = true;
            return false;
         } finally {
            GL13.glActiveTexture(33984);
            GL11.glBindTexture(3553, 0);
            GL20.glUseProgram(0);
            GL30.glBindVertexArray(0);
            FramebufferUtils.restoreGlState(glStateSnapshot);
         }

         return flag;
      }
   }

   private void invoke(int i, int j, float f, float g) {
      long longValue = System.nanoTime();
      float floatValue = this.timestamp == 0L ? 0.016666668F : (float)(longValue - this.timestamp) / 1.0E9F;
      this.timestamp = longValue;
      if (!Float.isFinite(floatValue) || floatValue <= 0.0F) {
         floatValue = 0.016666668F;
      }

      floatValue = Math.max(0.001F, Math.min(0.05F, floatValue));
      if (!this.flag3) {
         this.floatValue = f;
         this.floatValue2 = g;
         this.floatValue3 = 0.0F;
         this.floatValue4 = 0.0F;
         this.floatValue5 = 0.0F;
         this.floatValue6 = 0.0F;
         this.flag3 = true;
      } else {
         float floatValue2 = ((f - this.floatValue) * 310.0F - this.floatValue3 * 34.0F) / 1.0F;
         float floatValue3 = ((g - this.floatValue2) * 310.0F - this.floatValue4 * 34.0F) / 1.0F;
         this.floatValue3 += floatValue2 * floatValue;
         this.floatValue4 += floatValue3 * floatValue;
         this.floatValue = this.floatValue + this.floatValue3 * floatValue;
         this.floatValue2 = this.floatValue2 + this.floatValue4 * floatValue;
         float floatValue4 = f >= 0.0F && f <= i && g >= 0.0F && g <= j ? 1.0F : 0.0F;
         float floatValue5 = (float)Math.sqrt(this.floatValue3 * this.floatValue3 + this.floatValue4 * this.floatValue4);
         float floatValue6 = floatValue4 * measure4(0.58F + floatValue5 * 0.0018F, 0.0F, 1.0F);
         float floatValue7 = ((floatValue6 - this.floatValue5) * 92.0F - this.floatValue6 * 18.0F) / 1.0F;
         this.floatValue6 += floatValue7 * floatValue;
         this.floatValue5 = this.floatValue5 + this.floatValue6 * floatValue;
         this.floatValue5 = measure4(this.floatValue5, 0.0F, 1.0F);
      }
   }

   private boolean check2() {
      if (!this.flag) {
         this.flag = true;

         try {
            this.glShaderProgram = GlShaderProgram.resolve("assets/wild/shaders/blur/blur_fullscreen.vert", "assets/wild/shaders/foundry/grid.frag");
            this.glShaderProgram2 = GlShaderProgram.resolve("assets/wild/shaders/blur/blur_fullscreen.vert", "assets/wild/shaders/foundry/grid_composite.frag");
            this.intValue3 = this.glShaderProgram.compute2("uResolution");
            this.intValue4 = this.glShaderProgram.compute2("uPan");
            this.intValue5 = this.glShaderProgram.compute2("uZoom");
            this.intValue6 = this.glShaderProgram.compute2("uMouse");
            this.intValue7 = this.glShaderProgram.compute2("uSpringMouse");
            this.intValue8 = this.glShaderProgram.compute2("uMouseVelocity");
            this.intValue9 = this.glShaderProgram.compute2("uMagnetEnergy");
            this.intValue10 = this.glShaderProgram.compute2("uTime");
            this.intValue11 = this.glShaderProgram.compute2("uAlpha");
            this.intValue12 = this.glShaderProgram.compute2("uAccentTop");
            this.intValue13 = this.glShaderProgram.compute2("uAccentBottom");
            this.intValue14 = this.glShaderProgram.compute2("uLightMode");
            this.intValue15 = this.glShaderProgram2.compute2("uTexture");
            this.intValue16 = this.glShaderProgram2.compute2("uResolution");
            this.intValue17 = this.glShaderProgram2.compute2("uAlpha");
            this.intValue = GL30.glGenVertexArrays();
            this.intValue2 = GL15.glGenBuffers();
            GL30.glBindVertexArray(this.intValue);
            GL15.glBindBuffer(34962, this.intValue2);
            float[] floatValues = new float[]{
               -1.0F,
               -1.0F,
               0.0F,
               0.0F,
               1.0F,
               -1.0F,
               1.0F,
               0.0F,
               1.0F,
               1.0F,
               1.0F,
               1.0F,
               -1.0F,
               -1.0F,
               0.0F,
               0.0F,
               1.0F,
               1.0F,
               1.0F,
               1.0F,
               -1.0F,
               1.0F,
               0.0F,
               1.0F
            };
            GL15.glBufferData(34962, floatValues, 35044);
            byte byteValue = 16;
            GL20.glEnableVertexAttribArray(0);
            GL20.glVertexAttribPointer(0, 2, 5126, false, byteValue, 0L);
            GL20.glEnableVertexAttribArray(1);
            GL20.glVertexAttribPointer(1, 2, 5126, false, byteValue, 8L);
            GL15.glBindBuffer(34962, 0);
            GL30.glBindVertexArray(0);
            return true;
         } catch (Throwable exception2) {
            this.flag2 = true;
            this.glShaderProgram = null;
            this.glShaderProgram2 = null;
            return false;
         }
      } else {
         return this.glShaderProgram != null && this.glShaderProgram2 != null && this.intValue != 0;
      }
   }

   private static void invoke2(int i, int j) {
      if (i >= 0) {
         GL20.glUniform1i(i, j);
      }
   }

   private static void invoke3(int i, float f) {
      if (i >= 0) {
         GL20.glUniform1f(i, f);
      }
   }

   private static void invoke4(int i, float f, float g) {
      if (i >= 0) {
         GL20.glUniform2f(i, f, g);
      }
   }

   private static void invoke5(int i, float f, float g, float h) {
      if (i >= 0) {
         GL20.glUniform3f(i, f, g, h);
      }
   }

   private static float measure(int i) {
      return (i >>> 16 & 0xFF) / 255.0F;
   }

   private static float measure2(int i) {
      return (i >>> 8 & 0xFF) / 255.0F;
   }

   private static float measure3(int i) {
      return (i & 0xFF) / 255.0F;
   }

   private static float measure4(float f, float g, float h) {
      return f < g ? g : Math.min(f, h);
   }
}
