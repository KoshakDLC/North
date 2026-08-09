package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Supplier;
import lombok.Generated;
import ru.metaculture.sdk.Compile;
import ru.metaculture.sdk.Loader;

public abstract class Command implements MinecraftAccessor {
   private final String name;
   private final String description;
   private final String usage;
   private final Map<String, Supplier<List<String>>> completionProviders = new HashMap<>();

   public Command(String string, String string2, String string3) {
      this.name = string;
      this.description = string2;
      this.usage = string3;
   }

   @Compile
   protected void addCompletionProvider(String string, Supplier<List<String>> supplier) {
      if (string == null || string.isBlank()) {
         return;
      }

      this.completionProviders.put(string.toLowerCase(Locale.ROOT), Objects.requireNonNullElse(supplier, List::of));
   }

   public List<String> getSuggestions(String[] strings) {
      if (strings.length == 2) {
         return this.completionProviders.keySet().stream().filter(string -> string.startsWith(strings[1].toLowerCase())).toList();
      } else {
         if (strings.length == 3) {
            String text = strings[1].toLowerCase(Locale.ROOT);
            if (this.completionProviders.containsKey(text)) {
               List<String> items;

               try {
                  items = this.completionProviders.get(text).get();
               } catch (RuntimeException exception) {
                  return List.of();
               }

               return items == null
                  ? List.of()
                  : items.stream()
                     .filter(Objects::nonNull)
                     .filter(string -> string.toLowerCase(Locale.ROOT).startsWith(strings[2].toLowerCase(Locale.ROOT)))
                     .toList();
            }
         }

         return new ArrayList<>();
      }
   }

   public abstract void execute(String[] strings);

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public String getDescription() {
      return this.description;
   }

   @Generated
   public String getUsage() {
      return this.usage;
   }

   static {
      Loader.initialize();
   }
}
