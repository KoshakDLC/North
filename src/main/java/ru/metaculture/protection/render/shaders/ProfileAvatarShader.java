package ru.metaculture.protection;

import java.nio.FloatBuffer;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

final class ProfileAvatarShader {
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

   private ProfileAvatarShader() {
   }

   static void invoke(float f, float g, float h, int i, int j, int k, float l, boolean bl) {
      MinecraftClient client = MinecraftClient.getInstance();
      if (client != null && client.getWindow() != null && !client.getWindow().hasZeroWidthOrHeight() && i > 0) {
         float floatValue = h * 0.3F;
         FramebufferUtils.GlStateSnapshot glStateSnapshot = FramebufferUtils.captureGlState();

         try {
            invoke2();
            GL11.glDisable(2929);
            GL11.glDisable(2884);
            GL11.glEnable(3042);
            GL14.glBlendFuncSeparate(1, 771, 1, 771);
            GL30.glBindVertexArray(intValue);
            glShaderProgram.invoke();
            GL20.glUniform2f(intValue3, client.getWindow().getFramebufferWidth(), client.getWindow().getFramebufferHeight());
            GL20.glUniform4f(intValue4, f - floatValue, g - floatValue, h + floatValue * 2.0F, h + floatValue * 2.0F);
            GL13.glActiveTexture(33984);
            GL11.glBindTexture(3553, i);
            GL20.glUniform1i(intValue5, 0);
            GL20.glUniform3f(intValue6, measure(j), measure2(j), measure3(j));
            GL20.glUniform3f(intValue7, measure(k), measure2(k), measure3(k));
            GL20.glUniform1f(intValue8, (float)(System.currentTimeMillis() % 1000000L) * 0.001F);
            GL20.glUniform1f(intValue9, Math.max(0.0F, Math.min(1.0F, l)));
            GL20.glUniform1f(intValue10, bl ? 1.0F : 0.0F);
            GL11.glDrawArrays(4, 0, 6);
         } finally {
            GL20.glUseProgram(0);
            GL30.glBindVertexArray(0);
            GL11.glBindTexture(3553, 0);
            FramebufferUtils.restoreGlState(glStateSnapshot);
         }
      }
   }

   private static void invoke2() {
      if (glShaderProgram == null) {
         glShaderProgram = GlShaderProgram.resolve("assets/wild/shaders/hud/avatar.vert", "assets/wild/shaders/hud/avatar.frag");
         intValue3 = glShaderProgram.compute2("uViewport");
         intValue4 = glShaderProgram.compute2("uDrawRect");
         intValue5 = glShaderProgram.compute2("uTexture");
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
