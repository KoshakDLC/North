package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public interface SettingValue<T> {
   T resolve4();

   void invoke10(T object);

   T resolve5();

   void invoke11();

   static SettingValue<?> resolve(Setting setting) {
      Objects.requireNonNull(setting, "setting");
      if (setting instanceof BooleanSetting booleanSetting) {
         return new SettingValue<Boolean>() {
            public Boolean resolve4() {
               return booleanSetting.isEnabled();
            }

            public void invoke10(Boolean boolean_) {
               if (boolean_ != null) {
                  booleanSetting.setValue(boolean_);
               }
            }

            public Boolean resolve5() {
               return false;
            }

            @Override
            public void invoke11() {
               booleanSetting.setValue(false);
            }
         };
      } else if (setting instanceof NumberSetting numberSetting) {
         return new SettingValue<Double>() {
            public Double resolve4() {
               return (double)numberSetting.getValue();
            }

            public void invoke10(Double double_) {
               if (double_ != null) {
                  numberSetting.value = double_.floatValue();
               }
            }

            public Double resolve5() {
               return (double)numberSetting.minimum;
            }

            @Override
            public void invoke11() {
               numberSetting.value = numberSetting.minimum;
            }
         };
      } else if (setting instanceof ModeSetting modeSetting) {
         return new SettingValue<String>() {
            public String resolve4() {
               return modeSetting.getValue();
            }

            public void invoke10(String string) {
               if (modeSetting.options.contains(string)) {
                  modeSetting.value = string;
                  modeSetting.selectedIndex = modeSetting.options.indexOf(string);
               }
            }

            public String resolve5() {
               return modeSetting.options.isEmpty() ? "" : modeSetting.options.get(0);
            }

            @Override
            public void invoke11() {
               if (!modeSetting.options.isEmpty()) {
                  modeSetting.value = modeSetting.options.get(0);
                  modeSetting.selectedIndex = 0;
               }
            }
         };
      } else if (setting instanceof MultiSelectSetting multiSelectSetting) {
         return new SettingValue<Set<String>>() {
            public Set<String> resolve4() {
               return new LinkedHashSet<>(multiSelectSetting.selectedValues != null ? multiSelectSetting.selectedValues : List.of());
            }

            public void invoke10(Set<String> set) {
               if (set != null) {
                  multiSelectSetting.selectedValues = new ArrayList<>(set);
               } else {
                  multiSelectSetting.selectedValues = new ArrayList<>();
               }
            }

            public Set<String> resolve5() {
               return new LinkedHashSet<>();
            }

            @Override
            public void invoke11() {
               multiSelectSetting.selectedValues = new ArrayList<>();
            }
         };
      } else {
         return setting instanceof ColorSetting colorSetting ? new SettingValue<ColorPickerState>() {
            public ColorPickerState resolve4() {
               return ColorPickerState.resolve(colorSetting.measure2(), colorSetting.saturation, colorSetting.brightness, colorSetting.floatValue3);
            }

            public void invoke10(ColorPickerState colorPickerState) {
               if (colorPickerState != null) {
                  colorSetting.invoke3(colorPickerState.getFloatValue());
                  colorSetting.saturation = colorPickerState.getFloatValue2();
                  colorSetting.brightness = colorPickerState.getFloatValue3();
                  colorSetting.floatValue3 = colorPickerState.getFloatValue4();
               }
            }

            public ColorPickerState resolve5() {
               return ColorPickerState.resolve(0.0F, 1.0F, 1.0F, 1.0F);
            }

            @Override
            public void invoke11() {
               colorSetting.hueValue = 0.0F;
               colorSetting.saturation = 1.0F;
               colorSetting.brightness = 1.0F;
               colorSetting.floatValue3 = 1.0F;
            }
         } : new SettingValue<Object>() {
            @Override
            public Object resolve4() {
               return null;
            }

            @Override
            public void invoke10(Object object) {
            }

            @Override
            public Object resolve5() {
               return null;
            }

            @Override
            public void invoke11() {
            }
         };
      }
   }
}
