package ru.metaculture.protection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.util.List;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import net.minecraft.util.math.BlockPos;
import ru.metaculture.sdk.Compile;
import ru.metaculture.sdk.Loader;

public class CocoaFarmCommand extends Command {
   private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
   private final File file = new File(WildClient.INSTANCE.file, "cocoafarm.cfg");
   private CocoaFarmCommand.CocoaFarmCommandState cocoaFarmCommandState = new CocoaFarmCommand.CocoaFarmCommandState();

   public CocoaFarmCommand() {
      super("cocoa", "Управление границами фермы какао", ".cocoa <pos1/pos2/clear/info>");
      this.addCompletionProvider("pos1", List::of);
      this.addCompletionProvider("pos2", List::of);
      this.addCompletionProvider("clear", List::of);
      this.addCompletionProvider("info", List::of);
      this.invoke5();
   }

   @Compile
   @Override
   public void execute(String[] strings) {
      if (strings.length != 1) {
         ChatUtil.sendClientMessage("§cИспользование: §f" + this.getUsage());
         return;
      }
      switch (strings[0].toLowerCase(java.util.Locale.ROOT)) {
         case "pos1" -> this.invoke();
         case "pos2" -> this.invoke2();
         case "clear" -> this.invoke3();
         case "info" -> this.invoke4();
         default -> ChatUtil.sendClientMessage("§cИспользование: §f" + this.getUsage());
      }
   }

   @Compile
   private void invoke() {
      if (a_.player == null) return;
      BlockPos blockPos = a_.player.getBlockPos();
      this.cocoaFarmCommandState.cocoaFarmCommandState2 = new CocoaFarmCommand.CocoaFarmCommandState2(blockPos.getX(), blockPos.getY(), blockPos.getZ());
      this.invoke6();
      ChatUtil.sendClientMessage("§a[Cocoa] pos1: §f" + this.resolve(blockPos));
   }

   @Compile
   private void invoke2() {
      if (a_.player == null) return;
      BlockPos blockPos2 = a_.player.getBlockPos();
      this.cocoaFarmCommandState.cocoaFarmCommandState22 = new CocoaFarmCommand.CocoaFarmCommandState2(blockPos2.getX(), blockPos2.getY(), blockPos2.getZ());
      this.invoke6();
      ChatUtil.sendClientMessage("§a[Cocoa] pos2: §f" + this.resolve(blockPos2));
   }

   @Compile
   private void invoke3() { this.cocoaFarmCommandState = new CocoaFarmCommand.CocoaFarmCommandState(); this.invoke6(); ChatUtil.sendClientMessage("§a[Cocoa] Границы очищены."); }

   @Compile
   private void invoke4() {
      String text = this.cocoaFarmCommandState.cocoaFarmCommandState2 == null ? "не задана" : this.resolve2(this.cocoaFarmCommandState.cocoaFarmCommandState2);
      String text2 = this.cocoaFarmCommandState.cocoaFarmCommandState22 == null ? "не задана" : this.resolve2(this.cocoaFarmCommandState.cocoaFarmCommandState22);
      ChatUtil.sendClientMessage("§f[Cocoa] pos1: §7" + text + " §f| pos2: §7" + text2);
   }

   @Compile
   private String resolve(BlockPos blockPos) { return blockPos.getX() + ", " + blockPos.getY() + ", " + blockPos.getZ(); }

   private String resolve2(CocoaFarmCommand.CocoaFarmCommandState2 pos) { return pos.intValue + ", " + pos.intValue2 + ", " + pos.intValue3; }

   @Compile
   private void invoke5() {
      if (!this.file.isFile()) return;
      try (java.io.Reader reader = Files.newBufferedReader(this.file.toPath(), StandardCharsets.UTF_8)) {
         CocoaFarmCommand.CocoaFarmCommandState cocoaFarmCommandState = this.gson.fromJson(reader, CocoaFarmCommand.CocoaFarmCommandState.class);
         if (cocoaFarmCommandState != null) this.cocoaFarmCommandState = cocoaFarmCommandState;
      } catch (Exception exception) { System.out.println("[CocoaFarmCommand] Failed to load bounds: " + exception.getMessage()); }
   }

   @Compile
   private void invoke6() {
      try {
         File file = this.file.getParentFile(); if (file != null) Files.createDirectories(file.toPath());
         try (java.io.Writer writer = Files.newBufferedWriter(this.file.toPath(), StandardCharsets.UTF_8)) { this.gson.toJson(this.cocoaFarmCommandState, writer); }
      } catch (Exception exception2) { System.out.println("[CocoaFarmCommand] Failed to save bounds: " + exception2.getMessage()); }
   }

   static {
      Loader.initialize();
   }

   static class CocoaFarmCommandState {
      CocoaFarmCommand.CocoaFarmCommandState2 cocoaFarmCommandState2;
      CocoaFarmCommand.CocoaFarmCommandState2 cocoaFarmCommandState22;
   }

   static class CocoaFarmCommandState2 {
      int intValue;
      int intValue2;
      int intValue3;

      CocoaFarmCommandState2(int i, int j, int k) {
         this.intValue = i;
         this.intValue2 = j;
         this.intValue3 = k;
      }
   }
}
