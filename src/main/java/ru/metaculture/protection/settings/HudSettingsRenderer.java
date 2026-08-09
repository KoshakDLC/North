package ru.metaculture.protection;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.MinecraftClient;

public final class HudSettingsRenderer {
   private static final Map<String, Animation> VALUES_BY_KEY = new HashMap<>();

   private static float measure(RenderManager renderManager, ConfigurableHudElement configurableHudElement, String string, String string2) {
      float floatValue = 5.0F;
      float floatValue2 = 32.0F;
      float floatValue3 = 4.0F;
      float floatValue4 = floatValue2 - floatValue3 * 2.0F;
      float floatValue5 = floatValue + floatValue3 + floatValue4 + 8.0F;
      floatValue5 += TextMeasureCache.resolve(BrandMark.font(), BrandMark.GLYPH, 14.0F).floatValue + 3.0F;
      floatValue5 += TextMeasureCache.resolve(FontRegistry.fontObject, "t.me/soezproject", 22.0F).floatValue + 4.0F;
      floatValue5 += TextMeasureCache.resolve(FontRegistry.fontObject8, "k", 14.0F).floatValue + 4.0F;
      FontObject iconFont = BrandMark.GLYPH.equals(string2) ? BrandMark.font() : FontRegistry.fontObject8;
      floatValue5 += TextMeasureCache.resolve(iconFont, string2, 18.0F).floatValue + 4.0F;
      floatValue5 += TextMeasureCache.resolve(FontRegistry.fontObject4, string, 22.0F).floatValue;
      floatValue5 += 10.0F + floatValue;
      return Math.max(210.0F, floatValue5);
   }

   public static HudSettingsRenderer.HudSettingsRendererBounds resolve(RenderManager renderManager2, ConfigurableHudElement configurableHudElement2, float f, float g, float h, float i) {
      if (configurableHudElement2.resolve().isEmpty()) {
         return new HudSettingsRenderer.HudSettingsRendererBounds(f, g, 0.0F, 0.0F);
      } else {
         HudElementInfo hudElementInfo = configurableHudElement2.getClass().getAnnotation(HudElementInfo.class);
         String text = hudElementInfo != null ? hudElementInfo.resolve() : "Settings";
         String text2 = hudElementInfo != null && !hudElementInfo.resolve2().isEmpty() ? hudElementInfo.resolve2() : "e";
         float floatValue6 = measure(renderManager2, configurableHudElement2, text, text2);
         float floatValue7 = 5.0F;
         float floatValue8 = 32.0F;
         float floatValue9 = 8.0F;
         float floatValue10 = 4.0F;
         float floatValue11 = 20.0F;
         float floatValue12 = 28.0F;
         float floatValue13 = 20.0F;
         float floatValue14 = 20.0F;
         float floatValue15 = floatValue9 * 2.0F;

         for (Setting setting : configurableHudElement2.resolve()) {
            if (check(setting)) {
               if (setting instanceof BooleanSetting) {
                  floatValue15 += floatValue11;
               } else if (setting instanceof NumberSetting) {
                  floatValue15 += floatValue12;
               } else if (setting instanceof ModeSetting) {
                  floatValue15 += floatValue13;
               } else if (setting instanceof ButtonSetting) {
                  floatValue15 += floatValue14;
               } else if (setting instanceof GroupSetting groupSetting) {
                  floatValue15 += floatValue11 + compute(groupSetting) * floatValue11 * groupSetting.animation.measure3();
               }
            }
         }

         float floatValue16 = floatValue7 + floatValue8 + floatValue10 + floatValue15 + floatValue7;
         MinecraftClient client = MinecraftClient.getInstance();
         float floatValue17 = client.getWindow().getFramebufferWidth();
         float floatValue18 = client.getWindow().getFramebufferHeight();
         float floatValue19 = 10.0F;
         boolean flag = f + h + floatValue19 + floatValue6 > floatValue17;
         boolean flag2 = g + floatValue16 + floatValue19 > floatValue18;
         float floatValue20 = flag ? f - floatValue6 - floatValue19 : f + h + floatValue19;
         if (floatValue20 + floatValue6 > floatValue17) {
            floatValue20 = floatValue17 - floatValue6 - floatValue19;
         }

         if (floatValue20 < floatValue19) {
            floatValue20 = floatValue19;
         }

         float floatValue21 = flag2 ? g + i - floatValue16 : g;
         if (floatValue21 + floatValue16 > floatValue18) {
            floatValue21 = floatValue18 - floatValue16 - floatValue19;
         }

         if (floatValue21 < floatValue19) {
            floatValue21 = floatValue19;
         }

         return new HudSettingsRenderer.HudSettingsRendererBounds(floatValue20, floatValue21, floatValue6, floatValue16);
      }
   }

   public static void invoke(
      RenderManager renderManager3,
      ConfigurableHudElement configurableHudElement3,
      float f,
      float g,
      float h,
      float i,
      float j,
      float k,
      float l,
      float m,
      float n,
      boolean bl,
      boolean bl2
   ) {
      if (!configurableHudElement3.resolve().isEmpty() && !(l <= 0.01F)) {
         HudElementInfo hudElementInfo2 = configurableHudElement3.getClass().getAnnotation(HudElementInfo.class);
         String text3 = hudElementInfo2 != null ? hudElementInfo2.resolve() : "Settings";
         String text4 = hudElementInfo2 != null && !hudElementInfo2.resolve2().isEmpty() ? hudElementInfo2.resolve2() : "e";
         float floatValue22 = measure(renderManager3, configurableHudElement3, text3, text4);
         float floatValue23 = 5.0F;
         float floatValue24 = 32.0F;
         float floatValue25 = 4.0F;
         float floatValue26 = floatValue24 - floatValue25 * 2.0F;
         float floatValue27 = 8.0F;
         float floatValue28 = 4.0F;
         float floatValue29 = 20.0F;
         float floatValue30 = 28.0F;
         float floatValue31 = 20.0F;
         float floatValue32 = 20.0F;
         float floatValue33 = floatValue27 * 2.0F;

         for (Setting setting2 : configurableHudElement3.resolve()) {
            if (check(setting2)) {
               if (setting2 instanceof BooleanSetting) {
                  floatValue33 += floatValue29;
               } else if (setting2 instanceof NumberSetting) {
                  floatValue33 += floatValue30;
               } else if (setting2 instanceof ModeSetting) {
                  floatValue33 += floatValue31;
               } else if (setting2 instanceof ButtonSetting) {
                  floatValue33 += floatValue32;
               } else if (setting2 instanceof GroupSetting groupSetting2) {
                  groupSetting2.animation.check();
                  groupSetting2.animation.resolve4(groupSetting2.expanded ? 1.0 : 0.0, 0.2F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
                  floatValue33 += floatValue29 + compute(groupSetting2) * floatValue29 * groupSetting2.animation.measure3();
               }
            }
         }

         float floatValue34 = floatValue23 + floatValue24 + floatValue28 + floatValue33 + floatValue23;
         MinecraftClient client2 = MinecraftClient.getInstance();
         float floatValue35 = client2.getWindow().getFramebufferWidth();
         float floatValue36 = client2.getWindow().getFramebufferHeight();
         float floatValue37 = 10.0F;
         boolean flag3 = f + h + floatValue37 + floatValue22 > floatValue35;
         boolean flag4 = g + floatValue34 + floatValue37 > floatValue36;
         float floatValue38 = flag3 ? f - floatValue22 - floatValue37 : f + h + floatValue37;
         if (floatValue38 + floatValue22 > floatValue35) {
            floatValue38 = floatValue35 - floatValue22 - floatValue37;
         }

         if (floatValue38 < floatValue37) {
            floatValue38 = floatValue37;
         }

         float floatValue39 = flag4 ? g + i - floatValue34 : g;
         if (floatValue39 + floatValue34 > floatValue36) {
            floatValue39 = floatValue36 - floatValue34 - floatValue37;
         }

         if (floatValue39 < floatValue37) {
            floatValue39 = floatValue37;
         }

         float floatValue40 = (1.0F - l) * 10.0F;
         float floatValue41 = floatValue38 + (flag3 ? floatValue40 : -floatValue40);
         float floatValue42 = (1.0F - l) * 10.0F;
         float floatValue43 = floatValue39 + (flag4 ? floatValue42 : -floatValue42);
         int intValue = (int)(255.0F * l);
         int intValue2 = ColorUtils.compute43(10, 10, 10, (int)(40.0F * l));
         int intValue3 = ColorUtils.compute43(28, 30, 30, (int)(140.0F * l));
         int intValue4 = ColorUtils.compute43(255, 255, 255, (int)(10.0F * l));
         int intValue5 = configurableHudElement3 instanceof HudElement hudElement
            ? hudElement.compute9(l)
            : ColorUtils.compute29(RenderManager.RenderManagerState.compute6(255, 255), intValue);
         int intValue6 = ColorUtils.compute43(255, 255, 255, intValue);
         int intValue7 = ColorUtils.compute43(255, 255, 255, (int)(122.0F * l));
         int intValue8 = ColorUtils.compute43(255, 255, 255, (int)(120.0F * l));
         boolean flag5 = check2(configurableHudElement3);
         NeumorphismRenderer.NeumorphismRendererData2 neumorphismRendererData2 = resolve2(configurableHudElement3);
         if (flag5) {
            intValue2 = NeumorphismRenderer.compute(l);
            intValue3 = NeumorphismRenderer.compute(l);
            intValue4 = ColorUtils.compute43(0, 0, 0, 0);
            intValue6 = NeumorphismRenderer.compute2(l);
            intValue7 = NeumorphismRenderer.compute3(l);
            intValue8 = NeumorphismRenderer.compute3(l);
         }

         renderManager3.invoke65(l);
         float floatValue44 = 14.0F;
         if (!flag5 || !NeumorphismRenderer.check11(null, floatValue41, floatValue43, floatValue22, floatValue34, floatValue44, neumorphismRendererData2.distance(), neumorphismRendererData2.blur(), neumorphismRendererData2.intensity(), 1, false, l)) {
            renderManager3.invoke48(23.0F);
            renderManager3.invoke44(floatValue41, floatValue43, floatValue22, floatValue34, floatValue44, l);
            renderManager3.invoke5(floatValue41, floatValue43, floatValue22, floatValue34, floatValue44, intValue2);
            renderManager3.invoke28(floatValue41, floatValue43, floatValue22, floatValue34, floatValue44, intValue4, 1.0F);
         }

         float floatValue45 = floatValue41 + floatValue23;
         float floatValue46 = floatValue43 + floatValue23;
         float floatValue47 = floatValue22 - floatValue23 * 2.0F;
         if (!flag5 || !NeumorphismRenderer.check11(null, floatValue45, floatValue46, floatValue47, floatValue24, 11.0F, neumorphismRendererData2.distance(), neumorphismRendererData2.blur(), neumorphismRendererData2.intensity(), 1, false, l)) {
            renderManager3.invoke6(floatValue45, floatValue46, floatValue47, floatValue24, 11.0F, 11.0F, 4.0F, 4.0F, intValue3);
         }

         float floatValue48 = floatValue45 + floatValue25;
         float floatValue49 = floatValue46 + floatValue25;
         renderManager3.invoke5(floatValue48, floatValue49, floatValue26, floatValue26, 7.0F, intValue5);
         float floatValue50 = 28.0F;
         float floatValue51 = TextMeasureCache.resolve(FontRegistry.fontObject8, "o", floatValue50).floatValue;
         renderManager3.invoke69(
            FontRegistry.fontObject8,
            floatValue48 + (floatValue26 - floatValue51) / 2.0F,
            floatValue49 + floatValue26 / 2.0F + 6.0F,
            floatValue50,
            "o",
            ColorUtils.compute43(255, 255, 255, intValue)
         );
         float floatValue52 = floatValue48 + floatValue26 + 8.0F;
         float floatValue53 = floatValue46 + floatValue24 / 2.0F + 4.5F;
         renderManager3.invoke69(BrandMark.font(), floatValue52, floatValue53 - 0.5F, 14.0F, BrandMark.GLYPH, intValue8);
         floatValue52 += TextMeasureCache.resolve(BrandMark.font(), BrandMark.GLYPH, 14.0F).floatValue + 3.0F;
         renderManager3.invoke69(FontRegistry.fontObject, floatValue52, floatValue53, 22.0F, "t.me/soezproject", intValue7);
         floatValue52 += TextMeasureCache.resolve(FontRegistry.fontObject, "t.me/soezproject", 22.0F).floatValue + 4.0F;
         renderManager3.invoke69(FontRegistry.fontObject8, floatValue52, floatValue53 - 0.5F, 12.0F, "k", intValue8);
         floatValue52 += TextMeasureCache.resolve(FontRegistry.fontObject8, "k", 12.0F).floatValue + 4.0F;
         FontObject elementIconFont = BrandMark.GLYPH.equals(text4) ? BrandMark.font() : FontRegistry.fontObject8;
         renderManager3.invoke69(elementIconFont, floatValue52, floatValue53, 18.0F, text4, intValue5);
         floatValue52 += TextMeasureCache.resolve(elementIconFont, text4, 18.0F).floatValue + 4.0F;
         renderManager3.invoke69(FontRegistry.fontObject4, floatValue52, floatValue53, 22.0F, text3, intValue6);
         float floatValue54 = floatValue41 + floatValue23;
         float floatValue55 = floatValue46 + floatValue24 + floatValue28;
         float floatValue56 = floatValue22 - floatValue23 * 2.0F;
         if (!flag5 || !NeumorphismRenderer.check11(null, floatValue54, floatValue55, floatValue56, floatValue33, 9.0F, neumorphismRendererData2.distance(), neumorphismRendererData2.blur(), neumorphismRendererData2.intensity(), 2, true, l)) {
            renderManager3.invoke6(floatValue54, floatValue55, floatValue56, floatValue33, 4.0F, 4.0F, 11.0F, 11.0F, intValue3);
         }

         float floatValue57 = 1.5F;
         renderManager3.invoke5(floatValue54 + floatValue27, floatValue55 + floatValue27, floatValue57, floatValue33 - floatValue27 * 2.0F, 0.5F, intValue5);
         float floatValue58 = floatValue55 + floatValue27;
         float floatValue59 = floatValue54 + floatValue27 + floatValue57 + 6.5F;
         float floatValue60 = floatValue56 - (floatValue59 - floatValue54) - floatValue27;
         float floatValue61 = 22.0F;
         float floatValue62 = 20.0F;
         float floatValue63 = 5.0F;
         renderManager3.invoke24(floatValue54, floatValue55, floatValue56, floatValue33, 4.0F, 4.0F, 11.0F, 11.0F);

         for (Setting setting3 : configurableHudElement3.resolve()) {
            if (check(setting3)) {
               if (setting3 instanceof BooleanSetting booleanSetting) {
                  float floatValue64 = 12.0F;
                  float floatValue65 = floatValue59 + floatValue60 - floatValue64;
                  float floatValue66 = floatValue58 + (floatValue29 - floatValue64) / 2.0F;
                  float floatValue67 = floatValue60 - floatValue64 - 6.0F;
                  invoke3(renderManager3, FontRegistry.fontObject, booleanSetting.name, floatValue59, floatValue58 + floatValue29 / 2.0F + floatValue63, floatValue61, intValue6, floatValue58, floatValue29, floatValue67);
                  booleanSetting.animation.check();
                  booleanSetting.animation.resolve4(booleanSetting.isEnabled() ? 1.0 : 0.0, 0.15F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
                  int intValue9 = ColorUtils.compute43(255, 255, 255, (int)(10.0F * l));
                  boolean flag6 = bl2 && check3(m, n, floatValue59, floatValue58, floatValue60, floatValue29);
                  if (!flag5
                     || !NeumorphismRenderer.check11(
                        null, floatValue65, floatValue66, floatValue64, floatValue64, 3.0F, neumorphismRendererData2.distance(), neumorphismRendererData2.blur(), neumorphismRendererData2.intensity(), flag6 ? 2 : 1, flag6, l
                     )) {
                     renderManager3.invoke5(floatValue65, floatValue66, floatValue64, floatValue64, 3.0F, intValue9);
                  }

                  if (booleanSetting.isEnabled()) {
                     float floatValue68 = TextMeasureCache.resolve(FontRegistry.fontObject8, "j", 10.0F).floatValue;
                     renderManager3.invoke69(FontRegistry.fontObject8, floatValue65 + (floatValue64 - floatValue68) / 2.0F, floatValue66 + floatValue64 / 2.0F + 3.0F, 10.0F, "j", intValue7);
                  }

                  if (bl && check3(m, n, floatValue59, floatValue58, floatValue60, floatValue29)) {
                     booleanSetting.setValue(!booleanSetting.isEnabled());
                     HudPresetManager.invoke5();
                  }

                  floatValue58 += floatValue29;
               } else if (setting3 instanceof NumberSetting numberSetting) {
                  String text5 = resolve4(numberSetting.getValue());
                  float floatValue69 = TextMeasureCache.resolve(FontRegistry.fontObject, text5, floatValue62).floatValue;
                  float floatValue70 = floatValue60 - floatValue69 - 6.0F;
                  invoke3(renderManager3, FontRegistry.fontObject, numberSetting.name, floatValue59, floatValue58 + 13.0F, floatValue61, intValue6, floatValue58, floatValue30, floatValue70);
                  renderManager3.invoke69(FontRegistry.fontObject, floatValue59 + floatValue60 - floatValue69, floatValue58 + 13.0F, floatValue62, text5, intValue5);
                  float floatValue71 = floatValue58 + floatValue30 - 5.0F;
                  boolean flag7 = bl2 && check3(m, n, floatValue59 - 4.0F, floatValue71 - 6.0F, floatValue60 + 8.0F, 16.0F);
                  if (flag5) {
                     NeumorphismRenderer.check11(null, floatValue59, floatValue71 - 2.0F, floatValue60, 7.0F, 3.5F, neumorphismRendererData2.distance(), neumorphismRendererData2.blur(), neumorphismRendererData2.intensity(), 2, true, l);
                  } else {
                     renderManager3.invoke5(floatValue59, floatValue71, floatValue60, 3.0F, 1.5F, ColorUtils.compute43(255, 255, 255, (int)(10.0F * l)));
                  }

                  Animation animation = VALUES_BY_KEY.computeIfAbsent(text3 + "_" + numberSetting.name, string -> new Animation());
                  animation.check();
                  float floatValue72 = (numberSetting.getValue() - numberSetting.minimum) / (numberSetting.maximum - numberSetting.minimum);
                  animation.resolve4(floatValue72, 0.2F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
                  float floatValue73 = animation.measure3();
                  renderManager3.invoke5(floatValue59, floatValue71, floatValue60 * floatValue73, 3.0F, 1.5F, intValue5);
                  float floatValue74 = 8.0F;
                  float floatValue75 = 10.0F;
                  float floatValue76 = floatValue59 + floatValue60 * floatValue73 - floatValue74 / 2.0F;
                  if (!flag5
                     || !NeumorphismRenderer.check11(
                        null,
                        floatValue76,
                        floatValue71 - (floatValue75 - 3.0F) / 2.0F,
                        floatValue74,
                        floatValue75,
                        2.0F,
                        neumorphismRendererData2.distance(),
                        neumorphismRendererData2.blur(),
                        neumorphismRendererData2.intensity(),
                        flag7 ? 2 : 1,
                        flag7,
                        l
                     )) {
                     renderManager3.invoke5(floatValue76, floatValue71 - (floatValue75 - 3.0F) / 2.0F, floatValue74, floatValue75, 2.0F, ColorUtils.compute43(255, 255, 255, intValue));
                  }

                  if (!flag5) {
                     renderManager3.invoke5(
                        floatValue76 + 2.5F, floatValue71 - (floatValue75 - 3.0F) / 2.0F + 2.5F, 1.0F, 5.0F, 0.5F, ColorUtils.compute43(100, 100, 100, intValue)
                     );
                     renderManager3.invoke5(
                        floatValue76 + 4.5F, floatValue71 - (floatValue75 - 3.0F) / 2.0F + 2.5F, 1.0F, 5.0F, 0.5F, ColorUtils.compute43(100, 100, 100, intValue)
                     );
                  }

                  if (flag7) {
                     float floatValue77 = numberSetting.minimum + (m - floatValue59) / floatValue60 * (numberSetting.maximum - numberSetting.minimum);
                     floatValue77 = Math.max(numberSetting.minimum, Math.min(numberSetting.maximum, floatValue77));
                     floatValue77 = (float)(Math.round(floatValue77 * (1.0 / numberSetting.step)) / (1.0 / numberSetting.step));
                     numberSetting.invoke(floatValue77);
                     HudPresetManager.invoke5();
                  }

                  floatValue58 += floatValue30;
               } else if (setting3 instanceof ModeSetting modeSetting) {
                  String text6 = modeSetting.getValue();
                  float floatValue78 = TextMeasureCache.resolve(FontRegistry.fontObject, text6, floatValue62).floatValue;
                  float floatValue79 = floatValue60 - floatValue78 - 6.0F;
                  invoke3(renderManager3, FontRegistry.fontObject, modeSetting.name, floatValue59, floatValue58 + floatValue31 / 2.0F + floatValue63, floatValue61, intValue6, floatValue58, floatValue31, floatValue79);
                  float floatValue80 = floatValue59 + floatValue60 - floatValue78;
                  renderManager3.invoke69(FontRegistry.fontObject, floatValue80, floatValue58 + floatValue31 / 2.0F + floatValue63, floatValue62, text6, intValue7);
                  if (bl && check3(m, n, floatValue59, floatValue58, floatValue60, floatValue31)) {
                     modeSetting.selectedIndex = (modeSetting.selectedIndex + 1) % modeSetting.options.size();
                     modeSetting.value = modeSetting.options.get(modeSetting.selectedIndex);
                     HudPresetManager.invoke5();
                  }

                  floatValue58 += floatValue31;
               } else if (setting3 instanceof ButtonSetting buttonSetting) {
                  String text7 = buttonSetting.getRun();
                  float floatValue81 = TextMeasureCache.resolve(FontRegistry.fontObject, text7, floatValue62).floatValue;
                  float floatValue82 = Math.max(70.0F, floatValue81 + 18.0F);
                  floatValue82 = Math.min(floatValue82, floatValue60 * 0.55F);
                  float floatValue83 = 16.0F;
                  float floatValue84 = floatValue59 + floatValue60 - floatValue82;
                  float floatValue85 = floatValue58 + (floatValue32 - floatValue83) / 2.0F;
                  float floatValue86 = floatValue84 - floatValue59 - 6.0F;
                  invoke3(renderManager3, FontRegistry.fontObject, buttonSetting.name, floatValue59, floatValue58 + floatValue32 / 2.0F + floatValue63, floatValue61, intValue6, floatValue58, floatValue32, floatValue86);
                  boolean flag8 = bl2 && check3(m, n, floatValue84, floatValue85, floatValue82, floatValue83);
                  if (!flag5
                     || !NeumorphismRenderer.check11(
                        null, floatValue84, floatValue85, floatValue82, floatValue83, 4.0F, neumorphismRendererData2.distance(), neumorphismRendererData2.blur(), neumorphismRendererData2.intensity(), flag8 ? 2 : 1, flag8, l
                     )) {
                     renderManager3.invoke5(floatValue84, floatValue85, floatValue82, floatValue83, 4.0F, ColorUtils.compute43(255, 255, 255, (int)(14.0F * l)));
                     renderManager3.invoke28(floatValue84, floatValue85, floatValue82, floatValue83, 4.0F, intValue4, 0.6F);
                  }

                  String text8 = resolve3(text7, FontRegistry.fontObject, floatValue62, floatValue82 - 8.0F);
                  float floatValue87 = TextMeasureCache.resolve(FontRegistry.fontObject, text8, floatValue62).floatValue;
                  renderManager3.invoke69(FontRegistry.fontObject, floatValue84 + (floatValue82 - floatValue87) / 2.0F, floatValue85 + floatValue83 / 2.0F + floatValue63, floatValue62, text8, intValue7);
                  if (bl && check3(m, n, floatValue84, floatValue85, floatValue82, floatValue83)) {
                     buttonSetting.invoke8();
                     HudPresetManager.invoke5();
                  }

                  floatValue58 += floatValue32;
               } else if (setting3 instanceof GroupSetting groupSetting3) {
                  float floatValue88 = groupSetting3.animation.measure3();
                  float floatValue89 = 26.0F;
                  float floatValue90 = TextMeasureCache.resolve(FontRegistry.fontObject6, "m", floatValue89).floatValue;
                  float floatValue91 = floatValue60 - floatValue90 - 6.0F;
                  invoke3(renderManager3, FontRegistry.fontObject, groupSetting3.name, floatValue59, floatValue58 + floatValue29 / 2.0F + floatValue63, floatValue61, intValue6, floatValue58, floatValue29, floatValue91);
                  float floatValue92 = floatValue59 + floatValue60 - floatValue90 / 2.0F;
                  float floatValue93 = floatValue58 + floatValue29 / 2.0F;
                  renderManager3.invoke56(floatValue92, floatValue93);
                  renderManager3.invoke69(FontRegistry.fontObject6, -floatValue90 / 2.0F, floatValue89 / 3.0F, floatValue89, "m", intValue7);
                  renderManager3.invoke57();
                  if (bl && check3(m, n, floatValue59, floatValue58, floatValue60, floatValue29)) {
                     groupSetting3.expanded = !groupSetting3.expanded;
                  }

                  floatValue58 += floatValue29;
                  if (floatValue88 > 0.001F) {
                     float floatValue94 = compute(groupSetting3) * floatValue29;
                     float floatValue95 = floatValue94 * floatValue88;
                     renderManager3.invoke24(floatValue54, floatValue58, floatValue56, floatValue95, 0.0F, 0.0F, 0.0F, 0.0F);
                     float floatValue96 = floatValue58;
                     float floatValue97 = floatValue58 + floatValue95;
                     float floatValue98 = floatValue58 - floatValue94 * (1.0F - floatValue88);

                     for (BooleanSetting booleanSetting2 : groupSetting3.options) {
                        if (check(booleanSetting2)) {
                           float floatValue99 = 12.0F;
                           float floatValue100 = floatValue59 + floatValue60 - floatValue99;
                           float floatValue101 = floatValue98 + (floatValue29 - floatValue99) / 2.0F;
                           float floatValue102 = floatValue60 - 10.0F - floatValue99 - 6.0F;
                           invoke3(
                              renderManager3,
                              FontRegistry.fontObject,
                              booleanSetting2.name,
                              floatValue59 + 10.0F,
                              floatValue98 + floatValue29 / 2.0F + floatValue63,
                              floatValue62,
                              intValue7,
                              floatValue98,
                              floatValue29,
                              floatValue102
                           );
                           booleanSetting2.animation.check();
                           booleanSetting2.animation.resolve4(booleanSetting2.isEnabled() ? 1.0 : 0.0, 0.15F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
                           int intValue10 = ColorUtils.compute43(255, 255, 255, (int)(10.0F * l));
                           boolean flag9 = bl2 && check3(m, n, floatValue59, floatValue98, floatValue60, floatValue29);
                           boolean flag10 = floatValue101 >= floatValue96 && floatValue101 + floatValue99 <= floatValue97;
                           if (!flag5
                              || !flag10
                              || !NeumorphismRenderer.check11(
                                 null, floatValue100, floatValue101, floatValue99, floatValue99, 3.0F, neumorphismRendererData2.distance(), neumorphismRendererData2.blur(), neumorphismRendererData2.intensity(), flag9 ? 2 : 1, flag9, l * floatValue88
                              )) {
                              renderManager3.invoke5(floatValue100, floatValue101, floatValue99, floatValue99, 3.0F, intValue10);
                           }

                           if (booleanSetting2.isEnabled()) {
                              float floatValue103 = TextMeasureCache.resolve(FontRegistry.fontObject8, "j", 10.0F).floatValue;
                              renderManager3.invoke69(
                                 FontRegistry.fontObject8, floatValue100 + (floatValue99 - floatValue103) / 2.0F, floatValue101 + floatValue99 / 2.0F + 3.0F, 10.0F, "j", intValue7
                              );
                           }

                           if (groupSetting3.expanded && bl && check3(m, n, floatValue59, floatValue98, floatValue60, floatValue29)) {
                              booleanSetting2.setValue(!booleanSetting2.isEnabled());
                              HudPresetManager.invoke5();
                           }

                           floatValue98 += floatValue29;
                        }
                     }

                     renderManager3.invoke25();
                     floatValue58 += floatValue95;
                  }
               }
            }
         }

         renderManager3.invoke25();
         renderManager3.invoke66();
      }
   }

   public static void invoke2(
      RenderManager renderManager4, ConfigurableHudElement configurableHudElement4, HudEditorRenderer.HudEditorRendererState hudEditorRendererState, HudEditorRenderer hudEditorRenderer, float f, float g
   ) {
      invoke(
         renderManager4,
         configurableHudElement4,
         hudEditorRendererState.floatValue,
         hudEditorRendererState.floatValue2,
         hudEditorRendererState.floatValue3,
         hudEditorRendererState.floatValue4,
         f,
         g,
         hudEditorRendererState.floatValue6,
         hudEditorRenderer.getFloatValue(),
         hudEditorRenderer.getFloatValue2(),
         hudEditorRenderer.isFlag4(),
         hudEditorRenderer.isFlag3()
      );
   }

   private static boolean check(Setting setting4) {
      try {
         return setting4 == null || setting4.visibilityCondition == null || !setting4.visibilityCondition.get();
      } catch (Throwable exception) {
         return true;
      }
   }

   private static int compute(GroupSetting groupSetting4) {
      int intValue11 = 0;

      for (BooleanSetting booleanSetting3 : groupSetting4.options) {
         if (check(booleanSetting3)) {
            intValue11++;
         }
      }

      return intValue11;
   }

   private static boolean check2(ConfigurableHudElement configurableHudElement5) {
      for (Setting setting5 : configurableHudElement5.resolve()) {
         if (setting5 instanceof ModeSetting modeSetting2 && modeSetting2.name.equals("Стилистика")) {
            return HudElement.check11(modeSetting2.getValue());
         }
      }

      return false;
   }

   private static NeumorphismRenderer.NeumorphismRendererData2 resolve2(ConfigurableHudElement configurableHudElement6) {
      float floatValue104 = 5.5F;
      float floatValue105 = 18.0F;
      float floatValue106 = 0.72F;
      String text9 = "Выпуклая";

      for (Setting setting6 : configurableHudElement6.resolve()) {
         if (setting6 instanceof NumberSetting numberSetting2) {
            if (numberSetting2.name.equals("Нео дистанция")) {
               floatValue104 = numberSetting2.getValue();
            } else if (numberSetting2.name.equals("Нео размытие")) {
               floatValue105 = numberSetting2.getValue();
            } else if (numberSetting2.name.equals("Нео интенсивность")) {
               floatValue106 = numberSetting2.getValue();
            }
         } else if (setting6 instanceof ModeSetting modeSetting3 && modeSetting3.name.equals("Нео форма")) {
            text9 = modeSetting3.getValue();
         }
      }

      return NeumorphismRenderer.resolve3(floatValue104, floatValue105, floatValue106, text9);
   }

   private static String resolve3(String string, FontObject fontObject, float f, float g) {
      if (string == null || g <= 0.0F) {
         return "";
      } else if (TextMeasureCache.resolve(fontObject, string, f).floatValue <= g) {
         return string;
      } else {
         String text10 = "...";
         float floatValue107 = TextMeasureCache.resolve(fontObject, text10, f).floatValue;
         if (floatValue107 > g) {
            return "";
         } else {
            int intValue12 = string.length();

            while (intValue12 > 0 && TextMeasureCache.resolve(fontObject, string.substring(0, intValue12), f).floatValue + floatValue107 > g) {
               intValue12--;
            }

            return intValue12 <= 0 ? text10 : string.substring(0, intValue12) + text10;
         }
      }
   }

   private static void invoke3(
      RenderManager renderManager5, FontObject fontObject2, String string, float f, float g, float h, int i, float j, float k, float l
   ) {
      float floatValue108 = TextMeasureCache.resolve(fontObject2, string, h).floatValue;
      if (floatValue108 <= l) {
         renderManager5.invoke69(fontObject2, f, g, h, string, i);
      } else {
         float floatValue109 = floatValue108 - l;
         long longValue = 8000L;
         float floatValue110 = (float)(System.currentTimeMillis() % longValue) / (float)longValue;
         float floatValue111 = 0.0F;
         if (floatValue110 < 0.2F) {
            floatValue111 = 0.0F;
         } else if (floatValue110 < 0.45F) {
            float floatValue112 = (floatValue110 - 0.2F) / 0.3F;
            floatValue111 = measure2(floatValue112);
         } else if (floatValue110 < 0.7F) {
            floatValue111 = 1.0F;
         } else if (floatValue110 < 0.95F) {
            float floatValue113 = (floatValue110 - 0.7F) / 0.25F;
            floatValue111 = 1.0F - measure2(floatValue113);
         } else {
            floatValue111 = 0.0F;
         }

         float floatValue114 = floatValue109 * floatValue111;
         renderManager5.invoke24(f, j, Math.max(1.0F, l), k, 0.0F, 0.0F, 0.0F, 0.0F);
         renderManager5.invoke69(fontObject2, f - floatValue114, g, h, string, i);
         renderManager5.invoke25();
      }
   }

   private static float measure2(float f) {
      float floatValue115 = 2.0F;
      float floatValue116 = floatValue115 + 1.0F;
      float floatValue117 = f - 1.0F;
      return 1.0F + floatValue116 * floatValue117 * floatValue117 * floatValue117 + floatValue115 * floatValue117 * floatValue117;
   }

   private static String resolve4(float f) {
      int intValue13 = Math.round(f * 10.0F);
      return intValue13 / 10 + "." + Math.abs(intValue13 % 10);
   }

   private static boolean check3(float f, float g, float h, float i, float j, float k) {
      return f >= h && f <= h + j && g >= i && g <= i + k;
   }

   public record HudSettingsRendererBounds(float x, float y, float width, float height) {
      public boolean contains(float f, float g, float h) {
         return f >= this.x - h && f <= this.x + this.width + h && g >= this.y - h && g <= this.y + this.height + h;
      }
   }
}
