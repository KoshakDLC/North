package org.wild.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.ingame.RecipeBookScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.wild.mixin.acceser.HandledScreenAccessor;
import ru.metaculture.protection.AhHelper;
import ru.metaculture.protection.Animations;
import ru.metaculture.protection.AutoBuy;
import ru.metaculture.protection.LockSlots;
import ru.metaculture.protection.DiscountSliderWidget;
import ru.metaculture.protection.UnHook;
import ru.metaculture.protection.WildClient;

@Mixin({HandledScreen.class})
public abstract class HandledScreenMixin extends Screen {
   @Shadow
   protected int x;
   @Shadow
   protected int y;
   @Shadow
   protected int backgroundWidth;
   @Shadow
   protected int backgroundHeight;
   @Unique
   private static final int WILD_AUTOPARSE_CONTROL_WIDTH = 110;
   @Unique
   private static final int WILD_AUTOPARSE_CONTROL_HEIGHT = 20;
   @Unique
   private static final int WILD_AUTOPARSE_CONTROL_GAP = 4;
   @Unique
   private ButtonWidget wild$autoParseButton;
   @Unique
   private DiscountSliderWidget wild$parseDiscountSlider;
   @Unique
   private static final int WILD_QUICK_BUTTON_HEIGHT = 20;
   @Unique
   private static final int WILD_QUICK_BUTTON_GAP = 4;
   @Unique
   private ButtonWidget wild$dropInventoryButton;
   @Unique
   private ButtonWidget wild$takeAllButton;
   @Unique
   private ButtonWidget wild$depositAllButton;
   @Unique
   private ButtonWidget wild$dropContainerButton;

   protected HandledScreenMixin(Text text) {
      super(text);
   }

   @Unique
   private boolean litka$shouldAnimate(Animations animations) {
      return animations != null && animations.check2(this);
   }

   @Unique
   private boolean litka$isRecipeBookScreen() {
      return (Object)this instanceof RecipeBookScreen;
   }

   @Unique
   private void litka$applyScale(DrawContext drawContext, Animations animations2) {
      float floatValue = animations2.measure3(this);
      drawContext.getMatrices().pushMatrix();
      float floatValue2 = drawContext.getScaledWindowWidth() / 2.0F;
      float floatValue3 = drawContext.getScaledWindowHeight() / 2.0F;
      drawContext.getMatrices().translate(floatValue2, floatValue3);
      drawContext.getMatrices().scale(floatValue, floatValue);
      drawContext.getMatrices().translate(-floatValue2, -floatValue3);
   }

   @Inject(
      method = {"renderBackground"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/screen/ingame/HandledScreen;drawBackground(Lnet/minecraft/client/gui/DrawContext;FII)V"
      )}
   )
   private void litka$preDrawBackground(DrawContext drawContext, int i, int j, float f, CallbackInfo callbackInfo) {
      Animations animations3 = WildClient.INSTANCE.moduleManager.getModule(Animations.class);
      if (this.litka$shouldAnimate(animations3)) {
         this.litka$applyScale(drawContext, animations3);
      }
   }

   @Inject(
      method = {"renderBackground"},
      at = {@At("TAIL")}
   )
   private void litka$postDrawBackground(DrawContext drawContext, int i, int j, float f, CallbackInfo callbackInfo) {
      Animations animations4 = WildClient.INSTANCE.moduleManager.getModule(Animations.class);
      if (this.litka$shouldAnimate(animations4)) {
         drawContext.getMatrices().popMatrix();
      }
   }

   @Inject(
      method = {"renderMain"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/screen/Screen;render(Lnet/minecraft/client/gui/DrawContext;IIF)V",
         shift = Shift.AFTER
      )}
   )
   private void litka$preRenderForeground(DrawContext drawContext, int i, int j, float f, CallbackInfo callbackInfo) {
      Animations animations5 = WildClient.INSTANCE.moduleManager.getModule(Animations.class);
      if (!this.litka$isRecipeBookScreen() && this.litka$shouldAnimate(animations5)) {
         this.litka$applyScale(drawContext, animations5);
      }
   }

   @Inject(
      method = {"renderMain"},
      at = {@At("TAIL")}
   )
   private void litka$postRenderForeground(DrawContext drawContext, int i, int j, float f, CallbackInfo callbackInfo) {
      Animations animations6 = WildClient.INSTANCE.moduleManager.getModule(Animations.class);
      if (!this.litka$isRecipeBookScreen() && this.litka$shouldAnimate(animations6)) {
         drawContext.getMatrices().popMatrix();
      }
   }

   @Inject(
      method = {"close"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void litka$animateClose(CallbackInfo callbackInfo) {
      if (WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null) {
         Animations animations7 = WildClient.INSTANCE.moduleManager.getModule(Animations.class);
         if (this.litka$shouldAnimate(animations7) && !animations7.isFlag4()) {
            animations7.invoke4(this);
            callbackInfo.cancel();
         }
      }
   }

   @Inject(
      method = {"removed"},
      at = {@At("HEAD")}
   )
   private void litka$onClose(CallbackInfo callbackInfo) {
      Animations animations8 = WildClient.INSTANCE.moduleManager.getModule(Animations.class);
      if (animations8 != null) {
         animations8.invoke5();
      }

      AhHelper.ITEMS.clear();
   }

   @Inject(
      method = {"drawSlot"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void litka$onDrawSlot(DrawContext drawContext, Slot slot, CallbackInfo callbackInfo) {
      if (AhHelper.check4((HandledScreen<?>)(Object)this, slot)) {
         callbackInfo.cancel();
      } else {
         if (AhHelper.pokazSamyhDeshevyhPredmetov.isEnabled() && AhHelper.ITEMS.contains(slot.id)) {
            int intValue = slot.x;
            int intValue2 = slot.y;
            drawContext.fill(intValue, intValue2, intValue + 16, intValue2 + 16, 1610678016);
         }
      }
   }

   @Inject(
      method = {"onMouseClick(Lnet/minecraft/screen/slot/Slot;IILnet/minecraft/screen/slot/SlotActionType;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void wild$blockFilteredAuctionSlotClick(Slot slot, int i, int j, SlotActionType slotActionType, CallbackInfo callbackInfo) {
      if (AhHelper.check4((HandledScreen<?>)(Object)this, slot)) {
         callbackInfo.cancel();
      } else {
         if (this.wild$isLockedHotbarThrow(slot, slotActionType)) {
            callbackInfo.cancel();
         }
      }
   }

   @Inject(
      method = {"onMouseClick(Lnet/minecraft/screen/slot/Slot;Lnet/minecraft/screen/slot/SlotActionType;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void wild$blockFilteredAuctionQuickMove(Slot slot, SlotActionType slotActionType, CallbackInfo callbackInfo) {
      if (AhHelper.check4((HandledScreen<?>)(Object)this, slot)) {
         callbackInfo.cancel();
      }
   }

   @Inject(
      method = {"getSlotAt"},
      at = {@At("RETURN")},
      cancellable = true
   )
   private void wild$excludeFilteredAuctionSlot(double d, double e, CallbackInfoReturnable<Slot> callbackInfoReturnable) {
      Slot slot2 = (Slot)callbackInfoReturnable.getReturnValue();
      if (AhHelper.check4((HandledScreen<?>)(Object)this, slot2)) {
         callbackInfoReturnable.setReturnValue(null);
      }
   }

   @Inject(
      method = {"drawMouseoverTooltip"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void wild$hideFilteredAuctionTooltip(DrawContext drawContext, int i, int j, CallbackInfo callbackInfo) {
      if (this.wild$isFilteredFocusedSlot()) {
         callbackInfo.cancel();
      }
   }

   @Inject(
      method = {"drawSlotHighlightBack"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void wild$hideFilteredAuctionBackHighlight(DrawContext drawContext, CallbackInfo callbackInfo) {
      if (this.wild$isFilteredFocusedSlot()) {
         callbackInfo.cancel();
      }
   }

   @Inject(
      method = {"drawSlotHighlightFront"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void wild$hideFilteredAuctionFrontHighlight(DrawContext drawContext, CallbackInfo callbackInfo) {
      if (this.wild$isFilteredFocusedSlot()) {
         callbackInfo.cancel();
      }
   }

   @Unique
   private boolean wild$isFilteredFocusedSlot() {
      Slot slot3 = ((HandledScreenAccessor)(Object)this).litka$getFocusedSlot();
      return AhHelper.check4((HandledScreen<?>)(Object)this, slot3);
   }

   @Inject(
      method = {"init"},
      at = {@At("TAIL")}
   )
   private void wild$initAutoParseControls(CallbackInfo callbackInfo) {
      if (!UnHook.active) {
         AutoBuy autoBuy = this.wild$getAutoBuy();
         if (autoBuy != null && this.wild$isAuctionContainer()) {
            int intValue3 = this.x + this.backgroundWidth + 4;
            int intValue4 = this.y;
            int intValue5 = intValue4 + 20 + 4;
            this.wild$autoParseButton = ButtonWidget.builder(this.wild$autoParseText(autoBuy), buttonWidget -> {
               autoBuy.invoke62();
               buttonWidget.setMessage(this.wild$autoParseText(autoBuy));
               if (this.wild$parseDiscountSlider != null) {
                  this.wild$parseDiscountSlider.invoke();
               }
            }).dimensions(intValue3, intValue4, 110, 20).build();
            this.addDrawableChild(this.wild$autoParseButton);
            this.wild$parseDiscountSlider = new DiscountSliderWidget(autoBuy, intValue3, intValue5, 110, 20);
            this.addDrawableChild(this.wild$parseDiscountSlider);
         }
      }
   }

   @Inject(
      method = {"init"},
      at = {@At("TAIL")}
   )
   private void wild$initQuickContainerControls(CallbackInfo callbackInfo) {
      if (!UnHook.active) {
         if ((Object)this instanceof InventoryScreen) {
            byte byteValue = 124;
            int intValue6 = this.x + this.backgroundWidth / 2 - byteValue / 2;
            int intValue7 = this.wild$controlsY();
            this.wild$dropInventoryButton = ButtonWidget.builder(Text.literal("Выбросить все"), buttonWidget -> this.wild$dropInventoryItems())
               .dimensions(intValue6, intValue7, byteValue, 20)
               .build();
            this.addDrawableChild(this.wild$dropInventoryButton);
         } else if (this.wild$isQuickContainer() && !this.wild$isAuctionContainer()) {
            byte byteValue2 = 82;
            int intValue8 = this.x + this.backgroundWidth + 4;
            int intValue9 = this.y;
            this.wild$takeAllButton = ButtonWidget.builder(Text.literal("Забрать все"), buttonWidget -> this.wild$takeAllFromContainer())
               .dimensions(intValue8, intValue9, byteValue2, 20)
               .build();
            this.wild$depositAllButton = ButtonWidget.builder(Text.literal("Сложить"), buttonWidget -> this.wild$depositAllToContainer())
               .dimensions(intValue8, intValue9 + 20 + 4, byteValue2, 20)
               .build();
            this.wild$dropContainerButton = ButtonWidget.builder(Text.literal("Выбросить все"), buttonWidget -> this.wild$dropAllFromContainer())
               .dimensions(intValue8, intValue9 + 48, byteValue2, 20)
               .build();
            this.addDrawableChild(this.wild$takeAllButton);
            this.addDrawableChild(this.wild$depositAllButton);
            this.addDrawableChild(this.wild$dropContainerButton);
         }
      }
   }

   @Inject(
      method = {"tick"},
      at = {@At("TAIL")}
   )
   private void wild$tickAutoParseControls(CallbackInfo callbackInfo) {
      AutoBuy autoBuy2 = this.wild$getAutoBuy();
      boolean flag = !UnHook.active && autoBuy2 != null && this.wild$isAuctionContainer();
      if (this.wild$autoParseButton != null) {
         this.wild$autoParseButton.visible = flag;
         this.wild$autoParseButton.active = flag;
         if (flag) {
            this.wild$autoParseButton.setMessage(this.wild$autoParseText(autoBuy2));
         }
      }

      if (this.wild$parseDiscountSlider != null) {
         this.wild$parseDiscountSlider.visible = flag;
         this.wild$parseDiscountSlider.active = flag;
         if (flag) {
            this.wild$parseDiscountSlider.invoke();
         }
      }

      boolean flag2 = !UnHook.active;
      if (this.wild$dropInventoryButton != null) {
         this.wild$dropInventoryButton.visible = flag2;
         this.wild$dropInventoryButton.active = flag2;
      }

      if (this.wild$takeAllButton != null) {
         this.wild$takeAllButton.visible = flag2;
         this.wild$takeAllButton.active = flag2;
      }

      if (this.wild$depositAllButton != null) {
         this.wild$depositAllButton.visible = flag2;
         this.wild$depositAllButton.active = flag2;
      }

      if (this.wild$dropContainerButton != null) {
         this.wild$dropContainerButton.visible = flag2;
         this.wild$dropContainerButton.active = flag2;
      }
   }

   @Unique
   private AutoBuy wild$getAutoBuy() {
      return WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null ? WildClient.INSTANCE.moduleManager.getModule(AutoBuy.class) : null;
   }

   @Unique
   private boolean wild$isAuctionContainer() {
      if ((Object)this instanceof GenericContainerScreen genericContainerScreen) {
         AutoBuy autoBuy3 = this.wild$getAutoBuy();
         return autoBuy3 != null && autoBuy3.rezhimServera.is("HolyWorld") ? autoBuy3.check22(genericContainerScreen) : AhHelper.check(genericContainerScreen);
      } else {
         return false;
      }
   }

   @Unique
   private Text wild$autoParseText(AutoBuy autoBuy4) {
      return Text.literal("AutoParse: " + (autoBuy4.autoParse.isEnabled() ? "ON" : "OFF"));
   }

   @Unique
   private int wild$controlsY() {
      int intValue10 = this.y - 20 - 4;
      return intValue10 >= 4 ? intValue10 : this.y + this.backgroundHeight + 4;
   }

   @Unique
   private int wild$centeredControlsX(int i) {
      int intValue11 = this.x + this.backgroundWidth / 2 - i / 2;
      int intValue12 = this.width - i - 4;
      return Math.max(4, Math.min(intValue11, intValue12));
   }

   @Unique
   private void wild$dropInventoryItems() {
      ScreenHandler screenHandler2 = this.wild$screenHandler();
      if (this.wild$canInteract(screenHandler2)) {
         for (Slot slot4 : screenHandler2.slots) {
            if (this.wild$isPlayerInventorySlot(slot4) && slot4.hasStack() && slot4.canTakeItems(this.client.player)) {
               this.client.interactionManager.clickSlot(screenHandler2.syncId, slot4.id, 1, SlotActionType.THROW, this.client.player);
            }
         }
      }
   }

   @Unique
   private void wild$takeAllFromContainer() {
      ScreenHandler screenHandler3 = this.wild$screenHandler();
      if (this.wild$canInteract(screenHandler3) && this.wild$isQuickContainer(screenHandler3)) {
         int intValue13 = this.wild$containerSlotCount(screenHandler3);

         for (int intValue14 = 0; intValue14 < intValue13; intValue14++) {
            Slot slot5 = screenHandler3.getSlot(intValue14);
            if (slot5.hasStack() && slot5.canTakeItems(this.client.player)) {
               this.client.interactionManager.clickSlot(screenHandler3.syncId, intValue14, 0, SlotActionType.QUICK_MOVE, this.client.player);
            }
         }
      }
   }

   @Unique
   private void wild$depositAllToContainer() {
      ScreenHandler screenHandler4 = this.wild$screenHandler();
      if (this.wild$canInteract(screenHandler4)) {
         for (Slot slot6 : screenHandler4.slots) {
            if (this.wild$isPlayerInventorySlot(slot6) && slot6.hasStack()) {
               this.client.interactionManager.clickSlot(screenHandler4.syncId, slot6.id, 0, SlotActionType.QUICK_MOVE, this.client.player);
            }
         }
      }
   }

   @Unique
   private void wild$dropAllFromContainer() {
      ScreenHandler screenHandler5 = this.wild$screenHandler();
      if (this.wild$canInteract(screenHandler5) && this.wild$isQuickContainer(screenHandler5)) {
         int intValue15 = this.wild$containerSlotCount(screenHandler5);

         for (int intValue16 = 0; intValue16 < intValue15; intValue16++) {
            Slot slot7 = screenHandler5.getSlot(intValue16);
            if (slot7.hasStack() && slot7.canTakeItems(this.client.player)) {
               this.client.interactionManager.clickSlot(screenHandler5.syncId, intValue16, 1, SlotActionType.THROW, this.client.player);
            }
         }
      }
   }

   @Unique
   private boolean wild$canInteract(ScreenHandler screenHandler) {
      return this.client != null && this.client.player != null && this.client.interactionManager != null && screenHandler != null;
   }

   @Unique
   private boolean wild$isLockedHotbarThrow(Slot slot, SlotActionType slotActionType) {
      if (slotActionType != SlotActionType.THROW || !this.wild$isPlayerInventorySlot(slot)) {
         return false;
      } else if (WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null) {
         LockSlots lockSlots = WildClient.INSTANCE.moduleManager.getModule(LockSlots.class);
         return lockSlots != null && lockSlots.enabled && lockSlots.check(slot.getIndex());
      } else {
         return false;
      }
   }

   @Unique
   private boolean wild$isPlayerInventorySlot(Slot slot) {
      return slot != null && this.client != null && this.client.player != null && slot.inventory == this.client.player.getInventory();
   }

   @Unique
   private ScreenHandler wild$screenHandler() {
      return ((HandledScreen)(Object)this).getScreenHandler();
   }

   @Unique
   private boolean wild$isQuickContainer() {
      return this.wild$isQuickContainer(this.wild$screenHandler());
   }

   @Unique
   private boolean wild$isQuickContainer(ScreenHandler screenHandler) {
      return screenHandler instanceof GenericContainerScreenHandler || screenHandler instanceof ShulkerBoxScreenHandler;
   }

   @Unique
   private int wild$containerSlotCount(ScreenHandler screenHandler) {
      int intValue17;
      if (screenHandler instanceof GenericContainerScreenHandler genericContainerScreenHandler) {
         intValue17 = genericContainerScreenHandler.getRows();
      } else {
         if (!(screenHandler instanceof ShulkerBoxScreenHandler)) {
            return 0;
         }

         intValue17 = 3;
      }

      int intValue18 = screenHandler.slots.size();
      return Math.max(0, Math.min(intValue17 * 9, intValue18));
   }
}
