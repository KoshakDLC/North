package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import lombok.Generated;
import org.wild.module.api.Module;

public final class ClickGuiState {
   public static final int INT_VALUE = 96;
   static final SpringAnimation.MotionSpec MOTION_SPEC = new SpringAnimation.MotionSpec(8.8F, 2.35F, 0.0015F, 0.08F);
   static final SpringAnimation.MotionSpec MOTION_SPEC_2 = new SpringAnimation.MotionSpec(18.0F, 2.55F, 0.0015F, 0.08F);
   static final long TIMESTAMP = 1280L;
   static final long TIMESTAMP_2 = 420L;
   static final long TIMESTAMP_3 = 620L;
   private final Set<Module> values = new HashSet<>();
   private final Map<String, SpringAnimation> valuesByKey = new HashMap<>();
   private final SpringAnimation springAnimation = new SpringAnimation(0.0F);
   private final SpringAnimation springAnimation2 = new SpringAnimation(0.0F);
   private final SpringAnimation springAnimation3 = new SpringAnimation(0.0F);
   private final SpringAnimation springAnimation4 = new SpringAnimation(1.0F);
   private final Map<Integer, Long> valuesByKey2 = new HashMap<>();
   private Category category = Category.Combat;
   private boolean flag;
   private boolean flag2;
   private boolean flag3;
   private long timestamp;
   private Theme theme = Theme.WILD;
   private boolean flag4;
   private String text = "";
   private boolean flag5;
   private String text2 = "";
   private boolean flag6;
   private List<Integer> items = List.of();
   private String text3;
   private int intValue = -1;
   private boolean flag7;
   private float floatValue;
   private float floatValue2;
   private float floatValue3;
   private float floatValue4;
   private float floatValue5;
   private float floatValue6;
   private float floatValue7;
   private float floatValue8;
   private float floatValue9;
   private float floatValue10;
   private float floatValue11;
   private float floatValue12;
   private float floatValue13;
   private float floatValue14;
   private float floatValue15;
   private float floatValue16;
   private float floatValue17;
   private boolean flag8;
   private boolean flag9;
   private float floatValue18;
   private float floatValue19;
   private long timestamp2;
   private long timestamp3;
   private long timestamp4;
   private long timestamp5;
   private long timestamp6;
   private boolean flag10;
   private boolean flag11;
   private boolean flag12;
   private boolean flag13;
   private boolean flag14;
   private float floatValue20;
   private float floatValue21;
   private float floatValue22 = 1.0F;
   private float floatValue23;
   private float floatValue24;
   private boolean flag15;
   private boolean flag16;
   private float floatValue25;
   private float floatValue26;
   private float floatValue27 = 1.0F;
   private float floatValue28;
   private float floatValue29;
   private boolean flag17;
   private NumberSetting numberSetting;
   private ColorSetting colorSetting;
   private float floatValue30;
   private float floatValue31;
   private Module module;
   private KeybindSetting keybindSetting;
   private BooleanSetting booleanSetting;
   private TextSetting textSetting;
   private ModeSetting modeSetting;
   private FoundryShaderSetting foundryShaderSetting;
   private int intValue2 = -1;
   private ColorScheme colorScheme;
   private ColorScheme colorScheme2;
   private Theme theme2 = Theme.WILD;
   private float floatValue32;
   private float floatValue33;
   private long timestamp7;
   private long timestamp8;
   private ColorSetting colorSetting2;
   private boolean flag18;
   private boolean flag19;
   private boolean flag20;
   private float floatValue34;
   private float floatValue35;
   private float floatValue36;
   private float floatValue37;
   private float floatValue38;
   private float floatValue39;
   private float floatValue40;
   private float floatValue41;
   private float floatValue42;
   private float floatValue43;
   private float floatValue44;
   private float floatValue45;
   private float floatValue46;
   private float floatValue47;
   private float floatValue48;
   private float floatValue49;
   private float floatValue50;
   private float floatValue51;
   private float floatValue52;
   private float floatValue53;
   private float floatValue54;
   private float floatValue55;
   private float floatValue56;
   private float floatValue57;
   private float floatValue58;
   private float floatValue59;
   private float floatValue60;
   private float DynamicButtonSetting;
   private float floatValue61;
   private float floatValue62;
   private float floatValue63;
   private float floatValue64;
   private float SpacerSetting;
   private float FoundryShaderSetting;
   private final Map<ColorSetting, Integer> valuesByKey3 = new HashMap<>();
   private ColorSetting colorSetting3;
   private String text4 = "";
   private ColorSetting colorSetting4;
   private String text5 = "";
   private boolean flag21;
   private boolean flag22;
   private boolean flag23;
   private boolean flag24;
   private final SpringAnimation springAnimation5 = new SpringAnimation(1.0F);
   private boolean flag25;
   private boolean flag26 = true;
   private int intValue3 = -1;
   private int intValue4 = -1;
   private float floatValue65;
   private float floatValue66;
   private float floatValue67;
   private float floatValue68;
   private float floatValue69;
   private float floatValue70;
   private float floatValue71;
   private float floatValue72;
   private float floatValue73;
   private float floatValue74;
   private float floatValue75;
   private float floatValue76;
   private float floatValue77 = 1.0F;
   private String text6;
   private float floatValue78;
   private float floatValue79;
   private long timestamp9;
   private String text7;
   private static final char[] CHARS = new char[65535];

   public void invoke() {
      this.invoke70();
      if (WildClient.INSTANCE != null && WildClient.INSTANCE.themeManager != null) {
         this.category = WildClient.INSTANCE.themeManager.getCategory();
         this.theme = WildClient.INSTANCE.themeManager.getTheme();
         this.theme2 = this.theme;
         if (WildClient.INSTANCE.themeManager.isFlag()) {
            this.floatValue5 = WildClient.INSTANCE.themeManager.getFloatValue();
            this.floatValue6 = WildClient.INSTANCE.themeManager.getFloatValue2();
            this.flag11 = true;
         }

         this.flag22 = WildClient.INSTANCE.themeManager.isFlag2() && WildClient.INSTANCE.themeManager.isFlag3();
      }

      this.intValue2 = -1;
      this.invoke74();
      this.invoke51();
   }

   public void invoke2() {
      this.flag7 = false;
      this.springAnimation.invoke(0.0F);
      this.timestamp2 = System.currentTimeMillis();
      this.timestamp4 = this.timestamp2;
   }

   public void invoke3() {
      this.flag7 = true;
      this.flag5 = false;
      this.flag6 = false;
      this.invoke51();
      this.timestamp3 = System.currentTimeMillis();
   }

   public float measure(SpringSpec springSpec) {
      return this.springAnimation.measure(this.flag7 ? 0.0F : 1.0F, springSpec);
   }

   public float measure2() {
      return this.springAnimation.getFloatValue();
   }

   public boolean check(SpringSpec springSpec2) {
      return this.flag7 && this.springAnimation.check(0.0F, springSpec2);
   }

   public List<Module> resolve() {
      this.invoke70();
      if (this.flag || this.flag2) {
         return List.of();
      } else if (WildClient.INSTANCE == null || WildClient.INSTANCE.moduleManager == null) {
         return List.of();
      } else {
         return this.text != null && !this.text.isBlank()
            ? WildClient.INSTANCE.moduleManager.getModules().stream().filter(module -> !(module instanceof AutoBuy)).toList()
            : WildClient.INSTANCE.moduleManager.getModules(this.category).stream().filter(module -> !(module instanceof AutoBuy)).toList();
      }
   }

   public List<Module> resolve2() {
      this.invoke70();
      if (this.flag || this.flag2) {
         return List.of();
      } else if (WildClient.INSTANCE == null || WildClient.INSTANCE.moduleManager == null) {
         return List.of();
      } else if (this.text != null && !this.text.isBlank()) {
         String text = this.text.toLowerCase(Locale.ROOT).trim();
         return WildClient.INSTANCE
            .moduleManager
            .getModules()
            .stream()
            .filter(module -> !(module instanceof AutoBuy))
            .filter(module -> this.check9(module, text))
            .toList();
      } else {
         return WildClient.INSTANCE.moduleManager.getModules(this.category).stream().filter(module -> !(module instanceof AutoBuy)).toList();
      }
   }

   public void invoke4(Category category) {
      this.flag21 = false;
      this.flag = false;
      this.flag2 = false;
      this.flag3 = false;
      this.category = category;
      this.invoke72();
      this.invoke21();
      this.flag5 = false;
      this.invoke75((List<Integer>)null);
      this.numberSetting = null;
      this.colorSetting = null;
      this.timestamp4 = System.currentTimeMillis();
      this.invoke33();
      this.invoke36();
      this.invoke38();
      this.invoke11();
      if (WildClient.INSTANCE != null && WildClient.INSTANCE.themeManager != null) {
         WildClient.INSTANCE.themeManager.invoke3(category);
      }
   }

   public void invoke5() {
      this.flag21 = false;
      this.flag = true;
      this.flag2 = false;
      this.flag3 = false;
      this.invoke72();
      this.invoke21();
      this.flag5 = false;
      this.invoke75((List<Integer>)null);
      this.numberSetting = null;
      this.colorSetting = null;
      this.timestamp4 = System.currentTimeMillis();
      this.timestamp5 = this.timestamp4;
      this.invoke33();
      this.invoke36();
      this.invoke38();
      this.invoke11();
   }

   public void invoke6() {
      this.flag21 = false;
      this.flag = false;
      this.flag2 = true;
      this.flag3 = false;
      this.timestamp++;
      this.invoke72();
      this.invoke21();
      this.flag5 = false;
      this.invoke75((List<Integer>)null);
      this.numberSetting = null;
      this.colorSetting = null;
      this.timestamp4 = System.currentTimeMillis();
      this.timestamp6 = this.timestamp4;
      this.invoke33();
      this.invoke36();
      this.invoke38();
      this.invoke11();
      this.invoke10();
   }

   public void invoke7() {
      if (this.flag2) {
         this.flag2 = false;
         this.invoke21();
         this.timestamp4 = System.currentTimeMillis();
      } else {
         this.invoke6();
      }
   }

   public void invoke8() {
      if (StudioAccessControl.check()) {
         this.flag21 = false;
         this.flag = false;
         this.flag2 = false;
         this.flag3 = true;
         this.invoke72();
         this.invoke21();
         this.flag5 = false;
         this.invoke75((List<Integer>)null);
         this.numberSetting = null;
         this.colorSetting = null;
         this.timestamp4 = System.currentTimeMillis();
         this.invoke33();
         this.invoke36();
         this.invoke38();
         this.invoke11();
         StudioLibrary.resolve().invoke();
      }
   }

   public void invoke9() {
      if (this.flag3) {
         this.flag3 = false;
         this.invoke4(this.category);
      } else {
         this.invoke8();
      }
   }

   private void invoke10() {
      SpringAnimation springAnimation = this.valuesByKey.get(AnimationKeyRegistry.resolve7());
      if (springAnimation == null) {
         this.valuesByKey.put(AnimationKeyRegistry.resolve7(), new SpringAnimation(0.0F));
      } else {
         springAnimation.invoke(0.0F);
      }

      SpringAnimation springAnimation2 = this.valuesByKey.get(AnimationKeyRegistry.resolve8());
      if (springAnimation2 != null) {
         springAnimation2.invoke(0.0F);
      }
   }

   public void invoke11() {
      this.valuesByKey.entrySet().removeIf(entry -> {
         String text2 = entry.getKey();
         return text2.startsWith("module:card:transition:") || text2.startsWith("module:card:entry:") || text2.startsWith("module:svis:");
      });
   }

   public void invoke12(int i, float f, float g, float h, float j) {
      if (this.flag25 && this.intValue3 >= 0) {
         if (this.intValue3 != i) {
            this.intValue4 = this.intValue3;
            this.floatValue69 = this.floatValue65;
            this.floatValue70 = this.floatValue66;
            this.floatValue71 = this.floatValue67;
            this.floatValue72 = this.floatValue68;
            this.intValue3 = i;
            this.floatValue77 = 0.0F;
            this.springAnimation5.invoke(0.0F);
         }

         this.floatValue73 = f;
         this.floatValue74 = g;
         this.floatValue75 = h;
         this.floatValue76 = j;
         this.floatValue77 = this.springAnimation5.measure2(1.0F, MOTION_SPEC);
         if (this.floatValue77 >= 0.998F) {
            this.floatValue77 = 1.0F;
            this.springAnimation5.invoke(1.0F);
            this.intValue4 = i;
            this.floatValue69 = f;
            this.floatValue70 = g;
            this.floatValue71 = h;
            this.floatValue72 = j;
         }

         float floatValue = this.measure12(this.floatValue77);
         this.floatValue65 = this.measure13(this.floatValue69, f, floatValue);
         this.floatValue66 = this.measure13(this.floatValue70, g, floatValue);
         this.floatValue67 = this.measure13(this.floatValue71, h, floatValue);
         this.floatValue68 = this.measure13(this.floatValue72, j, floatValue);
      } else {
         this.intValue3 = i;
         this.intValue4 = i;
         this.floatValue65 = f;
         this.floatValue66 = g;
         this.floatValue67 = h;
         this.floatValue68 = j;
         this.floatValue69 = f;
         this.floatValue70 = g;
         this.floatValue71 = h;
         this.floatValue72 = j;
         this.floatValue73 = f;
         this.floatValue74 = g;
         this.floatValue75 = h;
         this.floatValue76 = j;
         this.floatValue77 = 1.0F;
         this.springAnimation5.invoke(1.0F);
         this.flag25 = true;
      }
   }

   public void invoke13(Theme theme, int i) {
      if (this.theme != theme) {
         this.theme2 = this.theme;
         if (MenuModule.check(MenuModule.VOLNY_TEMY)) {
            this.floatValue32 = this.floatValue;
            this.floatValue33 = this.floatValue2;
            this.timestamp7 = System.currentTimeMillis();
         }

         if (MenuModule.check(MenuModule.UDARNAYA_VOLNA_TEMY)) {
            ColorScheme colorScheme = ColorScheme.resolve(theme);
            ScreenTransitionController.getINSTANCE().invoke(this.floatValue, this.floatValue2, colorScheme.getIntValue14(), colorScheme.getIntValue15());
         }
      }

      this.theme = theme;
      this.intValue2 = i;
      if (WildClient.INSTANCE != null && WildClient.INSTANCE.themeManager != null) {
         WildClient.INSTANCE.themeManager.invoke2(theme);
      }
   }

   public void setFloatValue13(float f) {
      this.floatValue13 = Math.max(0.0F, f);
      if (this.floatValue13 <= 0.001F) {
         this.floatValue12 = 0.0F;
         this.floatValue11 = 0.0F;
         this.springAnimation2.invoke(0.0F);
      }
   }

   public void setFloatValue19(float f) {
      this.floatValue19 = Math.max(0.0F, f);
      if (this.floatValue19 <= 0.001F) {
         this.floatValue18 = 0.0F;
         this.springAnimation3.invoke(0.0F);
      }
   }

   public void invoke14(float f, Metrics metrics) {
      float floatValue2 = this.measure15(metrics, this.floatValue19);
      this.floatValue18 = this.measure16(this.floatValue18, f, -this.floatValue19, 0.0F, floatValue2);
   }

   public void invoke15(float f) {
      if (!(this.floatValue13 <= 0.001F)) {
         this.floatValue12 = -this.floatValue13 * this.measure14(f, 0.0F, 1.0F);
      }
   }

   public void invoke16(float f) {
      if (!(this.floatValue19 <= 0.001F)) {
         this.floatValue18 = -this.floatValue19 * this.measure14(f, 0.0F, 1.0F);
      }
   }

   public void invoke17(float f, float g) {
      this.floatValue16 = Math.max(0.0F, f);
      this.floatValue17 = Math.max(0.0F, g);
      this.floatValue14 = this.floatValue16 <= 0.001F ? 0.0F : this.measure14(this.floatValue14, 0.0F, this.floatValue16);
      this.floatValue15 = this.floatValue17 <= 0.001F ? 0.0F : this.measure14(this.floatValue15, 0.0F, this.floatValue17);
   }

   public void invoke18(float f, float g) {
      this.floatValue14 = this.floatValue16 <= 0.001F ? 0.0F : this.measure14(this.floatValue14 - f, 0.0F, this.floatValue16);
      this.floatValue15 = this.floatValue17 <= 0.001F ? 0.0F : this.measure14(this.floatValue15 - g, 0.0F, this.floatValue17);
   }

   public void setFloatValue15(float f) {
      this.floatValue15 = this.floatValue17 <= 0.001F
         ? 0.0F
         : this.measure14(this.floatValue17 * this.measure14(f, 0.0F, 1.0F), 0.0F, this.floatValue17);
   }

   public void setFloatValue14(float f) {
      this.floatValue14 = this.floatValue16 <= 0.001F
         ? 0.0F
         : this.measure14(this.floatValue16 * this.measure14(f, 0.0F, 1.0F), 0.0F, this.floatValue16);
   }

   public void invoke19() {
      this.flag8 = true;
      this.flag9 = false;
   }

   public void invoke20() {
      this.flag9 = true;
      this.flag8 = false;
   }

   public boolean check2() {
      boolean flag = this.flag8 || this.flag9;
      this.flag8 = false;
      this.flag9 = false;
      return flag;
   }

   public void invoke21() {
      this.floatValue14 = 0.0F;
      this.floatValue15 = 0.0F;
      this.floatValue16 = 0.0F;
      this.floatValue17 = 0.0F;
      this.flag8 = false;
      this.flag9 = false;
   }

   public float measure3(SpringSpec springSpec3, Metrics metrics2) {
      float floatValue3 = this.measure15(metrics2, this.floatValue19);
      this.floatValue18 = this.measure17(this.floatValue18, -this.floatValue19, 0.0F, floatValue3);
      float floatValue4 = this.springAnimation3.measure2(this.floatValue18, SpringAnimation.MotionSpec.resolve());
      return this.measure18(this.springAnimation3, floatValue4, -this.floatValue19, 0.0F, floatValue3);
   }

   public float measure4() {
      return this.springAnimation3.getFloatValue();
   }

   public void invoke22() {
      if (!this.text.isEmpty() || this.flag4) {
         this.text = "";
         this.flag4 = false;
         this.flag5 = false;
         this.invoke72();
         this.timestamp4 = System.currentTimeMillis();
      }
   }

   public void invoke23(char c) {
      if (this.flag4) {
         this.text = "";
         this.flag4 = false;
      }

      if (this.text.length() < 96) {
         this.text = this.text + c;
         this.invoke72();
         this.timestamp4 = System.currentTimeMillis();
      }
   }

   public void invoke24() {
      if (this.flag4) {
         this.text = "";
         this.flag4 = false;
         this.invoke72();
         this.timestamp4 = System.currentTimeMillis();
      } else if (!this.text.isEmpty()) {
         this.text = this.text.substring(0, this.text.length() - 1);
         this.invoke72();
         this.timestamp4 = System.currentTimeMillis();
      }
   }

   public void invoke25() {
      this.flag6 = true;
      this.flag5 = false;
      this.invoke75((List<Integer>)null);
   }

   public void invoke26() {
      if (!this.text2.isEmpty()) {
         this.text2 = "";
         this.flag6 = false;
         this.invoke73();
      }
   }

   public void invoke27(char c) {
      if (this.text2.length() < 96) {
         this.text2 = this.text2 + c;
         this.invoke73();
      }
   }

   public void invoke28() {
      if (!this.text2.isEmpty()) {
         this.text2 = this.text2.substring(0, this.text2.length() - 1);
         this.invoke73();
      }
   }

   public List<Integer> resolve3(ThemePalette themePalette) {
      List items = themePalette == null ? List.of() : themePalette.getItems();
      if (items == null) {
         items = List.of();
      }

      String text3 = this.text2 == null ? "" : this.text2;
      if (text3.equals(this.text3) && this.intValue == items.size() && this.items != null) {
         return this.items;
      } else {
         ArrayList arrayList = new ArrayList(items.size());
         if (text3.isBlank()) {
            for (int intValue = 0; intValue < items.size(); intValue++) {
               arrayList.add(intValue);
            }
         } else {
            String text4 = text3.toLowerCase(Locale.ROOT).trim();
            String text5 = this.resolve5(text4);

            for (int intValue2 = 0; intValue2 < items.size(); intValue2++) {
               String text6 = ((ThemePalette.Swatch)items.get(intValue2)).getText().toLowerCase(Locale.ROOT);
               if (text6.contains(text4) || !text5.equals(text4) && text6.contains(text5)) {
                  arrayList.add(intValue2);
               }
            }
         }

         this.text3 = text3;
         this.intValue = items.size();
         this.items = arrayList;
         return arrayList;
      }
   }

   public void invoke29(Module module) {
      if (this.values.contains(module)) {
         this.values.remove(module);
      } else if (SpecialModuleCardHandlers.check(module) || !module.getVisibleSettings().isEmpty()) {
         this.values.add(module);
         this.valuesByKey2.put(System.identityHashCode(module), System.currentTimeMillis());
      }
   }

   public void invoke30() {
      this.flag22 = false;
      this.flag13 = false;
      this.flag6 = false;
   }

   public void setFlag22(boolean bl) {
      this.flag22 = bl;
      this.flag6 = false;
   }

   public void invoke31(ModeSetting modeSetting) {
      if (this.modeSetting == modeSetting) {
         this.modeSetting = null;
         modeSetting.expanded = false;
      } else {
         this.invoke36();
         if (this.modeSetting != null) {
            this.modeSetting.expanded = false;
         }

         this.modeSetting = modeSetting;
         modeSetting.expanded = true;
      }
   }

   public void invoke32(ModeSetting modeSetting2, int i) {
      if (i >= 0 && i < modeSetting2.options.size()) {
         modeSetting2.selectedIndex = i;
         modeSetting2.value = modeSetting2.options.get(i);
      }

      modeSetting2.expanded = false;
      if (this.modeSetting == modeSetting2) {
         this.modeSetting = null;
      }

      this.invoke66();
   }

   public void invoke33() {
      if (this.modeSetting != null) {
         this.modeSetting.expanded = false;
         this.modeSetting = null;
      }
   }

   public void invoke34(FoundryShaderSetting foundryShaderSetting) {
      if (foundryShaderSetting == null) {
         this.invoke36();
      } else {
         foundryShaderSetting.refreshOptions();
         if (this.foundryShaderSetting == foundryShaderSetting) {
            this.foundryShaderSetting = null;
            foundryShaderSetting.expanded = false;
         } else {
            this.invoke33();
            if (this.foundryShaderSetting != null) {
               this.foundryShaderSetting.expanded = false;
            }

            this.foundryShaderSetting = foundryShaderSetting;
            foundryShaderSetting.expanded = true;
         }
      }
   }

   public void invoke35(FoundryShaderSetting foundryShaderSetting2, int i) {
      if (foundryShaderSetting2 != null) {
         foundryShaderSetting2.invoke3(i);
         foundryShaderSetting2.expanded = false;
      }

      if (this.foundryShaderSetting == foundryShaderSetting2) {
         this.foundryShaderSetting = null;
      }

      this.invoke66();
   }

   public void invoke36() {
      if (this.foundryShaderSetting != null) {
         this.foundryShaderSetting.expanded = false;
         this.foundryShaderSetting = null;
      }
   }

   public void invoke37(ColorSetting colorSetting) {
      if (this.colorSetting2 == colorSetting) {
         this.colorSetting2 = null;
         this.valuesByKey3.remove(colorSetting);
         this.invoke40();
      } else {
         this.invoke40();
         this.colorSetting2 = colorSetting;
         if (colorSetting != null) {
            this.valuesByKey3.put(colorSetting, colorSetting.compute2());
         }
      }

      this.invoke39();
      this.flag18 = false;
      this.flag19 = false;
      this.flag20 = false;
   }

   public void invoke38() {
      if (this.colorSetting2 != null) {
         this.valuesByKey3.remove(this.colorSetting2);
      }

      this.colorSetting2 = null;
      this.invoke40();
      this.invoke39();
      this.flag18 = false;
      this.flag19 = false;
      this.flag20 = false;
   }

   public int compute(ColorSetting colorSetting2) {
      if (colorSetting2 == null) {
         return 0;
      } else {
         Integer integerValue = this.valuesByKey3.get(colorSetting2);
         return integerValue != null ? integerValue : colorSetting2.compute2();
      }
   }

   public void invoke39() {
      this.colorSetting3 = null;
      this.text4 = "";
      this.colorSetting4 = null;
      this.text5 = "";
   }

   private void invoke40() {
      this.floatValue34 = 0.0F;
      this.floatValue35 = 0.0F;
      this.floatValue36 = 0.0F;
      this.floatValue37 = 0.0F;
      this.floatValue38 = 0.0F;
      this.floatValue39 = 0.0F;
      this.floatValue40 = 0.0F;
      this.floatValue41 = 0.0F;
      this.floatValue42 = 0.0F;
      this.floatValue43 = 0.0F;
      this.floatValue44 = 0.0F;
      this.floatValue45 = 0.0F;
      this.floatValue46 = 0.0F;
      this.floatValue47 = 0.0F;
      this.floatValue48 = 0.0F;
      this.floatValue49 = 0.0F;
      this.floatValue50 = 0.0F;
      this.floatValue51 = 0.0F;
      this.floatValue52 = 0.0F;
      this.floatValue53 = 0.0F;
      this.floatValue54 = 0.0F;
      this.floatValue55 = 0.0F;
      this.floatValue56 = 0.0F;
      this.floatValue57 = 0.0F;
      this.floatValue58 = 0.0F;
      this.floatValue59 = 0.0F;
      this.floatValue60 = 0.0F;
      this.DynamicButtonSetting = 0.0F;
      this.floatValue61 = 0.0F;
      this.floatValue62 = 0.0F;
      this.floatValue63 = 0.0F;
      this.floatValue64 = 0.0F;
      this.SpacerSetting = 0.0F;
      this.FoundryShaderSetting = 0.0F;
   }

   public void invoke41() {
      this.flag21 = !this.flag21;
   }

   public void invoke42(String string, String string2, float f, float g) {
      if (string == null || !string.equals(this.text7)) {
         this.text7 = string;
         this.text6 = string2;
         this.floatValue78 = f;
         this.floatValue79 = g;
         this.timestamp9 = System.currentTimeMillis();
      }
   }

   public void invoke43() {
      this.text7 = null;
      this.text6 = null;
      this.timestamp9 = 0L;
   }

   public boolean check3() {
      return this.text7 != null && this.text6 != null && System.currentTimeMillis() - this.timestamp9 > 220L;
   }

   public void invoke44(Module module) {
      this.invoke71();
      this.module = module;
      module.expanded = true;
   }

   public void invoke45(KeybindSetting keybindSetting) {
      this.invoke71();
      this.keybindSetting = keybindSetting;
      keybindSetting.waitingForBind = true;
   }

   public void invoke46(BooleanSetting booleanSetting) {
      this.invoke71();
      this.booleanSetting = booleanSetting;
      booleanSetting.waitingForBind = true;
   }

   public void invoke47(int i) {
      if (this.module != null) {
         this.module.bindKey = this.check11(i) ? -1 : i;
         this.module.expanded = false;
         this.module = null;
         this.invoke66();
      }
   }

   public void invoke48(int i) {
      if (this.keybindSetting != null) {
         this.keybindSetting.keyCode = this.check11(i) ? -1 : i;
         this.keybindSetting.waitingForBind = false;
         this.keybindSetting = null;
         this.invoke66();
      }
   }

   public void invoke49(int i) {
      if (this.booleanSetting != null) {
         this.booleanSetting.bindKey = this.check11(i) ? -1 : i;
         this.booleanSetting.waitingForBind = false;
         this.booleanSetting = null;
         this.invoke66();
      }
   }

   public void invoke50(int i) {
      int intValue3 = -100 - i;
      if (this.module != null) {
         this.module.bindKey = intValue3;
         this.module.expanded = false;
         this.module = null;
         this.invoke66();
      } else if (this.keybindSetting != null) {
         this.keybindSetting.keyCode = intValue3;
         this.keybindSetting.waitingForBind = false;
         this.keybindSetting = null;
         this.invoke66();
      } else if (this.booleanSetting != null) {
         this.booleanSetting.bindKey = intValue3;
         this.booleanSetting.waitingForBind = false;
         this.booleanSetting = null;
         this.invoke66();
      }
   }

   public boolean check4() {
      return this.module != null || this.keybindSetting != null || this.booleanSetting != null;
   }

   public void invoke51() {
      this.invoke71();
      this.invoke75((List<Integer>)null);
      this.invoke33();
      this.invoke36();
      this.invoke38();
      this.numberSetting = null;
      this.colorSetting = null;
      this.flag12 = false;
      this.flag13 = false;
      this.flag14 = false;
      this.flag16 = false;
      this.flag21 = false;
   }

   public void invoke52(TextSetting textSetting) {
      if (this.textSetting != null) {
         this.textSetting.flag = false;
      }

      this.textSetting = textSetting;
      if (this.textSetting != null) {
         this.textSetting.flag = true;
      }
   }

   public void invoke53(SpringSpec springSpec4, Metrics metrics3) {
      float floatValue5 = this.measure15(metrics3, this.floatValue13);
      this.floatValue12 = this.measure17(this.floatValue12, -this.floatValue13, 0.0F, floatValue5);
      float floatValue6 = this.springAnimation2.measure2(this.floatValue12, SpringAnimation.MotionSpec.resolve());
      this.floatValue11 = this.measure18(this.springAnimation2, floatValue6, -this.floatValue13, 0.0F, floatValue5);
   }

   public void invoke54(Metrics metrics4, float f, float g) {
      if (!(f <= 0.0F) && !(g <= 0.0F)) {
         float floatValue7 = metrics4.measure(8.0F);
         if (!this.flag10) {
            this.floatValue3 = Math.max(floatValue7, (f - metrics4.getFloatValue3()) * 0.5F);
            this.floatValue4 = Math.max(floatValue7, (g - metrics4.getFloatValue4()) * 0.5F);
            this.flag10 = true;
         }

         this.floatValue3 = this.measure14(this.floatValue3, floatValue7, Math.max(floatValue7, f - metrics4.getFloatValue3() - floatValue7));
         this.floatValue4 = this.measure14(this.floatValue4, floatValue7, Math.max(floatValue7, g - metrics4.getFloatValue4() - floatValue7));
      }
   }

   public void invoke55(Metrics metrics5, float f, float g) {
      if (!(f <= 0.0F) && !(g <= 0.0F)) {
         float floatValue8 = metrics5.measure(8.0F);
         if (!this.flag11) {
            float floatValue9 = metrics5.measure(12.0F);
            float floatValue10 = this.floatValue4 - metrics5.getFloatValue19() - floatValue9;
            if (floatValue10 < floatValue8) {
               floatValue10 = this.floatValue4 + metrics5.getFloatValue4() + floatValue9;
            }

            if (floatValue10 + metrics5.getFloatValue19() > g - floatValue8) {
               floatValue10 = Math.max(floatValue8, g - metrics5.getFloatValue19() - floatValue8);
            }

            float floatValue11 = this.floatValue3;
            floatValue11 = Math.max(floatValue8, Math.min(floatValue11, f - metrics5.getFloatValue18() - floatValue8));
            this.floatValue5 = floatValue11;
            this.floatValue6 = floatValue10;
            this.flag11 = true;
         }

         this.floatValue5 = this.measure14(this.floatValue5, floatValue8, Math.max(floatValue8, f - metrics5.getFloatValue18() - floatValue8));
         this.floatValue6 = this.measure14(this.floatValue6, floatValue8, Math.max(floatValue8, g - metrics5.getFloatValue19() - floatValue8));
      }
   }

   public void invoke56(float f, float g, ClickGuiGeometry clickGuiGeometry) {
      this.flag12 = true;
      this.flag13 = false;
      this.floatValue7 = f - clickGuiGeometry.getFloatValue();
      this.floatValue8 = g - clickGuiGeometry.getFloatValue2();
      this.floatValue3 = clickGuiGeometry.getFloatValue();
      this.floatValue4 = clickGuiGeometry.getFloatValue2();
      this.flag10 = true;
      this.flag5 = false;
      this.flag6 = false;
      this.invoke75((List<Integer>)null);
   }

   public void invoke57(float f, float g) {
      if (this.flag12) {
         this.floatValue3 = f - this.floatValue7;
         this.floatValue4 = g - this.floatValue8;
         this.flag10 = true;
      }
   }

   public void invoke58(float f, float g, ClickGuiGeometry clickGuiGeometry2) {
      this.flag13 = true;
      this.flag12 = false;
      this.floatValue9 = f - clickGuiGeometry2.getFloatValue22();
      this.floatValue10 = g - clickGuiGeometry2.getFloatValue23();
      this.floatValue5 = clickGuiGeometry2.getFloatValue22();
      this.floatValue6 = clickGuiGeometry2.getFloatValue23();
      this.flag11 = true;
      this.flag5 = false;
      this.flag6 = false;
      this.invoke75((List<Integer>)null);
   }

   public boolean check5() {
      boolean flag2 = this.flag12;
      this.flag12 = false;
      return flag2;
   }

   public void invoke59(float f, float g, ClickGuiGeometry clickGuiGeometry3, Metrics metrics6) {
      this.flag14 = true;
      this.flag12 = false;
      this.flag13 = false;
      this.floatValue20 = f;
      this.floatValue21 = g;
      this.floatValue23 = metrics6 == null ? 1.0F : Math.max(1.0F, metrics6.getFloatValue3());
      this.floatValue24 = metrics6 == null ? 1.0F : Math.max(1.0F, metrics6.getFloatValue4());

      try {
         this.floatValue22 = MenuModule.MASSHTAB_GUI == null ? 1.0F : MenuModule.MASSHTAB_GUI.getValue();
      } catch (Throwable exception) {
         this.floatValue22 = 1.0F;
      }

      this.flag5 = false;
      this.flag6 = false;
      this.invoke75((List<Integer>)null);
      if (clickGuiGeometry3 != null) {
         this.floatValue3 = clickGuiGeometry3.getFloatValue();
         this.floatValue4 = clickGuiGeometry3.getFloatValue2();
         this.flag10 = true;
      }
   }

   public void invoke60(float f, float g) {
      if (this.flag14 && MenuModule.MASSHTAB_GUI != null) {
         float floatValue12 = (f - this.floatValue20) / Math.max(1.0F, this.floatValue23);
         float floatValue13 = (g - this.floatValue21) / Math.max(1.0F, this.floatValue24);
         float floatValue14 = Math.abs(floatValue12) >= Math.abs(floatValue13) ? floatValue12 : floatValue13;
         float floatValue15 = this.floatValue22 * (1.0F + floatValue14);
         floatValue15 = Math.max(0.72F, Math.min(1.7F, floatValue15));

         try {
            MenuModule.MASSHTAB_GUI.invoke(floatValue15);
         } catch (Throwable exception2) {
         }
      }
   }

   public boolean check6() {
      boolean flag3 = this.flag14;
      this.flag14 = false;
      if (flag3) {
         this.invoke66();
      }

      return flag3;
   }

   public void setFlag15(boolean bl) {
      this.flag15 = bl;
   }

   public void invoke61(float f, float g, ClickGuiGeometry clickGuiGeometry4, Metrics metrics7) {
      this.flag16 = true;
      this.flag12 = false;
      this.flag13 = false;
      this.flag14 = false;
      this.floatValue25 = f;
      this.floatValue26 = g;
      this.floatValue28 = metrics7 == null ? 1.0F : Math.max(1.0F, metrics7.getFloatValue18());
      this.floatValue29 = metrics7 == null ? 1.0F : Math.max(1.0F, metrics7.getFloatValue19());

      try {
         this.floatValue27 = MenuModule.MASSHTAB_PANELI_TEMY == null ? 1.0F : MenuModule.MASSHTAB_PANELI_TEMY.getValue();
      } catch (Throwable exception3) {
         this.floatValue27 = 1.0F;
      }

      this.flag5 = false;
      this.flag6 = false;
      this.invoke75((List<Integer>)null);
      if (clickGuiGeometry4 != null) {
         this.floatValue5 = clickGuiGeometry4.getFloatValue22();
         this.floatValue6 = clickGuiGeometry4.getFloatValue23();
         this.flag11 = true;
      }
   }

   public void invoke62(float f, float g) {
      if (this.flag16 && MenuModule.MASSHTAB_PANELI_TEMY != null) {
         float floatValue16 = (f - this.floatValue25) / Math.max(1.0F, this.floatValue28);
         float floatValue17 = (g - this.floatValue26) / Math.max(1.0F, this.floatValue29);
         float floatValue18 = Math.abs(floatValue16) >= Math.abs(floatValue17) ? floatValue16 : floatValue17;
         float floatValue19 = this.floatValue27 * (1.0F + floatValue18);
         floatValue19 = Math.max(0.72F, Math.min(1.7F, floatValue19));

         try {
            MenuModule.MASSHTAB_PANELI_TEMY.invoke(floatValue19);
         } catch (Throwable exception4) {
         }
      }
   }

   public boolean check7() {
      boolean flag4 = this.flag16;
      this.flag16 = false;
      if (flag4) {
         this.invoke66();
      }

      return flag4;
   }

   public void setFlag17(boolean bl) {
      this.flag17 = bl;
   }

   public void invoke63(float f, float g) {
      if (this.flag13) {
         this.floatValue5 = f - this.floatValue9;
         this.floatValue6 = g - this.floatValue10;
         this.flag11 = true;
      }
   }

   public boolean check8() {
      boolean flag5 = this.flag13;
      this.flag13 = false;
      if (flag5 && WildClient.INSTANCE != null && WildClient.INSTANCE.themeManager != null) {
         WildClient.INSTANCE.themeManager.invoke4(this.floatValue5, this.floatValue6);
      }

      return flag5;
   }

   public void invoke64(float f, Metrics metrics8) {
      float floatValue20 = this.measure15(metrics8, this.floatValue13);
      this.floatValue12 = this.measure16(this.floatValue12, f, -this.floatValue13, 0.0F, floatValue20);
      this.invoke65(metrics8);
   }

   public void invoke65(Metrics metrics9) {
      float floatValue21 = this.measure15(metrics9, this.floatValue13);
      this.floatValue12 = this.measure17(this.floatValue12, -this.floatValue13, 0.0F, floatValue21);
      this.floatValue11 = this.measure18(this.springAnimation2, this.floatValue11, -this.floatValue13, 0.0F, floatValue21);
   }

   public float measure5(String string, float f, SpringSpec springSpec5) {
      return this.valuesByKey.computeIfAbsent(string, stringx -> new SpringAnimation(f)).measure(f, springSpec5);
   }

   public float measure6(String string, float f, SpringAnimation.MotionSpec motionSpec) {
      return this.valuesByKey.computeIfAbsent(string, stringx -> new SpringAnimation(f)).measure2(f, motionSpec);
   }

   public float measure7(String string) {
      SpringAnimation springAnimation3 = this.valuesByKey.get(string);
      return springAnimation3 == null ? 0.0F : springAnimation3.getFloatValue();
   }

   public float measure8(String string) {
      SpringAnimation springAnimation4 = this.valuesByKey.get(string);
      return springAnimation4 == null ? 0.0F : springAnimation4.getFloatValue2();
   }

   public float measure9(String string, float f, SpringSpec springSpec6) {
      return this.valuesByKey.computeIfAbsent(string, stringx -> new SpringAnimation(0.0F)).measure(f, springSpec6);
   }

   public float measure10() {
      if (this.flag7) {
         return -1.0F;
      } else {
         long longValue = System.currentTimeMillis();
         long longValue2 = longValue - this.timestamp2;
         return longValue2 < 350L ? 0.0F : Math.min(1.0F, (float)(longValue2 - 350L) / 600.0F);
      }
   }

   public void invoke66() {
      if (WildClient.INSTANCE != null && WildClient.INSTANCE.configManager != null) {
         WildClient.INSTANCE.configManager.scheduleSave();
      }
   }

   public void invoke67(ColorScheme colorScheme2) {
      if (this.colorScheme2 == null) {
         this.colorScheme = colorScheme2;
         this.colorScheme2 = colorScheme2;
         this.springAnimation4.invoke(1.0F);
      }
   }

   public void invoke68(ColorScheme colorScheme3) {
      this.colorScheme = colorScheme3;
      this.colorScheme2 = colorScheme3;
      this.timestamp8 = 0L;
      this.springAnimation4.invoke(1.0F);
   }

   public void invoke69(SpringSpec springSpec7) {
      if (this.colorScheme2 != null && this.colorScheme != null) {
         this.colorScheme = this.colorScheme2;
         this.springAnimation4.invoke(1.0F);
         this.timestamp8 = 0L;
      } else {
         this.springAnimation4.invoke(1.0F);
         this.timestamp8 = 0L;
      }
   }

   public ColorScheme resolve4() {
      return this.colorScheme2 == null ? this.colorScheme : this.colorScheme2;
   }

   public float measure11() {
      if (this.timestamp7 <= 0L) {
         return -1.0F;
      } else {
         long longValue3 = System.currentTimeMillis() - this.timestamp7;
         float floatValue22 = Math.min(1.0F, (float)longValue3 / 760.0F);
         if (floatValue22 >= 1.0F) {
            this.timestamp7 = 0L;
         }

         return floatValue22;
      }
   }

   public long compute2(Module module) {
      return this.valuesByKey2.getOrDefault(System.identityHashCode(module), 0L);
   }

   public void invoke70() {
      if (WildClient.INSTANCE != null) {
         if (WildClient.INSTANCE.moduleManager == null) {
            WildClient.INSTANCE.moduleManager = new ModuleManager();
         }

         if (WildClient.INSTANCE.themeManager == null) {
            WildClient.INSTANCE.themeManager = new ThemeManager();
            WildClient.INSTANCE.themeManager.invoke();
         }
      }
   }

   private void invoke71() {
      if (this.module != null) {
         this.module.expanded = false;
      }

      if (this.keybindSetting != null) {
         this.keybindSetting.waitingForBind = false;
      }

      if (this.booleanSetting != null) {
         this.booleanSetting.waitingForBind = false;
      }

      this.module = null;
      this.keybindSetting = null;
      this.booleanSetting = null;
   }

   private void invoke72() {
      this.floatValue12 = 0.0F;
      this.floatValue11 = 0.0F;
      this.springAnimation2.invoke(0.0F);
   }

   private void invoke73() {
      this.floatValue18 = 0.0F;
      this.springAnimation3.invoke(0.0F);
   }

   private void invoke74() {
      this.flag25 = false;
      this.intValue3 = -1;
      this.intValue4 = -1;
      this.floatValue65 = 0.0F;
      this.floatValue66 = 0.0F;
      this.floatValue67 = 0.0F;
      this.floatValue68 = 0.0F;
      this.floatValue69 = 0.0F;
      this.floatValue70 = 0.0F;
      this.floatValue71 = 0.0F;
      this.floatValue72 = 0.0F;
      this.floatValue73 = 0.0F;
      this.floatValue74 = 0.0F;
      this.floatValue75 = 0.0F;
      this.floatValue76 = 0.0F;
      this.floatValue77 = 1.0F;
      this.springAnimation5.invoke(1.0F);
   }

   private float measure12(float f) {
      float floatValue23 = this.measure14(f, 0.0F, 1.0F);
      return floatValue23 * floatValue23 * floatValue23 * (floatValue23 * (floatValue23 * 6.0F - 15.0F) + 10.0F);
   }

   private float measure13(float f, float g, float h) {
      return f + (g - f) * this.measure14(h, 0.0F, 1.0F);
   }

   private boolean check9(Module module, String string) {
      if (string.startsWith("#")) {
         return this.check10(module, string);
      } else {
         String text7 = module.description == null ? "" : module.description;
         String text8 = this.resolve5(string);
         String text9 = module.name.toLowerCase(Locale.ROOT);
         String text10 = text7.toLowerCase(Locale.ROOT);
         return text9.contains(string) || text10.contains(string) || !text8.equals(string) && (text9.contains(text8) || text10.contains(text8));
      }
   }

   private boolean check10(Module module, String string) {
      String text11 = this.resolve5(string);
      String[] texts = text11.trim().split("\\s+");
      boolean flag6 = false;

      for (String text12 : texts) {
         if (text12 != null && !text12.isBlank()) {
            if (!text12.startsWith("#")) {
               return false;
            }

            ModuleRiskLevel moduleRiskLevel = ru.metaculture.protection.ModuleRiskLevel.resolve(text12);
            if (moduleRiskLevel == null || !module.hasRiskLevel(moduleRiskLevel)) {
               return false;
            }

            flag6 = true;
         }
      }

      return flag6;
   }

   private String resolve5(String string) {
      StringBuilder stringBuilder = new StringBuilder(string.length());

      for (int intValue4 = 0; intValue4 < string.length(); intValue4++) {
         char character = string.charAt(intValue4);
         stringBuilder.append(character < CHARS.length && CHARS[character] != 0 ? CHARS[character] : character);
      }

      return stringBuilder.toString();
   }

   private boolean check11(int i) {
      return i == 256 || i == 261 || i == 259;
   }

   private float measure14(float f, float g, float h) {
      return Math.max(g, Math.min(h, f));
   }

   private float measure15(Metrics metrics10, float f) {
      float floatValue24 = metrics10 == null ? 18.0F : metrics10.measure(18.0F);
      float floatValue25 = metrics10 == null ? 42.0F : metrics10.measure(42.0F);
      return Math.max(floatValue24, Math.min(floatValue25, floatValue24 + f * 0.12F));
   }

   private float measure16(float f, float g, float h, float i, float j) {
      float floatValue26 = f + g;
      if (floatValue26 < h) {
         return h - this.measure20(h - floatValue26, j);
      } else {
         return floatValue26 > i ? i + this.measure20(floatValue26 - i, j) : floatValue26;
      }
   }

   private float measure17(float f, float g, float h, float i) {
      float floatValue27 = this.measure14(f, g - i, h + i);
      float floatValue28 = SpringAnimation.measure3();
      if (floatValue27 < g) {
         float floatValue29 = g + SpringAnimation.measure6(floatValue27 - g, floatValue28, 12.8F);
         return Math.abs(floatValue29 - g) <= 0.35F ? g : floatValue29;
      } else if (floatValue27 > h) {
         float floatValue30 = h + SpringAnimation.measure6(floatValue27 - h, floatValue28, 12.8F);
         return Math.abs(floatValue30 - h) <= 0.35F ? h : floatValue30;
      } else {
         return floatValue27;
      }
   }

   private float measure18(SpringAnimation springAnimation5, float f, float g, float h, float i) {
      float floatValue31 = this.measure19(f, g - i, h + i, 0.34F);
      if (Math.abs(floatValue31 - f) > 0.001F) {
         springAnimation5.setFloatValue(floatValue31);
         springAnimation5.setFloatValue2(springAnimation5.getFloatValue2() * 0.74F);
      }

      if (Math.abs(floatValue31 - g) <= 0.35F && floatValue31 < g) {
         springAnimation5.setFloatValue(g);
         return g;
      } else if (Math.abs(floatValue31 - h) <= 0.35F && floatValue31 > h) {
         springAnimation5.setFloatValue(h);
         return h;
      } else {
         return floatValue31;
      }
   }

   private float measure19(float f, float g, float h, float i) {
      if (f < g) {
         return g - (g - f) * i;
      } else {
         return f > h ? h + (f - h) * i : f;
      }
   }

   private float measure20(float f, float g) {
      float floatValue32 = Math.max(1.0F, g);
      return floatValue32 * (1.0F - (float)Math.exp(-Math.max(0.0F, f) / (floatValue32 * 0.62F)));
   }

   @Generated
   public Set<Module> getValues() {
      return this.values;
   }

   @Generated
   public Map<String, SpringAnimation> getValuesByKey() {
      return this.valuesByKey;
   }

   @Generated
   public SpringAnimation getSpringAnimation() {
      return this.springAnimation;
   }

   @Generated
   public SpringAnimation getSpringAnimation2() {
      return this.springAnimation2;
   }

   @Generated
   public SpringAnimation getSpringAnimation3() {
      return this.springAnimation3;
   }

   @Generated
   public SpringAnimation getSpringAnimation4() {
      return this.springAnimation4;
   }

   @Generated
   public Map<Integer, Long> getValuesByKey2() {
      return this.valuesByKey2;
   }

   @Generated
   public Category getCategory() {
      return this.category;
   }

   @Generated
   public boolean isFlag() {
      return this.flag;
   }

   @Generated
   public boolean isFlag2() {
      return this.flag2;
   }

   @Generated
   public boolean isFlag3() {
      return this.flag3;
   }

   @Generated
   public long getTimestamp() {
      return this.timestamp;
   }

   @Generated
   public Theme getTheme() {
      return this.theme;
   }

   @Generated
   public boolean isFlag4() {
      return this.flag4;
   }

   @Generated
   public String getText() {
      return this.text;
   }

   @Generated
   public boolean isFlag5() {
      return this.flag5;
   }

   @Generated
   public String getText2() {
      return this.text2;
   }

   @Generated
   public boolean isFlag6() {
      return this.flag6;
   }

   @Generated
   public List<Integer> getItems() {
      return this.items;
   }

   @Generated
   public String getText3() {
      return this.text3;
   }

   @Generated
   public int getIntValue() {
      return this.intValue;
   }

   @Generated
   public boolean isFlag7() {
      return this.flag7;
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
   public boolean isFlag8() {
      return this.flag8;
   }

   @Generated
   public boolean isFlag9() {
      return this.flag9;
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
   public long getTimestamp2() {
      return this.timestamp2;
   }

   @Generated
   public long getTimestamp3() {
      return this.timestamp3;
   }

   @Generated
   public long getTimestamp4() {
      return this.timestamp4;
   }

   @Generated
   public long getTimestamp5() {
      return this.timestamp5;
   }

   @Generated
   public long getTimestamp6() {
      return this.timestamp6;
   }

   @Generated
   public boolean isFlag10() {
      return this.flag10;
   }

   @Generated
   public boolean isFlag11() {
      return this.flag11;
   }

   @Generated
   public boolean isFlag12() {
      return this.flag12;
   }

   @Generated
   public boolean isFlag13() {
      return this.flag13;
   }

   @Generated
   public boolean isFlag14() {
      return this.flag14;
   }

   @Generated
   public float getFloatValue20() {
      return this.floatValue20;
   }

   @Generated
   public float getFloatValue21() {
      return this.floatValue21;
   }

   @Generated
   public float getFloatValue22() {
      return this.floatValue22;
   }

   @Generated
   public float getFloatValue23() {
      return this.floatValue23;
   }

   @Generated
   public float getFloatValue24() {
      return this.floatValue24;
   }

   @Generated
   public boolean isFlag15() {
      return this.flag15;
   }

   @Generated
   public boolean isFlag16() {
      return this.flag16;
   }

   @Generated
   public float getFloatValue25() {
      return this.floatValue25;
   }

   @Generated
   public float getFloatValue26() {
      return this.floatValue26;
   }

   @Generated
   public float getFloatValue27() {
      return this.floatValue27;
   }

   @Generated
   public float getFloatValue28() {
      return this.floatValue28;
   }

   @Generated
   public float getFloatValue29() {
      return this.floatValue29;
   }

   @Generated
   public boolean isFlag17() {
      return this.flag17;
   }

   @Generated
   public NumberSetting getNumberSetting() {
      return this.numberSetting;
   }

   @Generated
   public ColorSetting getColorSetting() {
      return this.colorSetting;
   }

   @Generated
   public float getFloatValue30() {
      return this.floatValue30;
   }

   @Generated
   public float getFloatValue31() {
      return this.floatValue31;
   }

   @Generated
   public Module getModule() {
      return this.module;
   }

   @Generated
   public KeybindSetting getKeybindSetting() {
      return this.keybindSetting;
   }

   @Generated
   public BooleanSetting getBooleanSetting() {
      return this.booleanSetting;
   }

   @Generated
   public TextSetting getTextSetting() {
      return this.textSetting;
   }

   @Generated
   public ModeSetting getModeSetting() {
      return this.modeSetting;
   }

   @Generated
   public FoundryShaderSetting getFoundryShaderSetting() {
      return this.foundryShaderSetting;
   }

   @Generated
   public int getIntValue2() {
      return this.intValue2;
   }

   @Generated
   public ColorScheme getColorScheme() {
      return this.colorScheme;
   }

   @Generated
   public ColorScheme getColorScheme2() {
      return this.colorScheme2;
   }

   @Generated
   public Theme getTheme2() {
      return this.theme2;
   }

   @Generated
   public float DynamicButtonSetting() {
      return this.floatValue32;
   }

   @Generated
   public float getFloatValue33() {
      return this.floatValue33;
   }

   @Generated
   public long getTimestamp7() {
      return this.timestamp7;
   }

   @Generated
   public long getTimestamp8() {
      return this.timestamp8;
   }

   @Generated
   public ColorSetting getColorSetting2() {
      return this.colorSetting2;
   }

   @Generated
   public boolean SpacerSetting() {
      return this.flag18;
   }

   @Generated
   public boolean FoundryShaderSetting() {
      return this.flag19;
   }

   @Generated
   public boolean isFlag20() {
      return this.flag20;
   }

   @Generated
   public float getFloatValue34() {
      return this.floatValue34;
   }

   @Generated
   public float getFloatValue35() {
      return this.floatValue35;
   }

   @Generated
   public float getFloatValue36() {
      return this.floatValue36;
   }

   @Generated
   public float getFloatValue37() {
      return this.floatValue37;
   }

   @Generated
   public float getFloatValue38() {
      return this.floatValue38;
   }

   @Generated
   public float getFloatValue39() {
      return this.floatValue39;
   }

   @Generated
   public float getFloatValue40() {
      return this.floatValue40;
   }

   @Generated
   public float getFloatValue41() {
      return this.floatValue41;
   }

   @Generated
   public float getFloatValue42() {
      return this.floatValue42;
   }

   @Generated
   public float getFloatValue43() {
      return this.floatValue43;
   }

   @Generated
   public float getFloatValue44() {
      return this.floatValue44;
   }

   @Generated
   public float getFloatValue45() {
      return this.floatValue45;
   }

   @Generated
   public float getFloatValue46() {
      return this.floatValue46;
   }

   @Generated
   public float getFloatValue47() {
      return this.floatValue47;
   }

   @Generated
   public float getFloatValue48() {
      return this.floatValue48;
   }

   @Generated
   public float getFloatValue49() {
      return this.floatValue49;
   }

   @Generated
   public float getFloatValue50() {
      return this.floatValue50;
   }

   @Generated
   public float getFloatValue51() {
      return this.floatValue51;
   }

   @Generated
   public float getFloatValue52() {
      return this.floatValue52;
   }

   @Generated
   public float getFloatValue53() {
      return this.floatValue53;
   }

   @Generated
   public float getFloatValue54() {
      return this.floatValue54;
   }

   @Generated
   public float getFloatValue55() {
      return this.floatValue55;
   }

   @Generated
   public float getFloatValue56() {
      return this.floatValue56;
   }

   @Generated
   public float getFloatValue57() {
      return this.floatValue57;
   }

   @Generated
   public float getFloatValue58() {
      return this.floatValue58;
   }

   @Generated
   public float getFloatValue59() {
      return this.floatValue59;
   }

   @Generated
   public float getFloatValue60() {
      return this.floatValue60;
   }

   @Generated
   public float getDynamicButtonSetting() {
      return this.DynamicButtonSetting;
   }

   @Generated
   public float getFloatValue61() {
      return this.floatValue61;
   }

   @Generated
   public float getFloatValue62() {
      return this.floatValue62;
   }

   @Generated
   public float getFloatValue63() {
      return this.floatValue63;
   }

   @Generated
   public float getFloatValue64() {
      return this.floatValue64;
   }

   @Generated
   public float getSpacerSetting() {
      return this.SpacerSetting;
   }

   @Generated
   public float getFoundryShaderSetting2() {
      return this.FoundryShaderSetting;
   }

   @Generated
   public Map<ColorSetting, Integer> getValuesByKey3() {
      return this.valuesByKey3;
   }

   @Generated
   public ColorSetting getColorSetting3() {
      return this.colorSetting3;
   }

   @Generated
   public String getText4() {
      return this.text4;
   }

   @Generated
   public ColorSetting getColorSetting4() {
      return this.colorSetting4;
   }

   @Generated
   public String getText5() {
      return this.text5;
   }

   @Generated
   public boolean isFlag21() {
      return this.flag21;
   }

   @Generated
   public boolean isFlag22() {
      return this.flag22;
   }

   @Generated
   public boolean isFlag23() {
      return this.flag23;
   }

   @Generated
   public boolean isFlag24() {
      return this.flag24;
   }

   @Generated
   public SpringAnimation getSpringAnimation5() {
      return this.springAnimation5;
   }

   @Generated
   public boolean isFlag25() {
      return this.flag25;
   }

   @Generated
   public boolean isFlag26() {
      return this.flag26;
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
   public float getFloatValue65() {
      return this.floatValue65;
   }

   @Generated
   public float getFloatValue66() {
      return this.floatValue66;
   }

   @Generated
   public float getFloatValue67() {
      return this.floatValue67;
   }

   @Generated
   public float getFloatValue68() {
      return this.floatValue68;
   }

   @Generated
   public float getFloatValue69() {
      return this.floatValue69;
   }

   @Generated
   public float getFloatValue70() {
      return this.floatValue70;
   }

   @Generated
   public float getFloatValue71() {
      return this.floatValue71;
   }

   @Generated
   public float getFloatValue72() {
      return this.floatValue72;
   }

   @Generated
   public float getFloatValue73() {
      return this.floatValue73;
   }

   @Generated
   public float getFloatValue74() {
      return this.floatValue74;
   }

   @Generated
   public float getFloatValue75() {
      return this.floatValue75;
   }

   @Generated
   public float getFloatValue76() {
      return this.floatValue76;
   }

   @Generated
   public float getFloatValue77() {
      return this.floatValue77;
   }

   @Generated
   public String getText6() {
      return this.text6;
   }

   @Generated
   public float getFloatValue78() {
      return this.floatValue78;
   }

   @Generated
   public float getFloatValue79() {
      return this.floatValue79;
   }

   @Generated
   public long getTimestamp9() {
      return this.timestamp9;
   }

   @Generated
   public String getText7() {
      return this.text7;
   }

   @Generated
   public void setCategory(Category category2) {
      this.category = category2;
   }

   @Generated
   public void setFlag(boolean bl) {
      this.flag = bl;
   }

   @Generated
   public void setFlag2(boolean bl) {
      this.flag2 = bl;
   }

   @Generated
   public void setFlag3(boolean bl) {
      this.flag3 = bl;
   }

   @Generated
   public void setTimestamp(long l) {
      this.timestamp = l;
   }

   @Generated
   public void setTheme(Theme theme2) {
      this.theme = theme2;
   }

   @Generated
   public void setFlag4(boolean bl) {
      this.flag4 = bl;
   }

   @Generated
   public void setText(String string) {
      this.text = string;
   }

   @Generated
   public void setFlag5(boolean bl) {
      this.flag5 = bl;
   }

   @Generated
   public void setText2(String string) {
      this.text2 = string;
   }

   @Generated
   public void setFlag6(boolean bl) {
      this.flag6 = bl;
   }

   @Generated
   public void invoke75(List<Integer> list) {
      if (list == null) {
         this.items = List.of();
         this.text3 = null;
         this.intValue = -1;
      } else {
         this.items = list;
      }
   }

   @Generated
   public void setText3(String string) {
      this.text3 = string;
   }

   @Generated
   public void setIntValue(int i) {
      this.intValue = i;
   }

   @Generated
   public void setFlag7(boolean bl) {
      this.flag7 = bl;
   }

   @Generated
   public void setFloatValue(float f) {
      this.floatValue = f;
   }

   @Generated
   public void setFloatValue2(float f) {
      this.floatValue2 = f;
   }

   @Generated
   public void setFloatValue3(float f) {
      this.floatValue3 = f;
   }

   @Generated
   public void setFloatValue4(float f) {
      this.floatValue4 = f;
   }

   @Generated
   public void setFloatValue5(float f) {
      this.floatValue5 = f;
   }

   @Generated
   public void setFloatValue6(float f) {
      this.floatValue6 = f;
   }

   @Generated
   public void setFloatValue7(float f) {
      this.floatValue7 = f;
   }

   @Generated
   public void setFloatValue8(float f) {
      this.floatValue8 = f;
   }

   @Generated
   public void setFloatValue9(float f) {
      this.floatValue9 = f;
   }

   @Generated
   public void setFloatValue10(float f) {
      this.floatValue10 = f;
   }

   @Generated
   public void setFloatValue11(float f) {
      this.floatValue11 = f;
   }

   @Generated
   public void setFloatValue12(float f) {
      this.floatValue12 = f;
   }

   @Generated
   public void setFloatValue142(float f) {
      this.floatValue14 = f;
   }

   @Generated
   public void setFloatValue152(float f) {
      this.floatValue15 = f;
   }

   @Generated
   public void setFloatValue16(float f) {
      this.floatValue16 = f;
   }

   @Generated
   public void setFloatValue17(float f) {
      this.floatValue17 = f;
   }

   @Generated
   public void setFlag8(boolean bl) {
      this.flag8 = bl;
   }

   @Generated
   public void setFlag9(boolean bl) {
      this.flag9 = bl;
   }

   @Generated
   public void setFloatValue18(float f) {
      this.floatValue18 = f;
   }

   @Generated
   public void setTimestamp2(long l) {
      this.timestamp2 = l;
   }

   @Generated
   public void setTimestamp3(long l) {
      this.timestamp3 = l;
   }

   @Generated
   public void setTimestamp4(long l) {
      this.timestamp4 = l;
   }

   @Generated
   public void setTimestamp5(long l) {
      this.timestamp5 = l;
   }

   @Generated
   public void setTimestamp6(long l) {
      this.timestamp6 = l;
   }

   @Generated
   public void setFlag10(boolean bl) {
      this.flag10 = bl;
   }

   @Generated
   public void setFlag11(boolean bl) {
      this.flag11 = bl;
   }

   @Generated
   public void setFlag12(boolean bl) {
      this.flag12 = bl;
   }

   @Generated
   public void setFlag13(boolean bl) {
      this.flag13 = bl;
   }

   @Generated
   public void setFlag14(boolean bl) {
      this.flag14 = bl;
   }

   @Generated
   public void setFloatValue20(float f) {
      this.floatValue20 = f;
   }

   @Generated
   public void setFloatValue21(float f) {
      this.floatValue21 = f;
   }

   @Generated
   public void setFloatValue22(float f) {
      this.floatValue22 = f;
   }

   @Generated
   public void setFloatValue23(float f) {
      this.floatValue23 = f;
   }

   @Generated
   public void setFloatValue24(float f) {
      this.floatValue24 = f;
   }

   @Generated
   public void setFlag16(boolean bl) {
      this.flag16 = bl;
   }

   @Generated
   public void setFloatValue25(float f) {
      this.floatValue25 = f;
   }

   @Generated
   public void setFloatValue26(float f) {
      this.floatValue26 = f;
   }

   @Generated
   public void setFloatValue27(float f) {
      this.floatValue27 = f;
   }

   @Generated
   public void setFloatValue28(float f) {
      this.floatValue28 = f;
   }

   @Generated
   public void setFloatValue29(float f) {
      this.floatValue29 = f;
   }

   @Generated
   public void setNumberSetting(NumberSetting numberSetting) {
      this.numberSetting = numberSetting;
   }

   @Generated
   public void setColorSetting(ColorSetting colorSetting3) {
      this.colorSetting = colorSetting3;
   }

   @Generated
   public void setFloatValue30(float f) {
      this.floatValue30 = f;
   }

   @Generated
   public void setFloatValue31(float f) {
      this.floatValue31 = f;
   }

   @Generated
   public void setModule(Module module) {
      this.module = module;
   }

   @Generated
   public void setKeybindSetting(KeybindSetting keybindSetting2) {
      this.keybindSetting = keybindSetting2;
   }

   @Generated
   public void setBooleanSetting(BooleanSetting booleanSetting2) {
      this.booleanSetting = booleanSetting2;
   }

   @Generated
   public void setModeSetting(ModeSetting modeSetting3) {
      this.modeSetting = modeSetting3;
   }

   @Generated
   public void setFoundryShaderSetting(FoundryShaderSetting foundryShaderSetting3) {
      this.foundryShaderSetting = foundryShaderSetting3;
   }

   @Generated
   public void setIntValue2(int i) {
      this.intValue2 = i;
   }

   @Generated
   public void setColorScheme(ColorScheme colorScheme4) {
      this.colorScheme = colorScheme4;
   }

   @Generated
   public void setColorScheme2(ColorScheme colorScheme5) {
      this.colorScheme2 = colorScheme5;
   }

   @Generated
   public void setTheme2(Theme theme3) {
      this.theme2 = theme3;
   }

   @Generated
   public void setFloatValue32(float f) {
      this.floatValue32 = f;
   }

   @Generated
   public void setFloatValue33(float f) {
      this.floatValue33 = f;
   }

   @Generated
   public void setTimestamp7(long l) {
      this.timestamp7 = l;
   }

   @Generated
   public void setTimestamp8(long l) {
      this.timestamp8 = l;
   }

   @Generated
   public void setColorSetting2(ColorSetting colorSetting4) {
      this.colorSetting2 = colorSetting4;
   }

   @Generated
   public void setFlag18(boolean bl) {
      this.flag18 = bl;
   }

   @Generated
   public void setFlag19(boolean bl) {
      this.flag19 = bl;
   }

   @Generated
   public void setFlag20(boolean bl) {
      this.flag20 = bl;
   }

   @Generated
   public void setFloatValue34(float f) {
      this.floatValue34 = f;
   }

   @Generated
   public void setFloatValue35(float f) {
      this.floatValue35 = f;
   }

   @Generated
   public void setFloatValue36(float f) {
      this.floatValue36 = f;
   }

   @Generated
   public void setFloatValue37(float f) {
      this.floatValue37 = f;
   }

   @Generated
   public void setFloatValue38(float f) {
      this.floatValue38 = f;
   }

   @Generated
   public void setFloatValue39(float f) {
      this.floatValue39 = f;
   }

   @Generated
   public void setFloatValue40(float f) {
      this.floatValue40 = f;
   }

   @Generated
   public void setFloatValue41(float f) {
      this.floatValue41 = f;
   }

   @Generated
   public void setFloatValue42(float f) {
      this.floatValue42 = f;
   }

   @Generated
   public void setFloatValue43(float f) {
      this.floatValue43 = f;
   }

   @Generated
   public void setFloatValue44(float f) {
      this.floatValue44 = f;
   }

   @Generated
   public void setFloatValue45(float f) {
      this.floatValue45 = f;
   }

   @Generated
   public void setFloatValue46(float f) {
      this.floatValue46 = f;
   }

   @Generated
   public void setFloatValue47(float f) {
      this.floatValue47 = f;
   }

   @Generated
   public void setFloatValue48(float f) {
      this.floatValue48 = f;
   }

   @Generated
   public void setFloatValue49(float f) {
      this.floatValue49 = f;
   }

   @Generated
   public void setFloatValue50(float f) {
      this.floatValue50 = f;
   }

   @Generated
   public void setFloatValue51(float f) {
      this.floatValue51 = f;
   }

   @Generated
   public void setFloatValue52(float f) {
      this.floatValue52 = f;
   }

   @Generated
   public void setFloatValue53(float f) {
      this.floatValue53 = f;
   }

   @Generated
   public void setFloatValue54(float f) {
      this.floatValue54 = f;
   }

   @Generated
   public void setFloatValue55(float f) {
      this.floatValue55 = f;
   }

   @Generated
   public void setFloatValue56(float f) {
      this.floatValue56 = f;
   }

   @Generated
   public void setFloatValue57(float f) {
      this.floatValue57 = f;
   }

   @Generated
   public void setFloatValue58(float f) {
      this.floatValue58 = f;
   }

   @Generated
   public void setFloatValue59(float f) {
      this.floatValue59 = f;
   }

   @Generated
   public void setFloatValue60(float f) {
      this.floatValue60 = f;
   }

   @Generated
   public void setDynamicButtonSetting(float f) {
      this.DynamicButtonSetting = f;
   }

   @Generated
   public void setFloatValue61(float f) {
      this.floatValue61 = f;
   }

   @Generated
   public void setFloatValue62(float f) {
      this.floatValue62 = f;
   }

   @Generated
   public void setFloatValue63(float f) {
      this.floatValue63 = f;
   }

   @Generated
   public void setFloatValue64(float f) {
      this.floatValue64 = f;
   }

   @Generated
   public void setSpacerSetting(float f) {
      this.SpacerSetting = f;
   }

   @Generated
   public void setFoundryShaderSetting2(float f) {
      this.FoundryShaderSetting = f;
   }

   @Generated
   public void setColorSetting3(ColorSetting colorSetting5) {
      this.colorSetting3 = colorSetting5;
   }

   @Generated
   public void setText4(String string) {
      this.text4 = string;
   }

   @Generated
   public void setColorSetting4(ColorSetting colorSetting6) {
      this.colorSetting4 = colorSetting6;
   }

   @Generated
   public void setText5(String string) {
      this.text5 = string;
   }

   @Generated
   public void setFlag21(boolean bl) {
      this.flag21 = bl;
   }

   @Generated
   public void setFlag23(boolean bl) {
      this.flag23 = bl;
   }

   @Generated
   public void setFlag24(boolean bl) {
      this.flag24 = bl;
   }

   @Generated
   public void setFlag25(boolean bl) {
      this.flag25 = bl;
   }

   @Generated
   public void setFlag26(boolean bl) {
      this.flag26 = bl;
   }

   @Generated
   public void setIntValue3(int i) {
      this.intValue3 = i;
   }

   @Generated
   public void setIntValue4(int i) {
      this.intValue4 = i;
   }

   @Generated
   public void setFloatValue65(float f) {
      this.floatValue65 = f;
   }

   @Generated
   public void setFloatValue66(float f) {
      this.floatValue66 = f;
   }

   @Generated
   public void setFloatValue67(float f) {
      this.floatValue67 = f;
   }

   @Generated
   public void setFloatValue68(float f) {
      this.floatValue68 = f;
   }

   @Generated
   public void setFloatValue69(float f) {
      this.floatValue69 = f;
   }

   @Generated
   public void setFloatValue70(float f) {
      this.floatValue70 = f;
   }

   @Generated
   public void setFloatValue71(float f) {
      this.floatValue71 = f;
   }

   @Generated
   public void setFloatValue72(float f) {
      this.floatValue72 = f;
   }

   @Generated
   public void setFloatValue73(float f) {
      this.floatValue73 = f;
   }

   @Generated
   public void setFloatValue74(float f) {
      this.floatValue74 = f;
   }

   @Generated
   public void setFloatValue75(float f) {
      this.floatValue75 = f;
   }

   @Generated
   public void setFloatValue76(float f) {
      this.floatValue76 = f;
   }

   @Generated
   public void setFloatValue77(float f) {
      this.floatValue77 = f;
   }

   @Generated
   public void setText6(String string) {
      this.text6 = string;
   }

   @Generated
   public void setFloatValue78(float f) {
      this.floatValue78 = f;
   }

   @Generated
   public void setFloatValue79(float f) {
      this.floatValue79 = f;
   }

   @Generated
   public void setTimestamp9(long l) {
      this.timestamp9 = l;
   }

   @Generated
   public void setText7(String string) {
      this.text7 = string;
   }

   static {
      String text13 = "йцукенгшщзхъфывапролджэячсмитьбю";
      String text14 = "qwertyuiop[]asdfghjkl;'zxcvbnm,.";

      for (int intValue5 = 0; intValue5 < text13.length(); intValue5++) {
         CHARS[text13.charAt(intValue5)] = text14.charAt(intValue5);
      }
   }
}
