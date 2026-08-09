package ru.metaculture.protection;

import java.util.Locale;
import net.minecraft.util.math.MathHelper;

@HudElementInfo(
   resolve = "Neuro Monitor",
   resolve2 = "i"
)
public final class NeuroMonitorHud extends HudElement implements MinecraftAccessor {
   private static final NeuroMonitorHud INSTANCE = new NeuroMonitorHud();
   private static final Animation ANIMATION = new Animation();

   private NeuroMonitorHud() {
      ru.metaculture.protection.HudPresetManager.invoke2(this);
   }

   public static void invoke(RenderManager renderManager) {
      INSTANCE.invoke2(renderManager);
   }

   private void invoke2(RenderManager renderManager2) {
      if (a_.player != null && a_.world != null) {
         AiRotationStatus aiRotationStatus = AiRotationTrainer.resolve11();
         long longValue = System.currentTimeMillis();
         boolean flag = AttackAura.rezhimRotatsii.is("AI") || AiRotationTrainer.isFlag() || aiRotationStatus.training() || longValue - aiRotationStatus.updatedAtMs() < 4000L;
         ANIMATION.check();
         ANIMATION.resolve4(flag ? 1.0 : 0.0, 0.2F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
         float floatValue = ANIMATION.measure3();
         if (!(floatValue <= 0.01F)) {
            float floatValue2 = 306.0F;
            float floatValue3 = 170.0F;
            float floatValue4 = 12.0F;
            float floatValue5 = 120.0F;
            HudEditorRenderer.HudEditorRendererState hudEditorRendererState = HudEditorRenderer.getINSTANCE().resolve("HUD_NeuroMonitor", floatValue4, floatValue5, floatValue2, floatValue3);
            float floatValue6 = floatValue * this.prozrachnost.getValue();
            float floatValue7 = hudEditorRendererState.floatValue;
            float floatValue8 = hudEditorRendererState.floatValue2;
            float floatValue9 = hudEditorRendererState.floatValue3;
            float floatValue10 = hudEditorRendererState.floatValue4;
            this.invoke3(floatValue7, floatValue8, floatValue9, floatValue10);
            int intValue = this.compute6(floatValue6);
            int intValue2 = this.compute7(floatValue6);
            int intValue3 = this.compute9(floatValue6);
            int intValue4 = ColorUtils.compute43(255, 156, 86, (int)(255.0F * floatValue6));
            int intValue5 = this.compute(aiRotationStatus, floatValue6);
            this.invoke(renderManager2, floatValue7, floatValue8, floatValue9, floatValue10, 12.0F, floatValue6);
            float floatValue11 = 12.0F;
            renderManager2.invoke39(floatValue7 + floatValue11 + 4.0F, floatValue8 + 15.0F, 4.0F, 0.0F, 360.0F, intValue5);
            renderManager2.invoke69(FontRegistry.fontObject4, floatValue7 + floatValue11 + 14.0F, floatValue8 + 18.0F, 21.0F, "Neuro Monitor", intValue);
            String text = aiRotationStatus.text();
            float floatValue12 = TextMeasureCache.resolve(FontRegistry.fontObject, text, 15.0F).floatValue;
            renderManager2.invoke69(FontRegistry.fontObject, floatValue7 + floatValue9 - floatValue11 - floatValue12, floatValue8 + 17.0F, 15.0F, text, intValue2);
            String text2 = AiRotationTrainer.getFloatValue17() < 0.0F ? "—" : String.format(Locale.ROOT, "%.4f", AiRotationTrainer.getFloatValue17());
            String text3 = "Profile "
               + AiRotationTrainer.getDefaultValue()
               + "   Pairs "
               + AiRotationTrainer.getIntValue10()
               + "   Loss "
               + text2
               + "   Jitter "
               + String.format(Locale.ROOT, "%.2f", AttackAura.aiJitter.getValue());
            renderManager2.invoke69(FontRegistry.fontObject, floatValue7 + floatValue11, floatValue8 + 35.0F, 13.0F, text3, ColorUtils.compute2(intValue2, (int)(215.0F * floatValue6)));
            float floatValue13 = floatValue7 + floatValue11;
            float floatValue14 = floatValue9 - floatValue11 * 2.0F;
            float floatValue15 = 44.0F;
            float floatValue16 = floatValue8 + 50.0F;
            renderManager2.invoke69(FontRegistry.fontObject, floatValue13, floatValue16, 13.0F, "Твой стиль (датасет)", intValue2);
            this.invoke3(renderManager2, floatValue13 + floatValue14, floatValue16, intValue3, intValue4, floatValue6);
            this.invoke4(
               renderManager2,
               floatValue13,
               floatValue16 + 5.0F,
               floatValue14,
               floatValue15,
               AiRotationTrainer.getFloats(),
               AiRotationTrainer.getFloats2(),
               -1,
               floatValue6,
               intValue3,
               intValue4,
               "Нет записи — .ai train -> .ai learn"
            );
            float floatValue17 = floatValue16 + 5.0F + floatValue15 + 12.0F;
            String text4 = AiRotationTrainer.isFlag() ? "Твой аим — запись (live)" : (AiRotationTrainer.check() ? "Нейросеть — бой (live)" : "Live");
            renderManager2.invoke69(FontRegistry.fontObject, floatValue13, floatValue17, 13.0F, text4, intValue2);
            this.invoke4(
               renderManager2,
               floatValue13,
               floatValue17 + 5.0F,
               floatValue14,
               floatValue15,
               AiRotationTrainer.getFLOATS_2(),
               AiRotationTrainer.getFLOATS_3(),
               AiRotationTrainer.getIntValue9(),
               floatValue6,
               intValue3,
               intValue4,
               "Ожидание..."
            );
            HudEditorRenderer.getINSTANCE().invoke6(hudEditorRendererState);
            HudSettingsRenderer.invoke2(renderManager2, this, hudEditorRendererState, HudEditorRenderer.getINSTANCE(), a_.getWindow().getScaledWidth(), a_.getWindow().getScaledHeight());
         }
      }
   }

   private void invoke3(RenderManager renderManager3, float f, float g, int i, int j, float h) {
      float floatValue18 = 12.0F;
      String text5 = "Pitch";
      String text6 = "Yaw";
      float floatValue19 = TextMeasureCache.resolve(FontRegistry.fontObject, text5, floatValue18).floatValue;
      float floatValue20 = TextMeasureCache.resolve(FontRegistry.fontObject, text6, floatValue18).floatValue;
      float floatValue21 = f - floatValue19;
      renderManager3.invoke69(FontRegistry.fontObject, floatValue21, g, floatValue18, text5, ColorUtils.compute2(j, (int)(255.0F * h)));
      float floatValue22 = floatValue21 - 10.0F - floatValue20;
      renderManager3.invoke69(FontRegistry.fontObject, floatValue22, g, floatValue18, text6, ColorUtils.compute2(i, (int)(255.0F * h)));
   }

   private void invoke4(RenderManager renderManager4, float f, float g, float h, float i, float[] fs, float[] gs, int j, float k, int l, int m, String string) {
      renderManager4.invoke5(f, g, h, i, 6.0F, ColorUtils.compute43(8, 10, 16, (int)(150.0F * k)));
      if (this.check2()) {
         renderManager4.invoke28(f, g, h, i, 6.0F, this.compute5(k), 1.0F);
      }

      float floatValue23 = g + i * 0.5F;
      renderManager4.invoke4(f + 3.0F, floatValue23 - 0.5F, h - 6.0F, 1.0F, ColorUtils.compute2(l, (int)(40.0F * k)));
      if (fs != null && gs != null && fs.length != 0) {
         int intValue6 = Math.min(fs.length, gs.length);
         float floatValue24 = 6.0F;

         for (int intValue7 = 0; intValue7 < intValue6; intValue7++) {
            float floatValue25 = Math.abs(fs[intValue7]);
            if (floatValue25 > floatValue24) {
               floatValue24 = floatValue25;
            }

            float floatValue26 = Math.abs(gs[intValue7]);
            if (floatValue26 > floatValue24) {
               floatValue24 = floatValue26;
            }
         }

         if (floatValue24 > 35.0F) {
            floatValue24 = 35.0F;
         }

         float floatValue27 = i * 0.5F - 3.0F;
         float floatValue28 = floatValue27 / floatValue24;
         float floatValue29 = h / intValue6;
         float floatValue30 = Math.max(1.0F, floatValue29 * 0.9F);
         int intValue8 = ColorUtils.compute2(l, (int)(225.0F * k));
         int intValue9 = ColorUtils.compute2(m, (int)(150.0F * k));

         for (int intValue10 = 0; intValue10 < intValue6; intValue10++) {
            int intValue11 = j < 0 ? intValue10 : (j + intValue10) % intValue6;
            float floatValue31 = f + intValue10 * floatValue29;
            float floatValue32 = MathHelper.clamp(gs[intValue11] * floatValue28, -floatValue27, floatValue27);
            if (floatValue32 >= 0.0F) {
               renderManager4.invoke4(floatValue31, floatValue23 - floatValue32, floatValue30, floatValue32, intValue9);
            } else {
               renderManager4.invoke4(floatValue31, floatValue23, floatValue30, -floatValue32, intValue9);
            }

            float floatValue33 = MathHelper.clamp(fs[intValue11] * floatValue28, -floatValue27, floatValue27);
            if (floatValue33 >= 0.0F) {
               renderManager4.invoke4(floatValue31, floatValue23 - floatValue33, floatValue30, floatValue33, intValue8);
            } else {
               renderManager4.invoke4(floatValue31, floatValue23, floatValue30, -floatValue33, intValue8);
            }
         }
      } else {
         float floatValue34 = TextMeasureCache.resolve(FontRegistry.fontObject, string, 12.0F).floatValue;
         renderManager4.invoke69(
            FontRegistry.fontObject, f + (h - floatValue34) * 0.5F, floatValue23 + 4.0F, 12.0F, string, ColorUtils.compute43(150, 156, 170, (int)(185.0F * k))
         );
      }
   }

   private int compute(AiRotationStatus aiRotationStatus2, float f) {
      String text7 = aiRotationStatus2.text().toLowerCase(Locale.ROOT);
      if (text7.contains("failed") || text7.contains("error") || text7.contains("missing") || text7.contains("устар")) {
         return ColorUtils.compute43(255, 96, 112, (int)(255.0F * f));
      } else if (aiRotationStatus2.training()) {
         return ColorUtils.compute43(255, 198, 92, (int)(255.0F * f));
      } else if (text7.contains("recording") || text7.contains("запис")) {
         return ColorUtils.compute43(92, 235, 182, (int)(255.0F * f));
      } else {
         return !text7.contains("brain") && !text7.contains("ready") && !text7.contains("replay")
            ? this.compute9(f)
            : ColorUtils.compute43(128, 226, 255, (int)(255.0F * f));
      }
   }
}
