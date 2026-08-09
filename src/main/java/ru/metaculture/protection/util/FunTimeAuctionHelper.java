package ru.metaculture.protection;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;

final class FunTimeAuctionHelper {
   private static final int INT_VALUE = 5;
   private static final long TIMESTAMP = 900L;
   private static final DualTimer DUAL_TIMER = new DualTimer();
   private static FunTimeAuctionHelper.FunTimeAuctionHelperState funTimeAuctionHelperState = FunTimeAuctionHelper.FunTimeAuctionHelperState.NONE;
   private static boolean flag = false;
   private static boolean flag2 = false;
   private static boolean flag3 = false;
   private static int intValue = 0;

   private FunTimeAuctionHelper() {
   }

   static void invoke() {
      funTimeAuctionHelperState = FunTimeAuctionHelper.FunTimeAuctionHelperState.NONE;
      flag = false;
      flag2 = false;
      flag3 = false;
      intValue = 0;
      invoke14();
   }

   static void invoke2() {
      if (flag3) {
         MinecraftClient client = MinecraftClient.getInstance();
         if (client.player != null) {
            if (check6(client)) {
               flag3 = false;
               intValue = 0;
            } else if (!check7()) {
               flag3 = false;
               intValue = 0;
            } else if (intValue >= 5) {
               if (DUAL_TIMER.check5(900L)) {
                  flag3 = false;
               }
            } else {
               if (DUAL_TIMER.check5(900L)) {
                  invoke13(client);
               }
            }
         }
      }
   }

   static boolean isFlag() {
      return flag;
   }

   static boolean isFlag2() {
      return flag2;
   }

   static boolean check() {
      return funTimeAuctionHelperState == FunTimeAuctionHelper.FunTimeAuctionHelperState.SELL || AutoBuy.flag;
   }

   static boolean check2() {
      return funTimeAuctionHelperState == FunTimeAuctionHelper.FunTimeAuctionHelperState.RESELL || AutoBuy.flag2;
   }

   static boolean check3() {
      return !flag && !check2();
   }

   static boolean check4() {
      if (!check3()) {
         return false;
      } else {
         funTimeAuctionHelperState = FunTimeAuctionHelper.FunTimeAuctionHelperState.SELL;
         invoke14();
         return true;
      }
   }

   static void invoke3(boolean bl) {
      if (funTimeAuctionHelperState == FunTimeAuctionHelper.FunTimeAuctionHelperState.SELL) {
         funTimeAuctionHelperState = FunTimeAuctionHelper.FunTimeAuctionHelperState.NONE;
      }

      AutoBuy.flag = false;
      invoke14();
      invoke11(bl);
   }

   static boolean check5() {
      if (flag2 && !check()) {
         funTimeAuctionHelperState = FunTimeAuctionHelper.FunTimeAuctionHelperState.RESELL;
         invoke14();
         return true;
      } else {
         return false;
      }
   }

   static void invoke4(boolean bl) {
      if (funTimeAuctionHelperState == FunTimeAuctionHelper.FunTimeAuctionHelperState.RESELL) {
         funTimeAuctionHelperState = FunTimeAuctionHelper.FunTimeAuctionHelperState.NONE;
      }

      AutoBuy.flag2 = false;
      invoke14();
      invoke11(bl);
   }

   static void invoke5(boolean bl) {
      if (bl && !flag) {
         check4();
      } else {
         invoke14();
         invoke11(true);
      }
   }

   static void invoke6() {
      flag = false;
      flag2 = true;
      invoke14();
   }

   static void invoke7() {
      flag = true;
      flag2 = true;
      if (funTimeAuctionHelperState == FunTimeAuctionHelper.FunTimeAuctionHelperState.SELL) {
         funTimeAuctionHelperState = FunTimeAuctionHelper.FunTimeAuctionHelperState.NONE;
      }

      invoke14();
   }

   static void invoke8() {
      flag = false;
      flag2 = true;
      invoke14();
   }

   static void invoke9() {
      flag2 = true;
      invoke14();
   }

   static void invoke10() {
      flag = false;
      flag2 = false;
      if (funTimeAuctionHelperState == FunTimeAuctionHelper.FunTimeAuctionHelperState.RESELL) {
         funTimeAuctionHelperState = FunTimeAuctionHelper.FunTimeAuctionHelperState.NONE;
      }

      invoke14();
   }

   static void invoke11(boolean bl) {
      if (bl) {
         MinecraftClient client2 = MinecraftClient.getInstance();
         if (client2.player != null) {
            if (AutoBuy.instance != null && AutoBuy.instance.enabled) {
               invoke12(client2);
            }
         }
      }
   }

   private static void invoke12(MinecraftClient minecraftClient) {
      if (check6(minecraftClient)) {
         flag3 = false;
         intValue = 0;
      } else if (check7()) {
         flag3 = false;
         intValue = 0;
         AutoBuy.instance.invoke55();
      } else {
         minecraftClient.player.networkHandler.sendChatCommand("ah");
      }
   }

   private static void invoke13(MinecraftClient minecraftClient) {
      minecraftClient.player.networkHandler.sendChatCommand("ah");
      intValue++;
      DUAL_TIMER.invoke();
   }

   private static boolean check6(MinecraftClient minecraftClient) {
      return minecraftClient.currentScreen instanceof GenericContainerScreen genericContainerScreen && AhHelper.check(genericContainerScreen);
   }

   private static boolean check7() {
      return AutoBuy.instance != null && AutoBuy.instance.rezhimServera.is("FunTime");
   }

   private static void invoke14() {
      AutoBuy.flag = funTimeAuctionHelperState == FunTimeAuctionHelper.FunTimeAuctionHelperState.SELL;
      AutoBuy.flag2 = funTimeAuctionHelperState == FunTimeAuctionHelper.FunTimeAuctionHelperState.RESELL;
      AutoBuy.flag3 = flag;
   }

   static enum FunTimeAuctionHelperState {
      NONE,
      SELL,
      RESELL;
   }
}
