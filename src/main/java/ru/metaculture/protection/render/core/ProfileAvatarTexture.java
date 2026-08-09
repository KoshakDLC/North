package ru.metaculture.protection;

import com.mojang.logging.LogUtils;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Optional;
import javax.imageio.ImageIO;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

final class ProfileAvatarTexture {
   private static final Identifier IDENTIFIER = Identifier.of("wild", "textures/profile/gigachad.jpg");
   private static final int INT_VALUE = 256;
   private static final float FLOAT_VALUE = 0.45F;
   private static final float FLOAT_VALUE_2 = 0.27F;
   private static final float FLOAT_VALUE_3 = 0.42F;
   private static int intValue;
   private static boolean flag;

   private ProfileAvatarTexture() {
   }

   static int compute() {
      if (!flag) {
         flag = true;
         intValue = compute2();
      }

      return intValue;
   }

   private static int compute2() {
      MinecraftClient client = MinecraftClient.getInstance();
      if (client != null && client.getResourceManager() != null) {
         Optional optional = client.getResourceManager().getResource(IDENTIFIER);
         if (optional.isEmpty()) {
            return -1;
         } else {
            try {
               int intValue;
               try (InputStream inputStream = ((Resource)optional.get()).getInputStream()) {
                  BufferedImage bufferedImage2 = ImageIO.read(inputStream);
                  if (bufferedImage2 == null) {
                     return -1;
                  }

                  BufferedImage bufferedImage3 = resolve(bufferedImage2);
                  ByteBuffer byteBuffer2 = resolve2(bufferedImage3);
                  intValue = compute3(byteBuffer2, bufferedImage3.getWidth(), bufferedImage3.getHeight());
               }

               return intValue;
            } catch (Throwable exception) {
               LogUtils.getLogger().error("[low free] failed to load profile avatar texture", exception);
               return -1;
            }
         }
      } else {
         return -1;
      }
   }

   private static BufferedImage resolve(BufferedImage bufferedImage) {
      int intValue2 = bufferedImage.getWidth();
      int intValue3 = bufferedImage.getHeight();
      int intValue4 = Math.max(1, Math.min(Math.min(intValue2, intValue3), Math.round(0.42F * intValue3)));
      int intValue5 = Math.max(0, Math.min(intValue2 - intValue4, Math.round(0.45F * intValue2) - intValue4 / 2));
      int intValue6 = Math.max(0, Math.min(intValue3 - intValue4, Math.round(0.27F * intValue3) - intValue4 / 2));
      BufferedImage bufferedImage4 = new BufferedImage(256, 256, 2);
      Graphics2D graphics2D = bufferedImage4.createGraphics();
      graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
      graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
      graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      graphics2D.drawImage(bufferedImage, 0, 0, 256, 256, intValue5, intValue6, intValue5 + intValue4, intValue6 + intValue4, null);
      graphics2D.dispose();
      return bufferedImage4;
   }

   private static ByteBuffer resolve2(BufferedImage bufferedImage) {
      int intValue7 = bufferedImage.getWidth();
      int intValue8 = bufferedImage.getHeight();
      int[] intValues = new int[intValue7 * intValue8];
      bufferedImage.getRGB(0, 0, intValue7, intValue8, intValues, 0, intValue7);
      ByteBuffer byteBuffer3 = BufferUtils.createByteBuffer(intValue7 * intValue8 * 4);

      for (int intValue9 = 0; intValue9 < intValue8; intValue9++) {
         for (int intValue10 = 0; intValue10 < intValue7; intValue10++) {
            int intValue11 = intValues[intValue9 * intValue7 + intValue10];
            byteBuffer3.put((byte)(intValue11 >> 16 & 0xFF));
            byteBuffer3.put((byte)(intValue11 >> 8 & 0xFF));
            byteBuffer3.put((byte)(intValue11 & 0xFF));
            byteBuffer3.put((byte)(intValue11 >> 24 & 0xFF));
         }
      }

      byteBuffer3.flip();
      return byteBuffer3;
   }

   private static int compute3(ByteBuffer byteBuffer, int i, int j) {
      FramebufferUtils.GlStateSnapshot glStateSnapshot = FramebufferUtils.captureGlState();

      int intValue12;
      try {
         int intValue13 = GL11.glGenTextures();
         GL11.glBindTexture(3553, intValue13);
         GL11.glPixelStorei(3317, 1);
         GL11.glTexParameteri(3553, 10241, 9987);
         GL11.glTexParameteri(3553, 10240, 9729);
         GL11.glTexParameteri(3553, 10242, 33071);
         GL11.glTexParameteri(3553, 10243, 33071);
         GL11.glTexImage2D(3553, 0, 32856, i, j, 0, 6408, 5121, byteBuffer);
         GL30.glGenerateMipmap(3553);
         intValue12 = intValue13;
      } finally {
         FramebufferUtils.restoreGlState(glStateSnapshot);
      }

      return intValue12;
   }
}
