package ru.metaculture.protection;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.ConnectScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.network.DisconnectionInfo;
import ru.metaculture.sdk.Loader;

public final class ServerConnector {
   private static final long TIMESTAMP = 3500L;
   private static final Object OBJECT = new Object();
   private static volatile long timestamp;
   private static volatile ServerInfo serverInfo;
   private static volatile boolean flag;
   private static volatile ServerInfo serverInfo2;

   private ServerConnector() {
   }

   public static void invoke(ServerInfo serverInfo, DisconnectionInfo disconnectionInfo) {
   }

   public static void invoke2(MinecraftClient minecraftClient) {
      if (flag && minecraftClient != null) {
         try {
            invoke4(minecraftClient);
         } catch (Throwable exception) {
         }

         if (minecraftClient.currentScreen instanceof DisconnectedScreen) {
            Object object = OBJECT;
            synchronized (OBJECT){}
            boolean flag = false ;

            ServerInfo serverInfo2;
            long longValue;
            try {
               flag = true;
               if (!flag) {
                  return;
               }

               longValue = timestamp;
               serverInfo2 = serverInfo;
               flag = false;
            } finally {
               if (flag) {
               }
            }

            if (System.currentTimeMillis() >= longValue) {
               if (minecraftClient.getNetworkHandler() == null) {
                  if (serverInfo2 != null && serverInfo2.address != null && !serverInfo2.address.isBlank()) {
                     boolean flag2 = false ;

                     label157: {
                        try {
                           flag2 = true;
                           object = ServerAddress.parse(serverInfo2.address);
                           ConnectScreen.connect(resolve2(), minecraftClient, (ServerAddress)object, serverInfo2, false, null);
                           flag2 = false;
                           break label157;
                        } catch (Throwable exception2) {
                           flag2 = false;
                        } finally {
                           if (flag2) {
                              invoke3();
                           }
                        }

                        invoke3();
                        return;
                     }

                     invoke3();
                  } else {
                     invoke3();
                  }
               }
            }
         }
      }
   }

   public static void invoke3() {
      synchronized (OBJECT) {
         flag = false;
         timestamp = 0L;
         serverInfo = null;
      }
   }

   public static void invoke4(MinecraftClient minecraftClient) {
      if (minecraftClient != null) {
         try {
            if (minecraftClient.getNetworkHandler() == null) {
               return;
            }

            ServerInfo serverInfo3 = minecraftClient.getCurrentServerEntry();
            if (serverInfo3 == null || serverInfo3.address == null || serverInfo3.address.isBlank()) {
               return;
            }

            serverInfo2 = resolve(serverInfo3);
         } catch (Throwable exception3) {
         }
      }
   }

   private static ServerInfo resolve(ServerInfo serverInfo) {
      ServerInfo serverInfo4 = new ServerInfo(serverInfo.name, serverInfo.address, serverInfo.getServerType());
      serverInfo4.copyWithSettingsFrom(serverInfo);
      return serverInfo4;
   }

   private static Screen resolve2() {
      return (Screen)(UnHook.active ? new MultiplayerScreen(new TitleScreen()) : new WildMultiplayerScreen(new MainMenuScreen()));
   }

   private static String resolve3(String string) {
      return null;
   }

   private static boolean check(String string) {
      return false;
   }

   static {
      Loader.initialize();
   }
}
