package org.wild.mixin.acceser;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({MinecraftClient.class})
public interface MinecraftClientAccessor {
   @Accessor("attackCooldown")
   void setAttackCooldown(int i);

   @Accessor("attackCooldown")
   int getAttackCooldown();

   @Accessor("itemUseCooldown")
   void setItemUseCooldown(int i);

   @Accessor("itemUseCooldown")
   int getItemUseCooldown();

   @Invoker("doAttack")
   boolean invokeDoAttack();

   @Invoker("doItemUse")
   void invokeDoItemUse();
}
