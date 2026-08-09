package ru.metaculture.protection;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

public final class TextureLoader {
   private static RenderEngine renderEngine;
   private static final Map<String, Integer> VALUES_BY_KEY = new HashMap<>();
   private static final Set<String> VALUES = ConcurrentHashMap.newKeySet();
   private static int intValue;

   private TextureLoader() {
   }

   public static void setRenderEngine(RenderEngine renderEngine) {
      TextureLoader.renderEngine = renderEngine;
   }

   public static int compute(String string) {
      if (renderEngine == null) {
         throw new IllegalStateException("TextureLoader.initialize() must be called first");
      } else {
         Integer integerValue = VALUES_BY_KEY.get(string);
         if (integerValue != null) {
            return integerValue;
         } else {
            int intValue = compute3(string);
            if (intValue > 0) {
               VALUES_BY_KEY.put(string, intValue);
               return intValue;
            } else {
               if (VALUES.add(string)) {
                  System.err.println("[TextureLoader] Falling back for: " + string);
               }

               return compute2();
            }
         }
      }
   }

   public static synchronized int compute2() {
      if (intValue != 0) {
         return intValue;
      } else {
         try {
            ByteBuffer byteBuffer = BufferUtils.createByteBuffer(4);
            byteBuffer.put((byte)0).put((byte)0).put((byte)0).put((byte)0).flip();
            int intValue2 = GL11.glGenTextures();
            if (intValue2 <= 0) {
               return 0;
            } else {
               int intValue3 = GL11.glGetInteger(32873);
               GL11.glBindTexture(3553, intValue2);
               GL11.glTexParameteri(3553, 10241, 9729);
               GL11.glTexParameteri(3553, 10240, 9729);
               GL11.glTexParameteri(3553, 10242, 33071);
               GL11.glTexParameteri(3553, 10243, 33071);
               GL11.glTexImage2D(3553, 0, 32856, 1, 1, 0, 6408, 5121, byteBuffer);
               GL11.glBindTexture(3553, intValue3);
               intValue = intValue2;
               return intValue2;
            }
         } catch (Throwable exception) {
            return 0;
         }
      }
   }

   private static int compute3(String string) {
      ByteBuffer byteBuffer2;
      try {
         byteBuffer2 = ResourceUtils.resolve2(string);
      } catch (Exception exception2) {
         System.err.println("Failed to read texture resource: " + string);
         exception2.printStackTrace();
         return 0;
      }

      MemoryStack memoryStack = MemoryStack.stackPush();

      int intValue4;
      label49: {
         int intValue5;
         try {
            IntBuffer intBuffer = memoryStack.mallocInt(1);
            IntBuffer intBuffer2 = memoryStack.mallocInt(1);
            IntBuffer intBuffer3 = memoryStack.mallocInt(1);
            ByteBuffer byteBuffer3 = STBImage.stbi_load_from_memory(byteBuffer2, intBuffer, intBuffer2, intBuffer3, 4);
            if (byteBuffer3 == null) {
               System.err.println("Failed to decode texture: " + string + " - " + STBImage.stbi_failure_reason());
               intValue4 = 0;
               break label49;
            }

            intValue4 = intBuffer.get(0);
            int intValue6 = intBuffer2.get(0);
            int intValue7 = renderEngine.compute3(intValue4, intValue6, byteBuffer3);
            STBImage.stbi_image_free(byteBuffer3);
            System.out.println("[TextureLoader] Loaded: " + string + " (" + intValue4 + "x" + intValue6 + ") -> ID " + intValue7);
            intValue5 = intValue7;
         } catch (Throwable exception3) {
            if (memoryStack != null) {
               try {
                  memoryStack.close();
               } catch (Throwable exception4) {
                  exception3.addSuppressed(exception4);
               }
            }

            throw exception3;
         }

         if (memoryStack != null) {
            memoryStack.close();
         }

         return intValue5;
      }

      if (memoryStack != null) {
         memoryStack.close();
      }

      return intValue4;
   }

   public static void invoke() {
      VALUES_BY_KEY.clear();
      VALUES.clear();
   }
}
