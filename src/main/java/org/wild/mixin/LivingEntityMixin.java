package org.wild.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.metaculture.protection.Event;
import ru.metaculture.protection.EventManager;
import ru.metaculture.protection.GrimGlide;
import ru.metaculture.protection.NoDelay;
import ru.metaculture.protection.LivingEntityTickEvent;
import ru.metaculture.protection.EntityVelocityEvent;
import ru.metaculture.protection.Removals;
import ru.metaculture.protection.SwingAnimation;
import ru.metaculture.protection.WildClient;

@Mixin({LivingEntity.class})
public abstract class LivingEntityMixin {
   @Unique
   private final MinecraftClient client = MinecraftClient.getInstance();

   @Shadow
   public abstract boolean hasStatusEffect(RegistryEntry<StatusEffect> registryEntry);

   @Shadow
   @Nullable
   public abstract StatusEffectInstance getStatusEffect(RegistryEntry<StatusEffect> registryEntry);

   @Inject(
      method = {"jump"},
      at = {@At("HEAD")}
   )
   public void jumpYo(CallbackInfo callbackInfo) {
      LivingEntity livingEntity = (LivingEntity)(Object)this;
      if (livingEntity instanceof ClientPlayerEntity) {
         EventManager.post((Event)(new LivingEntityTickEvent()));
      }
   }

   @Inject(
      method = {"hasStatusEffect"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onHasStatusEffect(RegistryEntry<StatusEffect> registryEntry, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
      LivingEntity livingEntity2 = (LivingEntity)(Object)this;
      if (livingEntity2 instanceof ClientPlayerEntity && Removals.check3(registryEntry)) {
         callbackInfoReturnable.setReturnValue(false);
      }
   }

   @Inject(
      method = {"getStatusEffect"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onGetStatusEffect(RegistryEntry<StatusEffect> registryEntry, CallbackInfoReturnable<StatusEffectInstance> callbackInfoReturnable) {
      LivingEntity livingEntity3 = (LivingEntity)(Object)this;
      if (livingEntity3 instanceof ClientPlayerEntity && Removals.check3(registryEntry)) {
         callbackInfoReturnable.setReturnValue(null);
      }
   }

   @Inject(
      method = {"getHandSwingDuration"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void swingProgressHook(CallbackInfoReturnable<Integer> callbackInfoReturnable) {
      LivingEntity livingEntity4 = (LivingEntity)(Object)this;
      if (livingEntity4 instanceof ClientPlayerEntity) {
         if (WildClient.isInitialized()) {
            if (this.client != null && this.client.player != null) {
               if (livingEntity4 == this.client.player) {
                  if (WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null) {
                     SwingAnimation swingAnimation = (SwingAnimation)WildClient.INSTANCE.moduleManager.findModule(SwingAnimation.class);
                     if (swingAnimation != null && swingAnimation.enabled) {
                        if (!SwingAnimation.animatsiya.is("Off")) {
                           if (SwingAnimation.check()) {
                              float floatValue = SwingAnimation.skorostAnimatsii.getValue();
                              if (!(floatValue <= 0.0F)) {
                                 int intValue = 6;
                                 if (StatusEffectUtil.hasHaste(livingEntity4)) {
                                    intValue = Math.max(1, intValue - (1 + StatusEffectUtil.getHasteAmplifier(livingEntity4)));
                                 } else if (this.hasStatusEffect(StatusEffects.MINING_FATIGUE)) {
                                    StatusEffectInstance statusEffectInstance = this.getStatusEffect(StatusEffects.MINING_FATIGUE);
                                    if (statusEffectInstance != null) {
                                       intValue += (1 + statusEffectInstance.getAmplifier()) * 2;
                                    }
                                 }

                                 int intValue2 = Math.max(1, (int)(intValue / floatValue));
                                 callbackInfoReturnable.setReturnValue(intValue2);
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   @ModifyConstant(
      method = {"tickMovement"},
      constant = {@Constant(
         intValue = 10
      )}
   )
   private int modifyJumpTicks(int i) {
      LivingEntity livingEntity5 = (LivingEntity)(Object)this;
      if (livingEntity5 instanceof ClientPlayerEntity) {
         NoDelay noDelay = WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null
            ? (NoDelay)WildClient.INSTANCE.moduleManager.findModule(NoDelay.class)
            : null;
         if (noDelay != null && noDelay.enabled && NoDelay.pryzhki.isEnabled()) {
            return (int)NoDelay.skorostPryzhka.getValue();
         }
      }

      return i;
   }

   @Inject(
      method = {"calcGlidingVelocity"},
      at = {@At("RETURN")},
      cancellable = true
   )
   private void wild$onCalcGlidingVelocity(Vec3d vec3d, CallbackInfoReturnable<Vec3d> callbackInfoReturnable) {
      LivingEntity livingEntity6 = (LivingEntity)(Object)this;
      if (livingEntity6 instanceof ClientPlayerEntity) {
         if (WildClient.isInitialized() && WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null) {
            GrimGlide grimGlide = WildClient.INSTANCE.moduleManager.getModule(GrimGlide.class);
            if (grimGlide != null && grimGlide.enabled) {
               EntityVelocityEvent entityVelocityEvent = new EntityVelocityEvent(vec3d.multiply(0.99F, 0.98F, 0.99F));
               EventManager.post((Event)entityVelocityEvent);
               callbackInfoReturnable.setReturnValue(entityVelocityEvent.getVec3d());
            }
         }
      }
   }
}
