package ru.metaculture.protection;

import ru.metaculture.sdk.Compile;
import ru.metaculture.sdk.Loader;

public class HelpCommand extends Command {
   private final CommandManager commandManager;

   public HelpCommand(CommandManager commandManager) {
      super("help", "Показывает список всех команд", ".help");
      this.commandManager = commandManager;
   }

   @Compile
   @Override
   public void execute(String[] strings) {
      String text = WildClient.INSTANCE == null ? "." : WildClient.INSTANCE.getCommandPrefix();
      ChatUtil.sendClientMessage("§fКоманды Wild (§7" + this.commandManager.getCommands().size() + "§f):");

      for (Command command : this.commandManager.getCommands()) {
         ChatUtil.sendClientMessage("§f" + text + command.getName() + " §7— " + command.getDescription());
      }
   }

   static {
      Loader.initialize();
   }
}
