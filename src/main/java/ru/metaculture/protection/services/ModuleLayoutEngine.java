package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.List;
import org.wild.module.api.Module;

public final class ModuleLayoutEngine {
   public ModuleLayoutResult resolve(ClickGuiState clickGuiState, ClickGuiGeometry clickGuiGeometry, Metrics metrics) {
      List items = clickGuiState.resolve2();
      ArrayList arrayList = new ArrayList(items.size());
      float[] floatValues = new float[]{0.0F, 0.0F};
      int intValue = 0;

      for (int intValue2 = 0; intValue2 < items.size(); intValue2++) {
         Module module2 = (Module)items.get(intValue2);
         SpecialModuleCardHandler specialModuleCardHandler = SpecialModuleCardHandlers.resolve(module2);
         boolean flag = specialModuleCardHandler != null && specialModuleCardHandler.check2(module2, clickGuiState);
         int intValue3 = flag ? -1 : intValue % 2;
         float floatValue = flag ? Math.max(floatValues[0], floatValues[1]) : floatValues[intValue3];
         float floatValue2 = flag ? clickGuiGeometry.getFloatValue19() : (intValue3 == 0 ? clickGuiGeometry.getFloatValue19() : clickGuiGeometry.getFloatValue20());
         float floatValue3 = clickGuiGeometry.getFloatValue16() + clickGuiState.getFloatValue11() + floatValue;
         float floatValue4 = flag ? metrics.getFloatValue14() * 2.0F + metrics.getFloatValue6() : metrics.getFloatValue14();
         float floatValue5 = clickGuiState.measure7(AnimationKeyRegistry.resolve15(module2));
         float floatValue6 = this.measure2(module2, metrics, clickGuiState) * floatValue5;
         float floatValue7 = this.measure3(module2, floatValue4, metrics);
         float floatValue8 = floatValue7 + floatValue6;
         arrayList.add(new ModulePlacement(module2, floatValue2, floatValue3, floatValue4, floatValue8, floatValue6));
         if (flag) {
            float floatValue9 = floatValue + floatValue8 + metrics.getFloatValue16();
            floatValues[0] = floatValue9;
            floatValues[1] = floatValue9;
         } else {
            floatValues[intValue3] += floatValue8 + metrics.getFloatValue16();
            intValue++;
         }
      }

      float floatValue10 = Math.max(0.0F, Math.max(floatValues[0], floatValues[1]) - metrics.getFloatValue16());
      if (!arrayList.isEmpty()) {
         floatValue10 += this.measure(metrics, clickGuiGeometry, floatValue10);
      }

      float floatValue11 = Math.max(0.0F, floatValue10 - clickGuiGeometry.getFloatValue18());
      return new ModuleLayoutResult(arrayList, floatValue11);
   }

   private float measure(Metrics metrics2, ClickGuiGeometry clickGuiGeometry2, float f) {
      float floatValue12 = Math.max(metrics2.getFloatValue13(), metrics2.getFloatValue16() * 2.0F);
      float floatValue13 = clickGuiGeometry2.getFloatValue18() - f;
      return floatValue13 >= floatValue12 ? 0.0F : floatValue12;
   }

   public float measure2(Module module, Metrics metrics3, ClickGuiState clickGuiState2) {
      SpecialModuleCardHandler specialModuleCardHandler2 = SpecialModuleCardHandlers.resolve(module);
      if (specialModuleCardHandler2 != null) {
         return specialModuleCardHandler2.measure(module, metrics3, clickGuiState2);
      } else {
         float floatValue14 = metrics3.measure(1.0F) + metrics3.measure(20.0F);
         List items2 = module.getVisibleSettings();

         for (int intValue4 = 0; intValue4 < items2.size(); intValue4++) {
            Setting setting = (Setting)items2.get(intValue4);
            if (setting instanceof SpacerSetting spacerSetting) {
               floatValue14 += metrics3.measure(spacerSetting.getFloatValue());
            } else {
               float floatValue15 = clickGuiState2.measure7(AnimationKeyRegistry.resolve22(setting));
               float floatValue16 = this.measure4(setting, metrics3, clickGuiState2);
               float floatValue17 = this.measure6(setting, clickGuiState2, metrics3);
               floatValue14 += (floatValue16 + floatValue17) * floatValue15;
               if (intValue4 < items2.size() - 1) {
                  floatValue14 += metrics3.measure(12.0F) * floatValue15;
               }
            }
         }

         return floatValue14;
      }
   }

   public float measure3(Module module, float f, Metrics metrics4) {
      String text = module.description == null ? "" : module.description;
      if (text.isBlank()) {
         return metrics4.getFloatValue15();
      } else {
         float floatValue18 = Math.max(metrics4.measure(160.0F), f - metrics4.measure(90.0F));
         int intValue5 = ClickGuiRenderUtils.resolve2(FontRegistry.fontObject, text, 10.0F, floatValue18, 10).size();
         float floatValue19 = metrics4.measure(54.0F) + Math.max(1, intValue5) * metrics4.measure(12.0F);
         return Math.max(metrics4.getFloatValue15(), floatValue19);
      }
   }

   public float measure4(Setting setting2, Metrics metrics5, ClickGuiState clickGuiState3) {
      if (setting2 instanceof NumberSetting) {
         return metrics5.measure(22.0F);
      } else if (setting2 instanceof FoundryShaderSetting) {
         return metrics5.measure(18.0F);
      } else if (setting2 instanceof ColorSetting colorSetting) {
         float floatValue20 = clickGuiState3.measure7(AnimationKeyRegistry.resolve37(colorSetting));
         float floatValue21 = metrics5.measure(16.0F);
         float floatValue22 = metrics5.measure(186.0F);
         return floatValue21 + floatValue22 * floatValue20;
      } else if (setting2 instanceof SpacerSetting spacerSetting2) {
         return metrics5.measure(spacerSetting2.getFloatValue());
      } else if (setting2 instanceof GroupSetting groupSetting) {
         float floatValue23 = metrics5.getFloatValue14() - metrics5.measure(32.0F);
         float floatValue24 = floatValue23 * 0.7F;
         int intValue6 = ClickGuiRenderUtils.compute16(groupSetting, floatValue24, metrics5);
         float floatValue25 = metrics5.measure(14.0F);
         float floatValue26 = metrics5.measure(3.0F);
         return metrics5.measure(1.0F) + intValue6 * floatValue25 + (intValue6 > 1 ? (intValue6 - 1) * floatValue26 : 0.0F) + metrics5.measure(1.0F);
      } else {
         return metrics5.measure(14.0F);
      }
   }

   public float measure5(Setting setting3, Metrics metrics6) {
      if (setting3 instanceof NumberSetting || setting3 instanceof ColorSetting) {
         return metrics6.measure(22.0F);
      } else if (setting3 instanceof FoundryShaderSetting) {
         return metrics6.measure(18.0F);
      } else if (setting3 instanceof SpacerSetting spacerSetting3) {
         return metrics6.measure(spacerSetting3.getFloatValue());
      } else if (setting3 instanceof GroupSetting groupSetting2) {
         float floatValue27 = metrics6.getFloatValue14() - metrics6.measure(32.0F);
         float floatValue28 = floatValue27 * 0.7F;
         int intValue7 = ClickGuiRenderUtils.compute16(groupSetting2, floatValue28, metrics6);
         float floatValue29 = metrics6.measure(14.0F);
         float floatValue30 = metrics6.measure(3.0F);
         return metrics6.measure(1.0F) + intValue7 * floatValue29 + (intValue7 > 1 ? (intValue7 - 1) * floatValue30 : 0.0F) + metrics6.measure(1.0F);
      } else {
         return metrics6.measure(14.0F);
      }
   }

   public float measure6(Setting setting4, ClickGuiState clickGuiState4, Metrics metrics7) {
      if (setting4 instanceof ModeSetting modeSetting) {
         float floatValue31 = clickGuiState4.measure7(AnimationKeyRegistry.resolve30(modeSetting));
         if (floatValue31 > 0.01F) {
            float floatValue32 = metrics7.measure(6.0F) + modeSetting.options.size() * metrics7.measure(18.0F) + metrics7.measure(4.0F);
            return floatValue32 * floatValue31;
         }
      }

      if (setting4 instanceof FoundryShaderSetting foundryShaderSetting) {
         float floatValue33 = clickGuiState4.measure7(AnimationKeyRegistry.resolve30(foundryShaderSetting));
         if (floatValue33 > 0.01F) {
            return SettingsRenderer.measure16(foundryShaderSetting, metrics7) * floatValue33;
         }
      }

      return 0.0F;
   }
}
