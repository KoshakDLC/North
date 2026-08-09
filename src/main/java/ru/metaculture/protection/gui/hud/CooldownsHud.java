package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.CooldownUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.wild.mixin.acceser.ItemCooldownManagerAccessor;
import org.wild.mixin.acceser.ItemCooldownManagerEntryAccessor;

@HudElementInfo(
   resolve = "CoolDownsHUD",
   resolve2 = "i"
)
public final class CooldownsHud extends HudElement {
   private static final CooldownsHud INSTANCE = new CooldownsHud();
   private static final Map<Item, CooldownsHud.CooldownsHudItemState> VALUES_BY_KEY = new ConcurrentHashMap<>();
   private static final List<CooldownsHud.CooldownsHudItemState> ITEMS = new ArrayList<>(16);
   private static final List<CooldownsHud.CooldownsHudItemData> ITEMS_2 = new ArrayList<>(16);
   private static final Animation ANIMATION = new Animation();
   private static final Animation ANIMATION_2 = new Animation();
   private static final Animation ANIMATION_3 = new Animation();
   private final BooleanSetting pokazyvatVerhushku = new BooleanSetting("Показывать верхушку", true);
   private final BooleanSetting pokazyvatIkonki = new BooleanSetting("Показывать иконки", true);

   private CooldownsHud() {
      this.invoke(this.pokazyvatVerhushku);
      this.invoke(this.pokazyvatIkonki);
      ru.metaculture.protection.HudPresetManager.invoke2(this);
   }

   public static CooldownsHud getINSTANCE() {
      return INSTANCE;
   }

   public static long compute(Item item) {
      return item == null ? 0L : compute2(new ItemStack(item));
   }

   public static void invoke(PacketEvent packetEvent) {
      if (packetEvent != null && !packetEvent.check() && MinecraftAccessor.a_.player != null) {
         if (packetEvent.getPacket() instanceof CooldownUpdateS2CPacket cooldownUpdateS2CPacket) {
            Item item2 = (Item)Registries.ITEM.get(cooldownUpdateS2CPacket.cooldownGroup());
            if (item2 == null || item2 == Items.AIR) {
               return;
            }

            int intValue = cooldownUpdateS2CPacket.cooldown();
            if (intValue <= 0) {
               VALUES_BY_KEY.remove(item2);
            } else {
               CooldownsHud.CooldownsHudItemState cooldownsHudItemState = VALUES_BY_KEY.computeIfAbsent(item2, CooldownsHud.CooldownsHudItemState::new);
               cooldownsHudItemState.setItemStack(resolve2(item2));
            }
         } else if (packetEvent.getPacket() instanceof PlayerRespawnS2CPacket) {
            invoke5();
         }
      }
   }

   public static void invoke2(RenderManager renderManager, DrawContext drawContext) {
      INSTANCE.invoke3(renderManager, drawContext);
   }

   private void invoke3(RenderManager renderManager2, DrawContext drawContext) {
      if (MinecraftAccessor.a_.player != null && MinecraftAccessor.a_.world != null) {
         invoke4();
         ITEMS.clear();
         ITEMS_2.clear();
         Iterator iterator = VALUES_BY_KEY.entrySet().iterator();

         while (iterator.hasNext()) {
            CooldownsHud.CooldownsHudItemState cooldownsHudItemState2 = (CooldownsHud.CooldownsHudItemState)((Entry)iterator.next()).getValue();
            boolean flag = cooldownsHudItemState2.timestamp > 0L;
            cooldownsHudItemState2.animation.check();
            cooldownsHudItemState2.animation.resolve4(flag ? 1.0 : 0.0, 0.15F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
            if (!flag && !(cooldownsHudItemState2.animation.measure3() > 0.01F)) {
               iterator.remove();
            } else {
               ITEMS.add(cooldownsHudItemState2);
            }
         }

         ITEMS.sort(
            Comparator.<CooldownsHud.CooldownsHudItemState>comparingLong(cooldownsHudItemState3 -> -cooldownsHudItemState3.timestamp).thenComparing(cooldownsHudItemState4 -> cooldownsHudItemState4.text)
         );
         boolean flag2 = !ITEMS.isEmpty() || MinecraftAccessor.a_.currentScreen instanceof ChatScreen;
         ANIMATION.check();
         ANIMATION.resolve4(flag2 ? 1.0 : 0.0, 0.22F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
         float floatValue = ANIMATION.measure3();
         if (!(floatValue <= 0.01F)) {
            boolean flag3 = this.pokazyvatVerhushku.isEnabled();
            boolean flag4 = this.pokazyvatIkonki.isEnabled();
            boolean flag5 = HudModule.check2();
            HudLayoutManager.HudLayoutManagerState hudLayoutManagerState = flag5 ? HudLayoutManager.resolve4("HUD_CoolDowns") : null;
            float floatValue2 = 24.0F;
            float floatValue3 = flag5 ? hudLayoutManagerState.floatValue8 : 7.0F;
            float floatValue4 = flag3 ? (flag5 ? hudLayoutManagerState.floatValue10 : 32.0F) : 0.0F;
            float floatValue5 = flag5 ? hudLayoutManagerState.floatValue11 : 22.0F;
            float floatValue6 = flag5 ? hudLayoutManagerState.floatValue9 : 5.0F;
            float floatValue7 = flag5 ? hudLayoutManagerState.floatValue12 : 28.0F;
            String text = "Cooldowns";
            float floatValue8 = TextMeasureCache.measure(FontRegistry.fontObject4, text, floatValue7);
            float floatValue9 = flag3 ? floatValue8 + 46.0F : 0.0F;
            float floatValue10 = 0.0F;
            float floatValue11 = 0.0F;
            float floatValue12 = 0.0F;

            for (CooldownsHud.CooldownsHudItemState cooldownsHudItemState5 : ITEMS) {
               float floatValue13 = cooldownsHudItemState5.animation.measure3();
               if (!(floatValue13 <= 0.01F)) {
                  String text2 = resolve((float)cooldownsHudItemState5.timestamp / 1000.0F);
                  floatValue10 = Math.max(floatValue10, TextMeasureCache.measure(FontRegistry.fontObject, cooldownsHudItemState5.text, floatValue2));
                  floatValue11 = Math.max(floatValue11, TextMeasureCache.measure(FontRegistry.fontObject, text2, floatValue2));
                  floatValue12 += floatValue5 * floatValue13;
               }
            }

            float floatValue14 = flag4 ? 22.0F : 0.0F;
            float floatValue15 = floatValue10 + floatValue14 + 24.0F;
            float floatValue16 = floatValue11 + 20.0F + (flag5 ? hudLayoutManagerState.floatValue14 : 0.0F);
            float floatValue17 = ITEMS.isEmpty() ? 0.0F : floatValue15 + floatValue6 + floatValue16;
            float floatValue18 = Math.max(floatValue9, floatValue17) + floatValue3 * 2.0F;
            floatValue18 = Math.max(floatValue18, flag3 ? 104.0F : 74.0F);
            if (floatValue17 > 0.0F) {
               float floatValue19 = floatValue18 - floatValue3 * 2.0F;
               floatValue15 = Math.max(40.0F, floatValue19 - floatValue6 - floatValue16);
            }

            float floatValue20 = floatValue3 + floatValue4 + (flag3 && floatValue12 > 0.01F ? floatValue6 : 0.0F) + floatValue12 + floatValue3;
            ANIMATION_2.check();
            ANIMATION_3.check();
            ANIMATION_2.resolve4(floatValue18, 0.18F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
            ANIMATION_3.resolve4(floatValue20, 0.18F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
            float floatValue21 = ANIMATION_2.measure3();
            float floatValue22 = ANIMATION_3.measure3();
            float floatValue23 = MinecraftAccessor.a_.getWindow().getFramebufferWidth();
            HudEditorRenderer.HudEditorRendererState hudEditorRendererState = HudEditorRenderer.getINSTANCE().resolve("HUD_CoolDowns", Math.max(10.0F, floatValue23 - floatValue21 - 10.0F), 140.0F, floatValue21, floatValue22);
            float floatValue24 = hudEditorRendererState.floatValue;
            float floatValue25 = hudEditorRendererState.floatValue2;
            float floatValue26 = hudEditorRendererState.floatValue3;
            float floatValue27 = hudEditorRendererState.floatValue4;
            this.invoke3(floatValue24, floatValue25, floatValue26, floatValue27);
            float floatValue28 = floatValue26 / Math.max(1.0F, floatValue21);
            float floatValue29 = floatValue27 / Math.max(1.0F, floatValue22);
            float floatValue30 = Math.min(floatValue28, floatValue29);
            float floatValue31 = floatValue3 * floatValue28;
            float floatValue32 = floatValue3 * floatValue29;
            float floatValue33 = floatValue4 * floatValue29;
            float floatValue34 = floatValue5 * floatValue29;
            float floatValue35 = floatValue6 * floatValue28;
            float floatValue36 = floatValue6 * floatValue29;
            float floatValue37 = floatValue2 * floatValue30;
            float floatValue38 = floatValue15 * floatValue28;
            float floatValue39 = floatValue16 * floatValue28;
            float floatValue40 = floatValue26 - floatValue31 * 2.0F;
            float floatValue41 = floatValue * this.prozrachnost.getValue();
            int intValue2 = this.compute(floatValue41);
            int intValue3 = this.compute2(floatValue41);
            int intValue4 = this.compute3(floatValue41);
            int intValue5 = this.compute5(floatValue41);
            int intValue6 = this.compute6(floatValue41);
            int intValue7 = this.compute9(floatValue41);
            float floatValue42 = flag5 ? hudLayoutManagerState.floatValue : 14.0F;
            float floatValue43 = flag5 ? hudLayoutManagerState.floatValue2 : 11.0F;
            float floatValue44 = flag5 ? hudLayoutManagerState.floatValue4 : 7.0F;
            float floatValue45 = flag5 ? hudLayoutManagerState.floatValue5 : 7.0F;
            float floatValue46 = flag5 ? hudLayoutManagerState.floatValue15 : 1.9F;
            this.invoke(renderManager2, floatValue24, floatValue25, floatValue26, floatValue27, floatValue42, floatValue41);
            if (flag3) {
               if (this.check8()) {
                  this.invoke(renderManager2, floatValue24 + floatValue31, floatValue25 + floatValue32, floatValue40, floatValue33, floatValue43, floatValue41);
               } else if (flag5) {
                  renderManager2.invoke5(floatValue24 + floatValue31, floatValue25 + floatValue32, floatValue40, floatValue33, floatValue43, intValue3);
               } else {
                  renderManager2.invoke6(floatValue24 + floatValue31, floatValue25 + floatValue32, floatValue40, floatValue33, 11.0F, 11.0F, 4.0F, 4.0F, intValue3);
               }

               float floatValue47 = flag5 ? floatValue24 + hudLayoutManagerState.hudLayoutManagerState3.floatValue * floatValue28 : floatValue24 + floatValue31 + 10.0F * floatValue28;
               float floatValue48 = flag5 ? floatValue25 + hudLayoutManagerState.hudLayoutManagerState3.floatValue2 * floatValue29 : floatValue25 + floatValue32 + floatValue33 * 0.5F + 6.0F * floatValue29;
               renderManager2.invoke69(FontRegistry.fontObject4, floatValue47, floatValue48, floatValue7 * floatValue30, text, intValue6);
               float floatValue49 = 22.0F * floatValue29;
               float floatValue50 = floatValue24 + floatValue31 + floatValue40 - 10.0F * floatValue28 - floatValue49;
               float floatValue51 = floatValue25 + floatValue32 + (floatValue33 - floatValue49) * 0.5F;
               float floatValue52 = (flag5 ? hudLayoutManagerState.floatValue13 : floatValue2) * floatValue30;
               float floatValue53 = TextMeasureCache.measure(FontRegistry.fontObject5, "g", floatValue52);
               float floatValue54 = flag5
                  ? (hudLayoutManagerState.hudLayoutManagerState32.flag ? floatValue24 + floatValue26 : floatValue24) + hudLayoutManagerState.hudLayoutManagerState32.floatValue * floatValue28
                  : floatValue50 + (floatValue49 - floatValue53) * 0.8F;
               float floatValue55 = flag5 ? floatValue25 + hudLayoutManagerState.hudLayoutManagerState32.floatValue2 * floatValue29 : floatValue51 + floatValue49 * 0.55F + 5.5F * floatValue29;
               renderManager2.invoke69(FontRegistry.fontObject5, floatValue54, floatValue55, floatValue52, "g", intValue7);
            }

            float floatValue56 = floatValue25 + floatValue32 + floatValue33 + (flag3 && floatValue12 > 0.01F ? floatValue36 : 0.0F);
            float floatValue57 = floatValue24 + floatValue31 + (flag5 ? hudLayoutManagerState.hudLayoutManagerState33.floatValue * floatValue28 : 0.0F);
            float floatValue58 = floatValue56 + (flag5 ? hudLayoutManagerState.hudLayoutManagerState33.floatValue2 * floatValue29 : 0.0F);
            float floatValue59 = floatValue24 + floatValue31 + floatValue38 + floatValue35 + (flag5 ? hudLayoutManagerState.hudLayoutManagerState34.floatValue * floatValue28 : 0.0F);
            float floatValue60 = floatValue56 + (flag5 ? hudLayoutManagerState.hudLayoutManagerState34.floatValue2 * floatValue29 : 0.0F);
            float floatValue61 = floatValue12 * floatValue29;
            if (floatValue61 > 0.01F && this.check5()) {
               if (this.check8()) {
                  this.invoke2(renderManager2, floatValue57, floatValue58, floatValue38, floatValue61, floatValue44, floatValue41);
                  this.invoke2(renderManager2, floatValue59, floatValue60, floatValue39, floatValue61, floatValue45, floatValue41);
               } else if (flag5) {
                  renderManager2.invoke5(floatValue57, floatValue58, floatValue38, floatValue61, floatValue44, intValue4);
                  renderManager2.invoke5(floatValue59, floatValue60, floatValue39, floatValue61, floatValue45, intValue4);
               } else {
                  renderManager2.invoke6(floatValue57, floatValue58, floatValue38, floatValue61, flag3 ? 4.0F : 11.0F, flag3 ? 4.0F : 11.0F, 4.0F, 11.0F, intValue4);
                  renderManager2.invoke6(floatValue59, floatValue60, floatValue39, floatValue61, 4.0F, flag3 ? 4.0F : 11.0F, 11.0F, 4.0F, intValue4);
               }
            }

            renderManager2.invoke20();
            renderManager2.invoke24(floatValue24, floatValue25, floatValue26, floatValue27, floatValue42, floatValue42, floatValue42, floatValue42);

            try {
               float floatValue62 = floatValue58;
               float floatValue63 = floatValue60;

               for (int intValue8 = 0; intValue8 < ITEMS.size(); intValue8++) {
                  CooldownsHud.CooldownsHudItemState cooldownsHudItemState6 = ITEMS.get(intValue8);
                  float floatValue64 = cooldownsHudItemState6.animation.measure3();
                  if (!(floatValue64 <= 0.01F)) {
                     String text3 = resolve((float)cooldownsHudItemState6.timestamp / 1000.0F);
                     int intValue9 = (int)(255.0F * floatValue41 * floatValue64);
                     int intValue10 = ColorUtils.compute2(this.compute6(1.0F), intValue9);
                     int intValue11 = ColorUtils.compute2(this.compute9(1.0F), intValue9);
                     float floatValue65 = (1.0F - floatValue64) * 8.0F * floatValue28;
                     float floatValue66 = floatValue57 + 10.0F * floatValue28 - floatValue65;
                     if (floatValue46 > 0.05F) {
                        renderManager2.invoke5(
                           floatValue66, floatValue62 + (floatValue34 - 8.0F * floatValue29) * 0.5F, floatValue46 * floatValue28, 8.0F * floatValue29, Math.max(0.7F, floatValue46 * 0.5F) * floatValue28, intValue11
                        );
                     }

                     floatValue66 += 8.0F * floatValue28;
                     if (flag4) {
                        float floatValue67 = 0.9F * floatValue30;
                        float floatValue68 = 16.0F * floatValue67;
                        float floatValue69 = floatValue62 + (floatValue34 - floatValue68) * 0.5F;
                        ITEMS_2.add(new CooldownsHud.CooldownsHudItemData(cooldownsHudItemState6.itemStack, floatValue66, floatValue69, floatValue67, intValue8));
                        floatValue66 += 20.0F * floatValue28;
                     }

                     renderManager2.invoke69(FontRegistry.fontObject, floatValue66, floatValue62 + floatValue34 * 0.5F + 4.0F * floatValue29, floatValue37, cooldownsHudItemState6.text, intValue10);
                     cooldownsHudItemState6.hudMetricUtils.invoke2(text3, cooldownsHudItemState6.timestamp);
                     float floatValue70 = floatValue59 + floatValue39 * 0.5F + floatValue65;
                     float floatValue71 = floatValue63 + floatValue34 * 0.5F + 4.0F * floatValue29;
                     cooldownsHudItemState6.hudMetricUtils
                        .invoke3(
                           renderManager2, FontRegistry.fontObject, floatValue59, floatValue63, floatValue39, floatValue34, Math.min(floatValue45, floatValue34 * 0.5F), floatValue70, floatValue71, floatValue37, intValue11
                        );
                     floatValue62 += floatValue34 * floatValue64;
                     floatValue63 += floatValue34 * floatValue64;
                  }
               }

               if (!ITEMS_2.isEmpty()) {
                  renderManager2.invoke20();

                  for (CooldownsHud.CooldownsHudItemData cooldownsHudItemData : ITEMS_2) {
                     ItemRenderUtil.invoke3(
                        renderManager2,
                        cooldownsHudItemData.stack,
                        ItemRenderUtil.measure(cooldownsHudItemData.x),
                        ItemRenderUtil.measure(cooldownsHudItemData.y),
                        ItemRenderUtil.measure3(cooldownsHudItemData.scale),
                        cooldownsHudItemData.seed,
                        false,
                        cooldownsHudItemData.seed
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
      } else {
         invoke5();
         ANIMATION.invoke(0.0);
         ITEMS.clear();
         ITEMS_2.clear();
      }
   }

   private static String resolve(float f) {
      int intValue12 = Math.max(0, Math.round(f * 10.0F));
      return intValue12 / 10 + "." + intValue12 % 10 + "s";
   }

   private static void invoke4() {
      if (MinecraftAccessor.a_.player == null) {
         invoke5();
      } else {
         for (CooldownsHud.CooldownsHudItemState cooldownsHudItemState7 : VALUES_BY_KEY.values()) {
            cooldownsHudItemState7.timestamp = 0L;
         }

         ItemCooldownManager itemCooldownManager = MinecraftAccessor.a_.player.getItemCooldownManager();
         ItemCooldownManagerAccessor itemCooldownManagerAccessor = (ItemCooldownManagerAccessor)itemCooldownManager;
         int intValue13 = itemCooldownManagerAccessor.wild$getTick();

         for (Entry entry : itemCooldownManagerAccessor.wild$getEntries().entrySet()) {
            long longValue = compute3(entry.getValue(), intValue13);
            if (longValue > 0L) {
               Item item3 = (Item)Registries.ITEM.get((Identifier)entry.getKey());
               if (item3 != null && item3 != Items.AIR) {
                  CooldownsHud.CooldownsHudItemState cooldownsHudItemState8 = VALUES_BY_KEY.computeIfAbsent(item3, CooldownsHud.CooldownsHudItemState::new);
                  cooldownsHudItemState8.setItemStack(resolve2(item3));
                  cooldownsHudItemState8.timestamp = Math.max(cooldownsHudItemState8.timestamp, longValue);
               }
            }
         }
      }
   }

   private static long compute2(ItemStack itemStack) {
      if (MinecraftAccessor.a_.player != null && itemStack != null && !itemStack.isEmpty()) {
         ItemCooldownManager itemCooldownManager2 = MinecraftAccessor.a_.player.getItemCooldownManager();
         ItemCooldownManagerAccessor itemCooldownManagerAccessor2 = (ItemCooldownManagerAccessor)itemCooldownManager2;
         Object object2 = itemCooldownManagerAccessor2.wild$getEntries().get(itemCooldownManager2.getGroup(itemStack));
         return object2 == null ? 0L : compute3(object2, itemCooldownManagerAccessor2.wild$getTick());
      } else {
         return 0L;
      }
   }

   private static long compute3(Object object, int i) {
      int intValue14 = ((ItemCooldownManagerEntryAccessor)object).wild$getEndTick() - i;
      return intValue14 > 0 ? intValue14 * 50L : 0L;
   }

   private static void invoke5() {
      if (!VALUES_BY_KEY.isEmpty()) {
         VALUES_BY_KEY.clear();
      }
   }

   static ItemStack resolve2(Item item) {
      if (MinecraftAccessor.a_.player != null) {
         for (int intValue15 = 0; intValue15 < 36; intValue15++) {
            ItemStack itemStack2 = MinecraftAccessor.a_.player.getInventory().getStack(intValue15);
            if (!itemStack2.isEmpty() && itemStack2.isOf(item)) {
               return itemStack2.copy();
            }
         }

         ItemStack itemStack3 = MinecraftAccessor.a_.player.getOffHandStack();
         if (!itemStack3.isEmpty() && itemStack3.isOf(item)) {
            return itemStack3.copy();
         }
      }

      if (item == Items.ENDER_PEARL) {
         ItemStack itemStack4 = ClickPearl.resolve2();
         if (!itemStack4.isEmpty()) {
            return itemStack4;
         }
      }

      return new ItemStack(item);
   }

   static String resolve3(ItemStack itemStack, Item item) {
      String text4 = itemStack.getName().getString();
      if (text4 != null && !text4.isBlank()) {
         return text4;
      } else {
         Identifier identifier = Registries.ITEM.getId(item);
         String text5 = identifier.getPath().replace('_', ' ');
         StringBuilder stringBuilder = new StringBuilder();

         for (String text6 : text5.split(" ")) {
            if (!text6.isEmpty()) {
               stringBuilder.append(Character.toUpperCase(text6.charAt(0))).append(text6.substring(1)).append(" ");
            }
         }

         return stringBuilder.toString().trim();
      }
   }

   static class CooldownsHudItemState {
      final Item item;
      ItemStack itemStack;
      String text;
      final Animation animation = new Animation();
      final HudMetricUtils hudMetricUtils = new HudMetricUtils();
      long timestamp;

      CooldownsHudItemState(Item item) {
         this.item = item;
         this.setItemStack(CooldownsHud.resolve2(item));
         this.animation.invoke(0.0);
      }

      void setItemStack(ItemStack itemStack) {
         this.itemStack = itemStack;
         this.text = CooldownsHud.resolve3(itemStack, this.item);
      }
   }

   record CooldownsHudItemData(ItemStack stack, float x, float y, float scale, int seed) {
   }
}
