package ru.metaculture.protection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.MinecraftClient;
import org.wild.module.api.Module;

public class LegacyClickGuiState {
   public static MinecraftClient client = MinecraftClient.getInstance();
   public static DirectionalAnimation directionalAnimation = new EaseInOutQuadAnimation(200, 1.0);
   public static DirectionalAnimation directionalAnimation2 = new EaseInOutQuadAnimation(500, 1.0);
   public static DirectionalAnimation directionalAnimation3 = new EaseInOutQuadAnimation(500, 1.0);
   public static DirectionalAnimation directionalAnimation4 = new EaseInOutQuadAnimation(500, 1.0);
   public static DirectionalAnimation directionalAnimation5 = new EaseInOutQuadAnimation(1000, 1.0);
   public static BooleanSetting blyurNada = new BooleanSetting("Блюр нада?", true);
   public static TimedAnimation timedAnimation = new TimedAnimation(AnimationMode.EASE_OUT_SINE, 1500L);
   public static FlexibleAnimation flexibleAnimation = new FlexibleAnimation();
   public static EasedAnimation easedAnimation = new EasedAnimation();
   public static EasedAnimation easedAnimation2 = new EasedAnimation();
   public static EasedAnimation easedAnimation3 = new EasedAnimation();
   public static EasedAnimation easedAnimation4 = new EasedAnimation();
   public static EasedAnimation easedAnimation5 = new EasedAnimation();
   public static ColorSetting colorSetting = null;
   public static float floatValue = 0.0F;
   public static float floatValue2 = 0.0F;
   public static boolean flag = false;
   public static boolean flag2 = false;
   public static boolean flag3 = false;
   public static KeybindSetting keybindSetting = null;
   public static TextSetting textSetting = null;
   public static NumberSetting numberSetting = null;
   public static Module module = null;
   public static float floatValue3 = 0.0F;
   public static float floatValue4 = 0.0F;
   public static float floatValue5 = 0.0F;
   public static String text = "";
   public static boolean flag4 = false;
   public static long timestamp = 0L;
   public static boolean flag5 = false;
   public static long timestamp2 = 0L;
   public static final int INT_VALUE = -200;
   public static final int INT_VALUE_2 = -201;
   public static boolean flag6 = false;
   public static float floatValue6;
   public static float floatValue7;
   public static float floatValue8;
   public static float floatValue9;
   public static int intValue = 0;
   public static int intValue2 = 0;
   public static Category[] categorys;
   public static Theme theme;
   public static Theme theme2;
   public static Theme[] themes;
   public static Category category;
   public static List<Module> items;
   private static TypeUtils typeUtils;
   public static Set<Module> values = new HashSet<>();
   public static Map<Module, EasedAnimation> valuesByKey = new HashMap<>();
   public static Map<Module, EasedAnimation> valuesByKey2 = new HashMap<>();
   public static Map<Module, EasedAnimation> valuesByKey3 = new HashMap<>();
   public static Map<NumberSetting, EasedAnimation> valuesByKey4 = new HashMap<>();

   public static TypeUtils resolve() {
      if (typeUtils == null) {
         typeUtils = new TypeUtils();
      }

      return typeUtils;
   }

   public static EasedAnimation resolve2(Module module) {
      return valuesByKey.computeIfAbsent(module, modulex -> new EasedAnimation());
   }

   public static EasedAnimation resolve3(Module module) {
      return valuesByKey2.computeIfAbsent(module, modulex -> new EasedAnimation());
   }

   public static EasedAnimation resolve4(Module module) {
      EasedAnimation easedAnimation = valuesByKey3.computeIfAbsent(module, modulex -> new EasedAnimation());
      if (module.bindKey != -1 && easedAnimation.getDoubleValue() == 0.0 && easedAnimation.getDoubleValue4() == 0.0) {
         easedAnimation.setDoubleValue4(1.0);
      }

      return easedAnimation;
   }

   public static EasedAnimation resolve5(NumberSetting numberSetting) {
      return valuesByKey4.computeIfAbsent(numberSetting, numberSetting2 -> {
         EasedAnimation easedAnimation2 = new EasedAnimation();
         float floatValue = (numberSetting.value - numberSetting.minimum) / (numberSetting.maximum - numberSetting.minimum);
         easedAnimation2.setDoubleValue4(floatValue);
         return easedAnimation2;
      });
   }
}
