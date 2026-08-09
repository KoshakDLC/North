package org.wild.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.metaculture.protection.AutoBuy;
import ru.metaculture.protection.UnHook;
import ru.metaculture.protection.WildClient;

@Mixin({HandledScreen.class})
public abstract class ChestScreenMixin extends Screen {
   @Shadow
   protected int x;
   @Shadow
   protected int y;
   @Shadow
   protected int backgroundWidth;
   @Unique
   private ButtonWidget autoBuyButton;

   protected ChestScreenMixin(Text text) {
      super(text);
   }

   @Inject(
      method = {"init"},
      at = {@At("TAIL")}
   )
   private void initAutoBuyButtons(CallbackInfo callbackInfo) {
      if (!UnHook.active) {
         if ((Object)this instanceof GenericContainerScreen) {
            String text2 = this.getTitle().getString();
            if (text2 != null && (text2.contains("Аукцион") || text2.contains("Auction") || text2.contains("Поиск: "))) {
               AutoBuy autoBuy = this.getAutoBuyModule();
               if (autoBuy != null) {
                  byte byteValue = 5;
                  byte byteValue2 = 100;
                  byte byteValue3 = 20;
                  int intValue = this.x + this.backgroundWidth / 2 - byteValue2 / 2;
                  int intValue2 = this.y - byteValue3 - byteValue;
                  this.autoBuyButton = ButtonWidget.builder(this.getButtonText(autoBuy), buttonWidget -> {
                     autoBuy.toggle();
                     buttonWidget.setMessage(this.getButtonText(autoBuy));
                  }).dimensions(intValue, intValue2, byteValue2, byteValue3).build();
                  this.addDrawableChild(this.autoBuyButton);
               }
            }
         }
      }
   }

   @Inject(
      method = {"render"},
      at = {@At("TAIL")}
   )
   private void updateButtonStates(DrawContext drawContext, int i, int j, float f, CallbackInfo callbackInfo) {
      if ((Object)this instanceof GenericContainerScreen) {
         if (this.autoBuyButton != null) {
            this.autoBuyButton.visible = !UnHook.active;
            this.autoBuyButton.active = !UnHook.active;
         }

         if (!UnHook.active) {
            AutoBuy autoBuy2 = this.getAutoBuyModule();
            if (autoBuy2 != null && this.autoBuyButton != null) {
               this.autoBuyButton.setMessage(this.getButtonText(autoBuy2));
            }
         }
      }
   }

   @Inject(
      method = {"tick"},
      at = {@At("HEAD")}
   )
   private void onTick(CallbackInfo callbackInfo) {
      if (!UnHook.active) {
         if ((Object)this instanceof GenericContainerScreen) {
            String text3 = this.getTitle().getString();
            if (text3 != null && (text3.contains("Аукцион") || text3.contains("Auction") || text3.contains("Поиск: "))) {
               AutoBuy autoBuy3 = this.getAutoBuyModule();
               if (autoBuy3 != null && autoBuy3.enabled && autoBuy3.autoParse.isEnabled()) {
                  autoBuy3.invoke34();
               }
            }
         }
      }
   }

   @Unique
   private Text getButtonText(AutoBuy autoBuy4) {
      String text4 = autoBuy4.enabled ? "§aON" : "§cOFF";
      return Text.of("AutoBuy: " + text4);
   }

   @Unique
   private AutoBuy getAutoBuyModule() {
      return WildClient.INSTANCE.moduleManager.getModule(AutoBuy.class);
   }
}
