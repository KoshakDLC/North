package ru.metaculture.protection;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.HungerManager;

public final class HotbarStatusRenderer {
   private static final HotbarStatusRenderer INSTANCE = new HotbarStatusRenderer();
   private final Animation animation = new Animation();
   private final Animation animation2 = new Animation();
   private final Animation animation3 = new Animation();
   private final Animation animation4 = new Animation();
   private final Animation animation5 = new Animation();
   private final Animation animation6 = new Animation();
   private final Animation animation7 = new Animation();
   private float floatValue = -1.0F;
   private long timestamp;
   private boolean flag;

   public static HotbarStatusRenderer getINSTANCE() {
      return INSTANCE;
   }

   public void invoke(RenderManager renderManager, HudElement hudElement, float f, float g, float h, float i, float j, float k) {
      MinecraftClient client = MinecraftClient.getInstance();
      if (client != null && client.player != null && client.world != null) {
         ClientPlayerEntity clientPlayerEntity = client.player;
         boolean flag = HotbarHud.getINSTANCE().elementyStatusa.isEnabled("Здоровье");
         boolean flag2 = HotbarHud.getINSTANCE().elementyStatusa.isEnabled("Голод");
         boolean flag3 = HotbarHud.getINSTANCE().elementyStatusa.isEnabled("Броня");
         boolean flag4 = HotbarHud.getINSTANCE().elementyStatusa.isEnabled("Воздух");
         boolean flag5 = HotbarHud.getINSTANCE().elementyStatusa.isEnabled("Поглощение");
         if (flag || flag2 || flag3 || flag4) {
            float floatValue;
            try {
               floatValue = (float)clientPlayerEntity.getAttributeValue(EntityAttributes.MAX_HEALTH);
            } catch (Throwable exception) {
               floatValue = 20.0F;
            }

            if (floatValue <= 0.0F || Float.isNaN(floatValue) || Float.isInfinite(floatValue)) {
               floatValue = 20.0F;
            }

            float floatValue2 = 0.0F;
            float floatValue3 = 0.0F;

            try {
               floatValue2 = clientPlayerEntity.getHealth();
               floatValue3 = clientPlayerEntity.getAbsorptionAmount();
            } catch (Throwable exception2) {
            }

            float floatValue4 = Math.max(0.0F, Math.min(floatValue, floatValue2));
            float floatValue5 = Math.max(0.0F, floatValue3);
            float floatValue6 = 20.0F;
            HungerManager hungerManager = clientPlayerEntity.getHungerManager();
            float floatValue7 = hungerManager == null ? 20.0F : Math.max(0.0F, Math.min(floatValue6, (float)hungerManager.getFoodLevel()));
            int intValue = 0;

            try {
               intValue = clientPlayerEntity.getArmor();
            } catch (Throwable exception3) {
            }

            float floatValue8 = 20.0F;
            int intValue2 = 0;
            int intValue3 = 300;

            try {
               intValue2 = clientPlayerEntity.getAir();
               int intValue4 = clientPlayerEntity.getMaxAir();
               if (intValue4 > 0) {
                  intValue3 = intValue4;
               }
            } catch (Throwable exception4) {
            }

            boolean flag6 = intValue2 >= intValue3;
            float floatValue9 = Math.min(i, j);
            if (!this.flag) {
               this.animation.invoke(floatValue4);
               this.animation2.invoke(floatValue7);
               this.animation3.invoke(intValue);
               this.animation4.invoke(floatValue5);
               this.animation5.invoke(intValue2);
               this.animation7.invoke(intValue > 0 ? 1.0 : 0.0);
               this.animation6.invoke(flag6 ? 0.0 : 1.0);
               this.floatValue = floatValue4;
               this.flag = true;
            }

            if (this.floatValue >= 0.0F && floatValue4 < this.floatValue - 0.05F) {
               this.timestamp = System.currentTimeMillis();
            }

            this.floatValue = floatValue4;
            this.animation.check();
            this.animation.resolve4(floatValue4, 0.22, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_6, true);
            this.animation2.check();
            this.animation2.resolve4(floatValue7, 0.22, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_6, true);
            this.animation3.check();
            this.animation3.resolve4(intValue, 0.22, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_6, true);
            this.animation4.check();
            this.animation4.resolve4(floatValue5, 0.22, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_6, true);
            this.animation5.check();
            this.animation5.resolve4(intValue2, 0.18, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_6, true);
            this.animation7.check();
            this.animation7.resolve4(intValue > 0 ? 1.0 : 0.0, 0.3, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, true);
            this.animation6.check();
            this.animation6.resolve4(flag6 ? 0.0 : 1.0, 0.3, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, true);
            float floatValue10 = 6.0F * floatValue9;
            float floatValue11 = (h - floatValue10) * 0.5F;
            float floatValue12 = 12.0F * floatValue9;
            float floatValue13 = 4.0F * floatValue9;
            float floatValue14 = g - floatValue13 - floatValue12;
            boolean flag7 = flag3 && this.animation7.measure3() > 0.01F || flag4 && this.animation6.measure3() > 0.01F;
            float floatValue15 = floatValue14 - floatValue13 - floatValue12;
            long longValue = System.currentTimeMillis();
            float floatValue16 = 0.0F;
            if (flag && floatValue4 / Math.max(1.0F, floatValue) < 0.2F && floatValue4 > 0.0F && MenuModule.check(MenuModule.TRYASKA_PRI_NIZKOM_ZDOROVE)) {
               floatValue16 = (float)Math.sin(longValue / 90.0) * 1.2F * floatValue9;
            }

            boolean flag8 = clientPlayerEntity.hasStatusEffect(StatusEffects.REGENERATION);
            float floatValue17 = 0.0F;
            if (flag && flag8 && MenuModule.check(MenuModule.PULSATSIYA_REGENERATSII)) {
               floatValue17 = 0.5F + 0.5F * (float)Math.sin(longValue / 230.0);
            }

            float floatValue18 = 0.0F;
            if (flag && MenuModule.check(MenuModule.VSPYSHKA_URONA)) {
               long longValue2 = longValue - this.timestamp;
               if (this.timestamp > 0L && longValue2 < 180L) {
                  floatValue18 = 1.0F - (float)longValue2 / 180.0F;
               }
            }

            float floatValue19 = 0.0F;
            if (flag2 && floatValue7 / floatValue6 < 0.3F && floatValue7 > 0.0F && MenuModule.check(MenuModule.ANIMATSII_STATUSOV)) {
               floatValue19 = 0.4F + 0.6F * (float)Math.sin(longValue / 200.0);
            }

            if (flag) {
               float floatValue20 = this.animation.measure3() / Math.max(1.0F, floatValue);
               float floatValue21 = this.animation4.measure3() / Math.max(1.0F, floatValue);
               int intValue5 = ColorUtils.compute43(255, 90, 96, (int)(255.0F * k));
               int intValue6 = ColorUtils.compute43(220, 36, 50, (int)(255.0F * k));
               if (floatValue17 > 0.0F) {
                  int intValue7 = ColorUtils.compute43(255, 220, 110, (int)(255.0F * k));
                  intValue5 = compute(intValue5, intValue7, floatValue17 * 0.55F);
                  intValue6 = compute(intValue6, intValue7, floatValue17 * 0.55F);
               }

               this.invoke2(renderManager, hudElement, f + floatValue16, floatValue14, floatValue11, floatValue12, floatValue20, 10, intValue5, intValue6, k, floatValue9);
               if (floatValue18 > 0.0F) {
                  int intValue8 = ColorUtils.compute43(255, 250, 250, (int)(220.0F * floatValue18 * k));
                  renderManager.invoke5(f + floatValue16, floatValue14, floatValue11, floatValue12, floatValue12 * 0.45F, intValue8);
               }

               if (flag5 && floatValue21 > 0.001F) {
                  int intValue9 = ColorUtils.compute43(255, 220, 110, (int)(220.0F * k));
                  int intValue10 = ColorUtils.compute43(255, 180, 60, (int)(220.0F * k));
                  this.invoke4(renderManager, hudElement, f + floatValue16, floatValue14, floatValue11, floatValue12, floatValue21, 10, intValue9, intValue10, 0.92F, floatValue9);
               }
            }

            if (flag2) {
               float floatValue22 = this.animation2.measure3() / floatValue6;
               int intValue11 = ColorUtils.compute43(220, 158, 92, (int)(255.0F * k));
               int intValue12 = ColorUtils.compute43(150, 92, 44, (int)(255.0F * k));
               if (floatValue19 > 0.0F) {
                  int intValue13 = ColorUtils.compute43(255, 120, 60, (int)(255.0F * k));
                  intValue11 = compute(intValue11, intValue13, floatValue19 * 0.6F);
                  intValue12 = compute(intValue12, intValue13, floatValue19 * 0.6F);
               }

               float floatValue23 = f + floatValue11 + floatValue10;
               this.invoke2(renderManager, hudElement, floatValue23, floatValue14, floatValue11, floatValue12, floatValue22, 10, intValue11, intValue12, k, floatValue9);
            }

            if (flag7) {
               if (flag3 && this.animation7.measure3() > 0.01F) {
                  float floatValue24 = this.animation7.measure3();
                  float floatValue25 = this.animation3.measure3() / floatValue8;
                  int intValue14 = ColorUtils.compute43(180, 200, 230, (int)(255.0F * k * floatValue24));
                  int intValue15 = ColorUtils.compute43(110, 130, 170, (int)(255.0F * k * floatValue24));
                  this.invoke2(
                     renderManager, hudElement, f, floatValue15 + (1.0F - floatValue24) * floatValue12 * 0.5F, floatValue11, floatValue12, floatValue25, 10, intValue14, intValue15, k * floatValue24, floatValue9
                  );
               }

               if (flag4 && this.animation6.measure3() > 0.01F) {
                  float floatValue26 = this.animation6.measure3();
                  float floatValue27 = this.animation5.measure3() / Math.max(1.0F, (float)intValue3);
                  int intValue16 = ColorUtils.compute43(120, 200, 255, (int)(255.0F * k * floatValue26));
                  int intValue17 = ColorUtils.compute43(60, 130, 220, (int)(255.0F * k * floatValue26));
                  float floatValue28 = f + floatValue11 + floatValue10;
                  this.invoke2(
                     renderManager, hudElement, floatValue28, floatValue15 + (1.0F - floatValue26) * floatValue12 * 0.5F, floatValue11, floatValue12, floatValue27, 10, intValue16, intValue17, k * floatValue26, floatValue9
                  );
               }
            }
         }
      }
   }

   private void invoke2(
      RenderManager renderManager2, HudElement hudElement2, float f, float g, float h, float i, float j, int k, int l, int m, float n, float o
   ) {
      j = Math.max(0.0F, Math.min(1.0F, j));
      float floatValue29 = i * 0.45F;
      if (hudElement2.check8()) {
         this.invoke3(renderManager2, hudElement2, f, g, h, i, j, k, l, m, n, o);
      } else {
         if (hudElement2.check9() || hudElement2.check10()) {
            hudElement2.invoke2(renderManager2, f, g, h, i, floatValue29, n);
         } else if (hudElement2.check()) {
            renderManager2.invoke41(f, g, h, i, floatValue29, (hudElement2.check17() ? 6.0F : 4.0F) * o, 1.0F, hudElement2.compute17(n));
            int intValue18 = hudElement2.compute(n);
            renderManager2.invoke5(f, g, h, i, floatValue29, intValue18);
            if (hudElement2.check2()) {
               renderManager2.invoke28(f, g, h, i, floatValue29, hudElement2.compute5(n), hudElement2.measure2());
            }
         } else {
            int intValue19 = hudElement2.compute(n);
            renderManager2.invoke5(f, g, h, i, floatValue29, intValue19);
            if (hudElement2.check2()) {
               renderManager2.invoke28(f, g, h, i, floatValue29, hudElement2.compute5(n), hudElement2.measure2());
            }
         }

         float floatValue30 = 1.5F * o;
         float floatValue31 = (h - floatValue30 * (k - 1)) / k;
         float floatValue32 = i - 4.0F * o;
         float floatValue33 = g + (i - floatValue32) * 0.5F;
         float floatValue34 = floatValue32 * 0.4F;
         float floatValue35 = j * k;

         for (int intValue20 = 0; intValue20 < k; intValue20++) {
            float floatValue36 = f + intValue20 * (floatValue31 + floatValue30);
            float floatValue37 = Math.max(0.0F, Math.min(1.0F, floatValue35 - intValue20));
            int intValue21 = hudElement2.compute2(hudElement2.measure(n));
            renderManager2.invoke5(floatValue36, floatValue33, floatValue31, floatValue32, floatValue34, intValue21);
            if (floatValue37 > 0.01F) {
               float floatValue38 = floatValue31 * floatValue37;
               renderManager2.invoke34(floatValue36, floatValue33, floatValue38, floatValue32, floatValue34, m, l);
            }
         }

         renderManager2.invoke20();
      }
   }

   private void invoke3(
      RenderManager renderManager3, HudElement hudElement3, float f, float g, float h, float i, float j, int k, int l, int m, float n, float o
   ) {
      float floatValue39 = i * 0.5F;
      hudElement3.invoke2(renderManager3, f, g, h, i, floatValue39, n);
      float floatValue40 = Math.max(1.5F * o, 1.0F);
      float floatValue41 = f + floatValue40;
      float floatValue42 = g + floatValue40;
      float floatValue43 = Math.max(1.0F, h - floatValue40 * 2.0F);
      float floatValue44 = Math.max(1.0F, i - floatValue40 * 2.0F);
      float floatValue45 = floatValue43 * j;
      float floatValue46 = floatValue44 * 0.5F;
      if (floatValue45 > 0.5F) {
         renderManager3.invoke24(floatValue41, floatValue42, floatValue43, floatValue44, floatValue46, floatValue46, floatValue46, floatValue46);
         renderManager3.invoke34(floatValue41, floatValue42, floatValue45, floatValue44, floatValue46, m, l);
         renderManager3.invoke5(
            floatValue41 + floatValue46 * 0.6F,
            floatValue42 + floatValue44 * 0.16F,
            Math.max(0.0F, floatValue45 - floatValue46),
            Math.max(1.0F, floatValue44 * 0.22F),
            floatValue44 * 0.11F,
            ColorUtils.compute43(255, 255, 255, (int)(48.0F * n))
         );
         renderManager3.invoke25();
      }

      float floatValue47 = floatValue43 / Math.max(1, k);
      int intValue22 = ColorUtils.compute2(hudElement3.compute9(1.0F), (int)(36.0F * n));

      for (int intValue23 = 1; intValue23 < k; intValue23++) {
         float floatValue48 = floatValue41 + floatValue47 * intValue23;
         renderManager3.invoke5(floatValue48 - 0.35F * o, floatValue42 + floatValue44 * 0.18F, 0.7F * o, floatValue44 * 0.64F, 0.35F * o, intValue22);
      }

      renderManager3.invoke20();
   }

   private void invoke4(
      RenderManager renderManager4, HudElement hudElement4, float f, float g, float h, float i, float j, int k, int l, int m, float n, float o
   ) {
      j = Math.max(0.0F, Math.min(1.0F, j));
      if (hudElement4.check8()) {
         float floatValue49 = Math.max(2.6F * o, 1.5F);
         float floatValue50 = f + floatValue49;
         float floatValue51 = g + floatValue49;
         float floatValue52 = Math.max(1.0F, h - floatValue49 * 2.0F);
         float floatValue53 = Math.max(1.0F, i - floatValue49 * 2.0F);
         float floatValue54 = floatValue52 * j;
         if (floatValue54 > 0.5F) {
            renderManager4.invoke24(floatValue50, floatValue51, floatValue52, floatValue53, floatValue53 * 0.5F, floatValue53 * 0.5F, floatValue53 * 0.5F, floatValue53 * 0.5F);
            int intValue24 = ColorUtils.compute2(l, (int)(ColorUtils.compute4(l) * n));
            int intValue25 = ColorUtils.compute2(m, (int)(ColorUtils.compute4(m) * n));
            renderManager4.invoke34(floatValue50, floatValue51, floatValue54, floatValue53, floatValue53 * 0.5F, intValue25, intValue24);
            renderManager4.invoke25();
         }

         renderManager4.invoke20();
      } else {
         float floatValue55 = 1.5F * o;
         float floatValue56 = (h - floatValue55 * (k - 1)) / k;
         float floatValue57 = i - 4.0F * o;
         float floatValue58 = g + (i - floatValue57) * 0.5F;
         float floatValue59 = floatValue57 * 0.4F;
         float floatValue60 = j * k;

         for (int intValue26 = 0; intValue26 < k; intValue26++) {
            float floatValue61 = Math.max(0.0F, Math.min(1.0F, floatValue60 - intValue26));
            if (!(floatValue61 <= 0.01F)) {
               float floatValue62 = f + intValue26 * (floatValue56 + floatValue55);
               int intValue27 = ColorUtils.compute2(l, (int)(ColorUtils.compute4(l) * n));
               int intValue28 = ColorUtils.compute2(m, (int)(ColorUtils.compute4(m) * n));
               renderManager4.invoke34(floatValue62, floatValue58, floatValue56 * floatValue61, floatValue57, floatValue59, intValue28, intValue27);
            }
         }

         renderManager4.invoke20();
      }
   }

   private static int compute(int i, int j, float f) {
      f = Math.max(0.0F, Math.min(1.0F, f));
      int intValue29 = i >>> 24 & 0xFF;
      int intValue30 = i >>> 16 & 0xFF;
      int intValue31 = i >>> 8 & 0xFF;
      int intValue32 = i & 0xFF;
      int intValue33 = j >>> 24 & 0xFF;
      int intValue34 = j >>> 16 & 0xFF;
      int intValue35 = j >>> 8 & 0xFF;
      int intValue36 = j & 0xFF;
      int intValue37 = Math.round(intValue29 + (intValue33 - intValue29) * f);
      int intValue38 = Math.round(intValue30 + (intValue34 - intValue30) * f);
      int intValue39 = Math.round(intValue31 + (intValue35 - intValue31) * f);
      int intValue40 = Math.round(intValue32 + (intValue36 - intValue32) * f);
      return (intValue37 & 0xFF) << 24 | (intValue38 & 0xFF) << 16 | (intValue39 & 0xFF) << 8 | intValue40 & 0xFF;
   }
}
