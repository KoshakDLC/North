package ru.metaculture.protection;

import java.util.List;
import ru.metaculture.sdk.Compile;
import ru.metaculture.sdk.Loader;

public class AutoBuyCommand extends Command {
   public AutoBuyCommand() {
      super("autobuy", "Управление AutoBuy", ".autobuy <ignore/unignore/list/clear> [name]");
      this.addCompletionProvider("ignore", this::resolve2);
      this.addCompletionProvider("add", this::resolve2);
      this.addCompletionProvider("unignore", AutoBuy::resolve);
      this.addCompletionProvider("remove", AutoBuy::resolve);
      this.addCompletionProvider("list", List::of);
      this.addCompletionProvider("clear", List::of);
   }

   @Compile
   @Override
   public void execute(String[] strings) {
      if (strings.length == 0) {
         this.invoke5();
         return;
      }

      switch (strings[0].toLowerCase(java.util.Locale.ROOT)) {
         case "ignore", "add", "+" -> this.invoke(strings);
         case "unignore", "remove", "del", "delete", "-" -> this.invoke2(strings);
         case "list" -> this.invoke3();
         case "clear" -> this.invoke4();
         default -> this.invoke5();
      }
   }

   private void invoke(String[] strings) {
      String text = this.resolve(strings);
      if (text == null || text.isBlank()) {
         ChatUtil.sendClientMessage("§cУкажите ник игрока: §f.autobuy ignore Nick");
      } else if (AutoBuy.check3(text)) {
         ChatUtil.sendClientMessage("§e[AutoBuy] Игрок уже в ignore: §f" + text);
      } else if (AutoBuy.check(text)) {
         this.invoke6();
         ChatUtil.sendClientMessage("§a[AutoBuy] Игрок добавлен в ignore: §f" + text);
      } else {
         ChatUtil.sendClientMessage("§c[AutoBuy] Некорректный ник.");
      }
   }

   private void invoke2(String[] strings) {
      String text2 = this.resolve(strings);
      if (text2 != null && !text2.isBlank()) {
         if (AutoBuy.check2(text2)) {
            this.invoke6();
            ChatUtil.sendClientMessage("§a[AutoBuy] Игрок удален из ignore: §f" + text2);
         } else {
            ChatUtil.sendClientMessage("§e[AutoBuy] Игрок не найден в ignore: §f" + text2);
         }
      } else {
         ChatUtil.sendClientMessage("§cУкажите ник игрока: §f.autobuy unignore Nick");
      }
   }

   private void invoke3() {
      List items = AutoBuy.resolve();
      if (items.isEmpty()) {
         ChatUtil.sendClientMessage("§7[AutoBuy] Ignore-список продавцов пуст.");
      } else {
         ChatUtil.sendClientMessage("§f[AutoBuy] Ignore-продавцы (§7" + items.size() + "§f): §7" + String.join(", ", items));
      }
   }

   private void invoke4() {
      if (AutoBuy.resolve().isEmpty()) {
         ChatUtil.sendClientMessage("§7[AutoBuy] Ignore-список продавцов уже пуст.");
      } else {
         AutoBuy.invoke2();
         this.invoke6();
         ChatUtil.sendClientMessage("§a[AutoBuy] Ignore-список продавцов очищен.");
      }
   }

   private void invoke5() {
      ChatUtil.sendClientMessage("§cИспользование: " + this.getUsage());
      ChatUtil.sendClientMessage("§7Пример: §f.autobuy ignore QWEERZIK");
   }

   private String resolve(String[] strings) {
      if (strings.length < 2) {
         return null;
      } else if ("+".equals(strings[1])) {
         return strings.length >= 3 ? strings[2] : null;
      } else {
         return strings[1];
      }
   }

   private List<String> resolve2() {
      return a_.getNetworkHandler() == null
         ? List.of()
         : a_.getNetworkHandler()
            .getPlayerList()
            .stream()
            .map(playerListEntry -> playerListEntry.getProfile().getName())
            .filter(string -> string != null && !string.isBlank())
            .toList();
   }

   private void invoke6() {
      if (WildClient.INSTANCE != null && WildClient.INSTANCE.configManager != null) {
         WildClient.INSTANCE.configManager.scheduleSave();
      }
   }

   static {
      Loader.initialize();
   }
}
