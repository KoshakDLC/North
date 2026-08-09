package ru.metaculture.protection;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Hand;

public class FirstPersonItemRenderEvent extends Event {
   private final MatrixStack matrixStack;
   private final Hand hand;
   private final float floatValue;
   private final float floatValue2;

   public FirstPersonItemRenderEvent(MatrixStack matrixStack, Hand hand, float f, float g) {
      this.matrixStack = matrixStack;
      this.hand = hand;
      this.floatValue = f;
      this.floatValue2 = g;
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

   public float getFloatValue() {
      return this.floatValue;
   }

   public float getFloatValue2() {
      return this.floatValue2;
   }
}
