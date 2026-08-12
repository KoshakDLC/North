package ru.metaculture.protection;

import java.util.List;
import java.util.Locale;
import ru.metaculture.sdk.Compile;
import ru.metaculture.sdk.Loader;

public class EndFarmCommand extends Command {
   private static EndFarmCommand instance;
   private String stashAnarchy = "";
   private String farmAnarchy = "";
   private String endWarp = "";

   public EndFarmCommand() {
      super("end", "Настройки AutoEnd FunTime", ".end <stash/farm/warp/info> [значение]");
      instance = this;
      this.addCompletionProvider("stash", List::of);
      this.addCompletionProvider("farm", List::of);
      this.addCompletionProvider("an", List::of);
      this.addCompletionProvider("warp", List::of);
      this.addCompletionProvider("info", List::of);
      this.addCompletionProvider("help", List::of);
      this.addCompletionProvider("clear", List::of);
   }

   public static String getStashAnarchy() {
      return instance == null ? "" : normalizeAnarchy(instance.stashAnarchy);
   }

   public static String getFarmAnarchy() {
      return instance == null ? "" : normalizeAnarchy(instance.farmAnarchy);
   }

   public static String getEndWarp() {
      if (instance == null || instance.endWarp == null) {
         return "";
      }

      return instance.endWarp.trim().toLowerCase(Locale.ROOT);
   }

   @Compile
   @Override
   public void execute(String[] strings) {
      String action = strings.length == 0 ? "info" : strings[0].toLowerCase(Locale.ROOT);
      switch (action) {
         case "info", "help" -> {
            AutoEnd module = getModule();
            String stash = firstNonEmpty(getStashAnarchy(), module == null ? "" : normalizeAnarchy(module.anarhiyaSklada.getValue()), "312");
            String farm = firstNonEmpty(getFarmAnarchy(), module == null ? "" : normalizeAnarchy(module.anarhiyaFarma.getValue()), "103");
            String warp = firstNonEmpty(getEndWarp(), module == null ? "" : safeWarp(module.warpEnda.getValue()), "endeajavar");
            ChatUtil.sendClientMessage("§d[AutoEnd] §fЦикл FunTime:");
            ChatUtil.sendClientMessage("§71) /an" + stash + " §7→ взять инвиз (не пить)");
            ChatUtil.sendClientMessage("§72) /an" + farm + " §7→ если заполнена — повторять");
            ChatUtil.sendClientMessage("§73) выпить инвиз → /warp " + warp);
            ChatUtil.sendClientMessage("§74) фарм → после PvP на стеш → «Ресурсы» → снова");
            ChatUtil.sendClientMessage("§7Команды: §f.end stash <n> §7| §f.end farm <n> §7| §f.end warp <имя>");
         }
         case "stash", "склад" -> {
            if (strings.length < 2) {
               ChatUtil.sendClientMessage("§cИспользование: §f.end stash <номер>");
               return;
            }

            String stash = normalizeAnarchy(strings[1]);
            if (stash.isEmpty()) {
               ChatUtil.sendClientMessage("§cУкажи номер анархии склада");
               return;
            }

            this.stashAnarchy = stash;
            this.syncModule();
            ChatUtil.sendClientMessage("§a[AutoEnd] Анархия склада: §f/an" + stash);
         }
         case "farm", "an", "anarchy" -> {
            if (strings.length < 2) {
               ChatUtil.sendClientMessage("§cИспользование: §f.end farm <номер>");
               return;
            }

            String farm = normalizeAnarchy(strings[1]);
            if (farm.isEmpty()) {
               ChatUtil.sendClientMessage("§cУкажи номер анархии фарма");
               return;
            }

            this.farmAnarchy = farm;
            this.syncModule();
            ChatUtil.sendClientMessage("§a[AutoEnd] Анархия фарма: §f/an" + farm);
         }
         case "warp" -> {
            if (strings.length < 2) {
               ChatUtil.sendClientMessage("§cИспользование: §f.end warp <имя>");
               return;
            }

            String warp = strings[1].trim().toLowerCase(Locale.ROOT);
            if (warp.isEmpty()) {
               ChatUtil.sendClientMessage("§cУкажи имя варпа");
               return;
            }

            this.endWarp = warp;
            this.syncModule();
            ChatUtil.sendClientMessage("§a[AutoEnd] Варп энда: §f/warp " + warp);
         }
         case "clear" -> {
            this.stashAnarchy = "";
            this.farmAnarchy = "";
            this.endWarp = "";
            ChatUtil.sendClientMessage("§a[AutoEnd] Командные оверрайды очищены (остаются настройки модуля).");
         }
         default -> ChatUtil.sendClientMessage("§cИспользование: §f.end <stash/farm/warp/info> [значение]");
      }
   }

   private void syncModule() {
      AutoEnd module = getModule();
      if (module == null) {
         return;
      }

      if (!getStashAnarchy().isEmpty()) {
         module.anarhiyaSklada.value = getStashAnarchy();
      }

      if (!getFarmAnarchy().isEmpty()) {
         module.anarhiyaFarma.value = getFarmAnarchy();
      }

      if (!getEndWarp().isEmpty()) {
         module.warpEnda.value = getEndWarp();
      }
   }

   private static AutoEnd getModule() {
      if (!WildClient.isInitialized() || WildClient.INSTANCE.moduleManager == null) {
         return null;
      }

      return WildClient.INSTANCE.moduleManager.getModule(AutoEnd.class);
   }

   private static String firstNonEmpty(String... values) {
      for (String value : values) {
         if (value != null && !value.isEmpty()) {
            return value;
         }
      }

      return "";
   }

   private static String safeWarp(String value) {
      return value == null ? "" : value.trim().toLowerCase(Locale.ROOT);
   }

   private static String normalizeAnarchy(String value) {
      return value == null ? "" : value.replaceAll("[^0-9]", "").trim();
   }

   static {
      Loader.initialize();
   }
}
