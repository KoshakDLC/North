package ru.metaculture.protection;

import net.minecraft.client.MinecraftClient;

public final class MouseUpdateEvent extends Event {
   private final MinecraftClient client;

   public MouseUpdateEvent(MinecraftClient minecraftClient) {
      this.client = minecraftClient;
   }

   public MinecraftClient getClient() {
      return this.client;
   }
}
