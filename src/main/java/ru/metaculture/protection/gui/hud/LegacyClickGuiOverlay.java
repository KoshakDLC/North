package ru.metaculture.protection;

import net.minecraft.client.gui.DrawContext;
import org.joml.Vector4f;

public class LegacyClickGuiOverlay extends LegacyClickGuiState {
   public static void invoke(RenderManager renderManager, DrawContext drawContext, int i, int j) {
      float floatValue = (float)LegacyClickGuiState.easedAnimation.getDoubleValue4();
      int intValue = (int)(255.0F * floatValue);
      float floatValue2 = client.getWindow().getScaledWidth() / 2.0F;
      float floatValue3 = client.getWindow().getScaledHeight() - 16 + (15.0F - 15.0F * floatValue);
      int intValue4 = LegacyClickGuiState.themes.length;
      float floatValue4 = 18.0F;
      float floatValue5 = intValue4 * floatValue4;
      float floatValue6 = floatValue2 - floatValue5 / 2.0F;
      int intValue5 = RenderManager.RenderManagerState.compute24(RenderManager.RenderManagerState.compute25(RenderManager.RenderManagerState.compute5(1, 1), 1.0F), (int)(12.0F * floatValue));
      int intValue6 = RenderManager.RenderManagerState.compute24(RenderManager.RenderManagerState.compute3(1, 1), (int)(188.0F * floatValue));
      EspPreviewBounds.invoke2(renderManager, floatValue6 - 9.0F + 1.0F, floatValue3 - 5.0F, floatValue5 + 9.0F - 1.0F, 21.25F, new Vector4f(6.5F, 6.5F, 0.0F, 0.0F), intValue6);
      renderManager.invoke28(floatValue6 - 9.0F + 1.0F, floatValue3 - 5.0F, floatValue5 + 9.0F - 1.0F, 25.0F, 6.5F, intValue5, 0.45F);
      float floatValue7 = floatValue6;

      for (Theme theme : LegacyClickGuiState.themes) {
         theme.directionalAnimation.invoke3(theme == LegacyClickGuiState.theme ? AnimationDirection.FORWARDS : AnimationDirection.BACKWARDS);
         renderManager.invoke5(floatValue7, floatValue3 + 0.76F, 9.25F, 9.25F, 10.0F, RenderManager.RenderManagerState.resolve3(theme.getColor(), intValue).getRGB());
         floatValue7 += floatValue4;
      }
   }

   public static void invoke2(double d, double e, int i) {
      int intValue7 = (int)LegacyMatrixScaleUtils.resolve((float)d, (float)e)[0];
      int intValue8 = (int)LegacyMatrixScaleUtils.resolve((float)d, (float)e)[1];
      if (!check(intValue7, intValue8)) {
         float floatValue8 = client.getWindow().getScaledWidth() / 2.0F;
         float floatValue9 = client.getWindow().getScaledHeight() - 16;
         int intValue9 = LegacyClickGuiState.themes.length;
         float floatValue10 = 18.0F;
         float floatValue11 = intValue9 * floatValue10;
         float floatValue12 = floatValue8 - floatValue11 / 2.0F;
         float floatValue13 = floatValue12;

         for (Theme theme2 : LegacyClickGuiState.themes) {
            if (RenderMath.check(intValue7, intValue8, floatValue13, floatValue9, 16.0F, 16.0F) && theme2 != LegacyClickGuiState.theme) {
               LegacyClickGuiState.directionalAnimation4.invoke();
               ScreenTransitionController.getINSTANCE().invoke2((double)intValue7, (double)intValue8, theme2.getColor().getRGB(), theme2.getColor4().getRGB());
               LegacyClickGuiState.theme = theme2;
               LegacyClickGuiState.theme2 = theme2;
               WildClient.INSTANCE.themeManager.invoke2(theme2);
            }

            floatValue13 += floatValue10;
         }
      }
   }

   private static boolean check(int i, int j) {
      return RenderMath.check(
         i, j, LegacyClickGuiState.floatValue6, LegacyClickGuiState.floatValue7, LegacyClickGuiState.floatValue8, LegacyClickGuiState.floatValue9
      );
   }
}
