package org.wild.mixin;

import com.google.common.base.MoreObjects;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.metaculture.protection.Event;
import ru.metaculture.protection.EventManager;
import ru.metaculture.protection.Hands;
import ru.metaculture.protection.NoSlow;
import ru.metaculture.protection.HandRenderEvent;
import ru.metaculture.protection.HandVisibilityEvent;
import ru.metaculture.protection.FreeLookController;
import ru.metaculture.protection.HandMaskRenderer;
import ru.metaculture.protection.SwingAnimation;
import ru.metaculture.protection.WildClient;

@Environment(EnvType.CLIENT)
@Mixin({HeldItemRenderer.class})
public abstract class HeldItemRendererMixin {
   @Unique
   private Hand wild$currentHand;
   @Shadow
   private ItemStack mainHand;
   @Shadow
   private ItemStack offHand;
   @Shadow
   private float equipProgressMainHand;
   @Shadow
   private float lastEquipProgressMainHand;
   @Shadow
   private float equipProgressOffHand;
   @Shadow
   private float lastEquipProgressOffHand;

   @Shadow
   protected abstract void renderFirstPersonItem(
      AbstractClientPlayerEntity abstractClientPlayerEntity,
      float f,
      float g,
      Hand hand,
      float h,
      ItemStack itemStack,
      float i,
      MatrixStack matrixStack,
      VertexConsumerProvider vertexConsumerProvider,
      int j
   );

   @Inject(
      method = {"renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/network/ClientPlayerEntity;I)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void fullRenderItemOverride(
      float f, MatrixStack matrixStack, Immediate immediate, ClientPlayerEntity clientPlayerEntity, int i, CallbackInfo callbackInfo
   ) {
      callbackInfo.cancel();
      Hands hands = WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null ? WildClient.INSTANCE.moduleManager.getModule(Hands.class) : null;
      HandMaskRenderer handMaskRenderer = HandMaskRenderer.getINSTANCE();
      handMaskRenderer.invoke(
         hands != null && hands.check(Hand.MAIN_HAND),
         hands != null && hands.check(Hand.OFF_HAND),
         MinecraftClient.getInstance().getWindow().getFramebufferWidth(),
         MinecraftClient.getInstance().getWindow().getFramebufferHeight()
      );
      float floatValue = clientPlayerEntity.getHandSwingProgress(f);
      Hand hand2 = (Hand)MoreObjects.firstNonNull(clientPlayerEntity.preferredHand, Hand.MAIN_HAND);
      float floatValue2 = clientPlayerEntity.getLerpedPitch(f);
      float floatValue3 = MathHelper.lerp(f, clientPlayerEntity.lastRenderPitch, clientPlayerEntity.renderPitch);
      float floatValue4 = MathHelper.lerp(f, clientPlayerEntity.lastRenderYaw, clientPlayerEntity.renderYaw);
      MinecraftClient client = MinecraftClient.getInstance();
      if (FreeLookController.active) {
         matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(0.0F));
         matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(0.0F));
      } else {
         matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees((clientPlayerEntity.getPitch(f) - floatValue3) * 0.1F));
         matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((clientPlayerEntity.getYaw(f) - floatValue4) * 0.1F));
      }

      boolean flag = true;
      boolean flag2 = true;
      ItemStack itemStack2 = clientPlayerEntity.getMainHandStack();
      ItemStack itemStack3 = NoSlow.resolve(clientPlayerEntity.getOffHandStack());
      boolean flag3 = itemStack2.isOf(Items.BOW) || itemStack3.isOf(Items.BOW);
      boolean flag4 = itemStack2.isOf(Items.CROSSBOW) || itemStack3.isOf(Items.CROSSBOW);
      if (flag3 || flag4) {
         if (clientPlayerEntity.isUsingItem()) {
            ItemStack itemStack4 = clientPlayerEntity.getActiveItem();
            Hand hand3 = clientPlayerEntity.getActiveHand();
            if (itemStack4.isOf(Items.BOW) || itemStack4.isOf(Items.CROSSBOW)) {
               flag = hand3 == Hand.MAIN_HAND;
               flag2 = hand3 == Hand.OFF_HAND;
            }
         } else if (this.isChargedCrossbow(itemStack2)) {
            flag2 = false;
         }
      }

      if (flag) {
         float floatValue5 = hand2 == Hand.MAIN_HAND ? floatValue : 0.0F;
         float floatValue6 = 1.0F - MathHelper.lerp(f, this.lastEquipProgressMainHand, this.equipProgressMainHand);
         matrixStack.push();
         HandVisibilityEvent handVisibilityEvent = new HandVisibilityEvent(matrixStack, Hand.MAIN_HAND);
         EventManager.post((Event)handVisibilityEvent);
         boolean flag5 = hands != null && hands.check(Hand.MAIN_HAND);
         Object object = flag5 ? handMaskRenderer.resolve(Hand.MAIN_HAND, immediate) : immediate;
         this.wild$currentHand = Hand.MAIN_HAND;

         try {
            this.renderFirstPersonItem(clientPlayerEntity, f, floatValue2, Hand.MAIN_HAND, floatValue5, this.mainHand, floatValue6, matrixStack, (VertexConsumerProvider)object, i);
         } finally {
            this.wild$currentHand = null;
            if (flag5) {
               handMaskRenderer.invoke2(Hand.MAIN_HAND);
            }

            matrixStack.pop();
         }
      }

      if (flag2) {
         float floatValue7 = hand2 == Hand.OFF_HAND ? floatValue : 0.0F;
         float floatValue8 = 1.0F - MathHelper.lerp(f, this.lastEquipProgressOffHand, this.equipProgressOffHand);
         matrixStack.push();
         HandVisibilityEvent handVisibilityEvent2 = new HandVisibilityEvent(matrixStack, Hand.OFF_HAND);
         EventManager.post((Event)handVisibilityEvent2);
         boolean flag6 = hands != null && hands.check(Hand.OFF_HAND);
         Object object2 = flag6 ? handMaskRenderer.resolve(Hand.OFF_HAND, immediate) : immediate;
         this.wild$currentHand = Hand.OFF_HAND;

         try {
            this.renderFirstPersonItem(
               clientPlayerEntity, f, floatValue2, Hand.OFF_HAND, floatValue7, NoSlow.resolve(this.offHand), floatValue8, matrixStack, (VertexConsumerProvider)object2, i
            );
         } finally {
            this.wild$currentHand = null;
            if (flag6) {
               handMaskRenderer.invoke2(Hand.OFF_HAND);
            }

            matrixStack.pop();
         }
      }

      immediate.draw();
   }

   @WrapOperation(
      method = {"renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemDisplayContext;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/render/item/ItemRenderer;renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemDisplayContext;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/world/World;III)V"
      )}
   )
   private void wild$renderScaledItem(
      ItemRenderer itemRenderer,
      LivingEntity livingEntity,
      ItemStack itemStack,
      ItemDisplayContext itemDisplayContext,
      MatrixStack matrixStack,
      VertexConsumerProvider vertexConsumerProvider,
      World world,
      int i,
      int j,
      int k,
      Operation<Void> operation
   ) {
      VertexConsumerProvider vertexConsumerProvider2 = vertexConsumerProvider;
      Hands hands2 = WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null ? WildClient.INSTANCE.moduleManager.getModule(Hands.class) : null;
      if (this.wild$currentHand != null && hands2 != null && hands2.check(this.wild$currentHand)) {
         vertexConsumerProvider2 = HandMaskRenderer.getINSTANCE().resolve2(this.wild$currentHand, vertexConsumerProvider);
      }

      float floatValue9 = SwingAnimation.measure(this.wild$currentHand);
      if (Math.abs(floatValue9 - 1.0F) <= 1.0E-4F) {
         operation.call(new Object[]{itemRenderer, livingEntity, itemStack, itemDisplayContext, matrixStack, vertexConsumerProvider2, world, i, j, k});
      } else {
         matrixStack.push();
         matrixStack.scale(floatValue9, floatValue9, floatValue9);

         try {
            operation.call(new Object[]{itemRenderer, livingEntity, itemStack, itemDisplayContext, matrixStack, vertexConsumerProvider2, world, i, j, k});
         } finally {
            matrixStack.pop();
         }
      }
   }

   @WrapOperation(
      method = {"renderFirstPersonItem"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/render/item/HeldItemRenderer;swingArm(FFLnet/minecraft/client/util/math/MatrixStack;ILnet/minecraft/util/Arm;)V",
         ordinal = 2
      )}
   )
   private void handAnimationHook(
      HeldItemRenderer heldItemRenderer,
      float f,
      float g,
      MatrixStack matrixStack,
      int i,
      Arm arm,
      Operation<Void> operation,
      @Local(ordinal = 0,argsOnly = true) AbstractClientPlayerEntity abstractClientPlayerEntity,
      @Local(ordinal = 0,argsOnly = true) Hand hand
   ) {
      HandRenderEvent handRenderEvent = new HandRenderEvent(matrixStack, hand, f);
      EventManager.post((Event)handRenderEvent);
      if (!handRenderEvent.isInvalidated()) {
         operation.call(new Object[]{heldItemRenderer, f, g, matrixStack, i, arm});
      }
   }

   @Unique
   private boolean isChargedCrossbow(ItemStack itemStack) {
      return itemStack.isOf(Items.CROSSBOW) && CrossbowItem.isCharged(itemStack);
   }
}
