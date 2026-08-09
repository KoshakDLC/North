package ru.metaculture.protection;

import lombok.Generated;
import net.minecraft.client.gui.DrawContext;

public final class ContentPanelRenderer {
   private final ModuleListRenderer moduleListRenderer;
   private final CoreDiagnosticsOverlay coreDiagnosticsOverlay = new CoreDiagnosticsOverlay();
   private final StudioPanelRenderer studioPanelRenderer = new StudioPanelRenderer();

   public StudioPanelRenderer getStudioPanelRenderer() {
      return this.studioPanelRenderer;
   }

   public void invoke(
      RenderManager renderManager,
      DrawContext drawContext,
      ClickGuiState clickGuiState,
      ClickGuiGeometry clickGuiGeometry,
      ModuleLayoutResult moduleLayoutResult,
      ThemeContext themeContext
   ) {
      Metrics metrics = themeContext.getMetrics();
      ColorScheme colorScheme = themeContext.getColorScheme();
      renderManager.invoke6(
         clickGuiGeometry.getFloatValue11(),
         clickGuiGeometry.getFloatValue12(),
         clickGuiGeometry.getFloatValue13(),
         metrics.getFloatValue12(),
         metrics.measure(4.0F),
         metrics.measure(4.0F),
         metrics.measure(16.0F),
         metrics.measure(4.0F),
         ClickGuiRenderUtils.compute11(colorScheme)
      );
      if (colorScheme.isFlag()) {
         renderManager.invoke29(
            clickGuiGeometry.getFloatValue11() + metrics.measure(0.75F),
            clickGuiGeometry.getFloatValue12() + metrics.measure(0.75F),
            clickGuiGeometry.getFloatValue13() - metrics.measure(1.5F),
            metrics.getFloatValue12() - metrics.measure(1.5F),
            metrics.measure(3.5F),
            metrics.measure(3.5F),
            metrics.measure(15.5F),
            metrics.measure(3.5F),
            ClickGuiRenderUtils.compute13(colorScheme, 0.82F),
            metrics.measure(0.85F)
         );
      }

      if (clickGuiState.isFlag2()) {
         renderManager.invoke20();
         renderManager.invoke24(
            clickGuiGeometry.getFloatValue11(),
            clickGuiGeometry.getFloatValue12(),
            clickGuiGeometry.getFloatValue13(),
            metrics.getFloatValue12(),
            metrics.measure(4.0F),
            metrics.measure(4.0F),
            metrics.measure(16.0F),
            metrics.measure(4.0F)
         );
         boolean flag = false ;

         try {
            flag = true;
            this.coreDiagnosticsOverlay.invoke(renderManager, clickGuiState, clickGuiGeometry, themeContext);
            flag = false;
         } finally {
            if (flag) {
               renderManager.invoke20();
               renderManager.invoke25();
            }
         }

         renderManager.invoke20();
         renderManager.invoke25();
      } else if (clickGuiState.isFlag()) {
         renderManager.invoke20();
         renderManager.invoke24(
            clickGuiGeometry.getFloatValue11(),
            clickGuiGeometry.getFloatValue12(),
            clickGuiGeometry.getFloatValue13(),
            metrics.getFloatValue12(),
            metrics.measure(4.0F),
            metrics.measure(4.0F),
            metrics.measure(16.0F),
            metrics.measure(4.0F)
         );

         try {
            this.invoke2(renderManager, drawContext, clickGuiState, clickGuiGeometry, metrics, themeContext);
         } finally {
            renderManager.invoke20();
            renderManager.invoke25();
         }
      } else if (clickGuiState.isFlag3()) {
         renderManager.invoke20();
         renderManager.invoke24(
            clickGuiGeometry.getFloatValue11(),
            clickGuiGeometry.getFloatValue12(),
            clickGuiGeometry.getFloatValue13(),
            metrics.getFloatValue12(),
            metrics.measure(4.0F),
            metrics.measure(4.0F),
            metrics.measure(16.0F),
            metrics.measure(4.0F)
         );

         try {
            this.studioPanelRenderer
               .invoke2(
                  renderManager,
                  clickGuiState,
                  themeContext,
                  clickGuiGeometry.getFloatValue11(),
                  clickGuiGeometry.getFloatValue12(),
                  clickGuiGeometry.getFloatValue13(),
                  metrics.getFloatValue12()
               );
         } finally {
            renderManager.invoke20();
            renderManager.invoke25();
         }
      } else {
         float floatValue = ClickGuiRenderUtils.measure13(metrics);
         float floatValue2 = clickGuiGeometry.getFloatValue12() - floatValue;
         float floatValue3 = clickGuiGeometry.getFloatValue12() + metrics.getFloatValue12() + floatValue;
         float floatValue4 = clickGuiState.getSpringAnimation2().getFloatValue2();
         float floatValue5 = Math.min(1.0F, Math.abs(floatValue4) / Math.max(metrics.measure(180.0F), 1.0F));
         float floatValue6 = clickGuiState.measure5("content:scroll:material", floatValue5, SpringSpec.resolve2());
         ClickGuiRenderUtils.invoke14(
            renderManager,
            metrics,
            colorScheme,
            clickGuiGeometry.getFloatValue11(),
            clickGuiGeometry.getFloatValue12(),
            clickGuiGeometry.getFloatValue13(),
            metrics.getFloatValue12(),
            metrics.measure(4.0F),
            metrics.measure(4.0F),
            metrics.measure(16.0F),
            metrics.measure(4.0F),
            floatValue4,
            () -> {
               for (ModulePlacement var10x : moduleLayoutResult.getItems()) {
                  if (!(var10x.getFloatValue2() + var10x.getFloatValue4() < floatValue2) && !(var10x.getFloatValue2() > floatValue3)) {
                     this.moduleListRenderer.invoke(renderManager, drawContext, clickGuiState, var10x, themeContext, floatValue6);
                  }
               }
            }
         );
      }

      if (!clickGuiState.isFlag() && !clickGuiState.isFlag2() && !clickGuiState.isFlag3()) {
         this.invoke3(renderManager, clickGuiState, clickGuiGeometry, moduleLayoutResult, themeContext);
      }
   }

   private void invoke2(
      RenderManager renderManager2,
      DrawContext drawContext,
      ClickGuiState clickGuiState2,
      ClickGuiGeometry clickGuiGeometry2,
      Metrics metrics2,
      ThemeContext themeContext2
   ) {
      if (WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null) {
         AutoBuy autoBuy = WildClient.INSTANCE.moduleManager.getModule(AutoBuy.class);
         SpecialModuleCardHandler specialModuleCardHandler = SpecialModuleCardHandlers.resolve(autoBuy);
         if (specialModuleCardHandler != null) {
            specialModuleCardHandler.invoke4(renderManager2, drawContext, clickGuiState2, SpecialModuleCardHandlers.resolve2(autoBuy, clickGuiGeometry2, metrics2), themeContext2);
         }
      }
   }

   private void invoke3(
      RenderManager renderManager3, ClickGuiState clickGuiState3, ClickGuiGeometry clickGuiGeometry3, ModuleLayoutResult moduleLayoutResult2, ThemeContext themeContext3
   ) {
      Metrics metrics3 = themeContext3.getMetrics();
      if (!(moduleLayoutResult2.getFloatValue() <= 0.5F)) {
         float floatValue7 = Math.max(
            metrics3.measure(24.0F), clickGuiGeometry3.getFloatValue18() * (clickGuiGeometry3.getFloatValue18() / (clickGuiGeometry3.getFloatValue18() + moduleLayoutResult2.getFloatValue()))
         );
         float floatValue8 = Math.min(1.0F, Math.max(0.0F, -clickGuiState3.getFloatValue11() / moduleLayoutResult2.getFloatValue()));
         float floatValue9 = clickGuiGeometry3.getFloatValue16() + (clickGuiGeometry3.getFloatValue18() - floatValue7) * floatValue8;
         float floatValue10 = clickGuiGeometry3.getFloatValue21() - metrics3.measure(0.35F);
         ClickGuiRenderUtils.invoke17(
            renderManager3,
            metrics3,
            themeContext3.getColorScheme(),
            floatValue10,
            clickGuiGeometry3.getFloatValue16(),
            metrics3.getFloatValue17(),
            clickGuiGeometry3.getFloatValue18(),
            floatValue9,
            floatValue7,
            clickGuiState3.getSpringAnimation2().getFloatValue2(),
            0.0F,
            1L,
            clickGuiState3.getFloatValue(),
            clickGuiState3.getFloatValue2(),
            clickGuiState3::invoke15
         );
      }
   }

   @Generated
   public ContentPanelRenderer(ModuleListRenderer moduleListRenderer) {
      this.moduleListRenderer = moduleListRenderer;
   }
}
