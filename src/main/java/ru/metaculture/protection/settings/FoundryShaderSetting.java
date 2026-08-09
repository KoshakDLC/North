package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;

public class FoundryShaderSetting extends MultiSelectSetting {
   public static final String NONE = "None";
   private static final List<FoundryShaderSetting> ITEMS = new CopyOnWriteArrayList<>();
   public final ShaderSurface shaderSurface;
   private final Supplier<List<String>> supplier;

   public FoundryShaderSetting(String string, ShaderSurface shaderSurface) {
      this(string, shaderSurface, () -> ShaderPresetRegistry.getINSTANCE().resolve17(shaderSurface));
   }

   public FoundryShaderSetting(String string, ShaderSurface shaderSurface2, Supplier<List<String>> supplier) {
      super(string, "None");
      this.shaderSurface = shaderSurface2;
      this.supplier = supplier;
      this.selectedValues = new ArrayList<>();
      this.selectedValues.add("None");
      this.refreshOptions();
      this.invoke();
      ITEMS.add(this);
   }

   public static void invoke(ShaderSurface shaderSurface3, String string) {
      if (shaderSurface3 != null && string != null && !string.isBlank()) {
         for (FoundryShaderSetting foundryShaderSetting : ITEMS) {
            if (foundryShaderSetting.shaderSurface == shaderSurface3) {
               foundryShaderSetting.setSelectedPreset(string);
            }
         }
      }
   }

   public static void invoke2(ShaderSurface shaderSurface4) {
      if (shaderSurface4 != null) {
         for (FoundryShaderSetting foundryShaderSetting2 : ITEMS) {
            if (foundryShaderSetting2.shaderSurface == shaderSurface4) {
               foundryShaderSetting2.setSelectedPreset("None");
            }
         }
      }
   }

   public FoundryShaderSetting setVisibilityCondition(Supplier<Boolean> supplier) {
      this.visibilityCondition = supplier;
      return this;
   }

   @Override
   public List<String> refreshOptions() {
      List items;
      try {
         items = this.supplier == null ? Collections.emptyList() : this.supplier.get();
      } catch (Throwable exception) {
         items = Collections.emptyList();
      }

      ArrayList arrayList = new ArrayList();
      arrayList.add("None");
      if (items != null) {
         for (String text : (List<String>)items) {
            if (text != null && !text.isBlank() && !check5(arrayList, text)) {
               arrayList.add(text.trim());
            }
         }
      }

      if (arrayList.size() > 2) {
         arrayList.subList(1, arrayList.size()).sort((string, string2) -> {
            int var2x = compute2((String)string);
            int intValue = compute2((String)string2);
            return var2x != intValue ? Integer.compare(var2x, intValue) : ((String)string).compareToIgnoreCase((String)string2);
         });
      }

      this.options = arrayList;
      if (this.selectedValues == null) {
         this.selectedValues = new ArrayList<>();
      }

      if (this.selectedValues.isEmpty()) {
         this.selectedValues.add("None");
      } else {
         String text2 = this.selectedValues.get(this.selectedValues.size() - 1);
         this.selectedValues.clear();
         this.selectedValues.add(resolve5(text2));
      }

      return this.options;
   }

   public String getSelectedPreset() {
      this.refreshOptions();
      return this.selectedValues.isEmpty() ? "None" : this.selectedValues.get(this.selectedValues.size() - 1);
   }

   public String resolve() {
      String text3 = this.getSelectedPreset();
      return check4(text3) ? "None" : text3;
   }

   public void setSelectedPreset(String string) {
      if (this.selectedValues == null) {
         this.selectedValues = new ArrayList<>();
      }

      this.selectedValues.clear();
      this.selectedValues.add(resolve5(string));
      this.refreshOptions();
   }

   public void invoke3(int i) {
      this.refreshOptions();
      if (i >= 0 && i < this.options.size()) {
         this.setSelectedPreset(this.options.get(i));
      }
   }

   public int compute() {
      String text4 = this.getSelectedPreset();

      for (int intValue2 = 0; intValue2 < this.options.size(); intValue2++) {
         if (this.options.get(intValue2).equalsIgnoreCase(text4)) {
            return intValue2;
         }
      }

      return -1;
   }

   public boolean check() {
      String text5 = this.getSelectedPreset();
      return !check4(text5) && !check5(this.options, text5);
   }

   public String resolve2() {
      return this.resolve3();
   }

   public boolean check2() {
      return check4(this.getSelectedPreset());
   }

   public String resolve3() {
      String text6 = this.getSelectedPreset();
      if (check4(text6)) {
         return "";
      } else {
         return ShaderPresetRegistry.getINSTANCE().check2(text6) ? text6 : "";
      }
   }

   public String resolve4() {
      if (this.shaderSurface != null && this.shaderSurface != ShaderSurface.PREVIEW_ONLY) {
         try {
            SavedShaderPreset savedShaderPreset = ShaderPresetStore.getINSTANCE().resolve13(this.shaderSurface);
            if (savedShaderPreset != null && ShaderPresetRegistry.getINSTANCE().check2(savedShaderPreset.getText2())) {
               return savedShaderPreset.getText2();
            }
         } catch (Throwable exception2) {
         }

         return "";
      } else {
         return "";
      }
   }

   @Override
   public boolean check3(String string) {
      return string != null && string.equalsIgnoreCase(this.getSelectedPreset());
   }

   private static String resolve5(String string) {
      return string != null && !string.isBlank() && !check4(string) ? string.trim() : "None";
   }

   private static boolean check4(String string) {
      return string == null || string.isBlank() || "None".equalsIgnoreCase(string.trim());
   }

   private static int compute2(String string) {
      ShaderPresetRegistry.ShaderPresetRegistryState2 shaderPresetRegistryState2 = ShaderPresetRegistry.getINSTANCE().resolve7(string);

      return switch (shaderPresetRegistryState2) {
         case PRESET -> 0;
         case USER -> 1;
         case IMPORTED -> 2;
         case RUNTIME -> 3;
      };
   }

   private static boolean check5(List<String> list, String string) {
      if (list != null && string != null) {
         for (String text7 : list) {
            if (text7 != null && text7.equalsIgnoreCase(string.trim())) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }
}
