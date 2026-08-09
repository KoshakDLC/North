package org.wild.mixin;

import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.metaculture.protection.Animations;
import ru.metaculture.protection.Event;
import ru.metaculture.protection.EventManager;
import ru.metaculture.protection.FreeCamera;
import ru.metaculture.protection.CameraClipEvent;
import ru.metaculture.protection.CameraRotationEvent;
import ru.metaculture.protection.WildClient;

@Mixin({Camera.class})
public abstract class CameraMixin {
   @Unique
   private CameraRotationEvent rotationEvent;
   @Unique
   private float originalYaw;
   @Unique
   private float originalPitch;
   @Unique
   private boolean disableClip;
   @Unique
   private float freeCameraTickProgress;

   @Shadow
   protected abstract void setRotation(float f, float g);

   @Shadow
   protected abstract void moveBy(float f, float g, float h);

   @Shadow
   protected abstract void setPos(Vec3d vec3d);

   @Inject(
      method = {"update"},
      at = {@At("HEAD")}
   )
   private void onUpdateHead(BlockView blockView, Entity entity, boolean bl, boolean bl2, float f, CallbackInfo callbackInfo) {
      this.freeCameraTickProgress = f;
      CameraClipEvent cameraClipEvent = new CameraClipEvent();
      EventManager.post((Event)cameraClipEvent);
      this.disableClip = cameraClipEvent.isInvalidated();
      if (entity != null) {
         this.originalYaw = entity.getYaw(f);
         this.originalPitch = entity.getPitch(f);
         this.rotationEvent = new CameraRotationEvent(this.originalYaw, this.originalPitch, f);
         EventManager.post((Event)(Object)this.rotationEvent);
      } else {
         this.rotationEvent = null;
      }
   }

   @Redirect(
      method = {"update"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/render/Camera;setRotation(FF)V"
      )
   )
   private void redirectSetRotation(Camera camera, float f, float g) {
      boolean flag = this.rotationEvent != null
         && (this.rotationEvent.getFloatValue() != this.originalYaw || this.rotationEvent.getFloatValue2() != this.originalPitch);
      float floatValue = flag ? this.rotationEvent.getFloatValue() : this.originalYaw;
      float floatValue2 = flag ? this.rotationEvent.getFloatValue2() : this.originalPitch;
      Animations animations = this.wild$getAnimations();
      if (animations != null && animations.enabled && animations.animirovat.isEnabled("F5") && animations.check6()) {
         if (animations.check8()) {
            this.setRotation(floatValue + animations.measure7(), animations.measure8(floatValue2));
            return;
         }

         if (animations.check7() && this.wild$isInverseRotationCall(f, g)) {
            this.setRotation(floatValue + animations.measure7(), animations.measure8(floatValue2));
            return;
         }
      }

      if (flag) {
         this.setRotation(floatValue, floatValue2);
      } else {
         this.setRotation(f, g);
      }
   }

   @Inject(
      method = {"update"},
      at = {@At("RETURN")}
   )
   private void onUpdateReturn(CallbackInfo callbackInfo) {
      Animations animations2 = this.wild$getAnimations();
      if (animations2 != null && animations2.enabled && animations2.animirovat.isEnabled("F5") && animations2.check5()) {
         float floatValue3 = this.originalYaw + animations2.measure9();
         float floatValue4 = animations2.measure10(this.originalPitch);
         float floatValue5 = 4.0F * animations2.measure11();
         this.setRotation(floatValue3, floatValue4);
         if (floatValue5 > 0.001F) {
            this.moveBy(-floatValue5, 0.0F, 0.0F);
         }
      }

      FreeCamera freeCamera = FreeCamera.resolve();
      if (freeCamera != null && freeCamera.enabled) {
         Vec3d vec3d2 = freeCamera.resolve2(this.freeCameraTickProgress);
         if (vec3d2 != null) {
            this.setPos(vec3d2);
         }
      }

      this.rotationEvent = null;
      this.disableClip = false;
   }

   @ModifyVariable(
      method = {"clipToSpace"},
      at = @At("HEAD"),
      argsOnly = true
   )
   private float modifyCameraDistance(float f) {
      Animations animations3 = this.wild$getAnimations();
      if (animations3 != null && animations3.enabled && animations3.animirovat.isEnabled("F5") && animations3.check4()) {
         float floatValue6 = animations3.measure5();
         if (floatValue6 < 1.0F) {
            float floatValue7 = 1.0F - (float)Math.pow(1.0F - Animations.floatValue, 3.0);
            return f * floatValue7;
         }
      }

      return f;
   }

   @Inject(
      method = {"clipToSpace"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onClipToSpace(float f, CallbackInfoReturnable<Float> callbackInfoReturnable) {
      if (this.disableClip) {
         callbackInfoReturnable.setReturnValue(f);
      }
   }

   @Unique
   private Animations wild$getAnimations() {
      return WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null
         ? (Animations)WildClient.INSTANCE.moduleManager.findModule(Animations.class)
         : null;
   }

   @Unique
   private boolean wild$isInverseRotationCall(float f, float g) {
      return Math.abs(Math.abs(f - this.originalYaw) - 180.0F) < 0.5F || Math.abs(g + this.originalPitch) < 0.5F;
   }
}
