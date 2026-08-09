package ru.metaculture.protection;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;

public final class ScreenUtil {
   private ScreenUtil() {
   }

   public static <T extends Screen> T resolve(MinecraftClient minecraftClient, Screen screen, Class<T> class_) {
      if (minecraftClient != null && class_.isInstance(minecraftClient.currentScreen)) {
         return (T)class_.cast(minecraftClient.currentScreen);
      } else {
         return (T)(class_.isInstance(screen) && check(minecraftClient, screen) ? class_.cast(screen) : null);
      }
   }

   public static boolean check(MinecraftClient minecraftClient, Screen screen) {
      return minecraftClient != null && minecraftClient.player != null && screen instanceof HandledScreen handledScreen
         ? minecraftClient.player.currentScreenHandler == handledScreen.getScreenHandler()
         : false;
   }

   public static boolean check2(MinecraftClient minecraftClient) {
      return minecraftClient != null
         && minecraftClient.player != null
         && minecraftClient.player.currentScreenHandler != minecraftClient.player.playerScreenHandler;
   }

   public static boolean check3(MinecraftClient minecraftClient, Screen screen) {
      return minecraftClient != null && (minecraftClient.currentScreen != null || check(minecraftClient, screen));
   }
}
