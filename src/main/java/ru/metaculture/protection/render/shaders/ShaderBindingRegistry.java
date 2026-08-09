package ru.metaculture.protection;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import org.wild.module.api.Module;

public final class ShaderBindingRegistry {
   private static final ShaderBindingRegistry INSTANCE = new ShaderBindingRegistry();
   private final List<ShaderBindingRegistry.ShaderBindingRegistryState> items = new CopyOnWriteArrayList<>();

   private ShaderBindingRegistry() {
      ShaderPresetRegistry.getINSTANCE().invoke4(this::invoke10);
      ShaderPresetRegistry.getINSTANCE().invoke5(this::invoke11);
   }

   public static ShaderBindingRegistry getINSTANCE() {
      return INSTANCE;
   }

   public synchronized void invoke(Module module, ShaderBinding shaderBinding) {
      this.invoke3((Object)module, shaderBinding);
   }

   public synchronized void invoke2(ConfigurableHudElement configurableHudElement, ShaderBinding shaderBinding2) {
      this.invoke3((Object)configurableHudElement, shaderBinding2);
   }

   private void invoke3(Object object, ShaderBinding shaderBinding3) {
      if (object != null && shaderBinding3 != null) {
         this.invoke12(object);
         ShaderBindingRegistry.ShaderBindingRegistryState shaderBindingRegistryState = new ShaderBindingRegistry.ShaderBindingRegistryState(object, shaderBinding3);
         this.items.add(shaderBindingRegistryState);
         this.invoke13(shaderBindingRegistryState);
      }
   }

   public synchronized void invoke4(Module module) {
      this.invoke6((Object)module);
   }

   public synchronized void invoke5(ConfigurableHudElement configurableHudElement2) {
      this.invoke6((Object)configurableHudElement2);
   }

   private void invoke6(Object object) {
      if (object != null) {
         this.invoke12(object);
         invoke17();
      }
   }

   public void invoke7(Module module, ShaderBinding shaderBinding4) {
      this.invoke9((Object)module, shaderBinding4);
   }

   public void invoke8(ConfigurableHudElement configurableHudElement3, ShaderBinding shaderBinding5) {
      this.invoke9((Object)configurableHudElement3, shaderBinding5);
   }

   private void invoke9(Object object, ShaderBinding shaderBinding6) {
      if (object != null && shaderBinding6 != null) {
         ShaderBindingRegistry.ShaderBindingRegistryState shaderBindingRegistryState2 = this.resolve(object);
         if (shaderBindingRegistryState2 != null) {
            this.invoke13(shaderBindingRegistryState2);
         }

         ArrayList arrayList = new ArrayList();

         for (Setting setting : resolve2(object)) {
            if (setting != null && setting.configTransient) {
               arrayList.add(setting);
            }
         }

         if (!arrayList.isEmpty()) {
            if (shaderBinding6.check()) {
               String text = shaderBinding6.resolve();
               if (text != null && !text.isBlank() && !"None".equalsIgnoreCase(text)) {
                  ShaderEffects.invoke5(text, arrayList);
               }
            } else {
               ShaderSurface shaderSurface = shaderBinding6.getESP();
               if (shaderSurface != null) {
                  ShaderEffects.invoke6(shaderSurface, arrayList);
               }
            }
         }
      }
   }

   private ShaderBindingRegistry.ShaderBindingRegistryState resolve(Object object) {
      for (ShaderBindingRegistry.ShaderBindingRegistryState shaderBindingRegistryState3 : this.items) {
         Object object2 = shaderBindingRegistryState3.weakReference.get();
         if (object2 == object) {
            return shaderBindingRegistryState3;
         }
      }

      return null;
   }

   private void invoke10(ShaderSurface shaderSurface2) {
      if (shaderSurface2 != null) {
         for (ShaderBindingRegistry.ShaderBindingRegistryState shaderBindingRegistryState4 : this.items) {
            ShaderBinding shaderBinding7 = shaderBindingRegistryState4.shaderBinding;
            if (shaderBinding7 != null && !shaderBinding7.check() && shaderBinding7.getESP() == shaderSurface2) {
               this.invoke13(shaderBindingRegistryState4);
            }
         }
      }
   }

   private void invoke11(String string) {
      if (string != null) {
         String text2 = ShaderPresetRegistry.resolve21(string);

         for (ShaderBindingRegistry.ShaderBindingRegistryState shaderBindingRegistryState5 : this.items) {
            ShaderBinding shaderBinding8 = shaderBindingRegistryState5.shaderBinding;
            if (shaderBinding8 != null && shaderBinding8.check()) {
               String text3 = shaderBinding8.resolve();
               if (text3 != null && text2.equals(ShaderPresetRegistry.resolve21(text3))) {
                  this.invoke13(shaderBindingRegistryState5);
               }
            }
         }
      }
   }

   private synchronized void invoke12(Object object) {
      for (ShaderBindingRegistry.ShaderBindingRegistryState shaderBindingRegistryState6 : this.items) {
         Object object3 = shaderBindingRegistryState6.weakReference.get();
         if (object3 == null) {
            this.items.remove(shaderBindingRegistryState6);
         } else if (object3 == object) {
            if (!shaderBindingRegistryState6.items.isEmpty()) {
               invoke16(object3, shaderBindingRegistryState6.items);
            }

            this.items.remove(shaderBindingRegistryState6);
         }
      }
   }

   private synchronized void invoke13(ShaderBindingRegistry.ShaderBindingRegistryState shaderBindingRegistryState7) {
      Object object4 = shaderBindingRegistryState7.weakReference.get();
      if (object4 == null) {
         this.items.remove(shaderBindingRegistryState7);
      } else {
         String text4;
         List items;
         if (shaderBindingRegistryState7.shaderBinding.check()) {
            String text5 = shaderBindingRegistryState7.shaderBinding.resolve();
            if (text5 == null || text5.isBlank() || "None".equalsIgnoreCase(text5)) {
               invoke14(object4, shaderBindingRegistryState7);
               shaderBindingRegistryState7.text = "";
               shaderBindingRegistryState7.text2 = "";
               return;
            }

            text4 = ShaderEffects.resolve3(text5);
            items = ShaderEffects.resolve9(text5);
            String text6 = "name:" + ShaderPresetRegistry.resolve21(text5);
            if (Objects.equals(shaderBindingRegistryState7.text, text4) && Objects.equals(shaderBindingRegistryState7.text2, text6)) {
               return;
            }

            shaderBindingRegistryState7.text2 = text6;
         } else {
            ShaderSurface shaderSurface3 = shaderBindingRegistryState7.shaderBinding.getESP();
            if (shaderSurface3 == null) {
               invoke14(object4, shaderBindingRegistryState7);
               shaderBindingRegistryState7.text = "";
               shaderBindingRegistryState7.text2 = "";
               return;
            }

            text4 = ShaderEffects.resolve4(shaderSurface3);
            items = ShaderEffects.resolve10(shaderSurface3);
            String text7 = "target:" + shaderSurface3.getText();
            if (Objects.equals(shaderBindingRegistryState7.text, text4) && Objects.equals(shaderBindingRegistryState7.text2, text7)) {
               return;
            }

            shaderBindingRegistryState7.text2 = text7;
         }

         shaderBindingRegistryState7.text = text4 == null ? "" : text4;
         invoke14(object4, shaderBindingRegistryState7);
         if (items != null && !items.isEmpty()) {
            for (Setting setting2 : (List<Setting>)items) {
               if (setting2 != null) {
                  setting2.configTransient = true;
               }
            }

            shaderBindingRegistryState7.items.addAll(items);
            invoke15(object4, items);
            invoke17();
         }
      }
   }

   private static void invoke14(Object object, ShaderBindingRegistry.ShaderBindingRegistryState shaderBindingRegistryState8) {
      if (!shaderBindingRegistryState8.items.isEmpty()) {
         invoke16(object, shaderBindingRegistryState8.items);
         shaderBindingRegistryState8.items.clear();
         invoke17();
      }
   }

   private static List<Setting> resolve2(Object object) {
      if (object instanceof Module module2) {
         return module2.getAllSettings();
      } else {
         return object instanceof ConfigurableHudElement configurableHudElement4 ? configurableHudElement4.resolve() : List.of();
      }
   }

   private static void invoke15(Object object, List<Setting> list) {
      if (object instanceof Module module3) {
         module3.addSettings(list.toArray(new Setting[0]));
      } else if (object instanceof ConfigurableHudElement configurableHudElement5) {
         configurableHudElement5.invoke2(list.toArray(new Setting[0]));
      }
   }

   private static void invoke16(Object object, List<Setting> list) {
      if (object instanceof Module module4) {
         module4.removeSettings(list);
      } else if (object instanceof ConfigurableHudElement configurableHudElement6) {
         configurableHudElement6.invoke3(list);
      }
   }

   private static void invoke17() {
      try {
         if (WildClient.INSTANCE != null && WildClient.INSTANCE.themeManager != null) {
            ClickGui clickGui = WildClient.INSTANCE.themeManager.resolve();
            if (clickGui != null) {
               clickGui.invoke4();
            }
         }
      } catch (Throwable exception) {
      }
   }

   static final class ShaderBindingRegistryState {
      final WeakReference<Object> weakReference;
      final ShaderBinding shaderBinding;
      final List<Setting> items = new ArrayList<>();
      String text = "";
      String text2 = "";

      ShaderBindingRegistryState(Object object, ShaderBinding shaderBinding9) {
         this.weakReference = new WeakReference<>(object);
         this.shaderBinding = shaderBinding9;
      }
   }
}
