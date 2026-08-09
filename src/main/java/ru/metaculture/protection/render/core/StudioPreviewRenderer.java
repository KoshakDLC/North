package ru.metaculture.protection;

public final class StudioPreviewRenderer {
   private static final int INT_VALUE = -15921388;
   private static final int INT_VALUE_2 = -15197404;
   private static final int INT_VALUE_3 = 12;
   private static final int INT_VALUE_4 = -15657957;
   private static final int INT_VALUE_5 = -14670802;
   private static final StudioModelRenderer STUDIO_MODEL_RENDERER = new StudioModelRenderer();
   private static final StudioModelRenderer STUDIO_MODEL_RENDERER_2 = new StudioModelRenderer();

   private StudioPreviewRenderer() {
   }

   public static void invoke(
      RenderManager renderManager,
      ThemeContext themeContext,
      float f,
      float g,
      float h,
      float i,
      StudioAsset studioAsset,
      float j,
      float k,
      float l,
      float m
   ) {
      Metrics metrics = themeContext.getMetrics();
      ColorScheme colorScheme = themeContext.getColorScheme();
      renderManager.invoke24(f, g, h, i, metrics.measure(10.0F), metrics.measure(10.0F), metrics.measure(10.0F), metrics.measure(10.0F));

      try {
         invoke3(renderManager, f, g, h, i);
         StudioModel studioModel = studioAsset == null ? null : studioAsset.resolve7();
         if (studioModel == null) {
            invoke4(renderManager, themeContext, f, g, h, i, studioAsset, m);
         } else {
            float floatValue = (float)(System.currentTimeMillis() % 100000L) * 0.001F;
            float floatValue2 = Math.max(studioModel.measure7(), studioModel.measure8());
            float floatValue3 = Math.min(i * 0.82F / studioModel.measure6(), h * 0.78F / floatValue2) * Math.max(0.2F, l);
            float floatValue4 = f + h * 0.5F;
            float floatValue5 = g + i * 0.5F + (float)Math.sin(floatValue * 1.3F) * floatValue3 * 0.3F;
            float floatValue6 = j + (float)Math.sin(floatValue * 0.25F) * 4.0F;
            STUDIO_MODEL_RENDERER.invoke(renderManager, studioModel, studioAsset.getText(), floatValue4, floatValue5, floatValue3, floatValue6, k, m, floatValue, true);
         }
      } finally {
         renderManager.invoke20();
         renderManager.invoke25();
      }

      renderManager.invoke28(f, g, h, i, metrics.measure(10.0F), ColorScheme.compute6(colorScheme.getIntValue14(), 96), 0.7F);
   }

   public static void invoke2(RenderManager renderManager2, StudioModel studioModel2, String string, float f, float g, float h, float i, float j) {
      if (studioModel2 != null) {
         float floatValue7 = Math.max(studioModel2.measure7(), studioModel2.measure8());
         float floatValue8 = Math.min(i * 0.8F / studioModel2.measure6(), h * 0.84F / floatValue7);
         float floatValue9 = f + h * 0.5F;
         float floatValue10 = g + i * 0.52F;
         STUDIO_MODEL_RENDERER_2.invoke(renderManager2, studioModel2, string, floatValue9, floatValue10, floatValue8, 200.0F, -10.0F, j, 0.0F, false);
      }
   }

   private static void invoke3(RenderManager renderManager3, float f, float g, float h, float i) {
      renderManager3.invoke5(f, g, h, i, 0.0F, -15921388);
      int intValue = (int)Math.ceil(h / 12.0F);
      int intValue2 = (int)Math.ceil(i / 12.0F);

      for (int intValue3 = 0; intValue3 < intValue2; intValue3++) {
         for (int intValue4 = 0; intValue4 < intValue; intValue4++) {
            if ((intValue3 + intValue4 & 1) != 0) {
               float floatValue11 = f + intValue4 * 12;
               float floatValue12 = g + intValue3 * 12;
               float floatValue13 = Math.min(12.0F, f + h - floatValue11);
               float floatValue14 = Math.min(12.0F, g + i - floatValue12);
               if (floatValue13 > 0.0F && floatValue14 > 0.0F) {
                  renderManager3.invoke5(floatValue11, floatValue12, floatValue13, floatValue14, 0.0F, -15197404);
               }
            }
         }
      }

      float floatValue15 = g + i * 0.86F;
      renderManager3.invoke5(f, floatValue15, h, i - (floatValue15 - g), 0.0F, -15657957);
      renderManager3.invoke5(f, floatValue15, h, 1.0F, 0.0F, -14670802);
   }

   private static void invoke4(
      RenderManager renderManager4, ThemeContext themeContext2, float f, float g, float h, float i, StudioAsset studioAsset2, float j
   ) {
      Metrics metrics2 = themeContext2.getMetrics();
      ColorScheme colorScheme2 = themeContext2.getColorScheme();
      String text = studioAsset2 == null ? "Выберите модель" : "Не удалось загрузить модель";
      String text2 = studioAsset2 == null ? "" : resolve(studioAsset2.resolve6());
      float floatValue16 = ClickGuiRenderUtils.measure(FontRegistry.fontObject, text, 11.0F);
      ClickGuiRenderUtils.invoke4(
         renderManager4,
         metrics2,
         FontRegistry.fontObject,
         f + (h - floatValue16) * 0.5F,
         g + i * 0.46F,
         metrics2.measure(14.0F),
         11.0F,
         text,
         ColorScheme.compute6(colorScheme2.getIntValue13(), Math.round(200.0F * j))
      );
      if (!text2.isEmpty()) {
         float floatValue17 = ClickGuiRenderUtils.measure(FontRegistry.fontObject, text2, 9.0F);
         ClickGuiRenderUtils.invoke4(
            renderManager4,
            metrics2,
            FontRegistry.fontObject,
            f + (h - floatValue17) * 0.5F,
            g + i * 0.46F + metrics2.measure(16.0F),
            metrics2.measure(12.0F),
            9.0F,
            text2,
            ColorScheme.compute6(colorScheme2.getIntValue15(), Math.round(180.0F * j))
         );
      }
   }

   private static String resolve(String string) {
      return string == null ? "" : string;
   }
}
