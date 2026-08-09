package ru.metaculture.protection;

import com.mojang.logging.LogUtils;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class GifTexture {
   private final List<GifTexture.GifTextureEntry> items = new ArrayList<>();
   private int intValue = 0;

   public GifTexture(Identifier identifier) {
      Optional optional = MinecraftClient.getInstance().getResourceManager().getResource(identifier);
      if (optional.isEmpty()) {
         LogUtils.getLogger().error("GIF файл не найден по пути: {}", identifier);
      } else {
         try (
            InputStream inputStream = ((Resource)optional.get()).getInputStream();
            ImageInputStream imageInputStream = ImageIO.createImageInputStream(inputStream);
         ) {
            ImageReader imageReader = ImageIO.getImageReadersByFormatName("gif").next();
            imageReader.setInput(imageInputStream);
            int intValue = imageReader.getNumImages(true);
            BufferedImage bufferedImage = null;
            Graphics2D graphics2D = null;
            int intValue2 = 0;
            int intValue3 = 0;

            for (int intValue4 = 0; intValue4 < intValue; intValue4++) {
               BufferedImage bufferedImage2 = imageReader.read(intValue4);
               if (bufferedImage == null) {
                  intValue2 = bufferedImage2.getWidth();
                  intValue3 = bufferedImage2.getHeight();
                  bufferedImage = new BufferedImage(intValue2, intValue3, 2);
                  graphics2D = bufferedImage.createGraphics();
                  graphics2D.setBackground(new Color(0, 0, 0, 0));
                  graphics2D.clearRect(0, 0, intValue2, intValue3);
               }

               int intValue5 = 0;
               int intValue6 = 0;
               int intValue7 = 100;
               String text = "none";

               try {
                  Node node = imageReader.getImageMetadata(intValue4).getAsTree("javax_imageio_gif_image_1.0");

                  for (int intValue8 = 0; intValue8 < node.getChildNodes().getLength(); intValue8++) {
                     Node node2 = node.getChildNodes().item(intValue8);
                     if (node2.getNodeName().equals("ImageDescriptor")) {
                        NamedNodeMap namedNodeMap = node2.getAttributes();
                        if (namedNodeMap.getNamedItem("imageLeftPosition") != null) {
                           intValue5 = Integer.parseInt(namedNodeMap.getNamedItem("imageLeftPosition").getNodeValue());
                        }

                        if (namedNodeMap.getNamedItem("imageTopPosition") != null) {
                           intValue6 = Integer.parseInt(namedNodeMap.getNamedItem("imageTopPosition").getNodeValue());
                        }
                     } else if (node2.getNodeName().equals("GraphicControlExtension")) {
                        NamedNodeMap namedNodeMap2 = node2.getAttributes();
                        if (namedNodeMap2.getNamedItem("delayTime") != null) {
                           intValue7 = Integer.parseInt(namedNodeMap2.getNamedItem("delayTime").getNodeValue()) * 10;
                        }

                        if (namedNodeMap2.getNamedItem("disposalMethod") != null) {
                           text = namedNodeMap2.getNamedItem("disposalMethod").getNodeValue();
                        }
                     }
                  }
               } catch (Exception exception) {
               }

               if (intValue7 <= 0) {
                  intValue7 = 100;
               }

               graphics2D.drawImage(bufferedImage2, intValue5, intValue6, null);
               int[] intValues = new int[intValue2 * intValue3];
               bufferedImage.getRGB(0, 0, intValue2, intValue3, intValues, 0, intValue2);
               if (text.equals("restoreToBackgroundColor")) {
                  graphics2D.clearRect(intValue5, intValue6, bufferedImage2.getWidth(), bufferedImage2.getHeight());
               }

               ByteBuffer byteBuffer = BufferUtils.createByteBuffer(intValue2 * intValue3 * 4);

               for (int intValue9 = 0; intValue9 < intValue3; intValue9++) {
                  for (int intValue10 = 0; intValue10 < intValue2; intValue10++) {
                     int intValue11 = intValues[intValue9 * intValue2 + intValue10];
                     byteBuffer.put((byte)(intValue11 >> 16 & 0xFF));
                     byteBuffer.put((byte)(intValue11 >> 8 & 0xFF));
                     byteBuffer.put((byte)(intValue11 & 0xFF));
                     byteBuffer.put((byte)(intValue11 >> 24 & 0xFF));
                  }
               }

               byteBuffer.flip();
               int intValue12 = GL11.glGenTextures();
               GL11.glBindTexture(3553, intValue12);
               GL11.glPixelStorei(3317, 1);
               GL11.glTexParameteri(3553, 10241, 9729);
               GL11.glTexParameteri(3553, 10240, 9729);
               GL11.glTexParameteri(3553, 10242, 33071);
               GL11.glTexParameteri(3553, 10243, 33071);
               GL11.glTexImage2D(3553, 0, 32856, intValue2, intValue3, 0, 6408, 5121, byteBuffer);
               this.items.add(new GifTexture.GifTextureEntry(intValue12, intValue7));
               this.intValue += intValue7;
            }

            if (graphics2D != null) {
               graphics2D.dispose();
            }

            imageReader.dispose();
         } catch (Exception exception2) {
            LogUtils.getLogger().error("Ошибка при обработке GIF файла", exception2);
         }
      }
   }

   public int compute() {
      if (this.items.isEmpty()) {
         return -1;
      } else if (this.items.size() == 1) {
         return this.items.getFirst().id();
      } else {
         int intValue13 = (int)(System.currentTimeMillis() % Math.max(1, this.intValue));
         int intValue14 = 0;

         for (GifTexture.GifTextureEntry gifTextureEntry : this.items) {
            intValue14 += gifTextureEntry.delay();
            if (intValue13 <= intValue14) {
               return gifTextureEntry.id();
            }
         }

         return this.items.getLast().id();
      }
   }

   record GifTextureEntry(int id, int delay) {
   }
}
