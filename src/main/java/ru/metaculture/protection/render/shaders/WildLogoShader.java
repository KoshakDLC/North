package ru.metaculture.protection;

import java.nio.FloatBuffer;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

final class WildLogoShader {
   private static final FloatBuffer FLOAT_BUFFER = BufferUtils.createFloatBuffer(12);
   private static GlShaderProgram glShaderProgram;
   private static int intValue;
   private static int intValue2;
   private static int intValue3;
   private static int intValue4;
   private static int intValue5;
   private static int intValue6;
   private static int intValue7;
   private static int intValue8;
   private static int intValue9;
   private static int intValue10;

   private WildLogoShader() {
   }

   static void invoke(float f, float g, float h, int i, int j, float k, boolean bl) {
      MinecraftClient client = MinecraftClient.getInstance();
      if (client != null && client.getWindow() != null && !client.getWindow().hasZeroWidthOrHeight()) {
         float floatValue = Math.max(10.0F, h * 0.34F);
         float floatValue2 = f - floatValue;
         float floatValue3 = g - floatValue;
         float floatValue4 = h + floatValue * 2.0F;
         float floatValue5 = h + floatValue * 2.0F;
         FramebufferUtils.GlStateSnapshot glStateSnapshot = FramebufferUtils.captureGlState();

         try {
            invoke2();
            GL11.glDisable(2929);
            GL11.glDisable(2884);
            GL11.glEnable(3042);
            GL14.glBlendFuncSeparate(770, 771, 1, 771);
            GL30.glBindVertexArray(intValue);
            glShaderProgram.invoke();
            GL20.glUniform2f(intValue3, client.getWindow().getFramebufferWidth(), client.getWindow().getFramebufferHeight());
            GL20.glUniform4f(intValue4, floatValue2, floatValue3, floatValue4, floatValue5);
            GL20.glUniform4f(intValue5, f, g, h, h);
            int intValue = bl ? -15066598 : i;
            int intValue2 = bl ? i : j;
            GL20.glUniform3f(intValue6, measure(intValue), measure2(intValue), measure3(intValue));
            GL20.glUniform3f(intValue7, measure(intValue2), measure2(intValue2), measure3(intValue2));
            GL20.glUniform1f(intValue8, (float)(System.currentTimeMillis() % 1000000L) * 0.001F);
            GL20.glUniform1f(intValue9, k);
            GL20.glUniform1f(intValue10, bl ? 1.0F : 0.0F);
            GL11.glDrawArrays(4, 0, 6);
         } finally {
            GL20.glUseProgram(0);
            GL30.glBindVertexArray(0);
            FramebufferUtils.restoreGlState(glStateSnapshot);
         }
      }
   }

   private static void invoke2() {
      if (glShaderProgram == null) {
         glShaderProgram = GlShaderProgram.resolve("assets/wild/shaders/hud/wild_logo.vert", "assets/wild/shaders/hud/wild_logo.frag");
         intValue3 = glShaderProgram.compute2("uViewport");
         intValue4 = glShaderProgram.compute2("uDrawRect");
         intValue5 = glShaderProgram.compute2("uBoxRect");
         intValue6 = glShaderProgram.compute2("uAccentTop");
         intValue7 = glShaderProgram.compute2("uAccentBottom");
         intValue8 = glShaderProgram.compute2("uTime");
         intValue9 = glShaderProgram.compute2("uAlpha");
         intValue10 = glShaderProgram.compute2("uLightMode");
      }

      if (intValue == 0) {
         intValue = GL30.glGenVertexArrays();
         intValue2 = GL15.glGenBuffers();
         GL30.glBindVertexArray(intValue);
         GL15.glBindBuffer(34962, intValue2);
         GL15.glBufferData(34962, FLOAT_BUFFER, 35044);
         GL20.glEnableVertexAttribArray(0);
         GL20.glVertexAttribPointer(0, 2, 5126, false, 8, 0L);
         GL30.glBindVertexArray(0);
         GL15.glBindBuffer(34962, 0);
      }
   }

   private static float measure(int i) {
      return (i >> 16 & 0xFF) / 255.0F;
   }

   private static float measure2(int i) {
      return (i >> 8 & 0xFF) / 255.0F;
   }

   private static float measure3(int i) {
      return (i & 0xFF) / 255.0F;
   }

   static {
      FLOAT_BUFFER.put(0.0F).put(0.0F);
      FLOAT_BUFFER.put(1.0F).put(0.0F);
      FLOAT_BUFFER.put(1.0F).put(1.0F);
      FLOAT_BUFFER.put(0.0F).put(0.0F);
      FLOAT_BUFFER.put(1.0F).put(1.0F);
      FLOAT_BUFFER.put(0.0F).put(1.0F);
      FLOAT_BUFFER.flip();
   }
}
