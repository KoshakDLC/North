package ru.metaculture.protection;

import lombok.Generated;

public final class ThemeContext {
   private final Theme theme;
   private final Metrics metrics;
   private final ColorScheme colorScheme;
   private final ThemePalette themePalette;

   public boolean check() {
      if (this.colorScheme != null && this.colorScheme.isFlag()) {
         return true;
      } else {
         return this.themePalette == null ? false : this.themePalette.check(this.theme);
      }
   }

   public boolean check2() {
      return this.check();
   }

   @Generated
   ThemeContext(Theme theme, Metrics metrics, ColorScheme colorScheme, ThemePalette themePalette) {
      this.theme = theme;
      this.metrics = metrics;
      this.colorScheme = colorScheme;
      this.themePalette = themePalette;
   }

   @Generated
   public static ThemeContext.ThemeContextBuilder resolve() {
      return new ThemeContext.ThemeContextBuilder();
   }

   @Generated
   public Theme getTheme() {
      return this.theme;
   }

   @Generated
   public Metrics getMetrics() {
      return this.metrics;
   }

   @Generated
   public ColorScheme getColorScheme() {
      return this.colorScheme;
   }

   @Generated
   public ThemePalette getThemePalette() {
      return this.themePalette;
   }

   @Generated
   @Override
   public boolean equals(Object object) {
      if (object == this) {
         return true;
      } else if (!(object instanceof ThemeContext themeContext)) {
         return false;
      } else {
         Theme theme2 = this.getTheme();
         Theme theme3 = themeContext.getTheme();
         if (theme2 == null) {
            if (theme3 != null) {
               return false;
            }
         } else if (!theme2.equals(theme3)) {
            return false;
         }

         Metrics metrics2 = this.getMetrics();
         Metrics metrics3 = themeContext.getMetrics();
         if (metrics2 == null ? metrics3 == null : metrics2.equals(metrics3)) {
            ColorScheme colorScheme2 = this.getColorScheme();
            ColorScheme colorScheme3 = themeContext.getColorScheme();
            if (colorScheme2 == null ? colorScheme3 == null : colorScheme2.equals(colorScheme3)) {
               ThemePalette themePalette2 = this.getThemePalette();
               ThemePalette themePalette3 = themeContext.getThemePalette();
               if (themePalette2 == null) {
                  if (themePalette3 != null) {
                     return false;
                  }
               } else if (!themePalette2.equals(themePalette3)) {
                  return false;
               }

               return true;
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   @Generated
   @Override
   public int hashCode() {
      byte byteValue = 59;
      int intValue = 1;
      Theme theme4 = this.getTheme();
      intValue = intValue * 59 + (theme4 == null ? 43 : theme4.hashCode());
      Metrics metrics4 = this.getMetrics();
      intValue = intValue * 59 + (metrics4 == null ? 43 : metrics4.hashCode());
      ColorScheme colorScheme4 = this.getColorScheme();
      intValue = intValue * 59 + (colorScheme4 == null ? 43 : colorScheme4.hashCode());
      ThemePalette themePalette4 = this.getThemePalette();
      return intValue * 59 + (themePalette4 == null ? 43 : themePalette4.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "ThemeContext(theme="
         + this.getTheme()
         + ", metrics="
         + this.getMetrics()
         + ", colors="
         + this.getColorScheme()
         + ", palette="
         + this.getThemePalette()
         + ")";
   }

   @Generated
   public static class ThemeContextBuilder {
      @Generated
      private Theme theme;
      @Generated
      private Metrics metrics;
      @Generated
      private ColorScheme colorScheme;
      @Generated
      private ThemePalette themePalette;

      @Generated
      ThemeContextBuilder() {
      }

      @Generated
      public ThemeContext.ThemeContextBuilder setTheme(Theme theme5) {
         this.theme = theme5;
         return this;
      }

      @Generated
      public ThemeContext.ThemeContextBuilder setMetrics(Metrics metrics5) {
         this.metrics = metrics5;
         return this;
      }

      @Generated
      public ThemeContext.ThemeContextBuilder setColorScheme(ColorScheme colorScheme5) {
         this.colorScheme = colorScheme5;
         return this;
      }

      @Generated
      public ThemeContext.ThemeContextBuilder setThemePalette(ThemePalette themePalette5) {
         this.themePalette = themePalette5;
         return this;
      }

      @Generated
      public ThemeContext resolve() {
         return new ThemeContext(this.theme, this.metrics, this.colorScheme, this.themePalette);
      }

      @Generated
      @Override
      public String toString() {
         return "ThemeContext.ThemeContextBuilder(theme="
            + this.theme
            + ", metrics="
            + this.metrics
            + ", colors="
            + this.colorScheme
            + ", palette="
            + this.themePalette
            + ")";
      }
   }
}
