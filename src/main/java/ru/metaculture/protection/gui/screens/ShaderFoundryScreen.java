package ru.metaculture.protection;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Util;
import org.json.JSONObject;
import org.lwjgl.glfw.GLFW;

public final class ShaderFoundryScreen implements AutoCloseable {
   private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ROOT);
   private static final String[] SAVE_AS = new String[]{"Save As", "Export .wifd", "Import", "Open Folder", "Cleanup Legacy", "Reset"};
   private static final String[] ALL = new String[]{"All", "Мои", "Пресеты"};
   private static final int INT_VALUE = 0;
   private static final int INT_VALUE_2 = 1;
   private final ShaderNodeRegistry shaderNodeRegistry = new ShaderNodeRegistry();
   private final ShaderSourceBuilder shaderSourceBuilder = new ShaderSourceBuilder(this.shaderNodeRegistry);
   private final NamedShaderProgram namedShaderProgram = new NamedShaderProgram(this.shaderSourceBuilder);
   private final ShaderNodeBrowser shaderNodeBrowser = new ShaderNodeBrowser(this.shaderNodeRegistry);
   private final ShaderNodePreviewRenderer shaderNodePreviewRenderer = new ShaderNodePreviewRenderer(this.shaderNodeRegistry, this.shaderSourceBuilder);
   private final ShaderLibraryBrowser shaderLibraryBrowser = new ShaderLibraryBrowser();
   private ShaderNode shaderNode = ShaderGraphFactory.resolve(this.shaderNodeRegistry);
   private float floatValue = 520.0F;
   private float floatValue2 = 260.0F;
   private float floatValue3 = 0.92F;
   private float floatValue4 = 0.92F;
   private final SpringAnimation springAnimation = new SpringAnimation(0.92F);
   private boolean flag;
   private float floatValue5;
   private float floatValue6;
   private float floatValue7;
   private float floatValue8;
   private float floatValue9;
   private float floatValue10;
   private long timestamp;
   private String text;
   private float floatValue11;
   private float floatValue12;
   private String text2;
   private final Set<String> values = new LinkedHashSet<>();
   private final Set<String> values2 = new LinkedHashSet<>();
   private final Map<String, ShaderFoundryScreen.ShaderFoundryScreenData> valuesByKey = new HashMap<>();
   private float floatValue13;
   private float floatValue14;
   private boolean flag2;
   private float floatValue15;
   private float floatValue16;
   private float floatValue17;
   private float floatValue18;
   private String text3;
   private String text4;
   private float floatValue19;
   private float floatValue20;
   private float floatValue21;
   private float floatValue22;
   private long timestamp2;
   private String ready = "ready";
   private long timestamp3;
   private ShaderSurface shaderSurface = ShaderSurface.HUD;
   private boolean flag3;
   private boolean flag4;
   private boolean flag5;
   private ShaderSurface shaderSurface2;
   private boolean flag6;
   private String hostRectangle = "Host Rectangle";
   private ShaderFoundryScreen.ShaderFoundryScreenState shaderFoundryScreenState = ShaderFoundryScreen.ShaderFoundryScreenState.AUTO;
   private int intValue;
   private final Map<Integer, ShaderBuildResult> valuesByKey2 = new HashMap<>();
   private boolean flag7;
   private final ClampedSpringAnimation clampedSpringAnimation = new ClampedSpringAnimation(
      AnimationSystem.getINSTANCE(), SpringConfig.resolve(2.7F, 0.86F), 0.0F, 0.0F, 1.0F, 0.001F, 0.001F
   );
   private final ClampedSpringAnimation clampedSpringAnimation2 = new ClampedSpringAnimation(
      AnimationSystem.getINSTANCE(), SpringConfig.resolve(2.4F, 0.78F), 0.0F, 0.0F, 1.0F, 0.001F, 0.001F
   );
   private final ClampedSpringAnimation clampedSpringAnimation3 = new ClampedSpringAnimation(
      AnimationSystem.getINSTANCE(), SpringConfig.resolve(2.6F, 0.84F), 0.0F, 0.0F, 1.0F, 0.001F, 0.001F
   );
   private final ClampedSpringAnimation clampedSpringAnimation4 = new ClampedSpringAnimation(
      AnimationSystem.getINSTANCE(), SpringConfig.resolve(3.0F, 0.88F), 0.0F, 0.0F, 1.0F, 0.001F, 0.001F
   );
   private final ClampedSpringAnimation clampedSpringAnimation5 = new ClampedSpringAnimation(
      AnimationSystem.getINSTANCE(), SpringConfig.resolve(2.6F, 0.82F), 0.0F, 0.0F, 1.0F, 0.001F, 0.001F
   );
   private boolean flag8;
   private float floatValue23;
   private boolean flag9;
   private float floatValue24;
   private int intValue2;
   private String text5 = "";
   private boolean flag10;
   private long timestamp4;
   private final Set<String> values3 = new LinkedHashSet<>();
   private long timestamp5;
   private String text6;
   private int intValue3 = -1;
   private int intValue4 = -1;
   private long timestamp6;
   private final ShaderEditHistory shaderEditHistory = new ShaderEditHistory();
   private String text7 = ShaderStylePreset.resolve();
   private boolean flag11;
   private long timestamp7;
   private final Map<String, ShaderUniformEditor> valuesByKey3 = new HashMap<>();
   private final Map<String, ShaderUniformEditor> valuesByKey4 = new HashMap<>();
   private final Map<String, ShaderUniformEditor> valuesByKey5 = new HashMap<>();
   private String text8;
   private String text9;
   private String text10;
   private String text11;
   private String text12;
   private final Map<String, SpringAnimation> valuesByKey6 = new HashMap<>();
   private final Map<String, SpringAnimation> valuesByKey7 = new HashMap<>();
   private final Map<String, ClampedSpringAnimation> valuesByKey8 = new HashMap<>();
   private final Map<String, Boolean> valuesByKey9 = new LinkedHashMap<>(16, 0.75F, true);
   private final Map<String, ClampedSpringAnimation> valuesByKey10 = new HashMap<>();
   private final Map<String, SpringAnimation> valuesByKey11 = new HashMap<>();
   private final SpringAnimation springAnimation2 = new SpringAnimation(0.0F);
   private float floatValue25;
   private float floatValue26;
   private float floatValue27;
   private float floatValue28;
   private float floatValue29;
   private float floatValue30;
   private boolean flag12;
   private boolean flag13;
   private float floatValue31;
   private float floatValue32;
   private boolean flag14;
   private float floatValue33;
   private float floatValue34;
   private Metrics metrics;
   private int intValue5;
   private int intValue6;

   public ShaderFoundryScreen() {
      ShaderEffectManager.getINSTANCE().invoke(this.shaderNodeRegistry);
      this.invoke94(this.shaderSurface);
      this.shaderNode.getShaderTemplate().invoke5(this.text7, ShaderPresetStore.resolve19());
      this.invoke95();
      this.text7 = this.shaderNode.getShaderTemplate().getText();
      this.intValue4 = this.shaderNode.getIntValue();
      this.namedShaderProgram.invoke(this.shaderSurface);
      ShaderPresetStore.getINSTANCE().invoke(this.shaderNodeRegistry);
      BuiltInShaderPresets.invoke(this.shaderNodeRegistry, this.shaderSourceBuilder);
   }

   public ShaderNodeRegistry getShaderNodeRegistry() {
      return this.shaderNodeRegistry;
   }

   public ShaderNode getShaderNode() {
      return this.shaderNode;
   }

   public ShaderSurface getShaderSurface() {
      return this.shaderSurface;
   }

   public boolean check(ClickGuiState clickGuiState) {
      return clickGuiState != null && (clickGuiState.isFlag24() || clickGuiState.measure7(AnimationKeyRegistry.resolve56()) > 0.035F);
   }

   public boolean check2() {
      return this.shaderLibraryBrowser.isFlag() || this.shaderNodeBrowser.isFlag() || this.flag6;
   }

   public boolean check3(ClickGuiState clickGuiState2) {
      return clickGuiState2 != null && (clickGuiState2.isFlag24() || clickGuiState2.measure7(AnimationKeyRegistry.resolve56()) > 0.0015F);
   }

   public void invoke(RenderManager renderManager, ClickGuiState clickGuiState3, ThemeContext themeContext, int i, int j) {
      if (renderManager != null && clickGuiState3 != null && themeContext != null && i > 0 && j > 0) {
         float floatValue = clickGuiState3.measure7(AnimationKeyRegistry.resolve56());
         if (!(floatValue <= 0.0015F)) {
            this.invoke97(clickGuiState3.getFloatValue(), clickGuiState3.getFloatValue2());
            this.invoke74();
            this.floatValue3 = this.springAnimation.measure(this.floatValue4, SpringSpec.resolve8());
            this.invoke73();
            ColorScheme colorScheme = themeContext.getColorScheme();
            this.metrics = themeContext.getMetrics();
            this.intValue5 = i;
            this.intValue6 = j;
            boolean flag = clickGuiState3.isFlag24();
            float floatValue2 = measure20(floatValue);
            float floatValue3 = this.measure(floatValue);
            float floatValue4 = this.measure2(floatValue, flag);
            float floatValue5 = this.measure3(floatValue, flag, j);
            float floatValue6 = clickGuiState3.measure8(AnimationKeyRegistry.resolve56());
            float floatValue7 = (float)(System.currentTimeMillis() % 12000L) / 12000.0F;
            this.clampedSpringAnimation.invoke2(this.flag3 ? 1.0F : 0.0F);
            this.clampedSpringAnimation2.invoke2(this.flag4 ? 1.0F : 0.0F);
            this.clampedSpringAnimation3.invoke2(this.flag5 ? 1.0F : 0.0F);
            this.clampedSpringAnimation4.invoke2(this.flag6 ? 1.0F : 0.0F);
            this.clampedSpringAnimation5.invoke2(this.flag9 ? 1.0F : 0.0F);
            renderManager.invoke20();
            this.invoke3(renderManager, clickGuiState3, themeContext, colorScheme, i, j, floatValue2, floatValue7, floatValue, flag);
            this.invoke4(renderManager, themeContext.getMetrics(), colorScheme, i, j, floatValue, flag, floatValue6, floatValue7);
            renderManager.invoke65(floatValue3);
            renderManager.invoke56(0.0F, floatValue5);
            renderManager.invoke62(floatValue4, i * 0.5F, j * 0.5F);

            try {
               this.invoke75(renderManager, clickGuiState3, colorScheme, i, j, floatValue7);
               this.invoke67(renderManager, colorScheme, i, j, floatValue2);
               this.invoke57(renderManager, clickGuiState3, themeContext);
               this.invoke68(renderManager, clickGuiState3, colorScheme, i, j, floatValue2);
               this.invoke76(renderManager, themeContext, colorScheme);
               this.invoke44(renderManager, clickGuiState3, themeContext, i, j);
               this.invoke27(renderManager, clickGuiState3, themeContext, i);
               this.invoke48(renderManager, clickGuiState3, themeContext, i, j, floatValue2);
               this.invoke49(renderManager, clickGuiState3, themeContext, i, j);
               this.invoke34(renderManager, clickGuiState3, themeContext, i, j);
               this.invoke35(renderManager, clickGuiState3, themeContext, i, j);
               this.invoke37(renderManager, clickGuiState3, themeContext, i, j);
               this.invoke38(renderManager, clickGuiState3, themeContext, i, j);
               this.invoke10(renderManager, clickGuiState3, themeContext, i, j);
               this.shaderNodeBrowser.invoke10(renderManager, themeContext, clickGuiState3, i, j);
               this.invoke77(renderManager, themeContext, i, j);
               this.shaderLibraryBrowser.invoke3(renderManager, themeContext.getMetrics(), colorScheme, clickGuiState3.getFloatValue(), clickGuiState3.getFloatValue2(), i, j);
            } finally {
               renderManager.invoke57();
               renderManager.invoke57();
               renderManager.invoke66();
            }

            this.invoke5(renderManager, themeContext.getMetrics(), colorScheme, i, j, floatValue, flag, floatValue6, floatValue7);
            this.invoke93();
            boolean flag2 = flag && floatValue3 > 0.72F;
            if (flag2 && System.currentTimeMillis() - this.timestamp5 > 130L) {
               this.invoke96();
               this.namedShaderProgram.invoke(this.shaderSurface);
               this.namedShaderProgram.invoke2(this.shaderNode);
               this.timestamp5 = System.currentTimeMillis();
            }

            boolean flag3 = this.text == null && this.text3 == null && !this.flag2 && !this.flag;
            if (flag2 && flag3 && this.shaderNode.getIntValue() != this.intValue3 && System.currentTimeMillis() - this.timestamp6 > 1800L) {
               this.intValue3 = this.shaderNode.getIntValue();
               this.timestamp6 = System.currentTimeMillis();
               this.invoke96();
               SavedShaderPreset savedShaderPreset = ShaderPresetStore.getINSTANCE().resolve5(this.shaderSurface, this.shaderNode, this.text6);
               if (savedShaderPreset != null) {
                  this.text6 = savedShaderPreset.getText();
                  this.invoke30(savedShaderPreset.getText2(), this.shaderNode);
               }
            }
         }
      }
   }

   private void invoke2() {
      this.intValue3 = this.shaderNode.getIntValue();
      this.timestamp6 = System.currentTimeMillis();
   }

   private String resolve() {
      return resolve5(this.text7);
   }

   private String resolve2() {
      String text = this.namedShaderProgram.resolve2();
      if (text != null && !text.isBlank()) {
         return "failed";
      } else {
         return this.shaderNode.getIntValue() != this.intValue4 ? "dirty" : "saved";
      }
   }

   private void invoke3(
      RenderManager renderManager2,
      ClickGuiState clickGuiState4,
      ThemeContext themeContext2,
      ColorScheme colorScheme2,
      int i,
      int j,
      float f,
      float g,
      float h,
      boolean bl
   ) {
      float floatValue8 = this.measure4(h, bl);
      if (MenuVisualPreferences.check()) {
         renderManager2.invoke48(26.0F + 22.0F * floatValue8);
         renderManager2.invoke44(
            0.0F, 0.0F, (float)i, (float)j, 0.0F, this.check4(colorScheme2) ? 0.42F + 0.2F * f + 0.22F * floatValue8 : 0.66F + 0.16F * f + 0.16F * floatValue8
         );
      }

      renderManager2.invoke4(0.0F, 0.0F, (float)i, (float)j, this.compute(themeContext2, f));
   }

   private float measure(float f) {
      float floatValue9 = measure20(measure21(f, 0.0F, 1.0F));
      return floatValue9 * measure20(measure21((f - 0.006F) / 0.64F, 0.0F, 1.0F));
   }

   private float measure2(float f, boolean bl) {
      float floatValue10 = measure20(measure21(f, 0.0F, 1.0F));
      float floatValue11 = (float)Math.sin(Math.PI * measure21(bl ? f : 1.0F - f, 0.0F, 1.0F));
      return bl ? 0.952F + 0.048F * floatValue10 + 0.01F * floatValue11 * (1.0F - floatValue10) : 0.97F + 0.03F * floatValue10 - 0.01F * floatValue11;
   }

   private float measure3(float f, boolean bl, int i) {
      float floatValue12 = measure20(measure21(f, 0.0F, 1.0F));
      float floatValue13 = Math.max(18.0F, i * 0.032F);
      return bl ? floatValue13 * (1.0F - floatValue12) : -floatValue13 * (1.0F - floatValue12);
   }

   private float measure4(float f, boolean bl) {
      float floatValue14 = measure20(measure21(f, 0.0F, 1.0F));
      return bl ? 1.0F - floatValue14 : (1.0F - floatValue14) * 0.96F;
   }

   private void invoke4(RenderManager renderManager3, Metrics metrics, ColorScheme colorScheme3, int i, int j, float f, boolean bl, float g, float h) {
      float floatValue15 = measure21(bl ? f : 1.0F - f, 0.0F, 1.0F);
      float floatValue16 = (float)Math.sin(Math.PI * floatValue15);
      float floatValue17 = this.measure4(f, bl);
      float floatValue18 = measure21(floatValue16 * 0.52F + Math.abs(g) * 0.35F + floatValue17 * 0.18F, 0.0F, 1.0F);
      if (!bl) {
         float floatValue19 = measure20(1.0F - measure21(f, 0.0F, 1.0F));
         float floatValue20 = measure21(floatValue19 * 0.42F + Math.abs(g) * 0.1F, 0.0F, 1.0F);
         int intValue = this.check4(colorScheme3)
            ? ColorScheme.compute5(244, 247, 255, Math.round(54.0F * floatValue20))
            : ColorScheme.compute5(0, 0, 0, Math.round(86.0F * floatValue20));
         int intValue2 = ColorScheme.compute6(colorScheme3.getIntValue15(), Math.round(14.0F * floatValue18 * (1.0F - floatValue19 * 0.35F)));
         renderManager3.invoke5(0.0F, 0.0F, (float)i, (float)j, 0.0F, intValue);
         renderManager3.invoke5(0.0F, 0.0F, (float)i, (float)j, 0.0F, intValue2);
         float floatValue21 = Math.max(metrics.measure(7.0F), j * 0.01F);
         int intValue3 = this.check4(colorScheme3)
            ? ColorScheme.compute5(18, 24, 40, Math.round(8.0F * floatValue20))
            : ColorScheme.compute5(0, 0, 0, Math.round(18.0F * floatValue20));
         renderManager3.invoke5(0.0F, 0.0F, (float)i, floatValue21, 0.0F, intValue3);
         renderManager3.invoke5(0.0F, j - floatValue21, (float)i, floatValue21, 0.0F, intValue3);
      } else {
         int intValue4 = ColorScheme.compute6(colorScheme3.getIntValue14(), Math.round((this.check4(colorScheme3) ? 34 : 46) * floatValue18));
         int intValue5 = ColorScheme.compute6(colorScheme3.getIntValue15(), Math.round((this.check4(colorScheme3) ? 22 : 38) * floatValue18));
         renderManager3.invoke4(0.0F, 0.0F, (float)i, (float)j, intValue4);
         float floatValue22 = measure20(measure21(bl ? f * 1.14F : f, 0.0F, 1.0F));
         float floatValue23 = (1.0F - floatValue22) * j * 0.28F;
         if (floatValue23 > 0.6F) {
            int intValue6 = this.check4(colorScheme3)
               ? ColorScheme.compute5(244, 248, 255, Math.round(118.0F * (1.0F - floatValue22)))
               : ColorScheme.compute5(0, 0, 0, Math.round(150.0F * (1.0F - floatValue22)));
            renderManager3.invoke5(0.0F, 0.0F, (float)i, floatValue23, 0.0F, intValue6);
            renderManager3.invoke5(0.0F, j - floatValue23, (float)i, floatValue23, 0.0F, intValue6);
            renderManager3.invoke5(
               0.0F,
               floatValue23 - metrics.measure(1.0F),
               (float)i,
               metrics.measure(1.0F),
               0.0F,
               ColorScheme.compute6(colorScheme3.getIntValue14(), Math.round(120.0F * (1.0F - floatValue22)))
            );
            renderManager3.invoke5(
               0.0F,
               j - floatValue23,
               (float)i,
               metrics.measure(1.0F),
               0.0F,
               ColorScheme.compute6(colorScheme3.getIntValue15(), Math.round(120.0F * (1.0F - floatValue22)))
            );
         }

         float floatValue24 = bl ? floatValue15 : 1.0F - floatValue15;

         for (int intValue7 = 0; intValue7 < 5; intValue7++) {
            float floatValue25 = measure10(floatValue24 * 1.18F + intValue7 * 0.17F + h * 0.045F);
            float floatValue26 = -i * 0.28F + floatValue25 * i * 1.58F;
            float floatValue27 = metrics.measure(42 + intValue7 * 9) * (0.72F + floatValue18);
            float floatValue28 = floatValue18 * (0.62F - intValue7 * 0.075F);
            renderManager3.invoke56(floatValue26, j * (0.42F + intValue7 * 0.035F));
            renderManager3.invoke54(-18.0F);
            renderManager3.invoke5(
               -floatValue27 * 0.5F,
               (float)(-j),
               floatValue27,
               j * 2.1F,
               floatValue27 * 0.5F,
               ColorScheme.compute6(intValue7 % 2 == 0 ? colorScheme3.getIntValue14() : colorScheme3.getIntValue15(), Math.round(52.0F * floatValue28))
            );
            renderManager3.invoke5(
               -floatValue27 * 0.08F,
               (float)(-j),
               floatValue27 * 0.16F,
               j * 2.1F,
               floatValue27 * 0.08F,
               ColorScheme.compute6(colorScheme3.getIntValue13(), Math.round(18.0F * floatValue28))
            );
            renderManager3.invoke55();
            renderManager3.invoke57();
         }

         float floatValue29 = measure21(0.18F + floatValue17 * 0.62F + floatValue18 * 0.22F, 0.0F, 1.0F);
         int intValue8 = this.check4(colorScheme3)
            ? ColorScheme.compute5(18, 24, 40, Math.round(24.0F * floatValue29))
            : ColorScheme.compute5(0, 0, 0, Math.round(78.0F * floatValue29));
         float floatValue30 = Math.max(metrics.measure(42.0F), i * 0.035F);
         float floatValue31 = Math.max(metrics.measure(36.0F), j * 0.045F);
         renderManager3.invoke5(0.0F, 0.0F, (float)i, floatValue31, 0.0F, intValue8);
         renderManager3.invoke5(0.0F, j - floatValue31, (float)i, floatValue31, 0.0F, intValue8);
         renderManager3.invoke5(0.0F, 0.0F, floatValue30, (float)j, 0.0F, intValue8);
         renderManager3.invoke5(i - floatValue30, 0.0F, floatValue30, (float)j, 0.0F, intValue8);
         renderManager3.invoke4(0.0F, 0.0F, (float)i, (float)j, intValue5);
      }
   }

   private void invoke5(RenderManager renderManager4, Metrics metrics2, ColorScheme colorScheme4, int i, int j, float f, boolean bl, float g, float h) {
      if (bl) {
         float floatValue32 = measure21(bl ? f : 1.0F - f, 0.0F, 1.0F);
         float floatValue33 = (float)Math.sin(Math.PI * floatValue32);
         floatValue33 = measure21(floatValue33 * 0.34F + Math.abs(g) * 0.22F, 0.0F, 1.0F);
         if (!(floatValue33 <= 0.015F)) {
            float floatValue34 = j * measure10(floatValue32 * 0.85F + h * 0.18F);
            renderManager4.invoke5(
               0.0F,
               floatValue34 - metrics2.measure(1.2F),
               (float)i,
               metrics2.measure(2.4F),
               metrics2.measure(1.2F),
               ColorScheme.compute6(colorScheme4.getIntValue13(), Math.round((this.check4(colorScheme4) ? 34 : 48) * floatValue33))
            );
            renderManager4.invoke5(
               0.0F,
               floatValue34 + metrics2.measure(3.5F),
               (float)i,
               metrics2.measure(1.0F),
               metrics2.measure(0.5F),
               ColorScheme.compute6(colorScheme4.getIntValue15(), Math.round(78.0F * floatValue33))
            );
            float floatValue35 = i * (0.18F + 0.16F * floatValue33);
            float floatValue36 = i * measure10(floatValue32 * 1.25F + 0.18F);
            renderManager4.invoke56(floatValue36, j * 0.5F);
            renderManager4.invoke54(12.0F);
            renderManager4.invoke41(
               -floatValue35 * 0.5F,
               -j * 0.62F,
               floatValue35,
               j * 1.24F,
               floatValue35 * 0.18F,
               metrics2.measure(34.0F) * floatValue33,
               metrics2.measure(4.0F),
               ColorScheme.compute6(colorScheme4.getIntValue14(), Math.round(58.0F * floatValue33))
            );
            renderManager4.invoke5(
               -floatValue35 * 0.5F, -j * 0.62F, floatValue35, j * 1.24F, floatValue35 * 0.18F, ColorScheme.compute6(colorScheme4.getIntValue14(), Math.round(18.0F * floatValue33))
            );
            renderManager4.invoke55();
            renderManager4.invoke57();
         }
      }
   }

   private int compute(ThemeContext themeContext3, float f) {
      ColorScheme colorScheme5 = themeContext3.getColorScheme();
      return this.check4(colorScheme5)
         ? ColorScheme.compute7(
            ColorScheme.compute5(246, 248, 252, Math.round(214.0F * f)), ColorScheme.compute6(colorScheme5.getIntValue14(), Math.round(56.0F * f)), 0.08F
         )
         : ColorScheme.compute5(2, 4, 8, Math.round(240.0F * f));
   }

   private int compute2(ColorScheme colorScheme6, int i) {
      return this.check4(colorScheme6)
         ? ColorScheme.compute7(ColorScheme.compute5(255, 255, 255, Math.min(255, i + 8)), ColorScheme.compute6(colorScheme6.getIntValue14(), i), 0.038F)
         : ColorScheme.compute7(ColorScheme.compute5(8, 10, 16, i), ColorScheme.compute6(colorScheme6.getIntValue14(), i), 0.026F);
   }

   private int compute3(ColorScheme colorScheme7, int i) {
      return this.check4(colorScheme7)
         ? ColorScheme.compute7(ColorScheme.compute5(248, 250, 254, Math.min(255, i + 6)), ColorScheme.compute6(colorScheme7.getIntValue15(), i), 0.034F)
         : ColorScheme.compute7(ColorScheme.compute5(6, 8, 13, i), ColorScheme.compute6(colorScheme7.getIntValue15(), i), 0.022F);
   }

   private int compute4(ColorScheme colorScheme8, int i) {
      return this.check4(colorScheme8) ? ColorScheme.compute5(20, 27, 42, Math.round(i * 0.36F)) : ColorScheme.compute5(0, 0, 0, i);
   }

   private boolean check4(ColorScheme colorScheme9) {
      return switch (this.shaderFoundryScreenState) {
         case AUTO -> colorScheme9 != null && colorScheme9.isFlag();
         case DARK -> false;
         case LIGHT -> true;
      };
   }

   private List<ShaderFoundryScreen.ShaderFoundryScreenData2> resolve3() {
      ArrayList arrayList = new ArrayList();
      if (this.intValue2 != 2) {
         for (SavedShaderPreset savedShaderPreset2 : ShaderPresetStore.getINSTANCE().resolve()) {
            arrayList.add(new ShaderFoundryScreen.ShaderFoundryScreenData2(savedShaderPreset2, -1));
         }
      }

      if (this.intValue2 != 1) {
         for (int intValue9 = 0; intValue9 < ShaderSurfaceTemplates.ITEMS.size(); intValue9++) {
            arrayList.add(new ShaderFoundryScreen.ShaderFoundryScreenData2(null, intValue9));
         }
      }

      return arrayList;
   }

   private void invoke6(Metrics metrics3, Rect rect, float f, float g) {
      float floatValue37 = this.measure18(metrics3);
      Rect rect2 = this.resolve53(rect, metrics3);
      if (rect2.contains(f, g)) {
         List items = this.resolve3();
         int intValue10 = (int)Math.floor((g - rect2.y() + this.floatValue24) / floatValue37);
         if (intValue10 >= 0 && intValue10 < items.size()) {
            ShaderFoundryScreen.ShaderFoundryScreenData2 shaderFoundryScreenData2 = (ShaderFoundryScreen.ShaderFoundryScreenData2)items.get(intValue10);
            float floatValue38 = rect2.y() + intValue10 * floatValue37 - this.floatValue24;
            if (shaderFoundryScreenData2.presetIndex() >= 0) {
               if (this.resolve59(rect, metrics3, floatValue38).contains(f, g)) {
                  this.intValue = shaderFoundryScreenData2.presetIndex();
                  this.invoke88(false);
               } else if (this.resolve60(rect, metrics3, floatValue38).contains(f, g)) {
                  this.intValue = shaderFoundryScreenData2.presetIndex();
                  this.invoke88(true);
               } else {
                  this.intValue = shaderFoundryScreenData2.presetIndex();
                  this.setReady(ShaderSurfaceTemplates.ITEMS.get(shaderFoundryScreenData2.presetIndex()).text);
               }
            } else {
               SavedShaderPreset savedShaderPreset3 = shaderFoundryScreenData2.slot();
               ShaderSurface shaderSurface = ShaderSurface.resolve4(savedShaderPreset3.getText3());
               boolean flag4 = savedShaderPreset3.getText().equals(ShaderPresetStore.getINSTANCE().resolve12(shaderSurface));
               if (this.resolve58(rect, metrics3, floatValue38).contains(f, g)) {
                  if (flag4) {
                     ShaderPresetRegistry.getINSTANCE().invoke8(shaderSurface);
                     ThemeShaderProgramCache.getINSTANCE().invoke(shaderSurface);
                     ShaderPresetStore.getINSTANCE().invoke2(shaderSurface, null);
                  }

                  ShaderPresetRegistry.getINSTANCE().invoke9(savedShaderPreset3.getText2());
                  ThemeShaderProgramCache.getINSTANCE().invoke2(savedShaderPreset3.getText2());
                  ShaderPresetStore.getINSTANCE().check(savedShaderPreset3.getText());
                  if (savedShaderPreset3.getText().equals(this.text6)) {
                     this.text6 = null;
                  }

                  this.setReady("slot deleted");
               } else if (this.resolve57(rect, metrics3, floatValue38).contains(f, g)) {
                  this.invoke31(savedShaderPreset3);
               } else {
                  ShaderNode shaderNode = ShaderPresetStore.getINSTANCE().resolve11(savedShaderPreset3.getText(), this.shaderNodeRegistry);
                  if (shaderNode != null) {
                     this.invoke81();
                     this.shaderNode = shaderNode;
                     this.invoke95();
                     this.shaderSurface = shaderSurface == ShaderSurface.PREVIEW_ONLY ? ShaderSurface.HUD : shaderSurface;
                     this.invoke94(this.shaderSurface);
                     this.namedShaderProgram.invoke(this.shaderSurface);
                     this.text7 = this.shaderNode.getShaderTemplate().getText().isBlank()
                        ? savedShaderPreset3.getText2()
                        : this.shaderNode.getShaderTemplate().getText();
                     this.text6 = savedShaderPreset3.getText();
                     this.invoke17();
                     this.namedShaderProgram.invoke2(this.shaderNode);
                     this.invoke30(savedShaderPreset3.getText2(), this.shaderNode);
                     this.intValue4 = this.shaderNode.getIntValue();
                     this.invoke2();
                     this.setReady("loaded " + savedShaderPreset3.getText2());
                  }
               }
            }
         }
      }
   }

   private boolean check5(Metrics metrics4, int i, int j, float f, float g, int k) {
      Rect rect3 = this.resolve52(metrics4, i, j);
      if (!this.flag9) {
         return false;
      } else if (k != 0) {
         return rect3.contains(f, g);
      } else if (!rect3.contains(f, g)) {
         this.flag9 = false;
         return false;
      } else {
         Rect rect4 = this.resolve55(rect3, metrics4);
         if (rect4.contains(f, g)) {
            this.flag9 = false;
            return true;
         } else {
            for (int intValue11 = 0; intValue11 < ALL.length; intValue11++) {
               if (this.resolve54(rect3, metrics4, intValue11).contains(f, g)) {
                  this.intValue2 = intValue11;
                  this.floatValue24 = 0.0F;
                  return true;
               }
            }

            this.invoke6(metrics4, rect3, f, g);
            return true;
         }
      }
   }

   private boolean check6(Metrics metrics5, int i, int j, float f, float g, double d) {
      if (!this.flag9) {
         return false;
      } else {
         Rect rect5 = this.resolve52(metrics5, i, j);
         if (!rect5.contains(f, g)) {
            return false;
         } else {
            Rect rect6 = this.resolve53(rect5, metrics5);
            float floatValue39 = this.resolve3().size() * this.measure18(metrics5);
            this.floatValue24 = measure21(this.floatValue24 - (float)d * metrics5.measure(46.0F), 0.0F, Math.max(0.0F, floatValue39 - rect6.h()));
            return true;
         }
      }
   }

   private void invoke7(RenderManager renderManager5, Metrics metrics6, Rect rect7, int i) {
      float floatValue40 = rect7.x() + rect7.w() * 0.5F;
      float floatValue41 = rect7.y() + rect7.h() * 0.5F;
      float floatValue42 = metrics6.measure(9.0F);
      float floatValue43 = metrics6.measure(9.0F);
      renderManager5.invoke28(floatValue40 - floatValue42 * 0.5F, floatValue41 - floatValue43 * 0.32F, floatValue42, floatValue43 * 0.82F, metrics6.measure(1.6F), i, 0.8F);
      renderManager5.invoke5(floatValue40 - floatValue42 * 0.62F, floatValue41 - floatValue43 * 0.56F, floatValue42 * 1.24F, metrics6.measure(1.3F), metrics6.measure(0.8F), i);
      renderManager5.invoke5(floatValue40 - floatValue42 * 0.22F, floatValue41 - floatValue43 * 0.78F, floatValue42 * 0.44F, metrics6.measure(1.4F), metrics6.measure(0.8F), i);
      renderManager5.invoke5(
         floatValue40 - floatValue42 * 0.18F, floatValue41 - floatValue43 * 0.12F, metrics6.measure(1.0F), floatValue43 * 0.45F, metrics6.measure(0.5F), ColorScheme.compute6(i, 170)
      );
      renderManager5.invoke5(
         floatValue40 + floatValue42 * 0.18F, floatValue41 - floatValue43 * 0.12F, metrics6.measure(1.0F), floatValue43 * 0.45F, metrics6.measure(0.5F), ColorScheme.compute6(i, 170)
      );
   }

   private void invoke8(RenderManager renderManager6, Metrics metrics7, float f, float g, int i, boolean bl) {
      if (bl) {
         renderManager6.invoke39(f, g, metrics7.measure(4.2F), 0.0F, 1.0F, ColorScheme.compute6(i, 90));
         renderManager6.invoke39(f, g, metrics7.measure(2.2F), 0.0F, 1.0F, ColorScheme.compute6(i, 240));
      } else {
         renderManager6.invoke5(
            f - metrics7.measure(2.4F),
            g - metrics7.measure(2.4F),
            metrics7.measure(4.8F),
            metrics7.measure(4.8F),
            metrics7.measure(1.4F),
            ColorScheme.compute6(i, 116)
         );
      }
   }

   private void invoke9(
      RenderManager renderManager7, Metrics metrics8, ColorScheme colorScheme10, Rect rect8, float f, float g, float h
   ) {
      if (!(f <= rect8.h() + 1.0F)) {
         float floatValue44 = rect8.x() + rect8.w() - metrics8.measure(4.0F);
         float floatValue45 = rect8.y() + metrics8.measure(3.0F);
         float floatValue46 = rect8.h() - metrics8.measure(6.0F);
         float floatValue47 = Math.max(metrics8.measure(36.0F), floatValue46 * rect8.h() / f);
         float floatValue48 = Math.max(1.0F, f - rect8.h());
         float floatValue49 = floatValue45 + (floatValue46 - floatValue47) * (this.floatValue24 / floatValue48);
         float floatValue50 = ClickGuiDragRegistry.measure(
            7102L,
            floatValue44 - metrics8.measure(3.0F),
            floatValue45,
            metrics8.measure(9.0F),
            floatValue46,
            floatValue49,
            floatValue47,
            metrics8.measure(6.0F),
            g,
            h,
            gx -> this.floatValue24 = measure21(gx, 0.0F, 1.0F) * floatValue48
         );
         float floatValue51 = metrics8.measure(2.0F) + metrics8.measure(2.0F) * floatValue50;
         renderManager7.invoke5(floatValue44, floatValue45, metrics8.measure(2.0F), floatValue46, metrics8.measure(1.0F), colorScheme10.getIntValue4());
         renderManager7.invoke5(
            floatValue44 + metrics8.measure(2.0F) - floatValue51,
            floatValue49,
            floatValue51,
            floatValue47,
            metrics8.measure(1.5F),
            ColorScheme.compute6(colorScheme10.getIntValue15(), (int)(150.0F + 80.0F * floatValue50))
         );
      }
   }

   private void invoke10(RenderManager renderManager8, ClickGuiState clickGuiState5, ThemeContext themeContext4, int i, int j) {
      float floatValue52 = this.clampedSpringAnimation5.measure();
      if (this.flag9 || !(floatValue52 <= 0.01F)) {
         this.invoke36();
         Metrics metrics9 = themeContext4.getMetrics();
         ColorScheme colorScheme11 = themeContext4.getColorScheme();
         Rect rect9 = this.resolve52(metrics9, i, j);
         rect9 = new Rect(rect9.x(), rect9.y() - metrics9.measure(10.0F) * (1.0F - floatValue52), rect9.w(), rect9.h());
         float floatValue53 = metrics9.measure(12.0F);
         renderManager8.invoke65(floatValue52);
         boolean flag5 = false ;

         label200: {
            try {
               flag5 = true;
               renderManager8.invoke41(rect9.x(), rect9.y(), rect9.w(), rect9.h(), floatValue53, metrics9.measure(22.0F), metrics9.measure(2.0F), this.compute4(colorScheme11, 148));
               renderManager8.invoke5(rect9.x(), rect9.y(), rect9.w(), rect9.h(), floatValue53, this.compute3(colorScheme11, 232));
               renderManager8.invoke28(rect9.x(), rect9.y(), rect9.w(), rect9.h(), floatValue53, ColorScheme.compute6(colorScheme11.getIntValue14(), 108), 0.8F);
               renderManager8.invoke5(
                  rect9.x() + metrics9.measure(1.0F),
                  rect9.y() + metrics9.measure(1.0F),
                  rect9.w() - metrics9.measure(2.0F),
                  metrics9.measure(1.0F),
                  metrics9.measure(1.0F),
                  ColorScheme.compute6(colorScheme11.getIntValue13(), this.check4(colorScheme11) ? 58 : 18)
               );
               ClickGuiRenderUtils.invoke3(
                  renderManager8,
                  metrics9,
                  FontRegistry.fontObject4,
                  rect9.x() + metrics9.measure(12.0F),
                  rect9.y() + metrics9.measure(12.0F),
                  12.0F,
                  "Library",
                  this.compute11(colorScheme11)
               );
               ClickGuiRenderUtils.invoke3(
                  renderManager8,
                  metrics9,
                  FontRegistry.fontObject,
                  rect9.x() + metrics9.measure(12.0F),
                  rect9.y() + metrics9.measure(27.0F),
                  8.0F,
                  "your shaders and presets / preview, bind, apply",
                  ColorScheme.compute6(colorScheme11.getIntValue14(), 196)
               );
               Rect rect10 = this.resolve55(rect9, metrics9);
               boolean flag6 = rect10.contains(clickGuiState5.getFloatValue(), clickGuiState5.getFloatValue2());
               renderManager8.invoke5(
                  rect10.x(),
                  rect10.y(),
                  rect10.w(),
                  rect10.h(),
                  metrics9.measure(7.0F),
                  ColorScheme.compute7(colorScheme11.getIntValue4(), ColorScheme.compute5(220, 80, 96, 112), flag6 ? 1.0F : 0.0F)
               );
               renderManager8.invoke28(
                  rect10.x(),
                  rect10.y(),
                  rect10.w(),
                  rect10.h(),
                  metrics9.measure(7.0F),
                  ColorScheme.compute6(flag6 ? -37756 : colorScheme11.getIntValue6(), flag6 ? 210 : 64),
                  0.65F
               );
               this.invoke29(renderManager8, metrics9, colorScheme11, rect10.x() + rect10.w() * 0.5F, rect10.y() + rect10.h() * 0.5F, 4, flag6 ? 1.0F : 0.35F);

               for (int intValue12 = 0; intValue12 < ALL.length; intValue12++) {
                  this.invoke43(
                     renderManager8,
                     metrics9,
                     colorScheme11,
                     this.resolve54(rect9, metrics9, intValue12),
                     ALL[intValue12],
                     this.intValue2 == intValue12,
                     clickGuiState5.getFloatValue(),
                     clickGuiState5.getFloatValue2()
                  );
               }

               List items2 = this.resolve3();
               Rect rect11 = this.resolve53(rect9, metrics9);
               if (items2.isEmpty()) {
                  renderManager8.invoke5(
                     rect11.x(), rect11.y(), rect11.w(), rect11.h(), metrics9.measure(9.0F), ColorScheme.compute5(255, 255, 255, this.check4(colorScheme11) ? 38 : 8)
                  );
                  renderManager8.invoke28(rect11.x(), rect11.y(), rect11.w(), rect11.h(), metrics9.measure(9.0F), colorScheme11.getIntValue6(), 0.65F);
                  ClickGuiRenderUtils.invoke3(
                     renderManager8,
                     metrics9,
                     FontRegistry.fontObject4,
                     rect11.x() + metrics9.measure(12.0F),
                     rect11.y() + metrics9.measure(18.0F),
                     10.0F,
                     "No saved shaders",
                     this.compute11(colorScheme11)
                  );
                  ClickGuiRenderUtils.invoke3(
                     renderManager8,
                     metrics9,
                     FontRegistry.fontObject,
                     rect11.x() + metrics9.measure(12.0F),
                     rect11.y() + metrics9.measure(34.0F),
                     8.0F,
                     "Ctrl+S or File / Save As stores the current graph here.",
                     this.compute12(colorScheme11)
                  );
                  flag5 = false;
                  break label200;
               }

               float floatValue54 = this.measure18(metrics9);
               float floatValue55 = items2.size() * floatValue54;
               this.floatValue24 = measure21(this.floatValue24, 0.0F, Math.max(0.0F, floatValue55 - rect11.h()));
               renderManager8.invoke20();
               renderManager8.invoke24(
                  rect11.x(), rect11.y(), rect11.w(), rect11.h(), metrics9.measure(9.0F), metrics9.measure(9.0F), metrics9.measure(9.0F), metrics9.measure(9.0F)
               );

               try {
                  for (int intValue13 = 0; intValue13 < items2.size(); intValue13++) {
                     float floatValue56 = rect11.y() + intValue13 * floatValue54 - this.floatValue24;
                     if (!(floatValue56 > rect11.y() + rect11.h()) && !(floatValue56 + floatValue54 < rect11.y())) {
                        ShaderFoundryScreen.ShaderFoundryScreenData2 shaderFoundryScreenData22 = (ShaderFoundryScreen.ShaderFoundryScreenData2)items2.get(intValue13);
                        if (shaderFoundryScreenData22.presetIndex() >= 0) {
                           this.invoke12(renderManager8, clickGuiState5, themeContext4, metrics9, colorScheme11, rect9, rect11, shaderFoundryScreenData22.presetIndex(), floatValue56, floatValue54, i, j, floatValue52);
                        } else {
                           this.invoke11(renderManager8, clickGuiState5, themeContext4, metrics9, colorScheme11, rect9, rect11, shaderFoundryScreenData22.slot(), floatValue56, floatValue54, floatValue52);
                        }
                     }
                  }
               } finally {
                  renderManager8.invoke20();
                  renderManager8.invoke25();
               }

               this.invoke9(renderManager8, metrics9, colorScheme11, rect11, floatValue55, clickGuiState5.getFloatValue(), clickGuiState5.getFloatValue2());
               flag5 = false;
            } finally {
               if (flag5) {
                  renderManager8.invoke66();
               }
            }

            renderManager8.invoke66();
            return;
         }

         renderManager8.invoke66();
      }
   }

   private void invoke11(
      RenderManager renderManager9,
      ClickGuiState clickGuiState6,
      ThemeContext themeContext5,
      Metrics metrics10,
      ColorScheme colorScheme12,
      Rect rect12,
      Rect rect13,
      SavedShaderPreset savedShaderPreset4,
      float f,
      float g,
      float h
   ) {
      ShaderPresetStore shaderPresetStore = ShaderPresetStore.getINSTANCE();
      boolean flag7 = clickGuiState6.getFloatValue() >= rect13.x()
         && clickGuiState6.getFloatValue() <= rect13.x() + rect13.w()
         && clickGuiState6.getFloatValue2() >= f
         && clickGuiState6.getFloatValue2() <= f + g - metrics10.measure(6.0F);
      boolean flag8 = savedShaderPreset4.getText().equals(this.text6);
      ShaderSurface shaderSurface2 = ShaderSurface.resolve4(savedShaderPreset4.getText3());
      boolean flag9 = savedShaderPreset4.getText().equals(shaderPresetStore.resolve12(shaderSurface2));
      Rect rect14 = new Rect(rect13.x(), f, rect13.w() - metrics10.measure(4.0F), g - metrics10.measure(6.0F));
      float floatValue57 = Math.max(flag7 ? 0.75F : 0.0F, flag8 ? 0.58F : 0.0F);
      renderManager9.invoke5(
         rect14.x(),
         rect14.y(),
         rect14.w(),
         rect14.h(),
         metrics10.measure(8.0F),
         ColorScheme.compute7(
            ColorScheme.compute5(255, 255, 255, this.check4(colorScheme12) ? 46 : 10), ColorScheme.compute6(colorScheme12.getIntValue14(), 72), floatValue57
         )
      );
      renderManager9.invoke28(
         rect14.x(),
         rect14.y(),
         rect14.w(),
         rect14.h(),
         metrics10.measure(8.0F),
         ColorScheme.compute7(
            colorScheme12.getIntValue6(),
            ColorScheme.compute6(flag9 ? colorScheme12.getIntValue15() : colorScheme12.getIntValue14(), 126),
            Math.max(floatValue57, flag9 ? 0.38F : 0.0F)
         ),
         0.65F
      );
      Rect rect15 = this.resolve56(rect14, metrics10);
      this.invoke13(renderManager9, clickGuiState6, themeContext5, savedShaderPreset4, rect15, h);
      this.invoke8(
         renderManager9,
         metrics10,
         rect14.x() + metrics10.measure(12.0F),
         rect14.y() + rect14.h() * 0.5F,
         flag9 ? colorScheme12.getIntValue15() : colorScheme12.getIntValue14(),
         flag8
      );
      float floatValue58 = rect15.x() + rect15.w() + metrics10.measure(10.0F);
      Rect rect16 = this.resolve57(rect12, metrics10, f);
      Rect rect17 = this.resolve58(rect12, metrics10, f);
      float floatValue59 = Math.max(metrics10.measure(72.0F), rect16.x() - floatValue58 - metrics10.measure(10.0F));
      ClickGuiRenderUtils.invoke3(
         renderManager9,
         metrics10,
         FontRegistry.fontObject4,
         floatValue58,
         rect14.y() + metrics10.measure(8.0F),
         10.0F,
         ClickGuiRenderUtils.resolve4(metrics10, FontRegistry.fontObject4, savedShaderPreset4.getText2(), 10.0F, floatValue59),
         this.compute11(colorScheme12)
      );
      String text2 = (shaderSurface2 == null ? "Unknown" : shaderSurface2.getText2()) + (flag9 ? " / bound" : "") + " / " + this.resolve73(savedShaderPreset4.getTimestamp2());
      ClickGuiRenderUtils.invoke3(
         renderManager9,
         metrics10,
         FontRegistry.fontObject,
         floatValue58,
         rect14.y() + metrics10.measure(25.0F),
         8.0F,
         ClickGuiRenderUtils.resolve4(metrics10, FontRegistry.fontObject, text2, 8.0F, floatValue59),
         ColorScheme.compute6(colorScheme12.getIntValue14(), 200)
      );
      String text3 = savedShaderPreset4.getText7() + " / " + ShaderPresetRegistry.getINSTANCE().resolve11(savedShaderPreset4.getText2()).size() + " uniforms";
      ClickGuiRenderUtils.invoke3(
         renderManager9,
         metrics10,
         FontRegistry.fontObject,
         floatValue58,
         rect14.y() + metrics10.measure(40.0F),
         8.0F,
         ClickGuiRenderUtils.resolve4(metrics10, FontRegistry.fontObject, text3, 8.0F, floatValue59),
         ColorScheme.compute6(colorScheme12.getIntValue15(), 176)
      );
      boolean flag10 = rect16.contains(clickGuiState6.getFloatValue(), clickGuiState6.getFloatValue2());
      this.invoke14(renderManager9, metrics10, colorScheme12, rect16, flag9 ? "Off" : "Bind", flag10, flag9);
      boolean flag11 = rect17.contains(clickGuiState6.getFloatValue(), clickGuiState6.getFloatValue2());
      renderManager9.invoke5(
         rect17.x(),
         rect17.y(),
         rect17.w(),
         rect17.h(),
         metrics10.measure(7.0F),
         ColorScheme.compute7(colorScheme12.getIntValue4(), ColorScheme.compute5(230, 82, 96, 128), flag11 ? 1.0F : 0.0F)
      );
      renderManager9.invoke28(
         rect17.x(),
         rect17.y(),
         rect17.w(),
         rect17.h(),
         metrics10.measure(7.0F),
         ColorScheme.compute6(flag11 ? -37756 : colorScheme12.getIntValue6(), flag11 ? 220 : 70),
         0.65F
      );
      this.invoke7(
         renderManager9, metrics10, rect17, flag11 ? ColorScheme.compute5(255, 214, 220, 242) : ColorScheme.compute6(colorScheme12.getIntValue13(), 148)
      );
   }

   private void invoke12(
      RenderManager renderManager10,
      ClickGuiState clickGuiState7,
      ThemeContext themeContext6,
      Metrics metrics11,
      ColorScheme colorScheme13,
      Rect rect18,
      Rect rect19,
      int i,
      float f,
      float g,
      int j,
      int k,
      float h
   ) {
      ShaderSurfaceTemplates.ShaderSurfaceTemplatesState shaderSurfaceTemplatesState = ShaderSurfaceTemplates.ITEMS.get(i);
      boolean flag12 = clickGuiState7.getFloatValue() >= rect19.x()
         && clickGuiState7.getFloatValue() <= rect19.x() + rect19.w()
         && clickGuiState7.getFloatValue2() >= f
         && clickGuiState7.getFloatValue2() <= f + g - metrics11.measure(6.0F);
      boolean flag13 = i == this.intValue;
      Rect rect20 = new Rect(rect19.x(), f, rect19.w() - metrics11.measure(4.0F), g - metrics11.measure(6.0F));
      float floatValue60 = Math.max(flag12 ? 0.62F : 0.0F, flag13 ? 0.5F : 0.0F);
      renderManager10.invoke5(
         rect20.x(),
         rect20.y(),
         rect20.w(),
         rect20.h(),
         metrics11.measure(8.0F),
         ColorScheme.compute7(
            ColorScheme.compute5(255, 255, 255, this.check4(colorScheme13) ? 46 : 10), ColorScheme.compute6(colorScheme13.getIntValue15(), 66), floatValue60
         )
      );
      renderManager10.invoke28(
         rect20.x(),
         rect20.y(),
         rect20.w(),
         rect20.h(),
         metrics11.measure(8.0F),
         ColorScheme.compute7(
            colorScheme13.getIntValue6(),
            ColorScheme.compute6(flag13 ? colorScheme13.getIntValue14() : colorScheme13.getIntValue15(), flag13 ? 148 : 112),
            Math.max(floatValue60, flag13 ? 0.6F : 0.0F)
         ),
         flag13 ? 0.85F : 0.65F
      );
      if (flag13) {
         renderManager10.invoke5(
            rect20.x(),
            rect20.y() + metrics11.measure(9.0F),
            metrics11.measure(2.4F),
            rect20.h() - metrics11.measure(18.0F),
            metrics11.measure(1.2F),
            ColorScheme.compute6(colorScheme13.getIntValue14(), 230)
         );
      }

      Rect rect21 = this.resolve56(rect20, metrics11);
      ShaderPreviewRenderer.invoke19(
         renderManager10,
         themeContext6,
         this.valuesByKey2.get(i),
         "__preset_thumb_" + i,
         rect21.x(),
         rect21.y(),
         rect21.w(),
         rect21.h(),
         j,
         k,
         clickGuiState7.getFloatValue(),
         clickGuiState7.getFloatValue2(),
         h
      );
      renderManager10.invoke28(
         rect21.x(),
         rect21.y(),
         rect21.w(),
         rect21.h(),
         metrics11.measure(6.0F),
         ColorScheme.compute6(colorScheme13.getIntValue15(), Math.round(84.0F * h)),
         0.55F
      );
      this.invoke8(renderManager10, metrics11, rect20.x() + metrics11.measure(12.0F), rect20.y() + rect20.h() * 0.5F, colorScheme13.getIntValue15(), flag13);
      float floatValue61 = rect21.x() + rect21.w() + metrics11.measure(10.0F);
      Rect rect22 = this.resolve59(rect18, metrics11, f);
      float floatValue62 = Math.max(metrics11.measure(72.0F), rect22.x() - floatValue61 - metrics11.measure(10.0F));
      ClickGuiRenderUtils.invoke3(
         renderManager10,
         metrics11,
         FontRegistry.fontObject4,
         floatValue61,
         rect20.y() + metrics11.measure(8.0F),
         10.0F,
         ClickGuiRenderUtils.resolve4(metrics11, FontRegistry.fontObject4, shaderSurfaceTemplatesState.text, 10.0F, floatValue62),
         this.compute11(colorScheme13)
      );
      String text4 = shaderSurfaceTemplatesState.shaderSurface.getText2();
      float floatValue63 = ClickGuiRenderUtils.measure2(metrics11, FontRegistry.fontObject, text4, 7.0F) + metrics11.measure(12.0F);
      float floatValue64 = rect20.y() + metrics11.measure(23.0F);
      renderManager10.invoke5(
         floatValue61, floatValue64, floatValue63, metrics11.measure(13.0F), metrics11.measure(6.5F), ColorScheme.compute6(colorScheme13.getIntValue15(), 44)
      );
      ClickGuiRenderUtils.invoke4(
         renderManager10,
         metrics11,
         FontRegistry.fontObject,
         floatValue61 + metrics11.measure(6.0F),
         floatValue64,
         metrics11.measure(13.0F),
         7.0F,
         text4,
         ColorScheme.compute6(colorScheme13.getIntValue15(), 235)
      );
      ClickGuiRenderUtils.invoke3(
         renderManager10,
         metrics11,
         FontRegistry.fontObject,
         floatValue61 + floatValue63 + metrics11.measure(8.0F),
         floatValue64 + metrics11.measure(3.0F),
         7.5F,
         ClickGuiRenderUtils.resolve4(
            metrics11, FontRegistry.fontObject, "preset / " + shaderSurfaceTemplatesState.text3, 7.5F, Math.max(1.0F, floatValue62 - floatValue63 - metrics11.measure(8.0F))
         ),
         ColorScheme.compute6(colorScheme13.getIntValue14(), 186)
      );
      ClickGuiRenderUtils.invoke3(
         renderManager10,
         metrics11,
         FontRegistry.fontObject,
         floatValue61,
         rect20.y() + metrics11.measure(40.0F),
         8.0F,
         ClickGuiRenderUtils.resolve4(metrics11, FontRegistry.fontObject, shaderSurfaceTemplatesState.text2, 8.0F, floatValue62),
         this.compute12(colorScheme13)
      );
      this.invoke41(renderManager10, metrics11, colorScheme13, rect22, "Use", clickGuiState7.getFloatValue(), clickGuiState7.getFloatValue2(), true);
      this.invoke41(
         renderManager10,
         metrics11,
         colorScheme13,
         this.resolve60(rect18, metrics11, f),
         "Merge",
         clickGuiState7.getFloatValue(),
         clickGuiState7.getFloatValue2(),
         false
      );
   }

   private void invoke13(
      RenderManager renderManager11,
      ClickGuiState clickGuiState8,
      ThemeContext themeContext7,
      SavedShaderPreset savedShaderPreset5,
      Rect rect23,
      float f
   ) {
      Metrics metrics12 = themeContext7.getMetrics();
      ColorScheme colorScheme14 = themeContext7.getColorScheme();
      float floatValue65 = metrics12.measure(6.0F);
      renderManager11.invoke5(
         rect23.x(),
         rect23.y(),
         rect23.w(),
         rect23.h(),
         floatValue65,
         ColorScheme.compute5(255, 255, 255, this.check4(colorScheme14) ? 48 : 10)
      );
      boolean flag14 = false;
      String text5 = savedShaderPreset5 == null ? "" : savedShaderPreset5.getText2();
      if (!text5.isBlank() && ShaderPresetRegistry.getINSTANCE().check2(text5)) {
         ShaderNode shaderNode2 = ShaderPresetRegistry.getINSTANCE().resolve4(text5);
         ShaderSurface shaderSurface3 = ShaderSurface.resolve4(savedShaderPreset5.getText3());
         if (shaderSurface3 == ShaderSurface.PREVIEW_ONLY) {
            shaderSurface3 = this.shaderSurface;
         }

         if (shaderNode2 != null) {
            ShaderPreviewRenderer.invoke2(
               renderManager11,
               themeContext7,
               text5,
               shaderSurface3,
               shaderNode2,
               rect23.x(),
               rect23.y(),
               rect23.w(),
               rect23.h(),
               this.compute13(),
               this.compute14(),
               clickGuiState8.getFloatValue(),
               clickGuiState8.getFloatValue2(),
               f
            );
            flag14 = true;
         }
      }

      if (!flag14) {
         renderManager11.invoke37(
            rect23.x(),
            rect23.y(),
            rect23.w(),
            rect23.h(),
            floatValue65,
            ColorScheme.compute6(colorScheme14.getIntValue14(), Math.round(70.0F * f)),
            ColorScheme.compute6(colorScheme14.getIntValue15(), Math.round(42.0F * f))
         );
      }

      renderManager11.invoke28(
         rect23.x(),
         rect23.y(),
         rect23.w(),
         rect23.h(),
         floatValue65,
         ColorScheme.compute6(colorScheme14.getIntValue14(), Math.round(84.0F * f)),
         0.55F
      );
   }

   private void invoke14(
      RenderManager renderManager12, Metrics metrics13, ColorScheme colorScheme15, Rect rect24, String string, boolean bl, boolean bl2
   ) {
      float floatValue66 = Math.max(bl2 ? 0.62F : 0.0F, bl ? 1.0F : 0.0F);
      renderManager12.invoke5(
         rect24.x(),
         rect24.y(),
         rect24.w(),
         rect24.h(),
         metrics13.measure(7.0F),
         ColorScheme.compute7(
            colorScheme15.getIntValue4(), ColorScheme.compute6(bl2 ? colorScheme15.getIntValue15() : colorScheme15.getIntValue14(), 84), floatValue66
         )
      );
      renderManager12.invoke28(
         rect24.x(),
         rect24.y(),
         rect24.w(),
         rect24.h(),
         metrics13.measure(7.0F),
         ColorScheme.compute7(
            colorScheme15.getIntValue6(), ColorScheme.compute6(bl2 ? colorScheme15.getIntValue15() : colorScheme15.getIntValue14(), 152), floatValue66
         ),
         0.62F
      );
      float floatValue67 = ClickGuiRenderUtils.measure2(metrics13, FontRegistry.fontObject4, string, 9.0F);
      float floatValue68 = rect24.x() + (rect24.w() - floatValue67 - metrics13.measure(10.0F)) * 0.5F;
      int intValue14 = ColorScheme.compute6(bl2 ? colorScheme15.getIntValue15() : colorScheme15.getIntValue14(), Math.round(160.0F + 80.0F * floatValue66));
      float floatValue69 = rect24.y() + rect24.h() * 0.5F;
      if (bl2) {
         renderManager12.invoke39(floatValue68 + metrics13.measure(3.0F), floatValue69, metrics13.measure(3.0F), 0.0F, 1.0F, ColorScheme.compute6(intValue14, 88));
         renderManager12.invoke39(floatValue68 + metrics13.measure(3.0F), floatValue69, metrics13.measure(1.6F), 0.0F, 1.0F, intValue14);
      } else {
         renderManager12.invoke40(floatValue68 + metrics13.measure(3.0F), floatValue69, metrics13.measure(2.6F), 0.0F, 1.0F, 0.9F, intValue14);
      }

      ClickGuiRenderUtils.invoke4(
         renderManager12,
         metrics13,
         FontRegistry.fontObject4,
         floatValue68 + metrics13.measure(10.0F),
         rect24.y(),
         rect24.h(),
         9.0F,
         string,
         ColorScheme.compute7(colorScheme15.getIntValue12(), colorScheme15.getIntValue13(), 0.52F + floatValue66 * 0.48F)
      );
   }

   public boolean check7(ClickGuiState clickGuiState9, ThemeContext themeContext8, float f, float g, int i, int j, int k) {
      if (clickGuiState9 != null && themeContext8 != null && this.check(clickGuiState9)) {
         this.floatValue19 = f;
         this.floatValue20 = g;
         Metrics metrics14 = themeContext8.getMetrics();
         if (this.shaderLibraryBrowser.isFlag()) {
            boolean flag15 = this.shaderLibraryBrowser.check(f, g, i, metrics14, j, k);
            this.invoke93();
            return flag15;
         } else {
            if (this.shaderNodeBrowser.isFlag()) {
               Rect rect25 = this.shaderNodeBrowser.resolve2(metrics14, j, k);
               if (rect25.contains(f, g)) {
                  if (i == 0) {
                     ShaderNodeDefinition shaderNodeDefinition = this.shaderNodeBrowser.resolve4(metrics14, j, k, f, g);
                     if (shaderNodeDefinition != null) {
                        this.invoke15(shaderNodeDefinition);
                        return true;
                     }

                     String text6 = this.shaderNodeBrowser.resolve5(metrics14, j, k, f, g);
                     if (text6 != null) {
                        this.shaderNodeBrowser.invoke8(text6);
                        return true;
                     }
                  }

                  return true;
               }

               if (i == 0 || i == 1) {
                  this.shaderNodeBrowser.invoke2();
                  return true;
               }
            }

            if (this.flag6) {
               return this.check21(metrics14, j, k, f, g, i);
            } else {
               if (this.text10 != null && !this.resolve34(metrics14, j, k).contains(f, g)) {
                  this.invoke63();
               }

               if (this.flag10 && (this.flag8 || !this.resolve10(metrics14, k).contains(f, g))) {
                  this.flag10 = false;
               }

               if (i == 0 && this.check17(clickGuiState9, metrics14, j, f, g)) {
                  return true;
               } else if (this.flag9 && this.check5(metrics14, j, k, f, g, i)) {
                  return true;
               } else if (this.flag3 && this.check18(metrics14, j, f, g, i)) {
                  return true;
               } else if (this.flag4 && this.check19(metrics14, j, k, f, g, i)) {
                  return true;
               } else if (this.flag5 && this.check20(metrics14, j, f, g, i)) {
                  return true;
               } else if (this.check22(metrics14, j, k, f, g, i)) {
                  return true;
               } else {
                  Rect rect26 = this.resolve32(metrics14, k);
                  Rect rect27 = this.resolve33(metrics14, k);
                  if (i == 0 && rect27.contains(f, g)) {
                     this.flag8 = !this.flag8;
                     return true;
                  } else if (this.flag8 || i != 0 || !rect26.contains(f, g)) {
                     Rect rect28 = this.resolve43(metrics14, j, k);
                     if (i == 0 && rect28.contains(f, g)) {
                        if (g <= rect28.y() + metrics14.measure(34.0F)) {
                           this.flag14 = true;
                           this.floatValue33 = f - rect28.x();
                           this.floatValue34 = g - rect28.y();
                        }

                        return true;
                     } else {
                        if (i == 0) {
                           ShaderNodeKind shaderNodeKind = this.resolve22(f, g);
                           if (shaderNodeKind != null) {
                              this.invoke78(shaderNodeKind.getText());
                              this.invoke16(shaderNodeKind.getText());
                              return true;
                           }
                        }

                        if (i != 2 && (i != 0 || !this.check26())) {
                           ShaderFoundryScreen.ShaderFoundryScreenData4 shaderFoundryScreenData4 = this.resolve23(f, g);
                           if (i == 0 && shaderFoundryScreenData4 != null) {
                              if (shaderFoundryScreenData4.direction == ShaderPinDirection.OUTPUT) {
                                 this.text3 = shaderFoundryScreenData4.nodeId;
                                 this.text4 = shaderFoundryScreenData4.pinId;
                                 this.invoke16(shaderFoundryScreenData4.nodeId);
                              } else {
                                 this.invoke81();
                                 if (this.shaderNode.check3(shaderFoundryScreenData4.nodeId, shaderFoundryScreenData4.pinId)) {
                                    this.springAnimation2.invoke(1.0F);
                                 }

                                 this.invoke16(shaderFoundryScreenData4.nodeId);
                              }

                              return true;
                           } else {
                              ShaderNodeKind shaderNodeKind2 = this.resolve21(f, g);
                              if (i == 0 && shaderNodeKind2 != null) {
                                 if (Screen.hasShiftDown()) {
                                    this.values.add(shaderNodeKind2.getText());
                                    this.text2 = shaderNodeKind2.getText();
                                 } else if (!this.check8(shaderNodeKind2.getText())) {
                                    this.invoke16(shaderNodeKind2.getText());
                                 } else {
                                    this.text2 = shaderNodeKind2.getText();
                                 }

                                 if (check16(shaderNodeKind2.getText2()) && this.resolve41(shaderNodeKind2).contains(f, g)) {
                                    this.invoke81();
                                    this.invoke61();
                                    ShaderUniformEditor shaderUniformEditor = this.resolve14(shaderNodeKind2);
                                    shaderUniformEditor.setFloatValue5(shaderNodeKind2.measure("value", "int_value".equals(shaderNodeKind2.getText2()) ? 1.0F : 0.5F));
                                    if (shaderUniformEditor.check2(f, g, i, this.resolve41(shaderNodeKind2))) {
                                       this.text8 = shaderNodeKind2.getText();
                                    }

                                    return true;
                                 } else if (this.check25(shaderNodeKind2) && this.resolve42(shaderNodeKind2).contains(f, g)) {
                                    this.invoke81();
                                    this.invoke61();
                                    this.invoke62();
                                    ShaderUniformEditor shaderUniformEditor2 = this.resolve15(shaderNodeKind2);
                                    shaderUniformEditor2.setText(shaderNodeKind2.resolve("name", this.resolve44(shaderNodeKind2)));
                                    if (shaderUniformEditor2.check2(f, g, i, this.resolve42(shaderNodeKind2))) {
                                       this.text9 = shaderNodeKind2.getText();
                                    }

                                    return true;
                                 } else {
                                    this.invoke61();
                                    this.invoke62();
                                    this.invoke81();
                                    this.text = shaderNodeKind2.getText();
                                    this.floatValue11 = this.measure16(f) - shaderNodeKind2.getFloatValue();
                                    this.floatValue12 = this.measure17(g) - shaderNodeKind2.getFloatValue2();
                                    this.invoke21(f, g);
                                    return true;
                                 }
                              } else {
                                 if (this.text8 != null) {
                                    this.invoke61();
                                 }

                                 if (this.text9 != null) {
                                    this.invoke62();
                                 }

                                 if (i == 1) {
                                    this.shaderNodeBrowser.invoke(f, g, null);
                                    return true;
                                 } else if (i == 0) {
                                    this.flag2 = true;
                                    this.values2.clear();
                                    if (Screen.hasShiftDown()) {
                                       this.values2.addAll(this.values);
                                    }

                                    this.floatValue15 = f;
                                    this.floatValue16 = g;
                                    this.floatValue17 = f;
                                    this.floatValue18 = g;
                                    if (!Screen.hasShiftDown()) {
                                       this.invoke17();
                                    }

                                    return true;
                                 } else {
                                    return true;
                                 }
                              }
                           }
                        } else {
                           this.invoke71(f, g);
                           return true;
                        }
                     }
                  } else if (this.resolve10(metrics14, k).contains(f, g)) {
                     this.flag10 = true;
                     this.timestamp4 = System.currentTimeMillis();
                     return true;
                  } else {
                     this.flag10 = false;
                     ShaderFoundryScreen.ShaderFoundryScreenData3 shaderFoundryScreenData3 = this.resolve13(metrics14, k, f, g);
                     if (shaderFoundryScreenData3 != null) {
                        if (shaderFoundryScreenData3.row().type() == 0) {
                           if (!this.values3.remove(shaderFoundryScreenData3.row().category())) {
                              this.values3.add(shaderFoundryScreenData3.row().category());
                           }

                           return true;
                        }

                        ShaderNodeDefinition shaderNodeDefinition2 = shaderFoundryScreenData3.row().def();
                        if (shaderFoundryScreenData3.star()) {
                           ShaderLibraryState.getINSTANCE().invoke(shaderNodeDefinition2.getText());
                           return true;
                        }

                        float floatValue70 = this.measure16(j * 0.5F);
                        float floatValue71 = this.measure17(k * 0.5F);
                        this.invoke81();
                        ShaderNodeKind shaderNodeKind3 = this.shaderNode
                           .resolve(shaderNodeDefinition2.getText(), floatValue70 - shaderNodeDefinition2.getFloatValue() * 0.5F, floatValue71 - this.measure11(shaderNodeDefinition2) * 0.5F, this.shaderNodeRegistry);
                        this.invoke16(shaderNodeKind3.getText());
                        this.valuesByKey6.put(shaderNodeKind3.getText(), new SpringAnimation(0.0F));
                        ShaderLibraryState.getINSTANCE().invoke2(shaderNodeDefinition2.getText());
                        this.setReady(shaderNodeDefinition2.getText2());
                     }

                     return true;
                  }
               }
            }
         }
      } else {
         return false;
      }
   }

   private void invoke15(ShaderNodeDefinition shaderNodeDefinition3) {
      float floatValue72 = this.shaderNodeBrowser.getFloatValue();
      float floatValue73 = this.shaderNodeBrowser.getFloatValue2();
      float floatValue74 = this.measure16(floatValue72);
      float floatValue75 = this.measure17(floatValue73);
      this.invoke81();
      ShaderNodeKind shaderNodeKind4 = this.shaderNode
         .resolve(shaderNodeDefinition3.getText(), floatValue74 - shaderNodeDefinition3.getFloatValue() * 0.5F, floatValue75 - this.measure11(shaderNodeDefinition3) * 0.5F, this.shaderNodeRegistry);
      this.invoke16(shaderNodeKind4.getText());
      this.valuesByKey6.put(shaderNodeKind4.getText(), new SpringAnimation(0.0F));
      if (shaderNodeDefinition3.isFlag()) {
         this.valuesByKey9.put(shaderNodeKind4.getText(), true);
         this.resolve30(shaderNodeKind4.getText()).invoke2(1.0F);
         this.invoke79(shaderNodeKind4.getText());
      }

      if (this.shaderNodeBrowser.getShaderValueType() != null && this.text11 != null && this.text12 != null) {
         String text7 = null;

         for (ShaderPin shaderPin : shaderNodeDefinition3.getItems()) {
            if (shaderPin.type() == this.shaderNodeBrowser.getShaderValueType()) {
               text7 = shaderPin.id();
               break;
            }
         }

         if (text7 != null) {
            this.shaderNode.check2(this.text11, this.text12, shaderNodeKind4.getText(), text7, this.shaderNodeRegistry);
            this.springAnimation2.invoke(1.0F);
         }
      }

      this.text11 = null;
      this.text12 = null;
      this.shaderNodeBrowser.invoke2();
      ShaderLibraryState.getINSTANCE().invoke2(shaderNodeDefinition3.getText());
      this.setReady(shaderNodeDefinition3.getText2());
   }

   private void invoke16(String string) {
      this.values.clear();
      if (string != null) {
         this.values.add(string);
      }

      this.text2 = string;
   }

   private void invoke17() {
      this.values.clear();
      this.text2 = null;
      this.text10 = null;
   }

   private void invoke18() {
      for (ClampedSpringAnimation clampedSpringAnimation : this.valuesByKey10.values()) {
         if (clampedSpringAnimation != null) {
            clampedSpringAnimation.invoke2(0.0F);
         }
      }

      this.valuesByKey9.clear();
      this.valuesByKey10.clear();
   }

   private void invoke19(String string) {
      if (string != null) {
         this.valuesByKey5.keySet().removeIf(string2 -> string2.startsWith(string + ":"));
      }
   }

   private boolean check8(String string) {
      return string != null && this.values.contains(string);
   }

   private void invoke20() {
      if (this.values.isEmpty()) {
         this.text2 = null;
      } else {
         if (this.text2 == null || !this.values.contains(this.text2)) {
            this.text2 = this.values.iterator().next();
         }
      }
   }

   private void invoke21(float f, float g) {
      this.valuesByKey.clear();
      if (!this.values.isEmpty() && this.values.contains(this.text)) {
         for (String text8 : this.values) {
            ShaderNodeKind shaderNodeKind5 = this.shaderNode.resolve3(text8);
            if (shaderNodeKind5 != null) {
               this.valuesByKey.put(text8, new ShaderFoundryScreen.ShaderFoundryScreenData(shaderNodeKind5.getFloatValue(), shaderNodeKind5.getFloatValue2()));
            }
         }
      } else {
         ShaderNodeKind shaderNodeKind6 = this.shaderNode.resolve3(this.text);
         if (shaderNodeKind6 != null) {
            this.valuesByKey.put(shaderNodeKind6.getText(), new ShaderFoundryScreen.ShaderFoundryScreenData(shaderNodeKind6.getFloatValue(), shaderNodeKind6.getFloatValue2()));
         }
      }

      this.floatValue13 = this.measure16(f);
      this.floatValue14 = this.measure17(g);
   }

   private void invoke22(boolean bl) {
      float floatValue76 = Math.min(this.floatValue15, this.floatValue17);
      float floatValue77 = Math.min(this.floatValue16, this.floatValue18);
      float floatValue78 = Math.max(this.floatValue15, this.floatValue17);
      float floatValue79 = Math.max(this.floatValue16, this.floatValue18);
      this.values.clear();
      if (bl) {
         this.values.addAll(this.values2);
      }

      for (ShaderNodeKind shaderNodeKind7 : this.shaderNode.resolve2()) {
         ShaderNodeDefinition shaderNodeDefinition4 = this.shaderNodeRegistry.resolve(shaderNodeKind7.getText2());
         if (shaderNodeDefinition4 != null) {
            float floatValue80 = this.measure14(shaderNodeKind7.getFloatValue());
            float floatValue81 = this.measure15(shaderNodeKind7.getFloatValue2());
            float floatValue82 = shaderNodeDefinition4.getFloatValue() * this.floatValue3;
            float floatValue83 = this.measure12(shaderNodeDefinition4, shaderNodeKind7) * this.floatValue3;
            if (check9(floatValue76, floatValue77, floatValue78 - floatValue76, floatValue79 - floatValue77, floatValue80, floatValue81, floatValue82, floatValue83)) {
               this.values.add(shaderNodeKind7.getText());
            }
         }
      }

      this.invoke20();
   }

   private void invoke23() {
      if (!this.values.isEmpty()) {
         this.invoke81();
         ArrayList arrayList2 = new ArrayList<>(this.values);
         HashMap hashMap = new HashMap();
         this.values.clear();

         for (String text9 : (List<String>)arrayList2) {
            ShaderNodeKind shaderNodeKind8 = this.shaderNode.resolve3(text9);
            if (shaderNodeKind8 != null) {
               ShaderNodeKind shaderNodeKind9 = this.shaderNode
                  .resolve(shaderNodeKind8.getText2(), shaderNodeKind8.getFloatValue() + 42.0F, shaderNodeKind8.getFloatValue2() + 42.0F, this.shaderNodeRegistry);
               shaderNodeKind9.getValuesByKey().putAll(shaderNodeKind8.getValuesByKey());
               shaderNodeKind9.getValuesByKey2().putAll(shaderNodeKind8.getValuesByKey2());
               hashMap.put(text9, shaderNodeKind9.getText());
               this.values.add(shaderNodeKind9.getText());
               this.valuesByKey6.put(shaderNodeKind9.getText(), new SpringAnimation(0.0F));
            }
         }

         for (ShaderConnection shaderConnection : new ArrayList<>(this.shaderNode.getItems())) {
            String text10 = (String)hashMap.get(shaderConnection.getText());
            String text11 = (String)hashMap.get(shaderConnection.getText3());
            if (text10 != null && text11 != null) {
               this.shaderNode.check2(text10, shaderConnection.getText2(), text11, shaderConnection.getText4(), this.shaderNodeRegistry);
            }
         }

         this.invoke20();
         this.springAnimation2.invoke(1.0F);
         this.setReady("duplicated " + this.values.size());
      }
   }

   private void invoke24() {
      this.text7 = resolve5(this.text7);
      ShaderPresetStore shaderPresetStore2 = ShaderPresetStore.getINSTANCE();
      SavedShaderPreset savedShaderPreset6 = this.text6 == null ? null : shaderPresetStore2.resolve3(this.text6);
      String text12 = savedShaderPreset6 == null ? "" : savedShaderPreset6.getText2();
      this.invoke96();
      this.shaderNode.getShaderTemplate().invoke5(this.text7, ShaderPresetStore.resolve19());
      this.shaderNode.getShaderTemplate().setText(this.text7);
      this.shaderNode
         .getShaderTemplate()
         .setText2(this.shaderNode.getShaderTemplate().getText2().isBlank() ? ShaderPresetStore.resolve19() : this.shaderNode.getShaderTemplate().getText2());
      this.shaderNode.getShaderTemplate().invoke3("local");
      this.shaderNode.getShaderTemplate().setTimestamp2(System.currentTimeMillis());
      this.invoke94(this.shaderSurface);
      this.namedShaderProgram.invoke(this.shaderSurface);
      boolean flag16 = this.namedShaderProgram.check(this.text7, this.shaderNode);
      if (flag16) {
         SavedShaderPreset savedShaderPreset7 = shaderPresetStore2.resolve4(this.shaderSurface, this.shaderNode, this.text7, this.text6);
         if (savedShaderPreset7 != null) {
            this.text6 = savedShaderPreset7.getText();
            this.invoke30(savedShaderPreset7.getText2(), this.shaderNode);
         }

         if (!text12.isBlank() && !ShaderPresetRegistry.resolve21(text12).equals(ShaderPresetRegistry.resolve21(this.text7))) {
            ShaderPresetRegistry.getINSTANCE().invoke9(text12);
            ThemeShaderProgramCache.getINSTANCE().invoke2(text12);
         }

         this.intValue4 = this.shaderNode.getIntValue();
         this.setReady("saved " + this.text7);
      } else {
         this.setReady(this.namedShaderProgram.resolve2().isBlank() ? "compile failed" : this.namedShaderProgram.resolve2());
      }
   }

   private void invoke25() {
      this.text7 = this.resolve4(resolve5(this.text7));
      this.text6 = null;
      this.invoke24();
   }

   private String resolve4(String string) {
      String text13 = string != null && !string.isBlank() ? string : ShaderStylePreset.resolve();

      for (int intValue15 = 1; intValue15 < 128; intValue15++) {
         String text14 = intValue15 == 1 ? text13 + " Copy" : text13 + " Copy " + intValue15;
         boolean flag17 = false;

         for (SavedShaderPreset savedShaderPreset8 : ShaderPresetStore.getINSTANCE().resolve2(this.shaderSurface)) {
            if (savedShaderPreset8.getText2().equalsIgnoreCase(text14)) {
               flag17 = true;
               break;
            }
         }

         if (!flag17 && !ShaderPresetRegistry.getINSTANCE().check2(text14)) {
            return text14;
         }
      }

      return text13 + " Copy " + System.currentTimeMillis() % 10000L;
   }

   private void invoke26() {
      int intValue16 = ShaderPresetStore.getINSTANCE().compute(BuiltInShaderPresets.getVALUES());
      this.setReady(intValue16 == 0 ? "no legacy slots" : "cleanup " + intValue16 + " legacy");
   }

   private static boolean check9(float f, float g, float h, float i, float j, float k, float l, float m) {
      return f < j + l && f + h > j && g < k + m && g + i > k;
   }

   private static String resolve5(String string) {
      String text15 = ShaderPresetRegistry.resolve21(string);
      return text15.isBlank() ? ShaderStylePreset.resolve() : text15;
   }

   private static boolean check10(char c) {
      return Character.isLetterOrDigit(c) || c == ' ' || c == '_' || c == '-' || c == '.';
   }

   public boolean check11(ClickGuiState clickGuiState10, float f, float g) {
      if (clickGuiState10 != null && this.check(clickGuiState10)) {
         this.floatValue19 = f;
         this.floatValue20 = g;
         if (this.flag14) {
            this.floatValue31 = f - this.floatValue33;
            this.floatValue32 = g - this.floatValue34;
            return true;
         } else if (this.flag) {
            float floatValue84 = this.floatValue;
            float floatValue85 = this.floatValue2;
            this.floatValue = this.floatValue7 + f - this.floatValue5;
            this.floatValue2 = this.floatValue8 + g - this.floatValue6;
            this.invoke72(floatValue84, floatValue85);
            return true;
         } else {
            if (this.text8 != null) {
               ShaderUniformEditor shaderUniformEditor3 = this.valuesByKey3.get(this.text8);
               if (shaderUniformEditor3 != null && shaderUniformEditor3.check3(f, g, Screen.hasShiftDown())) {
                  ShaderNodeKind shaderNodeKind10 = this.shaderNode.resolve3(this.text8);
                  if (shaderNodeKind10 != null) {
                     shaderNodeKind10.invoke2("value", measure6(shaderNodeKind10, shaderUniformEditor3.getFloatValue5()));
                     this.shaderNode.invoke4();
                  }

                  return true;
               }
            }

            if (this.text9 != null) {
               ShaderUniformEditor shaderUniformEditor4 = this.valuesByKey4.get(this.text9);
               if (shaderUniformEditor4 != null && shaderUniformEditor4.check3(f, g, Screen.hasShiftDown())) {
                  return true;
               }
            }

            if (this.text10 != null) {
               ShaderUniformEditor shaderUniformEditor5 = this.valuesByKey5.get(this.text10);
               if (shaderUniformEditor5 != null && shaderUniformEditor5.check3(f, g, Screen.hasShiftDown())) {
                  this.invoke64(this.text10);
                  return true;
               }
            }

            if (this.flag2) {
               this.floatValue17 = f;
               this.floatValue18 = g;
               this.invoke22(Screen.hasShiftDown());
               return true;
            } else if (this.text == null) {
               return this.text3 != null;
            } else {
               ShaderNodeKind shaderNodeKind11 = this.shaderNode.resolve3(this.text);
               if (shaderNodeKind11 != null) {
                  if (this.valuesByKey.size() > 1 || this.valuesByKey.size() == 1 && this.valuesByKey.containsKey(shaderNodeKind11.getText())) {
                     float floatValue86 = this.measure16(f) - this.floatValue13;
                     float floatValue87 = this.measure17(g) - this.floatValue14;

                     for (Entry entry : this.valuesByKey.entrySet()) {
                        ShaderNodeKind shaderNodeKind12 = this.shaderNode.resolve3((String)entry.getKey());
                        if (shaderNodeKind12 != null) {
                           ShaderFoundryScreen.ShaderFoundryScreenData shaderFoundryScreenData = (ShaderFoundryScreen.ShaderFoundryScreenData)entry.getValue();
                           shaderNodeKind12.invoke(shaderFoundryScreenData.x + floatValue86, shaderFoundryScreenData.y + floatValue87);
                        }
                     }
                  } else {
                     shaderNodeKind11.invoke(this.measure16(f) - this.floatValue11, this.measure17(g) - this.floatValue12);
                  }

                  this.shaderNode.invoke4();
               }

               return true;
            }
         }
      } else {
         return false;
      }
   }

   public boolean check12(ClickGuiState clickGuiState11, float f, float g) {
      if (clickGuiState11 != null && this.check(clickGuiState11)) {
         if (this.text3 != null) {
            ShaderFoundryScreen.ShaderFoundryScreenData4 shaderFoundryScreenData42 = this.resolve23(f, g);
            if (shaderFoundryScreenData42 != null && shaderFoundryScreenData42.direction == ShaderPinDirection.INPUT) {
               this.invoke81();
               boolean flag18 = this.shaderNode.check2(this.text3, this.text4, shaderFoundryScreenData42.nodeId, shaderFoundryScreenData42.pinId, this.shaderNodeRegistry);
               if (flag18) {
                  this.springAnimation2.invoke(1.0F);
               }

               this.setReady(flag18 ? "linked" : "cycle / type guard");
            } else if (shaderFoundryScreenData42 == null && this.resolve21(f, g) == null) {
               ShaderNodeKind shaderNodeKind13 = this.shaderNode.resolve3(this.text3);
               if (shaderNodeKind13 != null) {
                  ShaderNodeDefinition shaderNodeDefinition5 = this.shaderNodeRegistry.resolve(shaderNodeKind13.getText2());
                  ShaderPin shaderPin2 = shaderNodeDefinition5 == null ? null : shaderNodeDefinition5.resolve2(this.text4);
                  if (shaderPin2 != null) {
                     this.text11 = this.text3;
                     this.text12 = this.text4;
                     this.shaderNodeBrowser.invoke(f, g, shaderPin2.type());
                  }
               }
            }
         }

         if (this.flag2) {
            this.floatValue17 = f;
            this.floatValue18 = g;
            this.invoke22(Screen.hasShiftDown());
         }

         if (this.text8 != null) {
            ShaderUniformEditor shaderUniformEditor6 = this.valuesByKey3.get(this.text8);
            if (shaderUniformEditor6 != null) {
               if (shaderUniformEditor6.check4(f, g)) {
                  ShaderNodeKind shaderNodeKind14 = this.shaderNode.resolve3(this.text8);
                  if (shaderNodeKind14 != null) {
                     shaderNodeKind14.invoke2("value", measure6(shaderNodeKind14, shaderUniformEditor6.getFloatValue5()));
                     this.shaderNode.invoke4();
                  }
               }

               if (!shaderUniformEditor6.check()) {
                  this.text8 = null;
               }
            }
         }

         if (this.text9 != null) {
            ShaderUniformEditor shaderUniformEditor7 = this.valuesByKey4.get(this.text9);
            if (shaderUniformEditor7 != null) {
               if (shaderUniformEditor7.check4(f, g)) {
                  ShaderNodeKind shaderNodeKind15 = this.shaderNode.resolve3(this.text9);
                  if (shaderNodeKind15 != null) {
                     shaderNodeKind15.invoke3("name", shaderUniformEditor7.getText());
                     this.shaderNode.invoke4();
                  }
               }

               if (!shaderUniformEditor7.check()) {
                  this.text9 = null;
               }
            }
         }

         if (this.text10 != null) {
            ShaderUniformEditor shaderUniformEditor8 = this.valuesByKey5.get(this.text10);
            if (shaderUniformEditor8 != null) {
               if (shaderUniformEditor8.check4(f, g)) {
                  this.invoke64(this.text10);
               }

               if (!shaderUniformEditor8.check()) {
                  this.invoke64(this.text10);
                  this.text10 = null;
               }
            }
         }

         this.flag = false;
         this.flag14 = false;
         this.text = null;
         this.valuesByKey.clear();
         this.flag2 = false;
         this.values2.clear();
         this.text3 = null;
         this.text4 = null;
         this.flag12 = false;
         return true;
      } else {
         return false;
      }
   }

   public boolean check13(ClickGuiState clickGuiState12, float f, float g, double d) {
      if (clickGuiState12 == null || !this.check(clickGuiState12)) {
         return false;
      } else if (this.shaderLibraryBrowser.isFlag()) {
         return this.shaderLibraryBrowser
            .check2(
               d,
               this.resolve45(),
               this.intValue5 <= 0 ? this.compute13() : this.intValue5,
               this.intValue6 <= 0 ? this.compute14() : this.intValue6
            );
      } else if (this.shaderNodeBrowser.isFlag()) {
         this.shaderNodeBrowser.invoke7(d);
         return true;
      } else {
         Metrics metrics15 = this.resolve45();
         int intValue17 = this.intValue5 <= 0 ? this.compute13() : this.intValue5;
         int intValue18 = this.intValue6 <= 0 ? this.compute14() : this.intValue6;
         if (this.check6(metrics15, intValue17, intValue18, f, g, d)) {
            return true;
         } else if (!this.flag8 && this.resolve32(metrics15, this.intValue6 <= 0 ? this.compute14() : this.intValue6).contains(f, g)) {
            this.floatValue23 = Math.max(0.0F, this.floatValue23 - (float)d * metrics15.measure(28.0F));
            return true;
         } else {
            float floatValue88 = (f - this.floatValue) / Math.max(0.001F, this.floatValue4);
            float floatValue89 = (g - this.floatValue2) / Math.max(0.001F, this.floatValue4);
            float floatValue90 = (float)Math.exp(d * 0.105);
            this.floatValue4 = measure21(this.floatValue4 * floatValue90, 0.34F, 2.45F);
            this.floatValue = f - floatValue88 * this.floatValue4;
            this.floatValue2 = g - floatValue89 * this.floatValue4;
            this.floatValue9 = 0.0F;
            this.floatValue10 = 0.0F;
            return true;
         }
      }
   }

   public boolean check14(ClickGuiState clickGuiState13, char c) {
      if (clickGuiState13 == null || !this.check(clickGuiState13)) {
         return false;
      } else if (this.shaderLibraryBrowser.isFlag()) {
         return this.shaderLibraryBrowser.check3(c);
      } else if (this.flag11) {
         if (check10(c) && this.text7.length() < 48) {
            this.text7 = this.text7 + c;
            this.timestamp7 = System.currentTimeMillis();
         }

         return true;
      } else if (this.shaderNodeBrowser.isFlag() && c != ' ') {
         this.shaderNodeBrowser.invoke3(c);
         return true;
      } else if (this.flag10) {
         if (check10(c) && this.text5.length() < 40) {
            this.text5 = this.text5 + c;
            this.timestamp4 = System.currentTimeMillis();
            this.floatValue23 = 0.0F;
         }

         return true;
      } else {
         if (this.text8 != null) {
            ShaderUniformEditor shaderUniformEditor9 = this.valuesByKey3.get(this.text8);
            if (shaderUniformEditor9 != null && shaderUniformEditor9.check5(c)) {
               return true;
            }
         }

         if (this.text9 != null) {
            ShaderUniformEditor shaderUniformEditor10 = this.valuesByKey4.get(this.text9);
            if (shaderUniformEditor10 != null && shaderUniformEditor10.check5(c)) {
               return true;
            }
         }

         if (this.text10 != null) {
            ShaderUniformEditor shaderUniformEditor11 = this.valuesByKey5.get(this.text10);
            if (shaderUniformEditor11 != null && shaderUniformEditor11.check5(c)) {
               return true;
            }
         }

         return false;
      }
   }

   public boolean check15(ClickGuiState clickGuiState14, int i) {
      if (clickGuiState14 == null || !this.check(clickGuiState14)) {
         return false;
      } else if (this.shaderLibraryBrowser.isFlag()) {
         boolean flag19 = this.shaderLibraryBrowser.check4(i);
         this.invoke93();
         return flag19;
      } else if (this.flag11) {
         if (i == 256) {
            this.flag11 = false;
            this.text7 = resolve5(this.text7);
            return true;
         } else if (i == 257 || i == 335 || i == 258) {
            this.flag11 = false;
            this.text7 = resolve5(this.text7);
            return true;
         } else if (i == 259) {
            if (!this.text7.isEmpty()) {
               this.text7 = this.text7.substring(0, this.text7.length() - 1);
               this.timestamp7 = System.currentTimeMillis();
            }

            return true;
         } else {
            return true;
         }
      } else if (this.shaderNodeBrowser.isFlag()) {
         if (i == 256) {
            this.shaderNodeBrowser.invoke2();
            this.text11 = null;
            this.text12 = null;
            return true;
         } else if (i == 257 || i == 335) {
            ShaderNodeDefinition shaderNodeDefinition6 = this.shaderNodeBrowser.resolve();
            if (shaderNodeDefinition6 != null) {
               this.invoke15(shaderNodeDefinition6);
            } else {
               this.shaderNodeBrowser.invoke2();
            }

            return true;
         } else if (i == 259) {
            this.shaderNodeBrowser.invoke4();
            return true;
         } else if (i == 264) {
            this.shaderNodeBrowser.invoke6(1);
            return true;
         } else if (i == 265) {
            this.shaderNodeBrowser.invoke6(-1);
            return true;
         } else {
            return true;
         }
      } else {
         if (this.text8 != null) {
            ShaderUniformEditor shaderUniformEditor12 = this.valuesByKey3.get(this.text8);
            if (shaderUniformEditor12 != null && shaderUniformEditor12.check6(i)) {
               if (!shaderUniformEditor12.isFlag()) {
                  ShaderNodeKind shaderNodeKind16 = this.shaderNode.resolve3(this.text8);
                  if (shaderNodeKind16 != null) {
                     shaderNodeKind16.invoke2("value", measure6(shaderNodeKind16, shaderUniformEditor12.getFloatValue5()));
                     this.shaderNode.invoke4();
                  }

                  this.text8 = null;
               }

               return true;
            }
         }

         if (this.text9 != null) {
            ShaderUniformEditor shaderUniformEditor13 = this.valuesByKey4.get(this.text9);
            if (shaderUniformEditor13 != null && shaderUniformEditor13.check6(i)) {
               if (!shaderUniformEditor13.isFlag()) {
                  ShaderNodeKind shaderNodeKind17 = this.shaderNode.resolve3(this.text9);
                  if (shaderNodeKind17 != null) {
                     shaderNodeKind17.invoke3("name", shaderUniformEditor13.getText());
                     this.shaderNode.invoke4();
                  }

                  this.text9 = null;
               }

               return true;
            }
         }

         if (this.text10 != null) {
            ShaderUniformEditor shaderUniformEditor14 = this.valuesByKey5.get(this.text10);
            if (shaderUniformEditor14 != null && shaderUniformEditor14.check6(i)) {
               if (!shaderUniformEditor14.isFlag()) {
                  this.invoke64(this.text10);
                  this.text10 = null;
               }

               return true;
            }
         }

         if (this.flag10) {
            if (i == 256) {
               this.text5 = "";
               this.flag10 = false;
               this.floatValue23 = 0.0F;
               return true;
            } else if (i == 257 || i == 335) {
               this.flag10 = false;
               return true;
            } else if (i == 259) {
               if (!this.text5.isEmpty()) {
                  this.text5 = this.text5.substring(0, this.text5.length() - 1);
                  this.timestamp4 = System.currentTimeMillis();
                  this.floatValue23 = 0.0F;
               }

               return true;
            } else {
               return true;
            }
         } else if (i == 32) {
            return true;
         } else if (i == 68 && Screen.hasShiftDown()) {
            this.invoke23();
            return true;
         } else if (i == 256) {
            if (!this.flag3 && !this.flag4 && !this.flag5 && !this.flag6 && !this.flag9) {
               clickGuiState14.setFlag24(false);
            } else {
               this.invoke80();
            }

            return true;
         } else if (i != 261 && i != 259) {
            if (i == 76) {
               this.flag8 = !this.flag8;
               return true;
            } else {
               if (Screen.hasControlDown()) {
                  if (i == 90) {
                     if (Screen.hasShiftDown()) {
                        this.invoke84();
                     } else {
                        this.invoke83();
                     }

                     return true;
                  }

                  if (i == 89) {
                     this.invoke84();
                     return true;
                  }

                  if (i == 83) {
                     this.invoke24();
                     return true;
                  }

                  if (i == 80) {
                     this.shaderNodeBrowser.invoke(this.floatValue19, this.floatValue20, null);
                     this.setReady("command");
                     return true;
                  }

                  if (i == 67) {
                     this.invoke91();
                     return true;
                  }

                  if (i == 86) {
                     this.invoke92();
                     return true;
                  }

                  if (i == 82) {
                     this.invoke85();
                     return true;
                  }

                  if (i == 48) {
                     this.floatValue = 520.0F;
                     this.floatValue2 = 260.0F;
                     this.floatValue4 = 0.92F;
                     this.springAnimation.invoke(this.floatValue4);
                     this.setReady("view");
                     return true;
                  }
               }

               return true;
            }
         } else {
            if (!this.values.isEmpty()) {
               this.invoke81();
               ArrayList arrayList3 = new ArrayList<>(this.values);
               boolean flag20 = false;

               for (String text16 : (List<String>)arrayList3) {
                  if (this.shaderNode.check(text16)) {
                     flag20 = true;
                     this.valuesByKey3.remove(text16);
                     this.valuesByKey4.remove(text16);
                     this.invoke19(text16);
                     this.valuesByKey9.remove(text16);
                     this.valuesByKey10.remove(text16);
                     if (text16.equals(this.text8)) {
                        this.text8 = null;
                     }

                     if (text16.equals(this.text9)) {
                        this.text9 = null;
                     }

                     if (this.text10 != null && this.text10.startsWith(text16 + ":")) {
                        this.text10 = null;
                     }
                  }
               }

               this.invoke17();
               if (flag20) {
                  this.springAnimation2.invoke(1.0F);
               }

               this.setReady("deleted");
            }

            return true;
         }
      }
   }

   private void invoke27(RenderManager renderManager13, ClickGuiState clickGuiState15, ThemeContext themeContext9, int i) {
      Metrics metrics16 = themeContext9.getMetrics();
      ColorScheme colorScheme16 = themeContext9.getColorScheme();
      float floatValue91 = metrics16.measure(34.0F);
      float floatValue92 = metrics16.measure(28.0F);
      float floatValue93 = i - metrics16.measure(68.0F);
      float floatValue94 = metrics16.measure(60.0F);
      float floatValue95 = metrics16.measure(14.0F);
      renderManager13.invoke41(floatValue91, floatValue92, floatValue93, floatValue94, floatValue95, metrics16.measure(22.0F), metrics16.measure(2.0F), this.compute4(colorScheme16, 132));
      renderManager13.invoke44(floatValue91, floatValue92, floatValue93, floatValue94, floatValue95, 0.34F);
      renderManager13.invoke5(floatValue91, floatValue92, floatValue93, floatValue94, floatValue95, this.compute2(colorScheme16, 226));
      renderManager13.invoke28(floatValue91, floatValue92, floatValue93, floatValue94, floatValue95, ColorScheme.compute6(colorScheme16.getIntValue14(), 52), 0.7F);
      renderManager13.invoke5(
         floatValue91 + metrics16.measure(1.0F),
         floatValue92 + metrics16.measure(1.0F),
         floatValue93 - metrics16.measure(2.0F),
         metrics16.measure(1.0F),
         floatValue95,
         ColorScheme.compute6(colorScheme16.getIntValue13(), 18)
      );
      renderManager13.invoke39(
         floatValue91 + metrics16.measure(20.0F), floatValue92 + floatValue94 * 0.5F, metrics16.measure(4.0F), 0.0F, 1.0F, ColorScheme.compute6(colorScheme16.getIntValue14(), 235)
      );
      ClickGuiRenderUtils.invoke4(
         renderManager13,
         metrics16,
         FontRegistry.fontObject4,
         floatValue91 + metrics16.measure(32.0F),
         floatValue92 + metrics16.measure(5.0F),
         metrics16.measure(24.0F),
         13.0F,
         "Foundry",
         this.compute11(colorScheme16)
      );
      String text17 = this.namedShaderProgram.resolve3().isBlank() ? "cold" : this.namedShaderProgram.resolve3();
      String text18 = this.namedShaderProgram.resolve2();
      String text19 = !text18.isBlank() ? text18 : this.ready;
      int intValue19 = !text18.isBlank() ? ColorScheme.compute5(255, 132, 132, 230) : ColorScheme.compute6(colorScheme16.getIntValue14(), 210);
      String text20 = this.shaderNode.getShaderTemplate().getText2().isBlank()
         ? "#" + text17 + " / " + this.shaderNode.resolve2().size() + " nodes / " + this.shaderNode.getItems().size() + " links / " + text19
         : "#"
            + text17
            + " / "
            + this.shaderNode.getShaderTemplate().getText2()
            + " / "
            + this.shaderNode.resolve2().size()
            + " nodes / "
            + this.shaderNode.getItems().size()
            + " links / "
            + text19;
      ClickGuiRenderUtils.invoke4(
         renderManager13,
         metrics16,
         FontRegistry.fontObject,
         floatValue91 + metrics16.measure(32.0F),
         floatValue92 + metrics16.measure(29.0F),
         metrics16.measure(18.0F),
         8.0F,
         ClickGuiRenderUtils.resolve4(metrics16, FontRegistry.fontObject, text20, 8.0F, metrics16.measure(160.0F)),
         intValue19
      );
      this.invoke28(renderManager13, metrics16, colorScheme16, this.resolve48(metrics16), "File", this.flag3, clickGuiState15.getFloatValue(), clickGuiState15.getFloatValue2(), 6);
      this.invoke32(renderManager13, metrics16, colorScheme16, this.resolve49(metrics16), clickGuiState15);
      this.invoke28(
         renderManager13,
         metrics16,
         colorScheme16,
         this.resolve50(metrics16),
         this.shaderSurface.getText2(),
         this.flag4,
         clickGuiState15.getFloatValue(),
         clickGuiState15.getFloatValue2(),
         1
      );
      this.invoke28(
         renderManager13, metrics16, colorScheme16, this.resolve51(metrics16, i), "Library", this.flag9, clickGuiState15.getFloatValue(), clickGuiState15.getFloatValue2(), 7
      );
      this.invoke28(
         renderManager13,
         metrics16,
         colorScheme16,
         this.resolve47(metrics16, i),
         this.shaderFoundryScreenState.getText(),
         this.flag5,
         clickGuiState15.getFloatValue(),
         clickGuiState15.getFloatValue2(),
         3
      );
      this.invoke28(renderManager13, metrics16, colorScheme16, this.resolve46(metrics16, i), "Close", false, clickGuiState15.getFloatValue(), clickGuiState15.getFloatValue2(), 4);
   }

   private void invoke28(
      RenderManager renderManager14,
      Metrics metrics17,
      ColorScheme colorScheme17,
      Rect rect29,
      String string,
      boolean bl,
      float f,
      float g,
      int i
   ) {
      float floatValue96 = rect29.contains(f, g) ? 1.0F : 0.0F;
      float floatValue97 = Math.max(bl ? 0.82F : 0.0F, floatValue96);
      int intValue20 = ColorScheme.compute7(
         ColorScheme.compute5(255, 255, 255, this.check4(colorScheme17) ? 70 : 11),
         ColorScheme.compute6(i == 2 ? colorScheme17.getIntValue15() : colorScheme17.getIntValue14(), 86),
         floatValue97
      );
      renderManager14.invoke5(rect29.x(), rect29.y(), rect29.w(), rect29.h(), metrics17.measure(8.0F), intValue20);
      renderManager14.invoke28(
         rect29.x(),
         rect29.y(),
         rect29.w(),
         rect29.h(),
         metrics17.measure(8.0F),
         ColorScheme.compute7(colorScheme17.getIntValue6(), ColorScheme.compute6(colorScheme17.getIntValue14(), 128), floatValue97),
         0.7F
      );
      this.invoke29(
         renderManager14, metrics17, colorScheme17, rect29.x() + metrics17.measure(14.0F), rect29.y() + rect29.h() * 0.5F, i, floatValue97
      );
      float floatValue98 = rect29.x() + metrics17.measure(28.0F);
      float floatValue99 = rect29.w() - metrics17.measure(36.0F);
      ClickGuiRenderUtils.invoke4(
         renderManager14,
         metrics17,
         FontRegistry.fontObject4,
         floatValue98,
         rect29.y(),
         rect29.h(),
         9.0F,
         ClickGuiRenderUtils.resolve4(metrics17, FontRegistry.fontObject4, string, 9.0F, floatValue99),
         ColorScheme.compute7(this.compute12(colorScheme17), this.compute11(colorScheme17), 0.55F + floatValue97 * 0.45F)
      );
   }

   private void invoke29(RenderManager renderManager15, Metrics metrics18, ColorScheme colorScheme18, float f, float g, int i, float h) {
      int intValue21 = ColorScheme.compute6(i == 2 ? colorScheme18.getIntValue15() : colorScheme18.getIntValue14(), Math.round(150.0F + 90.0F * h));
      float floatValue100 = metrics18.measure(5.6F);
      if (i == 0) {
         renderManager15.invoke5(f - floatValue100, g - floatValue100 * 0.65F, floatValue100 * 2.0F, floatValue100 * 1.3F, metrics18.measure(2.0F), intValue21);
         renderManager15.invoke5(f - floatValue100 * 0.7F, g - floatValue100, floatValue100 * 0.9F, metrics18.measure(2.0F), metrics18.measure(1.0F), intValue21);
      } else if (i == 1) {
         renderManager15.invoke39(f, g, floatValue100 * 0.9F, 0.0F, 1.0F, ColorScheme.compute6(intValue21, 82));
         renderManager15.invoke39(f, g, floatValue100 * 0.38F, 0.0F, 1.0F, intValue21);
      } else if (i == 2) {
         renderManager15.invoke5(f - floatValue100, g - floatValue100, floatValue100 * 0.72F, floatValue100 * 0.72F, metrics18.measure(1.5F), intValue21);
         renderManager15.invoke5(f + floatValue100 * 0.18F, g - floatValue100, floatValue100 * 0.72F, floatValue100 * 0.72F, metrics18.measure(1.5F), ColorScheme.compute6(intValue21, 170));
         renderManager15.invoke5(f - floatValue100 * 0.42F, g + floatValue100 * 0.18F, floatValue100 * 0.72F, floatValue100 * 0.72F, metrics18.measure(1.5F), ColorScheme.compute6(intValue21, 210));
      } else if (i == 3) {
         renderManager15.invoke39(f, g, floatValue100 * 0.88F, 0.0F, 1.0F, ColorScheme.compute6(intValue21, 74));
         renderManager15.invoke5(f - floatValue100, g - metrics18.measure(0.8F), floatValue100 * 2.0F, metrics18.measure(1.6F), metrics18.measure(1.0F), intValue21);
         renderManager15.invoke5(f - metrics18.measure(0.8F), g - floatValue100, metrics18.measure(1.6F), floatValue100 * 2.0F, metrics18.measure(1.0F), intValue21);
      } else if (i == 5) {
         renderManager15.invoke5(f - floatValue100 * 1.05F, g - floatValue100 * 0.78F, floatValue100 * 1.62F, metrics18.measure(1.5F), metrics18.measure(1.0F), intValue21);
         renderManager15.invoke5(
            f - floatValue100 * 0.62F,
            g - metrics18.measure(0.75F),
            floatValue100 * 1.78F,
            metrics18.measure(1.5F),
            metrics18.measure(1.0F),
            ColorScheme.compute6(intValue21, 194)
         );
         renderManager15.invoke5(
            f - floatValue100 * 1.05F, g + floatValue100 * 0.78F, floatValue100 * 1.62F, metrics18.measure(1.5F), metrics18.measure(1.0F), ColorScheme.compute6(intValue21, 155)
         );
         renderManager15.invoke39(f + floatValue100 * 1.05F, g - floatValue100 * 0.78F, metrics18.measure(1.9F), 0.0F, 1.0F, ColorScheme.compute6(intValue21, 210));
         renderManager15.invoke39(f - floatValue100 * 1.0F, g, metrics18.measure(1.9F), 0.0F, 1.0F, ColorScheme.compute6(intValue21, 170));
         renderManager15.invoke39(f + floatValue100 * 0.92F, g + floatValue100 * 0.78F, metrics18.measure(1.9F), 0.0F, 1.0F, intValue21);
      } else if (i == 6) {
         renderManager15.invoke5(f - floatValue100, g - floatValue100 * 0.82F, floatValue100 * 0.92F, metrics18.measure(2.2F), metrics18.measure(1.0F), intValue21);
         renderManager15.invoke5(f - floatValue100, g - floatValue100 * 0.42F, floatValue100 * 2.0F, floatValue100 * 1.28F, metrics18.measure(1.6F), ColorScheme.compute6(intValue21, 210));
         renderManager15.invoke5(
            f - floatValue100 * 0.74F,
            g - floatValue100 * 0.12F,
            floatValue100 * 1.48F,
            metrics18.measure(1.1F),
            metrics18.measure(0.5F),
            ColorScheme.compute6(colorScheme18.getIntValue13(), 96)
         );
      } else if (i == 7) {
         renderManager15.invoke5(f - floatValue100, g - floatValue100, floatValue100 * 0.82F, floatValue100 * 0.82F, metrics18.measure(1.4F), intValue21);
         renderManager15.invoke5(f + floatValue100 * 0.18F, g - floatValue100, floatValue100 * 0.82F, floatValue100 * 0.82F, metrics18.measure(1.4F), ColorScheme.compute6(intValue21, 176));
         renderManager15.invoke5(
            f - floatValue100, g + floatValue100 * 0.18F, floatValue100 * 2.0F, metrics18.measure(1.5F), metrics18.measure(0.8F), ColorScheme.compute6(intValue21, 214)
         );
         renderManager15.invoke5(
            f - floatValue100, g + floatValue100 * 0.66F, floatValue100 * 1.44F, metrics18.measure(1.5F), metrics18.measure(0.8F), ColorScheme.compute6(intValue21, 150)
         );
      } else {
         renderManager15.invoke56(f, g);
         renderManager15.invoke54(45.0F);
         renderManager15.invoke5(-floatValue100, -metrics18.measure(0.8F), floatValue100 * 2.0F, metrics18.measure(1.6F), metrics18.measure(1.0F), intValue21);
         renderManager15.invoke55();
         renderManager15.invoke54(-45.0F);
         renderManager15.invoke5(-floatValue100, -metrics18.measure(0.8F), floatValue100 * 2.0F, metrics18.measure(1.6F), metrics18.measure(1.0F), intValue21);
         renderManager15.invoke55();
         renderManager15.invoke57();
      }
   }

   private void invoke30(String string, ShaderNode shaderNode3) {
      if (string != null && !string.isBlank() && shaderNode3 != null) {
         if (shaderNode3 == this.shaderNode) {
            this.invoke96();
         }

         ShaderBuildResult shaderBuildResult = this.shaderSourceBuilder.resolve2(shaderNode3);
         ShaderPresetRegistry.getINSTANCE().invoke3(string, shaderNode3, shaderBuildResult, this.resolve6(shaderNode3));
      }
   }

   private void invoke31(SavedShaderPreset savedShaderPreset9) {
      if (savedShaderPreset9 != null) {
         ShaderSurface shaderSurface4 = ShaderSurface.resolve4(savedShaderPreset9.getText3());
         if (shaderSurface4 == ShaderSurface.PREVIEW_ONLY) {
            this.setReady("preview-only slot");
         } else {
            ShaderPresetStore shaderPresetStore3 = ShaderPresetStore.getINSTANCE();
            if (savedShaderPreset9.getText().equals(shaderPresetStore3.resolve12(shaderSurface4))) {
               this.invoke33(shaderSurface4);
            } else {
               ShaderNode shaderNode4 = shaderPresetStore3.resolve11(savedShaderPreset9.getText(), this.shaderNodeRegistry);
               if (shaderNode4 == null) {
                  this.setReady("slot load failed");
               } else {
                  shaderNode4.invoke2(shaderSurface4.getText());
                  ShaderBuildResult shaderBuildResult2 = this.shaderSourceBuilder.resolve2(shaderNode4);
                  ShaderPresetRegistry.getINSTANCE().invoke3(savedShaderPreset9.getText2(), shaderNode4, shaderBuildResult2, this.resolve7(savedShaderPreset9));
                  ShaderPresetRegistry.getINSTANCE().invoke(shaderSurface4, shaderNode4, shaderBuildResult2);
                  ThemeShaderProgramCache.getINSTANCE().resolve2(shaderSurface4, shaderBuildResult2);
                  shaderPresetStore3.invoke2(shaderSurface4, savedShaderPreset9.getText());
                  FoundryShaderSetting.invoke(shaderSurface4, savedShaderPreset9.getText2());
                  this.setReady("bound " + shaderSurface4.getText2());
               }
            }
         }
      }
   }

   private ShaderPresetRegistry.ShaderPresetRegistryState2 resolve6(ShaderNode shaderNode5) {
      if (shaderNode5 != null && shaderNode5.getShaderTemplate() != null) {
         String text21 = shaderNode5.getShaderTemplate().getLocal();
         if ("preset".equalsIgnoreCase(text21)) {
            return ShaderPresetRegistry.ShaderPresetRegistryState2.PRESET;
         } else {
            return !"imported".equalsIgnoreCase(text21) && !"shared".equalsIgnoreCase(text21) ? ShaderPresetRegistry.ShaderPresetRegistryState2.USER : ShaderPresetRegistry.ShaderPresetRegistryState2.IMPORTED;
         }
      } else {
         return ShaderPresetRegistry.ShaderPresetRegistryState2.USER;
      }
   }

   private ShaderPresetRegistry.ShaderPresetRegistryState2 resolve7(SavedShaderPreset savedShaderPreset10) {
      if (savedShaderPreset10 == null) {
         return ShaderPresetRegistry.ShaderPresetRegistryState2.USER;
      } else {
         String text22 = savedShaderPreset10.getText8();
         if ("preset".equalsIgnoreCase(text22)) {
            return ShaderPresetRegistry.ShaderPresetRegistryState2.PRESET;
         } else {
            return !"imported".equalsIgnoreCase(text22) && !"shared".equalsIgnoreCase(text22) ? ShaderPresetRegistry.ShaderPresetRegistryState2.USER : ShaderPresetRegistry.ShaderPresetRegistryState2.IMPORTED;
         }
      }
   }

   private void invoke32(
      RenderManager renderManager16, Metrics metrics19, ColorScheme colorScheme19, Rect rect30, ClickGuiState clickGuiState16
   ) {
      float floatValue101 = !rect30.contains(clickGuiState16.getFloatValue(), clickGuiState16.getFloatValue2()) && !this.flag11 ? 0.0F : 1.0F;
      int intValue22 = ColorScheme.compute7(
         ColorScheme.compute5(255, 255, 255, this.check4(colorScheme19) ? 76 : 14), ColorScheme.compute6(colorScheme19.getIntValue14(), 64), floatValue101
      );
      renderManager16.invoke5(rect30.x(), rect30.y(), rect30.w(), rect30.h(), metrics19.measure(7.0F), intValue22);
      renderManager16.invoke28(
         rect30.x(),
         rect30.y(),
         rect30.w(),
         rect30.h(),
         metrics19.measure(7.0F),
         ColorScheme.compute7(colorScheme19.getIntValue6(), ColorScheme.compute6(colorScheme19.getIntValue14(), 134), floatValue101),
         this.flag11 ? 1.0F : 0.7F
      );
      String text23 = this.text7 != null && !this.text7.isBlank() ? this.text7 : "Shader name";
      int intValue23 = this.text7 != null && !this.text7.isBlank() ? this.compute11(colorScheme19) : this.compute12(colorScheme19);
      renderManager16.invoke20();
      renderManager16.invoke24(
         rect30.x() + metrics19.measure(8.0F),
         rect30.y(),
         rect30.w() - metrics19.measure(16.0F),
         rect30.h(),
         metrics19.measure(6.0F),
         metrics19.measure(6.0F),
         metrics19.measure(6.0F),
         metrics19.measure(6.0F)
      );

      try {
         ClickGuiRenderUtils.invoke4(
            renderManager16,
            metrics19,
            FontRegistry.fontObject,
            rect30.x() + metrics19.measure(10.0F),
            rect30.y(),
            rect30.h(),
            10.0F,
            text23,
            intValue23
         );
         if (this.flag11 && (System.currentTimeMillis() - this.timestamp7) / 500L % 2L == 0L) {
            float floatValue102 = ClickGuiRenderUtils.measure2(metrics19, FontRegistry.fontObject, text23, 10.0F);
            float floatValue103 = Math.min(
               rect30.x() + rect30.w() - metrics19.measure(12.0F),
               rect30.x() + metrics19.measure(10.0F) + floatValue102 + metrics19.measure(2.0F)
            );
            renderManager16.invoke5(
               floatValue103,
               rect30.y() + metrics19.measure(6.0F),
               1.0F,
               rect30.h() - metrics19.measure(12.0F),
               0.0F,
               ColorScheme.compute6(colorScheme19.getIntValue14(), 240)
            );
         }
      } finally {
         renderManager16.invoke20();
         renderManager16.invoke25();
      }
   }

   private void invoke33(ShaderSurface shaderSurface5) {
      if (shaderSurface5 != null) {
         ShaderPresetRegistry.getINSTANCE().invoke8(shaderSurface5);
         ThemeShaderProgramCache.getINSTANCE().invoke(shaderSurface5);
         ShaderPresetStore.getINSTANCE().invoke2(shaderSurface5, null);
         FoundryShaderSetting.invoke2(shaderSurface5);
         this.setReady(shaderSurface5.getText2() + " unbound");
      }
   }

   private void invoke34(RenderManager renderManager17, ClickGuiState clickGuiState17, ThemeContext themeContext10, int i, int j) {
      float floatValue104 = this.clampedSpringAnimation.measure();
      if (this.flag3 || !(floatValue104 <= 0.01F)) {
         Metrics metrics20 = themeContext10.getMetrics();
         ColorScheme colorScheme20 = themeContext10.getColorScheme();
         Rect rect31 = this.resolve61(metrics20);
         rect31 = new Rect(rect31.x(), rect31.y() - metrics20.measure(9.0F) * (1.0F - floatValue104), rect31.w(), rect31.h());
         float floatValue105 = metrics20.measure(14.0F);
         renderManager17.invoke65(floatValue104);

         try {
            renderManager17.invoke41(rect31.x(), rect31.y(), rect31.w(), rect31.h(), floatValue105, metrics20.measure(24.0F), metrics20.measure(2.0F), this.compute4(colorScheme20, 142));
            renderManager17.invoke5(rect31.x(), rect31.y(), rect31.w(), rect31.h(), floatValue105, this.compute2(colorScheme20, 236));
            renderManager17.invoke28(rect31.x(), rect31.y(), rect31.w(), rect31.h(), floatValue105, ColorScheme.compute6(colorScheme20.getIntValue14(), 82), 0.8F);
            ClickGuiRenderUtils.invoke3(
               renderManager17,
               metrics20,
               FontRegistry.fontObject4,
               rect31.x() + metrics20.measure(12.0F),
               rect31.y() + metrics20.measure(12.0F),
               12.0F,
               "File",
               this.compute11(colorScheme20)
            );
            ClickGuiRenderUtils.invoke3(
               renderManager17,
               metrics20,
               FontRegistry.fontObject,
               rect31.x() + metrics20.measure(12.0F),
               rect31.y() + metrics20.measure(28.0F),
               8.0F,
               "autosave on / Ctrl+S saves the named slot",
               ColorScheme.compute6(colorScheme20.getIntValue14(), 190)
            );
            float floatValue106 = rect31.y() + metrics20.measure(48.0F);
            SavedShaderPreset savedShaderPreset11 = this.text6 == null ? null : ShaderPresetStore.getINSTANCE().resolve3(this.text6);
            this.invoke40(renderManager17, metrics20, colorScheme20, rect31.x() + metrics20.measure(12.0F), floatValue106, "File", savedShaderPreset11 == null ? "unsaved" : savedShaderPreset11.getText2());
            this.invoke40(renderManager17, metrics20, colorScheme20, rect31.x() + metrics20.measure(12.0F), floatValue106 + metrics20.measure(19.0F), "State", this.resolve2());
            this.invoke40(renderManager17, metrics20, colorScheme20, rect31.x() + metrics20.measure(12.0F), floatValue106 + metrics20.measure(38.0F), "Target", this.shaderSurface.getText2());
            this.invoke40(
               renderManager17,
               metrics20,
               colorScheme20,
               rect31.x() + metrics20.measure(12.0F),
               floatValue106 + metrics20.measure(57.0F),
               "Uniforms",
               String.valueOf(this.shaderSourceBuilder.resolve2(this.shaderNode).exposedUniforms().size())
            );
            this.invoke40(
               renderManager17,
               metrics20,
               colorScheme20,
               rect31.x() + metrics20.measure(12.0F),
               floatValue106 + metrics20.measure(76.0F),
               "Source",
               this.shaderNode.getShaderTemplate().getLocal()
            );
            SavedShaderPreset savedShaderPreset12 = ShaderPresetStore.getINSTANCE().resolve13(this.shaderSurface);
            this.invoke40(
               renderManager17, metrics20, colorScheme20, rect31.x() + metrics20.measure(12.0F), floatValue106 + metrics20.measure(95.0F), "Bound", savedShaderPreset12 == null ? "-" : savedShaderPreset12.getText2()
            );

            for (int intValue24 = 0; intValue24 < SAVE_AS.length; intValue24++) {
               Rect rect32 = this.resolve62(rect31, metrics20, intValue24);
               this.invoke41(
                  renderManager17, metrics20, colorScheme20, rect32, SAVE_AS[intValue24], clickGuiState17.getFloatValue(), clickGuiState17.getFloatValue2(), intValue24 == 0 || intValue24 == 1
               );
            }
         } finally {
            renderManager17.invoke66();
         }
      }
   }

   private void invoke35(RenderManager renderManager18, ClickGuiState clickGuiState18, ThemeContext themeContext11, int i, int j) {
      float floatValue107 = this.clampedSpringAnimation2.measure();
      if (this.flag4 || !(floatValue107 <= 0.01F)) {
         Metrics metrics21 = themeContext11.getMetrics();
         ColorScheme colorScheme21 = themeContext11.getColorScheme();
         Rect rect33 = this.resolve63(metrics21, i, j);
         rect33 = new Rect(rect33.x(), rect33.y() - metrics21.measure(10.0F) * (1.0F - floatValue107), rect33.w(), rect33.h());
         float floatValue108 = metrics21.measure(14.0F);
         renderManager18.invoke65(floatValue107);
         boolean flag21 = false ;

         try {
            flag21 = true;
            renderManager18.invoke41(rect33.x(), rect33.y(), rect33.w(), rect33.h(), floatValue108, metrics21.measure(24.0F), metrics21.measure(2.0F), this.compute4(colorScheme21, 148));
            renderManager18.invoke5(rect33.x(), rect33.y(), rect33.w(), rect33.h(), floatValue108, this.compute2(colorScheme21, 238));
            renderManager18.invoke28(rect33.x(), rect33.y(), rect33.w(), rect33.h(), floatValue108, ColorScheme.compute6(colorScheme21.getIntValue14(), 90), 0.8F);
            ClickGuiRenderUtils.invoke3(
               renderManager18,
               metrics21,
               FontRegistry.fontObject4,
               rect33.x() + metrics21.measure(16.0F),
               rect33.y() + metrics21.measure(14.0F),
               12.0F,
               "Target Studio",
               this.compute11(colorScheme21)
            );
            ClickGuiRenderUtils.invoke3(
               renderManager18,
               metrics21,
               FontRegistry.fontObject,
               rect33.x() + metrics21.measure(16.0F),
               rect33.y() + metrics21.measure(31.0F),
               8.0F,
               "pick where this shader runs — click a target to edit it",
               ColorScheme.compute6(colorScheme21.getIntValue14(), 190)
            );
            ShaderSurface[] shaderSurfaces = ShaderSurface.resolve3();

            for (int intValue25 = 0; intValue25 < shaderSurfaces.length; intValue25++) {
               this.invoke39(
                  renderManager18, metrics21, colorScheme21, this.resolve64(rect33, metrics21, intValue25), shaderSurfaces[intValue25], clickGuiState18.getFloatValue(), clickGuiState18.getFloatValue2()
               );
            }

            float floatValue109 = rect33.y() + rect33.h() - metrics21.measure(76.0F);
            ClickGuiRenderUtils.invoke3(
               renderManager18,
               metrics21,
               FontRegistry.fontObject4,
               rect33.x() + metrics21.measure(16.0F),
               floatValue109,
               9.0F,
               "Shape Source",
               ColorScheme.compute6(colorScheme21.getIntValue15(), 220)
            );
            String[] texts = new String[]{"Host Rectangle", "Inset Shape", "Full Quad"};

            for (int intValue26 = 0; intValue26 < texts.length; intValue26++) {
               Rect rect34 = this.resolve65(rect33, metrics21, intValue26);
               this.invoke43(
                  renderManager18,
                  metrics21,
                  colorScheme21,
                  rect34,
                  texts[intValue26],
                  this.hostRectangle.equals(texts[intValue26]),
                  clickGuiState18.getFloatValue(),
                  clickGuiState18.getFloatValue2()
               );
            }

            flag21 = false;
         } finally {
            if (flag21) {
               renderManager18.invoke66();
            }
         }

         renderManager18.invoke66();
      }
   }

   private void invoke36() {
      if (!this.flag7) {
         this.flag7 = true;

         for (int intValue27 = 0; intValue27 < ShaderSurfaceTemplates.ITEMS.size(); intValue27++) {
            try {
               ShaderNode shaderNode6 = ShaderSurfaceTemplates.resolve(ShaderSurfaceTemplates.ITEMS.get(intValue27), this.shaderNodeRegistry);
               this.valuesByKey2.put(intValue27, this.shaderSourceBuilder.resolve2(shaderNode6));
            } catch (Throwable exception) {
            }
         }
      }
   }

   private void invoke37(RenderManager renderManager19, ClickGuiState clickGuiState19, ThemeContext themeContext12, int i, int j) {
      float floatValue110 = this.clampedSpringAnimation3.measure();
      if (this.flag5 || !(floatValue110 <= 0.01F)) {
         Metrics metrics22 = themeContext12.getMetrics();
         ColorScheme colorScheme22 = themeContext12.getColorScheme();
         Rect rect35 = this.resolve66(metrics22, i);
         rect35 = new Rect(rect35.x() + metrics22.measure(10.0F) * (1.0F - floatValue110), rect35.y(), rect35.w(), rect35.h());
         float floatValue111 = metrics22.measure(14.0F);
         renderManager19.invoke65(floatValue110);

         try {
            renderManager19.invoke41(rect35.x(), rect35.y(), rect35.w(), rect35.h(), floatValue111, metrics22.measure(22.0F), metrics22.measure(2.0F), this.compute4(colorScheme22, 136));
            renderManager19.invoke5(rect35.x(), rect35.y(), rect35.w(), rect35.h(), floatValue111, this.compute2(colorScheme22, 236));
            renderManager19.invoke28(rect35.x(), rect35.y(), rect35.w(), rect35.h(), floatValue111, ColorScheme.compute6(colorScheme22.getIntValue14(), 84), 0.8F);
            ClickGuiRenderUtils.invoke3(
               renderManager19,
               metrics22,
               FontRegistry.fontObject4,
               rect35.x() + metrics22.measure(16.0F),
               rect35.y() + metrics22.measure(14.0F),
               12.0F,
               "Settings",
               this.compute11(colorScheme22)
            );
            ClickGuiRenderUtils.invoke3(
               renderManager19,
               metrics22,
               FontRegistry.fontObject,
               rect35.x() + metrics22.measure(16.0F),
               rect35.y() + metrics22.measure(32.0F),
               8.0F,
               "core editor behavior",
               ColorScheme.compute6(colorScheme22.getIntValue14(), 184)
            );
            ClickGuiRenderUtils.invoke3(
               renderManager19,
               metrics22,
               FontRegistry.fontObject4,
               rect35.x() + metrics22.measure(16.0F),
               rect35.y() + metrics22.measure(64.0F),
               9.0F,
               "Foundry Theme",
               ColorScheme.compute6(colorScheme22.getIntValue15(), 220)
            );
            ShaderFoundryScreen.ShaderFoundryScreenState[] w292s = ShaderFoundryScreen.ShaderFoundryScreenState.values();

            for (int intValue28 = 0; intValue28 < w292s.length; intValue28++) {
               this.invoke43(
                  renderManager19,
                  metrics22,
                  colorScheme22,
                  this.resolve67(rect35, metrics22, intValue28),
                  w292s[intValue28].getText(),
                  this.shaderFoundryScreenState == w292s[intValue28],
                  clickGuiState19.getFloatValue(),
                  clickGuiState19.getFloatValue2()
               );
            }

            ClickGuiRenderUtils.invoke3(
               renderManager19,
               metrics22,
               FontRegistry.fontObject4,
               rect35.x() + metrics22.measure(16.0F),
               rect35.y() + metrics22.measure(118.0F),
               9.0F,
               "Shader Properties",
               ColorScheme.compute6(colorScheme22.getIntValue15(), 220)
            );
            this.invoke40(
               renderManager19,
               metrics22,
               colorScheme22,
               rect35.x() + metrics22.measure(16.0F),
               rect35.y() + metrics22.measure(140.0F),
               "Complexity",
               this.shaderNode.getShaderTemplate().getCustom()
            );
            this.invoke40(
               renderManager19,
               metrics22,
               colorScheme22,
               rect35.x() + metrics22.measure(16.0F),
               rect35.y() + metrics22.measure(162.0F),
               "Uniforms",
               String.valueOf(this.shaderSourceBuilder.resolve2(this.shaderNode).exposedUniforms().size())
            );
            this.invoke40(renderManager19, metrics22, colorScheme22, rect35.x() + metrics22.measure(16.0F), rect35.y() + metrics22.measure(184.0F), "Shape", this.hostRectangle);
         } finally {
            renderManager19.invoke66();
         }
      }
   }

   private void invoke38(RenderManager renderManager20, ClickGuiState clickGuiState20, ThemeContext themeContext13, int i, int j) {
      float floatValue112 = this.clampedSpringAnimation4.measure();
      if ((this.flag6 || !(floatValue112 <= 0.01F)) && this.shaderSurface2 != null) {
         Metrics metrics23 = themeContext13.getMetrics();
         ColorScheme colorScheme23 = themeContext13.getColorScheme();
         Rect rect36 = this.resolve68(metrics23, i, j);
         float floatValue113 = metrics23.measure(14.0F);
         renderManager20.invoke65(floatValue112);

         try {
            renderManager20.invoke5(0.0F, 0.0F, (float)i, (float)j, 0.0F, ColorScheme.compute5(0, 0, 0, Math.round((this.check4(colorScheme23) ? 42 : 82) * floatValue112)));
            renderManager20.invoke41(rect36.x(), rect36.y(), rect36.w(), rect36.h(), floatValue113, metrics23.measure(26.0F), metrics23.measure(2.0F), this.compute4(colorScheme23, 172));
            renderManager20.invoke5(rect36.x(), rect36.y(), rect36.w(), rect36.h(), floatValue113, this.compute2(colorScheme23, 248));
            renderManager20.invoke28(rect36.x(), rect36.y(), rect36.w(), rect36.h(), floatValue113, ColorScheme.compute6(colorScheme23.getIntValue14(), 128), 0.9F);
            ClickGuiRenderUtils.invoke3(
               renderManager20,
               metrics23,
               FontRegistry.fontObject4,
               rect36.x() + metrics23.measure(18.0F),
               rect36.y() + metrics23.measure(16.0F),
               12.0F,
               "Switch Target",
               this.compute11(colorScheme23)
            );
            ClickGuiRenderUtils.invoke3(
               renderManager20,
               metrics23,
               FontRegistry.fontObject,
               rect36.x() + metrics23.measure(18.0F),
               rect36.y() + metrics23.measure(38.0F),
               9.0F,
               "Current graph has unsaved changes. Save before switching to " + this.shaderSurface2.getText2() + ".",
               this.compute12(colorScheme23)
            );
            this.invoke41(
               renderManager20, metrics23, colorScheme23, this.resolve69(rect36, metrics23), "Save & Switch", clickGuiState20.getFloatValue(), clickGuiState20.getFloatValue2(), true
            );
            this.invoke41(renderManager20, metrics23, colorScheme23, this.resolve70(rect36, metrics23), "Switch", clickGuiState20.getFloatValue(), clickGuiState20.getFloatValue2(), false);
            this.invoke41(renderManager20, metrics23, colorScheme23, this.resolve71(rect36, metrics23), "Cancel", clickGuiState20.getFloatValue(), clickGuiState20.getFloatValue2(), false);
         } finally {
            renderManager20.invoke66();
         }
      }
   }

   private void invoke39(
      RenderManager renderManager21,
      Metrics metrics24,
      ColorScheme colorScheme24,
      Rect rect37,
      ShaderSurface shaderSurface6,
      float f,
      float g
   ) {
      boolean flag22 = rect37.contains(f, g);
      boolean flag23 = shaderSurface6 == this.shaderSurface;
      SavedShaderPreset savedShaderPreset13 = ShaderPresetStore.getINSTANCE().resolve13(shaderSurface6);
      boolean flag24 = ShaderPresetRegistry.getINSTANCE().check(shaderSurface6);
      float floatValue114 = Math.max(flag23 ? 0.82F : 0.0F, flag22 ? 0.7F : 0.0F);
      renderManager21.invoke5(
         rect37.x(),
         rect37.y(),
         rect37.w(),
         rect37.h(),
         metrics24.measure(8.0F),
         ColorScheme.compute7(
            ColorScheme.compute5(255, 255, 255, this.check4(colorScheme24) ? 52 : 8), ColorScheme.compute6(colorScheme24.getIntValue14(), 74), floatValue114
         )
      );
      renderManager21.invoke28(
         rect37.x(),
         rect37.y(),
         rect37.w(),
         rect37.h(),
         metrics24.measure(8.0F),
         ColorScheme.compute7(
            colorScheme24.getIntValue6(), ColorScheme.compute6(flag23 ? colorScheme24.getIntValue14() : colorScheme24.getIntValue15(), flag23 ? 150 : 96), floatValue114
         ),
         flag23 ? 0.9F : 0.6F
      );
      int intValue29 = flag23 ? colorScheme24.getIntValue14() : ColorScheme.compute5(120, 230, 150, 255);
      renderManager21.invoke39(
         rect37.x() + metrics24.measure(15.0F), rect37.y() + metrics24.measure(15.0F), metrics24.measure(3.1F), 0.0F, 1.0F, intValue29
      );
      if (flag23) {
         renderManager21.invoke40(
            rect37.x() + metrics24.measure(15.0F),
            rect37.y() + metrics24.measure(15.0F),
            metrics24.measure(5.4F),
            0.0F,
            1.0F,
            0.9F,
            ColorScheme.compute6(colorScheme24.getIntValue14(), 150)
         );
      }

      ClickGuiRenderUtils.invoke3(
         renderManager21,
         metrics24,
         FontRegistry.fontObject4,
         rect37.x() + metrics24.measure(26.0F),
         rect37.y() + metrics24.measure(8.0F),
         10.0F,
         shaderSurface6.getText2(),
         this.compute11(colorScheme24)
      );
      ClickGuiRenderUtils.invoke3(
         renderManager21,
         metrics24,
         FontRegistry.fontObject,
         rect37.x() + metrics24.measure(26.0F),
         rect37.y() + metrics24.measure(24.0F),
         7.5F,
         ClickGuiRenderUtils.resolve4(metrics24, FontRegistry.fontObject, this.resolve9(shaderSurface6), 7.5F, rect37.w() - metrics24.measure(96.0F)),
         this.compute12(colorScheme24)
      );
      if (flag24) {
         String text24 = savedShaderPreset13 == null ? "runtime" : savedShaderPreset13.getText2();
         Rect rect38 = this.resolve8(rect37, metrics24);
         ClickGuiRenderUtils.invoke3(
            renderManager21,
            metrics24,
            FontRegistry.fontObject,
            rect37.x() + rect37.w() - metrics24.measure(88.0F),
            rect37.y() + metrics24.measure(25.0F),
            7.0F,
            ClickGuiRenderUtils.resolve4(metrics24, FontRegistry.fontObject, "◆ " + text24, 7.0F, metrics24.measure(44.0F)),
            ColorScheme.compute6(colorScheme24.getIntValue15(), 210)
         );
         boolean flag25 = rect38.contains(f, g);
         renderManager21.invoke5(
            rect38.x(),
            rect38.y(),
            rect38.w(),
            rect38.h(),
            metrics24.measure(5.0F),
            ColorScheme.compute7(ColorScheme.compute6(colorScheme24.getIntValue13(), 24), ColorScheme.compute5(220, 80, 92, 126), flag25 ? 1.0F : 0.0F)
         );
         renderManager21.invoke28(
            rect38.x(),
            rect38.y(),
            rect38.w(),
            rect38.h(),
            metrics24.measure(5.0F),
            ColorScheme.compute6(flag25 ? -33652 : colorScheme24.getIntValue13(), flag25 ? 220 : 72),
            0.58F
         );
         float floatValue115 = ClickGuiRenderUtils.measure2(metrics24, FontRegistry.fontObject4, "Off", 8.0F);
         ClickGuiRenderUtils.invoke4(
            renderManager21,
            metrics24,
            FontRegistry.fontObject4,
            rect38.x() + (rect38.w() - floatValue115) * 0.5F,
            rect38.y(),
            rect38.h(),
            8.0F,
            "Off",
            flag25 ? colorScheme24.getIntValue13() : colorScheme24.getIntValue12()
         );
      }
   }

   private Rect resolve8(Rect rect39, Metrics metrics25) {
      return new Rect(
         rect39.x() + rect39.w() - metrics25.measure(44.0F),
         rect39.y() + rect39.h() - metrics25.measure(20.0F),
         metrics25.measure(36.0F),
         metrics25.measure(15.0F)
      );
   }

   private String resolve9(ShaderSurface shaderSurface7) {
      return switch (shaderSurface7) {
         case HUD -> "Drives HUD element plates";
         case BACKGROUND -> "Drives the ClickGUI background";
         case ESP -> "Drives the TargetESP entity fill";
         default -> shaderSurface7.getText3();
      };
   }

   private void invoke40(RenderManager renderManager22, Metrics metrics26, ColorScheme colorScheme25, float f, float g, String string, String string2) {
      ClickGuiRenderUtils.invoke3(renderManager22, metrics26, FontRegistry.fontObject, f, g, 8.0F, string, colorScheme25.getIntValue12());
      ClickGuiRenderUtils.invoke3(
         renderManager22,
         metrics26,
         FontRegistry.fontObject4,
         f + metrics26.measure(82.0F),
         g - metrics26.measure(1.0F),
         9.0F,
         string2 != null && !string2.isBlank() ? string2 : "-",
         this.compute11(colorScheme25)
      );
   }

   private void invoke41(
      RenderManager renderManager23,
      Metrics metrics27,
      ColorScheme colorScheme26,
      Rect rect40,
      String string,
      float f,
      float g,
      boolean bl
   ) {
      float floatValue116 = rect40.contains(f, g) ? 1.0F : 0.0F;
      int intValue30 = ColorScheme.compute5(255, 255, 255, this.check4(colorScheme26) ? 72 : 12);
      int intValue31 = ColorScheme.compute6(bl ? colorScheme26.getIntValue14() : colorScheme26.getIntValue15(), 88);
      renderManager23.invoke5(
         rect40.x(), rect40.y(), rect40.w(), rect40.h(), metrics27.measure(8.0F), ColorScheme.compute7(intValue30, intValue31, floatValue116)
      );
      renderManager23.invoke28(
         rect40.x(),
         rect40.y(),
         rect40.w(),
         rect40.h(),
         metrics27.measure(8.0F),
         ColorScheme.compute7(
            colorScheme26.getIntValue6(), ColorScheme.compute6(bl ? colorScheme26.getIntValue14() : colorScheme26.getIntValue15(), 122), floatValue116
         ),
         0.7F
      );
      int intValue32 = this.compute5(string);
      if (intValue32 >= 0 && rect40.w() > metrics27.measure(78.0F)) {
         this.invoke42(
            renderManager23,
            metrics27,
            colorScheme26,
            rect40.x() + metrics27.measure(14.0F),
            rect40.y() + rect40.h() * 0.5F,
            intValue32,
            bl,
            floatValue116
         );
         ClickGuiRenderUtils.invoke4(
            renderManager23,
            metrics27,
            FontRegistry.fontObject4,
            rect40.x() + metrics27.measure(28.0F),
            rect40.y(),
            rect40.h(),
            9.0F,
            ClickGuiRenderUtils.resolve4(metrics27, FontRegistry.fontObject4, string, 9.0F, rect40.w() - metrics27.measure(36.0F)),
            this.compute11(colorScheme26)
         );
      } else {
         float floatValue117 = ClickGuiRenderUtils.measure2(metrics27, FontRegistry.fontObject4, string, 9.0F);
         ClickGuiRenderUtils.invoke4(
            renderManager23,
            metrics27,
            FontRegistry.fontObject4,
            rect40.x() + (rect40.w() - floatValue117) * 0.5F,
            rect40.y(),
            rect40.h(),
            9.0F,
            string,
            this.compute11(colorScheme26)
         );
      }
   }

   private int compute5(String string) {
      if (string == null) {
         return -1;
      } else if (string.startsWith("Save")) {
         return 0;
      } else if (string.startsWith("Slots")) {
         return 1;
      } else if (string.startsWith("Export")) {
         return 2;
      } else if (string.startsWith("Import")) {
         return 3;
      } else if (string.startsWith("Open")) {
         return 4;
      } else if (string.startsWith("Reset")) {
         return 5;
      } else if (string.startsWith("Use")) {
         return 6;
      } else if (string.startsWith("Merge")) {
         return 7;
      } else if (string.startsWith("Cleanup")) {
         return 8;
      } else if (string.startsWith("Switch")) {
         return 9;
      } else {
         return string.startsWith("Cancel") ? 10 : -1;
      }
   }

   private void invoke42(RenderManager renderManager24, Metrics metrics28, ColorScheme colorScheme27, float f, float g, int i, boolean bl, float h) {
      int intValue33 = ColorScheme.compute6(bl ? colorScheme27.getIntValue14() : colorScheme27.getIntValue15(), Math.round(150.0F + 90.0F * h));
      float floatValue118 = metrics28.measure(5.2F);
      if (i == 0) {
         renderManager24.invoke5(f - floatValue118, g - floatValue118, floatValue118 * 2.0F, floatValue118 * 2.0F, metrics28.measure(1.8F), intValue33);
         renderManager24.invoke5(
            f - floatValue118 * 0.58F,
            g + floatValue118 * 0.1F,
            floatValue118 * 1.16F,
            floatValue118 * 0.52F,
            metrics28.measure(1.0F),
            ColorScheme.compute6(colorScheme27.getIntValue13(), 115)
         );
      } else if (i == 1) {
         renderManager24.invoke5(f - floatValue118, g - floatValue118, floatValue118 * 0.78F, floatValue118 * 0.78F, metrics28.measure(1.6F), intValue33);
         renderManager24.invoke5(f + floatValue118 * 0.22F, g - floatValue118, floatValue118 * 0.78F, floatValue118 * 0.78F, metrics28.measure(1.6F), ColorScheme.compute6(intValue33, 160));
         renderManager24.invoke5(f - floatValue118, g + floatValue118 * 0.22F, floatValue118 * 0.78F, floatValue118 * 0.78F, metrics28.measure(1.6F), ColorScheme.compute6(intValue33, 200));
      } else if (i == 2 || i == 3) {
         float floatValue119 = i == 2 ? -1.0F : 1.0F;
         renderManager24.invoke5(
            f - metrics28.measure(0.8F), g - floatValue118 * 0.65F, metrics28.measure(1.6F), floatValue118 * 1.3F, metrics28.measure(1.0F), intValue33
         );
         renderManager24.invoke5(f - floatValue118 * 0.72F, g + floatValue119 * floatValue118 * 0.55F, floatValue118 * 1.44F, metrics28.measure(1.5F), metrics28.measure(1.0F), intValue33);
         renderManager24.invoke5(
            f - floatValue118, g - floatValue119 * floatValue118 * 0.95F, floatValue118 * 2.0F, metrics28.measure(1.5F), metrics28.measure(1.0F), ColorScheme.compute6(intValue33, 140)
         );
      } else if (i == 4) {
         this.invoke29(renderManager24, metrics28, colorScheme27, f, g, 0, h);
      } else if (i == 5) {
         renderManager24.invoke39(f, g, floatValue118, 0.0F, 0.82F, ColorScheme.compute6(intValue33, 90));
         renderManager24.invoke5(f + floatValue118 * 0.2F, g - floatValue118 * 0.9F, floatValue118 * 0.78F, metrics28.measure(1.4F), metrics28.measure(1.0F), intValue33);
      } else if (i == 8) {
         renderManager24.invoke28(f - floatValue118 * 0.5F, g - floatValue118 * 0.32F, floatValue118, floatValue118 * 0.92F, metrics28.measure(1.4F), intValue33, 0.8F);
         renderManager24.invoke5(f - floatValue118 * 0.68F, g - floatValue118 * 0.56F, floatValue118 * 1.36F, metrics28.measure(1.3F), metrics28.measure(0.8F), intValue33);
         renderManager24.invoke5(f - floatValue118 * 0.22F, g - floatValue118 * 0.82F, floatValue118 * 0.44F, metrics28.measure(1.3F), metrics28.measure(0.8F), intValue33);
         renderManager24.invoke5(
            f - metrics28.measure(0.6F),
            g - floatValue118 * 0.1F,
            metrics28.measure(1.2F),
            floatValue118 * 0.5F,
            metrics28.measure(0.5F),
            ColorScheme.compute6(intValue33, 170)
         );
      } else if (i == 9) {
         renderManager24.invoke39(f, g, floatValue118 * 0.9F, 0.0F, 1.0F, ColorScheme.compute6(intValue33, 82));
         renderManager24.invoke39(f, g, floatValue118 * 0.38F, 0.0F, 1.0F, intValue33);
      } else if (i == 10) {
         renderManager24.invoke56(f, g);
         renderManager24.invoke54(45.0F);
         renderManager24.invoke5(-floatValue118 * 0.8F, -metrics28.measure(0.8F), floatValue118 * 1.6F, metrics28.measure(1.6F), metrics28.measure(1.0F), intValue33);
         renderManager24.invoke55();
         renderManager24.invoke54(-45.0F);
         renderManager24.invoke5(-floatValue118 * 0.8F, -metrics28.measure(0.8F), floatValue118 * 1.6F, metrics28.measure(1.6F), metrics28.measure(1.0F), intValue33);
         renderManager24.invoke55();
         renderManager24.invoke57();
      } else {
         renderManager24.invoke39(f, g, floatValue118, 0.0F, 1.0F, ColorScheme.compute6(intValue33, i == 6 ? 165 : 92));
         renderManager24.invoke39(f, g, floatValue118 * 0.38F, 0.0F, 1.0F, intValue33);
      }
   }

   private void invoke43(
      RenderManager renderManager25,
      Metrics metrics29,
      ColorScheme colorScheme28,
      Rect rect41,
      String string,
      boolean bl,
      float f,
      float g
   ) {
      float floatValue120 = rect41.contains(f, g) ? 1.0F : 0.0F;
      float floatValue121 = Math.max(bl ? 0.84F : 0.0F, floatValue120);
      renderManager25.invoke5(
         rect41.x(),
         rect41.y(),
         rect41.w(),
         rect41.h(),
         metrics29.measure(8.0F),
         ColorScheme.compute7(
            ColorScheme.compute5(255, 255, 255, this.check4(colorScheme28) ? 64 : 10), ColorScheme.compute6(colorScheme28.getIntValue14(), 92), floatValue121
         )
      );
      renderManager25.invoke28(
         rect41.x(),
         rect41.y(),
         rect41.w(),
         rect41.h(),
         metrics29.measure(8.0F),
         ColorScheme.compute6(bl ? colorScheme28.getIntValue14() : colorScheme28.getIntValue13(), bl ? 150 : 42),
         0.65F
      );
      float floatValue122 = ClickGuiRenderUtils.measure2(metrics29, FontRegistry.fontObject, string, 8.0F);
      ClickGuiRenderUtils.invoke4(
         renderManager25,
         metrics29,
         FontRegistry.fontObject,
         rect41.x() + (rect41.w() - floatValue122) * 0.5F,
         rect41.y(),
         rect41.h(),
         8.0F,
         string,
         bl ? this.compute11(colorScheme28) : this.compute12(colorScheme28)
      );
   }

   private void invoke44(RenderManager renderManager26, ClickGuiState clickGuiState21, ThemeContext themeContext14, int i, int j) {
      Metrics metrics30 = themeContext14.getMetrics();
      ColorScheme colorScheme29 = themeContext14.getColorScheme();
      Rect rect42 = this.resolve33(metrics30, j);
      boolean flag26 = rect42.contains(clickGuiState21.getFloatValue(), clickGuiState21.getFloatValue2());
      renderManager26.invoke5(
         rect42.x(),
         rect42.y(),
         rect42.w(),
         rect42.h(),
         metrics30.measure(7.0F),
         ColorScheme.compute7(ColorScheme.compute5(255, 255, 255, 8), ColorScheme.compute6(colorScheme29.getIntValue14(), 64), flag26 ? 1.0F : 0.0F)
      );
      renderManager26.invoke28(rect42.x(), rect42.y(), rect42.w(), rect42.h(), metrics30.measure(7.0F), ColorScheme.compute6(colorScheme29.getIntValue14(), 96), 0.7F);
      String text25 = this.flag8 ? ">" : "<";
      float floatValue123 = ClickGuiRenderUtils.measure2(metrics30, FontRegistry.fontObject4, text25, 10.0F);
      ClickGuiRenderUtils.invoke4(
         renderManager26, metrics30, FontRegistry.fontObject4, rect42.x() + (rect42.w() - floatValue123) * 0.5F, rect42.y(), rect42.h(), 10.0F, text25, colorScheme29.getIntValue13()
      );
      if (!this.flag8) {
         Rect rect43 = this.resolve32(metrics30, j);
         float floatValue124 = metrics30.measure(14.0F);
         renderManager26.invoke41(rect43.x(), rect43.y(), rect43.w(), rect43.h(), floatValue124, metrics30.measure(18.0F), metrics30.measure(2.0F), this.compute4(colorScheme29, 118));
         renderManager26.invoke5(rect43.x(), rect43.y(), rect43.w(), rect43.h(), floatValue124, this.compute3(colorScheme29, 220));
         renderManager26.invoke28(rect43.x(), rect43.y(), rect43.w(), rect43.h(), floatValue124, colorScheme29.getIntValue6(), 0.7F);
         ClickGuiRenderUtils.invoke3(
            renderManager26,
            metrics30,
            FontRegistry.fontObject4,
            rect43.x() + metrics30.measure(15.0F),
            rect43.y() + metrics30.measure(14.0F),
            12.0F,
            "Node Library",
            colorScheme29.getIntValue13()
         );
         ClickGuiRenderUtils.invoke3(
            renderManager26,
            metrics30,
            FontRegistry.fontObject,
            rect43.x() + metrics30.measure(15.0F),
            rect43.y() + metrics30.measure(28.0F),
            8.0F,
            "click to spawn / RMB opens search",
            ColorScheme.compute6(colorScheme29.getIntValue14(), 156)
         );
         this.invoke45(renderManager26, metrics30, colorScheme29, this.resolve10(metrics30, j));
         List items3 = this.resolve12();
         float floatValue125 = rect43.y() + metrics30.measure(74.0F);
         float floatValue126 = rect43.y() + rect43.h() - metrics30.measure(14.0F);
         float floatValue127 = Math.max(1.0F, floatValue126 - floatValue125);
         float floatValue128 = this.measure5(metrics30, items3);
         this.floatValue23 = measure21(this.floatValue23, 0.0F, Math.max(0.0F, floatValue128 - floatValue127));
         ShaderLibraryState shaderLibraryState = ShaderLibraryState.getINSTANCE();
         renderManager26.invoke20();
         renderManager26.invoke24(
            rect43.x() + metrics30.measure(8.0F),
            floatValue125,
            rect43.w() - metrics30.measure(16.0F),
            floatValue127,
            metrics30.measure(8.0F),
            metrics30.measure(8.0F),
            metrics30.measure(8.0F),
            metrics30.measure(8.0F)
         );

         try {
            float floatValue129 = floatValue125 - this.floatValue23;

            for (ShaderFoundryScreen.ShaderFoundryScreenEntry shaderFoundryScreenEntry : (List<ShaderFoundryScreen.ShaderFoundryScreenEntry>)items3) {
               if (shaderFoundryScreenEntry.type() == 0) {
                  float floatValue130 = metrics30.measure(20.0F);
                  if (floatValue129 + floatValue130 >= floatValue125 && floatValue129 <= floatValue126) {
                     boolean flag27 = clickGuiState21.getFloatValue() >= rect43.x() + metrics30.measure(8.0F)
                        && clickGuiState21.getFloatValue() < rect43.x() + rect43.w() - metrics30.measure(8.0F)
                        && clickGuiState21.getFloatValue2() >= floatValue129
                        && clickGuiState21.getFloatValue2() < floatValue129 + floatValue130;
                     boolean flag28 = this.values3.contains(shaderFoundryScreenEntry.category());
                     if (flag27) {
                        renderManager26.invoke5(
                           rect43.x() + metrics30.measure(8.0F),
                           floatValue129,
                           rect43.w() - metrics30.measure(16.0F),
                           floatValue130,
                           metrics30.measure(6.0F),
                           ColorScheme.compute6(colorScheme29.getIntValue15(), 26)
                        );
                     }

                     ClickGuiRenderUtils.invoke4(
                        renderManager26,
                        metrics30,
                        FontRegistry.fontObject4,
                        rect43.x() + metrics30.measure(14.0F),
                        floatValue129,
                        floatValue130,
                        8.0F,
                        (flag28 ? "▸ " : "▾ ") + shaderFoundryScreenEntry.category().toUpperCase(Locale.ROOT),
                        ColorScheme.compute6(colorScheme29.getIntValue15(), flag27 ? 245 : 210)
                     );
                     String text26 = String.valueOf(shaderFoundryScreenEntry.count());
                     float floatValue131 = ClickGuiRenderUtils.measure2(metrics30, FontRegistry.fontObject, text26, 8.0F);
                     ClickGuiRenderUtils.invoke4(
                        renderManager26,
                        metrics30,
                        FontRegistry.fontObject,
                        rect43.x() + rect43.w() - metrics30.measure(18.0F) - floatValue131,
                        floatValue129,
                        floatValue130,
                        8.0F,
                        text26,
                        ColorScheme.compute6(colorScheme29.getIntValue14(), flag27 ? 210 : 140)
                     );
                  }

                  floatValue129 += metrics30.measure(22.0F);
               } else {
                  ShaderNodeDefinition shaderNodeDefinition7 = shaderFoundryScreenEntry.def();
                  float floatValue132 = metrics30.measure(24.0F);
                  if (floatValue129 + floatValue132 >= floatValue125 && floatValue129 <= floatValue126) {
                     boolean flag29 = clickGuiState21.getFloatValue() >= rect43.x() + metrics30.measure(8.0F)
                        && clickGuiState21.getFloatValue() < rect43.x() + rect43.w() - metrics30.measure(8.0F)
                        && clickGuiState21.getFloatValue2() >= floatValue129
                        && clickGuiState21.getFloatValue2() < floatValue129 + floatValue132;
                     float floatValue133 = flag29 ? 1.0F : 0.0F;
                     boolean flag30 = shaderLibraryState.check(shaderNodeDefinition7.getText());
                     renderManager26.invoke5(
                        rect43.x() + metrics30.measure(8.0F),
                        floatValue129,
                        rect43.w() - metrics30.measure(16.0F),
                        floatValue132,
                        metrics30.measure(7.0F),
                        ColorScheme.compute7(ColorScheme.compute5(255, 255, 255, 4), ColorScheme.compute6(colorScheme29.getIntValue14(), 54), floatValue133)
                     );
                     renderManager26.invoke39(
                        rect43.x() + metrics30.measure(19.0F),
                        floatValue129 + floatValue132 * 0.5F,
                        metrics30.measure(2.6F),
                        0.0F,
                        1.0F,
                        this.compute10(shaderNodeDefinition7.getItems2().isEmpty() ? null : shaderNodeDefinition7.getItems2().get(0), colorScheme29)
                     );
                     ClickGuiRenderUtils.invoke4(
                        renderManager26,
                        metrics30,
                        FontRegistry.fontObject,
                        rect43.x() + metrics30.measure(31.0F),
                        floatValue129,
                        floatValue132,
                        9.0F,
                        ClickGuiRenderUtils.resolve4(metrics30, FontRegistry.fontObject, shaderNodeDefinition7.getText2(), 9.0F, rect43.w() - metrics30.measure(112.0F)),
                        flag29 ? colorScheme29.getIntValue13() : colorScheme29.getIntValue12()
                     );
                     String text27 = shaderNodeDefinition7.getItems2().isEmpty() ? "out" : shaderNodeDefinition7.getItems2().get(0).type().getText();
                     ClickGuiRenderUtils.invoke4(
                        renderManager26,
                        metrics30,
                        FontRegistry.fontObject,
                        rect43.x() + rect43.w() - metrics30.measure(72.0F),
                        floatValue129,
                        floatValue132,
                        8.0F,
                        text27,
                        ColorScheme.compute6(colorScheme29.getIntValue14(), flag29 ? 230 : 150)
                     );
                     if (flag30 || flag29) {
                        Rect rect44 = this.resolve11(rect43, metrics30, floatValue129, floatValue132);
                        boolean flag31 = rect44.contains(clickGuiState21.getFloatValue(), clickGuiState21.getFloatValue2());
                        this.invoke46(
                           renderManager26, metrics30, colorScheme29, rect44.x() + rect44.w() * 0.5F, rect44.y() + rect44.h() * 0.5F, flag30, flag31 ? 1.0F : (flag30 ? 0.8F : 0.35F)
                        );
                     }
                  }

                  floatValue129 += metrics30.measure(26.0F);
               }
            }

            if (items3.isEmpty()) {
               ClickGuiRenderUtils.invoke3(
                  renderManager26,
                  metrics30,
                  FontRegistry.fontObject,
                  rect43.x() + metrics30.measure(16.0F),
                  floatValue125 + metrics30.measure(10.0F),
                  9.0F,
                  "no matching nodes",
                  colorScheme29.getIntValue12()
               );
            }
         } finally {
            renderManager26.invoke20();
            renderManager26.invoke25();
         }

         if (floatValue128 > floatValue127 + 1.0F) {
            float floatValue134 = rect43.x() + rect43.w() - metrics30.measure(8.0F);
            float floatValue135 = floatValue125 + metrics30.measure(2.0F);
            float floatValue136 = floatValue127 - metrics30.measure(4.0F);
            float floatValue137 = Math.max(metrics30.measure(34.0F), floatValue136 * floatValue127 / floatValue128);
            float floatValue138 = Math.max(1.0F, floatValue128 - floatValue127);
            float floatValue139 = floatValue135 + (floatValue136 - floatValue137) * (this.floatValue23 / floatValue138);
            float floatValue140 = ClickGuiDragRegistry.measure(
               7101L,
               floatValue134 - metrics30.measure(3.0F),
               floatValue135,
               metrics30.measure(8.0F),
               floatValue136,
               floatValue139,
               floatValue137,
               metrics30.measure(6.0F),
               clickGuiState21.getFloatValue(),
               clickGuiState21.getFloatValue2(),
               g -> this.floatValue23 = measure21(g, 0.0F, 1.0F) * floatValue138
            );
            float floatValue141 = metrics30.measure(2.0F) + metrics30.measure(2.0F) * floatValue140;
            renderManager26.invoke5(floatValue134, floatValue135, metrics30.measure(2.0F), floatValue136, metrics30.measure(1.0F), colorScheme29.getIntValue4());
            renderManager26.invoke5(
               floatValue134 + metrics30.measure(2.0F) - floatValue141,
               floatValue139,
               floatValue141,
               floatValue137,
               metrics30.measure(1.5F),
               ColorScheme.compute6(colorScheme29.getIntValue14(), (int)(142.0F + 90.0F * floatValue140))
            );
         }
      }
   }

   private void invoke45(RenderManager renderManager27, Metrics metrics31, ColorScheme colorScheme30, Rect rect45) {
      float floatValue142 = this.flag10 ? 1.0F : 0.0F;
      float floatValue143 = metrics31.measure(7.0F);
      renderManager27.invoke5(
         rect45.x(),
         rect45.y(),
         rect45.w(),
         rect45.h(),
         floatValue143,
         ColorScheme.compute5(255, 255, 255, this.check4(colorScheme30) ? 78 : 12)
      );
      renderManager27.invoke28(
         rect45.x(),
         rect45.y(),
         rect45.w(),
         rect45.h(),
         floatValue143,
         ColorScheme.compute7(
            colorScheme30.getIntValue6(), ColorScheme.compute6(colorScheme30.getIntValue14(), 176), Math.max(floatValue142, this.text5.isEmpty() ? 0.0F : 0.5F)
         ),
         this.flag10 ? 1.0F : 0.65F
      );
      float floatValue144 = rect45.x() + metrics31.measure(11.0F);
      float floatValue145 = rect45.y() + rect45.h() * 0.5F - metrics31.measure(1.0F);
      renderManager27.invoke40(floatValue144, floatValue145, metrics31.measure(3.2F), 0.0F, 1.0F, 1.2F, ColorScheme.compute6(colorScheme30.getIntValue14(), 220));
      renderManager27.invoke5(
         floatValue144 + metrics31.measure(2.4F),
         floatValue145 + metrics31.measure(2.4F),
         metrics31.measure(3.8F),
         1.2F,
         0.6F,
         ColorScheme.compute6(colorScheme30.getIntValue14(), 220)
      );
      float floatValue146 = rect45.x() + metrics31.measure(21.0F);
      String text28 = this.text5.isEmpty() ? "Search nodes…" : this.text5;
      int intValue34 = this.text5.isEmpty() ? this.compute12(colorScheme30) : this.compute11(colorScheme30);
      renderManager27.invoke20();
      renderManager27.invoke24(
         rect45.x() + metrics31.measure(4.0F),
         rect45.y(),
         rect45.w() - metrics31.measure(8.0F),
         rect45.h(),
         floatValue143,
         floatValue143,
         floatValue143,
         floatValue143
      );

      try {
         ClickGuiRenderUtils.invoke4(renderManager27, metrics31, FontRegistry.fontObject, floatValue146, rect45.y(), rect45.h(), 9.0F, text28, intValue34);
         if (this.flag10 && (System.currentTimeMillis() - this.timestamp4) / 500L % 2L == 0L) {
            float floatValue147 = floatValue146
               + (
                  this.text5.isEmpty()
                     ? 0.0F
                     : ClickGuiRenderUtils.measure2(metrics31, FontRegistry.fontObject, this.text5, 9.0F) + metrics31.measure(1.5F)
               );
            renderManager27.invoke5(
               Math.min(floatValue147, rect45.x() + rect45.w() - metrics31.measure(8.0F)),
               rect45.y() + metrics31.measure(4.5F),
               1.0F,
               rect45.h() - metrics31.measure(9.0F),
               0.0F,
               ColorScheme.compute6(colorScheme30.getIntValue14(), 240)
            );
         }
      } finally {
         renderManager27.invoke20();
         renderManager27.invoke25();
      }
   }

   private void invoke46(RenderManager renderManager28, Metrics metrics32, ColorScheme colorScheme31, float f, float g, boolean bl, float h) {
      int intValue35 = ColorScheme.compute6(colorScheme31.getIntValue15(), Math.round(120.0F + 130.0F * h));
      float floatValue148 = metrics32.measure(2.9F);
      if (bl) {
         renderManager28.invoke5(f - floatValue148, g - floatValue148, floatValue148 * 2.0F, floatValue148 * 2.0F, metrics32.measure(1.0F), intValue35);
         renderManager28.invoke56(f, g);
         renderManager28.invoke54(45.0F);
         renderManager28.invoke5(-floatValue148, -floatValue148, floatValue148 * 2.0F, floatValue148 * 2.0F, metrics32.measure(1.0F), ColorScheme.compute6(intValue35, 210));
         renderManager28.invoke55();
         renderManager28.invoke57();
         renderManager28.invoke39(f, g, metrics32.measure(1.4F), 0.0F, 1.0F, ColorScheme.compute6(colorScheme31.getIntValue13(), 200));
      } else {
         renderManager28.invoke28(f - floatValue148, g - floatValue148, floatValue148 * 2.0F, floatValue148 * 2.0F, metrics32.measure(1.0F), intValue35, 0.7F);
         renderManager28.invoke56(f, g);
         renderManager28.invoke54(45.0F);
         renderManager28.invoke28(-floatValue148, -floatValue148, floatValue148 * 2.0F, floatValue148 * 2.0F, metrics32.measure(1.0F), ColorScheme.compute6(intValue35, 150), 0.7F);
         renderManager28.invoke55();
         renderManager28.invoke57();
      }
   }

   private Rect resolve10(Metrics metrics33, int i) {
      Rect rect46 = this.resolve32(metrics33, i);
      return new Rect(
         rect46.x() + metrics33.measure(10.0F),
         rect46.y() + metrics33.measure(42.0F),
         rect46.w() - metrics33.measure(20.0F),
         metrics33.measure(22.0F)
      );
   }

   private Rect resolve11(Rect rect47, Metrics metrics34, float f, float g) {
      return new Rect(
         rect47.x() + rect47.w() - metrics34.measure(30.0F),
         f + (g - metrics34.measure(16.0F)) * 0.5F,
         metrics34.measure(16.0F),
         metrics34.measure(16.0F)
      );
   }

   private List<ShaderFoundryScreen.ShaderFoundryScreenEntry> resolve12() {
      ArrayList arrayList4 = new ArrayList();
      String text29 = this.text5 == null ? "" : this.text5.toLowerCase(Locale.ROOT).trim();
      if (text29.isEmpty()) {
         ShaderLibraryState shaderLibraryState2 = ShaderLibraryState.getINSTANCE();
         ArrayList arrayList5 = new ArrayList();

         for (String text30 : shaderLibraryState2.resolve()) {
            ShaderNodeDefinition shaderNodeDefinition8 = this.shaderNodeRegistry.resolve(text30);
            if (shaderNodeDefinition8 != null) {
               arrayList5.add(shaderNodeDefinition8);
            }
         }

         this.invoke47(arrayList4, "Избранное", arrayList5);
         ArrayList arrayList6 = new ArrayList();

         for (String text31 : shaderLibraryState2.resolve2()) {
            ShaderNodeDefinition shaderNodeDefinition9 = this.shaderNodeRegistry.resolve(text31);
            if (shaderNodeDefinition9 != null) {
               arrayList6.add(shaderNodeDefinition9);
            }
         }

         this.invoke47(arrayList4, "Недавние", arrayList6);
         String text32 = null;
         ArrayList arrayList7 = new ArrayList();

         for (ShaderNodeDefinition shaderNodeDefinition10 : this.resolve20()) {
            if (!shaderNodeDefinition10.getText3().equals(text32)) {
               if (text32 != null) {
                  this.invoke47(arrayList4, text32, arrayList7);
               }

               text32 = shaderNodeDefinition10.getText3();
               arrayList7 = new ArrayList();
            }

            arrayList7.add(shaderNodeDefinition10);
         }

         if (text32 != null) {
            this.invoke47(arrayList4, text32, arrayList7);
         }

         return arrayList4;
      } else {
         ArrayList arrayList8 = new ArrayList();

         for (ShaderNodeDefinition shaderNodeDefinition11 : this.shaderNodeRegistry.resolve2()) {
            ShaderNodeSearch.ShaderNodeSearchData shaderNodeSearchData = ShaderNodeSearch.resolve(shaderNodeDefinition11, text29);
            if (shaderNodeSearchData != null) {
               arrayList8.add(shaderNodeSearchData);
            }
         }

         arrayList8.sort(Comparator.<ShaderNodeSearch.ShaderNodeSearchData>comparingInt(shaderNodeSearchData2 -> -shaderNodeSearchData2.score()).thenComparing(shaderNodeSearchData3 -> shaderNodeSearchData3.def().getText2()));

         for (ShaderNodeSearch.ShaderNodeSearchData shaderNodeSearchData4 : (List<ShaderNodeSearch.ShaderNodeSearchData>)arrayList8) {
            arrayList4.add(new ShaderFoundryScreen.ShaderFoundryScreenEntry(1, shaderNodeSearchData4.def().getText3(), shaderNodeSearchData4.def(), 0));
         }

         return arrayList4;
      }
   }

   private void invoke47(List<ShaderFoundryScreen.ShaderFoundryScreenEntry> list, String string, List<ShaderNodeDefinition> list2) {
      if (!list2.isEmpty()) {
         list.add(new ShaderFoundryScreen.ShaderFoundryScreenEntry(0, string, null, list2.size()));
         if (!this.values3.contains(string)) {
            for (ShaderNodeDefinition shaderNodeDefinition12 : list2) {
               list.add(new ShaderFoundryScreen.ShaderFoundryScreenEntry(1, string, shaderNodeDefinition12, 0));
            }
         }
      }
   }

   private float measure5(Metrics metrics35, List<ShaderFoundryScreen.ShaderFoundryScreenEntry> list) {
      float floatValue149 = 0.0F;

      for (ShaderFoundryScreen.ShaderFoundryScreenEntry shaderFoundryScreenEntry2 : list) {
         floatValue149 += shaderFoundryScreenEntry2.type() == 0 ? metrics35.measure(22.0F) : metrics35.measure(26.0F);
      }

      return floatValue149;
   }

   private ShaderFoundryScreen.ShaderFoundryScreenData3 resolve13(Metrics metrics36, int i, float f, float g) {
      Rect rect48 = this.resolve32(metrics36, i);
      float floatValue150 = rect48.y() + metrics36.measure(74.0F);
      float floatValue151 = rect48.y() + rect48.h() - metrics36.measure(14.0F);
      if (!(g < floatValue150) && !(g > floatValue151) && !(f < rect48.x() + metrics36.measure(8.0F)) && !(f >= rect48.x() + rect48.w() - metrics36.measure(8.0F))) {
         float floatValue152 = floatValue150 - this.floatValue23;

         for (ShaderFoundryScreen.ShaderFoundryScreenEntry shaderFoundryScreenEntry3 : this.resolve12()) {
            if (shaderFoundryScreenEntry3.type() == 0) {
               if (g >= floatValue152 && g < floatValue152 + metrics36.measure(20.0F)) {
                  return new ShaderFoundryScreen.ShaderFoundryScreenData3(shaderFoundryScreenEntry3, false);
               }

               floatValue152 += metrics36.measure(22.0F);
            } else {
               float floatValue153 = metrics36.measure(24.0F);
               if (g >= floatValue152 && g < floatValue152 + floatValue153) {
                  boolean flag32 = this.resolve11(rect48, metrics36, floatValue152, floatValue153).contains(f, g);
                  return new ShaderFoundryScreen.ShaderFoundryScreenData3(shaderFoundryScreenEntry3, flag32);
               }

               floatValue152 += metrics36.measure(26.0F);
               if (floatValue152 > floatValue151 + metrics36.measure(26.0F)) {
                  break;
               }
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private void invoke48(RenderManager renderManager29, ClickGuiState clickGuiState22, ThemeContext themeContext15, int i, int j, float f) {
      Metrics metrics37 = themeContext15.getMetrics();
      ColorScheme colorScheme32 = themeContext15.getColorScheme();
      Rect rect49 = this.resolve43(metrics37, i, j);
      float floatValue154 = rect49.w();
      float floatValue155 = rect49.h();
      float floatValue156 = rect49.x();
      float floatValue157 = rect49.y();
      float floatValue158 = metrics37.measure(15.0F);
      renderManager29.invoke41(floatValue156, floatValue157, floatValue154, floatValue155, floatValue158, metrics37.measure(22.0F), metrics37.measure(2.0F), this.compute4(colorScheme32, 138));
      renderManager29.invoke5(floatValue156, floatValue157, floatValue154, floatValue155, floatValue158, this.compute3(colorScheme32, 210));
      renderManager29.invoke28(floatValue156, floatValue157, floatValue154, floatValue155, floatValue158, ColorScheme.compute6(colorScheme32.getIntValue15(), 56), 0.8F);
      float floatValue159 = metrics37.measure(12.0F);
      float floatValue160 = floatValue155 - metrics37.measure(50.0F);
      float floatValue161 = floatValue154 - floatValue159 * 2.0F;
      float floatValue162 = floatValue156 + floatValue159;
      float floatValue163 = floatValue157 + metrics37.measure(38.0F);
      ShaderPreviewRenderer.invoke(
         renderManager29,
         themeContext15,
         this.shaderSurface,
         this.namedShaderProgram,
         this.shaderNode,
         floatValue162,
         floatValue163,
         floatValue161,
         floatValue160,
         i,
         j,
         clickGuiState22.getFloatValue(),
         clickGuiState22.getFloatValue2(),
         f
      );
      String text33 = this.namedShaderProgram.resolve2();
      float floatValue164 = metrics37.measure(9.0F);
      if (!text33.isBlank()) {
         renderManager29.invoke5(floatValue162, floatValue163, floatValue161, floatValue160, floatValue164, ColorScheme.compute5(8, 4, 6, Math.round(150.0F * f)));
         renderManager29.invoke28(floatValue162, floatValue163, floatValue161, floatValue160, floatValue164, ColorScheme.compute5(255, 110, 124, Math.round(142.0F * f)), 0.7F);
         String text34 = "compile failed";
         float floatValue165 = ClickGuiRenderUtils.measure2(metrics37, FontRegistry.fontObject4, text34, 10.0F);
         ClickGuiRenderUtils.invoke4(
            renderManager29,
            metrics37,
            FontRegistry.fontObject4,
            floatValue162 + (floatValue161 - floatValue165) * 0.5F,
            floatValue163 + floatValue160 * 0.5F - metrics37.measure(17.0F),
            metrics37.measure(14.0F),
            10.0F,
            text34,
            ColorScheme.compute5(255, 132, 132, 240)
         );
         String text35 = ClickGuiRenderUtils.resolve4(metrics37, FontRegistry.fontObject, text33, 8.0F, floatValue161 - metrics37.measure(24.0F));
         float floatValue166 = ClickGuiRenderUtils.measure2(metrics37, FontRegistry.fontObject, text35, 8.0F);
         ClickGuiRenderUtils.invoke4(
            renderManager29,
            metrics37,
            FontRegistry.fontObject,
            floatValue162 + (floatValue161 - floatValue166) * 0.5F,
            floatValue163 + floatValue160 * 0.5F + metrics37.measure(1.0F),
            metrics37.measure(12.0F),
            8.0F,
            text35,
            ColorScheme.compute5(255, 182, 188, 218)
         );
      } else if (this.namedShaderProgram.resolve3().isBlank()) {
         renderManager29.invoke37(
            floatValue162,
            floatValue163,
            floatValue161,
            floatValue160,
            floatValue164,
            ColorScheme.compute6(colorScheme32.getIntValue14(), Math.round(52.0F * f)),
            ColorScheme.compute6(colorScheme32.getIntValue15(), Math.round(30.0F * f))
         );
         renderManager29.invoke28(floatValue162, floatValue163, floatValue161, floatValue160, floatValue164, ColorScheme.compute6(colorScheme32.getIntValue14(), Math.round(74.0F * f)), 0.6F);
         String text36 = "connect Master Output to see the result";
         float floatValue167 = ClickGuiRenderUtils.measure2(metrics37, FontRegistry.fontObject, text36, 9.0F);
         ClickGuiRenderUtils.invoke4(
            renderManager29,
            metrics37,
            FontRegistry.fontObject,
            floatValue162 + (floatValue161 - floatValue167) * 0.5F,
            floatValue163 + floatValue160 * 0.5F - metrics37.measure(7.0F),
            metrics37.measure(14.0F),
            9.0F,
            text36,
            this.compute12(colorScheme32)
         );
      }

      ClickGuiRenderUtils.invoke3(
         renderManager29, metrics37, FontRegistry.fontObject4, floatValue156 + floatValue159, floatValue157 + metrics37.measure(12.0F), 11.0F, "Master Preview", colorScheme32.getIntValue13()
      );
      ClickGuiRenderUtils.invoke3(
         renderManager29,
         metrics37,
         FontRegistry.fontObject,
         floatValue156 + floatValue159 + metrics37.measure(108.0F),
         floatValue157 + metrics37.measure(14.0F),
         9.0F,
         this.shaderSurface.getText2(),
         ColorScheme.compute6(colorScheme32.getIntValue15(), 220)
      );
   }

   private void invoke49(RenderManager renderManager30, ClickGuiState clickGuiState23, ThemeContext themeContext16, int i, int j) {
      Metrics metrics38 = themeContext16.getMetrics();
      ColorScheme colorScheme33 = themeContext16.getColorScheme();
      Rect rect50 = this.resolve34(metrics38, i, j);
      float floatValue168 = metrics38.measure(14.0F);
      renderManager30.invoke41(rect50.x(), rect50.y(), rect50.w(), rect50.h(), floatValue168, metrics38.measure(20.0F), metrics38.measure(2.0F), this.compute4(colorScheme33, 132));
      renderManager30.invoke5(rect50.x(), rect50.y(), rect50.w(), rect50.h(), floatValue168, this.compute2(colorScheme33, 214));
      renderManager30.invoke28(rect50.x(), rect50.y(), rect50.w(), rect50.h(), floatValue168, ColorScheme.compute6(colorScheme33.getIntValue14(), 58), 0.7F);
      ShaderNodeKind shaderNodeKind18 = this.resolve37();
      if (shaderNodeKind18 == null) {
         ClickGuiRenderUtils.invoke3(
            renderManager30,
            metrics38,
            FontRegistry.fontObject4,
            rect50.x() + metrics38.measure(14.0F),
            rect50.y() + metrics38.measure(14.0F),
            12.0F,
            "Shader Settings",
            colorScheme33.getIntValue13()
         );
         ClickGuiRenderUtils.invoke3(
            renderManager30,
            metrics38,
            FontRegistry.fontObject,
            rect50.x() + metrics38.measure(14.0F),
            rect50.y() + metrics38.measure(32.0F),
            8.0F,
            this.shaderSurface.getText2() + " / " + this.hostRectangle,
            ColorScheme.compute6(colorScheme33.getIntValue14(), 190)
         );
         this.invoke50(renderManager30, metrics38, colorScheme33, rect50);
      } else {
         ShaderNodeDefinition shaderNodeDefinition13 = this.shaderNodeRegistry.resolve(shaderNodeKind18.getText2());
         if (shaderNodeDefinition13 != null) {
            ClickGuiRenderUtils.invoke3(
               renderManager30,
               metrics38,
               FontRegistry.fontObject4,
               rect50.x() + metrics38.measure(14.0F),
               rect50.y() + metrics38.measure(14.0F),
               12.0F,
               shaderNodeDefinition13.getText2(),
               colorScheme33.getIntValue13()
            );
            ClickGuiRenderUtils.invoke3(
               renderManager30,
               metrics38,
               FontRegistry.fontObject,
               rect50.x() + metrics38.measure(14.0F),
               rect50.y() + metrics38.measure(30.0F),
               8.0F,
               shaderNodeDefinition13.getText3() + " / " + shaderNodeKind18.getText2(),
               ColorScheme.compute6(colorScheme33.getIntValue14(), 190)
            );
            this.invoke51(renderManager30, metrics38, colorScheme33, rect50, shaderNodeDefinition13, shaderNodeKind18);
            float floatValue169 = rect50.y() + metrics38.measure(74.0F);
            if (shaderNodeDefinition13.isFlag()) {
               Rect rect51 = this.resolve36(rect50, metrics38);
               boolean flag33 = Boolean.TRUE.equals(this.valuesByKey9.get(shaderNodeKind18.getText()));
               boolean flag34 = rect51.contains(clickGuiState23.getFloatValue(), clickGuiState23.getFloatValue2());
               renderManager30.invoke5(
                  rect51.x(),
                  rect51.y(),
                  rect51.w(),
                  rect51.h(),
                  metrics38.measure(7.0F),
                  ColorScheme.compute7(
                     colorScheme33.getIntValue4(), ColorScheme.compute6(flag33 ? colorScheme33.getIntValue15() : colorScheme33.getIntValue14(), 76), !flag34 && !flag33 ? 0.0F : 1.0F
                  )
               );
               renderManager30.invoke28(
                  rect51.x(), rect51.y(), rect51.w(), rect51.h(), metrics38.measure(7.0F), ColorScheme.compute6(colorScheme33.getIntValue14(), flag33 ? 150 : 84), 0.65F
               );
               String text37 = flag33 ? "Preview ON" : "Preview OFF";
               float floatValue170 = ClickGuiRenderUtils.measure2(metrics38, FontRegistry.fontObject, text37, 9.0F);
               ClickGuiRenderUtils.invoke4(
                  renderManager30, metrics38, FontRegistry.fontObject, rect51.x() + (rect51.w() - floatValue170) * 0.5F, rect51.y(), rect51.h(), 9.0F, text37, colorScheme33.getIntValue13()
               );
            }

            if ("float_value".equals(shaderNodeKind18.getText2())) {
               this.invoke56(renderManager30, metrics38, colorScheme33, shaderNodeKind18, "value", "Value", -12.0F, 12.0F, 0.01F, 0.5F, rect50, 0, clickGuiState23);
            } else if ("int_value".equals(shaderNodeKind18.getText2())) {
               this.invoke56(renderManager30, metrics38, colorScheme33, shaderNodeKind18, "value", "Value", -64.0F, 64.0F, 1.0F, 1.0F, rect50, 0, clickGuiState23);
            } else if ("exposed_float".equals(shaderNodeKind18.getText2())) {
               this.invoke55(renderManager30, metrics38, colorScheme33, shaderNodeKind18, "name", "Name", rect50, 0, clickGuiState23);
               float floatValue171 = shaderNodeKind18.measure("min", 0.0F);
               float floatValue172 = shaderNodeKind18.measure("max", 1.0F);
               if (floatValue172 <= floatValue171) {
                  floatValue172 = floatValue171 + 0.001F;
               }

               this.invoke56(renderManager30, metrics38, colorScheme33, shaderNodeKind18, "value", "Default", floatValue171, floatValue172, shaderNodeKind18.measure("step", 0.01F), 0.5F, rect50, 1, clickGuiState23);
               this.invoke56(renderManager30, metrics38, colorScheme33, shaderNodeKind18, "min", "Min", -128.0F, 128.0F, 0.01F, 0.0F, rect50, 2, clickGuiState23);
               this.invoke56(renderManager30, metrics38, colorScheme33, shaderNodeKind18, "max", "Max", -128.0F, 128.0F, 0.01F, 1.0F, rect50, 3, clickGuiState23);
               this.invoke56(renderManager30, metrics38, colorScheme33, shaderNodeKind18, "step", "Step", 1.0E-4F, 16.0F, 0.001F, 0.01F, rect50, 4, clickGuiState23);
            } else if ("exposed_color".equals(shaderNodeKind18.getText2())) {
               this.invoke55(renderManager30, metrics38, colorScheme33, shaderNodeKind18, "name", "Name", rect50, 0, clickGuiState23);
               this.invoke56(renderManager30, metrics38, colorScheme33, shaderNodeKind18, "r", "Red", 0.0F, 1.0F, 0.01F, 1.0F, rect50, 1, clickGuiState23);
               this.invoke56(renderManager30, metrics38, colorScheme33, shaderNodeKind18, "g", "Green", 0.0F, 1.0F, 0.01F, 1.0F, rect50, 2, clickGuiState23);
               this.invoke56(renderManager30, metrics38, colorScheme33, shaderNodeKind18, "b", "Blue", 0.0F, 1.0F, 0.01F, 1.0F, rect50, 3, clickGuiState23);
               this.invoke56(renderManager30, metrics38, colorScheme33, shaderNodeKind18, "a", "Alpha", 0.0F, 1.0F, 0.01F, 1.0F, rect50, 4, clickGuiState23);
               Rect rect52 = new Rect(rect50.x() + rect50.w() - metrics38.measure(82.0F), floatValue169, metrics38.measure(68.0F), metrics38.measure(18.0F));
               renderManager30.invoke5(
                  rect52.x(),
                  rect52.y(),
                  rect52.w(),
                  rect52.h(),
                  metrics38.measure(6.0F),
                  ColorScheme.compute5(
                     Math.round(shaderNodeKind18.measure("r", 1.0F) * 255.0F),
                     Math.round(shaderNodeKind18.measure("g", 1.0F) * 255.0F),
                     Math.round(shaderNodeKind18.measure("b", 1.0F) * 255.0F),
                     Math.round(shaderNodeKind18.measure("a", 1.0F) * 255.0F)
                  )
               );
               renderManager30.invoke28(rect52.x(), rect52.y(), rect52.w(), rect52.h(), metrics38.measure(6.0F), colorScheme33.getIntValue6(), 0.6F);
            } else {
               this.invoke52(renderManager30, metrics38, colorScheme33, rect50, shaderNodeDefinition13, shaderNodeKind18, floatValue169);
            }
         }
      }
   }

   private void invoke50(RenderManager renderManager31, Metrics metrics39, ColorScheme colorScheme34, Rect rect53) {
      float floatValue173 = rect53.y() + metrics39.measure(58.0F);
      this.invoke40(
         renderManager31,
         metrics39,
         colorScheme34,
         rect53.x() + metrics39.measure(14.0F),
         floatValue173,
         "Name",
         this.text7 != null && !this.text7.isBlank() ? this.text7 : this.shaderNode.getShaderTemplate().getText()
      );
      this.invoke40(
         renderManager31,
         metrics39,
         colorScheme34,
         rect53.x() + metrics39.measure(14.0F),
         floatValue173 + metrics39.measure(22.0F),
         "Nodes",
         String.valueOf(this.shaderNode.resolve2().size())
      );
      this.invoke40(
         renderManager31,
         metrics39,
         colorScheme34,
         rect53.x() + metrics39.measure(14.0F),
         floatValue173 + metrics39.measure(44.0F),
         "Links",
         String.valueOf(this.shaderNode.getItems().size())
      );
      this.invoke40(
         renderManager31,
         metrics39,
         colorScheme34,
         rect53.x() + metrics39.measure(14.0F),
         floatValue173 + metrics39.measure(66.0F),
         "Uniforms",
         String.valueOf(this.shaderSourceBuilder.resolve2(this.shaderNode).exposedUniforms().size())
      );
      this.invoke40(
         renderManager31,
         metrics39,
         colorScheme34,
         rect53.x() + metrics39.measure(14.0F),
         floatValue173 + metrics39.measure(88.0F),
         "Author",
         this.shaderNode.getShaderTemplate().getText2().isBlank() ? ShaderPresetStore.resolve19() : this.shaderNode.getShaderTemplate().getText2()
      );
      String text38 = this.shaderNode.getIntValue() == this.intValue4 ? "saved" : "dirty";
      Rect rect54 = new Rect(
         rect53.x() + metrics39.measure(14.0F),
         rect53.y() + rect53.h() - metrics39.measure(42.0F),
         rect53.w() - metrics39.measure(28.0F),
         metrics39.measure(28.0F)
      );
      renderManager31.invoke5(
         rect54.x(),
         rect54.y(),
         rect54.w(),
         rect54.h(),
         metrics39.measure(8.0F),
         ColorScheme.compute7(
            ColorScheme.compute5(255, 255, 255, this.check4(colorScheme34) ? 50 : 9),
            ColorScheme.compute6(this.shaderNode.getIntValue() == this.intValue4 ? colorScheme34.getIntValue15() : colorScheme34.getIntValue14(), 72),
            0.86F
         )
      );
      renderManager31.invoke28(
         rect54.x(),
         rect54.y(),
         rect54.w(),
         rect54.h(),
         metrics39.measure(8.0F),
         ColorScheme.compute6(this.shaderNode.getIntValue() == this.intValue4 ? colorScheme34.getIntValue15() : colorScheme34.getIntValue14(), 120),
         0.65F
      );
      ClickGuiRenderUtils.invoke4(
         renderManager31,
         metrics39,
         FontRegistry.fontObject4,
         rect54.x() + metrics39.measure(12.0F),
         rect54.y(),
         rect54.h(),
         9.0F,
         "compile state: " + text38,
         this.compute11(colorScheme34)
      );
   }

   private void invoke51(
      RenderManager renderManager32,
      Metrics metrics40,
      ColorScheme colorScheme35,
      Rect rect55,
      ShaderNodeDefinition shaderNodeDefinition14,
      ShaderNodeKind shaderNodeKind19
   ) {
      float floatValue174 = rect55.y() + metrics40.measure(48.0F);
      String[] texts2 = new String[]{
         shaderNodeDefinition14.getItems().size() + " in",
         shaderNodeDefinition14.getItems2().size() + " out",
         this.check25(shaderNodeKind19) ? "uniform" : shaderNodeDefinition14.getText3()
      };
      float floatValue175 = rect55.x() + metrics40.measure(14.0F);

      for (int intValue36 = 0; intValue36 < texts2.length; intValue36++) {
         float floatValue176 = intValue36 == 2
            ? rect55.w() - metrics40.measure(28.0F) - (floatValue175 - rect55.x() - metrics40.measure(14.0F))
            : metrics40.measure(58.0F);
         Rect rect56 = new Rect(floatValue175, floatValue174, floatValue176, metrics40.measure(18.0F));
         renderManager32.invoke5(
            rect56.x(),
            rect56.y(),
            rect56.w(),
            rect56.h(),
            metrics40.measure(6.0F),
            ColorScheme.compute5(255, 255, 255, this.check4(colorScheme35) ? 42 : 8)
         );
         renderManager32.invoke28(
            rect56.x(),
            rect56.y(),
            rect56.w(),
            rect56.h(),
            metrics40.measure(6.0F),
            ColorScheme.compute6(intValue36 == 2 ? colorScheme35.getIntValue15() : colorScheme35.getIntValue14(), 72),
            0.55F
         );
         ClickGuiRenderUtils.invoke4(
            renderManager32,
            metrics40,
            FontRegistry.fontObject,
            rect56.x() + metrics40.measure(8.0F),
            rect56.y(),
            rect56.h(),
            8.0F,
            ClickGuiRenderUtils.resolve4(metrics40, FontRegistry.fontObject, texts2[intValue36], 8.0F, rect56.w() - metrics40.measure(16.0F)),
            this.compute12(colorScheme35)
         );
         floatValue175 += floatValue176 + metrics40.measure(6.0F);
      }
   }

   private void invoke52(
      RenderManager renderManager33,
      Metrics metrics41,
      ColorScheme colorScheme36,
      Rect rect57,
      ShaderNodeDefinition shaderNodeDefinition15,
      ShaderNodeKind shaderNodeKind20,
      float f
   ) {
      float floatValue177 = rect57.w() - metrics41.measure(28.0F);
      float floatValue178 = rect57.y() + rect57.h() - metrics41.measure(16.0F) - f;
      if ("output_color".equals(shaderNodeKind20.getText2())) {
         this.invoke53(
            renderManager33,
            metrics41,
            colorScheme36,
            new Rect(rect57.x() + metrics41.measure(14.0F), f, floatValue177, floatValue178),
            shaderNodeDefinition15,
            shaderNodeKind20
         );
      } else {
         boolean flag35 = !shaderNodeDefinition15.getItems().isEmpty();
         boolean flag36 = !shaderNodeDefinition15.getItems2().isEmpty();
         if (flag35 || flag36) {
            if (flag35 != flag36) {
               Rect rect58 = new Rect(rect57.x() + metrics41.measure(14.0F), f, floatValue177, floatValue178);
               if (flag35) {
                  this.invoke54(renderManager33, metrics41, colorScheme36, rect58, "Inputs", shaderNodeDefinition15.getItems(), shaderNodeKind20, true);
               } else {
                  this.invoke54(renderManager33, metrics41, colorScheme36, rect58, "Outputs", shaderNodeDefinition15.getItems2(), shaderNodeKind20, false);
               }
            } else {
               float floatValue179 = metrics41.measure(10.0F);
               float floatValue180 = (floatValue177 - floatValue179) * 0.5F;
               Rect rect59 = new Rect(rect57.x() + metrics41.measure(14.0F), f, floatValue180, floatValue178);
               Rect rect60 = new Rect(rect59.x() + rect59.w() + floatValue179, f, floatValue180, rect59.h());
               this.invoke54(renderManager33, metrics41, colorScheme36, rect59, "Inputs", shaderNodeDefinition15.getItems(), shaderNodeKind20, true);
               this.invoke54(renderManager33, metrics41, colorScheme36, rect60, "Outputs", shaderNodeDefinition15.getItems2(), shaderNodeKind20, false);
            }
         }
      }
   }

   private void invoke53(
      RenderManager renderManager34,
      Metrics metrics42,
      ColorScheme colorScheme37,
      Rect rect61,
      ShaderNodeDefinition shaderNodeDefinition16,
      ShaderNodeKind shaderNodeKind21
   ) {
      renderManager34.invoke5(
         rect61.x(),
         rect61.y(),
         rect61.w(),
         rect61.h(),
         metrics42.measure(8.0F),
         ColorScheme.compute5(255, 255, 255, this.check4(colorScheme37) ? 34 : 6)
      );
      renderManager34.invoke28(
         rect61.x(),
         rect61.y(),
         rect61.w(),
         rect61.h(),
         metrics42.measure(8.0F),
         ColorScheme.compute6(colorScheme37.getIntValue15(), 92),
         0.55F
      );
      renderManager34.invoke5(
         rect61.x(),
         rect61.y() + metrics42.measure(8.0F),
         metrics42.measure(2.2F),
         rect61.h() - metrics42.measure(16.0F),
         metrics42.measure(1.1F),
         ColorScheme.compute6(colorScheme37.getIntValue15(), 190)
      );
      ClickGuiRenderUtils.invoke3(
         renderManager34,
         metrics42,
         FontRegistry.fontObject4,
         rect61.x() + metrics42.measure(12.0F),
         rect61.y() + metrics42.measure(8.0F),
         9.0F,
         "Result",
         ColorScheme.compute6(colorScheme37.getIntValue15(), 224)
      );
      String text39 = this.namedShaderProgram.resolve3().isBlank() ? "cold" : "#" + this.namedShaderProgram.resolve3();
      float floatValue181 = rect61.x() + metrics42.measure(12.0F);
      float floatValue182 = rect61.y() + metrics42.measure(26.0F);
      this.invoke40(renderManager34, metrics42, colorScheme37, floatValue181, floatValue182, "Hash", text39);
      this.invoke40(renderManager34, metrics42, colorScheme37, floatValue181, floatValue182 + metrics42.measure(18.0F), "State", this.resolve2());
      this.invoke40(renderManager34, metrics42, colorScheme37, floatValue181, floatValue182 + metrics42.measure(36.0F), "Target", this.shaderSurface.getText2());
      this.invoke40(
         renderManager34,
         metrics42,
         colorScheme37,
         floatValue181,
         floatValue182 + metrics42.measure(54.0F),
         "Uniforms",
         String.valueOf(this.shaderSourceBuilder.resolve2(this.shaderNode).exposedUniforms().size())
      );
      float floatValue183 = floatValue182 + metrics42.measure(76.0F);

      for (ShaderPin shaderPin3 : shaderNodeDefinition16.getItems()) {
         if (floatValue183 > rect61.y() + rect61.h() - metrics42.measure(14.0F)) {
            break;
         }

         boolean flag37 = this.shaderNode.resolve4(shaderNodeKind21.getText(), shaderPin3.id()) != null;
         int intValue37 = this.compute10(shaderPin3, colorScheme37);
         renderManager34.invoke39(
            floatValue181 + metrics42.measure(3.0F),
            floatValue183 + metrics42.measure(4.4F),
            metrics42.measure(2.6F),
            0.0F,
            1.0F,
            ColorScheme.compute6(intValue37, flag37 ? 245 : 130)
         );
         ClickGuiRenderUtils.invoke3(
            renderManager34,
            metrics42,
            FontRegistry.fontObject,
            floatValue181 + metrics42.measure(12.0F),
            floatValue183,
            8.0F,
            ClickGuiRenderUtils.resolve4(
               metrics42,
               FontRegistry.fontObject,
               shaderPin3.label() + (flag37 ? " / linked" : " / not connected"),
               8.0F,
               rect61.w() - metrics42.measure(60.0F)
            ),
            flag37 ? this.compute11(colorScheme37) : this.compute12(colorScheme37)
         );
         ClickGuiRenderUtils.invoke3(
            renderManager34,
            metrics42,
            FontRegistry.fontObject,
            rect61.x() + rect61.w() - metrics42.measure(38.0F),
            floatValue183,
            7.0F,
            shaderPin3.type().getText(),
            ColorScheme.compute6(intValue37, flag37 ? 220 : 150)
         );
         floatValue183 += metrics42.measure(17.0F);
      }
   }

   private void invoke54(
      RenderManager renderManager35,
      Metrics metrics43,
      ColorScheme colorScheme38,
      Rect rect62,
      String string,
      List<ShaderPin> list,
      ShaderNodeKind shaderNodeKind22,
      boolean bl
   ) {
      renderManager35.invoke5(
         rect62.x(),
         rect62.y(),
         rect62.w(),
         rect62.h(),
         metrics43.measure(8.0F),
         ColorScheme.compute5(255, 255, 255, this.check4(colorScheme38) ? 34 : 6)
      );
      renderManager35.invoke28(
         rect62.x(), rect62.y(), rect62.w(), rect62.h(), metrics43.measure(8.0F), colorScheme38.getIntValue6(), 0.55F
      );
      ClickGuiRenderUtils.invoke3(
         renderManager35,
         metrics43,
         FontRegistry.fontObject4,
         rect62.x() + metrics43.measure(10.0F),
         rect62.y() + metrics43.measure(8.0F),
         9.0F,
         string,
         ColorScheme.compute6(colorScheme38.getIntValue15(), 220)
      );
      float floatValue184 = rect62.y() + metrics43.measure(28.0F);

      for (ShaderPin shaderPin4 : list) {
         if (floatValue184 > rect62.y() + rect62.h() - metrics43.measure(18.0F)) {
            break;
         }

         int intValue38 = this.compute10(shaderPin4, colorScheme38);
         boolean flag38 = bl
            ? this.shaderNode.resolve4(shaderNodeKind22.getText(), shaderPin4.id()) != null
            : this.shaderNode
               .getItems()
               .stream()
               .anyMatch(shaderConnection2 -> shaderConnection2.getText().equals(shaderNodeKind22.getText()) && shaderConnection2.getText2().equals(shaderPin4.id()));
         renderManager35.invoke39(
            rect62.x() + metrics43.measure(12.0F),
            floatValue184 + metrics43.measure(6.0F),
            metrics43.measure(2.6F),
            0.0F,
            1.0F,
            ColorScheme.compute6(intValue38, flag38 ? 245 : 130)
         );
         ClickGuiRenderUtils.invoke3(
            renderManager35,
            metrics43,
            FontRegistry.fontObject,
            rect62.x() + metrics43.measure(22.0F),
            floatValue184,
            8.0F,
            ClickGuiRenderUtils.resolve4(metrics43, FontRegistry.fontObject, shaderPin4.label(), 8.0F, rect62.w() - metrics43.measure(62.0F)),
            flag38 ? this.compute11(colorScheme38) : this.compute12(colorScheme38)
         );
         ClickGuiRenderUtils.invoke3(
            renderManager35,
            metrics43,
            FontRegistry.fontObject,
            rect62.x() + rect62.w() - metrics43.measure(38.0F),
            floatValue184,
            7.0F,
            shaderPin4.type().getText(),
            ColorScheme.compute6(intValue38, flag38 ? 220 : 150)
         );
         floatValue184 += metrics43.measure(17.0F);
      }
   }

   private void invoke55(
      RenderManager renderManager36,
      Metrics metrics44,
      ColorScheme colorScheme39,
      ShaderNodeKind shaderNodeKind23,
      String string,
      String string2,
      Rect rect63,
      int i,
      ClickGuiState clickGuiState24
   ) {
      Rect rect64 = this.resolve35(rect63, metrics44, i);
      ClickGuiRenderUtils.invoke4(
         renderManager36,
         metrics44,
         FontRegistry.fontObject,
         rect63.x() + metrics44.measure(14.0F),
         rect64.y(),
         rect64.h(),
         9.0F,
         string2,
         colorScheme39.getIntValue12()
      );
      String text40 = this.resolve38(shaderNodeKind23, string);
      ShaderUniformEditor shaderUniformEditor15 = this.resolve40(text40);
      if (!shaderUniformEditor15.check()) {
         shaderUniformEditor15.setText(shaderNodeKind23.resolve(string, this.resolve44(shaderNodeKind23)));
      }

      shaderUniformEditor15.invoke5(renderManager36, metrics44, colorScheme39, rect64, clickGuiState24.getFloatValue(), clickGuiState24.getFloatValue2());
   }

   private void invoke56(
      RenderManager renderManager37,
      Metrics metrics45,
      ColorScheme colorScheme40,
      ShaderNodeKind shaderNodeKind24,
      String string,
      String string2,
      float f,
      float g,
      float h,
      float i,
      Rect rect65,
      int j,
      ClickGuiState clickGuiState25
   ) {
      Rect rect66 = this.resolve35(rect65, metrics45, j);
      ClickGuiRenderUtils.invoke4(
         renderManager37,
         metrics45,
         FontRegistry.fontObject,
         rect65.x() + metrics45.measure(14.0F),
         rect66.y(),
         rect66.h(),
         9.0F,
         string2,
         colorScheme40.getIntValue12()
      );
      String text41 = this.resolve38(shaderNodeKind24, string);
      ShaderUniformEditor shaderUniformEditor16 = this.resolve39(text41, f, g, h);
      if (!shaderUniformEditor16.check()) {
         shaderUniformEditor16.setFloatValue5(shaderNodeKind24.measure(string, i));
      }

      shaderUniformEditor16.invoke5(renderManager37, metrics45, colorScheme40, rect66, clickGuiState25.getFloatValue(), clickGuiState25.getFloatValue2());
   }

   private void invoke57(RenderManager renderManager38, ClickGuiState clickGuiState26, ThemeContext themeContext17) {
      ArrayList arrayList9 = new ArrayList<>(this.shaderNode.resolve2());
      ((List<ShaderNodeKind>)arrayList9).sort(Comparator.comparing(shaderNodeKind25 -> this.check8(shaderNodeKind25.getText())));

      for (ShaderNodeKind shaderNodeKind26 : (List<ShaderNodeKind>)arrayList9) {
         this.invoke58(renderManager38, clickGuiState26, themeContext17, shaderNodeKind26);
      }
   }

   private void invoke58(RenderManager renderManager39, ClickGuiState clickGuiState27, ThemeContext themeContext18, ShaderNodeKind shaderNodeKind27) {
      ShaderNodeDefinition shaderNodeDefinition17 = this.shaderNodeRegistry.resolve(shaderNodeKind27.getText2());
      if (shaderNodeDefinition17 != null) {
         Metrics metrics46 = themeContext18.getMetrics();
         ColorScheme colorScheme41 = themeContext18.getColorScheme();
         float floatValue185 = this.measure14(shaderNodeKind27.getFloatValue());
         float floatValue186 = this.measure15(shaderNodeKind27.getFloatValue2());
         float floatValue187 = shaderNodeDefinition17.getFloatValue() * this.floatValue3;
         ClampedSpringAnimation clampedSpringAnimation2 = this.resolve30(shaderNodeKind27.getText());
         clampedSpringAnimation2.invoke2(Boolean.TRUE.equals(this.valuesByKey9.get(shaderNodeKind27.getText())) ? 1.0F : 0.0F);
         float floatValue188 = clampedSpringAnimation2.measure();
         float floatValue189 = this.measure11(shaderNodeDefinition17);
         float floatValue190 = this.measure12(shaderNodeDefinition17, shaderNodeKind27) * this.floatValue3;
         float floatValue191 = Math.max(metrics46.measure(6.0F), 10.0F * this.floatValue3);
         boolean flag39 = this.check8(shaderNodeKind27.getText());
         boolean flag40 = clickGuiState27.getFloatValue() >= floatValue185
            && clickGuiState27.getFloatValue() < floatValue185 + floatValue187
            && clickGuiState27.getFloatValue2() >= floatValue186
            && clickGuiState27.getFloatValue2() < floatValue186 + floatValue190;
         SpringAnimation springAnimation = this.valuesByKey7.computeIfAbsent(shaderNodeKind27.getText(), string -> new SpringAnimation(0.0F));
         float floatValue192 = springAnimation.measure(flag40 ? 1.0F : 0.0F, SpringSpec.resolve12());
         ClampedSpringAnimation clampedSpringAnimation3 = this.valuesByKey8.computeIfAbsent(shaderNodeKind27.getText(), string -> this.resolve17());
         clampedSpringAnimation3.invoke2(flag39 ? 1.0F : (flag40 ? 0.38F : 0.0F));
         float floatValue193 = measure20(clampedSpringAnimation3.measure());
         SpringAnimation springAnimation2 = this.valuesByKey6.computeIfAbsent(shaderNodeKind27.getText(), string -> new SpringAnimation(1.0F));
         float floatValue194 = springAnimation2.measure(1.0F, SpringSpec.resolve10());
         float floatValue195 = Math.max(floatValue193, floatValue192 * 0.45F);
         float floatValue196 = Math.min(1.0F, (Math.abs(this.floatValue21) + Math.abs(this.floatValue22)) * 0.0012F);
         float floatValue197 = Math.max(0.001F, floatValue194) * (1.0F + floatValue195 * 0.016F + floatValue196 * (flag40 ? 0.006F : 0.0F));
         renderManager39.invoke62(floatValue197, floatValue185 + floatValue187 * 0.5F, floatValue186 + floatValue190 * 0.5F);

         try {
            boolean flag41 = ShaderNodeSurfaceRenderer.getINSTANCE()
               .check(
                  renderManager39,
                  floatValue185,
                  floatValue186,
                  floatValue187,
                  floatValue190,
                  floatValue191,
                  floatValue192,
                  floatValue193,
                  floatValue196,
                  colorScheme41,
                  this.floatValue19,
                  this.floatValue20,
                  this.intValue5,
                  this.intValue6,
                  this.check4(colorScheme41)
               );
            if (!flag41) {
               if (floatValue192 > 0.001F) {
                  renderManager39.invoke41(
                     floatValue185,
                     floatValue186,
                     floatValue187,
                     floatValue190,
                     floatValue191,
                     metrics46.measure(12.0F) * floatValue192,
                     metrics46.measure(1.1F),
                     ColorScheme.compute6(colorScheme41.getIntValue15(), Math.round(34.0F * floatValue192))
                  );
               }

               if (floatValue193 > 0.001F) {
                  float floatValue198 = 0.86F + 0.14F * (float)Math.sin((float)(System.currentTimeMillis() % 2200L) / 2200.0F * Math.PI * 2.0);
                  this.invoke70(renderManager39, floatValue185, floatValue186, floatValue187, floatValue190, floatValue191, colorScheme41.getIntValue14(), floatValue193 * floatValue198 * 0.62F, metrics46);
               }

               renderManager39.invoke41(
                  floatValue185,
                  floatValue186 + metrics46.measure(2.0F),
                  floatValue187,
                  floatValue190,
                  floatValue191,
                  metrics46.measure(11.0F),
                  metrics46.measure(1.0F),
                  this.compute4(colorScheme41, Math.round(82.0F + 24.0F * floatValue193))
               );
               renderManager39.invoke44(floatValue185, floatValue186, floatValue187, floatValue190, floatValue191, 0.24F + 0.08F * floatValue193);
               renderManager39.invoke5(floatValue185, floatValue186, floatValue187, floatValue190, floatValue191, this.compute2(colorScheme41, Math.round(194.0F + 20.0F * floatValue193)));
               if (flag40) {
                  renderManager39.invoke5(
                     floatValue185 + 1.2F * this.floatValue3,
                     floatValue186 + 1.2F * this.floatValue3,
                     floatValue187 - 2.4F * this.floatValue3,
                     floatValue190 - 2.4F * this.floatValue3,
                     Math.max(0.0F, floatValue191 - 1.2F * this.floatValue3),
                     ColorScheme.compute6(colorScheme41.getIntValue14(), Math.round(10.0F * floatValue192))
                  );
               }

               renderManager39.invoke28(
                  floatValue185,
                  floatValue186,
                  floatValue187,
                  floatValue190,
                  floatValue191,
                  ColorScheme.compute7(colorScheme41.getIntValue6(), ColorScheme.compute6(colorScheme41.getIntValue14(), 118), Math.max(floatValue192 * 0.48F, floatValue193 * 0.72F)),
                  0.55F
               );
            }

            int intValue39 = this.compute11(colorScheme41);
            int intValue40 = this.compute12(colorScheme41);
            ClickGuiRenderUtils.invoke3(
               renderManager39,
               metrics46,
               FontRegistry.fontObject4,
               floatValue185 + 14.0F * this.floatValue3,
               floatValue186 + 12.0F * this.floatValue3,
               11.0F * this.floatValue3 / Math.max(0.001F, metrics46.getFloatValue()),
               shaderNodeDefinition17.getText2(),
               intValue39
            );
            ClickGuiRenderUtils.invoke3(
               renderManager39,
               metrics46,
               FontRegistry.fontObject,
               floatValue185 + 14.0F * this.floatValue3,
               floatValue186 + 28.0F * this.floatValue3,
               8.5F * this.floatValue3 / Math.max(0.001F, metrics46.getFloatValue()),
               shaderNodeDefinition17.getText3(),
               intValue40
            );
            if (shaderNodeDefinition17.isFlag()) {
               String text42 = "Preview";
               float floatValue199 = 7.5F * this.floatValue3 / Math.max(0.001F, metrics46.getFloatValue());
               float floatValue200 = ClickGuiRenderUtils.measure2(metrics46, FontRegistry.fontObject, text42, floatValue199);
               Rect rect67 = this.resolve31(shaderNodeDefinition17, floatValue185, floatValue186, floatValue187);
               int intValue41 = ColorScheme.compute7(
                  ColorScheme.compute6(colorScheme41.getIntValue14(), this.check4(colorScheme41) ? 32 : 44), ColorScheme.compute6(colorScheme41.getIntValue15(), 116), floatValue188
               );
               renderManager39.invoke5(rect67.x(), rect67.y(), rect67.w(), rect67.h(), rect67.h() * 0.5F, intValue41);
               renderManager39.invoke28(
                  rect67.x(),
                  rect67.y(),
                  rect67.w(),
                  rect67.h(),
                  rect67.h() * 0.5F,
                  ColorScheme.compute6(colorScheme41.getIntValue14(), Math.round(70.0F + 92.0F * floatValue188)),
                  0.55F
               );
               ClickGuiRenderUtils.invoke4(
                  renderManager39, metrics46, FontRegistry.fontObject, rect67.x() + (rect67.w() - floatValue200) * 0.5F, rect67.y(), rect67.h(), floatValue199, text42, intValue39
               );
            }

            renderManager39.invoke20();
            renderManager39.invoke24(
               floatValue185 + 1.2F,
               floatValue186 + 1.2F,
               floatValue187 - 2.4F,
               floatValue190 - 2.4F,
               Math.max(0.0F, floatValue191 - 1.2F),
               Math.max(0.0F, floatValue191 - 1.2F),
               Math.max(0.0F, floatValue191 - 1.2F),
               Math.max(0.0F, floatValue191 - 1.2F)
            );

            try {
               this.invoke59(renderManager39, themeContext18, shaderNodeKind27, floatValue185, floatValue186, floatValue187);
               this.invoke60(renderManager39, themeContext18, shaderNodeKind27, shaderNodeDefinition17, floatValue185, floatValue186, floatValue187, floatValue189, floatValue188);
            } finally {
               renderManager39.invoke20();
               renderManager39.invoke25();
            }

            this.invoke66(renderManager39, themeContext18, shaderNodeKind27, shaderNodeDefinition17, floatValue185, floatValue186);
         } finally {
            renderManager39.invoke64();
         }
      }
   }

   private void invoke59(RenderManager renderManager40, ThemeContext themeContext19, ShaderNodeKind shaderNodeKind28, float f, float g, float h) {
      Metrics metrics47 = themeContext19.getMetrics();
      ColorScheme colorScheme42 = themeContext19.getColorScheme();
      if (this.check25(shaderNodeKind28)) {
         Rect rect68 = this.resolve42(shaderNodeKind28);
         ShaderUniformEditor shaderUniformEditor17 = this.resolve15(shaderNodeKind28);
         if (!shaderUniformEditor17.check()) {
            shaderUniformEditor17.setText(shaderNodeKind28.resolve("name", this.resolve44(shaderNodeKind28)));
         }

         shaderUniformEditor17.invoke5(renderManager40, metrics47, colorScheme42, rect68, this.floatValue19, this.floatValue20);
      } else if (check16(shaderNodeKind28.getText2())) {
         Rect rect69 = this.resolve41(shaderNodeKind28);
         ShaderUniformEditor shaderUniformEditor18 = this.resolve14(shaderNodeKind28);
         if (!shaderUniformEditor18.check()) {
            shaderUniformEditor18.setFloatValue5(shaderNodeKind28.measure("value", "int_value".equals(shaderNodeKind28.getText2()) ? 1.0F : 0.5F));
         }

         shaderUniformEditor18.invoke5(renderManager40, metrics47, colorScheme42, rect69, this.floatValue19, this.floatValue20);
      }
   }

   private void invoke60(
      RenderManager renderManager41,
      ThemeContext themeContext20,
      ShaderNodeKind shaderNodeKind29,
      ShaderNodeDefinition shaderNodeDefinition18,
      float f,
      float g,
      float h,
      float i,
      float j
   ) {
      if (shaderNodeKind29 != null && shaderNodeDefinition18 != null && shaderNodeDefinition18.isFlag() && !(j <= 0.01F)) {
         Metrics metrics48 = themeContext20.getMetrics();
         ColorScheme colorScheme43 = themeContext20.getColorScheme();
         float floatValue201 = f + 6.0F * this.floatValue3;
         float floatValue202 = g + (i + 4.0F) * this.floatValue3;
         float floatValue203 = Math.max(1.0F, h - 12.0F * this.floatValue3);
         float floatValue204 = Math.max(1.0F, 120.0F * this.floatValue3 * j);
         float floatValue205 = Math.max(metrics48.measure(5.0F), 8.0F * this.floatValue3);
         renderManager41.invoke5(floatValue201, floatValue202, floatValue203, floatValue204, floatValue205, ColorScheme.compute5(5, 7, 12, Math.round(156.0F * j)));
         renderManager41.invoke28(floatValue201, floatValue202, floatValue203, floatValue204, floatValue205, ColorScheme.compute6(colorScheme43.getIntValue14(), Math.round(62.0F * j)), 0.65F);
         if (!(this.floatValue3 < 0.6F) && !(floatValue204 < 14.0F)) {
            renderManager41.invoke20();
            renderManager41.invoke24(floatValue201, floatValue202, floatValue203, floatValue204, floatValue205, floatValue205, floatValue205, floatValue205);
            boolean flag42 = false ;

            try {
               flag42 = true;
               this.shaderNodePreviewRenderer
                  .invoke(
                     this.shaderNode,
                     shaderNodeKind29.getText(),
                     this.namedShaderProgram,
                     renderManager41,
                     floatValue201,
                     floatValue202,
                     floatValue203,
                     floatValue204,
                     this.compute13(),
                     this.compute14(),
                     colorScheme43,
                     j
                  );
               flag42 = false;
            } finally {
               if (flag42) {
                  renderManager41.invoke20();
                  renderManager41.invoke25();
               }
            }

            renderManager41.invoke20();
            renderManager41.invoke25();
         }
      }
   }

   private ShaderUniformEditor resolve14(ShaderNodeKind shaderNodeKind30) {
      return this.valuesByKey3
         .computeIfAbsent(
            shaderNodeKind30.getText(),
            string -> "int_value".equals(shaderNodeKind30.getText2()) ? ShaderUniformEditor.resolve(-64.0F, 64.0F) : ShaderUniformEditor.resolve(-12.0F, 12.0F)
         );
   }

   private static boolean check16(String string) {
      return "float_value".equals(string) || "int_value".equals(string);
   }

   private static float measure6(ShaderNodeKind shaderNodeKind31, float f) {
      return "int_value".equals(shaderNodeKind31.getText2()) ? Math.round(f) : f;
   }

   private ShaderUniformEditor resolve15(ShaderNodeKind shaderNodeKind32) {
      return this.valuesByKey4.computeIfAbsent(shaderNodeKind32.getText(), string -> ShaderUniformEditor.resolve2());
   }

   private void invoke61() {
      if (this.text8 != null) {
         ShaderUniformEditor shaderUniformEditor19 = this.valuesByKey3.get(this.text8);
         if (shaderUniformEditor19 != null) {
            if (shaderUniformEditor19.isFlag()) {
               shaderUniformEditor19.invoke3();
            }

            ShaderNodeKind shaderNodeKind33 = this.shaderNode.resolve3(this.text8);
            if (shaderNodeKind33 != null) {
               shaderNodeKind33.invoke2("value", measure6(shaderNodeKind33, shaderUniformEditor19.getFloatValue5()));
               this.shaderNode.invoke4();
            }
         }

         this.text8 = null;
      }
   }

   private void invoke62() {
      if (this.text9 != null) {
         ShaderUniformEditor shaderUniformEditor20 = this.valuesByKey4.get(this.text9);
         if (shaderUniformEditor20 != null) {
            if (shaderUniformEditor20.isFlag()) {
               shaderUniformEditor20.invoke3();
            }

            ShaderNodeKind shaderNodeKind34 = this.shaderNode.resolve3(this.text9);
            if (shaderNodeKind34 != null) {
               shaderNodeKind34.invoke3("name", shaderUniformEditor20.getText());
               this.shaderNode.invoke4();
            }
         }

         this.text9 = null;
      }
   }

   private void invoke63() {
      if (this.text10 != null) {
         ShaderUniformEditor shaderUniformEditor21 = this.valuesByKey5.get(this.text10);
         if (shaderUniformEditor21 != null) {
            if (shaderUniformEditor21.isFlag()) {
               shaderUniformEditor21.invoke3();
            }

            this.invoke64(this.text10);
         }

         this.text10 = null;
      }
   }

   private void invoke64(String string) {
      if (string != null) {
         int intValue42 = string.indexOf(58);
         if (intValue42 > 0 && intValue42 < string.length() - 1) {
            ShaderNodeKind shaderNodeKind35 = this.shaderNode.resolve3(string.substring(0, intValue42));
            ShaderUniformEditor shaderUniformEditor22 = this.valuesByKey5.get(string);
            if (shaderNodeKind35 != null && shaderUniformEditor22 != null) {
               String text43 = string.substring(intValue42 + 1);
               if ("name".equals(text43)) {
                  shaderNodeKind35.invoke3("name", shaderUniformEditor22.getText());
               } else {
                  shaderNodeKind35.invoke2(text43, shaderUniformEditor22.getFloatValue5());
                  this.invoke65(shaderNodeKind35, text43);
               }

               this.shaderNode.invoke4();
            }
         }
      }
   }

   private void invoke65(ShaderNodeKind shaderNodeKind36, String string) {
      if (shaderNodeKind36 != null) {
         if ("step".equals(string)) {
            shaderNodeKind36.invoke2("step", Math.max(1.0E-4F, shaderNodeKind36.measure("step", 0.01F)));
         } else {
            float floatValue206 = shaderNodeKind36.measure("min", 0.0F);
            float floatValue207 = shaderNodeKind36.measure("max", 1.0F);
            if (floatValue207 <= floatValue206) {
               if ("min".equals(string)) {
                  shaderNodeKind36.invoke2("max", floatValue206 + 0.001F);
               } else {
                  shaderNodeKind36.invoke2("min", floatValue207 - 0.001F);
               }
            }
         }
      }
   }

   private boolean check17(ClickGuiState clickGuiState28, Metrics metrics49, int i, float f, float g) {
      Rect rect70 = this.resolve49(metrics49);
      if (!rect70.contains(f, g)) {
         this.flag11 = false;
      }

      if (this.resolve46(metrics49, i).contains(f, g)) {
         clickGuiState28.setFlag24(false);
         this.invoke80();
         return true;
      } else if (rect70.contains(f, g)) {
         this.flag11 = true;
         this.timestamp7 = System.currentTimeMillis();
         return true;
      } else if (this.resolve48(metrics49).contains(f, g)) {
         this.flag3 = !this.flag3;
         this.flag4 = false;
         this.flag9 = false;
         this.flag5 = false;
         return true;
      } else if (this.resolve50(metrics49).contains(f, g)) {
         this.flag4 = !this.flag4;
         this.flag3 = false;
         this.flag9 = false;
         this.flag5 = false;
         return true;
      } else if (this.resolve51(metrics49, i).contains(f, g)) {
         this.flag9 = !this.flag9;
         this.flag3 = false;
         this.flag4 = false;
         this.flag5 = false;
         this.floatValue24 = 0.0F;
         return true;
      } else if (this.resolve47(metrics49, i).contains(f, g)) {
         this.flag5 = !this.flag5;
         this.flag3 = false;
         this.flag4 = false;
         this.flag9 = false;
         return true;
      } else {
         return false;
      }
   }

   private boolean check18(Metrics metrics50, int i, float f, float g, int j) {
      Rect rect71 = this.resolve61(metrics50);
      if (j != 0) {
         return rect71.contains(f, g);
      } else if (!rect71.contains(f, g)) {
         this.flag3 = false;
         return true;
      } else {
         for (int intValue43 = 0; intValue43 < SAVE_AS.length; intValue43++) {
            if (this.resolve62(rect71, metrics50, intValue43).contains(f, g)) {
               if (intValue43 == 0) {
                  this.invoke25();
               } else if (intValue43 == 1) {
                  this.invoke91();
               } else if (intValue43 == 2) {
                  this.invoke92();
               } else if (intValue43 == 3) {
                  this.invoke90();
               } else if (intValue43 == 4) {
                  this.invoke26();
               } else if (intValue43 == 5) {
                  this.invoke85();
               }

               return true;
            }
         }

         return true;
      }
   }

   private boolean check19(Metrics metrics51, int i, int j, float f, float g, int k) {
      Rect rect72 = this.resolve63(metrics51, i, j);
      if (k != 0) {
         return rect72.contains(f, g);
      } else if (!rect72.contains(f, g)) {
         this.flag4 = false;
         return false;
      } else {
         ShaderSurface[] shaderSurfaces2 = ShaderSurface.resolve3();

         for (int intValue44 = 0; intValue44 < shaderSurfaces2.length; intValue44++) {
            Rect rect73 = this.resolve64(rect72, metrics51, intValue44);
            if (rect73.contains(f, g)) {
               if (ShaderPresetRegistry.getINSTANCE().check(shaderSurfaces2[intValue44]) && this.resolve8(rect73, metrics51).contains(f, g)) {
                  this.invoke33(shaderSurfaces2[intValue44]);
                  return true;
               }

               this.invoke86(shaderSurfaces2[intValue44]);
               return true;
            }
         }

         String[] texts3 = new String[]{"Host Rectangle", "Inset Shape", "Full Quad"};

         for (int intValue45 = 0; intValue45 < texts3.length; intValue45++) {
            if (this.resolve65(rect72, metrics51, intValue45).contains(f, g)) {
               this.invoke81();
               this.hostRectangle = texts3[intValue45];
               this.invoke96();
               this.shaderNode.invoke4();
               this.namedShaderProgram.invoke(this.shaderSurface);
               this.namedShaderProgram.invoke2(this.shaderNode);
               this.setReady(this.hostRectangle);
               return true;
            }
         }

         return true;
      }
   }

   private boolean check20(Metrics metrics52, int i, float f, float g, int j) {
      Rect rect74 = this.resolve66(metrics52, i);
      if (j != 0) {
         return rect74.contains(f, g);
      } else if (!rect74.contains(f, g)) {
         this.flag5 = false;
         return false;
      } else {
         ShaderFoundryScreen.ShaderFoundryScreenState[] w292s2 = ShaderFoundryScreen.ShaderFoundryScreenState.values();

         for (int intValue46 = 0; intValue46 < w292s2.length; intValue46++) {
            if (this.resolve67(rect74, metrics52, intValue46).contains(f, g)) {
               this.shaderFoundryScreenState = w292s2[intValue46];
               this.setReady("theme " + this.shaderFoundryScreenState.getText());
               return true;
            }
         }

         return true;
      }
   }

   private boolean check21(Metrics metrics53, int i, int j, float f, float g, int k) {
      if (k != 0) {
         return true;
      } else {
         Rect rect75 = this.resolve68(metrics53, i, j);
         if (this.resolve69(rect75, metrics53).contains(f, g)) {
            this.invoke24();
            this.invoke87(this.shaderSurface2);
            this.flag6 = false;
            this.shaderSurface2 = null;
            return true;
         } else if (this.resolve70(rect75, metrics53).contains(f, g)) {
            this.invoke87(this.shaderSurface2);
            this.flag6 = false;
            this.shaderSurface2 = null;
            return true;
         } else if (!this.resolve71(rect75, metrics53).contains(f, g) && rect75.contains(f, g)) {
            return true;
         } else {
            this.flag6 = false;
            this.shaderSurface2 = null;
            return true;
         }
      }
   }

   private boolean check22(Metrics metrics54, int i, int j, float f, float g, int k) {
      Rect rect76 = this.resolve34(metrics54, i, j);
      if (!rect76.contains(f, g)) {
         return false;
      } else {
         ShaderNodeKind shaderNodeKind37 = this.resolve37();
         if (shaderNodeKind37 == null) {
            this.invoke63();
            return true;
         } else {
            ShaderNodeDefinition shaderNodeDefinition19 = this.shaderNodeRegistry.resolve(shaderNodeKind37.getText2());
            if (shaderNodeDefinition19 == null) {
               this.invoke63();
               return true;
            } else if (k == 0 && shaderNodeDefinition19.isFlag() && this.resolve36(rect76, metrics54).contains(f, g)) {
               this.invoke78(shaderNodeKind37.getText());
               return true;
            } else if (k != 0) {
               return true;
            } else if ("float_value".equals(shaderNodeKind37.getText2())) {
               return this.check24(shaderNodeKind37, "value", -12.0F, 12.0F, 0.01F, 0.5F, rect76, metrics54, 0, f, g, k);
            } else if ("int_value".equals(shaderNodeKind37.getText2())) {
               return this.check24(shaderNodeKind37, "value", -64.0F, 64.0F, 1.0F, 1.0F, rect76, metrics54, 0, f, g, k);
            } else {
               if ("exposed_float".equals(shaderNodeKind37.getText2())) {
                  if (this.check23(shaderNodeKind37, "name", rect76, metrics54, 0, f, g, k)) {
                     return true;
                  }

                  float floatValue208 = shaderNodeKind37.measure("min", 0.0F);
                  float floatValue209 = shaderNodeKind37.measure("max", 1.0F);
                  if (floatValue209 <= floatValue208) {
                     floatValue209 = floatValue208 + 0.001F;
                  }

                  if (this.check24(shaderNodeKind37, "value", floatValue208, floatValue209, shaderNodeKind37.measure("step", 0.01F), 0.5F, rect76, metrics54, 1, f, g, k)) {
                     return true;
                  }

                  if (this.check24(shaderNodeKind37, "min", -128.0F, 128.0F, 0.01F, 0.0F, rect76, metrics54, 2, f, g, k)) {
                     return true;
                  }

                  if (this.check24(shaderNodeKind37, "max", -128.0F, 128.0F, 0.01F, 1.0F, rect76, metrics54, 3, f, g, k)) {
                     return true;
                  }

                  if (this.check24(shaderNodeKind37, "step", 1.0E-4F, 16.0F, 0.001F, 0.01F, rect76, metrics54, 4, f, g, k)) {
                     return true;
                  }
               }

               if ("exposed_color".equals(shaderNodeKind37.getText2())) {
                  if (this.check23(shaderNodeKind37, "name", rect76, metrics54, 0, f, g, k)) {
                     return true;
                  }

                  if (this.check24(shaderNodeKind37, "r", 0.0F, 1.0F, 0.01F, 1.0F, rect76, metrics54, 1, f, g, k)) {
                     return true;
                  }

                  if (this.check24(shaderNodeKind37, "g", 0.0F, 1.0F, 0.01F, 1.0F, rect76, metrics54, 2, f, g, k)) {
                     return true;
                  }

                  if (this.check24(shaderNodeKind37, "b", 0.0F, 1.0F, 0.01F, 1.0F, rect76, metrics54, 3, f, g, k)) {
                     return true;
                  }

                  if (this.check24(shaderNodeKind37, "a", 0.0F, 1.0F, 0.01F, 1.0F, rect76, metrics54, 4, f, g, k)) {
                     return true;
                  }
               }

               this.invoke63();
               return true;
            }
         }
      }
   }

   private boolean check23(
      ShaderNodeKind shaderNodeKind38, String string, Rect rect77, Metrics metrics55, int i, float f, float g, int j
   ) {
      Rect rect78 = this.resolve35(rect77, metrics55, i);
      if (!rect78.contains(f, g)) {
         return false;
      } else {
         this.invoke81();
         this.invoke63();
         String text44 = this.resolve38(shaderNodeKind38, string);
         ShaderUniformEditor shaderUniformEditor23 = this.resolve40(text44);
         shaderUniformEditor23.setText(shaderNodeKind38.resolve(string, this.resolve44(shaderNodeKind38)));
         if (shaderUniformEditor23.check2(f, g, j, rect78)) {
            this.text10 = text44;
         }

         return true;
      }
   }

   private boolean check24(
      ShaderNodeKind shaderNodeKind39,
      String string,
      float f,
      float g,
      float h,
      float i,
      Rect rect79,
      Metrics metrics56,
      int j,
      float k,
      float l,
      int m
   ) {
      Rect rect80 = this.resolve35(rect79, metrics56, j);
      if (!rect80.contains(k, l)) {
         return false;
      } else {
         this.invoke81();
         this.invoke63();
         String text45 = this.resolve38(shaderNodeKind39, string);
         ShaderUniformEditor shaderUniformEditor24 = this.resolve39(text45, f, g, h);
         shaderUniformEditor24.setFloatValue5(shaderNodeKind39.measure(string, i));
         if (shaderUniformEditor24.check2(k, l, m, rect80)) {
            this.text10 = text45;
         }

         return true;
      }
   }

   private void invoke66(
      RenderManager renderManager42, ThemeContext themeContext21, ShaderNodeKind shaderNodeKind40, ShaderNodeDefinition shaderNodeDefinition20, float f, float g
   ) {
      Metrics metrics57 = themeContext21.getMetrics();
      ColorScheme colorScheme44 = themeContext21.getColorScheme();
      ShaderPinRenderer shaderPinRenderer = ShaderPinRenderer.getINSTANCE();
      boolean flag43 = shaderPinRenderer.check(renderManager42, this.intValue5, this.intValue6);
      int intValue47 = this.check4(colorScheme44) ? ColorScheme.compute5(255, 255, 255, 245) : ColorScheme.compute5(8, 10, 16, 240);

      for (int intValue48 = 0; intValue48 < shaderNodeDefinition20.getItems().size(); intValue48++) {
         ShaderPin shaderPin5 = shaderNodeDefinition20.getItems().get(intValue48);
         float floatValue210 = g + this.measure13(intValue48) * this.floatValue3;
         float floatValue211 = this.measure7(shaderNodeKind40.getText(), shaderPin5.id(), ShaderPinDirection.INPUT, f, floatValue210);
         float[] floatValues = this.resolve16(f, floatValue210, floatValue211);
         float floatValue212 = floatValues[0];
         floatValue210 = floatValues[1];
         int intValue49 = this.compute10(shaderPin5, colorScheme44);
         if (flag43) {
            float floatValue213 = Math.max(3.4F, 4.6F * this.floatValue3) + 3.4F * floatValue211;
            float floatValue214 = Math.max(1.8F, 2.1F * this.floatValue3);
            shaderPinRenderer.invoke(
               renderManager42, floatValue212, floatValue210, floatValue213, floatValue214, intValue49, intValue47, floatValue211, measure10(shaderNodeKind40.getText().hashCode() * 0.0031F + intValue48 * 0.173F)
            );
         } else {
            if (floatValue211 > 0.001F) {
               renderManager42.invoke41(
                  floatValue212 - 5.0F * this.floatValue3,
                  floatValue210 - 5.0F * this.floatValue3,
                  10.0F * this.floatValue3,
                  10.0F * this.floatValue3,
                  5.0F * this.floatValue3,
                  metrics57.measure(14.0F) * floatValue211,
                  metrics57.measure(2.0F),
                  ColorScheme.compute6(intValue49, Math.round(132.0F * floatValue211))
               );
            }

            renderManager42.invoke39(floatValue212, floatValue210, Math.max(3.4F, 4.6F * this.floatValue3) + 3.4F * floatValue211, 0.0F, 1.0F, intValue49);
            renderManager42.invoke39(floatValue212, floatValue210, Math.max(1.8F, 2.1F * this.floatValue3), 0.0F, 1.0F, intValue47);
         }

         ClickGuiRenderUtils.invoke3(
            renderManager42,
            metrics57,
            FontRegistry.fontObject,
            floatValue212 + 10.0F * this.floatValue3,
            floatValue210 - 6.3F * this.floatValue3,
            8.5F * this.floatValue3 / Math.max(0.001F, metrics57.getFloatValue()),
            shaderPin5.label(),
            this.compute12(colorScheme44)
         );
      }

      for (int intValue50 = 0; intValue50 < shaderNodeDefinition20.getItems2().size(); intValue50++) {
         ShaderPin shaderPin6 = shaderNodeDefinition20.getItems2().get(intValue50);
         float floatValue215 = f + shaderNodeDefinition20.getFloatValue() * this.floatValue3;
         float floatValue216 = g + this.measure13(intValue50) * this.floatValue3;
         float floatValue217 = this.measure7(shaderNodeKind40.getText(), shaderPin6.id(), ShaderPinDirection.OUTPUT, floatValue215, floatValue216);
         float[] floatValues2 = this.resolve16(floatValue215, floatValue216, floatValue217);
         floatValue215 = floatValues2[0];
         floatValue216 = floatValues2[1];
         int intValue51 = this.compute10(shaderPin6, colorScheme44);
         if (flag43) {
            float floatValue218 = Math.max(3.4F, 4.6F * this.floatValue3) + 3.4F * floatValue217;
            float floatValue219 = Math.max(1.8F, 2.1F * this.floatValue3);
            shaderPinRenderer.invoke(
               renderManager42,
               floatValue215,
               floatValue216,
               floatValue218,
               floatValue219,
               intValue51,
               intValue47,
               floatValue217,
               measure10(shaderNodeKind40.getText().hashCode() * 0.0047F + intValue50 * 0.191F + 0.41F)
            );
         } else {
            if (floatValue217 > 0.001F) {
               renderManager42.invoke41(
                  floatValue215 - 5.0F * this.floatValue3,
                  floatValue216 - 5.0F * this.floatValue3,
                  10.0F * this.floatValue3,
                  10.0F * this.floatValue3,
                  5.0F * this.floatValue3,
                  metrics57.measure(14.0F) * floatValue217,
                  metrics57.measure(2.0F),
                  ColorScheme.compute6(intValue51, Math.round(132.0F * floatValue217))
               );
            }

            renderManager42.invoke39(floatValue215, floatValue216, Math.max(3.4F, 4.6F * this.floatValue3) + 3.4F * floatValue217, 0.0F, 1.0F, intValue51);
            renderManager42.invoke39(floatValue215, floatValue216, Math.max(1.8F, 2.1F * this.floatValue3), 0.0F, 1.0F, intValue47);
         }

         float floatValue220 = ClickGuiRenderUtils.measure2(metrics57, FontRegistry.fontObject, shaderPin6.label(), 8.5F * this.floatValue3 / Math.max(0.001F, metrics57.getFloatValue()));
         ClickGuiRenderUtils.invoke3(
            renderManager42,
            metrics57,
            FontRegistry.fontObject,
            floatValue215 - 10.0F * this.floatValue3 - floatValue220,
            floatValue216 - 6.3F * this.floatValue3,
            8.5F * this.floatValue3 / Math.max(0.001F, metrics57.getFloatValue()),
            shaderPin6.label(),
            this.compute12(colorScheme44)
         );
      }

      if (flag43) {
         shaderPinRenderer.invoke2();
      }
   }

   private float measure7(String string, String string2, ShaderPinDirection shaderPinDirection, float f, float g) {
      float floatValue221 = (float)Math.hypot(this.floatValue19 - f, this.floatValue20 - g);
      float floatValue222 = floatValue221 <= Math.max(18.0F, 22.0F * this.floatValue3) ? 1.0F : 0.0F;
      String text46 = string + "." + string2 + "." + shaderPinDirection.name();
      return this.valuesByKey11.computeIfAbsent(text46, stringx -> new SpringAnimation(0.0F)).measure(floatValue222, SpringSpec.resolve11());
   }

   private float[] resolve16(float f, float g, float h) {
      float floatValue223 = this.floatValue19 - f;
      float floatValue224 = this.floatValue20 - g;
      float floatValue225 = (float)Math.hypot(floatValue223, floatValue224);
      if (!(floatValue225 <= 0.001F) && !(h <= 0.001F)) {
         float floatValue226 = Math.min(5.5F * this.floatValue3, floatValue225 * 0.22F) * h;
         return new float[]{f + floatValue223 / floatValue225 * floatValue226, g + floatValue224 / floatValue225 * floatValue226};
      } else {
         return new float[]{f, g};
      }
   }

   private void invoke67(RenderManager renderManager43, ColorScheme colorScheme45, int i, int j, float f) {
      HashMap hashMap2 = new HashMap();
      Map valuesByKey = this.resolve18();
      float floatValue227 = this.springAnimation2.measure(0.0F, SpringSpec.resolve10());
      ShaderWireRenderer shaderWireRenderer = ShaderWireRenderer.getINSTANCE();
      if (shaderWireRenderer.check(renderManager43, i, j, f)) {
         for (ShaderConnection shaderConnection3 : this.shaderNode.getItems()) {
            ShaderFoundryScreen.ShaderFoundryScreenData5 shaderFoundryScreenData5 = this.resolve24(shaderConnection3.getText(), shaderConnection3.getText2(), ShaderPinDirection.OUTPUT);
            ShaderFoundryScreen.ShaderFoundryScreenData5 shaderFoundryScreenData52 = this.resolve24(shaderConnection3.getText3(), shaderConnection3.getText4(), ShaderPinDirection.INPUT);
            if (shaderFoundryScreenData5 != null && shaderFoundryScreenData52 != null) {
               ShaderPin shaderPin7 = this.resolve25(shaderConnection3.getText(), shaderConnection3.getText2(), ShaderPinDirection.OUTPUT);
               ShaderPin shaderPin8 = this.resolve25(shaderConnection3.getText3(), shaderConnection3.getText4(), ShaderPinDirection.INPUT);
               ShaderFoundryScreen.ShaderFoundryScreenData6 shaderFoundryScreenData6 = this.resolve26(shaderConnection3.getText(), shaderConnection3.getText2(), shaderPin7, shaderPin8, colorScheme45);
               int intValue52 = this.compute6(shaderConnection3.getText(), hashMap2);
               Integer integerValue = (Integer)valuesByKey.get(resolve19(shaderConnection3));
               float floatValue228 = integerValue == null ? -1.0F : measure10(integerValue.intValue() * 0.105F + measure9(shaderConnection3) * 0.019F);
               this.invoke69(
                  shaderWireRenderer,
                  shaderFoundryScreenData5.x,
                  shaderFoundryScreenData5.y,
                  shaderFoundryScreenData52.x,
                  shaderFoundryScreenData52.y,
                  shaderFoundryScreenData6.a(),
                  shaderFoundryScreenData6.b(),
                  false,
                  floatValue228,
                  floatValue227,
                  intValue52,
                  this.measure8(shaderConnection3.getText()),
                  this.measure8(shaderConnection3.getText3())
               );
            }
         }

         shaderWireRenderer.invoke3();
      }
   }

   private void invoke68(RenderManager renderManager44, ClickGuiState clickGuiState29, ColorScheme colorScheme46, int i, int j, float f) {
      if (this.text3 != null && this.text4 != null) {
         ShaderFoundryScreen.ShaderFoundryScreenData5 shaderFoundryScreenData53 = this.resolve24(this.text3, this.text4, ShaderPinDirection.OUTPUT);
         if (shaderFoundryScreenData53 == null) {
            this.flag12 = false;
         } else {
            float floatValue229 = clickGuiState29.getFloatValue();
            float floatValue230 = clickGuiState29.getFloatValue2();
            if (!this.flag12) {
               this.floatValue29 = floatValue229;
               this.floatValue30 = floatValue230;
               this.flag12 = true;
            } else {
               float floatValue231 = Math.max(0.001F, Math.min(0.05F, SpringAnimation.measure3()));
               float floatValue232 = 1.0F - (float)Math.exp(-24.0F * floatValue231);
               this.floatValue29 = this.floatValue29 + (floatValue229 - this.floatValue29) * floatValue232;
               this.floatValue30 = this.floatValue30 + (floatValue230 - this.floatValue30) * floatValue232;
            }

            ShaderPin shaderPin9 = this.resolve25(this.text3, this.text4, ShaderPinDirection.OUTPUT);
            ShaderFoundryScreen.ShaderFoundryScreenData6 shaderFoundryScreenData62 = this.resolve27(this.text3, this.text4, shaderPin9, colorScheme46, 0);
            int intValue53 = shaderFoundryScreenData62.a();
            int intValue54 = ColorScheme.compute6(shaderFoundryScreenData62.b(), 190);
            ShaderWireRenderer shaderWireRenderer2 = ShaderWireRenderer.getINSTANCE();
            if (shaderWireRenderer2.check(renderManager44, i, j, f)) {
               this.invoke69(
                  shaderWireRenderer2,
                  shaderFoundryScreenData53.x,
                  shaderFoundryScreenData53.y,
                  this.floatValue29,
                  this.floatValue30,
                  intValue53,
                  intValue54,
                  true,
                  -1.0F,
                  1.0F,
                  0,
                  this.measure8(this.text3),
                  1.0F
               );
               shaderWireRenderer2.invoke3();
            }
         }
      } else {
         this.flag12 = false;
      }
   }

   private void invoke69(
      ShaderWireRenderer shaderWireRenderer3, float f, float g, float h, float i, int j, int k, boolean bl, float l, float m, int n, float o, float p
   ) {
      float floatValue233 = Math.abs(h - f);
      float floatValue234 = measure21((Math.abs(this.floatValue25) + Math.abs(this.floatValue26)) * 0.012F, 0.0F, 1.0F);
      float floatValue235 = Math.max(78.0F * this.floatValue3, floatValue233 * (0.44F + 0.14F * m + floatValue234 * 0.075F + Math.min(0.08F, n * 0.008F)));
      float floatValue236 = bl ? measure21((Math.abs(this.floatValue21) + Math.abs(this.floatValue22)) * 6.0E-4F, 0.0F, 1.0F) : 0.0F;
      float floatValue237 = 1.2F + m * 0.2F + (bl ? 0.34F : 0.0F) + floatValue236 * 0.12F + floatValue234 * 0.08F;
      boolean flag44 = bl || l >= 0.0F;
      float floatValue238 = l >= 0.0F ? 0.118F + m * 0.036F + floatValue234 * 0.02F + Math.min(0.028F, n * 0.002F) : 0.0F;
      float floatValue239 = this.floatValue25 * measure21(o, 0.0F, 1.0F);
      float floatValue240 = this.floatValue26 * measure21(o, 0.0F, 1.0F);
      float floatValue241 = this.floatValue25 * measure21(p, 0.0F, 1.0F);
      float floatValue242 = this.floatValue26 * measure21(p, 0.0F, 1.0F);
      shaderWireRenderer3.invoke2(f, g, h, i, floatValue235, j, k, floatValue237, flag44, floatValue238, l, floatValue239, floatValue240, floatValue241, floatValue242);
   }

   private float measure8(String string) {
      if (string == null || this.text == null) {
         return 0.0F;
      } else if (string.equals(this.text)) {
         return 1.0F;
      } else {
         return this.valuesByKey.containsKey(string) ? 0.92F : 0.0F;
      }
   }

   private ClampedSpringAnimation resolve17() {
      return new ClampedSpringAnimation(AnimationSystem.getINSTANCE(), SpringConfig.resolve(2.4F, 0.72F), 0.0F, 0.0F, 1.0F, 0.001F, 0.001F);
   }

   private void invoke70(RenderManager renderManager45, float f, float g, float h, float i, float j, int k, float l, Metrics metrics58) {
      float floatValue243 = measure21(l, 0.0F, 1.0F);
      renderManager45.invoke41(
         f, g, h, i, j, metrics58.measure(15.0F) * floatValue243, metrics58.measure(2.2F), ColorScheme.compute6(k, Math.round(96.0F * floatValue243))
      );
      renderManager45.invoke41(
         f, g, h, i, j, metrics58.measure(30.0F) * floatValue243, metrics58.measure(6.0F), ColorScheme.compute6(k, Math.round(36.0F * floatValue243))
      );
   }

   private void invoke71(float f, float g) {
      this.flag = true;
      this.floatValue5 = f;
      this.floatValue6 = g;
      this.floatValue7 = this.floatValue;
      this.floatValue8 = this.floatValue2;
      this.floatValue9 = 0.0F;
      this.floatValue10 = 0.0F;
      this.timestamp = System.nanoTime();
   }

   private void invoke72(float f, float g) {
      long longValue = System.nanoTime();
      float floatValue244 = Math.max(0.001F, Math.min(0.05F, (float)(longValue - this.timestamp) / 1.0E9F));
      this.floatValue9 = (this.floatValue - f) / floatValue244;
      this.floatValue10 = (this.floatValue2 - g) / floatValue244;
      this.timestamp = longValue;
   }

   private void invoke73() {
      if (!this.flag && !this.flag14) {
         float floatValue245 = SpringAnimation.measure3();
         if (Math.abs(this.floatValue9) < 0.01F && Math.abs(this.floatValue10) < 0.01F) {
            this.floatValue9 = 0.0F;
            this.floatValue10 = 0.0F;
         } else {
            this.floatValue = this.floatValue + this.floatValue9 * floatValue245;
            this.floatValue2 = this.floatValue2 + this.floatValue10 * floatValue245;
            float floatValue246 = (float)Math.exp(-8.8F * floatValue245);
            this.floatValue9 *= floatValue246;
            this.floatValue10 *= floatValue246;
         }
      }
   }

   private void invoke74() {
      float floatValue247 = Math.max(0.001F, Math.min(0.05F, SpringAnimation.measure3()));
      boolean flag45 = this.text != null;
      float floatValue248 = flag45 ? measure21(this.floatValue21 * 0.018F, -42.0F, 42.0F) : 0.0F;
      float floatValue249 = flag45 ? measure21(this.floatValue22 * 0.018F, -42.0F, 42.0F) : 0.0F;
      float floatValue250 = (floatValue248 - this.floatValue25) * 82.0F - this.floatValue27 * 15.5F;
      float floatValue251 = (floatValue249 - this.floatValue26) * 82.0F - this.floatValue28 * 15.5F;
      this.floatValue27 += floatValue250 * floatValue247;
      this.floatValue28 += floatValue251 * floatValue247;
      this.floatValue25 = this.floatValue25 + this.floatValue27 * floatValue247;
      this.floatValue26 = this.floatValue26 + this.floatValue28 * floatValue247;
      if (!flag45
         && Math.abs(this.floatValue25) < 0.01F
         && Math.abs(this.floatValue26) < 0.01F
         && Math.abs(this.floatValue27) < 0.01F
         && Math.abs(this.floatValue28) < 0.01F) {
         this.floatValue25 = 0.0F;
         this.floatValue26 = 0.0F;
         this.floatValue27 = 0.0F;
         this.floatValue28 = 0.0F;
      }
   }

   private void invoke75(RenderManager renderManager46, ClickGuiState clickGuiState30, ColorScheme colorScheme47, int i, int j, float f) {
      ShaderGridRenderer.getINSTANCE()
         .check(
            renderManager46,
            i,
            j,
            this.floatValue,
            this.floatValue2,
            this.floatValue3,
            clickGuiState30.getFloatValue(),
            clickGuiState30.getFloatValue2(),
            0.95F,
            f,
            colorScheme47,
            this.check4(colorScheme47)
         );
   }

   private void invoke76(RenderManager renderManager47, ThemeContext themeContext22, ColorScheme colorScheme48) {
      if (this.flag2) {
         Metrics metrics59 = themeContext22.getMetrics();
         float floatValue252 = Math.min(this.floatValue15, this.floatValue17);
         float floatValue253 = Math.min(this.floatValue16, this.floatValue18);
         float floatValue254 = Math.abs(this.floatValue17 - this.floatValue15);
         float floatValue255 = Math.abs(this.floatValue18 - this.floatValue16);
         if (!(floatValue254 < 1.0F) && !(floatValue255 < 1.0F)) {
            float floatValue256 = metrics59.measure(6.0F);
            renderManager47.invoke5(floatValue252, floatValue253, floatValue254, floatValue255, floatValue256, ColorScheme.compute6(colorScheme48.getIntValue14(), 24));
            renderManager47.invoke28(floatValue252, floatValue253, floatValue254, floatValue255, floatValue256, ColorScheme.compute6(colorScheme48.getIntValue14(), 150), 0.9F);
            renderManager47.invoke41(
               floatValue252, floatValue253, floatValue254, floatValue255, floatValue256, metrics59.measure(14.0F), metrics59.measure(1.0F), ColorScheme.compute6(colorScheme48.getIntValue15(), 34)
            );
         }
      }
   }

   private void invoke77(RenderManager renderManager48, ThemeContext themeContext23, int i, int j) {
      Metrics metrics60 = themeContext23.getMetrics();
      ColorScheme colorScheme49 = themeContext23.getColorScheme();
      String text47 = "RMB -> Node Browser | Space+LMB / MMB pan | LMB drag select | Shift+D duplicate | Wheel zoom | Ctrl+C/V share | Del erase | Ctrl+Z/Y undo | Ctrl+S save";
      float floatValue257 = ClickGuiRenderUtils.measure2(metrics60, FontRegistry.fontObject, text47, 9.0F);
      float floatValue258 = (i - floatValue257) * 0.5F;
      float floatValue259 = j - metrics60.measure(20.0F);
      renderManager48.invoke5(
         floatValue258 - metrics60.measure(10.0F),
         floatValue259 - metrics60.measure(2.0F),
         floatValue257 + metrics60.measure(20.0F),
         metrics60.measure(18.0F),
         metrics60.measure(8.0F),
         this.compute3(colorScheme49, 188)
      );
      renderManager48.invoke28(
         floatValue258 - metrics60.measure(10.0F),
         floatValue259 - metrics60.measure(2.0F),
         floatValue257 + metrics60.measure(20.0F),
         metrics60.measure(18.0F),
         metrics60.measure(8.0F),
         ColorScheme.compute6(colorScheme49.getIntValue14(), 56),
         0.6F
      );
      ClickGuiRenderUtils.invoke4(
         renderManager48,
         metrics60,
         FontRegistry.fontObject,
         floatValue258,
         floatValue259 - metrics60.measure(2.0F),
         metrics60.measure(18.0F),
         9.0F,
         text47,
         ColorScheme.compute6(colorScheme49.getIntValue13(), 200)
      );
   }

   private int compute6(String string, Map<String, Integer> map) {
      Integer integerValue2 = (Integer)map.get(string);
      if (integerValue2 != null) {
         return integerValue2;
      } else {
         map.put(string, 0);
         int intValue55 = 0;

         for (ShaderConnection shaderConnection4 : this.shaderNode.getItems()) {
            if (shaderConnection4.getText3().equals(string)) {
               intValue55 = Math.max(intValue55, this.compute6(shaderConnection4.getText(), map) + 1);
            }
         }

         map.put(string, intValue55);
         return intValue55;
      }
   }

   private Map<String, Integer> resolve18() {
      HashMap hashMap3 = new HashMap();
      LinkedHashSet linkedHashSet = new LinkedHashSet();

      for (ShaderNodeKind shaderNodeKind41 : this.shaderNode.resolve2()) {
         if ("output_color".equals(shaderNodeKind41.getText2())) {
            linkedHashSet.add(shaderNodeKind41.getText());
         }
      }

      LinkedHashSet linkedHashSet2 = new LinkedHashSet(linkedHashSet);

      for (int intValue56 = 0; !linkedHashSet.isEmpty() && intValue56 < 256; intValue56++) {
         LinkedHashSet linkedHashSet3 = new LinkedHashSet();

         for (String text48 : (Set<String>)linkedHashSet) {
            for (ShaderConnection shaderConnection5 : this.shaderNode.getItems()) {
               if (shaderConnection5.getText3().equals(text48)) {
                  hashMap3.putIfAbsent(resolve19(shaderConnection5), intValue56);
                  if (linkedHashSet2.add(shaderConnection5.getText())) {
                     linkedHashSet3.add(shaderConnection5.getText());
                  }
               }
            }
         }

         linkedHashSet = linkedHashSet3;
      }

      return hashMap3;
   }

   private static String resolve19(ShaderConnection shaderConnection6) {
      return shaderConnection6.resolve() + ">" + shaderConnection6.resolve2();
   }

   private static float measure9(ShaderConnection shaderConnection7) {
      int intValue57 = 17;
      intValue57 = intValue57 * 31 + shaderConnection7.getText().hashCode();
      intValue57 = intValue57 * 31 + shaderConnection7.getText2().hashCode();
      intValue57 = intValue57 * 31 + shaderConnection7.getText3().hashCode();
      intValue57 = intValue57 * 31 + shaderConnection7.getText4().hashCode();
      return (intValue57 & 1023) / 1023.0F;
   }

   private static float measure10(float f) {
      return f - (float)Math.floor(f);
   }

   private List<ShaderNodeDefinition> resolve20() {
      ArrayList arrayList10 = new ArrayList<>(this.shaderNodeRegistry.resolve2());
      ((List<ShaderNodeDefinition>)arrayList10).sort(Comparator.comparing(ShaderNodeDefinition::getText3).thenComparing(Comparator.comparing(ShaderNodeDefinition::getText2)));
      return arrayList10;
   }

   private ShaderNodeKind resolve21(float f, float g) {
      ArrayList arrayList11 = new ArrayList<>(this.shaderNode.resolve2());

      for (int intValue58 = arrayList11.size() - 1; intValue58 >= 0; intValue58--) {
         ShaderNodeKind shaderNodeKind42 = (ShaderNodeKind)arrayList11.get(intValue58);
         ShaderNodeDefinition shaderNodeDefinition21 = this.shaderNodeRegistry.resolve(shaderNodeKind42.getText2());
         if (shaderNodeDefinition21 != null) {
            float floatValue260 = this.measure14(shaderNodeKind42.getFloatValue());
            float floatValue261 = this.measure15(shaderNodeKind42.getFloatValue2());
            float floatValue262 = shaderNodeDefinition21.getFloatValue() * this.floatValue3;
            float floatValue263 = this.measure12(shaderNodeDefinition21, shaderNodeKind42) * this.floatValue3;
            if (f >= floatValue260 && f < floatValue260 + floatValue262 && g >= floatValue261 && g < floatValue261 + floatValue263) {
               return shaderNodeKind42;
            }
         }
      }

      return null;
   }

   private ShaderNodeKind resolve22(float f, float g) {
      ArrayList arrayList12 = new ArrayList<>(this.shaderNode.resolve2());

      for (int intValue59 = arrayList12.size() - 1; intValue59 >= 0; intValue59--) {
         ShaderNodeKind shaderNodeKind43 = (ShaderNodeKind)arrayList12.get(intValue59);
         ShaderNodeDefinition shaderNodeDefinition22 = this.shaderNodeRegistry.resolve(shaderNodeKind43.getText2());
         if (shaderNodeDefinition22 != null && shaderNodeDefinition22.isFlag()) {
            float floatValue264 = this.measure14(shaderNodeKind43.getFloatValue());
            float floatValue265 = this.measure15(shaderNodeKind43.getFloatValue2());
            float floatValue266 = shaderNodeDefinition22.getFloatValue() * this.floatValue3;
            if (this.resolve31(shaderNodeDefinition22, floatValue264, floatValue265, floatValue266).contains(f, g)) {
               return shaderNodeKind43;
            }
         }
      }

      return null;
   }

   private void invoke78(String string) {
      if (string != null) {
         boolean flag46 = !Boolean.TRUE.equals(this.valuesByKey9.get(string));
         this.valuesByKey9.put(string, flag46);
         this.resolve30(string).invoke2(flag46 ? 1.0F : 0.0F);
         if (flag46) {
            this.invoke79(string);
         }
      }
   }

   private void invoke79(String string) {
      int intValue60 = 0;

      for (Boolean booleanValue : this.valuesByKey9.values()) {
         if (Boolean.TRUE.equals(booleanValue)) {
            intValue60++;
         }
      }

      Iterator iterator = this.valuesByKey9.entrySet().iterator();

      while (intValue60 > 10 && iterator.hasNext()) {
         Entry entry2 = (Entry)iterator.next();
         if (!((String)entry2.getKey()).equals(string) && Boolean.TRUE.equals(entry2.getValue())) {
            entry2.setValue(false);
            this.resolve30((String)entry2.getKey()).invoke2(0.0F);
            intValue60--;
         }
      }
   }

   private ShaderFoundryScreen.ShaderFoundryScreenData4 resolve23(float f, float g) {
      for (ShaderNodeKind shaderNodeKind44 : this.shaderNode.resolve2()) {
         ShaderNodeDefinition shaderNodeDefinition23 = this.shaderNodeRegistry.resolve(shaderNodeKind44.getText2());
         if (shaderNodeDefinition23 != null) {
            for (int intValue61 = 0; intValue61 < shaderNodeDefinition23.getItems().size(); intValue61++) {
               ShaderPin shaderPin10 = shaderNodeDefinition23.getItems().get(intValue61);
               float floatValue267 = this.measure14(shaderNodeKind44.getFloatValue());
               float floatValue268 = this.measure15(shaderNodeKind44.getFloatValue2() + this.measure13(intValue61));
               if (Math.hypot(f - floatValue267, g - floatValue268) <= Math.max(12.0F, 13.0F * this.floatValue3)) {
                  return new ShaderFoundryScreen.ShaderFoundryScreenData4(shaderNodeKind44.getText(), shaderPin10.id(), ShaderPinDirection.INPUT);
               }
            }

            for (int intValue62 = 0; intValue62 < shaderNodeDefinition23.getItems2().size(); intValue62++) {
               ShaderPin shaderPin11 = shaderNodeDefinition23.getItems2().get(intValue62);
               float floatValue269 = this.measure14(shaderNodeKind44.getFloatValue() + shaderNodeDefinition23.getFloatValue());
               float floatValue270 = this.measure15(shaderNodeKind44.getFloatValue2() + this.measure13(intValue62));
               if (Math.hypot(f - floatValue269, g - floatValue270) <= Math.max(12.0F, 13.0F * this.floatValue3)) {
                  return new ShaderFoundryScreen.ShaderFoundryScreenData4(shaderNodeKind44.getText(), shaderPin11.id(), ShaderPinDirection.OUTPUT);
               }
            }
         }
      }

      return null;
   }

   private ShaderFoundryScreen.ShaderFoundryScreenData5 resolve24(String string, String string2, ShaderPinDirection shaderPinDirection2) {
      ShaderNodeKind shaderNodeKind45 = this.shaderNode.resolve3(string);
      if (shaderNodeKind45 == null) {
         return null;
      } else {
         ShaderNodeDefinition shaderNodeDefinition24 = this.shaderNodeRegistry.resolve(shaderNodeKind45.getText2());
         if (shaderNodeDefinition24 == null) {
            return null;
         } else {
            List items4 = shaderPinDirection2 == ShaderPinDirection.INPUT ? shaderNodeDefinition24.getItems() : shaderNodeDefinition24.getItems2();

            for (int intValue63 = 0; intValue63 < items4.size(); intValue63++) {
               if (((ShaderPin)items4.get(intValue63)).id().equals(string2)) {
                  float floatValue271 = shaderPinDirection2 == ShaderPinDirection.INPUT ? shaderNodeKind45.getFloatValue() : shaderNodeKind45.getFloatValue() + shaderNodeDefinition24.getFloatValue();
                  return new ShaderFoundryScreen.ShaderFoundryScreenData5(this.measure14(floatValue271), this.measure15(shaderNodeKind45.getFloatValue2() + this.measure13(intValue63)));
               }
            }

            return null;
         }
      }
   }

   private int compute7(String string, String string2, ShaderPinDirection shaderPinDirection3, ColorScheme colorScheme50) {
      return this.compute10(this.resolve25(string, string2, shaderPinDirection3), colorScheme50);
   }

   private ShaderPin resolve25(String string, String string2, ShaderPinDirection shaderPinDirection4) {
      ShaderNodeKind shaderNodeKind46 = this.shaderNode.resolve3(string);
      if (shaderNodeKind46 == null) {
         return null;
      } else {
         ShaderNodeDefinition shaderNodeDefinition25 = this.shaderNodeRegistry.resolve(shaderNodeKind46.getText2());
         if (shaderNodeDefinition25 == null) {
            return null;
         } else {
            for (ShaderPin shaderPin12 : shaderPinDirection4 == ShaderPinDirection.INPUT ? shaderNodeDefinition25.getItems() : shaderNodeDefinition25.getItems2()) {
               if (shaderPin12.id().equals(string2)) {
                  return shaderPin12;
               }
            }

            return null;
         }
      }
   }

   private ShaderFoundryScreen.ShaderFoundryScreenData6 resolve26(
      String string, String string2, ShaderPin shaderPin13, ShaderPin shaderPin14, ColorScheme colorScheme51
   ) {
      ShaderFoundryScreen.ShaderFoundryScreenData6 shaderFoundryScreenData63 = this.resolve27(string, string2, shaderPin13, colorScheme51, 0);
      if (shaderPin14 != null && shaderPin13 != null && shaderPin13.type() != shaderPin14.type()) {
         int intValue64 = ColorScheme.compute6(this.compute9(this.compute10(shaderPin14, colorScheme51), colorScheme51), 246);
         return new ShaderFoundryScreen.ShaderFoundryScreenData6(shaderFoundryScreenData63.b(), intValue64);
      } else {
         return shaderFoundryScreenData63;
      }
   }

   private ShaderFoundryScreen.ShaderFoundryScreenData6 resolve27(String string, String string2, ShaderPin shaderPin15, ColorScheme colorScheme52, int i) {
      int intValue65 = ColorScheme.compute6(this.compute9(this.compute10(shaderPin15, colorScheme52), colorScheme52), 246);
      if (shaderPin15 != null && shaderPin15.type() == ShaderValueType.VEC4 && i <= 10) {
         ShaderNodeKind shaderNodeKind47 = this.shaderNode.resolve3(string);
         if (shaderNodeKind47 == null) {
            return new ShaderFoundryScreen.ShaderFoundryScreenData6(intValue65, intValue65);
         } else {
            String text49 = shaderNodeKind47.getText2();
            if ("theme_top".equals(text49)) {
               int intValue66 = ColorScheme.compute6(this.compute9(colorScheme52.getIntValue14(), colorScheme52), 246);
               return new ShaderFoundryScreen.ShaderFoundryScreenData6(intValue66, intValue66);
            } else if ("theme_bottom".equals(text49)) {
               int intValue67 = ColorScheme.compute6(this.compute9(colorScheme52.getIntValue15(), colorScheme52), 246);
               return new ShaderFoundryScreen.ShaderFoundryScreenData6(intValue67, intValue67);
            } else if ("theme_panel".equals(text49)) {
               int intValue68 = ColorScheme.compute6(this.compute9(colorScheme52.getIntValue(), colorScheme52), 246);
               return new ShaderFoundryScreen.ShaderFoundryScreenData6(intValue68, intValue68);
            } else if ("color_ramp".equals(text49) || "color_pulse".equals(text49) || "vec4_mix".equals(text49)) {
               return this.resolve28(string, "a", "b", colorScheme52, i + 1, intValue65);
            } else if ("color_gradient_map".equals(text49)) {
               return this.resolve28(string, "a", "c", colorScheme52, i + 1, intValue65);
            } else if ("alpha_blend".equals(text49)
               || "blend_screen".equals(text49)
               || "blend_overlay".equals(text49)
               || "blend_multiply".equals(text49)
               || "blend_add".equals(text49)) {
               return this.resolve28(string, "base", "layer", colorScheme52, i + 1, intValue65);
            } else if ("glass_surface".equals(text49)) {
               return this.resolve29(string, "tint", colorScheme52, i + 1, intValue65);
            } else {
               return !"sdf_fill".equals(text49)
                     && !"rim_light".equals(text49)
                     && !"hover_glow".equals(text49)
                     && !"exposure_lift".equals(text49)
                     && !"color_multiply_scalar".equals(text49)
                     && !"color_desaturate".equals(text49)
                     && !"color_invert".equals(text49)
                     && !"color_screen_split".equals(text49)
                     && !"chromatic_aberration".equals(text49)
                     && !"posterize".equals(text49)
                     && !"bloom_lift".equals(text49)
                  ? new ShaderFoundryScreen.ShaderFoundryScreenData6(intValue65, intValue65)
                  : this.resolve29(string, "color", colorScheme52, i + 1, intValue65);
            }
         }
      } else {
         return new ShaderFoundryScreen.ShaderFoundryScreenData6(intValue65, intValue65);
      }
   }

   private ShaderFoundryScreen.ShaderFoundryScreenData6 resolve28(String string, String string2, String string3, ColorScheme colorScheme53, int i, int j) {
      ShaderFoundryScreen.ShaderFoundryScreenData6 shaderFoundryScreenData64 = this.resolve29(string, string2, colorScheme53, i, j);
      ShaderFoundryScreen.ShaderFoundryScreenData6 shaderFoundryScreenData65 = this.resolve29(string, string3, colorScheme53, i, j);
      return new ShaderFoundryScreen.ShaderFoundryScreenData6(shaderFoundryScreenData64.a(), shaderFoundryScreenData65.b());
   }

   private ShaderFoundryScreen.ShaderFoundryScreenData6 resolve29(String string, String string2, ColorScheme colorScheme54, int i, int j) {
      ShaderConnection shaderConnection8 = this.shaderNode.resolve4(string, string2);
      if (shaderConnection8 != null) {
         ShaderPin shaderPin16 = this.resolve25(shaderConnection8.getText(), shaderConnection8.getText2(), ShaderPinDirection.OUTPUT);
         return this.resolve27(shaderConnection8.getText(), shaderConnection8.getText2(), shaderPin16, colorScheme54, i);
      } else {
         ShaderPin shaderPin17 = this.resolve25(string, string2, ShaderPinDirection.INPUT);
         int intValue69 = this.compute8(shaderPin17, colorScheme54, j);
         return new ShaderFoundryScreen.ShaderFoundryScreenData6(intValue69, intValue69);
      }
   }

   private int compute8(ShaderPin shaderPin18, ColorScheme colorScheme55, int i) {
      if (shaderPin18 != null && shaderPin18.type() == ShaderValueType.VEC4) {
         String text50 = shaderPin18.defaultExpression();
         if (text50 == null) {
            return i;
         } else if (text50.contains("u_AccentTop")) {
            return ColorScheme.compute6(this.compute9(colorScheme55.getIntValue14(), colorScheme55), 246);
         } else if (text50.contains("u_AccentBottom")) {
            return ColorScheme.compute6(this.compute9(colorScheme55.getIntValue15(), colorScheme55), 246);
         } else if (text50.contains("u_ThemeColors")) {
            return ColorScheme.compute6(this.compute9(colorScheme55.getIntValue(), colorScheme55), 246);
         } else {
            return text50.contains("vec4(1.0") ? ColorScheme.compute6(this.compute9(colorScheme55.getIntValue13(), colorScheme55), 246) : i;
         }
      } else {
         return i;
      }
   }

   private int compute9(int i, ColorScheme colorScheme56) {
      int intValue70 = ColorScheme.compute6(i, 255);
      float floatValue272 = this.check4(colorScheme56) ? 0.02F : 0.16F;
      return ColorScheme.compute7(intValue70, colorScheme56.getIntValue13(), floatValue272);
   }

   private int compute10(ShaderPin shaderPin19, ColorScheme colorScheme57) {
      if (shaderPin19 == null) {
         return ColorScheme.compute6(colorScheme57.getIntValue14(), 220);
      } else {
         return switch (shaderPin19.type()) {
            case FLOAT -> ColorScheme.compute5(250, 211, 126, 240);
            case VEC2 -> ColorScheme.compute5(119, 210, 255, 240);
            case VEC3 -> ColorScheme.compute6(colorScheme57.getIntValue14(), 240);
            case VEC4 -> ColorScheme.compute6(colorScheme57.getIntValue15(), 240);
            case INT -> ColorScheme.compute5(155, 255, 61, 240);
         };
      }
   }

   private int compute11(ColorScheme colorScheme58) {
      return this.check4(colorScheme58) ? ColorScheme.compute5(10, 10, 10, 255) : colorScheme58.getIntValue13();
   }

   private int compute12(ColorScheme colorScheme59) {
      return this.check4(colorScheme59) ? ColorScheme.compute5(10, 10, 10, 210) : colorScheme59.getIntValue12();
   }

   private float measure11(ShaderNodeDefinition shaderNodeDefinition26) {
      int intValue71 = Math.max(shaderNodeDefinition26.getItems().size(), shaderNodeDefinition26.getItems2().size());
      float floatValue273 = Math.max(96.0F, 60.0F + intValue71 * 26.0F);
      return !"float_value".equals(shaderNodeDefinition26.getText())
            && !"int_value".equals(shaderNodeDefinition26.getText())
            && !"exposed_float".equals(shaderNodeDefinition26.getText())
            && !"exposed_color".equals(shaderNodeDefinition26.getText())
         ? floatValue273
         : floatValue273 + 24.0F;
   }

   private float measure12(ShaderNodeDefinition shaderNodeDefinition27, ShaderNodeKind shaderNodeKind48) {
      float floatValue274 = this.measure11(shaderNodeDefinition27);
      if (shaderNodeKind48 != null && shaderNodeDefinition27.isFlag()) {
         ClampedSpringAnimation clampedSpringAnimation4 = this.resolve30(shaderNodeKind48.getText());
         return floatValue274 + clampedSpringAnimation4.measure() * 128.0F;
      } else {
         return floatValue274;
      }
   }

   private ClampedSpringAnimation resolve30(String string) {
      return this.valuesByKey10
         .computeIfAbsent(
            string, stringx -> new ClampedSpringAnimation(AnimationSystem.getINSTANCE(), SpringConfig.resolve(2.6F, 0.78F), 0.0F, 0.0F, 1.0F, 0.001F, 0.001F)
         );
   }

   private Rect resolve31(ShaderNodeDefinition shaderNodeDefinition28, float f, float g, float h) {
      float floatValue275 = Math.max(52.0F * this.floatValue3, 0.0F);
      float floatValue276 = Math.max(16.0F * this.floatValue3, 0.0F);
      return new Rect(f + h - floatValue275 - 10.0F * this.floatValue3, g + 12.0F * this.floatValue3, floatValue275, floatValue276);
   }

   private float measure13(int i) {
      return 64.0F + i * 26.0F;
   }

   private float measure14(float f) {
      return this.floatValue + f * this.floatValue3;
   }

   private float measure15(float f) {
      return this.floatValue2 + f * this.floatValue3;
   }

   private float measure16(float f) {
      return (f - this.floatValue) / Math.max(0.001F, this.floatValue3);
   }

   private float measure17(float f) {
      return (f - this.floatValue2) / Math.max(0.001F, this.floatValue3);
   }

   private Rect resolve32(Metrics metrics61, int i) {
      return new Rect(metrics61.measure(42.0F), metrics61.measure(106.0F), metrics61.measure(232.0F), i - metrics61.measure(148.0F));
   }

   private Rect resolve33(Metrics metrics62, int i) {
      return this.flag8
         ? new Rect(metrics62.measure(42.0F), metrics62.measure(106.0F), metrics62.measure(28.0F), metrics62.measure(28.0F))
         : new Rect(
            metrics62.measure(42.0F) + metrics62.measure(232.0F) - metrics62.measure(34.0F),
            metrics62.measure(106.0F),
            metrics62.measure(28.0F),
            metrics62.measure(28.0F)
         );
   }

   private Rect resolve34(Metrics metrics63, int i, int j) {
      float floatValue277 = Math.min(metrics63.measure(342.0F), Math.max(metrics63.measure(286.0F), i * 0.25F));
      float floatValue278 = Math.min(metrics63.measure(292.0F), Math.max(metrics63.measure(220.0F), j * 0.3F));
      return new Rect(i - floatValue277 - metrics63.measure(42.0F), metrics63.measure(104.0F), floatValue277, floatValue278);
   }

   private Rect resolve35(Rect rect81, Metrics metrics64, int i) {
      float floatValue279 = metrics64.measure(27.0F);
      float floatValue280 = rect81.y() + metrics64.measure(74.0F) + i * floatValue279;
      float floatValue281 = rect81.x() + metrics64.measure(90.0F);
      return new Rect(floatValue281, floatValue280, rect81.w() - metrics64.measure(104.0F), metrics64.measure(20.0F));
   }

   private Rect resolve36(Rect rect82, Metrics metrics65) {
      return new Rect(
         rect82.x() + rect82.w() - metrics65.measure(108.0F),
         rect82.y() + metrics65.measure(14.0F),
         metrics65.measure(92.0F),
         metrics65.measure(22.0F)
      );
   }

   private ShaderNodeKind resolve37() {
      this.invoke20();
      return this.text2 == null ? null : this.shaderNode.resolve3(this.text2);
   }

   private String resolve38(ShaderNodeKind shaderNodeKind49, String string) {
      return shaderNodeKind49.getText() + ":" + string;
   }

   private ShaderUniformEditor resolve39(String string, float f, float g, float h) {
      ShaderUniformEditor shaderUniformEditor25 = this.valuesByKey5.computeIfAbsent(string, stringx -> ShaderUniformEditor.resolve(f, g));
      shaderUniformEditor25.invoke(f, g);
      float floatValue282 = Math.max(2.0E-4F, Math.min(1.0F, Math.abs(h) * 1.8F));
      shaderUniformEditor25.invoke2(floatValue282, floatValue282 * 0.1F);
      return shaderUniformEditor25;
   }

   private ShaderUniformEditor resolve40(String string) {
      return this.valuesByKey5.computeIfAbsent(string, stringx -> ShaderUniformEditor.resolve2());
   }

   private Rect resolve41(ShaderNodeKind shaderNodeKind50) {
      ShaderNodeDefinition shaderNodeDefinition29 = this.shaderNodeRegistry.resolve(shaderNodeKind50.getText2());
      if (shaderNodeDefinition29 == null) {
         return new Rect(0.0F, 0.0F, 0.0F, 0.0F);
      } else {
         float floatValue283 = this.measure14(shaderNodeKind50.getFloatValue()) + 14.0F * this.floatValue3;
         float floatValue284 = this.measure15(shaderNodeKind50.getFloatValue2()) + 78.0F * this.floatValue3;
         float floatValue285 = Math.max(1.0F, (shaderNodeDefinition29.getFloatValue() - 28.0F) * this.floatValue3);
         float floatValue286 = Math.max(1.0F, 18.0F * this.floatValue3);
         return new Rect(floatValue283, floatValue284, floatValue285, floatValue286);
      }
   }

   private Rect resolve42(ShaderNodeKind shaderNodeKind51) {
      ShaderNodeDefinition shaderNodeDefinition30 = this.shaderNodeRegistry.resolve(shaderNodeKind51.getText2());
      if (shaderNodeDefinition30 == null) {
         return new Rect(0.0F, 0.0F, 0.0F, 0.0F);
      } else {
         float floatValue287 = this.measure14(shaderNodeKind51.getFloatValue()) + 14.0F * this.floatValue3;
         float floatValue288 = this.measure15(shaderNodeKind51.getFloatValue2()) + 78.0F * this.floatValue3;
         float floatValue289 = Math.max(1.0F, (shaderNodeDefinition30.getFloatValue() - 28.0F) * this.floatValue3);
         float floatValue290 = Math.max(1.0F, 18.0F * this.floatValue3);
         return new Rect(floatValue287, floatValue288, floatValue289, floatValue290);
      }
   }

   private Rect resolve43(Metrics metrics66, int i, int j) {
      float floatValue291 = Math.min(metrics66.measure(340.0F), i * 0.31F);
      float floatValue292 = Math.min(metrics66.measure(232.0F), j * 0.3F);
      if (!this.flag13) {
         this.floatValue31 = i - floatValue291 - metrics66.measure(42.0F);
         this.floatValue32 = j - floatValue292 - metrics66.measure(42.0F);
         this.flag13 = true;
      }

      this.floatValue31 = measure21(
         this.floatValue31, metrics66.measure(24.0F), Math.max(metrics66.measure(24.0F), i - floatValue291 - metrics66.measure(24.0F))
      );
      this.floatValue32 = measure21(
         this.floatValue32, metrics66.measure(94.0F), Math.max(metrics66.measure(94.0F), j - floatValue292 - metrics66.measure(24.0F))
      );
      return new Rect(this.floatValue31, this.floatValue32, floatValue291, floatValue292);
   }

   private boolean check25(ShaderNodeKind shaderNodeKind52) {
      return shaderNodeKind52 != null && ("exposed_float".equals(shaderNodeKind52.getText2()) || "exposed_color".equals(shaderNodeKind52.getText2()));
   }

   private String resolve44(ShaderNodeKind shaderNodeKind53) {
      return shaderNodeKind53 != null && "exposed_color".equals(shaderNodeKind53.getText2()) ? "Color" : "Radius";
   }

   private boolean check26() {
      MinecraftClient client = MinecraftClient.getInstance();
      return client != null && client.getWindow() != null ? GLFW.glfwGetKey(client.getWindow().getHandle(), 32) == 1 : false;
   }

   private int compute13() {
      MinecraftClient client2 = MinecraftClient.getInstance();
      return client2 != null && client2.getWindow() != null ? Math.max(1, client2.getWindow().getFramebufferWidth()) : 1;
   }

   private int compute14() {
      MinecraftClient client3 = MinecraftClient.getInstance();
      return client3 != null && client3.getWindow() != null ? Math.max(1, client3.getWindow().getFramebufferHeight()) : 1;
   }

   private Metrics resolve45() {
      return this.metrics != null ? this.metrics : Metrics.resolve2(this.compute13(), this.compute14(), LayoutSpec.resolve());
   }

   private Rect resolve46(Metrics metrics67, int i) {
      float floatValue293 = metrics67.measure(28.0F);
      return new Rect(
         i - this.measure19(metrics67) - metrics67.measure(74.0F), metrics67.measure(46.0F), metrics67.measure(74.0F), floatValue293
      );
   }

   private Rect resolve47(Metrics metrics68, int i) {
      float floatValue294 = metrics68.measure(28.0F);
      return new Rect(
         this.resolve46(metrics68, i).x() - metrics68.measure(10.0F) - metrics68.measure(96.0F),
         metrics68.measure(46.0F),
         metrics68.measure(96.0F),
         floatValue294
      );
   }

   private Rect resolve48(Metrics metrics69) {
      return new Rect(
         metrics69.measure(34.0F) + metrics69.measure(200.0F), metrics69.measure(46.0F), metrics69.measure(84.0F), metrics69.measure(28.0F)
      );
   }

   private Rect resolve49(Metrics metrics70) {
      Rect rect83 = this.resolve48(metrics70);
      return new Rect(
         rect83.x() + rect83.w() + metrics70.measure(10.0F), metrics70.measure(46.0F), metrics70.measure(220.0F), metrics70.measure(28.0F)
      );
   }

   private Rect resolve50(Metrics metrics71) {
      Rect rect84 = this.resolve49(metrics71);
      return new Rect(
         rect84.x() + rect84.w() + metrics71.measure(10.0F), metrics71.measure(46.0F), metrics71.measure(136.0F), metrics71.measure(28.0F)
      );
   }

   private Rect resolve51(Metrics metrics72, int i) {
      Rect rect85 = this.resolve47(metrics72, i);
      return new Rect(
         rect85.x() - metrics72.measure(10.0F) - metrics72.measure(110.0F),
         metrics72.measure(46.0F),
         metrics72.measure(110.0F),
         metrics72.measure(28.0F)
      );
   }

   private Rect resolve52(Metrics metrics73, int i, int j) {
      Rect rect86 = this.resolve51(metrics73, i);
      float floatValue295 = Math.min(metrics73.measure(520.0F), Math.max(metrics73.measure(420.0F), i * 0.3F));
      float floatValue296 = measure21(rect86.x() + rect86.w() - floatValue295, metrics73.measure(42.0F), i - floatValue295 - metrics73.measure(42.0F));
      float floatValue297 = rect86.y() + rect86.h() + metrics73.measure(10.0F);
      float floatValue298 = Math.min(metrics73.measure(520.0F), Math.max(metrics73.measure(220.0F), j - floatValue297 - metrics73.measure(34.0F)));
      return new Rect(floatValue296, floatValue297, floatValue295, floatValue298);
   }

   private Rect resolve53(Rect rect87, Metrics metrics74) {
      return new Rect(
         rect87.x() + metrics74.measure(10.0F),
         rect87.y() + metrics74.measure(74.0F),
         rect87.w() - metrics74.measure(20.0F),
         rect87.h() - metrics74.measure(84.0F)
      );
   }

   private Rect resolve54(Rect rect88, Metrics metrics75, int i) {
      float floatValue299 = metrics75.measure(8.0F);
      float floatValue300 = (rect88.w() - metrics75.measure(20.0F) - floatValue299 * 2.0F) / 3.0F;
      return new Rect(
         rect88.x() + metrics75.measure(10.0F) + i * (floatValue300 + floatValue299),
         rect88.y() + metrics75.measure(42.0F),
         floatValue300,
         metrics75.measure(24.0F)
      );
   }

   private float measure18(Metrics metrics76) {
      return metrics76.measure(68.0F);
   }

   private Rect resolve55(Rect rect89, Metrics metrics77) {
      float floatValue301 = metrics77.measure(24.0F);
      return new Rect(
         rect89.x() + rect89.w() - floatValue301 - metrics77.measure(10.0F), rect89.y() + metrics77.measure(10.0F), floatValue301, floatValue301
      );
   }

   private Rect resolve56(Rect rect90, Metrics metrics78) {
      float floatValue302 = rect90.h() - metrics78.measure(14.0F);
      float floatValue303 = metrics78.measure(78.0F);
      return new Rect(rect90.x() + metrics78.measure(26.0F), rect90.y() + metrics78.measure(7.0F), floatValue303, floatValue302);
   }

   private Rect resolve57(Rect rect91, Metrics metrics79, float f) {
      float floatValue304 = metrics79.measure(48.0F);
      float floatValue305 = metrics79.measure(24.0F);
      Rect rect92 = this.resolve58(rect91, metrics79, f);
      return new Rect(rect92.x() - metrics79.measure(8.0F) - floatValue304, f + metrics79.measure(14.0F), floatValue304, floatValue305);
   }

   private Rect resolve58(Rect rect93, Metrics metrics80, float f) {
      float floatValue306 = metrics80.measure(24.0F);
      return new Rect(rect93.x() + rect93.w() - floatValue306 - metrics80.measure(18.0F), f + metrics80.measure(14.0F), floatValue306, floatValue306);
   }

   private Rect resolve59(Rect rect94, Metrics metrics81, float f) {
      Rect rect95 = this.resolve60(rect94, metrics81, f);
      float floatValue307 = metrics81.measure(52.0F);
      return new Rect(rect95.x() - metrics81.measure(8.0F) - floatValue307, f + metrics81.measure(19.0F), floatValue307, metrics81.measure(24.0F));
   }

   private Rect resolve60(Rect rect96, Metrics metrics82, float f) {
      float floatValue308 = metrics82.measure(58.0F);
      return new Rect(
         rect96.x() + rect96.w() - floatValue308 - metrics82.measure(18.0F), f + metrics82.measure(19.0F), floatValue308, metrics82.measure(24.0F)
      );
   }

   private Rect resolve61(Metrics metrics83) {
      Rect rect97 = this.resolve48(metrics83);
      float floatValue309 = metrics83.measure(320.0F);
      return new Rect(rect97.x(), rect97.y() + rect97.h() + metrics83.measure(10.0F), floatValue309, metrics83.measure(300.0F));
   }

   private Rect resolve62(Rect rect98, Metrics metrics84, int i) {
      float floatValue310 = metrics84.measure(7.0F);
      float floatValue311 = (rect98.w() - metrics84.measure(24.0F) - floatValue310) * 0.5F;
      float floatValue312 = metrics84.measure(28.0F);
      int intValue72 = i & 1;
      int intValue73 = i >> 1;
      return new Rect(
         rect98.x() + metrics84.measure(12.0F) + intValue72 * (floatValue311 + floatValue310),
         rect98.y() + metrics84.measure(164.0F) + intValue73 * (floatValue312 + floatValue310),
         floatValue311,
         floatValue312
      );
   }

   private Rect resolve63(Metrics metrics85, int i, int j) {
      Rect rect99 = this.resolve50(metrics85);
      float floatValue313 = Math.min(metrics85.measure(520.0F), i - metrics85.measure(84.0F));
      float floatValue314 = Math.min(metrics85.measure(252.0F), j - rect99.y() - rect99.h() - metrics85.measure(34.0F));
      float floatValue315 = measure21(rect99.x() + rect99.w() - floatValue313, metrics85.measure(42.0F), i - floatValue313 - metrics85.measure(42.0F));
      return new Rect(floatValue315, rect99.y() + rect99.h() + metrics85.measure(10.0F), floatValue313, floatValue314);
   }

   private Rect resolve64(Rect rect100, Metrics metrics86, int i) {
      float floatValue316 = metrics86.measure(8.0F);
      float floatValue317 = (rect100.w() - metrics86.measure(24.0F) - floatValue316) * 0.5F;
      float floatValue318 = metrics86.measure(42.0F);
      int intValue74 = i & 1;
      int intValue75 = i >> 1;
      return new Rect(
         rect100.x() + metrics86.measure(12.0F) + intValue74 * (floatValue317 + floatValue316),
         rect100.y() + metrics86.measure(52.0F) + intValue75 * (floatValue318 + floatValue316),
         floatValue317,
         floatValue318
      );
   }

   private Rect resolve65(Rect rect101, Metrics metrics87, int i) {
      float floatValue319 = metrics87.measure(7.0F);
      float floatValue320 = (rect101.w() - metrics87.measure(24.0F) - floatValue319 * 2.0F) / 3.0F;
      return new Rect(
         rect101.x() + metrics87.measure(12.0F) + i * (floatValue320 + floatValue319),
         rect101.y() + rect101.h() - metrics87.measure(40.0F),
         floatValue320,
         metrics87.measure(26.0F)
      );
   }

   private Rect resolve66(Metrics metrics88, int i) {
      Rect rect102 = this.resolve47(metrics88, i);
      return new Rect(
         rect102.x() - metrics88.measure(150.0F),
         rect102.y() + rect102.h() + metrics88.measure(10.0F),
         metrics88.measure(300.0F),
         metrics88.measure(236.0F)
      );
   }

   private Rect resolve67(Rect rect103, Metrics metrics89, int i) {
      float floatValue321 = metrics89.measure(8.0F);
      float floatValue322 = (rect103.w() - metrics89.measure(32.0F) - floatValue321 * 2.0F) / 3.0F;
      return new Rect(
         rect103.x() + metrics89.measure(16.0F) + i * (floatValue322 + floatValue321),
         rect103.y() + metrics89.measure(84.0F),
         floatValue322,
         metrics89.measure(26.0F)
      );
   }

   private Rect resolve68(Metrics metrics90, int i, int j) {
      float floatValue323 = metrics90.measure(430.0F);
      float floatValue324 = metrics90.measure(148.0F);
      return new Rect((i - floatValue323) * 0.5F, (j - floatValue324) * 0.5F, floatValue323, floatValue324);
   }

   private Rect resolve69(Rect rect104, Metrics metrics91) {
      return new Rect(
         rect104.x() + metrics91.measure(18.0F),
         rect104.y() + rect104.h() - metrics91.measure(44.0F),
         metrics91.measure(132.0F),
         metrics91.measure(30.0F)
      );
   }

   private Rect resolve70(Rect rect105, Metrics metrics92) {
      return new Rect(
         rect105.x() + metrics92.measure(160.0F),
         rect105.y() + rect105.h() - metrics92.measure(44.0F),
         metrics92.measure(112.0F),
         metrics92.measure(30.0F)
      );
   }

   private Rect resolve71(Rect rect106, Metrics metrics93) {
      return new Rect(
         rect106.x() + rect106.w() - metrics93.measure(118.0F),
         rect106.y() + rect106.h() - metrics93.measure(44.0F),
         metrics93.measure(100.0F),
         metrics93.measure(30.0F)
      );
   }

   private float measure19(Metrics metrics94) {
      return metrics94.measure(64.0F);
   }

   private void invoke80() {
      this.flag3 = false;
      this.flag4 = false;
      this.flag5 = false;
      this.flag6 = false;
      this.flag9 = false;
      this.flag11 = false;
   }

   private void invoke81() {
      try {
         this.shaderEditHistory.invoke(this.resolve72());
      } catch (Throwable exception2) {
      }
   }

   private String resolve72() {
      JSONObject jsonObject = WildThemeCodec.resolve3(this.shaderNode);
      JSONObject jsonObject2 = jsonObject.optJSONObject("metadata");
      if (jsonObject2 != null) {
         jsonObject2.put("updatedAt", 0L);
         jsonObject2.put("source", "");
      }

      return jsonObject.toString();
   }

   private void invoke82(String string) {
      this.shaderNode = WildThemeCodec.resolve4(new JSONObject(string), this.shaderNodeRegistry);
      this.invoke95();
      ShaderSurface shaderSurface8 = ShaderSurface.resolve4(this.shaderNode.getPreview());
      if (shaderSurface8 != null && shaderSurface8 != ShaderSurface.PREVIEW_ONLY) {
         this.shaderSurface = shaderSurface8;
      }

      this.invoke94(this.shaderSurface);
      this.namedShaderProgram.invoke(this.shaderSurface);
      this.namedShaderProgram.invoke2(this.shaderNode);
      this.invoke17();
      this.valuesByKey3.keySet().removeIf(stringx -> this.shaderNode.resolve3(stringx) == null);
      this.valuesByKey4.keySet().removeIf(stringx -> this.shaderNode.resolve3(stringx) == null);
      this.valuesByKey5.keySet().removeIf(stringx -> {
         int var2x = stringx.indexOf(58);
         String text51 = var2x > 0 ? stringx.substring(0, var2x) : stringx;
         return this.shaderNode.resolve3(text51) == null;
      });
      this.valuesByKey9.keySet().removeIf(stringx -> this.shaderNode.resolve3(stringx) == null);
      this.valuesByKey10.keySet().removeIf(stringx -> this.shaderNode.resolve3(stringx) == null);
      this.text8 = null;
      this.text9 = null;
      this.text10 = null;
      this.text = null;
      this.text3 = null;
      this.text4 = null;
      if (!this.shaderNode.getShaderTemplate().getText().isBlank()) {
         this.text7 = this.shaderNode.getShaderTemplate().getText();
      }
   }

   private void invoke83() {
      try {
         String text52 = this.shaderEditHistory.resolve(this.resolve72());
         if (text52 == null) {
            this.setReady("nothing to undo");
            return;
         }

         this.invoke82(text52);
         this.setReady("undo");
      } catch (Throwable exception3) {
         this.setReady("undo failed");
      }
   }

   private void invoke84() {
      try {
         String text53 = this.shaderEditHistory.resolve2(this.resolve72());
         if (text53 == null) {
            this.setReady("nothing to redo");
            return;
         }

         this.invoke82(text53);
         this.setReady("redo");
      } catch (Throwable exception4) {
         this.setReady("redo failed");
      }
   }

   private void invoke85() {
      this.invoke81();
      this.shaderNode = ShaderGraphFactory.resolve(this.shaderNodeRegistry);
      this.text7 = ShaderStylePreset.resolve();
      this.shaderNode.getShaderTemplate().invoke5(this.text7, ShaderPresetStore.resolve19());
      this.shaderNode.getShaderTemplate().setText(this.text7);
      this.shaderNode.getShaderTemplate().invoke4("Host Rectangle");
      this.invoke95();
      this.invoke94(this.shaderSurface);
      this.invoke17();
      this.invoke18();
      this.text6 = null;
      this.namedShaderProgram.close();
      this.intValue4 = this.shaderNode.getIntValue();
      this.invoke2();
      this.setReady("reset");
   }

   private void invoke86(ShaderSurface shaderSurface9) {
      if (shaderSurface9 != null && shaderSurface9 != this.shaderSurface) {
         if (this.shaderNode != null && this.shaderNode.getIntValue() != this.intValue4 && !this.shaderNode.resolve2().isEmpty()) {
            this.shaderSurface2 = shaderSurface9;
            this.flag6 = true;
         } else {
            this.invoke87(shaderSurface9);
         }
      }
   }

   private void invoke87(ShaderSurface shaderSurface10) {
      if (shaderSurface10 != null) {
         this.invoke81();
         this.shaderSurface = shaderSurface10;
         this.invoke94(shaderSurface10);
         this.invoke96();
         this.namedShaderProgram.invoke(shaderSurface10);
         this.namedShaderProgram.invoke2(this.shaderNode);
         this.setReady(shaderSurface10.getText2());
      }
   }

   private void invoke88(boolean bl) {
      if (!ShaderSurfaceTemplates.ITEMS.isEmpty()) {
         int intValue76 = Math.max(0, Math.min(ShaderSurfaceTemplates.ITEMS.size() - 1, this.intValue));
         this.invoke81();
         ShaderSurfaceTemplates.ShaderSurfaceTemplatesState shaderSurfaceTemplatesState2 = ShaderSurfaceTemplates.ITEMS.get(intValue76);
         ShaderNode shaderNode7 = ShaderSurfaceTemplates.resolve(shaderSurfaceTemplatesState2, this.shaderNodeRegistry);
         if (shaderNode7 != null) {
            if (bl) {
               this.invoke89(shaderNode7);
               this.setReady("merged " + shaderSurfaceTemplatesState2.text);
            } else {
               this.shaderNode = shaderNode7;
               this.invoke95();
               ShaderSurface shaderSurface11 = ShaderSurface.resolve4(this.shaderNode.getPreview());
               if (shaderSurface11 != ShaderSurface.PREVIEW_ONLY) {
                  this.shaderSurface = shaderSurface11;
               }

               this.text7 = this.shaderNode.getShaderTemplate().getText().isBlank() ? shaderSurfaceTemplatesState2.text : this.shaderNode.getShaderTemplate().getText();
               this.shaderNode.getShaderTemplate().invoke5(this.text7, ShaderPresetStore.resolve19());
               this.text6 = null;
               this.invoke17();
               this.invoke18();
               this.namedShaderProgram.close();
               this.namedShaderProgram.invoke(this.shaderSurface);
               this.namedShaderProgram.invoke2(this.shaderNode);
               this.floatValue4 = 0.78F;
               this.springAnimation.invoke(0.78F);
               this.floatValue = 720.0F;
               this.floatValue2 = 360.0F;
               this.intValue4 = -1;
               this.invoke2();
               this.setReady("template: " + shaderSurfaceTemplatesState2.text);
            }
         }
      }
   }

   private void invoke89(ShaderNode shaderNode8) {
      if (shaderNode8 != null) {
         HashMap hashMap4 = new HashMap();
         float floatValue325 = this.resolve45().measure(80.0F);
         float floatValue326 = this.resolve45().measure(80.0F);

         for (ShaderNodeKind shaderNodeKind54 : shaderNode8.resolve2()) {
            ShaderNodeKind shaderNodeKind55 = this.shaderNode.resolve(shaderNodeKind54.getText2(), shaderNodeKind54.getFloatValue() + floatValue325, shaderNodeKind54.getFloatValue2() + floatValue326, this.shaderNodeRegistry);
            shaderNodeKind55.getValuesByKey().putAll(shaderNodeKind54.getValuesByKey());
            shaderNodeKind55.getValuesByKey2().putAll(shaderNodeKind54.getValuesByKey2());
            hashMap4.put(shaderNodeKind54.getText(), shaderNodeKind55.getText());
         }

         for (ShaderConnection shaderConnection9 : shaderNode8.getItems()) {
            String text54 = (String)hashMap4.get(shaderConnection9.getText());
            String text55 = (String)hashMap4.get(shaderConnection9.getText3());
            if (text54 != null && text55 != null) {
               this.shaderNode.check2(text54, shaderConnection9.getText2(), text55, shaderConnection9.getText4(), this.shaderNodeRegistry);
            }
         }

         this.shaderNode.invoke4();
      }
   }

   private void invoke90() {
      try {
         File file = ShaderPresetStore.getINSTANCE().resolve6();
         if (!file.exists()) {
            file.mkdirs();
         }

         Util.getOperatingSystem().open(file);
         this.setReady("opened folder");
      } catch (Throwable exception5) {
         this.setReady("open folder failed");
      }
   }

   private String resolve73(long l) {
      return l <= 0L ? "-" : SIMPLE_DATE_FORMAT.format(new Date(l));
   }

   private void invoke91() {
      try {
         this.invoke94(this.shaderSurface);
         this.invoke96();
         File file2 = ShaderPresetStore.getINSTANCE().resolve8(this.shaderSurface, this.shaderNode, this.resolve());
         if (file2 != null) {
            this.setReady("exported -> " + file2.getName());
         } else {
            this.setReady("export failed");
         }
      } catch (Throwable exception6) {
         this.setReady("export failed");
      }
   }

   private void invoke92() {
      try {
         List items5 = ShaderPresetStore.getINSTANCE().resolve9();
         this.shaderLibraryBrowser.invoke(items5);
         this.setReady(items5.isEmpty() ? "no shader files" : "import");
      } catch (Throwable exception7) {
         this.setReady("import failed");
      }
   }

   private void invoke93() {
      File file3 = this.shaderLibraryBrowser.resolve();
      if (file3 != null) {
         try {
            ShaderNode shaderNode9 = ShaderPresetStore.getINSTANCE().resolve10(file3, this.shaderNodeRegistry);
            if (shaderNode9 == null) {
               this.setReady("import failed");
               return;
            }

            this.invoke81();
            this.shaderNode = shaderNode9;
            this.invoke95();
            ShaderSurface shaderSurface12 = ShaderSurface.resolve4(this.shaderNode.getPreview());
            this.shaderSurface = shaderSurface12 == ShaderSurface.PREVIEW_ONLY ? this.shaderSurface : shaderSurface12;
            this.invoke94(this.shaderSurface);
            this.namedShaderProgram.invoke(this.shaderSurface);
            this.text7 = this.shaderNode.getShaderTemplate().getText().isBlank()
               ? resolve5(file3.getName().replace(".wifd", "").replace(".json", ""))
               : this.shaderNode.getShaderTemplate().getText();
            this.invoke17();
            this.invoke18();
            this.text6 = null;
            this.namedShaderProgram.close();
            this.intValue4 = -1;
            this.invoke2();
            this.setReady("imported " + file3.getName());
         } catch (Throwable exception8) {
            this.setReady("import failed");
         }
      }
   }

   private void invoke94(ShaderSurface shaderSurface13) {
      if (this.shaderNode != null && shaderSurface13 != null) {
         this.shaderNode.invoke2(shaderSurface13.getText());
      }
   }

   private void invoke95() {
      this.hostRectangle = this.shaderNode != null && this.shaderNode.getShaderTemplate() != null
         ? this.shaderNode.getShaderTemplate().getHostRectangle()
         : "Host Rectangle";
   }

   private void invoke96() {
      if (this.shaderNode != null && this.shaderNode.getShaderTemplate() != null) {
         this.shaderNode.getShaderTemplate().invoke4(this.hostRectangle);
      }
   }

   private void setReady(String string) {
      this.ready = string != null && !string.isBlank() ? string : "ready";
      this.timestamp3 = System.currentTimeMillis() + 1500L;
   }

   private void invoke97(float f, float g) {
      long longValue2 = System.nanoTime();
      if (this.timestamp2 != 0L) {
         float floatValue327 = Math.max(0.001F, Math.min(0.05F, (float)(longValue2 - this.timestamp2) / 1.0E9F));
         this.floatValue21 = (f - this.floatValue19) / floatValue327;
         this.floatValue22 = (g - this.floatValue20) / floatValue327;
      }

      this.timestamp2 = longValue2;
      this.floatValue19 = f;
      this.floatValue20 = g;
      if (System.currentTimeMillis() > this.timestamp3 && this.namedShaderProgram.resolve2().isBlank()) {
         this.ready = "ready";
      }
   }

   private static float measure20(float f) {
      float floatValue328 = measure21(f, 0.0F, 1.0F);
      return floatValue328 * floatValue328 * floatValue328 * (floatValue328 * (floatValue328 * 6.0F - 15.0F) + 10.0F);
   }

   private static float measure21(float f, float g, float h) {
      return Math.max(g, Math.min(h, f));
   }

   @Override
   public void close() {
      this.namedShaderProgram.close();
      this.shaderNodePreviewRenderer.close();
   }

   record ShaderFoundryScreenData(float x, float y) {
   }

   static enum ShaderFoundryScreenState {
      AUTO("Auto"),
      DARK("Dark"),
      LIGHT("Light");

      private final String text;

      private ShaderFoundryScreenState(String string2) {
         this.text = string2;
      }

      String getText() {
         return this.text;
      }
   }

   record ShaderFoundryScreenData2(SavedShaderPreset slot, int presetIndex) {
   }

   record ShaderFoundryScreenEntry(int type, String category, ShaderNodeDefinition def, int count) {
   }

   record ShaderFoundryScreenData3(ShaderFoundryScreen.ShaderFoundryScreenEntry row, boolean star) {
   }

   record ShaderFoundryScreenData4(String nodeId, String pinId, ShaderPinDirection direction) {
   }

   record ShaderFoundryScreenData5(float x, float y) {
   }

   record ShaderFoundryScreenData6(int a, int b) {
   }
}
