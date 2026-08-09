package ru.metaculture.protection;

import com.mojang.blaze3d.opengl.GlStateManager;
import java.lang.reflect.Method;
import java.nio.FloatBuffer;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.system.MemoryUtil;

public final class NeumorphismRenderer {
   private static final String ASSETS_WILD_SHADERS_MAINMENU_MENU_QUAD_VERT = "assets/wild/shaders/mainmenu/menu_quad.vert";
   private static final String ASSETS_WILD_SHADERS_ADVANCED_NEUMORPHISM_FRAG = "assets/wild/shaders/advanced_neumorphism.frag";
   private static final String ASSETS_WILD_SHADERS_ADVANCED_NEUMORPHISM_BATCH_VERT = "assets/wild/shaders/advanced_neumorphism_batch.vert";
   private static final String ASSETS_WILD_SHADERS_ADVANCED_NEUMORPHISM_BATCH_FRAG = "assets/wild/shaders/advanced_neumorphism_batch.frag";
   private static final int INT_VALUE = 128;
   private static final int INT_VALUE_2 = 7;
   private static final int INT_VALUE_3 = 3;
   private static final ThemePalette THEME_PALETTE = ThemePalette.resolve2();
   private static Boolean booleanValue;
   private static Method method;
   private static Method method2;
   private static GlShaderProgram glShaderProgram;
   private static GlShaderProgram glShaderProgram2;
   private static int intValue;
   private static int intValue2;
   private static int intValue3;
   private static int intValue4;
   private static final NeumorphismRenderer.NeumorphismRendererState[] NEUMORPHISM_RENDERER_STATES = resolve12();
   private static final FloatBuffer FLOAT_BUFFER = MemoryUtil.memAllocFloat(3584);
   private static String text = "";

   private NeumorphismRenderer() {
   }

   public static void invoke() {
      if (intValue3++ == 0) {
         intValue4 = 0;
         RenderManager renderManager = WildClient.resolve();
         if (renderManager != null) {
            try {
               renderManager.invoke20();
            } catch (Throwable exception) {
            }
         }
      }
   }

   public static void invoke2() {
      if (intValue4 > 0) {
         RenderManager renderManager2 = WildClient.resolve();
         if (renderManager2 != null) {
            try {
               renderManager2.invoke20();
            } catch (Throwable exception2) {
            }
         }

         invoke7();
         intValue4 = 0;
      }
   }

   public static void invoke3() {
      if (intValue3 <= 0) {
         intValue3 = 0;
      } else {
         boolean flag = false ;

         try {
            flag = true;
            invoke2();
            flag = false;
         } finally {
            if (flag) {
               intValue3--;
               if (intValue3 == 0) {
                  intValue4 = 0;
               }
            }
         }

         intValue3--;
         if (intValue3 == 0) {
            intValue4 = 0;
         }
      }
   }

   public static boolean check(ShaderSurface shaderSurface) {
      return ShaderPresetRegistry.getINSTANCE().check(shaderSurface);
   }

   public static boolean check2(
      ShaderSurface shaderSurface2, float f, float g, float h, float i, int j, int k, float l, float m, ColorScheme colorScheme, float n
   ) {
      return !check(shaderSurface2) ? false : ShaderUniformBinder.check(shaderSurface2, f, g, h, i, j, k, l, m, colorScheme, n);
   }

   public static String resolve(ShaderSurface shaderSurface3) {
      return ThemeShaderProgramCache.getINSTANCE().resolve6(shaderSurface3);
   }

   public static String resolve2(ShaderSurface shaderSurface4) {
      return ThemeShaderProgramCache.getINSTANCE().resolve4(shaderSurface4);
   }

   public static boolean check3(String string, float f, float g, float h, float i, int j, int k, float l, float m, ColorScheme colorScheme2, float n) {
      return ShaderPresetRegistry.getINSTANCE().check2(string)
         ? ShaderUniformBinder.check2(string, f, g, h, i, j, k, l, m, colorScheme2, n)
         : ShaderEffectRenderer.getINSTANCE().check(string, f, g, h, i, j, k, l, m, colorScheme2, n);
   }

   public static boolean check4(MatrixStack matrixStack, float f, float g, float h, float i, float j) {
      return check5(matrixStack, f, g, h, i, j, 1.0F);
   }

   public static boolean check5(MatrixStack matrixStack, float f, float g, float h, float i, float j, float k) {
      String text = HudModule.resolve3();
      return check14(matrixStack, text, f, g, h, i, j, k);
   }

   public static boolean check6(MatrixStack matrixStack, String string, float f, float g, float h, float i, float j) {
      return check14(matrixStack, string, f, g, h, i, j, 1.0F);
   }

   public static boolean check7(MatrixStack matrixStack, float f, float g, float h, float i, float j, boolean bl) {
      return check8(matrixStack, f, g, h, i, j, bl, 1.0F);
   }

   public static boolean check8(MatrixStack matrixStack, float f, float g, float h, float i, float j, boolean bl, float k) {
      return check9(matrixStack, f, g, h, i, j, bl, k, resolve9());
   }

   public static boolean check9(MatrixStack matrixStack, float f, float g, float h, float i, float j, boolean bl, float k, NeumorphismRenderer.NeumorphismRendererData2 neumorphismRendererData2) {
      NeumorphismRenderer.NeumorphismRendererData2 neumorphismRendererData22 = neumorphismRendererData2 == null ? resolve9() : neumorphismRendererData2;
      return check12(matrixStack, f, g, h, i, j, neumorphismRendererData22.distance(), neumorphismRendererData22.blur(), neumorphismRendererData22.intensity(), neumorphismRendererData22.shape(), bl, k);
   }

   public static boolean check10(MatrixStack matrixStack, float f, float g, float h, float i, float j, float k, float l, float m, int n, boolean bl) {
      return check11(matrixStack, f, g, h, i, j, k, l, m, n, bl, 1.0F);
   }

   public static boolean check11(MatrixStack matrixStack, float f, float g, float h, float i, float j, float k, float l, float m, int n, boolean bl, float o) {
      return check12(matrixStack, f, g, h, i, j, k, l, m, n, bl, o);
   }

   public static void invoke4(MatrixStack matrixStack, float f, float g, float h, float i, float j, float k, float l, float m, int n, boolean bl) {
      check12(matrixStack, f, g, h, i, j, k, l, m, n, bl, 1.0F);
   }

   private static boolean check12(
      MatrixStack matrixStack, float f, float g, float h, float i, float j, float k, float l, float m, int n, boolean bl, float o
   ) {
      MinecraftClient client = MinecraftClient.getInstance();
      if (client != null && client.getWindow() != null && !(h <= 1.0F) && !(i <= 1.0F) && !(o <= 0.001F)) {
         int intValue = client.getWindow().getFramebufferWidth();
         int intValue2 = client.getWindow().getFramebufferHeight();
         if (intValue > 0 && intValue2 > 0) {
            RenderManager renderManager3 = WildClient.resolve();
            if (intValue3 <= 0 && renderManager3 != null) {
               try {
                  renderManager3.invoke20();
               } catch (Throwable exception3) {
               }
            }

            NeumorphismRenderer.NeumorphismRendererData neumorphismRendererData = resolve10(renderManager3, matrixStack, f, g, h, i);
            float floatValue = neumorphismRendererData.maxX - neumorphismRendererData.minX;
            float floatValue2 = neumorphismRendererData.maxY - neumorphismRendererData.minY;
            if (!(floatValue <= 1.0F) && !(floatValue2 <= 1.0F)) {
               NeumorphismRenderer.NeumorphismRendererData2 neumorphismRendererData23 = new NeumorphismRenderer.NeumorphismRendererData2(k, l, m, n);
               float floatValue3 = Math.min(floatValue / Math.max(h, 1.0F), floatValue2 / Math.max(i, 1.0F));
               float floatValue4 = Math.max(0.0F, j * floatValue3);
               float floatValue5 = Math.max(0.5F, neumorphismRendererData23.distance() * floatValue3);
               float floatValue6 = Math.max(1.0F, neumorphismRendererData23.blur() * floatValue3);
               NeumorphismRenderer.NeumorphismRendererData2 neumorphismRendererData24 = new NeumorphismRenderer.NeumorphismRendererData2(floatValue5, floatValue6, neumorphismRendererData23.intensity(), neumorphismRendererData23.shape());
               float floatValue7 = bl ? Math.max(2.0F, Math.min(18.0F, floatValue5 + floatValue6 * 0.32F)) : Math.max(6.0F, Math.min(96.0F, floatValue5 + floatValue6 * 1.35F));
               float floatValue8 = neumorphismRendererData.minX - floatValue7;
               float floatValue9 = neumorphismRendererData.minY - floatValue7;
               float floatValue10 = floatValue + floatValue7 * 2.0F;
               float floatValue11 = floatValue2 + floatValue7 * 2.0F;
               ThemePalette.BaseColors baseColors = ThemePalette.resolve(check13());
               if (intValue3 <= 0) {
                  GlShaderProgram glShaderProgram = resolve7();
                  return glShaderProgram == null
                     ? false
                     : check18(
                        glShaderProgram, floatValue8, floatValue9, floatValue10, floatValue11, neumorphismRendererData.minX, neumorphismRendererData.minY, floatValue, floatValue2, floatValue4, intValue, intValue2, baseColors, bl, Math.min(1.0F, o), neumorphismRendererData24
                     );
               } else if (resolve8() == null) {
                  return false;
               } else {
                  invoke6(floatValue8, floatValue9, floatValue10, floatValue11, neumorphismRendererData.minX, neumorphismRendererData.minY, floatValue, floatValue2, floatValue4, intValue, intValue2, baseColors, bl, Math.min(1.0F, o), neumorphismRendererData24);
                  return true;
               }
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

   public static NeumorphismRenderer.NeumorphismRendererData2 resolve3(float f, float g, float h, String string) {
      return new NeumorphismRenderer.NeumorphismRendererData2(f, g, h, compute4(string));
   }

   public static boolean check13() {
      return THEME_PALETTE.check(resolve5());
   }

   public static int compute(float f) {
      return compute5(ThemePalette.resolve(check13()).baseColor(), f);
   }

   public static int compute2(float f) {
      return compute5(check13() ? -14670285 : -591617, f);
   }

   public static int compute3(float f) {
      return compute5(check13() ? -10194811 : -5524281, f);
   }

   public static boolean check14(MatrixStack matrixStack, String string, float f, float g, float h, float i, float j, float k) {
      String text2 = string == null ? "" : string.trim();
      if (text2.isBlank()) {
         return false;
      } else {
         MinecraftClient client2 = MinecraftClient.getInstance();
         if (client2 != null && client2.getWindow() != null && !(h <= 1.0F) && !(i <= 1.0F) && !(k <= 0.001F)) {
            int intValue3 = client2.getWindow().getFramebufferWidth();
            int intValue4 = client2.getWindow().getFramebufferHeight();
            if (intValue3 > 0 && intValue4 > 0) {
               ShaderSurface shaderSurface5 = resolve6(text2);
               if (shaderSurface5 != ShaderSurface.HUD) {
                  return false;
               } else {
                  GlShaderProgram glShaderProgram2 = ShaderEffects.resolve5(text2);
                  ShaderBuildResult shaderBuildResult = ShaderPresetRegistry.getINSTANCE().resolve2(text2);
                  if (glShaderProgram2 != null && shaderBuildResult != null) {
                     RenderManager renderManager4 = WildClient.resolve();
                     if (renderManager4 != null) {
                        try {
                           renderManager4.invoke20();
                        } catch (Throwable exception4) {
                        }
                     }

                     NeumorphismRenderer.NeumorphismRendererData neumorphismRendererData3 = resolve10(renderManager4, matrixStack, f, g, h, i);
                     float floatValue12 = neumorphismRendererData3.maxX - neumorphismRendererData3.minX;
                     float floatValue13 = neumorphismRendererData3.maxY - neumorphismRendererData3.minY;
                     if (!(floatValue12 <= 1.0F) && !(floatValue13 <= 1.0F)) {
                        float floatValue14 = Math.min(floatValue12 / Math.max(h, 1.0F), floatValue13 / Math.max(i, 1.0F));
                        float floatValue15 = Math.max(0.0F, j * floatValue14);
                        float floatValue16 = HudEditorRenderer.getINSTANCE().getFloatValue();
                        float floatValue17 = HudEditorRenderer.getINSTANCE().getFloatValue2();
                        ColorScheme colorScheme3 = resolve4();
                        return check19(
                           glShaderProgram2,
                           shaderBuildResult,
                           ShaderPresetRegistry.getINSTANCE().resolve13(text2),
                           neumorphismRendererData3.minX,
                           neumorphismRendererData3.minY,
                           floatValue12,
                           floatValue13,
                           neumorphismRendererData3.minX,
                           neumorphismRendererData3.minY,
                           floatValue12,
                           floatValue13,
                           floatValue15,
                           intValue3,
                           intValue4,
                           floatValue16,
                           floatValue17,
                           colorScheme3,
                           Math.min(1.0F, k)
                        );
                     } else {
                        return false;
                     }
                  } else {
                     return false;
                  }
               }
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   public static boolean check15(
      MatrixStack matrixStack,
      ShaderSurface shaderSurface6,
      float f,
      float g,
      float h,
      float i,
      float j,
      int k,
      int l,
      float m,
      float n,
      ColorScheme colorScheme4,
      float o
   ) {
      if (shaderSurface6 != null && shaderSurface6.resolve() == ShaderSurface.HUD && !(h <= 1.0F) && !(i <= 1.0F) && k > 0 && l > 0 && !(o <= 0.001F)) {
         RenderManager renderManager5 = WildClient.resolve();
         if (renderManager5 != null) {
            try {
               renderManager5.invoke20();
            } catch (Throwable exception5) {
            }
         }

         NeumorphismRenderer.NeumorphismRendererData neumorphismRendererData4 = resolve10(renderManager5, matrixStack, f, g, h, i);
         float floatValue18 = neumorphismRendererData4.maxX - neumorphismRendererData4.minX;
         float floatValue19 = neumorphismRendererData4.maxY - neumorphismRendererData4.minY;
         if (!(floatValue18 <= 1.0F) && !(floatValue19 <= 1.0F)) {
            float floatValue20 = Math.min(floatValue18 / Math.max(h, 1.0F), floatValue19 / Math.max(i, 1.0F));
            float floatValue21 = Math.max(0.0F, j * floatValue20);
            float floatValue22 = Math.max(12.0F, Math.min(64.0F, Math.min(floatValue18, floatValue19) * 0.38F));
            float floatValue23 = neumorphismRendererData4.minX - floatValue22;
            float floatValue24 = neumorphismRendererData4.minY - floatValue22;
            float floatValue25 = floatValue18 + floatValue22 * 2.0F;
            float floatValue26 = floatValue19 + floatValue22 * 2.0F;
            ColorScheme colorScheme5 = colorScheme4 == null ? resolve4() : colorScheme4;
            return ShaderUniformBinder.check6(
               shaderSurface6, floatValue23, floatValue24, floatValue25, floatValue26, neumorphismRendererData4.minX, neumorphismRendererData4.minY, floatValue18, floatValue19, floatValue21, k, l, m, n, colorScheme5, Math.min(1.0F, o)
            );
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public static boolean check16(String string, int i, float f, float g, float h, float j, int k, int l, float m, float n, ColorScheme colorScheme6, float o) {
      return !ShaderPresetRegistry.getINSTANCE().check2(string) ? false : ShaderUniformBinder.check3(string, i, f, g, h, j, k, l, m, n, colorScheme6, o);
   }

   public static void invoke5(String string) {
      ShaderEffectRenderer.getINSTANCE().invoke2(string);
   }

   private static ColorScheme resolve4() {
      Theme theme = resolve5();
      return ColorScheme.resolve2(theme, THEME_PALETTE.check(theme));
   }

   private static Theme resolve5() {
      return WildClient.INSTANCE != null && WildClient.INSTANCE.themeManager != null ? WildClient.INSTANCE.themeManager.getTheme() : Theme.WILD;
   }

   private static boolean check17(String string) {
      return resolve6(string) == ShaderSurface.HUD;
   }

   private static ShaderSurface resolve6(String string) {
      ShaderNode shaderNode = ShaderPresetRegistry.getINSTANCE().resolve4(string);
      return shaderNode == null ? ShaderSurface.PREVIEW_ONLY : ShaderSurface.resolve4(shaderNode.getPreview()).resolve();
   }

   private static synchronized GlShaderProgram resolve7() {
      if (glShaderProgram != null) {
         return glShaderProgram;
      } else {
         try {
            glShaderProgram = GlShaderProgram.resolve("assets/wild/shaders/mainmenu/menu_quad.vert", "assets/wild/shaders/advanced_neumorphism.frag");
            text = "";
            return glShaderProgram;
         } catch (Throwable exception6) {
            text = exception6.getMessage() == null ? exception6.getClass().getSimpleName() : exception6.getMessage();
            glShaderProgram = null;
            RenderDiagnosticsTracker.getInstance().fail("ThemeShaderApply.acquireNeumorphicProgram", exception6);
            throw new IllegalStateException("unreachable shader failure", exception6);
         }
      }
   }

   private static synchronized GlShaderProgram resolve8() {
      if (glShaderProgram2 != null) {
         return glShaderProgram2;
      } else {
         try {
            glShaderProgram2 = GlShaderProgram.resolve("assets/wild/shaders/advanced_neumorphism_batch.vert", "assets/wild/shaders/advanced_neumorphism_batch.frag");
            int intValue5 = GL31.glGetUniformBlockIndex(glShaderProgram2.getIntValue(), "NeumorphicPlateBlock");
            if (intValue5 >= 0) {
               GL31.glUniformBlockBinding(glShaderProgram2.getIntValue(), intValue5, 3);
            }

            if (intValue == 0) {
               intValue = GL30.glGenVertexArrays();
            }

            if (intValue2 == 0) {
               intValue2 = GL15.glGenBuffers();
               GL15.glBindBuffer(35345, intValue2);
               GL15.glBufferData(35345, FLOAT_BUFFER.capacity() * 4L, 35040);
               GL15.glBindBuffer(35345, 0);
            }

            text = "";
            return glShaderProgram2;
         } catch (Throwable exception7) {
            text = exception7.getMessage() == null ? exception7.getClass().getSimpleName() : exception7.getMessage();
            glShaderProgram2 = null;
            RenderDiagnosticsTracker.getInstance().fail("ThemeShaderApply.acquireNeumorphicBatchProgram", exception7);
            throw new IllegalStateException("unreachable shader failure", exception7);
         }
      }
   }

   private static void invoke6(
      float f,
      float g,
      float h,
      float i,
      float j,
      float k,
      float l,
      float m,
      float n,
      int o,
      int p,
      ThemePalette.BaseColors baseColors2,
      boolean bl,
      float q,
      NeumorphismRenderer.NeumorphismRendererData2 neumorphismRendererData25
   ) {
      if (intValue4 >= 128) {
         invoke2();
      }

      NeumorphismRenderer.NeumorphismRendererState neumorphismRendererState = NEUMORPHISM_RENDERER_STATES[intValue4++];
      neumorphismRendererState.invoke(
         f,
         g,
         h,
         i,
         j,
         k,
         l,
         m,
         n,
         neumorphismRendererData25.distance(),
         neumorphismRendererData25.blur(),
         neumorphismRendererData25.intensity(),
         neumorphismRendererData25.shape(),
         bl ? 1 : 0,
         o,
         p,
         baseColors2.baseColor(),
         baseColors2.darkShadowColor(),
         baseColors2.lightShadowColor(),
         q
      );
   }

   private static void invoke7() {
      GlShaderProgram glShaderProgram3 = resolve8();
      if (glShaderProgram3 != null && intValue4 > 0) {
         int intValue6 = 0;
         int intValue7 = 0;

         for (int intValue8 = 0; intValue8 < intValue4; intValue8++) {
            intValue6 = Math.max(intValue6, NEUMORPHISM_RENDERER_STATES[intValue8].intValue);
            intValue7 = Math.max(intValue7, NEUMORPHISM_RENDERER_STATES[intValue8].intValue2);
         }

         if (intValue6 > 0 && intValue7 > 0) {
            invoke8();
            FramebufferUtils.GlStateSnapshot glStateSnapshot = FramebufferUtils.captureGlState();
            boolean flag2 = false ;

            label90: {
               try {
                  flag2 = true;
                  GL11.glViewport(0, 0, Math.max(0, intValue6), Math.max(0, intValue7));
                  GL11.glDisable(2929);
                  GL11.glDisable(2884);
                  GL11.glDepthMask(false);
                  GlStateManager._enableBlend();
                  GL11.glEnable(3042);
                  GL14.glBlendFuncSeparate(770, 771, 1, 771);
                  GL11.glDisable(36281);
                  glShaderProgram3.invoke();
                  invoke12(glShaderProgram3, "uViewport", intValue6, intValue7);
                  invoke12(glShaderProgram3, "u_LightDirection", -1.0F, -1.0F);
                  GL15.glBindBuffer(35345, intValue2);
                  GL15.glBufferSubData(35345, 0L, FLOAT_BUFFER);
                  GL30.glBindBufferBase(35345, 3, intValue2);
                  GL30.glBindVertexArray(intValue);
                  GL31.glDrawArraysInstanced(4, 0, 6, intValue4);
                  GL30.glBindVertexArray(0);
                  GL30.glBindBufferBase(35345, 3, 0);
                  GL15.glBindBuffer(35345, 0);
                  flag2 = false;
                  break label90;
               } catch (Throwable exception8) {
                  text = exception8.getMessage() == null ? exception8.getClass().getSimpleName() : exception8.getMessage();
                  RenderDiagnosticsTracker.getInstance().fail("ThemeShaderApply.drawNeumorphicBatch", exception8);
                  flag2 = false;
               } finally {
                  if (flag2) {
                     GL20.glUseProgram(0);
                     FramebufferUtils.restoreGlState(glStateSnapshot);
                     invoke16();
                  }
               }

               GL20.glUseProgram(0);
               FramebufferUtils.restoreGlState(glStateSnapshot);
               invoke16();
               return;
            }

            GL20.glUseProgram(0);
            FramebufferUtils.restoreGlState(glStateSnapshot);
            invoke16();
         }
      }
   }

   private static void invoke8() {
      FLOAT_BUFFER.clear();
      short shortValue = 128;
      byte byteValue = 0;
      int intValue9 = shortValue * 4;
      int intValue10 = shortValue * 8;
      int intValue11 = shortValue * 12;
      int intValue12 = shortValue * 16;
      int intValue13 = shortValue * 20;
      int intValue14 = shortValue * 24;

      for (int intValue15 = 0; intValue15 < intValue4; intValue15++) {
         NeumorphismRenderer.NeumorphismRendererState neumorphismRendererState2 = NEUMORPHISM_RENDERER_STATES[intValue15];
         invoke9(byteValue + intValue15 * 4, neumorphismRendererState2.floatValue, neumorphismRendererState2.floatValue2, neumorphismRendererState2.floatValue3, neumorphismRendererState2.floatValue4);
         invoke9(intValue9 + intValue15 * 4, neumorphismRendererState2.floatValue5, neumorphismRendererState2.floatValue6, neumorphismRendererState2.floatValue7, neumorphismRendererState2.floatValue8);
         invoke9(intValue10 + intValue15 * 4, neumorphismRendererState2.floatValue9, neumorphismRendererState2.floatValue10, neumorphismRendererState2.floatValue11, neumorphismRendererState2.floatValue12);
         invoke9(intValue11 + intValue15 * 4, measure(neumorphismRendererState2.intValue3), measure2(neumorphismRendererState2.intValue3), measure3(neumorphismRendererState2.intValue3), neumorphismRendererState2.floatValue15);
         invoke9(intValue12 + intValue15 * 4, measure(neumorphismRendererState2.intValue4), measure2(neumorphismRendererState2.intValue4), measure3(neumorphismRendererState2.intValue4), measure4(neumorphismRendererState2.intValue4));
         invoke9(
            intValue13 + intValue15 * 4, measure(neumorphismRendererState2.intValue5), measure2(neumorphismRendererState2.intValue5), measure3(neumorphismRendererState2.intValue5), measure4(neumorphismRendererState2.intValue5)
         );
         invoke9(intValue14 + intValue15 * 4, neumorphismRendererState2.floatValue13, neumorphismRendererState2.floatValue14, 0.0F, 0.0F);
      }

      FLOAT_BUFFER.position(0);
      FLOAT_BUFFER.limit(FLOAT_BUFFER.capacity());
   }

   private static void invoke9(int i, float f, float g, float h, float j) {
      FLOAT_BUFFER.put(i, f);
      FLOAT_BUFFER.put(i + 1, g);
      FLOAT_BUFFER.put(i + 2, h);
      FLOAT_BUFFER.put(i + 3, j);
   }

   private static boolean check18(
      GlShaderProgram glShaderProgram4,
      float f,
      float g,
      float h,
      float i,
      float j,
      float k,
      float l,
      float m,
      float n,
      int o,
      int p,
      ThemePalette.BaseColors baseColors3,
      boolean bl,
      float q,
      NeumorphismRenderer.NeumorphismRendererData2 neumorphismRendererData26
   ) {
      ShaderProgram shaderProgram = ThemeShaderProgramCache.getINSTANCE().resolve();
      if (glShaderProgram4 != null && shaderProgram != null && baseColors3 != null) {
         FramebufferUtils.GlStateSnapshot glStateSnapshot2 = FramebufferUtils.captureGlState();

         boolean flag3;
         try {
            GL11.glViewport(0, 0, Math.max(0, o), Math.max(0, p));
            GL11.glDisable(2929);
            GL11.glDisable(2884);
            GL11.glDepthMask(false);
            GlStateManager._enableBlend();
            GL11.glEnable(3042);
            GL14.glBlendFuncSeparate(770, 771, 1, 771);
            GL11.glDisable(36281);
            glShaderProgram4.invoke();
            invoke12(glShaderProgram4, "uViewport", o, p);
            invoke14(glShaderProgram4, "uRect", f, g, h, i);
            invoke14(glShaderProgram4, "u_ElementRect", j, k, l, m);
            invoke12(glShaderProgram4, "u_Resolution", Math.max(1.0F, (float)o), Math.max(1.0F, (float)p));
            invoke10(glShaderProgram4, "u_Radius", Math.max(0.0F, n));
            invoke10(glShaderProgram4, "u_ElementRadius", Math.max(0.0F, n));
            invoke13(glShaderProgram4, "u_BaseColor", measure(baseColors3.baseColor()), measure2(baseColors3.baseColor()), measure3(baseColors3.baseColor()));
            invoke13(
               glShaderProgram4,
               "u_LightShadowColor",
               measure(baseColors3.lightShadowColor()),
               measure2(baseColors3.lightShadowColor()),
               measure3(baseColors3.lightShadowColor())
            );
            invoke13(
               glShaderProgram4,
               "u_DarkShadowColor",
               measure(baseColors3.darkShadowColor()),
               measure2(baseColors3.darkShadowColor()),
               measure3(baseColors3.darkShadowColor())
            );
            invoke10(glShaderProgram4, "u_LightShadowAlpha", measure4(baseColors3.lightShadowColor()));
            invoke10(glShaderProgram4, "u_DarkShadowAlpha", measure4(baseColors3.darkShadowColor()));
            invoke10(glShaderProgram4, "u_Alpha", q);
            invoke11(glShaderProgram4, "u_Inset", bl ? 1 : 0);
            invoke10(glShaderProgram4, "u_Distance", neumorphismRendererData26.distance());
            invoke10(glShaderProgram4, "u_Blur", neumorphismRendererData26.blur());
            invoke10(glShaderProgram4, "u_Intensity", neumorphismRendererData26.intensity());
            invoke11(glShaderProgram4, "u_ShapeType", neumorphismRendererData26.shape());
            invoke11(glShaderProgram4, "u_Shape", neumorphismRendererData26.shape());
            invoke12(glShaderProgram4, "u_LightDirection", -1.0F, -1.0F);
            shaderProgram.invoke();
            flag3 = true;
         } catch (Throwable exception9) {
            text = exception9.getMessage() == null ? exception9.getClass().getSimpleName() : exception9.getMessage();
            RenderDiagnosticsTracker.getInstance().fail("ThemeShaderApply.drawNeumorphicProgram", exception9);
            throw new IllegalStateException("unreachable shader failure", exception9);
         } finally {
            GL20.glUseProgram(0);
            FramebufferUtils.restoreGlState(glStateSnapshot2);
            invoke16();
         }

         return flag3;
      } else {
         return false;
      }
   }

   private static NeumorphismRenderer.NeumorphismRendererData2 resolve9() {
      try {
         HudEditorRenderer.HudEditorRendererVariant hudEditorRendererVariant = HudEditorRenderer.getINSTANCE().hudEditorRendererVariant;
         return resolve3(
            hudEditorRendererVariant.neoDistantsiya.getValue(), hudEditorRendererVariant.neoRazmytie.getValue(), hudEditorRendererVariant.neoIntensivnost.getValue(), hudEditorRendererVariant.neoForma.getValue()
         );
      } catch (Throwable exception10) {
         return new NeumorphismRenderer.NeumorphismRendererData2(5.5F, 18.0F, 0.72F, 1);
      }
   }

   private static int compute4(String string) {
      if ("Вогнутая".equals(string)) {
         return 2;
      } else {
         return "Выпуклая".equals(string) ? 1 : 0;
      }
   }

   private static boolean check19(
      GlShaderProgram glShaderProgram5,
      ShaderBuildResult shaderBuildResult2,
      Map<String, float[]> map,
      float f,
      float g,
      float h,
      float i,
      float j,
      float k,
      float l,
      float m,
      float n,
      int o,
      int p,
      float q,
      float r,
      ColorScheme colorScheme7,
      float s
   ) {
      ShaderProgram shaderProgram2 = ThemeShaderProgramCache.getINSTANCE().resolve();
      if (glShaderProgram5 != null && shaderBuildResult2 != null && shaderProgram2 != null) {
         FramebufferUtils.GlStateSnapshot glStateSnapshot3 = FramebufferUtils.captureGlState();

         boolean flag4;
         try {
            GL11.glViewport(0, 0, Math.max(0, o), Math.max(0, p));
            GL11.glDisable(2929);
            GL11.glDisable(2884);
            GL11.glDepthMask(false);
            GlStateManager._enableBlend();
            GL11.glEnable(3042);
            GL14.glBlendFuncSeparate(770, 771, 1, 771);
            GL11.glDisable(36281);
            glShaderProgram5.invoke();
            GL13.glActiveTexture(33984);
            GL11.glBindTexture(3553, ThemeShaderProgramCache.getINSTANCE().compute());
            invoke11(glShaderProgram5, "u_DiffuseMap", 0);
            invoke12(glShaderProgram5, "uViewport", o, p);
            invoke14(glShaderProgram5, "uRect", f, g, h, i);
            invoke14(glShaderProgram5, "u_ElementRect", j, k, l, m);
            invoke10(glShaderProgram5, "u_ElementRadius", Math.max(0.0F, n));
            invoke12(glShaderProgram5, "u_GlobalUV", j / Math.max(1.0F, (float)o), k / Math.max(1.0F, (float)p));
            invoke12(glShaderProgram5, "u_Resolution", Math.max(1.0F, (float)o), Math.max(1.0F, (float)p));
            invoke10(glShaderProgram5, "u_Time", ThemeShaderProgramCache.getINSTANCE().measure());
            invoke12(glShaderProgram5, "u_Mouse", q - j, r - k);
            int intValue16 = colorScheme7 == null ? -1 : colorScheme7.getIntValue14();
            int intValue17 = colorScheme7 == null ? -16777216 : colorScheme7.getIntValue15();
            int intValue18 = colorScheme7 == null ? -15724520 : colorScheme7.getIntValue();
            int intValue19 = colorScheme7 == null ? -14671832 : colorScheme7.getIntValue2();
            invoke13(glShaderProgram5, "u_AccentTop", measure(intValue16), measure2(intValue16), measure3(intValue16));
            invoke13(glShaderProgram5, "u_AccentBottom", measure(intValue17), measure2(intValue17), measure3(intValue17));
            invoke14(glShaderProgram5, "u_ThemeColors[0]", measure(intValue18), measure2(intValue18), measure3(intValue18), measure4(intValue18));
            invoke14(glShaderProgram5, "u_ThemeColors[1]", measure(intValue19), measure2(intValue19), measure3(intValue19), measure4(intValue19));
            invoke14(glShaderProgram5, "u_ThemeColors[2]", measure(intValue16), measure2(intValue16), measure3(intValue16), s);
            invoke14(glShaderProgram5, "u_ThemeColors[3]", measure(intValue17), measure2(intValue17), measure3(intValue17), s);
            invoke10(glShaderProgram5, "u_Alpha", s);
            invoke15(glShaderProgram5, shaderBuildResult2, map);
            shaderProgram2.invoke();
            flag4 = true;
         } catch (Throwable exception11) {
            RenderDiagnosticsTracker.getInstance().fail("ThemeShaderApply.drawHudProgram", exception11);
            throw new IllegalStateException("unreachable shader failure", exception11);
         } finally {
            GL13.glActiveTexture(33984);
            GL11.glBindTexture(3553, 0);
            FramebufferUtils.restoreGlState(glStateSnapshot3);
            invoke16();
         }

         return flag4;
      } else {
         return false;
      }
   }

   private static void invoke10(GlShaderProgram glShaderProgram6, String string, float f) {
      int intValue20 = glShaderProgram6.compute2(string);
      if (intValue20 >= 0) {
         GL20.glUniform1f(intValue20, f);
      }
   }

   private static void invoke11(GlShaderProgram glShaderProgram7, String string, int i) {
      int intValue21 = glShaderProgram7.compute2(string);
      if (intValue21 >= 0) {
         GL20.glUniform1i(intValue21, i);
      }
   }

   private static void invoke12(GlShaderProgram glShaderProgram8, String string, float f, float g) {
      int intValue22 = glShaderProgram8.compute2(string);
      if (intValue22 >= 0) {
         GL20.glUniform2f(intValue22, f, g);
      }
   }

   private static void invoke13(GlShaderProgram glShaderProgram9, String string, float f, float g, float h) {
      int intValue23 = glShaderProgram9.compute2(string);
      if (intValue23 >= 0) {
         GL20.glUniform3f(intValue23, f, g, h);
      }
   }

   private static void invoke14(GlShaderProgram glShaderProgram10, String string, float f, float g, float h, float i) {
      int intValue24 = glShaderProgram10.compute2(string);
      if (intValue24 >= 0) {
         GL20.glUniform4f(intValue24, f, g, h, i);
      }
   }

   private static void invoke15(GlShaderProgram glShaderProgram11, ShaderBuildResult shaderBuildResult3, Map<String, float[]> map) {
      if (glShaderProgram11 != null && shaderBuildResult3 != null && !shaderBuildResult3.exposedUniforms().isEmpty()) {
         for (ShaderUniformSpec shaderUniformSpec : shaderBuildResult3.exposedUniforms()) {
            float[] floatValues = map == null ? null : (float[])map.get(shaderUniformSpec.uniformName());
            if (floatValues == null || floatValues.length == 0) {
               floatValues = shaderUniformSpec.defaults();
            }

            if (shaderUniformSpec.kind() == ShaderUniformSpec.ShaderUniformSpecState.FLOAT) {
               invoke10(glShaderProgram11, shaderUniformSpec.uniformName(), floatValues[0]);
            } else {
               float floatValue27 = floatValues.length > 0 ? floatValues[0] : 0.0F;
               float floatValue28 = floatValues.length > 1 ? floatValues[1] : 0.0F;
               float floatValue29 = floatValues.length > 2 ? floatValues[2] : 0.0F;
               float floatValue30 = floatValues.length > 3 ? floatValues[3] : 1.0F;
               invoke14(glShaderProgram11, shaderUniformSpec.uniformName(), floatValue27, floatValue28, floatValue29, floatValue30);
            }
         }
      }
   }

   private static void invoke16() {
      GL20.glUseProgram(0);
      if (!Boolean.FALSE.equals(booleanValue)) {
         try {
            if (booleanValue == null) {
               Class type = Class.forName("com.mojang.blaze3d.systems.RenderSystem");
               Class type2 = Class.forName("net.minecraft.client.render.GameRenderer");
               method = type.getMethod("setShader", Supplier.class);
               method2 = type2.getMethod("getPositionColorProgram");
               booleanValue = true;
            }

            Supplier supplier = () -> {
               try {
                  return method2.invoke(null);
               } catch (Throwable var1x) {
                  return null;
               }
            };
            method.invoke(null, supplier);
         } catch (Throwable exception12) {
            booleanValue = false;
         }
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

   private static int compute5(int i, float f) {
      int intValue25 = Math.max(0, Math.min(255, Math.round(f * 255.0F)));
      return i & 16777215 | intValue25 << 24;
   }

   private static NeumorphismRenderer.NeumorphismRendererData resolve10(RenderManager renderManager6, MatrixStack matrixStack, float f, float g, float h, float i) {
      float[] floatValues2 = renderManager6 == null ? null : renderManager6.getMatrix3Stack().resolve2();
      Matrix4f matrix4f2 = matrixStack == null ? null : new Matrix4f(matrixStack.peek().getPositionMatrix());
      float floatValue31 = f + h;
      float floatValue32 = g + i;
      NeumorphismRenderer.NeumorphismRendererData3 neumorphismRendererData32 = resolve11(floatValues2, matrix4f2, f, g);
      NeumorphismRenderer.NeumorphismRendererData3 neumorphismRendererData33 = resolve11(floatValues2, matrix4f2, floatValue31, g);
      NeumorphismRenderer.NeumorphismRendererData3 neumorphismRendererData34 = resolve11(floatValues2, matrix4f2, floatValue31, floatValue32);
      NeumorphismRenderer.NeumorphismRendererData3 neumorphismRendererData35 = resolve11(floatValues2, matrix4f2, f, floatValue32);
      float floatValue33 = Math.min(Math.min(neumorphismRendererData32.x, neumorphismRendererData33.x), Math.min(neumorphismRendererData34.x, neumorphismRendererData35.x));
      float floatValue34 = Math.min(Math.min(neumorphismRendererData32.y, neumorphismRendererData33.y), Math.min(neumorphismRendererData34.y, neumorphismRendererData35.y));
      float floatValue35 = Math.max(Math.max(neumorphismRendererData32.x, neumorphismRendererData33.x), Math.max(neumorphismRendererData34.x, neumorphismRendererData35.x));
      float floatValue36 = Math.max(Math.max(neumorphismRendererData32.y, neumorphismRendererData33.y), Math.max(neumorphismRendererData34.y, neumorphismRendererData35.y));
      return new NeumorphismRenderer.NeumorphismRendererData(floatValue33, floatValue34, floatValue35, floatValue36);
   }

   private static NeumorphismRenderer.NeumorphismRendererData3 resolve11(float[] fs, Matrix4f matrix4f, float f, float g) {
      float floatValue37 = fs != null && fs.length >= 6 ? fs[0] * f + fs[1] * g + fs[2] : f;
      float floatValue38 = fs != null && fs.length >= 6 ? fs[3] * f + fs[4] * g + fs[5] : g;
      if (matrix4f != null) {
         Vector4f vector4f = matrix4f.transform(new Vector4f(floatValue37, floatValue38, 0.0F, 1.0F));
         float floatValue39 = Math.abs(vector4f.w) <= 1.0E-6F ? 1.0F : 1.0F / vector4f.w;
         floatValue37 = vector4f.x * floatValue39;
         floatValue38 = vector4f.y * floatValue39;
      }

      return new NeumorphismRenderer.NeumorphismRendererData3(floatValue37, floatValue38);
   }

   static float measure5(float f, float g, float h) {
      return !Float.isFinite(f) ? g : Math.max(g, Math.min(h, f));
   }

   private static NeumorphismRenderer.NeumorphismRendererState[] resolve12() {
      NeumorphismRenderer.NeumorphismRendererState[] w307s = new NeumorphismRenderer.NeumorphismRendererState[128];

      for (int intValue26 = 0; intValue26 < w307s.length; intValue26++) {
         w307s[intValue26] = new NeumorphismRenderer.NeumorphismRendererState();
      }

      return w307s;
   }

   static final class NeumorphismRendererState {
      float floatValue;
      float floatValue2;
      float floatValue3;
      float floatValue4;
      float floatValue5;
      float floatValue6;
      float floatValue7;
      float floatValue8;
      float floatValue9;
      float floatValue10;
      float floatValue11;
      float floatValue12;
      float floatValue13;
      float floatValue14;
      int intValue;
      int intValue2;
      int intValue3;
      int intValue4;
      int intValue5;
      float floatValue15;

      void invoke(
         float f,
         float g,
         float h,
         float i,
         float j,
         float k,
         float l,
         float m,
         float n,
         float o,
         float p,
         float q,
         int r,
         int s,
         int t,
         int u,
         int v,
         int w,
         int x,
         float y
      ) {
         this.floatValue = f;
         this.floatValue2 = g;
         this.floatValue3 = h;
         this.floatValue4 = i;
         this.floatValue5 = j;
         this.floatValue6 = k;
         this.floatValue7 = l;
         this.floatValue8 = m;
         this.floatValue9 = n;
         this.floatValue10 = o;
         this.floatValue11 = p;
         this.floatValue12 = q;
         this.floatValue13 = r;
         this.floatValue14 = s;
         this.intValue = t;
         this.intValue2 = u;
         this.intValue3 = v;
         this.intValue4 = w;
         this.intValue5 = x;
         this.floatValue15 = y;
      }
   }

   record NeumorphismRendererData(float minX, float minY, float maxX, float maxY) {
   }

   public record NeumorphismRendererData2(float distance, float blur, float intensity, int shape) {
      public NeumorphismRendererData2(float distance, float blur, float intensity, int shape) {
         distance = NeumorphismRenderer.measure5(distance, 1.0F, 36.0F);
         blur = NeumorphismRenderer.measure5(blur, 2.0F, 96.0F);
         intensity = NeumorphismRenderer.measure5(intensity, 0.0F, 1.4F);
         shape = Math.max(0, Math.min(2, shape));
         this.distance = distance;
         this.blur = blur;
         this.intensity = intensity;
         this.shape = shape;
      }
   }

   record NeumorphismRendererData3(float x, float y) {
   }
}
