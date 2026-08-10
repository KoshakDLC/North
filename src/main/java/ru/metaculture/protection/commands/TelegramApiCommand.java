package ru.metaculture.protection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.util.List;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import ru.metaculture.sdk.Compile;
import ru.metaculture.sdk.Loader;

public class TelegramApiCommand extends Command {
   private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
   private final File file = new File(WildClient.INSTANCE.file, "telegram.cfg");

   public TelegramApiCommand() {
      super("tapi", "телеграм API для отправки уведомлений в ТГ", ".tapi <token/chatid/test/clear/info/help/dir/load>");
      this.addCompletionProvider("token", List::of);
      this.addCompletionProvider("chatid", List::of);
      this.addCompletionProvider("test", List::of);
      this.addCompletionProvider("clear", List::of);
      this.addCompletionProvider("info", List::of);
      this.addCompletionProvider("help", List::of);
      this.addCompletionProvider("dir", List::of);
      this.addCompletionProvider("load", List::of);
      this.invoke8();
   }

   @Compile
   @Override
   public void execute(String[] strings) {
      if (strings.length == 0) {
         this.invoke6();
         return;
      }
      switch (strings[0].toLowerCase(java.util.Locale.ROOT)) {
         case "token" -> this.invoke(strings);
         case "chatid" -> this.invoke2(strings);
         case "test" -> this.invoke3();
         case "clear" -> this.invoke4();
         case "info" -> this.invoke5();
         case "help" -> this.invoke6();
         case "dir" -> this.invoke7();
         case "load" -> this.invoke8();
         default -> this.invoke6();
      }
   }

   @Compile
   private void invoke(String[] strings) {
      if (strings.length != 2 || strings[1].isBlank()) {
         ChatUtil.sendClientMessage("§cИспользование: §f.tapi token <bot-token>");
         return;
      }
      TelegramApiCommand.TelegramApiCommandState telegramApiCommandState = this.resolve();
      if (telegramApiCommandState == null) telegramApiCommandState = new TelegramApiCommand.TelegramApiCommandState();
      telegramApiCommandState.text = CryptoUtils.resolve(strings[1].trim(), CryptoUtils.GUHDVBZDE4XQ5F4BXKPVXV70VY44WSUH1O6S2NZ2F9U1W9Y1VVG1MXQCUFBJM2DDUCD8NVTM0L4O1T1NN8FWWAVYLCHNNCDAGIV9UR8FPLXXF8IMATLWY4MENYTLHPB3);
      try { this.invoke9(telegramApiCommandState); this.invoke10(telegramApiCommandState); ChatUtil.sendClientMessage("§aTelegram bot token сохранён."); }
      catch (Exception exception) { ChatUtil.sendClientMessage("§cНе удалось сохранить Telegram token."); }
   }

   @Compile
   private void invoke2(String[] strings) {
      if (strings.length != 2 || strings[1].isBlank()) {
         ChatUtil.sendClientMessage("§cИспользование: §f.tapi chatid <chat-id>");
         return;
      }
      TelegramApiCommand.TelegramApiCommandState telegramApiCommandState2 = this.resolve();
      if (telegramApiCommandState2 == null) telegramApiCommandState2 = new TelegramApiCommand.TelegramApiCommandState();
      telegramApiCommandState2.text2 = CryptoUtils.resolve(strings[1].trim(), CryptoUtils.GUHDVBZDE4XQ5F4BXKPVXV70VY44WSUH1O6S2NZ2F9U1W9Y1VVG1MXQCUFBJM2DDUCD8NVTM0L4O1T1NN8FWWAVYLCHNNCDAGIV9UR8FPLXXF8IMATLWY4MENYTLHPB3);
      try { this.invoke9(telegramApiCommandState2); this.invoke10(telegramApiCommandState2); ChatUtil.sendClientMessage("§aTelegram chat id сохранён."); }
      catch (Exception exception2) { ChatUtil.sendClientMessage("§cНе удалось сохранить Telegram chat id."); }
   }

   @Compile
   private void invoke3() {
      if (!TelegramApi.check()) {
         ChatUtil.sendClientMessage("§cTelegram API не настроен. Задайте token и chatid.");
         return;
      }
      Thread thread = new Thread(() -> TelegramApi.invoke2("North: тестовое уведомление"), "north-Telegram-Test");
      thread.setDaemon(true);
      thread.start();
      ChatUtil.sendClientMessage("§aТестовое Telegram-уведомление отправляется.");
   }

   @Compile
   private void invoke4() {
      try { Files.deleteIfExists(this.file.toPath()); } catch (Exception exception3) {}
      TelegramApi.invoke("", "");
      ChatUtil.sendClientMessage("§aНастройки Telegram API очищены.");
   }

   @Compile
   private void invoke5() {
      TelegramApiCommand.TelegramApiCommandState telegramApiCommandState3 = this.resolve();
      boolean flag = telegramApiCommandState3 != null && telegramApiCommandState3.text != null && !telegramApiCommandState3.text.isEmpty();
      boolean flag2 = telegramApiCommandState3 != null && telegramApiCommandState3.text2 != null && !telegramApiCommandState3.text2.isEmpty();
      ChatUtil.sendClientMessage("§fTelegram API: token §7" + (flag ? "задан" : "не задан") + "§f, chat id §7" + (flag2 ? "задан" : "не задан"));
   }

   @Compile
   private void invoke6() {
      ChatUtil.sendClientMessage("§cИспользование: §f" + this.getUsage());
      ChatUtil.sendClientMessage("§7.tapi token <token> | .tapi chatid <id> | .tapi test | .tapi clear | .tapi info");
   }

   @Compile
   private void invoke7() {
      File file = this.file.getParentFile();
      try { java.awt.Desktop.getDesktop().open(file); } catch (Exception exception4) {}
      ChatUtil.sendClientMessage("§7Telegram config: §f" + this.file.getAbsolutePath());
   }

   @Compile
   private void invoke8() {
      TelegramApiCommand.TelegramApiCommandState telegramApiCommandState4 = this.resolve();
      if (telegramApiCommandState4 == null) {
         ChatUtil.sendClientMessage("§eTelegram config не найден.");
      } else {
         this.invoke10(telegramApiCommandState4);
         ChatUtil.sendClientMessage("§aTelegram config загружен.");
      }
   }

   @Compile
   private TelegramApiCommand.TelegramApiCommandState resolve() {
      if (!this.file.isFile()) return null;
      try (java.io.Reader reader = Files.newBufferedReader(this.file.toPath(), StandardCharsets.UTF_8)) {
         return this.gson.fromJson(reader, TelegramApiCommand.TelegramApiCommandState.class);
      } catch (Exception exception5) { return null; }
   }

   @Compile
   private void invoke9(TelegramApiCommand.TelegramApiCommandState telegramApiCommandState5) throws Exception {
      File file2 = this.file.getParentFile();
      if (file2 != null) Files.createDirectories(file2.toPath());
      try (java.io.Writer writer = Files.newBufferedWriter(this.file.toPath(), StandardCharsets.UTF_8)) {
         this.gson.toJson(telegramApiCommandState5, writer);
      }
   }

   private void invoke10(TelegramApiCommand.TelegramApiCommandState telegramApiCommandState6) {
      try {
         String text = "";
         String text2 = "";
         if (telegramApiCommandState6.text != null && !telegramApiCommandState6.text.isEmpty()) {
            text = CryptoUtils.resolve2(
               telegramApiCommandState6.text,
               "gUhDvBzdE4xq5f4BxkPvxv70VY44WsuH1O6s2nZ2F9U1w9y1VVG1mXQcUfbJM2DDUCd8NvtM0L4O1t1nn8FwwAVYlChNncdagiv9UR8FpLXXF8iMAtlWY4mEnYtLHPB3"
            );
         }

         if (telegramApiCommandState6.text2 != null && !telegramApiCommandState6.text2.isEmpty()) {
            text2 = CryptoUtils.resolve2(
               telegramApiCommandState6.text2,
               "gUhDvBzdE4xq5f4BxkPvxv70VY44WsuH1O6s2nZ2F9U1w9y1VVG1mXQcUfbJM2DDUCd8NvtM0L4O1t1nn8FwwAVYlChNncdagiv9UR8FpLXXF8iMAtlWY4mEnYtLHPB3"
            );
         }

         TelegramApi.invoke(text, text2);
      } catch (Exception exception6) {
         ChatUtil.sendClientMessage("§cОшибка расшифровки данных.");
         exception6.printStackTrace();
      }
   }

   static {
      Loader.initialize();
   }

   static class TelegramApiCommandState {
      String text;
      String text2;
   }
}
