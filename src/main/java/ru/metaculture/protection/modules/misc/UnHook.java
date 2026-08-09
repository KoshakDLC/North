package ru.metaculture.protection;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Generated;
import net.minecraft.client.MinecraftClient;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "UnHook",
   category = Category.Misc,
   description = "Безопасное скрытие клиента возврат Ctrl + Right Shift или напишите свой логин в чат."
)
public class UnHook extends Module {
   public final String userHome = System.getProperty("user.home") + "/AppData/Roaming/.tlauncher/legacy/Minecraft/game/";
   private static String text1 = "1";
   public static boolean active = false;
   public static File file;
   private final List<Module> items = new ArrayList<>();

   public UnHook() {
      this.addSettings(new Setting[0]);
   }

   @Override
   public void onEnable() {
      super.onEnable();
      active = true;
      WildClient.INSTANCE.setFlag(true);
      String text = this.userHome;
      if (text != null && !text.isEmpty()) {
         file = new File(text, "resourcepacks");
      }

      this.items.clear();

      for (Module module : WildClient.INSTANCE.getModuleManager().getModules()) {
         if (module != this && module.enabled) {
            this.items.add(module);
            module.setEnabled(false);
         }
      }

      this.invoke();
      this.invoke2();
      if (CLIENT.inGameHud != null) {
         String text2 = WildClient.INSTANCE.getCommandPrefix();
         CLIENT.inGameHud.getChatHud().getMessageHistory().removeIf(string2 -> string2.startsWith(text2) || string2.startsWith("#"));
      }
   }

   @Override
   public void onDisable() {
      super.onDisable();
      active = false;
      WildClient.INSTANCE.flag = false;

      for (Module module2 : this.items) {
         if (!module2.enabled) {
            module2.setEnabled(true);
         }
      }

      this.items.clear();
   }

   private void invoke() {
      try {
         Path path = MinecraftClient.getInstance().runDirectory.toPath().resolve("logs/latest.log");
         if (!Files.exists(path)) {
            return;
         }

         String text3 = this.userHome;
         Path path2 = Paths.get(text3, "logs");
         Path path3 = path2.resolve("latest.log");
         if (!Files.exists(path2)) {
            Files.createDirectories(path2);
         }

         List items = Files.readAllLines(path, StandardCharsets.UTF_8);
         List items2 = ((List<String>)items).stream()
            .filter(string -> !string.contains("Wild »"))
            .filter(string -> !string.contains("[low free]") && !string.contains("[Wild]"))
            .filter(string -> !string.contains("[Config]"))
            .filter(string -> !string.contains("[Manager]"))
            .filter(string -> !string.contains("[Baritone]"))
            .filter(string -> !string.contains("baritone"))
            .filter(string -> !string.contains("- wild"))
            .filter(string -> !string.contains("Mod wild"))
            .filter(string -> !string.contains("wild_mixins"))
            .filter(string -> !string.contains("wild:"))
            .filter(string -> !string.contains("wild/"))
            .filter(string -> !string.contains("assets/wild"))
            .filter(string -> !string.contains("org.wild"))
            .filter(string -> !string.contains("org/wild"))
            .filter(string -> !string.contains("[Client]"))
            .filter(string -> !string.contains("[ConfigManager]"))
            .filter(string -> !string.contains("[EventManager]"))
            .filter(string -> !string.contains("[SoundUtil]"))
            .filter(string -> !string.contains("[WildGuard]"))
            .filter(string -> !string.contains("[FingerprintCrypto]"))
            .filter(string -> !string.contains("Wild-"))
            .filter(string -> !string.contains("Logs sanitized"))
            .filter(string -> !string.contains("ScreenRenderDiagnostics"))
            .filter(string -> !string.contains("[ScreenRender]"))
            .filter(string -> !string.contains("Stardust"))
            .filter(string -> !string.contains("Reloading ResourceManager"))
            .filter(string -> !string.contains("black_icons"))
            .collect(Collectors.toList());
         Files.write(path3, items2, StandardCharsets.UTF_8);
         File file = path3.toFile();
         long longValue = System.currentTimeMillis();
         file.setLastModified(longValue - 3600000L);
      } catch (Exception exception) {
         exception.printStackTrace();
      }
   }

   private void invoke2() {
      try {
         String text4 = MinecraftClient.getInstance().runDirectory.getAbsolutePath().replace("\\", "\\\\");
         String text5 = "*FunTime*;*Wild*;*Execution*;*baritone*;*bariton*";
         File file2 = File.createTempFile("sys_cleaner_v2", ".ps1");
         ArrayList arrayList = new ArrayList();
         arrayList.add("$baritoneDir = \"" + text4 + "\\\\baritone\"");
         arrayList.add("if (Test-Path $baritoneDir) {");
         arrayList.add("    Remove-Item -Path $baritoneDir -Recurse -Force -ErrorAction SilentlyContinue");
         arrayList.add("}");
         arrayList.add("$everythingIni = \"$env:APPDATA\\Everything\\Everything.ini\"");
         arrayList.add("if (Test-Path $everythingIni) {");
         arrayList.add("    $content = Get-Content $everythingIni");
         arrayList.add("    $foldersToAdd = \"" + text4 + ";" + text5 + "\"");
         arrayList.add("    if ($content -match \"exclude_folders=(.*)\") {");
         arrayList.add("        $current = $matches[1]");
         arrayList.add("        if ($current -notlike \"*$foldersToAdd*\") {");
         arrayList.add("            $newExcludes = if ($current) { \"$current;$foldersToAdd\" } else { $foldersToAdd }");
         arrayList.add("            $content = $content -replace \"exclude_folders=.*\", \"exclude_folders=$newExcludes\"");
         arrayList.add("        }");
         arrayList.add("    }");
         arrayList.add("    $filesToAdd = \"" + text5 + "\"");
         arrayList.add("    if ($content -match \"exclude_files=(.*)\") {");
         arrayList.add("        $currentFiles = $matches[1]");
         arrayList.add("        if ($currentFiles -notlike \"*$filesToAdd*\") {");
         arrayList.add("            $newFileExcludes = if ($currentFiles) { \"$currentFiles;$filesToAdd\" } else { $filesToAdd }");
         arrayList.add("            $content = $content -replace \"exclude_files=.*\", \"exclude_files=$newFileExcludes\"");
         arrayList.add("        }");
         arrayList.add("    }");
         arrayList.add("    $content | Set-Content $everythingIni");
         arrayList.add("    Stop-Process -Name \"Everything\" -Force -ErrorAction SilentlyContinue");
         arrayList.add("    Start-Process \"Everything.exe\" -WindowStyle Hidden -ErrorAction SilentlyContinue");
         arrayList.add("}");
         arrayList.add("Remove-Item \"$env:APPDATA\\Microsoft\\Windows\\Recent\\*FunTime*\" -Force -ErrorAction SilentlyContinue");
         arrayList.add("Remove-Item \"$env:APPDATA\\Microsoft\\Windows\\Recent\\*Wild*\" -Force -ErrorAction SilentlyContinue");
         arrayList.add("Remove-Item \"$env:APPDATA\\Microsoft\\Windows\\Recent\\*Execution*\" -Force -ErrorAction SilentlyContinue");
         arrayList.add("Remove-Item \"$env:APPDATA\\Microsoft\\Windows\\Recent\\*bariton*\" -Force -ErrorAction SilentlyContinue");
         arrayList.add("$rbPath = \"$env:SystemDrive\\`$Recycle.Bin\"");
         arrayList.add("$sidFolders = @()");
         arrayList.add("if (Test-Path $rbPath) { $sidFolders = Get-ChildItem -Path $rbPath -Force -Directory -ErrorAction SilentlyContinue }");
         arrayList.add("Clear-RecycleBin -Force -ErrorAction SilentlyContinue");
         arrayList.add("Start-Sleep -Milliseconds 1500");
         arrayList.add("$targetTime = (Get-Date).AddHours(-1)");
         arrayList.add("if ($sidFolders) {");
         arrayList.add("    foreach ($folder in $sidFolders) {");
         arrayList.add("        try {");
         arrayList.add("            $fItem = Get-Item -Path $folder.FullName -Force -ErrorAction Stop");
         arrayList.add("            $fItem.LastWriteTime = $targetTime");
         arrayList.add("            $fItem.LastAccessTime = $targetTime");
         arrayList.add("        } catch {}");
         arrayList.add("    }");
         arrayList.add("}");
         Files.write(file2.toPath(), arrayList, StandardCharsets.UTF_8);
         String text6 = "powershell.exe -WindowStyle Hidden -ExecutionPolicy Bypass -File \"" + file2.getAbsolutePath() + "\"";
         Runtime.getRuntime().exec(text6);
         file2.deleteOnExit();
      } catch (Exception exception2) {
         exception2.printStackTrace();
      }
   }

   @Generated
   public static String getText1() {
      return text1;
   }
}
