package ru.metaculture.protection.cosmetics.render;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;

public final class RenderStack {
   private MatrixStack stack;

   public void update(MatrixStack matrices) {
      this.stack = matrices;
   }

   public void push() {
      this.stack.push();
   }

   public void pop() {
      this.stack.pop();
   }

   public void translate(float x, float y, float z) {
      this.stack.translate(x, y, z);
   }

   public void scale(float x, float y, float z) {
      this.stack.scale(x, y, z);
   }

   public void rotateDegrees(float pitch, float yaw, float roll) {
      if (roll != 0.0F) {
         this.stack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(roll));
      }

      if (yaw != 0.0F) {
         this.stack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(yaw));
      }

      if (pitch != 0.0F) {
         this.stack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(pitch));
      }
   }

   public void rotateXDegrees(float degrees) {
      this.stack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(degrees));
   }

   public void rotateYDegrees(float degrees) {
      this.stack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(degrees));
   }

   public void rotateZDegrees(float degrees) {
      this.stack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(degrees));
   }
}
