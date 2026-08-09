package org.wild.mixin;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particle.ParticleEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.metaculture.protection.Removals;

@Mixin({ParticleManager.class})
public class ParticleManagerMixin {
   @Inject(
      method = {"addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)Lnet/minecraft/client/particle/Particle;"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void wild$cancelParticle(
      ParticleEffect particleEffect, double d, double e, double f, double g, double h, double i, CallbackInfoReturnable<Particle> callbackInfoReturnable
   ) {
      if (Removals.check6(particleEffect)) {
         callbackInfoReturnable.setReturnValue(null);
      }
   }
}
