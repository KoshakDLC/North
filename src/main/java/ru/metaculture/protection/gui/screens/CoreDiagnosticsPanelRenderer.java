package ru.metaculture.protection;

import java.util.ArrayList;
import net.minecraft.client.MinecraftClient;
import org.wild.module.api.Module;
import ru.metaculture.profile.Profile;

public final class CoreDiagnosticsPanelRenderer {
   private static final float FLOAT_VALUE = 330.0F;
   private static final float FLOAT_VALUE_2 = 18.0F;
   private static final float FLOAT_VALUE_3 = 108.0F;
   private static final float FLOAT_VALUE_4 = 126.0F;
   private static final float FLOAT_VALUE_5 = 32.0F;
   private static final float FLOAT_VALUE_6 = 32.0F;
   private static final float FLOAT_VALUE_7 = 10.0F;
   private static final float FLOAT_VALUE_8 = 34.0F;
   private static final float FLOAT_VALUE_9 = 7.0F;
   private static final float FLOAT_VALUE_10 = 56.0F;
   private static final float FLOAT_VALUE_11 = 8.0F;
   private static final float FLOAT_VALUE_12 = 12.0F;
   private static final float FLOAT_VALUE_13 = 24.0F;
   private static final float FLOAT_VALUE_14 = 8.0F;
   private static final float FLOAT_VALUE_15 = 4.0F;
   private static final float FLOAT_VALUE_16 = 4.0F;
   private static final float FLOAT_VALUE_17 = 9.0F;
   private static final int INT_VALUE = 4;
   private static final int INT_VALUE_2 = 5;
   private static final String UID = "UID";
   private static final String SYSTEM = "SYSTEM";
   private static final String SHADER_PIPELINE = "SHADER PIPELINE";
   private static final String TEMA = "Тема";
   private static final String MODULI = "Модули";
   private static final String WILD_CORE = "Wild Core";
   private static final String BUILD = "Build";
   private static final String SHADER_STAGE = "Shader Stage";
   private static final String SHADER_EXCEPTION = "Shader Exception";
   private static final String CFI_CHAIN = "CFI chain";
   private static final String FRAMES = "Frames";
   private static final String ANOMALIES = "Anomalies";
   private static final String TEXTURE_UNITS = "Texture Units";
   private static final String MATRICES = "Matrices";
   private static final String MIXIN_POLICY = "Mixin policy";
   private static final String DIAGNOSTIKA = "Диагностика";
   private static final String ZAKRYT = "Закрыть";
   private static final String WILD12181783538716222 = resolve3("wild-1.21.8-1783538716222");
   private static final TypeUtils TYPE_UTILS = new TypeUtils();
   private static final SpringSpec SPRING_SPEC = SpringSpec.resolve14();
   private static final SpringSpec SPRING_SPEC_2 = SpringSpec.resolve14();
   private static final SpringSpec SPRING_SPEC_3 = SpringSpec.resolve14();
   private static final SpringSpec SPRING_SPEC_4 = SpringSpec.resolve14();
   private static final SpringSpec SPRING_SPEC_5 = SpringSpec.resolve16();
   private final RenderDiagnosticsStatus renderDiagnosticsStatus = new RenderDiagnosticsStatus();
   private final SpringValue springValue = new SpringValue(0.0F);
   private final SpringValue springValue2 = new SpringValue(0.0F);
   private final SpringValue springValue3 = new SpringValue(0.0F);
   private final SpringValue springValue4 = new SpringValue(0.0F);
   private String text0 = "0";
   private String text02 = "0";
   private String text = "";
   private String corrupted = "CORRUPTED";
   private int intValue = Integer.MIN_VALUE;
   private int intValue2 = Integer.MIN_VALUE;

   public void invoke(RenderManager renderManager, ClickGuiState clickGuiState, ClickGuiGeometry clickGuiGeometry, ThemeContext themeContext, float f) {
      float floatValue = !clickGuiState.isFlag7() && clickGuiState.isFlag21() ? 1.0F : 0.0F;
      float floatValue2 = measure26(this.springValue.measure(floatValue, floatValue > 0.0F ? SPRING_SPEC : SPRING_SPEC_5));
      if (floatValue2 < 0.01F) {
         TYPE_UTILS.invoke6();
      } else {
         float floatValue3 = measure26(this.springValue2.measure(floatValue, floatValue > 0.0F ? SPRING_SPEC_2 : SPRING_SPEC_5));
         float floatValue4 = measure26(this.springValue3.measure(floatValue, floatValue > 0.0F ? SPRING_SPEC_3 : SPRING_SPEC_5));
         float floatValue5 = measure26(this.springValue4.measure(floatValue, floatValue > 0.0F ? SPRING_SPEC_4 : SPRING_SPEC_5));
         RenderDiagnosticsTracker.getInstance().invoke21(this.renderDiagnosticsStatus);
         this.invoke17();
         Metrics metrics = themeContext.getMetrics();
         ColorScheme colorScheme = themeContext.getColorScheme();
         float floatValue6 = measure5(clickGuiGeometry, metrics);
         float floatValue7 = measure6(clickGuiGeometry, metrics);
         float floatValue8 = measure7(metrics);
         float floatValue9 = measure8(clickGuiGeometry, metrics);
         float floatValue10 = measure18(metrics, floatValue9);
         float floatValue11 = clickGuiGeometry.getFloatValue3() + metrics.measure(36.0F);
         float floatValue12 = clickGuiGeometry.getFloatValue4() + metrics.getFloatValue9() - metrics.measure(36.0F);
         float floatValue13 = 0.965F + floatValue2 * 0.035F;
         float floatValue14 = measure25(floatValue2);
         float floatValue15 = metrics.measure(-18.0F) * (1.0F - floatValue14);
         float floatValue16 = metrics.measure(12.0F) * (1.0F - floatValue14);
         float floatValue17 = floatValue11 + (floatValue6 + floatValue8 * 0.5F - floatValue11) * floatValue2;
         float floatValue18 = floatValue12 + (floatValue7 + floatValue9 * 0.5F - floatValue12) * floatValue2;
         renderManager.invoke65(floatValue2);
         renderManager.invoke56(floatValue15, floatValue16);

         try {
            renderManager.invoke62(floatValue13, floatValue17, floatValue18);

            try {
               this.invoke2(renderManager, metrics, colorScheme, floatValue6, floatValue7, floatValue8, floatValue9, metrics.measure(14.0F), floatValue2);
               renderManager.invoke20();
               renderManager.invoke24(
                  floatValue6, floatValue7, floatValue8, floatValue9, metrics.measure(14.0F), metrics.measure(14.0F), metrics.measure(14.0F), metrics.measure(14.0F)
               );

               try {
                  float floatValue19 = measure24(floatValue3, 0.0F, 0.72F);
                  renderManager.invoke65(floatValue19);
                  renderManager.invoke56(metrics.measure(-10.0F) * (1.0F - floatValue19), 0.0F);

                  try {
                     this.invoke3(renderManager, clickGuiState, metrics, colorScheme, floatValue6, floatValue7, floatValue8, floatValue10, floatValue2 * floatValue19);
                  } finally {
                     renderManager.invoke57();
                     renderManager.invoke66();
                  }

                  float floatValue20 = measure24(floatValue4, 0.22F, 0.92F);
                  renderManager.invoke65(floatValue20);
                  renderManager.invoke56(metrics.measure(12.0F) * (1.0F - floatValue20), 0.0F);

                  try {
                     this.invoke6(renderManager, clickGuiState, metrics, colorScheme, floatValue6, floatValue7, floatValue8, floatValue9, floatValue10);
                  } finally {
                     renderManager.invoke57();
                     renderManager.invoke66();
                  }

                  float floatValue21 = measure24(floatValue5, 0.42F, 1.0F);
                  renderManager.invoke65(floatValue21);
                  renderManager.invoke56(0.0F, metrics.measure(10.0F) * (1.0F - floatValue21));
                  boolean flag = false ;

                  try {
                     flag = true;
                     this.invoke10(renderManager, clickGuiState, metrics, colorScheme, clickGuiGeometry);
                     flag = false;
                  } finally {
                     if (flag) {
                        renderManager.invoke57();
                        renderManager.invoke66();
                     }
                  }

                  renderManager.invoke57();
                  renderManager.invoke66();
               } finally {
                  renderManager.invoke20();
                  renderManager.invoke25();
               }
            } finally {
               renderManager.invoke64();
            }
         } finally {
            renderManager.invoke57();
            renderManager.invoke66();
         }
      }
   }

   private void invoke2(RenderManager renderManager2, Metrics metrics2, ColorScheme colorScheme2, float f, float g, float h, float i, float j, float k) {
      if (!colorScheme2.isFlag()) {
         renderManager2.invoke41(
            f, g, h, i, j, metrics2.measure(30.0F) * k, metrics2.measure(6.0F), ColorScheme.compute5(0, 0, 0, Math.round(140.0F * k))
         );
      }

      renderManager2.invoke44(f, g, h, i, j, colorScheme2.isFlag() ? 0.92F : 0.86F);
      int intValue = colorScheme2.isFlag()
         ? ColorScheme.compute5(255, 255, 255, 234)
         : ColorScheme.compute7(ColorScheme.compute5(6, 8, 15, 246), ColorScheme.compute6(colorScheme2.getIntValue15(), 118), 0.12F);
      renderManager2.invoke5(f, g, h, i, j, intValue);
      int intValue2 = colorScheme2.isFlag() ? ColorScheme.compute5(255, 255, 255, 150) : ColorScheme.compute6(colorScheme2.getIntValue14(), 38);
      renderManager2.invoke28(f, g, h, i, j, intValue2, Math.max(1.0F, metrics2.measure(1.0F)));
      renderManager2.invoke28(
         f + metrics2.measure(1.0F),
         g + metrics2.measure(1.0F),
         Math.max(1.0F, h - metrics2.measure(2.0F)),
         Math.max(1.0F, i - metrics2.measure(2.0F)),
         Math.max(0.0F, j - metrics2.measure(1.0F)),
         colorScheme2.isFlag() ? ColorScheme.compute5(255, 255, 255, 70) : colorScheme2.getIntValue6(),
         0.5F
      );
   }

   private void invoke3(
      RenderManager renderManager3, ClickGuiState clickGuiState2, Metrics metrics3, ColorScheme colorScheme3, float f, float g, float h, float i, float j
   ) {
      float floatValue22 = metrics3.measure(18.0F);
      float floatValue23 = measure28(f + floatValue22);
      float floatValue24 = measure28(g + floatValue22);
      float floatValue25 = measure28(h - floatValue22 * 2.0F);
      float floatValue26 = measure28(i - metrics3.measure(8.0F));
      float floatValue27 = metrics3.measure(12.0F);
      this.invoke4(renderManager3, metrics3, colorScheme3, floatValue23, floatValue24, floatValue25, floatValue26, floatValue27);
      float floatValue28 = measure28(measure27(floatValue25 * 0.26F, metrics3.measure(64.0F), metrics3.measure(72.0F)));
      float floatValue29 = measure28(floatValue23 + metrics3.measure(16.0F));
      float floatValue30 = measure28(floatValue24 + (floatValue26 - floatValue28) * 0.5F);
      this.invoke5(renderManager3, metrics3, colorScheme3, floatValue29, floatValue30, floatValue28, j);
      int intValue3 = this.renderDiagnosticsStatus.intValue == 0 ? colorScheme3.compute() : colorScheme3.compute2();
      float floatValue31 = this.measure(renderManager3, clickGuiState2, metrics3, colorScheme3, floatValue23, floatValue24, floatValue25, floatValue26, intValue3);
      float floatValue32 = floatValue30 + floatValue28 * 0.5F;
      float floatValue33 = measure28(floatValue29 + floatValue28 + metrics3.measure(16.0F));
      float floatValue34 = measure28(floatValue33 + metrics3.measure(9.0F));
      float floatValue35 = Math.max(metrics3.measure(56.0F), floatValue31 - metrics3.measure(12.0F) - floatValue34);
      renderManager3.invoke37(
         floatValue33,
         measure28(floatValue32 - metrics3.measure(15.0F)),
         metrics3.measure(1.0F),
         metrics3.measure(10.0F),
         metrics3.measure(1.0F),
         ColorScheme.compute6(colorScheme3.getIntValue14(), 255),
         ColorScheme.compute6(colorScheme3.getIntValue15(), 255)
      );
      this.invoke14(
         renderManager3,
         clickGuiState2,
         metrics3,
         FontRegistry.fontObject4,
         floatValue34,
         measure28(floatValue32 - metrics3.measure(20.0F)),
         metrics3.measure(20.0F),
         16.0F,
         Profile.getUsername(),
         ClickGuiRenderUtils.compute2(colorScheme3),
         floatValue35
      );
      this.invoke14(
         renderManager3,
         clickGuiState2,
         metrics3,
         FontRegistry.fontObject,
         floatValue34,
         measure28(floatValue32 + metrics3.measure(2.0F)),
         metrics3.measure(15.0F),
         8.0F,
         "UID " + this.text0,
         ClickGuiRenderUtils.compute4(colorScheme3),
         floatValue35
      );
   }

   private void invoke4(RenderManager renderManager4, Metrics metrics4, ColorScheme colorScheme4, float f, float g, float h, float i, float j) {
      if (colorScheme4.isFlag()) {
         renderManager4.invoke41(
            f, g + metrics4.measure(2.0F), h, i, j, metrics4.measure(16.0F), metrics4.measure(2.0F), ColorScheme.compute5(0, 0, 0, 26)
         );
         int intValue4 = ColorScheme.compute5(255, 255, 255, 240);
         int intValue5 = ColorScheme.compute7(intValue4, ColorScheme.compute6(colorScheme4.getIntValue14(), 255), 0.14F);
         int intValue6 = ColorScheme.compute7(intValue4, ColorScheme.compute6(colorScheme4.getIntValue15(), 255), 0.09F);
         int intValue7 = ColorScheme.compute7(intValue4, ColorScheme.compute6(colorScheme4.getIntValue14(), 255), 0.035F);
         renderManager4.invoke31(f, g, h, i, j, intValue5, intValue7, intValue6, intValue7);
         renderManager4.invoke38(f, g, h, i * 0.42F, j, j, 0.0F, 0.0F, ColorScheme.compute5(255, 255, 255, 140), ColorScheme.compute5(255, 255, 255, 0));
         renderManager4.invoke28(f, g, h, i, j, ColorScheme.compute6(colorScheme4.getIntValue14(), 44), Math.max(1.0F, metrics4.measure(0.9F)));
      } else {
         renderManager4.invoke41(
            f, g + metrics4.measure(3.0F), h, i, j, metrics4.measure(22.0F), metrics4.measure(3.0F), ColorScheme.compute5(0, 0, 0, 128)
         );
         int intValue8 = ColorScheme.compute5(9, 12, 21, 240);
         int intValue9 = ColorScheme.compute7(intValue8, ColorScheme.compute6(colorScheme4.getIntValue14(), 255), 0.3F);
         int intValue10 = ColorScheme.compute7(intValue8, ColorScheme.compute6(colorScheme4.getIntValue14(), 255), 0.09F);
         int intValue11 = ColorScheme.compute7(intValue8, ColorScheme.compute6(colorScheme4.getIntValue15(), 255), 0.24F);
         int intValue12 = ColorScheme.compute7(intValue8, ColorScheme.compute6(colorScheme4.getIntValue15(), 255), 0.06F);
         renderManager4.invoke31(f, g, h, i, j, intValue9, intValue10, intValue11, intValue12);
         renderManager4.invoke38(
            f, g, h, i * 0.46F, j, j, 0.0F, 0.0F, ColorScheme.compute6(colorScheme4.getIntValue13(), 18), ColorScheme.compute5(0, 0, 0, 0)
         );
         renderManager4.invoke28(f, g, h, i, j, ColorScheme.compute6(colorScheme4.getIntValue14(), 42), Math.max(1.0F, metrics4.measure(0.9F)));
         renderManager4.invoke28(
            f + metrics4.measure(1.0F),
            g + metrics4.measure(1.0F),
            Math.max(1.0F, h - metrics4.measure(2.0F)),
            Math.max(1.0F, i - metrics4.measure(2.0F)),
            Math.max(0.0F, j - metrics4.measure(1.0F)),
            colorScheme4.getIntValue6(),
            0.5F
         );
      }
   }

   private float measure(
      RenderManager renderManager5, ClickGuiState clickGuiState3, Metrics metrics5, ColorScheme colorScheme5, float f, float g, float h, float i, int j
   ) {
      String text = resolve4(this.renderDiagnosticsStatus.nominal);
      float floatValue36 = Math.min(h * 0.3F, ClickGuiRenderUtils.measure2(metrics5, FontRegistry.fontObject4, text, 8.0F));
      float floatValue37 = metrics5.measure(22.0F);
      float floatValue38 = measure28(floatValue36 + metrics5.measure(28.0F));
      float floatValue39 = measure28(f + h - metrics5.measure(14.0F) - floatValue38);
      float floatValue40 = measure28(g + (i - floatValue37) * 0.5F);
      boolean flag2 = ClickGuiRenderUtils.check(clickGuiState3, floatValue39, floatValue40, floatValue38, floatValue37);
      float floatValue41 = clickGuiState3.measure5("profile:chip:hover", flag2 ? 1.0F : 0.0F, SpringSpec.resolve12());
      int intValue13 = ColorScheme.compute6(j, Math.round((colorScheme5.isFlag() ? 24.0F : 34.0F) + 18.0F * floatValue41));
      renderManager5.invoke5(floatValue39, floatValue40, floatValue38, floatValue37, floatValue37 * 0.5F, intValue13);
      renderManager5.invoke28(
         floatValue39, floatValue40, floatValue38, floatValue37, floatValue37 * 0.5F, ColorScheme.compute6(j, Math.round(96.0F + 60.0F * floatValue41)), metrics5.measure(0.6F)
      );
      renderManager5.invoke39(
         floatValue39 + metrics5.measure(10.0F), floatValue40 + floatValue37 * 0.5F, metrics5.measure(2.2F), 0.0F, 1.0F, ColorScheme.compute6(j, 232)
      );
      int intValue14 = colorScheme5.isFlag() ? j : ColorScheme.compute7(j, colorScheme5.getIntValue13(), 0.22F);
      this.invoke14(
         renderManager5,
         clickGuiState3,
         metrics5,
         FontRegistry.fontObject4,
         floatValue39 + metrics5.measure(17.0F),
         floatValue40,
         floatValue37,
         8.0F,
         text,
         intValue14,
         floatValue38 - metrics5.measure(24.0F)
      );
      return floatValue39;
   }

   private void invoke5(RenderManager renderManager6, Metrics metrics6, ColorScheme colorScheme6, float f, float g, float h, float i) {
      float floatValue42 = h * 0.5F;
      float floatValue43 = f + floatValue42;
      float floatValue44 = g + floatValue42;
      renderManager6.invoke41(
         f - metrics6.measure(2.0F),
         g - metrics6.measure(2.0F),
         h + metrics6.measure(4.0F),
         h + metrics6.measure(4.0F),
         floatValue42 + metrics6.measure(4.0F),
         metrics6.measure(20.0F),
         metrics6.measure(2.5F),
         ColorScheme.compute6(colorScheme6.getIntValue14(), colorScheme6.isFlag() ? 44 : 92)
      );
      int intValue15 = ProfileAvatarTexture.compute();
      if (intValue15 > 0) {
         renderManager6.invoke39(
            floatValue43, floatValue44, floatValue42, 0.0F, 1.0F, colorScheme6.isFlag() ? ColorScheme.compute5(244, 246, 250, 255) : ColorScheme.compute5(9, 12, 20, 255)
         );
         renderManager6.invoke20();
         ProfileAvatarShader.invoke(f, g, h, intValue15, colorScheme6.getIntValue14(), colorScheme6.getIntValue15(), i, colorScheme6.isFlag());
      } else {
         renderManager6.invoke39(
            floatValue43,
            floatValue44,
            floatValue42 + metrics6.measure(2.5F),
            0.0F,
            1.0F,
            colorScheme6.isFlag() ? ColorScheme.compute5(255, 255, 255, 250) : ColorScheme.compute5(6, 9, 16, 255)
         );
         renderManager6.invoke39(
            floatValue43,
            floatValue44,
            floatValue42 + metrics6.measure(1.0F),
            0.0F,
            1.0F,
            ColorScheme.compute6(colorScheme6.getIntValue14(), colorScheme6.isFlag() ? 150 : 224)
         );
         renderManager6.invoke39(
            floatValue43,
            floatValue44,
            floatValue42 - metrics6.measure(0.5F),
            0.0F,
            1.0F,
            colorScheme6.isFlag() ? ColorScheme.compute5(250, 251, 254, 255) : ColorScheme.compute5(13, 18, 30, 255)
         );
         NotificationBellRenderer.invoke(
            renderManager6,
            metrics6,
            floatValue43,
            floatValue44 + metrics6.measure(0.6F),
            metrics6.measure(1.12F),
            ClickGuiRenderUtils.compute7(colorScheme6),
            ColorScheme.compute6(colorScheme6.getIntValue15(), colorScheme6.isFlag() ? 28 : 62)
         );
      }
   }

   private void invoke6(
      RenderManager renderManager7, ClickGuiState clickGuiState4, Metrics metrics7, ColorScheme colorScheme7, float f, float g, float h, float i, float j
   ) {
      float floatValue45 = measure19(f, metrics7);
      float floatValue46 = measure20(g, metrics7, j);
      float floatValue47 = measure21(h, metrics7);
      float floatValue48 = measure22(g, i, metrics7, j);
      float floatValue49 = measure23(metrics7);
      if (floatValue49 <= floatValue48 + metrics7.measure(1.0F)) {
         TYPE_UTILS.invoke6();
      }

      TYPE_UTILS.setFloatValue4(7.5F);
      TYPE_UTILS.setFlag(true);
      TYPE_UTILS.invoke7(Math.max(floatValue49, floatValue48), floatValue48);
      TYPE_UTILS.invoke();
      float floatValue50 = measure27(TYPE_UTILS.measure(), Math.min(0.0F, TYPE_UTILS.getFloatValue3()), 0.0F);
      float floatValue51 = metrics7.measure(10.0F);
      renderManager7.invoke20();
      renderManager7.invoke24(floatValue45, floatValue46, floatValue47, floatValue48, floatValue51, floatValue51, floatValue51, floatValue51);

      try {
         float floatValue52 = measure28(floatValue46 + metrics7.measure(4.0F) + floatValue50);
         floatValue52 = this.measure2(renderManager7, clickGuiState4, metrics7, colorScheme7, floatValue45, floatValue46, floatValue47, floatValue48, floatValue52);
         floatValue52 = this.measure3(renderManager7, metrics7, colorScheme7, floatValue45, floatValue46, floatValue47, floatValue48, floatValue52, "SYSTEM");
         floatValue52 = this.measure4(
            renderManager7,
            clickGuiState4,
            metrics7,
            colorScheme7,
            floatValue45,
            floatValue46,
            floatValue47,
            floatValue48,
            floatValue52,
            "Тема",
            clickGuiState4.getTheme().name(),
            colorScheme7.getIntValue14(),
            0
         );
         floatValue52 = this.measure4(
            renderManager7,
            clickGuiState4,
            metrics7,
            colorScheme7,
            floatValue45,
            floatValue46,
            floatValue47,
            floatValue48,
            floatValue52,
            "Wild Core",
            this.renderDiagnosticsStatus.intValue == 0 ? resolve4(this.renderDiagnosticsStatus.nominal) : resolve4(this.renderDiagnosticsStatus.none2),
            this.renderDiagnosticsStatus.intValue == 0 ? colorScheme7.getIntValue14() : colorScheme7.compute2(),
            1
         );
         floatValue52 = this.measure4(
            renderManager7,
            clickGuiState4,
            metrics7,
            colorScheme7,
            floatValue45,
            floatValue46,
            floatValue47,
            floatValue48,
            floatValue52,
            "Build",
            WILD12181783538716222,
            colorScheme7.getIntValue15(),
            2
         );
         floatValue52 = this.measure4(
            renderManager7,
            clickGuiState4,
            metrics7,
            colorScheme7,
            floatValue45,
            floatValue46,
            floatValue47,
            floatValue48,
            floatValue52,
            "Matrices",
            this.getCorrupted(),
            this.compute3(colorScheme7),
            3
         );
         floatValue52 = measure28(floatValue52 + metrics7.measure(8.0F));
         floatValue52 = this.measure3(renderManager7, metrics7, colorScheme7, floatValue45, floatValue46, floatValue47, floatValue48, floatValue52, "SHADER PIPELINE");
         floatValue52 = this.measure4(
            renderManager7,
            clickGuiState4,
            metrics7,
            colorScheme7,
            floatValue45,
            floatValue46,
            floatValue47,
            floatValue48,
            floatValue52,
            "Shader Stage",
            resolve4(this.renderDiagnosticsStatus.none5),
            colorScheme7.getIntValue14(),
            4
         );
         floatValue52 = this.measure4(
            renderManager7,
            clickGuiState4,
            metrics7,
            colorScheme7,
            floatValue45,
            floatValue46,
            floatValue47,
            floatValue48,
            floatValue52,
            "Shader Exception",
            resolve4(this.renderDiagnosticsStatus.none6),
            "0".equals(this.renderDiagnosticsStatus.text0) ? colorScheme7.getIntValue15() : colorScheme7.compute2(),
            5
         );
         floatValue52 = this.measure4(
            renderManager7,
            clickGuiState4,
            metrics7,
            colorScheme7,
            floatValue45,
            floatValue46,
            floatValue47,
            floatValue48,
            floatValue52,
            "CFI chain",
            resolve4(this.renderDiagnosticsStatus.text0x0000000000000000),
            colorScheme7.getIntValue14(),
            6
         );
         floatValue52 = this.measure4(
            renderManager7,
            clickGuiState4,
            metrics7,
            colorScheme7,
            floatValue45,
            floatValue46,
            floatValue47,
            floatValue48,
            floatValue52,
            "Texture Units",
            this.resolve2(),
            colorScheme7.getIntValue14(),
            7
         );
         this.measure4(
            renderManager7,
            clickGuiState4,
            metrics7,
            colorScheme7,
            floatValue45,
            floatValue46,
            floatValue47,
            floatValue48,
            floatValue52,
            "Mixin policy",
            resolve4(this.renderDiagnosticsStatus.injectHeadTail),
            colorScheme7.getIntValue15(),
            8
         );
      } finally {
         renderManager7.invoke20();
         renderManager7.invoke25();
      }

      this.invoke12(renderManager7, metrics7, colorScheme7, floatValue45, floatValue46, floatValue47, floatValue48, floatValue49, floatValue50);
      this.invoke13(renderManager7, clickGuiState4, metrics7, colorScheme7, floatValue45, floatValue46, floatValue47, floatValue48, floatValue49, floatValue50);
   }

   public static void invoke7(float f) {
      float floatValue53 = Math.min(0.0F, TYPE_UTILS.getFloatValue3());
      TYPE_UTILS.setFloatValue(floatValue53 * Math.max(0.0F, Math.min(1.0F, f)));
   }

   private float measure2(
      RenderManager renderManager8, ClickGuiState clickGuiState5, Metrics metrics8, ColorScheme colorScheme8, float f, float g, float h, float i, float j
   ) {
      float floatValue54 = metrics8.measure(56.0F);
      if (j + floatValue54 >= g - metrics8.measure(3.0F) && j <= g + i + metrics8.measure(3.0F)) {
         float floatValue55 = measure28(f + metrics8.measure(4.0F));
         float floatValue56 = Math.max(metrics8.measure(120.0F), h - metrics8.measure(13.0F));
         float floatValue57 = metrics8.measure(8.0F);
         float floatValue58 = measure28((floatValue56 - floatValue57 * 2.0F) / 3.0F);
         float floatValue59 = measure28(floatValue55 + (floatValue58 + floatValue57) * 2.0F);
         int intValue16 = this.renderDiagnosticsStatus.intValue == 0 ? colorScheme8.compute() : colorScheme8.compute2();
         this.invoke8(
            renderManager8, clickGuiState5, metrics8, colorScheme8, floatValue55, j, floatValue58, floatValue54, this.text02, "Модули", colorScheme8.getIntValue14(), 0
         );
         this.invoke8(
            renderManager8,
            clickGuiState5,
            metrics8,
            colorScheme8,
            measure28(floatValue55 + floatValue58 + floatValue57),
            j,
            floatValue58,
            floatValue54,
            resolve4(this.renderDiagnosticsStatus.text03),
            "Frames",
            colorScheme8.getIntValue15(),
            1
         );
         this.invoke8(
            renderManager8,
            clickGuiState5,
            metrics8,
            colorScheme8,
            floatValue59,
            j,
            Math.max(metrics8.measure(40.0F), floatValue55 + floatValue56 - floatValue59),
            floatValue54,
            resolve4(this.renderDiagnosticsStatus.text02),
            "Anomalies",
            intValue16,
            2
         );
      }

      return measure28(j + floatValue54 + metrics8.measure(12.0F));
   }

   private void invoke8(
      RenderManager renderManager9,
      ClickGuiState clickGuiState6,
      Metrics metrics9,
      ColorScheme colorScheme9,
      float f,
      float g,
      float h,
      float i,
      String string,
      String string2,
      int j,
      int k
   ) {
      float floatValue60 = measure28(f);
      float floatValue61 = measure28(g);
      float floatValue62 = measure29(floatValue60, h);
      float floatValue63 = measure29(floatValue61, i);
      boolean flag3 = ClickGuiRenderUtils.check(clickGuiState6, floatValue60, floatValue61, floatValue62, floatValue63);
      float floatValue64 = clickGuiState6.measure5("profile:tile:hover:" + k, flag3 ? 1.0F : 0.0F, SpringSpec.resolve12());
      float floatValue65 = measure25(floatValue64);
      renderManager9.invoke56(0.0F, -metrics9.measure(0.9F) * floatValue65);
      renderManager9.invoke62(1.0F + floatValue65 * 0.009F, floatValue60 + floatValue62 * 0.5F, floatValue61 + floatValue63 * 0.5F);

      try {
         float floatValue66 = metrics9.measure(10.0F);
         int intValue17 = colorScheme9.isFlag()
            ? ColorScheme.compute7(ClickGuiRenderUtils.compute12(colorScheme9, 0.16F), ColorScheme.compute6(j, 30), 0.1F + floatValue65 * 0.26F)
            : ColorScheme.compute7(ColorScheme.compute5(8, 12, 23, 186), ColorScheme.compute6(j, 58), 0.16F + floatValue65 * 0.16F);
         if (floatValue65 > 0.01F) {
            renderManager9.invoke41(
               floatValue60,
               floatValue61,
               floatValue62,
               floatValue63,
               floatValue66,
               metrics9.measure(8.0F) * floatValue65,
               metrics9.measure(1.4F),
               ColorScheme.compute6(j, Math.round(30.0F * floatValue65))
            );
         }

         renderManager9.invoke5(floatValue60, floatValue61, floatValue62, floatValue63, floatValue66, intValue17);
         renderManager9.invoke28(
            floatValue60,
            floatValue61,
            floatValue62,
            floatValue63,
            floatValue66,
            ColorScheme.compute7(
               colorScheme9.isFlag() ? ColorScheme.compute5(255, 255, 255, 70) : colorScheme9.getIntValue6(), ColorScheme.compute6(j, 150), floatValue65
            ),
            metrics9.measure(0.55F + floatValue65 * 0.3F)
         );
         this.invoke14(
            renderManager9,
            clickGuiState6,
            metrics9,
            FontRegistry.fontObject4,
            measure28(floatValue60 + metrics9.measure(12.0F)),
            measure28(floatValue61 + metrics9.measure(7.0F)),
            metrics9.measure(24.0F),
            15.0F,
            resolve4(string),
            ClickGuiRenderUtils.compute2(colorScheme9),
            floatValue62 - metrics9.measure(22.0F)
         );
         this.invoke14(
            renderManager9,
            clickGuiState6,
            metrics9,
            FontRegistry.fontObject,
            measure28(floatValue60 + metrics9.measure(12.0F)),
            measure28(floatValue61 + floatValue63 - metrics9.measure(24.0F)),
            metrics9.measure(16.0F),
            7.0F,
            string2,
            ColorScheme.compute7(ClickGuiRenderUtils.compute4(colorScheme9), ClickGuiRenderUtils.compute2(colorScheme9), floatValue65 * 0.24F),
            floatValue62 - metrics9.measure(22.0F)
         );
      } finally {
         renderManager9.invoke64();
         renderManager9.invoke57();
      }
   }

   private float measure3(
      RenderManager renderManager10, Metrics metrics10, ColorScheme colorScheme10, float f, float g, float h, float i, float j, String string
   ) {
      float floatValue67 = metrics10.measure(24.0F);
      if (j + floatValue67 >= g - metrics10.measure(3.0F) && j <= g + i + metrics10.measure(3.0F)) {
         float floatValue68 = measure28(f + metrics10.measure(6.0F));
         float floatValue69 = measure28(f + h - metrics10.measure(9.0F));
         float floatValue70 = metrics10.measure(16.0F);
         float floatValue71 = measure28(j + floatValue67 - floatValue70 - metrics10.measure(2.0F));
         ClickGuiRenderUtils.invoke4(
            renderManager10, metrics10, FontRegistry.fontObject4, floatValue68, floatValue71, floatValue70, 7.5F, string, ClickGuiRenderUtils.compute4(colorScheme10)
         );
         float floatValue72 = ClickGuiRenderUtils.measure2(metrics10, FontRegistry.fontObject4, string, 7.5F);
         float floatValue73 = measure28(floatValue68 + floatValue72 + metrics10.measure(10.0F));
         float floatValue74 = floatValue69 - floatValue73;
         if (floatValue74 > metrics10.measure(8.0F)) {
            renderManager10.invoke5(
               floatValue73,
               measure28(floatValue71 + floatValue70 * 0.5F),
               floatValue74,
               Math.max(1.0F, metrics10.measure(1.0F)),
               metrics10.measure(0.5F),
               colorScheme10.isFlag() ? ColorScheme.compute5(0, 0, 0, 26) : colorScheme10.getIntValue7()
            );
         }
      }

      return measure28(j + floatValue67);
   }

   private float measure4(
      RenderManager renderManager11,
      ClickGuiState clickGuiState7,
      Metrics metrics11,
      ColorScheme colorScheme11,
      float f,
      float g,
      float h,
      float i,
      float j,
      String string,
      String string2,
      int k,
      int l
   ) {
      float floatValue75 = metrics11.measure(34.0F);
      if (j + floatValue75 >= g - metrics11.measure(3.0F) && j <= g + i + metrics11.measure(3.0F)) {
         float floatValue76 = f + metrics11.measure(4.0F);
         float floatValue77 = Math.max(metrics11.measure(80.0F), h - metrics11.measure(13.0F));
         this.invoke9(renderManager11, clickGuiState7, metrics11, colorScheme11, floatValue76, j, floatValue77, floatValue75, string, string2, k, l);
      }

      return measure28(j + floatValue75 + metrics11.measure(7.0F));
   }

   private void invoke9(
      RenderManager renderManager12,
      ClickGuiState clickGuiState8,
      Metrics metrics12,
      ColorScheme colorScheme12,
      float f,
      float g,
      float h,
      float i,
      String string,
      String string2,
      int j,
      int k
   ) {
      float floatValue78 = measure28(f);
      float floatValue79 = measure28(g);
      float floatValue80 = measure29(floatValue78, h);
      float floatValue81 = measure29(floatValue79, i);
      boolean flag4 = ClickGuiRenderUtils.check(clickGuiState8, floatValue78, floatValue79, floatValue80, floatValue81);
      float floatValue82 = clickGuiState8.measure5("profile:row:hover:" + k, flag4 ? 1.0F : 0.0F, SpringSpec.resolve12());
      float floatValue83 = measure25(floatValue82);
      renderManager12.invoke56(0.0F, -metrics12.measure(0.9F) * floatValue83);
      renderManager12.invoke62(1.0F + floatValue83 * 0.009F, floatValue78 + floatValue80 * 0.5F, floatValue79 + floatValue81 * 0.5F);
      boolean flag5 = false ;

      try {
         flag5 = true;
         int intValue18 = colorScheme12.isFlag()
            ? ColorScheme.compute7(ClickGuiRenderUtils.compute12(colorScheme12, 0.14F), ColorScheme.compute6(j, 30), 0.08F + floatValue83 * 0.28F)
            : ColorScheme.compute7(ColorScheme.compute5(8, 12, 23, 164), ColorScheme.compute6(j, 52), 0.14F + floatValue83 * 0.16F);
         if (floatValue83 > 0.01F) {
            renderManager12.invoke41(
               floatValue78,
               floatValue79,
               floatValue80,
               floatValue81,
               metrics12.measure(9.0F),
               metrics12.measure(7.0F) * floatValue83,
               metrics12.measure(1.2F),
               ColorScheme.compute6(j, Math.round(28.0F * floatValue83))
            );
         }

         renderManager12.invoke5(floatValue78, floatValue79, floatValue80, floatValue81, metrics12.measure(9.0F), intValue18);
         renderManager12.invoke28(
            floatValue78,
            floatValue79,
            floatValue80,
            floatValue81,
            metrics12.measure(9.0F),
            ColorScheme.compute7(
               colorScheme12.isFlag() ? ColorScheme.compute5(255, 255, 255, 70) : colorScheme12.getIntValue6(), ColorScheme.compute6(j, 154), floatValue83
            ),
            metrics12.measure(0.55F + floatValue83 * 0.3F)
         );
         if (floatValue83 > 0.01F) {
            renderManager12.invoke5(
               floatValue78 + metrics12.measure(1.2F),
               floatValue79 + floatValue81 * 0.27F,
               metrics12.measure(1.8F),
               floatValue81 * 0.46F,
               metrics12.measure(0.9F),
               ColorScheme.compute6(j, Math.round(196.0F * floatValue83))
            );
         }

         this.invoke15(renderManager12, metrics12, floatValue78 + metrics12.measure(17.0F), floatValue79 + floatValue81 * 0.5F, k, j, colorScheme12);
         float floatValue84 = measure28(floatValue78 + metrics12.measure(34.0F));
         float floatValue85 = measure28(floatValue78 + floatValue80 * 0.52F);
         int intValue19 = ColorScheme.compute7(ClickGuiRenderUtils.compute4(colorScheme12), ClickGuiRenderUtils.compute2(colorScheme12), floatValue83 * 0.28F);
         this.invoke14(
            renderManager12,
            clickGuiState8,
            metrics12,
            FontRegistry.fontObject,
            floatValue84,
            floatValue79,
            floatValue81,
            8.0F,
            string,
            intValue19,
            Math.max(metrics12.measure(34.0F), floatValue85 - floatValue84 - metrics12.measure(10.0F))
         );
         this.invoke14(
            renderManager12,
            clickGuiState8,
            metrics12,
            FontRegistry.fontObject4,
            floatValue85,
            floatValue79,
            floatValue81,
            8.5F,
            resolve4(string2),
            ClickGuiRenderUtils.compute2(colorScheme12),
            Math.max(metrics12.measure(42.0F), floatValue78 + floatValue80 - floatValue85 - metrics12.measure(12.0F))
         );
         flag5 = false;
      } finally {
         if (flag5) {
            renderManager12.invoke64();
            renderManager12.invoke57();
         }
      }

      renderManager12.invoke64();
      renderManager12.invoke57();
   }

   private void invoke10(
      RenderManager renderManager13, ClickGuiState clickGuiState9, Metrics metrics13, ColorScheme colorScheme13, ClickGuiGeometry clickGuiGeometry2
   ) {
      float floatValue86 = this.measure11(clickGuiGeometry2, metrics13);
      float floatValue87 = this.measure10(clickGuiGeometry2, metrics13);
      float floatValue88 = this.measure12(metrics13);
      this.invoke11(renderManager13, clickGuiState9, metrics13, colorScheme13, floatValue87, floatValue86, floatValue88, "Диагностика", colorScheme13.getIntValue14(), 0);
      this.invoke11(
         renderManager13,
         clickGuiState9,
         metrics13,
         colorScheme13,
         this.measure14(clickGuiGeometry2, metrics13),
         this.measure15(clickGuiGeometry2, metrics13),
         this.measure16(metrics13),
         "Закрыть",
         ClickGuiRenderUtils.compute2(colorScheme13),
         2
      );
   }

   private void invoke11(
      RenderManager renderManager14,
      ClickGuiState clickGuiState10,
      Metrics metrics14,
      ColorScheme colorScheme14,
      float f,
      float g,
      float h,
      String string,
      int i,
      int j
   ) {
      float floatValue89 = measure28(f);
      float floatValue90 = measure28(g);
      float floatValue91 = measure29(floatValue89, h);
      float floatValue92 = metrics14.measure(j == 2 ? 32.0F : 32.0F);
      boolean flag6 = ClickGuiRenderUtils.check(clickGuiState10, floatValue89, floatValue90, floatValue91, floatValue92);
      float floatValue93 = clickGuiState10.measure5("profile:action:hover:" + j, flag6 ? 1.0F : 0.0F, SpringSpec.resolve11());
      float floatValue94 = measure25(floatValue93);
      renderManager14.invoke56(0.0F, -metrics14.measure(1.0F) * floatValue94);
      renderManager14.invoke62(1.0F + floatValue94 * 0.014F, floatValue89 + floatValue91 * 0.5F, floatValue90 + floatValue92 * 0.5F);
      boolean flag7 = false ;

      try {
         flag7 = true;
         int intValue20 = colorScheme14.isFlag()
            ? ColorScheme.compute7(ClickGuiRenderUtils.compute12(colorScheme14, 0.25F), ColorScheme.compute6(i, 42), floatValue94 * 0.34F)
            : ColorScheme.compute7(ColorScheme.compute5(8, 12, 23, 188), ColorScheme.compute6(i, 68), 0.18F + floatValue94 * 0.22F);
         if (floatValue94 > 0.01F) {
            renderManager14.invoke41(
               floatValue89,
               floatValue90,
               floatValue91,
               floatValue92,
               metrics14.measure(9.0F),
               metrics14.measure(8.0F) * floatValue94,
               metrics14.measure(1.5F),
               ColorScheme.compute6(i, Math.round(34.0F * floatValue94))
            );
         }

         renderManager14.invoke5(floatValue89, floatValue90, floatValue91, floatValue92, metrics14.measure(9.0F), intValue20);
         renderManager14.invoke28(
            floatValue89,
            floatValue90,
            floatValue91,
            floatValue92,
            metrics14.measure(9.0F),
            ColorScheme.compute6(i, Math.round((colorScheme14.isFlag() ? 58.0F : 84.0F) + 92.0F * floatValue94)),
            metrics14.measure(0.6F + 0.25F * floatValue94)
         );
         this.invoke16(renderManager14, metrics14, floatValue89 + metrics14.measure(17.0F), floatValue90 + floatValue92 * 0.5F, j, i, colorScheme14);
         this.invoke14(
            renderManager14,
            clickGuiState10,
            metrics14,
            FontRegistry.fontObject4,
            floatValue89 + metrics14.measure(34.0F + floatValue94 * 1.5F),
            floatValue90,
            floatValue92,
            8.5F,
            string,
            ClickGuiRenderUtils.compute2(colorScheme14),
            floatValue91 - metrics14.measure(44.0F)
         );
         flag7 = false;
      } finally {
         if (flag7) {
            renderManager14.invoke64();
            renderManager14.invoke57();
         }
      }

      renderManager14.invoke64();
      renderManager14.invoke57();
   }

   private void invoke12(
      RenderManager renderManager15, Metrics metrics15, ColorScheme colorScheme15, float f, float g, float h, float i, float j, float k
   ) {
      float floatValue95 = metrics15.measure(12.0F);
      int intValue21 = colorScheme15.isFlag() ? ColorScheme.compute5(255, 255, 255, 142) : ColorScheme.compute5(5, 7, 13, 166);
      int intValue22 = colorScheme15.isFlag() ? ColorScheme.compute5(255, 255, 255, 0) : ColorScheme.compute5(5, 7, 13, 0);
      if (k < -metrics15.measure(0.5F)) {
         renderManager15.invoke36(f, g, h, floatValue95, intValue21, intValue22);
      }

      if (j + k > i + metrics15.measure(0.5F)) {
         renderManager15.invoke36(f, g + i - floatValue95, h, floatValue95, intValue22, intValue21);
      }
   }

   private void invoke13(
      RenderManager renderManager16,
      ClickGuiState clickGuiState11,
      Metrics metrics16,
      ColorScheme colorScheme16,
      float f,
      float g,
      float h,
      float i,
      float j,
      float k
   ) {
      float floatValue96 = Math.max(0.0F, j - i);
      if (!(floatValue96 <= metrics16.measure(1.0F))) {
         float floatValue97 = Math.max(metrics16.measure(4.0F), metrics16.measure(5.0F));
         float floatValue98 = measure28(f + h - floatValue97);
         float floatValue99 = Math.max(metrics16.measure(24.0F), i * i / Math.max(i, j));
         float floatValue100 = measure28(g + (i - floatValue99) * (Math.abs(k) / Math.max(1.0F, floatValue96)));
         ClickGuiRenderUtils.invoke17(
            renderManager16,
            metrics16,
            colorScheme16,
            floatValue98,
            g,
            floatValue97,
            i,
            floatValue100,
            floatValue99,
            0.0F,
            0.48F,
            3L,
            clickGuiState11.getFloatValue(),
            clickGuiState11.getFloatValue2(),
            CoreDiagnosticsPanelRenderer::invoke7
         );
      }
   }

   private void invoke14(
      RenderManager renderManager17,
      ClickGuiState clickGuiState12,
      Metrics metrics17,
      FontObject fontObject,
      float f,
      float g,
      float h,
      float i,
      String string,
      int j,
      float k
   ) {
      String text2 = resolve4(string);
      float floatValue101 = measure28(f);
      float floatValue102 = measure28(g);
      float floatValue103 = Math.max(metrics17.measure(8.0F), measure28(k));
      float floatValue104 = measure28(h);
      float floatValue105 = ClickGuiRenderUtils.measure2(metrics17, fontObject, text2, i);
      float floatValue106 = Math.max(0.0F, floatValue105 - floatValue103 + metrics17.measure(8.0F));
      float floatValue107 = 0.0F;
      if (floatValue106 > metrics17.measure(1.0F) && ClickGuiRenderUtils.check2(clickGuiState12.getFloatValue(), clickGuiState12.getFloatValue2(), floatValue101, floatValue102, floatValue103, floatValue104)) {
         float floatValue108 = (float)(System.currentTimeMillis() % 2600L) / 2600.0F;
         float floatValue109 = floatValue108 < 0.5F ? floatValue108 * 2.0F : 2.0F - floatValue108 * 2.0F;
         floatValue107 = measure28(floatValue106 * floatValue109);
      }

      renderManager17.invoke20();
      renderManager17.invoke24(floatValue101, floatValue102, floatValue103, floatValue104, 0.0F, 0.0F, 0.0F, 0.0F);
      boolean flag8 = false ;

      try {
         flag8 = true;
         RenderStateMaintenance.invoke();
         ClickGuiRenderUtils.invoke4(renderManager17, metrics17, fontObject, floatValue101 - floatValue107, floatValue102, floatValue104, i, text2, j);
         flag8 = false;
      } finally {
         if (flag8) {
            RenderStateMaintenance.invoke2();
            renderManager17.invoke20();
            renderManager17.invoke25();
         }
      }

      RenderStateMaintenance.invoke2();
      renderManager17.invoke20();
      renderManager17.invoke25();
   }

   private void invoke15(RenderManager renderManager18, Metrics metrics18, float f, float g, int i, int j, ColorScheme colorScheme17) {
      float floatValue110 = metrics18.measure(1.0F);
      int intValue23 = ColorScheme.compute6(j, 216);
      renderManager18.invoke28(
         f - 5.8F * floatValue110,
         g - 5.8F * floatValue110,
         11.6F * floatValue110,
         11.6F * floatValue110,
         3.5F * floatValue110,
         ColorScheme.compute6(intValue23, 132),
         Math.max(0.65F, metrics18.measure(0.65F))
      );
      if (i % 3 == 0) {
         renderManager18.invoke5(f - 3.8F * floatValue110, g + 2.0F * floatValue110, 7.6F * floatValue110, 1.5F * floatValue110, 0.75F * floatValue110, intValue23);
         renderManager18.invoke5(f - 1.0F * floatValue110, g - 4.2F * floatValue110, 2.0F * floatValue110, 8.0F * floatValue110, 1.0F * floatValue110, ColorScheme.compute6(intValue23, 196));
      } else if (i % 3 == 1) {
         renderManager18.invoke5(f - 4.0F * floatValue110, g - 2.7F * floatValue110, 8.0F * floatValue110, 1.5F * floatValue110, 0.75F * floatValue110, intValue23);
         renderManager18.invoke5(f - 4.0F * floatValue110, g + 1.4F * floatValue110, 8.0F * floatValue110, 1.5F * floatValue110, 0.75F * floatValue110, ColorScheme.compute6(intValue23, 186));
      } else {
         renderManager18.invoke39(f - 3.2F * floatValue110, g, 1.7F * floatValue110, 0.0F, 1.0F, intValue23);
         renderManager18.invoke39(f + 3.2F * floatValue110, g, 1.7F * floatValue110, 0.0F, 1.0F, ColorScheme.compute6(intValue23, 188));
         renderManager18.invoke5(
            f - 1.6F * floatValue110, g - 0.7F * floatValue110, 3.2F * floatValue110, 1.4F * floatValue110, 0.7F * floatValue110, ColorScheme.compute6(ClickGuiRenderUtils.compute2(colorScheme17), 156)
         );
      }
   }

   private void invoke16(RenderManager renderManager19, Metrics metrics19, float f, float g, int i, int j, ColorScheme colorScheme18) {
      float floatValue111 = metrics19.measure(1.0F);
      int intValue24 = ColorScheme.compute6(i == 2 ? ClickGuiRenderUtils.compute2(colorScheme18) : j, 234);
      if (i == 0) {
         renderManager19.invoke28(
            f - 6.0F * floatValue111,
            g - 5.5F * floatValue111,
            12.0F * floatValue111,
            11.0F * floatValue111,
            3.0F * floatValue111,
            ColorScheme.compute6(intValue24, 118),
            Math.max(0.6F, metrics19.measure(0.6F))
         );
         renderManager19.invoke5(f - 3.6F * floatValue111, g + 1.6F * floatValue111, 1.4F * floatValue111, 3.0F * floatValue111, 0.7F * floatValue111, intValue24);
         renderManager19.invoke5(f - 0.7F * floatValue111, g - 1.6F * floatValue111, 1.4F * floatValue111, 6.2F * floatValue111, 0.7F * floatValue111, intValue24);
         renderManager19.invoke5(f + 2.2F * floatValue111, g - 4.0F * floatValue111, 1.4F * floatValue111, 8.6F * floatValue111, 0.7F * floatValue111, intValue24);
      } else if (i == 1) {
         renderManager19.invoke5(f - 5.2F * floatValue111, g - 4.0F * floatValue111, 10.4F * floatValue111, 1.4F * floatValue111, 0.7F * floatValue111, intValue24);
         renderManager19.invoke5(f - 5.2F * floatValue111, g - 0.5F * floatValue111, 10.4F * floatValue111, 1.4F * floatValue111, 0.7F * floatValue111, intValue24);
         renderManager19.invoke5(f - 5.2F * floatValue111, g + 3.0F * floatValue111, 7.4F * floatValue111, 1.4F * floatValue111, 0.7F * floatValue111, ColorScheme.compute6(intValue24, 188));
      } else {
         renderManager19.invoke56(f, g);
         renderManager19.invoke54(45.0F);

         try {
            renderManager19.invoke5(-4.9F * floatValue111, -0.8F * floatValue111, 9.8F * floatValue111, 1.6F * floatValue111, 0.8F * floatValue111, intValue24);
            renderManager19.invoke5(-0.8F * floatValue111, -4.9F * floatValue111, 1.6F * floatValue111, 9.8F * floatValue111, 0.8F * floatValue111, intValue24);
         } finally {
            renderManager19.invoke55();
            renderManager19.invoke57();
         }
      }
   }

   public static boolean check(ClickGuiGeometry clickGuiGeometry3, Metrics metrics20, float f, float g, double d) {
      if (!check2(clickGuiGeometry3, metrics20, f, g)) {
         return false;
      } else {
         TYPE_UTILS.invoke2(d);
         return true;
      }
   }

   public static boolean check2(ClickGuiGeometry clickGuiGeometry4, Metrics metrics21, float f, float g) {
      float floatValue112 = measure5(clickGuiGeometry4, metrics21);
      float floatValue113 = measure6(clickGuiGeometry4, metrics21);
      float floatValue114 = measure8(clickGuiGeometry4, metrics21);
      float floatValue115 = measure18(metrics21, floatValue114);
      return ClickGuiRenderUtils.check2(
         f,
         g,
         measure19(floatValue112, metrics21),
         measure20(floatValue113, metrics21, floatValue115),
         measure21(measure7(metrics21), metrics21),
         measure22(floatValue113, floatValue114, metrics21, floatValue115)
      );
   }

   public static float measure5(ClickGuiGeometry clickGuiGeometry5, Metrics metrics22) {
      return measure28(clickGuiGeometry5.getFloatValue11() + metrics22.measure(18.0F));
   }

   public static float measure6(ClickGuiGeometry clickGuiGeometry6, Metrics metrics23) {
      return measure28(clickGuiGeometry6.getFloatValue12() + metrics23.measure(18.0F));
   }

   private static float measure7(Metrics metrics24) {
      return measure28(
         measure27(metrics24.measure(330.0F), metrics24.measure(292.0F), Math.max(metrics24.measure(306.0F), metrics24.getFloatValue8() * 0.48F))
      );
   }

   public static float measure8(ClickGuiGeometry clickGuiGeometry7, Metrics metrics25) {
      float floatValue116 = measure6(clickGuiGeometry7, metrics25);
      return Math.max(metrics25.measure(352.0F), measure28(clickGuiGeometry7.getFloatValue12() + clickGuiGeometry7.getFloatValue14()) - floatValue116);
   }

   public float measure9(Metrics metrics26) {
      return measure7(metrics26);
   }

   public float measure10(ClickGuiGeometry clickGuiGeometry8, Metrics metrics27) {
      return measure28(measure5(clickGuiGeometry8, metrics27) + metrics27.measure(18.0F));
   }

   public float measure11(ClickGuiGeometry clickGuiGeometry9, Metrics metrics28) {
      return measure28(this.measure15(clickGuiGeometry9, metrics28) - metrics28.measure(10.0F) - this.measure13(metrics28));
   }

   public float measure12(Metrics metrics29) {
      return measure28(measure7(metrics29) - metrics29.measure(36.0F));
   }

   public float measure13(Metrics metrics30) {
      return measure28(metrics30.measure(32.0F));
   }

   public float measure14(ClickGuiGeometry clickGuiGeometry10, Metrics metrics31) {
      return measure28(measure5(clickGuiGeometry10, metrics31) + metrics31.measure(18.0F));
   }

   public float measure15(ClickGuiGeometry clickGuiGeometry11, Metrics metrics32) {
      return measure28(
         measure6(clickGuiGeometry11, metrics32) + measure8(clickGuiGeometry11, metrics32) - metrics32.measure(18.0F) - this.measure17(metrics32)
      );
   }

   public float measure16(Metrics metrics33) {
      return measure28(measure7(metrics33) - metrics33.measure(36.0F));
   }

   public float measure17(Metrics metrics34) {
      return measure28(metrics34.measure(32.0F));
   }

   private static float measure18(Metrics metrics35, float f) {
      float floatValue117 = metrics35.measure(74.0F);
      float floatValue118 = f - metrics35.measure(36.0F) - floatValue117 - metrics35.measure(150.0F);
      return measure28(
         measure27(f * 0.27F, metrics35.measure(108.0F), Math.max(metrics35.measure(108.0F), Math.min(metrics35.measure(126.0F), floatValue118)))
      );
   }

   private static float measure19(float f, Metrics metrics36) {
      return measure28(f + metrics36.measure(18.0F));
   }

   private static float measure20(float f, Metrics metrics37, float g) {
      return measure28(f + metrics37.measure(18.0F) + g + metrics37.measure(8.0F));
   }

   private static float measure21(float f, Metrics metrics38) {
      return measure28(f - metrics38.measure(36.0F));
   }

   private static float measure22(float f, float g, Metrics metrics39, float h) {
      float floatValue119 = measure20(f, metrics39, h);
      float floatValue120 = measure28(f + g - metrics39.measure(18.0F) - metrics39.measure(74.0F) - metrics39.measure(12.0F));
      return Math.max(metrics39.measure(80.0F), floatValue120 - floatValue119);
   }

   private static float measure23(Metrics metrics40) {
      byte byteValue = 9;
      float floatValue121 = metrics40.measure(byteValue * 34.0F + (byteValue - 1) * 7.0F);
      return measure28(metrics40.measure(132.0F) + floatValue121);
   }

   private void invoke17() {
      int intValue25 = compute();
      if (intValue25 != this.intValue) {
         this.intValue = intValue25;
         this.text0 = Integer.toString(intValue25);
      }

      int intValue26 = this.compute2();
      if (intValue26 != this.intValue2) {
         this.intValue2 = intValue26;
         this.text02 = Integer.toString(intValue26);
      }

      String text3 = resolve4(this.renderDiagnosticsStatus.matrixFinite);
      if (!text3.equals(this.text)) {
         this.text = text3;
         this.corrupted = check3(text3, "finite") ? "OK" : "CORRUPTED";
      }
   }

   private static int compute() {
      try {
         return Profile.getUid();
      } catch (Throwable exception) {
         return 0;
      }
   }

   private int compute2() {
      try {
         if (WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null) {
            ArrayList arrayList = WildClient.INSTANCE.moduleManager.getModules();
            int intValue27 = 0;

            for (int intValue28 = 0; intValue28 < arrayList.size(); intValue28++) {
               if (((Module)arrayList.get(intValue28)).enabled) {
                  intValue27++;
               }
            }

            return intValue27;
         } else {
            return 0;
         }
      } catch (Throwable exception2) {
         return 0;
      }
   }

   private String resolve() {
      try {
         MinecraftClient client = MinecraftClient.getInstance();
         if (client != null && client.getSession() != null) {
            return client.getSession().getUsername();
         }
      } catch (Throwable exception3) {
      }

      return "Player";
   }

   private String getCorrupted() {
      return this.corrupted;
   }

   private String resolve2() {
      return this.renderDiagnosticsStatus.intValue == 0 ? "Изолированы [TextureUnitGuard]" : resolve4(this.renderDiagnosticsStatus.glClean);
   }

   private int compute3(ColorScheme colorScheme19) {
      return "OK".equals(this.corrupted) ? colorScheme19.getIntValue14() : colorScheme19.compute2();
   }

   private static boolean check3(String string, String string2) {
      int intValue29 = string.length() - string2.length();

      for (int intValue30 = 0; intValue30 <= intValue29; intValue30++) {
         if (string.regionMatches(true, intValue30, string2, 0, string2.length())) {
            return true;
         }
      }

      return false;
   }

   private static String resolve3(String string) {
      if (string != null && !string.isBlank()) {
         return string.length() <= 26 ? string : string.substring(0, 23) + "...";
      } else {
         return "unknown";
      }
   }

   private static String resolve4(String string) {
      return string != null && !string.isBlank() ? string : "none";
   }

   private static float measure24(float f, float g, float h) {
      return measure26((f - g) / Math.max(0.001F, h - g));
   }

   private static float measure25(float f) {
      float floatValue122 = measure26(f);
      return floatValue122 * floatValue122 * (3.0F - 2.0F * floatValue122);
   }

   private static float measure26(float f) {
      return f < 0.0F ? 0.0F : Math.min(f, 1.0F);
   }

   private static float measure27(float f, float g, float h) {
      return Math.max(g, Math.min(h, f));
   }

   private static float measure28(float f) {
      return Math.round(f);
   }

   private static float measure29(float f, float g) {
      return Math.max(0.0F, measure28(f + g) - measure28(f));
   }
}
