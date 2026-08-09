package ru.metaculture.protection;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket.Mode;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;

public final class AttackPacketController {
   private static final double DOUBLE_VALUE = 1.0E-7;
   private static volatile boolean flag = false;
   private static volatile double doubleValue = 0.0;
   private static volatile double doubleValue2 = 0.0;
   private static volatile double doubleValue3 = 0.0;
   private static volatile float floatValue = 0.0F;
   private static volatile boolean flag2 = true;
   private static volatile boolean flag3 = false;
   private static volatile boolean flag4 = false;
   private static volatile float floatValue2 = 0.0F;
   private static volatile float floatValue3 = 0.0F;
   private static volatile double doubleValue4 = 0.0;
   private static volatile double doubleValue5 = 0.0;
   private static volatile boolean flag5 = false;
   private static volatile long timestamp = 0L;
   private static volatile long timestamp2 = 0L;
   private static volatile long timestamp3 = 0L;

   private AttackPacketController() {
   }

   public static void invoke(Packet<?> packet) {
      if (packet instanceof ClientCommandC2SPacket clientCommandC2SPacket2) {
         invoke3(clientCommandC2SPacket2);
         timestamp = System.currentTimeMillis();
      } else if (packet instanceof PlayerMoveC2SPacket playerMoveC2SPacket) {
         boolean flag = playerMoveC2SPacket.isOnGround();
         if (playerMoveC2SPacket.changesLook()) {
            floatValue2 = playerMoveC2SPacket.getYaw(floatValue2);
            floatValue3 = playerMoveC2SPacket.getPitch(floatValue3);
            flag4 = true;
            timestamp3 = System.currentTimeMillis();
         }

         if (playerMoveC2SPacket.changesPosition()) {
            double packetX = playerMoveC2SPacket.getX(AttackPacketController.doubleValue);
            double packetY = playerMoveC2SPacket.getY(AttackPacketController.doubleValue2);
            double packetZ = playerMoveC2SPacket.getZ(AttackPacketController.doubleValue3);
            invoke4(packetX, packetY, packetZ, flag);
         } else {
            setFlag2(flag);
         }

         timestamp = System.currentTimeMillis();
      }
   }

   public static void invoke2(Packet<?> packet) {
      if (packet instanceof GameJoinS2CPacket || packet instanceof PlayerRespawnS2CPacket) {
         invoke5();
      }
   }

   private static void invoke3(ClientCommandC2SPacket clientCommandC2SPacket) {
      Mode mode = clientCommandC2SPacket.getMode();
      if (mode == Mode.START_SPRINTING) {
         flag3 = true;
         timestamp2 = System.currentTimeMillis();
      } else {
         if (mode == Mode.STOP_SPRINTING) {
            flag3 = false;
            timestamp2 = System.currentTimeMillis();
         }
      }
   }

   private static void invoke4(double d, double e, double f, boolean bl) {
      if (!flag) {
         flag = true;
         doubleValue = d;
         doubleValue2 = e;
         doubleValue3 = f;
         flag2 = bl;
         floatValue = 0.0F;
         doubleValue4 = 0.0;
         doubleValue5 = 0.0;
         flag5 = false;
      } else {
         double verticalDelta = e - doubleValue2;
         doubleValue5 = verticalDelta;
         doubleValue4 = verticalDelta;
         flag5 = !bl && doubleValue5 > 1.0E-7 && doubleValue4 < -1.0E-7;
         if (bl) {
            floatValue = 0.0F;
         } else if (doubleValue4 < -1.0E-7) {
            floatValue += (float)(-doubleValue4);
         }

         doubleValue = d;
         doubleValue2 = e;
         doubleValue3 = f;
         flag2 = bl;
      }
   }

   private static void setFlag2(boolean bl) {
      flag2 = bl;
      if (bl) {
         floatValue = 0.0F;
         flag5 = false;
      }
   }

   public static float getFloatValue() {
      return floatValue;
   }

   public static boolean isFlag2() {
      return flag2;
   }

   public static boolean isFlag3() {
      return flag3;
   }

   public static boolean isFlag4() {
      return flag4;
   }

   public static float measure(float f) {
      return flag4 ? floatValue2 : f;
   }

   public static float measure2(float f) {
      return flag4 ? floatValue3 : f;
   }

   public static void setFlag3(boolean bl) {
      flag3 = bl;
      timestamp2 = System.currentTimeMillis();
   }

   public static double getDoubleValue4() {
      return doubleValue4;
   }

   public static double getDoubleValue5() {
      return doubleValue5;
   }

   public static boolean isFlag5() {
      return flag5;
   }

   public static long getTimestamp() {
      return timestamp;
   }

   public static long getTimestamp2() {
      return timestamp2;
   }

   public static long getTimestamp3() {
      return timestamp3;
   }

   public static boolean isFlag() {
      return flag;
   }

   public static void invoke5() {
      flag = false;
      doubleValue = 0.0;
      doubleValue2 = 0.0;
      doubleValue3 = 0.0;
      floatValue = 0.0F;
      flag2 = true;
      flag3 = false;
      flag4 = false;
      floatValue2 = 0.0F;
      floatValue3 = 0.0F;
      doubleValue4 = 0.0;
      doubleValue5 = 0.0;
      flag5 = false;
      timestamp = 0L;
      timestamp2 = 0L;
      timestamp3 = 0L;
   }
}
