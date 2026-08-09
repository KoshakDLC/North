package ru.metaculture.protection;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "HitBox",
   description = "Увеличивает хитбокс таргета",
   category = Category.Combat,
   riskLevels = {ModuleRiskLevel.RISKY}
)
public class HitBox extends Module {
   public static ModeSetting rezhim = new ModeSetting("Режим", "Обычный", "Легит", "Обычный");
   public static NumberSetting razmer = new NumberSetting("Размер", 0.2F, 0.0F, 5.0F, 0.1F, false);
   public static BooleanSetting ignorDruzey = new BooleanSetting("Игнор друзей", true);
   public static ModeSetting rezhimSnapa = new ModeSetting("Режим снапа", "Fast", "Fast", "Smooth", "Random")
      .setVisibilityCondition(() -> !rezhim.is("Легит"));
   public static LivingEntity livingEntity = null;
   public static int intValue = 0;

   public HitBox() {
      this.addSettings(new Setting[]{rezhim, razmer, ignorDruzey, rezhimSnapa});
   }

   public static void setLivingEntity(LivingEntity livingEntity) {
      HitBox.livingEntity = livingEntity;
      intValue = 0;
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player != null && CLIENT.world != null) {
         if (!rezhim.is("Легит")) {
            livingEntity = null;
         } else {
            if (livingEntity != null) {
               if (!livingEntity.isAlive() || CLIENT.player.distanceTo(livingEntity) > 3.0F) {
                  livingEntity = null;
                  return;
               }

               RotationSmoothing.invoke9(livingEntity, true, rezhimSnapa.getValue());
               intValue++;
               if (intValue >= 2 && AttackUtils.check14(livingEntity, 3.0, false)) {
                  CLIENT.interactionManager.attackEntity(CLIENT.player, livingEntity);
                  CLIENT.player.swingHand(Hand.MAIN_HAND);
                  livingEntity = null;
                  intValue = 0;
               } else if (intValue >= 6) {
                  livingEntity = null;
                  intValue = 0;
               }
            }
         }
      }
   }

   public LivingEntity resolve() {
      LivingEntity livingEntity2 = null;
      double doubleValue = Double.MAX_VALUE;
      Vec3d vec3d = CLIENT.player.getEyePos();
      Vec3d vec3d2 = CLIENT.player.getRotationVec(1.0F).normalize();

      for (Entity entity : CLIENT.world.getEntities()) {
         if (entity instanceof LivingEntity livingEntity3
            && livingEntity3 != CLIENT.player
            && livingEntity3.isAlive()
            && (!(ignorDruzey.isEnabled() && livingEntity3 instanceof PlayerEntity playerEntity) || !FriendCommand.check(playerEntity.getName().getString()))
            && !(CLIENT.player.distanceTo(livingEntity3) > 3.0F)) {
            Vec3d vec3d3 = livingEntity3.getPos().add(0.0, livingEntity3.getHeight() / 2.0, 0.0);
            Vec3d vec3d4 = vec3d3.subtract(vec3d).normalize();
            double doubleValue2 = MathHelper.clamp(vec3d2.dotProduct(vec3d4), -1.0, 1.0);
            double doubleValue3 = Math.toDegrees(Math.acos(doubleValue2));
            double doubleValue4 = razmer.getValue() * 30.0;
            if (doubleValue3 <= doubleValue4 && doubleValue3 < doubleValue) {
               doubleValue = doubleValue3;
               livingEntity2 = livingEntity3;
            }
         }
      }

      return livingEntity2;
   }
}
