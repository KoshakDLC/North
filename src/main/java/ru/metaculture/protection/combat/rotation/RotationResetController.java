package ru.metaculture.protection;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public final class RotationResetController {
   private int intValue = -1;

   void invoke(Rotation rotation, LivingEntity livingEntity, float f, float g) {
      if (livingEntity.getId() != this.intValue) {
         this.setIntValue(livingEntity.getId());
      }

      float floatValue = rotation.floatValue + f;
      float floatValue2 = MathHelper.clamp(rotation.floatValue2 + g, -90.0F, 90.0F);
      RotationController.invoke3(new Rotation(floatValue, floatValue2), Math.abs(f), Math.abs(g), 20.0F, 20.0F, 1, 15, false);
   }

   public void invoke2(Rotation rotation2, float f, float g, int i, int j) {
      this.invoke3(rotation2, f, g, 20.0F, 20.0F, i, j);
   }

   public void invoke3(Rotation rotation3, float f, float g, float h, float i, int j, int k) {
      this.setIntValue(-1);
      RotationController.invoke3(rotation3, f, g, h, i, j, k, false);
   }

   public void invoke4() {
      this.setIntValue(-1);
   }

   private void setIntValue(int i) {
      this.intValue = i;
   }
}
