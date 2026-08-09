package ru.metaculture.protection;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Objects;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;
import org.wild.module.api.Module;

public final class NumberSettingPopup implements SettingPopup {
   private static final float FLOAT_VALUE = 80.0F;
   private static final float FLOAT_VALUE_2 = 18.0F;
   private static final float FLOAT_VALUE_3 = 18.0F;
   private static final float FLOAT_VALUE_4 = 17.0F;
   private static final float FLOAT_VALUE_5 = 18.0F;
   private static final float FLOAT_VALUE_6 = 20.0F;
   private static final float FLOAT_VALUE_7 = 298.0F;
   private static final float FLOAT_VALUE_8 = 6.0F;
   private static final float FLOAT_VALUE_9 = 15.0F;
   private static final float FLOAT_VALUE_10 = 3.0F;
   private static final float FLOAT_VALUE_11 = 18.0F;
   private static final float FLOAT_VALUE_12 = 12.0F;
   private static final float FLOAT_VALUE_13 = 1.35F;
   private static final int INT_VALUE = -14606047;
   private static final int INT_VALUE_2 = -2500135;
   private static final int INT_VALUE_3 = -7829368;
   private static final int INT_VALUE_4 = -1;
   private static final SpringConfig SPRING_CONFIG = SpringConfig.resolve(2.1F, 0.55F);
   private static final SpringConfig SPRING_CONFIG_2 = SpringConfig.resolve(1.4F, 0.7F);
   private static final SpringConfig SPRING_CONFIG_3 = SpringConfig.resolve(8.0F, 0.8F);
   private static final SpringConfig SPRING_CONFIG_4 = SpringConfig.resolve(1.8F, 0.65F);
   private static final float FLOAT_VALUE_14 = 5.0E-4F;
   private static final float FLOAT_VALUE_15 = 5.0E-4F;
   private static final float FLOAT_VALUE_16 = 1.0E-4F;
   private static final double DOUBLE_VALUE = 1.0E-4;
   private static final float FLOAT_VALUE_17 = 0.001F;
   private final Module module;
   private final NumberSetting numberSetting;
   private final PopupValueEditor popupValueEditor;
   private final SettingValue<Double> settingValue;
   private final String text;
   private final ClampedSpringAnimation clampedSpringAnimation;
   private final ClampedSpringAnimation clampedSpringAnimation2;
   private final ClampedSpringAnimation clampedSpringAnimation3;
   private final ClampedSpringAnimation clampedSpringAnimation4;
   private NumberSettingPopup.NumberSettingPopupBounds numberSettingPopupBounds = NumberSettingPopup.NumberSettingPopupBounds.EMPTY;
   private NumberSettingPopup.NumberSettingPopupBounds numberSettingPopupBounds2 = NumberSettingPopup.NumberSettingPopupBounds.EMPTY;
   private NumberSettingPopup.NumberSettingPopupBounds numberSettingPopupBounds3 = NumberSettingPopup.NumberSettingPopupBounds.EMPTY;
   private boolean flag = false;
   private double doubleValue;
   private int intValue;
   private boolean flag2 = false;

   public NumberSettingPopup(Module module, PopupValueEditor popupValueEditor, NumberSetting numberSetting, SettingValue<Double> settingValue) {
      this(module, popupValueEditor, numberSetting, settingValue, null);
   }

   public NumberSettingPopup(Module module, PopupValueEditor popupValueEditor2, NumberSetting numberSetting2, SettingValue<Double> settingValue2, String string) {
      this.module = Objects.requireNonNull(module, "module");
      this.popupValueEditor = Objects.requireNonNull(popupValueEditor2, "popupContext");
      this.numberSetting = Objects.requireNonNull(numberSetting2, "setting");
      this.settingValue = Objects.requireNonNull(settingValue2, "valueAccessor");
      this.text = resolve2(string);
      this.clampedSpringAnimation = new ClampedSpringAnimation(AnimationSystem.getINSTANCE(), SPRING_CONFIG, 0.0F, 0.0F, 1.0F, 5.0E-4F, 5.0E-4F);
      this.clampedSpringAnimation.setFloatEasing(FloatEasings.FLOAT_EASING_3);
      this.clampedSpringAnimation2 = new ClampedSpringAnimation(AnimationSystem.getINSTANCE(), SPRING_CONFIG_2, 0.0F, 0.0F, 1.0F, 5.0E-4F, 5.0E-4F);
      this.clampedSpringAnimation2.setFloatEasing(FloatEasings.FLOAT_EASING_3);
      Double doubleValue = (Double)settingValue2.resolve4();
      double doubleValue2 = doubleValue != null ? doubleValue : numberSetting2.value;
      this.doubleValue = doubleValue2;
      this.intValue = compute(numberSetting2.step);
      float floatValue = this.measure4(this.doubleValue);
      this.clampedSpringAnimation3 = new ClampedSpringAnimation(AnimationSystem.getINSTANCE(), SPRING_CONFIG_3, floatValue, 0.0F, 1.0F, 5.0E-4F, 5.0E-4F);
      this.clampedSpringAnimation4 = new ClampedSpringAnimation(AnimationSystem.getINSTANCE(), SPRING_CONFIG_4, 1.0F, 0.0F, 1.0F, 5.0E-4F, 5.0E-4F);
      this.clampedSpringAnimation4.setFloatEasing(FloatEasings.FLOAT_EASING_3);
   }

   @Override
   public void invoke() {
      Double doubleValue3 = this.settingValue.resolve4();
      double doubleValue4 = doubleValue3 != null ? doubleValue3 : this.numberSetting.value;
      this.doubleValue = doubleValue4;
      this.clampedSpringAnimation.invoke2(this.flag ? 1.0F : 0.0F);
      this.clampedSpringAnimation3.invoke2(this.measure3());
      this.invoke8();
   }

   @Override
   public void invoke7(boolean bl) {
      float floatValue2 = bl ? 1.0F : 0.0F;
      this.clampedSpringAnimation4.invoke2(floatValue2);
      if (!bl) {
         this.flag = false;
         this.flag2 = false;
         this.clampedSpringAnimation.invoke2(0.0F);
         this.invoke8();
      }
   }

   @Override
   public void invoke2(float f, float g, float h) {
      float floatValue3 = Math.max(0.0F, this.measure2());
      this.numberSettingPopupBounds = new NumberSettingPopup.NumberSettingPopupBounds(f, g, h, 80.0F);
      this.numberSettingPopupBounds2 = new NumberSettingPopup.NumberSettingPopupBounds(f, g, h, floatValue3);
      float floatValue4 = f + 18.0F;
      float floatValue5 = g + 17.0F + 18.0F + 15.0F;
      this.numberSettingPopupBounds3 = new NumberSettingPopup.NumberSettingPopupBounds(floatValue4, floatValue5, 298.0F, 6.0F);
   }

   @Override
   public float measure() {
      return 80.0F;
   }

   @Override
   public float measure2() {
      return 80.0F * measure5(this.clampedSpringAnimation4.measure());
   }

   @Override
   public void invoke3(RenderManager renderManager, float f, float g, float h) {
      float floatValue6 = measure5(this.clampedSpringAnimation4.measure());
      if (!(floatValue6 <= 0.001F)) {
         float floatValue7 = f * measure5(g) * floatValue6;
         if (!(floatValue7 <= 1.0E-4F)) {
            renderManager.invoke63(1.0F, floatValue6, this.numberSettingPopupBounds.x, this.numberSettingPopupBounds.y);

            try {
               this.invoke4();
               float floatValue8 = measure5(this.clampedSpringAnimation2.measure());
               int intValue = ColorInterpolator.compute10(-7829368, -1, floatValue8);
               int intValue2 = compute2(intValue, floatValue7);
               float floatValue9 = this.numberSettingPopupBounds.x + 18.0F;
               float floatValue10 = this.numberSettingPopupBounds.y + 17.0F + 18.0F;
               renderManager.invoke70(FontRegistry.fontObject4, floatValue9, floatValue10, 18.0F, this.resolve(), intValue2, "l");
               float floatValue11 = this.numberSettingPopupBounds.y + 20.0F + 18.0F;
               float floatValue12 = this.numberSettingPopupBounds.x + this.numberSettingPopupBounds.width - 18.0F;
               renderManager.invoke70(FontRegistry.fontObject4, floatValue12, floatValue11, 18.0F, this.resolve3(), intValue2, "r");
               int intValue3 = compute2(-14606047, floatValue7);
               renderManager.invoke5(this.numberSettingPopupBounds3.x, this.numberSettingPopupBounds3.y, this.numberSettingPopupBounds3.width, this.numberSettingPopupBounds3.height, 3.0F, intValue3);
               float floatValue13 = measure5(this.clampedSpringAnimation3.measure());
               float floatValue14 = this.numberSettingPopupBounds3.width * floatValue13;
               if (floatValue14 > 0.0F) {
                  int intValue4 = compute2(UiAccentColor.compute(), floatValue7);
                  float floatValue15 = floatValue14 >= this.numberSettingPopupBounds3.width - 0.01F ? 3.0F : 0.0F;
                  renderManager.invoke6(this.numberSettingPopupBounds3.x, this.numberSettingPopupBounds3.y, floatValue14, this.numberSettingPopupBounds3.height, 3.0F, floatValue15, floatValue15, 3.0F, intValue4);
               }

               float floatValue16 = this.numberSettingPopupBounds3.x + floatValue14;
               float floatValue17 = this.numberSettingPopupBounds3.y + this.numberSettingPopupBounds3.height * 0.5F;
               float floatValue18 = 1.0F + measure5(this.clampedSpringAnimation.measure()) * 0.35000002F;
               float floatValue19 = 12.0F * floatValue18;
               float floatValue20 = floatValue19 * 0.5F;
               int intValue5 = compute2(-2500135, floatValue7);
               renderManager.invoke39(floatValue16, floatValue17, floatValue20, 0.0F, 1.0F, intValue5);
            } finally {
               renderManager.invoke64();
            }
         }
      }
   }

   @Override
   public boolean check3(double d, double e, int i) {
      if (!this.check2() || !this.numberSettingPopupBounds2.contains(d, e)) {
         return false;
      } else if (i == 2) {
         Double doubleValue5 = this.settingValue.resolve4();
         Double doubleValue6 = doubleValue5 != null ? doubleValue5 : this.numberSetting.value;
         this.popupValueEditor.openForSetting(this.module, this.numberSetting, d, e, doubleValue6);
         return true;
      } else if (i != 0) {
         return false;
      } else {
         this.flag = true;
         this.clampedSpringAnimation.invoke2(1.0F);
         this.invoke8();
         return true;
      }
   }

   @Override
   public Setting getMultiSelectSetting() {
      return this.numberSetting;
   }

   @Override
   public boolean check5() {
      return true;
   }

   @Override
   public boolean check6(double d, double e, double f, double g) {
      if (this.check2() && this.numberSettingPopupBounds2.contains(d, e)) {
         if (Math.abs(g) <= 1.0E-4) {
            return false;
         } else {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client != null && client.getWindow() != null) {
               long longValue = client.getWindow().getHandle();
               if (GLFW.glfwGetKey(longValue, 341) != 1) {
                  return false;
               } else if (!this.check(g)) {
                  return false;
               } else {
                  this.clampedSpringAnimation.invoke2(1.0F);
                  this.invoke8();
                  return true;
               }
            } else {
               return false;
            }
         }
      } else {
         return false;
      }
   }

   @Override
   public void invoke5(double d, double e) {
      if (!this.check2()) {
         this.flag2 = false;
         this.invoke8();
      } else {
         this.flag2 = this.numberSettingPopupBounds2.contains(d, e);
         this.invoke8();
      }
   }

   private void invoke4() {
      if (this.flag) {
         if (!this.check2()) {
            this.flag = false;
            this.clampedSpringAnimation.invoke2(0.0F);
            this.invoke8();
         } else {
            MinecraftClient client2 = MinecraftClient.getInstance();
            if (client2 != null && client2.getWindow() != null) {
               long longValue2 = client2.getWindow().getHandle();
               if (GLFW.glfwGetMouseButton(longValue2, 0) != 1) {
                  this.flag = false;
                  this.clampedSpringAnimation.invoke2(0.0F);
                  this.invoke8();
               } else {
                  double[] doubleValues = new double[1];
                  double[] doubleValues2 = new double[1];
                  GLFW.glfwGetCursorPos(longValue2, doubleValues, doubleValues2);
                  this.invoke6(measure7(doubleValues[0], client2));
               }
            } else {
               this.flag = false;
               this.clampedSpringAnimation.invoke2(0.0F);
               this.invoke8();
            }
         }
      }
   }

   private void invoke6(double d) {
      if (!(this.numberSettingPopupBounds3.width <= 0.0F) && this.check2()) {
         double doubleValue7 = Math.min(Math.max(d, (double)this.numberSettingPopupBounds3.x), (double)(this.numberSettingPopupBounds3.x + this.numberSettingPopupBounds3.width));
         double doubleValue8 = (doubleValue7 - this.numberSettingPopupBounds3.x) / this.numberSettingPopupBounds3.width;
         double doubleValue9 = this.numberSetting.minimum;
         double doubleValue10 = this.numberSetting.maximum;
         if (!(doubleValue10 <= doubleValue9)) {
            double doubleValue11 = doubleValue9 + (doubleValue10 - doubleValue9) * doubleValue8;
            if (!(Math.abs(doubleValue11 - this.doubleValue) <= 1.0E-4F)) {
               this.settingValue.invoke10(doubleValue11);
               Double doubleValue12 = this.settingValue.resolve4();
               this.doubleValue = doubleValue12 != null ? doubleValue12 : this.numberSetting.value;
               this.clampedSpringAnimation3.invoke2(this.measure3());
            }
         }
      }
   }

   private boolean check(double d) {
      double doubleValue13 = this.numberSetting.step;
      if (doubleValue13 <= 0.0) {
         return false;
      } else {
         double doubleValue14 = this.numberSetting.minimum;
         double doubleValue15 = this.numberSetting.maximum;
         double doubleValue16 = Math.signum(d);
         if (doubleValue16 == 0.0) {
            return false;
         } else {
            double doubleValue17 = Math.ceil(Math.abs(d));
            if (doubleValue17 <= 0.0) {
               doubleValue17 = 1.0;
            }

            double doubleValue18 = this.doubleValue + doubleValue13 * doubleValue17 * doubleValue16;
            double doubleValue19 = Math.min(Math.max(doubleValue18, doubleValue14), doubleValue15);
            if (Math.abs(doubleValue19 - this.doubleValue) <= 1.0E-4F) {
               return false;
            } else {
               this.settingValue.invoke10(doubleValue19);
               Double doubleValue20 = this.settingValue.resolve4();
               double doubleValue21 = doubleValue20 instanceof Number ? doubleValue20.doubleValue() : this.numberSetting.value;
               this.doubleValue = doubleValue21;
               this.invoke8();
               this.clampedSpringAnimation3.invoke2(this.measure3());
               return true;
            }
         }
      }
   }

   private float measure3() {
      return this.measure4(this.doubleValue);
   }

   private float measure4(double d) {
      double doubleValue22 = this.numberSetting.minimum;
      double doubleValue23 = this.numberSetting.maximum;
      if (doubleValue23 <= doubleValue22) {
         return 0.0F;
      } else {
         double doubleValue24 = Math.min(Math.max(d, doubleValue22), doubleValue23);
         return (float)((doubleValue24 - doubleValue22) / (doubleValue23 - doubleValue22));
      }
   }

   private String resolve() {
      return this.text != null ? this.text : this.numberSetting.name;
   }

   private static String resolve2(String string) {
      if (string == null) {
         return null;
      } else {
         String text = string.trim();
         return text.isEmpty() ? null : text;
      }
   }

   private String resolve3() {
      return this.intValue <= 0
         ? String.format(Locale.US, "%.0f", this.doubleValue)
         : String.format(Locale.US, "%1$." + this.intValue + "f", this.doubleValue);
   }

   private static int compute(double d) {
      BigDecimal bigDecimal = BigDecimal.valueOf(d);
      int intValue6 = bigDecimal.scale();
      if (intValue6 <= 0) {
         return 0;
      } else {
         BigDecimal bigDecimal2 = bigDecimal.stripTrailingZeros();
         return Math.max(0, bigDecimal2.scale());
      }
   }

   private void invoke8() {
      float floatValue21;
      if (this.flag) {
         floatValue21 = 1.0F;
      } else if (this.flag2 && this.check2()) {
         floatValue21 = 0.5F;
      } else {
         floatValue21 = 0.0F;
      }

      this.clampedSpringAnimation2.invoke2(floatValue21);
   }

   private boolean check2() {
      return measure5(this.clampedSpringAnimation4.measure()) > 0.001F;
   }

   private static float measure5(float f) {
      if (f <= 0.0F) {
         return 0.0F;
      } else {
         return f >= 1.0F ? 1.0F : f;
      }
   }

   private static int compute2(int i, float f) {
      int intValue7 = i >>> 24 & 0xFF;
      int intValue8 = Math.round(intValue7 * f);
      int intValue9 = i & 16777215;
      return intValue8 << 24 | intValue9;
   }

   private static double measure6(double d, int i, float f) {
      if (i <= 0) {
         return d;
      } else if (Float.isFinite(f) && !(Math.abs(f - 1.0F) <= 0.001F)) {
         double doubleValue25 = i * 0.5;
         return doubleValue25 + (d - doubleValue25) / f;
      } else {
         return d;
      }
   }

   private static double measure7(double d, MinecraftClient minecraftClient) {
      if (minecraftClient != null && minecraftClient.getWindow() != null) {
         int intValue10 = minecraftClient.getWindow().getFramebufferWidth();
         if (intValue10 <= 0) {
            return d;
         } else {
            float floatValue22 = 1.0F;
            return Float.isFinite(floatValue22) && !(Math.abs(floatValue22) <= 1.0E-4F) ? measure6(d, intValue10, floatValue22) : d;
         }
      } else {
         return d;
      }
   }

   record NumberSettingPopupBounds(float x, float y, float width, float height) {
      static final NumberSettingPopup.NumberSettingPopupBounds EMPTY = new NumberSettingPopup.NumberSettingPopupBounds(0.0F, 0.0F, 0.0F, 0.0F);

      boolean contains(double d, double e) {
         return d >= this.x && d <= this.x + this.width && e >= this.y && e <= this.y + this.height;
      }
   }
}
