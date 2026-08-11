package ru.metaculture.protection.cosmetics.render;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import ru.metaculture.protection.cosmetics.geckolib.GeckolibCosmeticRenderer;
import ru.metaculture.protection.cosmetics.model.CosmeticModel;
import ru.metaculture.protection.cosmetics.model.ModelPosition;

public final class CosmeticRenderer {
   private static CosmeticRenderer instance;
   private final GeckolibCosmeticRenderer geckolibRenderer = GeckolibCosmeticRenderer.getInstance();
   private final RenderStack stack = new RenderStack();

   public static CosmeticRenderer getInstance() {
      if (instance == null) {
         instance = new CosmeticRenderer();
      }

      return instance;
   }

   public void renderCosmetic(
      CosmeticModel cosmetic, PlayerEntityModel playerModel, MatrixStack matrices, VertexConsumerProvider buffers, int light
   ) {
      if (cosmetic == null || cosmetic.getTextureId() == null) {
         return;
      }

      this.stack.update(matrices);
      this.stack.push();
      float extraY = this.transformToPosition(cosmetic, playerModel);
      this.stack.rotateZDegrees(180.0F);
      this.stack.translate(cosmetic.getX(), cosmetic.getY() + extraY, cosmetic.getZ());
      this.stack.rotateYDegrees(cosmetic.getYaw());
      this.stack.rotateXDegrees(cosmetic.getPitch());
      this.stack.rotateZDegrees(cosmetic.getRoll());
      this.stack.scale(cosmetic.getScale(), cosmetic.getScale(), cosmetic.getScale());
      this.geckolibRenderer.renderCosmetic(cosmetic, matrices, buffers, light);
      this.stack.pop();
   }

   private float transformToPosition(CosmeticModel cosmetic, PlayerEntityModel playerModel) {
      if (playerModel == null) {
         return 0.0F;
      }

      return switch (cosmetic.getPosition()) {
         case HEAD -> {
            this.transformToModelPart(playerModel.head);
            // 0.5 sits on the crown; Pulse hats/masks were floating above it.
            yield 0.18F;
         }
         case ABOVE_HEAD -> 0.75F;
         case BODY -> {
            this.transformToModelPart(playerModel.body);
            yield -0.3F;
         }
         case RIGHT_ARM -> {
            this.transformToModelPart(playerModel.rightArm);
            yield -0.25F;
         }
         case LEFT_ARM -> {
            this.transformToModelPart(playerModel.leftArm);
            yield -0.25F;
         }
         case RIGHT_LEG -> {
            this.transformToModelPart(playerModel.rightLeg);
            yield -0.35F;
         }
         case LEFT_LEG -> {
            this.transformToModelPart(playerModel.leftLeg);
            yield -0.35F;
         }
         case FREE -> 0.0F;
      };
   }

   private void transformToModelPart(ModelPart part) {
      this.stack.translate(part.originX * 0.0625F, part.originY * 0.0625F, part.originZ * 0.0625F);
      this.stack.rotateDegrees(
         part.pitch * (180.0F / (float)Math.PI),
         part.yaw * (180.0F / (float)Math.PI),
         part.roll * (180.0F / (float)Math.PI)
      );
   }
}
