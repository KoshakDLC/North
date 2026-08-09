package ru.metaculture.protection;

import java.awt.Color;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.client.util.math.MatrixStack;
import org.wild.module.api.Module;

public class LegacySearchOverlay extends LegacyClickGuiState {
   private static final char[] CHARS = new char[65535];

   public static void invoke(RenderManager renderManager, MatrixStack matrixStack, int i, int j, float f) {
      if (LegacyClickGuiState.flag4) {
         InputUtils.getINSTANCE().invoke("Search");
      } else {
         InputUtils.getINSTANCE().invoke2("Search");
      }

      if (LegacyClickGuiState.flag4) {
         boolean flag = KeyCodeUtils.check(259);
         long longValue = System.currentTimeMillis();
         if (flag) {
            if (!LegacyClickGuiState.flag5) {
               LegacyClickGuiState.flag5 = true;
               LegacyClickGuiState.timestamp2 = longValue;
               LegacyClickGuiState.timestamp = longValue;
               if (!LegacyClickGuiState.text.isEmpty()) {
                  LegacyClickGuiState.text = LegacyClickGuiState.text.substring(0, LegacyClickGuiState.text.length() - 1);
               }
            } else if (longValue - LegacyClickGuiState.timestamp2 > 500L && longValue - LegacyClickGuiState.timestamp > 30L) {
               if (!LegacyClickGuiState.text.isEmpty()) {
                  LegacyClickGuiState.text = LegacyClickGuiState.text.substring(0, LegacyClickGuiState.text.length() - 1);
               }

               LegacyClickGuiState.timestamp = longValue;
            }
         } else {
            LegacyClickGuiState.flag5 = false;
            LegacyClickGuiState.timestamp2 = 0L;
         }
      } else {
         LegacyClickGuiState.flag5 = false;
         LegacyClickGuiState.timestamp2 = 0L;
      }

      int intValue = RenderManager.RenderManagerState.compute24(RenderManager.RenderManagerState.compute5(1, 1), (int)(20.4F * f));
      int intValue2 = RenderManager.RenderManagerState.compute24(RenderManager.RenderManagerState.compute6(1, 1), (int)(10.2F * f));
      int intValue3 = RenderManager.RenderManagerState.compute24(RenderManager.RenderManagerState.compute6(1, 1), (int)(255.0F * f));
      int intValue4 = RenderManager.RenderManagerState.compute24(RenderManager.RenderManagerState.compute6(1, 1), (int)(15.3F * f));
      int intValue5 = RenderManager.RenderManagerState.compute24(RenderManager.RenderManagerState.compute6(1, 1), (int)(102.0F * f));
      int intValue6 = RenderManager.RenderManagerState.compute24(RenderManager.RenderManagerState.compute7(1, 1), (int)(255.0F * f));
      int intValue7 = RenderManager.RenderManagerState.compute24(RenderManager.RenderManagerState.compute3(1, 1), (int)(178.5F * f));
      Color color = RenderManager.RenderManagerState.resolve15(RenderManager.RenderManagerState.compute24(RenderManager.RenderManagerState.compute6(1, 1), (int)(56.0F * f)));
      float floatValue = LegacyClickGuiState.floatValue6 + 104.735F;
      float floatValue2 = LegacyClickGuiState.floatValue7 + 34.025F;
      float floatValue3 = 261.5F;
      float floatValue4 = 209.5F;
      float floatValue5 = floatValue + 5.0F;
      float floatValue6 = floatValue2 + 5.0F;
      float floatValue7 = floatValue3 - 10.0F;
      float floatValue8 = floatValue4 - 10.0F;
      renderManager.invoke24(floatValue5, floatValue6, floatValue7, floatValue8, 0.0F, 0.0F, 0.0F, 0.0F);
      List items = LegacyClickGuiState.items;
      if (LegacyClickGuiState.flag4 && !LegacyClickGuiState.text.isEmpty()) {
         String text = LegacyClickGuiState.text.toLowerCase().trim();
         String text2 = resolve(text);
         items = LegacyClickGuiState.items.stream().filter(module -> {
            String text3 = module.name.toLowerCase();
            return text3.contains(text) || !text2.equals(text) && text3.contains(text2);
         }).collect(Collectors.toList());
      }

      float floatValue9 = 0.0F;
      float floatValue10 = 0.0F;
      float floatValue11 = 0.0F;
      float floatValue12 = 0.0F;
      float floatValue13 = 0.0F;
      int intValue8 = 1;

      for (Module module2 : (List<Module>)items) {
         module2.lifecycleAnimation.check();
         module2.lifecycleAnimation.resolve2(module2.enabled ? 1.0 : 0.0, 0.15F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_15);
         module2.enableAnimation.invoke3(module2.enabled ? AnimationDirection.FORWARDS : AnimationDirection.BACKWARDS);
         float floatValue14 = module2.lifecycleAnimation.measure3();
         float floatValue15 = 12.0F;
         float floatValue16 = 12.0F;
         float floatValue17 = LegacyClickGuiState.resolve2(module2).measure3();
         float floatValue18 = LegacyClickGuiState.resolve3(module2).measure3();
         if (LegacyClickGuiState.values.contains(module2) || floatValue17 > 0.0F || floatValue18 > 0.0F) {
            for (Setting setting : module2.getVisibleSettings()) {
               floatValue16 += LegacySettingLayout.measure(renderManager, setting);
            }

            floatValue16 = Math.max(floatValue16, 20.0F);
            floatValue15 = 12.0F + (floatValue16 - 12.0F) * floatValue17;
         }

         if (intValue8 % 2 == 0) {
            float floatValue19 = floatValue9 + floatValue11 - 30.0F;
            float floatValue20 = 21.325F;
            floatValue20 += floatValue15;
            float floatValue21 = floatValue19 + floatValue20;
            floatValue13 = Math.max(floatValue13, floatValue21);
            floatValue11 += floatValue15;
         } else {
            float floatValue22 = floatValue9 + floatValue10;
            float floatValue23 = 21.325F;
            floatValue23 += floatValue15;
            float floatValue24 = floatValue22 + floatValue23;
            floatValue12 = Math.max(floatValue12, floatValue24);
            floatValue10 += floatValue15;
            floatValue9 += 30.325F;
         }

         intValue8++;
      }

      float floatValue25 = Math.max(floatValue12, floatValue13);
      float floatValue26 = floatValue25 + 150.0F;
      float floatValue27 = LegacyClickGuiState.floatValue6 + 104.735F;
      float floatValue28 = LegacyClickGuiState.floatValue7 + 34.025F;
      boolean flag2 = check(i, j, floatValue27 + 5.0F, floatValue28 + 5.0F, floatValue3 - 10.0F, floatValue4 - 10.0F);
      LegacyClickGuiState.resolve().setFloatValue4(6.0F);
      LegacyClickGuiState.resolve().setFlag(flag2);
      LegacyClickGuiState.resolve().invoke();
      LegacyClickGuiState.resolve().invoke7(RenderMath.measure49(floatValue26, 260.0F, 9999.0F), floatValue4 - 10.0F);
      float floatValue29 = -0.35F;
      float floatValue30 = -0.7F;
      int intValue9 = 1;
      float floatValue31 = LegacyClickGuiState.resolve().measure();
      float floatValue32 = 0.0F;
      float floatValue33 = 0.0F;

      for (Module module3 : (List<Module>)items) {
         if (intValue9 % 2 == 0) {
            float floatValue34 = module3.lifecycleAnimation.measure3();
            float floatValue35 = floatValue31 + floatValue33 - 30.0F;
            float floatValue36 = 12.0F;
            float floatValue37 = 12.0F;
            float floatValue38 = LegacyClickGuiState.resolve2(module3).measure3();
            float floatValue39 = LegacyClickGuiState.resolve3(module3).measure3();
            if (LegacyClickGuiState.values.contains(module3) || floatValue38 > 0.0F) {
               for (Setting setting2 : module3.getVisibleSettings()) {
                  floatValue37 += LegacySettingLayout.measure(renderManager, setting2) + 0.5F;
               }

               floatValue37 = Math.max(floatValue37, 20.0F);
               floatValue36 = 12.0F * floatValue38 + (floatValue37 - 12.0F) * floatValue38;
            }

            if (!(floatValue38 > 0.0F) && !(floatValue39 > 0.0F)) {
               renderManager.invoke28(
                  LegacyClickGuiState.floatValue6 + 238.35F, LegacyClickGuiState.floatValue7 + 43.365F + floatValue35, 121.47F, 21.325F, 6.5F, intValue, 0.1F
               );
               renderManager.invoke5(LegacyClickGuiState.floatValue6 + 238.35F, LegacyClickGuiState.floatValue7 + 43.365F + floatValue35, 121.47F, 21.325F, 6.5F, intValue2);
            } else {
               renderManager.invoke28(
                  LegacyClickGuiState.floatValue6 + 238.35F, LegacyClickGuiState.floatValue7 + 43.365F + floatValue35, 121.47F, 21.325F + floatValue36, 6.5F, intValue, 0.1F
               );
               renderManager.invoke5(
                  LegacyClickGuiState.floatValue6 + 238.35F, LegacyClickGuiState.floatValue7 + 43.365F + floatValue35, 121.47F, 21.325F + floatValue36, 6.5F, intValue2
               );
               if (floatValue39 > 0.01F) {
                  renderManager.invoke4(
                     LegacyClickGuiState.floatValue6 + 238.515F,
                     LegacyClickGuiState.floatValue7 + 64.69F + floatValue35,
                     121.47F,
                     1.0F,
                     ColorUtils.compute31(intValue, floatValue39)
                  );
               }
            }

            float floatValue40 = LegacyClickGuiState.floatValue6 + 247.895F;
            float floatValue41 = LegacyClickGuiState.floatValue7 + 49.555F + floatValue35;
            renderManager.invoke69(FontRegistry.fontObject, floatValue40, floatValue41 + 6.6F, 14.0F, module3.name, ColorUtils.compute35(intValue5, intValue6, floatValue34));
            float floatValue42 = LegacyClickGuiState.resolve4(module3).measure3();
            if (module3.expanded || module3.bindKey != -1 || floatValue42 > 0.0F) {
               float floatValue43 = 10.0F;
               String text4 = module3.expanded ? "..." : (module3.bindKey != -1 ? KeyboardKey.resolve(module3.bindKey) : "");
               float floatValue44 = text4.isEmpty() ? 0.0F : RenderManager.resolve7(FontRegistry.fontObject, text4, 12.0F).floatValue;
               float floatValue45 = 6.0F;
               float floatValue46 = Math.max(floatValue45, floatValue44 + 6.0F);
               float floatValue47 = RenderManager.resolve7(FontRegistry.fontObject, module3.name, 14.0F).floatValue;
               float floatValue48 = floatValue40 + floatValue47 + 4.0F;
               float floatValue49 = floatValue41 - 0.35F;
               renderManager.invoke28(floatValue48, floatValue49, floatValue46, floatValue43, 3.0F, ColorUtils.compute31(intValue, floatValue42), 0.1F);
               renderManager.invoke5(floatValue48, floatValue49, floatValue46, floatValue43, 3.0F, ColorUtils.compute31(intValue4, floatValue42));
               if (!text4.isEmpty()) {
                  renderManager.invoke69(
                     FontRegistry.fontObject,
                     floatValue48 + floatValue46 / 2.0F - floatValue44 / 2.0F - 0.2F,
                     floatValue49 + 2.0F + 5.25F,
                     12.0F,
                     text4,
                     ColorUtils.compute31(module3.expanded ? intValue3 : intValue5, floatValue42)
                  );
               }
            }

            renderManager.invoke28(
               LegacyClickGuiState.floatValue6 + 348.415F - 1.5F, LegacyClickGuiState.floatValue7 + 52.505F + floatValue35 - 1.5F + floatValue29, 6.0F, 6.0F, 3.0F, intValue, 0.08F
            );
            renderManager.invoke5(
               LegacyClickGuiState.floatValue6 + 348.415F - 1.5F, LegacyClickGuiState.floatValue7 + 52.505F + floatValue35 - 1.5F + floatValue29, 6.0F, 6.0F, 3.0F, intValue4
            );
            renderManager.invoke5(
               LegacyClickGuiState.floatValue6 + 349.27F - 0.75F,
               LegacyClickGuiState.floatValue7 + 53.365F + floatValue35 - 0.78F + floatValue29,
               3.0F,
               3.0F,
               1.5F,
               ColorUtils.compute35(intValue5, intValue3, floatValue34)
            );
            renderManager.invoke41(
               LegacyClickGuiState.floatValue6 + 349.27F + 0.7F,
               LegacyClickGuiState.floatValue7 + 53.365F + floatValue35 + floatValue29,
               0.1F,
               0.1F,
               1.5F,
               2.575F,
               0.1F,
               ColorUtils.compute35(0, color.getRGB(), floatValue34)
            );
            if (!module3.getVisibleSettings().isEmpty()) {
               renderManager.invoke69(
                  FontRegistry.fontObject3,
                  LegacyClickGuiState.floatValue6 + 337.975F,
                  LegacyClickGuiState.floatValue7 + 52.81F + floatValue35 - 1.5F + floatValue30 + 6.5F + 6.0F - 6.0F * floatValue38,
                  11.0F,
                  "S",
                  ColorUtils.compute35(0, intValue3, floatValue38)
               );
               renderManager.invoke69(
                  FontRegistry.fontObject3,
                  LegacyClickGuiState.floatValue6 + 337.975F,
                  LegacyClickGuiState.floatValue7 + 52.81F + floatValue35 - 1.5F + floatValue30 + 6.5F + 6.0F * floatValue38,
                  11.0F,
                  "R",
                  ColorUtils.compute35(intValue5, 0, floatValue38)
               );
            }

            if (floatValue38 > 0.0F || floatValue39 > 0.0F) {
               float floatValue50 = LegacyClickGuiState.floatValue7 + 64.69F + floatValue35 + 4.0F;
               float floatValue51 = LegacyClickGuiState.floatValue6 + 238.35F + 9.0F;
               float floatValue52 = 105.47F;
               float floatValue53 = 0.0F;

               for (Setting setting3 : module3.getVisibleSettings()) {
                  floatValue53 += LegacySettingLayout.measure2(
                        renderManager,
                        setting3,
                        floatValue51,
                        floatValue50 + floatValue53,
                        floatValue52,
                        i,
                        j,
                        ColorUtils.compute31(intValue, floatValue39),
                        ColorUtils.compute31(intValue3, floatValue39),
                        ColorUtils.compute31(intValue4, floatValue39),
                        ColorUtils.compute31(intValue5, floatValue39),
                        ColorUtils.compute31(intValue6, floatValue39),
                        f * floatValue39
                     )
                     * floatValue39;
               }

               floatValue33 += floatValue36;
            }
         } else {
            float floatValue54 = module3.lifecycleAnimation.measure3();
            float floatValue55 = floatValue31 + floatValue32;
            float floatValue56 = 12.0F;
            float floatValue57 = 12.0F;
            float floatValue58 = LegacyClickGuiState.resolve2(module3).measure3();
            float floatValue59 = LegacyClickGuiState.resolve3(module3).measure3();
            if (LegacyClickGuiState.values.contains(module3) || floatValue58 > 0.0F) {
               for (Setting setting4 : module3.getVisibleSettings()) {
                  floatValue57 += LegacySettingLayout.measure(renderManager, setting4) + 0.5F;
               }

               floatValue57 = Math.max(floatValue57, 20.0F);
               floatValue56 = 12.0F * floatValue58 + (floatValue57 - 12.0F) * floatValue58;
            }

            if (!(floatValue58 > 0.0F) && !(floatValue59 > 0.0F)) {
               renderManager.invoke28(
                  LegacyClickGuiState.floatValue6 + 111.885F, LegacyClickGuiState.floatValue7 + 43.365F + floatValue55, 121.47F, 21.325F, 6.5F, intValue, 0.1F
               );
               renderManager.invoke5(LegacyClickGuiState.floatValue6 + 111.885F, LegacyClickGuiState.floatValue7 + 43.365F + floatValue55, 121.47F, 21.325F, 6.5F, intValue2);
            } else {
               renderManager.invoke28(
                  LegacyClickGuiState.floatValue6 + 111.885F, LegacyClickGuiState.floatValue7 + 43.365F + floatValue55, 121.47F, 21.325F + floatValue56, 6.5F, intValue, 0.1F
               );
               renderManager.invoke5(
                  LegacyClickGuiState.floatValue6 + 111.885F, LegacyClickGuiState.floatValue7 + 43.365F + floatValue55, 121.47F, 21.325F + floatValue56, 6.5F, intValue2
               );
               if (floatValue59 > 0.01F) {
                  renderManager.invoke4(
                     LegacyClickGuiState.floatValue6 + 111.885F,
                     LegacyClickGuiState.floatValue7 + 64.69F + floatValue55,
                     121.47F,
                     1.0F,
                     ColorUtils.compute31(intValue, floatValue59)
                  );
               }
            }

            float floatValue60 = LegacyClickGuiState.floatValue6 + 121.425F;
            float floatValue61 = LegacyClickGuiState.floatValue7 + 49.555F + floatValue55;
            renderManager.invoke69(FontRegistry.fontObject, floatValue60, floatValue61 + 6.6F, 14.0F, module3.name, ColorUtils.compute35(intValue5, intValue6, floatValue54));
            float floatValue62 = LegacyClickGuiState.resolve4(module3).measure3();
            if (module3.expanded || module3.bindKey != -1 || floatValue62 > 0.0F) {
               float floatValue63 = 10.0F;
               String text5 = module3.expanded ? "..." : (module3.bindKey != -1 ? KeyboardKey.resolve(module3.bindKey) : "");
               float floatValue64 = text5.isEmpty() ? 0.0F : RenderManager.resolve7(FontRegistry.fontObject, text5, 12.0F).floatValue;
               float floatValue65 = 6.0F;
               float floatValue66 = Math.max(floatValue65, floatValue64 + 6.0F);
               float floatValue67 = RenderManager.resolve7(FontRegistry.fontObject, module3.name, 14.0F).floatValue;
               float floatValue68 = floatValue60 + floatValue67 + 4.0F;
               float floatValue69 = floatValue61 - 0.35F;
               renderManager.invoke28(floatValue68, floatValue69, floatValue66, floatValue63, 3.0F, ColorUtils.compute31(intValue, floatValue62), 0.1F);
               renderManager.invoke5(floatValue68, floatValue69, floatValue66, floatValue63, 3.0F, ColorUtils.compute31(intValue4, floatValue62));
               if (!text5.isEmpty()) {
                  renderManager.invoke69(
                     FontRegistry.fontObject,
                     floatValue68 + floatValue66 / 2.0F - floatValue64 / 2.0F - 0.2F,
                     floatValue69 + 2.0F + 5.25F,
                     12.0F,
                     text5,
                     ColorUtils.compute31(module3.expanded ? intValue3 : intValue5, floatValue62)
                  );
               }
            }

            renderManager.invoke28(
               LegacyClickGuiState.floatValue6 + 221.875F - 1.5F, LegacyClickGuiState.floatValue7 + 52.505F + floatValue55 - 1.5F + floatValue29, 6.0F, 6.0F, 3.0F, intValue, 0.08F
            );
            renderManager.invoke5(
               LegacyClickGuiState.floatValue6 + 221.875F - 1.5F, LegacyClickGuiState.floatValue7 + 52.505F + floatValue55 - 1.5F + floatValue29, 6.0F, 6.0F, 3.0F, intValue4
            );
            renderManager.invoke5(
               LegacyClickGuiState.floatValue6 + 222.735F - 0.75F,
               LegacyClickGuiState.floatValue7 + 53.365F + floatValue55 - 0.78F + floatValue29,
               3.0F,
               3.0F,
               1.5F,
               ColorUtils.compute35(intValue5, intValue3, floatValue54)
            );
            renderManager.invoke41(
               LegacyClickGuiState.floatValue6 + 222.735F + 0.7F,
               LegacyClickGuiState.floatValue7 + 53.365F + floatValue55 + floatValue29,
               0.1F,
               0.1F,
               1.5F,
               2.575F,
               0.1F,
               ColorUtils.compute35(0, color.getRGB(), floatValue54)
            );
            if (!module3.getVisibleSettings().isEmpty() && !module3.getVisibleSettings().isEmpty()) {
               renderManager.invoke69(
                  FontRegistry.fontObject3,
                  LegacyClickGuiState.floatValue6 + 211.48F,
                  LegacyClickGuiState.floatValue7 + 52.81F + floatValue55 - 1.5F + floatValue30 + 6.5F + 6.0F - 6.0F * floatValue58,
                  11.0F,
                  "S",
                  ColorUtils.compute35(0, intValue3, floatValue58)
               );
               renderManager.invoke69(
                  FontRegistry.fontObject3,
                  LegacyClickGuiState.floatValue6 + 211.48F,
                  LegacyClickGuiState.floatValue7 + 52.81F + floatValue55 - 1.5F + floatValue30 + 6.5F + 6.0F * floatValue58,
                  11.0F,
                  "R",
                  ColorUtils.compute35(intValue5, 0, floatValue58)
               );
            }

            if (floatValue58 > 0.0F || floatValue59 > 0.0F) {
               float floatValue70 = LegacyClickGuiState.floatValue7 + 64.69F + floatValue55 + 4.0F;
               float floatValue71 = LegacyClickGuiState.floatValue6 + 111.885F + 9.0F;
               float floatValue72 = 105.47F;
               float floatValue73 = 0.0F;

               for (Setting setting5 : module3.getVisibleSettings()) {
                  floatValue73 += LegacySettingLayout.measure2(
                        renderManager,
                        setting5,
                        floatValue71,
                        floatValue70 + floatValue73,
                        floatValue72,
                        i,
                        j,
                        ColorUtils.compute31(intValue, floatValue59),
                        ColorUtils.compute31(intValue3, floatValue59),
                        ColorUtils.compute31(intValue4, floatValue59),
                        ColorUtils.compute31(intValue5, floatValue59),
                        ColorUtils.compute31(intValue6, floatValue59),
                        f * floatValue59
                     )
                     * floatValue59;
               }

               floatValue32 += floatValue56;
            }

            floatValue31 += 30.325F;
         }

         intValue9++;
      }

      renderManager.invoke25();
      LegacyClickGuiState.resolve()
         .invoke8(
            renderManager, LegacyClickGuiState.floatValue6 + 104.735F + 261.5F - 5.0F + 1.0F, LegacyClickGuiState.floatValue7 + 34.025F + 5.0F, 2.0F, 194.5F, f
         );
      if (LegacyClickGuiState.colorSetting != null && LegacyClickGuiState.colorSetting instanceof ColorSetting) {
         LegacyColorPickerRenderer.invoke(
            renderManager,
            LegacyClickGuiState.colorSetting,
            i,
            j,
            ColorUtils.compute31(intValue, LegacyClickGuiState.directionalAnimation5.measure3()),
            ColorUtils.compute31(intValue7, LegacyClickGuiState.directionalAnimation5.measure3()),
            ColorUtils.compute31(intValue5, LegacyClickGuiState.directionalAnimation5.measure3()),
            f * LegacyClickGuiState.directionalAnimation5.measure3()
         );
      }
   }

   private static String resolve(String string) {
      StringBuilder stringBuilder = new StringBuilder(string.length());

      for (int intValue10 = 0; intValue10 < string.length(); intValue10++) {
         char character = string.charAt(intValue10);
         stringBuilder.append(character < CHARS.length && CHARS[character] != 0 ? CHARS[character] : character);
      }

      return stringBuilder.toString();
   }

   public static boolean check(float f, float g, float h, float i, float j, float k) {
      return f >= h && g >= i && f < h + j && g < i + k;
   }

   static {
      String text6 = "йцукенгшщзхъфывапролджэячсмитьбю";
      String text7 = "qwertyuiop[]asdfghjkl;'zxcvbnm,.";

      for (int intValue11 = 0; intValue11 < text6.length(); intValue11++) {
         CHARS[text6.charAt(intValue11)] = text7.charAt(intValue11);
      }
   }
}
