package ru.metaculture.protection;

import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.OnGroundOnly;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "GrimGlide",
   category = Category.Movement,
   description = "Обход Grim при полёте на элитрах",
   riskLevels = {ModuleRiskLevel.RISKY, ModuleRiskLevel.GRIM}
)
public class GrimGlide extends Module {
   public final NumberSetting skorost = new NumberSetting("Скорость", 1.0F, 1.0F, 2.0F, 0.05F, false);
   private int intValue;
   private boolean flag;
   private boolean flag2;

   public GrimGlide() {
      this.addSettings(new Setting[]{this.skorost});
   }

   @EventHandler
   public void onPacket(PacketEvent packetEvent) {
      if (CLIENT.player != null && !this.flag2) {
         if (!packetEvent.check() && packetEvent.getPacket() instanceof PlayerPositionLookS2CPacket) {
            this.intValue = 2;
            this.flag = true;
         }

         if (packetEvent.check() && packetEvent.getPacket() instanceof PlayerMoveC2SPacket) {
            if (CLIENT.player.isGliding() && this.intValue == 0 && !this.flag) {
               this.flag2 = true;
               CLIENT.getNetworkHandler().sendPacket(new OnGroundOnly(true, true));
               this.flag2 = false;
               packetEvent.invalidate();
            }

            this.flag = false;
         }
      }
   }

   @EventHandler
   public void onClientTick(ClientTickEvent clientTickEvent) {
      if (CLIENT.player != null) {
         if (this.intValue > 0) {
            this.intValue--;
         }
      }
   }

   @EventHandler
   public void onEntityVelocity(EntityVelocityEvent entityVelocityEvent) {
      if (CLIENT.player != null) {
         Vec3d vec3d = CLIENT.player.getVelocity();
         Vec3d vec3d2 = CLIENT.player.getRotationVector();
         float floatValue = CLIENT.player.getPitch() * (float) (Math.PI / 180.0);
         double doubleValue = Math.sqrt(vec3d2.x * vec3d2.x + vec3d2.z * vec3d2.z);
         double doubleValue2 = vec3d.horizontalLength();
         boolean flag = CLIENT.player.getVelocity().y <= 0.0;
         double doubleValue3 = flag && CLIENT.player.hasStatusEffect(StatusEffects.SLOW_FALLING)
            ? Math.min(CLIENT.player.getFinalGravity(), 0.01)
            : CLIENT.player.getFinalGravity();
         double doubleValue4 = MathHelper.square(Math.cos(floatValue));
         vec3d = vec3d.add(0.0, doubleValue3 * (-1.0 + doubleValue4 * 0.75), 0.0);
         if (vec3d.y < 0.0 && doubleValue > 0.0) {
            double doubleValue5 = vec3d.y * -0.1 * doubleValue4;
            vec3d = vec3d.add(vec3d2.x * doubleValue5 / doubleValue, doubleValue5, vec3d2.z * doubleValue5 / doubleValue);
         }

         if (floatValue < 0.0F && doubleValue > 0.0) {
            double doubleValue6 = doubleValue2 * -MathHelper.sin(floatValue) * 0.04F;
            vec3d = vec3d.add(-vec3d2.x * doubleValue6 / doubleValue, doubleValue6 * 3.2, -vec3d2.z * doubleValue6 / doubleValue);
         }

         if (doubleValue > 0.0) {
            vec3d = vec3d.add((vec3d2.x / doubleValue * doubleValue2 - vec3d.x) * 0.1, 0.0, (vec3d2.z / doubleValue * doubleValue2 - vec3d.z) * 0.1);
         }

         double doubleValue7 = Math.toRadians(CLIENT.player.getYaw());
         double doubleValue8 = -Math.sin(doubleValue7);
         double doubleValue9 = Math.cos(doubleValue7);
         float floatValue2 = this.skorost.getValue();
         if (this.intValue >= 1) {
            double doubleValue10 = 0.09F * floatValue2;
            entityVelocityEvent.setVec3d(vec3d.multiply(0.99F, 0.98F, 0.99F).add(doubleValue8 * doubleValue10, 0.03F * floatValue2, doubleValue9 * doubleValue10));
         } else {
            float floatValue3 = MathHelper.clamp(0.3F * floatValue2, 0.3F, 0.85F);
            entityVelocityEvent.setVec3d(vec3d.multiply(floatValue3, floatValue3, floatValue3));
         }
      }
   }
}
