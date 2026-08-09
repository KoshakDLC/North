package ru.metaculture.protection;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public final class NetworkTimeService {
   private static final String WILD_NTP_HOST = System.getProperty("wild.ntp.host", "time.windows.com");
   private static final int INT_VALUE = Integer.getInteger("wild.ntp.port", 123);
   private static final int INT_VALUE_2 = Integer.getInteger("wild.ntp.timeoutMs", 1200);
   private static final long TIMESTAMP = Long.getLong("wild.ntp.cacheTtlMs", 300000L);
   private static final long TIMESTAMP_2 = Long.getLong("wild.ntp.failCooldownMs", 60000L);
   private static final long TIMESTAMP_3 = 2208988800L;
   private static volatile NetworkTimeService.NetworkTimeServiceData networkTimeServiceData;
   private static volatile long timestamp;

   private NetworkTimeService() {
   }

   public static long compute() {
      NetworkTimeService.NetworkTimeServiceData networkTimeServiceData = NetworkTimeService.networkTimeServiceData;
      long longValue = System.nanoTime();
      if (networkTimeServiceData != null && longValue - networkTimeServiceData.nanoTime <= TIMESTAMP * 1000000L) {
         return networkTimeServiceData.epochMillis + (longValue - networkTimeServiceData.nanoTime) / 1000000L;
      } else {
         if (longValue >= timestamp) {
            NetworkTimeService.NetworkTimeServiceData networkTimeServiceData2 = resolve2(longValue);
            if (networkTimeServiceData2 != null) {
               networkTimeServiceData = networkTimeServiceData2;
               return networkTimeServiceData2.epochMillis;
            }

            timestamp = longValue + TIMESTAMP_2 * 1000000L;
         }

         return networkTimeServiceData != null ? networkTimeServiceData.epochMillis + (longValue - networkTimeServiceData.nanoTime) / 1000000L : System.currentTimeMillis();
      }
   }

   public static long compute2() {
      NetworkTimeService.NetworkTimeServiceData networkTimeServiceData3 = networkTimeServiceData;
      long longValue2 = System.nanoTime();
      return networkTimeServiceData3 != null ? networkTimeServiceData3.epochMillis + (longValue2 - networkTimeServiceData3.nanoTime) / 1000000L : System.currentTimeMillis();
   }

   public static long compute3() {
      return compute() / 1000L;
   }

   public static boolean check() {
      NetworkTimeService.NetworkTimeServiceData networkTimeServiceData4 = networkTimeServiceData;
      return networkTimeServiceData4 == null ? false : System.nanoTime() - networkTimeServiceData4.nanoTime <= TIMESTAMP * 1000000L;
   }

   public static String resolve() {
      return check() ? "NTP:" + WILD_NTP_HOST : "LOCAL";
   }

   public static void invoke() {
      long longValue3 = System.nanoTime();
      NetworkTimeService.NetworkTimeServiceData networkTimeServiceData5 = resolve2(longValue3);
      if (networkTimeServiceData5 != null) {
         networkTimeServiceData = networkTimeServiceData5;
      } else {
         timestamp = longValue3 + TIMESTAMP_2 * 1000000L;
      }
   }

   private static NetworkTimeService.NetworkTimeServiceData resolve2(long l) {
      try {
         byte[] byteValues = new byte[48];
         byteValues[0] = 35;
         InetAddress inetAddress = InetAddress.getByName(WILD_NTP_HOST);
         DatagramPacket datagramPacket = new DatagramPacket(byteValues, byteValues.length, inetAddress, INT_VALUE);

         NetworkTimeService.NetworkTimeServiceData networkTimeServiceData6;
         try (DatagramSocket datagramSocket = new DatagramSocket()) {
            datagramSocket.setSoTimeout(INT_VALUE_2);
            datagramSocket.send(datagramPacket);
            DatagramPacket datagramPacket2 = new DatagramPacket(byteValues, byteValues.length);
            datagramSocket.receive(datagramPacket2);
            long longValue4 = System.nanoTime();
            long longValue5 = compute4(byteValues);
            if (longValue5 <= 0L) {
               return null;
            }

            networkTimeServiceData6 = new NetworkTimeService.NetworkTimeServiceData(longValue5, longValue4);
         }

         return networkTimeServiceData6;
      } catch (Throwable exception) {
         return null;
      }
   }

   private static long compute4(byte[] bs) {
      long longValue6 = (bs[40] & 255L) << 24 | (bs[41] & 255L) << 16 | (bs[42] & 255L) << 8 | bs[43] & 255L;
      long longValue7 = (bs[44] & 255L) << 24 | (bs[45] & 255L) << 16 | (bs[46] & 255L) << 8 | bs[47] & 255L;
      long longValue8 = longValue6 - 2208988800L;
      long longValue9 = longValue8 * 1000L + longValue7 * 1000L / 4294967296L;
      return longValue9 > 0L ? longValue9 : 0L;
   }

   record NetworkTimeServiceData(long epochMillis, long nanoTime) {
   }
}
