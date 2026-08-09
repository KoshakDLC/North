package ru.metaculture.protection;

import java.util.Objects;
import lombok.Generated;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.ClientStatusC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientStatusC2SPacket.Mode;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import net.minecraft.util.Hand;

public class PacketStateListener extends RotationListener implements MinecraftAccessor {
   public static final PacketStateListener INSTANCE = new PacketStateListener();
   public boolean flag;
   public boolean flag2 = true;

   @EventHandler
   public void onPacket(PacketEvent packetEvent) {
      Packet packet = packetEvent.getPacket();
      Objects.requireNonNull(packet);
      Object object = packet;
      switch (object) {
         case PlayerActionC2SPacket playerActionC2SPacket when playerActionC2SPacket.getAction().equals(Action.RELEASE_USE_ITEM):
            this.flag2 = true;
            break;
         case ClientStatusC2SPacket clientStatusC2SPacket when clientStatusC2SPacket.getMode().equals(Mode.PERFORM_RESPAWN):
            this.flag2 = true;
            break;
         case PlayerRespawnS2CPacket playerRespawnS2CPacket:
            this.flag2 = true;
            break;
         case GameJoinS2CPacket gameJoinS2CPacket:
            this.flag2 = true;
            break;
         default:
      }
   }

   public void invoke(Hand hand) {
      if (this.flag2) {
         a_.interactionManager.interactItem(a_.player, hand);
         this.flag2 = false;
      }

      this.flag = true;
   }

   @Generated
   public void setFlag(boolean bl) {
      this.flag = bl;
   }

   @Generated
   public void setFlag2(boolean bl) {
      this.flag2 = bl;
   }

   @Generated
   public boolean isFlag() {
      return this.flag;
   }

   @Generated
   public boolean isFlag2() {
      return this.flag2;
   }
}
