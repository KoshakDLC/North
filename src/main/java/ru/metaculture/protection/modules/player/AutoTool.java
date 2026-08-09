package ru.metaculture.protection;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "AutoTool",
   category = Category.Player,
   description = "Автоматически берет нужный вам инструмент"
)
public class AutoTool extends Module {
   private static final String AUTOTOOL = "AutoTool";
   private static final long TIMESTAMP = 50L;
   private static final String TOLKO_HOTBAR = "Только хотбар";
   private static final String INVENTAR = "Инвентарь";
   private static final String GIBRID = "Гибрид";
   public static ModeSetting rezhim = new ModeSetting("Режим", "Гибрид", "Только хотбар", "Инвентарь", "Гибрид");
   private AutoTool.AutoToolState autoToolState = AutoTool.AutoToolState.IDLE;
   private long timestamp;
   private int intValue = -1;
   private int intValue2 = -1;
   private boolean flag;

   public AutoTool() {
      this.addSettings(new Setting[]{rezhim});
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player != null && CLIENT.world != null && CLIENT.interactionManager != null) {
         switch (this.autoToolState) {
            case IDLE:
               this.invoke();
               break;
            case PREPARE_SWAP:
               this.invoke2();
               break;
            case MINING:
               this.invoke3();
               break;
            case PREPARE_RESTORE:
               this.invoke4();
         }
      } else {
         this.invoke8(false);
      }
   }

   @Override
   public void onDisable() {
      this.invoke8(true);
      super.onDisable();
   }

   private void invoke() {
      BlockState blockState2 = this.resolve();
      if (blockState2 != null && CLIENT.options.attackKey.isPressed()) {
         int intValue = this.compute(blockState2);
         int intValue2 = CLIENT.player.getInventory().getSelectedSlot();
         if (intValue != -1 && intValue != intValue2) {
            this.intValue = intValue;
            this.intValue2 = intValue2;
            this.flag = intValue >= 9;
            if (this.flag) {
               this.invoke5(AutoTool.AutoToolState.PREPARE_SWAP);
            } else {
               CLIENT.player.getInventory().setSelectedSlot(intValue);
               this.autoToolState = AutoTool.AutoToolState.MINING;
            }
         }
      }
   }

   private void invoke2() {
      if (!CLIENT.options.attackKey.isPressed() || this.resolve() == null) {
         this.invoke8(false);
      } else if (this.check()) {
         this.invoke6();
         InputUtils.getINSTANCE().invoke2("AutoTool");
         this.autoToolState = AutoTool.AutoToolState.MINING;
      }
   }

   private void invoke3() {
      if (!CLIENT.options.attackKey.isPressed() || this.resolve() == null) {
         if (this.flag) {
            this.invoke5(AutoTool.AutoToolState.PREPARE_RESTORE);
         } else {
            this.invoke7();
            this.invoke8(false);
         }
      }
   }

   private void invoke4() {
      if (this.check()) {
         this.invoke6();
         this.invoke8(false);
      }
   }

   private void invoke5(AutoTool.AutoToolState autoToolState) {
      InputUtils.getINSTANCE().invoke("AutoTool");
      CLIENT.options.sprintKey.setPressed(false);
      CLIENT.player.setSprinting(false);
      this.timestamp = System.currentTimeMillis();
      this.autoToolState = autoToolState;
   }

   private boolean check() {
      InputUtils.getINSTANCE().invoke("AutoTool");
      return System.currentTimeMillis() - this.timestamp >= 50L;
   }

   private void invoke6() {
      if (this.intValue >= 9 && this.intValue2 >= 0) {
         CLIENT.interactionManager
            .clickSlot(CLIENT.player.playerScreenHandler.syncId, this.intValue, this.intValue2, SlotActionType.SWAP, CLIENT.player);
      }
   }

   private void invoke7() {
      if (this.intValue2 >= 0 && this.intValue2 <= 8) {
         CLIENT.player.getInventory().setSelectedSlot(this.intValue2);
      }
   }

   private BlockState resolve() {
      return CLIENT.crosshairTarget instanceof BlockHitResult blockHitResult && blockHitResult.getType() == Type.BLOCK
         ? CLIENT.world.getBlockState(blockHitResult.getBlockPos())
         : null;
   }

   private int compute(BlockState blockState) {
      int intValue3 = rezhim.is("Инвентарь") ? 9 : 0;
      int intValue4 = rezhim.is("Только хотбар") ? 9 : 36;
      int intValue5 = CLIENT.player.getInventory().getSelectedSlot();
      ItemStack itemStack2 = CLIENT.player.getInventory().getStack(intValue5);
      boolean flag = this.check2(itemStack2);
      int intValue6 = flag ? intValue5 : -1;
      float floatValue = flag ? itemStack2.getMiningSpeedMultiplier(blockState) : 1.0F;
      boolean flag2 = !blockState.isToolRequired() || flag && itemStack2.isSuitableFor(blockState);

      for (int intValue7 = intValue3; intValue7 < intValue4; intValue7++) {
         ItemStack itemStack3 = CLIENT.player.getInventory().getStack(intValue7);
         if (this.check2(itemStack3)) {
            float floatValue2 = itemStack3.getMiningSpeedMultiplier(blockState);
            boolean flag3 = !blockState.isToolRequired() || itemStack3.isSuitableFor(blockState);
            if (flag3 && !flag2 || flag3 == flag2 && floatValue2 > floatValue) {
               intValue6 = intValue7;
               floatValue = floatValue2;
               flag2 = flag3;
            }
         }
      }

      return intValue6;
   }

   private boolean check2(ItemStack itemStack) {
      return !itemStack.isEmpty() && (!itemStack.isDamageable() || itemStack.getMaxDamage() - itemStack.getDamage() > 1);
   }

   private void invoke8(boolean bl) {
      if (bl
         && CLIENT.player != null
         && CLIENT.interactionManager != null
         && (this.autoToolState == AutoTool.AutoToolState.MINING || this.autoToolState == AutoTool.AutoToolState.PREPARE_RESTORE)) {
         if (this.flag) {
            this.invoke6();
         } else {
            this.invoke7();
         }
      }

      InputUtils.getINSTANCE().invoke2("AutoTool");
      InputUtils.getINSTANCE().values.remove("AutoTool");
      this.autoToolState = AutoTool.AutoToolState.IDLE;
      this.timestamp = 0L;
      this.intValue = -1;
      this.intValue2 = -1;
      this.flag = false;
   }

   static enum AutoToolState {
      IDLE,
      PREPARE_SWAP,
      MINING,
      PREPARE_RESTORE;
   }
}
