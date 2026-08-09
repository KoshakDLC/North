package ru.metaculture.protection;

import java.awt.Color;
import java.io.File;
import net.minecraft.client.MinecraftClient;
import ru.metaculture.sdk.Compile;
import ru.metaculture.sdk.Loader;

public class ThemeManager {
   public static MinecraftClient client = MinecraftClient.getInstance();
   private File file;
   private Theme theme = Theme.WILD;
   private Category category = Category.Visuals;
   private boolean flag;
   private float floatValue;
   private float floatValue2;
   private boolean flag2;
   private boolean flag3;
   public ColorSetting customThemeColor = new ColorSetting("Custom Theme Color", Color.WHITE.getRGB());

   @Compile
   public void invoke() {}

   @Compile
   public void invoke2(Theme theme) {
      if (theme != null) {
         this.theme = theme;
      }
   }

   @Compile
   public void invoke3(Category category) {}

   public Theme getTheme() {
      return this.theme;
   }

   public Category getCategory() {
      return this.category;
   }

   public boolean isFlag() {
      return this.flag;
   }

   public float getFloatValue() {
      return this.floatValue;
   }

   public float getFloatValue2() {
      return this.floatValue2;
   }

   public void invoke4(float f, float g) {
      if (Float.isFinite(f) && Float.isFinite(g)) {
         if (!this.flag || !(Math.abs(this.floatValue - f) < 0.5F) || !(Math.abs(this.floatValue2 - g) < 0.5F)) {
            this.flag = true;
            this.floatValue = f;
            this.floatValue2 = g;
            this.invoke6();
         }
      }
   }

   public boolean isFlag2() {
      return this.flag2;
   }

   public boolean isFlag3() {
      return this.flag3;
   }

   public void invoke5(boolean bl) {
      if (!this.flag2 || this.flag3 != bl) {
         this.flag2 = true;
         this.flag3 = bl;
         this.invoke6();
      }
   }

   public ClickGui resolve() {
      return client != null && client.currentScreen instanceof ModernClickGuiScreen modernClickGuiScreen ? modernClickGuiScreen.getClickGui() : null;
   }

   @Compile
   private void invoke6() {}

   @Compile
   private void invoke7() {}

   private String resolve2(ColorSetting colorSetting) {
      StringBuilder stringBuilder = new StringBuilder();

      for (int intValue = 0; intValue < colorSetting.items.size(); intValue++) {
         if (intValue > 0) {
            stringBuilder.append(',');
         }

         stringBuilder.append(colorSetting.items.get(intValue));
      }

      return stringBuilder.toString();
   }

   private void invoke8(ColorSetting colorSetting2, String string) {
      colorSetting2.items.clear();
      if (string != null && !string.isBlank()) {
         String[] texts = string.split(",");

         for (String text : texts) {
            if (colorSetting2.items.size() >= 8) {
               break;
            }

            try {
               colorSetting2.items.add(Integer.parseInt(text.trim()));
            } catch (NumberFormatException numberFormatException) {
            }
         }
      }
   }

   static {
      Loader.initialize();
   }
}
