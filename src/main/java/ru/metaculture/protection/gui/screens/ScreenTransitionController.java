package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.texture.GlTexture;
import net.minecraft.client.util.Window;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public final class ScreenTransitionController {
   private static final ScreenTransitionController INSTANCE = new ScreenTransitionController();
   private static final float FLOAT_VALUE = 1.08F;
   private static final int INT_VALUE = 10;
   private static final int INT_VALUE_2 = -4205825;
   private static final int INT_VALUE_3 = -8547073;
   private final ScreenTransitionController.ScreenTransitionControllerState screenTransitionControllerState = new ScreenTransitionController.ScreenTransitionControllerState();
   private final ScreenTransitionController.ScreenTransitionControllerState screenTransitionControllerState2 = new ScreenTransitionController.ScreenTransitionControllerState();
   private final ScreenTransitionController.ScreenTransitionControllerState screenTransitionControllerState3 = new ScreenTransitionController.ScreenTransitionControllerState();
   private final List<ScreenTransitionController.ScreenTransitionControllerUiState> items = new ArrayList<>();
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
   private int intValue12 = -1;
   private int intValue13;
   private int intValue14;
   private int intValue15;
   private int intValue16;

   private ScreenTransitionController() {
   }

   public static ScreenTransitionController getINSTANCE() {
      return INSTANCE;
   }

   public void invoke(float f, float g, int i, int j) {
      if (MenuModule.check(MenuModule.UDARNAYA_VOLNA_TEMY)) {
         MinecraftClient client = MinecraftClient.getInstance();
         if (!check8(client)) {
            this.invoke6();
         } else {
            Window window = client.getWindow();
            int intValue = window.getFramebufferWidth();
            int intValue2 = window.getFramebufferHeight();
            if (intValue > 0 && intValue2 > 0) {
               if (this.items.isEmpty()) {
                  if (!this.check4(client, this.screenTransitionControllerState, intValue, intValue2)) {
                     this.invoke6();
                     return;
                  }
               } else if (!this.check7(this.screenTransitionControllerState, intValue, intValue2)) {
                  this.invoke6();
                  return;
               }

               if (!this.check(client, intValue, intValue2)) {
                  this.invoke6();
               } else {
                  ScreenTransitionController.ScreenTransitionControllerUiState screenTransitionControllerUiState = new ScreenTransitionController.ScreenTransitionControllerUiState();
                  screenTransitionControllerUiState.floatValue4 = measure5(f, 0.0F, Math.max(0.0F, intValue - 1.0F));
                  screenTransitionControllerUiState.floatValue5 = measure5(g, 0.0F, Math.max(0.0F, intValue2 - 1.0F));
                  screenTransitionControllerUiState.intValue = i;
                  screenTransitionControllerUiState.intValue2 = j;
                  if (!this.check6(screenTransitionControllerUiState.screenTransitionControllerState, intValue, intValue2)) {
                     this.invoke12(screenTransitionControllerUiState.screenTransitionControllerState);
                     if (this.items.isEmpty()) {
                        this.invoke11();
                     }
                  } else {
                     this.items.add(screenTransitionControllerUiState);
                     BlurRenderer.getINSTANCE().invoke9();
                  }
               }
            } else {
               this.invoke11();
            }
         }
      }
   }

   public void invoke2(double d, double e, int i, int j) {
      MinecraftClient client2 = MinecraftClient.getInstance();
      if (!check8(client2)) {
         this.invoke6();
      } else {
         Window window2 = client2.getWindow();
         int intValue3 = window2.getFramebufferWidth();
         int intValue4 = window2.getFramebufferHeight();
         int intValue5 = window2.getScaledWidth();
         int intValue6 = window2.getScaledHeight();
         if (intValue3 > 0 && intValue4 > 0 && intValue5 > 0 && intValue6 > 0) {
            float floatValue = (float)(d * intValue3 / intValue5);
            float floatValue2 = (float)(e * intValue4 / intValue6);
            this.invoke(floatValue, floatValue2, i, j);
         } else {
            this.invoke6();
         }
      }
   }

   public void invoke3(float f) {
      if (!this.items.isEmpty()) {
         MinecraftClient client3 = MinecraftClient.getInstance();
         if (client3 != null && check8(client3) && client3.currentScreen != null) {
            Window window3 = client3.getWindow();
            int intValue7 = window3.getFramebufferWidth();
            int intValue8 = window3.getFramebufferHeight();
            if (intValue7 <= 0 || intValue8 <= 0 || !this.check7(this.screenTransitionControllerState, intValue7, intValue8)) {
               this.invoke11();
            } else if (!this.check2(intValue7, intValue8)) {
               this.invoke6();
            } else {
               ScreenTransitionController.ScreenTransitionControllerUiState screenTransitionControllerUiState2 = this.items.get(this.items.size() - 1);
               int intValue9 = this.compute(client3);
               if (intValue9 > 0 && this.check4(client3, screenTransitionControllerUiState2.screenTransitionControllerState, intValue7, intValue8)) {
                  this.invoke7(f);
                  if (!this.check3(intValue7, intValue8)) {
                     this.invoke6();
                  } else if (!this.items.isEmpty()) {
                     this.invoke10();
                     if (this.glShaderProgram != null && this.intValue15 != 0) {
                        if (this.items.size() <= 1
                           || this.check6(this.screenTransitionControllerState2, intValue7, intValue8) && this.check6(this.screenTransitionControllerState3, intValue7, intValue8)) {
                           int intValue10 = this.screenTransitionControllerState.intValue2;

                           for (int intValue11 = 0; intValue11 < this.items.size(); intValue11++) {
                              ScreenTransitionController.ScreenTransitionControllerUiState screenTransitionControllerUiState3 = this.items.get(intValue11);
                              boolean flag = intValue11 == this.items.size() - 1;
                              int intValue12 = flag ? intValue9 : ((intValue11 & 1) == 0 ? this.screenTransitionControllerState2.intValue2 : this.screenTransitionControllerState3.intValue2);
                              this.invoke8(intValue10, screenTransitionControllerUiState3.screenTransitionControllerState.intValue2, intValue12, intValue7, intValue8, screenTransitionControllerUiState3);
                              intValue10 = intValue12;
                           }
                        } else {
                           this.invoke6();
                        }
                     } else {
                        this.invoke6();
                     }
                  }
               } else {
                  this.invoke6();
               }
            }
         } else {
            this.invoke6();
         }
      }
   }

   public void invoke4(int i, int j) {
      if (i > 0 && j > 0) {
         if ((this.screenTransitionControllerState.intValue3 <= 0 || this.screenTransitionControllerState.intValue3 == i && this.screenTransitionControllerState.intValue4 == j)
            && (this.screenTransitionControllerState2.intValue3 <= 0 || this.screenTransitionControllerState2.intValue3 == i && this.screenTransitionControllerState2.intValue4 == j)
            && (this.screenTransitionControllerState3.intValue3 <= 0 || this.screenTransitionControllerState3.intValue3 == i && this.screenTransitionControllerState3.intValue4 == j)) {
            for (ScreenTransitionController.ScreenTransitionControllerUiState screenTransitionControllerUiState4 : this.items) {
               if (screenTransitionControllerUiState4.screenTransitionControllerState.intValue3 > 0 && (screenTransitionControllerUiState4.screenTransitionControllerState.intValue3 != i || screenTransitionControllerUiState4.screenTransitionControllerState.intValue4 != j)) {
                  this.invoke11();
                  return;
               }
            }
         } else {
            this.invoke11();
         }
      } else {
         this.invoke11();
      }
   }

   public void invoke5(boolean bl) {
      if (!bl) {
         this.invoke11();
      }
   }

   public void invoke6() {
      this.invoke11();
   }

   private boolean check(MinecraftClient minecraftClient, int i, int j) {
      while (this.items.size() >= 10 && !this.items.isEmpty()) {
         ScreenTransitionController.ScreenTransitionControllerUiState screenTransitionControllerUiState5 = this.items.remove(0);
         boolean flag2 = this.check5(screenTransitionControllerUiState5.screenTransitionControllerState.intValue2, i, j, this.screenTransitionControllerState);
         this.invoke12(screenTransitionControllerUiState5.screenTransitionControllerState);
         if (!flag2) {
            return false;
         }
      }

      return true;
   }

   private boolean check2(int i, int j) {
      for (ScreenTransitionController.ScreenTransitionControllerUiState screenTransitionControllerUiState6 : this.items) {
         if (!this.check6(screenTransitionControllerUiState6.screenTransitionControllerState, i, j)) {
            return false;
         }
      }

      return true;
   }

   private void invoke7(float f) {
      float floatValue3 = measure2(f);

      for (ScreenTransitionController.ScreenTransitionControllerUiState screenTransitionControllerUiState7 : this.items) {
         screenTransitionControllerUiState7.floatValue3 += floatValue3;
         screenTransitionControllerUiState7.floatValue = measure5(screenTransitionControllerUiState7.floatValue + floatValue3 / 1.08F, 0.0F, 1.0F);
         screenTransitionControllerUiState7.floatValue2 = measure3(screenTransitionControllerUiState7.floatValue);
      }
   }

   private boolean check3(int i, int j) {
      while (!this.items.isEmpty() && this.items.get(0).floatValue >= 1.0F) {
         ScreenTransitionController.ScreenTransitionControllerUiState screenTransitionControllerUiState8 = this.items.remove(0);
         boolean flag3 = this.check5(screenTransitionControllerUiState8.screenTransitionControllerState.intValue2, i, j, this.screenTransitionControllerState);
         this.invoke12(screenTransitionControllerUiState8.screenTransitionControllerState);
         if (!flag3) {
            return false;
         }
      }

      return true;
   }

   private void invoke8(int i, int j, int k, int l, int m, ScreenTransitionController.ScreenTransitionControllerUiState screenTransitionControllerUiState9) {
      FramebufferUtils.GlStateSnapshot glStateSnapshot = FramebufferUtils.captureGlState();
      boolean flag4 = false ;

      label155: {
         try {
            flag4 = true;

            try (
               ScreenTransitionShader screenTransitionShader = ScreenTransitionShader.resolve(0, 3553);
               ScreenTransitionShader screenTransitionShader2 = ScreenTransitionShader.resolve(1, 3553);
            ) {
               if (this.intValue14 == 0) {
                  this.intValue14 = GL30.glGenFramebuffers();
               }

               GL30.glBindFramebuffer(36160, this.intValue14);
               GL30.glFramebufferTexture2D(36160, 36064, 3553, k, 0);
               GL11.glDrawBuffer(36064);
               if (GL30.glCheckFramebufferStatus(36160) != 36053) {
                  break label155;
               }

               GL11.glViewport(0, 0, Math.max(0, l), Math.max(0, m));
               GL11.glDisable(3089);
               GL11.glDisable(2884);
               GL11.glDisable(2929);
               GL11.glDisable(3042);
               GL11.glDisable(36281);
               GL11.glColorMask(true, true, true, true);
               GL11.glDepthMask(false);
               this.glShaderProgram.invoke();
               this.invoke9(l, m, screenTransitionControllerUiState9);
               GL13.glActiveTexture(33984);
               GL11.glBindTexture(3553, i);
               GL13.glActiveTexture(33985);
               GL11.glBindTexture(3553, j);
               GL30.glBindVertexArray(this.intValue15);
               GlStateGuard.getINSTANCE().invoke2(2);
               GL11.glDrawArrays(4, 0, 6);
               GL30.glBindVertexArray(0);
            }
         } finally {
            if (flag4) {
               if (this.intValue14 != 0) {
                  GL30.glBindFramebuffer(36160, this.intValue14);
                  GL30.glFramebufferTexture2D(36160, 36064, 3553, 0, 0);
               }

               GL20.glUseProgram(0);
               FramebufferUtils.restoreGlState(glStateSnapshot);
            }
         }

         if (this.intValue14 != 0) {
            GL30.glBindFramebuffer(36160, this.intValue14);
            GL30.glFramebufferTexture2D(36160, 36064, 3553, 0, 0);
         }

         GL20.glUseProgram(0);
         FramebufferUtils.restoreGlState(glStateSnapshot);
         return;
      }

      if (this.intValue14 != 0) {
         GL30.glBindFramebuffer(36160, this.intValue14);
         GL30.glFramebufferTexture2D(36160, 36064, 3553, 0, 0);
      }

      GL20.glUseProgram(0);
      FramebufferUtils.restoreGlState(glStateSnapshot);
   }

   private void invoke9(int i, int j, ScreenTransitionController.ScreenTransitionControllerUiState screenTransitionControllerUiState10) {
      float floatValue4 = i / Math.max(1.0F, (float)j);
      float floatValue5 = this.measure(i, j, floatValue4, screenTransitionControllerUiState10.floatValue4, screenTransitionControllerUiState10.floatValue5);
      float floatValue6 = floatValue5 * (0.004F + screenTransitionControllerUiState10.floatValue2 * 1.145F);
      if (this.intValue >= 0) {
         GL20.glUniform1i(this.intValue, 0);
      }

      if (this.intValue2 >= 0) {
         GL20.glUniform1i(this.intValue2, 1);
      }

      if (this.intValue3 >= 0) {
         GL20.glUniform2f(this.intValue3, i, j);
      }

      if (this.intValue4 >= 0) {
         GL20.glUniform1f(this.intValue4, screenTransitionControllerUiState10.floatValue2);
      }

      if (this.intValue5 >= 0) {
         GL20.glUniform1f(this.intValue5, screenTransitionControllerUiState10.floatValue);
      }

      if (this.intValue6 >= 0) {
         GL20.glUniform1f(this.intValue6, screenTransitionControllerUiState10.floatValue3);
      }

      if (this.intValue7 >= 0) {
         GL20.glUniform2f(this.intValue7, screenTransitionControllerUiState10.floatValue4, screenTransitionControllerUiState10.floatValue5);
      }

      if (this.intValue8 >= 0) {
         GL20.glUniform1f(this.intValue8, floatValue4);
      }

      if (this.intValue9 >= 0) {
         GL20.glUniform1f(this.intValue9, floatValue6);
      }

      if (this.intValue10 >= 0) {
         GL20.glUniform1f(this.intValue10, floatValue5);
      }

      if (this.intValue11 >= 0) {
         GL20.glUniform3f(
            this.intValue11,
            (screenTransitionControllerUiState10.intValue >>> 16 & 0xFF) / 255.0F,
            (screenTransitionControllerUiState10.intValue >>> 8 & 0xFF) / 255.0F,
            (screenTransitionControllerUiState10.intValue & 0xFF) / 255.0F
         );
      }

      if (this.intValue12 >= 0) {
         GL20.glUniform3f(
            this.intValue12,
            (screenTransitionControllerUiState10.intValue2 >>> 16 & 0xFF) / 255.0F,
            (screenTransitionControllerUiState10.intValue2 >>> 8 & 0xFF) / 255.0F,
            (screenTransitionControllerUiState10.intValue2 & 0xFF) / 255.0F
         );
      }
   }

   private float measure(int i, int j, float f, float g, float h) {
      float floatValue7 = measure5(g / Math.max(1.0F, (float)i), 0.0F, 1.0F);
      float floatValue8 = measure5(1.0F - h / Math.max(1.0F, (float)j), 0.0F, 1.0F);
      float floatValue9 = measure4(floatValue7, floatValue8, 0.0F, 0.0F, f);
      float floatValue10 = measure4(floatValue7, floatValue8, 1.0F, 0.0F, f);
      float floatValue11 = measure4(floatValue7, floatValue8, 1.0F, 1.0F, f);
      float floatValue12 = measure4(floatValue7, floatValue8, 0.0F, 1.0F, f);
      return Math.max(Math.max(floatValue9, floatValue10), Math.max(floatValue11, floatValue12));
   }

   private boolean check4(MinecraftClient minecraftClient, ScreenTransitionController.ScreenTransitionControllerState screenTransitionControllerState, int i, int j) {
      int intValue13 = this.compute(minecraftClient);
      return intValue13 > 0 && this.check5(intValue13, i, j, screenTransitionControllerState);
   }

   private int compute(MinecraftClient minecraftClient) {
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

   private boolean check5(int i, int j, int k, ScreenTransitionController.ScreenTransitionControllerState screenTransitionControllerState2) {
      if (i > 0 && j > 0 && k > 0 && this.check6(screenTransitionControllerState2, j, k)) {
         FramebufferUtils.GlStateSnapshot glStateSnapshot2 = FramebufferUtils.captureGlState();
         boolean flag5 = false ;

         boolean flag6;
         label85: {
            try {
               flag5 = true;
               if (this.intValue13 == 0) {
                  this.intValue13 = GL30.glGenFramebuffers();
               }

               GL11.glDisable(3089);
               GL11.glDisable(3042);
               GL11.glDisable(2884);
               GL11.glDisable(2929);
               GL11.glDisable(36281);
               GL30.glBindFramebuffer(36008, this.intValue13);
               GL30.glFramebufferTexture2D(36008, 36064, 3553, i, 0);
               if (GL30.glCheckFramebufferStatus(36008) != 36053) {
                  flag6 = false;
                  flag5 = false;
                  break label85;
               }

               GL30.glBindFramebuffer(36009, screenTransitionControllerState2.intValue);
               GL11.glReadBuffer(36064);
               GL11.glDrawBuffer(36064);
               GL30.glBlitFramebuffer(0, 0, j, k, 0, 0, j, k, 16384, 9728);
               flag6 = true;
               flag5 = false;
            } finally {
               if (flag5) {
                  if (this.intValue13 != 0) {
                     GL30.glBindFramebuffer(36008, this.intValue13);
                     GL30.glFramebufferTexture2D(36008, 36064, 3553, 0, 0);
                  }

                  FramebufferUtils.restoreGlState(glStateSnapshot2);
               }
            }

            if (this.intValue13 != 0) {
               GL30.glBindFramebuffer(36008, this.intValue13);
               GL30.glFramebufferTexture2D(36008, 36064, 3553, 0, 0);
            }

            FramebufferUtils.restoreGlState(glStateSnapshot2);
            return flag6;
         }

         if (this.intValue13 != 0) {
            GL30.glBindFramebuffer(36008, this.intValue13);
            GL30.glFramebufferTexture2D(36008, 36064, 3553, 0, 0);
         }

         FramebufferUtils.restoreGlState(glStateSnapshot2);
         return flag6;
      } else {
         return false;
      }
   }

   private boolean check6(ScreenTransitionController.ScreenTransitionControllerState screenTransitionControllerState3, int i, int j) {
      if (screenTransitionControllerState3 != null && i > 0 && j > 0) {
         if (screenTransitionControllerState3.intValue2 != 0 && (screenTransitionControllerState3.intValue3 != i || screenTransitionControllerState3.intValue4 != j || screenTransitionControllerState3.intValue == 0)) {
            this.invoke12(screenTransitionControllerState3);
         }

         label63:
         if (screenTransitionControllerState3.intValue2 == 0) {
            FramebufferUtils.GlStateSnapshot glStateSnapshot3 = FramebufferUtils.captureGlState();

            boolean flag7;
            try {
               screenTransitionControllerState3.intValue2 = GL11.glGenTextures();
               GL11.glBindTexture(3553, screenTransitionControllerState3.intValue2);
               GL11.glTexParameteri(3553, 10241, 9729);
               GL11.glTexParameteri(3553, 10240, 9729);
               GL11.glTexParameteri(3553, 10242, 33071);
               GL11.glTexParameteri(3553, 10243, 33071);
               RenderCapabilities.invoke(32856, i, j, 6408, 5121);
               screenTransitionControllerState3.intValue = GL30.glGenFramebuffers();
               GL30.glBindFramebuffer(36160, screenTransitionControllerState3.intValue);
               GL30.glFramebufferTexture2D(36160, 36064, 3553, screenTransitionControllerState3.intValue2, 0);
               GL11.glDrawBuffer(36064);
               if (GL30.glCheckFramebufferStatus(36160) == 36053) {
                  break label63;
               }

               this.invoke12(screenTransitionControllerState3);
               flag7 = false;
            } finally {
               FramebufferUtils.restoreGlState(glStateSnapshot3);
            }

            return flag7;
         }

         screenTransitionControllerState3.intValue3 = i;
         screenTransitionControllerState3.intValue4 = j;
         return true;
      } else {
         return false;
      }
   }

   private void invoke10() {
      if (this.intValue15 == 0) {
         FramebufferUtils.GlStateSnapshot glStateSnapshot4 = FramebufferUtils.captureGlState();

         try {
            this.intValue15 = GL30.glGenVertexArrays();
            this.intValue16 = GL15.glGenBuffers();
            GL30.glBindVertexArray(this.intValue15);
            GL15.glBindBuffer(34962, this.intValue16);
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
            "assets/wild/shaders/postfx/theme_shockwave_transition.vert", "assets/wild/shaders/postfx/theme_shockwave_transition.frag"
         );
         this.intValue = this.glShaderProgram.compute2("u_textureOld");
         this.intValue2 = this.glShaderProgram.compute2("u_textureNew");
         this.intValue3 = this.glShaderProgram.compute2("u_resolution");
         this.intValue4 = this.glShaderProgram.compute2("u_progress");
         this.intValue5 = this.glShaderProgram.compute2("u_linearProgress");
         this.intValue6 = this.glShaderProgram.compute2("u_time");
         this.intValue7 = this.glShaderProgram.compute2("u_center");
         this.intValue8 = this.glShaderProgram.compute2("u_aspect");
         this.intValue9 = this.glShaderProgram.compute2("u_radius");
         this.intValue10 = this.glShaderProgram.compute2("u_maxRadius");
         this.intValue11 = this.glShaderProgram.compute2("u_accentTop");
         this.intValue12 = this.glShaderProgram.compute2("u_accentBottom");
      }
   }

   private boolean check7(ScreenTransitionController.ScreenTransitionControllerState screenTransitionControllerState4, int i, int j) {
      return screenTransitionControllerState4 != null && screenTransitionControllerState4.intValue != 0 && screenTransitionControllerState4.intValue2 != 0 && screenTransitionControllerState4.intValue3 == i && screenTransitionControllerState4.intValue4 == j;
   }

   private void invoke11() {
      for (ScreenTransitionController.ScreenTransitionControllerUiState screenTransitionControllerUiState11 : this.items) {
         this.invoke12(screenTransitionControllerUiState11.screenTransitionControllerState);
      }

      this.items.clear();
      this.invoke12(this.screenTransitionControllerState);
      this.invoke12(this.screenTransitionControllerState2);
      this.invoke12(this.screenTransitionControllerState3);
   }

   private void invoke12(ScreenTransitionController.ScreenTransitionControllerState screenTransitionControllerState5) {
      if (screenTransitionControllerState5 != null) {
         if (screenTransitionControllerState5.intValue != 0) {
            GL30.glDeleteFramebuffers(screenTransitionControllerState5.intValue);
            screenTransitionControllerState5.intValue = 0;
         }

         if (screenTransitionControllerState5.intValue2 != 0) {
            GL11.glDeleteTextures(screenTransitionControllerState5.intValue2);
            screenTransitionControllerState5.intValue2 = 0;
         }

         screenTransitionControllerState5.intValue3 = 0;
         screenTransitionControllerState5.intValue4 = 0;
      }
   }

   private static boolean check8(MinecraftClient minecraftClient) {
      if (minecraftClient != null && minecraftClient.getWindow() != null) {
         Window window4 = minecraftClient.getWindow();
         return !window4.hasZeroWidthOrHeight() && window4.getFramebufferWidth() > 0 && window4.getFramebufferHeight() > 0;
      } else {
         return false;
      }
   }

   private static float measure2(float f) {
      return Float.isFinite(f) && !(f <= 0.0F) ? measure5(f, 0.0F, 6.0F) * 0.05F : 0.0F;
   }

   private static float measure3(float f) {
      float floatValue13 = measure5(f, 0.0F, 1.0F);
      float floatValue14 = floatValue13 * floatValue13 * floatValue13 * (floatValue13 * (floatValue13 * 6.0F - 15.0F) + 10.0F);
      float floatValue15 = 1.0F - (float)Math.exp(-3.15F * floatValue13);
      return measure5(floatValue14 * 0.58F + floatValue15 * 0.42F, 0.0F, 1.0F);
   }

   private static float measure4(float f, float g, float h, float i, float j) {
      float floatValue16 = (h - f) * j;
      float floatValue17 = i - g;
      return (float)Math.sqrt(floatValue16 * floatValue16 + floatValue17 * floatValue17);
   }

   private static float measure5(float f, float g, float h) {
      return Math.max(g, Math.min(h, f));
   }

   static final class ScreenTransitionControllerUiState {
      final ScreenTransitionController.ScreenTransitionControllerState screenTransitionControllerState = new ScreenTransitionController.ScreenTransitionControllerState();
      float floatValue;
      float floatValue2;
      float floatValue3;
      float floatValue4;
      float floatValue5;
      int intValue = -4205825;
      int intValue2 = -8547073;
   }

   static final class ScreenTransitionControllerState {
      int intValue;
      int intValue2;
      int intValue3;
      int intValue4;
   }
}
