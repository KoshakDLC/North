package org.wild.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.metaculture.protection.FriendCommand;
import ru.metaculture.protection.HitBox;
import ru.metaculture.protection.NoPush;
import ru.metaculture.protection.WildClient;

@Environment(EnvType.CLIENT)
@Mixin({Entity.class})
public abstract class EntityMixin {
   @Inject(
      method = {"getTargetingMargin"},
      at = {@At("RETURN")},
      cancellable = true
   )
   private void client$getTargetingMargin(CallbackInfoReturnable<Float> callbackInfoReturnable) {
      Entity entity2 = (Entity)(Object)this;
      if (entity2 instanceof PlayerEntity) {
         HitBox hitBox = (HitBox)WildClient.INSTANCE.moduleManager.findModule(HitBox.class);
         if (hitBox != null && hitBox.enabled) {
            if (HitBox.rezhim.is("Обычный")) {
               if (!(HitBox.ignorDruzey.isEnabled() && entity2 instanceof PlayerEntity playerEntity) || !FriendCommand.check(playerEntity.getName().getString())) {
                  float floatValue = (Float)callbackInfoReturnable.getReturnValue();
                  float floatValue2 = HitBox.razmer.getValue();
                  callbackInfoReturnable.setReturnValue(floatValue + floatValue2);
               }
            }
         }
      }
   }

   @Inject(
      method = {"pushAwayFrom"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onPushAwayFrom(Entity entity, CallbackInfo callbackInfo) {
      Entity entity3 = (Entity)(Object)this;
      if (entity3 instanceof ClientPlayerEntity) {
         if (WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null) {
            NoPush noPush = (NoPush)WildClient.INSTANCE.moduleManager.findModule(NoPush.class);
            if (noPush != null && noPush.enabled) {
               if (entity instanceof PlayerEntity && noPush.players.isEnabled()) {
                  callbackInfo.cancel();
               } else if (entity instanceof LivingEntity && !(entity instanceof PlayerEntity) && noPush.mobs.isEnabled()) {
                  callbackInfo.cancel();
               }
            }
         }
      }
   }
}
