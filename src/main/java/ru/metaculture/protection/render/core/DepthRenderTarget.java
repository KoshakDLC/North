package ru.metaculture.protection;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public final class DepthRenderTarget {
   public int intValue = 0;
   public int intValue2 = 0;
   public int intValue3 = 0;
   public int intValue4 = 0;
   public int intValue5 = 0;

   public void invoke(int i, int j) {
      if (i <= 0 || j <= 0) {
         this.invoke2();
      } else if (this.intValue == 0 || this.intValue2 == 0 || this.intValue3 == 0 || this.intValue4 != i || this.intValue5 != j) {
         this.invoke2();
         this.intValue4 = i;
         this.intValue5 = j;
         FramebufferUtils.GlStateSnapshot glStateSnapshot = FramebufferUtils.captureGlState();

         int intValue;
         try {
            this.intValue2 = GL11.glGenTextures();
            GL11.glBindTexture(3553, this.intValue2);
            GL11.glTexParameteri(3553, 10241, 9729);
            GL11.glTexParameteri(3553, 10240, 9729);
            GL11.glTexParameteri(3553, 10242, 33071);
            GL11.glTexParameteri(3553, 10243, 33071);
            RenderCapabilities.invoke(32856, this.intValue4, this.intValue5, 6408, 5121);
            this.intValue3 = GL11.glGenTextures();
            GL11.glBindTexture(3553, this.intValue3);
            GL11.glTexParameteri(3553, 10241, 9728);
            GL11.glTexParameteri(3553, 10240, 9728);
            GL11.glTexParameteri(3553, 10242, 33071);
            GL11.glTexParameteri(3553, 10243, 33071);
            GL11.glTexParameteri(3553, 34892, 0);
            RenderCapabilities.invoke(33190, this.intValue4, this.intValue5, 6402, 5125);
            this.intValue = GL30.glGenFramebuffers();
            GL30.glBindFramebuffer(36160, this.intValue);
            GL30.glFramebufferTexture2D(36160, 36064, 3553, this.intValue2, 0);
            GL30.glFramebufferTexture2D(36160, 36096, 3553, this.intValue3, 0);
            GL11.glDrawBuffer(36064);
            GL11.glReadBuffer(36064);
            intValue = GL30.glCheckFramebufferStatus(36160);
         } finally {
            FramebufferUtils.restoreGlState(glStateSnapshot);
         }

         if (intValue != 36053) {
            this.invoke2();
            throw new IllegalStateException("DepthRenderTarget incomplete: status=" + intValue);
         }
      }
   }

   public void invoke2() {
      if (this.intValue != 0) {
         GL30.glDeleteFramebuffers(this.intValue);
         this.intValue = 0;
      }

      if (this.intValue2 != 0) {
         GL11.glDeleteTextures(this.intValue2);
         this.intValue2 = 0;
      }

      if (this.intValue3 != 0) {
         GL11.glDeleteTextures(this.intValue3);
         this.intValue3 = 0;
      }

      this.intValue4 = 0;
      this.intValue5 = 0;
   }
}
