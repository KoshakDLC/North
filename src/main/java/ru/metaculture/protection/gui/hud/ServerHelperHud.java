package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

@HudElementInfo(
   resolve = "ServerHelper",
   resolve2 = "w"
)
public final class ServerHelperHud extends HudElement {
   private static final ServerHelperHud INSTANCE = new ServerHelperHud();
   private static final MinecraftClient CLIENT = MinecraftClient.getInstance();
   private static final Animation ANIMATION = new Animation();
   private static final Animation ANIMATION_2 = new Animation();
   private static final Animation ANIMATION_3 = new Animation();
   private static final Animation ANIMATION_4 = new Animation();
   private static final Map<String, Animation> VALUES_BY_KEY = new HashMap<>();
   static final Map<Item, ItemStack> VALUES_BY_KEY_2 = new HashMap<>();
   private static boolean flag;
   private final BooleanSetting otobrazhatBindy = new BooleanSetting("Отображать бинды", true);
   private final List<ServerHelperHud.ServerHelperHudItemState> items = new ArrayList<>(12);
   private final List<ServerHelperHud.ServerHelperHudItemState> items2 = new ArrayList<>(12);
   private final List<ServerHelperHud.ServerHelperHudItemData> items3 = new ArrayList<>(12);

   private ServerHelperHud() {
      this.invoke(this.otobrazhatBindy);
      ru.metaculture.protection.HudPresetManager.invoke2(this);
   }

   private void invoke(List<ServerHelperHud.ServerHelperHudItemState> list, Item item, int i) {
      this.invoke2(list, item.getTranslationKey(), item, itemStack -> itemStack.isOf(item), i);
   }

   private void invoke2(List<ServerHelperHud.ServerHelperHudItemState> list, String string, Item item, Predicate<ItemStack> predicate, int i) {
      boolean flag = i != -1 && i != 0;
      String text = "";
      if (flag) {
         String text2 = i > 0 ? InputUtil.fromKeyCode(i, -1).getTranslationKey() : "";
         text = ServerHelper.instance.resolve3(i, text2);
      }

      list.add(new ServerHelperHud.ServerHelperHudItemState(string, item, predicate, text, flag));
   }

   private List<ServerHelperHud.ServerHelperHudItemState> resolveSelf() {
      this.items.clear();
      List items = this.items;
      ServerHelper serverHelper = ServerHelper.instance;
      if (serverHelper == null) {
         return items;
      } else {
         if (serverHelper.rezhimRaboty.is("FunTime")) {
            this.invoke2(
               items, "ft_disorientation", Items.ENDER_EYE, serverHelper.resolve(SpecialItemUtils::check33, "Дезориентация"), serverHelper.klavishaDezorientatsii.getKeyCode()
            );
            this.invoke2(items, "ft_light_dust", Items.SUGAR, serverHelper.resolve(SpecialItemUtils::check32, "Явная пыль"), serverHelper.klavishaYavnoyPyli.getKeyCode());
            this.invoke2(items, "ft_trap", Items.NETHERITE_SCRAP, serverHelper.resolve(SpecialItemUtils::check34, "Трапка"), serverHelper.klavishaTrapki.getKeyCode());
            this.invoke2(
               items,
               "ft_freezing_snowball",
               Items.SNOWBALL,
               serverHelper.resolve(SpecialItemUtils::check53, "Снежок заморозка"),
               serverHelper.klavishaSnezhkaZamorozki.getKeyCode()
            );
            this.invoke2(
               items, "ft_gods_aura", Items.PHANTOM_MEMBRANE, serverHelper.resolve(SpecialItemUtils::check54, "Божья аура"), serverHelper.klavishaBozheyAury.getKeyCode()
            );
            this.invoke2(items, "ft_plast", Items.DRIED_KELP, serverHelper.resolve(SpecialItemUtils::check36, "Пласт"), serverHelper.klavishaPlasta.getKeyCode());
            this.invoke2(
               items,
               "ft_potion_assassin",
               Items.SPLASH_POTION,
               serverHelper.resolve(SpecialItemUtils::check25, "Зелье Ассасина"),
               serverHelper.klavishaZelyaAssasina.getKeyCode()
            );
            this.invoke2(
               items,
               "ft_potion_paladin",
               Items.SPLASH_POTION,
               serverHelper.resolve(SpecialItemUtils::check29, "Зелье Паладина", "Зелье Палладина"),
               serverHelper.klavishaZelyaPaladina.getKeyCode()
            );
            this.invoke2(
               items, "ft_potion_sleep", Items.SPLASH_POTION, serverHelper.resolve(SpecialItemUtils::check31, "Снотворное"), serverHelper.klavishaZelyaSnotvornogo.getKeyCode()
            );
            this.invoke2(
               items, "ft_potion_wrath", Items.SPLASH_POTION, serverHelper.resolve(SpecialItemUtils::check26, "Зелье Гнева"), serverHelper.klavishaZelyaGneva.getKeyCode()
            );
            this.invoke2(
               items,
               "ft_potion_holy_water",
               Items.SPLASH_POTION,
               serverHelper.resolve(SpecialItemUtils::check28, "Святая вода"),
               serverHelper.klavishaZelyaSvyatayaVoda.getKeyCode()
            );
            this.invoke2(
               items, "ft_potion_radiation", Items.SPLASH_POTION, serverHelper.resolve(SpecialItemUtils::check30, "Зелье Радиации"), serverHelper.klavishaZelyaRadiatsii.getKeyCode()
            );
            this.invoke2(
               items, "ft_potion_hlopushka", Items.SPLASH_POTION, serverHelper.resolve(SpecialItemUtils::check27, "Хлопушка"), serverHelper.klavishaZelyaHlopushki.getKeyCode()
            );
         } else if (serverHelper.rezhimRaboty.is("HolyWorld")) {
            this.invoke2(items, "hw_trap", Items.POPPED_CHORUS_FRUIT, HolyWorldItemParser::check3, serverHelper.klavishaTrapki2.getKeyCode());
            this.invoke2(items, "hw_freezing_snowball", Items.SNOWBALL, HolyWorldItemParser::check4, serverHelper.klavishaSnezhkaZamorozki2.getKeyCode());
            this.invoke2(items, "hw_stan", Items.NETHER_STAR, HolyWorldItemParser::check5, serverHelper.klavishaStana.getKeyCode());
            this.invoke2(items, "hw_explosive_trap", Items.PRISMARINE_SHARD, HolyWorldItemParser::check6, serverHelper.klavishaVzryvnoyTrapki.getKeyCode());
         }

         this.invoke2(
            items, "utility_shulker", Items.SHULKER_BOX, itemStack -> itemStack.getItem().toString().contains("shulker_box"), serverHelper.klavishaShalkera.getKeyCode()
         );
         this.invoke(items, Items.WIND_CHARGE, serverHelper.klavishaVozduhana.getKeyCode());
         this.invoke(items, Items.CHORUS_FRUIT, serverHelper.horus.getKeyCode());
         return items;
      }
   }

   public static ServerHelperHud getINSTANCE() {
      return INSTANCE;
   }

   public static void invoke3(RenderManager renderManager, DrawContext drawContext) {
      INSTANCE.invoke4(renderManager, drawContext);
   }

   private void invoke4(RenderManager renderManager2, DrawContext drawContext) {
      if (CLIENT.player != null) {
         List items2 = this.resolveSelf();
         this.items2.clear();
         List items3 = this.items2;

         for (ServerHelperHud.ServerHelperHudItemState serverHelperHudItemState : (List<ServerHelperHud.ServerHelperHudItemState>)items2) {
            Animation animation = VALUES_BY_KEY.computeIfAbsent(serverHelperHudItemState.text, string -> new Animation());
            animation.check();
            animation.resolve4(serverHelperHudItemState.flag ? 1.0 : 0.0, 0.2F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
            if (animation.measure3() > 0.001F || serverHelperHudItemState.flag) {
               items3.add(serverHelperHudItemState);
            }
         }

         boolean flag2 = CLIENT.currentScreen instanceof ChatScreen;
         boolean flag3 = !items3.isEmpty() || flag2;
         ANIMATION.check();
         ANIMATION_2.check();
         ANIMATION.resolve4(flag3 ? 1.0 : 0.0, 0.18F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
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
            float floatValue2 = 7.0F;
            float floatValue3 = 46.0F;
            float floatValue4 = 5.0F;
            float floatValue5 = 0.0F;
            boolean flag4 = true;

            for (ServerHelperHud.ServerHelperHudItemState serverHelperHudItemState2 : (List<ServerHelperHud.ServerHelperHudItemState>)items3) {
               float floatValue6 = VALUES_BY_KEY.get(serverHelperHudItemState2.text).measure3();
               if (!(floatValue6 <= 0.01F)) {
                  if (!flag4) {
                     floatValue5 += floatValue4 * floatValue6;
                  }

                  floatValue5 += floatValue3 * floatValue6;
                  flag4 = false;
               }
            }

            if (items3.isEmpty()) {
               floatValue5 = floatValue3;
            }

            float floatValue7 = floatValue5 + floatValue2 * 2.0F;
            float floatValue8 = floatValue3 + floatValue2 * 2.0F;
            ANIMATION_3.check();
            ANIMATION_4.check();
            ANIMATION_3.resolve4(floatValue7, 0.18F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
            ANIMATION_4.resolve4(floatValue8, 0.18F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
            float floatValue9 = ANIMATION_3.measure3();
            float floatValue10 = ANIMATION_4.measure3();
            float floatValue11 = CLIENT.getWindow().getFramebufferWidth();
            float floatValue12 = CLIENT.getWindow().getFramebufferHeight();
            float floatValue13 = (floatValue11 - floatValue9) / 2.0F;
            float floatValue14 = floatValue12 - floatValue10 - 60.0F;
            HudEditorRenderer.HudEditorRendererState hudEditorRendererState = HudEditorRenderer.getINSTANCE().resolve("HUD_ServerHelper", floatValue13, floatValue14, floatValue9, floatValue10);
            float floatValue15 = hudEditorRendererState.floatValue + ANIMATION_2.measure3();
            float floatValue16 = hudEditorRendererState.floatValue2;
            float floatValue17 = hudEditorRendererState.floatValue3;
            float floatValue18 = hudEditorRendererState.floatValue4;
            this.invoke3(floatValue15, floatValue16, floatValue17, floatValue18);
            float floatValue19 = floatValue17 / Math.max(1.0F, floatValue9);
            float floatValue20 = floatValue18 / Math.max(1.0F, floatValue10);
            float floatValue21 = Math.min(floatValue19, floatValue20);
            float floatValue22 = floatValue3 * floatValue21;
            float floatValue23 = floatValue4 * floatValue19;
            float floatValue24 = floatValue5 * floatValue19;
            float floatValue25 = floatValue * this.prozrachnost.getValue();
            float floatValue26 = this.measure(floatValue25);
            int intValue = this.compute(floatValue25);
            int intValue2 = this.compute5(floatValue25);
            boolean flag5 = this.check8();
            float floatValue27 = 14.0F;
            this.invoke(renderManager2, floatValue15, floatValue16, floatValue17, floatValue18, floatValue27, floatValue25);
            renderManager2.invoke24(floatValue15, floatValue16, floatValue17, floatValue18, floatValue27, floatValue27, floatValue27, floatValue27);
            float floatValue28 = floatValue16 + (floatValue18 - floatValue22) / 2.0F;
            float floatValue29 = floatValue15 + (floatValue17 - floatValue24) / 2.0F;
            this.items3.clear();
            flag4 = true;

            for (ServerHelperHud.ServerHelperHudItemState serverHelperHudItemState3 : (List<ServerHelperHud.ServerHelperHudItemState>)items3) {
               float floatValue30 = VALUES_BY_KEY.get(serverHelperHudItemState3.text).measure3();
               if (!(floatValue30 <= 0.01F)) {
                  if (!flag4) {
                     floatValue29 += floatValue23 * floatValue30;
                  }

                  flag4 = false;
                  int intValue3 = (int)(255.0F * floatValue25 * floatValue30);
                  int intValue4 = this.check7() ? ColorUtils.compute43(255, 255, 255, (int)(5.0F * floatValue26 * floatValue30)) : this.compute2(floatValue26 * floatValue30);
                  float floatValue31 = (1.0F - floatValue30) * 8.0F * floatValue20;
                  float floatValue32 = floatValue28 + floatValue31;
                  if (!flag5 && !this.check9() && !this.check10()) {
                     renderManager2.invoke5(floatValue29, floatValue32, floatValue22, floatValue22, 6.0F * floatValue21, intValue4);
                  } else {
                     this.invoke2(renderManager2, floatValue29, floatValue32, floatValue22, floatValue22, 6.0F * floatValue21, floatValue25 * floatValue30);
                  }

                  int intValue5 = this.compute(serverHelperHudItemState3);
                  ItemStack itemStack2 = this.resolve2(serverHelperHudItemState3);
                  if (this.otobrazhatBindy.isEnabled()) {
                     int intValue6 = this.compute7(floatValue25 * floatValue30);
                     renderManager2.invoke69(FontRegistry.fontObject4, floatValue29 + 4.0F * floatValue19, floatValue32 + 12.0F * floatValue20, 16.0F * floatValue21, serverHelperHudItemState3.text2, intValue6);
                  }

                  float floatValue33 = 1.3F * floatValue21;
                  float floatValue34 = 16.0F * floatValue33;
                  float floatValue35 = floatValue29 + (floatValue22 - floatValue34) / 2.0F;
                  float floatValue36 = floatValue32 + (floatValue22 - floatValue34) / 2.0F;
                  String text3 = String.valueOf(intValue5);
                  int intValue7 = intValue5 > 0 ? ColorUtils.compute43(200, 200, 200, intValue3) : ColorUtils.compute43(255, 60, 60, intValue3);
                  float floatValue37 = (this.otobrazhatBindy.isEnabled() ? 18.0F : 23.0F) * floatValue21;
                  float floatValue38 = TextMeasureCache.resolve(FontRegistry.fontObject4, text3, floatValue37).floatValue;
                  float floatValue39 = this.otobrazhatBindy.isEnabled() ? 4.0F : 5.0F;
                  long longValue = CooldownsHud.compute(serverHelperHudItemState3.item);
                  String text4 = longValue > 0L ? resolve3(longValue) : "";
                  float floatValue40 = measure(text4, 20.0F * floatValue21, floatValue22 - 6.0F * floatValue19, floatValue21);
                  float floatValue41 = text4.isEmpty() ? 0.0F : TextMeasureCache.measure(FontRegistry.fontObject4, text4, floatValue40);
                  this.items3
                     .add(
                        new ServerHelperHud.ServerHelperHudItemData(
                           itemStack2,
                           floatValue29,
                           floatValue32,
                           floatValue22,
                           6.0F * floatValue21,
                           floatValue35,
                           floatValue36,
                           floatValue33,
                           floatValue25 * floatValue30,
                           floatValue29 + floatValue22 - floatValue38 - 4.0F * floatValue19,
                           floatValue32 + floatValue22 - floatValue39 * floatValue20,
                           floatValue37,
                           text3,
                           intValue7,
                           text4,
                           floatValue29 + (floatValue22 - floatValue41) * 0.5F,
                           floatValue32 + floatValue22 * 0.5F + 5.0F * floatValue21,
                           floatValue40,
                           ColorUtils.compute2(this.compute8(floatValue25 * floatValue30), intValue3)
                        )
                     );
                  floatValue29 += floatValue22 * floatValue30;
               }
            }

            renderManager2.invoke20();

            for (int intValue8 = 0; intValue8 < this.items3.size(); intValue8++) {
               ServerHelperHud.ServerHelperHudItemData serverHelperHudItemData = this.items3.get(intValue8);
               if (serverHelperHudItemData.alpha >= 0.35F) {
                  ItemRenderUtil.invoke3(renderManager2, serverHelperHudItemData.stack, serverHelperHudItemData.itemX, serverHelperHudItemData.itemY, serverHelperHudItemData.itemScale, intValue8, false, 0);
               }
            }

            renderManager2.invoke20();

            for (ServerHelperHud.ServerHelperHudItemData serverHelperHudItemData2 : this.items3) {
               if (serverHelperHudItemData2.hasCooldown()) {
                  renderManager2.invoke51(serverHelperHudItemData2.slotX, serverHelperHudItemData2.slotY, serverHelperHudItemData2.slotSize, serverHelperHudItemData2.slotSize, 8.0F * serverHelperHudItemData2.itemScale);
                  renderManager2.invoke47(serverHelperHudItemData2.slotX, serverHelperHudItemData2.slotY, serverHelperHudItemData2.slotSize, serverHelperHudItemData2.slotSize, serverHelperHudItemData2.slotRadius, serverHelperHudItemData2.alpha);
                  renderManager2.invoke5(
                     serverHelperHudItemData2.slotX,
                     serverHelperHudItemData2.slotY,
                     serverHelperHudItemData2.slotSize,
                     serverHelperHudItemData2.slotSize,
                     serverHelperHudItemData2.slotRadius,
                     ColorUtils.compute43(0, 0, 0, (int)(116.0F * serverHelperHudItemData2.alpha))
                  );
               }
            }

            for (ServerHelperHud.ServerHelperHudItemData serverHelperHudItemData3 : this.items3) {
               if (serverHelperHudItemData3.hasCooldown()) {
                  int intValue9 = ColorUtils.compute43(0, 0, 0, (int)(130.0F * serverHelperHudItemData3.alpha));
                  renderManager2.invoke69(FontRegistry.fontObject4, serverHelperHudItemData3.cooldownX + 1.0F, serverHelperHudItemData3.cooldownY + 1.0F, serverHelperHudItemData3.cooldownFont, serverHelperHudItemData3.cooldown, intValue9);
                  renderManager2.invoke69(FontRegistry.fontObject4, serverHelperHudItemData3.cooldownX, serverHelperHudItemData3.cooldownY, serverHelperHudItemData3.cooldownFont, serverHelperHudItemData3.cooldown, serverHelperHudItemData3.cooldownColor);
               } else {
                  renderManager2.invoke69(FontRegistry.fontObject4, serverHelperHudItemData3.countX, serverHelperHudItemData3.countY, serverHelperHudItemData3.countFont, serverHelperHudItemData3.count, serverHelperHudItemData3.countColor);
               }
            }

            renderManager2.invoke20();
            renderManager2.invoke25();
            HudEditorRenderer.getINSTANCE().invoke6(hudEditorRendererState);
            HudSettingsRenderer.invoke2(
               renderManager2, this, hudEditorRendererState, HudEditorRenderer.getINSTANCE(), CLIENT.getWindow().getScaledWidth(), CLIENT.getWindow().getScaledHeight()
            );
         }
      }
   }

   private int compute(ServerHelperHud.ServerHelperHudItemState serverHelperHudItemState4) {
      if (CLIENT.player == null) {
         return 0;
      } else {
         int intValue10 = 0;

         for (int intValue11 = 0; intValue11 < CLIENT.player.getInventory().size(); intValue11++) {
            ItemStack itemStack3 = CLIENT.player.getInventory().getStack(intValue11);
            if (!itemStack3.isEmpty() && serverHelperHudItemState4.check(itemStack3)) {
               intValue10 += itemStack3.getCount();
            }
         }

         return intValue10;
      }
   }

   private ItemStack resolve2(ServerHelperHud.ServerHelperHudItemState serverHelperHudItemState5) {
      if (serverHelperHudItemState5.text.startsWith("ft_potion_")) {
         return serverHelperHudItemState5.itemStack;
      } else {
         if (CLIENT.player != null) {
            for (int intValue12 = 0; intValue12 < CLIENT.player.getInventory().size(); intValue12++) {
               ItemStack itemStack4 = CLIENT.player.getInventory().getStack(intValue12);
               if (!itemStack4.isEmpty() && serverHelperHudItemState5.check(itemStack4)) {
                  return itemStack4;
               }
            }
         }

         return serverHelperHudItemState5.itemStack;
      }
   }

   private static String resolve3(long l) {
      int intValue13 = Math.max(1, (int)Math.ceil(l / 1000.0));
      return intValue13 + "сек";
   }

   private static float measure(String string, float f, float g, float h) {
      if (string != null && !string.isEmpty()) {
         float floatValue42 = TextMeasureCache.measure(FontRegistry.fontObject4, string, f);
         return floatValue42 <= g ? f : Math.max(12.0F * h, f * g / Math.max(1.0F, floatValue42));
      } else {
         return f;
      }
   }

   record ServerHelperHudItemData(
      ItemStack stack,
      float slotX,
      float slotY,
      float slotSize,
      float slotRadius,
      float itemX,
      float itemY,
      float itemScale,
      float alpha,
      float countX,
      float countY,
      float countFont,
      String count,
      int countColor,
      String cooldown,
      float cooldownX,
      float cooldownY,
      float cooldownFont,
      int cooldownColor
   ) {

      boolean hasCooldown() {
         return this.cooldown != null && !this.cooldown.isEmpty();
      }
   }

   static class ServerHelperHudItemState {
      final String text;
      final Item item;
      final Predicate<ItemStack> predicate;
      final String text2;
      final boolean flag;
      final ItemStack itemStack;

      ServerHelperHudItemState(String string, Item item, Predicate<ItemStack> predicate, String string2, boolean bl) {
         this.text = string;
         this.item = item;
         this.predicate = predicate;
         this.text2 = string2;
         this.flag = bl;
         this.itemStack = ServerHelperHud.VALUES_BY_KEY_2.computeIfAbsent(item, ItemStack::new);
      }

      boolean check(ItemStack itemStack) {
         try {
            return this.predicate.test(itemStack);
         } catch (Throwable exception) {
            return false;
         }
      }
   }
}
