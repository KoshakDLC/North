package org.wild.mixin;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.client.render.entity.state.ItemEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ItemEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import ru.metaculture.protection.ItemPhysic;
import ru.metaculture.protection.ItemPhysicsStateAccessor;

@Mixin({ItemEntityRenderer.class})
public abstract class ItemEntityRendererMixin {
   @Inject(
      method = {"updateRenderState(Lnet/minecraft/entity/ItemEntity;Lnet/minecraft/client/render/entity/state/ItemEntityRenderState;F)V"},
      at = {@At("TAIL")}
   )
   private void wild$updateItemPhysicState(ItemEntity itemEntity, ItemEntityRenderState itemEntityRenderState, float f, CallbackInfo callbackInfo) {
      if (itemEntityRenderState instanceof ItemPhysicsStateAccessor itemPhysicsStateAccessor) {
         itemPhysicsStateAccessor.wild$setItemPhysicOnGround(itemEntity.isOnGround());
      }
   }

   @ModifyArgs(
      method = {"render(Lnet/minecraft/client/render/entity/state/ItemEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/util/math/MatrixStack;translate(FFF)V",
         ordinal = 0
      )
   )
   private void wild$removeGroundBob(
      Args args, ItemEntityRenderState itemEntityRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i
   ) {
      if (ItemPhysic.check(itemEntityRenderState)) {
         Box box = itemEntityRenderState.itemRenderState.getModelBoundingBox();
         float floatValue = (float)Math.max(0.0, -box.minY + ItemPhysic.measure());
         args.set(1, floatValue);
      }
   }

   @Inject(
      method = {"render(Lnet/minecraft/client/render/entity/state/ItemEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/render/entity/ItemEntityRenderer;renderStack(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/ItemStackEntityRenderState;Lnet/minecraft/util/math/random/Random;Lnet/minecraft/util/math/Box;)V",
         shift = Shift.BEFORE
      )}
   )
   private void wild$applyItemPhysicTransform(
      ItemEntityRenderState itemEntityRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo callbackInfo
   ) {
      if (ItemPhysic.check(itemEntityRenderState) || ItemPhysic.check2(itemEntityRenderState)) {
         float floatValue2 = ItemEntity.getRotation(itemEntityRenderState.age, itemEntityRenderState.uniqueOffset);
         matrixStack.multiply(RotationAxis.POSITIVE_Y.rotation(-floatValue2));
         if (ItemPhysic.check(itemEntityRenderState)) {
            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(ItemPhysic.measure2()));
         } else {
            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(ItemPhysic.measure3(itemEntityRenderState.age)));
         }
      }
   }
}
