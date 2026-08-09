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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import com.google.gson.reflect.TypeToken;
import net.minecraft.client.MinecraftClient;
import ru.metaculture.sdk.Compile;
import ru.metaculture.sdk.Loader;

public class FriendCommand extends Command {
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   private static final File FILE = new File(WildClient.INSTANCE.file, "friend.cfg");
   private static final Map<String, FriendCommand.FriendCommandState> VALUES_BY_KEY = new HashMap<>();
   private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm");

   public FriendCommand() {
      super("friend", "Управление друзьями", ".friend <add/remove/list/clear> <name>");
      this.addCompletionProvider("add", () -> a_.getNetworkHandler().getPlayerList().stream().map(playerListEntry -> playerListEntry.getProfile().getName()).toList());
      this.addCompletionProvider("remove", () -> new ArrayList<>(VALUES_BY_KEY.keySet()));
      this.addCompletionProvider("list", List::of);
      this.addCompletionProvider("clear", List::of);
      this.invoke6();
   }

   @Compile
   @Override
   public void execute(String[] strings) {
      if (strings.length == 0) {
         ChatUtil.sendClientMessage("§cИспользование: §f" + this.getUsage());
         return;
      }

      switch (strings[0].toLowerCase(java.util.Locale.ROOT)) {
         case "add" -> this.invoke(strings);
         case "remove", "del", "delete" -> this.invoke2(strings);
         case "list" -> this.invoke3();
         case "clear" -> this.invoke4();
         default -> ChatUtil.sendClientMessage("§cИспользование: §f" + this.getUsage());
      }
   }

   @Compile
   private void invoke(String[] strings) {
      if (strings.length != 2 || strings[1].isBlank()) {
         ChatUtil.sendClientMessage("§cИспользование: §f.friend add <name>");
         return;
      }

      String text = strings[1].trim();
      if (check(text)) {
         ChatUtil.sendClientMessage("§eУже в друзьях: §f" + text);
      } else {
         invoke5(text);
      }
   }

   @Compile
   private void invoke2(String[] strings) {
      if (strings.length != 2 || strings[1].isBlank()) {
         ChatUtil.sendClientMessage("§cИспользование: §f.friend remove <name>");
         return;
      }

      String text2 = strings[1].trim();
      if (!check(text2)) {
         ChatUtil.sendClientMessage("§eНе найден в друзьях: §f" + text2);
      } else {
         invoke5(text2);
      }
   }

   @Compile
   private void invoke3() {
      if (VALUES_BY_KEY.isEmpty()) {
         ChatUtil.sendClientMessage("§7Список друзей пуст.");
         return;
      }

      ChatUtil.sendClientMessage("§fДрузья (§7" + VALUES_BY_KEY.size() + "§f):");
      VALUES_BY_KEY.values().stream().sorted((left, right) -> left.text.compareToIgnoreCase(right.text)).forEach(friend ->
         ChatUtil.sendClientMessage("§f" + friend.text + " §7— " + SIMPLE_DATE_FORMAT.format(friend.date))
      );
   }

   @Compile
   private void invoke4() {
      int intValue = VALUES_BY_KEY.size();
      VALUES_BY_KEY.clear();
      invoke8();
      ChatUtil.sendClientMessage("§aСписок друзей очищен (§f" + intValue + "§a).");
   }

   public static boolean check(String string) {
      return string == null ? false : VALUES_BY_KEY.containsKey(string.toLowerCase());
   }

   public static List<String> resolve() {
      return VALUES_BY_KEY.values().stream().map(friendCommandState -> friendCommandState.text).toList();
   }

   public static void invoke5(String string) {
      String text3 = string.toLowerCase();
      MinecraftClient client = MinecraftClient.getInstance();
      if (client.getSession() != null && string.equalsIgnoreCase(client.getSession().getUsername())) {
         ChatUtil.sendClientMessage("§cНельзя добавить самого себя.");
      } else {
         if (VALUES_BY_KEY.containsKey(text3)) {
            VALUES_BY_KEY.remove(text3);
            invoke8();
            SoundUtils.invoke2("friendremove", 100.0F, false);
            ChatUtil.sendClientMessage("§cУдалён из друзей: §f" + string);
         } else {
            FriendCommand.FriendCommandState friendCommandState2 = new FriendCommand.FriendCommandState(string, new Date());
            VALUES_BY_KEY.put(text3, friendCommandState2);
            invoke8();
            SoundUtils.invoke2("friendadd", 100.0F, false);
            ChatUtil.sendClientMessage("§aДобавлен в друзья: §f" + string + " §7(" + SIMPLE_DATE_FORMAT.format(friendCommandState2.date) + ")");
         }
      }
   }

   @Compile
   private void invoke6() {
      if (!FILE.isFile()) {
         return;
      }

      try (java.io.Reader reader = Files.newBufferedReader(FILE.toPath(), StandardCharsets.UTF_8)) {
         Map<String, FriendCommand.FriendCommandState> valuesByKey = GSON.fromJson(reader, new TypeToken<Map<String, FriendCommand.FriendCommandState>>() {}.getType());
         VALUES_BY_KEY.clear();
         if (valuesByKey != null) {
            valuesByKey.forEach((name, friend) -> {
               if (friend != null && friend.text != null) {
                  VALUES_BY_KEY.put(friend.text.toLowerCase(java.util.Locale.ROOT), friend);
               }
            });
         }
      } catch (Exception exception) {
         System.out.println("[FriendCommand] Failed to load friends: " + exception.getMessage());
      }
   }

   @Compile
   private void invoke7() { this.invoke3(); }

   @Compile
   private static void invoke8() {
      try {
         File file = FILE.getParentFile();
         if (file != null) {
            Files.createDirectories(file.toPath());
         }
         try (java.io.Writer writer = Files.newBufferedWriter(FILE.toPath(), StandardCharsets.UTF_8)) {
            GSON.toJson(VALUES_BY_KEY, writer);
         }
      } catch (Exception exception2) {
         System.out.println("[FriendCommand] Failed to save friends: " + exception2.getMessage());
      }
   }

   static {
      Loader.initialize();
   }

   static class FriendCommandState {
      String text;
      Date date;

      FriendCommandState(String string, Date date) {
         this.text = string;
         this.date = date;
      }
   }
}
