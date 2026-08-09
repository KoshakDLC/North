package ru.metaculture.protection;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

final class ArrayListBlurRenderer {
   private static final ArrayListBlurRenderer INSTANCE = new ArrayListBlurRenderer();
   private static final int INT_VALUE = 96;
   private static final String ASSETS_WILD_SHADERS_BLUR_BLUR_FULLSCREEN_VERT = "assets/wild/shaders/blur/blur_fullscreen.vert";
   private static final String ASSETS_WILD_SHADERS_HUD_ARRAYLIST_FERROFLUID_FRAG = "assets/wild/shaders/hud/arraylist_ferrofluid.frag";
   private final float[] floats = new float[384];
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
   private int intValue12 = -1;
   private int intValue13 = -1;
   private int intValue14 = -1;
   private int intValue15 = -1;
   private int intValue16 = -1;
   private int intValue17 = -1;
   private int intValue18 = -1;
   private int intValue19 = -1;
   private int intValue20 = -1;
   private int intValue21 = -1;
   private int intValue22 = -1;
   private int intValue23 = -1;
   private boolean flag;
   private boolean flag2;
   private final float[] floats2 = new float[384];

   private ArrayListBlurRenderer() {
   }

   static boolean check(
      RenderManager renderManager,
      int i,
      int j,
      float[] fs,
      float[] gs,
      int k,
      float f,
      float g,
      float h,
      int l,
      int m,
      int n,
      int o,
      boolean bl,
      boolean bl2,
      boolean bl3,
      float p,
      float q,
      float r,
      float s,
      float t,
      float u,
      float v
   ) {
      return INSTANCE.check2(renderManager, i, j, fs, gs, k, f, g, h, l, m, n, o, bl, bl2, bl3, p, q, r, s, t, u, v);
   }

   private boolean check2(
      RenderManager renderManager2,
      int i,
      int j,
      float[] fs,
      float[] gs,
      int k,
      float f,
      float g,
      float h,
      int l,
      int m,
      int n,
      int o,
      boolean bl,
      boolean bl2,
      boolean bl3,
      float p,
      float q,
      float r,
      float s,
      float t,
      float u,
      float v
   ) {
      if (!this.flag2 && renderManager2 != null && i > 0 && j > 0 && k > 0 && k <= 96 && fs != null && !(h <= 0.001F)) {
         if (!this.check3()) {
            return false;
         } else {
            float floatValue = Float.MAX_VALUE;
            float floatValue2 = Float.MAX_VALUE;
            float floatValue3 = -Float.MAX_VALUE;
            float floatValue4 = -Float.MAX_VALUE;

            for (int intValue = 0; intValue < 96; intValue++) {
               int intValue2 = intValue * 4;
               if (intValue < k) {
                  float floatValue5 = fs[intValue2];
                  float floatValue6 = fs[intValue2 + 1];
                  float floatValue7 = Math.max(0.0F, fs[intValue2 + 2]);
                  float floatValue8 = Math.max(0.0F, fs[intValue2 + 3]);
                  this.floats[intValue2] = floatValue5;
                  this.floats[intValue2 + 1] = floatValue6;
                  this.floats[intValue2 + 2] = floatValue7;
                  this.floats[intValue2 + 3] = floatValue8;
                  if (floatValue7 > 0.5F && floatValue8 > 0.5F) {
                     floatValue = Math.min(floatValue, floatValue5);
                     floatValue2 = Math.min(floatValue2, floatValue6);
                     floatValue3 = Math.max(floatValue3, floatValue5 + floatValue7);
                     floatValue4 = Math.max(floatValue4, floatValue6 + floatValue8);
                  }

                  if (gs != null && gs.length >= intValue2 + 4) {
                     this.floats2[intValue2] = measure5(gs[intValue2], -220.0F, 220.0F);
                     this.floats2[intValue2 + 1] = measure5(gs[intValue2 + 1], -220.0F, 220.0F);
                     this.floats2[intValue2 + 2] = measure5(gs[intValue2 + 2], 0.0F, 2.5F);
                     this.floats2[intValue2 + 3] = measure5(gs[intValue2 + 3], 0.0F, 1.0F);
                  } else {
                     this.floats2[intValue2] = 0.0F;
                     this.floats2[intValue2 + 1] = 0.0F;
                     this.floats2[intValue2 + 2] = 0.0F;
                     this.floats2[intValue2 + 3] = 1.0F;
                  }
               } else {
                  this.floats[intValue2] = 0.0F;
                  this.floats[intValue2 + 1] = 0.0F;
                  this.floats[intValue2 + 2] = 0.0F;
                  this.floats[intValue2 + 3] = 0.0F;
                  this.floats2[intValue2] = 0.0F;
                  this.floats2[intValue2 + 1] = 0.0F;
                  this.floats2[intValue2 + 2] = 0.0F;
                  this.floats2[intValue2 + 3] = 1.0F;
               }
            }

            if (floatValue != Float.MAX_VALUE && floatValue2 != Float.MAX_VALUE && !(floatValue3 <= floatValue) && !(floatValue4 <= floatValue2)) {
               renderManager2.invoke20();
               float floatValue9 = Math.max(24.0F, f * 4.2F);
               FramebufferUtils.GlStateSnapshot glStateSnapshot = FramebufferUtils.captureGlState();
               boolean flag = false ;

               boolean flag2;
               label290: {
                  boolean flag3;
                  try {
                     flag = true;
                     GL11.glViewport(0, 0, Math.max(0, i), Math.max(0, j));
                     GL11.glDisable(2929);
                     GL11.glDisable(2884);
                     GL11.glDisable(3089);
                     GL11.glDepthMask(false);
                     GL11.glEnable(3042);
                     GL14.glBlendFuncSeparate(770, 771, 1, 771);
                     GL11.glDisable(36281);
                     this.glShaderProgram.invoke();
                     if (this.intValue3 >= 0) {
                        GL20.glUniform2f(this.intValue3, i, j);
                     }

                     if (this.intValue4 >= 0) {
                        GL20.glUniform1f(this.intValue4, (float)(System.nanoTime() % 720000000000L) / 1.0E9F);
                     }

                     if (this.intValue5 >= 0) {
                        GL20.glUniform4f(this.intValue5, floatValue - floatValue9, floatValue2 - floatValue9, floatValue3 - floatValue + floatValue9 * 2.0F, floatValue4 - floatValue2 + floatValue9 * 2.0F);
                     }

                     if (this.intValue6 >= 0) {
                        GL20.glUniform1i(this.intValue6, k);
                     }

                     if (this.intValue7 >= 0) {
                        GL20.glUniform4fv(this.intValue7, this.floats);
                     }

                     if (this.intValue8 >= 0) {
                        GL20.glUniform1f(this.intValue8, Math.max(1.0F, f));
                     }

                     if (this.intValue9 >= 0) {
                        GL20.glUniform1f(this.intValue9, Math.max(0.0F, Math.min(1.0F, g)));
                     }

                     if (this.intValue10 >= 0) {
                        GL20.glUniform1f(this.intValue10, Math.max(0.0F, Math.min(1.0F, h)));
                     }

                     if (this.intValue11 >= 0) {
                        GL20.glUniform4fv(this.intValue11, this.floats2);
                     }

                     if (this.intValue12 >= 0) {
                        GL20.glUniform4f(this.intValue12, q, r, Math.max(0.0F, Math.min(1.0F, s)), Math.max(18.0F, f * 5.5F));
                     }

                     if (this.intValue13 >= 0) {
                        GL20.glUniform1f(this.intValue13, Math.max(0.0F, Math.min(2.5F, t)));
                     }

                     if (this.intValue14 >= 0) {
                        invoke2(this.intValue14, l);
                     }

                     if (this.intValue15 >= 0) {
                        invoke2(this.intValue15, m);
                     }

                     if (this.intValue16 >= 0) {
                        invoke(this.intValue16, n);
                     }

                     if (this.intValue17 >= 0) {
                        invoke(this.intValue17, o);
                     }

                     if (this.intValue18 >= 0) {
                        GL20.glUniform1f(this.intValue18, bl ? 1.0F : 0.0F);
                     }

                     if (this.intValue19 >= 0) {
                        GL20.glUniform1f(this.intValue19, bl2 ? 1.0F : 0.0F);
                     }

                     if (this.intValue20 >= 0) {
                        GL20.glUniform1f(this.intValue20, bl3 ? 1.0F : 0.0F);
                     }

                     if (this.intValue21 >= 0) {
                        GL20.glUniform1f(this.intValue21, Math.max(0.0F, Math.min(1.0F, p)));
                     }

                     if (this.intValue22 >= 0) {
                        GL20.glUniform1f(this.intValue22, Math.max(1.0F, u));
                     }

                     if (this.intValue23 >= 0) {
                        GL20.glUniform1f(this.intValue23, Math.max(0.0F, Math.min(1.0F, v)));
                     }

                     GL30.glBindVertexArray(this.intValue);
                     GL11.glDrawArrays(4, 0, 6);
                     GL30.glBindVertexArray(0);
                     flag2 = true;
                     flag = false;
                     break label290;
                  } catch (Throwable exception) {
                     this.flag2 = true;
                     flag3 = false;
                     flag = false;
                  } finally {
                     if (flag) {
                        GL20.glUseProgram(0);
                        FramebufferUtils.restoreGlState(glStateSnapshot);
                     }
                  }

                  GL20.glUseProgram(0);
                  FramebufferUtils.restoreGlState(glStateSnapshot);
                  return flag3;
               }

               GL20.glUseProgram(0);
               FramebufferUtils.restoreGlState(glStateSnapshot);
               return flag2;
            } else {
               return false;
            }
         }
      } else {
         return false;
      }
   }

   private boolean check3() {
      if (!this.flag) {
         this.flag = true;

         try {
            this.glShaderProgram = GlShaderProgram.resolve("assets/wild/shaders/blur/blur_fullscreen.vert", "assets/wild/shaders/hud/arraylist_ferrofluid.frag");
            this.intValue3 = this.glShaderProgram.compute2("uResolution");
            this.intValue4 = this.glShaderProgram.compute2("uTime");
            this.intValue5 = this.glShaderProgram.compute2("uDrawRect");
            this.intValue6 = this.glShaderProgram.compute2("uRowCount");
            this.intValue7 = this.glShaderProgram.compute2("uRows[0]");
            this.intValue8 = this.glShaderProgram.compute2("uRadius");
            this.intValue9 = this.glShaderProgram.compute2("uDirection");
            this.intValue10 = this.glShaderProgram.compute2("uAlpha");
            this.intValue11 = this.glShaderProgram.compute2("uMotionRows[0]");
            this.intValue12 = this.glShaderProgram.compute2("uPointer");
            this.intValue13 = this.glShaderProgram.compute2("uExposure");
            this.intValue14 = this.glShaderProgram.compute2("uSurfaceColor");
            this.intValue15 = this.glShaderProgram.compute2("uOutlineColor");
            this.intValue16 = this.glShaderProgram.compute2("uAccentTop");
            this.intValue17 = this.glShaderProgram.compute2("uAccentBottom");
            this.intValue18 = this.glShaderProgram.compute2("uOutline");
            this.intValue19 = this.glShaderProgram.compute2("uGlow");
            this.intValue20 = this.glShaderProgram.compute2("uEdgeHighlight");
            this.intValue21 = this.glShaderProgram.compute2("uLightMode");
            this.intValue22 = this.glShaderProgram.compute2("uFluidCohesion");
            this.intValue23 = this.glShaderProgram.compute2("uSoft");
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

   private static void invoke(int i, int j) {
      GL20.glUniform3f(i, measure(j), measure2(j), measure3(j));
   }

   private static void invoke2(int i, int j) {
      GL20.glUniform4f(i, measure(j), measure2(j), measure3(j), measure4(j));
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

   private static float measure4(int i) {
      return (i >>> 24 & 0xFF) / 255.0F;
   }

   private static float measure5(float f, float g, float h) {
      return Math.max(g, Math.min(h, f));
   }
}
