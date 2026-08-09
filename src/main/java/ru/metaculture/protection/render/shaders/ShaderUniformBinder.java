package ru.metaculture.protection;

import com.mojang.blaze3d.opengl.GlStateManager;
import java.util.Map;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL20;

public final class ShaderUniformBinder {
   private ShaderUniformBinder() {
   }

   public static boolean check(
      ShaderSurface shaderSurface, float f, float g, float h, float i, int j, int k, float l, float m, ColorScheme colorScheme, float n
   ) {
      if (shaderSurface != null && !(h <= 1.0F) && !(i <= 1.0F) && j > 0 && k > 0 && !(n <= 0.001F)) {
         ShaderBuildResult shaderBuildResult = ShaderPresetRegistry.getINSTANCE().resolve(shaderSurface);
         if (shaderBuildResult == null) {
            return false;
         } else {
            GlShaderProgram glShaderProgram = ThemeShaderProgramCache.getINSTANCE().resolve2(shaderSurface, shaderBuildResult);
            return check10(
               glShaderProgram,
               shaderBuildResult,
               ShaderPresetRegistry.getINSTANCE().resolve12(shaderSurface),
               f,
               g,
               h,
               i,
               f,
               g,
               h,
               i,
               0.0F,
               j,
               k,
               l,
               m,
               colorScheme,
               n,
               ThemeShaderProgramCache.getINSTANCE().compute()
            );
         }
      } else {
         return false;
      }
   }

   public static boolean check2(String string, float f, float g, float h, float i, int j, int k, float l, float m, ColorScheme colorScheme2, float n) {
      if (string != null && !(h <= 1.0F) && !(i <= 1.0F) && j > 0 && k > 0 && !(n <= 0.001F)) {
         ShaderBuildResult shaderBuildResult2 = ShaderPresetRegistry.getINSTANCE().resolve2(string);
         if (shaderBuildResult2 == null) {
            return false;
         } else {
            GlShaderProgram glShaderProgram2 = ThemeShaderProgramCache.getINSTANCE().resolve3(string, shaderBuildResult2);
            return check10(
               glShaderProgram2,
               shaderBuildResult2,
               ShaderPresetRegistry.getINSTANCE().resolve13(string),
               f,
               g,
               h,
               i,
               f,
               g,
               h,
               i,
               0.0F,
               j,
               k,
               l,
               m,
               colorScheme2,
               n,
               ThemeShaderProgramCache.getINSTANCE().compute()
            );
         }
      } else {
         return false;
      }
   }

   public static boolean check3(String string, int i, float f, float g, float h, float j, int k, int l, float m, float n, ColorScheme colorScheme3, float o) {
      if (string != null && !(h <= 1.0F) && !(j <= 1.0F) && k > 0 && l > 0 && !(o <= 0.001F)) {
         ShaderBuildResult shaderBuildResult3 = ShaderPresetRegistry.getINSTANCE().resolve2(string);
         if (shaderBuildResult3 == null) {
            return false;
         } else {
            GlShaderProgram glShaderProgram3 = ThemeShaderProgramCache.getINSTANCE().resolve3(string, shaderBuildResult3);
            int intValue = i > 0 ? i : ThemeShaderProgramCache.getINSTANCE().compute();
            return check10(glShaderProgram3, shaderBuildResult3, ShaderPresetRegistry.getINSTANCE().resolve13(string), f, g, h, j, f, g, h, j, 0.0F, k, l, m, n, colorScheme3, o, intValue);
         }
      } else {
         return false;
      }
   }

   public static boolean check4(
      ShaderSurface shaderSurface2, int i, float f, float g, float h, float j, int k, int l, float m, float n, ColorScheme colorScheme4, float o
   ) {
      if (shaderSurface2 != null && !(h <= 1.0F) && !(j <= 1.0F) && k > 0 && l > 0 && !(o <= 0.001F)) {
         ShaderBuildResult shaderBuildResult4 = ShaderPresetRegistry.getINSTANCE().resolve(shaderSurface2);
         if (shaderBuildResult4 == null) {
            return false;
         } else {
            GlShaderProgram glShaderProgram4 = ThemeShaderProgramCache.getINSTANCE().resolve2(shaderSurface2, shaderBuildResult4);
            int intValue2 = i > 0 ? i : ThemeShaderProgramCache.getINSTANCE().compute();
            return check10(
               glShaderProgram4, shaderBuildResult4, ShaderPresetRegistry.getINSTANCE().resolve12(shaderSurface2), f, g, h, j, f, g, h, j, 0.0F, k, l, m, n, colorScheme4, o, intValue2
            );
         }
      } else {
         return false;
      }
   }

   public static boolean check5(
      String string,
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
      ColorScheme colorScheme5,
      float s
   ) {
      if (string != null && !(h <= 1.0F) && !(i <= 1.0F) && !(l <= 1.0F) && !(m <= 1.0F) && o > 0 && p > 0 && !(s <= 0.001F)) {
         ShaderBuildResult shaderBuildResult5 = ShaderPresetRegistry.getINSTANCE().resolve2(string);
         if (shaderBuildResult5 == null) {
            return false;
         } else {
            GlShaderProgram glShaderProgram5 = ThemeShaderProgramCache.getINSTANCE().resolve3(string, shaderBuildResult5);
            return check10(
               glShaderProgram5,
               shaderBuildResult5,
               ShaderPresetRegistry.getINSTANCE().resolve13(string),
               f,
               g,
               h,
               i,
               j,
               k,
               l,
               m,
               n,
               o,
               p,
               q,
               r,
               colorScheme5,
               s,
               ThemeShaderProgramCache.getINSTANCE().compute()
            );
         }
      } else {
         return false;
      }
   }

   public static boolean check6(
      ShaderSurface shaderSurface3,
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
      ColorScheme colorScheme6,
      float s
   ) {
      if (shaderSurface3 != null && !(h <= 1.0F) && !(i <= 1.0F) && !(l <= 1.0F) && !(m <= 1.0F) && o > 0 && p > 0 && !(s <= 0.001F)) {
         ShaderBuildResult shaderBuildResult6 = ShaderPresetRegistry.getINSTANCE().resolve(shaderSurface3);
         if (shaderBuildResult6 == null) {
            return false;
         } else {
            GlShaderProgram glShaderProgram6 = ThemeShaderProgramCache.getINSTANCE().resolve2(shaderSurface3, shaderBuildResult6);
            return check10(
               glShaderProgram6,
               shaderBuildResult6,
               ShaderPresetRegistry.getINSTANCE().resolve12(shaderSurface3),
               f,
               g,
               h,
               i,
               j,
               k,
               l,
               m,
               n,
               o,
               p,
               q,
               r,
               colorScheme6,
               s,
               ThemeShaderProgramCache.getINSTANCE().compute()
            );
         }
      } else {
         return false;
      }
   }

   public static boolean check7(
      String string,
      ShaderBuildResult shaderBuildResult7,
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
      if (string != null
         && !string.isBlank()
         && shaderBuildResult7 != null
         && !(h <= 1.0F)
         && !(i <= 1.0F)
         && !(l <= 1.0F)
         && !(m <= 1.0F)
         && o > 0
         && p > 0
         && !(s <= 0.001F)) {
         GlShaderProgram glShaderProgram7 = ThemeShaderProgramCache.getINSTANCE().resolve3(string, shaderBuildResult7);
         return check10(glShaderProgram7, shaderBuildResult7, Map.of(), f, g, h, i, j, k, l, m, n, o, p, q, r, colorScheme7, s, ThemeShaderProgramCache.getINSTANCE().compute());
      } else {
         return false;
      }
   }

   public static boolean check8(
      String string, ShaderBuildResult shaderBuildResult8, float f, float g, float h, float i, int j, int k, float l, float m, ColorScheme colorScheme8, float n
   ) {
      if (string != null && !string.isBlank() && shaderBuildResult8 != null && !(h <= 1.0F) && !(i <= 1.0F) && j > 0 && k > 0 && !(n <= 0.001F)) {
         GlShaderProgram glShaderProgram8 = ThemeShaderProgramCache.getINSTANCE().resolve3(string, shaderBuildResult8);
         return check10(
            glShaderProgram8, shaderBuildResult8, Map.of(), f, g, h, i, f, g, h, i, 0.0F, j, k, l, m, colorScheme8, n, ThemeShaderProgramCache.getINSTANCE().compute()
         );
      } else {
         return false;
      }
   }

   public static boolean check9(
      String string,
      ShaderBuildResult shaderBuildResult9,
      int i,
      float f,
      float g,
      float h,
      float j,
      int k,
      int l,
      float m,
      float n,
      ColorScheme colorScheme9,
      float o
   ) {
      if (string != null && !string.isBlank() && shaderBuildResult9 != null && !(h <= 1.0F) && !(j <= 1.0F) && k > 0 && l > 0 && !(o <= 0.001F)) {
         GlShaderProgram glShaderProgram9 = ThemeShaderProgramCache.getINSTANCE().resolve3(string, shaderBuildResult9);
         int intValue3 = i > 0 ? i : ThemeShaderProgramCache.getINSTANCE().compute();
         return check10(glShaderProgram9, shaderBuildResult9, Map.of(), f, g, h, j, f, g, h, j, 0.0F, k, l, m, n, colorScheme9, o, intValue3);
      } else {
         return false;
      }
   }

   private static boolean check10(
      GlShaderProgram glShaderProgram10,
      ShaderBuildResult shaderBuildResult10,
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
      ColorScheme colorScheme10,
      float s,
      int t
   ) {
      if (glShaderProgram10 == null) {
         return false;
      } else {
         ShaderProgram shaderProgram = ThemeShaderProgramCache.getINSTANCE().resolve();
         if (shaderProgram == null) {
            return false;
         } else {
            int intValue4 = GlStateInspector.getCurrentProgram();
            int intValue5 = GlStateInspector.getActiveTexture();
            int intValue6 = GlStateInspector.getTextureBinding2D();
            RenderDiagnosticsTracker.getInstance().invoke9();
            FramebufferUtils.GlStateSnapshot glStateSnapshot = FramebufferUtils.captureGlState();

            boolean flag;
            try {
               GL11.glViewport(0, 0, Math.max(0, o), Math.max(0, p));
               GL11.glDisable(2929);
               GL11.glDisable(2884);
               GL11.glDepthMask(false);
               GlStateManager._enableBlend();
               GL11.glEnable(3042);
               GL14.glBlendFuncSeparate(770, 771, 1, 771);
               GL11.glDisable(36281);
               glShaderProgram10.invoke();
               GL13.glActiveTexture(33984);
               GL11.glBindTexture(3553, t);
               invoke2(glShaderProgram10, "u_DiffuseMap", 0);
               invoke3(glShaderProgram10, "uViewport", o, p);
               invoke5(glShaderProgram10, "uRect", f, g, h, i);
               invoke5(glShaderProgram10, "u_ElementRect", j, k, l, m);
               invoke(glShaderProgram10, "u_ElementRadius", Math.max(0.0F, n));
               invoke(glShaderProgram10, "u_Time", ThemeShaderProgramCache.getINSTANCE().measure());
               invoke3(glShaderProgram10, "u_Resolution", Math.max(1.0F, (float)o), Math.max(1.0F, (float)p));
               invoke3(glShaderProgram10, "u_GlobalUV", j / Math.max(1.0F, (float)o), k / Math.max(1.0F, (float)p));
               invoke3(glShaderProgram10, "u_Mouse", q - j, r - k);
               int intValue7 = colorScheme10 == null ? -1 : colorScheme10.getIntValue14();
               int intValue8 = colorScheme10 == null ? -16777216 : colorScheme10.getIntValue15();
               int intValue9 = colorScheme10 == null ? -15724520 : colorScheme10.getIntValue();
               int intValue10 = colorScheme10 == null ? -14671832 : colorScheme10.getIntValue2();
               invoke4(glShaderProgram10, "u_AccentTop", measure(intValue7), measure2(intValue7), measure3(intValue7));
               invoke4(glShaderProgram10, "u_AccentBottom", measure(intValue8), measure2(intValue8), measure3(intValue8));
               invoke5(glShaderProgram10, "u_ThemeColors[0]", measure(intValue9), measure2(intValue9), measure3(intValue9), measure4(intValue9));
               invoke5(glShaderProgram10, "u_ThemeColors[1]", measure(intValue10), measure2(intValue10), measure3(intValue10), measure4(intValue10));
               invoke5(glShaderProgram10, "u_ThemeColors[2]", measure(intValue7), measure2(intValue7), measure3(intValue7), s);
               invoke5(glShaderProgram10, "u_ThemeColors[3]", measure(intValue8), measure2(intValue8), measure3(intValue8), s);
               invoke(glShaderProgram10, "u_Alpha", s);
               invoke6(glShaderProgram10, shaderBuildResult10, map);
               shaderProgram.invoke();
               flag = true;
            } catch (Throwable exception) {
               RenderDiagnosticsTracker.getInstance().fail("ThemeShaderDispatcher.drawProgram", exception);
               throw new IllegalStateException("unreachable shader failure", exception);
            } finally {
               try {
                  GL13.glActiveTexture(33984);
                  GL11.glBindTexture(3553, 0);
                  GL20.glUseProgram(0);
                  FramebufferUtils.restoreGlState(glStateSnapshot);
               } finally {
                  RenderDiagnosticsTracker.getInstance().invoke10(intValue4, intValue5, intValue6);
               }
            }

            return flag;
         }
      }
   }

   private static void invoke(GlShaderProgram glShaderProgram11, String string, float f) {
      int intValue11 = glShaderProgram11.compute2(string);
      if (intValue11 >= 0) {
         GL20.glUniform1f(intValue11, f);
      }
   }

   private static void invoke2(GlShaderProgram glShaderProgram12, String string, int i) {
      int intValue12 = glShaderProgram12.compute2(string);
      if (intValue12 >= 0) {
         GL20.glUniform1i(intValue12, i);
      }
   }

   private static void invoke3(GlShaderProgram glShaderProgram13, String string, float f, float g) {
      int intValue13 = glShaderProgram13.compute2(string);
      if (intValue13 >= 0) {
         GL20.glUniform2f(intValue13, f, g);
      }
   }

   private static void invoke4(GlShaderProgram glShaderProgram14, String string, float f, float g, float h) {
      int intValue14 = glShaderProgram14.compute2(string);
      if (intValue14 >= 0) {
         GL20.glUniform3f(intValue14, f, g, h);
      }
   }

   private static void invoke5(GlShaderProgram glShaderProgram15, String string, float f, float g, float h, float i) {
      int intValue15 = glShaderProgram15.compute2(string);
      if (intValue15 >= 0) {
         GL20.glUniform4f(intValue15, f, g, h, i);
      }
   }

   private static void invoke6(GlShaderProgram glShaderProgram16, ShaderBuildResult shaderBuildResult11, Map<String, float[]> map) {
      if (glShaderProgram16 != null && shaderBuildResult11 != null && !shaderBuildResult11.exposedUniforms().isEmpty()) {
         for (ShaderUniformSpec shaderUniformSpec : shaderBuildResult11.exposedUniforms()) {
            float[] floatValues = map == null ? null : (float[])map.get(shaderUniformSpec.uniformName());
            if (floatValues == null || floatValues.length == 0) {
               floatValues = shaderUniformSpec.defaults();
            }

            if (shaderUniformSpec.kind() == ShaderUniformSpec.ShaderUniformSpecState.FLOAT) {
               invoke(glShaderProgram16, shaderUniformSpec.uniformName(), floatValues[0]);
            } else {
               float floatValue = floatValues.length > 0 ? floatValues[0] : 0.0F;
               float floatValue2 = floatValues.length > 1 ? floatValues[1] : 0.0F;
               float floatValue3 = floatValues.length > 2 ? floatValues[2] : 0.0F;
               float floatValue4 = floatValues.length > 3 ? floatValues[3] : 1.0F;
               invoke5(glShaderProgram16, shaderUniformSpec.uniformName(), floatValue, floatValue2, floatValue3, floatValue4);
            }
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
}
