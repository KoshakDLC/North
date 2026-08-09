package ru.metaculture.protection;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.opengl.GlStateManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.GlTexture;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@HudElementInfo(
   resolve = "PartyListHUD",
   resolve2 = "w"
)
public final class PartyListHud extends ConfigurableHudElement {
   private static final PartyListHud INSTANCE = new PartyListHud();
   private static final MinecraftClient CLIENT = MinecraftClient.getInstance();
   private static final Animation ANIMATION = new Animation();
   private static final Animation ANIMATION_2 = new Animation();
   private static final Animation ANIMATION_3 = new Animation();
   private static final Animation ANIMATION_4 = new Animation();
   private static final Map<String, Animation> VALUES_BY_KEY = new HashMap<>();
   private static final Map<String, Identifier> VALUES_BY_KEY_2 = new HashMap<>();
   private static final Map<String, List<PartyListHud.PartyListHudState>> VALUES_BY_KEY_3 = new HashMap<>();
   private static final List<String> ITEMS = new ArrayList<>(16);
   private static boolean flag;
   private static long timestamp;
   private final BooleanSetting pokazyvatVerhushku = new BooleanSetting("Показывать верхушку", true);
   private final NumberSetting prozrachnost = new NumberSetting("Прозрачность", 1.0F, 0.1F, 1.0F, 0.05F, true);
   private final NumberSetting prozrachnostTyomnyhElementov = new NumberSetting("Прозрачность тёмных элементов", 1.0F, 0.0F, 1.0F, 0.05F, true);
   private final ModeSetting stilistika = new ModeSetting("Стилистика", "Тёмный", "Тёмный", "Светлый", "Блюр", "Феррофлюид");
   private final GroupSetting vizual = new GroupSetting("Визуал", new BooleanSetting("Тень", true), new BooleanSetting("Обводка", true));
   private final BooleanSetting pokazyvatZdorove = new BooleanSetting("Показывать здоровье", true);

   private PartyListHud() {
      this.invoke(this.pokazyvatVerhushku);
      this.invoke(this.prozrachnost);
      this.invoke(this.prozrachnostTyomnyhElementov);
      this.invoke(this.stilistika);
      this.invoke(this.vizual);
      this.invoke(this.pokazyvatZdorove);
      HudPresetManager.invoke2(this);
   }

   public static void invoke(RenderManager renderManager) {
      INSTANCE.invoke2(renderManager);
   }

   private void invoke2(RenderManager renderManager2) {
      if (CLIENT.player != null) {
         long longValue = System.currentTimeMillis();
         if (longValue - timestamp > 1000L) {
            timestamp = longValue;
            VALUES_BY_KEY_3.clear();
         }

         ITEMS.clear();

         for (String text : PartyCommand.resolve()) {
            ITEMS.add(text.toLowerCase());
         }

         String text2 = CLIENT.player.getName().getString().toLowerCase();
         if (!ITEMS.contains(text2)) {
            ITEMS.add(0, text2);
         }

         for (String text3 : ITEMS) {
            VALUES_BY_KEY.computeIfAbsent(text3, string -> new Animation()).resolve4(1.0, 0.2F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
         }

         for (Entry entry : VALUES_BY_KEY.entrySet()) {
            ((Animation)entry.getValue()).check();
            if (!ITEMS.contains(((String)entry.getKey()).toLowerCase())) {
               ((Animation)entry.getValue()).resolve4(0.0, 0.2F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
            }
         }

         boolean flag = ITEMS.isEmpty() && !(CLIENT.currentScreen instanceof ChatScreen);
         boolean flag2 = !flag;
         ANIMATION.check();
         ANIMATION_2.check();
         ANIMATION.resolve4(flag ? 0.0 : 1.0, 0.18F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
         if (flag2) {
             if (!flag) {
               ANIMATION_2.invoke(-10.0);
            }

            ANIMATION_2.resolve4(0.0, 0.2F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
         } else {
             if (flag) {
               ANIMATION_2.invoke(0.0);
            }

            ANIMATION_2.resolve4(10.0, 0.2F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
         }

          flag = flag2;
         float floatValue = ANIMATION.measure3();
         if (!(floatValue <= 0.01F)) {
            float floatValue2 = 24.0F;
            boolean flag3 = this.pokazyvatVerhushku.isEnabled();
            float floatValue3 = flag3 ? 7.0F : 0.0F;
            float floatValue4 = flag3 ? 32.0F : 0.0F;
            float floatValue5 = 22.0F;
            float floatValue6 = 28.0F;
            float floatValue7 = 10.0F;
            String text4 = "Party";
            float floatValue8 = TextMeasureCache.resolve(FontRegistry.fontObject4, text4, 26.0F).floatValue;
            float floatValue9 = floatValue7 * 2.0F + 30.0F;
            if (flag3) {
               floatValue9 = Math.max(floatValue9, floatValue8 + floatValue5 + floatValue7 * 2.0F + 24.0F);
            }

            for (Entry entry2 : VALUES_BY_KEY.entrySet()) {
               if (((Animation)entry2.getValue()).measure3() > 0.01F) {
                  List items = this.resolve((String)entry2.getKey(), ColorUtils.compute43(245, 245, 245, 255));
                  float floatValue10 = 0.0F;

                  for (PartyListHud.PartyListHudState partyListHudState : (List<PartyListHud.PartyListHudState>)items) {
                     floatValue10 += TextMeasureCache.resolve(FontRegistry.fontObject, partyListHudState.text, floatValue2).floatValue;
                  }

                  float floatValue11 = floatValue10 + floatValue7 * 2.0F + 26.0F;
                  if (this.pokazyvatZdorove.isEnabled()) {
                     floatValue11 += 40.0F;
                  }

                  floatValue9 = Math.max(floatValue9, floatValue11);
               }
            }

            float floatValue12 = 0.0F;

            for (Animation animation : VALUES_BY_KEY.values()) {
               floatValue12 += floatValue6 * Math.max(0.0F, Math.min(1.0F, animation.measure3()));
            }

            if (floatValue12 > 0.01F) {
               floatValue12 += flag3 ? 5.0F : 7.0F;
            }

            float floatValue13 = (flag3 ? floatValue3 + floatValue4 + 5.0F : 7.0F) + floatValue12;
            ANIMATION_3.check();
            ANIMATION_4.check();
            ANIMATION_3.resolve4(floatValue9, 0.18F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
            ANIMATION_4.resolve4(floatValue13, 0.18F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
            float floatValue14 = ANIMATION_3.measure3();
            float floatValue15 = ANIMATION_4.measure3();
            float floatValue16 = CLIENT.getWindow().getFramebufferWidth();
            float floatValue17 = 10.0F;
            float floatValue18 = 100.0F;
            HudEditorRenderer.HudEditorRendererState hudEditorRendererState = HudEditorRenderer.getINSTANCE().resolve("HUD_PartyList", floatValue17, floatValue18, floatValue14, floatValue15);
            float floatValue19 = hudEditorRendererState.floatValue + ANIMATION_2.measure3();
            float floatValue20 = hudEditorRendererState.floatValue2;
            float floatValue21 = hudEditorRendererState.floatValue3;
            float floatValue22 = hudEditorRendererState.floatValue4;
            float floatValue23 = floatValue21 / Math.max(1.0F, floatValue14);
            float floatValue24 = floatValue22 / Math.max(1.0F, floatValue15);
            float floatValue25 = Math.min(floatValue23, floatValue24);
            float floatValue26 = floatValue3 * floatValue23;
            float floatValue27 = flag3 ? floatValue3 * floatValue24 : 0.0F;
            float floatValue28 = flag3 ? floatValue4 * floatValue24 : 0.0F;
            float floatValue29 = floatValue6 * floatValue24;
            float floatValue30 = floatValue7 * floatValue23;
            float floatValue31 = floatValue2 * floatValue25;
            int intValue = (int)(255.0F * floatValue * this.prozrachnost.getValue());
            float floatValue32 = floatValue * this.prozrachnost.getValue() * this.prozrachnostTyomnyhElementov.getValue();
            int intValue2 = (int)(255.0F * floatValue32);
            int intValue3 = ColorUtils.compute43(24, 24, 24, intValue);
            int intValue4 = ColorUtils.compute43(40, 37, 40, intValue2);
            int intValue5 = ColorUtils.compute43(45, 45, 45, intValue);
            int intValue6 = ColorUtils.compute43(255, 255, 255, intValue);
            int intValue7 = ColorUtils.compute43(255, 255, 255, intValue);
            int intValue8 = ColorUtils.compute43(22, 22, 22, intValue2);
            if (this.stilistika.getValue().equals("Светлый")) {
               intValue3 = ColorUtils.compute43(240, 240, 245, intValue);
               intValue4 = ColorUtils.compute43(220, 220, 225, intValue2);
               intValue5 = ColorUtils.compute43(200, 200, 200, intValue);
               intValue6 = ColorUtils.compute43(20, 20, 20, intValue);
               int intValue9 = RenderManager.RenderManagerState.compute6(255, 255);
               intValue7 = ColorUtils.compute29(intValue9, (int)(255.0F * floatValue * this.prozrachnost.getValue()));
               intValue8 = ColorUtils.compute43(200, 200, 200, intValue2);
            } else if (this.stilistika.getValue().equals("Блюр")) {
               intValue3 = ColorUtils.compute43(10, 10, 10, (int)(40.0F * floatValue * this.prozrachnost.getValue()));
               intValue4 = ColorUtils.compute43(25, 25, 25, (int)(120.0F * floatValue32));
               intValue5 = ColorUtils.compute43(255, 255, 255, (int)(35.0F * floatValue * this.prozrachnost.getValue()));
               intValue8 = ColorUtils.compute43(255, 255, 255, (int)(40.0F * floatValue32));
            }

            float floatValue33 = 10.0F;
            float floatValue34 = 6.0F;
            if (this.vizual.isEnabled("Тень")) {
               renderManager2.invoke41(
                  floatValue19, floatValue20, floatValue21, floatValue22, floatValue33, 4.0F, 1.0F, ColorUtils.compute43(0, 0, 0, (int)(80.0F * floatValue * this.prozrachnost.getValue()))
               );
            }

            if (this.stilistika.getValue().equals("Блюр")) {
               renderManager2.invoke48(23.0F);
               renderManager2.invoke44(floatValue19, floatValue20, floatValue21, floatValue22, floatValue33, floatValue * this.prozrachnost.getValue());
            }

            renderManager2.invoke5(floatValue19, floatValue20, floatValue21, floatValue22, floatValue33, intValue3);
            if (this.vizual.isEnabled("Обводка")) {
               renderManager2.invoke28(floatValue19, floatValue20, floatValue21, floatValue22, floatValue33, intValue5, this.stilistika.getValue().equals("Блюр") ? 1.0F : 1.5F);
            }

            if (flag3) {
               float floatValue35 = floatValue21 - floatValue26 * 2.0F;
               if (this.stilistika.getValue().equals("Блюр")) {
                  renderManager2.invoke48(23.0F);
                  renderManager2.invoke44(floatValue19 + floatValue26, floatValue20 + floatValue27, floatValue35, floatValue28, floatValue34, floatValue32);
               }

               renderManager2.invoke5(floatValue19 + floatValue26, floatValue20 + floatValue27, floatValue35, floatValue28, floatValue34, intValue4);
               renderManager2.invoke69(
                  FontRegistry.fontObject4, floatValue19 + floatValue26 + 10.0F * floatValue23, floatValue20 + floatValue27 + floatValue28 / 2.0F + 6.0F * floatValue24, 26.0F * floatValue25, text4, intValue6
               );
               float floatValue36 = floatValue5 * floatValue24;
               float floatValue37 = floatValue19 + floatValue26 + floatValue35 - 6.0F * floatValue23 - floatValue36;
               float floatValue38 = floatValue20 + floatValue27 + (floatValue28 - floatValue36) / 2.0F;
               renderManager2.invoke5(floatValue37, floatValue38, floatValue36, floatValue36, 6.0F, intValue8);
               float floatValue39 = (floatValue2 + 4.0F) * floatValue25;
               float floatValue40 = TextMeasureCache.resolve(FontRegistry.fontObject5, "p", floatValue39).floatValue;
               renderManager2.invoke69(FontRegistry.fontObject5, floatValue37 + (floatValue36 - floatValue40) / 2.0F, floatValue38 + floatValue36 / 2.0F + 7.0F * floatValue24, floatValue39, "p", intValue7);
            }

            renderManager2.invoke24(floatValue19, floatValue20, floatValue21, floatValue22, floatValue33, floatValue33, floatValue33, floatValue33);
            float floatValue41 = floatValue20 + (flag3 ? floatValue27 + floatValue28 + 5.0F * floatValue24 : 7.0F * floatValue24);

            for (Entry entry3 : VALUES_BY_KEY.entrySet()) {
               float floatValue42 = Math.max(0.0F, Math.min(1.0F, ((Animation)entry3.getValue()).measure3()));
               if (!(floatValue42 <= 0.01F)) {
                  float floatValue43 = floatValue42 * floatValue42;
                  int intValue10 = (int)(255.0F * floatValue * floatValue43 * this.prozrachnost.getValue());
                  if (intValue10 <= 5) {
                     floatValue41 += floatValue29 * floatValue42;
                  } else {
                     String text5 = (String)entry3.getKey();
                     float floatValue44 = (1.0F - floatValue42) * 8.0F * floatValue23;
                     float floatValue45 = floatValue19 + floatValue30 - floatValue44;
                     float floatValue46 = 18.0F * floatValue25;
                     invoke3(renderManager2, text5, floatValue45, floatValue41 + (floatValue29 - floatValue46) / 2.0F, floatValue46, floatValue * floatValue43 * this.prozrachnost.getValue());
                     float floatValue47 = floatValue45 + floatValue46 + 6.0F * floatValue23;

                     for (PartyListHud.PartyListHudState partyListHudState2 : this.resolve(text5, ColorUtils.compute43(245, 245, 245, intValue10))) {
                        int intValue11 = ColorUtils.compute29(partyListHudState2.intValue, intValue10);
                        renderManager2.invoke69(FontRegistry.fontObject, floatValue47, floatValue41 + floatValue29 / 2.0F + 3.0F * floatValue24, floatValue31, partyListHudState2.text, intValue11);
                        floatValue47 += TextMeasureCache.resolve(FontRegistry.fontObject, partyListHudState2.text, floatValue31).floatValue;
                     }

                     if (this.pokazyvatZdorove.isEnabled()) {
                        String text6 = "20.0 HP";
                        float floatValue48 = TextMeasureCache.resolve(FontRegistry.fontObject, text6, floatValue31).floatValue;
                        renderManager2.invoke69(
                           FontRegistry.fontObject,
                           floatValue19 + floatValue21 - floatValue30 - floatValue48 + floatValue44,
                           floatValue41 + floatValue29 / 2.0F + 3.0F * floatValue24,
                           floatValue31,
                           text6,
                           ColorUtils.compute43(100, 255, 100, intValue10)
                        );
                     }

                     floatValue41 += floatValue29 * floatValue42;
                  }
               }
            }

            renderManager2.invoke25();
            HudEditorRenderer.getINSTANCE().invoke6(hudEditorRendererState);
            HudSettingsRenderer.invoke2(
               renderManager2, this, hudEditorRendererState, HudEditorRenderer.getINSTANCE(), CLIENT.getWindow().getScaledWidth(), CLIENT.getWindow().getScaledHeight()
            );
         }
      }
   }

   private List<PartyListHud.PartyListHudState> resolve(String string, int i) {
      ArrayList arrayList = new ArrayList();
      if (CLIENT.getNetworkHandler() != null) {
         for (PlayerListEntry playerListEntry : CLIENT.getNetworkHandler().getPlayerList()) {
            if (playerListEntry.getProfile().getName().equalsIgnoreCase(string)) {
               Object object = playerListEntry.getDisplayName() != null ? playerListEntry.getDisplayName() : Text.literal(playerListEntry.getProfile().getName());
               ((Text)object).visit((style, stringx) -> {
                  String text7 = stringx.replaceAll("(?i)§.", "").replaceAll("[^A-Za-zА-Яа-яЁё0-9\\s\\[\\]()_\\-.,!<>:|]", "");
                  if (!text7.isEmpty()) {
                     int var5x = i;
                     if (style.getColor() != null) {
                        var5x = style.getColor().getRgb() | 0xFF000000;
                     }

                     arrayList.add(new PartyListHud.PartyListHudState(text7, var5x));
                  }

                  return Optional.empty();
               }, Style.EMPTY);
               if (!arrayList.isEmpty()) {
                  return arrayList;
               }
            }
         }
      }

      arrayList.add(new PartyListHud.PartyListHudState(string, i));
      return arrayList;
   }

   private static void invoke3(RenderManager renderManager3, String string, float f, float g, float h, float i) {
      try {
         String text8 = string.toLowerCase(Locale.ROOT);
         Identifier identifier = VALUES_BY_KEY_2.computeIfAbsent(text8, string2 -> {
            GameProfile gameProfile = new GameProfile(UUID.nameUUIDFromBytes(("OfflinePlayer:" + string).getBytes()), string);
            return CLIENT.getSkinProvider().getSkinTextures(gameProfile).texture();
         });
         AbstractTexture abstractTexture = CLIENT.getTextureManager().getTexture(identifier);
         if (!(abstractTexture.getGlTexture() instanceof GlTexture glTexture)) {
            return;
         }

         int intValue12 = glTexture.getGlId();
         if (intValue12 <= 0) {
            return;
         }

         GlStateManager._bindTexture(intValue12);
         renderManager3.invoke65(i);
         renderManager3.invoke12(intValue12, f, g, h, h, 0.125F, 0.125F, 0.25F, 0.25F, 4.0F);
         renderManager3.invoke12(intValue12, f, g, h, h, 0.625F, 0.125F, 0.75F, 0.25F, 4.0F);
         renderManager3.invoke66();
      } catch (Throwable exception) {
         renderManager3.invoke5(f, g, h, h, 4.0F, ColorUtils.compute26(255, (int)(40.0F * i)));
      }
   }

   static class PartyListHudState {
      String text;
      int intValue;

      PartyListHudState(String string, int i) {
         this.text = string;
         this.intValue = i;
      }
   }
}
