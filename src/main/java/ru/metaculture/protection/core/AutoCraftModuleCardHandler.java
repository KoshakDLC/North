package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.wild.module.api.Module;

public final class AutoCraftModuleCardHandler implements SpecialModuleCardHandler {
   private static final int INT_VALUE = 6;
   private static final int INT_VALUE_2 = 3;
   private static List<AutoCraftModuleCardHandler.AutoCraftModuleCardHandlerDisplayEntry> items;
   private final SettingsRenderer settingsRenderer = new SettingsRenderer();
   private final ClickGuiSettingController clickGuiSettingController = new ClickGuiSettingController();
   private final TextSetting autocraftSearch = new TextSetting("AutoCraft Search", "");
   private final SpringAnimation springAnimation = new SpringAnimation(0.0F);
   private final long[] longs = new long[9];
   private final Map<String, Long> valuesByKey = new HashMap<>();
   private String minecraftOakLog = "minecraft:oak_log";
   private String text = "";
   private boolean flag;
   private long timestamp;
   private long timestamp2;
   private float floatValue;
   private AutoCraft autoCraft;
   private AutoCraftModuleCardHandler.AutoCraftModuleCardHandlerBounds autoCraftModuleCardHandlerBounds;
   private Metrics metrics;
   private float floatValue2;
   private float floatValue3;
   private float floatValue4 = 1.0F;

   @Override
   public boolean check(Module module) {
      return module instanceof AutoCraft;
   }

   @Override
   public boolean check2(Module module, ClickGuiState clickGuiState) {
      return false;
   }

   @Override
   public void invoke(ClickGuiState clickGuiState2) {
      this.floatValue = 0.0F;
      this.springAnimation.invoke(0.0F);
   }

   @Override
   public float measure(Module module, Metrics metrics, ClickGuiState clickGuiState3) {
      return metrics.measure(238.0F);
   }

   @Override
   public void invoke3(Module module, ClickGuiState clickGuiState4, SpringSpec springSpec, SpringSpec springSpec2) {
      clickGuiState4.measure9("autocraft:panel", clickGuiState4.getValues().contains(module) ? 1.0F : 0.0F, springSpec);
   }

   @Override
   public void invoke4(
      RenderManager renderManager, DrawContext drawContext, ClickGuiState clickGuiState5, ModulePlacement modulePlacement, ThemeContext themeContext
   ) {
      if (modulePlacement.getModule() instanceof AutoCraft autoCraft) {
         Metrics metrics2 = themeContext.getMetrics();
         ColorScheme colorScheme = themeContext.getColorScheme();
         AutoCraftModuleCardHandler.AutoCraftModuleCardHandlerBounds autoCraftModuleCardHandlerBounds = this.resolve(modulePlacement, metrics2);
         this.autoCraft = autoCraft;
         this.autoCraftModuleCardHandlerBounds = autoCraftModuleCardHandlerBounds;
         this.metrics = metrics2;
         this.floatValue2 = this.measure4(metrics2);
         this.floatValue3 = metrics2.measure(3.0F);
         float floatValue = Math.max(0.05F, clickGuiState5.measure7("autocraft:panel"));
         this.floatValue4 = !clickGuiState5.isFlag7() && clickGuiState5.getValues().contains(autoCraft) ? floatValue : 0.0F;
         float floatValue2 = this.springAnimation.measure2(this.floatValue, SpringAnimation.MotionSpec.resolve());
         renderManager.invoke65(floatValue);
         boolean flag = false ;

         try {
            flag = true;
            this.invoke2(renderManager, autoCraftModuleCardHandlerBounds, metrics2, colorScheme);
            this.invoke6(renderManager, drawContext, clickGuiState5, autoCraft, autoCraftModuleCardHandlerBounds, metrics2, colorScheme);
            this.invoke7(renderManager, drawContext, clickGuiState5, autoCraft, autoCraftModuleCardHandlerBounds, metrics2, colorScheme, floatValue2);
            this.invoke9(renderManager, clickGuiState5, autoCraft, autoCraftModuleCardHandlerBounds, metrics2, themeContext);
            this.invoke10(renderManager, drawContext, clickGuiState5, autoCraftModuleCardHandlerBounds, metrics2);
            flag = false;
         } finally {
            if (flag) {
               renderManager.invoke66();
            }
         }

         renderManager.invoke66();
      }
   }

   @Override
   public void invoke5(List<ClickGuiHitTarget> list, ClickGuiState clickGuiState6, ModulePlacement modulePlacement2, Metrics metrics3) {
      if (modulePlacement2.getModule() instanceof AutoCraft autoCraft2) {
         AutoCraftModuleCardHandler.AutoCraftModuleCardHandlerBounds autoCraftModuleCardHandlerBounds2 = this.resolve(modulePlacement2, metrics3);
         this.autoCraft = autoCraft2;
         this.autoCraftModuleCardHandlerBounds = autoCraftModuleCardHandlerBounds2;
         this.metrics = metrics3;
         this.floatValue2 = this.measure4(metrics3);
         this.floatValue3 = metrics3.measure(3.0F);
         float floatValue3 = this.measure4(metrics3);
         float floatValue4 = metrics3.measure(3.0F);

         for (int intValue = 0; intValue < 9; intValue++) {
            int intValue2 = intValue;
            int intValue3 = intValue / 3;
            int intValue4 = intValue % 3;
            float floatValue5 = autoCraftModuleCardHandlerBounds2.gridX() + intValue4 * (floatValue3 + floatValue4);
            float floatValue6 = autoCraftModuleCardHandlerBounds2.gridY() + intValue3 * (floatValue3 + floatValue4);
            list.add(
               ClickGuiHitTarget.resolve().setIntValue(0).setFloatValue(floatValue5).setFloatValue2(floatValue6).setFloatValue3(floatValue3).setFloatValue4(floatValue3).setClickGuiAction(clickGuiState7 -> {
                  if (!this.minecraftOakLog.isBlank()) {
                     autoCraft2.retsept.invoke(intValue2, this.minecraftOakLog);
                     this.invoke14(intValue2);
                     clickGuiState7.invoke66();
                  }
               }).resolve()
            );
            list.add(
               ClickGuiHitTarget.resolve().setIntValue(1).setFloatValue(floatValue5).setFloatValue2(floatValue6).setFloatValue3(floatValue3).setFloatValue4(floatValue3).setClickGuiAction(clickGuiState8 -> {
                  autoCraft2.retsept.invoke2(intValue2);
                  this.invoke14(intValue2);
                  clickGuiState8.invoke66();
               }).resolve()
            );
         }

         list.add(
            ClickGuiHitTarget.resolve()
               .setIntValue(0)
               .setFloatValue(autoCraftModuleCardHandlerBounds2.clearX())
               .setFloatValue2(autoCraftModuleCardHandlerBounds2.clearY())
               .setFloatValue3(autoCraftModuleCardHandlerBounds2.clearW())
               .setFloatValue4(metrics3.measure(14.0F))
               .setClickGuiAction(clickGuiState9 -> {
                  autoCraft2.retsept.invoke3();
                  this.timestamp = System.currentTimeMillis();

                  for (int intValue5 = 0; intValue5 < this.longs.length; intValue5++) {
                     this.invoke14(intValue5);
                  }

                  clickGuiState9.invoke66();
               })
               .resolve()
         );
         list.add(
            ClickGuiHitTarget.resolve()
               .setIntValue(0)
               .setFloatValue(autoCraftModuleCardHandlerBounds2.searchX())
               .setFloatValue2(autoCraftModuleCardHandlerBounds2.searchY())
               .setFloatValue3(autoCraftModuleCardHandlerBounds2.searchW())
               .setFloatValue4(autoCraftModuleCardHandlerBounds2.searchH())
               .setClickGuiAction(clickGuiState10 -> {
                  clickGuiState10.setFlag5(false);
                  clickGuiState10.invoke52(this.autocraftSearch);
               })
               .resolve()
         );
         float floatValue7 = this.springAnimation.getFloatValue();
         List items = resolve2(this.autocraftSearch.value);
         int intValue6 = this.compute(autoCraftModuleCardHandlerBounds2, metrics3);
         float floatValue8 = this.measure3(metrics3);
         float floatValue9 = metrics3.measure(3.0F);
         float floatValue10 = autoCraftModuleCardHandlerBounds2.catalogY() + floatValue7;

         for (int intValue7 = 0; intValue7 < items.size(); intValue7++) {
            AutoCraftModuleCardHandler.AutoCraftModuleCardHandlerDisplayEntry autoCraftModuleCardHandlerDisplayEntry = (AutoCraftModuleCardHandler.AutoCraftModuleCardHandlerDisplayEntry)items.get(intValue7);
            int intValue8 = intValue7 / intValue6;
            int intValue9 = intValue7 % intValue6;
            float floatValue11 = autoCraftModuleCardHandlerBounds2.catalogX() + intValue9 * (floatValue8 + floatValue9);
            float floatValue12 = floatValue10 + intValue8 * (floatValue8 + floatValue9);
            if (!(floatValue12 + floatValue8 < autoCraftModuleCardHandlerBounds2.catalogY()) && !(floatValue12 > autoCraftModuleCardHandlerBounds2.catalogY() + autoCraftModuleCardHandlerBounds2.catalogH())) {
               list.add(
                  ClickGuiHitTarget.resolve()
                     .setIntValue(0)
                     .setFloatValue(floatValue11)
                     .setFloatValue2(floatValue12)
                     .setFloatValue3(floatValue8)
                     .setFloatValue4(floatValue8)
                     .setFloatValue5(autoCraftModuleCardHandlerBounds2.catalogX())
                     .setFloatValue6(autoCraftModuleCardHandlerBounds2.catalogY())
                     .setFloatValue7(autoCraftModuleCardHandlerBounds2.catalogW())
                     .setFloatValue8(autoCraftModuleCardHandlerBounds2.catalogH())
                     .setClickGuiAction(clickGuiState11 -> {
                        this.minecraftOakLog = autoCraftModuleCardHandlerDisplayEntry.id();
                        this.text = autoCraftModuleCardHandlerDisplayEntry.id();
                        this.invoke15(autoCraftModuleCardHandlerDisplayEntry.id());
                        clickGuiState11.setFlag5(false);
                        clickGuiState11.setNumberSetting((NumberSetting)null);
                     })
                     .resolve()
               );
            }
         }

         float floatValue13 = this.measure5(autoCraftModuleCardHandlerBounds2, metrics3);
         float floatValue14 = this.measure6(autoCraftModuleCardHandlerBounds2, metrics3);
         float floatValue15 = this.measure7(autoCraftModuleCardHandlerBounds2, metrics3);
         list.add(
            ClickGuiHitTarget.resolve()
               .setIntValue(0)
               .setFloatValue(floatValue13)
               .setFloatValue2(floatValue14 - metrics3.measure(2.0F))
               .setFloatValue3(floatValue15)
               .setFloatValue4(metrics3.measure(18.0F))
               .setClickGuiAction(clickGuiState12 -> {
                  clickGuiState12.setFlag5(false);
                  clickGuiState12.invoke52(autoCraft2.kolVoPredmetov);
               })
               .resolve()
         );
         float floatValue16 = floatValue14 + metrics3.measure(24.0F);
         list.add(
            ClickGuiHitTarget.resolve()
               .setIntValue(0)
               .setFloatValue(floatValue13)
               .setFloatValue2(floatValue16 + metrics3.measure(3.0F))
               .setFloatValue3(floatValue15)
               .setFloatValue4(metrics3.measure(26.0F))
               .setClickGuiAction(clickGuiState13 -> this.clickGuiSettingController.invoke(clickGuiState13, autoCraft2.zaderzhka, clickGuiState13.getFloatValue(), floatValue13, floatValue15))
               .resolve()
         );
         list.add(
            ClickGuiHitTarget.resolve()
               .setIntValue(0)
               .setFloatValue(this.measure8(autoCraftModuleCardHandlerBounds2, metrics3) - metrics3.measure(3.0F))
               .setFloatValue2(autoCraftModuleCardHandlerBounds2.catalogY())
               .setFloatValue3(metrics3.measure(9.0F))
               .setFloatValue4(autoCraftModuleCardHandlerBounds2.catalogH())
               .setClickGuiAction(clickGuiState14 -> {
                  this.flag = true;
                  this.invoke12(autoCraft2, autoCraftModuleCardHandlerBounds2, metrics3, clickGuiState14.getFloatValue2());
               })
               .resolve()
         );
      }
   }

   @Override
   public boolean check3(ClickGuiState clickGuiState15, ModuleLayoutResult moduleLayoutResult, Metrics metrics4, float f, float g, double d) {
      for (ModulePlacement modulePlacement3 : moduleLayoutResult.getItems()) {
         if (modulePlacement3.getModule() instanceof AutoCraft autoCraft3) {
            AutoCraftModuleCardHandler.AutoCraftModuleCardHandlerBounds autoCraftModuleCardHandlerBounds3 = this.resolve(modulePlacement3, metrics4);
            if (ClickGuiRenderUtils.check2(f, g, autoCraftModuleCardHandlerBounds3.catalogX(), autoCraftModuleCardHandlerBounds3.catalogY(), autoCraftModuleCardHandlerBounds3.catalogW() + metrics4.measure(8.0F), autoCraftModuleCardHandlerBounds3.catalogH())) {
               float floatValue17 = this.measure2(autoCraft3, autoCraftModuleCardHandlerBounds3, metrics4);
               this.floatValue = this.measure10(this.floatValue + (float)d * metrics4.measure(28.0F), -floatValue17, 0.0F);
               return true;
            }
         }
      }

      return false;
   }

   @Override
   public boolean check4(ClickGuiState clickGuiState16, float f, float g) {
      if (this.flag) {
         this.invoke12(this.autoCraft, this.autoCraftModuleCardHandlerBounds, this.metrics, g);
         return true;
      } else if (this.autoCraft != null && this.autoCraftModuleCardHandlerBounds != null) {
         String text = !this.text.isBlank() ? this.text : this.minecraftOakLog;
         if (text.isBlank()) {
            return false;
         } else {
            int intValue10 = this.compute2(f, g);
            if (intValue10 == -1) {
               return !this.text.isBlank();
            } else {
               if (!text.equals(this.autoCraft.retsept.resolve(intValue10))) {
                  this.autoCraft.retsept.invoke(intValue10, text);
                  this.invoke14(intValue10);
                  clickGuiState16.invoke66();
               }

               return true;
            }
         }
      } else {
         return false;
      }
   }

   @Override
   public boolean check5(ClickGuiState clickGuiState17) {
      if (this.flag) {
         this.flag = false;
         return true;
      } else if (this.text.isBlank()) {
         return false;
      } else {
         if (this.autoCraft != null && this.autoCraftModuleCardHandlerBounds != null) {
            int intValue11 = this.compute2(clickGuiState17.getFloatValue(), clickGuiState17.getFloatValue2());
            if (intValue11 != -1 && !this.text.equals(this.autoCraft.retsept.resolve(intValue11))) {
               this.autoCraft.retsept.invoke(intValue11, this.text);
               this.invoke14(intValue11);
               clickGuiState17.invoke66();
            }
         }

         this.text = "";
         return true;
      }
   }

   @Override
   public boolean check6(ClickGuiState clickGuiState18, int i) {
      TextSetting textSetting = clickGuiState18.getTextSetting();
      if (textSetting == this.autocraftSearch || this.autoCraft != null && textSetting == this.autoCraft.kolVoPredmetov) {
         if (i == 256 || i == 257) {
            clickGuiState18.setNumberSetting((NumberSetting)null);
            return true;
         } else if (i == 259 && !textSetting.value.isEmpty()) {
            textSetting.value = textSetting.value.substring(0, textSetting.value.length() - 1);
            if (textSetting == this.autocraftSearch) {
               this.invoke13();
               this.invoke16();
            } else {
               clickGuiState18.invoke66();
            }

            return true;
         } else if (textSetting == this.autocraftSearch && i == 261 && !this.autocraftSearch.value.isEmpty()) {
            this.autocraftSearch.value = "";
            this.invoke13();
            this.invoke16();
            return true;
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   @Override
   public boolean check7(ClickGuiState clickGuiState19, char c) {
      TextSetting textSetting2 = clickGuiState19.getTextSetting();
      if (textSetting2 == this.autocraftSearch || this.autoCraft != null && textSetting2 == this.autoCraft.kolVoPredmetov) {
         if (!Character.isISOControl(c)) {
            if (textSetting2 == this.autocraftSearch && this.autocraftSearch.value.length() < 64) {
               this.autocraftSearch.value = this.autocraftSearch.value + c;
               this.invoke13();
               this.invoke16();
            } else if (Character.isDigit(c) && textSetting2.value.length() < textSetting2.maxLength) {
               textSetting2.value = textSetting2.value + c;
               clickGuiState19.invoke66();
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private void invoke2(RenderManager renderManager2, AutoCraftModuleCardHandler.AutoCraftModuleCardHandlerBounds autoCraftModuleCardHandlerBounds4, Metrics metrics5, ColorScheme colorScheme2) {
      int intValue12 = ColorScheme.compute7(colorScheme2.getIntValue6(), ColorScheme.compute6(colorScheme2.getIntValue15(), 34), 0.16F);
      int intValue13 = ColorScheme.compute7(colorScheme2.getIntValue10(), ColorScheme.compute6(colorScheme2.getIntValue14(), 150), 0.24F);
      renderManager2.invoke41(
         autoCraftModuleCardHandlerBounds4.leftX(),
         autoCraftModuleCardHandlerBounds4.panelY(),
         autoCraftModuleCardHandlerBounds4.leftW(),
         autoCraftModuleCardHandlerBounds4.panelH(),
         metrics5.measure(6.0F),
         metrics5.measure(7.0F),
         metrics5.measure(1.0F),
         ColorScheme.compute6(colorScheme2.getIntValue14(), 13)
      );
      renderManager2.invoke5(autoCraftModuleCardHandlerBounds4.leftX(), autoCraftModuleCardHandlerBounds4.panelY(), autoCraftModuleCardHandlerBounds4.leftW(), autoCraftModuleCardHandlerBounds4.panelH(), metrics5.measure(6.0F), intValue12);
      renderManager2.invoke28(autoCraftModuleCardHandlerBounds4.leftX(), autoCraftModuleCardHandlerBounds4.panelY(), autoCraftModuleCardHandlerBounds4.leftW(), autoCraftModuleCardHandlerBounds4.panelH(), metrics5.measure(6.0F), intValue13, 0.7F);
      ClickGuiRenderUtils.invoke4(
         renderManager2,
         metrics5,
         FontRegistry.fontObject4,
         autoCraftModuleCardHandlerBounds4.leftX() + metrics5.measure(12.0F),
         autoCraftModuleCardHandlerBounds4.panelY() + metrics5.measure(8.0F),
         metrics5.measure(12.0F),
         10.0F,
         "Рецепт крафта",
         ClickGuiRenderUtils.compute2(colorScheme2)
      );
   }

   private void invoke6(
      RenderManager renderManager3,
      DrawContext drawContext,
      ClickGuiState clickGuiState20,
      AutoCraft autoCraft4,
      AutoCraftModuleCardHandler.AutoCraftModuleCardHandlerBounds autoCraftModuleCardHandlerBounds5,
      Metrics metrics6,
      ColorScheme colorScheme3
   ) {
      float floatValue18 = this.measure4(metrics6);
      float floatValue19 = metrics6.measure(3.0F);

      for (int intValue14 = 0; intValue14 < 9; intValue14++) {
         int intValue15 = intValue14 / 3;
         int intValue16 = intValue14 % 3;
         float floatValue20 = autoCraftModuleCardHandlerBounds5.gridX() + intValue16 * (floatValue18 + floatValue19);
         float floatValue21 = autoCraftModuleCardHandlerBounds5.gridY() + intValue15 * (floatValue18 + floatValue19);
         boolean flag2 = autoCraft4.retsept.resolve(intValue14).equals(this.minecraftOakLog);
         float floatValue22 = clickGuiState20.measure5(
            "autocraft:slot:hover:" + intValue14, ClickGuiRenderUtils.check(clickGuiState20, floatValue20, floatValue21, floatValue18, floatValue18) ? 1.0F : 0.0F, SpringSpec.resolve11()
         );
         float floatValue23 = this.measure9(this.longs[intValue14], 430L);
         float floatValue24 = 1.0F + floatValue22 * 0.035F + floatValue23 * 0.08F;
         renderManager3.invoke62(floatValue24, floatValue20 + floatValue18 * 0.5F, floatValue21 + floatValue18 * 0.5F);

         try {
            if (floatValue23 > 0.01F) {
               renderManager3.invoke41(
                  floatValue20,
                  floatValue21,
                  floatValue18,
                  floatValue18,
                  metrics6.measure(3.0F),
                  metrics6.measure(8.0F) * floatValue23,
                  metrics6.measure(1.0F),
                  ColorScheme.compute6(colorScheme3.getIntValue14(), Math.round(80.0F * floatValue23))
               );
            }

            int intValue17 = flag2
               ? ColorScheme.compute6(colorScheme3.getIntValue15(), Math.round(46.0F + 24.0F * floatValue22 + 38.0F * floatValue23))
               : ColorScheme.compute7(colorScheme3.getIntValue5(), colorScheme3.getIntValue7(), floatValue22);
            int intValue18 = !flag2 && !(floatValue23 > 0.01F)
               ? colorScheme3.getIntValue9()
               : ColorScheme.compute7(colorScheme3.getIntValue15(), colorScheme3.getIntValue14(), Math.max(floatValue22, floatValue23));
            renderManager3.invoke5(floatValue20, floatValue21, floatValue18, floatValue18, metrics6.measure(3.0F), intValue17);
            renderManager3.invoke28(floatValue20, floatValue21, floatValue18, floatValue18, metrics6.measure(3.0F), intValue18, !flag2 && !(floatValue23 > 0.01F) ? 0.55F : 0.95F);
            ItemStack itemStack2 = this.resolve4(autoCraft4.retsept.resolve(intValue14));
            if (!itemStack2.isEmpty()) {
               this.invoke11(
                  renderManager3,
                  drawContext,
                  itemStack2,
                  floatValue20 + floatValue18 * 0.23F,
                  floatValue21 + floatValue18 * 0.18F,
                  floatValue18 * 0.54F,
                  autoCraftModuleCardHandlerBounds5.leftX(),
                  autoCraftModuleCardHandlerBounds5.panelY(),
                  autoCraftModuleCardHandlerBounds5.leftW(),
                  autoCraftModuleCardHandlerBounds5.panelH()
               );
            }
         } finally {
            renderManager3.invoke64();
         }
      }

      float floatValue25 = clickGuiState20.measure5(
         "autocraft:clear:hover",
         ClickGuiRenderUtils.check(clickGuiState20, autoCraftModuleCardHandlerBounds5.clearX(), autoCraftModuleCardHandlerBounds5.clearY(), autoCraftModuleCardHandlerBounds5.clearW(), metrics6.measure(14.0F)) ? 1.0F : 0.0F,
         SpringSpec.resolve11()
      );
      float floatValue26 = this.measure9(this.timestamp, 450L);
      renderManager3.invoke5(
         autoCraftModuleCardHandlerBounds5.clearX(),
         autoCraftModuleCardHandlerBounds5.clearY(),
         autoCraftModuleCardHandlerBounds5.clearW(),
         metrics6.measure(14.0F),
         metrics6.measure(3.0F),
         ColorScheme.compute7(colorScheme3.getIntValue6(), ColorScheme.compute6(colorScheme3.getIntValue15(), 64), Math.max(floatValue25, floatValue26))
      );
      renderManager3.invoke28(
         autoCraftModuleCardHandlerBounds5.clearX(),
         autoCraftModuleCardHandlerBounds5.clearY(),
         autoCraftModuleCardHandlerBounds5.clearW(),
         metrics6.measure(14.0F),
         metrics6.measure(3.0F),
         ColorScheme.compute7(colorScheme3.getIntValue8(), colorScheme3.getIntValue14(), Math.max(floatValue25, floatValue26)),
         0.5F + floatValue26 * 0.4F
      );
      ClickGuiRenderUtils.invoke5(
         renderManager3,
         metrics6,
         FontRegistry.fontObject4,
         autoCraftModuleCardHandlerBounds5.clearX() + autoCraftModuleCardHandlerBounds5.clearW() * 0.5F,
         autoCraftModuleCardHandlerBounds5.clearY(),
         metrics6.measure(14.0F),
         7.0F,
         "Очистить",
         ClickGuiRenderUtils.compute2(colorScheme3),
         "c"
      );
   }

   private void invoke7(
      RenderManager renderManager4,
      DrawContext drawContext,
      ClickGuiState clickGuiState21,
      AutoCraft autoCraft5,
      AutoCraftModuleCardHandler.AutoCraftModuleCardHandlerBounds autoCraftModuleCardHandlerBounds6,
      Metrics metrics7,
      ColorScheme colorScheme4,
      float f
   ) {
      List items2 = resolve2(this.autocraftSearch.value);
      float floatValue27 = clickGuiState21.measure5(
         "autocraft:search:focus", clickGuiState21.getTextSetting() == this.autocraftSearch ? 1.0F : 0.0F, SpringSpec.resolve9()
      );
      float floatValue28 = clickGuiState21.measure5("autocraft:search:query", this.autocraftSearch.value.isBlank() ? 0.0F : 1.0F, SpringSpec.resolve9());
      float floatValue29 = this.measure9(this.timestamp2, 360L);
      int intValue19 = ColorScheme.compute7(
         colorScheme4.getIntValue7(), ColorScheme.compute6(colorScheme4.getIntValue15(), 58), Math.max(floatValue28 * 0.45F, floatValue27 * 0.7F)
      );
      int intValue20 = ColorScheme.compute7(colorScheme4.getIntValue10(), colorScheme4.getIntValue14(), Math.max(floatValue27, floatValue29));
      renderManager4.invoke5(autoCraftModuleCardHandlerBounds6.searchX(), autoCraftModuleCardHandlerBounds6.searchY(), autoCraftModuleCardHandlerBounds6.searchW(), autoCraftModuleCardHandlerBounds6.searchH(), metrics7.measure(4.0F), intValue19);
      renderManager4.invoke28(
         autoCraftModuleCardHandlerBounds6.searchX(),
         autoCraftModuleCardHandlerBounds6.searchY(),
         autoCraftModuleCardHandlerBounds6.searchW(),
         autoCraftModuleCardHandlerBounds6.searchH(),
         metrics7.measure(4.0F),
         intValue20,
         0.55F + floatValue27 * 0.25F + floatValue29 * 0.35F
      );
      if (floatValue27 > 0.01F || floatValue29 > 0.01F) {
         renderManager4.invoke41(
            autoCraftModuleCardHandlerBounds6.searchX(),
            autoCraftModuleCardHandlerBounds6.searchY(),
            autoCraftModuleCardHandlerBounds6.searchW(),
            autoCraftModuleCardHandlerBounds6.searchH(),
            metrics7.measure(4.0F),
            metrics7.measure(8.0F) * Math.max(floatValue27, floatValue29),
            metrics7.measure(1.0F),
            ColorScheme.compute6(colorScheme4.getIntValue14(), Math.round(32.0F * Math.max(floatValue27, floatValue29)))
         );
      }

      String text2 = this.autocraftSearch.value.isBlank()
         ? "Поиск"
         : this.autocraftSearch.value + (clickGuiState21.getTextSetting() == this.autocraftSearch ? "|" : "");
      int intValue21 = this.autocraftSearch.value.isBlank() ? ClickGuiRenderUtils.compute5(colorScheme4) : ClickGuiRenderUtils.compute2(colorScheme4);
      String text3 = this.autocraftSearch.value.isBlank() ? "" : Integer.toString(items2.size());
      float floatValue30 = text3.isBlank() ? 0.0F : ClickGuiRenderUtils.measure2(metrics7, FontRegistry.fontObject4, text3, 8.0F) + metrics7.measure(12.0F);
      String text4 = ClickGuiRenderUtils.resolve4(metrics7, FontRegistry.fontObject, text2, 8.0F, autoCraftModuleCardHandlerBounds6.searchW() - metrics7.measure(12.0F) - floatValue30);
      ClickGuiRenderUtils.invoke4(
         renderManager4,
         metrics7,
         FontRegistry.fontObject,
         autoCraftModuleCardHandlerBounds6.searchX() + metrics7.measure(6.0F),
         autoCraftModuleCardHandlerBounds6.searchY(),
         autoCraftModuleCardHandlerBounds6.searchH(),
         8.0F,
         text4,
         intValue21
      );
      if (!text3.isBlank()) {
         renderManager4.invoke5(
            autoCraftModuleCardHandlerBounds6.searchX() + autoCraftModuleCardHandlerBounds6.searchW() - floatValue30 - metrics7.measure(4.0F),
            autoCraftModuleCardHandlerBounds6.searchY() + metrics7.measure(4.0F),
            floatValue30,
            autoCraftModuleCardHandlerBounds6.searchH() - metrics7.measure(8.0F),
            metrics7.measure(4.0F),
            ColorScheme.compute6(colorScheme4.getIntValue15(), 55)
         );
         ClickGuiRenderUtils.invoke5(
            renderManager4,
            metrics7,
            FontRegistry.fontObject4,
            autoCraftModuleCardHandlerBounds6.searchX() + autoCraftModuleCardHandlerBounds6.searchW() - floatValue30 * 0.5F - metrics7.measure(4.0F),
            autoCraftModuleCardHandlerBounds6.searchY(),
            autoCraftModuleCardHandlerBounds6.searchH(),
            8.0F,
            text3,
            colorScheme4.getIntValue14(),
            "c"
         );
      }

      renderManager4.invoke20();
      renderManager4.invoke23(
         Math.round(autoCraftModuleCardHandlerBounds6.catalogX()), Math.round(autoCraftModuleCardHandlerBounds6.catalogY()), Math.round(autoCraftModuleCardHandlerBounds6.catalogW()), Math.round(autoCraftModuleCardHandlerBounds6.catalogH())
      );

      try {
         if (items2.isEmpty()) {
            ClickGuiRenderUtils.invoke5(
               renderManager4,
               metrics7,
               FontRegistry.fontObject,
               autoCraftModuleCardHandlerBounds6.catalogX() + autoCraftModuleCardHandlerBounds6.catalogW() * 0.5F,
               autoCraftModuleCardHandlerBounds6.catalogY() + autoCraftModuleCardHandlerBounds6.catalogH() * 0.5F - metrics7.measure(5.0F),
               metrics7.measure(10.0F),
               8.0F,
               "Нет совпадений",
               ClickGuiRenderUtils.compute5(colorScheme4),
               "c"
            );
            return;
         }

         int intValue22 = this.compute(autoCraftModuleCardHandlerBounds6, metrics7);
         float floatValue31 = this.measure3(metrics7);
         float floatValue32 = metrics7.measure(3.0F);
         float floatValue33 = autoCraftModuleCardHandlerBounds6.catalogY() + f;

         for (int intValue23 = 0; intValue23 < items2.size(); intValue23++) {
            AutoCraftModuleCardHandler.AutoCraftModuleCardHandlerDisplayEntry autoCraftModuleCardHandlerDisplayEntry2 = (AutoCraftModuleCardHandler.AutoCraftModuleCardHandlerDisplayEntry)items2.get(intValue23);
            int intValue24 = intValue23 / intValue22;
            int intValue25 = intValue23 % intValue22;
            float floatValue34 = autoCraftModuleCardHandlerBounds6.catalogX() + intValue25 * (floatValue31 + floatValue32);
            float floatValue35 = floatValue33 + intValue24 * (floatValue31 + floatValue32);
            if (!(floatValue35 + floatValue31 < autoCraftModuleCardHandlerBounds6.catalogY()) && !(floatValue35 > autoCraftModuleCardHandlerBounds6.catalogY() + autoCraftModuleCardHandlerBounds6.catalogH())) {
               boolean flag3 = autoCraftModuleCardHandlerDisplayEntry2.id().equals(this.minecraftOakLog);
               float floatValue36 = clickGuiState21.measure5(
                  "autocraft:catalog:hover:" + autoCraftModuleCardHandlerDisplayEntry2.id(),
                  ClickGuiRenderUtils.check(clickGuiState21, floatValue34, floatValue35, floatValue31, floatValue31) ? 1.0F : 0.0F,
                  SpringSpec.resolve11()
               );
               float floatValue37 = clickGuiState21.measure5("autocraft:catalog:selected:" + autoCraftModuleCardHandlerDisplayEntry2.id(), flag3 ? 1.0F : 0.0F, SpringSpec.resolve9());
               float floatValue38 = this.measure9(this.valuesByKey.getOrDefault(autoCraftModuleCardHandlerDisplayEntry2.id(), 0L), 430L);
               float floatValue39 = 1.0F + floatValue36 * 0.04F + floatValue38 * 0.1F;
               renderManager4.invoke62(floatValue39, floatValue34 + floatValue31 * 0.5F, floatValue35 + floatValue31 * 0.5F);
               boolean flag4 = false ;

               try {
                  flag4 = true;
                  if (floatValue38 > 0.01F) {
                     renderManager4.invoke41(
                        floatValue34,
                        floatValue35,
                        floatValue31,
                        floatValue31,
                        metrics7.measure(3.0F),
                        metrics7.measure(7.0F) * floatValue38,
                        metrics7.measure(1.0F),
                        ColorScheme.compute6(colorScheme4.getIntValue14(), Math.round(72.0F * floatValue38))
                     );
                  }

                  renderManager4.invoke5(
                     floatValue34,
                     floatValue35,
                     floatValue31,
                     floatValue31,
                     metrics7.measure(3.0F),
                     ColorScheme.compute7(
                        colorScheme4.getIntValue6(), ColorScheme.compute6(colorScheme4.getIntValue15(), 72), Math.max(floatValue37, floatValue36 * 0.45F)
                     )
                  );
                  renderManager4.invoke28(
                     floatValue34,
                     floatValue35,
                     floatValue31,
                     floatValue31,
                     metrics7.measure(3.0F),
                     ColorScheme.compute7(colorScheme4.getIntValue8(), colorScheme4.getIntValue14(), Math.max(floatValue37, floatValue38)),
                     !(floatValue37 > 0.01F) && !(floatValue38 > 0.01F) ? 0.45F : 0.9F
                  );
                  this.invoke11(
                     renderManager4,
                     drawContext,
                     autoCraftModuleCardHandlerDisplayEntry2.stack(),
                     floatValue34 + floatValue31 * 0.16F,
                     floatValue35 + floatValue31 * 0.16F,
                     floatValue31 * 0.68F,
                     autoCraftModuleCardHandlerBounds6.catalogX(),
                     autoCraftModuleCardHandlerBounds6.catalogY(),
                     autoCraftModuleCardHandlerBounds6.catalogW(),
                     autoCraftModuleCardHandlerBounds6.catalogH()
                  );
                  flag4 = false;
               } finally {
                  if (flag4) {
                     renderManager4.invoke64();
                  }
               }

               renderManager4.invoke64();
            }
         }
      } finally {
         renderManager4.invoke20();
         renderManager4.invoke25();
      }

      this.invoke8(renderManager4, autoCraft5, autoCraftModuleCardHandlerBounds6, metrics7, colorScheme4, f);
   }

   private void invoke8(
      RenderManager renderManager5, AutoCraft autoCraft6, AutoCraftModuleCardHandler.AutoCraftModuleCardHandlerBounds autoCraftModuleCardHandlerBounds7, Metrics metrics8, ColorScheme colorScheme5, float f
   ) {
      float floatValue40 = this.measure2(autoCraft6, autoCraftModuleCardHandlerBounds7, metrics8);
      float floatValue41 = metrics8.measure(3.0F);
      float floatValue42 = this.measure8(autoCraftModuleCardHandlerBounds7, metrics8);
      float floatValue43 = autoCraftModuleCardHandlerBounds7.catalogY();
      float floatValue44 = autoCraftModuleCardHandlerBounds7.catalogH();
      renderManager5.invoke5(floatValue42, floatValue43, floatValue41, floatValue44, floatValue41 * 0.5F, colorScheme5.getIntValue6());
      float floatValue45 = floatValue40 <= 0.0F ? floatValue44 : Math.max(metrics8.measure(16.0F), floatValue44 * (floatValue44 / (floatValue44 + floatValue40)));
      float floatValue46 = floatValue40 <= 0.0F ? 0.0F : this.measure10(-f / floatValue40, 0.0F, 1.0F);
      float floatValue47 = floatValue43 + (floatValue44 - floatValue45) * floatValue46;
      renderManager5.invoke5(floatValue42, floatValue47, floatValue41, floatValue45, floatValue41 * 0.5F, ColorScheme.compute7(colorScheme5.getIntValue11(), colorScheme5.getIntValue14(), 0.45F));
   }

   private void invoke9(
      RenderManager renderManager6,
      ClickGuiState clickGuiState22,
      AutoCraft autoCraft7,
      AutoCraftModuleCardHandler.AutoCraftModuleCardHandlerBounds autoCraftModuleCardHandlerBounds8,
      Metrics metrics9,
      ThemeContext themeContext2
   ) {
      float floatValue48 = this.measure5(autoCraftModuleCardHandlerBounds8, metrics9);
      float floatValue49 = this.measure6(autoCraftModuleCardHandlerBounds8, metrics9);
      float floatValue50 = this.measure7(autoCraftModuleCardHandlerBounds8, metrics9);
      this.settingsRenderer.invoke(renderManager6, clickGuiState22, autoCraft7.kolVoPredmetov, floatValue48, floatValue49, floatValue50, themeContext2);
      this.settingsRenderer.invoke(renderManager6, clickGuiState22, autoCraft7.zaderzhka, floatValue48, floatValue49 + metrics9.measure(24.0F), floatValue50, themeContext2);
   }

   private void invoke10(
      RenderManager renderManager7, DrawContext drawContext, ClickGuiState clickGuiState23, AutoCraftModuleCardHandler.AutoCraftModuleCardHandlerBounds autoCraftModuleCardHandlerBounds9, Metrics metrics10
   ) {
      if (!this.text.isBlank()) {
         ItemStack itemStack3 = this.resolve4(this.text);
         if (!itemStack3.isEmpty()) {
            float floatValue51 = metrics10.measure(18.0F);
            this.invoke11(
               renderManager7,
               drawContext,
               itemStack3,
               clickGuiState23.getFloatValue() - floatValue51 * 0.5F,
               clickGuiState23.getFloatValue2() - floatValue51 * 0.5F,
               floatValue51,
               autoCraftModuleCardHandlerBounds9.x() - metrics10.measure(20.0F),
               autoCraftModuleCardHandlerBounds9.y() - metrics10.measure(20.0F),
               autoCraftModuleCardHandlerBounds9.width() + metrics10.measure(40.0F),
               autoCraftModuleCardHandlerBounds9.height() + metrics10.measure(80.0F)
            );
         }
      }
   }

   private AutoCraftModuleCardHandler.AutoCraftModuleCardHandlerBounds resolve(ModulePlacement modulePlacement4, Metrics metrics11) {
      float floatValue52 = metrics11.getFloatValue15();
      float floatValue53 = modulePlacement4.getFloatValue() + metrics11.measure(14.0F);
      float floatValue54 = modulePlacement4.getFloatValue2() + floatValue52 + metrics11.measure(8.0F);
      float floatValue55 = modulePlacement4.getFloatValue3() - metrics11.measure(28.0F);
      float floatValue56 = metrics11.measure(12.0F);
      float floatValue57 = metrics11.measure(14.0F);
      float floatValue58 = this.measure3(metrics11);
      float floatValue59 = metrics11.measure(3.0F);
      float floatValue60 = floatValue58 * 6.0F + floatValue59 * 5.0F;
      float floatValue61 = floatValue58 * 3.0F + floatValue59 * 2.0F;
      float floatValue62 = this.measure4(metrics11);
      float floatValue63 = metrics11.measure(3.0F);
      float floatValue64 = floatValue62 * 3.0F + floatValue63 * 2.0F;
      float floatValue65 = floatValue64 + floatValue57 + floatValue60 + metrics11.measure(8.0F);
      float floatValue66 = metrics11.measure(162.0F);
      float floatValue67 = floatValue53 + floatValue56;
      float floatValue68 = floatValue67 + floatValue64 + floatValue57;
      float floatValue69 = floatValue54 + floatValue56 + metrics11.measure(14.0F);
      float floatValue70 = floatValue55 - floatValue56 * 2.0F;
      float floatValue71 = metrics11.measure(18.0F);
      float floatValue72 = floatValue69 + floatValue71 + metrics11.measure(8.0F);
      float floatValue73 = floatValue72 + floatValue64 + metrics11.measure(6.0F);
      return new AutoCraftModuleCardHandler.AutoCraftModuleCardHandlerBounds(
         floatValue53,
         floatValue54,
         floatValue55,
         floatValue66,
         floatValue53,
         floatValue68,
         floatValue55,
         floatValue60,
         floatValue54,
         floatValue66,
         floatValue67,
         floatValue72,
         floatValue67,
         floatValue73,
         floatValue64,
         floatValue67,
         floatValue69,
         floatValue70,
         floatValue71,
         floatValue68,
         floatValue72,
         floatValue60,
         floatValue61
      );
   }

   private static List<AutoCraftModuleCardHandler.AutoCraftModuleCardHandlerDisplayEntry> resolve2(String string) {
      String text5 = string == null ? "" : string.trim().toLowerCase(Locale.ROOT);
      if (text5.isEmpty()) {
         return resolve3();
      } else {
         ArrayList arrayList = new ArrayList();

         for (AutoCraftModuleCardHandler.AutoCraftModuleCardHandlerDisplayEntry autoCraftModuleCardHandlerDisplayEntry3 : resolve3()) {
            if (autoCraftModuleCardHandlerDisplayEntry3.id().toLowerCase(Locale.ROOT).contains(text5) || autoCraftModuleCardHandlerDisplayEntry3.label().toLowerCase(Locale.ROOT).contains(text5)) {
               arrayList.add(autoCraftModuleCardHandlerDisplayEntry3);
            }
         }

         return arrayList;
      }
   }

   private static List<AutoCraftModuleCardHandler.AutoCraftModuleCardHandlerDisplayEntry> resolve3() {
      if (items != null) {
         return items;
      } else {
         ArrayList arrayList2 = new ArrayList();

         for (Item item : Registries.ITEM) {
            if (item != Items.AIR) {
               Identifier identifier = Registries.ITEM.getId(item);
               if (identifier != null && "minecraft".equals(identifier.getNamespace())) {
                  ItemStack itemStack4 = item.getDefaultStack();
                  arrayList2.add(new AutoCraftModuleCardHandler.AutoCraftModuleCardHandlerDisplayEntry(identifier.toString(), itemStack4.getName().getString(), itemStack4));
               }
            }
         }

         arrayList2.sort(Comparator.comparing(AutoCraftModuleCardHandler.AutoCraftModuleCardHandlerDisplayEntry::label, String.CASE_INSENSITIVE_ORDER));
         items = List.copyOf(arrayList2);
         return items;
      }
   }

   private ItemStack resolve4(String string) {
      Identifier identifier2 = Identifier.tryParse(string == null ? "" : string);
      if (identifier2 == null) {
         return ItemStack.EMPTY;
      } else {
         Item item2 = (Item)Registries.ITEM.get(identifier2);
         return item2 == Items.AIR ? ItemStack.EMPTY : item2.getDefaultStack();
      }
   }

   private void invoke11(
      RenderManager renderManager8, DrawContext drawContext, ItemStack itemStack, float f, float g, float h, float i, float j, float k, float l
   ) {
      if (!(this.floatValue4 < 0.15F)) {
         if (drawContext != null && itemStack != null && !itemStack.isEmpty()) {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client != null && client.getWindow() != null) {
               float[] floatValues = renderManager8.resolve6();
               float floatValue74 = floatValues[0] * f + floatValues[1] * g + floatValues[2];
               float floatValue75 = floatValues[3] * f + floatValues[4] * g + floatValues[5];
               float floatValue76 = floatValues[0] * (f + h) + floatValues[1] * g + floatValues[2];
               float floatValue77 = floatValues[3] * (f + h) + floatValues[4] * g + floatValues[5];
               float floatValue78 = floatValues[0] * i + floatValues[1] * j + floatValues[2];
               float floatValue79 = floatValues[3] * i + floatValues[4] * j + floatValues[5];
               float floatValue80 = floatValues[0] * (i + k) + floatValues[1] * j + floatValues[2];
               float floatValue81 = floatValues[3] * (i + k) + floatValues[4] * j + floatValues[5];
               float floatValue82 = floatValues[0] * (i + k) + floatValues[1] * (j + l) + floatValues[2];
               float floatValue83 = floatValues[3] * (i + k) + floatValues[4] * (j + l) + floatValues[5];
               float floatValue84 = floatValues[0] * i + floatValues[1] * (j + l) + floatValues[2];
               float floatValue85 = floatValues[3] * i + floatValues[4] * (j + l) + floatValues[5];
               float floatValue86 = Math.min(Math.min(floatValue78, floatValue80), Math.min(floatValue82, floatValue84));
               float floatValue87 = Math.min(Math.min(floatValue79, floatValue81), Math.min(floatValue83, floatValue85));
               float floatValue88 = Math.max(Math.max(floatValue78, floatValue80), Math.max(floatValue82, floatValue84));
               float floatValue89 = Math.max(Math.max(floatValue79, floatValue81), Math.max(floatValue83, floatValue85));
               float floatValue90 = Math.max(1.0F, (float)Math.hypot(floatValue76 - floatValue74, floatValue77 - floatValue75));
               float floatValue91 = client.getWindow().getScaleFactor();
               renderManager8.invoke20();
               drawContext.enableScissor(
                  (int)Math.floor(floatValue86 / floatValue91), (int)Math.floor(floatValue87 / floatValue91), (int)Math.ceil(floatValue88 / floatValue91), (int)Math.ceil(floatValue89 / floatValue91)
               );
               drawContext.getMatrices().pushMatrix();
               boolean flag5 = false ;

               try {
                  flag5 = true;
                  drawContext.getMatrices().identity();
                  drawContext.getMatrices().translate(floatValue74 / floatValue91, floatValue75 / floatValue91);
                  drawContext.getMatrices().scale(floatValue90 / 16.0F / floatValue91, floatValue90 / 16.0F / floatValue91);
                  drawContext.drawItem(itemStack, 0, 0);
                  flag5 = false;
               } finally {
                  if (flag5) {
                     drawContext.getMatrices().popMatrix();
                     drawContext.disableScissor();
                  }
               }

               drawContext.getMatrices().popMatrix();
               drawContext.disableScissor();
            }
         }
      }
   }

   private float measure2(AutoCraft autoCraft8, AutoCraftModuleCardHandler.AutoCraftModuleCardHandlerBounds autoCraftModuleCardHandlerBounds10, Metrics metrics12) {
      int intValue26 = this.compute(autoCraftModuleCardHandlerBounds10, metrics12);
      int intValue27 = Math.max(1, (resolve2(this.autocraftSearch.value).size() + intValue26 - 1) / intValue26);
      float floatValue92 = intValue27 * this.measure3(metrics12) + Math.max(0, intValue27 - 1) * metrics12.measure(3.0F);
      return Math.max(0.0F, floatValue92 - autoCraftModuleCardHandlerBounds10.catalogH());
   }

   private int compute(AutoCraftModuleCardHandler.AutoCraftModuleCardHandlerBounds autoCraftModuleCardHandlerBounds11, Metrics metrics13) {
      return 6;
   }

   private float measure3(Metrics metrics14) {
      return metrics14.measure(28.0F);
   }

   private float measure4(Metrics metrics15) {
      return metrics15.measure(24.0F);
   }

   private float measure5(AutoCraftModuleCardHandler.AutoCraftModuleCardHandlerBounds autoCraftModuleCardHandlerBounds12, Metrics metrics16) {
      return autoCraftModuleCardHandlerBounds12.x() + metrics16.measure(12.0F);
   }

   private float measure6(AutoCraftModuleCardHandler.AutoCraftModuleCardHandlerBounds autoCraftModuleCardHandlerBounds13, Metrics metrics17) {
      return autoCraftModuleCardHandlerBounds13.panelY() + autoCraftModuleCardHandlerBounds13.panelH() + metrics17.measure(10.0F);
   }

   private float measure7(AutoCraftModuleCardHandler.AutoCraftModuleCardHandlerBounds autoCraftModuleCardHandlerBounds14, Metrics metrics18) {
      return autoCraftModuleCardHandlerBounds14.width() - metrics18.measure(24.0F);
   }

   private float measure8(AutoCraftModuleCardHandler.AutoCraftModuleCardHandlerBounds autoCraftModuleCardHandlerBounds15, Metrics metrics19) {
      return autoCraftModuleCardHandlerBounds15.catalogX() + autoCraftModuleCardHandlerBounds15.catalogW() + metrics19.measure(3.0F);
   }

   private void invoke12(AutoCraft autoCraft9, AutoCraftModuleCardHandler.AutoCraftModuleCardHandlerBounds autoCraftModuleCardHandlerBounds16, Metrics metrics20, float f) {
      if (autoCraft9 != null && autoCraftModuleCardHandlerBounds16 != null && metrics20 != null) {
         float floatValue93 = this.measure2(autoCraft9, autoCraftModuleCardHandlerBounds16, metrics20);
         if (floatValue93 <= 0.0F) {
            this.floatValue = 0.0F;
            this.springAnimation.invoke(0.0F);
         } else {
            float floatValue94 = this.measure10((f - autoCraftModuleCardHandlerBounds16.catalogY()) / Math.max(1.0F, autoCraftModuleCardHandlerBounds16.catalogH()), 0.0F, 1.0F);
            this.floatValue = -floatValue93 * floatValue94;
            this.springAnimation.invoke(this.floatValue);
         }
      }
   }

   private void invoke13() {
      this.floatValue = 0.0F;
      this.springAnimation.invoke(0.0F);
   }

   private void invoke14(int i) {
      if (i >= 0 && i < this.longs.length) {
         this.longs[i] = System.currentTimeMillis();
      }
   }

   private void invoke15(String string) {
      if (string != null && !string.isBlank()) {
         this.valuesByKey.put(string, System.currentTimeMillis());
      }
   }

   private void invoke16() {
      this.timestamp2 = System.currentTimeMillis();
   }

   private float measure9(long l, long m) {
      if (l > 0L && m > 0L) {
         float floatValue95 = (float)(System.currentTimeMillis() - l);
         if (floatValue95 >= (float)m) {
            return 0.0F;
         } else {
            float floatValue96 = 1.0F - floatValue95 / (float)m;
            return floatValue96 * floatValue96;
         }
      } else {
         return 0.0F;
      }
   }

   private int compute2(float f, float g) {
      for (int intValue28 = 0; intValue28 < 9; intValue28++) {
         int intValue29 = intValue28 / 3;
         int intValue30 = intValue28 % 3;
         float floatValue97 = this.autoCraftModuleCardHandlerBounds.gridX() + intValue30 * (this.floatValue2 + this.floatValue3);
         float floatValue98 = this.autoCraftModuleCardHandlerBounds.gridY() + intValue29 * (this.floatValue2 + this.floatValue3);
         if (f >= floatValue97 && g >= floatValue98 && f < floatValue97 + this.floatValue2 && g < floatValue98 + this.floatValue2) {
            return intValue28;
         }
      }

      return -1;
   }

   private float measure10(float f, float g, float h) {
      return Math.max(g, Math.min(h, f));
   }

   record AutoCraftModuleCardHandlerDisplayEntry(String id, String label, ItemStack stack) {
   }

   record AutoCraftModuleCardHandlerBounds(
      float x,
      float y,
      float width,
      float height,
      float leftX,
      float rightX,
      float leftW,
      float rightW,
      float panelY,
      float panelH,
      float gridX,
      float gridY,
      float clearX,
      float clearY,
      float clearW,
      float searchX,
      float searchY,
      float searchW,
      float searchH,
      float catalogX,
      float catalogY,
      float catalogW,
      float catalogH
   ) {
   }
}
