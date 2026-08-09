package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.wild.module.api.Module;

public final class MultiSelectPopup implements SettingPopup {
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
   private static final int INT_VALUE_4 = -11184811;
   private static final float FLOAT_VALUE_13 = 40.0F;
   private static final float FLOAT_VALUE_14 = 24.0F;
   private static final int INT_VALUE_5 = 58131;
   private static final SpringConfig SPRING_CONFIG = SpringConfig.resolve(1.4F, 0.7F);
   private static final SpringConfig SPRING_CONFIG_2 = SpringConfig.resolve(2.1F, 0.55F);
   private static final SpringConfig SPRING_CONFIG_3 = SpringConfig.resolve(2.2F, 0.6F);
   private static final float FLOAT_VALUE_15 = 38.0F;
   private static final int INT_VALUE_6 = -14408668;
   private static final int INT_VALUE_7 = -13750738;
   private static final float FLOAT_VALUE_16 = 18.0F;
   private static final float FLOAT_VALUE_17 = 6.0F;
   private static final int INT_VALUE_8 = -1;
   private static final float FLOAT_VALUE_18 = 1.0E-4F;
   private static final float FLOAT_VALUE_19 = 0.001F;
   private static final float FLOAT_VALUE_20 = 12.0F;
   private static final float FLOAT_VALUE_21 = 10.0F;
   private static final String SELECT_VALUES = "Select values";
   private static final String TEXT = ", ";
   private final Module module;
   private final MultiSelectSetting multiSelectSetting;
   private final PopupValueEditor popupValueEditor;
   private final SettingValue<Set<String>> settingValue;
   private final String text;
   private final List<String> items;
   private final ClampedSpringAnimation clampedSpringAnimation;
   private final ClampedSpringAnimation clampedSpringAnimation2;
   private final List<ClampedSpringAnimation> items2;
   private final List<ClampedSpringAnimation> items3;
   private final List<ClampedSpringAnimation> items4;
   private final List<Integer> items5;
   private static float floatValue = Float.NaN;
   private MultiSelectPopup.MultiSelectPopupBounds multiSelectPopupBounds = MultiSelectPopup.MultiSelectPopupBounds.EMPTY;
   private MultiSelectPopup.MultiSelectPopupBounds multiSelectPopupBounds2 = MultiSelectPopup.MultiSelectPopupBounds.EMPTY;
   private MultiSelectPopup.MultiSelectPopupBounds multiSelectPopupBounds3 = MultiSelectPopup.MultiSelectPopupBounds.EMPTY;
   private MultiSelectPopup.MultiSelectPopupBounds multiSelectPopupBounds4 = MultiSelectPopup.MultiSelectPopupBounds.EMPTY;
   private final List<MultiSelectPopup.MultiSelectPopupBounds> items6 = new ArrayList<>();
   private float floatValue2 = 0.0F;
   private float floatValue3 = 0.0F;
   private boolean flag = false;
   private boolean flag2 = false;
   private int intValue = -1;
   private boolean flag3 = false;
   private final LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>();

   public MultiSelectPopup(Module module, PopupValueEditor popupValueEditor, MultiSelectSetting multiSelectSetting, SettingValue<Set<String>> settingValue) {
      this(module, popupValueEditor, multiSelectSetting, settingValue, null);
   }

   public MultiSelectPopup(Module module, PopupValueEditor popupValueEditor2, MultiSelectSetting multiSelectSetting2, SettingValue<Set<String>> settingValue2, String string) {
      this.module = Objects.requireNonNull(module, "module");
      this.popupValueEditor = Objects.requireNonNull(popupValueEditor2, "popupContext");
      this.multiSelectSetting = Objects.requireNonNull(multiSelectSetting2, "setting");
      this.settingValue = Objects.requireNonNull(settingValue2, "valueAccessor");
      this.text = resolve2(string);
      this.items = new ArrayList<>(multiSelectSetting2.refreshOptions());
      this.clampedSpringAnimation = new ClampedSpringAnimation(AnimationSystem.getINSTANCE(), SPRING_CONFIG, 0.0F, 0.0F, 1.0F, 5.0E-4F, 5.0E-4F);
      this.clampedSpringAnimation.setFloatEasing(FloatEasings.FLOAT_EASING_3);
      this.clampedSpringAnimation2 = new ClampedSpringAnimation(AnimationSystem.getINSTANCE(), SPRING_CONFIG_2, 0.0F, 0.0F, 1.0F, 5.0E-4F, 5.0E-4F);
      this.clampedSpringAnimation2.setFloatEasing(FloatEasings.FLOAT_EASING_3);
      this.items2 = new ArrayList<>();
      this.items3 = new ArrayList<>();
      this.items4 = new ArrayList<>();
      this.items5 = new ArrayList<>();
      this.invoke12();
   }

   @Override
   public void invoke() {
      List items = this.multiSelectSetting.refreshOptions();
      if (items.size() != this.items.size() || !this.items.equals(items)) {
         this.items.clear();
         this.items.addAll(items);
         this.invoke12();
         this.invoke11();
      }

      this.clampedSpringAnimation.invoke2(this.flag2 ? 1.0F : (this.flag ? 0.5F : 0.0F));
      this.clampedSpringAnimation2.invoke2(this.flag2 ? 1.0F : 0.0F);
      this.linkedHashSet.clear();
      Set values = this.settingValue.resolve4();
      if (values != null) {
         this.linkedHashSet.addAll(values);
      }

      this.flag3 = !this.linkedHashSet.isEmpty();

      for (int intValue = 0; intValue < this.items2.size(); intValue++) {
         float floatValue = this.flag2 && intValue == this.intValue ? 1.0F : 0.0F;
         this.items2.get(intValue).invoke2(floatValue);
      }

      for (int intValue2 = 0; intValue2 < this.items3.size(); intValue2++) {
         String text = this.items.get(intValue2);
         float floatValue2 = this.linkedHashSet.contains(text) ? 1.0F : 0.0F;
         this.items3.get(intValue2).invoke2(floatValue2);
      }

      boolean flag = false;

      for (int intValue3 = 0; intValue3 < this.items4.size(); intValue3++) {
         ClampedSpringAnimation clampedSpringAnimation = this.items3.get(intValue3);
         ClampedSpringAnimation clampedSpringAnimation2 = this.items4.get(intValue3);
         float floatValue3 = measure3(clampedSpringAnimation.measure());
         float floatValue4 = clampedSpringAnimation.getFloatValue6();
         boolean flag2 = floatValue3 > 1.0E-4F;
         boolean flag3 = floatValue4 > 1.0E-4F;
         boolean flag4 = Math.abs(floatValue3 - floatValue4) > 1.0E-4F;
         boolean flag5 = flag && (flag2 || flag4 && flag3);
         clampedSpringAnimation2.invoke2(flag5 ? 1.0F : 0.0F);
         boolean flag6 = flag2 || flag3;
         flag = flag || flag6;
      }

      if (!this.flag2) {
         this.intValue = -1;
      }
   }

   @Override
   public void invoke2(float f, float g, float h) {
      this.multiSelectPopupBounds = new MultiSelectPopup.MultiSelectPopupBounds(f, g, h, 100.0F);
      this.floatValue2 = f + 18.0F;
      this.floatValue3 = g + 17.0F + 18.0F;
      float floatValue5 = f + 18.0F;
      float floatValue6 = this.floatValue3 + 8.0F;
      this.multiSelectPopupBounds2 = new MultiSelectPopup.MultiSelectPopupBounds(floatValue5, floatValue6, 298.0F, 38.0F);
      this.multiSelectPopupBounds3 = new MultiSelectPopup.MultiSelectPopupBounds(floatValue5 + 298.0F - 40.0F, floatValue6, 40.0F, 38.0F);
      this.invoke11();
   }

   @Override
   public float measure() {
      return 100.0F;
   }

   @Override
   public void invoke3(RenderManager renderManager, float f, float g, float h) {
      float floatValue7 = measure3(g);
      float floatValue8 = f * floatValue7;
      if (!(floatValue8 <= 1.0E-4F)) {
         float floatValue9 = measure3(this.clampedSpringAnimation.measure());
         int intValue4 = compute2(-7829368, floatValue8);
         int intValue5 = compute2(-1, floatValue8);
         int intValue6 = ColorInterpolator.compute10(intValue4, intValue5, floatValue9);
         renderManager.invoke70(FontRegistry.fontObject4, this.floatValue2, this.floatValue3 - 4.0F, 18.0F, this.resolve(), intValue6, "l");
         int intValue7 = compute2(-14408668, floatValue8);
         renderManager.invoke5(this.multiSelectPopupBounds2.x, this.multiSelectPopupBounds2.y, this.multiSelectPopupBounds2.width, this.multiSelectPopupBounds2.height, 6.0F, intValue7);
         int intValue8 = compute2(UiAccentColor.compute(), floatValue8);
         renderManager.invoke6(this.multiSelectPopupBounds3.x, this.multiSelectPopupBounds3.y, this.multiSelectPopupBounds3.width, this.multiSelectPopupBounds3.height, 0.0F, 6.0F, 6.0F, 0.0F, intValue8);
         float floatValue10 = this.multiSelectPopupBounds2.centerY() + 6.0F;
         float floatValue11 = this.multiSelectPopupBounds2.x + 16.0F;
         int intValue9 = compute2(-7829368, floatValue8);
         int intValue10 = compute2(-1, floatValue8);
         int intValue11 = ColorInterpolator.compute10(intValue9, intValue10, floatValue9);
         float floatValue12 = this.multiSelectPopupBounds3.x - floatValue11 - 10.0F;
         float floatValue13 = this.multiSelectPopupBounds2.height - 8.0F;
         if (floatValue12 > 0.0F && floatValue13 > 0.0F) {
            int intValue12 = Math.round(floatValue11);
            int intValue13 = Math.round(this.multiSelectPopupBounds2.y + 4.0F);
            int intValue14 = Math.max(0, Math.round(floatValue12));
            int intValue15 = Math.max(0, Math.round(floatValue13));
            renderManager.invoke23(intValue12, intValue13, intValue14, intValue15);

            try {
               this.items5.clear();

               for (int intValue16 = 0; intValue16 < this.items.size() && intValue16 < this.items3.size(); intValue16++) {
                  ClampedSpringAnimation clampedSpringAnimation3 = this.items3.get(intValue16);
                  float floatValue14 = measure3(clampedSpringAnimation3.measure());
                  float floatValue15 = clampedSpringAnimation3.getFloatValue6();
                  boolean flag7 = floatValue14 > 1.0E-4F;
                  boolean flag8 = floatValue14 > 1.0E-4F && floatValue15 <= 1.0E-4F;
                  if (flag7 || flag8) {
                     this.items5.add(intValue16);
                  }
               }

               if (!this.flag3 && this.items5.isEmpty()) {
                  int intValue17 = compute2(-11184811, floatValue8);
                  renderManager.invoke70(FontRegistry.fontObject4, floatValue11, floatValue10, 16.0F, "Select values", intValue17, "l");
               } else {
                  float floatValue16 = floatValue11;
                  float floatValue17 = RenderManager.resolve7(FontRegistry.fontObject4, ", ", 16.0F).floatValue;

                  for (int intValue18 = 0; intValue18 < this.items5.size(); intValue18++) {
                     int intValue19 = this.items5.get(intValue18);
                     ClampedSpringAnimation clampedSpringAnimation4 = this.items3.get(intValue19);
                     float floatValue18 = measure3(clampedSpringAnimation4.measure());
                     if (!(floatValue18 <= 1.0E-4F)) {
                        float floatValue19 = this.items4.size() > intValue19 ? measure3(this.items4.get(intValue19).measure()) : 0.0F;
                        String text2 = this.items.get(intValue19);
                        float floatValue20 = (1.0F - floatValue18) * 12.0F;
                        int intValue20 = compute2(intValue11, floatValue18);
                        float floatValue21 = floatValue19 * floatValue18;
                        if (floatValue21 > 1.0E-4F) {
                           int intValue21 = compute2(intValue11, floatValue21);
                           renderManager.invoke70(FontRegistry.fontObject4, floatValue16 + floatValue20, floatValue10, 16.0F, ", ", intValue21, "l");
                           floatValue16 += Math.max(0.0F, floatValue17 * floatValue21);
                        }

                        float floatValue22 = RenderManager.resolve7(FontRegistry.fontObject4, text2, 16.0F).floatValue;
                        renderManager.invoke70(FontRegistry.fontObject4, floatValue16 + floatValue20, floatValue10, 16.0F, text2, intValue20, "l");
                        floatValue16 += Math.max(0.0F, floatValue22 * floatValue18);
                     }
                  }
               }
            } finally {
               renderManager.invoke25();
            }
         }

         float floatValue23 = measure3(this.clampedSpringAnimation2.measure()) * 180.0F;
         float floatValue24 = this.multiSelectPopupBounds3.centerX();
         float floatValue25 = this.multiSelectPopupBounds3.centerY();
         float floatValue26 = measure2Self();
         float floatValue27 = floatValue25 + floatValue26;
         renderManager.invoke56(floatValue24, floatValue27);
         renderManager.invoke56(0.0F, -floatValue26);
         renderManager.invoke54(floatValue23);
         renderManager.invoke56(0.0F, floatValue26);
         renderManager.invoke56(-floatValue24, -floatValue27);

         try {
            renderManager.invoke70(FontRegistry.fontObject3, floatValue24, floatValue27, 24.0F, "\ue313", compute2(-1, floatValue8), "c");
         } finally {
            renderManager.invoke57();
            renderManager.invoke57();
            renderManager.invoke55();
            renderManager.invoke57();
            renderManager.invoke57();
         }
      }
   }

   @Override
   public void invoke4(RenderManager renderManager2, float f, float g) {
      float floatValue28 = measure3(this.clampedSpringAnimation2.measure());
      if (!(floatValue28 <= 0.001F)) {
         if (!this.items.isEmpty() && !(this.multiSelectPopupBounds4.width <= 0.0F) && !(this.multiSelectPopupBounds4.height <= 0.0F)) {
            float floatValue29 = measure3(g);
            float floatValue30 = f * floatValue29 * floatValue28;
            if (!(floatValue30 <= 1.0E-4F)) {
               int intValue22 = -14408668;
               renderManager2.invoke63(1.0F, floatValue28, this.multiSelectPopupBounds4.x, this.multiSelectPopupBounds4.y);
               boolean flag9 = false ;

               try {
                  flag9 = true;
                  renderManager2.invoke6(
                     this.multiSelectPopupBounds4.x, this.multiSelectPopupBounds4.y, this.multiSelectPopupBounds4.width, this.multiSelectPopupBounds4.height, 6.0F, 6.0F, 6.0F, 6.0F, intValue22
                  );

                  for (int intValue23 = 0; intValue23 < this.items6.size(); intValue23++) {
                     MultiSelectPopup.MultiSelectPopupBounds multiSelectPopupBounds = this.items6.get(intValue23);
                     String text3 = this.items.get(intValue23);
                     boolean flag10 = this.linkedHashSet.contains(text3);
                     float floatValue31 = intValue23 < this.items2.size() ? measure3(this.items2.get(intValue23).measure()) : 0.0F;
                     if (floatValue31 > 0.001F) {
                        int intValue24 = compute2(-13750738, floatValue31 * floatValue30);
                        float floatValue32 = intValue23 == 0 ? 6.0F : 0.0F;
                        float floatValue33 = intValue23 == 0 ? 6.0F : 0.0F;
                        float floatValue34 = intValue23 == this.items6.size() - 1 ? 6.0F : 0.0F;
                        float floatValue35 = intValue23 == this.items6.size() - 1 ? 6.0F : 0.0F;
                        renderManager2.invoke6(multiSelectPopupBounds.x, multiSelectPopupBounds.y, multiSelectPopupBounds.width, multiSelectPopupBounds.height, floatValue32, floatValue33, floatValue34, floatValue35, intValue24);
                     }

                     float floatValue36 = multiSelectPopupBounds.x + 16.0F;
                     float floatValue37 = multiSelectPopupBounds.centerY() + 6.0F;
                     int intValue25 = compute2(-7829368, floatValue30);
                     int intValue26 = compute2(-1, floatValue30);
                     float floatValue38 = flag10 ? 1.0F : floatValue31 * 0.7F;
                     int intValue27 = ColorInterpolator.compute10(intValue25, intValue26, floatValue38);
                     renderManager2.invoke70(FontRegistry.fontObject4, floatValue36, floatValue37, 16.0F, text3, intValue27, "l");
                     float floatValue39 = intValue23 < this.items3.size() ? measure3(this.items3.get(intValue23).measure()) : (flag10 ? 1.0F : 0.0F);
                     if (floatValue39 > 1.0E-4F) {
                        float floatValue40 = multiSelectPopupBounds.x + multiSelectPopupBounds.width - 16.0F + 2.0F;
                        float floatValue41 = multiSelectPopupBounds.centerY() + 6.0F + 3.0F;
                        int intValue28 = compute2(-1, floatValue30 * floatValue39);
                        renderManager2.invoke70(FontRegistry.fontObject3, floatValue40, floatValue41, 18.0F, "\ue5ca", intValue28, "r");
                     }
                  }

                  flag9 = false;
               } finally {
                  if (flag9) {
                     renderManager2.invoke64();
                  }
               }

               renderManager2.invoke64();
            }
         }
      }
   }

   @Override
   public boolean check() {
      return (this.flag2 || this.clampedSpringAnimation2.measure() > 0.001F) && this.multiSelectPopupBounds4.width > 0.0F && this.multiSelectPopupBounds4.height > 0.0F;
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
      } else if (this.multiSelectPopupBounds4.contains(d, e)) {
         int intValue29 = this.compute(e);
         if (intValue29 >= 0 && intValue29 < this.items.size()) {
            this.invoke10(intValue29);
         }

         return true;
      } else if (!this.multiSelectPopupBounds2.contains(d, e) && !this.multiSelectPopupBounds3.contains(d, e)) {
         this.invoke8();
         return true;
      } else {
         this.invoke8();
         return true;
      }
   }

   @Override
   public boolean check3(double d, double e, int i) {
      boolean flag11 = this.multiSelectPopupBounds2.contains(d, e) || this.multiSelectPopupBounds3.contains(d, e);
      if (i == 2) {
         if (!flag11) {
            return false;
         } else {
            this.invoke8();
            LinkedHashSet linkedHashSet = new LinkedHashSet<>(this.linkedHashSet);
            this.popupValueEditor.openForSetting(this.module, this.multiSelectSetting, d, e, linkedHashSet);
            return true;
         }
      } else if (i != 0) {
         return false;
      } else if (this.flag2) {
         return this.check2(d, e, i);
      } else if (flag11) {
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
      boolean flag12 = this.multiSelectPopupBounds2.contains(d, e) || this.multiSelectPopupBounds3.contains(d, e);
      boolean flag13 = this.flag2 && this.multiSelectPopupBounds4.contains(d, e);
      this.flag = this.flag2 ? false : flag12;
      if (this.flag2) {
         if (flag13) {
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
      return this.multiSelectSetting;
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
         String text4 = this.items.get(i);
         LinkedHashSet linkedHashSet2 = new LinkedHashSet<>(this.linkedHashSet);
         if (linkedHashSet2.contains(text4)) {
            linkedHashSet2.remove(text4);
         } else {
            linkedHashSet2.add(text4);
         }

         this.settingValue.invoke10(linkedHashSet2);
         this.linkedHashSet.clear();
         this.linkedHashSet.addAll(linkedHashSet2);
      }
   }

   private void invoke11() {
      this.items6.clear();
      if (this.items.isEmpty()) {
         this.multiSelectPopupBounds4 = MultiSelectPopup.MultiSelectPopupBounds.EMPTY;
      } else {
         float floatValue42 = this.multiSelectPopupBounds2.x;
         float floatValue43 = this.multiSelectPopupBounds2.y + this.multiSelectPopupBounds2.height + 6.0F;
         float floatValue44 = this.multiSelectPopupBounds2.width;
         float floatValue45 = 38.0F * this.items.size();
         this.multiSelectPopupBounds4 = new MultiSelectPopup.MultiSelectPopupBounds(floatValue42, floatValue43, floatValue44, floatValue45);
         float floatValue46 = floatValue43;

         for (int intValue30 = 0; intValue30 < this.items.size(); intValue30++) {
            this.items6.add(new MultiSelectPopup.MultiSelectPopupBounds(floatValue42, floatValue46, floatValue44, 38.0F));
            floatValue46 += 38.0F;
         }
      }
   }

   private int compute(double d) {
      if (!(d < this.multiSelectPopupBounds4.y) && !(d > this.multiSelectPopupBounds4.y + this.multiSelectPopupBounds4.height)) {
         double doubleValue = d - this.multiSelectPopupBounds4.y;
         if (doubleValue < 0.0) {
            return -1;
         } else {
            int intValue31 = (int)(doubleValue / 38.0);
            return intValue31 >= 0 && intValue31 < this.items.size() ? intValue31 : -1;
         }
      } else {
         return -1;
      }
   }

   private String resolve() {
      return this.text != null ? this.text : this.multiSelectSetting.name;
   }

   private static String resolve2(String string) {
      if (string == null) {
         return null;
      } else {
         String text5 = string.trim();
         return text5.isEmpty() ? null : text5;
      }
   }

   private void invoke12() {
      this.items2.clear();
      this.items3.clear();
      this.items4.clear();
      this.items5.clear();

      for (int intValue32 = 0; intValue32 < this.items.size(); intValue32++) {
         ClampedSpringAnimation clampedSpringAnimation5 = new ClampedSpringAnimation(AnimationSystem.getINSTANCE(), SPRING_CONFIG, 0.0F, 0.0F, 1.0F, 5.0E-4F, 5.0E-4F);
         clampedSpringAnimation5.setFloatEasing(FloatEasings.FLOAT_EASING_3);
         this.items2.add(clampedSpringAnimation5);
         ClampedSpringAnimation clampedSpringAnimation6 = new ClampedSpringAnimation(AnimationSystem.getINSTANCE(), SPRING_CONFIG_3, 0.0F, 0.0F, 1.0F, 5.0E-4F, 5.0E-4F);
         clampedSpringAnimation6.setFloatEasing(FloatEasings.FLOAT_EASING_3);
         this.items3.add(clampedSpringAnimation6);
         ClampedSpringAnimation clampedSpringAnimation7 = new ClampedSpringAnimation(AnimationSystem.getINSTANCE(), SPRING_CONFIG_3, 0.0F, 0.0F, 1.0F, 5.0E-4F, 5.0E-4F);
         clampedSpringAnimation7.setFloatEasing(FloatEasings.FLOAT_EASING_3);
         this.items4.add(clampedSpringAnimation7);
      }
   }

   private static float measure2Self() {
      if (Float.isNaN(floatValue)) {
         float floatValue47 = FontRegistry.measure(FontRegistry.fontObject3, 58131, 24.0F);
         floatValue = floatValue47;
      }

      return floatValue;
   }

   private static float measure3(float f) {
      if (f <= 0.0F) {
         return 0.0F;
      } else {
         return f >= 1.0F ? 1.0F : f;
      }
   }

   private static int compute2(int i, float f) {
      int intValue33 = i >>> 24 & 0xFF;
      int intValue34 = Math.round(intValue33 * f);
      int intValue35 = i & 16777215;
      return intValue34 << 24 | intValue35;
   }

   record MultiSelectPopupBounds(float x, float y, float width, float height) {
      static final MultiSelectPopup.MultiSelectPopupBounds EMPTY = new MultiSelectPopup.MultiSelectPopupBounds(0.0F, 0.0F, 0.0F, 0.0F);

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
