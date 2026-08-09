package ru.metaculture.protection;

import java.util.Objects;
import net.minecraft.client.MinecraftClient;

public final class ClientTickEvent extends Event {
   private final MinecraftClient client;

   public ClientTickEvent(MinecraftClient minecraftClient) {
      this.client = Objects.requireNonNull(minecraftClient, "client");
   }

   public MinecraftClient getClient() {
      return this.client;
   }
}
