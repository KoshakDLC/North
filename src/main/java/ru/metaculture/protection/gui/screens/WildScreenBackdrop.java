package ru.metaculture.protection;

import com.mojang.blaze3d.opengl.GlStateManager;
import java.awt.Color;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.Window;
import net.minecraft.server.WorldGenerationProgressTracker;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public final class WildScreenBackdrop implements AutoCloseable {
   private static final WildScreenBackdrop INSTANCE = new WildScreenBackdrop();
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
   private FullscreenQuad.FullscreenQuadResources fullscreenQuadResources5;
   private FullscreenQuad.FullscreenQuadResources fullscreenQuadResources6;
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
   private boolean flag2;
   private int intValue;
   private static final int INT_VALUE = 4;
   private int intValue2 = -6357021;
   private int intValue3 = -11341636;
   private Theme theme = Theme.AURORA;
   private boolean flag3;

   public static WildScreenBackdrop getINSTANCE() {
      return INSTANCE;
   }

   public boolean check(MinecraftClient minecraftClient, int i, int j, float f) {
      return this.check2(minecraftClient, i, j, f, null);
   }

   public boolean check2(MinecraftClient minecraftClient, int i, int j, float f, Screen screen) {
      MainMenuRenderer.MainMenuRendererBounds mainMenuRendererBounds = MainMenuRenderer.resolve(minecraftClient, 1, 1);
      if (mainMenuRendererBounds == null || mainMenuRendererBounds.width() <= 0 || mainMenuRendererBounds.height() <= 0) {
         ScreenRenderDiagnostics.invoke4(screen, "WildScreenBackdrop", false, "invalid frame metrics");
         return false;
      } else if (GLFW.glfwGetCurrentContext() == 0L) {
         ScreenRenderDiagnostics.invoke4(screen, "WildScreenBackdrop", false, "no gl context");
         return false;
      } else {
         Window window2 = minecraftClient.getWindow();
         if (window2 == null) {
            ScreenRenderDiagnostics.invoke4(screen, "WildScreenBackdrop", false, "window missing");
            return false;
         } else {
            long longValue = System.nanoTime();
            if (this.timestamp == 0L) {
               this.timestamp = longValue;
               this.timestamp2 = longValue;
            }

            float floatValue = Math.max(0.001F, Math.min(0.05F, (float)(longValue - this.timestamp2) / 1.0E9F));
            this.timestamp2 = longValue;
            float floatValue2 = (float)(longValue - this.timestamp) / 1.0E9F;
            this.invoke9();
            this.invoke10(window2, i, j, floatValue);
            FramebufferUtils.GlStateSnapshot glStateSnapshot = FramebufferUtils.captureGlState();
            boolean flag = false ;

            boolean flag2;
            label121: {
               boolean flag3;
               label120: {
                  label119: {
                     int intValue;
                     try {
                        flag = true;
                        this.fullscreenQuad.invoke();
                        this.invoke7();
                        int intValue2 = mainMenuRendererBounds.width();
                        intValue = mainMenuRendererBounds.height();
                        int intValue3 = FramebufferUtils.compute(GL11.glGetInteger(36006));
                        int intValue4 = Math.max(420, Math.round(intValue2 * 0.88F));
                        int intValue5 = Math.max(240, Math.round(intValue * 0.88F));
                        int intValue6 = this.offscreenFramebuffer.getIntValue3();
                        int intValue7 = this.offscreenFramebuffer.getIntValue4();
                        this.offscreenFramebuffer.invoke(intValue4, intValue5);
                        if (!this.offscreenFramebuffer.check()) {
                           ScreenRenderDiagnostics.invoke4(screen, "WildScreenBackdrop", false, "gas target not ready");
                           flag2 = false;
                           flag = false;
                           break label121;
                        }

                        flag2 = intValue6 != this.offscreenFramebuffer.getIntValue3() || intValue7 != this.offscreenFramebuffer.getIntValue4();
                        if (flag2 || this.timestamp3 == 0L || longValue - this.timestamp3 >= 25000000L) {
                           this.invoke4(floatValue2);
                           this.timestamp3 = longValue;
                        }

                        FramebufferUtils.check(36160, intValue3);
                        int intValue8 = GL30.glCheckFramebufferStatus(36009);
                        if (intValue8 != 36053) {
                           ScreenRenderDiagnostics.invoke4(screen, "WildScreenBackdrop", false, "draw framebuffer incomplete status=0x" + Integer.toHexString(intValue8));
                           flag3 = false;
                           flag = false;
                           break label120;
                        }

                        GL11.glViewport(0, 0, Math.max(0, intValue2), Math.max(0, intValue));
                        GL11.glDisable(2929);
                        GL11.glDisable(2884);
                        GL11.glDisable(3089);
                        GL11.glDisable(36281);
                        GL11.glColorMask(true, true, true, true);
                        this.invoke5(intValue2, intValue, floatValue2, measure3(f, 0.0F, 1.0F));
                        this.invoke6(intValue2, intValue, floatValue2, measure3(f, 0.0F, 1.0F));
                        this.intValue = 0;
                        ScreenRenderDiagnostics.invoke4(screen, "WildScreenBackdrop", true, "size=" + intValue2 + "x" + intValue);
                        flag3 = true;
                        flag = false;
                        break label119;
                     } catch (Throwable exception) {
                        this.intValue++;
                        ScreenRenderDiagnostics.invoke6("WildScreenBackdrop", screen, "renderBackdrop failed (" + this.intValue + "/4)", exception);
                        if (this.intValue >= 4) {
                           this.intValue = 0;
                           this.close();
                        }

                        intValue = 0;
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
                     return intValue != 0;
                  }

                  GL13.glActiveTexture(33984);
                  GL11.glBindTexture(3553, 0);
                  GL20.glUseProgram(0);
                  FramebufferUtils.restoreGlState(glStateSnapshot);
                  return flag3;
               }

               GL13.glActiveTexture(33984);
               GL11.glBindTexture(3553, 0);
               GL20.glUseProgram(0);
               FramebufferUtils.restoreGlState(glStateSnapshot);
               return flag3;
            }

            GL13.glActiveTexture(33984);
            GL11.glBindTexture(3553, 0);
            GL20.glUseProgram(0);
            FramebufferUtils.restoreGlState(glStateSnapshot);
            return flag2;
         }
      }
   }

   public void invoke(MinecraftClient minecraftClient, Text text) {
      MainMenuRenderer.MainMenuRendererBounds mainMenuRendererBounds2 = MainMenuRenderer.resolve(minecraftClient, 1, 1);
      if (mainMenuRendererBounds2 != null) {
         this.invoke3(mainMenuRendererBounds2.width(), mainMenuRendererBounds2.height(), text == null ? "Connecting" : text.getString(), -1.0F);
      }
   }

   public void invoke2(MinecraftClient minecraftClient, WorldGenerationProgressTracker worldGenerationProgressTracker) {
      MainMenuRenderer.MainMenuRendererBounds mainMenuRendererBounds3 = MainMenuRenderer.resolve(minecraftClient, 1, 1);
      if (mainMenuRendererBounds3 != null && worldGenerationProgressTracker != null) {
         float floatValue3 = measure3(worldGenerationProgressTracker.getProgressPercentage() / 100.0F, 0.0F, 1.0F);
         this.invoke3(mainMenuRendererBounds3.width(), mainMenuRendererBounds3.height(), Math.round(floatValue3 * 100.0F) + "%", floatValue3);
      }
   }

   private void invoke3(int i, int j, String string, float f) {
      try {
         WildClient.invoke15();
         RenderManager renderManager = WildClient.resolve();
         if (renderManager == null) {
            return;
         }

         renderManager.invoke(i, j);
         boolean flag4 = false;
         boolean flag5 = false ;

         try {
            flag5 = true;
            float floatValue4 = measure((float)i, (float)j);
            float floatValue5 = measure3(i * 0.3F, 320.0F * floatValue4, 560.0F * floatValue4);
            float floatValue6 = Math.max(8.0F * floatValue4, 8.0F);
            float floatValue7 = i * 0.5F - floatValue5 * 0.5F;
            float floatValue8 = j * 0.5F + 42.0F * floatValue4;
            float floatValue9 = floatValue6 * 0.5F;
            float floatValue10 = 0.5F + 0.5F * (float)Math.sin((float)(System.nanoTime() - Math.max(1L, this.timestamp)) / 1.0E9F * 1.2F);
            float floatValue11 = f >= 0.0F ? f : 0.18F + 0.64F * floatValue10;
            float floatValue12 = Math.max(floatValue6, floatValue5 * measure3(floatValue11, 0.0F, 1.0F));
            int intValue9 = this.flag3 ? compute(0.12F, 0.13F, 0.15F, 0.18F) : compute(1.0F, 1.0F, 1.0F, 0.105F);
            int intValue10 = this.flag3 ? compute(0.07F, 0.08F, 0.09F, 0.88F) : compute(0.94F, 0.97F, 1.0F, 0.9F);
            int intValue11 = this.flag3 ? compute(0.22F, 0.23F, 0.24F, 0.48F) : compute(0.66F, 0.72F, 0.8F, 0.48F);
            renderManager.invoke41(floatValue7, floatValue8, floatValue5, floatValue6, floatValue9, 18.0F * floatValue4, 0.9F, compute2(this.intValue3, 78));
            renderManager.invoke5(floatValue7, floatValue8, floatValue5, floatValue6, floatValue9, intValue9);
            renderManager.invoke5(floatValue7, floatValue8, floatValue12, floatValue6, floatValue9, compute3(this.intValue3, this.intValue2, floatValue10, 0.88F));
            float floatValue13 = 25.0F * floatValue4;
            float floatValue14 = RenderManager.resolve7(FontRegistry.fontObject4, string, floatValue13).floatValue;
            float floatValue15 = floatValue8 - 22.0F * floatValue4;
            renderManager.invoke69(FontRegistry.fontObject4, i * 0.5F - floatValue14 * 0.5F, floatValue15, floatValue13, string, intValue10);
            if (f >= 0.0F) {
               String text2 = "Loading world";
               float floatValue16 = 14.0F * floatValue4;
               float floatValue17 = RenderManager.resolve7(FontRegistry.fontObject, text2, floatValue16).floatValue;
               renderManager.invoke69(FontRegistry.fontObject, i * 0.5F - floatValue17 * 0.5F, floatValue8 + 34.0F * floatValue4, floatValue16, text2, intValue11);
            }

            renderManager.invoke19();
            flag4 = true;
            flag5 = false;
         } finally {
            if (flag5) {
               if (!flag4) {
                  renderManager.invoke2();
               }
            }
         }

         if (!flag4) {
            renderManager.invoke2();
         }
      } catch (Throwable exception2) {
      }
   }

   private void invoke4(float f) {
      this.offscreenFramebuffer.invoke2();
      GL11.glDisable(3042);
      GL11.glDisable(2929);
      GL11.glDisable(2884);
      FullscreenQuad.FullscreenQuadResources fullscreenQuadResources = this.theme == Theme.MIDNIGHT_AZURE
         ? this.fullscreenQuadResources4
         : (
            this.theme == Theme.VERNAL_SOLSTICE
               ? this.fullscreenQuadResources3
               : (this.theme == Theme.SAKURA_BREEZE ? this.fullscreenQuadResources2 : this.fullscreenQuadResources)
         );
      if (this.theme == Theme.SAKURA_BREEZE || this.theme == Theme.VERNAL_SOLSTICE || this.theme == Theme.MIDNIGHT_AZURE) {
         GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
         GL11.glClear(16384);
         GlStateManager._enableBlend();
         GlStateManager._blendFuncSeparate(770, 771, 1, 771);
         GL11.glEnable(3042);
         GL14.glBlendFuncSeparate(770, 771, 1, 771);
      }

      fullscreenQuadResources.invoke();
      this.invoke8(
         fullscreenQuadResources,
         this.offscreenFramebuffer.getIntValue3(),
         this.offscreenFramebuffer.getIntValue4(),
         0.0F,
         0.0F,
         this.offscreenFramebuffer.getIntValue3(),
         this.offscreenFramebuffer.getIntValue4()
      );
      fullscreenQuadResources.invoke3("uTime", f);
      fullscreenQuadResources.invoke4("uResolution", this.offscreenFramebuffer.getIntValue3(), this.offscreenFramebuffer.getIntValue4());
      fullscreenQuadResources.invoke4(
         "uMouse",
         this.floatValue3 / Math.max(1.0F, (float)this.offscreenFramebuffer.getIntValue3()),
         this.floatValue4 / Math.max(1.0F, (float)this.offscreenFramebuffer.getIntValue4())
      );
      fullscreenQuadResources.invoke4("uMouseVelocity", this.floatValue5, this.floatValue6);
      fullscreenQuadResources.invoke5("uAccentTop", measure4(this.intValue2), measure5(this.intValue2), measure6(this.intValue2));
      fullscreenQuadResources.invoke5("uAccentBottom", measure4(this.intValue3), measure5(this.intValue3), measure6(this.intValue3));
      fullscreenQuadResources.invoke3("uActivity", 0.54F);
      fullscreenQuadResources.invoke3("uAlpha", 1.0F);
      fullscreenQuadResources.invoke3("uLightMode", this.flag3 ? 1.0F : 0.0F);

      for (int intValue12 = 0; intValue12 < 14; intValue12++) {
         fullscreenQuadResources.invoke6(TEXT[intValue12], 0.0F, 0.0F, 100.0F, 0.0F);
      }

      this.shaderProgram.invoke();
   }

   private void invoke5(int i, int j, float f, float g) {
      GL11.glDisable(3042);
      this.fullscreenQuadResources5.invoke();
      this.invoke8(this.fullscreenQuadResources5, i, j, 0.0F, 0.0F, i, j);
      this.fullscreenQuadResources5.invoke2("uTexture", 0);
      this.fullscreenQuadResources5.invoke4("uTextureSize", this.offscreenFramebuffer.getIntValue3(), this.offscreenFramebuffer.getIntValue4());
      this.fullscreenQuadResources5
         .invoke4("uParallax", (this.floatValue3 / Math.max(1.0F, (float)i) - 0.5F) * 0.01F, (this.floatValue4 / Math.max(1.0F, (float)j) - 0.5F) * 0.008F);
      this.fullscreenQuadResources5.invoke3("uTime", f);
      this.fullscreenQuadResources5.invoke3("uEntry", g);
      this.fullscreenQuadResources5.invoke3("uClickFlash", 0.0F);
      this.fullscreenQuadResources5.invoke3("uLightMode", this.flag3 ? 1.0F : 0.0F);
      this.fullscreenQuadResources5.invoke3("uSakura", this.theme == Theme.SAKURA_BREEZE ? 1.0F : 0.0F);
      this.fullscreenQuadResources5.invoke3("uVernal", this.theme == Theme.VERNAL_SOLSTICE ? 1.0F : 0.0F);
      GL13.glActiveTexture(33984);
      GL11.glBindTexture(3553, this.offscreenFramebuffer.getIntValue2());
      this.shaderProgram.invoke();
   }

   private void invoke6(int i, int j, float f, float g) {
      GL11.glEnable(3042);
      GL14.glBlendFuncSeparate(770, 771, 1, 771);
      this.fullscreenQuadResources6.invoke();
      this.invoke8(this.fullscreenQuadResources6, i, j, 0.0F, 0.0F, i, j);
      this.fullscreenQuadResources6.invoke2("uBackground", 0);
      this.fullscreenQuadResources6.invoke4("uTextureSize", this.offscreenFramebuffer.getIntValue3(), this.offscreenFramebuffer.getIntValue4());
      this.fullscreenQuadResources6.invoke3("uTime", f);
      this.fullscreenQuadResources6.invoke3("uAlpha", g);
      this.fullscreenQuadResources6.invoke5("uAccentTop", measure4(this.intValue2), measure5(this.intValue2), measure6(this.intValue2));
      this.fullscreenQuadResources6.invoke5("uAccentBottom", measure4(this.intValue3), measure5(this.intValue3), measure6(this.intValue3));
      this.fullscreenQuadResources6.invoke3("uLightMode", this.flag3 ? 1.0F : 0.0F);
      GL13.glActiveTexture(33984);
      GL11.glBindTexture(3553, this.offscreenFramebuffer.getIntValue2());
      this.shaderProgram.invoke();
   }

   private void invoke7() {
      if (!this.flag2) {
         this.shaderProgram = new ShaderProgram();
         this.fullscreenQuadResources = this.fullscreenQuad
            .resolve("screen_liquid_neon_gas", "assets/wild/shaders/mainmenu/menu_quad.vert", "assets/wild/shaders/mainmenu/menu_aurora.frag");
         this.fullscreenQuadResources2 = this.fullscreenQuad
            .resolve("screen_sakura_breeze", "assets/wild/shaders/mainmenu/menu_quad.vert", "assets/wild/shaders/mainmenu/sakura_breeze.frag");
         this.fullscreenQuadResources3 = this.fullscreenQuad
            .resolve("screen_vernal_solstice", "assets/wild/shaders/mainmenu/menu_quad.vert", "assets/wild/shaders/mainmenu/vernal_solstice.frag");
         this.fullscreenQuadResources4 = this.fullscreenQuad
            .resolve("screen_midnight_azure", "assets/wild/shaders/mainmenu/menu_quad.vert", "assets/wild/shaders/mainmenu/midnight_azure.frag");
         this.fullscreenQuadResources5 = this.fullscreenQuad
            .resolve("screen_composite", "assets/wild/shaders/mainmenu/menu_quad.vert", "assets/wild/shaders/mainmenu/menu_composite.frag");
         this.fullscreenQuadResources6 = this.fullscreenQuad
            .resolve("screen_mica_wash", "assets/wild/shaders/mainmenu/menu_quad.vert", "assets/wild/shaders/mainmenu/menu_mica_wash.frag");
         this.flag2 = true;
      }
   }

   private void invoke8(FullscreenQuad.FullscreenQuadResources fullscreenQuadResources2, float f, float g, float h, float i, float j, float k) {
      fullscreenQuadResources2.invoke4("uViewport", f, g);
      fullscreenQuadResources2.invoke6("uRect", h, i, j, k);
   }

   private void invoke9() {
      Theme theme = WildClient.INSTANCE != null && WildClient.INSTANCE.themeManager != null ? WildClient.INSTANCE.themeManager.getTheme() : Theme.AURORA;
      this.theme = theme;
      ThemePalette.Swatch swatch = THEME_PALETTE.resolve3(theme);
      if (swatch != null) {
         this.intValue2 = swatch.getIntValue();
         this.intValue3 = swatch.getIntValue2();
         this.flag3 = swatch.isFlag();
      } else {
         this.flag3 = false;
         Color color = theme.getColor();
         this.intValue2 = 0xFF000000 | color.getRGB() & 16777215;
         float[] floatValues = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
         this.intValue3 = 0xFF000000
            | Color.HSBtoRGB((floatValues[0] + 0.075F) % 1.0F, Math.min(1.0F, floatValues[1] * 1.08F), Math.min(1.0F, floatValues[2] * 1.18F)) & 16777215;
      }
   }

   private void invoke10(Window window, int i, int j, float f) {
      float floatValue18 = (float)(i * window.getFramebufferWidth() / Math.max(1.0, (double)window.getScaledWidth()));
      float floatValue19 = (float)(j * window.getFramebufferHeight() / Math.max(1.0, (double)window.getScaledHeight()));
      if (!this.flag) {
         this.floatValue = this.floatValue3 = floatValue18;
         this.floatValue2 = this.floatValue4 = floatValue19;
         this.floatValue5 = 0.0F;
         this.floatValue6 = 0.0F;
         this.flag = true;
      } else {
         this.floatValue = floatValue18;
         this.floatValue2 = floatValue19;
         float floatValue20 = this.floatValue3;
         float floatValue21 = this.floatValue4;
         float floatValue22 = measure2(this.floatValue - this.floatValue3, this.floatValue2 - this.floatValue4);
         float floatValue23 = (1.0F - (float)Math.pow(3.5E-5F, f)) * (0.72F + measure3(floatValue22 / 520.0F, 0.0F, 0.42F));
         this.floatValue3 = this.floatValue3 + (this.floatValue - this.floatValue3) * measure3(floatValue23, 0.05F, 0.26F);
         this.floatValue4 = this.floatValue4 + (this.floatValue2 - this.floatValue4) * measure3(floatValue23, 0.05F, 0.26F);
         float floatValue24 = measure3((this.floatValue3 - floatValue20) / Math.max(1.0F, (float)window.getFramebufferWidth()) / f, -1.8F, 1.8F);
         float floatValue25 = measure3((this.floatValue4 - floatValue21) / Math.max(1.0F, (float)window.getFramebufferHeight()) / f, -1.8F, 1.8F);
         float floatValue26 = 1.0F - (float)Math.pow(0.0025F, f);
         this.floatValue5 = this.floatValue5 + (floatValue24 - this.floatValue5) * floatValue26;
         this.floatValue6 = this.floatValue6 + (floatValue25 - this.floatValue6) * floatValue26;
      }
   }

   @Override
   public void close() {
      this.offscreenFramebuffer.close();
      if (this.shaderProgram != null) {
         this.shaderProgram.close();
         this.shaderProgram = null;
      }

      this.fullscreenQuad.close();
      this.fullscreenQuadResources = null;
      this.fullscreenQuadResources2 = null;
      this.fullscreenQuadResources3 = null;
      this.fullscreenQuadResources4 = null;
      this.fullscreenQuadResources5 = null;
      this.fullscreenQuadResources6 = null;
      this.flag2 = false;
      this.timestamp3 = 0L;
      this.timestamp2 = 0L;
      this.timestamp = 0L;
      this.flag = false;
   }

   private static float measure(float f, float g) {
      return measure3(Math.min(f / 1920.0F, g / 1080.0F) * 1.16F, 0.72F, 1.38F);
   }

   private static float measure2(float f, float g) {
      return (float)Math.sqrt(f * f + g * g);
   }

   private static float measure3(float f, float g, float h) {
      return Math.max(g, Math.min(h, f));
   }

   private static float measure4(int i) {
      return (i >> 16 & 0xFF) / 255.0F;
   }

   private static float measure5(int i) {
      return (i >> 8 & 0xFF) / 255.0F;
   }

   private static float measure6(int i) {
      return (i & 0xFF) / 255.0F;
   }

   private static int compute(float f, float g, float h, float i) {
      int intValue13 = Math.round(measure3(f, 0.0F, 1.0F) * 255.0F);
      int intValue14 = Math.round(measure3(g, 0.0F, 1.0F) * 255.0F);
      int intValue15 = Math.round(measure3(h, 0.0F, 1.0F) * 255.0F);
      int intValue16 = Math.round(measure3(i, 0.0F, 1.0F) * 255.0F);
      return intValue16 << 24 | intValue13 << 16 | intValue14 << 8 | intValue15;
   }

   private static int compute2(int i, int j) {
      int intValue17 = Math.max(0, Math.min(255, j));
      return i & 16777215 | intValue17 << 24;
   }

   private static int compute3(int i, int j, float f, float g) {
      float floatValue27 = measure3(f, 0.0F, 1.0F);
      int intValue18 = ColorUtils.compute16(i, j, floatValue27);
      int intValue19 = Math.round(measure3(g, 0.0F, 1.0F) * 255.0F);
      return intValue19 << 24 | intValue18;
   }

   private static String[] resolve() {
      String[] texts = new String[14];

      for (int intValue20 = 0; intValue20 < texts.length; intValue20++) {
         texts[intValue20] = "uTrail[" + intValue20 + "]";
      }

      return texts;
   }
}
