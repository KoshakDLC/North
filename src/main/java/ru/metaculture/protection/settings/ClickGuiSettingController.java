package ru.metaculture.protection;

public final class ClickGuiSettingController {
   public void invoke(ClickGuiState clickGuiState, Setting setting, float f, float g, float h) {
      clickGuiState.setFlag5(false);
      clickGuiState.setNumberSetting((NumberSetting)null);
      if (setting instanceof BooleanSetting booleanSetting) {
         booleanSetting.setValue(!booleanSetting.getValue());
         clickGuiState.invoke66();
      } else if (setting instanceof NumberSetting numberSetting) {
         clickGuiState.setNumberSetting(numberSetting);
         clickGuiState.setFloatValue30(g);
         clickGuiState.setFloatValue31(h);
         this.invoke13(numberSetting, f, g, h);
         clickGuiState.invoke66();
      } else if (setting instanceof ColorSetting colorSetting) {
         clickGuiState.invoke37(colorSetting);
      } else if (setting instanceof ModeSetting modeSetting) {
         clickGuiState.invoke31(modeSetting);
      } else if (setting instanceof FoundryShaderSetting foundryShaderSetting) {
         clickGuiState.invoke34(foundryShaderSetting);
      } else if (setting instanceof MultiSelectSetting multiSelectSetting) {
         multiSelectSetting.refreshOptions();
         if (!multiSelectSetting.options.isEmpty()) {
            this.invoke14(multiSelectSetting);
            clickGuiState.invoke66();
         }
      } else if (setting instanceof KeybindSetting keybindSetting) {
         clickGuiState.invoke45(keybindSetting);
      } else if (setting instanceof TextSetting textSetting) {
         clickGuiState.invoke52(textSetting);
      } else if (setting instanceof ButtonSetting buttonSetting) {
         buttonSetting.invoke8();
      }
   }

   public void invoke2(ClickGuiState clickGuiState2, ColorSetting colorSetting2, float f, float g) {
      clickGuiState2.setFlag18(true);
      clickGuiState2.setFlag19(false);
      clickGuiState2.setFlag20(false);
      this.invoke10(clickGuiState2, colorSetting2, f, g);
   }

   public void invoke3(ClickGuiState clickGuiState3, ColorSetting colorSetting3, float f, float g) {
      clickGuiState3.setFlag19(true);
      clickGuiState3.setFlag18(false);
      clickGuiState3.setFlag20(false);
      this.invoke11(clickGuiState3, colorSetting3, g);
   }

   public void invoke4(ClickGuiState clickGuiState4, ColorSetting colorSetting4, float f) {
      clickGuiState4.setFlag20(true);
      clickGuiState4.setFlag18(false);
      clickGuiState4.setFlag19(false);
      this.invoke12(clickGuiState4, colorSetting4, f);
   }

   public void invoke5(ClickGuiState clickGuiState5, ColorSetting colorSetting5, float f) {
      float floatValue = clickGuiState5.getFloatValue46();
      float floatValue2 = clickGuiState5.getFloatValue48();
      if (!(floatValue2 < 1.0F)) {
         byte byteValue = 5;
         int intValue = Math.max(0, Math.min(byteValue - 1, (int)((f - floatValue) / floatValue2 * byteValue)));
         float[] floatValues = new float[]{0.0F, 180.0F, -30.0F, 30.0F, 120.0F};
         colorSetting5.invoke3(colorSetting5.measure2() + floatValues[intValue]);
         if (colorSetting5.saturation < 0.05F) {
            colorSetting5.saturation = 0.65F;
         }

         if (colorSetting5.brightness < 0.08F) {
            colorSetting5.brightness = 0.85F;
         }

         clickGuiState5.invoke66();
      }
   }

   public void invoke6(ClickGuiState clickGuiState6, ColorSetting colorSetting6, float f, boolean bl) {
      float floatValue3 = clickGuiState6.getFloatValue50();
      float floatValue4 = clickGuiState6.getFloatValue52();
      if (!(floatValue4 < 1.0F)) {
         byte byteValue2 = 9;
         int intValue2 = Math.max(0, Math.min(byteValue2 - 1, (int)((f - floatValue3) / floatValue4 * byteValue2)));
         if (intValue2 == 8) {
            if (!bl) {
               colorSetting6.invoke5();
               clickGuiState6.invoke66();
            }
         } else {
            if (bl) {
               colorSetting6.invoke7(intValue2);
            } else {
               colorSetting6.invoke6(intValue2);
            }

            clickGuiState6.invoke66();
         }
      }
   }

   public void invoke7(ClickGuiState clickGuiState7, float f) {
      if (clickGuiState7.getNumberSetting() != null) {
         this.invoke13(clickGuiState7.getNumberSetting(), f, clickGuiState7.getFloatValue30(), clickGuiState7.getFloatValue31());
      }
   }

   public void invoke8(ClickGuiState clickGuiState8, float f, float g) {
      if (clickGuiState8.SpacerSetting() && clickGuiState8.getColorSetting2() != null) {
         this.invoke10(clickGuiState8, clickGuiState8.getColorSetting2(), f, g);
      }

      if (clickGuiState8.FoundryShaderSetting() && clickGuiState8.getColorSetting2() != null) {
         this.invoke11(clickGuiState8, clickGuiState8.getColorSetting2(), g);
      }

      if (clickGuiState8.isFlag20() && clickGuiState8.getColorSetting2() != null) {
         this.invoke12(clickGuiState8, clickGuiState8.getColorSetting2(), f);
      }
   }

   public void invoke9(ClickGuiState clickGuiState9) {
      if (clickGuiState9.SpacerSetting() || clickGuiState9.FoundryShaderSetting() || clickGuiState9.isFlag20()) {
         clickGuiState9.setFlag18(false);
         clickGuiState9.setFlag19(false);
         clickGuiState9.setFlag20(false);
         clickGuiState9.invoke66();
      }
   }

   private void invoke10(ClickGuiState clickGuiState10, ColorSetting colorSetting7, float f, float g) {
      float floatValue5 = clickGuiState10.getFloatValue34();
      float floatValue6 = clickGuiState10.getFloatValue35();
      float floatValue7 = clickGuiState10.getFloatValue36();
      float floatValue8 = clickGuiState10.getFloatValue37();
      if (!(floatValue7 < 1.0F) && !(floatValue8 < 1.0F)) {
         float floatValue9 = Math.max(0.0F, Math.min(1.0F, (f - floatValue5) / floatValue7));
         float floatValue10 = Math.max(0.0F, Math.min(1.0F, (g - floatValue6) / floatValue8));
         colorSetting7.saturation = floatValue9;
         colorSetting7.brightness = 1.0F - floatValue10;
      }
   }

   private void invoke11(ClickGuiState clickGuiState11, ColorSetting colorSetting8, float f) {
      float floatValue11 = clickGuiState11.getFloatValue39();
      float floatValue12 = clickGuiState11.getFloatValue41();
      if (!(floatValue12 < 1.0F)) {
         float floatValue13 = Math.max(0.0F, Math.min(1.0F, (f - floatValue11) / floatValue12));
         colorSetting8.invoke3(floatValue13 * 360.0F);
      }
   }

   private void invoke12(ClickGuiState clickGuiState12, ColorSetting colorSetting9, float f) {
      float floatValue14 = clickGuiState12.getFloatValue42();
      float floatValue15 = clickGuiState12.getFloatValue44();
      if (!(floatValue15 < 1.0F)) {
         colorSetting9.setFloatValue3((f - floatValue14) / floatValue15);
      }
   }

   private void invoke13(NumberSetting numberSetting2, float f, float g, float h) {
      float floatValue16 = Math.max(0.0F, Math.min(1.0F, (f - g) / Math.max(1.0F, h)));
      float floatValue17 = numberSetting2.minimum + (numberSetting2.maximum - numberSetting2.minimum) * floatValue16;
      if (numberSetting2.step > 0.0F) {
         floatValue17 = Math.round(floatValue17 / numberSetting2.step) * numberSetting2.step;
      }

      numberSetting2.value = Math.max(numberSetting2.minimum, Math.min(numberSetting2.maximum, floatValue17));
   }

   private void invoke14(MultiSelectSetting multiSelectSetting2) {
      multiSelectSetting2.refreshOptions();
      if (!multiSelectSetting2.options.isEmpty()) {
         String text = multiSelectSetting2.options.get(0);
         if (!multiSelectSetting2.selectedValues.isEmpty()) {
            int intValue3 = multiSelectSetting2.options.indexOf(multiSelectSetting2.selectedValues.get(multiSelectSetting2.selectedValues.size() - 1));
            text = multiSelectSetting2.options.get((intValue3 + 1 + multiSelectSetting2.options.size()) % multiSelectSetting2.options.size());
         }

         multiSelectSetting2.selectedValues.clear();
         multiSelectSetting2.selectedValues.add(text);
      }
   }
}
