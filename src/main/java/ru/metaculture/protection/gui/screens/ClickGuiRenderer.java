package ru.metaculture.protection;

import lombok.Generated;
import net.minecraft.client.gui.DrawContext;
import org.lwjgl.opengl.GL11;

public final class ClickGuiRenderer {
   private final ThemePanelRenderer themePanelRenderer;
   private final SidebarRenderer sidebarRenderer;
   private final SearchBarRenderer searchBarRenderer;
   private final ContentPanelRenderer contentPanelRenderer;
   private final CoreDiagnosticsPanelRenderer coreDiagnosticsPanelRenderer;
   private final TooltipRenderer tooltipRenderer;
   private static final OffscreenFramebuffer OFFSCREEN_FRAMEBUFFER = new OffscreenFramebuffer();

   public StudioPanelRenderer resolve() {
      return this.contentPanelRenderer.getStudioPanelRenderer();
   }

   public void invoke(
      RenderManager renderManager,
      DrawContext drawContext,
      ClickGuiState clickGuiState,
      ClickGuiGeometry clickGuiGeometry,
      ModuleLayoutResult moduleLayoutResult,
      ThemeContext themeContext,
      int i,
      int j
   ) {
      if (renderManager != null && clickGuiState != null && clickGuiGeometry != null && moduleLayoutResult != null && themeContext != null && i > 0 && j > 0) {
         float floatValue = clickGuiState.measure2();
         if (!(floatValue <= 0.001F)) {
            Metrics metrics = themeContext.getMetrics();
            if (metrics != null) {
               RenderDiagnosticsTracker.getInstance().invoke7(i, j);

               try {
                  boolean flag = themeContext.check();
                  boolean flag2 = themeContext.getTheme() == Theme.SAKURA_BREEZE;
                  boolean flag3 = themeContext.getTheme() == Theme.VERNAL_SOLSTICE;
                  boolean flag4 = themeContext.getTheme() == Theme.MIDNIGHT_AZURE;
                  boolean flag5 = themeContext.getTheme() == Theme.FRUTIGER_AERO;
                  boolean flag6 = themeContext.getTheme() == Theme.PORCELAIN_DAWN;
                  boolean flag7 = themeContext.getTheme() == Theme.VELVET_DUSK;
                  boolean flag8 = themeContext.getTheme() == Theme.OBSIDIAN_EMBER;
                  boolean flag9 = themeContext.getTheme() == Theme.GLACIER_VEIL;
                  boolean flag10 = FontRenderer.check(flag);

                  try {
                     BlurRenderer.getINSTANCE()
                        .invoke4(
                           clickGuiGeometry.getFloatValue(),
                           clickGuiGeometry.getFloatValue2(),
                           metrics.getFloatValue3(),
                           metrics.getFloatValue4(),
                           metrics.measure(24.0F),
                           clickGuiState.isFlag22() ? clickGuiGeometry.getFloatValue22() : clickGuiGeometry.getFloatValue(),
                           clickGuiState.isFlag22() ? clickGuiGeometry.getFloatValue23() : clickGuiGeometry.getFloatValue2(),
                           clickGuiState.isFlag22() ? metrics.getFloatValue18() : 0.0F,
                           clickGuiState.isFlag22() ? metrics.getFloatValue19() : 0.0F,
                           metrics.measure(14.0F)
                        );
                     if (MenuVisualPreferences.check()) {
                        renderManager.invoke48(32.0F);
                     }

                     float floatValue2 = (float)(System.currentTimeMillis() % 10000L) / 10000.0F;
                     float floatValue3 = clickGuiState.measure10();
                     renderManager.invoke65(floatValue);

                     try {
                        boolean flag11 = false;

                        try {
                           flag11 = !flag4 && MenuModule.FON_CLICKGUI.is("Голограмма") && MenuVisualPreferences.check();
                        } catch (Throwable exception) {
                        }

                        if (flag11) {
                           try {
                              renderManager.invoke20();
                              ClickGuiHolographicBlurShader.resolve()
                                 .invoke(
                                    i,
                                    j,
                                    clickGuiState.getFloatValue(),
                                    clickGuiState.getFloatValue2(),
                                    floatValue,
                                    MenuModule.MAKSIMALNYY_BLYUR.getValue(),
                                    MenuModule.IRIDISTSENTNYY_OTLIV.getValue(),
                                    MenuModule.PRITYAZHENIE_KKURSORU.getValue(),
                                    MenuModule.RADIUS_PROZRACHNOSTI_UKURSORA.getValue(),
                                    MenuModule.RAZMER_OSTROVKOV.getValue(),
                                    MenuModule.SKOROST_TECHENIYA.getValue(),
                                    MenuModule.KONTRAST_OSTROVKOV.getValue(),
                                    MenuModule.VINETKA.getValue(),
                                    MenuModule.YARKOST.getValue(),
                                    MenuModule.NASYSCHENNOST.getValue()
                                 );
                           } catch (Throwable exception2) {
                              RenderDiagnosticsTracker.getInstance().fail("GuiRenderer.holoBlur", exception2);
                           }

                           renderManager.invoke4(
                              0.0F,
                              0.0F,
                              (float)i,
                              (float)j,
                              flag ? ColorScheme.compute5(255, 255, 255, Math.round(26.0F * floatValue)) : ColorScheme.compute5(0, 0, 0, Math.round(14.0F * floatValue))
                           );
                        } else {
                           renderManager.invoke4(
                              0.0F,
                              0.0F,
                              (float)i,
                              (float)j,
                              flag
                                 ? ColorScheme.compute5(255, 255, 255, Math.round((flag2 ? 22 : (flag3 ? 34 : (flag5 ? 24 : (flag6 ? 26 : 112)))) * floatValue))
                                 : (
                                    flag4
                                       ? ColorScheme.compute5(3, 7, 18, Math.round(36.0F * floatValue))
                                       : (
                                          flag7
                                             ? ColorScheme.compute5(19, 12, 32, Math.round(36.0F * floatValue))
                                             : (
                                                flag8
                                                   ? ColorScheme.compute5(12, 10, 11, Math.round(34.0F * floatValue))
                                                   : (
                                                      flag9
                                                         ? ColorScheme.compute5(7, 19, 32, Math.round(36.0F * floatValue))
                                                         : ColorScheme.compute5(0, 0, 0, Math.round(100.0F * floatValue))
                                                   )
                                             )
                                       )
                                 )
                           );
                        }

                        if (flag2) {
                           try {
                              renderManager.invoke20();
                              SakuraBreezeMenuShader.getINSTANCE()
                                 .invoke(i, j, clickGuiState.getFloatValue(), clickGuiState.getFloatValue2(), themeContext.getColorScheme(), floatValue);
                              renderManager.invoke20();
                              renderManager.invoke4(0.0F, 0.0F, (float)i, (float)j, ColorScheme.compute5(255, 255, 255, Math.round(4.0F * floatValue)));
                           } catch (Throwable exception3) {
                              RenderDiagnosticsTracker.getInstance().fail("GuiRenderer.sakuraBreeze", exception3);
                           }
                        }

                        if (flag3) {
                           try {
                              renderManager.invoke20();
                              VernalSolsticeMenuShader.getINSTANCE()
                                 .invoke(i, j, clickGuiState.getFloatValue(), clickGuiState.getFloatValue2(), themeContext.getColorScheme(), floatValue);
                              renderManager.invoke20();
                              renderManager.invoke4(0.0F, 0.0F, (float)i, (float)j, ColorScheme.compute5(255, 255, 255, Math.round(5.0F * floatValue)));
                           } catch (Throwable exception4) {
                              RenderDiagnosticsTracker.getInstance().fail("GuiRenderer.vernalSolstice", exception4);
                           }
                        }

                        if (flag4) {
                           try {
                              renderManager.invoke20();
                              MidnightAzureMenuShader.getINSTANCE()
                                 .invoke(i, j, clickGuiState.getFloatValue(), clickGuiState.getFloatValue2(), themeContext.getColorScheme(), floatValue);
                              renderManager.invoke20();
                              renderManager.invoke4(0.0F, 0.0F, (float)i, (float)j, ColorScheme.compute5(3, 7, 18, Math.round(18.0F * floatValue)));
                           } catch (Throwable exception5) {
                              RenderDiagnosticsTracker.getInstance().fail("GuiRenderer.midnightAzure", exception5);
                           }
                        }

                        if (flag5) {
                           try {
                              renderManager.invoke20();
                              FrutigerAeroMenuShader.getINSTANCE()
                                 .invoke(i, j, clickGuiState.getFloatValue(), clickGuiState.getFloatValue2(), themeContext.getColorScheme(), floatValue);
                              renderManager.invoke20();
                              renderManager.invoke4(0.0F, 0.0F, (float)i, (float)j, ColorScheme.compute5(255, 255, 255, Math.round(4.0F * floatValue)));
                           } catch (Throwable exception6) {
                              RenderDiagnosticsTracker.getInstance().fail("GuiRenderer.frutigerAero", exception6);
                           }
                        }

                        if (flag6) {
                           try {
                              renderManager.invoke20();
                              PorcelainDawnMenuShader.getINSTANCE()
                                 .invoke(i, j, clickGuiState.getFloatValue(), clickGuiState.getFloatValue2(), themeContext.getColorScheme(), floatValue);
                              renderManager.invoke20();
                              renderManager.invoke4(0.0F, 0.0F, (float)i, (float)j, ColorScheme.compute5(255, 255, 255, Math.round(5.0F * floatValue)));
                           } catch (Throwable exception7) {
                              RenderDiagnosticsTracker.getInstance().fail("GuiRenderer.porcelainDawn", exception7);
                           }
                        }

                        if (flag7) {
                           try {
                              renderManager.invoke20();
                              VelvetDuskMenuShader.getINSTANCE()
                                 .invoke(i, j, clickGuiState.getFloatValue(), clickGuiState.getFloatValue2(), themeContext.getColorScheme(), floatValue);
                              renderManager.invoke20();
                              renderManager.invoke4(0.0F, 0.0F, (float)i, (float)j, ColorScheme.compute5(19, 12, 32, Math.round(18.0F * floatValue)));
                           } catch (Throwable exception8) {
                              RenderDiagnosticsTracker.getInstance().fail("GuiRenderer.velvetDusk", exception8);
                           }
                        }

                        if (flag8) {
                           try {
                              renderManager.invoke20();
                              ObsidianEmberMenuShader.getINSTANCE()
                                 .invoke(i, j, clickGuiState.getFloatValue(), clickGuiState.getFloatValue2(), themeContext.getColorScheme(), floatValue);
                              renderManager.invoke20();
                              renderManager.invoke4(0.0F, 0.0F, (float)i, (float)j, ColorScheme.compute5(12, 10, 11, Math.round(16.0F * floatValue)));
                           } catch (Throwable exception9) {
                              RenderDiagnosticsTracker.getInstance().fail("GuiRenderer.obsidianEmber", exception9);
                           }
                        }

                        if (flag9) {
                           try {
                              renderManager.invoke20();
                              GlacierVeilMenuShader.getINSTANCE()
                                 .invoke(i, j, clickGuiState.getFloatValue(), clickGuiState.getFloatValue2(), themeContext.getColorScheme(), floatValue);
                              renderManager.invoke20();
                              renderManager.invoke4(0.0F, 0.0F, (float)i, (float)j, ColorScheme.compute5(7, 19, 32, Math.round(18.0F * floatValue)));
                           } catch (Throwable exception10) {
                              RenderDiagnosticsTracker.getInstance().fail("GuiRenderer.glacierVeil", exception10);
                           }
                        }

                        String text = this.resolve2();
                        if (!text.isBlank()) {
                           renderManager.invoke20();
                           boolean flag12 = ShaderUniformBinder.check2(
                              text,
                              0.0F,
                              0.0F,
                              (float)i,
                              (float)j,
                              i,
                              j,
                              clickGuiState.getFloatValue(),
                              clickGuiState.getFloatValue2(),
                              themeContext.getColorScheme(),
                              floatValue
                           );
                           renderManager.invoke20();
                           if (flag12) {
                              renderManager.invoke4(
                                 0.0F,
                                 0.0F,
                                 (float)i,
                                 (float)j,
                                 flag
                                    ? ColorScheme.compute5(255, 255, 255, Math.round(10.0F * floatValue))
                                    : ColorScheme.compute5(0, 0, 0, Math.round(16.0F * floatValue))
                              );
                           }
                        }

                        float floatValue4 = 0.94F + floatValue * 0.06F;
                        this.invoke2(
                           renderManager, drawContext, clickGuiState, clickGuiGeometry, moduleLayoutResult, themeContext, floatValue4, floatValue2, floatValue3, floatValue, i, j
                        );
                        this.tooltipRenderer.invoke(renderManager, clickGuiState, themeContext, i, j);
                     } finally {
                        renderManager.invoke66();
                     }
                  } finally {
                     FontRenderer.check(flag10);
                  }
               } finally {
                  RenderDiagnosticsTracker.getInstance().invoke8();
               }
            }
         }
      }
   }

   private void invoke2(
      RenderManager renderManager2,
      DrawContext drawContext,
      ClickGuiState clickGuiState2,
      ClickGuiGeometry clickGuiGeometry2,
      ModuleLayoutResult moduleLayoutResult2,
      ThemeContext themeContext2,
      float f,
      float g,
      float h,
      float i,
      int j,
      int k
   ) {
      Metrics metrics2 = themeContext2.getMetrics();
      ColorScheme colorScheme = themeContext2.getColorScheme();
      float floatValue5 = clickGuiState2.measure5("panel:z:lift", clickGuiState2.isFlag23() ? 1.0F : 0.0F, SpringSpec.resolve2());
      if (clickGuiState2.isFlag23()) {
         this.invoke4(renderManager2, drawContext, clickGuiState2, clickGuiGeometry2, moduleLayoutResult2, themeContext2, f, g, h, i, j, k, metrics2, colorScheme, 1.0F - floatValue5);
         this.invoke3(renderManager2, clickGuiState2, clickGuiGeometry2, themeContext2, f, g, i, metrics2, colorScheme, floatValue5);
      } else {
         this.invoke3(renderManager2, clickGuiState2, clickGuiGeometry2, themeContext2, f, g, i, metrics2, colorScheme, 1.0F - floatValue5);
         this.invoke4(renderManager2, drawContext, clickGuiState2, clickGuiGeometry2, moduleLayoutResult2, themeContext2, f, g, h, i, j, k, metrics2, colorScheme, floatValue5);
      }
   }

   private void invoke3(
      RenderManager renderManager3,
      ClickGuiState clickGuiState3,
      ClickGuiGeometry clickGuiGeometry3,
      ThemeContext themeContext3,
      float f,
      float g,
      float h,
      Metrics metrics3,
      ColorScheme colorScheme2,
      float i
   ) {
      float floatValue6 = clickGuiState3.measure7(AnimationKeyRegistry.resolve32());
      if (!(floatValue6 <= 0.005F)) {
         float floatValue7 = clickGuiGeometry3.getFloatValue22() + metrics3.getFloatValue18() * 0.5F;
         float floatValue8 = clickGuiGeometry3.getFloatValue23() + metrics3.getFloatValue19() * 0.5F;
         float floatValue9 = 1.0F + i * 0.004F;
         renderManager3.invoke62(floatValue9 * f, floatValue7, floatValue8);
         boolean flag13 = false ;

         try {
            flag13 = true;
            float floatValue10 = h * floatValue6 * (1.0F + i * 0.3F);
            this.invoke7(
               renderManager3,
               clickGuiGeometry3.getFloatValue22(),
               clickGuiGeometry3.getFloatValue23(),
               metrics3.getFloatValue18(),
               metrics3.getFloatValue19(),
               metrics3.measure2(14.0F),
               metrics3,
               themeContext3,
               floatValue10,
               g
            );
            this.themePanelRenderer.invoke(renderManager3, clickGuiState3, clickGuiGeometry3, themeContext3, g);
            this.invoke10(
               renderManager3,
               clickGuiGeometry3.getFloatValue22(),
               clickGuiGeometry3.getFloatValue23(),
               metrics3.getFloatValue18(),
               metrics3.getFloatValue19(),
               metrics3.measure2(14.0F),
               colorScheme2,
               floatValue6
            );
            this.invoke8(
               renderManager3,
               clickGuiGeometry3.getFloatValue22(),
               clickGuiGeometry3.getFloatValue23(),
               metrics3.getFloatValue18(),
               metrics3.getFloatValue19(),
               metrics3.measure2(14.0F),
               themeContext3,
               floatValue6,
               g
            );
            this.invoke5(renderManager3, clickGuiState3, clickGuiGeometry3, metrics3, colorScheme2, g);
            flag13 = false;
         } finally {
            if (flag13) {
               renderManager3.invoke64();
            }
         }

         renderManager3.invoke64();
      }
   }

   private void invoke4(
      RenderManager renderManager4,
      DrawContext drawContext,
      ClickGuiState clickGuiState4,
      ClickGuiGeometry clickGuiGeometry4,
      ModuleLayoutResult moduleLayoutResult3,
      ThemeContext themeContext4,
      float f,
      float g,
      float h,
      float i,
      int j,
      int k,
      Metrics metrics4,
      ColorScheme colorScheme3,
      float l
   ) {
      float floatValue11 = clickGuiGeometry4.getFloatValue() + metrics4.getFloatValue3() * 0.5F;
      float floatValue12 = clickGuiGeometry4.getFloatValue2() + metrics4.getFloatValue4() * 0.5F;
      float floatValue13 = 1.0F + l * 0.008F;
      renderManager4.invoke62(f * floatValue13, floatValue11, floatValue12);
      boolean flag14 = false ;

      try {
         flag14 = true;
         float floatValue14 = i * (1.0F + l * 0.3F);
         this.invoke7(
            renderManager4,
            clickGuiGeometry4.getFloatValue(),
            clickGuiGeometry4.getFloatValue2(),
            metrics4.getFloatValue3(),
            metrics4.getFloatValue4(),
            metrics4.measure(24.0F),
            metrics4,
            themeContext4,
            floatValue14,
            g
         );
         this.invoke11(renderManager4, drawContext, clickGuiState4, clickGuiGeometry4, moduleLayoutResult3, themeContext4, g, j, k);
         this.invoke9(
            renderManager4,
            clickGuiGeometry4.getFloatValue(),
            clickGuiGeometry4.getFloatValue2(),
            metrics4.getFloatValue3(),
            metrics4.getFloatValue4(),
            metrics4.measure(24.0F),
            colorScheme3
         );
         this.invoke8(
            renderManager4,
            clickGuiGeometry4.getFloatValue(),
            clickGuiGeometry4.getFloatValue2(),
            metrics4.getFloatValue3(),
            metrics4.getFloatValue4(),
            metrics4.measure(24.0F),
            themeContext4,
            1.0F,
            g
         );
         this.invoke14(renderManager4, clickGuiGeometry4, metrics4, h);
         this.invoke6(renderManager4, clickGuiState4, clickGuiGeometry4, metrics4, colorScheme3, g);
         flag14 = false;
      } finally {
         if (flag14) {
            renderManager4.invoke64();
         }
      }

      renderManager4.invoke64();
   }

   private void invoke5(
      RenderManager renderManager5, ClickGuiState clickGuiState5, ClickGuiGeometry clickGuiGeometry5, Metrics metrics5, ColorScheme colorScheme4, float f
   ) {
      boolean flag15 = ClickGuiInputController.check3(clickGuiGeometry5, metrics5, clickGuiState5.getFloatValue(), clickGuiState5.getFloatValue2());
      clickGuiState5.setFlag17(flag15);
      boolean flag16 = clickGuiState5.isFlag16();
      float floatValue15 = clickGuiState5.measure5(AnimationKeyRegistry.resolve54(), !flag15 && !flag16 ? 0.0F : 1.0F, SpringSpec.resolve11());
      float floatValue16 = clickGuiState5.measure5(AnimationKeyRegistry.resolve55(), flag16 ? 1.0F : 0.0F, SpringSpec.resolve11());
      float floatValue17 = Math.max(floatValue15, floatValue16);
      float floatValue18 = metrics5.measure2(2.0F) + metrics5.measure2(0.5F) * floatValue17;
      float floatValue19 = metrics5.measure2(2.9F);
      float floatValue20 = metrics5.measure2(7.5F);
      float floatValue21 = clickGuiGeometry5.getFloatValue22() + metrics5.getFloatValue18() - floatValue20;
      float floatValue22 = clickGuiGeometry5.getFloatValue23() + metrics5.getFloatValue19() - floatValue20;
      int intValue = ColorScheme.compute7(colorScheme4.getIntValue14(), colorScheme4.getIntValue15(), 0.5F);
      if (floatValue17 > 0.01F) {
         float floatValue23 = 0.78F + 0.22F * (float)Math.sin(f * Math.PI * 2.4);
         float floatValue24 = metrics5.measure2(9.0F) * (0.35F + 0.65F * floatValue17) * floatValue23;
         float floatValue25 = floatValue21 - floatValue19 * 0.5F;
         float floatValue26 = floatValue22 - floatValue19 * 0.5F;
         renderManager5.invoke41(
            floatValue25 - floatValue19 * 1.6F,
            floatValue26 - floatValue19 * 1.6F,
            floatValue19 * 3.2F,
            floatValue19 * 3.2F,
            floatValue19 * 1.6F,
            floatValue24,
            metrics5.measure2(1.2F),
            ColorScheme.compute6(intValue, Math.round(82.0F * floatValue17 * floatValue23))
         );
      }

      for (int intValue2 = 0; intValue2 < 3; intValue2++) {
         int intValue3 = 3 - intValue2;
         float floatValue27 = floatValue22 - intValue2 * floatValue19;

         for (int intValue4 = 0; intValue4 < intValue3; intValue4++) {
            float floatValue28 = floatValue21 - intValue4 * floatValue19;
            float floatValue29 = floatValue18 * 0.5F;
            int intValue5 = ColorScheme.compute6(ClickGuiRenderUtils.compute7(colorScheme4), Math.round(Math.min(255.0F, 155.0F + 75.0F * floatValue17)));
            int intValue6 = ColorScheme.compute7(intValue5, ColorScheme.compute6(intValue, 240), floatValue17 * 0.7F);
            if (intValue2 == 0 && intValue4 == 0) {
               intValue6 = ColorScheme.compute7(
                  intValue6, colorScheme4.isFlag() ? ClickGuiRenderUtils.compute7(colorScheme4) : ColorScheme.compute5(255, 255, 255, 255), floatValue17 * 0.25F
               );
            }

            renderManager5.invoke5(floatValue28 - floatValue29, floatValue27 - floatValue29, floatValue18, floatValue18, floatValue18 * 0.5F, intValue6);
         }
      }
   }

   private void invoke6(
      RenderManager renderManager6, ClickGuiState clickGuiState6, ClickGuiGeometry clickGuiGeometry6, Metrics metrics6, ColorScheme colorScheme5, float f
   ) {
      boolean flag17 = ClickGuiInputController.check2(clickGuiGeometry6, metrics6, clickGuiState6.getFloatValue(), clickGuiState6.getFloatValue2());
      clickGuiState6.setFlag15(flag17);
      boolean flag18 = clickGuiState6.isFlag14();
      float floatValue30 = clickGuiState6.measure5(AnimationKeyRegistry.resolve52(), !flag17 && !flag18 ? 0.0F : 1.0F, SpringSpec.resolve11());
      float floatValue31 = clickGuiState6.measure5(AnimationKeyRegistry.resolve53(), flag18 ? 1.0F : 0.0F, SpringSpec.resolve11());
      float floatValue32 = Math.max(floatValue30, floatValue31);
      float floatValue33 = metrics6.measure(2.4F) + metrics6.measure(0.6F) * floatValue32;
      float floatValue34 = metrics6.measure(3.4F);
      float floatValue35 = metrics6.measure(8.5F);
      float floatValue36 = clickGuiGeometry6.getFloatValue() + metrics6.getFloatValue3() - floatValue35;
      float floatValue37 = clickGuiGeometry6.getFloatValue2() + metrics6.getFloatValue4() - floatValue35;
      int intValue7 = ColorScheme.compute7(colorScheme5.getIntValue14(), colorScheme5.getIntValue15(), 0.5F);

      for (int intValue8 = 0; intValue8 < 3; intValue8++) {
         int intValue9 = 3 - intValue8;
         float floatValue38 = floatValue37 - intValue8 * floatValue34;

         for (int intValue10 = 0; intValue10 < intValue9; intValue10++) {
            float floatValue39 = floatValue36 - intValue10 * floatValue34;
            float floatValue40 = floatValue33 * 3.0F;
            int intValue11 = ColorScheme.compute6(ClickGuiRenderUtils.compute7(colorScheme5), Math.round(Math.min(255.0F, 165.0F + 70.0F * floatValue32)));
            int intValue12 = ColorScheme.compute7(intValue11, ColorScheme.compute6(intValue7, 240), floatValue32 * 0.7F);
            if (intValue8 == 0 && intValue10 == 0) {
               intValue12 = ColorScheme.compute7(
                  intValue12, colorScheme5.isFlag() ? ClickGuiRenderUtils.compute7(colorScheme5) : ColorScheme.compute5(255, 255, 255, 255), floatValue32 * 0.25F
               );
            }

            renderManager6.invoke5(floatValue39 - floatValue40, floatValue38 - floatValue40, floatValue33, floatValue33, floatValue33 * 0.5F, intValue12);
         }
      }
   }

   private void invoke7(
      RenderManager renderManager7, float f, float g, float h, float i, float j, Metrics metrics7, ThemeContext themeContext5, float k, float l
   ) {
      if (HudEditorRenderer.getINSTANCE().hudEditorRendererVariant.vizual.isEnabled("Тень")) {
         ColorScheme colorScheme6 = themeContext5.getColorScheme();
         if (colorScheme6.isFlag()) {
            if (themeContext5.getTheme() == Theme.VERNAL_SOLSTICE) {
               int intValue13 = ColorScheme.compute5(10, 31, 10, Math.round(30.0F * k));
               int intValue14 = ColorScheme.compute5(10, 31, 10, Math.round(12.0F * k));
               int intValue15 = ColorScheme.compute6(colorScheme6.getIntValue15(), Math.round(11.0F * k * (0.78F + 0.22F * (float)Math.sin(l * Math.PI * 2.0))));
               renderManager7.invoke41(f, g + metrics7.measure(3.0F), h, i, j, metrics7.measure(34.0F), metrics7.measure(3.2F), intValue13);
               renderManager7.invoke41(f, g + metrics7.measure(10.0F), h, i, j, metrics7.measure(74.0F), metrics7.measure(8.0F), intValue14);
               renderManager7.invoke41(f, g, h, i, j, metrics7.measure(42.0F), metrics7.measure(4.0F), intValue15);
            } else {
               renderManager7.invoke41(
                  f,
                  g + metrics7.measure(2.0F),
                  h,
                  i,
                  j,
                  metrics7.measure(28.0F),
                  metrics7.measure(2.5F),
                  ColorScheme.compute5(0, 0, 0, Math.round(38.0F * k))
               );
               renderManager7.invoke41(
                  f,
                  g + metrics7.measure(8.0F),
                  h,
                  i,
                  j,
                  metrics7.measure(58.0F),
                  metrics7.measure(7.0F),
                  ColorScheme.compute5(0, 0, 0, Math.round(18.0F * k))
               );
            }
         } else {
            renderManager7.invoke41(
               f, g, h, i, j, metrics7.measure(14.0F), metrics7.measure(1.0F), ColorScheme.compute5(0, 0, 0, Math.round(180.0F * k))
            );
            if (themeContext5.getTheme() == Theme.MIDNIGHT_AZURE) {
               float floatValue41 = 0.78F + 0.22F * (float)Math.sin(l * Math.PI * 2.0);
               renderManager7.invoke41(
                  f,
                  g + metrics7.measure(2.0F),
                  h,
                  i,
                  j,
                  metrics7.measure(44.0F) * floatValue41,
                  metrics7.measure(5.5F),
                  ColorScheme.compute6(colorScheme6.getIntValue14(), Math.round(28.0F * k * floatValue41))
               );
               renderManager7.invoke41(
                  f,
                  g,
                  h,
                  i,
                  j,
                  metrics7.measure(92.0F) * floatValue41,
                  metrics7.measure(11.0F),
                  ColorScheme.compute6(colorScheme6.getIntValue15(), Math.round(18.0F * k * floatValue41))
               );
            } else {
               float floatValue42 = 0.85F + 0.15F * (float)Math.sin(l * Math.PI * 2.0);
               int intValue16 = ColorScheme.compute7(colorScheme6.getIntValue15(), colorScheme6.getIntValue14(), 0.5F);
               renderManager7.invoke41(
                  f, g, h, i, j, metrics7.measure(60.0F) * floatValue42, metrics7.measure(8.0F), ColorScheme.compute6(intValue16, Math.round(5.0F * k * floatValue42))
               );
            }
         }
      }
   }

   private void invoke8(RenderManager renderManager8, float f, float g, float h, float i, float j, ThemeContext themeContext6, float k, float l) {
      if (themeContext6.getTheme() == Theme.VERNAL_SOLSTICE && !(k <= 0.001F)) {
         Metrics metrics8 = themeContext6.getMetrics();
         ColorScheme colorScheme7 = themeContext6.getColorScheme();
         float floatValue43 = Math.max(0.0F, Math.min(1.0F, k));
         float floatValue44 = Math.max(metrics8.measure(80.0F), h * 0.22F);
         float floatValue45 = h + floatValue44 * 2.0F;
         float floatValue46 = (l * 0.075F + f * 3.1E-4F + g * 1.9E-4F) % 1.0F;
         if (floatValue46 < 0.0F) {
            floatValue46++;
         }

         float floatValue47 = f - floatValue44 + floatValue45 * floatValue46;
         int intValue17 = ColorScheme.compute6(colorScheme7.getIntValue15(), Math.round(42.0F * floatValue43));
         int intValue18 = ColorScheme.compute6(
            ColorScheme.compute7(colorScheme7.getIntValue15(), ColorScheme.compute5(255, 255, 255, 255), 0.42F), Math.round(60.0F * floatValue43)
         );
         renderManager8.invoke20();
         renderManager8.invoke24(f, g, h, i, j, j, j, j);

         try {
            renderManager8.invoke34(floatValue47, g + metrics8.measure(1.0F), floatValue44 * 0.5F, Math.max(1.0F, metrics8.measure(1.1F)), 0.0F, 0, intValue17);
            renderManager8.invoke34(floatValue47 + floatValue44 * 0.5F, g + metrics8.measure(1.0F), floatValue44 * 0.5F, Math.max(1.0F, metrics8.measure(1.1F)), 0.0F, intValue18, 0);
            renderManager8.invoke37(
               f + metrics8.measure(1.0F),
               g + j * 0.35F,
               Math.max(1.0F, metrics8.measure(1.0F)),
               Math.max(1.0F, i - j * 0.7F),
               0.0F,
               ColorScheme.compute6(colorScheme7.getIntValue14(), Math.round(14.0F * floatValue43)),
               ColorScheme.compute6(colorScheme7.getIntValue15(), Math.round(10.0F * floatValue43))
            );
         } finally {
            renderManager8.invoke20();
            renderManager8.invoke25();
         }
      }
   }

   private void invoke9(RenderManager renderManager9, float f, float g, float h, float i, float j, ColorScheme colorScheme8) {
      this.invoke10(renderManager9, f, g, h, i, j, colorScheme8, 1.0F);
   }

   private void invoke10(RenderManager renderManager10, float f, float g, float h, float i, float j, ColorScheme colorScheme9, float k) {
      float floatValue48 = Math.max(0.0F, Math.min(1.0F, k));
      if (colorScheme9.isFlag()) {
         float floatValue49 = Math.round(f);
         float floatValue50 = Math.round(g);
         renderManager10.invoke28(
            floatValue49 + 1.0F,
            floatValue50 + 1.0F,
            Math.max(0.0F, h - 2.0F),
            Math.max(0.0F, i - 2.0F),
            Math.max(0.0F, j - 1.0F),
            ClickGuiRenderUtils.compute13(colorScheme9, 0.92F * floatValue48),
            1.0F
         );
         int intValue19 = ColorScheme.compute6(ColorScheme.compute5(255, 255, 255, 255), Math.round(58.0F * floatValue48));
         float floatValue51 = (h - j * 2.0F) * 0.5F;
         renderManager10.invoke34(floatValue49 + j, floatValue50 + 1.0F, floatValue51, 1.0F, 0.0F, ColorScheme.compute6(intValue19, Math.round(10.0F * floatValue48)), intValue19);
         renderManager10.invoke34(floatValue49 + j + floatValue51, floatValue50 + 1.0F, floatValue51, 1.0F, 0.0F, intValue19, ColorScheme.compute6(intValue19, Math.round(10.0F * floatValue48)));
      } else if (this.check2(colorScheme9)) {
         float floatValue52 = Math.round(f);
         float floatValue53 = Math.round(g);
         int intValue20 = ColorScheme.compute6(colorScheme9.getIntValue14(), Math.round(52.0F * floatValue48));
         int intValue21 = ColorScheme.compute6(colorScheme9.getIntValue15(), Math.round(34.0F * floatValue48));
         renderManager10.invoke28(floatValue52 + 1.0F, floatValue53 + 1.0F, Math.max(0.0F, h - 2.0F), Math.max(0.0F, i - 2.0F), Math.max(0.0F, j - 1.0F), intValue20, 1.0F);
         renderManager10.invoke28(floatValue52 - 1.0F, floatValue53 - 1.0F, h + 2.0F, i + 2.0F, j + 1.0F, intValue21, 0.65F);
         float floatValue54 = (h - j * 2.0F) * 0.5F;
         int intValue22 = ColorScheme.compute6(
            ColorScheme.compute7(ColorScheme.compute5(255, 255, 255, 255), colorScheme9.getIntValue14(), 0.34F), Math.round(46.0F * floatValue48)
         );
         renderManager10.invoke34(floatValue52 + j, floatValue53 + 1.0F, floatValue54, 1.0F, 0.0F, ColorScheme.compute6(intValue22, Math.round(4.0F * floatValue48)), intValue22);
         renderManager10.invoke34(floatValue52 + j + floatValue54, floatValue53 + 1.0F, floatValue54, 1.0F, 0.0F, intValue22, ColorScheme.compute6(intValue22, Math.round(4.0F * floatValue48)));
      } else {
         int intValue23 = ColorScheme.compute7(ColorScheme.compute5(255, 255, 255, 64), colorScheme9.getIntValue14(), 0.3F);
         intValue23 = ColorScheme.compute6(intValue23, Math.round(64.0F * floatValue48));
         float floatValue55 = Math.round(f);
         float floatValue56 = Math.round(g);
         renderManager10.invoke28(floatValue55 - 1.0F, floatValue56, h, i - 1.0F, Math.max(0.0F, j - 0.5F), intValue23, 1.5F);
         int intValue24 = ColorScheme.compute6(
            ColorScheme.compute7(ColorScheme.compute5(255, 255, 255, 255), colorScheme9.getIntValue14(), 0.2F), Math.round(30.0F * floatValue48)
         );
         float floatValue57 = (h - j * 2.0F) * 0.5F;
         renderManager10.invoke34(floatValue55 + j, floatValue56, floatValue57, 1.0F, 0.0F, ColorScheme.compute6(intValue24, Math.round(5.0F * floatValue48)), intValue24);
         renderManager10.invoke34(floatValue55 + j + floatValue57, floatValue56, floatValue57, 1.0F, 0.0F, intValue24, ColorScheme.compute6(intValue24, Math.round(5.0F * floatValue48)));
      }
   }

   private void invoke11(
      RenderManager renderManager11,
      DrawContext drawContext,
      ClickGuiState clickGuiState7,
      ClickGuiGeometry clickGuiGeometry7,
      ModuleLayoutResult moduleLayoutResult4,
      ThemeContext themeContext7,
      float f,
      int i,
      int j
   ) {
      Metrics metrics9 = themeContext7.getMetrics();
      ColorScheme colorScheme10 = themeContext7.getColorScheme();
      float floatValue58 = clickGuiGeometry7.getFloatValue();
      float floatValue59 = clickGuiGeometry7.getFloatValue2();
      float floatValue60 = metrics9.getFloatValue3();
      float floatValue61 = metrics9.getFloatValue4();
      float floatValue62 = metrics9.measure(24.0F);
      renderManager11.invoke5(
         floatValue58 - 1.0F,
         floatValue59 - 1.0F,
         floatValue60 + 2.0F,
         floatValue61 + 2.0F,
         floatValue62 + 1.0F,
         colorScheme10.isFlag() ? ColorScheme.compute5(255, 255, 255, 212) : ColorScheme.compute5(15, 16, 19, 255)
      );
      renderManager11.invoke44(floatValue58, floatValue59, floatValue60, floatValue61, floatValue62, colorScheme10.isFlag() ? 0.96F : 0.92F);
      renderManager11.invoke5(floatValue58, floatValue59, floatValue60, floatValue61, floatValue62, ClickGuiRenderUtils.compute10(colorScheme10));
      if (colorScheme10.isFlag()) {
         renderManager11.invoke28(
            floatValue58 + metrics9.measure(0.8F),
            floatValue59 + metrics9.measure(0.8F),
            floatValue60 - metrics9.measure(1.6F),
            floatValue61 - metrics9.measure(1.6F),
            Math.max(0.0F, floatValue62 - metrics9.measure(0.8F)),
            ClickGuiRenderUtils.compute13(colorScheme10, 0.96F),
            metrics9.measure(0.85F)
         );
      }

      if (ShaderPresetRegistry.getINSTANCE().check(ShaderSurface.MENU_PANEL_BG)) {
         boolean flag19 = this.check(renderManager11, ShaderSurface.MENU_PANEL_BG, null, floatValue58, floatValue59, floatValue60, floatValue61, floatValue62, i, j, clickGuiState7, colorScheme10, 1.0F);
         if (flag19) {
            renderManager11.invoke5(
               floatValue58,
               floatValue59,
               floatValue60,
               floatValue61,
               floatValue62,
               colorScheme10.isFlag()
                  ? ColorScheme.compute6(ClickGuiRenderUtils.compute10(colorScheme10), 58)
                  : ColorScheme.compute6(colorScheme10.getIntValue(), this.check2(colorScheme10) ? 74 : 42)
            );
         }
      }

      String text2 = this.resolve2();
      if (!text2.isBlank()) {
         boolean flag20 = this.check(renderManager11, null, text2, floatValue58, floatValue59, floatValue60, floatValue61, floatValue62, i, j, clickGuiState7, colorScheme10, 0.94F);
         if (flag20) {
            renderManager11.invoke5(
               floatValue58,
               floatValue59,
               floatValue60,
               floatValue61,
               floatValue62,
               colorScheme10.isFlag()
                  ? ColorScheme.compute6(ClickGuiRenderUtils.compute10(colorScheme10), 54)
                  : ColorScheme.compute6(colorScheme10.getIntValue(), this.check2(colorScheme10) ? 68 : 38)
            );
         }
      }

      this.invoke12(renderManager11, floatValue58, floatValue59, floatValue60, floatValue61, floatValue62, colorScheme10, f);
      this.invoke13(renderManager11, floatValue58, floatValue59, floatValue60, floatValue61, floatValue62, f);
      this.sidebarRenderer.invoke(renderManager11, clickGuiState7, clickGuiGeometry7, themeContext7);
      this.searchBarRenderer.invoke(renderManager11, clickGuiState7, clickGuiGeometry7, themeContext7);
      float floatValue63 = clickGuiState7.measure7(AnimationKeyRegistry.resolve42());
      if (floatValue63 > 0.01F) {
         float floatValue64 = this.coreDiagnosticsPanelRenderer.measure9(metrics9);
         float floatValue65 = (floatValue64 + metrics9.measure(48.0F)) * floatValue63;
         float floatValue66 = 1.0F - floatValue63 * 0.04F;
         float floatValue67 = clickGuiGeometry7.getFloatValue11() + clickGuiGeometry7.getFloatValue13() * 0.5F + floatValue65 * 0.5F;
         float floatValue68 = clickGuiGeometry7.getFloatValue12() + metrics9.getFloatValue12() * 0.5F;
         renderManager11.invoke20();
         renderManager11.invoke24(
            clickGuiGeometry7.getFloatValue11(),
            clickGuiGeometry7.getFloatValue12(),
            clickGuiGeometry7.getFloatValue13(),
            metrics9.getFloatValue12(),
            metrics9.measure(4.0F),
            metrics9.measure(4.0F),
            metrics9.measure(16.0F),
            metrics9.measure(4.0F)
         );
         boolean flag21 = false ;

         try {
            flag21 = true;
            renderManager11.invoke62(floatValue66, floatValue67, floatValue68);
            renderManager11.invoke56(floatValue65, 0.0F);
            renderManager11.invoke65(1.0F - floatValue63 * 0.5F);
            boolean flag22 = false ;

            try {
               flag22 = true;
               this.contentPanelRenderer.invoke(renderManager11, drawContext, clickGuiState7, clickGuiGeometry7, moduleLayoutResult4, themeContext7);
               flag22 = false;
            } finally {
               if (flag22) {
                  renderManager11.invoke66();
                  renderManager11.invoke57();
                  renderManager11.invoke64();
               }
            }

            renderManager11.invoke66();
            renderManager11.invoke57();
            renderManager11.invoke64();
            renderManager11.invoke4(
               clickGuiGeometry7.getFloatValue11(),
               clickGuiGeometry7.getFloatValue12(),
               clickGuiGeometry7.getFloatValue13(),
               metrics9.getFloatValue12(),
               colorScheme10.isFlag()
                  ? ColorScheme.compute5(255, 255, 255, Math.round(74.0F * floatValue63))
                  : ColorScheme.compute5(0, 0, 0, Math.round(60.0F * floatValue63))
            );
            flag21 = false;
         } finally {
            if (flag21) {
               renderManager11.invoke20();
               renderManager11.invoke25();
            }
         }

         renderManager11.invoke20();
         renderManager11.invoke25();
      } else {
         this.contentPanelRenderer.invoke(renderManager11, drawContext, clickGuiState7, clickGuiGeometry7, moduleLayoutResult4, themeContext7);
      }

      this.coreDiagnosticsPanelRenderer.invoke(renderManager11, clickGuiState7, clickGuiGeometry7, themeContext7, floatValue63);
   }

   private void invoke12(RenderManager renderManager12, float f, float g, float h, float i, float j, ColorScheme colorScheme11, float k) {
      if (MenuModule.check(MenuModule.VNUTRENNEE_SVECHENIE)) {
         if (!colorScheme11.isFlag()) {
            renderManager12.invoke20();
            renderManager12.invoke24(f, g, h, i, j, j, j, j);
            boolean flag23 = false ;

            try {
               flag23 = true;
               float floatValue69 = k * (float) (Math.PI * 2);
               float floatValue70 = 0.8F + 0.2F * (float)Math.sin(floatValue69 * 0.2);
               float floatValue71 = h * 0.8F;
               float floatValue72 = i * 0.65F;
               float floatValue73 = Math.min(floatValue71, floatValue72) * 0.5F;
               float floatValue74 = f + h * 0.06F + (float)Math.cos(floatValue69 * 0.08) * h * 0.03F;
               float floatValue75 = g + i * 0.04F + (float)Math.sin(floatValue69 * 0.06) * i * 0.02F;
               renderManager12.invoke41(
                  floatValue74, floatValue75, floatValue71, floatValue72, floatValue73, floatValue71 * 0.5F, floatValue71 * 0.15F, ColorScheme.compute6(colorScheme11.getIntValue14(), Math.round(3.0F * floatValue70))
               );
               float floatValue76 = 0.75F + 0.25F * (float)Math.sin(floatValue69 * 0.25 + 2.094F);
               float floatValue77 = h * 0.7F;
               float floatValue78 = i * 0.6F;
               float floatValue79 = Math.min(floatValue77, floatValue78) * 0.5F;
               float floatValue80 = f + h * 0.35F + (float)Math.cos(floatValue69 * 0.1 + 1.5) * h * 0.05F;
               float floatValue81 = g + i * 0.45F + (float)Math.sin(floatValue69 * 0.07 + 0.8F) * i * 0.04F;
               renderManager12.invoke41(
                  floatValue80,
                  floatValue81,
                  floatValue77,
                  floatValue78,
                  floatValue79,
                  floatValue77 * 0.45F,
                  floatValue77 * 0.1F,
                  ColorScheme.compute6(colorScheme11.getIntValue15(), Math.round(2.0F * floatValue76))
               );
               flag23 = false;
            } finally {
               if (flag23) {
                  renderManager12.invoke20();
                  renderManager12.invoke25();
               }
            }

            renderManager12.invoke20();
            renderManager12.invoke25();
         }
      }
   }

   private boolean check(
      RenderManager renderManager13,
      ShaderSurface shaderSurface,
      String string,
      float f,
      float g,
      float h,
      float i,
      float j,
      int k,
      int l,
      ClickGuiState clickGuiState8,
      ColorScheme colorScheme12,
      float m
   ) {
      OFFSCREEN_FRAMEBUFFER.invoke(k, l);
      if (!OFFSCREEN_FRAMEBUFFER.check()) {
         return false;
      } else {
         renderManager13.invoke20();
         FramebufferUtils.GlStateSnapshot glStateSnapshot = FramebufferUtils.captureGlState();
         float[] floatValues = new float[4];
         GL11.glGetFloatv(3106, floatValues);
         boolean flag24 = false ;

         boolean flag25;
         try {
            flag24 = true;
            OFFSCREEN_FRAMEBUFFER.invoke2();
            GL11.glDisable(3089);
            GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
            GL11.glClear(16384);
            flag25 = string != null
               ? ShaderUniformBinder.check2(string, f, g, h, i, k, l, clickGuiState8.getFloatValue(), clickGuiState8.getFloatValue2(), colorScheme12, m)
               : ShaderUniformBinder.check(shaderSurface, f, g, h, i, k, l, clickGuiState8.getFloatValue(), clickGuiState8.getFloatValue2(), colorScheme12, m);
            flag24 = false;
         } finally {
            if (flag24) {
               GL11.glClearColor(floatValues[0], floatValues[1], floatValues[2], floatValues[3]);
               FramebufferUtils.restoreGlState(glStateSnapshot);
            }
         }

         GL11.glClearColor(floatValues[0], floatValues[1], floatValues[2], floatValues[3]);
         FramebufferUtils.restoreGlState(glStateSnapshot);
         if (!flag25) {
            return false;
         } else {
            renderManager13.invoke20();
            renderManager13.invoke24(f, g, h, i, j, j, j, j);

            try {
               renderManager13.drawFlippedTexture(OFFSCREEN_FRAMEBUFFER.getIntValue2(), 0.0F, 0.0F, (float)k, (float)l);
            } finally {
               renderManager13.invoke20();
               renderManager13.invoke25();
            }

            return true;
         }
      }
   }

   private String resolve2() {
      try {
         return MenuModule.FOUNDRY_SHADER.resolve3();
      } catch (Throwable exception11) {
         return "";
      }
   }

   private boolean check2(ColorScheme colorScheme13) {
      return colorScheme13 != null && (colorScheme13.getIntValue14() & 16777215) == 61695 && (colorScheme13.getIntValue15() & 16777215) == 17663;
   }

   private void invoke13(RenderManager renderManager14, float f, float g, float h, float i, float j, float k) {
      if (MenuModule.check(MenuModule.ZERNO_PLYONKI)) {
         renderManager14.invoke20();
         renderManager14.invoke24(f, g, h, i, j, j, j, j);

         try {
            long longValue = (long)(k * 10000.0F);
            float floatValue82 = 40.0F;
            float floatValue83 = 40.0F;
            int intValue25 = (int)Math.ceil(h / floatValue82) + 1;
            int intValue26 = (int)Math.ceil(i / floatValue83) + 1;

            for (int intValue27 = 0; intValue27 < intValue26; intValue27++) {
               for (int intValue28 = 0; intValue28 < intValue25; intValue28++) {
                  long longValue2 = longValue + intValue28 * 73856093L + intValue27 * 19349663L ^ 25214903917L;
                  longValue2 = longValue2 * 6364136223846793005L + 1442695040888963407L;
                  int intValue29 = (int)(longValue2 >>> 48 & 15L);
                  if (intValue29 <= 5) {
                     int intValue30 = 3 + (intValue29 & 3);
                     float floatValue84 = f + intValue28 * floatValue82 + (float)(longValue2 >>> 32 & 31L) - 16.0F;
                     float floatValue85 = g + intValue27 * floatValue83 + (float)(longValue2 >>> 16 & 31L) - 16.0F;
                     float floatValue86 = 1.0F + (intValue29 & 1);
                     int intValue31 = (intValue29 & 1) == 0 ? ColorScheme.compute5(255, 255, 255, intValue30) : ColorScheme.compute5(0, 0, 0, intValue30 + 1);
                     renderManager14.invoke5(floatValue84, floatValue85, floatValue86, floatValue86, 0.5F, intValue31);
                  }
               }
            }
         } finally {
            renderManager14.invoke20();
            renderManager14.invoke25();
         }
      }
   }

   private void invoke14(RenderManager renderManager15, ClickGuiGeometry clickGuiGeometry8, Metrics metrics10, float f) {
      if (!(f <= 0.0F) && !(f >= 1.0F)) {
         float floatValue87 = clickGuiGeometry8.getFloatValue();
         float floatValue88 = clickGuiGeometry8.getFloatValue2();
         float floatValue89 = metrics10.getFloatValue3();
         float floatValue90 = metrics10.getFloatValue4();
         float floatValue91 = metrics10.measure(24.0F);
         float floatValue92 = floatValue89 * 0.18F;
         float floatValue93 = floatValue87 + (floatValue89 + floatValue92 * 2.0F) * f - floatValue92;
         float floatValue94 = Math.max(floatValue87, floatValue93 - floatValue92 * 0.5F);
         float floatValue95 = Math.min(floatValue87 + floatValue89, floatValue93 + floatValue92 * 0.5F);
         if (!(floatValue95 <= floatValue94)) {
            float floatValue96 = (float)Math.sin(f * Math.PI);
            float floatValue97 = 0.08F * floatValue96;
            float floatValue98 = f * 360.0F;
            int intValue32 = ClickGuiRenderUtils.compute18(floatValue98, 0.7F, 0.6F, floatValue97);
            int intValue33 = ClickGuiRenderUtils.compute18((floatValue98 + 60.0F) % 360.0F, 0.7F, 0.6F, floatValue97 * 0.5F);
            renderManager15.invoke20();
            renderManager15.invoke24(floatValue87, floatValue88, floatValue89, floatValue90, floatValue91, floatValue91, floatValue91, floatValue91);

            try {
               renderManager15.invoke33(floatValue94, floatValue88, floatValue95 - floatValue94, floatValue90, intValue32, intValue33);
            } finally {
               renderManager15.invoke20();
               renderManager15.invoke25();
            }
         }
      }
   }

   @Generated
   public ClickGuiRenderer(
      ThemePanelRenderer themePanelRenderer,
      SidebarRenderer sidebarRenderer,
      SearchBarRenderer searchBarRenderer,
      ContentPanelRenderer contentPanelRenderer,
      CoreDiagnosticsPanelRenderer coreDiagnosticsPanelRenderer,
      TooltipRenderer tooltipRenderer
   ) {
      this.themePanelRenderer = themePanelRenderer;
      this.sidebarRenderer = sidebarRenderer;
      this.searchBarRenderer = searchBarRenderer;
      this.contentPanelRenderer = contentPanelRenderer;
      this.coreDiagnosticsPanelRenderer = coreDiagnosticsPanelRenderer;
      this.tooltipRenderer = tooltipRenderer;
   }
}
