package ru.metaculture.protection;

import java.util.Objects;

public final class PopupContext {
   private static final float FLOAT_VALUE = 334.0F;
   private static final float FLOAT_VALUE_2 = 48.0F;
   private static final float FLOAT_VALUE_3 = 62.0F;
   private static final float FLOAT_VALUE_4 = 62.0F;
   private static final float FLOAT_VALUE_5 = 14.0F;
   private static final float FLOAT_VALUE_6 = 0.0F;
   private static final float FLOAT_VALUE_7 = 0.0F;
   private static final float FLOAT_VALUE_8 = 10.0F;
   private static final float FLOAT_VALUE_9 = 34.0F;
   private static final float FLOAT_VALUE_10 = 156.0F;
   private static final float FLOAT_VALUE_11 = 12.0F;
   private static final float FLOAT_VALUE_12 = 8.0F;
   private static final float FLOAT_VALUE_13 = 143.0F;
   private static final float FLOAT_VALUE_14 = 38.0F;
   private static final float FLOAT_VALUE_15 = 6.0F;
   private static final float FLOAT_VALUE_16 = 12.0F;
   private static final float FLOAT_VALUE_17 = 18.0F;
   private static final float FLOAT_VALUE_18 = 27.0F;
   private static final float FLOAT_VALUE_19 = 10.0F;
   private static final float FLOAT_VALUE_20 = 10.0F;
   private static final float FLOAT_VALUE_21 = 8.0F;
   private static final float FLOAT_VALUE_22 = 1.0F;
   private static final float FLOAT_VALUE_23 = 20.0F;
   private static final float FLOAT_VALUE_24 = 15.0F;
   private static final float FLOAT_VALUE_25 = 17.0F;
   private static final float FLOAT_VALUE_26 = 18.0F;
   private static final float FLOAT_VALUE_27 = 17.0F;
   private static final int INT_VALUE = 1447446;
   private static final int INT_VALUE_2 = 3355443;
   private static final int INT_VALUE_3 = 5197646;
   private static final int INT_VALUE_4 = 6974057;
   private static final int INT_VALUE_5 = 16777215;
   private static final int INT_VALUE_6 = 8947848;
   private static final int INT_VALUE_7 = 7105644;
   private static final int INT_VALUE_8 = 14765389;
   private static final double DOUBLE_VALUE = 0.76;
   private static final double DOUBLE_VALUE_2 = 0.08;
   private static final double DOUBLE_VALUE_3 = 0.05;
   private static final double DOUBLE_VALUE_4 = 0.12;
   private static final double DOUBLE_VALUE_5 = 0.18;
   private static final double DOUBLE_VALUE_6 = 0.14;
   private static final double DOUBLE_VALUE_7 = 0.18;
   private static final double DOUBLE_VALUE_8 = 0.18;
   private static final double DOUBLE_VALUE_9 = 0.24;
   private static final double DOUBLE_VALUE_10 = 0.06;

   private PopupContext() {
   }

   public static PopupContext.PopupContextBounds resolve(SettingBinding settingBinding, float f, float g, float h) {
      Objects.requireNonNull(settingBinding, "model");
      float floatValue = Math.max(0.0F, h);
      boolean flag = floatValue > 0.001F;
      float floatValue2 = 0.0F;
      float floatValue3 = flag ? 0.0F : 0.0F;
      float floatValue4 = flag ? floatValue2 + floatValue + floatValue3 : 0.0F;
      float floatValue5 = 124.0F + floatValue4;
      PopupContext.PopupContextState popupContextState = new PopupContext.PopupContextState(f, g, 334.0F, floatValue5);
      PopupContext.PopupContextState popupContextState2 = new PopupContext.PopupContextState(f, g, 334.0F, 0.0F);
      PopupContext.PopupContextState popupContextState3 = new PopupContext.PopupContextState(f + 18.0F, g, 298.0F, 62.0F);
      PopupContext.PopupContextState popupContextState4 = new PopupContext.PopupContextState(popupContextState3.getFloatValue(), popupContextState3.measure3(), popupContextState3.getFloatValue3(), 62.0F);
      PopupContext.PopupContextState popupContextState5 = new PopupContext.PopupContextState(popupContextState3.getFloatValue(), popupContextState4.measure3(), popupContextState3.getFloatValue3(), floatValue4);
      float floatValue6 = popupContextState5.getFloatValue2() + (flag ? 0.0F : 0.0F);
      PopupContext.PopupContextState popupContextState6 = flag
         ? new PopupContext.PopupContextState(popupContextState.getFloatValue(), floatValue6, 334.0F, floatValue)
         : new PopupContext.PopupContextState(popupContextState.getFloatValue(), popupContextState5.getFloatValue2(), 334.0F, 0.0F);
      float floatValue7 = popupContextState.getFloatValue() + popupContextState.getFloatValue3() - 18.0F - 156.0F;
      float floatValue8 = popupContextState3.getFloatValue2() + (popupContextState3.getFloatValue4() - 34.0F) * 0.5F;
      PopupContext.PopupContextState popupContextState7 = new PopupContext.PopupContextState(floatValue7, floatValue8, 156.0F, 34.0F);
      float floatValue9 = popupContextState4.getFloatValue2() + (popupContextState4.getFloatValue4() - 38.0F) * 0.5F;
      PopupContext.PopupContextState popupContextState8 = new PopupContext.PopupContextState(popupContextState3.getFloatValue(), floatValue9, 143.0F, 38.0F);
      PopupContext.PopupContextState popupContextState9 = new PopupContext.PopupContextState(popupContextState8.measure4() + 12.0F, floatValue9, 143.0F, 38.0F);
      float floatValue10 = popupContextState3.getFloatValue2() + popupContextState3.getFloatValue4() * 0.5F + 5.0F;
      float floatValue11 = popupContextState4.getFloatValue2() + 27.0F;
      float floatValue12 = flag ? popupContextState5.getFloatValue2() + 27.0F : 0.0F;
      float floatValue13 = popupContextState2.getFloatValue2() + 22.0F;
      float floatValue14 = floatValue13 + 20.0F;
      return new PopupContext.PopupContextBounds(popupContextState, popupContextState2, popupContextState3, popupContextState4, popupContextState5, popupContextState6, popupContextState7, popupContextState8, popupContextState9, floatValue13, floatValue14, floatValue10, floatValue11, floatValue12, floatValue2, floatValue);
   }

   public static PopupContext.PopupContextState resolve2(PopupContext.PopupContextBounds popupContextBounds, RenderManager renderManager, String string) {
      Objects.requireNonNull(popupContextBounds, "layout");
      Objects.requireNonNull(renderManager, "renderer");
      String text = string == null ? "" : string;
      float floatValue15 = 0.0F;
      if (!text.isEmpty()) {
         floatValue15 = RenderManager.resolve7(FontRegistry.fontObject4, text, 18.0F).floatValue;
      }

      float floatValue16 = 24.0F;
      float floatValue17 = floatValue15 + floatValue16;
      float floatValue18 = popupContextBounds.valueContent().getFloatValue4() > 0.0F ? popupContextBounds.valueContent().getFloatValue() : popupContextBounds.bindBlock().getFloatValue();
      float floatValue19 = Math.max(floatValue16, popupContextBounds.field().measure4() - floatValue18);
      float floatValue20 = Math.min(Math.max(floatValue17, floatValue16), floatValue19);
      float floatValue21 = popupContextBounds.field().measure4();
      float floatValue22 = floatValue21 - floatValue20;
      return new PopupContext.PopupContextState(floatValue22, popupContextBounds.field().getFloatValue2(), floatValue20, popupContextBounds.field().getFloatValue4());
   }

   public static void invoke(
      RenderManager renderManager2, FontObject fontObject, SettingBinding settingBinding2, PopupContext.PopupContextBounds popupContextBounds2, PopupContext.PopupContextData popupContextData
   ) {
      Objects.requireNonNull(renderManager2, "renderer");
      Objects.requireNonNull(fontObject, "defaultFont");
      Objects.requireNonNull(settingBinding2, "model");
      Objects.requireNonNull(popupContextBounds2, "layout");
      Objects.requireNonNull(popupContextData, "state");
      float floatValue23 = measure(popupContextData.alpha());
      if (!(floatValue23 <= 0.001F)) {
         float floatValue24 = measure(popupContextData.blurFactor());
         renderManager2.invoke65(floatValue23);
         boolean flag2 = false ;

         try {
            flag2 = true;
            if (floatValue24 > 0.001F) {
               renderManager2.invoke44(
                  popupContextBounds2.bounds().getFloatValue(),
                  popupContextBounds2.bounds().getFloatValue2(),
                  popupContextBounds2.bounds().getFloatValue3(),
                  popupContextBounds2.bounds().getFloatValue4(),
                  12.0F,
                  floatValue24
               );
            }

            double doubleValue = 0.75;
            renderManager2.invoke5(
               popupContextBounds2.bounds().getFloatValue(),
               popupContextBounds2.bounds().getFloatValue2(),
               popupContextBounds2.bounds().getFloatValue3(),
               popupContextBounds2.bounds().getFloatValue4(),
               12.0F,
               ColorInterpolator.compute3(1447446, doubleValue)
            );
            renderManager2.invoke28(
               popupContextBounds2.bounds().getFloatValue(),
               popupContextBounds2.bounds().getFloatValue2(),
               popupContextBounds2.bounds().getFloatValue3(),
               popupContextBounds2.bounds().getFloatValue4(),
               12.0F,
               ColorInterpolator.compute3(3355443, 1.0),
               0.5F
            );
            float floatValue25 = popupContextBounds2.bounds().getFloatValue() + 18.0F;
            float floatValue26 = Math.max(popupContextData.bindHoverProgress(), popupContextData.bindHovered() ? 1.0F : 0.0F);
            int intValue;
            if (popupContextData.listening()) {
               intValue = ColorInterpolator.compute3(16777215, 0.98);
            } else if (floatValue26 > 0.001F) {
               int intValue2 = ColorInterpolator.compute3(8947848, 0.92);
               int intValue3 = ColorInterpolator.compute3(16777215, 0.85);
               intValue = ColorInterpolator.compute10(intValue2, intValue3, floatValue26);
            } else {
               intValue = ColorInterpolator.compute3(8947848, 0.92);
            }

            renderManager2.invoke70(FontRegistry.fontObject4, floatValue25, popupContextBounds2.bindLabelBaseline(), 17.0F, "Bind Key", intValue, "l");
            int intValue4;
            if (popupContextData.listening()) {
               intValue4 = ColorInterpolator.compute3(6974057, 1.0);
            } else if (floatValue26 > 0.001F) {
               int intValue5 = ColorInterpolator.compute3(5197646, 1.0);
               int intValue6 = ColorInterpolator.compute3(6974057, 1.0);
               intValue4 = ColorInterpolator.compute10(intValue5, intValue6, floatValue26);
            } else {
               intValue4 = ColorInterpolator.compute3(5197646, 1.0);
            }

            PopupContext.PopupContextState popupContextState10 = popupContextData.fieldRect();
            renderManager2.invoke28(popupContextState10.getFloatValue(), popupContextState10.getFloatValue2(), popupContextState10.getFloatValue3(), popupContextState10.getFloatValue4(), 8.0F, intValue4, 1.0F);
            float floatValue27 = popupContextState10.measure2() + 5.0F + 1.0F;
            int intValue7;
            if (popupContextData.listening()) {
               intValue7 = ColorInterpolator.compute3(16777215, 0.98);
            } else if (floatValue26 > 0.001F) {
               int intValue8 = ColorInterpolator.compute3(8947848, 0.92);
               int intValue9 = ColorInterpolator.compute3(16777215, 0.85);
               intValue7 = ColorInterpolator.compute10(intValue8, intValue9, floatValue26);
            } else {
               intValue7 = ColorInterpolator.compute3(8947848, 0.92);
            }

            renderManager2.invoke70(FontRegistry.fontObject4, popupContextState10.measure(), floatValue27, 18.0F, popupContextData.keyLabel(), intValue7, "c");
            if (!popupContextData.statusMessage().isEmpty()) {
               renderManager2.invoke70(
                  FontRegistry.fontObject4,
                  floatValue25,
                  popupContextState10.measure3() + 8.0F + 18.0F,
                  15.0F,
                  popupContextData.statusMessage(),
                  ColorInterpolator.compute3(7105644, 0.9),
                  "l"
               );
            }

            invoke4(renderManager2, popupContextBounds2.bindBlock().measure3(), popupContextBounds2.bounds().getFloatValue(), popupContextBounds2.bounds().getFloatValue3(), floatValue23);
            float floatValue28 = Math.max(popupContextData.toggleHoverProgress(), popupContextData.toggleHovered() ? 1.0F : 0.0F);
            float floatValue29 = Math.max(popupContextData.holdHoverProgress(), popupContextData.holdHovered() ? 1.0F : 0.0F);
            invoke3(
               renderManager2,
               popupContextBounds2.toggleButton(),
               "Toggle",
               popupContextData.mode() == KeybindMode.TOGGLE,
               floatValue28,
               floatValue23,
               popupContextData.toggleSelectionProgress()
            );
            invoke3(renderManager2, popupContextBounds2.holdButton(), "Hold", popupContextData.mode() == KeybindMode.HOLD, floatValue29, floatValue23, popupContextData.holdSelectionProgress());
            if (popupContextBounds2.valueBlock().getFloatValue4() > 0.0F) {
               invoke2(renderManager2, popupContextBounds2, popupContextData);
               invoke4(renderManager2, popupContextBounds2.modesBlock().measure3(), popupContextBounds2.bounds().getFloatValue(), popupContextBounds2.bounds().getFloatValue3(), floatValue23);
               flag2 = false;
            } else {
               flag2 = false;
            }
         } finally {
            if (flag2) {
               renderManager2.invoke66();
            }
         }

         renderManager2.invoke66();
      }
   }

   private static void invoke2(RenderManager renderManager3, PopupContext.PopupContextBounds popupContextBounds3, PopupContext.PopupContextData popupContextData2) {
      if (!(popupContextData2.valueBlockHeight() <= 0.001F)) {
         PopupContext.PopupContextState popupContextState11 = popupContextBounds3.valueBlock();
         if (!(popupContextState11.getFloatValue4() <= 0.001F)) {
            renderManager3.invoke5(
               popupContextState11.getFloatValue(), popupContextState11.getFloatValue2(), popupContextState11.getFloatValue3(), popupContextState11.getFloatValue4(), 10.0F, ColorInterpolator.compute3(1447446, 0.18)
            );
            float floatValue30 = Math.max(0.0F, popupContextBounds3.valueHeaderHeight());
            if (floatValue30 > 0.001F) {
               renderManager3.invoke6(
                  popupContextState11.getFloatValue(), popupContextState11.getFloatValue2(), popupContextState11.getFloatValue3(), floatValue30, 10.0F, 10.0F, 0.0F, 0.0F, ColorInterpolator.compute3(1447446, 0.24)
               );
            }
         }
      }
   }

   private static void invoke3(RenderManager renderManager4, PopupContext.PopupContextState popupContextState12, String string, boolean bl, float f, float g, float h) {
      double doubleValue2 = measure(g);
      int intValue10 = ColorInterpolator.compute3(5197646, 0.9);
      float floatValue31 = measure(f);
      float floatValue32 = measure(h);
      double doubleValue3 = 0.12 + 0.06 * floatValue31;
      renderManager4.invoke5(
         popupContextState12.getFloatValue(), popupContextState12.getFloatValue2(), popupContextState12.getFloatValue3(), popupContextState12.getFloatValue4(), 6.0F, ColorInterpolator.compute3(1447446, doubleValue3)
      );
      renderManager4.invoke28(popupContextState12.getFloatValue(), popupContextState12.getFloatValue2(), popupContextState12.getFloatValue3(), popupContextState12.getFloatValue4(), 6.0F, intValue10, 1.0F);
      if (floatValue32 > 0.001F) {
         renderManager4.invoke5(
            popupContextState12.getFloatValue(),
            popupContextState12.getFloatValue2(),
            popupContextState12.getFloatValue3(),
            popupContextState12.getFloatValue4(),
            6.0F,
            ColorInterpolator.compute3(UiAccentColor.compute(), floatValue32)
         );
         renderManager4.invoke28(
            popupContextState12.getFloatValue(),
            popupContextState12.getFloatValue2(),
            popupContextState12.getFloatValue3(),
            popupContextState12.getFloatValue4(),
            6.0F,
            ColorInterpolator.compute3(UiAccentColor.compute(), floatValue32),
            1.0F
         );
      }

      int intValue11 = ColorInterpolator.compute3(8947848, 0.85 * doubleValue2);
      int intValue12 = ColorInterpolator.compute3(16777215, doubleValue2);
      int intValue13 = ColorInterpolator.compute10(intValue11, intValue12, 0.35F * floatValue31);
      int intValue14 = ColorInterpolator.compute10(intValue13, intValue12, floatValue32);
      float floatValue33 = popupContextState12.measure2() + 5.0F;
      renderManager4.invoke70(FontRegistry.fontObject4, popupContextState12.measure(), floatValue33, 17.0F, string, intValue14, "c");
   }

   private static void invoke4(RenderManager renderManager5, float f, float g, float h, float i) {
      float floatValue34 = 100.0F;
      float floatValue35 = Math.round(f * floatValue34) / floatValue34;
      renderManager5.invoke4(g + 18.0F, floatValue35, h - 36.0F, 1.0F / floatValue34, ColorInterpolator.compute3(16777215, 0.05 * i));
   }

   static float measure(float f) {
      if (f <= 0.0F) {
         return 0.0F;
      } else {
         return f >= 1.0F ? 1.0F : f;
      }
   }

   public record PopupContextBounds(
      PopupContext.PopupContextState bounds,
      PopupContext.PopupContextState header,
      PopupContext.PopupContextState bindBlock,
      PopupContext.PopupContextState modesBlock,
      PopupContext.PopupContextState valueBlock,
      PopupContext.PopupContextState valueContent,
      PopupContext.PopupContextState field,
      PopupContext.PopupContextState toggleButton,
      PopupContext.PopupContextState holdButton,
      float titleBaseline,
      float subtitleBaseline,
      float bindLabelBaseline,
      float modeLabelBaseline,
      float valueLabelBaseline,
      float valueHeaderHeight,
      float valueContentHeight
   ) {
   }

   public static final class PopupContextState {
      private final float floatValue;
      private final float floatValue2;
      private final float floatValue3;
      private final float floatValue4;

      public PopupContextState(float f, float g, float h, float i) {
         this.floatValue = f;
         this.floatValue2 = g;
         this.floatValue3 = h;
         this.floatValue4 = i;
      }

      public float getFloatValue() {
         return this.floatValue;
      }

      public float getFloatValue2() {
         return this.floatValue2;
      }

      public float getFloatValue3() {
         return this.floatValue3;
      }

      public float getFloatValue4() {
         return this.floatValue4;
      }

      public float measure() {
         return this.floatValue + this.floatValue3 * 0.5F;
      }

      public float measure2() {
         return this.floatValue2 + this.floatValue4 * 0.5F;
      }

      public float measure3() {
         return this.floatValue2 + this.floatValue4;
      }

      public float measure4() {
         return this.floatValue + this.floatValue3;
      }

      public boolean check(double d, double e) {
         return d >= this.floatValue && d <= this.floatValue + this.floatValue3 && e >= this.floatValue2 && e <= this.floatValue2 + this.floatValue4;
      }
   }

   public record PopupContextData(
      float alpha,
      float blurFactor,
      boolean listening,
      boolean bindHovered,
      boolean toggleHovered,
      boolean holdHovered,
      float bindHoverProgress,
      float toggleHoverProgress,
      float holdHoverProgress,
      float toggleSelectionProgress,
      float holdSelectionProgress,
      KeybindMode mode,
      String keyLabel,
      String statusMessage,
      float valueBlockHeight,
      float valueLabelBaseline,
      PopupContext.PopupContextState fieldRect
   ) {
      public PopupContextData(
         float alpha,
         float blurFactor,
         boolean listening,
         boolean bindHovered,
         boolean toggleHovered,
         boolean holdHovered,
         float bindHoverProgress,
         float toggleHoverProgress,
         float holdHoverProgress,
         float toggleSelectionProgress,
         float holdSelectionProgress,
         KeybindMode mode,
         String keyLabel,
         String statusMessage,
         float valueBlockHeight,
         float valueLabelBaseline,
         PopupContext.PopupContextState fieldRect
      ) {
         Objects.requireNonNull(mode, "mode");
         keyLabel = keyLabel == null ? "" : keyLabel;
         statusMessage = statusMessage == null ? "" : statusMessage;
         fieldRect = Objects.requireNonNull(fieldRect, "fieldRect");
         bindHoverProgress = PopupContext.measure(bindHoverProgress);
         toggleHoverProgress = PopupContext.measure(toggleHoverProgress);
         holdHoverProgress = PopupContext.measure(holdHoverProgress);
         toggleSelectionProgress = PopupContext.measure(toggleSelectionProgress);
         holdSelectionProgress = PopupContext.measure(holdSelectionProgress);
         valueBlockHeight = Math.max(0.0F, valueBlockHeight);
         if (valueBlockHeight <= 0.0F) {
            valueLabelBaseline = 0.0F;
         }

         this.alpha = alpha;
         this.blurFactor = blurFactor;
         this.listening = listening;
         this.bindHovered = bindHovered;
         this.toggleHovered = toggleHovered;
         this.holdHovered = holdHovered;
         this.bindHoverProgress = bindHoverProgress;
         this.toggleHoverProgress = toggleHoverProgress;
         this.holdHoverProgress = holdHoverProgress;
         this.toggleSelectionProgress = toggleSelectionProgress;
         this.holdSelectionProgress = holdSelectionProgress;
         this.mode = mode;
         this.keyLabel = keyLabel;
         this.statusMessage = statusMessage;
         this.valueBlockHeight = valueBlockHeight;
         this.valueLabelBaseline = valueLabelBaseline;
         this.fieldRect = fieldRect;
      }
   }
}
