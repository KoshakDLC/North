package ru.metaculture.protection;

import lombok.Generated;
import net.minecraft.client.MinecraftClient;

public final class Metrics {
   private final float floatValue;
   private final float floatValue2;
   private final float floatValue3;
   private final float floatValue4;
   private final float floatValue5;
   private final float floatValue6;
   private final float floatValue7;
   private final float floatValue8;
   private final float floatValue9;
   private final float floatValue10;
   private final float floatValue11;
   private final float floatValue12;
   private final float floatValue13;
   private final float floatValue14;
   private final float floatValue15;
   private final float floatValue16;
   private final float floatValue17;
   private final float floatValue18;
   private final float floatValue19;

   public static Metrics resolve(MinecraftClient minecraftClient, LayoutSpec layoutSpec) {
      return minecraftClient != null
            && minecraftClient.getWindow() != null
            && minecraftClient.getWindow().getFramebufferWidth() > 0
            && minecraftClient.getWindow().getFramebufferHeight() > 0
         ? resolve3(
            minecraftClient.getWindow().getFramebufferWidth(), minecraftClient.getWindow().getFramebufferHeight(), measure3(minecraftClient), layoutSpec
         )
         : resolve4(layoutSpec.getFloatValue19(), layoutSpec);
   }

   public static Metrics resolve2(float f, float g, LayoutSpec layoutSpec2) {
      return resolve3(f, g, 1.0F, layoutSpec2);
   }

   public static Metrics resolve3(float f, float g, float h, LayoutSpec layoutSpec3) {
      if (!(f <= 0.0F) && !(g <= 0.0F)) {
         float floatValue = 16.0F;
         float floatValue2 = (f - floatValue * 2.0F) / layoutSpec3.getFloatValue();
         float floatValue3 = (g - floatValue * 2.0F) / layoutSpec3.getFloatValue2();
         float floatValue4 = Math.min(floatValue2, floatValue3);
         float floatValue5 = Math.max(1.0F, h);
         float floatValue6 = 0.68F + Math.min(floatValue5, 2.0F) * 0.28F;
         float floatValue7 = Math.max(layoutSpec3.getFloatValue18(), Math.min(layoutSpec3.getFloatValue19(), floatValue6 * measure4()));
         float floatValue8 = Math.min(floatValue7, floatValue4);
         floatValue8 = Math.max(layoutSpec3.getFloatValue18(), Math.min(layoutSpec3.getFloatValue19(), floatValue8));
         float floatValue9 = Math.max(layoutSpec3.getFloatValue18(), Math.min(layoutSpec3.getFloatValue19(), floatValue6 * measure5()));
         float floatValue10 = Math.max(layoutSpec3.getFloatValue18(), Math.min(layoutSpec3.getFloatValue19(), floatValue9));
         return resolve5(floatValue8, floatValue10, layoutSpec3);
      } else {
         return resolve4(layoutSpec3.getFloatValue19(), layoutSpec3);
      }
   }

   public static Metrics resolve4(float f, LayoutSpec layoutSpec4) {
      return resolve5(f, f, layoutSpec4);
   }

   public static Metrics resolve5(float f, float g, LayoutSpec layoutSpec5) {
      return resolve7()
         .setFloatValue(f)
         .setFloatValue2(g)
         .setFloatValue3(layoutSpec5.getFloatValue() * f)
         .setFloatValue4(layoutSpec5.getFloatValue2() * f)
         .setFloatValue5(layoutSpec5.getFloatValue3() * f)
         .setFloatValue6(layoutSpec5.getFloatValue4() * f)
         .setFloatValue7(layoutSpec5.getFloatValue5() * f)
         .setFloatValue8(layoutSpec5.getFloatValue6() * f)
         .setFloatValue9(layoutSpec5.getFloatValue7() * f)
         .setFloatValue10(layoutSpec5.getFloatValue8() * f)
         .setFloatValue11(layoutSpec5.getFloatValue9() * f)
         .setFloatValue12(layoutSpec5.getFloatValue10() * f)
         .setFloatValue13(layoutSpec5.getFloatValue11() * f)
         .setFloatValue14(layoutSpec5.getFloatValue12() * f)
         .setFloatValue15(layoutSpec5.getFloatValue13() * f)
         .setFloatValue16(layoutSpec5.getFloatValue14() * f)
         .setFloatValue17(layoutSpec5.getFloatValue15() * f)
         .setFloatValue18(layoutSpec5.getFloatValue16() * g)
         .setFloatValue19(layoutSpec5.getFloatValue17() * g)
         .resolve();
   }

   public float measure(float f) {
      return f * this.floatValue;
   }

   public float measure2(float f) {
      return f * this.floatValue2;
   }

   public Metrics resolve6(float f) {
      return resolve7()
         .setFloatValue(f)
         .setFloatValue2(this.floatValue2)
         .setFloatValue3(this.floatValue3)
         .setFloatValue4(this.floatValue4)
         .setFloatValue5(this.floatValue5)
         .setFloatValue6(this.floatValue6)
         .setFloatValue7(this.floatValue7)
         .setFloatValue8(this.floatValue8)
         .setFloatValue9(this.floatValue9)
         .setFloatValue10(this.floatValue10)
         .setFloatValue11(this.floatValue11)
         .setFloatValue12(this.floatValue12)
         .setFloatValue13(this.floatValue13)
         .setFloatValue14(this.floatValue14)
         .setFloatValue15(this.floatValue15)
         .setFloatValue16(this.floatValue16)
         .setFloatValue17(this.floatValue17)
         .setFloatValue18(this.floatValue18)
         .setFloatValue19(this.floatValue19)
         .resolve();
   }

   private static float measure3(MinecraftClient minecraftClient) {
      if (minecraftClient != null && minecraftClient.getWindow() != null) {
         try {
            return Math.max(1.0F, (float)minecraftClient.getWindow().getScaleFactor());
         } catch (Exception exception) {
            int intValue = Math.max(1, minecraftClient.getWindow().getScaledWidth());
            return Math.max(1.0F, (float)minecraftClient.getWindow().getFramebufferWidth() / intValue);
         }
      } else {
         return 1.0F;
      }
   }

   private static float measure4() {
      try {
         return MenuModule.MASSHTAB_GUI == null ? 0.86F : Math.max(0.72F, Math.min(1.7F, MenuModule.MASSHTAB_GUI.getValue()));
      } catch (Throwable exception2) {
         return 0.86F;
      }
   }

   private static float measure5() {
      try {
         return MenuModule.MASSHTAB_PANELI_TEMY == null ? 0.86F : Math.max(0.72F, Math.min(1.7F, MenuModule.MASSHTAB_PANELI_TEMY.getValue()));
      } catch (Throwable exception3) {
         return 0.86F;
      }
   }

   @Generated
   Metrics(
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
      float s,
      float t,
      float u,
      float v,
      float w,
      float x
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
      this.floatValue15 = t;
      this.floatValue16 = u;
      this.floatValue17 = v;
      this.floatValue18 = w;
      this.floatValue19 = x;
   }

   @Generated
   public static Metrics.MetricsBuilder resolve7() {
      return new Metrics.MetricsBuilder();
   }

   @Generated
   public float getFloatValue() {
      return this.floatValue;
   }

   @Generated
   public float getFloatValue2() {
      return this.floatValue2;
   }

   @Generated
   public float getFloatValue3() {
      return this.floatValue3;
   }

   @Generated
   public float getFloatValue4() {
      return this.floatValue4;
   }

   @Generated
   public float getFloatValue5() {
      return this.floatValue5;
   }

   @Generated
   public float getFloatValue6() {
      return this.floatValue6;
   }

   @Generated
   public float getFloatValue7() {
      return this.floatValue7;
   }

   @Generated
   public float getFloatValue8() {
      return this.floatValue8;
   }

   @Generated
   public float getFloatValue9() {
      return this.floatValue9;
   }

   @Generated
   public float getFloatValue10() {
      return this.floatValue10;
   }

   @Generated
   public float getFloatValue11() {
      return this.floatValue11;
   }

   @Generated
   public float getFloatValue12() {
      return this.floatValue12;
   }

   @Generated
   public float getFloatValue13() {
      return this.floatValue13;
   }

   @Generated
   public float getFloatValue14() {
      return this.floatValue14;
   }

   @Generated
   public float getFloatValue15() {
      return this.floatValue15;
   }

   @Generated
   public float getFloatValue16() {
      return this.floatValue16;
   }

   @Generated
   public float getFloatValue17() {
      return this.floatValue17;
   }

   @Generated
   public float getFloatValue18() {
      return this.floatValue18;
   }

   @Generated
   public float getFloatValue19() {
      return this.floatValue19;
   }

   @Generated
   @Override
   public boolean equals(Object object) {
      if (object == this) {
         return true;
      } else if (!(object instanceof Metrics metrics)) {
         return false;
      } else if (Float.compare(this.getFloatValue(), metrics.getFloatValue()) != 0) {
         return false;
      } else if (Float.compare(this.getFloatValue2(), metrics.getFloatValue2()) != 0) {
         return false;
      } else if (Float.compare(this.getFloatValue3(), metrics.getFloatValue3()) != 0) {
         return false;
      } else if (Float.compare(this.getFloatValue4(), metrics.getFloatValue4()) != 0) {
         return false;
      } else if (Float.compare(this.getFloatValue5(), metrics.getFloatValue5()) != 0) {
         return false;
      } else if (Float.compare(this.getFloatValue6(), metrics.getFloatValue6()) != 0) {
         return false;
      } else if (Float.compare(this.getFloatValue7(), metrics.getFloatValue7()) != 0) {
         return false;
      } else if (Float.compare(this.getFloatValue8(), metrics.getFloatValue8()) != 0) {
         return false;
      } else if (Float.compare(this.getFloatValue9(), metrics.getFloatValue9()) != 0) {
         return false;
      } else if (Float.compare(this.getFloatValue10(), metrics.getFloatValue10()) != 0) {
         return false;
      } else if (Float.compare(this.getFloatValue11(), metrics.getFloatValue11()) != 0) {
         return false;
      } else if (Float.compare(this.getFloatValue12(), metrics.getFloatValue12()) != 0) {
         return false;
      } else if (Float.compare(this.getFloatValue13(), metrics.getFloatValue13()) != 0) {
         return false;
      } else if (Float.compare(this.getFloatValue14(), metrics.getFloatValue14()) != 0) {
         return false;
      } else if (Float.compare(this.getFloatValue15(), metrics.getFloatValue15()) != 0) {
         return false;
      } else if (Float.compare(this.getFloatValue16(), metrics.getFloatValue16()) != 0) {
         return false;
      } else if (Float.compare(this.getFloatValue17(), metrics.getFloatValue17()) != 0) {
         return false;
      } else {
         return Float.compare(this.getFloatValue18(), metrics.getFloatValue18()) != 0 ? false : Float.compare(this.getFloatValue19(), metrics.getFloatValue19()) == 0;
      }
   }

   @Generated
   @Override
   public int hashCode() {
      byte byteValue = 59;
      int intValue2 = 1;
      intValue2 = intValue2 * 59 + Float.floatToIntBits(this.getFloatValue());
      intValue2 = intValue2 * 59 + Float.floatToIntBits(this.getFloatValue2());
      intValue2 = intValue2 * 59 + Float.floatToIntBits(this.getFloatValue3());
      intValue2 = intValue2 * 59 + Float.floatToIntBits(this.getFloatValue4());
      intValue2 = intValue2 * 59 + Float.floatToIntBits(this.getFloatValue5());
      intValue2 = intValue2 * 59 + Float.floatToIntBits(this.getFloatValue6());
      intValue2 = intValue2 * 59 + Float.floatToIntBits(this.getFloatValue7());
      intValue2 = intValue2 * 59 + Float.floatToIntBits(this.getFloatValue8());
      intValue2 = intValue2 * 59 + Float.floatToIntBits(this.getFloatValue9());
      intValue2 = intValue2 * 59 + Float.floatToIntBits(this.getFloatValue10());
      intValue2 = intValue2 * 59 + Float.floatToIntBits(this.getFloatValue11());
      intValue2 = intValue2 * 59 + Float.floatToIntBits(this.getFloatValue12());
      intValue2 = intValue2 * 59 + Float.floatToIntBits(this.getFloatValue13());
      intValue2 = intValue2 * 59 + Float.floatToIntBits(this.getFloatValue14());
      intValue2 = intValue2 * 59 + Float.floatToIntBits(this.getFloatValue15());
      intValue2 = intValue2 * 59 + Float.floatToIntBits(this.getFloatValue16());
      intValue2 = intValue2 * 59 + Float.floatToIntBits(this.getFloatValue17());
      intValue2 = intValue2 * 59 + Float.floatToIntBits(this.getFloatValue18());
      return intValue2 * 59 + Float.floatToIntBits(this.getFloatValue19());
   }

   @Generated
   @Override
   public String toString() {
      return "Metrics(scale="
         + this.getFloatValue()
         + ", themeScale="
         + this.getFloatValue2()
         + ", guiW="
         + this.getFloatValue3()
         + ", guiH="
         + this.getFloatValue4()
         + ", padding="
         + this.getFloatValue5()
         + ", gap="
         + this.getFloatValue6()
         + ", sidebarW="
         + this.getFloatValue7()
         + ", bodyW="
         + this.getFloatValue8()
         + ", bodyH="
         + this.getFloatValue9()
         + ", headerH="
         + this.getFloatValue10()
         + ", searchW="
         + this.getFloatValue11()
         + ", contentH="
         + this.getFloatValue12()
         + ", contentPadding="
         + this.getFloatValue13()
         + ", columnW="
         + this.getFloatValue14()
         + ", moduleHeaderH="
         + this.getFloatValue15()
         + ", moduleGap="
         + this.getFloatValue16()
         + ", scrollbarW="
         + this.getFloatValue17()
         + ", themeW="
         + this.getFloatValue18()
         + ", themeH="
         + this.getFloatValue19()
         + ")";
   }

   @Generated
   public static class MetricsBuilder {
      @Generated
      private float floatValue;
      @Generated
      private float floatValue2;
      @Generated
      private float floatValue3;
      @Generated
      private float floatValue4;
      @Generated
      private float floatValue5;
      @Generated
      private float floatValue6;
      @Generated
      private float floatValue7;
      @Generated
      private float floatValue8;
      @Generated
      private float floatValue9;
      @Generated
      private float floatValue10;
      @Generated
      private float floatValue11;
      @Generated
      private float floatValue12;
      @Generated
      private float floatValue13;
      @Generated
      private float floatValue14;
      @Generated
      private float floatValue15;
      @Generated
      private float floatValue16;
      @Generated
      private float floatValue17;
      @Generated
      private float floatValue18;
      @Generated
      private float floatValue19;

      @Generated
      MetricsBuilder() {
      }

      @Generated
      public Metrics.MetricsBuilder setFloatValue(float f) {
         this.floatValue = f;
         return this;
      }

      @Generated
      public Metrics.MetricsBuilder setFloatValue2(float f) {
         this.floatValue2 = f;
         return this;
      }

      @Generated
      public Metrics.MetricsBuilder setFloatValue3(float f) {
         this.floatValue3 = f;
         return this;
      }

      @Generated
      public Metrics.MetricsBuilder setFloatValue4(float f) {
         this.floatValue4 = f;
         return this;
      }

      @Generated
      public Metrics.MetricsBuilder setFloatValue5(float f) {
         this.floatValue5 = f;
         return this;
      }

      @Generated
      public Metrics.MetricsBuilder setFloatValue6(float f) {
         this.floatValue6 = f;
         return this;
      }

      @Generated
      public Metrics.MetricsBuilder setFloatValue7(float f) {
         this.floatValue7 = f;
         return this;
      }

      @Generated
      public Metrics.MetricsBuilder setFloatValue8(float f) {
         this.floatValue8 = f;
         return this;
      }

      @Generated
      public Metrics.MetricsBuilder setFloatValue9(float f) {
         this.floatValue9 = f;
         return this;
      }

      @Generated
      public Metrics.MetricsBuilder setFloatValue10(float f) {
         this.floatValue10 = f;
         return this;
      }

      @Generated
      public Metrics.MetricsBuilder setFloatValue11(float f) {
         this.floatValue11 = f;
         return this;
      }

      @Generated
      public Metrics.MetricsBuilder setFloatValue12(float f) {
         this.floatValue12 = f;
         return this;
      }

      @Generated
      public Metrics.MetricsBuilder setFloatValue13(float f) {
         this.floatValue13 = f;
         return this;
      }

      @Generated
      public Metrics.MetricsBuilder setFloatValue14(float f) {
         this.floatValue14 = f;
         return this;
      }

      @Generated
      public Metrics.MetricsBuilder setFloatValue15(float f) {
         this.floatValue15 = f;
         return this;
      }

      @Generated
      public Metrics.MetricsBuilder setFloatValue16(float f) {
         this.floatValue16 = f;
         return this;
      }

      @Generated
      public Metrics.MetricsBuilder setFloatValue17(float f) {
         this.floatValue17 = f;
         return this;
      }

      @Generated
      public Metrics.MetricsBuilder setFloatValue18(float f) {
         this.floatValue18 = f;
         return this;
      }

      @Generated
      public Metrics.MetricsBuilder setFloatValue19(float f) {
         this.floatValue19 = f;
         return this;
      }

      @Generated
      public Metrics resolve() {
         return new Metrics(
            this.floatValue,
            this.floatValue2,
            this.floatValue3,
            this.floatValue4,
            this.floatValue5,
            this.floatValue6,
            this.floatValue7,
            this.floatValue8,
            this.floatValue9,
            this.floatValue10,
            this.floatValue11,
            this.floatValue12,
            this.floatValue13,
            this.floatValue14,
            this.floatValue15,
            this.floatValue16,
            this.floatValue17,
            this.floatValue18,
            this.floatValue19
         );
      }

      @Generated
      @Override
      public String toString() {
         return "Metrics.MetricsBuilder(scale="
            + this.floatValue
            + ", themeScale="
            + this.floatValue2
            + ", guiW="
            + this.floatValue3
            + ", guiH="
            + this.floatValue4
            + ", padding="
            + this.floatValue5
            + ", gap="
            + this.floatValue6
            + ", sidebarW="
            + this.floatValue7
            + ", bodyW="
            + this.floatValue8
            + ", bodyH="
            + this.floatValue9
            + ", headerH="
            + this.floatValue10
            + ", searchW="
            + this.floatValue11
            + ", contentH="
            + this.floatValue12
            + ", contentPadding="
            + this.floatValue13
            + ", columnW="
            + this.floatValue14
            + ", moduleHeaderH="
            + this.floatValue15
            + ", moduleGap="
            + this.floatValue16
            + ", scrollbarW="
            + this.floatValue17
            + ", themeW="
            + this.floatValue18
            + ", themeH="
            + this.floatValue19
            + ")";
      }
   }
}
