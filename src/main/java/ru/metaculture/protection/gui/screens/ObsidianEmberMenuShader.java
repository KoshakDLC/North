package ru.metaculture.protection;

import com.mojang.blaze3d.opengl.GlStateManager;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL20;

public final class ObsidianEmberMenuShader implements AutoCloseable {
   private static final ObsidianEmberMenuShader INSTANCE = new ObsidianEmberMenuShader();
   private static final String ASSETS_WILD_SHADERS_MAINMENU_MENU_QUAD_VERT = "assets/wild/shaders/mainmenu/menu_quad.vert";
   private static final String ASSETS_WILD_SHADERS_MAINMENU_OBSIDIAN_EMBER_FRAG = "assets/wild/shaders/mainmenu/obsidian_ember.frag";
   private final FullscreenQuad fullscreenQuad = new FullscreenQuad();
   private ShaderProgram shaderProgram;
   private FullscreenQuad.FullscreenQuadResources fullscreenQuadResources;
   private long timestamp = System.nanoTime();
   private long timestamp2;
   private float floatValue;
   private float floatValue2;
   private float floatValue3;
   private float floatValue4;

   public static ObsidianEmberMenuShader getINSTANCE() {
      return INSTANCE;
   }

   public void invoke(int i, int j, float f, float g, ColorScheme colorScheme, float h) {
      if (i > 0 && j > 0) {
         this.invoke3();
         FramebufferUtils.GlStateSnapshot glStateSnapshot = FramebufferUtils.captureGlState();
         boolean flag = false ;

         try {
            flag = true;
            GL11.glViewport(0, 0, Math.max(0, i), Math.max(0, j));
            GL11.glDisable(2929);
            GL11.glDisable(2884);
            GL11.glDisable(3089);
            GL11.glDisable(36281);
            GlStateManager._enableBlend();
            GlStateManager._blendFuncSeparate(770, 771, 1, 771);
            GL11.glEnable(3042);
            GL14.glBlendFuncSeparate(770, 771, 1, 771);
            this.fullscreenQuadResources.invoke();
            this.fullscreenQuadResources.invoke4("uViewport", i, j);
            this.fullscreenQuadResources.invoke6("uRect", 0.0F, 0.0F, i, j);
            long longValue = System.nanoTime();
            float floatValue = (float)(longValue - this.timestamp) / 1.0E9F;
            float floatValue2 = this.measure2(f / Math.max(1.0F, (float)i));
            float floatValue3 = this.measure2(g / Math.max(1.0F, (float)j));
            this.invoke2(floatValue2, floatValue3, longValue);
            this.fullscreenQuadResources.invoke3("uTime", floatValue);
            this.fullscreenQuadResources.invoke4("uResolution", i, j);
            this.fullscreenQuadResources.invoke4("uMouse", floatValue2, floatValue3);
            this.fullscreenQuadResources.invoke4("uMouseVelocity", this.floatValue3, this.floatValue4);
            int intValue = colorScheme == null ? -20119 : colorScheme.getIntValue14();
            int intValue2 = colorScheme == null ? -42198 : colorScheme.getIntValue15();
            this.fullscreenQuadResources.invoke5("uAccentTop", this.measure(intValue, 16), this.measure(intValue, 8), this.measure(intValue, 0));
            this.fullscreenQuadResources.invoke5("uAccentBottom", this.measure(intValue2, 16), this.measure(intValue2, 8), this.measure(intValue2, 0));
            this.fullscreenQuadResources.invoke3("uAlpha", this.measure2(h) * 0.9F);
            this.fullscreenQuadResources.invoke3("uLightMode", colorScheme != null && colorScheme.isFlag() ? 1.0F : 0.0F);
            this.shaderProgram.invoke();
            flag = false;
         } finally {
            if (flag) {
               GL13.glActiveTexture(33984);
               GL11.glBindTexture(3553, 0);
               GL20.glUseProgram(0);
               FramebufferUtils.restoreGlState(glStateSnapshot);
               GlStateManager._enableBlend();
               GlStateManager._blendFuncSeparate(770, 771, 1, 771);
            }
         }

         GL13.glActiveTexture(33984);
         GL11.glBindTexture(3553, 0);
         GL20.glUseProgram(0);
         FramebufferUtils.restoreGlState(glStateSnapshot);
         GlStateManager._enableBlend();
         GlStateManager._blendFuncSeparate(770, 771, 1, 771);
      }
   }

   private void invoke2(float f, float g, long l) {
      if (this.timestamp2 == 0L) {
         this.timestamp2 = l;
         this.floatValue = f;
         this.floatValue2 = g;
         this.floatValue3 = 0.0F;
         this.floatValue4 = 0.0F;
      } else {
         float floatValue4 = (float)(l - this.timestamp2) / 1.0E9F;
         this.timestamp2 = l;
         if (Float.isFinite(floatValue4) && !(floatValue4 <= 0.0F)) {
            floatValue4 = Math.min(floatValue4, 0.08F);
            float floatValue5 = this.measure3((f - this.floatValue) / floatValue4, 4.0F);
            float floatValue6 = this.measure3((g - this.floatValue2) / floatValue4, 4.0F);
            this.floatValue = f;
            this.floatValue2 = g;
            float floatValue7 = 1.0F - (float)Math.exp(-floatValue4 * 16.0F);
            this.floatValue3 = this.floatValue3 + (floatValue5 - this.floatValue3) * floatValue7;
            this.floatValue4 = this.floatValue4 + (floatValue6 - this.floatValue4) * floatValue7;
         }
      }
   }

   private void invoke3() {
      if (this.shaderProgram == null) {
         this.shaderProgram = new ShaderProgram();
      }

      if (this.fullscreenQuadResources == null) {
         this.fullscreenQuadResources = this.fullscreenQuad
            .resolve("obsidian_ember_click_gui", "assets/wild/shaders/mainmenu/menu_quad.vert", "assets/wild/shaders/mainmenu/obsidian_ember.frag");
      }
   }

   private float measure(int i, int j) {
      return (i >> j & 0xFF) / 255.0F;
   }

   private float measure2(float f) {
      return Math.max(0.0F, Math.min(1.0F, f));
   }

   private float measure3(float f, float g) {
      return Math.max(-g, Math.min(g, f));
   }

   @Override
   public void close() {
      if (this.shaderProgram != null) {
         this.shaderProgram.close();
         this.shaderProgram = null;
      }

      this.fullscreenQuad.close();
      this.fullscreenQuadResources = null;
      this.timestamp = System.nanoTime();
      this.timestamp2 = 0L;
      this.floatValue = 0.0F;
      this.floatValue2 = 0.0F;
      this.floatValue3 = 0.0F;
      this.floatValue4 = 0.0F;
   }
}
