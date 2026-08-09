package ru.metaculture.protection;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

@HudElementInfo(
   resolve = "ArmorHUD",
   resolve2 = "w"
)
public final class ArmorHud extends HudElement {
   private static final ArmorHud INSTANCE = new ArmorHud();
   private static final Animation ANIMATION = new Animation();
   private static final Animation ANIMATION_2 = new Animation();
   private static final Animation ANIMATION_3 = new Animation();
   private static final ItemStack[] ITEM_STACKS = new ItemStack[4];
   private static final HudMetricUtils[] HUD_METRIC_UTILS_VALUES = new HudMetricUtils[4];
   private final BooleanSetting pokazyvatVProtsentah = new BooleanSetting("Показывать в процентах", true);
   private final ModeSetting orientatsiya = new ModeSetting("Ориентация", "Горизонтально", "Горизонтально", "Вертикально");

   private ArmorHud() {
      this.invoke(this.pokazyvatVProtsentah);
      this.invoke(this.orientatsiya);
      ru.metaculture.protection.HudPresetManager.invoke2(this);
   }

   public static ArmorHud getINSTANCE() {
      return INSTANCE;
   }

   public static void invoke(RenderManager renderManager, DrawContext drawContext) {
      INSTANCE.invoke2(renderManager, drawContext);
   }

   public void invoke2(RenderManager renderManager2, DrawContext drawContext) {
      if (MinecraftAccessor.a_.player != null) {
         ITEM_STACKS[0] = MinecraftAccessor.a_.player.getEquippedStack(EquipmentSlot.HEAD);
         ITEM_STACKS[1] = MinecraftAccessor.a_.player.getEquippedStack(EquipmentSlot.CHEST);
         ITEM_STACKS[2] = MinecraftAccessor.a_.player.getEquippedStack(EquipmentSlot.LEGS);
         ITEM_STACKS[3] = MinecraftAccessor.a_.player.getEquippedStack(EquipmentSlot.FEET);
         int intValue = 0;

         for (int intValue2 = 0; intValue2 < 4; intValue2++) {
            ItemStack itemStack = ITEM_STACKS[intValue2];
            if (itemStack != null && !itemStack.isEmpty()) {
               ITEM_STACKS[intValue++] = itemStack;
            }
         }

         boolean flag = intValue > 0;
         boolean flag2 = flag || MinecraftAccessor.a_.currentScreen instanceof ChatScreen;
         ANIMATION.check();
         ANIMATION.resolve4(flag2 ? 1.0 : 0.0, 0.22F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
         float floatValue = ANIMATION.measure3();
         if (!(floatValue <= 0.01F)) {
            float floatValue2 = MinecraftAccessor.a_.getWindow().getFramebufferWidth();
            float floatValue3 = MinecraftAccessor.a_.getWindow().getFramebufferHeight();
            float floatValue4 = 7.0F;
            boolean flag3 = this.orientatsiya.is("Вертикально");
            float floatValue5 = flag3 ? 56.0F : 42.0F;
            float floatValue6 = 54.0F;
            float floatValue7 = 5.0F;
            int intValue3 = flag ? intValue : 4;
            float floatValue8 = flag3 ? floatValue5 : intValue3 * floatValue5 + (intValue3 - 1) * floatValue7;
            float floatValue9 = flag3 ? intValue3 * floatValue6 + (intValue3 - 1) * floatValue7 : floatValue6;
            float floatValue10 = floatValue8 + floatValue4 * 2.0F;
            float floatValue11 = floatValue9 + floatValue4 * 2.0F;
            ANIMATION_2.check();
            ANIMATION_3.check();
            ANIMATION_2.resolve4(floatValue10, 0.18F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
            ANIMATION_3.resolve4(floatValue11, 0.18F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
            float floatValue12 = ANIMATION_2.measure3();
            float floatValue13 = ANIMATION_3.measure3();
            float floatValue14 = floatValue2 * 0.5F + 96.0F;
            float floatValue15 = floatValue3 - floatValue13 - 12.0F;
            HudEditorRenderer.HudEditorRendererState hudEditorRendererState = HudEditorRenderer.getINSTANCE().resolve("hud_armor", floatValue14, floatValue15, floatValue12, floatValue13);
            float floatValue16 = hudEditorRendererState.floatValue;
            float floatValue17 = hudEditorRendererState.floatValue2;
            float floatValue18 = hudEditorRendererState.floatValue3;
            float floatValue19 = hudEditorRendererState.floatValue4;
            this.invoke3(floatValue16, floatValue17, floatValue18, floatValue19);
            float floatValue20 = floatValue18 / Math.max(1.0F, floatValue12);
            float floatValue21 = floatValue19 / Math.max(1.0F, floatValue13);
            float floatValue22 = Math.min(floatValue20, floatValue21);
            float floatValue23 = floatValue5 * floatValue20;
            float floatValue24 = floatValue6 * floatValue21;
            float floatValue25 = floatValue7 * (flag3 ? floatValue21 : floatValue20);
            float floatValue26 = floatValue8 * floatValue20;
            float floatValue27 = floatValue9 * floatValue21;
            float floatValue28 = floatValue * this.prozrachnost.getValue();
            float floatValue29 = this.measure(floatValue28);
            int intValue4 = (int)(255.0F * floatValue28);
            int intValue5 = this.compute(floatValue28);
            int intValue6 = this.compute5(floatValue28);
            int intValue7 = this.check7() ? ColorUtils.compute43(255, 255, 255, (int)(5.0F * floatValue29)) : this.compute2(floatValue29);
            float floatValue30 = 10.0F;
            this.invoke(renderManager2, floatValue16, floatValue17, floatValue18, floatValue19, floatValue30, floatValue28);
            renderManager2.invoke20();
            renderManager2.invoke24(floatValue16, floatValue17, floatValue18, floatValue19, floatValue30, floatValue30, floatValue30, floatValue30);

            try {
               float floatValue31 = floatValue16 + (floatValue18 - floatValue26) * 0.5F;
               float floatValue32 = floatValue17 + (floatValue19 - floatValue27) * 0.5F;

               for (int intValue8 = 0; intValue8 < intValue3; intValue8++) {
                  float floatValue33 = flag3 ? floatValue31 : floatValue31 + intValue8 * (floatValue23 + floatValue25);
                  float floatValue34 = flag3 ? floatValue32 + intValue8 * (floatValue24 + floatValue25) : floatValue32;
                  if (!this.check8() && !this.check9() && !this.check10()) {
                     renderManager2.invoke5(floatValue33, floatValue34, floatValue23, floatValue24, 6.0F * floatValue22, intValue7);
                  } else {
                     this.invoke2(renderManager2, floatValue33, floatValue34, floatValue23, floatValue24, 6.0F * floatValue22, floatValue28);
                  }
               }

               if (flag) {
                  renderManager2.invoke20();
               }

               for (int intValue9 = 0; intValue9 < intValue3 && flag; intValue9++) {
                  float floatValue35 = flag3 ? floatValue31 : floatValue31 + intValue9 * (floatValue23 + floatValue25);
                  float floatValue36 = flag3 ? floatValue32 + intValue9 * (floatValue24 + floatValue25) : floatValue32;
                  ItemStack itemStack2 = ITEM_STACKS[intValue9];
                  float floatValue37 = 1.5F * floatValue22;
                  float floatValue38 = 16.0F * floatValue37;
                  float floatValue39 = floatValue35 + (floatValue23 - floatValue38) * 0.5F;
                  float floatValue40 = floatValue36 + 8.0F * floatValue21;
                  ItemRenderUtil.invoke3(
                     renderManager2,
                     itemStack2,
                     ItemRenderUtil.measure(floatValue39),
                     ItemRenderUtil.measure(floatValue40),
                     ItemRenderUtil.measure3(floatValue37),
                     intValue9,
                     true,
                     intValue9
                  );
                  if (itemStack2.isDamageable()) {
                     int intValue10 = itemStack2.getMaxDamage();
                     int intValue11 = intValue10 - itemStack2.getDamage();
                     boolean flag4 = this.pokazyvatVProtsentah.isEnabled();
                     float floatValue41 = intValue10 <= 0 ? 1.0F : (float)intValue11 / intValue10;
                     String text = flag4 ? (int)(floatValue41 * 100.0F) + "%" : intValue11 + "/" + intValue10;
                     int intValue12 = floatValue41 <= 0.2F ? ColorUtils.compute43(255, 85, 85, intValue4) : this.compute9(floatValue28);
                     float floatValue42 = 16.0F * floatValue22;
                     HUD_METRIC_UTILS_VALUES[intValue9].invoke2(text, intValue11);
                     HUD_METRIC_UTILS_VALUES[intValue9]
                        .invoke3(
                           renderManager2,
                           FontRegistry.fontObject4,
                           floatValue35,
                           floatValue36,
                           floatValue23,
                           floatValue24,
                           4.0F * floatValue22,
                           floatValue35 + floatValue23 * 0.5F,
                           floatValue36 + floatValue24 - 6.0F * floatValue21,
                           floatValue42,
                           intValue12
                        );
                  }
               }
            } finally {
               renderManager2.invoke20();
               renderManager2.invoke25();
            }

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

   static {
      for (int intValue13 = 0; intValue13 < HUD_METRIC_UTILS_VALUES.length; intValue13++) {
         HUD_METRIC_UTILS_VALUES[intValue13] = new HudMetricUtils();
      }
   }
}
