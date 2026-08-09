package ru.metaculture.protection;

import com.sun.jna.Callback;

public interface DiscordDisconnectedCallback extends Callback {
   void invoke(int i, String string);
}
