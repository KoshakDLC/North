package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "InvMove",
   description = "Позволяет ходить с открытым инвентарём и меню клиента",
   category = Category.Movement
)
public class InvMove extends Module {
   public static ModeSetting rezhim = new ModeSetting("Режим", "Grim", "Grim", "Vanilla", "FunTime");
   public static NumberSetting zaderzhkaZakrytiya = new NumberSetting("Задержка закрытия", 100.0F, 0.0F, 300.0F, 10.0F, false);
   private final List<Packet<?>> items = new ArrayList<>();
   public boolean flag = false;
   private boolean flag2 = false;
   private boolean flag3 = false;
   private long timestamp = 0L;
   private static long timestamp2 = 0L;

   public InvMove() {
      this.addSettings(new Setting[]{rezhim, zaderzhkaZakrytiya});
   }

   @EventHandler
   public void onClientTick(ClientTickEvent clientTickEvent) {
      if (this.flag) {
         InputUtils.getINSTANCE().invoke("GuiMove");
      } else {
         InputUtils.getINSTANCE().invoke2("GuiMove");
      }

      if (this.flag2 && System.currentTimeMillis() >= this.timestamp) {
         this.flag2 = false;
         this.invoke3();
         this.flag = false;
      }

      if (CLIENT.player != null) {
         if (rezhim.is("Vanilla")) {
            this.invoke7();
         } else if (rezhim.is("Grim")) {
            this.invoke6();
         } else if (rezhim.is("FunTime")) {
            if (!MovementUtils.check() && !this.items.isEmpty() && CLIENT.currentScreen instanceof InventoryScreen) {
               this.invoke();
            }

            this.invoke8();
         }
      }
   }

   @EventHandler
   public void onPacket(PacketEvent packetEvent) {
      if (packetEvent.check() && !this.flag3 && CLIENT.currentScreen instanceof InventoryScreen) {
         if (!(packetEvent.getPacket() instanceof CloseHandledScreenC2SPacket)
            || !rezhim.is("FunTime")
            || this.items.isEmpty() && !MovementUtils.check()) {
            if (packetEvent.getPacket() instanceof ClickSlotC2SPacket clickSlotC2SPacket2) {
               invoke5();
               if (!rezhim.is("Grim") || !CLIENT.player.isSprinting() && !CLIENT.player.isJumping()) {
                  if (rezhim.is("FunTime") && MovementUtils.check() && (!this.items.isEmpty() || this.check(clickSlotC2SPacket2))) {
                     this.items.add(clickSlotC2SPacket2);
                     packetEvent.invalidate();
                  }
               } else {
                  this.items.add(clickSlotC2SPacket2);
                  packetEvent.invalidate();
               }
            }
         } else {
            packetEvent.invalidate();
            this.timestamp = System.currentTimeMillis() + this.compute2();
            this.flag2 = true;
            this.flag = true;
         }
      }
   }

   @EventHandler
   public void onScreenHandlerOpen(ScreenHandlerOpenEvent screenHandlerOpenEvent) {
      if (CLIENT.currentScreen instanceof InventoryScreen) {
         if (rezhim.is("Grim")) {
            if (!CLIENT.player.isSprinting()) {
               this.invoke4();
               return;
            }

            screenHandlerOpenEvent.invalidate();
            this.flag = false;
            this.timestamp = System.currentTimeMillis() + this.compute2();
            this.flag2 = true;
         } else if (rezhim.is("FunTime")) {
            if (this.items.isEmpty() && !MovementUtils.check()) {
               this.invoke4();
               this.flag = false;
               this.flag2 = false;
            } else {
               screenHandlerOpenEvent.invalidate();
               this.timestamp = System.currentTimeMillis() + this.compute2();
               this.flag2 = true;
               this.flag = true;
            }
         }
      }
   }

   private void invoke() {
      if (!this.items.isEmpty()) {
         invoke5();
         this.flag3 = true;

         try {
            for (Packet packet : this.items) {
               if (CLIENT.getNetworkHandler() != null) {
                  CLIENT.getNetworkHandler().sendPacket(packet);
               }
            }
         } finally {
            this.flag3 = false;
            this.items.clear();
         }
      }
   }

   private void invoke2() {
      if (CLIENT.player != null) {
         CLIENT.player.closeScreen();
      }
   }

   private void invoke3() {
      this.invoke();
      this.invoke4();
      this.invoke2();
   }

   private void invoke4() {
      if (CLIENT.player != null && CLIENT.interactionManager != null) {
         ScreenHandler screenHandler2 = CLIENT.player.currentScreenHandler;
         if (screenHandler2 != null && !screenHandler2.getCursorStack().isEmpty()) {
            int intValue = this.compute(screenHandler2);
            if (intValue != -1) {
               boolean flag = this.flag3;
               this.flag3 = true;

               try {
                  invoke5();
                  CLIENT.interactionManager.clickSlot(screenHandler2.syncId, intValue, 0, SlotActionType.PICKUP, CLIENT.player);
               } finally {
                  this.flag3 = flag;
               }
            }
         }
      }
   }

   private int compute(ScreenHandler screenHandler) {
      ItemStack itemStack = screenHandler.getCursorStack();
      int intValue2 = -1;

      for (Slot slot : screenHandler.slots) {
         if (slot.inventory == CLIENT.player.getInventory()) {
            ItemStack itemStack2 = slot.getStack();
            if (itemStack2.isEmpty()) {
               if (intValue2 == -1) {
                  intValue2 = slot.id;
               }
            } else if (ItemStack.areItemsAndComponentsEqual(itemStack2, itemStack) && itemStack2.getCount() + itemStack.getCount() <= itemStack2.getMaxCount()) {
               return slot.id;
            }
         }
      }

      return intValue2;
   }

   private long compute2() {
      return (long)zaderzhkaZakrytiya.getValue();
   }

   private boolean check(ClickSlotC2SPacket clickSlotC2SPacket) {
      return !clickSlotC2SPacket.modifiedStacks().isEmpty();
   }

   private static void invoke5() {
      timestamp2 = System.currentTimeMillis();
   }

   public static boolean check2() {
      return CLIENT.currentScreen instanceof InventoryScreen && System.currentTimeMillis() - timestamp2 < 350L;
   }

   private void invoke6() {
      KeyBinding[] keyBindings2 = new KeyBinding[]{
         CLIENT.options.forwardKey,
         CLIENT.options.backKey,
         CLIENT.options.leftKey,
         CLIENT.options.rightKey,
         CLIENT.options.jumpKey,
         CLIENT.options.sprintKey
      };
      if (this.check3()) {
         this.flag = false;
         this.invoke9(keyBindings2);
      } else if (this.flag2) {
         this.flag = true;
      } else {
         if (!(CLIENT.currentScreen instanceof InventoryScreen)) {
            this.flag = false;
         }

         if (CLIENT.currentScreen instanceof InventoryScreen) {
            this.invoke9(keyBindings2);
         }
      }
   }

   private void invoke7() {
      if (!(CLIENT.currentScreen instanceof InventoryScreen) && !this.check3()) {
         this.flag = false;
      }

      KeyBinding[] keyBindings3 = new KeyBinding[]{
         CLIENT.options.forwardKey,
         CLIENT.options.backKey,
         CLIENT.options.leftKey,
         CLIENT.options.rightKey,
         CLIENT.options.jumpKey,
         CLIENT.options.sprintKey
      };
      if (CLIENT.currentScreen instanceof InventoryScreen || this.check3()) {
         this.flag = false;
         this.invoke9(keyBindings3);
      }
   }

   private void invoke8() {
      KeyBinding[] keyBindings4 = new KeyBinding[]{
         CLIENT.options.forwardKey,
         CLIENT.options.backKey,
         CLIENT.options.leftKey,
         CLIENT.options.rightKey,
         CLIENT.options.jumpKey,
         CLIENT.options.sprintKey
      };
      if (this.check3()) {
         this.flag = false;
         this.invoke9(keyBindings4);
      } else if (this.flag2) {
         this.flag = true;
      } else {
         if (!(CLIENT.currentScreen instanceof InventoryScreen)) {
            this.flag = false;
         }

         if (CLIENT.currentScreen instanceof InventoryScreen) {
            this.invoke9(keyBindings4);
         }
      }
   }

   private boolean check3() {
      return CLIENT.currentScreen instanceof ClickGuiScreen || CLIENT.currentScreen instanceof ModernClickGuiScreen;
   }

   private void invoke9(KeyBinding[] keyBindings) {
      long longValue = MinecraftClient.getInstance().getWindow().getHandle();
      ModernClickGuiScreen modernClickGuiScreen = CLIENT.currentScreen instanceof ModernClickGuiScreen ? (ModernClickGuiScreen)CLIENT.currentScreen : null;
      boolean flag2 = modernClickGuiScreen != null && modernClickGuiScreen.getClickGui().check11();

      for (KeyBinding keyBinding : keyBindings) {
         if (flag2) {
            keyBinding.setPressed(false);
         } else {
            int intValue3 = keyBinding.getDefaultKey().getCode();
            boolean flag3 = InputUtil.isKeyPressed(longValue, intValue3);
            keyBinding.setPressed(flag3);
         }
      }
   }

   @Override
   public void onDisable() {
      this.flag = false;
      this.flag2 = false;
      this.flag3 = false;
      InputUtils.getINSTANCE().invoke2("GuiMove");
      this.items.clear();
      super.onDisable();
   }
}
