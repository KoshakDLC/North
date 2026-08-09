package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.List;
import ru.metaculture.sdk.Compile;
import ru.metaculture.sdk.Loader;

public abstract class ConfigStore<T> {
   private List<T> items = new ArrayList<>();

   public List<T> getItems() {
      return this.items;
   }

   @Compile
   public void invoke(ArrayList<T> arrayList) {
      this.items.clear();
      if (arrayList != null) {
         this.items.addAll(arrayList);
      }
   }

   static {
      Loader.initialize();
   }
}
