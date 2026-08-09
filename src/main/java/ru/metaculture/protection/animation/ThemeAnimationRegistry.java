package ru.metaculture.protection;

import java.util.EnumMap;
import java.util.Map;

public final class ThemeAnimationRegistry {
   private static final Map<Theme, int[]> VALUES_BY_KEY = new EnumMap<>(Theme.class);
   private static final Map<Theme, ThemeAnimationMode> VALUES_BY_KEY_2 = new EnumMap<>(Theme.class);

   private ThemeAnimationRegistry() {
   }

   private static void invoke(Theme theme, ThemeAnimationMode themeAnimationMode, int... is) {
      VALUES_BY_KEY.put(theme, is);
      VALUES_BY_KEY_2.put(theme, themeAnimationMode);
   }

   public static int[] resolve(Theme theme2) {
      return theme2 == null ? null : VALUES_BY_KEY.get(theme2);
   }

   public static ThemeAnimationMode resolve2(Theme theme3) {
      if (theme3 == null) {
         return ThemeAnimationMode.STATIC;
      } else {
         ThemeAnimationMode themeAnimationMode2 = VALUES_BY_KEY_2.get(theme3);
         return themeAnimationMode2 == null ? ThemeAnimationMode.STATIC : themeAnimationMode2;
      }
   }

   public static boolean check(Theme theme4) {
      return theme4 != null && VALUES_BY_KEY_2.get(theme4) != null && VALUES_BY_KEY_2.get(theme4) != ThemeAnimationMode.STATIC;
   }

   static {
      invoke(Theme.ASTOLFO_RAINBOW, ThemeAnimationMode.MULTI_GRADIENT, -29969, -22820, -8128257, -5636114, -19472, -29969);
      invoke(Theme.LAGUNE_RAINBOW, ThemeAnimationMode.TWIN_LAYERS, -10027033, -10762241, -12088321, -5046284, -9649409, -10027033);
      invoke(Theme.HALF_RAINBOW, ThemeAnimationMode.MULTI_GRADIENT, -3989, -25262, -41059, -7473153, -9856, -3989);
      invoke(Theme.AURORA_RAINBOW, ThemeAnimationMode.MULTI_GRADIENT, -5636168, -10291758, -8549121, -22028, -8650800, -5636168);
      invoke(Theme.NEON_RAINBOW, ThemeAnimationMode.HUE_WHEEL, -8519833, -13371393, -4006, -49678, -8519833);
      invoke(Theme.BLOSSOM_RAINBOW, ThemeAnimationMode.MULTI_GRADIENT, -22584, -8049, -5777153, -2709505, -14116, -22584);
      invoke(Theme.ABYSS_RAINBOW, ThemeAnimationMode.TWIN_LAYERS, -8743937, -5215233, -10813482, -41074, -8022017, -8743937);
      invoke(Theme.SUNSET_RAINBOW, ThemeAnimationMode.MULTI_GRADIENT, -19622, -38070, -45175, -2781953, -18320, -19622);
      invoke(Theme.GLACIER_RAINBOW, ThemeAnimationMode.BREATHING, -2688001, -7607553, -7627265, -1, -4725249, -2688001);
      invoke(Theme.CHROMA_RAINBOW, ThemeAnimationMode.HUE_WHEEL, -41107, -7076, -10616904, -9597697, -41107);
      invoke(Theme.DREAM_RAINBOW, ThemeAnimationMode.MULTI_GRADIENT, -2775297, -24613, -9043994, -3956, -1722881, -2775297);
      invoke(Theme.TOXIC_RAINBOW, ThemeAnimationMode.HUE_WHEEL, -3604664, -3750, -13044993, -41759, -3604664);
      invoke(Theme.AURORA_BOREALIS, ThemeAnimationMode.MULTI_GRADIENT, -10747980, -9730561, -37947, -14997, -9699351, -10747980);
      invoke(Theme.TOKYO_NEON, ThemeAnimationMode.HUE_WHEEL, -57736, -12255278, -8691201, -74951, -57736);
      invoke(Theme.GALAXY, ThemeAnimationMode.MULTI_GRADIENT, -9740289, -5350401, -41035, -10498049, -7000, -9740289);
      invoke(Theme.LAVA, ThemeAnimationMode.TWIN_LAYERS, -21955, -38083, -49828, -3922881, -9877, -21955);
      invoke(Theme.FROST, ThemeAnimationMode.BREATHING, -5707521, -9710593, -11552257, -8410881, -4853505, -5707521);
      invoke(Theme.SAKURA, ThemeAnimationMode.MULTI_GRADIENT, -13860, -24372, -32843, -2055937, -10264, -13860);
      invoke(Theme.FOREST_MIST, ThemeAnimationMode.MULTI_GRADIENT, -9699390, -11541080, -13057392, -7340071, -10485842, -9699390);
      invoke(Theme.COSMIC_LATTE, ThemeAnimationMode.BREATHING, -6752, -669313, -1660831, -13126, -8011, -6752);
      invoke(Theme.SYNTHWAVE, ThemeAnimationMode.TWIN_LAYERS, -51019, -13056513, -297473, -21668, -8692737, -51019);
      invoke(Theme.HOLOGRAPHIC, ThemeAnimationMode.PRISMATIC_WAVE, -24321, -6225921, -96, -6226016, -3104513, -24321);
      invoke(Theme.MIDNIGHT_AZURE, ThemeAnimationMode.BREATHING, -16715521, -16733953, -16759553, -12292609, -2556929, -16715521);
      invoke(Theme.MIDNIGHT_OCEAN, ThemeAnimationMode.TWIN_LAYERS, -12612097, -13057793, -9740289, -8734721, -5252609, -12612097);
      invoke(Theme.MAGMA, ThemeAnimationMode.MULTI_GRADIENT, -22436, -37059, -49828, -3922881, -8776415, -22436);
      invoke(Theme.VERNAL_SOLSTICE, ThemeAnimationMode.BREATHING, -13447886, -10496, -6291605, -1441815, -4720701, -3416);
      invoke(Theme.OBSIDIAN_EMBER, ThemeAnimationMode.BREATHING, -42198, -25531, -14221);
      invoke(Theme.GLACIER_VEIL, ThemeAnimationMode.TWIN_LAYERS, -5706497, -11352065, -12681729, -8390688, -5706497);
      invoke(Theme.VELVET_DUSK, ThemeAnimationMode.MULTI_GRADIENT, -3563265, -8635667, -34106, -14221, -3563265);
      invoke(Theme.PORCELAIN_DAWN, ThemeAnimationMode.BREATHING, -20342, -30116, -13912);
      invoke(Theme.FRUTIGER_AERO, ThemeAnimationMode.MULTI_GRADIENT, -8657678, -13121888, -7346033, -13722666, -8657678);
   }
}
