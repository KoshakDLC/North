package ru.metaculture.protection;

public enum ThemeAnimationMode {
   STATIC(0L) {
      @Override
      public ColorScheme resolve(ColorScheme colorScheme, int[] is, long l) {
         return colorScheme;
      }
   },
   MULTI_GRADIENT(11000L) {
      @Override
      public ColorScheme resolve(ColorScheme colorScheme2, int[] is, long l) {
         float floatValue = measure(l, this.timestamp);
         float floatValue2 = 0.5F + 0.5F * (float)Math.sin(l * 9.0E-4);
         int intValue = ColorScheme.compute12(compute(is, floatValue), 0.18F);
         int intValue2 = compute(is, floatValue + 0.34F);
         int intValue3 = compute(is, floatValue + 0.18F);
         int intValue4 = compute(is, floatValue + 0.62F);
         int intValue5 = compute(is, floatValue + 0.84F);
         int intValue6 = compute(is, floatValue + 0.5F);
         int intValue7 = compute(is, floatValue + 0.05F);
         return ThemeAnimationMode.resolve4(
            colorScheme2,
            intValue,
            intValue2,
            ColorScheme.compute8(colorScheme2.getIntValue(), intValue3, 0.16F + 0.04F * floatValue2),
            ColorScheme.compute8(colorScheme2.getIntValue2(), intValue4, 0.18F + 0.04F * (1.0F - floatValue2)),
            ColorScheme.compute8(colorScheme2.getIntValue9(), intValue5, 0.3F),
            ColorScheme.compute8(colorScheme2.getIntValue10(), intValue5, 0.34F),
            ColorScheme.compute8(colorScheme2.getIntValue11(), intValue6, 0.2F),
            ColorScheme.compute8(colorScheme2.getIntValue12(), intValue6, 0.22F),
            ColorScheme.compute7(colorScheme2.getIntValue13(), intValue7, 0.06F)
         );
      }
   },
   TWIN_LAYERS(13000L) {
      @Override
      public ColorScheme resolve(ColorScheme colorScheme3, int[] is, long l) {
         float floatValue3 = measure(l, this.timestamp);
         float floatValue4 = 1.0F - floatValue3;
         float floatValue5 = 0.5F + 0.5F * (float)Math.sin(l * 0.0011);
         int intValue8 = ColorScheme.compute12(compute(is, floatValue3), 0.16F);
         int intValue9 = compute(is, floatValue4 + 0.5F);
         int intValue10 = compute(is, floatValue3 + 0.3F);
         int intValue11 = compute(is, floatValue4 + 0.3F);
         int intValue12 = compute(is, floatValue3 + 0.5F);
         int intValue13 = compute(is, floatValue4 + 0.18F);
         return ThemeAnimationMode.resolve4(
            colorScheme3,
            intValue8,
            intValue9,
            ColorScheme.compute8(colorScheme3.getIntValue(), intValue10, 0.18F + 0.05F * floatValue5),
            ColorScheme.compute8(colorScheme3.getIntValue2(), intValue11, 0.2F + 0.05F * (1.0F - floatValue5)),
            ColorScheme.compute8(colorScheme3.getIntValue9(), intValue12, 0.32F),
            ColorScheme.compute8(colorScheme3.getIntValue10(), intValue12, 0.36F),
            ColorScheme.compute8(colorScheme3.getIntValue11(), intValue13, 0.22F),
            ColorScheme.compute8(colorScheme3.getIntValue12(), intValue13, 0.24F),
            ColorScheme.compute7(colorScheme3.getIntValue13(), ColorScheme.compute7(intValue8, intValue9, 0.5F), 0.05F)
         );
      }
   },
   HUE_WHEEL(8500L) {
      @Override
      public ColorScheme resolve(ColorScheme colorScheme4, int[] is, long l) {
         float floatValue6 = measure(l, this.timestamp);
         float floatValue7 = 0.5F + 0.5F * (float)Math.sin(l * 0.0013);
         int intValue14 = ColorScheme.compute12(compute(is, floatValue6), 0.14F);
         int intValue15 = compute(is, floatValue6 + 0.3F);
         int intValue16 = compute(is, floatValue6 + 0.15F);
         int intValue17 = compute(is, floatValue6 + 0.6F);
         int intValue18 = compute(is, floatValue6 + 0.8F);
         int intValue19 = compute(is, floatValue6 + 0.45F);
         int intValue20 = compute(is, floatValue6 + 0.05F);
         return ThemeAnimationMode.resolve4(
            colorScheme4,
            intValue14,
            intValue15,
            ColorScheme.compute8(colorScheme4.getIntValue(), intValue16, 0.12F + 0.03F * floatValue7),
            ColorScheme.compute8(colorScheme4.getIntValue2(), intValue17, 0.14F + 0.03F * (1.0F - floatValue7)),
            ColorScheme.compute8(colorScheme4.getIntValue9(), intValue18, 0.3F),
            ColorScheme.compute8(colorScheme4.getIntValue10(), intValue18, 0.34F),
            ColorScheme.compute8(colorScheme4.getIntValue11(), intValue19, 0.18F),
            ColorScheme.compute8(colorScheme4.getIntValue12(), intValue19, 0.2F),
            ColorScheme.compute7(colorScheme4.getIntValue13(), intValue20, 0.05F)
         );
      }
   },
   BREATHING(7000L) {
      @Override
      public ColorScheme resolve(ColorScheme colorScheme5, int[] is, long l) {
         float floatValue8 = measure(l, this.timestamp);
         float floatValue9 = 0.5F + 0.5F * (float)Math.sin(floatValue8 * Math.PI * 2.0);
         float floatValue10 = 0.5F + 0.5F * (float)Math.sin(floatValue8 * Math.PI * 2.0 + 2.0734511513692637);
         int intValue21 = compute(is, 0.05F);
         int intValue22 = compute(is, 0.35F);
         int intValue23 = compute(is, 0.55F);
         int intValue24 = compute(is, 0.75F);
         int intValue25 = ColorScheme.compute12(intValue21, 0.04F + 0.1F * floatValue9);
         int intValue26 = ColorScheme.compute12(intValue22, 0.02F * (1.0F - floatValue9));
         return ThemeAnimationMode.resolve4(
            colorScheme5,
            intValue25,
            intValue26,
            ColorScheme.compute8(colorScheme5.getIntValue(), intValue23, 0.08F + 0.04F * floatValue9),
            ColorScheme.compute8(colorScheme5.getIntValue2(), intValue24, 0.1F + 0.04F * floatValue10),
            ColorScheme.compute8(colorScheme5.getIntValue9(), intValue21, 0.18F + 0.06F * floatValue9),
            ColorScheme.compute8(colorScheme5.getIntValue10(), intValue21, 0.2F + 0.06F * floatValue9),
            ColorScheme.compute8(colorScheme5.getIntValue11(), intValue22, 0.12F),
            ColorScheme.compute8(colorScheme5.getIntValue12(), intValue22, 0.16F),
            ColorScheme.compute7(colorScheme5.getIntValue13(), intValue25, 0.02F * floatValue9)
         );
      }
   },
   PRISMATIC_WAVE(8500L) {
      @Override
      public ColorScheme resolve(ColorScheme colorScheme6, int[] is, long l) {
         float floatValue11 = measure(l, this.timestamp);
         float floatValue12 = 0.5F + 0.5F * (float)Math.sin(l * 0.0017);
         int intValue27 = ColorScheme.compute12(compute(is, floatValue11), 0.16F);
         int intValue28 = compute(is, floatValue11 + 0.27F);
         int intValue29 = compute(is, floatValue11 + 0.12F + floatValue12 * 0.04F);
         int intValue30 = compute(is, floatValue11 + 0.55F + floatValue12 * 0.04F);
         int intValue31 = compute(is, floatValue11 + 0.78F);
         int intValue32 = compute(is, floatValue11 + 0.42F);
         return ThemeAnimationMode.resolve4(
            colorScheme6,
            intValue27,
            intValue28,
            ColorScheme.compute8(colorScheme6.getIntValue(), intValue29, 0.15F + 0.03F * floatValue12),
            ColorScheme.compute8(colorScheme6.getIntValue2(), intValue30, 0.17F + 0.03F * (1.0F - floatValue12)),
            ColorScheme.compute8(colorScheme6.getIntValue9(), intValue31, 0.3F),
            ColorScheme.compute8(colorScheme6.getIntValue10(), intValue31, 0.34F),
            ColorScheme.compute8(colorScheme6.getIntValue11(), intValue32, 0.2F),
            ColorScheme.compute8(colorScheme6.getIntValue12(), intValue32, 0.24F),
            ColorScheme.compute7(colorScheme6.getIntValue13(), ColorScheme.compute7(intValue27, intValue29, 0.5F), 0.05F)
         );
      }
   },
   RAINBOW_LINEAR(9000L) {
      @Override
      public ColorScheme resolve(ColorScheme colorScheme7, int[] is, long l) {
         float floatValue13 = measure(l, this.timestamp);
         float floatValue14 = 0.5F + 0.5F * (float)Math.sin(l * 0.0014);
         int intValue33 = compute(is, floatValue13);
         int intValue34 = ColorScheme.compute12(compute(is, floatValue13 + 0.38F), 0.18F);
         int intValue35 = compute(is, floatValue13 + 0.16F);
         int intValue36 = compute(is, floatValue13 + 0.58F);
         int intValue37 = compute(is, floatValue13 + 0.82F);
         int intValue38 = compute(is, floatValue13 + 0.46F);
         return ThemeAnimationMode.resolve4(
            colorScheme7,
            intValue34,
            intValue33,
            ColorScheme.compute8(colorScheme7.getIntValue(), intValue35, 0.1F + 0.05F * floatValue14),
            ColorScheme.compute8(colorScheme7.getIntValue2(), intValue36, 0.12F + 0.05F * (1.0F - floatValue14)),
            ColorScheme.compute8(colorScheme7.getIntValue9(), intValue37, 0.22F),
            ColorScheme.compute8(colorScheme7.getIntValue10(), intValue37, 0.26F),
            ColorScheme.compute8(colorScheme7.getIntValue11(), intValue38, 0.16F),
            ColorScheme.compute8(colorScheme7.getIntValue12(), intValue38, 0.18F),
            ColorScheme.compute7(colorScheme7.getIntValue13(), intValue35, 0.04F)
         );
      }
   };

   public final long timestamp;

   ThemeAnimationMode(long l) {
      this.timestamp = l;
   }

   public abstract ColorScheme resolve(ColorScheme colorScheme8, int[] is, long l);

   public static ThemeAnimationMode resolve2(Theme theme) {
      if (theme == null) {
         return STATIC;
      } else {
         ThemeAnimationMode themeAnimationMode = ThemeAnimationRegistry.resolve2(theme);
         return themeAnimationMode == null ? STATIC : themeAnimationMode;
      }
   }

   public static ColorScheme resolve3(Theme theme2, ColorScheme colorScheme9, long l) {
      if (theme2 == null || colorScheme9 == null) {
         return colorScheme9;
      } else if (!MenuModule.check(MenuModule.DREYF_TSVETA_TEMY)) {
         return colorScheme9;
      } else {
         int[] intValues = ThemeAnimationRegistry.resolve(theme2);
         ThemeAnimationMode themeAnimationMode2 = resolve2(theme2);
         return themeAnimationMode2 != STATIC && intValues != null && intValues.length >= 2 ? themeAnimationMode2.resolve(colorScheme9, intValues, l) : colorScheme9;
      }
   }

   static float measure(long l, long m) {
      if (m <= 0L) {
         return 0.0F;
      } else {
         long longValue = l % m;
         if (longValue < 0L) {
            longValue += m;
         }

         return (float)longValue / (float)m;
      }
   }

   static int compute(int[] is, float f) {
      if (is != null && is.length != 0) {
         if (is.length == 1) {
            return is[0];
         } else {
            float floatValue15 = f - (float)Math.floor(f);
            float floatValue16 = floatValue15 * (is.length - 1);
            int intValue39 = Math.min(is.length - 2, Math.max(0, (int)Math.floor(floatValue16)));
            return ColorScheme.compute7(is[intValue39], is[intValue39 + 1], floatValue16 - intValue39);
         }
      } else {
         return -1;
      }
   }

   static int compute2(float f, float g, float h) {
      f = (f % 360.0F + 360.0F) % 360.0F;
      float floatValue17 = (1.0F - Math.abs(2.0F * h - 1.0F)) * g;
      float floatValue18 = floatValue17 * (1.0F - Math.abs(f / 60.0F % 2.0F - 1.0F));
      float floatValue19 = h - floatValue17 * 0.5F;
      float floatValue20;
      float floatValue21;
      float floatValue22;
      if (f < 60.0F) {
         floatValue20 = floatValue17;
         floatValue21 = floatValue18;
         floatValue22 = 0.0F;
      } else if (f < 120.0F) {
         floatValue20 = floatValue18;
         floatValue21 = floatValue17;
         floatValue22 = 0.0F;
      } else if (f < 180.0F) {
         floatValue20 = 0.0F;
         floatValue21 = floatValue17;
         floatValue22 = floatValue18;
      } else if (f < 240.0F) {
         floatValue20 = 0.0F;
         floatValue21 = floatValue18;
         floatValue22 = floatValue17;
      } else if (f < 300.0F) {
         floatValue20 = floatValue18;
         floatValue21 = 0.0F;
         floatValue22 = floatValue17;
      } else {
         floatValue20 = floatValue17;
         floatValue21 = 0.0F;
         floatValue22 = floatValue18;
      }

      return ColorScheme.compute5(Math.round((floatValue20 + floatValue19) * 255.0F), Math.round((floatValue21 + floatValue19) * 255.0F), Math.round((floatValue22 + floatValue19) * 255.0F), 255);
   }

   static ColorScheme resolve4(ColorScheme colorScheme10, int i, int j, int k, int l, int m, int n, int o, int p, int q) {
      int intValue40 = colorScheme10.isFlag() ? colorScheme10.getIntValue11() : o;
      int intValue41 = colorScheme10.isFlag() ? colorScheme10.getIntValue12() : p;
      int intValue42 = colorScheme10.isFlag() ? colorScheme10.getIntValue13() : q;
      return ColorScheme.resolve5()
         .setIntValue(k)
         .setIntValue2(l)
         .setIntValue3(colorScheme10.getIntValue3())
         .setIntValue4(colorScheme10.getIntValue4())
         .setIntValue5(colorScheme10.getIntValue5())
         .setIntValue6(colorScheme10.getIntValue6())
         .setIntValue7(colorScheme10.getIntValue7())
         .setIntValue8(colorScheme10.getIntValue8())
         .setIntValue9(m)
         .setIntValue10(n)
         .setIntValue11(intValue40)
         .setIntValue12(intValue41)
         .setIntValue13(intValue42)
         .setIntValue14(i)
         .setIntValue15(j)
         .setFlag(colorScheme10.isFlag())
         .resolve();
   }
}
