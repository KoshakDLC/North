package ru.metaculture.protection;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import ru.metaculture.sdk.Compile;
import ru.metaculture.sdk.Loader;

public final class ConfigManager extends ConfigStore<Config> {
   public static final File FILE = resolve();
   private static final ArrayList<Config> ARRAY_LIST = new ArrayList<>();
   private static final long TIMESTAMP = 350L;
   private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(runnable -> {
      Thread thread = new Thread(runnable, "Wild-Config-Autosave");
      thread.setDaemon(true);
      return thread;
   });
   private final Object object = new Object();
   private ScheduledFuture<?> scheduledFuture;
   private boolean flag;
   private boolean flag2;

   @Compile
   private static File resolve() { return new java.io.File(System.getProperty("wild.root", "C:/WildClient")); }

   public ConfigManager() {
      if (FILE != null && !FILE.exists() && !FILE.mkdirs()) {
         System.out.println("[ConfigManager] Warning: cannot create config directory at " + FILE.getAbsolutePath());
      }

      this.invoke(resolve2());
   }

   @Compile
   private static ArrayList<Config> resolve2() {
      ArrayList<Config> arrayList = new ArrayList<>();
      File[] files = FILE.listFiles((file, name) -> name.toLowerCase(java.util.Locale.ROOT).endsWith(".json"));
      if (files == null) {
         return arrayList;
      }

      for (File file2 : files) {
         try {
            JsonElement jsonElement = JsonParser.parseString(Files.readString(file2.toPath(), StandardCharsets.UTF_8));
            if (jsonElement != null && jsonElement.isJsonObject() && jsonElement.getAsJsonObject().has("Features")) {
               String text = file2.getName();
               arrayList.add(new Config(text.substring(0, text.length() - 5)));
            }
         } catch (Throwable exception) {
            System.out.println("[ConfigManager] Skipping invalid config '" + file2.getName() + "': " + exception.getMessage());
         }
      }

      arrayList.sort(Comparator.comparing(Config::getText, String.CASE_INSENSITIVE_ORDER));
      return arrayList;
   }

   @Compile
   public static ArrayList<Config> resolve3() { return new ArrayList<>(ARRAY_LIST); }

   @Compile
   public void invoke() {
      ArrayList<Config> arrayList2 = resolve2();
      ARRAY_LIST.clear();
      ARRAY_LIST.addAll(arrayList2);
      this.invoke(arrayList2);
   }

   @Compile
   public synchronized boolean check(String string) {
      if (!check6(string)) {
         return false;
      }
      Config config2 = this.resolve4(string);
      if (config2 == null || config2.getFile() == null || !config2.getFile().isFile()) {
         return false;
      }

      try {
         JsonElement jsonElement2 = JsonParser.parseString(Files.readString(config2.getFile().toPath(), StandardCharsets.UTF_8));
         if (jsonElement2 == null || !jsonElement2.isJsonObject()) {
            return false;
         }

         config2.invoke(jsonElement2.getAsJsonObject());
         if (!this.getItems().contains(config2)) {
            this.getItems().add(config2);
         }
         if (ARRAY_LIST.stream().noneMatch(config -> config.getText().equalsIgnoreCase(config2.getText()))) {
            ARRAY_LIST.add(config2);
         }
         return true;
      } catch (Throwable exception2) {
         System.out.println("[ConfigManager] Failed to load '" + string + "': " + exception2.getMessage());
         return false;
      }
   }

   @Compile
   public synchronized boolean check2(String string, JsonObject jsonObject) {
      if (!check6(string) || jsonObject == null) {
         return false;
      }

      try {
         Config config3 = this.resolve4(string);
         if (config3 == null) {
            config3 = new Config(string);
            this.getItems().add(config3);
            ARRAY_LIST.add(config3);
         }
         config3.invoke(jsonObject);
         return true;
      } catch (Throwable exception3) {
         System.out.println("[ConfigManager] Failed to apply '" + string + "': " + exception3.getMessage());
         return false;
      }
   }

   public synchronized boolean check3(String string) {
      if (!check6(string)) {
         return false;
      } else {
         Config config4;
         if ((config4 = this.resolve4(string)) == null) {
            try {
               Config config5 = config4 = new Config(string);
               this.getItems().add(config5);
            } catch (Throwable exception4) {
               System.out.println("[ConfigManager] Cannot create config '" + string + "': " + exception4.getMessage());
               return false;
            }
         }

          File file3 = config4.getFile();
         if (file3 == null) {
            return false;
         } else {
            File file4 = file3.getParentFile();
            if (file4 != null && !file4.exists() && !file4.mkdirs()) {
               System.out.println("[ConfigManager] Cannot create directory for '" + string + "'");
               return false;
            } else {
               String text2;
               try {
                  text2 = new GsonBuilder().setPrettyPrinting().create().toJson(config4.resolve());
               } catch (Throwable exception5) {
                  System.out.println("[ConfigManager] Failed to serialize config '" + string + "': " + exception5.getMessage());
                  return false;
               }

               try {
                  boolean flag;
                  try (BufferedWriter bufferedWriter = Files.newBufferedWriter(file3.toPath(), StandardCharsets.UTF_8)) {
                     bufferedWriter.write(text2);
                     flag = true;
                  }

                  if (ARRAY_LIST.stream().noneMatch(config -> config.getText().equalsIgnoreCase(string))) {
                     ARRAY_LIST.add(config4);
                     ARRAY_LIST.sort(Comparator.comparing(Config::getText, String.CASE_INSENSITIVE_ORDER));
                  }
                  return flag;
               } catch (IOException ioException) {
                  System.out.println("[ConfigManager] I/O error saving '" + string + "': " + ioException.getMessage());
                  return false;
               }
            }
         }
      }
   }

   public Config resolve4(String string) {
      if (!check6(string)) {
         return null;
      } else {
         for (Config config6 : this.getItems()) {
            if (config6.getText().equalsIgnoreCase(string)) {
               return config6;
            }
         }

         return new File(FILE, string + ".json").exists() ? new Config(string) : null;
      }
   }

   @Compile
   public synchronized boolean check4(String string) {
      if (!check6(string)) {
         return false;
      }
      Config config7 = this.resolve4(string);
      if (config7 == null || "default".equalsIgnoreCase(config7.getText())) {
         return false;
      }

      boolean flag2;
      try {
         flag2 = Files.deleteIfExists(config7.getFile().toPath());
      } catch (IOException ioException2) {
         return false;
      }

      this.getItems().removeIf(config -> config.getText().equalsIgnoreCase(string));
      ARRAY_LIST.removeIf(config -> config.getText().equalsIgnoreCase(string));
      return flag2;
   }

   public void scheduleSave() {
      if (WildClient.INSTANCE != null && WildClient.INSTANCE.configManager != null) {
         Object object = this.object;
         synchronized (this.object){}

         try {
            if (this.flag2) {
            } else {
               this.flag = true;
               if (this.scheduledFuture != null) {
                  this.scheduledFuture.cancel(false);
               }

               this.scheduledFuture = this.scheduledExecutorService.schedule(this::invoke3, 350L, TimeUnit.MILLISECONDS);
            }
         } finally {
         }
      }
   }

   public void invoke2() {
      boolean flag3;
      synchronized (this.object) {
         if (this.flag2) {
            return;
         }

         this.flag2 = true;
         flag3 = this.flag;
         this.flag = false;
         if (this.scheduledFuture != null) {
            this.scheduledFuture.cancel(false);
            this.scheduledFuture = null;
         }
      }

      if (flag3) {
         this.check3("default");
      }

      this.scheduledExecutorService.shutdown();

      try {
         this.scheduledExecutorService.awaitTermination(1L, TimeUnit.SECONDS);
      } catch (InterruptedException interruptedException) {
         Thread.currentThread().interrupt();
      }
   }

   private void invoke3() {
      synchronized (this.object) {
         if (!this.flag) {
            this.scheduledFuture = null;
            return;
         }

         this.flag = false;
         this.scheduledFuture = null;
      }

      if (!this.check3("default")) {
         synchronized (this.object) {
            if (!this.flag2) {
               this.flag = true;
               this.scheduledFuture = this.scheduledExecutorService.schedule(this::invoke3, 350L, TimeUnit.MILLISECONDS);
            }
         }
      }
   }

   @Compile
   public boolean check5() { return !this.flag2 && FILE.isDirectory(); }

   private static boolean check6(String string) {
      return string != null
         && !string.isBlank()
         && string.length() <= 64
         && !string.contains("..")
         && string.matches("[\\p{L}\\p{N}._-]+");
   }

   static {
      Loader.initialize();
   }
}
