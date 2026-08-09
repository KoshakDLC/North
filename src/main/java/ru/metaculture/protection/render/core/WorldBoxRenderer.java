package ru.metaculture.protection;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.util.math.MathHelper;
import org.joml.Matrix4f;

public final class WorldBoxRenderer {
   private WorldBoxRenderer() {
   }

   public static void invoke(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float g, float h, float i, float j, float k, int l) {
      float floatValue = (l >> 24 & 0xFF) / 255.0F;
      float floatValue2 = (l >> 16 & 0xFF) / 255.0F;
      float floatValue3 = (l >> 8 & 0xFF) / 255.0F;
      float floatValue4 = (l & 0xFF) / 255.0F;
      vertexConsumer.vertex(matrix4f, f, g, h).color(floatValue2, floatValue3, floatValue4, floatValue);
      vertexConsumer.vertex(matrix4f, i, j, k).color(floatValue2, floatValue3, floatValue4, floatValue);
   }

   public static void invoke2(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float g, float h, float i, float j, float k, int l) {
      float floatValue5 = (l >> 24 & 0xFF) / 255.0F;
      float floatValue6 = (l >> 16 & 0xFF) / 255.0F;
      float floatValue7 = (l >> 8 & 0xFF) / 255.0F;
      float floatValue8 = (l & 0xFF) / 255.0F;
      vertexConsumer.vertex(matrix4f, f, g, h).color(floatValue6, floatValue7, floatValue8, floatValue5);
      vertexConsumer.vertex(matrix4f, i, g, h).color(floatValue6, floatValue7, floatValue8, floatValue5);
      vertexConsumer.vertex(matrix4f, i, g, h).color(floatValue6, floatValue7, floatValue8, floatValue5);
      vertexConsumer.vertex(matrix4f, i, g, k).color(floatValue6, floatValue7, floatValue8, floatValue5);
      vertexConsumer.vertex(matrix4f, i, g, k).color(floatValue6, floatValue7, floatValue8, floatValue5);
      vertexConsumer.vertex(matrix4f, f, g, k).color(floatValue6, floatValue7, floatValue8, floatValue5);
      vertexConsumer.vertex(matrix4f, f, g, k).color(floatValue6, floatValue7, floatValue8, floatValue5);
      vertexConsumer.vertex(matrix4f, f, g, h).color(floatValue6, floatValue7, floatValue8, floatValue5);
      vertexConsumer.vertex(matrix4f, f, j, h).color(floatValue6, floatValue7, floatValue8, floatValue5);
      vertexConsumer.vertex(matrix4f, i, j, h).color(floatValue6, floatValue7, floatValue8, floatValue5);
      vertexConsumer.vertex(matrix4f, i, j, h).color(floatValue6, floatValue7, floatValue8, floatValue5);
      vertexConsumer.vertex(matrix4f, i, j, k).color(floatValue6, floatValue7, floatValue8, floatValue5);
      vertexConsumer.vertex(matrix4f, i, j, k).color(floatValue6, floatValue7, floatValue8, floatValue5);
      vertexConsumer.vertex(matrix4f, f, j, k).color(floatValue6, floatValue7, floatValue8, floatValue5);
      vertexConsumer.vertex(matrix4f, f, j, k).color(floatValue6, floatValue7, floatValue8, floatValue5);
      vertexConsumer.vertex(matrix4f, f, j, h).color(floatValue6, floatValue7, floatValue8, floatValue5);
      vertexConsumer.vertex(matrix4f, f, g, h).color(floatValue6, floatValue7, floatValue8, floatValue5);
      vertexConsumer.vertex(matrix4f, f, j, h).color(floatValue6, floatValue7, floatValue8, floatValue5);
      vertexConsumer.vertex(matrix4f, i, g, h).color(floatValue6, floatValue7, floatValue8, floatValue5);
      vertexConsumer.vertex(matrix4f, i, j, h).color(floatValue6, floatValue7, floatValue8, floatValue5);
      vertexConsumer.vertex(matrix4f, i, g, k).color(floatValue6, floatValue7, floatValue8, floatValue5);
      vertexConsumer.vertex(matrix4f, i, j, k).color(floatValue6, floatValue7, floatValue8, floatValue5);
      vertexConsumer.vertex(matrix4f, f, g, k).color(floatValue6, floatValue7, floatValue8, floatValue5);
      vertexConsumer.vertex(matrix4f, f, j, k).color(floatValue6, floatValue7, floatValue8, floatValue5);
   }

   public static void invoke3(
      VertexConsumer vertexConsumer, VertexConsumer vertexConsumer2, Matrix4f matrix4f, float f, float g, float h, float i, float j, float k, int l, int m
   ) {
      invoke5(vertexConsumer, matrix4f, f, g, h, i, j, k, l);
      invoke2(vertexConsumer2, matrix4f, f, g, h, i, j, k, m);
   }

   public static void invoke4(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float g, float h, float i, float j, int k, int l) {
      float floatValue9 = (k >> 24 & 0xFF) / 255.0F;
      float floatValue10 = (k >> 16 & 0xFF) / 255.0F;
      float floatValue11 = (k >> 8 & 0xFF) / 255.0F;
      float floatValue12 = (k & 0xFF) / 255.0F;
      float floatValue13 = (float)((Math.PI * 2) / l);

      for (int intValue = 0; intValue < l; intValue++) {
         float floatValue14 = intValue * floatValue13;
         float floatValue15 = (intValue + 1) * floatValue13;
         float floatValue16 = f + MathHelper.sin(floatValue14) * i;
         float floatValue17 = h + MathHelper.cos(floatValue14) * i;
         float floatValue18 = f + MathHelper.sin(floatValue15) * i;
         float floatValue19 = h + MathHelper.cos(floatValue15) * i;
         vertexConsumer.vertex(matrix4f, floatValue16, g, floatValue17).color(floatValue10, floatValue11, floatValue12, floatValue9);
         vertexConsumer.vertex(matrix4f, floatValue18, g, floatValue19).color(floatValue10, floatValue11, floatValue12, floatValue9);
         vertexConsumer.vertex(matrix4f, floatValue16, g + j, floatValue17).color(floatValue10, floatValue11, floatValue12, floatValue9);
         vertexConsumer.vertex(matrix4f, floatValue18, g + j, floatValue19).color(floatValue10, floatValue11, floatValue12, floatValue9);
         if (intValue % (l / 8) == 0) {
            vertexConsumer.vertex(matrix4f, floatValue16, g, floatValue17).color(floatValue10, floatValue11, floatValue12, floatValue9);
            vertexConsumer.vertex(matrix4f, floatValue16, g + j, floatValue17).color(floatValue10, floatValue11, floatValue12, floatValue9);
         }
      }
   }

   public static void invoke5(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float g, float h, float i, float j, float k, int l) {
      float floatValue20 = (l >> 24 & 0xFF) / 255.0F;
      float floatValue21 = (l >> 16 & 0xFF) / 255.0F;
      float floatValue22 = (l >> 8 & 0xFF) / 255.0F;
      float floatValue23 = (l & 0xFF) / 255.0F;
      vertexConsumer.vertex(matrix4f, f, g, h).color(floatValue21, floatValue22, floatValue23, floatValue20);
      vertexConsumer.vertex(matrix4f, i, g, h).color(floatValue21, floatValue22, floatValue23, floatValue20);
      vertexConsumer.vertex(matrix4f, i, g, k).color(floatValue21, floatValue22, floatValue23, floatValue20);
      vertexConsumer.vertex(matrix4f, f, g, k).color(floatValue21, floatValue22, floatValue23, floatValue20);
      vertexConsumer.vertex(matrix4f, f, j, h).color(floatValue21, floatValue22, floatValue23, floatValue20);
      vertexConsumer.vertex(matrix4f, f, j, k).color(floatValue21, floatValue22, floatValue23, floatValue20);
      vertexConsumer.vertex(matrix4f, i, j, k).color(floatValue21, floatValue22, floatValue23, floatValue20);
      vertexConsumer.vertex(matrix4f, i, j, h).color(floatValue21, floatValue22, floatValue23, floatValue20);
      vertexConsumer.vertex(matrix4f, f, g, h).color(floatValue21, floatValue22, floatValue23, floatValue20);
      vertexConsumer.vertex(matrix4f, f, j, h).color(floatValue21, floatValue22, floatValue23, floatValue20);
      vertexConsumer.vertex(matrix4f, i, j, h).color(floatValue21, floatValue22, floatValue23, floatValue20);
      vertexConsumer.vertex(matrix4f, i, g, h).color(floatValue21, floatValue22, floatValue23, floatValue20);
      vertexConsumer.vertex(matrix4f, f, g, k).color(floatValue21, floatValue22, floatValue23, floatValue20);
      vertexConsumer.vertex(matrix4f, i, g, k).color(floatValue21, floatValue22, floatValue23, floatValue20);
      vertexConsumer.vertex(matrix4f, i, j, k).color(floatValue21, floatValue22, floatValue23, floatValue20);
      vertexConsumer.vertex(matrix4f, f, j, k).color(floatValue21, floatValue22, floatValue23, floatValue20);
      vertexConsumer.vertex(matrix4f, f, g, h).color(floatValue21, floatValue22, floatValue23, floatValue20);
      vertexConsumer.vertex(matrix4f, f, g, k).color(floatValue21, floatValue22, floatValue23, floatValue20);
      vertexConsumer.vertex(matrix4f, f, j, k).color(floatValue21, floatValue22, floatValue23, floatValue20);
      vertexConsumer.vertex(matrix4f, f, j, h).color(floatValue21, floatValue22, floatValue23, floatValue20);
      vertexConsumer.vertex(matrix4f, i, g, h).color(floatValue21, floatValue22, floatValue23, floatValue20);
      vertexConsumer.vertex(matrix4f, i, j, h).color(floatValue21, floatValue22, floatValue23, floatValue20);
      vertexConsumer.vertex(matrix4f, i, j, k).color(floatValue21, floatValue22, floatValue23, floatValue20);
      vertexConsumer.vertex(matrix4f, i, g, k).color(floatValue21, floatValue22, floatValue23, floatValue20);
   }

   public static void invoke6(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float g, float h, float i, float j, int k, int l) {
      float floatValue24 = (k >> 24 & 0xFF) / 255.0F;
      float floatValue25 = (k >> 16 & 0xFF) / 255.0F;
      float floatValue26 = (k >> 8 & 0xFF) / 255.0F;
      float floatValue27 = (k & 0xFF) / 255.0F;
      float floatValue28 = (float)((Math.PI * 2) / l);

      for (int intValue2 = 0; intValue2 < l; intValue2++) {
         float floatValue29 = intValue2 * floatValue28;
         float floatValue30 = (intValue2 + 1) * floatValue28;
         float floatValue31 = f + MathHelper.sin(floatValue29) * i;
         float floatValue32 = h + MathHelper.cos(floatValue29) * i;
         float floatValue33 = f + MathHelper.sin(floatValue30) * i;
         float floatValue34 = h + MathHelper.cos(floatValue30) * i;
         vertexConsumer.vertex(matrix4f, floatValue31, g, floatValue32).color(floatValue25, floatValue26, floatValue27, floatValue24);
         vertexConsumer.vertex(matrix4f, floatValue31, g + j, floatValue32).color(floatValue25, floatValue26, floatValue27, floatValue24);
         vertexConsumer.vertex(matrix4f, floatValue33, g + j, floatValue34).color(floatValue25, floatValue26, floatValue27, floatValue24);
         vertexConsumer.vertex(matrix4f, floatValue33, g, floatValue34).color(floatValue25, floatValue26, floatValue27, floatValue24);
         vertexConsumer.vertex(matrix4f, f, g, h).color(floatValue25, floatValue26, floatValue27, floatValue24);
         vertexConsumer.vertex(matrix4f, f, g, h).color(floatValue25, floatValue26, floatValue27, floatValue24);
         vertexConsumer.vertex(matrix4f, floatValue31, g, floatValue32).color(floatValue25, floatValue26, floatValue27, floatValue24);
         vertexConsumer.vertex(matrix4f, floatValue33, g, floatValue34).color(floatValue25, floatValue26, floatValue27, floatValue24);
         vertexConsumer.vertex(matrix4f, f, g + j, h).color(floatValue25, floatValue26, floatValue27, floatValue24);
         vertexConsumer.vertex(matrix4f, f, g + j, h).color(floatValue25, floatValue26, floatValue27, floatValue24);
         vertexConsumer.vertex(matrix4f, floatValue33, g + j, floatValue34).color(floatValue25, floatValue26, floatValue27, floatValue24);
         vertexConsumer.vertex(matrix4f, floatValue31, g + j, floatValue32).color(floatValue25, floatValue26, floatValue27, floatValue24);
      }
   }

   public static void invoke7(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float g, float h, float i, float j, float k, int l, int m) {
      float floatValue35 = (l >> 24 & 0xFF) / 255.0F;
      float floatValue36 = (l >> 16 & 0xFF) / 255.0F;
      float floatValue37 = (l >> 8 & 0xFF) / 255.0F;
      float floatValue38 = (l & 0xFF) / 255.0F;
      float floatValue39 = (m >> 24 & 0xFF) / 255.0F;
      float floatValue40 = (m >> 16 & 0xFF) / 255.0F;
      float floatValue41 = (m >> 8 & 0xFF) / 255.0F;
      float floatValue42 = (m & 0xFF) / 255.0F;
      vertexConsumer.vertex(matrix4f, f, g, h).color(floatValue36, floatValue37, floatValue38, floatValue35);
      vertexConsumer.vertex(matrix4f, i, g, h).color(floatValue36, floatValue37, floatValue38, floatValue35);
      vertexConsumer.vertex(matrix4f, i, g, k).color(floatValue36, floatValue37, floatValue38, floatValue35);
      vertexConsumer.vertex(matrix4f, f, g, k).color(floatValue36, floatValue37, floatValue38, floatValue35);
      vertexConsumer.vertex(matrix4f, f, j, h).color(floatValue40, floatValue41, floatValue42, floatValue39);
      vertexConsumer.vertex(matrix4f, f, j, k).color(floatValue40, floatValue41, floatValue42, floatValue39);
      vertexConsumer.vertex(matrix4f, i, j, k).color(floatValue40, floatValue41, floatValue42, floatValue39);
      vertexConsumer.vertex(matrix4f, i, j, h).color(floatValue40, floatValue41, floatValue42, floatValue39);
      vertexConsumer.vertex(matrix4f, f, g, h).color(floatValue36, floatValue37, floatValue38, floatValue35);
      vertexConsumer.vertex(matrix4f, f, j, h).color(floatValue40, floatValue41, floatValue42, floatValue39);
      vertexConsumer.vertex(matrix4f, i, j, h).color(floatValue40, floatValue41, floatValue42, floatValue39);
      vertexConsumer.vertex(matrix4f, i, g, h).color(floatValue36, floatValue37, floatValue38, floatValue35);
      vertexConsumer.vertex(matrix4f, f, g, k).color(floatValue36, floatValue37, floatValue38, floatValue35);
      vertexConsumer.vertex(matrix4f, i, g, k).color(floatValue36, floatValue37, floatValue38, floatValue35);
      vertexConsumer.vertex(matrix4f, i, j, k).color(floatValue40, floatValue41, floatValue42, floatValue39);
      vertexConsumer.vertex(matrix4f, f, j, k).color(floatValue40, floatValue41, floatValue42, floatValue39);
      vertexConsumer.vertex(matrix4f, f, g, h).color(floatValue36, floatValue37, floatValue38, floatValue35);
      vertexConsumer.vertex(matrix4f, f, g, k).color(floatValue36, floatValue37, floatValue38, floatValue35);
      vertexConsumer.vertex(matrix4f, f, j, k).color(floatValue40, floatValue41, floatValue42, floatValue39);
      vertexConsumer.vertex(matrix4f, f, j, h).color(floatValue40, floatValue41, floatValue42, floatValue39);
      vertexConsumer.vertex(matrix4f, i, g, h).color(floatValue36, floatValue37, floatValue38, floatValue35);
      vertexConsumer.vertex(matrix4f, i, j, h).color(floatValue40, floatValue41, floatValue42, floatValue39);
      vertexConsumer.vertex(matrix4f, i, j, k).color(floatValue40, floatValue41, floatValue42, floatValue39);
      vertexConsumer.vertex(matrix4f, i, g, k).color(floatValue36, floatValue37, floatValue38, floatValue35);
   }

   public static void invoke8(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float g, float h, float i, float j, int k, int l, int m) {
      float floatValue43 = (k >> 24 & 0xFF) / 255.0F;
      float floatValue44 = (k >> 16 & 0xFF) / 255.0F;
      float floatValue45 = (k >> 8 & 0xFF) / 255.0F;
      float floatValue46 = (k & 0xFF) / 255.0F;
      float floatValue47 = (l >> 24 & 0xFF) / 255.0F;
      float floatValue48 = (l >> 16 & 0xFF) / 255.0F;
      float floatValue49 = (l >> 8 & 0xFF) / 255.0F;
      float floatValue50 = (l & 0xFF) / 255.0F;
      float floatValue51 = (float)((Math.PI * 2) / m);

      for (int intValue3 = 0; intValue3 < m; intValue3++) {
         float floatValue52 = intValue3 * floatValue51;
         float floatValue53 = (intValue3 + 1) * floatValue51;
         float floatValue54 = f + MathHelper.sin(floatValue52) * i;
         float floatValue55 = h + MathHelper.cos(floatValue52) * i;
         float floatValue56 = f + MathHelper.sin(floatValue53) * i;
         float floatValue57 = h + MathHelper.cos(floatValue53) * i;
         vertexConsumer.vertex(matrix4f, floatValue54, g, floatValue55).color(floatValue44, floatValue45, floatValue46, floatValue43);
         vertexConsumer.vertex(matrix4f, floatValue54, g + j, floatValue55).color(floatValue48, floatValue49, floatValue50, floatValue47);
         vertexConsumer.vertex(matrix4f, floatValue56, g + j, floatValue57).color(floatValue48, floatValue49, floatValue50, floatValue47);
         vertexConsumer.vertex(matrix4f, floatValue56, g, floatValue57).color(floatValue44, floatValue45, floatValue46, floatValue43);
         vertexConsumer.vertex(matrix4f, f, g, h).color(floatValue44, floatValue45, floatValue46, floatValue43);
         vertexConsumer.vertex(matrix4f, f, g, h).color(floatValue44, floatValue45, floatValue46, floatValue43);
         vertexConsumer.vertex(matrix4f, floatValue54, g, floatValue55).color(floatValue44, floatValue45, floatValue46, floatValue43);
         vertexConsumer.vertex(matrix4f, floatValue56, g, floatValue57).color(floatValue44, floatValue45, floatValue46, floatValue43);
         vertexConsumer.vertex(matrix4f, f, g + j, h).color(floatValue48, floatValue49, floatValue50, floatValue47);
         vertexConsumer.vertex(matrix4f, f, g + j, h).color(floatValue48, floatValue49, floatValue50, floatValue47);
         vertexConsumer.vertex(matrix4f, floatValue56, g + j, floatValue57).color(floatValue48, floatValue49, floatValue50, floatValue47);
         vertexConsumer.vertex(matrix4f, floatValue54, g + j, floatValue55).color(floatValue48, floatValue49, floatValue50, floatValue47);
      }
   }

   private static void invoke9(
      VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float g, float h, float i, float j, float k, float l, float m, float n, float o
   ) {
      vertexConsumer.vertex(matrix4f, f, g, h).color(l, m, n, o);
      vertexConsumer.vertex(matrix4f, f, j, k).color(l, m, n, o);
      vertexConsumer.vertex(matrix4f, i, j, k).color(l, m, n, o);
      vertexConsumer.vertex(matrix4f, i, g, h).color(l, m, n, o);
   }
}
