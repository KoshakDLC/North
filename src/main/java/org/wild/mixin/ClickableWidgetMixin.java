package org.wild.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ClickableWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.metaculture.protection.Animations;
import ru.metaculture.protection.SpringAnimation;
import ru.metaculture.protection.SpringSpec;
import ru.metaculture.protection.WildClient;

@Mixin({ClickableWidget.class})
public abstract class ClickableWidgetMixin {
   @Unique
   private SpringAnimation litka$buttonMotion;
   @Unique
   private boolean litka$buttonScaled;

   @Shadow
   public abstract int getX();

   @Shadow
   public abstract int getY();

   @Shadow
   public abstract int getWidth();

   @Shadow
   public abstract int getHeight();

   @Shadow
   public abstract boolean isHovered();

   @Inject(
      method = {"render"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/widget/ClickableWidget;renderWidget(Lnet/minecraft/client/gui/DrawContext;IIF)V"
      )}
   )
   private void litka$preRenderWidget(DrawContext drawContext, int i, int j, float f, CallbackInfo callbackInfo) {
      this.litka$buttonScaled = false;
      if (WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null) {
         Animations animations = WildClient.INSTANCE.moduleManager.getModule(Animations.class);
         if (animations != null && animations.enabled && animations.animirovat.isEnabled("Кнопки")) {
            if (this.litka$buttonMotion == null) {
               this.litka$buttonMotion = new SpringAnimation(1.0F);
            }

            float floatValue = this.litka$buttonMotion.measure(this.isHovered() ? 1.03F : 1.0F, this.litka$buttonSpring(animations));
            float floatValue2 = this.getX() + this.getWidth() * 0.5F;
            float floatValue3 = this.getY() + this.getHeight() * 0.5F;
            drawContext.getMatrices().pushMatrix();
            drawContext.getMatrices().translate(floatValue2, floatValue3);
            drawContext.getMatrices().scale(floatValue, floatValue);
            drawContext.getMatrices().translate(-floatValue2, -floatValue3);
            this.litka$buttonScaled = true;
         }
      }
   }

   @Unique
   private SpringSpec litka$buttonSpring(Animations animations2) {
      SpringSpec springSpec = SpringSpec.resolve11();
      float floatValue4 = animations2.measure();
      return new SpringSpec(springSpec.getFloatValue() * floatValue4, springSpec.getFloatValue2(), springSpec.getFloatValue3(), springSpec.getFloatValue4());
   }

   @Inject(
      method = {"render"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/widget/ClickableWidget;renderWidget(Lnet/minecraft/client/gui/DrawContext;IIF)V",
         shift = Shift.AFTER
      )}
   )
   private void litka$postRenderWidget(DrawContext drawContext, int i, int j, float f, CallbackInfo callbackInfo) {
      if (this.litka$buttonScaled) {
         drawContext.getMatrices().popMatrix();
         this.litka$buttonScaled = false;
      }
   }
}
