package ru.metaculture.protection;

import net.minecraft.client.render.Camera;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.Full;
import net.minecraft.util.math.Vec3d;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "AirStuck",
   category = Category.Movement,
   description = "Позволяет застывать в воздухе",
   riskLevels = {ModuleRiskLevel.RISKY, ModuleRiskLevel.PATCHED, ModuleRiskLevel.GRIM}
)
public class AirStuck extends Module {
   public final BooleanSetting obhodGrim = new BooleanSetting("Обход Grim", true);
   private Vec3d vec3d = null;
   private boolean flag = false;

   public AirStuck() {
      this.addSettings(new Setting[]{this.obhodGrim});
   }

   @Override
   public void onEnable() {
      super.onEnable();
      if (CLIENT.player != null) {
         this.vec3d = CLIENT.player.getPos();
      }

      this.flag = false;
   }

   @Override
   public void onDisable() {
      this.vec3d = null;
      super.onDisable();
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player != null && this.vec3d != null) {
         CLIENT.player.setVelocity(0.0, 0.0, 0.0);
         CLIENT.player.setPosition(this.vec3d);
         CLIENT.player.lastX = this.vec3d.x;
         CLIENT.player.lastY = this.vec3d.y;
         CLIENT.player.lastZ = this.vec3d.z;
         CLIENT.player.fallDistance = 0.0;
      }
   }

   @EventHandler
   public void onPacket(PacketEvent packetEvent) {
      if (CLIENT.player != null && CLIENT.gameRenderer != null && !this.flag) {
         if (packetEvent.getPacket() instanceof PlayerMoveC2SPacket && this.vec3d != null) {
            if (this.obhodGrim.isEnabled()) {
               packetEvent.invalidate();
            } else {
               packetEvent.invalidate();
               Camera camera = CLIENT.gameRenderer.getCamera();
               this.flag = true;
               CLIENT.getNetworkHandler()
                  .sendPacket(new Full(this.vec3d.x, this.vec3d.y, this.vec3d.z, camera.getYaw(), camera.getPitch(), false, false));
               this.flag = false;
            }
         }
      }
   }
}
