package ru.metaculture.protection;

import java.util.Objects;
import org.wild.module.api.Module;

abstract class SettingValueAccessor<T> implements SettingsWidget {
   private static final PopupValueEditor POPUP_VALUE_EDITOR = (module, setting, d, e, object) -> {};
   protected static final String NEW_VALUE = "New Value";
   private final SettingBinding settingBinding;
   private final SettingPopup settingPopup;
   private PopupContext.PopupContextState popupContextState = new PopupContext.PopupContextState(0.0F, 0.0F, 0.0F, 0.0F);

   SettingValueAccessor(SettingBinding settingBinding, SettingPopup settingPopup) {
      this.settingBinding = Objects.requireNonNull(settingBinding, "model");
      this.settingPopup = Objects.requireNonNull(settingPopup, "widget");
   }

   protected final SettingBinding getSettingBinding() {
      return this.settingBinding;
   }

   protected final SettingPopup getSettingPopup() {
      return this.settingPopup;
   }

   protected final PopupValueEditor getPOPUP_VALUE_EDITOR() {
      return POPUP_VALUE_EDITOR;
   }

   protected static PopupValueEditor getPOPUP_VALUE_EDITOR2() {
      return POPUP_VALUE_EDITOR;
   }

   protected static Module resolve(SettingBinding settingBinding2) {
      Module module2 = settingBinding2.getModule();
      if (module2 == null) {
         throw new IllegalStateException("Bind popup model is missing module context");
      } else {
         return module2;
      }
   }

   @Override
   public void invoke(PopupContext.PopupContextState popupContextState) {
      Objects.requireNonNull(popupContextState, "area");
      this.popupContextState = popupContextState;
      this.settingPopup.invoke2(popupContextState.getFloatValue(), popupContextState.getFloatValue2(), popupContextState.getFloatValue3());
   }

   @Override
   public float measure() {
      return this.settingPopup.measure();
   }

   @Override
   public void invoke2() {
      this.settingPopup.invoke();
   }

   @Override
   public void invoke3(double d, double e) {
      this.settingPopup.invoke5(d, e);
   }

   @Override
   public boolean check(double d, double e, int i) {
      if (this.settingPopup.check()) {
         return this.settingPopup.check2(d, e, i) ? true : true;
      } else {
         return !this.popupContextState.check(d, e) ? false : this.settingPopup.check3(d, e, i);
      }
   }

   @Override
   public boolean check2(double d, double e, double f, double g) {
      if (this.settingPopup.check()) {
         return this.settingPopup.check4(d, e, f, g) ? true : true;
      } else {
         return !this.popupContextState.check(d, e) ? false : this.settingPopup.check6(d, e, f, g);
      }
   }

   @Override
   public void invoke4(RenderManager renderManager, float f, float g) {
      this.settingPopup.invoke3(renderManager, f, g, 0.0F);
   }

   @Override
   public void invoke5(RenderManager renderManager2, float f, float g) {
      this.settingPopup.invoke4(renderManager2, f, g);
   }

   @Override
   public boolean check3() {
      return this.settingPopup.check();
   }

   protected static <V> SettingValue<V> resolve2(SettingBinding settingBinding3, V object, SettingValueAccessor.SettingValueAccessorContract<V> settingValueAccessorContract) {
      Objects.requireNonNull(settingBinding3, "model");
      Objects.requireNonNull(settingValueAccessorContract, "adapter");
      return new SettingValue<V>() {
         @Override
         public V resolve4() {
            return (V)settingValueAccessorContract.resolve2(settingBinding3);
         }

         @Override
         public void invoke10(V object) {
            settingValueAccessorContract.invoke9(settingBinding3, object);
         }

         @Override
         public V resolve5() {
            return (V)object;
         }

         @Override
         public void invoke11() {
         }
      };
   }

   @FunctionalInterface
   protected interface SettingValueAccessorContract<V> {
      V resolve2(SettingBinding settingBinding4);

      default void invoke9(SettingBinding settingBinding5, V object) {
         settingBinding5.invoke8(object);
      }
   }
}
