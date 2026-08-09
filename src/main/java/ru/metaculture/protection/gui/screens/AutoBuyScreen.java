package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class AutoBuyScreen extends Screen {
   private static volatile boolean flag = false;
   private static final String[] FUNTIME = new String[]{"FunTime", "SpookyTime", "HolyWorld"};
   private static final String[] FT = new String[]{"FT", "SP", "HW"};
   private final List<String> items = Arrays.asList(
      "Сфера Хаоса",
      "Сфера Титана",
      "Сфера Ареса",
      "Сфера Бестии",
      "Талисман Демона",
      "Талисман Карателя",
      "Шлем Крушителя",
      "Нагрудник Крушителя",
      "Поножи Крушителя",
      "Ботинки Крушителя",
      "Меч Крушителя",
      "Кирка Крушителя",
      "Лук Крушителя",
      "Арбалет Крушителя",
      "Трезубец Крушителя",
      "Булава Крушителя",
      "Элитры Крушителя",
      "Удочка Крушителя"
   );
   private final List<String> items2 = Arrays.asList("Явная Пыль", "Дезориентация", "Трапка", "Отмычка к Сферам");
   private final List<String> items3 = HolyWorldItemParser.getITEMS().stream().map(HolyWorldItemParser.HolyWorldItemParserDisplayEntry::key).toList();
   private final List<AutoBuyScreen.AutoBuyScreenState> items4 = new ArrayList<>();
   private final List<AutoBuyScreen.AutoBuyScreenState> items5 = new ArrayList<>();
   private AutoBuyScreen.AutoBuyScreenState autoBuyScreenState = null;
   private float floatValue = 0.0F;
   private float floatValue2 = 0.0F;
   private float floatValue3 = 0.0F;
   private String text = null;
   private float floatValue4;
   private float floatValue5;
   private float floatValue6;
   private float floatValue7;
   private final int intValue = RenderManager.RenderManagerState.compute37(21, 23, 30, 120);
   private final int intValue2 = RenderManager.RenderManagerState.compute37(12, 43, 64, 150);
   private final int intValue3 = RenderManager.RenderManagerState.compute37(24, 88, 124, 255);
   private final int intValue4 = RenderManager.RenderManagerState.compute37(0, 0, 0, 70);

   public AutoBuyScreen() {
      super(Text.literal("AutoBuy Panel"));
      this.invoke();
      invoke2();
   }

   private void invoke() {
      this.items4.clear();
      this.items5.clear();
      AutoBuy.VALUES_BY_KEY.forEach((string, long_) -> this.items4.add(new AutoBuyScreen.AutoBuyScreenState(string, String.valueOf(long_))));
      AutoBuy.ITEMS.forEach(string -> this.items5.add(new AutoBuyScreen.AutoBuyScreenState(string, "")));
   }

   public static void invoke2() {
      if (!flag) {
         flag = true;
         EventManager.register(
            new Object() {
               @EventHandler
               public void onHudRender(HudRenderEvent hudRenderEvent) {
                  MinecraftClient client = hudRenderEvent.getClient();
                  if (client != null && client.currentScreen instanceof AutoBuyScreen autoBuyScreen && client.getWindow() != null) {
                     int intValue = (int)(client.mouse.getX() * client.getWindow().getScaledWidth() / client.getWindow().getFramebufferWidth());
                     int intValue2 = (int)(client.mouse.getY() * client.getWindow().getScaledHeight() / client.getWindow().getFramebufferHeight());
                     BlurRenderer blurRenderer = BlurRenderer.getINSTANCE();
                     boolean flag = blurRenderer.check2((Object)autoBuyScreen)
                        && blurRenderer.check3(client.getWindow().getFramebufferWidth(), client.getWindow().getFramebufferHeight());

                     try {
                        autoBuyScreen.invoke3(hudRenderEvent.getRenderManager(), hudRenderEvent.getDrawContext(), intValue, intValue2);
                        hudRenderEvent.getRenderManager().invoke20();
                     } finally {
                        if (flag) {
                           blurRenderer.invoke5();
                        }
                     }
                  }
               }
            }
         );
      }
   }

   public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
      super.render(context, mouseX, mouseY, deltaTicks);
   }

   public void invoke3(RenderManager renderManager, DrawContext drawContext, int i, int j) {
      AutoBuy autoBuy = (AutoBuy)WildClient.INSTANCE.moduleManager.findModule(AutoBuy.class);
      if (autoBuy != null && autoBuy.enabled) {
         float floatValue = (float)this.client.getWindow().getFramebufferWidth() / this.client.getWindow().getScaledWidth();
         float floatValue2 = 350.0F;
         float floatValue3 = 180.0F;
         float floatValue4 = 15.0F;
         float floatValue5 = floatValue2 + floatValue4 + floatValue3;
         float floatValue6 = 260.0F;
         float floatValue7 = (this.client.getWindow().getScaledWidth() - floatValue5) / 2.0F;
         float floatValue8 = (this.client.getWindow().getScaledHeight() - floatValue6) / 2.0F;
         renderManager.invoke58(floatValue);
         float floatValue9 = floatValue8;
         renderManager.invoke48(23.0F);
         renderManager.invoke44(floatValue7, floatValue8, floatValue2, floatValue6, 6.0F, (float)this.intValue);
         renderManager.invoke5(floatValue7, floatValue8, floatValue2, floatValue6, 6.0F, this.intValue);
         float floatValue10 = floatValue7 + floatValue2 + floatValue4;
         renderManager.invoke48(23.0F);
         renderManager.invoke44(floatValue10, floatValue8, floatValue3, floatValue6, 6.0F, (float)this.intValue);
         renderManager.invoke5(floatValue10, floatValue8, floatValue3, floatValue6, 6.0F, this.intValue);
         float floatValue11 = 13.0F;
         float floatValue12 = RenderManager.resolve7(FontRegistry.fontObject4, "D", floatValue11).floatValue;
         float floatValue13 = RenderManager.resolve7(FontRegistry.fontObject, "Autobuy |", floatValue11).floatValue;
         float floatValue14 = floatValue12 + 4.0F + floatValue13 + 8.0F;

         for (String text : FT) {
            floatValue14 += RenderManager.resolve7(FontRegistry.fontObject4, text, floatValue11).floatValue + 12.0F;
         }

         renderManager.invoke5(floatValue7 + 10.0F, floatValue8 + 10.0F, floatValue14 + 10.0F, 24.0F, 6.0F, RenderManager.RenderManagerState.compute37(20, 20, 25, 200));
         renderManager.invoke69(FontRegistry.fontObject4, floatValue7 + 18.0F, floatValue8 + 15.0F, floatValue11, "D", RenderManager.RenderManagerState.compute37(80, 90, 160, 255));
         renderManager.invoke69(
            FontRegistry.fontObject, floatValue7 + 18.0F + floatValue12 + 4.0F, floatValue8 + 15.0F, floatValue11, "Autobuy |", RenderManager.RenderManagerState.compute37(100, 100, 100, 255)
         );
         float floatValue15 = floatValue7 + 18.0F + floatValue12 + 4.0F + floatValue13 + 10.0F;

         for (int intValue3 = 0; intValue3 < FUNTIME.length; intValue3++) {
            boolean flag2 = autoBuy.rezhimServera.getValue().equals(FUNTIME[intValue3]);
            renderManager.invoke69(
               FontRegistry.fontObject4, floatValue15, floatValue9 + 15.0F, floatValue11, FT[intValue3], flag2 ? -1 : RenderManager.RenderManagerState.compute37(120, 120, 120, 255)
            );
            floatValue15 += RenderManager.resolve7(FontRegistry.fontObject4, FT[intValue3], floatValue11).floatValue + 12.0F;
         }

         float floatValue16 = RenderManager.resolve7(FontRegistry.fontObject, "Autopars", floatValue11).floatValue;
         renderManager.invoke5(floatValue10 + 10.0F, floatValue8 + 10.0F, floatValue12 + 4.0F + floatValue16 + 16.0F, 24.0F, 6.0F, RenderManager.RenderManagerState.compute37(20, 20, 25, 200));
         renderManager.invoke69(FontRegistry.fontObject4, floatValue10 + 18.0F, floatValue8 + 15.0F, floatValue11, "D", RenderManager.RenderManagerState.compute37(80, 90, 160, 255));
         renderManager.invoke69(
            FontRegistry.fontObject, floatValue10 + 18.0F + floatValue12 + 4.0F, floatValue8 + 15.0F, floatValue11, "Autopars", RenderManager.RenderManagerState.compute37(100, 100, 100, 255)
         );
         float floatValue17 = floatValue9 + 45.0F;
         float floatValue18 = floatValue6 - 55.0F;
         float floatValue19 = 160.0F;
         float floatValue20 = 160.0F;
         float floatValue21 = floatValue7 + 10.0F;
         float floatValue22 = floatValue21 + floatValue19 + 10.0F;
         float floatValue23 = floatValue10 + 10.0F;
         float floatValue24 = floatValue3 - 20.0F;
         renderManager.invoke5(floatValue21, floatValue17, floatValue19 + 10.0F + floatValue20, floatValue18, 6.0F, this.intValue2);
         renderManager.invoke5(floatValue23, floatValue17, floatValue24, floatValue18, 6.0F, this.intValue2);
         List items = this.resolve(autoBuy);
         renderManager.invoke24(floatValue21, floatValue17, floatValue19, floatValue18, 8.0F, 8.0F, 8.0F, 8.0F);
         float floatValue25 = 32.0F;
         float floatValue26 = 8.0F;
         int intValue4 = (int)((floatValue19 - 16.0F) / (floatValue25 + floatValue26));
         float floatValue27 = floatValue21 + 10.0F;
         float floatValue28 = floatValue17 + 10.0F + this.floatValue;

         for (int intValue5 = 0; intValue5 < items.size(); intValue5++) {
            float floatValue29 = floatValue27 + intValue5 % intValue4 * (floatValue25 + floatValue26);
            float floatValue30 = floatValue28 + intValue5 / intValue4 * (floatValue25 + floatValue26);
            renderManager.invoke5(floatValue29, floatValue30, floatValue25, floatValue25, 6.0F, this.intValue4);
            if (drawContext != null) {
               SpecialItemIconRenderer.invoke(drawContext, (String)items.get(intValue5), floatValue29 + 8.0F, floatValue30 + 8.0F);
            }
         }

         renderManager.invoke25();
         renderManager.invoke24(floatValue22, floatValue17, floatValue20, floatValue18, 8.0F, 8.0F, 8.0F, 8.0F);
         float floatValue31 = floatValue17 + 10.0F + this.floatValue2;

         for (int intValue6 = this.items4.size() - 1; intValue6 >= 0; intValue6--) {
            AutoBuyScreen.AutoBuyScreenState autoBuyScreenState = this.items4.get(intValue6);
            autoBuyScreenState.animation.check();
            autoBuyScreenState.animation.resolve4(autoBuyScreenState.flag ? 0.0 : 1.0, 0.2F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
            if (autoBuyScreenState.flag && autoBuyScreenState.animation.measure3() < 0.01F) {
               AutoBuy.VALUES_BY_KEY.remove(autoBuyScreenState.text);
               this.items4.remove(intValue6);
            } else {
               float floatValue32 = 46.0F * autoBuyScreenState.animation.measure3();
               float floatValue33 = floatValue22 + 8.0F;
               renderManager.invoke5(floatValue33, floatValue31, floatValue20 - 16.0F, floatValue32, 6.0F, this.intValue3);
               renderManager.invoke5(floatValue33 + 6.0F, floatValue31 + 6.0F, 34.0F, 34.0F, 4.0F, this.intValue4);
               if (drawContext != null) {
                  SpecialItemIconRenderer.invoke(drawContext, autoBuyScreenState.text, floatValue33 + 15.0F, floatValue31 + 15.0F);
               }

               renderManager.invoke24(floatValue33, floatValue31, floatValue20 - 16.0F, floatValue32, 6.0F, 6.0F, 6.0F, 6.0F);
               renderManager.invoke69(FontRegistry.fontObject, floatValue33 + 48.0F, floatValue31 + 10.0F, floatValue11, HolyWorldItemParser.resolve2(autoBuyScreenState.text), -1);
               renderManager.invoke69(
                  FontRegistry.fontObject, floatValue33 + 48.0F, floatValue31 + 26.0F, 11.0F, "Цена: ", RenderManager.RenderManagerState.compute37(180, 180, 180, 255)
               );
               float floatValue34 = floatValue33 + 48.0F + RenderManager.resolve7(FontRegistry.fontObject, "Цена: ", 11.0F).floatValue;
               boolean flag3 = this.autoBuyScreenState == autoBuyScreenState;
               renderManager.invoke5(floatValue34, floatValue31 + 24.0F, 60.0F, 16.0F, 4.0F, RenderManager.RenderManagerState.compute37(15, 20, 25, 200));
               if (flag3) {
                  renderManager.invoke28(floatValue34, floatValue31 + 24.0F, 60.0F, 16.0F, 4.0F, RenderManager.RenderManagerState.compute37(80, 150, 220, 255), 1.0F);
               }

               String text2 = autoBuyScreenState.text2 + (flag3 && System.currentTimeMillis() % 1000L > 500L ? "_" : "");
               renderManager.invoke69(FontRegistry.fontObject, floatValue34 + 4.0F, floatValue31 + 26.0F, 11.0F, text2, -1);
               renderManager.invoke25();
               floatValue31 += floatValue32 + 8.0F;
            }
         }

         renderManager.invoke25();
         renderManager.invoke24(floatValue23, floatValue17, floatValue24, floatValue18, 8.0F, 8.0F, 8.0F, 8.0F);
         if (this.items5.isEmpty()) {
            float floatValue35 = floatValue23 + floatValue24 / 2.0F;
            renderManager.invoke69(
               FontRegistry.fontObject,
               floatValue35 - RenderManager.resolve7(FontRegistry.fontObject, "ДЛЯ ПАРСА ЦЕНЫ У", 11.0F).floatValue / 2.0F,
               floatValue17 + floatValue18 / 2.0F - 18.0F,
               11.0F,
               "ДЛЯ ПАРСА ЦЕНЫ У",
               RenderManager.RenderManagerState.compute37(80, 110, 130, 180)
            );
            renderManager.invoke69(
               FontRegistry.fontObject,
               floatValue35 - RenderManager.resolve7(FontRegistry.fontObject, "ПРЕДМЕТА,", 11.0F).floatValue / 2.0F,
               floatValue17 + floatValue18 / 2.0F - 4.0F,
               11.0F,
               "ПРЕДМЕТА,",
               RenderManager.RenderManagerState.compute37(80, 110, 130, 180)
            );
            renderManager.invoke69(
               FontRegistry.fontObject,
               floatValue35 - RenderManager.resolve7(FontRegistry.fontObject, "ПЕРЕМЕСТИТЕ ЕГО В", 11.0F).floatValue / 2.0F,
               floatValue17 + floatValue18 / 2.0F + 10.0F,
               11.0F,
               "ПЕРЕМЕСТИТЕ ЕГО В",
               RenderManager.RenderManagerState.compute37(80, 110, 130, 180)
            );
            renderManager.invoke69(
               FontRegistry.fontObject,
               floatValue35 - RenderManager.resolve7(FontRegistry.fontObject, "ОДНУ ИЗ ЯЧЕЕК", 11.0F).floatValue / 2.0F,
               floatValue17 + floatValue18 / 2.0F + 24.0F,
               11.0F,
               "ОДНУ ИЗ ЯЧЕЕК",
               RenderManager.RenderManagerState.compute37(80, 110, 130, 180)
            );
         } else {
            float floatValue36 = floatValue17 + 10.0F + this.floatValue3;

            for (int intValue7 = this.items5.size() - 1; intValue7 >= 0; intValue7--) {
               AutoBuyScreen.AutoBuyScreenState autoBuyScreenState2 = this.items5.get(intValue7);
               autoBuyScreenState2.animation.check();
               autoBuyScreenState2.animation.resolve4(autoBuyScreenState2.flag ? 0.0 : 1.0, 0.2F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
               if (autoBuyScreenState2.flag && autoBuyScreenState2.animation.measure3() < 0.01F) {
                  AutoBuy.ITEMS.remove(autoBuyScreenState2.text);
                  this.items5.remove(intValue7);
               } else {
                  float floatValue37 = 46.0F * autoBuyScreenState2.animation.measure3();
                  float floatValue38 = floatValue23 + 8.0F;
                  renderManager.invoke5(floatValue38, floatValue36, floatValue24 - 16.0F, floatValue37, 6.0F, this.intValue3);
                  renderManager.invoke5(floatValue38 + 6.0F, floatValue36 + 6.0F, 34.0F, 34.0F, 4.0F, this.intValue4);
                  if (drawContext != null) {
                     SpecialItemIconRenderer.invoke(drawContext, autoBuyScreenState2.text, floatValue38 + 15.0F, floatValue36 + 15.0F);
                  }

                  renderManager.invoke24(floatValue38, floatValue36, floatValue24 - 16.0F, floatValue37, 6.0F, 6.0F, 6.0F, 6.0F);
                  renderManager.invoke69(FontRegistry.fontObject, floatValue38 + 48.0F, floatValue36 + 17.0F, floatValue11, HolyWorldItemParser.resolve2(autoBuyScreenState2.text), -1);
                  renderManager.invoke25();
                  floatValue36 += floatValue37 + 8.0F;
               }
            }
         }

         renderManager.invoke25();
         if (this.text != null) {
            renderManager.invoke5(this.floatValue4, this.floatValue5, 32.0F, 32.0F, 6.0F, RenderManager.RenderManagerState.compute37(255, 255, 255, 180));
            if (drawContext != null) {
               SpecialItemIconRenderer.invoke(drawContext, this.text, this.floatValue4 + 8.0F, this.floatValue5 + 8.0F);
            }

            this.floatValue4 = i - this.floatValue6;
            this.floatValue5 = j - this.floatValue7;
         }

         renderManager.invoke57();
      } else {
         this.client.setScreen(null);
      }
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      AutoBuy autoBuy2 = (AutoBuy)WildClient.INSTANCE.moduleManager.findModule(AutoBuy.class);
      float floatValue39 = 350.0F;
      float floatValue40 = 180.0F;
      float floatValue41 = 15.0F;
      float floatValue42 = floatValue39 + floatValue41 + floatValue40;
      float floatValue43 = 260.0F;
      float floatValue44 = (this.width - floatValue42) / 2.0F;
      float floatValue45 = (this.height - floatValue43) / 2.0F;
      float floatValue46 = floatValue45;
      float floatValue47 = floatValue44 + floatValue39 + floatValue41;
      float floatValue48 = 13.0F;
      float floatValue49 = RenderManager.resolve7(FontRegistry.fontObject4, "Litka", floatValue48).floatValue;
      float floatValue50 = RenderManager.resolve7(FontRegistry.fontObject, "Autobuy |", floatValue48).floatValue;
      float floatValue51 = floatValue44 + 18.0F + floatValue49 + 4.0F + floatValue50 + 10.0F;

      for (int intValue8 = 0; intValue8 < FUNTIME.length; intValue8++) {
         float floatValue52 = RenderManager.resolve7(FontRegistry.fontObject4, FT[intValue8], floatValue48).floatValue;
         if (mouseX >= floatValue51 - 4.0F && mouseX <= floatValue51 + floatValue52 + 6.0F && mouseY >= floatValue46 + 10.0F && mouseY <= floatValue46 + 34.0F) {
            autoBuy2.rezhimServera.value = FUNTIME[intValue8];
            autoBuy2.rezhimServera.selectedIndex = autoBuy2.rezhimServera.options.indexOf(FUNTIME[intValue8]);
            return true;
         }

         floatValue51 += floatValue52 + 12.0F;
      }

      float floatValue53 = floatValue46 + 45.0F;
      float floatValue54 = floatValue43 - 55.0F;
      float floatValue55 = floatValue44 + 10.0F;
      float floatValue56 = 160.0F;
      float floatValue57 = floatValue55 + floatValue56 + 10.0F;
      float floatValue58 = 160.0F;
      float floatValue59 = floatValue47 + 10.0F;
      float floatValue60 = floatValue40 - 20.0F;
      if (button == 1) {
         if (mouseX >= floatValue57 && mouseX <= floatValue57 + floatValue58 && mouseY >= floatValue53 && mouseY <= floatValue53 + floatValue54) {
            float floatValue61 = floatValue53 + 10.0F + this.floatValue2;

            for (AutoBuyScreen.AutoBuyScreenState autoBuyScreenState3 : this.items4) {
               if (mouseY >= floatValue61 && mouseY <= floatValue61 + 46.0F) {
                  autoBuyScreenState3.flag = true;
                  return true;
               }

               floatValue61 += 54.0F;
            }
         }

         if (mouseX >= floatValue59 && mouseX <= floatValue59 + floatValue60 && mouseY >= floatValue53 && mouseY <= floatValue53 + floatValue54) {
            float floatValue62 = floatValue53 + 10.0F + this.floatValue3;

            for (AutoBuyScreen.AutoBuyScreenState autoBuyScreenState4 : this.items5) {
               if (mouseY >= floatValue62 && mouseY <= floatValue62 + 46.0F) {
                  autoBuyScreenState4.flag = true;
                  return true;
               }

               floatValue62 += 54.0F;
            }
         }
      }

      this.autoBuyScreenState = null;
      if (button == 0) {
         float floatValue63 = floatValue53 + 10.0F + this.floatValue2;

         for (AutoBuyScreen.AutoBuyScreenState autoBuyScreenState5 : this.items4) {
            if (mouseY >= floatValue63 + 24.0F && mouseY <= floatValue63 + 40.0F && mouseX >= floatValue57 + 48.0F && mouseX <= floatValue57 + 150.0F) {
               this.autoBuyScreenState = autoBuyScreenState5;
               return true;
            }

            floatValue63 += 54.0F;
         }
      }

      if (button == 0 && mouseX >= floatValue55 && mouseX <= floatValue55 + floatValue56 && mouseY >= floatValue53 && mouseY <= floatValue53 + floatValue54) {
         List items2 = this.resolve(autoBuy2);
         float floatValue64 = 32.0F;
         float floatValue65 = 8.0F;
         int intValue9 = (int)((floatValue56 - 16.0F) / (floatValue64 + floatValue65));
         float floatValue66 = floatValue55 + 10.0F;
         float floatValue67 = floatValue53 + 10.0F + this.floatValue;

         for (int intValue10 = 0; intValue10 < items2.size(); intValue10++) {
            float floatValue68 = floatValue66 + intValue10 % intValue9 * (floatValue64 + floatValue65);
            float floatValue69 = floatValue67 + intValue10 / intValue9 * (floatValue64 + floatValue65);
            if (mouseX >= floatValue68 && mouseX <= floatValue68 + floatValue64 && mouseY >= floatValue69 && mouseY <= floatValue69 + floatValue64) {
               this.text = (String)items2.get(intValue10);
               this.floatValue6 = (float)mouseX - floatValue68;
               this.floatValue7 = (float)mouseY - floatValue69;
               this.floatValue4 = floatValue68;
               this.floatValue5 = floatValue69;
               return true;
            }
         }
      }

      return super.mouseClicked(mouseX, mouseY, button);
   }

   public boolean mouseReleased(double mouseX, double mouseY, int button) {
      if (this.text != null && button == 0) {
         float floatValue70 = 350.0F;
         float floatValue71 = 180.0F;
         float floatValue72 = 15.0F;
         float floatValue73 = floatValue70 + floatValue72 + floatValue71;
         float floatValue74 = 260.0F;
         float floatValue75 = (this.width - floatValue73) / 2.0F;
         float floatValue76 = (this.height - floatValue74) / 2.0F;
         float floatValue77 = floatValue76 + 45.0F;
         float floatValue78 = floatValue74 - 55.0F;
         float floatValue79 = floatValue75 + 10.0F + 160.0F + 10.0F;
         float floatValue80 = 160.0F;
         float floatValue81 = floatValue75 + floatValue70 + floatValue72 + 10.0F;
         float floatValue82 = floatValue71 - 20.0F;
         if (mouseX >= floatValue79 && mouseX <= floatValue79 + floatValue80 && mouseY >= floatValue77 && mouseY <= floatValue77 + floatValue78) {
            if (this.items4.stream().noneMatch(autoBuyScreenState6 -> autoBuyScreenState6.text.equals(this.text))) {
               this.items4.add(new AutoBuyScreen.AutoBuyScreenState(this.text, ""));
               AutoBuy.VALUES_BY_KEY.put(this.text, 0L);
            }
         } else if (mouseX >= floatValue81
            && mouseX <= floatValue81 + floatValue82
            && mouseY >= floatValue77
            && mouseY <= floatValue77 + floatValue78
            && this.items5.stream().noneMatch(autoBuyScreenState7 -> autoBuyScreenState7.text.equals(this.text))) {
            this.items5.add(new AutoBuyScreen.AutoBuyScreenState(this.text, ""));
            if (!AutoBuy.ITEMS.contains(this.text)) {
               AutoBuy.ITEMS.add(this.text);
            }
         }

         this.text = null;
         return true;
      } else {
         return super.mouseReleased(mouseX, mouseY, button);
      }
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
      float floatValue83 = 350.0F;
      float floatValue84 = 180.0F;
      float floatValue85 = 15.0F;
      float floatValue86 = floatValue83 + floatValue85 + floatValue84;
      float floatValue87 = 260.0F;
      float floatValue88 = (this.width - floatValue86) / 2.0F;
      float floatValue89 = (this.height - floatValue87) / 2.0F;
      float floatValue90 = floatValue89 + 45.0F;
      float floatValue91 = floatValue87 - 55.0F;
      float floatValue92 = floatValue88 + 10.0F;
      float floatValue93 = 160.0F;
      float floatValue94 = floatValue92 + floatValue93 + 10.0F;
      float floatValue95 = 160.0F;
      float floatValue96 = floatValue88 + floatValue83 + floatValue85 + 10.0F;
      float floatValue97 = floatValue84 - 20.0F;
      if (mouseX >= floatValue92 && mouseX <= floatValue92 + floatValue93 && mouseY >= floatValue90 && mouseY <= floatValue90 + floatValue91) {
         this.floatValue += (float)(verticalAmount * 22.0);
         if (this.floatValue > 0.0F) {
            this.floatValue = 0.0F;
         }
      } else if (mouseX >= floatValue94 && mouseX <= floatValue94 + floatValue95 && mouseY >= floatValue90 && mouseY <= floatValue90 + floatValue91) {
         this.floatValue2 += (float)(verticalAmount * 22.0);
         if (this.floatValue2 > 0.0F) {
            this.floatValue2 = 0.0F;
         }
      } else if (mouseX >= floatValue96 && mouseX <= floatValue96 + floatValue97 && mouseY >= floatValue90 && mouseY <= floatValue90 + floatValue91) {
         this.floatValue3 += (float)(verticalAmount * 22.0);
         if (this.floatValue3 > 0.0F) {
            this.floatValue3 = 0.0F;
         }
      }

      return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
   }

   public boolean charTyped(char chr, int modifiers) {
      if (this.autoBuyScreenState != null && Character.isDigit(chr) && this.autoBuyScreenState.text2.length() < 12) {
         this.autoBuyScreenState.text2 = this.autoBuyScreenState.text2 + chr;
         this.invoke4(this.autoBuyScreenState);
         return true;
      } else {
         return super.charTyped(chr, modifiers);
      }
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (this.autoBuyScreenState != null) {
         if (keyCode == 259 && !this.autoBuyScreenState.text2.isEmpty()) {
            this.autoBuyScreenState.text2 = this.autoBuyScreenState.text2.substring(0, this.autoBuyScreenState.text2.length() - 1);
            this.invoke4(this.autoBuyScreenState);
            return true;
         }

         if (keyCode == 257 || keyCode == 256) {
            this.autoBuyScreenState = null;
            return true;
         }
      }

      return super.keyPressed(keyCode, scanCode, modifiers);
   }

   private List<String> resolve(AutoBuy autoBuy3) {
      if (autoBuy3.rezhimServera.getValue().equals("SpookyTime")) {
         return this.items2;
      } else {
         return autoBuy3.rezhimServera.getValue().equals("HolyWorld") ? this.items3 : this.items;
      }
   }

   private void invoke4(AutoBuyScreen.AutoBuyScreenState autoBuyScreenState8) {
      try {
         if (this.items4.contains(autoBuyScreenState8)) {
            long longValue = autoBuyScreenState8.text2.isEmpty() ? 0L : Long.parseLong(autoBuyScreenState8.text2);
            AutoBuy.VALUES_BY_KEY.put(autoBuyScreenState8.text, longValue);
         }

         if (WildClient.INSTANCE.configManager != null) {
            WildClient.INSTANCE.configManager.scheduleSave();
         }
      } catch (Exception exception) {
      }
   }

   public boolean shouldPause() {
      return false;
   }

   class AutoBuyScreenState {
      String text;
      String text2;
      Animation animation = new Animation();
      boolean flag = false;

      AutoBuyScreenState(String string, String string2) {
         this.text = string;
         this.text2 = string2;
      }
   }
}
