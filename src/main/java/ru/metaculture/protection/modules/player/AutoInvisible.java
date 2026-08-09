package ru.metaculture.protection;

import java.util.function.Predicate;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;
import org.wild.mixin.acceser.ClientPlayerInteractionManagerAccessor;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "AutoInvisible",
   category = Category.Player,
   description = "Автоматически пьёт зелье невидимости и возвращает прошлый слот"
)
public class AutoInvisible extends Module {
   public final NumberSetting porogDoZelyaSek = new NumberSetting("Порог до зелья (сек)", 5.0F, 1.0F, 60.0F, 1.0F, false);
   private static final long TIMESTAMP = 1850L;
   private final DualTimer dualTimer = new DualTimer();
   private boolean flag;
   private int intValue = -1;

   public AutoInvisible() {
      this.addSettings(new Setting[]{this.porogDoZelyaSek});
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player != null && CLIENT.world != null && CLIENT.interactionManager != null) {
         if (CLIENT.currentScreen != null) {
            if (this.flag) {
               this.invoke();
            }
         } else if (this.flag) {
            if (CLIENT.player.isUsingItem() && !this.dualTimer.check5(1850L)) {
               CLIENT.options.useKey.setPressed(true);
            } else {
               this.invoke();
            }
         } else if (this.check()) {
            int intValue = this.compute(this::check2);
            if (intValue != -1) {
               int intValue2 = this.compute2(intValue);
               if (intValue2 != -1) {
                  this.intValue = CLIENT.player.getInventory().getSelectedSlot();
                  this.invoke2(intValue2);
                  CLIENT.options.useKey.setPressed(true);
                  this.flag = true;
                  this.dualTimer.invoke();
               }
            }
         }
      }
   }

   private void invoke() {
      CLIENT.options.useKey.setPressed(false);
      if (this.intValue >= 0 && this.intValue < 9) {
         this.invoke2(this.intValue);
      }

      this.flag = false;
      this.intValue = -1;
   }

   private boolean check() {
      StatusEffectInstance statusEffectInstance = CLIENT.player.getStatusEffect(StatusEffects.INVISIBILITY);
      return statusEffectInstance == null || statusEffectInstance.getDuration() <= (int)this.porogDoZelyaSek.getValue() * 20;
   }

   private int compute(Predicate<ItemStack> predicate) {
      for (int intValue3 = 0; intValue3 < 36; intValue3++) {
         ItemStack itemStack2 = CLIENT.player.getInventory().getStack(intValue3);
         if (!itemStack2.isEmpty() && predicate.test(itemStack2)) {
            return intValue3;
         }
      }

      return -1;
   }

   private int compute2(int i) {
      if (i >= 0 && i < 9) {
         return i;
      } else {
         int intValue4 = CLIENT.player.getInventory().getSelectedSlot();

         for (int intValue5 = 0; intValue5 < 9; intValue5++) {
            if (CLIENT.player.getInventory().getStack(intValue5).isEmpty()) {
               intValue4 = intValue5;
               break;
            }
         }

         int intValue6 = i < 9 ? i + 36 : i;
         CLIENT.interactionManager.clickSlot(CLIENT.player.playerScreenHandler.syncId, intValue6, intValue4, SlotActionType.SWAP, CLIENT.player);
         return intValue4;
      }
   }

   private boolean check2(ItemStack itemStack) {
      if (itemStack != null && !itemStack.isEmpty() && itemStack.isOf(Items.POTION)) {
         PotionContentsComponent potionContentsComponent = (PotionContentsComponent)itemStack.get(DataComponentTypes.POTION_CONTENTS);
         if (potionContentsComponent == null) {
            return false;
         } else {
            for (StatusEffectInstance statusEffectInstance2 : potionContentsComponent.getEffects()) {
               if (statusEffectInstance2.getEffectType().equals(StatusEffects.INVISIBILITY)) {
                  return true;
               }
            }

            return false;
         }
      } else {
         return false;
      }
   }

   private void invoke2(int i) {
      CLIENT.player.getInventory().setSelectedSlot(i);
      if (CLIENT.interactionManager instanceof ClientPlayerInteractionManagerAccessor clientPlayerInteractionManagerAccessor) {
         clientPlayerInteractionManagerAccessor.invokeSyncSelectedSlot();
      }
   }

   @Override
   public void onDisable() {
      if (this.flag || CLIENT.options != null && CLIENT.options.useKey.isPressed()) {
         CLIENT.options.useKey.setPressed(false);
         if (this.intValue >= 0 && this.intValue < 9 && CLIENT.player != null) {
            this.invoke2(this.intValue);
         }
      }

      this.flag = false;
      this.intValue = -1;
      super.onDisable();
   }
}
