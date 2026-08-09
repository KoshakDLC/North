package ru.metaculture.protection;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.Random;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import net.minecraft.entity.Entity;
import org.joml.Vector3d;

public class RenderMath {
   public static MinecraftClient client = MinecraftClient.getInstance();
   private static final Random RANDOM = new Random();
   public static int intValue = 2;
   private static final double DOUBLE_VALUE = Double.longBitsToDouble(4805340802404319232L);
   private static final double[] DOUBLES = new double[257];
   private static final double[] DOUBLES_2 = new double[257];

   public static double measure(double d, double e, double f) {
      return e + d * (f - e);
   }

   public static double measure2(double d, int i) {
      return new BigDecimal(d).setScale(i, RoundingMode.HALF_EVEN).doubleValue();
   }

   public static float measure3(float f, float g, float h) {
      return (f - g) / (h - g);
   }

   public static double measure4(double d, double e) {
      return Math.random() * (e - d) + d;
   }

   public static float measure5(float f, float g) {
      return (float)(Math.random() * (g - f) + f);
   }

   public static boolean check(float f, float g, float h, float i, float j, float k) {
      return f >= h && g >= i && f < h + j && g < i + k;
   }

   public static double measure6(double d) {
      return new BigDecimal(d).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
   }

   public static double measure7(double d, double e, double f) {
      return d + (e - d) * f;
   }

   public static float measure8(float f) {
      if ((f = f % 360.0F) >= 180.0F) {
         f -= 360.0F;
      }

      if (f < -180.0F) {
         f += 360.0F;
      }

      return f;
   }

   public static float measure9(float f, float g) {
      return (float)(f + Math.random() * (g - f));
   }

   public static double measure10(double d) {
      return d * d;
   }

   public static double measure11(double d, double e) {
      return Math.sqrt(measure10(d) - measure10(e));
   }

   public static float measure12(float f) {
      return f * 9.0F / 16.0F;
   }

   public static float measure13(float f) {
      return f * 16.0F / 9.0F;
   }

   public static int compute(int i) {
      Window window = MinecraftClient.getInstance().getWindow();
      return (int)((double)i * window.getScaleFactor() / intValue);
   }

   public static double measure14(double d, double e) {
      double doubleValue = e * e + d * d;
      if (Double.isNaN(doubleValue)) {
         return Double.NaN;
      } else {
         boolean flag = d < 0.0;
         if (flag) {
            d = -d;
         }

         boolean flag2 = e < 0.0;
         if (flag2) {
            e = -e;
         }

         boolean flag3 = d > e;
         if (flag3) {
            double doubleValue2 = e;
            e = d;
            d = doubleValue2;
         }

         double doubleValue3 = measure15(doubleValue);
         e *= doubleValue3;
         d *= doubleValue3;
         double doubleValue4 = DOUBLE_VALUE + d;
         int intValue = (int)Double.doubleToRawLongBits(doubleValue4);
         double doubleValue5 = DOUBLES[intValue];
         double doubleValue6 = DOUBLES_2[intValue];
         double doubleValue7 = doubleValue4 - DOUBLE_VALUE;
         double doubleValue8 = d * doubleValue6 - e * doubleValue7;
         double doubleValue9 = (6.0 + doubleValue8 * doubleValue8) * doubleValue8 * 0.16666666666666666;
         double doubleValue10 = doubleValue5 + doubleValue9;
         if (flag3) {
            doubleValue10 = (Math.PI / 2) - doubleValue10;
         }

         if (flag2) {
            doubleValue10 = Math.PI - doubleValue10;
         }

         if (flag) {
            doubleValue10 = -doubleValue10;
         }

         return doubleValue10;
      }
   }

   public static double measure15(double d) {
      double doubleValue11 = 0.5 * d;
      long longValue = Double.doubleToRawLongBits(d);
      longValue = 6910469410427058090L - (longValue >> 1);
      d = Double.longBitsToDouble(longValue);
      return d * (1.5 - doubleValue11 * d * d);
   }

   public static double measure16(double d, double e) {
      return Math.abs(e - d) > Math.abs(d - e) ? Math.abs(d - e) : Math.abs(e - d);
   }

   public static double measure17(double d, int i) {
      return d < 0.5 ? 2.0 * d * d : 1.0 - Math.pow(-2.0 * d + 2.0, i) / 2.0;
   }

   public static float measure18(float f, int i) {
      if (i < 0) {
         throw new IllegalArgumentException("Decimal places must be non-negative");
      } else {
         double doubleValue12 = Math.pow(10.0, i);
         return (float)(Math.round(f * doubleValue12) / doubleValue12);
      }
   }

   public static double measure19(Vector3d vector3d, Vector3d vector3d2) {
      double doubleValue13 = vector3d2.x - vector3d.x;
      double doubleValue14 = vector3d2.z - vector3d.z;
      return Math.sqrt(doubleValue13 * doubleValue13 + doubleValue14 * doubleValue14);
   }

   public float measure20(float f) {
      Window window2 = MinecraftClient.getInstance().getWindow();
      return f * (int)((double)f * window2.getScaleFactor() / intValue);
   }

   public static int compute2(double d) {
      int intValue2 = (int)d;
      return d > intValue2 ? intValue2 + 1 : intValue2;
   }

   public double measure21(double d) {
      Window window3 = MinecraftClient.getInstance().getWindow();
      return d * (int)(d * window3.getScaleFactor() / intValue);
   }

   public static float measure22(float f) {
      return measure25(f) * measure23();
   }

   public static float measure23() {
      return (float)(measure24() * 0.15);
   }

   public static float measure24() {
      float floatValue;
      return (floatValue = (float)((Double)client.options.getMouseSensitivity().getValue() * 0.6 + 0.2)) * floatValue * floatValue * 8.0F;
   }

   public static float measure25(float f) {
      return Math.round(f / measure23());
   }

   public static double measure26(Entity entity) {
      double doubleValue15 = entity.getZ() - entity.lastZ;
      double doubleValue16 = entity.getX() - entity.lastX;
      double doubleValue17 = entity.getY() - entity.lastY;
      double doubleValue18 = Math.sqrt(doubleValue16 * doubleValue16 + doubleValue15 * doubleValue15 + doubleValue17 * doubleValue17);
      return doubleValue18 * 15.3571428571;
   }

   public static float measure27(float f) {
      if ((f = (float)(f % 360.0)) >= 180.0F) {
         f -= 360.0F;
      }

      if (f < -180.0F) {
         f += 360.0F;
      }

      return f;
   }

   public static float measure28(float f, float g, float h, float i, float j) {
      if (h - g == 0.0F) {
         throw new IllegalArgumentException("Диапазон входных значений не может быть равен нулю.");
      } else {
         float floatValue2 = (h - f) / (h - g) * (j - i) + i;
         return Math.max(i, Math.min(j, floatValue2));
      }
   }

   public static float measure29(float f, float g, float h, float i, float j) {
      if (h - g == 0.0F) {
         throw new IllegalArgumentException("Диапазон входных значений не может быть равен нулю.");
      } else {
         float floatValue3 = (f - g) / (h - g) * (j - i) + i;
         return Math.max(i, Math.min(j, floatValue3));
      }
   }

   public static float measure30(float f, float g, float h) {
      if (!(f < g) && !(f > h)) {
         float floatValue4 = h - g;
         return (f - g) / floatValue4 * 100.0F;
      } else {
         return 0.0F;
      }
   }

   public static float measure31(float f, float g, float h) {
      if (!(f < g) && !(f > h)) {
         float floatValue5 = h - g;
         return (f - g) / floatValue5 * 101.0F;
      } else {
         return 0.0F;
      }
   }

   public static float measure32(float f, float g, float h) {
      if (!(f < g) && !(f > h)) {
         float floatValue6 = h - g;
         return (f - g) / floatValue6 * 191.0F;
      } else {
         return 0.0F;
      }
   }

   public static float measure33(float f, float g, float h) {
      if (!(f < 0.0F) && !(f > 100.0F)) {
         float floatValue7 = h - g;
         return f / 100.0F * floatValue7 + g;
      } else {
         return 0.0F;
      }
   }

   public static double measure34(double d, double e) {
      return e + (d - e) * RANDOM.nextDouble();
   }

   public static BigDecimal resolve(float f, int i) {
      BigDecimal bigDecimal = new BigDecimal(Float.toString(f));
      return bigDecimal.setScale(i, 4);
   }

   public static int compute3(int i, int j) {
      return (int)(j + (i - j) * RANDOM.nextDouble());
   }

   public static boolean check2(int i) {
      return i % 2 == 0;
   }

   public static double measure35(double d, int i) {
      if (i < 0) {
         throw new IllegalArgumentException();
      } else {
         BigDecimal bigDecimal2 = new BigDecimal(d);
         bigDecimal2 = bigDecimal2.setScale(i, RoundingMode.HALF_UP);
         return bigDecimal2.doubleValue();
      }
   }

   public static double measure36(double d, double e) {
      double doubleValue19 = Math.pow(10.0, e);
      return Math.round(d * doubleValue19) / doubleValue19;
   }

   public static double measure37(double d, double e) {
      return Math.random() * (d - e) + e;
   }

   public static int compute4(int i, int j) {
      return -j + (int)(Math.random() * (i - -j + 1));
   }

   public static float measure38(float f, float g) {
      return f != g && !(g - f <= 0.0F) ? (float)(f + (g - f) * Math.random()) : f;
   }

   public static int compute5(int i, int j) {
      return RANDOM.nextInt(j - i) + i;
   }

   public static double measure39(double d, double e) {
      double doubleValue20 = 1.0 / e;
      return Math.round(d * doubleValue20) / doubleValue20;
   }

   public static boolean check3(Double double_) {
      return double_ == Math.floor(double_) && !Double.isInfinite(double_);
   }

   public static float[] resolve2(float[] fs) {
      fs[0] %= 360.0F;
      fs[1] %= 360.0F;

      while (fs[0] <= -180.0F) {
         fs[0] += 360.0F;
      }

      while (fs[1] <= -180.0F) {
         fs[1] += 360.0F;
      }

      while (fs[0] > 180.0F) {
         fs[0] -= 360.0F;
      }

      while (fs[1] > 180.0F) {
         fs[1] -= 360.0F;
      }

      return fs;
   }

   public static double measure40(double d, double e) {
      Random random = new Random();
      double doubleValue21 = e - d;
      double doubleValue22 = random.nextDouble() * doubleValue21;
      if (doubleValue22 > e) {
         doubleValue22 = e;
      }

      double doubleValue23;
      if ((doubleValue23 = doubleValue22 + d) > e) {
         doubleValue23 = e;
      }

      return doubleValue23;
   }

   public static float measure41(float f, float g, float h, float i) {
      float floatValue8 = measure33(i, 0.0F, h);
      return measure43(f, g, floatValue8);
   }

   public static float measure42(float f, float g, float h) {
      float floatValue9 = f + h / 2.0F;
      if (floatValue9 > g) {
         floatValue9 = g;
      }

      return floatValue9;
   }

   public static float measure43(float f, float g, float h) {
      return f + h * (g - f);
   }

   public static float measure44(float f, float g, float h, float i) {
      float floatValue10 = (g - f) * (i / 2.0F) > 0.0F
         ? Math.max(i, Math.min(g - f, (g - f) * (i / 2.0F)))
         : Math.max(g - f, Math.min(-(i / 2.0F), (g - f) * (i / 2.0F)));
      return h + floatValue10;
   }

   public static float measure45(float f, float g, float h) {
      float floatValue11 = (g - f) * (h / 2.0F) > 0.0F
         ? Math.max(h, Math.min(g - f, (g - f) * (h / 2.0F)))
         : Math.max(g - f, Math.min(-(h / 2.0F), (g - f) * (h / 2.0F)));
      return f + floatValue11;
   }

   public static double measure46(double d, double e) {
      double doubleValue24 = e / 2.0;
      double doubleValue25 = Math.floor(d / e) * e;
      return d >= doubleValue25 + doubleValue24
         ? new BigDecimal(Math.ceil(d / e) * e, MathContext.DECIMAL64).stripTrailingZeros().doubleValue()
         : new BigDecimal(doubleValue25, MathContext.DECIMAL64).stripTrailingZeros().doubleValue();
   }

   public static float measure47(float f, float g, float h, float i) {
      Random random2 = new Random();
      float floatValue12 = random2.nextFloat() * i;
      return f + h * floatValue12 * (g - f);
   }

   public static int compute6(int i, int j, int k) {
      if (i <= j) {
         i = j;
      }

      if (i >= k) {
         i = k;
      }

      return i;
   }

   public static float measure48(float f, float g, float h) {
      if (f <= g) {
         f = g;
      }

      if (f >= h) {
         f = h;
      }

      return f;
   }

   public static String resolve3(long l) {
      long longValue2 = l / 3600000L;
      long longValue3 = l % 3600000L / 60000L;
      long longValue4 = l % 360000L % 60000L / 1000L;
      return String.format("%02d:%02d:%02d", longValue2, longValue3, longValue4);
   }

   public static float measure49(float f, float g, float h) {
      if (f < g) {
         return g;
      } else {
         return f > h ? h : f;
      }
   }

   public static double measure50(double d, double e, double f) {
      return e + (d - e) * f;
   }

   public static float measure51(float f, float g, double d) {
      return (float)measure50((double)f, (double)g, d);
   }

   public static int compute7(int i, int j, double d) {
      return (int)measure50((double)i, (double)j, d);
   }

   public static Vector3d resolve4(Vector3d vector3d, Vector3d vector3d2, float f) {
      return new Vector3d(
         measure50(vector3d.x, vector3d2.x, (double)f), measure50(vector3d.y, vector3d2.y, (double)f), measure50(vector3d.z, vector3d2.z, (double)f)
      );
   }

   public static double measure52(double d, double e) {
      double doubleValue26 = Math.round(d / e) * e;
      BigDecimal bigDecimal3 = new BigDecimal(doubleValue26);
      bigDecimal3 = bigDecimal3.setScale(2, RoundingMode.HALF_UP);
      return bigDecimal3.doubleValue();
   }

   public static int compute8(int i, int j) {
      return (int)(Math.random() * (j - i + 1) + i);
   }

   public static double measure53(double d, double e) {
      return Math.random() * (e - d) + d;
   }

   public static Vector3d resolve5(Vector3d vector3d, Vector3d vector3d2, float f) {
      return new Vector3d(
         measure54((float)vector3d.x, (float)vector3d2.x, f),
         measure54((float)vector3d.y, (float)vector3d2.y, f),
         measure54((float)vector3d.z, (float)vector3d2.z, f)
      );
   }

   public static float measure54(float f, float g, float h) {
      return (1.0F - measure49((float)(measure55() * h), 0.0F, 1.0F)) * f + measure49((float)(measure55() * h), 0.0F, 1.0F) * g;
   }

   public static double measure55() {
      return AnimationSystem.getINSTANCE().getFloatValue();
   }

   public static double measure56(double d) {
      double doubleValue27 = Math.max(0.0, Math.min(1.0, d));
      double doubleValue28 = Math.max(0.0, Math.min(60.0, measure55() * 240.0));
      return 1.0 - Math.pow(1.0 - doubleValue27, doubleValue28);
   }

   public static double measure57(double d) {
      double doubleValue29 = Math.max(0.0, Math.min(1.0, d));
      double doubleValue30 = Math.max(0.0, Math.min(60.0, measure55() * 240.0));
      return Math.pow(doubleValue29, doubleValue30);
   }

   public static int compute9(double d) {
      int intValue3 = (int)d;
      return d > intValue3 ? intValue3 + 1 : intValue3;
   }

   public static double measure58(double d, double e, double f) {
      return Math.max(e, Math.min(f, d));
   }

   public static float measure59(float f, float g, float h, float i, float j) {
      return i + (j - i) * (f - g) / (h - g);
   }

   public static float measure60(float f, float g) {
      return (float)(Math.random() * (f - g) + g);
   }

   public static float measure61(float f, float g, float h) {
      return f + (g - f) * measure49(h, 0.0F, 1.0F);
   }

   public static double measure62(double d, double e, double f) {
      return d + (e - d) * measure49((float)f, 0.0F, 1.0F);
   }

   public static double measure63(double d, int i) {
      if (i < 0) {
         throw new IllegalArgumentException();
      } else {
         BigDecimal bigDecimal4 = new BigDecimal(d);
         bigDecimal4 = bigDecimal4.setScale(i, RoundingMode.HALF_UP);
         return bigDecimal4.doubleValue();
      }
   }

   public static int compute10(int i, int j) {
      return i / 2 - j / 2;
   }

   public static float measure64(float f, float g) {
      SecureRandom secureRandom = new SecureRandom();
      return secureRandom.nextFloat() * (g - f) + f;
   }

   public static float measure65(float f, float g, double d) {
      return measure61(f, g, (float)d);
   }

   public static float measure66(float f, float g) {
      double doubleValue31 = 3.141592653;
      double doubleValue32 = 1.0 / Math.sqrt(2.0 * doubleValue31 * (g * g));
      return (float)(doubleValue32 * Math.exp(-(f * f) / (2.0 * (g * g))));
   }

   public static double measure67(double d) {
      return Math.round(d * 2.0) / 2.0;
   }

   public static float measure68(float f, float g) {
      SecureRandom secureRandom2 = new SecureRandom();
      return secureRandom2.nextFloat() * (f - g) + g;
   }

   public static int compute11(int i, int j) {
      return (i + j) / 2;
   }

   public static int compute12(int i, int j) {
      return (int)(Math.random() * (i - j)) + j;
   }

   public static float measure69(float f, float g, float h) {
      float floatValue13 = measure27(f - g);
      if (floatValue13 < -h) {
         floatValue13 = -h;
      }

      if (floatValue13 >= h) {
         floatValue13 = h;
      }

      return f - floatValue13;
   }

   public static float measure70(float f) {
      return (float)measure71(0.0, 1.0, (double)f);
   }

   public static double measure71(double d, double e, double f) {
      return Math.max(d, Math.min(e, f));
   }
}
