package ru.metaculture.protection;

import net.minecraft.client.MinecraftClient;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

final class HudBlurRenderer {
   private static final HudBlurRenderer INSTANCE = new HudBlurRenderer();
   private static final String ASSETS_WILD_SHADERS_BLUR_BLUR_FULLSCREEN_VERT = "assets/wild/shaders/blur/blur_fullscreen.vert";
   private static final String ASSETS_WILD_SHADERS_HUD_HUD_FERROFLUID_SURFACE_FRAG = "assets/wild/shaders/hud/hud_ferrofluid_surface.frag";
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
   private boolean flag;
   private boolean flag2;

   private HudBlurRenderer() {
   }

   static boolean check(
      RenderManager renderManager,
      float f,
      float g,
      float h,
      float i,
      float j,
      float k,
      boolean bl,
      int l,
      int m,
      int n,
      int o,
      boolean bl2,
      boolean bl3,
      float p
   ) {
      return INSTANCE.check2(renderManager, f, g, h, i, j, k, bl, l, m, n, o, bl2, bl3, p);
   }

   private boolean check2(
      RenderManager renderManager2,
      float f,
      float g,
      float h,
      float i,
      float j,
      float k,
      boolean bl,
      int l,
      int m,
      int n,
      int o,
      boolean bl2,
      boolean bl3,
      float p
   ) {
      MinecraftClient client = MinecraftClient.getInstance();
      if (!this.flag2 && renderManager2 != null && client != null && client.getWindow() != null && !(h <= 1.0F) && !(i <= 1.0F) && !(k <= 0.001F)) {
         int intValue = client.getWindow().getFramebufferWidth();
         int intValue2 = client.getWindow().getFramebufferHeight();
         if (intValue > 1 && intValue2 > 1 && this.check3()) {
            renderManager2.invoke20();
            float floatValue = Math.max(18.0F, j * 2.8F);
            float floatValue2 = HudEditorRenderer.getINSTANCE().getFloatValue();
            float floatValue3 = HudEditorRenderer.getINSTANCE().getFloatValue2();
            FramebufferUtils.GlStateSnapshot glStateSnapshot = FramebufferUtils.captureGlState();

            boolean flag;
            try {
               GL11.glViewport(0, 0, Math.max(0, intValue), Math.max(0, intValue2));
               GL11.glDisable(2929);
               GL11.glDisable(2884);
               GL11.glDisable(3089);
               GL11.glDepthMask(false);
               GL11.glEnable(3042);
               GL14.glBlendFuncSeparate(770, 771, 1, 771);
               GL11.glDisable(36281);
               this.glShaderProgram.invoke();
               if (this.intValue3 >= 0) {
                  GL20.glUniform2f(this.intValue3, intValue, intValue2);
               }

               if (this.intValue4 >= 0) {
                  GL20.glUniform1f(this.intValue4, (float)(System.nanoTime() % 720000000000L) / 1.0E9F);
               }

               if (this.intValue5 >= 0) {
                  GL20.glUniform4f(this.intValue5, f - floatValue, g - floatValue, h + floatValue * 2.0F, i + floatValue * 2.0F);
               }

               if (this.intValue6 >= 0) {
                  GL20.glUniform4f(this.intValue6, f, g, h, i);
               }

               if (this.intValue7 >= 0) {
                  GL20.glUniform1f(this.intValue7, Math.max(0.0F, j));
               }

               if (this.intValue8 >= 0) {
                  GL20.glUniform1f(this.intValue8, measure5(k));
               }

               if (this.intValue9 >= 0) {
                  GL20.glUniform1f(this.intValue9, bl ? 1.0F : 0.0F);
               }

               if (this.intValue10 >= 0) {
                  invoke2(this.intValue10, l);
               }

               if (this.intValue11 >= 0) {
                  invoke2(this.intValue11, m);
               }

               if (this.intValue12 >= 0) {
                  invoke(this.intValue12, n);
               }

               if (this.intValue13 >= 0) {
                  invoke(this.intValue13, o);
               }

               if (this.intValue14 >= 0) {
                  GL20.glUniform2f(this.intValue14, floatValue2, floatValue3);
               }

               if (this.intValue15 >= 0) {
                  GL20.glUniform1f(this.intValue15, bl2 ? 1.0F : 0.0F);
               }

               if (this.intValue16 >= 0) {
                  GL20.glUniform1f(this.intValue16, bl3 ? 1.0F : 0.0F);
               }

               if (this.intValue17 >= 0) {
                  GL20.glUniform1f(this.intValue17, measure5(p));
               }

               GL30.glBindVertexArray(this.intValue);
               GL11.glDrawArrays(4, 0, 6);
               GL30.glBindVertexArray(0);
               return true;
            } catch (Throwable exception) {
               this.flag2 = true;
               flag = false;
            } finally {
               GL20.glUseProgram(0);
               FramebufferUtils.restoreGlState(glStateSnapshot);
            }

            return flag;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private boolean check3() {
      if (!this.flag) {
         this.flag = true;

         try {
            this.glShaderProgram = GlShaderProgram.resolve("assets/wild/shaders/blur/blur_fullscreen.vert", "assets/wild/shaders/hud/hud_ferrofluid_surface.frag");
            this.intValue3 = this.glShaderProgram.compute2("uResolution");
            this.intValue4 = this.glShaderProgram.compute2("uTime");
            this.intValue5 = this.glShaderProgram.compute2("uDrawRect");
            this.intValue6 = this.glShaderProgram.compute2("uElementRect");
            this.intValue7 = this.glShaderProgram.compute2("uRadius");
            this.intValue8 = this.glShaderProgram.compute2("uAlpha");
            this.intValue9 = this.glShaderProgram.compute2("uInset");
            this.intValue10 = this.glShaderProgram.compute2("uSurfaceColor");
            this.intValue11 = this.glShaderProgram.compute2("uOutlineColor");
            this.intValue12 = this.glShaderProgram.compute2("uAccentTop");
            this.intValue13 = this.glShaderProgram.compute2("uAccentBottom");
            this.intValue14 = this.glShaderProgram.compute2("uMouse");
            this.intValue15 = this.glShaderProgram.compute2("uShadow");
            this.intValue16 = this.glShaderProgram.compute2("uOutline");
            this.intValue17 = this.glShaderProgram.compute2("uLightMode");
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

   private static float measure5(float f) {
      return Math.max(0.0F, Math.min(1.0F, f));
   }
}
