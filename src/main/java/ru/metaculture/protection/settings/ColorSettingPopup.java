package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.wild.module.api.Module;

public final class ColorSettingPopup implements SettingPopup {
   private static final float FLOAT_VALUE = 100.0F;
   private static final float FLOAT_VALUE_2 = 18.0F;
   private static final float FLOAT_VALUE_3 = 18.0F;
   private static final float FLOAT_VALUE_4 = 17.0F;
   private static final float FLOAT_VALUE_5 = 8.0F;
   private static final float FLOAT_VALUE_6 = 298.0F;
   private static final float FLOAT_VALUE_7 = 38.0F;
   private static final float FLOAT_VALUE_8 = 6.0F;
   private static final float FLOAT_VALUE_9 = 18.0F;
   private static final int INT_VALUE = -14408668;
   private static final float FLOAT_VALUE_10 = 16.0F;
   private static final float FLOAT_VALUE_11 = 16.0F;
   private static final float FLOAT_VALUE_12 = 6.0F;
   private static final int INT_VALUE_2 = -7829368;
   private static final int INT_VALUE_3 = -1;
   private static final float FLOAT_VALUE_13 = 40.0F;
   private static final float FLOAT_VALUE_14 = 24.0F;
   private static final int INT_VALUE_4 = 58131;
   private static final SpringConfig SPRING_CONFIG = SpringConfig.resolve(1.4F, 0.7F);
   private static final SpringConfig SPRING_CONFIG_2 = SpringConfig.resolve(2.1F, 0.55F);
   private static final float FLOAT_VALUE_15 = 38.0F;
   private static final int INT_VALUE_5 = -14408668;
   private static final int INT_VALUE_6 = -13750738;
   private static final float FLOAT_VALUE_16 = 18.0F;
   private static final float FLOAT_VALUE_17 = 6.0F;
   private static final int INT_VALUE_7 = -1;
   private static final float FLOAT_VALUE_18 = 1.0E-4F;
   private static final float FLOAT_VALUE_19 = 0.001F;
   private final Module module;
   private final ModeSetting modeSetting;
   private final PopupValueEditor popupValueEditor;
   private final SettingValue<String> settingValue;
   private final String text;
   private final List<String> items;
   private final ClampedSpringAnimation clampedSpringAnimation;
   private final ClampedSpringAnimation clampedSpringAnimation2;
   private final List<ClampedSpringAnimation> items2;
   private static float floatValue = Float.NaN;
   private ColorSettingPopup.ColorSettingPopupBounds colorSettingPopupBounds = ColorSettingPopup.ColorSettingPopupBounds.EMPTY;
   private ColorSettingPopup.ColorSettingPopupBounds colorSettingPopupBounds2 = ColorSettingPopup.ColorSettingPopupBounds.EMPTY;
   private ColorSettingPopup.ColorSettingPopupBounds colorSettingPopupBounds3 = ColorSettingPopup.ColorSettingPopupBounds.EMPTY;
   private ColorSettingPopup.ColorSettingPopupBounds colorSettingPopupBounds4 = ColorSettingPopup.ColorSettingPopupBounds.EMPTY;
   private final List<ColorSettingPopup.ColorSettingPopupBounds> items3 = new ArrayList<>();
   private float floatValue2 = 0.0F;
   private float floatValue3 = 0.0F;
   private boolean flag = false;
   private boolean flag2 = false;
   private int intValue = -1;

   public ColorSettingPopup(Module module, PopupValueEditor popupValueEditor, ModeSetting modeSetting, SettingValue<String> settingValue) {
      this(module, popupValueEditor, modeSetting, settingValue, null);
   }

   public ColorSettingPopup(Module module, PopupValueEditor popupValueEditor2, ModeSetting modeSetting2, SettingValue<String> settingValue2, String string) {
      this.module = Objects.requireNonNull(module, "module");
      this.popupValueEditor = Objects.requireNonNull(popupValueEditor2, "popupContext");
      this.modeSetting = Objects.requireNonNull(modeSetting2, "setting");
      this.settingValue = Objects.requireNonNull(settingValue2, "valueAccessor");
      this.text = resolve2(string);
      this.items = new ArrayList<>(modeSetting2.options != null ? modeSetting2.options : List.of());
      this.clampedSpringAnimation = new ClampedSpringAnimation(AnimationSystem.getINSTANCE(), SPRING_CONFIG, 0.0F, 0.0F, 1.0F, 5.0E-4F, 5.0E-4F);
      this.clampedSpringAnimation.setFloatEasing(FloatEasings.FLOAT_EASING_3);
      this.clampedSpringAnimation2 = new ClampedSpringAnimation(AnimationSystem.getINSTANCE(), SPRING_CONFIG_2, 0.0F, 0.0F, 1.0F, 5.0E-4F, 5.0E-4F);
      this.clampedSpringAnimation2.setFloatEasing(FloatEasings.FLOAT_EASING_3);
      this.items2 = new ArrayList<>();
      this.invoke12();
   }

   @Override
   public void invoke() {
      List items = this.modeSetting.options != null ? this.modeSetting.options : List.of();
      if (items.size() != this.items.size() || !this.items.equals(items)) {
         this.items.clear();
         this.items.addAll(items);
         this.invoke12();
         this.invoke11();
      }

      this.clampedSpringAnimation.invoke2(this.flag2 ? 1.0F : (this.flag2 ? 0.0F : (this.flag ? 0.5F : 0.0F)));
      this.clampedSpringAnimation2.invoke2(this.flag2 ? 1.0F : 0.0F);

      for (int intValue = 0; intValue < this.items2.size(); intValue++) {
         float floatValue = this.flag2 && intValue == this.intValue ? 1.0F : 0.0F;
         this.items2.get(intValue).invoke2(floatValue);
      }

      if (!this.flag2) {
         this.intValue = -1;
      }
   }

   @Override
   public void invoke2(float f, float g, float h) {
      this.colorSettingPopupBounds = new ColorSettingPopup.ColorSettingPopupBounds(f, g, h, 100.0F);
      this.floatValue2 = f + 18.0F;
      this.floatValue3 = g + 17.0F + 18.0F;
      float floatValue2 = f + 18.0F;
      float floatValue3 = this.floatValue3 + 8.0F;
      this.colorSettingPopupBounds2 = new ColorSettingPopup.ColorSettingPopupBounds(floatValue2, floatValue3, 298.0F, 38.0F);
      this.colorSettingPopupBounds3 = new ColorSettingPopup.ColorSettingPopupBounds(floatValue2 + 298.0F - 40.0F, floatValue3, 40.0F, 38.0F);
      this.invoke11();
   }

   @Override
   public float measure() {
      return 100.0F;
   }

   @Override
   public void invoke3(RenderManager renderManager, float f, float g, float h) {
      float floatValue4 = measure2(g);
      float floatValue5 = f * floatValue4;
      if (!(floatValue5 <= 1.0E-4F)) {
         float floatValue6 = measure2(this.clampedSpringAnimation.measure());
         int intValue2 = compute2(-7829368, floatValue5);
         int intValue3 = compute2(-1, floatValue5);
         int intValue4 = ColorInterpolator.compute10(intValue2, intValue3, floatValue6);
         renderManager.invoke70(FontRegistry.fontObject4, this.floatValue2, this.floatValue3 - 4.0F, 18.0F, this.resolve(), intValue4, "l");
         int intValue5 = compute2(-14408668, floatValue5);
         renderManager.invoke5(this.colorSettingPopupBounds2.x, this.colorSettingPopupBounds2.y, this.colorSettingPopupBounds2.width, this.colorSettingPopupBounds2.height, 6.0F, intValue5);
         int intValue6 = compute2(UiAccentColor.compute(), floatValue5);
         renderManager.invoke6(
            this.colorSettingPopupBounds3.x, this.colorSettingPopupBounds3.y, this.colorSettingPopupBounds3.width, this.colorSettingPopupBounds3.height, 0.0F, 6.0F, 6.0F, 0.0F, intValue6
         );
         float floatValue7 = this.colorSettingPopupBounds2.centerY() + 6.0F;
         float floatValue8 = this.colorSettingPopupBounds2.x + 16.0F;
         String text = this.settingValue.resolve4();
         if (text == null) {
            text = "";
         }

         int intValue7 = compute2(-7829368, floatValue5);
         int intValue8 = compute2(-1, floatValue5);
         int intValue9 = ColorInterpolator.compute10(intValue7, intValue8, floatValue6);
         renderManager.invoke70(FontRegistry.fontObject4, floatValue8, floatValue7, 16.0F, text, intValue9, "l");
         float floatValue9 = measure2(this.clampedSpringAnimation2.measure()) * 180.0F;
         float floatValue10 = this.colorSettingPopupBounds3.centerX();
         float floatValue11 = this.colorSettingPopupBounds3.centerY();
         float floatValue12 = measure3();
         float floatValue13 = floatValue11 + floatValue12;
         renderManager.invoke56(floatValue10, floatValue13);
         renderManager.invoke56(0.0F, -floatValue12);
         renderManager.invoke54(floatValue9);
         renderManager.invoke56(0.0F, floatValue12);
         renderManager.invoke56(-floatValue10, -floatValue13);
         boolean flag = false ;

         try {
            flag = true;
            renderManager.invoke70(FontRegistry.fontObject3, floatValue10, floatValue13, 24.0F, "\ue313", compute2(-1, floatValue5), "c");
            flag = false;
         } finally {
            if (flag) {
               renderManager.invoke57();
               renderManager.invoke57();
               renderManager.invoke55();
               renderManager.invoke57();
               renderManager.invoke57();
            }
         }

         renderManager.invoke57();
         renderManager.invoke57();
         renderManager.invoke55();
         renderManager.invoke57();
         renderManager.invoke57();
      }
   }

   @Override
   public void invoke4(RenderManager renderManager2, float f, float g) {
      float floatValue14 = measure2(this.clampedSpringAnimation2.measure());
      if (!(floatValue14 <= 0.001F)) {
         if (!this.items.isEmpty() && !(this.colorSettingPopupBounds4.width <= 0.0F) && !(this.colorSettingPopupBounds4.height <= 0.0F)) {
            float floatValue15 = measure2(g);
            float floatValue16 = f * floatValue15 * floatValue14;
            if (!(floatValue16 <= 1.0E-4F)) {
               int intValue10 = -14408668;
               renderManager2.invoke63(1.0F, floatValue14, this.colorSettingPopupBounds4.x, this.colorSettingPopupBounds4.y);

               try {
                  renderManager2.invoke6(
                     this.colorSettingPopupBounds4.x, this.colorSettingPopupBounds4.y, this.colorSettingPopupBounds4.width, this.colorSettingPopupBounds4.height, 6.0F, 6.0F, 6.0F, 6.0F, intValue10
                  );
                  String text2 = this.settingValue.resolve4();
                  if (text2 == null) {
                     text2 = "";
                  }

                  for (int intValue11 = 0; intValue11 < this.items3.size(); intValue11++) {
                     ColorSettingPopup.ColorSettingPopupBounds colorSettingPopupBounds = this.items3.get(intValue11);
                     String text3 = this.items.get(intValue11);
                     boolean flag2 = Objects.equals(text3, text2);
                     float floatValue17 = intValue11 < this.items2.size() ? measure2(this.items2.get(intValue11).measure()) : 0.0F;
                     if (floatValue17 > 0.001F) {
                        int intValue12 = compute2(-13750738, floatValue17 * floatValue16);
                        float floatValue18 = intValue11 == 0 ? 6.0F : 0.0F;
                        float floatValue19 = intValue11 == 0 ? 6.0F : 0.0F;
                        float floatValue20 = intValue11 == this.items3.size() - 1 ? 6.0F : 0.0F;
                        float floatValue21 = intValue11 == this.items3.size() - 1 ? 6.0F : 0.0F;
                        renderManager2.invoke6(colorSettingPopupBounds.x, colorSettingPopupBounds.y, colorSettingPopupBounds.width, colorSettingPopupBounds.height, floatValue18, floatValue19, floatValue20, floatValue21, intValue12);
                     }

                     float floatValue22 = colorSettingPopupBounds.x + 16.0F;
                     float floatValue23 = colorSettingPopupBounds.centerY() + 6.0F;
                     int intValue13 = compute2(-7829368, floatValue16);
                     int intValue14 = compute2(-1, floatValue16);
                     float floatValue24;
                     if (flag2) {
                        floatValue24 = 1.0F;
                     } else {
                        floatValue24 = floatValue17 * 0.7F;
                     }

                     int intValue15 = ColorInterpolator.compute10(intValue13, intValue14, floatValue24);
                     renderManager2.invoke70(FontRegistry.fontObject4, floatValue22, floatValue23, 16.0F, text3, intValue15, "l");
                     if (Objects.equals(text3, text2)) {
                        float floatValue25 = colorSettingPopupBounds.x + colorSettingPopupBounds.width - 16.0F + 2.0F;
                        float floatValue26 = colorSettingPopupBounds.centerY() + 6.0F + 3.0F;
                        renderManager2.invoke70(FontRegistry.fontObject3, floatValue25, floatValue26, 18.0F, "\ue5ca", compute2(-1, floatValue16), "r");
                     }
                  }
               } finally {
                  renderManager2.invoke64();
               }
            }
         }
      }
   }

   @Override
   public boolean check() {
      return (this.flag2 || this.clampedSpringAnimation2.measure() > 0.001F) && this.colorSettingPopupBounds4.width > 0.0F && this.colorSettingPopupBounds4.height > 0.0F;
   }

   @Override
   public boolean check2(double d, double e, int i) {
      if (!this.check()) {
         return false;
      } else if (!this.flag2) {
         return false;
      } else if (i != 0) {
         this.invoke8();
         return true;
      } else if (this.colorSettingPopupBounds4.contains(d, e)) {
         int intValue16 = this.compute(e);
         if (intValue16 >= 0 && intValue16 < this.items.size()) {
            this.invoke10(intValue16);
         }

         this.invoke8();
         return true;
      } else if (!this.colorSettingPopupBounds2.contains(d, e) && !this.colorSettingPopupBounds3.contains(d, e)) {
         this.invoke8();
         return true;
      } else {
         this.invoke8();
         return true;
      }
   }

   @Override
   public boolean check3(double d, double e, int i) {
      boolean flag3 = this.colorSettingPopupBounds2.contains(d, e) || this.colorSettingPopupBounds3.contains(d, e);
      if (i == 2) {
         if (!flag3) {
            return false;
         } else {
            this.invoke8();
            String text4 = this.settingValue.resolve4();
            String text5 = text4 != null ? text4.toString() : "";
            this.popupValueEditor.openForSetting(this.module, this.modeSetting, d, e, text5);
            return true;
         }
      } else if (i != 0) {
         return false;
      } else if (this.flag2) {
         return this.check2(d, e, i);
      } else if (flag3) {
         this.invoke7();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean check4(double d, double e, double f, double g) {
      return this.check();
   }

   @Override
   public void invoke5(double d, double e) {
      boolean flag4 = this.colorSettingPopupBounds2.contains(d, e) || this.colorSettingPopupBounds3.contains(d, e);
      boolean flag5 = this.flag2 && this.colorSettingPopupBounds4.contains(d, e);
      this.flag = this.flag2 ? false : flag4;
      if (this.flag2) {
         if (flag5) {
            this.intValue = this.compute(e);
         } else {
            this.intValue = -1;
         }
      }
   }

   @Override
   public void invoke6() {
      this.invoke9();
   }

   @Override
   public Setting getMultiSelectSetting() {
      return this.modeSetting;
   }

   @Override
   public boolean check5() {
      return true;
   }

   private void invoke7() {
      this.flag2 = true;
      this.clampedSpringAnimation2.invoke2(1.0F);
      this.clampedSpringAnimation.invoke2(1.0F);
   }

   private void invoke8() {
      this.flag2 = false;
      this.clampedSpringAnimation2.invoke2(0.0F);
      this.intValue = -1;
   }

   private void invoke9() {
      this.flag2 = false;
      this.clampedSpringAnimation2.invoke(0.0F);
      this.intValue = -1;
   }

   private void invoke10(int i) {
      if (i >= 0 && i < this.items.size()) {
         String text6 = this.items.get(i);
         String text7 = this.settingValue.resolve4();
         if (!Objects.equals(text6, text7)) {
            this.settingValue.invoke10(text6);
         }
      }
   }

   private void invoke11() {
      this.items3.clear();
      if (this.items.isEmpty()) {
         this.colorSettingPopupBounds4 = ColorSettingPopup.ColorSettingPopupBounds.EMPTY;
      } else {
         float floatValue27 = this.colorSettingPopupBounds2.x;
         float floatValue28 = this.colorSettingPopupBounds2.y + this.colorSettingPopupBounds2.height + 6.0F;
         float floatValue29 = this.colorSettingPopupBounds2.width;
         float floatValue30 = 38.0F * this.items.size();
         this.colorSettingPopupBounds4 = new ColorSettingPopup.ColorSettingPopupBounds(floatValue27, floatValue28, floatValue29, floatValue30);
         float floatValue31 = floatValue28;

         for (int intValue17 = 0; intValue17 < this.items.size(); intValue17++) {
            this.items3.add(new ColorSettingPopup.ColorSettingPopupBounds(floatValue27, floatValue31, floatValue29, 38.0F));
            floatValue31 += 38.0F;
         }
      }
   }

   private int compute(double d) {
      if (!(d < this.colorSettingPopupBounds4.y) && !(d > this.colorSettingPopupBounds4.y + this.colorSettingPopupBounds4.height)) {
         double doubleValue = d - this.colorSettingPopupBounds4.y;
         if (doubleValue < 0.0) {
            return -1;
         } else {
            int intValue18 = (int)(doubleValue / 38.0);
            return intValue18 >= 0 && intValue18 < this.items.size() ? intValue18 : -1;
         }
      } else {
         return -1;
      }
   }

   private String resolve() {
      return this.text != null ? this.text : this.modeSetting.name;
   }

   private static String resolve2(String string) {
      if (string == null) {
         return null;
      } else {
         String text8 = string.trim();
         return text8.isEmpty() ? null : text8;
      }
   }

   private static float measure2(float f) {
      if (f <= 0.0F) {
         return 0.0F;
      } else {
         return f >= 1.0F ? 1.0F : f;
      }
   }

   private void invoke12() {
      this.items2.clear();

      for (int intValue19 = 0; intValue19 < this.items.size(); intValue19++) {
         ClampedSpringAnimation clampedSpringAnimation = new ClampedSpringAnimation(AnimationSystem.getINSTANCE(), SPRING_CONFIG, 0.0F, 0.0F, 1.0F, 5.0E-4F, 5.0E-4F);
         clampedSpringAnimation.setFloatEasing(FloatEasings.FLOAT_EASING_3);
         this.items2.add(clampedSpringAnimation);
      }
   }

   private static float measure3() {
      if (Float.isNaN(floatValue)) {
         float floatValue32 = FontRegistry.measure(FontRegistry.fontObject3, 58131, 24.0F);
         floatValue = floatValue32;
      }

      return floatValue;
   }

   private static int compute2(int i, float f) {
      int intValue20 = i >>> 24 & 0xFF;
      int intValue21 = Math.round(intValue20 * f);
      int intValue22 = i & 16777215;
      return intValue21 << 24 | intValue22;
   }

   record ColorSettingPopupBounds(float x, float y, float width, float height) {
      static final ColorSettingPopup.ColorSettingPopupBounds EMPTY = new ColorSettingPopup.ColorSettingPopupBounds(0.0F, 0.0F, 0.0F, 0.0F);

      boolean contains(double d, double e) {
         return d >= this.x && d <= this.x + this.width && e >= this.y && e <= this.y + this.height;
      }

      float centerX() {
         return this.x + this.width * 0.5F;
      }

      float centerY() {
         return this.y + this.height * 0.5F;
      }
   }
}
