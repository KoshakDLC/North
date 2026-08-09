package ru.metaculture.protection;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;

@HudElementInfo(
   resolve = "HotBar",
   resolve2 = "w"
)
public final class HotbarHud extends HudElement {
   private static final HotbarHud INSTANCE = new HotbarHud();
   private static float floatValue = 0.0F;
   private static final Animation ANIMATION = new Animation();
   private static final Animation ANIMATION_2 = new Animation();
   private static String text = "";
   private static long timestamp;
   public final GroupSetting elementyStatusa = new GroupSetting(
      "Элементы статуса",
      new BooleanSetting("Здоровье", true),
      new BooleanSetting("Голод", true),
      new BooleanSetting("Броня", true),
      new BooleanSetting("Воздух", true),
      new BooleanSetting("Поглощение", true)
   );

   private HotbarHud() {
      ru.metaculture.protection.HudPresetManager.invoke2(this);
      this.invoke(this.elementyStatusa);
   }

   public static HotbarHud getINSTANCE() {
      return INSTANCE;
   }

   public static void invoke(RenderManager renderManager, DrawContext drawContext) {
      INSTANCE.invoke2(renderManager, drawContext);
   }

   public void invoke2(RenderManager renderManager2, DrawContext drawContext) {
      if (MinecraftAccessor.a_ != null && MinecraftAccessor.a_.player != null && MinecraftAccessor.a_.world != null) {
         if (MinecraftAccessor.a_.getWindow() != null) {
            ANIMATION.check();
            ANIMATION.resolve4(1.0, 0.18F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
            float floatValue = ANIMATION.measure3();
            if (!(floatValue <= 0.01F)) {
               PlayerInventory playerInventory = MinecraftAccessor.a_.player.getInventory();
               if (playerInventory != null) {
                  ItemStack itemStack = playerInventory.getStack(playerInventory.getSelectedSlot());
                  String text = itemStack != null && !itemStack.isEmpty() ? itemStack.getName().getString() : "";
                  String text2 = text.isEmpty() ? "" : playerInventory.getSelectedSlot() + ":" + itemStack.getItem().toString() + ":" + text;
                  long longValue = System.currentTimeMillis();
                   if (!text2.equals(text)) {
                      text = text2;
                     timestamp = text2.isEmpty() ? 0L : longValue + 2200L;
                  }

                  ANIMATION_2.check();
                  ANIMATION_2.resolve4(!text.isEmpty() && longValue <= timestamp ? 1.0 : 0.0, 0.18, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, true);
                  float floatValue2 = Math.max(0.0F, Math.min(1.0F, ANIMATION_2.measure3()));
                  float floatValue3 = MinecraftAccessor.a_.getWindow().getFramebufferWidth();
                  float floatValue4 = MinecraftAccessor.a_.getWindow().getFramebufferHeight();
                  if (!(floatValue3 <= 0.0F) && !(floatValue4 <= 0.0F)) {
                     float floatValue5 = 42.0F;
                     float floatValue6 = 5.0F;
                     float floatValue7 = 1.75F;
                     float floatValue8 = 16.0F * floatValue7;
                     float floatValue9 = 7.0F;
                     float floatValue10 = floatValue5 * 9.0F + floatValue6 * 8.0F + floatValue9 * 2.0F;
                     float floatValue11 = floatValue5 + floatValue9 * 2.0F;
                     float floatValue12 = (floatValue3 - floatValue10) / 2.0F;
                     float floatValue13 = floatValue4 - floatValue11 - 3.0F;
                     HudEditorRenderer.HudEditorRendererState hudEditorRendererState = HudEditorRenderer.getINSTANCE().resolve("HUD_HotBar", floatValue12, floatValue13, floatValue10, floatValue11);
                     float floatValue14 = Math.min(hudEditorRendererState.floatValue3 / Math.max(1.0F, floatValue10), hudEditorRendererState.floatValue4 / Math.max(1.0F, floatValue11));
                     float floatValue15 = this.measureSelf(floatValue14) + this.measure2(floatValue14) + this.measure3(floatValue14, floatValue2);
                     HudModule.HudModuleState hudModuleState = HudModule.resolve4(
                        "HUD_HotBar", hudEditorRendererState.floatValue, hudEditorRendererState.floatValue2 - floatValue15, hudEditorRendererState.floatValue3, hudEditorRendererState.floatValue4 + floatValue15, 8.0F
                     );
                     float floatValue16 = hudModuleState.floatValue;
                     float floatValue17 = hudModuleState.floatValue2 + floatValue15;
                     float floatValue18 = hudEditorRendererState.floatValue3;
                     float floatValue19 = hudEditorRendererState.floatValue4;
                     this.invoke3(floatValue16, floatValue17, floatValue18, floatValue19);
                     float floatValue20 = floatValue18 / Math.max(1.0F, floatValue10);
                     float floatValue21 = floatValue19 / Math.max(1.0F, floatValue11);
                     float floatValue22 = Math.min(floatValue20, floatValue21);
                     float floatValue23 = floatValue5 * floatValue20;
                     float floatValue24 = floatValue6 * floatValue20;
                     float floatValue25 = floatValue9 * floatValue20;
                     float floatValue26 = floatValue9 * floatValue21;
                     float floatValue27 = floatValue8 * floatValue22;
                     float floatValue28 = floatValue7 * floatValue22;
                     float floatValue29 = playerInventory.getSelectedSlot() * (floatValue23 + floatValue24);
                      floatValue = floatValue + (floatValue29 - floatValue) * 0.25F;
                     float floatValue30 = floatValue * this.prozrachnost.getValue();
                     float floatValue31 = this.measureSelf(floatValue30);
                     int intValue = (int)(255.0F * floatValue30);
                     int intValue2 = this.compute(floatValue30);
                     int intValue3 = this.compute5(floatValue30);
                     int intValue4 = this.compute9(floatValue30);
                     int intValue5 = this.compute10(floatValue30);
                     int intValue6 = this.check7() ? ColorUtils.compute43(255, 255, 255, (int)(5.0F * floatValue31)) : this.compute2(floatValue31);
                     float floatValue32 = 12.0F * floatValue22;
                     boolean flag = this.check8();
                     if (flag) {
                        NeumorphismRenderer.invoke();
                     }

                     try {
                        this.invoke(renderManager2, floatValue16, floatValue17, floatValue18, floatValue19, floatValue32, floatValue30);

                        for (int intValue7 = 0; intValue7 < 9; intValue7++) {
                           float floatValue33 = floatValue16 + floatValue25 + intValue7 * (floatValue23 + floatValue24);
                           float floatValue34 = intValue7 == 0 ? 8.0F * floatValue22 : 4.0F * floatValue22;
                           float floatValue35 = intValue7 == 8 ? 8.0F * floatValue22 : 4.0F * floatValue22;
                           if (!this.check9() && !this.check10()) {
                              if (!flag
                                 || !this.check16(
                                    floatValue33, floatValue17 + floatValue26, floatValue23, floatValue23, Math.min(floatValue34, floatValue35), 2.8F * floatValue22, 6.0F * floatValue22, 0.86F, 2, true, floatValue30
                                 )) {
                                 renderManager2.invoke6(floatValue33, floatValue17 + floatValue26, floatValue23, floatValue23, floatValue34, floatValue35, floatValue35, floatValue34, intValue6);
                              }
                           } else {
                              this.invoke2(renderManager2, floatValue33, floatValue17 + floatValue26, floatValue23, floatValue23, Math.min(floatValue34, floatValue35), floatValue30);
                           }
                        }
                     } finally {
                        if (flag) {
                           NeumorphismRenderer.invoke3();
                        }
                     }

                      float floatValue36 = floatValue16 + floatValue25 + floatValue;
                     float floatValue37 = floatValue17 + floatValue26;
                     renderManager2.invoke5(
                        floatValue36 + 3.0F * floatValue20,
                        floatValue37 + floatValue23 - Math.max(2.0F, 2.0F * floatValue21),
                        floatValue23 - 4.0F * floatValue20,
                        Math.max(1.0F, 2.0F * floatValue21),
                        Math.max(0.5F, 0.8F * floatValue22),
                        ColorUtils.compute2(intValue5, (int)(140.0F * floatValue30))
                     );
                     renderManager2.invoke20();
                     renderManager2.invoke24(floatValue16, floatValue17, floatValue18, floatValue19, floatValue32, floatValue32, floatValue32, floatValue32);

                     try {
                        for (int intValue8 = 0; intValue8 < 9; intValue8++) {
                           ItemStack itemStack2 = playerInventory.getStack(intValue8);
                           float floatValue38 = floatValue16 + floatValue25 + intValue8 * (floatValue23 + floatValue24);
                           float floatValue39 = floatValue38 + (floatValue23 - floatValue27) * 0.5F;
                           float floatValue40 = floatValue17 + floatValue26 + (floatValue23 - floatValue27) * 0.5F;
                           if (itemStack2 != null && !itemStack2.isEmpty()) {
                              ItemRenderUtil.invoke3(
                                 renderManager2,
                                 itemStack2,
                                 ItemRenderUtil.measure(floatValue39),
                                 ItemRenderUtil.measure(floatValue40),
                                 ItemRenderUtil.measure3(floatValue28),
                                 intValue8,
                                 true,
                                 intValue8
                              );
                           }

                           String text3 = String.valueOf(intValue8 + 1);
                           float floatValue41 = 22.0F * floatValue22;
                           int intValue9 = intValue8 == playerInventory.getSelectedSlot()
                              ? ColorUtils.compute43(255, 255, 255, (int)(245.0F * floatValue30))
                              : ColorUtils.compute2(this.compute7(1.0F), (int)(175.0F * floatValue30));
                           float floatValue42 = floatValue38 + 4.0F * floatValue20;
                           float floatValue43 = floatValue17 + floatValue26 + floatValue23 - floatValue41 * floatValue21 - 8.0F;
                           renderManager2.invoke69(FontRegistry.fontObject4, floatValue42, floatValue43, floatValue41, text3, intValue9);
                        }
                     } finally {
                        renderManager2.invoke20();
                        renderManager2.invoke25();
                     }

                     ItemStack itemStack3 = NoSlow.resolve(MinecraftAccessor.a_.player.getOffHandStack());
                     if (itemStack3 != null && !itemStack3.isEmpty()) {
                        float floatValue44 = MinecraftAccessor.a_.player.getMainArm() == Arm.RIGHT ? floatValue16 - floatValue19 - 5.0F * floatValue20 : floatValue16 + floatValue18 + 5.0F * floatValue20;
                        if (flag) {
                           NeumorphismRenderer.invoke();
                        }

                        boolean flag2 = false ;

                        try {
                           flag2 = true;
                           if (this.check9() || this.check10()) {
                              this.invoke(renderManager2, floatValue44, floatValue17, floatValue19, floatValue19, floatValue32, floatValue30);
                              this.invoke2(renderManager2, floatValue44 + floatValue25, floatValue17 + floatValue26, floatValue23, floatValue23, 8.0F * floatValue22, floatValue30);
                              flag2 = false;
                           } else if (flag) {
                              if (!this.check15(floatValue44, floatValue17, floatValue19, floatValue19, floatValue32, false, floatValue30, 1)) {
                                 renderManager2.invoke5(floatValue44, floatValue17, floatValue19, floatValue19, floatValue32, intValue2);
                              }

                              if (!this.check16(floatValue44 + floatValue25, floatValue17 + floatValue26, floatValue23, floatValue23, 8.0F * floatValue22, 2.8F * floatValue22, 6.0F * floatValue22, 0.86F, 2, true, floatValue30)) {
                                 renderManager2.invoke5(floatValue44 + floatValue25, floatValue17 + floatValue26, floatValue23, floatValue23, 8.0F * floatValue22, intValue6);
                                 flag2 = false;
                              } else {
                                 flag2 = false;
                              }
                           } else {
                              if (this.check()) {
                                 renderManager2.invoke41(floatValue44, floatValue17, floatValue19, floatValue19, floatValue32, this.check17() ? 6.0F : 4.0F, 1.0F, this.compute17(floatValue30));
                              }

                              if (this.check7()) {
                                 renderManager2.invoke48(23.0F);
                                 renderManager2.invoke44(floatValue44, floatValue17, floatValue19, floatValue19, floatValue32, floatValue30);
                              }

                              renderManager2.invoke5(floatValue44, floatValue17, floatValue19, floatValue19, floatValue32, intValue2);
                              if (this.check2()) {
                                 renderManager2.invoke28(floatValue44, floatValue17, floatValue19, floatValue19, floatValue32, intValue3, this.measure2());
                              }

                              renderManager2.invoke5(floatValue44 + floatValue25, floatValue17 + floatValue26, floatValue23, floatValue23, 8.0F * floatValue22, intValue6);
                              flag2 = false;
                           }
                        } finally {
                           if (flag2) {
                              if (flag) {
                                 NeumorphismRenderer.invoke3();
                              }
                           }
                        }

                        if (flag) {
                           NeumorphismRenderer.invoke3();
                        }

                        float floatValue45 = floatValue44 + floatValue25 + (floatValue23 - floatValue27) * 0.5F;
                        float floatValue46 = floatValue17 + floatValue26 + (floatValue23 - floatValue27) * 0.5F;
                        renderManager2.invoke20();
                        renderManager2.invoke24(floatValue44, floatValue17, floatValue19, floatValue19, floatValue32, floatValue32, floatValue32, floatValue32);
                        boolean flag3 = false ;

                        try {
                           flag3 = true;
                           ItemRenderUtil.invoke3(
                              renderManager2,
                              itemStack3,
                              ItemRenderUtil.measure(floatValue45),
                              ItemRenderUtil.measure(floatValue46),
                              ItemRenderUtil.measure3(floatValue28),
                              0,
                              true,
                              0
                           );
                           flag3 = false;
                        } finally {
                           if (flag3) {
                              renderManager2.invoke20();
                              renderManager2.invoke25();
                           }
                        }

                        renderManager2.invoke20();
                        renderManager2.invoke25();
                     }

                     HotbarStatusRenderer.getINSTANCE().invoke(renderManager2, this, floatValue16, floatValue17, floatValue18, floatValue20, floatValue21, floatValue30);
                     if (MinecraftAccessor.a_.player.experienceLevel > 0) {
                        String text4 = String.valueOf(MinecraftAccessor.a_.player.experienceLevel);
                        float floatValue47 = 12.0F * floatValue22;
                        float floatValue48 = 8.0F * floatValue22;
                        float floatValue49 = 26.0F * floatValue22;
                        float floatValue50 = TextMeasureCache.resolve(FontRegistry.fontObject4, text4, floatValue49).floatValue;
                        int intValue10 = ColorUtils.compute2(intValue4, intValue);
                        int intValue11 = this.compute2(floatValue30);
                        float floatValue51 = this.measureSelf(floatValue22);
                        float floatValue52 = Math.max(34.0F * floatValue22, floatValue50 + 16.0F * floatValue22);
                        float floatValue53 = floatValue16 + (floatValue18 - floatValue52) * 0.5F;
                        float floatValue54 = floatValue17 - floatValue51 - floatValue48 - floatValue47;
                        if (flag) {
                           if (!this.check16(floatValue53, floatValue54, floatValue52, floatValue47, floatValue47 * 0.5F, 2.4F * floatValue22, 5.5F * floatValue22, 0.82F, 1, false, floatValue30)) {
                              renderManager2.invoke5(floatValue53, floatValue54, floatValue52, floatValue47, floatValue47 * 0.5F, intValue11);
                           }
                        } else if (this.check9() || this.check10()) {
                           this.invoke2(renderManager2, floatValue53, floatValue54, floatValue52, floatValue47, floatValue47 * 0.5F, floatValue30);
                        }

                        renderManager2.invoke69(
                           FontRegistry.fontObject4, floatValue53 + (floatValue52 - floatValue50) * 0.5F, floatValue54 + floatValue47 * 0.5F + 3.7F * floatValue22, floatValue49, text4, intValue10
                        );
                     }

                     if (floatValue2 > 0.01F && !text.isEmpty()) {
                        float floatValue55 = floatValue30 * floatValue2;
                        float floatValue56 = 16.0F * floatValue22;
                        float floatValue57 = 32.0F * floatValue22;
                        float floatValue58 = 4.0F * floatValue22;
                        float floatValue59 = Math.clamp(floatValue18 * 0.72F, 20.0F * floatValue22, 190.0F * floatValue22);
                        String text5 = this.resolve(text, floatValue57, floatValue59);
                        float floatValue60 = TextMeasureCache.measure(FontRegistry.fontObject, text5, floatValue57);
                        float floatValue61 = Math.max(54.0F * floatValue22, floatValue60 + 20.0F * floatValue22);
                        float floatValue62 = this.measureSelf(floatValue22);
                        float floatValue63 = this.measure2(floatValue22);
                        float floatValue64 = floatValue16 + (floatValue18 - floatValue61) * 0.5F;
                        float floatValue65 = floatValue17 - floatValue62 - floatValue63 - floatValue58 - floatValue56;
                        renderManager2.invoke69(
                           FontRegistry.fontObject,
                           floatValue64 + (floatValue61 - floatValue60) * 0.5F,
                           floatValue65 + floatValue56 * 0.5F + 1.05F * floatValue22,
                           floatValue57,
                           text5,
                           ColorUtils.compute2(this.compute6(1.0F), (int)(255.0F * floatValue55))
                        );
                     }

                     HudModule.invoke3("HUD_HotBar", floatValue16, floatValue17 - floatValue15, floatValue18, floatValue19 + floatValue15);
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
         }
      }
   }

   private float measureSelf(float f) {
      if (MinecraftAccessor.a_ != null && MinecraftAccessor.a_.player != null) {
         boolean flag4 = this.elementyStatusa.isEnabled("Здоровье");
         boolean flag5 = this.elementyStatusa.isEnabled("Голод");
         boolean flag6 = this.elementyStatusa.isEnabled("Броня") && MinecraftAccessor.a_.player.getArmor() > 0;
         boolean flag7 = this.elementyStatusa.isEnabled("Воздух") && MinecraftAccessor.a_.player.getAir() < MinecraftAccessor.a_.player.getMaxAir();
         float floatValue66 = 12.0F * f;
         float floatValue67 = 4.0F * f;
         int intValue12 = 0;
         if (flag4 || flag5) {
            intValue12++;
         }

         if (flag6 || flag7) {
            intValue12++;
         }

         return intValue12 == 0 ? 0.0F : intValue12 * floatValue66 + intValue12 * floatValue67;
      } else {
         return 0.0F;
      }
   }

   private float measure2(float f) {
      return MinecraftAccessor.a_ != null && MinecraftAccessor.a_.player != null && MinecraftAccessor.a_.player.experienceLevel > 0 ? 16.0F * f : 0.0F;
   }

   private float measure3(float f, float g) {
      return g > 0.01F ? 20.0F * f : 0.0F;
   }

   private String resolve(String string, float f, float g) {
      if (string != null && !string.isEmpty()) {
         if (TextMeasureCache.measure(FontRegistry.fontObject, string, f) <= g) {
            return string;
         } else {
            String text6 = "...";
            float floatValue68 = TextMeasureCache.measure(FontRegistry.fontObject, text6, f);
            if (floatValue68 >= g) {
               return text6;
            } else {
               int intValue13 = 0;
               int intValue14 = string.length();

               while (intValue13 < intValue14) {
                  int intValue15 = intValue13 + intValue14 + 1 >>> 1;
                  String text7 = string.substring(0, intValue15).trim();
                  float floatValue69 = TextMeasureCache.measure(FontRegistry.fontObject, text7, f) + floatValue68;
                  if (floatValue69 <= g) {
                     intValue13 = intValue15;
                  } else {
                     intValue14 = intValue15 - 1;
                  }
               }

               String text8 = string.substring(0, Math.max(0, intValue13)).trim();
               return text8.isEmpty() ? text6 : text8 + text6;
            }
         }
      } else {
         return "";
      }
   }
}
