package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.screen.ChatScreen;
import org.wild.module.api.Module;

@HudElementInfo(
   resolve = "KeyBindHUD",
   resolve2 = "q"
)
public final class KeybindHud extends HudElement {
   private static final KeybindHud INSTANCE = new KeybindHud();
   private static final List<KeybindHud.KeybindHudState> ITEMS = new ArrayList<>(64);
   private static final Map<Module, KeybindHud.KeybindHudState> VALUES_BY_KEY = new IdentityHashMap<>(128);
   private static final Map<BooleanSetting, KeybindHud.KeybindHudState> VALUES_BY_KEY_2 = new IdentityHashMap<>(64);
   private static final Animation ANIMATION = new Animation();
   private static final Animation ANIMATION_2 = new Animation();
   private static final Animation ANIMATION_3 = new Animation();
   private static final Map<String, Animation> VALUES_BY_KEY_3 = new HashMap<>();
   private static final Map<BooleanSetting, Animation> VALUES_BY_KEY_4 = new IdentityHashMap<>(64);
   private static final List<Module> ITEMS_2 = new ArrayList<>(64);
   private static final List<BooleanSetting> ITEMS_3 = new ArrayList<>(8);
   private static final float FLOAT_VALUE = 12.0F;
   private static final float FLOAT_VALUE_2 = 0.94F;
   private static final float FLOAT_VALUE_3 = 0.78F;
   private static final float FLOAT_VALUE_4 = 3.4F;
   private static final float FLOAT_VALUE_5 = 3.5F;
   private static final float FLOAT_VALUE_6 = 1.1F;
   private final BooleanSetting otobrazhatIkonki = new BooleanSetting("Отображать иконки", true);

   private KeybindHud() {
      this.invoke((Setting)this.otobrazhatIkonki);
      ru.metaculture.protection.HudPresetManager.invoke2(this);
   }

   public static void invoke(RenderManager renderManager) {
      INSTANCE.invoke2(renderManager);
   }

   public static KeybindHud getINSTANCE() {
      return INSTANCE;
   }

   public void invoke2(RenderManager renderManager2) {
      if (MinecraftAccessor.a_.player != null && WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null) {
         boolean flag = this.otobrazhatIkonki.isEnabled();
         boolean flag2 = HudModule.check2();
         HudLayoutManager.HudLayoutManagerState hudLayoutManagerState = flag2 ? HudLayoutManager.resolve() : null;
         float floatValue = 22.0F;
         float floatValue2 = flag2 ? hudLayoutManagerState.floatValue8 : 7.0F;
         float floatValue3 = flag2 ? hudLayoutManagerState.floatValue10 : 32.0F;
         float floatValue4 = flag2 ? hudLayoutManagerState.floatValue11 : 22.0F;
         float floatValue5 = flag2 ? hudLayoutManagerState.floatValue9 : 5.0F;
         float floatValue6 = flag2 ? hudLayoutManagerState.floatValue12 : 28.0F;
         float floatValue7 = flag2 ? hudLayoutManagerState.floatValue13 : floatValue;
         float floatValue8 = flag2 ? hudLayoutManagerState.floatValue15 : 1.9F;
         ITEMS.clear();
         ITEMS_2.clear();

         for (Module module3 : WildClient.INSTANCE.moduleManager.getModules()) {
            if (!"Menu".equals(module3.name) && module3.bindKey != -1) {
               Animation animation = VALUES_BY_KEY_3.computeIfAbsent(module3.name, string -> new Animation());
               animation.check();
               animation.resolve4(module3.enabled ? 1.0 : 0.0, 0.2F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
               if (animation.measure3() > 0.001F || module3.enabled) {
                  KeybindHud.KeybindHudState keybindHudState = VALUES_BY_KEY.computeIfAbsent(module3, KeybindHud.KeybindHudState::new);
                  keybindHudState.invoke(module3, animation, flag, floatValue);
                  ITEMS_2.add(module3);
               }
            }
         }

         ITEMS_2.sort((module, module2) -> Float.compare(VALUES_BY_KEY.get(module2).floatValue, VALUES_BY_KEY.get(module).floatValue));

         for (Module module4 : ITEMS_2) {
            ITEMS.add(VALUES_BY_KEY.get(module4));

            for (BooleanSetting booleanSetting : resolve(module4)) {
               Animation animation2 = VALUES_BY_KEY_4.computeIfAbsent(booleanSetting, booleanSetting2 -> new Animation());
               animation2.check();
               animation2.resolve4(module4.enabled ? 1.0 : 0.0, 0.2F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
               if (animation2.measure3() > 0.001F || module4.enabled) {
                  KeybindHud.KeybindHudState keybindHudState2 = VALUES_BY_KEY_2.computeIfAbsent(booleanSetting, booleanSetting3 -> new KeybindHud.KeybindHudState(module4, booleanSetting3));
                  keybindHudState2.invoke2(booleanSetting, animation2, floatValue);
                  ITEMS.add(keybindHudState2);
               }
            }
         }

         boolean flag3 = !ITEMS.isEmpty() || MinecraftAccessor.a_.currentScreen instanceof ChatScreen;
         ANIMATION.check();
         ANIMATION.resolve4(flag3 ? 1.0 : 0.0, 0.22F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
         float floatValue9 = ANIMATION.measure3();
         if (!(floatValue9 <= 0.01F)) {
            float floatValue10 = MinecraftAccessor.a_.getWindow().getFramebufferWidth();
            String text = "Binds";
            float floatValue11 = TextMeasureCache.measure(FontRegistry.fontObject4, text, floatValue6);
            float floatValue12 = 0.0F;
            float floatValue13 = 0.0F;
            float floatValue14 = 0.0F;

            for (KeybindHud.KeybindHudState keybindHudState3 : ITEMS) {
               floatValue12 = Math.max(floatValue12, keybindHudState3.floatValue);
               floatValue13 = Math.max(floatValue13, keybindHudState3.floatValue2);
               floatValue14 += (keybindHudState3.check() ? floatValue4 * 0.78F : floatValue4) * keybindHudState3.animation2.measure3();
            }

            float floatValue15 = floatValue12 + 24.0F;
            float floatValue16 = floatValue13 + 20.0F + (flag2 ? hudLayoutManagerState.floatValue14 : 0.0F);
            float floatValue17 = floatValue15 + floatValue5 + floatValue16;
            float floatValue18 = floatValue17 + floatValue2 * 2.0F;
            float floatValue19 = floatValue11 + floatValue7 + 34.0F;
            floatValue18 = Math.max(floatValue18, floatValue19 + floatValue2 * 2.0F);
            floatValue17 = floatValue18 - floatValue2 * 2.0F;
            floatValue15 = floatValue17 - floatValue5 - floatValue16;
            boolean flag4 = floatValue14 > 0.01F;
            float floatValue20 = floatValue2 + floatValue3 + (flag4 ? floatValue5 : 0.0F) + floatValue14 + floatValue2;
            ANIMATION_2.check();
            ANIMATION_3.check();
            ANIMATION_2.resolve4(floatValue18, 0.18F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
            ANIMATION_3.resolve4(floatValue20, 0.18F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
            float floatValue21 = ANIMATION_2.measure3();
            float floatValue22 = ANIMATION_3.measure3();
            float floatValue23 = Math.max(10.0F, floatValue10 - floatValue21 - 10.0F);
            float floatValue24 = 10.0F;
            HudEditorRenderer.HudEditorRendererState hudEditorRendererState = HudEditorRenderer.getINSTANCE().resolve("HUD_HotKeys", floatValue23, floatValue24, floatValue21, floatValue22);
            float floatValue25 = hudEditorRendererState.floatValue;
            float floatValue26 = hudEditorRendererState.floatValue2;
            float floatValue27 = hudEditorRendererState.floatValue3;
            float floatValue28 = hudEditorRendererState.floatValue4;
            this.invoke3(floatValue25, floatValue26, floatValue27, floatValue28);
            float floatValue29 = floatValue27 / Math.max(1.0F, floatValue21);
            float floatValue30 = floatValue28 / Math.max(1.0F, floatValue22);
            float floatValue31 = Math.min(floatValue29, floatValue30);
            float floatValue32 = floatValue2 * floatValue29;
            float floatValue33 = floatValue2 * floatValue30;
            float floatValue34 = floatValue3 * floatValue30;
            float floatValue35 = floatValue4 * floatValue30;
            float floatValue36 = floatValue * floatValue31;
            float floatValue37 = floatValue15 * floatValue29;
            float floatValue38 = floatValue16 * floatValue29;
            float floatValue39 = floatValue5 * floatValue29;
            float floatValue40 = floatValue9 * this.prozrachnost.getValue();
            int intValue = this.compute(floatValue40);
            int intValue2 = this.compute2(floatValue40);
            int intValue3 = this.compute3(floatValue40);
            int intValue4 = this.compute5(floatValue40);
            int intValue5 = this.compute6(floatValue40);
            int intValue6 = this.compute9(floatValue40);
            float floatValue41 = flag2 ? hudLayoutManagerState.floatValue : 14.0F;
            float floatValue42 = flag2 ? hudLayoutManagerState.floatValue2 : 11.0F;
            float floatValue43 = flag2 ? hudLayoutManagerState.floatValue3 : 7.0F;
            float floatValue44 = flag2 ? hudLayoutManagerState.floatValue4 : floatValue43;
            float floatValue45 = flag2 ? hudLayoutManagerState.floatValue5 : floatValue43;
            float floatValue46 = floatValue27 - floatValue32 * 2.0F;
            this.invoke(renderManager2, floatValue25, floatValue26, floatValue27, floatValue28, floatValue41, floatValue40);
            if (this.check9() || this.check10()) {
               this.invoke2(renderManager2, floatValue25 + floatValue32, floatValue26 + floatValue33, floatValue46, floatValue34, floatValue42, floatValue40);
            } else if (this.check8()) {
               if (!this.check15(floatValue25 + floatValue32, floatValue26 + floatValue33, floatValue46, floatValue34, floatValue42, false, floatValue40, 1)) {
                  renderManager2.invoke5(floatValue25 + floatValue32, floatValue26 + floatValue33, floatValue46, floatValue34, floatValue42, intValue2);
               }
            } else if (flag2) {
               renderManager2.invoke5(floatValue25 + floatValue32, floatValue26 + floatValue33, floatValue46, floatValue34, floatValue42, intValue2);
            } else {
               renderManager2.invoke6(floatValue25 + floatValue32, floatValue26 + floatValue33, floatValue46, floatValue34, 11.0F, 11.0F, 4.0F, 4.0F, intValue2);
            }

            float floatValue47 = flag2 ? floatValue25 + hudLayoutManagerState.hudLayoutManagerState3.floatValue * floatValue29 : floatValue25 + floatValue32 + 10.0F * floatValue29;
            float floatValue48 = flag2 ? floatValue26 + hudLayoutManagerState.hudLayoutManagerState3.floatValue2 * floatValue30 : measure(floatValue26 + floatValue33, floatValue34, 28.0F * floatValue31);
            renderManager2.invoke69(FontRegistry.fontObject4, floatValue47, floatValue48, floatValue6 * floatValue31, text, intValue5);
            float floatValue49 = Math.max(17.0F * floatValue31, 20.0F * floatValue30);
            float floatValue50 = floatValue25 + floatValue32 + floatValue46 - 10.0F * floatValue29 - floatValue49;
            float floatValue51 = floatValue26 + floatValue33 + (floatValue34 - floatValue49) * 0.5F;
            float floatValue52 = floatValue7 * floatValue31;
            float floatValue53 = TextMeasureCache.measure(FontRegistry.fontObject8, "q", floatValue52);
            float floatValue54 = flag2
               ? (hudLayoutManagerState.hudLayoutManagerState32.flag ? floatValue25 + floatValue27 : floatValue25) + hudLayoutManagerState.hudLayoutManagerState32.floatValue * floatValue29
               : floatValue50 + (floatValue49 - floatValue53) * 0.5F - 1.5F;
            float floatValue55 = flag2 ? floatValue26 + hudLayoutManagerState.hudLayoutManagerState32.floatValue2 * floatValue30 + 1.5F * floatValue30 : measure(floatValue51, floatValue49, floatValue52) + 1.5F * floatValue30;
            float floatValue56 = flag2 ? floatValue54 - 6.0F * floatValue29 : floatValue50;
            float floatValue57 = flag2 ? floatValue55 - floatValue49 * 0.72F : floatValue51;
            if (this.check9() || this.check10()) {
               this.invoke2(renderManager2, floatValue56, floatValue57, floatValue49, floatValue49, Math.max(5.0F, floatValue49 * 0.3F), floatValue40);
            } else if (this.check8()) {
               boolean flag5 = HudEditorRenderer.getINSTANCE().isFlag3()
                  && check(HudEditorRenderer.getINSTANCE().getFloatValue(), HudEditorRenderer.getINSTANCE().getFloatValue2(), floatValue56, floatValue57, floatValue49, floatValue49);
               this.check15(floatValue56, floatValue57, floatValue49, floatValue49, Math.max(5.0F, floatValue49 * 0.3F), flag5, floatValue40, flag5 ? 2 : 1);
            }

            renderManager2.invoke69(FontRegistry.fontObject8, floatValue54, floatValue55, floatValue52, "q", intValue6);
            float floatValue58 = floatValue26 + floatValue33 + floatValue34 + (flag4 ? floatValue5 * floatValue30 : 0.0F);
            float floatValue59 = floatValue14 * floatValue30;
            float floatValue60 = floatValue25 + floatValue32 + (flag2 ? hudLayoutManagerState.hudLayoutManagerState33.floatValue * floatValue29 : 0.0F);
            float floatValue61 = floatValue58 + (flag2 ? hudLayoutManagerState.hudLayoutManagerState33.floatValue2 * floatValue30 : 0.0F);
            float floatValue62 = floatValue25 + floatValue32 + floatValue37 + floatValue39 + (flag2 ? hudLayoutManagerState.hudLayoutManagerState34.floatValue * floatValue29 : 0.0F);
            float floatValue63 = floatValue58 + (flag2 ? hudLayoutManagerState.hudLayoutManagerState34.floatValue2 * floatValue30 : 0.0F);
            if (floatValue59 > 0.01F && (this.check5() || this.check9() || this.check10())) {
               if (this.check9() || this.check10()) {
                  this.invoke2(renderManager2, floatValue60, floatValue61, floatValue37, floatValue59, floatValue44, floatValue40);
                  this.invoke2(renderManager2, floatValue62, floatValue63, floatValue38, floatValue59, floatValue45, floatValue40);
               } else if (this.check8()) {
                  if (!this.check15(floatValue60, floatValue61, floatValue37, floatValue59, floatValue44, true, floatValue40, 2)) {
                     renderManager2.invoke5(floatValue60, floatValue61, floatValue37, floatValue59, floatValue44, intValue3);
                  }

                  if (!this.check15(floatValue62, floatValue63, floatValue38, floatValue59, floatValue45, true, floatValue40, 2)) {
                     renderManager2.invoke5(floatValue62, floatValue63, floatValue38, floatValue59, floatValue45, intValue3);
                  }
               } else if (flag2) {
                  renderManager2.invoke5(floatValue60, floatValue61, floatValue37, floatValue59, floatValue44, intValue3);
                  renderManager2.invoke5(floatValue62, floatValue63, floatValue38, floatValue59, floatValue45, intValue3);
               } else {
                  renderManager2.invoke6(floatValue60, floatValue61, floatValue37, floatValue59, 4.0F, 4.0F, 4.0F, 11.0F, intValue3);
                  renderManager2.invoke6(floatValue62, floatValue63, floatValue38, floatValue59, 4.0F, 4.0F, 11.0F, 4.0F, intValue3);
               }
            }

            renderManager2.invoke24(floatValue25, floatValue26, floatValue27, floatValue28, floatValue41, floatValue41, floatValue41, floatValue41);
            float floatValue64 = floatValue61;
            float floatValue65 = floatValue63;

            for (KeybindHud.KeybindHudState keybindHudState4 : ITEMS) {
               float floatValue66 = keybindHudState4.animation2.measure3();
               if (!(floatValue66 <= 0.01F)) {
                  boolean flag6 = keybindHudState4.check();
                  float floatValue67 = flag6 ? keybindHudState4.animation.measure3() : 1.0F;
                  float floatValue68 = flag6 ? 0.42F + 0.58F * floatValue67 : 1.0F;
                  int intValue7 = (int)(255.0F * floatValue40 * floatValue66 * floatValue68);
                  int intValue8 = ColorUtils.compute2(this.compute6(1.0F), intValue7);
                  int intValue9 = ColorUtils.compute2(this.compute9(1.0F), intValue7);
                  int intValue10 = flag6 ? ColorUtils.compute37(intValue8, intValue9, (double)floatValue67) : intValue9;
                  float floatValue69 = flag6 ? floatValue36 * 0.94F : floatValue36;
                  float floatValue70 = flag6 ? floatValue35 * 0.78F : floatValue35;
                  float floatValue71 = (1.0F - floatValue66) * 8.0F * floatValue29;
                  float floatValue72 = floatValue60 + 10.0F * floatValue29 - floatValue71 + (flag6 ? 12.0F * floatValue29 : 0.0F);
                  if (flag6) {
                     float floatValue73 = floatValue60 + 10.0F * floatValue29 - floatValue71 + floatValue8 * floatValue29 * 0.2F;
                     float floatValue74 = floatValue64 + floatValue70 * 0.5F;
                     float floatValue75 = Math.max(2.0F * floatValue29, floatValue72 - floatValue73);
                     float floatValue76 = Math.min(3.4F * floatValue29, floatValue75 * 0.05F);
                     float floatValue77 = Math.max(1.0F, 1.1F * floatValue29);
                     float floatValue78 = floatValue73 + floatValue76;
                     float floatValue79 = floatValue74 - floatValue76;
                     renderManager2.invoke40(floatValue78, floatValue79, floatValue76, 90.0F, 0.25F, floatValue77, intValue10);
                     float floatValue80 = floatValue72 - 3.5F * floatValue29;
                     if (floatValue80 > floatValue78 + 0.5F) {
                        renderManager2.invoke5(floatValue78, floatValue74 - floatValue77 * 0.5F, floatValue80 - floatValue78, floatValue77, floatValue77 * 0.5F, intValue10);
                     }
                  } else {
                     if (floatValue8 > 0.05F) {
                        renderManager2.invoke5(
                           floatValue72, floatValue64 + (floatValue70 - 8.0F * floatValue30) * 0.5F, floatValue8 * floatValue29, 8.0F * floatValue30, Math.max(0.7F, floatValue8 * 0.5F) * floatValue29, intValue9
                        );
                     }

                     floatValue72 += 8.0F * floatValue29;
                  }

                  float floatValue81 = measure(floatValue64, floatValue70, floatValue69);
                  if (!flag6 && flag && keybindHudState4.text3 != null) {
                     renderManager2.invoke69(FontRegistry.fontObject8, floatValue72, floatValue81, floatValue69, keybindHudState4.text3, intValue8);
                     floatValue72 += keybindHudState4.floatValue3 * floatValue31 + 6.0F * floatValue29;
                  }

                  renderManager2.invoke69(FontRegistry.fontObject, floatValue72, floatValue81, floatValue69, keybindHudState4.text, intValue8);
                  float floatValue82 = TextMeasureCache.measure(FontRegistry.fontObject, keybindHudState4.text2, floatValue69);
                  float floatValue83 = floatValue62 + (floatValue38 - floatValue82) * 0.5F + floatValue71;
                  float floatValue84 = flag2 ? floatValue65 + floatValue70 * 0.5F + 4.0F * floatValue30 : floatValue81;
                  if (flag6 && floatValue67 > 0.02F) {
                     float floatValue85 = 0.5F + 0.5F * (float)Math.sin(System.currentTimeMillis() / 540.0);
                     float floatValue86 = floatValue69 * 1.45F;
                     float floatValue87 = floatValue82 + 13.0F * floatValue29;
                     float floatValue88 = floatValue83 + floatValue82 * 0.5F;
                     if (flag2) {
                        float floatValue89 = floatValue65 + floatValue70 * 0.5F;
                     } else {
                        float floatValue90 = floatValue64 + floatValue70 * 0.5F;
                     }

                     int intValue11 = Math.max(0, Math.min(255, (int)(floatValue40 * floatValue66 * floatValue67 * (32.0F + 30.0F * floatValue85))));
                     int intValue12 = ColorUtils.compute2(this.compute9(1.0F), intValue11);
                  }

                  renderManager2.invoke69(FontRegistry.fontObject, floatValue83, floatValue84, floatValue69, keybindHudState4.text2, flag6 ? intValue10 : intValue9);
                  floatValue64 += floatValue70 * floatValue66;
                  floatValue65 += floatValue70 * floatValue66;
               }
            }

            renderManager2.invoke25();
            HudEditorRenderer.getINSTANCE().invoke6(hudEditorRendererState);
            HudSettingsRenderer.invoke2(
               renderManager2,
               this,
               hudEditorRendererState,
               HudEditorRenderer.getINSTANCE(),
               MinecraftAccessor.a_.getWindow().getScaledWidth(),
               MinecraftAccessor.a_.getWindow().getScaledHeight()
            );
         }
      }
   }

   private static float measure(float f, float g, float h) {
      return f + g * 0.5F + h * 0.18F;
   }

   private static List<BooleanSetting> resolve(Module module) {
      ITEMS_3.clear();

      for (Setting setting : module.getAllSettings()) {
         if (setting instanceof BooleanSetting booleanSetting4) {
            if (booleanSetting4.bindKey != -1) {
               ITEMS_3.add(booleanSetting4);
            }
         } else if (setting instanceof GroupSetting groupSetting) {
            for (BooleanSetting booleanSetting5 : groupSetting.options) {
               if (booleanSetting5.bindKey != -1) {
                  ITEMS_3.add(booleanSetting5);
               }
            }
         }
      }

      return ITEMS_3;
   }

   private static boolean check(float f, float g, float h, float i, float j, float k) {
      return f >= h && f <= h + j && g >= i && g <= i + k;
   }

   static final class KeybindHudState {
      private final Module module;
      private final BooleanSetting booleanSetting;
      final Animation animation = new Animation();
      Animation animation2;
      String text = "";
      String text2 = "";
      String text3;
      float floatValue;
      float floatValue2;
      float floatValue3;
      private int intValue = Integer.MIN_VALUE;
      private boolean flag;

      private KeybindHudState(Module module) {
         this.module = module;
         this.booleanSetting = null;
      }

      KeybindHudState(Module module, BooleanSetting booleanSetting6) {
         this.module = module;
         this.booleanSetting = booleanSetting6;
      }

      boolean check() {
         return this.booleanSetting != null;
      }

      void invoke(Module module, Animation animation3, boolean bl, float f) {
         this.animation2 = animation3;
         if (!module.name.equals(this.text)) {
            this.text = module.name;
         }

         if (module.bindKey != this.intValue) {
            this.intValue = module.bindKey;
            String text2 = KeyboardKey.resolve(module.bindKey).toUpperCase();
            if (!text2.equals(this.text2)) {
               this.text2 = text2;
               this.floatValue2 = TextMeasureCache.measure(FontRegistry.fontObject, this.text2, f);
            }
         }

         String text3 = bl && module.category != null ? module.category.getIconGlyph() : null;
         if (text3 == null) {
            this.text3 = null;
            this.floatValue3 = 0.0F;
            this.floatValue = TextMeasureCache.measure(FontRegistry.fontObject, this.text, f);
         } else {
            if (!text3.equals(this.text3)) {
               this.text3 = text3;
               this.floatValue3 = TextMeasureCache.measure(FontRegistry.fontObject8, this.text3, f);
            }

            this.floatValue = TextMeasureCache.measure(FontRegistry.fontObject, this.text, f) + this.floatValue3 + 6.0F;
         }
      }

      void invoke2(BooleanSetting booleanSetting7, Animation animation4, float f) {
         this.animation2 = animation4;
         this.animation.check();
         this.animation.resolve4(booleanSetting7.isEnabled() ? 1.0 : 0.0, 0.26, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
         float floatValue91 = f * 0.94F;
         if (!booleanSetting7.name.equals(this.text)) {
            this.text = booleanSetting7.name;
            this.floatValue = TextMeasureCache.measure(FontRegistry.fontObject, this.text, floatValue91) + 12.0F;
         }

         if (booleanSetting7.bindKey != this.intValue || booleanSetting7.holdToEnable != this.flag) {
            this.intValue = booleanSetting7.bindKey;
            this.flag = booleanSetting7.holdToEnable;
            String text4 = KeyboardKey.resolve(booleanSetting7.bindKey).toUpperCase();
            String text5 = this.flag ? "[HOLD] + " + text4 : text4;
            if (!text5.equals(this.text2)) {
               this.text2 = text5;
               this.floatValue2 = TextMeasureCache.measure(FontRegistry.fontObject, this.text2, floatValue91);
            }
         }

         this.text3 = null;
         this.floatValue3 = 0.0F;
      }
   }
}
