package ru.metaculture.protection;

import com.google.gson.JsonElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import org.wild.module.api.Module;

public final class SettingBinding {
   private static final double DOUBLE_VALUE = 1.0E-6;
   private final SettingBinding.SettingBindingState settingBindingState;
   private final Module module;
   private final Setting setting;
   private final String text;
   private final String text2;
   private final Object object;
   private final String text3;
   private Object object2;
   private Object object3;
   private int intValue;
   private KeybindMode keybindMode;
   private int intValue2;
   private KeybindMode keybindMode2;
   private boolean flag;
   private boolean flag2;

   private SettingBinding(
      SettingBinding.SettingBindingState settingBindingState,
      Module module,
      Setting setting,
      String string,
      String string2,
      Object object,
      String string3,
      Object object2,
      Object object3,
      int i,
      KeybindMode keybindMode
   ) {
      this.settingBindingState = Objects.requireNonNull(settingBindingState, "targetType");
      this.module = module;
      this.setting = setting;
      this.text = string == null ? "" : string;
      this.text2 = string2 == null ? "" : string2;
      this.object = object;
      this.text3 = string3 == null ? "" : string3;
      this.object2 = object2;
      this.object3 = object3;
      this.intValue = i;
      this.keybindMode = Objects.requireNonNull(keybindMode, "mode");
      this.intValue2 = i;
      this.keybindMode2 = keybindMode;
      this.flag = i == -1;
      this.flag2 = this.flag;
   }

   public static SettingBinding resolve(Module module) {
      Objects.requireNonNull(module, "module");
      int intValue = module.bindKey > 0 ? module.bindKey : -1;
      return new SettingBinding(
         SettingBinding.SettingBindingState.MODULE,
         module,
         null,
         module.name,
         module.description,
         module.enabled,
         "Modules toggle state is controlled by the mode.",
         null,
         null,
         intValue,
         KeybindMode.TOGGLE
      );
   }

   public static SettingBinding resolve2(Module module, Setting setting2, Object object, Object object2, int i, KeybindMode keybindMode2) {
      Objects.requireNonNull(module, "module");
      Objects.requireNonNull(setting2, "setting");
      return new SettingBinding(
         SettingBinding.SettingBindingState.SETTING, module, setting2, setting2.name, module.name, object, "", object2, object2, i, keybindMode2
      );
   }

   public SettingBinding.SettingBindingState getSettingBindingState() {
      return this.settingBindingState;
   }

   public Module getModule() {
      return this.module;
   }

   public Setting getSetting() {
      return this.setting;
   }

   public String getText() {
      return this.text;
   }

   public String getText2() {
      return this.text2;
   }

   public Object getObject() {
      return this.object;
   }

   public String getText3() {
      return this.text3;
   }

   public Object resolve3() {
      return !this.check2() ? null : this.object2;
   }

   public void invoke(Object object) {
      this.invoke9();
      this.object2 = this.resolve7(object);
      this.flag = false;
   }

   public int getIntValue() {
      return this.intValue;
   }

   public void invoke2(int i) {
      if (i == -1 || i >= 32 && i <= 348) {
         this.intValue = i;
         if (i != -1) {
            this.flag = false;
         }
      } else {
         throw new IllegalArgumentException("keyCode must be GLFW.GLFW_KEY_UNKNOWN or a valid GLFW key constant");
      }
   }

   public KeybindMode getKeybindMode() {
      return this.keybindMode;
   }

   public void setKeybindMode(KeybindMode keybindMode3) {
      this.keybindMode = Objects.requireNonNull(keybindMode3, "mode");
   }

   public boolean check() {
      return this.settingBindingState == SettingBinding.SettingBindingState.MODULE;
   }

   public boolean check2() {
      return this.settingBindingState == SettingBinding.SettingBindingState.SETTING;
   }

   public boolean check3() {
      return this.settingBindingState == SettingBinding.SettingBindingState.SETTING && this.setting != null
         ? this.setting instanceof NumberSetting || this.setting instanceof ModeSetting || this.setting instanceof MultiSelectSetting
         : false;
   }

   public boolean check4() {
      return !this.text3.isBlank();
   }

   public boolean check5() {
      return this.intValue != this.intValue2
         || this.keybindMode != this.keybindMode2
         || this.flag != this.flag2
         || this.check6();
   }

   public boolean check6() {
      return this.check2() && this.setting != null ? !check7(this.setting, this.object2, this.object3) : false;
   }

   public void invoke3() {
      this.invoke9();
      this.flag = this.flag2;
   }

   public void invoke4() {
      this.invoke5();
   }

   public void invoke5() {
      this.intValue2 = this.intValue;
      this.keybindMode2 = this.keybindMode;
      this.flag2 = this.flag;
      if (this.check2() && this.setting != null) {
         this.object3 = resolve20(this.setting, this.object2);
      }
   }

   public void invoke6() {
      this.intValue = this.intValue2;
      this.keybindMode = this.keybindMode2;
      this.flag = this.flag2;
      if (this.check2()) {
      }
   }

   public void invoke7() {
      this.intValue = -1;
      this.flag = true;
      if (this.check2()) {
      }
   }

   public boolean isFlag() {
      return this.flag;
   }

   public Object resolve4() {
      this.invoke9();
      return resolve20(this.setting, this.object2);
   }

   public void invoke8(Object object) {
      this.invoke9();
      this.object2 = this.resolve7(Objects.requireNonNull(object, "value"));
      this.flag = false;
   }

   public String resolve5() {
      return this.module != null ? this.module.name : "";
   }

   public String resolve6() {
      return this.setting != null ? this.setting.name : "";
   }

   private void invoke9() {
      if (!this.check2()) {
         throw new IllegalStateException("Operation only supported for setting targets");
      } else if (this.setting == null) {
         throw new IllegalStateException("Setting context is not available");
      }
   }

   private Object resolve7(Object object) {
      Objects.requireNonNull(object, "value");
      if (this.setting instanceof BooleanSetting) {
         if (object instanceof Boolean booleanValue) {
            return booleanValue;
         } else if (object instanceof Number number) {
            return number.doubleValue() != 0.0;
         } else {
            throw new IllegalArgumentException("Target value must be boolean-compatible");
         }
      } else if (this.setting instanceof NumberSetting) {
         return this.resolve8(object);
      } else if (this.setting instanceof ModeSetting) {
         return this.resolve9(object);
      } else if (this.setting instanceof MultiSelectSetting) {
         return this.resolve10(object);
      } else if (this.setting instanceof ColorSetting) {
         return this.resolve11(object);
      } else {
         return object instanceof String ? object : object.toString();
      }
   }

   private Object resolve8(Object object) {
      if (this.setting instanceof NumberSetting numberSetting) {
         if (object instanceof Number number2) {
            double doubleValue = number2.doubleValue();
            if (!Double.isNaN(doubleValue) && !Double.isInfinite(doubleValue)) {
               double doubleValue2 = Math.min(Math.max(doubleValue, (double)numberSetting.minimum), (double)numberSetting.maximum);
               double doubleValue3 = Math.round((doubleValue2 - numberSetting.minimum) / numberSetting.step);
               double doubleValue4 = numberSetting.minimum + doubleValue3 * numberSetting.step;
               if (doubleValue4 < numberSetting.minimum) {
                  doubleValue4 = numberSetting.minimum;
               } else if (doubleValue4 > numberSetting.maximum) {
                  doubleValue4 = numberSetting.maximum;
               }

               return doubleValue4;
            } else {
               throw new IllegalArgumentException("Target value must be a finite number");
            }
         } else {
            throw new IllegalArgumentException("Target value must be numeric");
         }
      } else {
         throw new IllegalStateException("Setting is not a SliderSetting");
      }
   }

   private Object resolve9(Object object) {
      if (this.setting instanceof ModeSetting modeSetting) {
         String text = object.toString();
         if (modeSetting.options != null && modeSetting.options.contains(text)) {
            return text;
         } else {
            throw new IllegalArgumentException("Unsupported option '" + text + "'");
         }
      } else {
         throw new IllegalStateException("Setting is not a ModeSetting");
      }
   }

   private Object resolve10(Object object) {
      if (!(this.setting instanceof MultiSelectSetting multiSelectSetting)) {
         throw new IllegalStateException("Setting is not a ListSetting");
      } else {
         multiSelectSetting.refreshOptions();
         if (!(object instanceof Collection items)) {
            throw new IllegalArgumentException("Target value must be a collection");
         } else {
            LinkedHashSet linkedHashSet = new LinkedHashSet();

            for (Object object4 : items) {
               if (object4 != null) {
                  String text2 = object4.toString();
                  if (multiSelectSetting.options == null || !multiSelectSetting.options.contains(text2)) {
                     throw new IllegalArgumentException("Unsupported option '" + text2 + "'");
                  }

                  linkedHashSet.add(text2);
               }
            }

            return linkedHashSet;
         }
      }
   }

   private Object resolve11(Object object) {
      if (this.setting instanceof ColorSetting colorSetting) {
         if (object instanceof ColorPickerState colorPickerState) {
            return colorPickerState;
         } else if (object instanceof Number number3) {
            return ColorPickerState.resolve3(number3.intValue());
         } else if (object instanceof String text3) {
            try {
               String text4 = text3.startsWith("#") ? text3.substring(1) : text3;
               int intValue2 = (int)Long.parseUnsignedLong(text4, 16);
               int intValue3 = text4.length() > 6 ? intValue2 : 0xFF000000 | intValue2;
               return ColorPickerState.resolve3(intValue3);
            } catch (NumberFormatException numberFormatException) {
               throw new IllegalArgumentException("Invalid colour string: " + text3, numberFormatException);
            }
         } else {
            return ColorPickerState.resolve(colorSetting.measure2(), colorSetting.saturation, colorSetting.brightness, colorSetting.floatValue3);
         }
      } else {
         throw new IllegalStateException("Setting is not a HueSetting");
      }
   }

   private static Object resolve12(Setting setting3, Object object) {
      if (setting3 instanceof BooleanSetting) {
         return resolve13((JsonElement)null, object, setting3);
      } else if (setting3 instanceof NumberSetting) {
         return resolve14((JsonElement)null, setting3, object);
      } else if (setting3 instanceof ModeSetting) {
         return resolve15(null, setting3, object);
      } else if (setting3 instanceof MultiSelectSetting) {
         return resolve17(null, setting3, object);
      } else {
         return setting3 instanceof ColorSetting ? resolve18(null, setting3, object) : object;
      }
   }

   private static Object resolve13(JsonElement jsonElement, Object object, Setting setting4) {
      boolean flag = object instanceof Boolean booleanValue2 ? booleanValue2 : Boolean.FALSE;
      if (jsonElement != null && jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isBoolean()) {
         flag = jsonElement.getAsBoolean();
      }

      return flag;
   }

   private static Object resolve14(JsonElement jsonElement, Setting setting5, Object object) {
      if (setting5 instanceof NumberSetting numberSetting2) {
         double doubleValue5 = object instanceof Number number4 ? number4.doubleValue() : numberSetting2.value;
         if (jsonElement != null && jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isNumber()) {
            doubleValue5 = jsonElement.getAsDouble();
         }

         double doubleValue6 = Math.min(Math.max(doubleValue5, (double)numberSetting2.minimum), (double)numberSetting2.maximum);
         double doubleValue7 = Math.round((doubleValue6 - numberSetting2.minimum) / numberSetting2.step);
         double doubleValue8 = numberSetting2.minimum + doubleValue7 * numberSetting2.step;
         if (doubleValue8 < numberSetting2.minimum) {
            doubleValue8 = numberSetting2.minimum;
         } else if (doubleValue8 > numberSetting2.maximum) {
            doubleValue8 = numberSetting2.maximum;
         }

         return doubleValue8;
      } else {
         throw new IllegalStateException("Setting is not a SliderSetting");
      }
   }

   private static Object resolve15(JsonElement jsonElement, Setting setting6, Object object) {
      if (setting6 instanceof ModeSetting modeSetting2) {
         String text5 = object instanceof String text6 ? text6 : (modeSetting2.value != null ? modeSetting2.value : "");
         if (jsonElement != null && jsonElement.isJsonPrimitive()) {
            text5 = jsonElement.getAsString();
         }

         if (modeSetting2.options == null || !modeSetting2.options.contains(text5)) {
            text5 = modeSetting2.value != null ? modeSetting2.value : "";
         }

         return text5;
      } else {
         throw new IllegalStateException("Setting is not a ModeSetting");
      }
   }

   private static Object resolve16(JsonElement jsonElement, Setting setting7, Object object) {
      String text7 = object instanceof String text8 ? text8 : "";
      if (jsonElement != null && jsonElement.isJsonPrimitive()) {
         text7 = jsonElement.getAsString();
      }

      return text7;
   }

   private static Object resolve17(JsonElement jsonElement, Setting setting8, Object object) {
      if (!(setting8 instanceof MultiSelectSetting multiSelectSetting2)) {
         throw new IllegalStateException("Setting is not a ListSetting");
      } else {
         multiSelectSetting2.refreshOptions();
         LinkedHashSet linkedHashSet2 = new LinkedHashSet();
         if (jsonElement != null && jsonElement.isJsonArray()) {
            for (JsonElement jsonElement2 : jsonElement.getAsJsonArray()) {
               if (jsonElement2.isJsonPrimitive()) {
                  String text9 = jsonElement2.getAsString();
                  if (multiSelectSetting2.options != null && multiSelectSetting2.options.contains(text9)) {
                     linkedHashSet2.add(text9);
                  }
               }
            }
         }

         return linkedHashSet2;
      }
   }

   private static Object resolve18(JsonElement jsonElement, Setting setting9, Object object) {
      if (setting9 instanceof ColorSetting colorSetting2) {
         ColorPickerState colorPickerState2;
         if (object instanceof ColorPickerState colorPickerState3) {
            colorPickerState2 = colorPickerState3;
         } else if (object instanceof Number number5) {
            colorPickerState2 = ColorPickerState.resolve3(number5.intValue());
         } else if (object instanceof String text10) {
            try {
               String text11 = text10.startsWith("#") ? text10.substring(1) : text10;
               int intValue4 = (int)Long.parseUnsignedLong(text11, 16);
               int intValue5 = text11.length() > 6 ? intValue4 : 0xFF000000 | intValue4;
               colorPickerState2 = ColorPickerState.resolve3(intValue5);
            } catch (NumberFormatException numberFormatException2) {
               colorPickerState2 = ColorPickerState.resolve(colorSetting2.measure2(), colorSetting2.saturation, colorSetting2.brightness, colorSetting2.floatValue3);
            }
         } else {
            colorPickerState2 = ColorPickerState.resolve(colorSetting2.measure2(), colorSetting2.saturation, colorSetting2.brightness, colorSetting2.floatValue3);
         }

         return colorPickerState2;
      } else {
         throw new IllegalStateException("Setting is not a HueSetting");
      }
   }

   private static Collection<?> resolve19(Object object) {
      return (Collection<?>)(object instanceof Collection items2 ? items2 : List.of());
   }

   private static Object resolve20(Setting setting10, Object object) {
      if (setting10 == null || object == null) {
         return object;
      } else if (setting10 instanceof BooleanSetting) {
         return Boolean.TRUE.equals(object);
      } else if (setting10 instanceof NumberSetting) {
         return ((Number)object).doubleValue();
      } else if (setting10 instanceof ModeSetting || setting10 instanceof TextSetting) {
         return object.toString();
      } else if (setting10 instanceof MultiSelectSetting) {
         LinkedHashSet linkedHashSet3 = new LinkedHashSet();
         if (object instanceof Collection) {
            for (Object object5 : (Collection)object) {
               if (object5 != null) {
                  linkedHashSet3.add(object5.toString());
               }
            }
         }

         return linkedHashSet3;
      } else {
         return setting10 instanceof ColorSetting ? resolve22(object) : object;
      }
   }

   private static boolean check7(Setting setting11, Object object, Object object2) {
      if (object == object2) {
         return true;
      } else if (object == null || object2 == null) {
         return false;
      } else if (setting11 instanceof BooleanSetting || setting11 instanceof ModeSetting || setting11 instanceof TextSetting) {
         return Objects.equals(object, object2);
      } else if (setting11 instanceof NumberSetting) {
         return Math.abs(((Number)object).doubleValue() - ((Number)object2).doubleValue()) <= 1.0E-6;
      } else if (setting11 instanceof MultiSelectSetting) {
         if (!(object instanceof Collection items3 && object2 instanceof Collection items4)) {
            return false;
         } else {
            return items3.size() != items4.size() ? false : new LinkedHashSet<>(resolve21(items3)).equals(new LinkedHashSet<>(resolve21(items4)));
         }
      } else if (setting11 instanceof ColorSetting) {
         if (object instanceof ColorPickerState colorPickerState4 && object2 instanceof ColorPickerState colorPickerState5) {
            return colorPickerState4.equals(colorPickerState5);
         } else if (object instanceof Number number6 && object2 instanceof Number number7) {
            return number6.intValue() == number7.intValue();
         } else {
            return object instanceof String text12 && object2 instanceof String text13 ? text12.equalsIgnoreCase(text13) : false;
         }
      } else {
         return Objects.equals(object, object2);
      }
   }

   private static List<String> resolve21(Collection<?> collection) {
      ArrayList arrayList = new ArrayList(collection.size());

      for (Object object6 : collection) {
         if (object6 != null) {
            arrayList.add(object6.toString());
         }
      }

      return arrayList;
   }

   private static ColorPickerState resolve22(Object object) {
      if (object instanceof ColorPickerState colorPickerState6) {
         return ColorPickerState.resolve(colorPickerState6.getFloatValue(), colorPickerState6.getFloatValue2(), colorPickerState6.getFloatValue3(), colorPickerState6.getFloatValue4());
      } else if (object instanceof Number number8) {
         return ColorPickerState.resolve3(number8.intValue());
      } else if (object instanceof String text14) {
         try {
            String text15 = text14.startsWith("#") ? text14.substring(1) : text14;
            int intValue6 = (int)Long.parseUnsignedLong(text15, 16);
            int intValue7 = text15.length() > 6 ? intValue6 : 0xFF000000 | intValue6;
            return ColorPickerState.resolve3(intValue7);
         } catch (NumberFormatException numberFormatException3) {
            throw new IllegalArgumentException("Invalid colour string: " + text14, numberFormatException3);
         }
      } else {
         throw new IllegalArgumentException("Unsupported colour value type: " + object.getClass().getName());
      }
   }

   public static enum SettingBindingState {
      MODULE,
      SETTING;
   }
}
