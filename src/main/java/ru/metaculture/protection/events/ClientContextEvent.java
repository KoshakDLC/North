package ru.metaculture.protection;

import java.util.Objects;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;

public final class ClientContextEvent extends Event {
   private final MinecraftClient client;
   private final ClientPlayerEntity clientPlayerEntity;
   private final ClientWorld clientWorld;

   public ClientContextEvent(MinecraftClient minecraftClient, ClientPlayerEntity clientPlayerEntity, ClientWorld clientWorld) {
      this.client = Objects.requireNonNull(minecraftClient, "client");
      this.clientPlayerEntity = Objects.requireNonNull(clientPlayerEntity, "player");
      this.clientWorld = Objects.requireNonNull(clientWorld, "world");
   }

   public MinecraftClient getClient() {
      return this.client;
   }

   public ClientPlayerEntity getClientPlayerEntity() {
      return this.clientPlayerEntity;
   }

   public ClientWorld getClientWorld() {
      return this.clientWorld;
   }
}
