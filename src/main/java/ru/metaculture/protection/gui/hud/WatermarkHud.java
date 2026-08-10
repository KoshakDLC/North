package ru.metaculture.protection;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.metaculture.profile.Profile;

@HudElementInfo(
   resolve = "WaterMark",
   resolve2 = "N"
)
public final class WatermarkHud extends HudElement {
   private static final WatermarkHud INSTANCE = new WatermarkHud();
   private static final Animation ANIMATION = new Animation();
   private static int intValue = 0;
   private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
   private final Map<String, Animation> valuesByKey = new HashMap<>();
   private final List<WatermarkHud.WatermarkHudState> items = new ArrayList<>(4);
   private final GroupSetting otobrazhat = new GroupSetting(
      "Отображать", new BooleanSetting("Username", true), new BooleanSetting("UID", true), new BooleanSetting("FPS", true), new BooleanSetting("Time", true)
   );
   private float floatValue = 0.0F;
   private float floatValue2 = 0.0F;
   private float floatValue3 = 0.0F;
   private float floatValue4 = 0.0F;

   private WatermarkHud() {
      this.invoke(this.otobrazhat);
      ru.metaculture.protection.HudPresetManager.invoke2(this);
   }

   public static WatermarkHud getINSTANCE() {
      return INSTANCE;
   }

   public static void invoke(RenderManager renderManager) {
      INSTANCE.invoke3(renderManager);
   }

   private boolean check(float f, float g, float h, float i, float j, float k) {
      return f >= h && f <= h + j && g >= i && g <= i + k;
   }

   private void invoke2(String string, String string2, String string3, String string4, List<WatermarkHud.WatermarkHudState> list) {
      Animation animation = this.valuesByKey.computeIfAbsent(string, stringx -> new Animation());
      animation.check();
      animation.resolve4(this.otobrazhat.isEnabled(string) ? 1.0 : 0.0, 0.2F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
      if (animation.measure3() > 0.01F) {
         WatermarkHud.WatermarkHudState watermarkHudState = new WatermarkHud.WatermarkHudState(string, string2, string3, string4);
         watermarkHudState.floatValue2 = animation.measure3();
         list.add(watermarkHudState);
      }
   }

   public void invoke3(RenderManager renderManager2) {
      if (MinecraftAccessor.a_.player != null) {
         ANIMATION.check();
         ANIMATION.resolve4(1.0, 0.22F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
         float floatValue = ANIMATION.measure3();
         if (!(floatValue <= 0.01F)) {
            float floatValue2 = HudEditorRenderer.getINSTANCE().getFloatValue();
            float floatValue3 = HudEditorRenderer.getINSTANCE().getFloatValue2();
            boolean flag = HudEditorRenderer.getINSTANCE().isFlag4();
            boolean flag2 = HudEditorRenderer.getINSTANCE().isFlag3();
            String text = HudEditorRenderer.getINSTANCE().getText();
            if (this.floatValue3 > 0.0F
               && this.check(floatValue2, floatValue3, this.floatValue, this.floatValue2, this.floatValue3, this.floatValue4)
               && text == null) {
               if (flag) {
                  MinecraftAccessor.a_.keyboard.setClipboard(Profile.getUsername());
               }

               if (flag2) {
                  HudEditorRenderer.getINSTANCE().invoke();
               }
            }

            int intValue = MinecraftAccessor.a_.getCurrentFps();
            intValue = intValue + (int)((intValue - intValue) * RenderMath.measure25(0.2F));
            int intValue2 = Profile.getUid();
            boolean flag3 = HudModule.check2();
            HudLayoutManager.HudLayoutManagerState hudLayoutManagerState = flag3 ? HudLayoutManager.resolve4("HUD_WaterMark") : null;
            float floatValue4 = flag3 ? hudLayoutManagerState.floatValue12 : 24.0F;
            float floatValue5 = flag3 ? hudLayoutManagerState.floatValue13 : 24.0F;
            float floatValue6 = flag3 ? hudLayoutManagerState.floatValue8 : 7.0F;
            float floatValue7 = 10.0F;
            float floatValue8 = flag3 ? hudLayoutManagerState.floatValue9 : 5.0F;
            float floatValue9 = flag3 ? hudLayoutManagerState.floatValue11 : 32.0F;
            this.items.clear();
            List items = this.items;
            this.invoke2("Username", "r", Profile.getUsername(), "", items);
            this.invoke2("FPS", "u", String.valueOf(intValue), "fps", items);
            this.invoke2("Time", "y", this.simpleDateFormat.format(System.currentTimeMillis()), "", items);
            this.invoke2("UID", "t", String.valueOf(intValue2), "uid", items);
            float floatValue10 = 32.0F;
            float floatValue11 = floatValue6 + floatValue10;

            for (WatermarkHud.WatermarkHudState watermarkHudState2 : (List<WatermarkHud.WatermarkHudState>)items) {
               float floatValue12 = TextMeasureCache.resolve(FontRegistry.fontObject, watermarkHudState2.text3, floatValue4).floatValue;
               float floatValue13 = watermarkHudState2.text4.isEmpty() ? 0.0F : TextMeasureCache.resolve(FontRegistry.fontObject, watermarkHudState2.text4, floatValue4).floatValue;
               float floatValue14 = TextMeasureCache.resolve(FontRegistry.fontObject8, watermarkHudState2.text2, floatValue5).floatValue;
               float floatValue15 = floatValue14 + 8.0F + floatValue12 + floatValue13 + floatValue7 * 2.0F;
               watermarkHudState2.floatValue = floatValue15 * watermarkHudState2.floatValue2;
               floatValue11 += floatValue8 * watermarkHudState2.floatValue2 + watermarkHudState2.floatValue;
            }

            floatValue11 += floatValue6;
            float floatValue16 = floatValue9 + floatValue6 * 2.0F;
            HudEditorRenderer.HudEditorRendererState hudEditorRendererState = HudEditorRenderer.getINSTANCE().resolve("HUD_WaterMark", 10.0F, 10.0F, floatValue11, floatValue16);
            float floatValue17 = hudEditorRendererState.floatValue;
            float floatValue18 = hudEditorRendererState.floatValue2;
            float floatValue19 = hudEditorRendererState.floatValue3;
            float floatValue20 = hudEditorRendererState.floatValue4;
            this.invoke3(floatValue17, floatValue18, floatValue19, floatValue20);
            float floatValue21 = floatValue19 / Math.max(1.0F, floatValue11);
            float floatValue22 = floatValue20 / Math.max(1.0F, floatValue16);
            float floatValue23 = Math.min(floatValue21, floatValue22);
            float floatValue24 = floatValue6 * floatValue21;
            float floatValue25 = floatValue6 * floatValue22;
            float floatValue26 = floatValue8 * floatValue21;
            float floatValue27 = floatValue10 * floatValue21;
            float floatValue28 = floatValue9 * floatValue22;
            float floatValue29 = floatValue * this.prozrachnost.getValue();
            int intValue3 = this.compute2(floatValue29);
            int intValue4 = this.compute5(floatValue29);
            int intValue5 = this.compute6(floatValue29);
            int intValue6 = this.compute8(floatValue29);
            float floatValue30 = flag3 ? hudLayoutManagerState.floatValue : 14.0F;
            this.invoke(renderManager2, floatValue17, floatValue18, floatValue19, floatValue20, floatValue30, floatValue29);
            float floatValue31 = floatValue17 + floatValue24;
            float floatValue32 = floatValue18 + floatValue25;
            if (this.check9() || this.check10()) {
               this.invoke2(renderManager2, floatValue31, floatValue32, floatValue27, floatValue28, 11.0F, floatValue29);
            } else if (!this.check15(floatValue31, floatValue32, floatValue27, floatValue28, 11.0F, false, floatValue29, 1)) {
               renderManager2.invoke6(floatValue31, floatValue32, floatValue27, floatValue28, 11.0F, 4.0F, 4.0F, 11.0F, intValue3);
               if (this.check2()) {
                  renderManager2.invoke29(floatValue31, floatValue32, floatValue27, floatValue28, 11.0F, 4.0F, 4.0F, 11.0F, intValue4, Math.max(1.0F, this.measure2() * 0.65F));
               }
            }

            float floatValue33 = (flag3 ? hudLayoutManagerState.floatValue13 : 26.0F) * floatValue23;
            float floatValue34 = TextMeasureCache.resolve(BrandMark.font(), BrandMark.GLYPH, floatValue33).floatValue;
            renderManager2.invoke69(BrandMark.font(), floatValue31 + (floatValue27 - floatValue34) / 2.0F, floatValue32 + floatValue28 / 2.0F + 5.5F * floatValue22, floatValue33, BrandMark.GLYPH, intValue6);
            float floatValue35 = floatValue31 + floatValue27;

            for (int intValue7 = 0; intValue7 < items.size(); intValue7++) {
               WatermarkHud.WatermarkHudState watermarkHudState3 = (WatermarkHud.WatermarkHudState)items.get(intValue7);
               floatValue35 += floatValue26 * watermarkHudState3.floatValue2;
               float floatValue36 = watermarkHudState3.floatValue * floatValue21;
               boolean flag4 = intValue7 == items.size() - 1;
               if (watermarkHudState3.text.equals("Username")) {
                  this.floatValue = floatValue35;
                  this.floatValue2 = floatValue32;
                  this.floatValue3 = floatValue36;
                  this.floatValue4 = floatValue28;
               }

               int intValue8 = ColorUtils.compute2(intValue3, (int)(ColorUtils.compute4(intValue3) * watermarkHudState3.floatValue2));
               int intValue9 = ColorUtils.compute2(intValue6, (int)(ColorUtils.compute4(intValue6) * watermarkHudState3.floatValue2));
               int intValue10 = ColorUtils.compute2(intValue5, (int)(ColorUtils.compute4(intValue5) * watermarkHudState3.floatValue2));
               boolean flag5 = watermarkHudState3.text.equals("Username") && flag2 && text == null && this.check(floatValue2, floatValue3, floatValue35, floatValue32, floatValue36, floatValue28);
               if (!this.check9() && !this.check10()) {
                  if (!this.check15(floatValue35, floatValue32, floatValue36, floatValue28, 11.0F, flag5, floatValue29 * watermarkHudState3.floatValue2, flag5 ? 2 : 1)) {
                     renderManager2.invoke6(floatValue35, floatValue32, floatValue36, floatValue28, 4.0F, flag4 ? 11.0F : 4.0F, flag4 ? 11.0F : 4.0F, 4.0F, intValue8);
                  }
               } else {
                  this.invoke2(renderManager2, floatValue35, floatValue32, floatValue36, floatValue28, 11.0F, floatValue29 * watermarkHudState3.floatValue2);
               }

               renderManager2.invoke24(floatValue35, floatValue32, floatValue36, floatValue28, 4.0F, flag4 ? 11.0F : 4.0F, flag4 ? 11.0F : 4.0F, 4.0F);
               float floatValue37 = floatValue35 + floatValue7 * floatValue21;
               float floatValue38 = floatValue32 + floatValue28 / 2.0F + 4.5F * floatValue22;
               float floatValue39 = floatValue5 * floatValue23;
               float floatValue40 = floatValue4 * floatValue23;
               renderManager2.invoke69(FontRegistry.fontObject8, floatValue37, floatValue38 + 1.0F * floatValue22, floatValue39, watermarkHudState3.text2, intValue9);
               floatValue37 += TextMeasureCache.resolve(FontRegistry.fontObject8, watermarkHudState3.text2, floatValue39).floatValue + 5.0F * floatValue21;
               renderManager2.invoke69(FontRegistry.fontObject, floatValue37, floatValue38, floatValue40, watermarkHudState3.text3, intValue10);
               if (!watermarkHudState3.text4.isEmpty()) {
                  floatValue37 += TextMeasureCache.resolve(FontRegistry.fontObject, watermarkHudState3.text3, floatValue40).floatValue;
                  renderManager2.invoke69(FontRegistry.fontObject, floatValue37, floatValue38, floatValue40, watermarkHudState3.text4, intValue9);
               }

               renderManager2.invoke25();
               floatValue35 += floatValue36;
            }

            HudEditorRenderer.getINSTANCE().invoke6(hudEditorRendererState);
            HudSettingsRenderer.invoke2(
               renderManager2,
               this,
               hudEditorRendererState,
               HudEditorRenderer.getINSTANCE(),
               MinecraftAccessor.a_.getWindow().getScaledWidth(),
               MinecraftAccessor.a_.getWindow().getScaledHeight()
            );
         }
      }
   }

   static class WatermarkHudState {
      final String text;
      final String text2;
      final String text3;
      final String text4;
      float floatValue;
      float floatValue2 = 1.0F;

      WatermarkHudState(String string, String string2, String string3, String string4) {
         this.text = string;
         this.text2 = string2;
         this.text3 = string3;
         this.text4 = string4;
      }
   }
}
