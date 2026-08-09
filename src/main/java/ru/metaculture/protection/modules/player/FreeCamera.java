package ru.metaculture.protection;

import net.minecraft.client.option.Perspective;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "FreeCamera",
   category = Category.Player,
   description = "Свободная камера",
   riskLevels = {ModuleRiskLevel.RISKY}
)
public class FreeCamera extends Module {
   private static FreeCamera instance;
   public final NumberSetting skorost = new NumberSetting("Скорость", 2.0F, 0.5F, 5.0F, 0.1F, false);
   public final BooleanSetting otmenyatPaket = new BooleanSetting("Отменять пакет", false);
   public Vec3d vec3d;
   public Vec3d vec3d2;
   private Vec3d vec3d3;

   public static FreeCamera resolve() {
      if (instance == null && WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null) {
         instance = WildClient.INSTANCE.moduleManager.getModule(FreeCamera.class);
      }

      return instance;
   }

   public FreeCamera() {
      instance = this;
      this.addSettings(new Setting[]{this.skorost, this.otmenyatPaket});
   }

   @Override
   public void onEnable() {
      if (CLIENT.player != null && CLIENT.world != null) {
         Vec3d vec3d = CLIENT.getEntityRenderDispatcher().camera != null
            ? CLIENT.getEntityRenderDispatcher().camera.getPos()
            : CLIENT.player.getEyePos();
         this.vec3d2 = this.vec3d = vec3d;
         this.vec3d3 = null;
         super.onEnable();
      } else {
         this.toggle();
      }
   }

   @Override
   public void onDisable() {
      this.vec3d3 = null;
      this.vec3d = null;
      this.vec3d2 = null;
      super.onDisable();
   }

   @EventHandler
   public void onPacket(PacketEvent packetEvent) {
      Packet packet = packetEvent.getPacket();
      if (!(packet instanceof PlayerRespawnS2CPacket) && !(packet instanceof GameJoinS2CPacket)) {
         if (packetEvent.check() && this.otmenyatPaket.isEnabled() && packet instanceof PlayerMoveC2SPacket) {
            packetEvent.invalidate();
         }
      } else {
         this.setEnabled(false);
      }
   }

   @EventHandler
   public void onWorldRenderContext(WorldRenderContextEvent worldRenderContextEvent) {
      if (CLIENT.player != null && CLIENT.world != null) {
         Vec3d vec3d2 = CLIENT.player.getLerpedPos(worldRenderContextEvent.getFloatValue());
         Box box2 = CLIENT.player.getBoundingBox().offset(vec3d2.subtract(CLIENT.player.getPos()));
         this.invoke2(worldRenderContextEvent, box2, ColorUtils.compute41());
      }
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player != null && CLIENT.world != null) {
         if (CLIENT.options != null) {
            CLIENT.options.setPerspective(Perspective.FIRST_PERSON);
         }

         this.invoke();
      } else {
         this.setEnabled(false);
      }
   }

   @EventHandler
   public void onPlayerMotion(PlayerMotionEvent playerMotionEvent) {
      if (CLIENT.player != null && this.otmenyatPaket.isEnabled()) {
         if (this.vec3d3 == null) {
            this.vec3d3 = CLIENT.player.getPos();
         }

         playerMotionEvent.setDoubleValue(this.vec3d3.x);
         playerMotionEvent.setDoubleValue2(this.vec3d3.y);
         playerMotionEvent.setDoubleValue3(this.vec3d3.z);
         CLIENT.player.setVelocity(Vec3d.ZERO);
         CLIENT.player.fallDistance = 0.0;
      }
   }

   @EventHandler
   public void onMovementInput(MovementInputEvent movementInputEvent) {
      if (CLIENT.player != null && CLIENT.world != null && this.vec3d != null) {
         float floatValue = this.skorost.getValue();
         double[] doubleValues = MovementUtils.resolve2(movementInputEvent.getFloatValue(), movementInputEvent.getFloatValue2(), (double)floatValue);
         this.vec3d2 = this.vec3d;
         this.vec3d = this.vec3d.add(doubleValues[0], movementInputEvent.isFlag() ? floatValue : (movementInputEvent.isFlag2() ? -floatValue : 0.0), doubleValues[1]);
         movementInputEvent.setFloatValue(0.0F);
         movementInputEvent.setFloatValue2(0.0F);
         movementInputEvent.setFlag(false);
         movementInputEvent.setFlag2(false);
      }
   }

   public Vec3d resolve2(float f) {
      if (this.enabled && this.vec3d2 != null && this.vec3d != null) {
         if (CLIENT.options != null) {
            CLIENT.options.setPerspective(Perspective.FIRST_PERSON);
         }

         return this.vec3d2.lerp(this.vec3d, f);
      } else {
         return null;
      }
   }

   private void invoke() {
      if (CLIENT.player != null) {
         if (!this.otmenyatPaket.isEnabled()) {
            this.vec3d3 = null;
         } else {
            if (this.vec3d3 == null) {
               this.vec3d3 = CLIENT.player.getPos();
            }

            CLIENT.player.setVelocity(Vec3d.ZERO);
            CLIENT.player.fallDistance = 0.0;
            CLIENT.player
               .refreshPositionAndAngles(
                  this.vec3d3.x, this.vec3d3.y, this.vec3d3.z, CLIENT.player.getYaw(), CLIENT.player.getPitch()
               );
            CLIENT.player.lastX = this.vec3d3.x;
            CLIENT.player.lastY = this.vec3d3.y;
            CLIENT.player.lastZ = this.vec3d3.z;
         }
      }
   }

   private void invoke2(WorldRenderContextEvent worldRenderContextEvent2, Box box, int i) {
      int intValue = ColorUtils.compute2(i, 220);
      Vec3d vec3d3 = new Vec3d(box.minX, box.minY, box.minZ);
      Vec3d vec3d4 = new Vec3d(box.maxX, box.maxY, box.maxZ);
      Vec3d vec3d5 = new Vec3d(vec3d3.x, vec3d3.y, vec3d3.z);
      Vec3d vec3d6 = new Vec3d(vec3d3.x, vec3d3.y, vec3d4.z);
      Vec3d vec3d7 = new Vec3d(vec3d3.x, vec3d4.y, vec3d3.z);
      Vec3d vec3d8 = new Vec3d(vec3d3.x, vec3d4.y, vec3d4.z);
      Vec3d vec3d9 = new Vec3d(vec3d4.x, vec3d3.y, vec3d3.z);
      Vec3d vec3d10 = new Vec3d(vec3d4.x, vec3d3.y, vec3d4.z);
      Vec3d vec3d11 = new Vec3d(vec3d4.x, vec3d4.y, vec3d3.z);
      Vec3d vec3d12 = new Vec3d(vec3d4.x, vec3d4.y, vec3d4.z);
      worldRenderContextEvent2.getWorldRenderCapture().invoke5(vec3d5, vec3d9, 1.0, intValue, false);
      worldRenderContextEvent2.getWorldRenderCapture().invoke5(vec3d9, vec3d10, 1.0, intValue, false);
      worldRenderContextEvent2.getWorldRenderCapture().invoke5(vec3d10, vec3d6, 1.0, intValue, false);
      worldRenderContextEvent2.getWorldRenderCapture().invoke5(vec3d6, vec3d5, 1.0, intValue, false);
      worldRenderContextEvent2.getWorldRenderCapture().invoke5(vec3d7, vec3d11, 1.0, intValue, false);
      worldRenderContextEvent2.getWorldRenderCapture().invoke5(vec3d11, vec3d12, 1.0, intValue, false);
      worldRenderContextEvent2.getWorldRenderCapture().invoke5(vec3d12, vec3d8, 1.0, intValue, false);
      worldRenderContextEvent2.getWorldRenderCapture().invoke5(vec3d8, vec3d7, 1.0, intValue, false);
      worldRenderContextEvent2.getWorldRenderCapture().invoke5(vec3d5, vec3d7, 1.0, intValue, false);
      worldRenderContextEvent2.getWorldRenderCapture().invoke5(vec3d9, vec3d11, 1.0, intValue, false);
      worldRenderContextEvent2.getWorldRenderCapture().invoke5(vec3d10, vec3d12, 1.0, intValue, false);
      worldRenderContextEvent2.getWorldRenderCapture().invoke5(vec3d6, vec3d8, 1.0, intValue, false);
   }
}
