package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import org.wild.module.api.Module;

public final class ClickGuiHitMapBuilder {
   private final ModuleLayoutEngine moduleLayoutEngine;
   private final ClickGuiSettingController clickGuiSettingController;
   private final CoreDiagnosticsPanelRenderer coreDiagnosticsPanelRenderer;

   public List<ClickGuiHitTarget> resolve(
      ClickGuiState clickGuiState,
      ClickGuiGeometry clickGuiGeometry,
      ModuleLayoutResult moduleLayoutResult,
      Metrics metrics,
      ThemePalette themePalette,
      float f,
      float g
   ) {
      ArrayList arrayList = new ArrayList();
      boolean flag = this.check(clickGuiGeometry, metrics, f, g);
      this.invoke5(arrayList, clickGuiGeometry, metrics);
      this.invoke2(arrayList, clickGuiGeometry, metrics);
      this.invoke3(arrayList, clickGuiGeometry, metrics);
      this.invoke6(arrayList, clickGuiGeometry, metrics);
      this.invoke4(arrayList, clickGuiGeometry, metrics);
      this.invoke7(arrayList, clickGuiGeometry, metrics);
      this.invoke10(arrayList, clickGuiGeometry, metrics);
      if (clickGuiState.isFlag21()) {
         this.invoke14(arrayList, clickGuiGeometry, metrics);
         this.invoke8(arrayList, clickGuiGeometry, metrics);
         this.invoke9(arrayList, clickGuiGeometry, metrics);
         if (!flag) {
            this.invoke(arrayList, clickGuiState, clickGuiGeometry, metrics, themePalette);
         }

         return arrayList;
      } else {
         if (clickGuiState.isFlag2()) {
            this.invoke15(arrayList, clickGuiGeometry, metrics);
            this.invoke13(arrayList, clickGuiGeometry, metrics);
         } else if (clickGuiState.isFlag()) {
            this.invoke12(arrayList, clickGuiState, clickGuiGeometry, metrics);
         } else if (!clickGuiState.isFlag3()) {
            this.invoke11(arrayList, clickGuiState, moduleLayoutResult, clickGuiGeometry, metrics, f);
         }

         if (!flag) {
            this.invoke(arrayList, clickGuiState, clickGuiGeometry, metrics, themePalette);
         }

         return arrayList;
      }
   }

   private void invoke(List<ClickGuiHitTarget> list, ClickGuiState clickGuiState2, ClickGuiGeometry clickGuiGeometry2, Metrics metrics2, ThemePalette themePalette2) {
      if (clickGuiState2.isFlag22()) {
         float floatValue = metrics2.measure2(20.0F);
         list.add(
            ClickGuiHitTarget.resolve()
               .setIntValue(0)
               .setFloatValue(clickGuiGeometry2.getFloatValue22() + metrics2.getFloatValue18() - metrics2.measure2(16.0F) - floatValue)
               .setFloatValue2(clickGuiGeometry2.getFloatValue23() + metrics2.measure2(20.0F))
               .setFloatValue3(floatValue)
               .setFloatValue4(floatValue)
               .setClickGuiAction(ClickGuiState::invoke30)
               .resolve()
         );
         ThemeGridLayout themeGridLayout = ThemeGridLayout.resolve(clickGuiGeometry2, metrics2);
         if (!clickGuiState2.getText2().isEmpty()) {
            list.add(
               ClickGuiHitTarget.resolve()
                  .setIntValue(0)
                  .setFloatValue(themeGridLayout.measure2())
                  .setFloatValue2(themeGridLayout.getFloatValue6())
                  .setFloatValue3(themeGridLayout.getFloatValue82())
                  .setFloatValue4(themeGridLayout.getFloatValue8())
                  .setClickGuiAction(ClickGuiState::invoke26)
                  .resolve()
            );
         }

         list.add(
            ClickGuiHitTarget.resolve()
               .setIntValue(0)
               .setFloatValue(themeGridLayout.getFloatValue5())
               .setFloatValue2(themeGridLayout.getFloatValue6())
               .setFloatValue3(themeGridLayout.getFloatValue7())
               .setFloatValue4(themeGridLayout.getFloatValue8())
               .setClickGuiAction(ClickGuiState::invoke25)
               .resolve()
         );
         float floatValue2 = themeGridLayout.getFloatValue2();
         float floatValue3 = floatValue2 + themeGridLayout.getFloatValue4();
         float floatValue4 = clickGuiState2.measure4();
         List items = clickGuiState2.resolve3(themePalette2);

         for (int intValue = 0; intValue < items.size(); intValue++) {
            int intValue2 = (Integer)items.get(intValue);
            ThemeGridLayout.Cell cell = themeGridLayout.resolve2(intValue, floatValue4);
            if (!(cell.y() + cell.height() < floatValue2) && !(cell.y() > floatValue3)) {
               list.add(
                  ClickGuiHitTarget.resolve()
                     .setIntValue(0)
                     .setFloatValue(cell.x())
                     .setFloatValue2(cell.y())
                     .setFloatValue3(cell.width())
                     .setFloatValue4(cell.height())
                     .setFloatValue5(themeGridLayout.getFloatValue())
                     .setFloatValue6(floatValue2)
                     .setFloatValue7(themeGridLayout.getFloatValue3())
                     .setFloatValue8(floatValue3 - floatValue2)
                     .setClickGuiAction(clickGuiState3 -> clickGuiState3.invoke13(themePalette2.getItems().get(intValue2).getTheme(), intValue2))
                     .resolve()
               );
            }
         }
      }
   }

   private void invoke2(List<ClickGuiHitTarget> list, ClickGuiGeometry clickGuiGeometry3, Metrics metrics3) {
      list.add(
         ClickGuiHitTarget.resolve()
            .setIntValue(0)
            .setFloatValue(SearchBarRenderer.measure7(clickGuiGeometry3, metrics3))
            .setFloatValue2(SearchBarRenderer.measure9(clickGuiGeometry3, metrics3))
            .setFloatValue3(SearchBarRenderer.measure5(metrics3))
            .setFloatValue4(SearchBarRenderer.measure6(metrics3))
            .setClickGuiAction(clickGuiState4 -> {
               clickGuiState4.setFlag5(false);
               clickGuiState4.setNumberSetting((NumberSetting)null);
               clickGuiState4.setFlag22(false);
               clickGuiState4.setFlag24(!clickGuiState4.isFlag24());
            })
            .resolve()
      );
   }

   private void invoke3(List<ClickGuiHitTarget> list, ClickGuiGeometry clickGuiGeometry4, Metrics metrics4) {
      if (StudioAccessControl.check()) {
         list.add(
            ClickGuiHitTarget.resolve()
               .setIntValue(0)
               .setFloatValue(SearchBarRenderer.measure3(clickGuiGeometry4, metrics4))
               .setFloatValue2(SearchBarRenderer.measure4(clickGuiGeometry4, metrics4))
               .setFloatValue3(SearchBarRenderer.measure(metrics4))
               .setFloatValue4(SearchBarRenderer.measure2(metrics4))
               .setClickGuiAction(clickGuiState5 -> {
                  clickGuiState5.setFlag5(false);
                  clickGuiState5.setNumberSetting((NumberSetting)null);
                  clickGuiState5.setFlag22(false);
                  clickGuiState5.setFlag24(false);
                  clickGuiState5.invoke9();
               })
               .resolve()
         );
      }
   }

   private void invoke4(List<ClickGuiHitTarget> list, ClickGuiGeometry clickGuiGeometry5, Metrics metrics5) {
      float floatValue5 = SidebarRenderer.measure5(clickGuiGeometry5, metrics5);
      float floatValue6 = SidebarRenderer.measure7(clickGuiGeometry5, metrics5);
      float floatValue7 = SidebarRenderer.measure4(metrics5);
      list.add(
         ClickGuiHitTarget.resolve()
            .setIntValue(0)
            .setFloatValue(floatValue5)
            .setFloatValue2(floatValue6)
            .setFloatValue3(floatValue7)
            .setFloatValue4(floatValue7)
            .setClickGuiAction(ClickGuiState::invoke5)
            .resolve()
      );
   }

   private void invoke5(List<ClickGuiHitTarget> list, ClickGuiGeometry clickGuiGeometry6, Metrics metrics6) {
      list.add(
         ClickGuiHitTarget.resolve()
            .setIntValue(0)
            .setFloatValue(SidebarRenderer.measure(clickGuiGeometry6, metrics6))
            .setFloatValue2(SidebarRenderer.measure2(clickGuiGeometry6, metrics6))
            .setFloatValue3(SidebarRenderer.measure3(metrics6))
            .setFloatValue4(SidebarRenderer.measure3(metrics6))
            .setClickGuiAction(clickGuiState6 -> clickGuiState6.setFlag22(!clickGuiState6.isFlag22()))
            .resolve()
      );
   }

   private void invoke6(List<ClickGuiHitTarget> list, ClickGuiGeometry clickGuiGeometry7, Metrics metrics7) {
      float floatValue8 = SidebarRenderer.measure5(clickGuiGeometry7, metrics7);
      float floatValue9 = Math.round(clickGuiGeometry7.getFloatValue4() + metrics7.measure(89.0F));
      Category[] categories = Category.values();

      for (int intValue3 = 0; intValue3 < categories.length; intValue3++) {
         Category category = categories[intValue3];
         float floatValue10 = floatValue9 + intValue3 * metrics7.measure(56.0F);
         list.add(
            ClickGuiHitTarget.resolve()
               .setIntValue(0)
               .setFloatValue(floatValue8)
               .setFloatValue2(floatValue10)
               .setFloatValue3(SidebarRenderer.measure4(metrics7))
               .setFloatValue4(SidebarRenderer.measure4(metrics7))
               .setClickGuiAction(clickGuiState7 -> clickGuiState7.invoke4(category))
               .resolve()
         );
      }
   }

   private void invoke7(List<ClickGuiHitTarget> list, ClickGuiGeometry clickGuiGeometry8, Metrics metrics8) {
      float floatValue11 = SidebarRenderer.measure5(clickGuiGeometry8, metrics8);
      float floatValue12 = SidebarRenderer.measure6(clickGuiGeometry8, metrics8);
      float floatValue13 = SidebarRenderer.measure4(metrics8);
      list.add(
         ClickGuiHitTarget.resolve()
            .setIntValue(0)
            .setFloatValue(floatValue11)
            .setFloatValue2(floatValue12)
            .setFloatValue3(floatValue13)
            .setFloatValue4(floatValue13)
            .setClickGuiAction(ClickGuiState::invoke41)
            .resolve()
      );
   }

   private void invoke8(List<ClickGuiHitTarget> list, ClickGuiGeometry clickGuiGeometry9, Metrics metrics9) {
      list.add(
         ClickGuiHitTarget.resolve()
            .setIntValue(0)
            .setFloatValue(this.coreDiagnosticsPanelRenderer.measure14(clickGuiGeometry9, metrics9))
            .setFloatValue2(this.coreDiagnosticsPanelRenderer.measure15(clickGuiGeometry9, metrics9))
            .setFloatValue3(this.coreDiagnosticsPanelRenderer.measure16(metrics9))
            .setFloatValue4(this.coreDiagnosticsPanelRenderer.measure17(metrics9))
            .setClickGuiAction(ClickGuiState::invoke41)
            .resolve()
      );
   }

   private void invoke9(List<ClickGuiHitTarget> list, ClickGuiGeometry clickGuiGeometry10, Metrics metrics10) {
      list.add(
         ClickGuiHitTarget.resolve()
            .setIntValue(0)
            .setFloatValue(CoreDiagnosticsPanelRenderer.measure5(clickGuiGeometry10, metrics10))
            .setFloatValue2(CoreDiagnosticsPanelRenderer.measure6(clickGuiGeometry10, metrics10))
            .setFloatValue3(this.coreDiagnosticsPanelRenderer.measure9(metrics10))
            .setFloatValue4(CoreDiagnosticsPanelRenderer.measure8(clickGuiGeometry10, metrics10))
            .setClickGuiAction(clickGuiState8 -> {})
            .resolve()
      );
   }

   private void invoke10(List<ClickGuiHitTarget> list, ClickGuiGeometry clickGuiGeometry11, Metrics metrics11) {
      list.add(
         ClickGuiHitTarget.resolve()
            .setIntValue(0)
            .setFloatValue(clickGuiGeometry11.getFloatValue10() + metrics11.getFloatValue11() - metrics11.measure(34.0F))
            .setFloatValue2(clickGuiGeometry11.getFloatValue8())
            .setFloatValue3(metrics11.measure(34.0F))
            .setFloatValue4(metrics11.getFloatValue10())
            .setClickGuiAction(ClickGuiState::invoke22)
            .resolve()
      );
      list.add(
         ClickGuiHitTarget.resolve()
            .setIntValue(0)
            .setFloatValue(clickGuiGeometry11.getFloatValue10())
            .setFloatValue2(clickGuiGeometry11.getFloatValue8())
            .setFloatValue3(metrics11.getFloatValue11())
            .setFloatValue4(metrics11.getFloatValue10())
            .setClickGuiAction(clickGuiState9 -> {
               clickGuiState9.setFlag5(true);
               clickGuiState9.setFlag6(false);
               clickGuiState9.setNumberSetting((NumberSetting)null);
            })
            .resolve()
      );
   }

   private void invoke11(
      List<ClickGuiHitTarget> list, ClickGuiState clickGuiState10, ModuleLayoutResult moduleLayoutResult2, ClickGuiGeometry clickGuiGeometry12, Metrics metrics12, float f
   ) {
      float floatValue14 = clickGuiGeometry12.getFloatValue11();
      float floatValue15 = clickGuiGeometry12.getFloatValue12();
      float floatValue16 = clickGuiGeometry12.getFloatValue13();
      float floatValue17 = metrics12.getFloatValue12();
      float floatValue18 = floatValue15 + floatValue17;

      for (ModulePlacement modulePlacement : moduleLayoutResult2.getItems()) {
         if (!(modulePlacement.getFloatValue2() >= floatValue18) && !(modulePlacement.getFloatValue2() + modulePlacement.getFloatValue4() <= floatValue15)) {
            if (clickGuiState10.getValues().contains(modulePlacement.getModule())) {
               if (SpecialModuleCardHandlers.check(modulePlacement.getModule())) {
                  SpecialModuleCardHandlers.invoke4(list, clickGuiState10, modulePlacement, metrics12);
               } else {
                  this.invoke17(list, clickGuiState10, modulePlacement, metrics12, f, floatValue14, floatValue15, floatValue16, floatValue17);
               }
            }

            this.invoke16(list, modulePlacement, metrics12, floatValue14, floatValue15, floatValue16, floatValue17);
         }
      }
   }

   private void invoke12(List<ClickGuiHitTarget> list, ClickGuiState clickGuiState11, ClickGuiGeometry clickGuiGeometry13, Metrics metrics13) {
      if (WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null) {
         AutoBuy autoBuy = WildClient.INSTANCE.moduleManager.getModule(AutoBuy.class);
         if (autoBuy != null && SpecialModuleCardHandlers.check(autoBuy)) {
            SpecialModuleCardHandlers.invoke4(list, clickGuiState11, SpecialModuleCardHandlers.resolve2(autoBuy, clickGuiGeometry13, metrics13), metrics13);
         }
      }
   }

   private void invoke13(List<ClickGuiHitTarget> list, ClickGuiGeometry clickGuiGeometry14, Metrics metrics14) {
      list.add(
         ClickGuiHitTarget.resolve()
            .setIntValue(0)
            .setFloatValue(clickGuiGeometry14.getFloatValue11())
            .setFloatValue2(clickGuiGeometry14.getFloatValue12())
            .setFloatValue3(clickGuiGeometry14.getFloatValue13())
            .setFloatValue4(metrics14.getFloatValue12())
            .setClickGuiAction(clickGuiState12 -> {})
            .resolve()
      );
   }

   private void invoke14(List<ClickGuiHitTarget> list, ClickGuiGeometry clickGuiGeometry15, Metrics metrics15) {
      list.add(
         ClickGuiHitTarget.resolve()
            .setIntValue(0)
            .setFloatValue(this.coreDiagnosticsPanelRenderer.measure10(clickGuiGeometry15, metrics15))
            .setFloatValue2(this.coreDiagnosticsPanelRenderer.measure11(clickGuiGeometry15, metrics15))
            .setFloatValue3(this.coreDiagnosticsPanelRenderer.measure12(metrics15))
            .setFloatValue4(this.coreDiagnosticsPanelRenderer.measure13(metrics15))
            .setClickGuiAction(ClickGuiState::invoke7)
            .resolve()
      );
   }

   private void invoke15(List<ClickGuiHitTarget> list, ClickGuiGeometry clickGuiGeometry16, Metrics metrics16) {
      list.add(
         ClickGuiHitTarget.resolve()
            .setIntValue(0)
            .setFloatValue(CoreDiagnosticsOverlay.measure(clickGuiGeometry16, metrics16))
            .setFloatValue2(CoreDiagnosticsOverlay.measure4(clickGuiGeometry16, metrics16))
            .setFloatValue3(CoreDiagnosticsOverlay.measure5(metrics16))
            .setFloatValue4(CoreDiagnosticsOverlay.measure8(metrics16))
            .setClickGuiAction(clickGuiState13 -> RenderDiagnosticsTracker.getInstance().invoke12())
            .resolve()
      );
      list.add(
         ClickGuiHitTarget.resolve()
            .setIntValue(0)
            .setFloatValue(CoreDiagnosticsOverlay.measure2(clickGuiGeometry16, metrics16))
            .setFloatValue2(CoreDiagnosticsOverlay.measure4(clickGuiGeometry16, metrics16))
            .setFloatValue3(CoreDiagnosticsOverlay.measure6(metrics16))
            .setFloatValue4(CoreDiagnosticsOverlay.measure8(metrics16))
            .setClickGuiAction(clickGuiState14 -> RenderDiagnosticsTracker.getInstance().invoke13())
            .resolve()
      );
      list.add(
         ClickGuiHitTarget.resolve()
            .setIntValue(0)
            .setFloatValue(CoreDiagnosticsOverlay.measure3(clickGuiGeometry16, metrics16))
            .setFloatValue2(CoreDiagnosticsOverlay.measure4(clickGuiGeometry16, metrics16))
            .setFloatValue3(CoreDiagnosticsOverlay.measure7(metrics16))
            .setFloatValue4(CoreDiagnosticsOverlay.measure8(metrics16))
            .setClickGuiAction(clickGuiState15 -> RenderDiagnosticsTracker.getInstance().invoke16())
            .resolve()
      );
   }

   private void invoke16(List<ClickGuiHitTarget> list, ModulePlacement modulePlacement2, Metrics metrics17, float f, float g, float h, float i) {
      Module module = modulePlacement2.getModule();
      float floatValue19 = this.moduleLayoutEngine.measure3(module, modulePlacement2.getFloatValue3(), metrics17);
      float floatValue20 = modulePlacement2.getFloatValue2() + metrics17.measure(16.0F);
      float floatValue21 = modulePlacement2.getFloatValue() + modulePlacement2.getFloatValue3() - metrics17.measure(16.0F) - metrics17.measure(24.0F);
      float floatValue22 = floatValue21 - metrics17.measure(22.0F);
      boolean flag2 = SpecialModuleCardHandlers.check(module) || !module.getVisibleSettings().isEmpty();
      if (flag2) {
         list.add(
            this.resolve2(
                  ClickGuiHitTarget.resolve()
                     .setIntValue(0)
                     .setFloatValue(floatValue22 - metrics17.measure(3.0F))
                     .setFloatValue2(floatValue20 - metrics17.measure(3.0F))
                     .setFloatValue3(metrics17.measure(20.0F))
                     .setFloatValue4(metrics17.measure(20.0F))
                     .setClickGuiAction(clickGuiState16 -> clickGuiState16.invoke29(module)),
                  f,
                  g,
                  h,
                  i
               )
               .resolve()
         );
      }

      list.add(
         this.resolve2(
               ClickGuiHitTarget.resolve()
                  .setIntValue(2)
                  .setFloatValue(modulePlacement2.getFloatValue())
                  .setFloatValue2(modulePlacement2.getFloatValue2())
                  .setFloatValue3(modulePlacement2.getFloatValue3())
                  .setFloatValue4(floatValue19)
                  .setClickGuiAction(clickGuiState17 -> clickGuiState17.invoke44(module)),
               f,
               g,
               h,
               i
            )
            .resolve()
      );
      if (flag2) {
         list.add(
            this.resolve2(
                  ClickGuiHitTarget.resolve()
                     .setIntValue(1)
                     .setFloatValue(modulePlacement2.getFloatValue())
                     .setFloatValue2(modulePlacement2.getFloatValue2())
                     .setFloatValue3(modulePlacement2.getFloatValue3())
                     .setFloatValue4(floatValue19)
                     .setClickGuiAction(clickGuiState18 -> clickGuiState18.invoke29(module)),
                  f,
                  g,
                  h,
                  i
               )
               .resolve()
         );
      }

      list.add(
         this.resolve2(
               ClickGuiHitTarget.resolve()
                  .setIntValue(0)
                  .setFloatValue(modulePlacement2.getFloatValue())
                  .setFloatValue2(modulePlacement2.getFloatValue2())
                  .setFloatValue3(modulePlacement2.getFloatValue3())
                  .setFloatValue4(floatValue19)
                  .setClickGuiAction(clickGuiState19 -> module.toggle()),
               f,
               g,
               h,
               i
            )
            .resolve()
      );
   }

   private void invoke17(
      List<ClickGuiHitTarget> list, ClickGuiState clickGuiState20, ModulePlacement modulePlacement3, Metrics metrics18, float f, float g, float h, float i, float j
   ) {
      float floatValue23 = modulePlacement3.getFloatValue() + metrics18.measure(16.0F);
      float floatValue24 = modulePlacement3.getFloatValue2()
         + this.moduleLayoutEngine.measure3(modulePlacement3.getModule(), modulePlacement3.getFloatValue3(), metrics18)
         + metrics18.measure(10.0F);
      float floatValue25 = modulePlacement3.getFloatValue3() - metrics18.measure(32.0F);

      for (Setting setting : modulePlacement3.getModule().getVisibleSettings()) {
         if (setting instanceof SpacerSetting spacerSetting) {
            floatValue24 += metrics18.measure(spacerSetting.getFloatValue());
         } else {
            float floatValue26 = clickGuiState20.measure7(AnimationKeyRegistry.resolve22(setting));
            float floatValue27 = this.moduleLayoutEngine.measure4(setting, metrics18, clickGuiState20);
            float floatValue28 = this.moduleLayoutEngine.measure6(setting, clickGuiState20, metrics18);
            if (floatValue26 < 0.5F) {
               floatValue24 += (floatValue27 + floatValue28 + metrics18.measure(12.0F)) * floatValue26;
            } else {
               float floatValue29 = (1.0F - floatValue26) * metrics18.measure(8.0F);
               if (setting instanceof ColorSetting colorSetting && clickGuiState20.getColorSetting2() == colorSetting) {
                  float floatValue30 = metrics18.measure(16.0F);
                  list.add(
                     this.resolve2(
                           ClickGuiHitTarget.resolve()
                              .setIntValue(0)
                              .setFloatValue(SettingsRenderer.measure21(floatValue23, floatValue25, metrics18))
                              .setFloatValue2(SettingsRenderer.measure22(floatValue24 + floatValue29, metrics18))
                              .setFloatValue3(SettingsRenderer.measure23(metrics18))
                              .setFloatValue4(SettingsRenderer.measure23(metrics18))
                              .setClickGuiAction(clickGuiState21 -> this.clickGuiSettingController.invoke(clickGuiState21, setting, f, floatValue23, floatValue25)),
                           g,
                           h,
                           i,
                           j
                        )
                        .resolve()
                  );
                  float floatValue31 = clickGuiState20.getFloatValue34();
                  float floatValue32 = clickGuiState20.getFloatValue35();
                  float floatValue33 = clickGuiState20.getFloatValue36();
                  float floatValue34 = clickGuiState20.getFloatValue37();
                  if (floatValue33 > 1.0F && floatValue34 > 1.0F) {
                     list.add(
                        this.resolve2(
                              ClickGuiHitTarget.resolve()
                                 .setIntValue(0)
                                 .setFloatValue(floatValue31)
                                 .setFloatValue2(floatValue32)
                                 .setFloatValue3(floatValue33)
                                 .setFloatValue4(floatValue34)
                                 .setClickGuiAction(
                                    clickGuiState22 -> this.clickGuiSettingController.invoke2(clickGuiState22, colorSetting, clickGuiState22.getFloatValue(), clickGuiState22.getFloatValue2())
                                 ),
                              g,
                              h,
                              i,
                              j
                           )
                           .resolve()
                     );
                  }

                  float floatValue35 = clickGuiState20.getFloatValue38();
                  float floatValue36 = clickGuiState20.getFloatValue39();
                  float floatValue37 = clickGuiState20.getFloatValue40();
                  float floatValue38 = clickGuiState20.getFloatValue41();
                  if (floatValue37 > 1.0F && floatValue38 > 1.0F) {
                     list.add(
                        this.resolve2(
                              ClickGuiHitTarget.resolve()
                                 .setIntValue(0)
                                 .setFloatValue(floatValue35)
                                 .setFloatValue2(floatValue36)
                                 .setFloatValue3(floatValue37)
                                 .setFloatValue4(floatValue38)
                                 .setClickGuiAction(
                                    clickGuiState23 -> this.clickGuiSettingController
                                       .invoke3(clickGuiState23, colorSetting, clickGuiState23.getFloatValue(), clickGuiState23.getFloatValue2())
                                 ),
                              g,
                              h,
                              i,
                              j
                           )
                           .resolve()
                     );
                  }

                  float floatValue39 = clickGuiState20.getFloatValue42();
                  float floatValue40 = clickGuiState20.getFloatValue43();
                  float floatValue41 = clickGuiState20.getFloatValue44();
                  float floatValue42 = clickGuiState20.getFloatValue45();
                  if (floatValue41 > 1.0F && floatValue42 > 1.0F) {
                     list.add(
                        this.resolve2(
                              ClickGuiHitTarget.resolve()
                                 .setIntValue(0)
                                 .setFloatValue(floatValue39)
                                 .setFloatValue2(floatValue40)
                                 .setFloatValue3(floatValue41)
                                 .setFloatValue4(floatValue42)
                                 .setClickGuiAction(clickGuiState24 -> this.clickGuiSettingController.invoke4(clickGuiState24, colorSetting, clickGuiState24.getFloatValue())),
                              g,
                              h,
                              i,
                              j
                           )
                           .resolve()
                     );
                  }

                  float floatValue43 = clickGuiState20.getFloatValue46();
                  float floatValue44 = clickGuiState20.getFloatValue47();
                  float floatValue45 = clickGuiState20.getFloatValue48();
                  float floatValue46 = clickGuiState20.getFloatValue49();
                  if (floatValue45 > 1.0F && floatValue46 > 1.0F) {
                     list.add(
                        this.resolve2(
                              ClickGuiHitTarget.resolve()
                                 .setIntValue(0)
                                 .setFloatValue(floatValue43)
                                 .setFloatValue2(floatValue44)
                                 .setFloatValue3(floatValue45)
                                 .setFloatValue4(floatValue46)
                                 .setClickGuiAction(clickGuiState25 -> this.clickGuiSettingController.invoke5(clickGuiState25, colorSetting, clickGuiState25.getFloatValue())),
                              g,
                              h,
                              i,
                              j
                           )
                           .resolve()
                     );
                  }

                  float floatValue47 = clickGuiState20.getFloatValue50();
                  float floatValue48 = clickGuiState20.getFloatValue51();
                  float floatValue49 = clickGuiState20.getFloatValue52();
                  float floatValue50 = clickGuiState20.getFloatValue53();
                  if (floatValue49 > 1.0F && floatValue50 > 1.0F) {
                     list.add(
                        this.resolve2(
                              ClickGuiHitTarget.resolve()
                                 .setIntValue(0)
                                 .setFloatValue(floatValue47)
                                 .setFloatValue2(floatValue48)
                                 .setFloatValue3(floatValue49)
                                 .setFloatValue4(floatValue50)
                                 .setClickGuiAction(clickGuiState26 -> this.clickGuiSettingController.invoke6(clickGuiState26, colorSetting, clickGuiState26.getFloatValue(), false)),
                              g,
                              h,
                              i,
                              j
                           )
                           .resolve()
                     );
                     list.add(
                        this.resolve2(
                              ClickGuiHitTarget.resolve()
                                 .setIntValue(1)
                                 .setFloatValue(floatValue47)
                                 .setFloatValue2(floatValue48)
                                 .setFloatValue3(floatValue49)
                                 .setFloatValue4(floatValue50)
                                 .setClickGuiAction(clickGuiState27 -> this.clickGuiSettingController.invoke6(clickGuiState27, colorSetting, clickGuiState27.getFloatValue(), true)),
                              g,
                              h,
                              i,
                              j
                           )
                           .resolve()
                     );
                  }

                  float floatValue51 = clickGuiState20.getFloatValue58();
                  float floatValue52 = clickGuiState20.getFloatValue55();
                  float floatValue53 = clickGuiState20.getFloatValue59();
                  float floatValue54 = clickGuiState20.getFloatValue57();
                  if (floatValue53 > 1.0F && floatValue54 > 1.0F) {
                     list.add(
                        this.resolve2(
                              ClickGuiHitTarget.resolve()
                                 .setIntValue(0)
                                 .setFloatValue(floatValue51)
                                 .setFloatValue2(floatValue52)
                                 .setFloatValue3(floatValue53)
                                 .setFloatValue4(floatValue54)
                                 .setClickGuiAction(clickGuiState28 -> {
                                    colorSetting.invoke2(clickGuiState28.compute(colorSetting));
                                    clickGuiState28.invoke39();
                                    clickGuiState28.invoke66();
                                 }),
                              g,
                              h,
                              i,
                              j
                           )
                           .resolve()
                     );
                  }

                  float floatValue55 = clickGuiState20.getFloatValue60();
                  float floatValue56 = clickGuiState20.getDynamicButtonSetting();
                  float floatValue57 = clickGuiState20.getFloatValue61();
                  float floatValue58 = clickGuiState20.getFloatValue62();
                  if (floatValue57 > 1.0F && floatValue58 > 1.0F) {
                     list.add(
                        this.resolve2(
                              ClickGuiHitTarget.resolve()
                                 .setIntValue(0)
                                 .setFloatValue(floatValue55)
                                 .setFloatValue2(floatValue56)
                                 .setFloatValue3(floatValue57)
                                 .setFloatValue4(floatValue58)
                                 .setClickGuiAction(clickGuiState29 -> {
                                    clickGuiState29.setColorSetting4((ColorSetting)null);
                                    clickGuiState29.setText5("");
                                    clickGuiState29.setColorSetting3(colorSetting);
                                    clickGuiState29.setText4(String.format("%06X", colorSetting.compute2() & 16777215));
                                 }),
                              g,
                              h,
                              i,
                              j
                           )
                           .resolve()
                     );
                  }

                  float floatValue59 = clickGuiState20.getFloatValue63();
                  float floatValue60 = clickGuiState20.getFloatValue64();
                  float floatValue61 = clickGuiState20.getSpacerSetting();
                  float floatValue62 = clickGuiState20.getFoundryShaderSetting2();
                  if (floatValue61 > 1.0F && floatValue62 > 1.0F) {
                     list.add(
                        this.resolve2(
                              ClickGuiHitTarget.resolve()
                                 .setIntValue(0)
                                 .setFloatValue(floatValue59)
                                 .setFloatValue2(floatValue60)
                                 .setFloatValue3(floatValue61)
                                 .setFloatValue4(floatValue62)
                                 .setClickGuiAction(clickGuiState30 -> {
                                    clickGuiState30.setColorSetting3((ColorSetting)null);
                                    clickGuiState30.setText4("");
                                    clickGuiState30.setColorSetting4(colorSetting);
                                    clickGuiState30.setText5(Integer.toString(Math.round(colorSetting.floatValue3 * 100.0F)));
                                 }),
                              g,
                              h,
                              i,
                              j
                           )
                           .resolve()
                     );
                  }
               } else if (setting instanceof GroupSetting groupSetting) {
                  this.invoke18(list, groupSetting, floatValue23, floatValue24 + floatValue29, floatValue25, floatValue27, metrics18, g, h, i, j);
               } else if (setting instanceof FoundryShaderSetting foundryShaderSetting) {
                  float floatValue63 = SettingsRenderer.measure13(foundryShaderSetting, floatValue23, floatValue25, metrics18);
                  float floatValue64 = SettingsRenderer.measure14(floatValue24 + floatValue29, metrics18);
                  list.add(
                     this.resolve2(
                           ClickGuiHitTarget.resolve()
                              .setIntValue(0)
                              .setFloatValue(floatValue63)
                              .setFloatValue2(floatValue64)
                              .setFloatValue3(SettingsRenderer.measure12(foundryShaderSetting, floatValue25, metrics18))
                              .setFloatValue4(SettingsRenderer.measure15(metrics18))
                              .setClickGuiAction(clickGuiState31 -> clickGuiState31.invoke34(foundryShaderSetting)),
                           g,
                           h,
                           i,
                           j
                        )
                        .resolve()
                  );
                  if (foundryShaderSetting.expanded) {
                     this.invoke20(list, foundryShaderSetting, floatValue23, floatValue24 + floatValue29, floatValue25, metrics18, g, h, i, j);
                  }
               } else if (setting instanceof ModeSetting modeSetting) {
                  float floatValue65 = SettingsRenderer.measure7(modeSetting, floatValue23, floatValue25, metrics18);
                  float floatValue66 = SettingsRenderer.measure8(floatValue24 + floatValue29, metrics18);
                  list.add(
                     this.resolve2(
                           ClickGuiHitTarget.resolve()
                              .setIntValue(0)
                              .setFloatValue(floatValue65)
                              .setFloatValue2(floatValue66)
                              .setFloatValue3(SettingsRenderer.measure6(modeSetting, floatValue25, metrics18))
                              .setFloatValue4(SettingsRenderer.measure9(metrics18))
                              .setClickGuiAction(clickGuiState32 -> clickGuiState32.invoke31(modeSetting)),
                           g,
                           h,
                           i,
                           j
                        )
                        .resolve()
                  );
                  if (modeSetting.expanded) {
                     this.invoke19(list, modeSetting, floatValue23, floatValue24 + floatValue29, floatValue25, metrics18, g, h, i, j);
                  }
               } else if (setting instanceof BooleanSetting booleanSetting) {
                  float floatValue67 = SettingsRenderer.measure20(metrics18);
                  list.add(
                     this.resolve2(
                           ClickGuiHitTarget.resolve()
                              .setIntValue(0)
                              .setFloatValue(SettingsRenderer.measure18(floatValue23, floatValue25, metrics18))
                              .setFloatValue2(SettingsRenderer.measure19(floatValue24 + floatValue29, metrics18))
                              .setFloatValue3(floatValue67)
                              .setFloatValue4(floatValue67)
                              .setClickGuiAction(clickGuiState33 -> this.clickGuiSettingController.invoke(clickGuiState33, setting, f, floatValue23, floatValue25)),
                           g,
                           h,
                           i,
                           j
                        )
                        .resolve()
                  );
                  list.add(
                     this.resolve2(
                           ClickGuiHitTarget.resolve()
                              .setIntValue(2)
                              .setFloatValue(floatValue23)
                              .setFloatValue2(floatValue24 + floatValue29 - metrics18.measure(2.0F))
                              .setFloatValue3(floatValue25)
                              .setFloatValue4(floatValue27 + metrics18.measure(4.0F))
                              .setClickGuiAction(clickGuiState34 -> clickGuiState34.invoke46(booleanSetting)),
                           g,
                           h,
                           i,
                           j
                        )
                        .resolve()
                  );
                  list.add(
                     this.resolve2(
                           ClickGuiHitTarget.resolve()
                              .setIntValue(1)
                              .setFloatValue(floatValue23)
                              .setFloatValue2(floatValue24 + floatValue29 - metrics18.measure(2.0F))
                              .setFloatValue3(floatValue25)
                              .setFloatValue4(floatValue27 + metrics18.measure(4.0F))
                              .setClickGuiAction(clickGuiState35 -> {
                                 if (booleanSetting.bindKey != -1) {
                                    booleanSetting.holdToEnable = !booleanSetting.holdToEnable;
                                    clickGuiState35.invoke66();
                                 }
                              }),
                           g,
                           h,
                           i,
                           j
                        )
                        .resolve()
                  );
               } else if (setting instanceof ColorSetting) {
                  float floatValue68 = SettingsRenderer.measure23(metrics18);
                  list.add(
                     this.resolve2(
                           ClickGuiHitTarget.resolve()
                              .setIntValue(0)
                              .setFloatValue(SettingsRenderer.measure21(floatValue23, floatValue25, metrics18))
                              .setFloatValue2(SettingsRenderer.measure22(floatValue24 + floatValue29, metrics18))
                              .setFloatValue3(floatValue68)
                              .setFloatValue4(floatValue68)
                              .setClickGuiAction(clickGuiState36 -> this.clickGuiSettingController.invoke(clickGuiState36, setting, f, floatValue23, floatValue25)),
                           g,
                           h,
                           i,
                           j
                        )
                        .resolve()
                  );
               } else if (setting instanceof NumberSetting) {
                  list.add(
                     this.resolve2(
                           ClickGuiHitTarget.resolve()
                              .setIntValue(0)
                              .setFloatValue(floatValue23)
                              .setFloatValue2(floatValue24 + floatValue29 + metrics18.measure(3.0F))
                              .setFloatValue3(floatValue25)
                              .setFloatValue4(metrics18.measure(26.0F))
                              .setClickGuiAction(clickGuiState37 -> this.clickGuiSettingController.invoke(clickGuiState37, setting, f, floatValue23, floatValue25)),
                           g,
                           h,
                           i,
                           j
                        )
                        .resolve()
                  );
               } else {
                  list.add(
                     this.resolve2(
                           ClickGuiHitTarget.resolve()
                              .setIntValue(0)
                              .setFloatValue(floatValue23)
                              .setFloatValue2(floatValue24 + floatValue29 - metrics18.measure(2.0F))
                              .setFloatValue3(floatValue25)
                              .setFloatValue4(floatValue27 + metrics18.measure(4.0F))
                              .setClickGuiAction(clickGuiState38 -> this.clickGuiSettingController.invoke(clickGuiState38, setting, f, floatValue23, floatValue25)),
                           g,
                           h,
                           i,
                           j
                        )
                        .resolve()
                  );
               }

               floatValue24 += (floatValue27 + floatValue28 + metrics18.measure(12.0F)) * floatValue26;
            }
         }
      }
   }

   private void invoke18(
      List<ClickGuiHitTarget> list, GroupSetting groupSetting2, float f, float g, float h, float i, Metrics metrics19, float j, float k, float l, float m
   ) {
      float floatValue69 = h * 0.7F;
      float floatValue70 = f + h - floatValue69;
      float floatValue71 = metrics19.measure(3.0F);
      float floatValue72 = metrics19.measure(14.0F);
      float floatValue73 = metrics19.measure(3.0F);
      float floatValue74 = 0.0F;
      int intValue4 = 0;

      for (int intValue5 = 0; intValue5 < groupSetting2.options.size(); intValue5++) {
         BooleanSetting booleanSetting2 = groupSetting2.options.get(intValue5);
         float floatValue75 = ClickGuiRenderUtils.measure(FontRegistry.fontObject, ClickGuiRenderUtils.resolve(booleanSetting2), 8.0F);
         float floatValue76 = Math.max(metrics19.measure(18.0F), floatValue75 + metrics19.measure(8.0F));
         if (floatValue74 > 0.0F && floatValue74 + floatValue76 > floatValue69) {
            intValue4++;
            floatValue74 = 0.0F;
         }

         float floatValue77 = floatValue70 + floatValue74;
         float floatValue78 = g + metrics19.measure(1.0F) + intValue4 * (floatValue72 + floatValue73);
         list.add(
            this.resolve2(
                  ClickGuiHitTarget.resolve()
                     .setIntValue(0)
                     .setFloatValue(floatValue77)
                     .setFloatValue2(floatValue78 - metrics19.measure(1.0F))
                     .setFloatValue3(floatValue76)
                     .setFloatValue4(floatValue72 + metrics19.measure(2.0F))
                     .setClickGuiAction(clickGuiState39 -> {
                        booleanSetting2.setValue(!booleanSetting2.getValue());
                        clickGuiState39.invoke66();
                     }),
                  j,
                  k,
                  l,
                  m
               )
               .resolve()
         );
         list.add(
            this.resolve2(
                  ClickGuiHitTarget.resolve()
                     .setIntValue(2)
                     .setFloatValue(floatValue77)
                     .setFloatValue2(floatValue78 - metrics19.measure(1.0F))
                     .setFloatValue3(floatValue76)
                     .setFloatValue4(floatValue72 + metrics19.measure(2.0F))
                     .setClickGuiAction(clickGuiState40 -> clickGuiState40.invoke46(booleanSetting2)),
                  j,
                  k,
                  l,
                  m
               )
               .resolve()
         );
         list.add(
            this.resolve2(
                  ClickGuiHitTarget.resolve()
                     .setIntValue(1)
                     .setFloatValue(floatValue77)
                     .setFloatValue2(floatValue78 - metrics19.measure(1.0F))
                     .setFloatValue3(floatValue76)
                     .setFloatValue4(floatValue72 + metrics19.measure(2.0F))
                     .setClickGuiAction(clickGuiState41 -> {
                        if (booleanSetting2.bindKey != -1) {
                           booleanSetting2.holdToEnable = !booleanSetting2.holdToEnable;
                           clickGuiState41.invoke66();
                        }
                     }),
                  j,
                  k,
                  l,
                  m
               )
               .resolve()
         );
         floatValue74 += floatValue76 + floatValue71;
      }
   }

   private void invoke19(
      List<ClickGuiHitTarget> list, ModeSetting modeSetting2, float f, float g, float h, Metrics metrics20, float i, float j, float k, float l
   ) {
      float floatValue79 = SettingsRenderer.measure4(h);
      float floatValue80 = SettingsRenderer.measure5(f, h);
      float floatValue81 = g + metrics20.measure(14.0F) + metrics20.measure(4.0F);
      float floatValue82 = metrics20.measure(18.0F);

      for (int intValue6 = 0; intValue6 < modeSetting2.options.size(); intValue6++) {
         int intValue7 = intValue6;
         float floatValue83 = floatValue81 + metrics20.measure(2.0F) + intValue6 * floatValue82;
         list.add(
            this.resolve2(
                  ClickGuiHitTarget.resolve()
                     .setIntValue(0)
                     .setFloatValue(floatValue80)
                     .setFloatValue2(floatValue83)
                     .setFloatValue3(floatValue79)
                     .setFloatValue4(floatValue82)
                     .setClickGuiAction(clickGuiState42 -> clickGuiState42.invoke32(modeSetting2, intValue7)),
                  i,
                  j,
                  k,
                  l
               )
               .resolve()
         );
      }
   }

   private void invoke20(
      List<ClickGuiHitTarget> list, FoundryShaderSetting foundryShaderSetting2, float f, float g, float h, Metrics metrics21, float i, float j, float k, float l
   ) {
      foundryShaderSetting2.refreshOptions();
      float floatValue84 = SettingsRenderer.measure10(h);
      float floatValue85 = SettingsRenderer.measure11(f, h);
      float floatValue86 = g + metrics21.measure(18.0F) + metrics21.measure(5.0F);
      float floatValue87 = SettingsRenderer.measure17(metrics21);
      float floatValue88 = metrics21.measure(4.0F);

      for (int intValue8 = 0; intValue8 < foundryShaderSetting2.options.size(); intValue8++) {
         int intValue9 = intValue8;
         float floatValue89 = floatValue86 + floatValue88 + intValue8 * floatValue87;
         list.add(
            this.resolve2(
                  ClickGuiHitTarget.resolve()
                     .setIntValue(0)
                     .setFloatValue(floatValue85)
                     .setFloatValue2(floatValue89)
                     .setFloatValue3(floatValue84)
                     .setFloatValue4(floatValue87)
                     .setClickGuiAction(clickGuiState43 -> clickGuiState43.invoke35(foundryShaderSetting2, intValue9)),
                  i,
                  j,
                  k,
                  l
               )
               .resolve()
         );
      }
   }

   private ClickGuiHitTarget.ClickGuiHitTargetState resolve2(ClickGuiHitTarget.ClickGuiHitTargetState clickGuiHitTargetState, float f, float g, float h, float i) {
      return clickGuiHitTargetState.setFloatValue5(f).setFloatValue6(g).setFloatValue7(h).setFloatValue8(i);
   }

   private boolean check(ClickGuiGeometry clickGuiGeometry17, Metrics metrics22, float f, float g) {
      return ClickGuiRenderUtils.check2(f, g, clickGuiGeometry17.getFloatValue(), clickGuiGeometry17.getFloatValue2(), metrics22.getFloatValue3(), metrics22.getFloatValue4());
   }

   @Generated
   public ClickGuiHitMapBuilder(ModuleLayoutEngine moduleLayoutEngine, ClickGuiSettingController clickGuiSettingController, CoreDiagnosticsPanelRenderer coreDiagnosticsPanelRenderer) {
      this.moduleLayoutEngine = moduleLayoutEngine;
      this.clickGuiSettingController = clickGuiSettingController;
      this.coreDiagnosticsPanelRenderer = coreDiagnosticsPanelRenderer;
   }
}
