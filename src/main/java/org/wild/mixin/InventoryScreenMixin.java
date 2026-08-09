package org.wild.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.metaculture.protection.Animations;
import ru.metaculture.protection.WildClient;

@Mixin({InventoryScreen.class})
public class InventoryScreenMixin {
   @Unique
   private boolean litka$inventoryScaled;

   @Inject(
      method = {"render"},
      at = {@At("HEAD")}
   )
   private void litka$preInventoryRender(DrawContext drawContext, int i, int j, float f, CallbackInfo callbackInfo) {
      this.litka$inventoryScaled = false;
      if (WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null) {
         Animations animations = WildClient.INSTANCE.moduleManager.getModule(Animations.class);
         Screen screen = (Screen)(Object)this;
         if (animations != null && animations.check2(screen)) {
            float floatValue = animations.measure3(screen);
            float floatValue2 = drawContext.getScaledWindowWidth() / 2.0F;
            float floatValue3 = drawContext.getScaledWindowHeight() / 2.0F;
            drawContext.getMatrices().pushMatrix();
            drawContext.getMatrices().translate(floatValue2, floatValue3);
            drawContext.getMatrices().scale(floatValue, floatValue);
            drawContext.getMatrices().translate(-floatValue2, -floatValue3);
            this.litka$inventoryScaled = true;
         }
      }
   }

   @Inject(
      method = {"render"},
      at = {@At("TAIL")}
   )
   private void litka$postInventoryRender(DrawContext drawContext, int i, int j, float f, CallbackInfo callbackInfo) {
      if (this.litka$inventoryScaled) {
         drawContext.getMatrices().popMatrix();
         this.litka$inventoryScaled = false;
      }
   }
}
