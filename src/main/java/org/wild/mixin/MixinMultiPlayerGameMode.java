package org.wild.mixin;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.metaculture.protection.Event;
import ru.metaculture.protection.EventManager;
import ru.metaculture.protection.FakePlayer;
import ru.metaculture.protection.FriendCommand;
import ru.metaculture.protection.FriendManager;
import ru.metaculture.protection.AttackEntityEvent;
import ru.metaculture.protection.WildClient;

@Mixin({ClientPlayerInteractionManager.class})
public class MixinMultiPlayerGameMode {
   @Inject(
      method = {"attackEntity"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onAttack(PlayerEntity playerEntity, Entity entity, CallbackInfo callbackInfo) {
      AttackEntityEvent attackEntityEvent = new AttackEntityEvent(entity);
      EventManager.post((Event)attackEntityEvent);
      if (attackEntityEvent.isInvalidated()) {
         callbackInfo.cancel();
      } else if (FakePlayer.check(entity)) {
         callbackInfo.cancel();
      } else {
         if (entity instanceof PlayerEntity) {
            String text = entity.getName().getString();
            FriendManager friendManager = WildClient.INSTANCE.moduleManager.getModule(FriendManager.class);
            if (friendManager != null && friendManager.enabled && FriendManager.neBitDruzey.isEnabled() && FriendCommand.check(text)) {
               callbackInfo.cancel();
            }
         }
      }
   }
}
