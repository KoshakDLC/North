package ru.metaculture.protection;

import java.util.Comparator;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "AutoMace",
   description = "Автоматический идеальный удар булавой на падении",
   category = Category.Combat
)
public class AutoMace extends Module {
   private final NumberSetting distantsiya = new NumberSetting("Дистанция", 4.2F, 2.8F, 6.0F, 0.1F, false);
   private final NumberSetting minPadenie = new NumberSetting("Мин. падение", 3.0F, 1.5F, 24.0F, 0.5F, false);
   private final NumberSetting tochkaUdara = new NumberSetting("Точка удара", 1.15F, 0.25F, 3.0F, 0.05F, false);
   private final NumberSetting kuldaun = new NumberSetting("Кулдаун", 92.0F, 70.0F, 100.0F, 1.0F, false);
   private final BooleanSetting tolkoIgroki = new BooleanSetting("Только игроки", false);
   private final BooleanSetting tolkoBulava = new BooleanSetting("Только булава", true);
   private long timestamp;

   public AutoMace() {
      this.addSettings(new Setting[]{this.distantsiya, this.minPadenie, this.tochkaUdara, this.kuldaun, this.tolkoIgroki, this.tolkoBulava});
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player != null && CLIENT.world != null && CLIENT.interactionManager != null) {
         if (!this.tolkoBulava.isEnabled() || CLIENT.player.getMainHandStack().isOf(Items.MACE)) {
            if (!CLIENT.player.isOnGround()
               && !(CLIENT.player.getVelocity().y >= -0.08)
               && !(CLIENT.player.fallDistance < this.minPadenie.getValue())) {
               if (!(CLIENT.player.getAttackCooldownProgress(0.0F) * 100.0F < this.kuldaun.getValue())) {
                  AutoMace.AutoMaceData autoMaceData = this.resolve2();
                  if (autoMaceData != null && !(autoMaceData.heightToGround > this.tochkaUdara.getValue()) && autoMaceData.ticksToGround <= 4) {
                     LivingEntity livingEntity2 = this.resolve();
                     if (livingEntity2 != null && this.check2(livingEntity2)) {
                        long longValue = System.currentTimeMillis();
                        if (longValue - this.timestamp >= 250L) {
                           CLIENT.interactionManager.attackEntity(CLIENT.player, livingEntity2);
                           CLIENT.player.swingHand(Hand.MAIN_HAND);
                           this.timestamp = longValue;
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private LivingEntity resolve() {
      double doubleValue = this.distantsiya.getValue();
      Box box = CLIENT.player.getBoundingBox().expand(doubleValue);
      return CLIENT.world
         .getEntitiesByClass(LivingEntity.class, box, this::check)
         .stream()
         .min(Comparator.comparingDouble(livingEntity -> CLIENT.player.squaredDistanceTo(livingEntity)))
         .orElse(null);
   }

   private boolean check(LivingEntity livingEntity) {
      if (livingEntity == CLIENT.player || !livingEntity.isAlive() || livingEntity.isSpectator() || livingEntity instanceof ArmorStandEntity) {
         return false;
      } else if (this.tolkoIgroki.isEnabled() && !(livingEntity instanceof PlayerEntity)) {
         return false;
      } else {
         double doubleValue2 = this.distantsiya.getValue();
         return CLIENT.player.squaredDistanceTo(livingEntity) <= doubleValue2 * doubleValue2;
      }
   }

   private boolean check2(LivingEntity livingEntity) {
      Vec3d vec3d = CLIENT.player.getEyePos();
      Vec3d vec3d2 = livingEntity.getBoundingBox().getCenter();
      BlockHitResult blockHitResult = CLIENT.world.raycast(new RaycastContext(vec3d, vec3d2, ShapeType.COLLIDER, FluidHandling.NONE, CLIENT.player));
      return blockHitResult.getType() == Type.MISS;
   }

   private AutoMace.AutoMaceData resolve2() {
      Vec3d vec3d3 = CLIENT.player.getPos();
      Vec3d vec3d4 = CLIENT.player.getVelocity();
      double doubleValue3 = vec3d3.y;

      for (int intValue = 0; intValue < 20; intValue++) {
         double doubleValue4 = doubleValue3 + vec3d4.y;
         Vec3d vec3d5 = new Vec3d(vec3d3.x, doubleValue3, vec3d3.z);
         Vec3d vec3d6 = new Vec3d(vec3d3.x, doubleValue4, vec3d3.z);
         BlockHitResult blockHitResult2 = CLIENT.world.raycast(new RaycastContext(vec3d5, vec3d6, ShapeType.COLLIDER, FluidHandling.NONE, CLIENT.player));
         if (blockHitResult2.getType() == Type.BLOCK) {
            double doubleValue5 = Math.max(0.0, CLIENT.player.getY() - blockHitResult2.getPos().y);
            return new AutoMace.AutoMaceData(intValue + 1, doubleValue5);
         }

         doubleValue3 = doubleValue4;
         vec3d4 = vec3d4.multiply(0.98, 0.98, 0.98).subtract(0.0, 0.08, 0.0);
      }

      Vec3d vec3d7 = CLIENT.player.getPos();
      Vec3d vec3d8 = vec3d7.subtract(0.0, 32.0, 0.0);
      BlockHitResult blockHitResult3 = CLIENT.world.raycast(new RaycastContext(vec3d7, vec3d8, ShapeType.COLLIDER, FluidHandling.NONE, CLIENT.player));
      return blockHitResult3.getType() != Type.BLOCK ? null : new AutoMace.AutoMaceData(20, Math.max(0.0, CLIENT.player.getY() - blockHitResult3.getPos().y));
   }

   record AutoMaceData(int ticksToGround, double heightToGround) {
   }
}
