package ru.metaculture.protection;

import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "AutoFish",
   description = "Ловит за вас карасей",
   category = Category.Player
)
public class AutoFish extends Module {
   public static DualTimer dualTimer = new DualTimer();
   public static boolean flag = false;
   public static boolean flag2 = false;

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player != null && CLIENT.interactionManager != null) {
         if (dualTimer.check8(600.0) && flag) {
            CLIENT.interactionManager.interactItem(CLIENT.player, Hand.MAIN_HAND);
            flag = false;
            flag2 = true;
            dualTimer.invoke();
         }

         if (dualTimer.check8(300.0) && flag2) {
            CLIENT.interactionManager.interactItem(CLIENT.player, Hand.MAIN_HAND);
            flag2 = false;
            dualTimer.invoke();
         }
      }
   }

   @EventHandler
   public void onPacket(PacketEvent packetEvent) {
      if (packetEvent.getPacket() instanceof PlaySoundS2CPacket playSoundS2CPacket) {
         if (playSoundS2CPacket.getSound().value() == SoundEvents.ENTITY_FISHING_BOBBER_SPLASH) {
            FishingBobberEntity fishingBobberEntity = CLIENT.player.fishHook;
            if (fishingBobberEntity != null) {
               double doubleValue = playSoundS2CPacket.getX() - fishingBobberEntity.getX();
               double doubleValue2 = playSoundS2CPacket.getY() - fishingBobberEntity.getY();
               double doubleValue3 = playSoundS2CPacket.getZ() - fishingBobberEntity.getZ();
               double doubleValue4 = Math.sqrt(doubleValue * doubleValue + doubleValue2 * doubleValue2 + doubleValue3 * doubleValue3);
               if (doubleValue4 <= 0.5) {
                  flag = true;
                  dualTimer.invoke();
               }
            }
         }
      }
   }
}
