package ru.metaculture.protection;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Items;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "BowHelper",
   description = "Плавная наводка без тряски",
   category = Category.Combat
)
public class BowHelper extends Module {
   public NumberSetting distantsiya = new NumberSetting("Дистанция", 30.0F, 1.0F, 50.0F, 1.0F, false);
   public BooleanSetting ignorDruzey = new BooleanSetting("Игнор друзей", true);
   public static LivingEntity livingEntity = null;
   private boolean flag = false;

   public BowHelper() {
      this.addSettings(new Setting[]{this.distantsiya, this.ignorDruzey});
   }

   @Override
   public void onEnable() {
      super.onEnable();
   }

   @Override
   public void onDisable() {
      RotationController.rotationControllerState2 = RotationController.RotationControllerState2.IDLE;
      RotationController.intValue = 0;
      RotationController.rotation = null;
      FreeLookController.active = false;
      super.onDisable();
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player != null && CLIENT.world != null) {
         RotationController.rotationControllerState2 = RotationController.RotationControllerState2.IDLE;
         boolean flag = CLIENT.player.getMainHandStack().getItem() instanceof BowItem
            || CLIENT.player.getOffHandStack().getItem() instanceof BowItem
            || CLIENT.player.getMainHandStack().getItem() instanceof CrossbowItem
            || CLIENT.player.getOffHandStack().getItem() instanceof CrossbowItem;
         if (!flag) {
            this.invoke();
         } else {
            boolean flag2 = CLIENT.player.isUsingItem() && CLIENT.player.getActiveItem().getItem() instanceof BowItem;
            boolean flag3 = CLIENT.player.getMainHandStack().isOf(Items.CROSSBOW) && CrossbowItem.isCharged(CLIENT.player.getMainHandStack())
               || CLIENT.player.getOffHandStack().isOf(Items.CROSSBOW) && CrossbowItem.isCharged(CLIENT.player.getOffHandStack());
            if (livingEntity != null && !this.check(livingEntity)) {
               livingEntity = null;
            }

            if (livingEntity == null) {
               livingEntity = this.resolve();
            }

            if (livingEntity != null) {
               if (!this.flag) {
                  FreeLookController.active = true;
                  this.flag = true;
               }

               if (flag2 || flag3) {
                  float floatValue = this.measure4();
                  Vec3d vec3d = CLIENT.player.getEyePos();
                  Vec3d vec3d2 = livingEntity.getPos().add(0.0, livingEntity.getHeight() * 0.5 + 0.1, 0.0);
                  double doubleValue = livingEntity.getX() - livingEntity.lastX;
                  double doubleValue2 = livingEntity.getZ() - livingEntity.lastZ;
                  double doubleValue3 = Math.sqrt(doubleValue * doubleValue + doubleValue2 * doubleValue2);
                  Vec3d vec3d3 = vec3d2;
                  float floatValue2 = 0.0F;

                  for (int intValue = 0; intValue < 3; intValue++) {
                     double doubleValue4 = Math.cos(Math.toRadians(floatValue2));
                     float floatValue3 = (float)(floatValue * Math.max(doubleValue4, 0.1));
                     if (doubleValue3 > 0.01) {
                        Vec3d vec3d4 = vec3d2;

                        for (int intValue2 = 0; intValue2 < 25; intValue2++) {
                           double doubleValue5 = vec3d4.x - vec3d.x;
                           double doubleValue6 = vec3d4.z - vec3d.z;
                           double doubleValue7 = Math.sqrt(doubleValue5 * doubleValue5 + doubleValue6 * doubleValue6);
                           double doubleValue8 = this.measure3(doubleValue7, floatValue3);
                           vec3d4 = new Vec3d(vec3d2.x + doubleValue * doubleValue8, vec3d2.y, vec3d2.z + doubleValue2 * doubleValue8);
                        }

                        vec3d3 = vec3d4;
                     }

                     double doubleValue9 = vec3d3.x - vec3d.x;
                     double doubleValue10 = vec3d3.z - vec3d.z;
                     double doubleValue11 = Math.sqrt(doubleValue9 * doubleValue9 + doubleValue10 * doubleValue10);
                     double doubleValue12 = vec3d3.y - vec3d.y;
                     floatValue2 = this.measure(doubleValue11, doubleValue12, floatValue);
                  }

                  double doubleValue13 = vec3d3.x - vec3d.x;
                  double doubleValue14 = vec3d3.z - vec3d.z;
                  float floatValue4 = (float)Math.toDegrees(Math.atan2(-doubleValue13, doubleValue14));
                  CLIENT.player.setYaw(floatValue4);
                  CLIENT.player.setPitch(floatValue2);
                  CLIENT.player.headYaw = floatValue4;
               }
            }
         }
      }
   }

   private void invoke() {
      if (this.flag) {
         CLIENT.player.setYaw(FreeLookController.floatValue);
         CLIENT.player.setPitch(FreeLookController.floatValue2);
         CLIENT.player.headYaw = FreeLookController.floatValue;
         FreeLookController.active = false;
         this.flag = false;
      }

      livingEntity = null;
   }

   private float measure(double d, double e, float f) {
      if (d < 0.5) {
         return 0.0F;
      } else {
         float floatValue5 = -89.0F;
         float floatValue6 = 89.0F;

         for (int intValue3 = 0; intValue3 < 60; intValue3++) {
            float floatValue7 = (floatValue5 + floatValue6) / 2.0F;
            double doubleValue15 = this.measure2(d, floatValue7, f);
            if (doubleValue15 > e) {
               floatValue5 = floatValue7;
            } else {
               floatValue6 = floatValue7;
            }
         }

         return (floatValue5 + floatValue6) / 2.0F;
      }
   }

   private double measure2(double d, float f, float g) {
      double doubleValue16 = Math.toRadians(f);
      double doubleValue17 = g * Math.cos(doubleValue16);
      double doubleValue18 = -g * Math.sin(doubleValue16);
      double doubleValue19 = 0.0;
      double doubleValue20 = 0.0;

      for (int intValue4 = 0; intValue4 < 500; intValue4++) {
         double doubleValue21 = doubleValue19;
         double doubleValue22 = doubleValue20;
         doubleValue19 += doubleValue17;
         doubleValue20 += doubleValue18;
         doubleValue17 *= 0.99;
         doubleValue18 *= 0.99;
         doubleValue18 -= 0.05;
         if (doubleValue19 >= d) {
            double doubleValue23 = doubleValue19 - doubleValue21 > 0.001 ? (d - doubleValue21) / (doubleValue19 - doubleValue21) : 1.0;
            return doubleValue22 + (doubleValue20 - doubleValue22) * doubleValue23;
         }
      }

      return doubleValue20;
   }

   private double measure3(double d, float f) {
      double doubleValue24 = f;
      double doubleValue25 = 0.0;

      for (int intValue5 = 0; intValue5 < 500; intValue5++) {
         doubleValue25 += doubleValue24;
         doubleValue24 *= 0.99;
         if (doubleValue25 >= d) {
            return intValue5 + 1;
         }
      }

      return 500.0;
   }

   private float measure4() {
      boolean flag4 = CLIENT.player.getMainHandStack().getItem() instanceof CrossbowItem
         || CLIENT.player.getOffHandStack().getItem() instanceof CrossbowItem;
      if (flag4) {
         return 3.15F;
      } else {
         float floatValue8 = 1.0F;
         if (CLIENT.player.isUsingItem() && CLIENT.player.getActiveItem().getItem() instanceof BowItem) {
            int intValue6 = CLIENT.player.getItemUseTime();
            float floatValue9 = intValue6 / 20.0F;
            floatValue8 = MathHelper.clamp((floatValue9 * floatValue9 + floatValue9 * 2.0F) / 3.0F, 0.0F, 1.0F);
         }

         return floatValue8 * 3.0F;
      }
   }

   private boolean check(LivingEntity livingEntity) {
      if (livingEntity instanceof PlayerEntity playerEntity) {
         if (!playerEntity.isAlive()) {
            return false;
         } else {
            return playerEntity.isInvulnerable() ? false : !(CLIENT.player.distanceTo(playerEntity) > this.distantsiya.getValue());
         }
      } else {
         return false;
      }
   }

   private LivingEntity resolve() {
      float floatValue10 = this.distantsiya.getValue();
      PlayerEntity playerEntity2 = null;
      double doubleValue26 = Double.MAX_VALUE;
      Vec3d vec3d5 = CLIENT.player.getEyePos();
      float floatValue11 = CLIENT.gameRenderer.getCamera().getYaw();
      float floatValue12 = CLIENT.gameRenderer.getCamera().getPitch();
      Vec3d vec3d6 = Vec3d.fromPolar(floatValue12, floatValue11).normalize();

      for (Entity entity : CLIENT.world.getEntities()) {
         if (entity instanceof PlayerEntity playerEntity3
            && playerEntity3 != CLIENT.player
            && playerEntity3.isAlive()
            && !playerEntity3.isInvulnerable()
            && !playerEntity3.isCreative()
            && !(CLIENT.player.distanceTo(playerEntity3) > floatValue10)
            && (!this.ignorDruzey.isEnabled() || !FriendCommand.check(playerEntity3.getName().getString()))) {
            Vec3d vec3d7 = playerEntity3.getPos().add(0.0, playerEntity3.getHeight() * 0.5, 0.0).subtract(vec3d5).normalize();
            double doubleValue27 = Math.acos(MathHelper.clamp(vec3d6.dotProduct(vec3d7), -1.0, 1.0));
            if (doubleValue27 < doubleValue26) {
               doubleValue26 = doubleValue27;
               playerEntity2 = playerEntity3;
            }
         }
      }

      return playerEntity2;
   }

   @Override
   public void toggle() {
      super.toggle();
      if (CLIENT.player != null) {
         this.invoke();
      }
   }
}
