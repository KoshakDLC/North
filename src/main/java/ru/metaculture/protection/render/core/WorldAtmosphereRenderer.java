package ru.metaculture.protection;

import com.mojang.blaze3d.systems.RenderSystem;
import java.nio.FloatBuffer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.texture.GlTexture;
import net.minecraft.client.util.Window;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;

public final class WorldAtmosphereRenderer implements AutoCloseable {
   private static final WorldAtmosphereRenderer INSTANCE = new WorldAtmosphereRenderer();
   private static final String ASSETS_WILD_SHADERS_WORLD_WORLD_VOLUME_VERT = "assets/wild/shaders/world/world_volume.vert";
   private static final String ASSETS_WILD_SHADERS_WORLD_WORLD_FOG_FRESNEL_FRAG = "assets/wild/shaders/world/world_fog_fresnel.frag";
   private static final String ASSETS_WILD_SHADERS_WORLD_AMBIENT_PARTICLES_FRAG = "assets/wild/shaders/world/ambient_particles.frag";
   private static final String ASSETS_WILD_SHADERS_WORLD_WORLD_COPY_FRAG = "assets/wild/shaders/world/world_copy.frag";
   private static final float FLOAT_VALUE = 1.0E-4F;
   private final WorldAtmosphereRenderer.WorldAtmosphereRendererState4 worldAtmosphereRendererState4 = new WorldAtmosphereRenderer.WorldAtmosphereRendererState4();
   private final WorldAtmosphereRenderer.WorldAtmosphereRendererState4 worldAtmosphereRendererState42 = new WorldAtmosphereRenderer.WorldAtmosphereRendererState4();
   private WorldAtmosphereRenderer.WorldAtmosphereRendererState3 worldAtmosphereRendererState3;
   private WorldAtmosphereRenderer.WorldAtmosphereRendererState3 worldAtmosphereRendererState32;
   private WorldAtmosphereRenderer.WorldAtmosphereRendererState3 worldAtmosphereRendererState33;
   private int intValue;
   private int intValue2;
   private int intValue3;
   private int intValue4;
   private boolean flag;
   private boolean flag2;

   private WorldAtmosphereRenderer() {
   }

   public static WorldAtmosphereRenderer getINSTANCE() {
      return INSTANCE;
   }

   public void invoke(MinecraftClient minecraftClient, Camera camera, Matrix4f matrix4f, Matrix4f matrix4f2, WorldAtmosphereRenderer.WorldAtmosphereRendererState2 worldAtmosphereRendererState2) {
      if (!this.flag2 && minecraftClient != null && camera != null && matrix4f != null && matrix4f2 != null && worldAtmosphereRendererState2 != null) {
         if (minecraftClient.world != null && minecraftClient.player != null && check3(minecraftClient)) {
            Window window = minecraftClient.getWindow();
            int intValue = window.getFramebufferWidth();
            int intValue2 = window.getFramebufferHeight();
            if (intValue > 1 && intValue2 > 1) {
               Framebuffer framebuffer = minecraftClient.getFramebuffer();
               if (framebuffer != null) {
                  int intValue3 = compute(framebuffer.getColorAttachment());
                  int intValue4 = compute(framebuffer.getDepthAttachment());
                  if (intValue3 > 0 && intValue4 > 0) {
                     FramebufferUtils.GlStateSnapshot glStateSnapshot = FramebufferUtils.captureGlState();
                     boolean flag = false ;

                     label188: {
                        label178: {
                           label189: {
                              try {
                                 flag = true;
                                 this.invoke6();
                                 if (!this.flag2) {
                                    if (this.check2(this.worldAtmosphereRendererState4, intValue, intValue2)) {
                                       if (this.check2(this.worldAtmosphereRendererState42, intValue, intValue2)) {
                                          if (!this.check(intValue3, intValue, intValue2, this.worldAtmosphereRendererState4)) {
                                             flag = false;
                                             break label188;
                                          }

                                          Vec3d vec3d2 = camera.getPos();
                                          Matrix4f matrix4f4 = new Matrix4f(matrix4f2).invert();
                                          Matrix4f matrix4f5 = new Matrix4f(matrix4f).invert();
                                          matrix4f5.m30((float)vec3d2.x);
                                          matrix4f5.m31((float)vec3d2.y);
                                          matrix4f5.m32((float)vec3d2.z);
                                          Matrix4f matrix4f6 = new Matrix4f(matrix4f5).mul(matrix4f4);
                                          WorldAtmosphereRenderer.WorldAtmosphereRendererState worldAtmosphereRendererState = new WorldAtmosphereRenderer.WorldAtmosphereRendererState(intValue, intValue2, intValue4, vec3d2, matrix4f4, matrix4f5, matrix4f6, worldAtmosphereRendererState2);
                                          int intValue5 = this.worldAtmosphereRendererState4.intValue2;
                                          int intValue6 = this.worldAtmosphereRendererState42.intValue2;
                                          if (worldAtmosphereRendererState2.floatValue5 > 1.0E-4F) {
                                             this.invoke3(this.worldAtmosphereRendererState3, intValue5, intValue6, worldAtmosphereRendererState);
                                             intValue5 = intValue6;
                                             intValue6 = this.worldAtmosphereRendererState4.intValue2;
                                          }

                                          this.invoke3(this.worldAtmosphereRendererState32, intValue5, intValue6, worldAtmosphereRendererState);
                                          this.invoke3(this.worldAtmosphereRendererState33, intValue6, intValue3, worldAtmosphereRendererState);
                                          flag = false;
                                          break label178;
                                       }

                                       flag = false;
                                    } else {
                                       flag = false;
                                    }
                                 } else {
                                    flag = false;
                                 }
                                 break label189;
                              } catch (Throwable exception) {
                                 this.flag2 = true;
                                 System.err.println("[WorldTweaks] renderer disabled: " + exception.getMessage());
                                 exception.printStackTrace();
                                 flag = false;
                              } finally {
                                 if (flag) {
                                    if (this.intValue2 != 0) {
                                       GL30.glBindFramebuffer(36160, this.intValue2);
                                       GL30.glFramebufferTexture2D(36160, 36064, 3553, 0, 0);
                                    }

                                    GL20.glUseProgram(0);
                                    FramebufferUtils.restoreGlState(glStateSnapshot);
                                 }
                              }

                              if (this.intValue2 != 0) {
                                 GL30.glBindFramebuffer(36160, this.intValue2);
                                 GL30.glFramebufferTexture2D(36160, 36064, 3553, 0, 0);
                              }

                              GL20.glUseProgram(0);
                              FramebufferUtils.restoreGlState(glStateSnapshot);
                              return;
                           }

                           if (this.intValue2 != 0) {
                              GL30.glBindFramebuffer(36160, this.intValue2);
                              GL30.glFramebufferTexture2D(36160, 36064, 3553, 0, 0);
                           }

                           GL20.glUseProgram(0);
                           FramebufferUtils.restoreGlState(glStateSnapshot);
                           return;
                        }

                        if (this.intValue2 != 0) {
                           GL30.glBindFramebuffer(36160, this.intValue2);
                           GL30.glFramebufferTexture2D(36160, 36064, 3553, 0, 0);
                        }

                        GL20.glUseProgram(0);
                        FramebufferUtils.restoreGlState(glStateSnapshot);
                        return;
                     }

                     if (this.intValue2 != 0) {
                        GL30.glBindFramebuffer(36160, this.intValue2);
                        GL30.glFramebufferTexture2D(36160, 36064, 3553, 0, 0);
                     }

                     GL20.glUseProgram(0);
                     FramebufferUtils.restoreGlState(glStateSnapshot);
                  }
               }
            }
         }
      }
   }

   public void invoke2(int i, int j) {
      if (i > 0 && j > 0) {
         if (this.worldAtmosphereRendererState4.intValue3 > 0 && (this.worldAtmosphereRendererState4.intValue3 != i || this.worldAtmosphereRendererState4.intValue4 != j)
            || this.worldAtmosphereRendererState42.intValue3 > 0 && (this.worldAtmosphereRendererState42.intValue3 != i || this.worldAtmosphereRendererState42.intValue4 != j)) {
            this.invoke7(this.worldAtmosphereRendererState4);
            this.invoke7(this.worldAtmosphereRendererState42);
         }
      } else {
         this.invoke7(this.worldAtmosphereRendererState4);
         this.invoke7(this.worldAtmosphereRendererState42);
      }
   }

   private void invoke3(WorldAtmosphereRenderer.WorldAtmosphereRendererState3 worldAtmosphereRendererState3, int i, int j, WorldAtmosphereRenderer.WorldAtmosphereRendererState worldAtmosphereRendererState4) {
      if (worldAtmosphereRendererState3 != null && i > 0 && j > 0 && worldAtmosphereRendererState4 != null) {
         if (this.intValue2 == 0) {
            this.intValue2 = GL30.glGenFramebuffers();
         }

         GL30.glBindFramebuffer(36160, this.intValue2);
         GL30.glFramebufferTexture2D(36160, 36064, 3553, j, 0);
         GL11.glDrawBuffer(36064);
         if (GL30.glCheckFramebufferStatus(36160) == 36053) {
            GL11.glViewport(0, 0, worldAtmosphereRendererState4.intValue, worldAtmosphereRendererState4.intValue2);
            GL11.glDisable(3089);
            GL11.glDisable(2929);
            GL11.glDisable(2884);
            GL11.glDisable(3042);
            GL11.glDisable(36281);
            GL11.glColorMask(true, true, true, true);
            GL11.glDepthMask(false);
            worldAtmosphereRendererState3.glShaderProgram.invoke();
            this.invoke4(worldAtmosphereRendererState3, i, worldAtmosphereRendererState4);
            GL30.glBindVertexArray(this.intValue3);
            GlStateGuard.getINSTANCE().invoke2(2);
            GL11.glDrawArrays(4, 0, 6);
            GL30.glBindVertexArray(0);
         }
      }
   }

   private void invoke4(WorldAtmosphereRenderer.WorldAtmosphereRendererState3 worldAtmosphereRendererState32, int i, WorldAtmosphereRenderer.WorldAtmosphereRendererState worldAtmosphereRendererState5) {
      if (worldAtmosphereRendererState32.intValue >= 0) {
         GL20.glUniform1i(worldAtmosphereRendererState32.intValue, 0);
      }

      if (worldAtmosphereRendererState32.intValue2 >= 0) {
         GL20.glUniform1i(worldAtmosphereRendererState32.intValue2, 1);
      }

      if (worldAtmosphereRendererState32.intValue3 >= 0) {
         GL20.glUniform2f(worldAtmosphereRendererState32.intValue3, worldAtmosphereRendererState5.intValue, worldAtmosphereRendererState5.intValue2);
      }

      if (worldAtmosphereRendererState32.intValue4 >= 0) {
         GL20.glUniform1f(worldAtmosphereRendererState32.intValue4, worldAtmosphereRendererState5.worldAtmosphereRendererState2.floatValue15);
      }

      if (worldAtmosphereRendererState32.intValue5 >= 0) {
         GL20.glUniform3f(worldAtmosphereRendererState32.intValue5, (float)worldAtmosphereRendererState5.vec3d.x, (float)worldAtmosphereRendererState5.vec3d.y, (float)worldAtmosphereRendererState5.vec3d.z);
      }

      if (worldAtmosphereRendererState32.intValue6 >= 0) {
         this.invoke5(worldAtmosphereRendererState32.intValue6, worldAtmosphereRendererState5.matrix4f);
      }

      if (worldAtmosphereRendererState32.intValue7 >= 0) {
         this.invoke5(worldAtmosphereRendererState32.intValue7, worldAtmosphereRendererState5.matrix4f2);
      }

      if (worldAtmosphereRendererState32.intValue8 >= 0) {
         this.invoke5(worldAtmosphereRendererState32.intValue8, worldAtmosphereRendererState5.matrix4f3);
      }

      if (worldAtmosphereRendererState32.intValue9 >= 0) {
         GL20.glUniform3f(
            worldAtmosphereRendererState32.intValue9, worldAtmosphereRendererState5.worldAtmosphereRendererState2.floatValue9, worldAtmosphereRendererState5.worldAtmosphereRendererState2.floatValue10, worldAtmosphereRendererState5.worldAtmosphereRendererState2.floatValue11
         );
      }

      if (worldAtmosphereRendererState32.intValue10 >= 0) {
         GL20.glUniform3f(
            worldAtmosphereRendererState32.intValue10, worldAtmosphereRendererState5.worldAtmosphereRendererState2.floatValue12, worldAtmosphereRendererState5.worldAtmosphereRendererState2.floatValue13, worldAtmosphereRendererState5.worldAtmosphereRendererState2.floatValue14
         );
      }

      if (worldAtmosphereRendererState32.intValue11 >= 0) {
         GL20.glUniform1f(worldAtmosphereRendererState32.intValue11, measure(worldAtmosphereRendererState5.worldAtmosphereRendererState2.floatValue5, 0.0F, 0.1F));
      }

      if (worldAtmosphereRendererState32.intValue12 >= 0) {
         GL20.glUniform1f(worldAtmosphereRendererState32.intValue12, measure(worldAtmosphereRendererState5.worldAtmosphereRendererState2.floatValue6, 0.0F, 1.0F));
      }

      if (worldAtmosphereRendererState32.intValue13 >= 0) {
         GL20.glUniform1f(worldAtmosphereRendererState32.intValue13, measure(worldAtmosphereRendererState5.worldAtmosphereRendererState2.floatValue7, 0.0F, 1.0F));
      }

      if (worldAtmosphereRendererState32.intValue14 >= 0) {
         GL20.glUniform1f(worldAtmosphereRendererState32.intValue14, measure(worldAtmosphereRendererState5.worldAtmosphereRendererState2.floatValue8, 0.0F, 1.0F));
      }

      if (worldAtmosphereRendererState32.intValue15 >= 0) {
         GL20.glUniform1f(worldAtmosphereRendererState32.intValue15, measure(worldAtmosphereRendererState5.worldAtmosphereRendererState2.floatValue, 0.0F, 2.0F));
      }

      if (worldAtmosphereRendererState32.intValue16 >= 0) {
         GL20.glUniform3f(
            worldAtmosphereRendererState32.intValue16, worldAtmosphereRendererState5.worldAtmosphereRendererState2.floatValue2, worldAtmosphereRendererState5.worldAtmosphereRendererState2.floatValue3, worldAtmosphereRendererState5.worldAtmosphereRendererState2.floatValue4
         );
      }

      GL13.glActiveTexture(33984);
      GL11.glBindTexture(3553, i);
      GL13.glActiveTexture(33985);
      GL11.glBindTexture(3553, worldAtmosphereRendererState5.intValue3);
      GL13.glActiveTexture(33984);
   }

   private void invoke5(int i, Matrix4f matrix4f) {
      MemoryStack memoryStack = MemoryStack.stackPush();

      try {
         FloatBuffer floatBuffer = memoryStack.mallocFloat(16);
         matrix4f.get(floatBuffer);
         GL20.glUniformMatrix4fv(i, false, floatBuffer);
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
      }
   }

   private boolean check(int i, int j, int k, WorldAtmosphereRenderer.WorldAtmosphereRendererState4 worldAtmosphereRendererState42) {
      if (i > 0 && worldAtmosphereRendererState42 != null && worldAtmosphereRendererState42.intValue > 0 && j > 0 && k > 0) {
         if (this.intValue == 0) {
            this.intValue = GL30.glGenFramebuffers();
         }

         GL30.glBindFramebuffer(36008, this.intValue);
         GL30.glFramebufferTexture2D(36008, 36064, 3553, i, 0);
         if (GL30.glCheckFramebufferStatus(36008) != 36053) {
            return false;
         } else {
            GL30.glBindFramebuffer(36009, worldAtmosphereRendererState42.intValue);
            GL11.glReadBuffer(36064);
            GL11.glDrawBuffer(36064);
            GL30.glBlitFramebuffer(0, 0, j, k, 0, 0, j, k, 16384, 9728);
            GL30.glBindFramebuffer(36008, this.intValue);
            GL30.glFramebufferTexture2D(36008, 36064, 3553, 0, 0);
            return true;
         }
      } else {
         return false;
      }
   }

   private boolean check2(WorldAtmosphereRenderer.WorldAtmosphereRendererState4 worldAtmosphereRendererState43, int i, int j) {
      if (worldAtmosphereRendererState43 != null && i > 0 && j > 0) {
         if (worldAtmosphereRendererState43.intValue2 != 0 && (worldAtmosphereRendererState43.intValue3 != i || worldAtmosphereRendererState43.intValue4 != j || worldAtmosphereRendererState43.intValue == 0)) {
            this.invoke7(worldAtmosphereRendererState43);
         }

         if (worldAtmosphereRendererState43.intValue2 == 0) {
            worldAtmosphereRendererState43.intValue2 = GL11.glGenTextures();
            GL11.glBindTexture(3553, worldAtmosphereRendererState43.intValue2);
            GL11.glTexParameteri(3553, 10241, 9729);
            GL11.glTexParameteri(3553, 10240, 9729);
            GL11.glTexParameteri(3553, 10242, 33071);
            GL11.glTexParameteri(3553, 10243, 33071);
            RenderCapabilities.invoke(32856, i, j, 6408, 5121);
            worldAtmosphereRendererState43.intValue = GL30.glGenFramebuffers();
            GL30.glBindFramebuffer(36160, worldAtmosphereRendererState43.intValue);
            GL30.glFramebufferTexture2D(36160, 36064, 3553, worldAtmosphereRendererState43.intValue2, 0);
            GL11.glDrawBuffer(36064);
            if (GL30.glCheckFramebufferStatus(36160) != 36053) {
               this.invoke7(worldAtmosphereRendererState43);
               return false;
            }
         }

         worldAtmosphereRendererState43.intValue3 = i;
         worldAtmosphereRendererState43.intValue4 = j;
         return true;
      } else {
         return false;
      }
   }

   private void invoke6() {
      if (!this.flag) {
         this.intValue3 = GL30.glGenVertexArrays();
         this.intValue4 = GL15.glGenBuffers();
         GL30.glBindVertexArray(this.intValue3);
         GL15.glBindBuffer(34962, this.intValue4);
         float[] floatValues = new float[]{
            -1.0F,
            -1.0F,
            0.0F,
            0.0F,
            1.0F,
            -1.0F,
            1.0F,
            0.0F,
            1.0F,
            1.0F,
            1.0F,
            1.0F,
            -1.0F,
            -1.0F,
            0.0F,
            0.0F,
            1.0F,
            1.0F,
            1.0F,
            1.0F,
            -1.0F,
            1.0F,
            0.0F,
            1.0F
         };
         GL15.glBufferData(34962, floatValues, 35044);
         byte byteValue = 16;
         GL20.glEnableVertexAttribArray(0);
         GL20.glVertexAttribPointer(0, 2, 5126, false, byteValue, 0L);
         GL20.glEnableVertexAttribArray(1);
         GL20.glVertexAttribPointer(1, 2, 5126, false, byteValue, 8L);
         GL15.glBindBuffer(34962, 0);
         GL30.glBindVertexArray(0);
         this.worldAtmosphereRendererState3 = new WorldAtmosphereRenderer.WorldAtmosphereRendererState3("assets/wild/shaders/world/world_fog_fresnel.frag");
         this.worldAtmosphereRendererState32 = new WorldAtmosphereRenderer.WorldAtmosphereRendererState3("assets/wild/shaders/world/ambient_particles.frag");
         this.worldAtmosphereRendererState33 = new WorldAtmosphereRenderer.WorldAtmosphereRendererState3("assets/wild/shaders/world/world_copy.frag");
         this.flag = true;
      }
   }

   private static int compute(Object object) {
      return object instanceof GlTexture glTexture ? glTexture.getGlId() : 0;
   }

   private static boolean check3(MinecraftClient minecraftClient) {
      if (minecraftClient != null && minecraftClient.getWindow() != null) {
         Window window2 = minecraftClient.getWindow();
         return !window2.hasZeroWidthOrHeight() && window2.getFramebufferWidth() > 0 && window2.getFramebufferHeight() > 0;
      } else {
         return false;
      }
   }

   private static float measure(float f, float g, float h) {
      return !Float.isFinite(f) ? g : Math.max(g, Math.min(h, f));
   }

   private static boolean check4() {
      return RenderSystem.isOnRenderThread() && GLFW.glfwGetCurrentContext() != 0L;
   }

   private void invoke7(WorldAtmosphereRenderer.WorldAtmosphereRendererState4 worldAtmosphereRendererState44) {
      if (worldAtmosphereRendererState44 != null) {
         if (worldAtmosphereRendererState44.intValue != 0 && check4()) {
            GL30.glDeleteFramebuffers(worldAtmosphereRendererState44.intValue);
         }

         if (worldAtmosphereRendererState44.intValue2 != 0 && check4()) {
            GL11.glDeleteTextures(worldAtmosphereRendererState44.intValue2);
         }

         worldAtmosphereRendererState44.intValue = 0;
         worldAtmosphereRendererState44.intValue2 = 0;
         worldAtmosphereRendererState44.intValue3 = 0;
         worldAtmosphereRendererState44.intValue4 = 0;
      }
   }

   @Override
   public void close() {
      if (!check4()) {
         this.invoke8();
      } else {
         this.invoke7(this.worldAtmosphereRendererState4);
         this.invoke7(this.worldAtmosphereRendererState42);
         if (this.intValue != 0) {
            GL30.glDeleteFramebuffers(this.intValue);
            this.intValue = 0;
         }

         if (this.intValue2 != 0) {
            GL30.glDeleteFramebuffers(this.intValue2);
            this.intValue2 = 0;
         }

         if (this.intValue3 != 0) {
            GL30.glDeleteVertexArrays(this.intValue3);
            this.intValue3 = 0;
         }

         if (this.intValue4 != 0) {
            GL15.glDeleteBuffers(this.intValue4);
            this.intValue4 = 0;
         }

         invoke9(this.worldAtmosphereRendererState3);
         invoke9(this.worldAtmosphereRendererState32);
         invoke9(this.worldAtmosphereRendererState33);
         this.worldAtmosphereRendererState3 = null;
         this.worldAtmosphereRendererState32 = null;
         this.worldAtmosphereRendererState33 = null;
         this.flag = false;
         this.flag2 = false;
      }
   }

   private void invoke8() {
      this.worldAtmosphereRendererState4.intValue = 0;
      this.worldAtmosphereRendererState4.intValue2 = 0;
      this.worldAtmosphereRendererState4.intValue3 = 0;
      this.worldAtmosphereRendererState4.intValue4 = 0;
      this.worldAtmosphereRendererState42.intValue = 0;
      this.worldAtmosphereRendererState42.intValue2 = 0;
      this.worldAtmosphereRendererState42.intValue3 = 0;
      this.worldAtmosphereRendererState42.intValue4 = 0;
      this.intValue = 0;
      this.intValue2 = 0;
      this.intValue3 = 0;
      this.intValue4 = 0;
      this.worldAtmosphereRendererState3 = null;
      this.worldAtmosphereRendererState32 = null;
      this.worldAtmosphereRendererState33 = null;
      this.flag = false;
      this.flag2 = false;
   }

   private static void invoke9(WorldAtmosphereRenderer.WorldAtmosphereRendererState3 worldAtmosphereRendererState33) {
      if (worldAtmosphereRendererState33 != null) {
         worldAtmosphereRendererState33.glShaderProgram.invoke2();
      }
   }

   static final class WorldAtmosphereRendererState {
      final int intValue;
      final int intValue2;
      final int intValue3;
      final Vec3d vec3d;
      final Matrix4f matrix4f;
      final Matrix4f matrix4f2;
      final Matrix4f matrix4f3;
      final WorldAtmosphereRenderer.WorldAtmosphereRendererState2 worldAtmosphereRendererState2;

      WorldAtmosphereRendererState(int i, int j, int k, Vec3d vec3d, Matrix4f matrix4f, Matrix4f matrix4f2, Matrix4f matrix4f3, WorldAtmosphereRenderer.WorldAtmosphereRendererState2 worldAtmosphereRendererState22) {
         this.intValue = i;
         this.intValue2 = j;
         this.intValue3 = k;
         this.vec3d = vec3d;
         this.matrix4f = matrix4f;
         this.matrix4f2 = matrix4f2;
         this.matrix4f3 = matrix4f3;
         this.worldAtmosphereRendererState2 = worldAtmosphereRendererState22;
      }
   }

   public static final class WorldAtmosphereRendererState2 {
      public float floatValue;
      public float floatValue2 = 0.819F;
      public float floatValue3;
      public float floatValue4 = 0.574F;
      public float floatValue5;
      public float floatValue6 = 0.82F;
      public float floatValue7 = 0.64F;
      public float floatValue8 = 0.72F;
      public float floatValue9 = 0.416F;
      public float floatValue10 = 0.482F;
      public float floatValue11 = 0.584F;
      public float floatValue12 = 0.5F;
      public float floatValue13 = 0.62F;
      public float floatValue14 = 0.78F;
      public float floatValue15;
   }

   static final class WorldAtmosphereRendererState3 {
      final GlShaderProgram glShaderProgram;
      final int intValue;
      final int intValue2;
      final int intValue3;
      final int intValue4;
      final int intValue5;
      final int intValue6;
      final int intValue7;
      final int intValue8;
      final int intValue9;
      final int intValue10;
      final int intValue11;
      final int intValue12;
      final int intValue13;
      final int intValue14;
      final int intValue15;
      final int intValue16;

      WorldAtmosphereRendererState3(String string) {
         this.glShaderProgram = GlShaderProgram.resolve("assets/wild/shaders/world/world_volume.vert", string);
         this.intValue = this.glShaderProgram.compute2("u_ScreenTexture");
         this.intValue2 = this.glShaderProgram.compute2("u_DepthTexture");
         this.intValue3 = this.glShaderProgram.compute2("u_Resolution");
         this.intValue4 = this.glShaderProgram.compute2("u_Time");
         this.intValue5 = this.glShaderProgram.compute2("u_CameraPos");
         this.intValue6 = this.glShaderProgram.compute2("u_InverseProjectionMatrix");
         this.intValue7 = this.glShaderProgram.compute2("u_InverseViewMatrix");
         this.intValue8 = this.glShaderProgram.compute2("u_InverseViewProjectionMatrix");
         this.intValue9 = this.glShaderProgram.compute2("u_AtmosphereTint");
         this.intValue10 = this.glShaderProgram.compute2("u_SkyColor");
         this.intValue11 = this.glShaderProgram.compute2("u_FogDensity");
         this.intValue12 = this.glShaderProgram.compute2("u_HorizonDissolve");
         this.intValue13 = this.glShaderProgram.compute2("u_SkyLift");
         this.intValue14 = this.glShaderProgram.compute2("u_EdgeSoftness");
         this.intValue15 = this.glShaderProgram.compute2("u_WindSpeed");
         this.intValue16 = this.glShaderProgram.compute2("u_WindDirection");
      }
   }

   static final class WorldAtmosphereRendererState4 {
      int intValue;
      int intValue2;
      int intValue3;
      int intValue4;
   }
}
