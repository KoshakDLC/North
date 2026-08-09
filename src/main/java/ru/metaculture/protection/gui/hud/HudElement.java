package ru.metaculture.protection;

import net.minecraft.client.MinecraftClient;

public abstract class HudElement extends ConfigurableHudElement {
   public static final String TYOMNYY = "Тёмный";
   public static final String SVETLYY = "Светлый";
   public static final String BLYUR = "Блюр";
   public static final String NEOMORFIZM = "Неоморфизм";
   public static final String FERROFLYUID = "Феррофлюид";
   public static final String PRIZMA = "Призма";
   public static final String PRIZMA_CORE = "Призма Core";
   public static final String NEO_DISTANTSIYA = "Нео дистанция";
   public static final String NEO_RAZMYTIE = "Нео размытие";
   public static final String NEO_INTENSIVNOST = "Нео интенсивность";
   public static final String NEO_FORMA = "Нео форма";
   public static final String PLOSKAYA = "Плоская";
   public static final String VYPUKLAYA = "Выпуклая";
   public static final String VOGNUTAYA = "Вогнутая";
   protected static final float FLOAT_VALUE = 7.0F;
   protected static final float FLOAT_VALUE_2 = 5.0F;
   protected static final float FLOAT_VALUE_3 = 10.0F;
   public final NumberSetting prozrachnost = new NumberSetting("Прозрачность", 1.0F, 0.1F, 1.0F, 0.05F, true);
   public final NumberSetting prozrachnostTyomnyhElementov = new NumberSetting("Прозрачность тёмных элементов", 1.0F, 0.0F, 1.0F, 0.05F, true);
   public final ModeSetting stilistika = new ModeSetting("Стилистика", "Тёмный", "Тёмный", "Светлый", "Блюр", "Неоморфизм", "Феррофлюид", "Призма");
   public final NumberSetting neoDistantsiya = new NumberSetting("Нео дистанция", 5.5F, 2.0F, 18.0F, 0.5F, false).setVisibilityCondition(() -> !this.check8());
   public final NumberSetting neoRazmytie = new NumberSetting("Нео размытие", 18.0F, 6.0F, 48.0F, 1.0F, false).setVisibilityCondition(() -> !this.check8());
   public final NumberSetting neoIntensivnost = new NumberSetting("Нео интенсивность", 0.72F, 0.1F, 1.0F, 0.05F, true).setVisibilityCondition(() -> !this.check8());
   public final ModeSetting neoForma = new ModeSetting("Нео форма", "Выпуклая", "Плоская", "Выпуклая", "Вогнутая").setVisibilityCondition(() -> !this.check8());
   public final GroupSetting vizual = new GroupSetting(
      "Визуал",
      new BooleanSetting("Тень", true),
      new BooleanSetting("Обводка", true),
      new BooleanSetting("Темные зоны", true),
      new BooleanSetting("Верхняя накладка", true),
      new BooleanSetting("Нижняя накладка", true),
      new BooleanSetting("Тёмный рект поверх", true)
   );
   private Theme theme;
   private boolean flag;
   private ColorScheme colorScheme;
   private ThemePalette.Swatch swatch;
   private Theme theme2;
   private long timestamp = Long.MIN_VALUE;
   private int intValue;
   private float floatValue = 0.5F;
   private float floatValue2 = 0.5F;
   private int intValue2;
   private final SpringAnimation springAnimation = new SpringAnimation(0.0F);
   private static final ThemePalette THEME_PALETTE = ThemePalette.resolve2();

   public HudElement() {
      this.invoke(this.prozrachnost);
      this.invoke(this.prozrachnostTyomnyhElementov);
      this.invoke(this.stilistika);
      this.invoke(this.neoDistantsiya);
      this.invoke(this.neoRazmytie);
      this.invoke(this.neoIntensivnost);
      this.invoke(this.neoForma);
      this.invoke(this.vizual);
   }

   public int compute(float f) {
      if (this.check8()) {
         return NeumorphismRenderer.compute(f);
      } else if (this.check9()) {
         int intValue = (int)(232.0F * f);
         float floatValue = this.measure3();
         int intValue2 = ColorUtils.compute14(
            ColorUtils.compute43(13, 15, 24, intValue), ColorUtils.compute2(this.resolve2().getIntValue15(), intValue), 0.12F
         );
         int intValue3 = ColorUtils.compute14(
            ColorUtils.compute43(250, 253, 255, (int)(152.0F * f)),
            ColorUtils.compute2(this.resolve2().getIntValue14(), (int)(132.0F * f)),
            0.055F
         );
         return ColorUtils.compute14(intValue2, intValue3, floatValue);
      } else if (this.check10()) {
         int intValue4 = (int)(172.0F * f);
         return ColorUtils.compute14(
            ColorUtils.compute43(14, 18, 30, intValue4), ColorUtils.compute2(this.resolve2().getIntValue15(), intValue4), 0.1F
         );
      } else {
         int intValue5 = (int)(255.0F * f);
         if (this.check17()) {
            return ColorUtils.compute2(this.compute12(), (int)((this.check18() ? 152 : 176) * f));
         } else {
            String text = this.stilistika.getValue();

            return switch (text) {
               case "Светлый" -> ColorUtils.compute43(240, 240, 245, intValue5);
               case "Блюр" -> ColorUtils.compute43(21, 22, 26, this.check6() ? (int)(122.0F * f) : 0);
               default -> ColorUtils.compute43(20, 20, 20, intValue5);
            };
         }
      }
   }

   public int compute2(float f) {
      return this.compute4(f, this.check4());
   }

   public int compute3(float f) {
      return this.compute4(f, this.check5());
   }

   private int compute4(float f, boolean bl) {
      if (!bl) {
         return ColorUtils.compute43(0, 0, 0, 0);
      } else if (this.check8()) {
         return NeumorphismRenderer.compute(f);
      } else {
         float floatValue2 = this.measure(f);
         if (this.check9()) {
            int intValue6 = (int)(202.0F * floatValue2);
            float floatValue3 = this.measure3();
            int intValue7 = ColorUtils.compute14(
               ColorUtils.compute43(15, 18, 30, intValue6), ColorUtils.compute2(this.resolve2().getIntValue14(), intValue6), 0.1F
            );
            int intValue8 = ColorUtils.compute14(
               ColorUtils.compute43(255, 255, 255, (int)(132.0F * floatValue2)),
               ColorUtils.compute2(this.resolve2().getIntValue15(), (int)(118.0F * floatValue2)),
               0.06F
            );
            return ColorUtils.compute14(intValue7, intValue8, floatValue3);
         } else if (this.check10()) {
            int intValue9 = (int)(150.0F * floatValue2);
            return ColorUtils.compute14(
               ColorUtils.compute43(12, 16, 26, intValue9), ColorUtils.compute2(this.resolve2().getIntValue14(), intValue9), 0.08F
            );
         } else {
            int intValue10 = (int)(255.0F * floatValue2);
            if (this.check17()) {
               return ColorUtils.compute2(this.compute13(), (int)((this.check18() ? 138 : 160) * floatValue2));
            } else {
               String text2 = this.stilistika.getValue();

               return switch (text2) {
                  case "Светлый" -> ColorUtils.compute43(200, 200, 205, intValue10);
                  case "Блюр" -> ColorUtils.compute43(21, 22, 26, (int)(184.0F * floatValue2));
                  default -> ColorUtils.compute43(25, 25, 25, intValue10);
               };
            }
         }
      }
   }

   public int compute5(float f) {
      if (this.check8()) {
         return ColorUtils.compute43(0, 0, 0, 0);
      } else if (this.check9()) {
         float floatValue4 = this.measure3();
         int intValue11 = ColorUtils.compute2(this.compute18(), (int)(92.0F * f));
         int intValue12 = ColorUtils.compute14(
            ColorUtils.compute43(20, 28, 42, (int)(56.0F * f)), ColorUtils.compute2(this.compute19(), (int)(76.0F * f)), 0.35F
         );
         return ColorUtils.compute14(intValue11, intValue12, floatValue4);
      } else if (this.check10()) {
         int intValue13 = ColorUtils.compute14(this.resolve2().getIntValue14(), this.resolve2().getIntValue15(), 0.5F);
         return ColorUtils.compute2(intValue13, (int)(70.0F * f));
      } else {
         int intValue14 = (int)(255.0F * f);
         if (this.resolveSelf() == Theme.VERNAL_SOLSTICE) {
            return ColorUtils.compute43(5, 17, 5, (int)(46.0F * f));
         } else if (this.check17()) {
            return ColorUtils.compute2(this.compute14(), (int)((this.check18() ? 38 : 48) * f));
         } else {
            String text3 = this.stilistika.getValue();

            return switch (text3) {
               case "Светлый" -> ColorUtils.compute43(200, 200, 200, intValue14);
               case "Блюр" -> ColorUtils.compute43(255, 255, 255, (int)(10.0F * f));
               default -> ColorUtils.compute43(45, 45, 45, intValue14);
            };
         }
      }
   }

   public int compute6(float f) {
      if (this.check8()) {
         return NeumorphismRenderer.compute2(f);
      } else if (this.check9()) {
         float floatValue5 = this.measure3();
         int intValue15 = ColorUtils.compute43(242, 245, 255, (int)(255.0F * f));
         int intValue16 = ColorUtils.compute43(18, 25, 38, (int)(255.0F * f));
         return ColorUtils.compute14(intValue15, intValue16, floatValue5);
      } else if (this.check10()) {
         return ColorUtils.compute43(244, 247, 255, (int)(255.0F * f));
      } else {
         int intValue17 = (int)(255.0F * f);
         if (this.resolveSelf() == Theme.VERNAL_SOLSTICE) {
            return ColorUtils.compute43(5, 17, 5, intValue17);
         } else if (this.check17()) {
            return ColorUtils.compute2(this.compute15(), intValue17);
         } else {
            return this.stilistika.getValue().equals("Светлый")
               ? ColorUtils.compute43(20, 20, 20, intValue17)
               : ColorUtils.compute43(255, 255, 255, intValue17);
         }
      }
   }

   public int compute7(float f) {
      if (this.check8()) {
         return NeumorphismRenderer.compute3(f);
      } else if (this.check9()) {
         float floatValue6 = this.measure3();
         int intValue18 = ColorUtils.compute43(170, 177, 196, (int)(214.0F * f));
         int intValue19 = ColorUtils.compute43(72, 84, 108, (int)(224.0F * f));
         return ColorUtils.compute14(intValue18, intValue19, floatValue6);
      } else if (this.check10()) {
         return ColorUtils.compute43(176, 184, 204, (int)(220.0F * f));
      } else {
         int intValue20 = (int)(255.0F * f);
         if (this.resolveSelf() == Theme.VERNAL_SOLSTICE) {
            return ColorUtils.compute43(5, 17, 5, (int)(184.0F * f));
         } else if (this.check17()) {
            return ColorUtils.compute2(this.compute16(), intValue20);
         } else {
            String text4 = this.stilistika.getValue();

            return switch (text4) {
               case "Светлый" -> ColorUtils.compute43(80, 80, 80, intValue20);
               case "Блюр" -> ColorUtils.compute43(255, 255, 255, (int)(122.0F * f));
               default -> ColorUtils.compute43(170, 170, 170, intValue20);
            };
         }
      }
   }

   public int compute8(float f) {
      return this.check9()
         ? ColorUtils.compute2(ColorScheme.compute7(this.compute18(), this.compute19(), 0.55F), (int)(255.0F * f))
         : ColorUtils.compute2(this.resolve2().getIntValue15(), (int)(255.0F * f));
   }

   public int compute9(float f) {
      return this.check9()
         ? ColorUtils.compute2(this.compute18(), (int)(255.0F * f))
         : ColorUtils.compute2(this.resolve2().getIntValue14(), (int)(255.0F * f));
   }

   public int compute10(float f) {
      return this.check9()
         ? ColorUtils.compute2(this.compute19(), (int)(255.0F * f))
         : ColorUtils.compute2(this.resolve2().getIntValue15(), (int)(255.0F * f));
   }

   public float measure(float f) {
      return f * this.prozrachnostTyomnyhElementov.getValue();
   }

   public float measure2() {
      return this.stilistika.getValue().equals("Блюр") ? 1.0F : 1.5F;
   }

   public boolean check() {
      return !this.check8() && this.vizual.isEnabled("Тень");
   }

   public boolean check2() {
      return !this.check8() && this.vizual.isEnabled("Обводка");
   }

   public boolean check3() {
      return this.vizual.isEnabled("Темные зоны");
   }

   public boolean check4() {
      return this.check3() && this.vizual.isEnabled("Верхняя накладка");
   }

   public boolean check5() {
      return this.check3() && this.vizual.isEnabled("Нижняя накладка");
   }

   public boolean check6() {
      return this.vizual.isEnabled("Тёмный рект поверх");
   }

   public boolean check7() {
      return !this.check8() && !this.check9() && !this.check10() ? this.stilistika.getValue().equals("Блюр") : false;
   }

   public boolean check8() {
      return "Неоморфизм".equals(this.stilistika.getValue());
   }

   public boolean check9() {
      return "Феррофлюид".equals(this.stilistika.getValue());
   }

   public boolean check10() {
      return "Призма".equals(this.stilistika.getValue()) || "Призма Core".equals(this.stilistika.getValue());
   }

   public static boolean check11(String string) {
      return "Неоморфизм".equals(string);
   }

   public static boolean check12(String string) {
      return "Феррофлюид".equals(string);
   }

   public static boolean check13(String string) {
      return "Призма".equals(string);
   }

   public boolean check14(float f, float g, float h, float i, float j, boolean bl, float k) {
      return this.check8()
         && NeumorphismRenderer.check9(
            null,
            f,
            g,
            h,
            i,
            j,
            bl,
            k,
            NeumorphismRenderer.resolve3(
               this.neoDistantsiya.getValue(), this.neoRazmytie.getValue(), this.neoIntensivnost.getValue(), this.neoForma.getValue()
            )
         );
   }

   public boolean check15(float f, float g, float h, float i, float j, boolean bl, float k, int l) {
      return this.check8()
         && NeumorphismRenderer.check11(
            null, f, g, h, i, j, this.neoDistantsiya.getValue(), this.neoRazmytie.getValue(), this.neoIntensivnost.getValue(), l, bl, k
         );
   }

   public boolean check16(float f, float g, float h, float i, float j, float k, float l, float m, int n, boolean bl, float o) {
      return this.check8() && NeumorphismRenderer.check11(null, f, g, h, i, j, k, l, m, n, bl, o);
   }

   public void invoke(RenderManager renderManager, float f, float g, float h, float i, float j, float k) {
      this.invoke3(f, g, h, i);
      if (!NeumorphismRenderer.check5(null, f, g, h, i, j, k)) {
         if (!this.check14(f, g, h, i, j, false, k)) {
            if (!this.check19(renderManager, f, g, h, i, j, false, k)) {
               if (!this.check20(renderManager, f, g, h, i, j, false, k)) {
                  if (this.check()) {
                     renderManager.invoke41(f, g, h, i, j, this.check17() ? 6.0F : 4.0F, 1.0F, this.compute17(k));
                  }

                  if (this.check7()) {
                     renderManager.invoke48(23.0F);
                     renderManager.invoke44(f, g, h, i, j, k);
                  }

                  if (this.check17() && !this.check7()) {
                     this.invoke4(renderManager, f, g, h, i, j, this.compute(k), false, k);
                  } else {
                     renderManager.invoke5(f, g, h, i, j, this.compute(k));
                  }

                  if (this.check2()) {
                     renderManager.invoke28(f, g, h, i, j, this.compute5(k), this.measure2());
                  }
               }
            }
         }
      }
   }

   public void invoke2(RenderManager renderManager2, float f, float g, float h, float i, float j, float k) {
      this.invoke3(f, g, h, i);
      if (!this.check19(renderManager2, f, g, h, i, j, true, k)) {
         if (!this.check20(renderManager2, f, g, h, i, j, true, k)) {
            if (this.check5()) {
               if (!this.check14(f, g, h, i, j, true, k)) {
                  if (this.check17() && !this.check7()) {
                     this.invoke4(renderManager2, f, g, h, i, j, this.compute3(k), true, k);
                  } else {
                     renderManager2.invoke5(f, g, h, i, j, this.compute3(k));
                  }

                  if (this.check2()) {
                     renderManager2.invoke28(f, g, h, i, j, this.compute5(k), Math.max(1.0F, this.measure2() * 0.65F));
                  }
               }
            }
         }
      }
   }

   protected boolean check17() {
      return "Светлый".equals(this.stilistika.getValue()) || NeumorphismRenderer.check13();
   }

   private boolean check18() {
      Theme theme = this.resolveSelf();
      return this.check17() && (theme == Theme.SAKURA_BREEZE || theme == Theme.SAKURA);
   }

   protected float measure3() {
      return this.springAnimation.measure(this.check17() ? 1.0F : 0.0F, SpringSpec.resolve17());
   }

   protected void invoke3(float f, float g, float h, float i) {
      int intValue21 = 0;
      int intValue22 = 0;

      try {
         MinecraftClient client = MinecraftClient.getInstance();
         if (client != null && client.getWindow() != null) {
            intValue21 = client.getWindow().getFramebufferWidth();
            intValue22 = client.getWindow().getFramebufferHeight();
         }
      } catch (Throwable exception) {
      }

      if (intValue21 > 0 && intValue22 > 0 && Float.isFinite(f) && Float.isFinite(g) && Float.isFinite(h) && Float.isFinite(i)) {
         this.floatValue = this.measure5((f + h * 0.5F) / intValue21);
         this.floatValue2 = this.measure5((g + i * 0.5F) / intValue22);
         int intValue23 = Math.round(this.floatValue * 2048.0F);
         int intValue24 = Math.round(this.floatValue2 * 2048.0F);
         int intValue25 = Math.round(Math.max(1.0F, h) * 0.25F);
         int intValue26 = Math.round(Math.max(1.0F, i) * 0.25F);
         this.intValue2 = (intValue23 * 7349 ^ intValue24 * 9151) * 31 ^ intValue25 * 131 ^ intValue26;
      } else {
         this.floatValue = 0.5F;
         this.floatValue2 = 0.5F;
         this.intValue2 = 0;
      }
   }

   private boolean check19(RenderManager renderManager3, float f, float g, float h, float i, float j, boolean bl, float k) {
      return this.check9()
         && HudBlurRenderer.check(
            renderManager3,
            f,
            g,
            h,
            i,
            j,
            k,
            bl,
            this.compute(k),
            this.compute5(k),
            this.compute9(k),
            this.compute10(k),
            this.check(),
            true,
            this.measure3()
         );
   }

   private boolean check20(RenderManager renderManager4, float f, float g, float h, float i, float j, boolean bl, float k) {
      return this.check10()
         && PrismaticHudRenderer.check(
            renderManager4,
            f,
            g,
            h,
            i,
            j,
            k,
            bl,
            this.compute(k),
            this.compute5(k),
            this.compute9(k),
            this.compute10(k),
            this.check(),
            this.check2(),
            this.measure3()
         );
   }

   private Theme resolveSelf() {
      return WildClient.INSTANCE != null && WildClient.INSTANCE.themeManager != null ? WildClient.INSTANCE.themeManager.getTheme() : Theme.WILD;
   }

   private ColorScheme resolve2() {
      Theme theme2 = this.resolveSelf();
      boolean flag = this.check17();
      long longValue = System.currentTimeMillis();
      long longValue2 = longValue / 16L;
      if (this.colorScheme == null
         || this.theme != theme2
         || this.flag != flag
         || this.timestamp != longValue2
         || this.intValue != this.intValue2
         || theme2 == Theme.CUSTOM) {
         ColorScheme colorScheme = ColorScheme.resolve2(theme2, flag);
         this.colorScheme = this.resolve3(theme2, ColorScheme.resolve3(theme2, colorScheme, longValue), longValue);
         this.theme = theme2;
         this.flag = flag;
         this.timestamp = longValue2;
         this.intValue = this.intValue2;
      }

      return this.colorScheme;
   }

   private ColorScheme resolve3(Theme theme3, ColorScheme colorScheme2, long l) {
      ThemePalette.Swatch swatch = this.resolve4();
      int[] intValues = swatch == null ? null : swatch.getInts();
      if (colorScheme2 != null && intValues != null && intValues.length >= 2) {
         float floatValue7 = this.measure4(l);
         int intValue27 = this.compute11(intValues, floatValue7);
         int intValue28 = this.compute11(intValues, floatValue7 + 0.31F);
         int intValue29 = this.compute11(intValues, floatValue7 + 0.67F);
         float floatValue8 = intValues.length > 2 ? 1.0F : 0.52F;
         float floatValue9 = 0.34F + 0.3F * floatValue8;
         float floatValue10 = colorScheme2.isFlag() ? 0.018F + 0.02F * floatValue8 : 0.045F + 0.05F * floatValue8;
         float floatValue11 = colorScheme2.isFlag() ? 0.06F + 0.035F * floatValue8 : 0.16F + 0.1F * floatValue8;
         int intValue30 = ColorUtils.compute14(colorScheme2.getIntValue14(), ColorUtils.compute14(intValue27, -1, 0.1F), floatValue9);
         int intValue31 = ColorUtils.compute14(colorScheme2.getIntValue15(), intValue28, floatValue9);
         int intValue32 = ColorScheme.compute8(colorScheme2.getIntValue(), intValue29, floatValue10);
         int intValue33 = ColorScheme.compute8(colorScheme2.getIntValue2(), intValue28, floatValue10 * 1.08F);
         int intValue34 = ColorScheme.compute8(colorScheme2.getIntValue9(), intValue27, floatValue11);
         int intValue35 = ColorScheme.compute8(colorScheme2.getIntValue10(), intValue28, floatValue11);
         int intValue36 = colorScheme2.isFlag()
            ? colorScheme2.getIntValue11()
            : ColorScheme.compute8(colorScheme2.getIntValue11(), intValue29, 0.1F + 0.08F * floatValue8);
         int intValue37 = colorScheme2.isFlag()
            ? colorScheme2.getIntValue12()
            : ColorScheme.compute8(colorScheme2.getIntValue12(), intValue27, 0.08F + 0.06F * floatValue8);
         int intValue38 = colorScheme2.isFlag()
            ? colorScheme2.getIntValue13()
            : ColorUtils.compute14(colorScheme2.getIntValue13(), intValue27, 0.025F + 0.025F * floatValue8);
         return ColorScheme.resolve5()
            .setIntValue(intValue32)
            .setIntValue2(intValue33)
            .setIntValue3(colorScheme2.getIntValue3())
            .setIntValue4(colorScheme2.getIntValue4())
            .setIntValue5(colorScheme2.getIntValue5())
            .setIntValue6(colorScheme2.getIntValue6())
            .setIntValue7(colorScheme2.getIntValue7())
            .setIntValue8(colorScheme2.getIntValue8())
            .setIntValue9(intValue34)
            .setIntValue10(intValue35)
            .setIntValue11(intValue36)
            .setIntValue12(intValue37)
            .setIntValue13(intValue38)
            .setIntValue14(intValue30)
            .setIntValue15(intValue31)
            .setFlag(colorScheme2.isFlag())
            .resolve();
      } else {
         return colorScheme2;
      }
   }

   private float measure4(long l) {
      float floatValue12 = (float)(l % 14000L) / 14000.0F;
      float floatValue13 = (float)Math.sin((this.floatValue * 1.72F - this.floatValue2 * 1.18F + floatValue12 * 1.35F) * (float) (Math.PI * 2)) * 0.055F;
      return this.floatValue * 0.54F + this.floatValue2 * 0.36F + floatValue12 * 0.58F + floatValue13;
   }

   private int compute11(int[] is, float f) {
      if (is.length == 1) {
         return is[0];
      } else {
         float floatValue14 = f - (float)Math.floor(f);
         float floatValue15 = floatValue14 * (is.length - 1);
         int intValue39 = Math.min(is.length - 2, Math.max(0, (int)Math.floor(floatValue15)));
         return ColorUtils.compute14(is[intValue39], is[intValue39 + 1], floatValue15 - intValue39);
      }
   }

   private float measure5(float f) {
      return Math.max(0.0F, Math.min(1.0F, f));
   }

   private ThemePalette.Swatch resolve4() {
      Theme theme4 = this.resolveSelf();
      if (this.swatch == null || this.theme2 != theme4 || theme4 == Theme.CUSTOM) {
         this.swatch = THEME_PALETTE.resolve3(theme4);
         this.theme2 = theme4;
      }

      return this.swatch;
   }

   private int compute12() {
      ColorScheme colorScheme3 = this.resolve2();
      if (this.check18()) {
         int intValue40 = ColorUtils.compute14(-1283, colorScheme3.getIntValue14(), 0.05F);
         return ColorUtils.compute14(intValue40, colorScheme3.getIntValue15(), 0.03F);
      } else {
         int intValue41 = ColorUtils.compute14(-196865, colorScheme3.getIntValue14(), 0.026F);
         ThemePalette.Swatch swatch2 = this.resolve4();
         if (swatch2 != null && swatch2.isFlag()) {
            intValue41 = ColorUtils.compute14(intValue41, swatch2.getIntValue(), 0.022F);
         }

         return intValue41;
      }
   }

   private int compute13() {
      ColorScheme colorScheme4 = this.resolve2();
      if (this.check18()) {
         int intValue42 = ColorUtils.compute14(-1, colorScheme4.getIntValue15(), 0.07F);
         return ColorUtils.compute14(intValue42, colorScheme4.getIntValue14(), 0.03F);
      } else {
         int intValue43 = ColorUtils.compute14(-1, colorScheme4.getIntValue15(), 0.022F);
         ThemePalette.Swatch swatch3 = this.resolve4();
         if (swatch3 != null && swatch3.isFlag()) {
            intValue43 = ColorUtils.compute14(intValue43, swatch3.getIntValue2(), 0.018F);
         }

         return intValue43;
      }
   }

   private int compute14() {
      ColorScheme colorScheme5 = this.resolve2();
      int intValue44 = ColorUtils.compute14(colorScheme5.getIntValue14(), colorScheme5.getIntValue15(), 0.44F);
      return this.check18() ? ColorUtils.compute14(-7582617, intValue44, 0.48F) : ColorUtils.compute14(-15261133, intValue44, 0.34F);
   }

   private int compute15() {
      ColorScheme colorScheme6 = this.resolve2();
      return ColorUtils.compute14(-15722718, colorScheme6.getIntValue14(), 0.035F);
   }

   private int compute16() {
      ColorScheme colorScheme7 = this.resolve2();
      return ColorUtils.compute14(-12168086, colorScheme7.getIntValue15(), 0.055F);
   }

   public int compute17(float f) {
      if (this.check17()) {
         ColorScheme colorScheme8 = this.resolve2();
         if (this.check18()) {
            int intValue45 = ColorUtils.compute14(-2779216, colorScheme8.getIntValue15(), 0.26F);
            return ColorUtils.compute2(intValue45, (int)(34.0F * f));
         } else {
            int intValue46 = ColorUtils.compute14(-10787208, colorScheme8.getIntValue15(), 0.1F);
            return ColorUtils.compute2(intValue46, (int)(46.0F * f));
         }
      } else {
         return ColorUtils.compute43(0, 0, 0, (int)(80.0F * f));
      }
   }

   private void invoke4(RenderManager renderManager5, float f, float g, float h, float i, float j, int k, boolean bl, float l) {
      int intValue47 = ColorUtils.compute4(k);
      ColorScheme colorScheme9 = this.resolve2();
      float floatValue16 = this.check18() ? 0.115F : 0.055F;
      float floatValue17 = this.check18() ? 0.085F : 0.04F;
      int intValue48 = ColorUtils.compute2(ColorUtils.compute14(k, colorScheme9.getIntValue14(), bl ? floatValue16 * 0.76F : floatValue16), intValue47);
      int intValue49 = ColorUtils.compute2(ColorUtils.compute14(k, colorScheme9.getIntValue15(), bl ? floatValue17 * 0.7F : floatValue17), intValue47);
      renderManager5.invoke37(f, g, h, i, j, intValue48, intValue49);
      if (!bl && i > 10.0F) {
         float floatValue18 = Math.max(4.0F, Math.min(i * 0.36F, 18.0F));
         int intValue50 = ColorUtils.compute43(255, 255, 255, (int)((this.check18() ? 34 : 24) * l));
         renderManager5.invoke37(
            f + 1.0F, g + 1.0F, Math.max(1.0F, h - 2.0F), floatValue18, Math.max(0.0F, j - 1.0F), intValue50, ColorUtils.compute43(255, 255, 255, 0)
         );
      }
   }

   private int compute18() {
      return ColorScheme.compute8(ColorUtils.compute2(this.resolve2().getIntValue14(), 255), -1, 0.2F);
   }

   private int compute19() {
      return ColorScheme.compute8(ColorUtils.compute2(this.resolve2().getIntValue15(), 255), -1, 0.12F);
   }
}
