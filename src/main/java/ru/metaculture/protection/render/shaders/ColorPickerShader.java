package ru.metaculture.protection;

import com.mojang.blaze3d.opengl.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL20;

public final class ColorPickerShader {
   private static final String ASSETS_WILD_SHADERS_MAINMENU_MENU_QUAD_VERT = "assets/wild/shaders/mainmenu/menu_quad.vert";
   private static final String ASSETS_WILD_SHADERS_COLORPLUS_SB_SPECTRUM_FRAG = "assets/wild/shaders/colorplus/sb_spectrum.frag";
   private static final String ASSETS_WILD_SHADERS_COLORPLUS_HUE_STRIP_FRAG = "assets/wild/shaders/colorplus/hue_strip.frag";
   private static final String ASSETS_WILD_SHADERS_COLORPLUS_CP_PREVIEW_FRAG = "assets/wild/shaders/colorplus/cp_preview.frag";
   private static FullscreenQuad fullscreenQuad;
   private static ShaderProgram shaderProgram;
   private static FullscreenQuad.FullscreenQuadResources fullscreenQuadResources;
   private static FullscreenQuad.FullscreenQuadResources fullscreenQuadResources2;
   private static FullscreenQuad.FullscreenQuadResources fullscreenQuadResources3;
   private static boolean flag;

   private ColorPickerShader() {
   }

   public static synchronized FullscreenQuad.FullscreenQuadResources resolve() {
      if (flag) {
         return null;
      } else {
         invoke();
         return fullscreenQuadResources;
      }
   }

   public static synchronized FullscreenQuad.FullscreenQuadResources resolve2() {
      if (flag) {
         return null;
      } else {
         invoke();
         return fullscreenQuadResources2;
      }
   }

   public static synchronized FullscreenQuad.FullscreenQuadResources resolve3() {
      if (flag) {
         return null;
      } else {
         invoke();
         return fullscreenQuadResources3;
      }
   }

   public static synchronized ShaderProgram resolve4() {
      if (flag) {
         return null;
      } else {
         invoke();
         return shaderProgram;
      }
   }

   public static synchronized boolean check(float f, float g, float h, float i, int j, int k, int l, int m, float n, float o, float p, float q, boolean bl) {
      if (!flag && !(h <= 1.0F) && !(i <= 1.0F) && !(q <= 0.001F) && check2()) {
         invoke();
         if (!flag && fullscreenQuadResources3 != null && shaderProgram != null) {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client != null && client.getWindow() != null) {
               int intValue = Math.max(1, client.getWindow().getFramebufferWidth());
               int intValue2 = Math.max(1, client.getWindow().getFramebufferHeight());
               FramebufferUtils.GlStateSnapshot glStateSnapshot = FramebufferUtils.captureGlState();
               boolean flag = false ;

               boolean flag2;
               label63: {
                  boolean flag3;
                   try {
                      flag = true;
                     GL11.glViewport(0, 0, Math.max(0, intValue), Math.max(0, intValue2));
                     GL11.glDisable(2929);
                     GL11.glDisable(2884);
                     GL11.glDepthMask(false);
                     GL11.glColorMask(true, true, true, true);
                     GlStateManager._enableBlend();
                     GL11.glEnable(3042);
                     GL14.glBlendFuncSeparate(770, 771, 1, 771);
                     GL11.glDisable(36281);
                     fullscreenQuadResources3.invoke();
                     fullscreenQuadResources3.invoke4("uViewport", intValue, intValue2);
                     fullscreenQuadResources3.invoke6("uRect", f, g, h, i);
                     fullscreenQuadResources3.invoke6("u_ElementRect", f, g, h, i);
                     fullscreenQuadResources3.invoke4("uRectSize", h, i);
                     fullscreenQuadResources3.invoke3("uCornerRadius", Math.max(0.0F, p));
                     fullscreenQuadResources3.invoke6("uCurrentColor", measure(j), measure2(j), measure3(j), measure4(j));
                     fullscreenQuadResources3.invoke6("uInitialColor", measure(k), measure2(k), measure3(k), measure4(k));
                     fullscreenQuadResources3.invoke5("uAccentTop", measure(l), measure2(l), measure3(l));
                     fullscreenQuadResources3.invoke5("uAccentBottom", measure(m), measure2(m), measure3(m));
                     fullscreenQuadResources3.invoke4("uMouse", n - f, o - g);
                     fullscreenQuadResources3.invoke3("uTime", (float)(System.currentTimeMillis() % 1000000L) / 1000.0F);
                     fullscreenQuadResources3.invoke3("uAlpha", Math.max(0.0F, Math.min(1.0F, q)));
                     fullscreenQuadResources3.invoke3("uLive", bl ? 1.0F : 0.0F);
                     shaderProgram.invoke();
                     flag2 = true;
                     flag = false;
                     break label63;
                   } catch (Throwable exception) {
                      invoke2();
                      flag = true;
                     flag3 = false;
                     flag = false;
                  } finally {
                     if (flag) {
                        GL20.glUseProgram(0);
                        FramebufferUtils.restoreGlState(glStateSnapshot);
                     }
                  }

                  GL20.glUseProgram(0);
                  FramebufferUtils.restoreGlState(glStateSnapshot);
                  return flag3;
               }

               GL20.glUseProgram(0);
               FramebufferUtils.restoreGlState(glStateSnapshot);
               return flag2;
            } else {
               return false;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private static void invoke() {
      if (fullscreenQuadResources == null || fullscreenQuadResources2 == null || fullscreenQuadResources3 == null || shaderProgram == null) {
         try {
            if (fullscreenQuad == null) {
               fullscreenQuad = new FullscreenQuad();
            }

            if (shaderProgram == null) {
               shaderProgram = new ShaderProgram();
            }

            if (fullscreenQuadResources == null) {
               fullscreenQuadResources = fullscreenQuad.resolve(
                  "cp_sb", "assets/wild/shaders/mainmenu/menu_quad.vert", "assets/wild/shaders/colorplus/sb_spectrum.frag"
               );
            }

            if (fullscreenQuadResources2 == null) {
               fullscreenQuadResources2 = fullscreenQuad.resolve("cp_hue", "assets/wild/shaders/mainmenu/menu_quad.vert", "assets/wild/shaders/colorplus/hue_strip.frag");
            }

            if (fullscreenQuadResources3 == null) {
               fullscreenQuadResources3 = fullscreenQuad.resolve(
                  "cp_preview", "assets/wild/shaders/mainmenu/menu_quad.vert", "assets/wild/shaders/colorplus/cp_preview.frag"
               );
            }
         } catch (Throwable exception2) {
            invoke2();
            flag = true;
         }
      }
   }

   public static synchronized void invoke2() {
      try {
         if (shaderProgram != null) {
            try {
               shaderProgram.close();
            } catch (Throwable exception3) {
            }

            shaderProgram = null;
         }

         if (fullscreenQuad != null) {
            try {
               fullscreenQuad.close();
            } catch (Throwable exception4) {
            }

            fullscreenQuad = null;
         }

         fullscreenQuadResources = null;
         fullscreenQuadResources2 = null;
         fullscreenQuadResources3 = null;
         flag = false;
      } catch (Throwable exception5) {
      }
   }

   private static boolean check2() {
      return RenderSystem.isOnRenderThread() && GLFW.glfwGetCurrentContext() != 0L;
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
}
