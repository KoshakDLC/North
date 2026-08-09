package ru.metaculture.protection;

import java.util.Objects;

public final class BooleanValueAccessor extends SettingValueAccessor<Boolean> {
   public BooleanValueAccessor(SettingBinding settingBinding, BooleanSetting booleanSetting) {
      super(Objects.requireNonNull(settingBinding, "model"), resolve(settingBinding, Objects.requireNonNull(booleanSetting, "setting")));
   }

   private static ModeSettingPopup resolve(SettingBinding settingBinding2, BooleanSetting booleanSetting2) {
      Boolean booleanValue = Boolean.FALSE;
      SettingValue settingValue = resolve2(settingBinding2, booleanValue, new SettingValueAccessor.SettingValueAccessorContract<Boolean>() {
         public Boolean resolve2(SettingBinding settingBinding3) {
            Object var2x = settingBinding3.resolve4();
            return Boolean.TRUE.equals(var2x);
         }

         public void resolve2(SettingBinding settingBinding4, Boolean boolean_) {
            settingBinding4.invoke8(Boolean.TRUE.equals(boolean_));
         }
      });
      return new ModeSettingPopup(resolve(settingBinding2), getPOPUP_VALUE_EDITOR2(), booleanSetting2, settingValue, "New Value");
   }
}
