package ru.metaculture.protection;

import lombok.Generated;
import net.minecraft.network.packet.Packet;

public class PacketEvent extends Event {
   private Packet<?> packet;
   private PacketEvent.PacketEventState packetEventState;

   public boolean check() {
      return this.packetEventState.equals(PacketEvent.PacketEventState.SEND);
   }

   @Generated
   public Packet<?> getPacket() {
      return this.packet;
   }

   @Generated
   public PacketEvent.PacketEventState getPacketEventState() {
      return this.packetEventState;
   }

   @Generated
   public void setPacket(Packet<?> packet) {
      this.packet = packet;
   }

   @Generated
   public void setPacketEventState(PacketEvent.PacketEventState packetEventState) {
      this.packetEventState = packetEventState;
   }

   @Generated
   public PacketEvent(Packet<?> packet, PacketEvent.PacketEventState packetEventState2) {
      this.packet = packet;
      this.packetEventState = packetEventState2;
   }

   public static enum PacketEventState {
      SEND,
      RECEIVE;
   }
}
