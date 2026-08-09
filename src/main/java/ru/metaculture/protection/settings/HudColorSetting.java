package ru.metaculture.protection;

import java.awt.Color;
import java.util.function.Supplier;

public class HudColorSetting extends ColorSetting {
   public HudColorSetting(String string, int i) {
      super(string, 0.0F);
      this.invoke2(i);
      this.invoke8();
   }

   public HudColorSetting(String string, Color color) {
      super(string, 0.0F);
      this.invoke(color);
      this.invoke8();
   }

   public HudColorSetting resolve(Supplier<Boolean> supplier) {
      super.setVisibilityCondition(supplier);
      return this;
   }
}
