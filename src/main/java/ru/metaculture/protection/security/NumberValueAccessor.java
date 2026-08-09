package ru.metaculture.protection;

import java.util.Objects;

public final class NumberValueAccessor extends SettingValueAccessor<Double> {
   public NumberValueAccessor(SettingBinding settingBinding, NumberSetting numberSetting) {
      super(Objects.requireNonNull(settingBinding, "model"), resolve(settingBinding, Objects.requireNonNull(numberSetting, "setting")));
   }

   private static NumberSettingPopup resolve(SettingBinding settingBinding2, NumberSetting numberSetting2) {
      final double doubleValue = numberSetting2.value;
      SettingValue settingValue = resolve2(settingBinding2, doubleValue, new SettingValueAccessor.SettingValueAccessorContract<Double>() {
         public Double resolve2(SettingBinding settingBinding3) {
            return settingBinding3.resolve4() instanceof Number number ? number.doubleValue() : doubleValue;
         }

         public void resolve2(SettingBinding settingBinding4, Double double_) {
            double doubleValue2 = double_ != null ? double_ : doubleValue;
            settingBinding4.invoke8(doubleValue2);
         }
      });
      return new NumberSettingPopup(resolve(settingBinding2), getPOPUP_VALUE_EDITOR2(), numberSetting2, settingValue, "New Value");
   }
}
