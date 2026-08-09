package ru.metaculture.protection;

public final class SidebarRenderer {
   public void invoke(RenderManager renderManager, ClickGuiState clickGuiState, ClickGuiGeometry clickGuiGeometry, ThemeContext themeContext) {
      Metrics metrics = themeContext.getMetrics();
      ColorScheme colorScheme = themeContext.getColorScheme();
      renderManager.invoke6(
         clickGuiGeometry.getFloatValue3(),
         clickGuiGeometry.getFloatValue4(),
         metrics.getFloatValue7(),
         metrics.getFloatValue9(),
         metrics.measure(16.0F),
         metrics.measure(4.0F),
         metrics.measure(4.0F),
         metrics.measure(16.0F),
         ClickGuiRenderUtils.compute11(colorScheme)
      );
      if (colorScheme.isFlag()) {
         renderManager.invoke29(
            clickGuiGeometry.getFloatValue3() + metrics.measure(0.75F),
            clickGuiGeometry.getFloatValue4() + metrics.measure(0.75F),
            metrics.getFloatValue7() - metrics.measure(1.5F),
            metrics.getFloatValue9() - metrics.measure(1.5F),
            metrics.measure(15.5F),
            metrics.measure(3.5F),
            metrics.measure(3.5F),
            metrics.measure(15.5F),
            ClickGuiRenderUtils.compute13(colorScheme, 0.82F),
            metrics.measure(0.85F)
         );
      }

      this.invoke4(renderManager, clickGuiState, clickGuiGeometry, themeContext);
      this.invoke5(renderManager, clickGuiState, clickGuiGeometry, themeContext);
      this.invoke2(renderManager, clickGuiState, clickGuiGeometry, themeContext);
      this.invoke6(renderManager, clickGuiState, clickGuiGeometry, themeContext);
   }

   public static float measure(ClickGuiGeometry clickGuiGeometry2, Metrics metrics2) {
      return Math.round(clickGuiGeometry2.getFloatValue3() + metrics2.measure(16.0F));
   }

   public static float measure2(ClickGuiGeometry clickGuiGeometry3, Metrics metrics3) {
      return Math.round(clickGuiGeometry3.getFloatValue4() + metrics3.measure(16.0F));
   }

   public static float measure3(Metrics metrics4) {
      return metrics4.measure(40.0F);
   }

   public static float measure4(Metrics metrics5) {
      return metrics5.measure(40.0F);
   }

   public static float measure5(ClickGuiGeometry clickGuiGeometry4, Metrics metrics6) {
      return Math.round(clickGuiGeometry4.getFloatValue3() + metrics6.measure(16.0F));
   }

   public static float measure6(ClickGuiGeometry clickGuiGeometry5, Metrics metrics7) {
      return Math.round(clickGuiGeometry5.getFloatValue4() + metrics7.getFloatValue9() - metrics7.measure(56.0F));
   }

   public static float measure7(ClickGuiGeometry clickGuiGeometry6, Metrics metrics8) {
      float floatValue = Math.round(clickGuiGeometry6.getFloatValue4() + metrics8.measure(89.0F));
      return Math.round(
         Math.min(floatValue + Category.values().length * metrics8.measure(56.0F), measure6(clickGuiGeometry6, metrics8) - metrics8.measure(72.0F))
      );
   }

   private void invoke2(RenderManager renderManager2, ClickGuiState clickGuiState2, ClickGuiGeometry clickGuiGeometry7, ThemeContext themeContext2) {
      Metrics metrics9 = themeContext2.getMetrics();
      ColorScheme colorScheme2 = themeContext2.getColorScheme();
      float floatValue2 = measure5(clickGuiGeometry7, metrics9);
      float floatValue3 = measure7(clickGuiGeometry7, metrics9);
      float floatValue4 = clickGuiState2.measure7(AnimationKeyRegistry.resolve5());
      float floatValue5 = clickGuiState2.measure7(AnimationKeyRegistry.resolve6());
      float floatValue6 = Math.max(floatValue4, floatValue5) * metrics9.measure(1.0F);
      float floatValue7 = ClickGuiRenderUtils.measure7(floatValue4, clickGuiState2.measure8(AnimationKeyRegistry.resolve5()));
      this.invoke3(renderManager2, clickGuiState2, colorScheme2, floatValue2, floatValue3 - floatValue6, measure4(metrics9), 5, floatValue4, floatValue5, floatValue7);
      if (!this.check(clickGuiState2) && ClickGuiRenderUtils.check(clickGuiState2, floatValue2, floatValue3, metrics9.measure(40.0F), metrics9.measure(40.0F))) {
         clickGuiState2.invoke42("tab:autobuy", "AutoBuy", floatValue2 + metrics9.measure(40.0F), floatValue3 + metrics9.measure(20.0F));
      }
   }

   private void invoke3(
      RenderManager renderManager3, ClickGuiState clickGuiState3, ColorScheme colorScheme3, float f, float g, float h, int i, float j, float k, float l
   ) {
      int intValue = colorScheme3.isFlag()
         ? ColorScheme.compute7(colorScheme3.getIntValue14(), colorScheme3.getIntValue13(), 0.45F)
         : colorScheme3.getIntValue14();
      int intValue2 = colorScheme3.isFlag()
         ? ColorScheme.compute7(colorScheme3.getIntValue15(), colorScheme3.getIntValue13(), 0.45F)
         : colorScheme3.getIntValue15();
      int intValue3 = colorScheme3.isFlag() ? ClickGuiRenderUtils.compute12(colorScheme3, 0.0F) : colorScheme3.getIntValue3();
      int intValue4 = colorScheme3.isFlag() ? ClickGuiRenderUtils.compute13(colorScheme3, 0.9F) : colorScheme3.getIntValue5();
      renderManager3.invoke20();
      SidebarNavigationShader.invoke(
         f, g, h, i, j, k, l, intValue, intValue2, colorScheme3.getIntValue11(), intValue3, intValue4, clickGuiState3.measure2(), colorScheme3.isFlag()
      );
   }

   private void invoke4(RenderManager renderManager4, ClickGuiState clickGuiState4, ClickGuiGeometry clickGuiGeometry8, ThemeContext themeContext3) {
      Metrics metrics10 = themeContext3.getMetrics();
      ColorScheme colorScheme4 = themeContext3.getColorScheme();
      float floatValue8 = measure(clickGuiGeometry8, metrics10);
      float floatValue9 = measure2(clickGuiGeometry8, metrics10);
      float floatValue10 = measure3(metrics10);
      renderManager4.invoke20();
      WildLogoShader.invoke(floatValue8, floatValue9, floatValue10, colorScheme4.getIntValue14(), colorScheme4.getIntValue15(), clickGuiState4.measure2(), colorScheme4.isFlag());
      float floatValue11 = 0.5F + 0.5F * (float)Math.sin((float)System.currentTimeMillis() * 0.00108F);
      float floatValue12 = 19.5F;
      float floatValue13 = floatValue12 * (1.08F + floatValue11 * 0.035F);
      float floatValue14 = ClickGuiRenderUtils.measure(BrandMark.font(), BrandMark.GLYPH, floatValue12);
      float floatValue15 = ClickGuiRenderUtils.measure(BrandMark.font(), BrandMark.GLYPH, floatValue13);
      float floatValue16 = floatValue8 + floatValue10 * 0.5F;
      float floatValue17 = floatValue9 + floatValue10 * 0.5F;
      float floatValue18 = ClickGuiRenderUtils.measure3(metrics10, BrandMark.font(), floatValue12);
      float floatValue19 = ClickGuiRenderUtils.measure3(metrics10, BrandMark.font(), floatValue13);
      float floatValue20 = floatValue16 - floatValue14 * 0.5F;
      float floatValue21 = floatValue16 - floatValue15 * 0.5F;
      float floatValue22 = floatValue17 - floatValue18 * 0.5F - metrics10.measure(1.0F);
      float floatValue23 = floatValue17 - floatValue19 * 0.5F - metrics10.measure(1.0F);
      if (!colorScheme4.isFlag()) {
         renderManager4.invoke21();

         try {
            ClickGuiRenderUtils.invoke3(
               renderManager4,
               metrics10,
               BrandMark.font(),
               floatValue21,
               floatValue23,
               floatValue13,
               BrandMark.GLYPH,
               ColorScheme.compute7(ColorScheme.compute6(colorScheme4.getIntValue15(), 120), ColorScheme.compute6(colorScheme4.getIntValue14(), 135), floatValue11)
            );
         } finally {
            renderManager4.invoke22();
         }
      }

      ClickGuiRenderUtils.invoke3(
         renderManager4, metrics10, BrandMark.font(), floatValue20, floatValue22, floatValue12, BrandMark.GLYPH, ColorScheme.compute6(ClickGuiRenderUtils.compute7(colorScheme4), 246)
      );
      renderManager4.invoke5(
         floatValue8 + metrics10.measure(4.0F), floatValue9 + metrics10.measure(56.0F), metrics10.measure(32.0F), metrics10.measure(1.0F), metrics10.measure(1.0F), colorScheme4.getIntValue8()
      );
      if (!this.check(clickGuiState4) && ClickGuiRenderUtils.check(clickGuiState4, floatValue8, floatValue9, floatValue10, floatValue10)) {
         clickGuiState4.invoke42("logo:themes", "Themes", floatValue8 + floatValue10 + metrics10.measure(6.0F), floatValue9 + floatValue10 * 0.5F);
      }
   }

   private void invoke5(RenderManager renderManager5, ClickGuiState clickGuiState5, ClickGuiGeometry clickGuiGeometry9, ThemeContext themeContext4) {
      Metrics metrics11 = themeContext4.getMetrics();
      ColorScheme colorScheme5 = themeContext4.getColorScheme();
      float floatValue24 = measure5(clickGuiGeometry9, metrics11);
      float floatValue25 = clickGuiGeometry9.getFloatValue4() + metrics11.measure(89.0F);
      Category[] categories = Category.values();

      for (int intValue5 = 0; intValue5 < categories.length; intValue5++) {
         Category category = categories[intValue5];
         float floatValue26 = floatValue25 + intValue5 * metrics11.measure(56.0F);
         float floatValue27 = clickGuiState5.measure7(AnimationKeyRegistry.resolve11(category));
         float floatValue28 = clickGuiState5.measure7(AnimationKeyRegistry.resolve12(category));
         float floatValue29 = Math.max(floatValue27, floatValue28) * metrics11.measure(1.0F);
         float floatValue30 = ClickGuiRenderUtils.measure7(floatValue27, clickGuiState5.measure8(AnimationKeyRegistry.resolve11(category)));
         this.invoke3(renderManager5, clickGuiState5, colorScheme5, floatValue24, floatValue26 - floatValue29, measure4(metrics11), intValue5, floatValue27, floatValue28, floatValue30);
         if (!this.check(clickGuiState5) && ClickGuiRenderUtils.check(clickGuiState5, floatValue24, floatValue26, metrics11.measure(40.0F), metrics11.measure(40.0F))) {
            clickGuiState5.invoke42("cat:" + category.name(), category.getDisplayName(), floatValue24 + metrics11.measure(40.0F), floatValue26 + metrics11.measure(20.0F));
         }
      }
   }

   private void invoke6(RenderManager renderManager6, ClickGuiState clickGuiState6, ClickGuiGeometry clickGuiGeometry10, ThemeContext themeContext5) {
      Metrics metrics12 = themeContext5.getMetrics();
      ColorScheme colorScheme6 = themeContext5.getColorScheme();
      float floatValue31 = measure5(clickGuiGeometry10, metrics12);
      float floatValue32 = measure6(clickGuiGeometry10, metrics12);
      float floatValue33 = clickGuiState6.measure7(AnimationKeyRegistry.resolve2());
      float floatValue34 = clickGuiState6.isFlag21() ? 1.0F : 0.0F;
      float floatValue35 = Math.max(floatValue33, floatValue34);
      float floatValue36 = measure4(metrics12);
      float floatValue37 = floatValue31 + floatValue36 * 0.5F;
      float floatValue38 = floatValue32 + floatValue36 * 0.5F;
      float floatValue39 = ClickGuiRenderUtils.measure7(floatValue33, clickGuiState6.measure8(AnimationKeyRegistry.resolve2()));
      renderManager6.invoke62(floatValue39, floatValue37, floatValue38);

      try {
         if (floatValue35 > 0.01F) {
            int intValue6 = colorScheme6.isFlag()
               ? ColorScheme.compute5(0, 0, 0, Math.round(24.0F * floatValue35))
               : ColorScheme.compute6(colorScheme6.getIntValue15(), Math.round(38.0F * floatValue35));
            renderManager6.invoke41(
               floatValue31 - metrics12.measure(1.5F),
               floatValue32 - metrics12.measure(1.5F),
               floatValue36 + metrics12.measure(3.0F),
               floatValue36 + metrics12.measure(3.0F),
               metrics12.measure(21.5F),
               metrics12.measure(14.0F) * floatValue35,
               metrics12.measure(colorScheme6.isFlag() ? 2.6F : 2.0F),
               intValue6
            );
         }

         renderManager6.invoke39(
            floatValue37,
            floatValue38,
            metrics12.measure(20.0F),
            0.0F,
            1.0F,
            ColorScheme.compute7(ColorScheme.compute6(colorScheme6.getIntValue11(), 118), ColorScheme.compute6(colorScheme6.getIntValue14(), 196), floatValue35)
         );
         renderManager6.invoke39(
            floatValue37,
            floatValue38,
            metrics12.measure(18.25F),
            0.0F,
            1.0F,
            colorScheme6.isFlag() ? ColorScheme.compute5(255, 255, 255, 218) : ColorScheme.compute5(20, 15, 24, 238)
         );
         renderManager6.invoke39(
            floatValue37,
            floatValue38,
            metrics12.measure(15.8F),
            0.0F,
            1.0F,
            colorScheme6.isFlag()
               ? ClickGuiRenderUtils.compute12(colorScheme6, floatValue35)
               : ColorScheme.compute7(colorScheme6.getIntValue3(), ColorScheme.compute6(colorScheme6.getIntValue15(), 28), floatValue35)
         );
         NotificationBellRenderer.invoke(
            renderManager6,
            metrics12,
            floatValue37,
            floatValue38 + metrics12.measure(0.4F),
            metrics12.measure(0.88F),
            ColorScheme.compute7(ClickGuiRenderUtils.compute4(colorScheme6), ClickGuiRenderUtils.compute2(colorScheme6), floatValue35 * 0.72F),
            ColorScheme.compute6(colorScheme6.getIntValue14(), Math.round(18.0F + 46.0F * floatValue35))
         );
      } finally {
         renderManager6.invoke64();
      }

      if (!this.check(clickGuiState6) && ClickGuiRenderUtils.check(clickGuiState6, floatValue31, floatValue32, floatValue36, floatValue36)) {
         clickGuiState6.invoke42("avatar", "Profile", floatValue31 + floatValue36 + metrics12.measure(6.0F), floatValue32 + floatValue36 * 0.5F);
      }
   }

   private boolean check(ClickGuiState clickGuiState7) {
      return clickGuiState7.isFlag21() || clickGuiState7.isFlag2();
   }
}
