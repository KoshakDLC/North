package ru.metaculture.protection;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@HudElementInfo(
   resolve = "InventoryHUD",
   resolve2 = "w"
)
public final class InventoryHud extends HudElement {
   private static final InventoryHud INSTANCE = new InventoryHud();
   private static final Animation ANIMATION = new Animation();
   private static final Animation ANIMATION_2 = new Animation();
   private static final Animation ANIMATION_3 = new Animation();
   private static final Animation[] ANIMATIONS = new Animation[27];
   private static final Item[] ITEMS = new Item[27];
   private final BooleanSetting pokazyvatVerhushku = new BooleanSetting("Показывать верхушку", true);
   private final BooleanSetting fonSlotov = new BooleanSetting("Фон слотов", true);

   private InventoryHud() {
      this.invoke(this.pokazyvatVerhushku);
      this.invoke(this.fonSlotov);
      ru.metaculture.protection.HudPresetManager.invoke2(this);
   }

   public static void invoke(RenderManager renderManager, DrawContext drawContext) {
      INSTANCE.invoke2(renderManager, drawContext);
   }

   public static InventoryHud getINSTANCE() {
      return INSTANCE;
   }

   public void invoke2(RenderManager renderManager2, DrawContext drawContext) {
      if (MinecraftAccessor.a_.player != null) {
         boolean flag = false;

         for (int intValue = 9; intValue < 36; intValue++) {
            ItemStack itemStack = MinecraftAccessor.a_.player.getInventory().getStack(intValue);
            if (!itemStack.isEmpty()) {
               flag = true;
               break;
            }
         }

         boolean flag2 = !flag && !(MinecraftAccessor.a_.currentScreen instanceof ChatScreen);
         boolean flag3 = !flag2;
         ANIMATION.check();
         ANIMATION.resolve4(flag3 ? 1.0 : 0.0, 0.22F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
         float floatValue = ANIMATION.measure3();
         if (!(floatValue <= 0.01F)) {
            boolean flag4 = this.pokazyvatVerhushku.isEnabled();
            boolean flag5 = HudModule.check2();
            HudLayoutManager.HudLayoutManagerState hudLayoutManagerState = flag5 ? HudLayoutManager.resolve2() : null;
            float floatValue2 = 24.0F;
            float floatValue3 = flag5 ? hudLayoutManagerState.floatValue8 : 7.0F;
            float floatValue4 = flag4 ? (flag5 ? hudLayoutManagerState.floatValue10 : 32.0F) : 0.0F;
            float floatValue5 = flag4 ? (flag5 ? hudLayoutManagerState.floatValue9 : 5.0F) : 0.0F;
            float floatValue6 = 22.0F;
            float floatValue7 = flag5 ? hudLayoutManagerState.floatValue8 : 7.0F;
            float floatValue8 = 9.0F * floatValue6;
            float floatValue9 = 3.0F * floatValue6;
            String text = "Inventory";
            float floatValue10 = TextMeasureCache.resolve(FontRegistry.fontObject4, text, flag5 ? hudLayoutManagerState.floatValue12 : 26.0F).floatValue;
            float floatValue11 = floatValue8 + floatValue7 * 2.0F;
            float floatValue12 = floatValue9 + floatValue7 * 2.0F;
            float floatValue13 = floatValue11 + floatValue3 * 2.0F;
            if (flag4) {
               float floatValue14 = floatValue10 + 22.0F + floatValue7 * 2.0F + (flag5 ? hudLayoutManagerState.floatValue13 : 24.0F);
               floatValue13 = Math.max(floatValue13, floatValue14 + floatValue3 * 2.0F);
            }

            float floatValue15 = floatValue3 + floatValue4 + floatValue5 + floatValue12 + floatValue3;
            ANIMATION_2.check();
            ANIMATION_3.check();
            ANIMATION_2.resolve4(floatValue13, 0.18F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
            ANIMATION_3.resolve4(floatValue15, 0.18F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
            float floatValue16 = ANIMATION_2.measure3();
            float floatValue17 = ANIMATION_3.measure3();
            float floatValue18 = MinecraftAccessor.a_.getWindow().getFramebufferWidth();
            float floatValue19 = Math.max(10.0F, floatValue18 - floatValue16 - 10.0F);
            float floatValue20 = 10.0F;
            HudEditorRenderer.HudEditorRendererState hudEditorRendererState = HudEditorRenderer.getINSTANCE().resolve("HUD_Inventory", floatValue19, floatValue20, floatValue16, floatValue17);
            float floatValue21 = hudEditorRendererState.floatValue;
            float floatValue22 = hudEditorRendererState.floatValue2;
            float floatValue23 = hudEditorRendererState.floatValue3;
            float floatValue24 = hudEditorRendererState.floatValue4;
            this.invoke3(floatValue21, floatValue22, floatValue23, floatValue24);
            float floatValue25 = floatValue23 / Math.max(1.0F, floatValue16);
            float floatValue26 = floatValue24 / Math.max(1.0F, floatValue17);
            float floatValue27 = Math.min(floatValue25, floatValue26);
            float floatValue28 = floatValue3 * floatValue25;
            float floatValue29 = floatValue3 * floatValue26;
            float floatValue30 = flag4 ? floatValue4 * floatValue26 : 0.0F;
            float floatValue31 = floatValue5 * floatValue26;
            float floatValue32 = floatValue6 * floatValue27;
            float floatValue33 = floatValue * this.prozrachnost.getValue();
            float floatValue34 = this.measure(floatValue33);
            int intValue2 = (int)(255.0F * floatValue33);
            int intValue3 = this.compute2(floatValue33);
            int intValue4 = this.compute3(floatValue33);
            int intValue5 = this.compute6(floatValue33);
            int intValue6 = this.compute8(floatValue33);
            int intValue7 = this.check7() ? ColorUtils.compute43(255, 255, 255, (int)(5.0F * floatValue34)) : this.compute2(floatValue34);
            float floatValue35 = flag5 ? hudLayoutManagerState.floatValue : 14.0F;
            float floatValue36 = flag5 ? hudLayoutManagerState.floatValue2 : 11.0F;
            float floatValue37 = flag5 ? hudLayoutManagerState.floatValue3 : 9.0F;
            float floatValue38 = flag5 ? hudLayoutManagerState.floatValue7 : 4.0F;
            float floatValue39 = floatValue23 - floatValue28 * 2.0F;
            this.invoke(renderManager2, floatValue21, floatValue22, floatValue23, floatValue24, floatValue35, floatValue33);
            if (flag4) {
               if (this.check8() || this.check9() || this.check10()) {
                  this.invoke(renderManager2, floatValue21 + floatValue28, floatValue22 + floatValue29, floatValue39, floatValue30, floatValue36, floatValue33);
               } else if (flag5) {
                  renderManager2.invoke5(floatValue21 + floatValue28, floatValue22 + floatValue29, floatValue39, floatValue30, floatValue36, intValue3);
               } else {
                  renderManager2.invoke6(floatValue21 + floatValue28, floatValue22 + floatValue29, floatValue39, floatValue30, 11.0F, 11.0F, 4.0F, 4.0F, intValue3);
               }

               float floatValue40 = flag5 ? floatValue21 + hudLayoutManagerState.hudLayoutManagerState3.floatValue * floatValue25 : floatValue21 + floatValue28 + 10.0F * floatValue25;
               float floatValue41 = flag5 ? floatValue22 + hudLayoutManagerState.hudLayoutManagerState3.floatValue2 * floatValue26 : floatValue22 + floatValue29 + floatValue30 / 2.0F + 6.0F * floatValue26;
               renderManager2.invoke69(FontRegistry.fontObject4, floatValue40, floatValue41, (flag5 ? hudLayoutManagerState.floatValue12 : 26.0F) * floatValue27, text, intValue5);
               float floatValue42 = 22.0F * floatValue26;
               float floatValue43 = floatValue21 + floatValue28 + floatValue39 - 10.0F * floatValue25 - floatValue42;
               float floatValue44 = floatValue22 + floatValue29 + (floatValue30 - floatValue42) / 2.0F;
               float floatValue45 = (flag5 ? hudLayoutManagerState.floatValue13 : floatValue2 + 4.0F) * floatValue27;
               float floatValue46 = TextMeasureCache.resolve(FontRegistry.fontObject5, "h", floatValue45).floatValue;
               float floatValue47 = flag5
                  ? (hudLayoutManagerState.hudLayoutManagerState32.flag ? floatValue21 + floatValue23 : floatValue21) + hudLayoutManagerState.hudLayoutManagerState32.floatValue * floatValue25
                  : floatValue43 + (floatValue42 - floatValue46) / 2.0F;
               float floatValue48 = flag5 ? floatValue22 + hudLayoutManagerState.hudLayoutManagerState32.floatValue2 * floatValue26 : floatValue44 + floatValue42 / 2.0F + 7.0F * floatValue26;
               renderManager2.invoke69(FontRegistry.fontObject5, floatValue47, floatValue48, floatValue45, "h", intValue6);
            }

            float floatValue49 = floatValue22 + floatValue29 + floatValue30 + floatValue31;
            if (!flag4) {
               floatValue49 = floatValue22 + floatValue29;
            }

            float floatValue50 = floatValue21 + floatValue28 + (flag5 ? hudLayoutManagerState.hudLayoutManagerState33.floatValue * floatValue25 : 0.0F);
            floatValue49 += flag5 ? hudLayoutManagerState.hudLayoutManagerState33.floatValue2 * floatValue26 : 0.0F;
            float floatValue51 = floatValue12 * floatValue26;
            if (this.check8() || this.check9() || this.check10()) {
               this.invoke2(renderManager2, floatValue50, floatValue49, floatValue39, floatValue51, floatValue37, floatValue33);
            } else if (flag5) {
               renderManager2.invoke5(floatValue50, floatValue49, floatValue39, floatValue51, floatValue37, intValue4);
            } else {
               renderManager2.invoke6(floatValue50, floatValue49, floatValue39, floatValue51, flag4 ? 4.0F : 11.0F, flag4 ? 4.0F : 11.0F, 11.0F, 11.0F, intValue4);
            }

            renderManager2.invoke20();
            renderManager2.invoke24(floatValue21, floatValue22, floatValue23, floatValue24, floatValue35, floatValue35, floatValue35, floatValue35);

            try {
               float floatValue52 = floatValue50 + (floatValue39 - 9.0F * floatValue32) / 2.0F;
               float floatValue53 = floatValue49 + (floatValue51 - 3.0F * floatValue32) / 2.0F;

               for (int intValue8 = 0; intValue8 < 3; intValue8++) {
                  for (int intValue9 = 0; intValue9 < 9; intValue9++) {
                     float floatValue54 = floatValue52 + intValue9 * floatValue32;
                     float floatValue55 = floatValue53 + intValue8 * floatValue32;
                     if (this.fonSlotov.isEnabled()) {
                        if (!this.check8() && !this.check9() && !this.check10()) {
                           renderManager2.invoke5(floatValue54 + 1.0F, floatValue55 + 1.0F, floatValue32 - 2.0F, floatValue32 - 2.0F, floatValue38 * floatValue27, intValue7);
                        } else {
                           this.invoke2(renderManager2, floatValue54 + 1.0F, floatValue55 + 1.0F, floatValue32 - 2.0F, floatValue32 - 2.0F, floatValue38 * floatValue27, floatValue33);
                        }
                     }
                  }
               }

               renderManager2.invoke20();
               int intValue10 = 9;

               for (int intValue11 = 0; intValue11 < 3; intValue11++) {
                  for (int intValue12 = 0; intValue12 < 9; intValue12++) {
                     float floatValue56 = floatValue52 + intValue12 * floatValue32;
                     float floatValue57 = floatValue53 + intValue11 * floatValue32;
                     ItemStack itemStack2 = MinecraftAccessor.a_.player.getInventory().getStack(intValue10);
                     int intValue13 = intValue10 - 9;
                     Animation animation = ANIMATIONS[intValue13];
                     animation.check();
                     boolean flag6 = !itemStack2.isEmpty();
                     Item item = flag6 ? itemStack2.getItem() : null;
                     if (flag6 && ITEMS[intValue13] != item) {
                        animation.invoke(0.0);
                     }

                     animation.resolve4(flag6 ? 1.0 : 0.0, 0.2F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
                     ITEMS[intValue13] = item;
                     if (flag6 && animation.measure3() > 0.01F) {
                        float floatValue58 = animation.measure3();
                        float floatValue59 = 0.4F + 0.6F * floatValue58;
                        float floatValue60 = floatValue27 * floatValue59;
                        float floatValue61 = 16.0F * floatValue60;
                        float floatValue62 = floatValue56 + (floatValue32 - floatValue61) / 2.0F;
                        float floatValue63 = floatValue57 + (floatValue32 - floatValue61) / 2.0F;
                        ItemRenderUtil.invoke3(
                           renderManager2,
                           itemStack2,
                           ItemRenderUtil.measure(floatValue62),
                           ItemRenderUtil.measure(floatValue63),
                           ItemRenderUtil.measure3(floatValue60),
                           0,
                           true,
                           intValue13
                        );
                     }

                     intValue10++;
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
      for (int intValue14 = 0; intValue14 < ANIMATIONS.length; intValue14++) {
         ANIMATIONS[intValue14] = new Animation();
      }
   }
}
