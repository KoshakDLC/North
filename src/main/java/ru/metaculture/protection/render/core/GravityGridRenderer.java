package ru.metaculture.protection;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

final class GravityGridRenderer {
   private static final GravityGridRenderer INSTANCE = new GravityGridRenderer();
   private static final int INT_VALUE = 32;
   private static final String ASSETS_WILD_SHADERS_BLUR_BLUR_FULLSCREEN_VERT = "assets/wild/shaders/blur/blur_fullscreen.vert";
   private static final String ASSETS_WILD_SHADERS_HUD_GRAVITY_GRID_FRAG = "assets/wild/shaders/hud/gravity_grid.frag";
   private final float[] floats = new float[128];
   private final float[] floats2 = new float[32];
   private GlShaderProgram glShaderProgram;
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
   private boolean flag;
   private boolean flag2;

   private GravityGridRenderer() {
   }

   static GravityGridRenderer getINSTANCE() {
      return INSTANCE;
   }

   void invoke(int i, int j, HudEditorRenderer.HudEditorRendererState3[] w222s, int k, String string, float f, float g, float h, int l, int m) {
      if (!this.flag2 && i > 0 && j > 0 && !(h <= 0.01F)) {
         if (this.check()) {
            int intValue = Math.max(0, Math.min(32, Math.min(k, w222s == null ? 0 : w222s.length)));

            for (int intValue2 = 0; intValue2 < 32; intValue2++) {
               int intValue3 = intValue2 * 4;
               if (intValue2 < intValue && w222s[intValue2] != null) {
                  HudEditorRenderer.HudEditorRendererState3 hudEditorRendererState3 = w222s[intValue2];
                  boolean flag = string != null && string.equals(hudEditorRendererState3.text);
                  this.floats[intValue3] = hudEditorRendererState3.floatValue;
                  this.floats[intValue3 + 1] = hudEditorRendererState3.floatValue2;
                  this.floats[intValue3 + 2] = Math.max(1.0F, hudEditorRendererState3.floatValue3);
                  this.floats[intValue3 + 3] = Math.max(hudEditorRendererState3.floatValue5, hudEditorRendererState3.floatValue6);
                  this.floats2[intValue2] = Math.max(0.0F, hudEditorRendererState3.floatValue4) * (flag ? 2.25F : 1.0F);
               } else {
                  this.floats[intValue3] = 0.0F;
                  this.floats[intValue3 + 1] = 0.0F;
                  this.floats[intValue3 + 2] = 1.0F;
                  this.floats[intValue3 + 3] = 1.0F;
                  this.floats2[intValue2] = 0.0F;
               }
            }

            FramebufferUtils.GlStateSnapshot glStateSnapshot = FramebufferUtils.captureGlState();

            try {
               GL11.glViewport(0, 0, Math.max(0, i), Math.max(0, j));
               GL11.glDisable(3089);
               GL11.glDisable(2929);
               GL11.glDisable(2884);
               GL11.glEnable(3042);
               GL14.glBlendFuncSeparate(770, 771, 1, 771);
               GL11.glDisable(36281);
               this.glShaderProgram.invoke();
               if (this.intValue3 >= 0) {
                  GL20.glUniform2f(this.intValue3, i, j);
               }

               if (this.intValue4 >= 0) {
                  GL20.glUniform1f(this.intValue4, (float)(System.nanoTime() % 240000000000L) / 1.0E9F);
               }

               if (this.intValue5 >= 0) {
                  GL20.glUniform1f(this.intValue5, Math.max(0.0F, Math.min(1.0F, h)));
               }

               if (this.intValue6 >= 0) {
                  GL20.glUniform2f(this.intValue6, f, g);
               }

               if (this.intValue7 >= 0) {
                  GL20.glUniform1i(this.intValue7, intValue);
               }

               if (this.intValue8 >= 0) {
                  GL20.glUniform4fv(this.intValue8, this.floats);
               }

               if (this.intValue9 >= 0) {
                  GL20.glUniform1fv(this.intValue9, this.floats2);
               }

               if (this.intValue10 >= 0) {
                  GL20.glUniform3f(this.intValue10, measure(l), measure2(l), measure3(l));
               }

               if (this.intValue11 >= 0) {
                  GL20.glUniform3f(this.intValue11, measure(m), measure2(m), measure3(m));
               }

               GL30.glBindVertexArray(this.intValue);
               GL11.glDrawArrays(4, 0, 6);
               GL30.glBindVertexArray(0);
            } catch (Throwable exception) {
               this.flag2 = true;
            } finally {
               GL20.glUseProgram(0);
               FramebufferUtils.restoreGlState(glStateSnapshot);
            }
         }
      }
   }

   private boolean check() {
      if (!this.flag) {
         this.flag = true;

         try {
            this.glShaderProgram = GlShaderProgram.resolve("assets/wild/shaders/blur/blur_fullscreen.vert", "assets/wild/shaders/hud/gravity_grid.frag");
            this.intValue3 = this.glShaderProgram.compute2("uResolution");
            this.intValue4 = this.glShaderProgram.compute2("uTime");
            this.intValue5 = this.glShaderProgram.compute2("uAlpha");
            this.intValue6 = this.glShaderProgram.compute2("uCursor");
            this.intValue7 = this.glShaderProgram.compute2("uWellCount");
            this.intValue8 = this.glShaderProgram.compute2("uWells[0]");
            this.intValue9 = this.glShaderProgram.compute2("uMass[0]");
            this.intValue10 = this.glShaderProgram.compute2("uAccentTop");
            this.intValue11 = this.glShaderProgram.compute2("uAccentBottom");
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
            return false;
         }
      } else {
         return this.glShaderProgram != null && this.intValue != 0;
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
}
