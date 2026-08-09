package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class MultiSelectSetting extends Setting {
   public List<String> options;
   public boolean expanded;
   public String description;
   public List<String> selectedValues = new ArrayList<>();
   private Supplier<List<String>> optionsSupplier;
   protected List<String> defaultSelectedValues = new ArrayList<>();

   public MultiSelectSetting(String string, String... strings) {
      this.name = string;
      this.options = Arrays.asList(strings);
      this.description = this.description;
      this.invoke();
   }

   public MultiSelectSetting(String string, Supplier<List<String>> supplier) {
      this.name = string;
      this.optionsSupplier = supplier;
      this.options = new ArrayList<>();
      this.refreshOptions();
      this.invoke();
   }

   public MultiSelectSetting setVisibilityCondition(Supplier<Boolean> supplier) {
      this.visibilityCondition = supplier;
      return this;
   }

   public MultiSelectSetting resolve(Supplier<List<String>> supplier) {
      this.optionsSupplier = supplier;
      this.refreshOptions();
      return this;
   }

   public List<String> refreshOptions() {
      if (this.optionsSupplier == null) {
         return this.options == null ? Collections.emptyList() : this.options;
      } else {
         List items;
         try {
            items = this.optionsSupplier.get();
         } catch (Throwable exception) {
            items = Collections.emptyList();
         }

         ArrayList arrayList = new ArrayList();
         if (items != null) {
            for (String text : (List<String>)items) {
               if (text != null && !text.isBlank() && !arrayList.contains(text)) {
                  arrayList.add(text);
               }
            }
         }

         this.options = arrayList;
         if (this.selectedValues != null) {
            this.selectedValues.removeIf(string -> string == null || !arrayList.contains(string));
         } else {
            this.selectedValues = new ArrayList<>();
         }

         return this.options;
      }
   }

   protected void invoke() {
      this.defaultSelectedValues = this.selectedValues == null ? new ArrayList<>() : new ArrayList<>(this.selectedValues);
   }

   @Override
   public void resetToDefault() {
      this.refreshOptions();
      this.expanded = false;
      this.selectedValues = new ArrayList<>();
      if (this.defaultSelectedValues != null) {
         for (String text2 : this.defaultSelectedValues) {
            if (text2 != null && this.options != null && this.options.contains(text2)) {
               this.selectedValues.add(text2);
            }
         }
      }
   }

   public String resolve2() {
      this.refreshOptions();
      StringBuilder stringBuilder = new StringBuilder();

      for (int intValue = 0; intValue < this.options.size(); intValue++) {
         stringBuilder.append(this.options.get(intValue));
         if (intValue == 2 && this.options.size() > 3) {
            stringBuilder.append("...");
            break;
         }

         if (intValue < this.options.size() - 1) {
            stringBuilder.append(", ");
         }
      }

      return stringBuilder.toString();
   }

   public boolean check3(String string) {
      this.refreshOptions();
      return this.selectedValues.contains(string);
   }
}
