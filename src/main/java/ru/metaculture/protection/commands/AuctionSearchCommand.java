package ru.metaculture.protection;

import java.util.List;
import java.util.ArrayList;
import java.util.Locale;
import ru.metaculture.sdk.Compile;
import ru.metaculture.sdk.Loader;

public final class AuctionSearchCommand extends Command {
   public AuctionSearchCommand() {
      super("ahsearch", "Поиск лотов по названию и максимальной цене", ".ahsearch <название> <максимальная цена>");
   }

   @Compile
   @Override
   public void execute(String[] strings) {
      if (strings.length == 1 && this.check2(strings[0])) {
         AhHelper.invoke5();
         ChatUtil.sendClientMessage("§7Фильтр поиска аукциона сброшен.");
         return;
      }

      if (strings.length < 2 || a_ == null || a_.player == null || a_.player.networkHandler == null) {
         this.invoke();
         return;
      }

      List<String> items = new ArrayList<>(List.of(strings));
      int intValue = this.compute(items);
      if (intValue <= 0) {
         this.invoke();
         return;
      }

      String text = String.join(" ", items.subList(0, intValue)).trim();
      Long maxPrice = this.resolve(items.subList(intValue, items.size()));
      if (text.isEmpty() || maxPrice == null || maxPrice <= 0L) {
         this.invoke();
         return;
      }

      AhHelper.invoke3(text, maxPrice);
      a_.player.networkHandler.sendChatCommand("ah search " + text);
      ChatUtil.sendClientMessage("§aПоиск §f'" + text + "' §aдо §f" + this.resolve2(maxPrice));
   }

   @Override
   public List<String> getSuggestions(String[] strings) {
      String text2 = strings.length == 0 ? "" : strings[strings.length - 1].toLowerCase(Locale.ROOT);
      return List.of("clear").stream().filter(string2 -> string2.startsWith(text2)).toList();
   }

   private int compute(List<String> list) {
      if (list.size() >= 2 && this.check((String)list.getLast())) {
         int intValue2 = list.size() - 1;

         while (intValue2 > 1 && this.compute2((String)list.get(intValue2)) == 3 && this.check((String)list.get(intValue2 - 1))) {
            intValue2--;
         }

         return intValue2;
      } else {
         return -1;
      }
   }

   private Long resolve(List<String> list) {
      StringBuilder stringBuilder = new StringBuilder();

      for (String text3 : list) {
         stringBuilder.append(text3.replaceAll("[^0-9]", ""));
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

   private boolean check(String string) {
      return string != null && string.matches("[0-9][0-9_.,]*");
   }

   private int compute2(String string) {
      return string.replaceAll("[^0-9]", "").length();
   }

   private boolean check2(String string) {
      return string.equalsIgnoreCase("clear") || string.equalsIgnoreCase("off") || string.equalsIgnoreCase("reset");
   }

   private String resolve2(long l) {
      return String.format(Locale.ROOT, "%,d", l).replace(',', ' ');
   }

   private void invoke() {
      ChatUtil.sendClientMessage("§cИспользование: " + this.getUsage());
      ChatUtil.sendClientMessage("§7Пример: §f.ahsearch зачарованное золотое яблоко 100 000");
      ChatUtil.sendClientMessage("§7Сброс: §f.ahsearch clear");
   }

   static {
      Loader.initialize();
   }
}
