package ru.metaculture.protection;

import java.util.Objects;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;

public final class WorldRenderContextEvent extends Event {
   private final MinecraftClient client;
   private final GameRenderer gameRenderer;
   private final WorldRenderCapture worldRenderCapture;
   private final float floatValue;

   public WorldRenderContextEvent(MinecraftClient minecraftClient, GameRenderer gameRenderer, WorldRenderCapture worldRenderCapture, float f) {
      this.client = Objects.requireNonNull(minecraftClient, "client");
      this.gameRenderer = Objects.requireNonNull(gameRenderer, "gameRenderer");
      this.worldRenderCapture = Objects.requireNonNull(worldRenderCapture, "worldRenderer");
      this.floatValue = f;
   }

   public MinecraftClient getClient() {
      return this.client;
   }

   public GameRenderer getGameRenderer() {
      return this.gameRenderer;
   }

   public WorldRenderCapture getWorldRenderCapture() {
      return this.worldRenderCapture;
   }

   public MatrixStack resolve() {
      return this.worldRenderCapture.getMatrixStack();
   }

   public Matrix4f resolve2() {
      return this.worldRenderCapture.resolve2();
   }

   public Matrix4f resolve3() {
      return this.worldRenderCapture.resolve4();
   }

   public float getFloatValue() {
      return this.floatValue;
   }
}
