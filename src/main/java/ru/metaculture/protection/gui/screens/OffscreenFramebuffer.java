package ru.metaculture.protection;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public final class OffscreenFramebuffer implements AutoCloseable {
   private int intValue;
   private int intValue2;
   private int intValue3;
   private int intValue4;

   public void invoke(int i, int j) {
      if (GLFW.glfwGetCurrentContext() == 0L) {
         this.invoke4();
      } else if (i > 0 && j > 0) {
         int intValue = Math.max(1, GL11.glGetInteger(3379));
         int intValue2 = Math.max(1, Math.min(i, intValue));
         int intValue3 = Math.max(1, Math.min(j, intValue));
         if (this.intValue == 0 || this.intValue2 == 0 || this.intValue3 != intValue2 || this.intValue4 != intValue3) {
            int intValue4 = GL11.glGetInteger(36006);
            int intValue5 = GL11.glGetInteger(36010);
            int intValue6 = GL11.glGetInteger(32873);
            int intValue7 = this.intValue2;
            this.invoke3();
            if (intValue6 == intValue7) {
               intValue6 = 0;
            }

            this.intValue3 = intValue2;
            this.intValue4 = intValue3;

            try {
               this.intValue2 = GL11.glGenTextures();
               GL11.glBindTexture(3553, this.intValue2);
               GL11.glTexParameteri(3553, 10241, 9729);
               GL11.glTexParameteri(3553, 10240, 9729);
               GL11.glTexParameteri(3553, 10242, 33071);
               GL11.glTexParameteri(3553, 10243, 33071);
               RenderCapabilities.invoke(32856, this.intValue3, this.intValue4, 6408, 5121);
               this.intValue = GL30.glGenFramebuffers();
               GL30.glBindFramebuffer(36160, this.intValue);
               GL30.glFramebufferTexture2D(36160, 36064, 3553, this.intValue2, 0);
               GL11.glDrawBuffer(36064);
               if (GL30.glCheckFramebufferStatus(36160) != 36053) {
                  this.invoke3();
               } else {
                  float[] floatValues = new float[4];
                  GL11.glGetFloatv(3106, floatValues);
                  boolean flag = GL11.glIsEnabled(3089);
                  GL11.glDisable(3089);
                  GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
                  GL11.glClear(16384);
                  if (flag) {
                     GL11.glEnable(3089);
                  }

                  GL11.glClearColor(floatValues[0], floatValues[1], floatValues[2], floatValues[3]);
               }
            } finally {
               FramebufferUtils.check(36008, intValue5);
               FramebufferUtils.check(36009, intValue4);
               GL11.glBindTexture(3553, intValue6);
            }
         }
      } else {
         this.invoke3();
      }
   }

   public void invoke2() {
      if (this.check()) {
         GL30.glBindFramebuffer(36160, this.intValue);
         GL11.glViewport(0, 0, this.intValue3, this.intValue4);
      }
   }

   public int getIntValue2() {
      return this.intValue2;
   }

   public int getIntValue3() {
      return this.intValue3;
   }

   public int getIntValue4() {
      return this.intValue4;
   }

   public boolean check() {
      return this.intValue != 0 && this.intValue2 != 0 && this.intValue3 > 0 && this.intValue4 > 0;
   }

   private void invoke3() {
      if (GLFW.glfwGetCurrentContext() == 0L) {
         this.invoke4();
      } else {
         if (this.intValue != 0) {
            GL30.glDeleteFramebuffers(this.intValue);
            this.intValue = 0;
         }

         if (this.intValue2 != 0) {
            GL11.glDeleteTextures(this.intValue2);
            this.intValue2 = 0;
         }

         this.intValue3 = 0;
         this.intValue4 = 0;
      }
   }

   private void invoke4() {
      this.intValue = 0;
      this.intValue2 = 0;
      this.intValue3 = 0;
      this.intValue4 = 0;
   }

   @Override
   public void close() {
      this.invoke3();
   }
}
