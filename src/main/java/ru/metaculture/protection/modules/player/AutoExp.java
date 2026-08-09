package ru.metaculture.protection;

import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket.Mode;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import org.lwjgl.glfw.GLFW;
import org.wild.mixin.acceser.ClientPlayerInteractionManagerAccessor;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "AutoExp",
   description = "Автоматически использует пузырьки опыта",
   category = Category.Player
)
public class AutoExp extends Module {
   private final KeybindSetting klavishaOpyta = new KeybindSetting("Клавиша опыта", -1, true);
   private final NumberSetting zaderzhka = new NumberSetting("Задержка", 80.0F, 20.0F, 300.0F, 10.0F, false);
   private final BooleanSetting tolkoIznoshennoe = new BooleanSetting("Только изношенное", false);
   private final NumberSetting prochnostDo = new NumberSetting("Прочность до", 95.0F, 5.0F, 100.0F, 5.0F, false)
      .setVisibilityCondition(() -> !this.tolkoIznoshennoe.isEnabled());
   private final DualTimer dualTimer = new DualTimer();
   private int intValue = 0;
   private int intValue2 = 0;
   private int intValue3 = -1;
   private int intValue4 = -1;
   private Hand hand = Hand.MAIN_HAND;

   public AutoExp() {
      this.addSettings(new Setting[]{this.klavishaOpyta, this.zaderzhka, this.tolkoIznoshennoe, this.prochnostDo});
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player == null || CLIENT.interactionManager == null || this.klavishaOpyta.getKeyCode() == -1) {
         this.invoke6();
      } else if (this.intValue > 0) {
         if (this.intValue2 > 0) {
            this.invoke7();
            this.intValue2--;
         } else {
            this.invoke2();
         }
      } else if (this.check() && this.dualTimer.check5((long)this.zaderzhka.getValue())) {
         if (!this.tolkoIznoshennoe.isEnabled() || this.check2()) {
            this.invoke();
         }
      }
   }

   private void invoke() {
      this.intValue4 = CLIENT.player.getInventory().getSelectedSlot();
      if (CLIENT.player.getOffHandStack().isOf(Items.EXPERIENCE_BOTTLE)) {
         this.hand = Hand.OFF_HAND;
         this.intValue = CLIENT.currentScreen instanceof InventoryScreen ? 1 : 2;
      } else {
         int intValue = this.compute();
         if (intValue != -1) {
            this.intValue3 = intValue;
            this.hand = Hand.MAIN_HAND;
            this.intValue = CLIENT.currentScreen instanceof InventoryScreen ? 1 : (intValue < 9 ? 2 : 4);
         }
      }
   }

   private void invoke2() {
      if (CLIENT.player != null && CLIENT.interactionManager != null) {
         switch (this.intValue) {
            case 1:
               CLIENT.player.closeHandledScreen();
               this.intValue = this.intValue3 >= 9 ? 4 : 2;
               this.intValue2 = 2;
               break;
            case 2:
               this.invoke7();
               this.invoke3();
               this.intValue = 3;
               this.intValue2 = 1;
               break;
            case 3:
               this.invoke4();
               break;
            case 4:
               this.invoke7();
               this.invoke8();
               CLIENT.interactionManager
                  .clickSlot(CLIENT.player.playerScreenHandler.syncId, this.intValue3, this.intValue4, SlotActionType.SWAP, CLIENT.player);
               CLIENT.player.closeHandledScreen();
               this.intValue = 5;
               this.intValue2 = 2;
               break;
            case 5:
               this.invoke7();
               this.invoke3();
               this.intValue = 6;
               this.intValue2 = 2;
               break;
            case 6:
               this.invoke7();
               this.invoke8();
               CLIENT.interactionManager
                  .clickSlot(CLIENT.player.playerScreenHandler.syncId, this.intValue3, this.intValue4, SlotActionType.SWAP, CLIENT.player);
               CLIENT.player.closeHandledScreen();
               this.invoke5();
               break;
            default:
               this.invoke6();
         }
      } else {
         this.invoke6();
      }
   }

   private void invoke3() {
      if (this.hand == Hand.MAIN_HAND && this.intValue3 >= 0 && this.intValue3 < 9) {
         CLIENT.player.getInventory().setSelectedSlot(this.intValue3);
      }

      CLIENT.interactionManager.interactItem(CLIENT.player, this.hand);
      CLIENT.player.swingHand(this.hand);
   }

   private void invoke4() {
      if (this.hand == Hand.MAIN_HAND && this.intValue3 >= 0 && this.intValue3 < 9 && this.intValue4 != this.intValue3) {
         CLIENT.player.getInventory().setSelectedSlot(this.intValue4);
         ((ClientPlayerInteractionManagerAccessor)CLIENT.interactionManager).invokeSyncSelectedSlot();
      }

      this.invoke5();
   }

   private void invoke5() {
      this.dualTimer.invoke();
      this.invoke6();
   }

   private void invoke6() {
      this.intValue = 0;
      this.intValue2 = 0;
      this.intValue3 = -1;
      this.intValue4 = -1;
      this.hand = Hand.MAIN_HAND;
   }

   private void invoke7() {
      Sprint.intValue = 2;
      CLIENT.options.sprintKey.setPressed(false);
      if (CLIENT.player.isSprinting()) {
         CLIENT.player.setSprinting(false);
         if (CLIENT.getNetworkHandler() != null) {
            CLIENT.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(CLIENT.player, Mode.STOP_SPRINTING));
         }
      }
   }

   private void invoke8() {
      if (CLIENT.getNetworkHandler() != null) {
         CLIENT.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(CLIENT.player, Mode.OPEN_INVENTORY));
      }
   }

   private boolean check() {
      if (CLIENT.currentScreen == null) {
         return KeybindSetting.isPressed(this.klavishaOpyta.getKeyCode());
      } else if (CLIENT.currentScreen instanceof InventoryScreen && CLIENT.getWindow() != null) {
         long longValue = CLIENT.getWindow().getHandle();
         int intValue2 = this.klavishaOpyta.getKeyCode();
         if (intValue2 >= 0) {
            return InputUtil.isKeyPressed(longValue, intValue2);
         } else {
            return intValue2 <= -100 ? GLFW.glfwGetMouseButton(longValue, -intValue2 - 100) == 1 : false;
         }
      } else {
         return false;
      }
   }

   private int compute() {
      for (int intValue3 = 0; intValue3 < 36; intValue3++) {
         ItemStack itemStack2 = CLIENT.player.getInventory().getStack(intValue3);
         if (!itemStack2.isEmpty() && itemStack2.isOf(Items.EXPERIENCE_BOTTLE)) {
            return intValue3;
         }
      }

      return -1;
   }

   private boolean check2() {
      for (int intValue4 = 0; intValue4 < CLIENT.player.getInventory().size(); intValue4++) {
         if (this.check3(CLIENT.player.getInventory().getStack(intValue4))) {
            return true;
         }
      }

      return this.check3(CLIENT.player.getOffHandStack());
   }

   private boolean check3(ItemStack itemStack) {
      if (!itemStack.isEmpty() && itemStack.isDamageable()) {
         int intValue5 = itemStack.getMaxDamage();
         if (intValue5 <= 0) {
            return false;
         } else {
            int intValue6 = intValue5 - itemStack.getDamage();
            float floatValue = intValue6 * 100.0F / intValue5;
            return floatValue <= this.prochnostDo.getValue();
         }
      } else {
         return false;
      }
   }

   @Override
   public void onDisable() {
      this.invoke6();
      super.onDisable();
   }
}
