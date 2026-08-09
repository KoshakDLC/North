package ru.metaculture.protection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Locale;
import ru.metaculture.sdk.Compile;
import ru.metaculture.sdk.Loader;

public class CopperFarmCommand extends Command {
   private static CopperFarmCommand instance;
   private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
   private final File file = new File(WildClient.INSTANCE.file, "copperdange.cfg");
   private CopperFarmCommand.Config config = new CopperFarmCommand.Config();

   public CopperFarmCommand() {
      super("copper", "Анархия фарма медного данжа", ".copper <anarchy/stash/info> [номер]");
      instance = this;
      this.addCompletionProvider("anarchy", List::of);
      this.addCompletionProvider("an", List::of);
      this.addCompletionProvider("stash", List::of);
      this.addCompletionProvider("info", List::of);
      this.addCompletionProvider("clear", List::of);
      this.load();
   }

   public static CopperFarmCommand getInstance() {
      return instance;
   }

   public static String getFarmAnarchy() {
      return instance == null ? "" : normalize(instance.config.farmAnarchy);
   }

   public static String getStashAnarchy() {
      return instance == null ? "" : normalize(instance.config.stashAnarchy);
   }

   @Compile
   @Override
   public void execute(String[] strings) {
      if (strings.length == 0) {
         ChatUtil.sendClientMessage("§cИспользование: §f" + this.getUsage());
         return;
      }

      String action = strings[0].toLowerCase(Locale.ROOT);
      switch (action) {
         case "anarchy", "an", "farm" -> {
            if (strings.length < 2) {
               ChatUtil.sendClientMessage("§cИспользование: §f.copper anarchy <номер>");
               return;
            }

            String anarchy = normalize(strings[1]);
            if (anarchy.isEmpty()) {
               ChatUtil.sendClientMessage("§cУкажи номер анархии, например §f.copper anarchy 903");
               return;
            }

            this.config.farmAnarchy = anarchy;
            this.save();
            this.syncModule();
            ChatUtil.sendClientMessage("§a[Copper] Анархия фарма: §f/an" + anarchy);
         }
         case "stash", "склад" -> {
            if (strings.length < 2) {
               ChatUtil.sendClientMessage("§cИспользование: §f.copper stash <номер>");
               return;
            }

            String stash = normalize(strings[1]);
            if (stash.isEmpty()) {
               ChatUtil.sendClientMessage("§cУкажи номер анархии склада");
               return;
            }

            this.config.stashAnarchy = stash;
            this.save();
            this.syncModule();
            ChatUtil.sendClientMessage("§a[Copper] Анархия склада: §f/an" + stash);
         }
         case "info" -> ChatUtil.sendClientMessage(
            "§f[Copper] фарм: §7"
               + (getFarmAnarchy().isEmpty() ? "не задана" : "/an" + getFarmAnarchy())
               + " §f| склад: §7"
               + (getStashAnarchy().isEmpty() ? "не задана" : "/an" + getStashAnarchy())
         );
         case "clear" -> {
            this.config = new CopperFarmCommand.Config();
            this.save();
            this.syncModule();
            ChatUtil.sendClientMessage("§a[Copper] Настройки очищены.");
         }
         default -> {
            String maybeAnarchy = normalize(action);
            if (!maybeAnarchy.isEmpty() && strings.length == 1) {
               this.config.farmAnarchy = maybeAnarchy;
               this.save();
               this.syncModule();
               ChatUtil.sendClientMessage("§a[Copper] Анархия фарма: §f/an" + maybeAnarchy);
            } else {
               ChatUtil.sendClientMessage("§cИспользование: §f" + this.getUsage());
            }
         }
      }
   }

   private void syncModule() {
      if (!WildClient.isInitialized() || WildClient.INSTANCE.moduleManager == null) {
         return;
      }

      AutoCopperDange module = WildClient.INSTANCE.moduleManager.getModule(AutoCopperDange.class);
      if (module == null) {
         return;
      }

      if (!getFarmAnarchy().isEmpty()) {
         module.anarhiyaFarma.value = getFarmAnarchy();
      }

      if (!getStashAnarchy().isEmpty()) {
         module.anarhiyaSklada.value = getStashAnarchy();
      }
   }

   @Compile
   private void load() {
      if (!this.file.isFile()) {
         return;
      }

      try (java.io.Reader reader = Files.newBufferedReader(this.file.toPath(), StandardCharsets.UTF_8)) {
         CopperFarmCommand.Config loaded = this.gson.fromJson(reader, CopperFarmCommand.Config.class);
         if (loaded != null) {
            this.config = loaded;
         }
      } catch (Exception exception) {
         System.out.println("[CopperFarmCommand] Failed to load: " + exception.getMessage());
      }
   }

   @Compile
   private void save() {
      try {
         File parent = this.file.getParentFile();
         if (parent != null) {
            Files.createDirectories(parent.toPath());
         }

         try (java.io.Writer writer = Files.newBufferedWriter(this.file.toPath(), StandardCharsets.UTF_8)) {
            this.gson.toJson(this.config, writer);
         }
      } catch (Exception exception) {
         System.out.println("[CopperFarmCommand] Failed to save: " + exception.getMessage());
      }
   }

   private static String normalize(String value) {
      return value == null ? "" : value.replaceAll("[^0-9]", "").trim();
   }

   static {
      Loader.initialize();
   }

   static class Config {
      String farmAnarchy = "";
      String stashAnarchy = "";
   }
}
