package ru.metaculture.protection;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import lombok.Generated;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import org.wild.module.api.Module;

public final class ClickGui {
   private static final long TIMESTAMP = 1000L;
   private static final long TIMESTAMP_2 = 95L;
   private static final long TIMESTAMP_3 = 55L;
   private final ClickGuiState clickGuiState = new ClickGuiState();
   private final LayoutSpec layoutSpec = LayoutSpec.resolve();
   private final ClickGuiGeometry clickGuiGeometry = new ClickGuiGeometry();
   private final ClickGuiLayoutEngine clickGuiLayoutEngine = new ClickGuiLayoutEngine(this.layoutSpec);
   private final ModuleLayoutEngine moduleLayoutEngine = new ModuleLayoutEngine();
   private final ClickGuiSettingController clickGuiSettingController = new ClickGuiSettingController();
   private final CoreDiagnosticsPanelRenderer coreDiagnosticsPanelRenderer = new CoreDiagnosticsPanelRenderer();
   private final ClickGuiHitMapBuilder clickGuiHitMapBuilder = new ClickGuiHitMapBuilder(this.moduleLayoutEngine, this.clickGuiSettingController, this.coreDiagnosticsPanelRenderer);
   private final ClickGuiInputController clickGuiInputController = new ClickGuiInputController(this.clickGuiHitMapBuilder, this.clickGuiSettingController, new ClickGuiKeyboardController());
   private final ThemePalette themePalette = ThemePalette.resolve2();
   private ShaderFoundryScreen shaderFoundryScreen;
   private SpringSpec springSpec = SpringSpec.resolve6();
   private SpringSpec springSpec2 = SpringSpec.resolve2();
   private SpringSpec springSpec3 = SpringSpec.resolve3();
   private SpringSpec springSpec4 = SpringSpec.resolve4();
   private SpringSpec springSpec5 = SpringSpec.resolve5();
   private SpringSpec springSpec6 = SpringSpec.resolve9();
   private SpringSpec springSpec7 = SpringSpec.resolve8();
   private SpringSpec springSpec8 = SpringSpec.resolve10();
   private SpringSpec springSpec9 = SpringSpec.resolve11();
   private SpringSpec springSpec10 = SpringSpec.resolve12();
   private SpringSpec springSpec11 = SpringSpec.resolve13();
   private SpringSpec springSpec12 = SpringSpec.resolve14();
   private SpringSpec springSpec13 = SpringSpec.resolve15();
   private SpringSpec springSpec14 = SpringSpec.resolve16();
   private SpringSpec springSpec15 = SpringSpec.resolve17();
   private final ClickGuiRenderer clickGuiRenderer = new ClickGuiRenderer(
      new ThemePanelRenderer(),
      new SidebarRenderer(),
      new SearchBarRenderer(),
      new ContentPanelRenderer(new ModuleListRenderer(new SettingsRenderer())),
      this.coreDiagnosticsPanelRenderer,
      new TooltipRenderer()
   );
   private Metrics metrics = Metrics.resolve4(0.5F, this.layoutSpec);
   private ThemeContext themeContext = this.resolve3();
   private ModuleLayoutResult moduleLayoutResult = ModuleLayoutResult.resolve();
   private boolean flag;
   private Theme theme;
   private long timestamp;
   private boolean flag2;

   void invoke(MinecraftClient minecraftClient) {
      this.clickGuiState.invoke();
      MenuModule.invoke5(minecraftClient, this.layoutSpec);
      this.clickGuiState.invoke2();
      SpecialModuleCardHandlers.invoke(this.clickGuiState);
      this.flag = false;
      this.theme = null;
      this.timestamp = System.currentTimeMillis();
      this.flag2 = false;
      this.invoke7(minecraftClient);
      if (minecraftClient != null && minecraftClient.mouse != null) {
         minecraftClient.mouse.unlockCursor();
      }

      ProtectionHandler.checkAccess();
   }

   void invoke2(MinecraftClient minecraftClient, DrawContext drawContext, RenderManager renderManager, int i, int j, float f) {
      if (minecraftClient != null && minecraftClient.getWindow() != null && renderManager != null && i > 0 && j > 0) {
         long longValue = System.currentTimeMillis();
         if (this.flag2 || this.timestamp > 0L && longValue - this.timestamp > 1000L) {
            this.invoke12(minecraftClient);
         }

         this.timestamp = longValue;
         this.flag2 = false;
         this.invoke8(minecraftClient, i, j);
         ClickGuiDragRegistry.invoke();
         this.clickGuiInputController.invoke(this.clickGuiState, this.clickGuiState.getFloatValue());
         ShaderFoundryScreen shaderFoundryScreen = !this.clickGuiState.isFlag24() && !(this.clickGuiState.measure7(AnimationKeyRegistry.resolve56()) > 0.0015F)
            ? this.shaderFoundryScreen
            : this.resolve2();
         boolean flag = shaderFoundryScreen != null && shaderFoundryScreen.check3(this.clickGuiState);
         if (!flag) {
            this.clickGuiRenderer.invoke(renderManager, drawContext, this.clickGuiState, this.clickGuiGeometry, this.moduleLayoutResult, this.themeContext, i, j);
         }

         if (shaderFoundryScreen != null) {
            shaderFoundryScreen.invoke(renderManager, this.clickGuiState, this.themeContext, i, j);
         }
      } else {
         this.invoke5();
      }
   }

   void invoke3(float f, float g) {
      this.clickGuiState.setFloatValue(f);
      this.clickGuiState.setFloatValue2(g);
   }

   public boolean check() {
      return this.clickGuiState.isFlag24() || this.clickGuiState.measure7(AnimationKeyRegistry.resolve56()) > 0.0015F;
   }

   boolean check2(float f, float g, int i) {
      if (this.clickGuiState.isFlag7()) {
         return true;
      } else {
         ShaderFoundryScreen shaderFoundryScreen2 = this.resolve();
         if (shaderFoundryScreen2 != null) {
            return i == 0 && !shaderFoundryScreen2.check2() && ClickGuiDragRegistry.check2(f, g)
               ? true
               : shaderFoundryScreen2.check7(this.clickGuiState, this.themeContext, f, g, i, this.compute(), this.compute2());
         } else if (this.clickGuiState.isFlag3()
            && this.clickGuiRenderer.resolve() != null
            && this.clickGuiRenderer.resolve().check4(this.clickGuiState, this.themeContext, f, g, i)) {
            return true;
         } else {
            float floatValue = this.clickGuiState.measure7(AnimationKeyRegistry.resolve32());
            boolean flag2 = floatValue > 0.1F
               && f >= this.clickGuiGeometry.getFloatValue22()
               && f <= this.clickGuiGeometry.getFloatValue22() + this.metrics.getFloatValue18()
               && g >= this.clickGuiGeometry.getFloatValue23()
               && g <= this.clickGuiGeometry.getFloatValue23() + this.metrics.getFloatValue19();
            boolean flag3 = this.check13(f, g);
            if (i == 0) {
               float floatValue2 = this.clickGuiGeometry.getFloatValue3() + this.metrics.measure(16.0F);
               float floatValue3 = this.clickGuiGeometry.getFloatValue4() + this.metrics.measure(16.0F);
               float floatValue4 = this.metrics.measure(40.0F);
               if (f >= floatValue2 && f <= floatValue2 + floatValue4 && g >= floatValue3 && g <= floatValue3 + floatValue4) {
                  boolean flag4 = !this.clickGuiState.isFlag22();
                  this.clickGuiState.setFlag22(flag4);
                  if (flag4) {
                     this.clickGuiState.setFlag23(true);
                  }

                  return true;
               }
            }

            if (!flag2 || !this.clickGuiState.isFlag23() && flag3) {
               if (i == 0) {
                  boolean flag5 = f >= this.clickGuiGeometry.getFloatValue10()
                     && f <= this.clickGuiGeometry.getFloatValue10() + this.metrics.getFloatValue11()
                     && g >= this.clickGuiGeometry.getFloatValue8()
                     && g <= this.clickGuiGeometry.getFloatValue8() + this.metrics.getFloatValue10();
                  if (flag5) {
                     label96: {
                        boolean flag6 = f >= this.clickGuiGeometry.getFloatValue10() + this.metrics.getFloatValue11() - this.metrics.measure(34.0F);
                        if (flag6) {
                           if (!this.clickGuiState.getText().isEmpty()) {
                              break label96;
                           }

                           if (this.clickGuiState.isFlag5()) {
                              break label96;
                           }
                        }

                        this.clickGuiState.invoke51();
                        this.clickGuiState.setFlag6(false);
                        this.clickGuiState.setFlag5(true);
                        this.clickGuiState.setFlag4(false);
                        return true;
                     }

                     this.clickGuiState.invoke22();
                     this.clickGuiState.setFlag5(false);
                     this.clickGuiState.invoke51();
                     return true;
                  }

                  if (this.clickGuiState.isFlag5()) {
                     this.clickGuiState.setFlag5(false);
                     this.clickGuiState.setFlag4(false);
                  }
               }

               this.clickGuiState.setFlag23(false);
               return this.clickGuiInputController.check(this.clickGuiState, this.clickGuiGeometry, this.moduleLayoutResult, this.metrics, this.themePalette, f, g, i);
            } else {
               this.clickGuiState.setFlag23(true);
               if (i == 0) {
                  float floatValue5 = this.metrics.measure2(20.0F);
                  float floatValue6 = this.clickGuiGeometry.getFloatValue22() + this.metrics.getFloatValue18() - this.metrics.measure2(15.0F) - floatValue5;
                  float floatValue7 = this.clickGuiGeometry.getFloatValue23() + this.metrics.measure2(20.0F);
                  if (f >= floatValue6 && f <= floatValue6 + floatValue5 && g >= floatValue7 && g <= floatValue7 + floatValue5) {
                     this.clickGuiState.setFlag22(false);
                     return true;
                  }
               }

               this.clickGuiInputController.check(this.clickGuiState, this.clickGuiGeometry, this.moduleLayoutResult, this.metrics, this.themePalette, f, g, i);
               return true;
            }
         }
      }
   }

   boolean check3() {
      ShaderFoundryScreen shaderFoundryScreen3 = this.resolve();
      if (shaderFoundryScreen3 == null) {
         if (this.clickGuiState.isFlag3() && this.clickGuiRenderer.resolve() != null) {
            this.clickGuiRenderer.resolve().check5(this.clickGuiState, this.clickGuiState.getFloatValue(), this.clickGuiState.getFloatValue2());
         }

         return this.clickGuiInputController.check4(this.clickGuiState);
      } else {
         boolean flag7 = ClickGuiDragRegistry.check4();
         boolean flag8 = shaderFoundryScreen3.check12(this.clickGuiState, this.clickGuiState.getFloatValue(), this.clickGuiState.getFloatValue2());
         return flag7 || flag8;
      }
   }

   boolean check4(float f, float g) {
      if (this.clickGuiState.isFlag7()) {
         return true;
      } else {
         ShaderFoundryScreen shaderFoundryScreen4 = this.resolve();
         if (shaderFoundryScreen4 != null) {
            if (ClickGuiDragRegistry.check3(f, g)) {
               return true;
            } else {
               shaderFoundryScreen4.check11(this.clickGuiState, f, g);
               return true;
            }
         } else if (this.clickGuiState.isFlag3()
            && this.clickGuiRenderer.resolve() != null
            && this.clickGuiRenderer.resolve().check6(this.clickGuiState, f, g)) {
            return true;
         } else {
            boolean flag9 = this.clickGuiInputController.check5(this.clickGuiState, this.clickGuiGeometry, this.metrics, f, g);
            if (this.clickGuiState.isFlag14() || this.clickGuiState.isFlag16()) {
               this.invoke7(MinecraftClient.getInstance());
            } else if (this.clickGuiState.isFlag12() || this.clickGuiState.isFlag13()) {
               this.clickGuiLayoutEngine.invoke(this.metrics, this.clickGuiState, this.clickGuiGeometry);
            }

            return flag9;
         }
      }
   }

   boolean check5(float f, float g, double d, double e) {
      if (this.clickGuiState.isFlag7()) {
         return true;
      } else {
         ShaderFoundryScreen shaderFoundryScreen5 = this.resolve();
         if (shaderFoundryScreen5 != null) {
            return shaderFoundryScreen5.check13(this.clickGuiState, f, g, e);
         } else {
            return this.clickGuiState.isFlag3()
                  && this.clickGuiRenderer.resolve() != null
                  && this.clickGuiRenderer.resolve().check7(this.clickGuiState, f, g, e)
               ? true
               : this.clickGuiInputController.check6(this.clickGuiState, this.clickGuiGeometry, this.moduleLayoutResult, this.metrics, f, g, d, e);
         }
      }
   }

   boolean check6(int i) {
      if (this.clickGuiState.isFlag7()) {
         return true;
      } else if (i == 84 && Screen.hasControlDown()) {
         this.clickGuiState.invoke51();
         if (this.clickGuiState.isFlag24()) {
            this.clickGuiState.setFlag24(false);
         } else {
            this.clickGuiState.setFlag24(this.resolve2() != null);
         }

         return true;
      } else if (i == 77 && Screen.hasControlDown()) {
         this.clickGuiState.invoke51();
         this.clickGuiState.setFlag24(false);
         this.clickGuiState.invoke9();
         return true;
      } else {
         ShaderFoundryScreen shaderFoundryScreen6 = this.resolve();
         if (shaderFoundryScreen6 != null) {
            return shaderFoundryScreen6.check15(this.clickGuiState, i);
         } else if (this.clickGuiState.isFlag3()
            && this.clickGuiRenderer.resolve() != null
            && this.clickGuiRenderer.resolve().check8(this.clickGuiState, i)) {
            return true;
         } else {
            if (this.clickGuiState.isFlag5()) {
               if (Screen.hasControlDown()) {
                  switch (i) {
                     case 65:
                        if (!this.clickGuiState.getText().isEmpty()) {
                           this.clickGuiState.setFlag4(true);
                        }

                        return true;
                     case 67:
                        if (!this.clickGuiState.getText().isEmpty()) {
                           MinecraftClient.getInstance().keyboard.setClipboard(this.clickGuiState.getText());
                        }

                        return true;
                     case 86:
                        String text = MinecraftClient.getInstance().keyboard.getClipboard();
                        if (text != null && !text.isEmpty()) {
                           if (this.clickGuiState.isFlag4()) {
                              this.clickGuiState.setText("");
                           }

                           String text2 = this.clickGuiState.getText();
                           int intValue = 96 - text2.length();
                           if (intValue > 0) {
                              String text3 = text.length() > intValue ? text.substring(0, intValue) : text;
                              this.clickGuiState.setText(text2 + text3);
                           }

                           this.clickGuiState.setFlag4(false);
                           this.clickGuiState.setTimestamp4(System.currentTimeMillis());
                        }

                        return true;
                     case 88:
                        if (!this.clickGuiState.getText().isEmpty()) {
                           MinecraftClient.getInstance().keyboard.setClipboard(this.clickGuiState.getText());
                           this.clickGuiState.setText("");
                           this.clickGuiState.setFlag4(false);
                           this.clickGuiState.setTimestamp4(System.currentTimeMillis());
                        }

                        return true;
                  }
               } else if (i == 263 || i == 262) {
                  this.clickGuiState.setFlag4(false);
               }
            }

            if (i == 70 && Screen.hasControlDown()) {
               this.clickGuiState.invoke51();
               this.clickGuiState.setFlag6(false);
               this.clickGuiState.setFlag5(true);
               this.clickGuiState.setFlag4(true);
               return true;
            } else {
               return this.clickGuiInputController.check8(this.clickGuiState, i);
            }
         }
      }
   }

   boolean check7(char c) {
      if (this.clickGuiState.isFlag7()) {
         return true;
      } else {
         ShaderFoundryScreen shaderFoundryScreen7 = this.resolve();
         if (shaderFoundryScreen7 != null) {
            shaderFoundryScreen7.check14(this.clickGuiState, c);
            return true;
         } else {
            return this.clickGuiState.isFlag3()
                  && this.clickGuiRenderer.resolve() != null
                  && this.clickGuiRenderer.resolve().check9(this.clickGuiState, c)
               ? true
               : this.clickGuiInputController.check9(this.clickGuiState, c);
         }
      }
   }

   boolean check8() {
      if (this.clickGuiState.isFlag7()) {
         return false;
      } else {
         this.clickGuiState.invoke3();
         return true;
      }
   }

   boolean check9() {
      return this.clickGuiState.check(this.springSpec);
   }

   boolean check10() {
      return this.clickGuiState.isFlag7();
   }

   public boolean check11() {
      return this.clickGuiState.isFlag24()
         || this.clickGuiState.isFlag3() && this.clickGuiRenderer.resolve() != null && this.clickGuiRenderer.resolve().check3()
         || this.clickGuiState.isFlag5()
         || this.clickGuiState.isFlag6()
         || this.clickGuiState.getTextSetting() != null
         || this.clickGuiState.check4();
   }

   public void invoke4() {
      this.invoke7(MinecraftClient.getInstance());
   }

   void invoke5() {
      this.flag2 = true;
      this.invoke13();
   }

   void invoke6() {
      if (!this.flag) {
         this.flag = true;
         ClickGuiDragRegistry.invoke2();
         this.clickGuiState.invoke51();
         this.clickGuiState.setFlag5(false);
         this.clickGuiState.setFlag24(false);
         ShaderFoundryScreen shaderFoundryScreen8 = this.shaderFoundryScreen;
         if (shaderFoundryScreen8 != null) {
            shaderFoundryScreen8.close();
         }

         StudioPanelRenderer studioPanelRenderer = this.clickGuiRenderer.resolve();
         if (studioPanelRenderer != null) {
            studioPanelRenderer.invoke();
         }

         SpecialModuleCardHandlers.invoke2(this.clickGuiState);
         if (WildClient.INSTANCE != null && WildClient.INSTANCE.themeManager != null) {
            WildClient.INSTANCE.themeManager.invoke3(this.clickGuiState.getCategory());
            WildClient.INSTANCE.themeManager.invoke2(this.clickGuiState.getTheme());
            WildClient.INSTANCE.themeManager.invoke4(this.clickGuiState.getFloatValue5(), this.clickGuiState.getFloatValue6());
            WildClient.INSTANCE.themeManager.invoke5(this.clickGuiState.isFlag22());
         }
      }
   }

   private void invoke7(MinecraftClient minecraftClient) {
      if (minecraftClient != null && minecraftClient.getWindow() != null) {
         int intValue2 = minecraftClient.getWindow().getFramebufferWidth();
         int intValue3 = minecraftClient.getWindow().getFramebufferHeight();
         if (intValue2 > 0 && intValue3 > 0) {
            this.invoke8(minecraftClient, intValue2, intValue3);
         }
      }
   }

   private void invoke8(MinecraftClient minecraftClient, int i, int j) {
      this.invoke9();
      this.invoke11(minecraftClient, i, j);
      this.invoke10();
      this.clickGuiState.invoke69(this.springSpec15);
      this.themeContext = this.resolve3();
      this.clickGuiState.measure(this.springSpec);
      this.invoke14();
      this.moduleLayoutResult = this.moduleLayoutEngine.resolve(this.clickGuiState, this.clickGuiGeometry, this.metrics);
      this.clickGuiState.setFloatValue13(this.moduleLayoutResult.getFloatValue());
      this.clickGuiState.invoke65(this.metrics);
      this.clickGuiState.invoke53(this.springSpec7, this.metrics);
      this.moduleLayoutResult = this.moduleLayoutEngine.resolve(this.clickGuiState, this.clickGuiGeometry, this.metrics);
      this.invoke18();
      this.invoke24();
   }

   private void invoke9() {
      this.springSpec = SpringSpec.resolve6();
      this.springSpec2 = SpringSpec.resolve2();
      this.springSpec3 = SpringSpec.resolve3();
      this.springSpec4 = SpringSpec.resolve4();
      this.springSpec5 = SpringSpec.resolve5();
      this.springSpec6 = SpringSpec.resolve9();
      this.springSpec7 = SpringSpec.resolve8();
      this.springSpec8 = SpringSpec.resolve10();
      this.springSpec9 = SpringSpec.resolve11();
      this.springSpec10 = SpringSpec.resolve12();
      this.springSpec11 = SpringSpec.resolve13();
      this.springSpec12 = SpringSpec.resolve14();
      this.springSpec13 = SpringSpec.resolve15();
      this.springSpec14 = SpringSpec.resolve16();
      this.springSpec15 = SpringSpec.resolve17();
   }

   private void invoke10() {
      Theme theme = this.clickGuiState.getTheme();
      ColorScheme colorScheme = ColorScheme.resolve2(theme, this.check12(theme));
      if (this.theme == null) {
         this.clickGuiState.invoke67(colorScheme);
      } else if (this.theme != theme) {
         this.clickGuiState.setTheme2(this.theme);
         this.clickGuiState.invoke68(colorScheme);
      }

      this.theme = theme;
   }

   private void invoke11(MinecraftClient minecraftClient, int i, int j) {
      if (minecraftClient != null && minecraftClient.getWindow() != null) {
         int intValue4 = i > 0 ? i : minecraftClient.getWindow().getFramebufferWidth();
         int intValue5 = j > 0 ? j : minecraftClient.getWindow().getFramebufferHeight();
         if (intValue4 <= 0 || intValue5 <= 0) {
            return;
         }

         this.metrics = this.clickGuiLayoutEngine.resolve3(minecraftClient, intValue4, intValue5, this.clickGuiState, this.clickGuiGeometry);
         ClickGuiRenderUtils.invoke(this.metrics);
      }
   }

   private void invoke12(MinecraftClient minecraftClient) {
      this.invoke13();
      if (minecraftClient != null && minecraftClient.mouse != null) {
         minecraftClient.mouse.unlockCursor();
      }
   }

   private void invoke13() {
      ClickGuiDragRegistry.invoke2();
      this.clickGuiState.invoke51();
      this.clickGuiState.setFlag5(false);
      this.clickGuiState.setFlag6(false);
      this.clickGuiState.invoke43();
   }

   private ShaderFoundryScreen resolve() {
      ShaderFoundryScreen shaderFoundryScreen9 = this.shaderFoundryScreen;
      if (shaderFoundryScreen9 != null && shaderFoundryScreen9.check(this.clickGuiState)) {
         return shaderFoundryScreen9;
      } else {
         if (this.clickGuiState.isFlag24() || this.clickGuiState.measure7(AnimationKeyRegistry.resolve56()) > 0.035F) {
            shaderFoundryScreen9 = this.resolve2();
            if (shaderFoundryScreen9 != null && shaderFoundryScreen9.check(this.clickGuiState)) {
               return shaderFoundryScreen9;
            }
         }

         return null;
      }
   }

   private ShaderFoundryScreen resolve2() {
      if (this.shaderFoundryScreen != null) {
         return this.shaderFoundryScreen;
      } else {
         try {
            this.shaderFoundryScreen = new ShaderFoundryScreen();
            return this.shaderFoundryScreen;
         } catch (Throwable exception) {
            this.clickGuiState.setFlag24(false);
            System.out.println("[ModernClickGui] Foundry init failed: " + exception.getClass().getSimpleName() + ": " + exception.getMessage());
            return null;
         }
      }
   }

   private ThemeContext resolve3() {
      Theme theme2 = this.clickGuiState.getTheme();
      ColorScheme colorScheme2 = this.clickGuiState.resolve4();
      if (colorScheme2 == null) {
         colorScheme2 = ColorScheme.resolve2(theme2, this.check12(theme2));
      }

      if (this.clickGuiState.getTimestamp8() <= 0L) {
         colorScheme2 = ColorScheme.resolve3(theme2, colorScheme2, System.currentTimeMillis());
      }

      return ThemeContext.resolve().setTheme(theme2).setMetrics(this.metrics).setColorScheme(colorScheme2).setThemePalette(this.themePalette).resolve();
   }

   private boolean check12(Theme theme3) {
      return this.themePalette != null && this.themePalette.check(theme3);
   }

   private boolean check13(float f, float g) {
      return ClickGuiRenderUtils.check2(
         f, g, this.clickGuiGeometry.getFloatValue(), this.clickGuiGeometry.getFloatValue2(), this.metrics.getFloatValue3(), this.metrics.getFloatValue4()
      );
   }

   private int compute() {
      MinecraftClient client = MinecraftClient.getInstance();
      return client != null && client.getWindow() != null ? Math.max(1, client.getWindow().getFramebufferWidth()) : Math.round(this.metrics.getFloatValue3());
   }

   private int compute2() {
      MinecraftClient client2 = MinecraftClient.getInstance();
      return client2 != null && client2.getWindow() != null ? Math.max(1, client2.getWindow().getFramebufferHeight()) : Math.round(this.metrics.getFloatValue4());
   }

   private void invoke14() {
      boolean flag10 = !this.clickGuiState.isFlag7() && this.clickGuiState.isFlag5();
      boolean flag11 = !this.clickGuiState.isFlag7() && !this.clickGuiState.getText().isEmpty();
      boolean flag12 = !this.clickGuiState.isFlag7() && this.clickGuiState.isFlag22();
      boolean flag13 = !this.clickGuiState.isFlag7() && this.clickGuiState.isFlag24();
      boolean flag14 = !this.clickGuiState.isFlag7() && this.clickGuiState.isFlag2();
      this.clickGuiState.measure5(AnimationKeyRegistry.resolve(), flag10 ? 1.0F : 0.0F, this.resolve4(flag10, this.springSpec6));
      this.clickGuiState.measure5(AnimationKeyRegistry.resolve29(), flag11 ? 1.0F : 0.0F, this.resolve4(flag11, this.springSpec2));
      this.clickGuiState.measure5(AnimationKeyRegistry.resolve32(), flag12 ? 1.0F : 0.0F, flag12 ? this.springSpec : this.springSpec2);
      this.clickGuiState.measure5(AnimationKeyRegistry.resolve56(), flag13 ? 1.0F : 0.0F, flag13 ? this.springSpec : this.springSpec14);
      boolean flag15 = !this.clickGuiState.isFlag7()
         && ClickGuiRenderUtils.check(
            this.clickGuiState,
            SearchBarRenderer.measure7(this.clickGuiGeometry, this.metrics),
            SearchBarRenderer.measure9(this.clickGuiGeometry, this.metrics),
            SearchBarRenderer.measure5(this.metrics),
            SearchBarRenderer.measure6(this.metrics)
         );
      boolean flag16 = flag15 || flag13;
      this.clickGuiState.measure5(AnimationKeyRegistry.resolve57(), flag16 ? 1.0F : 0.0F, this.resolve4(flag16, this.springSpec9));
      boolean flag17 = !this.clickGuiState.isFlag7() && this.clickGuiState.isFlag3();
      boolean flag18 = StudioAccessControl.check()
         && !this.clickGuiState.isFlag7()
         && ClickGuiRenderUtils.check(
            this.clickGuiState,
            SearchBarRenderer.measure3(this.clickGuiGeometry, this.metrics),
            SearchBarRenderer.measure4(this.clickGuiGeometry, this.metrics),
            SearchBarRenderer.measure(this.metrics),
            SearchBarRenderer.measure2(this.metrics)
         );
      boolean flag19 = flag18 || flag17;
      this.clickGuiState.measure5(AnimationKeyRegistry.resolve59(), flag19 ? 1.0F : 0.0F, this.resolve4(flag19, this.springSpec9));
      this.clickGuiState.measure5(AnimationKeyRegistry.resolve7(), flag14 ? 1.0F : 0.0F, flag14 ? this.springSpec : this.springSpec14);
      this.invoke15();
      this.invoke16();
      this.invoke17();
      this.invoke21();
      this.invoke22();
      this.invoke23();
   }

   private void invoke15() {
      float floatValue8 = SidebarRenderer.measure5(this.clickGuiGeometry, this.metrics);
      float floatValue9 = this.clickGuiGeometry.getFloatValue4() + this.metrics.measure(89.0F);
      Category[] categories = Category.values();

      for (int intValue6 = 0; intValue6 < categories.length; intValue6++) {
         Category category = categories[intValue6];
         float floatValue10 = floatValue9 + intValue6 * this.metrics.measure(56.0F);
         boolean flag20 = !this.clickGuiState.isFlag7()
            && ClickGuiRenderUtils.check(this.clickGuiState, floatValue8, floatValue10, this.metrics.measure(40.0F), this.metrics.measure(40.0F));
         boolean flag21 = !this.clickGuiState.isFlag7()
            && !this.clickGuiState.isFlag()
            && !this.clickGuiState.isFlag2()
            && !this.clickGuiState.isFlag3()
            && category == this.clickGuiState.getCategory();
         this.clickGuiState.measure5(AnimationKeyRegistry.resolve11(category), flag20 ? 1.0F : 0.0F, this.resolve4(flag20, this.springSpec9));
         this.clickGuiState.measure5(AnimationKeyRegistry.resolve12(category), flag21 ? 1.0F : 0.0F, this.resolve4(flag21, this.springSpec2));
      }

      float floatValue11 = SidebarRenderer.measure4(this.metrics);
      float floatValue12 = SidebarRenderer.measure7(this.clickGuiGeometry, this.metrics);
      boolean flag22 = !this.clickGuiState.isFlag7() && ClickGuiRenderUtils.check(this.clickGuiState, floatValue8, floatValue12, floatValue11, floatValue11);
      boolean flag23 = !this.clickGuiState.isFlag7() && this.clickGuiState.isFlag();
      this.clickGuiState.measure5(AnimationKeyRegistry.resolve5(), flag22 ? 1.0F : 0.0F, this.resolve4(flag22, this.springSpec9));
      this.clickGuiState.measure5(AnimationKeyRegistry.resolve6(), flag23 ? 1.0F : 0.0F, this.resolve4(flag23, this.springSpec2));
      float floatValue13 = SidebarRenderer.measure5(this.clickGuiGeometry, this.metrics);
      float floatValue14 = SidebarRenderer.measure6(this.clickGuiGeometry, this.metrics);
      boolean flag24 = !this.clickGuiState.isFlag7() && ClickGuiRenderUtils.check(this.clickGuiState, floatValue13, floatValue14, floatValue11, floatValue11);
      this.clickGuiState.measure5(AnimationKeyRegistry.resolve2(), flag24 ? 1.0F : 0.0F, this.resolve4(flag24, this.springSpec9));
   }

   private void invoke16() {
      if (!this.clickGuiState.isFlag22() && this.clickGuiState.measure7(AnimationKeyRegistry.resolve32()) <= 0.005F) {
         this.clickGuiState.setFloatValue19(0.0F);
      } else {
         ThemeGridLayout themeGridLayout = ThemeGridLayout.resolve(this.clickGuiGeometry, this.metrics);
          List items = this.clickGuiState.resolve3(this.themePalette);
          if (items == null) {
             items = List.of();
         }
         this.clickGuiState.setFloatValue19(Math.max(0.0F, themeGridLayout.measure(items.size()) - themeGridLayout.getFloatValue4() + this.metrics.measure2(2.0F)));
         float floatValue15 = this.clickGuiState.measure3(this.springSpec7, this.metrics);
         float floatValue16 = themeGridLayout.getFloatValue();
         float floatValue17 = themeGridLayout.getFloatValue2();
         float floatValue18 = themeGridLayout.getFloatValue3();
         float floatValue19 = themeGridLayout.getFloatValue4();
         boolean flag25 = !this.clickGuiState.isFlag7() && ClickGuiRenderUtils.check(this.clickGuiState, floatValue16, floatValue17, floatValue18, floatValue19);
         boolean flag26 = !this.clickGuiState.isFlag7() && this.clickGuiState.isFlag6();
         boolean flag27 = !this.clickGuiState.isFlag7() && !this.clickGuiState.getText2().isEmpty();
         this.clickGuiState.measure5(AnimationKeyRegistry.resolve33(), flag26 ? 1.0F : 0.0F, this.resolve4(flag26, this.springSpec6));
         this.clickGuiState.measure5(AnimationKeyRegistry.resolve34(), flag27 ? 1.0F : 0.0F, this.resolve4(flag27, this.springSpec2));
         int intValue7 = this.clickGuiState.getIntValue2() >= 0
            ? this.clickGuiState.getIntValue2()
            : this.themePalette.compute(this.clickGuiState.getTheme());
         float floatValue20 = this.clickGuiState.getFloatValue65();
         float floatValue21 = this.clickGuiState.getFloatValue66();
         float floatValue22 = this.clickGuiState.getFloatValue67();
         float floatValue23 = this.clickGuiState.getFloatValue68();
         boolean flag28 = false;
         int intValue8 = this.themePalette.getItems().size();
         int[] intValues = new int[intValue8];
         Arrays.fill(intValues, -1);
          int intValue9 = 0;

          while (intValue9 < items.size()) {
             Object object = items.get(intValue9);
             if (object instanceof Integer integerValue && integerValue >= 0 && integerValue < intValues.length) {
                intValues[integerValue] = intValue9;
             }
             intValue9++;
          }

          for (int intValue10 = 0; intValue10 < intValue8; intValue10++) {
             int intValue11 = intValues[intValue10];
             if (intValue11 < 0) {
                this.clickGuiState.measure5(AnimationKeyRegistry.resolve13(intValue10), 0.0F, this.springSpec14);
                this.clickGuiState.measure5(AnimationKeyRegistry.resolve14(intValue10), 0.0F, this.springSpec14);
                this.clickGuiState.measure5(AnimationKeyRegistry.resolve31(intValue10), 0.0F, this.springSpec14);
             } else {
                ThemeGridLayout.Cell cell = themeGridLayout.resolve2(intValue11, floatValue15);
                boolean flag29 = flag25 && ClickGuiRenderUtils.check(this.clickGuiState, cell.x(), cell.y(), cell.width(), cell.height());
                boolean flag30 = !this.clickGuiState.isFlag7() && intValue10 == intValue7;
                this.clickGuiState.measure5(AnimationKeyRegistry.resolve13(intValue10), flag29 ? 1.0F : 0.0F, this.resolve4(flag29, this.springSpec9));
                this.clickGuiState.measure5(AnimationKeyRegistry.resolve14(intValue10), flag30 ? 1.0F : 0.0F, this.resolve4(flag30, this.springSpec2));
                this.clickGuiState.measure5(AnimationKeyRegistry.resolve31(intValue10), flag29 ? 1.0F : 0.0F, this.resolve4(flag29, this.springSpec8));
                if (flag30) {
                   floatValue20 = cell.x();
                   floatValue21 = cell.y();
                   floatValue22 = cell.width();
                   floatValue23 = cell.height();
                   flag28 = true;
                }
            }
         }

         this.clickGuiState.setFlag26(intValue7 >= 0 && intValue7 < intValue8 && intValues[intValue7] >= 0);
         if (flag28) {
            this.clickGuiState.invoke12(intValue7, floatValue20, floatValue21, floatValue22, floatValue23);
         }
      }
   }

   private void invoke17() {
      List items2 = this.clickGuiState.resolve();
      HashSet hashSet = new HashSet<>(this.clickGuiState.resolve2());

      for (Module module2 : (List<Module>)items2) {
         boolean flag31 = hashSet.contains(module2) && !this.clickGuiState.isFlag7();
         if (flag31) {
            boolean flag32 = this.clickGuiState.getValues().contains(module2);
            boolean flag33 = module2.enabled;
            this.clickGuiState.measure5(AnimationKeyRegistry.resolve15(module2), flag32 ? 1.0F : 0.0F, flag32 ? this.springSpec3 : this.springSpec4);
            this.clickGuiState.measure5(AnimationKeyRegistry.resolve18(module2), flag32 ? 1.0F : 0.0F, this.resolve4(flag32, this.springSpec2));
            this.clickGuiState.measure5(AnimationKeyRegistry.resolve17(module2), flag33 ? 1.0F : 0.0F, this.resolve4(flag33, this.springSpec2));
            if (SpecialModuleCardHandlers.check(module2)) {
               SpecialModuleCardHandlers.invoke3(module2, this.clickGuiState, this.springSpec2, this.springSpec8);
            } else {
               this.invoke20(module2);
            }
         } else {
            this.clickGuiState.measure5(AnimationKeyRegistry.resolve15(module2), 0.0F, this.springSpec14);
            this.clickGuiState.measure5(AnimationKeyRegistry.resolve18(module2), 0.0F, this.springSpec14);
            this.clickGuiState.measure5(AnimationKeyRegistry.resolve17(module2), 0.0F, this.springSpec14);
         }
      }
   }

   private void invoke18() {
      List items3 = this.clickGuiState.resolve();
      HashSet hashSet2 = new HashSet<>(this.clickGuiState.resolve2());
      long longValue2 = System.currentTimeMillis() - this.clickGuiState.getTimestamp4();
      HashMap hashMap = new HashMap();

      for (ModulePlacement modulePlacement : this.moduleLayoutResult.getItems()) {
         hashMap.put(modulePlacement.getModule(), modulePlacement);
      }

      float floatValue24 = this.clickGuiGeometry.getFloatValue16();
      float floatValue25 = floatValue24 + this.clickGuiGeometry.getFloatValue18();
      int intValue12 = 0;

      for (Module module3 : (List<Module>)items3) {
         if (!this.clickGuiState.isFlag7() && hashSet2.contains(module3)) {
            ModulePlacement modulePlacement2 = (ModulePlacement)hashMap.get(module3);
            boolean flag34 = modulePlacement2 != null && modulePlacement2.getFloatValue2() + modulePlacement2.getFloatValue4() >= floatValue24 && modulePlacement2.getFloatValue2() <= floatValue25;
            if (flag34) {
               long longValue3 = 95L + intValue12 * 55L;
               float floatValue26 = longValue2 >= longValue3 ? 1.0F : 0.0F;
               this.clickGuiState.measure9(AnimationKeyRegistry.resolve26(module3), floatValue26, floatValue26 > 0.0F ? this.springSpec2 : this.springSpec14);
               this.clickGuiState.measure9(AnimationKeyRegistry.resolve27(module3), floatValue26, floatValue26 > 0.0F ? this.springSpec11 : this.springSpec14);
               this.clickGuiState.measure9(AnimationKeyRegistry.resolve28(module3), floatValue26, floatValue26 > 0.0F ? this.springSpec13 : this.springSpec14);
               intValue12++;
            } else {
               this.invoke19(AnimationKeyRegistry.resolve26(module3));
               this.invoke19(AnimationKeyRegistry.resolve27(module3));
               this.invoke19(AnimationKeyRegistry.resolve28(module3));
            }
         } else {
            this.clickGuiState.measure5(AnimationKeyRegistry.resolve26(module3), 0.0F, this.springSpec14);
            this.clickGuiState.measure5(AnimationKeyRegistry.resolve27(module3), 0.0F, this.springSpec14);
            this.clickGuiState.measure5(AnimationKeyRegistry.resolve28(module3), 0.0F, this.springSpec14);
         }
      }
   }

   private void invoke19(String string) {
      this.clickGuiState.getValuesByKey().computeIfAbsent(string, stringx -> new SpringAnimation(1.0F)).invoke(1.0F);
   }

   private void invoke20(Module module) {
      boolean flag35 = !this.clickGuiState.isFlag7() && this.clickGuiState.getValues().contains(module);
      long longValue4 = this.clickGuiState.compute2(module);
      long longValue5 = System.currentTimeMillis();
      int intValue13 = 0;

      for (Setting setting : module.getVisibleSettings()) {
         if (!(setting instanceof SpacerSetting)) {
            float floatValue27;
            if (flag35) {
               if (longValue4 > 0L) {
                  long longValue6 = 60L + intValue13 * 55L;
                  floatValue27 = longValue5 - longValue4 > longValue6 ? 1.0F : 0.0F;
               } else {
                  floatValue27 = 1.0F;
               }
            } else {
               floatValue27 = 0.0F;
            }

            this.clickGuiState.measure9(AnimationKeyRegistry.resolve22(setting), floatValue27, floatValue27 > 0.0F ? this.springSpec5 : this.springSpec4);
            if (setting instanceof BooleanSetting booleanSetting) {
               boolean flag36 = !this.clickGuiState.isFlag7() && booleanSetting.isEnabled();
               this.clickGuiState.measure5(AnimationKeyRegistry.resolve19(setting), flag36 ? 1.0F : 0.0F, this.resolve4(flag36, this.springSpec2));
            } else if (setting instanceof NumberSetting numberSetting) {
               float floatValue28 = !this.clickGuiState.isFlag7() ? ClickGuiRenderUtils.measure10(numberSetting) : 0.0F;
               boolean flag37 = !this.clickGuiState.isFlag7() && this.clickGuiState.getNumberSetting() == numberSetting;
               this.clickGuiState.measure5(AnimationKeyRegistry.resolve19(setting), floatValue28, floatValue28 > 0.0F ? this.springSpec2 : this.springSpec14);
               this.clickGuiState.measure5(AnimationKeyRegistry.resolve35(setting), floatValue28, floatValue28 > 0.0F ? this.springSpec8 : this.springSpec14);
               this.clickGuiState.measure5(AnimationKeyRegistry.resolve36(setting), flag37 ? 1.0F : 0.0F, this.resolve4(flag37, this.springSpec2));
            } else if (setting instanceof ColorSetting colorSetting) {
               float floatValue29 = !this.clickGuiState.isFlag7() ? ClickGuiRenderUtils.measure11(colorSetting) : 0.0F;
               this.clickGuiState.measure5(AnimationKeyRegistry.resolve19(setting), floatValue29, floatValue29 > 0.0F ? this.springSpec2 : this.springSpec14);
               boolean flag38 = !this.clickGuiState.isFlag7() && this.clickGuiState.getColorSetting2() == colorSetting;
               float floatValue30 = this.clickGuiState.measure7(AnimationKeyRegistry.resolve37(setting));
               this.clickGuiState.measure5(AnimationKeyRegistry.resolve37(setting), flag38 ? 1.0F : 0.0F, this.resolve4(flag38, this.springSpec2));
               if (flag38) {
                  float floatValue31 = colorSetting.saturation;
                  float floatValue32 = 1.0F - colorSetting.brightness;
                  if (floatValue30 < 0.01F) {
                     SpringAnimation springAnimation = this.clickGuiState
                        .getValuesByKey()
                        .computeIfAbsent(AnimationKeyRegistry.resolve38(setting), string -> new SpringAnimation(floatValue31));
                     springAnimation.invoke(floatValue31);
                     SpringAnimation springAnimation2 = this.clickGuiState
                        .getValuesByKey()
                        .computeIfAbsent(AnimationKeyRegistry.resolve39(setting), string -> new SpringAnimation(floatValue32));
                     springAnimation2.invoke(floatValue32);
                     SpringAnimation springAnimation3 = this.clickGuiState
                        .getValuesByKey()
                        .computeIfAbsent(AnimationKeyRegistry.resolve40(setting), string -> new SpringAnimation(floatValue29));
                     springAnimation3.invoke(floatValue29);
                     SpringAnimation springAnimation4 = this.clickGuiState
                        .getValuesByKey()
                        .computeIfAbsent(AnimationKeyRegistry.resolve41(setting), string -> new SpringAnimation(colorSetting.floatValue3));
                     springAnimation4.invoke(colorSetting.floatValue3);
                  } else {
                     this.clickGuiState.measure5(AnimationKeyRegistry.resolve38(setting), floatValue31, this.springSpec8);
                     this.clickGuiState.measure5(AnimationKeyRegistry.resolve39(setting), floatValue32, this.springSpec8);
                     this.clickGuiState.measure5(AnimationKeyRegistry.resolve40(setting), floatValue29, this.springSpec8);
                     this.clickGuiState.measure5(AnimationKeyRegistry.resolve41(setting), colorSetting.floatValue3, this.springSpec8);
                  }
               } else {
                  this.clickGuiState.measure5(AnimationKeyRegistry.resolve38(setting), colorSetting.saturation, this.springSpec14);
                  this.clickGuiState.measure5(AnimationKeyRegistry.resolve39(setting), 1.0F - colorSetting.brightness, this.springSpec14);
                  this.clickGuiState.measure5(AnimationKeyRegistry.resolve40(setting), floatValue29, this.springSpec14);
                  this.clickGuiState.measure5(AnimationKeyRegistry.resolve41(setting), colorSetting.floatValue3, this.springSpec14);
               }
            } else if (setting instanceof ModeSetting modeSetting) {
               boolean flag39 = !this.clickGuiState.isFlag7() && modeSetting.expanded;
               this.clickGuiState.measure5(AnimationKeyRegistry.resolve30(setting), flag39 ? 1.0F : 0.0F, this.resolve4(flag39, this.springSpec2));
            } else if (setting instanceof FoundryShaderSetting foundryShaderSetting) {
               boolean flag40 = !this.clickGuiState.isFlag7() && foundryShaderSetting.expanded;
               this.clickGuiState.measure5(AnimationKeyRegistry.resolve30(setting), flag40 ? 1.0F : 0.0F, this.resolve4(flag40, this.springSpec2));
            } else if (setting instanceof GroupSetting groupSetting) {
               for (int intValue14 = 0; intValue14 < groupSetting.options.size(); intValue14++) {
                  boolean flag41 = !this.clickGuiState.isFlag7() && groupSetting.options.get(intValue14).isEnabled();
                  this.clickGuiState.measure5(AnimationKeyRegistry.resolve23(setting, intValue14), flag41 ? 1.0F : 0.0F, this.resolve4(flag41, this.springSpec2));
               }
            }

            intValue13++;
         }
      }
   }

   private void invoke21() {
      if (WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null) {
         AutoBuy autoBuy = WildClient.INSTANCE.moduleManager.getModule(AutoBuy.class);
         if (autoBuy != null) {
            SpecialModuleCardHandlers.invoke3(autoBuy, this.clickGuiState, this.springSpec2, this.springSpec8);
            boolean flag42 = !this.clickGuiState.isFlag7() && autoBuy.enabled;
            this.clickGuiState.measure5(AnimationKeyRegistry.resolve17(autoBuy), flag42 ? 1.0F : 0.0F, this.resolve4(flag42, this.springSpec2));
         }
      }
   }

   private void invoke22() {
      boolean flag43 = !this.clickGuiState.isFlag7() && this.clickGuiState.isFlag21();
      this.clickGuiState.measure5(AnimationKeyRegistry.resolve42(), flag43 ? 1.0F : 0.0F, this.resolve4(flag43, this.springSpec12));
   }

   private void invoke23() {
      if (!this.clickGuiState.isFlag21() && !this.clickGuiState.isFlag2()) {
         boolean flag44 = false;
         float floatValue33 = SidebarRenderer.measure5(this.clickGuiGeometry, this.metrics);
         float floatValue34 = this.clickGuiGeometry.getFloatValue4() + this.metrics.measure(89.0F);
         if (ClickGuiRenderUtils.check(
            this.clickGuiState,
            SidebarRenderer.measure(this.clickGuiGeometry, this.metrics),
            SidebarRenderer.measure2(this.clickGuiGeometry, this.metrics),
            SidebarRenderer.measure3(this.metrics),
            SidebarRenderer.measure3(this.metrics)
         )) {
            flag44 = true;
         }

         if (ClickGuiRenderUtils.check(
            this.clickGuiState,
            SearchBarRenderer.measure7(this.clickGuiGeometry, this.metrics),
            SearchBarRenderer.measure9(this.clickGuiGeometry, this.metrics),
            SearchBarRenderer.measure5(this.metrics),
            SearchBarRenderer.measure6(this.metrics)
         )) {
            flag44 = true;
         }

         if (StudioAccessControl.check()
            && ClickGuiRenderUtils.check(
               this.clickGuiState,
               SearchBarRenderer.measure3(this.clickGuiGeometry, this.metrics),
               SearchBarRenderer.measure4(this.clickGuiGeometry, this.metrics),
               SearchBarRenderer.measure(this.metrics),
               SearchBarRenderer.measure2(this.metrics)
            )) {
            flag44 = true;
         }

         Category[] categories2 = Category.values();

         for (int intValue15 = 0; intValue15 < categories2.length; intValue15++) {
            float floatValue35 = floatValue34 + intValue15 * this.metrics.measure(56.0F);
            if (ClickGuiRenderUtils.check(this.clickGuiState, floatValue33, floatValue35, this.metrics.measure(40.0F), this.metrics.measure(40.0F))) {
               flag44 = true;
               break;
            }
         }

         float floatValue36 = SidebarRenderer.measure4(this.metrics);
         float floatValue37 = SidebarRenderer.measure7(this.clickGuiGeometry, this.metrics);
         if (ClickGuiRenderUtils.check(this.clickGuiState, floatValue33, floatValue37, floatValue36, floatValue36)) {
            flag44 = true;
         }

         float floatValue38 = SidebarRenderer.measure5(this.clickGuiGeometry, this.metrics);
         float floatValue39 = SidebarRenderer.measure6(this.clickGuiGeometry, this.metrics);
         if (ClickGuiRenderUtils.check(this.clickGuiState, floatValue38, floatValue39, floatValue36, floatValue36)) {
            flag44 = true;
         }

         if (this.clickGuiState.isFlag22() && this.clickGuiState.getText7() != null && this.clickGuiState.getText7().startsWith("theme:")) {
            ThemeGridLayout themeGridLayout2 = ThemeGridLayout.resolve(this.clickGuiGeometry, this.metrics);
            if (ClickGuiRenderUtils.check(this.clickGuiState, themeGridLayout2.getFloatValue(), themeGridLayout2.getFloatValue2(), themeGridLayout2.getFloatValue3(), themeGridLayout2.getFloatValue4())) {
               float floatValue40 = this.clickGuiState.measure4();
               List items4 = this.clickGuiState.resolve3(this.themePalette);

               for (int intValue16 = 0; intValue16 < items4.size(); intValue16++) {
                  int intValue17 = (Integer)items4.get(intValue16);
                  ThemeGridLayout.Cell cell2 = themeGridLayout2.resolve2(intValue16, floatValue40);
                  if (("theme:" + intValue17).equals(this.clickGuiState.getText7())
                     && ClickGuiRenderUtils.check(this.clickGuiState, cell2.x(), cell2.y(), cell2.width(), cell2.height())) {
                     flag44 = true;
                     break;
                  }
               }
            }
         }

         if (!flag44) {
            this.clickGuiState.invoke43();
         }

         boolean flag45 = !this.clickGuiState.isFlag7() && this.clickGuiState.check3();
         this.clickGuiState.measure5(AnimationKeyRegistry.resolve43(), flag45 ? 1.0F : 0.0F, this.resolve4(flag45, this.springSpec2));
      } else {
         this.clickGuiState.invoke43();
         this.clickGuiState.measure5(AnimationKeyRegistry.resolve43(), 0.0F, this.springSpec14);
      }
   }

   private void invoke24() {
      for (ModulePlacement modulePlacement3 : this.moduleLayoutResult.getItems()) {
         boolean flag46 = !this.clickGuiState.isFlag7()
            && ClickGuiRenderUtils.check(this.clickGuiState, modulePlacement3.getFloatValue(), modulePlacement3.getFloatValue2(), modulePlacement3.getFloatValue3(), modulePlacement3.getFloatValue4());
         this.clickGuiState.measure5(AnimationKeyRegistry.resolve16(modulePlacement3.getModule()), flag46 ? 1.0F : 0.0F, this.springSpec10);
      }
   }

   private SpringSpec resolve4(boolean bl, SpringSpec springSpec) {
      return !this.clickGuiState.isFlag7() && bl ? springSpec : this.springSpec14;
   }

   @Generated
   public ClickGuiState getClickGuiState() {
      return this.clickGuiState;
   }

   @Generated
   public LayoutSpec getLayoutSpec() {
      return this.layoutSpec;
   }

   @Generated
   public ClickGuiGeometry getClickGuiGeometry() {
      return this.clickGuiGeometry;
   }

   @Generated
   public ClickGuiLayoutEngine getClickGuiLayoutEngine() {
      return this.clickGuiLayoutEngine;
   }

   @Generated
   public ModuleLayoutEngine getModuleLayoutEngine() {
      return this.moduleLayoutEngine;
   }

   @Generated
   public ClickGuiSettingController getClickGuiSettingController() {
      return this.clickGuiSettingController;
   }

   @Generated
   public CoreDiagnosticsPanelRenderer getCoreDiagnosticsPanelRenderer() {
      return this.coreDiagnosticsPanelRenderer;
   }

   @Generated
   public ClickGuiHitMapBuilder getClickGuiHitMapBuilder() {
      return this.clickGuiHitMapBuilder;
   }

   @Generated
   public ClickGuiInputController getClickGuiInputController() {
      return this.clickGuiInputController;
   }

   @Generated
   public ThemePalette getThemePalette() {
      return this.themePalette;
   }

   @Generated
   public ShaderFoundryScreen getShaderFoundryScreen() {
      return this.shaderFoundryScreen;
   }

   @Generated
   public SpringSpec getSpringSpec() {
      return this.springSpec;
   }

   @Generated
   public SpringSpec getSpringSpec2() {
      return this.springSpec2;
   }

   @Generated
   public SpringSpec getSpringSpec3() {
      return this.springSpec3;
   }

   @Generated
   public SpringSpec getSpringSpec4() {
      return this.springSpec4;
   }

   @Generated
   public SpringSpec getSpringSpec5() {
      return this.springSpec5;
   }

   @Generated
   public SpringSpec getSpringSpec6() {
      return this.springSpec6;
   }

   @Generated
   public SpringSpec getSpringSpec7() {
      return this.springSpec7;
   }

   @Generated
   public SpringSpec getSpringSpec8() {
      return this.springSpec8;
   }

   @Generated
   public SpringSpec getSpringSpec9() {
      return this.springSpec9;
   }

   @Generated
   public SpringSpec getSpringSpec10() {
      return this.springSpec10;
   }

   @Generated
   public SpringSpec getSpringSpec11() {
      return this.springSpec11;
   }

   @Generated
   public SpringSpec getSpringSpec12() {
      return this.springSpec12;
   }

   @Generated
   public SpringSpec getSpringSpec13() {
      return this.springSpec13;
   }

   @Generated
   public SpringSpec getSpringSpec14() {
      return this.springSpec14;
   }

   @Generated
   public SpringSpec getSpringSpec15() {
      return this.springSpec15;
   }

   @Generated
   public ClickGuiRenderer getClickGuiRenderer() {
      return this.clickGuiRenderer;
   }

   @Generated
   public Metrics getMetrics() {
      return this.metrics;
   }

   @Generated
   public ThemeContext getThemeContext() {
      return this.themeContext;
   }

   @Generated
   public ModuleLayoutResult getModuleLayoutResult() {
      return this.moduleLayoutResult;
   }

   @Generated
   public boolean isFlag() {
      return this.flag;
   }

   @Generated
   public Theme getTheme() {
      return this.theme;
   }

   @Generated
   public long getTimestamp() {
      return this.timestamp;
   }

   @Generated
   public boolean isFlag2() {
      return this.flag2;
   }
}
