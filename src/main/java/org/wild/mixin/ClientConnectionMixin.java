package org.wild.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.metaculture.protection.Event;
import ru.metaculture.protection.EventManager;
import ru.metaculture.protection.PacketEvent;
import ru.metaculture.protection.ScreenHandlerOpenEvent;
import ru.metaculture.protection.AttackPacketController;
import ru.metaculture.protection.ProtectionHandler;
import ru.metaculture.protection.TpsTracker;
import ru.metaculture.protection.WildClient;

@Mixin({ClientConnection.class})
public class ClientConnectionMixin {
   @Inject(
      method = {"handlePacket"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private static <T extends PacketListener> void handlePacketPre(Packet<T> packet, PacketListener packetListener, CallbackInfo callbackInfo) {
      ProtectionHandler.checkAccess();
      boolean flag = wild$dispatchReceiveEvent(packet);
      wild$updateTps(packet);
      if (flag) {
         callbackInfo.cancel();
      }
   }

   private static <T extends PacketListener> boolean wild$dispatchReceiveEvent(Packet<T> packet) {
      try {
         PacketEvent packetEvent = new PacketEvent(packet, PacketEvent.PacketEventState.RECEIVE);
         boolean flag2 = TpsTracker.check(packetEvent);
         EventManager.post((Event)packetEvent);
         if (flag2) {
            WildClient.invoke14();
         }

         return packetEvent.isInvalidated();
      } catch (Throwable exception) {
         return false;
      }
   }

   private static void wild$updateTps(Packet<?> packet) {
      AttackPacketController.invoke2(packet);
   }

   @Inject(
      method = {"send(Lnet/minecraft/network/packet/Packet;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void sendPre(Packet<?> packet, CallbackInfo callbackInfo) {
      ProtectionHandler.checkAccess();

      try {
         PacketEvent packetEvent2 = new PacketEvent(packet, PacketEvent.PacketEventState.SEND);
         EventManager.post((Event)packetEvent2);
         if (packetEvent2.isInvalidated()) {
            callbackInfo.cancel();
            return;
         }
      } catch (Throwable exception2) {
      }

      AttackPacketController.invoke(packet);
      if (packet instanceof CloseHandledScreenC2SPacket closeHandledScreenC2SPacket) {
         try {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client != null) {
               ScreenHandlerOpenEvent screenHandlerOpenEvent = new ScreenHandlerOpenEvent(client.currentScreen, closeHandledScreenC2SPacket.getSyncId());
               EventManager.post((Event)screenHandlerOpenEvent);
               if (screenHandlerOpenEvent.isInvalidated()) {
                  callbackInfo.cancel();
               }
            }
         } catch (Throwable exception3) {
         }
      }
   }
}
