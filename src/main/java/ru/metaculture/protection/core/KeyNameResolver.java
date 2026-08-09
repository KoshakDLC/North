package ru.metaculture.protection;

import net.minecraft.client.MinecraftClient;
import org.wild.module.api.Module;
import ru.metaculture.sdk.Compile;
import ru.metaculture.sdk.Loader;

public class KeyNameResolver {
   private static final KeyNameResolver INSTANCE = new KeyNameResolver();
   private static final MinecraftClient CLIENT = MinecraftClient.getInstance();
   private boolean flag = false;
   private boolean flag2 = false;

   public static KeyNameResolver getINSTANCE() {
      return INSTANCE;
   }

   @Compile
   public void invoke() {}

   @EventHandler
   public void onRawInput(RawInputEvent rawInputEvent) {
      if (rawInputEvent.getAction() == 1) {
         if (rawInputEvent.getKeyCode() >= 0) {
            if (WildClient.INSTANCE.moduleManager != null) {
               Module[] modules = WildClient.INSTANCE.moduleManager.getBoundModules(rawInputEvent.getKeyCode());
               if (modules != null) {
                  for (Module module2 : modules) {
                     module2.toggle();
                  }
               }

               this.invoke2(rawInputEvent.getKeyCode());
            }
         }
      }
   }

   @EventHandler
   public void onMouseButtonPosition(MouseButtonPositionEvent mouseButtonPositionEvent) {
      if (mouseButtonPositionEvent.isPress()) {
         if (CLIENT == null || CLIENT.currentScreen == null) {
            if (!this.flag2) {
               if (WildClient.INSTANCE.moduleManager != null) {
                  int intValue = -100 - mouseButtonPositionEvent.getButton();
                  Module[] modules2 = WildClient.INSTANCE.moduleManager.getBoundModules(intValue);
                  if (modules2 != null) {
                     for (Module module3 : modules2) {
                        module3.toggle();
                     }
                  }

                  this.invoke2(intValue);
               }
            }
         }
      }
   }

   @EventHandler
   public void onMouseScroll(MouseScrollEvent mouseScrollEvent) {
      if (CLIENT == null || CLIENT.currentScreen == null) {
         if (!this.flag2 && WildClient.INSTANCE.moduleManager != null) {
            if (!(Math.abs(mouseScrollEvent.getVerticalOffset()) < 1.0E-4)) {
               int intValue2 = mouseScrollEvent.getVerticalOffset() > 0.0 ? -200 : -201;
               Module[] modules3 = WildClient.INSTANCE.moduleManager.getBoundModules(intValue2);
               if (modules3 != null) {
                  for (Module module4 : modules3) {
                     module4.toggle();
                  }
               }
            }
         }
      }
   }

   private void invoke2(int i) {
      for (Module module5 : WildClient.INSTANCE.moduleManager.modules) {
         for (Setting setting : module5.getAllSettings()) {
            if (setting instanceof BooleanSetting booleanSetting) {
               this.invoke3(booleanSetting, i);
            } else if (setting instanceof GroupSetting groupSetting) {
               for (BooleanSetting booleanSetting2 : groupSetting.options) {
                  this.invoke3(booleanSetting2, i);
               }
            }
         }
      }
   }

   private void invoke3(BooleanSetting booleanSetting3, int i) {
      if (booleanSetting3.bindKey == i && !booleanSetting3.holdToEnable) {
         booleanSetting3.setValue(!booleanSetting3.getValue());
      }
   }

   public void invoke4() {
   }

   public void invoke5(String string) {
   }

   public void setFlag2(boolean bl) {
      this.flag2 = bl;
   }

   public boolean isFlag2() {
      return this.flag2;
   }

   public void invoke6(Module module, int i, KeybindMode keybindMode) {
      if (module != null) {
         module.bindKey = i;
      }
   }

   public void invoke7(Module module, Setting setting2, KeybindMode keybindMode2, int i, Object object) {
   }

   public void invoke8(String string, String string2) {
   }

   public Object resolve(String string, String string2) {
      return null;
   }

   public String resolve2(int i) {
      if (i == -200) {
         return "Wheel Up";
      } else if (i == -201) {
         return "Wheel Down";
      } else if (i <= -100) {
         return "Mouse " + (Math.abs(i + 100) + 1);
      } else if (i == -1) {
         return "None";
      } else if (i >= 65 && i <= 90) {
         return String.valueOf((char)(65 + (i - 65)));
      } else if (i >= 48 && i <= 57) {
         return String.valueOf((char)(48 + (i - 48)));
      } else if (i == 32) {
         return "Space";
      } else if (i == 257) {
         return "Enter";
      } else if (i == 256) {
         return "Escape";
      } else if (i == 259) {
         return "Backspace";
      } else if (i == 258) {
         return "Tab";
      } else if (i == 340 || i == 344) {
         return "Shift";
      } else if (i == 341 || i == 345) {
         return "Ctrl";
      } else if (i == 342 || i == 346) {
         return "Alt";
      } else {
         return i >= 290 && i <= 314 ? "F" + (i - 290 + 1) : "Key " + i;
      }
   }

   static {
      Loader.initialize();
   }
}
