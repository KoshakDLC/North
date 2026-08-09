package ru.metaculture.protection;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import org.lwjgl.glfw.GLFW;
import org.wild.module.api.Module;
import ru.metaculture.sdk.Compile;
import ru.metaculture.sdk.Loader;

public class BindCommand extends Command {
   private static final List<String> ITEMS = List.of("list", "clear", "del", "delete", "remove", "unbind");
   private static final List<String> ITEMS_2 = List.of(
      "A",
      "B",
      "C",
      "D",
      "E",
      "F",
      "G",
      "H",
      "I",
      "J",
      "K",
      "L",
      "M",
      "N",
      "O",
      "P",
      "Q",
      "R",
      "S",
      "T",
      "U",
      "V",
      "W",
      "X",
      "Y",
      "Z",
      "0",
      "1",
      "2",
      "3",
      "4",
      "5",
      "6",
      "7",
      "8",
      "9",
      "F1",
      "F2",
      "F3",
      "F4",
      "F5",
      "F6",
      "F7",
      "F8",
      "F9",
      "F10",
      "F11",
      "F12",
      "SPACE",
      "ENTER",
      "TAB",
      "ESCAPE",
      "BACKSPACE",
      "DELETE",
      "INSERT",
      "HOME",
      "END",
      "PAGEUP",
      "PAGEDOWN",
      "LEFT",
      "RIGHT",
      "UP",
      "DOWN",
      "LSHIFT",
      "RSHIFT",
      "LCONTROL",
      "RCONTROL",
      "LALT",
      "RALT",
      "MOUSE1",
      "MOUSE2",
      "MOUSE3",
      "MOUSE4",
      "MOUSE5",
      "WHEEL_UP",
      "WHEEL_DOWN",
      "NONE"
   );

   public BindCommand() {
      super("bind", "Управление биндами модулей", ".bind <module> <key> | .bind list | .bind del <module> | .bind clear");
   }

   @Override
   public List<String> getSuggestions(String[] strings) {
      if (strings.length == 2) {
         String text = strings[1].toLowerCase(Locale.ROOT);
         LinkedHashSet linkedHashSet = new LinkedHashSet();
         ITEMS.stream().filter(string2 -> string2.startsWith(text)).forEach(linkedHashSet::add);
         this.resolve2().stream().filter(string2 -> string2.toLowerCase(Locale.ROOT).startsWith(text)).forEach(linkedHashSet::add);
         return new ArrayList<>(linkedHashSet);
      } else {
         if (strings.length == 3) {
            String text2 = strings[1].toLowerCase(Locale.ROOT);
            String text3 = strings[2].toLowerCase(Locale.ROOT);
            if (this.check(text2)) {
               return this.resolve2().stream().filter(string2 -> string2.toLowerCase(Locale.ROOT).startsWith(text3)).toList();
            }

            if (!ITEMS.contains(text2)) {
               return ITEMS_2.stream().filter(string2 -> string2.toLowerCase(Locale.ROOT).startsWith(text3)).toList();
            }
         }

         return List.of();
      }
   }

   @Compile
   @Override
   public void execute(String[] strings) {
      if (strings.length == 0) {
         ChatUtil.sendClientMessage("§cИспользование: §f" + this.getUsage());
         return;
      }

      String text4 = strings[0].toLowerCase(Locale.ROOT);
      if (text4.equals("list")) {
         this.invoke3();
      } else if (text4.equals("clear")) {
         this.invoke4();
      } else if (this.check(text4)) {
         this.invoke2(strings);
      } else {
         this.invoke(strings);
      }
   }

   @Compile
   private void invoke(String[] strings) {
      if (strings.length != 2) {
         ChatUtil.sendClientMessage("§cИспользование: §f.bind <module> <key>");
         return;
      }

      Module module = this.resolve(strings[0]);
      if (module == null) {
         this.invoke5(strings[0]);
         return;
      }

      Integer integer = this.resolve3(strings[1]);
      if (integer == null) {
         ChatUtil.sendClientMessage("§cНеизвестная клавиша: §f" + strings[1]);
         return;
      }

      module.bindKey = integer;
      this.invoke6();
      ChatUtil.sendClientMessage(
         integer == -1
            ? "§aБинд снят с модуля §f" + module.name
            : "§aМодуль §f" + module.name + " §aназначен на §f" + this.resolve4(integer)
      );
   }

   @Compile
   private void invoke2(String[] strings) {
      if (strings.length != 2) {
         ChatUtil.sendClientMessage("§cИспользование: §f.bind del <module>");
         return;
      }

      Module module2 = this.resolve(strings[1]);
      if (module2 == null) {
         this.invoke5(strings[1]);
         return;
      }

      module2.bindKey = -1;
      this.invoke6();
      ChatUtil.sendClientMessage("§aБинд снят с модуля §f" + module2.name);
   }

   @Compile
   private void invoke3() {
      if (WildClient.INSTANCE == null || WildClient.INSTANCE.moduleManager == null) {
         ChatUtil.sendClientMessage("§7Список биндов пуст.");
         return;
      }

      List<Module> items = WildClient.INSTANCE.moduleManager.getModules().stream()
         .filter(module -> module.bindKey != -1)
         .sorted(java.util.Comparator.comparing(module -> module.name, String.CASE_INSENSITIVE_ORDER))
         .toList();
      if (items.isEmpty()) {
         ChatUtil.sendClientMessage("§7Список биндов пуст.");
         return;
      }

      ChatUtil.sendClientMessage("§fБинды (§7" + items.size() + "§f):");
      for (Module module3 : items) {
         ChatUtil.sendClientMessage("§f" + module3.name + " §7— §f" + this.resolve4(module3.bindKey));
      }
   }

   @Compile
   private void invoke4() {
      if (WildClient.INSTANCE == null || WildClient.INSTANCE.moduleManager == null) {
         return;
      }

      int intValue = 0;
      for (Module module4 : WildClient.INSTANCE.moduleManager.getModules()) {
         if (module4.bindKey != -1) {
            module4.bindKey = -1;
            intValue++;
         }
      }
      this.invoke6();
      ChatUtil.sendClientMessage("§aСнято биндов: §f" + intValue);
   }

   private Module resolve(String string) {
      String text5 = this.resolve5(string);
      ArrayList arrayList = new ArrayList();

      for (Module module5 : WildClient.INSTANCE.moduleManager.getModules()) {
         if (text5.equals(this.resolve5(module5.name))
            || text5.equals(this.resolve5(module5.getDisplayName()))
            || text5.equals(this.resolve5(module5.getClass().getSimpleName()))) {
            return module5;
         }

         if (this.resolve5(module5.name).contains(text5)
            || this.resolve5(module5.getDisplayName()).contains(text5)
            || this.resolve5(module5.getClass().getSimpleName()).contains(text5)) {
            arrayList.add(module5);
         }
      }

      return arrayList.size() == 1 ? (Module)arrayList.get(0) : null;
   }

   private void invoke5(String string) {
      List items2 = this.resolve2().stream().filter(string2 -> this.resolve5(string2).contains(this.resolve5(string))).limit(8L).toList();
      if (items2.isEmpty()) {
         ChatUtil.sendClientMessage("§cМодуль не найден: §f" + string);
      } else {
         ChatUtil.sendClientMessage("§cНеоднозначный модуль: §f" + string + " §7(" + String.join(", ", items2) + ")");
      }
   }

   private List<String> resolve2() {
      return WildClient.INSTANCE.moduleManager == null
         ? List.of()
         : WildClient.INSTANCE.moduleManager.getModules().stream().map(module -> module.name).sorted(String.CASE_INSENSITIVE_ORDER).toList();
   }

   private Integer resolve3(String string) {
      String text6 = string.trim().toUpperCase(Locale.ROOT).replace("-", "_").replace(" ", "_");
      if (text6.equals("NONE") || text6.equals("NULL") || text6.equals("UNBOUND") || text6.equals("CLEAR")) {
         return -1;
      } else if (text6.equals("WHEELUP") || text6.equals("WHEEL_UP") || text6.equals("MWHEELUP")) {
         return -200;
      } else if (text6.equals("WHEELDOWN") || text6.equals("WHEEL_DOWN") || text6.equals("MWHEELDOWN")) {
         return -201;
      } else if (text6.equals("LMB") || text6.equals("MOUSELEFT") || text6.equals("MOUSE_LEFT")) {
         return -100;
      } else if (text6.equals("RMB") || text6.equals("MOUSERIGHT") || text6.equals("MOUSE_RIGHT")) {
         return -101;
      } else if (!text6.equals("MMB") && !text6.equals("MOUSEMIDDLE") && !text6.equals("MOUSE_MIDDLE")) {
         if (text6.matches("MOUSE_?\\d+")) {
            int intValue2 = Integer.parseInt(text6.replace("MOUSE", "").replace("_", ""));
            if (intValue2 >= 1 && intValue2 <= 16) {
               return -100 - (intValue2 - 1);
            }
         }

         int intValue3 = KeyboardKey.compute(text6.replace("_", ""));
         if (intValue3 != -1) {
            return intValue3;
         } else {
            intValue3 = KeyboardKey.compute(text6);
            if (intValue3 != -1) {
               return intValue3;
            } else {
               try {
                  Field field = GLFW.class.getField("GLFW_KEY_" + text6);
                  return field.getInt(null);
               } catch (ReflectiveOperationException reflectiveOperationException) {
                  return null;
               }
            }
         }
      } else {
         return -102;
      }
   }

   private String resolve4(int i) {
      return KeyNameResolver.getINSTANCE().resolve2(i);
   }

   private boolean check(String string) {
      return string.equals("del") || string.equals("delete") || string.equals("remove") || string.equals("unbind");
   }

   private String resolve5(String string) {
      return string == null ? "" : string.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9а-яё]", "");
   }

   private void invoke6() {
      if (WildClient.INSTANCE.configManager != null) {
         WildClient.INSTANCE.configManager.scheduleSave();
      }
   }

   static {
      Loader.initialize();
   }
}
