package ru.metaculture.protection;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public final class ShaderLibraryBrowser {
   private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ROOT);
   private final ClampedSpringAnimation clampedSpringAnimation = new ClampedSpringAnimation(
      AnimationSystem.getINSTANCE(), SpringConfig.resolve(2.5F, 0.82F), 0.0F, 0.0F, 1.0F, 0.001F, 0.001F
   );
   private final ClampedSpringAnimation clampedSpringAnimation2 = new ClampedSpringAnimation(
      AnimationSystem.getINSTANCE(), SpringConfig.resolve(2.2F, 0.86F), 0.0F, 0.0F, 100000.0F, 0.01F, 0.01F
   );
   private final List<File> items = new ArrayList<>();
   private boolean flag;
   private String text = "";
   private int intValue = -1;
   private int intValue2 = -1;
   private float floatValue;
   private File file;

   public void invoke(List<File> list) {
      this.items.clear();
      if (list != null) {
         this.items.addAll(list);
      }

      this.flag = true;
      this.text = "";
      this.intValue = -1;
      this.intValue2 = this.items.isEmpty() ? -1 : 0;
      this.file = null;
      this.floatValue = 0.0F;
      this.clampedSpringAnimation2.invoke(0.0F);
      this.clampedSpringAnimation.invoke2(1.0F);
   }

   public void invoke2() {
      this.flag = false;
      this.clampedSpringAnimation.invoke2(0.0F);
   }

   public boolean isFlag() {
      return this.flag;
   }

   public File resolve() {
      File file = this.file;
      this.file = null;
      return file;
   }

   public boolean check(float f, float g, int i, Metrics metrics, int j, int k) {
      if (!this.flag) {
         return false;
      } else if (i != 0) {
         return true;
      } else {
         Rect rect = this.resolve3(metrics, j, k);
         Rect rect2 = this.resolve6(rect, metrics);
         Rect rect3 = this.resolve7(rect, metrics);
         if (!rect3.contains(f, g) && rect.contains(f, g)) {
            List items = this.resolve2();
            if (rect2.contains(f, g)) {
               if (this.intValue2 >= 0 && this.intValue2 < items.size()) {
                  this.file = (File)items.get(this.intValue2);
                  this.invoke2();
               }

               return true;
            } else {
               Rect rect4 = this.resolve5(rect, metrics);
               if (rect4.contains(f, g)) {
                  float floatValue = metrics.measure(42.0F);
                  int intValue = (int)Math.floor((g - rect4.y() + this.clampedSpringAnimation2.measure()) / floatValue);
                  if (intValue >= 0 && intValue < items.size()) {
                     this.intValue2 = intValue;
                  }

                  return true;
               } else {
                  return true;
               }
            }
         } else {
            this.invoke2();
            return true;
         }
      }
   }

   public boolean check2(double d, Metrics metrics2, int i, int j) {
      if (!this.flag) {
         return false;
      } else {
         Rect rect5 = this.resolve5(this.resolve3(metrics2, i, j), metrics2);
         float floatValue2 = this.resolve2().size() * metrics2.measure(42.0F);
         float floatValue3 = Math.max(0.0F, floatValue2 - rect5.h());
         this.floatValue = Math.max(0.0F, Math.min(floatValue3, this.floatValue - (float)d * metrics2.measure(42.0F)));
         this.clampedSpringAnimation2.invoke2(this.floatValue);
         return true;
      }
   }

   public boolean check3(char c) {
      if (!this.flag) {
         return false;
      } else {
         if ((Character.isLetterOrDigit(c) || c == ' ' || c == '_' || c == '-' || c == '.') && this.text.length() < 64) {
            this.text = this.text + c;
            this.intValue2 = this.resolve2().isEmpty() ? -1 : 0;
            this.floatValue = 0.0F;
            this.clampedSpringAnimation2.invoke(0.0F);
         }

         return true;
      }
   }

   public boolean check4(int i) {
      if (!this.flag) {
         return false;
      } else {
         List items2 = this.resolve2();
         if (i == 256) {
            this.invoke2();
            return true;
         } else if (i == 259) {
            if (!this.text.isEmpty()) {
               this.text = this.text.substring(0, this.text.length() - 1);
               this.intValue2 = this.resolve2().isEmpty() ? -1 : 0;
               this.floatValue = 0.0F;
               this.clampedSpringAnimation2.invoke(0.0F);
            }

            return true;
         } else if (i == 264) {
            if (!items2.isEmpty()) {
               this.intValue2 = Math.min(items2.size() - 1, Math.max(0, this.intValue2 + 1));
            }

            return true;
         } else if (i == 265) {
            if (!items2.isEmpty()) {
               this.intValue2 = Math.max(0, this.intValue2 - 1);
            }

            return true;
         } else if (i != 257 && i != 335) {
            return true;
         } else {
            if (this.intValue2 >= 0 && this.intValue2 < items2.size()) {
               this.file = (File)items2.get(this.intValue2);
               this.invoke2();
            }

            return true;
         }
      }
   }

   public void invoke3(RenderManager renderManager, Metrics metrics3, ColorScheme colorScheme, float f, float g, int i, int j) {
      float floatValue4 = this.clampedSpringAnimation.measure();
      if (!(floatValue4 <= 0.001F) && renderManager != null && metrics3 != null && colorScheme != null) {
         Rect rect6 = this.resolve3(metrics3, i, j);
         float floatValue5 = metrics3.measure(14.0F) * (1.0F - floatValue4);
         rect6 = new Rect(rect6.x(), rect6.y() + floatValue5, rect6.w(), rect6.h());
         renderManager.invoke65(floatValue4);

         try {
            renderManager.invoke5(0.0F, 0.0F, (float)i, (float)j, 0.0F, ColorScheme.compute5(0, 0, 0, colorScheme.isFlag() ? 72 : 116));
            float floatValue6 = metrics3.measure(14.0F);
            renderManager.invoke41(
               rect6.x(), rect6.y(), rect6.w(), rect6.h(), floatValue6, metrics3.measure(26.0F), metrics3.measure(2.0F), ColorScheme.compute5(0, 0, 0, 164)
            );
            renderManager.invoke5(rect6.x(), rect6.y(), rect6.w(), rect6.h(), floatValue6, this.compute(colorScheme, 238));
            renderManager.invoke28(rect6.x(), rect6.y(), rect6.w(), rect6.h(), floatValue6, ColorScheme.compute6(colorScheme.getIntValue14(), 92), 0.8F);
            ClickGuiRenderUtils.invoke3(
               renderManager,
               metrics3,
               FontRegistry.fontObject4,
               rect6.x() + metrics3.measure(20.0F),
               rect6.y() + metrics3.measure(18.0F),
               13.0F,
               "Import Foundry Shader",
               colorScheme.getIntValue13()
            );
            this.invoke4(renderManager, metrics3, colorScheme, rect6);
            this.invoke5(renderManager, metrics3, colorScheme, rect6, f, g);
            this.invoke6(renderManager, metrics3, colorScheme, rect6, f, g);
         } finally {
            renderManager.invoke66();
         }
      }
   }

   private void invoke4(RenderManager renderManager2, Metrics metrics4, ColorScheme colorScheme2, Rect rect7) {
      Rect rect8 = this.resolve4(rect7, metrics4);
      renderManager2.invoke5(
         rect8.x(), rect8.y(), rect8.w(), rect8.h(), metrics4.measure(8.0F), ColorScheme.compute5(255, 255, 255, colorScheme2.isFlag() ? 126 : 16)
      );
      renderManager2.invoke28(
         rect8.x(), rect8.y(), rect8.w(), rect8.h(), metrics4.measure(8.0F), ColorScheme.compute6(colorScheme2.getIntValue14(), 82), 0.7F
      );
      String text = this.text.isBlank() ? "Search" : this.text;
      int intValue2 = this.text.isBlank() ? colorScheme2.getIntValue12() : colorScheme2.getIntValue13();
      ClickGuiRenderUtils.invoke4(
         renderManager2, metrics4, FontRegistry.fontObject, rect8.x() + metrics4.measure(12.0F), rect8.y(), rect8.h(), 10.0F, text, intValue2
      );
   }

   private void invoke5(RenderManager renderManager3, Metrics metrics5, ColorScheme colorScheme3, Rect rect9, float f, float g) {
      Rect rect10 = this.resolve5(rect9, metrics5);
      List items3 = this.resolve2();
      float floatValue7 = metrics5.measure(42.0F);
      this.intValue = -1;
      renderManager3.invoke20();
      renderManager3.invoke24(
         rect10.x(),
         rect10.y(),
         rect10.w(),
         rect10.h(),
         metrics5.measure(8.0F),
         metrics5.measure(8.0F),
         metrics5.measure(8.0F),
         metrics5.measure(8.0F)
      );
      boolean flag = false ;

      try {
         flag = true;
         renderManager3.invoke5(
            rect10.x(), rect10.y(), rect10.w(), rect10.h(), metrics5.measure(8.0F), ColorScheme.compute5(255, 255, 255, colorScheme3.isFlag() ? 82 : 10)
         );
         float floatValue8 = this.clampedSpringAnimation2.measure();
         if (items3.isEmpty()) {
            String text2 = this.items.isEmpty() ? "No shared shaders" : "No matches";
            float floatValue9 = ClickGuiRenderUtils.measure2(metrics5, FontRegistry.fontObject, text2, 10.0F);
            ClickGuiRenderUtils.invoke4(
               renderManager3,
               metrics5,
               FontRegistry.fontObject,
               rect10.x() + (rect10.w() - floatValue9) * 0.5F,
               rect10.y(),
               rect10.h(),
               10.0F,
               text2,
               colorScheme3.getIntValue12()
            );
         }

         for (int intValue3 = 0; intValue3 < items3.size(); intValue3++) {
            float floatValue10 = rect10.y() + intValue3 * floatValue7 - floatValue8;
            if (!(floatValue10 > rect10.y() + rect10.h()) && !(floatValue10 + floatValue7 < rect10.y())) {
               boolean flag2 = f >= rect10.x() && f < rect10.x() + rect10.w() && g >= floatValue10 && g < floatValue10 + floatValue7;
               if (flag2) {
                  this.intValue = intValue3;
               }

               boolean flag3 = intValue3 == this.intValue2;
               float floatValue11 = flag3 ? 1.0F : (flag2 ? 0.62F : 0.0F);
               renderManager3.invoke5(
                  rect10.x() + metrics5.measure(6.0F),
                  floatValue10 + metrics5.measure(4.0F),
                  rect10.w() - metrics5.measure(12.0F),
                  floatValue7 - metrics5.measure(8.0F),
                  metrics5.measure(7.0F),
                  ColorScheme.compute7(
                     ColorScheme.compute5(255, 255, 255, colorScheme3.isFlag() ? 86 : 10),
                     ColorScheme.compute6(colorScheme3.getIntValue14(), 76),
                     floatValue11
                  )
               );
               File file2 = (File)items3.get(intValue3);
               String text3 = this.resolve8(metrics5, file2.getName(), rect10.w() - metrics5.measure(132.0F), 10.0F);
               String text4 = SIMPLE_DATE_FORMAT.format(new Date(file2.lastModified()));
               ClickGuiRenderUtils.invoke3(
                  renderManager3,
                  metrics5,
                  FontRegistry.fontObject4,
                  rect10.x() + metrics5.measure(18.0F),
                  floatValue10 + metrics5.measure(10.0F),
                  10.0F,
                  text3,
                  colorScheme3.getIntValue13()
               );
               ClickGuiRenderUtils.invoke3(
                  renderManager3,
                  metrics5,
                  FontRegistry.fontObject,
                  rect10.x() + metrics5.measure(18.0F),
                  floatValue10 + metrics5.measure(24.0F),
                  8.0F,
                  text4,
                  colorScheme3.getIntValue12()
               );
            }
         }

         flag = false;
      } finally {
         if (flag) {
            renderManager3.invoke20();
            renderManager3.invoke25();
         }
      }

      renderManager3.invoke20();
      renderManager3.invoke25();
   }

   private void invoke6(RenderManager renderManager4, Metrics metrics6, ColorScheme colorScheme4, Rect rect11, float f, float g) {
      this.invoke7(renderManager4, metrics6, colorScheme4, this.resolve7(rect11, metrics6), "Cancel", f, g, false);
      this.invoke7(renderManager4, metrics6, colorScheme4, this.resolve6(rect11, metrics6), "Open", f, g, true);
   }

   private void invoke7(
      RenderManager renderManager5,
      Metrics metrics7,
      ColorScheme colorScheme5,
      Rect rect12,
      String string,
      float f,
      float g,
      boolean bl
   ) {
      boolean flag4 = rect12.contains(f, g);
      int intValue4 = ColorScheme.compute7(
         ColorScheme.compute5(255, 255, 255, colorScheme5.isFlag() ? 92 : 18),
         ColorScheme.compute6(bl ? colorScheme5.getIntValue14() : colorScheme5.getIntValue15(), 94),
         flag4 ? 1.0F : 0.0F
      );
      renderManager5.invoke5(rect12.x(), rect12.y(), rect12.w(), rect12.h(), metrics7.measure(8.0F), intValue4);
      renderManager5.invoke28(
         rect12.x(),
         rect12.y(),
         rect12.w(),
         rect12.h(),
         metrics7.measure(8.0F),
         ColorScheme.compute6(bl ? colorScheme5.getIntValue14() : colorScheme5.getIntValue15(), flag4 ? 148 : 78),
         0.7F
      );
      float floatValue12 = ClickGuiRenderUtils.measure2(metrics7, FontRegistry.fontObject4, string, 10.0F);
      ClickGuiRenderUtils.invoke4(
         renderManager5,
         metrics7,
         FontRegistry.fontObject4,
         rect12.x() + (rect12.w() - floatValue12) * 0.5F,
         rect12.y(),
         rect12.h(),
         10.0F,
         string,
         colorScheme5.getIntValue13()
      );
   }

   private List<File> resolve2() {
      if (this.text != null && !this.text.isBlank()) {
         String text5 = this.text.toLowerCase(Locale.ROOT);
         ArrayList arrayList = new ArrayList();

         for (File file3 : this.items) {
            if (file3.getName().toLowerCase(Locale.ROOT).contains(text5)) {
               arrayList.add(file3);
            }
         }

         if (this.intValue2 >= arrayList.size()) {
            this.intValue2 = arrayList.isEmpty() ? -1 : arrayList.size() - 1;
         }

         return arrayList;
      } else {
         return new ArrayList<>(this.items);
      }
   }

   private Rect resolve3(Metrics metrics8, int i, int j) {
      float floatValue13 = Math.min(metrics8.measure(480.0F), i - metrics8.measure(48.0F));
      float floatValue14 = Math.min(metrics8.measure(360.0F), j - metrics8.measure(64.0F));
      return new Rect((i - floatValue13) * 0.5F, (j - floatValue14) * 0.5F, floatValue13, floatValue14);
   }

   private Rect resolve4(Rect rect13, Metrics metrics9) {
      return new Rect(
         rect13.x() + metrics9.measure(20.0F),
         rect13.y() + metrics9.measure(52.0F),
         rect13.w() - metrics9.measure(40.0F),
         metrics9.measure(34.0F)
      );
   }

   private Rect resolve5(Rect rect14, Metrics metrics10) {
      return new Rect(
         rect14.x() + metrics10.measure(20.0F),
         rect14.y() + metrics10.measure(98.0F),
         rect14.w() - metrics10.measure(40.0F),
         rect14.h() - metrics10.measure(158.0F)
      );
   }

   private Rect resolve6(Rect rect15, Metrics metrics11) {
      return new Rect(
         rect15.x() + rect15.w() - metrics11.measure(112.0F),
         rect15.y() + rect15.h() - metrics11.measure(48.0F),
         metrics11.measure(92.0F),
         metrics11.measure(30.0F)
      );
   }

   private Rect resolve7(Rect rect16, Metrics metrics12) {
      return new Rect(
         rect16.x() + rect16.w() - metrics12.measure(214.0F),
         rect16.y() + rect16.h() - metrics12.measure(48.0F),
         metrics12.measure(92.0F),
         metrics12.measure(30.0F)
      );
   }

   private String resolve8(Metrics metrics13, String string, float f, float g) {
      if (string == null) {
         return "";
      } else if (ClickGuiRenderUtils.measure2(metrics13, FontRegistry.fontObject4, string, g) <= f) {
         return string;
      } else {
         String text6 = "...";
         String text7 = string;

         while (!text7.isEmpty() && ClickGuiRenderUtils.measure2(metrics13, FontRegistry.fontObject4, text7 + text6, g) > f) {
            text7 = text7.substring(0, text7.length() - 1);
         }

         return text7.isEmpty() ? text6 : text7 + text6;
      }
   }

   private int compute(ColorScheme colorScheme6, int i) {
      return colorScheme6.isFlag() ? ColorScheme.compute5(255, 255, 255, Math.min(255, i + 8)) : ColorScheme.compute5(10, 12, 18, i);
   }
}
