package org.wild.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec2f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.metaculture.protection.Event;
import ru.metaculture.protection.EventManager;
import ru.metaculture.protection.FreeCamera;
import ru.metaculture.protection.LockSlots;
import ru.metaculture.protection.NoPush;
import ru.metaculture.protection.PlayerTickEvent;
import ru.metaculture.protection.PlayerMotionEvent;
import ru.metaculture.protection.PlayerMovePacketEvent;
import ru.metaculture.protection.SlowdownEvent;
import ru.metaculture.protection.UseItemSlowdownEvent;
import ru.metaculture.protection.FreeLookController;
import ru.metaculture.protection.WildClient;

@Environment(EnvType.CLIENT)
@Mixin({ClientPlayerEntity.class})
public abstract class ClientPlayerEntityMixin {
   @Shadow
   public Input input;

   @Inject(
      method = {"tick"},
      at = {@At("HEAD")}
   )
   private void onTickHead(CallbackInfo callbackInfo) {
      EventManager.post((Event)(new PlayerTickEvent()));
   }

   @Redirect(
      method = {"tickMovementInput"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/network/ClientPlayerEntity;getPitch()F"
      )
   )
   private float redirectRenderPitchUpdate(ClientPlayerEntity clientPlayerEntity) {
      return FreeLookController.active ? MinecraftClient.getInstance().gameRenderer.getCamera().getPitch() : clientPlayerEntity.getPitch();
   }

   @Inject(
      method = {"dropSelectedItem"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void wild$lockSlotsDropSelected(boolean bl, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
      if (WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null) {
         LockSlots lockSlots = WildClient.INSTANCE.moduleManager.getModule(LockSlots.class);
         if (lockSlots != null && lockSlots.enabled) {
            ClientPlayerEntity clientPlayerEntity2 = (ClientPlayerEntity)(Object)this;
            if (lockSlots.check(clientPlayerEntity2.getInventory().getSelectedSlot())) {
               callbackInfoReturnable.setReturnValue(false);
            }
         }
      }
   }

   @Inject(
      method = {"sendMovementPackets"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void preMotion(CallbackInfo callbackInfo) {
      ClientPlayerEntity clientPlayerEntity3 = (ClientPlayerEntity)(Object)this;
      PlayerMovePacketEvent playerMovePacketEvent = new PlayerMovePacketEvent(clientPlayerEntity3.getX(), clientPlayerEntity3.getY(), clientPlayerEntity3.getZ(), clientPlayerEntity3.getYaw(), clientPlayerEntity3.getPitch(), clientPlayerEntity3.isOnGround());
      EventManager.post((Event)playerMovePacketEvent);
      if (playerMovePacketEvent.isInvalidated()) {
         callbackInfo.cancel();
      }
   }

   @ModifyExpressionValue(
      method = {"tickMovement"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z"
      )}
   )
   private boolean usingItemHook(boolean bl) {
      if (bl) {
         UseItemSlowdownEvent useItemSlowdownEvent = new UseItemSlowdownEvent((byte)1);
         EventManager.post((Event)useItemSlowdownEvent);
         if (useItemSlowdownEvent.isInvalidated()) {
            return false;
         }
      }

      return bl;
   }

   @Redirect(
      method = {"tickMovementInput"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/network/ClientPlayerEntity;getYaw()F"
      )
   )
   private float redirectRenderYawUpdate(ClientPlayerEntity clientPlayerEntity) {
      return FreeLookController.active ? MinecraftClient.getInstance().gameRenderer.getCamera().getYaw() : clientPlayerEntity.getYaw();
   }

   @Redirect(
      method = {"tickMovement"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/network/ClientPlayerEntity;pushOutOfBlocks(DD)V"
      )
   )
   private void redirectPushOutOfBlocks(ClientPlayerEntity clientPlayerEntity, double d, double e) {
      if (WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null) {
         FreeCamera freeCamera = WildClient.INSTANCE.moduleManager.getModule(FreeCamera.class);
         if (freeCamera != null && freeCamera.enabled) {
            return;
         }

         NoPush noPush = (NoPush)WildClient.INSTANCE.moduleManager.findModule(NoPush.class);
         if (noPush != null && noPush.enabled && noPush.blocks.isEnabled()) {
         }
      }
   }

   @Inject(
      method = {"tickMovement"},
      at = {@At("HEAD")}
   )
   private void onUpdateWalkingPlayer(CallbackInfo callbackInfo) {
      ClientPlayerEntity clientPlayerEntity4 = (ClientPlayerEntity)(Object)this;
      if (clientPlayerEntity4 != null) {
         Box box = clientPlayerEntity4.getBoundingBox();
         PlayerMotionEvent playerMotionEvent = new PlayerMotionEvent(clientPlayerEntity4.getYaw(), clientPlayerEntity4.getPitch(), clientPlayerEntity4.getX(), clientPlayerEntity4.getY(), clientPlayerEntity4.getZ(), clientPlayerEntity4.isOnGround(), box, null);
         EventManager.post((Event)playerMotionEvent);
         if (!playerMotionEvent.isInvalidated()) {
            if (playerMotionEvent.getFloatValue() != clientPlayerEntity4.getYaw() || playerMotionEvent.getFloatValue2() != clientPlayerEntity4.getPitch()) {
               clientPlayerEntity4.setYaw(playerMotionEvent.getFloatValue());
               clientPlayerEntity4.setPitch(playerMotionEvent.getFloatValue2());
            }

            if (playerMotionEvent.getDoubleValue() != clientPlayerEntity4.getX() || playerMotionEvent.getDoubleValue2() != clientPlayerEntity4.getY() || playerMotionEvent.getDoubleValue3() != clientPlayerEntity4.getZ()) {
               clientPlayerEntity4.refreshPositionAndAngles(playerMotionEvent.getDoubleValue(), playerMotionEvent.getDoubleValue2(), playerMotionEvent.getDoubleValue3(), playerMotionEvent.getFloatValue(), playerMotionEvent.getFloatValue2());
            }

            if (playerMotionEvent.isFlag() != clientPlayerEntity4.isOnGround()) {
               clientPlayerEntity4.setOnGround(playerMotionEvent.isFlag());
            }
         }
      }
   }

   @Redirect(
      method = {"applyMovementSpeedFactors"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/util/math/Vec2f;multiply(F)Lnet/minecraft/util/math/Vec2f;",
         ordinal = 1
      )
   )
   private Vec2f preventSlowdownMultiply(Vec2f vec2f, float f) {
      ClientPlayerEntity clientPlayerEntity5 = (ClientPlayerEntity)(Object)this;
      if (f == 0.2F && clientPlayerEntity5.isUsingItem() && !clientPlayerEntity5.hasVehicle()) {
         float floatValue = vec2f.y;
         float floatValue2 = vec2f.x;
         SlowdownEvent slowdownEvent = new SlowdownEvent(floatValue, floatValue2);
         EventManager.post((Event)slowdownEvent);
         slowdownEvent.invoke();
         if (slowdownEvent.isInvalidated()) {
            return vec2f;
         }
      }

      return vec2f.multiply(f);
   }

   @Unique
   private static float getMovementMultiplier(boolean bl, boolean bl2) {
      if (bl == bl2) {
         return 0.0F;
      } else {
         return bl ? 1.0F : -1.0F;
      }
   }
}
