package ru.metaculture.protection;

import net.minecraft.client.render.VertexConsumer;
import org.joml.Matrix4f;

public class EspBoxVertexWriter {
   private EspBoxVertexWriter() {
   }

   public static void invoke(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float g, float h, float i, float j, float k, int l) {
      int intValue = l >> 16 & 0xFF;
      int intValue2 = l >> 8 & 0xFF;
      int intValue3 = l & 0xFF;
      int intValue4 = l >> 24 & 0xFF;
      vertexConsumer.vertex(matrix4f, f, j, h).color(intValue, intValue2, intValue3, intValue4);
      vertexConsumer.vertex(matrix4f, f, j, k).color(intValue, intValue2, intValue3, intValue4);
      vertexConsumer.vertex(matrix4f, i, j, k).color(intValue, intValue2, intValue3, intValue4);
      vertexConsumer.vertex(matrix4f, i, j, h).color(intValue, intValue2, intValue3, intValue4);
      vertexConsumer.vertex(matrix4f, f, g, h).color(intValue, intValue2, intValue3, intValue4);
      vertexConsumer.vertex(matrix4f, i, g, h).color(intValue, intValue2, intValue3, intValue4);
      vertexConsumer.vertex(matrix4f, i, g, k).color(intValue, intValue2, intValue3, intValue4);
      vertexConsumer.vertex(matrix4f, f, g, k).color(intValue, intValue2, intValue3, intValue4);
      vertexConsumer.vertex(matrix4f, f, g, k).color(intValue, intValue2, intValue3, intValue4);
      vertexConsumer.vertex(matrix4f, f, j, k).color(intValue, intValue2, intValue3, intValue4);
      vertexConsumer.vertex(matrix4f, i, j, k).color(intValue, intValue2, intValue3, intValue4);
      vertexConsumer.vertex(matrix4f, i, g, k).color(intValue, intValue2, intValue3, intValue4);
      vertexConsumer.vertex(matrix4f, f, g, h).color(intValue, intValue2, intValue3, intValue4);
      vertexConsumer.vertex(matrix4f, f, j, h).color(intValue, intValue2, intValue3, intValue4);
      vertexConsumer.vertex(matrix4f, i, j, h).color(intValue, intValue2, intValue3, intValue4);
      vertexConsumer.vertex(matrix4f, i, g, h).color(intValue, intValue2, intValue3, intValue4);
      vertexConsumer.vertex(matrix4f, f, g, h).color(intValue, intValue2, intValue3, intValue4);
      vertexConsumer.vertex(matrix4f, f, g, k).color(intValue, intValue2, intValue3, intValue4);
      vertexConsumer.vertex(matrix4f, f, j, k).color(intValue, intValue2, intValue3, intValue4);
      vertexConsumer.vertex(matrix4f, f, j, h).color(intValue, intValue2, intValue3, intValue4);
      vertexConsumer.vertex(matrix4f, i, g, h).color(intValue, intValue2, intValue3, intValue4);
      vertexConsumer.vertex(matrix4f, i, j, h).color(intValue, intValue2, intValue3, intValue4);
      vertexConsumer.vertex(matrix4f, i, j, k).color(intValue, intValue2, intValue3, intValue4);
      vertexConsumer.vertex(matrix4f, i, g, k).color(intValue, intValue2, intValue3, intValue4);
   }

   public static void invoke2(
      VertexConsumer vertexConsumer,
      VertexConsumer vertexConsumer2,
      Matrix4f matrix4f,
      double d,
      double e,
      double f,
      double g,
      double h,
      double i,
      int[] is,
      int j,
      int k,
      double l,
      double m
   ) {
      VertexShapeRenderer.invoke2(vertexConsumer, matrix4f, d, e, f, g, h, i, is, j);
      VertexShapeRenderer.invoke3(vertexConsumer2, matrix4f, d, e, f, g, h, i, is, k, l, m);
   }

   public static void invoke3(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float g, float h, float i, float j, float k, int l, int m) {
      int intValue5 = l >> 16 & 0xFF;
      int intValue6 = l >> 8 & 0xFF;
      int intValue7 = l & 0xFF;
      int intValue8 = l >> 24 & 0xFF;
      int intValue9 = m >> 16 & 0xFF;
      int intValue10 = m >> 8 & 0xFF;
      int intValue11 = m & 0xFF;
      int intValue12 = m >> 24 & 0xFF;
      vertexConsumer.vertex(matrix4f, f, j, h).color(intValue5, intValue6, intValue7, intValue8);
      vertexConsumer.vertex(matrix4f, f, j, k).color(intValue5, intValue6, intValue7, intValue8);
      vertexConsumer.vertex(matrix4f, i, j, k).color(intValue5, intValue6, intValue7, intValue8);
      vertexConsumer.vertex(matrix4f, i, j, h).color(intValue5, intValue6, intValue7, intValue8);
      vertexConsumer.vertex(matrix4f, f, g, h).color(intValue5, intValue6, intValue7, intValue8);
      vertexConsumer.vertex(matrix4f, f, g, k).color(intValue5, intValue6, intValue7, intValue8);
      vertexConsumer.vertex(matrix4f, i, g, k).color(intValue5, intValue6, intValue7, intValue8);
      vertexConsumer.vertex(matrix4f, i, g, h).color(intValue5, intValue6, intValue7, intValue8);
      vertexConsumer.vertex(matrix4f, f, g, k).color(intValue5, intValue6, intValue7, intValue8);
      vertexConsumer.vertex(matrix4f, f, j, k).color(intValue5, intValue6, intValue7, intValue8);
      vertexConsumer.vertex(matrix4f, i, j, k).color(intValue5, intValue6, intValue7, intValue8);
      vertexConsumer.vertex(matrix4f, i, g, k).color(intValue5, intValue6, intValue7, intValue8);
      vertexConsumer.vertex(matrix4f, f, g, h).color(intValue5, intValue6, intValue7, intValue8);
      vertexConsumer.vertex(matrix4f, i, g, h).color(intValue5, intValue6, intValue7, intValue8);
      vertexConsumer.vertex(matrix4f, i, j, h).color(intValue5, intValue6, intValue7, intValue8);
      vertexConsumer.vertex(matrix4f, f, j, h).color(intValue5, intValue6, intValue7, intValue8);
      vertexConsumer.vertex(matrix4f, f, g, h).color(intValue5, intValue6, intValue7, intValue8);
      vertexConsumer.vertex(matrix4f, f, j, h).color(intValue5, intValue6, intValue7, intValue8);
      vertexConsumer.vertex(matrix4f, f, j, k).color(intValue5, intValue6, intValue7, intValue8);
      vertexConsumer.vertex(matrix4f, f, g, k).color(intValue5, intValue6, intValue7, intValue8);
      vertexConsumer.vertex(matrix4f, i, g, h).color(intValue5, intValue6, intValue7, intValue8);
      vertexConsumer.vertex(matrix4f, i, g, k).color(intValue5, intValue6, intValue7, intValue8);
      vertexConsumer.vertex(matrix4f, i, j, k).color(intValue5, intValue6, intValue7, intValue8);
      vertexConsumer.vertex(matrix4f, i, j, h).color(intValue5, intValue6, intValue7, intValue8);
      vertexConsumer.vertex(matrix4f, f, j, h).color(intValue9, intValue10, intValue11, intValue12);
      vertexConsumer.vertex(matrix4f, f, j, k).color(intValue9, intValue10, intValue11, intValue12);
      vertexConsumer.vertex(matrix4f, i, j, k).color(intValue9, intValue10, intValue11, intValue12);
      vertexConsumer.vertex(matrix4f, i, j, h).color(intValue9, intValue10, intValue11, intValue12);
      vertexConsumer.vertex(matrix4f, f, g, h).color(intValue9, intValue10, intValue11, intValue12);
      vertexConsumer.vertex(matrix4f, i, g, h).color(intValue9, intValue10, intValue11, intValue12);
      vertexConsumer.vertex(matrix4f, i, g, k).color(intValue9, intValue10, intValue11, intValue12);
      vertexConsumer.vertex(matrix4f, f, g, k).color(intValue9, intValue10, intValue11, intValue12);
      vertexConsumer.vertex(matrix4f, f, g, k).color(intValue9, intValue10, intValue11, intValue12);
      vertexConsumer.vertex(matrix4f, f, j, k).color(intValue9, intValue10, intValue11, intValue12);
      vertexConsumer.vertex(matrix4f, i, j, k).color(intValue9, intValue10, intValue11, intValue12);
      vertexConsumer.vertex(matrix4f, i, g, k).color(intValue9, intValue10, intValue11, intValue12);
      vertexConsumer.vertex(matrix4f, f, g, h).color(intValue9, intValue10, intValue11, intValue12);
      vertexConsumer.vertex(matrix4f, f, j, h).color(intValue9, intValue10, intValue11, intValue12);
      vertexConsumer.vertex(matrix4f, i, j, h).color(intValue9, intValue10, intValue11, intValue12);
      vertexConsumer.vertex(matrix4f, i, g, h).color(intValue9, intValue10, intValue11, intValue12);
      vertexConsumer.vertex(matrix4f, f, g, h).color(intValue9, intValue10, intValue11, intValue12);
      vertexConsumer.vertex(matrix4f, f, g, k).color(intValue9, intValue10, intValue11, intValue12);
      vertexConsumer.vertex(matrix4f, f, j, k).color(intValue9, intValue10, intValue11, intValue12);
      vertexConsumer.vertex(matrix4f, f, j, h).color(intValue9, intValue10, intValue11, intValue12);
      vertexConsumer.vertex(matrix4f, i, g, h).color(intValue9, intValue10, intValue11, intValue12);
      vertexConsumer.vertex(matrix4f, i, j, h).color(intValue9, intValue10, intValue11, intValue12);
      vertexConsumer.vertex(matrix4f, i, j, k).color(intValue9, intValue10, intValue11, intValue12);
      vertexConsumer.vertex(matrix4f, i, g, k).color(intValue9, intValue10, intValue11, intValue12);
   }

   public static void invoke4(VertexConsumer vertexConsumer, Matrix4f matrix4f, int i, float f) {
      float floatValue = f / 2.0F;
      int intValue13 = i >> 16 & 0xFF;
      int intValue14 = i >> 8 & 0xFF;
      int intValue15 = i & 0xFF;
      int intValue16 = i >> 24 & 0xFF;
      vertexConsumer.vertex(matrix4f, -floatValue, floatValue, -floatValue).color(intValue13, intValue14, intValue15, intValue16);
      vertexConsumer.vertex(matrix4f, -floatValue, floatValue, floatValue).color(intValue13, intValue14, intValue15, intValue16);
      vertexConsumer.vertex(matrix4f, floatValue, floatValue, floatValue).color(intValue13, intValue14, intValue15, intValue16);
      vertexConsumer.vertex(matrix4f, floatValue, floatValue, -floatValue).color(intValue13, intValue14, intValue15, intValue16);
      vertexConsumer.vertex(matrix4f, -floatValue, -floatValue, -floatValue).color(intValue13, intValue14, intValue15, intValue16);
      vertexConsumer.vertex(matrix4f, floatValue, -floatValue, -floatValue).color(intValue13, intValue14, intValue15, intValue16);
      vertexConsumer.vertex(matrix4f, floatValue, -floatValue, floatValue).color(intValue13, intValue14, intValue15, intValue16);
      vertexConsumer.vertex(matrix4f, -floatValue, -floatValue, floatValue).color(intValue13, intValue14, intValue15, intValue16);
      vertexConsumer.vertex(matrix4f, -floatValue, floatValue, floatValue).color(intValue13, intValue14, intValue15, intValue16);
      vertexConsumer.vertex(matrix4f, -floatValue, -floatValue, floatValue).color(intValue13, intValue14, intValue15, intValue16);
      vertexConsumer.vertex(matrix4f, floatValue, -floatValue, floatValue).color(intValue13, intValue14, intValue15, intValue16);
      vertexConsumer.vertex(matrix4f, floatValue, floatValue, floatValue).color(intValue13, intValue14, intValue15, intValue16);
      vertexConsumer.vertex(matrix4f, -floatValue, floatValue, -floatValue).color(intValue13, intValue14, intValue15, intValue16);
      vertexConsumer.vertex(matrix4f, floatValue, floatValue, -floatValue).color(intValue13, intValue14, intValue15, intValue16);
      vertexConsumer.vertex(matrix4f, floatValue, -floatValue, -floatValue).color(intValue13, intValue14, intValue15, intValue16);
      vertexConsumer.vertex(matrix4f, -floatValue, -floatValue, -floatValue).color(intValue13, intValue14, intValue15, intValue16);
      vertexConsumer.vertex(matrix4f, -floatValue, floatValue, -floatValue).color(intValue13, intValue14, intValue15, intValue16);
      vertexConsumer.vertex(matrix4f, -floatValue, -floatValue, -floatValue).color(intValue13, intValue14, intValue15, intValue16);
      vertexConsumer.vertex(matrix4f, -floatValue, -floatValue, floatValue).color(intValue13, intValue14, intValue15, intValue16);
      vertexConsumer.vertex(matrix4f, -floatValue, floatValue, floatValue).color(intValue13, intValue14, intValue15, intValue16);
      vertexConsumer.vertex(matrix4f, floatValue, floatValue, -floatValue).color(intValue13, intValue14, intValue15, intValue16);
      vertexConsumer.vertex(matrix4f, floatValue, floatValue, floatValue).color(intValue13, intValue14, intValue15, intValue16);
      vertexConsumer.vertex(matrix4f, floatValue, -floatValue, floatValue).color(intValue13, intValue14, intValue15, intValue16);
      vertexConsumer.vertex(matrix4f, floatValue, -floatValue, -floatValue).color(intValue13, intValue14, intValue15, intValue16);
   }

   public static void invoke5(VertexConsumer vertexConsumer, Matrix4f matrix4f, int i, float f) {
      float floatValue2 = f / 2.0F;
      int intValue17 = i >> 16 & 0xFF;
      int intValue18 = i >> 8 & 0xFF;
      int intValue19 = i & 0xFF;
      int intValue20 = i >> 24 & 0xFF;
      invoke6(vertexConsumer, matrix4f, -floatValue2, -floatValue2, -floatValue2, floatValue2, -floatValue2, -floatValue2, intValue17, intValue18, intValue19, intValue20);
      invoke6(vertexConsumer, matrix4f, floatValue2, -floatValue2, -floatValue2, floatValue2, -floatValue2, floatValue2, intValue17, intValue18, intValue19, intValue20);
      invoke6(vertexConsumer, matrix4f, floatValue2, -floatValue2, floatValue2, -floatValue2, -floatValue2, floatValue2, intValue17, intValue18, intValue19, intValue20);
      invoke6(vertexConsumer, matrix4f, -floatValue2, -floatValue2, floatValue2, -floatValue2, -floatValue2, -floatValue2, intValue17, intValue18, intValue19, intValue20);
      invoke6(vertexConsumer, matrix4f, -floatValue2, floatValue2, -floatValue2, floatValue2, floatValue2, -floatValue2, intValue17, intValue18, intValue19, intValue20);
      invoke6(vertexConsumer, matrix4f, floatValue2, floatValue2, -floatValue2, floatValue2, floatValue2, floatValue2, intValue17, intValue18, intValue19, intValue20);
      invoke6(vertexConsumer, matrix4f, floatValue2, floatValue2, floatValue2, -floatValue2, floatValue2, floatValue2, intValue17, intValue18, intValue19, intValue20);
      invoke6(vertexConsumer, matrix4f, -floatValue2, floatValue2, floatValue2, -floatValue2, floatValue2, -floatValue2, intValue17, intValue18, intValue19, intValue20);
      invoke6(vertexConsumer, matrix4f, -floatValue2, -floatValue2, -floatValue2, -floatValue2, floatValue2, -floatValue2, intValue17, intValue18, intValue19, intValue20);
      invoke6(vertexConsumer, matrix4f, floatValue2, -floatValue2, -floatValue2, floatValue2, floatValue2, -floatValue2, intValue17, intValue18, intValue19, intValue20);
      invoke6(vertexConsumer, matrix4f, floatValue2, -floatValue2, floatValue2, floatValue2, floatValue2, floatValue2, intValue17, intValue18, intValue19, intValue20);
      invoke6(vertexConsumer, matrix4f, -floatValue2, -floatValue2, floatValue2, -floatValue2, floatValue2, floatValue2, intValue17, intValue18, intValue19, intValue20);
   }

   private static void invoke6(
      VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float g, float h, float i, float j, float k, int l, int m, int n, int o
   ) {
      vertexConsumer.vertex(matrix4f, f, g, h).color(l, m, n, o);
      vertexConsumer.vertex(matrix4f, i, j, k).color(l, m, n, o);
   }
}
