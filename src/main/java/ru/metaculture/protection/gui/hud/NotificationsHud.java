package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.Generated;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.GlTexture;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

@HudElementInfo(
   resolve = "Notifications",
   resolve2 = "w"
)
public final class NotificationsHud extends HudElement {
   public static final String MODULI = "Модули";
   public static final String SVAP_PREDMETOV = "Свап предметов";
   public static final String EFFEKTY = "Эффекты";
   public static final String NIZKOE_HP = "Низкое HP";
   public static final String PREDUPREZHDENIYA = "Предупреждения";
   public static final String POLOMKA_BRONI = "Поломка брони";
   private static final NotificationsHud INSTANCE = new NotificationsHud();
   private static final List<NotificationsHud.NotificationsHudItemState> ITEMS = new ArrayList<>();
   private static final Map<String, NotificationsHud.NotificationsHudState> VALUES_BY_KEY = new HashMap<>();
   private static final Animation ANIMATION = new Animation();
   private static final float FLOAT_VALUE = 40.0F;
   private static final float FLOAT_VALUE_2 = 22.0F;
   private static final float FLOAT_VALUE_3 = 28.0F;
   private static final float FLOAT_VALUE_4 = 8.0F;
   private static final int INT_VALUE = 5;
   private static final float FLOAT_VALUE_5 = 220.0F;
   private static final EquipmentSlot[] EQUIPMENT_SLOTS = new EquipmentSlot[]{EquipmentSlot.FEET, EquipmentSlot.LEGS, EquipmentSlot.CHEST, EquipmentSlot.HEAD};
   private static final int[] INTS = new int[]{100, 100, 100, 100};
   private static final Item[] ITEMS_2 = new Item[4];
   private static final String SVAPNUL_NA = "Свапнул на » ";
   private static final String SKORO_SLOMAETSYA = "Скоро сломается » ";
   private static final Identifier IDENTIFIER = Identifier.of("minecraft", "textures/gui/sprites/hud/heart/full.png");
   private static float floatValue = Float.NaN;
   private static boolean flag;
   private final GroupSetting pokazyvat = new GroupSetting(
      "Показывать",
      new BooleanSetting("Модули", true),
      new BooleanSetting("Свап предметов", true),
      new BooleanSetting("Эффекты", true),
      new BooleanSetting("Низкое HP", true),
      new BooleanSetting("Предупреждения", true),
      new BooleanSetting("Поломка брони", true)
   );

   private NotificationsHud() {
      this.invoke(this.pokazyvat);
      ru.metaculture.protection.HudPresetManager.invoke2(this);
   }

   public static NotificationsHud getINSTANCE() {
      return INSTANCE;
   }

   public static boolean check(String string) {
      return INSTANCE.pokazyvat.isEnabled(string);
   }

   public static void invoke() {
      if (MinecraftAccessor.a_.player != null && MinecraftAccessor.a_.world != null) {
         if (check("Эффекты")) {
            invoke11();
         } else {
            VALUES_BY_KEY.clear();
         }

         if (check("Низкое HP")) {
            invoke15();
         } else {
            flag = false;
         }

         if (check("Поломка брони")) {
            invoke13();
         } else {
            invoke14();
         }
      } else {
         VALUES_BY_KEY.clear();
         flag = false;
         invoke14();
      }
   }

   public static void show(String string, String string2, long l) {
      invoke2(string, string2, l, "Предупреждения");
   }

   public static void invoke2(String string, String string2, long l, String string3) {
      if (check(string3)) {
         ITEMS.add(new NotificationsHud.NotificationsHudItemState(string, string2, l));
      }
   }

   public static void invoke3(ItemStack itemStack, String string, long l) {
      if (check("Свап предметов")) {
         String text = resolve3(string);
         if ((text == null || text.isEmpty()) && itemStack != null && !itemStack.isEmpty()) {
            text = resolve3(itemStack.getName().getString());
         }

         if (text == null || text.isEmpty()) {
            text = "предмет";
         }

         int intValue = compute(string);
         if (intValue == 0 && itemStack != null && itemStack.contains(DataComponentTypes.CUSTOM_NAME)) {
            intValue = compute(itemStack.getName().getString());
         }

         String text2 = "Свапнул на » " + text;
         if (itemStack != null && !itemStack.isEmpty()) {
            ITEMS.add(new NotificationsHud.NotificationsHudItemState(itemStack.copy(), text2, "Свапнул на » ".length(), intValue, l));
         } else {
            NotificationsHud.NotificationsHudItemState notificationsHudItemState = new NotificationsHud.NotificationsHudItemState("i", text2, l);
            notificationsHudItemState.intValue2 = "Свапнул на » ".length();
            notificationsHudItemState.intValue3 = intValue;
            ITEMS.add(notificationsHudItemState);
         }
      }
   }

   public static void invoke4(String string, String string2) {
      if (check("Эффекты")) {
         ITEMS.add(new NotificationsHud.NotificationsHudItemState(resolve5(string), string2 + " » Скоро закончится", 2200L));
      }
   }

   public static void invoke5(String string, String string2) {
      if (check("Эффекты")) {
         ITEMS.add(new NotificationsHud.NotificationsHudItemState(resolve5(string), "Истёк » " + string2, 2200L));
      }
   }

   public static void invoke6(ItemStack itemStack, int i) {
      if (check("Поломка брони") && itemStack != null && !itemStack.isEmpty()) {
         String text3 = "Скоро сломается » " + i + "%";
         int intValue2 = ColorUtils.compute43(255, 70, 70, 255);
         ITEMS.add(new NotificationsHud.NotificationsHudItemState(itemStack.copy(), text3, "Скоро сломается » ".length(), intValue2, 2600L));
      }
   }

   public static void showModuleToggle(String string, boolean bl) {
      if (check("Модули")) {
         for (NotificationsHud.NotificationsHudItemState notificationsHudItemState2 : ITEMS) {
            if (notificationsHudItemState2.isFlag() && notificationsHudItemState2.getText2().equals(string)) {
               notificationsHudItemState2.setFlag2(bl);
               notificationsHudItemState2.setTimestamp(System.currentTimeMillis());
               return;
            }
         }

         ITEMS.add(new NotificationsHud.NotificationsHudItemState(string, bl, 1000L));
      }
   }

   public static void invoke7(RenderManager renderManager) {
      INSTANCE.invoke8(renderManager);
   }

   private void invoke8(RenderManager renderManager2) {
      if (MinecraftAccessor.a_.player != null) {
         boolean flag = MinecraftAccessor.a_.currentScreen instanceof ChatScreen;
         ITEMS.removeIf(notificationsHudItemState3 -> {
            notificationsHudItemState3.getAnimation().check();
            return notificationsHudItemState3.check3() && notificationsHudItemState3.getAnimation().measure3() <= 0.01F;
         });
         ANIMATION.check();
         ANIMATION.resolve4(ITEMS.isEmpty() && flag ? 1.0 : 0.0, 0.22F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
         float floatValue = ANIMATION.measure3();
         float floatValue2 = 38.0F;
         float floatValue3 = 5.0F;
         float floatValue4 = 28.0F;
         if (ITEMS.isEmpty()) {
            if (!(floatValue <= 0.01F)) {
               String text4 = "Настройте позицию";
               float floatValue5 = 24.0F;
               float floatValue6 = TextMeasureCache.resolve(FontRegistry.fontObject, text4, floatValue5).floatValue;
               float floatValue7 = floatValue6 + 20.0F;
               int intValue3 = MinecraftAccessor.a_.getWindow().getFramebufferWidth();
               int intValue4 = MinecraftAccessor.a_.getWindow().getFramebufferHeight();
               float floatValue8 = intValue4 / 2.0F + 140.0F;
               HudEditorRenderer.HudEditorRendererState hudEditorRendererState = this.resolve(floatValue2, floatValue2, floatValue8, intValue3, intValue4);
                float floatValue9 = hudEditorRendererState.floatValue - floatValue7 * 0.5F;
               float floatValue10 = hudEditorRendererState.floatValue2;
               this.invoke3(floatValue9, floatValue10, floatValue7, floatValue2);
               float floatValue11 = this.prozrachnost.getValue() * floatValue;
               int intValue5 = this.compute6(floatValue11);
               float floatValue12 = 12.0F;
               this.invoke(renderManager2, floatValue9, floatValue10, floatValue7, floatValue2, floatValue12, floatValue11);
               String text5 = resolve2(text4, floatValue5, Math.max(0.0F, floatValue7 - 20.0F));
               float floatValue13 = TextMeasureCache.resolve(FontRegistry.fontObject, text5, floatValue5).floatValue;
               renderManager2.invoke24(floatValue9, floatValue10, Math.max(1.0F, floatValue7), Math.max(1.0F, floatValue2), floatValue12, floatValue12, floatValue12, floatValue12);
               renderManager2.invoke69(FontRegistry.fontObject, floatValue9 + (floatValue7 - floatValue13) / 2.0F, floatValue10 + floatValue2 / 2.0F + 3.0F, floatValue5, text5, intValue5);
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
         } else {
            float floatValue14 = 0.0F;
            float floatValue15 = 0.0F;

            for (NotificationsHud.NotificationsHudItemState notificationsHudItemState4 : ITEMS) {
               float floatValue16 = measure(notificationsHudItemState4, floatValue4);
               if (floatValue16 > floatValue14) {
                  floatValue14 = floatValue16;
               }

               floatValue15 += (floatValue2 + floatValue3) * notificationsHudItemState4.getAnimation().measure3();
            }

            int intValue6 = MinecraftAccessor.a_.getWindow().getFramebufferWidth();
            int intValue7 = MinecraftAccessor.a_.getWindow().getFramebufferHeight();
            float floatValue17 = intValue7 / 2.0F + 140.0F;
            HudEditorRenderer.HudEditorRendererState hudEditorRendererState2 = this.resolve(floatValue15 > 0.0F ? floatValue15 : floatValue2, floatValue2, floatValue17, intValue6, intValue7);
            float floatValue18 = hudEditorRendererState2.floatValue2;
             this.invoke3(hudEditorRendererState2.floatValue - floatValue14 * 0.5F, floatValue18, floatValue14, Math.max(floatValue2, hudEditorRendererState2.floatValue4));

            for (int intValue8 = ITEMS.size() - 1; intValue8 >= 0; intValue8--) {
               NotificationsHud.NotificationsHudItemState notificationsHudItemState5 = ITEMS.get(intValue8);
               boolean flag2 = !notificationsHudItemState5.check3();
               notificationsHudItemState5.getAnimation().resolve4(flag2 ? 1.0 : 0.0, 0.24F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
               float floatValue19 = notificationsHudItemState5.getAnimation().measure3();
               if (!(floatValue19 <= 0.01F)) {
                  float floatValue20 = measure(notificationsHudItemState5, floatValue4);
                   float floatValue21 = hudEditorRendererState2.floatValue - floatValue20 * 0.5F;
                  this.invoke9(renderManager2, floatValue21, floatValue18, floatValue20, floatValue2, floatValue19, notificationsHudItemState5, floatValue4);
                  floatValue18 += (floatValue2 + floatValue3) * floatValue19;
               }
            }

            HudEditorRenderer.getINSTANCE().invoke6(hudEditorRendererState2);
            HudSettingsRenderer.invoke2(
               renderManager2,
               this,
               hudEditorRendererState2,
               HudEditorRenderer.getINSTANCE(),
               MinecraftAccessor.a_.getWindow().getScaledWidth(),
               MinecraftAccessor.a_.getWindow().getScaledHeight()
            );
         }
      }
   }

   private HudEditorRenderer.HudEditorRendererState resolve(float f, float g, float h, int i, int j) {
      if (!Float.isFinite(floatValue)) {
         HudEditorRenderer.HudEditorRendererData2 hudEditorRendererData2 = HudEditorRenderer.getINSTANCE().getValuesByKey2().get("HUD_Notifications");
         if (hudEditorRendererData2 != null && i > 0) {
            floatValue = hudEditorRendererData2.nx() * i + 110.0F;
         } else {
            floatValue = i * 0.5F;
         }
      }

      float floatValue22 = floatValue - 110.0F;
      HudEditorRenderer.HudEditorRendererState hudEditorRendererState3 = HudEditorRenderer.getINSTANCE().resolve("HUD_Notifications", floatValue22, h, 220.0F, f > 0.0F ? f : g);
      floatValue = hudEditorRendererState3.floatValue + hudEditorRendererState3.floatValue3 * 0.5F;
      return hudEditorRendererState3;
   }

   private static float measure(NotificationsHud.NotificationsHudItemState notificationsHudItemState6, float f) {
      float floatValue23 = TextMeasureCache.resolve(FontRegistry.fontObject, notificationsHudItemState6.getText2(), f).floatValue;
      return notificationsHudItemState6.isFlag() ? floatValue23 + 20.0F + 5.0F + 40.0F : floatValue23 + 20.0F + 5.0F + 28.0F;
   }

   private void invoke9(RenderManager renderManager3, float f, float g, float h, float i, float j, NotificationsHud.NotificationsHudItemState notificationsHudItemState7, float k) {
      float floatValue24 = this.prozrachnost.getValue() * j;
      int intValue9 = (int)(255.0F * floatValue24);
      int intValue10 = this.compute(floatValue24);
      int intValue11 = this.compute5(floatValue24);
      int intValue12 = this.compute6(floatValue24);
      int intValue13 = this.compute8(floatValue24);
      float floatValue25 = 14.0F;
      float floatValue26 = 0.8F + 0.2F * j;
      renderManager3.invoke62(floatValue26, f + h / 2.0F, g + i / 2.0F);
      boolean flag3 = false ;

      try {
         flag3 = true;
         this.invoke(renderManager3, f, g, h, i, floatValue25, floatValue24);
         renderManager3.invoke24(f, g, Math.max(1.0F, h), Math.max(1.0F, i), floatValue25, floatValue25, floatValue25, floatValue25);

         try {
            if (notificationsHudItemState7.isFlag()) {
               notificationsHudItemState7.getAnimation2().check();
               notificationsHudItemState7.getAnimation2().resolve4(notificationsHudItemState7.isFlag2() ? 1.0 : 0.0, 0.15F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
               float floatValue27 = notificationsHudItemState7.getAnimation2().measure3();
               float floatValue28 = f + 10.0F;
               float floatValue29 = f + h - 10.0F;
               float floatValue30 = Math.max(0.0F, floatValue29 - floatValue28);
               float floatValue31 = Math.min(40.0F, floatValue30);
               float floatValue32 = Math.min(22.0F, Math.max(8.0F, i - 8.0F));
               float floatValue33 = floatValue29 - floatValue31;
               float floatValue34 = g + i / 2.0F - floatValue32 / 2.0F;
               if (floatValue31 >= 8.0F) {
                  if (!this.check9() && !this.check10()) {
                     if (!this.check15(floatValue33, floatValue34, floatValue31, floatValue32, floatValue32 / 2.0F, true, floatValue24, 2)) {
                        renderManager3.invoke5(floatValue33, floatValue34, floatValue31, floatValue32, floatValue32 / 2.0F, intValue10);
                     }
                  } else {
                     this.invoke2(renderManager3, floatValue33, floatValue34, floatValue31, floatValue32, floatValue32 / 2.0F, floatValue24);
                  }

                  if (floatValue27 > 0.01F) {
                     float floatValue35 = 3.0F;
                     float floatValue36 = Math.max(1.0F, floatValue31 - floatValue35 * 2.0F);
                     float floatValue37 = Math.max(1.0F, floatValue32 - floatValue35 * 2.0F);
                     float floatValue38 = Math.max(Math.min(floatValue37, floatValue36), floatValue36 * floatValue27);
                     float floatValue39 = floatValue37 / 2.0F;
                     renderManager3.invoke24(floatValue33 + floatValue35, floatValue34 + floatValue35, floatValue36, floatValue37, floatValue39, floatValue39, floatValue39, floatValue39);
                     renderManager3.invoke37(floatValue33 + floatValue35, floatValue34 + floatValue35, floatValue38, floatValue37, floatValue39, this.compute9(floatValue24), this.compute10(floatValue24));
                     renderManager3.invoke25();
                  }

                  float floatValue40 = Math.max(1.0F, Math.min(floatValue32 - 4.0F, floatValue31 - 4.0F) / 2.0F);
                  float floatValue41 = floatValue33 + 2.0F + floatValue40 + Math.max(0.0F, floatValue31 - floatValue32) * floatValue27;
                  float floatValue42 = floatValue34 + floatValue32 / 2.0F;
                  boolean flag4 = HudEditorRenderer.getINSTANCE().isFlag3()
                     && check2(HudEditorRenderer.getINSTANCE().getFloatValue(), HudEditorRenderer.getINSTANCE().getFloatValue2(), floatValue33, floatValue34, floatValue31, floatValue32);
                  if (!this.check9() && !this.check10()) {
                     if (!this.check15(floatValue41 - floatValue40, floatValue42 - floatValue40, floatValue40 * 2.0F, floatValue40 * 2.0F, floatValue40, flag4, floatValue24, flag4 ? 2 : 1)) {
                        renderManager3.invoke39(floatValue41, floatValue42, floatValue40, 0.0F, 360.0F, ColorUtils.compute43(255, 255, 255, intValue9));
                     }
                  } else {
                     this.invoke2(renderManager3, floatValue41 - floatValue40, floatValue42 - floatValue40, floatValue40 * 2.0F, floatValue40 * 2.0F, floatValue40, floatValue24);
                  }
               }

               float floatValue43 = Math.max(0.0F, floatValue33 - 5.0F - floatValue28);
               String text6 = resolve2(notificationsHudItemState7.getText2(), k, floatValue43);
               if (!text6.isEmpty()) {
                  renderManager3.invoke69(FontRegistry.fontObject, floatValue28, measure2(g, i), k, text6, intValue12);
               }
            } else {
               float floatValue44 = f + 10.0F;
               float floatValue45 = g + i * 0.5F;
               if (notificationsHudItemState7.check()) {
                  float floatValue46 = Math.max(0.65F, (i - 10.0F) / 20.0F);
                  float floatValue47 = 16.0F * floatValue46;
                  float floatValue48 = floatValue44 + (28.0F - floatValue47) * 0.5F;
                  float floatValue49 = floatValue45 - floatValue47 * 0.5F;
                  renderManager3.invoke20();
                  ItemRenderUtil.invoke3(renderManager3, notificationsHudItemState7.getItemStack(), floatValue48, floatValue49, floatValue46, notificationsHudItemState7.getIntValue(), false, 0);
               } else if (notificationsHudItemState7.check2()) {
                  float floatValue50 = Math.max(14.0F, (i - 10.0F) * 0.62F);
                  float floatValue51 = floatValue44 + (28.0F - floatValue50) * 0.5F;
                  float floatValue52 = floatValue45 - floatValue50 * 0.5F;
                  invoke16(renderManager3, notificationsHudItemState7.getIdentifier(), floatValue51, floatValue52, floatValue50, floatValue24);
               } else {
                  int intValue14 = ColorUtils.compute2(intValue13, (int)(ColorUtils.compute4(intValue13) * j));
                  String text7 = notificationsHudItemState7.getText();
                  float floatValue53 = measure2(g, i);
                  if (text7.contains("on")) {
                     renderManager3.invoke69(FontRegistry.fontObject5, floatValue44, floatValue53, 28.0F, "n", intValue14);
                  } else if (text7.contains("off")) {
                     renderManager3.invoke69(FontRegistry.fontObject5, floatValue44, floatValue53, 28.0F, "l", intValue14);
                  } else if (text7.contains("warn") || text7.contains("gg")) {
                     renderManager3.invoke69(FontRegistry.fontObject6, floatValue44, floatValue53 - 2.0F, 24.0F, text7.contains("warn") ? "i" : "y", intValue14);
                  } else if (text7.contains("cfg")) {
                     renderManager3.invoke69(FontRegistry.fontObject3, floatValue44, floatValue53 - 2.0F, 22.0F, "G", intValue14);
                  } else {
                     renderManager3.invoke69(FontRegistry.fontObject3, floatValue44, floatValue53, 28.0F, text7, intValue14);
                  }
               }

               float floatValue54 = floatValue44 + 28.0F + 5.0F;
               float floatValue55 = Math.max(0.0F, f + h - 10.0F - floatValue54);
               String text8 = resolve2(notificationsHudItemState7.getText2(), k, floatValue55);
               int intValue15 = Math.min(notificationsHudItemState7.getIntValue2(), text8.length());
               invoke10(renderManager3, floatValue54, measure2(g, i), k, text8, intValue12, intValue15, notificationsHudItemState7.getIntValue3(), floatValue24);
            }
         } finally {
            renderManager3.invoke25();
         }

         flag3 = false;
      } finally {
         if (flag3) {
            renderManager3.invoke57();
         }
      }

      renderManager3.invoke57();
   }

   private static float measure2(float f, float g) {
      return f + g * 0.5F + 5.0F;
   }

   private static void invoke10(RenderManager renderManager4, float f, float g, float h, String string, int i, int j, int k, float l) {
      if (string != null && !string.isEmpty()) {
         if (k != 0 && j > 0 && j < string.length()) {
            String text9 = string.substring(0, j);
            String text10 = string.substring(j);
            if (!text9.isEmpty()) {
               renderManager4.invoke69(FontRegistry.fontObject, f, g, h, text9, i);
            }

            float floatValue56 = text9.isEmpty() ? 0.0F : TextMeasureCache.resolve(FontRegistry.fontObject, text9, h).floatValue;
            int intValue16 = ColorUtils.compute2(k, Math.round(ColorUtils.compute4(k) * l));
            if (!text10.isEmpty()) {
               renderManager4.invoke69(FontRegistry.fontObject, f + floatValue56, g, h, text10, intValue16);
            }
         } else {
            renderManager4.invoke69(FontRegistry.fontObject, f, g, h, string, i);
         }
      }
   }

   private static String resolve2(String string, float f, float g) {
      if (string != null && !string.isEmpty() && !(g <= 1.0F)) {
         if (TextMeasureCache.resolve(FontRegistry.fontObject, string, f).floatValue <= g) {
            return string;
         } else {
            String text11 = "...";
            if (TextMeasureCache.resolve(FontRegistry.fontObject, text11, f).floatValue > g) {
               return "";
            } else {
               for (int intValue17 = string.length(); intValue17 > 0; intValue17--) {
                  String text12 = string.substring(0, intValue17).trim() + text11;
                  if (TextMeasureCache.resolve(FontRegistry.fontObject, text12, f).floatValue <= g) {
                     return text12;
                  }
               }

               return text11;
            }
         }
      } else {
         return "";
      }
   }

   private static boolean check2(float f, float g, float h, float i, float j, float k) {
      return f >= h && f <= h + j && g >= i && g <= i + k;
   }

   private static void invoke11() {
      HashSet hashSet = new HashSet();

      for (StatusEffectInstance statusEffectInstance : MinecraftAccessor.a_.player.getStatusEffects()) {
         if (!Removals.check3(statusEffectInstance.getEffectType())) {
            String text13 = statusEffectInstance.getEffectType().getIdAsString();
            hashSet.add(text13);
            NotificationsHud.NotificationsHudState notificationsHudState = VALUES_BY_KEY.computeIfAbsent(text13, string -> new NotificationsHud.NotificationsHudState());
            notificationsHudState.text = resolve3(I18n.translate(statusEffectInstance.getTranslationKey(), new Object[0]));
            if (statusEffectInstance.isInfinite()) {
               notificationsHudState.invoke();
            } else {
               int intValue18 = Math.max(0, (int)Math.ceil(statusEffectInstance.getDuration() / 20.0));
               invoke12(text13, notificationsHudState.text, intValue18, notificationsHudState);
            }
         }
      }

      Iterator iterator = VALUES_BY_KEY.entrySet().iterator();

      while (iterator.hasNext()) {
         Entry entry = (Entry)iterator.next();
         if (!hashSet.contains(entry.getKey())) {
            String text14 = ((NotificationsHud.NotificationsHudState)entry.getValue()).text;
            if (text14 != null && !text14.isEmpty()) {
               invoke5((String)entry.getKey(), text14);
            }

            iterator.remove();
         }
      }
   }

   private static void invoke12(String string, String string2, int i, NotificationsHud.NotificationsHudState notificationsHudState2) {
      if (i > 5) {
         notificationsHudState2.invoke();
      } else {
         if (i >= 1 && !notificationsHudState2.flag) {
            notificationsHudState2.flag = true;
            invoke4(string, string2);
         }
      }
   }

   private static void invoke13() {
      for (int intValue19 = 0; intValue19 < EQUIPMENT_SLOTS.length; intValue19++) {
         ItemStack itemStack2 = MinecraftAccessor.a_.player.getEquippedStack(EQUIPMENT_SLOTS[intValue19]);
         if (itemStack2 != null && !itemStack2.isEmpty() && itemStack2.isDamageable()) {
            if (ITEMS_2[intValue19] != itemStack2.getItem()) {
               ITEMS_2[intValue19] = itemStack2.getItem();
               INTS[intValue19] = 100;
            }

            int intValue20 = itemStack2.getMaxDamage();
            if (intValue20 > 0) {
               int intValue21 = intValue20 - itemStack2.getDamage();
               int intValue22 = (int)Math.floor(intValue21 * 100.0 / intValue20);
               if (intValue22 > 30) {
                  INTS[intValue19] = 100;
               } else {
                  int intValue23 = intValue22 <= 10 ? 10 : (intValue22 <= 20 ? 20 : 30);
                  if (intValue23 < INTS[intValue19]) {
                     INTS[intValue19] = intValue23;
                     invoke6(itemStack2, intValue22);
                  }
               }
            }
         } else {
            INTS[intValue19] = 100;
            ITEMS_2[intValue19] = null;
         }
      }
   }

   private static void invoke14() {
      for (int intValue24 = 0; intValue24 < INTS.length; intValue24++) {
         INTS[intValue24] = 100;
         ITEMS_2[intValue24] = null;
      }
   }

   private static void invoke15() {
      float floatValue57 = MinecraftAccessor.a_.player.getHealth() + MinecraftAccessor.a_.player.getAbsorptionAmount();
      if (floatValue57 <= 8.0F && floatValue57 > 0.0F && !flag) {
         if (check("Низкое HP")) {
            ITEMS.add(new NotificationsHud.NotificationsHudItemState(IDENTIFIER, "Низкое HP » " + String.format("%.1f", floatValue57), 2500L));
         }

         flag = true;
      } else {
         if (floatValue57 > 10.0F) {
            flag = false;
         }
      }
   }

   private static String resolve3(String string) {
      return string != null && !string.isEmpty() ? string.replaceAll("(?i)\\u0412?\\u00A7[0-9A-FK-OR]", "").replace("§", "").replace(' ', ' ').trim() : "";
   }

   private static int compute(String string) {
      if (string != null && !string.isEmpty()) {
         Integer integerValue = null;

         for (int intValue25 = 0; intValue25 < string.length(); intValue25++) {
            char character = string.charAt(intValue25);
            if (character == 167 || character == '&') {
               if (intValue25 + 1 >= string.length()) {
                  break;
               }

               if ((string.charAt(intValue25 + 1) == 'x' || string.charAt(intValue25 + 1) == 'X') && intValue25 + 13 < string.length()) {
                  Integer integerValue2 = resolve4(string, intValue25 + 2);
                  if (integerValue2 != null) {
                     integerValue = integerValue2;
                  }

                  intValue25 += 13;
               } else {
                  Formatting formatting = Formatting.byCode(string.charAt(intValue25 + 1));
                  if (formatting != null) {
                     if (formatting == Formatting.RESET) {
                        integerValue = null;
                     } else {
                        Integer integerValue3 = formatting.getColorValue();
                        if (integerValue3 != null) {
                           integerValue = 0xFF000000 | integerValue3;
                        }
                     }
                  }
               }
            }
         }

         return integerValue == null ? 0 : integerValue;
      } else {
         return 0;
      }
   }

   private static Integer resolve4(String string, int i) {
      int intValue26 = 0;
      int intValue27 = 0;

      for (int intValue28 = i; intValue28 < string.length() && intValue27 < 6; intValue28++) {
         char character2 = string.charAt(intValue28);
         if (character2 == 167 || character2 == '&') {
            if (++intValue28 >= string.length()) {
               return null;
            }

            character2 = string.charAt(intValue28);
         }

         int intValue29 = compute2(character2);
         if (intValue29 < 0) {
            return null;
         }

         intValue26 = intValue26 << 4 | intValue29;
         intValue27++;
      }

      return intValue27 == 6 ? 0xFF000000 | intValue26 : null;
   }

   private static int compute2(char c) {
      if (c >= '0' && c <= '9') {
         return c - 48;
      } else if (c >= 'a' && c <= 'f') {
         return c - 97 + 10;
      } else {
         return c >= 65 && c <= 70 ? c - 65 + 10 : -1;
      }
   }

   private static Identifier resolve5(String string) {
      if (string != null && !string.isEmpty()) {
         int intValue30 = string.indexOf(58);
         String text15 = intValue30 > 0 ? string.substring(0, intValue30) : "minecraft";
         String text16 = intValue30 > 0 && intValue30 + 1 < string.length() ? string.substring(intValue30 + 1) : string;
         return Identifier.of(text15, "textures/mob_effect/" + text16 + ".png");
      } else {
         return Identifier.of("minecraft", "textures/mob_effect/strength.png");
      }
   }

   private static void invoke16(RenderManager renderManager5, Identifier identifier, float f, float g, float h, float i) {
      if (renderManager5 != null && identifier != null && MinecraftAccessor.a_ != null && MinecraftAccessor.a_.getTextureManager() != null) {
         AbstractTexture abstractTexture = MinecraftAccessor.a_.getTextureManager().getTexture(identifier);
         if (abstractTexture != null && abstractTexture.getGlTexture() instanceof GlTexture glTexture && glTexture.getGlId() > 0) {
            renderManager5.invoke20();
            renderManager5.invoke65(i);
            renderManager5.invoke11(glTexture.getGlId(), f, g, h, h, 0.0F, 0.0F, 1.0F, 1.0F);
            renderManager5.invoke66();
         }
      }
   }

   public static class NotificationsHudItemState {
      private boolean flag;
      private String text;
      private String text2;
      private boolean flag2;
      private ItemStack itemStack;
      private int intValue;
      private Identifier identifier;
      int intValue2;
      int intValue3;
      private long timestamp;
      private long timestamp2;
      private Animation animation = new Animation();
      private Animation animation2 = new Animation();

      public NotificationsHudItemState(String string, String string2, long l) {
         this.flag = false;
         this.text = string;
         this.text2 = string2;
         this.timestamp2 = l;
         this.timestamp = System.currentTimeMillis();
      }

      public NotificationsHudItemState(ItemStack itemStack, String string, int i, int j, long l) {
         this.flag = false;
         this.itemStack = itemStack;
         this.intValue = itemStack.hashCode();
         this.text2 = string;
         this.intValue2 = i;
         this.intValue3 = j;
         this.timestamp2 = l;
         this.timestamp = System.currentTimeMillis();
      }

      public NotificationsHudItemState(Identifier identifier, String string, long l) {
         this.flag = false;
         this.identifier = identifier;
         this.text2 = string;
         this.timestamp2 = l;
         this.timestamp = System.currentTimeMillis();
      }

      public boolean check() {
         return this.itemStack != null && !this.itemStack.isEmpty();
      }

      public boolean check2() {
         return this.identifier != null;
      }

      public NotificationsHudItemState(String string, boolean bl, long l) {
         this.flag = true;
         this.text2 = string;
         this.flag2 = bl;
         this.timestamp2 = l;
         this.timestamp = System.currentTimeMillis();
         this.animation2.invoke(bl ? 1.0 : 0.0);
      }

      public boolean check3() {
         return System.currentTimeMillis() - this.timestamp > this.timestamp2;
      }

      @Generated
      public boolean isFlag() {
         return this.flag;
      }

      @Generated
      public String getText() {
         return this.text;
      }

      @Generated
      public String getText2() {
         return this.text2;
      }

      @Generated
      public boolean isFlag2() {
         return this.flag2;
      }

      @Generated
      public ItemStack getItemStack() {
         return this.itemStack;
      }

      @Generated
      public int getIntValue() {
         return this.intValue;
      }

      @Generated
      public Identifier getIdentifier() {
         return this.identifier;
      }

      @Generated
      public int getIntValue2() {
         return this.intValue2;
      }

      @Generated
      public int getIntValue3() {
         return this.intValue3;
      }

      @Generated
      public long getTimestamp() {
         return this.timestamp;
      }

      @Generated
      public long getTimestamp2() {
         return this.timestamp2;
      }

      @Generated
      public Animation getAnimation() {
         return this.animation;
      }

      @Generated
      public Animation getAnimation2() {
         return this.animation2;
      }

      @Generated
      public void setFlag(boolean bl) {
         this.flag = bl;
      }

      @Generated
      public void setText(String string) {
         this.text = string;
      }

      @Generated
      public void setText2(String string) {
         this.text2 = string;
      }

      @Generated
      public void setFlag2(boolean bl) {
         this.flag2 = bl;
      }

      @Generated
      public void setItemStack(ItemStack itemStack) {
         this.itemStack = itemStack;
      }

      @Generated
      public void setIntValue(int i) {
         this.intValue = i;
      }

      @Generated
      public void setIdentifier(Identifier identifier) {
         this.identifier = identifier;
      }

      @Generated
      public void setIntValue2(int i) {
         this.intValue2 = i;
      }

      @Generated
      public void setIntValue3(int i) {
         this.intValue3 = i;
      }

      @Generated
      public void setTimestamp(long l) {
         this.timestamp = l;
      }

      @Generated
      public void setTimestamp2(long l) {
         this.timestamp2 = l;
      }

      @Generated
      public void setAnimation(Animation animation) {
         this.animation = animation;
      }

      @Generated
      public void setAnimation2(Animation animation2) {
         this.animation2 = animation2;
      }

      @Generated
      @Override
      public boolean equals(Object object) {
         if (object == this) {
            return true;
         } else if (!(object instanceof NotificationsHud.NotificationsHudItemState notificationsHudItemState8)) {
            return false;
         } else if (!notificationsHudItemState8.check4(this)) {
            return false;
         } else if (this.isFlag() != notificationsHudItemState8.isFlag()) {
            return false;
         } else if (this.isFlag2() != notificationsHudItemState8.isFlag2()) {
            return false;
         } else if (this.getIntValue() != notificationsHudItemState8.getIntValue()) {
            return false;
         } else if (this.getIntValue2() != notificationsHudItemState8.getIntValue2()) {
            return false;
         } else if (this.getIntValue3() != notificationsHudItemState8.getIntValue3()) {
            return false;
         } else if (this.getTimestamp() != notificationsHudItemState8.getTimestamp()) {
            return false;
         } else if (this.getTimestamp2() != notificationsHudItemState8.getTimestamp2()) {
            return false;
         } else {
            String text17 = this.getText();
            String text18 = notificationsHudItemState8.getText();
            if (text17 == null) {
               if (text18 != null) {
                  return false;
               }
            } else if (!text17.equals(text18)) {
               return false;
            }

            String text19 = this.getText2();
            String text20 = notificationsHudItemState8.getText2();
            if (text19 == null ? text20 == null : text19.equals(text20)) {
               ItemStack itemStack3 = this.getItemStack();
               ItemStack itemStack4 = notificationsHudItemState8.getItemStack();
               if (itemStack3 == null ? itemStack4 == null : itemStack3.equals(itemStack4)) {
                  Identifier identifier2 = this.getIdentifier();
                  Identifier identifier3 = notificationsHudItemState8.getIdentifier();
                  if (identifier2 == null ? identifier3 == null : identifier2.equals(identifier3)) {
                     Animation animation3 = this.getAnimation();
                     Animation animation4 = notificationsHudItemState8.getAnimation();
                     if (animation3 == null ? animation4 == null : animation3.equals(animation4)) {
                        Animation animation5 = this.getAnimation2();
                        Animation animation6 = notificationsHudItemState8.getAnimation2();
                        return animation5 == null ? animation6 == null : animation5.equals(animation6);
                     } else {
                        return false;
                     }
                  } else {
                     return false;
                  }
               } else {
                  return false;
               }
            } else {
               return false;
            }
         }
      }

      @Generated
      protected boolean check4(Object object) {
         return object instanceof NotificationsHud.NotificationsHudItemState;
      }

      @Generated
      @Override
      public int hashCode() {
         byte byteValue = 59;
         int intValue31 = 1;
         intValue31 = intValue31 * 59 + (this.isFlag() ? 79 : 97);
         intValue31 = intValue31 * 59 + (this.isFlag2() ? 79 : 97);
         intValue31 = intValue31 * 59 + this.getIntValue();
         intValue31 = intValue31 * 59 + this.getIntValue2();
         intValue31 = intValue31 * 59 + this.getIntValue3();
         long longValue = this.getTimestamp();
         intValue31 = intValue31 * 59 + (int)(longValue >>> 32 ^ longValue);
         long longValue2 = this.getTimestamp2();
         intValue31 = intValue31 * 59 + (int)(longValue2 >>> 32 ^ longValue2);
         String text21 = this.getText();
         intValue31 = intValue31 * 59 + (text21 == null ? 43 : text21.hashCode());
         String text22 = this.getText2();
         intValue31 = intValue31 * 59 + (text22 == null ? 43 : text22.hashCode());
         ItemStack itemStack5 = this.getItemStack();
         intValue31 = intValue31 * 59 + (itemStack5 == null ? 43 : itemStack5.hashCode());
         Identifier identifier4 = this.getIdentifier();
         intValue31 = intValue31 * 59 + (identifier4 == null ? 43 : identifier4.hashCode());
         Animation animation7 = this.getAnimation();
         intValue31 = intValue31 * 59 + (animation7 == null ? 43 : animation7.hashCode());
         Animation animation8 = this.getAnimation2();
         return intValue31 * 59 + (animation8 == null ? 43 : animation8.hashCode());
      }

      @Generated
      @Override
      public String toString() {
         return "NotificationsHUD.Notification(isToggle="
            + this.isFlag()
            + ", icon="
            + this.getText()
            + ", text="
            + this.getText2()
            + ", toggleState="
            + this.isFlag2()
            + ", itemStack="
            + this.getItemStack()
            + ", itemSeed="
            + this.getIntValue()
            + ", textureIconId="
            + this.getIdentifier()
            + ", highlightStart="
            + this.getIntValue2()
            + ", highlightColor="
            + this.getIntValue3()
            + ", createTime="
            + this.getTimestamp()
            + ", duration="
            + this.getTimestamp2()
            + ", animation="
            + this.getAnimation()
            + ", toggleAnim="
            + this.getAnimation2()
            + ")";
      }
   }

   static final class NotificationsHudState {
      String text = "";
      boolean flag;

      void invoke() {
         this.flag = false;
      }
   }
}
