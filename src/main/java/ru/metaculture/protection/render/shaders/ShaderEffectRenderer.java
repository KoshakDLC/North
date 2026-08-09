package ru.metaculture.protection;

import com.mojang.blaze3d.opengl.GlStateManager;
import java.util.HashMap;
import java.util.Map;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL20;

public final class ShaderEffectRenderer {
   private static final ShaderEffectRenderer INSTANCE = new ShaderEffectRenderer();
   private final Map<String, ShaderEffectRenderer.ShaderEffectRendererState> valuesByKey = new HashMap<>();
   private ShaderSourceBuilder shaderSourceBuilder;
   private ShaderNodeRegistry shaderNodeRegistry;

   private ShaderEffectRenderer() {
   }

   public static ShaderEffectRenderer getINSTANCE() {
      return INSTANCE;
   }

   public synchronized void invoke(ShaderSourceBuilder shaderSourceBuilder, ShaderNodeRegistry shaderNodeRegistry) {
      this.shaderSourceBuilder = shaderSourceBuilder;
      this.shaderNodeRegistry = shaderNodeRegistry;
   }

   public synchronized boolean check(String string, float f, float g, float h, float i, int j, int k, float l, float m, ColorScheme colorScheme, float n) {
      return this.check3(string, null, f, g, h, i, f, g, h, i, 0.0F, j, k, l, m, colorScheme, n);
   }

   public synchronized boolean check2(
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
      ColorScheme colorScheme2,
      float s
   ) {
      return this.check3(string, ShaderSurface.HUD, f, g, h, i, j, k, l, m, n, o, p, q, r, colorScheme2, s);
   }

   private boolean check3(
      String string,
      ShaderSurface shaderSurface,
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
      ColorScheme colorScheme3,
      float s
   ) {
      if (string == null || string.isBlank() || this.shaderSourceBuilder == null || this.shaderNodeRegistry == null) {
         return false;
      } else if (!(h <= 1.0F) && !(i <= 1.0F) && !(l <= 1.0F) && !(m <= 1.0F) && !(s <= 0.001F)) {
         ShaderEffectRenderer.ShaderEffectRendererState shaderEffectRendererState = this.resolve(string, shaderSurface);
         if (shaderEffectRendererState != null && shaderEffectRendererState.glShaderProgram != null) {
            ShaderProgram shaderProgram = ThemeShaderProgramCache.getINSTANCE().resolve();
            if (shaderProgram == null) {
               return false;
            } else {
               FramebufferUtils.GlStateSnapshot glStateSnapshot = FramebufferUtils.captureGlState();

               boolean flag;
               try {
                  GL11.glViewport(0, 0, Math.max(0, o), Math.max(0, p));
                  GL11.glDisable(2929);
                  GL11.glDisable(2884);
                  GlStateManager._enableBlend();
                  GL11.glEnable(3042);
                  GL14.glBlendFuncSeparate(770, 771, 1, 771);
                  GL11.glDisable(36281);
                  shaderEffectRendererState.glShaderProgram.invoke();
                  GL13.glActiveTexture(33984);
                  GL11.glBindTexture(3553, ThemeShaderProgramCache.getINSTANCE().compute());
                  invoke5(shaderEffectRendererState.glShaderProgram, "u_DiffuseMap", 0);
                  invoke6(shaderEffectRendererState.glShaderProgram, "uViewport", o, p);
                  invoke8(shaderEffectRendererState.glShaderProgram, "uRect", f, g, h, i);
                  invoke8(shaderEffectRendererState.glShaderProgram, "u_ElementRect", j, k, l, m);
                  invoke4(shaderEffectRendererState.glShaderProgram, "u_ElementRadius", Math.max(0.0F, n));
                  invoke4(shaderEffectRendererState.glShaderProgram, "u_Time", ThemeShaderProgramCache.getINSTANCE().measure());
                  invoke6(shaderEffectRendererState.glShaderProgram, "u_Resolution", Math.max(1.0F, (float)o), Math.max(1.0F, (float)p));
                  invoke6(shaderEffectRendererState.glShaderProgram, "u_GlobalUV", j / Math.max(1.0F, (float)o), k / Math.max(1.0F, (float)p));
                  invoke6(shaderEffectRendererState.glShaderProgram, "u_Mouse", q - j, r - k);
                  int intValue = colorScheme3 == null ? -1 : colorScheme3.getIntValue14();
                  int intValue2 = colorScheme3 == null ? -16777216 : colorScheme3.getIntValue15();
                  int intValue3 = colorScheme3 == null ? -15724520 : colorScheme3.getIntValue();
                  int intValue4 = colorScheme3 == null ? -14671832 : colorScheme3.getIntValue2();
                  invoke7(shaderEffectRendererState.glShaderProgram, "u_AccentTop", measure(intValue), measure2(intValue), measure3(intValue));
                  invoke7(shaderEffectRendererState.glShaderProgram, "u_AccentBottom", measure(intValue2), measure2(intValue2), measure3(intValue2));
                  invoke8(shaderEffectRendererState.glShaderProgram, "u_ThemeColors[0]", measure(intValue3), measure2(intValue3), measure3(intValue3), measure4(intValue3));
                  invoke8(shaderEffectRendererState.glShaderProgram, "u_ThemeColors[1]", measure(intValue4), measure2(intValue4), measure3(intValue4), measure4(intValue4));
                  invoke8(shaderEffectRendererState.glShaderProgram, "u_ThemeColors[2]", measure(intValue), measure2(intValue), measure3(intValue), s);
                  invoke8(shaderEffectRendererState.glShaderProgram, "u_ThemeColors[3]", measure(intValue2), measure2(intValue2), measure3(intValue2), s);
                  invoke4(shaderEffectRendererState.glShaderProgram, "u_Alpha", s);
                  invoke9(shaderEffectRendererState.glShaderProgram, shaderEffectRendererState.shaderBuildResult);
                  shaderProgram.invoke();
                  flag = true;
               } catch (Throwable exception) {
                  RenderDiagnosticsTracker.getInstance().fail("NamedThemeCache.draw:" + string, exception);
                  throw new IllegalStateException("unreachable shader failure", exception);
               } finally {
                  GL13.glActiveTexture(33984);
                  GL11.glBindTexture(3553, 0);
                  GL20.glUseProgram(0);
                  FramebufferUtils.restoreGlState(glStateSnapshot);
               }

               return flag;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public synchronized void invoke2(String string) {
      if (string != null) {
         String text = string.trim();
         this.valuesByKey.entrySet().removeIf(entry -> {
            String var2x = entry.getKey();
            boolean var3x = var2x.equals(text) || var2x.startsWith(text + "#");
            if (var3x && entry.getValue() != null && entry.getValue().glShaderProgram != null) {
               entry.getValue().glShaderProgram.invoke2();
               entry.getValue().glShaderProgram = null;
            }

            return var3x;
         });
         SavedShaderPreset savedShaderPreset = ShaderPresetStore.getINSTANCE()
            .resolve()
            .stream()
            .filter(savedShaderPreset2 -> text.equals(savedShaderPreset2.getText2()) || text.equals(savedShaderPreset2.getText()))
            .findFirst()
            .orElse(null);
         if (savedShaderPreset != null && !savedShaderPreset.getText().equals(text)) {
            String text2 = savedShaderPreset.getText();
            this.valuesByKey.entrySet().removeIf(entry -> {
               String var2x = entry.getKey();
               boolean var3x = var2x.equals(text2) || var2x.startsWith(text2 + "#");
               if (var3x && entry.getValue() != null && entry.getValue().glShaderProgram != null) {
                  entry.getValue().glShaderProgram.invoke2();
                  entry.getValue().glShaderProgram = null;
               }

               return var3x;
            });
         }
      }
   }

   public synchronized void invoke3() {
      for (ShaderEffectRenderer.ShaderEffectRendererState shaderEffectRendererState2 : this.valuesByKey.values()) {
         if (shaderEffectRendererState2.glShaderProgram != null) {
            shaderEffectRendererState2.glShaderProgram.invoke2();
            shaderEffectRendererState2.glShaderProgram = null;
         }
      }

      this.valuesByKey.clear();
   }

   private ShaderEffectRenderer.ShaderEffectRendererState resolve(String string, ShaderSurface shaderSurface2) {
      SavedShaderPreset savedShaderPreset3 = ShaderPresetStore.getINSTANCE()
         .resolve()
         .stream()
         .filter(savedShaderPreset4 -> string.equals(savedShaderPreset4.getText()) || string.equals(savedShaderPreset4.getText2()))
         .findFirst()
         .orElse(null);
      if (savedShaderPreset3 == null) {
         return null;
      } else {
         String text3 = shaderSurface2 == null ? savedShaderPreset3.getText() : savedShaderPreset3.getText() + "#" + shaderSurface2.getText();
         ShaderEffectRenderer.ShaderEffectRendererState shaderEffectRendererState3 = this.valuesByKey.get(text3);
         String text4 = shaderSurface2 == null ? savedShaderPreset3.getText4() : savedShaderPreset3.getText4() + "#" + shaderSurface2.getText();
         if (shaderEffectRendererState3 != null && shaderEffectRendererState3.text != null && shaderEffectRendererState3.text.equals(text4) && shaderEffectRendererState3.glShaderProgram != null) {
            return shaderEffectRendererState3;
         } else {
            try {
               ShaderNode shaderNode = WildThemeCodec.resolve2(savedShaderPreset3.getText4(), this.shaderNodeRegistry);
               if (shaderSurface2 != null) {
                  shaderNode.invoke2(shaderSurface2.getText());
               }

               ShaderBuildResult shaderBuildResult = this.shaderSourceBuilder.resolve2(shaderNode);
               if (shaderEffectRendererState3 != null && shaderEffectRendererState3.glShaderProgram != null) {
                  shaderEffectRendererState3.glShaderProgram.invoke2();
                  shaderEffectRendererState3.glShaderProgram = null;
               }

               if (shaderEffectRendererState3 == null) {
                  shaderEffectRendererState3 = new ShaderEffectRenderer.ShaderEffectRendererState();
                  this.valuesByKey.put(text3, shaderEffectRendererState3);
               }

               shaderEffectRendererState3.text = text4;
               shaderEffectRendererState3.shaderBuildResult = shaderBuildResult;
               shaderEffectRendererState3.glShaderProgram = new GlShaderProgram(ResourceUtils.resolve("assets/wild/shaders/mainmenu/menu_quad.vert"), shaderBuildResult.fragmentSource());
               shaderEffectRendererState3.text2 = shaderBuildResult.error();
               return shaderEffectRendererState3;
            } catch (Throwable exception2) {
               if (shaderEffectRendererState3 == null) {
                  shaderEffectRendererState3 = new ShaderEffectRenderer.ShaderEffectRendererState();
                  this.valuesByKey.put(savedShaderPreset3.getText(), shaderEffectRendererState3);
               }

               shaderEffectRendererState3.text2 = exception2.getMessage() == null ? exception2.getClass().getSimpleName() : exception2.getMessage();
               shaderEffectRendererState3.glShaderProgram = null;
               shaderEffectRendererState3.shaderBuildResult = null;
               RenderDiagnosticsTracker.getInstance().fail("NamedThemeCache.compile:" + savedShaderPreset3.getText(), exception2);
               throw new IllegalStateException("unreachable shader failure", exception2);
            }
         }
      }
   }

   private static void invoke4(GlShaderProgram glShaderProgram, String string, float f) {
      int intValue5 = glShaderProgram.compute2(string);
      if (intValue5 >= 0) {
         GL20.glUniform1f(intValue5, f);
      }
   }

   private static void invoke5(GlShaderProgram glShaderProgram2, String string, int i) {
      int intValue6 = glShaderProgram2.compute2(string);
      if (intValue6 >= 0) {
         GL20.glUniform1i(intValue6, i);
      }
   }

   private static void invoke6(GlShaderProgram glShaderProgram3, String string, float f, float g) {
      int intValue7 = glShaderProgram3.compute2(string);
      if (intValue7 >= 0) {
         GL20.glUniform2f(intValue7, f, g);
      }
   }

   private static void invoke7(GlShaderProgram glShaderProgram4, String string, float f, float g, float h) {
      int intValue8 = glShaderProgram4.compute2(string);
      if (intValue8 >= 0) {
         GL20.glUniform3f(intValue8, f, g, h);
      }
   }

   private static void invoke8(GlShaderProgram glShaderProgram5, String string, float f, float g, float h, float i) {
      int intValue9 = glShaderProgram5.compute2(string);
      if (intValue9 >= 0) {
         GL20.glUniform4f(intValue9, f, g, h, i);
      }
   }

   private static void invoke9(GlShaderProgram glShaderProgram6, ShaderBuildResult shaderBuildResult2) {
      if (glShaderProgram6 != null && shaderBuildResult2 != null && !shaderBuildResult2.exposedUniforms().isEmpty()) {
         for (ShaderUniformSpec shaderUniformSpec : shaderBuildResult2.exposedUniforms()) {
            float[] floatValues = shaderUniformSpec.defaults();
            if (shaderUniformSpec.kind() == ShaderUniformSpec.ShaderUniformSpecState.FLOAT) {
               invoke4(glShaderProgram6, shaderUniformSpec.uniformName(), floatValues[0]);
            } else {
               float floatValue = floatValues.length > 0 ? floatValues[0] : 0.0F;
               float floatValue2 = floatValues.length > 1 ? floatValues[1] : 0.0F;
               float floatValue3 = floatValues.length > 2 ? floatValues[2] : 0.0F;
               float floatValue4 = floatValues.length > 3 ? floatValues[3] : 1.0F;
               invoke8(glShaderProgram6, shaderUniformSpec.uniformName(), floatValue, floatValue2, floatValue3, floatValue4);
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

   static final class ShaderEffectRendererState {
      String text = "";
      ShaderBuildResult shaderBuildResult;
      GlShaderProgram glShaderProgram;
      String text2 = "";
   }
}
