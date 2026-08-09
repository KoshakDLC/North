package ru.metaculture.protection;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Hand;

public class HandVisibilityEvent extends Event {
   private final MatrixStack matrixStack;
   private final Hand hand;

   public HandVisibilityEvent(MatrixStack matrixStack, Hand hand) {
      this.matrixStack = matrixStack;
      this.hand = hand;
   }

   public MatrixStack getMatrixStack() {
      return this.matrixStack;
   }

   public Hand getHand() {
      return this.hand;
   }

   public boolean check() {
      return this.hand == Hand.MAIN_HAND;
   }
}
