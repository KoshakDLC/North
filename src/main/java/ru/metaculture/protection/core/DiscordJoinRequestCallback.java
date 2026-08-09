package ru.metaculture.protection;

import com.sun.jna.Callback;
import org.wild.rpc.DiscordUser;

public interface DiscordJoinRequestCallback extends Callback {
   void invoke(DiscordUser discordUser);
}
