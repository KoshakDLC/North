package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class SettingContainer {
   private final ArrayList<Setting> settings = new ArrayList<>();

   public final void addSettings(Setting... settings) {
      this.settings.addAll(Arrays.asList(settings));
   }

   public final void removeSettings(Collection<Setting> collection) {
      if (collection != null && !collection.isEmpty()) {
         this.settings.removeAll(collection);
      }
   }

   public List<Setting> getVisibleSettings() {
      return this.settings.stream().filter(setting -> {
         try {
            return !setting.visibilityCondition.get();
         } catch (Throwable exception) {
            return false;
         }
      }).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
   }

   public final List<Setting> getAllSettings() {
      return this.settings;
   }
}
