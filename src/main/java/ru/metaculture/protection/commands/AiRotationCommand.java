package ru.metaculture.protection;

import java.util.List;
import java.util.Locale;
import ru.metaculture.sdk.Compile;
import ru.metaculture.sdk.Loader;

public final class AiRotationCommand extends Command {
   public AiRotationCommand() {
      super("ai", "Запись, обучение и воспроизведение AI-ротации", ".ai <train|learn|run|stop|log|lab|profile|list>");
      this.addCompletionProvider("train", List::of);
      this.addCompletionProvider("learn", List::of);
      this.addCompletionProvider("stop", List::of);
      this.addCompletionProvider("run", List::of);
      this.addCompletionProvider("log", List::of);
      this.addCompletionProvider("lab", List::of);
      this.addCompletionProvider("profile", List::of);
      this.addCompletionProvider("list", List::of);
   }

   public static void invoke() {
   }

   @Override
   public List<String> getSuggestions(String[] strings) {
      if (strings.length == 2) {
         String text = strings[1].toLowerCase(Locale.ROOT);
         return List.of("train", "learn", "run", "stop", "log", "lab", "profile", "list").stream().filter(string2 -> string2.startsWith(text)).toList();
      } else {
         if (strings.length == 3) {
            String text2 = strings[1].toLowerCase(Locale.ROOT);
            if (text2.equals("profile") || text2.equals("run") || text2.equals("train") || text2.equals("learn")) {
               String text3 = strings[2].toLowerCase(Locale.ROOT);
               return AiRotationTrainer.resolve27().stream().filter(string2 -> string2.toLowerCase(Locale.ROOT).startsWith(text3)).toList();
            }
         }

         return List.of();
      }
   }

   @Compile
   @Override
   public void execute(String[] strings) {
      if (strings.length == 0) {
         ChatUtil.sendClientMessage("§cИспользование: §f" + this.getUsage());
         return;
      }

      String text4 = strings[0].toLowerCase(Locale.ROOT);
      if (strings.length >= 2 && (text4.equals("train") || text4.equals("learn") || text4.equals("run"))) {
         ChatUtil.sendAiMessage(AiRotationTrainer.resolve26(strings[1]));
      }

      String text5;
      switch (text4) {
         case "train" -> text5 = AiRotationTrainer.resolve();
         case "learn" -> text5 = AiRotationTrainer.resolve4();
         case "run" -> {
            text5 = AiRotationTrainer.resolve3();
            this.invoke2();
         }
         case "stop" -> text5 = AiRotationTrainer.resolve2();
         case "profile" -> text5 = strings.length >= 2
            ? AiRotationTrainer.resolve26(strings[1])
            : "Активный профиль: " + AiRotationTrainer.getDefaultValue();
         case "list" -> text5 = AiRotationTrainer.resolve28();
         case "log" -> text5 = "AI log: " + AiRotationTrainer.resolve10().toAbsolutePath();
         case "lab" -> {
            if (a_ != null) {
               a_.setScreen(new AiLabScreen());
            }
            text5 = "AI Lab открыт.";
         }
         default -> text5 = "Использование: " + this.getUsage();
      }

      ChatUtil.sendAiMessage(text5);
   }

   @EventHandler
   public void onAttackEntity(AttackEntityEvent attackEntityEvent) {
      AiRotationTrainer.invoke(attackEntityEvent);
   }

   @EventHandler
   public void onClientTick(ClientTickEvent clientTickEvent) {
      AiRotationTrainer.invoke2();
   }

   private void invoke2() {
      if (WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null && AttackAura.rezhimRotatsii.options.contains("AI")) {
         AttackAura.rezhimRotatsii.value = "AI";
         AttackAura.rezhimRotatsii.selectedIndex = AttackAura.rezhimRotatsii.options.indexOf("AI");
         AttackAura attackAura = WildClient.INSTANCE.moduleManager.getModule(AttackAura.class);
         if (attackAura != null && !attackAura.enabled) {
            attackAura.setEnabled(true);
         }
      } else {
         ChatUtil.sendClientMessage("Режим AI недоступен для текущего профиля.");
         AiRotationTrainer.resolve2();
      }
   }

   static {
      Loader.initialize();
   }
}
