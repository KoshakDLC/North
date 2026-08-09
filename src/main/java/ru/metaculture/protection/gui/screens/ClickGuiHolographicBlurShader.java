package ru.metaculture.protection;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL20;

public final class ClickGuiHolographicBlurShader implements AutoCloseable {
   private static final String ASSETS_WILD_SHADERS_MAINMENU_MENU_QUAD_VERT = "assets/wild/shaders/mainmenu/menu_quad.vert";
   private static final String ASSETS_WILD_SHADERS_CLICKGUI_HOLO_BLUR_FRAG = "assets/wild/shaders/clickgui/holo_blur.frag";
   private static volatile ClickGuiHolographicBlurShader instance;
   private final FullscreenQuad fullscreenQuad = new FullscreenQuad();
   private ShaderProgram shaderProgram;
   private FullscreenQuad.FullscreenQuadResources fullscreenQuadResources;
   private int intValue;
   private int intValue2;
   private int intValue3;
   private long timestamp;
   private float floatValue;
   private long timestamp2;
   private boolean flag;
   private boolean flag2;
   private boolean flag3;
   private float floatValue2 = 0.5F;
   private float floatValue3 = 0.5F;
   private long timestamp3;
   private float floatValue4;
   private float floatValue5;
   private static final float FLOAT_VALUE = 0.85F;

   public static ClickGuiHolographicBlurShader resolve() {
      ClickGuiHolographicBlurShader clickGuiHolographicBlurShader = instance;
      if (clickGuiHolographicBlurShader != null) {
         return clickGuiHolographicBlurShader;
      } else {
         synchronized (ClickGuiHolographicBlurShader.class) {
            if (instance == null) {
               instance = new ClickGuiHolographicBlurShader();
            }

            return instance;
         }
      }
   }

   private ClickGuiHolographicBlurShader() {
   }

   public void invoke(int i, int j, float f, float g, float h, float k, float l, float m, float n, float o, float p, float q, float r, float s, float t) {
      if (!this.flag2) {
         if (i > 1 && j > 1) {
            if (!(h <= 0.001F)) {
               FramebufferUtils.GlStateSnapshot glStateSnapshot = FramebufferUtils.captureGlState();
               boolean flag = false ;

               label94: {
                  label112: {
                     label113: {
                        try {
                           flag = true;
                           this.invoke3();
                           if (this.flag2) {
                              flag = false;
                              break label94;
                           }

                           this.invoke2(i, j);
                           if (this.intValue == 0) {
                              flag = false;
                              break label112;
                           }

                           GL11.glDisable(2929);
                           GL11.glDisable(2884);
                           GL11.glDisable(3089);
                           GL11.glColorMask(true, true, true, true);
                           GL11.glEnable(3042);
                           GL14.glBlendFuncSeparate(770, 771, 1, 771);
                           GL11.glViewport(0, 0, Math.max(0, i), Math.max(0, j));
                           this.fullscreenQuadResources.invoke();
                           this.fullscreenQuadResources.invoke4("uViewport", i, j);
                           this.fullscreenQuadResources.invoke6("uRect", 0.0F, 0.0F, i, j);
                           this.fullscreenQuadResources.invoke2("uScene", 0);
                           this.fullscreenQuadResources.invoke4("uResolution", i, j);
                           this.fullscreenQuadResources.invoke3("uTime", this.measure3());
                           float floatValue = measure4(i <= 0 ? 0.0F : f / i);
                           float floatValue2 = measure4(j <= 0 ? 0.0F : g / j);
                           this.fullscreenQuadResources.invoke4("uMouse", floatValue, floatValue2);
                           this.fullscreenQuadResources.invoke3("uIntensity", measure4(h));
                           this.fullscreenQuadResources.invoke3("uBlurMax", Math.max(0.0F, k));
                           this.fullscreenQuadResources.invoke3("uTint", measure4(l));
                           this.fullscreenQuadResources.invoke3("uMouseInfluence", Math.max(0.0F, m));
                           this.fullscreenQuadResources.invoke3("uClarityRadius", Math.max(0.05F, n));
                           this.fullscreenQuadResources.invoke3("uNoiseScale", Math.max(0.5F, o));
                           this.fullscreenQuadResources.invoke3("uFlowSpeed", p);
                           this.fullscreenQuadResources.invoke3("uContrast", measure4(q));
                           this.fullscreenQuadResources.invoke3("uVignette", measure4(r));
                           this.fullscreenQuadResources.invoke3("uBrightness", measure4(s));
                           this.fullscreenQuadResources.invoke3("uSaturation", measure4(t));
                           float floatValue3 = measure4(h);
                           float floatValue4 = this.measure(floatValue3, floatValue, floatValue2);
                           this.fullscreenQuadResources.invoke3("uEntry", floatValue4);
                           this.fullscreenQuadResources.invoke4("uEntryCenter", this.floatValue2, this.floatValue3);
                           GL13.glActiveTexture(33984);
                           GL11.glBindTexture(3553, this.intValue);
                           this.shaderProgram.invoke();
                           flag = false;
                           break label113;
                        } catch (Throwable exception) {
                           this.flag2 = true;
                           RenderDiagnosticsTracker.getInstance().fail("HoloBlurBackground.render", exception);
                           flag = false;
                        } finally {
                           if (flag) {
                              GL13.glActiveTexture(33984);
                              GL11.glBindTexture(3553, 0);
                              GL20.glUseProgram(0);
                              FramebufferUtils.restoreGlState(glStateSnapshot);
                           }
                        }

                        GL13.glActiveTexture(33984);
                        GL11.glBindTexture(3553, 0);
                        GL20.glUseProgram(0);
                        FramebufferUtils.restoreGlState(glStateSnapshot);
                        return;
                     }

                     GL13.glActiveTexture(33984);
                     GL11.glBindTexture(3553, 0);
                     GL20.glUseProgram(0);
                     FramebufferUtils.restoreGlState(glStateSnapshot);
                     return;
                  }

                  GL13.glActiveTexture(33984);
                  GL11.glBindTexture(3553, 0);
                  GL20.glUseProgram(0);
                  FramebufferUtils.restoreGlState(glStateSnapshot);
                  return;
               }

               GL13.glActiveTexture(33984);
               GL11.glBindTexture(3553, 0);
               GL20.glUseProgram(0);
               FramebufferUtils.restoreGlState(glStateSnapshot);
            }
         }
      }
   }

   private void invoke2(int i, int j) {
      if (this.intValue == 0) {
         this.intValue = GL11.glGenTextures();
         if (this.intValue == 0) {
            return;
         }

         GL11.glBindTexture(3553, this.intValue);
         GL11.glTexParameteri(3553, 10241, 9729);
         GL11.glTexParameteri(3553, 10240, 9729);
         GL11.glTexParameteri(3553, 10242, 33071);
         GL11.glTexParameteri(3553, 10243, 33071);
         this.intValue2 = 0;
         this.intValue3 = 0;
      } else {
         GL11.glBindTexture(3553, this.intValue);
      }

      if (this.intValue2 == i && this.intValue3 == j) {
         GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, i, j);
      } else {
         GL11.glCopyTexImage2D(3553, 0, 32856, 0, 0, i, j, 0);
         this.intValue2 = i;
         this.intValue3 = j;
      }
   }

   private void invoke3() {
      if (!this.flag) {
         try {
            this.shaderProgram = new ShaderProgram();
            this.fullscreenQuadResources = this.fullscreenQuad
               .resolve("clickgui_holo", "assets/wild/shaders/mainmenu/menu_quad.vert", "assets/wild/shaders/clickgui/holo_blur.frag");
            this.timestamp = System.nanoTime();
            this.timestamp2 = this.timestamp;
            this.floatValue = 0.0F;
            this.flag = true;
         } catch (Throwable exception2) {
            this.flag2 = true;
            RenderDiagnosticsTracker.getInstance().fail("HoloBlurBackground.ensure", exception2);
         }
      }
   }

   private float measure(float f, float g, float h) {
      long longValue = System.nanoTime();
      boolean flag2 = f > this.floatValue5 + 1.0E-4F;
      boolean flag3 = f < this.floatValue5 - 1.0E-4F;
      this.floatValue5 = f;
      if (f < 0.012F) {
         this.flag3 = false;
         this.timestamp3 = 0L;
         this.floatValue4 = 0.0F;
         return 0.0F;
      } else {
         if (!this.flag3) {
            this.flag3 = true;
            this.timestamp3 = longValue;
            this.floatValue2 = measure4(g);
            this.floatValue3 = measure4(h);
            this.floatValue4 = 0.0F;
         }

         float floatValue5;
         if (this.timestamp3 == 0L) {
            this.timestamp3 = longValue;
            floatValue5 = 0.0F;
         } else {
            float floatValue6 = (float)(longValue - this.timestamp3) / 1.0E9F;
            floatValue5 = measure4(floatValue6 / 0.85F);
         }

         float floatValue7 = 1.0F - (1.0F - floatValue5) * (1.0F - floatValue5) * (1.0F - floatValue5);
         float floatValue8 = Math.min(floatValue7, measure2(f));
         if (flag3) {
            floatValue8 = Math.min(floatValue8, measure2(f));
         }

         if (flag2 && floatValue8 > this.floatValue4) {
            this.floatValue4 = floatValue8;
         } else {
            this.floatValue4 = floatValue8;
         }

         return measure4(this.floatValue4);
      }
   }

   private static float measure2(float f) {
      float floatValue9 = measure4(f / 0.6F);
      return floatValue9 * floatValue9 * (3.0F - 2.0F * floatValue9);
   }

   private float measure3() {
      long longValue2 = System.nanoTime();
      if (this.timestamp2 == 0L) {
         this.timestamp2 = longValue2;
         return this.floatValue;
      } else {
         float floatValue10 = (float)(longValue2 - this.timestamp2) / 1.0E9F;
         this.timestamp2 = longValue2;
         if (!Float.isFinite(floatValue10) || floatValue10 < 0.0F) {
            floatValue10 = 0.0F;
         }

         this.floatValue = this.floatValue + Math.min(floatValue10, 0.1F);
         if (this.floatValue > 720.0F) {
            this.floatValue -= 720.0F;
         }

         return this.floatValue;
      }
   }

   private static float measure4(float f) {
      if (Float.isNaN(f)) {
         return 0.0F;
      } else if (f < 0.0F) {
         return 0.0F;
      } else {
         return f > 1.0F ? 1.0F : f;
      }
   }

   @Override
   public void close() {
      try {
         if (this.intValue != 0) {
            GL11.glDeleteTextures(this.intValue);
            this.intValue = 0;
         }

         if (this.shaderProgram != null) {
            this.shaderProgram.close();
            this.shaderProgram = null;
         }

         this.fullscreenQuad.close();
      } catch (Throwable exception3) {
      }

      this.flag = false;
      this.timestamp = 0L;
      this.timestamp2 = 0L;
      this.floatValue = 0.0F;
      this.flag3 = false;
      this.floatValue2 = 0.5F;
      this.floatValue3 = 0.5F;
      this.timestamp3 = 0L;
      this.floatValue4 = 0.0F;
      this.floatValue5 = 0.0F;
   }

   public void invoke4(int i, int j) {
      try {
         if (this.intValue != 0) {
            GL11.glDeleteTextures(this.intValue);
            this.intValue = 0;
         }

         this.intValue2 = 0;
         this.intValue3 = 0;
         if (this.shaderProgram != null) {
            try {
               this.shaderProgram.close();
            } catch (Throwable exception4) {
            }

            this.shaderProgram = null;
         }

         try {
            this.fullscreenQuad.close();
         } catch (Throwable exception5) {
         }

         this.fullscreenQuadResources = null;
         this.flag = false;
         this.flag2 = false;
         this.timestamp = 0L;
         this.timestamp2 = 0L;
         this.floatValue = 0.0F;
         this.flag3 = false;
         this.floatValue2 = 0.5F;
         this.floatValue3 = 0.5F;
         this.timestamp3 = 0L;
         this.floatValue4 = 0.0F;
         this.floatValue5 = 0.0F;
      } catch (Throwable exception6) {
      }
   }
}
