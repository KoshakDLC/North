package ru.metaculture.protection;

import java.util.Locale;
import net.minecraft.client.MinecraftClient;

public final class SettingsRenderer {
   public static final float FLOAT_VALUE = 186.0F;
   private static final long TIMESTAMP = 5200L;
   private static final int INT_VALUE = -1577754;
   private static final int INT_VALUE_2 = -3945532;

   public void invoke(
      RenderManager renderManager, ClickGuiState clickGuiState, Setting setting, float f, float g, float h, ThemeContext themeContext
   ) {
      switch (setting) {
         case BooleanSetting booleanSetting:
            this.invoke2(renderManager, clickGuiState, booleanSetting, f, g, h, themeContext);
            break;
         case NumberSetting numberSetting:
            this.invoke3(renderManager, clickGuiState, numberSetting, f, g, h, themeContext);
            break;
         case ColorSetting colorSetting:
            this.invoke4(renderManager, clickGuiState, colorSetting, f, g, h, themeContext);
            break;
         case ModeSetting modeSetting:
            this.invoke11(renderManager, clickGuiState, modeSetting, f, g, h, themeContext);
            break;
         case FoundryShaderSetting foundryShaderSetting:
            this.invoke13(renderManager, clickGuiState, foundryShaderSetting, f, g, h, themeContext);
            break;
         case GroupSetting groupSetting:
            this.invoke16(renderManager, clickGuiState, groupSetting, f, g, h, themeContext);
            break;
         case MultiSelectSetting multiSelectSetting:
            this.invoke17(
               renderManager, clickGuiState, multiSelectSetting, multiSelectSetting.name, multiSelectSetting.selectedValues.isEmpty() ? "none" : multiSelectSetting.resolve2(), f, g, h, themeContext
            );
            break;
         case KeybindSetting keybindSetting:
            String text = clickGuiState.getKeybindSetting() == keybindSetting ? "..." : (keybindSetting.keyCode == -1 ? "n/a" : KeyboardKey.resolve(keybindSetting.keyCode));
            this.invoke17(renderManager, clickGuiState, keybindSetting, keybindSetting.name, text, f, g, h, themeContext);
            break;
         case TextSetting textSetting:
            String text2 = clickGuiState.getTextSetting() == textSetting ? textSetting.value + "|" : textSetting.value;
            this.invoke17(renderManager, clickGuiState, textSetting, textSetting.name, text2.isEmpty() ? "empty" : text2, f, g, h, themeContext);
            break;
         case ButtonSetting buttonSetting:
            this.invoke17(renderManager, clickGuiState, buttonSetting, buttonSetting.name, buttonSetting.getRun(), f, g, h, themeContext);
            break;
         default:
      }
   }

   public float measure(Setting setting2, Metrics metrics, ClickGuiState clickGuiState2) {
      return switch (setting2) {
         case NumberSetting numberSetting2 -> metrics.measure(22.0F);
         case FoundryShaderSetting foundryShaderSetting2 -> metrics.measure(18.0F);
         case ColorSetting colorSetting2 -> {
            float floatValue = clickGuiState2.measure7(AnimationKeyRegistry.resolve37(colorSetting2));
            yield metrics.measure(16.0F) + metrics.measure(186.0F) * floatValue;
         }
         case SpacerSetting spacerSetting -> metrics.measure(spacerSetting.getFloatValue());
         case GroupSetting groupSetting2 -> this.measure3(groupSetting2, metrics);
         default -> metrics.measure(14.0F);
      };
   }

   public float measure2(Setting setting3, Metrics metrics2) {
      return switch (setting3) {
         case NumberSetting numberSetting3 -> metrics2.measure(22.0F);
         case FoundryShaderSetting foundryShaderSetting3 -> metrics2.measure(18.0F);
         case ColorSetting colorSetting3 -> metrics2.measure(22.0F);
         case SpacerSetting spacerSetting2 -> metrics2.measure(spacerSetting2.getFloatValue());
         case GroupSetting groupSetting3 -> this.measure3(groupSetting3, metrics2);
         default -> metrics2.measure(14.0F);
      };
   }

   private float measure3(GroupSetting groupSetting4, Metrics metrics3) {
      float floatValue2 = (metrics3.getFloatValue14() - metrics3.measure(32.0F)) * 0.7F;
      int intValue = ClickGuiRenderUtils.compute16(groupSetting4, floatValue2, metrics3);
      float floatValue3 = metrics3.measure(14.0F);
      float floatValue4 = metrics3.measure(3.0F);
      return metrics3.measure(2.0F) + intValue * floatValue3 + (intValue > 1 ? (intValue - 1) * floatValue4 : 0.0F);
   }

   public static float measure4(float f) {
      return f * 0.4F;
   }

   public static float measure5(float f, float g) {
      return f + g - measure4(g);
   }

   public static float measure6(ModeSetting modeSetting2, float f, Metrics metrics4) {
      float floatValue5 = f * 0.52F;
      float floatValue6 = ClickGuiRenderUtils.measure(FontRegistry.fontObject, modeSetting2.value, 10.0F);
      return Math.max(metrics4.measure(52.0F), Math.min(floatValue5, floatValue6 + metrics4.measure(26.0F)));
   }

   public static float measure7(ModeSetting modeSetting3, float f, float g, Metrics metrics5) {
      return f + g - measure6(modeSetting3, g, metrics5);
   }

   public static float measure8(float f, Metrics metrics6) {
      return f - metrics6.measure(1.0F);
   }

   public static float measure9(Metrics metrics7) {
      return metrics7.measure(16.0F);
   }

   public static float measure10(float f) {
      return f;
   }

   public static float measure11(float f, float g) {
      return f;
   }

   public static float measure12(FoundryShaderSetting foundryShaderSetting4, float f, Metrics metrics8) {
      String text3 = foundryShaderSetting4 == null ? "None" : foundryShaderSetting4.resolve();
      float floatValue7 = f * 0.62F;
      float floatValue8 = ClickGuiRenderUtils.measure(FontRegistry.fontObject, text3, 10.0F);
      return Math.max(metrics8.measure(86.0F), Math.min(floatValue7, floatValue8 + metrics8.measure(38.0F)));
   }

   public static float measure13(FoundryShaderSetting foundryShaderSetting5, float f, float g, Metrics metrics9) {
      return f + g - measure12(foundryShaderSetting5, g, metrics9);
   }

   public static float measure14(float f, Metrics metrics10) {
      return f - metrics10.measure(1.0F);
   }

   public static float measure15(Metrics metrics11) {
      return metrics11.measure(18.0F);
   }

   public static float measure16(FoundryShaderSetting foundryShaderSetting6, Metrics metrics12) {
      int intValue2 = foundryShaderSetting6 == null ? 1 : Math.max(1, foundryShaderSetting6.refreshOptions().size());
      return metrics12.measure(8.0F) + intValue2 * measure17(metrics12) + metrics12.measure(6.0F);
   }

   public static float measure17(Metrics metrics13) {
      return metrics13.measure(58.0F);
   }

   public static float measure18(float f, float g, Metrics metrics14) {
      return f + g - metrics14.measure(12.0F) - metrics14.measure(3.0F);
   }

   public static float measure19(float f, Metrics metrics15) {
      return f - metrics15.measure(1.0F);
   }

   public static float measure20(Metrics metrics16) {
      return metrics16.measure(18.0F);
   }

   public static float measure21(float f, float g, Metrics metrics17) {
      return f + g - metrics17.measure(12.0F) - metrics17.measure(3.0F);
   }

   public static float measure22(float f, Metrics metrics18) {
      return f - metrics18.measure(2.0F);
   }

   public static float measure23(Metrics metrics19) {
      return metrics19.measure(18.0F);
   }

   private void invoke2(
      RenderManager renderManager2, ClickGuiState clickGuiState3, BooleanSetting booleanSetting2, float f, float g, float h, ThemeContext themeContext2
   ) {
      Metrics metrics20 = themeContext2.getMetrics();
      ColorScheme colorScheme = themeContext2.getColorScheme();
      float floatValue9 = clickGuiState3.measure5(
         AnimationKeyRegistry.resolve19(booleanSetting2), booleanSetting2.isEnabled() ? 1.0F : 0.0F, SpringSpec.resolve11()
      );
      float floatValue10 = metrics20.measure(12.0F);
      float floatValue11 = f + h - floatValue10;
      boolean flag = clickGuiState3.getBooleanSetting() == booleanSetting2;
      String text4 = flag ? booleanSetting2.name + " ..." : ClickGuiRenderUtils.resolve(booleanSetting2);
      this.invoke18(renderManager2, metrics20, text4, f, g, metrics20.measure(14.0F), 12.0F, floatValue11 - f - metrics20.measure(8.0F), ClickGuiRenderUtils.compute2(colorScheme));
      String text5 = AnimationKeyRegistry.resolve21(booleanSetting2);
      float floatValue12 = clickGuiState3.measure5(
         text5,
         ClickGuiRenderUtils.check(
               clickGuiState3, floatValue11 - metrics20.measure(3.0F), g - metrics20.measure(2.0F), floatValue10 + metrics20.measure(6.0F), floatValue10 + metrics20.measure(6.0F)
            )
            ? 1.0F
            : 0.0F,
         SpringSpec.resolve11()
      );
      float floatValue13 = ClickGuiRenderUtils.measure7(floatValue12, clickGuiState3.measure8(text5));
      renderManager2.invoke62(floatValue13, floatValue11 + floatValue10 * 0.5F, g + metrics20.measure(1.0F) + floatValue10 * 0.5F);
      boolean flag2 = false ;

      try {
         flag2 = true;
         renderManager2.invoke5(floatValue11, g + metrics20.measure(1.0F), floatValue10, floatValue10, metrics20.measure(4.0F), RenderManager.RenderManagerState.compute37(255, 255, 255, 30));
         renderManager2.invoke28(
            floatValue11,
            g + metrics20.measure(1.0F),
            floatValue10,
            floatValue10,
            metrics20.measure(4.0F),
            ColorScheme.compute7(colorScheme.getIntValue6(), ColorScheme.compute6(colorScheme.getIntValue14(), 95), Math.max(floatValue9 * 0.5F, floatValue12)),
            0.5F
         );
         if (floatValue9 > 0.01F) {
            renderManager2.invoke37(
               floatValue11 + metrics20.measure(2.0F),
               g + metrics20.measure(3.0F),
               metrics20.measure(8.0F),
               metrics20.measure(8.0F),
               metrics20.measure(8.0F),
               ColorScheme.compute6(colorScheme.getIntValue14(), Math.round(255.0F * floatValue9)),
               ColorScheme.compute6(colorScheme.getIntValue15(), Math.round(255.0F * floatValue9))
            );
            flag2 = false;
         } else {
            flag2 = false;
         }
      } finally {
         if (flag2) {
            renderManager2.invoke64();
         }
      }

      renderManager2.invoke64();
   }

   private void invoke3(
      RenderManager renderManager3, ClickGuiState clickGuiState4, NumberSetting numberSetting4, float f, float g, float h, ThemeContext themeContext3
   ) {
      Metrics metrics21 = themeContext3.getMetrics();
      ColorScheme colorScheme2 = themeContext3.getColorScheme();
      float floatValue14 = (numberSetting4.value - numberSetting4.minimum) / (numberSetting4.maximum - numberSetting4.minimum);
      String text6 = AnimationKeyRegistry.resolve19(numberSetting4) + "_prog";
      float floatValue15 = clickGuiState4.measure5(text6, floatValue14, SpringSpec.resolve11());
      float floatValue16 = clickGuiState4.measure7(AnimationKeyRegistry.resolve36(numberSetting4));
      float floatValue17 = Math.max(0.0F, Math.min(1.0F, floatValue15 + (floatValue14 - floatValue15) * floatValue16 * 0.85F));
      String text7 = AnimationKeyRegistry.resolve20(numberSetting4);
      float floatValue18 = clickGuiState4.measure5(
         text7, ClickGuiRenderUtils.check(clickGuiState4, f, g + metrics21.measure(11.0F), h, metrics21.measure(14.0F)) ? 1.0F : 0.0F, SpringSpec.resolve11()
      );
      long longValue = System.currentTimeMillis();
      float floatValue19 = (float)(longValue % 1000L) / 1000.0F;
      float floatValue20 = floatValue14 - floatValue15;
      float floatValue21 = numberSetting4.minimum + floatValue17 * (numberSetting4.maximum - numberSetting4.minimum);
      String text8 = numberSetting4.check()
         ? numberSetting4.resolve(numberSetting4.value)
         : ClickGuiRenderUtils.resolve6(floatValue21, numberSetting4.step);
      float floatValue22 = ClickGuiRenderUtils.measure(FontRegistry.fontObject4, text8, 12.0F);
      int intValue3 = ColorScheme.compute7(ClickGuiRenderUtils.compute4(colorScheme2), colorScheme2.getIntValue14(), floatValue16 * 0.8F);
      float floatValue23 = f + h - floatValue22;
      this.invoke18(
         renderManager3, metrics21, numberSetting4.name, f, g, metrics21.measure(14.0F), 12.0F, floatValue23 - f - metrics21.measure(8.0F), ClickGuiRenderUtils.compute2(colorScheme2)
      );
      ClickGuiRenderUtils.invoke4(renderManager3, metrics21, FontRegistry.fontObject4, floatValue23, g, metrics21.measure(14.0F), 12.0F, text8, intValue3);
      float floatValue24 = g + metrics21.measure(17.0F);
      float floatValue25 = metrics21.measure(5.0F);
      float floatValue26 = floatValue25 * 0.5F;
      float floatValue27 = h * floatValue17;
      renderManager3.invoke5(f, floatValue24, h, floatValue25, floatValue26, ColorScheme.compute7(colorScheme2.getIntValue4(), colorScheme2.getIntValue6(), floatValue18 * 0.42F));
      int intValue4 = numberSetting4.check() ? numberSetting4.text2.length - 1 : 5;

      for (int intValue5 = 1; intValue5 < intValue4; intValue5++) {
         float floatValue28 = f + h * intValue5 / intValue4 - metrics21.measure(0.5F);
         renderManager3.invoke5(
            floatValue28, floatValue24 + metrics21.measure(1.0F), metrics21.measure(1.0F), floatValue25 - metrics21.measure(2.0F), metrics21.measure(0.5F), colorScheme2.getIntValue6()
         );
      }

      if (floatValue27 > 1.0F) {
         renderManager3.invoke34(f, floatValue24, floatValue27, floatValue25, floatValue26, colorScheme2.getIntValue15(), colorScheme2.getIntValue14());
         float floatValue29 = 0.75F + 0.25F * (float)Math.sin(floatValue19 * Math.PI * 2.0);
         float floatValue30 = floatValue17 * (1.0F + floatValue16 * 0.6F);
         int intValue6 = colorScheme2.isFlag()
            ? ColorScheme.compute5(0, 0, 0, Math.round((18.0F + floatValue16 * 10.0F) * floatValue29 * floatValue30))
            : ColorScheme.compute6(colorScheme2.getIntValue14(), Math.round((22.0F + floatValue16 * 18.0F) * floatValue29 * floatValue30));
         renderManager3.invoke41(
            f,
            floatValue24,
            floatValue27,
            floatValue25,
            floatValue26,
            metrics21.measure((colorScheme2.isFlag() ? 7 : 10) + floatValue16 * 6.0F) * floatValue29 * floatValue30,
            metrics21.measure(colorScheme2.isFlag() ? 1.5F : 2.0F),
            intValue6
         );
         float floatValue31 = metrics21.measure(8.0F + floatValue16 * 6.0F);
         float floatValue32 = Math.max(f, f + floatValue27 - floatValue31);
         renderManager3.invoke34(
            floatValue32,
            floatValue24 - metrics21.measure(0.5F),
            Math.min(floatValue31, floatValue27),
            floatValue25 + metrics21.measure(1.0F),
            floatValue26,
            ColorScheme.compute6(colorScheme2.getIntValue14(), 0),
            ColorScheme.compute6(colorScheme2.getIntValue14(), Math.round((40.0F + floatValue16 * 35.0F) * floatValue29 * floatValue30))
         );
      }

      float floatValue33 = Math.abs(floatValue20);
      float floatValue34 = floatValue16 * Math.min(0.3F, floatValue33 * 8.0F);
      float floatValue35 = Math.min(0.5F, floatValue33 * 5.0F + floatValue34);
      float floatValue36 = metrics21.measure(5.5F);
      float floatValue37 = floatValue36 * 2.0F;
      float floatValue38 = ClickGuiRenderUtils.measure8(floatValue18, clickGuiState4.measure8(text7), 0.018F, 0.006F);
      float floatValue39 = 1.0F + floatValue16 * 0.12F;
      float floatValue40 = floatValue37 * (1.0F + floatValue35) * floatValue38 * floatValue39;
      float floatValue41 = floatValue37 * (1.0F - floatValue35 * 0.35F) * floatValue38 * floatValue39;
      float floatValue42 = floatValue41 * 0.5F;
      float floatValue43 = f + h * floatValue17;
      float floatValue44 = Math.signum(floatValue20) * Math.min(metrics21.measure(1.5F), floatValue33 * metrics21.measure(20.0F));
      floatValue43 += floatValue44;
      float floatValue45 = floatValue43 - floatValue40 * 0.5F;
      float floatValue46 = floatValue24 + (floatValue25 - floatValue41) * 0.5F;
      if (floatValue16 > 0.01F) {
         float floatValue47 = 0.6F + 0.4F * (float)Math.sin(floatValue19 * Math.PI * 3.0);
         float floatValue48 = metrics21.measure(14.0F) * floatValue16 * floatValue47;
         int intValue7 = colorScheme2.isFlag()
            ? ColorScheme.compute5(0, 0, 0, Math.round(22.0F * floatValue16 * floatValue47))
            : ColorScheme.compute6(colorScheme2.getIntValue14(), Math.round(35.0F * floatValue16 * floatValue47));
         renderManager3.invoke41(
            floatValue45 - metrics21.measure(2.0F),
            floatValue46 - metrics21.measure(2.0F),
            floatValue40 + metrics21.measure(4.0F),
            floatValue41 + metrics21.measure(4.0F),
            floatValue42 + metrics21.measure(2.0F),
            floatValue48,
            metrics21.measure(2.0F),
            intValue7
         );
      }

      if (floatValue17 > 0.01F) {
         float floatValue49 = 0.5F + 0.5F * (float)Math.sin(floatValue19 * Math.PI * 2.0);
         int intValue8 = colorScheme2.isFlag()
            ? ColorScheme.compute5(0, 0, 0, Math.round(14.0F * floatValue17 * floatValue49))
            : ColorScheme.compute6(colorScheme2.getIntValue14(), Math.round(18.0F * floatValue17 * floatValue49));
         renderManager3.invoke41(
            floatValue45 - metrics21.measure(1.0F),
            floatValue46 - metrics21.measure(1.0F),
            floatValue40 + metrics21.measure(2.0F),
            floatValue41 + metrics21.measure(2.0F),
            floatValue42 + metrics21.measure(1.0F),
            metrics21.measure(8.0F) * floatValue49 * floatValue17,
            metrics21.measure(1.0F),
            intValue8
         );
      }

      renderManager3.invoke41(
         floatValue45 + metrics21.measure(0.5F),
         floatValue46 + metrics21.measure(1.0F),
         floatValue40,
         floatValue41,
         floatValue42,
         metrics21.measure(3.0F),
         metrics21.measure(0.5F),
         ColorScheme.compute5(0, 0, 0, 50)
      );
      renderManager3.invoke37(floatValue45, floatValue46, floatValue40, floatValue41, floatValue42, ClickGuiRenderUtils.compute8(colorScheme2), ClickGuiRenderUtils.compute9(colorScheme2));
      if (floatValue17 > 0.01F) {
         float floatValue50 = Math.max(floatValue17, floatValue16);
         renderManager3.invoke28(
            floatValue45 + metrics21.measure(1.0F),
            floatValue46 + metrics21.measure(1.0F),
            floatValue40 - metrics21.measure(2.0F),
            floatValue41 - metrics21.measure(2.0F),
            Math.max(0.0F, floatValue42 - metrics21.measure(1.0F)),
            ColorScheme.compute6(colorScheme2.getIntValue14(), Math.round((80.0F + floatValue16 * 40.0F) * floatValue50)),
            0.7F
         );
      }

      renderManager3.invoke5(
         floatValue43 - floatValue36 * 0.4F,
         floatValue46 + metrics21.measure(1.0F),
         floatValue36 * 0.8F,
         floatValue41 * 0.3F,
         floatValue42 * 0.4F,
         ColorScheme.compute6(colorScheme2.isFlag() ? -16777216 : colorScheme2.getIntValue13(), colorScheme2.isFlag() ? 16 : 18)
      );
   }

   private void invoke4(
      RenderManager renderManager4, ClickGuiState clickGuiState5, ColorSetting colorSetting4, float f, float g, float h, ThemeContext themeContext4
   ) {
      Metrics metrics22 = themeContext4.getMetrics();
      ColorScheme colorScheme3 = themeContext4.getColorScheme();
      float floatValue51 = clickGuiState5.measure7(AnimationKeyRegistry.resolve37(colorSetting4));
      float floatValue52 = clickGuiState5.measure5(AnimationKeyRegistry.resolve40(colorSetting4), colorSetting4.measure(), SpringSpec.resolve11());
      float floatValue53 = clickGuiState5.measure5(AnimationKeyRegistry.resolve41(colorSetting4), colorSetting4.floatValue3, SpringSpec.resolve11());
      int intValue9 = ClickGuiRenderUtils.compute20(floatValue52, colorSetting4.saturation, colorSetting4.brightness, floatValue53);
      float floatValue54 = metrics22.measure(12.0F);
      float floatValue55 = f + h - floatValue54;
      float floatValue56 = g + metrics22.measure(1.0F);
      float floatValue57 = metrics22.measure(3.0F);
      this.invoke18(
         renderManager4, metrics22, colorSetting4.name, f, g, metrics22.measure(14.0F), 12.0F, floatValue55 - f - metrics22.measure(8.0F), ClickGuiRenderUtils.compute2(colorScheme3)
      );
      String text9 = AnimationKeyRegistry.resolve21(colorSetting4);
      float floatValue58 = clickGuiState5.measure5(
         text9,
         ClickGuiRenderUtils.check(
               clickGuiState5, floatValue55 - metrics22.measure(3.0F), floatValue56 - metrics22.measure(3.0F), floatValue54 + metrics22.measure(6.0F), floatValue54 + metrics22.measure(6.0F)
            )
            ? 1.0F
            : 0.0F,
         SpringSpec.resolve11()
      );
      renderManager4.invoke62(ClickGuiRenderUtils.measure7(floatValue58, clickGuiState5.measure8(text9)), floatValue55 + floatValue54 * 0.5F, floatValue56 + floatValue54 * 0.5F);

      try {
         renderManager4.invoke24(floatValue55, floatValue56, floatValue54, floatValue54, floatValue57, floatValue57, floatValue57, floatValue57);

         try {
            this.invoke10(renderManager4, floatValue55, floatValue56, floatValue54, floatValue54, this.measure27(metrics22, 0.74F), 1.0F);
         } finally {
            renderManager4.invoke25();
         }

         renderManager4.invoke5(floatValue55, floatValue56, floatValue54, floatValue54, floatValue57, intValue9);
         renderManager4.invoke32(
            floatValue55, floatValue56, floatValue54, floatValue54 * 0.55F, floatValue57, floatValue57, 0.0F, 0.0F, ColorScheme.compute6(-1, 60), ColorScheme.compute6(-1, 60), 0, 0
         );
         int intValue10 = ColorScheme.compute6(colorScheme3.isFlag() ? -16777216 : -1, 102);
         renderManager4.invoke28(floatValue55, floatValue56, floatValue54, floatValue54, floatValue57, ColorScheme.compute7(intValue10, ColorScheme.compute6(colorScheme3.getIntValue14(), 180), floatValue58), 0.5F);
      } finally {
         renderManager4.invoke64();
      }

      if (floatValue51 > 0.01F) {
         this.invoke5(renderManager4, clickGuiState5, colorSetting4, f, g + metrics22.measure(16.0F), h, floatValue51, themeContext4);
      }
   }

   private void invoke5(
      RenderManager renderManager5, ClickGuiState clickGuiState6, ColorSetting colorSetting5, float f, float g, float h, float i, ThemeContext themeContext5
   ) {
      Metrics metrics23 = themeContext5.getMetrics();
      ColorScheme colorScheme4 = themeContext5.getColorScheme();
      float floatValue59 = metrics23.measure(186.0F) * i;
      float floatValue60 = metrics23.measure(5.0F);
      float floatValue61 = metrics23.measure(12.0F);
      float floatValue62 = metrics23.measure(5.0F);
      float floatValue63 = metrics23.measure(9.0F);
      float floatValue64 = metrics23.measure(16.0F);
      float floatValue65 = metrics23.measure(16.0F);
      float floatValue66 = metrics23.measure(14.0F);
      float floatValue67 = metrics23.measure(12.0F);
      float floatValue68 = h - floatValue61 - floatValue62;
      float floatValue69 = floatValue59 - floatValue60 * 2.0F - floatValue63 - floatValue64 - floatValue65 - floatValue66 - floatValue67 - floatValue62 * 5.0F;
      float floatValue70 = g + floatValue60;
      float floatValue71 = f + floatValue68 + floatValue62;
      float floatValue72 = floatValue70 + floatValue69 + floatValue62;
      float floatValue73 = floatValue72 + floatValue63 + floatValue62;
      float floatValue74 = floatValue73 + floatValue64 + floatValue62;
      float floatValue75 = floatValue74 + floatValue65 + floatValue62;
      float floatValue76 = floatValue75 + floatValue66 + floatValue62;
      float floatValue77 = metrics23.measure(5.0F);
      if (clickGuiState6.getColorSetting2() == colorSetting5 && i > 0.025F) {
         clickGuiState6.setFloatValue34(f);
         clickGuiState6.setFloatValue35(floatValue70);
         clickGuiState6.setFloatValue36(Math.max(0.0F, floatValue68));
         clickGuiState6.setFloatValue37(Math.max(0.0F, floatValue69));
         clickGuiState6.setFloatValue38(floatValue71);
         clickGuiState6.setFloatValue39(floatValue70);
         clickGuiState6.setFloatValue40(Math.max(0.0F, floatValue61));
         clickGuiState6.setFloatValue41(Math.max(0.0F, floatValue69));
         clickGuiState6.setFloatValue42(f);
         clickGuiState6.setFloatValue43(floatValue72);
         clickGuiState6.setFloatValue44(Math.max(0.0F, h));
         clickGuiState6.setFloatValue45(Math.max(0.0F, floatValue63));
         clickGuiState6.setFloatValue46(f);
         clickGuiState6.setFloatValue47(floatValue73);
         clickGuiState6.setFloatValue48(Math.max(0.0F, h));
         clickGuiState6.setFloatValue49(Math.max(0.0F, floatValue64));
         clickGuiState6.setFloatValue50(f);
         clickGuiState6.setFloatValue51(floatValue74);
         clickGuiState6.setFloatValue52(Math.max(0.0F, h));
         clickGuiState6.setFloatValue53(Math.max(0.0F, floatValue65));
         clickGuiState6.setFloatValue54(f);
         clickGuiState6.setFloatValue55(floatValue75);
         clickGuiState6.setFloatValue56(Math.max(0.0F, h));
         clickGuiState6.setFloatValue57(Math.max(0.0F, floatValue66));
      }

      if (!(floatValue69 <= 1.0F) && !(floatValue68 <= 1.0F)) {
         renderManager5.invoke65(i);

         try {
            float floatValue78 = clickGuiState6.measure5(AnimationKeyRegistry.resolve40(colorSetting5), colorSetting5.measure(), SpringSpec.resolve11());
            float floatValue79 = clickGuiState6.measure5(AnimationKeyRegistry.resolve41(colorSetting5), colorSetting5.floatValue3, SpringSpec.resolve11());
            int intValue11 = ClickGuiRenderUtils.compute19(floatValue78, 1.0F, 1.0F);
            int intValue12 = ColorScheme.compute5(255, 255, 255, 255);
            int intValue13 = ColorScheme.compute5(0, 0, 0, 255);
            int intValue14 = ColorScheme.compute5(0, 0, 0, 0);
            renderManager5.invoke5(
               f - metrics23.measure(3.0F),
               g + metrics23.measure(1.0F),
               h + metrics23.measure(6.0F),
               floatValue59 - metrics23.measure(2.0F),
               metrics23.measure(7.0F),
               ColorScheme.compute7(colorScheme4.getIntValue3(), colorScheme4.getIntValue4(), i)
            );
            renderManager5.invoke28(
               f - metrics23.measure(3.0F),
               g + metrics23.measure(1.0F),
               h + metrics23.measure(6.0F),
               floatValue59 - metrics23.measure(2.0F),
               metrics23.measure(7.0F),
               colorScheme4.getIntValue6(),
               0.5F
            );
            renderManager5.invoke20();
            renderManager5.invoke24(f, floatValue70, floatValue68, floatValue69, floatValue77, floatValue77, floatValue77, floatValue77);

            try {
               renderManager5.invoke30(f, floatValue70, floatValue68, floatValue69, intValue12, intValue11, intValue11, intValue12);
               renderManager5.invoke30(f, floatValue70, floatValue68, floatValue69, intValue14, intValue14, intValue13, intValue13);
            } finally {
               renderManager5.invoke20();
               renderManager5.invoke25();
            }

            renderManager5.invoke28(f, floatValue70, floatValue68, floatValue69, floatValue77, colorScheme4.getIntValue7(), 0.5F);
            ClickGuiRenderUtils.invoke12(renderManager5, floatValue71, floatValue70, floatValue61, floatValue69, floatValue77);
            renderManager5.invoke28(floatValue71, floatValue70, floatValue61, floatValue69, floatValue77, colorScheme4.getIntValue7(), 0.5F);
            float floatValue80 = clickGuiState6.measure5(AnimationKeyRegistry.resolve38(colorSetting5), colorSetting5.saturation, SpringSpec.resolve11());
            float floatValue81 = clickGuiState6.measure5(
               AnimationKeyRegistry.resolve39(colorSetting5), 1.0F - colorSetting5.brightness, SpringSpec.resolve11()
            );
            float floatValue82 = f + floatValue80 * floatValue68;
            float floatValue83 = floatValue70 + floatValue81 * floatValue69;
            float floatValue84 = metrics23.measure(5.0F);
            int intValue15 = ClickGuiRenderUtils.compute19(floatValue78, floatValue80, 1.0F - floatValue81);
            renderManager5.invoke41(
               floatValue82 - floatValue84,
               floatValue83 - floatValue84,
               floatValue84 * 2.0F,
               floatValue84 * 2.0F,
               floatValue84,
               metrics23.measure(4.0F),
               metrics23.measure(1.0F),
               colorScheme4.isFlag() ? ColorScheme.compute5(0, 0, 0, 34) : ColorScheme.compute6(intValue15, 40)
            );
            renderManager5.invoke28(floatValue82 - floatValue84, floatValue83 - floatValue84, floatValue84 * 2.0F, floatValue84 * 2.0F, floatValue84, colorScheme4.getIntValue13(), 1.5F);
            renderManager5.invoke28(
               floatValue82 - floatValue84 + 1.0F,
               floatValue83 - floatValue84 + 1.0F,
               floatValue84 * 2.0F - 2.0F,
               floatValue84 * 2.0F - 2.0F,
               Math.max(0.0F, floatValue84 - 1.0F),
               ColorScheme.compute5(0, 0, 0, 80),
               0.5F
            );
            float floatValue85 = floatValue70 + floatValue78 * floatValue69;
            float floatValue86 = metrics23.measure(4.0F);
            float floatValue87 = floatValue61 + metrics23.measure(2.0F);
            renderManager5.invoke5(floatValue71 - metrics23.measure(1.0F), floatValue85 - floatValue86 * 0.5F, floatValue87, floatValue86, metrics23.measure(2.0F), colorScheme4.getIntValue13());
            renderManager5.invoke28(
               floatValue71 - metrics23.measure(1.0F), floatValue85 - floatValue86 * 0.5F, floatValue87, floatValue86, metrics23.measure(2.0F), ColorScheme.compute5(0, 0, 0, 60), 0.5F
            );
            renderManager5.invoke24(f, floatValue72, h, floatValue63, metrics23.measure(3.0F), metrics23.measure(3.0F), metrics23.measure(3.0F), metrics23.measure(3.0F));

            try {
               this.invoke10(renderManager5, f, floatValue72, h, floatValue63, this.measure27(metrics23, 1.0F), 1.0F);
               int intValue16 = ClickGuiRenderUtils.compute20(floatValue78, colorSetting5.saturation, colorSetting5.brightness, 0.0F);
               int intValue17 = ClickGuiRenderUtils.compute20(floatValue78, colorSetting5.saturation, colorSetting5.brightness, 1.0F);
               renderManager5.invoke34(f, floatValue72, h, floatValue63, metrics23.measure(3.0F), intValue16, intValue17);
            } finally {
               renderManager5.invoke25();
            }

            renderManager5.invoke28(f, floatValue72, h, floatValue63, metrics23.measure(3.0F), colorScheme4.getIntValue7(), 0.5F);
            float floatValue88 = f + floatValue79 * h;
            renderManager5.invoke41(
               floatValue88 - metrics23.measure(2.0F),
               floatValue72 - metrics23.measure(2.0F),
               metrics23.measure(4.0F),
               floatValue63 + metrics23.measure(4.0F),
               metrics23.measure(2.0F),
               metrics23.measure(4.0F),
               metrics23.measure(1.0F),
               ColorScheme.compute5(0, 0, 0, 70)
            );
            renderManager5.invoke5(
               floatValue88 - metrics23.measure(1.5F),
               floatValue72 - metrics23.measure(1.0F),
               metrics23.measure(3.0F),
               floatValue63 + metrics23.measure(2.0F),
               metrics23.measure(1.5F),
               colorScheme4.getIntValue13()
            );
            renderManager5.invoke28(
               floatValue88 - metrics23.measure(1.5F),
               floatValue72 - metrics23.measure(1.0F),
               metrics23.measure(3.0F),
               floatValue63 + metrics23.measure(2.0F),
               metrics23.measure(1.5F),
               ColorScheme.compute5(0, 0, 0, 80),
               0.5F
            );
            this.invoke8(renderManager5, metrics23, colorScheme4, f, floatValue73, h, floatValue64, floatValue78, colorSetting5.saturation, colorSetting5.brightness, floatValue79);
            this.invoke9(renderManager5, metrics23, colorScheme4, colorSetting5, f, floatValue74, h, floatValue65, floatValue79);
            int intValue18 = ClickGuiRenderUtils.compute20(floatValue78, colorSetting5.saturation, colorSetting5.brightness, floatValue79);
            int intValue19 = clickGuiState6.compute(colorSetting5);
            this.invoke6(renderManager5, clickGuiState6, metrics23, colorScheme4, colorSetting5, f, floatValue75, h, floatValue66, intValue18, intValue19);
            this.invoke7(renderManager5, clickGuiState6, metrics23, colorScheme4, colorSetting5, f, floatValue76, h, floatValue67, intValue18, floatValue79);
         } finally {
            renderManager5.invoke66();
         }
      }
   }

   private void invoke6(
      RenderManager renderManager6,
      ClickGuiState clickGuiState7,
      Metrics metrics24,
      ColorScheme colorScheme5,
      ColorSetting colorSetting6,
      float f,
      float g,
      float h,
      float i,
      int j,
      int k
   ) {
      float floatValue89 = metrics24.measure(4.0F);
      float floatValue90 = (h - floatValue89) * 0.5F;
      float floatValue91 = f + floatValue90 + floatValue89;
      float floatValue92 = Math.min(i, floatValue90);
      float floatValue93 = floatValue92 * 0.5F;
      float floatValue94 = f + (floatValue90 - floatValue92) * 0.5F;
      float floatValue95 = floatValue91 + (floatValue90 - floatValue92) * 0.5F;
      if (clickGuiState7.getColorSetting2() == colorSetting6) {
         clickGuiState7.setFloatValue58(floatValue91);
         clickGuiState7.setFloatValue59(floatValue90);
      }

      float[] floatValues = this.resolve(renderManager6, floatValue94, g, floatValue92, floatValue92);
      float[] floatValues2 = this.resolve(renderManager6, floatValue95, g, floatValue92, floatValue92);
      float floatValue96 = this.measure24(renderManager6, floatValue93);
      float floatValue97 = renderManager6.measure3();
      boolean flag3 = clickGuiState7.getColorSetting2() == colorSetting6 && !clickGuiState7.isFlag7();
      renderManager6.invoke20();
      if (!flag3
         || !ColorPickerShader.check(
            floatValues[0],
            floatValues[1],
            floatValues[2],
            floatValues[3],
            j,
            k,
            colorScheme5.getIntValue14(),
            colorScheme5.getIntValue15(),
            clickGuiState7.getFloatValue(),
            clickGuiState7.getFloatValue2(),
            floatValue96,
            floatValue97,
            true
         )) {
         if ((j >>> 24 & 0xFF) < 250) {
            renderManager6.invoke24(floatValue94, g, floatValue92, floatValue92, floatValue93, floatValue93, floatValue93, floatValue93);

            try {
               this.invoke10(renderManager6, floatValue94, g, floatValue92, floatValue92, this.measure27(metrics24, 1.0F), 1.0F);
            } finally {
               renderManager6.invoke25();
            }
         }

         renderManager6.invoke39(floatValue94 + floatValue93, g + floatValue93, floatValue93, 0.0F, 1.0F, j);
      }

      if (!flag3
         || !ColorPickerShader.check(
            floatValues2[0],
            floatValues2[1],
            floatValues2[2],
            floatValues2[3],
            k,
            j,
            colorScheme5.getIntValue15(),
            colorScheme5.getIntValue14(),
            clickGuiState7.getFloatValue(),
            clickGuiState7.getFloatValue2(),
            floatValue96,
            floatValue97,
            false
         )) {
         if ((k >>> 24 & 0xFF) < 250) {
            renderManager6.invoke24(floatValue95, g, floatValue92, floatValue92, floatValue93, floatValue93, floatValue93, floatValue93);
            boolean flag4 = false ;

            try {
               flag4 = true;
               this.invoke10(renderManager6, floatValue95, g, floatValue92, floatValue92, this.measure27(metrics24, 1.0F), 1.0F);
               flag4 = false;
            } finally {
               if (flag4) {
                  renderManager6.invoke25();
               }
            }

            renderManager6.invoke25();
         }

         renderManager6.invoke39(floatValue95 + floatValue93, g + floatValue93, floatValue93, 0.0F, 1.0F, k);
      }

      renderManager6.invoke28(floatValue94, g, floatValue92, floatValue92, floatValue93, colorScheme5.getIntValue7(), 0.5F);
      renderManager6.invoke28(floatValue95, g, floatValue92, floatValue92, floatValue93, colorScheme5.getIntValue7(), 0.5F);
      float floatValue98 = floatValue91 - floatValue89 * 0.5F;
      renderManager6.invoke37(
         floatValue98 - metrics24.measure(0.5F),
         g + metrics24.measure(2.0F),
         metrics24.measure(1.0F),
         i - metrics24.measure(4.0F),
         metrics24.measure(0.5F),
         ColorScheme.compute6(colorScheme5.getIntValue14(), 120),
         ColorScheme.compute6(colorScheme5.getIntValue15(), 90)
      );
   }

   private void invoke7(
      RenderManager renderManager7,
      ClickGuiState clickGuiState8,
      Metrics metrics25,
      ColorScheme colorScheme6,
      ColorSetting colorSetting7,
      float f,
      float g,
      float h,
      float i,
      int j,
      float k
   ) {
      float floatValue99 = h * 0.62F;
      float floatValue100 = h * 0.32F;
      float floatValue101 = f + h - floatValue100;
      float floatValue102 = i + metrics25.measure(4.0F);
      float floatValue103 = g - metrics25.measure(2.0F);
      float floatValue104 = metrics25.measure(3.0F);
      if (clickGuiState8.getColorSetting2() == colorSetting7) {
         clickGuiState8.setFloatValue60(f);
         clickGuiState8.setDynamicButtonSetting(floatValue103);
         clickGuiState8.setFloatValue61(floatValue99);
         clickGuiState8.setFloatValue62(floatValue102);
         clickGuiState8.setFloatValue63(floatValue101);
         clickGuiState8.setFloatValue64(floatValue103);
         clickGuiState8.setSpacerSetting(floatValue100);
         clickGuiState8.setFoundryShaderSetting2(floatValue102);
      }

      boolean flag5 = clickGuiState8.getColorSetting3() == colorSetting7;
      boolean flag6 = clickGuiState8.getColorSetting4() == colorSetting7;
      boolean flag7 = System.currentTimeMillis() / 500L % 2L == 0L;
      int intValue20 = ColorScheme.compute7(colorScheme6.getIntValue4(), ColorScheme.compute6(colorScheme6.getIntValue14(), 36), flag5 ? 1.0F : 0.0F);
      renderManager7.invoke5(f, floatValue103, floatValue99, floatValue102, floatValue104, intValue20);
      if (flag5) {
         renderManager7.invoke28(f, floatValue103, floatValue99, floatValue102, floatValue104, ColorScheme.compute6(colorScheme6.getIntValue14(), 220), 1.0F);
      } else {
         renderManager7.invoke28(f, floatValue103, floatValue99, floatValue102, floatValue104, colorScheme6.getIntValue7(), 0.5F);
      }

      String text10;
      if (flag5) {
         String text11 = clickGuiState8.getText4();
         text10 = "#" + (text11 == null ? "" : text11) + (flag7 ? "|" : " ");
      } else {
         text10 = String.format("#%02X%02X%02X", j >>> 16 & 0xFF, j >>> 8 & 0xFF, j & 0xFF);
      }

      int intValue21 = flag5 ? colorScheme6.getIntValue13() : colorScheme6.getIntValue12();
      ClickGuiRenderUtils.invoke4(
         renderManager7,
         metrics25,
         FontRegistry.fontObject,
         f + metrics25.measure(6.0F),
         floatValue103,
         floatValue102,
         8.0F,
         ClickGuiRenderUtils.resolve3(FontRegistry.fontObject, text10, 8.0F, floatValue99 - metrics25.measure(12.0F)),
         intValue21
      );
      int intValue22 = ColorScheme.compute7(colorScheme6.getIntValue4(), ColorScheme.compute6(colorScheme6.getIntValue14(), 36), flag6 ? 1.0F : 0.0F);
      renderManager7.invoke5(floatValue101, floatValue103, floatValue100, floatValue102, floatValue104, intValue22);
      if (flag6) {
         renderManager7.invoke28(floatValue101, floatValue103, floatValue100, floatValue102, floatValue104, ColorScheme.compute6(colorScheme6.getIntValue14(), 220), 1.0F);
      } else {
         renderManager7.invoke28(floatValue101, floatValue103, floatValue100, floatValue102, floatValue104, colorScheme6.getIntValue7(), 0.5F);
      }

      String text12;
      if (flag6) {
         String text13 = clickGuiState8.getText5();
         text12 = (text13 == null ? "" : text13) + (flag7 ? "|" : " ") + "%";
      } else {
         text12 = Math.round(k * 100.0F) + "%";
      }

      int intValue23 = flag6 ? colorScheme6.getIntValue13() : colorScheme6.getIntValue12();
      float floatValue105 = ClickGuiRenderUtils.measure(FontRegistry.fontObject, text12, 8.0F);
      float floatValue106 = floatValue101 + (floatValue100 - floatValue105) * 0.5F;
      ClickGuiRenderUtils.invoke4(renderManager7, metrics25, FontRegistry.fontObject, floatValue106, floatValue103, floatValue102, 8.0F, text12, intValue23);
   }

   private float[] resolve(RenderManager renderManager8, float f, float g, float h, float i) {
      float[] floatValues3 = renderManager8.getMatrix3Stack().resolve2();
      float floatValue107 = this.measure25(floatValues3, f, g);
      float floatValue108 = this.measure26(floatValues3, f, g);
      float floatValue109 = this.measure25(floatValues3, f + h, g);
      float floatValue110 = this.measure26(floatValues3, f + h, g);
      float floatValue111 = this.measure25(floatValues3, f + h, g + i);
      float floatValue112 = this.measure26(floatValues3, f + h, g + i);
      float floatValue113 = this.measure25(floatValues3, f, g + i);
      float floatValue114 = this.measure26(floatValues3, f, g + i);
      float floatValue115 = Math.min(Math.min(floatValue107, floatValue109), Math.min(floatValue111, floatValue113));
      float floatValue116 = Math.min(Math.min(floatValue108, floatValue110), Math.min(floatValue112, floatValue114));
      float floatValue117 = Math.max(Math.max(floatValue107, floatValue109), Math.max(floatValue111, floatValue113));
      float floatValue118 = Math.max(Math.max(floatValue108, floatValue110), Math.max(floatValue112, floatValue114));
      return new float[]{floatValue115, floatValue116, Math.max(0.0F, floatValue117 - floatValue115), Math.max(0.0F, floatValue118 - floatValue116)};
   }

   private float measure24(RenderManager renderManager9, float f) {
      float[] floatValues4 = renderManager9.getMatrix3Stack().resolve2();
      float floatValue119 = (float)Math.sqrt(floatValues4[0] * floatValues4[0] + floatValues4[3] * floatValues4[3]);
      float floatValue120 = (float)Math.sqrt(floatValues4[1] * floatValues4[1] + floatValues4[4] * floatValues4[4]);
      return f * Math.max(0.001F, (floatValue119 + floatValue120) * 0.5F);
   }

   private float measure25(float[] fs, float f, float g) {
      return fs[0] * f + fs[1] * g + fs[2];
   }

   private float measure26(float[] fs, float f, float g) {
      return fs[3] * f + fs[4] * g + fs[5];
   }

   private void invoke8(
      RenderManager renderManager10, Metrics metrics26, ColorScheme colorScheme7, float f, float g, float h, float i, float j, float k, float l, float m
   ) {
      float floatValue121 = metrics26.measure(3.0F);
      byte byteValue = 5;
      float floatValue122 = (h - floatValue121 * (byteValue - 1)) / byteValue;
      float floatValue123 = metrics26.measure(4.0F);
      float[] floatValues5 = new float[]{0.0F, 0.5F, -0.083333336F, 0.083333336F, 0.33333334F};
      float floatValue124 = Math.max(0.65F, k);
      float floatValue125 = Math.max(0.72F, l);

      for (int intValue24 = 0; intValue24 < byteValue; intValue24++) {
         float floatValue126 = f + intValue24 * (floatValue122 + floatValue121);
         float floatValue127 = j + floatValues5[intValue24];
         renderManager10.invoke24(floatValue126, g, floatValue122, i, floatValue123, floatValue123, floatValue123, floatValue123);

         try {
            if (m < 0.995F) {
               this.invoke10(renderManager10, floatValue126, g, floatValue122, i, this.measure27(metrics26, 0.92F), 1.0F);
            }

            renderManager10.invoke5(floatValue126, g, floatValue122, i, floatValue123, ClickGuiRenderUtils.compute20(floatValue127, floatValue124, floatValue125, m));
         } finally {
            renderManager10.invoke25();
         }

         renderManager10.invoke28(
            floatValue126, g, floatValue122, i, floatValue123, intValue24 == 0 ? ColorScheme.compute6(colorScheme7.getIntValue14(), 120) : colorScheme7.getIntValue7(), 0.5F
         );
      }
   }

   private void invoke9(
      RenderManager renderManager11, Metrics metrics27, ColorScheme colorScheme8, ColorSetting colorSetting8, float f, float g, float h, float i, float j
   ) {
      byte byteValue2 = 9;
      float floatValue128 = metrics27.measure(3.0F);
      float floatValue129 = (h - floatValue128 * (byteValue2 - 1)) / byteValue2;
      float floatValue130 = metrics27.measure(4.0F);
      int intValue25 = colorSetting8.compute2();

      for (int intValue26 = 0; intValue26 < byteValue2; intValue26++) {
         float floatValue131 = f + intValue26 * (floatValue129 + floatValue128);
         boolean flag8 = intValue26 == 8;
         boolean flag9 = !flag8 && intValue26 < colorSetting8.items.size();
         renderManager11.invoke24(floatValue131, g, floatValue129, i, floatValue130, floatValue130, floatValue130, floatValue130);

         try {
            this.invoke10(renderManager11, floatValue131, g, floatValue129, i, this.measure27(metrics27, 0.92F), flag9 ? 0.8F : 0.35F);
            if (flag9) {
               renderManager11.invoke5(floatValue131, g, floatValue129, i, floatValue130, colorSetting8.items.get(intValue26));
            } else {
               renderManager11.invoke5(
                  floatValue131, g, floatValue129, i, floatValue130, flag8 ? ColorScheme.compute6(colorScheme8.getIntValue14(), 18) : colorScheme8.getIntValue4()
               );
            }
         } finally {
            renderManager11.invoke25();
         }

         if (flag8) {
            float floatValue132 = ClickGuiRenderUtils.measure(FontRegistry.fontObject8, "O", 8.0F);
            ClickGuiRenderUtils.invoke4(
               renderManager11,
               metrics27,
               FontRegistry.fontObject8,
               floatValue131 + (floatValue129 - floatValue132) * 0.5F,
               g,
               i,
               8.0F,
               "O",
               ColorScheme.compute6(colorScheme8.getIntValue14(), Math.round(160.0F + 70.0F * j))
            );
         }

         boolean flag10 = flag9 && colorSetting8.items.get(intValue26) == intValue25;
         if (flag10) {
            float floatValue133 = ClickGuiRenderUtils.measure(FontRegistry.fontObject8, "j", 7.0F);
            ClickGuiRenderUtils.invoke4(
               renderManager11,
               metrics27,
               FontRegistry.fontObject8,
               floatValue131 + (floatValue129 - floatValue133) * 0.5F,
               g,
               i,
               7.0F,
               "j",
               ColorScheme.compute6(colorScheme8.getIntValue13(), 220)
            );
         }

         int intValue27 = flag10
            ? ColorScheme.compute6(colorScheme8.getIntValue14(), 160)
            : (flag8 ? ColorScheme.compute6(colorScheme8.getIntValue14(), 95) : colorScheme8.getIntValue7());
         renderManager11.invoke28(floatValue131, g, floatValue129, i, floatValue130, intValue27, flag10 ? 0.8F : 0.5F);
      }
   }

   private void invoke10(RenderManager renderManager12, float f, float g, float h, float i, float j, float k) {
      if (!(h <= 0.0F) && !(i <= 0.0F) && !(j <= 0.0F)) {
         boolean flag11 = false;

         for (float floatValue134 = g; floatValue134 < g + i; floatValue134 += j) {
            boolean flag12 = flag11;
            float floatValue135 = Math.min(j, g + i - floatValue134);

            for (float floatValue136 = f; floatValue136 < f + h; floatValue136 += j) {
               float floatValue137 = Math.min(j, f + h - floatValue136);
               renderManager12.invoke4(floatValue136, floatValue134, floatValue137, floatValue135, ColorScheme.compute6(flag12 ? -1577754 : -3945532, Math.round(255.0F * k)));
               flag12 = !flag12;
            }

            flag11 = !flag11;
         }
      }
   }

   private float measure27(Metrics metrics28, float f) {
      return Math.max(4.5F, metrics28.measure(6.0F * f));
   }

   private void invoke11(
      RenderManager renderManager13, ClickGuiState clickGuiState9, ModeSetting modeSetting4, float f, float g, float h, ThemeContext themeContext6
   ) {
      Metrics metrics29 = themeContext6.getMetrics();
      ColorScheme colorScheme9 = themeContext6.getColorScheme();
      float floatValue138 = clickGuiState9.measure7(AnimationKeyRegistry.resolve30(modeSetting4));
      float floatValue139 = measure6(modeSetting4, h, metrics29);
      float floatValue140 = measure7(modeSetting4, f, h, metrics29);
      float floatValue141 = measure9(metrics29);
      float floatValue142 = measure8(g, metrics29);
      float floatValue143 = metrics29.measure(5.0F);
      this.invoke18(
         renderManager13, metrics29, modeSetting4.name, f, g, metrics29.measure(14.0F), 12.0F, floatValue140 - f - metrics29.measure(8.0F), ClickGuiRenderUtils.compute2(colorScheme9)
      );
      String text14 = AnimationKeyRegistry.resolve21(modeSetting4);
      float floatValue144 = clickGuiState9.measure5(
         text14, ClickGuiRenderUtils.check(clickGuiState9, floatValue140, floatValue142, floatValue139, floatValue141) ? 1.0F : 0.0F, SpringSpec.resolve11()
      );
      float floatValue145 = ClickGuiRenderUtils.measure7(floatValue144, clickGuiState9.measure8(text14));
      renderManager13.invoke62(floatValue145, floatValue140 + floatValue139 * 0.5F, floatValue142 + floatValue141 * 0.5F);

      try {
         renderManager13.invoke5(
            floatValue140, floatValue142, floatValue139, floatValue141, floatValue143, ColorScheme.compute7(colorScheme9.getIntValue4(), colorScheme9.getIntValue6(), Math.max(floatValue138, floatValue144 * 0.58F))
         );
         renderManager13.invoke28(
            floatValue140,
            floatValue142,
            floatValue139,
            floatValue141,
            floatValue143,
            ColorScheme.compute7(colorScheme9.getIntValue7(), ColorScheme.compute6(colorScheme9.getIntValue14(), 120), Math.max(floatValue138, floatValue144)),
            0.5F
         );
         renderManager13.invoke37(
            floatValue140 + metrics29.measure(1.5F),
            floatValue142 + metrics29.measure(3.0F),
            metrics29.measure(1.5F),
            floatValue141 - metrics29.measure(6.0F),
            metrics29.measure(1.0F),
            ColorScheme.compute6(colorScheme9.getIntValue14(), 200),
            ColorScheme.compute6(colorScheme9.getIntValue15(), 180)
         );
         ClickGuiRenderUtils.invoke4(
            renderManager13,
            metrics29,
            FontRegistry.fontObject,
            floatValue140 + metrics29.measure(7.0F),
            floatValue142,
            floatValue141,
            10.0F,
            ClickGuiRenderUtils.resolve3(FontRegistry.fontObject, modeSetting4.value, 10.0F, floatValue139 - metrics29.measure(22.0F)),
            ClickGuiRenderUtils.compute2(colorScheme9)
         );
         float floatValue146 = floatValue140 + floatValue139 - metrics29.measure(12.0F);
         float floatValue147 = floatValue142 + floatValue141 * 0.5F;
         int intValue28 = ColorScheme.compute7(ColorScheme.compute6(colorScheme9.getIntValue14(), 160), colorScheme9.getIntValue14(), Math.max(floatValue138, floatValue144 * 0.5F));
         float floatValue148 = 1.0F - 2.0F * floatValue138;
         if (Math.abs(floatValue148) > 0.01F) {
            renderManager13.invoke62(floatValue148, floatValue146, floatValue147);

            try {
               ClickGuiRenderUtils.invoke4(renderManager13, metrics29, FontRegistry.fontObject8, floatValue146, floatValue142, floatValue141, 7.0F, "k", intValue28);
            } finally {
               renderManager13.invoke64();
            }
         }
      } finally {
         renderManager13.invoke64();
      }

      if (floatValue138 > 0.01F) {
         this.invoke12(renderManager13, clickGuiState9, modeSetting4, f, g, h, floatValue138, themeContext6);
      }
   }

   private void invoke12(
      RenderManager renderManager14, ClickGuiState clickGuiState10, ModeSetting modeSetting5, float f, float g, float h, float i, ThemeContext themeContext7
   ) {
      Metrics metrics30 = themeContext7.getMetrics();
      ColorScheme colorScheme10 = themeContext7.getColorScheme();
      float floatValue149 = measure4(h);
      float floatValue150 = measure5(f, h);
      float floatValue151 = g + metrics30.measure(14.0F) + metrics30.measure(4.0F);
      float floatValue152 = metrics30.measure(18.0F);
      float floatValue153 = metrics30.measure(3.0F);
      float floatValue154 = floatValue153 * 2.0F + modeSetting5.options.size() * floatValue152;
      float floatValue155 = metrics30.measure(6.0F);
      renderManager14.invoke65(i);

      try {
         renderManager14.invoke5(floatValue150, floatValue151, floatValue149, floatValue154 * i, floatValue155, colorScheme10.getIntValue4());
         renderManager14.invoke28(floatValue150, floatValue151, floatValue149, floatValue154 * i, floatValue155, colorScheme10.getIntValue7(), 0.5F);
         if (i > 0.5F) {
            for (int intValue29 = 0; intValue29 < modeSetting5.options.size(); intValue29++) {
               String text15 = modeSetting5.options.get(intValue29);
               boolean flag13 = intValue29 == modeSetting5.selectedIndex;
               float floatValue156 = floatValue151 + floatValue153 + intValue29 * floatValue152;
               if (flag13) {
                  renderManager14.invoke34(
                     floatValue150 + metrics30.measure(2.0F),
                     floatValue156,
                     floatValue149 - metrics30.measure(4.0F),
                     floatValue152,
                     metrics30.measure(4.0F),
                     ColorScheme.compute6(colorScheme10.getIntValue15(), 35),
                     ColorScheme.compute6(colorScheme10.getIntValue14(), 20)
                  );
               }

               String text16 = AnimationKeyRegistry.resolve25(modeSetting5, intValue29);
               boolean flag14 = ClickGuiRenderUtils.check(clickGuiState10, floatValue150, floatValue156, floatValue149, floatValue152);
               float floatValue157 = clickGuiState10.measure5(text16, flag14 ? 1.0F : 0.0F, SpringSpec.resolve11());
               if (floatValue157 > 0.01F && !flag13) {
                  renderManager14.invoke5(
                     floatValue150 + metrics30.measure(2.0F),
                     floatValue156,
                     floatValue149 - metrics30.measure(4.0F),
                     floatValue152,
                     metrics30.measure(4.0F),
                     ColorScheme.compute7(colorScheme10.getIntValue3(), colorScheme10.getIntValue5(), floatValue157)
                  );
               }

               int intValue30 = flag13 ? colorScheme10.getIntValue14() : (floatValue157 > 0.2F ? ClickGuiRenderUtils.compute2(colorScheme10) : ClickGuiRenderUtils.compute4(colorScheme10));
               if (flag13) {
                  renderManager14.invoke37(
                     floatValue150 + metrics30.measure(4.0F),
                     floatValue156 + metrics30.measure(3.0F),
                     metrics30.measure(1.5F),
                     floatValue152 - metrics30.measure(6.0F),
                     metrics30.measure(1.0F),
                     ColorScheme.compute6(colorScheme10.getIntValue14(), 200),
                     ColorScheme.compute6(colorScheme10.getIntValue15(), 180)
                  );
               }

               renderManager14.invoke62(
                  ClickGuiRenderUtils.measure8(floatValue157, clickGuiState10.measure8(text16), 0.012F, 0.004F), floatValue150 + floatValue149 * 0.5F, floatValue156 + floatValue152 * 0.5F
               );

               try {
                  ClickGuiRenderUtils.invoke4(
                     renderManager14,
                     metrics30,
                     FontRegistry.fontObject,
                     floatValue150 + metrics30.measure(10.0F),
                     floatValue156,
                     floatValue152,
                     10.0F,
                     ClickGuiRenderUtils.resolve3(FontRegistry.fontObject, text15, 10.0F, floatValue149 - metrics30.measure(18.0F)),
                     intValue30
                  );
               } finally {
                  renderManager14.invoke64();
               }
            }
         }
      } finally {
         renderManager14.invoke66();
      }
   }

   private void invoke13(
      RenderManager renderManager15, ClickGuiState clickGuiState11, FoundryShaderSetting foundryShaderSetting7, float f, float g, float h, ThemeContext themeContext8
   ) {
      Metrics metrics31 = themeContext8.getMetrics();
      ColorScheme colorScheme11 = themeContext8.getColorScheme();
      foundryShaderSetting7.refreshOptions();
      float floatValue158 = metrics31.measure(18.0F);
      float floatValue159 = clickGuiState11.measure7(AnimationKeyRegistry.resolve30(foundryShaderSetting7));
      float floatValue160 = measure12(foundryShaderSetting7, h, metrics31);
      float floatValue161 = measure13(foundryShaderSetting7, f, h, metrics31);
      float floatValue162 = measure14(g, metrics31);
      float floatValue163 = measure15(metrics31);
      float floatValue164 = metrics31.measure(6.0F);
      this.invoke18(renderManager15, metrics31, foundryShaderSetting7.name, f, g, floatValue158, 12.0F, floatValue161 - f - metrics31.measure(8.0F), ClickGuiRenderUtils.compute2(colorScheme11));
      String text17 = AnimationKeyRegistry.resolve21(foundryShaderSetting7);
      float floatValue165 = clickGuiState11.measure5(
         text17, ClickGuiRenderUtils.check(clickGuiState11, floatValue161, floatValue162, floatValue160, floatValue163) ? 1.0F : 0.0F, SpringSpec.resolve11()
      );
      float floatValue166 = Math.max(floatValue159, floatValue165);
      renderManager15.invoke62(ClickGuiRenderUtils.measure8(floatValue165, clickGuiState11.measure8(text17), 0.014F, 0.004F), floatValue161 + floatValue160 * 0.5F, floatValue162 + floatValue163 * 0.5F);

      try {
         renderManager15.invoke5(floatValue161, floatValue162, floatValue160, floatValue163, floatValue164, ColorScheme.compute7(colorScheme11.getIntValue4(), colorScheme11.getIntValue6(), floatValue166 * 0.7F));
         renderManager15.invoke28(
            floatValue161, floatValue162, floatValue160, floatValue163, floatValue164, ColorScheme.compute7(colorScheme11.getIntValue7(), ColorScheme.compute6(colorScheme11.getIntValue14(), 120), floatValue166), 0.5F
         );
         float floatValue167 = metrics31.measure(12.0F);
         float floatValue168 = floatValue161 + metrics31.measure(4.0F);
         float floatValue169 = floatValue162 + (floatValue163 - floatValue167) * 0.5F;
         renderManager15.invoke37(
            floatValue168, floatValue169, floatValue167, floatValue167, metrics31.measure(4.0F), ColorScheme.compute6(colorScheme11.getIntValue14(), 190), ColorScheme.compute6(colorScheme11.getIntValue15(), 150)
         );
         float floatValue170 = ClickGuiRenderUtils.measure(BrandMark.font(), BrandMark.GLYPH, 7.0F);
         ClickGuiRenderUtils.invoke4(renderManager15, metrics31, BrandMark.font(), floatValue168 + (floatValue167 - floatValue170) * 0.5F, floatValue169, floatValue167, 7.0F, BrandMark.GLYPH, colorScheme11.getIntValue13());
         float floatValue171 = floatValue161 + floatValue160 - metrics31.measure(11.0F);
         float floatValue172 = 1.0F - 2.0F * floatValue159;
         if (Math.abs(floatValue172) > 0.01F) {
            renderManager15.invoke62(floatValue172, floatValue171, floatValue162 + floatValue163 * 0.5F);

            try {
               ClickGuiRenderUtils.invoke4(
                  renderManager15,
                  metrics31,
                  FontRegistry.fontObject8,
                  floatValue171,
                  floatValue162,
                  floatValue163,
                  7.0F,
                  "k",
                  ColorScheme.compute7(colorScheme11.getIntValue12(), colorScheme11.getIntValue14(), floatValue166)
               );
            } finally {
               renderManager15.invoke64();
            }
         }

         String text18 = foundryShaderSetting7.resolve();
         int intValue31 = foundryShaderSetting7.check()
            ? ColorScheme.compute7(ClickGuiRenderUtils.compute5(colorScheme11), colorScheme11.getIntValue15(), 0.45F)
            : ColorScheme.compute7(ClickGuiRenderUtils.compute4(colorScheme11), ClickGuiRenderUtils.compute2(colorScheme11), floatValue165 * 0.48F + floatValue159 * 0.22F);
         ClickGuiRenderUtils.invoke4(
            renderManager15,
            metrics31,
            FontRegistry.fontObject,
            floatValue161 + metrics31.measure(20.0F),
            floatValue162,
            floatValue163,
            10.0F,
            ClickGuiRenderUtils.resolve3(FontRegistry.fontObject, text18, 10.0F, floatValue160 - metrics31.measure(36.0F)),
            intValue31
         );
      } finally {
         renderManager15.invoke64();
      }

      if (floatValue159 > 0.01F) {
         this.invoke14(renderManager15, clickGuiState11, foundryShaderSetting7, f, g, h, floatValue159, themeContext8);
      }
   }

   private void invoke14(
      RenderManager renderManager16, ClickGuiState clickGuiState12, FoundryShaderSetting foundryShaderSetting8, float f, float g, float h, float i, ThemeContext themeContext9
   ) {
      Metrics metrics32 = themeContext9.getMetrics();
      ColorScheme colorScheme12 = themeContext9.getColorScheme();
      foundryShaderSetting8.refreshOptions();
      float floatValue173 = measure10(h);
      float floatValue174 = measure11(f, h);
      float floatValue175 = g + metrics32.measure(18.0F) + metrics32.measure(5.0F);
      float floatValue176 = measure17(metrics32);
      float floatValue177 = metrics32.measure(4.0F);
      float floatValue178 = floatValue177 * 2.0F + foundryShaderSetting8.options.size() * floatValue176;
      float floatValue179 = metrics32.measure(8.0F);
      renderManager16.invoke65(i);

      try {
         renderManager16.invoke41(
            floatValue174,
            floatValue175,
            floatValue173,
            floatValue178 * i,
            floatValue179,
            metrics32.measure(14.0F),
            metrics32.measure(1.0F),
            ColorScheme.compute6(colorScheme12.getIntValue14(), Math.round(34.0F * i))
         );
         renderManager16.invoke5(floatValue174, floatValue175, floatValue173, floatValue178 * i, floatValue179, ColorScheme.compute7(colorScheme12.getIntValue4(), colorScheme12.getIntValue6(), 0.28F));
         renderManager16.invoke28(
            floatValue174, floatValue175, floatValue173, floatValue178 * i, floatValue179, ColorScheme.compute7(colorScheme12.getIntValue7(), ColorScheme.compute6(colorScheme12.getIntValue14(), 112), i), 0.55F
         );
         if (i > 0.45F) {
            for (int intValue32 = 0; intValue32 < foundryShaderSetting8.options.size(); intValue32++) {
               String text19 = foundryShaderSetting8.options.get(intValue32);
               boolean flag15 = foundryShaderSetting8.check3(text19);
               float floatValue180 = floatValue175 + floatValue177 + intValue32 * floatValue176;
               String text20 = AnimationKeyRegistry.resolve25(foundryShaderSetting8, intValue32);
               float floatValue181 = clickGuiState12.measure5(
                  text20, ClickGuiRenderUtils.check(clickGuiState12, floatValue174, floatValue180, floatValue173, floatValue176) ? 1.0F : 0.0F, SpringSpec.resolve11()
               );
               float floatValue182 = floatValue174 + metrics32.measure(7.0F);
               float floatValue183 = floatValue180 + metrics32.measure(6.0F);
               float floatValue184 = metrics32.measure(76.0F);
               float floatValue185 = floatValue176 - metrics32.measure(12.0F);
               if (flag15) {
                  renderManager16.invoke34(
                     floatValue174 + metrics32.measure(3.0F),
                     floatValue180 + metrics32.measure(1.0F),
                     floatValue173 - metrics32.measure(6.0F),
                     floatValue176 - metrics32.measure(2.0F),
                     metrics32.measure(6.0F),
                     ColorScheme.compute6(colorScheme12.getIntValue15(), 42),
                     ColorScheme.compute6(colorScheme12.getIntValue14(), 24)
                  );
                  renderManager16.invoke41(
                     floatValue174 + metrics32.measure(5.0F),
                     floatValue180 + metrics32.measure(4.0F),
                     floatValue173 - metrics32.measure(10.0F),
                     floatValue176 - metrics32.measure(8.0F),
                     metrics32.measure(7.0F),
                     metrics32.measure(10.0F),
                     metrics32.measure(1.0F),
                     ColorScheme.compute6(colorScheme12.getIntValue14(), Math.round(24.0F * i))
                  );
               } else if (floatValue181 > 0.01F) {
                  renderManager16.invoke5(
                     floatValue174 + metrics32.measure(3.0F),
                     floatValue180 + metrics32.measure(1.0F),
                     floatValue173 - metrics32.measure(6.0F),
                     floatValue176 - metrics32.measure(2.0F),
                     metrics32.measure(6.0F),
                     ColorScheme.compute7(colorScheme12.getIntValue3(), colorScheme12.getIntValue6(), floatValue181)
                  );
               }

               this.invoke15(renderManager16, clickGuiState12, themeContext9, foundryShaderSetting8, text19, floatValue182, floatValue183, floatValue184, floatValue185, i);
               float floatValue186 = floatValue182 + floatValue184 + metrics32.measure(10.0F);
               int intValue33 = flag15 ? colorScheme12.getIntValue14() : ColorScheme.compute7(ClickGuiRenderUtils.compute4(colorScheme12), ClickGuiRenderUtils.compute2(colorScheme12), floatValue181 * 0.55F);
               String text21 = this.resolve2(foundryShaderSetting8, text19);
               renderManager16.invoke62(
                  ClickGuiRenderUtils.measure8(floatValue181, clickGuiState12.measure8(text20), 0.01F, 0.003F), floatValue174 + floatValue173 * 0.5F, floatValue180 + floatValue176 * 0.5F
               );

               try {
                  ClickGuiRenderUtils.invoke4(
                     renderManager16,
                     metrics32,
                     FontRegistry.fontObject,
                     floatValue186,
                     floatValue180 + metrics32.measure(8.0F),
                     metrics32.measure(16.0F),
                     10.0F,
                     ClickGuiRenderUtils.resolve3(FontRegistry.fontObject, text19, 10.0F, floatValue174 + floatValue173 - metrics32.measure(12.0F) - floatValue186),
                     intValue33
                  );
                  ClickGuiRenderUtils.invoke4(
                     renderManager16,
                     metrics32,
                     FontRegistry.fontObject,
                     floatValue186,
                     floatValue180 + metrics32.measure(29.0F),
                     metrics32.measure(14.0F),
                     8.0F,
                     ClickGuiRenderUtils.resolve3(FontRegistry.fontObject, text21, 8.0F, floatValue174 + floatValue173 - metrics32.measure(12.0F) - floatValue186),
                     ColorScheme.compute7(colorScheme12.getIntValue11(), colorScheme12.getIntValue15(), flag15 ? 0.55F : floatValue181 * 0.38F)
                  );
               } finally {
                  renderManager16.invoke64();
               }
            }
         }
      } finally {
         renderManager16.invoke66();
      }
   }

   private void invoke15(
      RenderManager renderManager17,
      ClickGuiState clickGuiState13,
      ThemeContext themeContext10,
      FoundryShaderSetting foundryShaderSetting9,
      String string,
      float f,
      float g,
      float h,
      float i,
      float j
   ) {
      Metrics metrics33 = themeContext10.getMetrics();
      ColorScheme colorScheme13 = themeContext10.getColorScheme();
      float floatValue187 = metrics33.measure(5.0F);
      renderManager17.invoke5(f, g, h, i, floatValue187, ColorScheme.compute7(colorScheme13.getIntValue3(), colorScheme13.getIntValue4(), 0.5F));
      boolean flag16 = false;
      if (!"None".equalsIgnoreCase(string) && ShaderPresetRegistry.getINSTANCE().check2(string)) {
         MinecraftClient client = MinecraftClient.getInstance();
         int intValue34 = client == null ? Math.max(1, Math.round(f + h)) : Math.max(1, client.getWindow().getFramebufferWidth());
         int intValue35 = client == null ? Math.max(1, Math.round(g + i)) : Math.max(1, client.getWindow().getFramebufferHeight());
         ShaderNode shaderNode = ShaderPresetRegistry.getINSTANCE().resolve4(string);
         ShaderSurface shaderSurface = ShaderSurface.resolve4(shaderNode == null ? null : shaderNode.getPreview());
         if (shaderSurface == ShaderSurface.PREVIEW_ONLY) {
            shaderSurface = foundryShaderSetting9.shaderSurface;
         }

         if (shaderNode != null) {
            ShaderPreviewRenderer.invoke2(
               renderManager17, themeContext10, string, shaderSurface, shaderNode, f, g, h, i, intValue34, intValue35, clickGuiState13.getFloatValue(), clickGuiState13.getFloatValue2(), j
            );
            flag16 = true;
         }
      }

      if (!flag16) {
         if ("None".equalsIgnoreCase(string)) {
            renderManager17.invoke28(
               f + metrics33.measure(5.0F),
               g + metrics33.measure(5.0F),
               h - metrics33.measure(10.0F),
               i - metrics33.measure(10.0F),
               metrics33.measure(4.0F),
               colorScheme13.getIntValue8(),
               0.6F
            );
         } else {
            renderManager17.invoke37(f, g, h, i, floatValue187, ColorScheme.compute6(colorScheme13.getIntValue14(), 72), ColorScheme.compute6(colorScheme13.getIntValue15(), 48));
         }
      }

      renderManager17.invoke28(f, g, h, i, floatValue187, ColorScheme.compute6(colorScheme13.getIntValue14(), Math.round(70.0F * j)), 0.55F);
   }

   private String resolve2(FoundryShaderSetting foundryShaderSetting10, String string) {
      if ("None".equalsIgnoreCase(string)) {
         return foundryShaderSetting10.shaderSurface.getText2() + " slot hidden";
      } else {
         ShaderNode shaderNode2 = ShaderPresetRegistry.getINSTANCE().resolve4(string);
         ShaderSurface shaderSurface2 = ShaderSurface.resolve4(shaderNode2 == null ? null : shaderNode2.getPreview());
         if (shaderSurface2 == ShaderSurface.PREVIEW_ONLY) {
            shaderSurface2 = foundryShaderSetting10.shaderSurface;
         }

         int intValue36 = ShaderPresetRegistry.getINSTANCE().resolve11(string).size();
         ShaderPresetRegistry.ShaderPresetRegistryState2 shaderPresetRegistryState2 = ShaderPresetRegistry.getINSTANCE().resolve7(string);
         ShaderPresetRegistry.ShaderPresetRegistryState shaderPresetRegistryState = ShaderPresetRegistry.getINSTANCE().resolve8(string);
         return shaderPresetRegistryState2.name().toLowerCase(Locale.ROOT) + " / " + shaderSurface2.getText2() + " / " + intValue36 + " uniforms / " + shaderPresetRegistryState.name().toLowerCase(Locale.ROOT);
      }
   }

   private void invoke16(
      RenderManager renderManager18, ClickGuiState clickGuiState14, GroupSetting groupSetting5, float f, float g, float h, ThemeContext themeContext11
   ) {
      Metrics metrics34 = themeContext11.getMetrics();
      ColorScheme colorScheme14 = themeContext11.getColorScheme();
      float floatValue188 = this.measure2((Setting)groupSetting5, metrics34);
      float floatValue189 = metrics34.measure(14.0F);
      float floatValue190 = metrics34.measure(3.0F);
      float floatValue191 = metrics34.measure(3.0F);
      float floatValue192 = h * 0.7F;
      float floatValue193 = f + h - floatValue192;
      this.invoke18(renderManager18, metrics34, groupSetting5.name, f, g, floatValue188, 12.0F, floatValue193 - f - metrics34.measure(8.0F), ClickGuiRenderUtils.compute2(colorScheme14));
      float floatValue194 = 0.0F;
      int intValue37 = 0;
      float floatValue195 = metrics34.measure(3.0F);

      for (int intValue38 = 0; intValue38 < groupSetting5.options.size(); intValue38++) {
         BooleanSetting booleanSetting3 = groupSetting5.options.get(intValue38);
         float floatValue196 = clickGuiState14.measure5(
            AnimationKeyRegistry.resolve23(groupSetting5, intValue38), booleanSetting3.isEnabled() ? 1.0F : 0.0F, SpringSpec.resolve11()
         );
         boolean flag17 = clickGuiState14.getBooleanSetting() == booleanSetting3;
         String text22 = ClickGuiRenderUtils.resolve(booleanSetting3);
         float floatValue197 = ClickGuiRenderUtils.measure(FontRegistry.fontObject, text22, 8.0F);
         float floatValue198 = Math.max(metrics34.measure(18.0F), floatValue197 + metrics34.measure(8.0F));
         if (floatValue194 > 0.0F && floatValue194 + floatValue198 > floatValue192) {
            intValue37++;
            floatValue194 = 0.0F;
         }

         float floatValue199 = floatValue193 + floatValue194;
         float floatValue200 = g + metrics34.measure(1.0F) + intValue37 * (floatValue189 + floatValue195);
         boolean flag18 = ClickGuiRenderUtils.check(clickGuiState14, floatValue199, floatValue200 - metrics34.measure(1.0F), floatValue198, floatValue189 + metrics34.measure(2.0F));
         String text23 = AnimationKeyRegistry.resolve24(groupSetting5, intValue38);
         float floatValue201 = clickGuiState14.measure5(text23, flag18 ? 1.0F : 0.0F, SpringSpec.resolve11());
         float floatValue202 = Math.max(floatValue196, Math.max(floatValue201 * 0.72F, flag17 ? 1.0F : 0.0F));
         renderManager18.invoke62(ClickGuiRenderUtils.measure8(floatValue201, clickGuiState14.measure8(text23), 0.026F, 0.008F), floatValue199 + floatValue198 * 0.5F, floatValue200 + floatValue189 * 0.5F);

         try {
            if (floatValue196 > 0.01F) {
               renderManager18.invoke34(
                  floatValue199,
                  floatValue200,
                  floatValue198,
                  floatValue189,
                  floatValue191,
                  ColorScheme.compute6(colorScheme14.getIntValue15(), Math.round(180.0F * floatValue196)),
                  ColorScheme.compute6(colorScheme14.getIntValue14(), Math.round(180.0F * floatValue196))
               );
            }

            if (floatValue196 <= 0.5F) {
               renderManager18.invoke5(floatValue199, floatValue200, floatValue198, floatValue189, floatValue191, ColorScheme.compute7(colorScheme14.getIntValue4(), colorScheme14.getIntValue6(), floatValue202));
            }

            renderManager18.invoke28(
               floatValue199,
               floatValue200,
               floatValue198,
               floatValue189,
               floatValue191,
               ColorScheme.compute7(colorScheme14.getIntValue6(), ColorScheme.compute6(colorScheme14.getIntValue14(), 100), Math.max(floatValue202, floatValue201)),
               0.5F
            );
            int intValue39 = ColorScheme.compute7(
               ColorScheme.compute7(ClickGuiRenderUtils.compute5(colorScheme14), ClickGuiRenderUtils.compute4(colorScheme14), ClickGuiRenderUtils.measure9(floatValue201)),
               ClickGuiRenderUtils.compute2(colorScheme14),
               floatValue196
            );
            String text24 = flag17 ? "..." : ClickGuiRenderUtils.resolve3(FontRegistry.fontObject, text22, 8.0F, floatValue198 - metrics34.measure(6.0F));
            float floatValue203 = ClickGuiRenderUtils.measure(FontRegistry.fontObject, text24, 8.0F);
            ClickGuiRenderUtils.invoke4(renderManager18, metrics34, FontRegistry.fontObject, floatValue199 + (floatValue198 - floatValue203) * 0.5F, floatValue200, floatValue189, 8.0F, text24, intValue39);
         } finally {
            renderManager18.invoke64();
         }

         floatValue194 += floatValue198 + floatValue190;
      }
   }

   private void invoke17(
      RenderManager renderManager19,
      ClickGuiState clickGuiState15,
      Setting setting4,
      String string,
      String string2,
      float f,
      float g,
      float h,
      ThemeContext themeContext12
   ) {
      Metrics metrics35 = themeContext12.getMetrics();
      ColorScheme colorScheme15 = themeContext12.getColorScheme();
      float floatValue204 = metrics35.measure(14.0F);
      String text25 = string2 == null ? "" : string2;
      float floatValue205 = ClickGuiRenderUtils.measure(FontRegistry.fontObject, text25, 10.0F);
      float floatValue206 = Math.min(h * 0.5F, floatValue205);
      float floatValue207 = Math.max(metrics35.measure(36.0F), floatValue206 + metrics35.measure(14.0F));
      float floatValue208 = metrics35.measure(16.0F);
      float floatValue209 = f + h - floatValue207;
      float floatValue210 = g + (floatValue204 - floatValue208) * 0.5F;
      float floatValue211 = metrics35.measure(5.0F);
      this.invoke18(renderManager19, metrics35, string, f, g, floatValue204, 12.0F, floatValue209 - f - metrics35.measure(8.0F), ClickGuiRenderUtils.compute2(colorScheme15));
      String text26 = AnimationKeyRegistry.resolve21(setting4);
      float floatValue212 = clickGuiState15.measure5(
         text26, ClickGuiRenderUtils.check(clickGuiState15, floatValue209, floatValue210, floatValue207, floatValue208) ? 1.0F : 0.0F, SpringSpec.resolve11()
      );
      renderManager19.invoke62(ClickGuiRenderUtils.measure7(floatValue212, clickGuiState15.measure8(text26)), floatValue209 + floatValue207 * 0.5F, floatValue210 + floatValue208 * 0.5F);

      try {
         renderManager19.invoke5(floatValue209, floatValue210, floatValue207, floatValue208, floatValue211, ColorScheme.compute7(colorScheme15.getIntValue4(), colorScheme15.getIntValue6(), floatValue212 * 0.72F));
         renderManager19.invoke28(
            floatValue209, floatValue210, floatValue207, floatValue208, floatValue211, ColorScheme.compute7(colorScheme15.getIntValue6(), ColorScheme.compute6(colorScheme15.getIntValue14(), 95), floatValue212), 0.5F
         );
         String text27 = ClickGuiRenderUtils.resolve3(FontRegistry.fontObject, text25, 10.0F, floatValue207 - metrics35.measure(8.0F));
         float floatValue213 = ClickGuiRenderUtils.measure(FontRegistry.fontObject, text27, 10.0F);
         float floatValue214 = floatValue209 + (floatValue207 - floatValue213) * 0.5F;
         ClickGuiRenderUtils.invoke4(
            renderManager19,
            metrics35,
            FontRegistry.fontObject,
            floatValue214,
            floatValue210,
            floatValue208,
            10.0F,
            text27,
            ColorScheme.compute7(ClickGuiRenderUtils.compute4(colorScheme15), ClickGuiRenderUtils.compute2(colorScheme15), floatValue212 * 0.46F)
         );
      } finally {
         renderManager19.invoke64();
      }
   }

   private void invoke18(RenderManager renderManager20, Metrics metrics36, String string, float f, float g, float h, float i, float j, int k) {
      if (string != null && !string.isEmpty() && !(j <= 1.0F) && !(h <= 1.0F)) {
         float floatValue215 = ClickGuiRenderUtils.measure2(metrics36, FontRegistry.fontObject4, string, i);
         if (floatValue215 <= j) {
            ClickGuiRenderUtils.invoke4(renderManager20, metrics36, FontRegistry.fontObject4, f, g, h, i, string, k);
         } else {
            float floatValue216 = floatValue215 - j;
            float floatValue217 = floatValue216 * this.measure28();
            renderManager20.invoke24(f, g, Math.max(1.0F, j), h, 0.0F, 0.0F, 0.0F, 0.0F);
            ClickGuiRenderUtils.invoke4(renderManager20, metrics36, FontRegistry.fontObject4, f - floatValue217, g, h, i, string, k);
            renderManager20.invoke25();
         }
      }
   }

   private float measure28() {
      float floatValue218 = (float)(System.currentTimeMillis() % 5200L) / 5200.0F;
      if (floatValue218 < 0.22F) {
         return 0.0F;
      } else if (floatValue218 < 0.46F) {
         return this.measure29((floatValue218 - 0.22F) / 0.24F);
      } else if (floatValue218 < 0.62F) {
         return 1.0F;
      } else {
         return floatValue218 < 0.86F ? 1.0F - this.measure29((floatValue218 - 0.62F) / 0.24F) : 0.0F;
      }
   }

   private float measure29(float f) {
      float floatValue219 = Math.max(0.0F, Math.min(1.0F, f));
      return floatValue219 * floatValue219 * floatValue219 * (floatValue219 * (floatValue219 * 6.0F - 15.0F) + 10.0F);
   }
}
