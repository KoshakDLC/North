package ru.metaculture.protection;

import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "Velocity",
   description = "Убирает откидывание",
   category = Category.Combat,
   riskLevels = {ModuleRiskLevel.RISKY, ModuleRiskLevel.MATRIX, ModuleRiskLevel.GRIM}
)
public class Velocity extends Module {
   public static ModeSetting obhod = new ModeSetting("Обход", "Vanilla", "Vanilla", "Lag", "Funtime");

   public Velocity() {
      this.addSettings(new Setting[]{obhod});
   }

   @EventHandler
   public void onPacket(PacketEvent packetEvent) {
      if (!ServerBlockUtils.check()) {
         if (obhod.is("Vanilla")
            && packetEvent.getPacket() instanceof EntityVelocityUpdateS2CPacket entityVelocityUpdateS2CPacket
            && entityVelocityUpdateS2CPacket.getEntityId() == CLIENT.player.getId()) {
            packetEvent.invalidate();
         }

         if (obhod.is("Funtime")
            && packetEvent.getPacket() instanceof EntityVelocityUpdateS2CPacket entityVelocityUpdateS2CPacket2
            && entityVelocityUpdateS2CPacket2.getEntityId() == CLIENT.player.getId()
            && CLIENT.player.inPowderSnow) {
            packetEvent.invalidate();
         }

         if (obhod.is("Lag")) {
            if (packetEvent.getPacket() instanceof EntityVelocityUpdateS2CPacket entityVelocityUpdateS2CPacket3 && entityVelocityUpdateS2CPacket3.getEntityId() == CLIENT.player.getId()) {
               packetEvent.invalidate();
            }

            if (packetEvent.check()) {
               packetEvent.invalidate();
            }
         }
      }
   }
}
