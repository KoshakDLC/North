package org.wild.mixin;

import net.minecraft.block.BubbleColumnBlock;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import ru.metaculture.protection.Removals;

@Mixin({BubbleColumnBlock.class})
public class BubbleColumnBlockMixin {
   @Redirect(
      method = {"randomDisplayTick"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/World;addImportantParticleClient(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V"
      )
   )
   private void litka$noSoulSandBubbles(World world, ParticleEffect particleEffect, double d, double e, double f, double g, double h, double i) {
      if (!this.shouldSkip(particleEffect)) {
         world.addImportantParticleClient(particleEffect, d, e, f, g, h, i);
      }
   }

   private boolean shouldSkip(ParticleEffect particleEffect) {
      return particleEffect != ParticleTypes.BUBBLE_COLUMN_UP ? false : Removals.check4(particleEffect);
   }
}
