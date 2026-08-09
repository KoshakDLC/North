package ru.metaculture.protection;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.opengl.GL11;

public class LegacyMatrixScaleUtils {
   private MinecraftClient client = MinecraftClient.getInstance();
   public static float floatValue = 2.0F;

   public static void invoke() {
      ProjectionUtils projectionUtils = new ProjectionUtils(MinecraftClient.getInstance());
      double doubleValue = ProjectionUtils.getIntValue3() / Math.pow(ProjectionUtils.getIntValue3(), 2.0);
      GL11.glPushMatrix();
      GL11.glScaled(doubleValue * floatValue, doubleValue * floatValue, doubleValue * floatValue);
   }

   public static void invoke2() {
      GL11.glScaled(floatValue, floatValue, floatValue);
      GL11.glPopMatrix();
   }

   public static void invoke3(float f, float g, float h) {
      MatrixStack matrices = new MatrixStack();
      matrices.push();
      matrices.translate(f, g, 0.0F);
      matrices.scale(h, h, 1.0F);
      matrices.translate(-f, -g, 0.0F);
   }

   public static void invoke4() {
      MatrixStack matrices2 = new MatrixStack();
      matrices2.pop();
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

   public static void invoke5(float f, float g, float h) {
      MatrixStack matrices3 = new MatrixStack();
      matrices3.translate(f, g, 0.0F);
      matrices3.scale(h, h, 1.0F);
      matrices3.translate(-f, -g, 0.0F);
   }
}
