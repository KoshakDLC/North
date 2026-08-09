package ru.metaculture.protection;

public final class ClickGuiKeyboardController {
   public boolean check(ClickGuiState clickGuiState, int i) {
      if (clickGuiState.getModule() != null) {
         clickGuiState.invoke47(i);
         return true;
      } else if (clickGuiState.getKeybindSetting() != null) {
         clickGuiState.invoke48(i);
         return true;
      } else if (clickGuiState.getBooleanSetting() != null) {
         clickGuiState.invoke49(i);
         return true;
      } else if (clickGuiState.getColorSetting3() != null) {
         this.invoke4(clickGuiState, i);
         return true;
      } else if (clickGuiState.getColorSetting4() != null) {
         this.invoke6(clickGuiState, i);
         return true;
      } else if (clickGuiState.getTextSetting() != null) {
         this.invoke(clickGuiState, i);
         return true;
      } else if (clickGuiState.isFlag6()) {
         this.invoke3(clickGuiState, i);
         return true;
      } else if (clickGuiState.isFlag5()) {
         this.invoke2(clickGuiState, i);
         return true;
      } else {
         return false;
      }
   }

   public boolean check2(ClickGuiState clickGuiState2, char c) {
      if (clickGuiState2.getColorSetting3() != null) {
         this.invoke5(clickGuiState2, c);
         return true;
      } else if (clickGuiState2.getColorSetting4() != null) {
         this.invoke7(clickGuiState2, c);
         return true;
      } else if (clickGuiState2.getTextSetting() != null) {
         if (!Character.isISOControl(c)) {
            TextSetting textSetting = clickGuiState2.getTextSetting();
            textSetting.value = textSetting.value + c;
            clickGuiState2.invoke66();
         }

         return true;
      } else if (clickGuiState2.isFlag6()) {
         if (!Character.isISOControl(c)) {
            clickGuiState2.invoke27(c);
         }

         return true;
      } else if (clickGuiState2.isFlag5()) {
         if (!Character.isISOControl(c)) {
            clickGuiState2.invoke23(c);
         }

         return true;
      } else {
         return false;
      }
   }

   private void invoke(ClickGuiState clickGuiState3, int i) {
      if (i == 256 || i == 257) {
         clickGuiState3.setNumberSetting((NumberSetting)null);
      } else if (i == 259 && !clickGuiState3.getTextSetting().value.isEmpty()) {
         String text = clickGuiState3.getTextSetting().value;
         clickGuiState3.getTextSetting().value = text.substring(0, text.length() - 1);
         clickGuiState3.invoke66();
      }
   }

   private void invoke2(ClickGuiState clickGuiState4, int i) {
      if (i == 256 || i == 257) {
         clickGuiState4.setFlag5(false);
      } else if (i == 259) {
         clickGuiState4.invoke24();
      }
   }

   private void invoke3(ClickGuiState clickGuiState5, int i) {
      if (i == 256) {
         clickGuiState5.invoke26();
         clickGuiState5.setFlag6(false);
      } else if (i == 257) {
         clickGuiState5.setFlag6(false);
      } else if (i == 259) {
         clickGuiState5.invoke28();
      }
   }

   private void invoke4(ClickGuiState clickGuiState6, int i) {
      ColorSetting colorSetting = clickGuiState6.getColorSetting3();
      if (colorSetting != null) {
         if (i == 256) {
            clickGuiState6.setColorSetting3((ColorSetting)null);
            clickGuiState6.setText4("");
         } else if (i != 257 && i != 258) {
            if (i == 259) {
               String text2 = clickGuiState6.getText4();
               if (text2 != null && !text2.isEmpty()) {
                  clickGuiState6.setText4(text2.substring(0, text2.length() - 1));
               }
            }
         } else {
            this.invoke8(clickGuiState6, colorSetting);
            clickGuiState6.setColorSetting3((ColorSetting)null);
            clickGuiState6.setText4("");
         }
      }
   }

   private void invoke5(ClickGuiState clickGuiState7, char c) {
      if (check3(c)) {
         String text3 = clickGuiState7.getText4();
         if (text3 == null) {
            text3 = "";
         }

         if (text3.length() < 8) {
            clickGuiState7.setText4(text3 + Character.toUpperCase(c));
         }
      }
   }

   private void invoke6(ClickGuiState clickGuiState8, int i) {
      ColorSetting colorSetting2 = clickGuiState8.getColorSetting4();
      if (colorSetting2 != null) {
         if (i == 256) {
            clickGuiState8.setColorSetting4((ColorSetting)null);
            clickGuiState8.setText5("");
         } else if (i != 257 && i != 258) {
            if (i == 259) {
               String text4 = clickGuiState8.getText5();
               if (text4 != null && !text4.isEmpty()) {
                  clickGuiState8.setText5(text4.substring(0, text4.length() - 1));
               }
            }
         } else {
            this.invoke9(clickGuiState8, colorSetting2);
            clickGuiState8.setColorSetting4((ColorSetting)null);
            clickGuiState8.setText5("");
         }
      }
   }

   private void invoke7(ClickGuiState clickGuiState9, char c) {
      if (c >= '0' && c <= '9') {
         String text5 = clickGuiState9.getText5();
         if (text5 == null) {
            text5 = "";
         }

         if (text5.length() < 3) {
            clickGuiState9.setText5(text5 + c);
         }
      }
   }

   private void invoke8(ClickGuiState clickGuiState10, ColorSetting colorSetting3) {
      String text6 = clickGuiState10.getText4();
      if (text6 != null) {
         String text7 = text6.trim();
         if (text7.startsWith("#")) {
            text7 = text7.substring(1);
         }

         if (!text7.isEmpty()) {
            try {
               long longValue = Long.parseUnsignedLong(text7, 16);
               int intValue;
               switch (text7.length()) {
                  case 3:
                     int intValue2 = ((int)(longValue >> 8) & 15) * 17;
                     int intValue3 = ((int)(longValue >> 4) & 15) * 17;
                     int intValue4 = ((int)longValue & 15) * 17;
                     int intValue5 = Math.round(colorSetting3.floatValue3 * 255.0F) & 0xFF;
                     intValue = intValue5 << 24 | intValue2 << 16 | intValue3 << 8 | intValue4;
                     break;
                  case 4:
                     int intValue6 = ((int)(longValue >> 12) & 15) * 17;
                     int intValue7 = ((int)(longValue >> 8) & 15) * 17;
                     int intValue8 = ((int)(longValue >> 4) & 15) * 17;
                     int intValue9 = ((int)longValue & 15) * 17;
                     intValue = intValue9 << 24 | intValue6 << 16 | intValue7 << 8 | intValue8;
                     break;
                  case 5:
                  case 7:
                  default:
                     return;
                  case 6:
                     int intValue10 = (int)longValue & 16777215;
                     int intValue11 = Math.round(colorSetting3.floatValue3 * 255.0F) & 0xFF;
                     intValue = intValue11 << 24 | intValue10;
                     break;
                  case 8:
                     intValue = (int)longValue;
               }

               colorSetting3.invoke2(intValue);
               clickGuiState10.invoke66();
            } catch (NumberFormatException numberFormatException) {
            }
         }
      }
   }

   private void invoke9(ClickGuiState clickGuiState11, ColorSetting colorSetting4) {
      String text8 = clickGuiState11.getText5();
      if (text8 != null && !text8.isEmpty()) {
         try {
            int intValue12 = Integer.parseUnsignedInt(text8);
            if (intValue12 < 0) {
               intValue12 = 0;
            }

            if (intValue12 > 100) {
               intValue12 = 100;
            }

            colorSetting4.setFloatValue3(intValue12 / 100.0F);
            clickGuiState11.invoke66();
         } catch (NumberFormatException numberFormatException2) {
         }
      }
   }

   private static boolean check3(char c) {
      return c >= '0' && c <= '9' || c >= 'a' && c <= 'f' || c >= 'A' && c <= 'F';
   }
}
