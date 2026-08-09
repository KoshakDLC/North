package ru.metaculture.protection;

import java.awt.Color;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL20;

public final class MenuBackdropRenderer implements AutoCloseable {
   private static final MenuBackdropRenderer INSTANCE = new MenuBackdropRenderer();
   private static final int INT_VALUE = 14;
   private static final String ASSETS_WILD_SHADERS_MAINMENU_MENU_QUAD_VERT = "assets/wild/shaders/mainmenu/menu_quad.vert";
   private static final ThemePalette THEME_PALETTE = ThemePalette.resolve2();
   private static final String[] TEXT = resolve();
   private final FullscreenQuad fullscreenQuad = new FullscreenQuad();
   private final OffscreenFramebuffer offscreenFramebuffer = new OffscreenFramebuffer();
   private ShaderProgram shaderProgram;
   private FullscreenQuad.FullscreenQuadResources fullscreenQuadResources;
   private FullscreenQuad.FullscreenQuadResources fullscreenQuadResources2;
   private FullscreenQuad.FullscreenQuadResources fullscreenQuadResources3;
   private FullscreenQuad.FullscreenQuadResources fullscreenQuadResources4;
   private long timestamp;
   private long timestamp2;
   private long timestamp3;
   private float floatValue;
   private float floatValue2;
   private float floatValue3;
   private float floatValue4;
   private float floatValue5;
   private float floatValue6;
   private boolean flag;
   private int intValue = -6357021;
   private int intValue2 = -11341636;
   private Theme theme = Theme.AURORA;
   private boolean flag2;
   private boolean flag3;
   private boolean flag4;
   private float floatValue7;
   private float floatValue8 = 1.0F;
   private boolean flag5;
   private long timestamp4;

   public static MenuBackdropRenderer getINSTANCE() {
      return INSTANCE;
   }

   public void invoke(float f, float g) {
      this.floatValue7 = measure7(f, 0.0F, 1.0F);
      this.floatValue8 = measure7(g, 0.0F, 1.0F);
   }

   public boolean check() {
      if (this.flag4 && this.check4()) {
         this.flag4 = false;
      }

      return this.flag4;
   }

   public boolean check2(MinecraftClient minecraftClient, int i, int j) {
      return this.check3(minecraftClient, i, j, this.floatValue7, this.floatValue8);
   }

   public void invoke2() {
      this.flag5 = true;
      this.timestamp4 = System.nanoTime() + 650000000L;
   }

   public void invoke3() {
      if (this.flag5 && System.nanoTime() >= this.timestamp4) {
         this.close();
      }
   }

   public boolean check3(MinecraftClient minecraftClient, int i, int j, float f, float g) {
      if (this.flag4) {
         if (!this.check4()) {
            return false;
         }

         this.flag4 = false;
      }

      if (minecraftClient != null && minecraftClient.getWindow() != null) {
         Window window2 = minecraftClient.getWindow();
         if (!window2.hasZeroWidthOrHeight() && window2.getFramebufferWidth() > 0 && window2.getFramebufferHeight() > 0) {
            try {
               long longValue = System.nanoTime();
               if (this.timestamp == 0L) {
                  this.timestamp = longValue;
                  this.timestamp2 = longValue;
               }

               float floatValue = Math.max(0.001F, Math.min(0.05F, (float)(longValue - this.timestamp2) / 1.0E9F));
               this.timestamp2 = longValue;
               float floatValue2 = (float)(longValue - this.timestamp) / 1.0E9F;
               f = measure7(f, 0.0F, 1.0F);
               g = measure7(g, 0.0F, 1.0F);
               this.flag5 = false;
               this.timestamp4 = 0L;
               this.invoke11();
               this.invoke12(window2, i, j, floatValue);
               FramebufferUtils.GlStateSnapshot glStateSnapshot = FramebufferUtils.captureGlState();
               boolean flag = false ;

               try {
                  flag = true;
                  this.invoke4(window2.getFramebufferWidth(), window2.getFramebufferHeight(), floatValue2, f, g);
                  this.invoke8(window2.getFramebufferWidth(), window2.getFramebufferHeight(), floatValue2, f, g);
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
               return true;
            } catch (Throwable exception) {
               this.flag4 = true;
               this.close();
               return false;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private boolean check4() {
      if (GLFW.glfwGetCurrentContext() == 0L) {
         return false;
      } else {
         MinecraftClient client = MinecraftClient.getInstance();
         if (client != null && client.getWindow() != null) {
            Window window3 = client.getWindow();
            return !window3.hasZeroWidthOrHeight() && window3.getFramebufferWidth() > 0 && window3.getFramebufferHeight() > 0;
         } else {
            return false;
         }
      }
   }

   private void invoke4(int i, int j, float f, float g, float h) {
      this.fullscreenQuad.invoke();
      this.invoke13();
      float floatValue3 = 0.92F;
      int intValue = Math.max(420, Math.round(i * floatValue3));
      int intValue2 = Math.max(240, Math.round(j * floatValue3));
      int intValue3 = this.offscreenFramebuffer.getIntValue3();
      int intValue4 = this.offscreenFramebuffer.getIntValue4();
      int intValue5 = FramebufferUtils.compute(GL11.glGetInteger(36006));
      this.offscreenFramebuffer.invoke(intValue, intValue2);
      boolean flag2 = intValue3 != this.offscreenFramebuffer.getIntValue3() || intValue4 != this.offscreenFramebuffer.getIntValue4();
      long longValue2 = System.nanoTime();
      if (flag2 || this.timestamp3 == 0L || longValue2 - this.timestamp3 >= 16666667L) {
         this.invoke5(i, j, f, g, h);
         this.timestamp3 = longValue2;
      }

      FramebufferUtils.check(36160, intValue5);
      GL11.glViewport(0, 0, Math.max(0, i), Math.max(0, j));
      GL11.glDisable(2929);
      GL11.glDisable(2884);
      GL11.glDisable(3089);
      GL11.glDisable(36281);
      GL11.glColorMask(true, true, true, true);
      this.invoke6(i, j, f, h);
      this.invoke7(i, j, f, g, h);
   }

   private void invoke5(int i, int j, float f, float g, float h) {
      if (this.offscreenFramebuffer.check()) {
         this.offscreenFramebuffer.invoke2();
         GL11.glDisable(3042);
         GL11.glDisable(2929);
         GL11.glDisable(2884);
         FullscreenQuad.FullscreenQuadResources fullscreenQuadResources = this.theme == Theme.SAKURA_BREEZE ? this.fullscreenQuadResources2 : this.fullscreenQuadResources;
         if (this.theme == Theme.SAKURA_BREEZE) {
            GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
            GL11.glClear(16384);
            GL11.glEnable(3042);
            GL14.glBlendFuncSeparate(770, 771, 1, 771);
         }

         fullscreenQuadResources.invoke();
         this.invoke14(
            fullscreenQuadResources,
            (float)this.offscreenFramebuffer.getIntValue3(),
            (float)this.offscreenFramebuffer.getIntValue4(),
            0.0F,
            0.0F,
            (float)this.offscreenFramebuffer.getIntValue3(),
            (float)this.offscreenFramebuffer.getIntValue4()
         );
         fullscreenQuadResources.invoke3("uTime", f);
         fullscreenQuadResources.invoke4("uResolution", this.offscreenFramebuffer.getIntValue3(), this.offscreenFramebuffer.getIntValue4());
         fullscreenQuadResources.invoke4("uMouse", this.measure(i), this.measure2(j));
         fullscreenQuadResources.invoke4("uMouseVelocity", this.floatValue5, this.floatValue6);
         fullscreenQuadResources.invoke5("uAccentTop", measure8(this.intValue), measure9(this.intValue), measure10(this.intValue));
         fullscreenQuadResources.invoke5("uAccentBottom", measure8(this.intValue2), measure9(this.intValue2), measure10(this.intValue2));
         fullscreenQuadResources.invoke3("uActivity", measure7(0.36F + g * 0.42F, 0.0F, 1.0F));
         fullscreenQuadResources.invoke3("uAlpha", h);
         fullscreenQuadResources.invoke3("uLightMode", this.flag2 ? 1.0F : 0.0F);
         this.invoke15(fullscreenQuadResources);
         this.shaderProgram.invoke();
      }
   }

   private void invoke6(int i, int j, float f, float g) {
      if (this.offscreenFramebuffer.check()) {
         GL11.glDisable(3042);
         this.fullscreenQuadResources3.invoke();
         this.invoke14(this.fullscreenQuadResources3, (float)i, (float)j, 0.0F, 0.0F, (float)i, (float)j);
         this.fullscreenQuadResources3.invoke2("uTexture", 0);
         this.fullscreenQuadResources3.invoke4("uTextureSize", this.offscreenFramebuffer.getIntValue3(), this.offscreenFramebuffer.getIntValue4());
         this.fullscreenQuadResources3.invoke4("uParallax", this.measure3(i) * 0.0012F, this.measure4(j) * 0.001F);
         this.fullscreenQuadResources3.invoke3("uTime", f);
         this.fullscreenQuadResources3.invoke3("uEntry", g);
         this.fullscreenQuadResources3.invoke3("uClickFlash", 0.0F);
         this.fullscreenQuadResources3.invoke3("uLightMode", this.flag2 ? 1.0F : 0.0F);
         this.fullscreenQuadResources3.invoke3("uSakura", this.theme == Theme.SAKURA_BREEZE ? 1.0F : 0.0F);
         GL13.glActiveTexture(33984);
         GL11.glBindTexture(3553, this.offscreenFramebuffer.getIntValue2());
         this.shaderProgram.invoke();
      }
   }

   private void invoke7(int i, int j, float f, float g, float h) {
      GL11.glEnable(3042);
      if (this.flag2) {
         GL14.glBlendFuncSeparate(770, 771, 1, 771);
      } else {
         GL14.glBlendFuncSeparate(770, 1, 1, 1);
      }

      this.fullscreenQuadResources4.invoke();
      this.invoke14(this.fullscreenQuadResources4, (float)i, (float)j, 0.0F, 0.0F, (float)i, (float)j);
      this.fullscreenQuadResources4.invoke3("uTime", f);
      this.fullscreenQuadResources4.invoke4("uResolution", i, j);
      this.fullscreenQuadResources4.invoke4("uMouse", this.measure(i), this.measure2(j));
      this.fullscreenQuadResources4.invoke4("uParallax", this.measure3(i), this.measure4(j));
      this.fullscreenQuadResources4.invoke5("uAccentTop", measure8(this.intValue), measure9(this.intValue), measure10(this.intValue));
      this.fullscreenQuadResources4
         .invoke5("uAccentBottom", measure8(this.intValue2), measure9(this.intValue2), measure10(this.intValue2));
      this.fullscreenQuadResources4.invoke3("uEntry", measure7(h * (0.55F + g * 0.45F), 0.0F, 1.0F));
      this.fullscreenQuadResources4.invoke3("uLightMode", this.flag2 ? 1.0F : 0.0F);
      this.invoke15(this.fullscreenQuadResources4);
      this.shaderProgram.invoke();
      GL14.glBlendFuncSeparate(770, 771, 1, 771);
   }

   private void invoke8(int i, int j, float f, float g, float h) {
      if (!(h <= 0.01F)) {
         try {
            WildClient.invoke15();
            RenderManager renderManager = WildClient.resolve();
            if (renderManager == null) {
               return;
            }

            renderManager.invoke(i, j);
            boolean flag3 = false;
            boolean flag4 = false ;

            try {
               flag4 = true;
               float floatValue4 = measure5(i, j);
               float floatValue5 = i * 0.5F;
               float floatValue6 = j * 0.5F - 58.0F * floatValue4;
               float floatValue7 = measure7(Math.min(i, j) * 0.112F, 82.0F * floatValue4, 132.0F * floatValue4);
               float floatValue8 = 0.5F + 0.5F * (float)Math.sin(f * 1.08F);
               this.invoke9(renderManager, floatValue5, floatValue6, floatValue7, floatValue8, h);
               this.invoke10(renderManager, i, j, g, h, floatValue4, f);
               renderManager.invoke19();
               flag3 = true;
               flag4 = false;
            } finally {
               if (flag4) {
                  if (!flag3) {
                     renderManager.invoke2();
                  }
               }
            }

            if (!flag3) {
               renderManager.invoke2();
            }
         } catch (Throwable exception2) {
         }
      }
   }

   private void invoke9(RenderManager renderManager2, float f, float g, float h, float i, float j) {
      float floatValue9 = h * 0.98F;
      float floatValue10 = floatValue9 * (1.08F + i * 0.035F);
      float floatValue11 = RenderManager.resolve7(BrandMark.font(), BrandMark.GLYPH, floatValue9).floatValue;
      float floatValue12 = RenderManager.resolve7(BrandMark.font(), BrandMark.GLYPH, floatValue10).floatValue;
      float floatValue13 = g + h * 0.148F;
      int intValue6 = this.flag2
         ? (this.theme == Theme.VERNAL_SOLSTICE ? compute(0.0196F, 0.0667F, 0.0196F, j) : compute(0.1F, 0.1F, 0.1F, j))
         : compute(1.0F, 1.0F, 1.0F, j);
      renderManager2.invoke69(
         BrandMark.font(), f - floatValue12 * 0.5F, floatValue13 + h * 0.002F, floatValue10, BrandMark.GLYPH, compute3(this.intValue2, this.intValue, i, 0.24F * j)
      );
      renderManager2.invoke69(BrandMark.font(), f - floatValue11 * 0.5F, floatValue13, floatValue9, BrandMark.GLYPH, intValue6);
   }

   private void invoke10(RenderManager renderManager3, int i, int j, float f, float g, float h, float k) {
      float floatValue14 = measure7(i * 0.26F, 292.0F * h, 520.0F * h);
      float floatValue15 = Math.max(8.0F * h, 8.0F);
      float floatValue16 = i * 0.5F - floatValue14 * 0.5F;
      float floatValue17 = j * 0.5F + 92.0F * h;
      float floatValue18 = floatValue15 * 0.5F;
      float floatValue19 = Math.max(floatValue15, floatValue14 * measure7(f, 0.0F, 1.0F));
      int intValue7 = this.flag2 ? compute(0.18F, 0.2F, 0.22F, 0.16F * g) : compute(1.0F, 1.0F, 1.0F, 0.105F * g);
      int intValue8 = this.flag2 ? compute(0.1F, 0.11F, 0.12F, 0.16F * g) : compute(1.0F, 1.0F, 1.0F, 0.15F * g);
      renderManager3.invoke41(floatValue16, floatValue17, floatValue14, floatValue15, floatValue18, 18.0F * h, 0.9F, compute2(this.intValue2, Math.round(70.0F * g)));
      renderManager3.invoke5(floatValue16, floatValue17, floatValue14, floatValue15, floatValue18, intValue7);
      renderManager3.invoke5(
         floatValue16, floatValue17, floatValue19, floatValue15, floatValue18, compute3(this.intValue2, this.intValue, 0.5F + 0.5F * (float)Math.sin(k * 1.15F), 0.86F * g)
      );
      float floatValue20 = Math.max(46.0F * h, floatValue14 * 0.18F);
      float floatValue21 = floatValue16 + (floatValue14 + floatValue20) * measure7(f, 0.0F, 1.0F) - floatValue20;
      float floatValue22 = Math.max(floatValue16, floatValue21);
      float floatValue23 = Math.min(floatValue16 + floatValue19, floatValue21 + floatValue20) - floatValue22;
      if (floatValue23 > 0.5F) {
         renderManager3.invoke5(floatValue22, floatValue17 + floatValue15 * 0.16F, floatValue23, floatValue15 * 0.25F, floatValue15 * 0.125F, compute(1.0F, 1.0F, 1.0F, 0.2F * g));
      }

      renderManager3.invoke5(floatValue16, floatValue17, floatValue14, 1.0F * h, floatValue18, intValue8);
      String text = Math.round(measure7(f, 0.0F, 1.0F) * 100.0F) + "%";
      float floatValue24 = 25.0F * h;
      renderManager3.invoke70(
         FontRegistry.fontObject,
         i * 0.5F,
         floatValue17 + 30.0F * h,
         floatValue24,
         text,
         this.flag2 ? compute(0.12F, 0.13F, 0.14F, 0.52F * g) : compute(0.88F, 0.92F, 0.96F, 0.54F * g),
         "c"
      );
   }

   private void invoke11() {
      Theme theme = WildClient.INSTANCE != null && WildClient.INSTANCE.themeManager != null ? WildClient.INSTANCE.themeManager.getTheme() : Theme.AURORA;
      this.theme = theme;
      ThemePalette.Swatch swatch = THEME_PALETTE.resolve3(theme);
      if (swatch != null) {
         this.intValue = swatch.getIntValue();
         this.intValue2 = swatch.getIntValue2();
         this.flag2 = false;
      } else {
         this.flag2 = false;
         Color color = theme.getColor();
         this.intValue = 0xFF000000 | color.getRGB() & 16777215;
         float[] floatValues = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
         this.intValue2 = 0xFF000000
            | Color.HSBtoRGB((floatValues[0] + 0.075F) % 1.0F, Math.min(1.0F, floatValues[1] * 1.08F), Math.min(1.0F, floatValues[2] * 1.18F)) & 16777215;
      }
   }

   private void invoke12(Window window, int i, int j, float f) {
      float floatValue25 = (float)(i * window.getFramebufferWidth() / Math.max(1.0, (double)window.getScaledWidth()));
      float floatValue26 = (float)(j * window.getFramebufferHeight() / Math.max(1.0, (double)window.getScaledHeight()));
      if (!this.flag) {
         this.floatValue = this.floatValue3 = floatValue25;
         this.floatValue2 = this.floatValue4 = floatValue26;
         this.floatValue5 = 0.0F;
         this.floatValue6 = 0.0F;
         this.flag = true;
      } else {
         this.floatValue = floatValue25;
         this.floatValue2 = floatValue26;
         float floatValue27 = this.floatValue3;
         float floatValue28 = this.floatValue4;
         float floatValue29 = measure6(this.floatValue - this.floatValue3, this.floatValue2 - this.floatValue4);
         float floatValue30 = (1.0F - (float)Math.pow(3.5E-5F, f)) * (0.72F + measure7(floatValue29 / 520.0F, 0.0F, 0.42F));
         this.floatValue3 = this.floatValue3 + (this.floatValue - this.floatValue3) * measure7(floatValue30, 0.05F, 0.26F);
         this.floatValue4 = this.floatValue4 + (this.floatValue2 - this.floatValue4) * measure7(floatValue30, 0.05F, 0.26F);
         float floatValue31 = measure7((this.floatValue3 - floatValue27) / Math.max(1.0F, (float)window.getFramebufferWidth()) / f, -1.8F, 1.8F);
         float floatValue32 = measure7((this.floatValue4 - floatValue28) / Math.max(1.0F, (float)window.getFramebufferHeight()) / f, -1.8F, 1.8F);
         float floatValue33 = 1.0F - (float)Math.pow(0.0025F, f);
         this.floatValue5 = this.floatValue5 + (floatValue31 - this.floatValue5) * floatValue33;
         this.floatValue6 = this.floatValue6 + (floatValue32 - this.floatValue6) * floatValue33;
      }
   }

   private float measure(int i) {
      return this.floatValue3 / Math.max(1.0F, (float)i);
   }

   private float measure2(int i) {
      return this.floatValue4 / Math.max(1.0F, (float)i);
   }

   private float measure3(int i) {
      return (this.measure(i) - 0.5F) * 10.0F;
   }

   private float measure4(int i) {
      return (this.measure2(i) - 0.5F) * 8.0F;
   }

   private void invoke13() {
      if (!this.flag3) {
         this.shaderProgram = new ShaderProgram();
         this.fullscreenQuadResources = this.fullscreenQuad
            .resolve("loading_liquid_neon_gas", "assets/wild/shaders/mainmenu/menu_quad.vert", "assets/wild/shaders/mainmenu/menu_aurora.frag");
         this.fullscreenQuadResources2 = this.fullscreenQuad
            .resolve("loading_sakura_breeze", "assets/wild/shaders/mainmenu/menu_quad.vert", "assets/wild/shaders/mainmenu/sakura_breeze.frag");
         this.fullscreenQuadResources3 = this.fullscreenQuad
            .resolve("loading_composite", "assets/wild/shaders/mainmenu/menu_quad.vert", "assets/wild/shaders/mainmenu/menu_composite.frag");
         this.fullscreenQuadResources4 = this.fullscreenQuad
            .resolve("loading_particles", "assets/wild/shaders/mainmenu/menu_quad.vert", "assets/wild/shaders/mainmenu/menu_particles.frag");
         this.flag3 = true;
      }
   }

   private void invoke14(FullscreenQuad.FullscreenQuadResources fullscreenQuadResources2, float f, float g, float h, float i, float j, float k) {
      fullscreenQuadResources2.invoke4("uViewport", f, g);
      fullscreenQuadResources2.invoke6("uRect", h, i, j, k);
   }

   private void invoke15(FullscreenQuad.FullscreenQuadResources fullscreenQuadResources3) {
      for (int intValue9 = 0; intValue9 < 14; intValue9++) {
         fullscreenQuadResources3.invoke6(TEXT[intValue9], 0.0F, 0.0F, 100.0F, 0.0F);
      }
   }

   private static String[] resolve() {
      String[] texts = new String[14];

      for (int intValue10 = 0; intValue10 < texts.length; intValue10++) {
         texts[intValue10] = "uTrail[" + intValue10 + "]";
      }

      return texts;
   }

   @Override
   public void close() {
      this.offscreenFramebuffer.close();
      if (this.shaderProgram != null) {
         this.shaderProgram.close();
         this.shaderProgram = null;
      }

      this.fullscreenQuad.close();
      this.flag3 = false;
      this.floatValue7 = 0.0F;
      this.floatValue8 = 1.0F;
      this.flag5 = false;
      this.timestamp4 = 0L;
      this.timestamp = 0L;
      this.timestamp2 = 0L;
      this.timestamp3 = 0L;
      this.floatValue = 0.0F;
      this.floatValue2 = 0.0F;
      this.floatValue3 = 0.0F;
      this.floatValue4 = 0.0F;
      this.floatValue5 = 0.0F;
      this.floatValue6 = 0.0F;
      this.flag = false;
   }

   private static float measure5(float f, float g) {
      return measure7(Math.min(f / 1920.0F, g / 1080.0F) * 1.16F, 0.72F, 1.38F);
   }

   private static float measure6(float f, float g) {
      return (float)Math.sqrt(f * f + g * g);
   }

   private static float measure7(float f, float g, float h) {
      return Math.max(g, Math.min(h, f));
   }

   private static float measure8(int i) {
      return (i >> 16 & 0xFF) / 255.0F;
   }

   private static float measure9(int i) {
      return (i >> 8 & 0xFF) / 255.0F;
   }

   private static float measure10(int i) {
      return (i & 0xFF) / 255.0F;
   }

   private static int compute(float f, float g, float h, float i) {
      int intValue11 = Math.round(measure7(f, 0.0F, 1.0F) * 255.0F);
      int intValue12 = Math.round(measure7(g, 0.0F, 1.0F) * 255.0F);
      int intValue13 = Math.round(measure7(h, 0.0F, 1.0F) * 255.0F);
      int intValue14 = Math.round(measure7(i, 0.0F, 1.0F) * 255.0F);
      return intValue14 << 24 | intValue11 << 16 | intValue12 << 8 | intValue13;
   }

   private static int compute2(int i, int j) {
      int intValue15 = Math.max(0, Math.min(255, j));
      return i & 16777215 | intValue15 << 24;
   }

   private static int compute3(int i, int j, float f, float g) {
      float floatValue34 = measure7(f, 0.0F, 1.0F);
      int intValue16 = ColorUtils.compute16(i, j, floatValue34);
      int intValue17 = Math.round(measure7(g, 0.0F, 1.0F) * 255.0F);
      return intValue17 << 24 | intValue16;
   }
}
