package ru.metaculture.protection;

import java.util.List;

@HudElementInfo(
   resolve = "Brew Monitor",
   resolve2 = "i"
)
public final class BrewMonitorHud extends HudElement implements MinecraftAccessor {
   private static final BrewMonitorHud INSTANCE = new BrewMonitorHud();
   private static final Animation ANIMATION = new Animation();
   private static final int INT_VALUE = 6;

   private BrewMonitorHud() {
      ru.metaculture.protection.HudPresetManager.invoke2(this);
   }

   public static void invoke(RenderManager renderManager) {
      INSTANCE.invoke2(renderManager);
   }

   private void invoke2(RenderManager renderManager2) {
      if (a_.player != null && a_.world != null) {
         ANIMATION.check();
         ANIMATION.resolve4(AutoPottBot.flag ? 1.0 : 0.0, 0.2F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
         float floatValue = ANIMATION.measure3();
         if (!(floatValue <= 0.01F)) {
            List items = AutoPottBot.items;
            List items2 = AutoPottBot.items2;
            int[] intValues = AutoPottBot.ints;
            int intValue = Math.min(items.size(), 6);
            float floatValue2 = 252.0F;
            float floatValue3 = 52.0F;
            float floatValue4 = 32.0F;
            float floatValue5 = 15.0F;
            float floatValue6 = items2.isEmpty() ? 0.0F : 16.0F;
            float floatValue7 = floatValue3 + floatValue4 + intValue * floatValue5 + floatValue6 + 12.0F;
            HudEditorRenderer.HudEditorRendererState hudEditorRendererState = HudEditorRenderer.getINSTANCE().resolve("HUD_BrewMonitor", 12.0F, 300.0F, floatValue2, floatValue7);
            float floatValue8 = floatValue * this.prozrachnost.getValue();
            float floatValue9 = hudEditorRendererState.floatValue;
            float floatValue10 = hudEditorRendererState.floatValue2;
            float floatValue11 = hudEditorRendererState.floatValue3;
            float floatValue12 = hudEditorRendererState.floatValue4;
            this.invoke3(floatValue9, floatValue10, floatValue11, floatValue12);
            int intValue2 = this.compute6(floatValue8);
            int intValue3 = this.compute7(floatValue8);
            int intValue4 = AutoPottBot.flag ? compute(5954680, floatValue8) : compute(8421512, floatValue8);
            this.invoke(renderManager2, floatValue9, floatValue10, floatValue11, floatValue12, 12.0F, floatValue8);
            float floatValue13 = 12.0F;
            renderManager2.invoke39(floatValue9 + floatValue13 + 4.0F, floatValue10 + 15.0F, 4.0F, 0.0F, 360.0F, intValue4);
            renderManager2.invoke69(FontRegistry.fontObject4, floatValue9 + floatValue13 + 14.0F, floatValue10 + 18.0F, 21.0F, "Brew Monitor", intValue2);
            String text = AutoPottBot.text;
            float floatValue14 = TextMeasureCache.resolve(FontRegistry.fontObject, text, 14.0F).floatValue;
            renderManager2.invoke69(FontRegistry.fontObject, floatValue9 + floatValue11 - floatValue13 - floatValue14, floatValue10 + 17.0F, 14.0F, text, intValue3);
            String text2 = "Варок "
               + AutoPottBot.intValue
               + "   вар "
               + AutoPottBot.intValue2
               + "   своб "
               + AutoPottBot.intValue3
               + "   гот "
               + AutoPottBot.intValue4
               + "   зелий ≈ "
               + AutoPottBot.intValue5;
            renderManager2.invoke69(FontRegistry.fontObject, floatValue9 + floatValue13, floatValue10 + 36.0F, 13.0F, text2, ColorUtils.compute2(intValue3, (int)(235.0F * floatValue8)));
            float floatValue15 = floatValue10 + 54.0F;
            String text3 = "Вода " + intValues[0] + "    Бут " + AutoPottBot.intValue6 + "    Нарост " + intValues[1] + "    Блэйз " + intValues[2];
            String text4 = "Глоу " + intValues[3] + "    Сахар " + intValues[4] + "    Магма " + intValues[5] + "    Редст " + intValues[6];
            renderManager2.invoke69(FontRegistry.fontObject, floatValue9 + floatValue13, floatValue15, 12.5F, text3, intValue3);
            floatValue15 += 14.0F;
            renderManager2.invoke69(FontRegistry.fontObject, floatValue9 + floatValue13, floatValue15, 12.5F, text4, intValue3);
            floatValue15 += 18.0F;
            float floatValue16 = 84.0F;
            float floatValue17 = floatValue9 + floatValue11 - floatValue13 - floatValue16;

            for (int intValue5 = 0; intValue5 < intValue; intValue5++) {
               AutoPottBot.AutoPottBotDisplayEntry autoPottBotDisplayEntry = (AutoPottBot.AutoPottBotDisplayEntry)items.get(intValue5);
               renderManager2.invoke69(FontRegistry.fontObject, floatValue9 + floatValue13, floatValue15 + 1.0F, 12.5F, autoPottBotDisplayEntry.label(), intValue2);
               float floatValue18 = floatValue15 + 2.5F;
               float floatValue19 = 4.0F;
               renderManager2.invoke5(floatValue17, floatValue18, floatValue16, floatValue19, floatValue19 / 2.0F, compute(0, floatValue8 * 0.55F));
               float floatValue20 = Math.max(0.0F, Math.min(1.0F, autoPottBotDisplayEntry.progress()));
               if (floatValue20 > 0.001F) {
                  renderManager2.invoke5(floatValue17, floatValue18, floatValue16 * floatValue20, floatValue19, floatValue19 / 2.0F, compute(autoPottBotDisplayEntry.color(), floatValue8));
               }

               floatValue15 += floatValue5;
            }

            if (!items2.isEmpty()) {
               String text5 = "Не хватает: " + String.join(", ", items2);
               renderManager2.invoke69(FontRegistry.fontObject, floatValue9 + floatValue13, floatValue15 + 2.0F, 12.5F, text5, compute(16737392, floatValue8));
            }

            HudEditorRenderer.getINSTANCE().invoke6(hudEditorRendererState);
            HudSettingsRenderer.invoke2(renderManager2, this, hudEditorRendererState, HudEditorRenderer.getINSTANCE(), a_.getWindow().getScaledWidth(), a_.getWindow().getScaledHeight());
         }
      }
   }

   private static int compute(int i, float f) {
      int intValue6 = (int)(255.0F * Math.max(0.0F, Math.min(1.0F, f)));
      return ColorUtils.compute43(i >> 16 & 0xFF, i >> 8 & 0xFF, i & 0xFF, intValue6);
   }
}
