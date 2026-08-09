package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class ConfigurableHudElement {
   private static final String SBROS_NASTROEK = "Сброс настроек";
   private static final String DO_ZAVODSKIH = "До заводских";
   private final List<Setting> items = new ArrayList<>();
   private final DynamicButtonSetting sbrosNastroek = new DynamicButtonSetting("Сброс настроек", 0, () -> "До заводских").onClick((Runnable)this::invoke4);

   public void invoke(Setting setting) {
      this.items.add(setting);
   }

   public void invoke2(Setting... settings) {
      if (settings != null) {
         for (Setting setting2 : settings) {
            this.invoke(setting2);
         }
      }
   }

   public void invoke3(Collection<Setting> collection) {
      if (collection != null && !collection.isEmpty()) {
         this.items.removeAll(collection);
      }
   }

   public List<Setting> resolve() {
      if (!this.check()) {
         return this.items;
      } else {
         ArrayList arrayList = new ArrayList<>(this.items);
         arrayList.add(this.sbrosNastroek);
         return arrayList;
      }
   }

   private void invoke4() {
      for (Setting setting3 : this.items) {
         if (this.check2(setting3)) {
            setting3.resetToDefault();
         }
      }

      HudPresetManager.invoke5();
   }

   private boolean check() {
      for (Setting setting4 : this.items) {
         if (this.check2(setting4)) {
            return true;
         }
      }

      return false;
   }

   private boolean check2(Setting setting5) {
      return setting5 != null && !setting5.configTransient && !(setting5 instanceof ButtonSetting) && !(setting5 instanceof SpacerSetting);
   }
}
