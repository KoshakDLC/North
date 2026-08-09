package ru.metaculture.protection;

import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import lombok.Generated;
import net.minecraft.client.gui.DrawContext;
import org.wild.module.api.Module;

public final class ModuleListRenderer {
   static final float FLOAT_VALUE = 120.0F;
   static final float FLOAT_VALUE_2 = 14.0F;
   static final SpringConfig SPRING_CONFIG = SpringConfig.resolve((float)(Math.sqrt(120.0) / (Math.PI * 2)), 14.0F / (2.0F * (float)Math.sqrt(120.0)));
   private final SettingsRenderer settingsRenderer;
   private final Map<Module, ClampedSpringAnimation> valuesByKey = new IdentityHashMap<>();

   public void invoke(
      RenderManager renderManager, DrawContext drawContext, ClickGuiState clickGuiState, ModulePlacement modulePlacement, ThemeContext themeContext, float f
   ) {
      Module module2 = modulePlacement.getModule();
      float floatValue = clickGuiState.measure7(AnimationKeyRegistry.resolve26(module2));
      if (!(floatValue < 0.01F)) {
         float floatValue2 = clickGuiState.measure7(AnimationKeyRegistry.resolve27(module2));
         if (!(floatValue2 < 0.005F)) {
            Metrics metrics = themeContext.getMetrics();
            ColorScheme colorScheme = themeContext.getColorScheme();
            float floatValue3 = modulePlacement.getFloatValue();
            float floatValue4 = modulePlacement.getFloatValue2();
            float floatValue5 = modulePlacement.getFloatValue3();
            String text = AnimationKeyRegistry.resolve16(module2);
            float floatValue6 = clickGuiState.measure7(text);
            float floatValue7 = clickGuiState.measure7(AnimationKeyRegistry.resolve17(module2));
            float floatValue8 = clickGuiState.measure7(AnimationKeyRegistry.resolve28(module2));
            float floatValue9 = this.measure4(floatValue2);
            float floatValue10 = floatValue * Math.min(1.0F, floatValue2);
            float floatValue11 = 0.85F + 0.15F * floatValue9;
            float floatValue12 = ClickGuiRenderUtils.measure7(floatValue6, clickGuiState.measure8(text));
            float floatValue13 = (1.0F - floatValue9) * metrics.measure(15.0F);
            float floatValue14 = floatValue3 + floatValue5 * 0.5F;
            float floatValue15 = floatValue4 + modulePlacement.getFloatValue4() * 0.5F;
            renderManager.invoke65(floatValue10);
            boolean flag = false ;

            try {
               flag = true;
               renderManager.invoke56(0.0F, floatValue13);

               try {
                  renderManager.invoke62(floatValue11 * floatValue12, floatValue14, floatValue15);

                  try {
                     float floatValue16 = floatValue6 * metrics.measure(1.5F);
                     float floatValue17 = floatValue4 - floatValue16;
                     float floatValue18 = modulePlacement.getFloatValue4();
                     float floatValue19 = metrics.measure(8.0F);
                     boolean flag2 = clickGuiState.isFlag7();
                     float floatValue20 = flag2 ? 1.0F : this.measure5(Math.min(floatValue, floatValue8));
                     float floatValue21 = this.measure6();
                     float floatValue22 = this.measure(0.02F, 0.9F, f);
                     if (!flag2) {
                        this.invoke2(renderManager, metrics, colorScheme, floatValue3, floatValue17, floatValue5, floatValue18, floatValue19, floatValue20, floatValue6, floatValue7);
                     }

                     if (colorScheme.isFlag()) {
                        int intValue = themeContext.getTheme() == Theme.VERNAL_SOLSTICE
                           ? ColorScheme.compute5(10, 31, 10, Math.round(15.0F + 8.0F * floatValue6 + 7.0F * floatValue22))
                           : ColorScheme.compute5(54, 34, 42, Math.round(12.0F + 7.0F * floatValue6 + 6.0F * floatValue22));
                        renderManager.invoke41(
                           floatValue3,
                           floatValue17 + metrics.measure(2.0F),
                           floatValue5,
                           floatValue18,
                           floatValue19,
                           metrics.measure(12.0F + 5.0F * floatValue22 + 2.0F * floatValue6),
                           metrics.measure(1.6F),
                           intValue
                        );
                     }

                     if (floatValue7 > 0.3F) {
                        int intValue2 = colorScheme.isFlag()
                           ? (
                              themeContext.getTheme() == Theme.VERNAL_SOLSTICE
                                 ? ColorScheme.compute5(10, 31, 10, Math.round(30.0F * floatValue7))
                                 : ColorScheme.compute5(54, 34, 42, Math.round(26.0F * floatValue7))
                           )
                           : ColorScheme.compute6(colorScheme.getIntValue15(), Math.round(18.0F * floatValue7));
                        renderManager.invoke41(
                           floatValue3,
                           floatValue17,
                           floatValue5,
                           floatValue18,
                           floatValue19,
                           metrics.measure(colorScheme.isFlag() ? 12.0F : 6.0F) * floatValue7,
                           metrics.measure(colorScheme.isFlag() ? 2.0F : 1.0F),
                           intValue2
                        );
                     }

                     if (floatValue6 > 0.01F) {
                        renderManager.invoke41(
                           floatValue3,
                           floatValue17,
                           floatValue5,
                           floatValue18,
                           floatValue19,
                           metrics.measure(colorScheme.isFlag() ? 10.0F : 4.0F) * floatValue6,
                           metrics.measure(colorScheme.isFlag() ? 2.0F : 1.0F),
                           ColorScheme.compute5(0, 0, 0, Math.round((colorScheme.isFlag() ? 24 : 25) * floatValue6))
                        );
                     }

                     int intValue3 = colorScheme.isFlag()
                        ? this.compute(colorScheme, floatValue6, floatValue7, floatValue22)
                        : ColorScheme.compute7(module2.enabled ? colorScheme.getIntValue4() : colorScheme.getIntValue3(), colorScheme.getIntValue5(), floatValue6);
                     int intValue4 = colorScheme.isFlag()
                        ? ColorScheme.compute7(
                           ClickGuiRenderUtils.compute13(colorScheme, 0.96F + floatValue6 * 0.12F + floatValue22 * 0.08F),
                           ColorScheme.compute6(colorScheme.getIntValue14(), 104),
                           Math.max(floatValue7 * 0.18F, Math.max(floatValue6 * 0.22F, floatValue22 * 0.1F))
                        )
                        : ColorScheme.compute7(module2.enabled ? colorScheme.getIntValue6() : colorScheme.getIntValue4(), colorScheme.getIntValue7(), floatValue6);
                     int intValue5 = colorScheme.getIntValue15();
                     int intValue6 = colorScheme.getIntValue14();
                     if (colorScheme.isFlag()) {
                        renderManager.invoke44(floatValue3, floatValue17, floatValue5, floatValue18, floatValue19, 0.64F + floatValue6 * 0.1F + floatValue7 * 0.07F + floatValue22 * 0.05F);
                     }

                     boolean flag3 = MenuModule.check(MenuModule.PEREHODY_KART);
                     RenderManager.RenderManagerState2 renderManagerState2 = !flag2 && flag3 ? renderManager.resolve3(floatValue3, floatValue17, floatValue5, floatValue18) : null;
                     if (renderManagerState2 != null) {
                        try {
                           this.invoke5(
                              renderManager, drawContext, clickGuiState, modulePlacement, module2, floatValue3, floatValue17, floatValue5, floatValue18, floatValue7, floatValue6, themeContext
                           );
                        } finally {
                           renderManager.invoke14(renderManagerState2);
                        }

                        renderManager.invoke16(renderManagerState2, floatValue3, floatValue17, floatValue5, floatValue18, floatValue19, intValue3, intValue4, intValue5, intValue6, floatValue20, floatValue21);
                     } else {
                        this.invoke3(
                           renderManager,
                           drawContext,
                           clickGuiState,
                           modulePlacement,
                           module2,
                           floatValue3,
                           floatValue17,
                           floatValue5,
                           floatValue18,
                           floatValue19,
                           intValue3,
                           intValue4,
                           floatValue7,
                           floatValue6,
                           themeContext
                        );
                     }

                     float floatValue23 = (1.0F - floatValue7) * (1.0F - floatValue6 * 0.55F);
                     if (floatValue23 > 0.01F) {
                        int intValue7 = ColorScheme.compute6(colorScheme.getIntValue13(), Math.round(34.0F * floatValue23));
                        renderManager.invoke28(floatValue3, floatValue17, floatValue5, floatValue18, floatValue19, intValue7, 0.5F);
                     }

                     this.invoke4(renderManager, metrics, themeContext, floatValue3, floatValue17, floatValue5, floatValue18, floatValue19, floatValue6, floatValue7, floatValue21);
                  } finally {
                     renderManager.invoke64();
                  }
               } finally {
                  renderManager.invoke57();
               }

               flag = false;
            } finally {
               if (flag) {
                  renderManager.invoke66();
               }
            }

            renderManager.invoke66();
         }
      }
   }

   private void invoke2(
      RenderManager renderManager2, Metrics metrics2, ColorScheme colorScheme2, float f, float g, float h, float i, float j, float k, float l, float m
   ) {
      float floatValue24 = this.measure(0.025F, 0.18F, k) * (1.0F - this.measure(0.88F, 1.0F, k));
      float floatValue25 = this.measure(0.18F, 0.46F, k) * (1.0F - this.measure(0.72F, 1.0F, k));
      float floatValue26 = Math.max(Math.max(floatValue24, floatValue25 * 0.42F), m * 0.18F + l * 0.08F);
      if (!(floatValue26 <= 0.01F)) {
         int intValue8 = ColorScheme.compute7(colorScheme2.getIntValue15(), colorScheme2.getIntValue14(), 0.42F);
         int intValue9 = ColorScheme.compute7(colorScheme2.getIntValue15(), colorScheme2.getIntValue13(), 0.2F);
         if (colorScheme2.isFlag()) {
            renderManager2.invoke41(
               f,
               g + metrics2.measure(2.0F),
               h,
               i,
               j,
               metrics2.measure(20.0F) * floatValue26,
               metrics2.measure(3.2F),
               ColorScheme.compute5(0, 0, 0, Math.round(24.0F * floatValue26))
            );
            renderManager2.invoke41(
               f,
               g + metrics2.measure(5.0F),
               h,
               i,
               j,
               metrics2.measure(34.0F) * floatValue26,
               metrics2.measure(4.2F),
               ColorScheme.compute5(0, 0, 0, Math.round(10.0F * floatValue26))
            );
            if (floatValue24 > 0.01F) {
               float floatValue27 = metrics2.measure(1.4F);
               renderManager2.invoke28(
                  f + floatValue27,
                  g + floatValue27,
                  h - floatValue27 * 2.0F,
                  i - floatValue27 * 2.0F,
                  Math.max(0.0F, j - floatValue27),
                  ColorScheme.compute6(colorScheme2.getIntValue14(), Math.round(26.0F * floatValue24)),
                  0.5F
               );
            }
         } else {
            renderManager2.invoke21();

            try {
               renderManager2.invoke41(
                  f, g, h, i, j, metrics2.measure(18.0F) * floatValue26, metrics2.measure(3.2F), ColorScheme.compute6(intValue9, Math.round(26.0F * floatValue26))
               );
               renderManager2.invoke41(
                  f, g, h, i, j, metrics2.measure(9.0F) * floatValue26, metrics2.measure(1.4F), ColorScheme.compute6(intValue8, Math.round(34.0F * floatValue26))
               );
               if (floatValue24 > 0.01F) {
                  float floatValue28 = metrics2.measure(1.4F);
                  renderManager2.invoke28(
                     f + floatValue28,
                     g + floatValue28,
                     h - floatValue28 * 2.0F,
                     i - floatValue28 * 2.0F,
                     Math.max(0.0F, j - floatValue28),
                     ColorScheme.compute6(intValue8, Math.round(28.0F * floatValue24)),
                     0.5F
                  );
               }
            } finally {
               renderManager2.invoke22();
            }
         }
      }
   }

   private float measure(float f, float g, float h) {
      float floatValue29 = this.measure7((h - f) / Math.max(1.0E-5F, g - f));
      return floatValue29 * floatValue29 * (3.0F - 2.0F * floatValue29);
   }

   private float measure2(float f) {
      float floatValue30 = this.measure7(f);
      return floatValue30 * floatValue30 * floatValue30 * (floatValue30 * (floatValue30 * 6.0F - 15.0F) + 10.0F);
   }

   private float measure3(float f) {
      float floatValue31 = this.measure2(f);
      float floatValue32 = this.measure(0.08F, 0.92F, floatValue31);
      return RenderMath.measure49(floatValue31 * 0.72F + floatValue32 * 0.28F, 0.0F, 1.0F);
   }

   private float measure4(float f) {
      float floatValue33 = this.measure7(f);
      return floatValue33 * floatValue33 * (3.0F - 2.0F * floatValue33);
   }

   private float measure5(float f) {
      float floatValue34 = this.measure7(f);
      return (float)Math.pow(floatValue34, 1.42F);
   }

   private int compute(ColorScheme colorScheme3, float f, float g, float h) {
      float floatValue35 = Math.max(f, Math.max(g * 0.22F, h * 0.34F));
      int intValue10 = ClickGuiRenderUtils.compute12(colorScheme3, floatValue35);
      int intValue11 = ColorScheme.compute7(ColorScheme.compute5(255, 255, 255, 232), ColorScheme.compute6(colorScheme3.getIntValue14(), 232), 0.04F);
      int intValue12 = ColorScheme.compute7(ColorScheme.compute5(255, 255, 255, 238), ColorScheme.compute6(colorScheme3.getIntValue15(), 238), 0.035F);
      return ColorScheme.compute7(ColorScheme.compute7(intValue10, intValue11, 0.28F + h * 0.12F), intValue12, g * 0.035F);
   }

   private void invoke3(
      RenderManager renderManager3,
      DrawContext drawContext,
      ClickGuiState clickGuiState2,
      ModulePlacement modulePlacement2,
      Module module,
      float f,
      float g,
      float h,
      float i,
      float j,
      int k,
      int l,
      float m,
      float n,
      ThemeContext themeContext2
   ) {
      ColorScheme colorScheme4 = themeContext2.getColorScheme();
      renderManager3.invoke5(f, g, h, i, j, k);
      renderManager3.invoke28(f, g, h, i, j, l, colorScheme4.isFlag() ? 1.0F : 0.5F);
      this.invoke5(renderManager3, drawContext, clickGuiState2, modulePlacement2, module, f, g, h, i, m, n, themeContext2);
   }

   private void invoke4(
      RenderManager renderManager4,
      Metrics metrics3,
      ThemeContext themeContext3,
      float f,
      float g,
      float h,
      float i,
      float j,
      float k,
      float l,
      float m
   ) {
      if (themeContext3.getTheme() == Theme.VERNAL_SOLSTICE) {
         ColorScheme colorScheme5 = themeContext3.getColorScheme();
         float floatValue36 = Math.max(0.18F, k * 0.72F + l * 0.34F);
         float floatValue37 = (m * 0.18F + f * 0.0031F + g * 0.0017F) % 1.0F;
         if (floatValue37 < 0.0F) {
            floatValue37++;
         }

         float floatValue38 = Math.max(metrics3.measure(42.0F), h * 0.28F);
         float floatValue39 = f - floatValue38 + (h + floatValue38 * 2.0F) * floatValue37;
         int intValue13 = ColorScheme.compute6(colorScheme5.getIntValue15(), Math.round(28.0F * floatValue36));
         int intValue14 = ColorScheme.compute6(
            ColorScheme.compute7(colorScheme5.getIntValue15(), ColorScheme.compute5(255, 255, 255, 255), 0.45F), Math.round(46.0F * floatValue36)
         );
         renderManager4.invoke20();
         renderManager4.invoke24(f, g, h, i, j, j, j, j);
         boolean flag4 = false ;

         try {
            flag4 = true;
            renderManager4.invoke34(floatValue39, g + metrics3.measure(0.7F), floatValue38 * 0.5F, Math.max(1.0F, metrics3.measure(0.85F)), 0.0F, 0, intValue13);
            renderManager4.invoke34(
               floatValue39 + floatValue38 * 0.5F, g + metrics3.measure(0.7F), floatValue38 * 0.5F, Math.max(1.0F, metrics3.measure(0.85F)), 0.0F, intValue14, 0
            );
            flag4 = false;
         } finally {
            if (flag4) {
               renderManager4.invoke20();
               renderManager4.invoke25();
            }
         }

         renderManager4.invoke20();
         renderManager4.invoke25();
      }
   }

   private void invoke5(
      RenderManager renderManager5,
      DrawContext drawContext,
      ClickGuiState clickGuiState3,
      ModulePlacement modulePlacement3,
      Module module,
      float f,
      float g,
      float h,
      float i,
      float j,
      float k,
      ThemeContext themeContext4
   ) {
      Metrics metrics4 = themeContext4.getMetrics();
      ColorScheme colorScheme6 = themeContext4.getColorScheme();
      if (j > 0.01F) {
         int intValue15 = ColorScheme.compute6(
            ColorScheme.compute7(colorScheme6.getIntValue14(), colorScheme6.getIntValue15(), colorScheme6.isFlag() ? 0.38F : 0.0F),
            Math.round((colorScheme6.isFlag() ? 26 : 20) * j)
         );
         renderManager5.invoke6(
            f + metrics4.measure(1.0F), g, h - metrics4.measure(2.0F), metrics4.measure(1.0F), metrics4.measure(8.0F), metrics4.measure(8.0F), 0.0F, 0.0F, intValue15
         );
      }

      this.invoke8(renderManager5, clickGuiState3, module, f, g, h, i, j, k, themeContext4);
      if (modulePlacement3.getFloatValue5() > 0.01F) {
         this.invoke6(renderManager5, drawContext, clickGuiState3, modulePlacement3, module, f, g, h, i, themeContext4);
      }
   }

   private void invoke6(
      RenderManager renderManager6,
      DrawContext drawContext,
      ClickGuiState clickGuiState4,
      ModulePlacement modulePlacement4,
      Module module,
      float f,
      float g,
      float h,
      float i,
      ThemeContext themeContext5
   ) {
      Metrics metrics5 = themeContext5.getMetrics();
      ColorScheme colorScheme7 = themeContext5.getColorScheme();
      float floatValue40 = this.measure7(clickGuiState4.measure7(AnimationKeyRegistry.resolve15(module)));
      float floatValue41 = this.measure11(module, h, themeContext5);
      float floatValue42 = g + floatValue41;
      float floatValue43 = Math.max(metrics5.measure(1.0F), i - floatValue41);
      boolean flag5 = MenuModule.check(MenuModule.PEREHODY_KART);
      if (!clickGuiState4.isFlag7() && flag5) {
         RenderManager.RenderManagerState2 renderManagerState22 = renderManager6.resolve3(f, floatValue42, h, floatValue43);
         if (renderManagerState22 != null) {
            try {
               this.invoke7(renderManager6, drawContext, clickGuiState4, modulePlacement4, module, f, g, h, i, themeContext5);
            } finally {
               renderManager6.invoke14(renderManagerState22);
            }

            int intValue16 = colorScheme7.isFlag() ? ColorScheme.compute7(colorScheme7.getIntValue14(), colorScheme7.getIntValue13(), 0.45F) : colorScheme7.getIntValue14();
            int intValue17 = colorScheme7.isFlag() ? ColorScheme.compute7(colorScheme7.getIntValue15(), colorScheme7.getIntValue13(), 0.45F) : colorScheme7.getIntValue15();
            int intValue18 = ColorScheme.compute6(ColorScheme.compute7(intValue17, intValue16, 0.5F), Math.round(200.0F * floatValue40));
            boolean flag6 = clickGuiState4.getValues().contains(module);
            float floatValue44 = 0.988F + 0.012F * this.measure(0.0F, 0.6F, floatValue40);
            renderManager6.invoke62(floatValue44, f + h * 0.5F, floatValue42);
            renderManager6.invoke65(this.measure(0.02F, 0.3F, floatValue40));
            boolean flag7 = false ;

            try {
               flag7 = true;
               renderManager6.invoke18(
                  renderManagerState22,
                  f,
                  floatValue42,
                  h,
                  floatValue43,
                  metrics5.measure(7.0F),
                  ColorScheme.compute6(intValue16, flag6 ? 255 : 0),
                  ColorScheme.compute6(intValue17, 255),
                  intValue18,
                  floatValue40,
                  this.measure6()
               );
               flag7 = false;
            } finally {
               if (flag7) {
                  renderManager6.invoke66();
                  renderManager6.invoke64();
               }
            }

            renderManager6.invoke66();
            renderManager6.invoke64();
         } else {
            ClickGuiRenderUtils.invoke6(renderManager6, f, floatValue42, h, floatValue43, metrics5.measure(7.0F), () -> {
               renderManager6.invoke65(floatValue40);
               boolean var14x = false ;

               try {
                  var14x = true;
                  this.invoke7(renderManager6, drawContext, clickGuiState4, modulePlacement4, module, f, g, h, i, themeContext5);
                  var14x = false;
               } finally {
                  if (var14x) {
                     renderManager6.invoke66();
                  }
               }

               renderManager6.invoke66();
            });
         }
      } else {
         ClickGuiRenderUtils.invoke6(renderManager6, f, floatValue42, h, floatValue43, metrics5.measure(7.0F), () -> {
            renderManager6.invoke65(floatValue40);

            try {
               this.invoke7(renderManager6, drawContext, clickGuiState4, modulePlacement4, module, f, g, h, i, themeContext5);
            } finally {
               renderManager6.invoke66();
            }
         });
      }
   }

   private void invoke7(
      RenderManager renderManager7,
      DrawContext drawContext,
      ClickGuiState clickGuiState5,
      ModulePlacement modulePlacement5,
      Module module,
      float f,
      float g,
      float h,
      float i,
      ThemeContext themeContext6
   ) {
      Metrics metrics6 = themeContext6.getMetrics();
      ColorScheme colorScheme8 = themeContext6.getColorScheme();
      float floatValue45 = this.measure11(module, h, themeContext6);
      renderManager7.invoke4(
         f + metrics6.measure(1.0F),
         g + floatValue45,
         h - metrics6.measure(2.0F),
         metrics6.measure(1.0F),
         module.enabled ? colorScheme8.getIntValue7() : colorScheme8.getIntValue5()
      );
      SpecialModuleCardHandler specialModuleCardHandler = SpecialModuleCardHandlers.resolve(module);
      if (specialModuleCardHandler != null) {
         ModulePlacement modulePlacement6 = new ModulePlacement(module, f, g, h, i, modulePlacement5.getFloatValue5());
         specialModuleCardHandler.invoke4(renderManager7, drawContext, clickGuiState5, modulePlacement6, themeContext6);
      } else {
         this.invoke13(
            renderManager7, clickGuiState5, module, f + metrics6.measure(16.0F), g + floatValue45 + metrics6.measure(10.0F), h - metrics6.measure(32.0F), themeContext6
         );
      }
   }

   private float measure6() {
      return (float)(System.currentTimeMillis() % 1000000L) / 1000.0F;
   }

   private float measure7(float f) {
      return Math.max(0.0F, Math.min(1.0F, f));
   }

   private void invoke8(
      RenderManager renderManager8,
      ClickGuiState clickGuiState6,
      Module module,
      float f,
      float g,
      float h,
      float i,
      float j,
      float k,
      ThemeContext themeContext7
   ) {
      Metrics metrics7 = themeContext7.getMetrics();
      ColorScheme colorScheme9 = themeContext7.getColorScheme();
      float floatValue46 = f + metrics7.measure(16.0F);
      float floatValue47 = g + metrics7.measure(16.0F);
      boolean flag8 = !clickGuiState6.isFlag7() && ClickGuiRenderUtils.check(clickGuiState6, f, g, h, i);
      int intValue19 = ColorScheme.compute7(ClickGuiRenderUtils.compute4(colorScheme9), ClickGuiRenderUtils.compute2(colorScheme9), Math.max(j, k * 0.45F));
      if (j > 0.01F) {
         renderManager8.invoke37(
            floatValue46,
            floatValue47 + metrics7.measure(2.0F),
            metrics7.measure(1.0F),
            metrics7.measure(10.0F),
            metrics7.measure(1.0F),
            ColorScheme.compute6(colorScheme9.getIntValue14(), Math.round(255.0F * j)),
            ColorScheme.compute6(colorScheme9.getIntValue15(), Math.round(255.0F * j))
         );
      }

      float floatValue48 = floatValue46 + metrics7.measure(9.0F) * j;
      ClickGuiRenderUtils.invoke4(renderManager8, metrics7, FontRegistry.fontObject4, floatValue48, floatValue47, metrics7.measure(14.0F), 12.0F, module.displayName, intValue19);
      this.invoke9(renderManager8, clickGuiState6, module, f, h, floatValue47, k, flag8, themeContext7);
      this.invoke11(renderManager8, clickGuiState6, module, f, h, floatValue47, j, k, themeContext7);
      this.invoke12(
         renderManager8, module, f, g, h, ColorScheme.compute7(ClickGuiRenderUtils.compute5(colorScheme9), ClickGuiRenderUtils.compute4(colorScheme9), j), themeContext7
      );
   }

   private void invoke9(
      RenderManager renderManager9, ClickGuiState clickGuiState7, Module module, float f, float g, float h, float i, boolean bl, ThemeContext themeContext8
   ) {
      if (!module.getRiskLevels().isEmpty()) {
         Metrics metrics8 = themeContext8.getMetrics();
         ColorScheme colorScheme10 = themeContext8.getColorScheme();
         ClampedSpringAnimation clampedSpringAnimation = this.valuesByKey.computeIfAbsent(module, modulex -> this.resolve());
         clampedSpringAnimation.invoke2(1.0F);
         float floatValue49 = RenderMath.measure49(clampedSpringAnimation.measure(), 0.0F, 1.0F);
         List items = this.resolve2(module);
         float floatValue50 = f + g - metrics8.measure(16.0F) - metrics8.measure(24.0F);
         float floatValue51 = floatValue50 - metrics8.measure(this.check(module) ? 32.0F : 9.0F);
         float floatValue52 = f + metrics8.measure(90.0F);
         float floatValue53 = floatValue51 - floatValue52;
         float floatValue54 = metrics8.measure(15.5F);
         float floatValue55 = h + metrics8.measure(7.0F);
         float floatValue56 = metrics8.measure(5.0F);
         float floatValue57 = metrics8.measure(22.0F);
         float floatValue58 = this.measure6();
         float floatValue59 = floatValue56 * Math.max(0, items.size() - 1);

         for (ModuleRiskLevel moduleRiskLevel : (List<ModuleRiskLevel>)items) {
            floatValue59 += this.measure8(metrics8, moduleRiskLevel);
         }

         boolean flag9 = floatValue59 <= floatValue53;
         int intValue20 = items.size();
         float floatValue60 = intValue20 <= 1 ? 0.0F : Math.min(0.42F, 0.07F * (intValue20 - 1)) / (intValue20 - 1);
         float floatValue61 = Math.max(0.001F, 1.0F - floatValue60 * Math.max(0, intValue20 - 1));
         float floatValue62 = floatValue51;

         for (int intValue21 = 0; intValue21 < intValue20; intValue21++) {
            ModuleRiskLevel moduleRiskLevel2 = (ModuleRiskLevel)items.get(intValue21);
            float floatValue63 = flag9 ? this.measure8(metrics8, moduleRiskLevel2) : floatValue57;
            float floatValue64 = floatValue62 - floatValue63;
            if (floatValue64 < floatValue52 && intValue21 > 0) {
               break;
            }

            float floatValue65 = RenderMath.measure49((floatValue49 - intValue21 * floatValue60) / floatValue61, 0.0F, 1.0F);
            float floatValue66 = this.measure3(floatValue65);
            this.invoke10(renderManager9, metrics8, colorScheme10, moduleRiskLevel2, floatValue64, floatValue55 - floatValue54 * 0.5F, floatValue63, floatValue54, flag9, floatValue66, i, floatValue58, intValue21);
            floatValue62 -= floatValue63 + floatValue56;
         }
      }
   }

   private ClampedSpringAnimation resolve() {
      ClampedSpringAnimation clampedSpringAnimation2 = new ClampedSpringAnimation(AnimationSystem.getINSTANCE(), SPRING_CONFIG, 0.0F, 0.0F, 1.0F, 6.0E-4F, 6.0E-4F);
      clampedSpringAnimation2.setFloatEasing(this::measure2);
      return clampedSpringAnimation2;
   }

   private List<ModuleRiskLevel> resolve2(Module module) {
      return module.getRiskLevels().stream().sorted(Comparator.comparingInt(ModuleRiskLevel::getIntValue2).thenComparing(Enum::name)).toList();
   }

   private float measure8(Metrics metrics9, ModuleRiskLevel moduleRiskLevel3) {
      String text2 = moduleRiskLevel3.getText().toUpperCase(Locale.ROOT);
      float floatValue67 = ClickGuiRenderUtils.measure2(metrics9, FontRegistry.resolve4(), moduleRiskLevel3.getText2(), 7.6F);
      float floatValue68 = ClickGuiRenderUtils.measure2(metrics9, FontRegistry.fontObject4, text2, 8.0F);
      return Math.max(metrics9.measure(25.0F), floatValue67 + floatValue68 + metrics9.measure(21.0F));
   }

   private void invoke10(
      RenderManager renderManager10,
      Metrics metrics10,
      ColorScheme colorScheme11,
      ModuleRiskLevel moduleRiskLevel4,
      float f,
      float g,
      float h,
      float i,
      boolean bl,
      float j,
      float k,
      float l,
      int m
   ) {
      if (!(j <= 0.001F)) {
         boolean flag10 = colorScheme11.isFlag();
         int intValue22 = moduleRiskLevel4.getIntValue();
         float floatValue69 = i * 0.5F;
         g += (1.0F - j) * metrics10.measure(4.0F);
         float floatValue70 = j;
         float floatValue71 = k * k;
         float floatValue72 = 0.5F + 0.5F * (float)Math.sin(l * 2.1F + m * 0.9F);
         int intValue23 = ColorScheme.compute6(intValue22, Math.round((9.0F + 15.0F * floatValue71 + 5.0F * floatValue72) * j));
         renderManager10.invoke41(f, g, h, i, floatValue69, metrics10.measure(3.5F + 6.0F * floatValue71), metrics10.measure(0.9F), intValue23);
         int intValue24 = flag10
            ? ColorScheme.compute6(ColorScheme.compute7(colorScheme11.getIntValue13(), intValue22, 0.12F), Math.round((32.0F + 16.0F * floatValue71) * j))
            : ColorScheme.compute6(ColorScheme.compute7(colorScheme11.getIntValue(), intValue22, 0.22F), Math.round((36.0F + 16.0F * floatValue71) * j));
         int intValue25 = flag10
            ? ColorScheme.compute6(ColorScheme.compute7(colorScheme11.getIntValue13(), intValue22, 0.05F), Math.round((20.0F + 12.0F * floatValue71) * j))
            : ColorScheme.compute6(ColorScheme.compute7(colorScheme11.getIntValue(), intValue22, 0.1F), Math.round((22.0F + 12.0F * floatValue71) * j));
         renderManager10.invoke37(f, g, h, i, floatValue69, intValue24, intValue25);
         int intValue26 = ColorScheme.compute6(ColorScheme.compute7(colorScheme11.getIntValue13(), intValue22, 0.3F), Math.round((flag10 ? 42.0F : 34.0F) * j));
         renderManager10.invoke5(
            f + floatValue69 * 0.6F,
            g + metrics10.measure(1.5F),
            Math.max(metrics10.measure(1.0F), h - floatValue69 * 1.2F),
            Math.max(metrics10.measure(0.85F), 1.0F),
            metrics10.measure(0.5F),
            intValue26
         );
         int intValue27 = ColorScheme.compute6(intValue22, Math.round((74.0F + 66.0F * floatValue71 + 32.0F * floatValue72) * j));
         renderManager10.invoke28(f, g, h, i, floatValue69, intValue27, flag10 ? 1.0F : 0.9F);
         if (floatValue71 > 0.01F) {
            renderManager10.invoke20();
            renderManager10.invoke24(f, g, h, i, floatValue69, floatValue69, floatValue69, floatValue69);

            try {
               float floatValue73 = h * 0.42F;
               float floatValue74 = (float)(l * 0.5 + m * 0.17);
               floatValue74 -= (float)Math.floor(floatValue74);
               float floatValue75 = f - floatValue73 + (h + floatValue73) * floatValue74;
               int intValue28 = ColorScheme.compute7(colorScheme11.getIntValue13(), intValue22, 0.25F);
               int intValue29 = ColorScheme.compute6(intValue28, Math.round(26.0F * floatValue71 * floatValue70));
               int intValue30 = ColorScheme.compute6(intValue28, 0);
               renderManager10.invoke34(floatValue75, g, floatValue73, i, 0.0F, intValue30, intValue29);
               renderManager10.invoke34(floatValue75 + floatValue73, g, floatValue73 * 0.6F, i, 0.0F, intValue29, intValue30);
            } finally {
               renderManager10.invoke20();
               renderManager10.invoke25();
            }
         }

         float floatValue76 = 7.4F;
         float floatValue77 = ClickGuiRenderUtils.measure2(metrics10, FontRegistry.resolve4(), moduleRiskLevel4.getText2(), floatValue76);
         int intValue31 = ColorScheme.compute6(ColorScheme.compute7(intValue22, colorScheme11.getIntValue13(), flag10 ? 0.34F : 0.56F), Math.round(246.0F * j));
         if (bl) {
            float floatValue78 = f + metrics10.measure(7.5F);
            ClickGuiRenderUtils.invoke4(renderManager10, metrics10, FontRegistry.resolve4(), floatValue78, g, i, floatValue76, moduleRiskLevel4.getText2(), intValue31);
            String text3 = moduleRiskLevel4.getText().toUpperCase(Locale.ROOT);
            int intValue32 = ColorScheme.compute6(ColorScheme.compute7(intValue22, colorScheme11.getIntValue13(), flag10 ? 0.5F : 0.74F), Math.round(242.0F * j));
            ClickGuiRenderUtils.invoke4(
               renderManager10, metrics10, FontRegistry.fontObject4, floatValue78 + floatValue77 + metrics10.measure(5.0F), g, i, 8.0F, text3, intValue32
            );
         } else {
            float floatValue79 = f + (h - floatValue77) * 0.5F;
            ClickGuiRenderUtils.invoke4(renderManager10, metrics10, FontRegistry.resolve4(), floatValue79, g, i, floatValue76, moduleRiskLevel4.getText2(), intValue31);
         }
      }
   }

   private void invoke11(
      RenderManager renderManager11, ClickGuiState clickGuiState8, Module module, float f, float g, float h, float i, float j, ThemeContext themeContext9
   ) {
      Metrics metrics11 = themeContext9.getMetrics();
      ColorScheme colorScheme12 = themeContext9.getColorScheme();
      float floatValue80 = metrics11.measure(24.0F);
      float floatValue81 = metrics11.measure(14.0F);
      float floatValue82 = f + g - metrics11.measure(16.0F) - floatValue80;
      int intValue33 = colorScheme12.getIntValue9();
      int intValue34 = ColorScheme.compute7(colorScheme12.getIntValue15(), colorScheme12.getIntValue14(), 0.5F);
      int intValue35 = ColorScheme.compute7(intValue33, intValue34, i);
      if (i > 0.01F) {
         int intValue36 = colorScheme12.isFlag()
            ? ColorScheme.compute5(0, 0, 0, Math.round(24.0F * i))
            : ColorScheme.compute6(colorScheme12.getIntValue15(), Math.round(40.0F * i));
         renderManager11.invoke41(
            floatValue82,
            h,
            floatValue80,
            floatValue81,
            floatValue81 * 0.5F,
            metrics11.measure(colorScheme12.isFlag() ? 8.0F : 4.0F) * i,
            metrics11.measure(colorScheme12.isFlag() ? 1.5F : 1.0F),
            intValue36
         );
      }

      if (i > 0.5F) {
         renderManager11.invoke34(floatValue82, h, floatValue80, floatValue81, floatValue81 * 0.5F, colorScheme12.getIntValue15(), colorScheme12.getIntValue14());
      } else {
         renderManager11.invoke5(floatValue82, h, floatValue80, floatValue81, floatValue81 * 0.5F, ColorScheme.compute7(intValue33, intValue35, i * 2.0F));
      }

      float floatValue83 = metrics11.measure(10.0F);
      float floatValue84 = floatValue80 - floatValue83 - metrics11.measure(4.0F);
      int intValue37 = ColorScheme.compute7(ClickGuiRenderUtils.compute9(colorScheme12), ClickGuiRenderUtils.compute8(colorScheme12), i);
      float floatValue85 = floatValue82 + metrics11.measure(2.0F) + i * floatValue84;
      renderManager11.invoke41(
         floatValue85,
         h + metrics11.measure(2.0F),
         floatValue83,
         floatValue83,
         floatValue83 * 0.5F,
         metrics11.measure(3.0F),
         metrics11.measure(0.5F),
         ColorScheme.compute5(0, 0, 0, Math.round(60.0F * (0.5F + i * 0.5F)))
      );
      renderManager11.invoke5(floatValue85, h + metrics11.measure(2.0F), floatValue83, floatValue83, floatValue83 * 0.5F, intValue37);
      if (this.check(module)) {
         float floatValue86 = clickGuiState8.measure7(AnimationKeyRegistry.resolve18(module));
         float floatValue87 = Math.max(floatValue86, j * 0.35F);
         int intValue38 = ColorScheme.compute7(ClickGuiRenderUtils.compute5(colorScheme12), ClickGuiRenderUtils.compute4(colorScheme12), i);
         ClickGuiRenderUtils.invoke4(
            renderManager11,
            metrics11,
            FontRegistry.fontObject3,
            floatValue82 - metrics11.measure(22.0F),
            h,
            metrics11.measure(14.0F),
            14.0F,
            "I",
            ColorScheme.compute7(intValue38, colorScheme12.getIntValue14(), floatValue87)
         );
      }
   }

   private void invoke12(RenderManager renderManager12, Module module, float f, float g, float h, int i, ThemeContext themeContext10) {
      Metrics metrics12 = themeContext10.getMetrics();
      ColorScheme colorScheme13 = themeContext10.getColorScheme();
      float floatValue88 = g + metrics12.measure(38.0F);
      List items2 = ClickGuiRenderUtils.resolve2(
         FontRegistry.fontObject,
         module.description == null ? "" : module.description,
         10.0F,
         Math.max(metrics12.measure(160.0F), h - metrics12.measure(90.0F)),
         10
      );

      for (int intValue39 = 0; intValue39 < items2.size(); intValue39++) {
         ClickGuiRenderUtils.invoke4(
            renderManager12,
            metrics12,
            FontRegistry.fontObject,
            f + metrics12.measure(16.0F),
            floatValue88 + intValue39 * metrics12.measure(12.0F),
            metrics12.measure(12.0F),
            10.0F,
            (String)items2.get(intValue39),
            i
         );
      }

      if (module.expanded || module.bindKey != -1) {
         String text4 = module.expanded ? "..." : KeyboardKey.resolve(module.bindKey);
         float floatValue89 = ClickGuiRenderUtils.measure(FontRegistry.fontObject, text4, 10.0F);
         ClickGuiRenderUtils.invoke4(
            renderManager12,
            metrics12,
            FontRegistry.fontObject,
            f + metrics12.getFloatValue14() - metrics12.measure(30.0F) - floatValue89,
            floatValue88,
            metrics12.measure(12.0F),
            10.0F,
            text4,
            i
         );
         ClickGuiRenderUtils.invoke4(
            renderManager12,
            metrics12,
            FontRegistry.fontObject5,
            f + metrics12.getFloatValue14() - metrics12.measure(26.0F),
            floatValue88,
            metrics12.measure(12.0F),
            10.0F,
            "g",
            ColorScheme.compute7(colorScheme13.getIntValue9(), ClickGuiRenderUtils.compute5(colorScheme13), this.measure9(i))
         );
      }
   }

   private float measure9(int i) {
      return (i >>> 24 & 0xFF) / 255.0F;
   }

   private float measure10(float f, float g, float h) {
      float floatValue90 = this.measure7(h);
      return f + (g - f) * floatValue90;
   }

   private float measure11(Module module, float f, ThemeContext themeContext11) {
      Metrics metrics13 = themeContext11.getMetrics();
      String text5 = module.description == null ? "" : module.description;
      if (text5.isBlank()) {
         return metrics13.getFloatValue15();
      } else {
         int intValue40 = ClickGuiRenderUtils.resolve2(FontRegistry.fontObject, text5, 10.0F, Math.max(metrics13.measure(160.0F), f - metrics13.measure(90.0F)), 10).size();
         return Math.max(metrics13.getFloatValue15(), metrics13.measure(54.0F) + Math.max(1, intValue40) * metrics13.measure(12.0F));
      }
   }

   private boolean check(Module module) {
      return SpecialModuleCardHandlers.check(module) || !module.getVisibleSettings().isEmpty();
   }

   private void invoke13(RenderManager renderManager13, ClickGuiState clickGuiState9, Module module, float f, float g, float h, ThemeContext themeContext12) {
      Metrics metrics14 = themeContext12.getMetrics();
      float floatValue91 = g;

      for (Setting setting : module.getVisibleSettings()) {
         if (setting instanceof SpacerSetting spacerSetting) {
            floatValue91 += metrics14.measure(spacerSetting.getFloatValue());
         } else {
            float floatValue92 = clickGuiState9.measure7(AnimationKeyRegistry.resolve22(setting));
            if (!(floatValue92 < 0.01F)) {
               float floatValue93 = (1.0F - floatValue92) * metrics14.measure(8.0F);
               renderManager13.invoke65(floatValue92);

               try {
                  this.settingsRenderer.invoke(renderManager13, clickGuiState9, setting, f, floatValue91 + floatValue93, h, themeContext12);
               } finally {
                  renderManager13.invoke66();
               }

               float floatValue94 = this.settingsRenderer.measure(setting, metrics14, clickGuiState9);
               float floatValue95 = 0.0F;
               if (setting instanceof ModeSetting modeSetting) {
                  float floatValue96 = clickGuiState9.measure7(AnimationKeyRegistry.resolve30(modeSetting));
                  if (floatValue96 > 0.01F) {
                     floatValue95 = (metrics14.measure(6.0F) + modeSetting.options.size() * metrics14.measure(18.0F) + metrics14.measure(4.0F)) * floatValue96;
                  }
               } else if (setting instanceof FoundryShaderSetting foundryShaderSetting) {
                  float floatValue97 = clickGuiState9.measure7(AnimationKeyRegistry.resolve30(foundryShaderSetting));
                  if (floatValue97 > 0.01F) {
                     floatValue95 = SettingsRenderer.measure16(foundryShaderSetting, metrics14) * floatValue97;
                  }
               }

               floatValue91 += (floatValue94 + floatValue95 + metrics14.measure(12.0F)) * floatValue92;
            }
         }
      }
   }

   @Generated
   public ModuleListRenderer(SettingsRenderer settingsRenderer) {
      this.settingsRenderer = settingsRenderer;
   }
}
