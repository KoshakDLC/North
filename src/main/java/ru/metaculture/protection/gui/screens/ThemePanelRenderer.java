package ru.metaculture.protection;

import java.util.List;

public final class ThemePanelRenderer {
   private static final float FLOAT_VALUE = 1.5F;
   private static final float FLOAT_VALUE_2 = 2.0F;
   private static final float FLOAT_VALUE_3 = 3.5F;

   public void invoke(RenderManager renderManager, ClickGuiState clickGuiState, ClickGuiGeometry clickGuiGeometry, ThemeContext themeContext, float f) {
      float floatValue = clickGuiState.measure7(AnimationKeyRegistry.resolve32());
      if (!(floatValue <= 0.005F)) {
         Metrics metrics = resolve(themeContext);
         ColorScheme colorScheme = themeContext.getColorScheme();
         float floatValue2 = clickGuiGeometry.getFloatValue22();
         float floatValue3 = clickGuiGeometry.getFloatValue23();
         float floatValue4 = metrics.getFloatValue18();
         float floatValue5 = metrics.getFloatValue19();
         float floatValue6 = metrics.measure(14.0F);
         float floatValue7 = this.measure3(floatValue);
         RenderManager.RenderManagerState2 renderManagerState2 = !clickGuiState.isFlag7() && !clickGuiState.isFlag16()
            ? renderManager.resolve3(floatValue2, floatValue3, floatValue4, floatValue5)
            : null;
         if (renderManagerState2 != null) {
            try {
               this.invoke2(renderManager, clickGuiState, clickGuiGeometry, themeContext, f, floatValue2, floatValue3, floatValue4, floatValue5, floatValue6);
            } finally {
               renderManager.invoke14(renderManagerState2);
            }

            int intValue = ClickGuiRenderUtils.compute10(colorScheme);
            int intValue2 = colorScheme.isFlag()
               ? ClickGuiRenderUtils.compute13(colorScheme, 0.95F)
               : ColorScheme.compute7(ColorScheme.compute5(255, 255, 255, 64), colorScheme.getIntValue14(), 0.3F);
            int intValue3 = colorScheme.getIntValue15();
            int intValue4 = colorScheme.getIntValue14();
            float floatValue8 = this.measure4();
            renderManager.invoke16(renderManagerState2, floatValue2, floatValue3, floatValue4, floatValue5, floatValue6, intValue, intValue2, intValue3, intValue4, floatValue7, floatValue8);
         } else {
            this.invoke2(renderManager, clickGuiState, clickGuiGeometry, themeContext, f, floatValue2, floatValue3, floatValue4, floatValue5, floatValue6);
         }
      }
   }

   private static Metrics resolve(ThemeContext themeContext2) {
      Metrics metrics2 = themeContext2.getMetrics();
      return metrics2.resolve6(metrics2.getFloatValue2());
   }

   private void invoke2(
      RenderManager renderManager2,
      ClickGuiState clickGuiState2,
      ClickGuiGeometry clickGuiGeometry2,
      ThemeContext themeContext3,
      float f,
      float g,
      float h,
      float i,
      float j,
      float k
   ) {
      Metrics metrics3 = resolve(themeContext3);
      ColorScheme colorScheme2 = themeContext3.getColorScheme();
      float floatValue9 = metrics3.measure(8.0F);
      float floatValue10 = metrics3.measure(44.0F);
      float floatValue11 = metrics3.measure(8.0F);
      renderManager2.invoke5(
         g - 1.0F,
         h - 1.0F,
         i + 2.0F,
         j + 2.0F,
         k + 1.0F,
         colorScheme2.isFlag() ? ColorScheme.compute5(255, 255, 255, 194) : ColorScheme.compute5(15, 16, 19, 255)
      );
      renderManager2.invoke44(g, h, i, j, k, 0.88F);
      renderManager2.invoke5(g, h, i, j, k, ClickGuiRenderUtils.compute10(colorScheme2));
      if (colorScheme2.isFlag()) {
         renderManager2.invoke28(
            g + metrics3.measure(0.8F),
            h + metrics3.measure(0.8F),
            i - metrics3.measure(1.6F),
            j - metrics3.measure(1.6F),
            Math.max(0.0F, k - metrics3.measure(0.8F)),
            ClickGuiRenderUtils.compute13(colorScheme2, 0.95F),
            metrics3.measure(0.85F)
         );
      }

      this.invoke4(renderManager2, g, h, i, j, k, colorScheme2, f);
      this.invoke5(renderManager2, g, h, i, j, k, f);
      ThemeGridLayout themeGridLayout = ThemeGridLayout.resolve(clickGuiGeometry2, metrics3);
      float floatValue12 = themeGridLayout.getFloatValue3();
      float floatValue13 = themeGridLayout.getFloatValue2();
      float floatValue14 = themeGridLayout.getFloatValue4();
      renderManager2.invoke6(
         g + floatValue9, h + floatValue9, floatValue12, floatValue10, floatValue11, floatValue11, metrics3.measure(4.0F), metrics3.measure(4.0F), ClickGuiRenderUtils.compute11(colorScheme2)
      );
      renderManager2.invoke6(g + floatValue9, floatValue13, floatValue12, floatValue14, metrics3.measure(4.0F), metrics3.measure(4.0F), floatValue11, floatValue11, ClickGuiRenderUtils.compute11(colorScheme2));
      if (colorScheme2.isFlag()) {
         renderManager2.invoke29(
            g + floatValue9 + metrics3.measure(0.7F),
            h + floatValue9 + metrics3.measure(0.7F),
            floatValue12 - metrics3.measure(1.4F),
            floatValue10 - metrics3.measure(1.4F),
            Math.max(0.0F, floatValue11 - metrics3.measure(0.7F)),
            Math.max(0.0F, floatValue11 - metrics3.measure(0.7F)),
            Math.max(0.0F, metrics3.measure(4.0F) - metrics3.measure(0.7F)),
            Math.max(0.0F, metrics3.measure(4.0F) - metrics3.measure(0.7F)),
            ClickGuiRenderUtils.compute13(colorScheme2, 0.72F),
            metrics3.measure(0.75F)
         );
         renderManager2.invoke29(
            g + floatValue9 + metrics3.measure(0.7F),
            floatValue13 + metrics3.measure(0.7F),
            floatValue12 - metrics3.measure(1.4F),
            floatValue14 - metrics3.measure(1.4F),
            Math.max(0.0F, metrics3.measure(4.0F) - metrics3.measure(0.7F)),
            Math.max(0.0F, metrics3.measure(4.0F) - metrics3.measure(0.7F)),
            Math.max(0.0F, floatValue11 - metrics3.measure(0.7F)),
            Math.max(0.0F, floatValue11 - metrics3.measure(0.7F)),
            ClickGuiRenderUtils.compute13(colorScheme2, 0.72F),
            metrics3.measure(0.75F)
         );
      }

      this.invoke6(renderManager2, metrics3, colorScheme2, g, h, i, floatValue9, floatValue10, themeContext3);
      this.invoke3(renderManager2, clickGuiState2, themeGridLayout, metrics3, colorScheme2);
      this.invoke7(renderManager2, clickGuiState2, themeGridLayout, themeContext3);
      this.invoke11(renderManager2, clickGuiState2, clickGuiGeometry2, themeContext3, floatValue13, floatValue14);
   }

   private void invoke3(
      RenderManager renderManager3, ClickGuiState clickGuiState3, ThemeGridLayout themeGridLayout2, Metrics metrics4, ColorScheme colorScheme3
   ) {
      float floatValue15 = themeGridLayout2.getFloatValue5();
      float floatValue16 = themeGridLayout2.getFloatValue6();
      float floatValue17 = themeGridLayout2.getFloatValue7();
      float floatValue18 = themeGridLayout2.getFloatValue8();
      float floatValue19 = metrics4.measure(8.0F);
      float floatValue20 = clickGuiState3.measure7(AnimationKeyRegistry.resolve33());
      float floatValue21 = clickGuiState3.measure7(AnimationKeyRegistry.resolve34());
      String text = clickGuiState3.getText2();
      boolean flag = !clickGuiState3.isFlag7() && clickGuiState3.isFlag6();
      renderManager3.invoke5(floatValue15, floatValue16, floatValue17, floatValue18, floatValue19, ClickGuiRenderUtils.compute11(colorScheme3));
      renderManager3.invoke5(floatValue15, floatValue16, floatValue17, floatValue18, floatValue19, ColorScheme.compute7(colorScheme3.getIntValue4(), colorScheme3.getIntValue6(), floatValue20));
      if (colorScheme3.isFlag()) {
         renderManager3.invoke28(
            floatValue15 + metrics4.measure(0.7F),
            floatValue16 + metrics4.measure(0.7F),
            floatValue17 - metrics4.measure(1.4F),
            floatValue18 - metrics4.measure(1.4F),
            Math.max(0.0F, floatValue19 - metrics4.measure(0.7F)),
            ClickGuiRenderUtils.compute13(colorScheme3, 0.78F),
            metrics4.measure(0.75F)
         );
      }

      if (floatValue20 > 0.01F) {
         renderManager3.invoke28(
            floatValue15 - metrics4.measure(0.5F),
            floatValue16 - metrics4.measure(0.5F),
            floatValue17 + metrics4.measure(1.0F),
            floatValue18 + metrics4.measure(1.0F),
            floatValue19 + metrics4.measure(0.5F),
            ColorScheme.compute6(colorScheme3.getIntValue14(), Math.round(50.0F * floatValue20)),
            1.0F
         );
      }

      float floatValue22 = metrics4.measure(10.0F);
      float floatValue23 = themeGridLayout2.getFloatValue82();
      float floatValue24 = floatValue17 - floatValue22 * 2.0F - floatValue23;
      int intValue5 = ColorScheme.compute7(colorScheme3.getIntValue12(), colorScheme3.getIntValue13(), floatValue20);
      float floatValue25 = text.isEmpty() ? 0.0F : ClickGuiRenderUtils.measure2(metrics4, FontRegistry.fontObject, text, 10.0F);
      float floatValue26 = floatValue25 > floatValue24 ? floatValue24 - floatValue25 : 0.0F;
      renderManager3.invoke23((int)floatValue15, (int)floatValue16, (int)(floatValue17 - floatValue23), (int)floatValue18);
      if (!text.isEmpty()) {
         ClickGuiRenderUtils.invoke4(renderManager3, metrics4, FontRegistry.fontObject, floatValue15 + floatValue22 + floatValue26, floatValue16, floatValue18, 10.0F, text, intValue5);
      } else if (!flag) {
         ClickGuiRenderUtils.invoke4(
            renderManager3, metrics4, FontRegistry.fontObject, floatValue15 + floatValue22, floatValue16, floatValue18, 10.0F, "Поиск тем...", colorScheme3.getIntValue11()
         );
      }

      if (flag) {
         float floatValue27 = (float)((Math.sin(System.currentTimeMillis() * 0.006) + 1.0) * 0.5);
         float floatValue28 = metrics4.measure(11.0F);
         renderManager3.invoke5(
            floatValue15 + floatValue22 + floatValue26 + floatValue25 + metrics4.measure(1.0F),
            floatValue16 + (floatValue18 - floatValue28) * 0.5F,
            Math.max(1.0F, metrics4.measure(1.0F)),
            floatValue28,
            0.0F,
            ColorScheme.compute6(colorScheme3.compute4(), Math.round(255.0F * floatValue27))
         );
      }

      renderManager3.invoke25();
      float floatValue29 = Math.max(floatValue20 * 0.3F, floatValue21);
      if (floatValue29 > 0.01F) {
         float floatValue30 = ClickGuiRenderUtils.measure2(metrics4, FontRegistry.fontObject5, "l", 10.0F);
         ClickGuiRenderUtils.invoke4(
            renderManager3,
            metrics4,
            FontRegistry.fontObject5,
            themeGridLayout2.measure2() + (floatValue23 - floatValue30) * 0.5F,
            floatValue16,
            floatValue18,
            10.0F,
            "l",
            ColorScheme.compute6(colorScheme3.compute4(), Math.round(255.0F * floatValue29))
         );
      }
   }

   private void invoke4(RenderManager renderManager4, float f, float g, float h, float i, float j, ColorScheme colorScheme4, float k) {
      if (!colorScheme4.isFlag()) {
         renderManager4.invoke20();
         renderManager4.invoke24(f, g, h, i, j, j, j, j);

         try {
            float floatValue31 = k * (float) (Math.PI * 2);
            float floatValue32 = 0.8F + 0.2F * (float)Math.sin(floatValue31 * 0.3);
            float floatValue33 = h * 0.75F;
            float floatValue34 = i * 0.55F;
            float floatValue35 = Math.min(floatValue33, floatValue34) * 0.5F;
            float floatValue36 = f + h * 0.05F + (float)Math.cos(floatValue31 * 0.1) * h * 0.04F;
            float floatValue37 = g + i * 0.06F + (float)Math.sin(floatValue31 * 0.08) * i * 0.03F;
            renderManager4.invoke41(
               floatValue36, floatValue37, floatValue33, floatValue34, floatValue35, floatValue33 * 0.45F, floatValue33 * 0.12F, ColorScheme.compute6(colorScheme4.getIntValue14(), Math.round(3.0F * floatValue32))
            );
            float floatValue38 = 0.75F + 0.25F * (float)Math.sin(floatValue31 * 0.22 + 2.094F);
            float floatValue39 = h * 0.65F;
            float floatValue40 = i * 0.5F;
            float floatValue41 = Math.min(floatValue39, floatValue40) * 0.5F;
            float floatValue42 = f + h * 0.35F + (float)Math.cos(floatValue31 * 0.14 + 1.2F) * h * 0.05F;
            float floatValue43 = g + i * 0.5F + (float)Math.sin(floatValue31 * 0.1 + 0.7F) * i * 0.04F;
            renderManager4.invoke41(
               floatValue42, floatValue43, floatValue39, floatValue40, floatValue41, floatValue39 * 0.4F, floatValue39 * 0.1F, ColorScheme.compute6(colorScheme4.getIntValue15(), Math.round(2.0F * floatValue38))
            );
         } finally {
            renderManager4.invoke20();
            renderManager4.invoke25();
         }
      }
   }

   private void invoke5(RenderManager renderManager5, float f, float g, float h, float i, float j, float k) {
      renderManager5.invoke20();
      renderManager5.invoke24(f, g, h, i, j, j, j, j);

      try {
         long longValue = (long)(k * 10000.0F) + 9999L;
         float floatValue44 = 36.0F;
         float floatValue45 = 36.0F;
         int intValue6 = (int)Math.ceil(h / floatValue44) + 1;
         int intValue7 = (int)Math.ceil(i / floatValue45) + 1;

         for (int intValue8 = 0; intValue8 < intValue7; intValue8++) {
            for (int intValue9 = 0; intValue9 < intValue6; intValue9++) {
               long longValue2 = longValue + intValue9 * 73856093L + intValue8 * 19349663L ^ 25214903917L;
               longValue2 = longValue2 * 6364136223846793005L + 1442695040888963407L;
               int intValue10 = (int)(longValue2 >>> 48 & 15L);
               if (intValue10 <= 5) {
                  int intValue11 = 3 + (intValue10 & 3);
                  float floatValue46 = Math.round(f + intValue9 * floatValue44 + (float)(longValue2 >>> 32 & 15L) - 8.0F);
                  float floatValue47 = Math.round(g + intValue8 * floatValue45 + (float)(longValue2 >>> 16 & 15L) - 8.0F);
                  float floatValue48 = 1.0F + (intValue10 & 1);
                  int intValue12 = (intValue10 & 1) == 0 ? ColorScheme.compute5(255, 255, 255, intValue11) : ColorScheme.compute5(0, 0, 0, intValue11 + 1);
                  renderManager5.invoke5(floatValue46, floatValue47, floatValue48, floatValue48, 0.0F, intValue12);
               }
            }
         }
      } finally {
         renderManager5.invoke20();
         renderManager5.invoke25();
      }
   }

   private void invoke6(
      RenderManager renderManager6,
      Metrics metrics5,
      ColorScheme colorScheme5,
      float f,
      float g,
      float h,
      float i,
      float j,
      ThemeContext themeContext4
   ) {
      float floatValue49 = metrics5.measure(30.0F);
      float floatValue50 = f + i + metrics5.measure(8.0F);
      float floatValue51 = g + i + (j - floatValue49) * 0.5F;
      renderManager6.invoke5(
         floatValue50,
         floatValue51,
         floatValue49,
         floatValue49,
         metrics5.measure(7.0F),
         colorScheme5.isFlag() ? ClickGuiRenderUtils.compute12(colorScheme5, 0.0F) : colorScheme5.getIntValue4()
      );
      renderManager6.invoke28(
         floatValue50,
         floatValue51,
         floatValue49,
         floatValue49,
         metrics5.measure(7.0F),
         colorScheme5.isFlag() ? ClickGuiRenderUtils.compute13(colorScheme5, 0.82F) : colorScheme5.getIntValue6(),
         colorScheme5.isFlag() ? metrics5.measure(0.85F) : 1.5F
      );
      ClickGuiRenderUtils.invoke9(
         renderManager6,
         metrics5,
         colorScheme5,
         floatValue50 + metrics5.measure(5.0F),
         floatValue51 + metrics5.measure(5.0F),
         metrics5.measure(9.0F),
         metrics5.measure(2.0F)
      );
      float floatValue52 = metrics5.measure(5.0F);
      float floatValue53 = g + i;
      float floatValue54 = floatValue50 + floatValue49 + floatValue52;
      ClickGuiRenderUtils.invoke4(
         renderManager6, metrics5, FontRegistry.fontObject, floatValue54, floatValue53, j, 11.0F, "t.me/soezproject", colorScheme5.getIntValue12()
      );
      floatValue54 += ClickGuiRenderUtils.measure2(metrics5, FontRegistry.fontObject, "t.me/soezproject", 11.0F) + floatValue52;
      ClickGuiRenderUtils.invoke4(renderManager6, metrics5, FontRegistry.fontObject8, floatValue54, floatValue53, j, 8.0F, "k", colorScheme5.getIntValue11());
      floatValue54 += ClickGuiRenderUtils.measure2(metrics5, FontRegistry.fontObject8, "k", 8.0F) + floatValue52;
      ClickGuiRenderUtils.invoke4(renderManager6, metrics5, FontRegistry.fontObject5, floatValue54, floatValue53, j, 11.0F, "p", colorScheme5.compute4());
      floatValue54 += ClickGuiRenderUtils.measure2(metrics5, FontRegistry.fontObject5, "p", 11.0F) + floatValue52;
      ClickGuiRenderUtils.invoke4(renderManager6, metrics5, FontRegistry.fontObject4, floatValue54, floatValue53, j, 11.0F, "Themes", colorScheme5.getIntValue13());
      float floatValue55 = metrics5.measure(20.0F);
      float floatValue56 = f + h - metrics5.measure(15.0F) - floatValue55;
      float floatValue57 = g + metrics5.measure(20.0F);
      renderManager6.invoke5(
         floatValue56,
         floatValue57,
         floatValue55,
         floatValue55,
         metrics5.measure(5.0F),
         colorScheme5.isFlag() ? ClickGuiRenderUtils.compute12(colorScheme5, 0.2F) : colorScheme5.getIntValue5()
      );
      renderManager6.invoke28(
         floatValue56,
         floatValue57,
         floatValue55,
         floatValue55,
         metrics5.measure(5.0F),
         colorScheme5.isFlag() ? ClickGuiRenderUtils.compute13(colorScheme5, 0.86F) : colorScheme5.getIntValue7(),
         metrics5.measure(0.8F)
      );
      float floatValue58 = ClickGuiRenderUtils.measure2(metrics5, FontRegistry.fontObject5, "l", 14.0F);
      ClickGuiRenderUtils.invoke4(
         renderManager6, metrics5, FontRegistry.fontObject5, floatValue56 + (floatValue55 - floatValue58) * 0.5F, floatValue57, floatValue55, 14.0F, "l", colorScheme5.getIntValue12()
      );
   }

   private void invoke7(RenderManager renderManager7, ClickGuiState clickGuiState4, ThemeGridLayout themeGridLayout3, ThemeContext themeContext5) {
      Metrics metrics6 = resolve(themeContext5);
      ColorScheme colorScheme6 = themeContext5.getColorScheme();
      float floatValue59 = themeGridLayout3.getFloatValue3();
      float floatValue60 = themeGridLayout3.getFloatValue10();
      float floatValue61 = themeGridLayout3.getFloatValue11();
      float floatValue62 = metrics6.measure(8.0F);
      float floatValue63 = metrics6.measure(8.0F);
      float floatValue64 = (float)(System.currentTimeMillis() % 4000L) / 4000.0F;
      float floatValue65 = themeGridLayout3.getFloatValue2();
      float floatValue66 = themeGridLayout3.getFloatValue4();
      float floatValue67 = clickGuiState4.measure4();
      float floatValue68 = ClickGuiRenderUtils.measure13(metrics6);
      ThemePalette var17x = themeContext5.getThemePalette();
      List<ThemePalette.Swatch> items = var17x == null || var17x.getItems() == null ? List.of() : var17x.getItems();
      List<Integer> var18x = clickGuiState4.resolve3(var17x);
      List<Integer> items2 = var18x == null ? List.of() : var18x;
      if (items2.isEmpty()) {
         String text2 = "Ничего не найдено";
         float floatValue69 = ClickGuiRenderUtils.measure2(metrics6, FontRegistry.fontObject, text2, 10.0F);
         ClickGuiRenderUtils.invoke4(
            renderManager7, metrics6, FontRegistry.fontObject, themeGridLayout3.getFloatValue() + (floatValue59 - floatValue69) * 0.5F, floatValue65, floatValue66, 10.0F, text2, colorScheme6.getIntValue11()
         );
      } else {
         ClickGuiRenderUtils.invoke14(
            renderManager7,
            metrics6,
            colorScheme6,
            themeGridLayout3.getFloatValue(),
            floatValue65,
            floatValue59,
            floatValue66,
            metrics6.measure(4.0F),
            metrics6.measure(4.0F),
            floatValue63,
            floatValue63,
            clickGuiState4.getSpringAnimation3().getFloatValue2(),
            () -> {
               for (int var17xx = 0; var17xx < items2.size(); var17xx++) {
                  Integer var18xx = items2.get(var17xx);
                  if (var18xx == null || var18xx < 0 || var18xx >= items.size()) {
                     continue;
                  }

                  int var18xxx = var18xx;
                  ThemePalette.Swatch var19x = items.get(var18xxx);
                  ThemeGridLayout.Cell var20x = themeGridLayout3.resolve2(var17xx, floatValue67);
                  if (themeGridLayout3.check(var20x, floatValue68)) {
                     float floatValue70 = var20x.x();
                     float floatValue71 = var20x.y();
                     float floatValue72 = clickGuiState4.measure7(AnimationKeyRegistry.resolve13(var18xxx));
                     float floatValue73 = clickGuiState4.measure7(AnimationKeyRegistry.resolve14(var18xxx));
                     float floatValue74 = Math.max(floatValue72, floatValue73);
                     if (floatValue73 > 0.01F) {
                        float floatValue75 = 0.72F + 0.28F * (float)Math.sin(floatValue64 * Math.PI * 2.0 + var18xxx * 0.5);
                        int intValue13 = colorScheme6.isFlag()
                           ? ColorScheme.compute5(0, 0, 0, Math.round(34.0F * floatValue73 * floatValue75))
                           : ColorScheme.compute6(var19x.getIntValue(), Math.round(62.0F * floatValue73 * floatValue75));
                        renderManager7.invoke41(floatValue70, floatValue71, floatValue60, floatValue61, floatValue62, metrics6.measure(18.0F) * floatValue73 * floatValue75, metrics6.measure(4.0F), intValue13);
                     }

                     renderManager7.invoke5(
                        floatValue70,
                        floatValue71,
                        floatValue60,
                        floatValue61,
                        floatValue62,
                        colorScheme6.isFlag()
                           ? ClickGuiRenderUtils.compute12(colorScheme6, floatValue72 * 0.7F)
                           : ColorScheme.compute7(colorScheme6.getIntValue3(), colorScheme6.getIntValue6(), floatValue72 * 0.7F)
                     );
                     if (floatValue73 < 0.5F) {
                        float floatValue76 = 1.0F - floatValue73 * 2.0F;
                        int intValue14 = colorScheme6.isFlag()
                           ? ColorScheme.compute7(ClickGuiRenderUtils.compute13(colorScheme6, 0.85F), ColorScheme.compute6(var19x.getIntValue(), 90), floatValue72 * 0.22F)
                           : ColorScheme.compute7(colorScheme6.getIntValue5(), ColorScheme.compute6(var19x.getIntValue(), 110), floatValue72 * 0.6F);
                        int intValue15 = intValue14 >>> 24 & 0xFF;
                        renderManager7.invoke28(
                           floatValue70,
                           floatValue71,
                           floatValue60,
                           floatValue61,
                           floatValue62,
                           ColorScheme.compute6(intValue14, Math.round(intValue15 * floatValue76)),
                           colorScheme6.isFlag() ? metrics6.measure(0.85F) : 1.5F
                        );
                     }

                     float floatValue77 = ClickGuiRenderUtils.measure19(floatValue64 + var18xxx * 0.071F);
                     if (floatValue72 > 0.01F || floatValue73 > 0.01F) {
                        float floatValue78 = metrics6.measure(5.0F);
                        float floatValue79 = metrics6.measure(18.0F);
                        float floatValue80 = Math.max(0.0F, floatValue60 - floatValue78 * 2.0F - floatValue79);
                        float floatValue81 = floatValue70 + floatValue78 + floatValue80 * floatValue77;
                        renderManager7.invoke20();
                        renderManager7.invoke24(
                           floatValue70 + metrics6.measure(1.0F),
                           floatValue71 + metrics6.measure(1.0F),
                           floatValue60 - metrics6.measure(2.0F),
                           floatValue61 - metrics6.measure(2.0F),
                           Math.max(0.0F, floatValue62 - metrics6.measure(1.0F)),
                           Math.max(0.0F, floatValue62 - metrics6.measure(1.0F)),
                           Math.max(0.0F, floatValue62 - metrics6.measure(1.0F)),
                           Math.max(0.0F, floatValue62 - metrics6.measure(1.0F))
                        );

                        try {
                           renderManager7.invoke34(
                              floatValue81,
                              floatValue71 + metrics6.measure(2.0F),
                              floatValue79,
                              floatValue61 - metrics6.measure(4.0F),
                              metrics6.measure(7.0F),
                              ColorScheme.compute5(255, 255, 255, 0),
                              ColorScheme.compute5(255, 255, 255, Math.round((16.0F + floatValue73 * 20.0F) * floatValue74))
                           );
                           renderManager7.invoke34(
                              floatValue81 + floatValue79 * 0.42F,
                              floatValue71 + metrics6.measure(3.0F),
                              floatValue79 * 0.42F,
                              floatValue61 - metrics6.measure(6.0F),
                              metrics6.measure(6.0F),
                              ColorScheme.compute5(255, 255, 255, Math.round((10.0F + floatValue73 * 14.0F) * floatValue74)),
                              ColorScheme.compute5(255, 255, 255, 0)
                           );
                        } finally {
                           renderManager7.invoke20();
                           renderManager7.invoke25();
                        }
                     }

                     float floatValue82 = metrics6.measure(28.0F);
                     float floatValue83 = metrics6.measure(14.0F);
                     float floatValue84 = Math.round((floatValue70 + floatValue60 - floatValue82 - metrics6.measure(10.0F)) * 2.0F) * 0.5F;
                     float floatValue85 = Math.round((floatValue71 + (floatValue61 - floatValue83) * 0.5F) * 2.0F) * 0.5F;
                     float floatValue86 = 3.5F;
                     float floatValue87 = floatValue70 + metrics6.measure(10.0F);
                     float floatValue88 = Math.max(metrics6.measure(34.0F), floatValue84 - floatValue87 - metrics6.measure(10.0F));
                     String text3 = var19x.getText();
                     String text4 = ClickGuiRenderUtils.resolve4(metrics6, FontRegistry.fontObject4, text3, 10.0F, floatValue88);
                     ClickGuiRenderUtils.invoke4(
                        renderManager7,
                        metrics6,
                        FontRegistry.fontObject4,
                        floatValue87,
                        floatValue71,
                        floatValue61,
                        10.0F,
                        text4,
                        ColorScheme.compute7(colorScheme6.getIntValue12(), colorScheme6.getIntValue13(), floatValue74)
                     );
                     if (!text4.equals(text3)
                        && !clickGuiState4.isFlag7()
                        && ClickGuiRenderUtils.check(clickGuiState4, themeGridLayout3.getFloatValue(), floatValue65, floatValue59, floatValue66)
                        && ClickGuiRenderUtils.check(clickGuiState4, floatValue70, floatValue71, floatValue60, floatValue61)) {
                        clickGuiState4.invoke42("theme:" + var18xxx, text3, floatValue87, floatValue71 + floatValue61 + metrics6.measure(8.0F));
                     }

                     if (floatValue73 > 0.01F) {
                        int intValue16 = colorScheme6.isFlag()
                           ? ColorScheme.compute5(0, 0, 0, Math.round(22.0F * floatValue73))
                           : ColorScheme.compute6(var19x.getIntValue(), Math.round(68.0F * floatValue73));
                        renderManager7.invoke41(
                           floatValue84 - metrics6.measure(1.5F),
                           floatValue85 - metrics6.measure(1.5F),
                           floatValue82 + metrics6.measure(3.0F),
                           floatValue83 + metrics6.measure(3.0F),
                           floatValue86 + metrics6.measure(1.5F),
                           metrics6.measure(8.0F) * floatValue73,
                           metrics6.measure(1.3F),
                           intValue16
                        );
                     }

                     this.invoke10(renderManager7, var19x, floatValue84, floatValue85, floatValue82, floatValue83, floatValue86);
                     if (floatValue73 > 0.01F) {
                        renderManager7.invoke28(
                           floatValue84, floatValue85, floatValue82, floatValue83, floatValue86, ColorScheme.compute6(ColorScheme.compute5(255, 255, 255, 255), Math.round(36.0F * floatValue73)), 1.0F
                        );
                     }
                  }
               }

               this.invoke8(renderManager7, clickGuiState4, items, colorScheme6, floatValue62, metrics6);
            }
         );
      }
   }

   private void invoke8(
      RenderManager renderManager8, ClickGuiState clickGuiState5, List<ThemePalette.Swatch> list, ColorScheme colorScheme7, float f, Metrics metrics7
   ) {
      if (clickGuiState5.isFlag25() && clickGuiState5.isFlag26()) {
         int intValue17 = clickGuiState5.getIntValue3();
         int intValue18 = intValue17 >= 0 && intValue17 < list.size() ? intValue17 : -1;
         ThemePalette.Swatch swatch = intValue18 >= 0 ? (ThemePalette.Swatch)list.get(intValue18) : null;
         int intValue19 = swatch != null ? swatch.getIntValue() : colorScheme7.getIntValue14();
         float floatValue89 = clickGuiState5.getFloatValue73();
         float floatValue90 = clickGuiState5.getFloatValue74();
         float floatValue91 = clickGuiState5.getFloatValue75();
         float floatValue92 = clickGuiState5.getFloatValue76();
         if (!(floatValue91 < 1.0F) && !(floatValue92 < 1.0F)) {
            float floatValue93 = Math.max(0.0F, Math.min(1.0F, clickGuiState5.getFloatValue77()));
            float floatValue94 = this.measure(floatValue93);
            float floatValue95 = 1.0F - floatValue94;
            float floatValue96 = (float)Math.sin(floatValue93 * Math.PI);
            if (floatValue95 > 0.01F && clickGuiState5.getFloatValue71() > 1.0F && clickGuiState5.getFloatValue72() > 1.0F) {
               int intValue20 = clickGuiState5.getIntValue4();
               ThemePalette.Swatch swatch2 = intValue20 >= 0 && intValue20 < list.size() ? (ThemePalette.Swatch)list.get(intValue20) : swatch;
               int intValue21 = swatch2 != null ? swatch2.getIntValue() : intValue19;
               this.invoke9(
                  renderManager8,
                  clickGuiState5.getFloatValue69(),
                  clickGuiState5.getFloatValue70(),
                  clickGuiState5.getFloatValue71(),
                  clickGuiState5.getFloatValue72(),
                  f,
                  intValue21,
                  floatValue95 * 0.82F,
                  true,
                  metrics7,
                  colorScheme7.isFlag()
               );
            }

            if (floatValue96 > 0.01F) {
               float floatValue97 = metrics7.measure(3.0F);
               float floatValue98 = f + metrics7.measure(4.0F);
               float floatValue99 = Math.round((this.measure2(clickGuiState5.getFloatValue69(), floatValue89, floatValue94) - floatValue97) * 2.0F) * 0.5F;
               float floatValue100 = Math.round((this.measure2(clickGuiState5.getFloatValue70(), floatValue90, floatValue94) - floatValue97) * 2.0F) * 0.5F;
               float floatValue101 = Math.round((this.measure2(clickGuiState5.getFloatValue71(), floatValue91, floatValue94) + floatValue97 * 2.0F) * 2.0F) * 0.5F;
               float floatValue102 = Math.round((this.measure2(clickGuiState5.getFloatValue72(), floatValue92, floatValue94) + floatValue97 * 2.0F) * 2.0F) * 0.5F;
               int intValue22 = swatch != null ? ColorScheme.compute7(intValue19, swatch.getIntValue2(), 0.28F) : intValue19;
               int intValue23 = colorScheme7.isFlag()
                  ? ColorScheme.compute5(0, 0, 0, Math.round(30.0F * floatValue96))
                  : ColorScheme.compute6(intValue22, Math.round(70.0F * floatValue96));
               renderManager8.invoke41(floatValue99, floatValue100, floatValue101, floatValue102, floatValue98, metrics7.measure(10.0F) * floatValue96, metrics7.measure(2.2F) * floatValue96, intValue23);
               renderManager8.invoke28(
                  floatValue99, floatValue100, floatValue101, floatValue102, floatValue98, ColorScheme.compute6(intValue22, Math.round(82.0F * floatValue96)), metrics7.measure(2.0F + floatValue96 * 1.1F)
               );
            }

            this.invoke9(renderManager8, floatValue89, floatValue90, floatValue91, floatValue92, f, intValue19, 0.06F + floatValue94 * 0.94F, false, metrics7, colorScheme7.isFlag());
         }
      }
   }

   private void invoke9(
      RenderManager renderManager9, float f, float g, float h, float i, float j, int k, float l, boolean bl, Metrics metrics8, boolean bl2
   ) {
      float floatValue103 = Math.max(0.0F, Math.min(1.0F, l));
      if (!(floatValue103 <= 0.005F)) {
         float floatValue104 = bl ? (float)Math.pow(floatValue103, 0.5) : floatValue103;
         float floatValue105 = metrics8.measure(3.0F);
         f -= floatValue105;
         g -= floatValue105;
         h += floatValue105 * 2.0F;
         i += floatValue105 * 2.0F;
         j += metrics8.measure(4.0F);
         f = Math.round(f * 2.0F) * 0.5F;
         g = Math.round(g * 2.0F) * 0.5F;
         h = Math.round(h * 2.0F) * 0.5F;
         i = Math.round(i * 2.0F) * 0.5F;
         j = Math.round(j * 2.0F) * 0.5F;
         float floatValue106 = 1.0F - floatValue104;
         float floatValue107 = metrics8.measure(bl ? 2.4F + floatValue106 * floatValue106 * 3.6F : 1.4F);
         float floatValue108 = metrics8.measure(bl ? 13.0F + floatValue106 * floatValue106 * 8.0F : 8.0F);
         float floatValue109 = bl ? 44.0F * floatValue104 * floatValue104 : 58.0F * floatValue103;
         renderManager9.invoke41(
            f,
            g,
            h,
            i,
            j,
            floatValue108 * floatValue104,
            floatValue107 * floatValue104,
            bl2 ? ColorScheme.compute5(0, 0, 0, Math.round(Math.min(38.0F, floatValue109 * 0.55F))) : ColorScheme.compute6(k, Math.round(floatValue109))
         );
         float floatValue110 = bl ? 120.0F * floatValue104 * floatValue104 : 220.0F * floatValue103;
         float floatValue111 = metrics8.measure(bl ? 2.0F * floatValue104 : 2.0F);
         renderManager9.invoke28(f, g, h, i, j, ColorScheme.compute6(k, Math.round(floatValue110)), floatValue111);
      }
   }

   private float measure(float f) {
      float floatValue112 = Math.max(0.0F, Math.min(1.0F, f));
      return floatValue112 * floatValue112 * floatValue112 * (floatValue112 * (floatValue112 * 6.0F - 15.0F) + 10.0F);
   }

   private float measure2(float f, float g, float h) {
      float floatValue113 = Math.max(0.0F, Math.min(1.0F, h));
      return f + (g - f) * floatValue113;
   }

   private float measure3(float f) {
      float floatValue114 = Math.max(0.0F, Math.min(1.0F, f));
      return (float)Math.pow(floatValue114, 1.42F);
   }

   private float measure4() {
      return (float)(System.currentTimeMillis() % 1000000L) / 1000.0F;
   }

   private void invoke10(RenderManager renderManager10, ThemePalette.Swatch swatch3, float f, float g, float h, float i, float j) {
      float floatValue115 = Math.round(f * 2.0F) * 0.5F;
      float floatValue116 = Math.round(g * 2.0F) * 0.5F;
      float floatValue117 = Math.round((f + h) * 2.0F) * 0.5F - floatValue115;
      float floatValue118 = Math.round((g + i) * 2.0F) * 0.5F - floatValue116;
      float floatValue119 = Math.min(j, Math.min(floatValue117, floatValue118) * 0.5F);
      int[] intValues = swatch3.getInts();
      if (intValues != null && intValues.length >= 2) {
         float floatValue120 = floatValue117 / (intValues.length - 1);
         renderManager10.invoke20();
         renderManager10.invoke24(floatValue115, floatValue116, floatValue117, floatValue118, floatValue119, floatValue119, floatValue119, floatValue119);
         boolean flag2 = false ;

         try {
            flag2 = true;

            for (int intValue24 = 0; intValue24 < intValues.length - 1; intValue24++) {
               float floatValue121 = floatValue115 + floatValue120 * intValue24;
               float floatValue122 = intValue24 == intValues.length - 2 ? floatValue117 - floatValue120 * intValue24 : floatValue120 + 0.5F;
               renderManager10.invoke34(floatValue121, floatValue116, floatValue122, floatValue118, 0.0F, intValues[intValue24], intValues[intValue24 + 1]);
            }

            flag2 = false;
         } finally {
            if (flag2) {
               renderManager10.invoke20();
               renderManager10.invoke25();
            }
         }

         renderManager10.invoke20();
         renderManager10.invoke25();
      } else {
         renderManager10.invoke34(floatValue115, floatValue116, floatValue117, floatValue118, floatValue119, swatch3.getIntValue(), swatch3.getIntValue2());
      }
   }

   private void invoke11(
      RenderManager renderManager11, ClickGuiState clickGuiState6, ClickGuiGeometry clickGuiGeometry3, ThemeContext themeContext6, float f, float g
   ) {
      Metrics metrics9 = resolve(themeContext6);
      ColorScheme colorScheme8 = themeContext6.getColorScheme();
      if (!(clickGuiState6.getFloatValue19() <= 0.5F)) {
         float floatValue123 = metrics9.measure(7.0F);
         float floatValue124 = Math.max(metrics9.measure(3.2F), metrics9.getFloatValue17() - metrics9.measure(0.2F));
         float floatValue125 = clickGuiGeometry3.getFloatValue22() + metrics9.getFloatValue18() - floatValue123 - floatValue124 - metrics9.measure(0.8F);
         float floatValue126 = g - metrics9.measure(10.0F);
         float floatValue127 = f + metrics9.measure(5.0F);
         float floatValue128 = Math.max(metrics9.measure(28.0F), floatValue126 * (floatValue126 / (floatValue126 + clickGuiState6.getFloatValue19())));
         float floatValue129 = Math.min(1.0F, Math.max(0.0F, -clickGuiState6.measure4() / clickGuiState6.getFloatValue19()));
         float floatValue130 = floatValue127 + (floatValue126 - floatValue128) * floatValue129;
         ClickGuiRenderUtils.invoke17(
            renderManager11,
            metrics9,
            colorScheme8,
            floatValue125,
            floatValue127,
            floatValue124,
            floatValue126,
            floatValue130,
            floatValue128,
            clickGuiState6.getSpringAnimation3().getFloatValue2(),
            0.0F,
            2L,
            clickGuiState6.getFloatValue(),
            clickGuiState6.getFloatValue2(),
            clickGuiState6::invoke16
         );
      }
   }
}
