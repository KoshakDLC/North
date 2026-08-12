package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import ru.metaculture.sdk.Compile;
import ru.metaculture.sdk.Loader;

public class CommandManager {
   private final List<Command> commands = new ArrayList<>();

   public CommandManager() {
      this.register(new HelpCommand(this));
      this.register(new FriendCommand());
      this.register(new PrefixCommand());
      this.register(new ConfigCommand());
      this.register(new CreeperFarmCommand());
      this.register(new CocoaFarmCommand());
      this.register(new ChorusFarmCommand());
      this.register(new CopperFarmCommand());
      this.register(new EndFarmCommand());
      this.register(new TelegramApiCommand());
      this.register(new MacroCommand());
      this.register(new GpsCommand());
      this.register(new BindCommand());
      this.register(new AutoBuyCommand());
      this.register(new AuctionHouseCommand());
      this.register(new AuctionSearchCommand());
      this.register(new ReconnectCommand());
      this.register(new XrayCommand());
      this.register(new AiRotationCommand());
   }

   @Compile
   public void register(Command registeredCommand) {
      if (registeredCommand != null
         && this.commands.stream().noneMatch(existingCommand -> existingCommand.getName().equalsIgnoreCase(registeredCommand.getName()))) {
         this.commands.add(registeredCommand);
         this.commands.sort(Comparator.comparing(Command::getName, String.CASE_INSENSITIVE_ORDER));
         EventManager.register(registeredCommand);
      }
   }

   @Compile
   public void execute(String string) {
      if (string == null || WildClient.INSTANCE == null) {
         return;
      }

      String text = WildClient.INSTANCE.getCommandPrefix();
      if (text == null || text.isEmpty() || !string.startsWith(text)) {
         return;
      }

      String text2 = string.substring(text.length()).trim();
      if (text2.isEmpty()) {
         ChatUtil.sendClientMessage("§cВведите команду. Используйте §f" + text + "help§c.");
         return;
      }

      String[] texts = text2.split("\\s+");
      Command command2 = this.commands.stream().filter(command -> command.getName().equalsIgnoreCase(texts[0])).findFirst().orElse(null);
      if (command2 == null) {
         ChatUtil.sendClientMessage("§cНеизвестная команда: §f" + texts[0] + "§c. Используйте §f" + text + "help§c.");
         return;
      }

      try {
         command2.execute(Arrays.copyOfRange(texts, 1, texts.length));
      } catch (Throwable exception) {
         ChatUtil.sendClientMessage("§cОшибка команды §f" + command2.getName() + "§c: " + exception.getClass().getSimpleName());
         exception.printStackTrace();
      }
   }

   public List<Command> getCommands() {
      return this.commands;
   }

   static {
      Loader.initialize();
   }
}
