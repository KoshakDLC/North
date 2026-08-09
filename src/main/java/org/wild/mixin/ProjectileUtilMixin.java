package org.wild.mixin;

import java.util.function.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import ru.metaculture.protection.FriendCommand;
import ru.metaculture.protection.FriendManager;
import ru.metaculture.protection.WildClient;

@Mixin({ProjectileUtil.class})
public class ProjectileUtilMixin {
   @ModifyVariable(
      method = {"raycast"},
      at = @At("HEAD"),
      argsOnly = true
   )
   private static Predicate<Entity> litka$ignoreFriendsCollision(Predicate<Entity> predicate) {
      return entity -> {
         if (entity instanceof PlayerEntity playerEntity) {
            FriendManager friendManager = WildClient.INSTANCE.moduleManager.getModule(FriendManager.class);
            if (friendManager != null && friendManager.enabled && FriendManager.ubiratHitboksDruga.isEnabled() && FriendCommand.check(playerEntity.getName().getString())) {
               return false;
            }
         }

         return predicate != null && predicate.test(entity);
      };
   }
}
