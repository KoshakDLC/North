package ru.metaculture.protection;

public final class ClickGuiFooterLayout {
   private ClickGuiFooterLayout() {
   }

   public static Rect resolve(ClickGuiGeometry clickGuiGeometry, Metrics metrics) {
      if (clickGuiGeometry != null && metrics != null) {
         String text = resolve2();
         float floatValue = ClickGuiRenderUtils.measure(FontRegistry.fontObject4, text, 12.0F);
         float floatValue2 = ClickGuiRenderUtils.measure(FontRegistry.fontObject5, "g", 12.0F);
         float floatValue3 = clickGuiGeometry.getFloatValue7() + clickGuiGeometry.getFloatValue9() - metrics.measure(16.0F) - floatValue2;
         float floatValue4 = floatValue3 - metrics.measure(8.0F) - floatValue;
         float floatValue5 = metrics.measure(86.0F);
         float floatValue6 = metrics.measure(24.0F);
         float floatValue7 = floatValue4 - metrics.measure(12.0F) - floatValue5;
         float floatValue8 = clickGuiGeometry.getFloatValue8() + (metrics.getFloatValue10() - floatValue6) * 0.5F;
         return new Rect(floatValue7, floatValue8, floatValue5, floatValue6);
      } else {
         return new Rect(0.0F, 0.0F, 0.0F, 0.0F);
      }
   }

   private static String resolve2() {
      MenuModule menuModule = MenuModule.getInstance();
      int intValue = menuModule != null && menuModule.bindKey != -1 ? menuModule.bindKey : 344;
      return KeyboardKey.resolve(intValue);
   }
}
