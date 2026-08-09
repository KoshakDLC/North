package ru.metaculture.protection;

import java.nio.FloatBuffer;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

final class SidebarNavigationShader {
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
   private static int intValue11;
   private static int intValue12;
   private static int intValue13;
   private static int intValue14;
   private static int intValue15;
   private static int intValue16;
   private static int intValue17;

   private SidebarNavigationShader() {
   }

   static void invoke(float f, float g, float h, int i, float j, float k, float l, int m, int n, int o, int p, int q, float r, boolean bl) {
      MinecraftClient client = MinecraftClient.getInstance();
      if (client != null && client.getWindow() != null && !client.getWindow().hasZeroWidthOrHeight()) {
         float floatValue = Math.max(10.0F, h * 0.35F);
         FramebufferUtils.GlStateSnapshot glStateSnapshot = FramebufferUtils.captureGlState();
         boolean flag = false ;

         try {
            flag = true;
            invoke2();
            GL11.glDisable(2929);
            GL11.glDisable(2884);
            GL11.glEnable(3042);
            GL14.glBlendFuncSeparate(1, 771, 1, 771);
            GL30.glBindVertexArray(intValue);
            glShaderProgram.invoke();
            GL20.glUniform2f(intValue3, client.getWindow().getFramebufferWidth(), client.getWindow().getFramebufferHeight());
            GL20.glUniform4f(intValue4, f - floatValue, g - floatValue, h + floatValue * 2.0F, h + floatValue * 2.0F);
            GL20.glUniform4f(intValue5, f, g, h, h);
            GL20.glUniform3f(intValue6, measure(m), measure2(m), measure3(m));
            GL20.glUniform3f(intValue7, measure(n), measure2(n), measure3(n));
            GL20.glUniform4f(intValue8, measure(o), measure2(o), measure3(o), measure4(o));
            GL20.glUniform4f(intValue9, measure(p), measure2(p), measure3(p), measure4(p));
            GL20.glUniform4f(intValue10, measure(q), measure2(q), measure3(q), measure4(q));
            GL20.glUniform1f(intValue11, (float)(System.currentTimeMillis() % 1000000L) * 0.001F);
            GL20.glUniform1f(intValue12, r);
            GL20.glUniform1f(intValue13, bl ? 1.0F : 0.0F);
            GL20.glUniform1f(intValue14, j);
            GL20.glUniform1f(intValue15, k);
            GL20.glUniform1f(intValue16, l);
            GL20.glUniform1i(intValue17, i);
            GL11.glDrawArrays(4, 0, 6);
            flag = false;
         } finally {
            if (flag) {
               GL20.glUseProgram(0);
               GL30.glBindVertexArray(0);
               FramebufferUtils.restoreGlState(glStateSnapshot);
            }
         }

         GL20.glUseProgram(0);
         GL30.glBindVertexArray(0);
         FramebufferUtils.restoreGlState(glStateSnapshot);
      }
   }

   private static void invoke2() {
      if (glShaderProgram == null) {
         glShaderProgram = GlShaderProgram.resolve("assets/wild/shaders/hud/wild_logo.vert", "assets/wild/shaders/clickgui/sidebar_nav.frag");
         intValue3 = glShaderProgram.compute2("uViewport");
         intValue4 = glShaderProgram.compute2("uDrawRect");
         intValue5 = glShaderProgram.compute2("uBoxRect");
         intValue6 = glShaderProgram.compute2("uAccentTop");
         intValue7 = glShaderProgram.compute2("uAccentBottom");
         intValue8 = glShaderProgram.compute2("uMuted");
         intValue9 = glShaderProgram.compute2("uFill");
         intValue10 = glShaderProgram.compute2("uOutline");
         intValue11 = glShaderProgram.compute2("uTime");
         intValue12 = glShaderProgram.compute2("uAlpha");
         intValue13 = glShaderProgram.compute2("uLightMode");
         intValue14 = glShaderProgram.compute2("uHover");
         intValue15 = glShaderProgram.compute2("uActive");
         intValue16 = glShaderProgram.compute2("uPop");
         intValue17 = glShaderProgram.compute2("uIcon");
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

   private static float measure4(int i) {
      return (i >>> 24 & 0xFF) / 255.0F;
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
