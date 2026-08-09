package ru.metaculture.protection;

import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;
import java.util.Locale;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;

public final class CoreDiagnosticsOverlay {
   private static final String DIAGNOSTIKA_WILD_CORE = "Диагностика Wild Core";
   private static final String RENDER_SHEYDERY_GL_ILOKALNYE_SLEPKI = "рендер, шейдеры, GL и локальные слепки";
   private static final String SLEPOK = "Слепок";
   private static final String PAPKA = "Папка";
   private static final String LOGI = "Логи";
   private static final String SOSTOYANIE = "Состояние";
   private static final String TRACKER_ID = "Tracker ID";
   private static final String CODE = "Code";
   private static final String OCHERED = "Очередь";
   private static final String OSHIBKI = "Ошибки";
   private static final String CFI_CHAIN = "CFI chain";
   private static final String TEKSTURNYE_YUNITY = "Текстурные Юниты";
   private static final String MATRITSY = "Матрицы";
   private static final String FRAMES = "Frames";
   private static final String ANOMALIES = "Anomalies";
   private static final String CHTO_SEYCHAS_LOMAETSYA = "Что сейчас ломается";
   private static final String FAYL_SLEPKA = "Файл слепка";
   private static final String MIXIN_POLICY = "Mixin policy";
   private static final String PRIVACY = "Privacy";
   private static final String GAYDLAYN = "Гайдлайн";
   private static final String TEXT1_SMOTRI_CODE_STAGE = "1 смотри Code/Stage";
   private static final String TEXT2_ZHMI_SLEPOK = "2 жми Слепок";
   private static final String TEXT3_OTKROY_LOGI = "3 открой Логи";
   private static final String TEXT4_PEREDAY_TRACKER_ID = "4 передай Tracker ID";
   private static final String SHEYDERNYH_ISKLYUCHENIY_NET = "Шейдерных исключений нет";
   private static final String NAZHMI_LOGI_CHTOBY_ZAGRUZIT_LATEST_LOG = "Нажми Логи, чтобы загрузить latest.log";
   private static final String VSTROENNYY_VIEWER = "Встроенный viewer";
   private static final String LATEST_LOG_TAIL = "latest.log tail";
   private static final String BUFER_SOBYTIY = "Буфер событий";
   private static final String CORE_LOAD = "Core Load";
   private static final String RENDER_TPS = "Render TPS";
   private static final String LATENCY = "Latency";
   private static final float FLOAT_VALUE = 44.0F;
   private static final float FLOAT_VALUE_2 = 10.0F;
   private static final SpringSpec SPRING_SPEC = SpringSpec.resolve13();
   private final RenderDiagnosticsStatus renderDiagnosticsStatus = new RenderDiagnosticsStatus();
   private final CoreDiagnosticsOverlay.CoreDiagnosticsOverlayState coreDiagnosticsOverlayState = new CoreDiagnosticsOverlay.CoreDiagnosticsOverlayState();
   private final SpringValue springValue = new SpringValue(0.0F);
   private long timestamp = Long.MIN_VALUE;
   private static float floatValue;
   private static float floatValue2;
   private static float floatValue3;
   private static float floatValue4;
   private static float floatValue5;
   private static float floatValue6;
   private static float floatValue7;
   private static float floatValue8;

   public static float measure(ClickGuiGeometry clickGuiGeometry, Metrics metrics) {
      return Math.round(
         measure32(clickGuiGeometry, metrics)
            + measure34(clickGuiGeometry, metrics)
            - measure5(metrics)
            - measure6(metrics)
            - measure7(metrics)
            - metrics.measure(16.0F)
      );
   }

   public static float measure2(ClickGuiGeometry clickGuiGeometry2, Metrics metrics2) {
      return Math.round(
         measure32(clickGuiGeometry2, metrics2)
            + measure34(clickGuiGeometry2, metrics2)
            - measure6(metrics2)
            - measure7(metrics2)
            - metrics2.measure(8.0F)
      );
   }

   public static float measure3(ClickGuiGeometry clickGuiGeometry3, Metrics metrics3) {
      return Math.round(measure32(clickGuiGeometry3, metrics3) + measure34(clickGuiGeometry3, metrics3) - measure7(metrics3));
   }

   public static float measure4(ClickGuiGeometry clickGuiGeometry4, Metrics metrics4) {
      return Math.round(measure33(clickGuiGeometry4, metrics4) + metrics4.measure(3.0F));
   }

   public static float measure5(Metrics metrics5) {
      return metrics5.measure(94.0F);
   }

   public static float measure6(Metrics metrics6) {
      return metrics6.measure(78.0F);
   }

   public static float measure7(Metrics metrics7) {
      return metrics7.measure(68.0F);
   }

   public static float measure8(Metrics metrics8) {
      return metrics8.measure(24.0F);
   }

   public static boolean check(ClickGuiGeometry clickGuiGeometry5, Metrics metrics9, float f, float g) {
      return ClickGuiRenderUtils.check2(
         f,
         g,
         measure11(clickGuiGeometry5, metrics9),
         measure12(clickGuiGeometry5, metrics9),
         measure13(clickGuiGeometry5, metrics9),
         measure14(clickGuiGeometry5, metrics9)
      );
   }

   public static boolean check2(ClickGuiGeometry clickGuiGeometry6, Metrics metrics10, float f, float g) {
      if (!(floatValue7 <= 0.5F) && !(floatValue3 <= 1.0F) && !(floatValue4 <= 1.0F)) {
         float floatValue = measure26(metrics10);
         float actionY = Math.round(floatValue2 + floatValue4 - floatValue);
         return ClickGuiRenderUtils.check2(f, g, floatValue, actionY - metrics10.measure(4.0F), floatValue3, metrics10.measure(12.0F));
      } else {
         return false;
      }
   }

   public static boolean check3(ClickGuiGeometry clickGuiGeometry7, Metrics metrics11, float f, float g) {
      if (!(floatValue8 <= 0.5F) && !(floatValue3 <= 1.0F) && !(floatValue4 <= 1.0F)) {
         float floatValue3 = measure26(metrics11);
         float actionX = Math.round(floatValue + floatValue3 - floatValue3);
         return ClickGuiRenderUtils.check2(f, g, actionX - metrics11.measure(4.0F), floatValue2, metrics11.measure(12.0F), actionX);
      } else {
         return false;
      }
   }

   public static float measure9(ClickGuiGeometry clickGuiGeometry8, Metrics metrics12, float f) {
      float floatValue5 = measure27(metrics12);
      float floatValue6 = Math.max(1.0F, floatValue3 - floatValue5);
      return measure19((f - floatValue - floatValue5 * 0.5F) / floatValue6);
   }

   public static float measure10(ClickGuiGeometry clickGuiGeometry9, Metrics metrics13, float f) {
      float floatValue7 = measure28(metrics13);
      float floatValue8 = Math.max(1.0F, floatValue4 - floatValue7);
      return measure19((f - floatValue2 - floatValue7 * 0.5F) / floatValue8);
   }

   private static float measure11(ClickGuiGeometry clickGuiGeometry10, Metrics metrics14) {
      float floatValue9 = measure32(clickGuiGeometry10, metrics14);
      float floatValue10 = measure34(clickGuiGeometry10, metrics14);
      float floatValue11 = measure21(floatValue10, metrics14);
      return Math.round(floatValue9 + floatValue11 + metrics14.measure(10.0F));
   }

   private static float measure12(ClickGuiGeometry clickGuiGeometry11, Metrics metrics15) {
      float floatValue12 = measure33(clickGuiGeometry11, metrics15);
      float floatValue13 = measure35(metrics15);
      float floatValue14 = Math.round(floatValue12 + metrics15.measure(44.0F));
      float floatValue15 = Math.round(floatValue13 - metrics15.measure(44.0F));
      float floatValue16 = measure22(floatValue15, metrics15);
      return Math.round(floatValue14 + floatValue16 + metrics15.measure(8.0F));
   }

   private static float measure13(ClickGuiGeometry clickGuiGeometry12, Metrics metrics16) {
      float floatValue17 = measure34(clickGuiGeometry12, metrics16);
      float floatValue18 = measure21(floatValue17, metrics16);
      return Math.round(floatValue17 - floatValue18 - metrics16.measure(10.0F));
   }

   private static float measure14(ClickGuiGeometry clickGuiGeometry13, Metrics metrics17) {
      float floatValue19 = measure35(metrics17);
      float floatValue20 = Math.round(floatValue19 - metrics17.measure(44.0F));
      float floatValue21 = measure22(floatValue20, metrics17);
      return Math.max(metrics17.measure(24.0F), floatValue20 - floatValue21 - metrics17.measure(8.0F));
   }

   private static float measure15(ClickGuiGeometry clickGuiGeometry14, Metrics metrics18) {
      return Math.round(measure11(clickGuiGeometry14, metrics18) + metrics18.measure(10.0F));
   }

   private static float measure16(ClickGuiGeometry clickGuiGeometry15, Metrics metrics19) {
      return Math.round(measure12(clickGuiGeometry15, metrics19) + metrics19.measure(30.0F));
   }

   private static float measure17(ClickGuiGeometry clickGuiGeometry16, Metrics metrics20) {
      return Math.round(measure13(clickGuiGeometry16, metrics20) - metrics20.measure(20.0F));
   }

   private static float measure18(ClickGuiGeometry clickGuiGeometry17, Metrics metrics21) {
      return Math.max(metrics21.measure(24.0F), measure14(clickGuiGeometry17, metrics21) - metrics21.measure(38.0F));
   }

   private static float measure19(float f) {
      return Math.max(0.0F, Math.min(1.0F, f));
   }

   private static float measure20(float f, float g, float h) {
      return Math.max(g, Math.min(h, f));
   }

   private static float measure21(float f, Metrics metrics22) {
      return Math.round(measure20(f * 0.265F, metrics22.measure(150.0F), metrics22.measure(182.0F)));
   }

   private static float measure22(float f, Metrics metrics23) {
      return Math.round(measure20(f * 0.31F, metrics23.measure(104.0F), metrics23.measure(124.0F)));
   }

   public void invoke(RenderManager renderManager, ClickGuiState clickGuiState, ClickGuiGeometry clickGuiGeometry18, ThemeContext themeContext) {
      RenderDiagnosticsTracker.getInstance().invoke21(this.renderDiagnosticsStatus);
      Metrics metrics24 = themeContext.getMetrics();
      ColorScheme colorScheme = themeContext.getColorScheme();
      long longValue = clickGuiState.getTimestamp();
      if (longValue != this.timestamp) {
         this.timestamp = longValue;
         this.invoke2();
      }

      this.coreDiagnosticsOverlayState.invoke2();
      float floatValue22 = clickGuiState.measure7(AnimationKeyRegistry.resolve7());
      if (!(floatValue22 <= 0.001F)) {
         float floatValue23 = measure32(clickGuiGeometry18, metrics24);
         float floatValue24 = measure33(clickGuiGeometry18, metrics24);
         float floatValue25 = measure34(clickGuiGeometry18, metrics24);
         float floatValue26 = measure35(metrics24);
         renderManager.invoke65(floatValue22);

         try {
            this.invoke3(renderManager, metrics24, colorScheme, floatValue23, floatValue24, floatValue25, floatValue22);
            float floatValue27 = Math.round(floatValue24 + metrics24.measure(44.0F));
            float floatValue28 = Math.round(floatValue26 - metrics24.measure(44.0F));
            float floatValue29 = measure21(floatValue25, metrics24);
            this.invoke5(renderManager, metrics24, colorScheme, floatValue23, floatValue27, floatValue29, floatValue28);
            float floatValue30 = Math.round(floatValue23 + floatValue29 + metrics24.measure(10.0F));
            float floatValue31 = Math.round(floatValue25 - floatValue29 - metrics24.measure(10.0F));
            float floatValue32 = this.renderDiagnosticsStatus.flag2 ? 1.0F : 0.0F;
            float floatValue33 = measure19(
               clickGuiState.measure5(AnimationKeyRegistry.resolve8(), floatValue32, floatValue32 > 0.0F ? SpringSpec.resolve13() : SpringSpec.resolve16())
            );
            float floatValue34 = measure24(floatValue33);
            renderManager.invoke20();
            renderManager.invoke24(floatValue30, floatValue27, floatValue31, floatValue28, metrics24.measure(9.0F), metrics24.measure(9.0F), metrics24.measure(9.0F), metrics24.measure(9.0F));

            try {
               if (floatValue34 < 0.999F) {
                  renderManager.invoke65(1.0F - floatValue34);
                  renderManager.invoke56(-metrics24.measure(14.0F) * floatValue34, 0.0F);
                  renderManager.invoke62(1.0F - floatValue34 * 0.018F, floatValue30 + floatValue31 * 0.5F, floatValue27 + floatValue28 * 0.5F);

                  try {
                     this.invoke6(renderManager, metrics24, colorScheme, floatValue30, floatValue27, floatValue31, floatValue28);
                  } finally {
                     renderManager.invoke64();
                     renderManager.invoke57();
                     renderManager.invoke66();
                  }
               }

               if (floatValue34 > 0.001F) {
                  renderManager.invoke65(floatValue34);
                  renderManager.invoke56(metrics24.measure(18.0F) * (1.0F - floatValue34), metrics24.measure(5.0F) * (1.0F - floatValue34));
                  renderManager.invoke62(0.982F + floatValue34 * 0.018F, floatValue30 + floatValue31 * 0.5F, floatValue27 + floatValue28 * 0.5F);
                  boolean flag = false ;

                  try {
                     flag = true;
                     this.invoke7(renderManager, clickGuiState, metrics24, colorScheme, floatValue30, floatValue27, floatValue31, floatValue28);
                     flag = false;
                  } finally {
                     if (flag) {
                        renderManager.invoke64();
                        renderManager.invoke57();
                        renderManager.invoke66();
                     }
                  }

                  renderManager.invoke64();
                  renderManager.invoke57();
                  renderManager.invoke66();
               }
            } finally {
               renderManager.invoke20();
               renderManager.invoke25();
            }

            if (floatValue34 <= 0.001F) {
               invoke24();
            }
         } finally {
            renderManager.invoke66();
         }
      }
   }

   private void invoke2() {
      this.coreDiagnosticsOverlayState.invoke();
      this.springValue.invoke2(0.0F);
      invoke24();
      RenderDiagnosticsTracker.getInstance().invoke17();
   }

   private void invoke3(RenderManager renderManager2, Metrics metrics25, ColorScheme colorScheme2, float f, float g, float h, float i) {
      float floatValue35 = metrics25.measure(32.0F);
      this.invoke4(renderManager2, metrics25, colorScheme2, f, g, floatValue35, i);
      float floatValue36 = f + floatValue35 + metrics25.measure(11.0F);
      if (!colorScheme2.isFlag()) {
         renderManager2.invoke21();

         try {
            ClickGuiRenderUtils.invoke4(
               renderManager2,
               metrics25,
               FontRegistry.fontObject4,
               floatValue36,
               g - metrics25.measure(1.0F),
               metrics25.measure(17.0F),
               13.0F,
               "Диагностика Wild Core",
               ColorScheme.compute6(colorScheme2.getIntValue14(), 40)
            );
         } finally {
            renderManager2.invoke22();
         }
      }

      ClickGuiRenderUtils.invoke4(
         renderManager2,
         metrics25,
         FontRegistry.fontObject4,
         floatValue36,
         g - metrics25.measure(1.0F),
         metrics25.measure(17.0F),
         13.0F,
         "Диагностика Wild Core",
         ClickGuiRenderUtils.compute2(colorScheme2)
      );
      ClickGuiRenderUtils.invoke4(
         renderManager2,
         metrics25,
         FontRegistry.fontObject,
         floatValue36,
         g + metrics25.measure(16.0F),
         metrics25.measure(14.0F),
         9.0F,
         "рендер, шейдеры, GL и локальные слепки",
         ClickGuiRenderUtils.compute4(colorScheme2)
      );
      this.invoke8(
         renderManager2,
         metrics25,
         colorScheme2,
         measure29(metrics25, f, h),
         g + metrics25.measure(3.0F),
         measure5(metrics25),
         "Слепок",
         colorScheme2.getIntValue14(),
         false,
         0
      );
      this.invoke8(
         renderManager2,
         metrics25,
         colorScheme2,
         measure30(metrics25, f, h),
         g + metrics25.measure(3.0F),
         measure6(metrics25),
         "Папка",
         colorScheme2.getIntValue15(),
         false,
         1
      );
      this.invoke8(
         renderManager2,
         metrics25,
         colorScheme2,
         measure31(metrics25, f, h),
         g + metrics25.measure(3.0F),
         measure7(metrics25),
         "Логи",
         colorScheme2.getIntValue14(),
         this.renderDiagnosticsStatus.flag2,
         2
      );
   }

   private void invoke4(RenderManager renderManager3, Metrics metrics26, ColorScheme colorScheme3, float f, float g, float h, float i) {
      renderManager3.invoke20();
      WildLogoShader.invoke(
         Math.round(f), Math.round(g), Math.round(h), colorScheme3.getIntValue14(), colorScheme3.getIntValue15(), measure19(i), colorScheme3.isFlag()
      );
      float floatValue37 = 0.5F + 0.5F * (float)Math.sin((float)System.currentTimeMillis() * 0.00108F);
      float floatValue38 = 15.0F;
      float floatValue39 = floatValue38 * (1.08F + floatValue37 * 0.04F);
      float floatValue40 = f + h * 0.5F;
      float floatValue41 = g + h * 0.5F;
      float floatValue42 = ClickGuiRenderUtils.measure2(metrics26, BrandMark.font(), BrandMark.GLYPH, floatValue38);
      float floatValue43 = ClickGuiRenderUtils.measure2(metrics26, BrandMark.font(), BrandMark.GLYPH, floatValue39);
      float floatValue44 = ClickGuiRenderUtils.measure3(metrics26, BrandMark.font(), floatValue38);
      float floatValue45 = ClickGuiRenderUtils.measure3(metrics26, BrandMark.font(), floatValue39);
      float floatValue46 = floatValue41 - floatValue44 * 0.5F - metrics26.measure(1.0F);
      float floatValue47 = floatValue41 - floatValue45 * 0.5F - metrics26.measure(1.0F);
      if (!colorScheme3.isFlag()) {
         renderManager3.invoke21();

         try {
            ClickGuiRenderUtils.invoke3(
               renderManager3,
               metrics26,
               BrandMark.font(),
               floatValue40 - floatValue43 * 0.5F,
               floatValue47,
               floatValue39,
               BrandMark.GLYPH,
               ColorScheme.compute7(ColorScheme.compute6(colorScheme3.getIntValue15(), 110), ColorScheme.compute6(colorScheme3.getIntValue14(), 130), floatValue37)
            );
         } finally {
            renderManager3.invoke22();
         }
      }

      ClickGuiRenderUtils.invoke3(
         renderManager3,
         metrics26,
         BrandMark.font(),
         floatValue40 - floatValue42 * 0.5F,
         floatValue46,
         floatValue38,
         BrandMark.GLYPH,
         ColorScheme.compute6(ClickGuiRenderUtils.compute7(colorScheme3), 246)
      );
   }

   private void invoke5(RenderManager renderManager4, Metrics metrics27, ColorScheme colorScheme4, float f, float g, float h, float i) {
      float floatValue48 = metrics27.measure(10.0F);
      int intValue = colorScheme4.isFlag()
         ? ClickGuiRenderUtils.compute12(colorScheme4, 0.35F)
         : ColorScheme.compute7(colorScheme4.getIntValue3(), colorScheme4.getIntValue5(), 0.36F);
      renderManager4.invoke5(f, g, h, i, floatValue48, intValue);
      renderManager4.invoke28(
         f,
         g,
         h,
         i,
         floatValue48,
         ColorScheme.compute6(colorScheme4.getIntValue13(), colorScheme4.isFlag() ? 92 : 20),
         Math.max(0.6F, metrics27.measure(0.65F))
      );
      float floatValue49 = metrics27.measure(9.0F);
      float floatValue50 = metrics27.measure(4.0F);
      float floatValue51 = metrics27.measure(35.0F);
      float floatValue52 = Math.max(metrics27.measure(180.0F), i - floatValue49 * 2.0F - floatValue51 - floatValue50 * 4.0F);
      float floatValue53 = floatValue52 / 5.0F;
      float floatValue54 = g + floatValue49;
      String text = this.renderDiagnosticsStatus.intValue == 0 ? "Nominal" : "Anomaly";
      this.invoke18(
         renderManager4,
         metrics27,
         colorScheme4,
         f + floatValue49,
         floatValue54,
         h - floatValue49 * 2.0F,
         floatValue53,
         "Состояние",
         text,
         this.renderDiagnosticsStatus.intValue == 0 ? colorScheme4.getIntValue14() : colorScheme4.compute2()
      );
      floatValue54 += floatValue53 + floatValue50;
      this.invoke18(
         renderManager4,
         metrics27,
         colorScheme4,
         f + floatValue49,
         floatValue54,
         h - floatValue49 * 2.0F,
         floatValue53,
         "Tracker ID",
         this.renderDiagnosticsStatus.none,
         ClickGuiRenderUtils.compute2(colorScheme4)
      );
      floatValue54 += floatValue53 + floatValue50;
      this.invoke18(
         renderManager4,
         metrics27,
         colorScheme4,
         f + floatValue49,
         floatValue54,
         h - floatValue49 * 2.0F,
         floatValue53,
         "Code",
         this.renderDiagnosticsStatus.none2,
         this.renderDiagnosticsStatus.intValue == 0 ? ClickGuiRenderUtils.compute4(colorScheme4) : colorScheme4.compute2()
      );
      floatValue54 += floatValue53 + floatValue50;
      this.invoke18(
         renderManager4,
         metrics27,
         colorScheme4,
         f + floatValue49,
         floatValue54,
         h - floatValue49 * 2.0F,
         floatValue53,
         "Ошибки",
         this.renderDiagnosticsStatus.text0,
         "0".equals(this.renderDiagnosticsStatus.text0) ? ClickGuiRenderUtils.compute4(colorScheme4) : colorScheme4.compute2()
      );
      floatValue54 += floatValue53 + floatValue50;
      this.invoke18(
         renderManager4,
         metrics27,
         colorScheme4,
         f + floatValue49,
         floatValue54,
         h - floatValue49 * 2.0F,
         floatValue53,
         "Очередь",
         this.renderDiagnosticsStatus.ozhidanie2,
         ClickGuiRenderUtils.compute2(colorScheme4)
      );
      float floatValue55 = Math.round(g + i - floatValue49 - floatValue51 + metrics27.measure(14.0F));
      float floatValue56 = Math.round(f + floatValue49);
      float floatValue57 = Math.round(h - floatValue49 * 2.0F);
      ClickGuiRenderUtils.invoke4(
         renderManager4,
         metrics27,
         FontRegistry.fontObject4,
         floatValue56,
         floatValue55,
         metrics27.measure(13.0F),
         9.5F,
         "Буфер событий",
         ClickGuiRenderUtils.compute2(colorScheme4)
      );
      ClickGuiRenderUtils.invoke4(
         renderManager4,
         metrics27,
         FontRegistry.fontObject,
         floatValue56 + floatValue57 - metrics27.measure(28.0F),
         floatValue55,
         metrics27.measure(13.0F),
         8.5F,
         this.renderDiagnosticsStatus.intValue2 + "/32",
         ClickGuiRenderUtils.compute4(colorScheme4)
      );
      float floatValue58 = Math.round(floatValue55 + metrics27.measure(18.0F));
      float floatValue59 = Math.min(1.0F, this.renderDiagnosticsStatus.intValue2 / 32.0F);
      renderManager4.invoke5(floatValue56, floatValue58, floatValue57, metrics27.measure(5.0F), metrics27.measure(2.5F), colorScheme4.getIntValue6());
      renderManager4.invoke34(
         floatValue56, floatValue58, floatValue57 * floatValue59, metrics27.measure(5.0F), metrics27.measure(2.5F), colorScheme4.getIntValue14(), colorScheme4.getIntValue15()
      );
   }

   private void invoke6(RenderManager renderManager5, Metrics metrics28, ColorScheme colorScheme5, float f, float g, float h, float i) {
      float floatValue60 = metrics28.measure(8.0F);
      float floatValue61 = metrics28.measure(9.0F);
      float floatValue62 = measure20(i * 0.22F, metrics28.measure(78.0F), metrics28.measure(96.0F));
      float floatValue63 = Math.round((h - floatValue60 * 2.0F) / 3.0F);
      float floatValue64 = measure19(this.springValue.measure(1.0F, SPRING_SPEC));
      int intValue2 = this.renderDiagnosticsStatus.intValue == 0 ? colorScheme5.getIntValue14() : colorScheme5.compute2();
      this.invoke20(
         renderManager5,
         metrics28,
         colorScheme5,
         f,
         g,
         floatValue63,
         floatValue62,
         floatValue61,
         "Core Load",
         this.coreDiagnosticsOverlayState.resolve(),
         "CFI chain  " + this.renderDiagnosticsStatus.text0x0000000000000000,
         colorScheme5.getIntValue14(),
         this.coreDiagnosticsOverlayState.floats,
         this.coreDiagnosticsOverlayState.measure2(),
         measure25(floatValue64, 0.0F, 0.78F)
      );
      this.invoke20(
         renderManager5,
         metrics28,
         colorScheme5,
         f + floatValue63 + floatValue60,
         g,
         floatValue63,
         floatValue62,
         floatValue61,
         "Render TPS",
         this.coreDiagnosticsOverlayState.resolve2(),
         "Frames  " + this.renderDiagnosticsStatus.text03,
         colorScheme5.getIntValue15(),
         this.coreDiagnosticsOverlayState.floats2,
         this.coreDiagnosticsOverlayState.measure3(),
         measure25(floatValue64, 0.12F, 0.9F)
      );
      this.invoke20(
         renderManager5,
         metrics28,
         colorScheme5,
         f + (floatValue63 + floatValue60) * 2.0F,
         g,
         h - floatValue63 * 2.0F - floatValue60 * 2.0F,
         floatValue62,
         floatValue61,
         "Latency",
         this.coreDiagnosticsOverlayState.resolve3(),
         "Anomalies  " + this.renderDiagnosticsStatus.text02,
         intValue2,
         this.coreDiagnosticsOverlayState.floats3,
         this.coreDiagnosticsOverlayState.measure4(),
         measure25(floatValue64, 0.24F, 1.0F)
      );
      float floatValue65 = Math.round(g + floatValue62 + floatValue60);
      float floatValue66 = measure20(i * 0.29F, metrics28.measure(88.0F), metrics28.measure(106.0F));
      float floatValue67 = Math.round(h * 0.58F);
      this.invoke11(renderManager5, metrics28, colorScheme5, f, floatValue65, floatValue67, floatValue66, floatValue61);
      float floatValue68 = Math.round(f + floatValue67 + floatValue60);
      float floatValue69 = Math.round(h - floatValue67 - floatValue60);
      float floatValue70 = Math.round((floatValue66 - floatValue60) * 0.5F);
      this.invoke10(
         renderManager5,
         metrics28,
         colorScheme5,
         floatValue68,
         floatValue65,
         floatValue69,
         floatValue70,
         floatValue61,
         "Текстурные Юниты",
         this.resolve2(),
         this.renderDiagnosticsStatus.intValue == 0 ? colorScheme5.getIntValue15() : colorScheme5.compute2()
      );
      this.invoke10(
         renderManager5,
         metrics28,
         colorScheme5,
         floatValue68,
         floatValue65 + floatValue70 + floatValue60,
         floatValue69,
         floatValue70,
         floatValue61,
         "Матрицы",
         this.resolve(),
         this.compute(colorScheme5)
      );
      float floatValue71 = Math.round(floatValue65 + floatValue66 + floatValue60);
      float floatValue72 = measure20(i * 0.14F, metrics28.measure(42.0F), metrics28.measure(50.0F));
      String text2 = this.renderDiagnosticsStatus.none4 != null && !"none".equals(this.renderDiagnosticsStatus.none4)
         ? this.renderDiagnosticsStatus.none4
         : this.renderDiagnosticsStatus.ozhidanieNone;
      this.invoke10(renderManager5, metrics28, colorScheme5, f, floatValue71, h, floatValue72, floatValue61, "Файл слепка", text2, colorScheme5.getIntValue15());
      float floatValue73 = Math.round(floatValue71 + floatValue72 + floatValue60);
      float floatValue74 = measure20(i * 0.13F, metrics28.measure(40.0F), metrics28.measure(46.0F));
      this.invoke12(renderManager5, metrics28, colorScheme5, f, floatValue73, h, floatValue74, floatValue61);
      float floatValue75 = Math.round(floatValue73 + floatValue74 + floatValue60);
      this.invoke14(renderManager5, metrics28, colorScheme5, f, floatValue75, h, Math.max(metrics28.measure(46.0F), i - (floatValue75 - g)), floatValue61);
   }

   private void invoke7(
      RenderManager renderManager6, ClickGuiState clickGuiState2, Metrics metrics29, ColorScheme colorScheme6, float f, float g, float h, float i
   ) {
      float floatValue76 = metrics29.measure(8.0F);
      float floatValue77 = metrics29.measure(9.0F);
      float floatValue78 = measure22(i, metrics29);
      float floatValue79 = Math.round(h * 0.58F);
      this.invoke11(renderManager6, metrics29, colorScheme6, f, g, floatValue79, floatValue78, floatValue77);
      float floatValue80 = Math.round(f + floatValue79 + floatValue76);
      float floatValue81 = Math.round(h - floatValue79 - floatValue76);
      float floatValue82 = Math.round((floatValue78 - floatValue76 * 2.0F) / 3.0F);
      this.invoke10(
         renderManager6, metrics29, colorScheme6, floatValue80, g, floatValue81, floatValue82, floatValue77, "CFI chain", this.renderDiagnosticsStatus.text0x0000000000000000, colorScheme6.getIntValue14()
      );
      this.invoke10(
         renderManager6,
         metrics29,
         colorScheme6,
         floatValue80,
         g + floatValue82 + floatValue76,
         floatValue81,
         floatValue82,
         floatValue77,
         "Текстурные Юниты",
         this.resolve2(),
         this.renderDiagnosticsStatus.intValue == 0 ? colorScheme6.getIntValue15() : colorScheme6.compute2()
      );
      this.invoke10(
         renderManager6,
         metrics29,
         colorScheme6,
         floatValue80,
         g + (floatValue82 + floatValue76) * 2.0F,
         floatValue81,
         floatValue78 - floatValue82 * 2.0F - floatValue76 * 2.0F,
         floatValue77,
         "Матрицы",
         this.resolve(),
         this.compute(colorScheme6)
      );
      float floatValue83 = Math.round(g + floatValue78 + floatValue76);
      this.invoke15(renderManager6, clickGuiState2, metrics29, colorScheme6, f, floatValue83, h, i - floatValue78 - floatValue76, floatValue77);
   }

   private void invoke8(
      RenderManager renderManager7, Metrics metrics30, ColorScheme colorScheme7, float f, float g, float h, String string, int i, boolean bl, int j
   ) {
      float floatValue84 = Math.round(f);
      float floatValue85 = Math.round(g);
      float floatValue86 = Math.round(h);
      float floatValue87 = measure8(metrics30);
      float floatValue88 = metrics30.measure(7.0F);
      float floatValue89 = bl ? 1.0F : 0.0F;
      int intValue3 = colorScheme7.isFlag()
         ? ClickGuiRenderUtils.compute12(colorScheme7, 0.38F + floatValue89 * 0.34F)
         : ColorScheme.compute7(colorScheme7.getIntValue3(), ColorScheme.compute6(i, 22), 0.36F + floatValue89 * 0.2F);
      renderManager7.invoke5(floatValue84, floatValue85, floatValue86, floatValue87, floatValue88, intValue3);
      renderManager7.invoke28(
         floatValue84, floatValue85, floatValue86, floatValue87, floatValue88, ColorScheme.compute6(i, colorScheme7.isFlag() ? 68 : 58), Math.max(0.5F, metrics30.measure(0.55F))
      );
      float floatValue90 = metrics30.measure(18.0F);
      float floatValue91 = floatValue84 + metrics30.measure(4.0F);
      float floatValue92 = floatValue85 + Math.round((floatValue87 - floatValue90) * 0.5F);
      renderManager7.invoke5(floatValue91, floatValue92, floatValue90, floatValue90, metrics30.measure(5.0F), ColorScheme.compute6(i, bl ? 68 : 38));
      this.invoke9(renderManager7, metrics30, floatValue91 + floatValue90 * 0.5F, floatValue92 + floatValue90 * 0.5F, j, bl ? colorScheme7.getIntValue13() : i, colorScheme7);
      ClickGuiRenderUtils.invoke4(
         renderManager7,
         metrics30,
         FontRegistry.fontObject4,
         floatValue84 + metrics30.measure(28.0F),
         floatValue85,
         floatValue87,
         9.0F,
         string,
         ClickGuiRenderUtils.compute2(colorScheme7)
      );
   }

   private void invoke9(RenderManager renderManager8, Metrics metrics31, float f, float g, int i, int j, ColorScheme colorScheme8) {
      float floatValue93 = metrics31.measure(1.0F);
      int intValue4 = ColorScheme.compute6(j, 235);
      if (i == 0) {
         renderManager8.invoke5(f - 4.8F * floatValue93, g - 4.4F * floatValue93, 9.6F * floatValue93, 8.8F * floatValue93, 2.2F * floatValue93, ColorScheme.compute6(intValue4, 92));
         renderManager8.invoke5(f - 2.8F * floatValue93, g + 1.2F * floatValue93, 1.4F * floatValue93, 2.6F * floatValue93, 0.7F * floatValue93, intValue4);
         renderManager8.invoke5(f - 0.2F * floatValue93, g - 1.8F * floatValue93, 1.4F * floatValue93, 5.6F * floatValue93, 0.7F * floatValue93, intValue4);
         renderManager8.invoke5(f + 2.4F * floatValue93, g - 4.0F * floatValue93, 1.4F * floatValue93, 7.8F * floatValue93, 0.7F * floatValue93, intValue4);
      } else if (i == 1) {
         renderManager8.invoke5(f - 5.2F * floatValue93, g - 2.8F * floatValue93, 10.4F * floatValue93, 6.8F * floatValue93, 1.8F * floatValue93, ColorScheme.compute6(intValue4, 108));
         renderManager8.invoke5(f - 4.2F * floatValue93, g - 4.4F * floatValue93, 4.8F * floatValue93, 2.6F * floatValue93, 1.1F * floatValue93, ColorScheme.compute6(intValue4, 178));
         renderManager8.invoke5(f - 2.6F * floatValue93, g + 0.1F * floatValue93, 5.2F * floatValue93, 1.1F * floatValue93, 0.55F * floatValue93, intValue4);
      } else {
         renderManager8.invoke5(f - 4.8F * floatValue93, g - 4.0F * floatValue93, 9.6F * floatValue93, 1.3F * floatValue93, 0.65F * floatValue93, intValue4);
         renderManager8.invoke5(f - 4.8F * floatValue93, g - 0.6F * floatValue93, 9.6F * floatValue93, 1.3F * floatValue93, 0.65F * floatValue93, intValue4);
         renderManager8.invoke5(f - 4.8F * floatValue93, g + 2.8F * floatValue93, 7.1F * floatValue93, 1.3F * floatValue93, 0.65F * floatValue93, ColorScheme.compute6(intValue4, 190));
         renderManager8.invoke39(f + 4.5F * floatValue93, g + 3.4F * floatValue93, 1.15F * floatValue93, 0.0F, 1.0F, colorScheme8.getIntValue15());
      }
   }

   private void invoke10(
      RenderManager renderManager9,
      Metrics metrics32,
      ColorScheme colorScheme9,
      float f,
      float g,
      float h,
      float i,
      float j,
      String string,
      String string2,
      int k
   ) {
      float floatValue94 = Math.round(f);
      float floatValue95 = Math.round(g);
      float floatValue96 = Math.round(h);
      float floatValue97 = Math.round(i);
      float floatValue98 = floatValue94 + metrics32.measure(27.0F);
      float floatValue99 = Math.max(metrics32.measure(12.0F), floatValue96 - metrics32.measure(37.0F));
      float floatValue100 = metrics32.measure(11.0F);
      float floatValue101 = metrics32.measure(12.0F);
      float floatValue102 = Math.round(floatValue95 + metrics32.measure(18.0F));
      int intValue5 = colorScheme9.isFlag()
         ? ClickGuiRenderUtils.compute12(colorScheme9, 0.18F)
         : ColorScheme.compute7(colorScheme9.getIntValue3(), ColorScheme.compute6(k, 10), 0.16F);
      renderManager9.invoke5(floatValue94, floatValue95, floatValue96, floatValue97, j, intValue5);
      renderManager9.invoke28(
         floatValue94,
         floatValue95,
         floatValue96,
         floatValue97,
         j,
         ColorScheme.compute6(colorScheme9.getIntValue13(), colorScheme9.isFlag() ? 54 : 20),
         Math.max(0.5F, metrics32.measure(0.55F))
      );
      this.invoke25(renderManager9, metrics32, floatValue94 + metrics32.measure(14.0F), floatValue95 + metrics32.measure(8.0F), k, colorScheme9);
      this.invoke19(
         renderManager9,
         metrics32,
         FontRegistry.fontObject4,
         floatValue98,
         floatValue95 + metrics32.measure(2.0F),
         floatValue100,
         9.5F,
         string,
         ClickGuiRenderUtils.compute4(colorScheme9),
         floatValue99
      );
      this.invoke19(renderManager9, metrics32, FontRegistry.fontObject4, floatValue98, floatValue102, floatValue101, 9.0F, string2, ClickGuiRenderUtils.compute2(colorScheme9), floatValue99);
   }

   private void invoke11(RenderManager renderManager10, Metrics metrics33, ColorScheme colorScheme10, float f, float g, float h, float i, float j) {
      float floatValue103 = Math.round(f);
      float floatValue104 = Math.round(g);
      float floatValue105 = Math.round(h);
      float floatValue106 = Math.round(i);
      boolean flag2 = "0".equals(this.renderDiagnosticsStatus.text0);
      int intValue6 = flag2 ? colorScheme10.getIntValue15() : colorScheme10.compute2();
      int intValue7 = colorScheme10.isFlag()
         ? ClickGuiRenderUtils.compute12(colorScheme10, flag2 ? 0.2F : 0.31F)
         : ColorScheme.compute7(colorScheme10.getIntValue3(), ColorScheme.compute6(intValue6, flag2 ? 12 : 28), 0.24F);
      renderManager10.invoke5(floatValue103, floatValue104, floatValue105, floatValue106, j, intValue7);
      renderManager10.invoke28(
         floatValue103,
         floatValue104,
         floatValue105,
         floatValue106,
         j,
         ColorScheme.compute6(colorScheme10.getIntValue13(), colorScheme10.isFlag() ? 58 : 22),
         Math.max(0.55F, metrics33.measure(0.6F))
      );
      float floatValue107 = metrics33.measure(14.0F);
      float floatValue108 = floatValue103 + floatValue107 + metrics33.measure(14.0F);
      float floatValue109 = Math.max(metrics33.measure(16.0F), floatValue105 - floatValue107 - metrics33.measure(24.0F));
      this.invoke25(renderManager10, metrics33, floatValue103 + floatValue107, floatValue104 + metrics33.measure(12.0F), intValue6, colorScheme10);
      this.invoke19(
         renderManager10,
         metrics33,
         FontRegistry.fontObject4,
         floatValue108,
         floatValue104 + metrics33.measure(5.0F),
         metrics33.measure(14.0F),
         10.0F,
         "Что сейчас ломается",
         ClickGuiRenderUtils.compute4(colorScheme10),
         floatValue109
      );
      this.invoke19(
         renderManager10,
         metrics33,
         FontRegistry.fontObject4,
         floatValue108,
         floatValue104 + metrics33.measure(22.0F),
         metrics33.measure(16.0F),
         10.0F,
         flag2 ? "Шейдерных исключений нет" : this.renderDiagnosticsStatus.none5,
         ClickGuiRenderUtils.compute2(colorScheme10),
         floatValue109
      );
      this.invoke19(
         renderManager10,
         metrics33,
         FontRegistry.fontObject,
         floatValue108,
         floatValue104 + metrics33.measure(42.0F),
         metrics33.measure(14.0F),
         9.0F,
         flag2 ? this.renderDiagnosticsStatus.latestLog : this.renderDiagnosticsStatus.none6,
         flag2 ? ClickGuiRenderUtils.compute4(colorScheme10) : intValue6,
         floatValue109
      );
      this.invoke19(
         renderManager10,
         metrics33,
         FontRegistry.fontObject,
         floatValue108,
         floatValue104 + metrics33.measure(59.0F),
         metrics33.measure(15.0F),
         8.5F,
         flag2 ? "Нажми Логи, чтобы загрузить latest.log" : this.renderDiagnosticsStatus.none7,
         flag2 ? ClickGuiRenderUtils.compute4(colorScheme10) : ClickGuiRenderUtils.compute2(colorScheme10),
         floatValue109
      );
   }

   private void invoke12(RenderManager renderManager11, Metrics metrics34, ColorScheme colorScheme11, float f, float g, float h, float i, float j) {
      float floatValue110 = Math.round(f);
      float floatValue111 = Math.round(g);
      float floatValue112 = Math.round(h);
      float floatValue113 = Math.round((floatValue112 - metrics34.measure(8.0F)) * 0.5F);
      this.invoke13(
         renderManager11, metrics34, colorScheme11, floatValue110, floatValue111, floatValue113, i, j, "Mixin policy", this.renderDiagnosticsStatus.injectHeadTail, colorScheme11.getIntValue14()
      );
      this.invoke13(
         renderManager11,
         metrics34,
         colorScheme11,
         floatValue110 + floatValue113 + metrics34.measure(8.0F),
         floatValue111,
         floatValue112 - floatValue113 - metrics34.measure(8.0F),
         i,
         j,
         "Privacy",
         this.renderDiagnosticsStatus.localEncrypted,
         colorScheme11.getIntValue15()
      );
   }

   private void invoke13(
      RenderManager renderManager12,
      Metrics metrics35,
      ColorScheme colorScheme12,
      float f,
      float g,
      float h,
      float i,
      float j,
      String string,
      String string2,
      int k
   ) {
      int intValue8 = colorScheme12.isFlag()
         ? ClickGuiRenderUtils.compute12(colorScheme12, 0.18F)
         : ColorScheme.compute7(colorScheme12.getIntValue3(), ColorScheme.compute6(k, 14), 0.18F);
      renderManager12.invoke5(f, g, (float)Math.round(h), (float)Math.round(i), j, intValue8);
      renderManager12.invoke28(
         f,
         g,
         (float)Math.round(h),
         (float)Math.round(i),
         j,
         ColorScheme.compute6(k, colorScheme12.isFlag() ? 46 : 42),
         Math.max(0.5F, metrics35.measure(0.55F))
      );
      float floatValue114 = f + metrics35.measure(12.0F);
      float floatValue115 = Math.max(metrics35.measure(12.0F), h - metrics35.measure(24.0F));
      this.invoke19(
         renderManager12,
         metrics35,
         FontRegistry.fontObject4,
         floatValue114,
         g + metrics35.measure(5.0F),
         metrics35.measure(13.0F),
         9.5F,
         string,
         ClickGuiRenderUtils.compute4(colorScheme12),
         floatValue115
      );
      this.invoke19(
         renderManager12,
         metrics35,
         FontRegistry.fontObject4,
         floatValue114,
         g + metrics35.measure(21.0F),
         metrics35.measure(14.0F),
         9.0F,
         string2,
         ClickGuiRenderUtils.compute2(colorScheme12),
         floatValue115
      );
   }

   private void invoke14(RenderManager renderManager13, Metrics metrics36, ColorScheme colorScheme13, float f, float g, float h, float i, float j) {
      float floatValue116 = Math.round(f);
      float floatValue117 = Math.round(g);
      float floatValue118 = Math.round(h);
      float floatValue119 = Math.round(i);
      int intValue9 = colorScheme13.isFlag()
         ? ClickGuiRenderUtils.compute12(colorScheme13, 0.24F)
         : ColorScheme.compute7(colorScheme13.getIntValue3(), colorScheme13.getIntValue5(), 0.32F);
      renderManager13.invoke5(floatValue116, floatValue117, floatValue118, floatValue119, j, intValue9);
      renderManager13.invoke28(
         floatValue116,
         floatValue117,
         floatValue118,
         floatValue119,
         j,
         ColorScheme.compute6(colorScheme13.getIntValue13(), colorScheme13.isFlag() ? 76 : 24),
         Math.max(0.55F, metrics36.measure(0.6F))
      );
      ClickGuiRenderUtils.invoke4(
         renderManager13,
         metrics36,
         FontRegistry.fontObject4,
         floatValue116 + metrics36.measure(14.0F),
         floatValue117 + metrics36.measure(5.0F),
         metrics36.measure(13.0F),
         9.5F,
         "Гайдлайн",
         ClickGuiRenderUtils.compute4(colorScheme13)
      );
      float floatValue120 = floatValue117 + metrics36.measure(25.0F);
      this.invoke17(renderManager13, metrics36, colorScheme13, floatValue116 + metrics36.measure(14.0F), floatValue120, "1 смотри Code/Stage", colorScheme13.getIntValue14());
      this.invoke17(renderManager13, metrics36, colorScheme13, floatValue116 + floatValue118 * 0.29F, floatValue120, "2 жми Слепок", colorScheme13.getIntValue15());
      this.invoke17(renderManager13, metrics36, colorScheme13, floatValue116 + floatValue118 * 0.53F, floatValue120, "3 открой Логи", colorScheme13.getIntValue14());
      this.invoke17(renderManager13, metrics36, colorScheme13, floatValue116 + floatValue118 * 0.76F, floatValue120, "4 передай Tracker ID", colorScheme13.getIntValue15());
   }

   private void invoke15(
      RenderManager renderManager14, ClickGuiState clickGuiState3, Metrics metrics37, ColorScheme colorScheme14, float f, float g, float h, float i, float j
   ) {
      float floatValue121 = Math.round(f);
      float floatValue122 = Math.round(g);
      float floatValue123 = Math.round(h);
      float floatValue124 = Math.round(i);
      int intValue10 = colorScheme14.isFlag()
         ? ColorScheme.compute5(247, 248, 252, 226)
         : ColorScheme.compute7(ColorScheme.compute5(5, 7, 12, 238), ColorScheme.compute6(colorScheme14.getIntValue14(), 34), 0.22F);
      renderManager14.invoke5(floatValue121, floatValue122, floatValue123, floatValue124, j, intValue10);
      renderManager14.invoke28(
         floatValue121,
         floatValue122,
         floatValue123,
         floatValue124,
         j,
         ColorScheme.compute6(colorScheme14.getIntValue14(), colorScheme14.isFlag() ? 58 : 76),
         Math.max(0.55F, metrics37.measure(0.6F))
      );
      this.invoke19(
         renderManager14,
         metrics37,
         FontRegistry.fontObject4,
         floatValue121 + metrics37.measure(14.0F),
         floatValue122 + metrics37.measure(6.0F),
         metrics37.measure(16.0F),
         10.5F,
         "Встроенный viewer",
         ClickGuiRenderUtils.compute2(colorScheme14),
         metrics37.measure(138.0F)
      );
      this.invoke19(
         renderManager14,
         metrics37,
         FontRegistry.fontObject,
         floatValue121 + metrics37.measure(166.0F),
         floatValue122 + metrics37.measure(6.0F),
         metrics37.measure(16.0F),
         8.5F,
         this.renderDiagnosticsStatus.latestLog2,
         ClickGuiRenderUtils.compute4(colorScheme14),
         Math.max(metrics37.measure(40.0F), floatValue123 - metrics37.measure(276.0F))
      );
      this.invoke19(
         renderManager14,
         metrics37,
         FontRegistry.fontObject,
         floatValue121 + floatValue123 - metrics37.measure(92.0F),
         floatValue122 + metrics37.measure(6.0F),
         metrics37.measure(16.0F),
         8.5F,
         "latest.log tail",
         ClickGuiRenderUtils.compute4(colorScheme14),
         metrics37.measure(80.0F)
      );
      float floatValue125 = floatValue121 + metrics37.measure(10.0F);
      float floatValue126 = floatValue122 + metrics37.measure(30.0F);
      float floatValue127 = floatValue123 - metrics37.measure(20.0F);
      float floatValue128 = Math.max(metrics37.measure(24.0F), floatValue124 - metrics37.measure(38.0F));
      int intValue11 = Math.min(this.renderDiagnosticsStatus.intValue3, 96);
      float floatValue129 = Math.max(metrics37.measure(14.0F), Math.min(metrics37.measure(18.0F), floatValue128 / Math.max(1, Math.min(96, 14))));
      float floatValue130 = metrics37.measure(62.0F);
      float floatValue131 = Math.max(floatValue128, intValue11 * floatValue129);
      float floatValue132 = floatValue127;

      for (int intValue12 = 0; intValue12 < intValue11; intValue12++) {
         float floatValue133 = floatValue130
            + metrics37.measure(24.0F)
            + ClickGuiRenderUtils.measure2(metrics37, FontRegistry.fontObject, this.resolve4(this.renderDiagnosticsStatus.text[intValue12]), 8.0F);
         floatValue132 = Math.max(floatValue132, floatValue133);
      }

      float floatValue134 = Math.max(0.0F, floatValue131 - floatValue128);
      float floatValue135 = Math.max(0.0F, floatValue132 - floatValue127);
      invoke23(floatValue125, floatValue126, floatValue127, floatValue128, floatValue132, floatValue131, floatValue135, floatValue134);
      clickGuiState3.invoke17(floatValue134, floatValue135);
      float floatValue136 = Math.min(clickGuiState3.getFloatValue14(), floatValue134);
      float floatValue137 = Math.min(clickGuiState3.getFloatValue15(), floatValue135);
      renderManager14.invoke20();
      renderManager14.invoke24(
         floatValue125, floatValue126, floatValue127, floatValue128, metrics37.measure(6.0F), metrics37.measure(6.0F), metrics37.measure(6.0F), metrics37.measure(6.0F)
      );

      try {
         for (int intValue13 = 0; intValue13 < intValue11; intValue13++) {
            float floatValue138 = floatValue126 + intValue13 * floatValue129 - floatValue136;
            if (!(floatValue138 + floatValue129 < floatValue126) && !(floatValue138 > floatValue126 + floatValue128)) {
               this.invoke16(
                  renderManager14,
                  metrics37,
                  colorScheme14,
                  floatValue125,
                  floatValue138,
                  floatValue127,
                  floatValue129,
                  this.renderDiagnosticsStatus.text[intValue13],
                  this.renderDiagnosticsStatus.ints[intValue13],
                  floatValue137
               );
            }
         }

         if (intValue11 == 0) {
            ClickGuiRenderUtils.invoke4(
               renderManager14,
               metrics37,
               FontRegistry.fontObject,
               floatValue125 + metrics37.measure(9.0F),
               floatValue126 + metrics37.measure(3.0F),
               metrics37.measure(16.0F),
               9.0F,
               "Нажми Логи, чтобы загрузить latest.log",
               ClickGuiRenderUtils.compute4(colorScheme14)
            );
         }
      } finally {
         renderManager14.invoke20();
         renderManager14.invoke25();
      }

      if (floatValue134 > 0.5F) {
         float floatValue139 = measure26(metrics37);
         float floatValue140 = Math.round(floatValue125 + floatValue127 - floatValue139);
         float floatValue141 = measure28(metrics37);
         float floatValue142 = Math.round(floatValue126 + (floatValue128 - floatValue141) * (floatValue136 / Math.max(1.0F, floatValue134)));
         ClickGuiRenderUtils.invoke16(renderManager14, metrics37, colorScheme14, floatValue140, floatValue126, floatValue139, floatValue128, floatValue142, floatValue141, 0.0F, 0.42F);
      }

      if (floatValue135 > 0.5F) {
         float floatValue143 = measure26(metrics37);
         float floatValue144 = Math.round(floatValue126 + floatValue128 - floatValue143);
         float floatValue145 = measure27(metrics37);
         float floatValue146 = Math.round(floatValue125 + (floatValue127 - floatValue145) * (floatValue137 / Math.max(1.0F, floatValue135)));
         renderManager14.invoke5(
            floatValue125, floatValue144, floatValue127, floatValue143, floatValue143 * 0.5F, ColorScheme.compute7(colorScheme14.getIntValue3(), colorScheme14.getIntValue5(), 0.42F)
         );
         renderManager14.invoke34(
            floatValue146,
            floatValue144,
            floatValue145,
            floatValue143,
            floatValue143 * 0.5F,
            ColorScheme.compute6(colorScheme14.getIntValue14(), 165),
            ColorScheme.compute6(colorScheme14.getIntValue15(), 150)
         );
      }
   }

   private void invoke16(
      RenderManager renderManager15, Metrics metrics38, ColorScheme colorScheme15, float f, float g, float h, float i, String string, int j, float k
   ) {
      int intValue14 = this.compute2(colorScheme15, j);
      if (j >= 2) {
         renderManager15.invoke5(f, g, h, i, metrics38.measure(3.0F), ColorScheme.compute6(intValue14, j == 3 ? 24 : 16));
      }

      float floatValue147 = f + metrics38.measure(7.0F);
      float floatValue148 = g + i * 0.5F;
      renderManager15.invoke39(floatValue147, floatValue148, metrics38.measure(2.2F), 0.0F, 1.0F, ColorScheme.compute6(intValue14, 230));
      ClickGuiRenderUtils.invoke4(
         renderManager15,
         metrics38,
         FontRegistry.fontObject4,
         f + metrics38.measure(16.0F),
         g,
         i,
         7.0F,
         this.resolve3(j),
         ColorScheme.compute6(intValue14, 238)
      );
      float floatValue149 = f + metrics38.measure(72.0F);
      float floatValue150 = Math.max(metrics38.measure(18.0F), h - metrics38.measure(76.0F));
      renderManager15.invoke20();
      renderManager15.invoke24(floatValue149, g, floatValue150, i, 0.0F, 0.0F, 0.0F, 0.0F);
      boolean flag3 = false ;

      try {
         flag3 = true;
         ClickGuiRenderUtils.invoke4(
            renderManager15,
            metrics38,
            FontRegistry.fontObject,
            floatValue149 - k,
            g,
            i,
            8.0F,
            this.resolve4(string),
            j == 3 ? colorScheme15.compute2() : ClickGuiRenderUtils.compute2(colorScheme15)
         );
         flag3 = false;
      } finally {
         if (flag3) {
            renderManager15.invoke20();
            renderManager15.invoke25();
         }
      }

      renderManager15.invoke20();
      renderManager15.invoke25();
   }

   private void invoke17(RenderManager renderManager16, Metrics metrics39, ColorScheme colorScheme16, float f, float g, String string, int i) {
      float floatValue151 = metrics39.measure(4.0F);
      renderManager16.invoke39(f, g + metrics39.measure(8.0F), floatValue151, 0.0F, 1.0F, ColorScheme.compute6(i, 210));
      ClickGuiRenderUtils.invoke4(
         renderManager16,
         metrics39,
         FontRegistry.fontObject,
         f + metrics39.measure(9.0F),
         g,
         metrics39.measure(16.0F),
         8.5F,
         string,
         ClickGuiRenderUtils.compute2(colorScheme16)
      );
   }

   private void invoke18(
      RenderManager renderManager17, Metrics metrics40, ColorScheme colorScheme17, float f, float g, float h, float i, String string, String string2, int j
   ) {
      float floatValue152 = Math.round(f);
      float floatValue153 = Math.round(g);
      float floatValue154 = Math.round(h);
      float floatValue155 = Math.round(i);
      float floatValue156 = metrics40.measure(10.0F);
      int intValue15 = colorScheme17.isFlag()
         ? ClickGuiRenderUtils.compute12(colorScheme17, 0.13F)
         : ColorScheme.compute7(colorScheme17.getIntValue3(), colorScheme17.getIntValue5(), 0.22F);
      renderManager17.invoke5(floatValue152, floatValue153, floatValue154, floatValue155, metrics40.measure(7.0F), intValue15);
      renderManager17.invoke28(
         floatValue152,
         floatValue153,
         floatValue154,
         floatValue155,
         metrics40.measure(7.0F),
         ColorScheme.compute6(colorScheme17.getIntValue13(), colorScheme17.isFlag() ? 52 : 15),
         Math.max(0.45F, metrics40.measure(0.5F))
      );
      float floatValue157 = Math.max(metrics40.measure(12.0F), floatValue154 - floatValue156 * 2.0F);
      this.invoke19(
         renderManager17,
         metrics40,
         FontRegistry.fontObject4,
         floatValue152 + floatValue156,
         floatValue153 + metrics40.measure(5.0F),
         metrics40.measure(13.0F),
         9.5F,
         string,
         ClickGuiRenderUtils.compute4(colorScheme17),
         floatValue157
      );
      this.invoke19(
         renderManager17,
         metrics40,
         FontRegistry.fontObject4,
         floatValue152 + floatValue156,
         floatValue153 + metrics40.measure(19.0F),
         metrics40.measure(15.0F),
         9.0F,
         string2,
         j,
         floatValue157
      );
   }

   private void invoke19(
      RenderManager renderManager18, Metrics metrics41, FontObject fontObject, float f, float g, float h, float i, String string, int j, float k
   ) {
      String text3 = this.resolve4(string);
      float floatValue158 = Math.max(metrics41.measure(8.0F), k);
      float floatValue159 = ClickGuiRenderUtils.measure2(metrics41, fontObject, text3, i);
      if (floatValue159 <= floatValue158) {
         ClickGuiRenderUtils.invoke4(renderManager18, metrics41, fontObject, f, g, h, i, text3, j);
      } else {
         float floatValue160 = floatValue159 - floatValue158 + metrics41.measure(5.0F);
         float floatValue161 = floatValue160 * this.measure23();
         renderManager18.invoke20();
         renderManager18.invoke24(f, g, floatValue158, h, 0.0F, 0.0F, 0.0F, 0.0F);
         boolean flag4 = false ;

         try {
            flag4 = true;
            ClickGuiRenderUtils.invoke4(renderManager18, metrics41, fontObject, f - floatValue161, g, h, i, text3, j);
            flag4 = false;
         } finally {
            if (flag4) {
               renderManager18.invoke20();
               renderManager18.invoke25();
            }
         }

         renderManager18.invoke20();
         renderManager18.invoke25();
      }
   }

   private float measure23() {
      float floatValue162 = (float)(System.currentTimeMillis() % 7200L) / 7200.0F;
      if (floatValue162 < 0.18F) {
         return 0.0F;
      } else if (floatValue162 < 0.44F) {
         return measure24((floatValue162 - 0.18F) / 0.26F);
      } else if (floatValue162 < 0.62F) {
         return 1.0F;
      } else {
         return floatValue162 < 0.88F ? 1.0F - measure24((floatValue162 - 0.62F) / 0.26F) : 0.0F;
      }
   }

   private static float measure24(float f) {
      float floatValue163 = measure19(f);
      return floatValue163 * floatValue163 * (3.0F - 2.0F * floatValue163);
   }

   private static float measure25(float f, float g, float h) {
      return measure19((f - g) / Math.max(0.001F, h - g));
   }

   private void invoke20(
      RenderManager renderManager19,
      Metrics metrics42,
      ColorScheme colorScheme18,
      float f,
      float g,
      float h,
      float i,
      float j,
      String string,
      String string2,
      String string3,
      int k,
      float[] fs,
      float l,
      float m
   ) {
      float floatValue164 = Math.round(f);
      float floatValue165 = Math.round(g);
      float floatValue166 = Math.round(h);
      float floatValue167 = Math.round(i);
      int intValue16 = colorScheme18.isFlag()
         ? ClickGuiRenderUtils.compute12(colorScheme18, 0.16F)
         : ColorScheme.compute7(colorScheme18.getIntValue3(), ColorScheme.compute6(k, 12), 0.18F);
      renderManager19.invoke5(floatValue164, floatValue165, floatValue166, floatValue167, j, intValue16);
      renderManager19.invoke28(
         floatValue164, floatValue165, floatValue166, floatValue167, j, ColorScheme.compute6(k, colorScheme18.isFlag() ? 54 : 40), Math.max(0.5F, metrics42.measure(0.6F))
      );
      float floatValue168 = metrics42.measure(10.0F);
      float floatValue169 = ClickGuiRenderUtils.measure2(metrics42, FontRegistry.fontObject4, string2, 10.5F);
      float floatValue170 = Math.max(metrics42.measure(12.0F), floatValue166 - floatValue168 * 2.0F - floatValue169 - metrics42.measure(6.0F));
      this.invoke19(
         renderManager19,
         metrics42,
         FontRegistry.fontObject4,
         floatValue164 + floatValue168,
         floatValue165 + metrics42.measure(6.0F),
         metrics42.measure(12.0F),
         9.0F,
         string,
         ClickGuiRenderUtils.compute4(colorScheme18),
         floatValue170
      );
      ClickGuiRenderUtils.invoke4(
         renderManager19,
         metrics42,
         FontRegistry.fontObject4,
         floatValue164 + floatValue166 - floatValue168 - floatValue169,
         floatValue165 + metrics42.measure(5.0F),
         metrics42.measure(13.0F),
         10.5F,
         string2,
         ColorScheme.compute6(k, 235)
      );
      float floatValue171 = floatValue164 + floatValue168;
      float floatValue172 = floatValue165 + metrics42.measure(24.0F);
      float floatValue173 = Math.max(metrics42.measure(8.0F), floatValue166 - floatValue168 * 2.0F);
      float floatValue174 = floatValue165 + floatValue167 - metrics42.measure(15.0F);
      float floatValue175 = Math.max(metrics42.measure(8.0F), floatValue174 - floatValue172);
      this.invoke21(renderManager19, metrics42, colorScheme18, floatValue171, floatValue172, floatValue173, floatValue175, k, fs, l, measure24(m));
      this.invoke19(
         renderManager19,
         metrics42,
         FontRegistry.fontObject,
         floatValue164 + floatValue168,
         floatValue165 + floatValue167 - metrics42.measure(13.0F),
         metrics42.measure(11.0F),
         7.5F,
         string3,
         ClickGuiRenderUtils.compute4(colorScheme18),
         Math.max(metrics42.measure(12.0F), floatValue166 - floatValue168 * 2.0F)
      );
   }

   private void invoke21(
      RenderManager renderManager20, Metrics metrics43, ColorScheme colorScheme19, float f, float g, float h, float i, int j, float[] fs, float k, float l
   ) {
      renderManager20.invoke5(
         f, g + i - metrics43.measure(0.75F), h, metrics43.measure(0.75F), 0.0F, ColorScheme.compute6(j, colorScheme19.isFlag() ? 40 : 32)
      );
      int intValue17 = fs.length;
      if (intValue17 >= 2 && !(k <= 1.0E-4F) && !(l <= 0.001F)) {
         float floatValue176 = h / (intValue17 - 1);
         int intValue18 = ColorScheme.compute6(j, colorScheme19.isFlag() ? 118 : 150);
         int intValue19 = ColorScheme.compute6(j, colorScheme19.isFlag() ? 12 : 18);
         int intValue20 = ColorScheme.compute6(j, 235);
         float floatValue177 = 0.0F;
         float floatValue178 = 0.0F;

         for (int intValue21 = 0; intValue21 < intValue17; intValue21++) {
            float floatValue179 = measure19(this.coreDiagnosticsOverlayState.measure(fs, intValue21) / k) * l;
            float floatValue180 = floatValue179 * i;
            float floatValue181 = f + intValue21 * floatValue176;
            float floatValue182 = g + i - floatValue180;
            if (floatValue180 > 0.5F) {
               renderManager20.invoke37(floatValue181 - floatValue176 * 0.5F, floatValue182, floatValue176 + metrics43.measure(0.6F), floatValue180, 0.0F, intValue18, intValue19);
            }

            if (intValue21 > 0) {
               this.invoke22(renderManager20, metrics43, floatValue177, floatValue178, floatValue181, floatValue182, intValue20);
            }

            floatValue177 = floatValue181;
            floatValue178 = floatValue182;
         }
      }
   }

   private void invoke22(RenderManager renderManager21, Metrics metrics44, float f, float g, float h, float i, int j) {
      float floatValue183 = h - f;
      float floatValue184 = i - g;
      float floatValue185 = (float)Math.sqrt(floatValue183 * floatValue183 + floatValue184 * floatValue184);
      float floatValue186 = Math.max(1.0F, metrics44.measure(1.4F));
      if (floatValue185 < 0.001F) {
         renderManager21.invoke5(f - floatValue186 * 0.5F, g - floatValue186 * 0.5F, floatValue186, floatValue186, floatValue186 * 0.5F, j);
      } else {
         float floatValue187 = (float)Math.toDegrees(Math.atan2(floatValue184, floatValue183));
         renderManager21.invoke56(f, g);
         renderManager21.invoke54(floatValue187);

         try {
            renderManager21.invoke5(0.0F, -floatValue186 * 0.5F, floatValue185, floatValue186, floatValue186 * 0.5F, j);
         } finally {
            renderManager21.invoke55();
            renderManager21.invoke57();
         }
      }
   }

   private static void invoke23(float f, float g, float h, float i, float j, float k, float l, float m) {
      floatValue = f;
      floatValue2 = g;
      floatValue3 = h;
      floatValue4 = i;
      floatValue5 = j;
      floatValue6 = k;
      floatValue7 = l;
      floatValue8 = m;
   }

   private static void invoke24() {
      floatValue = 0.0F;
      floatValue2 = 0.0F;
      floatValue3 = 0.0F;
      floatValue4 = 0.0F;
      floatValue5 = 0.0F;
      floatValue6 = 0.0F;
      floatValue7 = 0.0F;
      floatValue8 = 0.0F;
   }

   private static float measure26(Metrics metrics45) {
      return Math.max(metrics45.measure(5.0F), metrics45.measure(4.0F));
   }

   private static float measure27(Metrics metrics46) {
      return !(floatValue3 <= 1.0F) && !(floatValue5 <= floatValue3)
         ? Math.max(metrics46.measure(28.0F), floatValue3 * floatValue3 / Math.max(floatValue3, floatValue5))
         : floatValue3;
   }

   private static float measure28(Metrics metrics47) {
      return !(floatValue4 <= 1.0F) && !(floatValue6 <= floatValue4)
         ? Math.max(metrics47.measure(18.0F), floatValue4 * floatValue4 / Math.max(floatValue4, floatValue6))
         : floatValue4;
   }

   private String resolve() {
      String text4 = this.resolve4(this.renderDiagnosticsStatus.matrixFinite);
      return text4.toLowerCase(Locale.ROOT).contains("finite") ? "OK" : "CORRUPTED";
   }

   private String resolve2() {
      return this.renderDiagnosticsStatus.intValue == 0 ? "Изолированы [TextureUnitGuard]" : this.resolve4(this.renderDiagnosticsStatus.glClean);
   }

   private int compute(ColorScheme colorScheme20) {
      return "OK".equals(this.resolve()) ? colorScheme20.getIntValue14() : colorScheme20.compute2();
   }

   private void invoke25(RenderManager renderManager22, Metrics metrics48, float f, float g, int i, ColorScheme colorScheme21) {
      float floatValue188 = metrics48.measure(1.0F);
      renderManager22.invoke28(
         f - 5.2F * floatValue188,
         g - 5.2F * floatValue188,
         10.4F * floatValue188,
         10.4F * floatValue188,
         3.0F * floatValue188,
         ColorScheme.compute6(i, colorScheme21.isFlag() ? 96 : 124),
         Math.max(0.6F, metrics48.measure(0.65F))
      );
      renderManager22.invoke5(f - 0.9F * floatValue188, g - 3.7F * floatValue188, 1.8F * floatValue188, 7.4F * floatValue188, 0.9F * floatValue188, ColorScheme.compute6(i, 214));
      renderManager22.invoke5(f - 3.6F * floatValue188, g + 1.9F * floatValue188, 7.2F * floatValue188, 1.5F * floatValue188, 0.75F * floatValue188, ColorScheme.compute6(i, 178));
   }

   private int compute2(ColorScheme colorScheme22, int i) {
      return switch (i) {
         case 2 -> colorScheme22.compute3();
         case 3 -> colorScheme22.compute2();
         case 4 -> colorScheme22.getIntValue14();
         default -> colorScheme22.getIntValue15();
      };
   }

   private String resolve3(int i) {
      return switch (i) {
         case 2 -> "WARN";
         case 3 -> "ERROR";
         case 4 -> "GL";
         default -> "INFO";
      };
   }

   private String resolve4(String string) {
      return string != null && !string.isBlank() ? string : "none";
   }

   private static float measure29(Metrics metrics49, float f, float g) {
      return Math.round(f + g - measure5(metrics49) - measure6(metrics49) - measure7(metrics49) - metrics49.measure(16.0F));
   }

   private static float measure30(Metrics metrics50, float f, float g) {
      return Math.round(f + g - measure6(metrics50) - measure7(metrics50) - metrics50.measure(8.0F));
   }

   private static float measure31(Metrics metrics51, float f, float g) {
      return Math.round(f + g - measure7(metrics51));
   }

   private static float measure32(ClickGuiGeometry clickGuiGeometry19, Metrics metrics52) {
      return Math.round(clickGuiGeometry19.getFloatValue11() + metrics52.measure(18.0F));
   }

   private static float measure33(ClickGuiGeometry clickGuiGeometry20, Metrics metrics53) {
      return Math.round(clickGuiGeometry20.getFloatValue12() + metrics53.measure(18.0F));
   }

   private static float measure34(ClickGuiGeometry clickGuiGeometry21, Metrics metrics54) {
      return Math.round(clickGuiGeometry21.getFloatValue13() - metrics54.measure(36.0F));
   }

   private static float measure35(Metrics metrics55) {
      return Math.round(metrics55.getFloatValue12() - metrics55.measure(36.0F));
   }

   static final class CoreDiagnosticsOverlayState {
      private static final int INT_VALUE = 48;
      private static final long TIMESTAMP = 50L;
      final float[] floats = new float[48];
      final float[] floats2 = new float[48];
      final float[] floats3 = new float[48];
      private int intValue;
      private long timestamp;
      private float floatValue;
      private float floatValue2;
      private float floatValue3;
      private OperatingSystemMXBean operatingSystemMXBean;
      private boolean flag;

      void invoke() {
         this.floatValue = this.measure5();
         this.floatValue2 = this.measure6();
         this.floatValue3 = this.measure7();

         for (int intValue22 = 0; intValue22 < 48; intValue22++) {
            this.floats[intValue22] = this.floatValue;
            this.floats2[intValue22] = this.floatValue2;
            this.floats3[intValue22] = this.floatValue3;
         }

         this.intValue = 47;
         this.timestamp = System.currentTimeMillis();
      }

      void invoke2() {
         this.floatValue = this.measure5();
         this.floatValue2 = this.measure6();
         this.floatValue3 = this.measure7();
         long longValue2 = System.currentTimeMillis();
         if (longValue2 - this.timestamp < 50L) {
            this.floats[this.intValue] = this.floatValue;
            this.floats2[this.intValue] = this.floatValue2;
            this.floats3[this.intValue] = this.floatValue3;
         } else {
            this.timestamp = longValue2;
            this.intValue = (this.intValue + 1) % 48;
            this.floats[this.intValue] = this.floatValue;
            this.floats2[this.intValue] = this.floatValue2;
            this.floats3[this.intValue] = this.floatValue3;
         }
      }

      float measure(float[] fs, int i) {
         return fs[(this.intValue + 1 + i) % 48];
      }

      float measure2() {
         return 1.0F;
      }

      float measure3() {
         float floatValue189 = 1.0F;

         for (int intValue23 = 0; intValue23 < 48; intValue23++) {
            floatValue189 = Math.max(floatValue189, this.floats2[intValue23]);
         }

         return Math.max(60.0F, floatValue189 * 1.12F);
      }

      float measure4() {
         float floatValue190 = 1.0F;

         for (int intValue24 = 0; intValue24 < 48; intValue24++) {
            floatValue190 = Math.max(floatValue190, this.floats3[intValue24]);
         }

         return Math.max(80.0F, floatValue190 * 1.2F);
      }

      String resolve() {
         return Math.round(this.floatValue * 100.0F) + "%";
      }

      String resolve2() {
         return Integer.toString(Math.round(this.floatValue2));
      }

      String resolve3() {
         return Math.round(this.floatValue3) + " ms";
      }

      private float measure5() {
         try {
            if (!this.flag) {
               this.flag = true;
               if (ManagementFactory.getOperatingSystemMXBean() instanceof OperatingSystemMXBean operatingSystemMXBean) {
                  this.operatingSystemMXBean = operatingSystemMXBean;
               }
            }

            if (this.operatingSystemMXBean != null) {
               double doubleValue = this.operatingSystemMXBean.getProcessCpuLoad();
               if (doubleValue >= 0.0) {
                  return (float)Math.min(1.0, doubleValue);
               }
            }
         } catch (Throwable exception) {
         }

         return this.floatValue;
      }

      private float measure6() {
         try {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client != null) {
               return Math.max(0.0F, (float)client.getCurrentFps());
            }
         } catch (Throwable exception2) {
         }

         return this.floatValue2;
      }

      private float measure7() {
         try {
            MinecraftClient client2 = MinecraftClient.getInstance();
            if (client2 != null && client2.player != null && client2.getNetworkHandler() != null) {
               PlayerListEntry playerListEntry = client2.getNetworkHandler().getPlayerListEntry(client2.player.getUuid());
               if (playerListEntry != null) {
                  return Math.max(0.0F, (float)playerListEntry.getLatency());
               }
            }
         } catch (Throwable exception3) {
         }

         return this.floatValue3;
      }
   }
}
