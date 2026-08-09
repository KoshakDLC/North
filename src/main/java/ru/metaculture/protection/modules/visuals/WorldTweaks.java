package ru.metaculture.protection;

import java.awt.Color;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleAccess(
   usernames = {"lichoday"}
)
@ModuleRegister(
   name = "WorldTweaks",
   category = Category.Visuals,
   description = "Кинематографичная атмосфера: ветер, туман, тонировка неба",
   riskLevels = {ModuleRiskLevel.NEW}
)
public final class WorldTweaks extends Module {
   public final NumberSetting windSpeed = new NumberSetting("Wind Speed", 0.72F, 0.0F, 2.0F, 0.01F, false);
   public final NumberSetting windDirection = new NumberSetting("Wind Direction", 35.0F, 0.0F, 360.0F, 1.0F, false);
   public final NumberSetting fogDensity = new NumberSetting("Fog Density", 0.032F, 0.0F, 0.1F, 0.001F, false);
   public final NumberSetting horizonDissolve = new NumberSetting("Horizon Dissolve", 0.82F, 0.0F, 1.0F, 0.01F, true);
   public final NumberSetting skyLift = new NumberSetting("Sky Lift", 0.64F, 0.0F, 1.0F, 0.01F, true);
   public final NumberSetting edgeSoftness = new NumberSetting("Edge Softness", 0.72F, 0.0F, 1.0F, 0.01F, true);
   public final HudColorSetting atmosphereTint = new HudColorSetting("Atmosphere Tint", 6978453);

   public WorldTweaks() {
      this.addSettings(
         new Setting[]{
            this.windSpeed, this.windDirection, this.fogDensity, this.horizonDissolve, this.skyLift, this.edgeSoftness, this.atmosphereTint
         }
      );
   }

   public static boolean check() {
      if (WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null) {
         WorldTweaks worldTweaks = WildClient.INSTANCE.moduleManager.getModule(WorldTweaks.class);
         return worldTweaks != null && worldTweaks.enabled;
      } else {
         return false;
      }
   }

   @EventHandler(
      priority = 0
   )
   public void onWorldRenderContext(WorldRenderContextEvent worldRenderContextEvent) {
      if (worldRenderContextEvent != null
         && worldRenderContextEvent.getClient() != null
         && worldRenderContextEvent.getClient().world != null
         && worldRenderContextEvent.getClient().player != null
         && worldRenderContextEvent.getWorldRenderCapture() != null) {
         WorldAtmosphereRenderer.WorldAtmosphereRendererState2 worldAtmosphereRendererState2 = new WorldAtmosphereRenderer.WorldAtmosphereRendererState2();
         Color color = this.atmosphereTint.getColor();
         float floatValue = (float)Math.toRadians(this.windDirection.getValue());
         worldAtmosphereRendererState2.floatValue = this.windSpeed.getValue();
         worldAtmosphereRendererState2.floatValue2 = (float)Math.cos(floatValue);
         worldAtmosphereRendererState2.floatValue3 = 0.0F;
         worldAtmosphereRendererState2.floatValue4 = (float)Math.sin(floatValue);
         worldAtmosphereRendererState2.floatValue5 = this.fogDensity.getValue();
         worldAtmosphereRendererState2.floatValue6 = this.horizonDissolve.getValue();
         worldAtmosphereRendererState2.floatValue7 = this.skyLift.getValue();
         worldAtmosphereRendererState2.floatValue8 = this.edgeSoftness.getValue();
         worldAtmosphereRendererState2.floatValue9 = color.getRed() / 255.0F;
         worldAtmosphereRendererState2.floatValue10 = color.getGreen() / 255.0F;
         worldAtmosphereRendererState2.floatValue11 = color.getBlue() / 255.0F;
         worldAtmosphereRendererState2.floatValue15 = ((float)worldRenderContextEvent.getClient().world.getTime() + worldRenderContextEvent.getFloatValue()) * 0.05F;
         WorldAtmosphereRenderer.getINSTANCE()
            .invoke(
               worldRenderContextEvent.getClient(), worldRenderContextEvent.getWorldRenderCapture().getCamera(), worldRenderContextEvent.resolve2(), worldRenderContextEvent.resolve3(), worldAtmosphereRendererState2
            );
      }
   }
}
