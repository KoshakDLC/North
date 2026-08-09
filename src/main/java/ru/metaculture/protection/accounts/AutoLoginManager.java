package ru.metaculture.protection;

import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import net.minecraft.client.MinecraftClient;

public final class AutoLoginManager {
   private static volatile String text = "";
   private static volatile String text2 = "";
   private static volatile String text3 = "";
   private static volatile long timestamp;

   private AutoLoginManager() {
   }

   public static void invoke(String string, String string2) {
      text = resolve3(string);
      text2 = string2 == null ? "" : string2.trim();
   }

   public static void invoke2(MinecraftClient minecraftClient) {
      if (minecraftClient != null && minecraftClient.getSession() != null) {
         List items = AltVaultStore.resolve(resolve2());
         if (!items.isEmpty()) {
            String text = AltVaultStore.resolve2(resolve2());
            AltVaultStore.AltVaultStoreTimedEntry altVaultStoreTimedEntry = (AltVaultStore.AltVaultStoreTimedEntry)items.stream()
               .filter(object -> ((AltVaultStore.AltVaultStoreTimedEntry)object).id().equals(text))
               .findFirst()
               .orElseGet(
                  () -> items.stream()
                     .filter(object2 -> ((AltVaultStore.AltVaultStoreTimedEntry)object2).lastUsedAt() > 0L)
                     .max(Comparator.comparingLong(AltVaultStore.AltVaultStoreTimedEntry::lastUsedAt))
                     .orElse(null)
               );
            if (altVaultStoreTimedEntry != null && !altVaultStoreTimedEntry.name().isBlank()) {
               invoke(altVaultStoreTimedEntry.name(), altVaultStoreTimedEntry.password());
               if (!altVaultStoreTimedEntry.name().equalsIgnoreCase(minecraftClient.getSession().getUsername())) {
                  SessionManager.invoke(minecraftClient);
                  if (!SessionManager.check(minecraftClient, altVaultStoreTimedEntry.name())) {
                     SessionManager.resolve2(minecraftClient, altVaultStoreTimedEntry.name());
                  }
               }
            }
         }
      }
   }

   public static void invoke3(MinecraftClient minecraftClient) {
      if (minecraftClient != null && minecraftClient.getSession() != null) {
         String text2 = resolve3(minecraftClient.getSession().getUsername());
         if (!text2.isEmpty()) {
            Thread thread = new Thread(() -> {
               String var1x;
               try {
                  var1x = resolve(text2);
               } catch (Throwable exception) {
                  return;
               }

               if (!var1x.isEmpty()) {
                  String var2x = text2.toLowerCase(Locale.ROOT) + ":" + Integer.toHexString(var1x.hashCode());
                  long longValue = System.currentTimeMillis();
                  synchronized (AutoLoginManager.class) {
                     if (var2x.equals(text3) && longValue - timestamp < 4500L) {
                        return;
                     }

                     text3 = var2x;
                     timestamp = longValue;
                  }

                  try {
                     Thread.sleep(1600L);
                  } catch (InterruptedException interruptedException) {
                     Thread.currentThread().interrupt();
                     return;
                  }

                  MinecraftClient client = MinecraftClient.getInstance();
                  if (client != null) {
                     client.execute(() -> {
                        if (client.player != null && client.player.networkHandler != null && client.getSession() != null) {
                           if (text2.equalsIgnoreCase(resolve3(client.getSession().getUsername()))) {
                              client.player.networkHandler.sendChatCommand("login " + var1x);
                           }
                        }
                     });
                  }
               }
            }, "Wild Alt AutoLogin");
            thread.setDaemon(true);
            thread.start();
         }
      }
   }

   private static String resolve(String string) {
      if (string.isEmpty()) {
         return "";
      } else if (string.equalsIgnoreCase(text) && !text2.isEmpty()) {
         return text2;
      } else {
         for (AltVaultStore.AltVaultStoreTimedEntry altVaultStoreTimedEntry2 : AltVaultStore.resolve(resolve2())) {
            if ("CRACKED".equalsIgnoreCase(altVaultStoreTimedEntry2.type()) && string.equalsIgnoreCase(altVaultStoreTimedEntry2.name())) {
               return altVaultStoreTimedEntry2.password() == null ? "" : altVaultStoreTimedEntry2.password().trim();
            }
         }

         return "";
      }
   }

   private static File resolve2() {
      return WildClient.INSTANCE != null && WildClient.INSTANCE.file != null
         ? new File(WildClient.INSTANCE.file, "accounts.json")
         : new File(WildClient.getFILE(), "accounts.json");
   }

   private static String resolve3(String string) {
      return string == null ? "" : string.trim();
   }
}
