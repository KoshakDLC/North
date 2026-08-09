package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.wild.module.api.Module;

final class LegacySettingListLayout {
   private static final float FLOAT_VALUE = 20.0F;
   private static final float FLOAT_VALUE_2 = 8.0F;
   private static final float FLOAT_VALUE_3 = 4.0F;
   private static final float FLOAT_VALUE_4 = 20.0F;
   private static final float FLOAT_VALUE_5 = 4.0F;
   private static final float FLOAT_VALUE_6 = 4.0F;
   private static final float FLOAT_VALUE_7 = 4.0F;
   private final Category category;
   private final TypeUtils typeUtils = new TypeUtils();
   private float floatValue;
   private float floatValue2;
   private float floatValue3;
   private float floatValue4;

   LegacySettingListLayout(Category category) {
      this.category = category;
   }

   void invoke(float f, float g, float h, float i) {
      this.floatValue = f;
      this.floatValue2 = g;
      this.floatValue3 = h;
      this.floatValue4 = i;
   }

   void invoke2(RenderManager renderManager, int i, int j, float f) {
      int intValue = RenderManager.RenderManagerState.compute24(RenderManager.RenderManagerState.compute5(1, 1), (int)(18.0F * f));
      int intValue2 = RenderManager.RenderManagerState.compute24(RenderManager.RenderManagerState.compute3(1, 1), (int)(108.0F * f));
      int intValue4 = RenderManager.RenderManagerState.compute24(RenderManager.RenderManagerState.compute6(1, 1), (int)(220.0F * f));
      int intValue5 = RenderManager.RenderManagerState.compute24(RenderManager.RenderManagerState.compute6(1, 1), (int)(108.0F * f));
      int intValue6 = RenderManager.RenderManagerState.compute24(RenderManager.RenderManagerState.compute7(1, 1), (int)(236.0F * f));
      int intValuePremium = RenderManager.RenderManagerState.compute24(RenderManager.RenderManagerState.compute6(1, 1), (int)(78.0F * f));
      int accent = RenderManager.RenderManagerState.compute24(RenderManager.RenderManagerState.compute6(1, 1), (int)(255.0F * f));
      float radius = 12.0F;
      float rowRadius = 6.0F;
      boolean glass = LegacyClickGuiState.blyurNada.isEnabled();
      // liquid glass: одна размытая текстура на кадр (invoke48), панель лишь сэмплирует её — дёшево
      if (glass) {
         renderManager.invoke44(this.floatValue, this.floatValue2, this.floatValue3, this.floatValue4, radius, f);
      }

      int panelFill = RenderManager.RenderManagerState.compute24(RenderManager.RenderManagerState.compute4(1, 1), (int)((glass ? 118.0F : 216.0F) * f));
      renderManager.invoke28(this.floatValue, this.floatValue2 + 1.5F, this.floatValue3, this.floatValue4, radius, RenderManager.RenderManagerState.compute37(0, 0, 0, (int)(55.0F * f)), 1.2F);
      renderManager.invoke5(this.floatValue, this.floatValue2, this.floatValue3, this.floatValue4, radius, panelFill);
      renderManager.invoke24(this.floatValue, this.floatValue2, this.floatValue3, this.floatValue4, radius, radius, radius, radius);
      renderManager.invoke37(
         this.floatValue,
         this.floatValue2,
         this.floatValue3,
         this.floatValue4 * 0.42F,
         0.0F,
         RenderManager.RenderManagerState.compute37(255, 255, 255, (int)(24.0F * f)),
         RenderManager.RenderManagerState.compute37(255, 255, 255, 0)
      );
      renderManager.invoke5(
         this.floatValue + radius * 0.6F,
         this.floatValue2,
         this.floatValue3 - radius * 1.2F,
         1.0F,
         0.5F,
         RenderManager.RenderManagerState.compute37(255, 255, 255, (int)(52.0F * f))
      );
      renderManager.invoke25();
      renderManager.invoke28(this.floatValue, this.floatValue2, this.floatValue3, this.floatValue4, radius, RenderManager.RenderManagerState.compute37(255, 255, 255, (int)(28.0F * f)), 1.0F);
      renderManager.invoke5(
         this.floatValue + 10.0F,
         this.floatValue2 + 19.0F,
         this.floatValue3 - 20.0F,
         1.0F,
         0.5F,
         RenderManager.RenderManagerState.compute37(255, 255, 255, (int)(20.0F * f))
      );
      float headerTextWidth = RenderManager.resolve7(FontRegistry.fontObject4, this.category.getDisplayName(), 14.0F).floatValue;
      renderManager.invoke69(
         FontRegistry.fontObject4,
         this.floatValue + (this.floatValue3 - headerTextWidth) * 0.5F,
         this.floatValue2 + 6.0F + 6.5F,
         14.0F,
         this.category.getDisplayName(),
         intValue6
      );
      float floatValue = this.floatValue + 4.0F;
      float floatValue2 = this.floatValue2 + 20.0F + 4.0F;
      float floatValue3 = this.floatValue3 - 8.0F;
      float floatValue4 = this.floatValue4 - 20.0F - 8.0F;
      boolean flag = RenderMath.check(i, j, floatValue, floatValue2, floatValue3, floatValue4);
      this.typeUtils.setFlag(flag);
      this.typeUtils.setFloatValue4(6.0F);
      List items = this.resolve();
      float floatValue5 = 0.0F;

      for (Module module2 : (List<Module>)items) {
         EasedAnimation easedAnimation = LegacyClickGuiScreenController.resolve(module2);
         easedAnimation.check();
         float floatValue6 = LegacySettingValueFormatter.measure2(renderManager, module2.getVisibleSettings(), floatValue3 - 10.0F);
         float floatValue7 = floatValue6 > 0.0F ? floatValue6 + 4.0F : 0.0F;
         float floatValue8 = floatValue7 * easedAnimation.measure3();
         floatValue5 += 20.0F + floatValue8 + 4.0F;
      }

      floatValue5 = Math.max(0.0F, floatValue5 - 4.0F);
      this.typeUtils.invoke7(floatValue5, floatValue4);
      this.typeUtils.invoke();
      renderManager.invoke24(floatValue, floatValue2, floatValue3, floatValue4, 0.0F, 0.0F, 0.0F, 0.0F);
      float floatValue9 = floatValue2 + this.typeUtils.measure();

      for (Module module3 : (List<Module>)items) {
         EasedAnimation easedAnimation2 = LegacyClickGuiScreenController.resolve(module3);
         float floatValue10 = easedAnimation2.measure3();
         float floatValue11 = LegacySettingValueFormatter.measure2(renderManager, module3.getVisibleSettings(), floatValue3 - 10.0F);
         float floatValue12 = floatValue11 > 0.0F ? floatValue11 + 4.0F : 0.0F;
         float floatValue13 = floatValue12 * floatValue10;
         float floatValue14 = 20.0F + floatValue13;
         if (!(floatValue9 + floatValue14 < floatValue2 - 20.0F) && !(floatValue9 > floatValue2 + floatValue4 + 20.0F)) {
            boolean hasSettings = !module3.getVisibleSettings().isEmpty();
            int intValue7 = module3.enabled ? ColorUtils.compute35(intValue2, intValue4, 0.38F) : intValue2;
            renderManager.invoke5(floatValue, floatValue9, floatValue3, floatValue14, rowRadius, intValue7);
            if (module3.enabled) {
               renderManager.invoke5(floatValue, floatValue9 + 2.0F, 2.0F, floatValue14 - 4.0F, 1.0F, accent);
            }

            float floatValue15 = floatValue9 + 5.0F + 6.5F;
            float nameX = floatValue + (module3.enabled ? 8.0F : 6.0F);
            renderManager.invoke69(FontRegistry.fontObject, nameX, floatValue15, 12.0F, module3.name, module3.enabled ? intValue6 : intValue5);
            float nameEndX = nameX + RenderManager.resolve7(FontRegistry.fontObject, module3.name, 12.0F).floatValue + 4.0F;
            if (module3.hasRiskLevel(ModuleRiskLevel.VIP)) {
               renderManager.invoke69(FontRegistry.fontObject, nameEndX, floatValue15, 8.0F, "PREMIUM", intValuePremium);
            }

            if (hasSettings || module3.expanded || module3.bindKey != -1) {
               String text = module3.expanded
                  ? "..."
                  : module3.bindKey != -1 && !hasSettings ? KeyCodeUtils.resolve(module3.bindKey) : "...";
               float floatValue16 = RenderManager.resolve7(FontRegistry.fontObject, text, 10.0F).floatValue + 4.0F;
               float floatValue17 = floatValue + floatValue3 - floatValue16 - 4.0F;
               renderManager.invoke69(FontRegistry.fontObject4, floatValue17 + 2.0F, floatValue9 + 4.0F + 6.8F, 10.0F, text, intValue5);
            }

            if (floatValue13 > 0.5F && floatValue11 > 0.0F) {
               float floatValue20 = floatValue + 5.0F;
               float floatValue21 = floatValue9 + 20.0F + 2.0F;
               float floatValue22 = floatValue3 - 10.0F;
               renderManager.invoke24(floatValue, floatValue9 + 20.0F, floatValue3, floatValue13, 0.0F, 0.0F, 4.0F, 4.0F);
               float floatValue23 = 0.0F;

               for (Setting setting : module3.getVisibleSettings()) {
                  if (!setting.visibilityCondition.get()) {
                     float floatValue24 = LegacySettingValueFormatter.measure3(renderManager, setting, floatValue20, floatValue21 + floatValue23, floatValue22, i, j, f * floatValue10, intValue, intValue4, intValue5, intValue6, intValue2);
                     floatValue23 += floatValue24 + LegacySettingValueFormatter.measure4();
                  }
               }

               renderManager.invoke25();
            }

            floatValue9 += floatValue14 + 4.0F;
         } else {
            floatValue9 += floatValue14 + 4.0F;
         }
      }

      renderManager.invoke25();
   }

   boolean check(RenderManager renderManager2, int i, int j, int k) {
      float floatValue25 = this.floatValue + 4.0F;
      float floatValue26 = this.floatValue2 + 20.0F + 4.0F;
      float floatValue27 = this.floatValue3 - 8.0F;
      float floatValue28 = this.floatValue4 - 20.0F - 8.0F;
      if (!RenderMath.check(i, j, floatValue25, floatValue26, floatValue27, floatValue28)) {
         return false;
      } else {
         List items2 = this.resolve();
         float floatValue29 = floatValue26 + this.typeUtils.measure();

         for (Module module4 : (List<Module>)items2) {
            EasedAnimation easedAnimation3 = LegacyClickGuiScreenController.resolve(module4);
            float floatValue30 = easedAnimation3.measure3();
            float floatValue31 = LegacySettingValueFormatter.measure2(renderManager2, module4.getVisibleSettings(), floatValue27 - 10.0F);
            float floatValue32 = floatValue31 > 0.0F ? floatValue31 + 4.0F : 0.0F;
            float floatValue33 = floatValue32 * floatValue30;
            float floatValue34 = 20.0F;
            if (RenderMath.check(i, j, floatValue25, floatValue29, floatValue27, floatValue34)) {
               if (k == 1 && !module4.getVisibleSettings().isEmpty()) {
                  LegacyClickGuiScreenController.invoke(module4);
                  return true;
               }

               if (k == 2) {
                  if (LegacyClickGuiState.module != null && LegacyClickGuiState.module != module4) {
                     LegacyClickGuiState.module.expanded = false;
                  }

                  module4.expanded = !module4.expanded;
                  LegacyClickGuiState.module = module4.expanded ? module4 : null;
                  return true;
               }

               if (k == 0) {
                  module4.toggle();
                  return true;
               }
            }

            if (floatValue33 > 0.5F) {
               float floatValue35 = floatValue25 + 5.0F;
               float floatValue36 = floatValue29 + 20.0F + 2.0F;
               float floatValue37 = floatValue27 - 10.0F;
               float floatValue38 = 0.0F;

               for (Setting setting2 : module4.getVisibleSettings()) {
                  if (!setting2.visibilityCondition.get()) {
                     float floatValue39 = LegacySettingValueFormatter.measure(renderManager2, setting2, floatValue37);
                     if (LegacySettingValueFormatter.check(renderManager2, setting2, floatValue35, floatValue36 + floatValue38, floatValue37, i, j, k)) {
                        return true;
                     }

                     floatValue38 += floatValue39 + LegacySettingValueFormatter.measure4();
                  }
               }
            }

            float floatValue40 = 20.0F + floatValue33;
            floatValue29 += floatValue40 + 4.0F;
         }

         return false;
      }
   }

   boolean check2(float f, float g, double d) {
      float floatValue41 = this.floatValue + 4.0F;
      float floatValue42 = this.floatValue2 + 20.0F + 4.0F;
      float floatValue43 = this.floatValue3 - 8.0F;
      float floatValue44 = this.floatValue4 - 20.0F - 8.0F;
      if (RenderMath.check(f, g, floatValue41, floatValue42, floatValue43, floatValue44)) {
         this.typeUtils.invoke2(d);
         return true;
      } else {
         return false;
      }
   }

   private List<Module> resolve() {
      if (WildClient.INSTANCE.moduleManager == null) {
         return Collections.emptyList();
      } else {
         ArrayList arrayList = WildClient.INSTANCE.moduleManager.getModules(this.category);
         String text2 = LegacyClickGuiState.text == null ? "" : LegacyClickGuiState.text.trim().toLowerCase();
         return (List<Module>)(text2.isEmpty()
            ? arrayList
            : arrayList.stream().filter(module -> ((Module)module).name != null && ((Module)module).name.toLowerCase().contains(text2)).toList());
      }
   }
}
