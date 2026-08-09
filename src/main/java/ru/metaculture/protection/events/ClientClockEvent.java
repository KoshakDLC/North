package ru.metaculture.protection;

import java.time.Instant;
import java.util.Objects;
import net.minecraft.client.MinecraftClient;

public final class ClientClockEvent extends Event {
   private final MinecraftClient client;
   private final Instant instant;

   public ClientClockEvent(MinecraftClient minecraftClient) {
      this(minecraftClient, Instant.now());
   }

   public ClientClockEvent(MinecraftClient minecraftClient, Instant instant) {
      this.client = minecraftClient;
      this.instant = Objects.requireNonNull(instant, "timestamp");
   }

   public MinecraftClient getClient() {
      return this.client;
   }

   public Instant getInstant() {
      return this.instant;
   }
}
