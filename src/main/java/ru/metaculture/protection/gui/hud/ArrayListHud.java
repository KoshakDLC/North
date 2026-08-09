package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.screen.ChatScreen;
import org.wild.module.api.Module;

@HudElementInfo(
   resolve = "ArrayList",
   resolve2 = "n"
)
public final class ArrayListHud extends HudElement {
   private static final String PRYAMOUGOLNIK = "Прямоугольник";
   private static final String OBVOLAKIVANIE = "Обволакивание";
   private static final String KLASSICHESKIY = "Классический";
   private static final String NOVYY = "Новый";
   private static final String FERROFLUID_SDF = "Ferrofluid SDF";
   private static final ArrayListHud INSTANCE = new ArrayListHud();
   private static final int INT_VALUE = 96;
   private static final List<ArrayListHud.ArrayListHudState> ITEMS = new ArrayList<>(64);
   private static final Map<Module, ArrayListHud.ArrayListHudState> VALUES_BY_KEY = new IdentityHashMap<>(128);
   private static final SpringSpec SPRING_SPEC = new SpringSpec(0.068F, 0.72F, 0.001F, 0.001F);
   private static final SpringSpec SPRING_SPEC_2 = new SpringSpec(0.105F, 0.84F, 0.001F, 0.001F);
   private static final SpringSpec SPRING_SPEC_3 = new SpringSpec(0.064F, 0.76F, 0.01F, 0.01F);
   private static final SpringSpec SPRING_SPEC_4 = new SpringSpec(0.075F, 0.7F, 0.001F, 0.001F);
   private static final SpringSpec SPRING_SPEC_5 = new SpringSpec(0.082F, 0.62F, 0.001F, 0.001F);
   private static final SpringSpec SPRING_SPEC_6 = new SpringSpec(0.12F, 0.82F, 0.001F, 0.001F);
   static final SpringSpec SPRING_SPEC_7 = new SpringSpec(0.07F, 0.68F, 0.01F, 0.01F);
   static final SpringSpec SPRING_SPEC_8 = new SpringSpec(0.078F, 0.66F, 0.01F, 0.01F);
   private static final SpringSpec SPRING_SPEC_9 = new SpringSpec(0.09F, 0.74F, 0.001F, 0.001F);
   private static final SpringSpec SPRING_SPEC_10 = new SpringSpec(0.062F, 0.8F, 0.001F, 0.001F);
   private static final SpringAnimation SPRING_ANIMATION = new SpringAnimation(0.0F);
   private static final SpringAnimation SPRING_ANIMATION_2 = new SpringAnimation(0.0F);
   private static final SpringAnimation SPRING_ANIMATION_3 = new SpringAnimation(0.0F);
   private static final SpringAnimation SPRING_ANIMATION_4 = new SpringAnimation(0.0F);
   private static final SpringAnimation SPRING_ANIMATION_5 = new SpringAnimation(0.0F);
   private static final SpringAnimation SPRING_ANIMATION_6 = new SpringAnimation(0.0F);
   private static final Animation ANIMATION = new Animation();
   private static final Animation ANIMATION_2 = new Animation();
   private static final Animation ANIMATION_3 = new Animation();
   private static final Animation ANIMATION_4 = new Animation();
   private static final float[] FLOATS = new float[384];
   private static final float[] FLOATS_2 = new float[384];
   private static float[] floats = new float[96];
   private static float[] floats2 = new float[96];
   private static boolean flag;
   public static final ArrayList<Module> ARRAY_LIST = new ArrayList<>(64);
   private final GroupSetting filtr = new GroupSetting(
      "Фильтр", new BooleanSetting("Combat", true), new BooleanSetting("Movement", true), new BooleanSetting("Player", true), new BooleanSetting("Misc", true)
   );
   private final GroupSetting vid = new GroupSetting(
      "Вид",
      new BooleanSetting("Иконки категорий", true),
      new BooleanSetting("Индикатор", true),
      new BooleanSetting("Мягкое свечение", true),
      new BooleanSetting("Показывать бинд", false)
   );
   private final ModeSetting stilOtobrazheniya = new ModeSetting("Стиль отображения", "Новый", "Классический", "Новый");
   private final ModeSetting formaFona = new ModeSetting("Форма фона", "Прямоугольник", "Прямоугольник", "Обволакивание");
   private final NumberSetting intervalStrok = new NumberSetting("Интервал строк", 0.0F, 0.0F, 8.0F, 0.5F, false);
   private final BooleanSetting ferrofluidSdf = new BooleanSetting("Ferrofluid SDF", true);
   private final NumberSetting sliyanieKapel = new NumberSetting("Слияние капель", 12.0F, 4.0F, 24.0F, 0.5F, false)
      .setVisibilityCondition(() -> !this.ferrofluidSdf.isEnabled() && !this.check9());

   private ArrayListHud() {
      this.invoke2(
         new Setting[]{this.filtr, this.vid, this.stilOtobrazheniya, this.ferrofluidSdf, this.sliyanieKapel, this.formaFona, this.intervalStrok}
      );
      ru.metaculture.protection.HudPresetManager.invoke2(this);
   }

   public static ArrayListHud getINSTANCE() {
      return INSTANCE;
   }

   public static void invoke(RenderManager renderManager) {
      INSTANCE.invoke2(renderManager);
   }

   private void invoke2(RenderManager renderManager2) {
      if (MinecraftAccessor.a_.player != null && WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null) {
         this.invoke13();
         if (this.checkSelf()) {
            this.invoke3(renderManager2);
         } else {
            this.invoke4(renderManager2);
         }
      }
   }

   private boolean checkSelf() {
      return this.stilOtobrazheniya.is("Классический");
   }

   private void invoke3(RenderManager renderManager3) {
      boolean flag = !ITEMS.isEmpty() || MinecraftAccessor.a_.currentScreen instanceof ChatScreen;
      ANIMATION.check();
      ANIMATION.resolve4(flag ? 1.0 : 0.0, 0.22F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
      float floatValue = ANIMATION.measure3();
      if (!(floatValue <= 0.01F)) {
         boolean flag2 = this.vid.isEnabled("Иконки категорий");
         boolean flag3 = this.vid.isEnabled("Индикатор");
         boolean flag4 = this.vid.isEnabled("Мягкое свечение");
         boolean flag5 = this.vid.isEnabled("Показывать бинд");
         float floatValue2 = 24.0F;
         float floatValue3 = 32.0F;
         float floatValue4 = this.intervalStrok.getValue();
         float floatValue5 = 13.0F;
         float floatValue6 = 4.0F;
         float floatValue7 = 0.0F;
         float floatValue8 = 0.0F;
         int intValue = 0;

         for (ArrayListHud.ArrayListHudState arrayListHudState : ITEMS) {
            float floatValue9 = arrayListHudState.animation.measure3();
            if (!(floatValue9 <= 0.01F)) {
               intValue++;
               floatValue7 = Math.max(floatValue7, arrayListHudState.measure(floatValue2, floatValue5, flag3, flag2, flag5));
               floatValue8 += floatValue3 * floatValue9;
               if (intValue > 1) {
                  floatValue8 += floatValue4 * floatValue9;
               }
            }
         }

         boolean flag6 = intValue > 0;
         if (!flag6) {
            floatValue7 = TextMeasureCache.measure(FontRegistry.fontObject, "Нет активных модулей ", floatValue2) + floatValue5 * 2.0F;
            floatValue8 = floatValue3;
         }

         float floatValue10 = floatValue7 + floatValue6 * 2.0F;
         float floatValue11 = floatValue8 + floatValue6 * 2.0F;
         ANIMATION_2.check();
         ANIMATION_3.check();
         ANIMATION_2.resolve4(floatValue10, 0.18F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
         ANIMATION_3.resolve4(floatValue11, 0.18F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
         float floatValue12 = Math.max(32.0F, ANIMATION_2.measure3());
         float floatValue13 = Math.max(32.0F, ANIMATION_3.measure3());
         float floatValue14 = MinecraftAccessor.a_.getWindow().getFramebufferWidth();
         float floatValue15 = Math.max(10.0F, floatValue14 - floatValue12 - 10.0F);
         float floatValue16 = 120.0F;
         HudEditorRenderer.HudEditorRendererState hudEditorRendererState = HudEditorRenderer.getINSTANCE().resolve("HUD_ArrayList", floatValue15, floatValue16, floatValue12, floatValue13);
         float floatValue17 = hudEditorRendererState.floatValue;
         float floatValue18 = hudEditorRendererState.floatValue2;
         float floatValue19 = hudEditorRendererState.floatValue3;
         float floatValue20 = hudEditorRendererState.floatValue4;
         this.invoke3(floatValue17, floatValue18, floatValue19, floatValue20);
         float floatValue21 = floatValue19 / Math.max(1.0F, floatValue12);
         float floatValue22 = floatValue20 / Math.max(1.0F, floatValue13);
         float floatValue23 = Math.min(floatValue21, floatValue22);
         float floatValue24 = floatValue3 * floatValue22;
         float floatValue25 = floatValue4 * floatValue22;
         float floatValue26 = floatValue5 * floatValue21;
         float floatValue27 = floatValue6 * floatValue21;
         float floatValue28 = floatValue6 * floatValue22;
         float floatValue29 = floatValue2 * floatValue23;
         float floatValue30 = floatValue * this.prozrachnost.getValue();
         int intValue2 = this.compute6(floatValue30);
         int intValue3 = this.compute7(floatValue30);
         float floatValue31 = floatValue17 + floatValue19 * 0.5F > floatValue14 * 0.5F ? 1.0F : 0.0F;
         ANIMATION_4.check();
         ANIMATION_4.resolve4(floatValue31, 0.26F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
         float floatValue32 = ANIMATION_4.measure3();
         float floatValue33 = floatValue7 * floatValue21;
         float floatValue34 = floatValue18 + floatValue28;
         if (flag6) {
            this.invoke6(renderManager3, floatValue17 + floatValue27, floatValue34, floatValue24, floatValue25, floatValue5, floatValue2, floatValue21, floatValue22, floatValue30, flag3, flag2, flag5, flag4, floatValue32);

            for (ArrayListHud.ArrayListHudState arrayListHudState2 : ITEMS) {
               float floatValue35 = arrayListHudState2.animation.measure3();
               if (!(floatValue35 <= 0.01F)) {
                  float floatValue36 = arrayListHudState2.measure(floatValue2, floatValue5, flag3, flag2, flag5);
                  float floatValue37 = Math.max(1.0F, floatValue36 * floatValue21 * floatValue35);
                  float floatValue38 = floatValue17 + floatValue27 + (floatValue33 - floatValue37) * floatValue32;
                  this.invoke14(
                     renderManager3, arrayListHudState2, floatValue38, floatValue34, floatValue37, floatValue24, floatValue26, floatValue29, floatValue21, floatValue22, floatValue30, floatValue35, intValue2, intValue3, flag3, flag2, flag5, floatValue32
                  );
                  floatValue34 += floatValue24 * floatValue35 + floatValue25 * floatValue35;
               }
            }
         } else {
            this.invoke16(renderManager3, floatValue17 + floatValue27, floatValue34, Math.max(1.0F, floatValue19 - floatValue27 * 2.0F), floatValue24, floatValue26, floatValue29, floatValue30, intValue3, true);
         }

         HudEditorRenderer.getINSTANCE().invoke6(hudEditorRendererState);
         HudSettingsRenderer.invoke2(
            renderManager3,
            this,
            hudEditorRendererState,
            HudEditorRenderer.getINSTANCE(),
            MinecraftAccessor.a_.getWindow().getScaledWidth(),
            MinecraftAccessor.a_.getWindow().getScaledHeight()
         );
      }
   }

   private void invoke4(RenderManager renderManager4) {
      boolean flag7 = !ITEMS.isEmpty() || MinecraftAccessor.a_.currentScreen instanceof ChatScreen;
      if (flag7 && !flag) {
         SPRING_ANIMATION_6.setFloatValue(Math.max(SPRING_ANIMATION_6.getFloatValue(), 1.2F));
         SPRING_ANIMATION_5.invoke(0.0F);
      }

      flag = flag7;
      float floatValue39 = measure3(SPRING_ANIMATION.measure(flag7 ? 1.0F : 0.0F, flag7 ? SPRING_SPEC : SPRING_SPEC_2));
      float floatValue40 = measure3(SPRING_ANIMATION_5.measure(flag7 ? 1.0F : 0.0F, SPRING_SPEC_9));
      float floatValue41 = Math.max(0.0F, SPRING_ANIMATION_6.measure(0.0F, SPRING_SPEC_10));
      if (!(floatValue39 <= 0.01F)) {
         boolean flag8 = this.vid.isEnabled("Иконки категорий");
         boolean flag9 = this.vid.isEnabled("Индикатор");
         boolean flag10 = this.vid.isEnabled("Мягкое свечение");
         boolean flag11 = this.vid.isEnabled("Показывать бинд");
         float floatValue42 = 24.0F;
         float floatValue43 = 32.0F;
         float floatValue44 = this.intervalStrok.getValue();
         float floatValue45 = 13.0F;
         float floatValue46 = 4.0F;
         float floatValue47 = 0.0F;
         float floatValue48 = 0.0F;
         int intValue4 = 0;

         for (ArrayListHud.ArrayListHudState arrayListHudState3 : ITEMS) {
            float floatValue49 = measure3(arrayListHudState3.springAnimation.getFloatValue());
            if (!(floatValue49 <= 0.01F) || !(Math.abs(arrayListHudState3.springAnimation.getFloatValue2()) <= 0.01F)) {
               intValue4++;
               floatValue47 = Math.max(floatValue47, arrayListHudState3.measure(floatValue42, floatValue45, flag9, flag8, flag11));
               floatValue48 += floatValue43 * floatValue49;
               if (intValue4 > 1) {
                  floatValue48 += floatValue44 * floatValue49;
               }
            }
         }

         boolean flag12 = intValue4 > 0;
         if (!flag12) {
            floatValue47 = TextMeasureCache.measure(FontRegistry.fontObject, "Нет активных модулей ", floatValue42) + floatValue45 * 2.0F;
            floatValue48 = floatValue43;
         }

         float floatValue50 = floatValue47 + floatValue46 * 2.0F;
         float floatValue51 = floatValue48 + floatValue46 * 2.0F;
         float floatValue52 = Math.max(32.0F, SPRING_ANIMATION_2.measure(floatValue50, SPRING_SPEC_3));
         float floatValue53 = Math.max(32.0F, SPRING_ANIMATION_3.measure(floatValue51, SPRING_SPEC_3));
         float floatValue54 = MinecraftAccessor.a_.getWindow().getFramebufferWidth();
         float floatValue55 = Math.max(10.0F, floatValue54 - floatValue52 - 10.0F);
         float floatValue56 = 120.0F;
         HudEditorRenderer.HudEditorRendererState hudEditorRendererState2 = HudEditorRenderer.getINSTANCE().resolve("HUD_ArrayList", floatValue55, floatValue56, floatValue52, floatValue53);
         float floatValue57 = hudEditorRendererState2.floatValue;
         float floatValue58 = hudEditorRendererState2.floatValue2;
         float floatValue59 = hudEditorRendererState2.floatValue3;
         float floatValue60 = hudEditorRendererState2.floatValue4;
         this.invoke3(floatValue57, floatValue58, floatValue59, floatValue60);
         float floatValue61 = floatValue59 / Math.max(1.0F, floatValue52);
         float floatValue62 = floatValue60 / Math.max(1.0F, floatValue53);
         float floatValue63 = Math.min(floatValue61, floatValue62);
         float floatValue64 = floatValue43 * floatValue62;
         float floatValue65 = floatValue44 * floatValue62;
         float floatValue66 = floatValue45 * floatValue61;
         float floatValue67 = floatValue46 * floatValue61;
         float floatValue68 = floatValue46 * floatValue62;
         float floatValue69 = floatValue42 * floatValue63;
         float floatValue70 = floatValue39 * this.prozrachnost.getValue();
         int intValue5 = this.compute6(floatValue70);
         int intValue6 = this.compute7(floatValue70);
         float floatValue71 = floatValue57 + floatValue59 * 0.5F > floatValue54 * 0.5F ? 1.0F : 0.0F;
         float floatValue72 = measure3(SPRING_ANIMATION_4.measure(floatValue71, SPRING_SPEC_4));
         float floatValue73 = floatValue47 * floatValue61;
         float floatValue74 = floatValue57 + floatValue67;
         float floatValue75 = floatValue58 + floatValue68;
         HudEditorRenderer hudEditorRenderer = HudEditorRenderer.getINSTANCE();
         float floatValue76 = hudEditorRenderer.getFloatValue();
         float floatValue77 = hudEditorRenderer.getFloatValue2();
         float floatValue78 = MinecraftAccessor.a_.currentScreen instanceof ChatScreen && check3(floatValue76, floatValue77, floatValue57, floatValue58, floatValue59, floatValue60) ? 1.0F : 0.0F;
         boolean flag13 = this.check8() && !this.formaFona.is("Обволакивание");
         int intValue7 = flag12 ? this.compute(floatValue74, floatValue75, floatValue73, floatValue64, floatValue65, floatValue42, floatValue45, floatValue61, floatValue72, flag9, flag8, flag11, intValue4, flag13) : 0;
         if (flag12 && intValue7 > 0) {
            this.invoke5(renderManager4, floatValue62, floatValue70 * (0.82F + floatValue40 * 0.18F), flag9, flag10, floatValue72, floatValue76, floatValue77, floatValue78, floatValue41);

            for (ArrayListHud.ArrayListHudState arrayListHudState4 : ITEMS) {
               if (arrayListHudState4.isFlag4()) {
                  this.invoke15(
                     renderManager4,
                     arrayListHudState4,
                     arrayListHudState4.floatValue4,
                     arrayListHudState4.floatValue5,
                     arrayListHudState4.floatValue6,
                     arrayListHudState4.floatValue7,
                     floatValue66,
                     floatValue69,
                     floatValue61,
                     floatValue62,
                     floatValue70,
                     measure3(arrayListHudState4.springAnimation.getFloatValue()),
                     intValue5,
                     intValue6,
                     flag9,
                     flag8,
                     flag11,
                     floatValue72
                  );
               }
            }
         } else {
            this.invoke16(renderManager4, floatValue74, floatValue75, Math.max(1.0F, floatValue59 - floatValue67 * 2.0F), floatValue64, floatValue66, floatValue69, floatValue70, intValue6, false);
         }

         HudEditorRenderer.getINSTANCE().invoke6(hudEditorRendererState2);
         HudSettingsRenderer.invoke2(
            renderManager4,
            this,
            hudEditorRendererState2,
            HudEditorRenderer.getINSTANCE(),
            MinecraftAccessor.a_.getWindow().getScaledWidth(),
            MinecraftAccessor.a_.getWindow().getScaledHeight()
         );
      }
   }

   private int compute(
      float f, float g, float h, float i, float j, float k, float l, float m, float n, boolean bl, boolean bl2, boolean bl3, int o, boolean bl4
   ) {
      float floatValue79 = g;
      int intValue8 = 0;

      for (ArrayListHud.ArrayListHudState arrayListHudState5 : ITEMS) {
         float floatValue80 = measure3(arrayListHudState5.springAnimation.getFloatValue());
         if (floatValue80 <= 0.01F && Math.abs(arrayListHudState5.springAnimation.getFloatValue2()) <= 0.01F) {
            arrayListHudState5.flag4 = false;
         } else {
            float floatValue81 = Math.max(1.0F, arrayListHudState5.measure(k, l, bl, bl2, bl3) * m);
            float floatValue82 = bl4 ? h : Math.max(1.0F, floatValue81 * (0.32F + floatValue80 * 0.68F));
            float floatValue83 = Math.max(0.0F, i * floatValue80);
            float floatValue84 = f + (h - floatValue82) * n;
            arrayListHudState5.invoke2(floatValue84, floatValue79, floatValue82, floatValue83);
            arrayListHudState5.flag4 = arrayListHudState5.floatValue6 > 0.75F && arrayListHudState5.floatValue7 > 0.75F;
            floatValue79 += i * floatValue80;
            if (++intValue8 < o) {
               floatValue79 += j * floatValue80;
            }
         }
      }

      return intValue8;
   }

   private void invoke5(RenderManager renderManager5, float f, float g, boolean bl, boolean bl2, float h, float i, float j, float k, float l) {
      int intValue9 = 0;
      float floatValue85 = Float.MAX_VALUE;
      float floatValue86 = Float.MAX_VALUE;
      float floatValue87 = -Float.MAX_VALUE;
      float floatValue88 = -Float.MAX_VALUE;
      float floatValue89 = Float.MAX_VALUE;
      float floatValue90 = 0.0F;
      float floatValue91 = 0.0F;

      for (ArrayListHud.ArrayListHudState arrayListHudState6 : ITEMS) {
         if (arrayListHudState6.isFlag4() && intValue9 < 96) {
            intValue9++;
            floatValue85 = Math.min(floatValue85, arrayListHudState6.floatValue4);
            floatValue86 = Math.min(floatValue86, arrayListHudState6.floatValue5);
            floatValue87 = Math.max(floatValue87, arrayListHudState6.floatValue4 + arrayListHudState6.floatValue6);
            floatValue88 = Math.max(floatValue88, arrayListHudState6.floatValue5 + arrayListHudState6.floatValue7);
            floatValue89 = Math.min(floatValue89, arrayListHudState6.floatValue6);
            floatValue90 = Math.max(floatValue90, arrayListHudState6.floatValue6);
            floatValue91 = Math.max(floatValue91, Math.min(1.0F, (Math.abs(arrayListHudState6.springAnimation4.getFloatValue2()) + Math.abs(arrayListHudState6.springAnimation5.getFloatValue2())) * 0.012F));
         }
      }

      if (intValue9 > 0 && floatValue85 != Float.MAX_VALUE && floatValue86 != Float.MAX_VALUE && !(floatValue87 <= floatValue85) && !(floatValue88 <= floatValue86)) {
         boolean flag14 = this.formaFona.is("Обволакивание");
         boolean flag15 = this.ferrofluidSdf.isEnabled() || this.check9();
         float floatValue92 = Math.max(1.0F, floatValue88 - floatValue86);
         float floatValue93 = Math.max(1.0F, floatValue92 / intValue9);
         float floatValue94 = Math.min(15.0F * f, floatValue93 * 0.5F);
         if (floats.length < intValue9) {
            floats = new float[intValue9];
            floats2 = new float[intValue9];
         }

         float[] floatValues = floats;
         float[] floatValues2 = floats2;
         float floatValue95 = flag14 ? floatValue90 : floatValue87 - floatValue85;
         float floatValue96 = Math.max(l, floatValue91);
         int intValue10 = 0;

         for (ArrayListHud.ArrayListHudState arrayListHudState7 : ITEMS) {
            if (arrayListHudState7.isFlag4() && intValue10 < intValue9) {
               int intValue11 = intValue10 * 4;
               float floatValue97 = flag14 ? arrayListHudState7.floatValue4 : floatValue85;
               float floatValue98 = flag14 ? arrayListHudState7.floatValue6 : floatValue95;
               FLOATS[intValue11] = floatValue97;
               FLOATS[intValue11 + 1] = arrayListHudState7.floatValue5;
               FLOATS[intValue11 + 2] = floatValue98;
               FLOATS[intValue11 + 3] = arrayListHudState7.floatValue7;
               FLOATS_2[intValue11] = arrayListHudState7.springAnimation4.getFloatValue2();
               FLOATS_2[intValue11 + 1] = arrayListHudState7.springAnimation5.getFloatValue2();
               FLOATS_2[intValue11 + 2] = Math.max(0.0F, arrayListHudState7.springAnimation3.getFloatValue());
               FLOATS_2[intValue11 + 3] = measure3(arrayListHudState7.springAnimation2.getFloatValue());
               floatValues[intValue10] = floatValue98;
               floatValues2[intValue10] = Math.max(1.0F, arrayListHudState7.floatValue5 - floatValue86 + arrayListHudState7.floatValue7);
               floatValue96 = Math.max(floatValue96, FLOATS_2[intValue11 + 2]);
               intValue10++;
            }
         }

         int intValue12 = this.compute2Self(g);
         float floatValue99 = this.measure3();
         if (flag15) {
            float floatValue100 = Math.max(2.0F, this.sliyanieKapel.getValue() * f);
            float floatValue101 = this.check8() ? 1.0F : 0.0F;
            boolean flag16 = ArrayListBlurRenderer.check(
               renderManager5,
               MinecraftAccessor.a_.getWindow().getFramebufferWidth(),
               MinecraftAccessor.a_.getWindow().getFramebufferHeight(),
               FLOATS,
               FLOATS_2,
               intValue9,
               floatValue94,
               h,
               g,
               intValue12,
               this.compute5(g),
               this.compute9(g),
               this.compute10(g),
               this.check2() || this.check9(),
               bl2 || this.checkSelf(),
               bl,
               floatValue99,
               i,
               j,
               k,
               floatValue96,
               floatValue100,
               floatValue101
            );
            if (flag16) {
               return;
            }
         }

         if (this.check8()) {
            this.invoke(renderManager5, floatValue85, floatValue86, floatValue95, floatValue92, floatValue94, g);
         } else if (this.check10()) {
            this.invoke(renderManager5, floatValue85, floatValue86, floatValue95, floatValue92, floatValue94, g);
         } else {
            float floatValue102 = Math.max(1.5F, 2.0F * f);
            if (bl2) {
               this.invoke10(
                  renderManager5,
                  floatValue85,
                  floatValue86,
                  floatValue95,
                  floatValues,
                  floatValues2,
                  intValue9,
                  floatValue89,
                  floatValue92,
                  floatValue93,
                  f,
                  floatValue94,
                  floatValue102,
                  ColorUtils.compute2(this.compute9(g), Math.round(52.0F * g)),
                  Math.max(8.0F, 10.0F * f),
                  Math.max(1.0F, 1.4F * f),
                  h
               );
               this.invoke10(
                  renderManager5,
                  floatValue85,
                  floatValue86,
                  floatValue95,
                  floatValues,
                  floatValues2,
                  intValue9,
                  floatValue89,
                  floatValue92,
                  floatValue93,
                  f,
                  floatValue94,
                  floatValue102,
                  ColorUtils.compute2(this.compute10(g), Math.round(32.0F * g)),
                  Math.max(16.0F, 22.0F * f),
                  Math.max(2.0F, 3.0F * f),
                  h
               );
            }

            if (this.vizual.isEnabled("Тень")) {
               this.invoke10(
                  renderManager5,
                  floatValue85,
                  floatValue86,
                  floatValue95,
                  floatValues,
                  floatValues2,
                  intValue9,
                  floatValue89,
                  floatValue92,
                  floatValue93,
                  f,
                  floatValue94,
                  floatValue102,
                  this.compute17(g),
                  Math.max(4.0F, 4.0F * f),
                  Math.max(1.0F, 1.0F * f),
                  h
               );
            }

            this.invoke8(renderManager5, floatValue85, floatValue86, floatValue95, floatValues, floatValues2, intValue9, floatValue89, floatValue92, floatValue93, f, floatValue94, floatValue102, intValue12, g, false, true, h);
            if (this.check2()) {
               this.invoke7(
                  renderManager5,
                  floatValue85,
                  floatValue86,
                  floatValue95,
                  floatValues,
                  floatValues2,
                  intValue9,
                  floatValue89,
                  floatValue92,
                  floatValue93,
                  f,
                  floatValue94,
                  this.compute5(g),
                  Math.max(1.0F, this.measure2() * 0.55F),
                  h
               );
            }
         }
      }
   }

   private void invoke6(
      RenderManager renderManager6,
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
      boolean bl2,
      boolean bl3,
      boolean bl4,
      float o
   ) {
      int intValue13 = 0;

      for (ArrayListHud.ArrayListHudState arrayListHudState8 : ITEMS) {
         float floatValue103 = arrayListHudState8.animation.measure3();
         if (!(floatValue103 <= 0.01F)) {
            intValue13++;
         }
      }

      if (intValue13 > 0) {
         if (floats.length < intValue13) {
            floats = new float[intValue13];
            floats2 = new float[intValue13];
         }

         float[] floatValues3 = floats;
         float[] floatValues4 = floats2;
         float floatValue104 = 0.0F;
         float floatValue105 = Float.MAX_VALUE;
         float floatValue106 = 0.0F;
         int intValue14 = 0;

         for (ArrayListHud.ArrayListHudState arrayListHudState9 : ITEMS) {
            float floatValue107 = arrayListHudState9.animation.measure3();
            if (!(floatValue107 <= 0.01F)) {
               float floatValue108 = Math.max(1.0F, arrayListHudState9.measure(k, j, bl, bl2, bl3) * l);
               float floatValue109 = h * floatValue107 + (intValue14 < intValue13 - 1 ? i * floatValue107 : 0.0F);
               floatValue104 += floatValue109;
               floatValues3[intValue14] = floatValue108;
               floatValues4[intValue14] = floatValue104;
               floatValue105 = Math.min(floatValue105, floatValue108);
               floatValue106 = Math.max(floatValue106, floatValue108);
               intValue14++;
            }
         }

         if (!(floatValue105 <= 1.0F) && !(floatValue104 <= 1.0F)) {
            float floatValue110 = Math.min(15.0F * m, h * 0.5F);
            int intValue15 = this.compute3Self(n);
            float floatValue111 = Math.max(1.5F, 2.0F * l);
            boolean flag17 = this.formaFona.is("Обволакивание");
            if (this.check10()) {
               this.invoke(renderManager6, f, g, floatValue106, floatValue104, floatValue110, n);
               if (bl) {
                  float floatValue112 = Math.max(1.35F, 1.8F * l);
                  float floatValue113 = f + measure2(5.0F * l, floatValue106 - 5.0F * l - floatValue112, o);
                  renderManager6.invoke37(floatValue113, g + 5.0F * m, floatValue112, Math.max(1.0F, floatValue104 - 10.0F * m), floatValue112, this.compute9(n), this.compute10(n));
               }
            } else {
               if (bl4) {
                  if (flag17) {
                     this.invoke10(
                        renderManager6,
                        f,
                        g,
                        floatValue106,
                        floatValues3,
                        floatValues4,
                        intValue13,
                        floatValue105,
                        floatValue104,
                        h,
                        m,
                        floatValue110,
                        floatValue111,
                        ColorUtils.compute2(this.compute9(n), Math.round(52.0F * n)),
                        Math.max(8.0F, 10.0F * m),
                        Math.max(1.0F, 1.4F * m),
                        o
                     );
                     this.invoke10(
                        renderManager6,
                        f,
                        g,
                        floatValue106,
                        floatValues3,
                        floatValues4,
                        intValue13,
                        floatValue105,
                        floatValue104,
                        h,
                        m,
                        floatValue110,
                        floatValue111,
                        ColorUtils.compute2(this.compute10(n), Math.round(32.0F * n)),
                        Math.max(16.0F, 22.0F * m),
                        Math.max(2.0F, 3.0F * m),
                        o
                     );
                  } else {
                     renderManager6.invoke41(
                        f,
                        g,
                        floatValue106,
                        floatValue104,
                        floatValue110,
                        Math.max(8.0F, 10.0F * m),
                        Math.max(1.0F, 1.4F * m),
                        ColorUtils.compute2(this.compute9(n), Math.round(52.0F * n))
                     );
                     renderManager6.invoke41(
                        f,
                        g,
                        floatValue106,
                        floatValue104,
                        floatValue110,
                        Math.max(16.0F, 22.0F * m),
                        Math.max(2.0F, 3.0F * m),
                        ColorUtils.compute2(this.compute10(n), Math.round(32.0F * n))
                     );
                  }
               }

               if (this.vizual.isEnabled("Тень")) {
                  if (flag17) {
                     this.invoke10(
                        renderManager6,
                        f,
                        g,
                        floatValue106,
                        floatValues3,
                        floatValues4,
                        intValue13,
                        floatValue105,
                        floatValue104,
                        h,
                        m,
                        floatValue110,
                        floatValue111,
                        this.compute17(n),
                        Math.max(4.0F, 4.0F * m),
                        Math.max(1.0F, 1.0F * m),
                        o
                     );
                  } else {
                     renderManager6.invoke41(f, g, floatValue106, floatValue104, floatValue110, Math.max(4.0F, 4.0F * m), Math.max(1.0F, 1.0F * m), this.compute17(n));
                  }
               }

               if (this.check7()) {
                  if (flag17) {
                     this.invoke8(renderManager6, f, g, floatValue106, floatValues3, floatValues4, intValue13, floatValue105, floatValue104, h, m, floatValue110, floatValue111, intValue15, n, true, false, o);
                  } else {
                     renderManager6.invoke48(23.0F);
                     renderManager6.invoke44(f, g, floatValue106, floatValue104, floatValue110, n);
                  }
               }

               if (flag17) {
                  this.invoke8(renderManager6, f, g, floatValue106, floatValues3, floatValues4, intValue13, floatValue105, floatValue104, h, m, floatValue110, floatValue111, intValue15, n, false, true, o);
               } else {
                  renderManager6.invoke5(f, g, floatValue106, floatValue104, floatValue110, intValue15);
               }

               if (this.check2()) {
                  if (flag17) {
                     this.invoke7(
                        renderManager6,
                        f,
                        g,
                        floatValue106,
                        floatValues3,
                        floatValues4,
                        intValue13,
                        floatValue105,
                        floatValue104,
                        h,
                        m,
                        floatValue110,
                        this.compute5(n),
                        Math.max(1.0F, this.measure2() * 0.55F),
                        o
                     );
                  } else {
                     renderManager6.invoke28(f, g, floatValue106, floatValue104, floatValue110, this.compute5(n), Math.max(1.0F, this.measure2() * 0.55F));
                  }
               }

               if (bl) {
                  float floatValue114 = Math.max(1.35F, 1.8F * l);
                  float floatValue115 = f + measure2(5.0F * l, floatValue106 - 5.0F * l - floatValue114, o);
                  int intValue16 = this.compute9(n);
                  int intValue17 = this.compute10(n);
                  renderManager6.invoke37(floatValue115, g + 5.0F * m, floatValue114, Math.max(1.0F, floatValue104 - 10.0F * m), floatValue114, intValue16, intValue17);
               }
            }
         }
      }
   }

   private void invoke7(
      RenderManager renderManager7,
      float f,
      float g,
      float h,
      float[] fs,
      float[] gs,
      int i,
      float j,
      float k,
      float l,
      float m,
      float n,
      int o,
      float p,
      float q
   ) {
      if (i > 0 && ColorUtils.compute4(o) > 0) {
         float floatValue116 = Math.max(1.0F, p);
         float floatValue117 = floatValue116 * 0.5F;
         invoke18(renderManager7, f, h, 0.0F, g + n, floatValue116, Math.max(1.0F, k - n * 2.0F), floatValue117, o, q);
         invoke18(renderManager7, f, h, n, g, Math.max(1.0F, fs[0] - n * 2.0F), floatValue116, floatValue117, o, q);

         for (int intValue18 = 0; intValue18 < i; intValue18++) {
            float floatValue118 = intValue18 == 0 ? 0.0F : gs[intValue18 - 1];
            float floatValue119 = gs[intValue18];
            float floatValue120 = intValue18 == 0 ? fs[intValue18] : fs[intValue18 - 1];
            float floatValue121 = intValue18 == i - 1 ? fs[intValue18] : fs[intValue18 + 1];
            float floatValue122 = intValue18 == 0 ? n : (fs[intValue18] > floatValue120 + 0.5F ? this.measure(n, fs[intValue18] - floatValue120, l, m) : 0.0F);
            float floatValue123 = intValue18 == i - 1 ? n : (fs[intValue18] > floatValue121 + 0.5F ? this.measure(n, fs[intValue18] - floatValue121, l, m) : 0.0F);
            float floatValue124 = g + floatValue118 + floatValue122;
            float floatValue125 = g + floatValue119 - floatValue123;
            if (floatValue125 > floatValue124) {
               invoke18(renderManager7, f, h, fs[intValue18] - floatValue116, floatValue124, floatValue116, floatValue125 - floatValue124, floatValue117, o, q);
            }

            if (intValue18 < i - 1 && Math.abs(fs[intValue18] - fs[intValue18 + 1]) > 0.5F) {
               float floatValue126 = Math.min(fs[intValue18], fs[intValue18 + 1]);
               float floatValue127 = Math.max(fs[intValue18], fs[intValue18 + 1]);
               float floatValue128 = floatValue127 - floatValue126;
               float floatValue129 = this.measure(n, floatValue128, l, m);
               invoke18(renderManager7, f, h, floatValue126 + floatValue129 * 0.35F, g + floatValue119 - floatValue117, Math.max(1.0F, floatValue128 - floatValue129 * 0.7F), floatValue116, floatValue117, o, q);
            }
         }

         float floatValue130 = fs[i - 1];
         invoke18(renderManager7, f, h, n, g + k - floatValue116, Math.max(1.0F, floatValue130 - n * 2.0F), floatValue116, floatValue117, o, q);
      }
   }

   private void invoke8(
      RenderManager renderManager8,
      float f,
      float g,
      float h,
      float[] fs,
      float[] gs,
      int i,
      float j,
      float k,
      float l,
      float m,
      float n,
      float o,
      int p,
      float q,
      boolean bl,
      boolean bl2,
      float r
   ) {
      for (int intValue19 = 0; intValue19 < i; intValue19++) {
         float floatValue131 = intValue19 == 0 ? 0.0F : gs[intValue19 - 1];
         float floatValue132 = gs[intValue19] - floatValue131;
         float floatValue133 = intValue19 == 0 ? fs[intValue19] : fs[intValue19 - 1];
         float floatValue134 = intValue19 == i - 1 ? fs[intValue19] : fs[intValue19 + 1];
         float floatValue135 = intValue19 == 0 ? n : 0.0F;
         float floatValue136 = intValue19 == i - 1 ? n : 0.0F;
         float floatValue137 = intValue19 == 0 ? n : (fs[intValue19] > floatValue133 + 0.5F ? this.measure(n, fs[intValue19] - floatValue133, l, m) : 0.0F);
         float floatValue138 = intValue19 == i - 1 ? n : (fs[intValue19] > floatValue134 + 0.5F ? this.measure(n, fs[intValue19] - floatValue134, l, m) : 0.0F);
         this.invoke12(renderManager8, f, g + floatValue131, h, 0.0F, fs[intValue19], floatValue132, floatValue135, floatValue137, floatValue138, floatValue136, p, q, bl, bl2, r);
      }
   }

   private void invoke9(
      RenderManager renderManager9, float f, float g, float h, float[] fs, float[] gs, int i, float j, float k, float l, float m, float n, int o, float p
   ) {
      int intValue20 = ColorUtils.compute4(o);
      if (intValue20 > 0) {
         float floatValue139 = 0.0F;
         this.invoke8(renderManager9, f, g, h, fs, gs, i, j, k, l, m, n, floatValue139, o, 1.0F, false, true, p);
      }
   }

   private void invoke10(
      RenderManager renderManager10,
      float f,
      float g,
      float h,
      float[] fs,
      float[] gs,
      int i,
      float j,
      float k,
      float l,
      float m,
      float n,
      float o,
      int p,
      float q,
      float r,
      float s
   ) {
      if (ColorUtils.compute4(p) > 0 && !(q <= 0.0F) && !(r <= 0.0F)) {
         for (int intValue21 = 0; intValue21 < i; intValue21++) {
            float floatValue140 = intValue21 == 0 ? 0.0F : gs[intValue21 - 1];
            float floatValue141 = gs[intValue21] - floatValue140;
            float floatValue142 = intValue21 == 0 ? fs[intValue21] : fs[intValue21 - 1];
            float floatValue143 = intValue21 == i - 1 ? fs[intValue21] : fs[intValue21 + 1];
            float floatValue144 = intValue21 == 0 ? n : 0.0F;
            float floatValue145 = intValue21 == i - 1 ? n : 0.0F;
            float floatValue146 = intValue21 == 0 ? n : (fs[intValue21] > floatValue142 + 0.5F ? this.measure(n, fs[intValue21] - floatValue142, l, m) : 0.0F);
            float floatValue147 = intValue21 == i - 1 ? n : (fs[intValue21] > floatValue143 + 0.5F ? this.measure(n, fs[intValue21] - floatValue143, l, m) : 0.0F);
            this.invoke11(renderManager10, f, g + floatValue140, h, 0.0F, fs[intValue21], floatValue141, floatValue144, floatValue146, floatValue147, floatValue145, p, q, r, s);
         }
      }
   }

   private void invoke11(
      RenderManager renderManager11, float f, float g, float h, float i, float j, float k, float l, float m, float n, float o, int p, float q, float r, float s
   ) {
      if (!(j <= 0.5F) && !(k <= 0.5F)) {
         float floatValue148 = f + measure2(i, h - i - j, s);
         float floatValue149 = measure2(l, m, s);
         float floatValue150 = measure2(m, l, s);
         float floatValue151 = measure2(n, o, s);
         float floatValue152 = measure2(o, n, s);
         renderManager11.invoke42(floatValue148, g, j, k, floatValue149, floatValue150, floatValue151, floatValue152, q, r, p);
      }
   }

   private void invoke12(
      RenderManager renderManager12,
      float f,
      float g,
      float h,
      float i,
      float j,
      float k,
      float l,
      float m,
      float n,
      float o,
      int p,
      float q,
      boolean bl,
      boolean bl2,
      float r
   ) {
      if (!(j <= 0.5F) && !(k <= 0.5F)) {
         float floatValue153 = f + measure2(i, h - i - j, r);
         float floatValue154 = measure2(l, m, r);
         float floatValue155 = measure2(m, l, r);
         float floatValue156 = measure2(n, o, r);
         float floatValue157 = measure2(o, n, r);
         if (bl) {
            renderManager12.invoke48(23.0F);
            renderManager12.invoke45(floatValue153, g, j, k, floatValue154, floatValue155, floatValue156, floatValue157, q);
         }

         if (bl2) {
            renderManager12.invoke6(floatValue153, g, j, k, floatValue154, floatValue155, floatValue156, floatValue157, p);
         }
      }
   }

   private float measure(float f, float g, float h, float i) {
      float floatValue158 = Math.max(4.0F * i, 3.0F);
      float floatValue159 = Math.max(0.0F, g) * 0.72F;
      float floatValue160 = h * 0.42F;
      return Math.min(f, Math.max(floatValue158, Math.min(floatValue159, floatValue160)));
   }

   private int compute2Self(float f) {
      int intValue22 = this.compute(f);
      if (!"Тёмный".equals(this.stilistika.getValue()) && (this.checkSelf() || !this.check9())) {
         return intValue22;
      } else {
         int intValue23 = Math.round((210.0F - 92.0F * this.measure3()) * f);
         return ColorUtils.compute2(intValue22, Math.max(ColorUtils.compute4(intValue22), intValue23));
      }
   }

   private int compute3Self(float f) {
      int intValue24 = this.compute(f);
      if ("Тёмный".equals(this.stilistika.getValue())) {
         int intValue25 = Math.round(210.0F * f);
         return ColorUtils.compute2(intValue24, Math.max(ColorUtils.compute4(intValue24), intValue25));
      } else {
         return intValue24;
      }
   }

   private void invoke13() {
      ARRAY_LIST.clear();
      ITEMS.clear();
      boolean flag18 = this.checkSelf();

      for (Module module2 : WildClient.INSTANCE.moduleManager.getModules()) {
         if (module2 != null && module2.category != Category.Visuals && !"Menu".equals(module2.name) && this.check2(module2.category)) {
            ArrayListHud.ArrayListHudState arrayListHudState10 = VALUES_BY_KEY.computeIfAbsent(module2, ArrayListHud.ArrayListHudState::new);
            arrayListHudState10.invoke(module2);
            if (flag18) {
               arrayListHudState10.animation.check();
               arrayListHudState10.animation.resolve4(module2.enabled ? 1.0 : 0.0, 0.23F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
               if (module2.enabled) {
                  ARRAY_LIST.add(module2);
               }

               if (module2.enabled || arrayListHudState10.animation.measure3() > 0.01F) {
                  ITEMS.add(arrayListHudState10);
               }
            } else {
               float floatValue161 = arrayListHudState10.springAnimation.measure(module2.enabled ? 1.0F : 0.0F, module2.enabled ? SPRING_SPEC_5 : SPRING_SPEC_6);
               arrayListHudState10.springAnimation2.measure(module2.enabled ? 1.0F : 0.0F, SPRING_SPEC_9);
               arrayListHudState10.springAnimation3.measure(0.0F, SPRING_SPEC_10);
               if (module2.enabled) {
                  ARRAY_LIST.add(module2);
               }

               if (module2.enabled || floatValue161 > 0.01F || Math.abs(arrayListHudState10.springAnimation.getFloatValue2()) > 0.01F) {
                  ITEMS.add(arrayListHudState10);
               }
            }
         }
      }

      ITEMS.sort(ArrayListHud.ArrayListHudState.COMPARATOR);
   }

   private boolean check2(Category category) {
      return switch (category == null ? Category.Misc : category) {
         case Combat -> this.filtr.isEnabled("Combat");
         case Movement -> this.filtr.isEnabled("Movement");
         case Player -> this.filtr.isEnabled("Player");
         case Misc -> this.filtr.isEnabled("Misc");
         case Visuals -> false;
      };
   }

   private void invoke14(
      RenderManager renderManager13,
      ArrayListHud.ArrayListHudState arrayListHudState11,
      float f,
      float g,
      float h,
      float i,
      float j,
      float k,
      float l,
      float m,
      float n,
      float o,
      int p,
      int q,
      boolean bl,
      boolean bl2,
      boolean bl3,
      float r
   ) {
      int intValue26 = Math.round(255.0F * n * o);
      int intValue27 = ColorUtils.compute2(p, intValue26);
      int intValue28 = ColorUtils.compute2(q, Math.round(ColorUtils.compute4(q) * o));
      float floatValue162 = bl ? 8.0F * l : 0.0F;
      float floatValue163 = k * 0.92F;
      float floatValue164 = bl2 && arrayListHudState11.text2 != null ? TextMeasureCache.measure(FontRegistry.fontObject8, arrayListHudState11.text2, floatValue163) : 0.0F;
      float floatValue165 = bl2 && arrayListHudState11.text2 != null ? 7.0F * l : 0.0F;
      String text = bl3 ? arrayListHudState11.text3 : "";
      boolean flag19 = !text.isEmpty();
      float floatValue166 = flag19 ? 6.0F * l : 0.0F;
      float floatValue167 = flag19 ? TextMeasureCache.measure(FontRegistry.fontObject, text, k) : 0.0F;
      float floatValue168 = Math.max(12.0F * l, h - j * 2.0F - floatValue162 - floatValue164 - floatValue165 - floatValue166 - floatValue167);
      String text2 = resolve(arrayListHudState11.text, k, floatValue168);
      float floatValue169 = TextMeasureCache.measure(FontRegistry.fontObject4, text2, k);
      float floatValue170 = floatValue164 + floatValue165 + floatValue169 + floatValue166 + floatValue167;
      float floatValue171 = f + j + floatValue162;
      float floatValue172 = floatValue171 + (floatValue164 > 0.0F ? floatValue164 + floatValue165 : 0.0F);
      float floatValue173 = f + h - j - floatValue162 - floatValue170;
      float floatValue174 = floatValue173 + (floatValue164 > 0.0F ? floatValue164 + floatValue165 : 0.0F);
      float floatValue175 = measure2(floatValue171, floatValue173, r);
      float floatValue176 = measure2(floatValue172, floatValue174, r);
      float floatValue177 = floatValue176 + floatValue169 + floatValue166;
      if (bl2 && arrayListHudState11.text2 != null) {
         int intValue29 = ColorUtils.compute2(this.compute9(n), intValue26);
         renderManager13.invoke69(FontRegistry.fontObject8, floatValue175, g + i * 0.5F + 5.0F * m, floatValue163, arrayListHudState11.text2, intValue29);
      }

      float floatValue178 = g + i * 0.5F + 5.0F * m;
      renderManager13.invoke69(FontRegistry.fontObject4, floatValue176, floatValue178, k, text2, intValue27);
      if (flag19) {
         renderManager13.invoke69(FontRegistry.fontObject, floatValue177, floatValue178, k, text, intValue28);
      }

      if (o < 0.98F) {
         float floatValue179 = 3.0F * m;
         renderManager13.invoke39(f + h - j * 0.5F, g + i * 0.5F, floatValue179, 0.0F, 360.0F, intValue28);
      }
   }

   private void invoke15(
      RenderManager renderManager14,
      ArrayListHud.ArrayListHudState arrayListHudState12,
      float f,
      float g,
      float h,
      float i,
      float j,
      float k,
      float l,
      float m,
      float n,
      float o,
      int p,
      int q,
      boolean bl,
      boolean bl2,
      boolean bl3,
      float r
   ) {
      if (!(h <= 2.0F) && !(i <= 4.0F) && !(o <= 0.005F)) {
         float floatValue180 = measure3(arrayListHudState12.springAnimation2.getFloatValue());
         float floatValue181 = Math.min(1.0F, Math.max(0.0F, arrayListHudState12.springAnimation3.getFloatValue()));
         int intValue30 = Math.round(255.0F * n * o * (0.58F + floatValue180 * 0.42F));
         int intValue31 = ColorUtils.compute2(p, intValue30);
         int intValue32 = ColorUtils.compute2(q, Math.round(ColorUtils.compute4(q) * o));
         float floatValue182 = bl ? 8.0F * l : 0.0F;
         float floatValue183 = k * 0.92F;
         float floatValue184 = bl2 && arrayListHudState12.text2 != null ? TextMeasureCache.measure(FontRegistry.fontObject8, arrayListHudState12.text2, floatValue183) : 0.0F;
         float floatValue185 = bl2 && arrayListHudState12.text2 != null ? 7.0F * l : 0.0F;
         String text3 = bl3 ? arrayListHudState12.text3 : "";
         boolean flag20 = !text3.isEmpty();
         float floatValue186 = flag20 ? 6.0F * l : 0.0F;
         float floatValue187 = flag20 ? TextMeasureCache.measure(FontRegistry.fontObject, text3, k) : 0.0F;
         float floatValue188 = Math.max(12.0F * l, h - j * 2.0F - floatValue182 - floatValue184 - floatValue185 - floatValue186 - floatValue187);
         String text4 = resolve(arrayListHudState12.text, k, floatValue188);
         float floatValue189 = TextMeasureCache.measure(FontRegistry.fontObject4, text4, k);
         float floatValue190 = floatValue184 + floatValue185 + floatValue189 + floatValue186 + floatValue187;
         float floatValue191 = f + j + floatValue182;
         float floatValue192 = floatValue191 + (floatValue184 > 0.0F ? floatValue184 + floatValue185 : 0.0F);
         float floatValue193 = f + h - j - floatValue182 - floatValue190;
         float floatValue194 = floatValue193 + (floatValue184 > 0.0F ? floatValue184 + floatValue185 : 0.0F);
         float floatValue195 = measure2(floatValue191, floatValue193, r);
         float floatValue196 = measure2(floatValue192, floatValue194, r);
         float floatValue197 = floatValue196 + floatValue189 + floatValue186;
         float floatValue198 = g + i * 0.5F + 5.0F * m + measure4(arrayListHudState12.springAnimation5.getFloatValue2() * 0.018F, -2.8F * m, 2.8F * m);
         float floatValue199 = Math.min(12.0F * m, Math.max(1.0F, i * 0.5F));
         renderManager14.invoke24(f + 1.0F, g + 1.0F, Math.max(1.0F, h - 2.0F), Math.max(1.0F, i - 2.0F), floatValue199, floatValue199, floatValue199, floatValue199);

         try {
            if (bl2 && arrayListHudState12.text2 != null) {
               int intValue33 = ColorUtils.compute14(
                  ColorUtils.compute2(this.compute9(n), intValue30), ColorUtils.compute43(255, 255, 255, intValue30), floatValue181 * 0.22F
               );
               renderManager14.invoke69(FontRegistry.fontObject8, floatValue195, floatValue198, floatValue183, arrayListHudState12.text2, intValue33);
            }

            int intValue34 = ColorUtils.compute14(intValue31, ColorUtils.compute43(255, 255, 255, intValue30), floatValue181 * 0.14F);
            renderManager14.invoke69(FontRegistry.fontObject4, floatValue196, floatValue198, k, text4, intValue34);
            if (flag20) {
               renderManager14.invoke69(FontRegistry.fontObject, floatValue197, floatValue198, k, text3, intValue32);
            }
         } finally {
            renderManager14.invoke25();
         }

         if (o < 0.98F) {
            float floatValue200 = 3.0F * m;
            renderManager14.invoke39(f + h - j * 0.5F, g + i * 0.5F, floatValue200, 0.0F, 360.0F, intValue32);
         }
      }
   }

   private void invoke16(RenderManager renderManager15, float f, float g, float h, float i, float j, float k, float l, int m, boolean bl) {
      float floatValue201 = Math.min(10.0F, i * 0.45F);
      this.invoke17(renderManager15, f, g, h, i, floatValue201, l, bl);
      renderManager15.invoke69(FontRegistry.fontObject, f + j, g + i * 0.5F + 5.0F, k, "Нет активных модулей ", m);
   }

   private void invoke17(RenderManager renderManager16, float f, float g, float h, float i, float j, float k, boolean bl) {
      if (this.vizual.isEnabled("Тень")) {
         if (bl) {
            renderManager16.invoke41(f, g, h, i, j, 4.0F, 1.0F, ColorUtils.compute43(0, 0, 0, Math.round(80.0F * k)));
         } else {
            renderManager16.invoke41(f, g, h, i, j, this.check17() ? 6.0F : 4.0F, 1.0F, this.compute17(k));
         }
      }

      if (this.check7()) {
         renderManager16.invoke48(23.0F);
         renderManager16.invoke44(f, g, h, i, j, k);
      }

      if (!bl && this.check9()) {
         this.invoke2(renderManager16, f, g, h, i, j, k);
      } else if (this.check8()) {
         renderManager16.invoke5(f, g, h, i, j, this.compute(k));
      } else {
         renderManager16.invoke5(f, g, h, i, j, this.check5() ? this.compute3Self(k) : this.compute(k));
         if (this.check2()) {
            renderManager16.invoke28(f, g, h, i, j, this.compute5(k), Math.max(1.0F, this.measure2() * 0.55F));
         }
      }
   }

   private static void invoke18(RenderManager renderManager17, float f, float g, float h, float i, float j, float k, float l, int m, float n) {
      renderManager17.invoke5(f + measure2(h, g - h - j, n), i, j, k, l, m);
   }

   private static float measure2(float f, float g, float h) {
      return f + (g - f) * Math.max(0.0F, Math.min(1.0F, h));
   }

   private static float measure3(float f) {
      return Math.max(0.0F, Math.min(1.0F, f));
   }

   private static float measure4(float f, float g, float h) {
      return Math.max(g, Math.min(h, f));
   }

   private static boolean check3(float f, float g, float h, float i, float j, float k) {
      return f >= h && f <= h + j && g >= i && g <= i + k;
   }

   private static String resolve(String string, float f, float g) {
      if (string != null && !string.isEmpty() && !(TextMeasureCache.measure(FontRegistry.fontObject4, string, f) <= g)) {
         String text5 = "...";
         float floatValue202 = TextMeasureCache.measure(FontRegistry.fontObject4, text5, f);
         if (floatValue202 >= g) {
            return text5;
         } else {
            int intValue35 = 0;
            int intValue36 = string.length();

            while (intValue35 < intValue36) {
               int intValue37 = intValue35 + intValue36 + 1 >>> 1;
               if (TextMeasureCache.measure(FontRegistry.fontObject4, string.substring(0, intValue37), f) + floatValue202 <= g) {
                  intValue35 = intValue37;
               } else {
                  intValue36 = intValue37 - 1;
               }
            }

            return intValue35 <= 0 ? text5 : string.substring(0, intValue35) + text5;
         }
      } else {
         return string == null ? "" : string;
      }
   }

   static final class ArrayListHudState {
      static final Comparator<ArrayListHud.ArrayListHudState> COMPARATOR = (arrayListHudState13, arrayListHudState14) -> {
         int intValue38 = Float.compare(arrayListHudState14.floatValue, arrayListHudState13.floatValue);
         return intValue38 != 0 ? intValue38 : arrayListHudState13.text.compareToIgnoreCase(arrayListHudState14.text);
      };
      private final Module module;
      final Animation animation = new Animation();
      final SpringAnimation springAnimation = new SpringAnimation(0.0F);
      final SpringAnimation springAnimation2 = new SpringAnimation(0.0F);
      final SpringAnimation springAnimation3 = new SpringAnimation(0.0F);
      final SpringAnimation springAnimation4 = new SpringAnimation(0.0F);
      final SpringAnimation springAnimation5 = new SpringAnimation(0.0F);
      private final SpringAnimation springAnimation6 = new SpringAnimation(1.0F);
      private final SpringAnimation springAnimation7 = new SpringAnimation(1.0F);
      String text = "";
      String text2;
      String text3 = "";
      private float floatValue;
      private float floatValue2;
      private float floatValue3;
      float floatValue4;
      float floatValue5;
      float floatValue6 = 1.0F;
      float floatValue7 = 1.0F;
      private boolean flag;
      private boolean flag2;
      private boolean flag3;
      boolean flag4;

      private ArrayListHudState(Module module) {
         this.module = module;
      }

      void invoke(Module module) {
         boolean flag21 = module.enabled;
         if (!this.flag) {
            this.springAnimation.invoke(flag21 ? 1.0F : 0.0F);
            this.springAnimation2.invoke(flag21 ? 1.0F : 0.0F);
            this.flag3 = flag21;
            this.flag = true;
         } else if (flag21 != this.flag3) {
            this.springAnimation3.setFloatValue(Math.min(2.25F, this.springAnimation3.getFloatValue() + (flag21 ? 1.2F : 0.72F)));
            if (flag21) {
               this.springAnimation2.invoke(0.0F);
            }

            this.flag3 = flag21;
         }

         String text6 = module.getDisplayName();
         if (text6 == null || text6.isEmpty()) {
            text6 = module.name;
         }

         if (!text6.equals(this.text)) {
            this.text = text6;
            this.floatValue = TextMeasureCache.measure(FontRegistry.fontObject4, this.text, 24.0F);
         }

         String text7 = module.category == null ? null : module.category.getIconGlyph();
         if (text7 == null) {
            this.text2 = null;
            this.floatValue2 = 0.0F;
         } else if (!text7.equals(this.text2)) {
            this.text2 = text7;
            this.floatValue2 = TextMeasureCache.measure(FontRegistry.fontObject8, this.text2, 22.08F);
         }

         String text8 = module.bindKey == -1 ? "" : "[" + KeyboardKey.resolve(module.bindKey) + "]";
         if (!text8.equals(this.text3)) {
            this.text3 = text8;
            this.floatValue3 = this.text3.isEmpty() ? 0.0F : TextMeasureCache.measure(FontRegistry.fontObject, this.text3, 24.0F);
         }
      }

      float measure(float f, float g, boolean bl, boolean bl2, boolean bl3) {
         float floatValue203 = bl2 && this.text2 != null ? TextMeasureCache.measure(FontRegistry.fontObject8, this.text2, f * 0.92F) + 7.0F : 0.0F;
         float floatValue204 = bl3 && !this.text3.isEmpty() ? TextMeasureCache.measure(FontRegistry.fontObject, this.text3, f) + 6.0F : 0.0F;
         return TextMeasureCache.measure(FontRegistry.fontObject4, this.text, f) + floatValue203 + floatValue204 + g * 2.0F + (bl ? 8.0F : 0.0F);
      }

      void invoke2(float f, float g, float h, float i) {
         if (!this.flag2) {
            this.springAnimation4.invoke(f);
            this.springAnimation5.invoke(g);
            this.springAnimation6.invoke(h);
            this.springAnimation7.invoke(i);
            this.flag2 = true;
         } else {
            this.springAnimation4.measure(f, ArrayListHud.SPRING_SPEC_7);
            this.springAnimation5.measure(g, ArrayListHud.SPRING_SPEC_7);
            this.springAnimation6.measure(h, ArrayListHud.SPRING_SPEC_8);
            this.springAnimation7.measure(i, ArrayListHud.SPRING_SPEC_8);
         }

         this.floatValue4 = this.springAnimation4.getFloatValue();
         this.floatValue5 = this.springAnimation5.getFloatValue();
         this.floatValue6 = Math.max(0.0F, this.springAnimation6.getFloatValue());
         this.floatValue7 = Math.max(0.0F, this.springAnimation7.getFloatValue());
      }

      boolean isFlag4() {
         return this.flag4;
      }
   }
}
