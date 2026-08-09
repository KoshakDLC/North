package ru.metaculture.protection;

import java.util.Locale;

public final class ShaderUniformEditor {
   private static final long TIMESTAMP = 600L;
   private static final float FLOAT_VALUE = 2.0F;
   private static final int INT_VALUE = 12;
   private static final int INT_VALUE_2 = 48;
   private final ShaderUniformEditor.ShaderUniformEditorState shaderUniformEditorState;
   private float floatValue;
   private float floatValue2;
   private float floatValue3;
   private float floatValue4;
   private float floatValue5;
   private String text = "";
   private boolean flag;
   private boolean flag2;
   private boolean flag3;
   private long timestamp;
   private float floatValue6;
   private float floatValue7;
   private float floatValue8;
   private String text2 = "";
   private long timestamp2;

   public ShaderUniformEditor(ShaderUniformEditor.ShaderUniformEditorState shaderUniformEditorState, float f, float g, float h, float i) {
      this.shaderUniformEditorState = shaderUniformEditorState;
      this.floatValue = f;
      this.floatValue2 = g;
      this.floatValue3 = h;
      this.floatValue4 = i;
   }

   public static ShaderUniformEditor resolve(float f, float g) {
      return new ShaderUniformEditor(ShaderUniformEditor.ShaderUniformEditorState.NUMERIC, f, g, 0.012F, 0.001F);
   }

   public static ShaderUniformEditor resolve2() {
      return new ShaderUniformEditor(ShaderUniformEditor.ShaderUniformEditorState.TEXT, 0.0F, 0.0F, 0.0F, 0.0F);
   }

   public ShaderUniformEditor.ShaderUniformEditorState getShaderUniformEditorState() {
      return this.shaderUniformEditorState;
   }

   public void invoke(float f, float g) {
      this.floatValue = f;
      this.floatValue2 = g;
      this.floatValue5 = this.measure(this.floatValue5);
   }

   public void invoke2(float f, float g) {
      this.floatValue3 = f;
      this.floatValue4 = g;
   }

   public void setFloatValue5(float f) {
      this.floatValue5 = this.measure(f);
   }

   public void setText(String string) {
      this.text = string == null ? "" : string;
   }

   public float getFloatValue5() {
      return this.floatValue5;
   }

   public String getText() {
      return this.text;
   }

   public boolean isFlag() {
      return this.flag;
   }

   public boolean isFlag3() {
      return this.flag3;
   }

   public boolean isFlag2() {
      return this.flag2;
   }

   public boolean check() {
      return this.flag || this.flag3 || this.flag2;
   }

   public boolean check2(float f, float g, int i, Rect rect) {
      if (i != 0) {
         return false;
      } else if (rect == null || !rect.contains(f, g)) {
         return false;
      } else if (this.flag) {
         return true;
      } else {
         this.flag2 = true;
         this.flag3 = false;
         this.timestamp = System.currentTimeMillis();
         this.floatValue6 = f;
         this.floatValue7 = f;
         this.floatValue8 = this.floatValue5;
         return true;
      }
   }

   public boolean check3(float f, float g, boolean bl) {
      if (this.flag || this.shaderUniformEditorState != ShaderUniformEditor.ShaderUniformEditorState.NUMERIC) {
         return false;
      } else if (!this.flag2 && !this.flag3) {
         return false;
      } else {
         if (!this.flag3 && Math.abs(f - this.floatValue6) > 2.0F) {
            this.flag3 = true;
         }

         if (this.flag3) {
            float floatValue = bl ? this.floatValue4 : this.floatValue3;
            float floatValue2 = (f - this.floatValue7) * floatValue;
            this.floatValue5 = this.measure(this.floatValue8 + floatValue2);
            return true;
         } else {
            return false;
         }
      }
   }

   public boolean check4(float f, float g) {
      if (this.flag) {
         this.flag2 = false;
         this.flag3 = false;
         return false;
      } else {
         boolean flag = this.flag3;
         if (this.flag2 && !this.flag3 && System.currentTimeMillis() - this.timestamp < 600L) {
            this.flag = true;
            this.text2 = this.shaderUniformEditorState == ShaderUniformEditor.ShaderUniformEditorState.NUMERIC ? resolve4(resolve3(this.floatValue5)) : this.text;
            this.timestamp2 = System.currentTimeMillis();
         }

         this.flag2 = false;
         this.flag3 = false;
         return flag;
      }
   }

   public boolean check5(char c) {
      if (!this.flag) {
         return false;
      } else if (this.shaderUniformEditorState == ShaderUniformEditor.ShaderUniformEditorState.NUMERIC) {
         if (c >= '0' && c <= '9' || c == '.' || c == ',' || c == '-') {
            if (c == '-' && !this.text2.isEmpty()) {
               return true;
            }

            if ((c == '.' || c == ',') && this.text2.contains(".")) {
               return true;
            }

            if (this.text2.length() < 12) {
               this.text2 = this.text2 + (c == ',' ? '.' : c);
               this.timestamp2 = System.currentTimeMillis();
            }
         }

         return true;
      } else {
         if (this.text2.length() < 48 && (Character.isLetterOrDigit(c) || c == ' ' || c == '_' || c == '-' || c == '.')) {
            this.text2 = this.text2 + c;
            this.timestamp2 = System.currentTimeMillis();
         }

         return true;
      }
   }

   public boolean check6(int i) {
      if (!this.flag) {
         return false;
      } else if (i == 256) {
         this.flag = false;
         this.text2 = "";
         return true;
      } else if (i == 257 || i == 335 || i == 258) {
         this.invoke3();
         return true;
      } else if (i == 259) {
         if (!this.text2.isEmpty()) {
            this.text2 = this.text2.substring(0, this.text2.length() - 1);
            this.timestamp2 = System.currentTimeMillis();
         }

         return true;
      } else {
         return true;
      }
   }

   public void invoke3() {
      if (this.flag) {
         if (this.shaderUniformEditorState == ShaderUniformEditor.ShaderUniformEditorState.NUMERIC) {
            try {
               float floatValue3 = Float.parseFloat(this.text2.replace(',', '.'));
               if (Float.isFinite(floatValue3)) {
                  this.floatValue5 = this.measure(floatValue3);
               }
            } catch (NumberFormatException numberFormatException) {
            }
         } else {
            this.text = this.text2;
         }

         this.flag = false;
         this.text2 = "";
      }
   }

   public void invoke4() {
      this.flag = false;
      this.text2 = "";
      this.flag2 = false;
      this.flag3 = false;
   }

   public void invoke5(RenderManager renderManager, Metrics metrics, ColorScheme colorScheme, Rect rect2, float f, float g) {
      boolean flag2 = rect2.contains(f, g);
      boolean flag3 = flag2 || this.flag3 || this.flag2;
      int intValue = this.flag
         ? ColorScheme.compute6(colorScheme.getIntValue14(), 132)
         : ColorScheme.compute7(colorScheme.getIntValue4(), ColorScheme.compute6(colorScheme.getIntValue15(), 56), flag3 ? 1.0F : 0.0F);
      renderManager.invoke5(rect2.x(), rect2.y(), rect2.w(), rect2.h(), metrics.measure(6.0F), intValue);
      if (!this.flag && this.shaderUniformEditorState == ShaderUniformEditor.ShaderUniformEditorState.NUMERIC) {
         float floatValue4 = Math.max(1.0E-4F, this.floatValue2 - this.floatValue);
         float floatValue5 = Math.max(0.0F, Math.min(1.0F, (this.floatValue5 - this.floatValue) / floatValue4));
         renderManager.invoke5(
            rect2.x(),
            rect2.y(),
            rect2.w() * floatValue5,
            rect2.h(),
            metrics.measure(6.0F),
            ColorScheme.compute6(colorScheme.getIntValue14(), flag3 ? 80 : 48)
         );
      }

      renderManager.invoke28(
         rect2.x(),
         rect2.y(),
         rect2.w(),
         rect2.h(),
         metrics.measure(6.0F),
         this.flag
            ? ColorScheme.compute6(colorScheme.getIntValue14(), 230)
            : ColorScheme.compute7(colorScheme.getIntValue6(), ColorScheme.compute6(colorScheme.getIntValue14(), 122), flag3 ? 1.0F : 0.0F),
         this.flag ? 1.0F : 0.6F
      );
      String text = this.flag
         ? this.text2
         : (this.shaderUniformEditorState == ShaderUniformEditor.ShaderUniformEditorState.NUMERIC ? resolve4(resolve3(this.floatValue5)) : this.text);
      float floatValue6 = ClickGuiRenderUtils.measure2(metrics, FontRegistry.fontObject, text, 9.0F);
      int intValue2 = colorScheme.isFlag() ? ColorScheme.compute5(10, 10, 10, 255) : colorScheme.getIntValue13();
      ClickGuiRenderUtils.invoke4(
         renderManager,
         metrics,
         FontRegistry.fontObject,
         rect2.x() + (rect2.w() - floatValue6) * 0.5F,
         rect2.y(),
         rect2.h(),
         9.0F,
         text,
         intValue2
      );
      if (this.flag && (System.currentTimeMillis() - this.timestamp2) / 500L % 2L == 0L) {
         float floatValue7 = rect2.x() + (rect2.w() - floatValue6) * 0.5F + floatValue6 + metrics.measure(1.5F);
         renderManager.invoke5(
            floatValue7,
            rect2.y() + metrics.measure(3.0F),
            1.0F,
            rect2.h() - metrics.measure(6.0F),
            0.0F,
            ColorScheme.compute6(colorScheme.getIntValue14(), 240)
         );
      }
   }

   private float measure(float f) {
      return !Float.isFinite(f) ? this.floatValue5 : Math.max(this.floatValue, Math.min(this.floatValue2, f));
   }

   private static String resolve3(float f) {
      return String.format(Locale.ROOT, "%.3f", f);
   }

   private static String resolve4(String string) {
      if (string != null && string.contains(".")) {
         int intValue3 = string.length();

         while (intValue3 > 0 && string.charAt(intValue3 - 1) == '0') {
            intValue3--;
         }

         if (intValue3 > 0 && string.charAt(intValue3 - 1) == '.') {
            intValue3--;
         }

         return string.substring(0, intValue3);
      } else {
         return string;
      }
   }

   public static enum ShaderUniformEditorState {
      NUMERIC,
      TEXT;
   }
}
