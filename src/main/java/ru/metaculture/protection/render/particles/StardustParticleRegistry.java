package ru.metaculture.protection;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class StardustParticleRegistry {
   public static final Identifier IDENTIFIER = Identifier.of("wild", "floating_stardust");
   public static final Identifier IDENTIFIER_2 = Identifier.of("wild", "shooting_star");
   public static final SimpleParticleType SIMPLE_PARTICLE_TYPE = FabricParticleTypes.simple(true);
   public static final SimpleParticleType SIMPLE_PARTICLE_TYPE_2 = FabricParticleTypes.simple(true);
   private static boolean flag;

   private StardustParticleRegistry() {
   }

   public static void invoke() {
      if (!flag) {
         flag = true;
         StardustShaderRegistry.invoke();
         StardustSkyRenderer.invoke();
         FloatingStardustParticle.simpleParticleType = SIMPLE_PARTICLE_TYPE;
         ShootingStarParticle.simpleParticleType = SIMPLE_PARTICLE_TYPE_2;
         Registry.register(Registries.PARTICLE_TYPE, IDENTIFIER, SIMPLE_PARTICLE_TYPE);
         Registry.register(Registries.PARTICLE_TYPE, IDENTIFIER_2, SIMPLE_PARTICLE_TYPE_2);
         ParticleFactoryRegistry.getInstance().register(SIMPLE_PARTICLE_TYPE, new FloatingStardustParticle.FloatingStardustParticleState());
         ParticleFactoryRegistry.getInstance().register(SIMPLE_PARTICLE_TYPE_2, new ShootingStarParticle.ShootingStarParticleState());
      }
   }
}
