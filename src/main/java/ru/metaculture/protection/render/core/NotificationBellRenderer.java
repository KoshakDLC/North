package ru.metaculture.protection;

final class NotificationBellRenderer {
   private NotificationBellRenderer() {
   }

   static void invoke(RenderManager renderManager, Metrics metrics, float f, float g, float h, int i, int j) {
      float floatValue = Math.max(metrics.measure(0.35F), h);
      renderManager.invoke39(f, g - 5.4F * floatValue, 4.3F * floatValue, 0.0F, 1.0F, j);
      renderManager.invoke5(f - 6.8F * floatValue, g + 0.9F * floatValue, 13.6F * floatValue, 9.0F * floatValue, 4.5F * floatValue, j);
      renderManager.invoke39(f, g - 5.4F * floatValue, 3.2F * floatValue, 0.0F, 1.0F, i);
      renderManager.invoke5(f - 5.2F * floatValue, g + 1.7F * floatValue, 10.4F * floatValue, 7.1F * floatValue, 3.4F * floatValue, i);
   }
}
