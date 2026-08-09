package ru.metaculture.protection;

import com.sun.jna.Callback;

public interface DiscordSpectateGameCallback extends Callback {
   void invoke(String string);
}
