package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public final class RotationBuilderScreen extends Screen {
   private static volatile boolean flag;
   private static final String[] FUNTIME = new String[]{"FunTime", "Spooky", "Holy", "Matrix", "Smooth", "Snap", "Custom"};
   private final RotationPresetManager rotationPresetManager;
   private final RotationPresetStore rotationPresetStore;
   private List<RotationBuilderScreen.RotationBuilderScreenUiState> items = new ArrayList<>();
   private final List<RotationBuilderScreen.RotationBuilderScreenUiState> items2 = new ArrayList<>();
   private final List<RotationBuilderScreen.RotationBuilderScreenUiState> items3 = new ArrayList<>();
   private final List<RotationBuilderScreen.RotationBuilderScreenBounds> items4 = new ArrayList<>();
   private final Map<String, Animation> valuesByKey = new HashMap<>();
   private final Map<String, Animation> valuesByKey2 = new HashMap<>();
   private final Map<String, Animation> valuesByKey3 = new HashMap<>();
   private final Map<String, Animation> valuesByKey4 = new HashMap<>();
   private final Animation animation = new Animation();
   private final Animation animation2 = new Animation();
   private final Animation animation3 = new Animation();
   private final Animation animation4 = new Animation();
   private int intValue;
   private RotationBuilderScreen.RotationBuilderScreenState rotationBuilderScreenState = RotationBuilderScreen.RotationBuilderScreenState.resolve();
   private RotationBuilderScreen.RotationBuilderScreenState rotationBuilderScreenState2 = RotationBuilderScreen.RotationBuilderScreenState.resolve();
   private RotationBuilderScreen.RotationBuilderScreenState rotationBuilderScreenState3 = RotationBuilderScreen.RotationBuilderScreenState.resolve();
   private RotationBuilderScreen.RotationBuilderScreenState rotationBuilderScreenState4 = RotationBuilderScreen.RotationBuilderScreenState.resolve();
   private RotationBuilderScreen.RotationBuilderScreenState rotationBuilderScreenState5 = RotationBuilderScreen.RotationBuilderScreenState.resolve();
   private RotationBuilderScreen.RotationBuilderScreenState rotationBuilderScreenState6 = RotationBuilderScreen.RotationBuilderScreenState.resolve();
   private RotationBuilderScreen.RotationBuilderScreenState rotationBuilderScreenState7 = RotationBuilderScreen.RotationBuilderScreenState.resolve();
   private RotationBuilderScreen.RotationBuilderScreenState rotationBuilderScreenState8 = RotationBuilderScreen.RotationBuilderScreenState.resolve();
   private RotationBuilderScreen.RotationBuilderScreenState rotationBuilderScreenState9 = RotationBuilderScreen.RotationBuilderScreenState.resolve();
   private RotationBuilderScreen.RotationBuilderScreenState rotationBuilderScreenState10 = RotationBuilderScreen.RotationBuilderScreenState.resolve();
   private RotationBuilderScreen.RotationBuilderScreenState rotationBuilderScreenState11 = RotationBuilderScreen.RotationBuilderScreenState.resolve();
   private RotationBuilderScreen.RotationBuilderScreenState rotationBuilderScreenState12 = RotationBuilderScreen.RotationBuilderScreenState.resolve();
   private RotationBuilderScreen.RotationBuilderScreenState rotationBuilderScreenState13 = RotationBuilderScreen.RotationBuilderScreenState.resolve();
   private RotationBuilderScreen.RotationBuilderScreenState rotationBuilderScreenState14 = RotationBuilderScreen.RotationBuilderScreenState.resolve();
   private RotationBuilderScreen.RotationBuilderScreenState rotationBuilderScreenState15 = RotationBuilderScreen.RotationBuilderScreenState.resolve();
   private RotationBuilderScreen.RotationBuilderScreenState rotationBuilderScreenState16 = RotationBuilderScreen.RotationBuilderScreenState.resolve();
   private final List<RotationBuilderScreen.RotationBuilderScreenData> items5 = new ArrayList<>();
   private String text = "";
   private long timestamp;
   private RotationBuilderScreen.RotationBuilderScreenState rotationBuilderScreenState17 = RotationBuilderScreen.RotationBuilderScreenState.resolve();
   private RotationBuilderScreen.RotationBuilderScreenState rotationBuilderScreenState18 = RotationBuilderScreen.RotationBuilderScreenState.resolve();
   private RotationBuilderScreen.RotationBuilderScreenState rotationBuilderScreenState19 = RotationBuilderScreen.RotationBuilderScreenState.resolve();
   private RotationBuilderScreen.RotationBuilderScreenState rotationBuilderScreenState20 = RotationBuilderScreen.RotationBuilderScreenState.resolve();
   private RotationBuilderScreen.RotationBuilderScreenState rotationBuilderScreenState21 = RotationBuilderScreen.RotationBuilderScreenState.resolve();
   private RotationBuilderScreen.RotationBuilderScreenState rotationBuilderScreenState22 = RotationBuilderScreen.RotationBuilderScreenState.resolve();
   private RotationBuilderScreen.RotationBuilderScreenState rotationBuilderScreenState23 = RotationBuilderScreen.RotationBuilderScreenState.resolve();
   private float floatValue;
   private float floatValue2;
   private float floatValue3;
   private float floatValue4;
   private RotationBuilderScreen.RotationBuilderScreenUiState rotationBuilderScreenUiState;
   private int intValue2 = -1;
   private int intValue3 = -1;
   private float floatValue5;
   private float floatValue6;
   private float floatValue7;
   private float floatValue8;
   private float floatValue9;
   private float floatValue10;
   private float floatValue11;
   private float floatValue12;
   private boolean flag2;
   private long timestamp2;
   private int intValue4;
   private long timestamp3;
   private boolean flag3;
   private boolean flag4;
   private boolean flag5;
   private boolean flag6;
   private String text2 = "";
   private String text3;
   private float floatValue13;
   private float floatValue14;

   public RotationBuilderScreen() {
      super(Text.literal("Rotation Builder"));
      this.rotationPresetManager = RotationPresetManager.resolve();
      this.rotationPresetStore = RotationPresetStore.getINSTANCE();
      this.animation.invoke(0.0);
      this.animation2.invoke(1.0);
      this.animation3.invoke(0.0);
      this.animation4.invoke(0.0);
      invoke39();
      this.invoke();
   }

   private void invoke() {
      this.items2.clear();
      this.items2
         .add(
            new RotationBuilderScreen.RotationBuilderScreenUiState(
               "Скорость Yaw мин", 0.0F, 180.0F, 35.0F, true, () -> this.rotationPresetManager.floatValue, f -> this.rotationPresetManager.floatValue = f
            )
         );
      this.items2
         .add(
            new RotationBuilderScreen.RotationBuilderScreenUiState(
               "Скорость Yaw макс", 0.0F, 180.0F, 55.0F, true, () -> this.rotationPresetManager.floatValue2, f -> this.rotationPresetManager.floatValue2 = f
            )
         );
      this.items2
         .add(
            new RotationBuilderScreen.RotationBuilderScreenUiState(
               "Скорость Pitch мин", 0.0F, 120.0F, 6.0F, true, () -> this.rotationPresetManager.floatValue3, f -> this.rotationPresetManager.floatValue3 = f
            )
         );
      this.items2
         .add(
            new RotationBuilderScreen.RotationBuilderScreenUiState(
               "Скорость Pitch макс", 0.0F, 120.0F, 12.0F, true, () -> this.rotationPresetManager.floatValue4, f -> this.rotationPresetManager.floatValue4 = f
            )
         );
      this.items2
         .add(
            new RotationBuilderScreen.RotationBuilderScreenUiState(
               "Скорость удара Yaw", 0.0F, 240.0F, 65.0F, true, () -> this.rotationPresetManager.floatValue5, f -> this.rotationPresetManager.floatValue5 = f
            )
         );
      this.items2
         .add(
            new RotationBuilderScreen.RotationBuilderScreenUiState(
               "Скорость удара Pitch", 0.0F, 240.0F, 22.0F, true, () -> this.rotationPresetManager.floatValue6, f -> this.rotationPresetManager.floatValue6 = f
            )
         );
      this.items2
         .add(
            new RotationBuilderScreen.RotationBuilderScreenUiState(
               "Рандом Yaw", 0.0F, 20.0F, 4.0F, false, () -> this.rotationPresetManager.floatValue7, f -> this.rotationPresetManager.floatValue7 = f
            )
         );
      this.items2
         .add(
            new RotationBuilderScreen.RotationBuilderScreenUiState("Рандом Pitch", 0.0F, 20.0F, 3.0F, false, () -> this.rotationPresetManager.floatValue8, f -> this.rotationPresetManager.floatValue8 = f)
         );
      this.items2
         .add(
            new RotationBuilderScreen.RotationBuilderScreenUiState("Осцилляция X", 0.0F, 1.0F, 0.2F, false, () -> this.rotationPresetManager.floatValue9, f -> this.rotationPresetManager.floatValue9 = f)
         );
      this.items2
         .add(
            new RotationBuilderScreen.RotationBuilderScreenUiState(
               "Осцилляция Y", 0.0F, 1.0F, 0.12F, false, () -> this.rotationPresetManager.floatValue10, f -> this.rotationPresetManager.floatValue10 = f
            )
         );
      this.items2
         .add(
            new RotationBuilderScreen.RotationBuilderScreenUiState(
               "Частота осцилляции", 0.2F, 3.0F, 1.0F, false, () -> this.rotationPresetManager.floatValue11, f -> this.rotationPresetManager.floatValue11 = f
            )
         );
      this.items2
         .add(
            new RotationBuilderScreen.RotationBuilderScreenUiState(
               "Боковая точка", 0.0F, 0.6F, 0.0F, false, () -> this.rotationPresetManager.floatValue12, f -> this.rotationPresetManager.floatValue12 = f
            )
         );
      this.items2
         .add(
            new RotationBuilderScreen.RotationBuilderScreenUiState(
               "Скорость возврата", 5.0F, 120.0F, 30.0F, true, () -> this.rotationPresetManager.floatValue13, f -> this.rotationPresetManager.floatValue13 = f
            )
         );
      this.items2
         .add(
            new RotationBuilderScreen.RotationBuilderScreenUiState(
               "Смена точки (сек)", 0.1F, 3.0F, 0.9F, false, () -> this.rotationPresetManager.floatValue14, f -> this.rotationPresetManager.floatValue14 = f
            )
         );
      this.items2
         .add(
            new RotationBuilderScreen.RotationBuilderScreenUiState(
               "Скорость смены точек", 0.1F, 3.0F, 1.0F, false, () -> this.rotationPresetManager.floatValue22, f -> this.rotationPresetManager.floatValue22 = f
            )
         );
      this.items2
         .add(
            new RotationBuilderScreen.RotationBuilderScreenUiState(
               "Интерполяция оверлея", 0.05F, 1.0F, 0.35F, false, () -> this.rotationPresetManager.floatValue20, f -> this.rotationPresetManager.floatValue20 = f
            )
         );
      this.items2
         .add(
            new RotationBuilderScreen.RotationBuilderScreenUiState(
               "Скорость прицела", 0.2F, 3.0F, 1.0F, false, () -> this.rotationPresetManager.floatValue21, f -> this.rotationPresetManager.floatValue21 = f
            )
         );
      this.items3.clear();
      this.items3
         .add(
            new RotationBuilderScreen.RotationBuilderScreenUiState(
               "Смещение Yaw", -30.0F, 30.0F, 0.0F, true, () -> this.rotationPresetManager.floatValue15, f -> this.rotationPresetManager.floatValue15 = f
            )
         );
      this.items3
         .add(
            new RotationBuilderScreen.RotationBuilderScreenUiState(
               "Смещение Pitch", -30.0F, 30.0F, 0.0F, true, () -> this.rotationPresetManager.floatValue16, f -> this.rotationPresetManager.floatValue16 = f
            )
         );
      this.items3
         .add(
            new RotationBuilderScreen.RotationBuilderScreenUiState(
               "Pitch минимум", -90.0F, 0.0F, -90.0F, true, () -> this.rotationPresetManager.floatValue17, f -> this.rotationPresetManager.floatValue17 = f
            )
         );
      this.items3
         .add(
            new RotationBuilderScreen.RotationBuilderScreenUiState(
               "Pitch максимум", 0.0F, 90.0F, 90.0F, true, () -> this.rotationPresetManager.floatValue18, f -> this.rotationPresetManager.floatValue18 = f
            )
         );
      this.items3
         .add(
            new RotationBuilderScreen.RotationBuilderScreenUiState(
               "Упреждение цели", 0.0F, 0.6F, 0.0F, false, () -> this.rotationPresetManager.floatValue19, f -> this.rotationPresetManager.floatValue19 = f
            )
         );
      this.items3
         .add(
            new RotationBuilderScreen.RotationBuilderScreenUiState(
               "Угол отвода", 0.0F, 90.0F, 80.0F, true, () -> this.rotationPresetManager.floatValue23, f -> this.rotationPresetManager.floatValue23 = f
            )
         );
      this.items3
         .add(
            new RotationBuilderScreen.RotationBuilderScreenUiState(
               "Интервал отвода (сек)", 1.5F, 15.0F, 5.0F, false, () -> this.rotationPresetManager.floatValue24, f -> this.rotationPresetManager.floatValue24 = f
            )
         );
      this.items = this.items2;
   }

   public boolean shouldPause() {
      return false;
   }

   public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
      this.invoke40(this.measure6((double)mouseX), this.measure7((double)mouseY));
      super.render(context, mouseX, mouseY, deltaTicks);
   }

   public void renderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
   }

   public void renderInGameBackground(DrawContext context) {
   }

   public void invoke2(RenderManager renderManager, int i, int j) {
      if (renderManager != null && i > 0 && j > 0) {
         this.invoke41();
         this.invoke37();
         this.animation.check();
         this.animation
            .resolve4(
               this.flag5 ? 0.0 : 1.0,
               this.flag5 ? 0.18F : 0.22F,
               this.flag5 ? LegacyEasingFunctions.LEGACY_EASING_FUNCTION_8 : LegacyEasingFunctions.LEGACY_EASING_FUNCTION_27,
               false
            );
         float floatValue = measure8(this.animation.measure3(), 0.0F, 1.0F);
         renderManager.invoke5(0.0F, 0.0F, (float)i, (float)j, 0.0F, compute3(0, 0, 0, Math.round(150.0F * floatValue)));
         renderManager.invoke65(floatValue);
         float floatValue2 = 0.97F + 0.03F * floatValue;
         renderManager.invoke63(floatValue2, floatValue2, i * 0.5F, j * 0.5F);
         this.invoke3(renderManager, i, j);
         this.invoke4(renderManager);
         this.invoke10(renderManager, this.measure());
         this.invoke16(renderManager);
         this.invoke7(renderManager);
         renderManager.invoke64();
         renderManager.invoke66();
         if (this.flag5 && floatValue <= 0.015F) {
            this.invoke21();
         }
      }
   }

   private float measure() {
      long longValue = System.currentTimeMillis();
      if (this.timestamp2 == 0L) {
         this.timestamp2 = longValue;
      }

      float floatValue3 = (float)(longValue - this.timestamp2) / 1000.0F;
      this.timestamp2 = longValue;
      return Math.min(0.1F, Math.max(0.0F, floatValue3));
   }

   private void invoke3(RenderManager renderManager2, int i, int j) {
      float floatValue4 = measure8(i - 120.0F, 620.0F, 780.0F);
      float floatValue5 = measure8(j - 120.0F, 420.0F, 520.0F);
      float floatValue6 = (i - floatValue4) * 0.5F;
      float floatValue7 = (j - floatValue5) * 0.5F;
      this.rotationBuilderScreenState = new RotationBuilderScreen.RotationBuilderScreenState(floatValue6, floatValue7, floatValue4, floatValue5);
      renderManager2.invoke48(20.0F);
      renderManager2.invoke44(floatValue6, floatValue7, floatValue4, floatValue5, 16.0F, 1.0F);
      renderManager2.invoke5(floatValue6, floatValue7, floatValue4, floatValue5, 16.0F, compute3(13, 15, 21, 180));
      renderManager2.invoke28(floatValue6, floatValue7, floatValue4, floatValue5, 16.0F, compute3(255, 255, 255, 26), 2.0F);
   }

   private void invoke4(RenderManager renderManager3) {
      float floatValue8 = this.rotationBuilderScreenState.floatValue + 18.0F;
      float floatValue9 = this.rotationBuilderScreenState.floatValue2 + 16.0F;
      renderManager3.invoke69(FontRegistry.fontObject4, floatValue8, floatValue9 + 14.0F, 26.0F, "Rotation Builder", compute3(245, 248, 255, 246));
      boolean flag = System.currentTimeMillis() < this.timestamp && !this.text.isEmpty();
      if (flag) {
         float floatValue10 = measure8(this.animation3.measure3(), 0.0F, 1.0F);
         long longValue2 = this.timestamp - System.currentTimeMillis();
         if (longValue2 < 400L) {
            floatValue10 *= measure8((float)longValue2 / 400.0F, 0.0F, 1.0F);
         }

         renderManager3.invoke69(FontRegistry.fontObject, floatValue8, floatValue9 + 34.0F, 22.0F, this.text, compute3(120, 220, 150, Math.round(235.0F * floatValue10)));
      } else {
         renderManager3.invoke69(
            FontRegistry.fontObject,
            floatValue8,
            floatValue9 + 32.0F,
            24.0F,
            "Текущий присет ротации: " + this.rotationPresetManager.funtime3 + " - " + this.rotationPresetManager.funtime2,
            compute3(154, 164, 180, 222)
         );
      }

      this.items4.clear();
      float floatValue11 = floatValue8;
      float floatValue12 = this.rotationBuilderScreenState.floatValue2 + 58.0F;

      for (String text : FUNTIME) {
         float floatValue13 = TextMeasureCache.measure(FontRegistry.fontObject, text, 14.0F) + 18.0F;
         boolean flag2 = text.equals(this.rotationPresetManager.funtime3)
            || text.equals(this.rotationPresetManager.funtime)
            || "Custom".equals(text) && "Custom".equals(this.rotationPresetManager.funtime2);
         this.items4.add(new RotationBuilderScreen.RotationBuilderScreenBounds(text, new RotationBuilderScreen.RotationBuilderScreenState(floatValue11, floatValue12, floatValue13, 24.0F), flag2));
         floatValue11 += floatValue13 + 7.0F;
      }

      for (RotationBuilderScreen.RotationBuilderScreenBounds rotationBuilderScreenBounds : this.items4) {
         float floatValue14 = this.measure2("chip." + rotationBuilderScreenBounds.label, rotationBuilderScreenBounds.bounds.check(this.floatValue7, this.floatValue8));
         float floatValue15 = this.measure4("chip." + rotationBuilderScreenBounds.label, rotationBuilderScreenBounds.active);
         float floatValue16 = this.measure3("chip." + rotationBuilderScreenBounds.label);
         float floatValue17 = 1.0F - floatValue16 * 0.06F;
         float floatValue18 = rotationBuilderScreenBounds.bounds.floatValue + rotationBuilderScreenBounds.bounds.floatValue3 * 0.5F;
         float floatValue19 = rotationBuilderScreenBounds.bounds.floatValue2 + rotationBuilderScreenBounds.bounds.floatValue4 * 0.5F;
         int intValue = compute2(compute3(255, 255, 255, Math.round(12.0F + floatValue14 * 18.0F)), compute3(95, 190, 255, 64), floatValue15);
         renderManager3.invoke63(floatValue17, floatValue17, floatValue18, floatValue19);
         renderManager3.invoke5(rotationBuilderScreenBounds.bounds.floatValue, rotationBuilderScreenBounds.bounds.floatValue2, rotationBuilderScreenBounds.bounds.floatValue3, rotationBuilderScreenBounds.bounds.floatValue4, 7.0F, intValue);
         float floatValue20 = TextMeasureCache.measure(FontRegistry.fontObject, rotationBuilderScreenBounds.label, 20.0F);
         int intValue2 = compute2(compute3(188, 198, 212, 226), compute3(235, 248, 255, 246), floatValue15);
         renderManager3.invoke69(
            FontRegistry.fontObject,
            rotationBuilderScreenBounds.bounds.floatValue + (rotationBuilderScreenBounds.bounds.floatValue3 - floatValue20) * 0.5F,
            rotationBuilderScreenBounds.bounds.floatValue2 + 16.0F,
            20.0F,
            rotationBuilderScreenBounds.label,
            intValue2
         );
         renderManager3.invoke64();
      }

      this.rotationBuilderScreenState5 = new RotationBuilderScreen.RotationBuilderScreenState(this.rotationBuilderScreenState.floatValue + this.rotationBuilderScreenState.floatValue3 - 18.0F - 26.0F, floatValue9, 26.0F, 26.0F);
      this.invoke5(renderManager3, "screen.close", this.rotationBuilderScreenState5, "l", FontRegistry.fontObject5, 26.0F, false);
      this.rotationBuilderScreenState10 = new RotationBuilderScreen.RotationBuilderScreenState(this.rotationBuilderScreenState5.floatValue - 8.0F - 26.0F, floatValue9, 26.0F, 26.0F);
      this.invoke5(renderManager3, "presets.open", this.rotationBuilderScreenState10, "I", FontRegistry.fontObject3, 18.0F, this.flag3);
      float floatValue21 = 104.0F;
      this.rotationBuilderScreenState6 = new RotationBuilderScreen.RotationBuilderScreenState(this.rotationBuilderScreenState.floatValue + this.rotationBuilderScreenState.floatValue3 - 18.0F - floatValue21, floatValue12, floatValue21, 24.0F);
      this.invoke6(
         renderManager3,
         "clear",
         this.rotationBuilderScreenState6,
         "Очистить точки",
         compute3(255, 120, 120, 26),
         compute3(255, 120, 120, 70),
         compute3(245, 220, 220, 232)
      );
      this.rotationBuilderScreenState7 = new RotationBuilderScreen.RotationBuilderScreenState(this.rotationBuilderScreenState6.floatValue - 8.0F - 78.0F, floatValue12, 78.0F, 24.0F);
      this.invoke6(
         renderManager3, "reset", this.rotationBuilderScreenState7, "Сброс", compute3(255, 255, 255, 14), compute3(255, 255, 255, 36), compute3(235, 242, 255, 230)
      );
      this.rotationBuilderScreenState9 = new RotationBuilderScreen.RotationBuilderScreenState(this.rotationBuilderScreenState7.floatValue - 8.0F - 92.0F, floatValue12, 92.0F, 24.0F);
      this.invoke6(
         renderManager3, "paste", this.rotationBuilderScreenState9, "Вставить", compute3(120, 200, 255, 22), compute3(120, 200, 255, 66), compute3(225, 240, 255, 232)
      );
      this.rotationBuilderScreenState8 = new RotationBuilderScreen.RotationBuilderScreenState(this.rotationBuilderScreenState9.floatValue - 8.0F - 100.0F, floatValue12, 100.0F, 24.0F);
      this.invoke6(
         renderManager3, "copy", this.rotationBuilderScreenState8, "Копировать", compute3(120, 255, 180, 22), compute3(120, 255, 180, 66), compute3(225, 255, 240, 232)
      );
   }

   private void invoke5(
      RenderManager renderManager4, String string, RotationBuilderScreen.RotationBuilderScreenState rotationBuilderScreenState, String string2, FontObject fontObject, float f, boolean bl
   ) {
      float floatValue22 = this.measure2(string, rotationBuilderScreenState.check(this.floatValue7, this.floatValue8));
      float floatValue23 = this.measure4(string, bl);
      float floatValue24 = this.measure3(string);
      float floatValue25 = 1.0F - floatValue24 * 0.08F;
      int intValue3 = compute2(compute3(255, 255, 255, Math.round(10.0F + floatValue22 * 18.0F)), compute3(95, 190, 255, 62), floatValue23);
      int intValue4 = compute2(compute3(255, 255, 255, 22), compute3(95, 210, 255, 112), floatValue23);
      renderManager4.invoke63(floatValue25, floatValue25, rotationBuilderScreenState.floatValue + rotationBuilderScreenState.floatValue3 * 0.5F, rotationBuilderScreenState.floatValue2 + rotationBuilderScreenState.floatValue4 * 0.5F);
      renderManager4.invoke5(rotationBuilderScreenState.floatValue, rotationBuilderScreenState.floatValue2, rotationBuilderScreenState.floatValue3, rotationBuilderScreenState.floatValue4, 8.0F, intValue3);
      float floatValue26 = TextMeasureCache.measure(fontObject, string2, f);
      renderManager4.invoke69(
         fontObject,
         rotationBuilderScreenState.floatValue + (rotationBuilderScreenState.floatValue3 - floatValue26) * 0.5F,
         rotationBuilderScreenState.floatValue2 + rotationBuilderScreenState.floatValue4 * 0.5F + f * 0.28F,
         f,
         string2,
         compute3(226, 239, 250, Math.round(224.0F + floatValue22 * 26.0F))
      );
      renderManager4.invoke64();
   }

   private void invoke6(RenderManager renderManager5, String string, RotationBuilderScreen.RotationBuilderScreenState rotationBuilderScreenState2, String string2, int i, int j, int k) {
      float floatValue27 = this.measure2(string, rotationBuilderScreenState2.check(this.floatValue7, this.floatValue8));
      float floatValue28 = this.measure3(string);
      float floatValue29 = 1.0F - floatValue28 * 0.07F;
      float floatValue30 = rotationBuilderScreenState2.floatValue + rotationBuilderScreenState2.floatValue3 * 0.5F;
      float floatValue31 = rotationBuilderScreenState2.floatValue2 + rotationBuilderScreenState2.floatValue4 * 0.5F;
      int intValue5 = compute2(i, j, floatValue27);
      renderManager5.invoke63(floatValue29, floatValue29, floatValue30, floatValue31);
      renderManager5.invoke5(rotationBuilderScreenState2.floatValue, rotationBuilderScreenState2.floatValue2, rotationBuilderScreenState2.floatValue3, rotationBuilderScreenState2.floatValue4, 7.0F, intValue5);
      this.invoke36(renderManager5, string2, rotationBuilderScreenState2, 22.0F, k);
      renderManager5.invoke64();
   }

   private void invoke7(RenderManager renderManager6) {
      float floatValue32 = measure8(this.animation4.measure3(), 0.0F, 1.0F);
      if (floatValue32 <= 0.01F) {
         this.rotationBuilderScreenState11 = RotationBuilderScreen.RotationBuilderScreenState.resolve();
         this.items5.clear();
      } else {
         float floatValue33 = Math.min(336.0F, this.rotationBuilderScreenState.floatValue3 - 36.0F);
         float floatValue34 = this.rotationBuilderScreenState.floatValue4 - 72.0F;
         float floatValue35 = this.rotationBuilderScreenState.floatValue + this.rotationBuilderScreenState.floatValue3 - floatValue33 - 18.0F;
         float floatValue36 = this.rotationBuilderScreenState.floatValue2 + 54.0F;
         float floatValue37 = 1.0F - (float)Math.pow(1.0F - floatValue32, 3.0);
         float floatValue38 = floatValue36 - 28.0F * (1.0F - floatValue37);
         this.rotationBuilderScreenState11 = new RotationBuilderScreen.RotationBuilderScreenState(floatValue35, floatValue38, floatValue33, floatValue34);
         renderManager6.invoke5(
            this.rotationBuilderScreenState.floatValue,
            this.rotationBuilderScreenState.floatValue2,
            this.rotationBuilderScreenState.floatValue3,
            this.rotationBuilderScreenState.floatValue4,
            16.0F,
            compute3(13, 15, 21, Math.round(180.0F * floatValue32))
         );
         renderManager6.invoke65(floatValue32);
         renderManager6.invoke5(floatValue35, floatValue38, floatValue33, floatValue34, 14.0F, compute3(13, 15, 21, 255));
         float floatValue39 = 14.0F;
         renderManager6.invoke69(FontRegistry.fontObject4, floatValue35 + floatValue39, floatValue38 + 23.0F, 24.0F, "Сохранённые ротации", compute3(242, 247, 255, 246));
         renderManager6.invoke69(
            FontRegistry.fontObject, floatValue35 + floatValue39, floatValue38 + 41.0F, 22.0F, "Локальные пресеты текущего конструктора", compute3(142, 154, 174, 210)
         );
         this.rotationBuilderScreenState12 = new RotationBuilderScreen.RotationBuilderScreenState(floatValue35 + floatValue33 - floatValue39 - 24.0F, floatValue38 + 12.0F, 24.0F, 26.0F);
         this.invoke5(renderManager6, "presets.close", this.rotationBuilderScreenState12, "l", FontRegistry.fontObject5, 26.0F, false);
         this.rotationBuilderScreenState13 = new RotationBuilderScreen.RotationBuilderScreenState(floatValue35 + floatValue39, floatValue38 + 54.0F, floatValue33 - floatValue39 * 2.0F, 30.0F);
         float floatValue40 = this.measure2("presets.name", this.rotationBuilderScreenState13.check(this.floatValue7, this.floatValue8));
         float floatValue41 = this.measure4("presets.name.active", this.flag4);
         int intValue6 = compute2(compute3(255, 255, 255, Math.round(10.0F + floatValue40 * 8.0F)), compute3(95, 190, 255, 28), floatValue41);
         int intValue7 = compute2(compute3(255, 255, 255, 24), compute3(95, 210, 255, 124), floatValue41);
         renderManager6.invoke5(
            this.rotationBuilderScreenState13.floatValue, this.rotationBuilderScreenState13.floatValue2, this.rotationBuilderScreenState13.floatValue3, this.rotationBuilderScreenState13.floatValue4, 8.0F, intValue6
         );
         String text2 = this.text2.isEmpty() && !this.flag4 ? "Название пресета" : this.text2;
         int intValue8 = this.text2.isEmpty() && !this.flag4 ? compute3(128, 140, 158, 190) : compute3(229, 238, 250, 236);
         String text3 = this.resolve(text2, this.rotationBuilderScreenState13.floatValue3 - 22.0F, 21.0F);
         renderManager6.invoke69(FontRegistry.fontObject, this.rotationBuilderScreenState13.floatValue + 10.0F, this.rotationBuilderScreenState13.floatValue2 + 20.0F, 21.0F, text3, intValue8);
         if (this.flag4 && System.currentTimeMillis() / 480L % 2L == 0L) {
            float floatValue42 = this.rotationBuilderScreenState13.floatValue + 10.0F + TextMeasureCache.measure(FontRegistry.fontObject, text3, 21.0F) + 1.0F;
            renderManager6.invoke5(floatValue42, this.rotationBuilderScreenState13.floatValue2 + 7.0F, 1.0F, 16.0F, 0.5F, compute3(110, 215, 255, 230));
         }

         float floatValue43 = floatValue38 + 92.0F;
         float floatValue44 = (floatValue33 - floatValue39 * 2.0F - 8.0F) * 0.5F;
         this.rotationBuilderScreenState14 = new RotationBuilderScreen.RotationBuilderScreenState(floatValue35 + floatValue39, floatValue43, floatValue44, 28.0F);
         this.rotationBuilderScreenState15 = new RotationBuilderScreen.RotationBuilderScreenState(this.rotationBuilderScreenState14.floatValue + floatValue44 + 8.0F, floatValue43, floatValue44, 28.0F);
         this.invoke6(
            renderManager6,
            "presets.create",
            this.rotationBuilderScreenState14,
            "Сохранить новый",
            compute3(95, 210, 255, 22),
            compute3(95, 210, 255, 62),
            compute3(228, 247, 255, 238)
         );
         renderManager6.invoke65(this.text3 == null ? 0.42F : 1.0F);
         this.invoke6(
            renderManager6,
            "presets.update",
            this.rotationBuilderScreenState15,
            "Обновить",
            compute3(120, 255, 180, 18),
            compute3(120, 255, 180, 54),
            compute3(226, 255, 240, 232)
         );
         renderManager6.invoke66();
         float floatValue45 = floatValue38 + 132.0F;
         float floatValue46 = floatValue38 + floatValue34 - floatValue39;
         this.rotationBuilderScreenState16 = new RotationBuilderScreen.RotationBuilderScreenState(floatValue35 + floatValue39, floatValue45, floatValue33 - floatValue39 * 2.0F, Math.max(20.0F, floatValue46 - floatValue45));
         renderManager6.invoke24(
            this.rotationBuilderScreenState16.floatValue, this.rotationBuilderScreenState16.floatValue2, this.rotationBuilderScreenState16.floatValue3, this.rotationBuilderScreenState16.floatValue4, 8.0F, 8.0F, 8.0F, 8.0F
         );
         List items = this.rotationPresetStore.resolve();
         this.items5.clear();
         float floatValue47 = 58.0F;
         float floatValue48 = 8.0F;
         float floatValue49 = floatValue45 - this.floatValue13;
         if (items.isEmpty()) {
            renderManager6.invoke5(
               this.rotationBuilderScreenState16.floatValue, this.rotationBuilderScreenState16.floatValue2, this.rotationBuilderScreenState16.floatValue3, 64.0F, 10.0F, compute3(255, 255, 255, 8)
            );
            this.invoke36(
               renderManager6,
               "Сохранённых пресетов пока нет",
               new RotationBuilderScreen.RotationBuilderScreenState(this.rotationBuilderScreenState16.floatValue, this.rotationBuilderScreenState16.floatValue2, this.rotationBuilderScreenState16.floatValue3, 64.0F),
               24.0F,
               compute3(145, 157, 176, 206)
            );
         } else {
            for (RotationPresetStore.RotationPresetStoreTimedEntry rotationPresetStoreTimedEntry : (List<RotationPresetStore.RotationPresetStoreTimedEntry>)items) {
               RotationBuilderScreen.RotationBuilderScreenState rotationBuilderScreenState3 = new RotationBuilderScreen.RotationBuilderScreenState(this.rotationBuilderScreenState16.floatValue, floatValue49, this.rotationBuilderScreenState16.floatValue3, floatValue47);
               boolean flag3 = rotationPresetStoreTimedEntry.id().equals(this.text3);
               float floatValue50 = this.measure2("preset.row." + rotationPresetStoreTimedEntry.id(), rotationBuilderScreenState3.check(this.floatValue7, this.floatValue8));
               float floatValue51 = this.measure4("preset.row.active." + rotationPresetStoreTimedEntry.id(), flag3);
               int intValue9 = compute2(compute3(255, 255, 255, Math.round(12.0F + floatValue50 * 13.0F)), compute3(95, 190, 255, 34), floatValue51);
               renderManager6.invoke5(rotationBuilderScreenState3.floatValue, rotationBuilderScreenState3.floatValue2, rotationBuilderScreenState3.floatValue3, rotationBuilderScreenState3.floatValue4, 10.0F, intValue9);
               String text4 = this.resolve(rotationPresetStoreTimedEntry.name(), rotationBuilderScreenState3.floatValue3 - 154.0F, 24.0F);
               renderManager6.invoke69(
                  FontRegistry.fontObject4,
                  rotationBuilderScreenState3.floatValue + 11.0F,
                  rotationBuilderScreenState3.floatValue2 + 25.0F,
                  24.0F,
                  text4,
                  flag3 ? compute3(231, 248, 255, 246) : compute3(218, 227, 240, 232)
               );
               renderManager6.invoke69(
                  FontRegistry.fontObject,
                  rotationBuilderScreenState3.floatValue + 11.0F,
                  rotationBuilderScreenState3.floatValue2 + 40.0F,
                  20.0F,
                  flag3 ? "Выбран для редактирования" : "Нажмите, чтобы выбрать",
                  compute3(135, 149, 169, 196)
               );
               RotationBuilderScreen.RotationBuilderScreenState rotationBuilderScreenState4 = new RotationBuilderScreen.RotationBuilderScreenState(
                  rotationBuilderScreenState3.floatValue + rotationBuilderScreenState3.floatValue3 - 128.0F, rotationBuilderScreenState3.floatValue2 + 9.0F, 72.0F, 22.0F
               );
               RotationBuilderScreen.RotationBuilderScreenState rotationBuilderScreenState5 = new RotationBuilderScreen.RotationBuilderScreenState(
                  rotationBuilderScreenState3.floatValue + rotationBuilderScreenState3.floatValue3 - 50.0F, rotationBuilderScreenState3.floatValue2 + 9.0F, 20.0F, 22.0F
               );
               RotationBuilderScreen.RotationBuilderScreenState rotationBuilderScreenState6 = new RotationBuilderScreen.RotationBuilderScreenState(
                  rotationBuilderScreenState3.floatValue + rotationBuilderScreenState3.floatValue3 - 24.0F, rotationBuilderScreenState3.floatValue2 + 9.0F, 20.0F, 22.0F
               );
               this.invoke8(renderManager6, "preset.apply." + rotationPresetStoreTimedEntry.id(), rotationBuilderScreenState4, "Применить", false);
               this.invoke9(renderManager6, "preset.copy." + rotationPresetStoreTimedEntry.id(), rotationBuilderScreenState5, "k", FontRegistry.fontObject3, 18.0F, false);
               this.invoke9(renderManager6, "preset.delete." + rotationPresetStoreTimedEntry.id(), rotationBuilderScreenState6, "l", FontRegistry.fontObject5, 20.0F, true);
               this.items5.add(new RotationBuilderScreen.RotationBuilderScreenData(rotationPresetStoreTimedEntry, rotationBuilderScreenState3, rotationBuilderScreenState4, rotationBuilderScreenState5, rotationBuilderScreenState6));
               floatValue49 += floatValue47 + floatValue48;
            }
         }

         renderManager6.invoke25();
         float floatValue52 = items.isEmpty() ? 64.0F : items.size() * (floatValue47 + floatValue48) - floatValue48;
         this.floatValue14 = Math.max(0.0F, floatValue52 - this.rotationBuilderScreenState16.floatValue4);
         this.floatValue13 = measure8(this.floatValue13, 0.0F, this.floatValue14);
         if (this.floatValue14 > 0.0F) {
            float floatValue53 = this.rotationBuilderScreenState16.floatValue4;
            float floatValue54 = Math.max(30.0F, floatValue53 * (floatValue53 / (floatValue53 + this.floatValue14)));
            float floatValue55 = this.rotationBuilderScreenState16.floatValue2 + (floatValue53 - floatValue54) * (this.floatValue13 / this.floatValue14);
            renderManager6.invoke5(
               this.rotationBuilderScreenState16.floatValue + this.rotationBuilderScreenState16.floatValue3 - 3.0F, this.rotationBuilderScreenState16.floatValue2, 2.0F, floatValue53, 1.0F, compute3(255, 255, 255, 14)
            );
            renderManager6.invoke5(this.rotationBuilderScreenState16.floatValue + this.rotationBuilderScreenState16.floatValue3 - 3.0F, floatValue55, 2.0F, floatValue54, 1.0F, compute3(95, 210, 255, 116));
         }

         renderManager6.invoke66();
      }
   }

   private void invoke8(RenderManager renderManager7, String string, RotationBuilderScreen.RotationBuilderScreenState rotationBuilderScreenState7, String string2, boolean bl) {
      this.invoke9(renderManager7, string, rotationBuilderScreenState7, string2, FontRegistry.fontObject, string2.length() > 2 ? 16.0F : 19.0F, bl);
   }

   private void invoke9(
      RenderManager renderManager8, String string, RotationBuilderScreen.RotationBuilderScreenState rotationBuilderScreenState8, String string2, FontObject fontObject2, float f, boolean bl
   ) {
      float floatValue56 = this.measure2(string, rotationBuilderScreenState8.check(this.floatValue7, this.floatValue8));
      float floatValue57 = this.measure3(string);
      int intValue10 = bl ? compute3(255, 105, 120, 18) : compute3(255, 255, 255, 12);
      int intValue11 = bl ? compute3(255, 105, 120, 54) : compute3(95, 210, 255, 42);
      int intValue12 = bl ? compute3(255, 204, 210, 232) : compute3(218, 235, 248, 226);
      renderManager8.invoke63(
         1.0F - floatValue57 * 0.08F, 1.0F - floatValue57 * 0.08F, rotationBuilderScreenState8.floatValue + rotationBuilderScreenState8.floatValue3 * 0.5F, rotationBuilderScreenState8.floatValue2 + rotationBuilderScreenState8.floatValue4 * 0.5F
      );
      renderManager8.invoke5(rotationBuilderScreenState8.floatValue, rotationBuilderScreenState8.floatValue2, rotationBuilderScreenState8.floatValue3, rotationBuilderScreenState8.floatValue4, 6.0F, compute2(intValue10, intValue11, floatValue56));
      float floatValue58 = TextMeasureCache.measure(fontObject2, string2, f);
      renderManager8.invoke69(
         fontObject2,
         rotationBuilderScreenState8.floatValue + (rotationBuilderScreenState8.floatValue3 - floatValue58) * 0.5F,
         rotationBuilderScreenState8.floatValue2 + rotationBuilderScreenState8.floatValue4 * 0.5F + f * 0.28F,
         f,
         string2,
         intValue12
      );
      renderManager8.invoke64();
   }

   private String resolve(String string, float f, float g) {
      if (string != null && !string.isEmpty()) {
         String text5 = string;

         while (text5.length() > 1 && TextMeasureCache.measure(FontRegistry.fontObject, text5, g) > f) {
            text5 = text5.substring(1);
         }

         return text5;
      } else {
         return "";
      }
   }

   private void invoke10(RenderManager renderManager9, float f) {
      float floatValue59 = this.rotationBuilderScreenState.floatValue2 + 90.0F;
      float floatValue60 = this.rotationBuilderScreenState.floatValue + 18.0F;
      float floatValue61 = 238.0F;
      float floatValue62 = this.rotationBuilderScreenState.floatValue2 + this.rotationBuilderScreenState.floatValue4 - floatValue59 - 18.0F;
      this.rotationBuilderScreenState2 = new RotationBuilderScreen.RotationBuilderScreenState(floatValue60, floatValue59, floatValue61, floatValue62);
      renderManager9.invoke5(floatValue60, floatValue59, floatValue61, floatValue62, 14.0F, compute3(255, 255, 255, 10));
      renderManager9.invoke28(floatValue60, floatValue59, floatValue61, floatValue62, 14.0F, compute3(255, 255, 255, 20), 2.0F);
      float floatValue63 = measure8(this.animation2.measure3(), 0.0F, 1.0F);
      renderManager9.invoke65(floatValue63);
      renderManager9.invoke69(
         FontRegistry.fontObject,
         floatValue60 + 12.0F,
         floatValue59 + 18.0F,
         24.0F,
         this.intValue == 0 ? "Привью режим поведение ротации" : "Превью вектора",
         compute3(176, 186, 202, 224)
      );
      float floatValue64 = floatValue59 + 30.0F;
      float floatValue65 = floatValue62 - 46.0F;
      this.floatValue4 = floatValue65 * 0.82F;
      this.floatValue3 = this.floatValue4 * 0.42F;
      this.floatValue = floatValue60 + floatValue61 * 0.5F;
      this.floatValue2 = floatValue64 + floatValue65 - 10.0F;
      if (this.intValue == 0) {
         this.invoke15(f);
      } else {
         this.invoke12(f);
      }

      RotationPresetEditorState.invoke(
         renderManager9,
         floatValue60 + 4.0F,
         floatValue64,
         floatValue61 - 8.0F,
         floatValue65,
         this.floatValue,
         this.floatValue2,
         this.floatValue3,
         this.floatValue4,
         this.intValue == 0 ? 1.0F : 0.55F
      );
      if (this.intValue == 0) {
         this.invoke14(renderManager9);
         this.invoke13(renderManager9);
      } else {
         this.invoke11(renderManager9, floatValue60, floatValue64, floatValue61, floatValue65);
      }

      renderManager9.invoke69(
         FontRegistry.fontObject,
         floatValue60 + 12.0F,
         floatValue59 + floatValue62 + 13.0F,
         18.0F,
         this.intValue == 0
            ? "ЛКМ - точка на модели : ПКМ - удалить · " + this.rotationPresetManager.items.size() + "/12"
            : "Голубой - база/смещение · жёлтый - упреждение · красный - итог",
         compute3(146, 156, 172, 206)
      );
      renderManager9.invoke66();
   }

   private void invoke11(RenderManager renderManager10, float f, float g, float h, float i) {
      RotationPresetEditorState.invoke4(
         renderManager10,
         this.floatValue,
         this.floatValue2,
         this.floatValue3,
         this.floatValue4,
         this.rotationPresetManager.floatValue15,
         this.rotationPresetManager.floatValue16,
         this.rotationPresetManager.floatValue17,
         this.rotationPresetManager.floatValue18,
         this.rotationPresetManager.floatValue19,
         this.floatValue9,
         this.floatValue10,
         1.0F
      );
      renderManager10.invoke69(
         FontRegistry.fontObject,
         f + 12.0F,
         g + 6.0F,
         22.0F,
         String.format("Yaw %.1f° - Pitch %.1f°", this.floatValue11, this.floatValue12),
         compute3(210, 220, 235, 220)
      );
   }

   private void invoke12(float f) {
      float floatValue66 = this.rotationPresetManager.floatValue15;
      float floatValue67 = Math.max(this.rotationPresetManager.floatValue17, Math.min(this.rotationPresetManager.floatValue18, this.rotationPresetManager.floatValue16));
      float floatValue68 = Math.max(0.05F, this.rotationPresetManager.floatValue20) * (0.5F + this.rotationPresetManager.floatValue21 * 0.5F) * Math.min(1.0F, f * 30.0F + 0.15F);
      float[] floatValues = RotationPresetEditorState.resolve2(this.floatValue, this.floatValue2, this.floatValue3, this.floatValue4, floatValue66, floatValue67);
      if (!this.flag2) {
         this.floatValue11 = floatValue66;
         this.floatValue12 = floatValue67;
         this.floatValue9 = floatValues[0];
         this.floatValue10 = floatValues[1];
         this.flag2 = true;
      } else {
         this.floatValue11 = this.floatValue11 + (floatValue66 - this.floatValue11) * floatValue68;
         this.floatValue12 = this.floatValue12 + (floatValue67 - this.floatValue12) * floatValue68;
         float[] floatValues2 = RotationPresetEditorState.resolve2(
            this.floatValue, this.floatValue2, this.floatValue3, this.floatValue4, this.floatValue11, this.floatValue12
         );
         this.floatValue9 = this.floatValue9 + (floatValues2[0] - this.floatValue9) * floatValue68;
         this.floatValue10 = this.floatValue10 + (floatValues2[1] - this.floatValue10) * floatValue68;
      }
   }

   private void invoke13(RenderManager renderManager11) {
      float floatValue69 = 0.5F + 0.5F * (float)Math.sin(System.currentTimeMillis() / 320.0);

      for (int intValue13 = 0; intValue13 < this.rotationPresetManager.items.size(); intValue13++) {
         RotationPresetManager.RotationPresetManagerState rotationPresetManagerState = this.rotationPresetManager.items.get(intValue13);
         float floatValue70 = this.floatValue + rotationPresetManagerState.floatValue * this.floatValue3;
         float floatValue71 = this.floatValue2 - rotationPresetManagerState.floatValue2 * this.floatValue4;
         boolean flag4 = intValue13 == this.intValue3;
         float floatValue72 = this.measure3("point." + intValue13);
         float floatValue73 = 1.0F + floatValue72 * 0.35F;
         if (flag4) {
            float floatValue74 = (8.0F + floatValue69 * 2.0F) * floatValue73;
            renderManager11.invoke5(floatValue70 - floatValue74, floatValue71 - floatValue74, floatValue74 * 2.0F, floatValue74 * 2.0F, 6.0F, compute3(95, 210, 255, 70));
         }

         float floatValue75 = 4.0F * floatValue73;
         renderManager11.invoke5(floatValue70 - floatValue75, floatValue71 - floatValue75, floatValue75 * 2.0F, floatValue75 * 2.0F, 6.0F, compute3(95, 210, 255, 238));
      }
   }

   private void invoke14(RenderManager renderManager12) {
      float floatValue76 = 3.0F + (this.rotationPresetManager.floatValue7 + this.rotationPresetManager.floatValue8) * 0.6F;
      renderManager12.invoke5(this.floatValue9 - floatValue76, this.floatValue10 - floatValue76, floatValue76 * 2.0F, floatValue76 * 2.0F, 12.0F, compute3(255, 110, 130, 42));
      renderManager12.invoke5(this.floatValue9 - 7.0F, this.floatValue10 - 0.7F, 14.0F, 1.4F, 0.0F, compute3(255, 90, 110, 235));
      renderManager12.invoke5(this.floatValue9 - 0.7F, this.floatValue10 - 7.0F, 1.4F, 14.0F, 0.0F, compute3(255, 90, 110, 235));
   }

   private void invoke15(float f) {
      long longValue3 = System.currentTimeMillis();
      float floatValue77;
      float floatValue78;
      if (!this.rotationPresetManager.items.isEmpty()) {
         if (longValue3 >= this.timestamp3) {
            if ("Random".equals(this.rotationPresetManager.cycle)) {
               this.intValue4 = (int)(Math.random() * this.rotationPresetManager.items.size());
            } else {
               this.intValue4 = (this.intValue4 + 1) % this.rotationPresetManager.items.size();
            }

            this.timestamp3 = longValue3 + (long)(this.rotationPresetManager.floatValue14 * 1000.0F / Math.max(0.1F, this.rotationPresetManager.floatValue22));
         }

         RotationPresetManager.RotationPresetManagerState rotationPresetManagerState2 = this.rotationPresetManager.items.get(Math.min(this.intValue4, this.rotationPresetManager.items.size() - 1));
         floatValue77 = this.floatValue + rotationPresetManagerState2.floatValue * this.floatValue3;
         floatValue78 = this.floatValue2 - rotationPresetManagerState2.floatValue2 * this.floatValue4;
      } else {
         float[] floatValues3 = RotationPresetEditorState.resolve(this.floatValue, this.floatValue2, this.floatValue3, this.floatValue4, 0.0F, 0.5625F);
         floatValue77 = floatValues3[0];
         floatValue78 = floatValues3[1];
      }

      floatValue77 += (float)(Math.sin(longValue3 / (250.0 / Math.max(0.2F, this.rotationPresetManager.floatValue11))) * this.rotationPresetManager.floatValue9 * this.floatValue3 * 0.5);
      floatValue78 += (float)(Math.cos(longValue3 / (520.0 / Math.max(0.2F, this.rotationPresetManager.floatValue11))) * this.rotationPresetManager.floatValue10 * this.floatValue4 * 0.3F);
      floatValue77 += (float)(Math.cos(longValue3 / 40.0) * this.rotationPresetManager.floatValue7 * 0.6F);
      floatValue78 += (float)(Math.sin(longValue3 / 70.0) * this.rotationPresetManager.floatValue8 * 0.6F);
      if (!this.flag2) {
         this.floatValue9 = floatValue77;
         this.floatValue10 = floatValue78;
         this.flag2 = true;
      } else {
         float floatValue79 = (this.rotationPresetManager.floatValue + this.rotationPresetManager.floatValue2) * 0.5F;
         float floatValue80 = (this.rotationPresetManager.floatValue3 + this.rotationPresetManager.floatValue4) * 0.5F;
         float floatValue81 = Math.max(0.01F, floatValue79 / 180.0F * this.floatValue3 * f * 22.0F);
         float floatValue82 = Math.max(0.01F, floatValue80 / 120.0F * this.floatValue4 * f * 22.0F);
         this.floatValue9 = this.floatValue9 + measure8(floatValue77 - this.floatValue9, -floatValue81, floatValue81);
         this.floatValue10 = this.floatValue10 + measure8(floatValue78 - this.floatValue10, -floatValue82, floatValue82);
      }
   }

   private void invoke16(RenderManager renderManager13) {
      float floatValue83 = this.rotationBuilderScreenState2.floatValue + this.rotationBuilderScreenState2.floatValue3 + 16.0F;
      float floatValue84 = this.rotationBuilderScreenState.floatValue2 + 90.0F;
      float floatValue85 = this.rotationBuilderScreenState.floatValue + this.rotationBuilderScreenState.floatValue3 - floatValue83 - 18.0F;
      float floatValue86 = this.rotationBuilderScreenState.floatValue2 + this.rotationBuilderScreenState.floatValue4 - floatValue84 - 18.0F;
      this.rotationBuilderScreenState3 = new RotationBuilderScreen.RotationBuilderScreenState(floatValue83, floatValue84, floatValue85, floatValue86);
      renderManager13.invoke5(floatValue83, floatValue84, floatValue85, floatValue86, 11.0F, compute3(255, 255, 255, 10));
      renderManager13.invoke28(floatValue83, floatValue84, floatValue85, floatValue86, 11.0F, compute3(255, 255, 255, 20), 2.0F);
      this.rotationBuilderScreenState4 = new RotationBuilderScreen.RotationBuilderScreenState(floatValue83 + 1.0F, floatValue84 + 1.0F, floatValue85 - 2.0F, floatValue86 - 2.0F);
      renderManager13.invoke24(
         this.rotationBuilderScreenState4.floatValue,
         this.rotationBuilderScreenState4.floatValue2,
         this.rotationBuilderScreenState4.floatValue3,
         this.rotationBuilderScreenState4.floatValue4,
         11.0F,
         11.0F,
         11.0F,
         11.0F
      );
      float floatValue87 = floatValue83 + 14.0F;
      float floatValue88 = floatValue85 - 28.0F;
      this.invoke17(renderManager13, floatValue87, floatValue84 + 12.0F, floatValue88);
      float floatValue89 = measure8(this.animation2.measure3(), 0.0F, 1.0F);
      renderManager13.invoke65(floatValue89);
      float floatValue90 = floatValue84 + 48.0F - this.floatValue5;
      this.items = this.intValue == 0 ? this.items2 : this.items3;
      float floatValue91 = (floatValue88 - 8.0F) * 0.5F;
      if (this.intValue == 0) {
         this.rotationBuilderScreenState19 = new RotationBuilderScreen.RotationBuilderScreenState(floatValue87, floatValue90, floatValue91, 24.0F);
         this.invoke19(renderManager13, "pmode", this.rotationBuilderScreenState19, "Точка: " + this.resolve3(), false);
         this.rotationBuilderScreenState20 = new RotationBuilderScreen.RotationBuilderScreenState(floatValue87 + floatValue91 + 8.0F, floatValue90, floatValue91, 24.0F);
         this.invoke19(renderManager13, "mmode", this.rotationBuilderScreenState20, "Точки: " + this.rotationPresetManager.cycle, false);
         floatValue90 += 32.0F;
         this.rotationBuilderScreenState21 = new RotationBuilderScreen.RotationBuilderScreenState(floatValue87, floatValue90, floatValue88, 24.0F);
         this.invoke19(
            renderManager13, "mhead", this.rotationBuilderScreenState21, this.rotationPresetManager.flag ? "Голова: ВКЛ" : "Голова: ВЫКЛ", this.rotationPresetManager.flag
         );
         floatValue90 += 36.0F;
      } else {
         this.rotationBuilderScreenState22 = new RotationBuilderScreen.RotationBuilderScreenState(floatValue87, floatValue90, floatValue91, 24.0F);
         this.invoke19(renderManager13, "pfollow", this.rotationBuilderScreenState22, "Pitch: " + this.rotationPresetManager.smooth, false);
         this.rotationBuilderScreenState23 = new RotationBuilderScreen.RotationBuilderScreenState(floatValue87 + floatValue91 + 8.0F, floatValue90, floatValue91, 24.0F);
         this.invoke19(
            renderManager13, "laway", this.rotationBuilderScreenState23, this.rotationPresetManager.flag2 ? "Отвод: ВКЛ" : "Отвод: ВЫКЛ", this.rotationPresetManager.flag2
         );
         floatValue90 += 36.0F;
      }

      for (RotationBuilderScreen.RotationBuilderScreenUiState rotationBuilderScreenUiState : this.items) {
         rotationBuilderScreenUiState.invoke(floatValue87, floatValue90, floatValue88);
         this.invoke20(renderManager13, rotationBuilderScreenUiState);
         floatValue90 += 34.0F;
      }

      renderManager13.invoke66();
      float floatValue92 = floatValue90 + this.floatValue5;
      float floatValue93 = floatValue84 + floatValue86 - 12.0F;
      this.floatValue6 = Math.max(0.0F, floatValue92 - floatValue93);
      this.floatValue5 = measure8(this.floatValue5, 0.0F, this.floatValue6);
      renderManager13.invoke25();
      if (this.floatValue6 > 0.0F) {
         float floatValue94 = floatValue86 - 16.0F;
         float floatValue95 = Math.max(30.0F, floatValue94 * (floatValue86 / (floatValue86 + this.floatValue6)));
         float floatValue96 = floatValue84 + 8.0F + (floatValue94 - floatValue95) * (this.floatValue5 / this.floatValue6);
         renderManager13.invoke5(floatValue83 + floatValue85 - 6.0F, floatValue84 + 8.0F, 3.0F, floatValue94, 1.5F, compute3(255, 255, 255, 18));
         renderManager13.invoke5(floatValue83 + floatValue85 - 6.0F, floatValue96, 3.0F, floatValue95, 1.5F, compute3(95, 210, 255, 130));
      }
   }

   private void invoke17(RenderManager renderManager14, float f, float g, float h) {
      float floatValue97 = (h - 8.0F) * 0.5F;
      this.rotationBuilderScreenState17 = new RotationBuilderScreen.RotationBuilderScreenState(f, g, floatValue97, 26.0F);
      this.rotationBuilderScreenState18 = new RotationBuilderScreen.RotationBuilderScreenState(f + floatValue97 + 8.0F, g, floatValue97, 26.0F);
      this.invoke18(renderManager14, this.rotationBuilderScreenState17, "Настройки для ротации ", this.intValue == 0);
      this.invoke18(renderManager14, this.rotationBuilderScreenState18, "Вектор головы", this.intValue == 1);
   }

   private void invoke18(RenderManager renderManager15, RotationBuilderScreen.RotationBuilderScreenState rotationBuilderScreenState9, String string, boolean bl) {
      String text6 = "tab." + string;
      float floatValue98 = this.measure2(text6, rotationBuilderScreenState9.check(this.floatValue7, this.floatValue8));
      float floatValue99 = this.measure4(text6, bl);
      float floatValue100 = this.measure3(text6);
      float floatValue101 = 1.0F - floatValue100 * 0.05F;
      float floatValue102 = rotationBuilderScreenState9.floatValue + rotationBuilderScreenState9.floatValue3 * 0.5F;
      float floatValue103 = rotationBuilderScreenState9.floatValue2 + rotationBuilderScreenState9.floatValue4 * 0.5F;
      int intValue14 = compute2(compute3(255, 255, 255, Math.round(10.0F + floatValue98 * 16.0F)), compute3(95, 190, 255, 60), floatValue99);
      renderManager15.invoke63(floatValue101, floatValue101, floatValue102, floatValue103);
      renderManager15.invoke5(rotationBuilderScreenState9.floatValue, rotationBuilderScreenState9.floatValue2, rotationBuilderScreenState9.floatValue3, rotationBuilderScreenState9.floatValue4, 8.0F, intValue14);
      float floatValue104 = TextMeasureCache.measure(FontRegistry.fontObject, string, 22.0F);
      int intValue15 = compute2(compute3(190, 200, 214, 224), compute3(235, 248, 255, 246), floatValue99);
      renderManager15.invoke69(
         FontRegistry.fontObject, rotationBuilderScreenState9.floatValue + (rotationBuilderScreenState9.floatValue3 - floatValue104) * 0.5F, rotationBuilderScreenState9.floatValue2 + 18.0F, 22.0F, string, intValue15
      );
      renderManager15.invoke64();
   }

   private void invoke19(RenderManager renderManager16, String string, RotationBuilderScreen.RotationBuilderScreenState rotationBuilderScreenState10, String string2, boolean bl) {
      float floatValue105 = this.measure2(string, rotationBuilderScreenState10.check(this.floatValue7, this.floatValue8));
      float floatValue106 = this.measure4(string, bl);
      float floatValue107 = this.measure3(string);
      float floatValue108 = 1.0F - floatValue107 * 0.05F;
      float floatValue109 = rotationBuilderScreenState10.floatValue + rotationBuilderScreenState10.floatValue3 * 0.5F;
      float floatValue110 = rotationBuilderScreenState10.floatValue2 + rotationBuilderScreenState10.floatValue4 * 0.5F;
      int intValue16 = compute2(compute3(255, 255, 255, Math.round(14.0F + floatValue105 * 20.0F)), compute3(95, 190, 255, 60), floatValue106);
      int intValue17 = compute2(compute3(255, 255, 255, 24), compute3(95, 210, 255, 124), floatValue106);
      renderManager16.invoke63(floatValue108, floatValue108, floatValue109, floatValue110);
      renderManager16.invoke5(rotationBuilderScreenState10.floatValue, rotationBuilderScreenState10.floatValue2, rotationBuilderScreenState10.floatValue3, rotationBuilderScreenState10.floatValue4, 7.0F, intValue16);
      renderManager16.invoke28(rotationBuilderScreenState10.floatValue, rotationBuilderScreenState10.floatValue2, rotationBuilderScreenState10.floatValue3, rotationBuilderScreenState10.floatValue4, 7.0F, intValue17, 1.0F);
      renderManager16.invoke69(FontRegistry.fontObject, rotationBuilderScreenState10.floatValue + 9.0F, rotationBuilderScreenState10.floatValue2 + 16.0F, 22.0F, string2, compute3(212, 222, 236, 232));
      renderManager16.invoke64();
   }

   private void invoke20(RenderManager renderManager17, RotationBuilderScreen.RotationBuilderScreenUiState rotationBuilderScreenUiState2) {
      float floatValue111 = rotationBuilderScreenUiState2.rotationBuilderScreenProvider.get();
      renderManager17.invoke69(
         FontRegistry.fontObject,
         rotationBuilderScreenUiState2.floatValue4,
         rotationBuilderScreenUiState2.floatValue5 + 10.0F,
         22.0F,
         rotationBuilderScreenUiState2.text,
         compute3(190, 200, 214, 224)
      );
      String text7 = rotationBuilderScreenUiState2.flag ? String.valueOf(Math.round(floatValue111)) : String.format("%.2f", floatValue111);
      float floatValue112 = TextMeasureCache.measure(FontRegistry.fontObject, text7, 22.0F);
      renderManager17.invoke69(
         FontRegistry.fontObject,
         rotationBuilderScreenUiState2.floatValue4 + rotationBuilderScreenUiState2.floatValue6 - floatValue112,
         rotationBuilderScreenUiState2.floatValue5 + 10.0F,
         22.0F,
         text7,
         compute3(240, 246, 255, 226)
      );
      float floatValue113 = rotationBuilderScreenUiState2.floatValue5 + 20.0F;
      float floatValue114 = this.measure5(rotationBuilderScreenUiState2);
      float floatValue115 = this.measure3("slider." + rotationBuilderScreenUiState2.text);
      float floatValue116 = 1.0F + floatValue115 * 0.18F;
      renderManager17.invoke5(rotationBuilderScreenUiState2.floatValue4, floatValue113, rotationBuilderScreenUiState2.floatValue6, 5.0F, 2.5F, compute3(255, 255, 255, 28));
      renderManager17.invoke5(rotationBuilderScreenUiState2.floatValue4, floatValue113, rotationBuilderScreenUiState2.floatValue6 * floatValue114, 5.0F, 2.5F, compute3(95, 210, 255, 165));
      float floatValue117 = rotationBuilderScreenUiState2.floatValue4 + rotationBuilderScreenUiState2.floatValue6 * floatValue114 - 4.0F;
      float floatValue118 = floatValue113 - 2.5F;
      float floatValue119 = 9.0F * floatValue116;
      float floatValue120 = 10.0F * floatValue116;
      renderManager17.invoke5(floatValue117 - (floatValue119 - 9.0F) * 0.5F, floatValue118 - (floatValue120 - 10.0F) * 0.5F, floatValue119, floatValue120, 4.5F, compute3(235, 250, 255, 246));
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      this.invoke40(this.measure6(mouseX), this.measure7(mouseY));
      if (this.flag5) {
         return true;
      } else if (button == 0 && this.rotationBuilderScreenState10.check(this.floatValue7, this.floatValue8)) {
         this.invoke38("presets.open");
         this.flag3 = !this.flag3;
         this.flag4 = false;
         return true;
      } else if (this.animation4.measure3() > 0.04F) {
         if (this.rotationBuilderScreenState11.check(this.floatValue7, this.floatValue8)) {
            if (button == 0) {
               this.invoke22();
            }

            return true;
         } else if (button == 0) {
            this.flag3 = false;
            this.flag4 = false;
            return true;
         } else {
            return true;
         }
      } else if (button == 0) {
         if (this.rotationBuilderScreenState5.check(this.floatValue7, this.floatValue8)) {
            this.invoke38("screen.close");
            this.close();
            return true;
         } else {
            for (RotationBuilderScreen.RotationBuilderScreenBounds rotationBuilderScreenBounds2 : this.items4) {
               if (rotationBuilderScreenBounds2.bounds.check(this.floatValue7, this.floatValue8)) {
                  this.invoke38("chip." + rotationBuilderScreenBounds2.label);
                  this.rotationPresetManager.invoke5(rotationBuilderScreenBounds2.label);
                  return true;
               }
            }

            if (this.rotationBuilderScreenState6.check(this.floatValue7, this.floatValue8)) {
               this.invoke38("clear");
               this.rotationPresetManager.invoke3();
               this.intValue3 = -1;
               return true;
            } else if (this.rotationBuilderScreenState7.check(this.floatValue7, this.floatValue8)) {
               this.invoke38("reset");
               this.rotationPresetManager.invoke7();
               this.intValue3 = -1;
               this.flag2 = false;
               return true;
            } else if (this.rotationBuilderScreenState8.check(this.floatValue7, this.floatValue8)) {
               this.invoke38("copy");
               this.invoke30();
               return true;
            } else if (this.rotationBuilderScreenState9.check(this.floatValue7, this.floatValue8)) {
               this.invoke38("paste");
               this.invoke31();
               return true;
            } else if (!this.rotationBuilderScreenState4.check(this.floatValue7, this.floatValue8)) {
               int intValue18 = this.compute(this.floatValue7, this.floatValue8);
               if (intValue18 >= 0) {
                  this.invoke38("point." + intValue18);
                  this.intValue3 = intValue18;
                  this.intValue2 = intValue18;
                  return true;
               } else if (this.check(this.floatValue7, this.floatValue8)) {
                  float floatValue121 = measure8((this.floatValue7 - this.floatValue) / this.floatValue3, -0.5F, 0.5F);
                  float floatValue122 = measure8((this.floatValue2 - this.floatValue8) / this.floatValue4, 0.0F, 1.0F);
                  this.rotationPresetManager.invoke2(floatValue121, floatValue122);
                  this.intValue3 = this.rotationPresetManager.items.size() - 1;
                  this.invoke38("point." + this.intValue3);
                  return true;
               } else {
                  return true;
               }
            } else if (this.rotationBuilderScreenState17.check(this.floatValue7, this.floatValue8)) {
               this.invoke38("tab.Настройки для ротации ");
               this.invoke34(0);
               return true;
            } else if (this.rotationBuilderScreenState18.check(this.floatValue7, this.floatValue8)) {
               this.invoke38("tab.Вектор головы");
               this.invoke34(1);
               return true;
            } else {
               if (this.intValue == 0) {
                  if (this.rotationBuilderScreenState19.check(this.floatValue7, this.floatValue8)) {
                     this.invoke38("pmode");
                     this.invoke35();
                     return true;
                  }

                  if (this.rotationBuilderScreenState20.check(this.floatValue7, this.floatValue8)) {
                     this.invoke38("mmode");
                     this.rotationPresetManager.cycle = resolve2(RotationPresetManager.CYCLE, this.rotationPresetManager.cycle, 1);
                     RotationPresetManager.invoke10();
                     return true;
                  }

                  if (this.rotationBuilderScreenState21.check(this.floatValue7, this.floatValue8)) {
                     this.invoke38("mhead");
                     this.rotationPresetManager.flag = !this.rotationPresetManager.flag;
                     RotationPresetManager.invoke10();
                     return true;
                  }
               } else {
                  if (this.rotationBuilderScreenState22.check(this.floatValue7, this.floatValue8)) {
                     this.invoke38("pfollow");
                     this.rotationPresetManager.smooth = resolve2(RotationPresetManager.SMOOTH, this.rotationPresetManager.smooth, 1);
                     RotationPresetManager.invoke10();
                     return true;
                  }

                  if (this.rotationBuilderScreenState23.check(this.floatValue7, this.floatValue8)) {
                     this.invoke38("laway");
                     this.rotationPresetManager.flag2 = !this.rotationPresetManager.flag2;
                     RotationPresetManager.invoke10();
                     return true;
                  }
               }

               for (RotationBuilderScreen.RotationBuilderScreenUiState rotationBuilderScreenUiState3 : this.items) {
                  if (rotationBuilderScreenUiState3.check(this.floatValue7, this.floatValue8)) {
                     this.invoke38("slider." + rotationBuilderScreenUiState3.text);
                     this.rotationBuilderScreenUiState = rotationBuilderScreenUiState3;
                     rotationBuilderScreenUiState3.invoke2(this.floatValue7);
                     RotationPresetManager.invoke10();
                     return true;
                  }
               }

               return true;
            }
         }
      } else {
         if (button == 1) {
            if (this.rotationBuilderScreenState4.check(this.floatValue7, this.floatValue8)) {
               if (this.intValue == 0 && this.rotationBuilderScreenState19.check(this.floatValue7, this.floatValue8)) {
                  this.invoke38("pmode");
                  this.rotationPresetManager.multipoint = resolve2(RotationPresetManager.MULTIPOINT, this.rotationPresetManager.multipoint, -1);
                  RotationPresetManager.invoke10();
                  return true;
               }

               if (this.intValue == 0 && this.rotationBuilderScreenState20.check(this.floatValue7, this.floatValue8)) {
                  this.invoke38("mmode");
                  this.rotationPresetManager.cycle = resolve2(RotationPresetManager.CYCLE, this.rotationPresetManager.cycle, -1);
                  RotationPresetManager.invoke10();
                  return true;
               }

               if (this.intValue == 1 && this.rotationBuilderScreenState22.check(this.floatValue7, this.floatValue8)) {
                  this.invoke38("pfollow");
                  this.rotationPresetManager.smooth = resolve2(RotationPresetManager.SMOOTH, this.rotationPresetManager.smooth, -1);
                  RotationPresetManager.invoke10();
                  return true;
               }

               for (RotationBuilderScreen.RotationBuilderScreenUiState rotationBuilderScreenUiState4 : this.items) {
                  if (rotationBuilderScreenUiState4.check(this.floatValue7, this.floatValue8)) {
                     this.invoke38("slider." + rotationBuilderScreenUiState4.text);
                     rotationBuilderScreenUiState4.rotationBuilderScreenListener.set(rotationBuilderScreenUiState4.floatValue3);
                     this.rotationPresetManager.invoke();
                     RotationPresetManager.invoke10();
                     return true;
                  }
               }

               return true;
            }

            int intValue19 = this.compute(this.floatValue7, this.floatValue8);
            if (intValue19 >= 0) {
               this.invoke38("point." + intValue19);
               this.rotationPresetManager.invoke4(this.rotationPresetManager.items.get(intValue19));
               this.intValue3 = -1;
               return true;
            }
         }

         return true;
      }
   }

   public boolean mouseReleased(double mouseX, double mouseY, int button) {
      this.invoke40(this.measure6(mouseX), this.measure7(mouseY));
      if (this.rotationBuilderScreenUiState != null) {
         this.rotationPresetManager.invoke();
         RotationPresetManager.invoke10();
         this.rotationBuilderScreenUiState = null;
      }

      if (this.intValue2 >= 0) {
         RotationPresetManager.invoke10();
         this.intValue2 = -1;
      }

      return true;
   }

   public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
      this.invoke40(this.measure6(mouseX), this.measure7(mouseY));
      if (this.animation4.measure3() > 0.04F) {
         return true;
      } else if (this.rotationBuilderScreenUiState != null) {
         this.rotationBuilderScreenUiState.invoke2(this.floatValue7);
         return true;
      } else if (this.intValue2 >= 0 && this.intValue2 < this.rotationPresetManager.items.size()) {
         RotationPresetManager.RotationPresetManagerState rotationPresetManagerState3 = this.rotationPresetManager.items.get(this.intValue2);
         rotationPresetManagerState3.floatValue = measure8((this.floatValue7 - this.floatValue) / this.floatValue3, -0.5F, 0.5F);
         rotationPresetManagerState3.floatValue2 = measure8((this.floatValue2 - this.floatValue8) / this.floatValue4, 0.0F, 1.0F);
         return true;
      } else {
         return true;
      }
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
      this.invoke40(this.measure6(mouseX), this.measure7(mouseY));
      if (this.animation4.measure3() > 0.04F) {
         if (this.rotationBuilderScreenState16.check(this.floatValue7, this.floatValue8) && this.floatValue14 > 0.0F) {
            this.floatValue13 = measure8(this.floatValue13 - (float)verticalAmount * 30.0F, 0.0F, this.floatValue14);
         }

         return true;
      } else if (this.rotationBuilderScreenState3.check(this.floatValue7, this.floatValue8) && this.floatValue6 > 0.0F) {
         this.floatValue5 = measure8(this.floatValue5 - (float)verticalAmount * 28.0F, 0.0F, this.floatValue6);
         return true;
      } else {
         return true;
      }
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (!(this.animation4.measure3() > 0.04F)) {
         if (keyCode == 256) {
            this.close();
            return true;
         } else {
            return super.keyPressed(keyCode, scanCode, modifiers);
         }
      } else {
         if (this.flag4) {
            if (keyCode == 259 && !this.text2.isEmpty()) {
               this.text2 = this.text2.substring(0, this.text2.length() - 1);
               return true;
            }

            if (keyCode == 86 && (modifiers & 2) != 0 && this.client != null && this.client.keyboard != null) {
               this.invoke29(this.client.keyboard.getClipboard());
               return true;
            }

            if (keyCode == 257 || keyCode == 335) {
               this.invoke23();
               return true;
            }
         }

         if (keyCode == 256) {
            if (this.flag4) {
               this.flag4 = false;
            } else {
               this.flag3 = false;
            }

            return true;
         } else {
            return true;
         }
      }
   }

   public boolean charTyped(char chr, int modifiers) {
      if (this.animation4.measure3() > 0.04F && this.flag4) {
         if (!Character.isISOControl(chr)) {
            this.invoke29(String.valueOf(chr));
         }

         return true;
      } else {
         return super.charTyped(chr, modifiers);
      }
   }

   public void close() {
      if (!this.flag5) {
         this.flag3 = false;
         this.flag4 = false;
         this.flag5 = true;
      }
   }

   private void invoke21() {
      if (!this.flag6) {
         this.flag6 = true;
         this.rotationPresetManager.invoke();
         RotationPresetManager.invoke10();
         super.close();
      }
   }

   private int compute(float f, float g) {
      for (int intValue20 = this.rotationPresetManager.items.size() - 1; intValue20 >= 0; intValue20--) {
         RotationPresetManager.RotationPresetManagerState rotationPresetManagerState4 = this.rotationPresetManager.items.get(intValue20);
         float floatValue123 = this.floatValue + rotationPresetManagerState4.floatValue * this.floatValue3;
         float floatValue124 = this.floatValue2 - rotationPresetManagerState4.floatValue2 * this.floatValue4;
         if (Math.hypot(f - floatValue123, g - floatValue124) <= 8.0) {
            return intValue20;
         }
      }

      return -1;
   }

   private boolean check(float f, float g) {
      float floatValue125 = (f - this.floatValue) / this.floatValue3;
      float floatValue126 = (this.floatValue2 - g) / this.floatValue4;
      return RotationPresetEditorState.check(floatValue125, floatValue126);
   }

   private void invoke22() {
      if (this.rotationBuilderScreenState12.check(this.floatValue7, this.floatValue8)) {
         this.invoke38("presets.close");
         this.flag3 = false;
         this.flag4 = false;
      } else if (this.rotationBuilderScreenState13.check(this.floatValue7, this.floatValue8)) {
         this.invoke38("presets.name");
         this.flag4 = true;
      } else {
         this.flag4 = false;
         if (this.rotationBuilderScreenState14.check(this.floatValue7, this.floatValue8)) {
            this.invoke38("presets.create");
            this.invoke24();
         } else if (this.rotationBuilderScreenState15.check(this.floatValue7, this.floatValue8)) {
            this.invoke38("presets.update");
            this.invoke25();
         } else {
            if (this.rotationBuilderScreenState16.check(this.floatValue7, this.floatValue8)) {
               for (RotationBuilderScreen.RotationBuilderScreenData rotationBuilderScreenData : this.items5) {
                  String text8 = rotationBuilderScreenData.preset.id();
                  if (rotationBuilderScreenData.apply.check(this.floatValue7, this.floatValue8)) {
                     this.invoke38("preset.apply." + text8);
                     this.invoke26(text8);
                     return;
                  }

                  if (rotationBuilderScreenData.copy.check(this.floatValue7, this.floatValue8)) {
                     this.invoke38("preset.copy." + text8);
                     this.invoke27(text8);
                     return;
                  }

                  if (rotationBuilderScreenData.delete.check(this.floatValue7, this.floatValue8)) {
                     this.invoke38("preset.delete." + text8);
                     this.invoke28(text8);
                     return;
                  }

                  if (rotationBuilderScreenData.card.check(this.floatValue7, this.floatValue8)) {
                     this.invoke38("preset.row." + text8);
                     this.text3 = text8;
                     this.text2 = rotationBuilderScreenData.preset.name();
                     return;
                  }
               }
            }
         }
      }
   }

   private void invoke23() {
      if (this.text3 == null) {
         this.invoke24();
      } else {
         this.invoke25();
      }
   }

   private void invoke24() {
      if (this.text2.trim().isEmpty()) {
         this.invoke33("Введите название пресета");
         this.flag4 = true;
      } else {
         RotationPresetStore.RotationPresetStoreTimedEntry rotationPresetStoreTimedEntry2 = this.rotationPresetStore.resolve2(this.text2, this.rotationPresetManager);
         if (rotationPresetStoreTimedEntry2 == null) {
            this.invoke33("Не удалось сохранить пресет");
         } else {
            this.text3 = rotationPresetStoreTimedEntry2.id();
            this.text2 = rotationPresetStoreTimedEntry2.name();
            this.floatValue13 = 0.0F;
            this.invoke33("Пресет сохранён");
         }
      }
   }

   private void invoke25() {
      if (this.text3 == null) {
         this.invoke33("Сначала выберите пресет");
      } else if (this.text2.trim().isEmpty()) {
         this.invoke33("Введите название пресета");
         this.flag4 = true;
      } else {
         RotationPresetStore.RotationPresetStoreTimedEntry rotationPresetStoreTimedEntry3 = this.rotationPresetStore.resolve3(this.text3, this.text2, this.rotationPresetManager);
         if (rotationPresetStoreTimedEntry3 == null) {
            this.invoke33("Не удалось обновить пресет");
         } else {
            this.text2 = rotationPresetStoreTimedEntry3.name();
            this.invoke33("Пресет обновлён");
         }
      }
   }

   private void invoke26(String string) {
      if (!this.rotationPresetStore.check(string)) {
         this.invoke33("Не удалось применить пресет");
      } else {
         this.invoke32();
         RotationPresetStore.RotationPresetStoreTimedEntry rotationPresetStoreTimedEntry4 = this.rotationPresetStore.resolve4(string);
         this.text3 = string;
         this.text2 = rotationPresetStoreTimedEntry4 == null ? this.text2 : rotationPresetStoreTimedEntry4.name();
         this.intValue3 = -1;
         this.intValue2 = -1;
         this.flag2 = false;
         this.invoke();
         this.invoke33("Пресет применён");
      }
   }

   private void invoke27(String string) {
      RotationPresetStore.RotationPresetStoreTimedEntry rotationPresetStoreTimedEntry5 = this.rotationPresetStore.resolve4(string);
      if (rotationPresetStoreTimedEntry5 != null && this.client != null && this.client.keyboard != null) {
         this.client.keyboard.setClipboard(rotationPresetStoreTimedEntry5.key());
         this.invoke33("Код пресета скопирован");
      } else {
         this.invoke33("Не удалось скопировать код");
      }
   }

   private void invoke28(String string) {
      if (!this.rotationPresetStore.check2(string)) {
         this.invoke33("Не удалось удалить пресет");
      } else {
         if (string.equals(this.text3)) {
            this.text3 = null;
            this.text2 = "";
         }

         this.floatValue13 = measure8(this.floatValue13, 0.0F, this.floatValue14);
         this.invoke33("Пресет удалён");
      }
   }

   private void invoke29(String string) {
      if (string != null && !string.isEmpty() && this.text2.length() < 40) {
         StringBuilder stringBuilder = new StringBuilder(this.text2);

         for (int intValue21 = 0; intValue21 < string.length() && stringBuilder.length() < 40; intValue21++) {
            char character = string.charAt(intValue21);
            if (!Character.isISOControl(character)) {
               stringBuilder.append(character);
            }
         }

         this.text2 = stringBuilder.toString();
      }
   }

   private void invoke30() {
      try {
         String text9 = this.rotationPresetManager.resolve4();
         if (this.client != null && this.client.keyboard != null) {
            this.client.keyboard.setClipboard(text9);
            this.invoke33("Ключ скопирован в буфер обмена");
         } else {
            this.invoke33("Не удалось получить буфер обмена");
         }
      } catch (Throwable exception) {
         this.invoke33("Ошибка при создании ключа");
      }
   }

   private void invoke31() {
      try {
         if (this.client == null || this.client.keyboard == null) {
            this.invoke33("Не удалось получить буфер обмена");
            return;
         }

         String text10 = this.client.keyboard.getClipboard();
         if (text10 == null || text10.trim().isEmpty()) {
            this.invoke33("Буфер обмена пуст");
            return;
         }

         if (RotationPresetManager.check3(text10)) {
            this.invoke32();
            this.invoke();
            this.intValue3 = -1;
            this.invoke33("Ключ применён");
         } else {
            this.invoke33("Неверный ключ");
         }
      } catch (Throwable exception2) {
         this.invoke33("Ошибка при вставке ключа");
      }
   }

   private void invoke32() {
      int intValue22 = AttackAura.rezhimRotatsii.options.indexOf("Custom");
      if (intValue22 >= 0) {
         AttackAura.rezhimRotatsii.selectedIndex = intValue22;
         AttackAura.rezhimRotatsii.value = AttackAura.rezhimRotatsii.options.get(intValue22);
         if (WildClient.INSTANCE != null && WildClient.INSTANCE.configManager != null) {
            WildClient.INSTANCE.configManager.scheduleSave();
         }
      }
   }

   private void invoke33(String string) {
      this.text = string;
      this.timestamp = System.currentTimeMillis() + 2600L;
      this.animation3.invoke(0.0);
      this.animation3.resolve4(1.0, 0.22F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
   }

   private void invoke34(int i) {
      if (this.intValue != i) {
         this.intValue = i;
         this.floatValue5 = 0.0F;
         this.flag2 = false;
         this.animation2.invoke(0.0);
         this.animation2.resolve4(1.0, 0.26F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
      }
   }

   private void invoke35() {
      this.rotationPresetManager.multipoint = resolve2(RotationPresetManager.MULTIPOINT, this.rotationPresetManager.multipoint, 1);
      RotationPresetManager.invoke10();
   }

   private static String resolve2(String[] strings, String string, int i) {
      int intValue23 = 0;

      for (int intValue24 = 0; intValue24 < strings.length; intValue24++) {
         if (strings[intValue24].equals(string)) {
            intValue23 = intValue24;
            break;
         }
      }

      intValue23 = (intValue23 + i % strings.length + strings.length) % strings.length;
      return strings[intValue23];
   }

   private String resolve3() {
      return this.rotationPresetManager.items.isEmpty() ? this.rotationPresetManager.multipoint : "Custom";
   }

   private void invoke36(RenderManager renderManager18, String string, RotationBuilderScreen.RotationBuilderScreenState rotationBuilderScreenState11, float f, int i) {
      float floatValue127 = TextMeasureCache.measure(FontRegistry.fontObject, string, f);
      renderManager18.invoke69(
         FontRegistry.fontObject,
         rotationBuilderScreenState11.floatValue + (rotationBuilderScreenState11.floatValue3 - floatValue127) * 0.5F,
         rotationBuilderScreenState11.floatValue2 + rotationBuilderScreenState11.floatValue4 * 0.5F + f * 0.2F,
         f,
         string,
         i
      );
   }

   private float measure2(String string, boolean bl) {
      Animation animation = this.valuesByKey.computeIfAbsent(string, stringx -> {
         Animation animation2 = new Animation();
         animation2.invoke(bl ? 1.0 : 0.0);
         return animation2;
      });
      animation.check();
      animation.resolve4(bl ? 1.0 : 0.0, 0.14F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
      return measure8(animation.measure3(), 0.0F, 1.0F);
   }

   private void invoke37() {
      this.animation2.check();
      this.animation3.check();
      this.animation4.check();
      this.animation4
         .resolve4(
            this.flag3 ? 1.0 : 0.0,
            this.flag3 ? 0.24F : 0.18F,
            this.flag3 ? LegacyEasingFunctions.LEGACY_EASING_FUNCTION_27 : LegacyEasingFunctions.LEGACY_EASING_FUNCTION_8,
            false
         );
      long longValue4 = this.timestamp - System.currentTimeMillis();
      if (longValue4 > 0L && longValue4 < 400L) {
         this.animation3.resolve4(0.0, 0.28F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_8, false);
      }
   }

   private void invoke38(String string) {
      Animation animation3 = this.valuesByKey2.computeIfAbsent(string, stringx -> {
         Animation animation4 = new Animation();
         animation4.invoke(0.0);
         return animation4;
      });
      animation3.invoke(1.0);
      animation3.resolve4(0.0, 0.16F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_3, false);
   }

   private float measure3(String string) {
      Animation animation5 = this.valuesByKey2.get(string);
      if (animation5 == null) {
         return 0.0F;
      } else {
         animation5.check();
         return measure8(animation5.measure3(), 0.0F, 1.0F);
      }
   }

   private float measure4(String string, boolean bl) {
      Animation animation6 = this.valuesByKey3.computeIfAbsent(string, stringx -> {
         Animation animation7 = new Animation();
         animation7.invoke(bl ? 1.0 : 0.0);
         return animation7;
      });
      animation6.check();
      animation6.resolve4(bl ? 1.0 : 0.0, 0.2F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
      return measure8(animation6.measure3(), 0.0F, 1.0F);
   }

   private float measure5(RotationBuilderScreen.RotationBuilderScreenUiState rotationBuilderScreenUiState5) {
      float floatValue128 = measure8(
         (rotationBuilderScreenUiState5.rotationBuilderScreenProvider.get() - rotationBuilderScreenUiState5.floatValue) / (rotationBuilderScreenUiState5.floatValue2 - rotationBuilderScreenUiState5.floatValue), 0.0F, 1.0F
      );
      Animation animation8 = this.valuesByKey4.computeIfAbsent(rotationBuilderScreenUiState5.text, string -> {
         Animation var2x = new Animation();
         var2x.invoke(floatValue128);
         return var2x;
      });
      animation8.check();
      float floatValue129 = this.rotationBuilderScreenUiState == rotationBuilderScreenUiState5 ? 0.08F : 0.16F;
      animation8.resolve4(floatValue128, floatValue129, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
      return measure8(animation8.measure3(), 0.0F, 1.0F);
   }

   private static int compute2(int i, int j, float f) {
      f = Math.max(0.0F, Math.min(1.0F, f));
      int intValue25 = i >> 24 & 0xFF;
      int intValue26 = i >> 16 & 0xFF;
      int intValue27 = i >> 8 & 0xFF;
      int intValue28 = i & 0xFF;
      int intValue29 = j >> 24 & 0xFF;
      int intValue30 = j >> 16 & 0xFF;
      int intValue31 = j >> 8 & 0xFF;
      int intValue32 = j & 0xFF;
      int intValue33 = Math.round(intValue25 + (intValue29 - intValue25) * f);
      int intValue34 = Math.round(intValue26 + (intValue30 - intValue26) * f);
      int intValue35 = Math.round(intValue27 + (intValue31 - intValue27) * f);
      int intValue36 = Math.round(intValue28 + (intValue32 - intValue28) * f);
      return compute3(intValue34, intValue35, intValue36, intValue33);
   }

   private static void invoke39() {
      if (!flag) {
         flag = true;
         EventManager.register(new Object() {
            @EventHandler
            public void onHudRender(HudRenderEvent hudRenderEvent) {
               if (hudRenderEvent.getClient() != null && hudRenderEvent.getClient().currentScreen instanceof RotationBuilderScreen rotationBuilderScreen) {
                  rotationBuilderScreen.invoke2(hudRenderEvent.getRenderManager(), hudRenderEvent.getIntValue(), hudRenderEvent.getIntValue2());
                  if (hudRenderEvent.getRenderManager() != null) {
                     hudRenderEvent.getRenderManager().invoke20();
                  }
               }
            }
         });
      }
   }

   private void invoke40(float f, float g) {
      this.floatValue7 = f;
      this.floatValue8 = g;
   }

   private void invoke41() {
      if (this.client != null && this.client.getWindow() != null && this.client.mouse != null) {
         double doubleValue = this.client.getWindow().getFramebufferWidth();
         double doubleValue2 = this.client.getWindow().getFramebufferHeight();
         if (!(doubleValue <= 0.0) && !(doubleValue2 <= 0.0)) {
            double doubleValue3 = this.client.mouse.getX();
            double doubleValue4 = this.client.mouse.getY();
            if (doubleValue3 >= 0.0 && doubleValue4 >= 0.0 && doubleValue3 <= doubleValue + 2.0 && doubleValue4 <= doubleValue2 + 2.0) {
               this.invoke40((float)doubleValue3, (float)doubleValue4);
            }
         }
      }
   }

   private float measure6(double d) {
      if (this.client != null && this.client.getWindow() != null) {
         int intValue37 = this.client.getWindow().getFramebufferWidth();
         int intValue38 = this.client.getWindow().getScaledWidth();
         return intValue37 > 0 && intValue38 > 0 ? (float)(d * intValue37 / Math.max(1.0, (double)intValue38)) : (float)d;
      } else {
         return (float)d;
      }
   }

   private float measure7(double d) {
      if (this.client != null && this.client.getWindow() != null) {
         int intValue39 = this.client.getWindow().getFramebufferHeight();
         int intValue40 = this.client.getWindow().getScaledHeight();
         return intValue39 > 0 && intValue40 > 0 ? (float)(d * intValue39 / Math.max(1.0, (double)intValue40)) : (float)d;
      } else {
         return (float)d;
      }
   }

   static float measure8(float f, float g, float h) {
      return !Float.isFinite(f) ? g : Math.max(g, Math.min(h, f));
   }

   private static int compute3(int i, int j, int k, int l) {
      return RenderManager.RenderManagerState.compute37(i, j, k, Math.max(0, Math.min(255, l)));
   }

   static final class RotationBuilderScreenState {
      final float floatValue;
      final float floatValue2;
      final float floatValue3;
      final float floatValue4;

      RotationBuilderScreenState(float f, float g, float h, float i) {
         this.floatValue = f;
         this.floatValue2 = g;
         this.floatValue3 = h;
         this.floatValue4 = i;
      }

      static RotationBuilderScreen.RotationBuilderScreenState resolve() {
         return new RotationBuilderScreen.RotationBuilderScreenState(0.0F, 0.0F, 0.0F, 0.0F);
      }

      boolean check(float f, float g) {
         return f >= this.floatValue && g >= this.floatValue2 && f <= this.floatValue + this.floatValue3 && g <= this.floatValue2 + this.floatValue4;
      }
   }

   record RotationBuilderScreenBounds(String label, RotationBuilderScreen.RotationBuilderScreenState bounds, boolean active) {
   }

   interface RotationBuilderScreenProvider {
      float get();
   }

   interface RotationBuilderScreenListener {
      void set(float f);
   }

   record RotationBuilderScreenData(
      RotationPresetStore.RotationPresetStoreTimedEntry preset,
      RotationBuilderScreen.RotationBuilderScreenState card,
      RotationBuilderScreen.RotationBuilderScreenState apply,
      RotationBuilderScreen.RotationBuilderScreenState copy,
      RotationBuilderScreen.RotationBuilderScreenState delete
   ) {
   }

   final class RotationBuilderScreenUiState {
      final String text;
      final float floatValue;
      final float floatValue2;
      final float floatValue3;
      final boolean flag;
      final RotationBuilderScreen.RotationBuilderScreenProvider rotationBuilderScreenProvider;
      final RotationBuilderScreen.RotationBuilderScreenListener rotationBuilderScreenListener;
      float floatValue4;
      float floatValue5;
      float floatValue6;

      RotationBuilderScreenUiState(String string, float f, float g, float h, boolean bl, RotationBuilderScreen.RotationBuilderScreenProvider rotationBuilderScreenProvider, RotationBuilderScreen.RotationBuilderScreenListener rotationBuilderScreenListener) {
         this.text = string;
         this.floatValue = f;
         this.floatValue2 = g;
         this.floatValue3 = h;
         this.flag = bl;
         this.rotationBuilderScreenProvider = rotationBuilderScreenProvider;
         this.rotationBuilderScreenListener = rotationBuilderScreenListener;
      }

      void invoke(float f, float g, float h) {
         this.floatValue4 = f;
         this.floatValue5 = g;
         this.floatValue6 = h;
      }

      boolean check(float f, float g) {
         return f >= this.floatValue4 && f <= this.floatValue4 + this.floatValue6 && g >= this.floatValue5 && g <= this.floatValue5 + 30.0F;
      }

      void invoke2(float f) {
         float floatValue130 = RotationBuilderScreen.measure8((f - this.floatValue4) / this.floatValue6, 0.0F, 1.0F);
         float floatValue131 = this.floatValue + floatValue130 * (this.floatValue2 - this.floatValue);
         if (this.flag) {
            floatValue131 = Math.round(floatValue131);
         } else {
            floatValue131 = Math.round(floatValue131 * 100.0F) / 100.0F;
         }

         this.rotationBuilderScreenListener.set(RotationBuilderScreen.measure8(floatValue131, this.floatValue, this.floatValue2));
      }
   }
}
