package ru.metaculture.protection;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.CloudRenderMode;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.GraphicsMode;
import net.minecraft.particle.ParticlesMode;

public enum GraphicsQuality {
   LOW("Низкое"),
   MEDIUM("Баланс"),
   HIGH("Высокое"),
   ULTRA("Ультра"),
   ULTRA_LOW("UltraLow");

   private final String text;

   private GraphicsQuality(String string2) {
      this.text = string2;
   }

   public String getText() {
      return this.text;
   }

   public static boolean isUltraLow() {
      try {
         return MenuModule.resolve() == ULTRA_LOW;
      } catch (Throwable exception) {
         return false;
      }
   }

   public static String[] resolve() {
      GraphicsQuality[] graphicsQualities = values();
      String[] texts = new String[graphicsQualities.length];

      for (int intValue = 0; intValue < graphicsQualities.length; intValue++) {
         texts[intValue] = graphicsQualities[intValue].text;
      }

      return texts;
   }

   public static GraphicsQuality resolve2(int i) {
      GraphicsQuality[] graphicsQualities2 = values();
      int intValue2 = Math.max(0, Math.min(graphicsQualities2.length - 1, i));
      return graphicsQualities2[intValue2];
   }

   public void invoke() {
      boolean ultraLow = this == ULTRA_LOW;
      boolean flag = this == LOW || ultraLow;
      boolean flag2 = this == MEDIUM;
      boolean flag3 = this == HIGH;
      boolean flag4 = this == ULTRA;
      boolean flag5 = flag2 || flag3 || flag4;
      boolean flag6 = flag3 || flag4;
      MenuModule.VOLNY_KLIKA.setValue(flag5);
      MenuModule.VOLNY_TEMY.setValue(flag6);
      MenuModule.UDARNAYA_VOLNA_TEMY.setValue(flag4);
      MenuModule.RAZMYTIE_SKROLLA.setValue(flag6);
      MenuModule.PEREHODY_KART.setValue(flag5);
      MenuModule.PEREHODY_EKRANA.setValue(flag5);
      MenuModule.DREYF_TSVETA_TEMY.setValue(flag6);
      MenuModule.VNUTRENNEE_SVECHENIE.setValue(flag4);
      MenuModule.ZERNO_PLYONKI.setValue(flag6);
      MenuModule.PULSATSIYA_HOTBARA.setValue(flag5);
      MenuModule.ANIMATSII_STATUSOV.setValue(flag5);
      MenuModule.VSPYSHKA_URONA.setValue(flag5);
      MenuModule.PULSATSIYA_REGENERATSII.setValue(flag6);
      MenuModule.TRYASKA_PRI_NIZKOM_ZDOROVE.setValue(flag6);
      MenuModule.SLED_KURSORA_VMENYU.setValue(flag5);
      MenuModule.PARALLAKS_GLAVNOGO_MENYU.setValue(flag5);
      MenuModule.MAKSIMALNYY_BLYUR.invoke(ultraLow ? 8.0F : (flag ? 12.0F : (flag2 ? 26.0F : (flag3 ? 42.0F : 58.0F))));
      MenuModule.IRIDISTSENTNYY_OTLIV.invoke(ultraLow ? 0.1F : (flag ? 0.26F : (flag2 ? 0.46F : (flag3 ? 0.64F : 0.82F))));
      MenuModule.PRITYAZHENIE_KKURSORU.invoke(ultraLow ? 0.0F : (flag ? 0.06F : (flag2 ? 0.16F : (flag3 ? 0.27F : 0.36F))));
      MenuModule.RADIUS_PROZRACHNOSTI_UKURSORA.invoke(ultraLow ? 0.1F : (flag ? 0.18F : (flag2 ? 0.26F : (flag3 ? 0.36F : 0.48F))));
      MenuModule.RAZMER_OSTROVKOV.invoke(ultraLow ? 0.9F : (flag ? 1.05F : (flag2 ? 1.55F : (flag3 ? 2.15F : 2.8F))));
      MenuModule.SKOROST_TECHENIYA.invoke(ultraLow ? 0.0F : (flag ? 0.18F : (flag2 ? 0.46F : (flag3 ? 0.72F : 1.02F))));
      MenuModule.KONTRAST_OSTROVKOV.invoke(ultraLow ? 0.2F : (flag ? 0.32F : (flag2 ? 0.52F : (flag3 ? 0.68F : 0.82F))));
      MenuModule.VINETKA.invoke(ultraLow ? 0.1F : (flag ? 0.2F : (flag2 ? 0.34F : (flag3 ? 0.46F : 0.58F))));
      MenuModule.YARKOST.invoke(ultraLow ? 0.42F : (flag ? 0.48F : (flag2 ? 0.56F : (flag3 ? 0.62F : 0.7F))));
      MenuModule.NASYSCHENNOST.invoke(ultraLow ? 0.2F : (flag ? 0.32F : (flag2 ? 0.48F : (flag3 ? 0.62F : 0.78F))));
      MenuModule.OTKLYUCHIT_BLYUR.setValue(flag);
      MenuModule.UPROSCHYONNYE_TENI_HUD.setValue(flag || flag2);
      MenuModule.BYSTRYE_ANIMATSII.setValue(flag);
      MenuModule.PROPUSKAT_CHASTITSY_KLIENTA.setValue(flag);
      if (ultraLow) {
         invokeMinecraft();
         invokeHud();
         invokeNameTags();
      }
   }

   private static void invokeMinecraft() {
      try {
         MinecraftClient client = MinecraftClient.getInstance();
         if (client == null || client.options == null) {
            return;
         }

         GameOptions options = client.options;
         options.getGraphicsMode().setValue(GraphicsMode.FAST);
         options.getCloudRenderMode().setValue(CloudRenderMode.OFF);
         options.getParticles().setValue(ParticlesMode.MINIMAL);
         options.getEntityShadows().setValue(Boolean.FALSE);
         options.getAo().setValue(Boolean.FALSE);
         options.getViewDistance().setValue(Math.min(4, options.getViewDistance().getValue()));
         options.getSimulationDistance().setValue(Math.min(4, options.getSimulationDistance().getValue()));
         options.getEntityDistanceScaling().setValue(Math.min(0.5, options.getEntityDistanceScaling().getValue()));
         options.getMipmapLevels().setValue(0);
         options.getBiomeBlendRadius().setValue(0);
         options.write();
      } catch (Throwable exception) {
      }
   }

   private static void invokeHud() {
      try {
         if (WildClient.INSTANCE == null || WildClient.INSTANCE.moduleManager == null) {
            return;
         }

         HudModule hudModule = WildClient.INSTANCE.moduleManager.getModule(HudModule.class);
         if (hudModule == null) {
            return;
         }

         invokeHudElement(HudModule.ELEMENTS, "Watermark", true);
         invokeHudElement(HudModule.ELEMENTS, "ArrayList", true);
         invokeHudElement(HudModule.ELEMENTS, "HotKeys", true);
         invokeHudElement(HudModule.ELEMENTS, "Potions", true);
         invokeHudElement(HudModule.ELEMENTS, "TargetHud", true);
         invokeHudElement(HudModule.ELEMENTS, "Armor", true);
         invokeHudElement(HudModule.ELEMENTS, "Notifications", true);
         invokeHudElement(HudModule.ELEMENTS, "PlayerInfo", false);
         invokeHudElement(HudModule.ELEMENTS, "Cool Downs", false);
         invokeHudElement(HudModule.ELEMENTS, "Inventory", false);
         invokeHudElement(HudModule.ELEMENTS, "MediaPlayer", false);
         invokeHudElement(HudModule.ELEMENTS, "Brew Monitor", false);
         invokeHudElement(HudModule.ELEMENTS, "Server Helper", false);
         invokeHudElement(HudModule.ELEMENTS, "HotBar", false);
         invokeHudElement(HudModule.ELEMENTS, "AutoBuy Info", false);
         invokeHudElement(HudModule.ELEMENTS, "AI Status", false);
      } catch (Throwable exception2) {
      }
   }

   private static void invokeHudElement(GroupSetting groupSetting, String string, boolean bl) {
      if (groupSetting != null && groupSetting.options != null) {
         for (BooleanSetting booleanSetting : groupSetting.options) {
            if (booleanSetting != null && string.equals(booleanSetting.name)) {
               booleanSetting.setValue(bl);
               return;
            }
         }
      }
   }

   private static void invokeNameTags() {
      try {
         if (WildClient.INSTANCE == null || WildClient.INSTANCE.moduleManager == null) {
            return;
         }

         NameTags nameTags = WildClient.INSTANCE.moduleManager.getModule(NameTags.class);
         if (nameTags == null) {
            return;
         }

         invokeMode(nameTags.rezhimOtobrazheniya, "Legacy");
         nameTags.bronya.setValue(false);
         nameTags.pravayaRuka.setValue(false);
         nameTags.levayaRuka.setValue(false);
         nameTags.effekty.setValue(false);
         nameTags.polosaHp.setValue(false);
         nameTags.nevidimki.setValue(false);
         nameTags.infoPriNavodke.setValue(false);
         nameTags.pokazyvatGolovu.setValue(false);
         nameTags.podsvetkaPredmetov.setValue(false);
         nameTags.tenPlashek.setValue(false);
         nameTags.gradientTeksta.setValue(false);
         nameTags.razmer.invoke(1.0F);
         nameTags.radiusDetaley.invoke(4.0F);
         invokeMode(nameTags.rezhimObvodki, "Не рендерить");
         invokeMode(nameTags.stilistika, "Тёмный");
         invokeHudElement(nameTags.tseli, "Игроки", true);
         invokeHudElement(nameTags.tseli, "Голые", true);
         invokeHudElement(nameTags.tseli, "Мобы", false);
         invokeHudElement(nameTags.tseli, "Животные", false);
         invokeHudElement(nameTags.tseli, "Предметы", false);
      } catch (Throwable exception3) {
      }
   }

   private static void invokeMode(ModeSetting modeSetting, String string) {
      if (modeSetting != null && modeSetting.options != null && modeSetting.options.contains(string)) {
         modeSetting.value = string;
         modeSetting.selectedIndex = modeSetting.options.indexOf(string);
      }
   }
}
