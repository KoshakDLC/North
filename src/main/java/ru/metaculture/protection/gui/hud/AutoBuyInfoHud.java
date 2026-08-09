package ru.metaculture.protection;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@ModuleAccess(
   usernames = {"lichoday"}
)
@HudElementInfo(
   resolve = "AutoBuyInfoHUD",
   resolve2 = "L"
)
public final class AutoBuyInfoHud extends HudElement implements MinecraftAccessor {
   private static final AutoBuyInfoHud INSTANCE = new AutoBuyInfoHud();
   private static final Animation ANIMATION = new Animation();
   private static final Animation ANIMATION_2 = new Animation();
   private static final Animation ANIMATION_3 = new Animation();
   private static final List<AutoBuyInfoHud.AutoBuyInfoHudDisplayEntry> ITEMS = new ArrayList<>(8);
   private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");

   private AutoBuyInfoHud() {
      ru.metaculture.protection.HudPresetManager.invoke2(this);
   }

   public static void invoke(RenderManager renderManager) {
      INSTANCE.invoke2(renderManager);
   }

   public void invoke2(RenderManager renderManager2) {
      if (a_.player != null && AutoBuy.instance != null) {
         AutoBuy autoBuy = AutoBuy.instance;
         ITEMS.clear();
         ITEMS.add(
            new AutoBuyInfoHud.AutoBuyInfoHudDisplayEntry(
               "Статус",
               autoBuy.enabled ? "ON" : "OFF",
               autoBuy.enabled ? ColorUtils.compute43(100, 255, 140, 255) : ColorUtils.compute43(255, 90, 90, 255)
            )
         );
         ITEMS.add(new AutoBuyInfoHud.AutoBuyInfoHudDisplayEntry("Режим", autoBuy.rezhimServera.getValue(), ColorUtils.compute43(120, 190, 255, 255)));
         ITEMS.add(new AutoBuyInfoHud.AutoBuyInfoHudDisplayEntry("Время", resolve4(compute()), ColorUtils.compute43(255, 255, 255, 255)));
         ITEMS.add(new AutoBuyInfoHud.AutoBuyInfoHudDisplayEntry("Сделки", String.valueOf(AutoBuy.compute4()), ColorUtils.compute43(255, 190, 80, 255)));
         ITEMS.add(new AutoBuyInfoHud.AutoBuyInfoHudDisplayEntry("Предметы", String.valueOf(AutoBuy.compute5()), ColorUtils.compute43(255, 190, 80, 255)));
         ITEMS.add(new AutoBuyInfoHud.AutoBuyInfoHudDisplayEntry("Потрачено", resolve3(AutoBuy.compute6()), ColorUtils.compute43(255, 120, 120, 255)));
         ITEMS.add(new AutoBuyInfoHud.AutoBuyInfoHudDisplayEntry("Баланс", resolveSelf(), ColorUtils.compute43(160, 220, 255, 255)));
         ITEMS.add(
            new AutoBuyInfoHud.AutoBuyInfoHudDisplayEntry(
               "Окуп",
               resolve2(),
               AutoBuy.timestamp4 > 0L ? ColorUtils.compute43(100, 255, 140, 255) : ColorUtils.compute43(180, 180, 180, 255)
            )
         );
         ANIMATION.check();
         ANIMATION.resolve4(1.0, 0.2F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
         float floatValue = ANIMATION.measure3();
         if (!(floatValue <= 0.01F)) {
            float floatValue2 = 22.0F;
            float floatValue3 = 7.0F;
            float floatValue4 = 32.0F;
            float floatValue5 = 22.0F;
            float floatValue6 = 5.0F;
            float floatValue7 = 28.0F;
            String text = "AutoBuy";
            float floatValue8 = 0.0F;
            float floatValue9 = 0.0F;

            for (AutoBuyInfoHud.AutoBuyInfoHudDisplayEntry autoBuyInfoHudDisplayEntry : ITEMS) {
               floatValue8 = Math.max(floatValue8, TextMeasureCache.measure(FontRegistry.fontObject, autoBuyInfoHudDisplayEntry.label(), floatValue2));
               floatValue9 = Math.max(floatValue9, TextMeasureCache.measure(FontRegistry.fontObject, autoBuyInfoHudDisplayEntry.value(), floatValue2));
            }

            float floatValue10 = floatValue8 + floatValue9 + 20.0F + 22.0F;
            float floatValue11 = TextMeasureCache.measure(FontRegistry.fontObject4, text, floatValue7) + 42.0F;
            float floatValue12 = Math.max(floatValue10, floatValue11) + floatValue3 * 2.0F;
            float floatValue13 = ITEMS.size() * floatValue5 + 10.0F;
            float floatValue14 = floatValue3 + floatValue4 + floatValue6 + floatValue13 + floatValue3;
            ANIMATION_2.check();
            ANIMATION_3.check();
            ANIMATION_2.resolve4(floatValue12, 0.18F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
            ANIMATION_3.resolve4(floatValue14, 0.18F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
            float floatValue15 = ANIMATION_2.measure3();
            float floatValue16 = ANIMATION_3.measure3();
            float floatValue17 = 10.0F;
            float floatValue18 = 155.0F;
            HudEditorRenderer.HudEditorRendererState hudEditorRendererState = HudEditorRenderer.getINSTANCE().resolve("HUD_AutoBuyInfo", floatValue17, floatValue18, floatValue15, floatValue16);
            float floatValue19 = hudEditorRendererState.floatValue;
            float floatValue20 = hudEditorRendererState.floatValue2;
            float floatValue21 = hudEditorRendererState.floatValue3;
            float floatValue22 = hudEditorRendererState.floatValue4;
            this.invoke3(floatValue19, floatValue20, floatValue21, floatValue22);
            float floatValue23 = floatValue21 / Math.max(1.0F, floatValue15);
            float floatValue24 = floatValue22 / Math.max(1.0F, floatValue16);
            float floatValue25 = Math.min(floatValue23, floatValue24);
            float floatValue26 = floatValue3 * floatValue23;
            float floatValue27 = floatValue3 * floatValue24;
            float floatValue28 = floatValue4 * floatValue24;
            float floatValue29 = floatValue5 * floatValue24;
            float floatValue30 = floatValue2 * floatValue25;
            float floatValue31 = floatValue * this.prozrachnost.getValue();
            int intValue = (int)(255.0F * floatValue31);
            int intValue2 = this.compute(floatValue31);
            int intValue3 = this.compute2(floatValue31);
            int intValue4 = this.compute3(floatValue31);
            int intValue5 = this.compute5(floatValue31);
            int intValue6 = ColorUtils.compute2(this.compute6(1.0F), intValue);
            int intValue7 = ColorUtils.compute2(this.compute7(1.0F), intValue);
            int intValue8 = this.compute9(floatValue31);
            boolean flag = this.check8();
            float floatValue32 = 14.0F;
            float floatValue33 = floatValue21 - floatValue26 * 2.0F;
            this.invoke(renderManager2, floatValue19, floatValue20, floatValue21, floatValue22, floatValue32, floatValue31);
            if (flag) {
               this.invoke(renderManager2, floatValue19 + floatValue26, floatValue20 + floatValue27, floatValue33, floatValue28, 11.0F, floatValue31);
            } else {
               renderManager2.invoke6(floatValue19 + floatValue26, floatValue20 + floatValue27, floatValue33, floatValue28, 11.0F, 11.0F, 4.0F, 4.0F, intValue3);
            }

            renderManager2.invoke69(
               FontRegistry.fontObject4, floatValue19 + floatValue26 + 10.0F * floatValue23, floatValue20 + floatValue27 + floatValue28 * 0.5F + 6.0F * floatValue24, floatValue7 * floatValue25, text, intValue6
            );
            float floatValue34 = 20.0F * floatValue25;
            float floatValue35 = TextMeasureCache.measure(BrandMark.font(), BrandMark.GLYPH, floatValue34);
            renderManager2.invoke69(
               BrandMark.font(), floatValue19 + floatValue26 + floatValue33 - 18.0F * floatValue23 - floatValue35 * 0.5F, floatValue20 + floatValue27 + floatValue28 * 0.5F + 5.0F * floatValue24, floatValue34, BrandMark.GLYPH, intValue8
            );
            float floatValue36 = floatValue20 + floatValue27 + floatValue28 + floatValue6 * floatValue24;
            if (this.check5() || flag) {
               if (flag) {
                  this.invoke2(renderManager2, floatValue19 + floatValue26, floatValue36, floatValue33, floatValue13 * floatValue24, 8.0F, floatValue31);
               } else {
                  renderManager2.invoke6(floatValue19 + floatValue26, floatValue36, floatValue33, floatValue13 * floatValue24, 4.0F, 4.0F, 11.0F, 11.0F, intValue4);
               }
            }

            renderManager2.invoke24(floatValue19, floatValue20, floatValue21, floatValue22, floatValue32, floatValue32, floatValue32, floatValue32);
            float floatValue37 = floatValue36 + 5.0F * floatValue24;

            for (AutoBuyInfoHud.AutoBuyInfoHudDisplayEntry autoBuyInfoHudDisplayEntry2 : ITEMS) {
               renderManager2.invoke69(FontRegistry.fontObject, floatValue19 + floatValue26 + 10.0F * floatValue23, floatValue37 + floatValue29 * 0.5F + 4.0F * floatValue24, floatValue30, autoBuyInfoHudDisplayEntry2.label(), intValue7);
               int intValue9 = ColorUtils.compute2(autoBuyInfoHudDisplayEntry2.color(), intValue);
               float floatValue38 = TextMeasureCache.measure(FontRegistry.fontObject, autoBuyInfoHudDisplayEntry2.value(), floatValue30);
               renderManager2.invoke69(
                  FontRegistry.fontObject, floatValue19 + floatValue21 - floatValue26 - 10.0F * floatValue23 - floatValue38, floatValue37 + floatValue29 * 0.5F + 4.0F * floatValue24, floatValue30, autoBuyInfoHudDisplayEntry2.value(), intValue9
               );
               floatValue37 += floatValue29;
            }

            renderManager2.invoke25();
            HudEditorRenderer.getINSTANCE().invoke6(hudEditorRendererState);
            HudSettingsRenderer.invoke2(renderManager2, this, hudEditorRendererState, HudEditorRenderer.getINSTANCE(), a_.getWindow().getScaledWidth(), a_.getWindow().getScaledHeight());
         }
      }
   }

   private static long compute() {
      return AutoBuy.timestamp <= 0L ? 0L : Math.max(0L, System.currentTimeMillis() - AutoBuy.timestamp);
   }

   private static String resolveSelf() {
      if (AutoBuy.timestamp2 > 0L && AutoBuy.timestamp3 > 0L) {
         long longValue = AutoBuy.compute7();
         return resolve3(AutoBuy.timestamp3) + " (" + (longValue >= 0L ? "+" : "") + resolve3(longValue) + ")";
      } else {
         return "N/A";
      }
   }

   private static String resolve2() {
      if (AutoBuy.timestamp4 > 0L && AutoBuy.timestamp > 0L) {
         return SIMPLE_DATE_FORMAT.format(new Date(AutoBuy.timestamp4)) + " (" + resolve4(AutoBuy.timestamp4 - AutoBuy.timestamp) + ")";
      } else {
         return AutoBuy.compute6() <= 0L ? "-" : "ожидание";
      }
   }

   private static String resolve3(long l) {
      long longValue2 = Math.abs(l);
      String text2 = String.format(Locale.ROOT, "%,d", longValue2).replace(',', ' ') + "¤";
      return l < 0L ? "-" + text2 : text2;
   }

   private static String resolve4(long l) {
      long longValue3 = Math.max(0L, l / 1000L);
      long longValue4 = longValue3 / 3600L;
      long longValue5 = longValue3 % 3600L / 60L;
      long longValue6 = longValue3 % 60L;
      return longValue4 > 0L ? String.format(Locale.ROOT, "%d:%02d:%02d", longValue4, longValue5, longValue6) : String.format(Locale.ROOT, "%02d:%02d", longValue5, longValue6);
   }

   record AutoBuyInfoHudDisplayEntry(String label, String value, int color) {
   }
}
