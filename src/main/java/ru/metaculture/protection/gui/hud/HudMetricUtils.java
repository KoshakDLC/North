package ru.metaculture.protection;

public final class HudMetricUtils {
   private final Animation animation = new Animation();
   private String text;
   private String text2;
   private int intValue = -1;
   private double doubleValue = Double.NaN;

   public void invoke(String string) {
      this.invoke2(string, Double.NaN);
   }

   public void invoke2(String string, double d) {
      if (string == null) {
         string = "";
      }

      if (this.text == null) {
         this.text = string;
         this.text2 = null;
         this.doubleValue = d;
         this.animation.invoke(1.0);
      } else if (!string.equals(this.text)) {
         if (this.animation.measure3() >= 0.999F) {
            this.text2 = this.text;
            if (!Double.isNaN(d) && !Double.isNaN(this.doubleValue)) {
               this.intValue = d >= this.doubleValue ? 1 : -1;
            }

            this.animation.invoke(0.0);
         }

         this.text = string;
         if (!Double.isNaN(d)) {
            this.doubleValue = d;
         }
      }

      this.animation.check();
      this.animation.resolve4(1.0, 0.22F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
   }

   public void invoke3(RenderManager renderManager, FontObject fontObject, float f, float g, float h, float i, float j, float k, float l, float m, int n) {
      String text = this.text == null ? "" : this.text;
      float floatValue = TextMeasureCache.measure(fontObject, text, m);
      this.invoke5(renderManager, fontObject, f, g, h, i, j, k - floatValue * 0.5F, l, m, n);
   }

   public void invoke4(RenderManager renderManager2, FontObject fontObject2, float f, float g, float h, float i, float j, float k, float l, float m, int n) {
      this.invoke5(renderManager2, fontObject2, f, g, h, i, j, k, l, m, n);
   }

   private void invoke5(
      RenderManager renderManager3, FontObject fontObject3, float f, float g, float h, float i, float j, float k, float l, float m, int n
   ) {
      float floatValue2 = this.animation.measure3();
      String text2 = this.text == null ? "" : this.text;
      if (!(floatValue2 >= 0.999F) && this.text2 != null) {
         String text3 = this.text2;
         int intValue = text2.length();
         int intValue2 = text3.length();
         int intValue3 = Math.min(intValue, intValue2);
         int intValue4 = 0;

         while (intValue4 < intValue3 && text2.charAt(intValue4) == text3.charAt(intValue4)) {
            intValue4++;
         }

         int intValue5 = 0;

         while (intValue5 < intValue3 - intValue4 && text2.charAt(intValue - 1 - intValue5) == text3.charAt(intValue2 - 1 - intValue5)) {
            intValue5++;
         }

         String text4 = text2.substring(0, intValue4);
         String text5 = text2.substring(intValue4, intValue - intValue5);
         String text6 = text3.substring(intValue4, intValue2 - intValue5);
         String text7 = text2.substring(intValue - intValue5);
         float floatValue3 = TextMeasureCache.measure(fontObject3, text4, m);
         float floatValue4 = TextMeasureCache.measure(fontObject3, text5, m);
         if (!text4.isEmpty()) {
            renderManager3.invoke69(fontObject3, k, l, m, text4, n);
         }

         float floatValue5 = k + floatValue3;
         int intValue6 = ColorUtils.compute4(n);
         int intValue7 = ColorUtils.compute2(n, (int)(intValue6 * floatValue2));
         int intValue8 = ColorUtils.compute2(n, (int)(intValue6 * (1.0F - floatValue2)));
         renderManager3.invoke24(f, g, h, i, j, j, j, j);
         if (!text6.isEmpty()) {
            renderManager3.invoke69(fontObject3, floatValue5, l - this.intValue * m * floatValue2, m, text6, intValue8);
         }

         if (!text5.isEmpty()) {
            renderManager3.invoke69(fontObject3, floatValue5, l + this.intValue * m * (1.0F - floatValue2), m, text5, intValue7);
         }

         renderManager3.invoke25();
         if (!text7.isEmpty()) {
            renderManager3.invoke69(fontObject3, floatValue5 + floatValue4, l, m, text7, n);
         }
      } else {
         renderManager3.invoke69(fontObject3, k, l, m, text2, n);
      }
   }
}
