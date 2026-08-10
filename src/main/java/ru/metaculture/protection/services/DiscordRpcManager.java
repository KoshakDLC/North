package ru.metaculture.protection;

import org.wild.rpc.DiscordEventHandlers;
import org.wild.rpc.DiscordRichPresence;
import ru.metaculture.profile.Profile;

public class DiscordRpcManager implements MinecraftAccessor {
   public static DiscordRichPresence discordRichPresence = new DiscordRichPresence();
   public static boolean flag;
   private static Thread thread;
   private static DiscordRpcLibrary discordRpcLibrary;
   public static String text = "";

   public void invoke() {
      if (DiscordRpcLibrary.DiscordRpcLibraryState.check()) {
         DiscordRpcLibrary discordRpcLibrary = DiscordRpcLibrary.DiscordRpcLibraryState.resolve();
         if (!flag) {
            flag = true;
            this.discordRpcLibrary = discordRpcLibrary;
            DiscordEventHandlers discordEventHandlers = new DiscordEventHandlers();
            discordEventHandlers.ready = discordUser -> text = discordUser.userId;
            discordRpcLibrary.Discord_Initialize("1494051037655339148", discordEventHandlers, true, "");
            discordRichPresence.startTimestamp = System.currentTimeMillis() / 1000L;
            discordRichPresence.largeImageText = String.valueOf(Profile.getUid());
            discordRpcLibrary.Discord_UpdatePresence(discordRichPresence);
            thread = new Thread(() -> {
               while (!Thread.currentThread().isInterrupted()) {
                  discordRpcLibrary.Discord_RunCallbacks();
                  discordRichPresence.details = "North | Version: " + "1.21.8";
                  discordRichPresence.state = "User: " + Profile.getUsername();
                  discordRichPresence.button_label_1 = "Telegram";
                  discordRichPresence.button_url_1 = "";
                  discordRichPresence.button_label_2 = "VK";
                  discordRichPresence.button_url_2 = "";
                  discordRichPresence.largeImageKey = "https://i.ibb.co/20hRBGzL/gif-gif.gif";
                  discordRpcLibrary.Discord_UpdatePresence(discordRichPresence);

                  try {
                     Thread.sleep(2000L);
                  } catch (InterruptedException var2x) {
                     Thread.currentThread().interrupt();
                     break;
                  }
               }
            }, "TH-RPC-Handler");
            thread.setDaemon(true);
            thread.start();
         }
      }
   }

   public static synchronized void invoke2() {
      if (flag || thread != null || discordRpcLibrary != null) {
         flag = false;
         Thread rpcThread = thread;
         thread = null;
         DiscordRpcLibrary discordRpcLibrary2 = discordRpcLibrary;
         discordRpcLibrary = null;
         if (rpcThread != null) {
            rpcThread.interrupt();
         }

         if (discordRpcLibrary2 != null) {
            try {
               discordRpcLibrary2.Discord_ClearPresence();
            } catch (Throwable exception) {
            }

            try {
               discordRpcLibrary2.Discord_Shutdown();
            } catch (Throwable exception2) {
            }
         }
      }
   }
}
