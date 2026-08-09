package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.GlTexture;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import net.minecraft.util.Identifier;

@HudElementInfo(
   resolve = "PotionsHUD",
   resolve2 = "w"
)
public final class PotionsHud extends HudElement {
   private static final PotionsHud INSTANCE = new PotionsHud();
   private static final MinecraftClient CLIENT = MinecraftClient.getInstance();
   private static final List<PotionsHud.PotionsHudState> ITEMS = new ArrayList<>();
   private static final List<PotionsHud.PotionsHudState> ITEMS_2 = new ArrayList<>(16);
   private static final StatusEffectInstance[] STATUS_EFFECT_INSTANCES = new StatusEffectInstance[8];
   private static final Animation ANIMATION = new Animation();
   private static final Animation ANIMATION_2 = new Animation();
   private static final Animation ANIMATION_3 = new Animation();
   private static final Set<String> VALUES = new HashSet<>();
   private static final Set<StatusEffectInstance> VALUES_2 = new HashSet<>();
   private static final List<StatusEffectInstance> ITEMS_3 = new ArrayList<>();
   private final ModeSetting vid = new ModeSetting("Вид", "Капсулы", "Капсулы", "Список");
   private final BooleanSetting pokazyvatVerhushku = new BooleanSetting("Показывать верхушку", true);
   private final BooleanSetting pokazyvatIkonku = new BooleanSetting("Показывать иконку", true);
   private final BooleanSetting skrytBeskonechnye = new BooleanSetting("Скрыть бесконечные", false);
   private final BooleanSetting kastomnyeZelya = new BooleanSetting("Кастомные зелья", true);
   private final BooleanSetting shkalaVremeni = new BooleanSetting("Шкала времени", false);
   private static final List<PotionsHud.PotionsHudEntry> ITEMS_4 = List.of(
      new PotionsHud.PotionsHudEntry(
         "custom:hlopushka", "Хлопушка", false, "minecraft:slowness", 9, "minecraft:speed", 4, "minecraft:blindness", 9, "minecraft:glowing", 0
      ),
      new PotionsHud.PotionsHudEntry("custom:holy_water", "Святая Вода", false, "minecraft:regeneration", 2, "minecraft:invisibility", 1),
      new PotionsHud.PotionsHudEntry("custom:gnev", "Зелье Гнева", false, "minecraft:strength", 4, "minecraft:slowness", 3),
      new PotionsHud.PotionsHudEntry(
         "custom:paladin",
         "Зелье Палладина",
         false,
         "minecraft:resistance",
         0,
         "minecraft:fire_resistance",
         0,
         "minecraft:invisibility",
         0,
         "minecraft:health_boost",
         2
      ),
      new PotionsHud.PotionsHudEntry("custom:assassin", "Зелье Ассасина", false, "minecraft:strength", 3, "minecraft:speed", 2, "minecraft:haste", 0),
      new PotionsHud.PotionsHudEntry(
         "custom:radiation",
         "Зелье Радиации",
         true,
         "minecraft:poison",
         1,
         "minecraft:wither",
         1,
         "minecraft:slowness",
         2,
         "minecraft:hunger",
         4,
         "minecraft:glowing",
         0
      ),
      new PotionsHud.PotionsHudEntry(
         "custom:snotvornoye", "Снотворное", true, "minecraft:weakness", 1, "minecraft:mining_fatigue", 1, "minecraft:wither", 2, "minecraft:blindness", 0
      )
   );

   private PotionsHud() {
      this.invoke(this.vid);
      this.invoke(this.pokazyvatVerhushku);
      this.invoke(this.pokazyvatIkonku);
      this.invoke(this.skrytBeskonechnye);
      this.invoke(this.kastomnyeZelya);
      this.invoke(this.shkalaVremeni);
      ru.metaculture.protection.HudPresetManager.invoke2(this);
   }

   public static void invoke(PacketEvent packetEvent) {
      if (packetEvent != null && !packetEvent.check() && CLIENT.player != null) {
         if (packetEvent.getPacket() instanceof PlayerRespawnS2CPacket || packetEvent.getPacket() instanceof GameJoinS2CPacket) {
            ITEMS.clear();
         }
      }
   }

   public static void invoke2(RenderManager renderManager, DrawContext drawContext) {
      INSTANCE.invoke3(renderManager, drawContext);
   }

   public static PotionsHud getINSTANCE() {
      return INSTANCE;
   }

   public void invoke3(RenderManager renderManager2, DrawContext drawContext) {
      if (CLIENT.player != null) {
         this.invoke8();
         ITEMS_2.clear();
         boolean flag = this.skrytBeskonechnye.isEnabled();
         boolean flag2 = false;

         for (PotionsHud.PotionsHudState potionsHudState : ITEMS) {
            if (!flag || !potionsHudState.check()) {
               ITEMS_2.add(potionsHudState);
               if (potionsHudState.animation.measure3() > 0.01F) {
                  flag2 = true;
               }
            }
         }

         boolean flag3 = !flag2 && !(CLIENT.currentScreen instanceof ChatScreen);
         boolean flag4 = !flag3;
         ANIMATION.check();
         ANIMATION.resolve4(flag4 ? 1.0 : 0.0, 0.22F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
         float floatValue = ANIMATION.measure3();
         if (!(floatValue <= 0.01F)) {
            boolean flag5 = this.vid.getValue().equals("Капсулы");
            boolean flag6 = HudModule.check2();
            HudLayoutManager.HudLayoutManagerState hudLayoutManagerState = flag6 ? HudLayoutManager.resolve3() : null;
            float floatValue2 = 0.0F;
            float floatValue3 = 0.0F;
            if (flag5) {
               float floatValue4 = 18.0F;
               float floatValue5 = 14.0F;
               float floatValue6 = flag6 ? Math.max(28.0F, hudLayoutManagerState.floatValue11 + 14.0F) : 36.0F;
               float floatValue7 = flag6 ? hudLayoutManagerState.floatValue8 : 7.0F;
               float floatValue8 = floatValue6 - floatValue7 * 2.0F;
               float floatValue9 = floatValue8 + 4.0F;
               float floatValue10 = flag6 ? hudLayoutManagerState.floatValue9 : 5.0F;
               float floatValue11 = flag6 ? hudLayoutManagerState.floatValue9 : 5.0F;

               for (PotionsHud.PotionsHudState potionsHudState2 : ITEMS_2) {
                  float floatValue12 = TextMeasureCache.resolve(FontRegistry.fontObject, potionsHudState2.resolve(), floatValue4).floatValue;
                  float floatValue13 = TextMeasureCache.resolve(FontRegistry.fontObject, potionsHudState2.resolve2(), floatValue5).floatValue;
                  float floatValue14 = TextMeasureCache.resolve(FontRegistry.fontObject4, potionsHudState2.resolve3(), floatValue4).floatValue;
                  float floatValue15 = floatValue12 + (floatValue13 > 0.0F ? floatValue13 + 8.0F : 0.0F) + 16.0F;
                  float floatValue16 = floatValue14 + 16.0F;
                  float floatValue17 = floatValue7 * 2.0F + floatValue9 + floatValue10 + floatValue15 + floatValue10 + floatValue16;
                  if (floatValue17 > floatValue2) {
                     floatValue2 = floatValue17;
                  }

                  floatValue3 += (floatValue6 + floatValue11) * potionsHudState2.animation.measure3();
               }

               if (floatValue3 > 0.0F) {
                  floatValue3 -= floatValue11;
               }
            } else {
               float floatValue18 = 24.0F;
               float floatValue19 = flag6 ? hudLayoutManagerState.floatValue8 : 7.0F;
               float floatValue20 = this.pokazyvatVerhushku.isEnabled() ? (flag6 ? hudLayoutManagerState.floatValue10 : 32.0F) : 0.0F;
               float floatValue21 = flag6 ? hudLayoutManagerState.floatValue11 : 22.0F;
               float floatValue22 = flag6 ? hudLayoutManagerState.floatValue9 : 5.0F;
               float floatValue23 = TextMeasureCache.resolve(FontRegistry.fontObject4, "Potions", flag6 ? hudLayoutManagerState.floatValue12 : 28.0F).floatValue;
               float floatValue24 = floatValue23 + 22.0F + (flag6 ? hudLayoutManagerState.floatValue13 : 24.0F);
               float floatValue25 = 0.0F;
               float floatValue26 = 0.0F;

               for (PotionsHud.PotionsHudState potionsHudState3 : ITEMS_2) {
                  String text = potionsHudState3.resolve() + (potionsHudState3.resolve2().isEmpty() ? "" : " " + potionsHudState3.resolve2());
                  floatValue25 = Math.max(floatValue25, TextMeasureCache.resolve(FontRegistry.fontObject, text, floatValue18).floatValue);
                  floatValue26 = Math.max(floatValue26, TextMeasureCache.resolve(FontRegistry.fontObject, potionsHudState3.resolve3(), floatValue18).floatValue);
               }

               float floatValue27 = this.pokazyvatIkonku.isEnabled() ? 22.0F : 0.0F;
               float floatValue28 = floatValue25 + floatValue27 + 24.0F;
               float floatValue29 = floatValue26 + 20.0F + (flag6 ? hudLayoutManagerState.floatValue14 : 0.0F);
               float floatValue30 = floatValue28 + floatValue22 + floatValue29;
               floatValue2 = floatValue30 + floatValue19 * 2.0F;
               if (this.pokazyvatVerhushku.isEnabled()) {
                  floatValue2 = Math.max(floatValue2, floatValue24 + floatValue19 * 2.0F);
               }

               float floatValue31 = 0.0F;

               for (PotionsHud.PotionsHudState potionsHudState4 : ITEMS_2) {
                  floatValue31 += floatValue21 * potionsHudState4.animation.measure3();
               }

               floatValue3 = floatValue19 + floatValue20 + (this.pokazyvatVerhushku.isEnabled() && floatValue31 > 0.01F ? floatValue22 : 0.0F) + floatValue31 + floatValue19;
               if (ITEMS_2.isEmpty() && this.pokazyvatVerhushku.isEnabled()) {
                  floatValue3 = floatValue19 + floatValue20 + floatValue19;
               }
            }

            ANIMATION_2.check();
            ANIMATION_3.check();
            ANIMATION_2.resolve4(floatValue2, 0.18F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
            ANIMATION_3.resolve4(floatValue3, 0.18F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
            float floatValue32 = ANIMATION_2.measure3();
            float floatValue33 = ANIMATION_3.measure3();
            float floatValue34 = CLIENT.getWindow().getFramebufferWidth();
            HudEditorRenderer.HudEditorRendererState hudEditorRendererState = HudEditorRenderer.getINSTANCE().resolve("HUD_Potions", Math.max(10.0F, floatValue34 - floatValue32 - 10.0F), 70.0F, floatValue32, floatValue33);
            if (flag5) {
               this.invoke4(renderManager2, drawContext, hudEditorRendererState, ITEMS_2, floatValue, floatValue32);
            } else {
               this.invoke5(renderManager2, drawContext, hudEditorRendererState, ITEMS_2, floatValue, floatValue32, floatValue33);
            }
         }
      }
   }

   private void invoke4(RenderManager renderManager3, DrawContext drawContext, HudEditorRenderer.HudEditorRendererState hudEditorRendererState2, List<PotionsHud.PotionsHudState> list, float f, float g) {
      float floatValue35 = hudEditorRendererState2.floatValue;
      float floatValue36 = hudEditorRendererState2.floatValue2;
      float floatValue37 = hudEditorRendererState2.floatValue3;
      float floatValue38 = floatValue37 / Math.max(1.0F, g);
      boolean flag7 = HudModule.check2();
      HudLayoutManager.HudLayoutManagerState hudLayoutManagerState2 = flag7 ? HudLayoutManager.resolve3() : null;
      this.invoke3(floatValue35, floatValue36, floatValue37, Math.max(36.0F * floatValue38, hudEditorRendererState2.floatValue4));
      float floatValue39 = (flag7 ? Math.max(28.0F, hudLayoutManagerState2.floatValue11 + 14.0F) : 36.0F) * floatValue38;
      float floatValue40 = (flag7 ? hudLayoutManagerState2.floatValue8 : 7.0F) * floatValue38;
      float floatValue41 = floatValue39 - floatValue40 * 2.0F;
      float floatValue42 = floatValue41 + 4.0F * floatValue38;
      float floatValue43 = (flag7 ? hudLayoutManagerState2.floatValue9 : 5.0F) * floatValue38;
      float floatValue44 = (flag7 ? hudLayoutManagerState2.floatValue9 : 5.0F) * floatValue38;
      float floatValue45 = 18.0F * floatValue38;
      float floatValue46 = 14.0F * floatValue38;
      float floatValue47 = f * this.prozrachnost.getValue();
      int intValue = this.compute(floatValue47);
      int intValue2 = this.compute3(floatValue47);
      int intValue3 = this.compute5(floatValue47);
      int intValue4 = this.compute6(floatValue47);
      int intValue5 = ColorUtils.compute43(130, 130, 130, (int)(255.0F * floatValue47));
      int intValue6 = ColorUtils.compute43(145, 160, 255, (int)(255.0F * floatValue47));
      int intValue7 = ColorUtils.compute43(255, 77, 77, (int)(255.0F * floatValue47));
      float floatValue48 = (flag7 ? hudLayoutManagerState2.floatValue : 11.0F) * floatValue38;
      float floatValue49 = (flag7 ? hudLayoutManagerState2.floatValue7 : 8.0F) * floatValue38;
      float floatValue50 = (flag7 ? hudLayoutManagerState2.floatValue4 : 6.0F) * floatValue38;
      float floatValue51 = (flag7 ? hudLayoutManagerState2.floatValue5 : 8.0F) * floatValue38;

      for (PotionsHud.PotionsHudState potionsHudState5 : list) {
         float floatValue52 = Math.max(0.0F, Math.min(1.0F, potionsHudState5.animation.measure3()));
         if (!(floatValue52 <= 0.01F)) {
            float floatValue53 = TextMeasureCache.resolve(FontRegistry.fontObject, potionsHudState5.resolve(), floatValue45).floatValue;
            float floatValue54 = TextMeasureCache.resolve(FontRegistry.fontObject, potionsHudState5.resolve2(), floatValue46).floatValue;
            float floatValue55 = TextMeasureCache.resolve(FontRegistry.fontObject4, potionsHudState5.resolve3(), floatValue45).floatValue;
            float floatValue56 = floatValue53 + (floatValue54 > 0.0F ? floatValue54 + 8.0F * floatValue38 : 0.0F) + 16.0F * floatValue38;
            float floatValue57 = floatValue55 + 16.0F * floatValue38;
            float floatValue58 = floatValue40 * 2.0F + floatValue42 + floatValue43 + floatValue56 + floatValue43 + floatValue57;
            float floatValue59 = potionsHudState5.measure2();
            int intValue8 = (int)(255.0F * floatValue47 * floatValue52 * floatValue59);
            int intValue9 = ColorUtils.compute2(intValue, (int)((intValue >> 24 & 0xFF) * floatValue52));
            int intValue10 = ColorUtils.compute2(intValue2, (int)((intValue2 >> 24 & 0xFF) * floatValue52));
            int intValue11 = ColorUtils.compute2(potionsHudState5.check2() ? intValue7 : intValue4, intValue8);
            int intValue12 = ColorUtils.compute2(intValue5, intValue8);
            int intValue13 = ColorUtils.compute2(intValue6, intValue8);
            float floatValue60 = (1.0F - floatValue52) * 8.0F * floatValue38;
            float floatValue61 = floatValue35 - floatValue60;
            this.invoke(renderManager3, floatValue61, floatValue36, floatValue58, floatValue39, floatValue48, floatValue47 * floatValue52);
            float floatValue62 = floatValue61 + floatValue40;
            float floatValue63 = floatValue36 + floatValue40;
            float floatValue64 = floatValue63 + floatValue41 / 2.0F + 3.5F * floatValue38;
            if (this.check8()) {
               this.invoke2(renderManager3, floatValue62, floatValue63, floatValue42, floatValue41, floatValue49, floatValue47 * floatValue52);
            } else {
               renderManager3.invoke6(floatValue62, floatValue63, floatValue42, floatValue41, floatValue49, 4.0F, 4.0F, floatValue49, intValue10);
            }

            if (potionsHudState5.flag) {
               this.invoke7(renderManager3, potionsHudState5.resolve(), floatValue62, floatValue63, floatValue42, floatValue41, floatValue49, 0.7F);
            } else {
               int intValue14 = compute(potionsHudState5.identifier);
               if (intValue14 > 0) {
                  float floatValue65 = 18.0F * floatValue38;
                  float floatValue66 = floatValue62 + (floatValue42 - floatValue65) / 2.0F;
                  float floatValue67 = floatValue63 + (floatValue41 - floatValue65) / 2.0F;
                  renderManager3.invoke65(floatValue47 * floatValue52 * floatValue59);
                  renderManager3.invoke11(intValue14, floatValue66, floatValue67, floatValue65, floatValue65, 0.0F, 0.0F, 1.0F, 1.0F);
                  renderManager3.invoke66();
               } else {
                  float floatValue68 = TextMeasureCache.resolve(FontRegistry.fontObject5, "j", 18.0F * floatValue38).floatValue;
                  renderManager3.invoke69(
                     FontRegistry.fontObject5, floatValue62 + (floatValue42 - floatValue68) / 2.0F, floatValue63 + floatValue41 / 2.0F + 5.0F * floatValue38, 18.0F * floatValue38, "j", intValue11
                  );
               }
            }

            floatValue62 += floatValue42 + floatValue43;
            if (this.check8()) {
               this.invoke2(renderManager3, floatValue62, floatValue63, floatValue56, floatValue41, floatValue50, floatValue47 * floatValue52);
            } else {
               renderManager3.invoke5(floatValue62, floatValue63, floatValue56, floatValue41, flag7 ? floatValue50 : 4.0F, intValue10);
            }

            float floatValue69 = floatValue62 + 10.0F * floatValue38;
            renderManager3.invoke69(FontRegistry.fontObject, floatValue69, floatValue64, floatValue45, potionsHudState5.resolve(), intValue11);
            if (floatValue54 > 0.0F) {
               renderManager3.invoke69(FontRegistry.fontObject, floatValue69 + floatValue53 + 8.0F * floatValue38, floatValue64, floatValue46, potionsHudState5.resolve2(), intValue12);
            }

            floatValue62 += floatValue56 + floatValue43;
            if (this.check8()) {
               this.invoke2(renderManager3, floatValue62, floatValue63, floatValue57, floatValue41, floatValue51, floatValue47 * floatValue52);
            } else {
               renderManager3.invoke6(floatValue62, floatValue63, floatValue57, floatValue41, 4.0F, floatValue51, floatValue51, 4.0F, intValue10);
            }

            if (this.shkalaVremeni.isEnabled() && !potionsHudState5.check()) {
               float floatValue70 = potionsHudState5.measure();
               if (floatValue70 > 0.001F) {
                  float floatValue71 = Math.max(3.0F * floatValue38, floatValue57 * floatValue70);
                  int intValue15 = ColorUtils.compute2(potionsHudState5.check2() ? intValue7 : intValue6, (int)(60.0F * floatValue47 * floatValue52));
                  renderManager3.invoke24(floatValue62, floatValue63, floatValue57, floatValue41, 4.0F, floatValue51, floatValue51, 4.0F);
                  renderManager3.invoke5(floatValue62, floatValue63, floatValue71, floatValue41, 0.0F, intValue15);
                  renderManager3.invoke25();
               }
            }

            potionsHudState5.hudMetricUtils.invoke2(potionsHudState5.resolve3(), potionsHudState5.compute2());
            potionsHudState5.hudMetricUtils
               .invoke3(
                  renderManager3,
                  FontRegistry.fontObject4,
                  floatValue62,
                  floatValue63,
                  floatValue57,
                  floatValue41,
                  Math.min(floatValue51, floatValue41 * 0.5F),
                  floatValue62 + floatValue57 * 0.5F,
                  floatValue64,
                  floatValue45,
                  intValue13
               );
            floatValue36 += (floatValue39 + floatValue44) * floatValue52;
         }
      }

      HudEditorRenderer.getINSTANCE().invoke6(hudEditorRendererState2);
      HudSettingsRenderer.invoke2(
         renderManager3, this, hudEditorRendererState2, HudEditorRenderer.getINSTANCE(), CLIENT.getWindow().getScaledWidth(), CLIENT.getWindow().getScaledHeight()
      );
   }

   private void invoke5(
      RenderManager renderManager4, DrawContext drawContext, HudEditorRenderer.HudEditorRendererState hudEditorRendererState3, List<PotionsHud.PotionsHudState> list, float f, float g, float h
   ) {
      float floatValue72 = hudEditorRendererState3.floatValue;
      float floatValue73 = hudEditorRendererState3.floatValue2;
      float floatValue74 = hudEditorRendererState3.floatValue3;
      float floatValue75 = hudEditorRendererState3.floatValue4;
      this.invoke3(floatValue72, floatValue73, floatValue74, floatValue75);
      float floatValue76 = floatValue74 / Math.max(1.0F, g);
      float floatValue77 = floatValue75 / Math.max(1.0F, h);
      float floatValue78 = Math.min(floatValue76, floatValue77);
      boolean flag8 = HudModule.check2();
      HudLayoutManager.HudLayoutManagerState hudLayoutManagerState3 = flag8 ? HudLayoutManager.resolve3() : null;
      float floatValue79 = (flag8 ? hudLayoutManagerState3.floatValue8 : 7.0F) * floatValue76;
      float floatValue80 = (flag8 ? hudLayoutManagerState3.floatValue8 : 7.0F) * floatValue77;
      float floatValue81 = this.pokazyvatVerhushku.isEnabled() ? (flag8 ? hudLayoutManagerState3.floatValue10 : 32.0F) * floatValue77 : 0.0F;
      float floatValue82 = (flag8 ? hudLayoutManagerState3.floatValue11 : 22.0F) * floatValue77;
      float floatValue83 = (flag8 ? hudLayoutManagerState3.floatValue9 : 5.0F) * floatValue76;
      float floatValue84 = (flag8 ? hudLayoutManagerState3.floatValue9 : 5.0F) * floatValue77;
      float floatValue85 = 24.0F * floatValue78;
      boolean flag9 = this.pokazyvatIkonku.isEnabled();
      float floatValue86 = flag9 ? 22.0F : 0.0F;
      float floatValue87 = 0.0F;
      float floatValue88 = 0.0F;

      for (PotionsHud.PotionsHudState potionsHudState6 : list) {
         String text2 = potionsHudState6.resolve() + (potionsHudState6.resolve2().isEmpty() ? "" : " " + potionsHudState6.resolve2());
         floatValue87 = Math.max(floatValue87, TextMeasureCache.resolve(FontRegistry.fontObject, text2, 24.0F).floatValue);
         floatValue88 = Math.max(floatValue88, TextMeasureCache.resolve(FontRegistry.fontObject, potionsHudState6.resolve3(), 24.0F).floatValue);
      }

      float floatValue89 = (floatValue87 + floatValue86 + 24.0F) * floatValue76;
      float floatValue90 = (floatValue88 + 20.0F + (flag8 ? hudLayoutManagerState3.floatValue14 : 0.0F)) * floatValue76;
      float floatValue91 = floatValue89 + floatValue83 + floatValue90;
      float floatValue92 = floatValue74 - floatValue79 * 2.0F;
      if (floatValue92 > floatValue91) {
         floatValue89 = floatValue92 - floatValue83 - floatValue90;
      }

      float floatValue93 = f * this.prozrachnost.getValue();
      int intValue16 = this.compute2(floatValue93);
      int intValue17 = this.compute3(floatValue93);
      int intValue18 = this.compute6(floatValue93);
      int intValue19 = this.compute9(floatValue93);
      float floatValue94 = flag8 ? hudLayoutManagerState3.floatValue : 14.0F;
      float floatValue95 = flag8 ? hudLayoutManagerState3.floatValue2 : 11.0F;
      float floatValue96 = flag8 ? hudLayoutManagerState3.floatValue3 : 7.0F;
      float floatValue97 = flag8 ? hudLayoutManagerState3.floatValue4 : floatValue96;
      float floatValue98 = flag8 ? hudLayoutManagerState3.floatValue5 : floatValue96;
      this.invoke(renderManager4, floatValue72, floatValue73, floatValue74, floatValue75, floatValue94, floatValue93);
      if (this.pokazyvatVerhushku.isEnabled()) {
         if (this.check8()) {
            this.invoke(renderManager4, floatValue72 + floatValue79, floatValue73 + floatValue80, floatValue92, floatValue81, floatValue95, floatValue93);
         } else if (flag8) {
            renderManager4.invoke5(floatValue72 + floatValue79, floatValue73 + floatValue80, floatValue92, floatValue81, floatValue95, intValue16);
         } else {
            renderManager4.invoke6(floatValue72 + floatValue79, floatValue73 + floatValue80, floatValue92, floatValue81, 11.0F, 11.0F, 4.0F, 4.0F, intValue16);
         }

         float floatValue99 = flag8 ? floatValue72 + hudLayoutManagerState3.hudLayoutManagerState3.floatValue * floatValue76 : floatValue72 + floatValue79 + 10.0F * floatValue76;
         float floatValue100 = flag8 ? floatValue73 + hudLayoutManagerState3.hudLayoutManagerState3.floatValue2 * floatValue77 : floatValue73 + floatValue80 + floatValue81 / 2.0F + 6.0F * floatValue77;
         renderManager4.invoke69(FontRegistry.fontObject4, floatValue99, floatValue100, (flag8 ? hudLayoutManagerState3.floatValue12 : 28.0F) * floatValue78, "Potions", intValue18);
         float floatValue101 = 22.0F * floatValue77;
         float floatValue102 = floatValue72 + floatValue79 + floatValue92 - 10.0F * floatValue76 - floatValue101;
         float floatValue103 = floatValue73 + floatValue80 + (floatValue81 - floatValue101) / 2.0F;
         float floatValue104 = (flag8 ? hudLayoutManagerState3.floatValue13 : 24.0F) * floatValue78;
         float floatValue105 = TextMeasureCache.resolve(FontRegistry.fontObject5, "t", floatValue104).floatValue;
         float floatValue106 = flag8
            ? (hudLayoutManagerState3.hudLayoutManagerState32.flag ? floatValue72 + floatValue74 : floatValue72) + hudLayoutManagerState3.hudLayoutManagerState32.floatValue * floatValue76
            : floatValue102 + (floatValue101 - floatValue105) / 2.0F;
         float floatValue107 = flag8 ? floatValue73 + hudLayoutManagerState3.hudLayoutManagerState32.floatValue2 * floatValue77 : floatValue103 + floatValue101 / 2.0F + 5.5F * floatValue77;
         renderManager4.invoke69(FontRegistry.fontObject5, floatValue106, floatValue107, floatValue104, "t", intValue19);
      }

      float floatValue108 = floatValue73 + floatValue80 + floatValue81 + (this.pokazyvatVerhushku.isEnabled() ? floatValue84 : 0.0F);
      float floatValue109 = floatValue72 + floatValue79 + (flag8 ? hudLayoutManagerState3.hudLayoutManagerState33.floatValue * floatValue76 : 0.0F);
      float floatValue110 = floatValue108 + (flag8 ? hudLayoutManagerState3.hudLayoutManagerState33.floatValue2 * floatValue77 : 0.0F);
      float floatValue111 = floatValue72 + floatValue79 + floatValue89 + floatValue83 + (flag8 ? hudLayoutManagerState3.hudLayoutManagerState34.floatValue * floatValue76 : 0.0F);
      float floatValue112 = floatValue108 + (flag8 ? hudLayoutManagerState3.hudLayoutManagerState34.floatValue2 * floatValue77 : 0.0F);
      float floatValue113 = 0.0F;

      for (PotionsHud.PotionsHudState potionsHudState7 : list) {
         floatValue113 += floatValue82 * potionsHudState7.animation.measure3();
      }

      if (floatValue113 > 0.01F && this.check5()) {
         if (this.check8()) {
            this.invoke2(renderManager4, floatValue109, floatValue110, floatValue89, floatValue113, floatValue97, floatValue93);
            this.invoke2(renderManager4, floatValue111, floatValue112, floatValue90, floatValue113, floatValue98, floatValue93);
         } else if (flag8) {
            renderManager4.invoke5(floatValue109, floatValue110, floatValue89, floatValue113, floatValue97, intValue17);
            renderManager4.invoke5(floatValue111, floatValue112, floatValue90, floatValue113, floatValue98, intValue17);
         } else {
            renderManager4.invoke6(floatValue109, floatValue110, floatValue89, floatValue113, 4.0F, 4.0F, 4.0F, 11.0F, intValue17);
            renderManager4.invoke6(floatValue111, floatValue112, floatValue90, floatValue113, 4.0F, 4.0F, 11.0F, 4.0F, intValue17);
         }
      }

      renderManager4.invoke24(floatValue72, floatValue73, floatValue74, floatValue75, floatValue94, floatValue94, floatValue94, floatValue94);
      float floatValue114 = floatValue110;
      float floatValue115 = floatValue112;

      for (PotionsHud.PotionsHudState potionsHudState8 : list) {
         float floatValue116 = potionsHudState8.animation.measure3();
         if (!(floatValue116 <= 0.01F)) {
            float floatValue117 = potionsHudState8.measure2();
            int intValue20 = (int)(255.0F * floatValue93 * floatValue116 * floatValue117);
            int intValue21 = ColorUtils.compute2(this.compute6(1.0F), intValue20);
            int intValue22 = ColorUtils.compute2(this.compute9(1.0F), intValue20);
            if (potionsHudState8.check2()) {
               intValue21 = ColorUtils.compute43(255, 85, 85, intValue20);
               intValue22 = ColorUtils.compute43(255, 120, 120, intValue20);
            }

            float floatValue118 = (1.0F - floatValue116) * 8.0F * floatValue76;
            float floatValue119 = floatValue109 + 10.0F * floatValue76 - floatValue118;
            if (!flag8 || hudLayoutManagerState3.floatValue15 > 0.05F) {
               float floatValue120 = flag8 ? hudLayoutManagerState3.floatValue15 * floatValue76 : 1.9F * floatValue76;
               renderManager4.invoke5(floatValue119, floatValue114 + (floatValue82 - 8.0F * floatValue77) / 2.0F, floatValue120, 8.0F * floatValue77, Math.max(0.7F, floatValue120 * 0.5F), intValue22);
            }

            floatValue119 += 8.0F * floatValue76;
            if (flag9) {
               float floatValue121 = 14.0F * floatValue78;
               float floatValue122 = floatValue114 + (floatValue82 - floatValue121) * 0.5F;
               this.invoke6(renderManager4, potionsHudState8, floatValue119, floatValue122, floatValue121, floatValue93 * floatValue116 * floatValue117, intValue21);
               floatValue119 += floatValue121 + 6.0F * floatValue76;
            }

            String text3 = potionsHudState8.resolve() + (potionsHudState8.resolve2().isEmpty() ? "" : " " + potionsHudState8.resolve2());
            renderManager4.invoke69(FontRegistry.fontObject, floatValue119, floatValue114 + floatValue82 / 2.0F + 4.0F * floatValue77, floatValue85, text3, intValue21);
            if (this.shkalaVremeni.isEnabled() && !potionsHudState8.check()) {
               float floatValue123 = potionsHudState8.measure();
               if (floatValue123 > 0.001F) {
                  float floatValue124 = Math.max(2.0F, floatValue82 - 6.0F * floatValue77);
                  float floatValue125 = Math.max(1.0F, floatValue90 - 6.0F * floatValue76);
                  float floatValue126 = Math.max(3.0F * floatValue76, floatValue125 * floatValue123);
                  float floatValue127 = floatValue111 + 3.0F * floatValue76 + floatValue118;
                  float floatValue128 = floatValue115 + (floatValue82 - floatValue124) * 0.5F;
                  float floatValue129 = floatValue124 * 0.4F;
                  int intValue23 = ColorUtils.compute2(intValue22, (int)(ColorUtils.compute4(intValue22) * 0.22F));
                  renderManager4.invoke24(floatValue127, floatValue128, floatValue125, floatValue124, floatValue129, floatValue129, floatValue129, floatValue129);
                  renderManager4.invoke5(floatValue127, floatValue128, floatValue126, floatValue124, 0.0F, intValue23);
                  renderManager4.invoke25();
               }
            }

            potionsHudState8.hudMetricUtils.invoke2(potionsHudState8.resolve3(), potionsHudState8.compute2());
            potionsHudState8.hudMetricUtils
               .invoke3(
                  renderManager4,
                  FontRegistry.fontObject,
                  floatValue111,
                  floatValue115,
                  floatValue90,
                  floatValue82,
                  Math.min(floatValue98, floatValue82 * 0.5F),
                  floatValue111 + floatValue90 * 0.5F + floatValue118,
                  floatValue115 + floatValue82 / 2.0F + 4.0F * floatValue77,
                  floatValue85,
                  intValue22
               );
            floatValue114 += floatValue82 * floatValue116;
            floatValue115 += floatValue82 * floatValue116;
         }
      }

      renderManager4.invoke25();
      HudEditorRenderer.getINSTANCE().invoke6(hudEditorRendererState3);
      HudSettingsRenderer.invoke2(
         renderManager4, this, hudEditorRendererState3, HudEditorRenderer.getINSTANCE(), CLIENT.getWindow().getScaledWidth(), CLIENT.getWindow().getScaledHeight()
      );
   }

   private void invoke6(RenderManager renderManager5, PotionsHud.PotionsHudState potionsHudState9, float f, float g, float h, float i, int j) {
      if (potionsHudState9.flag) {
         this.invoke7(renderManager5, potionsHudState9.resolve(), f, g, h, h, h * 0.25F, 1.0F);
      } else {
         int intValue24 = compute(potionsHudState9.identifier);
         if (intValue24 > 0) {
            renderManager5.invoke65(i);
            renderManager5.invoke11(intValue24, f, g, h, h, 0.0F, 0.0F, 1.0F, 1.0F);
            renderManager5.invoke66();
         } else {
            float floatValue130 = TextMeasureCache.resolve(FontRegistry.fontObject5, "j", h).floatValue;
            renderManager5.invoke69(FontRegistry.fontObject5, f + (h - floatValue130) * 0.5F, g + h * 0.5F + h * 0.28F, h, "j", j);
         }
      }
   }

   private void invoke7(RenderManager renderManager6, String string, float f, float g, float h, float i, float j, float k) {
      ItemStack itemStack = SpecialItemIconRenderer.resolve(string);
      if (itemStack != null && !itemStack.isEmpty() && !(h <= 0.0F) && !(i <= 0.0F)) {
         float floatValue131 = Math.max(1.0F, Math.min(h, i) * k);
         float floatValue132 = ItemRenderUtil.measure3(floatValue131 / 16.0F);
         float floatValue133 = 16.0F * floatValue132;
         float floatValue134 = ItemRenderUtil.measure(f);
         float floatValue135 = ItemRenderUtil.measure(g);
         float floatValue136 = Math.max(1.0F, ItemRenderUtil.measure(h));
         float floatValue137 = Math.max(1.0F, ItemRenderUtil.measure(i));
         float floatValue138 = ItemRenderUtil.measure(floatValue134 + (floatValue136 - floatValue133) * 0.5F);
         float floatValue139 = ItemRenderUtil.measure(floatValue135 + (floatValue137 - floatValue133) * 0.5F);
         renderManager6.invoke20();
         renderManager6.invoke24(floatValue134, floatValue135, floatValue136, floatValue137, j, j, j, j);
         boolean flag10 = false ;

         try {
            flag10 = true;
            ItemRenderUtil.invoke3(renderManager6, itemStack, floatValue138, floatValue139, floatValue132, 0, false, 0);
            flag10 = false;
         } finally {
            if (flag10) {
               renderManager6.invoke20();
               renderManager6.invoke25();
            }
         }

         renderManager6.invoke20();
         renderManager6.invoke25();
      }
   }

   private static int compute(Identifier identifier) {
      if (CLIENT != null && CLIENT.getTextureManager() != null) {
         AbstractTexture abstractTexture = CLIENT.getTextureManager().getTexture(identifier);
         return abstractTexture != null && abstractTexture.getGlTexture() instanceof GlTexture glTexture ? glTexture.getGlId() : -1;
      } else {
         return -1;
      }
   }

   private void invoke8() {
      if (CLIENT.player != null) {
         VALUES.clear();
         VALUES_2.clear();
         ITEMS_3.clear();

         for (StatusEffectInstance statusEffectInstance2 : CLIENT.player.getStatusEffects()) {
            if (!Removals.check3(statusEffectInstance2.getEffectType())) {
               ITEMS_3.add(statusEffectInstance2);
            }
         }

         boolean flag11 = CLIENT.currentScreen instanceof ChatScreen;
         if (flag11 && ITEMS_3.isEmpty()) {
            VALUES.add("minecraft:fire_resistance");
            invoke10("minecraft:fire_resistance", I18n.translate("effect.minecraft.fire_resistance", new Object[0]), 1, 8000, false);
            VALUES.add("minecraft:strength");
            invoke10("minecraft:strength", I18n.translate("effect.minecraft.strength", new Object[0]), 3, 2380, false);
            VALUES.add("minecraft:poison");
            invoke10("minecraft:poison", I18n.translate("effect.minecraft.poison", new Object[0]), 2, 240, true);
         }

         if (this.kastomnyeZelya.isEnabled()) {
            for (PotionsHud.PotionsHudEntry potionsHudEntry : ITEMS_4) {
               boolean flag12 = true;
               int intValue25 = 0;

               for (PotionsHud.PotionsHudEntry2 potionsHudEntry2 : potionsHudEntry.reqs()) {
                  StatusEffectInstance statusEffectInstance3 = null;
                  int intValue26 = 0;

                  for (int intValue27 = ITEMS_3.size(); intValue26 < intValue27; intValue26++) {
                     StatusEffectInstance statusEffectInstance4 = ITEMS_3.get(intValue26);
                     if (statusEffectInstance4.getEffectType().getIdAsString().equals(potionsHudEntry2.id())
                        && (statusEffectInstance4.getAmplifier() == potionsHudEntry2.amp() || statusEffectInstance4.getAmplifier() == potionsHudEntry2.amp() - 1)) {
                        statusEffectInstance3 = statusEffectInstance4;
                        break;
                     }
                  }

                  if (statusEffectInstance3 == null) {
                     flag12 = false;
                     break;
                  }

                  STATUS_EFFECT_INSTANCES[intValue25++] = statusEffectInstance3;
               }

               if (flag12) {
                  VALUES.add(potionsHudEntry.id());
                  int intValue28 = 0;

                  for (int intValue29 = 0; intValue29 < intValue25; intValue29++) {
                     StatusEffectInstance statusEffectInstance5 = STATUS_EFFECT_INSTANCES[intValue29];
                     VALUES_2.add(statusEffectInstance5);
                     if (statusEffectInstance5.getDuration() > intValue28) {
                        intValue28 = statusEffectInstance5.getDuration();
                     }

                     STATUS_EFFECT_INSTANCES[intValue29] = null;
                  }

                  invoke9(potionsHudEntry.id(), potionsHudEntry.name(), 1, intValue28, potionsHudEntry.harmful());
               }
            }
         }

         for (StatusEffectInstance statusEffectInstance6 : ITEMS_3) {
            if (!VALUES_2.contains(statusEffectInstance6)) {
               String text4 = statusEffectInstance6.getEffectType().getIdAsString();
               VALUES.add(text4);
               invoke11(text4, statusEffectInstance6);
            }
         }

         for (PotionsHud.PotionsHudState potionsHudState10 : ITEMS) {
            if (!VALUES.contains(potionsHudState10.text)) {
               potionsHudState10.animation.resolve4(0.0, 0.15F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, true);
            }

            potionsHudState10.animation.check();
         }

         ITEMS.removeIf(potionsHudState11 -> potionsHudState11.animation.measure3() <= 0.01F && !VALUES.contains(potionsHudState11.text));
         ITEMS.sort(Comparator.comparingInt(PotionsHud.PotionsHudState::compute).reversed());
      }
   }

   private static void invoke9(String string, String string2, int i, int j, boolean bl) {
      PotionsHud.PotionsHudState potionsHudState12 = resolve(string);
      if (potionsHudState12 == null) {
         potionsHudState12 = new PotionsHud.PotionsHudState(string);
         potionsHudState12.flag = true;
         potionsHudState12.flag2 = false;
         potionsHudState12.text2 = string2;
         potionsHudState12.intValue = i;
         potionsHudState12.flag3 = bl;
         potionsHudState12.intValue2 = j;
         potionsHudState12.animation.invoke(0.0);
         potionsHudState12.animation.resolve4(1.0, 0.15F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
         ITEMS.add(potionsHudState12);
      } else {
         potionsHudState12.flag = true;
         potionsHudState12.flag2 = false;
         potionsHudState12.intValue2 = j;
         potionsHudState12.animation.resolve4(1.0, 0.15F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, true);
      }
   }

   private static void invoke10(String string, String string2, int i, int j, boolean bl) {
      PotionsHud.PotionsHudState potionsHudState13 = resolve(string);
      if (potionsHudState13 == null) {
         potionsHudState13 = new PotionsHud.PotionsHudState(string);
         potionsHudState13.animation.invoke(0.0);
         potionsHudState13.animation.resolve4(1.0, 0.15F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
         ITEMS.add(potionsHudState13);
      } else {
         potionsHudState13.animation.resolve4(1.0, 0.15F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, true);
      }

      potionsHudState13.flag = false;
      potionsHudState13.flag2 = true;
      potionsHudState13.statusEffectInstance = null;
      potionsHudState13.text2 = string2;
      potionsHudState13.intValue = i;
      potionsHudState13.intValue2 = j;
      potionsHudState13.flag3 = bl;
   }

   private static void invoke11(String string, StatusEffectInstance statusEffectInstance) {
      PotionsHud.PotionsHudState potionsHudState14 = resolve(string);
      if (potionsHudState14 == null) {
         potionsHudState14 = new PotionsHud.PotionsHudState(string);
         potionsHudState14.flag = false;
         potionsHudState14.flag2 = false;
         potionsHudState14.statusEffectInstance = statusEffectInstance;
         potionsHudState14.animation.invoke(0.0);
         potionsHudState14.animation.resolve4(1.0, 0.15F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
         ITEMS.add(potionsHudState14);
      } else {
         potionsHudState14.flag = false;
         potionsHudState14.flag2 = false;
         potionsHudState14.statusEffectInstance = statusEffectInstance;
         potionsHudState14.animation.resolve4(1.0, 0.15F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, true);
      }
   }

   private static PotionsHud.PotionsHudState resolve(String string) {
      for (PotionsHud.PotionsHudState potionsHudState15 : ITEMS) {
         if (potionsHudState15.text.equals(string)) {
            return potionsHudState15;
         }
      }

      return null;
   }

   static String resolve2(String string) {
      return string != null && !string.isEmpty()
         ? string.replaceAll("(?i)\\u0412?\\u00A7[0-9A-FK-OR]", "").replace("§", "").replace("Â", "").replaceAll("\\p{Cntrl}", "").trim()
         : "";
   }

   record PotionsHudEntry(String id, String name, boolean harmful, List<PotionsHud.PotionsHudEntry2> reqs) {
      public PotionsHudEntry(String string, String string2, boolean bl, Object... objects) {
         this(string, string2, bl, buildReqs(objects));
      }

      private static List<PotionsHud.PotionsHudEntry2> buildReqs(Object[] objects) {
         ArrayList arrayList = new ArrayList();

         for (byte byteValue = 0; byteValue < objects.length; byteValue += 2) {
            arrayList.add(new PotionsHud.PotionsHudEntry2((String)objects[byteValue], (Integer)objects[byteValue + 1]));
         }

         return arrayList;
      }
   }

   record PotionsHudEntry2(String id, int amp) {
   }

   static final class PotionsHudState {
      final String text;
      final Identifier identifier;
      boolean flag;
      boolean flag2;
      String text2;
      int intValue = 1;
      int intValue2;
      boolean flag3;
      StatusEffectInstance statusEffectInstance;
      final Animation animation = new Animation();
      private final Animation animation2 = new Animation();
      final HudMetricUtils hudMetricUtils = new HudMetricUtils();
      private int intValue3;
      private String text3;
      private String text4;
      private int intValue4 = Integer.MIN_VALUE;
      private String text5;
      private int intValue5 = Integer.MIN_VALUE;
      private boolean flag4;

      PotionsHudState(String string) {
         this.text = string;
         int intValue30 = string.indexOf(58);
         String text5 = intValue30 > 0 ? string.substring(0, intValue30) : "minecraft";
         String text6 = intValue30 > 0 && intValue30 + 1 < string.length() ? string.substring(intValue30 + 1) : string;
         this.identifier = Identifier.of(text5, "textures/mob_effect/" + text6 + ".png");
      }

      public int compute() {
         return !this.flag && !this.flag2 && this.statusEffectInstance != null ? this.statusEffectInstance.getDuration() : this.intValue2;
      }

      public boolean check() {
         return !this.flag && this.statusEffectInstance != null && this.statusEffectInstance.isInfinite();
      }

      public String resolve() {
         if (!this.flag2 && !this.flag) {
            if (this.text3 == null) {
               this.text3 = PotionsHud.resolve2(I18n.translate(this.statusEffectInstance.getTranslationKey(), new Object[0]));
            }

            return this.text3;
         } else {
            return PotionsHud.resolve2(this.text2);
         }
      }

      public String resolve2() {
         int intValue31 = !this.flag2 && !this.flag ? this.statusEffectInstance.getAmplifier() + 1 : this.intValue;
         if (intValue31 == this.intValue4 && this.text4 != null) {
            return this.text4;
         } else {
            this.intValue4 = intValue31;
            this.text4 = intValue31 > 1 ? "lvl " + intValue31 : "";
            return this.text4;
         }
      }

      public String resolve3() {
         boolean flag13 = !this.flag && !this.flag2 && this.statusEffectInstance != null && this.statusEffectInstance.isInfinite();
         int intValue32 = !this.flag && !this.flag2 && this.statusEffectInstance != null ? this.statusEffectInstance.getDuration() : this.intValue2;
         int intValue33 = flag13 ? Integer.MAX_VALUE : Math.max(0, intValue32 / 20);
         if (intValue33 == this.intValue5 && flag13 == this.flag4 && this.text5 != null) {
            return this.text5;
         } else {
            this.intValue5 = intValue33;
            this.flag4 = flag13;
            if (flag13) {
               String text7 = PotionsHud.resolve2(StatusEffectUtil.getDurationText(this.statusEffectInstance, 1.0F, 20.0F).getString());
               this.text5 = text7 != null && !text7.isEmpty() ? text7 : "∞";
            } else {
               this.text5 = intValue33 / 60 + (intValue33 % 60 < 10 ? ":0" : ":") + intValue33 % 60;
            }

            return this.text5;
         }
      }

      public boolean check2() {
         if (this.flag2) {
            return this.flag3;
         } else {
            return this.flag
               ? this.flag3
               : ((StatusEffect)this.statusEffectInstance.getEffectType().value()).getCategory() == StatusEffectCategory.HARMFUL;
         }
      }

      public float measure() {
         this.animation2.check();
         int intValue34 = !this.flag && !this.flag2 && this.statusEffectInstance != null ? this.statusEffectInstance.getDuration() : this.intValue2;
         if (intValue34 > this.intValue3) {
            this.intValue3 = intValue34;
         }

         float floatValue140 = this.intValue3 <= 0 ? 0.0F : Math.max(0.0F, Math.min(1.0F, (float)intValue34 / this.intValue3));
         this.animation2.resolve4(floatValue140, 0.2F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
         return this.animation2.measure3();
      }

      public int compute2() {
         return !this.flag && !this.flag2 && this.statusEffectInstance != null ? this.statusEffectInstance.getDuration() : this.intValue2;
      }

      public float measure2() {
         int intValue35 = !this.flag && !this.flag2 && this.statusEffectInstance != null ? this.statusEffectInstance.getDuration() : this.intValue2;
         if (!this.flag && !this.flag2 && this.statusEffectInstance != null && this.statusEffectInstance.isInfinite()) {
            return 1.0F;
         } else {
            float floatValue141 = Math.max(0.0F, intValue35 / 20.0F);
            if (floatValue141 > 10.0F) {
               return 1.0F;
            } else {
               float floatValue142 = 1.0F - floatValue141 / 10.0F;
               float floatValue143 = 0.8F + floatValue142 * 4.2F;
               double doubleValue = System.currentTimeMillis() / 1000.0 * floatValue143 * Math.PI * 2.0;
               return 0.68F + (float)((Math.sin(doubleValue) + 1.0) * 0.5) * 0.32F;
            }
         }
      }
   }
}
