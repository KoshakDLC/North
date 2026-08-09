package ru.metaculture.protection;

import java.util.Objects;

public final class FontRenderer {
   private static final int INT_VALUE = 1710618;
   private static final int INT_VALUE_2 = 6710886;
   private static volatile boolean batchingEnabled;
   private static final float[] FLOATS = new float[]{1.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 1.0F};
   private final RenderEngine renderEngine;
   private final MsdfFontAtlas msdfFontAtlas;

   public FontRenderer(RenderEngine renderEngine, MsdfFontAtlas msdfFontAtlas) {
      this.renderEngine = Objects.requireNonNull(renderEngine, "backend");
      this.msdfFontAtlas = Objects.requireNonNull(msdfFontAtlas, "font");
   }

   public static boolean check(boolean bl) {
      boolean previousValue = batchingEnabled;
      batchingEnabled = bl;
      return previousValue;
   }

   public void invoke(float f, float g, float h, String string, int i) {
      this.invoke4(f, g, h, string, i, "l", FLOATS);
   }

   public void invoke2(float f, float g, float h, String string, int i, float[] fs) {
      this.invoke4(f, g, h, string, i, "l", fs);
   }

   public void invoke3(float f, float g, float h, String string, int i, String string2) {
      this.invoke4(f, g, h, string, i, string2, FLOATS);
   }

   public void invoke4(float f, float g, float h, String string, int i, String string2, float[] fs) {
      if (!(h <= 0.0F)) {
         String text = string == null ? "" : string;
         if (!text.isEmpty()) {
            float[] floatValues = fs != null && fs.length >= 6 ? fs : FLOATS;
            float floatValue = h / Math.max(1.0E-6F, this.msdfFontAtlas.getFloatValue2());
            float floatValue2 = this.msdfFontAtlas.getFloatValue3() * floatValue;
            float floatValue3 = g;
            String text2 = string2 == null ? "l" : string2.toLowerCase();
            int intValue = compute2(i);
            int intValue2 = this.msdfFontAtlas.getIntValue();
            float floatValue4 = this.msdfFontAtlas.getFloatValue();
            String[] texts = text.split("\\n", -1);

            for (String text3 : texts) {
               float floatValue5 = this.measure(text3, floatValue);
               float floatValue6 = f;
               if ("c".equals(text2)) {
                  floatValue6 = f - floatValue5 * 0.5F;
               } else if ("r".equals(text2)) {
                  floatValue6 = f - floatValue5;
               }

               this.invoke6(floatValue6, floatValue3, floatValue, text3, intValue, floatValues, intValue2, floatValue4);
               floatValue3 += floatValue2;
            }
         }
      }
   }

   public void invoke5(float f, float g, float h, String string, int i, int j, float k, String string2, float[] fs) {
      if (!(h <= 0.0F)) {
         String text4 = string == null ? "" : string;
         if (!text4.isEmpty()) {
            float[] floatValues2 = fs != null && fs.length >= 6 ? fs : FLOATS;
            float floatValue7 = h / Math.max(1.0E-6F, this.msdfFontAtlas.getFloatValue2());
            float floatValue8 = this.msdfFontAtlas.getFloatValue3() * floatValue7;
            float floatValue9 = g;
            String text5 = string2 == null ? "l" : string2.toLowerCase();
            int intValue3 = this.msdfFontAtlas.getIntValue();
            float floatValue10 = this.msdfFontAtlas.getFloatValue();
            int intValue4 = compute2(i);
            int intValue5 = compute2(j);
            String[] texts2 = text4.split("\\n", -1);

            for (String text6 : texts2) {
               float floatValue11 = this.measure(text6, floatValue7);
               float floatValue12 = f;
               if ("c".equals(text5)) {
                  floatValue12 = f - floatValue11 * 0.5F;
               } else if ("r".equals(text5)) {
                  floatValue12 = f - floatValue11;
               }

               this.invoke7(floatValue12, floatValue9, floatValue7, text6, intValue4, intValue5, k, Math.max(floatValue11, 1.0E-6F), floatValues2, intValue3, floatValue10);
               floatValue9 += floatValue8;
            }
         }
      }
   }

   private void invoke6(float f, float g, float h, String string, int i, float[] fs, int j, float k) {
      if (!string.isEmpty()) {
         float floatValue13 = f;
         float floatValue14 = g;
         int intValue6 = -1;
         int intValue7 = 0;

         while (intValue7 < string.length()) {
            char character = string.charAt(intValue7);
            if (character == '\\' && intValue7 + 9 < string.length() && string.charAt(intValue7 + 1) == 'c') {
               intValue7 += 10;
            } else {
               int intValue8 = string.codePointAt(intValue7);
               int intValue9 = Character.charCount(intValue8);
               intValue7 += intValue9;
               MsdfFontAtlas.MsdfFontAtlasState msdfFontAtlasState = this.msdfFontAtlas.resolve3(intValue8);
               int intValue10 = intValue8;
               if (msdfFontAtlasState == null) {
                  int intValue11 = compute(intValue8);
                  if (intValue11 != intValue8) {
                     msdfFontAtlasState = this.msdfFontAtlas.resolve3(intValue11);
                     intValue10 = intValue11;
                  }
               }

               if (msdfFontAtlasState == null) {
                  msdfFontAtlasState = this.msdfFontAtlas.resolve3(63);
                  intValue10 = 63;
                  if (msdfFontAtlasState == null) {
                     continue;
                  }
               }

               if (intValue6 != -1) {
                  floatValue13 += this.msdfFontAtlas.measure(intValue6, intValue10) * h;
               }

               if (msdfFontAtlasState.flag) {
                  float floatValue15 = floatValue13 + msdfFontAtlasState.floatValue2 * h;
                  float floatValue16 = floatValue14 - msdfFontAtlasState.floatValue5 * h;
                  float floatValue17 = floatValue13 + msdfFontAtlasState.floatValue4 * h;
                  float floatValue18 = floatValue14 - msdfFontAtlasState.floatValue3 * h;
                  float floatValue19 = floatValue17 - floatValue15;
                  float floatValue20 = floatValue18 - floatValue16;
                  if (floatValue19 > 0.0F && floatValue20 > 0.0F) {
                     this.renderEngine
                        .invoke45(
                           j, k, floatValue15, floatValue16, floatValue19, floatValue20, msdfFontAtlasState.floatValue6, msdfFontAtlasState.floatValue9, msdfFontAtlasState.floatValue8, msdfFontAtlasState.floatValue7, i, fs
                        );
                  }
               }

               floatValue13 += msdfFontAtlasState.floatValue * h;
               intValue6 = intValue10;
            }
         }
      }
   }

   private void invoke7(float f, float g, float h, String string, int i, int j, float k, float l, float[] fs, int m, float n) {
      if (!string.isEmpty()) {
         float floatValue21 = f;
         float floatValue22 = g;
         int intValue12 = -1;
         int intValue13 = 0;

         while (intValue13 < string.length()) {
            char character2 = string.charAt(intValue13);
            if (character2 == '\\' && intValue13 + 9 < string.length() && string.charAt(intValue13 + 1) == 'c') {
               intValue13 += 10;
            } else {
               int intValue14 = string.codePointAt(intValue13);
               int intValue15 = Character.charCount(intValue14);
               intValue13 += intValue15;
               MsdfFontAtlas.MsdfFontAtlasState msdfFontAtlasState2 = this.msdfFontAtlas.resolve3(intValue14);
               int intValue16 = intValue14;
               if (msdfFontAtlasState2 == null) {
                  int intValue17 = compute(intValue14);
                  if (intValue17 != intValue14) {
                     msdfFontAtlasState2 = this.msdfFontAtlas.resolve3(intValue17);
                     intValue16 = intValue17;
                  }
               }

               if (msdfFontAtlasState2 == null) {
                  msdfFontAtlasState2 = this.msdfFontAtlas.resolve3(63);
                  intValue16 = 63;
                  if (msdfFontAtlasState2 == null) {
                     continue;
                  }
               }

               if (intValue12 != -1) {
                  floatValue21 += this.msdfFontAtlas.measure(intValue12, intValue16) * h;
               }

               float floatValue23 = (floatValue21 - f + msdfFontAtlasState2.floatValue * h * 0.5F) / l;
               float floatValue24 = 0.5F + 0.5F * (float)Math.sin((floatValue23 * 1.55F + k) * Math.PI * 2.0);
               int intValue18 = compute3(i, j, floatValue24);
               if (msdfFontAtlasState2.flag) {
                  float floatValue25 = floatValue21 + msdfFontAtlasState2.floatValue2 * h;
                  float floatValue26 = floatValue22 - msdfFontAtlasState2.floatValue5 * h;
                  float floatValue27 = floatValue21 + msdfFontAtlasState2.floatValue4 * h;
                  float floatValue28 = floatValue22 - msdfFontAtlasState2.floatValue3 * h;
                  float floatValue29 = floatValue27 - floatValue25;
                  float floatValue30 = floatValue28 - floatValue26;
                  if (floatValue29 > 0.0F && floatValue30 > 0.0F) {
                     this.renderEngine
                        .invoke45(
                           m, n, floatValue25, floatValue26, floatValue29, floatValue30, msdfFontAtlasState2.floatValue6, msdfFontAtlasState2.floatValue9, msdfFontAtlasState2.floatValue8, msdfFontAtlasState2.floatValue7, intValue18, fs
                        );
                  }
               }

               floatValue21 += msdfFontAtlasState2.floatValue * h;
               intValue12 = intValue16;
            }
         }
      }
   }

   public FontRenderer.FontRendererState resolve(String string, float f) {
      if (f <= 0.0F) {
         return new FontRenderer.FontRendererState(0.0F, 0.0F);
      } else {
         String text7 = string == null ? "" : string;
         if (text7.isEmpty()) {
            return new FontRenderer.FontRendererState(0.0F, 0.0F);
         } else {
            float floatValue31 = f / Math.max(1.0E-6F, this.msdfFontAtlas.getFloatValue2());
            float floatValue32 = this.msdfFontAtlas.getFloatValue3() * floatValue31;
            String[] texts3 = text7.split("\\n", -1);
            float floatValue33 = 0.0F;

            for (String text8 : texts3) {
               floatValue33 = Math.max(floatValue33, this.measure(text8, floatValue31));
            }

            float floatValue34 = Math.max(floatValue32 * texts3.length, floatValue32);
            return new FontRenderer.FontRendererState(floatValue33, floatValue34);
         }
      }
   }

   private float measure(String string, float f) {
      if (string.isEmpty()) {
         return 0.0F;
      } else {
         float floatValue35 = 0.0F;
         int intValue19 = -1;
         int intValue20 = 0;

         while (intValue20 < string.length()) {
            char character3 = string.charAt(intValue20);
            if (character3 == '\\' && intValue20 + 9 < string.length() && string.charAt(intValue20 + 1) == 'c') {
               intValue20 += 10;
            } else {
               int intValue21 = string.codePointAt(intValue20);
               int intValue22 = Character.charCount(intValue21);
               intValue20 += intValue22;
               MsdfFontAtlas.MsdfFontAtlasState msdfFontAtlasState3 = this.msdfFontAtlas.resolve3(intValue21);
               int intValue23 = intValue21;
               if (msdfFontAtlasState3 == null) {
                  int intValue24 = compute(intValue21);
                  if (intValue24 != intValue21) {
                     msdfFontAtlasState3 = this.msdfFontAtlas.resolve3(intValue24);
                     intValue23 = intValue24;
                  }
               }

               if (msdfFontAtlasState3 == null) {
                  msdfFontAtlasState3 = this.msdfFontAtlas.resolve3(63);
                  intValue23 = 63;
                  if (msdfFontAtlasState3 == null) {
                     continue;
                  }
               }

               if (intValue19 != -1) {
                  floatValue35 += this.msdfFontAtlas.measure(intValue19, intValue23) * f;
               }

               floatValue35 += msdfFontAtlasState3.floatValue * f;
               intValue19 = intValue23;
            }
         }

         return floatValue35;
      }
   }

   private static int compute(int i) {
      return i == 10028 ? 9733 : i;
   }

   private static int compute2(int i) {
      if (!batchingEnabled) {
         return i;
      } else {
         int intValue25 = i >>> 24 & 0xFF;
         if (intValue25 == 0) {
            return i;
         } else {
            int intValue26 = i >>> 16 & 0xFF;
            int intValue27 = i >>> 8 & 0xFF;
            int intValue28 = i & 0xFF;
            return intValue26 >= 210 && intValue27 >= 210 && intValue28 >= 210 ? intValue25 << 24 | (intValue25 < 180 ? 6710886 : 1710618) : i;
         }
      }
   }

   private static int compute3(int i, int j, float f) {
      float floatValue36 = Math.max(0.0F, Math.min(1.0F, f));
      int intValue29 = compute4(i >>> 24 & 0xFF, j >>> 24 & 0xFF, floatValue36);
      int intValue30 = compute4(i >>> 16 & 0xFF, j >>> 16 & 0xFF, floatValue36);
      int intValue31 = compute4(i >>> 8 & 0xFF, j >>> 8 & 0xFF, floatValue36);
      int intValue32 = compute4(i & 0xFF, j & 0xFF, floatValue36);
      return intValue29 << 24 | intValue30 << 16 | intValue31 << 8 | intValue32;
   }

   private static int compute4(int i, int j, float f) {
      return Math.round(i + (j - i) * f);
   }

   public static final class FontRendererState {
      public final float floatValue;
      public final float floatValue2;

      public FontRendererState(float f, float g) {
         this.floatValue = f;
         this.floatValue2 = g;
      }
   }
}
