package ru.metaculture.protection;

import lombok.Generated;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Hand;

public class HandRenderEvent extends Event {
   private MatrixStack matrixStack;
   private Hand hand;
   private float floatValue;

   @Generated
   public HandRenderEvent(MatrixStack matrixStack, Hand hand, float f) {
      this.matrixStack = matrixStack;
      this.hand = hand;
      this.floatValue = f;
   }

   @Generated
   public MatrixStack getMatrixStack() {
      return this.matrixStack;
   }

   @Generated
   public Hand getHand() {
      return this.hand;
   }

   @Generated
   public float getFloatValue() {
      return this.floatValue;
   }

   @Generated
   public void setMatrixStack(MatrixStack matrixStack) {
      this.matrixStack = matrixStack;
   }

   @Generated
   public void setHand(Hand hand) {
      this.hand = hand;
   }

   @Generated
   public void setFloatValue(float f) {
      this.floatValue = f;
   }
}
