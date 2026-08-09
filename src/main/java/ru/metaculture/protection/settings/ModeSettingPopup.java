package ru.metaculture.protection;

import java.util.Objects;
import org.wild.module.api.Module;

public final class ModeSettingPopup implements SettingPopup {
   private static final float FLOAT_VALUE = 62.0F;
   private static final float FLOAT_VALUE_2 = 18.0F;
   private static final float FLOAT_VALUE_3 = 18.0F;
   private static final float FLOAT_VALUE_4 = 5.0F;
   private static final float FLOAT_VALUE_5 = 22.0F;
   private static final float FLOAT_VALUE_6 = 4.0F;
   private static final float FLOAT_VALUE_7 = 18.0F;
   private static final float FLOAT_VALUE_8 = 16.0F;
   private static final float FLOAT_VALUE_9 = 5.0F;
   private static final SpringConfig SPRING_CONFIG = SpringConfig.resolve(2.1F, 0.55F);
   private static final float FLOAT_VALUE_10 = 0.001F;
   private static final SpringConfig SPRING_CONFIG_2 = SpringConfig.resolve(1.4F, 0.7F);
   private final Module module;
   private final BooleanSetting booleanSetting;
   private final PopupValueEditor popupValueEditor;
   private final SettingValue<Boolean> settingValue;
   private final String text;
   private final ClampedSpringAnimation clampedSpringAnimation;
   private final ClampedSpringAnimation clampedSpringAnimation2;
   private ModeSettingPopup.ModeSettingPopupBounds modeSettingPopupBounds = ModeSettingPopup.ModeSettingPopupBounds.EMPTY;
   private ModeSettingPopup.ModeSettingPopupBounds modeSettingPopupBounds2 = ModeSettingPopup.ModeSettingPopupBounds.EMPTY;
   private float floatValue = 0.0F;
   private float floatValue2 = 0.0F;
   private boolean flag = false;

   public ModeSettingPopup(Module module, PopupValueEditor popupValueEditor, BooleanSetting booleanSetting, SettingValue<?> settingValue) {
      this(module, popupValueEditor, booleanSetting, settingValue, null);
   }

   public ModeSettingPopup(Module module, PopupValueEditor popupValueEditor2, BooleanSetting booleanSetting2, SettingValue<?> settingValue2, String string) {
      this.module = Objects.requireNonNull(module, "module");
      this.popupValueEditor = Objects.requireNonNull(popupValueEditor2, "popupContext");
      this.booleanSetting = Objects.requireNonNull(booleanSetting2, "setting");
      this.settingValue = (SettingValue<Boolean>)Objects.requireNonNull(settingValue2, "valueAccessor");
      this.text = resolve2(string);
      Object object = settingValue2.resolve4();
      boolean flag = object instanceof Boolean ? (Boolean)object : false;
      float floatValue = flag ? 1.0F : 0.0F;
      this.clampedSpringAnimation = new ClampedSpringAnimation(AnimationSystem.getINSTANCE(), SPRING_CONFIG, floatValue, 0.0F, 1.0F, 5.0E-4F, 5.0E-4F);
      this.clampedSpringAnimation.setFloatEasing(FloatEasings.FLOAT_EASING);
      this.clampedSpringAnimation2 = new ClampedSpringAnimation(AnimationSystem.getINSTANCE(), SPRING_CONFIG_2, floatValue, 0.0F, 1.0F, 5.0E-4F, 5.0E-4F);
      this.clampedSpringAnimation2.setFloatEasing(FloatEasings.FLOAT_EASING_3);
   }

   @Override
   public void invoke() {
      Object object2 = this.settingValue.resolve4();
      boolean flag2 = object2 instanceof Boolean ? (Boolean)object2 : false;
      this.clampedSpringAnimation.invoke2(flag2 ? 1.0F : 0.0F);
      this.invoke4();
   }

   @Override
   public void invoke2(float f, float g, float h) {
      this.modeSettingPopupBounds = new ModeSettingPopup.ModeSettingPopupBounds(f, g, h, 62.0F);
      float floatValue2 = f + h - 18.0F - 22.0F;
      float floatValue3 = g + 20.0F;
      this.modeSettingPopupBounds2 = new ModeSettingPopup.ModeSettingPopupBounds(floatValue2, floatValue3, 22.0F, 22.0F);
      this.floatValue = f + 18.0F;
      this.floatValue2 = g + 31.0F + 5.0F;
   }

   @Override
   public float measure() {
      return 62.0F;
   }

   @Override
   public void invoke3(RenderManager renderManager, float f, float g, float h) {
      float floatValue4 = f * (float)measure2(g);
      if (!(floatValue4 <= 0.0F)) {
         float floatValue5 = this.clampedSpringAnimation.measure();
         double doubleValue = measure2(floatValue4 * floatValue5);
         if (doubleValue > 0.001F) {
            renderManager.invoke5(
               this.modeSettingPopupBounds2.x + 1.0F,
               this.modeSettingPopupBounds2.y + 1.0F,
               this.modeSettingPopupBounds2.width - 2.0F,
               this.modeSettingPopupBounds2.height - 2.0F,
               4.0F,
               ColorInterpolator.compute3(UiAccentColor.compute(), doubleValue)
            );
         }

         double doubleValue2 = measure2(floatValue4);
         renderManager.invoke28(
            this.modeSettingPopupBounds2.x,
            this.modeSettingPopupBounds2.y,
            this.modeSettingPopupBounds2.width,
            this.modeSettingPopupBounds2.height,
            4.0F,
            ColorInterpolator.compute3(5197646, doubleValue2),
            1.0F
         );
         double doubleValue3 = measure2(floatValue5 * floatValue4);
         if (doubleValue3 > 0.001F) {
            renderManager.invoke70(
               FontRegistry.fontObject3,
               this.modeSettingPopupBounds2.centerX(),
               this.modeSettingPopupBounds2.centerY() + 5.0F + 3.0F,
               16.0F,
               "\ue5ca",
               ColorInterpolator.compute3(16777215, doubleValue3),
               "c"
            );
         }

         double doubleValue4 = measure2(floatValue4);
         float floatValue6 = this.clampedSpringAnimation2.measure();
         int intValue = ColorInterpolator.compute3(8947848, doubleValue4);
         int intValue2 = ColorInterpolator.compute3(16777215, doubleValue4);
         int intValue3 = ColorInterpolator.compute10(intValue, intValue2, floatValue6);
         renderManager.invoke70(FontRegistry.fontObject4, this.floatValue, this.floatValue2, 18.0F, this.resolve(), intValue3, "l");
      }
   }

   @Override
   public boolean check3(double d, double e, int i) {
      if (!this.modeSettingPopupBounds.contains(d, e)) {
         return false;
      } else if (i == 2) {
         Object object3 = this.settingValue.resolve4();
         Boolean booleanValue = object3 instanceof Boolean ? (Boolean)object3 : false;
         this.popupValueEditor.openForSetting(this.module, this.booleanSetting, d, e, booleanValue);
         return true;
      } else if (i != 0) {
         return false;
      } else {
         Object object4 = this.settingValue.resolve4();
         boolean flag3 = object4 instanceof Boolean ? (Boolean)object4 : false;
         boolean flag4 = !flag3;
         this.settingValue.invoke10(flag4);
         this.clampedSpringAnimation.invoke2(flag4 ? 1.0F : 0.0F);
         return true;
      }
   }

   @Override
   public Setting getMultiSelectSetting() {
      return this.booleanSetting;
   }

   @Override
   public boolean check5() {
      return true;
   }

   @Override
   public void invoke5(double d, double e) {
      this.flag = this.modeSettingPopupBounds.contains(d, e);
      this.invoke4();
   }

   private void invoke4() {
      Object object5 = this.settingValue.resolve4();
      boolean flag5 = object5 instanceof Boolean ? (Boolean)object5 : false;
      float floatValue7;
      if (flag5) {
         floatValue7 = 1.0F;
      } else if (this.flag) {
         floatValue7 = 0.5F;
      } else {
         floatValue7 = 0.0F;
      }

      this.clampedSpringAnimation2.invoke2(floatValue7);
   }

   private String resolve() {
      return this.text != null ? this.text : this.booleanSetting.name;
   }

   private static String resolve2(String string) {
      if (string == null) {
         return null;
      } else {
         String text = string.trim();
         return text.isEmpty() ? null : text;
      }
   }

   private static double measure2(double d) {
      if (d <= 0.0) {
         return 0.0;
      } else {
         return d >= 1.0 ? 1.0 : d;
      }
   }

   record ModeSettingPopupBounds(float x, float y, float width, float height) {
      static final ModeSettingPopup.ModeSettingPopupBounds EMPTY = new ModeSettingPopup.ModeSettingPopupBounds(0.0F, 0.0F, 0.0F, 0.0F);

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
