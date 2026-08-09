package org.wild.mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.metaculture.protection.ProxyScreen;

@Mixin({MultiplayerScreen.class})
public class MultiplayerScreenMixin extends Screen {
   protected MultiplayerScreenMixin(Text text) {
      super(text);
   }

   @Inject(
      method = {"init"},
      at = {@At("RETURN")}
   )
   private void addProxyButton(CallbackInfo callbackInfo) {
      byte byteValue = 80;
      byte byteValue2 = 20;
      int intValue = this.width - byteValue - 5;
      byte byteValue3 = 5;
      this.addDrawableChild(
         ButtonWidget.builder(Text.literal("Proxy"), buttonWidget -> this.client.setScreen(new ProxyScreen(this))).dimensions(intValue, byteValue3, byteValue, byteValue2).build()
      );
   }
}
