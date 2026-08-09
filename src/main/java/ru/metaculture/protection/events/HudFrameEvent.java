package ru.metaculture.protection;

import java.util.Objects;
import net.minecraft.client.MinecraftClient;

public final class HudFrameEvent extends Event {
   private final MinecraftClient client;
   private final RenderManager renderManager;
   private final FontObject fontObject;
   private final int intValue;
   private final int intValue2;

   public HudFrameEvent(MinecraftClient minecraftClient, RenderManager renderManager, FontObject fontObject, int i, int j) {
      this.client = Objects.requireNonNull(minecraftClient, "client");
      this.renderManager = Objects.requireNonNull(renderManager, "renderer");
      this.fontObject = Objects.requireNonNull(fontObject, "defaultFont");
      this.intValue = i;
      this.intValue2 = j;
   }

   public MinecraftClient getClient() {
      return this.client;
   }

   public RenderManager getRenderManager() {
      return this.renderManager;
   }

   public FontObject getFontObject() {
      return this.fontObject;
   }

   public int getIntValue() {
      return this.intValue;
   }

   public int getIntValue2() {
      return this.intValue2;
   }
}
