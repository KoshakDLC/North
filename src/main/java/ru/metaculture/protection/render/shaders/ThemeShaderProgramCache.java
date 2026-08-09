package ru.metaculture.protection;

import com.mojang.blaze3d.systems.RenderSystem;
import java.nio.ByteBuffer;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public final class ThemeShaderProgramCache {
   private static final ThemeShaderProgramCache INSTANCE = new ThemeShaderProgramCache();
   private static final String ASSETS_WILD_SHADERS_MAINMENU_MENU_QUAD_VERT = "assets/wild/shaders/mainmenu/menu_quad.vert";
   private final Map<ShaderSurface, ThemeShaderProgramCache.ThemeShaderProgramCacheState> valuesByKey = new EnumMap<>(ShaderSurface.class);
   private final Map<String, ThemeShaderProgramCache.ThemeShaderProgramCacheState> valuesByKey2 = new HashMap<>();
   private ShaderProgram shaderProgram;
   private String text;
   private long timestamp;
   private int intValue;

   private ThemeShaderProgramCache() {
   }

   public static ThemeShaderProgramCache getINSTANCE() {
      return INSTANCE;
   }

   public ShaderProgram resolve() {
      if (this.shaderProgram == null) {
         this.shaderProgram = new ShaderProgram();
      }

      return this.shaderProgram;
   }

   public float measure() {
      if (this.timestamp == 0L) {
         this.timestamp = System.nanoTime();
         return 0.0F;
      } else {
         return (float)(System.nanoTime() - this.timestamp) / 1.0E9F % 720.0F;
      }
   }

   public synchronized GlShaderProgram resolve2(ShaderSurface shaderSurface, ShaderBuildResult shaderBuildResult) {
      if (shaderSurface != null && shaderBuildResult != null && shaderBuildResult.fragmentSource() != null) {
         ThemeShaderProgramCache.ThemeShaderProgramCacheState themeShaderProgramCacheState = this.valuesByKey.get(shaderSurface);
         String text = shaderBuildResult.hash();
         if (themeShaderProgramCacheState != null && themeShaderProgramCacheState.glShaderProgram != null && themeShaderProgramCacheState.text.equals(text)) {
            return themeShaderProgramCacheState.glShaderProgram;
         } else {
            if (themeShaderProgramCacheState != null && themeShaderProgramCacheState.glShaderProgram != null) {
               themeShaderProgramCacheState.glShaderProgram.invoke2();
               themeShaderProgramCacheState.glShaderProgram = null;
            }

            if (themeShaderProgramCacheState == null) {
               themeShaderProgramCacheState = new ThemeShaderProgramCache.ThemeShaderProgramCacheState();
               this.valuesByKey.put(shaderSurface, themeShaderProgramCacheState);
            }

            try {
               String text2 = this.resolve8();
               themeShaderProgramCacheState.glShaderProgram = new GlShaderProgram(text2, shaderBuildResult.fragmentSource());
               themeShaderProgramCacheState.text = text;
               themeShaderProgramCacheState.text2 = shaderBuildResult.error();
               return themeShaderProgramCacheState.glShaderProgram;
            } catch (Throwable exception) {
               themeShaderProgramCacheState.text2 = exception.getMessage() == null ? exception.getClass().getSimpleName() : exception.getMessage();
               themeShaderProgramCacheState.glShaderProgram = null;
               themeShaderProgramCacheState.text = "";
               RenderDiagnosticsTracker.getInstance().fail("ThemeShaderProgramCache.acquire:" + shaderSurface.getText(), exception);
               throw new IllegalStateException("unreachable shader failure", exception);
            }
         }
      } else {
         return null;
      }
   }

   public synchronized GlShaderProgram resolve3(String string, ShaderBuildResult shaderBuildResult2) {
      String text3 = ShaderPresetRegistry.resolve21(string);
      if (!text3.isBlank() && shaderBuildResult2 != null && shaderBuildResult2.fragmentSource() != null) {
         ThemeShaderProgramCache.ThemeShaderProgramCacheState themeShaderProgramCacheState2 = this.valuesByKey2.get(text3);
         String text4 = shaderBuildResult2.hash();
         if (themeShaderProgramCacheState2 != null && themeShaderProgramCacheState2.glShaderProgram != null && themeShaderProgramCacheState2.text.equals(text4)) {
            return themeShaderProgramCacheState2.glShaderProgram;
         } else {
            if (themeShaderProgramCacheState2 != null && themeShaderProgramCacheState2.glShaderProgram != null) {
               themeShaderProgramCacheState2.glShaderProgram.invoke2();
               themeShaderProgramCacheState2.glShaderProgram = null;
            }

            if (themeShaderProgramCacheState2 == null) {
               themeShaderProgramCacheState2 = new ThemeShaderProgramCache.ThemeShaderProgramCacheState();
               this.valuesByKey2.put(text3, themeShaderProgramCacheState2);
            }

            try {
               String text5 = this.resolve8();
               themeShaderProgramCacheState2.glShaderProgram = new GlShaderProgram(text5, shaderBuildResult2.fragmentSource());
               themeShaderProgramCacheState2.text = text4;
               themeShaderProgramCacheState2.text2 = shaderBuildResult2.error();
               return themeShaderProgramCacheState2.glShaderProgram;
            } catch (Throwable exception2) {
               themeShaderProgramCacheState2.text2 = exception2.getMessage() == null ? exception2.getClass().getSimpleName() : exception2.getMessage();
               themeShaderProgramCacheState2.glShaderProgram = null;
               themeShaderProgramCacheState2.text = "";
               RenderDiagnosticsTracker.getInstance().fail("ThemeShaderProgramCache.acquire:" + text3, exception2);
               throw new IllegalStateException("unreachable shader failure", exception2);
            }
         }
      } else {
         return null;
      }
   }

   public synchronized String resolve4(ShaderSurface shaderSurface2) {
      ThemeShaderProgramCache.ThemeShaderProgramCacheState themeShaderProgramCacheState3 = this.valuesByKey.get(shaderSurface2);
      return themeShaderProgramCacheState3 != null && themeShaderProgramCacheState3.text2 != null ? themeShaderProgramCacheState3.text2 : "";
   }

   public synchronized String resolve5(String string) {
      ThemeShaderProgramCache.ThemeShaderProgramCacheState themeShaderProgramCacheState4 = this.valuesByKey2.get(ShaderPresetRegistry.resolve21(string));
      return themeShaderProgramCacheState4 != null && themeShaderProgramCacheState4.text2 != null ? themeShaderProgramCacheState4.text2 : "";
   }

   public synchronized String resolve6(ShaderSurface shaderSurface3) {
      ThemeShaderProgramCache.ThemeShaderProgramCacheState themeShaderProgramCacheState5 = this.valuesByKey.get(shaderSurface3);
      return themeShaderProgramCacheState5 == null ? "" : themeShaderProgramCacheState5.text;
   }

   public synchronized String resolve7(String string) {
      ThemeShaderProgramCache.ThemeShaderProgramCacheState themeShaderProgramCacheState6 = this.valuesByKey2.get(ShaderPresetRegistry.resolve21(string));
      return themeShaderProgramCacheState6 == null ? "" : themeShaderProgramCacheState6.text;
   }

   public synchronized void invoke(ShaderSurface shaderSurface4) {
      ThemeShaderProgramCache.ThemeShaderProgramCacheState themeShaderProgramCacheState7 = this.valuesByKey.remove(shaderSurface4);
      if (themeShaderProgramCacheState7 != null && themeShaderProgramCacheState7.glShaderProgram != null && check()) {
         themeShaderProgramCacheState7.glShaderProgram.invoke2();
         themeShaderProgramCacheState7.glShaderProgram = null;
      }
   }

   public synchronized void invoke2(String string) {
      ThemeShaderProgramCache.ThemeShaderProgramCacheState themeShaderProgramCacheState8 = this.valuesByKey2.remove(ShaderPresetRegistry.resolve21(string));
      if (themeShaderProgramCacheState8 != null && themeShaderProgramCacheState8.glShaderProgram != null && check()) {
         themeShaderProgramCacheState8.glShaderProgram.invoke2();
         themeShaderProgramCacheState8.glShaderProgram = null;
      }
   }

   public synchronized void invoke3() {
      boolean flag = check();

      for (ThemeShaderProgramCache.ThemeShaderProgramCacheState themeShaderProgramCacheState9 : this.valuesByKey.values()) {
         if (themeShaderProgramCacheState9.glShaderProgram != null && flag) {
            themeShaderProgramCacheState9.glShaderProgram.invoke2();
         }

         themeShaderProgramCacheState9.glShaderProgram = null;
      }

      for (ThemeShaderProgramCache.ThemeShaderProgramCacheState themeShaderProgramCacheState10 : this.valuesByKey2.values()) {
         if (themeShaderProgramCacheState10.glShaderProgram != null && flag) {
            themeShaderProgramCacheState10.glShaderProgram.invoke2();
         }

         themeShaderProgramCacheState10.glShaderProgram = null;
      }

      this.valuesByKey.clear();
      this.valuesByKey2.clear();
      if (this.shaderProgram != null && flag) {
         this.shaderProgram.close();
      }

      this.shaderProgram = null;
      if (this.intValue > 0 && flag) {
         GL11.glDeleteTextures(this.intValue);
      }

      this.intValue = 0;
      this.timestamp = 0L;
      this.text = null;
   }

   public synchronized int compute() {
      if (this.intValue > 0) {
         return this.intValue;
      } else {
         ByteBuffer byteBuffer = BufferUtils.createByteBuffer(4);
         byteBuffer.put((byte)-1).put((byte)-1).put((byte)-1).put((byte)-1).flip();
         this.intValue = GL11.glGenTextures();
         GL11.glBindTexture(3553, this.intValue);
         GL11.glTexParameteri(3553, 10241, 9729);
         GL11.glTexParameteri(3553, 10240, 9729);
         GL11.glTexParameteri(3553, 10242, 33071);
         GL11.glTexParameteri(3553, 10243, 33071);
         GL11.glTexImage2D(3553, 0, 32856, 1, 1, 0, 6408, 5121, byteBuffer);
         GL11.glBindTexture(3553, 0);
         return this.intValue;
      }
   }

   private String resolve8() {
      if (this.text == null) {
         this.text = ResourceUtils.resolve("assets/wild/shaders/mainmenu/menu_quad.vert");
      }

      return this.text;
   }

   private static boolean check() {
      return RenderSystem.isOnRenderThread() && GLFW.glfwGetCurrentContext() != 0L;
   }

   static final class ThemeShaderProgramCacheState {
      GlShaderProgram glShaderProgram;
      String text = "";
      String text2 = "";
   }
}
