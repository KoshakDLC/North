package ru.metaculture.protection;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public final class MultiValueAccessor extends SettingValueAccessor<Set<String>> {
   public MultiValueAccessor(SettingBinding settingBinding, MultiSelectSetting multiSelectSetting) {
      super(Objects.requireNonNull(settingBinding, "model"), resolve(settingBinding, Objects.requireNonNull(multiSelectSetting, "setting")));
   }

   private static MultiSelectPopup resolve(SettingBinding settingBinding2, MultiSelectSetting multiSelectSetting2) {
      final LinkedHashSet linkedHashSet = new LinkedHashSet<>(multiSelectSetting2.selectedValues != null ? multiSelectSetting2.selectedValues : Collections.emptyList());
      SettingValue settingValue = resolve2(settingBinding2, linkedHashSet, new SettingValueAccessor.SettingValueAccessorContract<Set<String>>() {
         public Set<String> resolve2(SettingBinding settingBinding3) {
            Object var2x = settingBinding3.resolve4();
            return this.resolve2(var2x, linkedHashSet);
         }

         public void resolve2(SettingBinding settingBinding4, Set<String> set) {
            settingBinding4.invoke8(this.resolve2(set, linkedHashSet));
         }

         private LinkedHashSet<String> resolve2(Object object, Set<String> set) {
            LinkedHashSet var3x = new LinkedHashSet();
            Object object2 = null;
            boolean flag = false;
            if (object instanceof Collection items) {
               object2 = items;
               flag = true;
            } else if (object instanceof Set values) {
               object2 = values;
               flag = true;
            }

            if (object2 != null) {
               for (Object object3 : (Collection)object2) {
                  if (object3 != null) {
                     var3x.add(object3.toString());
                  }
               }
            } else if (object instanceof Object[] objects) {
               flag = true;

               for (Object object4 : objects) {
                  if (object4 != null) {
                     var3x.add(object4.toString());
                  }
               }
            }

            if (!flag && set != null) {
               var3x.addAll(set);
            }

            return var3x;
         }
      });
      return new MultiSelectPopup(resolve(settingBinding2), getPOPUP_VALUE_EDITOR2(), multiSelectSetting2, settingValue, "New Value");
   }
}
