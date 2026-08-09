package ru.metaculture.protection;

import java.util.Objects;

public final class StringValueAccessor extends SettingValueAccessor<String> {
   public StringValueAccessor(SettingBinding settingBinding, ModeSetting modeSetting) {
      super(Objects.requireNonNull(settingBinding, "model"), resolve(settingBinding, Objects.requireNonNull(modeSetting, "setting")));
   }

   private static ColorSettingPopup resolve(SettingBinding settingBinding2, ModeSetting modeSetting2) {
      final String text = modeSetting2.value != null ? modeSetting2.value : "";
      SettingValue settingValue = resolve2(settingBinding2, text, new SettingValueAccessor.SettingValueAccessorContract<String>() {
         public String resolve2(SettingBinding settingBinding3) {
            Object var2x = settingBinding3.resolve4();
            return var2x != null ? var2x.toString() : text;
         }

         public void resolve2(SettingBinding settingBinding4, String string) {
            String var3x = string != null ? string : text;
            settingBinding4.invoke8(var3x);
         }
      });
      return new ColorSettingPopup(resolve(settingBinding2), getPOPUP_VALUE_EDITOR2(), modeSetting2, settingValue, "New Value");
   }
}
