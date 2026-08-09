package org.wild.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.metaculture.protection.AttackAura;
import ru.metaculture.protection.FakePlayer;
import ru.metaculture.protection.NoInteract;
import ru.metaculture.protection.WildClient;

@Environment(EnvType.CLIENT)
@Mixin({ClientPlayerInteractionManager.class})
public abstract class ClientPlayerInteractionManagerMixin {
   @Inject(
      method = {"interactBlock"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void noInteract(
      ClientPlayerEntity clientPlayerEntity, Hand hand, BlockHitResult blockHitResult, CallbackInfoReturnable<ActionResult> callbackInfoReturnable
   ) {
      if (clientPlayerEntity != null) {
         NoInteract noInteract = (NoInteract)WildClient.INSTANCE.moduleManager.findModule(NoInteract.class);
         if (noInteract != null && noInteract.enabled) {
            if (AttackAura.livingEntity == null) {
               ClientWorld clientWorld = clientPlayerEntity.clientWorld;
               if (clientWorld != null) {
                  Block block = clientWorld.getBlockState(blockHitResult.getBlockPos()).getBlock();
                  if (NoInteract.resolve().contains(block)) {
                     callbackInfoReturnable.setReturnValue(ActionResult.FAIL);
                  }
               }
            }
         }
      }
   }

   @Inject(
      method = {"interactEntity"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void noInteractEntity(PlayerEntity playerEntity, Entity entity, Hand hand, CallbackInfoReturnable<ActionResult> callbackInfoReturnable) {
      if (FakePlayer.check2(entity)) {
         callbackInfoReturnable.setReturnValue(ActionResult.SUCCESS);
      } else if (entity instanceof ArmorStandEntity) {
         NoInteract noInteract2 = (NoInteract)WildClient.INSTANCE.moduleManager.findModule(NoInteract.class);
         if (noInteract2 != null && noInteract2.enabled) {
            if (NoInteract.bloki.isEnabled(0)) {
               callbackInfoReturnable.setReturnValue(ActionResult.FAIL);
            }
         }
      }
   }

   @Inject(
      method = {"interactEntityAtLocation"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void fakePlayerInteractAtLocation(
      PlayerEntity playerEntity, Entity entity, EntityHitResult entityHitResult, Hand hand, CallbackInfoReturnable<ActionResult> callbackInfoReturnable
   ) {
      if (FakePlayer.check2(entity)) {
         callbackInfoReturnable.setReturnValue(ActionResult.SUCCESS);
      }
   }
}
