package ru.metaculture.protection;

import com.sun.jna.Callback;

public interface DiscordJoinGameCallback extends Callback {
   void invoke(String string);
}
