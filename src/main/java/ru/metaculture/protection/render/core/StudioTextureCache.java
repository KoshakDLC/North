package ru.metaculture.protection;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

public final class StudioTextureCache {
   private static final StudioTextureCache INSTANCE = new StudioTextureCache();
   private static final int INT_VALUE = -1;
   private final Map<String, Integer> valuesByKey = new HashMap<>();

   private StudioTextureCache() {
   }

   public static StudioTextureCache getINSTANCE() {
      return INSTANCE;
   }

   public int compute(String string, int i, StudioModel studioModel) {
      if (studioModel == null) {
         return 0;
      } else {
         StudioModel.StudioModelState6 studioModelState6 = studioModel.resolve(i);
         if (studioModelState6 != null && studioModelState6.getBytes().length != 0) {
            String text = string + "|" + i;
            Integer integerValue = this.valuesByKey.get(text);
            if (integerValue != null) {
               return integerValue == -1 ? 0 : integerValue;
            } else {
               int intValue = this.compute2(studioModelState6);
               this.valuesByKey.put(text, intValue <= 0 ? -1 : intValue);
               return intValue <= 0 ? 0 : intValue;
            }
         } else {
            return 0;
         }
      }
   }

   private int compute2(StudioModel.StudioModelState6 studioModelState62) {
      byte[] byteValues = studioModelState62.getBytes();
      ByteBuffer byteBuffer = MemoryUtil.memAlloc(byteValues.length);
      boolean flag = false ;

      int intValue2;
      label347: {
         int intValue3;
         label346: {
            int intValue4;
            label345: {
               byte byteValue;
               label344: {
                  byte byteValue2;
                  try {
                     flag = true;
                     byteBuffer.put(byteValues).flip();
                     MemoryStack memoryStack = MemoryStack.stackPush();

                     label337: {
                        label336: {
                           label335: {
                              try {
                                 IntBuffer intBuffer = memoryStack.mallocInt(1);
                                 IntBuffer intBuffer2 = memoryStack.mallocInt(1);
                                 IntBuffer intBuffer3 = memoryStack.mallocInt(1);
                                 ByteBuffer byteBuffer2 = STBImage.stbi_load_from_memory(byteBuffer, intBuffer, intBuffer2, intBuffer3, 4);
                                 if (byteBuffer2 == null) {
                                    System.out.println("[Studio] texture decode failed: " + STBImage.stbi_failure_reason());
                                    intValue2 = 0;
                                    break label336;
                                 }

                                 boolean flag2 = false ;

                                 label331: {
                                    label330: {
                                       try {
                                          flag2 = true;
                                          intValue2 = intBuffer.get(0);
                                          int intValue5 = intBuffer2.get(0);
                                          long longValue = (long)intValue2 * intValue5 * 4L;
                                          int intValue6 = GL11.glGetInteger(3379);
                                          if (intValue2 <= 0 || intValue5 <= 0 || intValue2 > intValue6 || intValue5 > intValue6 || longValue > byteBuffer2.remaining()) {
                                             System.out.println("[Studio] invalid texture dimensions: " + intValue2 + "x" + intValue5);
                                             intValue3 = 0;
                                             flag2 = false;
                                             break label331;
                                          }

                                          intValue3 = GL11.glGetInteger(32873);
                                          int intValue7 = GL11.glGetInteger(35055);
                                          int intValue8 = GL11.glGetInteger(3317);
                                          int intValue9 = GL11.glGetInteger(3314);
                                          int intValue10 = GL11.glGetInteger(3315);
                                          int intValue11 = GL11.glGetInteger(3316);
                                          int intValue12 = GL11.glGetInteger(3312);
                                          int intValue13 = GL11.glGetInteger(3313);
                                          int intValue14 = 0;
                                          boolean flag3 = false ;

                                          label326: {
                                             try {
                                                flag3 = true;
                                                intValue14 = GL11.glGenTextures();
                                                GL11.glBindTexture(3553, intValue14);
                                                GL11.glTexParameteri(3553, 10241, 9728);
                                                GL11.glTexParameteri(3553, 10240, 9728);
                                                GL11.glTexParameteri(3553, 10242, 33071);
                                                GL11.glTexParameteri(3553, 10243, 33071);
                                                GL15.glBindBuffer(35052, 0);
                                                GL11.glPixelStorei(3317, 1);
                                                GL11.glPixelStorei(3314, 0);
                                                GL11.glPixelStorei(3315, 0);
                                                GL11.glPixelStorei(3316, 0);
                                                GL11.glPixelStorei(3312, 0);
                                                GL11.glPixelStorei(3313, 0);
                                                GL11.glTexImage2D(3553, 0, 32856, intValue2, intValue5, 0, 6408, 5121, byteBuffer2);
                                                intValue4 = intValue14;
                                                flag3 = false;
                                             } catch (Throwable exception) {
                                                if (intValue14 > 0) {
                                                   GL11.glDeleteTextures(intValue14);
                                                }

                                                System.out
                                                   .println("[Studio] texture upload failed: " + exception.getClass().getSimpleName() + ": " + exception.getMessage());
                                                byteValue = 0;
                                                flag3 = false;
                                                break label326;
                                             } finally {
                                                if (flag3) {
                                                   GL11.glPixelStorei(3313, intValue13);
                                                   GL11.glPixelStorei(3312, intValue12);
                                                   GL11.glPixelStorei(3316, intValue11);
                                                   GL11.glPixelStorei(3315, intValue10);
                                                   GL11.glPixelStorei(3314, intValue9);
                                                   GL11.glPixelStorei(3317, intValue8);
                                                   GL15.glBindBuffer(35052, intValue7);
                                                   GL11.glBindTexture(3553, intValue3);
                                                }
                                             }

                                             GL11.glPixelStorei(3313, intValue13);
                                             GL11.glPixelStorei(3312, intValue12);
                                             GL11.glPixelStorei(3316, intValue11);
                                             GL11.glPixelStorei(3315, intValue10);
                                             GL11.glPixelStorei(3314, intValue9);
                                             GL11.glPixelStorei(3317, intValue8);
                                             GL15.glBindBuffer(35052, intValue7);
                                             GL11.glBindTexture(3553, intValue3);
                                             flag2 = false;
                                             break label330;
                                          }

                                          GL11.glPixelStorei(3313, intValue13);
                                          GL11.glPixelStorei(3312, intValue12);
                                          GL11.glPixelStorei(3316, intValue11);
                                          GL11.glPixelStorei(3315, intValue10);
                                          GL11.glPixelStorei(3314, intValue9);
                                          GL11.glPixelStorei(3317, intValue8);
                                          GL15.glBindBuffer(35052, intValue7);
                                          GL11.glBindTexture(3553, intValue3);
                                          flag2 = false;
                                       } finally {
                                          if (flag2) {
                                             STBImage.stbi_image_free(byteBuffer2);
                                          }
                                       }

                                       STBImage.stbi_image_free(byteBuffer2);
                                       break label337;
                                    }

                                    STBImage.stbi_image_free(byteBuffer2);
                                    break label335;
                                 }

                                 STBImage.stbi_image_free(byteBuffer2);
                              } catch (Throwable exception2) {
                                 if (memoryStack != null) {
                                    try {
                                       memoryStack.close();
                                    } catch (Throwable exception3) {
                                       exception2.addSuppressed(exception3);
                                    }
                                 }

                                 throw exception2;
                              }

                              if (memoryStack != null) {
                                 memoryStack.close();
                                 flag = false;
                              } else {
                                 flag = false;
                              }
                              break label346;
                           }

                           if (memoryStack != null) {
                              memoryStack.close();
                              flag = false;
                           } else {
                              flag = false;
                           }
                           break label345;
                        }

                        if (memoryStack != null) {
                           memoryStack.close();
                           flag = false;
                        } else {
                           flag = false;
                        }
                        break label347;
                     }

                     if (memoryStack != null) {
                        memoryStack.close();
                        flag = false;
                     } else {
                        flag = false;
                     }
                     break label344;
                  } catch (Throwable exception4) {
                     System.out.println("[Studio] texture upload failed: " + exception4.getClass().getSimpleName() + ": " + exception4.getMessage());
                     byteValue2 = 0;
                     flag = false;
                  } finally {
                     if (flag) {
                        MemoryUtil.memFree(byteBuffer);
                     }
                  }

                  MemoryUtil.memFree(byteBuffer);
                  return byteValue2;
               }

               MemoryUtil.memFree(byteBuffer);
               return byteValue;
            }

            MemoryUtil.memFree(byteBuffer);
            return intValue4;
         }

         MemoryUtil.memFree(byteBuffer);
         return intValue3;
      }

      MemoryUtil.memFree(byteBuffer);
      return intValue2;
   }

   public void invoke(String string) {
      String text2 = string + "|";
      this.valuesByKey.entrySet().removeIf(entry -> {
         if (entry.getKey().startsWith(text2)) {
            return false;
         } else {
            int var2x = entry.getValue();
            if (var2x > 0) {
               try {
                  GL11.glDeleteTextures(var2x);
               } catch (Throwable exception5) {
               }
            }

            return true;
         }
      });
   }
}
