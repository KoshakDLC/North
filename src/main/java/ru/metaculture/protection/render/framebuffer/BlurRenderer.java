package ru.metaculture.protection;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.Window;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public final class BlurRenderer {
   private static final BlurRenderer INSTANCE = new BlurRenderer();
   private static final int INT_VALUE = 5;
   private static final long TIMESTAMP = 760000000L;
   private static final long TIMESTAMP_2 = 1860000000L;
   private final BlurRenderer.BlurRendererState[] blurRendererStates = new BlurRenderer.BlurRendererState[5];
   private final float[] floats = new float[10];
   private final float[] floats2 = new float[5];
   private final float[] floats3 = new float[5];
   private final float[] floats4 = new float[5];
   private final float[] floats5 = new float[15];
   private final float[] floats6 = new float[15];
   private final float[] floats7 = new float[15];
   private final float[] floats8 = new float[15];
   private final float[] floats9 = new float[4];
   private final float[] floats10 = new float[4];
   private final float[] floats11 = new float[2];
   private final DepthRenderTarget depthRenderTarget = new DepthRenderTarget();
   private boolean flag;
   private BlurRenderer.BlurRendererBounds blurRendererBounds;
   private GlShaderProgram glShaderProgram;
   private int intValue = -1;
   private int intValue2 = -1;
   private int intValue3 = -1;
   private int intValue4 = -1;
   private int intValue5 = -1;
   private int intValue6 = -1;
   private int intValue7 = -1;
   private int intValue8 = -1;
   private int intValue9 = -1;
   private int intValue10 = -1;
   private int intValue11 = -1;
   private int intValue12 = -1;
   private int intValue13 = -1;
   private int intValue14 = -1;

   private BlurRenderer() {
      for (int intValue = 0; intValue < this.blurRendererStates.length; intValue++) {
         this.blurRendererStates[intValue] = new BlurRenderer.BlurRendererState();
      }
   }

   public static BlurRenderer getINSTANCE() {
      return INSTANCE;
   }

   public void invoke() {
      if (!this.flag) {
         this.flag = true;
         EventManager.register(this);
      }
   }

   @EventHandler
   public void onMouseButtonPosition(MouseButtonPositionEvent mouseButtonPositionEvent) {
      if (mouseButtonPositionEvent.isPress()) {
         if (MenuModule.check(MenuModule.VOLNY_KLIKA)) {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client != null && client.currentScreen != null) {
               Window window = client.getWindow();
               if (window != null && !window.hasZeroWidthOrHeight()) {
                  int intValue2 = window.getFramebufferWidth();
                  int intValue3 = window.getFramebufferHeight();
                  int intValue4 = window.getWidth();
                  int intValue5 = window.getHeight();
                  if (intValue2 > 0 && intValue3 > 0 && intValue4 > 0 && intValue5 > 0) {
                     float floatValue = measure2((float)(mouseButtonPositionEvent.getCursorX() * intValue2 / intValue4), 0.0F, Math.max(0.0F, intValue2 - 1.0F));
                     float floatValue2 = measure2((float)(mouseButtonPositionEvent.getCursorY() * intValue3 / intValue5), 0.0F, Math.max(0.0F, intValue3 - 1.0F));
                     this.invoke15(floatValue, floatValue2, this.measure(mouseButtonPositionEvent.getButton()), -1, -2232577, 0.0F, 760000000L);
                  }
               }
            }
         }
      }
   }

   public void invoke2(float f, float g, int i, int j) {
      this.invoke3(f, g, i, j, i, j);
   }

   public void invoke3(float f, float g, int i, int j, int k, int l) {
      this.invoke16(f, g, 1.24F, i, j, k, l, 1.0F, 1860000000L);
   }

   public void invoke4(float f, float g, float h, float i, float j, float k, float l, float m, float n, float o) {
      this.floats9[0] = Math.max(0.0F, f);
      this.floats9[1] = Math.max(0.0F, g);
      this.floats9[2] = Math.max(0.0F, h);
      this.floats9[3] = Math.max(0.0F, i);
      this.floats10[0] = Math.max(0.0F, k);
      this.floats10[1] = Math.max(0.0F, l);
      this.floats10[2] = Math.max(0.0F, m);
      this.floats10[3] = Math.max(0.0F, n);
      this.floats11[0] = Math.max(0.0F, j);
      this.floats11[1] = Math.max(0.0F, o);
   }

   public boolean check(Screen screen) {
      this.invoke17(System.nanoTime());
      return screen != null && !this.check4(screen) && this.check5();
   }

   public boolean check2(Object object) {
      this.invoke17(System.nanoTime());
      return object != null && this.check4(object) && this.check5();
   }

   public boolean check3(int i, int j) {
      this.invoke17(System.nanoTime());
      if (this.blurRendererBounds == null && i > 0 && j > 0 && this.check5()) {
         this.invoke10(i, j);
         FramebufferUtils.GlStateSnapshot glStateSnapshot = FramebufferUtils.captureGlState();
         this.blurRendererBounds = new BlurRenderer.BlurRendererBounds(glStateSnapshot, i, j);
         GL30.glBindFramebuffer(36160, this.depthRenderTarget.intValue);
         GL11.glViewport(0, 0, Math.max(0, i), Math.max(0, j));
         GL11.glDisable(3089);
         GL11.glDisable(36281);
         GL11.glColorMask(true, true, true, true);
         GL11.glDepthMask(true);
         GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
         GL11.glClear(16640);
         return true;
      } else {
         return false;
      }
   }

   public void invoke5() {
      BlurRenderer.BlurRendererBounds blurRendererBounds = this.blurRendererBounds;
      if (blurRendererBounds != null) {
         this.blurRendererBounds = null;
         FramebufferUtils.restoreGlState(blurRendererBounds.snapshot());
         FramebufferUtils.GlStateSnapshot glStateSnapshot2 = FramebufferUtils.captureGlState();
         boolean flag = false ;

         try {
            flag = true;
            this.invoke12(blurRendererBounds.width(), blurRendererBounds.height());
            flag = false;
         } finally {
            if (flag) {
               FramebufferUtils.restoreGlState(glStateSnapshot2);
            }
         }

         FramebufferUtils.restoreGlState(glStateSnapshot2);
      }
   }

   public void invoke6() {
      long longValue = System.nanoTime();
      this.invoke17(longValue);
      if (this.blurRendererBounds == null && this.check5()) {
         MinecraftClient client2 = MinecraftClient.getInstance();
         if (client2 != null && client2.getWindow() != null && !client2.getWindow().hasZeroWidthOrHeight()) {
            RenderEngine renderEngine = WildClient.resolve2();
            if (renderEngine != null) {
               this.invoke11();
               RenderEngine.RenderEngineBounds renderEngineBounds = renderEngine.resolve6();
               if (renderEngineBounds.colorTexture() > 0 && renderEngineBounds.width() > 0 && renderEngineBounds.height() > 0) {
                  this.invoke13(renderEngineBounds.colorTexture(), renderEngineBounds.width(), renderEngineBounds.height(), false);
               }
            }
         }
      }
   }

   public void invoke7(int i, int j) {
      this.blurRendererBounds = null;
      if (i > 0 && j > 0) {
         if (this.depthRenderTarget.intValue4 != i || this.depthRenderTarget.intValue5 != j) {
            this.depthRenderTarget.invoke2();
            this.invoke18();
         }
      } else {
         this.depthRenderTarget.invoke2();
         this.invoke18();
      }
   }

   public void invoke8(boolean bl) {
      if (!bl) {
         this.blurRendererBounds = null;
         this.invoke18();
      }
   }

   public void invoke9() {
      this.blurRendererBounds = null;
      this.invoke18();
   }

   private boolean check4(Object object) {
      return object instanceof ModernClickGuiScreen || object instanceof ClickGuiScreen || object instanceof AutoBuyScreen || object instanceof BackdropScreen;
   }

   private void invoke10(int i, int j) {
      this.depthRenderTarget.invoke(i, j);
      this.invoke11();
   }

   private void invoke11() {
      if (this.glShaderProgram == null) {
         this.glShaderProgram = GlShaderProgram.resolve("assets/wild/shaders/blur/blur_fullscreen.vert", "assets/wild/shaders/postfx/gui_ripple.frag");
         this.intValue = this.glShaderProgram.compute2("uSource");
         this.intValue2 = this.glShaderProgram.compute2("uResolution");
         this.intValue3 = this.glShaderProgram.compute2("uRippleCount");
         this.intValue4 = this.glShaderProgram.compute2("uRippleCenter[0]");
         this.intValue5 = this.glShaderProgram.compute2("uRippleAge[0]");
         this.intValue6 = this.glShaderProgram.compute2("uRipplePower[0]");
         this.intValue7 = this.glShaderProgram.compute2("uRippleKind[0]");
         this.intValue8 = this.glShaderProgram.compute2("uRipplePreviousColorTop[0]");
         this.intValue9 = this.glShaderProgram.compute2("uRipplePreviousColorBottom[0]");
         this.intValue10 = this.glShaderProgram.compute2("uRippleColorTop[0]");
         this.intValue11 = this.glShaderProgram.compute2("uRippleColorBottom[0]");
         this.intValue12 = this.glShaderProgram.compute2("uThemeGuiRect");
         this.intValue13 = this.glShaderProgram.compute2("uThemePanelRect");
         this.intValue14 = this.glShaderProgram.compute2("uThemeRadii");
      }
   }

   private void invoke12(int i, int j) {
      this.invoke13(this.depthRenderTarget.intValue2, i, j, true);
   }

   private void invoke13(int i, int j, int k, boolean bl) {
      long longValue2 = System.nanoTime();
      int intValue6 = this.compute(longValue2);
      RenderEngine renderEngine2 = WildClient.resolve2();
      if (renderEngine2 != null) {
         renderEngine2.invoke60(i, j, k, this.glShaderProgram, () -> {
            if (this.intValue >= 0) {
               GL20.glUniform1i(this.intValue, 0);
            }

            if (this.intValue2 >= 0) {
               GL20.glUniform2f(this.intValue2, j, k);
            }

            if (this.intValue3 >= 0) {
               GL20.glUniform1i(this.intValue3, intValue6);
            }

            this.invoke14();
         }, bl);
      }
   }

   private int compute(long l) {
      int intValue7 = 0;

      for (int intValue8 = 0; intValue8 < 5; intValue8++) {
         BlurRenderer.BlurRendererState blurRendererState = this.blurRendererStates[intValue8];
         if (blurRendererState.flag) {
            float floatValue3 = (float)(l - blurRendererState.timestamp) / (float)blurRendererState.timestamp2;
            if (floatValue3 >= 1.0F) {
               blurRendererState.flag = false;
            } else {
               this.floats[intValue7 * 2] = blurRendererState.floatValue;
               this.floats[intValue7 * 2 + 1] = blurRendererState.floatValue2;
               this.floats2[intValue7] = measure2(floatValue3, 0.0F, 1.0F);
               this.floats3[intValue7] = blurRendererState.floatValue3;
               this.floats4[intValue7] = blurRendererState.floatValue4;
               invoke19(blurRendererState.intValue, this.floats5, intValue7 * 3);
               invoke19(blurRendererState.intValue2, this.floats6, intValue7 * 3);
               invoke19(blurRendererState.intValue3, this.floats7, intValue7 * 3);
               invoke19(blurRendererState.intValue4, this.floats8, intValue7 * 3);
               intValue7++;
            }
         }
      }

      for (int intValue9 = intValue7; intValue9 < 5; intValue9++) {
         this.floats[intValue9 * 2] = 0.0F;
         this.floats[intValue9 * 2 + 1] = 0.0F;
         this.floats2[intValue9] = 1.0F;
         this.floats3[intValue9] = 0.0F;
         this.floats4[intValue9] = 0.0F;
         invoke19(-1, this.floats5, intValue9 * 3);
         invoke19(-2232577, this.floats6, intValue9 * 3);
         invoke19(-1, this.floats7, intValue9 * 3);
         invoke19(-2232577, this.floats8, intValue9 * 3);
      }

      return intValue7;
   }

   private void invoke14() {
      if (this.intValue4 >= 0) {
         GL20.glUniform2fv(this.intValue4, this.floats);
      }

      if (this.intValue5 >= 0) {
         GL20.glUniform1fv(this.intValue5, this.floats2);
      }

      if (this.intValue6 >= 0) {
         GL20.glUniform1fv(this.intValue6, this.floats3);
      }

      if (this.intValue7 >= 0) {
         GL20.glUniform1fv(this.intValue7, this.floats4);
      }

      if (this.intValue8 >= 0) {
         GL20.glUniform3fv(this.intValue8, this.floats5);
      }

      if (this.intValue9 >= 0) {
         GL20.glUniform3fv(this.intValue9, this.floats6);
      }

      if (this.intValue10 >= 0) {
         GL20.glUniform3fv(this.intValue10, this.floats7);
      }

      if (this.intValue11 >= 0) {
         GL20.glUniform3fv(this.intValue11, this.floats8);
      }

      if (this.intValue12 >= 0) {
         GL20.glUniform4fv(this.intValue12, this.floats9);
      }

      if (this.intValue13 >= 0) {
         GL20.glUniform4fv(this.intValue13, this.floats10);
      }

      if (this.intValue14 >= 0) {
         GL20.glUniform2fv(this.intValue14, this.floats11);
      }
   }

   private void invoke15(float f, float g, float h, int i, int j, float k, long l) {
      this.invoke16(f, g, h, i, j, i, j, k, l);
   }

   private void invoke16(float f, float g, float h, int i, int j, int k, int l, float m, long n) {
      long longValue3 = System.nanoTime();
      BlurRenderer.BlurRendererState blurRendererState2 = null;
      BlurRenderer.BlurRendererState blurRendererState3 = this.blurRendererStates[0];

      for (BlurRenderer.BlurRendererState blurRendererState4 : this.blurRendererStates) {
         if (!blurRendererState4.flag) {
            blurRendererState2 = blurRendererState4;
            break;
         }

         if (blurRendererState4.timestamp < blurRendererState3.timestamp) {
            blurRendererState3 = blurRendererState4;
         }
      }

      if (blurRendererState2 == null) {
         blurRendererState2 = blurRendererState3;
      }

      blurRendererState2.flag = true;
      blurRendererState2.floatValue = f;
      blurRendererState2.floatValue2 = g;
      blurRendererState2.floatValue3 = h;
      blurRendererState2.floatValue4 = m;
      blurRendererState2.intValue = i;
      blurRendererState2.intValue2 = j;
      blurRendererState2.intValue3 = k;
      blurRendererState2.intValue4 = l;
      blurRendererState2.timestamp2 = Math.max(1L, n);
      blurRendererState2.timestamp = longValue3;
   }

   private void invoke17(long l) {
      for (BlurRenderer.BlurRendererState blurRendererState5 : this.blurRendererStates) {
         if (blurRendererState5.flag && l - blurRendererState5.timestamp >= blurRendererState5.timestamp2) {
            blurRendererState5.flag = false;
         }
      }
   }

   private boolean check5() {
      for (BlurRenderer.BlurRendererState blurRendererState6 : this.blurRendererStates) {
         if (blurRendererState6.flag) {
            return true;
         }
      }

      return false;
   }

   private void invoke18() {
      for (BlurRenderer.BlurRendererState blurRendererState7 : this.blurRendererStates) {
         blurRendererState7.flag = false;
         blurRendererState7.timestamp = 0L;
         blurRendererState7.floatValue = 0.0F;
         blurRendererState7.floatValue2 = 0.0F;
         blurRendererState7.floatValue3 = 0.0F;
         blurRendererState7.floatValue4 = 0.0F;
         blurRendererState7.intValue = -1;
         blurRendererState7.intValue2 = -2232577;
         blurRendererState7.intValue3 = -1;
         blurRendererState7.intValue4 = -2232577;
         blurRendererState7.timestamp2 = 760000000L;
      }
   }

   private float measure(int i) {
      return switch (i) {
         case 0 -> 0.9F;
         case 1 -> 0.84F;
         case 2 -> 0.96F;
         default -> 0.86F;
      };
   }

   private static float measure2(float f, float g, float h) {
      return Math.max(g, Math.min(h, f));
   }

   private static void invoke19(int i, float[] fs, int j) {
      fs[j] = (i >>> 16 & 0xFF) / 255.0F;
      fs[j + 1] = (i >>> 8 & 0xFF) / 255.0F;
      fs[j + 2] = (i & 0xFF) / 255.0F;
   }

   record BlurRendererBounds(FramebufferUtils.GlStateSnapshot snapshot, int width, int height) {
   }

   static final class BlurRendererState {
      boolean flag;
      long timestamp;
      float floatValue;
      float floatValue2;
      float floatValue3;
      float floatValue4;
      int intValue = -1;
      int intValue2 = -2232577;
      int intValue3 = -1;
      int intValue4 = -2232577;
      long timestamp2 = 760000000L;
   }
}
