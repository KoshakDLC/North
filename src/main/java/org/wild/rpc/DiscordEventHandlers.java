package org.wild.rpc;

import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
import ru.metaculture.protection.DiscordDisconnectedCallback;
import ru.metaculture.protection.DiscordErroredCallback;
import ru.metaculture.protection.DiscordJoinGameCallback;
import ru.metaculture.protection.DiscordJoinRequestCallback;
import ru.metaculture.protection.DiscordReadyCallback;
import ru.metaculture.protection.DiscordSpectateGameCallback;

public class DiscordEventHandlers extends Structure {
   public DiscordDisconnectedCallback disconnected;
   public DiscordJoinRequestCallback joinRequest;
   public DiscordSpectateGameCallback spectateGame;
   public DiscordReadyCallback ready;
   public DiscordErroredCallback errored;
   public DiscordJoinGameCallback joinGame;

   protected List<String> getFieldOrder() {
      return Arrays.asList("ready", "disconnected", "errored", "joinGame", "spectateGame", "joinRequest");
   }
}
