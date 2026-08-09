package ru.metaculture.protection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public final class RotationAssetLoader implements MinecraftAccessor {
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   private final List<RotationAssetLoader.RotationAssetLoaderData> items = new ArrayList<>();
   private long timestamp;
   private int intValue = -1;
   private long timestamp2 = -1L;

   public RotationLabModule.RotationLabModuleState resolve(float f, float g, boolean bl) {
      this.invoke3(false);
      if (this.items.isEmpty()) {
         return null;
      } else {
         float floatValue = (float)Math.hypot(Math.abs(f), Math.abs(g));
         float floatValue2 = floatValue <= 0.001F ? 1.0F : Math.abs(f) / floatValue;
         String text = resolve7(floatValue, floatValue2, bl);
         return this.items
            .stream()
            .min(Comparator.comparingDouble(rotationAssetLoaderData -> this.measure(rotationAssetLoaderData.pattern, floatValue, floatValue2, text)))
            .map(rotationAssetLoaderData2 -> rotationAssetLoaderData2.pattern)
            .orElse(null);
      }
   }

   public int compute() {
      this.invoke3(false);
      return this.items.size();
   }

   public String resolve2() {
      this.invoke3(false);
      return this.items.isEmpty() ? "Neuro: no assets" : "Neuro: " + this.items.size() + " patterns";
   }

   public void invoke() {
      this.invoke3(true);
   }

   public static Path resolve3() {
      return WildClient.INSTANCE != null && WildClient.INSTANCE.file != null
         ? WildClient.INSTANCE.file.toPath().resolve("rotation_assets")
         : a_.runDirectory.toPath().resolve("Wild").resolve("rotation_assets");
   }

   public static Path resolve4(String string) {
      String text2 = resolve6(string);
      return resolve3().resolve(text2 + ".json");
   }

   public static RotationLabModule resolve5(Path path) {
      if (!Files.isRegularFile(path)) {
         return null;
      } else {
         try {
            RotationLabModule rotationLabModule;
            try (BufferedReader bufferedReader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
               RotationLabModule rotationLabModule2 = (RotationLabModule)GSON.fromJson(bufferedReader, RotationLabModule.class);
               invoke5(rotationLabModule2, resolve8(path.getFileName().toString()));
               rotationLabModule = rotationLabModule2;
            }

            return rotationLabModule;
         } catch (Throwable exception) {
            return null;
         }
      }
   }

   public static void invoke2(Path path, RotationLabModule rotationLabModule3) {
      try {
         Files.createDirectories(path.getParent());
         invoke5(rotationLabModule3, resolve8(path.getFileName().toString()));

         try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            GSON.toJson(rotationLabModule3, bufferedWriter);
         }
      } catch (Throwable exception2) {
      }
   }

   public static String resolve6(String string) {
      String text3 = string != null && !string.isBlank() ? string.trim() : "rotation_lab";
      text3 = text3.replace('\\', '/');
      int intValue = text3.lastIndexOf(47);
      if (intValue >= 0) {
         text3 = text3.substring(intValue + 1);
      }

      if (text3.endsWith(".json")) {
         text3 = text3.substring(0, text3.length() - 5);
      }

      text3 = text3.replaceAll("[^a-zA-Z0-9._-]", "_");
      if (text3.isBlank() || text3.equals(".") || text3.equals("..")) {
         text3 = "rotation_lab";
      }

      return text3;
   }

   private void invoke3(boolean bl) {
      long longValue = System.currentTimeMillis();
      if (bl || longValue - this.timestamp >= 1500L) {
         this.timestamp = longValue;
         Path path2 = resolve3();
         int intValue2 = 0;
         long longValue2 = 0L;

         try {
            if (Files.isDirectory(path2)) {
               try (Stream stream = Files.list(path2)) {
                  List items = ((Stream<Path>)stream).filter(path -> Files.isRegularFile(path) && path.getFileName().toString().endsWith(".json")).toList();
                  intValue2 = items.size();

                  for (Path path3 : (List<Path>)items) {
                     longValue2 += Files.getLastModifiedTime(path3).toMillis();
                  }
               }
            }
         } catch (Throwable exception3) {
         }

         if (bl || intValue2 != this.intValue || longValue2 != this.timestamp2) {
            this.intValue = intValue2;
            this.timestamp2 = longValue2;
            this.items.clear();
            if (intValue2 != 0) {
               try (Stream stream2 = Files.list(path2)) {
                  ((Stream<Path>)stream2).filter(path -> Files.isRegularFile(path) && path.getFileName().toString().endsWith(".json")).forEach(this::invoke4);
               } catch (Throwable exception4) {
               }
            }
         }
      }
   }

   private void invoke4(Path path) {
      RotationLabModule rotationLabModule4 = resolve5(path);
      if (rotationLabModule4 != null && rotationLabModule4.items != null) {
         for (RotationLabModule.RotationLabModuleState rotationLabModuleState : rotationLabModule4.items) {
            if (check(rotationLabModuleState)) {
               this.items.add(new RotationAssetLoader.RotationAssetLoaderData(path.getFileName().toString(), rotationLabModuleState));
            }
         }
      }
   }

   private static void invoke5(RotationLabModule rotationLabModule5, String string) {
      if (rotationLabModule5 != null) {
         rotationLabModule5.intValue = 1;
         if (rotationLabModule5.rotationLab == null || rotationLabModule5.rotationLab.isBlank()) {
            rotationLabModule5.rotationLab = string;
         }

         if (rotationLabModule5.items == null) {
            rotationLabModule5.items = new ArrayList<>();
         }

         rotationLabModule5.items.removeIf(rotationLabModuleState2 -> !check(rotationLabModuleState2));

         for (RotationLabModule.RotationLabModuleState rotationLabModuleState3 : rotationLabModule5.items) {
            if (rotationLabModuleState3.mixed == null || rotationLabModuleState3.mixed.isBlank()) {
               rotationLabModuleState3.mixed = "Mixed";
            }

            rotationLabModuleState3.items.sort(Comparator.comparingInt(rotationLabModuleState22 -> rotationLabModuleState22.intValue));
            rotationLabModuleState3.intValue = Math.max(rotationLabModuleState3.intValue, rotationLabModuleState3.items.get(rotationLabModuleState3.items.size() - 1).intValue + 1);
            RotationLabModule.RotationLabModuleState2 rotationLabModuleState23 = rotationLabModuleState3.items.get(rotationLabModuleState3.items.size() - 1);
            rotationLabModuleState3.floatValue3 = Math.abs(rotationLabModuleState3.floatValue3) > 0.001F ? rotationLabModuleState3.floatValue3 : rotationLabModuleState23.floatValue;
            rotationLabModuleState3.floatValue4 = Math.abs(rotationLabModuleState3.floatValue4) > 0.001F ? rotationLabModuleState3.floatValue4 : rotationLabModuleState23.floatValue2;
            rotationLabModuleState3.floatValue7 = Math.max(0.0F, Math.min(1.0F, rotationLabModuleState3.floatValue7));
         }
      }
   }

   private static boolean check(RotationLabModule.RotationLabModuleState rotationLabModuleState4) {
      return rotationLabModuleState4 != null && rotationLabModuleState4.items != null && rotationLabModuleState4.items.size() >= 2;
   }

   private double measure(RotationLabModule.RotationLabModuleState rotationLabModuleState5, float f, float g, String string) {
      float floatValue3 = Math.max(0.001F, rotationLabModuleState5.measure());
      float floatValue4 = Math.abs(rotationLabModuleState5.floatValue3) / floatValue3;
      double doubleValue = Math.abs(floatValue3 - f) * 0.85;
      double doubleValue2 = Math.abs(floatValue4 - g) * 12.0;
      double doubleValue3 = measure2(rotationLabModuleState5.mixed, string);
      double doubleValue4 = (1.0 - rotationLabModuleState5.floatValue7) * 5.0;
      double doubleValue5 = ThreadLocalRandom.current().nextDouble(0.0, 1.35);
      return doubleValue + doubleValue2 + doubleValue3 + doubleValue4 + doubleValue5;
   }

   private static double measure2(String string, String string2) {
      String text4 = string == null ? "" : string.toLowerCase(Locale.ROOT);
      String text5 = string2 == null ? "" : string2.toLowerCase(Locale.ROOT);
      if (text4.equals(text5) || text4.equals("mixed")) {
         return 0.0;
      } else if (text5.equals("mixed")) {
         return 1.0;
      } else {
         return !text4.contains(text5) && !text5.contains(text4) ? 3.0 : 0.75;
      }
   }

   private static String resolve7(float f, float g, boolean bl) {
      if (bl) {
         return "Attack";
      } else if (f < 6.0F) {
         return "Micro";
      } else if (g < 0.35F) {
         return "Vertical";
      } else {
         return f > 28.0F ? "Flick" : "Tracking";
      }
   }

   private static String resolve8(String string) {
      return string != null && string.endsWith(".json") ? string.substring(0, string.length() - 5) : string;
   }

   record RotationAssetLoaderData(String file, RotationLabModule.RotationLabModuleState pattern) {
   }
}
