package ru.metaculture.protection;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.CubeMapRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.RotatingCubeMapRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.texture.CubemapTexture;
import net.minecraft.client.util.Window;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;

public final class MainMenuScreen extends Screen implements BackdropScreen {
   private static final ThemePalette THEME_PALETTE = ThemePalette.resolve2();
   private static final int INT_VALUE = 14;
   private static final Identifier IDENTIFIER = Identifier.ofVanilla("textures/gui/title/background/panorama");
   private final MainMenuRenderer mainMenuRenderer = new MainMenuRenderer();
   private final RotatingCubeMapRenderer rotatingCubeMapRenderer = new RotatingCubeMapRenderer(new CubeMapRenderer(IDENTIFIER));
   private boolean flag;
   private boolean flag2;
   private volatile boolean flag3;
   private final List<MainMenuScreen.MainMenuScreenUiState3> items = List.of(
      new MainMenuScreen.MainMenuScreenUiState3("Singleplayer", MainMenuScreen.MainMenuScreenState.SINGLEPLAYER),
      new MainMenuScreen.MainMenuScreenUiState3("Multiplayer", MainMenuScreen.MainMenuScreenState.MULTIPLAYER),
      new MainMenuScreen.MainMenuScreenUiState3("Alt Manager", MainMenuScreen.MainMenuScreenState.ALT_MANAGER),
      new MainMenuScreen.MainMenuScreenUiState3("Options", MainMenuScreen.MainMenuScreenState.OPTIONS),
      new MainMenuScreen.MainMenuScreenUiState3("Quit", MainMenuScreen.MainMenuScreenState.QUIT)
   );
   private final MainMenuScreen.MainMenuScreenState4[] mainMenuScreenState4s = new MainMenuScreen.MainMenuScreenState4[14];
   private final SpringIntegrator springIntegrator = new SpringIntegrator(SpringSpec.resolve6());
   private final SpringIntegrator springIntegrator2 = new SpringIntegrator(SpringSpec.resolve6());
   private long timestamp;
   private long timestamp2;
   private long timestamp3;
   private float floatValue;
   private float floatValue2;
   private float floatValue3;
   private float floatValue4;
   private float floatValue5;
   private float floatValue6;
   private float floatValue7;
   private float floatValue8;
   private float floatValue9;
   private float floatValue10;
   private float floatValue11;
   private boolean flag4;
   private boolean flag5;
   private boolean flag6;
   private int intValue = -6357021;
   private int intValue2 = -11341636;
   private Theme theme = Theme.AURORA;
   private boolean flag7;
   private MainMenuScreen.MainMenuScreenTimedEntry mainMenuScreenTimedEntry;
   private static final MainMenuScreen.MainMenuScreenDisplayEntry[] MAIN_MENU_SCREEN_DISPLAY_ENTRYS = new MainMenuScreen.MainMenuScreenDisplayEntry[]{
      new MainMenuScreen.MainMenuScreenDisplayEntry("UltraLow", "Max FPS: Minecraft + HUD + NameTags stripped", GraphicsQuality.ULTRA_LOW),
      new MainMenuScreen.MainMenuScreenDisplayEntry("Performance", "Lean shader cadence, reduced secondary bloom", GraphicsQuality.LOW),
      new MainMenuScreen.MainMenuScreenDisplayEntry("Balanced", "Full motion layer with restrained cinematic cost", GraphicsQuality.MEDIUM),
      new MainMenuScreen.MainMenuScreenDisplayEntry("Cinematic Dark", "Deep mica glass, bloom trails, focus pull", GraphicsQuality.HIGH),
      new MainMenuScreen.MainMenuScreenDisplayEntry("Custom", "All premium passes unlocked for manual tuning", GraphicsQuality.ULTRA)
   };
   private static final int INT_VALUE_2 = MAIN_MENU_SCREEN_DISPLAY_ENTRYS.length;
   private static final MainMenuScreen.MainMenuScreenUiState[] MAIN_MENU_SCREEN_UI_STATES = new MainMenuScreen.MainMenuScreenUiState[]{
      MainMenuScreen.MainMenuScreenUiState.resolve("Mica Blur", "Backdrop glass diffusion", MenuModule.MAKSIMALNYY_BLYUR, MainMenuScreen.MainMenuScreenState3.PIXELS),
      MainMenuScreen.MainMenuScreenUiState.resolve("Nebula Flow", "Background motion tempo", MenuModule.SKOROST_TECHENIYA, MainMenuScreen.MainMenuScreenState3.MULTIPLIER),
      MainMenuScreen.MainMenuScreenUiState.resolve2("Depth Parallax", "Motion reactive menu depth", MenuModule.PARALLAKS_GLAVNOGO_MENYU),
      MainMenuScreen.MainMenuScreenUiState.resolve("Glass Tint", "Accent wash intensity", MenuModule.IRIDISTSENTNYY_OTLIV, MainMenuScreen.MainMenuScreenState3.PERCENT),
      MainMenuScreen.MainMenuScreenUiState.resolve2("Interior Glow", "Card inner luminance pass", MenuModule.VNUTRENNEE_SVECHENIE),
      MainMenuScreen.MainMenuScreenUiState.resolve2("Film Grain", "Fine cinematic texture", MenuModule.ZERNO_PLYONKI),
      MainMenuScreen.MainMenuScreenUiState.resolve("UI Scale", "Menu and panel density", MenuModule.MASSHTAB_GUI, MainMenuScreen.MainMenuScreenState3.SCALE),
      MainMenuScreen.MainMenuScreenUiState.resolve2("Cursor Trail", "Magnetic pointer afterglow", MenuModule.SLED_KURSORA_VMENYU)
   };
   private static final int INT_VALUE_3 = MAIN_MENU_SCREEN_UI_STATES.length;
   private static final MainMenuScreen.MainMenuScreenDisplayEntry2[] MAIN_MENU_SCREEN_DISPLAY_ENTRY2S = new MainMenuScreen.MainMenuScreenDisplayEntry2[]{
      new MainMenuScreen.MainMenuScreenDisplayEntry2("RENDERING", 0, 3), new MainMenuScreen.MainMenuScreenDisplayEntry2("EFFECTS", 3, 3), new MainMenuScreen.MainMenuScreenDisplayEntry2("UI & PERFORMANCE", 6, 2)
   };
   private static final int INT_VALUE_4 = MAIN_MENU_SCREEN_DISPLAY_ENTRY2S.length;
   private final SpringIntegrator springIntegrator3 = new SpringIntegrator(SpringSpec.resolve18());
   private final SpringIntegrator springIntegrator4 = new SpringIntegrator(SpringSpec.resolve6());
   private final MainMenuScreen.MainMenuScreenUiState4[] mainMenuScreenUiState4s = new MainMenuScreen.MainMenuScreenUiState4[INT_VALUE_2];
   private final MainMenuScreen.MainMenuScreenUiState2[] mainMenuScreenUiState2s = new MainMenuScreen.MainMenuScreenUiState2[INT_VALUE_3];
   private final MainMenuScreen.MainMenuScreenUiState5[] mainMenuScreenUiState5s = new MainMenuScreen.MainMenuScreenUiState5[INT_VALUE_4];
   private int intValue3 = -1;
   private boolean flag8;
   private boolean flag9;
   private float floatValue12;
   private float floatValue13;
   private float floatValue14;
   private float floatValue15;
   private float floatValue16;
   private float floatValue17;
   private float floatValue18;
   private float floatValue19;
   private float floatValue20 = 1.0F;
   private float floatValue21 = 0.5F;
   private float floatValue22 = 0.5F;
   private float floatValue23;
   private float floatValue24;
   private float floatValue25;
   private float floatValue26;
   private float floatValue27;
   private float floatValue28;
   private int intValue4 = -1;
   private int intValue5 = -1;
   private int intValue6 = -1;
   private static final boolean FLAG = false;
   private static final float FLOAT_VALUE = 30.0F;
   private static final float FLOAT_VALUE_2 = 9.0F;
   private static final float FLOAT_VALUE_3 = 138.0F;
   private boolean flag10;
   private float floatValue29 = -1.0F;
   private float floatValue30 = -1.0F;

   public MainMenuScreen() {
      super(Text.literal("North"));

      for (int intValue = 0; intValue < this.mainMenuScreenState4s.length; intValue++) {
         this.mainMenuScreenState4s[intValue] = new MainMenuScreen.MainMenuScreenState4();
      }

      for (int intValue2 = 0; intValue2 < this.mainMenuScreenUiState4s.length; intValue2++) {
         this.mainMenuScreenUiState4s[intValue2] = new MainMenuScreen.MainMenuScreenUiState4(MAIN_MENU_SCREEN_DISPLAY_ENTRYS[intValue2]);
      }

      for (int intValue3 = 0; intValue3 < this.mainMenuScreenUiState2s.length; intValue3++) {
         this.mainMenuScreenUiState2s[intValue3] = new MainMenuScreen.MainMenuScreenUiState2(MAIN_MENU_SCREEN_UI_STATES[intValue3]);
      }

      for (int intValue4 = 0; intValue4 < this.mainMenuScreenUiState5s.length; intValue4++) {
         this.mainMenuScreenUiState5s[intValue4] = new MainMenuScreen.MainMenuScreenUiState5(MAIN_MENU_SCREEN_DISPLAY_ENTRY2S[intValue4]);
      }
   }

   protected void init() {
      super.init();
      this.timestamp = System.nanoTime();
      this.timestamp2 = this.timestamp;
      this.timestamp3 = this.timestamp;
      this.springIntegrator.setFloatValue(0.0F);
      this.springIntegrator2.setFloatValue(0.0F);
      this.flag4 = false;
      this.flag5 = false;
      this.flag6 = false;
      this.flag8 = false;
      this.springIntegrator3.setFloatValue(0.0F);
      this.springIntegrator4.setFloatValue(1.0F);
      this.floatValue17 = 0.0F;
      this.floatValue18 = 0.0F;
      this.floatValue19 = 0.0F;
      this.floatValue20 = 1.0F;
      this.floatValue21 = 0.5F;
      this.floatValue22 = 0.5F;
      this.intValue4 = -1;
      this.intValue5 = -1;
      this.intValue3 = -1;
      this.intValue6 = -1;
      this.flag10 = false;

      for (MainMenuScreen.MainMenuScreenUiState3 mainMenuScreenUiState3 : this.items) {
         mainMenuScreenUiState3.invoke();
      }

      for (MainMenuScreen.MainMenuScreenUiState4 mainMenuScreenUiState4 : this.mainMenuScreenUiState4s) {
         mainMenuScreenUiState4.invoke();
      }

      for (MainMenuScreen.MainMenuScreenUiState2 mainMenuScreenUiState2 : this.mainMenuScreenUiState2s) {
         mainMenuScreenUiState2.invoke();
      }

      for (MainMenuScreen.MainMenuScreenUiState5 mainMenuScreenUiState5 : this.mainMenuScreenUiState5s) {
         mainMenuScreenUiState5.invoke();
      }
   }

   public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
      if (!this.flag3) {
         this.invoke(context);
      }

      this.invoke3(mouseX, mouseY, deltaTicks, false);
   }

   private void invoke(DrawContext drawContext) {
      if (drawContext != null && this.client != null && this.width > 0 && this.height > 0) {
         if (this.flag2) {
            drawContext.fillGradient(0, 0, this.width, this.height, compute4(this.intValue, 255), compute4(this.intValue2, 255));
         } else {
            try {
               if (!this.flag) {
                  this.client.getTextureManager().registerTexture(IDENTIFIER, new CubemapTexture(IDENTIFIER));
                  this.flag = true;
               }

               this.rotatingCubeMapRenderer.render(drawContext, this.width, this.height, true);
               drawContext.fillGradient(0, 0, this.width, this.height, compute4(this.intValue, 70), compute4(this.intValue2, 110));
            } catch (Throwable exception) {
               this.flag2 = true;
               ScreenRenderDiagnostics.invoke6("MainMenuPanorama", this, "panorama fallback failed", exception);
               drawContext.fillGradient(0, 0, this.width, this.height, compute4(this.intValue, 255), compute4(this.intValue2, 255));
            }
         }
      }
   }

   @Override
   public void invoke2(int i, int j, float f) {
      this.invoke3(i, j, f, true);
   }

   private void invoke3(int i, int j, float f, boolean bl) {
      Window window2 = this.client == null ? null : this.client.getWindow();
      if (window2 != null && !window2.hasZeroWidthOrHeight() && window2.getFramebufferWidth() > 0 && window2.getFramebufferHeight() > 0) {
         int intValue5 = window2.getFramebufferWidth();
         int intValue6 = window2.getFramebufferHeight();
         MainMenuRenderer.MainMenuRendererBounds mainMenuRendererBounds = MainMenuRenderer.resolve(MinecraftClient.getInstance(), intValue5, intValue6);
         if (mainMenuRendererBounds == null) {
            if (bl) {
               this.flag3 = false;
            }
         } else {
            intValue5 = mainMenuRendererBounds.width();
            intValue6 = mainMenuRendererBounds.height();
            long longValue = System.nanoTime();
            float floatValue = Math.max(0.001F, Math.min(0.05F, (float)(longValue - this.timestamp2) / 1.0E9F));
            this.timestamp2 = longValue;
            this.floatValue = (float)(longValue - this.timestamp) / 1.0E9F;
            this.invoke5();
            this.invoke6(window2, i, j, floatValue, longValue);
            this.invoke8(intValue5, intValue6, floatValue);
            this.invoke7();
            boolean flag = MenuModule.check(MenuModule.PARALLAKS_GLAVNOGO_MENYU);
            float floatValue2 = flag ? (this.floatValue2 / Math.max(1.0F, (float)intValue5) - 0.5F) * 2.0F : 0.0F;
            float floatValue3 = flag ? (this.floatValue3 / Math.max(1.0F, (float)intValue6) - 0.5F) * 2.0F : 0.0F;
            float floatValue4 = this.springIntegrator.measure(floatValue2, floatValue);
            float floatValue5 = this.springIntegrator2.measure(floatValue3, floatValue);
            this.invoke10(intValue5, intValue6, floatValue4, floatValue5, floatValue);
            int intValue7 = GL11.glGetInteger(36006);
            WeakGlCompatibility.invoke(intValue7);
            MainMenuScreen.MainMenuScreenTimedEntry mainMenuScreenTimedEntry = this.resolve2(intValue5, intValue6, intValue7, floatValue4, floatValue5, longValue);
            this.mainMenuScreenTimedEntry = mainMenuScreenTimedEntry;
            if (bl) {
               boolean flag2 = false;
               FramebufferUtils.GlStateSnapshot glStateSnapshot = FramebufferUtils.captureGlState();

               try {
                  flag2 = this.mainMenuRenderer.check(mainMenuScreenTimedEntry);
               } finally {
                  FramebufferUtils.restoreGlState(glStateSnapshot);
               }

               this.flag3 = flag2;
               this.invoke15(mainMenuScreenTimedEntry);
            }
         }
      } else {
         if (bl) {
            this.flag3 = false;
         }
      }
   }

   public void renderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
   }

   public void renderInGameBackground(DrawContext context) {
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (button == 0 && this.client != null && this.client.getWindow() != null) {
         float floatValue6 = this.measure2(this.client.getWindow(), mouseX);
         float floatValue7 = this.measure3(this.client.getWindow(), mouseY);
         if (this.check(floatValue6, floatValue7)) {
            this.flag8 = !this.flag8;
            this.floatValue18 = 1.0F;
            this.floatValue19 = 1.0F;
            return true;
         } else if (this.check3(floatValue6, floatValue7)) {
            this.flag10 = true;
            this.invoke18(floatValue6);
            return true;
         } else if (this.flag8 && !this.check2(floatValue6, floatValue7)) {
            this.flag8 = false;
            return true;
         } else {
            for (MainMenuScreen.MainMenuScreenUiState3 mainMenuScreenUiState32 : this.items) {
               if (mainMenuScreenUiState32.check(floatValue6, floatValue7)) {
                  mainMenuScreenUiState32.floatValue10 = 1.0F;
                  mainMenuScreenUiState32.floatValue11 = 1.0F;
                  this.invoke21(mainMenuScreenUiState32.mainMenuScreenState);
                  return true;
               }
            }

            return true;
         }
      } else {
         return super.mouseClicked(mouseX, mouseY, button);
      }
   }

   public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
      if (button == 0 && this.flag10 && this.client != null && this.client.getWindow() != null) {
         this.invoke18(this.measure2(this.client.getWindow(), mouseX));
         return true;
      } else if (button == 0 && this.intValue6 >= 0 && this.client != null && this.client.getWindow() != null) {
         this.invoke12(this.intValue6, this.measure2(this.client.getWindow(), mouseX));
         return true;
      } else {
         return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
      }
   }

   public boolean mouseReleased(double mouseX, double mouseY, int button) {
      if (button == 0 && this.flag10) {
         this.flag10 = false;
         invoke14();
         return true;
      } else if (button == 0 && this.intValue6 >= 0) {
         this.intValue6 = -1;
         invoke14();
         return true;
      } else {
         return super.mouseReleased(mouseX, mouseY, button);
      }
   }

   public boolean shouldPause() {
      return false;
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (keyCode == 256 && this.flag8) {
         this.flag8 = false;
         return true;
      } else {
         return super.keyPressed(keyCode, scanCode, modifiers);
      }
   }

   public boolean shouldCloseOnEsc() {
      return false;
   }

   public void removed() {
      this.mainMenuRenderer.close();
      super.removed();
   }

   public void invoke4(int i, int j) {
      try {
         this.mainMenuRenderer.invoke13(i, j);
      } catch (Throwable exception2) {
      }
   }

   private void invoke5() {
      Theme theme = WildClient.INSTANCE != null && WildClient.INSTANCE.themeManager != null ? WildClient.INSTANCE.themeManager.getTheme() : Theme.AURORA;
      this.theme = theme;
      ThemePalette.Swatch swatch = THEME_PALETTE.resolve3(theme);
      if (swatch != null) {
         this.intValue = swatch.getIntValue();
         this.intValue2 = swatch.getIntValue2();
         this.flag7 = swatch.isFlag();
      } else {
         this.flag7 = false;
         Color color = theme.getColor();
         this.intValue = 0xFF000000 | color.getRGB() & 16777215;
         float[] floatValues = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
         this.intValue2 = 0xFF000000
            | Color.HSBtoRGB((floatValues[0] + 0.075F) % 1.0F, Math.min(1.0F, floatValues[1] * 1.08F), Math.min(1.0F, floatValues[2] * 1.18F)) & 16777215;
      }
   }

   private void invoke6(Window window, int i, int j, float f, long l) {
      float floatValue8 = this.measure2(window, i);
      float floatValue9 = this.measure3(window, j);
      if (!this.flag4) {
         this.floatValue2 = floatValue8;
         this.floatValue3 = floatValue9;
         this.floatValue4 = 0.0F;
         this.floatValue5 = 0.0F;
         this.flag4 = true;
      } else {
         float floatValue10 = floatValue8 - this.floatValue2;
         float floatValue11 = floatValue9 - this.floatValue3;
         float floatValue12 = (float)Math.sqrt(floatValue10 * floatValue10 + floatValue11 * floatValue11);
         if (floatValue12 > 0.2F) {
            this.floatValue4 = measure9(floatValue10 / Math.max(1.0F, (float)window.getFramebufferWidth()) / f, -3.0F, 3.0F);
            this.floatValue5 = measure9(floatValue11 / Math.max(1.0F, (float)window.getFramebufferHeight()) / f, -3.0F, 3.0F);
         } else {
            float floatValue13 = (float)Math.pow(8.0E-4F, f);
            this.floatValue4 *= floatValue13;
            this.floatValue5 *= floatValue13;
         }

         this.floatValue2 = floatValue8;
         this.floatValue3 = floatValue9;
         if (floatValue12 > 1.5F) {
            this.timestamp3 = l;
         }
      }
   }

   private void invoke7() {
      if (MenuVisualPreferences.check3() && MenuModule.check(MenuModule.SLED_KURSORA_VMENYU)) {
         if (!this.flag6) {
            this.floatValue10 = this.floatValue6;
            this.floatValue11 = this.floatValue7;
            this.flag6 = true;
            this.invoke9(this.floatValue6, this.floatValue7, 0.36F);
         } else {
            float floatValue14 = measure6(this.floatValue6 - this.floatValue10, this.floatValue7 - this.floatValue11);
            if (floatValue14 > 5.5F) {
               this.invoke9(this.floatValue6, this.floatValue7, measure9(floatValue14 / 180.0F, 0.12F, 0.54F));
               this.floatValue10 = this.floatValue6;
               this.floatValue11 = this.floatValue7;
            }
         }
      }
   }

   private void invoke8(int i, int j, float f) {
      if (!this.flag5) {
         this.floatValue6 = this.floatValue2;
         this.floatValue7 = this.floatValue3;
         this.floatValue8 = 0.0F;
         this.floatValue9 = 0.0F;
         this.flag5 = true;
      } else {
         float floatValue15 = this.floatValue6;
         float floatValue16 = this.floatValue7;
         float floatValue17 = measure6(this.floatValue2 - this.floatValue6, this.floatValue3 - this.floatValue7);
         float floatValue18 = (1.0F - (float)Math.pow(3.5E-5F, f)) * (0.72F + measure9(floatValue17 / 520.0F, 0.0F, 0.42F));
         this.floatValue6 = this.floatValue6 + (this.floatValue2 - this.floatValue6) * measure9(floatValue18, 0.05F, 0.26F);
         this.floatValue7 = this.floatValue7 + (this.floatValue3 - this.floatValue7) * measure9(floatValue18, 0.05F, 0.26F);
         float floatValue19 = measure9((this.floatValue6 - floatValue15) / Math.max(1.0F, (float)i) / f, -1.8F, 1.8F);
         float floatValue20 = measure9((this.floatValue7 - floatValue16) / Math.max(1.0F, (float)j) / f, -1.8F, 1.8F);
         float floatValue21 = 1.0F - (float)Math.pow(0.0025F, f);
         this.floatValue8 = this.floatValue8 + (floatValue19 - this.floatValue8) * floatValue21;
         this.floatValue9 = this.floatValue9 + (floatValue20 - this.floatValue9) * floatValue21;
      }
   }

   private void invoke9(float f, float g, float h) {
      int intValue8 = 0;
      float floatValue22 = -1.0F;

      for (int intValue9 = 0; intValue9 < this.mainMenuScreenState4s.length; intValue9++) {
         float floatValue23 = this.floatValue - this.mainMenuScreenState4s[intValue9].floatValue3;
         if (this.mainMenuScreenState4s[intValue9].floatValue4 <= 0.0F) {
            intValue8 = intValue9;
            break;
         }

         if (floatValue23 > floatValue22) {
            floatValue22 = floatValue23;
            intValue8 = intValue9;
         }
      }

      this.mainMenuScreenState4s[intValue8].floatValue = f;
      this.mainMenuScreenState4s[intValue8].floatValue2 = g;
      this.mainMenuScreenState4s[intValue8].floatValue3 = this.floatValue;
      this.mainMenuScreenState4s[intValue8].floatValue4 = h;
   }

   private void invoke10(int i, int j, float f, float g, float h) {
      float floatValue24 = measure4(i, j);
      this.invoke11(i, j, floatValue24, h);
      float floatValue25 = measure9(i * 0.226F, 266.0F * floatValue24, 420.0F * floatValue24);
      float floatValue26 = 54.0F * floatValue24;
      float floatValue27 = 13.0F * floatValue24;
      float floatValue28 = this.items.size() * floatValue26 + (this.items.size() - 1) * floatValue27;
      float floatValue29 = i * 0.5F + f * 2.1F * floatValue24;
      float floatValue30 = j * 0.52F - floatValue28 * 0.38F + g * 1.25F * floatValue24;
      float floatValue31 = Math.min(floatValue26 * 0.34F, 18.0F * floatValue24);
      float floatValue32 = 20.0F * floatValue24;

      for (int intValue10 = 0; intValue10 < this.items.size(); intValue10++) {
         MainMenuScreen.MainMenuScreenUiState3 mainMenuScreenUiState33 = this.items.get(intValue10);
         mainMenuScreenUiState33.floatValue5 = floatValue25;
         mainMenuScreenUiState33.floatValue6 = floatValue26;
         mainMenuScreenUiState33.floatValue = floatValue29 - floatValue25 * 0.5F;
         mainMenuScreenUiState33.floatValue2 = floatValue30 + intValue10 * (floatValue26 + floatValue27);
         mainMenuScreenUiState33.floatValue7 = floatValue31;
         float floatValue33 = measure5(
            this.floatValue2, this.floatValue3, mainMenuScreenUiState33.floatValue, mainMenuScreenUiState33.floatValue2, mainMenuScreenUiState33.floatValue5, mainMenuScreenUiState33.floatValue6, mainMenuScreenUiState33.floatValue7
         );
         boolean flag3 = floatValue33 <= 0.0F;
         float floatValue34 = 1.0F - measure8(measure9(Math.max(0.0F, floatValue33) / Math.max(1.0F, floatValue32), 0.0F, 1.0F));
         float floatValue35 = flag3 ? 1.0F : 0.0F;
         mainMenuScreenUiState33.floatValue8 = mainMenuScreenUiState33.floatValue8 + (floatValue35 - mainMenuScreenUiState33.floatValue8) * (1.0F - (float)Math.pow(8.0E-5F, h));
         mainMenuScreenUiState33.floatValue9 = mainMenuScreenUiState33.floatValue9 + (floatValue34 - mainMenuScreenUiState33.floatValue9) * (1.0F - (float)Math.pow(1.2E-4F, h));
         mainMenuScreenUiState33.floatValue10 = mainMenuScreenUiState33.floatValue10 + (0.0F - mainMenuScreenUiState33.floatValue10) * (1.0F - (float)Math.pow(2.0E-5F, h));
         mainMenuScreenUiState33.floatValue11 = mainMenuScreenUiState33.floatValue11 + (0.0F - mainMenuScreenUiState33.floatValue11) * (1.0F - (float)Math.pow(8.0E-6F, h));
         float floatValue36 = measure9((this.floatValue6 - mainMenuScreenUiState33.floatValue) / Math.max(1.0F, mainMenuScreenUiState33.floatValue5), 0.0F, 1.0F);
         float floatValue37 = measure9((this.floatValue7 - mainMenuScreenUiState33.floatValue2) / Math.max(1.0F, mainMenuScreenUiState33.floatValue6), 0.0F, 1.0F);
         float floatValue38 = 1.0F - (float)Math.pow(1.8E-4F, h);
         mainMenuScreenUiState33.floatValue13 = mainMenuScreenUiState33.floatValue13 + (floatValue36 - mainMenuScreenUiState33.floatValue13) * floatValue38;
         mainMenuScreenUiState33.floatValue14 = mainMenuScreenUiState33.floatValue14 + (floatValue37 - mainMenuScreenUiState33.floatValue14) * floatValue38;
         float floatValue39 = 1.0F + mainMenuScreenUiState33.floatValue9 * 0.05F - mainMenuScreenUiState33.floatValue10 * 0.07F;
         mainMenuScreenUiState33.floatValue12 = mainMenuScreenUiState33.springIntegrator.measure(floatValue39, h);
         float floatValue40 = (mainMenuScreenUiState33.floatValue13 - 0.5F) * 8.0F * floatValue24 * mainMenuScreenUiState33.floatValue9;
         float floatValue41 = (mainMenuScreenUiState33.floatValue14 - 0.5F) * 4.4F * floatValue24 * mainMenuScreenUiState33.floatValue9 - mainMenuScreenUiState33.floatValue8 * 1.1F * floatValue24;
         mainMenuScreenUiState33.floatValue3 = mainMenuScreenUiState33.floatValue + floatValue40;
         mainMenuScreenUiState33.floatValue4 = mainMenuScreenUiState33.floatValue2 + floatValue41;
         mainMenuScreenUiState33.floatValue15 = measure9(
            measure6(this.floatValue8, this.floatValue9) * 0.7F * mainMenuScreenUiState33.floatValue9 + Math.abs(mainMenuScreenUiState33.springIntegrator.getFloatValue2()) * 0.045F,
            0.0F,
            1.0F
         );
      }
   }

   private void invoke11(int i, int j, float f, float g) {
      this.floatValue28 = this.springIntegrator3.measure(this.flag8 ? 1.0F : 0.0F, g);
      this.floatValue14 = measure9(218.0F * f, 168.0F * f, 282.0F * f);
      this.floatValue15 = 46.0F * f;
      this.floatValue16 = Math.min(this.floatValue15 * 0.34F, 18.0F * f);
      this.floatValue12 = 28.0F * f;
      this.floatValue13 = j - this.floatValue15 - 28.0F * f;
      float floatValue42 = measure5(
         this.floatValue2, this.floatValue3, this.floatValue12, this.floatValue13, this.floatValue14, this.floatValue15, this.floatValue16
      );
      this.flag9 = floatValue42 <= 0.0F;
      float floatValue43 = this.flag9 ? 1.0F : 0.0F;
      this.floatValue17 = this.floatValue17 + (floatValue43 - this.floatValue17) * (1.0F - (float)Math.pow(8.0E-5F, g));
      this.floatValue18 = this.floatValue18 + (0.0F - this.floatValue18) * (1.0F - (float)Math.pow(2.0E-5F, g));
      this.floatValue19 = this.floatValue19 + (0.0F - this.floatValue19) * (1.0F - (float)Math.pow(8.0E-6F, g));
      float floatValue44 = 1.0F - (float)Math.pow(1.8E-4F, g);
      this.floatValue21 = this.floatValue21
         + (measure9((this.floatValue6 - this.floatValue12) / Math.max(1.0F, this.floatValue14), 0.0F, 1.0F) - this.floatValue21) * floatValue44;
      this.floatValue22 = this.floatValue22
         + (measure9((this.floatValue7 - this.floatValue13) / Math.max(1.0F, this.floatValue15), 0.0F, 1.0F) - this.floatValue22) * floatValue44;
      this.floatValue20 = this.springIntegrator4.measure(1.0F + this.floatValue17 * 0.035F - this.floatValue18 * 0.055F, g);
      this.floatValue25 = measure9(i * 0.35F, 620.0F * f, 760.0F * f);
      float floatValue45 = 24.0F * f;
      float floatValue46 = 14.0F * f;
      float floatValue47 = 13.0F * f;
      float floatValue48 = (this.floatValue25 - floatValue45 * 2.0F - floatValue46) * 0.5F;
      float floatValue49 = 66.0F * f;
      float floatValue50 = 30.0F * f;
      float floatValue51 = 8.0F * f;
      float floatValue52 = 16.0F * f;
      float floatValue53 = 190.0F * f;
      float floatValue54 = 26.0F * f;
      float floatValue55 = 0.0F;

      for (int intValue11 = 0; intValue11 < INT_VALUE_4; intValue11++) {
         MainMenuScreen.MainMenuScreenUiState5 mainMenuScreenUiState52 = this.mainMenuScreenUiState5s[intValue11];
         float floatValue56 = mainMenuScreenUiState52.springIntegrator.measure(mainMenuScreenUiState52.flag ? 0.0F : 1.0F, g);
         mainMenuScreenUiState52.floatValue = floatValue56;
         int intValue12 = mainMenuScreenUiState52.mainMenuScreenDisplayEntry2.rows();
         float floatValue57 = intValue12 * floatValue49 + Math.max(0, intValue12 - 1) * floatValue47;
         floatValue55 += floatValue50 + floatValue51 + floatValue57 * floatValue56 + floatValue52;
      }

      this.floatValue26 = measure9(floatValue53 + floatValue55 + floatValue54, 360.0F * f, j - 120.0F * f);
      this.floatValue26 = measure9(196.0F * f, 176.0F * f, j - 120.0F * f);
      this.floatValue27 = 26.0F * f;
      float floatValue58 = 28.0F * f;
      float floatValue59 = -this.floatValue25 - 26.0F * f;
      float floatValue60 = 68.0F * f;
      float floatValue61 = j - this.floatValue26 - 60.0F * f;
      this.floatValue23 = floatValue59 + (floatValue58 - floatValue59) * this.floatValue28;
      this.floatValue24 = measure9(j * 0.5F - this.floatValue26 * 0.5F, floatValue60, Math.max(floatValue60, floatValue61));
      float floatValue62 = 10.0F * f;
      float floatValue63 = this.floatValue23 + floatValue45;
      float floatValue64 = (this.floatValue25 - floatValue45 * 2.0F - floatValue62 * (INT_VALUE_2 - 1)) / INT_VALUE_2;
      float floatValue65 = 58.0F * f;
      float floatValue66 = this.floatValue24 + 92.0F * f;
      this.intValue4 = this.floatValue28 > 0.18F ? this.compute(this.floatValue2, this.floatValue3) : -1;
      this.intValue5 = this.floatValue28 > 0.18F ? this.compute2(this.floatValue2, this.floatValue3) : -1;
      int intValue13 = this.compute3();

      for (int intValue14 = 0; intValue14 < INT_VALUE_2; intValue14++) {
         MainMenuScreen.MainMenuScreenUiState4 mainMenuScreenUiState42 = this.mainMenuScreenUiState4s[intValue14];
         mainMenuScreenUiState42.floatValue = floatValue63 + intValue14 * (floatValue64 + floatValue62);
         mainMenuScreenUiState42.floatValue2 = floatValue66;
         mainMenuScreenUiState42.floatValue5 = floatValue64;
         mainMenuScreenUiState42.floatValue6 = floatValue65;
         mainMenuScreenUiState42.floatValue9 = Math.min(floatValue65 * 0.3F, 16.0F * f);
         boolean flag4 = intValue14 == this.intValue4;
         boolean flag5 = mainMenuScreenUiState42.mainMenuScreenDisplayEntry.preset().ordinal() == intValue13;
         float floatValue67 = flag4 ? 1.0F : 0.0F;
         mainMenuScreenUiState42.floatValue10 = mainMenuScreenUiState42.floatValue10 + (floatValue67 - mainMenuScreenUiState42.floatValue10) * (1.0F - (float)Math.pow(8.0E-5F, g));
         mainMenuScreenUiState42.floatValue11 = mainMenuScreenUiState42.floatValue11 + (0.0F - mainMenuScreenUiState42.floatValue11) * (1.0F - (float)Math.pow(2.0E-5F, g));
         mainMenuScreenUiState42.floatValue12 = mainMenuScreenUiState42.floatValue12 + (0.0F - mainMenuScreenUiState42.floatValue12) * (1.0F - (float)Math.pow(8.0E-6F, g));
         float floatValue68 = 1.0F - (float)Math.pow(1.8E-4F, g);
         mainMenuScreenUiState42.floatValue14 = mainMenuScreenUiState42.floatValue14
            + (measure9((this.floatValue6 - mainMenuScreenUiState42.floatValue) / Math.max(1.0F, mainMenuScreenUiState42.floatValue5), 0.0F, 1.0F) - mainMenuScreenUiState42.floatValue14) * floatValue68;
         mainMenuScreenUiState42.floatValue15 = mainMenuScreenUiState42.floatValue15
            + (measure9((this.floatValue7 - mainMenuScreenUiState42.floatValue2) / Math.max(1.0F, mainMenuScreenUiState42.floatValue6), 0.0F, 1.0F) - mainMenuScreenUiState42.floatValue15) * floatValue68;
         float floatValue69 = 1.0F + mainMenuScreenUiState42.floatValue10 * 0.035F + (flag5 ? 0.01F : 0.0F) - mainMenuScreenUiState42.floatValue11 * 0.055F;
         mainMenuScreenUiState42.floatValue13 = mainMenuScreenUiState42.springIntegrator.measure(floatValue69, g);
         float floatValue70 = mainMenuScreenUiState42.floatValue5 * (mainMenuScreenUiState42.floatValue13 - 1.0F) * 0.5F;
         float floatValue71 = mainMenuScreenUiState42.floatValue6 * (mainMenuScreenUiState42.floatValue13 - 1.0F) * 0.5F;
         mainMenuScreenUiState42.floatValue3 = mainMenuScreenUiState42.floatValue - floatValue70;
         mainMenuScreenUiState42.floatValue4 = mainMenuScreenUiState42.floatValue2 - floatValue71;
         mainMenuScreenUiState42.floatValue7 = mainMenuScreenUiState42.floatValue5 + floatValue70 * 2.0F;
         mainMenuScreenUiState42.floatValue8 = mainMenuScreenUiState42.floatValue6 + floatValue71 * 2.0F;
         mainMenuScreenUiState42.floatValue16 = flag5 ? 1.0F : 0.0F;
         mainMenuScreenUiState42.floatValue17 = measure9(
            measure6(this.floatValue8, this.floatValue9) * 0.62F * mainMenuScreenUiState42.floatValue10 + Math.abs(mainMenuScreenUiState42.springIntegrator.getFloatValue2()) * 0.035F,
            0.0F,
            1.0F
         );
      }

      float floatValue72 = this.floatValue24 + floatValue53;
      this.intValue3 = -1;

      for (int intValue15 = 0; intValue15 < INT_VALUE_4; intValue15++) {
         MainMenuScreen.MainMenuScreenUiState5 mainMenuScreenUiState53 = this.mainMenuScreenUiState5s[intValue15];
         mainMenuScreenUiState53.floatValue3 = this.floatValue23 + floatValue45;
         mainMenuScreenUiState53.floatValue4 = floatValue72;
         mainMenuScreenUiState53.floatValue5 = this.floatValue25 - floatValue45 * 2.0F;
         mainMenuScreenUiState53.floatValue6 = floatValue50;
         boolean flag6 = this.floatValue28 > 0.18F
            && check4(this.floatValue2, this.floatValue3, mainMenuScreenUiState53.floatValue3, mainMenuScreenUiState53.floatValue4, mainMenuScreenUiState53.floatValue5, mainMenuScreenUiState53.floatValue6);
         if (flag6) {
            this.intValue3 = intValue15;
         }

         float floatValue73 = flag6 ? 1.0F : 0.0F;
         mainMenuScreenUiState53.floatValue2 = mainMenuScreenUiState53.floatValue2 + (floatValue73 - mainMenuScreenUiState53.floatValue2) * (1.0F - (float)Math.pow(8.0E-5F, g));
         floatValue72 += floatValue50 + floatValue51;
         int intValue16 = mainMenuScreenUiState53.mainMenuScreenDisplayEntry2.rows();
         float floatValue74 = intValue16 * floatValue49 + Math.max(0, intValue16 - 1) * floatValue47;

         for (int intValue17 = 0; intValue17 < mainMenuScreenUiState53.mainMenuScreenDisplayEntry2.count(); intValue17++) {
            int intValue18 = mainMenuScreenUiState53.mainMenuScreenDisplayEntry2.start() + intValue17;
            MainMenuScreen.MainMenuScreenUiState2 mainMenuScreenUiState22 = this.mainMenuScreenUiState2s[intValue18];
            int intValue19 = intValue17 & 1;
            int intValue20 = intValue17 >> 1;
            mainMenuScreenUiState22.floatValue19 = mainMenuScreenUiState53.floatValue;
            mainMenuScreenUiState22.floatValue = this.floatValue23 + floatValue45 + intValue19 * (floatValue48 + floatValue46);
            mainMenuScreenUiState22.floatValue2 = floatValue72 + intValue20 * (floatValue49 + floatValue47);
            mainMenuScreenUiState22.floatValue5 = floatValue48;
            mainMenuScreenUiState22.floatValue6 = floatValue49;
            mainMenuScreenUiState22.floatValue9 = Math.min(floatValue49 * 0.28F, 18.0F * f);
            boolean flag7 = (intValue18 == this.intValue5 || intValue18 == this.intValue6) && mainMenuScreenUiState53.floatValue > 0.5F;
            boolean flag8 = mainMenuScreenUiState22.mainMenuScreenUiState.mainMenuScreenState2 == MainMenuScreen.MainMenuScreenState2.TOGGLE ? mainMenuScreenUiState22.mainMenuScreenUiState.booleanSetting.isEnabled() : true;
            float floatValue75 = flag7 ? 1.0F : 0.0F;
            mainMenuScreenUiState22.floatValue10 = mainMenuScreenUiState22.floatValue10 + (floatValue75 - mainMenuScreenUiState22.floatValue10) * (1.0F - (float)Math.pow(8.0E-5F, g));
            mainMenuScreenUiState22.floatValue11 = mainMenuScreenUiState22.floatValue11 + (0.0F - mainMenuScreenUiState22.floatValue11) * (1.0F - (float)Math.pow(2.0E-5F, g));
            mainMenuScreenUiState22.floatValue12 = mainMenuScreenUiState22.floatValue12 + (0.0F - mainMenuScreenUiState22.floatValue12) * (1.0F - (float)Math.pow(8.0E-6F, g));
            float floatValue76 = 1.0F - (float)Math.pow(1.8E-4F, g);
            mainMenuScreenUiState22.floatValue14 = mainMenuScreenUiState22.floatValue14
               + (measure9((this.floatValue6 - mainMenuScreenUiState22.floatValue) / Math.max(1.0F, mainMenuScreenUiState22.floatValue5), 0.0F, 1.0F) - mainMenuScreenUiState22.floatValue14) * floatValue76;
            mainMenuScreenUiState22.floatValue15 = mainMenuScreenUiState22.floatValue15
               + (measure9((this.floatValue7 - mainMenuScreenUiState22.floatValue2) / Math.max(1.0F, mainMenuScreenUiState22.floatValue6), 0.0F, 1.0F) - mainMenuScreenUiState22.floatValue15) * floatValue76;
            float floatValue77 = 1.0F + mainMenuScreenUiState22.floatValue10 * 0.022F - mainMenuScreenUiState22.floatValue11 * 0.038F;
            mainMenuScreenUiState22.floatValue13 = mainMenuScreenUiState22.springIntegrator.measure(floatValue77, g);
            float floatValue78 = mainMenuScreenUiState22.floatValue5 * (mainMenuScreenUiState22.floatValue13 - 1.0F) * 0.5F;
            float floatValue79 = mainMenuScreenUiState22.floatValue6 * (mainMenuScreenUiState22.floatValue13 - 1.0F) * 0.5F;
            mainMenuScreenUiState22.floatValue3 = mainMenuScreenUiState22.floatValue - floatValue78;
            mainMenuScreenUiState22.floatValue4 = mainMenuScreenUiState22.floatValue2 - floatValue79;
            mainMenuScreenUiState22.floatValue7 = mainMenuScreenUiState22.floatValue5 + floatValue78 * 2.0F;
            mainMenuScreenUiState22.floatValue8 = mainMenuScreenUiState22.floatValue6 + floatValue79 * 2.0F;
            mainMenuScreenUiState22.floatValue16 = flag8 ? 1.0F : 0.0F;
            mainMenuScreenUiState22.floatValue17 = measure(mainMenuScreenUiState22.mainMenuScreenUiState);
            mainMenuScreenUiState22.floatValue18 = measure9(
               measure6(this.floatValue8, this.floatValue9) * 0.48F * mainMenuScreenUiState22.floatValue10 + Math.abs(mainMenuScreenUiState22.springIntegrator.getFloatValue2()) * 0.032F,
               0.0F,
               1.0F
            );
         }

         floatValue72 += floatValue74 * mainMenuScreenUiState53.floatValue + floatValue52;
      }
   }

   private boolean check(float f, float g) {
      return this.floatValue14 > 0.0F
         && measure5(f, g, this.floatValue12, this.floatValue13, this.floatValue14, this.floatValue15, this.floatValue16) <= 0.0F;
   }

   private boolean check2(float f, float g) {
      return this.floatValue28 > 0.05F
         && measure5(f, g, this.floatValue23, this.floatValue24, this.floatValue25, this.floatValue26, this.floatValue27) <= 0.0F;
   }

   private int compute(float f, float g) {
      if (!this.flag8 && this.floatValue28 <= 0.18F) {
         return -1;
      } else {
         for (int intValue21 = 0; intValue21 < INT_VALUE_2; intValue21++) {
            MainMenuScreen.MainMenuScreenUiState4 mainMenuScreenUiState43 = this.mainMenuScreenUiState4s[intValue21];
            if (mainMenuScreenUiState43.floatValue5 > 0.0F
               && measure5(f, g, mainMenuScreenUiState43.floatValue, mainMenuScreenUiState43.floatValue2, mainMenuScreenUiState43.floatValue5, mainMenuScreenUiState43.floatValue6, mainMenuScreenUiState43.floatValue9) <= 0.0F) {
               return intValue21;
            }
         }

         return -1;
      }
   }

   private int compute2(float f, float g) {
      if (!this.flag8 && this.floatValue28 <= 0.18F) {
         return -1;
      } else {
         for (int intValue22 = 0; intValue22 < INT_VALUE_3; intValue22++) {
            if (!(this.measure7(intValue22) < 0.5F)) {
               MainMenuScreen.MainMenuScreenUiState2 mainMenuScreenUiState23 = this.mainMenuScreenUiState2s[intValue22];
               if (mainMenuScreenUiState23.floatValue5 > 0.0F
                  && measure5(f, g, mainMenuScreenUiState23.floatValue, mainMenuScreenUiState23.floatValue2, mainMenuScreenUiState23.floatValue5, mainMenuScreenUiState23.floatValue6, mainMenuScreenUiState23.floatValue9) <= 0.0F) {
                  return intValue22;
               }
            }
         }

         return -1;
      }
   }

   private void invoke12(int i, float f) {
      if (i >= 0 && i < INT_VALUE_3) {
         MainMenuScreen.MainMenuScreenUiState2 mainMenuScreenUiState24 = this.mainMenuScreenUiState2s[i];
         if (mainMenuScreenUiState24.mainMenuScreenUiState.mainMenuScreenState2 == MainMenuScreen.MainMenuScreenState2.SLIDER && mainMenuScreenUiState24.mainMenuScreenUiState.numberSetting != null) {
            float floatValue80 = measure9(mainMenuScreenUiState24.floatValue6 / 66.0F, 0.72F, 1.38F);
            float floatValue81 = mainMenuScreenUiState24.floatValue + 16.0F * floatValue80;
            float floatValue82 = Math.max(1.0F, mainMenuScreenUiState24.floatValue5 - 32.0F * floatValue80);
            float floatValue83 = measure9((f - floatValue81) / floatValue82, 0.0F, 1.0F);
            NumberSetting numberSetting = mainMenuScreenUiState24.mainMenuScreenUiState.numberSetting;
            float floatValue84 = numberSetting.minimum + (numberSetting.maximum - numberSetting.minimum) * floatValue83;
            if (numberSetting.step > 0.0F) {
               floatValue84 = Math.round(floatValue84 / numberSetting.step) * numberSetting.step;
            }

            numberSetting.invoke(floatValue84);
            mainMenuScreenUiState24.floatValue17 = measure(mainMenuScreenUiState24.mainMenuScreenUiState);
            invoke13();
         }
      }
   }

   private static float measure(MainMenuScreen.MainMenuScreenUiState mainMenuScreenUiState) {
      if (mainMenuScreenUiState == null) {
         return 0.0F;
      } else if (mainMenuScreenUiState.mainMenuScreenState2 != MainMenuScreen.MainMenuScreenState2.TOGGLE) {
         return mainMenuScreenUiState.numberSetting != null && !(mainMenuScreenUiState.numberSetting.maximum <= mainMenuScreenUiState.numberSetting.minimum)
            ? measure9(
               (mainMenuScreenUiState.numberSetting.getValue() - mainMenuScreenUiState.numberSetting.minimum)
                  / (mainMenuScreenUiState.numberSetting.maximum - mainMenuScreenUiState.numberSetting.minimum),
               0.0F,
               1.0F
            )
            : 0.0F;
      } else {
         return mainMenuScreenUiState.booleanSetting != null && mainMenuScreenUiState.booleanSetting.isEnabled() ? 1.0F : 0.0F;
      }
   }

   private static String resolve(MainMenuScreen.MainMenuScreenUiState mainMenuScreenUiState6) {
      if (mainMenuScreenUiState6.mainMenuScreenState2 != MainMenuScreen.MainMenuScreenState2.TOGGLE) {
         float floatValue85 = mainMenuScreenUiState6.numberSetting == null ? 0.0F : mainMenuScreenUiState6.numberSetting.getValue();

         return switch (mainMenuScreenUiState6.mainMenuScreenState3) {
            case PIXELS -> Math.round(floatValue85) + " px";
            case MULTIPLIER -> String.format(Locale.ROOT, "%.2fx", floatValue85);
            case PERCENT -> Math.round(floatValue85 * 100.0F) + "%";
            case SCALE -> Math.round(floatValue85 * 100.0F) + "%";
         };
      } else {
         return mainMenuScreenUiState6.booleanSetting != null && mainMenuScreenUiState6.booleanSetting.isEnabled() ? "ON" : "OFF";
      }
   }

   private static void invoke13() {
      MenuModule.invoke3();
   }

   private static void invoke14() {
      if (WildClient.INSTANCE != null && WildClient.INSTANCE.configManager != null) {
         WildClient.INSTANCE.configManager.scheduleSave();
      }
   }

   private MainMenuScreen.MainMenuScreenTimedEntry resolve2(int i, int j, int k, float f, float g, long l) {
      float floatValue86 = Math.max(0.0F, (float)(l - this.timestamp3) / 1.0E9F);
      float floatValue87 = measure9(measure6(this.floatValue8, this.floatValue9), 0.0F, 3.0F);
      float floatValue88 = Math.max((float)Math.exp(-floatValue86 * 1.45F), measure9(floatValue87 * 0.3F, 0.0F, 1.0F));
      float floatValue89 = measure8(measure9(this.floatValue / 1.25F, 0.0F, 1.0F));
      float floatValue90 = measure4(i, j);
      float floatValue91 = 0.0F;
      ArrayList arrayList = new ArrayList(this.items.size());

      for (int intValue23 = 0; intValue23 < this.items.size(); intValue23++) {
         MainMenuScreen.MainMenuScreenUiState3 mainMenuScreenUiState34 = this.items.get(intValue23);
         float floatValue92 = (this.floatValue - 0.18F - intValue23 * 0.058F) / 1.08F;
         float floatValue93 = measure8(measure9(floatValue92, 0.0F, 1.0F));
         float floatValue94 = (1.0F - floatValue93) * 14.0F * floatValue90;
         floatValue91 = Math.max(floatValue91, mainMenuScreenUiState34.floatValue11);
         arrayList.add(
            new MainMenuScreen.MainMenuScreenBounds(
               mainMenuScreenUiState34.text,
               mainMenuScreenUiState34.floatValue3,
               mainMenuScreenUiState34.floatValue4 + floatValue94,
               mainMenuScreenUiState34.floatValue5,
               mainMenuScreenUiState34.floatValue6,
               mainMenuScreenUiState34.floatValue7,
               mainMenuScreenUiState34.floatValue8,
               mainMenuScreenUiState34.floatValue9,
               mainMenuScreenUiState34.floatValue10,
               floatValue93,
               mainMenuScreenUiState34.floatValue11,
               46.0F * floatValue90,
               mainMenuScreenUiState34.floatValue12,
               mainMenuScreenUiState34.floatValue13,
               mainMenuScreenUiState34.floatValue14,
               mainMenuScreenUiState34.floatValue15
            )
         );
      }

      floatValue91 = Math.max(floatValue91, this.floatValue19);
      MainMenuScreen.MainMenuScreenBounds6 mainMenuScreenBounds6 = new MainMenuScreen.MainMenuScreenBounds6(
         this.floatValue12,
         this.floatValue13,
         this.floatValue14,
         this.floatValue15,
         this.floatValue16,
         this.floatValue17,
         this.floatValue18,
         measure8(measure9(this.floatValue / 1.18F, 0.0F, 1.0F)),
         this.floatValue19,
         this.floatValue20,
         this.floatValue21,
         this.floatValue22,
         this.floatValue28
      );
      MainMenuScreen.MainMenuScreenBounds5 mainMenuScreenBounds5 = new MainMenuScreen.MainMenuScreenBounds5(
         this.floatValue23, this.floatValue24, this.floatValue25, this.floatValue26, this.floatValue27, this.floatValue28, this.flag8 ? 1.0F : 0.0F
      );
      ArrayList arrayList2 = new ArrayList(INT_VALUE_2);

      for (int intValue24 = 0; intValue24 < INT_VALUE_2; intValue24++) {
         MainMenuScreen.MainMenuScreenUiState4 mainMenuScreenUiState44 = this.mainMenuScreenUiState4s[intValue24];
         floatValue91 = Math.max(floatValue91, mainMenuScreenUiState44.floatValue12);
         arrayList2.add(
            new MainMenuScreen.MainMenuScreenBounds4(
               mainMenuScreenUiState44.mainMenuScreenDisplayEntry.label(),
               mainMenuScreenUiState44.mainMenuScreenDisplayEntry.description(),
               mainMenuScreenUiState44.floatValue3,
               mainMenuScreenUiState44.floatValue4,
               mainMenuScreenUiState44.floatValue7,
               mainMenuScreenUiState44.floatValue8,
               mainMenuScreenUiState44.floatValue9,
               mainMenuScreenUiState44.floatValue10,
               mainMenuScreenUiState44.floatValue11,
               mainMenuScreenUiState44.floatValue12,
               mainMenuScreenUiState44.floatValue13,
               mainMenuScreenUiState44.floatValue14,
               mainMenuScreenUiState44.floatValue15,
               mainMenuScreenUiState44.floatValue16,
               mainMenuScreenUiState44.floatValue17,
               this.floatValue28,
               intValue24
            )
         );
      }

      ArrayList arrayList3 = new ArrayList(INT_VALUE_3);

      for (int intValue25 = 0; intValue25 < INT_VALUE_3; intValue25++) {
         MainMenuScreen.MainMenuScreenUiState2 mainMenuScreenUiState25 = this.mainMenuScreenUiState2s[intValue25];
         floatValue91 = Math.max(floatValue91, mainMenuScreenUiState25.floatValue12);
         arrayList3.add(
            new MainMenuScreen.MainMenuScreenBounds2(
               mainMenuScreenUiState25.mainMenuScreenUiState.text,
               mainMenuScreenUiState25.mainMenuScreenUiState.text2,
               resolve(mainMenuScreenUiState25.mainMenuScreenUiState),
               mainMenuScreenUiState25.floatValue3,
               mainMenuScreenUiState25.floatValue4,
               mainMenuScreenUiState25.floatValue7,
               mainMenuScreenUiState25.floatValue8,
               mainMenuScreenUiState25.floatValue9,
               mainMenuScreenUiState25.floatValue10,
               mainMenuScreenUiState25.floatValue11,
               mainMenuScreenUiState25.floatValue12,
               mainMenuScreenUiState25.floatValue13,
               mainMenuScreenUiState25.floatValue14,
               mainMenuScreenUiState25.floatValue15,
               mainMenuScreenUiState25.floatValue16,
               mainMenuScreenUiState25.floatValue17,
               mainMenuScreenUiState25.floatValue18,
               this.floatValue28 * mainMenuScreenUiState25.floatValue19,
               mainMenuScreenUiState25.mainMenuScreenUiState.mainMenuScreenState2 == MainMenuScreen.MainMenuScreenState2.SLIDER,
               intValue25
            )
         );
      }

      ArrayList arrayList4 = new ArrayList(INT_VALUE_4);

      for (int intValue26 = 0; intValue26 < INT_VALUE_4; intValue26++) {
         MainMenuScreen.MainMenuScreenUiState5 mainMenuScreenUiState54 = this.mainMenuScreenUiState5s[intValue26];
         arrayList4.add(
            new MainMenuScreen.MainMenuScreenBounds7(
               mainMenuScreenUiState54.mainMenuScreenDisplayEntry2.label(),
               mainMenuScreenUiState54.floatValue3,
               mainMenuScreenUiState54.floatValue4,
               mainMenuScreenUiState54.floatValue5,
               mainMenuScreenUiState54.floatValue6,
               mainMenuScreenUiState54.floatValue,
               mainMenuScreenUiState54.floatValue2,
               intValue26
            )
         );
      }

      arrayList2.clear();
      arrayList3.clear();
      arrayList4.clear();
      MainMenuScreen.MainMenuScreenData[] w283s = new MainMenuScreen.MainMenuScreenData[14];

      for (int intValue27 = 0; intValue27 < 14; intValue27++) {
         MainMenuScreen.MainMenuScreenState4 mainMenuScreenState4 = this.mainMenuScreenState4s[intValue27];
         float floatValue95 = Math.max(0.0F, this.floatValue - mainMenuScreenState4.floatValue3);
         float floatValue96 = floatValue95 > 3.1F ? 0.0F : mainMenuScreenState4.floatValue4;
         w283s[intValue27] = new MainMenuScreen.MainMenuScreenData(mainMenuScreenState4.floatValue / Math.max(1.0F, (float)i), mainMenuScreenState4.floatValue2 / Math.max(1.0F, (float)j), floatValue95, floatValue96);
      }

      float floatValue97 = measure9(Math.min(i, j) * 0.148F, 102.0F * floatValue90, 186.0F * floatValue90);
      float floatValue98 = floatValue97 * 6.25F;
      float floatValue99 = i * 0.5F + f * 1.65F * floatValue90;
      float floatValue100 = Math.max(50.0F * floatValue90, j * 0.132F + g * 0.95F * floatValue90);
      float floatValue101 = floatValue99 - floatValue98 * 0.5F;
      float floatValue102 = floatValue100 + floatValue97 * 0.5F - floatValue98 * 0.5F;
      float floatValue103 = 0.5F + 0.5F * (float)Math.sin(this.floatValue * 0.82F);
      MainMenuScreen.MainMenuScreenBounds3 mainMenuScreenBounds3 = new MainMenuScreen.MainMenuScreenBounds3(floatValue101, floatValue102, floatValue98, floatValue98, floatValue103);
      float floatValue104 = floatValue88 > 0.08F ? 1.0F : 0.86F;
      return new MainMenuScreen.MainMenuScreenTimedEntry(
         i,
         j,
         k,
         this.floatValue,
         this.floatValue6,
         this.floatValue7,
         this.floatValue6 / Math.max(1.0F, (float)i),
         this.floatValue7 / Math.max(1.0F, (float)j),
         this.floatValue8,
         this.floatValue9,
         floatValue87,
         measure10(this.intValue),
         measure11(this.intValue),
         measure12(this.intValue),
         measure10(this.intValue2),
         measure11(this.intValue2),
         measure12(this.intValue2),
         -f * 0.0014F,
         -g * 0.0011F,
         f * 1.75F * floatValue90,
         g * 1.35F * floatValue90,
         f * 2.15F * floatValue90,
         g * 1.72F * floatValue90,
         floatValue88,
         floatValue104,
         floatValue89,
         measure9(floatValue91, 0.0F, 1.0F),
         this.theme == Theme.SAKURA_BREEZE,
         this.theme == Theme.VERNAL_SOLSTICE,
         this.theme == Theme.MIDNIGHT_AZURE,
         this.flag7,
         mainMenuScreenBounds6,
         mainMenuScreenBounds5,
         arrayList2,
         arrayList3,
         arrayList4,
         mainMenuScreenBounds3,
         arrayList,
         w283s
      );
   }

   private void invoke15(MainMenuScreen.MainMenuScreenTimedEntry mainMenuScreenTimedEntry2) {
      try {
         WildClient.invoke15();
         RenderManager renderManager = WildClient.resolve();
         if (renderManager == null) {
            return;
         }

         FramebufferUtils.GlStateSnapshot glStateSnapshot2 = FramebufferUtils.captureGlState();

         try {
            renderManager.invoke(mainMenuScreenTimedEntry2.framebufferWidth(), mainMenuScreenTimedEntry2.framebufferHeight());
            float floatValue105 = measure4(mainMenuScreenTimedEntry2.framebufferWidth(), mainMenuScreenTimedEntry2.framebufferHeight());
            this.invoke19(renderManager, mainMenuScreenTimedEntry2, floatValue105);

            for (MainMenuScreen.MainMenuScreenBounds mainMenuScreenBounds : mainMenuScreenTimedEntry2.buttons()) {
               float floatValue106 = measure8(mainMenuScreenBounds.entry());
               float floatValue107 = 30.0F * floatValue105;
               float floatValue108 = mainMenuScreenBounds.x() + mainMenuScreenBounds.width() * 0.5F;
               float floatValue109 = mainMenuScreenBounds.y() + mainMenuScreenBounds.height() * 0.5F;
               float floatValue110 = floatValue109 + floatValue107 * 0.17F;
               renderManager.invoke70(
                  FontRegistry.fontObject,
                  floatValue108,
                  floatValue110,
                  floatValue107,
                  mainMenuScreenBounds.label(),
                  mainMenuScreenTimedEntry2.lightMode()
                     ? (mainMenuScreenTimedEntry2.vernalSolstice() ? compute6(0.0196F, 0.0667F, 0.0196F, floatValue106) : compute6(0.1F, 0.1F, 0.1F, floatValue106))
                     : compute6(1.0F, 1.0F, 1.0F, floatValue106),
                  "c"
               );
            }

            this.invoke20(renderManager, mainMenuScreenTimedEntry2, floatValue105);
            this.invoke16(renderManager, mainMenuScreenTimedEntry2, floatValue105);
            renderManager.invoke19();
         } finally {
            FramebufferUtils.restoreGlState(glStateSnapshot2);
         }
      } catch (Throwable exception3) {
      }
   }

   private void invoke16(RenderManager renderManager2, MainMenuScreen.MainMenuScreenTimedEntry mainMenuScreenTimedEntry3, float f) {
      MainMenuScreen.MainMenuScreenBounds6 mainMenuScreenBounds62 = mainMenuScreenTimedEntry3.profileTrigger();
      if (mainMenuScreenBounds62 != null) {
         float floatValue111 = measure8(mainMenuScreenBounds62.entry()) * mainMenuScreenTimedEntry3.sceneEntry();
         if (floatValue111 > 0.001F) {
            String text = "Graphics Console";
            String text2 = GraphicsQuality.resolve2(this.compute3()).getText();
            float floatValue112 = 22.0F * f;
            float floatValue113 = 22.0F * f;
            float floatValue114 = mainMenuScreenBounds62.x() + 20.0F * f;
            float floatValue115 = mainMenuScreenBounds62.y() + mainMenuScreenBounds62.height() * 0.43F;
            float floatValue116 = mainMenuScreenBounds62.y() + mainMenuScreenBounds62.height() * 0.74F;
            int intValue28 = mainMenuScreenTimedEntry3.lightMode() ? compute6(0.18F, 0.18F, 0.18F, 0.58F * floatValue111) : compute6(0.68F, 0.74F, 0.82F, 0.56F * floatValue111);
            int intValue29 = mainMenuScreenTimedEntry3.lightMode() ? compute6(0.06F, 0.06F, 0.07F, 0.9F * floatValue111) : compute6(0.96F, 0.98F, 1.0F, 0.92F * floatValue111);
            renderManager2.invoke69(FontRegistry.fontObject, floatValue114, floatValue115, floatValue112, text, intValue28);
            renderManager2.invoke69(FontRegistry.fontObject4, floatValue114, floatValue116, floatValue113, text2, intValue29);
         }
      }

      MainMenuScreen.MainMenuScreenBounds5 mainMenuScreenBounds52 = mainMenuScreenTimedEntry3.profilePanel();
      if (mainMenuScreenBounds52 != null && mainMenuScreenTimedEntry3.profiles() != null && mainMenuScreenTimedEntry3.controls() != null) {
         float floatValue117 = measure8(mainMenuScreenBounds52.progress()) * mainMenuScreenTimedEntry3.sceneEntry();
         if (!(floatValue117 <= 0.001F)) {
            float floatValue118 = 30.0F * f;
            float floatValue119 = 22.0F * f;
            float floatValue120 = mainMenuScreenBounds52.x() + 24.0F * f;
            float floatValue121 = mainMenuScreenBounds52.y() + 38.0F * f;
            float floatValue122 = mainMenuScreenBounds52.y() + 62.0F * f;
            int intValue30 = mainMenuScreenTimedEntry3.lightMode() ? compute6(0.06F, 0.06F, 0.07F, 0.94F * floatValue117) : compute6(0.97F, 0.98F, 1.0F, 0.94F * floatValue117);
            int intValue31 = mainMenuScreenTimedEntry3.lightMode() ? compute6(0.18F, 0.18F, 0.18F, 0.58F * floatValue117) : compute6(0.68F, 0.73F, 0.81F, 0.58F * floatValue117);
            renderManager2.invoke69(FontRegistry.fontObject4, floatValue120, floatValue121, floatValue118, "Graphics Console", intValue30);
            renderManager2.invoke69(FontRegistry.fontObject, floatValue120, floatValue122, floatValue119, "Единый ползунок качества — от плавности к кинематографии", intValue31);
            this.invoke17(renderManager2, mainMenuScreenTimedEntry3, f, mainMenuScreenBounds52, floatValue117, floatValue120, intValue31);
         }
      }
   }

   private void invoke17(RenderManager renderManager3, MainMenuScreen.MainMenuScreenTimedEntry mainMenuScreenTimedEntry4, float f, MainMenuScreen.MainMenuScreenBounds5 mainMenuScreenBounds53, float g, float h, int i) {
      int intValue32 = GraphicsQuality.values().length;
      int intValue33 = this.compute3();
      float floatValue123 = intValue33;
      if (!(this.floatValue29 < 0.0F) && !(this.floatValue30 < 0.0F)) {
         float floatValue124 = measure9(this.floatValue - this.floatValue30, 0.0F, 0.05F);
         this.floatValue30 = this.floatValue;
         this.floatValue29 = this.floatValue29 + (floatValue123 - this.floatValue29) * (1.0F - (float)Math.pow(4.0E-5F, floatValue124));
         if (Math.abs(floatValue123 - this.floatValue29) < 5.0E-4F) {
            this.floatValue29 = floatValue123;
         }
      } else {
         this.floatValue29 = floatValue123;
         this.floatValue30 = this.floatValue;
      }

      float floatValue125 = 9.0F * f;
      float floatValue126 = mainMenuScreenBounds53.x() + 30.0F * f + floatValue125;
      float floatValue127 = mainMenuScreenBounds53.x() + mainMenuScreenBounds53.width() - 30.0F * f - floatValue125;
      float floatValue128 = floatValue127 - floatValue126;
      if (!(floatValue128 < 1.0F)) {
         float floatValue129 = mainMenuScreenBounds53.y() + 138.0F * f;
         float floatValue130 = Math.max(4.0F, 5.0F * f);
         int intValue34 = mainMenuScreenTimedEntry4.lightMode() ? compute6(0.1F, 0.1F, 0.12F, 0.55F * g) : compute6(0.7F, 0.76F, 0.84F, 0.55F * g);
         renderManager3.invoke69(FontRegistry.fontObject4, h, mainMenuScreenBounds53.y() + 104.0F * f, 12.5F * f, "КАЧЕСТВО ГРАФИКИ", intValue34);
         String text3 = GraphicsQuality.resolve2(intValue33).getText();
         float floatValue131 = 13.0F * f;
         float floatValue132 = RenderManager.resolve7(FontRegistry.fontObject4, text3, floatValue131).floatValue;
         renderManager3.invoke69(
            FontRegistry.fontObject4,
            mainMenuScreenBounds53.x() + mainMenuScreenBounds53.width() - 24.0F * f - floatValue132,
            mainMenuScreenBounds53.y() + 104.0F * f,
            floatValue131,
            text3,
            compute7(this.intValue2, this.intValue, 0.6F, 0.95F * g)
         );
         float floatValue133 = measure9(this.floatValue29 / (intValue32 - 1), 0.0F, 1.0F);
         float floatValue134 = floatValue126 + floatValue128 * floatValue133;
         int intValue35 = mainMenuScreenTimedEntry4.lightMode() ? compute6(0.08F, 0.09F, 0.1F, 0.16F * g) : compute6(1.0F, 1.0F, 1.0F, 0.1F * g);
         renderManager3.invoke5(floatValue126, floatValue129 - floatValue130 * 0.5F, floatValue128, floatValue130, floatValue130 * 0.5F, intValue35);

         for (int intValue36 = 0; intValue36 < intValue32; intValue36++) {
            float floatValue135 = floatValue126 + floatValue128 * ((float)intValue36 / (intValue32 - 1));
            float floatValue136 = Math.max(1.5F, 2.0F * f);
            int intValue37 = mainMenuScreenTimedEntry4.lightMode() ? compute6(0.1F, 0.1F, 0.12F, 0.22F * g) : compute6(1.0F, 1.0F, 1.0F, 0.16F * g);
            renderManager3.invoke5(floatValue135 - floatValue136, floatValue129 - floatValue136, floatValue136 * 2.0F, floatValue136 * 2.0F, floatValue136, intValue37);
         }

         float floatValue137 = Math.max(floatValue130, floatValue134 - floatValue126);
         renderManager3.invoke5(floatValue126, floatValue129 - floatValue130 * 0.5F, floatValue137, floatValue130, floatValue130 * 0.5F, compute7(this.intValue2, this.intValue, floatValue133, 0.9F * g));
         renderManager3.invoke41(
            floatValue134 - floatValue125,
            floatValue129 - floatValue125,
            floatValue125 * 2.0F,
            floatValue125 * 2.0F,
            floatValue125,
            13.0F * f,
            1.0F,
            compute7(this.intValue2, this.intValue, floatValue133, 0.5F * g)
         );
         renderManager3.invoke5(
            floatValue134 - floatValue125,
            floatValue129 - floatValue125,
            floatValue125 * 2.0F,
            floatValue125 * 2.0F,
            floatValue125,
            mainMenuScreenTimedEntry4.lightMode() ? compute6(0.98F, 0.99F, 1.0F, 0.96F * g) : compute6(0.95F, 0.98F, 1.0F, 0.95F * g)
         );
         float floatValue138 = floatValue125 * 0.5F;
         renderManager3.invoke5(
            floatValue134 - floatValue138, floatValue129 - floatValue138, floatValue138 * 2.0F, floatValue138 * 2.0F, floatValue138, compute7(this.intValue2, this.intValue, floatValue133, 0.95F * g)
         );
         float floatValue139 = 13.0F * f;
         float floatValue140 = floatValue129 + 24.0F * f;

         for (int intValue38 = 0; intValue38 < intValue32; intValue38++) {
            String text4 = GraphicsQuality.resolve2(intValue38).getText();
            float floatValue141 = RenderManager.resolve7(FontRegistry.fontObject, text4, floatValue139).floatValue;
            float floatValue142 = floatValue126 + floatValue128 * ((float)intValue38 / (intValue32 - 1));
            float floatValue143 = measure9(floatValue142 - floatValue141 * 0.5F, mainMenuScreenBounds53.x() + 16.0F * f, mainMenuScreenBounds53.x() + mainMenuScreenBounds53.width() - 16.0F * f - floatValue141);
            boolean flag9 = intValue38 == intValue33;
            int intValue39 = flag9
               ? compute7(this.intValue2, this.intValue, 0.6F, 0.96F * g)
               : (mainMenuScreenTimedEntry4.lightMode() ? compute6(0.24F, 0.25F, 0.26F, 0.52F * g) : compute6(0.6F, 0.66F, 0.74F, 0.52F * g));
            renderManager3.invoke69(flag9 ? FontRegistry.fontObject4 : FontRegistry.fontObject, floatValue143, floatValue140, floatValue139, text4, intValue39);
         }
      }
   }

   private float[] resolve3() {
      if (this.client != null && this.client.getWindow() != null) {
         float floatValue144 = measure4(this.client.getWindow().getFramebufferWidth(), this.client.getWindow().getFramebufferHeight());
         float floatValue145 = 9.0F * floatValue144;
         float floatValue146 = this.floatValue23 + 30.0F * floatValue144 + floatValue145;
         float floatValue147 = this.floatValue23 + this.floatValue25 - 30.0F * floatValue144 - floatValue145;
         float floatValue148 = this.floatValue24 + 138.0F * floatValue144;
         return floatValue147 - floatValue146 < 1.0F ? null : new float[]{floatValue146, floatValue147, floatValue148, floatValue145, floatValue144};
      } else {
         return null;
      }
   }

   private boolean check3(float f, float g) {
      if (!this.flag8) {
         return false;
      } else {
         float[] floatValues2 = this.resolve3();
         if (floatValues2 == null) {
            return false;
         } else {
            float floatValue149 = 18.0F * floatValues2[4];
            return f >= floatValues2[0] - floatValues2[3] * 1.5F && f <= floatValues2[1] + floatValues2[3] * 1.5F && Math.abs(g - floatValues2[2]) <= floatValue149;
         }
      }
   }

   private void invoke18(float f) {
      float[] floatValues3 = this.resolve3();
      if (floatValues3 != null) {
         int intValue40 = GraphicsQuality.values().length;
         float floatValue150 = measure9((f - floatValues3[0]) / (floatValues3[1] - floatValues3[0]), 0.0F, 1.0F);
         int intValue41 = Math.max(0, Math.min(intValue40 - 1, Math.round(floatValue150 * (intValue40 - 1))));
         if (intValue41 != this.compute3()) {
            MenuModule.invoke2(intValue41);
         }
      }
   }

   private int compute3() {
      int intValue42 = GraphicsQuality.values().length - 1;
      return Math.max(0, Math.min(intValue42, Math.round(MenuModule.KACHESTVO_GRAFIKI.value)));
   }

   private static int compute4(int i, int j) {
      int intValue43 = Math.max(0, Math.min(255, j));
      return i & 16777215 | intValue43 << 24;
   }

   private void invoke19(RenderManager renderManager4, MainMenuScreen.MainMenuScreenTimedEntry mainMenuScreenTimedEntry5, float f) {
      MainMenuScreen.MainMenuScreenBounds3 mainMenuScreenBounds32 = mainMenuScreenTimedEntry5.logo();
      float floatValue151 = measure8(mainMenuScreenTimedEntry5.sceneEntry());
      float floatValue152 = 0.5F + 0.5F * (float)Math.sin(this.floatValue * 1.08F);
      float floatValue153 = mainMenuScreenBounds32.height() / 6.25F;
      float floatValue154 = floatValue153 * 0.98F;
      float floatValue155 = floatValue154 * (1.08F + floatValue152 * 0.035F);
      float floatValue156 = RenderManager.resolve7(BrandMark.font(), BrandMark.GLYPH, floatValue154).floatValue;
      float floatValue157 = RenderManager.resolve7(BrandMark.font(), BrandMark.GLYPH, floatValue155).floatValue;
      float floatValue158 = mainMenuScreenBounds32.x() + mainMenuScreenBounds32.width() * 0.5F;
      float floatValue159 = mainMenuScreenBounds32.y() + mainMenuScreenBounds32.height() * 0.5F;
      float floatValue160 = floatValue158 - floatValue156 * 0.5F;
      float floatValue161 = floatValue158 - floatValue157 * 0.5F;
      float floatValue162 = floatValue159 + floatValue153 * 0.148F;
      float floatValue163 = floatValue159 + floatValue153 * 0.15F;
      renderManager4.invoke69(BrandMark.font(), floatValue161, floatValue163, floatValue155, BrandMark.GLYPH, compute7(this.intValue2, this.intValue, floatValue152, 0.24F * floatValue151));
      renderManager4.invoke69(
         BrandMark.font(),
         floatValue160,
         floatValue162,
         floatValue154,
         BrandMark.GLYPH,
         mainMenuScreenTimedEntry5.lightMode()
            ? (mainMenuScreenTimedEntry5.vernalSolstice() ? compute6(0.0196F, 0.0667F, 0.0196F, floatValue151) : compute6(0.1F, 0.1F, 0.1F, floatValue151))
            : compute6(1.0F, 1.0F, 1.0F, floatValue151)
      );
   }

   private void invoke20(RenderManager renderManager5, MainMenuScreen.MainMenuScreenTimedEntry mainMenuScreenTimedEntry6, float f) {
   }

   private void invoke21(MainMenuScreen.MainMenuScreenState mainMenuScreenState) {
      MinecraftClient client = this.client == null ? MinecraftClient.getInstance() : this.client;
      if (client != null) {
         switch (mainMenuScreenState) {
            case SINGLEPLAYER:
               client.execute(() -> client.setScreen(new SelectWorldScreen(this)));
               break;
            case MULTIPLAYER:
               client.execute(() -> client.setScreen(new WildMultiplayerScreen(this)));
               break;
            case ALT_MANAGER:
               client.execute(() -> client.setScreen(new AltVaultScreen(this)));
               break;
            case OPTIONS:
               client.execute(() -> client.setScreen(new OptionsScreen(this, client.options)));
               break;
            case QUIT:
               client.execute(client::scheduleStop);
         }
      }
   }

   private float measure2(Window window, double d) {
      return (float)(d * window.getFramebufferWidth() / Math.max(1.0, (double)window.getScaledWidth()));
   }

   private float measure3(Window window, double d) {
      return (float)(d * window.getFramebufferHeight() / Math.max(1.0, (double)window.getScaledHeight()));
   }

   private static float measure4(float f, float g) {
      float floatValue172 = measure9(MenuModule.MASSHTAB_GUI.getValue() / 0.86F, 0.72F, 1.46F);
      return measure9(Math.min(f / 1920.0F, g / 1080.0F) * 1.16F * floatValue172, 0.66F, 1.56F);
   }

   static float measure5(float f, float g, float h, float i, float j, float k, float l) {
      float floatValue173 = h + j * 0.5F;
      float floatValue174 = i + k * 0.5F;
      float floatValue175 = j * 0.5F - l;
      float floatValue176 = k * 0.5F - l;
      float floatValue177 = Math.abs(f - floatValue173) - floatValue175;
      float floatValue178 = Math.abs(g - floatValue174) - floatValue176;
      float floatValue179 = Math.max(floatValue177, 0.0F);
      float floatValue180 = Math.max(floatValue178, 0.0F);
      return (float)Math.sqrt(floatValue179 * floatValue179 + floatValue180 * floatValue180) + Math.min(Math.max(floatValue177, floatValue178), 0.0F) - l;
   }

   private static float measure6(float f, float g) {
      return (float)Math.sqrt(f * f + g * g);
   }

   private static boolean check4(float f, float g, float h, float i, float j, float k) {
      return f >= h && f <= h + j && g >= i && g <= i + k;
   }

   private int compute5(float f, float g) {
      if (!this.flag8 && this.floatValue28 <= 0.18F) {
         return -1;
      } else {
         for (int intValue46 = 0; intValue46 < INT_VALUE_4; intValue46++) {
            if (this.mainMenuScreenUiState5s[intValue46].check(f, g)) {
               return intValue46;
            }
         }

         return -1;
      }
   }

   private float measure7(int i) {
      for (MainMenuScreen.MainMenuScreenUiState5 mainMenuScreenUiState55 : this.mainMenuScreenUiState5s) {
         if (i >= mainMenuScreenUiState55.mainMenuScreenDisplayEntry2.start() && i < mainMenuScreenUiState55.mainMenuScreenDisplayEntry2.start() + mainMenuScreenUiState55.mainMenuScreenDisplayEntry2.count()) {
            return mainMenuScreenUiState55.floatValue;
         }
      }

      return 1.0F;
   }

   private static float measure8(float f) {
      float floatValue181 = measure9(f, 0.0F, 1.0F);
      return floatValue181 * floatValue181 * floatValue181 * (floatValue181 * (floatValue181 * 6.0F - 15.0F) + 10.0F);
   }

   private static float measure9(float f, float g, float h) {
      return Math.max(g, Math.min(h, f));
   }

   private static float measure10(int i) {
      return (i >> 16 & 0xFF) / 255.0F;
   }

   private static float measure11(int i) {
      return (i >> 8 & 0xFF) / 255.0F;
   }

   private static float measure12(int i) {
      return (i & 0xFF) / 255.0F;
   }

   private static int compute6(float f, float g, float h, float i) {
      int intValue47 = Math.round(measure9(f, 0.0F, 1.0F) * 255.0F);
      int intValue48 = Math.round(measure9(g, 0.0F, 1.0F) * 255.0F);
      int intValue49 = Math.round(measure9(h, 0.0F, 1.0F) * 255.0F);
      int intValue50 = Math.round(measure9(i, 0.0F, 1.0F) * 255.0F);
      return intValue50 << 24 | intValue47 << 16 | intValue48 << 8 | intValue49;
   }

   private static int compute7(int i, int j, float f, float g) {
      float floatValue182 = measure9(f, 0.0F, 1.0F);
      int intValue51 = ColorUtils.compute16(i, j, floatValue182);
      int intValue52 = Math.round(measure9(g, 0.0F, 1.0F) * 255.0F);
      return intValue52 << 24 | intValue51;
   }

   static enum MainMenuScreenState {
      SINGLEPLAYER,
      MULTIPLAYER,
      ALT_MANAGER,
      OPTIONS,
      QUIT;
   }

   public record MainMenuScreenBounds(
      String label,
      float x,
      float y,
      float width,
      float height,
      float radius,
      float hover,
      float magnet,
      float press,
      float entry,
      float flash,
      float bleed,
      float scale,
      float localMouseX,
      float localMouseY,
      float velocity
   ) {
   }

   static enum MainMenuScreenState2 {
      SLIDER,
      TOGGLE;
   }

   static final class MainMenuScreenUiState {
      final String text;
      final String text2;
      final MainMenuScreen.MainMenuScreenState2 mainMenuScreenState2;
      final NumberSetting numberSetting;
      final BooleanSetting booleanSetting;
      final MainMenuScreen.MainMenuScreenState3 mainMenuScreenState3;

      private MainMenuScreenUiState(
         String string,
         String string2,
         MainMenuScreen.MainMenuScreenState2 mainMenuScreenState2,
         NumberSetting numberSetting2,
         BooleanSetting booleanSetting,
         MainMenuScreen.MainMenuScreenState3 mainMenuScreenState3
      ) {
         this.text = string;
         this.text2 = string2;
         this.mainMenuScreenState2 = mainMenuScreenState2;
         this.numberSetting = numberSetting2;
         this.booleanSetting = booleanSetting;
         this.mainMenuScreenState3 = mainMenuScreenState3;
      }

      static MainMenuScreen.MainMenuScreenUiState resolve(String string, String string2, NumberSetting numberSetting3, MainMenuScreen.MainMenuScreenState3 mainMenuScreenState32) {
         return new MainMenuScreen.MainMenuScreenUiState(string, string2, MainMenuScreen.MainMenuScreenState2.SLIDER, numberSetting3, null, mainMenuScreenState32);
      }

      static MainMenuScreen.MainMenuScreenUiState resolve2(String string, String string2, BooleanSetting booleanSetting2) {
         return new MainMenuScreen.MainMenuScreenUiState(string, string2, MainMenuScreen.MainMenuScreenState2.TOGGLE, null, booleanSetting2, MainMenuScreen.MainMenuScreenState3.PERCENT);
      }
   }

   static enum MainMenuScreenState3 {
      PIXELS,
      MULTIPLIER,
      PERCENT,
      SCALE;
   }

   static final class MainMenuScreenUiState2 {
      final MainMenuScreen.MainMenuScreenUiState mainMenuScreenUiState;
      final SpringIntegrator springIntegrator = new SpringIntegrator(SpringSpec.resolve6());
      float floatValue;
      float floatValue2;
      float floatValue3;
      float floatValue4;
      float floatValue5;
      float floatValue6;
      float floatValue7;
      float floatValue8;
      float floatValue9;
      float floatValue10;
      float floatValue11;
      float floatValue12;
      float floatValue13 = 1.0F;
      float floatValue14 = 0.5F;
      float floatValue15 = 0.5F;
      float floatValue16;
      float floatValue17;
      float floatValue18;
      float floatValue19 = 1.0F;

      MainMenuScreenUiState2(MainMenuScreen.MainMenuScreenUiState mainMenuScreenUiState7) {
         this.mainMenuScreenUiState = mainMenuScreenUiState7;
      }

      void invoke() {
         this.floatValue10 = 0.0F;
         this.floatValue11 = 0.0F;
         this.floatValue12 = 0.0F;
         this.floatValue13 = 1.0F;
         this.floatValue14 = 0.5F;
         this.floatValue15 = 0.5F;
         this.floatValue16 = 0.0F;
         this.floatValue17 = 0.0F;
         this.floatValue18 = 0.0F;
         this.floatValue19 = 1.0F;
         this.floatValue3 = this.floatValue;
         this.floatValue4 = this.floatValue2;
         this.floatValue7 = this.floatValue5;
         this.floatValue8 = this.floatValue6;
         this.springIntegrator.setFloatValue(1.0F);
      }
   }

   public record MainMenuScreenBounds2(
      String label,
      String description,
      String valueText,
      float x,
      float y,
      float width,
      float height,
      float radius,
      float hover,
      float press,
      float flash,
      float scale,
      float localMouseX,
      float localMouseY,
      float active,
      float value,
      float velocity,
      float entry,
      boolean slider,
      int index
   ) {
   }

   public record MainMenuScreenBounds3(float x, float y, float width, float height, float pulse) {
   }

   static final class MainMenuScreenUiState3 {
      final String text;
      final MainMenuScreen.MainMenuScreenState mainMenuScreenState;
      final SpringIntegrator springIntegrator = new SpringIntegrator(SpringSpec.resolve6());
      float floatValue;
      float floatValue2;
      float floatValue3;
      float floatValue4;
      float floatValue5;
      float floatValue6;
      float floatValue7;
      float floatValue8;
      float floatValue9;
      float floatValue10;
      float floatValue11;
      float floatValue12 = 1.0F;
      float floatValue13 = 0.5F;
      float floatValue14 = 0.5F;
      float floatValue15;

      MainMenuScreenUiState3(String string, MainMenuScreen.MainMenuScreenState mainMenuScreenState5) {
         this.text = string;
         this.mainMenuScreenState = mainMenuScreenState5;
      }

      void invoke() {
         this.floatValue8 = 0.0F;
         this.floatValue9 = 0.0F;
         this.floatValue10 = 0.0F;
         this.floatValue11 = 0.0F;
         this.floatValue12 = 1.0F;
         this.floatValue13 = 0.5F;
         this.floatValue14 = 0.5F;
         this.floatValue15 = 0.0F;
         this.springIntegrator.setFloatValue(1.0F);
      }

      boolean check(float f, float g) {
         return MainMenuScreen.measure5(f, g, this.floatValue, this.floatValue2, this.floatValue5, this.floatValue6, this.floatValue7) <= 0.0F;
      }
   }

   static final class MainMenuScreenUiState4 {
      final MainMenuScreen.MainMenuScreenDisplayEntry mainMenuScreenDisplayEntry;
      final SpringIntegrator springIntegrator = new SpringIntegrator(SpringSpec.resolve6());
      float floatValue;
      float floatValue2;
      float floatValue3;
      float floatValue4;
      float floatValue5;
      float floatValue6;
      float floatValue7;
      float floatValue8;
      float floatValue9;
      float floatValue10;
      float floatValue11;
      float floatValue12;
      float floatValue13 = 1.0F;
      float floatValue14 = 0.5F;
      float floatValue15 = 0.5F;
      float floatValue16;
      float floatValue17;

      MainMenuScreenUiState4(MainMenuScreen.MainMenuScreenDisplayEntry mainMenuScreenDisplayEntry) {
         this.mainMenuScreenDisplayEntry = mainMenuScreenDisplayEntry;
      }

      void invoke() {
         this.floatValue10 = 0.0F;
         this.floatValue11 = 0.0F;
         this.floatValue12 = 0.0F;
         this.floatValue13 = 1.0F;
         this.floatValue14 = 0.5F;
         this.floatValue15 = 0.5F;
         this.floatValue16 = 0.0F;
         this.floatValue17 = 0.0F;
         this.floatValue3 = this.floatValue;
         this.floatValue4 = this.floatValue2;
         this.floatValue7 = this.floatValue5;
         this.floatValue8 = this.floatValue6;
         this.springIntegrator.setFloatValue(1.0F);
      }
   }

   public record MainMenuScreenBounds4(
      String label,
      String description,
      float x,
      float y,
      float width,
      float height,
      float radius,
      float hover,
      float press,
      float flash,
      float scale,
      float localMouseX,
      float localMouseY,
      float selected,
      float velocity,
      float entry,
      int index
   ) {
   }

   public record MainMenuScreenBounds5(float x, float y, float width, float height, float radius, float progress, float open) {
   }

   record MainMenuScreenDisplayEntry(String label, String description, GraphicsQuality preset) {
   }

   public record MainMenuScreenBounds6(
      float x,
      float y,
      float width,
      float height,
      float radius,
      float hover,
      float press,
      float entry,
      float flash,
      float scale,
      float localMouseX,
      float localMouseY,
      float panelProgress
   ) {
   }

   record MainMenuScreenDisplayEntry2(String label, int start, int count) {
      int rows() {
         return (this.count + 1) / 2;
      }
   }

   static final class MainMenuScreenUiState5 {
      final MainMenuScreen.MainMenuScreenDisplayEntry2 mainMenuScreenDisplayEntry2;
      final SpringIntegrator springIntegrator = new SpringIntegrator(SpringSpec.resolve6());
      boolean flag;
      float floatValue = 1.0F;
      float floatValue2;
      float floatValue3;
      float floatValue4;
      float floatValue5;
      float floatValue6;

      MainMenuScreenUiState5(MainMenuScreen.MainMenuScreenDisplayEntry2 mainMenuScreenDisplayEntry2) {
         this.mainMenuScreenDisplayEntry2 = mainMenuScreenDisplayEntry2;
         this.springIntegrator.setFloatValue(1.0F);
      }

      void invoke() {
         this.flag = false;
         this.floatValue = 1.0F;
         this.floatValue2 = 0.0F;
         this.floatValue3 = 0.0F;
         this.floatValue4 = 0.0F;
         this.floatValue5 = 0.0F;
         this.floatValue6 = 0.0F;
         this.springIntegrator.setFloatValue(1.0F);
      }

      boolean check(float f, float g) {
         return this.floatValue5 > 0.0F
            && f >= this.floatValue3
            && f <= this.floatValue3 + this.floatValue5
            && g >= this.floatValue4
            && g <= this.floatValue4 + this.floatValue6;
      }
   }

   public record MainMenuScreenBounds7(String label, float x, float y, float width, float height, float openAmount, float hover, int index) {
   }

   public record MainMenuScreenTimedEntry(
      int framebufferWidth,
      int framebufferHeight,
      int drawFramebuffer,
      float time,
      float mouseX,
      float mouseY,
      float mouseNormX,
      float mouseNormY,
      float mouseVelocityX,
      float mouseVelocityY,
      float mouseSpeed,
      float accentTopR,
      float accentTopG,
      float accentTopB,
      float accentBottomR,
      float accentBottomG,
      float accentBottomB,
      float backgroundParallaxX,
      float backgroundParallaxY,
      float particleParallaxX,
      float particleParallaxY,
      float uiParallaxX,
      float uiParallaxY,
      float activeMotion,
      float backgroundScale,
      float sceneEntry,
      float clickFlash,
      boolean sakuraBreeze,
      boolean vernalSolstice,
      boolean midnightAzure,
      boolean lightMode,
      MainMenuScreen.MainMenuScreenBounds6 profileTrigger,
      MainMenuScreen.MainMenuScreenBounds5 profilePanel,
      List<MainMenuScreen.MainMenuScreenBounds4> profiles,
      List<MainMenuScreen.MainMenuScreenBounds2> controls,
      List<MainMenuScreen.MainMenuScreenBounds7> sections,
      MainMenuScreen.MainMenuScreenBounds3 logo,
      List<MainMenuScreen.MainMenuScreenBounds> buttons,
      MainMenuScreen.MainMenuScreenData[] trails
   ) {
      public MainMenuScreen.MainMenuScreenData trail(int i) {
         return this.trails != null && i >= 0 && i < this.trails.length && this.trails[i] != null
            ? this.trails[i]
            : new MainMenuScreen.MainMenuScreenData(0.0F, 0.0F, 100.0F, 0.0F);
      }
   }

   static final class MainMenuScreenState4 {
      float floatValue;
      float floatValue2;
      float floatValue3 = -100.0F;
      float floatValue4;
   }

   public record MainMenuScreenData(float x, float y, float age, float strength) {
   }
}
