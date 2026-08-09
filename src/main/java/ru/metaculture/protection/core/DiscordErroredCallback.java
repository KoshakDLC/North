package ru.metaculture.protection;

import com.sun.jna.Callback;

public interface DiscordErroredCallback extends Callback {
   void invoke(int i, String string);
}
