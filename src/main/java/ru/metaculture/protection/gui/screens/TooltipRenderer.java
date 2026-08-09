package ru.metaculture.protection;

public final class TooltipRenderer {
   public void invoke(RenderManager renderManager, ClickGuiState clickGuiState, ThemeContext themeContext, int i, int j) {
      float floatValue = clickGuiState.measure7(AnimationKeyRegistry.resolve43());
      if (!(floatValue < 0.01F) && clickGuiState.getText6() != null && !clickGuiState.getText6().isEmpty()) {
         Metrics metrics = themeContext.getMetrics();
         ColorScheme colorScheme = themeContext.getColorScheme();
         String text = clickGuiState.getText6();
         float floatValue2 = ClickGuiRenderUtils.measure(FontRegistry.fontObject, text, 9.0F);
         float floatValue3 = metrics.measure(8.0F);
         float floatValue4 = metrics.measure(4.0F);
         float floatValue5 = floatValue2 + floatValue3 * 2.0F;
         float floatValue6 = metrics.measure(18.0F);
         float floatValue7 = metrics.measure(5.0F);
         float floatValue8 = clickGuiState.getFloatValue78() + metrics.measure(12.0F);
         float floatValue9 = clickGuiState.getFloatValue79() - floatValue6 - metrics.measure(4.0F);
         if (floatValue8 + floatValue5 > i - metrics.measure(4.0F)) {
            floatValue8 = i - floatValue5 - metrics.measure(4.0F);
         }

         if (floatValue8 < metrics.measure(4.0F)) {
            floatValue8 = metrics.measure(4.0F);
         }

         if (floatValue9 < metrics.measure(4.0F)) {
            floatValue9 = clickGuiState.getFloatValue79() + metrics.measure(16.0F);
         }

         renderManager.invoke65(floatValue);

         try {
            renderManager.invoke41(
               floatValue8,
               floatValue9,
               floatValue5,
               floatValue6,
               floatValue7,
               metrics.measure(colorScheme.isFlag() ? 10.0F : 6.0F),
               metrics.measure(colorScheme.isFlag() ? 2.0F : 1.0F),
               ColorScheme.compute5(0, 0, 0, colorScheme.isFlag() ? 34 : 40)
            );
            renderManager.invoke5(
               floatValue8, floatValue9, floatValue5, floatValue6, floatValue7, colorScheme.isFlag() ? ColorScheme.compute5(255, 255, 255, 232) : ColorScheme.compute5(18, 19, 23, 240)
            );
            renderManager.invoke28(floatValue8, floatValue9, floatValue5, floatValue6, floatValue7, colorScheme.getIntValue7(), 0.5F);
            ClickGuiRenderUtils.invoke4(renderManager, metrics, FontRegistry.fontObject, floatValue8 + floatValue3, floatValue9, floatValue6, 9.0F, text, colorScheme.getIntValue13());
         } finally {
            renderManager.invoke66();
         }
      }
   }
}
