package ru.metaculture.protection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import ru.metaculture.sdk.Compile;
import ru.metaculture.sdk.Loader;

public class PartyCommand extends Command {
   private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
   private final File file = new File(WildClient.INSTANCE.file, "party.cfg");
   private static final Map<String, PartyCommand.PartyCommandState> VALUES_BY_KEY = new HashMap<>();
   private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

   public PartyCommand() {
      super("party", "Управление группой (Party List)", ".party <add/remove/list/clear> <name>");
      this.addCompletionProvider("add", () -> a_.getNetworkHandler().getPlayerList().stream().map(playerListEntry -> playerListEntry.getProfile().getName()).toList());
      this.addCompletionProvider("remove", () -> new ArrayList<>(VALUES_BY_KEY.keySet()));
      this.addCompletionProvider("list", List::of);
      this.addCompletionProvider("clear", List::of);
      this.invoke5();
   }

   @Compile
   @Override
   public void execute(String[] strings) {}

   @Compile
   private void invoke(String[] strings) {}

   @Compile
   private void invoke2(String[] strings) {}

   @Compile
   private void invoke3() {}

   @Compile
   private void invoke4() {}

   @Compile
   public static boolean check(String string) { return false; }

   public static Set<String> resolve() {
      return VALUES_BY_KEY.keySet();
   }

   @Compile
   private void invoke5() {}

   @Compile
   private void invoke6() {}

   static {
      Loader.initialize();
   }

   static class PartyCommandState {
      String text;
      Date date;

      PartyCommandState(String string, Date date) {
         this.text = string;
         this.date = date;
      }
   }
}
