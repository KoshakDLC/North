package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

public final class AiLabScreen extends Screen {
   private static volatile boolean flag;
   private static final String[] ANALITIKA = new String[]{"Аналитика", "Обучение", "Сравнение"};
   private static final float FLOAT_VALUE = 52.0F;
   private static final float FLOAT_VALUE_2 = 28.0F;
   private static final float FLOAT_VALUE_3 = 30.0F;
   private static final float FLOAT_VALUE_4 = 28.0F;
   private static final float FLOAT_VALUE_5 = 28.0F;
   private static final float FLOAT_VALUE_6 = 24.0F;
   private static final float FLOAT_VALUE_7 = 22.0F;
   private static final float FLOAT_VALUE_8 = 28.0F;
   private final Animation animation = new Animation();
   private final List<AiLabScreen.AiLabScreenBounds> items = new ArrayList<>();
   private final List<AiLabScreen.AiLabScreenUiState> items2 = new ArrayList<>();
   private float floatValue;
   private float floatValue2;
   private float floatValue3 = 1.0F;
   private float floatValue4;
   private float floatValue5;
   private boolean flag2;
   private int intValue;
   private AiLabScreen.AiLabScreenUiState aiLabScreenUiState;
   private AiRotationTelemetry aiRotationTelemetry;
   private long timestamp;
   private AiLabScreen.AiLabScreenState aiLabScreenState = new AiLabScreen.AiLabScreenState(0.0F, 0.0F, 0.0F, 0.0F);
   private AiLabScreen.AiLabScreenState aiLabScreenState2 = new AiLabScreen.AiLabScreenState(0.0F, 0.0F, 0.0F, 0.0F);
   private final AiLabScreen.AiLabScreenState[] aiLabScreenStates = new AiLabScreen.AiLabScreenState[]{
      new AiLabScreen.AiLabScreenState(0.0F, 0.0F, 0.0F, 0.0F), new AiLabScreen.AiLabScreenState(0.0F, 0.0F, 0.0F, 0.0F), new AiLabScreen.AiLabScreenState(0.0F, 0.0F, 0.0F, 0.0F)
   };

   public AiLabScreen() {
      super(Text.literal("AI Lab"));
      invoke20();
      this.invoke();
   }

   private void invoke() {
      this.aiRotationTelemetry = AiRotationTrainer.resolve7();
      this.timestamp = System.currentTimeMillis();
   }

   public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
      this.invoke21(this.measure3((double)mouseX), this.measure4((double)mouseY));
      super.render(context, mouseX, mouseY, deltaTicks);
   }

   public void renderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
   }

   public void renderInGameBackground(DrawContext context) {
   }

   public void invoke2(RenderManager renderManager, int i, int j) {
      if (renderManager != null && i > 0 && j > 0) {
         this.invoke22();
         long longValue = System.currentTimeMillis();
         if (!AiRotationTrainer.isFlag() && longValue - this.timestamp > 2000L) {
            this.invoke();
         }

         this.animation.check();
         this.animation
            .resolve4(
               this.flag2 ? 0.0 : 1.0,
               this.flag2 ? 0.18F : 0.22F,
               this.flag2 ? LegacyEasingFunctions.LEGACY_EASING_FUNCTION_8 : LegacyEasingFunctions.LEGACY_EASING_FUNCTION_27,
               false
            );
         float floatValue = measure5(this.animation.measure3(), 0.0F, 1.0F);
         renderManager.invoke5(0.0F, 0.0F, (float)i, (float)j, 0.0F, compute(0, 0, 0, Math.round(170.0F * floatValue)));
         renderManager.invoke65(floatValue);
         float floatValue2 = (0.97F + 0.03F * floatValue) * 0.9F;
         this.floatValue3 = floatValue2;
         this.floatValue4 = i * 0.5F;
         this.floatValue5 = j * 0.5F;
         renderManager.invoke63(floatValue2, floatValue2, i * 0.5F, j * 0.5F);
         float floatValue3 = measure5(i - 80.0F, 900.0F, 1120.0F);
         float floatValue4 = measure5(j - 70.0F, 600.0F, 780.0F);
         float floatValue5 = (i - floatValue3) * 0.5F;
         float floatValue6 = (j - floatValue4) * 0.5F;
         this.aiLabScreenState = new AiLabScreen.AiLabScreenState(floatValue5, floatValue6, floatValue3, floatValue4);
         renderManager.invoke48(20.0F);
         renderManager.invoke44(floatValue5, floatValue6, floatValue3, floatValue4, 18.0F, 1.0F);
         renderManager.invoke5(floatValue5, floatValue6, floatValue3, floatValue4, 18.0F, compute(13, 15, 21, 186));
         renderManager.invoke28(floatValue5, floatValue6, floatValue3, floatValue4, 18.0F, compute(255, 255, 255, 26), 2.0F);
         this.items.clear();
         this.items2.clear();
         this.invoke3(renderManager);
         this.invoke4(renderManager);
         float floatValue7 = floatValue5 + 24.0F;
         float floatValue8 = floatValue6 + 170.0F;
         float floatValue9 = floatValue3 - 48.0F;
         float floatValue10 = floatValue4 - 170.0F - 24.0F;
         switch (this.intValue) {
            case 1:
               this.invoke9(renderManager, floatValue7, floatValue8, floatValue9, floatValue10);
               break;
            case 2:
               this.invoke10(renderManager, floatValue7, floatValue8, floatValue9, floatValue10);
               break;
            default:
               this.invoke5(renderManager, floatValue7, floatValue8, floatValue9, floatValue10);
         }

         renderManager.invoke64();
         renderManager.invoke66();
         if (this.flag2 && floatValue <= 0.015F) {
            MinecraftClient.getInstance().execute(() -> {
               if (MinecraftClient.getInstance().currentScreen == this) {
                  MinecraftClient.getInstance().setScreen(null);
               }
            });
         }
      }
   }

   private void invoke3(RenderManager renderManager2) {
      float floatValue11 = this.aiLabScreenState.floatValue + 24.0F;
      renderManager2.invoke69(FontRegistry.fontObject4, floatValue11, this.aiLabScreenState.floatValue2 + 54.0F, 52.0F, "AI Lab", compute(245, 248, 255, 246));
      String text = "Профиль " + AiRotationTrainer.getDefaultValue() + "  •  " + AiRotationTrainer.resolve12();
      renderManager2.invoke69(FontRegistry.fontObject, floatValue11, this.aiLabScreenState.floatValue2 + 88.0F, 28.0F, text, compute(150, 160, 178, 220));
      float floatValue12 = 44.0F;
      this.aiLabScreenState2 = new AiLabScreen.AiLabScreenState(
         this.aiLabScreenState.floatValue + this.aiLabScreenState.floatValue3 - floatValue12 - 18.0F, this.aiLabScreenState.floatValue2 + 18.0F, floatValue12, floatValue12
      );
      boolean flag = this.aiLabScreenState2.check(this.floatValue, this.floatValue2);
      renderManager2.invoke5(
         this.aiLabScreenState2.floatValue,
         this.aiLabScreenState2.floatValue2,
         floatValue12,
         floatValue12,
         10.0F,
         compute(flag ? 235 : 40, flag ? 80 : 44, flag ? 92 : 52, flag ? 235 : 150)
      );
      this.invoke19(renderManager2, "X", this.aiLabScreenState2, 30.0F, compute(245, 245, 250, 240));
   }

   private void invoke4(RenderManager renderManager3) {
      float floatValue13 = this.aiLabScreenState.floatValue + 24.0F;
      float floatValue14 = this.aiLabScreenState.floatValue2 + 108.0F;
      float floatValue15 = 200.0F;
      float floatValue16 = 46.0F;

      for (int intValue = 0; intValue < ANALITIKA.length; intValue++) {
         AiLabScreen.AiLabScreenState aiLabScreenState = new AiLabScreen.AiLabScreenState(floatValue13 + intValue * (floatValue15 + 10.0F), floatValue14, floatValue15, floatValue16);
         this.aiLabScreenStates[intValue] = aiLabScreenState;
         boolean flag2 = this.intValue == intValue;
         boolean flag3 = aiLabScreenState.check(this.floatValue, this.floatValue2);
         int intValue2 = flag2 ? compute(96, 150, 240, 210) : compute(255, 255, 255, flag3 ? 26 : 14);
         renderManager3.invoke5(aiLabScreenState.floatValue, aiLabScreenState.floatValue2, aiLabScreenState.floatValue3, aiLabScreenState.floatValue4, 10.0F, intValue2);
         this.invoke19(renderManager3, ANALITIKA[intValue], aiLabScreenState, 30.0F, flag2 ? compute(255, 255, 255, 246) : compute(180, 188, 204, 220));
      }
   }

   private void invoke5(RenderManager renderManager4, float f, float g, float h, float i) {
      AiRotationTelemetry aiRotationTelemetry = this.aiRotationTelemetry;
      if (aiRotationTelemetry != null && aiRotationTelemetry.flag) {
         renderManager4.invoke69(
            FontRegistry.fontObject,
            f,
            g + 22.0F,
            28.0F,
            "Кадров "
               + aiRotationTelemetry.intValue
               + "   Удары "
               + aiRotationTelemetry.intValue2
               + "   Промахи "
               + Math.round(aiRotationTelemetry.floatValue * 100.0F)
               + "%   Сенса "
               + String.format(Locale.ROOT, "%.2f", aiRotationTelemetry.floatValue2)
               + "   Дист "
               + resolve(aiRotationTelemetry.floatValue3)
               + "-"
               + resolve(aiRotationTelemetry.floatValue4)
               + "м",
            compute(200, 208, 222, 230)
         );
         float floatValue17 = (h - 18.0F) * 0.5F;
         float floatValue18 = g + 44.0F;
         float floatValue19 = (i - 44.0F - 18.0F) * 0.5F - 9.0F;
         this.invoke6(renderManager4, f, floatValue18, floatValue17, floatValue19, aiRotationTelemetry);
         this.invoke7(renderManager4, f + floatValue17 + 18.0F, floatValue18, floatValue17, floatValue19, aiRotationTelemetry);
         float floatValue20 = floatValue18 + floatValue19 + 18.0F;
         this.invoke8(renderManager4, f, floatValue20, floatValue17, floatValue19, "Yaw дельты", aiRotationTelemetry.ints2, aiRotationTelemetry.intValue6, compute(110, 200, 255, 255));
         this.invoke8(
            renderManager4, f + floatValue17 + 18.0F, floatValue20, floatValue17, floatValue19, "Pitch дельты", aiRotationTelemetry.ints3, aiRotationTelemetry.intValue7, compute(255, 156, 86, 255)
         );
      } else {
         this.invoke16(renderManager4, f, g, h, i, "Нет записи. Вкладка Обучение -> Запись, затем вернись.");
      }
   }

   private void invoke6(RenderManager renderManager5, float f, float g, float h, float i, AiRotationTelemetry aiRotationTelemetry2) {
      this.invoke15(renderManager5, f, g, h, i, "Дистанция: распределение");
      float floatValue21 = f + 16.0F;
      float floatValue22 = g + 44.0F;
      float floatValue23 = h - 32.0F;
      float floatValue24 = i - 58.0F;
      int intValue3 = Math.max(1, aiRotationTelemetry2.ints[0] + aiRotationTelemetry2.ints[1] + aiRotationTelemetry2.ints[2]);
      String[] texts = new String[]{"Близко <" + resolve(aiRotationTelemetry2.floatValue5), "Средне", "Далеко >" + resolve(aiRotationTelemetry2.floatValue6)};
      int[] intValues = new int[]{compute(92, 235, 182, 255), compute(110, 200, 255, 255), compute(255, 156, 86, 255)};
      float floatValue25 = floatValue23 / 3.0F - 14.0F;

      for (int intValue4 = 0; intValue4 < 3; intValue4++) {
         float floatValue26 = (float)aiRotationTelemetry2.ints[intValue4] / intValue3;
         float floatValue27 = floatValue21 + intValue4 * (floatValue23 / 3.0F) + 7.0F;
         float floatValue28 = Math.max(3.0F, floatValue26 * (floatValue24 - 28.0F));
         renderManager5.invoke5(floatValue27, floatValue22 + floatValue24 - 26.0F - floatValue28, floatValue25, floatValue28, 5.0F, intValues[intValue4]);
         String text2 = Math.round(floatValue26 * 100.0F) + "%";
         renderManager5.invoke69(FontRegistry.fontObject, floatValue27, floatValue22 + floatValue24 - 2.0F, 22.0F, texts[intValue4], compute(170, 178, 194, 220));
         renderManager5.invoke69(FontRegistry.fontObject4, floatValue27, floatValue22 + floatValue24 - 30.0F - floatValue28, 24.0F, text2, compute(235, 240, 250, 235));
      }
   }

   private void invoke7(RenderManager renderManager6, float f, float g, float h, float i, AiRotationTelemetry aiRotationTelemetry3) {
      this.invoke15(renderManager6, f, g, h, i, "Скорость аима <-> дистанция");
      float floatValue29 = f + 16.0F;
      float floatValue30 = g + 44.0F;
      float floatValue31 = h - 32.0F;
      float floatValue32 = i - 64.0F;
      float floatValue33 = Math.max(1.0F, aiRotationTelemetry3.floatValue7);
      int intValue5 = aiRotationTelemetry3.floats == null ? 0 : aiRotationTelemetry3.floats.length;
      float floatValue34 = intValue5 > 0 ? floatValue31 / intValue5 : floatValue31;

      for (int intValue6 = 0; intValue6 < intValue5; intValue6++) {
         float floatValue35 = aiRotationTelemetry3.floats[intValue6] / floatValue33;
         float floatValue36 = Math.max(1.0F, floatValue35 * (floatValue32 - 4.0F));
         renderManager6.invoke5(floatValue29 + intValue6 * floatValue34, floatValue30 + floatValue32 - floatValue36, Math.max(1.0F, floatValue34 * 0.85F), floatValue36, 0.0F, compute(120, 170, 255, 230));
      }

      renderManager6.invoke69(
         FontRegistry.fontObject, floatValue29, floatValue30 + floatValue32 + 18.0F, 22.0F, resolve(aiRotationTelemetry3.floatValue3) + "м", compute(150, 158, 174, 200)
      );
      String text3 = resolve(aiRotationTelemetry3.floatValue4) + "м";
      renderManager6.invoke69(
         FontRegistry.fontObject,
         floatValue29 + floatValue31 - TextMeasureCache.measure(FontRegistry.fontObject, text3, 22.0F),
         floatValue30 + floatValue32 + 18.0F,
         22.0F,
         text3,
         compute(150, 158, 174, 200)
      );
   }

   private void invoke8(RenderManager renderManager7, float f, float g, float h, float i, String string, int[] is, int j, int k) {
      this.invoke15(renderManager7, f, g, h, i, string);
      if (is != null) {
         float floatValue37 = f + 16.0F;
         float floatValue38 = g + 44.0F;
         float floatValue39 = h - 32.0F;
         float floatValue40 = i - 58.0F;
         float floatValue41 = floatValue39 / is.length;
         float floatValue42 = Math.max(1.0F, (float)j);

         for (int intValue7 = 0; intValue7 < is.length; intValue7++) {
            float floatValue43 = Math.max(1.0F, is[intValue7] / floatValue42 * (floatValue40 - 2.0F));
            renderManager7.invoke5(floatValue37 + intValue7 * floatValue41, floatValue38 + floatValue40 - floatValue43, Math.max(1.0F, floatValue41 * 0.8F), floatValue43, 0.0F, k);
         }

         renderManager7.invoke4(floatValue37 + floatValue39 * 0.5F - 0.5F, floatValue38, 1.0F, floatValue40, compute(255, 255, 255, 40));
      }
   }

   private void invoke9(RenderManager renderManager8, float f, float g, float h, float i) {
      List items = AiRotationTrainer.resolve27();
      String text4 = AiRotationTrainer.getDefaultValue();
      this.invoke15(renderManager8, f, g, h, 86.0F, "Профиль");
      AiLabScreen.AiLabScreenState aiLabScreenState2 = new AiLabScreen.AiLabScreenState(f + 16.0F, g + 40.0F, 38.0F, 34.0F);
      AiLabScreen.AiLabScreenState aiLabScreenState3 = new AiLabScreen.AiLabScreenState(f + 16.0F + 44.0F, g + 40.0F, 220.0F, 34.0F);
      AiLabScreen.AiLabScreenState aiLabScreenState4 = new AiLabScreen.AiLabScreenState(aiLabScreenState3.floatValue + aiLabScreenState3.floatValue3 + 8.0F, g + 40.0F, 38.0F, 34.0F);
      this.invoke17(renderManager8, aiLabScreenState2, "<", false, false, () -> this.invoke14(items, -1));
      renderManager8.invoke5(aiLabScreenState3.floatValue, aiLabScreenState3.floatValue2, aiLabScreenState3.floatValue3, aiLabScreenState3.floatValue4, 7.0F, compute(255, 255, 255, 16));
      this.invoke19(renderManager8, text4, aiLabScreenState3, 28.0F, compute(235, 240, 250, 235));
      this.invoke17(renderManager8, aiLabScreenState4, ">", false, false, () -> this.invoke14(items, 1));
      float floatValue44 = g + 104.0F;
      float floatValue45 = (h - 24.0F) / 4.0F - 8.0F;
      boolean flag4 = AiRotationTrainer.isFlag();
      boolean flag5 = AiRotationTrainer.isFlag2();
      boolean flag6 = AiRotationTrainer.isFlag6();
      this.invoke17(renderManager8, new AiLabScreen.AiLabScreenState(f, floatValue44, floatValue45, 42.0F), flag4 ? "Запись..." : "Запись", false, flag4, AiRotationTrainer::resolve);
      this.invoke17(renderManager8, new AiLabScreen.AiLabScreenState(f + (floatValue45 + 10.0F), floatValue44, floatValue45, 42.0F), "Стоп", true, false, () -> {
         AiRotationTrainer.resolve2();
         this.invoke();
      });
      this.invoke17(
         renderManager8,
         new AiLabScreen.AiLabScreenState(f + (floatValue45 + 10.0F) * 2.0F, floatValue44, floatValue45, 42.0F),
         flag6 ? "Обучение..." : "Обучить",
         false,
         flag6,
         AiRotationTrainer::resolve4
      );
      this.invoke17(
         renderManager8, new AiLabScreen.AiLabScreenState(f + (floatValue45 + 10.0F) * 3.0F, floatValue44, floatValue45, 42.0F), flag5 ? "Идёт" : "Запуск", false, flag5, this::invoke13
      );
      float floatValue46 = floatValue44 + 58.0F;
      this.invoke15(renderManager8, f, floatValue46, h, 120.0F, "Параметры");
      this.invoke18(
         renderManager8,
         new AiLabScreen.AiLabScreenState(f + 16.0F, floatValue46 + 46.0F, h - 32.0F, 30.0F),
         "AI Jitter (сила твоей тряски)",
         0.0F,
         2.0F,
         false,
         AttackAura.aiJitter::getValue,
         AttackAura.aiJitter::invoke
      );
      AiLabScreen.AiLabScreenState aiLabScreenState5 = new AiLabScreen.AiLabScreenState(f + 16.0F, floatValue46 + 84.0F, 230.0F, 28.0F);
      boolean flag7 = AttackAura.aiDebugLog.isEnabled();
      this.invoke17(
         renderManager8,
         aiLabScreenState5,
         flag7 ? "Логи: ВКЛ" : "Логи: ВЫКЛ",
         false,
         flag7,
         () -> AttackAura.aiDebugLog.setValue(!AttackAura.aiDebugLog.isEnabled())
      );
      AiLabScreen.AiLabScreenState aiLabScreenState6 = new AiLabScreen.AiLabScreenState(aiLabScreenState5.floatValue + aiLabScreenState5.floatValue3 + 12.0F, floatValue46 + 84.0F, 260.0F, 28.0F);
      boolean flag8 = AttackAura.aiHumanMisses.isEnabled();
      this.invoke17(
         renderManager8,
         aiLabScreenState6,
         flag8 ? "Промахи: ВКЛ" : "Промахи: ВЫКЛ",
         false,
         flag8,
         () -> AttackAura.aiHumanMisses.setValue(!AttackAura.aiHumanMisses.isEnabled())
      );
      AiRotationTelemetry aiRotationTelemetry4 = this.aiRotationTelemetry;
      float floatValue47 = floatValue46 + 132.0F;
      String text5 = aiRotationTelemetry4 != null && aiRotationTelemetry4.flag ? String.valueOf(aiRotationTelemetry4.intValue) : "-";
      String text6 = AiRotationTrainer.getFloatValue17() < 0.0F ? "-" : String.format(Locale.ROOT, "%.4f", AiRotationTrainer.getFloatValue17());
      String text7 = aiRotationTelemetry4 != null && aiRotationTelemetry4.flag
         ? "[" + aiRotationTelemetry4.ints[0] + "," + aiRotationTelemetry4.ints[1] + "," + aiRotationTelemetry4.ints[2] + "]"
         : "-";
      String text8 = aiRotationTelemetry4 != null && aiRotationTelemetry4.flag ? Math.round(aiRotationTelemetry4.floatValue * 100.0F) + "%" : "-";
      renderManager8.invoke69(
         FontRegistry.fontObject,
         f,
         floatValue47 + 12.0F,
         28.0F,
         "Кадров " + text5 + "   Loss " + text6 + "   Бакеты " + text7 + "   Промахи " + text8,
         compute(195, 204, 220, 230)
      );
      renderManager8.invoke69(
         FontRegistry.fontObject,
         f,
         floatValue47 + 44.0F,
         24.0F,
         "Совет: пиши на РАЗНЫХ дистанциях и веди по таргету плавно, не только флик.",
         compute(150, 158, 176, 205)
      );
   }

   private void invoke10(RenderManager renderManager9, float f, float g, float h, float i) {
      AiRotationTelemetry aiRotationTelemetry5 = this.aiRotationTelemetry;
      if (aiRotationTelemetry5 != null && aiRotationTelemetry5.flag) {
         float floatValue48 = (i - 18.0F) * 0.5F - 6.0F;
         this.invoke11(renderManager9, f, g, h, floatValue48, "Yaw: ты vs нейросеть", aiRotationTelemetry5.floats2, aiRotationTelemetry5.flag2 ? aiRotationTelemetry5.floats4 : null);
         this.invoke11(
            renderManager9, f, g + floatValue48 + 18.0F, h, floatValue48, "Pitch: ты vs нейросеть", aiRotationTelemetry5.floats3, aiRotationTelemetry5.flag2 ? aiRotationTelemetry5.floats5 : null
         );
         if (!aiRotationTelemetry5.flag2) {
            renderManager9.invoke69(
               FontRegistry.fontObject, f + 16.0F, g + 34.0F, 24.0F, "Модель не обучена — оранжевой линии нет. Жми Обучить.", compute(255, 180, 110, 230)
            );
         } else {
            String text9 = aiRotationTelemetry5.floatValue9 < 0.0F ? "-" : String.format(Locale.ROOT, "%.4f", aiRotationTelemetry5.floatValue9);
            String text10 = "Loss " + text9;
            renderManager9.invoke69(
               FontRegistry.fontObject,
               f + h - TextMeasureCache.measure(FontRegistry.fontObject, text10, 24.0F) - 16.0F,
               g + 34.0F,
               24.0F,
               text10,
               compute(150, 200, 255, 230)
            );
         }
      } else {
         this.invoke16(renderManager9, f, g, h, i, "Нет данных. Сначала запись и обучение.");
      }
   }

   private void invoke11(RenderManager renderManager10, float f, float g, float h, float i, String string, float[] fs, float[] gs) {
      this.invoke15(renderManager10, f, g, h, i, string);
      float floatValue49 = f + 14.0F;
      float floatValue50 = g + 42.0F;
      float floatValue51 = h - 28.0F;
      float floatValue52 = i - 64.0F;
      float floatValue53 = floatValue50 + floatValue52 * 0.5F;
      renderManager10.invoke4(floatValue49, floatValue53 - 0.5F, floatValue51, 1.0F, compute(255, 255, 255, 36));
      float floatValue54 = 6.0F;
      if (fs != null) {
         for (float floatValue55 : fs) {
            floatValue54 = Math.max(floatValue54, Math.abs(floatValue55));
         }
      }

      if (gs != null) {
         for (float floatValue56 : gs) {
            floatValue54 = Math.max(floatValue54, Math.abs(floatValue56));
         }
      }

      floatValue54 = Math.min(floatValue54, 35.0F);
      this.invoke12(renderManager10, floatValue49, floatValue53, floatValue51, floatValue52 * 0.5F - 2.0F, fs, floatValue54, compute(120, 210, 255, 235));
      this.invoke12(renderManager10, floatValue49, floatValue53, floatValue51, floatValue52 * 0.5F - 2.0F, gs, floatValue54, compute(255, 150, 90, 235));
      renderManager10.invoke69(FontRegistry.fontObject, floatValue49, floatValue50 + floatValue52 + 18.0F, 22.0F, "ты", compute(120, 210, 255, 220));
      renderManager10.invoke69(FontRegistry.fontObject, floatValue49 + 48.0F, floatValue50 + floatValue52 + 18.0F, 22.0F, "нейросеть", compute(255, 150, 90, 220));
   }

   private void invoke12(RenderManager renderManager11, float f, float g, float h, float i, float[] fs, float j, int k) {
      if (fs != null && fs.length != 0) {
         float floatValue57 = h / fs.length;
         float floatValue58 = i / j;

         for (int intValue8 = 0; intValue8 < fs.length; intValue8++) {
            float floatValue59 = MathHelper.clamp(fs[intValue8] * floatValue58, -i, i);
            if (floatValue59 >= 0.0F) {
               renderManager11.invoke4(f + intValue8 * floatValue57, g - floatValue59, Math.max(1.0F, floatValue57 * 0.8F), floatValue59, k);
            } else {
               renderManager11.invoke4(f + intValue8 * floatValue57, g, Math.max(1.0F, floatValue57 * 0.8F), -floatValue59, k);
            }
         }
      }
   }

   private void invoke13() {
      AiRotationTrainer.resolve3();
      if (WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null && AttackAura.rezhimRotatsii.options.contains("AI")) {
         AttackAura.rezhimRotatsii.value = "AI";
         AttackAura.rezhimRotatsii.selectedIndex = AttackAura.rezhimRotatsii.options.indexOf("AI");
         AttackAura attackAura = WildClient.INSTANCE.moduleManager.getModule(AttackAura.class);
         if (attackAura != null && !attackAura.enabled) {
            attackAura.setEnabled(true);
         }
      }
   }

   private void invoke14(List<String> list, int i) {
      if (list != null && !list.isEmpty()) {
         int intValue9 = list.indexOf(AiRotationTrainer.getDefaultValue());
         intValue9 = Math.floorMod((intValue9 < 0 ? 0 : intValue9) + i, list.size());
         AiRotationTrainer.resolve26((String)list.get(intValue9));
         this.invoke();
      }
   }

   private void invoke15(RenderManager renderManager12, float f, float g, float h, float i, String string) {
      renderManager12.invoke5(f, g, h, i, 12.0F, compute(255, 255, 255, 12));
      renderManager12.invoke28(f, g, h, i, 12.0F, compute(255, 255, 255, 22), 1.0F);
      renderManager12.invoke69(FontRegistry.fontObject4, f + 16.0F, g + 26.0F, 28.0F, string, compute(210, 218, 232, 235));
   }

   private void invoke16(RenderManager renderManager13, float f, float g, float h, float i, String string) {
      float floatValue60 = TextMeasureCache.measure(FontRegistry.fontObject, string, 28.0F);
      renderManager13.invoke69(FontRegistry.fontObject, f + (h - floatValue60) * 0.5F, g + i * 0.5F, 28.0F, string, compute(170, 178, 196, 220));
   }

   private void invoke17(RenderManager renderManager14, AiLabScreen.AiLabScreenState aiLabScreenState7, String string, boolean bl, boolean bl2, Runnable runnable) {
      boolean flag9 = aiLabScreenState7.check(this.floatValue, this.floatValue2);
      int intValue10;
      if (bl2) {
         intValue10 = compute(96, 150, 240, 220);
      } else if (bl) {
         intValue10 = compute(flag9 ? 230 : 150, flag9 ? 78 : 52, flag9 ? 90 : 60, flag9 ? 230 : 170);
      } else {
         intValue10 = compute(255, 255, 255, flag9 ? 34 : 18);
      }

      renderManager14.invoke5(aiLabScreenState7.floatValue, aiLabScreenState7.floatValue2, aiLabScreenState7.floatValue3, aiLabScreenState7.floatValue4, 8.0F, intValue10);
      this.invoke19(renderManager14, string, aiLabScreenState7, 28.0F, compute(238, 242, 250, 240));
      this.items.add(new AiLabScreen.AiLabScreenBounds(aiLabScreenState7, runnable));
   }

   private void invoke18(
      RenderManager renderManager15,
      AiLabScreen.AiLabScreenState aiLabScreenState8,
      String string,
      float f,
      float g,
      boolean bl,
      AiLabScreen.AiLabScreenProvider aiLabScreenProvider,
      AiLabScreen.AiLabScreenListener aiLabScreenListener
   ) {
      renderManager15.invoke69(
         FontRegistry.fontObject,
         aiLabScreenState8.floatValue,
         aiLabScreenState8.floatValue2 - 6.0F,
         24.0F,
         string + "  " + String.format(Locale.ROOT, "%.2f", aiLabScreenProvider.get()),
         compute(190, 198, 214, 225)
      );
      float floatValue61 = aiLabScreenState8.floatValue2 + 16.0F;
      renderManager15.invoke5(aiLabScreenState8.floatValue, floatValue61, aiLabScreenState8.floatValue3, 6.0F, 3.0F, compute(255, 255, 255, 30));
      float floatValue62 = measure5((aiLabScreenProvider.get() - f) / (g - f), 0.0F, 1.0F);
      renderManager15.invoke5(aiLabScreenState8.floatValue, floatValue61, aiLabScreenState8.floatValue3 * floatValue62, 6.0F, 3.0F, compute(110, 170, 255, 235));
      renderManager15.invoke39(aiLabScreenState8.floatValue + aiLabScreenState8.floatValue3 * floatValue62, floatValue61 + 3.0F, 8.0F, 0.0F, 360.0F, compute(235, 242, 255, 245));
      this.items2
         .add(
            new AiLabScreen.AiLabScreenUiState(
               string, f, g, bl, aiLabScreenProvider, aiLabScreenListener, new AiLabScreen.AiLabScreenState(aiLabScreenState8.floatValue, floatValue61 - 12.0F, aiLabScreenState8.floatValue3, 30.0F)
            )
         );
   }

   private void invoke19(RenderManager renderManager16, String string, AiLabScreen.AiLabScreenState aiLabScreenState9, float f, int i) {
      float floatValue63 = TextMeasureCache.measure(FontRegistry.fontObject, string, f);
      renderManager16.invoke69(
         FontRegistry.fontObject,
         aiLabScreenState9.floatValue + (aiLabScreenState9.floatValue3 - floatValue63) * 0.5F,
         aiLabScreenState9.floatValue2 + aiLabScreenState9.floatValue4 * 0.5F + f * 0.2F,
         f,
         string,
         i
      );
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      float floatValue64 = this.measure(this.measure3(mouseX));
      float floatValue65 = this.measure2(this.measure4(mouseY));
      if (this.aiLabScreenState2.check(floatValue64, floatValue65)) {
         this.close();
         return true;
      } else {
         for (int intValue11 = 0; intValue11 < this.aiLabScreenStates.length; intValue11++) {
            if (this.aiLabScreenStates[intValue11].check(floatValue64, floatValue65)) {
               this.intValue = intValue11;
               return true;
            }
         }

         for (AiLabScreen.AiLabScreenUiState aiLabScreenUiState : this.items2) {
            if (aiLabScreenUiState.aiLabScreenState.check(floatValue64, floatValue65)) {
               this.aiLabScreenUiState = aiLabScreenUiState;
               aiLabScreenUiState.invoke(floatValue64);
               return true;
            }
         }

         for (AiLabScreen.AiLabScreenBounds aiLabScreenBounds : this.items) {
            if (aiLabScreenBounds.bounds().check(floatValue64, floatValue65)) {
               aiLabScreenBounds.action().run();
               return true;
            }
         }

         return super.mouseClicked(mouseX, mouseY, button);
      }
   }

   public boolean mouseReleased(double mouseX, double mouseY, int button) {
      this.aiLabScreenUiState = null;
      return super.mouseReleased(mouseX, mouseY, button);
   }

   public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
      if (this.aiLabScreenUiState != null) {
         this.aiLabScreenUiState.invoke(this.measure(this.measure3(mouseX)));
         return true;
      } else {
         return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
      }
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (keyCode == 256) {
         this.close();
         return true;
      } else {
         return super.keyPressed(keyCode, scanCode, modifiers);
      }
   }

   public void close() {
      this.flag2 = true;
   }

   public boolean shouldPause() {
      return false;
   }

   private static void invoke20() {
      if (!flag) {
         flag = true;
         EventManager.register(new Object() {
            @EventHandler
            public void onHudRender(HudRenderEvent hudRenderEvent) {
               if (hudRenderEvent.getClient() != null && hudRenderEvent.getClient().currentScreen instanceof AiLabScreen aiLabScreen) {
                  aiLabScreen.invoke2(hudRenderEvent.getRenderManager(), hudRenderEvent.getIntValue(), hudRenderEvent.getIntValue2());
                  if (hudRenderEvent.getRenderManager() != null) {
                     hudRenderEvent.getRenderManager().invoke20();
                  }
               }
            }
         });
      }
   }

   private void invoke21(float f, float g) {
      this.floatValue = this.measure(f);
      this.floatValue2 = this.measure2(g);
   }

   private float measure(float f) {
      return this.floatValue3 <= 0.0F ? f : (f - this.floatValue4) / this.floatValue3 + this.floatValue4;
   }

   private float measure2(float f) {
      return this.floatValue3 <= 0.0F ? f : (f - this.floatValue5) / this.floatValue3 + this.floatValue5;
   }

   private void invoke22() {
      MinecraftClient client = MinecraftClient.getInstance();
      if (client != null && client.getWindow() != null && client.mouse != null) {
         double doubleValue = client.getWindow().getFramebufferWidth();
         double doubleValue2 = client.getWindow().getFramebufferHeight();
         if (!(doubleValue <= 0.0) && !(doubleValue2 <= 0.0)) {
            double doubleValue3 = client.mouse.getX();
            double doubleValue4 = client.mouse.getY();
            if (doubleValue3 >= 0.0 && doubleValue4 >= 0.0 && doubleValue3 <= doubleValue + 2.0 && doubleValue4 <= doubleValue2 + 2.0) {
               this.invoke21((float)doubleValue3, (float)doubleValue4);
            }
         }
      }
   }

   private float measure3(double d) {
      MinecraftClient client2 = MinecraftClient.getInstance();
      if (client2 != null && client2.getWindow() != null) {
         int intValue12 = client2.getWindow().getFramebufferWidth();
         int intValue13 = client2.getWindow().getScaledWidth();
         return intValue12 > 0 && intValue13 > 0 ? (float)(d * intValue12 / Math.max(1.0, (double)intValue13)) : (float)d;
      } else {
         return (float)d;
      }
   }

   private float measure4(double d) {
      MinecraftClient client3 = MinecraftClient.getInstance();
      if (client3 != null && client3.getWindow() != null) {
         int intValue14 = client3.getWindow().getFramebufferHeight();
         int intValue15 = client3.getWindow().getScaledHeight();
         return intValue14 > 0 && intValue15 > 0 ? (float)(d * intValue14 / Math.max(1.0, (double)intValue15)) : (float)d;
      } else {
         return (float)d;
      }
   }

   private static String resolve(float f) {
      return String.format(Locale.ROOT, "%.1f", f);
   }

   static float measure5(float f, float g, float h) {
      return !Float.isFinite(f) ? g : Math.max(g, Math.min(h, f));
   }

   private static int compute(int i, int j, int k, int l) {
      return RenderManager.RenderManagerState.compute37(i, j, k, Math.max(0, Math.min(255, l)));
   }

   static final class AiLabScreenState {
      final float floatValue;
      final float floatValue2;
      final float floatValue3;
      final float floatValue4;

      AiLabScreenState(float f, float g, float h, float i) {
         this.floatValue = f;
         this.floatValue2 = g;
         this.floatValue3 = h;
         this.floatValue4 = i;
      }

      boolean check(float f, float g) {
         return f >= this.floatValue && f <= this.floatValue + this.floatValue3 && g >= this.floatValue2 && g <= this.floatValue2 + this.floatValue4;
      }
   }

   record AiLabScreenBounds(AiLabScreen.AiLabScreenState bounds, Runnable action) {
   }

   interface AiLabScreenProvider {
      float get();
   }

   interface AiLabScreenListener {
      void set(float f);
   }

   final class AiLabScreenUiState {
      final String text;
      final float floatValue;
      final float floatValue2;
      final boolean flag;
      final AiLabScreen.AiLabScreenProvider aiLabScreenProvider;
      final AiLabScreen.AiLabScreenListener aiLabScreenListener;
      final AiLabScreen.AiLabScreenState aiLabScreenState;

      AiLabScreenUiState(String string, float f, float g, boolean bl, AiLabScreen.AiLabScreenProvider aiLabScreenProvider2, AiLabScreen.AiLabScreenListener aiLabScreenListener2, AiLabScreen.AiLabScreenState aiLabScreenState10) {
         this.text = string;
         this.floatValue = f;
         this.floatValue2 = g;
         this.flag = bl;
         this.aiLabScreenProvider = aiLabScreenProvider2;
         this.aiLabScreenListener = aiLabScreenListener2;
         this.aiLabScreenState = aiLabScreenState10;
      }

      void invoke(float f) {
         float floatValue66 = AiLabScreen.measure5((f - this.aiLabScreenState.floatValue) / this.aiLabScreenState.floatValue3, 0.0F, 1.0F);
         float floatValue67 = this.floatValue + floatValue66 * (this.floatValue2 - this.floatValue);
         if (this.flag) {
            floatValue67 = Math.round(floatValue67);
         } else {
            floatValue67 = Math.round(floatValue67 * 100.0F) / 100.0F;
         }

         this.aiLabScreenListener.set(AiLabScreen.measure5(floatValue67, this.floatValue, this.floatValue2));
      }
   }
}
