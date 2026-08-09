package ru.metaculture.protection;

import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.texture.GlTexture;
import org.lwjgl.opengl.ARBDrawInstanced;
import org.lwjgl.opengl.ARBInstancedArrays;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL33;
import org.lwjgl.opengl.GL43;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.opengl.GLDebugMessageCallback;
import org.lwjgl.opengl.KHRDebug;

public final class RenderEngine {
   private static final int INT_VALUE = 4096;
   private static final int INT_VALUE_2 = 16;
   private static final int INT_VALUE_3 = 144;
   private static final int INT_VALUE_4 = 0;
   private static final int INT_VALUE_5 = 1;
   private static final int INT_VALUE_6 = 2;
   private static final int INT_VALUE_7 = 3;
   private static final int INT_VALUE_8 = 16;
   private static final int INT_VALUE_9 = 32;
   private static final int INT_VALUE_10 = 64;
   private static final int INT_VALUE_11 = 67108864;
   private static final float[] FLOATS = new float[]{1.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 1.0F};
   private final boolean flag;
   private final boolean flag2;
   private final boolean flag3;
   private final boolean flag4;
   private final GlShaderProgram glShaderProgram;
   private final int intValue;
   private final int intValue2;
   private final int intValue3;
   private final ByteBuffer byteBuffer;
   private int intValue4 = 0;
   private FramebufferUtils.GlStateSnapshot glStateSnapshot;
   private int intValue5;
   private int intValue6;
   private int intValue7 = -1;
   private int intValue8 = -1;
   private boolean flag5 = false;
   private int intValue9 = 0;
   private int intValue10 = 0;
   private int intValue11 = Integer.MAX_VALUE;
   private int intValue12 = Integer.MAX_VALUE;
   private float floatValue = 0.0F;
   private float floatValue2 = 0.0F;
   private float floatValue3 = 0.0F;
   private float floatValue4 = 0.0F;
   private final Int2IntOpenHashMap int2IntOpenHashMap = new Int2IntOpenHashMap(16);
   private final int[] ints = new int[16];
   private final int[] ints2 = new int[16];
   private int intValue13 = 0;
   private int intValue14 = -1;
   private boolean flag6 = false;
   private int intValue15 = 0;
   private int intValue16 = 0;
   private int intValue17 = 0;
   private int intValue18 = 0;
   private int intValue19 = 0;
   private int intValue20 = 0;
   private int intValue21 = 0;
   private int intValue22 = 0;
   private float floatValue5 = 0.5F;
   private float floatValue6 = 0.5F;
   private int intValue23 = 0;
   private int intValue24 = 0;
   private int intValue25 = 0;
   private int intValue26 = 0;
   private final DepthRenderTarget depthRenderTarget = new DepthRenderTarget();
   private int intValue27 = 0;
   private int intValue28 = 0;
   private int intValue29 = 0;
   private GlShaderProgram glShaderProgram2;
   private int intValue30 = -1;
   private final RenderEngine.RenderEngineState renderEngineState = new RenderEngine.RenderEngineState();
   private int intValue31 = 0;
   private int intValue32 = 0;
   private GlShaderProgram glShaderProgram3;
   private int intValue33 = -1;
   private int intValue34 = -1;
   private int intValue35 = -1;
   private int intValue36 = -1;
   private int intValue37 = -1;
   private int intValue38 = -1;
   private int intValue39 = -1;
   private int intValue40 = -1;
   private int intValue41 = -1;
   private int intValue42 = -1;
   private int intValue43 = -1;
   private int intValue44 = -1;
   private int intValue45 = -1;
   private int intValue46 = -1;
   private final List<RenderEngine.RenderEngineState> items = new ArrayList<>();
   private int intValue47 = 0;
   private int intValue48 = 0;
   private int intValue49 = 0;
   private GlShaderProgram glShaderProgram4;
   private int intValue50 = -1;
   private int intValue51 = -1;
   private int intValue52 = -1;
   private int intValue53 = -1;
   private int intValue54 = -1;
   private int intValue55 = -1;
   private int intValue56 = -1;
   private int intValue57 = -1;
   private int intValue58 = -1;
   private int intValue59 = -1;
   private int intValue60 = -1;
   private int intValue61 = -1;
   private int intValue62 = -1;
   private GlShaderProgram glShaderProgram5;
   private int intValue63 = -1;
   private int intValue64 = -1;
   private int intValue65 = -1;
   private int intValue66 = -1;
   private int intValue67 = -1;
   private int intValue68 = -1;
   private int intValue69 = -1;
   private int intValue70 = -1;
   private int intValue71 = -1;
   private int intValue72 = -1;
   private int intValue73 = -1;
   private int intValue74 = -1;
   private int intValue75 = -1;
   private int intValue76 = -1;
   private int intValue77 = -1;
   private int intValue78 = -1;
   private int intValue79 = -1;
   private GlShaderProgram glShaderProgram6;
   private int intValue80 = -1;
   private int intValue81 = -1;
   private int intValue82 = -1;
   private int intValue83 = -1;
   private int DynamicButtonSetting = -1;
   private int intValue84 = -1;
   private int intValue85 = -1;
   private int intValue86 = -1;
   private int intValue87 = -1;
   private int SpacerSetting = -1;
   private int FoundryShaderSetting = -1;
   private int intValue88 = -1;
   private GLDebugMessageCallback gLDebugMessageCallback;
   private final BlurPipeline blurPipeline = new BlurPipeline(32856, 5121);
   private final BlurPipeline blurPipeline2 = new BlurPipeline(32856, 5121);
   private int intValue89 = 0;
   private int intValue90 = 0;
   private int intValue91 = 0;
   private float floatValue7 = 1.0F;
   private float floatValue8 = 1.0F;
   private int intValue92 = 0;
   private int intValue93 = 0;
   private int intValue94 = 0;
   private int intValue95 = 0;
   private int intValue96 = 0;
   private boolean flag7 = false;
   private boolean flag8 = false;
   private static final ConcurrentHashMap<Integer, Long> CONCURRENT_HASH_MAP = new ConcurrentHashMap<>();
   private static final AtomicLong ATOMIC_LONG = new AtomicLong();
   private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger();
   private static final long TIMESTAMP = 5000L;
   private static final long TIMESTAMP_2 = 1000L;
   private static final int INT_VALUE_12 = 8;

   private static int compute(int i) {
      int intValue = i >> 16 & 0xFF;
      int intValue2 = i >> 8 & 0xFF;
      int intValue3 = i & 0xFF;
      int intValue4 = i >>> 24 & 0xFF;
      return intValue4 << 24 | intValue3 << 16 | intValue2 << 8 | intValue;
   }

   private void invoke() {
      this.invoke2(1);
   }

   private void invoke2(int i) {
      if (i > 0) {
         if (i > 4096) {
            throw new IllegalArgumentException("additionalInstances must be between 1 and 4096");
         } else {
            if (this.intValue4 + i > 4096) {
               this.invoke19();
               this.intValue4 = 0;
               this.byteBuffer.clear();
               this.invoke3();
            }
         }
      }
   }

   private void invoke3() {
      this.int2IntOpenHashMap.clear();
      this.intValue13 = 0;
   }

   public RenderEngine() {
      this.int2IntOpenHashMap.defaultReturnValue(-1);
      GLCapabilities glCapabilities = GL.getCapabilities();
      this.flag = glCapabilities.OpenGL43;
      this.flag2 = glCapabilities.OpenGL43 || glCapabilities.GL_KHR_debug;
      boolean flag = glCapabilities.glVertexAttribDivisor != 0L;
      boolean flag2 = glCapabilities.glVertexAttribDivisorARB != 0L;
      boolean flag3 = glCapabilities.glDrawArraysInstanced != 0L;
      boolean flag4 = glCapabilities.glDrawArraysInstancedARB != 0L;
      boolean flag5 = flag || flag2;
      boolean flag6 = flag3 || flag4;
      this.flag3 = !flag && flag2;
      this.flag4 = !flag3 && flag4;
      if (this.flag || flag5 && flag6) {
         String text = this.flag ? "assets/wild/shaders/shape.vert" : "assets/wild/shaders/shape_compat.vert";
         String text2 = ResourceUtils.resolve(text);
         String text3 = ResourceUtils.resolve("assets/wild/shaders/shape.frag");
         this.glShaderProgram = new GlShaderProgram(text2, text3);
         this.intValue = GL30.glGenVertexArrays();
         int intValue5 = GL15.glGenBuffers();
         GL30.glBindVertexArray(this.intValue);
         GL15.glBindBuffer(34962, intValue5);
         float[] floatValues = new float[]{0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, 1.0F};
         GL15.glBufferData(34962, floatValues, 35044);
         GL20.glEnableVertexAttribArray(0);
         GL20.glVertexAttribPointer(0, 2, 5126, false, 0, 0L);
         int intValue6 = 0;
         if (!this.flag) {
            intValue6 = GL15.glGenBuffers();
            GL15.glBindBuffer(34962, intValue6);
            GL15.glBufferData(34962, 589824L, 35040);
            short shortValue = 144;
            long longValue = 0L;
            GL20.glEnableVertexAttribArray(1);
            GL20.glVertexAttribPointer(1, 4, 5126, false, shortValue, longValue);
            this.invoke16(1, 1);
            longValue += 16L;
            GL20.glEnableVertexAttribArray(2);
            GL20.glVertexAttribPointer(2, 4, 5126, false, shortValue, longValue);
            this.invoke16(2, 1);
            longValue += 16L;
            GL20.glEnableVertexAttribArray(3);
            GL30.glVertexAttribIPointer(3, 4, 5124, shortValue, longValue);
            this.invoke16(3, 1);
            longValue += 16L;
            GL20.glEnableVertexAttribArray(4);
            GL20.glVertexAttribPointer(4, 4, 5126, false, shortValue, longValue);
            this.invoke16(4, 1);
            longValue += 16L;
            GL20.glEnableVertexAttribArray(5);
            GL20.glVertexAttribPointer(5, 4, 5126, false, shortValue, longValue);
            this.invoke16(5, 1);
            longValue += 16L;
            GL20.glEnableVertexAttribArray(6);
            GL30.glVertexAttribIPointer(6, 4, 5125, shortValue, longValue);
            this.invoke16(6, 1);
            longValue += 16L;
            GL20.glEnableVertexAttribArray(7);
            GL20.glVertexAttribPointer(7, 4, 5126, false, shortValue, longValue);
            this.invoke16(7, 1);
            longValue += 16L;
            GL20.glEnableVertexAttribArray(8);
            GL20.glVertexAttribPointer(8, 4, 5126, false, shortValue, longValue);
            this.invoke16(8, 1);
            longValue += 16L;
            GL20.glEnableVertexAttribArray(9);
            GL30.glVertexAttribIPointer(9, 1, 5124, shortValue, longValue);
            this.invoke16(9, 1);
            longValue += 4L;
            GL20.glEnableVertexAttribArray(10);
            GL30.glVertexAttribIPointer(10, 1, 5124, shortValue, longValue);
            this.invoke16(10, 1);
            GL15.glBindBuffer(34962, 0);
         }

         GL15.glBindBuffer(34962, 0);
         GL30.glBindVertexArray(0);
         this.intValue3 = intValue6;
         this.byteBuffer = ByteBuffer.allocateDirect(589824).order(ByteOrder.nativeOrder());
         if (this.flag) {
            this.intValue2 = GL15.glGenBuffers();
            GL15.glBindBuffer(37074, this.intValue2);
            GL15.glBufferData(37074, 589824L, 35040);
            GL15.glBindBuffer(37074, 0);
         } else {
            this.intValue2 = 0;
         }

         if (this.flag2) {
            this.invoke67(glCapabilities);
         }
      } else {
         throw new IllegalStateException("OpenGL instanced rendering is required when shader storage buffers are unavailable");
      }
   }

   private void invoke4() {
      if (this.intValue28 == 0) {
         this.intValue28 = GL30.glGenVertexArrays();
         this.intValue29 = GL15.glGenBuffers();
         GL30.glBindVertexArray(this.intValue28);
         GL15.glBindBuffer(34962, this.intValue29);
         float[] floatValues2 = new float[]{
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
         GL15.glBufferData(34962, floatValues2, 35044);
         byte byteValue = 16;
         GL20.glEnableVertexAttribArray(0);
         GL20.glVertexAttribPointer(0, 2, 5126, false, byteValue, 0L);
         GL20.glEnableVertexAttribArray(1);
         GL20.glVertexAttribPointer(1, 2, 5126, false, byteValue, 8L);
         GL15.glBindBuffer(34962, 0);
         GL30.glBindVertexArray(0);
      }
   }

   private GlShaderProgram resolve() {
      if (this.glShaderProgram2 != null) {
         return this.glShaderProgram2;
      } else {
         String text4 = ResourceUtils.resolve("assets/wild/shaders/blur/blur_fullscreen.vert");
         String text5 = "#version 330 core\nlayout(location = 0) out vec4 fragColor;\nin vec2 vUv;\nuniform sampler2D uSource;\nvoid main() {\n    fragColor = texture(uSource, vUv);\n}";
         this.glShaderProgram2 = new GlShaderProgram(text4, text5);
         this.intValue30 = this.glShaderProgram2.compute2("uSource");
         return this.glShaderProgram2;
      }
   }

   private void invoke5() {
      if (this.intValue31 == 0) {
         this.intValue31 = GL30.glGenVertexArrays();
         this.intValue32 = GL15.glGenBuffers();
         GL30.glBindVertexArray(this.intValue31);
         GL15.glBindBuffer(34962, this.intValue32);
         GL15.glBufferData(34962, 96L, 35040);
         byte byteValue2 = 16;
         GL20.glEnableVertexAttribArray(0);
         GL20.glVertexAttribPointer(0, 2, 5126, false, byteValue2, 0L);
         GL20.glEnableVertexAttribArray(1);
         GL20.glVertexAttribPointer(1, 2, 5126, false, byteValue2, 8L);
         GL15.glBindBuffer(34962, 0);
         GL30.glBindVertexArray(0);
      }

      if (this.glShaderProgram3 == null) {
         this.glShaderProgram3 = GlShaderProgram.resolve("assets/wild/shaders/postfx/scroll_layer.vert", "assets/wild/shaders/postfx/scroll_layer.frag");
         this.intValue33 = this.glShaderProgram3.compute2("uSource");
         this.intValue34 = this.glShaderProgram3.compute2("uViewport");
         this.intValue35 = this.glShaderProgram3.compute2("uSize");
         this.intValue36 = this.glShaderProgram3.compute2("uTextureSize");
         this.intValue37 = this.glShaderProgram3.compute2("uRadii");
         this.intValue38 = this.glShaderProgram3.compute2("uClipRect");
         this.intValue39 = this.glShaderProgram3.compute2("uClipRadii");
         this.intValue40 = this.glShaderProgram3.compute2("uFadePx");
         this.intValue41 = this.glShaderProgram3.compute2("uEdgeBlurPx");
         this.intValue42 = this.glShaderProgram3.compute2("uMotionBlurPx");
         this.intValue43 = this.glShaderProgram3.compute2("uMotionStrength");
         this.intValue44 = this.glShaderProgram3.compute2("uFocusStrength");
         this.intValue45 = this.glShaderProgram3.compute2("uDirection");
         this.intValue46 = this.glShaderProgram3.compute2("uAlpha");
      }
   }

   private void invoke6() {
      if (this.intValue48 == 0) {
         this.intValue48 = GL30.glGenVertexArrays();
         this.intValue49 = GL15.glGenBuffers();
         GL30.glBindVertexArray(this.intValue48);
         GL15.glBindBuffer(34962, this.intValue49);
         GL15.glBufferData(34962, 96L, 35040);
         byte byteValue3 = 16;
         GL20.glEnableVertexAttribArray(0);
         GL20.glVertexAttribPointer(0, 2, 5126, false, byteValue3, 0L);
         GL20.glEnableVertexAttribArray(1);
         GL20.glVertexAttribPointer(1, 2, 5126, false, byteValue3, 8L);
         GL15.glBindBuffer(34962, 0);
         GL30.glBindVertexArray(0);
      }

      if (this.glShaderProgram4 == null) {
         this.glShaderProgram4 = GlShaderProgram.resolve("assets/wild/shaders/card_transition.vert", "assets/wild/shaders/card_transition.frag");
         this.intValue50 = this.glShaderProgram4.compute2("u_texture");
         this.intValue51 = this.glShaderProgram4.compute2("u_viewport");
         this.intValue52 = this.glShaderProgram4.compute2("u_resolution");
         this.intValue53 = this.glShaderProgram4.compute2("u_time");
         this.intValue54 = this.glShaderProgram4.compute2("u_progress");
         this.intValue55 = this.glShaderProgram4.compute2("u_color");
         this.intValue56 = this.glShaderProgram4.compute2("u_borderColor");
         this.intValue57 = this.glShaderProgram4.compute2("u_emissiveColor");
         this.intValue58 = this.glShaderProgram4.compute2("u_emissiveColor2");
         this.intValue59 = this.glShaderProgram4.compute2("u_radius");
         this.intValue60 = this.glShaderProgram4.compute2("u_alpha");
         this.intValue61 = this.glShaderProgram4.compute2("u_clipRect");
         this.intValue62 = this.glShaderProgram4.compute2("u_clipRadii");
      }
   }

   private void invoke7() {
      this.invoke6();
      if (this.glShaderProgram5 == null) {
         this.glShaderProgram5 = GlShaderProgram.resolve("assets/wild/shaders/card_transition.vert", "assets/wild/shaders/entity/nametag_plasma.frag");
         this.intValue63 = this.glShaderProgram5.compute2("u_texture");
         this.intValue64 = this.glShaderProgram5.compute2("u_viewport");
         this.intValue65 = this.glShaderProgram5.compute2("u_resolution");
         this.intValue66 = this.glShaderProgram5.compute2("u_time");
         this.intValue67 = this.glShaderProgram5.compute2("u_progress");
         this.intValue68 = this.glShaderProgram5.compute2("u_contentReveal");
         this.intValue69 = this.glShaderProgram5.compute2("u_focus");
         this.intValue70 = this.glShaderProgram5.compute2("u_threat");
         this.intValue71 = this.glShaderProgram5.compute2("u_exposure");
         this.intValue72 = this.glShaderProgram5.compute2("u_color");
         this.intValue73 = this.glShaderProgram5.compute2("u_borderColor");
         this.intValue74 = this.glShaderProgram5.compute2("u_emissiveColor");
         this.intValue75 = this.glShaderProgram5.compute2("u_emissiveColor2");
         this.intValue76 = this.glShaderProgram5.compute2("u_radius");
         this.intValue77 = this.glShaderProgram5.compute2("u_alpha");
         this.intValue78 = this.glShaderProgram5.compute2("u_clipRect");
         this.intValue79 = this.glShaderProgram5.compute2("u_clipRadii");
      }
   }

   private void invoke8() {
      this.invoke6();
      if (this.glShaderProgram6 == null) {
         this.glShaderProgram6 = GlShaderProgram.resolve("assets/wild/shaders/card_transition.vert", "assets/wild/shaders/fbo_mask.frag");
         this.intValue80 = this.glShaderProgram6.compute2("u_texture");
         this.intValue81 = this.glShaderProgram6.compute2("u_viewport");
         this.intValue82 = this.glShaderProgram6.compute2("u_resolution");
         this.intValue83 = this.glShaderProgram6.compute2("u_time");
         this.DynamicButtonSetting = this.glShaderProgram6.compute2("u_progress");
         this.intValue84 = this.glShaderProgram6.compute2("u_color");
         this.intValue85 = this.glShaderProgram6.compute2("u_borderColor");
         this.intValue86 = this.glShaderProgram6.compute2("u_emissiveColor");
         this.intValue87 = this.glShaderProgram6.compute2("u_radius");
         this.SpacerSetting = this.glShaderProgram6.compute2("u_alpha");
         this.FoundryShaderSetting = this.glShaderProgram6.compute2("u_clipRect");
         this.intValue88 = this.glShaderProgram6.compute2("u_clipRadii");
      }
   }

   public RenderEngine.RenderEngineBounds2 resolve2(int i, int j) {
      return this.resolve5(this.renderEngineState, i, j, false);
   }

   public RenderEngine.RenderEngineBounds2 resolve3(int i, int j) {
      if (i > 0 && j > 0 && this.intValue5 > 0 && this.intValue6 > 0) {
         int intValue7 = this.intValue47;
         RenderEngine.RenderEngineState renderEngineState = this.resolve4(intValue7);
         this.intValue47++;

         try {
            RenderEngine.RenderEngineBounds2 renderEngineBounds2 = this.resolve5(renderEngineState, i, j, true);
            if (renderEngineBounds2 == null) {
               this.intValue47 = intValue7;
            }

            return renderEngineBounds2;
         } catch (Error | RuntimeException exception) {
            this.intValue47 = intValue7;
            throw exception;
         }
      } else {
         return null;
      }
   }

   private RenderEngine.RenderEngineState resolve4(int i) {
      while (this.items.size() <= i) {
         this.items.add(new RenderEngine.RenderEngineState());
      }

      return this.items.get(i);
   }

   private RenderEngine.RenderEngineBounds2 resolve5(RenderEngine.RenderEngineState renderEngineState2, int i, int j, boolean bl) {
      this.invoke19();
      if (i > 0 && j > 0 && this.intValue5 > 0 && this.intValue6 > 0) {
         int intValue8 = i;
         int intValue9 = j;
         FramebufferUtils.GlStateSnapshot glStateSnapshot = FramebufferUtils.captureGlState();

         try {
            this.invoke50(renderEngineState2, intValue8, intValue9);
            RenderEngine.RenderEngineBounds2 renderEngineBounds22 = new RenderEngine.RenderEngineBounds2(
               renderEngineState2.intValue2,
               intValue8,
               intValue9,
               glStateSnapshot,
               this.intValue5,
               this.intValue6,
               this.flag5,
               this.intValue9,
               this.intValue10,
               this.intValue11,
               this.intValue12,
               this.floatValue,
               this.floatValue2,
               this.floatValue3,
               this.floatValue4,
               this.flag7,
               bl
            );
            GL30.glBindFramebuffer(36160, renderEngineState2.intValue);
            GL11.glDrawBuffer(36064);
            GL11.glViewport(0, 0, Math.max(0, intValue8), Math.max(0, intValue9));
            GL11.glDisable(3089);
            GL11.glDisable(2929);
            GL11.glDisable(2884);
            GL11.glDisable(36281);
            GL11.glColorMask(true, true, true, true);
            GL11.glDepthMask(false);
            GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
            GL11.glClear(16384);
            this.intValue5 = intValue8;
            this.intValue6 = intValue9;
            this.flag5 = false;
            this.intValue9 = 0;
            this.intValue10 = 0;
            this.intValue11 = intValue8;
            this.intValue12 = intValue9;
            this.floatValue = 0.0F;
            this.floatValue2 = 0.0F;
            this.floatValue3 = 0.0F;
            this.floatValue4 = 0.0F;
            this.flag7 = false;
            this.glShaderProgram.invoke();
            GL30.glBindVertexArray(this.intValue);
            if (this.intValue14 == -1) {
               this.intValue14 = this.glShaderProgram.compute2("uViewport");
            }

            GL20.glUniform2f(this.intValue14, intValue8, intValue9);
            this.invoke21();
            this.invoke3();
            return renderEngineBounds22;
         } catch (Error | RuntimeException exception2) {
            FramebufferUtils.restoreGlState(glStateSnapshot);
            this.glShaderProgram.invoke();
            GL30.glBindVertexArray(this.intValue);
            if (this.intValue14 == -1) {
               this.intValue14 = this.glShaderProgram.compute2("uViewport");
            }

            GL20.glUniform2f(this.intValue14, this.intValue5, this.intValue6);
            this.invoke21();
            throw exception2;
         }
      } else {
         return null;
      }
   }

   public void invoke9(RenderEngine.RenderEngineBounds2 renderEngineBounds23) {
      this.invoke19();
      if (renderEngineBounds23 != null) {
         this.intValue5 = renderEngineBounds23.previousViewportWidth();
         this.intValue6 = renderEngineBounds23.previousViewportHeight();
         this.flag5 = renderEngineBounds23.previousClipEnabled();
         this.intValue9 = renderEngineBounds23.previousClipX();
         this.intValue10 = renderEngineBounds23.previousClipY();
         this.intValue11 = renderEngineBounds23.previousClipW();
         this.intValue12 = renderEngineBounds23.previousClipH();
         this.floatValue = renderEngineBounds23.previousClipRoundTL();
         this.floatValue2 = renderEngineBounds23.previousClipRoundTR();
         this.floatValue3 = renderEngineBounds23.previousClipRoundBR();
         this.floatValue4 = renderEngineBounds23.previousClipRoundBL();
         this.flag7 = renderEngineBounds23.previousAdditiveBlend();
         FramebufferUtils.restoreGlState(renderEngineBounds23.snapshot());
         this.glShaderProgram.invoke();
         GL30.glBindVertexArray(this.intValue);
         if (this.intValue14 == -1) {
            this.intValue14 = this.glShaderProgram.compute2("uViewport");
         }

         GL20.glUniform2f(this.intValue14, this.intValue5, this.intValue6);
         this.invoke21();
         this.invoke3();
         if (renderEngineBounds23.cardTransition()) {
            this.intValue47 = Math.max(0, this.intValue47 - 1);
         }
      }
   }

   public void invoke10(
      int i,
      int j,
      int k,
      float f,
      float g,
      float h,
      float l,
      float m,
      float n,
      float o,
      float p,
      float q,
      float r,
      float s,
      float t,
      float u,
      float v,
      float w,
      float[] fs,
      int x,
      int y,
      int z,
      int aa,
      float ab,
      float ac,
      float ad,
      float ae
   ) {
      if (i > 0 && j > 0 && k > 0 && !(h <= 0.0F) && !(l <= 0.0F)) {
         this.invoke19();
         float[] floatValues3 = fs != null && fs.length >= 6 ? fs : FLOATS;
         float floatValue = f + h;
         float floatValue2 = g + l;
         float floatValue3 = measure(floatValues3, f, g);
         float floatValue4 = measure2(floatValues3, f, g);
         float floatValue5 = measure(floatValues3, floatValue, g);
         float floatValue6 = measure2(floatValues3, floatValue, g);
         float floatValue7 = measure(floatValues3, floatValue, floatValue2);
         float floatValue8 = measure2(floatValues3, floatValue, floatValue2);
         float floatValue9 = measure(floatValues3, f, floatValue2);
         float floatValue10 = measure2(floatValues3, f, floatValue2);
         float[] floatValues4 = new float[]{
            floatValue3,
            floatValue4,
            0.0F,
            1.0F,
            floatValue5,
            floatValue6,
            1.0F,
            1.0F,
            floatValue7,
            floatValue8,
            1.0F,
            0.0F,
            floatValue3,
            floatValue4,
            0.0F,
            1.0F,
            floatValue7,
            floatValue8,
            1.0F,
            0.0F,
            floatValue9,
            floatValue10,
            0.0F,
            0.0F
         };
         FramebufferUtils.GlStateSnapshot glStateSnapshot2 = FramebufferUtils.captureGlState();
         boolean flag7 = false ;

         try {
            flag7 = true;
            this.invoke5();
            GL11.glDisable(3089);
            GL11.glDisable(2929);
            GL11.glDisable(2884);
            GL11.glDisable(36281);
            GL11.glEnable(3042);
            if (this.flag7) {
               GL14.glBlendFuncSeparate(1, 1, 1, 771);
            } else {
               GL14.glBlendFuncSeparate(1, 771, 1, 771);
            }

            this.glShaderProgram3.invoke();
            if (this.intValue33 >= 0) {
               GL20.glUniform1i(this.intValue33, 0);
            }

            if (this.intValue34 >= 0) {
               GL20.glUniform2f(this.intValue34, this.intValue5, this.intValue6);
            }

            if (this.intValue35 >= 0) {
               GL20.glUniform2f(this.intValue35, h, l);
            }

            if (this.intValue36 >= 0) {
               GL20.glUniform2f(this.intValue36, j, k);
            }

            if (this.intValue37 >= 0) {
               GL20.glUniform4f(this.intValue37, m, n, o, p);
            }

            if (this.intValue38 >= 0) {
               GL20.glUniform4f(this.intValue38, x, y, z, aa);
            }

            if (this.intValue39 >= 0) {
               GL20.glUniform4f(this.intValue39, ab, ac, ad, ae);
            }

            if (this.intValue40 >= 0) {
               GL20.glUniform1f(this.intValue40, Math.max(0.0F, q));
            }

            if (this.intValue41 >= 0) {
               GL20.glUniform1f(this.intValue41, Math.max(0.0F, r));
            }

            if (this.intValue42 >= 0) {
               GL20.glUniform1f(this.intValue42, Math.max(0.0F, s));
            }

            if (this.intValue43 >= 0) {
               GL20.glUniform1f(this.intValue43, Math.max(0.0F, Math.min(1.0F, t)));
            }

            if (this.intValue44 >= 0) {
               GL20.glUniform1f(this.intValue44, Math.max(0.0F, Math.min(1.0F, u)));
            }

            if (this.intValue45 >= 0) {
               GL20.glUniform1f(this.intValue45, v < 0.0F ? -1.0F : 1.0F);
            }

            if (this.intValue46 >= 0) {
               GL20.glUniform1f(this.intValue46, Math.max(0.0F, Math.min(1.0F, w)));
            }

            GL13.glActiveTexture(33984);
            GL11.glBindTexture(3553, i);
            GL30.glBindVertexArray(this.intValue31);
            GL15.glBindBuffer(34962, this.intValue32);
            GL15.glBufferData(34962, floatValues4, 35040);
            GlStateGuard.getINSTANCE().invoke2(2);
            GL11.glDrawArrays(4, 0, 6);
            flag7 = false;
         } finally {
            if (flag7) {
               GL30.glBindVertexArray(0);
               GL15.glBindBuffer(34962, 0);
               GL20.glUseProgram(0);
               FramebufferUtils.restoreGlState(glStateSnapshot2);
               this.glShaderProgram.invoke();
               GL30.glBindVertexArray(this.intValue);
               if (this.intValue14 == -1) {
                  this.intValue14 = this.glShaderProgram.compute2("uViewport");
               }

               GL20.glUniform2f(this.intValue14, this.intValue5, this.intValue6);
               this.invoke21();
            }
         }

         GL30.glBindVertexArray(0);
         GL15.glBindBuffer(34962, 0);
         GL20.glUseProgram(0);
         FramebufferUtils.restoreGlState(glStateSnapshot2);
         this.glShaderProgram.invoke();
         GL30.glBindVertexArray(this.intValue);
         if (this.intValue14 == -1) {
            this.intValue14 = this.glShaderProgram.compute2("uViewport");
         }

         GL20.glUniform2f(this.intValue14, this.intValue5, this.intValue6);
         this.invoke21();
      }
   }

   public void invoke11(
      int i,
      int j,
      int k,
      float f,
      float g,
      float h,
      float l,
      float m,
      int n,
      int o,
      int p,
      int q,
      float r,
      float s,
      float t,
      float[] fs,
      int u,
      int v,
      int w,
      int x,
      float y,
      float z,
      float aa,
      float ab
   ) {
      if (i > 0 && j > 0 && k > 0 && !(h <= 0.0F) && !(l <= 0.0F)) {
         this.invoke19();
         float[] floatValues5 = fs != null && fs.length >= 6 ? fs : FLOATS;
         float floatValue11 = f + h;
         float floatValue12 = g + l;
         float floatValue13 = measure(floatValues5, f, g);
         float floatValue14 = measure2(floatValues5, f, g);
         float floatValue15 = measure(floatValues5, floatValue11, g);
         float floatValue16 = measure2(floatValues5, floatValue11, g);
         float floatValue17 = measure(floatValues5, floatValue11, floatValue12);
         float floatValue18 = measure2(floatValues5, floatValue11, floatValue12);
         float floatValue19 = measure(floatValues5, f, floatValue12);
         float floatValue20 = measure2(floatValues5, f, floatValue12);
         float[] floatValues6 = new float[]{
            floatValue13,
            floatValue14,
            0.0F,
            1.0F,
            floatValue15,
            floatValue16,
            1.0F,
            1.0F,
            floatValue17,
            floatValue18,
            1.0F,
            0.0F,
            floatValue13,
            floatValue14,
            0.0F,
            1.0F,
            floatValue17,
            floatValue18,
            1.0F,
            0.0F,
            floatValue19,
            floatValue20,
            0.0F,
            0.0F
         };
         FramebufferUtils.GlStateSnapshot glStateSnapshot3 = FramebufferUtils.captureGlState();
         boolean flag8 = false ;

         try {
            flag8 = true;
            this.invoke6();
            GL11.glDisable(3089);
            GL11.glDisable(2929);
            GL11.glDisable(2884);
            GL11.glDisable(36281);
            GL11.glEnable(3042);
            if (this.flag7) {
               GL14.glBlendFuncSeparate(1, 1, 1, 771);
            } else {
               GL14.glBlendFuncSeparate(1, 771, 1, 771);
            }

            this.glShaderProgram4.invoke();
            if (this.intValue50 >= 0) {
               GL20.glUniform1i(this.intValue50, 0);
            }

            if (this.intValue51 >= 0) {
               GL20.glUniform2f(this.intValue51, this.intValue5, this.intValue6);
            }

            if (this.intValue52 >= 0) {
               GL20.glUniform2f(this.intValue52, h, l);
            }

            if (this.intValue53 >= 0) {
               GL20.glUniform1f(this.intValue53, s);
            }

            if (this.intValue54 >= 0) {
               GL20.glUniform1f(this.intValue54, Math.max(0.0F, Math.min(1.0F, r)));
            }

            if (this.intValue55 >= 0) {
               invoke18(this.intValue55, n);
            }

            if (this.intValue56 >= 0) {
               invoke18(this.intValue56, o);
            }

            if (this.intValue57 >= 0) {
               invoke18(this.intValue57, p);
            }

            if (this.intValue58 >= 0) {
               invoke18(this.intValue58, q);
            }

            if (this.intValue59 >= 0) {
               GL20.glUniform1f(this.intValue59, Math.max(0.0F, m));
            }

            if (this.intValue60 >= 0) {
               GL20.glUniform1f(this.intValue60, Math.max(0.0F, Math.min(1.0F, t)));
            }

            if (this.intValue61 >= 0) {
               GL20.glUniform4f(this.intValue61, u, v, w, x);
            }

            if (this.intValue62 >= 0) {
               GL20.glUniform4f(this.intValue62, y, z, aa, ab);
            }

            GL13.glActiveTexture(33984);
            GL11.glBindTexture(3553, i);
            GL30.glBindVertexArray(this.intValue48);
            GL15.glBindBuffer(34962, this.intValue49);
            GL15.glBufferData(34962, floatValues6, 35040);
            GlStateGuard.getINSTANCE().invoke2(2);
            GL11.glDrawArrays(4, 0, 6);
            flag8 = false;
         } finally {
            if (flag8) {
               GL30.glBindVertexArray(0);
               GL15.glBindBuffer(34962, 0);
               GL20.glUseProgram(0);
               FramebufferUtils.restoreGlState(glStateSnapshot3);
               this.glShaderProgram.invoke();
               GL30.glBindVertexArray(this.intValue);
               if (this.intValue14 == -1) {
                  this.intValue14 = this.glShaderProgram.compute2("uViewport");
               }

               GL20.glUniform2f(this.intValue14, this.intValue5, this.intValue6);
               this.invoke21();
            }
         }

         GL30.glBindVertexArray(0);
         GL15.glBindBuffer(34962, 0);
         GL20.glUseProgram(0);
         FramebufferUtils.restoreGlState(glStateSnapshot3);
         this.glShaderProgram.invoke();
         GL30.glBindVertexArray(this.intValue);
         if (this.intValue14 == -1) {
            this.intValue14 = this.glShaderProgram.compute2("uViewport");
         }

         GL20.glUniform2f(this.intValue14, this.intValue5, this.intValue6);
         this.invoke21();
      }
   }

   public void invoke12(
      int i,
      int j,
      int k,
      float f,
      float g,
      float h,
      float l,
      float m,
      int n,
      int o,
      int p,
      int q,
      float r,
      float s,
      float t,
      float u,
      float v,
      float w,
      float x,
      float[] fs,
      int y,
      int z,
      int aa,
      int ab,
      float ac,
      float ad,
      float ae,
      float af
   ) {
      if (i > 0 && j > 0 && k > 0 && !(h <= 0.0F) && !(l <= 0.0F)) {
         this.invoke19();
         float[] floatValues7 = fs != null && fs.length >= 6 ? fs : FLOATS;
         float floatValue21 = f + h;
         float floatValue22 = g + l;
         float floatValue23 = measure(floatValues7, f, g);
         float floatValue24 = measure2(floatValues7, f, g);
         float floatValue25 = measure(floatValues7, floatValue21, g);
         float floatValue26 = measure2(floatValues7, floatValue21, g);
         float floatValue27 = measure(floatValues7, floatValue21, floatValue22);
         float floatValue28 = measure2(floatValues7, floatValue21, floatValue22);
         float floatValue29 = measure(floatValues7, f, floatValue22);
         float floatValue30 = measure2(floatValues7, f, floatValue22);
         float[] floatValues8 = new float[]{
            floatValue23,
            floatValue24,
            0.0F,
            1.0F,
            floatValue25,
            floatValue26,
            1.0F,
            1.0F,
            floatValue27,
            floatValue28,
            1.0F,
            0.0F,
            floatValue23,
            floatValue24,
            0.0F,
            1.0F,
            floatValue27,
            floatValue28,
            1.0F,
            0.0F,
            floatValue29,
            floatValue30,
            0.0F,
            0.0F
         };
         FramebufferUtils.GlStateSnapshot glStateSnapshot4 = FramebufferUtils.captureGlState();
         boolean flag9 = false ;

         try {
            flag9 = true;
            this.invoke7();
            GL11.glDisable(3089);
            GL11.glDisable(2929);
            GL11.glDisable(2884);
            GL11.glDisable(36281);
            GL11.glEnable(3042);
            if (this.flag7) {
               GL14.glBlendFuncSeparate(1, 1, 1, 771);
            } else {
               GL14.glBlendFuncSeparate(1, 771, 1, 771);
            }

            this.glShaderProgram5.invoke();
            if (this.intValue63 >= 0) {
               GL20.glUniform1i(this.intValue63, 0);
            }

            if (this.intValue64 >= 0) {
               GL20.glUniform2f(this.intValue64, this.intValue5, this.intValue6);
            }

            if (this.intValue65 >= 0) {
               GL20.glUniform2f(this.intValue65, h, l);
            }

            if (this.intValue66 >= 0) {
               GL20.glUniform1f(this.intValue66, t);
            }

            if (this.intValue67 >= 0) {
               GL20.glUniform1f(this.intValue67, Math.max(0.0F, Math.min(1.0F, r)));
            }

            if (this.intValue68 >= 0) {
               GL20.glUniform1f(this.intValue68, Math.max(0.0F, Math.min(1.0F, s)));
            }

            if (this.intValue69 >= 0) {
               GL20.glUniform1f(this.intValue69, Math.max(0.0F, Math.min(1.0F, u)));
            }

            if (this.intValue70 >= 0) {
               GL20.glUniform1f(this.intValue70, Math.max(0.0F, Math.min(1.0F, v)));
            }

            if (this.intValue71 >= 0) {
               GL20.glUniform1f(this.intValue71, Math.max(0.0F, Math.min(1.0F, w)));
            }

            if (this.intValue72 >= 0) {
               invoke18(this.intValue72, n);
            }

            if (this.intValue73 >= 0) {
               invoke18(this.intValue73, o);
            }

            if (this.intValue74 >= 0) {
               invoke18(this.intValue74, p);
            }

            if (this.intValue75 >= 0) {
               invoke18(this.intValue75, q);
            }

            if (this.intValue76 >= 0) {
               GL20.glUniform1f(this.intValue76, Math.max(0.0F, m));
            }

            if (this.intValue77 >= 0) {
               GL20.glUniform1f(this.intValue77, Math.max(0.0F, Math.min(1.0F, x)));
            }

            if (this.intValue78 >= 0) {
               GL20.glUniform4f(this.intValue78, y, z, aa, ab);
            }

            if (this.intValue79 >= 0) {
               GL20.glUniform4f(this.intValue79, ac, ad, ae, af);
            }

            GL13.glActiveTexture(33984);
            GL11.glBindTexture(3553, i);
            GL30.glBindVertexArray(this.intValue48);
            GL15.glBindBuffer(34962, this.intValue49);
            GL15.glBufferData(34962, floatValues8, 35040);
            GlStateGuard.getINSTANCE().invoke2(2);
            GL11.glDrawArrays(4, 0, 6);
            flag9 = false;
         } finally {
            if (flag9) {
               GL30.glBindVertexArray(0);
               GL15.glBindBuffer(34962, 0);
               GL20.glUseProgram(0);
               FramebufferUtils.restoreGlState(glStateSnapshot4);
               this.glShaderProgram.invoke();
               GL30.glBindVertexArray(this.intValue);
               if (this.intValue14 == -1) {
                  this.intValue14 = this.glShaderProgram.compute2("uViewport");
               }

               GL20.glUniform2f(this.intValue14, this.intValue5, this.intValue6);
               this.invoke21();
            }
         }

         GL30.glBindVertexArray(0);
         GL15.glBindBuffer(34962, 0);
         GL20.glUseProgram(0);
         FramebufferUtils.restoreGlState(glStateSnapshot4);
         this.glShaderProgram.invoke();
         GL30.glBindVertexArray(this.intValue);
         if (this.intValue14 == -1) {
            this.intValue14 = this.glShaderProgram.compute2("uViewport");
         }

         GL20.glUniform2f(this.intValue14, this.intValue5, this.intValue6);
         this.invoke21();
      }
   }

   public void invoke13(
      int i,
      int j,
      int k,
      float f,
      float g,
      float h,
      float l,
      float m,
      int n,
      int o,
      int p,
      float q,
      float r,
      float s,
      float[] fs,
      int t,
      int u,
      int v,
      int w,
      float x,
      float y,
      float z,
      float aa
   ) {
      if (i > 0 && j > 0 && k > 0 && !(h <= 0.0F) && !(l <= 0.0F)) {
         this.invoke19();
         float[] floatValues9 = fs != null && fs.length >= 6 ? fs : FLOATS;
         float floatValue31 = f + h;
         float floatValue32 = g + l;
         float floatValue33 = measure(floatValues9, f, g);
         float floatValue34 = measure2(floatValues9, f, g);
         float floatValue35 = measure(floatValues9, floatValue31, g);
         float floatValue36 = measure2(floatValues9, floatValue31, g);
         float floatValue37 = measure(floatValues9, floatValue31, floatValue32);
         float floatValue38 = measure2(floatValues9, floatValue31, floatValue32);
         float floatValue39 = measure(floatValues9, f, floatValue32);
         float floatValue40 = measure2(floatValues9, f, floatValue32);
         float[] floatValues10 = new float[]{
            floatValue33,
            floatValue34,
            0.0F,
            1.0F,
            floatValue35,
            floatValue36,
            1.0F,
            1.0F,
            floatValue37,
            floatValue38,
            1.0F,
            0.0F,
            floatValue33,
            floatValue34,
            0.0F,
            1.0F,
            floatValue37,
            floatValue38,
            1.0F,
            0.0F,
            floatValue39,
            floatValue40,
            0.0F,
            0.0F
         };
         FramebufferUtils.GlStateSnapshot glStateSnapshot5 = FramebufferUtils.captureGlState();
         boolean flag10 = false ;

         try {
            flag10 = true;
            this.invoke8();
            GL11.glDisable(3089);
            GL11.glDisable(2929);
            GL11.glDisable(2884);
            GL11.glDisable(36281);
            GL11.glEnable(3042);
            if (this.flag7) {
               GL14.glBlendFuncSeparate(1, 1, 1, 771);
            } else {
               GL14.glBlendFuncSeparate(1, 771, 1, 771);
            }

            this.glShaderProgram6.invoke();
            if (this.intValue80 >= 0) {
               GL20.glUniform1i(this.intValue80, 0);
            }

            if (this.intValue81 >= 0) {
               GL20.glUniform2f(this.intValue81, this.intValue5, this.intValue6);
            }

            if (this.intValue82 >= 0) {
               GL20.glUniform2f(this.intValue82, h, l);
            }

            if (this.intValue83 >= 0) {
               GL20.glUniform1f(this.intValue83, r);
            }

            if (this.DynamicButtonSetting >= 0) {
               GL20.glUniform1f(this.DynamicButtonSetting, Math.max(0.0F, Math.min(1.0F, q)));
            }

            if (this.intValue84 >= 0) {
               invoke18(this.intValue84, n);
            }

            if (this.intValue85 >= 0) {
               invoke18(this.intValue85, o);
            }

            if (this.intValue86 >= 0) {
               invoke18(this.intValue86, p);
            }

            if (this.intValue87 >= 0) {
               GL20.glUniform1f(this.intValue87, Math.max(0.0F, m));
            }

            if (this.SpacerSetting >= 0) {
               GL20.glUniform1f(this.SpacerSetting, Math.max(0.0F, Math.min(1.0F, s)));
            }

            if (this.FoundryShaderSetting >= 0) {
               GL20.glUniform4f(this.FoundryShaderSetting, t, u, v, w);
            }

            if (this.intValue88 >= 0) {
               GL20.glUniform4f(this.intValue88, x, y, z, aa);
            }

            GL13.glActiveTexture(33984);
            GL11.glBindTexture(3553, i);
            GL30.glBindVertexArray(this.intValue48);
            GL15.glBindBuffer(34962, this.intValue49);
            GL15.glBufferData(34962, floatValues10, 35040);
            GlStateGuard.getINSTANCE().invoke2(2);
            GL11.glDrawArrays(4, 0, 6);
            flag10 = false;
         } finally {
            if (flag10) {
               GL30.glBindVertexArray(0);
               GL15.glBindBuffer(34962, 0);
               GL20.glUseProgram(0);
               FramebufferUtils.restoreGlState(glStateSnapshot5);
               this.glShaderProgram.invoke();
               GL30.glBindVertexArray(this.intValue);
               if (this.intValue14 == -1) {
                  this.intValue14 = this.glShaderProgram.compute2("uViewport");
               }

               GL20.glUniform2f(this.intValue14, this.intValue5, this.intValue6);
               this.invoke21();
            }
         }

         GL30.glBindVertexArray(0);
         GL15.glBindBuffer(34962, 0);
         GL20.glUseProgram(0);
         FramebufferUtils.restoreGlState(glStateSnapshot5);
         this.glShaderProgram.invoke();
         GL30.glBindVertexArray(this.intValue);
         if (this.intValue14 == -1) {
            this.intValue14 = this.glShaderProgram.compute2("uViewport");
         }

         GL20.glUniform2f(this.intValue14, this.intValue5, this.intValue6);
         this.invoke21();
      }
   }

   public void invoke14(int i, int j) {
      if (i > 0 && j > 0) {
         this.glStateSnapshot = FramebufferUtils.captureGlState();
         this.intValue5 = i;
         this.intValue6 = j;
         this.intValue4 = 0;
         this.byteBuffer.clear();
         this.invoke3();
         this.glShaderProgram.invoke();
         if (this.intValue14 == -1) {
            this.intValue14 = this.glShaderProgram.compute2("uViewport");
         }

         GL30.glBindVertexArray(this.intValue);
         GL20.glUniform2f(this.intValue14, i, j);
         this.intValue92 = 0;
         this.intValue95 = 0;
         this.intValue96 = 0;
         this.intValue93 = 0;
         this.intValue94 = 0;
         this.flag7 = false;
         GL11.glDisable(2929);
         GL11.glDisable(2884);
         GL11.glDisable(3089);
         this.invoke21();
         GL11.glViewport(0, 0, Math.max(0, i), Math.max(0, j));
         GL11.glColorMask(true, true, true, true);
         if (!this.flag6) {
            for (int intValue10 = 0; intValue10 < 16; intValue10++) {
               int intValue11 = this.glShaderProgram.compute2("uTextures[" + intValue10 + "]");
               if (intValue11 != -1) {
                  GL20.glUniform1i(intValue11, intValue10);
               }
            }

            this.flag6 = true;
         }
      } else {
         this.intValue5 = 0;
         this.intValue6 = 0;
         this.intValue4 = 0;
         this.byteBuffer.clear();
         this.invoke3();
         this.invoke63();
      }
   }

   public void invoke15() {
      this.invoke19();
      GL30.glBindVertexArray(0);
      GL20.glUseProgram(0);
      if (this.glStateSnapshot != null) {
         GL20.glUseProgram(this.glStateSnapshot.intValue9);
         GL30.glBindVertexArray(this.glStateSnapshot.intValue10);
         GL15.glBindBuffer(34962, this.glStateSnapshot.intValue11);
         GL15.glBindBuffer(34963, this.glStateSnapshot.intValue12);
         GL13.glActiveTexture(this.glStateSnapshot.intValue13);
         GL11.glBindTexture(3553, this.glStateSnapshot.intValue14);
         GL11.glPixelStorei(3317, this.glStateSnapshot.intValue15);
         invoke17(3089, this.glStateSnapshot.flag);
         invoke17(2929, this.glStateSnapshot.flag2);
         invoke17(2884, this.glStateSnapshot.flag3);
         invoke17(3042, this.glStateSnapshot.flag4);
         invoke17(36281, this.glStateSnapshot.flag5);
         GL14.glBlendFuncSeparate(
            this.glStateSnapshot.intValue5, this.glStateSnapshot.intValue6, this.glStateSnapshot.intValue7, this.glStateSnapshot.intValue8
         );
         GL11.glColorMask(this.glStateSnapshot.flag6, this.glStateSnapshot.flag7, this.glStateSnapshot.flag8, this.glStateSnapshot.flag9);
         GL11.glDepthMask(this.glStateSnapshot.flag10);
         GL11.glViewport(
            this.glStateSnapshot.ints[0], this.glStateSnapshot.ints[1], this.glStateSnapshot.ints[2], this.glStateSnapshot.ints[3]
         );
         GL11.glScissor(
            this.glStateSnapshot.ints2[0],
            this.glStateSnapshot.ints2[1],
            this.glStateSnapshot.ints2[2],
            this.glStateSnapshot.ints2[3]
         );
      }

      this.glStateSnapshot = null;
      this.intValue4 = 0;
      this.byteBuffer.clear();
   }

   private void invoke16(int i, int j) {
      if (this.flag3) {
         ARBInstancedArrays.glVertexAttribDivisorARB(i, j);
      } else {
         GL33.glVertexAttribDivisor(i, j);
      }
   }

   private static void invoke17(int i, boolean bl) {
      if (bl) {
         GL11.glEnable(i);
      } else {
         GL11.glDisable(i);
      }
   }

   private static void invoke18(int i, int j) {
      float floatValue41 = (j >>> 24 & 0xFF) / 255.0F;
      float floatValue42 = (j >>> 16 & 0xFF) / 255.0F;
      float floatValue43 = (j >>> 8 & 0xFF) / 255.0F;
      float floatValue44 = (j & 0xFF) / 255.0F;
      GL20.glUniform4f(i, floatValue42, floatValue43, floatValue44, floatValue41);
   }

   public void invoke19() {
      if (this.intValue4 > 0) {
         if (this.intValue5 > 0 && this.intValue6 > 0) {
            this.byteBuffer.limit(this.intValue4 * 144);
            this.byteBuffer.position(0);
            int intValue12 = GL11.glGetInteger(34229);
            int intValue13 = GL11.glGetInteger(35725);
            GL30.glBindVertexArray(this.intValue);
            this.glShaderProgram.invoke();
            GL20.glUniform2f(this.intValue14, this.intValue5, this.intValue6);
            GL11.glViewport(0, 0, this.intValue5, this.intValue6);
            this.invoke21();
            GL11.glDisable(2929);
            GL11.glDisable(2884);
            GL11.glColorMask(true, true, true, true);
            if (this.flag) {
               GL15.glBindBuffer(37074, this.intValue2);
               GL15.glBufferSubData(37074, 0L, this.byteBuffer);
               GL43.glBindBufferBase(37074, 0, this.intValue2);
            } else {
               GL15.glBindBuffer(34962, this.intValue3);
               GL15.glBufferSubData(34962, 0L, this.byteBuffer);
               GL15.glBindBuffer(34962, 0);
            }

            int intValue14 = GL11.glGetInteger(34016);
            int intValue15 = this.intValue13;

            for (int intValue16 = 0; intValue16 < intValue15; intValue16++) {
               GL13.glActiveTexture(33984 + intValue16);
               this.ints2[intValue16] = GL11.glGetInteger(32873);
               int intValue17 = this.ints[intValue16];
               GL11.glBindTexture(3553, intValue17);
            }

            int intValue18 = Math.max(0, this.intValue4) * 2;
            if (intValue18 > 0) {
               GlStateGuard.getINSTANCE().invoke2(intValue18);
            }

            if (this.flag) {
               GL11.glDrawArrays(4, 0, this.intValue4 * 6);
            } else if (this.flag4) {
               ARBDrawInstanced.glDrawArraysInstancedARB(4, 0, 6, this.intValue4);
            } else {
               GL31.glDrawArraysInstanced(4, 0, 6, this.intValue4);
            }

            for (int intValue19 = 0; intValue19 < intValue15; intValue19++) {
               GL13.glActiveTexture(33984 + intValue19);
               GL11.glBindTexture(3553, this.ints2[intValue19]);
            }

            GL13.glActiveTexture(intValue14);
            GL30.glBindVertexArray(intValue12);
            GL20.glUseProgram(intValue13);
            this.intValue4 = 0;
            this.byteBuffer.clear();
            this.invoke3();
         } else {
            this.intValue4 = 0;
            this.byteBuffer.clear();
            this.invoke3();
         }
      }
   }

   public void setFlag7(boolean bl) {
      this.flag7 = bl;
   }

   public void invoke20() {
      this.invoke21();
   }

   private void invoke21() {
      GL11.glEnable(3042);
      if (this.flag7) {
         GL14.glBlendFuncSeparate(1, 1, 1, 771);
      } else {
         GL14.glBlendFuncSeparate(1, 771, 1, 771);
      }
   }

   public void setFlag5(boolean bl) {
      this.flag5 = bl;
      if (!bl) {
         this.floatValue = 0.0F;
         this.floatValue2 = 0.0F;
         this.floatValue3 = 0.0F;
         this.floatValue4 = 0.0F;
      }
   }

   public void invoke22(int i, int j, int k, int l, float f, float g, float h, float m) {
      this.intValue9 = i;
      this.intValue10 = j;
      this.intValue11 = k;
      this.intValue12 = l;
      this.floatValue = f;
      this.floatValue2 = g;
      this.floatValue3 = h;
      this.floatValue4 = m;
   }

   public void invoke23(float[] fs) {
   }

   public void invoke24(float f, float g) {
      if (!Float.isFinite(f) || !Float.isFinite(g)) {
         throw new IllegalArgumentException("Blur capture scale must be finite");
      } else if (!(f <= 0.0F) && !(g <= 0.0F)) {
         this.floatValue5 = f;
         this.floatValue6 = g;
      } else {
         throw new IllegalArgumentException("Blur capture scale must be positive");
      }
   }

   private void invoke25(
      int i,
      float f,
      float g,
      float h,
      float j,
      int k,
      int l,
      int m,
      int n,
      float o,
      float p,
      float q,
      float r,
      float s,
      float[] fs,
      float t,
      float u,
      float v,
      float w,
      int x,
      float y,
      float z,
      int aa
   ) {
      if (this.intValue4 >= 4096) {
         throw new IllegalStateException("Instance capacity exceeded without prior ensureInstanceCapacity call");
      } else {
         int intValue20 = this.intValue4 * 144;
         this.byteBuffer.position(intValue20);
         invoke26(this.byteBuffer, fs, f, g, h, j);
         int intValue21 = this.flag5 ? this.intValue9 : 0;
         int intValue22 = this.flag5 ? this.intValue10 : 0;
         int intValue23 = this.flag5 ? this.intValue11 : this.intValue5;
         int intValue24 = this.flag5 ? this.intValue12 : this.intValue6;
         float floatValue45 = this.flag5 ? this.floatValue : 0.0F;
         float floatValue46 = this.flag5 ? this.floatValue2 : 0.0F;
         float floatValue47 = this.flag5 ? this.floatValue3 : 0.0F;
         float floatValue48 = this.flag5 ? this.floatValue4 : 0.0F;
         this.byteBuffer.putInt(intValue21);
         this.byteBuffer.putInt(intValue22);
         this.byteBuffer.putInt(intValue23);
         this.byteBuffer.putInt(intValue24);
         this.byteBuffer.putFloat(floatValue45);
         this.byteBuffer.putFloat(floatValue46);
         this.byteBuffer.putFloat(floatValue47);
         this.byteBuffer.putFloat(floatValue48);
         this.byteBuffer.putFloat(f);
         this.byteBuffer.putFloat(g);
         this.byteBuffer.putFloat(h);
         this.byteBuffer.putFloat(j);
         this.byteBuffer.putInt(compute(k));
         this.byteBuffer.putInt(compute(l));
         this.byteBuffer.putInt(compute(m));
         this.byteBuffer.putInt(compute(n));
         float floatValue49 = measure3(o);
         float floatValue50 = measure3(p);
         float floatValue51 = measure3(q);
         float floatValue52 = measure3(r);
         this.byteBuffer.putFloat(floatValue49);
         this.byteBuffer.putFloat(floatValue50);
         this.byteBuffer.putFloat(floatValue51);
         this.byteBuffer.putFloat(floatValue52);
         this.byteBuffer.putFloat(t);
         this.byteBuffer.putFloat(u);
         this.byteBuffer.putFloat(v);
         this.byteBuffer.putFloat(w);
         int intValue25 = i;
         if (i == 1 || i == 2) {
            int intValue26 = Math.max(0, Math.min(255, Math.round(s)));
            intValue25 = i | intValue26 << 2;
         }

         if (i == 2) {
            float floatValue53 = y % 360.0F;
            if (floatValue53 < 0.0F) {
               floatValue53 += 360.0F;
            }

            int intValue27 = Math.max(0, Math.min(255, Math.round(floatValue53 / 360.0F * 255.0F)));
            float floatValue54 = Math.max(0.0F, Math.min(1.0F, z));
            int intValue28 = Math.max(0, Math.min(255, Math.round(floatValue54 * 255.0F)));
            intValue25 |= intValue27 << 10;
            intValue25 |= intValue28 << 18;
         }

         if (i == 3 && s > 0.0F) {
            intValue25 |= 4;
         }

         intValue25 |= aa;
         this.byteBuffer.putInt(intValue25);
         this.byteBuffer.putInt(x);
         this.byteBuffer.putInt(0);
         this.byteBuffer.putInt(0);
         this.intValue4++;
      }
   }

   private static void invoke26(ByteBuffer byteBuffer, float[] fs, float f, float g, float h, float i) {
      float[] floatValues11 = fs != null && fs.length >= 6 ? fs : FLOATS;
      float floatValue55 = f + h;
      float floatValue56 = g + i;
      invoke27(byteBuffer, floatValues11, f, g);
      invoke27(byteBuffer, floatValues11, floatValue55, g);
      invoke27(byteBuffer, floatValues11, floatValue55, floatValue56);
      invoke27(byteBuffer, floatValues11, f, floatValue56);
   }

   private static void invoke27(ByteBuffer byteBuffer, float[] fs, float f, float g) {
      float floatValue57 = fs[0] * f + fs[1] * g + fs[2];
      float floatValue58 = fs[3] * f + fs[4] * g + fs[5];
      byteBuffer.putFloat(floatValue57);
      byteBuffer.putFloat(floatValue58);
   }

   private static float measure(float[] fs, float f, float g) {
      return fs[0] * f + fs[1] * g + fs[2];
   }

   private static float measure2(float[] fs, float f, float g) {
      return fs[3] * f + fs[4] * g + fs[5];
   }

   private static float measure3(float f) {
      if (!Float.isFinite(f)) {
         return 0.0F;
      } else {
         return f <= 0.0F ? 0.0F : f;
      }
   }

   private void invoke28(
      int i, float f, float g, float h, float j, int k, float l, float m, float[] fs, float n, float o, float p, float q, int r, float s, float t
   ) {
      this.invoke25(i, f, g, h, j, k, k, k, k, l, l, l, l, m, fs, n, o, p, q, r, s, t, 0);
   }

   public void invoke29(float f, float g, float h, float i, float j, float k, float l, float m, int n, float[] fs) {
      this.invoke();
      this.invoke25(0, f, g, h, i, n, n, n, n, j, k, l, m, 0.0F, fs, 0.0F, 0.0F, 1.0F, 1.0F, -1, 0.0F, 1.0F, 0);
   }

   public void invoke30(float f, float g, float h, float i, float j, float k, float l, float m, int n, float o, float[] fs) {
      this.invoke();
      this.invoke25(1, f, g, h, i, n, n, n, n, j, k, l, m, o, fs, 0.0F, 0.0F, 1.0F, 1.0F, -1, 0.0F, 1.0F, 0);
   }

   public void invoke31(float f, float g, float h, float i, float j, float k, float l, float m, int n, int o, int p, int q, float[] fs) {
      this.invoke();
      this.invoke25(0, f, g, h, i, n, o, p, q, j, k, l, m, 0.0F, fs, 0.0F, 0.0F, 1.0F, 1.0F, -1, 0.0F, 1.0F, 0);
   }

   public void invoke32(float f, float g, float h, float i, float j, int k, float[] fs) {
      this.invoke33(f, g, h, i, j, 0.0F, k, fs);
   }

   public void invoke33(float f, float g, float h, float i, float j, float k, int l, float[] fs) {
      float floatValue59 = h * 2.0F;
      this.invoke();
      this.invoke28(2, f - h, g - h, floatValue59, floatValue59, l, 0.0F, k, fs, 0.0F, 0.0F, 1.0F, 1.0F, -1, i, j);
   }

   public void invoke34(float f, float g, float h, float i, float j, float k, float l, float m, float n, float o, int p, float[] fs) {
      if (!(h <= 0.0F) && !(i <= 0.0F)) {
         float floatValue60 = n > 0.0F ? n : 0.0F;
         float floatValue61 = o > 0.0F ? o : 0.0F;
         float floatValue62 = floatValue61 + floatValue60 * 3.0F;
         float floatValue63 = f - floatValue62;
         float floatValue64 = g - floatValue62;
         float floatValue65 = h + floatValue62 * 2.0F;
         float floatValue66 = i + floatValue62 * 2.0F;
         if (!(floatValue65 <= 0.0F) && !(floatValue66 <= 0.0F)) {
            this.invoke();
            this.invoke25(0, floatValue63, floatValue64, floatValue65, floatValue66, p, p, p, p, j, k, l, m, 0.0F, fs, h, i, Math.max(floatValue60, 0.001F), floatValue61, 0, 0.0F, 1.0F, 67108864);
         }
      }
   }

   public void invoke35(int i, float f, float g, float h, float j, float k, float l, float m, float n, int o, float[] fs) {
      this.invoke();
      int intValue29 = this.compute2(i);
      this.invoke28(3, f, g, h, j, o, 0.0F, 0.0F, fs, k, l, m, n, intValue29, 0.0F, 1.0F);
   }

   public void invoke36(int i, float f, float g, float h, float j, float k, float l, float m, float n, float o, int p, float[] fs) {
      this.invoke();
      int intValue30 = this.compute2(i);
      this.invoke28(3, f, g, h, j, p, o, 0.0F, fs, k, l, m, n, intValue30, 0.0F, 1.0F);
   }

   public void invoke37(int i, float f, float g, float h, float j, float k, float l, float m, float n, int o, float[] fs) {
      this.invoke38(i, f, g, h, j, k, l, m, n, o, fs, false);
   }

   public void invoke38(int i, float f, float g, float h, float j, float k, float l, float m, float n, int o, float[] fs, boolean bl) {
      this.invoke();
      int intValue31 = this.compute2(i);
      int intValue32 = bl ? 64 : 0;
      this.invoke25(3, f, g, h, j, o, o, o, o, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, fs, k, l, m, n, intValue31, 0.0F, 1.0F, intValue32);
   }

   public void invoke39(int i, float f, float g, float h, float j, float k, float l, float m, float n, float o, int p, float[] fs) {
      this.invoke40(i, f, g, h, j, k, l, m, n, o, p, fs, false);
   }

   public void invoke40(int i, float f, float g, float h, float j, float k, float l, float m, float n, float o, int p, float[] fs, boolean bl) {
      this.invoke();
      int intValue33 = this.compute2(i);
      int intValue34 = bl ? 64 : 0;
      this.invoke25(3, f, g, h, j, p, p, p, p, o, o, o, o, 1.0F, fs, k, l, m, n, intValue33, 0.0F, 1.0F, intValue34);
   }

   public void invoke41(int i, float f, float g, float h, float j, float k, float l, float m, float n, float o, int p, float[] fs) {
      this.invoke();
      this.invoke42(i, f, g, h, j, k, l, m, n, o, p, fs, false);
   }

   public void invoke42(int i, float f, float g, float h, float j, float k, float l, float m, float n, float o, int p, float[] fs, boolean bl) {
      this.invoke43(i, f, g, h, j, k, l, m, n, o, o, o, o, p, fs, bl);
   }

   public void invoke43(
      int i, float f, float g, float h, float j, float k, float l, float m, float n, float o, float p, float q, float r, int s, float[] fs, boolean bl
   ) {
      this.invoke();
      int intValue35 = this.compute2(i);
      byte byteValue4 = 8;
      if (bl) {
         byteValue4 |= 32;
      }

      this.invoke25(3, f, g, h, j, s, s, s, s, o, p, q, r, 1.0F, fs, k, l, m, n, intValue35, 0.0F, 1.0F, byteValue4);
   }

   public void invoke44(int i, float f, float g, float h, float j, float k, float l, float m, float n, int o, float[] fs) {
      this.invoke();
      int intValue36 = this.compute2(i);
      byte byteValue5 = 8;
      this.invoke25(3, f, g, h, j, o, o, o, o, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, fs, k, l, m, n, intValue36, 0.0F, 1.0F, byteValue5);
   }

   public void invoke45(int i, float f, float g, float h, float j, float k, float l, float m, float n, float o, int p, float[] fs) {
      if (i > 0) {
         this.invoke();
         int intValue37 = this.compute2(i);
         float floatValue67 = f > 0.0F ? f : 0.001F;
         this.invoke25(3, g, h, j, k, p, p, p, p, floatValue67, floatValue67, floatValue67, floatValue67, 0.0F, fs, l, m, n, o, intValue37, 0.0F, 1.0F, 16);
      }
   }

   private int compute2(int i) {
      int intValue38 = this.int2IntOpenHashMap.get(i);
      if (intValue38 >= 0) {
         return intValue38;
      } else {
         if (this.intValue13 >= 16) {
            this.invoke19();
            this.invoke3();
         }

         int intValue39 = this.intValue13++;
         this.ints[intValue39] = i;
         this.int2IntOpenHashMap.put(i, intValue39);
         return intValue39;
      }
   }

   public void invoke46(ByteBuffer byteBuffer, int i) {
   }

   public int compute3(int i, int j, ByteBuffer byteBuffer) {
      if (i <= 0 || j <= 0) {
         throw new IllegalArgumentException("Invalid MSDF texture dimensions: " + i + "x" + j);
      } else if (byteBuffer == null) {
         throw new IllegalArgumentException("data");
      } else {
         int intValue40 = GL11.glGetInteger(34016);
         int intValue41 = GL11.glGetInteger(32873);
         int intValue42 = GL11.glGetInteger(3317);
         int intValue43 = GL11.glGetInteger(3314);
         int intValue44 = GL11.glGenTextures();
         boolean flag11 = false ;

         int intValue45;
         try {
            flag11 = true;
            GL13.glActiveTexture(33984);
            GL11.glBindTexture(3553, intValue44);
            GL11.glTexParameteri(3553, 10241, 9729);
            GL11.glTexParameteri(3553, 10240, 9729);
            GL12.glTexParameteri(3553, 33084, 0);
            GL12.glTexParameteri(3553, 33085, 0);
            GL11.glTexParameteri(3553, 10242, 33071);
            GL11.glTexParameteri(3553, 10243, 33071);
            GL11.glPixelStorei(3317, 1);
            GL12.glPixelStorei(3314, 0);
            byteBuffer.rewind();
            GL11.glTexImage2D(3553, 0, 32856, i, j, 0, 6408, 5121, byteBuffer);
            intValue45 = intValue44;
            flag11 = false;
         } finally {
            if (flag11) {
               GL12.glPixelStorei(3314, intValue43);
               GL11.glPixelStorei(3317, intValue42);
               GL11.glBindTexture(3553, intValue41);
               GL13.glActiveTexture(intValue40);
            }
         }

         GL12.glPixelStorei(3314, intValue43);
         GL11.glPixelStorei(3317, intValue42);
         GL11.glBindTexture(3553, intValue41);
         GL13.glActiveTexture(intValue40);
         return intValue45;
      }
   }

   public int compute4(int i, int j) {
      int intValue46 = GL11.glGetInteger(34016);
      int intValue47 = GL11.glGetInteger(32873);
      int intValue48 = GL11.glGetInteger(3317);
      int intValue49 = GL11.glGetInteger(3314);
      int intValue50 = GL11.glGenTextures();
      boolean flag12 = false ;

      int intValue51;
      try {
         flag12 = true;
         GL13.glActiveTexture(33984);
         GL11.glBindTexture(3553, intValue50);
         GL11.glTexParameteri(3553, 10241, 9729);
         GL11.glTexParameteri(3553, 10240, 9729);
         GL12.glTexParameteri(3553, 33084, 0);
         GL12.glTexParameteri(3553, 33085, 0);
         GL11.glTexParameteri(3553, 10242, 33071);
         GL11.glTexParameteri(3553, 10243, 33071);
         GL11.glTexParameteri(3553, 36418, 6403);
         GL11.glTexParameteri(3553, 36419, 6403);
         GL11.glTexParameteri(3553, 36420, 6403);
         GL11.glTexParameteri(3553, 36421, 6403);
         GL11.glPixelStorei(3317, 1);
         GL12.glPixelStorei(3314, 0);
         RenderCapabilities.invoke(33321, i, j, 6403, 5121);
         intValue51 = intValue50;
         flag12 = false;
      } finally {
         if (flag12) {
            GL12.glPixelStorei(3314, intValue49);
            GL11.glPixelStorei(3317, intValue48);
            GL11.glBindTexture(3553, intValue47);
            GL13.glActiveTexture(intValue46);
         }
      }

      GL12.glPixelStorei(3314, intValue49);
      GL11.glPixelStorei(3317, intValue48);
      GL11.glBindTexture(3553, intValue47);
      GL13.glActiveTexture(intValue46);
      return intValue51;
   }

   public void invoke47(int i, int j, int k, int l, int m, ByteBuffer byteBuffer) {
      int intValue52 = GL11.glGetInteger(34016);
      int intValue53 = GL11.glGetInteger(32873);
      int intValue54 = GL11.glGetInteger(3317);
      int intValue55 = GL11.glGetInteger(3314);
      boolean flag13 = false ;

      try {
         flag13 = true;
         byteBuffer.order(ByteOrder.nativeOrder());
         GL13.glActiveTexture(33984);
         GL11.glBindTexture(3553, i);
         GL11.glPixelStorei(3317, 1);
         GL12.glPixelStorei(3314, 0);
         GL11.glTexSubImage2D(3553, 0, j, k, l, m, 6403, 5121, byteBuffer);
         flag13 = false;
      } finally {
         if (flag13) {
            GL12.glPixelStorei(3314, intValue55);
            GL11.glPixelStorei(3317, intValue54);
            GL11.glBindTexture(3553, intValue53);
            GL13.glActiveTexture(intValue52);
         }
      }

      GL12.glPixelStorei(3314, intValue55);
      GL11.glPixelStorei(3317, intValue54);
      GL11.glBindTexture(3553, intValue53);
      GL13.glActiveTexture(intValue52);
   }

   public void invoke48(int i, int j, int k, int l, int m, ByteBuffer byteBuffer, int n) {
      int intValue56 = GL11.glGetInteger(34016);
      int intValue57 = GL11.glGetInteger(32873);
      int intValue58 = GL11.glGetInteger(3317);
      int intValue59 = GL11.glGetInteger(3314);
      boolean flag14 = false ;

      try {
         flag14 = true;
         byteBuffer.order(ByteOrder.nativeOrder());
         GL13.glActiveTexture(33984);
         GL11.glBindTexture(3553, i);
         GL11.glPixelStorei(3317, 1);
         GL12.glPixelStorei(3314, n);
         GL11.glTexSubImage2D(3553, 0, j, k, l, m, 6403, 5121, byteBuffer);
         flag14 = false;
      } finally {
         if (flag14) {
            GL12.glPixelStorei(3314, intValue59);
            GL11.glPixelStorei(3317, intValue58);
            GL11.glBindTexture(3553, intValue57);
            GL13.glActiveTexture(intValue56);
         }
      }

      GL12.glPixelStorei(3314, intValue59);
      GL11.glPixelStorei(3317, intValue58);
      GL11.glBindTexture(3553, intValue57);
      GL13.glActiveTexture(intValue56);
   }

   private void invoke49(int i, int j, boolean bl) {
      if (i > 0 && j > 0) {
         int intValue60 = bl ? this.intValue15 : this.intValue23;
         int intValue61 = bl ? this.intValue18 : this.intValue26;
         int intValue62 = bl ? this.intValue16 : this.intValue24;
         int intValue63 = bl ? this.intValue17 : this.intValue25;
         if (intValue60 == 0 || i != intValue62 || j != intValue63) {
            if (intValue60 != 0) {
               GL11.glDeleteTextures(intValue60);
               boolean flag15 = false;
            }

            if (intValue61 != 0) {
               GL30.glDeleteFramebuffers(intValue61);
               boolean flag16 = false;
            }

            intValue60 = GL11.glGenTextures();
            GL11.glBindTexture(3553, intValue60);
            GL11.glTexParameteri(3553, 10241, 9729);
            GL11.glTexParameteri(3553, 10240, 9729);
            GL11.glTexParameteri(3553, 10242, 33071);
            GL11.glTexParameteri(3553, 10243, 33071);
            RenderCapabilities.invoke(32856, i, j, 6408, 5121);
            GL11.glBindTexture(3553, 0);
            intValue61 = GL30.glGenFramebuffers();
            GL30.glBindFramebuffer(36160, intValue61);
            GL30.glFramebufferTexture2D(36160, 36064, 3553, intValue60, 0);
            GL11.glDrawBuffer(36064);
            int intValue64 = GL30.glCheckFramebufferStatus(36160);
            GL30.glBindFramebuffer(36160, 0);
            if (intValue64 != 36053) {
               GL30.glDeleteFramebuffers(intValue61);
               GL11.glDeleteTextures(intValue60);
               throw new IllegalStateException("Capture FBO incomplete: status=" + intValue64);
            } else {
               if (bl) {
                  this.intValue15 = intValue60;
                  this.intValue18 = intValue61;
                  this.intValue16 = i;
                  this.intValue17 = j;
               } else {
                  this.intValue23 = intValue60;
                  this.intValue26 = intValue61;
                  this.intValue24 = i;
                  this.intValue25 = j;
               }
            }
         }
      } else {
         if (bl) {
            this.invoke64(true);
         } else {
            this.invoke64(false);
         }
      }
   }

   private void invoke50(RenderEngine.RenderEngineState renderEngineState3, int i, int j) {
      if (renderEngineState3 != null) {
         if (i <= 0 || j <= 0) {
            this.invoke51(renderEngineState3);
         } else if (renderEngineState3.intValue2 == 0 || renderEngineState3.intValue == 0 || renderEngineState3.intValue3 != i || renderEngineState3.intValue4 != j) {
            this.invoke51(renderEngineState3);
            renderEngineState3.intValue3 = i;
            renderEngineState3.intValue4 = j;
            renderEngineState3.intValue2 = GL11.glGenTextures();
            GL11.glBindTexture(3553, renderEngineState3.intValue2);
            GL11.glTexParameteri(3553, 10241, 9729);
            GL11.glTexParameteri(3553, 10240, 9729);
            GL11.glTexParameteri(3553, 10242, 33071);
            GL11.glTexParameteri(3553, 10243, 33071);
            RenderCapabilities.invoke(32856, i, j, 6408, 5121);
            GL11.glBindTexture(3553, 0);
            renderEngineState3.intValue = GL30.glGenFramebuffers();
            GL30.glBindFramebuffer(36160, renderEngineState3.intValue);
            GL30.glFramebufferTexture2D(36160, 36064, 3553, renderEngineState3.intValue2, 0);
            GL11.glDrawBuffer(36064);
            GL11.glReadBuffer(36064);
            int intValue65 = GL30.glCheckFramebufferStatus(36160);
            GL30.glBindFramebuffer(36160, 0);
            if (intValue65 != 36053) {
               this.invoke51(renderEngineState3);
               throw new IllegalStateException("Layer framebuffer incomplete: status=" + intValue65);
            }
         }
      }
   }

   private void invoke51(RenderEngine.RenderEngineState renderEngineState4) {
      if (renderEngineState4 != null) {
         if (renderEngineState4.intValue != 0) {
            GL30.glDeleteFramebuffers(renderEngineState4.intValue);
            renderEngineState4.intValue = 0;
         }

         if (renderEngineState4.intValue2 != 0) {
            GL11.glDeleteTextures(renderEngineState4.intValue2);
            renderEngineState4.intValue2 = 0;
         }

         renderEngineState4.intValue3 = 0;
         renderEngineState4.intValue4 = 0;
      }
   }

   private void invoke52() {
      for (RenderEngine.RenderEngineState renderEngineState5 : this.items) {
         this.invoke51(renderEngineState5);
      }

      this.items.clear();
      this.intValue47 = 0;
   }

   private void invoke53(int i, int j) {
      this.invoke54(i, j, this.floatValue5, this.floatValue6);
   }

   private void invoke54(int i, int j, float f, float g) {
      if (i <= 0 || j <= 0) {
         this.invoke65();
      } else if (!Float.isFinite(f) || !Float.isFinite(g)) {
         throw new IllegalArgumentException("Blur capture scale must be finite");
      } else if (!(f <= 0.0F) && !(g <= 0.0F)) {
         int intValue66 = Math.max(1, i);
         int intValue67 = Math.max(1, j);
         int intValue68 = Math.max(1, Math.round(intValue66 * f));
         int intValue69 = Math.max(1, Math.round(intValue67 * g));
         if (this.intValue19 == 0 || intValue68 != this.intValue20 || intValue69 != this.intValue21) {
            if (this.intValue19 != 0) {
               GL11.glDeleteTextures(this.intValue19);
               this.intValue19 = 0;
            }

            if (this.intValue22 != 0) {
               GL30.glDeleteFramebuffers(this.intValue22);
               this.intValue22 = 0;
            }

            this.intValue19 = GL11.glGenTextures();
            GL11.glBindTexture(3553, this.intValue19);
            GL11.glTexParameteri(3553, 10241, 9729);
            GL11.glTexParameteri(3553, 10240, 9729);
            GL11.glTexParameteri(3553, 10242, 33071);
            GL11.glTexParameteri(3553, 10243, 33071);
            RenderCapabilities.invoke(32856, intValue68, intValue69, 6408, 5121);
            GL11.glBindTexture(3553, 0);
            this.intValue22 = GL30.glGenFramebuffers();
            GL30.glBindFramebuffer(36160, this.intValue22);
            GL30.glFramebufferTexture2D(36160, 36064, 3553, this.intValue19, 0);
            GL11.glDrawBuffer(36064);
            int intValue70 = GL30.glCheckFramebufferStatus(36160);
            GL30.glBindFramebuffer(36160, 0);
            if (intValue70 != 36053) {
               GL30.glDeleteFramebuffers(this.intValue22);
               GL11.glDeleteTextures(this.intValue19);
               this.intValue22 = 0;
               this.intValue19 = 0;
               throw new IllegalStateException("Downscaled capture FBO incomplete: status=" + intValue70);
            } else {
               this.intValue20 = intValue68;
               this.intValue21 = intValue69;
            }
         }
      } else {
         throw new IllegalArgumentException("Blur capture scale must be positive");
      }
   }

   public int compute5(int i, int j, int k, int l) {
      return this.compute6(i, j, k, l, true);
   }

   public int compute6(int i, int j, int k, int l, boolean bl) {
      if (k > 0 && l > 0 && this.intValue5 > 0 && this.intValue6 > 0) {
         this.invoke49(k, l, bl);
         int intValue71 = bl ? this.intValue18 : this.intValue26;
         int intValue72 = bl ? this.intValue15 : this.intValue23;
         if (intValue71 != 0 && intValue72 != 0) {
            int intValue73 = GL11.glGetInteger(36006);
            int intValue74 = Math.max(0, Math.min(i, this.intValue5));
            int intValue75 = Math.max(0, Math.min(this.intValue6, this.intValue6 - j - l));
            int intValue76 = Math.min(k, this.intValue5 - intValue74);
            int intValue77 = Math.min(l, this.intValue6 - intValue75);
            if (intValue76 > 0 && intValue77 > 0) {
               FramebufferUtils.GlStateSnapshot glStateSnapshot6 = FramebufferUtils.captureGlState();

               try {
                  boolean flag17 = GL11.glIsEnabled(3089);
                  boolean flag18 = GL11.glIsEnabled(36281);
                  if (flag17) {
                     GL11.glDisable(3089);
                  }

                  if (flag18) {
                     GL11.glDisable(36281);
                  }

                  GL30.glBindFramebuffer(36008, intValue73);
                  GL11.glReadBuffer(intValue73 == 0 ? 1029 : '賠');
                  GL30.glBindFramebuffer(36009, intValue71);
                  GL11.glDrawBuffer(36064);
                  GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
                  GL11.glClear(16384);
                  GL30.glBlitFramebuffer(intValue74, intValue75, intValue74 + intValue76, intValue75 + intValue77, 0, 0, k, l, 16384, 9729);
                  if (flag17) {
                     GL11.glEnable(3089);
                  }

                  if (flag18) {
                     GL11.glEnable(36281);
                  }
               } finally {
                  FramebufferUtils.restoreGlState(glStateSnapshot6);
               }

               return intValue72;
            } else {
               return 0;
            }
         } else {
            return 0;
         }
      } else {
         return 0;
      }
   }

   public void invoke55(int i, int j, float f) {
      if (i > 0 && j > 0) {
         float floatValue68 = 1.0F;
         float floatValue69 = 1.0F;
         float floatValue70 = this.floatValue5;
         float floatValue71 = this.floatValue6;
         float floatValue72 = this.blurPipeline.measure2();
         if (f > floatValue72) {
            float floatValue73 = this.blurPipeline.measure();
            float floatValue74 = Math.max(f, floatValue73);
            float floatValue75 = floatValue72 / floatValue74;
            floatValue75 = Math.max(floatValue75, 0.2F);
            floatValue68 = Math.min(floatValue68, floatValue75);
            floatValue69 = Math.min(floatValue69, floatValue75);
         }

         floatValue68 = Math.max(floatValue68, floatValue70);
         floatValue69 = Math.max(floatValue69, floatValue71);
         this.invoke54(i, j, floatValue68, floatValue69);
         if (this.intValue19 != 0 && this.intValue22 != 0) {
            float floatValue76 = (float)this.intValue20 / Math.max(1, i);
            float floatValue77 = (float)this.intValue21 / Math.max(1, j);
            FramebufferUtils.GlStateSnapshot glStateSnapshot7 = FramebufferUtils.captureGlState();

            try {
               boolean flag19 = GL11.glIsEnabled(3089);
               boolean flag20 = GL11.glIsEnabled(36281);
               if (flag19) {
                  GL11.glDisable(3089);
               }

               if (flag20) {
                  GL11.glDisable(36281);
               }

               boolean flag21 = false;
               MinecraftClient client = MinecraftClient.getInstance();
               if (client != null && client.getWindow() != null && !client.getWindow().hasZeroWidthOrHeight()) {
                  Framebuffer framebuffer = client.getFramebuffer();
                  if (framebuffer != null && framebuffer.getColorAttachment() instanceof GlTexture glTexture) {
                     int intValue78 = glTexture.getGlId();
                     if (intValue78 > 0) {
                        if (this.intValue27 == 0) {
                           this.intValue27 = GL30.glGenFramebuffers();
                        }

                        GL30.glBindFramebuffer(36008, this.intValue27);
                        GL30.glFramebufferTexture2D(36008, 36064, 3553, intValue78, 0);
                        GL11.glReadBuffer(36064);
                        flag21 = GL30.glCheckFramebufferStatus(36008) == 36053;
                     }
                  }
               }

               if (!flag21) {
                  this.intValue89 = 0;
                  this.intValue90 = 0;
                  this.intValue91 = 0;
                  this.floatValue7 = 1.0F;
                  this.floatValue8 = 1.0F;
                  return;
               }

               GL30.glBindFramebuffer(36009, this.intValue22);
               GL11.glDrawBuffer(36064);
               GL30.glBlitFramebuffer(0, 0, i, j, 0, 0, this.intValue20, this.intValue21, 16384, 9729);
               if (flag19) {
                  GL11.glEnable(3089);
               }

               if (flag20) {
                  GL11.glEnable(36281);
               }
            } finally {
               FramebufferUtils.restoreGlState(glStateSnapshot7);
            }

            float floatValue78 = (float)Math.sqrt(Math.max(0.0F, floatValue76) * Math.max(0.0F, floatValue77));
            float floatValue79 = Math.max(0.0F, f) * floatValue78;
            int intValue79 = this.blurPipeline.compute(this.intValue19, this.intValue20, this.intValue21, floatValue79);
            if (intValue79 == 0) {
               this.intValue89 = 0;
               this.intValue90 = 0;
               this.intValue91 = 0;
               this.floatValue7 = 1.0F;
               this.floatValue8 = 1.0F;
            } else {
               this.intValue89 = intValue79;
               this.intValue90 = this.intValue20;
               this.intValue91 = this.intValue21;
               this.floatValue7 = floatValue76;
               this.floatValue8 = floatValue77;
            }
         } else {
            this.intValue89 = 0;
            this.intValue90 = 0;
            this.intValue91 = 0;
            this.floatValue7 = 1.0F;
            this.floatValue8 = 1.0F;
         }
      } else {
         this.intValue89 = 0;
         this.intValue90 = 0;
         this.intValue91 = 0;
         this.floatValue7 = 1.0F;
         this.floatValue8 = 1.0F;
      }
   }

   public boolean check(int i, int j, int k, int l, float f) {
      if (k > 0 && l > 0) {
         int intValue80 = this.compute6(i, j, k, l, false);
         if (intValue80 <= 0) {
            this.intValue92 = 0;
            this.intValue95 = 0;
            this.intValue96 = 0;
            this.intValue93 = 0;
            this.intValue94 = 0;
            return false;
         } else {
            int intValue81 = this.blurPipeline2.compute(intValue80, k, l, f);
            this.intValue92 = intValue81;
            this.intValue95 = k;
            this.intValue96 = l;
            this.intValue93 = i;
            this.intValue94 = j;
            return intValue81 != 0;
         }
      } else {
         this.intValue92 = 0;
         this.intValue95 = 0;
         this.intValue96 = 0;
         this.intValue93 = 0;
         this.intValue94 = 0;
         return false;
      }
   }

   public void invoke56(float f, float g, float h, float i, float j, float k, float[] fs) {
      this.invoke57(f, g, h, i, j, j, j, j, k, fs);
   }

   public void invoke57(float f, float g, float h, float i, float j, float k, float l, float m, float n, float[] fs) {
      if (this.intValue89 != 0) {
         this.invoke();
         int intValue82 = (int)(Math.max(0.0F, Math.min(1.0F, n)) * 255.0F) << 24 | 16777215;
         float floatValue80 = this.intValue90 > 0 ? this.floatValue7 / this.intValue90 : 0.0F;
         float floatValue81 = this.intValue91 > 0 ? -this.floatValue8 / this.intValue91 : 0.0F;
         float floatValue82 = 0.0F;
         float floatValue83 = this.intValue91 > 0 ? 1.0F : 0.0F;
         this.invoke43(this.intValue89, f, g, h, i, floatValue80, floatValue81, floatValue82, floatValue83, j, k, l, m, intValue82, fs, true);
      }
   }

   public void invoke58(float f, float g, float h, float i, float j, float k, float[] fs, int l, int m, int n, int o) {
      if (this.intValue92 != 0) {
         if (n > 0 && o > 0) {
            if (this.intValue95 == n && this.intValue96 == o && this.intValue93 == l && this.intValue94 == m) {
               this.invoke();
               int intValue83 = (int)(Math.max(0.0F, Math.min(1.0F, k)) * 255.0F) << 24 | 16777215;
               float floatValue84 = 0.0F;
               float floatValue85 = 1.0F;
               float floatValue86 = 1.0F;
               float floatValue87 = 0.0F;
               this.invoke42(this.intValue92, f, g, h, i, floatValue84, floatValue85, floatValue86, floatValue87, j, intValue83, fs, false);
            }
         }
      }
   }

   public RenderEngine.RenderEngineBounds resolve6() {
      MinecraftClient client2 = MinecraftClient.getInstance();
      if (client2 != null && client2.getWindow() != null && !client2.getWindow().hasZeroWidthOrHeight()) {
         Framebuffer framebuffer2 = client2.getFramebuffer();
         if (framebuffer2 == null) {
            return new RenderEngine.RenderEngineBounds(0, 0, 0, 0);
         } else if (!(framebuffer2.getColorAttachment() instanceof GlTexture glTexture2)) {
            return new RenderEngine.RenderEngineBounds(0, 0, 0, 0);
         } else {
            int intValue84 = glTexture2.getGlId();
            if (intValue84 <= 0) {
               return new RenderEngine.RenderEngineBounds(0, 0, 0, 0);
            } else {
               int intValue85 = 0;
               if (framebuffer2.getDepthAttachment() instanceof GlTexture glTexture3) {
                  intValue85 = glTexture3.getGlId();
               }

               int intValue86 = client2.getWindow().getFramebufferWidth();
               int intValue87 = client2.getWindow().getFramebufferHeight();
               if (intValue86 > 0 && intValue87 > 0 && framebuffer2.textureWidth > 0 && framebuffer2.textureHeight > 0) {
                  this.depthRenderTarget.invoke(intValue86, intValue87);
                  if (this.depthRenderTarget.intValue != 0 && this.depthRenderTarget.intValue2 != 0 && this.depthRenderTarget.intValue3 != 0) {
                     FramebufferUtils.GlStateSnapshot glStateSnapshot8 = FramebufferUtils.captureGlState();
                     boolean flag22 = false ;

                     RenderEngine.RenderEngineBounds renderEngineBounds;
                     label110: {
                        try {
                           flag22 = true;
                           GL11.glDisable(3089);
                           GL11.glDisable(2884);
                           GL11.glDisable(3042);
                           GL11.glDisable(2929);
                           GL11.glDisable(36281);
                           if (this.intValue27 == 0) {
                              this.intValue27 = GL30.glGenFramebuffers();
                           }

                           GL30.glBindFramebuffer(36008, this.intValue27);
                           GL30.glFramebufferTexture2D(36008, 36064, 3553, intValue84, 0);
                           if (intValue85 > 0) {
                              GL30.glFramebufferTexture2D(36008, 36096, 3553, intValue85, 0);
                           } else {
                              GL30.glFramebufferTexture2D(36008, 36096, 3553, 0, 0);
                           }

                           int intValue88 = GL30.glCheckFramebufferStatus(36008);
                           if (intValue88 != 36053) {
                              renderEngineBounds = new RenderEngine.RenderEngineBounds(0, 0, 0, 0);
                              flag22 = false;
                              break label110;
                           }

                           GL30.glBindFramebuffer(36009, this.depthRenderTarget.intValue);
                           GL11.glReadBuffer(36064);
                           GL11.glDrawBuffer(36064);
                           short shortValue2 = 16384;
                           if (intValue85 > 0) {
                              shortValue2 |= 256;
                           }

                           GL30.glBlitFramebuffer(0, 0, intValue86, intValue87, 0, 0, intValue86, intValue87, shortValue2, 9728);
                           flag22 = false;
                        } finally {
                           if (flag22) {
                              FramebufferUtils.restoreGlState(glStateSnapshot8);
                           }
                        }

                        FramebufferUtils.restoreGlState(glStateSnapshot8);
                        return new RenderEngine.RenderEngineBounds(this.depthRenderTarget.intValue2, this.depthRenderTarget.intValue3, intValue86, intValue87);
                     }

                     FramebufferUtils.restoreGlState(glStateSnapshot8);
                     return renderEngineBounds;
                  } else {
                     return new RenderEngine.RenderEngineBounds(0, 0, 0, 0);
                  }
               } else {
                  return new RenderEngine.RenderEngineBounds(0, 0, 0, 0);
               }
            }
         }
      } else {
         return new RenderEngine.RenderEngineBounds(0, 0, 0, 0);
      }
   }

   public void invoke59(int i, int j, int k) {
      if (i > 0 && j > 0 && k > 0) {
         this.invoke4();
         GlShaderProgram glShaderProgram = this.resolve();
         FramebufferUtils.GlStateSnapshot glStateSnapshot9 = FramebufferUtils.captureGlState();

         try {
            GL30.glBindFramebuffer(36160, 0);
            GL11.glViewport(0, 0, Math.max(0, j), Math.max(0, k));
            GL11.glDisable(3089);
            GL11.glDisable(2884);
            GL11.glDisable(2929);
            GL11.glDisable(3042);
            GL11.glDisable(36281);
            glShaderProgram.invoke();
            if (this.intValue30 >= 0) {
               GL20.glUniform1i(this.intValue30, 0);
            }

            GL13.glActiveTexture(33984);
            GL11.glBindTexture(3553, i);
            GL30.glBindVertexArray(this.intValue28);
            GlStateGuard.getINSTANCE().invoke2(2);
            GL11.glDrawArrays(4, 0, 6);
            GL30.glBindVertexArray(0);
         } finally {
            GL13.glActiveTexture(33984);
            GL11.glBindTexture(3553, 0);
            GL20.glUseProgram(0);
            FramebufferUtils.restoreGlState(glStateSnapshot9);
         }
      }
   }

   public void invoke60(int i, int j, int k, GlShaderProgram glShaderProgram2, Runnable runnable, boolean bl) {
      if (i > 0 && j > 0 && k > 0 && glShaderProgram2 != null) {
         this.invoke4();
         FramebufferUtils.GlStateSnapshot glStateSnapshot10 = FramebufferUtils.captureGlState();

         try {
            int intValue89 = GL11.glGetInteger(36006);
            GL30.glBindFramebuffer(36009, intValue89);
            GL11.glViewport(0, 0, Math.max(0, j), Math.max(0, k));
            GL11.glDisable(3089);
            GL11.glDisable(2884);
            GL11.glDisable(2929);
            if (bl) {
               GL11.glEnable(3042);
               GL14.glBlendFuncSeparate(770, 771, 1, 771);
            } else {
               GL11.glDisable(3042);
            }

            GL11.glDisable(36281);
            glShaderProgram2.invoke();
            if (runnable != null) {
               runnable.run();
            }

            GL13.glActiveTexture(33984);
            GL11.glBindTexture(3553, i);
            GL30.glBindVertexArray(this.intValue28);
            GlStateGuard.getINSTANCE().invoke2(2);
            GL11.glDrawArrays(4, 0, 6);
            GL30.glBindVertexArray(0);
         } finally {
            GL13.glActiveTexture(33984);
            GL11.glBindTexture(3553, 0);
            GL20.glUseProgram(0);
            FramebufferUtils.restoreGlState(glStateSnapshot10);
         }
      }
   }

   public void invoke61(int i, int j) {
      if (!this.flag8) {
         if (i > 0 && j > 0) {
            if (i != this.intValue7 || j != this.intValue8) {
               this.intValue7 = i;
               this.intValue8 = j;
               this.invoke62();
            }
         } else {
            this.intValue5 = 0;
            this.intValue6 = 0;
            this.intValue7 = -1;
            this.intValue8 = -1;
            this.invoke62();
         }
      }
   }

   private void invoke62() {
      this.invoke63();
      this.invoke64(true);
      this.invoke64(false);
      this.invoke65();
      this.invoke51(this.renderEngineState);
      this.invoke52();
      this.depthRenderTarget.invoke2();
      this.blurPipeline.invoke2();
      this.blurPipeline2.invoke2();
      if (this.intValue27 != 0) {
         GL30.glDeleteFramebuffers(this.intValue27);
         this.intValue27 = 0;
      }
   }

   private void invoke63() {
      this.intValue89 = 0;
      this.intValue90 = 0;
      this.intValue91 = 0;
      this.floatValue7 = 1.0F;
      this.floatValue8 = 1.0F;
      this.intValue92 = 0;
      this.intValue93 = 0;
      this.intValue94 = 0;
      this.intValue95 = 0;
      this.intValue96 = 0;
   }

   private void invoke64(boolean bl) {
      if (bl) {
         if (this.intValue18 != 0) {
            GL30.glDeleteFramebuffers(this.intValue18);
            this.intValue18 = 0;
         }

         if (this.intValue15 != 0) {
            GL11.glDeleteTextures(this.intValue15);
            this.intValue15 = 0;
         }

         this.intValue16 = 0;
         this.intValue17 = 0;
      } else {
         if (this.intValue26 != 0) {
            GL30.glDeleteFramebuffers(this.intValue26);
            this.intValue26 = 0;
         }

         if (this.intValue23 != 0) {
            GL11.glDeleteTextures(this.intValue23);
            this.intValue23 = 0;
         }

         this.intValue24 = 0;
         this.intValue25 = 0;
      }
   }

   private void invoke65() {
      if (this.intValue22 != 0) {
         GL30.glDeleteFramebuffers(this.intValue22);
         this.intValue22 = 0;
      }

      if (this.intValue19 != 0) {
         GL11.glDeleteTextures(this.intValue19);
         this.intValue19 = 0;
      }

      this.intValue20 = 0;
      this.intValue21 = 0;
   }

   public void invoke66() {
      if (!this.flag8) {
         this.flag8 = true;
         this.blurPipeline.invoke();
         this.blurPipeline2.invoke();
         this.depthRenderTarget.invoke2();
         if (this.intValue27 != 0) {
            GL30.glDeleteFramebuffers(this.intValue27);
            this.intValue27 = 0;
         }

         if (this.intValue28 != 0) {
            GL30.glDeleteVertexArrays(this.intValue28);
            this.intValue28 = 0;
         }

         if (this.intValue29 != 0) {
            GL15.glDeleteBuffers(this.intValue29);
            this.intValue29 = 0;
         }

         if (this.intValue31 != 0) {
            GL30.glDeleteVertexArrays(this.intValue31);
            this.intValue31 = 0;
         }

         if (this.intValue32 != 0) {
            GL15.glDeleteBuffers(this.intValue32);
            this.intValue32 = 0;
         }

         if (this.intValue48 != 0) {
            GL30.glDeleteVertexArrays(this.intValue48);
            this.intValue48 = 0;
         }

         if (this.intValue49 != 0) {
            GL15.glDeleteBuffers(this.intValue49);
            this.intValue49 = 0;
         }

         this.invoke51(this.renderEngineState);
         this.invoke52();
         if (this.intValue18 != 0) {
            GL30.glDeleteFramebuffers(this.intValue18);
            this.intValue18 = 0;
         }

         if (this.intValue15 != 0) {
            GL11.glDeleteTextures(this.intValue15);
            this.intValue15 = 0;
         }

         this.intValue16 = 0;
         this.intValue17 = 0;
         if (this.intValue22 != 0) {
            GL30.glDeleteFramebuffers(this.intValue22);
            this.intValue22 = 0;
         }

         if (this.intValue19 != 0) {
            GL11.glDeleteTextures(this.intValue19);
            this.intValue19 = 0;
         }

         this.intValue20 = 0;
         this.intValue21 = 0;
         if (this.intValue26 != 0) {
            GL30.glDeleteFramebuffers(this.intValue26);
            this.intValue26 = 0;
         }

         if (this.intValue23 != 0) {
            GL11.glDeleteTextures(this.intValue23);
            this.intValue23 = 0;
         }

         this.intValue24 = 0;
         this.intValue25 = 0;
         this.intValue89 = 0;
         this.intValue90 = 0;
         this.intValue91 = 0;
         this.floatValue7 = 1.0F;
         this.floatValue8 = 1.0F;
         this.intValue92 = 0;
         this.intValue95 = 0;
         this.intValue96 = 0;
         this.intValue93 = 0;
         this.intValue94 = 0;
         this.invoke3();
         GL30.glBindVertexArray(0);
         GL20.glUseProgram(0);
         if (this.intValue != 0) {
            GL30.glDeleteVertexArrays(this.intValue);
         }

         if (this.intValue2 != 0) {
            GL15.glDeleteBuffers(this.intValue2);
         }

         if (this.intValue3 != 0) {
            GL15.glDeleteBuffers(this.intValue3);
         }

         this.glShaderProgram.invoke2();
         if (this.glShaderProgram2 != null) {
            this.glShaderProgram2.invoke2();
            this.glShaderProgram2 = null;
         }

         if (this.glShaderProgram3 != null) {
            this.glShaderProgram3.invoke2();
            this.glShaderProgram3 = null;
         }

         if (this.glShaderProgram4 != null) {
            this.glShaderProgram4.invoke2();
            this.glShaderProgram4 = null;
         }

         if (this.glShaderProgram5 != null) {
            this.glShaderProgram5.invoke2();
            this.glShaderProgram5 = null;
         }

         if (this.glShaderProgram6 != null) {
            this.glShaderProgram6.invoke2();
            this.glShaderProgram6 = null;
         }

         if (this.gLDebugMessageCallback != null) {
            this.gLDebugMessageCallback.free();
            this.gLDebugMessageCallback = null;
         }
      }
   }

   private void invoke67(GLCapabilities gLCapabilities) {
      if (this.gLDebugMessageCallback == null) {
         this.gLDebugMessageCallback = GLDebugMessageCallback.create((i, j, k, l, m, n, o) -> {
            if (l != 33387 && l != 37192) {
               long longValue2 = System.currentTimeMillis();
               Long longValue3 = CONCURRENT_HASH_MAP.get(k);
               if (longValue3 == null || longValue2 - longValue3 >= 5000L) {
                  CONCURRENT_HASH_MAP.put(k, longValue2);
                  long longValue4 = ATOMIC_LONG.get();
                  if (longValue2 - longValue4 > 1000L) {
                     ATOMIC_LONG.set(longValue2);
                     ATOMIC_INTEGER.set(0);
                  }

                  if (ATOMIC_INTEGER.incrementAndGet() <= 8) {
                     String text6 = GLDebugMessageCallback.getMessage(m, n);
                     System.err.println("[OpenGL] " + text6 + " (severity=" + resolve7(l) + ")");
                  }
               }
            }
         });
         if (gLCapabilities.OpenGL43) {
            GL11.glEnable(37600);
            GL43.glDebugMessageCallback(this.gLDebugMessageCallback, 0L);
            GL43.glDebugMessageControl(4352, 4352, 33387, (int[])null, false);
            GL43.glDebugMessageControl(4352, 4352, 37192, (int[])null, false);
         } else {
            GL11.glEnable(37600);
            KHRDebug.glDebugMessageCallback(this.gLDebugMessageCallback, 0L);
            KHRDebug.glDebugMessageControl(4352, 4352, 33387, (int[])null, false);
            KHRDebug.glDebugMessageControl(4352, 4352, 37192, (int[])null, false);
         }
      }
   }

   private static String resolve7(int i) {
      return switch (i) {
         case 33387 -> "NOTIFICATION";
         case 37190 -> "HIGH";
         case 37191 -> "MEDIUM";
         case 37192 -> "LOW";
         default -> Integer.toString(i);
      };
   }

   public record RenderEngineBounds(int colorTexture, int depthTexture, int width, int height) {
   }

   public record RenderEngineBounds2(
      int texture,
      int width,
      int height,
      FramebufferUtils.GlStateSnapshot snapshot,
      int previousViewportWidth,
      int previousViewportHeight,
      boolean previousClipEnabled,
      int previousClipX,
      int previousClipY,
      int previousClipW,
      int previousClipH,
      float previousClipRoundTL,
      float previousClipRoundTR,
      float previousClipRoundBR,
      float previousClipRoundBL,
      boolean previousAdditiveBlend,
      boolean cardTransition
   ) {
   }

   static final class RenderEngineState {
      int intValue;
      int intValue2;
      int intValue3;
      int intValue4;
   }
}
