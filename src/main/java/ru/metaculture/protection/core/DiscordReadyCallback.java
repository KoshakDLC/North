package ru.metaculture.protection;

import com.sun.jna.Callback;
import org.wild.rpc.DiscordUser;

public interface DiscordReadyCallback extends Callback {
   void apply(DiscordUser discordUser);
}
