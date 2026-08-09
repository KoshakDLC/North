package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.network.PlayerListEntry;

@HudElementInfo(
   resolve = "InformationHUD",
   resolve2 = "w"
)
public final class InformationHud extends HudElement implements MinecraftAccessor {
   private static final InformationHud INSTANCE = new InformationHud();
   private static double doubleValue = 0.0;
   private static final Animation ANIMATION = new Animation();
   private static final Animation ANIMATION_2 = new Animation();
   private static final Animation ANIMATION_3 = new Animation();
   private static final Animation ANIMATION_4 = new Animation();
   private static boolean flag;
   private static final List<InformationHud.InformationHudDisplayEntry> ITEMS = new ArrayList<>(4);
   private final ModeSetting vid = new ModeSetting("Вид", "Стандарт", "Стандарт", "Строка");
   private final BooleanSetting pokazyvatVerhushku = new BooleanSetting("Показывать верхушку", true);
   private final BooleanSetting animatsiyaZnacheniy = new BooleanSetting("Анимация значений", true);
   private final Map<String, HudMetricUtils> valuesByKey = new HashMap<>();
   private final GroupSetting otobrazhaemyeDannye = new GroupSetting(
      "Отображаемые данные",
      new BooleanSetting("Скорость (BPS)", true),
      new BooleanSetting("Тикрейт (TPS)", true),
      new BooleanSetting("Координаты (XYZ)", true),
      new BooleanSetting("Ping (MS)", true)
   );

   private InformationHud() {
      this.invoke(this.vid);
      this.invoke(this.pokazyvatVerhushku);
      this.invoke(this.animatsiyaZnacheniy);
      this.invoke(this.otobrazhaemyeDannye);
      ru.metaculture.protection.HudPresetManager.invoke2(this);
   }

   public static InformationHud getINSTANCE() {
      return INSTANCE;
   }

   public static void invoke(RenderManager renderManager) {
      INSTANCE.invoke2(renderManager);
   }

   public void invoke2(RenderManager renderManager2) {
      if (a_.player != null) {
         boolean flag = false;
         boolean flag2 = true;
         ANIMATION_2.check();
         ANIMATION.check();
         ANIMATION_2.resolve4(flag2 ? 1.0 : 0.0, 0.18F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
         if (flag2) {
             if (!flag) {
               ANIMATION.invoke(-10.0);
            }

            ANIMATION.resolve4(0.0, 0.2F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
         } else {
             if (flag) {
               ANIMATION.invoke(0.0);
            }

            ANIMATION.resolve4(10.0, 0.2F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
         }

          flag = flag2;
         float floatValue = ANIMATION_2.measure3();
         if (!(floatValue <= 0.01F)) {
            float floatValue2 = 24.0F;
            boolean flag3 = this.pokazyvatVerhushku.isEnabled();
            boolean flag4 = HudModule.check2();
            HudLayoutManager.HudLayoutManagerState hudLayoutManagerState = flag4 ? HudLayoutManager.resolve4("HUD_Info") : null;
            float floatValue3 = flag4 ? hudLayoutManagerState.floatValue8 : 7.0F;
            float floatValue4 = flag3 ? (flag4 ? hudLayoutManagerState.floatValue10 : 32.0F) : 0.0F;
            float floatValue5 = flag3 ? (flag4 ? hudLayoutManagerState.floatValue9 : 5.0F) : 0.0F;
            float floatValue6 = 22.0F;
            float floatValue7 = flag4 ? hudLayoutManagerState.floatValue11 : 22.0F;
            float floatValue8 = flag4 ? Math.max(4.0F, hudLayoutManagerState.floatValue8 + 3.0F) : 10.0F;
            ITEMS.clear();
            if (this.otobrazhaemyeDannye.isEnabled("Скорость (BPS)")) {
               ITEMS.add(new InformationHud.InformationHudDisplayEntry("BPS", resolveSelf(), ColorUtils.compute43(255, 90, 90, 255)));
            }

            if (this.otobrazhaemyeDannye.isEnabled("Тикрейт (TPS)")) {
               ITEMS.add(new InformationHud.InformationHudDisplayEntry("TPS", resolve4(TpsTracker.getDoubleValue()), ColorUtils.compute43(255, 170, 40, 255)));
            }

            if (this.otobrazhaemyeDannye.isEnabled("Координаты (XYZ)")) {
               ITEMS.add(new InformationHud.InformationHudDisplayEntry("XYZ", resolve2(), ColorUtils.compute43(100, 255, 100, 255)));
            }

            if (this.otobrazhaemyeDannye.isEnabled("Ping (MS)")) {
               ITEMS.add(new InformationHud.InformationHudDisplayEntry("PING", resolve3(), ColorUtils.compute43(120, 190, 255, 255)));
            }

            if (this.vid.is("Строка")) {
               this.invoke3(renderManager2, ITEMS, floatValue);
            } else {
               String text = "Information";
               float floatValue9 = TextMeasureCache.resolve(FontRegistry.fontObject4, text, flag4 ? hudLayoutManagerState.floatValue12 : 26.0F).floatValue;
               float floatValue10 = floatValue8 * 2.0F + 30.0F;

               for (InformationHud.InformationHudDisplayEntry informationHudDisplayEntry : ITEMS) {
                  float floatValue11 = TextMeasureCache.resolve(FontRegistry.fontObject, informationHudDisplayEntry.label, floatValue2).floatValue
                     + TextMeasureCache.resolve(FontRegistry.fontObject, informationHudDisplayEntry.value, floatValue2).floatValue
                     + floatValue8 * 2.0F
                     + 20.0F;
                  floatValue10 = Math.max(floatValue10, floatValue11);
               }

               float floatValue12 = ITEMS.size() * floatValue7 + 12.0F;
               float floatValue13 = floatValue10 + floatValue3 * 2.0F;
               if (flag3) {
                  float floatValue14 = floatValue9 + floatValue6 + floatValue8 * 2.0F + 24.0F;
                  floatValue13 = Math.max(floatValue13, floatValue14 + floatValue3 * 2.0F);
               }

               floatValue10 = floatValue13 - floatValue3 * 2.0F;
               float floatValue15 = floatValue3 + floatValue12 + floatValue3;
               if (flag3) {
                  floatValue15 = floatValue3 + floatValue4 + floatValue5 + floatValue12 + floatValue3;
               }

               ANIMATION_3.check();
               ANIMATION_4.check();
               ANIMATION_3.resolve4(floatValue13, 0.18F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
               ANIMATION_4.resolve4(floatValue15, 0.18F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
               float floatValue16 = ANIMATION_3.measure3();
               float floatValue17 = ANIMATION_4.measure3();
               float floatValue18 = 10.0F;
               float floatValue19 = 10.0F;
               HudEditorRenderer.HudEditorRendererState hudEditorRendererState = HudEditorRenderer.getINSTANCE().resolve("HUD_Info", floatValue18, floatValue19, floatValue16, floatValue17);
               float floatValue20 = hudEditorRendererState.floatValue + ANIMATION.measure3();
               float floatValue21 = hudEditorRendererState.floatValue2;
               float floatValue22 = Math.max(1.0F, hudEditorRendererState.floatValue3);
               float floatValue23 = Math.max(1.0F, hudEditorRendererState.floatValue4);
               boolean flag5 = this.check(floatValue22, floatValue23, floatValue16, floatValue17, ITEMS, flag3);
               float floatValue24 = flag5 ? measure5(floatValue23, 38.0F, 54.0F) : measure5(floatValue23, Math.max(34.0F, floatValue17 * 0.76F), Math.max(floatValue17, floatValue17 * 1.08F));
               hudEditorRendererState = HudEditorRenderer.getINSTANCE().resolve3(hudEditorRendererState, floatValue22, floatValue24, floatValue16, floatValue17);
               floatValue20 = hudEditorRendererState.floatValue + ANIMATION.measure3();
               floatValue21 = hudEditorRendererState.floatValue2;
               floatValue22 = hudEditorRendererState.floatValue3;
               floatValue24 = hudEditorRendererState.floatValue4;
               flag5 = this.check(floatValue22, floatValue24, floatValue16, floatValue17, ITEMS, flag3);
               this.invoke3(floatValue20, floatValue21, floatValue22, floatValue24);
               float floatValue25 = measure5(floatValue22 / Math.max(1.0F, floatValue16), 0.76F, 1.28F);
               float floatValue26 = flag5 ? 1.0F : measure5(floatValue24 / Math.max(1.0F, floatValue17), 0.78F, 1.08F);
               float floatValue27 = measure5(Math.min(floatValue25, floatValue26), 0.86F, 1.12F);
               float floatValue28 = measure5(floatValue3 * floatValue25, 6.0F, 10.0F);
               float floatValue29 = measure5(floatValue3 * floatValue26, 6.0F, 9.0F);
               float floatValue30 = flag3 ? measure5(floatValue4 * floatValue26, 27.0F, 36.0F) : 0.0F;
               float floatValue31 = flag3 ? measure5(floatValue5 * floatValue26, 4.0F, 7.0F) : 0.0F;
               float floatValue32 = measure5(floatValue7 * floatValue26, 19.0F, 26.0F);
               float floatValue33 = measure5(floatValue8 * floatValue25, 8.0F, 13.0F);
               float floatValue34 = measure5(floatValue2 * floatValue27, 21.0F, 27.0F);
               float floatValue35 = floatValue * this.prozrachnost.getValue();
               int intValue = (int)(255.0F * floatValue35);
               int intValue2 = this.compute(floatValue35);
               int intValue3 = this.compute2(floatValue35);
               int intValue4 = this.compute3(floatValue35);
               int intValue5 = this.compute5(floatValue35);
               int intValue6 = this.compute6(floatValue35);
               int intValue7 = this.compute8(floatValue35);
               boolean flag6 = this.check8();
               if (flag5) {
                  this.invoke4(renderManager2, floatValue20, floatValue21, floatValue22, floatValue24, text, flag3, ITEMS, floatValue35, intValue6, intValue7);
                  HudEditorRenderer.getINSTANCE().invoke6(hudEditorRendererState);
                  HudSettingsRenderer.invoke2(
                     renderManager2, this, hudEditorRendererState, HudEditorRenderer.getINSTANCE(), a_.getWindow().getScaledWidth(), a_.getWindow().getScaledHeight()
                  );
               } else {
                  float floatValue36 = flag4 ? hudLayoutManagerState.floatValue : measure5(Math.min(floatValue22, floatValue24) * 0.16F, 9.0F, 14.0F);
                  float floatValue37 = flag4 ? hudLayoutManagerState.floatValue2 : 11.0F;
                  float floatValue38 = flag4 ? hudLayoutManagerState.floatValue3 : (flag3 ? 8.0F : 11.0F);
                  float floatValue39 = Math.max(1.0F, floatValue22 - floatValue28 * 2.0F);
                  this.invoke(renderManager2, floatValue20, floatValue21, floatValue22, floatValue24, floatValue36, floatValue35);
                  if (flag3) {
                     if (flag6 || this.check9() || this.check10()) {
                        this.invoke(renderManager2, floatValue20 + floatValue28, floatValue21 + floatValue29, floatValue39, floatValue30, floatValue37, floatValue35);
                     } else if (flag4) {
                        renderManager2.invoke5(floatValue20 + floatValue28, floatValue21 + floatValue29, floatValue39, floatValue30, floatValue37, intValue3);
                     } else {
                        renderManager2.invoke6(floatValue20 + floatValue28, floatValue21 + floatValue29, floatValue39, floatValue30, 11.0F, 11.0F, 4.0F, 4.0F, intValue3);
                     }

                     float floatValue40 = measure5(floatValue6 * floatValue26, 18.0F, 24.0F);
                     float floatValue41 = floatValue20 + floatValue28 + floatValue39 - measure5(10.0F * floatValue25, 8.0F, 12.0F) - floatValue40;
                     float floatValue42 = floatValue21 + floatValue29 + (floatValue30 - floatValue40) / 2.0F;
                     if (flag4) {
                        float floatValue43 = floatValue20 + hudLayoutManagerState.hudLayoutManagerState3.floatValue * floatValue25;
                        float floatValue44 = floatValue21 + hudLayoutManagerState.hudLayoutManagerState3.floatValue2 * floatValue26;
                        renderManager2.invoke69(FontRegistry.fontObject4, floatValue43, floatValue44, hudLayoutManagerState.floatValue12 * floatValue27, text, intValue6);
                        float floatValue45 = hudLayoutManagerState.floatValue13 * floatValue27;
                        float floatValue46 = TextMeasureCache.resolve(FontRegistry.fontObject5, "e", floatValue45).floatValue;
                        float floatValue47 = (hudLayoutManagerState.hudLayoutManagerState32.flag ? floatValue20 + floatValue22 : floatValue20) + hudLayoutManagerState.hudLayoutManagerState32.floatValue * floatValue25;
                        float floatValue48 = floatValue21 + hudLayoutManagerState.hudLayoutManagerState32.floatValue2 * floatValue26;
                        renderManager2.invoke69(FontRegistry.fontObject5, floatValue47, floatValue48, floatValue45, "e", intValue7);
                     } else {
                        float floatValue49 = this.measure3(
                           FontRegistry.fontObject4, text, measure5(26.0F * floatValue27, 22.0F, 29.0F), Math.max(18.0F, floatValue39 - floatValue6 - 24.0F)
                        );
                        float floatValue50 = floatValue20 + floatValue28 + measure5(10.0F * floatValue25, 8.0F, 12.0F);
                        float floatValue51 = Math.max(1.0F, floatValue41 - floatValue50 - 4.0F);
                        renderManager2.invoke24(floatValue50, floatValue21 + floatValue29, floatValue51, floatValue30, 0.0F, 0.0F, 0.0F, 0.0F);
                        renderManager2.invoke69(FontRegistry.fontObject4, floatValue50, measure4(floatValue21 + floatValue29, floatValue30, floatValue49), floatValue49, text, intValue6);
                        renderManager2.invoke25();
                        float floatValue52 = measure5((floatValue2 + 4.0F) * floatValue27, 22.0F, 30.0F);
                        float floatValue53 = TextMeasureCache.resolve(FontRegistry.fontObject5, "e", floatValue52).floatValue;
                        renderManager2.invoke69(FontRegistry.fontObject5, floatValue41 + (floatValue40 - floatValue53) / 2.0F, measure4(floatValue42, floatValue40, floatValue52), floatValue52, "e", intValue7);
                     }
                  }

                  float floatValue54 = floatValue21 + floatValue29 + floatValue30 + floatValue31;
                  if (!flag3) {
                     floatValue54 = floatValue21 + floatValue29;
                  }

                  float floatValue55 = floatValue20 + floatValue28 + (flag4 ? hudLayoutManagerState.hudLayoutManagerState33.floatValue * floatValue25 : 0.0F);
                  floatValue54 += flag4 ? hudLayoutManagerState.hudLayoutManagerState33.floatValue2 * floatValue26 : 0.0F;
                  float floatValue56;
                  if (flag4) {
                     floatValue56 = floatValue12 * floatValue26;
                  } else {
                     float floatValue57 = Math.max(1.0F, floatValue24 - floatValue29 * 2.0F - floatValue30 - floatValue31);
                     floatValue56 = Math.min(Math.max(12.0F, ITEMS.size() * floatValue32 + 12.0F), floatValue57);
                  }

                  if (this.check5() || flag6 || this.check9() || this.check10()) {
                     if (flag6 || this.check9() || this.check10()) {
                        this.invoke2(renderManager2, floatValue55, floatValue54, floatValue39, floatValue56, floatValue38, floatValue35);
                     } else if (flag4) {
                        renderManager2.invoke5(floatValue55, floatValue54, floatValue39, floatValue56, floatValue38, intValue4);
                     } else {
                        renderManager2.invoke6(floatValue55, floatValue54, floatValue39, floatValue56, flag3 ? 4.0F : 11.0F, flag3 ? 4.0F : 11.0F, 11.0F, 11.0F, intValue4);
                     }
                  }

                  renderManager2.invoke24(floatValue20, floatValue21, floatValue22, floatValue24, floatValue36, floatValue36, floatValue36, floatValue36);
                  float floatValue58 = floatValue54 + Math.max(6.0F, (floatValue56 - ITEMS.size() * floatValue32) * 0.5F);
                  int intValue8 = ColorUtils.compute2(this.compute6(1.0F), intValue);

                  for (InformationHud.InformationHudDisplayEntry informationHudDisplayEntry2 : ITEMS) {
                     if (flag4) {
                        renderManager2.invoke69(FontRegistry.fontObject, floatValue55 + floatValue33, floatValue58 + floatValue32 / 2.0F + 3.0F * floatValue26, floatValue34, informationHudDisplayEntry2.label, intValue8);
                        int intValue9 = ColorUtils.compute29(informationHudDisplayEntry2.valColor, intValue);
                        float floatValue59 = floatValue55 + floatValue39 - floatValue33 - TextMeasureCache.resolve(FontRegistry.fontObject, informationHudDisplayEntry2.value, floatValue34).floatValue;
                        this.invoke5(
                           renderManager2,
                           informationHudDisplayEntry2.label,
                           informationHudDisplayEntry2.value,
                           FontRegistry.fontObject,
                           floatValue55,
                           floatValue58,
                           floatValue39,
                           floatValue32,
                           floatValue59,
                           floatValue58 + floatValue32 / 2.0F + 3.0F * floatValue26,
                           floatValue34,
                           intValue9
                        );
                     } else {
                        float floatValue60 = Math.max(1.0F, floatValue39 - floatValue33 * 2.0F);
                        float floatValue61 = this.measure2(informationHudDisplayEntry2, floatValue34, floatValue60, measure5(floatValue34 * 0.34F, 5.0F, 8.0F));
                        float floatValue62 = measure4(floatValue58, floatValue32, floatValue61);
                        float floatValue63 = floatValue20 + floatValue28 + floatValue33;
                        float floatValue64 = TextMeasureCache.resolve(FontRegistry.fontObject, informationHudDisplayEntry2.label, floatValue61).floatValue;
                        float floatValue65 = TextMeasureCache.resolve(FontRegistry.fontObject, informationHudDisplayEntry2.value, floatValue61).floatValue;
                        float floatValue66 = floatValue20 + floatValue28 + floatValue33 + floatValue60 - floatValue65;
                        float floatValue67 = floatValue63 + floatValue64 + measure5(floatValue61 * 0.34F, 5.0F, 8.0F);
                        if (floatValue66 < floatValue67) {
                           floatValue66 = floatValue67;
                        }

                        renderManager2.invoke24(floatValue63 - 1.0F, floatValue58, floatValue60 + 2.0F, floatValue32, 0.0F, 0.0F, 0.0F, 0.0F);
                        renderManager2.invoke69(FontRegistry.fontObject, floatValue63, floatValue62, floatValue61, informationHudDisplayEntry2.label, intValue8);
                        int intValue10 = ColorUtils.compute29(informationHudDisplayEntry2.valColor, intValue);
                        this.invoke5(
                           renderManager2,
                           informationHudDisplayEntry2.label,
                           informationHudDisplayEntry2.value,
                           FontRegistry.fontObject,
                           floatValue63 - 1.0F,
                           floatValue58,
                           floatValue60 + 2.0F,
                           floatValue32,
                           floatValue66,
                           floatValue62,
                           floatValue61,
                           intValue10
                        );
                        renderManager2.invoke25();
                     }

                     floatValue58 += floatValue32;
                  }

                  renderManager2.invoke25();
                  HudEditorRenderer.getINSTANCE().invoke6(hudEditorRendererState);
                  HudSettingsRenderer.invoke2(
                     renderManager2, this, hudEditorRendererState, HudEditorRenderer.getINSTANCE(), a_.getWindow().getScaledWidth(), a_.getWindow().getScaledHeight()
                  );
               }
            }
         }
      }
   }

   private void invoke3(RenderManager renderManager3, List<InformationHud.InformationHudDisplayEntry> list, float f) {
      float floatValue68 = 22.0F;
      float floatValue69 = 6.0F;
      float floatValue70 = 15.0F;
      float floatValue71 = 12.0F;
      float floatValue72 = 28.0F;
      float floatValue73 = 0.0F;

      for (int intValue11 = 0; intValue11 < list.size(); intValue11++) {
         InformationHud.InformationHudDisplayEntry informationHudDisplayEntry3 = (InformationHud.InformationHudDisplayEntry)list.get(intValue11);
         floatValue73 += TextMeasureCache.resolve(FontRegistry.fontObject, informationHudDisplayEntry3.label, floatValue68).floatValue
            + floatValue69
            + TextMeasureCache.resolve(FontRegistry.fontObject, informationHudDisplayEntry3.value, floatValue68).floatValue;
         if (intValue11 < list.size() - 1) {
            floatValue73 += floatValue70;
         }
      }

      float floatValue74 = floatValue73 + floatValue71 * 2.0F;
      ANIMATION_3.check();
      ANIMATION_4.check();
      ANIMATION_3.resolve4(floatValue74, 0.18F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
      ANIMATION_4.resolve4(floatValue72, 0.18F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
      float floatValue75 = ANIMATION_3.measure3();
      float floatValue76 = ANIMATION_4.measure3();
      HudEditorRenderer.HudEditorRendererState hudEditorRendererState2 = HudEditorRenderer.getINSTANCE().resolve("HUD_Info_Row", 10.0F, 10.0F, floatValue75, floatValue76);
      float floatValue77 = Math.max(1.0F, hudEditorRendererState2.floatValue3);
      float floatValue78 = Math.max(1.0F, hudEditorRendererState2.floatValue4);
      hudEditorRendererState2 = HudEditorRenderer.getINSTANCE().resolve3(hudEditorRendererState2, floatValue77, floatValue78, floatValue75, floatValue76);
      float floatValue79 = hudEditorRendererState2.floatValue + ANIMATION.measure3();
      float floatValue80 = hudEditorRendererState2.floatValue2;
      floatValue77 = hudEditorRendererState2.floatValue3;
      floatValue78 = hudEditorRendererState2.floatValue4;
      this.invoke3(floatValue79, floatValue80, floatValue77, floatValue78);
      float floatValue81 = measure5(floatValue77 / Math.max(1.0F, floatValue75), 0.6F, 3.5F);
      float floatValue82 = floatValue68 * floatValue81;
      float floatValue83 = floatValue69 * floatValue81;
      float floatValue84 = floatValue70 * floatValue81;
      float floatValue85 = floatValue71 * floatValue81;
      float floatValue86 = 0.0F;

      for (int intValue12 = 0; intValue12 < list.size(); intValue12++) {
         InformationHud.InformationHudDisplayEntry informationHudDisplayEntry4 = (InformationHud.InformationHudDisplayEntry)list.get(intValue12);
         floatValue86 += TextMeasureCache.resolve(FontRegistry.fontObject, informationHudDisplayEntry4.label, floatValue82).floatValue
            + floatValue83
            + TextMeasureCache.resolve(FontRegistry.fontObject, informationHudDisplayEntry4.value, floatValue82).floatValue;
         if (intValue12 < list.size() - 1) {
            floatValue86 += floatValue84;
         }
      }

      float floatValue87 = f * this.prozrachnost.getValue();
      float floatValue88 = measure5(floatValue78 * 0.32F, 8.0F, 16.0F);
      this.invoke(renderManager3, floatValue79, floatValue80, floatValue77, floatValue78, floatValue88, floatValue87);
      renderManager3.invoke24(floatValue79, floatValue80, floatValue77, floatValue78, floatValue88, floatValue88, floatValue88, floatValue88);
      float floatValue89 = floatValue79 + Math.max(floatValue85, (floatValue77 - floatValue86) * 0.5F);
      float floatValue90 = measure4(floatValue80, floatValue78, floatValue82);
      int intValue13 = ColorUtils.compute2(this.compute7(1.0F), (int)(255.0F * floatValue87));
      int intValue14 = (int)(255.0F * floatValue87);

      for (InformationHud.InformationHudDisplayEntry informationHudDisplayEntry5 : list) {
         float floatValue91 = TextMeasureCache.resolve(FontRegistry.fontObject, informationHudDisplayEntry5.label, floatValue82).floatValue;
         float floatValue92 = TextMeasureCache.resolve(FontRegistry.fontObject, informationHudDisplayEntry5.value, floatValue82).floatValue;
         renderManager3.invoke69(FontRegistry.fontObject, floatValue89, floatValue90, floatValue82, informationHudDisplayEntry5.label, intValue13);
         floatValue89 += floatValue91 + floatValue83;
         this.invoke5(
            renderManager3,
            informationHudDisplayEntry5.label,
            informationHudDisplayEntry5.value,
            FontRegistry.fontObject,
            floatValue79,
            floatValue80,
            floatValue77,
            floatValue78,
            floatValue89,
            floatValue90,
            floatValue82,
            ColorUtils.compute29(informationHudDisplayEntry5.valColor, intValue14)
         );
         floatValue89 += floatValue92 + floatValue84;
      }

      renderManager3.invoke25();
      HudEditorRenderer.getINSTANCE().invoke6(hudEditorRendererState2);
      HudSettingsRenderer.invoke2(renderManager3, this, hudEditorRendererState2, HudEditorRenderer.getINSTANCE(), a_.getWindow().getScaledWidth(), a_.getWindow().getScaledHeight());
   }

   private boolean check(float f, float g, float h, float i, List<InformationHud.InformationHudDisplayEntry> list, boolean bl) {
      float floatValue93 = f / Math.max(1.0F, g);
      float floatValue94 = f / Math.max(1.0F, h);
      float floatValue95 = this.measure(list, bl);
      return f >= Math.max(190.0F, floatValue95 * 0.86F) && floatValue93 >= 2.35F && floatValue94 >= 1.16F;
   }

   private float measure(List<InformationHud.InformationHudDisplayEntry> list, boolean bl) {
      float floatValue96 = 22.0F;
      float floatValue97 = bl ? TextMeasureCache.resolve(FontRegistry.fontObject4, "Information", 22.0F).floatValue + 36.0F : 0.0F;

      for (InformationHud.InformationHudDisplayEntry informationHudDisplayEntry6 : list) {
         floatValue97 += TextMeasureCache.resolve(FontRegistry.fontObject, informationHudDisplayEntry6.label, floatValue96).floatValue;
         floatValue97 += TextMeasureCache.resolve(FontRegistry.fontObject, informationHudDisplayEntry6.value, floatValue96).floatValue;
         floatValue97 += 26.0F;
      }

      return floatValue97 + 28.0F;
   }

   private void invoke4(
      RenderManager renderManager4, float f, float g, float h, float i, String string, boolean bl, List<InformationHud.InformationHudDisplayEntry> list, float j, int k, int l
   ) {
      float floatValue98 = measure5(i, 38.0F, 54.0F);
      float floatValue99 = g + (i - floatValue98) * 0.5F;
      float floatValue100 = measure5(floatValue98 * 0.28F, 10.0F, 14.0F);
      this.invoke(renderManager4, f, floatValue99, h, floatValue98, floatValue100, j);
      float floatValue101 = measure5(h * 0.033F, 13.0F, 22.0F);
      float floatValue102 = measure5(floatValue98 * 0.18F, 6.0F, 9.0F);
      float floatValue103 = f + floatValue101;
      float floatValue104 = floatValue99 + floatValue102;
      float floatValue105 = Math.max(1.0F, h - floatValue101 * 2.0F);
      float floatValue106 = Math.max(1.0F, floatValue98 - floatValue102 * 2.0F);
      float floatValue107 = measure5(floatValue106 * 0.82F, 21.0F, 30.0F);
      float floatValue108 = measure5(floatValue106 * 0.78F, 21.0F, 29.0F);
      renderManager4.invoke24(f, floatValue99, h, floatValue98, floatValue100, floatValue100, floatValue100, floatValue100);
      float floatValue109 = floatValue103;
      float floatValue110 = floatValue105;
      if (bl) {
         float floatValue111 = measure5(floatValue108 + 1.0F, 21.0F, 30.0F);
         float floatValue112 = TextMeasureCache.resolve(FontRegistry.fontObject4, string, floatValue108).floatValue;
         float floatValue113 = TextMeasureCache.resolve(FontRegistry.fontObject5, "e", floatValue111).floatValue;
         boolean flag7 = floatValue105 >= 300.0F;
         float floatValue114 = measure5(floatValue112 + (flag7 ? floatValue113 + 15.0F : 0.0F), 78.0F, Math.min(floatValue105 * 0.28F, 142.0F));
         renderManager4.invoke69(FontRegistry.fontObject4, floatValue103, measure4(floatValue104, floatValue106, floatValue108), floatValue108, string, k);
         if (flag7) {
            renderManager4.invoke69(FontRegistry.fontObject5, floatValue103 + floatValue114 - floatValue113, measure4(floatValue104, floatValue106, floatValue111), floatValue111, "e", l);
         }

         floatValue109 = floatValue103 + (floatValue114 + measure5(h * 0.018F, 8.0F, 16.0F));
         floatValue110 = Math.max(1.0F, floatValue103 + floatValue105 - floatValue109);
      }

      int intValue15 = Math.max(1, list.size());
      float floatValue115 = measure5(floatValue107 * 0.42F, 7.0F, 12.0F);
      float floatValue116 = floatValue110 / intValue15;
      int intValue16 = ColorUtils.compute2(this.compute7(1.0F), (int)(255.0F * j));

      for (int intValue17 = 0; intValue17 < list.size(); intValue17++) {
         InformationHud.InformationHudDisplayEntry informationHudDisplayEntry7 = (InformationHud.InformationHudDisplayEntry)list.get(intValue17);
         float floatValue117 = floatValue109 + floatValue116 * intValue17;
         float floatValue118 = this.measure2(informationHudDisplayEntry7, floatValue107, Math.max(20.0F, floatValue116 - 8.0F), floatValue115);
         float floatValue119 = TextMeasureCache.resolve(FontRegistry.fontObject, informationHudDisplayEntry7.label, floatValue118).floatValue;
         float floatValue120 = TextMeasureCache.resolve(FontRegistry.fontObject, informationHudDisplayEntry7.value, floatValue118).floatValue;
         float floatValue121 = floatValue119 + floatValue115 + floatValue120;
         float floatValue122 = floatValue117 + Math.max(3.0F, (floatValue116 - floatValue121) * 0.5F);
         float floatValue123 = measure4(floatValue104, floatValue106, floatValue118);
         int intValue18 = ColorUtils.compute29(informationHudDisplayEntry7.valColor, (int)(255.0F * j));
         renderManager4.invoke24(floatValue117 + 1.0F, floatValue99, Math.max(1.0F, floatValue116 - 2.0F), floatValue98, 0.0F, 0.0F, 0.0F, 0.0F);
         renderManager4.invoke69(FontRegistry.fontObject, floatValue122, floatValue123, floatValue118, informationHudDisplayEntry7.label, intValue16);
         this.invoke5(
            renderManager4,
            informationHudDisplayEntry7.label,
            informationHudDisplayEntry7.value,
            FontRegistry.fontObject,
            floatValue117 + 1.0F,
            floatValue99,
            Math.max(1.0F, floatValue116 - 2.0F),
            floatValue98,
            floatValue122 + floatValue119 + floatValue115,
            floatValue123,
            floatValue118,
            intValue18
         );
         renderManager4.invoke25();
      }

      renderManager4.invoke25();
   }

   private void invoke5(
      RenderManager renderManager5,
      String string,
      String string2,
      FontObject fontObject,
      float f,
      float g,
      float h,
      float i,
      float j,
      float k,
      float l,
      int m
   ) {
      if (!this.animatsiyaZnacheniy.isEnabled()) {
         renderManager5.invoke69(fontObject, j, k, l, string2, m);
      } else {
         HudMetricUtils hudMetricUtils = this.valuesByKey.get(string);
         if (hudMetricUtils == null) {
            hudMetricUtils = new HudMetricUtils();
            this.valuesByKey.put(string, hudMetricUtils);
         }

         hudMetricUtils.invoke(string2);
         hudMetricUtils.invoke4(renderManager5, fontObject, f, g, h, i, Math.min(i * 0.45F, 6.0F), j, k, l, m);
      }
   }

   private float measure2(InformationHud.InformationHudDisplayEntry informationHudDisplayEntry8, float f, float g, float h) {
      float floatValue124 = f;

      for (int intValue19 = 0; intValue19 < 8; intValue19++) {
         float floatValue125 = TextMeasureCache.resolve(FontRegistry.fontObject, informationHudDisplayEntry8.label, floatValue124).floatValue;
         float floatValue126 = TextMeasureCache.resolve(FontRegistry.fontObject, informationHudDisplayEntry8.value, floatValue124).floatValue;
         float floatValue127 = floatValue125 + h + floatValue126;
         if (floatValue127 <= g || floatValue124 <= 16.0F) {
            return floatValue124;
         }

         floatValue124 = Math.max(16.0F, floatValue124 * Math.max(0.72F, g / Math.max(1.0F, floatValue127)));
      }

      return floatValue124;
   }

   private float measure3(FontObject fontObject2, String string, float f, float g) {
      float floatValue128 = f;

      for (int intValue20 = 0; intValue20 < 8; intValue20++) {
         float floatValue129 = TextMeasureCache.resolve(fontObject2, string, floatValue128).floatValue;
         if (floatValue129 <= g || floatValue128 <= 16.0F) {
            return floatValue128;
         }

         floatValue128 = Math.max(16.0F, floatValue128 * Math.max(0.72F, g / Math.max(1.0F, floatValue129)));
      }

      return floatValue128;
   }

   private static float measure4(float f, float g, float h) {
      return f + g * 0.5F + h * 0.18F;
   }

   private static float measure5(float f, float g, float h) {
      return Math.max(g, Math.min(h, f));
   }

   private static String resolveSelf() {
      double doubleValue = 0.0;
      if (a_.player != null) {
         double doubleValue2 = a_.player.getX() - a_.player.lastX;
         double doubleValue3 = a_.player.getZ() - a_.player.lastZ;
         doubleValue = Math.sqrt(doubleValue2 * doubleValue2 + doubleValue3 * doubleValue3) * 20.0;
      }

      doubleValue = doubleValue + (doubleValue - doubleValue) * 0.1;
      return resolve5(doubleValue);
   }

   private static String resolve2() {
      if (a_.player != null) {
         int intValue21 = (int)a_.player.getX();
         int intValue22 = (int)a_.player.getY();
         int intValue23 = (int)a_.player.getZ();
         return intValue21 + " " + intValue22 + " " + intValue23;
      } else {
         return "0 0 0";
      }
   }

   private static String resolve3() {
      if (a_.player != null && a_.getNetworkHandler() != null) {
         PlayerListEntry playerListEntry = a_.getNetworkHandler().getPlayerListEntry(a_.player.getUuid());
         return playerListEntry == null ? "0 ms" : playerListEntry.getLatency() + " ms";
      } else {
         return "0 ms";
      }
   }

   private static String resolve4(double d) {
      int intValue24 = (int)Math.round(d * 10.0);
      int intValue25 = intValue24 / 10;
      int intValue26 = Math.abs(intValue24 % 10);
      return intValue25 + "." + intValue26;
   }

   private static String resolve5(double d) {
      int intValue27 = (int)Math.round(d * 100.0);
      int intValue28 = intValue27 / 100;
      int intValue29 = Math.abs(intValue27 % 100);
      return intValue28 + (intValue29 < 10 ? ".0" : ".") + intValue29;
   }

   record InformationHudDisplayEntry(String label, String value, int valColor) {
   }
}
