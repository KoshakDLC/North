package ru.metaculture.protection;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class HudRenderEvent extends Event {
   private MinecraftClient client;
   private RenderManager renderManager;
   private FontObject fontObject;
   private int intValue;
   private int intValue2;
   private DrawContext drawContext;

   public HudRenderEvent() {
   }

   public HudRenderEvent(MinecraftClient minecraftClient, RenderManager renderManager, FontObject fontObject, int i, int j, DrawContext drawContext) {
      this.resolve(minecraftClient, renderManager, fontObject, i, j, drawContext);
   }

   public HudRenderEvent resolve(MinecraftClient minecraftClient, RenderManager renderManager2, FontObject fontObject2, int i, int j, DrawContext drawContext) {
      this.client = minecraftClient;
      this.renderManager = renderManager2;
      this.fontObject = fontObject2;
      this.intValue = i;
      this.intValue2 = j;
      this.drawContext = drawContext;
      return this;
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

   public DrawContext getDrawContext() {
      return this.drawContext;
   }
}
