package ru.metaculture.protection;

import java.awt.Color;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.Generated;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public final class ColorUtils {
   private static final long TIMESTAMP = 60000L;
   private static final ConcurrentHashMap<ColorUtils.ColorUtilsState2, ColorUtils.ColorUtilsState> CONCURRENT_HASH_MAP = new ConcurrentHashMap<>();
   private static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor(runnable -> {
      Thread thread = new Thread(runnable, "ColorUtil-CacheCleaner");
      thread.setDaemon(true);
      return thread;
   });
   private static final DelayQueue<ColorUtils.ColorUtilsState> DELAY_QUEUE = new DelayQueue<>();
   private static final double[] DOUBLES = resolve2();
   private static final ThreadLocal<float[]> THREAD_LOCAL = ThreadLocal.withInitial(() -> new float[3]);
   public static final int INT_VALUE = compute44(255, 0, 0);
   public static final int INT_VALUE_2 = compute44(0, 255, 0);
   public static final int INT_VALUE_3 = compute44(0, 0, 255);
   public static final int INT_VALUE_4 = compute44(255, 255, 0);
   public static final int INT_VALUE_5 = compute28(255);
   public static final int INT_VALUE_6 = compute28(0);

   public static int compute(int i, int j, float f) {
      return compute14(i, j, f);
   }

   public static int compute2(int i, int j) {
      return MathHelper.clamp(j, 0, 255) << 24 | i & 16777215;
   }

   public static int compute3(int i, int j, double d) {
      return resolve((double)i, (double)j, (double)((float)d)).intValue();
   }

   public static Double resolve(double d, double e, double f) {
      return d + (e - d) * f;
   }

   public static int compute4(int i) {
      return i >>> 24;
   }

   public static int compute5(int i) {
      return i >> 16 & 0xFF;
   }

   public static int compute6(int i) {
      return i >> 8 & 0xFF;
   }

   public static int compute7(int i) {
      return i & 0xFF;
   }

   public static int compute8(int i, int j) {
      int intValue = compute5(i);
      int intValue2 = compute6(i);
      int intValue3 = compute7(i);
      intValue = Math.max(0, intValue - j);
      intValue2 = Math.max(0, intValue2 - j);
      intValue3 = Math.max(0, intValue3 - j);
      return 0xFF000000 | intValue << 16 | intValue2 << 8 | intValue3;
   }

   public static int compute9(int i) {
      float floatValue = (i >> 24 & 0xFF) / 255.0F;
      float floatValue2 = (i >> 16 & 0xFF) / 255.0F;
      float floatValue3 = (i >> 8 & 0xFF) / 255.0F;
      float floatValue4 = (i & 0xFF) / 255.0F;
      GL11.glColor4f(floatValue2, floatValue3, floatValue4, floatValue);
      return i;
   }

   public static int compute10(int i, int j) {
      double doubleValue = (int)((System.currentTimeMillis() / i + j) % 360L);
      double doubleValue2;
      return Color.getHSBColor((doubleValue2 = doubleValue % 360.0) / 360.0 < 0.5 ? -((float)(doubleValue2 / 360.0)) : (float)(doubleValue2 / 360.0), 0.5F, 1.0F).hashCode();
   }

   public static int compute11(float f, int i, int j, int k) {
      long longValue = System.currentTimeMillis() + i;
      double doubleValue3 = (Math.sin(longValue * 0.001 * f) + 1.0) / 2.0;
      return compute16(j, k, (float)doubleValue3);
   }

   public static int compute12(int i, int j, float f) {
      return compute14(i, j, f);
   }

   public static int compute13(int i, int j, double d) {
      return compute14(i, j, (float)d);
   }

   public static int compute14(int i, int j, float f) {
      float floatValue5 = measure(f);
      if (floatValue5 <= 0.0F) {
         return i;
      } else if (floatValue5 >= 1.0F) {
         return j;
      } else {
         int intValue4 = i >>> 24 & 0xFF;
         int intValue5 = j >>> 24 & 0xFF;
         int intValue6 = Math.round(intValue4 + (intValue5 - intValue4) * floatValue5);
         return compute17(i, j, floatValue5, intValue6);
      }
   }

   public static int compute15(int i, int j, double d) {
      return compute16(i, j, (float)d);
   }

   public static int compute16(int i, int j, float f) {
      float floatValue6 = measure(f);
      if (floatValue6 <= 0.0F) {
         return i & 16777215;
      } else {
         return floatValue6 >= 1.0F ? j & 16777215 : compute17(i, j, floatValue6, 0) & 16777215;
      }
   }

   private static int compute17(int i, int j, float f, int k) {
      double doubleValue4 = DOUBLES[i >>> 16 & 0xFF];
      double doubleValue5 = DOUBLES[i >>> 8 & 0xFF];
      double doubleValue6 = DOUBLES[i & 0xFF];
      double doubleValue7 = DOUBLES[j >>> 16 & 0xFF];
      double doubleValue8 = DOUBLES[j >>> 8 & 0xFF];
      double doubleValue9 = DOUBLES[j & 0xFF];
      double doubleValue10 = 0.4122214708 * doubleValue4 + 0.5363325363 * doubleValue5 + 0.0514459929 * doubleValue6;
      double doubleValue11 = 0.2119034982 * doubleValue4 + 0.6806995451 * doubleValue5 + 0.1073969566 * doubleValue6;
      double doubleValue12 = 0.0883024619 * doubleValue4 + 0.2817188376 * doubleValue5 + 0.6299787005 * doubleValue6;
      double doubleValue13 = Math.cbrt(doubleValue10);
      double doubleValue14 = Math.cbrt(doubleValue11);
      double doubleValue15 = Math.cbrt(doubleValue12);
      double doubleValue16 = 0.2104542553 * doubleValue13 + 0.793617785 * doubleValue14 - 0.0040720468 * doubleValue15;
      double doubleValue17 = 1.9779984951 * doubleValue13 - 2.428592205 * doubleValue14 + 0.4505937099 * doubleValue15;
      double doubleValue18 = 0.0259040371 * doubleValue13 + 0.7827717662 * doubleValue14 - 0.808675766 * doubleValue15;
      double doubleValue19 = 0.4122214708 * doubleValue7 + 0.5363325363 * doubleValue8 + 0.0514459929 * doubleValue9;
      double doubleValue20 = 0.2119034982 * doubleValue7 + 0.6806995451 * doubleValue8 + 0.1073969566 * doubleValue9;
      double doubleValue21 = 0.0883024619 * doubleValue7 + 0.2817188376 * doubleValue8 + 0.6299787005 * doubleValue9;
      double doubleValue22 = Math.cbrt(doubleValue19);
      double doubleValue23 = Math.cbrt(doubleValue20);
      double doubleValue24 = Math.cbrt(doubleValue21);
      double doubleValue25 = 0.2104542553 * doubleValue22 + 0.793617785 * doubleValue23 - 0.0040720468 * doubleValue24;
      double doubleValue26 = 1.9779984951 * doubleValue22 - 2.428592205 * doubleValue23 + 0.4505937099 * doubleValue24;
      double doubleValue27 = 0.0259040371 * doubleValue22 + 0.7827717662 * doubleValue23 - 0.808675766 * doubleValue24;
      double doubleValue28 = doubleValue16 + (doubleValue25 - doubleValue16) * f;
      double doubleValue29 = doubleValue17 + (doubleValue26 - doubleValue17) * f;
      double doubleValue30 = doubleValue18 + (doubleValue27 - doubleValue18) * f;
      double doubleValue31 = doubleValue28 + 0.3963377774 * doubleValue29 + 0.2158037573 * doubleValue30;
      double doubleValue32 = doubleValue28 - 0.1055613458 * doubleValue29 - 0.0638541728 * doubleValue30;
      double doubleValue33 = doubleValue28 - 0.0894841775 * doubleValue29 - 1.291485548 * doubleValue30;
      double doubleValue34 = doubleValue31 * doubleValue31 * doubleValue31;
      double doubleValue35 = doubleValue32 * doubleValue32 * doubleValue32;
      double doubleValue36 = doubleValue33 * doubleValue33 * doubleValue33;
      int intValue7 = compute18(4.0767416621 * doubleValue34 - 3.3077115913 * doubleValue35 + 0.2309699292 * doubleValue36);
      int intValue8 = compute18(-1.2684380046 * doubleValue34 + 2.6097574011 * doubleValue35 - 0.3413193965 * doubleValue36);
      int intValue9 = compute18(-0.0041960863 * doubleValue34 - 0.7034186147 * doubleValue35 + 1.707614701 * doubleValue36);
      return (k & 0xFF) << 24 | intValue7 << 16 | intValue8 << 8 | intValue9;
   }

   private static double[] resolve2() {
      double[] doubleValues = new double[256];

      for (int intValue10 = 0; intValue10 < doubleValues.length; intValue10++) {
         double doubleValue37 = intValue10 / 255.0;
         doubleValues[intValue10] = doubleValue37 <= 0.04045 ? doubleValue37 / 12.92 : Math.pow((doubleValue37 + 0.055) / 1.055, 2.4);
      }

      return doubleValues;
   }

   private static int compute18(double d) {
      double doubleValue38 = d <= 0.0 ? 0.0 : Math.min(1.0, d);
      double doubleValue39 = doubleValue38 <= 0.0031308 ? doubleValue38 * 12.92 : 1.055 * Math.pow(doubleValue38, 0.4166666666666667) - 0.055;
      int intValue11 = (int)Math.round(doubleValue39 * 255.0);
      if (intValue11 < 0) {
         return 0;
      } else {
         return intValue11 > 255 ? 255 : intValue11;
      }
   }

   private static float measure(float f) {
      if (f < 0.0F) {
         return 0.0F;
      } else {
         return f > 1.0F ? 1.0F : f;
      }
   }

   public static float[] resolve3(int i) {
      return new float[]{(i >> 16 & 0xFF) / 255.0F, (i >> 8 & 0xFF) / 255.0F, (i & 0xFF) / 255.0F, (i >> 24 & 0xFF) / 255.0F};
   }

   public static int[] resolve4(int i) {
      return new int[]{(int)((i >> 16 & 0xFF) / 255.0F), (int)((i >> 8 & 0xFF) / 255.0F), (int)((i & 0xFF) / 255.0F), (int)((i >> 24 & 0xFF) / 255.0F)};
   }

   public static int compute19(int i) {
      return i >> 16 & 0xFF;
   }

   public static int compute20(int i) {
      return i >> 8 & 0xFF;
   }

   public static int compute21(int i) {
      return i & 0xFF;
   }

   public static int compute22(int i) {
      return i >> 24 & 0xFF;
   }

   public static float measure2(int i) {
      return compute19(i) / 255.0F;
   }

   public static float measure3(int i) {
      return compute20(i) / 255.0F;
   }

   public static float measure4(int i) {
      return compute21(i) / 255.0F;
   }

   public static float measure5(int i) {
      return compute22(i) / 255.0F;
   }

   public static int[] resolve5(int i) {
      return new int[]{compute19(i), compute20(i), compute21(i), compute22(i)};
   }

   public static int[] resolve6(int i) {
      return new int[]{compute19(i), compute20(i), compute21(i)};
   }

   public static float[] resolve7(int i) {
      return new float[]{measure2(i), measure3(i), measure4(i), measure5(i)};
   }

   public static float[] resolve8(int i) {
      return new float[]{measure2(i), measure3(i), measure4(i)};
   }

   public static int compute23(float f, float g, float h, float i) {
      return compute43(Math.round(f * 255.0F), Math.round(g * 255.0F), Math.round(h * 255.0F), Math.round(i * 255.0F));
   }

   public static int compute24(int i, int j, int k, float f) {
      return compute43(i, j, k, Math.round(f * 255.0F));
   }

   public static int compute25(float f, float g, float h) {
      return compute23(f, g, h, 1.0F);
   }

   public static int compute26(int i, int j) {
      return compute43(i, i, i, j);
   }

   public static int compute27(int i, float f) {
      return compute26(i, Math.round(f * 255.0F));
   }

   public static int compute28(int i) {
      return compute44(i, i, i);
   }

   public static int compute29(int i, int j) {
      return compute43(compute19(i), compute20(i), compute21(i), j);
   }

   public static int compute30(int i, float f) {
      return compute24(compute19(i), compute20(i), compute21(i), f);
   }

   public static int compute31(int i, float f) {
      return compute43(compute19(i), compute20(i), compute21(i), Math.round(compute22(i) * f));
   }

   public static int compute32(int i, float f) {
      int intValue12 = compute22(i);
      int intValue13 = compute14(i, intValue12 << 24 | 8421504, f);
      int intValue14 = compute19(intValue13);
      int intValue15 = compute20(intValue13);
      int intValue16 = compute21(intValue13);
      float floatValue7 = f / 2.0F;
      intValue14 = Math.round(intValue14 * floatValue7);
      intValue15 = Math.round(intValue15 * floatValue7);
      intValue16 = Math.round(intValue16 * floatValue7);
      return compute43(intValue14, intValue15, intValue16, intValue12);
   }

   public static int compute33(int i, float f) {
      return compute43(Math.round(compute19(i) * f), Math.round(compute20(i) * f), Math.round(compute21(i) * f), compute22(i));
   }

   public static int compute34(int i, float f) {
      return compute43(
         Math.min(255, Math.round(compute19(i) / f)),
         Math.min(255, Math.round(compute20(i) / f)),
         Math.min(255, Math.round(compute21(i) / f)),
         compute22(i)
      );
   }

   public static int compute35(int i, int j, float f) {
      return compute14(i, j, f);
   }

   public static int compute36(int i, int j) {
      return compute35(i, j, 0.5F);
   }

   public static int[] resolve9(int i, int j, int k) {
      int[] intValues = new int[k];

      for (int intValue17 = 0; intValue17 < k; intValue17++) {
         float floatValue8 = (float)intValue17 / (k - 1);
         intValues[intValue17] = compute35(i, j, floatValue8);
      }

      return intValues;
   }

   public static int compute37(int i, int j, double d) {
      return compute13(i, j, d);
   }

   public static int compute38(int i, int j, float f, float g, float h) {
      int intValue18 = (int)((System.currentTimeMillis() / i + j) % 360L);
      float floatValue9 = intValue18 / 360.0F;
      int intValue19 = Color.HSBtoRGB(floatValue9, f, g);
      return compute43(compute19(intValue19), compute20(intValue19), compute21(intValue19), Math.round(h * 255.0F));
   }

   public static int compute39(int i, int j, int k, int l) {
      int intValue20 = (int)((System.currentTimeMillis() / i + j) % 360L);
      intValue20 = intValue20 >= 180 ? 360 - intValue20 : intValue20;
      return compute35(k, l, intValue20 / 180.0F);
   }

   public static int compute40(int i) {
      return compute39(10, i, compute41(), compute33(compute41(), 0.5F));
   }

   public static int compute41() {
      return RenderManager.RenderManagerState.compute2();
   }

   public static int compute42(int i, int j, int k, int l) {
      int intValue21 = (int)((System.currentTimeMillis() / l + k) % 360L);
      intValue21 = (intValue21 > 180 ? 360 - intValue21 : intValue21) + 180;
      int intValue22 = compute37(i, j, (double)MathHelper.clamp(intValue21 / 180.0F - 1.0F, 0.0F, 1.0F));
      float[] floatValues = Color.RGBtoHSB(compute19(intValue22), compute20(intValue22), compute21(intValue22), THREAD_LOCAL.get());
      floatValues[1] *= 1.5F;
      floatValues[1] = Math.min(floatValues[1], 1.0F);
      return Color.HSBtoRGB(floatValues[0], floatValues[1], floatValues[2]);
   }

   public static int compute43(int i, int j, int k, int l) {
      ColorUtils.ColorUtilsState2 colorUtilsState2 = new ColorUtils.ColorUtilsState2(i, j, k, l);
      ColorUtils.ColorUtilsState colorUtilsState = CONCURRENT_HASH_MAP.computeIfAbsent(colorUtilsState2, colorUtilsState22 -> {
         ColorUtils.ColorUtilsState var5x = new ColorUtils.ColorUtilsState(colorUtilsState22, compute45(i, j, k, l), 60000L);
         DELAY_QUEUE.offer(var5x);
         return var5x;
      });
      return colorUtilsState.getIntValue();
   }

   public static int compute44(int i, int j, int k) {
      return compute43(i, j, k, 255);
   }

   private static int compute45(int i, int j, int k, int l) {
      return MathHelper.clamp(l, 0, 255) << 24 | MathHelper.clamp(i, 0, 255) << 16 | MathHelper.clamp(j, 0, 255) << 8 | MathHelper.clamp(k, 0, 255);
   }

   private static String resolve10(int i, int j, int k, int l) {
      return i + "," + j + "," + k + "," + l;
   }

   public static void invoke() {
      SCHEDULED_EXECUTOR_SERVICE.shutdown();
   }

   @Generated
   private ColorUtils() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }

   static {
      SCHEDULED_EXECUTOR_SERVICE.scheduleWithFixedDelay(() -> {
         for (ColorUtils.ColorUtilsState colorUtilsState3 = DELAY_QUEUE.poll(); colorUtilsState3 != null; colorUtilsState3 = DELAY_QUEUE.poll()) {
            if (colorUtilsState3.check()) {
               CONCURRENT_HASH_MAP.remove(colorUtilsState3.getColorUtilsState2());
            }
         }
      }, 0L, 1L, TimeUnit.SECONDS);
   }

   static class ColorUtilsState implements Delayed {
      private final ColorUtils.ColorUtilsState2 colorUtilsState2;
      private final int intValue;
      private final long timestamp;

      ColorUtilsState(ColorUtils.ColorUtilsState2 colorUtilsState23, int i, long l) {
         this.colorUtilsState2 = colorUtilsState23;
         this.intValue = i;
         this.timestamp = System.currentTimeMillis() + l;
      }

      @Override
      public long getDelay(TimeUnit timeUnit) {
         long longValue2 = this.timestamp - System.currentTimeMillis();
         return timeUnit.convert(longValue2, TimeUnit.MILLISECONDS);
      }

      @Override
      public int compareTo(Delayed delayed) {
         return delayed instanceof ColorUtils.ColorUtilsState ? Long.compare(this.timestamp, ((ColorUtils.ColorUtilsState)delayed).timestamp) : 0;
      }

      public boolean check() {
         return System.currentTimeMillis() > this.timestamp;
      }

      @Generated
      public ColorUtils.ColorUtilsState2 getColorUtilsState2() {
         return this.colorUtilsState2;
      }

      @Generated
      public int getIntValue() {
         return this.intValue;
      }

      @Generated
      public long getTimestamp() {
         return this.timestamp;
      }
   }

   static class ColorUtilsState2 {
      final int intValue;
      final int intValue2;
      final int intValue3;
      final int intValue4;

      @Generated
      public int getIntValue() {
         return this.intValue;
      }

      @Generated
      public int getIntValue2() {
         return this.intValue2;
      }

      @Generated
      public int getIntValue3() {
         return this.intValue3;
      }

      @Generated
      public int getIntValue4() {
         return this.intValue4;
      }

      @Generated
      public ColorUtilsState2(int i, int j, int k, int l) {
         this.intValue = i;
         this.intValue2 = j;
         this.intValue3 = k;
         this.intValue4 = l;
      }

      @Generated
      @Override
      public boolean equals(Object object) {
         if (object == this) {
            return true;
         } else if (!(object instanceof ColorUtils.ColorUtilsState2 colorUtilsState24)) {
            return false;
         } else if (!colorUtilsState24.check(this)) {
            return false;
         } else if (this.getIntValue() != colorUtilsState24.getIntValue()) {
            return false;
         } else if (this.getIntValue2() != colorUtilsState24.getIntValue2()) {
            return false;
         } else {
            return this.getIntValue3() != colorUtilsState24.getIntValue3() ? false : this.getIntValue4() == colorUtilsState24.getIntValue4();
         }
      }

      @Generated
      protected boolean check(Object object) {
         return object instanceof ColorUtils.ColorUtilsState2;
      }

      @Generated
      @Override
      public int hashCode() {
         byte byteValue = 59;
         int intValue23 = 1;
         intValue23 = intValue23 * 59 + this.getIntValue();
         intValue23 = intValue23 * 59 + this.getIntValue2();
         intValue23 = intValue23 * 59 + this.getIntValue3();
         return intValue23 * 59 + this.getIntValue4();
      }
   }
}
