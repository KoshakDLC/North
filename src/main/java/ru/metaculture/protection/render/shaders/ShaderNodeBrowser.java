package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class ShaderNodeBrowser {
   private static final float FLOAT_VALUE = 16.0F;
   private static final int INT_VALUE = 40;
   private static final float FLOAT_VALUE_2 = 22.0F;
   private static final float FLOAT_VALUE_3 = 34.0F;
   private final ShaderNodeRegistry shaderNodeRegistry;
   private final ClampedSpringAnimation clampedSpringAnimation = new ClampedSpringAnimation(
      AnimationSystem.getINSTANCE(), SpringConfig.resolve(3.4F, 0.82F), 0.0F, 0.0F, 1.0F, 0.001F, 0.001F
   );
   private final SpringAnimation springAnimation = new SpringAnimation(0.0F);
   private final Map<String, SpringAnimation> valuesByKey = new HashMap<>();
   private final Map<String, Boolean> valuesByKey2 = new LinkedHashMap<>();
   private boolean flag;
   private float floatValue;
   private float floatValue2;
   private String text = "";
   private int intValue;
   private float floatValue3;
   private float floatValue4;
   private boolean flag2;
   private long timestamp;
   private ShaderValueType shaderValueType;
   private List<ShaderNodeSearch.ShaderNodeSearchData> items = new ArrayList<>();
   private List<ShaderNodeDefinition> items2 = new ArrayList<>();

   public ShaderNodeBrowser(ShaderNodeRegistry shaderNodeRegistry) {
      this.shaderNodeRegistry = shaderNodeRegistry;
   }

   public boolean isFlag() {
      return this.flag;
   }

   public ShaderValueType getShaderValueType() {
      return this.shaderValueType;
   }

   public float getFloatValue() {
      return this.floatValue;
   }

   public float getFloatValue2() {
      return this.floatValue2;
   }

   public void invoke(float f, float g, ShaderValueType shaderValueType) {
      this.flag = true;
      this.floatValue = f;
      this.floatValue2 = g;
      this.text = "";
      this.intValue = 0;
      this.floatValue3 = 0.0F;
      this.springAnimation.invoke(0.0F);
      this.timestamp = System.currentTimeMillis();
      this.shaderValueType = shaderValueType;
      this.clampedSpringAnimation.invoke2(1.0F);
      this.invoke9();
   }

   public void invoke2() {
      this.flag = false;
      this.shaderValueType = null;
      this.clampedSpringAnimation.invoke2(0.0F);
   }

   public void invoke3(char c) {
      if (this.flag) {
         if ((c >= '0' && c <= '9' || c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c == ' ' || c == '_' || c == '.' || c == '-')
            && this.text.length() < 40) {
            this.text = this.text + c;
            this.intValue = 0;
            this.floatValue3 = 0.0F;
            this.timestamp = System.currentTimeMillis();
            this.invoke9();
         }
      }
   }

   public void invoke4() {
      if (this.flag && !this.text.isEmpty()) {
         this.text = this.text.substring(0, this.text.length() - 1);
         this.intValue = 0;
         this.floatValue3 = 0.0F;
         this.timestamp = System.currentTimeMillis();
         this.invoke9();
      }
   }

   public void invoke5() {
      if (this.flag) {
         this.text = "";
         this.intValue = 0;
         this.floatValue3 = 0.0F;
         this.timestamp = System.currentTimeMillis();
         this.invoke9();
      }
   }

   public void invoke6(int i) {
      if (this.flag && !this.items2.isEmpty() && i != 0) {
         int intValue = i < 0 ? -1 : 1;
         int intValue2 = Math.floorMod(this.intValue + i, this.items2.size());
         if (this.text.isBlank()) {
            for (int intValue3 = 0; intValue3 < this.items2.size() && this.check(this.items2.get(intValue2).getText3()); intValue3++) {
               intValue2 = Math.floorMod(intValue2 + intValue, this.items2.size());
            }

            if (this.check(this.items2.get(intValue2).getText3())) {
               return;
            }
         }

         this.intValue = intValue2;
         this.flag2 = true;
      }
   }

   public ShaderNodeDefinition resolve() {
      return this.items2.isEmpty() ? null : this.items2.get(Math.min(this.intValue, this.items2.size() - 1));
   }

   public List<ShaderNodeDefinition> getItems2() {
      return this.items2;
   }

   public void invoke7(double d) {
      if (this.flag) {
         this.floatValue3 = Math.max(0.0F, Math.min(this.floatValue3 - (float)d * 28.0F, Math.max(0.0F, this.floatValue4)));
      }
   }

   public void invoke8(String string) {
      if (string != null) {
         boolean flag = !this.valuesByKey2.getOrDefault(string, false);
         this.valuesByKey2.put(string, flag);
         if (flag && this.text.isBlank() && !this.items2.isEmpty()) {
            ShaderNodeDefinition shaderNodeDefinition = this.items2.get(Math.min(this.intValue, this.items2.size() - 1));
            if (string.equals(shaderNodeDefinition.getText3())) {
               this.invoke6(1);
            }
         }
      }
   }

   public boolean check(String string) {
      return this.valuesByKey2.getOrDefault(string, false);
   }

   public Rect resolve2(Metrics metrics, int i, int j) {
      float floatValue = Math.min(metrics.measure(520.0F), i - metrics.measure(64.0F));
      float floatValue2 = Math.max(metrics.measure(28.0F), j * 0.14F);
      float floatValue3 = Math.min(metrics.measure(500.0F), j - floatValue2 - metrics.measure(28.0F));
      float floatValue4 = (i - floatValue) * 0.5F;
      return new Rect(floatValue4, floatValue2, floatValue, floatValue3);
   }

   public Rect resolve3(Metrics metrics2, int i, int j) {
      Rect rect = this.resolve2(metrics2, i, j);
      return new Rect(
         rect.x() + metrics2.measure(16.0F),
         rect.y() + metrics2.measure(16.0F),
         rect.w() - metrics2.measure(32.0F),
         metrics2.measure(46.0F)
      );
   }

   public ShaderNodeDefinition resolve4(Metrics metrics3, int i, int j, float f, float g) {
      ShaderNodeBrowser.ShaderNodeBrowserData shaderNodeBrowserData = this.resolve6(metrics3, i, j, f, g);
      return shaderNodeBrowserData == null ? null : shaderNodeBrowserData.definition;
   }

   public String resolve5(Metrics metrics4, int i, int j, float f, float g) {
      if (this.flag && this.text.isBlank()) {
         ShaderNodeBrowser.ShaderNodeBrowserData shaderNodeBrowserData2 = this.resolve6(metrics4, i, j, f, g);
         return shaderNodeBrowserData2 == null ? null : shaderNodeBrowserData2.category;
      } else {
         return null;
      }
   }

   private ShaderNodeBrowser.ShaderNodeBrowserData resolve6(Metrics metrics5, int i, int j, float f, float g) {
      if (!this.flag) {
         return null;
      } else {
         Rect rect2 = this.resolve2(metrics5, i, j);
         float floatValue5 = this.measure(metrics5, rect2);
         float floatValue6 = this.measure2(metrics5, rect2);
         if (!(f < rect2.x()) && !(f > rect2.x() + rect2.w()) && !(g < floatValue5) && !(g > floatValue6)) {
            float floatValue7 = floatValue5 - this.springAnimation.getFloatValue();
            String text = "";
            boolean flag2 = !this.text.isBlank();

            for (ShaderNodeSearch.ShaderNodeSearchData shaderNodeSearchData : this.items) {
               ShaderNodeDefinition shaderNodeDefinition2 = shaderNodeSearchData.def();
               if (!flag2 && !shaderNodeDefinition2.getText3().equals(text)) {
                  text = shaderNodeDefinition2.getText3();
                  if (g >= floatValue7 && g < floatValue7 + metrics5.measure(22.0F)) {
                     return new ShaderNodeBrowser.ShaderNodeBrowserData(null, text);
                  }

                  floatValue7 += metrics5.measure(22.0F);
                  if (this.check(text)) {
                     continue;
                  }
               } else if (!flag2 && this.check(shaderNodeDefinition2.getText3())) {
                  continue;
               }

               float floatValue8 = metrics5.measure(34.0F);
               if (g >= floatValue7 && g < floatValue7 + floatValue8) {
                  return new ShaderNodeBrowser.ShaderNodeBrowserData(shaderNodeDefinition2, null);
               }

               floatValue7 += floatValue8;
               if (floatValue7 > floatValue6) {
                  break;
               }
            }

            return null;
         } else {
            return null;
         }
      }
   }

   private float measure(Metrics metrics6, Rect rect3) {
      return rect3.y() + metrics6.measure(76.0F);
   }

   private float measure2(Metrics metrics7, Rect rect4) {
      return rect4.y() + rect4.h() - metrics7.measure(34.0F);
   }

   private void invoke9() {
      String text2 = this.text == null ? "" : this.text.toLowerCase(Locale.ROOT).trim();
      ArrayList arrayList = new ArrayList<>(this.shaderNodeRegistry.resolve2());
      if (this.shaderValueType != null) {
         ArrayList arrayList2 = new ArrayList();

         for (ShaderNodeDefinition shaderNodeDefinition3 : (List<ShaderNodeDefinition>)arrayList) {
            for (ShaderPin shaderPin : shaderNodeDefinition3.getItems()) {
               if (shaderPin.type() == this.shaderValueType) {
                  arrayList2.add(shaderNodeDefinition3);
                  break;
               }
            }
         }

         arrayList = arrayList2;
      }

      ArrayList arrayList3 = new ArrayList();
      if (text2.isEmpty()) {
         ((List<ShaderNodeDefinition>)arrayList).sort(Comparator.comparing(ShaderNodeDefinition::getText3).thenComparing(Comparator.comparing(ShaderNodeDefinition::getText2)));

         for (ShaderNodeDefinition shaderNodeDefinition4 : (List<ShaderNodeDefinition>)arrayList) {
            arrayList3.add(new ShaderNodeSearch.ShaderNodeSearchData(shaderNodeDefinition4, 0, new int[0]));
         }
      } else {
         for (ShaderNodeDefinition shaderNodeDefinition5 : (List<ShaderNodeDefinition>)arrayList) {
            ShaderNodeSearch.ShaderNodeSearchData shaderNodeSearchData2 = ShaderNodeSearch.resolve(shaderNodeDefinition5, text2);
            if (shaderNodeSearchData2 != null) {
               arrayList3.add(shaderNodeSearchData2);
            }
         }

         arrayList3.sort(Comparator.<ShaderNodeSearch.ShaderNodeSearchData>comparingInt(shaderNodeSearchData3 -> -shaderNodeSearchData3.score()).thenComparing(shaderNodeSearchData4 -> shaderNodeSearchData4.def().getText2()));
      }

      this.items = arrayList3;
      ArrayList arrayList4 = new ArrayList(arrayList3.size());

      for (ShaderNodeSearch.ShaderNodeSearchData shaderNodeSearchData5 : (List<ShaderNodeSearch.ShaderNodeSearchData>)arrayList3) {
         arrayList4.add(shaderNodeSearchData5.def());
      }

      this.items2 = arrayList4;
      if (this.intValue >= this.items2.size()) {
         this.intValue = Math.max(0, this.items2.size() - 1);
      }
   }

   public void invoke10(RenderManager renderManager, ThemeContext themeContext, ClickGuiState clickGuiState, int i, int j) {
      float floatValue9 = this.clampedSpringAnimation.measure();
      if (!(floatValue9 <= 0.004F)) {
         Metrics metrics8 = themeContext.getMetrics();
         ColorScheme colorScheme = themeContext.getColorScheme();
         boolean flag3 = colorScheme.isFlag();
         Rect rect5 = this.resolve2(metrics8, i, j);
         float floatValue10 = this.springAnimation
            .measure(Math.max(0.0F, Math.min(this.floatValue3, Math.max(0.0F, this.floatValue4))), SpringSpec.resolve8());
         renderManager.invoke48(16.0F);
         renderManager.invoke44(0.0F, 0.0F, (float)i, (float)j, 0.0F, floatValue9);
         renderManager.invoke5(
            0.0F,
            0.0F,
            (float)i,
            (float)j,
            0.0F,
            flag3 ? ColorScheme.compute5(236, 239, 246, Math.round(96.0F * floatValue9)) : ColorScheme.compute5(3, 5, 9, Math.round(150.0F * floatValue9))
         );
         float floatValue11 = rect5.x() + rect5.w() * 0.5F;
         float floatValue12 = rect5.y() + rect5.h() * 0.42F;
         renderManager.invoke62(0.92F + 0.08F * floatValue9, floatValue11, floatValue12);
         renderManager.invoke65(floatValue9);
         boolean flag4 = false ;

         try {
            flag4 = true;
            float floatValue13 = metrics8.measure(18.0F);
            renderManager.invoke41(
               rect5.x(),
               rect5.y(),
               rect5.w(),
               rect5.h(),
               floatValue13,
               metrics8.measure(36.0F),
               metrics8.measure(2.0F),
               flag3 ? ColorScheme.compute5(24, 32, 48, 44) : ColorScheme.compute5(0, 0, 0, 196)
            );
            renderManager.invoke5(
               rect5.x(), rect5.y(), rect5.w(), rect5.h(), floatValue13, flag3 ? ColorScheme.compute5(250, 251, 254, 246) : ColorScheme.compute5(9, 11, 17, 244)
            );
            renderManager.invoke28(rect5.x(), rect5.y(), rect5.w(), rect5.h(), floatValue13, ColorScheme.compute6(colorScheme.getIntValue14(), 96), 0.9F);
            renderManager.invoke33(
               rect5.x() + floatValue13,
               rect5.y(),
               rect5.w() - floatValue13 * 2.0F,
               1.2F,
               ColorScheme.compute6(colorScheme.getIntValue14(), 0),
               ColorScheme.compute6(colorScheme.getIntValue14(), 170)
            );
            this.invoke11(renderManager, metrics8, colorScheme, flag3, rect5);
            this.invoke12(renderManager, metrics8, colorScheme, clickGuiState, rect5, floatValue10, flag3);
            this.invoke15(renderManager, metrics8, colorScheme, rect5);
            flag4 = false;
         } finally {
            if (flag4) {
               renderManager.invoke66();
               renderManager.invoke64();
            }
         }

         renderManager.invoke66();
         renderManager.invoke64();
      }
   }

   private void invoke11(RenderManager renderManager2, Metrics metrics9, ColorScheme colorScheme2, boolean bl, Rect rect6) {
      Rect rect7 = new Rect(
         rect6.x() + metrics9.measure(16.0F),
         rect6.y() + metrics9.measure(16.0F),
         rect6.w() - metrics9.measure(32.0F),
         metrics9.measure(46.0F)
      );
      float floatValue14 = metrics9.measure(11.0F);
      renderManager2.invoke41(
         rect7.x(), rect7.y(), rect7.w(), rect7.h(), floatValue14, metrics9.measure(18.0F), 0.0F, ColorScheme.compute6(colorScheme2.getIntValue14(), 64)
      );
      renderManager2.invoke5(
         rect7.x(), rect7.y(), rect7.w(), rect7.h(), floatValue14, bl ? ColorScheme.compute5(255, 255, 255, 244) : ColorScheme.compute5(14, 16, 24, 240)
      );
      renderManager2.invoke28(rect7.x(), rect7.y(), rect7.w(), rect7.h(), floatValue14, ColorScheme.compute6(colorScheme2.getIntValue14(), 188), 1.1F);
      float floatValue15 = rect7.x() + metrics9.measure(19.0F);
      float floatValue16 = rect7.y() + rect7.h() * 0.5F - metrics9.measure(1.0F);
      renderManager2.invoke40(floatValue15, floatValue16, metrics9.measure(4.4F), 0.0F, 1.0F, 1.4F, ColorScheme.compute6(colorScheme2.getIntValue14(), 230));
      renderManager2.invoke5(
         floatValue15 + metrics9.measure(3.2F),
         floatValue16 + metrics9.measure(3.2F),
         metrics9.measure(5.4F),
         1.4F,
         0.7F,
         ColorScheme.compute6(colorScheme2.getIntValue14(), 230)
      );
      float floatValue17 = rect7.x() + metrics9.measure(34.0F);
      float floatValue18 = rect7.y() + (rect7.h() - metrics9.measure(15.0F)) * 0.5F;
      String text3 = this.text.isBlank() ? "Search nodes…" : this.text;
      int intValue4 = this.text.isBlank() ? colorScheme2.getIntValue12() : colorScheme2.getIntValue13();
      ClickGuiRenderUtils.invoke3(renderManager2, metrics9, FontRegistry.fontObject, floatValue17, floatValue18, 12.0F, text3, intValue4);
      boolean flag5 = (System.currentTimeMillis() - this.timestamp) / 500L % 2L == 0L;
      if (flag5) {
         float floatValue19 = floatValue17
            + (this.text.isBlank() ? 0.0F : ClickGuiRenderUtils.measure2(metrics9, FontRegistry.fontObject, this.text, 12.0F) + 1.5F);
         renderManager2.invoke5(
            floatValue19,
            rect7.y() + metrics9.measure(11.0F),
            1.2F,
            rect7.h() - metrics9.measure(22.0F),
            0.0F,
            ColorScheme.compute6(colorScheme2.getIntValue14(), 240)
         );
      }

      if (this.shaderValueType != null) {
         int intValue5 = ShaderEffectManager.ShaderEffectManagerState2.compute(this.shaderValueType);
         String text4 = "Connect → " + this.shaderValueType.getText();
         float floatValue20 = ClickGuiRenderUtils.measure2(metrics9, FontRegistry.fontObject, text4, 9.0F) + metrics9.measure(22.0F);
         float floatValue21 = rect7.x() + rect7.w() - floatValue20 - metrics9.measure(10.0F);
         float floatValue22 = rect7.y() + (rect7.h() - metrics9.measure(20.0F)) * 0.5F;
         renderManager2.invoke5(floatValue21, floatValue22, floatValue20, metrics9.measure(20.0F), metrics9.measure(10.0F), ColorScheme.compute6(intValue5, 46));
         renderManager2.invoke39(floatValue21 + metrics9.measure(9.0F), floatValue22 + metrics9.measure(10.0F), metrics9.measure(2.6F), 0.0F, 1.0F, intValue5);
         ClickGuiRenderUtils.invoke3(
            renderManager2,
            metrics9,
            FontRegistry.fontObject,
            floatValue21 + metrics9.measure(16.0F),
            floatValue22 + metrics9.measure(5.5F),
            9.0F,
            text4,
            ColorScheme.compute6(intValue5, 245)
         );
      }
   }

   private void invoke12(
      RenderManager renderManager3,
      Metrics metrics10,
      ColorScheme colorScheme3,
      ClickGuiState clickGuiState2,
      Rect rect8,
      float f,
      boolean bl
   ) {
      float floatValue23 = this.measure(metrics10, rect8);
      float floatValue24 = this.measure2(metrics10, rect8);
      float floatValue25 = rect8.x() + metrics10.measure(10.0F);
      float floatValue26 = rect8.w() - metrics10.measure(20.0F);
      renderManager3.invoke20();
      renderManager3.invoke24(
         floatValue25, floatValue23, floatValue26, floatValue24 - floatValue23, metrics10.measure(8.0F), metrics10.measure(8.0F), metrics10.measure(8.0F), metrics10.measure(8.0F)
      );

      try {
         float floatValue27 = floatValue23 - f;
         float floatValue28 = 0.0F;
         float floatValue29 = -1.0F;
         String text5 = "";
         int intValue6 = 0;
         boolean flag6 = !this.text.isBlank();
         String text6 = this.text.toLowerCase(Locale.ROOT).trim();

         for (ShaderNodeSearch.ShaderNodeSearchData shaderNodeSearchData6 : this.items) {
            ShaderNodeDefinition shaderNodeDefinition6 = shaderNodeSearchData6.def();
            if (!flag6 && !shaderNodeDefinition6.getText3().equals(text5)) {
               text5 = shaderNodeDefinition6.getText3();
               boolean flag7 = this.check(text5);
               if (floatValue27 + metrics10.measure(22.0F) > floatValue23 && floatValue27 < floatValue24) {
                  ClickGuiRenderUtils.invoke3(
                     renderManager3,
                     metrics10,
                     FontRegistry.fontObject4,
                     floatValue25 + metrics10.measure(12.0F),
                     floatValue27 + metrics10.measure(7.0F),
                     9.0F,
                     (flag7 ? "▸ " : "▾ ") + text5.toUpperCase(Locale.ROOT),
                     ColorScheme.compute6(colorScheme3.getIntValue15(), 215)
                  );
               }

               floatValue27 += metrics10.measure(22.0F);
               floatValue28 += metrics10.measure(22.0F);
               if (flag7) {
                  intValue6++;
                  continue;
               }
            } else if (!flag6 && this.check(shaderNodeDefinition6.getText3())) {
               intValue6++;
               continue;
            }

            float floatValue30 = metrics10.measure(34.0F);
            if (intValue6 == this.intValue) {
               floatValue29 = floatValue28;
            }

            if (floatValue27 + floatValue30 > floatValue23 && floatValue27 < floatValue24) {
               this.invoke13(
                  renderManager3, metrics10, colorScheme3, clickGuiState2, shaderNodeDefinition6, shaderNodeSearchData6, text6, floatValue25, floatValue27, floatValue26, floatValue30, intValue6 == this.intValue, bl
               );
            }

            floatValue27 += floatValue30;
            floatValue28 += floatValue30;
            intValue6++;
         }

         this.floatValue4 = Math.max(0.0F, floatValue28 - (floatValue24 - floatValue23));
         if (this.flag2 && floatValue29 >= 0.0F) {
            float floatValue31 = floatValue24 - floatValue23;
            float floatValue32 = metrics10.measure(34.0F);
            if (floatValue29 < this.floatValue3) {
               this.floatValue3 = Math.max(0.0F, floatValue29 - metrics10.measure(22.0F));
            } else if (floatValue29 + floatValue32 > this.floatValue3 + floatValue31) {
               this.floatValue3 = Math.min(this.floatValue4, floatValue29 + floatValue32 - floatValue31 + metrics10.measure(6.0F));
            }

            this.flag2 = false;
         }

         if (this.items.isEmpty()) {
            ClickGuiRenderUtils.invoke3(
               renderManager3,
               metrics10,
               FontRegistry.fontObject,
               floatValue25 + metrics10.measure(14.0F),
               floatValue23 + metrics10.measure(18.0F),
               11.0F,
               "no matching nodes",
               colorScheme3.getIntValue12()
            );
         }
      } finally {
         renderManager3.invoke20();
         renderManager3.invoke25();
      }

      if (this.floatValue4 > 0.0F) {
         float floatValue33 = floatValue24 - floatValue23;
         float floatValue34 = Math.max(metrics10.measure(26.0F), floatValue33 * floatValue33 / (floatValue33 + this.floatValue4));
         float floatValue35 = floatValue23 + (floatValue33 - floatValue34) * (this.floatValue4 <= 0.0F ? 0.0F : Math.min(1.0F, f / this.floatValue4));
         renderManager3.invoke5(
            rect8.x() + rect8.w() - metrics10.measure(6.0F),
            floatValue35,
            metrics10.measure(2.4F),
            floatValue34,
            metrics10.measure(1.2F),
            ColorScheme.compute6(colorScheme3.getIntValue14(), 130)
         );
      }
   }

   private void invoke13(
      RenderManager renderManager4,
      Metrics metrics11,
      ColorScheme colorScheme4,
      ClickGuiState clickGuiState3,
      ShaderNodeDefinition shaderNodeDefinition7,
      ShaderNodeSearch.ShaderNodeSearchData shaderNodeSearchData7,
      String string,
      float f,
      float g,
      float h,
      float i,
      boolean bl,
      boolean bl2
   ) {
      boolean flag8 = clickGuiState3 != null
         && clickGuiState3.getFloatValue() >= f
         && clickGuiState3.getFloatValue() <= f + h
         && clickGuiState3.getFloatValue2() >= g
         && clickGuiState3.getFloatValue2() < g + i;
      SpringAnimation springAnimation = this.valuesByKey.computeIfAbsent(shaderNodeDefinition7.getText(), stringx -> new SpringAnimation(0.0F));
      float floatValue36 = springAnimation.measure(Math.max(flag8 ? 0.72F : 0.0F, bl ? 1.0F : 0.0F), SpringSpec.resolve12());
      renderManager4.invoke5(
         f + metrics11.measure(4.0F),
         g + metrics11.measure(1.5F),
         h - metrics11.measure(8.0F),
         i - metrics11.measure(3.0F),
         metrics11.measure(8.0F),
         ColorScheme.compute7(
            bl2 ? ColorScheme.compute5(10, 14, 22, 5) : ColorScheme.compute5(255, 255, 255, 5),
            ColorScheme.compute6(colorScheme4.getIntValue14(), 62),
            floatValue36
         )
      );
      if (bl) {
         renderManager4.invoke5(
            f + metrics11.measure(4.0F),
            g + metrics11.measure(7.0F),
            metrics11.measure(2.4F),
            i - metrics11.measure(14.0F),
            metrics11.measure(1.2F),
            ColorScheme.compute6(colorScheme4.getIntValue14(), 235)
         );
      }

      int intValue7 = shaderNodeDefinition7.getItems2().isEmpty()
         ? colorScheme4.getIntValue11()
         : ShaderEffectManager.ShaderEffectManagerState2.compute(shaderNodeDefinition7.getItems2().get(0).type());
      float floatValue37 = f + metrics11.measure(18.0F);
      float floatValue38 = g + i * 0.5F;
      renderManager4.invoke39(floatValue37, floatValue38, metrics11.measure(3.4F) + floatValue36 * metrics11.measure(0.8F), 0.0F, 1.0F, ColorScheme.compute6(intValue7, 235));
      renderManager4.invoke39(
         floatValue37, floatValue38, metrics11.measure(1.4F), 0.0F, 1.0F, bl2 ? ColorScheme.compute5(255, 255, 255, 235) : ColorScheme.compute5(9, 11, 17, 235)
      );
      int intValue8 = ColorScheme.compute7(colorScheme4.getIntValue12(), colorScheme4.getIntValue13(), 0.62F + floatValue36 * 0.38F);
      this.invoke14(
         renderManager4,
         metrics11,
         colorScheme4,
         shaderNodeDefinition7.getText2(),
         shaderNodeSearchData7.titlePositions(),
         string,
         f + metrics11.measure(32.0F),
         g + metrics11.measure(7.0F),
         11.0F,
         intValue8
      );
      ClickGuiRenderUtils.invoke3(
         renderManager4,
         metrics11,
         FontRegistry.fontObject,
         f + metrics11.measure(32.0F),
         g + metrics11.measure(20.0F),
         7.5F,
         shaderNodeDefinition7.getText3(),
         ColorScheme.compute6(colorScheme4.getIntValue12(), 200)
      );
      float floatValue39 = f + h - metrics11.measure(14.0F);
      int intValue9 = Math.min(shaderNodeDefinition7.getItems().size(), 4);

      for (int intValue10 = intValue9 - 1; intValue10 >= 0; intValue10--) {
         int intValue11 = ShaderEffectManager.ShaderEffectManagerState2.compute(shaderNodeDefinition7.getItems().get(intValue10).type());
         renderManager4.invoke39(floatValue39, floatValue38, metrics11.measure(2.2F), 0.0F, 1.0F, ColorScheme.compute6(intValue11, 225));
         floatValue39 -= metrics11.measure(7.0F);
      }

      String text7 = shaderNodeDefinition7.getItems2().isEmpty() ? "sink" : shaderNodeDefinition7.getItems2().get(0).type().getText();
      float floatValue40 = ClickGuiRenderUtils.measure2(metrics11, FontRegistry.fontObject, text7, 8.0F);
      ClickGuiRenderUtils.invoke3(
         renderManager4,
         metrics11,
         FontRegistry.fontObject,
         floatValue39 - floatValue40 - metrics11.measure(8.0F),
         g + metrics11.measure(11.0F),
         8.0F,
         text7,
         ColorScheme.compute6(intValue7, 240)
      );
   }

   private void invoke14(
      RenderManager renderManager5,
      Metrics metrics12,
      ColorScheme colorScheme5,
      String string,
      int[] is,
      String string2,
      float f,
      float g,
      float h,
      int i
   ) {
      if (is != null && is.length != 0 && !string2.isEmpty()) {
         int intValue12 = ColorScheme.compute6(colorScheme5.getIntValue14(), 250);
         float floatValue41 = f;
         int intValue13 = 0;
         int intValue14 = 0;

         while (intValue13 < string.length()) {
            boolean flag9 = intValue14 < is.length && is[intValue14] == intValue13;
            int intValue15 = intValue13;
            if (flag9) {
               while (intValue14 < is.length && is[intValue14] == intValue15) {
                  intValue14++;
                  intValue15++;
               }
            } else {
               int intValue16 = intValue14 < is.length ? is[intValue14] : string.length();
               intValue15 = Math.max(intValue13 + 1, intValue16);
            }

            intValue15 = Math.min(intValue15, string.length());
            String text8 = string.substring(intValue13, intValue15);
            ClickGuiRenderUtils.invoke3(renderManager5, metrics12, FontRegistry.fontObject4, floatValue41, g, h, text8, flag9 ? intValue12 : i);
            floatValue41 += ClickGuiRenderUtils.measure2(metrics12, FontRegistry.fontObject4, text8, h);
            intValue13 = intValue15;
         }
      } else {
         ClickGuiRenderUtils.invoke3(renderManager5, metrics12, FontRegistry.fontObject4, f, g, h, string, i);
      }
   }

   private void invoke15(RenderManager renderManager6, Metrics metrics13, ColorScheme colorScheme6, Rect rect9) {
      ClickGuiRenderUtils.invoke3(
         renderManager6,
         metrics13,
         FontRegistry.fontObject,
         rect9.x() + metrics13.measure(16.0F),
         rect9.y() + rect9.h() - metrics13.measure(24.0F),
         8.0F,
         "↑↓ navigate • Enter spawn • LMB on category to toggle • Esc close",
         ColorScheme.compute6(colorScheme6.getIntValue13(), 150)
      );
      String text9 = this.items2.size() + (this.items2.size() == 1 ? " node" : " nodes");
      float floatValue42 = ClickGuiRenderUtils.measure2(metrics13, FontRegistry.fontObject, text9, 8.0F);
      ClickGuiRenderUtils.invoke3(
         renderManager6,
         metrics13,
         FontRegistry.fontObject,
         rect9.x() + rect9.w() - floatValue42 - metrics13.measure(16.0F),
         rect9.y() + rect9.h() - metrics13.measure(24.0F),
         8.0F,
         text9,
         ColorScheme.compute6(colorScheme6.getIntValue15(), 210)
      );
   }

   record ShaderNodeBrowserData(ShaderNodeDefinition definition, String category) {
   }
}
