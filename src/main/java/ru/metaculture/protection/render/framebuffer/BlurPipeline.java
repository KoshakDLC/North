package ru.metaculture.protection;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public final class BlurPipeline {
   private static final int INT_VALUE = 6;
   private static final float FLOAT_VALUE = 0.5F;
   private static final float FLOAT_VALUE_2 = 30.0F;
   private final GlShaderProgram glShaderProgram;
   private final GlShaderProgram glShaderProgram2;
   private final GlShaderProgram glShaderProgram3;
   private final GlShaderProgram glShaderProgram4;
   private final int intValue;
   private final int intValue2;
   private final int intValue3;
   private final int intValue4;
   private final int intValue5;
   private final int intValue6;
   private final int intValue7;
   private final int intValue8;
   private final int intValue9;
   private final int intValue10;
   private final int intValue11;
   private final int intValue12;
   private final int intValue13;
   private final int intValue14;
   private int intValue15;
   private int intValue16;
   private final BlurPipeline.BlurPipelineState[] blurPipelineStates = new BlurPipeline.BlurPipelineState[6];
   private final BlurPipeline.BlurPipelineState blurPipelineState = new BlurPipeline.BlurPipelineState();
   private final BlurPipeline.BlurPipelineState blurPipelineState2 = new BlurPipeline.BlurPipelineState();

   public float measure() {
      return 0.5F;
   }

   public float measure2() {
      return 30.0F;
   }

   public BlurPipeline() {
      this(32856, 5121);
   }

   public BlurPipeline(int i, int j) {
      if (i == 0) {
         throw new IllegalArgumentException("intermediateInternalFormat must be a valid OpenGL format constant");
      } else if (j == 0) {
         throw new IllegalArgumentException("intermediatePixelType must be a valid OpenGL pixel type constant");
      } else {
         this.glShaderProgram = GlShaderProgram.resolve("assets/wild/shaders/blur/blur_fullscreen.vert", "assets/wild/shaders/blur/blur_downsample.frag");
         this.glShaderProgram2 = GlShaderProgram.resolve("assets/wild/shaders/blur/blur_fullscreen.vert", "assets/wild/shaders/blur/blur_upsample.frag");
         this.glShaderProgram3 = GlShaderProgram.resolve("assets/wild/shaders/blur/blur_fullscreen.vert", "assets/wild/shaders/blur/blur_small_horizontal.frag");
         this.glShaderProgram4 = GlShaderProgram.resolve("assets/wild/shaders/blur/blur_fullscreen.vert", "assets/wild/shaders/blur/blur_small_vertical.frag");
         this.intValue = i;
         this.intValue2 = j;
         this.intValue3 = this.glShaderProgram.compute2("uSource");
         this.intValue4 = this.glShaderProgram.compute2("uTexelSize");
         this.intValue5 = this.glShaderProgram.compute2("uOffset");
         this.intValue6 = this.glShaderProgram2.compute2("uSource");
         this.intValue7 = this.glShaderProgram2.compute2("uTexelSize");
         this.intValue8 = this.glShaderProgram2.compute2("uOffset");
         this.intValue9 = this.glShaderProgram3.compute2("uSource");
         this.intValue10 = this.glShaderProgram3.compute2("uTexelSize");
         this.intValue11 = this.glShaderProgram3.compute2("uRadius");
         this.intValue12 = this.glShaderProgram4.compute2("uSource");
         this.intValue13 = this.glShaderProgram4.compute2("uTexelSize");
         this.intValue14 = this.glShaderProgram4.compute2("uRadius");

         for (int intValue = 0; intValue < this.blurPipelineStates.length; intValue++) {
            this.blurPipelineStates[intValue] = new BlurPipeline.BlurPipelineState();
         }

         FramebufferUtils.GlStateSnapshot glStateSnapshot = FramebufferUtils.captureGlState();

         try {
            this.intValue15 = GL30.glGenVertexArrays();
            this.intValue16 = GL15.glGenBuffers();
            GL30.glBindVertexArray(this.intValue15);
            GL15.glBindBuffer(34962, this.intValue16);
            float[] floatValues = new float[]{-1.0F, -1.0F, 0.0F, 0.0F, 1.0F, -1.0F, 1.0F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F};
            GL15.glBufferData(34962, floatValues, 35044);
            byte byteValue = 16;
            GL20.glEnableVertexAttribArray(0);
            GL20.glVertexAttribPointer(0, 2, 5126, false, byteValue, 0L);
            GL20.glEnableVertexAttribArray(1);
            GL20.glVertexAttribPointer(1, 2, 5126, false, byteValue, 8L);
         } finally {
            FramebufferUtils.restoreGlState(glStateSnapshot);
         }
      }
   }

   public void invoke() {
      this.invoke2();
      if (this.intValue15 != 0) {
         GL30.glDeleteVertexArrays(this.intValue15);
         this.intValue15 = 0;
      }

      if (this.intValue16 != 0) {
         GL15.glDeleteBuffers(this.intValue16);
         this.intValue16 = 0;
      }

      this.glShaderProgram.invoke2();
      this.glShaderProgram2.invoke2();
      this.glShaderProgram3.invoke2();
      this.glShaderProgram4.invoke2();
   }

   public void invoke2() {
      for (BlurPipeline.BlurPipelineState blurPipelineState : this.blurPipelineStates) {
         this.invoke6(blurPipelineState);
      }

      this.invoke6(this.blurPipelineState);
      this.invoke6(this.blurPipelineState2);
   }

   public int compute(int i, int j, int k, float f) {
      return this.compute2(i, j, k, f, true);
   }

   public int compute2(int i, int j, int k, float f, boolean bl) {
      if (i != 0 && j > 0 && k > 0) {
         float floatValue = Math.max(f, 0.5F);
         boolean flag = floatValue <= 30.0F;
         int intValue2 = 0;
         float[] floatValues2 = null;
         if (flag) {
            if (!this.check3(this.blurPipelineState, j, k) || !this.check3(this.blurPipelineState2, j, k)) {
               return 0;
            }
         } else {
            intValue2 = this.compute5(floatValue, j, k);
            if (intValue2 <= 0) {
               return i;
            }

            floatValues2 = this.resolve(intValue2, floatValue);
            if (!this.check2(j, k, intValue2) || !this.check3(this.blurPipelineState, j, k)) {
               return 0;
            }
         }

         FramebufferUtils.GlStateSnapshot glStateSnapshot2 = bl ? FramebufferUtils.captureGlState() : null;
         boolean flag2 = false ;

         int intValue3;
         try {
            flag2 = true;

            try (ScreenTransitionShader screenTransitionShader = ScreenTransitionShader.resolve(0, 3553)) {
               GL11.glDisable(3089);
               GL11.glDisable(2929);
               GL11.glDisable(2884);
               GL11.glDisable(3042);
               GL11.glDisable(36281);
               GL13.glActiveTexture(33984);
               GL30.glBindVertexArray(this.intValue15);
               if (flag) {
                  this.invoke3(i, j, k, floatValue);
               } else {
                  this.invoke4(i, j, k, intValue2, floatValues2);
               }

               intValue3 = this.blurPipelineState.intValue2;
            }
         } finally {
            if (flag2) {
               GL30.glBindVertexArray(0);
               GL20.glUseProgram(0);
               GL30.glBindFramebuffer(36160, 0);
               GL13.glActiveTexture(33984);
               GL11.glBindTexture(3553, 0);
               if (bl && glStateSnapshot2 != null) {
                  FramebufferUtils.restoreGlState(glStateSnapshot2);
               }
            }
         }

         GL30.glBindVertexArray(0);
         GL20.glUseProgram(0);
         GL30.glBindFramebuffer(36160, 0);
         GL13.glActiveTexture(33984);
         GL11.glBindTexture(3553, 0);
         if (bl && glStateSnapshot2 != null) {
            FramebufferUtils.restoreGlState(glStateSnapshot2);
         }

         return intValue3;
      } else {
         return 0;
      }
   }

   private void invoke3(int i, int j, int k, float f) {
      this.glShaderProgram3.invoke();
      if (this.intValue9 >= 0) {
         GL20.glUniform1i(this.intValue9, 0);
      }

      if (this.intValue10 >= 0) {
         GL20.glUniform2f(this.intValue10, 1.0F / Math.max(1, j), 1.0F / Math.max(1, k));
      }

      if (this.intValue11 >= 0) {
         GL20.glUniform1f(this.intValue11, f);
      }

      if (this.check(this.blurPipelineState2)) {
         GL11.glBindTexture(3553, i);
         this.invoke5();
         this.glShaderProgram4.invoke();
         if (this.intValue12 >= 0) {
            GL20.glUniform1i(this.intValue12, 0);
         }

         if (this.intValue13 >= 0) {
            GL20.glUniform2f(this.intValue13, 1.0F / Math.max(1, j), 1.0F / Math.max(1, k));
         }

         if (this.intValue14 >= 0) {
            GL20.glUniform1f(this.intValue14, f);
         }

         if (this.check(this.blurPipelineState)) {
            GL11.glBindTexture(3553, this.blurPipelineState2.intValue2);
            this.invoke5();
         }
      }
   }

   private void invoke4(int i, int j, int k, int l, float[] fs) {
      if (fs != null && fs.length == l) {
         int intValue4 = i;
         int intValue5 = j;
         int intValue6 = k;
         this.glShaderProgram.invoke();
         if (this.intValue3 >= 0) {
            GL20.glUniform1i(this.intValue3, 0);
         }

         for (int intValue7 = 0; intValue7 < l; intValue7++) {
            BlurPipeline.BlurPipelineState blurPipelineState2 = this.blurPipelineStates[intValue7];
            if (!this.check(blurPipelineState2)) {
               return;
            }

            if (this.intValue4 >= 0) {
               GL20.glUniform2f(this.intValue4, 1.0F / Math.max(1, intValue5), 1.0F / Math.max(1, intValue6));
            }

            if (this.intValue5 >= 0) {
               GL20.glUniform1f(this.intValue5, fs[intValue7]);
            }

            GL11.glBindTexture(3553, intValue4);
            this.invoke5();
            intValue4 = blurPipelineState2.intValue2;
            intValue5 = blurPipelineState2.intValue3;
            intValue6 = blurPipelineState2.intValue4;
         }

         this.glShaderProgram2.invoke();
         if (this.intValue6 >= 0) {
            GL20.glUniform1i(this.intValue6, 0);
         }

         for (int intValue8 = l - 2; intValue8 >= 0; intValue8--) {
            BlurPipeline.BlurPipelineState blurPipelineState3 = this.blurPipelineStates[intValue8];
            if (!this.check(blurPipelineState3)) {
               return;
            }

            if (this.intValue7 >= 0) {
               GL20.glUniform2f(this.intValue7, 1.0F / Math.max(1, intValue5), 1.0F / Math.max(1, intValue6));
            }

            if (this.intValue8 >= 0) {
               GL20.glUniform1f(this.intValue8, fs[intValue8]);
            }

            GL11.glBindTexture(3553, intValue4);
            this.invoke5();
            intValue4 = blurPipelineState3.intValue2;
            intValue5 = blurPipelineState3.intValue3;
            intValue6 = blurPipelineState3.intValue4;
         }

         if (this.check(this.blurPipelineState)) {
            if (this.intValue7 >= 0) {
               GL20.glUniform2f(this.intValue7, 1.0F / Math.max(1, intValue5), 1.0F / Math.max(1, intValue6));
            }

            if (this.intValue8 >= 0) {
               GL20.glUniform1f(this.intValue8, fs.length > 0 ? fs[0] : 0.5F);
            }

            GL11.glBindTexture(3553, intValue4);
            this.invoke5();
         }
      } else {
         throw new IllegalArgumentException("offsets length must match passCount");
      }
   }

   private void invoke5() {
      GlStateGuard.getINSTANCE().invoke2(2);
      GL11.glDrawArrays(5, 0, 4);
   }

   private boolean check(BlurPipeline.BlurPipelineState blurPipelineState4) {
      if (blurPipelineState4 != null && blurPipelineState4.intValue != 0 && blurPipelineState4.intValue2 != 0 && blurPipelineState4.intValue3 > 0 && blurPipelineState4.intValue4 > 0) {
         GL30.glBindFramebuffer(36160, blurPipelineState4.intValue);
         GL11.glViewport(0, 0, blurPipelineState4.intValue3, blurPipelineState4.intValue4);
         GL11.glDrawBuffer(36064);
         return true;
      } else {
         return false;
      }
   }

   private boolean check2(int i, int j, int k) {
      if (i > 0 && j > 0 && k > 0) {
         for (int intValue9 = 0; intValue9 < k; intValue9++) {
            int intValue10 = 1 << intValue9 + 1;
            int intValue11 = Math.max(1, i / intValue10);
            int intValue12 = Math.max(1, j / intValue10);
            if (!this.check3(this.blurPipelineStates[intValue9], intValue11, intValue12)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private boolean check3(BlurPipeline.BlurPipelineState blurPipelineState5, int i, int j) {
      if (blurPipelineState5 == null) {
         return false;
      } else if (i > 0 && j > 0) {
         if (blurPipelineState5.intValue2 != 0 && (blurPipelineState5.intValue3 != i || blurPipelineState5.intValue4 != j)) {
            GL11.glDeleteTextures(blurPipelineState5.intValue2);
            GL30.glDeleteFramebuffers(blurPipelineState5.intValue);
            blurPipelineState5.intValue2 = 0;
            blurPipelineState5.intValue = 0;
         }

         FramebufferUtils.GlStateSnapshot glStateSnapshot3;
         boolean flag3;
         label82: {
            label100: {
               if (blurPipelineState5.intValue2 == 0) {
                  glStateSnapshot3 = FramebufferUtils.captureGlState();
                  boolean flag4 = false ;

                  try {
                     flag4 = true;
                     blurPipelineState5.intValue2 = this.compute3(i, j);
                     if (blurPipelineState5.intValue2 == 0) {
                        blurPipelineState5.intValue3 = 0;
                        blurPipelineState5.intValue4 = 0;
                        flag3 = false;
                        flag4 = false;
                        break label82;
                     }

                     blurPipelineState5.intValue = this.compute4(blurPipelineState5.intValue2);
                     if (blurPipelineState5.intValue == 0) {
                        GL11.glDeleteTextures(blurPipelineState5.intValue2);
                        blurPipelineState5.intValue2 = 0;
                        blurPipelineState5.intValue3 = 0;
                        blurPipelineState5.intValue4 = 0;
                        flag3 = false;
                        flag4 = false;
                        break label100;
                     }

                     flag4 = false;
                  } finally {
                     if (flag4) {
                        FramebufferUtils.restoreGlState(glStateSnapshot3);
                     }
                  }

                  FramebufferUtils.restoreGlState(glStateSnapshot3);
               }

               blurPipelineState5.intValue3 = i;
               blurPipelineState5.intValue4 = j;
               return true;
            }

            FramebufferUtils.restoreGlState(glStateSnapshot3);
            return flag3;
         }

         FramebufferUtils.restoreGlState(glStateSnapshot3);
         return flag3;
      } else {
         this.invoke6(blurPipelineState5);
         return false;
      }
   }

   private void invoke6(BlurPipeline.BlurPipelineState blurPipelineState6) {
      if (blurPipelineState6 != null) {
         if (blurPipelineState6.intValue2 != 0) {
            GL11.glDeleteTextures(blurPipelineState6.intValue2);
            blurPipelineState6.intValue2 = 0;
         }

         if (blurPipelineState6.intValue != 0) {
            GL30.glDeleteFramebuffers(blurPipelineState6.intValue);
            blurPipelineState6.intValue = 0;
         }

         blurPipelineState6.intValue3 = 0;
         blurPipelineState6.intValue4 = 0;
      }
   }

   private int compute3(int i, int j) {
      if (i > 0 && j > 0) {
         int intValue13 = GL11.glGenTextures();
         GL11.glBindTexture(3553, intValue13);
         GL11.glTexParameteri(3553, 10241, 9729);
         GL11.glTexParameteri(3553, 10240, 9729);
         GL11.glTexParameteri(3553, 10242, 33071);
         GL11.glTexParameteri(3553, 10243, 33071);
         RenderCapabilities.invoke(this.intValue, i, j, 6408, this.intValue2);
         GL11.glBindTexture(3553, 0);
         return intValue13;
      } else {
         return 0;
      }
   }

   private int compute4(int i) {
      if (i <= 0) {
         return 0;
      } else {
         int intValue14 = GL30.glGenFramebuffers();
         GL30.glBindFramebuffer(36160, intValue14);
         GL30.glFramebufferTexture2D(36160, 36064, 3553, i, 0);
         int intValue15 = GL30.glCheckFramebufferStatus(36160);
         GL30.glBindFramebuffer(36160, 0);
         if (intValue15 != 36053) {
            GL30.glDeleteFramebuffers(intValue14);
            GL11.glDeleteTextures(i);
            throw new IllegalStateException("Blur framebuffer incomplete: status=" + intValue15);
         } else {
            return intValue14;
         }
      }
   }

   private int compute5(float f, int i, int j) {
      int intValue16 = 0;
      int intValue17 = i;
      int intValue18 = j;

      while (intValue16 < 6 && (intValue17 > 1 || intValue18 > 1)) {
         intValue17 = Math.max(1, intValue17 / 2);
         intValue18 = Math.max(1, intValue18 / 2);
         intValue16++;
         if (intValue17 == 1 && intValue18 == 1) {
            break;
         }
      }

      if (intValue16 == 0) {
         intValue16 = 1;
      }

      int intValue19 = Math.max(1, (int)Math.ceil(Math.sqrt(f / 2.0F)));
      return Math.min(intValue16, intValue19);
   }

   private float[] resolve(int i, float f) {
      float[] floatValues3 = new float[i];

      for (int intValue20 = 0; intValue20 < i; intValue20++) {
         float floatValue2 = 1.0F / (1 << intValue20);
         float floatValue3 = f / i;
         floatValues3[intValue20] = Math.max(0.5F, floatValue3 * floatValue2 * 2.0F + 0.5F);
      }

      return floatValues3;
   }

   static final class BlurPipelineState {
      int intValue;
      int intValue2;
      int intValue3;
      int intValue4;
   }
}
