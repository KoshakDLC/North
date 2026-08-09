package ru.metaculture.protection;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class TextMeasureCache {
   private static final int INT_VALUE = 4096;
   private static final Map<TextMeasureCache.TextMeasureCacheData, TextMeasureCache.TextMeasureCacheState> VALUES_BY_KEY = new LinkedHashMap<TextMeasureCache.TextMeasureCacheData, TextMeasureCache.TextMeasureCacheState>(
      1024, 0.75F, true
   ) {
      @Override
      protected boolean removeEldestEntry(Entry<TextMeasureCache.TextMeasureCacheData, TextMeasureCache.TextMeasureCacheState> entry) {
         return this.size() > 4096;
      }
   };

   private TextMeasureCache() {
   }

   public static TextMeasureCache.TextMeasureCacheState resolve(FontObject fontObject, String string, float f) {
      if (string == null) {
         string = "";
      }

      TextMeasureCache.TextMeasureCacheData textMeasureCacheData = new TextMeasureCache.TextMeasureCacheData(fontObject, string, Float.floatToIntBits(f));
      TextMeasureCache.TextMeasureCacheState textMeasureCacheState = VALUES_BY_KEY.get(textMeasureCacheData);
      if (textMeasureCacheState != null) {
         return textMeasureCacheState;
      } else {
         FontRenderer.FontRendererState fontRendererState = RenderManager.resolve7(fontObject, string, f);
         textMeasureCacheState = new TextMeasureCache.TextMeasureCacheState(fontRendererState.floatValue, fontRendererState.floatValue2);
         VALUES_BY_KEY.put(textMeasureCacheData, textMeasureCacheState);
         return textMeasureCacheState;
      }
   }

   public static float measure(FontObject fontObject2, String string, float f) {
      return resolve(fontObject2, string, f).floatValue;
   }

   public static float measure2(FontObject fontObject3, String string, float f) {
      return resolve(fontObject3, string, f).floatValue2;
   }

   public static void invoke() {
      VALUES_BY_KEY.clear();
   }

   record TextMeasureCacheData(FontObject font, String text, int sizeBits) {
   }

   public static final class TextMeasureCacheState {
      public final float floatValue;
      public final float floatValue2;

      TextMeasureCacheState(float f, float g) {
         this.floatValue = f;
         this.floatValue2 = g;
      }
   }
}
