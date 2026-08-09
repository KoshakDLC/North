package ru.metaculture.protection;

import java.util.ArrayDeque;
import java.util.Queue;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import org.wild.mixin.acceser.ClientPlayerInteractionManagerAccessor;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "AutoPotion",
   category = Category.Player,
   description = "Автоматически кидает под вас взрывные зелья"
)
public class AutoPotion extends Module {
   public static AutoPotion instance;
   public static boolean flag = false;
   public final GroupSetting chtoBafat = new GroupSetting(
      "Что бафать: ", new BooleanSetting("Сила", false), new BooleanSetting("Скорость", false), new BooleanSetting("Огнестойкость", false)
   );
   public final BooleanSetting kidatSmotryaVniz = new BooleanSetting("Кидать смотря вниз", false);
   private final BooleanSetting tolkoVPvp = new BooleanSetting("Только в PVP", false);
   private final Queue<Integer> queue = new ArrayDeque<>();
   private int intValue = -1;
   private boolean flag2 = false;
   private int intValue2 = 0;

   public AutoPotion() {
      instance = this;
      this.addSettings(new Setting[]{this.chtoBafat, this.kidatSmotryaVniz, this.tolkoVPvp});
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player != null && CLIENT.world != null && CLIENT.interactionManager != null) {
         if (this.queue.isEmpty()) {
            if (flag) {
               this.invoke2();
            }

            if (this.intValue2 > 0) {
               this.intValue2--;
            } else if (!this.tolkoVPvp.isEnabled() || this.check()) {
               if (!this.kidatSmotryaVniz.isEnabled() || !(CLIENT.player.getPitch() < 80.0F)) {
                  this.invoke();
               }
            }
         } else {
            if (!flag) {
               flag = true;
               if (!this.kidatSmotryaVniz.isEnabled()) {
                  FreeLookController.floatValue = CLIENT.player.getYaw();
                  FreeLookController.floatValue2 = CLIENT.player.getPitch();
                  FreeLookController.active = true;
               }

               if (this.intValue == -1) {
                  this.intValue = CLIENT.player.getInventory().getSelectedSlot();
               }
            }

            CLIENT.options.sprintKey.setPressed(false);
            CLIENT.player.setSprinting(false);
            if (!this.kidatSmotryaVniz.isEnabled()) {
               CLIENT.player.setPitch(90.0F);
            }

            int intValue = this.queue.poll();
            if (intValue < 9) {
               CLIENT.player.getInventory().setSelectedSlot(intValue);
               ((ClientPlayerInteractionManagerAccessor)CLIENT.interactionManager).invokeSyncSelectedSlot();
            } else {
               this.flag2 = true;
               CLIENT.interactionManager
                  .clickSlot(CLIENT.player.playerScreenHandler.syncId, intValue, this.intValue, SlotActionType.SWAP, CLIENT.player);
            }

            CLIENT.interactionManager.interactItem(CLIENT.player, Hand.MAIN_HAND);
            CLIENT.player.swingHand(Hand.MAIN_HAND);
            if (intValue < 9) {
               CLIENT.player.getInventory().setSelectedSlot(this.intValue);
               ((ClientPlayerInteractionManagerAccessor)CLIENT.interactionManager).invokeSyncSelectedSlot();
            } else {
               CLIENT.interactionManager
                  .clickSlot(CLIENT.player.playerScreenHandler.syncId, intValue, this.intValue, SlotActionType.SWAP, CLIENT.player);
            }

            if (this.queue.isEmpty()) {
               if (this.flag2) {
                  CLIENT.player.networkHandler.sendPacket(new CloseHandledScreenC2SPacket(CLIENT.player.playerScreenHandler.syncId));
                  this.flag2 = false;
               }

               this.intValue2 = 1;
               this.invoke2();
            }
         }
      }
   }

   private void invoke() {
      boolean flag = this.chtoBafat.isEnabled("Сила") && !CLIENT.player.hasStatusEffect(StatusEffects.STRENGTH);
      boolean flag2 = this.chtoBafat.isEnabled("Скорость") && !CLIENT.player.hasStatusEffect(StatusEffects.SPEED);
      boolean flag3 = this.chtoBafat.isEnabled("Огнестойкость") && !CLIENT.player.hasStatusEffect(StatusEffects.FIRE_RESISTANCE);
      if (flag || flag2 || flag3) {
         for (int intValue2 = 0; intValue2 < 36; intValue2++) {
            ItemStack itemStack = CLIENT.player.getInventory().getStack(intValue2);
            if (!itemStack.isEmpty() && itemStack.getItem() == Items.SPLASH_POTION) {
               PotionContentsComponent potionContentsComponent = (PotionContentsComponent)itemStack.get(DataComponentTypes.POTION_CONTENTS);
               if (potionContentsComponent != null) {
                  for (StatusEffectInstance statusEffectInstance : potionContentsComponent.getEffects()) {
                     RegistryEntry registryEntry = statusEffectInstance.getEffectType();
                     if (flag && registryEntry.equals(StatusEffects.STRENGTH)) {
                        this.queue.add(intValue2);
                        flag = false;
                        break;
                     }

                     if (flag2 && registryEntry.equals(StatusEffects.SPEED)) {
                        this.queue.add(intValue2);
                        flag2 = false;
                        break;
                     }

                     if (flag3 && registryEntry.equals(StatusEffects.FIRE_RESISTANCE)) {
                        this.queue.add(intValue2);
                        flag3 = false;
                        break;
                     }
                  }
               }
            }
         }
      }
   }

   private boolean check() {
      for (PlayerEntity playerEntity : CLIENT.world.getPlayers()) {
         if (playerEntity != CLIENT.player && CLIENT.player.squaredDistanceTo(playerEntity) <= 225.0) {
            return true;
         }
      }

      return false;
   }

   private void invoke2() {
      flag = false;
      this.intValue = -1;
      if (!this.kidatSmotryaVniz.isEnabled()) {
         if (CLIENT.player != null) {
            CLIENT.player.setYaw(FreeLookController.floatValue);
            CLIENT.player.setPitch(FreeLookController.floatValue2);
         }

         FreeLookController.active = false;
      }
   }

   @Override
   public void onDisable() {
      this.queue.clear();
      this.invoke2();
      super.onDisable();
   }
}
