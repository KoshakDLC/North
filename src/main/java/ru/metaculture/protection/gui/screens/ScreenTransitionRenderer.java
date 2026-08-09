package ru.metaculture.protection;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.texture.GlTexture;
import net.minecraft.client.util.Window;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public final class ScreenTransitionRenderer {
   private static final ScreenTransitionRenderer INSTANCE = new ScreenTransitionRenderer();
   private static final float FLOAT_VALUE = 0.3F;
   private static final float FLOAT_VALUE_2 = 9.0F;
   private static final float FLOAT_VALUE_3 = 0.5F;
   private static final ScreenTransitionRenderer.ScreenTransitionRendererData SCREEN_TRANSITION_RENDERER_DATA = new ScreenTransitionRenderer.ScreenTransitionRendererData(0.4F, 0.0F, 0.1F, 1.0F);
   private final ScreenTransitionRenderer.ScreenTransitionRendererState screenTransitionRendererState = new ScreenTransitionRenderer.ScreenTransitionRendererState();
   private final ScreenTransitionRenderer.ScreenTransitionRendererState screenTransitionRendererState2 = new ScreenTransitionRenderer.ScreenTransitionRendererState();
   private BlurPipeline blurPipeline;
   private GlShaderProgram glShaderProgram;
   private int intValue = -1;
   private int intValue2 = -1;
   private int intValue3 = -1;
   private int intValue4 = -1;
   private int intValue5 = -1;
   private int intValue6 = -1;
   private int intValue7 = -1;
   private int intValue8 = -1;
   private int intValue9 = -1;
   private int intValue10 = -1;
   private int intValue11 = -1;
   private int intValue12;
   private int intValue13;
   private int intValue14;
   private int intValue15;
   private boolean flag;
   private float floatValue;
   private float floatValue2;
   private float floatValue3;

   private ScreenTransitionRenderer() {
   }

   public static ScreenTransitionRenderer getINSTANCE() {
      return INSTANCE;
   }

   public void invoke(Screen screen, Screen screen2) {
      if (screen != screen2) {
         if (screen instanceof BackdropScreen || screen2 instanceof BackdropScreen) {
            this.invoke11();
         } else if (WeakGlCompatibility.check2()) {
            this.invoke11();
         } else if (!MenuModule.check(MenuModule.PEREHODY_EKRANA)) {
            this.invoke11();
         } else {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client != null && client.world != null && screen2 == null) {
               this.invoke11();
            } else if (check6(client, screen, screen2) && check5(client)) {
               Window window = client.getWindow();
               int intValue = window.getFramebufferWidth();
               int intValue2 = window.getFramebufferHeight();
               if (intValue > 0 && intValue2 > 0) {
                  boolean flag = this.flag
                     && this.check4(this.screenTransitionRendererState2, intValue, intValue2)
                     && this.check2(this.screenTransitionRendererState2.intValue2, intValue, intValue2, this.screenTransitionRendererState);
                  if (!flag) {
                     flag = this.check(client, this.screenTransitionRendererState, intValue, intValue2);
                  }

                  if (!flag) {
                     this.invoke10();
                  } else {
                     this.flag = true;
                     this.floatValue = 0.0F;
                     this.floatValue2 = 0.0F;
                     this.floatValue3 = 0.0F;
                  }
               } else {
                  this.invoke12();
               }
            } else {
               this.invoke10();
            }
         }
      }
   }

   public void invoke2() {
      MinecraftClient client2 = MinecraftClient.getInstance();
      float floatValue = client2 != null && client2.getRenderTickCounter() != null ? client2.getRenderTickCounter().getDynamicDeltaTicks() : 0.0F;
      this.invoke3(floatValue);
   }

   public void invoke3(float f) {
      if (this.flag) {
         MinecraftClient client3 = MinecraftClient.getInstance();
         if (client3 == null || !check5(client3)) {
            this.invoke10();
         } else if (client3.currentScreen != null && client3.world == null) {
            Window window2 = client3.getWindow();
            int intValue3 = window2.getFramebufferWidth();
            int intValue4 = window2.getFramebufferHeight();
            if (intValue3 > 0 && intValue4 > 0 && this.check4(this.screenTransitionRendererState, intValue3, intValue4)) {
               this.invoke6(f);
               if (this.floatValue >= 1.0F) {
                  this.invoke10();
               } else {
                  int intValue5 = this.compute2(client3);
                  if (intValue5 > 0 && this.check(client3, this.screenTransitionRendererState2, intValue3, intValue4) && this.check4(this.screenTransitionRendererState2, intValue3, intValue4)) {
                     this.invoke9();
                     if (this.glShaderProgram != null && this.intValue14 != 0) {
                        float floatValue2 = this.measure();
                        float floatValue3 = measure4(floatValue2 / 9.0F, 0.0F, 1.0F);
                        int intValue6 = this.compute(intValue3, intValue4, floatValue2);
                        if (intValue6 <= 0) {
                           intValue6 = this.screenTransitionRendererState2.intValue2;
                           floatValue3 = 0.0F;
                        }

                        this.invoke7(intValue5, intValue3, intValue4, intValue6, floatValue3);
                        if (this.floatValue >= 1.0F) {
                           this.invoke10();
                        }
                     } else {
                        this.invoke10();
                     }
                  } else {
                     this.invoke10();
                  }
               }
            } else {
               this.invoke12();
            }
         } else {
            this.invoke11();
         }
      }
   }

   public void invoke4(int i, int j) {
      if (i > 0 && j > 0) {
         if ((this.screenTransitionRendererState.intValue3 <= 0 || this.screenTransitionRendererState.intValue3 == i && this.screenTransitionRendererState.intValue4 == j)
            && (this.screenTransitionRendererState2.intValue3 <= 0 || this.screenTransitionRendererState2.intValue3 == i && this.screenTransitionRendererState2.intValue4 == j)) {
            if (this.blurPipeline != null) {
               this.blurPipeline.invoke2();
            }
         } else {
            this.invoke12();
         }
      } else {
         this.invoke12();
      }
   }

   public void invoke5(boolean bl) {
      if (!bl) {
         this.invoke12();
      }
   }

   private void invoke6(float f) {
      float floatValue4 = measure3(f);
      this.floatValue3 += floatValue4;
      this.floatValue = measure4(this.floatValue + floatValue4 / 0.3F, 0.0F, 1.0F);
      this.floatValue2 = SCREEN_TRANSITION_RENDERER_DATA.solve(this.floatValue);
   }

   private float measure() {
      float floatValue5 = measure2(this.floatValue2 * 1.6F);
      return 9.0F * floatValue5;
   }

   private int compute(int i, int j, float f) {
      if (f < 0.5F) {
         return this.screenTransitionRendererState.intValue2;
      } else {
         if (this.blurPipeline == null) {
            this.blurPipeline = new BlurPipeline(32856, 5121);
         }

         return this.blurPipeline.compute(this.screenTransitionRendererState.intValue2, i, j, f);
      }
   }

   private static float measure2(float f) {
      float floatValue6 = measure4(f, 0.0F, 1.0F);
      return floatValue6 * floatValue6 * (3.0F - 2.0F * floatValue6);
   }

   private void invoke7(int i, int j, int k, int l, float f) {
      FramebufferUtils.GlStateSnapshot glStateSnapshot = FramebufferUtils.captureGlState();
      boolean flag2 = false ;

      label202: {
         try {
            flag2 = true;

            try (
               ScreenTransitionShader screenTransitionShader = ScreenTransitionShader.resolve(0, 3553);
               ScreenTransitionShader screenTransitionShader2 = ScreenTransitionShader.resolve(1, 3553);
               ScreenTransitionShader screenTransitionShader3 = ScreenTransitionShader.resolve(2, 3553);
            ) {
               if (this.intValue13 == 0) {
                  this.intValue13 = GL30.glGenFramebuffers();
               }

               GL30.glBindFramebuffer(36160, this.intValue13);
               GL30.glFramebufferTexture2D(36160, 36064, 3553, i, 0);
               GL11.glDrawBuffer(36064);
               if (GL30.glCheckFramebufferStatus(36160) != 36053) {
                  break label202;
               }

               GL11.glViewport(0, 0, Math.max(0, j), Math.max(0, k));
               GL11.glDisable(3089);
               GL11.glDisable(2884);
               GL11.glDisable(2929);
               GL11.glDisable(3042);
               GL11.glDisable(36281);
               GL11.glColorMask(true, true, true, true);
               GL11.glDepthMask(false);
               this.glShaderProgram.invoke();
               this.invoke8(j, k, f);
               GL13.glActiveTexture(33984);
               GL11.glBindTexture(3553, this.screenTransitionRendererState.intValue2);
               GL13.glActiveTexture(33985);
               GL11.glBindTexture(3553, this.screenTransitionRendererState2.intValue2);
               GL13.glActiveTexture(33986);
               GL11.glBindTexture(3553, l);
               GL30.glBindVertexArray(this.intValue14);
               GlStateGuard.getINSTANCE().invoke2(2);
               GL11.glDrawArrays(4, 0, 6);
               GL30.glBindVertexArray(0);
            }
         } finally {
            if (flag2) {
               if (this.intValue13 != 0) {
                  GL30.glBindFramebuffer(36160, this.intValue13);
                  GL30.glFramebufferTexture2D(36160, 36064, 3553, 0, 0);
               }

               GL20.glUseProgram(0);
               FramebufferUtils.restoreGlState(glStateSnapshot);
            }
         }

         if (this.intValue13 != 0) {
            GL30.glBindFramebuffer(36160, this.intValue13);
            GL30.glFramebufferTexture2D(36160, 36064, 3553, 0, 0);
         }

         GL20.glUseProgram(0);
         FramebufferUtils.restoreGlState(glStateSnapshot);
         return;
      }

      if (this.intValue13 != 0) {
         GL30.glBindFramebuffer(36160, this.intValue13);
         GL30.glFramebufferTexture2D(36160, 36064, 3553, 0, 0);
      }

      GL20.glUseProgram(0);
      FramebufferUtils.restoreGlState(glStateSnapshot);
   }

   private void invoke8(int i, int j, float f) {
      if (this.intValue >= 0) {
         GL20.glUniform1i(this.intValue, 0);
      }

      if (this.intValue2 >= 0) {
         GL20.glUniform1i(this.intValue2, 1);
      }

      if (this.intValue3 >= 0) {
         GL20.glUniform1i(this.intValue3, 2);
      }

      if (this.intValue4 >= 0) {
         GL20.glUniform2f(this.intValue4, i, j);
      }

      if (this.intValue5 >= 0) {
         GL20.glUniform1f(this.intValue5, this.floatValue2);
      }

      if (this.intValue6 >= 0) {
         GL20.glUniform1f(this.intValue6, this.floatValue);
      }

      if (this.intValue7 >= 0) {
         GL20.glUniform1f(this.intValue7, this.floatValue2);
      }

      if (this.intValue8 >= 0) {
         GL20.glUniform1f(this.intValue8, 1.04F - 0.04F * this.floatValue2);
      }

      if (this.intValue9 >= 0) {
         GL20.glUniform1f(this.intValue9, f);
      }

      if (this.intValue10 >= 0) {
         GL20.glUniform1f(this.intValue10, 0.0F);
      }

      if (this.intValue11 >= 0) {
         GL20.glUniform1f(this.intValue11, this.floatValue3);
      }
   }

   private boolean check(MinecraftClient minecraftClient, ScreenTransitionRenderer.ScreenTransitionRendererState screenTransitionRendererState, int i, int j) {
      int intValue7 = this.compute2(minecraftClient);
      return intValue7 > 0 && this.check2(intValue7, i, j, screenTransitionRendererState);
   }

   private int compute2(MinecraftClient minecraftClient) {
      if (minecraftClient == null) {
         return 0;
      } else {
         Framebuffer framebuffer = minecraftClient.getFramebuffer();
         if (framebuffer == null) {
            return 0;
         } else {
            return framebuffer.getColorAttachment() instanceof GlTexture glTexture ? glTexture.getGlId() : 0;
         }
      }
   }

   private boolean check2(int i, int j, int k, ScreenTransitionRenderer.ScreenTransitionRendererState screenTransitionRendererState2) {
      if (i > 0 && j > 0 && k > 0 && this.check3(screenTransitionRendererState2, j, k)) {
         FramebufferUtils.GlStateSnapshot glStateSnapshot2 = FramebufferUtils.captureGlState();
         boolean flag3 = false ;

         boolean flag4;
         label85: {
            try {
               flag3 = true;
               if (this.intValue12 == 0) {
                  this.intValue12 = GL30.glGenFramebuffers();
               }

               GL11.glDisable(3089);
               GL11.glDisable(3042);
               GL11.glDisable(2884);
               GL11.glDisable(2929);
               GL11.glDisable(36281);
               GL30.glBindFramebuffer(36008, this.intValue12);
               GL30.glFramebufferTexture2D(36008, 36064, 3553, i, 0);
               if (GL30.glCheckFramebufferStatus(36008) != 36053) {
                  flag4 = false;
                  flag3 = false;
                  break label85;
               }

               GL30.glBindFramebuffer(36009, screenTransitionRendererState2.intValue);
               GL11.glReadBuffer(36064);
               GL11.glDrawBuffer(36064);
               GL30.glBlitFramebuffer(0, 0, j, k, 0, 0, j, k, 16384, 9728);
               flag4 = true;
               flag3 = false;
            } finally {
               if (flag3) {
                  if (this.intValue12 != 0) {
                     GL30.glBindFramebuffer(36008, this.intValue12);
                     GL30.glFramebufferTexture2D(36008, 36064, 3553, 0, 0);
                  }

                  FramebufferUtils.restoreGlState(glStateSnapshot2);
               }
            }

            if (this.intValue12 != 0) {
               GL30.glBindFramebuffer(36008, this.intValue12);
               GL30.glFramebufferTexture2D(36008, 36064, 3553, 0, 0);
            }

            FramebufferUtils.restoreGlState(glStateSnapshot2);
            return flag4;
         }

         if (this.intValue12 != 0) {
            GL30.glBindFramebuffer(36008, this.intValue12);
            GL30.glFramebufferTexture2D(36008, 36064, 3553, 0, 0);
         }

         FramebufferUtils.restoreGlState(glStateSnapshot2);
         return flag4;
      } else {
         return false;
      }
   }

   private boolean check3(ScreenTransitionRenderer.ScreenTransitionRendererState screenTransitionRendererState3, int i, int j) {
      if (screenTransitionRendererState3 != null && i > 0 && j > 0) {
         if (screenTransitionRendererState3.intValue2 != 0 && (screenTransitionRendererState3.intValue3 != i || screenTransitionRendererState3.intValue4 != j || screenTransitionRendererState3.intValue == 0)) {
            this.invoke13(screenTransitionRendererState3);
         }

         label61:
         if (screenTransitionRendererState3.intValue2 == 0) {
            FramebufferUtils.GlStateSnapshot glStateSnapshot3 = FramebufferUtils.captureGlState();

            boolean flag5;
            try {
               screenTransitionRendererState3.intValue2 = GL11.glGenTextures();
               GL11.glBindTexture(3553, screenTransitionRendererState3.intValue2);
               GL11.glTexParameteri(3553, 10241, 9729);
               GL11.glTexParameteri(3553, 10240, 9729);
               GL11.glTexParameteri(3553, 10242, 33071);
               GL11.glTexParameteri(3553, 10243, 33071);
               RenderCapabilities.invoke(32856, i, j, 6408, 5121);
               screenTransitionRendererState3.intValue = GL30.glGenFramebuffers();
               GL30.glBindFramebuffer(36160, screenTransitionRendererState3.intValue);
               GL30.glFramebufferTexture2D(36160, 36064, 3553, screenTransitionRendererState3.intValue2, 0);
               GL11.glDrawBuffer(36064);
               if (GL30.glCheckFramebufferStatus(36160) == 36053) {
                  break label61;
               }

               this.invoke13(screenTransitionRendererState3);
               flag5 = false;
            } finally {
               FramebufferUtils.restoreGlState(glStateSnapshot3);
            }

            return flag5;
         }

         screenTransitionRendererState3.intValue3 = i;
         screenTransitionRendererState3.intValue4 = j;
         return true;
      } else {
         return false;
      }
   }

   private void invoke9() {
      if (this.intValue14 == 0) {
         FramebufferUtils.GlStateSnapshot glStateSnapshot4 = FramebufferUtils.captureGlState();

         try {
            this.intValue14 = GL30.glGenVertexArrays();
            this.intValue15 = GL15.glGenBuffers();
            GL30.glBindVertexArray(this.intValue14);
            GL15.glBindBuffer(34962, this.intValue15);
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
         } finally {
            FramebufferUtils.restoreGlState(glStateSnapshot4);
         }
      }

      if (this.glShaderProgram == null) {
         this.glShaderProgram = GlShaderProgram.resolve(
            "assets/wild/shaders/blur/blur_fullscreen.vert", "assets/wild/shaders/postfx/cinematic_screen_transition.frag"
         );
         this.intValue = this.glShaderProgram.compute2("uOldScreen");
         this.intValue2 = this.glShaderProgram.compute2("uNewScreen");
         this.intValue3 = this.glShaderProgram.compute2("uBlurredScreen");
         this.intValue4 = this.glShaderProgram.compute2("uResolution");
         this.intValue5 = this.glShaderProgram.compute2("uProgress");
         this.intValue6 = this.glShaderProgram.compute2("uLinearProgress");
         this.intValue7 = this.glShaderProgram.compute2("uAlpha");
         this.intValue8 = this.glShaderProgram.compute2("uScale");
         this.intValue9 = this.glShaderProgram.compute2("uBlurMix");
         this.intValue10 = this.glShaderProgram.compute2("uExposure");
         this.intValue11 = this.glShaderProgram.compute2("uTime");
      }
   }

   private boolean check4(ScreenTransitionRenderer.ScreenTransitionRendererState screenTransitionRendererState4, int i, int j) {
      return screenTransitionRendererState4 != null && screenTransitionRendererState4.intValue != 0 && screenTransitionRendererState4.intValue2 != 0 && screenTransitionRendererState4.intValue3 == i && screenTransitionRendererState4.intValue4 == j;
   }

   private void invoke10() {
      this.flag = false;
      this.floatValue = 0.0F;
      this.floatValue2 = 0.0F;
      this.floatValue3 = 0.0F;
   }

   public void invoke11() {
      this.invoke12();
   }

   private void invoke12() {
      this.invoke10();
      this.invoke13(this.screenTransitionRendererState);
      this.invoke13(this.screenTransitionRendererState2);
      if (this.blurPipeline != null) {
         this.blurPipeline.invoke2();
      }
   }

   private void invoke13(ScreenTransitionRenderer.ScreenTransitionRendererState screenTransitionRendererState5) {
      if (screenTransitionRendererState5 != null) {
         if (screenTransitionRendererState5.intValue != 0) {
            GL30.glDeleteFramebuffers(screenTransitionRendererState5.intValue);
            screenTransitionRendererState5.intValue = 0;
         }

         if (screenTransitionRendererState5.intValue2 != 0) {
            GL11.glDeleteTextures(screenTransitionRendererState5.intValue2);
            screenTransitionRendererState5.intValue2 = 0;
         }

         screenTransitionRendererState5.intValue3 = 0;
         screenTransitionRendererState5.intValue4 = 0;
      }
   }

   private static boolean check5(MinecraftClient minecraftClient) {
      if (minecraftClient != null && minecraftClient.getWindow() != null) {
         Window window3 = minecraftClient.getWindow();
         return !window3.hasZeroWidthOrHeight() && window3.getFramebufferWidth() > 0 && window3.getFramebufferHeight() > 0;
      } else {
         return false;
      }
   }

   private static boolean check6(MinecraftClient minecraftClient, Screen screen, Screen screen2) {
      return !(screen instanceof BackdropScreen) && !(screen2 instanceof BackdropScreen)
         ? minecraftClient != null && minecraftClient.world == null && screen != null && screen2 != null
         : false;
   }

   private static float measure3(float f) {
      return Float.isFinite(f) && !(f <= 0.0F) ? measure4(f, 0.0F, 6.0F) * 0.05F : 0.0F;
   }

   static float measure4(float f, float g, float h) {
      return Math.max(g, Math.min(h, f));
   }

   record ScreenTransitionRendererData(float x1, float y1, float x2, float y2) {
      float solve(float f) {
         float floatValue7 = ScreenTransitionRenderer.measure4(f, 0.0F, 1.0F);
         float floatValue8 = floatValue7;

         for (int intValue8 = 0; intValue8 < 7; intValue8++) {
            float floatValue9 = sample(floatValue8, this.x1, this.x2) - floatValue7;
            if (Math.abs(floatValue9) < 1.0E-5F) {
               return ScreenTransitionRenderer.measure4(sample(floatValue8, this.y1, this.y2), 0.0F, 1.0F);
            }

            float floatValue10 = derivative(floatValue8, this.x1, this.x2);
            if (Math.abs(floatValue10) < 1.0E-5F) {
               break;
            }

            floatValue8 = ScreenTransitionRenderer.measure4(floatValue8 - floatValue9 / floatValue10, 0.0F, 1.0F);
         }

         float floatValue11 = 0.0F;
         float floatValue12 = 1.0F;
         floatValue8 = floatValue7;

         for (int intValue9 = 0; intValue9 < 10; intValue9++) {
            float floatValue13 = sample(floatValue8, this.x1, this.x2);
            if (Math.abs(floatValue13 - floatValue7) < 1.0E-5F) {
               break;
            }

            if (floatValue13 < floatValue7) {
               floatValue11 = floatValue8;
            } else {
               floatValue12 = floatValue8;
            }

            floatValue8 = (floatValue11 + floatValue12) * 0.5F;
         }

         return ScreenTransitionRenderer.measure4(sample(floatValue8, this.y1, this.y2), 0.0F, 1.0F);
      }

      private static float sample(float f, float g, float h) {
         float floatValue14 = 1.0F - f;
         return 3.0F * floatValue14 * floatValue14 * f * g + 3.0F * floatValue14 * f * f * h + f * f * f;
      }

      private static float derivative(float f, float g, float h) {
         float floatValue15 = 1.0F - f;
         return 3.0F * floatValue15 * floatValue15 * g + 6.0F * floatValue15 * f * (h - g) + 3.0F * f * f * (1.0F - h);
      }
   }

   static final class ScreenTransitionRendererState {
      int intValue;
      int intValue2;
      int intValue3;
      int intValue4;
   }
}
