package ru.metaculture.protection;

import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.consume.UseAction;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "NoSlow",
   description = "Убирает замедление при использовании предметов",
   category = Category.Movement,
   riskLevels = {ModuleRiskLevel.RISKY, ModuleRiskLevel.GRIM}
)
public class NoSlow extends Module {
   private static final Logger LOGGER = LogManager.getLogger("NoSlow");
   private static final int INT_VALUE = 1;
   private static final String NOSLOW_FT_SNOW_CROSSBOW = "NoSlow_FT-Snow_Crossbow";
   private static boolean flag;
   private static ItemStack itemStack = ItemStack.EMPTY;
   public static ModeSetting rezhim = new ModeSetting("Режим", "Grim", "Grim", "Grim Tick", "Grim V2", "FT");
   public static BooleanSetting arbalet = new BooleanSetting("Арбалет", true).visibleWhen(() -> !rezhim.is("FT"));
   public static BooleanSetting tochnyyStop = new BooleanSetting("Точный стоп", true)
      .visibleWhen(() -> !rezhim.is("FT-Snow") || !arbalet.isEnabled());
   public static NumberSetting zaderzhkaSvapa = new NumberSetting("Задержка свапа", 70.0F, 0.0F, 250.0F, 5.0F, false)
      .setVisibilityCondition(() -> !rezhim.is("FT-Snow") || !arbalet.isEnabled() || !tochnyyStop.isEnabled());
   private float floatValue = 0.0F;
   private int intValue = -1;
   private int intValue2;
   private boolean flag2;
   private final DualTimer dualTimer = new DualTimer();
   private NoSlow.NoSlowState noSlowState = NoSlow.NoSlowState.IDLE;

   public NoSlow() {
      this.addSettings(new Setting[]{rezhim});
   }

   @EventHandler
   public void onClientTick(ClientTickEvent clientTickEvent) {
      if (CLIENT.player != null) {
         if (rezhim.is("Grim Tick") || rezhim.is("Grim V2")) {
            if (CLIENT.player.isUsingItem()) {
               this.floatValue++;
            } else {
               this.floatValue = 0.0F;
            }
         }
      }
   }

   @EventHandler(
      priority = 0
   )
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player == null) {
         this.invoke12("player-missing");
      } else {
         if (rezhim.is("FT-Snow") && arbalet.isEnabled() && tochnyyStop.isEnabled()) {
            this.invoke();
         } else {
            this.invoke11();
         }
      }
   }

   @EventHandler
   public void onSlowdown(SlowdownEvent slowdownEvent) {
      if (CLIENT.player != null) {
         if (rezhim.is("Grim")) {
            if (CLIENT.player.getActiveHand() == Hand.MAIN_HAND) {
               CLIENT.interactionManager.interactItem(CLIENT.player, Hand.OFF_HAND);
            } else {
               CLIENT.interactionManager.interactItem(CLIENT.player, Hand.MAIN_HAND);
            }

            slowdownEvent.invalidate();
         }

         if (rezhim.is("FT")) {
            if (CLIENT.player.isUsingItem() && CLIENT.player.getActiveItem().getItem() instanceof CrossbowItem) {
               slowdownEvent.invalidate();
            }

            if (this.check2()) {
               slowdownEvent.invalidate();
            }
         }

         if (rezhim.is("Grim V2") && CLIENT.player.isUsingItem() && !CLIENT.player.hasVehicle() && this.floatValue >= 1.3F) {
            slowdownEvent.invalidate();
            this.floatValue = 0.26F;
         }

         if (rezhim.is("Grim Tick") && CLIENT.player.isUsingItem() && !CLIENT.player.hasVehicle() && this.floatValue >= 1.2F) {
            slowdownEvent.invalidate();
            this.floatValue = 0.0F;
         }
      }
   }

   private void invoke() {
      if (CLIENT.interactionManager != null && CLIENT.player != null) {
         if (this.noSlowState != NoSlow.NoSlowState.IDLE) {
            this.invoke2();
         } else {
            if (this.check3() && !CLIENT.player.getOffHandStack().isOf(Items.CROSSBOW) && MovementUtils.check()) {
               this.invoke4();
            }
         }
      }
   }

   private void invoke2() {
      if (CLIENT.player != null && CLIENT.interactionManager != null) {
         switch (this.noSlowState) {
            case IDLE:
            default:
               break;
            case PRE_SWAP_STOP:
               this.invoke9();
               if (!this.check()) {
                  return;
               }

               this.invoke8(this.intValue);
               if (!CLIENT.player.getOffHandStack().isOf(Items.CROSSBOW)) {
                  this.invoke18("swap-failed-after-stop", this.intValue);
                  this.invoke13("swap-failed-after-stop");
                  return;
               }

               this.invoke18("swapped-to-offhand", this.intValue);
               this.invoke5();
               break;
            case EATING:
               this.invoke3();
               break;
            case PRE_RESTORE_STOP:
               this.invoke9();
               if (!this.check()) {
                  return;
               }

               if (CLIENT.player.getOffHandStack().isOf(Items.CROSSBOW)) {
                  this.invoke8(this.intValue);
                  this.invoke18("back-swapped-after-stop", this.intValue);
               } else if (this.check6(this.intValue)) {
                  this.invoke18("crossbow-already-at-origin", this.intValue);
               } else {
                  this.invoke18("back-swap-missing-crossbow", this.intValue);
               }

               this.invoke13("restore-finished");
         }
      } else {
         this.invoke13("client-state-missing");
      }
   }

   private void invoke3() {
      if (this.check3()) {
         if (!this.flag2) {
            this.invoke10();
            this.invoke18("eating-confirmed-unlock", this.intValue);
         }

         this.flag2 = true;
         this.intValue2 = 0;
      } else {
         if (!this.flag2) {
            this.invoke9();
         }

         this.invoke14();
         if (!this.flag2 && this.check4()) {
            if (this.intValue2++ < 1) {
               this.invoke5();
               this.invoke18("restart-eating", this.intValue);
            } else {
               this.invoke6("eating-did-not-start");
            }
         } else {
            this.invoke6(this.flag2 ? "eating-finished" : "eating-cancelled");
         }
      }
   }

   private void invoke4() {
      int intValue = this.compute();
      if (intValue == -1) {
         this.invoke18("no-crossbow-found", -1);
      } else {
         invoke16();
         this.invoke15();
         this.intValue = intValue;
         this.noSlowState = NoSlow.NoSlowState.PRE_SWAP_STOP;
         this.invoke7();
         this.flag2 = false;
         this.intValue2 = 0;
         this.invoke9();
         this.invoke18("pre-swap-stop", intValue);
      }
   }

   private void invoke5() {
      if (!this.check4()) {
         this.invoke6("main-hand-food-missing");
      } else {
         CLIENT.options.useKey.setPressed(true);
         CLIENT.interactionManager.interactItem(CLIENT.player, Hand.MAIN_HAND);
         this.invoke10();
         this.noSlowState = NoSlow.NoSlowState.EATING;
         this.invoke18("start-eating", this.intValue);
      }
   }

   private void invoke6(String string) {
      this.invoke15();
      this.noSlowState = NoSlow.NoSlowState.PRE_RESTORE_STOP;
      this.invoke7();
      this.invoke9();
      this.invoke18("pre-back-swap-stop:" + string, this.intValue);
   }

   private void invoke7() {
      this.dualTimer.invoke();
   }

   private boolean check() {
      return tochnyyStop.isEnabled() && this.dualTimer.check11((long)zaderzhkaSvapa.getValue());
   }

   private boolean check2() {
      return !this.check3() ? false : this.noSlowState != NoSlow.NoSlowState.IDLE || CLIENT.player.getOffHandStack().isOf(Items.CROSSBOW);
   }

   private boolean check3() {
      if (CLIENT.player != null && CLIENT.player.isUsingItem() && CLIENT.player.getActiveHand() == Hand.MAIN_HAND) {
         ItemStack itemStack2 = CLIENT.player.getActiveItem();
         return !itemStack2.isEmpty() && itemStack2.getUseAction() == UseAction.EAT;
      } else {
         return false;
      }
   }

   private boolean check4() {
      if (CLIENT.player == null) {
         return false;
      } else {
         ItemStack itemStack3 = CLIENT.player.getMainHandStack();
         return !itemStack3.isEmpty() && itemStack3.getUseAction() == UseAction.EAT;
      }
   }

   private int compute() {
      for (int intValue2 = 0; intValue2 < 9; intValue2++) {
         if (this.check5(intValue2)) {
            return this.compute2(intValue2);
         }
      }

      for (int intValue3 = 9; intValue3 < 36; intValue3++) {
         if (this.check5(intValue3)) {
            return this.compute2(intValue3);
         }
      }

      return -1;
   }

   private boolean check5(int i) {
      return CLIENT.player != null && i >= 0 && i < 36 && CLIENT.player.getInventory().getStack(i).isOf(Items.CROSSBOW);
   }

   private boolean check6(int i) {
      return i >= 36 && i <= 44 ? this.check5(i - 36) : i >= 9 && i < 36 && this.check5(i);
   }

   private int compute2(int i) {
      return i < 9 ? i + 36 : i;
   }

   private void invoke8(int i) {
      if (CLIENT.player != null && CLIENT.interactionManager != null) {
         CLIENT.interactionManager.clickSlot(CLIENT.player.playerScreenHandler.syncId, i, 40, SlotActionType.SWAP, CLIENT.player);
      }
   }

   private void invoke9() {
      if (CLIENT.player != null && CLIENT.world != null) {
         InputUtils.getINSTANCE().invoke("NoSlow_FT-Snow_Crossbow");
         Sprint.intValue = Math.max(Sprint.intValue, 1);
         CLIENT.options.useKey.setPressed(false);
      }
   }

   private void invoke10() {
      if (CLIENT.player != null && CLIENT.world != null) {
         InputUtils.getINSTANCE().invoke2("NoSlow_FT-Snow_Crossbow");
      } else {
         InputUtils.getINSTANCE().values.remove("NoSlow_FT-Snow_Crossbow");
      }
   }

   private void invoke11() {
      this.invoke12("restore-requested");
   }

   private void invoke12(String string) {
      this.invoke14();
      if (this.noSlowState != NoSlow.NoSlowState.IDLE && this.intValue != -1) {
         if (CLIENT.player != null && CLIENT.interactionManager != null && CLIENT.player.getOffHandStack().isOf(Items.CROSSBOW)) {
            this.invoke8(this.intValue);
            this.invoke18(string, this.intValue);
         } else {
            this.invoke18(string + ":offhand-not-crossbow", this.intValue);
         }

         this.invoke13(string);
      } else {
         this.invoke13(string);
      }
   }

   private void invoke13(String string) {
      if (this.noSlowState != NoSlow.NoSlowState.IDLE || this.intValue != -1) {
         this.invoke18("reset:" + string, this.intValue);
      }

      this.invoke10();
      invoke17();
      this.intValue = -1;
      this.intValue2 = 0;
      this.flag2 = false;
      this.dualTimer.invoke();
      this.noSlowState = NoSlow.NoSlowState.IDLE;
   }

   private void invoke14() {
      if (CLIENT.player != null && CLIENT.interactionManager != null && this.noSlowState != NoSlow.NoSlowState.IDLE) {
         if (CLIENT.player.isUsingItem() && CLIENT.player.getActiveHand() == Hand.OFF_HAND) {
            if (CLIENT.player.getActiveItem().isOf(Items.CROSSBOW)) {
               this.invoke18("stop-offhand-crossbow-use", this.intValue);
               this.invoke15();
            }
         }
      }
   }

   private void invoke15() {
      if (CLIENT.player != null && CLIENT.interactionManager != null && CLIENT.player.isUsingItem()) {
         CLIENT.interactionManager.stopUsingItem(CLIENT.player);
         CLIENT.player.stopUsingItem();
      }
   }

   private static void invoke16() {
      if (CLIENT.player != null) {
         itemStack = CLIENT.player.getOffHandStack().copy();
         flag = true;
      }
   }

   private static void invoke17() {
      flag = false;
      itemStack = ItemStack.EMPTY;
   }

   public static ItemStack resolve(ItemStack itemStack) {
      if (!flag) {
         return itemStack;
      } else {
         return itemStack == null ? ItemStack.EMPTY : itemStack;
      }
   }

   private void invoke18(String string, int i) {
      LOGGER.info(
         "",
         new Object[]{
            string,
            this.noSlowState,
            i,
            tochnyyStop.isEnabled(),
            this.dualTimer.compute3(),
            (long)zaderzhkaSvapa.getValue(),
            CLIENT.player != null && CLIENT.player.isUsingItem(),
            CLIENT.player != null ? CLIENT.player.getActiveHand() : null,
            CLIENT.player != null ? CLIENT.player.getMainHandStack().getItem() : null,
            CLIENT.player != null ? CLIENT.player.getOffHandStack().getItem() : null,
            flag,
            itemStack != null ? itemStack.getItem() : null,
            CLIENT.options != null && CLIENT.options.useKey.isPressed()
         }
      );
   }

   @Override
   public void onDisable() {
      this.invoke12("module-disabled");
      super.onDisable();
   }

   static enum NoSlowState {
      IDLE,
      PRE_SWAP_STOP,
      EATING,
      PRE_RESTORE_STOP;
   }
}
