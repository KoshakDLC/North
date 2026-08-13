package ru.metaculture.protection;

import java.util.List;
import java.util.Locale;
import ru.metaculture.sdk.Compile;

public class WardenCommand extends Command {
   public WardenCommand() {
      super("warden", "Список анархий AutoWarden", ".warden <add|remove|list|clear> [номер]");
      this.addCompletionProvider("add", List::of);
      this.addCompletionProvider("remove", List::of);
      this.addCompletionProvider("list", List::of);
      this.addCompletionProvider("clear", List::of);
   }

   @Compile
   @Override
   public void execute(String[] strings) {
      AutoWarden module = getModule();
      if (module == null) {
         ChatUtil.sendClientMessage("§cМодуль AutoWarden недоступен.");
         return;
      }
      if (strings.length == 0) {
         ChatUtil.sendClientMessage("§cИспользование: §f.warden <add|remove|list|clear> [номер]");
         return;
      }

      List<Integer> anarchies = module.q();
      switch (strings[0].toLowerCase(Locale.ROOT)) {
         case "add" -> {
            if (strings.length < 2) {
               ChatUtil.sendClientMessage("Использование: .warden add <анархия>");
               return;
            }
            int anarchy = parse(strings[1]);
            if (anarchy < 1 || anarchy > 999) {
               ChatUtil.sendClientMessage("Анархия должна быть от 1 до 999.");
               return;
            }
            if (anarchies.contains(anarchy)) {
               ChatUtil.sendClientMessage("Анархия " + anarchy + " уже в списке.");
               return;
            }
            if (anarchies.size() >= 10) {
               ChatUtil.sendClientMessage("Можно добавить максимум 10 анархий.");
               return;
            }
            anarchies.add(anarchy);
            ChatUtil.sendClientMessage("Анархия " + anarchy + " добавлена.");
         }
         case "remove" -> {
            if (strings.length < 2) {
               ChatUtil.sendClientMessage("Использование: .warden remove <анархия>");
               return;
            }
            int anarchy = parse(strings[1]);
            if (!anarchies.remove(Integer.valueOf(anarchy))) {
               ChatUtil.sendClientMessage("Анархия " + anarchy + " не найдена.");
               return;
            }
            ChatUtil.sendClientMessage("Анархия " + anarchy + " удалена.");
         }
         case "list" -> {
            if (anarchies.isEmpty()) {
               ChatUtil.sendClientMessage("Список анархий пуст.");
            } else {
               ChatUtil.sendClientMessage("Анархии (" + anarchies.size() + "): " + anarchies);
            }
         }
         case "clear" -> {
            anarchies.clear();
            ChatUtil.sendClientMessage("Список анархий очищен.");
         }
         default -> ChatUtil.sendClientMessage("Использование: .warden <add|remove|list|clear>");
      }
   }

   private static int parse(String raw) {
      try {
         return Integer.parseInt(raw.replaceAll("[^0-9]", ""));
      } catch (NumberFormatException exception) {
         return -1;
      }
   }

   private static AutoWarden getModule() {
      return WildClient.INSTANCE == null || WildClient.INSTANCE.moduleManager == null
         ? null
         : WildClient.INSTANCE.moduleManager.getModule(AutoWarden.class);
   }
}
