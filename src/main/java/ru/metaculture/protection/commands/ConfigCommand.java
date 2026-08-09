package ru.metaculture.protection;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletionException;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import ru.metaculture.sdk.Compile;
import ru.metaculture.sdk.Loader;

public class ConfigCommand extends Command {
   private final File file;

   public ConfigCommand() {
      super("config", "Управление конфигурациями", ".config <save/load/cloudload/cloudlist/list/delete/dir/reset> <name>");
      this.file = ConfigManager.FILE;
      this.addCompletionProvider("load", this::resolve);
      this.addCompletionProvider("delete", this::resolve);
      this.addCompletionProvider("dir", List::of);
      this.addCompletionProvider("reset", List::of);
      this.addCompletionProvider("save", this::resolve);
      this.addCompletionProvider("list", List::of);
      this.addCompletionProvider("cloudload", CloudConfigService::getItems);
      this.addCompletionProvider("cloudlist", List::of);
   }

   private List<String> resolve() {
      if (!this.file.exists()) {
         return List.of();
      } else {
         File[] files = this.file.listFiles((file, string) -> string.endsWith(".cfg") || string.endsWith(".json"));
         return files == null ? List.of() : Arrays.stream(files).map(file -> {
            String var1x = file.getName();
            return var1x.substring(0, var1x.lastIndexOf(46));
         }).distinct().sorted(String.CASE_INSENSITIVE_ORDER).toList();
      }
   }

   @Compile
   @Override
   public void execute(String[] strings) {
      if (strings.length == 0 || WildClient.INSTANCE == null || WildClient.INSTANCE.configManager == null) {
         ChatUtil.sendClientMessage("§cИспользование: §f" + this.getUsage());
         return;
      }

      ConfigManager configManager = WildClient.INSTANCE.configManager;
      String text = strings[0].toLowerCase(java.util.Locale.ROOT);
      String text2 = strings.length >= 2 ? strings[1] : "default";
      switch (text) {
         case "save" -> ChatUtil.sendClientMessage(
            configManager.check3(text2) ? "§aКонфиг §f'" + text2 + "' §aсохранён." : "§cНе удалось сохранить конфиг §f'" + text2 + "'."
         );
         case "load" -> {
            if (configManager.check(text2)) {
               ChatUtil.sendClientMessage("§aКонфиг §f'" + text2 + "' §aзагружен.");
            } else {
               this.invoke4(text2, false);
            }
         }
         case "cloudload" -> this.invoke4(text2, true);
         case "list" -> {
            List<String> items = ConfigManager.resolve3().stream().map(Config::getText).sorted(String.CASE_INSENSITIVE_ORDER).toList();
            this.invoke10("Локальные конфиги", items.isEmpty() ? List.of("нет") : items);
         }
         case "cloudlist" -> {
            ChatUtil.sendClientMessage("§7Запрашиваю список Cloud Config...");
            CloudConfigService.resolve2().whenComplete((result, error) -> this.invoke11(() -> {
               if (error != null) {
                  ChatUtil.sendClientMessage("§cОшибка Cloud Config: §7" + this.resolve2(error));
               } else if (result != null && result.success()) {
                  this.invoke10("Cloud Config", result.names().isEmpty() ? List.of("нет") : result.names());
               } else {
                  ChatUtil.sendClientMessage("§cОшибка Cloud Config: §7" + (result == null ? "неизвестная ошибка" : result.error()));
               }
            }));
         }
         case "delete", "del", "remove" -> ChatUtil.sendClientMessage(
            configManager.check4(text2) ? "§aКонфиг §f'" + text2 + "' §aудалён." : "§cНе удалось удалить конфиг §f'" + text2 + "'."
         );
         case "dir" -> {
            try {
               java.awt.Desktop.getDesktop().open(this.file);
               ChatUtil.sendClientMessage("§aПапка конфигов открыта: §f" + this.file.getAbsolutePath());
            } catch (Exception exception) {
               ChatUtil.sendClientMessage("§7Папка конфигов: §f" + this.file.getAbsolutePath());
            }
         }
         case "reset" -> {
            if (WildClient.INSTANCE.moduleManager != null) {
               WildClient.INSTANCE.moduleManager.getModules().forEach(org.wild.module.api.Module::reset);
            }
            configManager.check3("default");
            ChatUtil.sendClientMessage("§aНастройки модулей сброшены.");
         }
         default -> ChatUtil.sendClientMessage("§cИспользование: §f" + this.getUsage());
      }
   }

   @Compile
   private void invoke(String[] strings) {}

   @Compile
   private void invoke2(String[] strings) {}

   @Compile
   private void invoke3(String[] strings) {}

   private void invoke4(String string, boolean bl) {
      if (bl) {
         ChatUtil.sendClientMessage("§7Принудительно загружаю Cloud Config §f'" + string + "'§7...");
      } else {
         ChatUtil.sendClientMessage("§7Локальный конфиг §f'" + string + "' §7не найден. Запрашиваю облако...");
      }

      CloudConfigService.resolve(string).whenComplete((cloudConfigServiceResult2, throwable) -> this.invoke11(() -> {
         if (throwable != null) {
            ChatUtil.sendClientMessage("§cОшибка Cloud Config: §7" + this.resolve2(throwable));
         } else {
            if (cloudConfigServiceResult2 != null && cloudConfigServiceResult2.success()) {
               ChatUtil.sendClientMessage("§aCloud Config §f'" + cloudConfigServiceResult2.name() + "' §aзагружен и сохранен.");
            } else {
               String text3 = cloudConfigServiceResult2 != null && cloudConfigServiceResult2.error() != null ? cloudConfigServiceResult2.error() : "неизвестная ошибка";
               ChatUtil.sendClientMessage("§cCloud Config §f'" + string + "' §cне загружен: §7" + text3);
            }
         }
      }));
   }

   @Compile
   private void invoke5() {}

   @Compile
   private void invoke6(String[] strings) {}

   @Compile
   private void invoke7() {}

   @Compile
   private void invoke8() {}

   @Compile
   private void invoke9() {}

   private void invoke10(String string, List<String> list) {
      MutableText mutableText = Text.literal("§f" + string + ": ");
      int intValue = UiAccentColor.compute();

      for (int intValue2 = 0; intValue2 < list.size(); intValue2++) {
         MutableText mutableText2 = Text.literal((String)list.get(intValue2)).setStyle(Style.EMPTY.withColor(TextColor.fromRgb(intValue)));
         mutableText.append(mutableText2);
         if (intValue2 < list.size() - 1) {
            mutableText.append(Text.literal("§7 | "));
         }
      }

      if (a_.player != null) {
         a_.player.sendMessage(mutableText, false);
      } else {
         ChatUtil.sendClientMessage(string + ": " + String.join(", ", list));
      }
   }

   private void invoke11(Runnable runnable) {
      if (a_ == null) {
         runnable.run();
      } else {
         a_.execute(runnable);
      }
   }

   private String resolve2(Throwable throwable) {
      Throwable exception2 = throwable;

      while (exception2 instanceof CompletionException && exception2.getCause() != null) {
         exception2 = exception2.getCause();
      }

      String text4 = exception2 == null ? null : exception2.getMessage();
      return text4 != null && !text4.isBlank() ? text4 : "неизвестная ошибка";
   }

   static {
      Loader.initialize();
   }
}
