package ru.metaculture.protection;

import com.mojang.blaze3d.opengl.GlStateManager;
import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Quaternionf;
import org.joml.Vector2d;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.wild.mixin.acceser.GameRendererAccessor;

public final class RenderManager {
   private static final float FLOAT_VALUE = 0.5F;
   private static final float FLOAT_VALUE_2 = 0.05F;
   public static volatile BooleanSupplier booleanSupplier = () -> true;
   public static volatile BooleanSupplier booleanSupplier2 = () -> true;
   private final RenderEngine renderEngine;
   private final ArrayDeque<RenderManager.RenderManagerData2> arrayDeque = new ArrayDeque<>();
   private final ArrayDeque<Float> arrayDeque2 = new ArrayDeque<>();
   private final ArrayDeque<Boolean> arrayDeque3 = new ArrayDeque<>();
   private final ArrayDeque<RenderManager.RenderManagerData3> arrayDeque4 = new ArrayDeque<>();
   private final Matrix3Stack matrix3Stack = new Matrix3Stack();
   private static Map<String, FontRenderer> valuesByKey = new HashMap<>();
   private final RenderEngineFacade renderEngineFacade;
   private boolean flag = false;
   private int intValue = 0;
   private int intValue2 = 0;
   private boolean flag2 = false;
   private float floatValue = 0.0F;
   private int intValue3 = 0;
   private int intValue4 = 0;
   private boolean flag3 = false;
   private float floatValue2 = 0.0F;
   private int intValue5 = 0;
   private int intValue6 = 0;
   private int intValue7 = 0;
   private int intValue8 = 0;
   private static final ThreadLocal<float[]> THREAD_LOCAL = ThreadLocal.withInitial(() -> new float[4]);
   private int intValue9 = 0;
   private int intValue10 = 0;
   public static MinecraftClient client = MinecraftClient.getInstance();

   public RenderManager(RenderEngine renderEngine) {
      if (renderEngine == null) {
         throw new IllegalArgumentException("GlBackend cannot be null");
      } else {
         this.renderEngine = renderEngine;
         this.renderEngineFacade = new RenderEngineFacade(renderEngine);
         this.invoke73();
      }
   }

   public void invoke(int i, int j) {
      if (i > 0 && j > 0) {
         if (this.flag) {
            this.invoke2();
         }

         this.flag = true;
         this.intValue = i;
         this.intValue2 = j;
         this.flag2 = false;
         this.flag3 = false;
         this.floatValue = 0.0F;
         this.floatValue2 = 0.0F;
         this.intValue3 = 0;
         this.intValue4 = 0;
         this.intValue5 = 0;
         this.intValue6 = 0;
         this.intValue7 = 0;
         this.intValue8 = 0;
         if (this.renderEngine != null) {
            GlStateGuard.getINSTANCE().invoke(i, j);
            this.renderEngine.invoke14(i, j);
            if (i != this.intValue9 || j != this.intValue10) {
               this.intValue9 = i;
               this.intValue10 = j;
            }

            this.renderEngine.setFlag5(false);
         }

         if (!this.arrayDeque.isEmpty()) {
            this.arrayDeque.clear();
         }

         this.arrayDeque4.clear();
         this.matrix3Stack.invoke();
         this.invoke73();
         this.invoke74();
      } else {
         throw new IllegalArgumentException("Width and height must be positive, got: " + i + "x" + j);
      }
   }

   public void invoke2() {
      boolean flag = false ;

      label57: {
         try {
            flag = true;
            if (this.renderEngineFacade != null) {
               this.renderEngineFacade.invoke6();
            }

            if (this.renderEngine != null) {
               this.renderEngine.invoke15();
            }

            GlStateGuard.getINSTANCE().invoke3();
            flag = false;
            break label57;
         } catch (Throwable exception) {
            flag = false;
         } finally {
            if (flag) {
               this.flag = false;
               this.intValue = 0;
               this.intValue2 = 0;
               this.flag2 = false;
               this.floatValue = 0.0F;
               this.intValue3 = 0;
               this.intValue4 = 0;
               this.flag3 = false;
               this.floatValue2 = 0.0F;
               this.intValue5 = 0;
               this.intValue6 = 0;
               this.intValue7 = 0;
               this.intValue8 = 0;
               this.arrayDeque.clear();
               this.arrayDeque4.clear();
               this.matrix3Stack.invoke();
               this.invoke73();
               this.invoke74();
            }
         }

         this.flag = false;
         this.intValue = 0;
         this.intValue2 = 0;
         this.flag2 = false;
         this.floatValue = 0.0F;
         this.intValue3 = 0;
         this.intValue4 = 0;
         this.flag3 = false;
         this.floatValue2 = 0.0F;
         this.intValue5 = 0;
         this.intValue6 = 0;
         this.intValue7 = 0;
         this.intValue8 = 0;
         this.arrayDeque.clear();
         this.arrayDeque4.clear();
         this.matrix3Stack.invoke();
         this.invoke73();
         this.invoke74();
         return;
      }

      this.flag = false;
      this.intValue = 0;
      this.intValue2 = 0;
      this.flag2 = false;
      this.floatValue = 0.0F;
      this.intValue3 = 0;
      this.intValue4 = 0;
      this.flag3 = false;
      this.floatValue2 = 0.0F;
      this.intValue5 = 0;
      this.intValue6 = 0;
      this.intValue7 = 0;
      this.intValue8 = 0;
      this.arrayDeque.clear();
      this.arrayDeque4.clear();
      this.matrix3Stack.invoke();
      this.invoke73();
      this.invoke74();
   }

   private void invoke3() {
      if (!this.flag) {
         throw new IllegalStateException("begin() must be called before issuing draw commands");
      } else if (this.renderEngine == null) {
         throw new IllegalStateException("Renderer2D backend is null - initialization failed");
      } else if (this.renderEngineFacade == null) {
         throw new IllegalStateException("Renderer2D batcher is null - initialization failed");
      }
   }

   private float[] resolve(float f, float g, float h, float i, float j, float k) {
      float[] floatValues = THREAD_LOCAL.get();
      floatValues[0] = Math.max(0.0F, h);
      floatValues[1] = Math.max(0.0F, i);
      floatValues[2] = Math.max(0.0F, j);
      floatValues[3] = Math.max(0.0F, k);
      float floatValue = Math.min(Math.abs(f), Math.abs(g)) * 0.5F;
      if (floatValue <= 0.0F) {
         floatValues[0] = floatValues[1] = floatValues[2] = floatValues[3] = 0.0F;
         return floatValues;
      } else {
         floatValues[0] = Math.min(floatValues[0], floatValue);
         floatValues[1] = Math.min(floatValues[1], floatValue);
         floatValues[2] = Math.min(floatValues[2], floatValue);
         floatValues[3] = Math.min(floatValues[3], floatValue);
         return floatValues;
      }
   }

   public void invoke4(float f, float g, float h, float i, int j) {
      this.invoke3();
      this.renderEngineFacade.invoke(f, g, h, i, 0.0F, 0.0F, 0.0F, 0.0F, this.compute3(j), this.matrix3Stack.resolve2());
   }

   public void invoke5(float f, float g, float h, float i, float j, int k) {
      this.invoke6(f, g, h, i, j, j, j, j, k);
   }

   public void invoke6(float f, float g, float h, float i, float j, float k, float l, float m, int n) {
      this.invoke3();
      float[] floatValues2 = this.resolve(h, i, j, k, l, m);
      this.renderEngineFacade.invoke(f, g, h, i, floatValues2[0], floatValues2[1], floatValues2[2], floatValues2[3], this.compute3(n), this.matrix3Stack.resolve2());
   }

   public void invoke7(int i, float f, float g, float h, float j) {
      this.invoke13(i, f, g, h, j, -1, true, false);
   }

   public void invoke8(int i, float f, float g, float h, float j, int k) {
      this.invoke13(i, f, g, h, j, k, true, false);
   }

   public void invoke9(int i, float f, float g, float h, float j, int k, boolean bl) {
      this.invoke13(i, f, g, h, j, k, bl, false);
   }

   public void drawFlippedTexture(int i, float f, float g, float h, float j) {
      this.invoke13(i, f, g, h, j, -1, true, true);
   }

   public void invoke10(int i, float f, float g, float h, float j, int k, boolean bl) {
      this.invoke13(i, f, g, h, j, k, bl, true);
   }

   public void invoke11(int i, float f, float g, float h, float j, float k, float l, float m, float n) {
      this.invoke3();
      if (i > 0) {
         this.invoke20();
         this.renderEngine.invoke38(i, f, g, h, j, k, l, m, n, this.compute3(-1), this.matrix3Stack.resolve2(), false);
      }
   }

   public void invoke12(int i, float f, float g, float h, float j, float k, float l, float m, float n, float o) {
      this.invoke3();
      if (i > 0) {
         this.invoke20();
         this.renderEngine.invoke40(i, f, g, h, j, k, l, m, n, o, this.compute3(-1), this.matrix3Stack.resolve2(), false);
      }
   }

   private void invoke13(int i, float f, float g, float h, float j, int k, boolean bl, boolean bl2) {
      this.invoke3();
      if (i > 0) {
         this.invoke20();
         float floatValue2 = bl ? 1.0F : 0.0F;
         float floatValue3 = bl ? 0.0F : 1.0F;
         this.renderEngine.invoke38(i, f, g, h, j, 0.0F, floatValue2, 1.0F, floatValue3, this.compute3(k), this.matrix3Stack.resolve2(), bl2);
      }
   }

   public RenderManager.RenderManagerState2 resolve2(float f, float g, float h, float i) {
      return this.resolve4(f, g, h, i, false);
   }

   public RenderManager.RenderManagerState2 resolve3(float f, float g, float h, float i) {
      return this.resolve4(f, g, h, i, true);
   }

   private RenderManager.RenderManagerState2 resolve4(float f, float g, float h, float i, boolean bl) {
      this.invoke3();
      if (this.intValue > 0 && this.intValue2 > 0 && !(h <= 0.0F) && !(i <= 0.0F)) {
         int intValue = (int)Math.ceil(h);
         int intValue2 = (int)Math.ceil(i);
         if (intValue > 0 && intValue2 > 0) {
            this.invoke20();
            RenderEngine.RenderEngineBounds2 renderEngineBounds2 = bl ? this.renderEngine.resolve3(intValue, intValue2) : this.renderEngine.resolve2(intValue, intValue2);
            if (renderEngineBounds2 == null) {
               return null;
            } else {
               float[] floatValues3 = this.arrayDeque4.isEmpty()
                  ? Arrays.copyOf(this.matrix3Stack.resolve2(), 9)
                  : Arrays.copyOf(this.arrayDeque4.peek().rootTransform(), 9);
               this.arrayDeque4.push(new RenderManager.RenderManagerData3(floatValues3, f, g));
               RenderManager.RenderManagerState2 renderManagerState2 = new RenderManager.RenderManagerState2(
                  renderEngineBounds2,
                  this.intValue,
                  this.intValue2,
                  this.flag2,
                  this.floatValue,
                  this.intValue3,
                  this.intValue4,
                  this.flag3,
                  this.floatValue2,
                  this.intValue5,
                  this.intValue6,
                  this.intValue7,
                  this.intValue8,
                  this.matrix3Stack.resolve(),
                  new ArrayDeque<>(this.arrayDeque),
                  new ArrayDeque<>(this.arrayDeque2),
                  new ArrayDeque<>(this.arrayDeque3)
               );
               this.intValue = intValue;
               this.intValue2 = intValue2;
               this.flag2 = false;
               this.floatValue = 0.0F;
               this.intValue3 = 0;
               this.intValue4 = 0;
               this.flag3 = false;
               this.floatValue2 = 0.0F;
               this.intValue5 = 0;
               this.intValue6 = 0;
               this.intValue7 = 0;
               this.intValue8 = 0;
               this.arrayDeque.clear();
               this.matrix3Stack.invoke();
               this.matrix3Stack.invoke4(-f, -g);
               this.invoke73();
               this.invoke74();
               this.renderEngine.setFlag5(false);
               return renderManagerState2;
            }
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   public void invoke14(RenderManager.RenderManagerState2 renderManagerState22) {
      this.invoke3();
      if (renderManagerState22 != null && renderManagerState22.renderEngineBounds2 != null) {
         this.invoke20();
         this.renderEngine.invoke9(renderManagerState22.renderEngineBounds2);
         this.intValue = renderManagerState22.intValue;
         this.intValue2 = renderManagerState22.intValue2;
         this.flag2 = renderManagerState22.flag;
         this.floatValue = renderManagerState22.floatValue;
         this.intValue3 = renderManagerState22.intValue3;
         this.intValue4 = renderManagerState22.intValue4;
         this.flag3 = renderManagerState22.flag2;
         this.floatValue2 = renderManagerState22.floatValue2;
         this.intValue5 = renderManagerState22.intValue5;
         this.intValue6 = renderManagerState22.intValue6;
         this.intValue7 = renderManagerState22.intValue7;
         this.intValue8 = renderManagerState22.intValue8;
         this.matrix3Stack.invoke10(renderManagerState22.arrayDeque);
         if (!this.arrayDeque4.isEmpty()) {
            this.arrayDeque4.pop();
         }

         this.arrayDeque.clear();
         this.arrayDeque.addAll(renderManagerState22.arrayDeque2);
         this.arrayDeque2.clear();
         this.arrayDeque2.addAll(renderManagerState22.arrayDeque3);
         this.arrayDeque3.clear();
         this.arrayDeque3.addAll(renderManagerState22.arrayDeque4);
         if (this.arrayDeque2.isEmpty()) {
            this.invoke73();
         }

         if (this.arrayDeque3.isEmpty()) {
            this.arrayDeque3.push(false);
         }

         this.renderEngine.setFlag7(this.arrayDeque3.peek());
         this.renderEngine.invoke20();
         if (this.arrayDeque.isEmpty()) {
            this.renderEngine.setFlag5(false);
         } else {
            this.invoke26(this.arrayDeque.peek());
         }
      }
   }

   public void invoke15(
      RenderManager.RenderManagerState2 renderManagerState23,
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
      float r,
      float s
   ) {
      this.invoke3();
      if (renderManagerState23 != null && renderManagerState23.renderEngineBounds2 != null) {
         float floatValue4 = this.measure4();
         if (!(floatValue4 <= 1.0E-4F)) {
            RenderManager.RenderManagerData2 renderManagerData2 = this.arrayDeque.peek();
            int intValue3 = renderManagerData2 == null ? 0 : renderManagerData2.x();
            int intValue4 = renderManagerData2 == null ? 0 : renderManagerData2.y();
            int intValue5 = renderManagerData2 == null ? this.intValue : renderManagerData2.w();
            int intValue6 = renderManagerData2 == null ? this.intValue2 : renderManagerData2.h();
            float floatValue5 = renderManagerData2 == null ? 0.0F : renderManagerData2.roundTopLeft();
            float floatValue6 = renderManagerData2 == null ? 0.0F : renderManagerData2.roundTopRight();
            float floatValue7 = renderManagerData2 == null ? 0.0F : renderManagerData2.roundBottomRight();
            float floatValue8 = renderManagerData2 == null ? 0.0F : renderManagerData2.roundBottomLeft();
            this.renderEngine
               .invoke10(
                  renderManagerState23.renderEngineBounds2.texture(),
                  renderManagerState23.renderEngineBounds2.width(),
                  renderManagerState23.renderEngineBounds2.height(),
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
                  s,
                  floatValue4,
                  this.matrix3Stack.resolve2(),
                  intValue3,
                  intValue4,
                  intValue5,
                  intValue6,
                  floatValue5,
                  floatValue6,
                  floatValue7,
                  floatValue8
               );
         }
      }
   }

   public void invoke16(RenderManager.RenderManagerState2 renderManagerState24, float f, float g, float h, float i, float j, int k, int l, int m, int n, float o, float p) {
      this.invoke3();
      if (renderManagerState24 != null && renderManagerState24.renderEngineBounds2 != null) {
         float floatValue9 = this.measure4();
         if (!(floatValue9 <= 1.0E-4F)) {
            RenderManager.RenderManagerData2 renderManagerData22 = this.arrayDeque.peek();
            int intValue7 = renderManagerData22 == null ? 0 : renderManagerData22.x();
            int intValue8 = renderManagerData22 == null ? 0 : renderManagerData22.y();
            int intValue9 = renderManagerData22 == null ? this.intValue : renderManagerData22.w();
            int intValue10 = renderManagerData22 == null ? this.intValue2 : renderManagerData22.h();
            float floatValue10 = renderManagerData22 == null ? 0.0F : renderManagerData22.roundTopLeft();
            float floatValue11 = renderManagerData22 == null ? 0.0F : renderManagerData22.roundTopRight();
            float floatValue12 = renderManagerData22 == null ? 0.0F : renderManagerData22.roundBottomRight();
            float floatValue13 = renderManagerData22 == null ? 0.0F : renderManagerData22.roundBottomLeft();
            this.renderEngine
               .invoke11(
                  renderManagerState24.renderEngineBounds2.texture(),
                  renderManagerState24.renderEngineBounds2.width(),
                  renderManagerState24.renderEngineBounds2.height(),
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
                  floatValue9,
                  this.matrix3Stack.resolve2(),
                  intValue7,
                  intValue8,
                  intValue9,
                  intValue10,
                  floatValue10,
                  floatValue11,
                  floatValue12,
                  floatValue13
               );
         }
      }
   }

   public void invoke17(
      RenderManager.RenderManagerState2 renderManagerState25,
      float f,
      float g,
      float h,
      float i,
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
      float t
   ) {
      this.invoke3();
      if (renderManagerState25 != null && renderManagerState25.renderEngineBounds2 != null) {
         float floatValue14 = this.measure4();
         if (!(floatValue14 <= 1.0E-4F)) {
            RenderManager.RenderManagerData2 renderManagerData23 = this.arrayDeque.peek();
            int intValue11 = renderManagerData23 == null ? 0 : renderManagerData23.x();
            int intValue12 = renderManagerData23 == null ? 0 : renderManagerData23.y();
            int intValue13 = renderManagerData23 == null ? this.intValue : renderManagerData23.w();
            int intValue14 = renderManagerData23 == null ? this.intValue2 : renderManagerData23.h();
            float floatValue15 = renderManagerData23 == null ? 0.0F : renderManagerData23.roundTopLeft();
            float floatValue16 = renderManagerData23 == null ? 0.0F : renderManagerData23.roundTopRight();
            float floatValue17 = renderManagerData23 == null ? 0.0F : renderManagerData23.roundBottomRight();
            float floatValue18 = renderManagerData23 == null ? 0.0F : renderManagerData23.roundBottomLeft();
            this.renderEngine
               .invoke12(
                  renderManagerState25.renderEngineBounds2.texture(),
                  renderManagerState25.renderEngineBounds2.width(),
                  renderManagerState25.renderEngineBounds2.height(),
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
                  s,
                  t,
                  floatValue14,
                  this.matrix3Stack.resolve2(),
                  intValue11,
                  intValue12,
                  intValue13,
                  intValue14,
                  floatValue15,
                  floatValue16,
                  floatValue17,
                  floatValue18
               );
         }
      }
   }

   public void invoke18(RenderManager.RenderManagerState2 renderManagerState26, float f, float g, float h, float i, float j, int k, int l, int m, float n, float o) {
      this.invoke3();
      if (renderManagerState26 != null && renderManagerState26.renderEngineBounds2 != null) {
         float floatValue19 = this.measure4();
         if (!(floatValue19 <= 1.0E-4F)) {
            RenderManager.RenderManagerData2 renderManagerData24 = this.arrayDeque.peek();
            int intValue15 = renderManagerData24 == null ? 0 : renderManagerData24.x();
            int intValue16 = renderManagerData24 == null ? 0 : renderManagerData24.y();
            int intValue17 = renderManagerData24 == null ? this.intValue : renderManagerData24.w();
            int intValue18 = renderManagerData24 == null ? this.intValue2 : renderManagerData24.h();
            float floatValue20 = renderManagerData24 == null ? 0.0F : renderManagerData24.roundTopLeft();
            float floatValue21 = renderManagerData24 == null ? 0.0F : renderManagerData24.roundTopRight();
            float floatValue22 = renderManagerData24 == null ? 0.0F : renderManagerData24.roundBottomRight();
            float floatValue23 = renderManagerData24 == null ? 0.0F : renderManagerData24.roundBottomLeft();
            this.renderEngine
               .invoke13(
                  renderManagerState26.renderEngineBounds2.texture(),
                  renderManagerState26.renderEngineBounds2.width(),
                  renderManagerState26.renderEngineBounds2.height(),
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
                  floatValue19,
                  this.matrix3Stack.resolve2(),
                  intValue15,
                  intValue16,
                  intValue17,
                  intValue18,
                  floatValue20,
                  floatValue21,
                  floatValue22,
                  floatValue23
               );
         }
      }
   }

   public void invoke19() {
      if (this.flag) {
         boolean flag2 = false ;

         label65: {
            try {
               flag2 = true;
               if (this.renderEngineFacade != null) {
                  this.renderEngineFacade.invoke6();
               }

               if (this.renderEngine != null) {
                  this.renderEngine.invoke15();
               }

               GlStateGuard.getINSTANCE().invoke3();
               flag2 = false;
               break label65;
            } catch (Exception exception2) {
               System.err.println("Error in Renderer2D.end(): " + exception2.getMessage());
               exception2.printStackTrace();
               flag2 = false;
            } finally {
               if (flag2) {
                  this.flag = false;
                  this.intValue = 0;
                  this.intValue2 = 0;
                  this.flag2 = false;
                  this.floatValue = 0.0F;
                  this.intValue3 = 0;
                  this.intValue4 = 0;
                  this.flag3 = false;
                  this.floatValue2 = 0.0F;
                  this.intValue5 = 0;
                  this.intValue6 = 0;
                  this.intValue7 = 0;
                  this.intValue8 = 0;
                  this.arrayDeque.clear();
                  this.matrix3Stack.invoke();
                  this.invoke73();
                  this.invoke74();
               }
            }

            this.flag = false;
            this.intValue = 0;
            this.intValue2 = 0;
            this.flag2 = false;
            this.floatValue = 0.0F;
            this.intValue3 = 0;
            this.intValue4 = 0;
            this.flag3 = false;
            this.floatValue2 = 0.0F;
            this.intValue5 = 0;
            this.intValue6 = 0;
            this.intValue7 = 0;
            this.intValue8 = 0;
            this.arrayDeque.clear();
            this.matrix3Stack.invoke();
            this.invoke73();
            this.invoke74();
            return;
         }

         this.flag = false;
         this.intValue = 0;
         this.intValue2 = 0;
         this.flag2 = false;
         this.floatValue = 0.0F;
         this.intValue3 = 0;
         this.intValue4 = 0;
         this.flag3 = false;
         this.floatValue2 = 0.0F;
         this.intValue5 = 0;
         this.intValue6 = 0;
         this.intValue7 = 0;
         this.intValue8 = 0;
         this.arrayDeque.clear();
         this.matrix3Stack.invoke();
         this.invoke73();
         this.invoke74();
      }
   }

   public void invoke20() {
      this.invoke3();
      this.renderEngineFacade.invoke6();
   }

   public void invoke21() {
      this.invoke3();
      this.invoke20();
      this.arrayDeque3.push(true);
      this.renderEngine.setFlag7(true);
   }

   public void invoke22() {
      this.invoke3();
      if (this.arrayDeque3.size() > 1) {
         this.invoke20();
         this.arrayDeque3.pop();
         this.renderEngine.setFlag7(this.arrayDeque3.peek());
         this.renderEngine.invoke20();
      }
   }

   public void invoke23(int i, int j, int k, int l) {
      this.invoke24((float)i, (float)j, (float)k, (float)l, 0.0F, 0.0F, 0.0F, 0.0F);
   }

   public void invoke24(float f, float g, float h, float i, float j, float k, float l, float m) {
      this.invoke3();
      RenderManager.RenderManagerData2 renderManagerData25 = RenderManager.RenderManagerData2.fromRect(f, g, h, i, j, k, l, m, this.matrix3Stack.resolve2());
      RenderManager.RenderManagerData2 renderManagerData26 = this.arrayDeque.isEmpty() ? renderManagerData25 : RenderManager.RenderManagerData2.intersect(this.arrayDeque.peek(), renderManagerData25);
      this.arrayDeque.push(renderManagerData26);
      this.invoke26(renderManagerData26);
   }

   public void invoke25() {
      this.invoke3();
      if (!this.arrayDeque.isEmpty()) {
         this.arrayDeque.pop();
         if (this.arrayDeque.isEmpty()) {
            this.renderEngine.setFlag5(false);
         } else {
            this.invoke26(this.arrayDeque.peek());
         }
      }
   }

   private void invoke26(RenderManager.RenderManagerData2 renderManagerData27) {
      if (renderManagerData27 == null) {
         this.renderEngine.setFlag5(false);
      } else {
         this.renderEngine.setFlag5(true);
         this.renderEngine
            .invoke22(
               renderManagerData27.x(),
               renderManagerData27.y(),
               renderManagerData27.w(),
               renderManagerData27.h(),
               renderManagerData27.roundTopLeft(),
               renderManagerData27.roundTopRight(),
               renderManagerData27.roundBottomRight(),
               renderManagerData27.roundBottomLeft()
            );
      }
   }

   public void invoke27(float f, float g, float h, float i, int j, float k) {
      this.invoke3();
      f--;
      g--;
      h += 2.0F;
      i += 2.0F;
      this.renderEngineFacade.invoke2(f, g, h, i, 0.0F, 0.0F, 0.0F, 0.0F, this.compute3(j), Math.max(1.0F, k), this.matrix3Stack.resolve2());
   }

   public void invoke28(float f, float g, float h, float i, float j, int k, float l) {
      this.invoke29(f, g, h, i, j, j, j, j, k, l);
   }

   public void invoke29(float f, float g, float h, float i, float j, float k, float l, float m, int n, float o) {
      this.invoke3();
      float[] floatValues4 = this.resolve(h, i, j, k, l, m);
      f--;
      g--;
      h += 2.0F;
      i += 2.0F;
      if (floatValues4[0] > 0.0F) {
         floatValues4[0]++;
      }

      if (floatValues4[1] > 0.0F) {
         floatValues4[1]++;
      }

      if (floatValues4[2] > 0.0F) {
         floatValues4[2]++;
      }

      if (floatValues4[3] > 0.0F) {
         floatValues4[3]++;
      }

      this.renderEngineFacade
         .invoke2(f, g, h, i, floatValues4[0], floatValues4[1], floatValues4[2], floatValues4[3], this.compute3(n), Math.max(1.0F, o), this.matrix3Stack.resolve2());
   }

   public void invoke30(float f, float g, float h, float i, int j, int k, int l, int m) {
      this.invoke3();
      this.renderEngineFacade
         .invoke3(
            f, g, h, i, 0.0F, 0.0F, 0.0F, 0.0F, this.compute3(j), this.compute3(k), this.compute3(l), this.compute3(m), this.matrix3Stack.resolve2()
         );
   }

   public void invoke31(float f, float g, float h, float i, float j, int k, int l, int m, int n) {
      this.invoke32(f, g, h, i, j, j, j, j, k, l, m, n);
   }

   public void invoke32(float f, float g, float h, float i, float j, float k, float l, float m, int n, int o, int p, int q) {
      this.invoke3();
      float[] floatValues5 = this.resolve(h, i, j, k, l, m);
      this.renderEngineFacade
         .invoke3(
            f,
            g,
            h,
            i,
            floatValues5[0],
            floatValues5[1],
            floatValues5[2],
            floatValues5[3],
            this.compute3(n),
            this.compute3(o),
            this.compute3(p),
            this.compute3(q),
            this.matrix3Stack.resolve2()
         );
   }

   public void invoke33(float f, float g, float h, float i, int j, int k) {
      this.invoke30(f, g, h, i, j, k, k, j);
   }

   public void invoke34(float f, float g, float h, float i, float j, int k, int l) {
      this.invoke31(f, g, h, i, j, k, l, l, k);
   }

   public void invoke35(float f, float g, float h, float i, float j, float k, float l, float m, int n, int o) {
      this.invoke32(f, g, h, i, j, k, l, m, n, o, o, n);
   }

   public void invoke36(float f, float g, float h, float i, int j, int k) {
      this.invoke30(f, g, h, i, j, j, k, k);
   }

   public void invoke37(float f, float g, float h, float i, float j, int k, int l) {
      this.invoke31(f, g, h, i, j, k, k, l, l);
   }

   public void invoke38(float f, float g, float h, float i, float j, float k, float l, float m, int n, int o) {
      this.invoke32(f, g, h, i, j, k, l, m, n, n, o, o);
   }

   public void invoke39(float f, float g, float h, float i, float j, int k) {
      this.invoke3();
      this.renderEngineFacade.invoke4(f, g, h, i, j, this.compute3(k), this.matrix3Stack.resolve2());
   }

   public void invoke40(float f, float g, float h, float i, float j, float k, int l) {
      this.invoke3();
      this.renderEngineFacade.invoke5(f, g, h, i, j, k, this.compute3(l), this.matrix3Stack.resolve2());
   }

   public void invoke41(float f, float g, float h, float i, float j, float k, float l, int m) {
      this.invoke42(f, g, h, i, j, j, j, j, k, l, m);
   }

   public void invoke42(float f, float g, float h, float i, float j, float k, float l, float m, float n, float o, int p) {
      this.invoke3();
      if (!(h <= 0.0F) && !(i <= 0.0F)) {
         boolean flag3 = true;

         try {
            flag3 = booleanSupplier2 == null || booleanSupplier2.getAsBoolean();
         } catch (Throwable exception3) {
         }

         float floatValue24 = Math.max(0.0F, n);
         if (!flag3 && floatValue24 > 6.0F) {
            floatValue24 = Math.min(floatValue24, 6.0F);
         }

         float floatValue25 = Math.max(0.0F, o);
         if (!(floatValue24 <= 0.0F) || !(floatValue25 <= 0.0F)) {
            float[] floatValues6 = resolve8(j, k, l, m);
            invoke75(h, i, floatValues6);
            this.invoke20();
            this.renderEngine
               .invoke34(f, g, h, i, floatValues6[0], floatValues6[1], floatValues6[2], floatValues6[3], floatValue24, floatValue25, this.compute3(p), this.matrix3Stack.resolve2());
         }
      }
   }

   public void invoke43(float f, float g, float h, float i, float j) {
      this.invoke44(f, g, h, i, j, 1.0F);
   }

   public void invoke44(float f, float g, float h, float i, float j, float k) {
      this.invoke45(f, g, h, i, j, j, j, j, k);
   }

   public void invoke45(float f, float g, float h, float i, float j, float k, float l, float m, float n) {
      this.invoke3();
      if (this.flag2) {
         float floatValue26 = measure5(n) * this.measure4();
         if (!(floatValue26 <= 1.0E-4F)) {
            float[] floatValues7 = resolve8(j, k, l, m);
            invoke75(h, i, floatValues7);
            this.invoke20();
            this.renderEngine.invoke57(f, g, h, i, floatValues7[0], floatValues7[1], floatValues7[2], floatValues7[3], floatValue26, this.matrix3Stack.resolve2());
         }
      }
   }

   public void invoke46(float f, float g, float h, float i, float j) {
      this.invoke47(f, g, h, i, j, 1.0F);
   }

   public void invoke47(float f, float g, float h, float i, float j, float k) {
      this.invoke3();
      if (this.flag3) {
         float floatValue27 = measure5(k) * this.measure4();
         if (!(floatValue27 <= 1.0E-4F)) {
            this.invoke20();
            this.renderEngine
               .invoke58(
                  f,
                  g,
                  h,
                  i,
                  Math.max(0.0F, j),
                  floatValue27,
                  this.matrix3Stack.resolve2(),
                  this.intValue5,
                  this.intValue6,
                  this.intValue7,
                  this.intValue8
               );
         }
      }
   }

   public void invoke48(float f) {
      this.invoke3();

      try {
         if (booleanSupplier != null && !booleanSupplier.getAsBoolean()) {
            this.flag2 = false;
            this.intValue3 = 0;
            this.intValue4 = 0;
            return;
         }
      } catch (Throwable exception4) {
      }

      int intValue19 = this.intValue;
      int intValue20 = this.intValue2;
      if (intValue19 > 0 && intValue20 > 0) {
         float floatValue28 = Math.max(0.5F, f);
         boolean flag4 = this.flag2 && this.intValue3 == intValue19 && this.intValue4 == intValue20 && Math.abs(this.floatValue - floatValue28) <= 0.05F;
         if (!flag4) {
            this.invoke20();
            this.renderEngine.invoke55(intValue19, intValue20, floatValue28);
            this.flag2 = true;
            this.floatValue = floatValue28;
            this.intValue3 = intValue19;
            this.intValue4 = intValue20;
         }
      } else {
         this.flag2 = false;
         this.intValue3 = 0;
         this.intValue4 = 0;
      }
   }

   public static void invoke49(boolean bl) {
      if (bl) {
         GlStateManager._enableBlend();
         GL11.glBlendFunc(770, 771);
         GlStateManager._disableCull();
         GlStateManager._blendFuncSeparate(770, 771, 1, 0);
         GlStateManager._colorMask(true, true, true, true);
      } else {
         GlStateManager._colorMask(true, true, true, true);
         GlStateManager._enableBlend();
      }
   }

   public static void invoke50(boolean bl) {
      if (bl) {
         GlStateManager._colorMask(true, true, true, true);
         GlStateManager._blendFuncSeparate(770, 771, 1, 0);
         GlStateManager._enableCull();
         GlStateManager._disableBlend();
      } else {
         GlStateManager._colorMask(true, true, true, true);
         GlStateManager._enableBlend();
      }
   }

   public void invoke51(float f, float g, float h, float i, float j) {
      this.invoke3();
      if (this.intValue > 0 && this.intValue2 > 0 && !(h <= 0.0F) && !(i <= 0.0F)) {
         float[] floatValues8 = this.matrix3Stack.resolve2();
         RenderManager.RenderManagerData renderManagerData = resolve5(floatValues8, f, g, h, i);
         int intValue21 = compute(renderManagerData.minX, this.intValue);
         int intValue22 = compute(renderManagerData.minY, this.intValue2);
         int intValue23 = compute2(renderManagerData.maxX, this.intValue);
         int intValue24 = compute2(renderManagerData.maxY, this.intValue2);
         int intValue25 = Math.max(0, intValue23 - intValue21);
         int intValue26 = Math.max(0, intValue24 - intValue22);
         if (intValue25 > 0 && intValue26 > 0) {
            float floatValue29 = Math.max(0.5F, j);
            boolean flag5 = this.flag3
               && this.intValue5 == intValue21
               && this.intValue6 == intValue22
               && this.intValue7 == intValue25
               && this.intValue8 == intValue26
               && Math.abs(this.floatValue2 - floatValue29) <= 0.05F;
            if (!flag5) {
               this.invoke20();
               boolean flag6 = this.renderEngine.check(intValue21, intValue22, intValue25, intValue26, floatValue29);
               this.flag3 = flag6;
               if (flag6) {
                  this.floatValue2 = floatValue29;
                  this.intValue5 = intValue21;
                  this.intValue6 = intValue22;
                  this.intValue7 = intValue25;
                  this.intValue8 = intValue26;
               } else {
                  this.floatValue2 = 0.0F;
                  this.intValue7 = 0;
                  this.intValue8 = 0;
               }
            }
         } else {
            this.flag3 = false;
            this.intValue7 = 0;
            this.intValue8 = 0;
         }
      } else {
         this.flag3 = false;
         this.intValue7 = 0;
         this.intValue8 = 0;
      }
   }

   private static int compute(float f, int i) {
      int intValue27 = (int)Math.floor(f);
      if (intValue27 < 0) {
         return 0;
      } else {
         return intValue27 > i ? i : intValue27;
      }
   }

   private static int compute2(float f, int i) {
      int intValue28 = (int)Math.ceil(f);
      if (intValue28 < 0) {
         return 0;
      } else {
         return intValue28 > i ? i : intValue28;
      }
   }

   private static RenderManager.RenderManagerData resolve5(float[] fs, float f, float g, float h, float i) {
      float floatValue30 = f + h;
      float floatValue31 = g + i;
      float floatValue32 = measure(fs, f, g);
      float floatValue33 = measure2(fs, f, g);
      float floatValue34 = measure(fs, floatValue30, g);
      float floatValue35 = measure2(fs, floatValue30, g);
      float floatValue36 = measure(fs, floatValue30, floatValue31);
      float floatValue37 = measure2(fs, floatValue30, floatValue31);
      float floatValue38 = measure(fs, f, floatValue31);
      float floatValue39 = measure2(fs, f, floatValue31);
      float floatValue40 = Math.min(Math.min(floatValue32, floatValue34), Math.min(floatValue36, floatValue38));
      float floatValue41 = Math.max(Math.max(floatValue32, floatValue34), Math.max(floatValue36, floatValue38));
      float floatValue42 = Math.min(Math.min(floatValue33, floatValue35), Math.min(floatValue37, floatValue39));
      float floatValue43 = Math.max(Math.max(floatValue33, floatValue35), Math.max(floatValue37, floatValue39));
      return new RenderManager.RenderManagerData(floatValue40, floatValue42, floatValue41, floatValue43);
   }

   private static float measure(float[] fs, float f, float g) {
      return fs != null && fs.length >= 6 ? fs[0] * f + fs[1] * g + fs[2] : f;
   }

   private static float measure2(float[] fs, float f, float g) {
      return fs != null && fs.length >= 6 ? fs[3] * f + fs[4] * g + fs[5] : g;
   }

   public void invoke52(float[] fs) {
      this.invoke3();
      this.matrix3Stack.invoke();
      this.matrix3Stack.invoke9(fs);
   }

   public void invoke53(float[] fs) {
      this.invoke3();
      this.matrix3Stack.invoke8(fs);
   }

   public void invoke54(float f) {
      this.invoke3();
      this.matrix3Stack.invoke3(f);
   }

   public void invoke55() {
      this.invoke3();
      this.matrix3Stack.invoke11();
   }

   public void invoke56(float f, float g) {
      this.invoke3();
      this.matrix3Stack.invoke4(f, g);
   }

   public void invoke57() {
      this.invoke3();
      this.matrix3Stack.invoke11();
   }

   public void invoke58(float f) {
      this.invoke59(f, f);
   }

   public void invoke59(float f, float g) {
      this.invoke3();
      this.matrix3Stack.invoke6(f, g, 0.0F, 0.0F);
   }

   public void invoke60(float f) {
      this.invoke61(f, f);
   }

   public void invoke61(float f, float g) {
      this.invoke3();
      if (this.intValue > 0 && this.intValue2 > 0) {
         this.matrix3Stack.invoke6(f, g, this.intValue * 0.5F, this.intValue2 * 0.5F);
      } else {
         throw new IllegalStateException("Cannot compute frame center before begin(width, height) is called with positive dimensions");
      }
   }

   public void invoke62(float f, float g, float h) {
      this.invoke63(f, f, g, h);
   }

   public void invoke63(float f, float g, float h, float i) {
      this.invoke3();
      this.matrix3Stack.invoke6(f, g, h, i);
   }

   public void invoke64() {
      this.invoke3();
      this.matrix3Stack.invoke11();
   }

   public void invoke65(float f) {
      this.invoke3();
      float floatValue44 = this.measure4();
      float floatValue45 = measure5(f);
      this.arrayDeque2.push(floatValue44 * floatValue45);
   }

   public void invoke66() {
      this.invoke3();
      if (this.arrayDeque2.size() > 1) {
         this.arrayDeque2.pop();
      }
   }

   public void invoke67(String string, FontRenderer fontRenderer) {
      if (fontRenderer != null) {
         valuesByKey.put(string, fontRenderer);
      }
   }

   public void invoke68(FontObject fontObject, FontRenderer fontRenderer2) {
      if (fontRenderer2 != null) {
         valuesByKey.put(fontObject.text, fontRenderer2);
      }
   }

   public Matrix3Stack getMatrix3Stack() {
      return this.matrix3Stack;
   }

   public float[] resolve6() {
      this.invoke3();
      if (this.arrayDeque4.isEmpty()) {
         return this.matrix3Stack.resolve2();
      } else {
         RenderManager.RenderManagerData3 renderManagerData3 = this.arrayDeque4.peek();
         float[] floatValues9 = this.matrix3Stack.resolve2();
         float[] floatValues10 = new float[]{floatValues9[0], floatValues9[1], floatValues9[2] + renderManagerData3.originX(), floatValues9[3], floatValues9[4], floatValues9[5] + renderManagerData3.originY(), floatValues9[6], floatValues9[7], floatValues9[8]};
         return resolve9(renderManagerData3.rootTransform(), floatValues10);
      }
   }

   public float measure3() {
      return this.measure4();
   }

   public void invoke69(FontObject fontObject2, float f, float g, float h, String string, int i) {
      this.invoke3();
      if (fontObject2 == null) {
         throw new IllegalArgumentException("FontObject must not be null");
      } else if (!(h <= 0.0F)) {
         FontRenderer fontRenderer3 = valuesByKey.get(fontObject2.text);
         if (fontRenderer3 != null) {
            fontRenderer3.invoke2(f, g, h / 2.0F, string, this.compute3(i), this.matrix3Stack.resolve2());
         }
      }
   }

   public void invoke70(FontObject fontObject3, float f, float g, float h, String string, int i, String string2) {
      this.invoke3();
      if (fontObject3 == null) {
         throw new IllegalArgumentException("FontObject must not be null");
      } else if (!(h <= 0.0F)) {
         FontRenderer fontRenderer4 = valuesByKey.get(fontObject3.text);
         if (fontRenderer4 != null) {
            fontRenderer4.invoke4(f, g, h / 2.0F, string, this.compute3(i), string2, this.matrix3Stack.resolve2());
         }
      }
   }

   public void invoke71(FontObject fontObject4, float f, float g, float h, String string, int i, int j, float k) {
      this.invoke72(fontObject4, f, g, h, string, i, j, k, "l");
   }

   public void invoke72(FontObject fontObject5, float f, float g, float h, String string, int i, int j, float k, String string2) {
      this.invoke3();
      if (fontObject5 == null) {
         throw new IllegalArgumentException("FontObject must not be null");
      } else if (!(h <= 0.0F)) {
         FontRenderer fontRenderer5 = valuesByKey.get(fontObject5.text);
         if (fontRenderer5 != null) {
            fontRenderer5.invoke5(f, g, h / 2.0F, string, this.compute3(i), this.compute3(j), k, string2, this.matrix3Stack.resolve2());
         }
      }
   }

   public static FontRenderer.FontRendererState resolve7(FontObject fontObject6, String string, float f) {
      if (fontObject6 == null) {
         throw new IllegalArgumentException("FontObject must not be null");
      } else if (f <= 0.0F) {
         return new FontRenderer.FontRendererState(0.0F, 0.0F);
      } else {
         FontRenderer fontRenderer6 = valuesByKey.get(fontObject6.text);
         if (fontRenderer6 == null) {
            return new FontRenderer.FontRendererState(0.0F, 0.0F);
         } else {
            String text = string == null ? "" : string;
            return fontRenderer6.resolve(text, f / 2.0F);
         }
      }
   }

   private void invoke73() {
      this.arrayDeque2.clear();
      this.arrayDeque2.push(1.0F);
   }

   private void invoke74() {
      this.arrayDeque3.clear();
      this.arrayDeque3.push(false);
      if (this.renderEngine != null) {
         this.renderEngine.setFlag7(false);
      }
   }

   private float measure4() {
      return this.arrayDeque2.isEmpty() ? 1.0F : this.arrayDeque2.peek();
   }

   private int compute3(int i) {
      float floatValue46 = this.measure4();
      if (floatValue46 >= 0.999F) {
         return i;
      } else {
         int intValue29 = i >>> 24 & 0xFF;
         int intValue30 = i >>> 16 & 0xFF;
         int intValue31 = i >>> 8 & 0xFF;
         int intValue32 = i & 0xFF;
         int intValue33 = compute4(intValue29, floatValue46);
         int intValue34 = compute4(intValue30, floatValue46);
         int intValue35 = compute4(intValue31, floatValue46);
         int intValue36 = compute4(intValue32, floatValue46);
         return intValue33 << 24 | intValue34 << 16 | intValue35 << 8 | intValue36;
      }
   }

   private static int compute4(int i, float f) {
      float floatValue47 = i * f;
      if (floatValue47 <= 0.0F) {
         return 0;
      } else {
         return floatValue47 >= 255.0F ? 255 : Math.round(floatValue47);
      }
   }

   private static float measure5(float f) {
      if (f < 0.0F) {
         return 0.0F;
      } else {
         return f > 1.0F ? 1.0F : f;
      }
   }

   static float[] resolve8(float f, float g, float h, float i) {
      float[] floatValues11 = THREAD_LOCAL.get();
      floatValues11[0] = f;
      floatValues11[1] = g;
      floatValues11[2] = h;
      floatValues11[3] = i;
      return floatValues11;
   }

   static void invoke75(float f, float g, float[] fs) {
      if (fs != null && fs.length >= 4) {
         float floatValue48 = Math.abs(f);
         float floatValue49 = Math.abs(g);

         for (int intValue37 = 0; intValue37 < 4; intValue37++) {
            float floatValue50 = fs[intValue37];
            if (!Float.isFinite(floatValue50)) {
               floatValue50 = 0.0F;
            }

            fs[intValue37] = Math.max(0.0F, floatValue50);
         }

         if (!(floatValue48 <= 0.0F) && !(floatValue49 <= 0.0F)) {
            float floatValue51 = Math.min(floatValue48, floatValue49) * 0.5F;

            for (int intValue38 = 0; intValue38 < 4; intValue38++) {
               fs[intValue38] = Math.min(fs[intValue38], floatValue51);
            }
         } else {
            Arrays.fill(fs, 0.0F);
         }
      } else {
         throw new IllegalArgumentException("radii");
      }
   }

   private static boolean check(float f, float g) {
      return Math.abs(f - g) <= 1.0E-4F;
   }

   private static boolean check2(float f) {
      return Math.abs(f) <= 1.0E-4F;
   }

   static boolean check3(float[] fs) {
      return fs != null && fs.length >= 9
         ? check(fs[0], 1.0F)
            && check2(fs[1])
            && check2(fs[2])
            && check2(fs[3])
            && check(fs[4], 1.0F)
            && check2(fs[5])
            && check2(fs[6])
            && check2(fs[7])
            && check(fs[8], 1.0F)
         : true;
   }

   static boolean check4(float[] fs) {
      return fs != null && fs.length >= 9
         ? check2(fs[1]) && check2(fs[3]) && check2(fs[6]) && check2(fs[7]) && check(fs[8], 1.0F)
         : true;
   }

   static float measure6(float[] fs, float f, float g) {
      return fs != null && fs.length >= 9 ? fs[0] * f + fs[1] * g + fs[2] : f;
   }

   static float measure7(float[] fs, float f, float g) {
      return fs != null && fs.length >= 9 ? fs[3] * f + fs[4] * g + fs[5] : g;
   }

   static float measure8(float[] fs) {
      if (fs != null && fs.length >= 9) {
         float floatValue52 = Math.abs(fs[0]);
         float floatValue53 = Math.abs(fs[4]);
         float floatValue54 = Math.min(floatValue52, floatValue53);
         return floatValue54 <= 1.0E-4F ? 0.0F : floatValue54;
      } else {
         return 1.0F;
      }
   }

   private static float[] resolve9(float[] fs, float[] gs) {
      return new float[]{
         fs[0] * gs[0] + fs[1] * gs[3] + fs[2] * gs[6],
         fs[0] * gs[1] + fs[1] * gs[4] + fs[2] * gs[7],
         fs[0] * gs[2] + fs[1] * gs[5] + fs[2] * gs[8],
         fs[3] * gs[0] + fs[4] * gs[3] + fs[5] * gs[6],
         fs[3] * gs[1] + fs[4] * gs[4] + fs[5] * gs[7],
         fs[3] * gs[2] + fs[4] * gs[5] + fs[5] * gs[8],
         fs[6] * gs[0] + fs[7] * gs[3] + fs[8] * gs[6],
         fs[6] * gs[1] + fs[7] * gs[4] + fs[8] * gs[7],
         fs[6] * gs[2] + fs[7] * gs[5] + fs[8] * gs[8]
      };
   }

   public static void invoke76(MatrixStack matrixStack, float f, float g, float h) {
      invoke77(matrixStack, (double)f, (double)g, (double)h);
   }

   public static void invoke77(MatrixStack matrixStack, double d, double e, double f) {
      Vec3d vec3d = client.getEntityRenderDispatcher().camera.getPos();
      matrixStack.translate(d - vec3d.x, e - vec3d.y, f - vec3d.z);
   }

   public static Vector2d resolve10(double d, double e, double f) {
      Camera camera = client.getEntityRenderDispatcher().camera;
      if (camera == null) {
         return new Vector2d(0.0, 0.0);
      } else {
         Vec3d vec3d2 = camera.getPos();
         Quaternionf quaternionf = new Quaternionf(camera.getRotation());
         quaternionf.conjugate();
         Vector3f vector3f = new Vector3f((float)(vec3d2.x - d), (float)(vec3d2.y - e), (float)(vec3d2.z - f));
         vector3f.rotate(quaternionf);
         float floatValue55 = client.getRenderTickCounter().getDynamicDeltaTicks();
         if ((Boolean)client.options.getBobView().getValue() && client.getCameraEntity() instanceof PlayerEntity playerEntity) {
            float floatValue56 = playerEntity.strideDistance;
            float floatValue57 = floatValue56 - playerEntity.lastStrideDistance;
            float floatValue58 = -(floatValue56 + floatValue57 * floatValue55);
            float floatValue59 = camera.getYaw();
            float floatValue60 = Math.abs(MathHelper.cos(floatValue58 * (float) Math.PI - 0.2F) * floatValue59) * 5.0F;
            Quaternionf quaternionf2 = new Quaternionf().rotateAxis((float)Math.toRadians(floatValue60), new Vector3f(1.0F, 0.0F, 0.0F));
            quaternionf2.conjugate();
            vector3f.rotate(quaternionf2);
            float floatValue61 = MathHelper.sin(floatValue58 * (float) Math.PI) * floatValue59 * 3.0F;
            Quaternionf quaternionf3 = new Quaternionf().rotateAxis((float)Math.toRadians(floatValue61), new Vector3f(0.0F, 0.0F, 1.0F));
            quaternionf3.conjugate();
            vector3f.rotate(quaternionf3);
            Vector3f vector3f2 = new Vector3f(
               MathHelper.sin(floatValue58 * (float) Math.PI) * floatValue59 * 0.5F, -Math.abs(MathHelper.cos(floatValue58 * (float) Math.PI) * floatValue59), 0.0F
            );
            vector3f2.y = -vector3f2.y;
            vector3f.add(vector3f2);
         }

         double doubleValue = ((GameRendererAccessor)client.gameRenderer).invokeGetFov(camera, floatValue55, true);
         float floatValue62 = client.getWindow().getScaledHeight() / 2.0F;
         float floatValue63 = floatValue62 / (vector3f.z() * (float)Math.tan(Math.toRadians(doubleValue / 2.0)));
         return vector3f.z() < 0.0F
            ? new Vector2d(-vector3f.x() * floatValue63 + client.getWindow().getScaledWidth() / 2, client.getWindow().getScaledHeight() / 2 - vector3f.y() * floatValue63)
            : null;
      }
   }

   record RenderManagerData(float minX, float minY, float maxX, float maxY) {
   }

   record RenderManagerData2(int x, int y, int w, int h, float roundTopLeft, float roundTopRight, float roundBottomRight, float roundBottomLeft) {
      private static RenderManager.RenderManagerData2 fromRect(float f, float g, float h, float i, float j, float k, float l, float m) {
         return fromRect(f, g, h, i, j, k, l, m, null);
      }

      static RenderManager.RenderManagerData2 fromRect(float f, float g, float h, float i, float j, float k, float l, float m, float[] fs) {
         if (Float.isFinite(f) && Float.isFinite(g) && Float.isFinite(h) && Float.isFinite(i)) {
            boolean flag7 = fs != null && fs.length >= 9 && !RenderManager.check3(fs);
            float[] floatValues12 = RenderManager.resolve8(j, k, l, m);
            RenderManager.invoke75(Math.abs(h), Math.abs(i), floatValues12);
            if (!flag7) {
               float floatValue64 = (float)Math.floor(Math.min(f, f + h));
               float floatValue65 = (float)Math.floor(Math.min(g, g + i));
               float floatValue66 = (float)Math.ceil(Math.max(f, f + h));
               float floatValue67 = (float)Math.ceil(Math.max(g, g + i));
               int intValue39 = (int)floatValue64;
               int intValue40 = (int)floatValue65;
               int intValue41 = Math.max(0, (int)(floatValue66 - floatValue64));
               int intValue42 = Math.max(0, (int)(floatValue67 - floatValue65));
               return intValue41 > 0 && intValue42 > 0
                  ? new RenderManager.RenderManagerData2(intValue39, intValue40, intValue41, intValue42, floatValues12[0], floatValues12[1], floatValues12[2], floatValues12[3])
                  : new RenderManager.RenderManagerData2(intValue39, intValue40, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F);
            } else {
               float floatValue68 = f + h;
               float floatValue69 = g + i;
               float floatValue70 = Float.POSITIVE_INFINITY;
               float floatValue71 = Float.POSITIVE_INFINITY;
               float floatValue72 = Float.NEGATIVE_INFINITY;
               float floatValue73 = Float.NEGATIVE_INFINITY;

               for (int intValue43 = 0; intValue43 < 4; intValue43++) {
                  float floatValue74 = (intValue43 & 1) == 0 ? f : floatValue68;
                  float floatValue75 = intValue43 < 2 ? g : floatValue69;
                  float floatValue76 = RenderManager.measure6(fs, floatValue74, floatValue75);
                  float floatValue77 = RenderManager.measure7(fs, floatValue74, floatValue75);
                  if (!Float.isFinite(floatValue76) || !Float.isFinite(floatValue77)) {
                     return new RenderManager.RenderManagerData2(0, 0, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F);
                  }

                  if (floatValue76 < floatValue70) {
                     floatValue70 = floatValue76;
                  }

                  if (floatValue76 > floatValue72) {
                     floatValue72 = floatValue76;
                  }

                  if (floatValue77 < floatValue71) {
                     floatValue71 = floatValue77;
                  }

                  if (floatValue77 > floatValue73) {
                     floatValue73 = floatValue77;
                  }
               }

               float floatValue78 = (float)Math.floor(Math.min(floatValue70, floatValue72));
               float floatValue79 = (float)Math.floor(Math.min(floatValue71, floatValue73));
               float floatValue80 = (float)Math.ceil(Math.max(floatValue70, floatValue72));
               float floatValue81 = (float)Math.ceil(Math.max(floatValue71, floatValue73));
               int intValue44 = (int)floatValue78;
               int intValue45 = (int)floatValue79;
               int intValue46 = Math.max(0, (int)(floatValue80 - floatValue78));
               int intValue47 = Math.max(0, (int)(floatValue81 - floatValue79));
               if (intValue46 > 0 && intValue47 > 0) {
                  if (RenderManager.check4(fs)) {
                     float floatValue82 = RenderManager.measure8(fs);
                     if (floatValue82 > 0.0F) {
                        for (int intValue48 = 0; intValue48 < floatValues12.length; intValue48++) {
                           floatValues12[intValue48] *= floatValue82;
                        }
                     } else {
                        Arrays.fill(floatValues12, 0.0F);
                     }
                  } else {
                     Arrays.fill(floatValues12, 0.0F);
                  }

                  RenderManager.invoke75(Math.abs(floatValue80 - floatValue78), Math.abs(floatValue81 - floatValue79), floatValues12);
                  return new RenderManager.RenderManagerData2(intValue44, intValue45, intValue46, intValue47, floatValues12[0], floatValues12[1], floatValues12[2], floatValues12[3]);
               } else {
                  return new RenderManager.RenderManagerData2(intValue44, intValue45, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F);
               }
            }
         } else {
            return new RenderManager.RenderManagerData2(0, 0, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F);
         }
      }

      static RenderManager.RenderManagerData2 intersect(RenderManager.RenderManagerData2 renderManagerData28, RenderManager.RenderManagerData2 renderManagerData29) {
         if (renderManagerData28 == null) {
            return renderManagerData29;
         } else if (renderManagerData29 == null) {
            return renderManagerData28;
         } else {
            int intValue49 = Math.max(renderManagerData28.x, renderManagerData29.x);
            int intValue50 = Math.max(renderManagerData28.y, renderManagerData29.y);
            int intValue51 = Math.min(renderManagerData28.x + renderManagerData28.w, renderManagerData29.x + renderManagerData29.w);
            int intValue52 = Math.min(renderManagerData28.y + renderManagerData28.h, renderManagerData29.y + renderManagerData29.h);
            int intValue53 = Math.max(0, intValue51 - intValue49);
            int intValue54 = Math.max(0, intValue52 - intValue50);
            if (intValue53 <= 0 || intValue54 <= 0) {
               return new RenderManager.RenderManagerData2(intValue49, intValue50, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F);
            } else if (matchesRect(intValue49, intValue50, intValue53, intValue54, renderManagerData29)) {
               return new RenderManager.RenderManagerData2(
                  intValue49, intValue50, intValue53, intValue54, renderManagerData29.roundTopLeft, renderManagerData29.roundTopRight, renderManagerData29.roundBottomRight, renderManagerData29.roundBottomLeft
               );
            } else {
               return matchesRect(intValue49, intValue50, intValue53, intValue54, renderManagerData28)
                  ? new RenderManager.RenderManagerData2(
                     intValue49, intValue50, intValue53, intValue54, renderManagerData28.roundTopLeft, renderManagerData28.roundTopRight, renderManagerData28.roundBottomRight, renderManagerData28.roundBottomLeft
                  )
                  : new RenderManager.RenderManagerData2(intValue49, intValue50, intValue53, intValue54, 0.0F, 0.0F, 0.0F, 0.0F);
            }
         }
      }

      private static boolean matchesRect(int i, int j, int k, int l, RenderManager.RenderManagerData2 renderManagerData210) {
         return renderManagerData210 != null && renderManagerData210.x == i && renderManagerData210.y == j && renderManagerData210.w == k && renderManagerData210.h == l;
      }
   }

   public static class RenderManagerState {
      public static float measure(int i) {
         return (i >> 16 & 0xFF) / 255.0F;
      }

      public static float measure2(int i) {
         return (i >> 8 & 0xFF) / 255.0F;
      }

      public static float measure3(int i) {
         return (i & 0xFF) / 255.0F;
      }

      public static float measure4(int i) {
         return (i >> 24 & 0xFF) / 255.0F;
      }

      public static Color resolve(Color color, int i) {
         return new Color(color.getRed(), color.getGreen(), color.getBlue(), i);
      }

      public static Color resolve2(Color color, Color color2, double d) {
         float floatValue83 = RenderMath.measure49((float)Math.sin((Math.PI * 6) * (d / 4.0 % 1.0)) / 2.0F + 0.5F, 0.0F, 1.0F);
         return new Color(ColorUtils.compute14(color.getRGB(), color2.getRGB(), floatValue83), true);
      }

      public static Color resolve3(Color color, int i) {
         return new Color(color.getRed(), color.getGreen(), color.getBlue(), i);
      }

      public static int compute(int i, int j) {
         return i & 16777215 | j << 24;
      }

      public static int compute2() {
         return compute6(10, 255);
      }

      private static Theme resolve4() {
         if (WildClient.INSTANCE != null && WildClient.INSTANCE.themeManager != null) {
            return WildClient.INSTANCE.themeManager.getTheme();
         } else {
            return LegacyClickGuiState.theme != null ? LegacyClickGuiState.theme : Theme.WILD;
         }
      }

      private static Theme resolve5() {
         return LegacyClickGuiState.theme2 != null ? LegacyClickGuiState.theme2 : resolve4();
      }

      public static int[] resolve6(int i, int j) {
         Theme theme = resolve4();
         Theme theme2 = resolve5();
         return new int[]{
            compute16(
               compute31(i, 0, compute11(theme.getColor().getRGB(), theme2.getColor().getRGB(), (double)(1.0F - LegacyClickGuiState.directionalAnimation4.measure3()))),
               (float)j
            ),
            compute16(
               compute31(i, 90, compute11(theme.getColor().getRGB(), theme2.getColor().getRGB(), (double)(1.0F - LegacyClickGuiState.directionalAnimation4.measure3()))),
               (float)j
            ),
            compute16(
               compute31(
                  i, 180, compute11(theme.getColor().getRGB(), theme2.getColor().getRGB(), (double)(1.0F - LegacyClickGuiState.directionalAnimation4.measure3()))
               ),
               (float)j
            ),
            compute16(
               compute31(
                  i, 270, compute11(theme.getColor().getRGB(), theme2.getColor().getRGB(), (double)(1.0F - LegacyClickGuiState.directionalAnimation4.measure3()))
               ),
               (float)j
            )
         };
      }

      public static int compute3(int i, int j) {
         Theme theme3 = resolve4();
         Theme theme4 = resolve5();
         return compute9(
            compute11(theme3.getColor2().getRGB(), theme4.getColor2().getRGB(), (double)(1.0F - LegacyClickGuiState.directionalAnimation4.measure3())),
            compute11(theme3.getColor2().getRGB(), theme4.getColor2().getRGB(), (double)(1.0F - LegacyClickGuiState.directionalAnimation4.measure3())),
            i,
            j
         );
      }

      public static int compute4(int i, int j) {
         Theme theme5 = resolve4();
         Theme theme6 = resolve5();
         return compute9(
            compute11(theme5.getColor3().getRGB(), theme6.getColor3().getRGB(), (double)(1.0F - LegacyClickGuiState.directionalAnimation4.measure3())),
            compute11(theme5.getColor3().getRGB(), theme6.getColor3().getRGB(), (double)(1.0F - LegacyClickGuiState.directionalAnimation4.measure3())),
            i,
            j
         );
      }

      public static int compute5(int i, int j) {
         Theme theme7 = resolve4();
         Theme theme8 = resolve5();
         return compute9(
            compute11(theme7.getColor4().getRGB(), theme8.getColor4().getRGB(), (double)(1.0F - LegacyClickGuiState.directionalAnimation4.measure3())),
            compute11(theme7.getColor4().getRGB(), theme8.getColor4().getRGB(), (double)(1.0F - LegacyClickGuiState.directionalAnimation4.measure3())),
            i,
            j
         );
      }

      public static int compute6(int i, int j) {
         Theme theme9 = resolve4();
         Theme theme10 = resolve5();
         return compute9(
            compute11(theme9.getColor().getRGB(), theme10.getColor().getRGB(), (double)(1.0F - LegacyClickGuiState.directionalAnimation4.measure3())),
            compute11(theme9.getColor().getRGB(), theme10.getColor().getRGB(), (double)(1.0F - LegacyClickGuiState.directionalAnimation4.measure3())),
            i,
            j
         );
      }

      public static int compute7(int i, int j) {
         Theme theme11 = resolve4();
         Theme theme12 = resolve5();
         return compute9(
            compute11(theme11.getColor5().getRGB(), theme12.getColor5().getRGB(), (double)(1.0F - LegacyClickGuiState.directionalAnimation4.measure3())),
            compute11(theme11.getColor5().getRGB(), theme12.getColor5().getRGB(), (double)(1.0F - LegacyClickGuiState.directionalAnimation4.measure3())),
            i,
            j
         );
      }

      public static int compute8(int i, int j) {
         Theme theme13 = resolve4();
         Theme theme14 = resolve5();
         return compute9(
            compute11(theme13.getColor6().getRGB(), theme14.getColor6().getRGB(), (double)(1.0F - LegacyClickGuiState.directionalAnimation4.measure3())),
            compute11(theme13.getColor6().getRGB(), theme14.getColor6().getRGB(), (double)(1.0F - LegacyClickGuiState.directionalAnimation4.measure3())),
            i,
            j
         );
      }

      public Color resolve7(Color color, Color color2, double d) {
         d = 1.0 - d;
         return new Color(ColorUtils.compute13(color.getRGB(), color2.getRGB(), d), true);
      }

      public static Color resolve8(int i, int j, Color color, Color color2, boolean bl) {
         int intValue55 = 0;
         if (i == 0) {
            intValue55 = j % 360;
         } else {
            intValue55 = (int)((System.currentTimeMillis() / i + j) % 360L);
         }

         intValue55 = (intValue55 >= 180 ? 360 - intValue55 : intValue55) * 2;
         return bl ? resolve9(color, color2, intValue55 / 360.0F) : resolve10(color, color2, intValue55 / 360.0F);
      }

      public static Color resolve9(Color color, Color color2, float f) {
         f = Math.min(1.0F, Math.max(0.0F, f));
         float[] floatValues13 = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
         float[] floatValues14 = Color.RGBtoHSB(color2.getRed(), color2.getGreen(), color2.getBlue(), null);
         Color color3 = Color.getHSBColor(measure5(floatValues13[0], floatValues14[0], f), measure5(floatValues13[1], floatValues14[1], f), measure5(floatValues13[2], floatValues14[2], f));
         return new Color(color3.getRed(), color3.getGreen(), color3.getBlue(), (int)measure5((float)color.getAlpha(), (float)color2.getAlpha(), f));
      }

      public static Color resolve10(Color color, Color color2, float f) {
         return new Color(ColorUtils.compute14(color.getRGB(), color2.getRGB(), f), true);
      }

      private static float measure5(float f, float g, float h) {
         float floatValue84 = Math.max(0.0F, Math.min(1.0F, h));
         return f + (g - f) * floatValue84;
      }

      public static int compute9(int i, int j, int k, int l) {
         double doubleValue2 = (System.currentTimeMillis() / k + l) % 360L;
         double doubleValue3;
         float floatValue85 = (float)((doubleValue3 = doubleValue2 % 360.0) / 360.0);
         return ColorUtils.compute16(i, j, floatValue85);
      }

      public static int compute10(int i, float f) {
         int intValue56 = i >> 16 & 0xFF;
         int intValue57 = i >> 8 & 0xFF;
         int intValue58 = i & 0xFF;
         int intValue59 = i >> 24 & 0xFF;
         float[] floatValues15 = Color.RGBtoHSB(intValue56, intValue57, intValue58, null);
         float floatValue86 = Math.max(0.0F, Math.min(1.0F, floatValues15[2] * f));
         int intValue60 = Color.HSBtoRGB(floatValues15[0], floatValues15[1], floatValue86);
         return intValue60 & 16777215 | intValue59 << 24;
      }

      public static int compute11(int i, int j, double d) {
         return ColorUtils.compute13(i, j, d);
      }

      public static int[] resolve11(int i) {
         int[] intValues = new int[4];
         if (i == 0) {
            i = 1;
         }

         intValues[0] = compute12(i, 1, 1.0F, 1.0F, 1.0F);
         intValues[1] = compute12(i, 90, 1.0F, 1.0F, 1.0F);
         intValues[2] = compute12(i, 180, 1.0F, 1.0F, 1.0F);
         intValues[3] = compute12(i, 270, 1.0F, 1.0F, 1.0F);
         return intValues;
      }

      public static int compute12(int i, int j, float f, float g, float h) {
         int intValue61 = (int)((System.currentTimeMillis() / i + j) % 360L);
         float floatValue87 = intValue61 / 360.0F;
         int intValue62 = Color.HSBtoRGB(floatValue87, f, g);
         return compute32(compute26(intValue62), compute27(intValue62), compute28(intValue62), Math.max(0, Math.min(255, (int)(h * 255.0F))));
      }

      public static int compute13(int i, int j, int... is) {
         int intValue63 = (int)((System.currentTimeMillis() / i + j) % 360L);
         intValue63 = (intValue63 > 180 ? 360 - intValue63 : intValue63) + 180;
         int intValue64 = (int)(intValue63 / 360.0F * is.length);
         if (intValue64 == is.length) {
            intValue64--;
         }

         int intValue65 = is[intValue64];
         int intValue66 = is[intValue64 == is.length - 1 ? 0 : intValue64 + 1];
         return compute14(intValue65, intValue66, intValue63 / 360.0F * is.length - intValue64);
      }

      public static int compute14(int i, int j, double d) {
         return ColorUtils.compute13(i, j, d);
      }

      public static float[] resolve12(int i) {
         return new float[]{compute26(i) / 255.0F, compute27(i) / 255.0F, compute28(i) / 255.0F, compute29(i) / 255.0F};
      }

      public static int compute15(int i, int j) {
         double doubleValue4 = (int)((System.currentTimeMillis() / i + j) % 360L);
         double doubleValue5;
         return Color.getHSBColor((doubleValue5 = doubleValue4 % 360.0) / 360.0 < 0.5 ? -((float)(doubleValue5 / 360.0)) : (float)(doubleValue5 / 360.0), 0.5F, 1.0F).hashCode();
      }

      public static int[] resolve13(int i) {
         int[] intValues2 = new int[4];
         if (i == 0) {
            int intValue67 = 1;
         }

         intValues2[0] = compute15(25, 1);
         intValues2[1] = compute15(25, 90);
         intValues2[2] = compute15(25, 180);
         intValues2[3] = compute15(25, 270);
         return intValues2;
      }

      public static int compute16(int i, float f) {
         return compute17(compute18(i), compute19(i), compute20(i), (int)(compute21(i) * f / 255.0F));
      }

      public static int compute17(int i, int j, int k, int l) {
         return l << 24 | i << 16 | j << 8 | k;
      }

      public static int compute18(int i) {
         return i >> 16 & 0xFF;
      }

      public static int compute19(int i) {
         return i >> 8 & 0xFF;
      }

      public static int compute20(int i) {
         return i & 0xFF;
      }

      public static int compute21(int i) {
         return i >> 24 & 0xFF;
      }

      public static float[] resolve14(Color color) {
         return new float[]{color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F};
      }

      public static int compute22(int i, int j) {
         return compute9(
            compute11(
               LegacyClickGuiState.theme.getColor().getRGB(),
               LegacyClickGuiState.theme2.getColor().getRGB(),
               (double)(1.0F - LegacyClickGuiState.directionalAnimation4.measure3())
            ),
            compute11(
               LegacyClickGuiState.theme.getColor().getRGB(),
               LegacyClickGuiState.theme2.getColor().getRGB(),
               (double)(1.0F - LegacyClickGuiState.directionalAnimation4.measure3())
            ),
            i,
            j
         );
      }

      public static int compute23(int i, float f) {
         int intValue68 = i >> 16 & 0xFF;
         int intValue69 = i >> 8 & 0xFF;
         int intValue70 = i & 0xFF;
         return compute32(intValue68, intValue69, intValue70, (int)f);
      }

      public static Color resolve15(int i) {
         int intValue71 = i >> 16 & 0xFF;
         int intValue72 = i >> 8 & 0xFF;
         int intValue73 = i & 0xFF;
         int intValue74 = i >> 24 & 0xFF;
         return new Color(intValue71, intValue72, intValue73, intValue74);
      }

      public static int compute24(int i, int j) {
         return compute32(compute26(i), compute27(i), compute28(i), j);
      }

      public static int compute25(int i, float f) {
         return compute30(compute26(i) * f, compute27(i) * f, compute28(i) * f, (float)compute29(i));
      }

      public static int compute26(int i) {
         return i >> 16 & 0xFF;
      }

      public static int compute27(int i) {
         return i >> 8 & 0xFF;
      }

      public static int compute28(int i) {
         return i & 0xFF;
      }

      public static int compute29(int i) {
         return i >> 24 & 0xFF;
      }

      public static int compute30(float f, float g, float h, float i) {
         return compute32(
            Math.max(0, Math.min(255, Math.round(f))),
            Math.max(0, Math.min(255, Math.round(g))),
            Math.max(0, Math.min(255, Math.round(h))),
            Math.max(0, Math.min(255, Math.round(i)))
         );
      }

      public static int compute31(int i, int j, int k) {
         return compute32(i, j, k, 255);
      }

      public static int compute32(int i, int j, int k, int l) {
         int intValue75 = 0;
         intValue75 |= l << 24;
         intValue75 |= i << 16;
         intValue75 |= j << 8;
         return intValue75 | k;
      }

      public static int compute33(int i) {
         return i >> 16 & 0xFF;
      }

      public static int compute34(int i) {
         return i >> 8 & 0xFF;
      }

      public static int compute35(int i) {
         return i & 0xFF;
      }

      public static int compute36(int i) {
         return i >> 24 & 0xFF;
      }

      public static float[] resolve16(int i) {
         return new float[]{(i >> 16 & 0xFF) / 255.0F, (i >> 8 & 0xFF) / 255.0F, (i & 0xFF) / 255.0F, (i >> 24 & 0xFF) / 255.0F};
      }

      public static int compute37(int i, int j, int k, int l) {
         return l << 24 | i << 16 | j << 8 | k;
      }

      public static int compute38(Color color) {
         int intValue76 = color.getAlpha();
         int intValue77 = color.getRed();
         int intValue78 = color.getGreen();
         int intValue79 = color.getBlue();
         return intValue76 << 24 | intValue77 << 16 | intValue78 << 8 | intValue79;
      }

      public static float[] resolve17(int i) {
         return new float[]{(i >> 16 & 0xFF) / 255.0F, (i >> 8 & 0xFF) / 255.0F, (i & 0xFF) / 255.0F, (i >> 24 & 0xFF) / 255.0F};
      }
   }

   record RenderManagerData3(float[] rootTransform, float originX, float originY) {
   }

   public static final class RenderManagerState2 {
      final RenderEngine.RenderEngineBounds2 renderEngineBounds2;
      final int intValue;
      final int intValue2;
      final boolean flag;
      final float floatValue;
      final int intValue3;
      final int intValue4;
      final boolean flag2;
      final float floatValue2;
      final int intValue5;
      final int intValue6;
      final int intValue7;
      final int intValue8;
      final ArrayDeque<float[]> arrayDeque;
      final ArrayDeque<RenderManager.RenderManagerData2> arrayDeque2;
      final ArrayDeque<Float> arrayDeque3;
      final ArrayDeque<Boolean> arrayDeque4;

      RenderManagerState2(
         RenderEngine.RenderEngineBounds2 renderEngineBounds22,
         int i,
         int j,
         boolean bl,
         float f,
         int k,
         int l,
         boolean bl2,
         float g,
         int m,
         int n,
         int o,
         int p,
         ArrayDeque<float[]> arrayDeque,
         ArrayDeque<RenderManager.RenderManagerData2> arrayDeque2,
         ArrayDeque<Float> arrayDeque3,
         ArrayDeque<Boolean> arrayDeque4
      ) {
         this.renderEngineBounds2 = renderEngineBounds22;
         this.intValue = i;
         this.intValue2 = j;
         this.flag = bl;
         this.floatValue = f;
         this.intValue3 = k;
         this.intValue4 = l;
         this.flag2 = bl2;
         this.floatValue2 = g;
         this.intValue5 = m;
         this.intValue6 = n;
         this.intValue7 = o;
         this.intValue8 = p;
         this.arrayDeque = arrayDeque;
         this.arrayDeque2 = arrayDeque2;
         this.arrayDeque3 = arrayDeque3;
         this.arrayDeque4 = arrayDeque4;
      }
   }
}
