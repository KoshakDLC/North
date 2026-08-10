package ru.metaculture.protection;

import java.awt.Color;
import lombok.Generated;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;

public final class ChatUtil implements MinecraftAccessor {
   public static void sendClientMessage(String string) {
      MinecraftClient client = MinecraftClient.getInstance();
      if (client.inGameHud != null && client.inGameHud.getChatHud() != null) {
         Theme theme = WildClient.INSTANCE != null && WildClient.INSTANCE.themeManager != null ? WildClient.INSTANCE.themeManager.getTheme() : Theme.WILD;
         MutableText mutableText = Text.literal("")
            .append(createGradientText("North", theme))
            .append(Text.literal(" » ").formatted(Formatting.WHITE))
            .append(Text.literal(string).formatted(Formatting.GRAY));
         client.inGameHud.getChatHud().addMessage(mutableText);
      } else {
         System.out.println("[North] " + string);
      }
   }

   public static void sendAiMessage(String string) {
      MinecraftClient client2 = MinecraftClient.getInstance();
      if (client2.inGameHud != null && client2.inGameHud.getChatHud() != null) {
         Theme theme2 = WildClient.INSTANCE != null && WildClient.INSTANCE.themeManager != null ? WildClient.INSTANCE.themeManager.getTheme() : Theme.WILD;
         MutableText mutableText2 = Text.literal("")
            .append(createGradientText("AI", theme2))
            .append(Text.literal(" » ").formatted(Formatting.DARK_GRAY))
            .append(Text.literal(string).formatted(Formatting.WHITE));
         client2.inGameHud.getChatHud().addMessage(mutableText2);
      }
   }

   public static Text createGradientText(String string, Theme theme3) {
      MutableText mutableText3 = Text.empty();
      int intValue = string.length();
      Color color = theme3.getColor();
      Color color2 = theme3.getColor6();
      long longValue = System.currentTimeMillis();

      for (int intValue2 = 0; intValue2 < intValue; intValue2++) {
         float floatValue = intValue2 * 0.15F + (float)longValue / 1500.0F;
         float floatValue2 = (float)(Math.sin(floatValue) + 1.0) / 2.0F;
         int intValue3 = (int)(color.getRed() * (1.0F - floatValue2) + color2.getRed() * floatValue2);
         int intValue4 = (int)(color.getGreen() * (1.0F - floatValue2) + color2.getGreen() * floatValue2);
         int intValue5 = (int)(color.getBlue() * (1.0F - floatValue2) + color2.getBlue() * floatValue2);
         TextColor textColor = TextColor.fromRgb(intValue3 << 16 | intValue4 << 8 | intValue5);
         MutableText mutableText4 = Text.literal(String.valueOf(string.charAt(intValue2))).setStyle(Style.EMPTY.withColor(textColor));
         mutableText3.append(mutableText4);
      }

      return mutableText3;
   }

   @Generated
   private ChatUtil() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }
}
