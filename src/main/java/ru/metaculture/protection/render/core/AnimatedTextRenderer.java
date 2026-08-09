package ru.metaculture.protection;

import java.util.ArrayList;

public final class AnimatedTextRenderer {
   private static final float FLOAT_VALUE = 170.0F;
   private String text = "";
   private final ArrayList<Long> arrayList = new ArrayList<>();
   private final ArrayList<AnimatedTextRenderer.AnimatedTextRendererTimedEntry> arrayList2 = new ArrayList<>();

   public boolean check() {
      return !this.arrayList2.isEmpty();
   }

   public void invoke(
      RenderManager renderManager,
      Metrics metrics,
      FontObject fontObject,
      String string,
      float f,
      float g,
      float h,
      float i,
      int j,
      boolean bl,
      int k,
      long l
   ) {
      this.invoke2(string, fontObject, f, i, l);
      float floatValue = f;

      for (int intValue = 0; intValue < string.length(); intValue++) {
         String text = String.valueOf(string.charAt(intValue));
         float floatValue2 = ClickGuiRenderUtils.measure(fontObject, text, i);
         long longValue = intValue < this.arrayList.size() ? this.arrayList.get(intValue) : 0L;
         float floatValue3 = (float)(l - longValue) / 170.0F;
         float floatValue4 = 0.0F;
         int intValue2 = j;
         if (floatValue3 < 1.0F) {
            float floatValue5 = 1.0F - (1.0F - floatValue3) * (1.0F - floatValue3);
            floatValue4 = (1.0F - floatValue5) * metrics.measure(7.0F);
            intValue2 = ColorScheme.compute6(j, Math.round(255.0F * floatValue5));
         }

         ClickGuiRenderUtils.invoke4(renderManager, metrics, fontObject, floatValue, g + floatValue4, h, i, text, intValue2);
         floatValue += floatValue2;
      }

      if (bl) {
         ClickGuiRenderUtils.invoke4(renderManager, metrics, fontObject, floatValue, g, h, i, "|", k);
      }

      for (int intValue3 = this.arrayList2.size() - 1; intValue3 >= 0; intValue3--) {
         AnimatedTextRenderer.AnimatedTextRendererTimedEntry animatedTextRendererTimedEntry = this.arrayList2.get(intValue3);
         float floatValue6 = (float)(l - animatedTextRendererTimedEntry.born()) / 170.0F;
         if (floatValue6 >= 1.0F) {
            this.arrayList2.remove(intValue3);
         } else {
            float floatValue7 = 1.0F - (1.0F - floatValue6) * (1.0F - floatValue6);
            ClickGuiRenderUtils.invoke4(
               renderManager,
               metrics,
               fontObject,
               animatedTextRendererTimedEntry.x(),
               g + floatValue7 * metrics.measure(8.0F),
               h,
               i,
               animatedTextRendererTimedEntry.ch(),
               ColorScheme.compute6(j, Math.round(255.0F * (1.0F - floatValue7)))
            );
         }
      }
   }

   private void invoke2(String string, FontObject fontObject2, float f, float g, long l) {
      if (!string.equals(this.text)) {
         int intValue4 = 0;
         int intValue5 = Math.min(this.text.length(), string.length());

         while (intValue4 < intValue5 && this.text.charAt(intValue4) == string.charAt(intValue4)) {
            intValue4++;
         }

         float floatValue8 = f + ClickGuiRenderUtils.measure(fontObject2, this.text.substring(0, intValue4), g);

         for (int intValue6 = intValue4; intValue6 < this.text.length(); intValue6++) {
            String text2 = String.valueOf(this.text.charAt(intValue6));
            this.arrayList2.add(new AnimatedTextRenderer.AnimatedTextRendererTimedEntry(text2, floatValue8, l));
            floatValue8 += ClickGuiRenderUtils.measure(fontObject2, text2, g);
         }

         while (this.arrayList.size() > intValue4) {
            this.arrayList.remove(this.arrayList.size() - 1);
         }

         while (this.arrayList.size() < string.length()) {
            this.arrayList.add(l);
         }

         this.text = string;
      }
   }

   record AnimatedTextRendererTimedEntry(String ch, float x, long born) {
   }
}
