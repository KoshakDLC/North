package ru.metaculture.protection;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.common.KeepAliveS2CPacket;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;

public class TpsTracker {
   private static final double DOUBLE_VALUE = 50.0;
   private static final double DOUBLE_VALUE_2 = 1.0E-6;
   private static final double DOUBLE_VALUE_3 = 1000000.0;
   private static final long TIMESTAMP = 5000000000L;
   private static final double DOUBLE_VALUE_4 = 0.15;
   private static final double DOUBLE_VALUE_5 = 0.125;
   public static long timestamp = System.currentTimeMillis() - 588L;
   public static double doubleValue = 20.0;
   private static volatile long timestamp2 = System.nanoTime();
   private static volatile long timestamp3;
   private static volatile double doubleValue2;
   private static volatile double doubleValue3;
   private static volatile boolean flag;

   public static boolean check(PacketEvent packetEvent) {
      if (packetEvent != null && packetEvent.getPacketEventState() == PacketEvent.PacketEventState.RECEIVE) {
         Packet packet = packetEvent.getPacket();
         long longValue = System.nanoTime();
         if (packet instanceof WorldTimeUpdateS2CPacket) {
            invoke(longValue, System.currentTimeMillis());
            return true;
         } else {
            if (packet instanceof KeepAliveS2CPacket) {
               invoke2(longValue);
            }

            return false;
         }
      } else {
         return false;
      }
   }

   public static void invoke(long l, long m) {
      long longValue2 = timestamp;
      float floatValue = (float)(m - longValue2);
      float floatValue2 = floatValue / 1000.0F;
      float floatValue3 = floatValue2 > 0.0F ? 20.0F / floatValue2 : 20.0F;
      doubleValue = Math.min(floatValue3, 20.0F);
      timestamp = m;
      invoke3(l, true);
   }

   public static void invoke2(long l) {
      invoke3(l, false);
   }

   private static void invoke3(long l, boolean bl) {
      long longValue3 = timestamp3;
      if (longValue3 > 0L) {
         invoke4(l - longValue3);
      }

      timestamp3 = l;
      if (bl || timestamp2 == 0L) {
         timestamp2 = l;
         flag = true;
      }

      invoke5();
   }

   private static void invoke4(long l) {
      if (l > 0L) {
         double doubleValue = l * 1.0E-6;
         double doubleValue2 = Math.max(1.0, Math.rint(doubleValue / 50.0));
         double doubleValue3 = Math.abs(doubleValue - doubleValue2 * 50.0);
         double doubleValue4 = doubleValue3;
         doubleValue3 = doubleValue4 <= 0.0 ? doubleValue3 : doubleValue4 + (doubleValue3 - doubleValue4) * 0.125;
      }
   }

   private static void invoke5() {
      MinecraftClient client = MinecraftClient.getInstance();
      if (client != null && client.player != null && client.getNetworkHandler() != null) {
         PlayerListEntry playerListEntry = client.getNetworkHandler().getPlayerListEntry(client.player.getUuid());
         if (playerListEntry != null) {
            int intValue = playerListEntry.getLatency();
            if (intValue > 0 && intValue <= 2000) {
               double doubleValue5 = doubleValue2;
               doubleValue2 = doubleValue5 <= 0.0 ? intValue : doubleValue5 + (intValue - doubleValue5) * 0.15;
            }
         }
      }
   }

   public static double getDoubleValue() {
      return doubleValue;
   }

   public static double measure() {
      return 20.0 - doubleValue;
   }

   public static boolean check2() {
      return check3(System.nanoTime());
   }

   public static boolean check3(long l) {
      long longValue4 = timestamp2;
      long longValue5 = l - longValue4;
      return flag && longValue4 > 0L && longValue5 >= 0L && longValue5 <= 5000000000L;
   }

   public static double measure2(long l) {
      if (!check3(l)) {
         return 50.0;
      } else {
         double doubleValue6 = measure3(l);
         double doubleValue7 = 50.0 - doubleValue6;
         return doubleValue7 <= 0.0 ? 50.0 : doubleValue7;
      }
   }

   public static double measure3(long l) {
      long longValue6 = timestamp2;
      if (longValue6 <= 0L) {
         return 0.0;
      } else {
         double doubleValue8 = (l - longValue6) * 1.0E-6 + doubleValue2 * 0.5;
         doubleValue8 %= 50.0;
         if (doubleValue8 < 0.0) {
            doubleValue8 += 50.0;
         }

         return doubleValue8;
      }
   }

   public static long compute(long l, double d) {
      if (!check3(l)) {
         return 0L;
      } else {
         double doubleValue9 = measure3(l);
         double doubleValue10 = d - doubleValue9;
         if (doubleValue10 < 0.0) {
            doubleValue10 += 50.0;
         }

         return (long)(doubleValue10 * 1000000.0);
      }
   }

   public static double getDoubleValue2() {
      return doubleValue2;
   }

   public static double getDoubleValue3() {
      return doubleValue3;
   }

   public static void invoke6() {
      timestamp = System.currentTimeMillis() - 588L;
      doubleValue = 20.0;
      timestamp2 = System.nanoTime();
      timestamp3 = 0L;
      doubleValue2 = 0.0;
      doubleValue3 = 0.0;
      flag = false;
   }
}
