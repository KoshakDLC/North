package ru.metaculture.protection;

import net.minecraft.client.MinecraftClient;

public final class MouseButtonEvent extends Event {
   private final MinecraftClient client;
   private final long windowHandle;

   public MouseButtonEvent(MinecraftClient client, long windowHandle) {
      this.client = client;
      this.windowHandle = windowHandle;
   }

   public MinecraftClient getClient() {
      return this.client;
   }

   public long getWindowHandle() {
      return this.windowHandle;
   }
}
