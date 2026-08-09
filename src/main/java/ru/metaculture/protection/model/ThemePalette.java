package ru.metaculture.protection;

import java.util.Arrays;
import java.util.List;
import lombok.Generated;

public final class ThemePalette {
   public static final int INT_VALUE = -1314568;
   public static final int INT_VALUE_2 = -858662426;
   public static final int INT_VALUE_3 = -1;
   public static final int INT_VALUE_4 = -15328994;
   public static final int INT_VALUE_5 = -435483376;
   public static final int INT_VALUE_6 = 1713580081;
   private final List<ThemePalette.Swatch> items;

   public static ThemePalette.BaseColors resolve(boolean bl) {
      return bl ? new ThemePalette.BaseColors(-1314568, -858662426, -1) : new ThemePalette.BaseColors(-15328994, -435483376, 1713580081);
   }

   public static ThemePalette resolve2() {
      return new ThemePalette(
         List.of(
            new ThemePalette.Swatch("Sakura Breeze", Theme.SAKURA_BREEZE, true, -18491, -16181, -1541, -18491, -16181, -1),
            new ThemePalette.Swatch("Vernal Solstice", Theme.VERNAL_SOLSTICE, true, -13447886, -10496, -13447886, -10496, -6291605, -1441815, -4720701, -3416),
            new ThemePalette.Swatch("Porcelain Dawn", Theme.PORCELAIN_DAWN, true, -20342, -30116, -20342, -30116, -13912),
            new ThemePalette.Swatch("Frutiger Aero", Theme.FRUTIGER_AERO, true, -8657678, -13722666, -8657678, -13121888, -7346033, -13722666, -8657678),
            new ThemePalette.Swatch("Midnight Azure", Theme.MIDNIGHT_AZURE, -16715521, -16759553, -16715521, -16733953, -16759553, -12292609, -2556929, -16715521),
            new ThemePalette.Swatch("Cherry", Theme.CHERRY, -24930, -32126),
            new ThemePalette.Swatch("Rose", Theme.ROSE, -24854, -32032),
            new ThemePalette.Swatch("Sun", Theme.SUN, -196706, -126),
            new ThemePalette.Swatch("Tangerine", Theme.TANGERINE, -15458, -29342),
            new ThemePalette.Swatch("Amethyst", Theme.AMETHYST, -6185217, -7437569),
            new ThemePalette.Swatch("Aqua", Theme.AQUA, -6364417, -8198913),
            new ThemePalette.Swatch("Mint", Theme.MINT, -6357069, -8192089),
            new ThemePalette.Swatch("Teal", Theme.LAGOON, -6357027, -10095918),
            new ThemePalette.Swatch("Blush", Theme.LOTUS, -24894, -32076),
            new ThemePalette.Swatch("Orchid", Theme.ORCHID, -23827, -36913),
            new ThemePalette.Swatch("Nebula", Theme.NEBULA, -5921793, -9603841),
            new ThemePalette.Swatch("Aurora", Theme.AURORA, -6357021, -11341636),
            new ThemePalette.Swatch("Volt", Theme.VOLT, -1507446, -4718787),
            new ThemePalette.Swatch("Cyber", Theme.CYBER, -7406593, -12985857),
            new ThemePalette.Swatch("Coral", Theme.CORAL, -25682, -41090),
            new ThemePalette.Swatch("Arctic", Theme.ARCTIC, -2622209, -7607553),
            new ThemePalette.Swatch("Peacock", Theme.PEACOCK, -8130305, -10841345),
            new ThemePalette.Swatch("Candy", Theme.CANDY, -22281, -6293249),
            new ThemePalette.Swatch("Matrix", Theme.MATRIX, -7471205, -12648600),
            new ThemePalette.Swatch("Bloodmoon", Theme.BLOODMOON, -30569, -52402),
            new ThemePalette.Swatch("Noir", Theme.NOIR, -987137, -5790503),
            new ThemePalette.Swatch("Prism", Theme.PRISM, -6422567, -6316289),
            new ThemePalette.Swatch("Velvet", Theme.VELVET, -1923585, -4756993),
            new ThemePalette.Swatch("Custom", Theme.CUSTOM, -1, -7433050),
            new ThemePalette.Swatch("Astolfo-rainbow", Theme.ASTOLFO_RAINBOW, -29969, -8128257, -29969, -22820, -8128257, -5636114),
            new ThemePalette.Swatch("Lagune-rainbow", Theme.LAGUNE_RAINBOW, -10027033, -12088321, -10027033, -10762241, -12088321, -5046284),
            new ThemePalette.Swatch("0.5-rainbow", Theme.HALF_RAINBOW, -3989, -41059, -3989, -25262, -41059, -7473153),
            new ThemePalette.Swatch("Aurora-rainbow", Theme.AURORA_RAINBOW, -5636168, -8549121, -5636168, -10291758, -8549121, -22028),
            new ThemePalette.Swatch("Neon-rainbow", Theme.NEON_RAINBOW, -8519833, -49678, -8519833, -13371393, -4006, -49678),
            new ThemePalette.Swatch("Blossom-rainbow", Theme.BLOSSOM_RAINBOW, -22584, -5777153, -22584, -8049, -5777153, -2709505),
            new ThemePalette.Swatch("Abyss-rainbow", Theme.ABYSS_RAINBOW, -8743937, -10813482, -8743937, -5215233, -10813482, -41074),
            new ThemePalette.Swatch("Sunset-rainbow", Theme.SUNSET_RAINBOW, -19622, -45175, -19622, -38070, -45175, -2781953),
            new ThemePalette.Swatch("Glacier-rainbow", Theme.GLACIER_RAINBOW, -2688001, -7627265, -2688001, -7607553, -7627265, -1),
            new ThemePalette.Swatch("Chroma-rainbow", Theme.CHROMA_RAINBOW, -41107, -10616904, -41107, -7076, -10616904, -9597697),
            new ThemePalette.Swatch("Dream-rainbow", Theme.DREAM_RAINBOW, -2775297, -9043994, -2775297, -24613, -9043994, -3956),
            new ThemePalette.Swatch("Toxic-rainbow", Theme.TOXIC_RAINBOW, -3604664, -13044993, -3604664, -3750, -13044993, -41759),
            new ThemePalette.Swatch("Aurora Borealis", Theme.AURORA_BOREALIS, -10747980, -9730561, -10747980, -37947, -14997, -9699351),
            new ThemePalette.Swatch("Tokyo Neon", Theme.TOKYO_NEON, -57736, -12255278, -57736, -8691201, -74951, -12255278),
            new ThemePalette.Swatch("Galaxy", Theme.GALAXY, -9740289, -41035, -9740289, -5350401, -41035, -10498049),
            new ThemePalette.Swatch("Lava", Theme.LAVA, -21955, -49828, -21955, -38083, -49828, -9877),
            new ThemePalette.Swatch("Frost", Theme.FROST, -5707521, -9710593, -5707521, -11552257, -8410881, -4853505),
            new ThemePalette.Swatch("Sakura", Theme.SAKURA, -13860, -32843, -13860, -24372, -32843, -2055937),
            new ThemePalette.Swatch("Forest Mist", Theme.FOREST_MIST, -9699390, -13057392, -9699390, -11541080, -13057392, -7340071),
            new ThemePalette.Swatch("Cosmic Latte", Theme.COSMIC_LATTE, -6752, -1660831, -6752, -669313, -1660831, -13126),
            new ThemePalette.Swatch("Synthwave", Theme.SYNTHWAVE, -51019, -13056513, -51019, -297473, -21668, -8692737),
            new ThemePalette.Swatch("Holographic", Theme.HOLOGRAPHIC, -24321, -6225921, -24321, -96, -6226016, -3104513),
            new ThemePalette.Swatch("Midnight Ocean", Theme.MIDNIGHT_OCEAN, -12612097, -9740289, -12612097, -13057793, -9740289, -5252609),
            new ThemePalette.Swatch("Magma", Theme.MAGMA, -22436, -49828, -22436, -37059, -49828, -3922881),
            new ThemePalette.Swatch("Obsidian Ember", Theme.OBSIDIAN_EMBER, -20119, -42198, -42198, -25531, -14221),
            new ThemePalette.Swatch("Glacier Veil", Theme.GLACIER_VEIL, -5706497, -12681729, -5706497, -11352065, -12681729, -8390688),
            new ThemePalette.Swatch("Velvet Dusk", Theme.VELVET_DUSK, -3563265, -8635667, -3563265, -8635667, -34106, -14221)
         )
      );
   }

   public int compute(Theme theme) {
      for (int intValue = 0; intValue < this.items.size(); intValue++) {
         if (this.items.get(intValue).getTheme() == theme) {
            return intValue;
         }
      }

      return -1;
   }

   public ThemePalette.Swatch resolve3(Theme theme2) {
      for (ThemePalette.Swatch swatch : this.items) {
         if (swatch.getTheme() == theme2) {
            return swatch;
         }
      }

      return null;
   }

   public boolean check(Theme theme3) {
      ThemePalette.Swatch swatch2 = this.resolve3(theme3);
      return swatch2 != null && swatch2.isFlag();
   }

   @Generated
   public ThemePalette(List<ThemePalette.Swatch> list) {
      this.items = list;
   }

   @Generated
   public List<ThemePalette.Swatch> getItems() {
      return this.items;
   }

   @Generated
   @Override
   public boolean equals(Object object) {
      if (object == this) {
         return true;
      } else if (!(object instanceof ThemePalette themePalette)) {
         return false;
      } else {
         List items = this.getItems();
         List items2 = themePalette.getItems();
         return items == null ? items2 == null : items.equals(items2);
      }
   }

   @Generated
   @Override
   public int hashCode() {
      byte byteValue = 59;
      byte byteValue2 = 1;
      List items3 = this.getItems();
      return byteValue2 * 59 + (items3 == null ? 43 : items3.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "ThemePalette(swatches=" + this.getItems() + ")";
   }

   public record BaseColors(int baseColor, int darkShadowColor, int lightShadowColor) {
   }

   public static final class Swatch {
      private final String text;
      private final Theme theme;
      private final int intValue;
      private final int intValue2;
      private final boolean flag;
      private final int[] ints;

      public Swatch(String string, Theme theme4, int i, int j) {
         this(string, theme4, i, j, i, j);
      }

      public Swatch(String string, Theme theme5, int i, int j, int... is) {
         this(string, theme5, false, i, j, is);
      }

      public Swatch(String string, Theme theme6, boolean bl, int i, int j, int... is) {
         this.text = string;
         this.theme = theme6;
         this.intValue = i;
         this.intValue2 = j;
         this.flag = bl;
         this.ints = is != null && is.length >= 2 ? is : new int[]{i, j};
      }

      @Generated
      public String getText() {
         return this.text;
      }

      @Generated
      public Theme getTheme() {
         return this.theme;
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
      public boolean isFlag() {
         return this.flag;
      }

      @Generated
      public int[] getInts() {
         return this.ints;
      }

      @Generated
      @Override
      public boolean equals(Object object) {
         if (object == this) {
            return true;
         } else if (!(object instanceof ThemePalette.Swatch swatch3)) {
            return false;
         } else if (this.getIntValue() != swatch3.getIntValue()) {
            return false;
         } else if (this.getIntValue2() != swatch3.getIntValue2()) {
            return false;
         } else if (this.isFlag() != swatch3.isFlag()) {
            return false;
         } else {
            String text = this.getText();
            String text2 = swatch3.getText();
            if (text == null) {
               if (text2 != null) {
                  return false;
               }
            } else if (!text.equals(text2)) {
               return false;
            }

            Theme theme7 = this.getTheme();
            Theme theme8 = swatch3.getTheme();
            return (theme7 == null ? theme8 != null : !theme7.equals(theme8)) ? false : Arrays.equals(this.getInts(), swatch3.getInts());
         }
      }

      @Generated
      @Override
      public int hashCode() {
         byte byteValue3 = 59;
         int intValue2 = 1;
         intValue2 = intValue2 * 59 + this.getIntValue();
         intValue2 = intValue2 * 59 + this.getIntValue2();
         intValue2 = intValue2 * 59 + (this.isFlag() ? 79 : 97);
         String text3 = this.getText();
         intValue2 = intValue2 * 59 + (text3 == null ? 43 : text3.hashCode());
         Theme theme9 = this.getTheme();
         intValue2 = intValue2 * 59 + (theme9 == null ? 43 : theme9.hashCode());
         return intValue2 * 59 + Arrays.hashCode(this.getInts());
      }

      @Generated
      @Override
      public String toString() {
         return "ThemePalette.Swatch(displayName="
            + this.getText()
            + ", theme="
            + this.getTheme()
            + ", top="
            + this.getIntValue()
            + ", bottom="
            + this.getIntValue2()
            + ", lightMode="
            + this.isFlag()
            + ", stops="
            + Arrays.toString(this.getInts())
            + ")";
      }
   }
}
