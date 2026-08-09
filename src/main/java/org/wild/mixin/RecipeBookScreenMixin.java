package org.wild.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.ingame.RecipeBookScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.metaculture.protection.Animations;
import ru.metaculture.protection.WildClient;

@Mixin({RecipeBookScreen.class})
public class RecipeBookScreenMixin {
   @Unique
   private boolean litka$recipeBookScaled;

   @Inject(
      method = {"render"},
      at = {@At("HEAD")}
   )
   private void litka$preRecipeBookRender(DrawContext drawContext, int i, int j, float f, CallbackInfo callbackInfo) {
      this.litka$recipeBookScaled = false;
      if (WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null) {
         Animations animations = WildClient.INSTANCE.moduleManager.getModule(Animations.class);
         Screen screen = (Screen)(Object)this;
         if (!(screen instanceof InventoryScreen)) {
            if (animations != null && animations.check2(screen)) {
               float floatValue = animations.measure3(screen);
               float floatValue2 = drawContext.getScaledWindowWidth() / 2.0F;
               float floatValue3 = drawContext.getScaledWindowHeight() / 2.0F;
               drawContext.getMatrices().pushMatrix();
               drawContext.getMatrices().translate(floatValue2, floatValue3);
               drawContext.getMatrices().scale(floatValue, floatValue);
               drawContext.getMatrices().translate(-floatValue2, -floatValue3);
               this.litka$recipeBookScaled = true;
            }
         }
      }
   }

   @Inject(
      method = {"render"},
      at = {@At("TAIL")}
   )
   private void litka$postRecipeBookRender(DrawContext drawContext, int i, int j, float f, CallbackInfo callbackInfo) {
      if (this.litka$recipeBookScaled) {
         drawContext.getMatrices().popMatrix();
         this.litka$recipeBookScaled = false;
      }
   }
}
