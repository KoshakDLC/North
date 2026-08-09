package ru.metaculture.protection;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

public final class RotationLabScreen extends Screen {
   private static final int INT_VALUE = -234156525;
   private static final int INT_VALUE_2 = -1441326300;
   private static final int INT_VALUE_3 = -1446152;
   private static final int INT_VALUE_4 = -7366230;
   private static final int INT_VALUE_5 = -45462;
   private static final int INT_VALUE_6 = -1;
   private static final int INT_VALUE_7 = -2142256137;
   private final RotationLab rotationLab;
   private final List<RotationLabModule.RotationLabModuleState> items = new ArrayList<>();
   private final List<RotationLabModule.RotationLabModuleState2> items2 = new ArrayList<>();
   private RotationLabScreen.RotationLabScreenState rotationLabScreenState;
   private long timestamp;
   private int intValue = -1;
   private double doubleValue;
   private double doubleValue2;
   private float floatValue;
   private float floatValue2;
   private boolean flag;

   public RotationLabScreen(RotationLab rotationLab) {
      super(Text.literal("RotationLab"));
      this.rotationLab = rotationLab;
   }

   public boolean shouldPause() {
      return false;
   }

   public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
      context.fill(0, 0, this.width, this.height, -234156525);
      if (this.rotationLabScreenState == null) {
         this.invoke3(mouseX, mouseY);
      }

      this.invoke4(deltaTicks);
      this.invoke5(mouseX, mouseY);
      if (this.rotationLab.check() && this.rotationLabScreenState != null && this.check(mouseX, mouseY)) {
         this.invoke6(mouseX, mouseY);
         this.invoke3(mouseX, mouseY);
      }

      this.invoke9(context);
      this.invoke10(context);
      this.invoke8(context);
      super.render(context, mouseX, mouseY, deltaTicks);
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (button == 0 && this.rotationLabScreenState != null && this.check(mouseX, mouseY)) {
         this.invoke6(mouseX, mouseY);
         this.invoke3(mouseX, mouseY);
         return true;
      } else {
         return super.mouseClicked(mouseX, mouseY, button);
      }
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (keyCode == 82) {
         this.invoke2();
         return true;
      } else if (keyCode != 68 && keyCode != 261) {
         return super.keyPressed(keyCode, scanCode, modifiers);
      } else {
         this.rotationLab.invoke3();
         return true;
      }
   }

   public void close() {
      if (!this.flag) {
         this.invoke();
         this.rotationLab.invoke2(this);
      }

      super.close();
   }

   public void invoke() {
      if (!this.flag) {
         this.flag = true;
         this.invoke7();
      }
   }

   public void invoke2() {
      this.items.clear();
      this.items2.clear();
      this.rotationLabScreenState = null;
      this.intValue = -1;
   }

   private void invoke3(double d, double e) {
      if (this.items.size() >= this.rotationLab.compute2()) {
         this.close();
      } else {
         String text = this.resolve();
         double doubleValue = Math.max(80.0, (double)(this.width * this.rotationLab.measure()));
         double doubleValue2 = Math.max(60.0, (double)(this.height * this.rotationLab.measure()));
         double doubleValue3 = (this.width - doubleValue) * 0.5;
         double doubleValue4 = (this.height - doubleValue2) * 0.5;
         double doubleValue5 = this.width * 0.5;
         double doubleValue6 = this.height * 0.5;

         double doubleValue7 = switch (text) {
            case "Micro", "Idle" -> 22.0;
            case "Vertical" -> 70.0;
            case "Attack" -> 120.0;
            default -> 95.0;
         };
         int intValue = 0;

         double doubleValue8;
         double doubleValue9;
         do {
            if ("Vertical".equals(text)) {
               doubleValue8 = ThreadLocalRandom.current().nextDouble(-24.0, 24.0);
               doubleValue9 = this.measure3(doubleValue7, doubleValue2 * 0.42);
            } else if ("Diagonal".equals(text)) {
               doubleValue8 = this.measure3(doubleValue7 * 0.65, doubleValue * 0.45);
               doubleValue9 = this.measure3(doubleValue7 * 0.45, doubleValue2 * 0.4);
            } else if (!"Micro".equals(text) && !"Idle".equals(text)) {
               doubleValue8 = this.measure3(doubleValue7, doubleValue * 0.48);
               doubleValue9 = this.measure3(12.0, doubleValue2 * 0.36);
            } else {
               doubleValue8 = this.measure3(12.0, 58.0);
               doubleValue9 = this.measure3(8.0, 42.0);
            }

            this.rotationLabScreenState = new RotationLabScreen.RotationLabScreenState(
               MathHelper.clamp(d + doubleValue8, doubleValue3, doubleValue3 + doubleValue), MathHelper.clamp(e + doubleValue9, doubleValue4, doubleValue4 + doubleValue2), this.rotationLab.compute(), text
            );
         } while (this.measure6(d, e, this.rotationLabScreenState.doubleValue, this.rotationLabScreenState.doubleValue2) < doubleValue7 && ++intValue < 12);

         if (intValue >= 12) {
            this.rotationLabScreenState.doubleValue = MathHelper.clamp(doubleValue5 + doubleValue8, doubleValue3, doubleValue3 + doubleValue);
            this.rotationLabScreenState.doubleValue2 = MathHelper.clamp(doubleValue6 + doubleValue9, doubleValue4, doubleValue4 + doubleValue2);
         }

         if ("Tracking".equals(text)) {
            this.rotationLabScreenState.doubleValue3 = ThreadLocalRandom.current().nextDouble(-1.15, 1.15);
            this.rotationLabScreenState.doubleValue4 = ThreadLocalRandom.current().nextDouble(-0.85, 0.85);
         }

         this.doubleValue = d;
         this.doubleValue2 = e;
         this.timestamp = System.currentTimeMillis();
         this.intValue = -1;
         this.floatValue = 0.0F;
         this.floatValue2 = 0.0F;
         this.items2.clear();
      }
   }

   private void invoke4(float f) {
      if (this.rotationLabScreenState != null && "Tracking".equals(this.rotationLabScreenState.text)) {
         double doubleValue10 = Math.max(0.35, (double)f);
         this.rotationLabScreenState.doubleValue = this.rotationLabScreenState.doubleValue + this.rotationLabScreenState.doubleValue3 * doubleValue10;
         this.rotationLabScreenState.doubleValue2 = this.rotationLabScreenState.doubleValue2 + this.rotationLabScreenState.doubleValue4 * doubleValue10;
         double doubleValue11 = this.rotationLabScreenState.intValue + 18.0;
         if (this.rotationLabScreenState.doubleValue < doubleValue11 || this.rotationLabScreenState.doubleValue > this.width - doubleValue11) {
            this.rotationLabScreenState.doubleValue3 = -this.rotationLabScreenState.doubleValue3;
         }

         if (this.rotationLabScreenState.doubleValue2 < doubleValue11 || this.rotationLabScreenState.doubleValue2 > this.height - doubleValue11) {
            this.rotationLabScreenState.doubleValue4 = -this.rotationLabScreenState.doubleValue4;
         }

         this.rotationLabScreenState.doubleValue = MathHelper.clamp(this.rotationLabScreenState.doubleValue, doubleValue11, this.width - doubleValue11);
         this.rotationLabScreenState.doubleValue2 = MathHelper.clamp(this.rotationLabScreenState.doubleValue2, doubleValue11, this.height - doubleValue11);
      }
   }

   private void invoke5(double d, double e) {
      if (this.rotationLabScreenState != null) {
         int intValue2 = (int)((System.currentTimeMillis() - this.timestamp) / 50L);
         if (intValue2 != this.intValue) {
            this.intValue = intValue2;
            float floatValue = this.measure4(d - this.doubleValue);
            float floatValue2 = this.measure5(e - this.doubleValue2);
            float floatValue3 = this.measure4(this.rotationLabScreenState.doubleValue - this.doubleValue);
            float floatValue4 = this.measure5(this.rotationLabScreenState.doubleValue2 - this.doubleValue2);
            float floatValue5 = (float)Math.max(0.001, Math.hypot(floatValue3, floatValue4));
            RotationLabModule.RotationLabModuleState2 rotationLabModuleState2 = new RotationLabModule.RotationLabModuleState2();
            rotationLabModuleState2.intValue = intValue2;
            rotationLabModuleState2.floatValue = floatValue;
            rotationLabModuleState2.floatValue2 = floatValue2;
            rotationLabModuleState2.floatValue3 = floatValue - this.floatValue;
            rotationLabModuleState2.floatValue4 = floatValue2 - this.floatValue2;
            rotationLabModuleState2.floatValue5 = Math.abs(rotationLabModuleState2.floatValue3);
            rotationLabModuleState2.floatValue6 = Math.abs(rotationLabModuleState2.floatValue4);
            rotationLabModuleState2.floatValue7 = (float)MathHelper.clamp(Math.hypot(floatValue, floatValue2) / floatValue5, 0.0, 1.35);
            this.items2.add(rotationLabModuleState2);
            this.floatValue = floatValue;
            this.floatValue2 = floatValue2;
            if (intValue2 > 120) {
               this.invoke3(d, e);
            }
         }
      }
   }

   private void invoke6(double d, double e) {
      if (this.rotationLabScreenState != null && this.items2.size() >= 2) {
         RotationLabModule.RotationLabModuleState rotationLabModuleState = new RotationLabModule.RotationLabModuleState();
         rotationLabModuleState.mixed = this.rotationLabScreenState.text;
         rotationLabModuleState.timestamp = System.currentTimeMillis();
         rotationLabModuleState.floatValue = this.measure4(this.rotationLabScreenState.doubleValue - this.doubleValue);
         rotationLabModuleState.floatValue2 = this.measure5(this.rotationLabScreenState.doubleValue2 - this.doubleValue2);
         RotationLabModule.RotationLabModuleState2 rotationLabModuleState22 = this.items2.get(this.items2.size() - 1);
         rotationLabModuleState.floatValue3 = rotationLabModuleState22.floatValue;
         rotationLabModuleState.floatValue4 = rotationLabModuleState22.floatValue2;
         rotationLabModuleState.intValue = rotationLabModuleState22.intValue + 1;
         rotationLabModuleState.items = new ArrayList<>(this.items2);
         rotationLabModuleState.floatValue5 = this.measure(rotationLabModuleState);
         rotationLabModuleState.floatValue6 = this.measure2(rotationLabModuleState);
         rotationLabModuleState.intValue2 = this.compute(rotationLabModuleState);
         double doubleValue12 = this.measure6(d, e, this.rotationLabScreenState.doubleValue, this.rotationLabScreenState.doubleValue2);
         float floatValue6 = 1.0F - (float)MathHelper.clamp(doubleValue12 / Math.max(1.0, this.rotationLabScreenState.intValue * 1.8), 0.0, 1.0);
         float floatValue7 = MathHelper.clamp(this.items2.size() / 6.0F, 0.0F, 1.0F);
         rotationLabModuleState.floatValue7 = MathHelper.clamp(floatValue6 * 0.75F + floatValue7 * 0.25F, 0.0F, 1.0F);
         this.items.add(rotationLabModuleState);
      }
   }

   private float measure(RotationLabModule.RotationLabModuleState rotationLabModuleState3) {
      float floatValue8 = rotationLabModuleState3.floatValue;
      float floatValue9 = 0.0F;

      for (RotationLabModule.RotationLabModuleState2 rotationLabModuleState23 : rotationLabModuleState3.items) {
         floatValue9 = Math.max(floatValue9, Math.abs(rotationLabModuleState23.floatValue) - Math.abs(floatValue8));
      }

      return Math.max(0.0F, floatValue9);
   }

   private float measure2(RotationLabModule.RotationLabModuleState rotationLabModuleState4) {
      float floatValue10 = rotationLabModuleState4.floatValue2;
      float floatValue11 = 0.0F;

      for (RotationLabModule.RotationLabModuleState2 rotationLabModuleState24 : rotationLabModuleState4.items) {
         floatValue11 = Math.max(floatValue11, Math.abs(rotationLabModuleState24.floatValue2) - Math.abs(floatValue10));
      }

      return Math.max(0.0F, floatValue11);
   }

   private int compute(RotationLabModule.RotationLabModuleState rotationLabModuleState5) {
      int intValue3 = 0;

      for (int intValue4 = rotationLabModuleState5.items.size() - 1; intValue4 >= 0; intValue4--) {
         RotationLabModule.RotationLabModuleState2 rotationLabModuleState25 = rotationLabModuleState5.items.get(intValue4);
         float floatValue12 = Math.abs(rotationLabModuleState5.floatValue - rotationLabModuleState25.floatValue);
         float floatValue13 = Math.abs(rotationLabModuleState5.floatValue2 - rotationLabModuleState25.floatValue2);
         if (!(floatValue12 <= 1.5F) || !(floatValue13 <= 1.5F)) {
            break;
         }

         intValue3++;
      }

      return intValue3;
   }

   private void invoke7() {
      if (!this.items.isEmpty()) {
         Path path = RotationAssetLoader.resolve4(this.rotationLab.resolve());
         RotationLabModule rotationLabModule = RotationAssetLoader.resolve5(path);
         if (rotationLabModule == null) {
            rotationLabModule = new RotationLabModule();
            rotationLabModule.timestamp = System.currentTimeMillis();
            rotationLabModule.rotationLab = RotationAssetLoader.resolve6(this.rotationLab.resolve());
         }

         rotationLabModule.timestamp2 = System.currentTimeMillis();
         rotationLabModule.items.addAll(this.items);
         RotationAssetLoader.invoke2(path, rotationLabModule);
         ChatUtil.sendClientMessage("[RotationLab] Saved " + this.items.size() + " patterns to " + path.getFileName());
      }
   }

   private boolean check(double d, double e) {
      return this.measure6(d, e, this.rotationLabScreenState.doubleValue, this.rotationLabScreenState.doubleValue2) <= this.rotationLabScreenState.intValue;
   }

   private String resolve() {
      String text2 = this.rotationLab.resolve2();
      if (!"Mixed".equals(text2)) {
         return text2;
      } else {
         String[] texts = new String[]{"Flick", "Tracking", "Micro", "Vertical", "Diagonal", "Attack"};
         return texts[ThreadLocalRandom.current().nextInt(texts.length)];
      }
   }

   private double measure3(double d, double e) {
      double doubleValue13 = ThreadLocalRandom.current().nextDouble(d, Math.max(d + 1.0, e));
      return ThreadLocalRandom.current().nextBoolean() ? doubleValue13 : -doubleValue13;
   }

   private float measure4(double d) {
      return (float)(d / Math.max(1.0, (double)this.width) * 95.0);
   }

   private float measure5(double d) {
      return (float)(d / Math.max(1.0, (double)this.height) * 70.0);
   }

   private double measure6(double d, double e, double f, double g) {
      return Math.hypot(d - f, e - g);
   }

   private void invoke8(DrawContext drawContext) {
      byte byteValue = 12;
      byte byteValue2 = 12;
      short shortValue = 222;
      byte byteValue3 = 74;
      drawContext.fill(byteValue - 6, byteValue2 - 6, byteValue + shortValue, byteValue2 + byteValue3, -1441326300);
      drawContext.drawTextWithShadow(this.textRenderer, "RotationLab", byteValue, byteValue2, -1446152);
      drawContext.drawTextWithShadow(this.textRenderer, "asset: " + RotationAssetLoader.resolve6(this.rotationLab.resolve()), byteValue, byteValue2 + 14, -7366230);
      drawContext.drawTextWithShadow(this.textRenderer, "mode: " + this.rotationLab.resolve2().toLowerCase(Locale.ROOT), byteValue, byteValue2 + 28, -7366230);
      drawContext.drawTextWithShadow(
         this.textRenderer, "patterns: " + this.items.size() + " / " + this.rotationLab.compute2(), byteValue, byteValue2 + 42, -7366230
      );
      drawContext.drawTextWithShadow(this.textRenderer, "R reset  D delete  Esc save", byteValue, byteValue2 + 56, -7366230);
   }

   private void invoke9(DrawContext drawContext) {
      if (this.items2.size() >= 2) {
         for (int intValue5 = Math.max(1, this.items2.size() - 20); intValue5 < this.items2.size(); intValue5++) {
            RotationLabModule.RotationLabModuleState2 rotationLabModuleState26 = this.items2.get(intValue5 - 1);
            RotationLabModule.RotationLabModuleState2 rotationLabModuleState27 = this.items2.get(intValue5);
            int intValue6 = (int)(this.doubleValue + rotationLabModuleState26.floatValue / 95.0F * this.width);
            int intValue7 = (int)(this.doubleValue2 + rotationLabModuleState26.floatValue2 / 70.0F * this.height);
            int intValue8 = (int)(this.doubleValue + rotationLabModuleState27.floatValue / 95.0F * this.width);
            int intValue9 = (int)(this.doubleValue2 + rotationLabModuleState27.floatValue2 / 70.0F * this.height);
            this.invoke12(drawContext, intValue6, intValue7, intValue8, intValue9, -2142256137);
         }
      }
   }

   private void invoke10(DrawContext drawContext) {
      if (this.rotationLabScreenState != null) {
         this.invoke11(drawContext, (int)this.rotationLabScreenState.doubleValue, (int)this.rotationLabScreenState.doubleValue2, this.rotationLabScreenState.intValue + 4, 956255850);
         this.invoke11(drawContext, (int)this.rotationLabScreenState.doubleValue, (int)this.rotationLabScreenState.doubleValue2, this.rotationLabScreenState.intValue, -45462);
         this.invoke11(drawContext, (int)this.rotationLabScreenState.doubleValue, (int)this.rotationLabScreenState.doubleValue2, Math.max(2, this.rotationLabScreenState.intValue / 4), -1);
         drawContext.drawTextWithShadow(
            this.textRenderer,
            this.rotationLabScreenState.text,
            (int)this.rotationLabScreenState.doubleValue + this.rotationLabScreenState.intValue + 8,
            (int)this.rotationLabScreenState.doubleValue2 - 4,
            -1446152
         );
      }
   }

   private void invoke11(DrawContext drawContext, int i, int j, int k, int l) {
      int intValue10 = k * k;

      for (int intValue11 = -k; intValue11 <= k; intValue11++) {
         int intValue12 = (int)Math.sqrt(Math.max(0, intValue10 - intValue11 * intValue11));
         drawContext.fill(i - intValue12, j + intValue11, i + intValue12 + 1, j + intValue11 + 1, l);
      }
   }

   private void invoke12(DrawContext drawContext, int i, int j, int k, int l, int m) {
      int intValue13 = Math.abs(k - i);
      int intValue14 = Math.abs(l - j);
      int intValue15 = i < k ? 1 : -1;
      int intValue16 = j < l ? 1 : -1;
      int intValue17 = intValue13 - intValue14;

      while (true) {
         drawContext.fill(i - 1, j - 1, i + 2, j + 2, m);
         if (i == k && j == l) {
            return;
         }

         int intValue18 = intValue17 * 2;
         if (intValue18 > -intValue14) {
            intValue17 -= intValue14;
            i += intValue15;
         }

         if (intValue18 < intValue13) {
            intValue17 += intValue13;
            j += intValue16;
         }
      }
   }

   static final class RotationLabScreenState {
      double doubleValue;
      double doubleValue2;
      double doubleValue3;
      double doubleValue4;
      final int intValue;
      final String text;

      RotationLabScreenState(double d, double e, int i, String string) {
         this.doubleValue = d;
         this.doubleValue2 = e;
         this.intValue = i;
         this.text = string;
      }
   }
}
