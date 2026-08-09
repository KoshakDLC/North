package ru.metaculture.protection;

import net.minecraft.client.MinecraftClient;

public class RenderScaleUtils {
   public static float floatValue = 2.0F;

   public static void invoke(RenderManager renderManager) {
      ProjectionUtils projectionUtils = new ProjectionUtils(MinecraftClient.getInstance());
      float floatValue = (float)(ProjectionUtils.getIntValue3() / Math.pow(ProjectionUtils.getIntValue3(), 2.0));
      renderManager.invoke62(floatValue * floatValue, floatValue * floatValue, floatValue * floatValue);
   }

   public static void invoke2(RenderManager renderManager2) {
      renderManager2.invoke62(floatValue, floatValue, floatValue);
   }

   public static int compute(int i) {
      ProjectionUtils projectionUtils2 = new ProjectionUtils(MinecraftClient.getInstance());
      return (int)(i * ProjectionUtils.getIntValue3() / floatValue);
   }

   public static int compute2(float f) {
      ProjectionUtils projectionUtils3 = new ProjectionUtils(MinecraftClient.getInstance());
      return (int)(f * ProjectionUtils.getIntValue3() / floatValue);
   }

   public static float[] resolve(float f, float g) {
      ProjectionUtils projectionUtils4 = new ProjectionUtils(MinecraftClient.getInstance());
      f = f * ProjectionUtils.getIntValue3() / floatValue;
      g = g * ProjectionUtils.getIntValue3() / floatValue;
      return new float[]{f, g};
   }
}
