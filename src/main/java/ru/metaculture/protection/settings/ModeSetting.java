package ru.metaculture.protection;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class ModeSetting extends Setting {
   public final List<String> options;
   public String value;
   public String description;
   public DirectionalAnimation animation = new EaseInOutQuadAnimation(300, 1.0);
   public int selectedIndex;
   public boolean expanded;
   private final String defaultValue;

   public ModeSetting(String string, String string2, String... strings) {
      this.name = string;
      this.options = Arrays.asList(strings);
      this.selectedIndex = this.options.indexOf(string2);
      if (this.selectedIndex < 0) {
         this.selectedIndex = 0;
      }

      this.value = this.options.get(this.selectedIndex);
      this.defaultValue = this.value;
   }

   public String getValue() {
      return this.value;
   }

   public boolean is(String string) {
      return this.value.equalsIgnoreCase(string);
   }

   public ModeSetting setVisibilityCondition(Supplier<Boolean> supplier) {
      this.visibilityCondition = supplier;
      return this;
   }

   @Override
   public void resetToDefault() {
      int intValue = this.options.indexOf(this.defaultValue);
      if (intValue < 0) {
         intValue = 0;
      }

      this.selectedIndex = intValue;
      this.value = this.options.get(intValue);
      this.expanded = false;
   }
}
