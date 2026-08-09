package ru.metaculture.protection;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import org.wild.module.api.Module;

public class LegacyClickGuiRenderer extends LegacyClickGuiState {
   public static void invoke(RenderManager renderManager, DrawContext drawContext, int i, int j, float f) {
      MinecraftClient client = MinecraftClient.getInstance();
      if (client != null && client.getWindow() != null) {
         int intValue = client.getWindow().getFramebufferWidth();
         int intValue2 = client.getWindow().getFramebufferHeight();
         if (intValue > 0 && intValue2 > 0) {
            // хит-тест должен быть в тех же scaled-координатах, что и панели: делим на реальный GUI scale, а не на фиксированный 2.0
            float guiScale = Math.max(1.0F, (float)intValue / Math.max(1.0F, client.getWindow().getScaledWidth()));
            int intValue3 = (int)(i / guiScale);
            int intValue4 = (int)(j / guiScale);
            LegacyClickGuiState.intValue = intValue3;
            LegacyClickGuiState.intValue2 = intValue4;
            LegacyClickGuiState.easedAnimation.check();
            LegacyClickGuiState.easedAnimation2.check();
            LegacyClickGuiState.easedAnimation3.check();
            LegacyClickGuiState.easedAnimation4.check();
            if (LegacyClickGuiState.items != null) {
               for (Module module : LegacyClickGuiState.items) {
                  LegacyClickGuiState.resolve2(module).check();
                  LegacyClickGuiState.resolve3(module).check();
                  LegacyClickGuiState.resolve4(module).check();
               }
            }

            LegacyClickGuiState.timedAnimation.setTimestamp2(1.0);
            float floatValue = LegacyClickGuiState.easedAnimation.measure3();
            if (!(floatValue <= 0.001F)) {
               float floatValue2 = client.getWindow().getScaledWidth();
               float floatValue3 = client.getWindow().getScaledHeight();
               LegacyClickGuiState.floatValue6 = floatValue2 / 2.0F - LegacyClickGuiState.floatValue8 / 2.0F;
               LegacyClickGuiState.floatValue7 = floatValue3 / 2.0F - LegacyClickGuiState.floatValue9 / 2.0F - (80.0F - 80.0F * floatValue);
               float floatValue4 = (float)client.getWindow().getFramebufferWidth() / client.getWindow().getScaledWidth();
               renderManager.invoke58(floatValue4);

               try {
                  if (LegacyClickGuiState.blyurNada.isEnabled()) {
                     renderManager.invoke48(18.0F);
                  }

                  renderManager.invoke4(0.0F, 0.0F, floatValue2, floatValue3, RenderManager.RenderManagerState.compute37(0, 0, 0, (int)(80.0F * floatValue)));
                  LegacyClickGuiScreenController.invoke2(renderManager, intValue3, intValue4, floatValue);
               } finally {
                  renderManager.invoke57();
                  renderManager.invoke57();
               }
            }
         }
      }
   }
}
