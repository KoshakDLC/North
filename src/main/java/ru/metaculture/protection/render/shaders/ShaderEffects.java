package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.List;

public final class ShaderEffects {
   private ShaderEffects() {
   }

   public static boolean check(String string) {
      return ShaderPresetRegistry.getINSTANCE().check2(string);
   }

   public static boolean check2(ShaderSurface shaderSurface) {
      return ShaderPresetRegistry.getINSTANCE().check(shaderSurface);
   }

   public static boolean check3(String string, float f, float g, float h, float i, int j, int k, float l, float m, ColorScheme colorScheme, float n) {
      return ShaderUniformBinder.check2(string, f, g, h, i, j, k, l, m, colorScheme, n);
   }

   public static boolean check4(
      ShaderSurface shaderSurface2, float f, float g, float h, float i, int j, int k, float l, float m, ColorScheme colorScheme2, float n
   ) {
      return ShaderUniformBinder.check(shaderSurface2, f, g, h, i, j, k, l, m, colorScheme2, n);
   }

   public static boolean check5(String string, int i, float f, float g, float h, float j, int k, int l, float m, float n, ColorScheme colorScheme3, float o) {
      return ShaderUniformBinder.check3(string, i, f, g, h, j, k, l, m, n, colorScheme3, o);
   }

   public static String resolve(String string) {
      return ThemeShaderProgramCache.getINSTANCE().resolve5(string);
   }

   public static String resolve2(ShaderSurface shaderSurface3) {
      return ThemeShaderProgramCache.getINSTANCE().resolve4(shaderSurface3);
   }

   public static String resolve3(String string) {
      ShaderBuildResult shaderBuildResult = ShaderPresetRegistry.getINSTANCE().resolve2(string);
      return shaderBuildResult != null && shaderBuildResult.hash() != null ? shaderBuildResult.hash() : ThemeShaderProgramCache.getINSTANCE().resolve7(string);
   }

   public static String resolve4(ShaderSurface shaderSurface4) {
      ShaderBuildResult shaderBuildResult2 = ShaderPresetRegistry.getINSTANCE().resolve(shaderSurface4);
      return shaderBuildResult2 != null && shaderBuildResult2.hash() != null ? shaderBuildResult2.hash() : ThemeShaderProgramCache.getINSTANCE().resolve6(shaderSurface4);
   }

   public static GlShaderProgram resolve5(String string) {
      String text = ShaderPresetRegistry.resolve21(string);
      if (text.isBlank()) {
         return null;
      } else {
         ShaderBuildResult shaderBuildResult3 = ShaderPresetRegistry.getINSTANCE().resolve2(text);
         return shaderBuildResult3 == null ? null : ThemeShaderProgramCache.getINSTANCE().resolve3(text, shaderBuildResult3);
      }
   }

   public static GlShaderProgram resolve6(ShaderSurface shaderSurface5) {
      if (shaderSurface5 == null) {
         return null;
      } else {
         ShaderBuildResult shaderBuildResult4 = ShaderPresetRegistry.getINSTANCE().resolve(shaderSurface5);
         return shaderBuildResult4 == null ? null : ThemeShaderProgramCache.getINSTANCE().resolve2(shaderSurface5, shaderBuildResult4);
      }
   }

   public static List<ShaderUniformSpec> resolve7(String string) {
      return ShaderPresetRegistry.getINSTANCE().resolve11(string);
   }

   public static List<ShaderUniformSpec> resolve8(ShaderSurface shaderSurface6) {
      return ShaderPresetRegistry.getINSTANCE().resolve10(shaderSurface6);
   }

   public static List<Setting> resolve9(String string) {
      return resolve11(ShaderPresetRegistry.getINSTANCE().resolve11(string));
   }

   public static List<Setting> resolve10(ShaderSurface shaderSurface7) {
      return resolve11(ShaderPresetRegistry.getINSTANCE().resolve10(shaderSurface7));
   }

   public static void invoke(String string, String string2, float f) {
      ShaderPresetRegistry.getINSTANCE().invoke11(string, string2, f);
   }

   public static void invoke2(ShaderSurface shaderSurface8, String string, float f) {
      ShaderPresetRegistry.getINSTANCE().invoke10(shaderSurface8, string, f);
   }

   public static void invoke3(String string, String string2, int i) {
      ShaderPresetRegistry.getINSTANCE().invoke13(string, string2, i);
   }

   public static void invoke4(ShaderSurface shaderSurface9, String string, int i) {
      ShaderPresetRegistry.getINSTANCE().invoke12(shaderSurface9, string, i);
   }

   public static void invoke5(String string, List<Setting> list) {
      if (list != null) {
         for (Setting setting : list) {
            if (setting instanceof NumberSetting numberSetting) {
               invoke(string, numberSetting.name, numberSetting.value);
            } else if (setting instanceof ColorSetting colorSetting) {
               invoke3(string, colorSetting.name, colorSetting.compute2());
            }
         }
      }
   }

   public static void invoke6(ShaderSurface shaderSurface10, List<Setting> list) {
      if (list != null) {
         for (Setting setting2 : list) {
            if (setting2 instanceof NumberSetting numberSetting2) {
               invoke2(shaderSurface10, numberSetting2.name, numberSetting2.value);
            } else if (setting2 instanceof ColorSetting colorSetting2) {
               invoke4(shaderSurface10, colorSetting2.name, colorSetting2.compute2());
            }
         }
      }
   }

   public static void invoke7(String string) {
      ShaderPresetRegistry.getINSTANCE().invoke9(string);
      ThemeShaderProgramCache.getINSTANCE().invoke2(string);
   }

   public static void invoke8(ShaderSurface shaderSurface11) {
      ShaderPresetRegistry.getINSTANCE().invoke8(shaderSurface11);
      ThemeShaderProgramCache.getINSTANCE().invoke(shaderSurface11);
   }

   public static void invoke9() {
      ThemeShaderProgramCache.getINSTANCE().invoke3();
   }

   private static List<Setting> resolve11(List<ShaderUniformSpec> list) {
      ArrayList arrayList = new ArrayList();
      if (list == null) {
         return arrayList;
      } else {
         for (ShaderUniformSpec shaderUniformSpec : list) {
            if (shaderUniformSpec.kind() == ShaderUniformSpec.ShaderUniformSpecState.FLOAT) {
               arrayList.add(new NumberSetting(shaderUniformSpec.name(), shaderUniformSpec.defaultFloat(), shaderUniformSpec.minimum(), shaderUniformSpec.maximum(), shaderUniformSpec.increment(), false));
            } else {
               arrayList.add(new ColorSetting(shaderUniformSpec.name(), shaderUniformSpec.defaultRgba()));
            }
         }

         return arrayList;
      }
   }
}
