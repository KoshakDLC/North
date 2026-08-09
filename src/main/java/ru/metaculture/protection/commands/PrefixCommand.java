package ru.metaculture.protection;

import java.util.List;
import ru.metaculture.sdk.Compile;
import ru.metaculture.sdk.Loader;

public class PrefixCommand extends Command {
   public PrefixCommand() {
      super("prefix", "Изменение префикса команд", ".prefix set <symbol>");
      this.addCompletionProvider("set", List::of);
   }

   @Compile
   @Override
   public void execute(String[] strings) {
      if (strings.length != 2 || !strings[0].equalsIgnoreCase("set") || strings[1].isBlank()) {
         ChatUtil.sendClientMessage("§cИспользование: §f" + this.getUsage());
         return;
      }

      String text = strings[1].trim();
      if (text.length() > 3 || text.chars().anyMatch(Character::isWhitespace) || text.startsWith("/")) {
         ChatUtil.sendClientMessage("§cПрефикс должен содержать 1–3 непробельных символа и не может начинаться с '/'.");
         return;
      }

      WildClient.INSTANCE.setCommandPrefix(text);
      if (WildClient.INSTANCE.configManager != null) {
         WildClient.INSTANCE.configManager.scheduleSave();
      }

      ChatUtil.sendClientMessage("§aПрефикс команд изменён на §f" + text);
   }

   static {
      Loader.initialize();
   }
}
