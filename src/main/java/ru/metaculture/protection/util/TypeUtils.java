package ru.metaculture.protection;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import org.lwjgl.opengl.GL11;

public class TypeUtils {
   private static final float FLOAT_VALUE = 0.35F;
   public static MinecraftClient client = MinecraftClient.getInstance();
   private static ProjectionUtils projectionUtils;
   private static Window window;
   private float floatValue;
   private float floatValue2;
   private float floatValue3;
   private float floatValue4 = 8.0F;
   private boolean flag;
   float floatValue5;

   public static ProjectionUtils resolve() {
      if (projectionUtils == null) {
         MinecraftClient client = MinecraftClient.getInstance();
         if (client != null && client.getWindow() != null) {
            projectionUtils = new ProjectionUtils(client);
         }
      }

      return projectionUtils;
   }

   public static Window resolve2() {
      if (window == null) {
         MinecraftClient client2 = MinecraftClient.getInstance();
         if (client2 != null) {
            window = client2.getWindow();
         }
      }

      return window;
   }

   public TypeUtils() {
      this.setFlag(true);
   }

   public void invoke() {
      this.floatValue2 = this.resolve3(this.floatValue2, this.floatValue, RenderMath.measure56((double)(this.floatValue4 / 100.0F)));
      if (Math.abs(this.floatValue - this.floatValue2) <= 0.35F) {
         this.floatValue2 = this.floatValue;
      }
   }

   public void invoke2(double d) {
      if (this.flag) {
         float floatValue = (float)d * (this.floatValue4 * 10.0F);
         float floatValue2 = 0.0F;
         this.floatValue = Math.min(Math.max(this.floatValue + floatValue / 2.0F, this.floatValue3 - floatValue2), floatValue2);
      }
   }

   public <T extends Number> T resolve3(T number, T number2, double d) {
      double doubleValue = number.doubleValue();
      double doubleValue2 = number2.doubleValue();
      double doubleValue3 = doubleValue + d * (doubleValue2 - doubleValue);
      if (number instanceof Integer) {
         return (T)(Object)(int)Math.round(doubleValue3);
      } else if (number instanceof Double) {
         return (T)(Object)doubleValue3;
      } else if (number instanceof Float) {
         return (T)(Object)(float)doubleValue3;
      } else if (number instanceof Long) {
         return (T)(Object)Math.round(doubleValue3);
      } else if (number instanceof Short) {
         return (T)(Object)(short)Math.round(doubleValue3);
      } else if (number instanceof Byte) {
         return (T)(Object)(byte)Math.round(doubleValue3);
      } else {
         throw new IllegalArgumentException("Unsupported type: " + number.getClass().getSimpleName());
      }
   }

   public static void invoke3() {
      GL11.glEnable(3089);
   }

   public static void invoke4() {
      GL11.glDisable(3089);
   }

   public static void invoke5(Window window, double d, double e, double f, double g) {
      if (d + f != d && e + g != e && !(d < 0.0) && !(e + g < 0.0)) {
         double doubleValue4 = window.getScaleFactor();
         GL11.glScissor(
            (int)Math.round(d * doubleValue4), (int)Math.round((window.getScaledHeight() - (e + g)) * doubleValue4), (int)Math.round(f * doubleValue4), (int)Math.round(g * doubleValue4)
         );
      }
   }

   public void invoke6() {
      this.floatValue2 = 0.0F;
      this.floatValue = 0.0F;
   }

   public void invoke7(float f, float g) {
      this.floatValue3 = -f + g;
   }

   public void invoke8(RenderManager renderManager, float f, float g, float h, float i, float j) {
      if (!(this.getFloatValue3() >= 0.0F)) {
         float floatValue3 = this.getFloatValue3() != 0.0F ? this.measure() / this.getFloatValue3() : 0.0F;
         float floatValue4 = i - this.getFloatValue3() / (this.getFloatValue3() - i) * i;
         this.floatValue5 = RenderMath.measure3(floatValue4, this.floatValue5, RenderMath.measure25(0.9F));
         boolean flag = this.floatValue5 < i && this.floatValue5 > 0.0F;
         if (flag) {
            float floatValue5 = g + i * floatValue3 - this.floatValue5 * floatValue3;
            int intValue = RenderManager.RenderManagerState.compute24(RenderManager.RenderManagerState.compute6(1, 1), (int)RenderMath.measure49(255.0F * j, 0.0F, 255.0F));
            int intValue2 = RenderManager.RenderManagerState.compute24(RenderManager.RenderManagerState.compute6(1, 1), (int)RenderMath.measure49(20.0F * j, 0.0F, 20.0F));
            renderManager.invoke4(f, g, h, i, intValue2);
            renderManager.invoke5(f, floatValue5, h, this.floatValue5, 1.0F, intValue);
         }
      }
   }

   public float getFloatValue() {
      return this.floatValue;
   }

   public void setFloatValue(float f) {
      this.floatValue = f;
   }

   public float measure() {
      return Math.abs(this.floatValue - this.floatValue2) <= 0.35F ? Math.round(this.floatValue2) : this.floatValue2;
   }

   public void setFloatValue2(float f) {
      this.floatValue2 = f;
   }

   public float getFloatValue3() {
      return this.floatValue3;
   }

   public void setFloatValue3(float f) {
      this.floatValue3 = f;
   }

   public float getFloatValue4() {
      return this.floatValue4;
   }

   public void setFloatValue4(float f) {
      this.floatValue4 = f;
   }

   public boolean isFlag() {
      return this.flag;
   }

   public void setFlag(boolean bl) {
      this.flag = bl;
   }
}
