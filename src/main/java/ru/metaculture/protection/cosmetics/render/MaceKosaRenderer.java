package ru.metaculture.protection.cosmetics.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.state.ArmedEntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import ru.metaculture.protection.ChamsRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.RotationAxis;
import ru.metaculture.protection.Cosmetics;
import ru.metaculture.protection.WildClient;
import ru.metaculture.protection.cosmetics.CosmeticPack;
import ru.metaculture.protection.cosmetics.geckolib.GeckolibCosmeticRenderer;
import ru.metaculture.protection.cosmetics.model.CosmeticModel;

public final class MaceKosaRenderer {
   public static final String COSMETIC_NAME = "Булава - Коса";
   public static final String COSMETIC_TYPE = "bodywear";

   private MaceKosaRenderer() {
   }

   public static boolean isSelectedCosmetic(String type, String name) {
      return COSMETIC_TYPE.equals(type) && COSMETIC_NAME.equals(name);
   }

   public static boolean tryRenderHeld(
      LivingEntity entity, ItemStack stack, ItemDisplayContext context, MatrixStack matrices, VertexConsumerProvider buffers, int light
   ) {
      if (!shouldReplace(entity, stack, context)) {
         return false;
      }

      renderModel(context, matrices, buffers, light);
      return true;
   }

   public static boolean tryRenderThirdPerson(
      ArmedEntityRenderState state, Arm arm, MatrixStack matrices, VertexConsumerProvider buffers, int light
   ) {
      MinecraftClient client = MinecraftClient.getInstance();
      if (client.world == null || state == null) {
         return false;
      }

      int entityId = resolveEntityId(state);
      if (entityId == Integer.MIN_VALUE) {
         return false;
      }

      Entity entity = client.world.getEntityById(entityId);
      if (!(entity instanceof LivingEntity living)) {
         return false;
      }

      ItemStack stack = living.getStackInHand(living.getMainArm() == arm ? Hand.MAIN_HAND : Hand.OFF_HAND);
      ItemDisplayContext context = arm == Arm.LEFT ? ItemDisplayContext.THIRD_PERSON_LEFT_HAND : ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
      return tryRenderHeld(living, stack, context, matrices, buffers, light);
   }

   public static boolean shouldReplace(LivingEntity entity, ItemStack stack, ItemDisplayContext context) {
      if (entity == null || stack == null || stack.isEmpty() || !stack.isOf(Items.MACE) || context == null || !isHandContext(context)) {
         return false;
      }

      Cosmetics cosmetics = resolveCosmetics();
      if (cosmetics == null || !cosmetics.enabled || !COSMETIC_NAME.equals(cosmetics.bodywear.getValue())) {
         return false;
      }

      MinecraftClient client = MinecraftClient.getInstance();
      if (client.player != null && entity != client.player && !cosmetics.naDrugihIgrokah.isEnabled()) {
         return false;
      }

      return CosmeticPack.resolve(COSMETIC_TYPE, COSMETIC_NAME) != null;
   }

   private static void renderModel(ItemDisplayContext context, MatrixStack matrices, VertexConsumerProvider buffers, int light) {
      CosmeticModel model = CosmeticPack.resolve(COSMETIC_TYPE, COSMETIC_NAME);
      if (model == null) {
         return;
      }

      matrices.push();
      applyHandTransform(context, matrices);
      GeckolibCosmeticRenderer.getInstance().renderCosmetic(model, matrices, buffers, light);
      matrices.pop();
   }

   private static void applyHandTransform(ItemDisplayContext context, MatrixStack matrices) {
      boolean left = context == ItemDisplayContext.FIRST_PERSON_LEFT_HAND || context == ItemDisplayContext.THIRD_PERSON_LEFT_HAND;
      if (left) {
         matrices.scale(-1.0F, 1.0F, 1.0F);
      }

      if (context.isFirstPerson()) {
         matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-90.0F));
         matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(25.0F));
         matrices.translate(0.06F, 0.18F, 0.06F);
         matrices.scale(0.62F, 0.62F, 0.62F);
      } else {
         matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-90.0F));
         matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(55.0F));
         matrices.translate(0.0F, 0.22F, 0.04F);
         matrices.scale(0.72F, 0.72F, 0.72F);
      }

      matrices.translate(0.0F, -0.2F, 0.0F);
   }

   private static int resolveEntityId(ArmedEntityRenderState state) {
      if (state instanceof PlayerEntityRenderState playerState) {
         return playerState.id;
      }

      if (state instanceof ChamsRenderState chamsState) {
         return chamsState.wild$getEntityId();
      }

      return Integer.MIN_VALUE;
   }

   private static boolean isHandContext(ItemDisplayContext context) {
      return context == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND
         || context == ItemDisplayContext.FIRST_PERSON_LEFT_HAND
         || context == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND
         || context == ItemDisplayContext.THIRD_PERSON_LEFT_HAND;
   }

   private static Cosmetics resolveCosmetics() {
      if (WildClient.INSTANCE == null || WildClient.INSTANCE.moduleManager == null) {
         return null;
      }

      return WildClient.INSTANCE.moduleManager.getModule(Cosmetics.class);
   }
}
