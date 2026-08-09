package ru.metaculture.protection;

public final class SearchBarRenderer {
   private static final String FOUNDRY = "Foundry";
   private static final String STUDIO = "Studio";
   private static final String A = "a";
   private static final float FLOAT_VALUE = 92.0F;
   private static final float FLOAT_VALUE_2 = 86.0F;
   private final AnimatedTextRenderer animatedTextRenderer = new AnimatedTextRenderer();

   public void invoke(RenderManager renderManager, ClickGuiState clickGuiState, ClickGuiGeometry clickGuiGeometry, ThemeContext themeContext) {
      Metrics metrics = themeContext.getMetrics();
      ColorScheme colorScheme = themeContext.getColorScheme();
      float floatValue = clickGuiState.measure7(AnimationKeyRegistry.resolve());
      renderManager.invoke5(
         clickGuiGeometry.getFloatValue7(),
         clickGuiGeometry.getFloatValue8(),
         clickGuiGeometry.getFloatValue9(),
         metrics.getFloatValue10(),
         metrics.measure(4.0F),
         ClickGuiRenderUtils.compute11(colorScheme)
      );
      renderManager.invoke6(
         clickGuiGeometry.getFloatValue10(),
         clickGuiGeometry.getFloatValue8(),
         metrics.getFloatValue11(),
         metrics.getFloatValue10(),
         metrics.measure(4.0F),
         metrics.measure(16.0F),
         metrics.measure(4.0F),
         metrics.measure(4.0F),
         ClickGuiRenderUtils.compute11(colorScheme)
      );
      if (colorScheme.isFlag()) {
         renderManager.invoke28(
            clickGuiGeometry.getFloatValue7() + metrics.measure(0.75F),
            clickGuiGeometry.getFloatValue8() + metrics.measure(0.75F),
            clickGuiGeometry.getFloatValue9() - metrics.measure(1.5F),
            metrics.getFloatValue10() - metrics.measure(1.5F),
            metrics.measure(3.5F),
            ClickGuiRenderUtils.compute13(colorScheme, 0.78F),
            metrics.measure(0.85F)
         );
         renderManager.invoke29(
            clickGuiGeometry.getFloatValue10() + metrics.measure(0.75F),
            clickGuiGeometry.getFloatValue8() + metrics.measure(0.75F),
            metrics.getFloatValue11() - metrics.measure(1.5F),
            metrics.getFloatValue10() - metrics.measure(1.5F),
            metrics.measure(3.5F),
            metrics.measure(15.5F),
            metrics.measure(3.5F),
            metrics.measure(3.5F),
            ClickGuiRenderUtils.compute13(colorScheme, 0.78F),
            metrics.measure(0.85F)
         );
      }

      renderManager.invoke6(
         clickGuiGeometry.getFloatValue10(),
         clickGuiGeometry.getFloatValue8(),
         metrics.getFloatValue11(),
         metrics.getFloatValue10(),
         metrics.measure(4.0F),
         metrics.measure(16.0F),
         metrics.measure(4.0F),
         metrics.measure(4.0F),
         ColorScheme.compute7(colorScheme.getIntValue4(), colorScheme.getIntValue6(), floatValue)
      );
      if (floatValue > 0.01F) {
         renderManager.invoke29(
            clickGuiGeometry.getFloatValue10() - metrics.measure(0.5F),
            clickGuiGeometry.getFloatValue8() - metrics.measure(0.5F),
            metrics.getFloatValue11() + metrics.measure(1.0F),
            metrics.getFloatValue10() + metrics.measure(1.0F),
            metrics.measure(4.5F),
            metrics.measure(16.5F),
            metrics.measure(4.5F),
            metrics.measure(4.5F),
            ColorScheme.compute6(colorScheme.getIntValue14(), Math.round(50.0F * floatValue)),
            1.0F
         );
      }

      this.invoke2(renderManager, clickGuiState, clickGuiGeometry, themeContext);
      this.invoke5(renderManager, clickGuiState, clickGuiGeometry, themeContext);
      this.invoke4(renderManager, clickGuiState, clickGuiGeometry, themeContext);
      this.invoke3(renderManager, clickGuiGeometry, themeContext);
      this.invoke6(renderManager, clickGuiState, clickGuiGeometry, themeContext, floatValue);
   }

   public static float measure(Metrics metrics2) {
      return measure2(metrics2);
   }

   public static float measure2(Metrics metrics3) {
      return measure6(metrics3);
   }

   public static float measure3(ClickGuiGeometry clickGuiGeometry2, Metrics metrics4) {
      float floatValue2 = measure7(clickGuiGeometry2, metrics4) - metrics4.measure(8.0F) - measure(metrics4);
      return Math.round(Math.max(clickGuiGeometry2.getFloatValue7() + clickGuiGeometry2.getFloatValue9() * 0.3F, floatValue2));
   }

   public static float measure4(ClickGuiGeometry clickGuiGeometry3, Metrics metrics5) {
      return measure9(clickGuiGeometry3, metrics5);
   }

   public static float measure5(Metrics metrics6) {
      return Math.round(metrics6.measure(92.0F));
   }

   public static float measure6(Metrics metrics7) {
      return Math.round(Math.max(metrics7.measure(24.0F), metrics7.getFloatValue10() - metrics7.measure(12.0F)));
   }

   public static float measure7(ClickGuiGeometry clickGuiGeometry4, Metrics metrics8) {
      float floatValue3 = clickGuiGeometry4.getFloatValue7()
         + clickGuiGeometry4.getFloatValue9()
         - metrics8.measure(16.0F)
         - measure8(metrics8)
         - metrics8.measure(10.0F)
         - measure5(metrics8);
      return Math.round(Math.max(clickGuiGeometry4.getFloatValue7() + clickGuiGeometry4.getFloatValue9() * 0.48F, floatValue3));
   }

   private static float measure8(Metrics metrics9) {
      float floatValue4 = ClickGuiRenderUtils.measure(FontRegistry.fontObject4, resolve(), 12.0F);
      float floatValue5 = ClickGuiRenderUtils.measure(FontRegistry.fontObject5, "g", 12.0F);
      return Math.max(metrics9.measure(86.0F), floatValue4 + metrics9.measure(8.0F) + floatValue5);
   }

   public static float measure9(ClickGuiGeometry clickGuiGeometry5, Metrics metrics10) {
      return Math.round(clickGuiGeometry5.getFloatValue8() + (metrics10.getFloatValue10() - measure6(metrics10)) * 0.5F);
   }

   private void invoke2(RenderManager renderManager2, ClickGuiState clickGuiState2, ClickGuiGeometry clickGuiGeometry6, ThemeContext themeContext2) {
      Metrics metrics11 = themeContext2.getMetrics();
      ColorScheme colorScheme2 = themeContext2.getColorScheme();
      float floatValue6 = clickGuiGeometry6.getFloatValue8();
      float floatValue7 = metrics11.measure(6.0F);
      float floatValue8 = 12.0F;
      float floatValue9 = metrics11.measure(10.0F);
      float floatValue10 = ClickGuiRenderUtils.measure(FontRegistry.fontObject, "t.me/soezproject", floatValue8);
      float floatValue11 = ClickGuiRenderUtils.measure(FontRegistry.fontObject8, "k", 8.0F);
      float floatValue12 = metrics11.measure(12.0F);
      String text = clickGuiState2.isFlag21() ? "P" : clickGuiState2.getCategory().getIconGlyph();
      String text2 = clickGuiState2.isFlag21()
         ? "Профиль"
         : (
            clickGuiState2.isFlag2()
               ? "Диагностика"
               : (clickGuiState2.isFlag() ? "AutoBuy" : (clickGuiState2.isFlag3() ? "Studio" : clickGuiState2.getCategory().getDisplayName()))
         );
      float floatValue13 = clickGuiGeometry6.getFloatValue7() + metrics11.measure(16.0F);
      this.invoke9(renderManager2, metrics11, floatValue13, floatValue6, metrics11.getFloatValue10(), floatValue9, colorScheme2);
      floatValue13 += floatValue9 + floatValue7;
      ClickGuiRenderUtils.invoke4(
         renderManager2, metrics11, FontRegistry.fontObject, floatValue13, floatValue6, metrics11.getFloatValue10(), floatValue8, "t.me/soezproject", colorScheme2.getIntValue12()
      );
      floatValue13 += floatValue10 + floatValue7;
      ClickGuiRenderUtils.invoke4(
         renderManager2, metrics11, FontRegistry.fontObject8, floatValue13 + metrics11.measure(1.0F), floatValue6, metrics11.getFloatValue10(), 8.0F, "k", colorScheme2.getIntValue11()
      );
      floatValue13 += floatValue11 + floatValue7;
      if (clickGuiState2.isFlag2()) {
         this.invoke8(renderManager2, metrics11, floatValue13 + floatValue12 * 0.5F, floatValue6 + metrics11.getFloatValue10() * 0.5F, colorScheme2.compute4());
      } else if (clickGuiState2.isFlag()) {
         this.invoke11(renderManager2, metrics11, floatValue13 + floatValue12 * 0.5F, floatValue6 + metrics11.getFloatValue10() * 0.5F, metrics11.measure(0.8F), colorScheme2.compute4());
      } else if (clickGuiState2.isFlag3()) {
         this.invoke10(
            renderManager2,
            metrics11,
            FontRegistry.fontObject8,
            "a",
            floatValue13 + metrics11.measure(1.5F),
            floatValue6,
            metrics11.getFloatValue10(),
            floatValue8,
            floatValue12,
            colorScheme2.compute4(),
            0.0F
         );
      } else if (clickGuiState2.isFlag21()) {
         this.invoke10(renderManager2, metrics11, FontRegistry.fontObject3, text, floatValue13, floatValue6, metrics11.getFloatValue10(), floatValue8, floatValue12, colorScheme2.compute4(), 0.0F);
      } else {
         this.invoke10(renderManager2, metrics11, FontRegistry.fontObject8, text, floatValue13, floatValue6, metrics11.getFloatValue10(), floatValue8, floatValue12, colorScheme2.compute4(), 0.0F);
      }

      floatValue13 += floatValue12 + floatValue7;
      ClickGuiRenderUtils.invoke4(renderManager2, metrics11, FontRegistry.fontObject4, floatValue13, floatValue6, metrics11.getFloatValue10(), floatValue8, text2, colorScheme2.getIntValue13());
   }

   private void invoke3(RenderManager renderManager3, ClickGuiGeometry clickGuiGeometry7, ThemeContext themeContext3) {
      Metrics metrics12 = themeContext3.getMetrics();
      ColorScheme colorScheme3 = themeContext3.getColorScheme();
      String text3 = resolve();
      float floatValue14 = ClickGuiRenderUtils.measure(FontRegistry.fontObject4, text3, 12.0F);
      String text4 = "g";
      float floatValue15 = ClickGuiRenderUtils.measure(FontRegistry.fontObject5, text4, 12.0F);
      float floatValue16 = clickGuiGeometry7.getFloatValue7() + clickGuiGeometry7.getFloatValue9() - metrics12.measure(16.0F) - floatValue15;
      float floatValue17 = floatValue16 - metrics12.measure(8.0F) - floatValue14;
      float floatValue18 = clickGuiGeometry7.getFloatValue8();
      ClickGuiRenderUtils.invoke4(renderManager3, metrics12, FontRegistry.fontObject4, floatValue17, floatValue18, metrics12.getFloatValue10(), 12.0F, text3, colorScheme3.getIntValue13());
      ClickGuiRenderUtils.invoke4(renderManager3, metrics12, FontRegistry.fontObject5, floatValue16, floatValue18, metrics12.getFloatValue10(), 12.0F, text4, colorScheme3.compute4());
   }

   private void invoke4(RenderManager renderManager4, ClickGuiState clickGuiState3, ClickGuiGeometry clickGuiGeometry8, ThemeContext themeContext4) {
      Metrics metrics13 = themeContext4.getMetrics();
      ColorScheme colorScheme4 = themeContext4.getColorScheme();
      float floatValue19 = measure7(clickGuiGeometry8, metrics13);
      float floatValue20 = measure9(clickGuiGeometry8, metrics13);
      float floatValue21 = measure5(metrics13);
      float floatValue22 = measure6(metrics13);
      float floatValue23 = clickGuiState3.measure7(AnimationKeyRegistry.resolve57());
      float floatValue24 = clickGuiState3.isFlag24() ? 1.0F : 0.0F;
      float floatValue25 = Math.max(floatValue23, floatValue24);
      int intValue = colorScheme4.isFlag()
         ? ColorScheme.compute7(ClickGuiRenderUtils.compute12(colorScheme4, 0.18F), ColorScheme.compute6(colorScheme4.getIntValue14(), 42), floatValue25 * 0.36F)
         : ColorScheme.compute7(colorScheme4.getIntValue4(), ColorScheme.compute6(colorScheme4.getIntValue15(), 62), 0.16F + floatValue25 * 0.18F);
      renderManager4.invoke5(floatValue19, floatValue20, floatValue21, floatValue22, metrics13.measure(6.0F), intValue);
      renderManager4.invoke28(
         floatValue19,
         floatValue20,
         floatValue21,
         floatValue22,
         metrics13.measure(6.0F),
         ColorScheme.compute7(
            colorScheme4.isFlag() ? ClickGuiRenderUtils.compute13(colorScheme4, 0.76F) : colorScheme4.getIntValue6(), ColorScheme.compute6(colorScheme4.getIntValue14(), 120), floatValue25
         ),
         Math.max(0.55F, metrics13.measure(0.6F))
      );
      float floatValue26 = 9.5F;
      float floatValue27 = metrics13.measure(15.0F);
      float floatValue28 = ClickGuiRenderUtils.measure(FontRegistry.fontObject4, "Foundry", floatValue26);
      float floatValue29 = metrics13.measure(7.0F);
      float floatValue30 = floatValue27 + floatValue29 + floatValue28;
      float floatValue31 = Math.round(floatValue19 + (floatValue21 - floatValue30) * 0.5F);
      float floatValue32 = floatValue31 + floatValue27 * 0.5F;
      float floatValue33 = floatValue20 + floatValue22 * 0.5F;
      float floatValue34 = Math.round(floatValue31 + floatValue27 + floatValue29);
      this.invoke7(renderManager4, metrics13, floatValue32, floatValue33, ColorScheme.compute7(ClickGuiRenderUtils.compute4(colorScheme4), colorScheme4.getIntValue14(), 0.45F + floatValue25 * 0.35F));
      ClickGuiRenderUtils.invoke4(
         renderManager4,
         metrics13,
         FontRegistry.fontObject4,
         floatValue34,
         floatValue20,
         floatValue22,
         floatValue26,
         "Foundry",
         ColorScheme.compute7(ClickGuiRenderUtils.compute4(colorScheme4), ClickGuiRenderUtils.compute2(colorScheme4), 0.72F + floatValue25 * 0.28F)
      );
      if (ClickGuiRenderUtils.check(clickGuiState3, floatValue19, floatValue20, floatValue21, floatValue22)) {
         clickGuiState3.invoke42("header:foundry", "Foundry", floatValue19 + floatValue21 * 0.5F, floatValue20 + floatValue22 + metrics13.measure(8.0F));
      }
   }

   private void invoke5(RenderManager renderManager5, ClickGuiState clickGuiState4, ClickGuiGeometry clickGuiGeometry9, ThemeContext themeContext5) {
      if (StudioAccessControl.check()) {
         Metrics metrics14 = themeContext5.getMetrics();
         ColorScheme colorScheme5 = themeContext5.getColorScheme();
         float floatValue35 = measure3(clickGuiGeometry9, metrics14);
         float floatValue36 = measure4(clickGuiGeometry9, metrics14);
         float floatValue37 = measure(metrics14);
         float floatValue38 = measure2(metrics14);
         float floatValue39 = clickGuiState4.measure7(AnimationKeyRegistry.resolve59());
         float floatValue40 = clickGuiState4.isFlag3() ? 1.0F : 0.0F;
         float floatValue41 = Math.max(floatValue39, floatValue40);
         int intValue2 = colorScheme5.isFlag()
            ? ColorScheme.compute7(ClickGuiRenderUtils.compute12(colorScheme5, 0.18F), ColorScheme.compute6(colorScheme5.getIntValue14(), 42), floatValue41 * 0.36F)
            : ColorScheme.compute7(colorScheme5.getIntValue4(), ColorScheme.compute6(colorScheme5.getIntValue15(), 62), 0.16F + floatValue41 * 0.18F);
         renderManager5.invoke5(floatValue35, floatValue36, floatValue37, floatValue38, metrics14.measure(6.0F), intValue2);
         renderManager5.invoke28(
            floatValue35,
            floatValue36,
            floatValue37,
            floatValue38,
            metrics14.measure(6.0F),
            ColorScheme.compute7(
               colorScheme5.isFlag() ? ClickGuiRenderUtils.compute13(colorScheme5, 0.76F) : colorScheme5.getIntValue6(), ColorScheme.compute6(colorScheme5.getIntValue14(), 120), floatValue41
            ),
            Math.max(0.55F, metrics14.measure(0.6F))
         );
         this.invoke10(
            renderManager5,
            metrics14,
            FontRegistry.fontObject8,
            "a",
            floatValue35 + metrics14.measure(2.0F),
            floatValue36,
            floatValue38,
            13.0F,
            floatValue37,
            ColorScheme.compute7(ClickGuiRenderUtils.compute4(colorScheme5), colorScheme5.getIntValue14(), 0.5F + floatValue41 * 0.35F),
            0.0F
         );
         if (ClickGuiRenderUtils.check(clickGuiState4, floatValue35, floatValue36, floatValue37, floatValue38)) {
            clickGuiState4.invoke42("header:studio", "Studio", floatValue35 + floatValue37 * 0.5F, floatValue36 + floatValue38 + metrics14.measure(8.0F));
         }
      }
   }

   private void invoke6(RenderManager renderManager6, ClickGuiState clickGuiState5, ClickGuiGeometry clickGuiGeometry10, ThemeContext themeContext6, float f) {
      Metrics metrics15 = themeContext6.getMetrics();
      ColorScheme colorScheme6 = themeContext6.getColorScheme();
      float floatValue42 = clickGuiGeometry10.getFloatValue8();
      String text5 = clickGuiState5.getText();
      float floatValue43 = clickGuiState5.measure7(AnimationKeyRegistry.resolve29());
      float floatValue44 = (float)((Math.sin(System.currentTimeMillis() * 0.006) + 1.0) * 0.5);
      boolean flag = clickGuiState5.isFlag5();
      int intValue3 = ColorScheme.compute7(colorScheme6.getIntValue12(), colorScheme6.getIntValue13(), f);
      float floatValue45 = metrics15.measure(16.0F);
      float floatValue46 = metrics15.measure(27.0F);
      float floatValue47 = metrics15.getFloatValue11() - floatValue45 - floatValue46;
      float floatValue48 = 0.0F;
      boolean flag2 = text5.isEmpty();
      if (!flag2) {
         float floatValue49 = ClickGuiRenderUtils.measure(FontRegistry.fontObject, text5, 12.0F);
         if (floatValue49 > floatValue47) {
            floatValue48 = floatValue47 - floatValue49;
         }
      }

      renderManager6.invoke23(
         (int)clickGuiGeometry10.getFloatValue10(), (int)clickGuiGeometry10.getFloatValue8(), (int)(metrics15.getFloatValue11() - floatValue46), (int)metrics15.getFloatValue10()
      );
      int intValue4 = ColorScheme.compute6(colorScheme6.compute4(), Math.round(255.0F * floatValue44));
      this.animatedTextRenderer
         .invoke(
            renderManager6,
            metrics15,
            FontRegistry.fontObject,
            text5,
            clickGuiGeometry10.getFloatValue10() + floatValue45 + floatValue48,
            floatValue42,
            metrics15.getFloatValue10(),
            12.0F,
            intValue3,
            flag,
            intValue4,
            System.currentTimeMillis()
         );
      if (flag2 && !flag && !this.animatedTextRenderer.check()) {
         ClickGuiRenderUtils.invoke4(
            renderManager6, metrics15, FontRegistry.fontObject, clickGuiGeometry10.getFloatValue10() + floatValue45, floatValue42, metrics15.getFloatValue10(), 12.0F, "Search...", intValue3
         );
      }

      renderManager6.invoke25();
      float floatValue50 = Math.max(f * 0.3F, floatValue43);
      ClickGuiRenderUtils.invoke4(
         renderManager6,
         metrics15,
         FontRegistry.fontObject5,
         clickGuiGeometry10.getFloatValue10() + metrics15.getFloatValue11() - metrics15.measure(27.0F),
         floatValue42,
         metrics15.getFloatValue10(),
         12.0F,
         "l",
         ColorScheme.compute6(colorScheme6.compute4(), Math.round(255.0F * floatValue50))
      );
   }

   private void invoke7(RenderManager renderManager7, Metrics metrics16, float f, float g, int i) {
      float floatValue51 = metrics16.measure(0.72F);
      float floatValue52 = 3.1F * floatValue51;
      float floatValue53 = Math.max(1.0F, metrics16.measure(0.85F));
      float floatValue54 = f - 6.2F * floatValue51;
      float floatValue55 = f + 5.0F * floatValue51;
      float floatValue56 = g - 5.4F * floatValue51;
      float floatValue57 = g + 5.2F * floatValue51;
      int intValue5 = ColorScheme.compute6(i, 138);
      renderManager7.invoke5(floatValue54 + floatValue52 * 0.5F, floatValue56 + floatValue52 * 0.5F, floatValue55 - floatValue54, floatValue53, floatValue53, intValue5);
      renderManager7.invoke5(floatValue54 + floatValue52 * 0.5F, floatValue57 + floatValue52 * 0.5F, floatValue55 - floatValue54, floatValue53, floatValue53, intValue5);
      renderManager7.invoke5(floatValue54 + floatValue52 * 0.5F, floatValue56 + floatValue52 * 0.5F, floatValue53, floatValue57 - floatValue56, floatValue53, intValue5);
      renderManager7.invoke5(floatValue55 + floatValue52 * 0.5F - floatValue53, floatValue56 + floatValue52 * 0.5F, floatValue53, floatValue57 - floatValue56, floatValue53, intValue5);
      renderManager7.invoke39(floatValue54 + floatValue52 * 0.5F, floatValue56 + floatValue52 * 0.5F, floatValue52, 0.0F, 1.0F, i);
      renderManager7.invoke39(floatValue55 + floatValue52 * 0.5F, floatValue56 + floatValue52 * 0.5F, floatValue52, 0.0F, 1.0F, i);
      renderManager7.invoke39(floatValue54 + floatValue52 * 0.5F, floatValue57 + floatValue52 * 0.5F, floatValue52, 0.0F, 1.0F, i);
      renderManager7.invoke39(floatValue55 + floatValue52 * 0.5F, floatValue57 + floatValue52 * 0.5F, floatValue52, 0.0F, 1.0F, i);
   }

   private void invoke8(RenderManager renderManager8, Metrics metrics17, float f, float g, int i) {
      float floatValue58 = metrics17.measure(1.0F);
      float floatValue59 = Math.round(f - 5.5F * floatValue58);
      float floatValue60 = Math.round(g - 5.5F * floatValue58);
      renderManager8.invoke28(floatValue59, floatValue60, 11.0F * floatValue58, 11.0F * floatValue58, 3.2F * floatValue58, i, Math.max(0.6F, 0.75F * floatValue58));
      renderManager8.invoke5(floatValue59 + 2.6F * floatValue58, floatValue60 + 7.0F * floatValue58, 1.3F * floatValue58, 2.0F * floatValue58, 0.65F * floatValue58, ColorScheme.compute6(i, 180));
      renderManager8.invoke5(floatValue59 + 4.9F * floatValue58, floatValue60 + 5.2F * floatValue58, 1.3F * floatValue58, 3.8F * floatValue58, 0.65F * floatValue58, i);
      renderManager8.invoke5(floatValue59 + 7.2F * floatValue58, floatValue60 + 3.0F * floatValue58, 1.3F * floatValue58, 6.0F * floatValue58, 0.65F * floatValue58, ColorScheme.compute6(i, 220));
   }

   private static String resolve() {
      MenuModule menuModule = MenuModule.getInstance();
      int intValue6 = menuModule != null && menuModule.bindKey != -1 ? menuModule.bindKey : 344;
      return KeyboardKey.resolve(intValue6);
   }

   private void invoke9(RenderManager renderManager9, Metrics metrics18, float f, float g, float h, float i, ColorScheme colorScheme7) {
      float floatValue61 = 9.5F;
      int intValue7 = ColorScheme.compute7(colorScheme7.getIntValue12(), colorScheme7.getIntValue14(), 0.34F);
      this.invoke10(renderManager9, metrics18, BrandMark.font(), BrandMark.GLYPH, f, g, h, floatValue61, i, ColorScheme.compute6(intValue7, 220), -0.45F);
   }

   private void invoke10(
      RenderManager renderManager10,
      Metrics metrics19,
      FontObject fontObject,
      String string,
      float f,
      float g,
      float h,
      float i,
      float j,
      int k,
      float l
   ) {
      float floatValue62 = ClickGuiRenderUtils.measure(fontObject, string, i);
      float floatValue63 = f + (j - floatValue62) * 0.5F;
      float floatValue64 = ClickGuiRenderUtils.measure4(metrics19, fontObject, g, h, i) + metrics19.measure(l);
      ClickGuiRenderUtils.invoke3(renderManager10, metrics19, fontObject, floatValue63, floatValue64, i, string, k);
   }

   private void invoke11(RenderManager renderManager11, Metrics metrics20, float f, float g, float h, int i) {
      float floatValue65 = metrics20.measure(0.75F);
      renderManager11.invoke5(f - 7.0F * floatValue65, g - 6.0F * floatValue65, 2.5F * floatValue65, h * 1.5F, h, i);
      renderManager11.invoke5(f - 4.8F * floatValue65, g - 4.6F * floatValue65, 11.0F * floatValue65, h * 1.5F, h, i);
      renderManager11.invoke5(f - 3.6F * floatValue65, g - 3.1F * floatValue65, 8.5F * floatValue65, 6.0F * floatValue65, metrics20.measure(1.5F), ColorScheme.compute6(i, 128));
      renderManager11.invoke5(f - 2.8F * floatValue65, g + 3.0F * floatValue65, 9.2F * floatValue65, h * 1.4F, h, i);
      renderManager11.invoke39(f - 2.2F * floatValue65, g + 6.3F * floatValue65, 1.7F * floatValue65, 0.0F, 1.0F, i);
      renderManager11.invoke39(f + 5.0F * floatValue65, g + 6.3F * floatValue65, 1.7F * floatValue65, 0.0F, 1.0F, i);
   }
}
