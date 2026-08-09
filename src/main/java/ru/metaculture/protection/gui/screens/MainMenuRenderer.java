package ru.metaculture.protection;

import com.mojang.blaze3d.opengl.GlStateManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.texture.GlTexture;
import net.minecraft.client.util.Window;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public final class MainMenuRenderer implements AutoCloseable {
   public static final int INT_VALUE = 14;
   private static final String ASSETS_WILD_SHADERS_MAINMENU_MENU_QUAD_VERT = "assets/wild/shaders/mainmenu/menu_quad.vert";
   private static final String[] TEXT = resolve2();
   private final FullscreenQuad fullscreenQuad = new FullscreenQuad();
   private final OffscreenFramebuffer offscreenFramebuffer = new OffscreenFramebuffer();
   private ShaderProgram shaderProgram;
   private FullscreenQuad.FullscreenQuadResources fullscreenQuadResources;
   private FullscreenQuad.FullscreenQuadResources fullscreenQuadResources2;
   private FullscreenQuad.FullscreenQuadResources fullscreenQuadResources3;
   private FullscreenQuad.FullscreenQuadResources fullscreenQuadResources4;
   private FullscreenQuad.FullscreenQuadResources fullscreenQuadResources5;
   private FullscreenQuad.FullscreenQuadResources fullscreenQuadResources6;
   private FullscreenQuad.FullscreenQuadResources fullscreenQuadResources7;
   private FullscreenQuad.FullscreenQuadResources fullscreenQuadResources8;
   private FullscreenQuad.FullscreenQuadResources fullscreenQuadResources9;
   private long timestamp;
   private long timestamp2;
   private float floatValue;
   private boolean flag;
   private int intValue = -1;
   private int intValue2 = -1;

   public boolean check(MainMenuScreen.MainMenuScreenTimedEntry mainMenuScreenTimedEntry) {
      if (mainMenuScreenTimedEntry != null && mainMenuScreenTimedEntry.framebufferWidth() > 0 && mainMenuScreenTimedEntry.framebufferHeight() > 0) {
         MainMenuRenderer.MainMenuRendererBounds mainMenuRendererBounds = resolve(MinecraftClient.getInstance(), mainMenuScreenTimedEntry.framebufferWidth(), mainMenuScreenTimedEntry.framebufferHeight());
         if (mainMenuRendererBounds != null && mainMenuRendererBounds.width() == mainMenuScreenTimedEntry.framebufferWidth() && mainMenuRendererBounds.height() == mainMenuScreenTimedEntry.framebufferHeight()) {
            FramebufferUtils.GlStateSnapshot glStateSnapshot = FramebufferUtils.captureGlState();
            boolean flag = false;

            boolean flag2;
            try {
               this.invoke11();
               this.invoke10();
               int intValue = Math.max(420, Math.round(mainMenuScreenTimedEntry.framebufferWidth() * mainMenuScreenTimedEntry.backgroundScale()));
               int intValue2 = Math.max(240, Math.round(mainMenuScreenTimedEntry.framebufferHeight() * mainMenuScreenTimedEntry.backgroundScale()));
               int intValue3 = this.offscreenFramebuffer.getIntValue3();
               int intValue4 = this.offscreenFramebuffer.getIntValue4();
               this.offscreenFramebuffer.invoke(intValue, intValue2);
               boolean flag3 = intValue3 != this.offscreenFramebuffer.getIntValue3() || intValue4 != this.offscreenFramebuffer.getIntValue4();
               boolean flag4 = mainMenuScreenTimedEntry.activeMotion() > 0.025F;
               long longValue = System.nanoTime();
               float floatValue = flag4 ? 0.0125F : 0.041666668F;
               if (flag3 || this.timestamp == 0L || (float)(longValue - this.timestamp) >= floatValue * 1.0E9F) {
                  this.invoke(mainMenuScreenTimedEntry);
                  this.timestamp = longValue;
               }

               GL30.glBindFramebuffer(36160, mainMenuScreenTimedEntry.drawFramebuffer());
               int intValue5 = GL30.glCheckFramebufferStatus(36009);
               if (intValue5 == 36053) {
                  GL11.glViewport(0, 0, mainMenuScreenTimedEntry.framebufferWidth(), mainMenuScreenTimedEntry.framebufferHeight());
                  GL11.glDisable(2929);
                  GL11.glDisable(2884);
                  GL11.glDisable(3089);
                  GL11.glDisable(36281);
                  GL11.glColorMask(true, true, true, true);
                  this.invoke2(mainMenuScreenTimedEntry);
                  this.invoke3(mainMenuScreenTimedEntry);
                  this.invoke4(mainMenuScreenTimedEntry);
                  this.invoke5(mainMenuScreenTimedEntry);
                  this.invoke8(mainMenuScreenTimedEntry);
                  return this.offscreenFramebuffer.check();
               }

               ScreenRenderDiagnostics.invoke6("MainMenuRenderer", null, "draw framebuffer incomplete status=0x" + Integer.toHexString(intValue5), null);
               flag2 = false;
            } finally {
               this.invoke12(0);
               GL20.glUseProgram(0);
               FramebufferUtils.restoreGlState(glStateSnapshot);
            }

            return flag2;
         } else {
            ScreenRenderDiagnostics.invoke6(
               "MainMenuRenderer", null, "frame metrics mismatch requested=" + mainMenuScreenTimedEntry.framebufferWidth() + "x" + mainMenuScreenTimedEntry.framebufferHeight(), null
            );
            return false;
         }
      } else {
         ScreenRenderDiagnostics.invoke6("MainMenuRenderer", null, "invalid state dimensions", null);
         return false;
      }
   }

   private void invoke(MainMenuScreen.MainMenuScreenTimedEntry mainMenuScreenTimedEntry2) {
      if (this.offscreenFramebuffer.check()) {
         this.offscreenFramebuffer.invoke2();
         GL11.glDisable(3042);
         GL11.glDisable(2929);
         GL11.glDisable(2884);
         FullscreenQuad.FullscreenQuadResources fullscreenQuadResources = mainMenuScreenTimedEntry2.midnightAzure()
            ? this.fullscreenQuadResources4
            : (mainMenuScreenTimedEntry2.vernalSolstice() ? this.fullscreenQuadResources3 : (mainMenuScreenTimedEntry2.sakuraBreeze() ? this.fullscreenQuadResources2 : this.fullscreenQuadResources));
         if (mainMenuScreenTimedEntry2.sakuraBreeze() || mainMenuScreenTimedEntry2.vernalSolstice() || mainMenuScreenTimedEntry2.midnightAzure()) {
            GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
            GL11.glClear(16384);
            GlStateManager._enableBlend();
            GlStateManager._blendFuncSeparate(770, 771, 1, 771);
            GL11.glEnable(3042);
            GL14.glBlendFuncSeparate(770, 771, 1, 771);
         }

         fullscreenQuadResources.invoke();
         this.invoke6(
            fullscreenQuadResources,
            this.offscreenFramebuffer.getIntValue3(),
            this.offscreenFramebuffer.getIntValue4(),
            0.0F,
            0.0F,
            this.offscreenFramebuffer.getIntValue3(),
            this.offscreenFramebuffer.getIntValue4()
         );
         fullscreenQuadResources.invoke3("uTime", mainMenuScreenTimedEntry2.time() * measure2());
         fullscreenQuadResources.invoke4("uResolution", this.offscreenFramebuffer.getIntValue3(), this.offscreenFramebuffer.getIntValue4());
         fullscreenQuadResources.invoke4("uMouse", mainMenuScreenTimedEntry2.mouseNormX(), mainMenuScreenTimedEntry2.mouseNormY());
         fullscreenQuadResources.invoke4("uMouseVelocity", mainMenuScreenTimedEntry2.mouseVelocityX(), mainMenuScreenTimedEntry2.mouseVelocityY());
         fullscreenQuadResources.invoke5("uAccentTop", mainMenuScreenTimedEntry2.accentTopR(), mainMenuScreenTimedEntry2.accentTopG(), mainMenuScreenTimedEntry2.accentTopB());
         fullscreenQuadResources.invoke5("uAccentBottom", mainMenuScreenTimedEntry2.accentBottomR(), mainMenuScreenTimedEntry2.accentBottomG(), mainMenuScreenTimedEntry2.accentBottomB());
         fullscreenQuadResources.invoke3("uActivity", mainMenuScreenTimedEntry2.activeMotion());
         fullscreenQuadResources.invoke3("uAlpha", 1.0F);
         fullscreenQuadResources.invoke3("uLightMode", mainMenuScreenTimedEntry2.lightMode() ? 1.0F : 0.0F);
         this.invoke7(fullscreenQuadResources, mainMenuScreenTimedEntry2);
         this.shaderProgram.invoke();
      }
   }

   private void invoke2(MainMenuScreen.MainMenuScreenTimedEntry mainMenuScreenTimedEntry3) {
      if (this.offscreenFramebuffer.check()) {
         GL11.glDisable(3042);
         this.fullscreenQuadResources5.invoke();
         this.invoke6(
            this.fullscreenQuadResources5,
            mainMenuScreenTimedEntry3.framebufferWidth(),
            mainMenuScreenTimedEntry3.framebufferHeight(),
            0.0F,
            0.0F,
            mainMenuScreenTimedEntry3.framebufferWidth(),
            mainMenuScreenTimedEntry3.framebufferHeight()
         );
         this.fullscreenQuadResources5.invoke2("uTexture", 0);
         this.fullscreenQuadResources5.invoke4("uTextureSize", this.offscreenFramebuffer.getIntValue3(), this.offscreenFramebuffer.getIntValue4());
         this.fullscreenQuadResources5.invoke4("uParallax", mainMenuScreenTimedEntry3.backgroundParallaxX(), mainMenuScreenTimedEntry3.backgroundParallaxY());
         this.fullscreenQuadResources5.invoke3("uTime", mainMenuScreenTimedEntry3.time() * measure2());
         this.fullscreenQuadResources5.invoke3("uEntry", mainMenuScreenTimedEntry3.sceneEntry());
         this.fullscreenQuadResources5.invoke3("uClickFlash", mainMenuScreenTimedEntry3.clickFlash());
         this.fullscreenQuadResources5.invoke3("uLightMode", mainMenuScreenTimedEntry3.lightMode() ? 1.0F : 0.0F);
         this.fullscreenQuadResources5.invoke3("uSakura", mainMenuScreenTimedEntry3.sakuraBreeze() ? 1.0F : 0.0F);
         this.fullscreenQuadResources5.invoke3("uVernal", mainMenuScreenTimedEntry3.vernalSolstice() ? 1.0F : 0.0F);
         this.invoke12(this.offscreenFramebuffer.getIntValue2());
         this.shaderProgram.invoke();
      }
   }

   private void invoke3(MainMenuScreen.MainMenuScreenTimedEntry mainMenuScreenTimedEntry4) {
      GL11.glEnable(3042);
      if (mainMenuScreenTimedEntry4.lightMode()) {
         GL14.glBlendFuncSeparate(770, 771, 1, 771);
      } else {
         GL14.glBlendFuncSeparate(770, 1, 1, 1);
      }

      this.fullscreenQuadResources6.invoke();
      this.invoke6(
         this.fullscreenQuadResources6,
         mainMenuScreenTimedEntry4.framebufferWidth(),
         mainMenuScreenTimedEntry4.framebufferHeight(),
         0.0F,
         0.0F,
         mainMenuScreenTimedEntry4.framebufferWidth(),
         mainMenuScreenTimedEntry4.framebufferHeight()
      );
      this.fullscreenQuadResources6.invoke3("uTime", mainMenuScreenTimedEntry4.time() * measure2());
      this.fullscreenQuadResources6.invoke4("uResolution", mainMenuScreenTimedEntry4.framebufferWidth(), mainMenuScreenTimedEntry4.framebufferHeight());
      this.fullscreenQuadResources6.invoke4("uMouse", mainMenuScreenTimedEntry4.mouseNormX(), mainMenuScreenTimedEntry4.mouseNormY());
      this.fullscreenQuadResources6.invoke4("uParallax", mainMenuScreenTimedEntry4.particleParallaxX(), mainMenuScreenTimedEntry4.particleParallaxY());
      this.fullscreenQuadResources6.invoke5("uAccentTop", mainMenuScreenTimedEntry4.accentTopR(), mainMenuScreenTimedEntry4.accentTopG(), mainMenuScreenTimedEntry4.accentTopB());
      this.fullscreenQuadResources6.invoke5("uAccentBottom", mainMenuScreenTimedEntry4.accentBottomR(), mainMenuScreenTimedEntry4.accentBottomG(), mainMenuScreenTimedEntry4.accentBottomB());
      this.fullscreenQuadResources6.invoke3("uEntry", mainMenuScreenTimedEntry4.sceneEntry());
      this.fullscreenQuadResources6.invoke3("uLightMode", mainMenuScreenTimedEntry4.lightMode() ? 1.0F : 0.0F);
      this.invoke7(this.fullscreenQuadResources6, mainMenuScreenTimedEntry4);
      this.shaderProgram.invoke();
      GL14.glBlendFuncSeparate(770, 771, 1, 771);
   }

   private void invoke4(MainMenuScreen.MainMenuScreenTimedEntry mainMenuScreenTimedEntry5) {
      MainMenuScreen.MainMenuScreenBounds3 mainMenuScreenBounds3 = mainMenuScreenTimedEntry5.logo();
      if (!(mainMenuScreenBounds3.width() <= 0.0F) && !(mainMenuScreenBounds3.height() <= 0.0F)) {
         GL11.glEnable(3042);
         GL14.glBlendFuncSeparate(770, 771, 1, 771);
         this.fullscreenQuadResources7.invoke();
         this.invoke6(this.fullscreenQuadResources7, mainMenuScreenTimedEntry5.framebufferWidth(), mainMenuScreenTimedEntry5.framebufferHeight(), mainMenuScreenBounds3.x(), mainMenuScreenBounds3.y(), mainMenuScreenBounds3.width(), mainMenuScreenBounds3.height());
         this.fullscreenQuadResources7.invoke3("uTime", this.measure());
         this.fullscreenQuadResources7.invoke3("uEntry", mainMenuScreenTimedEntry5.sceneEntry());
         this.fullscreenQuadResources7.invoke3("uPulse", mainMenuScreenBounds3.pulse());
         this.fullscreenQuadResources7.invoke4("uMouse", mainMenuScreenTimedEntry5.mouseX(), mainMenuScreenTimedEntry5.mouseY());
         this.fullscreenQuadResources7
            .invoke4(
               "uLocalMouse",
               (mainMenuScreenTimedEntry5.mouseX() - mainMenuScreenBounds3.x()) / Math.max(1.0F, mainMenuScreenBounds3.width()),
               (mainMenuScreenTimedEntry5.mouseY() - mainMenuScreenBounds3.y()) / Math.max(1.0F, mainMenuScreenBounds3.height())
            );
         this.fullscreenQuadResources7.invoke4("uMouseVelocity", mainMenuScreenTimedEntry5.mouseVelocityX(), mainMenuScreenTimedEntry5.mouseVelocityY());
         this.fullscreenQuadResources7.invoke5("uAccentTop", mainMenuScreenTimedEntry5.accentTopR(), mainMenuScreenTimedEntry5.accentTopG(), mainMenuScreenTimedEntry5.accentTopB());
         this.fullscreenQuadResources7.invoke5("uAccentBottom", mainMenuScreenTimedEntry5.accentBottomR(), mainMenuScreenTimedEntry5.accentBottomG(), mainMenuScreenTimedEntry5.accentBottomB());
         this.fullscreenQuadResources7.invoke3("uLightMode", mainMenuScreenTimedEntry5.lightMode() ? 1.0F : 0.0F);
         this.shaderProgram.invoke();
      }
   }

   private void invoke5(MainMenuScreen.MainMenuScreenTimedEntry mainMenuScreenTimedEntry6) {
      if (this.offscreenFramebuffer.check()) {
         GL11.glEnable(3042);
         GL14.glBlendFuncSeparate(770, 771, 1, 771);
         this.fullscreenQuadResources8.invoke();
         this.fullscreenQuadResources8.invoke2("uBackground", 0);
         this.fullscreenQuadResources8.invoke4("uTextureSize", this.offscreenFramebuffer.getIntValue3(), this.offscreenFramebuffer.getIntValue4());
         this.fullscreenQuadResources8.invoke3("uTime", mainMenuScreenTimedEntry6.time());
         this.fullscreenQuadResources8.invoke4("uMouse", mainMenuScreenTimedEntry6.mouseX(), mainMenuScreenTimedEntry6.mouseY());
         this.fullscreenQuadResources8.invoke5("uAccentTop", mainMenuScreenTimedEntry6.accentTopR(), mainMenuScreenTimedEntry6.accentTopG(), mainMenuScreenTimedEntry6.accentTopB());
         this.fullscreenQuadResources8.invoke5("uAccentBottom", mainMenuScreenTimedEntry6.accentBottomR(), mainMenuScreenTimedEntry6.accentBottomG(), mainMenuScreenTimedEntry6.accentBottomB());
         this.fullscreenQuadResources8.invoke3("uVelocity", mainMenuScreenTimedEntry6.mouseSpeed());
         this.fullscreenQuadResources8.invoke3("uLightMode", mainMenuScreenTimedEntry6.lightMode() ? 1.0F : 0.0F);
         this.invoke12(this.offscreenFramebuffer.getIntValue2());

         for (MainMenuScreen.MainMenuScreenBounds mainMenuScreenBounds : mainMenuScreenTimedEntry6.buttons()) {
            float floatValue2 = mainMenuScreenBounds.bleed();
            float floatValue3 = mainMenuScreenBounds.x() - floatValue2;
            float floatValue4 = mainMenuScreenBounds.y() - floatValue2;
            float floatValue5 = mainMenuScreenBounds.width() + floatValue2 * 2.0F;
            float floatValue6 = mainMenuScreenBounds.height() + floatValue2 * 2.0F;
            this.invoke6(this.fullscreenQuadResources8, mainMenuScreenTimedEntry6.framebufferWidth(), mainMenuScreenTimedEntry6.framebufferHeight(), floatValue3, floatValue4, floatValue5, floatValue6);
            this.fullscreenQuadResources8.invoke6("uButton", floatValue2, floatValue2, mainMenuScreenBounds.width(), mainMenuScreenBounds.height());
            this.fullscreenQuadResources8.invoke4("uLocalMouse", mainMenuScreenBounds.localMouseX(), mainMenuScreenBounds.localMouseY());
            this.fullscreenQuadResources8.invoke3("uRadius", mainMenuScreenBounds.radius());
            this.fullscreenQuadResources8.invoke3("uHover", mainMenuScreenBounds.hover());
            this.fullscreenQuadResources8.invoke3("uMagnet", mainMenuScreenBounds.magnet());
            this.fullscreenQuadResources8.invoke3("uPress", mainMenuScreenBounds.press());
            this.fullscreenQuadResources8.invoke3("uEntry", mainMenuScreenBounds.entry());
            this.fullscreenQuadResources8.invoke3("uFlash", mainMenuScreenBounds.flash());
            this.fullscreenQuadResources8.invoke3("uScale", mainMenuScreenBounds.scale());
            this.fullscreenQuadResources8.invoke3("uButtonVelocity", mainMenuScreenBounds.velocity());
            this.shaderProgram.invoke();
         }
      }
   }

   private void invoke6(FullscreenQuad.FullscreenQuadResources fullscreenQuadResources2, float f, float g, float h, float i, float j, float k) {
      fullscreenQuadResources2.invoke4("uViewport", f, g);
      fullscreenQuadResources2.invoke6("uRect", h, i, j, k);
   }

   private void invoke7(FullscreenQuad.FullscreenQuadResources fullscreenQuadResources3, MainMenuScreen.MainMenuScreenTimedEntry mainMenuScreenTimedEntry7) {
      for (int intValue6 = 0; intValue6 < 14; intValue6++) {
         MainMenuScreen.MainMenuScreenData mainMenuScreenData = mainMenuScreenTimedEntry7.trail(intValue6);
         fullscreenQuadResources3.invoke6(TEXT[intValue6], mainMenuScreenData.x(), mainMenuScreenData.y(), mainMenuScreenData.age(), mainMenuScreenData.strength());
      }
   }

   private void invoke8(MainMenuScreen.MainMenuScreenTimedEntry mainMenuScreenTimedEntry8) {
      if (this.offscreenFramebuffer.check()) {
         GL11.glEnable(3042);
         GL14.glBlendFuncSeparate(770, 771, 1, 771);
         this.fullscreenQuadResources9.invoke();
         this.fullscreenQuadResources9.invoke2("uBackground", 0);
         this.fullscreenQuadResources9.invoke4("uTextureSize", this.offscreenFramebuffer.getIntValue3(), this.offscreenFramebuffer.getIntValue4());
         this.fullscreenQuadResources9.invoke3("uTime", mainMenuScreenTimedEntry8.time() * measure2());
         this.fullscreenQuadResources9.invoke4("uMouse", mainMenuScreenTimedEntry8.mouseX(), mainMenuScreenTimedEntry8.mouseY());
         this.fullscreenQuadResources9.invoke5("uAccentTop", mainMenuScreenTimedEntry8.accentTopR(), mainMenuScreenTimedEntry8.accentTopG(), mainMenuScreenTimedEntry8.accentTopB());
         this.fullscreenQuadResources9.invoke5("uAccentBottom", mainMenuScreenTimedEntry8.accentBottomR(), mainMenuScreenTimedEntry8.accentBottomG(), mainMenuScreenTimedEntry8.accentBottomB());
         this.fullscreenQuadResources9.invoke3("uVelocity", mainMenuScreenTimedEntry8.mouseSpeed());
         this.fullscreenQuadResources9.invoke3("uLightMode", mainMenuScreenTimedEntry8.lightMode() ? 1.0F : 0.0F);
         this.fullscreenQuadResources9.invoke3("uGlassBlur", measure3(MenuModule.MAKSIMALNYY_BLYUR.getValue() / 32.0F, 0.25F, 2.0F));
         this.fullscreenQuadResources9.invoke3("uGlassTint", measure3(MenuModule.IRIDISTSENTNYY_OTLIV.getValue(), 0.0F, 1.0F));
         this.fullscreenQuadResources9.invoke3("uInteriorGlow", MenuModule.check(MenuModule.VNUTRENNEE_SVECHENIE) ? 1.0F : 0.0F);
         this.fullscreenQuadResources9.invoke3("uFilmGrain", MenuModule.check(MenuModule.ZERNO_PLYONKI) ? 1.0F : 0.0F);
         this.invoke12(this.offscreenFramebuffer.getIntValue2());
         MainMenuScreen.MainMenuScreenBounds6 mainMenuScreenBounds6 = mainMenuScreenTimedEntry8.profileTrigger();
         if (mainMenuScreenBounds6 != null && mainMenuScreenBounds6.width() > 1.0F && mainMenuScreenBounds6.height() > 1.0F && mainMenuScreenBounds6.entry() > 0.001F) {
            this.invoke9(
               mainMenuScreenTimedEntry8,
               mainMenuScreenBounds6.x(),
               mainMenuScreenBounds6.y(),
               mainMenuScreenBounds6.width(),
               mainMenuScreenBounds6.height(),
               mainMenuScreenBounds6.radius(),
               0,
               mainMenuScreenBounds6.hover(),
               mainMenuScreenBounds6.press(),
               mainMenuScreenBounds6.flash(),
               0.0F,
               mainMenuScreenBounds6.entry(),
               mainMenuScreenBounds6.scale(),
               mainMenuScreenBounds6.localMouseX(),
               mainMenuScreenBounds6.localMouseY(),
               mainMenuScreenBounds6.panelProgress(),
               mainMenuScreenBounds6.hover()
            );
         }

         MainMenuScreen.MainMenuScreenBounds5 mainMenuScreenBounds5 = mainMenuScreenTimedEntry8.profilePanel();
         if (mainMenuScreenBounds5 != null && mainMenuScreenBounds5.width() > 1.0F && mainMenuScreenBounds5.height() > 1.0F && mainMenuScreenBounds5.progress() > 0.001F) {
            this.invoke9(
               mainMenuScreenTimedEntry8,
               mainMenuScreenBounds5.x(),
               mainMenuScreenBounds5.y(),
               mainMenuScreenBounds5.width(),
               mainMenuScreenBounds5.height(),
               mainMenuScreenBounds5.radius(),
               1,
               mainMenuScreenBounds5.progress(),
               0.0F,
               0.0F,
               0.0F,
               mainMenuScreenBounds5.progress(),
               1.0F,
               0.5F,
               0.5F,
               mainMenuScreenBounds5.progress(),
               0.0F
            );
         }

         if (mainMenuScreenTimedEntry8.profiles() != null) {
            for (MainMenuScreen.MainMenuScreenBounds4 mainMenuScreenBounds4 : mainMenuScreenTimedEntry8.profiles()) {
               if (!(mainMenuScreenBounds4.width() <= 1.0F) && !(mainMenuScreenBounds4.height() <= 1.0F) && !(mainMenuScreenBounds4.entry() <= 0.001F)) {
                  this.invoke9(
                     mainMenuScreenTimedEntry8,
                     mainMenuScreenBounds4.x(),
                     mainMenuScreenBounds4.y(),
                     mainMenuScreenBounds4.width(),
                     mainMenuScreenBounds4.height(),
                     mainMenuScreenBounds4.radius(),
                     2,
                     mainMenuScreenBounds4.hover(),
                     mainMenuScreenBounds4.press(),
                     mainMenuScreenBounds4.flash(),
                     mainMenuScreenBounds4.selected(),
                     mainMenuScreenBounds4.entry(),
                     mainMenuScreenBounds4.scale(),
                     mainMenuScreenBounds4.localMouseX(),
                     mainMenuScreenBounds4.localMouseY(),
                     mainMenuScreenBounds4.entry(),
                     mainMenuScreenBounds4.velocity()
                  );
               }
            }

            if (mainMenuScreenTimedEntry8.controls() != null) {
               for (MainMenuScreen.MainMenuScreenBounds2 mainMenuScreenBounds2 : mainMenuScreenTimedEntry8.controls()) {
                  if (!(mainMenuScreenBounds2.width() <= 1.0F) && !(mainMenuScreenBounds2.height() <= 1.0F) && !(mainMenuScreenBounds2.entry() <= 0.001F)) {
                     this.invoke9(
                        mainMenuScreenTimedEntry8,
                        mainMenuScreenBounds2.x(),
                        mainMenuScreenBounds2.y(),
                        mainMenuScreenBounds2.width(),
                        mainMenuScreenBounds2.height(),
                        mainMenuScreenBounds2.radius(),
                        3,
                        mainMenuScreenBounds2.hover(),
                        mainMenuScreenBounds2.press(),
                        mainMenuScreenBounds2.flash(),
                        mainMenuScreenBounds2.active(),
                        mainMenuScreenBounds2.entry(),
                        mainMenuScreenBounds2.scale(),
                        mainMenuScreenBounds2.localMouseX(),
                        mainMenuScreenBounds2.localMouseY(),
                        mainMenuScreenBounds2.value(),
                        mainMenuScreenBounds2.velocity()
                     );
                  }
               }
            }
         }
      }
   }

   private void invoke9(
      MainMenuScreen.MainMenuScreenTimedEntry mainMenuScreenTimedEntry9,
      float f,
      float g,
      float h,
      float i,
      float j,
      int k,
      float l,
      float m,
      float n,
      float o,
      float p,
      float q,
      float r,
      float s,
      float t,
      float u
   ) {
      float floatValue7 = k == 1 ? 64.0F : (k == 2 ? 36.0F : 34.0F);
      float floatValue8 = f - floatValue7;
      float floatValue9 = g - floatValue7;
      float floatValue10 = h + floatValue7 * 2.0F;
      float floatValue11 = i + floatValue7 * 2.0F;
      this.invoke6(this.fullscreenQuadResources9, mainMenuScreenTimedEntry9.framebufferWidth(), mainMenuScreenTimedEntry9.framebufferHeight(), floatValue8, floatValue9, floatValue10, floatValue11);
      this.fullscreenQuadResources9.invoke6("uContent", floatValue7, floatValue7, h, i);
      this.fullscreenQuadResources9.invoke3("uRadius", j);
      this.fullscreenQuadResources9.invoke2("uMode", k);
      this.fullscreenQuadResources9.invoke3("uHover", l);
      this.fullscreenQuadResources9.invoke3("uPress", m);
      this.fullscreenQuadResources9.invoke3("uFlash", n);
      this.fullscreenQuadResources9.invoke3("uSelected", o);
      this.fullscreenQuadResources9.invoke3("uEntry", p);
      this.fullscreenQuadResources9.invoke3("uScale", q);
      this.fullscreenQuadResources9.invoke4("uLocalMouse", r, s);
      this.fullscreenQuadResources9.invoke3("uPanelProgress", t);
      this.fullscreenQuadResources9.invoke3("uSurfaceVelocity", u);
      this.shaderProgram.invoke();
   }

   private float measure() {
      long longValue2 = System.nanoTime();
      if (this.timestamp2 == 0L) {
         this.timestamp2 = longValue2;
         return this.floatValue;
      } else {
         float floatValue12 = (float)(longValue2 - this.timestamp2) / 1.0E9F;
         this.timestamp2 = longValue2;
         if (!Float.isFinite(floatValue12) || floatValue12 < 0.0F) {
            floatValue12 = 0.0F;
         }

         this.floatValue = this.floatValue + Math.min(floatValue12, 0.05F);
         if (this.floatValue > 240.0F) {
            this.floatValue -= 240.0F;
         }

         return this.floatValue;
      }
   }

   private static float measure2() {
      return measure3(MenuModule.SKOROST_TECHENIYA.getValue(), 0.0F, 1.5F);
   }

   private static float measure3(float f, float g, float h) {
      return Math.max(g, Math.min(h, f));
   }

   private void invoke10() {
      if (!this.flag) {
         this.shaderProgram = new ShaderProgram();
         this.fullscreenQuadResources = this.fullscreenQuad
            .resolve("liquid_neon_gas", "assets/wild/shaders/mainmenu/menu_quad.vert", "assets/wild/shaders/mainmenu/menu_aurora.frag");
         this.fullscreenQuadResources2 = this.fullscreenQuad
            .resolve("sakura_breeze", "assets/wild/shaders/mainmenu/menu_quad.vert", "assets/wild/shaders/mainmenu/sakura_breeze.frag");
         this.fullscreenQuadResources3 = this.fullscreenQuad
            .resolve("vernal_solstice", "assets/wild/shaders/mainmenu/menu_quad.vert", "assets/wild/shaders/mainmenu/vernal_solstice.frag");
         this.fullscreenQuadResources4 = this.fullscreenQuad
            .resolve("midnight_azure", "assets/wild/shaders/mainmenu/menu_quad.vert", "assets/wild/shaders/mainmenu/midnight_azure.frag");
         this.fullscreenQuadResources5 = this.fullscreenQuad
            .resolve("composite", "assets/wild/shaders/mainmenu/menu_quad.vert", "assets/wild/shaders/mainmenu/menu_composite.frag");
         this.fullscreenQuadResources6 = this.fullscreenQuad
            .resolve("particles", "assets/wild/shaders/mainmenu/menu_quad.vert", "assets/wild/shaders/mainmenu/menu_particles.frag");
         this.fullscreenQuadResources7 = this.fullscreenQuad
            .resolve("logo", "assets/wild/shaders/mainmenu/menu_quad.vert", "assets/wild/shaders/mainmenu/menu_logo.frag");
         this.fullscreenQuadResources8 = this.fullscreenQuad
            .resolve("buttons", "assets/wild/shaders/mainmenu/menu_quad.vert", "assets/wild/shaders/mainmenu/menu_button.frag");
         this.fullscreenQuadResources9 = this.fullscreenQuad
            .resolve("profile_panel", "assets/wild/shaders/mainmenu/menu_quad.vert", "assets/wild/shaders/mainmenu/menu_profile_panel.frag");
         this.flag = true;
      }
   }

   private void invoke11() {
      this.intValue = -1;
      this.intValue2 = -1;
      this.fullscreenQuad.invoke();
   }

   private void invoke12(int i) {
      if (this.intValue != 33984) {
         GL13.glActiveTexture(33984);
         this.intValue = 33984;
         this.intValue2 = -1;
      }

      if (this.intValue2 != i) {
         GL11.glBindTexture(3553, i);
         this.intValue2 = i;
      }
   }

   public static MainMenuRenderer.MainMenuRendererBounds resolve(MinecraftClient minecraftClient, int i, int j) {
      if (minecraftClient != null && GLFW.glfwGetCurrentContext() != 0L) {
         Window window = minecraftClient.getWindow();
         if (window != null && !window.hasZeroWidthOrHeight()) {
            int intValue7 = window.getFramebufferWidth();
            int intValue8 = window.getFramebufferHeight();
            if (intValue7 > 0 && intValue8 > 0 && i > 0 && j > 0) {
               Framebuffer framebuffer = minecraftClient.getFramebuffer();
               if (framebuffer != null && framebuffer.textureWidth > 0 && framebuffer.textureHeight > 0) {
                  if (!(framebuffer.getColorAttachment() instanceof GlTexture glTexture)) {
                     return null;
                  } else {
                     int intValue9 = glTexture.getGlId();
                     if (intValue9 > 0 && GL11.glIsTexture(intValue9)) {
                        if (intValue7 > framebuffer.textureWidth || intValue8 > framebuffer.textureHeight) {
                           intValue7 = Math.min(intValue7, framebuffer.textureWidth);
                           intValue8 = Math.min(intValue8, framebuffer.textureHeight);
                        }

                        return intValue7 > 0 && intValue8 > 0 ? new MainMenuRenderer.MainMenuRendererBounds(intValue7, intValue8, intValue9) : null;
                     } else {
                        return null;
                     }
                  }
               } else {
                  return null;
               }
            } else {
               return null;
            }
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   private static String[] resolve2() {
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
      this.fullscreenQuadResources = null;
      this.fullscreenQuadResources2 = null;
      this.fullscreenQuadResources3 = null;
      this.fullscreenQuadResources4 = null;
      this.fullscreenQuadResources5 = null;
      this.fullscreenQuadResources6 = null;
      this.fullscreenQuadResources7 = null;
      this.fullscreenQuadResources8 = null;
      this.fullscreenQuadResources9 = null;
      this.flag = false;
      this.timestamp = 0L;
      this.timestamp2 = 0L;
      this.floatValue = 0.0F;
   }

   public void invoke13(int i, int j) {
      try {
         this.offscreenFramebuffer.close();
         if (this.shaderProgram != null) {
            try {
               this.shaderProgram.close();
            } catch (Throwable exception) {
            }

            this.shaderProgram = null;
         }

         try {
            this.fullscreenQuad.close();
         } catch (Throwable exception2) {
         }

         this.fullscreenQuadResources = null;
         this.fullscreenQuadResources2 = null;
         this.fullscreenQuadResources3 = null;
         this.fullscreenQuadResources4 = null;
         this.fullscreenQuadResources5 = null;
         this.fullscreenQuadResources6 = null;
         this.fullscreenQuadResources7 = null;
         this.fullscreenQuadResources8 = null;
         this.fullscreenQuadResources9 = null;
         this.flag = false;
         this.timestamp = 0L;
         this.timestamp2 = 0L;
         this.floatValue = 0.0F;
      } catch (Throwable exception3) {
      }
   }

   public record MainMenuRendererBounds(int width, int height, int colorTexture) {
   }
}
