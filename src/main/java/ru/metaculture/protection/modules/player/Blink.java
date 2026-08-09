package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.Perspective;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "Blink",
   description = "Замедляет пакеты имитируя пинг",
   category = Category.Player,
   riskLevels = {ModuleRiskLevel.RISKY, ModuleRiskLevel.GRIM}
)
public class Blink extends Module {
   private static final int INT_VALUE = -1258291201;
   private final List<Packet<?>> items = new ArrayList<>();
   private final BooleanSetting pulsirovat = new BooleanSetting("Пульсировать", false);
   private final NumberSetting zaderzhka = new NumberSetting("Задержка", 12.0F, 1.0F, 40.0F, 1.0F, false)
      .setVisibilityCondition(() -> !this.pulsirovat.isEnabled());
   private final BooleanSetting sbrosPriUdare = new BooleanSetting("Сброс при ударе", false);
   private final BooleanSetting otobrazhatModel = new BooleanSetting("Отображать модель", true);
   private final BooleanSetting ubiratOtPervogoLitsa = new BooleanSetting("Убирать от первого лица", true).visibleWhen(() -> !this.otobrazhatModel.isEnabled());
   private Vec3d vec3d;
   private boolean flag;
   private boolean flag2;
   private boolean flag3;
   private long timestamp;

   public Blink() {
      this.addSettings(new Setting[]{this.pulsirovat, this.zaderzhka, this.sbrosPriUdare, this.otobrazhatModel, this.ubiratOtPervogoLitsa});
   }

   @Override
   public void onEnable() {
      if (!this.check()) {
         this.setEnabled(false);
      } else {
         this.items.clear();
         this.vec3d = CLIENT.player.getPos();
         this.flag = false;
         this.flag3 = false;
         this.timestamp = System.currentTimeMillis();
         super.onEnable();
      }
   }

   @Override
   public void onDisable() {
      if (!this.flag2) {
         this.invoke();
      }

      this.items.clear();
      this.vec3d = null;
      this.flag = false;
      this.flag3 = false;
      this.flag2 = false;
      super.onDisable();
   }

   @EventHandler
   public void onPacket(PacketEvent packetEvent) {
      if (packetEvent.check() && !this.flag && this.check()) {
         if (this.flag3) {
            this.flag3 = false;
            if (packetEvent.getPacket() instanceof PlayerInteractEntityC2SPacket) {
               return;
            }
         }

         this.items.add(packetEvent.getPacket());
         packetEvent.invalidate();
      }
   }

   @EventHandler
   public void onAttackEntity(AttackEntityEvent attackEntityEvent) {
      if (this.sbrosPriUdare.isEnabled()
         && this.check()
         && attackEntityEvent.getEntity() instanceof PlayerEntity playerEntity
         && playerEntity != CLIENT.player
         && !(playerEntity instanceof ClientPlayerEntity)) {
         this.invoke();
         this.items.clear();
         this.vec3d = CLIENT.player.getPos();
         this.flag3 = true;
         this.timestamp = System.currentTimeMillis();
      }
   }

   @EventHandler
   public void onClientTick(ClientTickEvent clientTickEvent) {
      if (this.pulsirovat.isEnabled() && !this.items.isEmpty()) {
         if (System.currentTimeMillis() - this.timestamp >= this.compute()) {
            this.invoke();
            this.items.clear();
            this.vec3d = CLIENT.player != null ? CLIENT.player.getPos() : null;
            this.timestamp = System.currentTimeMillis();
         }
      }
   }

   @EventHandler
   public void onWorldRenderContext(WorldRenderContextEvent worldRenderContextEvent) {
      if (this.otobrazhatModel.isEnabled() && this.vec3d != null && CLIENT.player != null && CLIENT.world != null) {
         if (CLIENT.options.getPerspective() != Perspective.FIRST_PERSON || !this.ubiratOtPervogoLitsa.isEnabled()) {
            Box box2 = CLIENT.player.getBoundingBox().offset(this.vec3d.subtract(CLIENT.player.getPos()));
            this.invoke2(worldRenderContextEvent, box2, -1258291201);
         }
      }
   }

   @EventHandler
   public void onWorldReady(WorldReadyEvent worldReadyEvent) {
      this.flag2 = true;
      this.setEnabled(false);
   }

   @EventHandler
   public void onWorldJoin(WorldJoinEvent worldJoinEvent) {
      this.flag2 = true;
      this.setEnabled(false);
   }

   private void invoke() {
      if (!this.items.isEmpty() && CLIENT.getNetworkHandler() != null) {
         this.flag = true;
         boolean flag = false ;

         try {
            flag = true;

            for (Packet packet : this.items) {
               CLIENT.getNetworkHandler().sendPacket(packet);
            }

            flag = false;
         } finally {
            if (flag) {
               this.flag = false;
            }
         }

         this.flag = false;
      }
   }

   private boolean check() {
      return CLIENT.player != null && CLIENT.world != null && CLIENT.getNetworkHandler() != null;
   }

   private long compute() {
      return Math.round(this.zaderzhka.getValue() * 50.0F);
   }

   private void invoke2(WorldRenderContextEvent worldRenderContextEvent2, Box box, int i) {
      Vec3d vec3d = new Vec3d(box.minX, box.minY, box.minZ);
      Vec3d vec3d2 = new Vec3d(box.maxX, box.maxY, box.maxZ);
      Vec3d vec3d3 = new Vec3d(vec3d.x, vec3d.y, vec3d.z);
      Vec3d vec3d4 = new Vec3d(vec3d.x, vec3d.y, vec3d2.z);
      Vec3d vec3d5 = new Vec3d(vec3d.x, vec3d2.y, vec3d.z);
      Vec3d vec3d6 = new Vec3d(vec3d.x, vec3d2.y, vec3d2.z);
      Vec3d vec3d7 = new Vec3d(vec3d2.x, vec3d.y, vec3d.z);
      Vec3d vec3d8 = new Vec3d(vec3d2.x, vec3d.y, vec3d2.z);
      Vec3d vec3d9 = new Vec3d(vec3d2.x, vec3d2.y, vec3d.z);
      Vec3d vec3d10 = new Vec3d(vec3d2.x, vec3d2.y, vec3d2.z);
      worldRenderContextEvent2.getWorldRenderCapture().invoke5(vec3d3, vec3d7, 1.0, i, false);
      worldRenderContextEvent2.getWorldRenderCapture().invoke5(vec3d7, vec3d8, 1.0, i, false);
      worldRenderContextEvent2.getWorldRenderCapture().invoke5(vec3d8, vec3d4, 1.0, i, false);
      worldRenderContextEvent2.getWorldRenderCapture().invoke5(vec3d4, vec3d3, 1.0, i, false);
      worldRenderContextEvent2.getWorldRenderCapture().invoke5(vec3d5, vec3d9, 1.0, i, false);
      worldRenderContextEvent2.getWorldRenderCapture().invoke5(vec3d9, vec3d10, 1.0, i, false);
      worldRenderContextEvent2.getWorldRenderCapture().invoke5(vec3d10, vec3d6, 1.0, i, false);
      worldRenderContextEvent2.getWorldRenderCapture().invoke5(vec3d6, vec3d5, 1.0, i, false);
      worldRenderContextEvent2.getWorldRenderCapture().invoke5(vec3d3, vec3d5, 1.0, i, false);
      worldRenderContextEvent2.getWorldRenderCapture().invoke5(vec3d7, vec3d9, 1.0, i, false);
      worldRenderContextEvent2.getWorldRenderCapture().invoke5(vec3d8, vec3d10, 1.0, i, false);
      worldRenderContextEvent2.getWorldRenderCapture().invoke5(vec3d4, vec3d6, 1.0, i, false);
   }
}
