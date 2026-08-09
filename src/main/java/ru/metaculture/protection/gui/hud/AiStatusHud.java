package ru.metaculture.protection;

import java.util.Locale;

@ModuleAccess(
   usernames = {"lichoday"}
)
@HudElementInfo(
   resolve = "AI Status",
   resolve2 = "i"
)
public final class AiStatusHud extends HudElement implements MinecraftAccessor {
   private static final AiStatusHud INSTANCE = new AiStatusHud();
   private static final Animation ANIMATION = new Animation();
   private static final Animation ANIMATION_2 = new Animation();
   private static final Animation ANIMATION_3 = new Animation();

   private AiStatusHud() {
      ru.metaculture.protection.HudPresetManager.invoke2(this);
   }

   public static void invoke(RenderManager renderManager) {
      INSTANCE.invoke2(renderManager);
   }

   private void invoke2(RenderManager renderManager2) {
      if (a_.player != null && a_.world != null) {
         AiRotationStatus aiRotationStatus = AiRotationTrainer.resolve11();
         boolean flag = AttackAura.rezhimRotatsii.is("AI") || System.currentTimeMillis() - aiRotationStatus.updatedAtMs() < 2000L;
         ANIMATION.check();
         ANIMATION_2.check();
         ANIMATION_3.check();
         ANIMATION.resolve4(flag ? 1.0 : 0.0, 0.2F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
         if (!(ANIMATION.measure3() <= 0.01F)) {
            String text = "AI Aura";
            String text2 = aiRotationStatus.text();
            String text3 = "Frames " + aiRotationStatus.queuedRecords() + "  Saved " + aiRotationStatus.writtenRecords();
            float floatValue = 24.0F;
            float floatValue2 = 21.0F;
            float floatValue3 = 18.0F;
            float floatValue4 = TextMeasureCache.resolve(FontRegistry.fontObject4, text, floatValue).floatValue;
            float floatValue5 = TextMeasureCache.resolve(FontRegistry.fontObject, text2, floatValue2).floatValue;
            float floatValue6 = TextMeasureCache.resolve(FontRegistry.fontObject, text3, floatValue3).floatValue;
            float floatValue7 = Math.max(142.0F, Math.max(floatValue4 + floatValue5 + 48.0F, floatValue6 + 44.0F));
            float floatValue8 = 48.0F;
            if (ANIMATION_2.measure3() <= 1.0F) {
               ANIMATION_2.invoke(floatValue7);
            }

            ANIMATION_2.resolve4(floatValue7, 0.18F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
            float floatValue9 = ANIMATION_2.measure3();
            float floatValue10 = (a_.getWindow().getFramebufferWidth() - floatValue9) * 0.5F;
            float floatValue11 = 52.0F;
            HudEditorRenderer.HudEditorRendererState hudEditorRendererState = HudEditorRenderer.getINSTANCE().resolve("HUD_AIStatus", floatValue10, floatValue11, floatValue9, floatValue8);
            float floatValue12 = ANIMATION.measure3() * this.prozrachnost.getValue();
            float floatValue13 = hudEditorRendererState.floatValue;
            float floatValue14 = hudEditorRendererState.floatValue2;
            float floatValue15 = hudEditorRendererState.floatValue3;
            float floatValue16 = hudEditorRendererState.floatValue4;
            this.invoke3(floatValue13, floatValue14, floatValue15, floatValue16);
            float floatValue17 = 12.0F;
            int intValue = this.compute2(floatValue12);
            int intValue2 = this.compute6(floatValue12);
            int intValue3 = this.compute7(floatValue12);
            int intValue4 = this.compute(aiRotationStatus, floatValue12);
            float floatValue18 = this.measure(aiRotationStatus);
            this.invoke(renderManager2, floatValue13, floatValue14, floatValue15, floatValue16, floatValue17, floatValue12);
            if (!this.check8() && this.check()) {
               renderManager2.invoke41(
                  floatValue13 + 8.0F, floatValue14 + floatValue16 - 3.0F, floatValue15 - 16.0F, 4.0F, 6.0F, 12.0F, 1.0F, ColorUtils.compute2(intValue4, (int)(50.0F * floatValue12))
               );
            }

            if (this.check8()) {
               this.invoke2(renderManager2, floatValue13 + 6.0F, floatValue14 + 6.0F, floatValue15 - 12.0F, floatValue16 - 12.0F, 8.0F, floatValue12);
            } else {
               renderManager2.invoke5(floatValue13 + 6.0F, floatValue14 + 6.0F, floatValue15 - 12.0F, floatValue16 - 12.0F, 8.0F, intValue);
            }

            renderManager2.invoke34(
               floatValue13 + 10.0F,
               floatValue14 + floatValue16 - 2.0F,
               floatValue15 - 20.0F,
               1.0F,
               0.5F,
               ColorUtils.compute2(this.compute10(1.0F), (int)(22.0F * floatValue12)),
               ColorUtils.compute2(intValue4, (int)(74.0F * floatValue12))
            );
            float floatValue19 = floatValue13 + 20.0F;
            float floatValue20 = floatValue14 + floatValue16 * 0.5F;
            renderManager2.invoke39(
               floatValue19, floatValue20, 8.0F + floatValue18 * 5.0F, 0.0F, 360.0F, ColorUtils.compute2(intValue4, (int)(42.0F * floatValue12 * (1.0F - floatValue18 * 0.5F)))
            );
            renderManager2.invoke39(floatValue19, floatValue20, 4.0F, 0.0F, 360.0F, intValue4);
            renderManager2.invoke69(FontRegistry.fontObject4, floatValue13 + 36.0F, floatValue14 + 20.0F, floatValue, text, intValue2);
            renderManager2.invoke69(FontRegistry.fontObject, floatValue13 + floatValue15 - 12.0F - floatValue5, floatValue14 + 20.0F, floatValue2, text2, intValue3);
            renderManager2.invoke69(
               FontRegistry.fontObject, floatValue13 + 36.0F, floatValue14 + 38.0F, floatValue3, text3, ColorUtils.compute43(155, 165, 180, (int)(165.0F * floatValue12))
            );
            HudEditorRenderer.getINSTANCE().invoke6(hudEditorRendererState);
            HudSettingsRenderer.invoke2(renderManager2, this, hudEditorRendererState, HudEditorRenderer.getINSTANCE(), a_.getWindow().getScaledWidth(), a_.getWindow().getScaledHeight());
         }
      }
   }

   private int compute(AiRotationStatus aiRotationStatus2, float f) {
      String text4 = aiRotationStatus2.text().toLowerCase(Locale.ROOT);
      if (text4.contains("failed") || text4.contains("error") || text4.contains("missing")) {
         return ColorUtils.compute43(255, 96, 112, (int)(255.0F * f));
      } else if (aiRotationStatus2.training()) {
         return ColorUtils.compute43(255, 198, 92, (int)(255.0F * f));
      } else if (aiRotationStatus2.loadingModel()) {
         return ColorUtils.compute43(120, 176, 255, (int)(255.0F * f));
      } else if (text4.contains("recording")) {
         return ColorUtils.compute43(92, 235, 182, (int)(255.0F * f));
      } else {
         return !text4.contains("replay") && !text4.contains("ready")
            ? ColorUtils.compute2(this.compute9(1.0F), (int)(255.0F * f))
            : ColorUtils.compute43(128, 226, 255, (int)(255.0F * f));
      }
   }

   private float measure(AiRotationStatus aiRotationStatus3) {
      boolean flag2 = aiRotationStatus3.training() || aiRotationStatus3.text().contains("recording") || aiRotationStatus3.text().contains("replay");
      ANIMATION_3.resolve4(flag2 ? 1.0 : 0.0, 0.2F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
      return ANIMATION_3.measure3() * (0.5F + 0.5F * (float)Math.sin(System.currentTimeMillis() / 180.0));
   }
}
