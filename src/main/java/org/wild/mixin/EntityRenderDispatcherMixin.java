package org.wild.mixin;

import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.decoration.GlowItemFrameEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.metaculture.protection.Removals;

@Mixin({EntityRenderDispatcher.class})
public class EntityRenderDispatcherMixin {
   @Inject(
      method = {"shouldRender"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private <E extends Entity> void litka$skipEntities(
      E entity, Frustum frustum, double d, double e, double f, CallbackInfoReturnable<Boolean> callbackInfoReturnable
   ) {
      if (Removals.check2("Стойки брони") && entity instanceof ArmorStandEntity) {
         callbackInfoReturnable.setReturnValue(false);
      } else if (!Removals.check2("Рамки") || !(entity instanceof ItemFrameEntity) && !(entity instanceof GlowItemFrameEntity)) {
         if (Removals.check2("Картины") && entity instanceof PaintingEntity) {
            callbackInfoReturnable.setReturnValue(false);
         } else if (Removals.check2("Дроп предметов") && entity instanceof ItemEntity) {
            callbackInfoReturnable.setReturnValue(false);
         } else {
            if (Removals.check2("Опыт-орбы") && entity instanceof ExperienceOrbEntity) {
               callbackInfoReturnable.setReturnValue(false);
            }
         }
      } else {
         if (Removals.NE_SKRYVAT_KARTY.isEnabled()) {
            ItemStack itemStack = ((ItemFrameEntity)entity).getHeldItemStack();
            if (itemStack != null && itemStack.isOf(Items.FILLED_MAP)) {
               return;
            }
         }

         callbackInfoReturnable.setReturnValue(false);
      }
   }
}
