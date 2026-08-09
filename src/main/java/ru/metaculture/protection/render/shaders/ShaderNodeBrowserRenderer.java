package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class ShaderNodeBrowserRenderer {
   private final ShaderNodeRegistry shaderNodeRegistry;
   private boolean flag;
   private float floatValue;
   private float floatValue2;
   private String text = "";
   private int intValue;
   private float floatValue3;
   private long timestamp;
   private ShaderValueType shaderValueType;
   private List<ShaderNodeDefinition> items = new ArrayList<>();
   private final Map<String, Boolean> valuesByKey = new LinkedHashMap<>();

   public ShaderNodeBrowserRenderer(ShaderNodeRegistry shaderNodeRegistry) {
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
      this.timestamp = System.currentTimeMillis();
      this.shaderValueType = shaderValueType;
      this.invoke9();
   }

   public void invoke2() {
      this.flag = false;
      this.shaderValueType = null;
   }

   public void invoke3(char c) {
      if (this.flag) {
         if ((c >= '0' && c <= '9' || c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c == ' ' || c == '_' || c == '.') && this.text.length() < 32) {
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
      if (this.flag && !this.items.isEmpty()) {
         this.intValue = Math.floorMod(this.intValue + i, this.items.size());
      }
   }

   public ShaderNodeDefinition resolve() {
      return this.items.isEmpty() ? null : this.items.get(Math.min(this.intValue, this.items.size() - 1));
   }

   public List<ShaderNodeDefinition> getItems() {
      return this.items;
   }

   public void invoke7(double d) {
      if (this.flag) {
         this.floatValue3 = Math.max(0.0F, this.floatValue3 - (float)d * 24.0F);
      }
   }

   public void invoke8(String string) {
      if (string != null) {
         this.valuesByKey.put(string, !this.valuesByKey.getOrDefault(string, false));
      }
   }

   public boolean check(String string) {
      return this.valuesByKey.getOrDefault(string, false);
   }

   public Rect resolve2(Metrics metrics, int i, int j) {
      float floatValue = metrics.measure(340.0F);
      float floatValue2 = metrics.measure(440.0F);
      float floatValue3 = Math.max(metrics.measure(16.0F), Math.min(this.floatValue - floatValue * 0.18F, i - floatValue - metrics.measure(16.0F)));
      float floatValue4 = Math.max(metrics.measure(16.0F), Math.min(this.floatValue2 - metrics.measure(28.0F), j - floatValue2 - metrics.measure(16.0F)));
      return new Rect(floatValue3, floatValue4, floatValue, floatValue2);
   }

   public Rect resolve3(Metrics metrics2, int i, int j) {
      Rect rect = this.resolve2(metrics2, i, j);
      return new Rect(
         rect.x() + metrics2.measure(12.0F),
         rect.y() + metrics2.measure(38.0F),
         rect.w() - metrics2.measure(24.0F),
         metrics2.measure(30.0F)
      );
   }

   public ShaderNodeDefinition resolve4(Metrics metrics3, int i, int j, float f, float g) {
      if (!this.flag) {
         return null;
      } else {
         Rect rect2 = this.resolve2(metrics3, i, j);
         float floatValue5 = rect2.y() + metrics3.measure(80.0F);
         float floatValue6 = rect2.y() + rect2.h() - metrics3.measure(40.0F);
         if (!(f < rect2.x()) && !(f > rect2.x() + rect2.w()) && !(g < floatValue5) && !(g > floatValue6)) {
            float floatValue7 = floatValue5 - this.floatValue3;
            String text = "";

            for (ShaderNodeDefinition shaderNodeDefinition : this.items) {
               if (!shaderNodeDefinition.getText3().equals(text)) {
                  text = shaderNodeDefinition.getText3();
                  if (g >= floatValue7 && g < floatValue7 + metrics3.measure(20.0F)) {
                     return null;
                  }

                  floatValue7 += metrics3.measure(20.0F);
                  if (this.check(text) && this.text.isBlank()) {
                     continue;
                  }
               } else if (this.check(text) && this.text.isBlank()) {
                  continue;
               }

               float floatValue8 = metrics3.measure(28.0F);
               if (g >= floatValue7 && g < floatValue7 + floatValue8) {
                  return shaderNodeDefinition;
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

   public String resolve5(Metrics metrics4, int i, int j, float f, float g) {
      if (this.flag && this.text.isBlank()) {
         Rect rect3 = this.resolve2(metrics4, i, j);
         float floatValue9 = rect3.y() + metrics4.measure(80.0F);
         float floatValue10 = rect3.y() + rect3.h() - metrics4.measure(40.0F);
         if (!(f < rect3.x()) && !(f > rect3.x() + rect3.w()) && !(g < floatValue9) && !(g > floatValue10)) {
            float floatValue11 = floatValue9 - this.floatValue3;
            String text2 = "";

            for (ShaderNodeDefinition shaderNodeDefinition2 : this.items) {
               if (!shaderNodeDefinition2.getText3().equals(text2)) {
                  text2 = shaderNodeDefinition2.getText3();
                  if (g >= floatValue11 && g < floatValue11 + metrics4.measure(20.0F)) {
                     return text2;
                  }

                  floatValue11 += metrics4.measure(20.0F);
                  if (this.check(text2)) {
                     continue;
                  }
               } else if (this.check(text2)) {
                  continue;
               }

               floatValue11 += metrics4.measure(28.0F);
               if (floatValue11 > floatValue10) {
                  break;
               }
            }

            return null;
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   private void invoke9() {
      String text3 = this.text == null ? "" : this.text.toLowerCase(Locale.ROOT).trim();
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

      if (text3.isEmpty()) {
         arrayList.sort(Comparator.<ShaderNodeDefinition, String>comparing(ShaderNodeDefinition::getText3).thenComparing(Comparator.comparing(ShaderNodeDefinition::getText2)));
         this.items = arrayList;
      } else {
         ArrayList arrayList3 = new ArrayList();

         for (ShaderNodeDefinition shaderNodeDefinition4 : (List<ShaderNodeDefinition>)arrayList) {
            int intValue = compute(shaderNodeDefinition4, text3);
            if (intValue > 0) {
               arrayList3.add(new ShaderNodeBrowserRenderer.ShaderNodeBrowserRendererData(shaderNodeDefinition4, intValue));
            }
         }

         arrayList3.sort(Comparator.<ShaderNodeBrowserRenderer.ShaderNodeBrowserRendererData>comparingInt(shaderNodeBrowserRendererData -> -shaderNodeBrowserRendererData.score).thenComparing(shaderNodeBrowserRendererData2 -> shaderNodeBrowserRendererData2.def.getText2()));
         ArrayList arrayList4 = new ArrayList();

         for (ShaderNodeBrowserRenderer.ShaderNodeBrowserRendererData shaderNodeBrowserRendererData3 : (List<ShaderNodeBrowserRenderer.ShaderNodeBrowserRendererData>)arrayList3) {
            arrayList4.add(shaderNodeBrowserRendererData3.def);
         }

         this.items = arrayList4;
      }
   }

   private static int compute(ShaderNodeDefinition shaderNodeDefinition5, String string) {
      String text4 = shaderNodeDefinition5.getText2().toLowerCase(Locale.ROOT);
      String text5 = shaderNodeDefinition5.getText3().toLowerCase(Locale.ROOT);
      String text6 = shaderNodeDefinition5.getText().toLowerCase(Locale.ROOT);
      byte byteValue = 0;
      if (text4.startsWith(string)) {
         byteValue += 80;
      }

      if (text4.contains(string)) {
         byteValue += 40;
      }

      if (text6.contains(string)) {
         byteValue += 30;
      }

      if (text5.contains(string)) {
         byteValue += 15;
      }

      int intValue2 = 0;
      int intValue3 = 0;

      for (int intValue4 = 0; intValue4 < string.length(); intValue4++) {
         int intValue5 = text4.indexOf(string.charAt(intValue4), intValue3);
         if (intValue5 < 0) {
            break;
         }

         intValue2++;
         intValue3 = intValue5 + 1;
      }

      if (intValue2 == string.length()) {
         byteValue += 25;
      }

      return byteValue;
   }

   public void invoke10(RenderManager renderManager, ThemeContext themeContext, ClickGuiState clickGuiState, int i, int j) {
      if (this.flag) {
         Metrics metrics5 = themeContext.getMetrics();
         ColorScheme colorScheme = themeContext.getColorScheme();
         Rect rect4 = this.resolve2(metrics5, i, j);
         float floatValue12 = metrics5.measure(12.0F);
         renderManager.invoke41(
            rect4.x(),
            rect4.y(),
            rect4.w(),
            rect4.h(),
            floatValue12,
            metrics5.measure(28.0F),
            metrics5.measure(2.0F),
            colorScheme.isFlag() ? ColorScheme.compute5(10, 31, 10, 30) : ColorScheme.compute5(0, 0, 0, 168)
         );
         renderManager.invoke5(
            rect4.x(),
            rect4.y(),
            rect4.w(),
            rect4.h(),
            floatValue12,
            colorScheme.isFlag()
               ? ColorScheme.compute7(ColorScheme.compute5(255, 255, 255, 246), ColorScheme.compute6(colorScheme.getIntValue14(), 246), 0.035F)
               : ColorScheme.compute5(8, 10, 16, 240)
         );
         renderManager.invoke28(rect4.x(), rect4.y(), rect4.w(), rect4.h(), floatValue12, ColorScheme.compute6(colorScheme.getIntValue14(), 108), 0.9F);
         ClickGuiRenderUtils.invoke3(
            renderManager,
            metrics5,
            FontRegistry.fontObject4,
            rect4.x() + metrics5.measure(14.0F),
            rect4.y() + metrics5.measure(14.0F),
            12.0F,
            this.shaderValueType != null ? "Connect → " + this.shaderValueType.getText() : "Node Browser",
            colorScheme.getIntValue13()
         );
         ClickGuiRenderUtils.invoke3(
            renderManager,
            metrics5,
            FontRegistry.fontObject,
            rect4.x() + rect4.w() - metrics5.measure(70.0F),
            rect4.y() + metrics5.measure(16.0F),
            8.0F,
            "Enter • Esc",
            ColorScheme.compute6(colorScheme.getIntValue14(), 200)
         );
         Rect rect5 = this.resolve3(metrics5, i, j);
         renderManager.invoke5(
            rect5.x(),
            rect5.y(),
            rect5.w(),
            rect5.h(),
            metrics5.measure(7.0F),
            colorScheme.isFlag()
               ? ColorScheme.compute7(ColorScheme.compute5(255, 255, 255, 242), ColorScheme.compute6(colorScheme.getIntValue14(), 242), 0.028F)
               : ColorScheme.compute5(14, 16, 22, 232)
         );
         renderManager.invoke28(rect5.x(), rect5.y(), rect5.w(), rect5.h(), metrics5.measure(7.0F), ColorScheme.compute6(colorScheme.getIntValue14(), 156), 0.8F);
         renderManager.invoke39(
            rect5.x() + metrics5.measure(11.0F), rect5.y() + rect5.h() * 0.5F, metrics5.measure(3.4F), 0.0F, 1.0F, ColorScheme.compute6(colorScheme.getIntValue14(), 220)
         );
         renderManager.invoke5(
            rect5.x() + metrics5.measure(13.5F),
            rect5.y() + rect5.h() * 0.5F + metrics5.measure(1.4F),
            metrics5.measure(6.0F),
            1.1F,
            0.0F,
            ColorScheme.compute6(colorScheme.getIntValue14(), 220)
         );
         String text7 = this.text.isBlank() ? "type to search…" : this.text;
         int intValue6 = this.text.isBlank() ? colorScheme.getIntValue12() : colorScheme.getIntValue13();
         ClickGuiRenderUtils.invoke3(
            renderManager, metrics5, FontRegistry.fontObject, rect5.x() + metrics5.measure(22.0F), rect5.y() + metrics5.measure(8.0F), 10.0F, text7, intValue6
         );
         if (!this.text.isBlank()) {
            float floatValue13 = ClickGuiRenderUtils.measure2(metrics5, FontRegistry.fontObject, this.text, 10.0F);
            boolean flag = (System.currentTimeMillis() - this.timestamp) / 500L % 2L == 0L;
            if (flag) {
               renderManager.invoke5(
                  rect5.x() + metrics5.measure(22.0F) + floatValue13 + 1.0F,
                  rect5.y() + metrics5.measure(6.0F),
                  1.0F,
                  rect5.h() - metrics5.measure(12.0F),
                  0.0F,
                  ColorScheme.compute6(colorScheme.getIntValue14(), 240)
               );
            }
         }

         float floatValue14 = rect4.y() + metrics5.measure(80.0F);
         float floatValue15 = rect4.y() + rect4.h() - metrics5.measure(40.0F);
         renderManager.invoke20();
         renderManager.invoke24(
            rect4.x() + metrics5.measure(8.0F),
            floatValue14,
            rect4.w() - metrics5.measure(16.0F),
            floatValue15 - floatValue14,
            metrics5.measure(6.0F),
            metrics5.measure(6.0F),
            metrics5.measure(6.0F),
            metrics5.measure(6.0F)
         );

         try {
            float floatValue16 = floatValue14 - this.floatValue3;
            String text8 = "";
            int intValue7 = 0;
            String text9 = this.text.toLowerCase(Locale.ROOT);

            for (ShaderNodeDefinition shaderNodeDefinition6 : this.items) {
               if (!shaderNodeDefinition6.getText3().equals(text8)) {
                  text8 = shaderNodeDefinition6.getText3();
                  boolean flag2 = this.text.isBlank() && this.check(text8);
                  ClickGuiRenderUtils.invoke3(
                     renderManager,
                     metrics5,
                     FontRegistry.fontObject4,
                     rect4.x() + metrics5.measure(20.0F),
                     floatValue16 + metrics5.measure(6.0F),
                     9.0F,
                     (flag2 ? "▸ " : "▾ ") + text8.toUpperCase(Locale.ROOT),
                     ColorScheme.compute6(colorScheme.getIntValue15(), 220)
                  );
                  floatValue16 += metrics5.measure(20.0F);
                  if (flag2) {
                     continue;
                  }
               } else if (this.text.isBlank() && this.check(text8)) {
                  continue;
               }

               float floatValue17 = metrics5.measure(28.0F);
               boolean flag3 = clickGuiState != null
                  && clickGuiState.getFloatValue() >= rect4.x() + metrics5.measure(12.0F)
                  && clickGuiState.getFloatValue() <= rect4.x() + rect4.w() - metrics5.measure(12.0F)
                  && clickGuiState.getFloatValue2() >= floatValue16
                  && clickGuiState.getFloatValue2() < floatValue16 + floatValue17;
               boolean flag4 = intValue7 == this.intValue;
               float floatValue18 = Math.max(flag3 ? 0.7F : 0.0F, flag4 ? 1.0F : 0.0F);
               renderManager.invoke5(
                  rect4.x() + metrics5.measure(12.0F),
                  floatValue16,
                  rect4.w() - metrics5.measure(24.0F),
                  floatValue17 - metrics5.measure(2.0F),
                  metrics5.measure(6.0F),
                  ColorScheme.compute7(ColorScheme.compute5(255, 255, 255, 6), ColorScheme.compute6(colorScheme.getIntValue14(), 72), floatValue18)
               );
               renderManager.invoke39(
                  rect4.x() + metrics5.measure(22.0F),
                  floatValue16 + floatValue17 * 0.5F - metrics5.measure(1.0F),
                  metrics5.measure(2.6F),
                  0.0F,
                  1.0F,
                  ColorScheme.compute7(colorScheme.getIntValue11(), colorScheme.getIntValue14(), floatValue18)
               );
               this.invoke11(
                  renderManager, metrics5, colorScheme, shaderNodeDefinition6.getText2(), text9, rect4.x() + metrics5.measure(34.0F), floatValue16 + metrics5.measure(5.0F), 10.0F, floatValue18
               );
               String text10 = shaderNodeDefinition6.getItems2().isEmpty() ? "output ✕" : shaderNodeDefinition6.getItems2().get(0).type().getText();
               ClickGuiRenderUtils.invoke3(
                  renderManager,
                  metrics5,
                  FontRegistry.fontObject,
                  rect4.x() + rect4.w() - metrics5.measure(60.0F),
                  floatValue16 + metrics5.measure(8.0F),
                  8.0F,
                  text10,
                  ColorScheme.compute6(colorScheme.getIntValue15(), 220)
               );
               floatValue16 += floatValue17;
               intValue7++;
               if (floatValue16 > floatValue15 + floatValue17) {
                  break;
               }
            }

            if (this.items.isEmpty()) {
               ClickGuiRenderUtils.invoke3(
                  renderManager,
                  metrics5,
                  FontRegistry.fontObject,
                  rect4.x() + metrics5.measure(20.0F),
                  floatValue14 + metrics5.measure(20.0F),
                  10.0F,
                  "no matches",
                  colorScheme.getIntValue12()
               );
            }
         } finally {
            renderManager.invoke20();
            renderManager.invoke25();
         }

         ClickGuiRenderUtils.invoke3(
            renderManager,
            metrics5,
            FontRegistry.fontObject,
            rect4.x() + metrics5.measure(14.0F),
            rect4.y() + rect4.h() - metrics5.measure(20.0F),
            8.0F,
            "↑↓ navigate • Enter spawn • LMB on category to toggle • Wheel scroll",
            ColorScheme.compute6(colorScheme.getIntValue13(), 156)
         );
      }
   }

   private void invoke11(
      RenderManager renderManager2, Metrics metrics6, ColorScheme colorScheme2, String string, String string2, float f, float g, float h, float i
   ) {
      int intValue8 = ColorScheme.compute7(colorScheme2.getIntValue12(), colorScheme2.getIntValue13(), 0.6F + i * 0.4F);
      if (string2 != null && !string2.isEmpty()) {
         String text11 = string.toLowerCase(Locale.ROOT);
         int intValue9 = text11.indexOf(string2);
         if (intValue9 < 0) {
            ClickGuiRenderUtils.invoke3(renderManager2, metrics6, FontRegistry.fontObject4, f, g, h, string, intValue8);
         } else {
            String text12 = string.substring(0, intValue9);
            String text13 = string.substring(intValue9, intValue9 + string2.length());
            String text14 = string.substring(intValue9 + string2.length());
            float floatValue19 = ClickGuiRenderUtils.measure2(metrics6, FontRegistry.fontObject4, text12, h);
            float floatValue20 = ClickGuiRenderUtils.measure2(metrics6, FontRegistry.fontObject4, text13, h);
            ClickGuiRenderUtils.invoke3(renderManager2, metrics6, FontRegistry.fontObject4, f, g, h, text12, intValue8);
            ClickGuiRenderUtils.invoke3(
               renderManager2, metrics6, FontRegistry.fontObject4, f + floatValue19, g, h, text13, ColorScheme.compute6(colorScheme2.getIntValue14(), 245)
            );
            ClickGuiRenderUtils.invoke3(renderManager2, metrics6, FontRegistry.fontObject4, f + floatValue19 + floatValue20, g, h, text14, intValue8);
         }
      } else {
         ClickGuiRenderUtils.invoke3(renderManager2, metrics6, FontRegistry.fontObject4, f, g, h, string, intValue8);
      }
   }

   record ShaderNodeBrowserRendererData(ShaderNodeDefinition def, int score) {
   }
}
