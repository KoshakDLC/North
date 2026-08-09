package ru.metaculture.protection;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import org.joml.Matrix4f;

public final class VertexShapeRenderer {
   private VertexShapeRenderer() {
   }

   public static void invoke(VertexConsumer vertexConsumer, Matrix4f matrix4f, int i, int j, float f) {
      float floatValue = f / 2.0F;
      int intValue = i >> 16 & 0xFF;
      int intValue2 = i >> 8 & 0xFF;
      int intValue3 = i & 0xFF;
      vertexConsumer.vertex(matrix4f, -floatValue, -floatValue, 0.0F)
         .color(intValue, intValue2, intValue3, j)
         .texture(0.0F, 1.0F)
         .overlay(OverlayTexture.DEFAULT_UV)
         .light(15728880)
         .normal(0.0F, 0.0F, 1.0F);
      vertexConsumer.vertex(matrix4f, floatValue, -floatValue, 0.0F)
         .color(intValue, intValue2, intValue3, j)
         .texture(1.0F, 1.0F)
         .overlay(OverlayTexture.DEFAULT_UV)
         .light(15728880)
         .normal(0.0F, 0.0F, 1.0F);
      vertexConsumer.vertex(matrix4f, floatValue, floatValue, 0.0F)
         .color(intValue, intValue2, intValue3, j)
         .texture(1.0F, 0.0F)
         .overlay(OverlayTexture.DEFAULT_UV)
         .light(15728880)
         .normal(0.0F, 0.0F, 1.0F);
      vertexConsumer.vertex(matrix4f, -floatValue, floatValue, 0.0F)
         .color(intValue, intValue2, intValue3, j)
         .texture(0.0F, 0.0F)
         .overlay(OverlayTexture.DEFAULT_UV)
         .light(15728880)
         .normal(0.0F, 0.0F, 1.0F);
   }

   public static void invoke2(VertexConsumer vertexConsumer, Matrix4f matrix4f, double d, double e, double f, double g, double h, double i, int[] is, int j) {
      int[] intValues = new int[4];
      int[][] intValuesValues = new int[4][4];

      for (int intValue4 = 0; intValue4 < 4; intValue4++) {
         intValues[intValue4] = ColorUtils.compute29(is[intValue4], j);
         intValuesValues[intValue4][0] = intValues[intValue4] >> 16 & 0xFF;
         intValuesValues[intValue4][1] = intValues[intValue4] >> 8 & 0xFF;
         intValuesValues[intValue4][2] = intValues[intValue4] & 0xFF;
         intValuesValues[intValue4][3] = intValues[intValue4] >> 24 & 0xFF;
      }

      vertexConsumer.vertex(matrix4f, (float)d, (float)e, (float)f).color(intValuesValues[0][0], intValuesValues[0][1], intValuesValues[0][2], intValuesValues[0][3]);
      vertexConsumer.vertex(matrix4f, (float)g, (float)e, (float)f).color(intValuesValues[1][0], intValuesValues[1][1], intValuesValues[1][2], intValuesValues[1][3]);
      vertexConsumer.vertex(matrix4f, (float)g, (float)e, (float)i).color(intValuesValues[2][0], intValuesValues[2][1], intValuesValues[2][2], intValuesValues[2][3]);
      vertexConsumer.vertex(matrix4f, (float)d, (float)e, (float)i).color(intValuesValues[3][0], intValuesValues[3][1], intValuesValues[3][2], intValuesValues[3][3]);
      vertexConsumer.vertex(matrix4f, (float)d, (float)h, (float)f).color(intValuesValues[0][0], intValuesValues[0][1], intValuesValues[0][2], intValuesValues[0][3]);
      vertexConsumer.vertex(matrix4f, (float)d, (float)h, (float)i).color(intValuesValues[3][0], intValuesValues[3][1], intValuesValues[3][2], intValuesValues[3][3]);
      vertexConsumer.vertex(matrix4f, (float)g, (float)h, (float)i).color(intValuesValues[2][0], intValuesValues[2][1], intValuesValues[2][2], intValuesValues[2][3]);
      vertexConsumer.vertex(matrix4f, (float)g, (float)h, (float)f).color(intValuesValues[1][0], intValuesValues[1][1], intValuesValues[1][2], intValuesValues[1][3]);
      vertexConsumer.vertex(matrix4f, (float)d, (float)e, (float)i).color(intValuesValues[3][0], intValuesValues[3][1], intValuesValues[3][2], intValuesValues[3][3]);
      vertexConsumer.vertex(matrix4f, (float)g, (float)e, (float)i).color(intValuesValues[2][0], intValuesValues[2][1], intValuesValues[2][2], intValuesValues[2][3]);
      vertexConsumer.vertex(matrix4f, (float)g, (float)h, (float)i).color(intValuesValues[2][0], intValuesValues[2][1], intValuesValues[2][2], intValuesValues[2][3]);
      vertexConsumer.vertex(matrix4f, (float)d, (float)h, (float)i).color(intValuesValues[3][0], intValuesValues[3][1], intValuesValues[3][2], intValuesValues[3][3]);
      vertexConsumer.vertex(matrix4f, (float)g, (float)e, (float)f).color(intValuesValues[1][0], intValuesValues[1][1], intValuesValues[1][2], intValuesValues[1][3]);
      vertexConsumer.vertex(matrix4f, (float)d, (float)e, (float)f).color(intValuesValues[0][0], intValuesValues[0][1], intValuesValues[0][2], intValuesValues[0][3]);
      vertexConsumer.vertex(matrix4f, (float)d, (float)h, (float)f).color(intValuesValues[0][0], intValuesValues[0][1], intValuesValues[0][2], intValuesValues[0][3]);
      vertexConsumer.vertex(matrix4f, (float)g, (float)h, (float)f).color(intValuesValues[1][0], intValuesValues[1][1], intValuesValues[1][2], intValuesValues[1][3]);
      vertexConsumer.vertex(matrix4f, (float)d, (float)e, (float)f).color(intValuesValues[0][0], intValuesValues[0][1], intValuesValues[0][2], intValuesValues[0][3]);
      vertexConsumer.vertex(matrix4f, (float)d, (float)e, (float)i).color(intValuesValues[3][0], intValuesValues[3][1], intValuesValues[3][2], intValuesValues[3][3]);
      vertexConsumer.vertex(matrix4f, (float)d, (float)h, (float)i).color(intValuesValues[3][0], intValuesValues[3][1], intValuesValues[3][2], intValuesValues[3][3]);
      vertexConsumer.vertex(matrix4f, (float)d, (float)h, (float)f).color(intValuesValues[0][0], intValuesValues[0][1], intValuesValues[0][2], intValuesValues[0][3]);
      vertexConsumer.vertex(matrix4f, (float)g, (float)e, (float)i).color(intValuesValues[2][0], intValuesValues[2][1], intValuesValues[2][2], intValuesValues[2][3]);
      vertexConsumer.vertex(matrix4f, (float)g, (float)e, (float)f).color(intValuesValues[1][0], intValuesValues[1][1], intValuesValues[1][2], intValuesValues[1][3]);
      vertexConsumer.vertex(matrix4f, (float)g, (float)h, (float)f).color(intValuesValues[1][0], intValuesValues[1][1], intValuesValues[1][2], intValuesValues[1][3]);
      vertexConsumer.vertex(matrix4f, (float)g, (float)h, (float)i).color(intValuesValues[2][0], intValuesValues[2][1], intValuesValues[2][2], intValuesValues[2][3]);
   }

   public static void invoke3(
      VertexConsumer vertexConsumer, Matrix4f matrix4f, double d, double e, double f, double g, double h, double i, int[] is, int j, double k, double l
   ) {
      int[] intValues2 = new int[4];

      for (int intValue5 = 0; intValue5 < 4; intValue5++) {
         intValues2[intValue5] = ColorUtils.compute29(is[intValue5], j);
      }

      invoke4(vertexConsumer, matrix4f, d, e, f, g, e, f, intValues2[0], intValues2[1], k, l);
      invoke4(vertexConsumer, matrix4f, g, e, f, g, e, i, intValues2[1], intValues2[2], k, l);
      invoke4(vertexConsumer, matrix4f, g, e, i, d, e, i, intValues2[2], intValues2[3], k, l);
      invoke4(vertexConsumer, matrix4f, d, e, i, d, e, f, intValues2[3], intValues2[0], k, l);
      invoke4(vertexConsumer, matrix4f, d, h, f, g, h, f, intValues2[0], intValues2[1], k, l);
      invoke4(vertexConsumer, matrix4f, g, h, f, g, h, i, intValues2[1], intValues2[2], k, l);
      invoke4(vertexConsumer, matrix4f, g, h, i, d, h, i, intValues2[2], intValues2[3], k, l);
      invoke4(vertexConsumer, matrix4f, d, h, i, d, h, f, intValues2[3], intValues2[0], k, l);
      invoke4(vertexConsumer, matrix4f, d, e, f, d, h, f, intValues2[0], intValues2[0], k, l);
      invoke4(vertexConsumer, matrix4f, g, e, f, g, h, f, intValues2[1], intValues2[1], k, l);
      invoke4(vertexConsumer, matrix4f, g, e, i, g, h, i, intValues2[2], intValues2[2], k, l);
      invoke4(vertexConsumer, matrix4f, d, e, i, d, h, i, intValues2[3], intValues2[3], k, l);
   }

   public static void invoke4(
      VertexConsumer vertexConsumer, Matrix4f matrix4f, double d, double e, double f, double g, double h, double i, int j, int k, double l, double m
   ) {
      double doubleValue = g - d;
      double doubleValue2 = h - e;
      double doubleValue3 = i - f;
      double doubleValue4 = Math.sqrt(doubleValue * doubleValue + doubleValue2 * doubleValue2 + doubleValue3 * doubleValue3);
      if (!(doubleValue4 < 0.001)) {
         double doubleValue5 = doubleValue / doubleValue4;
         double doubleValue6 = doubleValue2 / doubleValue4;
         double doubleValue7 = doubleValue3 / doubleValue4;
         double doubleValue8 = l + m;

         for (double doubleValue9 = 0.0; doubleValue9 < doubleValue4; doubleValue9 += doubleValue8) {
            double doubleValue10 = Math.min(doubleValue9 + l, doubleValue4);
            if (doubleValue10 > doubleValue9) {
               double doubleValue11 = d + doubleValue5 * doubleValue9;
               double doubleValue12 = e + doubleValue6 * doubleValue9;
               double doubleValue13 = f + doubleValue7 * doubleValue9;
               double doubleValue14 = d + doubleValue5 * doubleValue10;
               double doubleValue15 = e + doubleValue6 * doubleValue10;
               double doubleValue16 = f + doubleValue7 * doubleValue10;
               double doubleValue17 = doubleValue9 / doubleValue4;
               int intValue6 = ColorUtils.compute14(j, k, (float)doubleValue17);
               vertexConsumer.vertex(matrix4f, (float)doubleValue11, (float)doubleValue12, (float)doubleValue13)
                  .color(intValue6 >> 16 & 0xFF, intValue6 >> 8 & 0xFF, intValue6 & 0xFF, intValue6 >>> 24 & 0xFF);
               doubleValue17 = doubleValue10 / doubleValue4;
               intValue6 = ColorUtils.compute14(j, k, (float)doubleValue17);
               vertexConsumer.vertex(matrix4f, (float)doubleValue14, (float)doubleValue15, (float)doubleValue16)
                  .color(intValue6 >> 16 & 0xFF, intValue6 >> 8 & 0xFF, intValue6 & 0xFF, intValue6 >>> 24 & 0xFF);
            }
         }
      }
   }
}
