package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.network.PlayerListEntry;

@HudElementInfo(
   resolve = "StaffListHUD",
   resolve2 = "w"
)
public final class StaffListHud extends ConfigurableHudElement {
   private static final StaffListHud INSTANCE = new StaffListHud();
   private static final MinecraftClient CLIENT = MinecraftClient.getInstance();
   private static final Animation ANIMATION = new Animation();
   private static final Animation ANIMATION_2 = new Animation();
   private static final Animation ANIMATION_3 = new Animation();
   private static final Animation ANIMATION_4 = new Animation();
   private static final Map<String, Animation> VALUES_BY_KEY = new HashMap<>();
   private static final List<StaffListHud.StaffListHudState> ITEMS = new ArrayList<>(32);
   private static boolean flag;
   private static long timestamp;
   private static final List<String> ITEMS_2 = List.of(
      "helper", "moder", "staff", "admin", "curator", "stager", "sotrudnik", "pomoshnik", "стаж", "сотруд", "модер", "админ", "куратор", "хелпер"
   );
   private final BooleanSetting pokazyvatVerhushku = new BooleanSetting("Показывать верхушку", true);
   private final NumberSetting prozrachnost = new NumberSetting("Прозрачность", 1.0F, 0.1F, 1.0F, 0.05F, true);
   private final NumberSetting prozrachnostTyomnyhElementov = new NumberSetting("Прозрачность тёмных элементов", 1.0F, 0.0F, 1.0F, 0.05F, true);
   private final ModeSetting stilistika = new ModeSetting("Стилистика", "Тёмный", "Тёмный", "Светлый", "Блюр", "Феррофлюид");
   private final GroupSetting vizual = new GroupSetting("Визуал", new BooleanSetting("Тень", true), new BooleanSetting("Обводка", true));
   private final BooleanSetting pokazyvatGolovy = new BooleanSetting("Показывать головы", true);
   private static final Map<Character, Integer> VALUES_BY_KEY_2 = new HashMap<>();

   private StaffListHud() {
      this.invoke(this.pokazyvatVerhushku);
      this.invoke(this.prozrachnost);
      this.invoke(this.prozrachnostTyomnyhElementov);
      this.invoke(this.stilistika);
      this.invoke(this.vizual);
      this.invoke(this.pokazyvatGolovy);
      HudPresetManager.invoke2(this);
   }

   public static void invoke(RenderManager renderManager) {
      INSTANCE.invoke2(renderManager);
   }

   private void invoke2(RenderManager renderManager2) {
      if (CLIENT.player != null && CLIENT.getNetworkHandler() != null) {
         long longValue = System.currentTimeMillis();
         if (longValue - timestamp > 500L) {
            timestamp = longValue;
            invoke3();
         }

         for (StaffListHud.StaffListHudState staffListHudState : ITEMS) {
            VALUES_BY_KEY.computeIfAbsent(staffListHudState.text, string -> new Animation()).resolve4(1.0, 0.2F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
         }

         for (Entry entry : VALUES_BY_KEY.entrySet()) {
            ((Animation)entry.getValue()).check();
            boolean flag = false;

            for (StaffListHud.StaffListHudState staffListHudState2 : ITEMS) {
               if (staffListHudState2.text.equals(entry.getKey())) {
                  flag = true;
                  break;
               }
            }

            if (!flag) {
               ((Animation)entry.getValue()).resolve4(0.0, 0.2F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
            }
         }

         boolean flag2 = ITEMS.isEmpty() && !(CLIENT.currentScreen instanceof ChatScreen);
         boolean flag3 = !flag2;
         ANIMATION.check();
         ANIMATION_2.check();
         ANIMATION.resolve4(flag2 ? 0.0 : 1.0, 0.18F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
         if (flag3) {
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

         flag = flag3;
         float floatValue = ANIMATION.measure3();
         if (!(floatValue <= 0.01F)) {
            float floatValue2 = 24.0F;
            boolean flag4 = this.pokazyvatVerhushku.isEnabled();
            float floatValue3 = flag4 ? 7.0F : 0.0F;
            float floatValue4 = flag4 ? 29.48F : 0.0F;
            float floatValue5 = 22.0F;
            float floatValue6 = 19.37F;
            float floatValue7 = 10.0F;
            String text = "Staff";
            float floatValue8 = TextMeasureCache.resolve(FontRegistry.fontObject4, text, 30.0F).floatValue;
            float floatValue9 = floatValue7 * 2.0F + 30.0F;
            if (flag4) {
               floatValue9 = Math.max(floatValue9, floatValue8 + floatValue5 + floatValue7 * 2.0F + 24.0F);
            }

            floatValue9 = Math.max(floatValue9, 228.379F);

            for (Entry entry2 : VALUES_BY_KEY.entrySet()) {
               if (((Animation)entry2.getValue()).measure3() > 0.01F) {
                  float floatValue10 = TextMeasureCache.resolve(FontRegistry.fontObject, (String)entry2.getKey(), floatValue2).floatValue + floatValue7 * 2.0F + 20.0F;
                  if (this.pokazyvatGolovy.isEnabled()) {
                     floatValue10 += 22.0F;
                  }

                  floatValue9 = Math.max(floatValue9, floatValue10);
               }
            }

            float floatValue11 = 0.0F;

            for (Animation animation : VALUES_BY_KEY.values()) {
               floatValue11 += floatValue6 * Math.max(0.0F, Math.min(1.0F, animation.measure3()));
            }

            if (floatValue11 > 0.01F) {
               floatValue11 += flag4 ? 5.0F : 7.0F;
            }

            float floatValue12 = (flag4 ? floatValue3 + floatValue4 + floatValue3 : 12.0F) + floatValue11;
            ANIMATION_3.check();
            ANIMATION_4.check();
            ANIMATION_3.resolve4(floatValue9, 0.18F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
            ANIMATION_4.resolve4(floatValue12, 0.18F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
            float floatValue13 = ANIMATION_3.measure3();
            float floatValue14 = ANIMATION_4.measure3();
            float floatValue15 = CLIENT.getWindow().getFramebufferWidth();
            float floatValue16 = Math.max(10.0F, floatValue15 - floatValue13 - 10.0F);
            float floatValue17 = 100.0F;
            HudEditorRenderer.HudEditorRendererState hudEditorRendererState = HudEditorRenderer.getINSTANCE().resolve("HUD_StaffList", floatValue16, floatValue17, floatValue13, floatValue14);
            float floatValue18 = hudEditorRendererState.floatValue + ANIMATION_2.measure3();
            float floatValue19 = hudEditorRendererState.floatValue2;
            float floatValue20 = hudEditorRendererState.floatValue3;
            float floatValue21 = hudEditorRendererState.floatValue4;
            float floatValue22 = floatValue20 / Math.max(1.0F, floatValue13);
            float floatValue23 = floatValue21 / Math.max(1.0F, floatValue14);
            float floatValue24 = Math.min(floatValue22, floatValue23);
            float floatValue25 = floatValue3 * floatValue22;
            float floatValue26 = flag4 ? floatValue3 * floatValue23 : 0.0F;
            float floatValue27 = flag4 ? floatValue4 * floatValue23 : 0.0F;
            float floatValue28 = floatValue6 * floatValue23;
            float floatValue29 = floatValue7 * floatValue22;
            float floatValue30 = floatValue2 * floatValue24;
            int intValue = (int)(255.0F * floatValue * this.prozrachnost.getValue());
            float floatValue31 = floatValue * this.prozrachnost.getValue() * this.prozrachnostTyomnyhElementov.getValue();
            int intValue2 = (int)(255.0F * floatValue31);
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
               intValue3 = ColorUtils.compute43(21, 22, 26, (int)(122.0F * floatValue * this.prozrachnost.getValue()));
               intValue4 = ColorUtils.compute43(21, 22, 26, (int)(184.0F * floatValue31));
               intValue5 = ColorUtils.compute43(255, 255, 255, (int)(10.0F * floatValue * this.prozrachnost.getValue()));
               intValue8 = ColorUtils.compute43(255, 255, 255, (int)(10.0F * floatValue31));
            }

            float floatValue32 = 14.0F;
            float floatValue33 = 10.0F;
            if (this.vizual.isEnabled("Тень")) {
               renderManager2.invoke41(
                  floatValue18, floatValue19, floatValue20, floatValue21, floatValue32, 4.0F, 1.0F, ColorUtils.compute43(0, 0, 0, (int)(80.0F * floatValue * this.prozrachnost.getValue()))
               );
            }

            if (this.stilistika.getValue().equals("Блюр")) {
               renderManager2.invoke48(23.0F);
               renderManager2.invoke44(floatValue18, floatValue19, floatValue20, floatValue21, floatValue32, floatValue * this.prozrachnost.getValue());
            }

            renderManager2.invoke5(floatValue18, floatValue19, floatValue20, floatValue21, floatValue32, intValue3);
            if (this.vizual.isEnabled("Обводка")) {
               renderManager2.invoke28(floatValue18, floatValue19, floatValue20, floatValue21, floatValue32, intValue5, this.stilistika.getValue().equals("Блюр") ? 1.0F : 1.5F);
            }

            if (flag4) {
               float floatValue34 = floatValue20 - floatValue25 * 2.0F;
               if (this.stilistika.getValue().equals("Блюр")) {
                  renderManager2.invoke48(23.0F);
                  renderManager2.invoke44(floatValue18 + floatValue25, floatValue19 + floatValue26, floatValue34, floatValue27, floatValue33, floatValue31);
               }

               renderManager2.invoke6(floatValue18 + floatValue25, floatValue19 + floatValue26, floatValue34, floatValue27, 10.0F * floatValue24, 10.0F * floatValue24, 4.0F * floatValue24, 4.0F * floatValue24, intValue4);
               renderManager2.invoke69(
                  FontRegistry.fontObject4, floatValue18 + floatValue25 + 12.4F * floatValue22, floatValue19 + floatValue26 + floatValue27 / 2.0F + 6.0F * floatValue23, 30.0F * floatValue24, text, intValue6
               );
               float floatValue35 = floatValue5 * floatValue23;
               float floatValue36 = floatValue18 + floatValue25 + floatValue34 - 6.0F * floatValue22 - floatValue35;
               float floatValue37 = floatValue19 + floatValue26 + (floatValue27 - floatValue35) / 2.0F;
               renderManager2.invoke5(floatValue36, floatValue37, floatValue35, floatValue35, 6.0F, intValue8);
               float floatValue38 = (floatValue2 + 4.0F) * floatValue24;
               float floatValue39 = TextMeasureCache.resolve(FontRegistry.fontObject5, "f", floatValue38).floatValue;
               renderManager2.invoke69(FontRegistry.fontObject5, floatValue36 + (floatValue35 - floatValue39) / 2.0F, floatValue37 + floatValue35 / 2.0F + 7.0F * floatValue23, floatValue38, "f", intValue7);
            }

            renderManager2.invoke24(floatValue18, floatValue19, floatValue20, floatValue21, floatValue32, floatValue32, floatValue32, floatValue32);
            float floatValue40 = floatValue19 + (flag4 ? floatValue26 + floatValue27 + 5.0F * floatValue23 : 7.0F * floatValue23);

            for (Entry entry3 : VALUES_BY_KEY.entrySet()) {
               float floatValue41 = Math.max(0.0F, Math.min(1.0F, ((Animation)entry3.getValue()).measure3()));
               if (!(floatValue41 <= 0.01F)) {
                  StaffListHud.StaffListHudState staffListHudState3 = null;

                  for (StaffListHud.StaffListHudState staffListHudState4 : ITEMS) {
                     if (staffListHudState4.text.equals(entry3.getKey())) {
                        staffListHudState3 = staffListHudState4;
                        break;
                     }
                  }

                  float floatValue42 = (1.0F - floatValue41) * 8.0F * floatValue22;
                  float floatValue43 = floatValue18 + floatValue29 - floatValue42;
                  if (this.pokazyvatGolovy.isEnabled() && staffListHudState3 != null) {
                     float floatValue44 = 16.0F * floatValue24;
                     renderManager2.invoke5(
                        floatValue43,
                        floatValue40 + (floatValue28 - floatValue44) / 2.0F,
                        floatValue44,
                        floatValue44,
                        4.0F,
                        ColorUtils.compute43(150, 150, 150, (int)(255.0F * floatValue * this.prozrachnost.getValue()))
                     );
                     floatValue43 += floatValue44 + 6.0F * floatValue22;
                  }

                  if (staffListHudState3 != null) {
                     float floatValue45 = floatValue43;

                     for (StaffListHud.StaffListHudState2 staffListHudState22 : staffListHudState3.items) {
                        int intValue10 = (int)(255.0F * floatValue * floatValue41 * this.prozrachnost.getValue());
                        int intValue11 = ColorUtils.compute29(staffListHudState22.intValue, intValue10);
                        renderManager2.invoke69(FontRegistry.fontObject, floatValue45, floatValue40 + floatValue28 / 2.0F + 3.0F * floatValue23, floatValue30, staffListHudState22.text, intValue11);
                        floatValue45 += TextMeasureCache.resolve(FontRegistry.fontObject, staffListHudState22.text, floatValue30).floatValue;
                     }
                  } else {
                     renderManager2.invoke69(
                        FontRegistry.fontObject,
                        floatValue43,
                        floatValue40 + floatValue28 / 2.0F + 3.0F * floatValue23,
                        floatValue30,
                        (String)entry3.getKey(),
                        ColorUtils.compute43(200, 200, 200, (int)(255.0F * floatValue * this.prozrachnost.getValue()))
                     );
                  }

                  floatValue40 += floatValue28 * floatValue41;
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

   private static void invoke3() {
      ITEMS.clear();
      if (CLIENT.getNetworkHandler() != null) {
         for (PlayerListEntry playerListEntry : CLIENT.getNetworkHandler().getPlayerList()) {
            String text2 = playerListEntry.getProfile().getName();
            String text3 = playerListEntry.getDisplayName() != null ? playerListEntry.getDisplayName().getString() : text2;
            String text4 = text3.toLowerCase(Locale.ROOT);
            boolean flag5 = false;

            for (String text5 : ITEMS_2) {
               if (text4.contains(text5)) {
                  flag5 = true;
                  break;
               }
            }

            if (flag5) {
               ITEMS.add(resolve2(text3));
            }
         }
      }
   }

   private static String resolve(String string) {
      return string.replaceAll("(?i)§[0-9A-FK-OR]", "");
   }

   private static StaffListHud.StaffListHudState resolve2(String string) {
      ArrayList arrayList = new ArrayList();
      StringBuilder stringBuilder = new StringBuilder();
      int intValue12 = -1;

      for (int intValue13 = 0; intValue13 < string.length(); intValue13++) {
         char character = string.charAt(intValue13);
         if (character == 167 && intValue13 + 1 < string.length()) {
            char character2 = Character.toLowerCase(string.charAt(intValue13 + 1));
            if (VALUES_BY_KEY_2.containsKey(character2)) {
               if (!stringBuilder.isEmpty()) {
                  arrayList.add(new StaffListHud.StaffListHudState2(stringBuilder.toString(), intValue12));
                  stringBuilder.setLength(0);
               }

               intValue12 = VALUES_BY_KEY_2.get(character2);
            }

            intValue13++;
         } else {
            stringBuilder.append(character);
         }
      }

      if (!stringBuilder.isEmpty()) {
         arrayList.add(new StaffListHud.StaffListHudState2(stringBuilder.toString(), intValue12));
      }

      return new StaffListHud.StaffListHudState(resolve(string), arrayList);
   }

   static {
      VALUES_BY_KEY_2.put('0', -16777216);
      VALUES_BY_KEY_2.put('1', -16777046);
      VALUES_BY_KEY_2.put('2', -16733696);
      VALUES_BY_KEY_2.put('3', -16733526);
      VALUES_BY_KEY_2.put('4', -5636096);
      VALUES_BY_KEY_2.put('5', -5635926);
      VALUES_BY_KEY_2.put('6', -22016);
      VALUES_BY_KEY_2.put('7', -5592406);
      VALUES_BY_KEY_2.put('8', -11184811);
      VALUES_BY_KEY_2.put('9', -11184641);
      VALUES_BY_KEY_2.put('a', -11141291);
      VALUES_BY_KEY_2.put('b', -11141121);
      VALUES_BY_KEY_2.put('c', -43691);
      VALUES_BY_KEY_2.put('d', -43521);
      VALUES_BY_KEY_2.put('e', -171);
      VALUES_BY_KEY_2.put('f', -1);
   }

   static class StaffListHudState {
      final String text;
      final List<StaffListHud.StaffListHudState2> items;

      StaffListHudState(String string, List<StaffListHud.StaffListHudState2> list) {
         this.text = string;
         this.items = list;
      }
   }

   static class StaffListHudState2 {
      final String text;
      final int intValue;

      StaffListHudState2(String string, int i) {
         this.text = string;
         this.intValue = i;
      }
   }
}
