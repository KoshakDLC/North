package ru.metaculture.protection;

import com.sun.jna.Library;
import com.sun.jna.Native;
import org.wild.rpc.DiscordEventHandlers;
import org.wild.rpc.DiscordRichPresence;

public interface DiscordRpcLibrary extends Library {
   void Discord_UpdateHandlers(DiscordEventHandlers discordEventHandlers);

   void Discord_UpdatePresence(DiscordRichPresence discordRichPresence);

   void Discord_Respond(String string, int i);

   void Discord_Register(String string, String string2);

   void Discord_Shutdown();

   void Discord_UpdateConnection();

   void Discord_RegisterSteamGame(String string, String string2);

   void Discord_RunCallbacks();

   void Discord_Initialize(String string, DiscordEventHandlers discordEventHandlers, boolean bl, String string2);

   void Discord_ClearPresence();

   public static class DiscordRpcLibraryState {
      private static DiscordRpcLibrary discordRpcLibrary;
      private static boolean flag = false;

      public static DiscordRpcLibrary resolve() {
         if (!flag) {
            flag = true;

            try {
               discordRpcLibrary = (DiscordRpcLibrary)Native.loadLibrary("discord-rpc", DiscordRpcLibrary.class);
            } catch (UnsatisfiedLinkError unsatisfiedLinkError) {
               discordRpcLibrary = null;
            }
         }

         return discordRpcLibrary;
      }

      public static boolean check() {
         return resolve() != null;
      }
   }
}
