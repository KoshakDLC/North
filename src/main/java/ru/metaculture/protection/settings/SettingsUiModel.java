package ru.metaculture.protection;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import net.minecraft.client.MinecraftClient;
import org.wild.module.api.Module;

public final class SettingsUiModel {
   static final SpringConfig SPRING_CONFIG = SpringConfig.resolve(2.2F, 0.72F);
   private static final SpringConfig SPRING_CONFIG_2 = SpringConfig.resolve(1.9F, 0.68F);
   private static final float FLOAT_VALUE = 16.0F;
   private static final float FLOAT_VALUE_2 = 8.0F;
   private static final float FLOAT_VALUE_3 = 8.0F;
   private static final float FLOAT_VALUE_4 = 0.001F;
   private static final long TIMESTAMP = 1200000000L;
   private final ClampedSpringAnimation clampedSpringAnimation;
   private final ClampedSpringAnimation clampedSpringAnimation2;
   private final ClampedSpringAnimation clampedSpringAnimation3;
   private final ClampedSpringAnimation clampedSpringAnimation4;
   private final ClampedSpringAnimation clampedSpringAnimation5;
   private final ClampedSpringAnimation clampedSpringAnimation6;
   private final ClampedSpringAnimation clampedSpringAnimation7;
   private final PopupPlacement popupPlacement;
   private SettingBinding settingBinding;
   private PopupContext.PopupContextBounds popupContextBounds;
   private PopupContext.PopupContextState popupContextState = new PopupContext.PopupContextState(0.0F, 0.0F, 0.0F, 0.0F);
   private SettingsWidget settingsWidget;
   private float floatValue;
   private float floatValue2;
   private float floatValue3 = Float.NaN;
   private float floatValue4 = Float.NaN;
   private float floatValue5 = 1.0F;
   private boolean flag;
   private boolean flag2;
   private boolean flag3;
   private long timestamp;
   private double doubleValue = -1.0;
   private double doubleValue2 = -1.0;

   private static ClampedSpringAnimation resolve() {
      ClampedSpringAnimation clampedSpringAnimation = new ClampedSpringAnimation(AnimationSystem.getINSTANCE(), SPRING_CONFIG_2, 0.0F, 0.0F, 1.0F, 5.0E-4F, 5.0E-4F);
      clampedSpringAnimation.setFloatEasing(FloatEasings.FLOAT_EASING_3);
      return clampedSpringAnimation;
   }

   SettingsUiModel(ClampedSpringAnimation clampedSpringAnimation2) {
      this.clampedSpringAnimation = Objects.requireNonNull(clampedSpringAnimation2, "visibilityAnimator");
      this.clampedSpringAnimation2 = resolve();
      this.clampedSpringAnimation3 = resolve();
      this.clampedSpringAnimation4 = resolve();
      this.clampedSpringAnimation5 = resolve();
      this.clampedSpringAnimation6 = resolve();
      this.clampedSpringAnimation7 = resolve();
      this.popupPlacement = new PopupPlacement(16.0F, 8.0F, 8.0F);
   }

   private void invoke() {
      this.settingsWidget = null;
      if (this.settingBinding != null && this.settingBinding.check2()) {
         Setting setting = this.settingBinding.getSetting();
         if (setting instanceof BooleanSetting booleanSetting) {
            this.settingsWidget = new BooleanValueAccessor(this.settingBinding, booleanSetting);
         } else if (setting instanceof NumberSetting numberSetting) {
            this.settingsWidget = new NumberValueAccessor(this.settingBinding, numberSetting);
         } else if (setting instanceof ModeSetting modeSetting) {
            this.settingsWidget = new StringValueAccessor(this.settingBinding, modeSetting);
         } else if (setting instanceof MultiSelectSetting multiSelectSetting) {
            this.settingsWidget = new MultiValueAccessor(this.settingBinding, multiSelectSetting);
         }
      }
   }

   private float measure() {
      return this.settingsWidget == null ? 0.0F : Math.max(0.0F, this.settingsWidget.measure());
   }

   private void invoke2() {
      if (this.settingsWidget != null && this.popupContextBounds != null) {
         PopupContext.PopupContextState popupContextState = this.popupContextBounds.valueContent();
         if (!(popupContextState.getFloatValue4() <= 0.0F)) {
            this.settingsWidget.invoke(popupContextState);
         }
      }
   }

   private boolean check(double d, double e, int i) {
      if (this.settingsWidget == null) {
         return false;
      } else if (this.settingsWidget.check3()) {
         return this.settingsWidget.check(d, e, i) ? true : true;
      } else {
         return this.popupContextBounds != null && this.popupContextBounds.valueContent().check(d, e) ? this.settingsWidget.check(d, e, i) : false;
      }
   }

   private boolean check2(double d, double e, double f, double g) {
      if (this.settingsWidget == null) {
         return false;
      } else if (this.settingsWidget.check3()) {
         return this.settingsWidget.check2(d, e, f, g) ? true : true;
      } else {
         return this.popupContextBounds != null && this.popupContextBounds.valueContent().check(d, e) ? this.settingsWidget.check2(d, e, f, g) : false;
      }
   }

   public static SettingsUiModel getSETTINGS_UI_MODEL() {
      return SettingsUiModel.SettingsUiModelState.SETTINGS_UI_MODEL;
   }

   public synchronized void invoke3(Module module, double d, double e, int i, int j) {
      Objects.requireNonNull(module, "module");
      SettingBinding settingBinding = SettingBinding.resolve(module);
      this.invoke6(settingBinding, d, e, i, j);
   }

   public synchronized void invoke4(Module module, Setting setting2, double d, double e, Object object) {
      MinecraftClient client = MinecraftClient.getInstance();
      int intValue = 1;
      int intValue2 = 1;
      if (client != null && client.getWindow() != null) {
         intValue = Math.max(1, client.getWindow().getFramebufferWidth());
         intValue2 = Math.max(1, client.getWindow().getFramebufferHeight());
      }

      this.invoke5(module, setting2, d, e, intValue, intValue2, object);
   }

   public synchronized void invoke5(Module module, Setting setting3, double d, double e, int i, int j, Object object) {
      Objects.requireNonNull(module, "module");
      Objects.requireNonNull(setting3, "setting");
      Object object2 = object != null ? object : resolve2(setting3);
      Object object3 = resolve3(setting3);
      byte byteValue = -1;
      int intValue3 = Math.max(1, i);
      int intValue4 = Math.max(1, j);
      SettingBinding settingBinding2 = SettingBinding.resolve2(module, setting3, object2, object3, byteValue, KeybindMode.TOGGLE);
      this.invoke6(settingBinding2, d, e, intValue3, intValue4);
   }

   private void invoke6(SettingBinding settingBinding3, double d, double e, int i, int j) {
      this.settingBinding = Objects.requireNonNull(settingBinding3, "newModel");
      this.flag = false;
      this.flag3 = false;
      this.timestamp = 0L;
      this.flag2 = false;
      KeyNameResolver.getINSTANCE().setFlag2(false);
      this.invoke();
      PopupContext.PopupContextBounds popupContextBounds = PopupContext.resolve(this.settingBinding, 0.0F, 0.0F, this.measure());
      float floatValue = popupContextBounds.bounds().getFloatValue3();
      float floatValue2 = popupContextBounds.bounds().getFloatValue4();
      this.floatValue3 = measure4(d);
      this.floatValue4 = measure4(e);
      this.floatValue5 = this.measure3();
      this.invoke7(floatValue, floatValue2, i, j);
      this.popupContextBounds = PopupContext.resolve(this.settingBinding, this.floatValue, this.floatValue2, this.measure());
      this.popupContextState = this.popupContextBounds.field();
      this.invoke2();
      this.invoke14();
      this.invoke19();
      this.clampedSpringAnimation7.invoke2(1.0F);
      this.clampedSpringAnimation.invoke2(1.0F);
   }

   private static Object resolve2(Setting setting4) {
      if (setting4 instanceof BooleanSetting) {
         return ((BooleanSetting)setting4).isEnabled();
      } else if (setting4 instanceof ModeSetting) {
         return ((ModeSetting)setting4).value;
      } else if (setting4 instanceof NumberSetting) {
         return (double)((NumberSetting)setting4).value;
      } else if (setting4 instanceof MultiSelectSetting) {
         return new LinkedHashSet<>(((MultiSelectSetting)setting4).selectedValues);
      } else {
         return setting4 instanceof ColorSetting colorSetting
            ? ColorPickerState.resolve(colorSetting.measure2(), colorSetting.saturation, colorSetting.brightness, colorSetting.floatValue3)
            : null;
      }
   }

   private static Object resolve3(Setting setting5) {
      if (setting5 instanceof BooleanSetting) {
         return Boolean.FALSE;
      } else if (setting5 instanceof ModeSetting) {
         return ((ModeSetting)setting5).value != null ? ((ModeSetting)setting5).value : "";
      } else if (setting5 instanceof NumberSetting) {
         return (double)((NumberSetting)setting5).value;
      } else if (setting5 instanceof MultiSelectSetting) {
         return new LinkedHashSet<>(((MultiSelectSetting)setting5).selectedValues);
      } else {
         return setting5 instanceof ColorSetting colorSetting2
            ? ColorPickerState.resolve(colorSetting2.measure2(), colorSetting2.saturation, colorSetting2.brightness, colorSetting2.floatValue3)
            : null;
      }
   }

   private static JsonElement resolve4(Setting setting6, Object object) {
      if (setting6 instanceof BooleanSetting) {
         return new JsonPrimitive(check3(setting6, object));
      } else if (setting6 instanceof NumberSetting) {
         return new JsonPrimitive(measure2((NumberSetting)setting6, object));
      } else if (setting6 instanceof ModeSetting) {
         return new JsonPrimitive(resolve6((ModeSetting)setting6, object));
      } else if (setting6 instanceof MultiSelectSetting) {
         return resolve8((MultiSelectSetting)setting6, object);
      } else {
         return (JsonElement)(setting6 instanceof ColorSetting
            ? resolve5(setting6, object)
            : new JsonPrimitive(object != null ? object.toString() : ""));
      }
   }

   private static boolean check3(Setting setting7, Object object) {
      if (object instanceof Boolean booleanValue) {
         return booleanValue;
      } else if (object instanceof Number number) {
         return number.doubleValue() != 0.0;
      } else {
         return setting7 instanceof BooleanSetting ? ((BooleanSetting)setting7).isEnabled() : false;
      }
   }

   private static JsonElement resolve5(Setting setting8, Object object) {
      if (setting8 instanceof ColorSetting colorSetting3) {
         ColorPickerState colorPickerState;
         if (object instanceof ColorPickerState colorPickerState2) {
            colorPickerState = colorPickerState2;
         } else if (object instanceof Number number2) {
            colorPickerState = ColorPickerState.resolve3(number2.intValue());
         } else if (object instanceof String text) {
            try {
               String text2 = text.startsWith("#") ? text.substring(1) : text;
               int intValue5 = (int)Long.parseUnsignedLong(text2, 16);
               int intValue6 = text2.length() > 6 ? intValue5 : 0xFF000000 | intValue5;
               colorPickerState = ColorPickerState.resolve3(intValue6);
            } catch (NumberFormatException numberFormatException) {
               colorPickerState = ColorPickerState.resolve(colorSetting3.measure2(), colorSetting3.saturation, colorSetting3.brightness, colorSetting3.floatValue3);
            }
         } else {
            colorPickerState = ColorPickerState.resolve(colorSetting3.measure2(), colorSetting3.saturation, colorSetting3.brightness, colorSetting3.floatValue3);
         }

         return new JsonPrimitive(colorPickerState.compute());
      } else {
         throw new IllegalStateException("Expected HueSetting for colour type");
      }
   }

   private static double measure2(NumberSetting numberSetting2, Object object) {
      double doubleValue;
      if (object instanceof Number number3) {
         doubleValue = number3.doubleValue();
      } else {
         doubleValue = numberSetting2.value;
      }

      if (!Double.isFinite(doubleValue)) {
         doubleValue = numberSetting2.value;
      }

      double doubleValue2 = numberSetting2.minimum;
      double doubleValue3 = numberSetting2.maximum;
      double doubleValue4 = numberSetting2.step;
      if (!Double.isFinite(doubleValue4) || doubleValue4 <= 0.0) {
         doubleValue4 = 1.0;
      }

      double doubleValue5 = Math.min(Math.max(doubleValue, doubleValue2), doubleValue3);
      double doubleValue6 = Math.round((doubleValue5 - doubleValue2) / doubleValue4);
      double doubleValue7 = doubleValue2 + doubleValue6 * doubleValue4;
      if (doubleValue7 < doubleValue2) {
         doubleValue7 = doubleValue2;
      } else if (doubleValue7 > doubleValue3) {
         doubleValue7 = doubleValue3;
      }

      return doubleValue7;
   }

   private static String resolve6(ModeSetting modeSetting2, Object object) {
      String text3 = object != null ? object.toString() : null;
      if (text3 == null || text3.isBlank() || modeSetting2.options != null && !modeSetting2.options.contains(text3)) {
         text3 = modeSetting2.value != null ? modeSetting2.value : "";
      }

      return text3;
   }

   private static String resolve7(Setting setting9, Object object) {
      Object object4 = object != null ? object : "";
      return object4 == null ? "" : object4.toString();
   }

   private static JsonElement resolve8(MultiSelectSetting multiSelectSetting2, Object object) {
      multiSelectSetting2.refreshOptions();
      Object object5;
      if (object instanceof Collection items) {
         object5 = items;
      } else {
         object5 = multiSelectSetting2.selectedValues != null ? multiSelectSetting2.selectedValues : List.of();
      }

      LinkedHashSet linkedHashSet = new LinkedHashSet();
      if (object5 != null) {
         for (Object object6 : (Iterable<Object>)object5) {
            if (object6 != null) {
               String text4 = object6.toString();
               if (multiSelectSetting2.options != null && multiSelectSetting2.options.contains(text4)) {
                  linkedHashSet.add(text4);
               }
            }
         }
      }

      if (linkedHashSet.isEmpty() && multiSelectSetting2.selectedValues != null) {
         linkedHashSet.addAll(multiSelectSetting2.selectedValues);
      }

      JsonArray jsonArray = new JsonArray();

      for (String text5 : (Iterable<String>)linkedHashSet) {
         jsonArray.add(text5);
      }

      return jsonArray;
   }

   private void invoke7(float f, float g, int i, int j) {
      PopupPlacement.PopupPlacementData popupPlacementData = this.popupPlacement.resolve2(this.floatValue3, this.floatValue4, f, g, i, j, this.floatValue5);
      this.floatValue = popupPlacementData.x();
      this.floatValue2 = popupPlacementData.y();
   }

   public synchronized boolean check4(double d, double e, int i) {
      if (!this.check10()) {
         return false;
      } else if (this.popupContextBounds == null) {
         return false;
      } else if (this.check(d, e, i)) {
         return true;
      } else {
         boolean flag = this.popupContextBounds.bounds().check(d, e);
         if (!flag) {
            this.invoke10();
            return true;
         } else if (i == 0) {
            if (this.popupContextState.check(d, e)) {
               if (this.flag) {
                  this.invoke17();
               } else {
                  this.invoke16();
               }

               return true;
            } else if (this.popupContextBounds.toggleButton().check(d, e)) {
               this.invoke18(KeybindMode.TOGGLE);
               return true;
            } else if (this.popupContextBounds.holdButton().check(d, e)) {
               this.invoke18(KeybindMode.HOLD);
               return true;
            } else {
               this.invoke10();
               return true;
            }
         } else if (i == 1) {
            this.invoke10();
            return true;
         } else {
            return flag;
         }
      }
   }

   public synchronized boolean check5(double d, double e, double f, double g) {
      if (!this.check10()) {
         return false;
      } else {
         return this.check2(d, e, f, g) ? true : true;
      }
   }

   public synchronized boolean check6(MouseUpdateEvent mouseUpdateEvent) {
      Objects.requireNonNull(mouseUpdateEvent, "event");
      return this.check8();
   }

   public synchronized boolean check7(int i, int j, int k, int l) {
      if (!this.check10()) {
         return false;
      } else if (!this.flag) {
         int intValue7 = compute();
         return intValue7 != -1 && i == intValue7 ? false : this.settingBinding != null;
      } else if (k != 1) {
         return true;
      } else if (i == 261 || i == 259 || i == 256) {
         this.settingBinding.invoke7();
         this.flag3 = false;
         this.timestamp = 0L;
         this.invoke17();
         this.invoke12();
         return true;
      } else if (i == -1) {
         return true;
      } else if (this.check11(i)) {
         this.flag3 = true;
         this.timestamp = System.nanoTime();
         return true;
      } else {
         this.settingBinding.invoke2(i);
         this.flag3 = false;
         this.timestamp = 0L;
         this.invoke17();
         this.invoke12();
         return true;
      }
   }

   private static int compute() {
      MenuModule menuModule = MenuModule.getInstance();
      if (menuModule == null) {
         return 344;
      } else {
         return menuModule.bindKey > 0 ? menuModule.bindKey : 344;
      }
   }

   public synchronized void invoke8(RenderManager renderManager, FontObject fontObject, int i, int j, float f) {
      Objects.requireNonNull(renderManager, "renderer");
      Objects.requireNonNull(fontObject, "defaultFont");
      if (this.settingBinding == null) {
         if (this.flag2 && this.clampedSpringAnimation.measure() <= 0.001F) {
            this.invoke20();
         }
      } else {
         this.invoke13(i, j);
         float floatValue3 = measure6(this.clampedSpringAnimation.measure());
         if (floatValue3 <= 0.001F && this.clampedSpringAnimation.getFloatValue6() <= 0.0F) {
            if (this.flag2) {
               this.invoke20();
            }
         } else {
            float floatValue4 = this.measure();
            if (this.popupContextBounds == null || Math.abs(this.popupContextBounds.valueBlock().getFloatValue4() - floatValue4) > 0.001F) {
               this.popupContextBounds = PopupContext.resolve(this.settingBinding, this.floatValue, this.floatValue2, floatValue4);
               this.invoke2();
            }

            if (this.settingsWidget != null) {
               this.settingsWidget.invoke2();
            }

            String text6;
            if (this.flag) {
               text6 = "Press a key";
            } else {
               int intValue8 = this.settingBinding.getIntValue();
               if (intValue8 == -1) {
                  text6 = "None";
               } else {
                  text6 = resolve10(intValue8);
               }
            }

            this.popupContextState = PopupContext.resolve2(this.popupContextBounds, renderManager, text6);
            boolean flag2 = this.popupContextState.check(this.doubleValue, this.doubleValue2);
            boolean flag3 = this.popupContextBounds.toggleButton().check(this.doubleValue, this.doubleValue2);
            boolean flag4 = this.popupContextBounds.holdButton().check(this.doubleValue, this.doubleValue2);
            this.invoke15(flag2, flag3, flag4);
            float floatValue5 = this.clampedSpringAnimation2.measure();
            float floatValue6 = this.clampedSpringAnimation3.measure();
            float floatValue7 = this.clampedSpringAnimation4.measure();
            boolean flag5 = this.flag3 && System.nanoTime() - this.timestamp <= 1200000000L;
            String text7 = "";
            if (flag5) {
               text7 = "";
            }

            float floatValue8 = this.clampedSpringAnimation5.measure();
            float floatValue9 = this.clampedSpringAnimation6.measure();
            float floatValue10 = this.clampedSpringAnimation7.measure() * f;
            PopupContext.PopupContextData popupContextData = new PopupContext.PopupContextData(
               floatValue3,
               floatValue10,
               this.flag,
               flag2,
               flag3,
               flag4,
               floatValue5,
               floatValue6,
               floatValue7,
               floatValue8,
               floatValue9,
               this.settingBinding.getKeybindMode(),
               text6,
               text7,
               this.popupContextBounds.valueBlock().getFloatValue4(),
               this.popupContextBounds.valueLabelBaseline(),
               this.popupContextState
            );
            PopupContext.invoke(renderManager, fontObject, this.settingBinding, this.popupContextBounds, popupContextData);
            if (this.settingsWidget != null) {
               this.settingsWidget.invoke4(renderManager, floatValue3, 1.0F);
               this.settingsWidget.invoke5(renderManager, floatValue3, 1.0F);
            }

            if (!flag5) {
               this.flag3 = false;
            }
         }
      }
   }

   public synchronized void invoke9(double d, double e) {
      this.doubleValue = d;
      this.doubleValue2 = e;
      if (this.settingsWidget != null) {
         this.settingsWidget.invoke3(d, e);
      }
   }

   public synchronized void invoke10() {
      if (this.settingBinding != null || !(this.clampedSpringAnimation.measure() <= 0.001F)) {
         if (this.settingBinding != null) {
            this.invoke12();
         }

         this.invoke17();
         this.invoke15(false, false, false);
         this.clampedSpringAnimation7.invoke2(0.0F);
         this.clampedSpringAnimation.invoke2(0.0F);
         this.flag2 = true;
      }
   }

   public synchronized void invoke11() {
      if (this.settingBinding != null || !(this.clampedSpringAnimation.measure() <= 0.001F)) {
         if (this.settingBinding != null) {
            this.invoke12();
         }

         this.invoke17();
         this.invoke15(false, false, false);
         this.clampedSpringAnimation7.invoke(0.0F);
         this.clampedSpringAnimation.invoke(0.0F);
         this.invoke20();
      }
   }

   public synchronized boolean check8() {
      return this.settingBinding != null ? true : this.clampedSpringAnimation.measure() > 0.001F;
   }

   public synchronized boolean check9() {
      return this.settingBinding != null;
   }

   public synchronized PopupContext.PopupContextBounds getPopupContextBounds() {
      return this.popupContextBounds;
   }

   public synchronized PopupContext.PopupContextData resolve9(float f) {
      if (this.settingBinding != null && this.popupContextBounds != null) {
         float floatValue11 = measure6(this.clampedSpringAnimation.measure());
         boolean flag6 = this.popupContextState.check(this.doubleValue, this.doubleValue2);
         boolean flag7 = this.popupContextBounds.toggleButton().check(this.doubleValue, this.doubleValue2);
         boolean flag8 = this.popupContextBounds.holdButton().check(this.doubleValue, this.doubleValue2);
         float floatValue12 = this.clampedSpringAnimation2.measure();
         float floatValue13 = this.clampedSpringAnimation3.measure();
         float floatValue14 = this.clampedSpringAnimation4.measure();
         String text8;
         if (this.flag) {
            text8 = "Press a key";
         } else {
            int intValue9 = this.settingBinding.getIntValue();
            if (intValue9 == -1) {
               text8 = "None";
            } else {
               text8 = resolve10(intValue9);
            }
         }

         boolean flag9 = this.flag3 && System.nanoTime() - this.timestamp <= 1200000000L;
         String text9 = "";
         if (flag9) {
            text9 = "";
         }

         float floatValue15 = this.clampedSpringAnimation5.measure();
         float floatValue16 = this.clampedSpringAnimation6.measure();
         float floatValue17 = this.clampedSpringAnimation7.measure() * f;
         return new PopupContext.PopupContextData(
            floatValue11,
            floatValue17,
            this.flag,
            flag6,
            flag7,
            flag8,
            floatValue12,
            floatValue13,
            floatValue14,
            floatValue15,
            floatValue16,
            this.settingBinding.getKeybindMode(),
            text8,
            text9,
            this.popupContextBounds.valueBlock().getFloatValue4(),
            this.popupContextBounds.valueLabelBaseline(),
            this.popupContextState
         );
      } else {
         return null;
      }
   }

   public synchronized void invoke12() {
      if (this.settingBinding != null) {
         if (this.settingBinding.check5()) {
            KeyNameResolver keyNameResolver = KeyNameResolver.getINSTANCE();
            if (this.settingBinding.check()) {
               Module module2 = this.settingBinding.getModule();
               if (module2 != null) {
                  keyNameResolver.invoke6(module2, this.settingBinding.getIntValue(), this.settingBinding.getKeybindMode());
               }
            } else if (this.settingBinding.check2()) {
               Module module3 = this.settingBinding.getModule();
               Setting setting10 = this.settingBinding.getSetting();
               if (module3 != null && setting10 != null) {
                  if (this.settingBinding.isFlag()) {
                     keyNameResolver.invoke8(module3.name, setting10.name);
                  } else {
                     Object object7 = this.settingBinding.resolve3();
                     if (object7 != null) {
                        invoke21(setting10, object7);
                        keyNameResolver.invoke7(module3, setting10, this.settingBinding.getKeybindMode(), this.settingBinding.getIntValue(), object7);
                     }
                  }
               }
            }

            this.settingBinding.invoke5();
         }
      }
   }

   private boolean check10() {
      return this.settingBinding != null ? true : this.clampedSpringAnimation.measure() > 0.001F && this.popupContextBounds != null;
   }

   private void invoke13(int i, int j) {
      if (this.settingBinding != null && this.popupContextBounds != null) {
         float floatValue18 = this.floatValue;
         float floatValue19 = this.floatValue2;
         this.floatValue5 = this.measure3();
         this.invoke7(this.popupContextBounds.bounds().getFloatValue3(), this.popupContextBounds.bounds().getFloatValue4(), i, j);
         if (this.floatValue != floatValue18 || this.floatValue2 != floatValue19) {
            this.popupContextBounds = PopupContext.resolve(this.settingBinding, this.floatValue, this.floatValue2, this.measure());
            this.popupContextState = this.popupContextBounds.field();
            this.invoke2();
         }
      }
   }

   private void invoke14() {
      this.clampedSpringAnimation2.invoke2(0.0F);
      this.clampedSpringAnimation3.invoke2(0.0F);
      this.clampedSpringAnimation4.invoke2(0.0F);
      this.clampedSpringAnimation7.invoke2(0.0F);
      this.clampedSpringAnimation2.invoke(0.0F);
      this.clampedSpringAnimation3.invoke(0.0F);
      this.clampedSpringAnimation4.invoke(0.0F);
      this.clampedSpringAnimation7.invoke(0.0F);
   }

   private void invoke15(boolean bl, boolean bl2, boolean bl3) {
      this.clampedSpringAnimation2.invoke2(bl ? 1.0F : 0.0F);
      this.clampedSpringAnimation3.invoke2(bl2 ? 1.0F : 0.0F);
      this.clampedSpringAnimation4.invoke2(bl3 ? 1.0F : 0.0F);
   }

   private void invoke16() {
      this.flag = true;
      this.flag3 = false;
      this.timestamp = 0L;
      KeyNameResolver.getINSTANCE().setFlag2(true);
   }

   private void invoke17() {
      if (this.flag) {
         this.flag = false;
         KeyNameResolver.getINSTANCE().setFlag2(false);
      }
   }

   private void invoke18(KeybindMode keybindMode) {
      if (this.settingBinding != null && keybindMode != null) {
         this.settingBinding.setKeybindMode(keybindMode);
         this.invoke19();
         this.invoke12();
      }
   }

   private void invoke19() {
      if (this.settingBinding != null) {
         this.clampedSpringAnimation5.invoke2(this.settingBinding.getKeybindMode() == KeybindMode.TOGGLE ? 1.0F : 0.0F);
         this.clampedSpringAnimation6.invoke2(this.settingBinding.getKeybindMode() == KeybindMode.HOLD ? 1.0F : 0.0F);
      }
   }

   private boolean check11(int i) {
      return false;
   }

   private void invoke20() {
      this.settingBinding = null;
      this.popupContextBounds = null;
      this.popupContextState = new PopupContext.PopupContextState(0.0F, 0.0F, 0.0F, 0.0F);
      this.settingsWidget = null;
      this.flag2 = false;
      this.flag3 = false;
      this.timestamp = 0L;
      this.floatValue3 = Float.NaN;
      this.floatValue4 = Float.NaN;
      this.floatValue5 = 1.0F;
      this.invoke14();
   }

   private float measure3() {
      float floatValue20 = 1.0F;
      if (!Float.isFinite(floatValue20)) {
         return 1.0F;
      } else {
         return floatValue20 <= 0.001F ? 1.0F : floatValue20;
      }
   }

   private static float measure4(double d) {
      if (!Double.isFinite(d)) {
         return Float.NaN;
      } else if (d > Float.MAX_VALUE) {
         return Float.MAX_VALUE;
      } else {
         return d < -Float.MAX_VALUE ? -Float.MAX_VALUE : (float)d;
      }
   }

   private static float measure5(float f, float g, float h) {
      if (f < g) {
         return g;
      } else {
         return f > h ? h : f;
      }
   }

   private static float measure6(float f) {
      if (f <= 0.0F) {
         return 0.0F;
      } else {
         return f >= 1.0F ? 1.0F : f;
      }
   }

   private static String resolve10(int i) {
      if (i == -1) {
         return "None";
      } else if (i >= 65 && i <= 90) {
         return String.valueOf((char)(65 + (i - 65)));
      } else {
         return i >= 48 && i <= 57 ? String.valueOf((char)(48 + (i - 48))) : "Key " + i;
      }
   }

   private static void invoke21(Setting setting11, Object object) {
      if (setting11 instanceof BooleanSetting && object instanceof Boolean) {
         ((BooleanSetting)setting11).setValue((Boolean)object);
      } else if (setting11 instanceof ModeSetting && object instanceof String) {
         ((ModeSetting)setting11).value = (String)object;
         if (((ModeSetting)setting11).options != null && ((ModeSetting)setting11).options.contains((String)object)) {
            ((ModeSetting)setting11).selectedIndex = ((ModeSetting)setting11).options.indexOf((String)object);
         }
      } else if (setting11 instanceof NumberSetting && object instanceof Number) {
         double doubleValue8 = ((Number)object).doubleValue();
         ((NumberSetting)setting11).value = (float)Math.max(
            (double)((NumberSetting)setting11).minimum, Math.min((double)((NumberSetting)setting11).maximum, doubleValue8)
         );
      } else if (setting11 instanceof MultiSelectSetting && object instanceof Collection) {
         ((MultiSelectSetting)setting11).selectedValues = new ArrayList<>((Collection<? extends String>)object);
      } else if (setting11 instanceof ColorSetting && object instanceof ColorPickerState colorPickerState3) {
         ColorSetting colorSetting4 = (ColorSetting)setting11;
         colorSetting4.invoke3(colorPickerState3.getFloatValue());
         colorSetting4.saturation = colorPickerState3.getFloatValue2();
         colorSetting4.brightness = colorPickerState3.getFloatValue3();
         colorSetting4.floatValue3 = colorPickerState3.getFloatValue4();
      }
   }

   static final class SettingsUiModelState {
      static final SettingsUiModel SETTINGS_UI_MODEL = new SettingsUiModel(resolve());

      private SettingsUiModelState() {
      }

      private static ClampedSpringAnimation resolve() {
         ClampedSpringAnimation clampedSpringAnimation3 = new ClampedSpringAnimation(AnimationSystem.getINSTANCE(), SettingsUiModel.SPRING_CONFIG, 0.0F, 0.0F, 1.0F, 5.0E-4F, 5.0E-4F);
         clampedSpringAnimation3.setFloatEasing(FloatEasings.FLOAT_EASING_3);
         return clampedSpringAnimation3;
      }
   }
}
