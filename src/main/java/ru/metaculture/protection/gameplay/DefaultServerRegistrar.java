package ru.metaculture.protection;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.network.ServerInfo.ServerType;
import net.minecraft.client.option.ServerList;

public final class DefaultServerRegistrar {
   private static final String BRAVOHVH = "BravoHvH";
   private static final String WI_BRAVOHVH_SU = "wi.bravohvh.su";
   private static final AtomicBoolean ATOMIC_BOOLEAN = new AtomicBoolean(false);

   private DefaultServerRegistrar() {
   }

   public static void invoke(MinecraftClient minecraftClient) {
      if (minecraftClient != null && ATOMIC_BOOLEAN.compareAndSet(false, true)) {
         try {
            ServerList serverList2 = new ServerList(minecraftClient);
            serverList2.loadFile();
            if (check(serverList2, "wi.bravohvh.su")) {
               return;
            }

            ServerInfo serverInfo = new ServerInfo("BravoHvH", "wi.bravohvh.su", ServerType.OTHER);
            serverList2.add(serverInfo, false);
            serverList2.saveFile();
         } catch (Throwable exception) {
         }
      }
   }

   private static boolean check(ServerList serverList, String string) {
      String text = resolve(string);
      if (text.isEmpty()) {
         return true;
      } else {
         int intValue = serverList.size();

         for (int intValue2 = 0; intValue2 < intValue; intValue2++) {
            ServerInfo serverInfo2 = serverList.get(intValue2);
            if (serverInfo2 != null && resolve(serverInfo2.address).equals(text)) {
               return true;
            }
         }

         return false;
      }
   }

   private static String resolve(String string) {
      if (string == null) {
         return "";
      } else {
         String text2 = string.trim().toLowerCase(Locale.ROOT);
         if (text2.endsWith(":25565")) {
            text2 = text2.substring(0, text2.length() - ":25565".length());
         }

         return text2;
      }
   }
}
