package ru.metaculture.protection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Locale;
import com.google.gson.reflect.TypeToken;
import ru.metaculture.sdk.Compile;
import ru.metaculture.sdk.Loader;

public class MacroCommand extends Command {
   private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
   private final File file = new File(WildClient.INSTANCE.file, "macros.cfg");
   public static List<MacroCommand.MacroCommandState> items = new ArrayList<>();

   public MacroCommand() {
      super("macro", "Управление макросами", ".macro <add/remove/list/clear/load>");
      this.addCompletionProvider("add", () -> List.of("add"));
      this.addCompletionProvider("remove", () -> items.stream().map(macroCommandState -> macroCommandState.text).toList());
      this.addCompletionProvider("load", () -> List.of("load"));
      this.addCompletionProvider("list", () -> List.of("list"));
      this.addCompletionProvider("clear", () -> List.of("clear"));
      this.invoke2();
   }

   @Compile
   @Override
   public void execute(String[] strings) {
      if (strings.length == 0) {
         ChatUtil.sendClientMessage("§cИспользование: §f" + this.getUsage());
         return;
      }

      switch (strings[0].toLowerCase(Locale.ROOT)) {
         case "add" -> this.invoke(strings);
         case "remove", "del", "delete" -> this.invoke6(strings);
         case "list" -> this.invoke3();
         case "clear" -> this.invoke4();
         case "load" -> {
            this.invoke2();
            ChatUtil.sendClientMessage("§aМакросы перезагружены: §f" + items.size());
         }
         default -> ChatUtil.sendClientMessage("§cИспользование: §f" + this.getUsage());
      }
   }

   @EventHandler
   public void onRawInput(RawInputEvent rawInputEvent) {
      if (rawInputEvent.getAction() == 1) {
         for (MacroCommand.MacroCommandState macroCommandState2 : items) {
            if (rawInputEvent.getKeyCode() == macroCommandState2.intValue) {
               a_.player.networkHandler.sendChatMessage(macroCommandState2.text2);
            }
         }
      }
   }

   @Compile
   private void invoke(String[] strings) {
      if (strings.length < 4) {
         ChatUtil.sendClientMessage("§cИспользование: §f.macro add <name> <key> <message>");
         return;
      }

      String text = strings[1];
      int intValue = KeyboardKey.compute(strings[2].toUpperCase(Locale.ROOT).replace("_", ""));
      if (intValue == -1) {
         ChatUtil.sendClientMessage("§cНеизвестная клавиша: §f" + strings[2]);
         return;
      }

      String text2 = String.join(" ", java.util.Arrays.copyOfRange(strings, 3, strings.length));
      items.removeIf(macro -> macro.text.equalsIgnoreCase(text));
      items.add(new MacroCommand.MacroCommandState(text, intValue, text2));
      this.invoke5();
      ChatUtil.sendClientMessage("§aМакрос §f'" + text + "' §aназначен на §f" + strings[2].toUpperCase(Locale.ROOT));
   }

   @Compile
   public void invoke2() {
      if (!this.file.isFile()) {
         return;
      }

      try (java.io.Reader reader = Files.newBufferedReader(this.file.toPath(), StandardCharsets.UTF_8)) {
         List<MacroCommand.MacroCommandState> items = this.gson.fromJson(reader, new TypeToken<List<MacroCommand.MacroCommandState>>() {}.getType());
         items.clear();
         if (items != null) {
            items.addAll(items);
         }
      } catch (Exception exception) {
         System.out.println("[MacroCommand] Failed to load macros: " + exception.getMessage());
      }
   }

   @Compile
   private void invoke3() {
      if (items.isEmpty()) {
         ChatUtil.sendClientMessage("§7Список макросов пуст.");
         return;
      }
      ChatUtil.sendClientMessage("§fМакросы (§7" + items.size() + "§f):");
      items.stream().sorted((left, right) -> left.text.compareToIgnoreCase(right.text)).forEach(macro ->
         ChatUtil.sendClientMessage("§f" + macro.text + " §7[" + KeyboardKey.resolve(macro.intValue) + "] — §f" + macro.text2)
      );
   }

   @Compile
   private void invoke4() {
      int intValue2 = items.size();
      items.clear();
      this.invoke5();
      ChatUtil.sendClientMessage("§aМакросы очищены (§f" + intValue2 + "§a).");
   }

   @Compile
   private void invoke5() {
      try {
         File file = this.file.getParentFile();
         if (file != null) {
            Files.createDirectories(file.toPath());
         }
         try (java.io.Writer writer = Files.newBufferedWriter(this.file.toPath(), StandardCharsets.UTF_8)) {
            this.gson.toJson(items, writer);
         }
      } catch (Exception exception2) {
         System.out.println("[MacroCommand] Failed to save macros: " + exception2.getMessage());
      }
   }

   @Compile
   private void invoke6(String[] strings) {
      if (strings.length != 2) {
         ChatUtil.sendClientMessage("§cИспользование: §f.macro remove <name>");
         return;
      }
      boolean flag = items.removeIf(macro -> macro.text.equalsIgnoreCase(strings[1]));
      if (flag) {
         this.invoke5();
      }
      ChatUtil.sendClientMessage(flag ? "§aМакрос удалён: §f" + strings[1] : "§eМакрос не найден: §f" + strings[1]);
   }

   static {
      Loader.initialize();
   }

   public static class MacroCommandState {
      public String text;
      public String text2;
      public int intValue;

      public MacroCommandState(String string, int i, String string2) {
         this.text = string;
         this.intValue = i;
         this.text2 = string2;
      }
   }
}
