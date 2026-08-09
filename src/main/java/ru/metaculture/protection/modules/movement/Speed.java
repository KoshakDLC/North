package ru.metaculture.protection;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleAccess(
   usernames = {"lichoday"}
)
@ModuleRegister(
   name = "Speed",
   description = "Ускоряет вашего персонажа",
   category = Category.Movement,
   riskLevels = {ModuleRiskLevel.RISKY, ModuleRiskLevel.MATRIX, ModuleRiskLevel.GRIM}
)
public class Speed extends Module {
   public static ModeSetting rezhim = new ModeSetting("Режим", "Vanilla", "Vanilla", "ST duel", "HW", "Ares-Entity", "Grim-Entity", "TargetStrafe");
   public static NumberSetting pikovayaSkorostBps = new NumberSetting("Пиковая скорость (BPS)", 7.0F, 3.0F, 15.0F, 1.0F, false);
   public static NumberSetting silaUskoreniya = new NumberSetting("Сила ускорения", 0.8F, 0.1F, 2.0F, 0.1F, false);
   public static NumberSetting radiusStreyfa = new NumberSetting("Радиус стрейфа", 2.0F, 0.5F, 5.0F, 0.1F, false)
      .setVisibilityCondition(() -> !rezhim.is("TargetStrafe"));
   public static int intValue = 1;

   public Speed() {
      this.addSettings(new Setting[]{rezhim, pikovayaSkorostBps, silaUskoreniya, radiusStreyfa});
   }

   @Override
   public void onDisable() {
      super.onDisable();
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (!ServerBlockUtils.check() && CLIENT.player != null && CLIENT.world != null) {
         if (CLIENT.player.horizontalCollision) {
            intValue = -intValue;
         }

         String text = rezhim.getValue();
         switch (text) {
            case "Vanilla":
               MovementUtils.invoke5(0.42);
               break;
            case "ST duel":
               this.invoke(0.16);
               break;
            case "HW":
               this.invoke(0.1);
               break;
            case "Grim-Entity":
               this.invoke2();
               break;
            case "Ares-Entity":
               this.invoke3();
               break;
            case "TargetStrafe":
               this.invoke4();
         }
      }
   }

   private void invoke(double d) {
      if (!CLIENT.player.isOnGround()) {
         Box box = CLIENT.player.getBoundingBox().expand(d);
         List items = CLIENT.world.getOtherEntities(CLIENT.player, box);
         int intValue = 0;
         int intValue2 = 0;

         for (Entity entity : (Iterable<Entity>)items) {
            if (entity instanceof ArmorStandEntity) {
               intValue++;
            } else if (entity instanceof LivingEntity) {
               intValue2++;
            }

            if (intValue > 1 || intValue2 > 1) {
               this.invoke5();
               return;
            }
         }
      }
   }

   private void invoke2() {
      double doubleValue = 6.0E-4F;
      Entity entity2 = null;
      double doubleValue2 = Double.MAX_VALUE;
      double doubleValue3 = 0.2F;

      for (Entity entity3 : CLIENT.world.getEntities()) {
         if (entity3 != CLIENT.player && entity3 instanceof PlayerEntity && entity3 == AttackAura.livingEntity) {
            double doubleValue4 = entity3.getX() - CLIENT.player.getX();
            double doubleValue5 = entity3.getZ() - CLIENT.player.getZ();
            double doubleValue6 = doubleValue4 * doubleValue4 + doubleValue5 * doubleValue5;
            if (doubleValue6 <= doubleValue3 && doubleValue6 < doubleValue2) {
               doubleValue2 = doubleValue6;
               entity2 = entity3;
            }
         }
      }

      if (entity2 != null) {
         double[] doubleValues = this.resolve(CLIENT.player.getPos(), entity2.getPos(), doubleValue);
         CLIENT.player.addVelocity(doubleValues[0], 0.0, doubleValues[1]);
         CLIENT.player.velocityModified = true;
      }
   }

   private void invoke3() {
      Entity entity4 = null;
      double doubleValue7 = Double.MAX_VALUE;
      double doubleValue8 = 2.25;

      for (Entity entity5 : CLIENT.world.getEntities()) {
         if (entity5 != CLIENT.player && entity5 instanceof PlayerEntity) {
            double doubleValue9 = entity5.getX() - CLIENT.player.getX();
            double doubleValue10 = entity5.getZ() - CLIENT.player.getZ();
            double doubleValue11 = doubleValue9 * doubleValue9 + doubleValue10 * doubleValue10;
            if (doubleValue11 <= doubleValue8 && doubleValue11 < doubleValue7) {
               doubleValue7 = doubleValue11;
               entity4 = entity5;
            }
         }
      }

      if (entity4 != null && !CLIENT.player.isOnGround()) {
         this.invoke5();
      }
   }

   private void invoke4() {
      LivingEntity livingEntity = AttackAura.livingEntity;
      if (livingEntity != null && !CLIENT.player.isOnGround()) {
         Entity entity6 = null;
         double doubleValue12 = Double.MAX_VALUE;
         double doubleValue13 = 2.25;

         for (Entity entity7 : CLIENT.world.getEntities()) {
            if (entity7 != CLIENT.player && entity7 instanceof PlayerEntity) {
               double doubleValue14 = entity7.getX() - CLIENT.player.getX();
               double doubleValue15 = entity7.getZ() - CLIENT.player.getZ();
               double doubleValue16 = doubleValue14 * doubleValue14 + doubleValue15 * doubleValue15;
               if (doubleValue16 <= doubleValue13 && doubleValue16 < doubleValue12) {
                  doubleValue12 = doubleValue16;
                  entity6 = entity7;
               }
            }
         }

         Vec3d vec3d3 = CLIENT.player.getVelocity();
         double doubleValue17 = Math.sqrt(vec3d3.x * vec3d3.x + vec3d3.z * vec3d3.z);
         if (entity6 != null) {
            double doubleValue18 = 1.0 + silaUskoreniya.getValue() / 10.0;
            doubleValue17 *= doubleValue18;
         }

         double doubleValue19 = pikovayaSkorostBps.getValue() / 20.0;
         if (doubleValue17 > doubleValue19) {
            doubleValue17 = doubleValue19;
         }

         if (doubleValue17 < 0.15) {
            doubleValue17 = 0.15;
         }

         double doubleValue20 = radiusStreyfa.getValue();
         double doubleValue21 = CLIENT.player.distanceTo(livingEntity);
         double doubleValue22 = 0.0;
         double doubleValue23 = intValue;
         if (doubleValue21 > doubleValue20 + 0.5) {
            doubleValue22 = 1.0;
         } else if (doubleValue21 < doubleValue20 - 0.5) {
            doubleValue22 = -1.0;
         }

         double doubleValue24 = livingEntity.getX() - CLIENT.player.getX();
         double doubleValue25 = livingEntity.getZ() - CLIENT.player.getZ();
         float floatValue = (float)(Math.toDegrees(Math.atan2(doubleValue25, doubleValue24)) - 90.0);
         if (doubleValue22 != 0.0) {
            if (doubleValue23 > 0.0) {
               floatValue += doubleValue22 > 0.0 ? -45 : 45;
            } else if (doubleValue23 < 0.0) {
               floatValue += doubleValue22 > 0.0 ? 45 : -45;
            }

            doubleValue23 = 0.0;
            doubleValue22 = doubleValue22 > 0.0 ? 1.0 : -1.0;
         }

         double doubleValue26 = Math.sin(Math.toRadians(floatValue + 90.0F));
         double doubleValue27 = Math.cos(Math.toRadians(floatValue + 90.0F));
         double doubleValue28 = doubleValue22 * doubleValue17 * doubleValue27 + doubleValue23 * doubleValue17 * doubleValue26;
         double doubleValue29 = doubleValue22 * doubleValue17 * doubleValue26 - doubleValue23 * doubleValue17 * doubleValue27;
         CLIENT.player.setVelocity(doubleValue28, vec3d3.y, doubleValue29);
         CLIENT.player.velocityModified = true;
      }
   }

   private void invoke5() {
      Vec3d vec3d4 = CLIENT.player.getVelocity();
      double doubleValue30 = 1.0 + silaUskoreniya.getValue() / 10.0;
      double doubleValue31 = pikovayaSkorostBps.getValue() / 20.0;
      double doubleValue32 = vec3d4.x;
      double doubleValue33 = vec3d4.z;
      double doubleValue34 = doubleValue32 * doubleValue30;
      double doubleValue35 = doubleValue33 * doubleValue30;
      double doubleValue36 = Math.sqrt(doubleValue34 * doubleValue34 + doubleValue35 * doubleValue35);
      if (doubleValue36 > doubleValue31) {
         double doubleValue37 = doubleValue31 / doubleValue36;
         doubleValue34 *= doubleValue37;
         doubleValue35 *= doubleValue37;
      }

      double doubleValue38 = doubleValue34 - doubleValue32;
      double doubleValue39 = doubleValue35 - doubleValue33;
      CLIENT.player.addVelocity(doubleValue38, 0.0, doubleValue39);
      CLIENT.player.velocityModified = true;
   }

   private double[] resolve(Vec3d vec3d, Vec3d vec3d2, double d) {
      double doubleValue40 = vec3d2.x - vec3d.x;
      double doubleValue41 = vec3d2.z - vec3d.z;
      double doubleValue42 = Math.sqrt(doubleValue40 * doubleValue40 + doubleValue41 * doubleValue41);
      return doubleValue42 == 0.0 ? new double[]{0.0, 0.0} : new double[]{doubleValue40 / doubleValue42 * d, doubleValue41 / doubleValue42 * d};
   }
}
