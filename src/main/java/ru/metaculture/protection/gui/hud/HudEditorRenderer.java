package ru.metaculture.protection;

import java.lang.runtime.SwitchBootstraps;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Generated;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import org.lwjgl.glfw.GLFW;

public final class HudEditorRenderer {
   private static final float FLOAT_VALUE = 0.65F;
   private static final float FLOAT_VALUE_2 = 1.0F;
   static final SpringConfig SPRING_CONFIG = SpringConfig.resolve(4.0F, 0.85F);
   static final SpringConfig SPRING_CONFIG_2 = SpringConfig.resolve(2.5F, 0.9F);
   static final SpringConfig SPRING_CONFIG_3 = SpringConfig.resolve(6.0F, 0.8F);
   static final SpringConfig SPRING_CONFIG_4 = SpringConfig.resolve(5.0F, 0.85F);
   private static final SpringConfig SPRING_CONFIG_5 = SpringConfig.resolve(2.2F, 1.0F);
   private static final float FLOAT_VALUE_3 = 12.0F;
   private static final float FLOAT_VALUE_4 = 5.0F;
   private static final float FLOAT_VALUE_5 = 6.0F;
   private static final int INT_VALUE = 32;
   private static final HudEditorRenderer.HudEditorRendererData HUD_EDITOR_RENDERER_DATA = new HudEditorRenderer.HudEditorRendererData(0.82F, 1.18F, 0.72F, 0.22F, 6.0F);
   private static final HudEditorRenderer.HudEditorRendererData HUD_EDITOR_RENDERER_DATA_2 = new HudEditorRenderer.HudEditorRendererData(0.78F, 1.16F, 0.46F, 0.1F, 6.0F);
   private static final HudEditorRenderer.HudEditorRendererData HUD_EDITOR_RENDERER_DATA_3 = new HudEditorRenderer.HudEditorRendererData(0.72F, 1.48F, 0.42F, 0.34F, 6.0F);
   private static final HudEditorRenderer.HudEditorRendererData HUD_EDITOR_RENDERER_DATA_4 = new HudEditorRenderer.HudEditorRendererData(0.78F, 1.34F, 0.36F, 0.26F, 6.0F);
   private static final HudEditorRenderer.HudEditorRendererData HUD_EDITOR_RENDERER_DATA_5 = new HudEditorRenderer.HudEditorRendererData(0.72F, 1.36F, 0.38F, 0.42F, 6.0F);
   private static final HudEditorRenderer.HudEditorRendererData HUD_EDITOR_RENDERER_DATA_6 = new HudEditorRenderer.HudEditorRendererData(0.72F, 1.32F, 0.32F, 0.56F, 6.0F);
   private static final HudEditorRenderer.HudEditorRendererData HUD_EDITOR_RENDERER_DATA_7 = new HudEditorRenderer.HudEditorRendererData(0.72F, 1.18F, 0.34F, 0.52F, 6.0F);
   private static final HudEditorRenderer.HudEditorRendererData HUD_EDITOR_RENDERER_DATA_8 = new HudEditorRenderer.HudEditorRendererData(0.7F, 1.42F, 0.45F, 0.72F, 6.0F);
   private static final HudEditorRenderer.HudEditorRendererData HUD_EDITOR_RENDERER_DATA_9 = new HudEditorRenderer.HudEditorRendererData(0.78F, 1.12F, 0.38F, 0.34F, 6.0F);
   private static final HudEditorRenderer.HudEditorRendererData HUD_EDITOR_RENDERER_DATA_10 = new HudEditorRenderer.HudEditorRendererData(0.72F, 1.32F, 0.38F, 0.56F, 6.0F);
   private static final HudEditorRenderer.HudEditorRendererData HUD_EDITOR_RENDERER_DATA_11 = new HudEditorRenderer.HudEditorRendererData(0.76F, 1.32F, 0.24F, 0.3F, 6.0F);
   private static final HudEditorRenderer.HudEditorRendererData HUD_EDITOR_RENDERER_DATA_12 = new HudEditorRenderer.HudEditorRendererData(0.72F, 1.35F, 0.4F, 0.58F, 6.0F);
   private static final HudEditorRenderer.HudEditorRendererData HUD_EDITOR_RENDERER_DATA_13 = new HudEditorRenderer.HudEditorRendererData(0.72F, 1.42F, 0.42F, 0.55F, 6.0F);
   private static final HudEditorRenderer INSTANCE = new HudEditorRenderer();
   private final Map<String, HudEditorRenderer.HudEditorRendererState2> valuesByKey = new HashMap<>();
   private final Map<String, HudEditorRenderer.HudEditorRendererData2> valuesByKey2 = new ConcurrentHashMap<>();
   private final Map<String, Float> valuesByKey3 = new ConcurrentHashMap<>();
   private final HudEditorRenderer.HudEditorRendererState3[] hudEditorRendererState3s = new HudEditorRenderer.HudEditorRendererState3[32];
   private final HudEditorRenderer.HudEditorRendererState3[] hudEditorRendererState3s2 = new HudEditorRenderer.HudEditorRendererState3[32];
   private final double[] doubles = new double[1];
   private final double[] doubles2 = new double[1];
   private final HudEditorRenderer.HudEditorRendererState4 hudEditorRendererState4 = new HudEditorRenderer.HudEditorRendererState4();
   private RenderManager renderManager;
   private MinecraftClient client;
   private boolean flag;
   private float floatValue;
   private float floatValue2;
   private boolean flag2;
   private boolean flag3;
   private boolean flag4;
   private boolean flag5;
   private boolean flag6;
   private boolean flag7;
   private boolean flag8;
   private boolean flag9;
   private boolean flag10;
   private int intValue;
   private int intValue2;
   private String text = null;
   private float floatValue3 = Float.NaN;
   private float floatValue4 = Float.NaN;
   private float floatValue5 = 0.0F;
   private float floatValue6 = Float.NaN;
   private float floatValue7 = Float.MAX_VALUE;
   private boolean flag11;
   private boolean flag12;
   private boolean flag13;
   private float floatValue8;
   private float floatValue9;
   private final ClampedSpringAnimation clampedSpringAnimation = new ClampedSpringAnimation(AnimationSystem.getINSTANCE(), SPRING_CONFIG_4, 0.0F, 0.0F, 1.0F, 0.01F, 0.01F);
   private final ClampedSpringAnimation clampedSpringAnimation2 = new ClampedSpringAnimation(AnimationSystem.getINSTANCE(), SPRING_CONFIG_5, 0.0F, 0.0F, 1.0F, 0.01F, 0.01F);
   private float floatValue10 = 1.0F;
   private float floatValue11 = 1.0F;
   private String tyomnyy = "Тёмный";
   private float floatValue12 = 5.5F;
   private float floatValue13 = 18.0F;
   private float floatValue14 = 0.72F;
   private String vypuklaya = "Выпуклая";
   private boolean flag14 = true;
   private boolean flag15 = true;
   private boolean flag16 = true;
   private boolean flag17 = true;
   private boolean flag18 = true;
   private boolean flag19 = true;
   private int intValue3;
   private int intValue4;
   private Theme theme;
   private boolean flag20;
   private ColorScheme colorScheme;
   public final HudEditorRenderer.HudEditorRendererVariant hudEditorRendererVariant = new HudEditorRenderer.HudEditorRendererVariant();

   private HudEditorRenderer() {
      for (int intValue = 0; intValue < 32; intValue++) {
         this.hudEditorRendererState3s[intValue] = new HudEditorRenderer.HudEditorRendererState3();
         this.hudEditorRendererState3s2[intValue] = new HudEditorRenderer.HudEditorRendererState3();
      }
   }

   public static HudEditorRenderer getINSTANCE() {
      return INSTANCE;
   }

   public void invoke() {
      this.flag3 = false;
      this.text = null;
   }

   public void invoke2(MinecraftClient minecraftClient, RenderManager renderManager, int i, int j) {
      this.client = minecraftClient;
      this.renderManager = Objects.requireNonNull(renderManager, "renderer");
      this.intValue = Math.max(0, i);
      this.intValue2 = Math.max(0, j);
      this.flag = true;
      this.flag2 = false;
      this.flag11 = false;
      this.floatValue3 = Float.NaN;
      this.floatValue4 = Float.NaN;
      if (minecraftClient != null && minecraftClient.getWindow() != null) {
         this.flag10 = minecraftClient.currentScreen instanceof ChatScreen;
         long longValue = minecraftClient.getWindow().getHandle();
         if (longValue != 0L) {
            GLFW.glfwGetCursorPos(longValue, this.doubles, this.doubles2);
            if (Double.isFinite(this.doubles[0]) && Double.isFinite(this.doubles2[0])) {
               this.floatValue = (float)this.doubles[0];
               this.floatValue2 = (float)this.doubles2[0];
               this.flag2 = true;
            }

            if (this.flag2 && this.flag10) {
               boolean flag = GLFW.glfwGetMouseButton(longValue, 0) == 1;
               this.flag4 = flag && !this.flag5;
               this.flag3 = flag;
               this.flag5 = flag;
               boolean flag2 = GLFW.glfwGetMouseButton(longValue, 2) == 1;
               this.flag7 = flag2 && !this.flag6;
               this.flag6 = flag2;
               boolean flag3 = GLFW.glfwGetMouseButton(longValue, 1) == 1;
               this.flag8 = flag3 && !this.flag9;
               this.flag9 = flag3;
            } else {
               this.flag3 = this.flag4 = this.flag7 = this.flag8 = false;
               this.flag5 = this.flag6 = this.flag9 = false;
            }
         }

         if (!this.flag3) {
            this.text = null;
         }

         if (!this.flag3 && this.flag12) {
            this.flag12 = false;
            if (WildClient.INSTANCE != null && WildClient.INSTANCE.configManager != null) {
               WildClient.INSTANCE.configManager.scheduleSave();
            }
         }
      } else {
         this.flag3 = this.flag4 = this.flag7 = this.flag8 = false;
         this.flag5 = this.flag6 = this.flag9 = false;
         this.flag10 = false;
      }
   }

   public void invoke3() {
      this.invoke10();
      boolean flag4 = this.flag10 && this.flag3 && this.text != null;
      this.clampedSpringAnimation2.invoke2(flag4 ? 1.0F : 0.0F);
      float floatValue = this.clampedSpringAnimation2.measure();
      if ((this.flag10 || !(floatValue <= 0.01F)) && this.intValue > 0 && this.intValue2 > 0) {
         ColorScheme colorScheme = this.resolve4();
         int intValue2 = colorScheme == null ? -7473153 : colorScheme.getIntValue14();
         int intValue3 = colorScheme == null ? -41059 : colorScheme.getIntValue15();
         GravityGridRenderer.getINSTANCE()
            .invoke(
               this.intValue,
               this.intValue2,
               this.hudEditorRendererState3s,
               this.intValue3,
               this.text,
               this.floatValue,
               this.floatValue2,
               floatValue,
               intValue2,
               intValue3
            );
      }
   }

   public void invoke4(int i, int j, HudEditorRenderer.HudEditorRendererState3[] w222s, int k, String string, float f, float g, float h) {
      ColorScheme colorScheme2 = this.resolve4();
      int intValue4 = colorScheme2 == null ? -7473153 : colorScheme2.getIntValue14();
      int intValue5 = colorScheme2 == null ? -41059 : colorScheme2.getIntValue15();
      GravityGridRenderer.getINSTANCE().invoke(i, j, w222s, k, string, f, g, h, intValue4, intValue5);
   }

   public void invoke5() {
      if (this.flag10 && this.flag2) {
         float floatValue2 = 170.0F;
         float floatValue3 = 24.0F;

         for (Setting setting : this.hudEditorRendererVariant.resolve()) {
            if (setting instanceof BooleanSetting) {
               floatValue3 += 28.0F;
            } else if (setting instanceof NumberSetting) {
               floatValue3 += 40.0F;
            } else if (setting instanceof ModeSetting) {
               floatValue3 += 28.0F;
            } else if (setting instanceof GroupSetting groupSetting2) {
               floatValue3 += 28.0F + groupSetting2.options.size() * 28.0F * groupSetting2.animation.measure3();
            }
         }

         HudSettingsRenderer.HudSettingsRendererBounds hudSettingsRendererBounds = this.renderManager != null
            ? HudSettingsRenderer.resolve(this.renderManager, this.hudEditorRendererVariant, this.floatValue8, this.floatValue9, 0.0F, 0.0F)
            : new HudSettingsRenderer.HudSettingsRendererBounds(this.floatValue8, this.floatValue9, floatValue2, floatValue3);
         boolean flag5 = this.flag13 && hudSettingsRendererBounds.contains(this.floatValue, this.floatValue2, 10.0F);
         if (this.flag8) {
            if (this.flag11) {
               this.flag13 = false;
            } else if (!flag5) {
               this.flag13 = !this.flag13;
               if (this.flag13) {
                  this.floatValue8 = this.floatValue;
                  this.floatValue9 = this.floatValue2;
               }
            }

            this.flag8 = false;
         }

         if (this.flag4 && (this.flag11 || !flag5 && this.flag13)) {
            this.flag13 = false;
         }
      }

      if (!this.flag10) {
         this.flag13 = false;
      }

      this.clampedSpringAnimation.invoke2(this.flag13 ? 1.0F : 0.0F);
      float floatValue4 = this.clampedSpringAnimation.measure();
      if (floatValue4 > 0.01F && this.renderManager != null) {
         this.renderManager.invoke(this.intValue, this.intValue2);
         HudSettingsRenderer.invoke(
            this.renderManager,
            this.hudEditorRendererVariant,
            this.floatValue8,
            this.floatValue9,
            0.0F,
            0.0F,
            this.intValue,
            this.intValue2,
            floatValue4,
            this.floatValue,
            this.floatValue2,
            this.flag4,
            this.flag3
         );
         this.renderManager.invoke19();
         float floatValue5 = this.hudEditorRendererVariant.prozrachnost.getValue();
         float floatValue6 = this.hudEditorRendererVariant.prozrachnostTyomnyhElementov.getValue();
         String text = this.hudEditorRendererVariant.stilistika.getValue();
         float floatValue7 = this.hudEditorRendererVariant.neoDistantsiya.getValue();
         float floatValue8 = this.hudEditorRendererVariant.neoRazmytie.getValue();
         float floatValue9 = this.hudEditorRendererVariant.neoIntensivnost.getValue();
         String text2 = this.hudEditorRendererVariant.neoForma.getValue();
         boolean flag6 = this.hudEditorRendererVariant.vizual.isEnabled("Тень");
         boolean flag7 = this.hudEditorRendererVariant.vizual.isEnabled("Обводка");
         boolean flag8 = this.hudEditorRendererVariant.vizual.isEnabled("Темные зоны");
         boolean flag9 = this.hudEditorRendererVariant.vizual.isEnabled("Верхняя накладка");
         boolean flag10 = this.hudEditorRendererVariant.vizual.isEnabled("Нижняя накладка");
         boolean flag11 = this.hudEditorRendererVariant.vizual.isEnabled("Тёмный рект поверх");
         if (floatValue5 != this.floatValue10
            || floatValue6 != this.floatValue11
            || !text.equals(this.tyomnyy)
            || floatValue7 != this.floatValue12
            || floatValue8 != this.floatValue13
            || floatValue9 != this.floatValue14
            || !text2.equals(this.vypuklaya)
            || flag6 != this.flag14
            || flag7 != this.flag15
            || flag8 != this.flag16
            || flag9 != this.flag17
            || flag10 != this.flag18
            || flag11 != this.flag19) {
            this.floatValue10 = floatValue5;
            this.floatValue11 = floatValue6;
            this.tyomnyy = text;
            this.floatValue12 = floatValue7;
            this.floatValue13 = floatValue8;
            this.floatValue14 = floatValue9;
            this.vypuklaya = text2;
            this.flag14 = flag6;
            this.flag15 = flag7;
            this.flag16 = flag8;
            this.flag17 = flag9;
            this.flag18 = flag10;
            this.flag19 = flag11;

            for (ConfigurableHudElement configurableHudElement : ru.metaculture.protection.HudPresetManager.resolve2()) {
               if (configurableHudElement != this.hudEditorRendererVariant) {
                  for (Setting setting2 : configurableHudElement.resolve()) {
                     if (setting2 instanceof NumberSetting numberSetting) {
                        if (setting2.name.equals("Прозрачность")) {
                           numberSetting.invoke(floatValue5);
                        } else if (setting2.name.equals("Прозрачность тёмных элементов")) {
                           numberSetting.invoke(floatValue6);
                        } else if (setting2.name.equals("Нео дистанция")) {
                           numberSetting.invoke(floatValue7);
                        } else if (setting2.name.equals("Нео размытие")) {
                           numberSetting.invoke(floatValue8);
                        } else if (setting2.name.equals("Нео интенсивность")) {
                           numberSetting.invoke(floatValue9);
                        }
                     } else if (setting2 instanceof ModeSetting modeSetting) {
                        if (setting2.name.equals("Стилистика")) {
                           int idx = modeSetting.options.indexOf(text);
                           if (idx != -1) {
                              modeSetting.selectedIndex = idx;
                              modeSetting.value = text;
                           }
                        } else if (setting2.name.equals("Нео форма")) {
                           int idx = modeSetting.options.indexOf(text2);
                           if (idx != -1) {
                              modeSetting.selectedIndex = idx;
                              modeSetting.value = text2;
                           }
                        }
                     } else if (setting2 instanceof GroupSetting groupSetting) {
                        if (setting2.name.equals("Визуал")) {
                           for (BooleanSetting booleanSetting : groupSetting.options) {
                              if (booleanSetting.name.equals("Тень")) {
                                 booleanSetting.setValue(flag6);
                              }

                              if (booleanSetting.name.equals("Обводка")) {
                                 booleanSetting.setValue(flag7);
                              }

                              if (booleanSetting.name.equals("Темные зоны")) {
                                 booleanSetting.setValue(flag8);
                              }

                              if (booleanSetting.name.equals("Верхняя накладка")) {
                                 booleanSetting.setValue(flag9);
                              }

                              if (booleanSetting.name.equals("Нижняя накладка")) {
                                 booleanSetting.setValue(flag10);
                              }

                              if (booleanSetting.name.equals("Тёмный рект поверх")) {
                                 booleanSetting.setValue(flag11);
                              }
                           }
                        }
                     }
                  }
               }
            }

            ru.metaculture.protection.HudPresetManager.invoke5();
         }
      }

      this.flag = false;
      this.renderManager = null;
   }

   public HudEditorRenderer.HudEditorRendererState resolve(String string, float f, float g, float h, float i) {
      if (!this.flag) {
         throw new IllegalStateException("beginFrame must be called first");
      } else {
         String text3 = string.trim();
         HudEditorRenderer.HudEditorRendererData2 hudEditorRendererData2 = this.valuesByKey2.get(text3);
         HudEditorRenderer.HudEditorRendererState2 hudEditorRendererState2 = this.valuesByKey.computeIfAbsent(string, stringx -> new HudEditorRenderer.HudEditorRendererState2());
         hudEditorRendererState2.floatValue5 = measure15(h);
         hudEditorRendererState2.floatValue6 = measure15(i);
         HudEditorRenderer.HudEditorRendererData hudEditorRendererData = this.resolve5(text3);
         boolean flag12 = this.flag2 && this.flag10;
         Float floatValue10 = hudEditorRendererData2 == null ? this.valuesByKey3.remove(text3) : null;
         boolean flag13 = floatValue10 != null;
         boolean flag14 = hudEditorRendererData2 != null && hudEditorRendererData2.userResized() || flag13;
         float floatValue11 = flag13 ? floatValue10 : this.measure10(hudEditorRendererData2, flag14);
         HudEditorRenderer.HudEditorRendererState4 hudEditorRendererState4 = this.resolve8(text3, h, i, floatValue11);
         floatValue11 = hudEditorRendererState4.getFloatValue3();
         if (!hudEditorRendererState2.flag3) {
            hudEditorRendererState2.floatValue3 = hudEditorRendererState4.getFloatValue();
            hudEditorRendererState2.floatValue4 = hudEditorRendererState4.getFloatValue2();
         }

         float floatValue12 = this.measure(hudEditorRendererData2, f, hudEditorRendererState2.floatValue3, hudEditorRendererData);
         float floatValue13 = this.measure2(hudEditorRendererData2, g, hudEditorRendererState2.floatValue4, hudEditorRendererData);
         if (flag13) {
            this.invoke12(text3, floatValue12, floatValue13, floatValue11, floatValue11, true);
            hudEditorRendererData2 = this.valuesByKey2.get(text3);
         }

         float floatValue14 = hudEditorRendererState2.flag ? hudEditorRendererState2.clampedSpringAnimation2.measure() : floatValue12;
         float floatValue15 = hudEditorRendererState2.flag ? hudEditorRendererState2.clampedSpringAnimation3.measure() : floatValue13;
         boolean flag15 = check3(this.floatValue, this.floatValue2, floatValue14, floatValue15, hudEditorRendererState2.floatValue3, hudEditorRendererState2.floatValue4);
         boolean flag16 = check3(
            this.floatValue, this.floatValue2, floatValue14 + hudEditorRendererState2.floatValue3 - 12.0F, floatValue15 + hudEditorRendererState2.floatValue4 - 12.0F, 12.0F, 12.0F
         );
         if (flag12 && (flag15 || flag16)) {
            this.flag11 = true;
         }

         hudEditorRendererState2.clampedSpringAnimation4.invoke2(!flag12 || !flag15 && !flag16 ? 0.0F : 1.0F);
         if (flag12 && this.flag8 && flag15) {
            hudEditorRendererState2.flag4 = !hudEditorRendererState2.flag4;
            this.flag13 = false;
            this.flag8 = false;
         }

         if (!this.flag10) {
            hudEditorRendererState2.flag4 = false;
         }

         hudEditorRendererState2.clampedSpringAnimation5.invoke2(hudEditorRendererState2.flag4 ? 1.0F : 0.0F);
         if (flag12 && this.flag7 && (flag15 || flag16)) {
            this.invoke12(text3, hudEditorRendererState2.clampedSpringAnimation2.getFloatValue6(), hudEditorRendererState2.clampedSpringAnimation3.getFloatValue6(), 1.0F, 1.0F, false);
            hudEditorRendererData2 = this.valuesByKey2.get(text3);
            flag14 = false;
            floatValue11 = 1.0F;
            hudEditorRendererState4 = this.resolve8(text3, h, i, floatValue11);
            hudEditorRendererState2.floatValue3 = hudEditorRendererState4.getFloatValue();
            hudEditorRendererState2.floatValue4 = hudEditorRendererState4.getFloatValue2();
            floatValue12 = this.measure(hudEditorRendererData2, f, hudEditorRendererState2.floatValue3, hudEditorRendererData);
            floatValue13 = this.measure2(hudEditorRendererData2, g, hudEditorRendererState2.floatValue4, hudEditorRendererData);
            if (string.equals(this.text)) {
               this.text = null;
            }

            hudEditorRendererState2.flag3 = false;
            this.flag7 = false;
         }

         if (!hudEditorRendererState2.flag) {
            hudEditorRendererState2.clampedSpringAnimation2.invoke(floatValue12);
            hudEditorRendererState2.clampedSpringAnimation3.invoke(floatValue13);
            hudEditorRendererState2.flag = true;
         }

         if (!this.flag3 || !flag12) {
            hudEditorRendererState2.flag2 = false;
            hudEditorRendererState2.flag3 = false;
         } else if (!hudEditorRendererState2.flag2 && !hudEditorRendererState2.flag3 && (this.text == null || this.text.equals(string))) {
            if (flag16) {
               hudEditorRendererState2.flag3 = true;
               this.text = string;
               hudEditorRendererState2.floatValue = hudEditorRendererState2.floatValue3 - this.floatValue;
               hudEditorRendererState2.floatValue2 = hudEditorRendererState2.floatValue4 - this.floatValue2;
            } else if (flag15) {
               hudEditorRendererState2.flag2 = true;
               this.text = string;
               hudEditorRendererState2.floatValue = this.floatValue - floatValue14;
               hudEditorRendererState2.floatValue2 = this.floatValue2 - floatValue15;
            }
         }

         boolean flag17 = hudEditorRendererState2.flag2 && string.equals(this.text);
         boolean flag18 = hudEditorRendererState2.flag3 && string.equals(this.text);
         if (flag17 || flag18) {
            this.flag13 = false;
         }

         boolean flag19 = this.client != null
            && this.client.getWindow() != null
            && (GLFW.glfwGetKey(this.client.getWindow().getHandle(), 341) == 1 || GLFW.glfwGetKey(this.client.getWindow().getHandle(), 345) == 1);
         if (flag17) {
            floatValue12 = this.measure11(this.floatValue - hudEditorRendererState2.floatValue, hudEditorRendererState2.floatValue3, hudEditorRendererData);
            floatValue13 = this.measure12(this.floatValue2 - hudEditorRendererState2.floatValue2, hudEditorRendererState2.floatValue4, hudEditorRendererData);
            if (flag19) {
               floatValue12 = Math.round(floatValue12 / 10.0F) * 10.0F;
               floatValue13 = Math.round(floatValue13 / 10.0F) * 10.0F;
            }

            float floatValue16 = this.measure3(text3, floatValue12, hudEditorRendererState2.floatValue3);
            float floatValue17 = this.measure4(text3, floatValue13, hudEditorRendererState2.floatValue4);
            floatValue12 = this.measure11(floatValue16, hudEditorRendererState2.floatValue3, hudEditorRendererData);
            floatValue13 = this.measure12(floatValue17, hudEditorRendererState2.floatValue4, hudEditorRendererData);
            this.invoke12(text3, floatValue12, floatValue13, floatValue11, floatValue11, flag14);
         } else if (flag18) {
            float floatValue18 = Math.max(1.0F, this.floatValue + hudEditorRendererState2.floatValue);
            float floatValue19 = Math.max(1.0F, this.floatValue2 + hudEditorRendererState2.floatValue2);
            float floatValue20 = floatValue18 / Math.max(1.0F, h);
            float floatValue21 = floatValue19 / Math.max(1.0F, i);
            float floatValue22 = (floatValue20 + floatValue21) * 0.5F;
            if (flag19) {
               floatValue22 = Math.round(floatValue22 * 20.0F) / 20.0F;
            }

            HudEditorRenderer.HudEditorRendererState4 hudEditorRendererState42 = this.resolve8(text3, h, i, floatValue22);
            hudEditorRendererState2.floatValue3 = hudEditorRendererState42.getFloatValue();
            hudEditorRendererState2.floatValue4 = hudEditorRendererState42.getFloatValue2();
            floatValue11 = hudEditorRendererState42.getFloatValue3();
            floatValue12 = this.measure11(floatValue12, hudEditorRendererState2.floatValue3, hudEditorRendererData);
            floatValue13 = this.measure12(floatValue13, hudEditorRendererState2.floatValue4, hudEditorRendererData);
            this.invoke12(text3, floatValue12, floatValue13, floatValue11, floatValue11, true);
         } else if (flag14 && hudEditorRendererData2 != null && (Math.abs(hudEditorRendererData2.scaleX() - floatValue11) > 0.001F || Math.abs(hudEditorRendererData2.scaleY() - floatValue11) > 0.001F)) {
            this.invoke12(text3, floatValue12, floatValue13, floatValue11, floatValue11, Math.abs(floatValue11 - 1.0F) > 0.01F);
         }

         floatValue12 = this.measure11(floatValue12, hudEditorRendererState2.floatValue3, hudEditorRendererData);
         floatValue13 = this.measure12(floatValue13, hudEditorRendererState2.floatValue4, hudEditorRendererData);
         hudEditorRendererState2.clampedSpringAnimation2.invoke2(floatValue12);
         hudEditorRendererState2.clampedSpringAnimation3.invoke2(floatValue13);
         float floatValue23 = hudEditorRendererState2.clampedSpringAnimation2.measure();
         float floatValue24 = hudEditorRendererState2.clampedSpringAnimation3.measure();
         float floatValue25 = !flag17 && !flag18 ? 1.0F : 0.65F;
         hudEditorRendererState2.clampedSpringAnimation.invoke2(floatValue25);
         float floatValue26 = hudEditorRendererState2.clampedSpringAnimation.measure();
         boolean flag20 = false;
         if (floatValue26 < 0.99F) {
            this.renderManager.invoke65(floatValue26);
            flag20 = true;
         }

         boolean flag21 = flag12
            && check3(this.floatValue, this.floatValue2, floatValue23 + hudEditorRendererState2.floatValue3 - 12.0F, floatValue24 + hudEditorRendererState2.floatValue4 - 12.0F, 12.0F, 12.0F);
         return hudEditorRendererState2.hudEditorRendererState
            .resolve(
               string,
               floatValue23,
               floatValue24,
               hudEditorRendererState2.floatValue3,
               hudEditorRendererState2.floatValue4,
               flag17 || flag18,
               flag21,
               flag20,
               hudEditorRendererState2.flag4,
               hudEditorRendererState2.clampedSpringAnimation4.measure(),
               hudEditorRendererState2.clampedSpringAnimation5.measure()
            );
      }
   }

   public HudEditorRenderer.HudEditorRendererState resolve2(String string, float f, float g, float h, float i) {
      if (!this.flag) {
         throw new IllegalStateException("beginFrame must be called first");
      } else {
         String text4 = string.trim();
         HudEditorRenderer.HudEditorRendererData2 hudEditorRendererData22 = this.valuesByKey2.get(text4);
         HudEditorRenderer.HudEditorRendererState2 hudEditorRendererState22 = this.valuesByKey.computeIfAbsent(string, stringx -> new HudEditorRenderer.HudEditorRendererState2());
         hudEditorRendererState22.floatValue5 = measure15(h);
         hudEditorRendererState22.floatValue6 = measure15(i);
         HudEditorRenderer.HudEditorRendererData hudEditorRendererData3 = this.resolve5(text4);
         boolean flag22 = this.flag2 && this.flag10;
         Float floatValue27 = hudEditorRendererData22 == null ? this.valuesByKey3.remove(text4) : null;
         boolean flag23 = floatValue27 != null;
         boolean flag24 = hudEditorRendererData22 != null && hudEditorRendererData22.userResized() || flag23;
         float floatValue28 = flag23 ? floatValue27 : this.measure10(hudEditorRendererData22, flag24);
         HudEditorRenderer.HudEditorRendererState4 hudEditorRendererState43 = this.resolve8(text4, h, i, floatValue28);
         floatValue28 = hudEditorRendererState43.getFloatValue3();
         if (!hudEditorRendererState22.flag3) {
            hudEditorRendererState22.floatValue3 = hudEditorRendererState43.getFloatValue();
            hudEditorRendererState22.floatValue4 = hudEditorRendererState43.getFloatValue2();
         }

         float floatValue29 = this.measure(hudEditorRendererData22, f, hudEditorRendererState22.floatValue3, hudEditorRendererData3);
         float floatValue30 = this.measure2(hudEditorRendererData22, g, hudEditorRendererState22.floatValue4, hudEditorRendererData3);
         if (flag23) {
            this.invoke12(text4, floatValue29, floatValue30, floatValue28, floatValue28, true);
            hudEditorRendererData22 = this.valuesByKey2.get(text4);
         }

         float floatValue31 = measure16(f, floatValue29);
         float floatValue32 = measure16(g, floatValue30);
         boolean flag25 = check3(this.floatValue, this.floatValue2, floatValue31, floatValue32, hudEditorRendererState22.floatValue3, hudEditorRendererState22.floatValue4);
         boolean flag26 = check3(
            this.floatValue, this.floatValue2, floatValue31 + hudEditorRendererState22.floatValue3 - 12.0F, floatValue32 + hudEditorRendererState22.floatValue4 - 12.0F, 12.0F, 12.0F
         );
         if (flag22 && (flag25 || flag26)) {
            this.flag11 = true;
         }

         hudEditorRendererState22.clampedSpringAnimation4.invoke2(!flag22 || !flag25 && !flag26 ? 0.0F : 1.0F);
         if (flag22 && this.flag8 && flag25) {
            hudEditorRendererState22.flag4 = !hudEditorRendererState22.flag4;
            this.flag13 = false;
            this.flag8 = false;
         }

         if (!this.flag10) {
            hudEditorRendererState22.flag4 = false;
         }

         hudEditorRendererState22.clampedSpringAnimation5.invoke2(hudEditorRendererState22.flag4 ? 1.0F : 0.0F);
         hudEditorRendererState22.flag2 = false;
         if (flag22 && this.flag7 && (flag25 || flag26)) {
            this.invoke12(text4, floatValue29, floatValue30, 1.0F, 1.0F, false);
            hudEditorRendererData22 = this.valuesByKey2.get(text4);
            flag24 = false;
            floatValue28 = 1.0F;
            hudEditorRendererState43 = this.resolve8(text4, h, i, floatValue28);
            hudEditorRendererState22.floatValue3 = hudEditorRendererState43.getFloatValue();
            hudEditorRendererState22.floatValue4 = hudEditorRendererState43.getFloatValue2();
            if (string.equals(this.text)) {
               this.text = null;
            }

            hudEditorRendererState22.flag3 = false;
            this.flag7 = false;
         }

         if (!this.flag3 || !flag22) {
            hudEditorRendererState22.flag3 = false;
         } else if (!hudEditorRendererState22.flag3 && (this.text == null || this.text.equals(string)) && flag26) {
            hudEditorRendererState22.flag3 = true;
            this.text = string;
            hudEditorRendererState22.floatValue = hudEditorRendererState22.floatValue3 - this.floatValue;
            hudEditorRendererState22.floatValue2 = hudEditorRendererState22.floatValue4 - this.floatValue2;
         }

         boolean flag27 = hudEditorRendererState22.flag3 && string.equals(this.text);
         if (flag27) {
            this.flag13 = false;
            float floatValue33 = Math.max(1.0F, this.floatValue + hudEditorRendererState22.floatValue);
            float floatValue34 = Math.max(1.0F, this.floatValue2 + hudEditorRendererState22.floatValue2);
            float floatValue35 = floatValue33 / Math.max(1.0F, h);
            float floatValue36 = floatValue34 / Math.max(1.0F, i);
            float floatValue37 = (floatValue35 + floatValue36) * 0.5F;
            if (this.client != null
               && this.client.getWindow() != null
               && (
                  GLFW.glfwGetKey(this.client.getWindow().getHandle(), 341) == 1
                     || GLFW.glfwGetKey(this.client.getWindow().getHandle(), 345) == 1
               )) {
               floatValue37 = Math.round(floatValue37 * 20.0F) / 20.0F;
            }

            HudEditorRenderer.HudEditorRendererState4 hudEditorRendererState44 = this.resolve8(text4, h, i, floatValue37);
            hudEditorRendererState22.floatValue3 = hudEditorRendererState44.getFloatValue();
            hudEditorRendererState22.floatValue4 = hudEditorRendererState44.getFloatValue2();
            floatValue28 = hudEditorRendererState44.getFloatValue3();
            this.invoke12(text4, floatValue29, floatValue30, floatValue28, floatValue28, true);
         } else if (flag24 && hudEditorRendererData22 != null && (Math.abs(hudEditorRendererData22.scaleX() - floatValue28) > 0.001F || Math.abs(hudEditorRendererData22.scaleY() - floatValue28) > 0.001F)) {
            this.invoke12(text4, floatValue29, floatValue30, floatValue28, floatValue28, Math.abs(floatValue28 - 1.0F) > 0.01F);
         }

         float floatValue38 = flag27 ? 0.65F : 1.0F;
         hudEditorRendererState22.clampedSpringAnimation.invoke2(floatValue38);
         float floatValue39 = hudEditorRendererState22.clampedSpringAnimation.measure();
         boolean flag28 = false;
         if (floatValue39 < 0.99F) {
            this.renderManager.invoke65(floatValue39);
            flag28 = true;
         }

         boolean flag29 = flag22
            && check3(this.floatValue, this.floatValue2, floatValue31 + hudEditorRendererState22.floatValue3 - 12.0F, floatValue32 + hudEditorRendererState22.floatValue4 - 12.0F, 12.0F, 12.0F);
         return hudEditorRendererState22.hudEditorRendererState
            .resolve(
               string,
               floatValue31,
               floatValue32,
               hudEditorRendererState22.floatValue3,
               hudEditorRendererState22.floatValue4,
               flag27,
               flag29,
               flag28,
               hudEditorRendererState22.flag4,
               hudEditorRendererState22.clampedSpringAnimation4.measure(),
               hudEditorRendererState22.clampedSpringAnimation5.measure()
            );
      }
   }

   public HudEditorRenderer.HudEditorRendererState resolve3(HudEditorRenderer.HudEditorRendererState hudEditorRendererState, float f, float g, float h, float i) {
      if (hudEditorRendererState != null && hudEditorRendererState.text != null && !(f <= 0.0F) && !(g <= 0.0F) && Float.isFinite(f) && Float.isFinite(g)) {
         HudEditorRenderer.HudEditorRendererState2 hudEditorRendererState23 = this.valuesByKey.get(hudEditorRendererState.text);
         if (hudEditorRendererState23 == null) {
            return hudEditorRendererState;
         } else {
            HudEditorRenderer.HudEditorRendererData hudEditorRendererData4 = this.resolve5(hudEditorRendererState.text.trim());
            float floatValue40 = (float)Math.sqrt(Math.max(1.0E-4F, f / Math.max(1.0F, h) * (g / Math.max(1.0F, i))));
            HudEditorRenderer.HudEditorRendererState4 hudEditorRendererState45 = this.resolve8(hudEditorRendererState.text, h, i, floatValue40);
            float floatValue41 = hudEditorRendererState45.getFloatValue();
            float floatValue42 = hudEditorRendererState45.getFloatValue2();
            float floatValue43 = this.measure11(hudEditorRendererState.floatValue, floatValue41, hudEditorRendererData4);
            float floatValue44 = this.measure12(hudEditorRendererState.floatValue2, floatValue42, hudEditorRendererData4);
            hudEditorRendererState23.floatValue3 = floatValue41;
            hudEditorRendererState23.floatValue4 = floatValue42;
            String text5 = hudEditorRendererState.text.trim();
            float floatValue45 = this.measure11(Float.isFinite(hudEditorRendererState23.clampedSpringAnimation2.getFloatValue6()) ? hudEditorRendererState23.clampedSpringAnimation2.getFloatValue6() : floatValue43, floatValue41, hudEditorRendererData4);
            float floatValue46 = this.measure12(Float.isFinite(hudEditorRendererState23.clampedSpringAnimation3.getFloatValue6()) ? hudEditorRendererState23.clampedSpringAnimation3.getFloatValue6() : floatValue44, floatValue42, hudEditorRendererData4);
            hudEditorRendererState23.clampedSpringAnimation2.invoke2(floatValue45);
            hudEditorRendererState23.clampedSpringAnimation3.invoke2(floatValue46);
            boolean flag30 = Math.abs(hudEditorRendererState45.getFloatValue3() - 1.0F) > 0.01F;
            this.invoke12(text5, floatValue45, floatValue46, hudEditorRendererState45.getFloatValue3(), hudEditorRendererState45.getFloatValue3(), flag30);
            boolean flag31 = this.flag10
               && this.flag2
               && check3(this.floatValue, this.floatValue2, floatValue43 + floatValue41 - 12.0F, floatValue44 + floatValue42 - 12.0F, 12.0F, 12.0F);
            return hudEditorRendererState.resolve(
               hudEditorRendererState.text,
               floatValue43,
               floatValue44,
               floatValue41,
               floatValue42,
               hudEditorRendererState.flag,
               flag31,
               hudEditorRendererState.flag3,
               hudEditorRendererState.flag4,
               hudEditorRendererState.floatValue5,
               hudEditorRendererState.floatValue6
            );
         }
      } else {
         return hudEditorRendererState;
      }
   }

   public void invoke6(HudEditorRenderer.HudEditorRendererState hudEditorRendererState3) {
      this.invoke7(
         hudEditorRendererState3,
         hudEditorRendererState3 == null ? 0.0F : hudEditorRendererState3.floatValue,
         hudEditorRendererState3 == null ? 0.0F : hudEditorRendererState3.floatValue2,
         hudEditorRendererState3 == null ? 0.0F : hudEditorRendererState3.floatValue3,
         hudEditorRendererState3 == null ? 0.0F : hudEditorRendererState3.floatValue4
      );
   }

   public void invoke7(HudEditorRenderer.HudEditorRendererState hudEditorRendererState5, float f, float g, float h, float i) {
      if (hudEditorRendererState5 != null && this.renderManager != null) {
         this.invoke9(hudEditorRendererState5, f, g, h, i);
         if (this.flag10 && hudEditorRendererState5.text != null) {
            float floatValue47 = Math.max(hudEditorRendererState5.floatValue5, hudEditorRendererState5.flag ? 1.0F : 0.0F);
            if (floatValue47 > 0.01F) {
               float floatValue48 = Math.max(5.0F, Math.min(12.0F, Math.min(h, i) * 0.16F));
               int intValue6 = ColorUtils.compute43(255, 255, 255, (int)((hudEditorRendererState5.flag ? 62 : 34) * floatValue47));
               int intValue7 = ColorUtils.compute43(125, 210, 255, (int)((hudEditorRendererState5.flag ? 28 : 14) * floatValue47));
               this.renderManager.invoke41(f, g, h, i, floatValue48, hudEditorRendererState5.flag ? 7.0F : 4.0F, 0.8F, intValue7);
               this.renderManager.invoke28(f, g, h, i, floatValue48, intValue6, hudEditorRendererState5.flag ? 1.25F : 1.0F);
            }

            int intValue8 = !hudEditorRendererState5.flag2 && !hudEditorRendererState5.flag ? 721420287 : -2130706433;
            float floatValue49 = f + h - 6.0F;
            float floatValue50 = g + i - 6.0F;
            this.renderManager.invoke5(floatValue49, floatValue50, 2.5F, 2.5F, 1.0F, intValue8);
            this.renderManager.invoke5(floatValue49 - 4.5F, floatValue50, 2.5F, 2.5F, 1.0F, intValue8);
            this.renderManager.invoke5(floatValue49, floatValue50 - 4.5F, 2.5F, 2.5F, 1.0F, intValue8);
            if (hudEditorRendererState5.floatValue5 > 0.01F && !hudEditorRendererState5.flag) {
               this.invoke11(this.renderManager, f, g, h, hudEditorRendererState5.floatValue5);
            }

            if (hudEditorRendererState5.flag) {
               this.invoke15(this.renderManager);
            }
         }

         if (hudEditorRendererState5.flag3) {
            this.renderManager.invoke66();
         }
      }
   }

   private void invoke8(HudEditorRenderer.HudEditorRendererState hudEditorRendererState6) {
      this.invoke9(
         hudEditorRendererState6,
         hudEditorRendererState6 == null ? 0.0F : hudEditorRendererState6.floatValue,
         hudEditorRendererState6 == null ? 0.0F : hudEditorRendererState6.floatValue2,
         hudEditorRendererState6 == null ? 0.0F : hudEditorRendererState6.floatValue3,
         hudEditorRendererState6 == null ? 0.0F : hudEditorRendererState6.floatValue4
      );
   }

   private void invoke9(HudEditorRenderer.HudEditorRendererState hudEditorRendererState7, float f, float g, float h, float i) {
      if (hudEditorRendererState7 != null && hudEditorRendererState7.text != null && Float.isFinite(f) && Float.isFinite(g) && !(h <= 0.0F) && !(i <= 0.0F)) {
         int intValue9 = this.intValue4;
         if (intValue9 >= 32) {
            if (!hudEditorRendererState7.flag) {
               return;
            }

            intValue9 = 31;
         } else {
            this.intValue4++;
         }

         float floatValue51 = f + h * 0.5F;
         float floatValue52 = g + i * 0.5F;
         float floatValue53 = (float)Math.sqrt(h * h + i * i) * 0.5F;
         float floatValue54 = hudEditorRendererState7.flag ? 1.0F : 0.34F + Math.min(0.24F, hudEditorRendererState7.floatValue5 * 0.24F);
         this.hudEditorRendererState3s2[intValue9].invoke(hudEditorRendererState7.text, floatValue51, floatValue52, Math.max(34.0F, floatValue53), floatValue54, h, i);
      }
   }

   private void invoke10() {
      this.intValue3 = this.intValue4;

      for (int intValue10 = 0; intValue10 < this.intValue3; intValue10++) {
         this.hudEditorRendererState3s[intValue10].invoke2(this.hudEditorRendererState3s2[intValue10]);
      }

      this.intValue4 = 0;
   }

   private ColorScheme resolve4() {
      Theme theme = Theme.WILD;
      if (WildClient.INSTANCE != null && WildClient.INSTANCE.themeManager != null) {
         theme = WildClient.INSTANCE.themeManager.getTheme();
      }

      boolean flag32 = theme == Theme.VERNAL_SOLSTICE || theme == Theme.SAKURA_BREEZE || theme == Theme.PORCELAIN_DAWN || theme == Theme.FRUTIGER_AERO;
      if (this.colorScheme == null || this.theme != theme || this.flag20 != flag32 || theme == Theme.CUSTOM) {
         this.colorScheme = ColorScheme.resolve2(theme, flag32);
         this.theme = theme;
         this.flag20 = flag32;
      }

      return this.colorScheme;
   }

   private void invoke11(RenderManager renderManager2, float f, float g, float h, float i) {
      String text6 = "ЛКМ - Для перемещения";
      String text7 = "ПКМ - Для настроек";
      float floatValue55 = 25.0F;
      float floatValue56 = RenderManager.resolve7(FontRegistry.fontObject, text6, floatValue55).floatValue;
      float floatValue57 = RenderManager.resolve7(FontRegistry.fontObject, text7, floatValue55).floatValue;
      float floatValue58 = floatValue55 * 2.0F + 4.0F;
      float floatValue59 = f + h / 2.0F;
      float floatValue60 = g - floatValue58 - 8.0F;
      int intValue11 = (int)(255.0F * i);
      if (intValue11 > 5) {
         int intValue12 = RenderManager.RenderManagerState.compute32(255, 255, 255, intValue11);
         renderManager2.invoke69(FontRegistry.fontObject, floatValue59 - floatValue56 / 2.0F, floatValue60 + 6.0F, floatValue55, text6, intValue12);
         renderManager2.invoke69(FontRegistry.fontObject, floatValue59 - floatValue57 / 2.0F, floatValue60 + 6.0F + floatValue55 + 2.0F, floatValue55, text7, intValue12);
      }
   }

   private float measure(HudEditorRenderer.HudEditorRendererData2 hudEditorRendererData23, float f, float g, HudEditorRenderer.HudEditorRendererData hudEditorRendererData5) {
      return hudEditorRendererData23 != null && this.intValue > 0
         ? this.measure11(hudEditorRendererData23.nx() * this.intValue, g, hudEditorRendererData5)
         : this.measure11(f, g, hudEditorRendererData5);
   }

   private float measure2(HudEditorRenderer.HudEditorRendererData2 hudEditorRendererData24, float f, float g, HudEditorRenderer.HudEditorRendererData hudEditorRendererData6) {
      return hudEditorRendererData24 != null && this.intValue2 > 0
         ? this.measure12(hudEditorRendererData24.ny() * this.intValue2, g, hudEditorRendererData6)
         : this.measure12(f, g, hudEditorRendererData6);
   }

   private void invoke12(String string, float f, float g, float h, float i, boolean bl) {
      if (this.intValue > 0 && this.intValue2 > 0 && string != null) {
         float floatValue61 = f / this.intValue;
         float floatValue62 = g / this.intValue2;
         HudEditorRenderer.HudEditorRendererData2 hudEditorRendererData25 = this.valuesByKey2.get(string);
         if (!this.check(hudEditorRendererData25, floatValue61, floatValue62, h, i, bl)) {
            this.valuesByKey2.put(string, new HudEditorRenderer.HudEditorRendererData2(floatValue61, floatValue62, h, i, bl));
            this.flag12 = true;
         }
      }
   }

   private boolean check(HudEditorRenderer.HudEditorRendererData2 hudEditorRendererData26, float f, float g, float h, float i, boolean bl) {
      return hudEditorRendererData26 == null
         ? false
         : Math.abs(hudEditorRendererData26.nx() - f) < 1.0E-5F
            && Math.abs(hudEditorRendererData26.ny() - g) < 1.0E-5F
            && Math.abs(hudEditorRendererData26.scaleX() - h) < 1.0E-4F
            && Math.abs(hudEditorRendererData26.scaleY() - i) < 1.0E-4F
            && hudEditorRendererData26.userResized() == bl;
   }

   private float measure3(String string, float f, float g) {
      this.floatValue3 = Float.NaN;
      this.invoke13();
      this.invoke14(f, 0.0F, 0.0F);
      this.invoke14(f, this.intValue - g, this.intValue);
      this.invoke14(f, this.intValue * 0.5F - g * 0.5F, this.intValue * 0.5F);

      for (Entry entry : this.valuesByKey.entrySet()) {
         if (!((String)entry.getKey()).equals(string)) {
            HudEditorRenderer.HudEditorRendererState2 hudEditorRendererState24 = (HudEditorRenderer.HudEditorRendererState2)entry.getValue();
            if (hudEditorRendererState24.flag && !(hudEditorRendererState24.floatValue3 <= 0.0F) && !(hudEditorRendererState24.floatValue4 <= 0.0F)) {
               float floatValue63 = hudEditorRendererState24.clampedSpringAnimation2.getFloatValue6();
               float floatValue64 = hudEditorRendererState24.floatValue3;
               this.invoke14(f, floatValue63, floatValue63);
               this.invoke14(f, floatValue63 + floatValue64, floatValue63 + floatValue64);
               this.invoke14(f, floatValue63 - g, floatValue63);
               this.invoke14(f, floatValue63 + floatValue64 - g, floatValue63 + floatValue64);
               this.invoke14(f, floatValue63 + floatValue64 * 0.5F - g * 0.5F, floatValue63 + floatValue64 * 0.5F);
            }
         }
      }

      if (Float.isFinite(this.floatValue6)) {
         this.floatValue3 = this.floatValue6;
         return this.floatValue5;
      } else {
         return f;
      }
   }

   private float measure4(String string, float f, float g) {
      this.floatValue4 = Float.NaN;
      this.invoke13();
      this.invoke14(f, 0.0F, 0.0F);
      this.invoke14(f, this.intValue2 - g, this.intValue2);
      this.invoke14(f, this.intValue2 * 0.5F - g * 0.5F, this.intValue2 * 0.5F);

      for (Entry entry2 : this.valuesByKey.entrySet()) {
         if (!((String)entry2.getKey()).equals(string)) {
            HudEditorRenderer.HudEditorRendererState2 hudEditorRendererState25 = (HudEditorRenderer.HudEditorRendererState2)entry2.getValue();
            if (hudEditorRendererState25.flag && !(hudEditorRendererState25.floatValue3 <= 0.0F) && !(hudEditorRendererState25.floatValue4 <= 0.0F)) {
               float floatValue65 = hudEditorRendererState25.clampedSpringAnimation3.getFloatValue6();
               float floatValue66 = hudEditorRendererState25.floatValue4;
               this.invoke14(f, floatValue65, floatValue65);
               this.invoke14(f, floatValue65 + floatValue66, floatValue65 + floatValue66);
               this.invoke14(f, floatValue65 - g, floatValue65);
               this.invoke14(f, floatValue65 + floatValue66 - g, floatValue65 + floatValue66);
               this.invoke14(f, floatValue65 + floatValue66 * 0.5F - g * 0.5F, floatValue65 + floatValue66 * 0.5F);
            }
         }
      }

      if (Float.isFinite(this.floatValue6)) {
         this.floatValue4 = this.floatValue6;
         return this.floatValue5;
      } else {
         return f;
      }
   }

   private void invoke13() {
      this.floatValue5 = 0.0F;
      this.floatValue6 = Float.NaN;
      this.floatValue7 = Float.MAX_VALUE;
   }

   private void invoke14(float f, float g, float h) {
      float floatValue67 = Math.abs(f - g);
      if (!(floatValue67 > 5.0F) && !(floatValue67 >= this.floatValue7)) {
         this.floatValue5 = g;
         this.floatValue6 = h;
         this.floatValue7 = floatValue67;
      }
   }

   private void invoke15(RenderManager renderManager3) {
      int intValue13 = ColorUtils.compute43(125, 210, 255, 118);
      int intValue14 = ColorUtils.compute43(125, 210, 255, 32);
      if (Float.isFinite(this.floatValue3)) {
         renderManager3.invoke41(this.floatValue3 - 0.75F, 0.0F, 1.5F, (float)this.intValue2, 1.0F, 9.0F, 2.0F, intValue14);
         renderManager3.invoke5(this.floatValue3 - 0.5F, 0.0F, 1.0F, (float)this.intValue2, 0.5F, intValue13);
      }

      if (Float.isFinite(this.floatValue4)) {
         renderManager3.invoke41(0.0F, this.floatValue4 - 0.75F, (float)this.intValue, 1.5F, 1.0F, 9.0F, 2.0F, intValue14);
         renderManager3.invoke5(0.0F, this.floatValue4 - 0.5F, (float)this.intValue, 1.0F, 0.5F, intValue13);
      }
   }

   private HudEditorRenderer.HudEditorRendererData resolve5(String string) {
      if (check2(string, "hotbar")) {
         return HUD_EDITOR_RENDERER_DATA;
      } else if (check2(string, "watermark")) {
         return HUD_EDITOR_RENDERER_DATA_2;
      } else if (check2(string, "targethud")) {
         return HUD_EDITOR_RENDERER_DATA_3;
      } else if (check2(string, "info")) {
         return HUD_EDITOR_RENDERER_DATA_4;
      } else if (check2(string, "inventory")) {
         return HUD_EDITOR_RENDERER_DATA_5;
      } else if (check2(string, "autobuy")) {
         return HUD_EDITOR_RENDERER_DATA_6;
      } else if (check2(string, "music")) {
         return HUD_EDITOR_RENDERER_DATA_7;
      } else if (check2(string, "arraylist")) {
         return HUD_EDITOR_RENDERER_DATA_8;
      } else if (check2(string, "notifications")) {
         return HUD_EDITOR_RENDERER_DATA_9;
      } else if (check2(string, "potions") || check2(string, "cooldowns")) {
         return HUD_EDITOR_RENDERER_DATA_10;
      } else if (check2(string, "armor") || check2(string, "aistatus")) {
         return HUD_EDITOR_RENDERER_DATA_11;
      } else {
         return !check2(string, "staff") && !check2(string, "party") && !check2(string, "serverhelper") && !check2(string, "hotkeys")
            ? HUD_EDITOR_RENDERER_DATA_13
            : HUD_EDITOR_RENDERER_DATA_12;
      }
   }

   private static boolean check2(String string, String string2) {
      if (string != null && string2 != null && string2.length() <= string.length()) {
         int intValue15 = string.length() - string2.length();

         for (int intValue16 = 0; intValue16 <= intValue15; intValue16++) {
            if (string.regionMatches(true, intValue16, string2, 0, string2.length())) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public float measure5(String string, float f) {
      return this.measure6(string, f, Float.NaN, Float.NaN);
   }

   public float measure6(String string, float f, float g, float h) {
      String text8 = string == null ? "" : string.trim();
      HudEditorRenderer.HudEditorRendererState2 hudEditorRendererState26 = this.valuesByKey.get(text8);
      if (hudEditorRendererState26 == null && string != null) {
         hudEditorRendererState26 = this.valuesByKey.get(string);
      }

      if (hudEditorRendererState26 != null && hudEditorRendererState26.floatValue5 > 0.0F && hudEditorRendererState26.floatValue6 > 0.0F) {
         return this.measure9(text8, hudEditorRendererState26.floatValue5, hudEditorRendererState26.floatValue6, f);
      } else if (Float.isFinite(g) && g > 0.0F && Float.isFinite(h) && h > 0.0F) {
         return this.measure9(text8, g, h, f);
      } else {
         HudEditorRenderer.HudEditorRendererData hudEditorRendererData7 = this.resolve5(string);
         return measure14(measure16(f, 1.0F), hudEditorRendererData7.minScale(), hudEditorRendererData7.maxScale());
      }
   }

   public float measure7(String string) {
      return this.measure8(string, Float.NaN, Float.NaN);
   }

   public float measure8(String string, float f, float g) {
      if (string != null && !string.isBlank()) {
         HudEditorRenderer.HudEditorRendererData2 hudEditorRendererData27 = this.valuesByKey2.get(string);
         Float floatValue68 = hudEditorRendererData27 == null ? this.valuesByKey3.get(string) : null;
         float floatValue69 = floatValue68 == null ? this.measure10(hudEditorRendererData27, hudEditorRendererData27 != null && hudEditorRendererData27.userResized()) : floatValue68;
         return this.measure6(string, floatValue69, f, g);
      } else {
         return 1.0F;
      }
   }

   public HudEditorRenderer.HudEditorRendererData2 resolve6(String string, float f, float g, float h, float i, float j) {
      if (string != null && !string.isBlank() && this.intValue > 0 && this.intValue2 > 0) {
         String text9 = string.trim();
         HudEditorRenderer.HudEditorRendererState2 hudEditorRendererState27 = this.valuesByKey.get(text9);
         if (hudEditorRendererState27 == null) {
            hudEditorRendererState27 = this.valuesByKey.get(string);
         }

         float floatValue70 = this.measure6(text9, f, i, j);
         HudEditorRenderer.HudEditorRendererData2 hudEditorRendererData28 = this.valuesByKey2.get(text9);
         if (hudEditorRendererData28 != null || hudEditorRendererState27 != null && hudEditorRendererState27.flag) {
            this.valuesByKey3.remove(text9);
            float floatValue71;
            float floatValue72;
            if (hudEditorRendererData28 != null) {
               floatValue71 = hudEditorRendererData28.nx() * this.intValue;
               floatValue72 = hudEditorRendererData28.ny() * this.intValue2;
            } else if (hudEditorRendererState27 != null && hudEditorRendererState27.flag) {
               floatValue71 = hudEditorRendererState27.clampedSpringAnimation2.getFloatValue6();
               floatValue72 = hudEditorRendererState27.clampedSpringAnimation3.getFloatValue6();
            } else {
               floatValue71 = measure14(measure16(g, 0.5F), 0.0F, 1.0F) * this.intValue;
               floatValue72 = measure14(measure16(h, 0.5F), 0.0F, 1.0F) * this.intValue2;
            }

            float floatValue73 = hudEditorRendererState27 != null && !(hudEditorRendererState27.floatValue5 <= 0.0F) ? hudEditorRendererState27.floatValue5 : measure15(i);
            float floatValue74 = hudEditorRendererState27 != null && !(hudEditorRendererState27.floatValue6 <= 0.0F) ? hudEditorRendererState27.floatValue6 : measure15(j);
            HudEditorRenderer.HudEditorRendererState4 hudEditorRendererState46 = this.resolve8(text9, floatValue73, floatValue74, floatValue70);
            HudEditorRenderer.HudEditorRendererData hudEditorRendererData8 = this.resolve5(text9);
            floatValue71 = this.measure11(floatValue71, hudEditorRendererState46.getFloatValue(), hudEditorRendererData8);
            floatValue72 = this.measure12(floatValue72, hudEditorRendererState46.getFloatValue2(), hudEditorRendererData8);
            this.invoke12(text9, floatValue71, floatValue72, hudEditorRendererState46.getFloatValue3(), hudEditorRendererState46.getFloatValue3(), true);
            if (hudEditorRendererState27 != null) {
               hudEditorRendererState27.floatValue3 = hudEditorRendererState46.getFloatValue();
               hudEditorRendererState27.floatValue4 = hudEditorRendererState46.getFloatValue2();
               hudEditorRendererState27.clampedSpringAnimation2.invoke2(floatValue71);
               hudEditorRendererState27.clampedSpringAnimation3.invoke2(floatValue72);
            }

            return this.valuesByKey2.get(text9);
         } else {
            this.valuesByKey3.put(text9, floatValue70);
            return new HudEditorRenderer.HudEditorRendererData2(measure14(measure16(g, 0.5F), 0.0F, 1.0F), measure14(measure16(h, 0.5F), 0.0F, 1.0F), floatValue70, floatValue70, true);
         }
      } else {
         return null;
      }
   }

   public HudEditorRenderer.HudEditorRendererData2 resolve7(String string, float f, float g, float h, float i) {
      if (string != null && !string.isBlank() && this.intValue > 0 && this.intValue2 > 0) {
         String text10 = string.trim();
         HudEditorRenderer.HudEditorRendererState2 hudEditorRendererState28 = this.valuesByKey.get(text10);
         if (hudEditorRendererState28 == null) {
            hudEditorRendererState28 = this.valuesByKey.get(string);
         }

         HudEditorRenderer.HudEditorRendererData2 hudEditorRendererData29 = this.valuesByKey2.get(text10);
         boolean flag33 = hudEditorRendererData29 != null && hudEditorRendererData29.userResized() || this.valuesByKey3.containsKey(text10);
         float floatValue75 = this.measure8(text10, h, i);
         this.valuesByKey3.remove(text10);
         float floatValue76 = hudEditorRendererState28 != null && !(hudEditorRendererState28.floatValue5 <= 0.0F) ? hudEditorRendererState28.floatValue5 : measure15(h);
         float floatValue77 = hudEditorRendererState28 != null && !(hudEditorRendererState28.floatValue6 <= 0.0F) ? hudEditorRendererState28.floatValue6 : measure15(i);
         HudEditorRenderer.HudEditorRendererState4 hudEditorRendererState47 = this.resolve8(text10, floatValue76, floatValue77, floatValue75);
         HudEditorRenderer.HudEditorRendererData hudEditorRendererData9 = this.resolve5(text10);
         float floatValue78 = hudEditorRendererData9.padding();
         float floatValue79 = hudEditorRendererData9.padding();
         float floatValue80 = Math.max(floatValue78, this.intValue - hudEditorRendererState47.getFloatValue() - hudEditorRendererData9.padding());
         float floatValue81 = Math.max(floatValue79, this.intValue2 - hudEditorRendererState47.getFloatValue2() - hudEditorRendererData9.padding());
         float floatValue82 = floatValue78 + (floatValue80 - floatValue78) * measure14(measure16(f, 0.5F), 0.0F, 1.0F);
         float floatValue83 = floatValue79 + (floatValue81 - floatValue79) * measure14(measure16(g, 0.5F), 0.0F, 1.0F);
         this.invoke12(text10, floatValue82, floatValue83, hudEditorRendererState47.getFloatValue3(), hudEditorRendererState47.getFloatValue3(), flag33 || Math.abs(hudEditorRendererState47.getFloatValue3() - 1.0F) > 0.01F);
         if (hudEditorRendererState28 != null) {
            hudEditorRendererState28.floatValue3 = hudEditorRendererState47.getFloatValue();
            hudEditorRendererState28.floatValue4 = hudEditorRendererState47.getFloatValue2();
            hudEditorRendererState28.clampedSpringAnimation2.invoke2(floatValue82);
            hudEditorRendererState28.clampedSpringAnimation3.invoke2(floatValue83);
         }

         return this.valuesByKey2.get(text10);
      } else {
         return null;
      }
   }

   private float measure9(String string, float f, float g, float h) {
      HudEditorRenderer.HudEditorRendererData hudEditorRendererData10 = this.resolve5(string);
      float floatValue84 = measure15(f);
      float floatValue85 = measure15(g);
      float floatValue86 = this.intValue > 1 ? Math.max(1.0F, this.intValue * hudEditorRendererData10.maxWidthRatio() - hudEditorRendererData10.padding() * 2.0F) : floatValue84 * hudEditorRendererData10.maxScale();
      float floatValue87 = this.intValue2 > 1 ? Math.max(1.0F, this.intValue2 * hudEditorRendererData10.maxHeightRatio() - hudEditorRendererData10.padding() * 2.0F) : floatValue85 * hudEditorRendererData10.maxScale();
      float floatValue88 = Math.min(hudEditorRendererData10.maxScale(), Math.min(floatValue86 / floatValue84, floatValue87 / floatValue85));
      floatValue88 = Math.max(0.08F, floatValue88);
      float floatValue89 = Math.min(hudEditorRendererData10.minScale(), floatValue88);
      return measure14(measure16(h, 1.0F), floatValue89, floatValue88);
   }

   private HudEditorRenderer.HudEditorRendererState4 resolve8(String string, float f, float g, float h) {
      float floatValue90 = measure15(f);
      float floatValue91 = measure15(g);
      float floatValue92 = this.measure9(string, floatValue90, floatValue91, h);
      return this.hudEditorRendererState4.resolve(floatValue90 * floatValue92, floatValue91 * floatValue92, floatValue92);
   }

   private float measure10(HudEditorRenderer.HudEditorRendererData2 hudEditorRendererData210, boolean bl) {
      if (bl && hudEditorRendererData210 != null) {
         float floatValue93 = measure16(hudEditorRendererData210.scaleX(), 1.0F);
         float floatValue94 = measure16(hudEditorRendererData210.scaleY(), 1.0F);
         return !(floatValue93 <= 0.0F) && !(floatValue94 <= 0.0F) && !(floatValue93 > 12.0F) && !(floatValue94 > 12.0F) ? (float)Math.sqrt(Math.max(1.0E-4F, floatValue93 * floatValue94)) : 1.0F;
      } else {
         return 1.0F;
      }
   }

   private float measure11(float f, float g, HudEditorRenderer.HudEditorRendererData hudEditorRendererData11) {
      if (this.intValue <= 0) {
         return measure16(f, 0.0F);
      } else {
         float floatValue95 = Math.max(0.0F, hudEditorRendererData11 == null ? 6.0F : hudEditorRendererData11.padding());
         float floatValue96 = Math.min(floatValue95, Math.max(0.0F, this.intValue - 1.0F));
         float floatValue97 = Math.max(floatValue96, this.intValue - Math.max(1.0F, g) - floatValue95);
         return measure14(measure16(f, floatValue96), floatValue96, floatValue97);
      }
   }

   private float measure12(float f, float g, HudEditorRenderer.HudEditorRendererData hudEditorRendererData12) {
      if (this.intValue2 <= 0) {
         return measure16(f, 0.0F);
      } else {
         float floatValue98 = Math.max(0.0F, hudEditorRendererData12 == null ? 6.0F : hudEditorRendererData12.padding());
         float floatValue99 = Math.min(floatValue98, Math.max(0.0F, this.intValue2 - 1.0F));
         float floatValue100 = Math.max(floatValue99, this.intValue2 - Math.max(1.0F, g) - floatValue98);
         return measure14(measure16(f, floatValue99), floatValue99, floatValue100);
      }
   }

   private static boolean check3(float f, float g, float h, float i, float j, float k) {
      return Float.isFinite(f)
         && Float.isFinite(g)
         && Float.isFinite(h)
         && Float.isFinite(i)
         && Float.isFinite(j)
         && Float.isFinite(k)
         && j > 0.0F
         && k > 0.0F
         && f >= h
         && f <= h + j
         && g >= i
         && g <= i + k;
   }

   private static float measure13(float f, float g) {
      return Math.max(0.0F, Math.min(f, g));
   }

   private static float measure14(float f, float g, float h) {
      return h < g ? g : Math.max(g, Math.min(f, h));
   }

   private static float measure15(float f) {
      return Float.isFinite(f) && f > 1.0F ? f : 1.0F;
   }

   private static float measure16(float f, float g) {
      return Float.isFinite(f) ? f : g;
   }

   public Map<String, HudEditorRenderer.HudEditorRendererData2> getValuesByKey2() {
      return this.valuesByKey2;
   }

   public Map<String, Float> getValuesByKey3() {
      return this.valuesByKey3;
   }

   public void invoke16(Map<String, HudEditorRenderer.HudEditorRendererData2> map) {
      this.valuesByKey2.clear();
      this.valuesByKey3.clear();
      if (map != null) {
         this.valuesByKey2.putAll(map);
      }

      this.valuesByKey.clear();
      this.text = null;
      this.flag12 = false;
   }

   public void invoke17(Map<String, Float> map) {
      this.valuesByKey3.clear();
      if (map != null) {
         for (Entry entry3 : map.entrySet()) {
            String text11 = (String)entry3.getKey();
            Float floatValue101 = (Float)entry3.getValue();
            if (text11 != null && !text11.isBlank() && floatValue101 != null && Float.isFinite(floatValue101) && !(floatValue101 <= 0.0F) && !this.valuesByKey2.containsKey(text11)) {
               this.valuesByKey3.put(text11.trim(), this.measure5(text11, floatValue101));
            }
         }
      }
   }

   public void invoke18(String string) {
      if (string != null && !string.isBlank()) {
         String text12 = string.trim();
         this.valuesByKey2.remove(text12);
         this.valuesByKey3.remove(text12);
         HudEditorRenderer.HudEditorRendererState2 hudEditorRendererState29 = this.valuesByKey.remove(text12);
         if (hudEditorRendererState29 == null) {
            hudEditorRendererState29 = this.valuesByKey.remove(string);
         }

         if (text12.equals(this.text) || string.equals(this.text)) {
            this.text = null;
         }
      }
   }

   @Generated
   public float getFloatValue() {
      return this.floatValue;
   }

   @Generated
   public float getFloatValue2() {
      return this.floatValue2;
   }

   @Generated
   public boolean isFlag3() {
      return this.flag3;
   }

   @Generated
   public boolean isFlag4() {
      return this.flag4;
   }

   @Generated
   public String getText() {
      return this.text;
   }

   record HudEditorRendererData(float minScale, float maxScale, float maxWidthRatio, float maxHeightRatio, float padding) {
   }

   public static final class HudEditorRendererState {
      public String text;
      public float floatValue;
      public float floatValue2;
      public float floatValue3;
      public float floatValue4;
      public float floatValue5;
      public float floatValue6;
      public boolean flag;
      public boolean flag2;
      public boolean flag3;
      public boolean flag4;

      HudEditorRendererState() {
      }

      HudEditorRenderer.HudEditorRendererState resolve(String string, float f, float g, float h, float i, boolean bl, boolean bl2, boolean bl3, boolean bl4, float j, float k) {
         this.text = string;
         this.floatValue = f;
         this.floatValue2 = g;
         this.floatValue3 = h;
         this.floatValue4 = i;
         this.flag = bl;
         this.flag2 = bl2;
         this.flag3 = bl3;
         this.flag4 = bl4;
         this.floatValue5 = j;
         this.floatValue6 = k;
         return this;
      }
   }

   static final class HudEditorRendererState2 {
      boolean flag;
      boolean flag2;
      boolean flag3;
      boolean flag4;
      float floatValue;
      float floatValue2;
      float floatValue3;
      float floatValue4;
      float floatValue5;
      float floatValue6;
      final HudEditorRenderer.HudEditorRendererState hudEditorRendererState = new HudEditorRenderer.HudEditorRendererState();
      final ClampedSpringAnimation clampedSpringAnimation = new ClampedSpringAnimation(AnimationSystem.getINSTANCE(), HudEditorRenderer.SPRING_CONFIG_2, 1.0F, 0.0F, 1.0F, 0.001F, 0.001F);
      final ClampedSpringAnimation clampedSpringAnimation2 = new ClampedSpringAnimation(AnimationSystem.getINSTANCE(), HudEditorRenderer.SPRING_CONFIG, 0.0F, -9999.0F, 9999.0F, 0.1F, 0.1F);
      final ClampedSpringAnimation clampedSpringAnimation3 = new ClampedSpringAnimation(AnimationSystem.getINSTANCE(), HudEditorRenderer.SPRING_CONFIG, 0.0F, -9999.0F, 9999.0F, 0.1F, 0.1F);
      final ClampedSpringAnimation clampedSpringAnimation4 = new ClampedSpringAnimation(AnimationSystem.getINSTANCE(), HudEditorRenderer.SPRING_CONFIG_3, 0.0F, 0.0F, 1.0F, 0.01F, 0.01F);
      final ClampedSpringAnimation clampedSpringAnimation5 = new ClampedSpringAnimation(AnimationSystem.getINSTANCE(), HudEditorRenderer.SPRING_CONFIG_4, 0.0F, 0.0F, 1.0F, 0.01F, 0.01F);
   }

   @HudElementInfo(
      resolve = "GlobalHUD",
      resolve2 = "w"
   )
   public static class HudEditorRendererVariant extends ConfigurableHudElement {
      public final NumberSetting prozrachnost = new NumberSetting("Прозрачность", 1.0F, 0.1F, 1.0F, 0.05F, true);
      public final NumberSetting prozrachnostTyomnyhElementov = new NumberSetting("Прозрачность тёмных элементов", 1.0F, 0.0F, 1.0F, 0.05F, true);
      public final ModeSetting stilistika = new ModeSetting("Стилистика", "Тёмный", "Тёмный", "Светлый", "Блюр", "Неоморфизм", "Феррофлюид", "Призма");
      public final NumberSetting neoDistantsiya = new NumberSetting("Нео дистанция", 5.5F, 2.0F, 18.0F, 0.5F, false)
         .setVisibilityCondition(() -> !HudElement.check11(this.stilistika.getValue()));
      public final NumberSetting neoRazmytie = new NumberSetting("Нео размытие", 18.0F, 6.0F, 48.0F, 1.0F, false)
         .setVisibilityCondition(() -> !HudElement.check11(this.stilistika.getValue()));
      public final NumberSetting neoIntensivnost = new NumberSetting("Нео интенсивность", 0.72F, 0.1F, 1.0F, 0.05F, true)
         .setVisibilityCondition(() -> !HudElement.check11(this.stilistika.getValue()));
      public final ModeSetting neoForma = new ModeSetting("Нео форма", "Выпуклая", "Плоская", "Выпуклая", "Вогнутая")
         .setVisibilityCondition(() -> !HudElement.check11(this.stilistika.getValue()));
      public final GroupSetting vizual = new GroupSetting(
         "Визуал",
         new BooleanSetting("Тень", true),
         new BooleanSetting("Обводка", true),
         new BooleanSetting("Темные зоны", true),
         new BooleanSetting("Верхняя накладка", true),
         new BooleanSetting("Нижняя накладка", true),
         new BooleanSetting("Тёмный рект поверх", true)
      );

      public HudEditorRendererVariant() {
         this.invoke(this.prozrachnost);
         this.invoke(this.prozrachnostTyomnyhElementov);
         this.invoke(this.stilistika);
         this.invoke(this.neoDistantsiya);
         this.invoke(this.neoRazmytie);
         this.invoke(this.neoIntensivnost);
         this.invoke(this.neoForma);
         this.invoke(this.vizual);
         ru.metaculture.protection.HudPresetManager.invoke2(this);
      }
   }

   public static final class HudEditorRendererState3 {
      public String text = "";
      public float floatValue;
      public float floatValue2;
      public float floatValue3;
      public float floatValue4;
      public float floatValue5;
      public float floatValue6;

      public void invoke(String string, float f, float g, float h, float i, float j, float k) {
         this.text = string == null ? "" : string;
         this.floatValue = f;
         this.floatValue2 = g;
         this.floatValue3 = h;
         this.floatValue4 = i;
         this.floatValue5 = j;
         this.floatValue6 = k;
      }

      void invoke2(HudEditorRenderer.HudEditorRendererState3 hudEditorRendererState32) {
         if (hudEditorRendererState32 == null) {
            this.invoke("", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
         } else {
            this.invoke(
               hudEditorRendererState32.text,
               hudEditorRendererState32.floatValue,
               hudEditorRendererState32.floatValue2,
               hudEditorRendererState32.floatValue3,
               hudEditorRendererState32.floatValue4,
               hudEditorRendererState32.floatValue5,
               hudEditorRendererState32.floatValue6
            );
         }
      }
   }

   public record HudEditorRendererData2(float nx, float ny, float scaleX, float scaleY, boolean userResized) {
   }

   static final class HudEditorRendererState4 {
      private float floatValue;
      private float floatValue2;
      private float floatValue3;

      HudEditorRenderer.HudEditorRendererState4 resolve(float f, float g, float h) {
         this.floatValue = f;
         this.floatValue2 = g;
         this.floatValue3 = h;
         return this;
      }

      float getFloatValue() {
         return this.floatValue;
      }

      float getFloatValue2() {
         return this.floatValue2;
      }

      float getFloatValue3() {
         return this.floatValue3;
      }
   }
}
