package ru.metaculture.protection;

import java.util.List;
import lombok.Generated;
import net.minecraft.client.gui.screen.Screen;

public final class ClickGuiInputController {
   private final ClickGuiHitMapBuilder clickGuiHitMapBuilder;
   private final ClickGuiSettingController clickGuiSettingController;
   private final ClickGuiKeyboardController clickGuiKeyboardController;

   public boolean check(
      ClickGuiState clickGuiState,
      ClickGuiGeometry clickGuiGeometry,
      ModuleLayoutResult moduleLayoutResult,
      Metrics metrics,
      ThemePalette themePalette,
      float f,
      float g,
      int i
   ) {
      clickGuiState.setFloatValue(f);
      clickGuiState.setFloatValue2(g);
      if (clickGuiState.check4() && i >= 0 && i <= 8) {
         clickGuiState.invoke50(i);
         return true;
      } else if (i == 0 && check2(clickGuiGeometry, metrics, f, g)) {
         clickGuiState.setFlag5(false);
         clickGuiState.setFlag6(false);
         clickGuiState.invoke75((List<Integer>)null);
         clickGuiState.invoke59(f, g, clickGuiGeometry, metrics);
         return true;
      } else if (i == 0 && clickGuiState.isFlag22() && check3(clickGuiGeometry, metrics, f, g)) {
         clickGuiState.setFlag5(false);
         clickGuiState.setFlag6(false);
         clickGuiState.invoke75((List<Integer>)null);
         clickGuiState.invoke61(f, g, clickGuiGeometry, metrics);
         return true;
      } else if (i == 0 && ClickGuiDragRegistry.check2(f, g)) {
         clickGuiState.setFlag5(false);
         clickGuiState.setFlag6(false);
         clickGuiState.invoke75((List<Integer>)null);
         return true;
      } else {
         if (i == 0 && clickGuiState.isFlag2()) {
            if (CoreDiagnosticsOverlay.check2(clickGuiGeometry, metrics, f, g)) {
               clickGuiState.setFloatValue15(CoreDiagnosticsOverlay.measure9(clickGuiGeometry, metrics, f));
               clickGuiState.invoke19();
               return true;
            }

            if (CoreDiagnosticsOverlay.check3(clickGuiGeometry, metrics, f, g)) {
               clickGuiState.setFloatValue14(CoreDiagnosticsOverlay.measure10(clickGuiGeometry, metrics, g));
               clickGuiState.invoke20();
               return true;
            }
         }

         for (ClickGuiHitTarget clickGuiHitTarget : this.clickGuiHitMapBuilder.resolve(clickGuiState, clickGuiGeometry, moduleLayoutResult, metrics, themePalette, f, g)) {
            if (clickGuiHitTarget.check(f, g, i)) {
               clickGuiHitTarget.invoke(clickGuiState);
               return true;
            }
         }

         if (i == 0) {
            clickGuiState.setFlag5(false);
            clickGuiState.setFlag6(false);
            clickGuiState.invoke75((List<Integer>)null);
            clickGuiState.invoke39();
            if (this.check10(clickGuiGeometry, metrics, f, g)) {
               clickGuiState.invoke56(f, g, clickGuiGeometry);
               return true;
            }

            if (this.check12(clickGuiGeometry, metrics, f, g)) {
               return true;
            }

            if (clickGuiState.isFlag22() && this.check11(clickGuiGeometry, metrics, f, g)) {
               clickGuiState.invoke58(f, g, clickGuiGeometry);
               return true;
            }
         }

         return false;
      }
   }

   public static boolean check2(ClickGuiGeometry clickGuiGeometry2, Metrics metrics2, float f, float g) {
      if (clickGuiGeometry2 != null && metrics2 != null) {
         float floatValue = Math.max(14.0F, metrics2.measure(22.0F));
         float floatValue2 = clickGuiGeometry2.getFloatValue() + metrics2.getFloatValue3() - floatValue;
         float floatValue3 = clickGuiGeometry2.getFloatValue2() + metrics2.getFloatValue4() - floatValue;
         return f >= floatValue2
            && g >= floatValue3
            && f < clickGuiGeometry2.getFloatValue() + metrics2.getFloatValue3()
            && g < clickGuiGeometry2.getFloatValue2() + metrics2.getFloatValue4();
      } else {
         return false;
      }
   }

   public static boolean check3(ClickGuiGeometry clickGuiGeometry3, Metrics metrics3, float f, float g) {
      if (clickGuiGeometry3 != null && metrics3 != null) {
         float floatValue4 = Math.max(12.0F, metrics3.measure2(18.0F));
         float floatValue5 = clickGuiGeometry3.getFloatValue22() + metrics3.getFloatValue18() - floatValue4;
         float floatValue6 = clickGuiGeometry3.getFloatValue23() + metrics3.getFloatValue19() - floatValue4;
         return f >= floatValue5
            && g >= floatValue6
            && f < clickGuiGeometry3.getFloatValue22() + metrics3.getFloatValue18()
            && g < clickGuiGeometry3.getFloatValue23() + metrics3.getFloatValue19();
      } else {
         return false;
      }
   }

   public boolean check4(ClickGuiState clickGuiState2) {
      boolean flag = ClickGuiDragRegistry.check4();
      boolean flag2 = clickGuiState2.getNumberSetting() != null;
      boolean flag3 = clickGuiState2.SpacerSetting() || clickGuiState2.FoundryShaderSetting() || clickGuiState2.isFlag20();
      boolean flag4 = flag
         || clickGuiState2.check2()
         || clickGuiState2.check6()
         || clickGuiState2.check7()
         || clickGuiState2.check5()
         || clickGuiState2.check8()
         || flag2
         || flag3
         || SpecialModuleCardHandlers.check4(clickGuiState2);
      clickGuiState2.invoke75((List<Integer>)null);
      clickGuiState2.setColorSetting((ColorSetting)null);
      if (flag2) {
         clickGuiState2.invoke66();
         clickGuiState2.setNumberSetting((NumberSetting)null);
      }

      if (flag3) {
         this.clickGuiSettingController.invoke9(clickGuiState2);
      }

      return flag4;
   }

   public boolean check5(ClickGuiState clickGuiState3, ClickGuiGeometry clickGuiGeometry4, Metrics metrics4, float f, float g) {
      clickGuiState3.setFloatValue(f);
      clickGuiState3.setFloatValue2(g);
      if (ClickGuiDragRegistry.check3(f, g)) {
         return true;
      } else if (clickGuiState3.isFlag8()) {
         clickGuiState3.setFloatValue15(CoreDiagnosticsOverlay.measure9(clickGuiGeometry4, metrics4, f));
         return true;
      } else if (clickGuiState3.isFlag9()) {
         clickGuiState3.setFloatValue14(CoreDiagnosticsOverlay.measure10(clickGuiGeometry4, metrics4, g));
         return true;
      } else if (clickGuiState3.isFlag14()) {
         clickGuiState3.invoke60(f, g);
         return true;
      } else if (clickGuiState3.isFlag16()) {
         clickGuiState3.invoke62(f, g);
         return true;
      } else if (clickGuiState3.isFlag13()) {
         clickGuiState3.invoke63(f, g);
         return true;
      } else if (clickGuiState3.isFlag12()) {
         clickGuiState3.invoke57(f, g);
         return true;
      } else if (clickGuiState3.SpacerSetting() || clickGuiState3.FoundryShaderSetting() || clickGuiState3.isFlag20()) {
         this.clickGuiSettingController.invoke8(clickGuiState3, f, g);
         return true;
      } else if (clickGuiState3.getNumberSetting() != null) {
         this.clickGuiSettingController.invoke7(clickGuiState3, f);
         return true;
      } else {
         return SpecialModuleCardHandlers.check3(clickGuiState3, f, g);
      }
   }

   public boolean check6(
      ClickGuiState clickGuiState4, ClickGuiGeometry clickGuiGeometry5, ModuleLayoutResult moduleLayoutResult2, Metrics metrics5, float f, float g, double d, double e
   ) {
      if (!ClickGuiRenderUtils.check2(
         f, g, clickGuiGeometry5.getFloatValue11(), clickGuiGeometry5.getFloatValue12(), clickGuiGeometry5.getFloatValue13(), clickGuiGeometry5.getFloatValue14()
      )) {
         if (this.check12(clickGuiGeometry5, metrics5, f, g)) {
            return true;
         } else if (clickGuiState4.isFlag22()
            && ClickGuiRenderUtils.check2(
               f, g, clickGuiGeometry5.getFloatValue22(), clickGuiGeometry5.getFloatValue23(), metrics5.getFloatValue18(), metrics5.getFloatValue19()
            )) {
            clickGuiState4.invoke14((float)e * metrics5.measure(36.0F), metrics5);
            return true;
         } else {
            return false;
         }
      } else if (clickGuiState4.isFlag21()) {
         CoreDiagnosticsPanelRenderer.check(clickGuiGeometry5, metrics5, f, g, e);
         return true;
      } else if (!clickGuiState4.isFlag2()) {
         if (clickGuiState4.isFlag() && this.check7(clickGuiState4, clickGuiGeometry5, metrics5, f, g, e)) {
            return true;
         } else if (SpecialModuleCardHandlers.check2(clickGuiState4, moduleLayoutResult2, metrics5, f, g, e)) {
            return true;
         } else {
            clickGuiState4.invoke64((float)e * metrics5.measure(36.0F), metrics5);
            return true;
         }
      } else {
         if (CoreDiagnosticsOverlay.check(clickGuiGeometry5, metrics5, f, g)) {
            float floatValue7 = (float)e * metrics5.measure(36.0F);
            float floatValue8 = (float)d * metrics5.measure(64.0F);
            if (Math.abs(floatValue8) <= 0.001F && (Screen.hasShiftDown() || Screen.hasControlDown())) {
               floatValue8 = (float)e * metrics5.measure(96.0F);
               floatValue7 = 0.0F;
            }

            clickGuiState4.invoke18(floatValue7, floatValue8);
         }

         return true;
      }
   }

   private boolean check7(ClickGuiState clickGuiState5, ClickGuiGeometry clickGuiGeometry6, Metrics metrics6, float f, float g, double d) {
      if (WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null) {
         AutoBuy autoBuy = WildClient.INSTANCE.moduleManager.getModule(AutoBuy.class);
         return autoBuy != null && SpecialModuleCardHandlers.check(autoBuy)
            ? SpecialModuleCardHandlers.check2(
               clickGuiState5, new ModuleLayoutResult(List.of(SpecialModuleCardHandlers.resolve2(autoBuy, clickGuiGeometry6, metrics6)), 0.0F), metrics6, f, g, d
            )
            : false;
      } else {
         return false;
      }
   }

   public boolean check8(ClickGuiState clickGuiState6, int i) {
      return SpecialModuleCardHandlers.check5(clickGuiState6, i) ? true : this.clickGuiKeyboardController.check(clickGuiState6, i);
   }

   public boolean check9(ClickGuiState clickGuiState7, char c) {
      return SpecialModuleCardHandlers.check6(clickGuiState7, c) ? true : this.clickGuiKeyboardController.check2(clickGuiState7, c);
   }

   public void invoke(ClickGuiState clickGuiState8, float f) {
      if (!clickGuiState8.isFlag12() && !clickGuiState8.isFlag13()) {
         if (!clickGuiState8.SpacerSetting() && !clickGuiState8.FoundryShaderSetting() && !clickGuiState8.isFlag20()) {
            this.clickGuiSettingController.invoke7(clickGuiState8, f);
         } else {
            this.clickGuiSettingController.invoke8(clickGuiState8, f, clickGuiState8.getFloatValue2());
         }
      }
   }

   private boolean check10(ClickGuiGeometry clickGuiGeometry7, Metrics metrics7, float f, float g) {
      return ClickGuiRenderUtils.check2(
            f, g, clickGuiGeometry7.getFloatValue7(), clickGuiGeometry7.getFloatValue8(), clickGuiGeometry7.getFloatValue9(), metrics7.getFloatValue10()
         )
         || this.check13(clickGuiGeometry7, metrics7, f, g);
   }

   private boolean check11(ClickGuiGeometry clickGuiGeometry8, Metrics metrics8, float f, float g) {
      return ClickGuiRenderUtils.check2(
         f, g, clickGuiGeometry8.getFloatValue22(), clickGuiGeometry8.getFloatValue23(), metrics8.getFloatValue18(), metrics8.getFloatValue19()
      );
   }

   private boolean check12(ClickGuiGeometry clickGuiGeometry9, Metrics metrics9, float f, float g) {
      return ClickGuiRenderUtils.check2(f, g, clickGuiGeometry9.getFloatValue(), clickGuiGeometry9.getFloatValue2(), metrics9.getFloatValue3(), metrics9.getFloatValue4());
   }

   private boolean check13(ClickGuiGeometry clickGuiGeometry10, Metrics metrics10, float f, float g) {
      if (!ClickGuiRenderUtils.check2(f, g, clickGuiGeometry10.getFloatValue3(), clickGuiGeometry10.getFloatValue4(), metrics10.getFloatValue7(), metrics10.getFloatValue9())
         )
       {
         return false;
      } else if (ClickGuiRenderUtils.check2(
         f,
         g,
         clickGuiGeometry10.getFloatValue3() + metrics10.measure(16.0F),
         clickGuiGeometry10.getFloatValue4() + metrics10.measure(16.0F),
         metrics10.measure(40.0F),
         metrics10.measure(40.0F)
      )) {
         return false;
      } else {
         float floatValue9 = SidebarRenderer.measure5(clickGuiGeometry10, metrics10);
         float floatValue10 = SidebarRenderer.measure4(metrics10);
         if (ClickGuiRenderUtils.check2(f, g, floatValue9, SidebarRenderer.measure6(clickGuiGeometry10, metrics10), floatValue10, floatValue10)) {
            return false;
         } else {
            float floatValue11 = floatValue9;
            float floatValue12 = clickGuiGeometry10.getFloatValue4() + metrics10.measure(89.0F);

            for (int intValue = 0; intValue < Category.values().length; intValue++) {
               if (ClickGuiRenderUtils.check2(f, g, floatValue11, floatValue12 + intValue * metrics10.measure(56.0F), floatValue10, floatValue10)) {
                  return false;
               }
            }

            float floatValue13 = SidebarRenderer.measure7(clickGuiGeometry10, metrics10);
            return !ClickGuiRenderUtils.check2(f, g, floatValue11, floatValue13, floatValue10, floatValue10);
         }
      }
   }

   @Generated
   public ClickGuiInputController(ClickGuiHitMapBuilder clickGuiHitMapBuilder, ClickGuiSettingController clickGuiSettingController, ClickGuiKeyboardController clickGuiKeyboardController) {
      this.clickGuiHitMapBuilder = clickGuiHitMapBuilder;
      this.clickGuiSettingController = clickGuiSettingController;
      this.clickGuiKeyboardController = clickGuiKeyboardController;
   }
}
