package ru.metaculture.protection;

import net.minecraft.client.util.math.MatrixStack;

public class MatrixScaleUtils implements MinecraftAccessor {
   public static float floatValue = 2.0F;

   public static void invoke(MatrixStack matrixStack) {
      matrixStack.push();
      double doubleValue = a_.getWindow().getScaleFactor();
      double doubleValue2 = doubleValue / (doubleValue * doubleValue);
      matrixStack.scale((float)(doubleValue2 * floatValue), (float)(doubleValue2 * floatValue), 1.0F);
   }

   public static void invoke2(MatrixStack matrixStack) {
      matrixStack.pop();
   }

   public static void invoke3(MatrixStack matrixStack, float f, float g, float h) {
      matrixStack.push();
      matrixStack.translate(f, g, 0.0F);
      matrixStack.scale(h, h, 1.0F);
      matrixStack.translate(-f, -g, 0.0F);
   }

   public static void invoke4(MatrixStack matrixStack) {
      matrixStack.pop();
   }

   public static int compute(int i) {
      return (int)(i * a_.getWindow().getScaleFactor() / floatValue);
   }

   public static int compute2(float f) {
      return (int)(f * a_.getWindow().getScaleFactor() / floatValue);
   }

   public static float measure(float f) {
      return f * a_.getWindow().getScaleFactor() / floatValue;
   }

   public static float[] resolve(float f, float g) {
      double doubleValue3 = a_.getWindow().getScaleFactor();
      f = (float)(f * doubleValue3 / floatValue);
      g = (float)(g * doubleValue3 / floatValue);
      return new float[]{f, g};
   }

   public static void invoke5(MatrixStack matrixStack, float f, float g, float h) {
      matrixStack.translate(f, g, 0.0F);
      matrixStack.scale(h, h, 1.0F);
      matrixStack.translate(-f, -g, 0.0F);
   }
}
