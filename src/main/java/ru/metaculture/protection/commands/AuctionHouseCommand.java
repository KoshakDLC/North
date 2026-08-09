package ru.metaculture.protection;

import java.util.Locale;
import ru.metaculture.sdk.Compile;
import ru.metaculture.sdk.Loader;

public final class AuctionHouseCommand extends Command {
   public AuctionHouseCommand() {
      super("ah", "Открыть общую страницу аукциона с фильтром цены", ".ah [максимальная цена]");
   }

   @Compile
   @Override
   public void execute(String[] strings) {
      if (a_ == null || a_.player == null || a_.player.networkHandler == null) {
         ChatUtil.sendClientMessage("§cИгрок не подключён к серверу.");
         return;
      }

      if (strings.length == 0) {
         AhHelper.invoke5();
         a_.player.networkHandler.sendChatCommand("ah");
         return;
      }

      Long maxPrice = this.resolve(strings);
      if (maxPrice == null || maxPrice <= 0L) {
         ChatUtil.sendClientMessage("§cИспользование: §f" + this.getUsage());
         return;
      }

      AhHelper.invoke4(maxPrice);
      a_.player.networkHandler.sendChatCommand("ah");
      ChatUtil.sendClientMessage("§aФильтр аукциона: цена до §f" + this.resolve2(maxPrice));
   }

   private Long resolve(String[] strings) {
      StringBuilder stringBuilder = new StringBuilder();

      for (String text : strings) {
         if (text == null || !text.matches("[0-9][0-9_.,]*")) {
            return null;
         }

         stringBuilder.append(text.replaceAll("[^0-9]", ""));
      }

      if (stringBuilder.isEmpty()) {
         return null;
      } else {
         try {
            return Long.parseLong(stringBuilder.toString());
         } catch (NumberFormatException numberFormatException) {
            return null;
         }
      }
   }

   private String resolve2(long l) {
      return String.format(Locale.ROOT, "%,d", l).replace(',', ' ');
   }

   static {
      Loader.initialize();
   }
}
