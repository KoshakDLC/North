package org.wild.mixin;

import net.minecraft.client.input.KeyboardInput;
import net.minecraft.util.PlayerInput;
import net.minecraft.util.math.Vec2f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.metaculture.protection.Event;
import ru.metaculture.protection.EventManager;
import ru.metaculture.protection.MovementInputEvent;
import ru.metaculture.protection.ProtectionHandler;

@Mixin({KeyboardInput.class})
public abstract class KeyboardInputMixin {
   @Unique
   private MovementInputEvent inputEvent;

   @Inject(
      method = {"tick"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/util/math/Vec2f;<init>(FF)V",
         shift = Shift.BEFORE
      )}
   )
   private void onTickBeforeMovementVector(CallbackInfo callbackInfo) {
      ProtectionHandler.checkAccess();
      KeyboardInput keyboardInput = (KeyboardInput)(Object)this;
      float floatValue = getMovementMultiplier(keyboardInput.playerInput.forward(), keyboardInput.playerInput.backward());
      float floatValue2 = getMovementMultiplier(keyboardInput.playerInput.left(), keyboardInput.playerInput.right());
      this.inputEvent = new MovementInputEvent(floatValue, floatValue2, keyboardInput.playerInput.jump(), keyboardInput.playerInput.sneak(), keyboardInput.playerInput.sprint(), 0.3);
      EventManager.post((Event)(Object)this.inputEvent);
   }

   @Redirect(
      method = {"tick"},
      at = @At(
         value = "NEW",
         target = "Lnet/minecraft/util/math/Vec2f;"
      )
   )
   private Vec2f redirectVec2fCreation(float f, float g) {
      return this.inputEvent != null ? new Vec2f(this.inputEvent.getFloatValue2(), this.inputEvent.getFloatValue()).normalize() : new Vec2f(f, g).normalize();
   }

   @Inject(
      method = {"tick"},
      at = {@At(
         value = "FIELD",
         target = "Lnet/minecraft/client/input/KeyboardInput;playerInput:Lnet/minecraft/util/PlayerInput;",
         opcode = 181,
         shift = Shift.AFTER
      )}
   )
   private void onTickAfterPlayerInput(CallbackInfo callbackInfo) {
      if (this.inputEvent != null) {
         KeyboardInput keyboardInput2 = (KeyboardInput)(Object)this;
         PlayerInput playerInput = keyboardInput2.playerInput;
         PlayerInput playerInput2 = new PlayerInput(
            playerInput.forward(),
            playerInput.backward(),
            playerInput.left(),
            playerInput.right(),
            this.inputEvent.isFlag(),
            this.inputEvent.isFlag2(),
            this.inputEvent.isFlag3()
         );
         keyboardInput2.playerInput = playerInput2;
      }
   }

   @Inject(
      method = {"tick"},
      at = {@At("RETURN")}
   )
   private void onTickReturn(CallbackInfo callbackInfo) {
      this.inputEvent = null;
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
