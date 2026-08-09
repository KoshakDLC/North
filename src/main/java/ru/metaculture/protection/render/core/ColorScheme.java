package ru.metaculture.protection;

import lombok.Generated;

public final class ColorScheme {
   private final int intValue;
   private final int intValue2;
   private final int intValue3;
   private final int intValue4;
   private final int intValue5;
   private final int intValue6;
   private final int intValue7;
   private final int intValue8;
   private final int intValue9;
   private final int intValue10;
   private final int intValue11;
   private final int intValue12;
   private final int intValue13;
   private final int intValue14;
   private final int intValue15;
   private final boolean flag;

   public int compute() {
      return this.flag ? -14705331 : -12452048;
   }

   public int compute2() {
      return this.flag ? -4181953 : -43920;
   }

   public int compute3() {
      return this.flag ? -6200825 : -17847;
   }

   public int compute4() {
      return this.flag ? compute7(this.intValue14, this.intValue13, 0.55F) : this.intValue14;
   }

   public static ColorScheme resolve(Theme theme) {
      return resolve2(theme, false);
   }

   public static ColorScheme resolve2(Theme theme2, boolean bl) {
      if (theme2 == Theme.CUSTOM) {
         MenuModule.invoke4();
      }

      int intValue = compute9(theme2);
      int intValue2 = compute10(theme2);
      int intValue3 = bl ? theme2.getColor5().getRGB() : theme2.getColor4().getRGB();
      int intValue4 = theme2.getColor5().getRGB();
      int intValue5 = theme2.getColor6().getRGB();
      if (theme2 == Theme.MIDNIGHT_AZURE) {
         return resolve5()
            .setIntValue(compute5(5, 10, 22, 238))
            .setIntValue2(compute5(8, 19, 34, 242))
            .setIntValue3(compute5(189, 234, 255, 8))
            .setIntValue4(compute5(189, 234, 255, 13))
            .setIntValue5(compute5(189, 234, 255, 20))
            .setIntValue6(compute5(189, 234, 255, 28))
            .setIntValue7(compute5(189, 234, 255, 38))
            .setIntValue8(compute5(189, 234, 255, 50))
            .setIntValue9(compute5(0, 240, 255, 72))
            .setIntValue10(compute5(0, 240, 255, 90))
            .setIntValue11(compute5(189, 234, 255, 140))
            .setIntValue12(compute5(232, 251, 255, 214))
            .setIntValue13(compute5(255, 255, 255, 255))
            .setIntValue14(-16715521)
            .setIntValue15(-16759553)
            .setFlag(false)
            .resolve();
      } else {
         ColorScheme.ColorSchemeState colorSchemeState = resolve5()
            .setIntValue(compute6(compute7(theme2.getColor2().getRGB(), intValue2, bl ? 0.04F : 0.05F), bl ? 226 : 232))
            .setIntValue2(compute6(compute7(theme2.getColor3().getRGB(), intValue2, bl ? 0.025F : 0.035F), bl ? 240 : 238))
            .setIntValue13(compute6(intValue4, 255))
            .setIntValue14(intValue)
            .setIntValue15(intValue2)
            .setFlag(bl);
         return bl
            ? colorSchemeState.setIntValue3(compute6(intValue3, 4))
               .setIntValue4(compute6(intValue3, 8))
               .setIntValue5(compute6(intValue3, 12))
               .setIntValue6(compute6(intValue3, 16))
               .setIntValue7(compute6(intValue3, 24))
               .setIntValue8(compute6(intValue3, 31))
               .setIntValue9(compute6(intValue3, 42))
               .setIntValue10(compute6(intValue3, 54))
               .setIntValue11(compute6(intValue5, 190))
               .setIntValue12(compute6(intValue5, 255))
               .resolve()
            : colorSchemeState.setIntValue3(compute6(intValue3, 3))
               .setIntValue4(compute6(intValue3, 5))
               .setIntValue5(compute6(intValue3, 8))
               .setIntValue6(compute6(intValue3, 10))
               .setIntValue7(compute6(intValue3, 15))
               .setIntValue8(compute6(intValue3, 20))
               .setIntValue9(compute6(intValue3, 31))
               .setIntValue10(compute6(intValue3, 41))
               .setIntValue11(compute6(intValue5, 61))
               .setIntValue12(compute6(intValue5, 122))
               .resolve();
      }
   }

   public static ColorScheme resolve3(Theme theme3, ColorScheme colorScheme, long l) {
      if (colorScheme == null) {
         colorScheme = resolve(theme3);
      }

      return ThemeAnimationMode.resolve3(theme3, colorScheme, l);
   }

   public static int compute5(int i, int j, int k, int l) {
      return (l & 0xFF) << 24 | (i & 0xFF) << 16 | (j & 0xFF) << 8 | k & 0xFF;
   }

   public static int compute6(int i, int j) {
      return compute5(i >> 16 & 0xFF, i >> 8 & 0xFF, i & 0xFF, Math.max(0, Math.min(255, j)));
   }

   public static int compute7(int i, int j, float f) {
      return ColorUtils.compute14(i, j, f);
   }

   public static int compute8(int i, int j, float f) {
      float floatValue = Math.max(0.0F, Math.min(1.0F, f));
      int intValue6 = i >>> 16 & 0xFF;
      int intValue7 = i >>> 8 & 0xFF;
      int intValue8 = i & 0xFF;
      int intValue9 = i >>> 24 & 0xFF;
      int intValue10 = j >>> 16 & 0xFF;
      int intValue11 = j >>> 8 & 0xFF;
      int intValue12 = j & 0xFF;
      int intValue13 = 255 - (255 - intValue6) * (255 - intValue10) / 255;
      int intValue14 = 255 - (255 - intValue7) * (255 - intValue11) / 255;
      int intValue15 = 255 - (255 - intValue8) * (255 - intValue12) / 255;
      return ColorUtils.compute14(i, compute5(intValue13, intValue14, intValue15, intValue9), floatValue);
   }

   private static int compute9(Theme theme4) {
      if (theme4 == Theme.MIDNIGHT_AZURE) {
         return -16715521;
      } else if (theme4 == Theme.VERNAL_SOLSTICE) {
         return -13447886;
      } else if (theme4 == Theme.SAKURA_BREEZE) {
         return -18491;
      } else if (theme4 == Theme.OBSIDIAN_EMBER) {
         return -20119;
      } else if (theme4 == Theme.GLACIER_VEIL) {
         return -5706497;
      } else if (theme4 == Theme.VELVET_DUSK) {
         return -3563265;
      } else if (theme4 == Theme.PORCELAIN_DAWN) {
         return -20342;
      } else if (theme4 == Theme.FRUTIGER_AERO) {
         return -8657678;
      } else {
         return theme4 == Theme.WILD ? -6375937 : compute12(theme4.getColor().getRGB(), 0.22F);
      }
   }

   private static int compute10(Theme theme5) {
      if (theme5 == Theme.MIDNIGHT_AZURE) {
         return -16759553;
      } else if (theme5 == Theme.VERNAL_SOLSTICE) {
         return -10496;
      } else if (theme5 == Theme.SAKURA_BREEZE) {
         return -16181;
      } else if (theme5 == Theme.OBSIDIAN_EMBER) {
         return -42198;
      } else if (theme5 == Theme.GLACIER_VEIL) {
         return -12681729;
      } else if (theme5 == Theme.VELVET_DUSK) {
         return -8635667;
      } else if (theme5 == Theme.PORCELAIN_DAWN) {
         return -30116;
      } else if (theme5 == Theme.FRUTIGER_AERO) {
         return -13722666;
      } else {
         return theme5 == Theme.WILD ? -8216577 : compute6(theme5.getColor().getRGB(), 255);
      }
   }

   static int compute11(int[] is, float f) {
      if (is != null && is.length != 0) {
         if (is.length == 1) {
            return is[0];
         } else {
            float floatValue2 = f - (float)Math.floor(f);
            float floatValue3 = floatValue2 * (is.length - 1);
            int intValue16 = Math.min(is.length - 2, Math.max(0, (int)Math.floor(floatValue3)));
            return compute7(is[intValue16], is[intValue16 + 1], floatValue3 - intValue16);
         }
      } else {
         return -1;
      }
   }

   public static ColorScheme resolve4(ColorScheme colorScheme2, ColorScheme colorScheme3, float f) {
      if (f <= 0.0F) {
         return colorScheme2;
      } else {
         return f >= 1.0F
            ? colorScheme3
            : resolve5()
               .setIntValue(compute7(colorScheme2.intValue, colorScheme3.intValue, f))
               .setIntValue2(compute7(colorScheme2.intValue2, colorScheme3.intValue2, f))
               .setIntValue3(compute7(colorScheme2.intValue3, colorScheme3.intValue3, f))
               .setIntValue4(compute7(colorScheme2.intValue4, colorScheme3.intValue4, f))
               .setIntValue5(compute7(colorScheme2.intValue5, colorScheme3.intValue5, f))
               .setIntValue6(compute7(colorScheme2.intValue6, colorScheme3.intValue6, f))
               .setIntValue7(compute7(colorScheme2.intValue7, colorScheme3.intValue7, f))
               .setIntValue8(compute7(colorScheme2.intValue8, colorScheme3.intValue8, f))
               .setIntValue9(compute7(colorScheme2.intValue9, colorScheme3.intValue9, f))
               .setIntValue10(compute7(colorScheme2.intValue10, colorScheme3.intValue10, f))
               .setIntValue11(compute7(colorScheme2.intValue11, colorScheme3.intValue11, f))
               .setIntValue12(compute7(colorScheme2.intValue12, colorScheme3.intValue12, f))
               .setIntValue13(compute7(colorScheme2.intValue13, colorScheme3.intValue13, f))
               .setIntValue14(compute7(colorScheme2.intValue14, colorScheme3.intValue14, f))
               .setIntValue15(compute7(colorScheme2.intValue15, colorScheme3.intValue15, f))
               .setFlag(f >= 0.5F ? colorScheme3.flag : colorScheme2.flag)
               .resolve();
      }
   }

   static int compute12(int i, float f) {
      return ColorUtils.compute14(compute6(i, 255), -1, f);
   }

   @Generated
   ColorScheme(int i, int j, int k, int l, int m, int n, int o, int p, int q, int r, int s, int t, int u, int v, int w, boolean bl) {
      this.intValue = i;
      this.intValue2 = j;
      this.intValue3 = k;
      this.intValue4 = l;
      this.intValue5 = m;
      this.intValue6 = n;
      this.intValue7 = o;
      this.intValue8 = p;
      this.intValue9 = q;
      this.intValue10 = r;
      this.intValue11 = s;
      this.intValue12 = t;
      this.intValue13 = u;
      this.intValue14 = v;
      this.intValue15 = w;
      this.flag = bl;
   }

   @Generated
   public static ColorScheme.ColorSchemeState resolve5() {
      return new ColorScheme.ColorSchemeState();
   }

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
   public int getIntValue5() {
      return this.intValue5;
   }

   @Generated
   public int getIntValue6() {
      return this.intValue6;
   }

   @Generated
   public int getIntValue7() {
      return this.intValue7;
   }

   @Generated
   public int getIntValue8() {
      return this.intValue8;
   }

   @Generated
   public int getIntValue9() {
      return this.intValue9;
   }

   @Generated
   public int getIntValue10() {
      return this.intValue10;
   }

   @Generated
   public int getIntValue11() {
      return this.intValue11;
   }

   @Generated
   public int getIntValue12() {
      return this.intValue12;
   }

   @Generated
   public int getIntValue13() {
      return this.intValue13;
   }

   @Generated
   public int getIntValue14() {
      return this.intValue14;
   }

   @Generated
   public int getIntValue15() {
      return this.intValue15;
   }

   @Generated
   public boolean isFlag() {
      return this.flag;
   }

   @Generated
   @Override
   public boolean equals(Object object) {
      if (object == this) {
         return true;
      } else if (!(object instanceof ColorScheme colorScheme4)) {
         return false;
      } else if (this.getIntValue() != colorScheme4.getIntValue()) {
         return false;
      } else if (this.getIntValue2() != colorScheme4.getIntValue2()) {
         return false;
      } else if (this.getIntValue3() != colorScheme4.getIntValue3()) {
         return false;
      } else if (this.getIntValue4() != colorScheme4.getIntValue4()) {
         return false;
      } else if (this.getIntValue5() != colorScheme4.getIntValue5()) {
         return false;
      } else if (this.getIntValue6() != colorScheme4.getIntValue6()) {
         return false;
      } else if (this.getIntValue7() != colorScheme4.getIntValue7()) {
         return false;
      } else if (this.getIntValue8() != colorScheme4.getIntValue8()) {
         return false;
      } else if (this.getIntValue9() != colorScheme4.getIntValue9()) {
         return false;
      } else if (this.getIntValue10() != colorScheme4.getIntValue10()) {
         return false;
      } else if (this.getIntValue11() != colorScheme4.getIntValue11()) {
         return false;
      } else if (this.getIntValue12() != colorScheme4.getIntValue12()) {
         return false;
      } else if (this.getIntValue13() != colorScheme4.getIntValue13()) {
         return false;
      } else if (this.getIntValue14() != colorScheme4.getIntValue14()) {
         return false;
      } else {
         return this.getIntValue15() != colorScheme4.getIntValue15() ? false : this.isFlag() == colorScheme4.isFlag();
      }
   }

   @Generated
   @Override
   public int hashCode() {
      byte byteValue = 59;
      int intValue17 = 1;
      intValue17 = intValue17 * 59 + this.getIntValue();
      intValue17 = intValue17 * 59 + this.getIntValue2();
      intValue17 = intValue17 * 59 + this.getIntValue3();
      intValue17 = intValue17 * 59 + this.getIntValue4();
      intValue17 = intValue17 * 59 + this.getIntValue5();
      intValue17 = intValue17 * 59 + this.getIntValue6();
      intValue17 = intValue17 * 59 + this.getIntValue7();
      intValue17 = intValue17 * 59 + this.getIntValue8();
      intValue17 = intValue17 * 59 + this.getIntValue9();
      intValue17 = intValue17 * 59 + this.getIntValue10();
      intValue17 = intValue17 * 59 + this.getIntValue11();
      intValue17 = intValue17 * 59 + this.getIntValue12();
      intValue17 = intValue17 * 59 + this.getIntValue13();
      intValue17 = intValue17 * 59 + this.getIntValue14();
      intValue17 = intValue17 * 59 + this.getIntValue15();
      return intValue17 * 59 + (this.isFlag() ? 79 : 97);
   }

   @Generated
   @Override
   public String toString() {
      return "Colors(panel="
         + this.getIntValue()
         + ", surface="
         + this.getIntValue2()
         + ", white01="
         + this.getIntValue3()
         + ", white02="
         + this.getIntValue4()
         + ", white03="
         + this.getIntValue5()
         + ", white04="
         + this.getIntValue6()
         + ", white06="
         + this.getIntValue7()
         + ", white08="
         + this.getIntValue8()
         + ", white12="
         + this.getIntValue9()
         + ", white16="
         + this.getIntValue10()
         + ", white24="
         + this.getIntValue11()
         + ", white48="
         + this.getIntValue12()
         + ", white="
         + this.getIntValue13()
         + ", accentTop="
         + this.getIntValue14()
         + ", accentBottom="
         + this.getIntValue15()
         + ", lightMode="
         + this.isFlag()
         + ")";
   }

   @Generated
   public static class ColorSchemeState {
      @Generated
      private int intValue;
      @Generated
      private int intValue2;
      @Generated
      private int intValue3;
      @Generated
      private int intValue4;
      @Generated
      private int intValue5;
      @Generated
      private int intValue6;
      @Generated
      private int intValue7;
      @Generated
      private int intValue8;
      @Generated
      private int intValue9;
      @Generated
      private int intValue10;
      @Generated
      private int intValue11;
      @Generated
      private int intValue12;
      @Generated
      private int intValue13;
      @Generated
      private int intValue14;
      @Generated
      private int intValue15;
      @Generated
      private boolean flag;

      @Generated
      ColorSchemeState() {
      }

      @Generated
      public ColorScheme.ColorSchemeState setIntValue(int i) {
         this.intValue = i;
         return this;
      }

      @Generated
      public ColorScheme.ColorSchemeState setIntValue2(int i) {
         this.intValue2 = i;
         return this;
      }

      @Generated
      public ColorScheme.ColorSchemeState setIntValue3(int i) {
         this.intValue3 = i;
         return this;
      }

      @Generated
      public ColorScheme.ColorSchemeState setIntValue4(int i) {
         this.intValue4 = i;
         return this;
      }

      @Generated
      public ColorScheme.ColorSchemeState setIntValue5(int i) {
         this.intValue5 = i;
         return this;
      }

      @Generated
      public ColorScheme.ColorSchemeState setIntValue6(int i) {
         this.intValue6 = i;
         return this;
      }

      @Generated
      public ColorScheme.ColorSchemeState setIntValue7(int i) {
         this.intValue7 = i;
         return this;
      }

      @Generated
      public ColorScheme.ColorSchemeState setIntValue8(int i) {
         this.intValue8 = i;
         return this;
      }

      @Generated
      public ColorScheme.ColorSchemeState setIntValue9(int i) {
         this.intValue9 = i;
         return this;
      }

      @Generated
      public ColorScheme.ColorSchemeState setIntValue10(int i) {
         this.intValue10 = i;
         return this;
      }

      @Generated
      public ColorScheme.ColorSchemeState setIntValue11(int i) {
         this.intValue11 = i;
         return this;
      }

      @Generated
      public ColorScheme.ColorSchemeState setIntValue12(int i) {
         this.intValue12 = i;
         return this;
      }

      @Generated
      public ColorScheme.ColorSchemeState setIntValue13(int i) {
         this.intValue13 = i;
         return this;
      }

      @Generated
      public ColorScheme.ColorSchemeState setIntValue14(int i) {
         this.intValue14 = i;
         return this;
      }

      @Generated
      public ColorScheme.ColorSchemeState setIntValue15(int i) {
         this.intValue15 = i;
         return this;
      }

      @Generated
      public ColorScheme.ColorSchemeState setFlag(boolean bl) {
         this.flag = bl;
         return this;
      }

      @Generated
      public ColorScheme resolve() {
         return new ColorScheme(
            this.intValue,
            this.intValue2,
            this.intValue3,
            this.intValue4,
            this.intValue5,
            this.intValue6,
            this.intValue7,
            this.intValue8,
            this.intValue9,
            this.intValue10,
            this.intValue11,
            this.intValue12,
            this.intValue13,
            this.intValue14,
            this.intValue15,
            this.flag
         );
      }

      @Generated
      @Override
      public String toString() {
         return "Colors.ColorsBuilder(panel="
            + this.intValue
            + ", surface="
            + this.intValue2
            + ", white01="
            + this.intValue3
            + ", white02="
            + this.intValue4
            + ", white03="
            + this.intValue5
            + ", white04="
            + this.intValue6
            + ", white06="
            + this.intValue7
            + ", white08="
            + this.intValue8
            + ", white12="
            + this.intValue9
            + ", white16="
            + this.intValue10
            + ", white24="
            + this.intValue11
            + ", white48="
            + this.intValue12
            + ", white="
            + this.intValue13
            + ", accentTop="
            + this.intValue14
            + ", accentBottom="
            + this.intValue15
            + ", lightMode="
            + this.flag
            + ")";
      }
   }
}
