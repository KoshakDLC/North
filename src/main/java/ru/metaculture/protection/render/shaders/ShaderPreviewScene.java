package ru.metaculture.protection;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.GlTexture;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;

public final class ShaderPreviewScene {
   private ShaderPreviewScene() {
   }

   public static void invoke(
      RenderManager renderManager, ThemeContext themeContext, ShaderSurface shaderSurface, float f, float g, float h, float i, float j, float k, float l
   ) {
      if (shaderSurface == null) {
         shaderSurface = ShaderSurface.PREVIEW_ONLY;
      }

      Metrics metrics = themeContext.getMetrics();
      ColorScheme colorScheme = themeContext.getColorScheme();
      switch (shaderSurface) {
         case BACKGROUND:
         case MENU_BACKGROUND:
            invoke4(renderManager, metrics, colorScheme, f, g, h, i, l);
            break;
         case MENU_PANEL_BG:
            invoke6(renderManager, metrics, colorScheme, f, g, h, i);
            break;
         case HUD:
         case HUD_OVERLAY:
            invoke8(renderManager, metrics, colorScheme, f, g, h, i);
            break;
         case ESP:
         case ESP_OVERLAY:
            invoke10(renderManager, metrics, colorScheme, f, g, h, i, l);
            break;
         case ENTITY_HIGHLIGHT:
            invoke13(renderManager, metrics, colorScheme, f, g, h, i);
            break;
         case PREVIEW_ONLY:
            invoke3(renderManager, f, g, h, i);
      }
   }

   public static void invoke2(
      RenderManager renderManager2, ThemeContext themeContext2, ShaderSurface shaderSurface2, float f, float g, float h, float i, float j, float k
   ) {
      if (shaderSurface2 != null) {
         Metrics metrics2 = themeContext2.getMetrics();
         ColorScheme colorScheme2 = themeContext2.getColorScheme();
         switch (shaderSurface2) {
            case BACKGROUND:
            case MENU_BACKGROUND:
               invoke5(renderManager2, metrics2, colorScheme2, f, g, h, i);
               break;
            case MENU_PANEL_BG:
               invoke7(renderManager2, metrics2, colorScheme2, f, g, h, i);
               break;
            case HUD:
            case HUD_OVERLAY:
               invoke9(renderManager2, metrics2, colorScheme2, f, g, h, i);
               break;
            case ESP:
            case ESP_OVERLAY:
               invoke12(renderManager2, metrics2, colorScheme2, f, g, h, i);
               break;
            case ENTITY_HIGHLIGHT:
               invoke14(renderManager2, metrics2, colorScheme2, f, g, h, i);
         }
      }
   }

   private static void invoke3(RenderManager renderManager3, float f, float g, float h, float i) {
      renderManager3.invoke5(f, g, h, i, 0.0F, ColorScheme.compute5(3, 5, 9, 240));
   }

   private static void invoke4(RenderManager renderManager4, Metrics metrics3, ColorScheme colorScheme3, float f, float g, float h, float i, float j) {
      renderManager4.invoke5(f, g, h, i, 0.0F, ColorScheme.compute5(11, 13, 21, 232));
      int intValue = (int)(h / metrics3.measure(14.0F)) + 1;
      int intValue2 = (int)(i / metrics3.measure(14.0F)) + 1;

      for (int intValue3 = 0; intValue3 < intValue; intValue3++) {
         float floatValue = f + intValue3 * metrics3.measure(14.0F);
         renderManager4.invoke5(floatValue, g, 1.0F, i, 0.0F, ColorScheme.compute5(255, 255, 255, 5));
      }

      for (int intValue4 = 0; intValue4 < intValue2; intValue4++) {
         float floatValue2 = g + intValue4 * metrics3.measure(14.0F);
         renderManager4.invoke5(f, floatValue2, h, 1.0F, 0.0F, ColorScheme.compute5(255, 255, 255, 5));
      }

      float floatValue3 = (float)Math.sin(j * Math.PI * 2.0) * 0.5F + 0.5F;
      renderManager4.invoke41(
         f + h * 0.2F,
         g + i * 0.2F,
         h * 0.6F,
         i * 0.6F,
         Math.min(h, i) * 0.3F,
         Math.min(h, i) * 0.3F,
         Math.min(h, i) * 0.1F,
         ColorScheme.compute6(colorScheme3.getIntValue14(), Math.round(18.0F + 22.0F * floatValue3))
      );
   }

   private static void invoke5(RenderManager renderManager5, Metrics metrics4, ColorScheme colorScheme4, float f, float g, float h, float i) {
      float floatValue4 = metrics4.measure(12.0F);
      float floatValue5 = f + floatValue4;
      float floatValue6 = g + floatValue4;
      float floatValue7 = h - floatValue4 * 2.0F;
      float floatValue8 = i - floatValue4 * 2.0F;
      renderManager5.invoke5(floatValue5, floatValue6, floatValue7, floatValue8, metrics4.measure(8.0F), ColorScheme.compute6(colorScheme4.getIntValue(), 132));
      renderManager5.invoke28(floatValue5, floatValue6, floatValue7, floatValue8, metrics4.measure(8.0F), ColorScheme.compute6(colorScheme4.getIntValue14(), 96), 0.7F);
      ClickGuiRenderUtils.invoke3(
         renderManager5,
         metrics4,
         FontRegistry.fontObject4,
         floatValue5 + metrics4.measure(10.0F),
         floatValue6 + metrics4.measure(8.0F),
         9.0F,
         "ClickGUI mock",
         colorScheme4.getIntValue13()
      );
      float floatValue9 = metrics4.measure(10.0F);

      for (int intValue5 = 0; intValue5 < 4; intValue5++) {
         renderManager5.invoke5(
            floatValue5 + metrics4.measure(10.0F) + intValue5 * metrics4.measure(14.0F),
            floatValue6 + floatValue8 - metrics4.measure(18.0F),
            floatValue9,
            floatValue9,
            floatValue9 * 0.5F,
            ColorScheme.compute6(colorScheme4.getIntValue14(), 156 - intValue5 * 28)
         );
      }
   }

   private static void invoke6(RenderManager renderManager6, Metrics metrics5, ColorScheme colorScheme5, float f, float g, float h, float i) {
      renderManager6.invoke5(f, g, h, i, 0.0F, ColorScheme.compute5(9, 11, 17, 232));
   }

   private static void invoke7(RenderManager renderManager7, Metrics metrics6, ColorScheme colorScheme6, float f, float g, float h, float i) {
      float floatValue10 = metrics6.measure(10.0F);
      float floatValue11 = metrics6.measure(20.0F);
      renderManager7.invoke5(f + floatValue10, g + floatValue10, h - floatValue10 * 2.0F, floatValue11, metrics6.measure(6.0F), ColorScheme.compute5(255, 255, 255, 14));
      renderManager7.invoke5(
         f + floatValue10 + metrics6.measure(6.0F),
         g + floatValue10 + metrics6.measure(6.0F),
         metrics6.measure(8.0F),
         metrics6.measure(8.0F),
         2.0F,
         colorScheme6.getIntValue14()
      );
      ClickGuiRenderUtils.invoke3(
         renderManager7,
         metrics6,
         FontRegistry.fontObject4,
         f + floatValue10 + metrics6.measure(20.0F),
         g + floatValue10 + metrics6.measure(4.0F),
         9.0F,
         "Module name",
         colorScheme6.getIntValue13()
      );
      float floatValue12 = metrics6.measure(14.0F);
      float floatValue13 = g + floatValue10 + floatValue11 + metrics6.measure(6.0F);

      for (int intValue6 = 0; intValue6 < 3; intValue6++) {
         renderManager7.invoke5(
            f + floatValue10,
            floatValue13 + intValue6 * (floatValue12 + metrics6.measure(4.0F)),
            h - floatValue10 * 2.0F,
            floatValue12,
            metrics6.measure(4.0F),
            ColorScheme.compute5(255, 255, 255, 12)
         );
         renderManager7.invoke5(
            f + floatValue10 + metrics6.measure(4.0F),
            floatValue13 + intValue6 * (floatValue12 + metrics6.measure(4.0F)) + metrics6.measure(2.0F),
            metrics6.measure(8.0F),
            metrics6.measure(8.0F),
            1.0F,
            ColorScheme.compute6(colorScheme6.getIntValue15(), 200)
         );
      }
   }

   private static void invoke8(RenderManager renderManager8, Metrics metrics7, ColorScheme colorScheme7, float f, float g, float h, float i) {
      renderManager8.invoke5(f, g, h, i, 0.0F, ColorScheme.compute5(35, 50, 78, 192));
      renderManager8.invoke5(f, g + i * 0.62F, h, i * 0.38F, 0.0F, ColorScheme.compute5(56, 86, 52, 200));
      renderManager8.invoke5(f, g + i - metrics7.measure(4.0F), h, metrics7.measure(4.0F), 0.0F, ColorScheme.compute5(28, 34, 22, 220));
   }

   private static void invoke9(RenderManager renderManager9, Metrics metrics8, ColorScheme colorScheme8, float f, float g, float h, float i) {
      float floatValue14 = metrics8.measure(160.0F);
      float floatValue15 = metrics8.measure(20.0F);
      float floatValue16 = f + (h - floatValue14) * 0.5F;
      float floatValue17 = g + i - floatValue15 - metrics8.measure(10.0F);
      renderManager9.invoke5(floatValue16, floatValue17, floatValue14, floatValue15, 2.0F, ColorScheme.compute5(20, 22, 28, 200));
      renderManager9.invoke28(floatValue16, floatValue17, floatValue14, floatValue15, 2.0F, ColorScheme.compute5(50, 52, 62, 220), 0.7F);

      for (int intValue7 = 0; intValue7 < 9; intValue7++) {
         float floatValue18 = floatValue14 / 9.0F;
         renderManager9.invoke5(
            floatValue16 + intValue7 * floatValue18 + 1.0F,
            floatValue17 + 1.0F,
            floatValue18 - 2.0F,
            floatValue15 - 2.0F,
            1.0F,
            intValue7 == 4 ? ColorScheme.compute5(220, 220, 220, 110) : ColorScheme.compute5(255, 255, 255, 16)
         );
      }

      for (int intValue8 = 0; intValue8 < 10; intValue8++) {
         float floatValue19 = floatValue17 - metrics8.measure(12.0F);
         renderManager9.invoke5(
            floatValue16 + intValue8 * metrics8.measure(7.0F) + metrics8.measure(3.0F),
            floatValue19,
            metrics8.measure(6.0F),
            metrics8.measure(6.0F),
            1.0F,
            ColorScheme.compute5(220, 40, 40, 230)
         );
      }

      for (int intValue9 = 0; intValue9 < 10; intValue9++) {
         float floatValue20 = floatValue17 - metrics8.measure(20.0F);
         renderManager9.invoke5(
            floatValue16 + floatValue14 - (intValue9 + 1) * metrics8.measure(7.0F) - metrics8.measure(3.0F),
            floatValue20,
            metrics8.measure(6.0F),
            metrics8.measure(6.0F),
            1.0F,
            ColorScheme.compute5(54, 84, 250, 230)
         );
      }

      renderManager9.invoke5(
         floatValue16 + floatValue14 * 0.5F - 1.0F,
         g + i * 0.5F - metrics8.measure(4.0F),
         2.0F,
         metrics8.measure(8.0F),
         0.0F,
         ColorScheme.compute5(255, 255, 255, 220)
      );
      renderManager9.invoke5(
         floatValue16 + floatValue14 * 0.5F - metrics8.measure(4.0F),
         g + i * 0.5F - 1.0F,
         metrics8.measure(8.0F),
         2.0F,
         0.0F,
         ColorScheme.compute5(255, 255, 255, 220)
      );
   }

   private static void invoke10(RenderManager renderManager10, Metrics metrics9, ColorScheme colorScheme9, float f, float g, float h, float i, float j) {
      renderManager10.invoke5(f, g, h, i, 0.0F, ColorScheme.compute5(14, 18, 28, 232));

      for (int intValue10 = 0; intValue10 < 20; intValue10++) {
         float floatValue21 = f + intValue10 * 67 % (int)h;
         float floatValue22 = g + intValue10 * 41 % (int)i;
         renderManager10.invoke5(floatValue21, floatValue22, 1.0F, 1.0F, 0.0F, ColorScheme.compute5(255, 255, 255, 22));
      }

      float floatValue23 = (float)Math.sin(j * Math.PI * 2.0) * metrics9.measure(8.0F);
      float floatValue24 = metrics9.measure(40.0F);
      float floatValue25 = metrics9.measure(28.0F);
      invoke11(renderManager10, metrics9, f + h * 0.28F + floatValue23, g + i * 0.36F, floatValue24 * 0.55F, floatValue24, colorScheme9.getIntValue14());
      invoke11(renderManager10, metrics9, f + h * 0.6F - floatValue23 * 0.6F, g + i * 0.48F, floatValue25 * 0.55F, floatValue25, colorScheme9.getIntValue15());
   }

   private static void invoke11(RenderManager renderManager11, Metrics metrics10, float f, float g, float h, float i, int j) {
      renderManager11.invoke28(f, g, h, i, 1.0F, ColorScheme.compute6(j, 220), 1.2F);
      float floatValue26 = h * 0.4F;
      renderManager11.invoke5(f + (h - floatValue26) * 0.5F, g - floatValue26 - 1.0F, floatValue26, floatValue26, 1.0F, ColorScheme.compute6(j, 80));
      renderManager11.invoke28(f + (h - floatValue26) * 0.5F, g - floatValue26 - 1.0F, floatValue26, floatValue26, 1.0F, ColorScheme.compute6(j, 220), 1.0F);
   }

   private static void invoke12(RenderManager renderManager12, Metrics metrics11, ColorScheme colorScheme10, float f, float g, float h, float i) {
      ClickGuiRenderUtils.invoke3(
         renderManager12,
         metrics11,
         FontRegistry.fontObject,
         f + metrics11.measure(8.0F),
         g + metrics11.measure(6.0F),
         8.0F,
         "ESP fill preview",
         ColorScheme.compute6(colorScheme10.getIntValue13(), 192)
      );
   }

   private static void invoke13(RenderManager renderManager13, Metrics metrics12, ColorScheme colorScheme11, float f, float g, float h, float i) {
      renderManager13.invoke5(f, g, h, i, 0.0F, ColorScheme.compute5(7, 9, 14, 240));
      float floatValue27 = Math.min(h, i) * 0.55F;
      renderManager13.invoke41(
         f + h * 0.5F - floatValue27 * 0.5F,
         g + i * 0.5F - floatValue27 * 0.5F,
         floatValue27,
         floatValue27,
         floatValue27 * 0.5F,
         floatValue27 * 0.45F,
         floatValue27 * 0.1F,
         ColorScheme.compute6(colorScheme11.getIntValue15(), 56)
      );

      for (int intValue11 = 0; intValue11 < 6; intValue11++) {
         float floatValue28 = intValue11 / 6.0F;
         renderManager13.invoke5(f, g + i * floatValue28, h, 1.0F, 0.0F, ColorScheme.compute5(255, 255, 255, 6));
      }
   }

   private static void invoke14(RenderManager renderManager14, Metrics metrics13, ColorScheme colorScheme12, float f, float g, float h, float i) {
      float floatValue29 = Math.min(h, i) * 0.36F;
      float floatValue30 = f + (h - floatValue29) * 0.5F;
      float floatValue31 = g + (i - floatValue29) * 0.5F - metrics13.measure(4.0F);
      boolean flag = false;

      try {
         MinecraftClient client = MinecraftClient.getInstance();
         if (client != null && client.player != null) {
            Identifier identifier = client.getSkinProvider().getSkinTextures(client.player.getGameProfile()).texture();
            AbstractTexture abstractTexture = client.getTextureManager().getTexture(identifier);
            if (abstractTexture != null && abstractTexture.getGlTexture() instanceof GlTexture glTexture && glTexture.getGlId() > 0) {
               int intValue12 = glTexture.getGlId();
               GL11.glBindTexture(3553, intValue12);
               GL11.glTexParameteri(3553, 10241, 9728);
               GL11.glTexParameteri(3553, 10240, 9728);
               renderManager14.invoke12(intValue12, floatValue30, floatValue31, floatValue29, floatValue29, 0.125F, 0.125F, 0.25F, 0.25F, floatValue29 * 0.18F);
               renderManager14.invoke12(intValue12, floatValue30, floatValue31, floatValue29, floatValue29, 0.625F, 0.125F, 0.75F, 0.25F, floatValue29 * 0.18F);
               flag = true;
            }
         }
      } catch (Throwable exception) {
      }

      if (!flag) {
         renderManager14.invoke5(floatValue30, floatValue31, floatValue29, floatValue29, floatValue29 * 0.18F, ColorScheme.compute6(colorScheme12.getIntValue14(), 200));
         ClickGuiRenderUtils.invoke4(renderManager14, metrics13, FontRegistry.fontObject4, floatValue30, floatValue31, floatValue29, floatValue29 * 0.42F, "P", colorScheme12.getIntValue13());
      }

      renderManager14.invoke28(floatValue30, floatValue31, floatValue29, floatValue29, floatValue29 * 0.18F, ColorScheme.compute6(colorScheme12.getIntValue14(), 156), 0.8F);
      ClickGuiRenderUtils.invoke3(
         renderManager14,
         metrics13,
         FontRegistry.fontObject,
         f + metrics13.measure(8.0F),
         g + i - metrics13.measure(14.0F),
         8.0F,
         "Entity overlay preview",
         ColorScheme.compute6(colorScheme12.getIntValue13(), 192)
      );
   }
}
