package ru.metaculture.protection;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.wild.module.api.Module;
import ru.metaculture.profile.Profile;

public final class AutoBuyModuleCardHandler implements SpecialModuleCardHandler {
   private static final SpringSpec SPRING_SPEC = SpringSpec.resolve2();
   private static final SpringSpec SPRING_SPEC_2 = SpringSpec.resolve9();
   private static final SpringSpec SPRING_SPEC_3 = SpringSpec.resolve16();
   private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
   private static final SimpleDateFormat SIMPLE_DATE_FORMAT_2 = new SimpleDateFormat("dd.MM.yyyy");
   private static final String[] FUNTIME = new String[]{"FunTime", "SpookyTime", "HolyWorld"};
   private static final Map<String, String> VALUES_BY_KEY = Map.ofEntries(
      Map.entry("protection", "Защита"),
      Map.entry("fire_protection", "Огнеупорность"),
      Map.entry("feather_falling", "Невесомость"),
      Map.entry("blast_protection", "Взрывоустойчивость"),
      Map.entry("projectile_protection", "Защита от снарядов"),
      Map.entry("respiration", "Подводное дыхание"),
      Map.entry("aqua_affinity", "Подводник"),
      Map.entry("thorns", "Шипы"),
      Map.entry("depth_strider", "Подводная ходьба"),
      Map.entry("frost_walker", "Ледоход"),
      Map.entry("binding_curse", "Проклятие несъемности"),
      Map.entry("soul_speed", "Скорость души"),
      Map.entry("swift_sneak", "Проворство"),
      Map.entry("unbreaking", "Прочность"),
      Map.entry("mending", "Починка"),
      Map.entry("vanishing_curse", "Проклятие утраты"),
      Map.entry("efficiency", "Эффективность"),
      Map.entry("fortune", "Удача"),
      Map.entry("sharpness", "Острота"),
      Map.entry("smite", "Небесная кара"),
      Map.entry("bane_of_arthropods", "Бич членистоногих"),
      Map.entry("fire_aspect", "Заговор огня"),
      Map.entry("sweeping_edge", "Разящий клинок"),
      Map.entry("looting", "Добыча"),
      Map.entry("piercing", "Пронзатель"),
      Map.entry("multishot", "Тройной выстрел"),
      Map.entry("quick_charge", "Быстрая перезарядка"),
      Map.entry("luck_of_the_sea", "Морская удача")
   );
   private static final List<String> ITEMS = List.of(
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
      "Удочка Крушителя",
      "Сфера Хаоса",
      "Сфера Титана",
      "Сфера Ареса",
      "Сфера Бестии",
      "Сфера Гидры",
      "Сфера Икара",
      "Сфера Эрида",
      "Сфера Сатира",
      "Талисман Демона",
      "Талисман Карателя",
      "Талисман Мрака",
      "Талисман Ярости",
      "Талисман Тирана",
      "Талисман Крушителя",
      "Талисман Раздора",
      "Зелье Ассасина",
      "Зелье Гнева",
      "Хлопушка",
      "Святая Вода",
      "Зелье Палладина",
      "Зелье Радиации",
      "Снотворное",
      "Пласт",
      "Опыт 15",
      "Опыт 30",
      "Опыт 45",
      "Вайт",
      "Блек",
      "Блок дамагер",
      "Прогрузчик чанков",
      "Маяк",
      "Проклятая Душа",
      "Драконий Скин",
      "Огненный Смерч",
      "Снежок Заморозка",
      "Божья Аура",
      "Серебро",
      "Божье Касание",
      "Мощный Удар",
      "Мега Бульдозер",
      "Нерушимые Элитры"
   );
   private static List<AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerDisplayEntry> items;
   private final List<AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerTimedEntry> items2 = new ArrayList<>();
   private String text = "";
   private boolean flag = false;
   private final Map<String, TextSetting> valuesByKey = new HashMap<>();
   private String text2 = null;
   private final TypeUtils typeUtils = new TypeUtils();
   private final TypeUtils typeUtils2 = new TypeUtils();
   private final TypeUtils typeUtils3 = new TypeUtils();
   private final TypeUtils typeUtils4 = new TypeUtils();
   private float floatValue;
   private float floatValue2;
   private float floatValue3;
   private float floatValue4;
   private float floatValue5;
   private float floatValue6;
   private float floatValue7;
   private float floatValue8;
   private final TimedAnimation timedAnimation = new TimedAnimation(AnimationMode.EASE_IN_OUT_QUAD, 460L);
   private TimedAnimation timedAnimation2 = new TimedAnimation(AnimationMode.EASE_OUT_CUBIC, 600L);
   private int intValue = -1;
   private final TextSetting catalogSearch = new TextSetting("Catalog Search", "");
   private final Map<String, TextSetting> valuesByKey2 = new LinkedHashMap<>();
   private String text3 = null;
   private String text4 = null;
   private boolean flag2 = false;
   private float floatValue9 = 0.0F;
   private float floatValue10 = 1.0F;
   private AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerScrollLayout autoBuyModuleCardHandlerScrollLayout = AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerScrollLayout.hidden();
   private AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerScrollLayout autoBuyModuleCardHandlerScrollLayout2 = AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerScrollLayout.hidden();
   private AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerScrollLayout autoBuyModuleCardHandlerScrollLayout3 = AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerScrollLayout.hidden();
   private AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerScrollLayout autoBuyModuleCardHandlerScrollLayout4 = AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerScrollLayout.hidden();
   private boolean flag3;
   private boolean flag4;
   private boolean flag5;
   private boolean flag6;
   private float floatValue11;
   private int intValue2 = 0;

   @Override
   public boolean check(Module module) {
      return module instanceof AutoBuy;
   }

   @Override
   public boolean check2(Module module, ClickGuiState clickGuiState) {
      return clickGuiState.getValues().contains(module) || clickGuiState.measure7(AnimationKeyRegistry.resolve15(module)) > 0.01F;
   }

   @Override
   public void invoke(ClickGuiState clickGuiState2) {
      this.invoke55();
      this.flag = false;
   }

   @Override
   public void invoke2(ClickGuiState clickGuiState3) {
      this.invoke55();
   }

   @Override
   public float measure(Module module, Metrics metrics, ClickGuiState clickGuiState4) {
      return metrics.measure(386.0F);
   }

   @Override
   public void invoke3(Module module, ClickGuiState clickGuiState5, SpringSpec springSpec, SpringSpec springSpec2) {
      if (module instanceof AutoBuy autoBuy) {
         boolean flag = !clickGuiState5.isFlag7();
         boolean flag2 = flag && (clickGuiState5.getValues().contains(module) || clickGuiState5.isFlag());
         long longValue = clickGuiState5.isFlag() ? clickGuiState5.getTimestamp5() : clickGuiState5.compute2(module);
         long longValue2 = System.currentTimeMillis();
         clickGuiState5.measure9(AnimationKeyRegistry.resolve45(), flag2 ? 1.0F : 0.0F, flag2 ? springSpec : SPRING_SPEC_3);
         List items = this.resolve8(autoBuy, this.catalogSearch.value);
         int intValue = Math.min(items.size(), 80);

         for (int intValue2 = 0; intValue2 < intValue; intValue2++) {
            AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerDisplayEntry autoBuyModuleCardHandlerDisplayEntry;
            float floatValue;
            label115: {
               label114: {
                  autoBuyModuleCardHandlerDisplayEntry = (AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerDisplayEntry)items.get(intValue2);
                  if (flag2 && this.intValue2 == 0) {
                     if (longValue <= 0L) {
                        break label114;
                     }

                     if (longValue2 - longValue >= 12L * intValue2) {
                        break label114;
                     }
                  }

                  floatValue = 0.0F;
                  break label115;
               }

               floatValue = 1.0F;
            }

            float floatValue2 = floatValue;
            clickGuiState5.measure9(AnimationKeyRegistry.resolve44(autoBuyModuleCardHandlerDisplayEntry.key()), floatValue2, floatValue2 > 0.0F ? springSpec : SPRING_SPEC_3);
         }

         List items2 = this.resolve17();

         for (int intValue3 = 0; intValue3 < items2.size(); intValue3++) {
            String text = (String)items2.get(intValue3);
            float floatValue3 = !flag2 || this.intValue2 != 0 || longValue > 0L && longValue2 - longValue < 24L * intValue3 + 70L ? 0.0F : 1.0F;
            clickGuiState5.measure9(AnimationKeyRegistry.resolve46(text), floatValue3, floatValue3 > 0.0F ? springSpec : SPRING_SPEC_3);
         }

         for (int intValue4 = 0; intValue4 < this.items2.size(); intValue4++) {
            AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerTimedEntry autoBuyModuleCardHandlerTimedEntry = this.items2.get(intValue4);
            float floatValue4 = !flag2 || this.intValue2 != 2 || longValue > 0L && longValue2 - longValue < 24L * intValue4 + 70L ? 0.0F : 1.0F;
            clickGuiState5.measure9("cfg_entry:" + autoBuyModuleCardHandlerTimedEntry.name(), floatValue4, floatValue4 > 0.0F ? springSpec : SPRING_SPEC_3);
         }
      }
   }

   @Override
   public void invoke4(
      RenderManager renderManager, DrawContext drawContext, ClickGuiState clickGuiState6, ModulePlacement modulePlacement, ThemeContext themeContext
   ) {
      if (modulePlacement.getModule() instanceof AutoBuy autoBuy2) {
         if (this.intValue2 == 2 && !this.flag) {
            this.invoke6(autoBuy2);
            this.flag = true;
         }

         Metrics metrics2 = themeContext.getMetrics();
         AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerBounds autoBuyModuleCardHandlerBounds = this.resolve6(modulePlacement, metrics2);
         if (!(autoBuyModuleCardHandlerBounds.width() <= 1.0F) && !(autoBuyModuleCardHandlerBounds.height() <= 1.0F)) {
            float floatValue5 = metrics2.measure(4.0F);
            renderManager.invoke20();
            renderManager.invoke24(
               autoBuyModuleCardHandlerBounds.x() - floatValue5,
               autoBuyModuleCardHandlerBounds.y() - floatValue5,
               autoBuyModuleCardHandlerBounds.width() + floatValue5 * 2.0F,
               autoBuyModuleCardHandlerBounds.height() + floatValue5 * 2.0F,
               metrics2.measure(10.0F),
               metrics2.measure(10.0F),
               metrics2.measure(10.0F),
               metrics2.measure(10.0F)
            );
            boolean flag3 = false ;

            try {
               flag3 = true;
               this.invoke7(renderManager, clickGuiState6, autoBuy2, autoBuyModuleCardHandlerBounds, themeContext);
               if (this.intValue2 == 1) {
                  this.invoke13(renderManager, drawContext, clickGuiState6, autoBuy2, autoBuyModuleCardHandlerBounds, themeContext);
                  flag3 = false;
               } else if (this.intValue2 == 2) {
                  this.invoke12(renderManager, clickGuiState6, autoBuy2, autoBuyModuleCardHandlerBounds, themeContext);
                  flag3 = false;
               } else {
                  this.invoke14(renderManager, drawContext, clickGuiState6, autoBuy2, autoBuyModuleCardHandlerBounds, themeContext);
                  this.invoke18(renderManager, drawContext, clickGuiState6, autoBuyModuleCardHandlerBounds, themeContext);
                  flag3 = false;
               }
            } finally {
               if (flag3) {
                  renderManager.invoke20();
                  renderManager.invoke25();
               }
            }

            renderManager.invoke20();
            renderManager.invoke25();
            this.invoke50(clickGuiState6);
         }
      }
   }

   @Override
   public void invoke5(List<ClickGuiHitTarget> list, ClickGuiState clickGuiState7, ModulePlacement modulePlacement2, Metrics metrics3) {
      if (modulePlacement2.getModule() instanceof AutoBuy autoBuy3) {
         if (this.intValue2 == 2 && !this.flag) {
            this.invoke6(autoBuy3);
            this.flag = true;
         }

         AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerBounds autoBuyModuleCardHandlerBounds2 = this.resolve6(modulePlacement2, metrics3);
         if (!(autoBuyModuleCardHandlerBounds2.height() <= metrics3.measure(40.0F))) {
            this.invoke36(list, autoBuy3, autoBuyModuleCardHandlerBounds2, metrics3);
            if (this.intValue2 == 1) {
               this.invoke45(list, autoBuyModuleCardHandlerBounds2, metrics3);
            } else if (this.intValue2 == 2) {
               this.invoke46(list, autoBuy3, autoBuyModuleCardHandlerBounds2, metrics3);
            } else {
               this.invoke37(list, autoBuy3, autoBuyModuleCardHandlerBounds2, metrics3);
               this.invoke39(list, clickGuiState7, autoBuyModuleCardHandlerBounds2, metrics3);
            }

            list.add(
               ClickGuiHitTarget.resolve()
                  .setIntValue(0)
                  .setFloatValue(autoBuyModuleCardHandlerBounds2.x())
                  .setFloatValue2(autoBuyModuleCardHandlerBounds2.y())
                  .setFloatValue3(autoBuyModuleCardHandlerBounds2.width())
                  .setFloatValue4(autoBuyModuleCardHandlerBounds2.height())
                  .setClickGuiAction(clickGuiState8 -> {
                     clickGuiState8.setFlag5(false);
                     if (!this.check9(clickGuiState8.getTextSetting()) && this.resolve(clickGuiState8) == null) {
                        clickGuiState8.setNumberSetting((NumberSetting)null);
                     }
                  })
                  .resolve()
            );
         }
      }
   }

   @Override
   public boolean check3(ClickGuiState clickGuiState9, ModuleLayoutResult moduleLayoutResult, Metrics metrics4, float f, float g, double d) {
      for (ModulePlacement modulePlacement3 : moduleLayoutResult.getItems()) {
         if (modulePlacement3.getModule() instanceof AutoBuy autoBuy4 && (clickGuiState9.getValues().contains(modulePlacement3.getModule()) || clickGuiState9.isFlag())) {
            AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerBounds autoBuyModuleCardHandlerBounds3 = this.resolve6(modulePlacement3, metrics4);
            if (this.intValue2 == 1) {
               if (ClickGuiRenderUtils.check2(f, g, autoBuyModuleCardHandlerBounds3.x(), autoBuyModuleCardHandlerBounds3.panelY(), autoBuyModuleCardHandlerBounds3.width(), autoBuyModuleCardHandlerBounds3.panelH())) {
                  this.invoke56(this.typeUtils3, this.measure6(autoBuyModuleCardHandlerBounds3, metrics4), d);
                  return true;
               }

               return false;
            }

            if (this.intValue2 == 2) {
               if (ClickGuiRenderUtils.check2(f, g, autoBuyModuleCardHandlerBounds3.x(), autoBuyModuleCardHandlerBounds3.panelY(), autoBuyModuleCardHandlerBounds3.width(), autoBuyModuleCardHandlerBounds3.panelH())) {
                  this.invoke56(this.typeUtils4, this.measure7(autoBuyModuleCardHandlerBounds3, metrics4), d);
                  return true;
               }

               return false;
            }

            if (ClickGuiRenderUtils.check2(f, g, autoBuyModuleCardHandlerBounds3.leftX(), autoBuyModuleCardHandlerBounds3.panelY(), autoBuyModuleCardHandlerBounds3.leftW(), autoBuyModuleCardHandlerBounds3.panelH())) {
               this.invoke56(this.typeUtils, this.measure3(autoBuy4, autoBuyModuleCardHandlerBounds3, metrics4), d);
               return true;
            }

            if (ClickGuiRenderUtils.check2(f, g, autoBuyModuleCardHandlerBounds3.rightX(), autoBuyModuleCardHandlerBounds3.panelY(), autoBuyModuleCardHandlerBounds3.rightW(), autoBuyModuleCardHandlerBounds3.panelH())) {
               this.invoke56(this.typeUtils2, this.measure4(autoBuyModuleCardHandlerBounds3, metrics4), d);
               return true;
            }
         }
      }

      return false;
   }

   @Override
   public boolean check4(ClickGuiState clickGuiState10, float f, float g) {
      if (this.text4 != null) {
         this.invoke44(this.text4, f, clickGuiState10);
         return true;
      } else if (this.intValue2 == 1) {
         if (this.flag5 && this.autoBuyModuleCardHandlerScrollLayout3.visible()) {
            this.invoke49("history", g, this.autoBuyModuleCardHandlerScrollLayout3);
            return true;
         } else {
            return false;
         }
      } else if (this.intValue2 == 2) {
         if (this.flag6 && this.autoBuyModuleCardHandlerScrollLayout4.visible()) {
            this.invoke49("cloud", g, this.autoBuyModuleCardHandlerScrollLayout4);
            return true;
         } else {
            return false;
         }
      } else if (this.flag3 && this.autoBuyModuleCardHandlerScrollLayout.visible()) {
         this.invoke49("catalog", g, this.autoBuyModuleCardHandlerScrollLayout);
         return true;
      } else if (this.flag4 && this.autoBuyModuleCardHandlerScrollLayout2.visible()) {
         this.invoke49("rules", g, this.autoBuyModuleCardHandlerScrollLayout2);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean check5(ClickGuiState clickGuiState11) {
      boolean flag4 = this.flag3 || this.flag4 || this.flag5 || this.flag6 || this.text4 != null;
      this.flag3 = false;
      this.flag4 = false;
      this.flag5 = false;
      this.flag6 = false;
      this.text4 = null;
      return flag4;
   }

   @Override
   public boolean check6(ClickGuiState clickGuiState12, int i) {
      if (clickGuiState12.getTextSetting() == this.catalogSearch) {
         if (i == 256 || i == 257) {
            clickGuiState12.setNumberSetting((NumberSetting)null);
            return true;
         } else if (i == 259 && !this.catalogSearch.value.isEmpty()) {
            this.catalogSearch.value = this.catalogSearch.value.substring(0, this.catalogSearch.value.length() - 1);
            this.invoke54();
            return true;
         } else if (i == 261 && !this.catalogSearch.value.isEmpty()) {
            this.catalogSearch.value = "";
            this.invoke54();
            return true;
         } else {
            return true;
         }
      } else {
         String text2 = this.resolve(clickGuiState12);
         if (text2 != null) {
            TextSetting textSetting = this.valuesByKey.get(text2);
            if (i == 256 || i == 257) {
               clickGuiState12.setNumberSetting((NumberSetting)null);
               return true;
            } else if (i == 259 && !textSetting.value.isEmpty()) {
               textSetting.value = textSetting.value.substring(0, textSetting.value.length() - 1);
               return true;
            } else {
               return true;
            }
         } else {
            String text3 = this.resolve20(clickGuiState12);
            if (text3 == null) {
               return false;
            } else {
               TextSetting textSetting2 = this.valuesByKey2.get(text3);
               if (i == 256 || i == 257) {
                  clickGuiState12.setNumberSetting((NumberSetting)null);
                  clickGuiState12.invoke66();
                  return true;
               } else if (i == 259 && !textSetting2.value.isEmpty()) {
                  textSetting2.value = textSetting2.value.substring(0, textSetting2.value.length() - 1);
                  this.invoke51(text3, textSetting2.value, clickGuiState12);
                  return true;
               } else if (i == 261) {
                  textSetting2.value = "";
                  this.invoke51(text3, textSetting2.value, clickGuiState12);
                  return true;
               } else {
                  return true;
               }
            }
         }
      }
   }

   @Override
   public boolean check7(ClickGuiState clickGuiState13, char c) {
      if (clickGuiState13.getTextSetting() == this.catalogSearch) {
         if (!Character.isISOControl(c) && this.catalogSearch.value.length() < 64) {
            this.catalogSearch.value = this.catalogSearch.value + c;
            this.invoke54();
         }

         return true;
      } else {
         String text4 = this.resolve(clickGuiState13);
         if (text4 != null) {
            TextSetting textSetting3 = this.valuesByKey.get(text4);
            if (!Character.isISOControl(c) && textSetting3.value.length() < 25 && String.valueOf(c).matches("[a-zA-Z0-9_\\- ]")) {
               textSetting3.value = textSetting3.value + c;
            }

            return true;
         } else {
            String text5 = this.resolve20(clickGuiState13);
            if (text5 == null) {
               return false;
            } else {
               if (Character.isDigit(c)) {
                  TextSetting textSetting4 = this.valuesByKey2.get(text5);
                  if (textSetting4.value.length() < 12) {
                     textSetting4.value = textSetting4.value + c;
                     this.invoke51(text5, textSetting4.value, clickGuiState13);
                  }
               }

               return true;
            }
         }
      }
   }

   private void invoke6(AutoBuy autoBuy5) {
      this.items2.clear();
      File file2 = autoBuy5.resolve5();
      if (file2.exists()) {
         File[] files = file2.listFiles((file, string) -> string.endsWith(".json"));
         if (files != null) {
            String text6 = "Игрок";

            try {
               String text7 = Profile.getUsername();
               if (text7 != null) {
                  text6 = text7;
               }
            } catch (Throwable exception) {
               if (MinecraftClient.getInstance().getSession() != null) {
                  text6 = MinecraftClient.getInstance().getSession().getUsername();
               }
            }

            for (File file3 : files) {
               String text8 = file3.getName().replace(".json", "");
               this.items2.add(new AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerTimedEntry(text8, text6, file3.lastModified()));
            }

            this.items2.sort((autoBuyModuleCardHandlerTimedEntry2, autoBuyModuleCardHandlerTimedEntry3) -> Long.compare(autoBuyModuleCardHandlerTimedEntry3.timestamp, autoBuyModuleCardHandlerTimedEntry2.timestamp));
         }
      }
   }

   private String resolve(ClickGuiState clickGuiState14) {
      TextSetting textSetting5 = clickGuiState14.getTextSetting();
      if (textSetting5 == null) {
         return null;
      } else {
         for (Entry entry2 : this.valuesByKey.entrySet()) {
            if (entry2.getValue() == textSetting5) {
               return (String)entry2.getKey();
            }
         }

         return null;
      }
   }

   private AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerDisplayEntry resolve2(AutoBuy autoBuy6, String string) {
      if (string == null) {
         return this.resolve10("");
      } else {
         String text9 = string.replace(' ', ' ').trim();
         text9 = text9.replaceAll("^\\[.*?\\]\\s*", "").trim();
         if (text9.matches("(?i).*\\s+[xхXХ]?\\d+[xхXХ]?$")) {
            int intValue5 = text9.lastIndexOf(32);
            if (intValue5 != -1) {
               text9 = text9.substring(0, intValue5).trim();
            }
         }

         if (text9.matches("(?i)^[xхXХ]?\\d+[xхXХ]?\\s+.*")) {
            int intValue6 = text9.indexOf(32);
            if (intValue6 != -1) {
               text9 = text9.substring(intValue6 + 1).trim();
            }
         }

         String text10 = text9.toLowerCase(Locale.ROOT);

         for (String text11 : ITEMS) {
            if (text10.contains(text11.toLowerCase(Locale.ROOT))) {
               return new AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerDisplayEntry(text11, text11, ItemStack.EMPTY, true);
            }
         }

         for (AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerDisplayEntry autoBuyModuleCardHandlerDisplayEntry2 : this.resolve7(autoBuy6)) {
            if (text10.contains(autoBuyModuleCardHandlerDisplayEntry2.label().toLowerCase(Locale.ROOT)) || text10.contains(autoBuyModuleCardHandlerDisplayEntry2.key().toLowerCase(Locale.ROOT))) {
               return autoBuyModuleCardHandlerDisplayEntry2;
            }
         }

         return this.resolve10(text9);
      }
   }

   private void invoke7(
      RenderManager renderManager2, ClickGuiState clickGuiState15, AutoBuy autoBuy7, AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerBounds autoBuyModuleCardHandlerBounds4, ThemeContext themeContext2
   ) {
      Metrics metrics5 = themeContext2.getMetrics();
      ColorScheme colorScheme = themeContext2.getColorScheme();
      AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerData3 autoBuyModuleCardHandlerData3 = this.resolve3(autoBuyModuleCardHandlerBounds4, metrics5);
      float floatValue6 = autoBuyModuleCardHandlerData3.stripH();
      float floatValue7 = autoBuyModuleCardHandlerData3.modeX();
      float floatValue8 = autoBuyModuleCardHandlerData3.modeY();
      float floatValue9 = autoBuyModuleCardHandlerData3.toggleW();
      float floatValue10 = autoBuyModuleCardHandlerData3.toggleX();
      float floatValue11 = autoBuyModuleCardHandlerData3.gap();
      float floatValue12 = autoBuyModuleCardHandlerData3.tabBtnSize();
      float floatValue13 = autoBuyModuleCardHandlerData3.chipW();
      float floatValue14 = floatValue7;

      for (String text12 : FUNTIME) {
         boolean flag5 = autoBuy7.rezhimServera.is(text12);
         String text13 = ClickGuiRenderUtils.resolve4(metrics5, FontRegistry.fontObject4, text12, 10.0F, floatValue13 - metrics5.measure(10.0F));
         float floatValue15 = ClickGuiRenderUtils.measure(FontRegistry.fontObject4, text13, 10.0F);
         String text14 = AnimationKeyRegistry.resolve50("mode:" + text12);
         float floatValue16 = clickGuiState15.measure5(
            text14, ClickGuiRenderUtils.check(clickGuiState15, floatValue14, floatValue8, floatValue13, floatValue6) ? 1.0F : 0.0F, SpringSpec.resolve11()
         );
         renderManager2.invoke62(ClickGuiRenderUtils.measure8(floatValue16, clickGuiState15.measure8(text14), 0.016F, 0.006F), floatValue14 + floatValue13 * 0.5F, floatValue8 + floatValue6 * 0.5F);

         try {
            renderManager2.invoke5(
               floatValue14, floatValue8, floatValue13, floatValue6, metrics5.measure(8.0F), ColorScheme.compute7(colorScheme.getIntValue3(), colorScheme.getIntValue5(), floatValue16 * 0.7F)
            );
            renderManager2.invoke28(
               floatValue14,
               floatValue8,
               floatValue13,
               floatValue6,
               metrics5.measure(8.0F),
               ColorScheme.compute7(colorScheme.getIntValue5(), ColorScheme.compute6(colorScheme.getIntValue14(), 90), flag5 ? 0.72F : floatValue16),
               0.5F
            );
            if (flag5) {
               renderManager2.invoke34(
                  floatValue14 + metrics5.measure(4.0F),
                  floatValue8 + metrics5.measure(4.0F),
                  floatValue13 - metrics5.measure(8.0F),
                  floatValue6 - metrics5.measure(8.0F),
                  metrics5.measure(4.0F),
                  ColorScheme.compute6(colorScheme.getIntValue15(), 48),
                  ColorScheme.compute6(colorScheme.getIntValue14(), 44)
               );
            }

            ClickGuiRenderUtils.invoke4(
               renderManager2,
               metrics5,
               FontRegistry.fontObject4,
               floatValue14 + (floatValue13 - floatValue15) * 0.5F,
               floatValue8,
               floatValue6,
               10.0F,
               text13,
               flag5 ? colorScheme.getIntValue14() : ColorScheme.compute7(colorScheme.getIntValue11(), colorScheme.getIntValue12(), floatValue16)
            );
         } finally {
            renderManager2.invoke64();
         }

         floatValue14 += floatValue13 + floatValue11;
      }

      this.invoke10(renderManager2, clickGuiState15, "catalog_tab", this.intValue2 == 0, floatValue14, floatValue8, floatValue12, "W", themeContext2);
      floatValue14 += floatValue12 + floatValue11;
      this.invoke10(renderManager2, clickGuiState15, "history_tab", this.intValue2 == 1, floatValue14, floatValue8, floatValue12, "E", themeContext2);
      floatValue14 += floatValue12 + floatValue11;
      this.invoke10(renderManager2, clickGuiState15, "cloud_tab", this.intValue2 == 2, floatValue14, floatValue8, floatValue12, "Y", themeContext2);
      if (autoBuyModuleCardHandlerData3.showReparse()) {
         this.invoke8(renderManager2, clickGuiState15, autoBuy7, autoBuyModuleCardHandlerData3, themeContext2);
      }

      float floatValue17 = floatValue9 * 0.5F;
      float floatValue18 = clickGuiState15.measure5(
         AnimationKeyRegistry.resolve50("toggle:inactive"),
         ClickGuiRenderUtils.check(clickGuiState15, floatValue10, floatValue8, floatValue17, floatValue6) ? 1.0F : 0.0F,
         SpringSpec.resolve11()
      );
      float floatValue19 = clickGuiState15.measure5(
         AnimationKeyRegistry.resolve50("toggle:active"),
         ClickGuiRenderUtils.check(clickGuiState15, floatValue10 + floatValue17, floatValue8, floatValue17, floatValue6) ? 1.0F : 0.0F,
         SpringSpec.resolve11()
      );
      float floatValue20 = clickGuiState15.measure7(AnimationKeyRegistry.resolve17(autoBuy7));
      float floatValue21 = metrics5.measure(2.0F);
      float floatValue22 = floatValue17 - floatValue21 * 2.0F;
      float floatValue23 = floatValue6 - floatValue21 * 2.0F;
      float floatValue24 = floatValue10 + floatValue21 + floatValue17 * floatValue20;
      float floatValue25 = floatValue8 + floatValue21;
      int intValue7 = ColorScheme.compute7(ColorScheme.compute6(colorScheme.compute2(), 30), ColorScheme.compute6(colorScheme.compute(), 24), floatValue20);
      int intValue8 = ColorScheme.compute7(ColorScheme.compute6(colorScheme.compute2(), 16), ColorScheme.compute6(colorScheme.getIntValue14(), 24), floatValue20);
      renderManager2.invoke5(
         floatValue10, floatValue8, floatValue9, floatValue6, metrics5.measure(8.0F), ColorScheme.compute7(colorScheme.getIntValue3(), colorScheme.getIntValue4(), Math.max(floatValue18, floatValue19) * 0.45F)
      );
      renderManager2.invoke5(
         floatValue10 + metrics5.measure(1.0F),
         floatValue8 + metrics5.measure(1.0F),
         floatValue9 - metrics5.measure(2.0F),
         floatValue6 - metrics5.measure(2.0F),
         metrics5.measure(7.0F),
         ColorScheme.compute5(0, 0, 0, 18)
      );
      renderManager2.invoke34(floatValue24, floatValue25, floatValue22, floatValue23, metrics5.measure(6.0F), intValue7, intValue8);
      renderManager2.invoke28(
         floatValue10, floatValue8, floatValue9, floatValue6, metrics5.measure(8.0F), ColorScheme.compute7(colorScheme.getIntValue5(), colorScheme.getIntValue7(), Math.max(floatValue19, floatValue18)), 0.5F
      );
      renderManager2.invoke28(
         floatValue24,
         floatValue25,
         floatValue22,
         floatValue23,
         metrics5.measure(6.0F),
         ColorScheme.compute7(ColorScheme.compute6(colorScheme.compute2(), 72), ColorScheme.compute6(colorScheme.compute(), 68), floatValue20),
         0.5F
      );
      this.invoke32(
         renderManager2,
         metrics5,
         FontRegistry.fontObject,
         floatValue10,
         floatValue8,
         floatValue17,
         floatValue6,
         11.0F,
         "Пауза",
         ColorScheme.compute7(ColorScheme.compute6(colorScheme.compute2(), 145), colorScheme.getIntValue11(), floatValue20 * 0.82F)
      );
      this.invoke32(
         renderManager2,
         metrics5,
         FontRegistry.fontObject,
         floatValue10 + floatValue17,
         floatValue8,
         floatValue17,
         floatValue6,
         11.0F,
         "Активен",
         ColorScheme.compute7(colorScheme.getIntValue11(), ColorScheme.compute6(colorScheme.compute(), 145), floatValue20)
      );
   }

   private void invoke8(
      RenderManager renderManager3, ClickGuiState clickGuiState16, AutoBuy autoBuy8, AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerData3 autoBuyModuleCardHandlerData32, ThemeContext themeContext3
   ) {
      Metrics metrics6 = themeContext3.getMetrics();
      ColorScheme colorScheme2 = themeContext3.getColorScheme();
      boolean flag6 = autoBuy8.autoReparse.isEnabled();
      float floatValue26 = autoBuyModuleCardHandlerData32.reparseX();
      float floatValue27 = autoBuyModuleCardHandlerData32.modeY();
      float floatValue28 = autoBuyModuleCardHandlerData32.stripH();
      float floatValue29 = autoBuyModuleCardHandlerData32.reparseToggleW();
      float floatValue30 = autoBuyModuleCardHandlerData32.reparseSliderX();
      float floatValue31 = autoBuyModuleCardHandlerData32.reparseSliderW();
      float floatValue32 = clickGuiState16.measure5(
         AnimationKeyRegistry.resolve50("reparse:toggle"),
         ClickGuiRenderUtils.check(clickGuiState16, floatValue26, floatValue27, floatValue29, floatValue28) ? 1.0F : 0.0F,
         SpringSpec.resolve11()
      );
      float floatValue33 = clickGuiState16.measure5(AnimationKeyRegistry.resolve50("reparse:active"), flag6 ? 1.0F : 0.0F, SPRING_SPEC);
      int intValue9 = ColorScheme.compute7(colorScheme2.getIntValue4(), ColorScheme.compute5(24, 140, 72, 72), floatValue33);
      int intValue10 = ColorScheme.compute7(colorScheme2.getIntValue5(), ColorScheme.compute6(colorScheme2.compute(), 95), floatValue33);
      renderManager3.invoke5(floatValue26, floatValue27, floatValue29, floatValue28, metrics6.measure(8.0F), ColorScheme.compute7(intValue9, colorScheme2.getIntValue7(), floatValue32 * 0.45F));
      renderManager3.invoke28(floatValue26, floatValue27, floatValue29, floatValue28, metrics6.measure(8.0F), ColorScheme.compute7(intValue10, colorScheme2.getIntValue7(), floatValue32), 0.5F);
      this.invoke32(
         renderManager3,
         metrics6,
         FontRegistry.fontObject4,
         floatValue26,
         floatValue27,
         floatValue29,
         floatValue28,
         10.0F,
         "ReParse",
         flag6 ? ColorScheme.compute6(colorScheme2.compute(), 180) : colorScheme2.getIntValue12()
      );
      float floatValue34 = clickGuiState16.measure5(
         AnimationKeyRegistry.resolve50("reparse:slider"),
         ClickGuiRenderUtils.check(clickGuiState16, floatValue30, floatValue27, floatValue31, floatValue28) ? 1.0F : 0.0F,
         SpringSpec.resolve11()
      );
      float floatValue35 = autoBuy8.reparseKazhdyeMin.getValue();
      float floatValue36 = this.measure2(floatValue35, autoBuy8.reparseKazhdyeMin.minimum, autoBuy8.reparseKazhdyeMin.maximum);
      float floatValue37 = floatValue30 + metrics6.measure(8.0F);
      float floatValue38 = floatValue27 + metrics6.measure(21.0F);
      float floatValue39 = Math.max(metrics6.measure(28.0F), floatValue31 - metrics6.measure(16.0F));
      float floatValue40 = metrics6.measure(4.0F);
      renderManager3.invoke5(
         floatValue30, floatValue27, floatValue31, floatValue28, metrics6.measure(8.0F), ColorScheme.compute7(colorScheme2.getIntValue3(), colorScheme2.getIntValue5(), floatValue34 * 0.7F)
      );
      renderManager3.invoke28(floatValue30, floatValue27, floatValue31, floatValue28, metrics6.measure(8.0F), ColorScheme.compute7(colorScheme2.getIntValue5(), colorScheme2.getIntValue7(), floatValue34), 0.5F);
      renderManager3.invoke5(floatValue37, floatValue38, floatValue39, floatValue40, metrics6.measure(3.0F), colorScheme2.getIntValue7());
      renderManager3.invoke34(
         floatValue37,
         floatValue38,
         floatValue39 * floatValue36,
         floatValue40,
         metrics6.measure(3.0F),
         ColorScheme.compute6(colorScheme2.getIntValue15(), 110),
         ColorScheme.compute6(colorScheme2.getIntValue14(), 130)
      );
      float floatValue41 = floatValue37 + floatValue39 * floatValue36;
      renderManager3.invoke5(
         floatValue41 - metrics6.measure(2.5F),
         floatValue38 - metrics6.measure(2.0F),
         metrics6.measure(5.0F),
         metrics6.measure(8.0F),
         metrics6.measure(3.0F),
         flag6 ? colorScheme2.getIntValue14() : colorScheme2.getIntValue12()
      );
      String text15 = Math.round(floatValue35) + " мин";
      float floatValue42 = ClickGuiRenderUtils.measure(FontRegistry.fontObject, text15, 9.0F);
      ClickGuiRenderUtils.invoke4(
         renderManager3,
         metrics6,
         FontRegistry.fontObject,
         floatValue30 + (floatValue31 - floatValue42) * 0.5F,
         floatValue27 + metrics6.measure(5.0F),
         metrics6.measure(10.0F),
         9.0F,
         text15,
         flag6 ? colorScheme2.getIntValue14() : colorScheme2.getIntValue11()
      );
   }

   private AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerData3 resolve3(AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerBounds autoBuyModuleCardHandlerBounds5, Metrics metrics7) {
      float floatValue43 = metrics7.measure(34.0F);
      float floatValue44 = autoBuyModuleCardHandlerBounds5.x();
      float floatValue45 = autoBuyModuleCardHandlerBounds5.y();
      float floatValue46 = metrics7.measure(150.0F);
      float floatValue47 = autoBuyModuleCardHandlerBounds5.x() + autoBuyModuleCardHandlerBounds5.width() - floatValue46;
      float floatValue48 = metrics7.measure(8.0F);
      float floatValue49 = floatValue43 * 3.0F + floatValue48 * 2.0F;
      float floatValue50 = Math.max(0.0F, floatValue47 - floatValue44 - floatValue48);
      float floatValue51 = metrics7.measure(72.0F) * FUNTIME.length + floatValue48 * (FUNTIME.length - 1.0F);
      float floatValue52 = metrics7.measure(158.0F);
      float floatValue53 = metrics7.measure(224.0F);
      boolean flag7 = floatValue50 >= floatValue51 + floatValue49 + floatValue48 * 2.0F + floatValue52;
      float floatValue54 = flag7 ? Math.min(floatValue53, Math.max(floatValue52, floatValue50 - floatValue51 - floatValue49 - floatValue48 * 2.0F)) : 0.0F;
      float floatValue55 = Math.max(floatValue51, floatValue50 - floatValue49 - (flag7 ? floatValue54 + floatValue48 * 2.0F : floatValue48));
      float floatValue56 = 0.0F;

      for (String text16 : FUNTIME) {
         floatValue56 = Math.max(floatValue56, ClickGuiRenderUtils.measure(FontRegistry.fontObject4, text16, 10.0F));
      }

      float floatValue57 = this.measure22(
         floatValue56 + metrics7.measure(24.0F), metrics7.measure(72.0F), (floatValue55 - floatValue48 * (FUNTIME.length - 1.0F)) / FUNTIME.length
      );
      float floatValue58 = floatValue47 - floatValue48;
      float floatValue59 = floatValue44 + (floatValue57 + floatValue48) * FUNTIME.length + floatValue49;
      if (floatValue59 > floatValue58) {
         floatValue57 = Math.max(metrics7.measure(24.0F), (floatValue58 - floatValue44 - floatValue49 - floatValue48 * FUNTIME.length) / FUNTIME.length);
      }

      float floatValue60 = floatValue44 + floatValue57 * FUNTIME.length + floatValue48 * FUNTIME.length;
      float floatValue61 = floatValue60 + floatValue49 + floatValue48;
      if (flag7) {
         floatValue61 = Math.max(floatValue61, floatValue47 - floatValue48 - floatValue54);
         if (floatValue61 + floatValue54 > floatValue47 - floatValue48) {
            floatValue54 = Math.max(0.0F, floatValue47 - floatValue48 - floatValue61);
            if (floatValue54 < floatValue52 * 0.6F) {
               flag7 = false;
               floatValue54 = 0.0F;
            }
         }
      }

      float floatValue62 = flag7 ? Math.min(metrics7.measure(92.0F), Math.max(metrics7.measure(74.0F), floatValue54 * 0.42F)) : 0.0F;
      float floatValue63 = floatValue61 + floatValue62 + floatValue48;
      float floatValue64 = flag7 ? Math.max(metrics7.measure(64.0F), floatValue54 - floatValue62 - floatValue48) : 0.0F;
      return new AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerData3(floatValue43, floatValue44, floatValue45, floatValue47, floatValue46, floatValue48, floatValue43, floatValue57, flag7, floatValue61, floatValue54, floatValue62, floatValue63, floatValue64);
   }

   private float measure2(float f, float g, float h) {
      return this.measure22((f - g) / Math.max(0.001F, h - g), 0.0F, 1.0F);
   }

   private void invoke9(AutoBuy autoBuy9, float f, float g, float h) {
      float floatValue65 = this.measure22((f - g) / Math.max(1.0F, h), 0.0F, 1.0F);
      float floatValue66 = autoBuy9.reparseKazhdyeMin.minimum;
      float floatValue67 = autoBuy9.reparseKazhdyeMin.maximum;
      float floatValue68 = Math.max(1.0F, autoBuy9.reparseKazhdyeMin.step);
      float floatValue69 = floatValue66 + (floatValue67 - floatValue66) * floatValue65;
      float floatValue70 = floatValue66 + (float)RenderMath.measure35((double)((floatValue69 - floatValue66) / floatValue68), 0) * floatValue68;
      autoBuy9.reparseKazhdyeMin.invoke(floatValue70);
   }

   private void invoke10(
      RenderManager renderManager4,
      ClickGuiState clickGuiState17,
      String string,
      boolean bl,
      float f,
      float g,
      float h,
      String string2,
      ThemeContext themeContext4
   ) {
      Metrics metrics8 = themeContext4.getMetrics();
      ColorScheme colorScheme3 = themeContext4.getColorScheme();
      String text17 = AnimationKeyRegistry.resolve50("tab:" + string);
      float floatValue71 = clickGuiState17.measure5(text17, ClickGuiRenderUtils.check(clickGuiState17, f, g, h, h) ? 1.0F : 0.0F, SpringSpec.resolve11());
      renderManager4.invoke62(ClickGuiRenderUtils.measure7(floatValue71, clickGuiState17.measure8(text17)), f + h * 0.5F, g + h * 0.5F);

      try {
         renderManager4.invoke5(f, g, h, h, metrics8.measure(8.0F), ColorScheme.compute7(colorScheme3.getIntValue3(), colorScheme3.getIntValue5(), floatValue71 * 0.7F));
         renderManager4.invoke28(
            f,
            g,
            h,
            h,
            metrics8.measure(8.0F),
            ColorScheme.compute7(colorScheme3.getIntValue5(), ColorScheme.compute6(colorScheme3.getIntValue14(), 90), bl ? 0.72F : floatValue71),
            2.0F
         );
         ClickGuiRenderUtils.invoke4(
            renderManager4,
            metrics8,
            FontRegistry.fontObject8,
            f + h * 0.5F - ClickGuiRenderUtils.measure(FontRegistry.fontObject8, string2, 12.0F) * 0.5F,
            g,
            h,
            12.0F,
            string2,
            bl ? colorScheme3.getIntValue14() : ColorScheme.compute7(colorScheme3.getIntValue11(), colorScheme3.getIntValue12(), floatValue71)
         );
      } finally {
         renderManager4.invoke64();
      }
   }

   private void invoke11(
      RenderManager renderManager5,
      ClickGuiState clickGuiState18,
      String string,
      float f,
      float g,
      float h,
      String string2,
      boolean bl,
      boolean bl2,
      ThemeContext themeContext5
   ) {
      Metrics metrics9 = themeContext5.getMetrics();
      ColorScheme colorScheme4 = themeContext5.getColorScheme();
      String text18 = AnimationKeyRegistry.resolve50("iconBtn:" + string);
      float floatValue72 = clickGuiState18.measure5(text18, ClickGuiRenderUtils.check(clickGuiState18, f, g, h, h) ? 1.0F : 0.0F, SpringSpec.resolve11());
      renderManager5.invoke62(ClickGuiRenderUtils.measure7(floatValue72, clickGuiState18.measure8(text18)), f + h * 0.5F, g + h * 0.5F);
      boolean flag8 = false ;

      try {
         flag8 = true;
         int intValue11 = bl2 ? ColorScheme.compute6(colorScheme4.getIntValue14(), 30) : (bl ? ColorScheme.compute6(colorScheme4.compute2(), 40) : colorScheme4.getIntValue5());
         int intValue12 = bl2
            ? ColorScheme.compute6(colorScheme4.getIntValue14(), 90)
            : (bl ? ColorScheme.compute6(colorScheme4.compute2(), 120) : ColorScheme.compute6(colorScheme4.getIntValue13(), 20));
         int intValue13 = bl ? ColorScheme.compute6(colorScheme4.compute2(), 70) : colorScheme4.getIntValue7();
         int intValue14 = bl ? ColorScheme.compute6(colorScheme4.compute2(), 160) : ColorScheme.compute6(colorScheme4.getIntValue14(), 90);
         int intValue15 = ColorScheme.compute7(intValue11, intValue13, floatValue72);
         int intValue16 = ColorScheme.compute7(intValue12, intValue14, floatValue72);
         renderManager5.invoke5(f, g, h, h, metrics9.measure(6.0F), intValue15);
         renderManager5.invoke28(f, g, h, h, metrics9.measure(6.0F), intValue16, 0.5F);
         int intValue17 = bl2 ? colorScheme4.getIntValue14() : colorScheme4.getIntValue12();
         int intValue18 = bl ? colorScheme4.compute2() : colorScheme4.getIntValue13();
         int intValue19 = ColorScheme.compute7(intValue17, intValue18, floatValue72);
         ClickGuiRenderUtils.invoke4(
            renderManager5,
            metrics9,
            FontRegistry.fontObject8,
            f + h * 0.5F - ClickGuiRenderUtils.measure(FontRegistry.fontObject8, string2, 11.0F) * 0.5F,
            g,
            h,
            11.0F,
            string2,
            intValue19
         );
         flag8 = false;
      } finally {
         if (flag8) {
            renderManager5.invoke64();
         }
      }

      renderManager5.invoke64();
   }

   private void invoke12(
      RenderManager renderManager6, ClickGuiState clickGuiState19, AutoBuy autoBuy10, AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerBounds autoBuyModuleCardHandlerBounds6, ThemeContext themeContext6
   ) {
      Metrics metrics10 = themeContext6.getMetrics();
      ColorScheme colorScheme5 = themeContext6.getColorScheme();
      float floatValue73 = clickGuiState19.measure7(AnimationKeyRegistry.resolve45());
      float floatValue74 = this.measure7(autoBuyModuleCardHandlerBounds6, metrics10);
      float floatValue75 = this.measure23(this.typeUtils4, floatValue74);
      this.floatValue4 = floatValue75 - this.floatValue8;
      this.floatValue8 = floatValue75;
      float floatValue76 = metrics10.measure(62.0F);
      this.autoBuyModuleCardHandlerScrollLayout4 = this.resolve5(
         autoBuyModuleCardHandlerBounds6.x() + autoBuyModuleCardHandlerBounds6.width() - metrics10.measure(10.0F),
         autoBuyModuleCardHandlerBounds6.panelY() + floatValue76,
         autoBuyModuleCardHandlerBounds6.scrollbarW(),
         autoBuyModuleCardHandlerBounds6.panelH() - floatValue76 - metrics10.measure(10.0F),
         floatValue74,
         floatValue75,
         metrics10
      );
      renderManager6.invoke5(autoBuyModuleCardHandlerBounds6.x(), autoBuyModuleCardHandlerBounds6.panelY(), autoBuyModuleCardHandlerBounds6.width(), autoBuyModuleCardHandlerBounds6.panelH(), metrics10.measure(8.0F), colorScheme5.getIntValue3());
      renderManager6.invoke28(
         autoBuyModuleCardHandlerBounds6.x(), autoBuyModuleCardHandlerBounds6.panelY(), autoBuyModuleCardHandlerBounds6.width(), autoBuyModuleCardHandlerBounds6.panelH(), metrics10.measure(8.0F), colorScheme5.getIntValue5(), 0.5F
      );
      this.invoke34(renderManager6, autoBuyModuleCardHandlerBounds6.x(), autoBuyModuleCardHandlerBounds6.panelY(), autoBuyModuleCardHandlerBounds6.width(), autoBuyModuleCardHandlerBounds6.panelH(), metrics10, colorScheme5, 900);
      float floatValue77 = autoBuyModuleCardHandlerBounds6.x() + metrics10.measure(16.0F);
      float floatValue78 = autoBuyModuleCardHandlerBounds6.panelY() + metrics10.measure(14.0F);
      renderManager6.invoke65(floatValue73);

      try {
         ClickGuiRenderUtils.invoke4(
            renderManager6,
            metrics10,
            FontRegistry.fontObject4,
            floatValue77,
            floatValue78,
            metrics10.measure(16.0F),
            13.0F,
            "Конфигурации покупаемых предметов",
            colorScheme5.getIntValue12()
         );
         ClickGuiRenderUtils.invoke4(
            renderManager6,
            metrics10,
            FontRegistry.fontObject,
            floatValue77,
            floatValue78 + metrics10.measure(20.0F),
            metrics10.measure(12.0F),
            10.0F,
            "Загрузите готовый конфиг, чтобы не настраивать каждый предмет вручную.",
            colorScheme5.getIntValue11()
         );
         float floatValue79 = metrics10.measure(28.0F);
         float floatValue80 = metrics10.measure(8.0F);
         float floatValue81 = autoBuyModuleCardHandlerBounds6.x() + autoBuyModuleCardHandlerBounds6.width() - metrics10.measure(16.0F) - floatValue79;
         this.invoke11(renderManager6, clickGuiState19, "cloud_btn_Y", floatValue81, floatValue78, floatValue79, "Y", false, false, themeContext6);
         floatValue81 -= floatValue79 + floatValue80;
         this.invoke11(renderManager6, clickGuiState19, "cloud_btn_R", floatValue81, floatValue78, floatValue79, "R", false, false, themeContext6);
         floatValue81 -= floatValue79 + floatValue80;
         this.invoke11(renderManager6, clickGuiState19, "cloud_btn_T", floatValue81, floatValue78, floatValue79, "T", false, false, themeContext6);
      } finally {
         renderManager6.invoke66();
      }

      float floatValue82 = autoBuyModuleCardHandlerBounds6.x() + metrics10.measure(16.0F);
      float floatValue83 = autoBuyModuleCardHandlerBounds6.panelY() + floatValue76;
      float floatValue84 = autoBuyModuleCardHandlerBounds6.width() - metrics10.measure(25.0F) - autoBuyModuleCardHandlerBounds6.scrollbarW() - metrics10.measure(0.0F);
      float floatValue85 = autoBuyModuleCardHandlerBounds6.panelH() - floatValue76 - metrics10.measure(10.0F);
      renderManager6.invoke20();
      renderManager6.invoke24(floatValue82, floatValue83, floatValue84, floatValue85, metrics10.measure(6.0F), metrics10.measure(6.0F), metrics10.measure(6.0F), metrics10.measure(6.0F));
      boolean flag9 = false ;

      label346: {
         try {
            flag9 = true;
            if (this.items2.isEmpty()) {
               renderManager6.invoke65(floatValue73);

               try {
                  float floatValue86 = floatValue83 + floatValue85 * 0.5F - metrics10.measure(6.0F);
                  String text19 = "Конфигурации не найдены";
                  float floatValue87 = ClickGuiRenderUtils.measure(FontRegistry.fontObject, text19, 12.0F);
                  ClickGuiRenderUtils.invoke4(
                     renderManager6,
                     metrics10,
                     FontRegistry.fontObject,
                     floatValue82 + (floatValue84 - floatValue87) * 0.5F,
                     floatValue86 - metrics10.measure(10.0F),
                     metrics10.measure(12.0F),
                     12.0F,
                     text19,
                     colorScheme5.getIntValue11()
                  );
               } finally {
                  renderManager6.invoke66();
               }

               flag9 = false;
            } else {
               float floatValue88 = metrics10.measure(58.0F);
               float floatValue89 = metrics10.measure(8.0F);
               float floatValue90 = floatValue84 - metrics10.measure(24.0F);

               for (int intValue20 = 0; intValue20 < this.items2.size(); intValue20++) {
                  AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerTimedEntry autoBuyModuleCardHandlerTimedEntry4 = this.items2.get(intValue20);
                  float floatValue91 = floatValue83 + floatValue75 + intValue20 * (floatValue88 + floatValue89);
                  float floatValue92 = clickGuiState19.measure7("cfg_entry:" + autoBuyModuleCardHandlerTimedEntry4.name);
                  float floatValue93 = Math.min(floatValue92, floatValue73);
                  if (!(floatValue93 <= 0.01F)) {
                     float floatValue94 = (1.0F - floatValue92) * metrics10.measure(12.0F);
                     float floatValue95 = floatValue91 + floatValue94;
                     if (!(floatValue95 > floatValue83 + floatValue85) && !(floatValue95 + floatValue88 < floatValue83)) {
                        TextSetting textSetting6 = this.valuesByKey.computeIfAbsent(autoBuyModuleCardHandlerTimedEntry4.name, string -> new TextSetting("Name", string));
                        boolean flag10 = clickGuiState19.getTextSetting() == textSetting6;
                        if (!flag10 && this.text2 != null && this.text2.equals(autoBuyModuleCardHandlerTimedEntry4.name)) {
                           String text20 = textSetting6.value.trim();
                           if (!text20.isEmpty() && !text20.equals(autoBuyModuleCardHandlerTimedEntry4.name)) {
                              autoBuy10.invoke6(autoBuyModuleCardHandlerTimedEntry4.name, text20);
                              if (this.text.equals(autoBuyModuleCardHandlerTimedEntry4.name)) {
                                 this.text = text20;
                              }

                              this.valuesByKey.remove(autoBuyModuleCardHandlerTimedEntry4.name);
                              this.invoke6(autoBuy10);
                              this.text2 = null;
                              flag9 = false;
                              break label346;
                           }

                           this.text2 = null;
                        }

                        if (flag10) {
                           this.text2 = autoBuyModuleCardHandlerTimedEntry4.name;
                        }

                        boolean flag11 = this.text.equals(autoBuyModuleCardHandlerTimedEntry4.name);
                        float floatValue96 = clickGuiState19.measure5("cfg_active:" + autoBuyModuleCardHandlerTimedEntry4.name, flag11 ? 1.0F : 0.0F, SPRING_SPEC);
                        float floatValue97 = clickGuiState19.measure5(
                           "cfg_hover:" + autoBuyModuleCardHandlerTimedEntry4.name,
                           ClickGuiRenderUtils.check(clickGuiState19, floatValue82, floatValue95, floatValue90, floatValue88) ? 1.0F : 0.0F,
                           SpringSpec.resolve11()
                        );
                        renderManager6.invoke65(floatValue93);
                        renderManager6.invoke62(
                           ClickGuiRenderUtils.measure8(floatValue97, Math.abs(clickGuiState19.measure8("cfg_hover:" + autoBuyModuleCardHandlerTimedEntry4.name)), 0.01F, 5.0E-4F),
                           floatValue82 + floatValue90 * 0.5F,
                           floatValue95 + floatValue88 * 0.5F
                        );
                        boolean flag12 = false ;

                        try {
                           flag12 = true;
                           int intValue21 = ColorScheme.compute7(colorScheme5.getIntValue4(), colorScheme5.getIntValue6(), floatValue97);
                           int intValue22 = ColorScheme.compute7(intValue21, ColorScheme.compute6(colorScheme5.getIntValue14(), 30), floatValue96 * 0.35F);
                           renderManager6.invoke5(floatValue82, floatValue95, floatValue90, floatValue88, metrics10.measure(8.0F), intValue22);
                           float floatValue98 = floatValue82 + metrics10.measure(16.0F);
                           float floatValue99 = floatValue95 + metrics10.measure(12.0F);
                           int intValue23 = ColorScheme.compute7(colorScheme5.getIntValue13(), colorScheme5.getIntValue14(), floatValue96);
                           if (flag10) {
                              String text21 = textSetting6.value;
                              if (System.currentTimeMillis() % 1000L > 500L) {
                                 text21 = text21 + "|";
                              }

                              ClickGuiRenderUtils.invoke4(
                                 renderManager6, metrics10, FontRegistry.fontObject4, floatValue98, floatValue99, metrics10.measure(14.0F), 13.0F, text21, colorScheme5.getIntValue13()
                              );
                           } else {
                              ClickGuiRenderUtils.invoke4(
                                 renderManager6, metrics10, FontRegistry.fontObject4, floatValue98, floatValue99, metrics10.measure(14.0F), 13.0F, autoBuyModuleCardHandlerTimedEntry4.name, intValue23
                              );
                           }

                           float floatValue100 = floatValue99 + metrics10.measure(22.0F);
                           ClickGuiRenderUtils.invoke4(
                              renderManager6,
                              metrics10,
                              FontRegistry.fontObject8,
                              floatValue98,
                              floatValue100 - metrics10.measure(0.5F),
                              metrics10.measure(12.0F),
                              8.0F,
                              "r",
                              colorScheme5.getIntValue11()
                           );
                           float floatValue101 = floatValue98 + metrics10.measure(14.0F);
                           float floatValue102 = ClickGuiRenderUtils.measure(FontRegistry.fontObject, autoBuyModuleCardHandlerTimedEntry4.author, 10.0F);
                           ClickGuiRenderUtils.invoke4(
                              renderManager6, metrics10, FontRegistry.fontObject, floatValue101, floatValue100, metrics10.measure(12.0F), 10.0F, autoBuyModuleCardHandlerTimedEntry4.author, colorScheme5.getIntValue12()
                           );
                           floatValue101 += floatValue102 + metrics10.measure(6.0F);
                           ClickGuiRenderUtils.invoke4(
                              renderManager6, metrics10, FontRegistry.fontObject8, floatValue101, floatValue100 + 0.5F, metrics10.measure(12.0F), 6.0F, "k", colorScheme5.getIntValue11()
                           );
                           floatValue101 += metrics10.measure(12.0F);
                           ClickGuiRenderUtils.invoke4(
                              renderManager6,
                              metrics10,
                              FontRegistry.fontObject8,
                              floatValue101,
                              floatValue100 - metrics10.measure(0.5F),
                              metrics10.measure(12.0F),
                              10.0F,
                              "Q",
                              colorScheme5.getIntValue11()
                           );
                           floatValue101 += metrics10.measure(14.0F);
                           String text22 = SIMPLE_DATE_FORMAT_2.format(new Date(autoBuyModuleCardHandlerTimedEntry4.timestamp));
                           ClickGuiRenderUtils.invoke4(
                              renderManager6, metrics10, FontRegistry.fontObject, floatValue101, floatValue100, metrics10.measure(12.0F), 10.0F, text22, colorScheme5.getIntValue12()
                           );
                           float floatValue103 = metrics10.measure(26.0F);
                           float floatValue104 = metrics10.measure(8.0F);
                           float floatValue105 = floatValue82 + floatValue90 + metrics10.measure(8.0F);
                           ClickGuiRenderUtils.invoke4(
                              renderManager6, metrics10, FontRegistry.fontObject8, floatValue105, floatValue95 + (floatValue88 - floatValue103) * 0.5F, floatValue103, 12.0F, "O", colorScheme5.getIntValue12()
                           );
                           float floatValue106 = floatValue82 + floatValue90 - metrics10.measure(12.0F) - floatValue103;
                           this.invoke11(
                              renderManager6,
                              clickGuiState19,
                              "cfg_I_" + autoBuyModuleCardHandlerTimedEntry4.name,
                              floatValue106,
                              floatValue95 + (floatValue88 - floatValue103) * 0.5F,
                              floatValue103,
                              "I",
                              true,
                              false,
                              themeContext6
                           );
                           floatValue106 -= floatValue103 + floatValue104;
                           this.invoke11(
                              renderManager6,
                              clickGuiState19,
                              "cfg_U_" + autoBuyModuleCardHandlerTimedEntry4.name,
                              floatValue106,
                              floatValue95 + (floatValue88 - floatValue103) * 0.5F,
                              floatValue103,
                              "U",
                              false,
                              flag11,
                              themeContext6
                           );
                           flag12 = false;
                        } finally {
                           if (flag12) {
                              renderManager6.invoke64();
                              renderManager6.invoke66();
                           }
                        }

                        renderManager6.invoke64();
                        renderManager6.invoke66();
                     }
                  }
               }

               flag9 = false;
            }
         } finally {
            if (flag9) {
               renderManager6.invoke20();
               renderManager6.invoke25();
            }
         }

         renderManager6.invoke20();
         renderManager6.invoke25();
         ClickGuiRenderUtils.invoke15(renderManager6, metrics10, colorScheme5, floatValue82, floatValue83, floatValue84, floatValue85, metrics10.measure(6.0F), this.floatValue4);
         this.invoke35(renderManager6, this.autoBuyModuleCardHandlerScrollLayout4, this.flag6, this.floatValue4, metrics10, colorScheme5);
         return;
      }

      renderManager6.invoke20();
      renderManager6.invoke25();
   }

   private void invoke13(
      RenderManager renderManager7,
      DrawContext drawContext,
      ClickGuiState clickGuiState20,
      AutoBuy autoBuy11,
      AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerBounds autoBuyModuleCardHandlerBounds7,
      ThemeContext themeContext7
   ) {
      Metrics metrics11 = themeContext7.getMetrics();
      ColorScheme colorScheme6 = themeContext7.getColorScheme();
      float floatValue107 = clickGuiState20.measure7(AnimationKeyRegistry.resolve45());
      float floatValue108 = this.measure6(autoBuyModuleCardHandlerBounds7, metrics11);
      float floatValue109 = this.measure23(this.typeUtils3, floatValue108);
      this.floatValue3 = floatValue109 - this.floatValue7;
      this.floatValue7 = floatValue109;
      int intValue24 = AutoBuy.ITEMS_2.size();
      if (this.intValue >= 0 && intValue24 > this.intValue) {
         this.timedAnimation2 = new TimedAnimation(AnimationMode.EASE_OUT_CUBIC, 600L);
         this.timedAnimation2.setTimestamp2(1.0);
      }

      this.intValue = intValue24;
      this.timedAnimation2.setTimestamp2(1.0);
      this.timedAnimation.setTimestamp2(1.0);
      float floatValue110 = this.measure22((float)this.timedAnimation.getDoubleValue3(), 0.0F, 1.0F);
      float floatValue111 = this.measure22(1.0F - (float)this.timedAnimation2.getDoubleValue3(), 0.0F, 1.0F);
      float floatValue112 = metrics11.measure(42.0F);
      this.autoBuyModuleCardHandlerScrollLayout3 = this.resolve5(
         autoBuyModuleCardHandlerBounds7.x() + autoBuyModuleCardHandlerBounds7.width() - metrics11.measure(10.0F),
         autoBuyModuleCardHandlerBounds7.panelY() + floatValue112,
         autoBuyModuleCardHandlerBounds7.scrollbarW(),
         autoBuyModuleCardHandlerBounds7.panelH() - floatValue112 - metrics11.measure(10.0F),
         floatValue108,
         floatValue109,
         metrics11
      );
      renderManager7.invoke5(autoBuyModuleCardHandlerBounds7.x(), autoBuyModuleCardHandlerBounds7.panelY(), autoBuyModuleCardHandlerBounds7.width(), autoBuyModuleCardHandlerBounds7.panelH(), metrics11.measure(8.0F), colorScheme6.getIntValue3());
      renderManager7.invoke28(
         autoBuyModuleCardHandlerBounds7.x(), autoBuyModuleCardHandlerBounds7.panelY(), autoBuyModuleCardHandlerBounds7.width(), autoBuyModuleCardHandlerBounds7.panelH(), metrics11.measure(8.0F), colorScheme6.getIntValue5(), 0.5F
      );
      this.invoke34(renderManager7, autoBuyModuleCardHandlerBounds7.x(), autoBuyModuleCardHandlerBounds7.panelY(), autoBuyModuleCardHandlerBounds7.width(), autoBuyModuleCardHandlerBounds7.panelH(), metrics11, colorScheme6, 600);
      float floatValue113 = autoBuyModuleCardHandlerBounds7.x() + metrics11.measure(16.0F);
      float floatValue114 = autoBuyModuleCardHandlerBounds7.panelY() + metrics11.measure(14.0F);
      renderManager7.invoke65(floatValue107);

      try {
         ClickGuiRenderUtils.invoke4(
            renderManager7, metrics11, FontRegistry.fontObject4, floatValue113, floatValue114, metrics11.measure(16.0F), 14.0F, "История покупок", colorScheme6.getIntValue12()
         );
         float floatValue115 = ClickGuiRenderUtils.measure(FontRegistry.fontObject4, "История покупок", 14.0F);
         this.invoke33(renderManager7, metrics11, colorScheme6, floatValue113 + floatValue115 + metrics11.measure(12.0F), floatValue114, floatValue110, floatValue111, intValue24);
         float floatValue116 = metrics11.measure(75.0F);
         float floatValue117 = metrics11.measure(20.0F);
         float floatValue118 = autoBuyModuleCardHandlerBounds7.x() + autoBuyModuleCardHandlerBounds7.width() - metrics11.measure(16.0F) - floatValue116;
         String text23 = "history_clear_all";
         float floatValue119 = clickGuiState20.measure5(
            text23, ClickGuiRenderUtils.check(clickGuiState20, floatValue118, floatValue114, floatValue116, floatValue117) ? 1.0F : 0.0F, SpringSpec.resolve11()
         );
         renderManager7.invoke62(ClickGuiRenderUtils.measure7(floatValue119, clickGuiState20.measure8(text23)), floatValue118 + floatValue116 * 0.5F, floatValue114 + floatValue117 * 0.5F);
         renderManager7.invoke5(
            floatValue118,
            floatValue114,
            floatValue116,
            floatValue117,
            metrics11.measure(6.0F),
            ColorScheme.compute7(ColorScheme.compute6(colorScheme6.compute2(), 20), ColorScheme.compute6(colorScheme6.compute2(), 40), floatValue119)
         );
         renderManager7.invoke28(
            floatValue118,
            floatValue114,
            floatValue116,
            floatValue117,
            metrics11.measure(6.0F),
            ColorScheme.compute7(ColorScheme.compute6(colorScheme6.compute2(), 60), ColorScheme.compute6(colorScheme6.compute2(), 120), floatValue119),
            0.5F
         );
         float floatValue120 = ClickGuiRenderUtils.measure(FontRegistry.fontObject8, "I", 10.0F);
         float floatValue121 = ClickGuiRenderUtils.measure(FontRegistry.fontObject, "Очистить", 10.0F);
         float floatValue122 = metrics11.measure(4.0F);
         float floatValue123 = floatValue118 + (floatValue116 - (floatValue120 + floatValue122 + floatValue121)) / 2.0F;
         ClickGuiRenderUtils.invoke4(
            renderManager7,
            metrics11,
            FontRegistry.fontObject8,
            floatValue123,
            floatValue114,
            floatValue117,
            10.0F,
            "I",
            ColorScheme.compute7(colorScheme6.getIntValue12(), colorScheme6.compute2(), floatValue119)
         );
         ClickGuiRenderUtils.invoke4(
            renderManager7,
            metrics11,
            FontRegistry.fontObject,
            floatValue123 + floatValue120 + floatValue122,
            floatValue114,
            floatValue117,
            10.0F,
            "Очистить",
            ColorScheme.compute7(colorScheme6.getIntValue12(), colorScheme6.getIntValue13(), floatValue119)
         );
         renderManager7.invoke64();
      } finally {
         renderManager7.invoke66();
      }

      float floatValue124 = autoBuyModuleCardHandlerBounds7.x() + metrics11.measure(16.0F);
      float floatValue125 = autoBuyModuleCardHandlerBounds7.panelY() + floatValue112;
      float floatValue126 = autoBuyModuleCardHandlerBounds7.width() - metrics11.measure(20.0F) - autoBuyModuleCardHandlerBounds7.scrollbarW();
      float floatValue127 = autoBuyModuleCardHandlerBounds7.panelH() - floatValue112 - metrics11.measure(10.0F);
      float floatValue128 = this.typeUtils3.measure();
      float floatValue129 = floatValue126 - metrics11.measure(36.0F);
      renderManager7.invoke20();
      renderManager7.invoke24(floatValue124, floatValue125, floatValue126, floatValue127, metrics11.measure(6.0F), metrics11.measure(6.0F), metrics11.measure(6.0F), metrics11.measure(6.0F));
      boolean flag13 = false ;

      try {
         flag13 = true;
         if (AutoBuy.ITEMS_2.isEmpty()) {
            renderManager7.invoke65(floatValue107);
            boolean flag14 = false ;

            try {
               flag14 = true;
               String text24 = "История покупок пуста";
               float floatValue130 = ClickGuiRenderUtils.measure(FontRegistry.fontObject, text24, 12.0F);
               ClickGuiRenderUtils.invoke4(
                  renderManager7,
                  metrics11,
                  FontRegistry.fontObject,
                  floatValue124 + (floatValue126 - floatValue130) * 0.5F,
                  floatValue125 + floatValue127 * 0.5F - metrics11.measure(6.0F),
                  metrics11.measure(12.0F),
                  12.0F,
                  text24,
                  colorScheme6.getIntValue11()
               );
               flag14 = false;
            } finally {
               if (flag14) {
                  renderManager7.invoke66();
               }
            }

            renderManager7.invoke66();
            flag13 = false;
         } else {
            float floatValue131 = metrics11.measure(42.0F);
            float floatValue132 = metrics11.measure(6.0F);

            for (int intValue25 = 0; intValue25 < AutoBuy.ITEMS_2.size(); intValue25++) {
               AutoBuy.AutoBuyState autoBuyState = AutoBuy.ITEMS_2.get(intValue25);
               float floatValue133 = floatValue125 + floatValue128 + intValue25 * (floatValue131 + floatValue132);
               if (!(floatValue133 > floatValue125 + floatValue127) && !(floatValue133 + floatValue131 < floatValue125)) {
                  renderManager7.invoke65(floatValue107);

                  try {
                     renderManager7.invoke5(floatValue124, floatValue133, floatValue129, floatValue131, metrics11.measure(8.0F), colorScheme6.getIntValue4());
                     renderManager7.invoke28(floatValue124, floatValue133, floatValue129, floatValue131, metrics11.measure(8.0F), colorScheme6.getIntValue6(), 0.5F);
                     float floatValue134 = metrics11.measure(28.0F);
                     float floatValue135 = floatValue124 + metrics11.measure(8.0F);
                     float floatValue136 = floatValue133 + (floatValue131 - floatValue134) * 0.5F;
                     AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerDisplayEntry autoBuyModuleCardHandlerDisplayEntry3 = this.resolve2(autoBuy11, autoBuyState.text2);
                     this.invoke53(
                        renderManager7,
                        drawContext,
                        autoBuyModuleCardHandlerDisplayEntry3,
                        floatValue135 + metrics11.measure(6.0F),
                        floatValue136 + metrics11.measure(6.0F),
                        metrics11.measure(16.0F),
                        floatValue107,
                        floatValue124,
                        floatValue125,
                        floatValue126,
                        floatValue127
                     );
                     float floatValue137 = floatValue135 + floatValue134 + metrics11.measure(6.0F);
                     String text25 = "Куплено ";
                     String text26 = (autoBuyState.intValue > 1 ? "x" + autoBuyState.intValue + " " : "") + autoBuyState.text2;
                     String text27 = " за ";
                     String text28 = this.resolve22(autoBuyState.timestamp);
                     float floatValue138 = ClickGuiRenderUtils.measure(FontRegistry.fontObject, text25, 10.0F);
                     float floatValue139 = ClickGuiRenderUtils.measure(FontRegistry.fontObject4, text26, 10.0F);
                     float floatValue140 = ClickGuiRenderUtils.measure(FontRegistry.fontObject, text27, 10.0F);
                     ClickGuiRenderUtils.invoke4(renderManager7, metrics11, FontRegistry.fontObject, floatValue137, floatValue133, floatValue131, 10.0F, text25, colorScheme6.getIntValue12());
                     ClickGuiRenderUtils.invoke4(
                        renderManager7, metrics11, FontRegistry.fontObject4, floatValue137 + floatValue138, floatValue133 - 1.0F, floatValue131, 10.0F, text26, colorScheme6.getIntValue13()
                     );
                     ClickGuiRenderUtils.invoke4(
                        renderManager7, metrics11, FontRegistry.fontObject, floatValue137 + floatValue138 + floatValue139, floatValue133, floatValue131, 10.0F, text27, colorScheme6.getIntValue12()
                     );
                     ClickGuiRenderUtils.invoke4(
                        renderManager7,
                        metrics11,
                        FontRegistry.fontObject4,
                        floatValue137 + floatValue138 + floatValue139 + floatValue140,
                        floatValue133 - 1.0F,
                        floatValue131,
                        10.0F,
                        text28,
                        ColorScheme.compute6(colorScheme6.compute(), 200)
                     );
                     String text29 = SIMPLE_DATE_FORMAT.format(new Date(autoBuyState.timestamp2));
                     float floatValue141 = ClickGuiRenderUtils.measure(FontRegistry.fontObject, text29, 9.0F);
                     ClickGuiRenderUtils.invoke4(
                        renderManager7,
                        metrics11,
                        FontRegistry.fontObject,
                        floatValue124 + floatValue129 - floatValue141 - metrics11.measure(10.0F),
                        floatValue133,
                        floatValue131,
                        9.0F,
                        text29,
                        colorScheme6.getIntValue11()
                     );
                     ClickGuiRenderUtils.invoke4(
                        renderManager7,
                        metrics11,
                        FontRegistry.fontObject8,
                        floatValue124 + floatValue129 - floatValue141 - metrics11.measure(24.0F),
                        floatValue133 - 1.0F,
                        floatValue131,
                        10.0F,
                        "Q",
                        colorScheme6.getIntValue11()
                     );
                     float floatValue142 = metrics11.measure(26.0F);
                     float floatValue143 = floatValue124 + floatValue129 + metrics11.measure(6.0F);
                     this.invoke11(
                        renderManager7,
                        clickGuiState20,
                        "hist_del_" + autoBuyState.timestamp2 + "_" + intValue25,
                        floatValue143,
                        floatValue133 + (floatValue131 - floatValue142) * 0.5F,
                        floatValue142,
                        "I",
                        true,
                        false,
                        themeContext7
                     );
                  } finally {
                     renderManager7.invoke66();
                  }
               }
            }

            flag13 = false;
         }
      } finally {
         if (flag13) {
            renderManager7.invoke20();
            renderManager7.invoke25();
         }
      }

      renderManager7.invoke20();
      renderManager7.invoke25();
      ClickGuiRenderUtils.invoke15(renderManager7, metrics11, colorScheme6, floatValue124, floatValue125, floatValue126, floatValue127, metrics11.measure(6.0F), this.floatValue3);
      this.invoke35(renderManager7, this.autoBuyModuleCardHandlerScrollLayout3, this.flag5, this.floatValue3, metrics11, colorScheme6);
   }

   private void invoke14(
      RenderManager renderManager8,
      DrawContext drawContext,
      ClickGuiState clickGuiState21,
      AutoBuy autoBuy12,
      AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerBounds autoBuyModuleCardHandlerBounds8,
      ThemeContext themeContext8
   ) {
      Metrics metrics12 = themeContext8.getMetrics();
      ColorScheme colorScheme7 = themeContext8.getColorScheme();
      float floatValue144 = clickGuiState21.measure7(AnimationKeyRegistry.resolve45());
      List items3 = this.resolve8(autoBuy12, this.catalogSearch.value);
      float floatValue145 = this.measure3(autoBuy12, autoBuyModuleCardHandlerBounds8, metrics12);
      float floatValue146 = this.measure23(this.typeUtils, floatValue145);
      this.floatValue = floatValue146 - this.floatValue5;
      this.floatValue5 = floatValue146;
      this.autoBuyModuleCardHandlerScrollLayout = this.resolve5(
         autoBuyModuleCardHandlerBounds8.catalogScrollbarX(), autoBuyModuleCardHandlerBounds8.catalogViewportY(), autoBuyModuleCardHandlerBounds8.scrollbarW(), autoBuyModuleCardHandlerBounds8.catalogViewportH(), floatValue145, floatValue146, metrics12
      );
      renderManager8.invoke5(
         autoBuyModuleCardHandlerBounds8.leftX(), autoBuyModuleCardHandlerBounds8.panelY(), autoBuyModuleCardHandlerBounds8.leftW(), autoBuyModuleCardHandlerBounds8.panelH(), metrics12.measure(8.0F), colorScheme7.getIntValue3()
      );
      renderManager8.invoke28(
         autoBuyModuleCardHandlerBounds8.leftX(), autoBuyModuleCardHandlerBounds8.panelY(), autoBuyModuleCardHandlerBounds8.leftW(), autoBuyModuleCardHandlerBounds8.panelH(), metrics12.measure(8.0F), colorScheme7.getIntValue5(), 0.5F
      );
      this.invoke34(renderManager8, autoBuyModuleCardHandlerBounds8.leftX(), autoBuyModuleCardHandlerBounds8.panelY(), autoBuyModuleCardHandlerBounds8.leftW(), autoBuyModuleCardHandlerBounds8.panelH(), metrics12, colorScheme7, 0);
      ClickGuiRenderUtils.invoke4(
         renderManager8,
         metrics12,
         FontRegistry.fontObject4,
         autoBuyModuleCardHandlerBounds8.leftX() + metrics12.measure(12.0F),
         autoBuyModuleCardHandlerBounds8.panelY() + metrics12.measure(11.0F),
         metrics12.measure(14.0F),
         12.0F,
         "Каталог предметов",
         colorScheme7.getIntValue12()
      );
      ClickGuiRenderUtils.invoke4(
         renderManager8,
         metrics12,
         FontRegistry.fontObject,
         autoBuyModuleCardHandlerBounds8.leftX() + metrics12.measure(12.0F),
         autoBuyModuleCardHandlerBounds8.panelY() + metrics12.measure(28.0F),
         metrics12.measure(12.0F),
         10.0F,
         "ЛКМ по предмету — настроить цену",
         colorScheme7.getIntValue11()
      );
      this.invoke17(renderManager8, clickGuiState21, autoBuyModuleCardHandlerBounds8, metrics12, colorScheme7);
      renderManager8.invoke20();
      renderManager8.invoke24(
         autoBuyModuleCardHandlerBounds8.catalogViewportX(),
         autoBuyModuleCardHandlerBounds8.catalogViewportY(),
         autoBuyModuleCardHandlerBounds8.catalogViewportW(),
         autoBuyModuleCardHandlerBounds8.catalogViewportH(),
         metrics12.measure(6.0F),
         metrics12.measure(6.0F),
         metrics12.measure(6.0F),
         metrics12.measure(6.0F)
      );

      try {
         this.invoke15(renderManager8, drawContext, clickGuiState21, items3, autoBuyModuleCardHandlerBounds8, metrics12, colorScheme7, floatValue146, floatValue144);
      } finally {
         renderManager8.invoke20();
         renderManager8.invoke25();
      }
   }

   private void invoke15(
      RenderManager renderManager9,
      DrawContext drawContext,
      ClickGuiState clickGuiState22,
      List<AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerDisplayEntry> list,
      AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerBounds autoBuyModuleCardHandlerBounds9,
      Metrics metrics13,
      ColorScheme colorScheme8,
      float f,
      float g
   ) {
      int intValue26 = this.compute2(autoBuyModuleCardHandlerBounds9, metrics13);
      float floatValue147 = this.measure8(metrics13);
      float floatValue148 = this.measure9(metrics13);
      float floatValue149 = this.measure10(metrics13);
      int intValue27 = Math.max(1, (list.size() + intValue26 - 1) / intValue26);
      int intValue28 = Math.max(0, (int)Math.floor(-f / (floatValue148 + floatValue149)) - 1);
      int intValue29 = Math.min(intValue27, (int)Math.ceil((autoBuyModuleCardHandlerBounds9.catalogViewportH() - f) / (floatValue148 + floatValue149)) + 1);

      for (int intValue30 = intValue28; intValue30 < intValue29; intValue30++) {
         for (int intValue31 = 0; intValue31 < intValue26; intValue31++) {
            int intValue32 = intValue30 * intValue26 + intValue31;
            if (intValue32 >= list.size()) {
               break;
            }

            AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerDisplayEntry autoBuyModuleCardHandlerDisplayEntry4 = (AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerDisplayEntry)list.get(intValue32);
            float floatValue150 = autoBuyModuleCardHandlerBounds9.catalogViewportX() + intValue31 * (floatValue147 + floatValue149);
            float floatValue151 = autoBuyModuleCardHandlerBounds9.catalogViewportY() + f + intValue30 * (floatValue148 + floatValue149);
            if (!(floatValue151 > autoBuyModuleCardHandlerBounds9.catalogViewportY() + autoBuyModuleCardHandlerBounds9.catalogViewportH()) && !(floatValue151 + floatValue148 < autoBuyModuleCardHandlerBounds9.catalogViewportY())) {
               float floatValue152 = clickGuiState22.measure7(AnimationKeyRegistry.resolve44(autoBuyModuleCardHandlerDisplayEntry4.key()));
               if (intValue32 >= 80) {
                  floatValue152 = g;
               }

               if (!(floatValue152 <= 0.01F)) {
                  float floatValue153 = (1.0F - floatValue152) * metrics13.measure(9.0F);
                  renderManager9.invoke65(floatValue152);

                  try {
                     this.invoke16(
                        renderManager9,
                        drawContext,
                        clickGuiState22,
                        autoBuyModuleCardHandlerDisplayEntry4,
                        floatValue150,
                        floatValue151 + floatValue153,
                        floatValue147,
                        floatValue148,
                        metrics13,
                        colorScheme8,
                        Math.min(floatValue152, g),
                        autoBuyModuleCardHandlerBounds9.catalogViewportX(),
                        autoBuyModuleCardHandlerBounds9.catalogViewportY(),
                        autoBuyModuleCardHandlerBounds9.catalogViewportW(),
                        autoBuyModuleCardHandlerBounds9.catalogViewportH()
                     );
                  } finally {
                     renderManager9.invoke66();
                  }
               }
            }
         }
      }
   }

   private void invoke16(
      RenderManager renderManager10,
      DrawContext drawContext,
      ClickGuiState clickGuiState23,
      AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerDisplayEntry autoBuyModuleCardHandlerDisplayEntry5,
      float f,
      float g,
      float h,
      float i,
      Metrics metrics14,
      ColorScheme colorScheme9,
      float j,
      float k,
      float l,
      float m,
      float n
   ) {
      boolean flag15 = AutoBuy.VALUES_BY_KEY.containsKey(autoBuyModuleCardHandlerDisplayEntry5.key());
      boolean flag16 = autoBuyModuleCardHandlerDisplayEntry5.key().equals(this.text3);
      int intValue33 = AutoBuy.compute22(autoBuyModuleCardHandlerDisplayEntry5.key());
      int intValue34 = AutoBuy.compute23(autoBuyModuleCardHandlerDisplayEntry5.key());
      float floatValue154 = clickGuiState23.measure5(
         AnimationKeyRegistry.resolve47(autoBuyModuleCardHandlerDisplayEntry5.key()), ClickGuiRenderUtils.check(clickGuiState23, f, g, h, i) ? 1.0F : 0.0F, SpringSpec.resolve11()
      );
      float floatValue155 = clickGuiState23.measure5("ab_settings_tile:" + autoBuyModuleCardHandlerDisplayEntry5.key(), flag16 ? 1.0F : 0.0F, SPRING_SPEC_2);
      SpringAnimation springAnimation = clickGuiState23.getValuesByKey().get(AnimationKeyRegistry.resolve47(autoBuyModuleCardHandlerDisplayEntry5.key()));
      float floatValue156 = springAnimation == null ? 0.0F : Math.abs(springAnimation.getFloatValue2());
      float floatValue157 = ClickGuiRenderUtils.measure8(floatValue154, floatValue156, 0.04F, 0.014F);
      float floatValue158 = metrics14.measure(34.0F);
      float floatValue159 = f + (h - floatValue158) * 0.5F;
      float floatValue160 = g + metrics14.measure(1.0F);
      float floatValue161 = floatValue159 + floatValue158 * 0.5F;
      float floatValue162 = floatValue160 + floatValue158 * 0.5F;
      if (floatValue154 > 0.01F) {
         renderManager10.invoke41(
            floatValue159,
            floatValue160,
            floatValue158,
            floatValue158,
            metrics14.measure(8.0F),
            metrics14.measure(16.0F) * floatValue154,
            metrics14.measure(2.0F),
            ColorScheme.compute6(colorScheme9.getIntValue14(), Math.round(18.0F * floatValue154))
         );
      }

      renderManager10.invoke62(floatValue157, floatValue161, floatValue162);
      boolean flag17 = false ;

      try {
         flag17 = true;
         if (!flag15 && !flag16) {
            renderManager10.invoke5(
               floatValue159,
               floatValue160,
               floatValue158,
               floatValue158,
               metrics14.measure(8.0F),
               ColorScheme.compute7(colorScheme9.getIntValue3(), colorScheme9.getIntValue5(), floatValue154)
            );
         } else {
            renderManager10.invoke37(
               floatValue159,
               floatValue160,
               floatValue158,
               floatValue158,
               metrics14.measure(8.0F),
               ColorScheme.compute6(colorScheme9.getIntValue14(), Math.round(46.0F + 28.0F * floatValue155)),
               ColorScheme.compute6(colorScheme9.getIntValue4(), 255)
            );
         }

         renderManager10.invoke28(
            floatValue159,
            floatValue160,
            floatValue158,
            floatValue158,
            metrics14.measure(8.0F),
            ColorScheme.compute7(
               colorScheme9.getIntValue5(), ColorScheme.compute6(colorScheme9.getIntValue14(), 108), Math.max(Math.max(floatValue154, floatValue155), flag15 ? 0.55F : 0.0F)
            ),
            0.5F
         );
         ItemStack itemStack2 = autoBuyModuleCardHandlerDisplayEntry5.custom() ? SpecialItemIconRenderer.resolve(autoBuyModuleCardHandlerDisplayEntry5.key()) : autoBuyModuleCardHandlerDisplayEntry5.stack();
         if (itemStack2 != null && !itemStack2.isEmpty()) {
            this.invoke53(
               renderManager10,
               drawContext,
               autoBuyModuleCardHandlerDisplayEntry5,
               floatValue159 + metrics14.measure(7.0F),
               floatValue160 + metrics14.measure(7.0F),
               metrics14.measure(20.0F),
               j,
               k,
               l,
               m,
               n
            );
            flag17 = false;
         } else {
            this.invoke32(renderManager10, metrics14, FontRegistry.fontObject4, floatValue159, floatValue160, floatValue158, floatValue158, 12.0F, "?", colorScheme9.getIntValue10());
            flag17 = false;
         }
      } finally {
         if (flag17) {
            renderManager10.invoke64();
         }
      }

      renderManager10.invoke64();
      if (this.check8(autoBuyModuleCardHandlerDisplayEntry5.key()) && (intValue33 > 0 || intValue34 < 100)) {
         String text30 = intValue33 + "-" + intValue34 + "%";
         float floatValue163 = metrics14.measure(13.0F);
         float floatValue164 = Math.max(metrics14.measure(24.0F), ClickGuiRenderUtils.measure(FontRegistry.fontObject, text30, 7.5F) + metrics14.measure(8.0F));
         float floatValue165 = Math.min(floatValue159 + floatValue158 - floatValue164 + metrics14.measure(4.0F), f + h - floatValue164);
         float floatValue166 = Math.max(g, floatValue160 - metrics14.measure(5.0F));
         renderManager10.invoke5(floatValue165, floatValue166, floatValue164, floatValue163, metrics14.measure(5.0F), ColorScheme.compute6(colorScheme9.getIntValue2(), 238));
         renderManager10.invoke28(floatValue165, floatValue166, floatValue164, floatValue163, metrics14.measure(5.0F), ColorScheme.compute6(colorScheme9.getIntValue14(), 116), 0.5F);
         this.invoke32(
            renderManager10,
            metrics14,
            FontRegistry.fontObject,
            floatValue165,
            floatValue166,
            floatValue164,
            floatValue163,
            7.5F,
            text30,
            ColorScheme.compute6(colorScheme9.getIntValue14(), 205)
         );
      }

      String text31 = ClickGuiRenderUtils.resolve3(FontRegistry.fontObject, autoBuyModuleCardHandlerDisplayEntry5.label(), 9.0F, h - metrics14.measure(6.0F));
      String text32 = ClickGuiRenderUtils.resolve3(
         FontRegistry.fontObject,
         flag15 ? this.resolve22(AutoBuy.VALUES_BY_KEY.getOrDefault(autoBuyModuleCardHandlerDisplayEntry5.key(), 0L)) : "не задано",
         8.5F,
         h - metrics14.measure(6.0F)
      );
      float floatValue167 = ClickGuiRenderUtils.measure(FontRegistry.fontObject, text31, 9.0F);
      float floatValue168 = ClickGuiRenderUtils.measure(FontRegistry.fontObject, text32, 8.5F);
      ClickGuiRenderUtils.invoke4(
         renderManager10,
         metrics14,
         FontRegistry.fontObject,
         f + (h - floatValue167) * 0.5F,
         g + metrics14.measure(42.0F),
         metrics14.measure(11.0F),
         9.0F,
         text31,
         ColorScheme.compute7(colorScheme9.getIntValue11(), colorScheme9.getIntValue12(), floatValue154)
      );
      ClickGuiRenderUtils.invoke4(
         renderManager10,
         metrics14,
         FontRegistry.fontObject,
         f + (h - floatValue168) * 0.5F,
         g + metrics14.measure(57.0F),
         metrics14.measure(11.0F),
         8.5F,
         text32,
         flag15 ? ColorScheme.compute7(colorScheme9.getIntValue15(), colorScheme9.getIntValue14(), 0.65F) : colorScheme9.getIntValue11()
      );
   }

   private void invoke17(
      RenderManager renderManager11, ClickGuiState clickGuiState24, AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerBounds autoBuyModuleCardHandlerBounds10, Metrics metrics15, ColorScheme colorScheme10
   ) {
      float floatValue169 = this.measure11(autoBuyModuleCardHandlerBounds10, metrics15);
      float floatValue170 = this.measure12(autoBuyModuleCardHandlerBounds10, metrics15);
      float floatValue171 = this.measure13(autoBuyModuleCardHandlerBounds10, metrics15);
      float floatValue172 = this.measure14(metrics15);
      float floatValue173 = clickGuiState24.measure5(
         AnimationKeyRegistry.resolve51("catalog:search"), clickGuiState24.getTextSetting() == this.catalogSearch ? 1.0F : 0.0F, SPRING_SPEC_2
      );
      float floatValue174 = this.catalogSearch.value != null && !this.catalogSearch.value.isBlank() ? 1.0F : 0.0F;
      renderManager11.invoke5(
         floatValue169, floatValue170, floatValue171, floatValue172, metrics15.measure(8.0F), ColorScheme.compute7(colorScheme10.getIntValue3(), colorScheme10.getIntValue5(), floatValue173)
      );
      renderManager11.invoke28(
         floatValue169,
         floatValue170,
         floatValue171,
         floatValue172,
         metrics15.measure(8.0F),
         ColorScheme.compute7(colorScheme10.getIntValue5(), ColorScheme.compute6(colorScheme10.getIntValue14(), 105), Math.max(floatValue173, floatValue174 * 0.35F)),
         Math.max(0.75F, metrics15.measure(0.55F))
      );
      if (floatValue173 > 0.01F) {
         renderManager11.invoke41(
            floatValue169,
            floatValue170,
            floatValue171,
            floatValue172,
            metrics15.measure(8.0F),
            metrics15.measure(10.0F) * floatValue173,
            metrics15.measure(1.8F),
            ColorScheme.compute6(colorScheme10.getIntValue14(), Math.round(14.0F * floatValue173))
         );
      }

      String text33 = this.catalogSearch.value == null ? "" : this.catalogSearch.value;
      String text34 = text33.isEmpty() ? "Поиск предметов" : text33;
      if (clickGuiState24.getTextSetting() == this.catalogSearch && System.currentTimeMillis() % 1000L > 500L) {
         text34 = text34 + "|";
      }

      ClickGuiRenderUtils.invoke4(
         renderManager11,
         metrics15,
         FontRegistry.fontObject,
         floatValue169 + metrics15.measure(12.0F),
         floatValue170,
         floatValue172,
         10.5F,
         ClickGuiRenderUtils.resolve3(FontRegistry.fontObject, text34, 10.5F, floatValue171 - metrics15.measure(44.0F)),
         text33.isEmpty() ? colorScheme10.getIntValue11() : colorScheme10.getIntValue12()
      );
      if (!text33.isEmpty()) {
         ClickGuiRenderUtils.invoke4(
            renderManager11,
            metrics15,
            FontRegistry.fontObject5,
            floatValue169 + floatValue171 - metrics15.measure(25.0F),
            floatValue170,
            floatValue172,
            10.0F,
            "l",
            ColorScheme.compute6(colorScheme10.getIntValue14(), 170)
         );
      }
   }

   private void invoke18(
      RenderManager renderManager12, DrawContext drawContext, ClickGuiState clickGuiState25, AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerBounds autoBuyModuleCardHandlerBounds11, ThemeContext themeContext9
   ) {
      Metrics metrics16 = themeContext9.getMetrics();
      ColorScheme colorScheme11 = themeContext9.getColorScheme();
      float floatValue175 = clickGuiState25.measure7(AnimationKeyRegistry.resolve45());
      List items4 = this.resolve17();
      float floatValue176 = this.measure5(autoBuyModuleCardHandlerBounds11, metrics16, clickGuiState25);
      float floatValue177 = this.measure23(this.typeUtils2, floatValue176);
      this.floatValue2 = floatValue177 - this.floatValue6;
      this.floatValue6 = floatValue177;
      this.autoBuyModuleCardHandlerScrollLayout2 = this.resolve5(
         autoBuyModuleCardHandlerBounds11.rulesScrollbarX(), autoBuyModuleCardHandlerBounds11.rulesViewportY(), autoBuyModuleCardHandlerBounds11.scrollbarW(), autoBuyModuleCardHandlerBounds11.rulesViewportH(), floatValue176, floatValue177, metrics16
      );
      renderManager12.invoke5(
         autoBuyModuleCardHandlerBounds11.rightX(), autoBuyModuleCardHandlerBounds11.panelY(), autoBuyModuleCardHandlerBounds11.rightW(), autoBuyModuleCardHandlerBounds11.panelH(), metrics16.measure(8.0F), colorScheme11.getIntValue4()
      );
      renderManager12.invoke28(
         autoBuyModuleCardHandlerBounds11.rightX(), autoBuyModuleCardHandlerBounds11.panelY(), autoBuyModuleCardHandlerBounds11.rightW(), autoBuyModuleCardHandlerBounds11.panelH(), metrics16.measure(8.0F), colorScheme11.getIntValue8(), 0.75F
      );
      this.invoke34(renderManager12, autoBuyModuleCardHandlerBounds11.rightX(), autoBuyModuleCardHandlerBounds11.panelY(), autoBuyModuleCardHandlerBounds11.rightW(), autoBuyModuleCardHandlerBounds11.panelH(), metrics16, colorScheme11, 1200);
      ClickGuiRenderUtils.invoke4(
         renderManager12,
         metrics16,
         FontRegistry.fontObject4,
         autoBuyModuleCardHandlerBounds11.rightX() + metrics16.measure(12.0F),
         autoBuyModuleCardHandlerBounds11.panelY() + metrics16.measure(11.0F),
         metrics16.measure(14.0F),
         12.0F,
         "Настроенные предметы",
         colorScheme11.getIntValue13()
      );
      ClickGuiRenderUtils.invoke4(
         renderManager12,
         metrics16,
         FontRegistry.fontObject,
         autoBuyModuleCardHandlerBounds11.rightX() + metrics16.measure(12.0F),
         autoBuyModuleCardHandlerBounds11.panelY() + metrics16.measure(28.0F),
         metrics16.measure(12.0F),
         10.0F,
         "Цена, статус, настройки и удаление",
         colorScheme11.getIntValue12()
      );
      this.invoke20(renderManager12, clickGuiState25, autoBuyModuleCardHandlerBounds11, metrics16, colorScheme11);
      renderManager12.invoke20();
      renderManager12.invoke24(
         autoBuyModuleCardHandlerBounds11.rulesViewportX(),
         autoBuyModuleCardHandlerBounds11.rulesViewportY(),
         autoBuyModuleCardHandlerBounds11.rulesViewportW(),
         autoBuyModuleCardHandlerBounds11.rulesViewportH(),
         metrics16.measure(6.0F),
         metrics16.measure(6.0F),
         metrics16.measure(6.0F),
         metrics16.measure(6.0F)
      );
      boolean flag18 = false ;

      try {
         flag18 = true;
         if (items4.isEmpty()) {
            this.invoke19(renderManager12, autoBuyModuleCardHandlerBounds11, metrics16, colorScheme11);
            flag18 = false;
         } else {
            float floatValue178 = metrics16.measure(6.0F);
            float floatValue179 = autoBuyModuleCardHandlerBounds11.rulesViewportX() + floatValue178;
            float floatValue180 = autoBuyModuleCardHandlerBounds11.rulesViewportW() - floatValue178 * 2.0F;
            float floatValue181 = autoBuyModuleCardHandlerBounds11.rulesViewportY() + floatValue177;

            for (int intValue35 = 0; intValue35 < items4.size(); intValue35++) {
               String text35 = (String)items4.get(intValue35);
               float floatValue182 = this.measure19(clickGuiState25, text35);
               float floatValue183 = this.measure16(text35, metrics16, floatValue182);
               if (!(floatValue181 > autoBuyModuleCardHandlerBounds11.rulesViewportY() + autoBuyModuleCardHandlerBounds11.rulesViewportH()) && !(floatValue181 + floatValue183 < autoBuyModuleCardHandlerBounds11.rulesViewportY())) {
                  float floatValue184 = clickGuiState25.measure7(AnimationKeyRegistry.resolve46(text35));
                  if (floatValue184 <= 0.01F) {
                     floatValue181 += floatValue183 + this.measure21(metrics16);
                  } else {
                     float floatValue185 = (1.0F - floatValue184) * metrics16.measure(12.0F);
                     renderManager12.invoke65(floatValue184);

                     try {
                        this.invoke21(
                           renderManager12,
                           drawContext,
                           clickGuiState25,
                           text35,
                           floatValue179,
                           floatValue181 + floatValue185,
                           floatValue180,
                           this.measure15(metrics16),
                           metrics16,
                           colorScheme11,
                           Math.min(floatValue184, floatValue175),
                           autoBuyModuleCardHandlerBounds11.rulesViewportX(),
                           autoBuyModuleCardHandlerBounds11.rulesViewportY(),
                           autoBuyModuleCardHandlerBounds11.rulesViewportW(),
                           autoBuyModuleCardHandlerBounds11.rulesViewportH()
                        );
                        if (floatValue182 > 0.01F && this.check8(text35)) {
                           float floatValue186 = this.measure17(text35, metrics16);
                           float floatValue187 = floatValue181 + this.measure15(metrics16) + metrics16.measure(6.0F) * floatValue182 + floatValue185;
                           float floatValue188 = Math.max(metrics16.measure(1.0F), floatValue186 * floatValue182);
                           renderManager12.invoke20();
                           renderManager12.invoke24(
                              floatValue179, floatValue187, floatValue180, floatValue188, metrics16.measure(12.0F), metrics16.measure(12.0F), metrics16.measure(12.0F), metrics16.measure(12.0F)
                           );
                           renderManager12.invoke65(floatValue182);
                           boolean flag19 = false ;

                           try {
                              flag19 = true;
                              this.invoke27(
                                 renderManager12,
                                 drawContext,
                                 clickGuiState25,
                                 text35,
                                 floatValue179,
                                 floatValue187 - metrics16.measure(7.0F) * (1.0F - floatValue182),
                                 floatValue180,
                                 floatValue186,
                                 metrics16,
                                 colorScheme11,
                                 Math.min(floatValue184, floatValue175) * floatValue182,
                                 autoBuyModuleCardHandlerBounds11.rulesViewportX(),
                                 autoBuyModuleCardHandlerBounds11.rulesViewportY(),
                                 autoBuyModuleCardHandlerBounds11.rulesViewportW(),
                                 autoBuyModuleCardHandlerBounds11.rulesViewportH()
                              );
                              flag19 = false;
                           } finally {
                              if (flag19) {
                                 renderManager12.invoke66();
                                 renderManager12.invoke20();
                                 renderManager12.invoke25();
                              }
                           }

                           renderManager12.invoke66();
                           renderManager12.invoke20();
                           renderManager12.invoke25();
                        }
                     } finally {
                        renderManager12.invoke66();
                     }

                     floatValue181 += floatValue183 + this.measure21(metrics16);
                  }
               } else {
                  floatValue181 += floatValue183 + this.measure21(metrics16);
               }
            }

            flag18 = false;
         }
      } finally {
         if (flag18) {
            renderManager12.invoke20();
            renderManager12.invoke25();
         }
      }

      renderManager12.invoke20();
      renderManager12.invoke25();
   }

   private void invoke19(RenderManager renderManager13, AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerBounds autoBuyModuleCardHandlerBounds12, Metrics metrics17, ColorScheme colorScheme12) {
      String text36 = "Нет настроенных предметов";
      String text37 = "Выберите предмет из каталога";
      float floatValue189 = autoBuyModuleCardHandlerBounds12.rulesViewportY() + autoBuyModuleCardHandlerBounds12.rulesViewportH() * 0.5F - metrics17.measure(14.0F);
      float floatValue190 = ClickGuiRenderUtils.measure(FontRegistry.fontObject4, text36, 12.0F);
      float floatValue191 = ClickGuiRenderUtils.measure(FontRegistry.fontObject, text37, 10.0F);
      ClickGuiRenderUtils.invoke4(
         renderManager13,
         metrics17,
         FontRegistry.fontObject4,
         autoBuyModuleCardHandlerBounds12.rulesViewportX() + (autoBuyModuleCardHandlerBounds12.rulesViewportW() - floatValue190) * 0.5F,
         floatValue189,
         metrics17.measure(14.0F),
         12.0F,
         text36,
         colorScheme12.getIntValue12()
      );
      ClickGuiRenderUtils.invoke4(
         renderManager13,
         metrics17,
         FontRegistry.fontObject,
         autoBuyModuleCardHandlerBounds12.rulesViewportX() + (autoBuyModuleCardHandlerBounds12.rulesViewportW() - floatValue191) * 0.5F,
         floatValue189 + metrics17.measure(16.0F),
         metrics17.measure(12.0F),
         10.0F,
         text37,
         colorScheme12.getIntValue11()
      );
   }

   private AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerData2 resolve4(AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerBounds autoBuyModuleCardHandlerBounds13, Metrics metrics18) {
      float floatValue192 = metrics18.measure(24.0F);
      float floatValue193 = autoBuyModuleCardHandlerBounds13.panelY() + metrics18.measure(12.0F);
      float floatValue194 = metrics18.measure(38.0F);
      float floatValue195 = metrics18.measure(38.0F);
      float floatValue196 = metrics18.measure(122.0F);
      float floatValue197 = metrics18.measure(6.0F);
      float floatValue198 = autoBuyModuleCardHandlerBounds13.rightX() + autoBuyModuleCardHandlerBounds13.rightW() - floatValue194 - metrics18.measure(12.0F);
      float floatValue199 = floatValue198 - floatValue197 - floatValue195;
      float floatValue200 = floatValue199 - floatValue197 - floatValue196;
      boolean flag20 = floatValue200 >= autoBuyModuleCardHandlerBounds13.rightX() + metrics18.measure(206.0F);
      return new AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerData2(flag20, floatValue200, floatValue193, floatValue196, floatValue192, floatValue199, floatValue195, floatValue198, floatValue194);
   }

   private void invoke20(
      RenderManager renderManager14, ClickGuiState clickGuiState26, AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerBounds autoBuyModuleCardHandlerBounds14, Metrics metrics19, ColorScheme colorScheme13
   ) {
      AutoBuy autoBuy13 = AutoBuy.instance;
      if (autoBuy13 != null) {
         AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerData2 autoBuyModuleCardHandlerData2 = this.resolve4(autoBuyModuleCardHandlerBounds14, metrics19);
         if (autoBuyModuleCardHandlerData2.visible()) {
            boolean flag21 = autoBuy13.detektZamedleniyaAuka.isEnabled();
            boolean flag22 = autoBuy13.lagStatistikaVChat.isEnabled();
            AuctionClickTracker auctionClickTracker = autoBuy13.getAuctionClickTracker();
            boolean flag23 = flag21 && auctionClickTracker.getIntValue2() > 0;
            boolean flag24 = flag21 && autoBuy13.check6();
            String text38;
            int intValue36;
            if (!flag21) {
               text38 = "Детект: выкл";
               intValue36 = colorScheme13.getIntValue11();
            } else if (!flag23) {
               text38 = "Аук: нет данных";
               intValue36 = colorScheme13.getIntValue12();
            } else if (flag24) {
               text38 = "Замедлен ~" + auctionClickTracker.getTimestamp2() + "мс";
               intValue36 = colorScheme13.compute2();
            } else {
               text38 = "Аук ~" + auctionClickTracker.getTimestamp2() + "мс";
               intValue36 = colorScheme13.compute();
            }

            float floatValue201 = clickGuiState26.measure5(
               AnimationKeyRegistry.resolve50("lag:chip"),
               ClickGuiRenderUtils.check(clickGuiState26, autoBuyModuleCardHandlerData2.chipX(), autoBuyModuleCardHandlerData2.chipY(), autoBuyModuleCardHandlerData2.chipW(), autoBuyModuleCardHandlerData2.chipH()) ? 1.0F : 0.0F,
               SpringSpec.resolve11()
            );
            renderManager14.invoke5(
               autoBuyModuleCardHandlerData2.chipX(),
               autoBuyModuleCardHandlerData2.chipY(),
               autoBuyModuleCardHandlerData2.chipW(),
               autoBuyModuleCardHandlerData2.chipH(),
               metrics19.measure(8.0F),
               ColorScheme.compute7(colorScheme13.getIntValue4(), colorScheme13.getIntValue7(), floatValue201 * 0.6F)
            );
            renderManager14.invoke28(
               autoBuyModuleCardHandlerData2.chipX(),
               autoBuyModuleCardHandlerData2.chipY(),
               autoBuyModuleCardHandlerData2.chipW(),
               autoBuyModuleCardHandlerData2.chipH(),
               metrics19.measure(8.0F),
               ColorScheme.compute7(colorScheme13.getIntValue5(), ColorScheme.compute6(intValue36, 110), flag21 ? 0.65F : floatValue201),
               0.5F
            );
            float floatValue202 = metrics19.measure(6.0F);
            float floatValue203 = autoBuyModuleCardHandlerData2.chipX() + metrics19.measure(9.0F);
            float floatValue204 = autoBuyModuleCardHandlerData2.chipY() + (autoBuyModuleCardHandlerData2.chipH() - floatValue202) * 0.5F;
            renderManager14.invoke5(floatValue203, floatValue204, floatValue202, floatValue202, floatValue202 * 0.5F, flag21 ? ColorScheme.compute6(intValue36, 200) : colorScheme13.getIntValue11());
            String text39 = ClickGuiRenderUtils.resolve4(metrics19, FontRegistry.fontObject4, text38, 9.0F, autoBuyModuleCardHandlerData2.chipW() - metrics19.measure(26.0F));
            ClickGuiRenderUtils.invoke4(
               renderManager14,
               metrics19,
               FontRegistry.fontObject4,
               floatValue203 + floatValue202 + metrics19.measure(6.0F),
               autoBuyModuleCardHandlerData2.chipY(),
               autoBuyModuleCardHandlerData2.chipH(),
               9.0F,
               text39,
               flag21 ? ColorScheme.compute6(intValue36, 190) : colorScheme13.getIntValue12()
            );
            boolean flag25 = autoBuy13.avtoFiksZamedleniya.isEnabled();
            float floatValue205 = clickGuiState26.measure5(
               AnimationKeyRegistry.resolve50("lag:fix"),
               ClickGuiRenderUtils.check(clickGuiState26, autoBuyModuleCardHandlerData2.fixX(), autoBuyModuleCardHandlerData2.chipY(), autoBuyModuleCardHandlerData2.fixW(), autoBuyModuleCardHandlerData2.chipH()) ? 1.0F : 0.0F,
               SpringSpec.resolve11()
            );
            float floatValue206 = clickGuiState26.measure5(AnimationKeyRegistry.resolve50("lag:fixOn"), flag25 ? 1.0F : 0.0F, SPRING_SPEC);
            int intValue37 = ColorScheme.compute7(colorScheme13.getIntValue4(), ColorScheme.compute5(24, 140, 72, 72), floatValue206);
            int intValue38 = ColorScheme.compute7(colorScheme13.getIntValue5(), ColorScheme.compute6(colorScheme13.compute(), 95), floatValue206);
            renderManager14.invoke5(
               autoBuyModuleCardHandlerData2.fixX(),
               autoBuyModuleCardHandlerData2.chipY(),
               autoBuyModuleCardHandlerData2.fixW(),
               autoBuyModuleCardHandlerData2.chipH(),
               metrics19.measure(8.0F),
               ColorScheme.compute7(intValue37, colorScheme13.getIntValue7(), floatValue205 * 0.5F)
            );
            renderManager14.invoke28(
               autoBuyModuleCardHandlerData2.fixX(),
               autoBuyModuleCardHandlerData2.chipY(),
               autoBuyModuleCardHandlerData2.fixW(),
               autoBuyModuleCardHandlerData2.chipH(),
               metrics19.measure(8.0F),
               ColorScheme.compute7(intValue38, colorScheme13.getIntValue7(), floatValue205),
               0.5F
            );
            this.invoke32(
               renderManager14,
               metrics19,
               FontRegistry.fontObject4,
               autoBuyModuleCardHandlerData2.fixX(),
               autoBuyModuleCardHandlerData2.chipY(),
               autoBuyModuleCardHandlerData2.fixW(),
               autoBuyModuleCardHandlerData2.chipH(),
               9.0F,
               "фикс",
               flag25 ? ColorScheme.compute6(colorScheme13.compute(), 180) : colorScheme13.getIntValue12()
            );
            float floatValue207 = clickGuiState26.measure5(
               AnimationKeyRegistry.resolve50("lag:stat"),
               ClickGuiRenderUtils.check(clickGuiState26, autoBuyModuleCardHandlerData2.statX(), autoBuyModuleCardHandlerData2.chipY(), autoBuyModuleCardHandlerData2.statW(), autoBuyModuleCardHandlerData2.chipH()) ? 1.0F : 0.0F,
               SpringSpec.resolve11()
            );
            float floatValue208 = clickGuiState26.measure5(AnimationKeyRegistry.resolve50("lag:statOn"), flag22 ? 1.0F : 0.0F, SPRING_SPEC);
            int intValue39 = ColorScheme.compute7(colorScheme13.getIntValue4(), ColorScheme.compute6(colorScheme13.getIntValue14(), 52), floatValue208);
            int intValue40 = ColorScheme.compute7(colorScheme13.getIntValue5(), ColorScheme.compute6(colorScheme13.getIntValue14(), 110), floatValue208);
            renderManager14.invoke5(
               autoBuyModuleCardHandlerData2.statX(),
               autoBuyModuleCardHandlerData2.chipY(),
               autoBuyModuleCardHandlerData2.statW(),
               autoBuyModuleCardHandlerData2.chipH(),
               metrics19.measure(8.0F),
               ColorScheme.compute7(intValue39, colorScheme13.getIntValue7(), floatValue207 * 0.5F)
            );
            renderManager14.invoke28(
               autoBuyModuleCardHandlerData2.statX(),
               autoBuyModuleCardHandlerData2.chipY(),
               autoBuyModuleCardHandlerData2.statW(),
               autoBuyModuleCardHandlerData2.chipH(),
               metrics19.measure(8.0F),
               ColorScheme.compute7(intValue40, colorScheme13.getIntValue7(), floatValue207),
               0.5F
            );
            this.invoke32(
               renderManager14,
               metrics19,
               FontRegistry.fontObject4,
               autoBuyModuleCardHandlerData2.statX(),
               autoBuyModuleCardHandlerData2.chipY(),
               autoBuyModuleCardHandlerData2.statW(),
               autoBuyModuleCardHandlerData2.chipH(),
               9.0F,
               "стат",
               flag22 ? colorScheme13.getIntValue14() : colorScheme13.getIntValue12()
            );
         }
      }
   }

   private void invoke21(
      RenderManager renderManager15,
      DrawContext drawContext,
      ClickGuiState clickGuiState27,
      String string,
      float f,
      float g,
      float h,
      float i,
      Metrics metrics20,
      ColorScheme colorScheme14,
      float j,
      float k,
      float l,
      float m,
      float n
   ) {
      String text40 = AnimationKeyRegistry.resolve48(string);
      float floatValue209 = clickGuiState27.measure5(text40, ClickGuiRenderUtils.check(clickGuiState27, f, g, h, i) ? 1.0F : 0.0F, SpringSpec.resolve11());
      boolean flag26 = !AutoBuy.VALUES_3.contains(string);
      boolean flag27 = this.check8(string);
      renderManager15.invoke5(
         f, g, h, i, metrics20.measure(12.0F), ColorScheme.compute7(colorScheme14.getIntValue2(), colorScheme14.getIntValue7(), 0.32F + floatValue209 * 0.16F)
      );
      renderManager15.invoke28(
         f,
         g,
         h,
         i,
         metrics20.measure(12.0F),
         ColorScheme.compute7(colorScheme14.getIntValue8(), ColorScheme.compute6(colorScheme14.getIntValue14(), 118), Math.max(floatValue209, 0.22F)),
         2.0F
      );
      AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerDisplayEntry autoBuyModuleCardHandlerDisplayEntry6 = this.resolve10(string);
      float floatValue210 = metrics20.measure(29.0F);
      float floatValue211 = f + metrics20.measure(11.0F);
      float floatValue212 = g + (i - floatValue210) * 0.5F;
      renderManager15.invoke5(floatValue211, floatValue212, floatValue210, floatValue210, metrics20.measure(8.0F), colorScheme14.getIntValue3());
      renderManager15.invoke28(floatValue211, floatValue212, floatValue210, floatValue210, metrics20.measure(8.0F), colorScheme14.getIntValue8(), 0.75F);
      this.invoke53(
         renderManager15,
         drawContext,
         autoBuyModuleCardHandlerDisplayEntry6,
         floatValue211 + metrics20.measure(6.5F),
         floatValue212 + metrics20.measure(6.5F),
         metrics20.measure(16.0F),
         j,
         k,
         l,
         m,
         n
      );
      float floatValue213 = f + h - metrics20.measure(237.0F) - metrics20.measure(8.0F);
      float floatValue214 = Math.max(metrics20.measure(72.0F), floatValue213 - (f + metrics20.measure(50.0F)));
      this.invoke22(
         renderManager15,
         clickGuiState27,
         autoBuyModuleCardHandlerDisplayEntry6.label(),
         string,
         f + metrics20.measure(50.0F),
         g + metrics20.measure(12.0F),
         floatValue214,
         Math.max(metrics20.measure(72.0F), h - metrics20.measure(120.0F)),
         metrics20,
         colorScheme14,
         flag26
      );
      String text41 = ClickGuiRenderUtils.resolve3(FontRegistry.fontObject, flag26 ? "Статус: активен" : "Статус: пауза", 10.0F, floatValue214);
      ClickGuiRenderUtils.invoke4(
         renderManager15,
         metrics20,
         FontRegistry.fontObject,
         f + metrics20.measure(50.0F),
         g + metrics20.measure(32.0F),
         metrics20.measure(12.0F),
         10.0F,
         text41,
         flag26 ? ColorScheme.compute6(colorScheme14.compute(), 176) : colorScheme14.getIntValue12()
      );
      float floatValue215 = g + (i - metrics20.measure(29.0F)) * 0.5F;
      this.invoke23(
         renderManager15,
         clickGuiState27,
         string,
         f + h - metrics20.measure(237.0F),
         floatValue215,
         metrics20.measure(95.0F),
         metrics20.measure(29.0F),
         metrics20,
         colorScheme14
      );
      this.invoke24(
         renderManager15,
         clickGuiState27,
         string,
         f + h - metrics20.measure(134.0F),
         floatValue215,
         metrics20.measure(58.0F),
         metrics20.measure(29.0F),
         metrics20,
         colorScheme14,
         flag26
      );
      this.invoke25(
         renderManager15,
         clickGuiState27,
         string,
         f + h - metrics20.measure(68.0F),
         floatValue215,
         metrics20.measure(29.0F),
         metrics20.measure(29.0F),
         metrics20,
         colorScheme14
      );
      if (flag27) {
         this.invoke26(
            renderManager15, clickGuiState27, string, f + h - metrics20.measure(34.0F), floatValue215, metrics20.measure(29.0F), metrics20, colorScheme14
         );
      }
   }

   private void invoke22(
      RenderManager renderManager16,
      ClickGuiState clickGuiState28,
      String string,
      String string2,
      float f,
      float g,
      float h,
      float i,
      Metrics metrics21,
      ColorScheme colorScheme15,
      boolean bl
   ) {
      String text42 = string == null ? "" : string;
      float floatValue216 = metrics21.measure(14.0F);
      float floatValue217 = Math.max(metrics21.measure(48.0F), h);
      float floatValue218 = Math.max(floatValue217, i);
      float floatValue219 = ClickGuiRenderUtils.measure(FontRegistry.fontObject4, text42, 12.0F);
      boolean flag28 = floatValue219 > floatValue217 + metrics21.measure(1.0F);
      boolean flag29 = flag28
         && ClickGuiRenderUtils.check(
            clickGuiState28,
            f - metrics21.measure(4.0F),
            g - metrics21.measure(4.0F),
            floatValue217 + metrics21.measure(12.0F),
            floatValue216 + metrics21.measure(8.0F)
         );
      float floatValue220 = clickGuiState28.measure5(AnimationKeyRegistry.resolve50("rule:name:" + string2), flag29 ? 1.0F : 0.0F, SpringSpec.resolve11());
      int intValue41 = bl ? colorScheme15.getIntValue13() : colorScheme15.getIntValue12();
      String text43 = ClickGuiRenderUtils.resolve3(FontRegistry.fontObject4, text42, 12.0F, floatValue217);
      if (floatValue220 < 0.985F) {
         renderManager16.invoke65(1.0F - floatValue220);
         boolean flag30 = false ;

         try {
            flag30 = true;
            ClickGuiRenderUtils.invoke4(renderManager16, metrics21, FontRegistry.fontObject4, f, g, floatValue216, 12.0F, text43, intValue41);
            flag30 = false;
         } finally {
            if (flag30) {
               renderManager16.invoke66();
            }
         }

         renderManager16.invoke66();
      }

      if (!(floatValue220 <= 0.01F)) {
         float floatValue221 = metrics21.measure(8.0F);
         float floatValue222 = metrics21.measure(22.0F);
         float floatValue223 = Math.min(floatValue219, floatValue218);
         float floatValue224 = this.measure22(floatValue217 + (floatValue223 - floatValue217) * floatValue220, floatValue217, floatValue223);
         float floatValue225 = f - floatValue221;
         float floatValue226 = g - metrics21.measure(4.0F);
         float floatValue227 = floatValue224 + floatValue221 * 2.0F;
         int intValue42 = ColorScheme.compute7(
            ColorScheme.compute6(colorScheme15.getIntValue2(), 230), ColorScheme.compute6(colorScheme15.getIntValue7(), 235), 0.35F
         );
         int intValue43 = ColorScheme.compute7(colorScheme15.getIntValue8(), ColorScheme.compute6(colorScheme15.getIntValue14(), 132), floatValue220);
         int intValue44 = bl ? ColorScheme.compute7(colorScheme15.getIntValue12(), colorScheme15.getIntValue13(), floatValue220) : colorScheme15.getIntValue12();
         renderManager16.invoke65(floatValue220);

         try {
            renderManager16.invoke41(
               floatValue225,
               floatValue226,
               floatValue227,
               floatValue222,
               metrics21.measure(7.0F),
               metrics21.measure(12.0F) * floatValue220,
               metrics21.measure(1.6F),
               ColorScheme.compute6(colorScheme15.getIntValue14(), Math.round(16.0F * floatValue220))
            );
            renderManager16.invoke5(floatValue225, floatValue226, floatValue227, floatValue222, metrics21.measure(7.0F), intValue42);
            renderManager16.invoke28(floatValue225, floatValue226, floatValue227, floatValue222, metrics21.measure(7.0F), intValue43, 0.75F);
            ClickGuiRenderUtils.invoke6(
               renderManager16,
               floatValue225,
               floatValue226,
               floatValue227,
               floatValue222,
               metrics21.measure(7.0F),
               () -> ClickGuiRenderUtils.invoke4(
                  renderManager16,
                  metrics21,
                  FontRegistry.fontObject4,
                  f,
                  g,
                  floatValue216,
                  12.0F,
                  ClickGuiRenderUtils.resolve3(FontRegistry.fontObject4, text42, 12.0F, floatValue227 - floatValue221 * 2.0F),
                  intValue44
               )
            );
         } finally {
            renderManager16.invoke66();
         }
      }
   }

   private void invoke23(
      RenderManager renderManager17,
      ClickGuiState clickGuiState29,
      String string,
      float f,
      float g,
      float h,
      float i,
      Metrics metrics22,
      ColorScheme colorScheme16
   ) {
      TextSetting textSetting7 = this.resolve18(string);
      if (clickGuiState29.getTextSetting() != textSetting7) {
         textSetting7.value = this.resolve19(string);
      }

      float floatValue228 = clickGuiState29.measure5(AnimationKeyRegistry.resolve51(string), clickGuiState29.getTextSetting() == textSetting7 ? 1.0F : 0.0F, SPRING_SPEC_2);
      renderManager17.invoke5(
         f, g, h, i, metrics22.measure(8.0F), ColorScheme.compute7(colorScheme16.getIntValue3(), colorScheme16.getIntValue5(), floatValue228)
      );
      renderManager17.invoke28(
         f,
         g,
         h,
         i,
         metrics22.measure(8.0F),
         ColorScheme.compute7(colorScheme16.getIntValue5(), ColorScheme.compute6(colorScheme16.getIntValue14(), 95), floatValue228),
         0.5F
      );
      String text44 = textSetting7.value.isEmpty() ? "Макс. цена" : this.resolve22(this.compute(textSetting7.value));
      if (clickGuiState29.getTextSetting() == textSetting7 && System.currentTimeMillis() % 1000L > 500L) {
         text44 = text44 + "|";
      }

      ClickGuiRenderUtils.invoke4(
         renderManager17,
         metrics22,
         FontRegistry.fontObject,
         f + metrics22.measure(8.0F),
         g,
         i,
         10.0F,
         ClickGuiRenderUtils.resolve3(FontRegistry.fontObject, text44, 10.0F, h - metrics22.measure(16.0F)),
         textSetting7.value.isEmpty() ? colorScheme16.getIntValue11() : colorScheme16.getIntValue12()
      );
   }

   private void invoke24(
      RenderManager renderManager18,
      ClickGuiState clickGuiState30,
      String string,
      float f,
      float g,
      float h,
      float i,
      Metrics metrics23,
      ColorScheme colorScheme17,
      boolean bl
   ) {
      String text45 = AnimationKeyRegistry.resolve50(string);
      float floatValue229 = clickGuiState30.measure5(text45, ClickGuiRenderUtils.check(clickGuiState30, f, g, h, i) ? 1.0F : 0.0F, SpringSpec.resolve11());
      float floatValue230 = clickGuiState30.measure5(AnimationKeyRegistry.resolve19(this.resolve18(string)), bl ? 1.0F : 0.0F, SPRING_SPEC);
      renderManager18.invoke62(ClickGuiRenderUtils.measure8(floatValue229, clickGuiState30.measure8(text45), 0.02F, 0.006F), f + h * 0.5F, g + i * 0.5F);

      try {
         renderManager18.invoke5(
            f, g, h, i, metrics23.measure(8.0F), ColorScheme.compute7(colorScheme17.getIntValue3(), colorScheme17.getIntValue5(), floatValue229)
         );
         if (bl) {
            renderManager18.invoke34(
               f + metrics23.measure(4.0F),
               g + metrics23.measure(4.0F),
               h - metrics23.measure(8.0F),
               i - metrics23.measure(8.0F),
               metrics23.measure(6.0F),
               ColorScheme.compute6(colorScheme17.compute(), 20),
               ColorScheme.compute6(colorScheme17.getIntValue14(), 24)
            );
         }

         float floatValue231 = metrics23.measure(9.0F);
         float floatValue232 = f + metrics23.measure(7.0F) + (h - metrics23.measure(23.0F)) * floatValue230;
         renderManager18.invoke5(
            floatValue232,
            g + (i - floatValue231) * 0.5F,
            floatValue231,
            floatValue231,
            floatValue231 * 0.5F,
            bl ? ColorScheme.compute6(colorScheme17.compute(), 180) : colorScheme17.getIntValue11()
         );
         String text46 = bl ? "Вкл" : "Выкл";
         float floatValue233 = ClickGuiRenderUtils.measure(FontRegistry.fontObject, text46, 9.0F);
         ClickGuiRenderUtils.invoke4(
            renderManager18,
            metrics23,
            FontRegistry.fontObject,
            f + (h - floatValue233) * 0.5F,
            g,
            i,
            9.0F,
            text46,
            bl ? ColorScheme.compute6(colorScheme17.compute(), 122) : colorScheme17.getIntValue11()
         );
      } finally {
         renderManager18.invoke64();
      }
   }

   private void invoke25(
      RenderManager renderManager19,
      ClickGuiState clickGuiState31,
      String string,
      float f,
      float g,
      float h,
      float i,
      Metrics metrics24,
      ColorScheme colorScheme18
   ) {
      String text47 = AnimationKeyRegistry.resolve49(string);
      float floatValue234 = clickGuiState31.measure5(text47, ClickGuiRenderUtils.check(clickGuiState31, f, g, h, i) ? 1.0F : 0.0F, SpringSpec.resolve11());
      int intValue45 = ColorScheme.compute6(colorScheme18.compute2(), Math.round(22.0F + 46.0F * floatValue234));
      renderManager19.invoke62(ClickGuiRenderUtils.measure8(floatValue234, clickGuiState31.measure8(text47), 0.03F, 0.008F), f + h * 0.5F, g + i * 0.5F);
      boolean flag31 = false ;

      try {
         flag31 = true;
         renderManager19.invoke5(f, g, h, i, metrics24.measure(8.0F), ColorScheme.compute7(colorScheme18.getIntValue3(), intValue45, floatValue234));
         renderManager19.invoke28(
            f,
            g,
            h,
            i,
            metrics24.measure(8.0F),
            ColorScheme.compute7(colorScheme18.getIntValue5(), ColorScheme.compute6(colorScheme18.compute2(), 120), floatValue234),
            0.5F
         );
         int intValue46 = ColorScheme.compute7(colorScheme18.getIntValue11(), colorScheme18.compute2(), floatValue234);
         float floatValue235 = f + h * 0.5F;
         float floatValue236 = g + i * 0.5F;
         renderManager19.invoke5(
            floatValue235 - metrics24.measure(4.4F),
            floatValue236 - metrics24.measure(3.2F),
            metrics24.measure(8.8F),
            metrics24.measure(1.3F),
            metrics24.measure(1.0F),
            intValue46
         );
         renderManager19.invoke5(
            floatValue235 - metrics24.measure(3.4F),
            floatValue236 - metrics24.measure(1.2F),
            metrics24.measure(6.8F),
            metrics24.measure(6.7F),
            metrics24.measure(1.5F),
            ColorScheme.compute6(intValue46, 160)
         );
         renderManager19.invoke5(
            floatValue235 - metrics24.measure(1.8F),
            floatValue236 - metrics24.measure(5.1F),
            metrics24.measure(3.6F),
            metrics24.measure(1.4F),
            metrics24.measure(1.0F),
            intValue46
         );
         flag31 = false;
      } finally {
         if (flag31) {
            renderManager19.invoke64();
         }
      }

      renderManager19.invoke64();
   }

   private void invoke26(
      RenderManager renderManager20, ClickGuiState clickGuiState32, String string, float f, float g, float h, Metrics metrics25, ColorScheme colorScheme19
   ) {
      boolean flag32 = string.equals(this.text3);
      String text48 = AnimationKeyRegistry.resolve50("settings:" + string);
      float floatValue237 = clickGuiState32.measure5(text48, ClickGuiRenderUtils.check(clickGuiState32, f, g, h, h) ? 1.0F : 0.0F, SpringSpec.resolve11());
      float floatValue238 = clickGuiState32.measure5("ab_settings_on:" + string, flag32 ? 1.0F : 0.0F, SPRING_SPEC_2);
      renderManager20.invoke62(ClickGuiRenderUtils.measure8(floatValue237, clickGuiState32.measure8(text48), 0.03F, 0.008F), f + h * 0.5F, g + h * 0.5F);

      try {
         renderManager20.invoke5(
            f,
            g,
            h,
            h,
            metrics25.measure(8.0F),
            ColorScheme.compute7(colorScheme19.getIntValue3(), ColorScheme.compute6(colorScheme19.getIntValue14(), 34), Math.max(floatValue237 * 0.6F, floatValue238))
         );
         renderManager20.invoke28(
            f,
            g,
            h,
            h,
            metrics25.measure(8.0F),
            ColorScheme.compute7(colorScheme19.getIntValue5(), ColorScheme.compute6(colorScheme19.getIntValue14(), 118), Math.max(floatValue237, floatValue238)),
            0.5F
         );
         int intValue47 = ColorScheme.compute7(colorScheme19.getIntValue11(), colorScheme19.getIntValue14(), Math.max(floatValue237, floatValue238));
         float floatValue239 = metrics25.measure(2.4F);
         float floatValue240 = metrics25.measure(4.2F);
         float floatValue241 = f + h * 0.5F - floatValue240 * 0.5F - floatValue239;
         float floatValue242 = g + h * 0.5F - floatValue240 - floatValue239 * 0.5F;

         for (int intValue48 = 0; intValue48 < 3; intValue48++) {
            for (int intValue49 = 0; intValue49 < 2; intValue49++) {
               renderManager20.invoke5(floatValue241 + intValue49 * floatValue240, floatValue242 + intValue48 * floatValue240, floatValue239, floatValue239, floatValue239 * 0.5F, intValue47);
            }
         }
      } finally {
         renderManager20.invoke64();
      }
   }

   private void invoke27(
      RenderManager renderManager21,
      DrawContext drawContext,
      ClickGuiState clickGuiState33,
      String string,
      float f,
      float g,
      float h,
      float i,
      Metrics metrics26,
      ColorScheme colorScheme20,
      float j,
      float k,
      float l,
      float m,
      float n
   ) {
      float floatValue243 = clickGuiState33.measure5(
         AnimationKeyRegistry.resolve50("settingsPanel:" + string),
         ClickGuiRenderUtils.check(clickGuiState33, f, g, h, i) ? 1.0F : 0.0F,
         SpringSpec.resolve11()
      );
      renderManager21.invoke5(
         f, g, h, i, metrics26.measure(12.0F), ColorScheme.compute7(colorScheme20.getIntValue2(), colorScheme20.getIntValue7(), 0.26F + floatValue243 * 0.08F)
      );
      renderManager21.invoke28(
         f,
         g,
         h,
         i,
         metrics26.measure(12.0F),
         ColorScheme.compute7(colorScheme20.getIntValue8(), ColorScheme.compute6(colorScheme20.getIntValue14(), 108), 0.46F + floatValue243 * 0.25F),
         1.0F
      );
      AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerDisplayEntry autoBuyModuleCardHandlerDisplayEntry7 = this.resolve10(string);
      float floatValue244 = metrics26.measure(34.0F);
      float floatValue245 = f + metrics26.measure(13.0F);
      float floatValue246 = g + metrics26.measure(12.0F);
      renderManager21.invoke5(floatValue245, floatValue246, floatValue244, floatValue244, metrics26.measure(9.0F), colorScheme20.getIntValue3());
      renderManager21.invoke28(floatValue245, floatValue246, floatValue244, floatValue244, metrics26.measure(9.0F), colorScheme20.getIntValue8(), 0.75F);
      this.invoke53(
         renderManager21,
         drawContext,
         autoBuyModuleCardHandlerDisplayEntry7,
         floatValue245 + metrics26.measure(7.0F),
         floatValue246 + metrics26.measure(7.0F),
         metrics26.measure(20.0F),
         j,
         k,
         l,
         m,
         n
      );
      ClickGuiRenderUtils.invoke4(
         renderManager21,
         metrics26,
         FontRegistry.fontObject4,
         floatValue245 + floatValue244 + metrics26.measure(10.0F),
         g + metrics26.measure(12.0F),
         metrics26.measure(15.0F),
         12.5F,
         "Настройки предмета",
         colorScheme20.getIntValue13()
      );
      ClickGuiRenderUtils.invoke4(
         renderManager21,
         metrics26,
         FontRegistry.fontObject,
         floatValue245 + floatValue244 + metrics26.measure(10.0F),
         g + metrics26.measure(31.0F),
         metrics26.measure(12.0F),
         10.0F,
         ClickGuiRenderUtils.resolve3(FontRegistry.fontObject, autoBuyModuleCardHandlerDisplayEntry7.label(), 10.0F, h - floatValue244 - metrics26.measure(86.0F)),
         colorScheme20.getIntValue12()
      );
      String text49 = AutoBuy.compute22(string) + "-" + AutoBuy.compute23(string) + "%";
      float floatValue247 = Math.max(metrics26.measure(48.0F), ClickGuiRenderUtils.measure(FontRegistry.fontObject4, text49, 10.0F) + metrics26.measure(14.0F));
      renderManager21.invoke5(
         f + h - metrics26.measure(13.0F) - floatValue247,
         g + metrics26.measure(14.0F),
         floatValue247,
         metrics26.measure(22.0F),
         metrics26.measure(7.0F),
         colorScheme20.getIntValue4()
      );
      this.invoke32(
         renderManager21,
         metrics26,
         FontRegistry.fontObject4,
         f + h - metrics26.measure(13.0F) - floatValue247,
         g + metrics26.measure(14.0F),
         floatValue247,
         metrics26.measure(22.0F),
         10.0F,
         text49,
         colorScheme20.getIntValue14()
      );
      this.invoke28(
         renderManager21,
         clickGuiState33,
         string,
         f + metrics26.measure(16.0F),
         g + metrics26.measure(58.0F),
         h - metrics26.measure(32.0F),
         metrics26.measure(36.0F),
         metrics26,
         colorScheme20
      );
      AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerData autoBuyModuleCardHandlerData = this.resolve11(string);
      if (!autoBuyModuleCardHandlerData.enchantments().isEmpty()) {
         ClickGuiRenderUtils.invoke4(
            renderManager21,
            metrics26,
            FontRegistry.fontObject,
            f + metrics26.measure(16.0F),
            g + metrics26.measure(108.0F),
            metrics26.measure(12.0F),
            9.5F,
            "Зачарования",
            colorScheme20.getIntValue12()
         );
         this.invoke31(
            renderManager21,
            clickGuiState33,
            string,
            autoBuyModuleCardHandlerData.enchantments(),
            f + metrics26.measure(16.0F),
            g + metrics26.measure(128.0F),
            h - metrics26.measure(32.0F),
            metrics26,
            colorScheme20
         );
      }
   }

   private void invoke28(
      RenderManager renderManager22,
      ClickGuiState clickGuiState34,
      String string,
      float f,
      float g,
      float h,
      float i,
      Metrics metrics27,
      ColorScheme colorScheme21
   ) {
      int intValue50 = AutoBuy.compute22(string);
      int intValue51 = AutoBuy.compute23(string);
      float floatValue248 = g + metrics27.measure(22.0F);
      float floatValue249 = metrics27.measure(5.0F);
      float floatValue250 = f + h * intValue50 / 100.0F;
      float floatValue251 = f + h * intValue51 / 100.0F;
      float floatValue252 = clickGuiState34.measure5(
         AnimationKeyRegistry.resolve50("durSlider:" + string),
         ClickGuiRenderUtils.check(clickGuiState34, f, g, h, i) ? 1.0F : 0.0F,
         SpringSpec.resolve11()
      );
      ClickGuiRenderUtils.invoke4(
         renderManager22, metrics27, FontRegistry.fontObject, f, g, metrics27.measure(12.0F), 10.0F, "Диапазон прочности", colorScheme21.getIntValue12()
      );
      renderManager22.invoke5(f, floatValue248, h, floatValue249, metrics27.measure(3.0F), colorScheme21.getIntValue7());
      renderManager22.invoke34(
         floatValue250,
         floatValue248,
         Math.max(metrics27.measure(3.0F), floatValue251 - floatValue250),
         floatValue249,
         metrics27.measure(3.0F),
         ColorScheme.compute6(colorScheme21.getIntValue15(), 118),
         ColorScheme.compute6(colorScheme21.getIntValue14(), 150)
      );
      this.invoke29(renderManager22, floatValue250, floatValue248 + floatValue249 * 0.5F, intValue50 == 0 ? colorScheme21.getIntValue12() : colorScheme21.getIntValue14(), floatValue252, metrics27);
      this.invoke29(
         renderManager22, floatValue251, floatValue248 + floatValue249 * 0.5F, intValue51 == 100 ? colorScheme21.getIntValue12() : colorScheme21.getIntValue14(), floatValue252, metrics27
      );
      ClickGuiRenderUtils.invoke4(
         renderManager22,
         metrics27,
         FontRegistry.fontObject,
         f,
         g + metrics27.measure(30.0F),
         metrics27.measure(10.0F),
         8.5F,
         "Мин " + intValue50 + "%",
         colorScheme21.getIntValue11()
      );
      String text50 = "Макс " + intValue51 + "%";
      float floatValue253 = ClickGuiRenderUtils.measure(FontRegistry.fontObject, text50, 8.5F);
      ClickGuiRenderUtils.invoke4(
         renderManager22,
         metrics27,
         FontRegistry.fontObject,
         f + h - floatValue253,
         g + metrics27.measure(30.0F),
         metrics27.measure(10.0F),
         8.5F,
         text50,
         colorScheme21.getIntValue11()
      );
   }

   private void invoke29(RenderManager renderManager23, float f, float g, int i, float h, Metrics metrics28) {
      float floatValue254 = metrics28.measure(10.0F + h * 1.5F);
      renderManager23.invoke5(f - floatValue254 * 0.5F, g - floatValue254 * 0.5F, floatValue254, floatValue254, floatValue254 * 0.5F, ColorScheme.compute6(i, 230));
      renderManager23.invoke28(f - floatValue254 * 0.5F, g - floatValue254 * 0.5F, floatValue254, floatValue254, floatValue254 * 0.5F, ColorScheme.compute5(255, 255, 255, 44), 0.5F);
   }

   private void invoke30(
      RenderManager renderManager24,
      ClickGuiState clickGuiState35,
      String string,
      String string2,
      String string3,
      boolean bl,
      boolean bl2,
      float f,
      float g,
      float h,
      float i,
      Metrics metrics29,
      ColorScheme colorScheme22
   ) {
      float floatValue255 = clickGuiState35.measure5(
         AnimationKeyRegistry.resolve50("check:" + string + ":" + string2),
         bl2 && ClickGuiRenderUtils.check(clickGuiState35, f, g, h, i) ? 1.0F : 0.0F,
         SpringSpec.resolve11()
      );
      int intValue52 = bl2 ? ColorScheme.compute7(colorScheme22.getIntValue12(), colorScheme22.getIntValue13(), floatValue255) : colorScheme22.getIntValue11();
      float floatValue256 = metrics29.measure(13.0F);
      renderManager24.invoke5(
         f,
         g + (i - floatValue256) * 0.5F,
         floatValue256,
         floatValue256,
         metrics29.measure(4.0F),
         ColorScheme.compute7(colorScheme22.getIntValue3(), ColorScheme.compute6(colorScheme22.getIntValue14(), 38), bl && bl2 ? 1.0F : floatValue255 * 0.35F)
      );
      renderManager24.invoke28(
         f,
         g + (i - floatValue256) * 0.5F,
         floatValue256,
         floatValue256,
         metrics29.measure(4.0F),
         ColorScheme.compute7(colorScheme22.getIntValue5(), ColorScheme.compute6(colorScheme22.getIntValue14(), 110), bl && bl2 ? 0.9F : floatValue255),
         0.5F
      );
      if (bl && bl2) {
         renderManager24.invoke5(
            f + metrics29.measure(3.2F),
            g + (i - floatValue256) * 0.5F + metrics29.measure(6.2F),
            metrics29.measure(2.8F),
            metrics29.measure(1.4F),
            metrics29.measure(1.0F),
            colorScheme22.getIntValue14()
         );
         renderManager24.invoke5(
            f + metrics29.measure(5.4F),
            g + (i - floatValue256) * 0.5F + metrics29.measure(4.2F),
            metrics29.measure(5.4F),
            metrics29.measure(1.4F),
            metrics29.measure(1.0F),
            colorScheme22.getIntValue14()
         );
      }

      ClickGuiRenderUtils.invoke4(
         renderManager24,
         metrics29,
         FontRegistry.fontObject,
         f + floatValue256 + metrics29.measure(7.0F),
         g,
         i,
         9.5F,
         ClickGuiRenderUtils.resolve3(FontRegistry.fontObject, string3, 9.5F, h - floatValue256 - metrics29.measure(10.0F)),
         intValue52
      );
   }

   private void invoke31(
      RenderManager renderManager25,
      ClickGuiState clickGuiState36,
      String string,
      List<AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerDisplayEntry2> list,
      float f,
      float g,
      float h,
      Metrics metrics30,
      ColorScheme colorScheme23
   ) {
      if (list.isEmpty()) {
         ClickGuiRenderUtils.invoke4(
            renderManager25,
            metrics30,
            FontRegistry.fontObject,
            f,
            g,
            metrics30.measure(14.0F),
            9.5F,
            "Нет заданных зачарований",
            colorScheme23.getIntValue11()
         );
      } else {
         float floatValue257 = metrics30.measure(8.0F);
         float floatValue258 = metrics30.measure(22.0F);
         float floatValue259 = (h - floatValue257) * 0.5F;

         for (int intValue53 = 0; intValue53 < list.size(); intValue53++) {
            AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerDisplayEntry2 autoBuyModuleCardHandlerDisplayEntry22 = (AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerDisplayEntry2)list.get(intValue53);
            float floatValue260 = f + intValue53 % 2 * (floatValue259 + floatValue257);
            float floatValue261 = g + intValue53 / 2 * (floatValue258 + metrics30.measure(4.0F));
            boolean flag33 = AutoBuy.check34(string, autoBuyModuleCardHandlerDisplayEntry22.key());
            this.invoke30(
               renderManager25, clickGuiState36, string, autoBuyModuleCardHandlerDisplayEntry22.key(), autoBuyModuleCardHandlerDisplayEntry22.label(), flag33, true, floatValue260, floatValue261, floatValue259, floatValue258, metrics30, colorScheme23
            );
         }
      }
   }

   private void invoke32(
      RenderManager renderManager26, Metrics metrics31, FontObject fontObject, float f, float g, float h, float i, float j, String string, int k
   ) {
      float floatValue262 = ClickGuiRenderUtils.measure(fontObject, string, j);
      ClickGuiRenderUtils.invoke4(renderManager26, metrics31, fontObject, f + (h - floatValue262) * 0.5F, g, i, j, string, k);
   }

   private void invoke33(RenderManager renderManager27, Metrics metrics32, ColorScheme colorScheme24, float f, float g, float h, float i, int j) {
      if (!(h <= 0.01F)) {
         int intValue54 = colorScheme24.compute();
         float floatValue263 = (1.0F - h) * metrics32.measure(6.0F);
         renderManager27.invoke65(h);

         try {
            float floatValue264 = f + floatValue263;
            float floatValue265 = g + metrics32.measure(8.0F);
            float floatValue266 = metrics32.measure(5.0F) + metrics32.measure(2.2F) * i;
            if (i > 0.01F) {
               renderManager27.invoke41(
                  floatValue264 - floatValue266 * 0.5F,
                  floatValue265 - floatValue266 * 0.5F,
                  floatValue266,
                  floatValue266,
                  floatValue266 * 0.5F,
                  metrics32.measure(9.0F) * i,
                  metrics32.measure(1.5F),
                  ColorScheme.compute6(intValue54, Math.round(130.0F * i))
               );
            }

            renderManager27.invoke5(
               floatValue264 - floatValue266 * 0.5F, floatValue265 - floatValue266 * 0.5F, floatValue266, floatValue266, floatValue266 * 0.5F, ColorScheme.compute6(intValue54, Math.round(150.0F + 105.0F * i))
            );
            String text51 = j <= 0 ? "Live · мониторинг" : "Live · покупок: " + j;
            ClickGuiRenderUtils.invoke4(
               renderManager27,
               metrics32,
               FontRegistry.fontObject,
               floatValue264 + metrics32.measure(9.0F),
               g,
               metrics32.measure(16.0F),
               9.5F,
               text51,
               ColorScheme.compute6(intValue54, Math.round(150.0F + 80.0F * i))
            );
         } finally {
            renderManager27.invoke66();
         }
      }
   }

   private void invoke34(RenderManager renderManager28, float f, float g, float h, float i, Metrics metrics33, ColorScheme colorScheme25, int j) {
      float floatValue267 = (float)((System.currentTimeMillis() + j) % 7200L) / 7200.0F;
      float floatValue268 = floatValue267 < 0.5F ? floatValue267 * 2.0F : 2.0F - floatValue267 * 2.0F;
      float floatValue269 = Math.max(metrics33.measure(44.0F), h * 0.24F);
      float floatValue270 = f + metrics33.measure(8.0F) + (h - metrics33.measure(16.0F) - floatValue269) * floatValue268;
      int intValue55 = ColorScheme.compute6(colorScheme25.getIntValue14(), 30);
      renderManager28.invoke34(
         f + metrics33.measure(1.0F),
         g + metrics33.measure(1.0F),
         h - metrics33.measure(2.0F),
         metrics33.measure(1.0F),
         metrics33.measure(1.0F),
         ColorScheme.compute6(colorScheme25.getIntValue15(), 10),
         ColorScheme.compute6(colorScheme25.getIntValue14(), 12)
      );
      renderManager28.invoke34(
         floatValue270,
         g + metrics33.measure(1.0F),
         floatValue269 * 0.5F,
         metrics33.measure(1.4F),
         metrics33.measure(1.0F),
         ColorScheme.compute5(255, 255, 255, 0),
         intValue55
      );
      renderManager28.invoke34(
         floatValue270 + floatValue269 * 0.5F,
         g + metrics33.measure(1.0F),
         floatValue269 * 0.5F,
         metrics33.measure(1.4F),
         metrics33.measure(1.0F),
         intValue55,
         ColorScheme.compute5(255, 255, 255, 0)
      );
   }

   private void invoke35(RenderManager renderManager29, AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerScrollLayout autoBuyModuleCardHandlerScrollLayout, boolean bl, float f, Metrics metrics34, ColorScheme colorScheme26) {
      if (autoBuyModuleCardHandlerScrollLayout.visible()) {
         ClickGuiRenderUtils.invoke16(
            renderManager29,
            metrics34,
            colorScheme26,
            autoBuyModuleCardHandlerScrollLayout.x(),
            autoBuyModuleCardHandlerScrollLayout.y(),
            autoBuyModuleCardHandlerScrollLayout.w(),
            autoBuyModuleCardHandlerScrollLayout.h(),
            autoBuyModuleCardHandlerScrollLayout.thumbY(),
            autoBuyModuleCardHandlerScrollLayout.thumbH(),
            f,
            bl ? 1.0F : 0.0F
         );
      }
   }

   private void invoke36(List<ClickGuiHitTarget> list, AutoBuy autoBuy14, AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerBounds autoBuyModuleCardHandlerBounds15, Metrics metrics35) {
      AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerData3 autoBuyModuleCardHandlerData33 = this.resolve3(autoBuyModuleCardHandlerBounds15, metrics35);
      float floatValue271 = autoBuyModuleCardHandlerData33.stripH();
      float floatValue272 = autoBuyModuleCardHandlerData33.modeX();
      float floatValue273 = autoBuyModuleCardHandlerData33.toggleW();
      float floatValue274 = autoBuyModuleCardHandlerData33.toggleX();
      float floatValue275 = autoBuyModuleCardHandlerData33.gap();
      float floatValue276 = autoBuyModuleCardHandlerData33.tabBtnSize();
      float floatValue277 = autoBuyModuleCardHandlerData33.chipW();
      float floatValue278 = floatValue272;

      for (String text52 : FUNTIME) {
         list.add(
            ClickGuiHitTarget.resolve()
               .setIntValue(0)
               .setFloatValue(floatValue278)
               .setFloatValue2(autoBuyModuleCardHandlerBounds15.y())
               .setFloatValue3(floatValue277)
               .setFloatValue4(floatValue271)
               .setClickGuiAction(clickGuiState37 -> {
                  autoBuy14.rezhimServera.value = text52;
                  autoBuy14.rezhimServera.selectedIndex = autoBuy14.rezhimServera.options.indexOf(text52);
                  this.invoke57(this.typeUtils, 0.0F);
                  clickGuiState37.setFlag5(false);
                  clickGuiState37.invoke66();
               })
               .resolve()
         );
         floatValue278 += floatValue277 + floatValue275;
      }

      list.add(
         ClickGuiHitTarget.resolve()
            .setIntValue(0)
            .setFloatValue(floatValue278)
            .setFloatValue2(autoBuyModuleCardHandlerBounds15.y())
            .setFloatValue3(floatValue276)
            .setFloatValue4(floatValue276)
            .setClickGuiAction(clickGuiState38 -> {
               this.intValue2 = 0;
               clickGuiState38.invoke66();
            })
            .resolve()
      );
      floatValue278 += floatValue276 + floatValue275;
      list.add(
         ClickGuiHitTarget.resolve()
            .setIntValue(0)
            .setFloatValue(floatValue278)
            .setFloatValue2(autoBuyModuleCardHandlerBounds15.y())
            .setFloatValue3(floatValue276)
            .setFloatValue4(floatValue276)
            .setClickGuiAction(clickGuiState39 -> {
               this.intValue2 = 1;
               this.timedAnimation.setDoubleValue3(0.0);
               this.timedAnimation.invoke();
               clickGuiState39.invoke66();
            })
            .resolve()
      );
      floatValue278 += floatValue276 + floatValue275;
      list.add(
         ClickGuiHitTarget.resolve()
            .setIntValue(0)
            .setFloatValue(floatValue278)
            .setFloatValue2(autoBuyModuleCardHandlerBounds15.y())
            .setFloatValue3(floatValue276)
            .setFloatValue4(floatValue276)
            .setClickGuiAction(clickGuiState40 -> {
               this.intValue2 = 2;
               clickGuiState40.invoke66();
            })
            .resolve()
      );
      if (autoBuyModuleCardHandlerData33.showReparse()) {
         list.add(
            ClickGuiHitTarget.resolve()
               .setIntValue(0)
               .setFloatValue(autoBuyModuleCardHandlerData33.reparseX())
               .setFloatValue2(autoBuyModuleCardHandlerBounds15.y())
               .setFloatValue3(autoBuyModuleCardHandlerData33.reparseToggleW())
               .setFloatValue4(floatValue271)
               .setClickGuiAction(clickGuiState41 -> {
                  autoBuy14.autoReparse.setValue(!autoBuy14.autoReparse.isEnabled());
                  clickGuiState41.setFlag5(false);
                  clickGuiState41.invoke66();
               })
               .resolve()
         );
         list.add(
            ClickGuiHitTarget.resolve()
               .setIntValue(0)
               .setFloatValue(autoBuyModuleCardHandlerData33.reparseSliderX())
               .setFloatValue2(autoBuyModuleCardHandlerBounds15.y())
               .setFloatValue3(autoBuyModuleCardHandlerData33.reparseSliderW())
               .setFloatValue4(floatValue271)
               .setClickGuiAction(clickGuiState42 -> {
                  float var5x = autoBuyModuleCardHandlerData33.reparseSliderX() + metrics35.measure(8.0F);
                  float var6x = Math.max(metrics35.measure(28.0F), autoBuyModuleCardHandlerData33.reparseSliderW() - metrics35.measure(16.0F));
                  this.invoke9(autoBuy14, clickGuiState42.getFloatValue(), var5x, var6x);
                  clickGuiState42.setNumberSetting(autoBuy14.reparseKazhdyeMin);
                  clickGuiState42.setFloatValue30(var5x);
                  clickGuiState42.setFloatValue31(var6x);
                  clickGuiState42.setFlag5(false);
                  clickGuiState42.invoke66();
               })
               .resolve()
         );
      }

      float floatValue279 = floatValue273 * 0.5F;
      list.add(
         ClickGuiHitTarget.resolve().setIntValue(0).setFloatValue(floatValue274).setFloatValue2(autoBuyModuleCardHandlerBounds15.y()).setFloatValue3(floatValue279).setFloatValue4(floatValue271).setClickGuiAction(clickGuiState43 -> {
            if (autoBuy14.enabled) {
               autoBuy14.toggle();
            }

            clickGuiState43.setFlag5(false);
            clickGuiState43.invoke66();
         }).resolve()
      );
      list.add(
         ClickGuiHitTarget.resolve()
            .setIntValue(0)
            .setFloatValue(floatValue274 + floatValue279)
            .setFloatValue2(autoBuyModuleCardHandlerBounds15.y())
            .setFloatValue3(floatValue279)
            .setFloatValue4(floatValue271)
            .setClickGuiAction(clickGuiState44 -> {
               if (!autoBuy14.enabled) {
                  autoBuy14.toggle();
               }

               clickGuiState44.setFlag5(false);
               clickGuiState44.invoke66();
            })
            .resolve()
      );
   }

   private void invoke37(List<ClickGuiHitTarget> list, AutoBuy autoBuy15, AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerBounds autoBuyModuleCardHandlerBounds16, Metrics metrics36) {
      this.invoke38(list, autoBuyModuleCardHandlerBounds16, metrics36);
      List items5 = this.resolve8(autoBuy15, this.catalogSearch.value);
      AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerScrollLayout autoBuyModuleCardHandlerScrollLayout2 = this.resolve5(
         autoBuyModuleCardHandlerBounds16.catalogScrollbarX(),
         autoBuyModuleCardHandlerBounds16.catalogViewportY(),
         autoBuyModuleCardHandlerBounds16.scrollbarW(),
         autoBuyModuleCardHandlerBounds16.catalogViewportH(),
         this.measure3(autoBuy15, autoBuyModuleCardHandlerBounds16, metrics36),
         this.typeUtils.measure(),
         metrics36
      );
      float floatValue280 = this.typeUtils.measure();
      int intValue56 = this.compute2(autoBuyModuleCardHandlerBounds16, metrics36);
      float floatValue281 = this.measure8(metrics36);
      float floatValue282 = this.measure9(metrics36);
      float floatValue283 = this.measure10(metrics36);
      int intValue57 = Math.max(1, (items5.size() + intValue56 - 1) / intValue56);
      int intValue58 = Math.max(0, (int)Math.floor(-floatValue280 / (floatValue282 + floatValue283)) - 1);
      int intValue59 = Math.min(intValue57, (int)Math.ceil((autoBuyModuleCardHandlerBounds16.catalogViewportH() - floatValue280) / (floatValue282 + floatValue283)) + 1);

      for (int intValue60 = intValue58; intValue60 < intValue59; intValue60++) {
         for (int intValue61 = 0; intValue61 < intValue56; intValue61++) {
            int intValue62 = intValue60 * intValue56 + intValue61;
            if (intValue62 >= items5.size()) {
               break;
            }

            AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerDisplayEntry autoBuyModuleCardHandlerDisplayEntry8 = (AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerDisplayEntry)items5.get(intValue62);
            float floatValue284 = autoBuyModuleCardHandlerBounds16.catalogViewportX() + intValue61 * (floatValue281 + floatValue283);
            float floatValue285 = autoBuyModuleCardHandlerBounds16.catalogViewportY() + floatValue280 + intValue60 * (floatValue282 + floatValue283);
            if (!(floatValue285 > autoBuyModuleCardHandlerBounds16.catalogViewportY() + autoBuyModuleCardHandlerBounds16.catalogViewportH()) && !(floatValue285 + floatValue282 < autoBuyModuleCardHandlerBounds16.catalogViewportY())) {
               list.add(
                  ClickGuiHitTarget.resolve().setIntValue(0).setFloatValue(floatValue284).setFloatValue2(floatValue285).setFloatValue3(floatValue281).setFloatValue4(floatValue282).setClickGuiAction(clickGuiState45 -> {
                     AutoBuy.VALUES_BY_KEY.putIfAbsent(autoBuyModuleCardHandlerDisplayEntry8.key(), 0L);
                     AutoBuy.VALUES_3.remove(autoBuyModuleCardHandlerDisplayEntry8.key());
                     clickGuiState45.setFlag5(false);
                     clickGuiState45.invoke52(this.resolve18(autoBuyModuleCardHandlerDisplayEntry8.key()));
                     clickGuiState45.invoke66();
                  }).resolve()
               );
               list.add(
                  ClickGuiHitTarget.resolve()
                     .setIntValue(1)
                     .setFloatValue(floatValue284)
                     .setFloatValue2(floatValue285)
                     .setFloatValue3(floatValue281)
                     .setFloatValue4(floatValue282)
                     .setFloatValue5(autoBuyModuleCardHandlerBounds16.catalogViewportX())
                     .setFloatValue6(autoBuyModuleCardHandlerBounds16.catalogViewportY())
                     .setFloatValue7(autoBuyModuleCardHandlerBounds16.catalogViewportW())
                     .setFloatValue8(autoBuyModuleCardHandlerBounds16.catalogViewportH())
                     .setClickGuiAction(clickGuiState46 -> this.invoke52(autoBuyModuleCardHandlerDisplayEntry8.key(), clickGuiState46))
                     .resolve()
               );
            }
         }
      }

      this.invoke47(list, "catalog", autoBuyModuleCardHandlerScrollLayout2, metrics36);
   }

   private void invoke38(List<ClickGuiHitTarget> list, AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerBounds autoBuyModuleCardHandlerBounds17, Metrics metrics37) {
      float floatValue286 = this.measure11(autoBuyModuleCardHandlerBounds17, metrics37);
      float floatValue287 = this.measure12(autoBuyModuleCardHandlerBounds17, metrics37);
      float floatValue288 = this.measure13(autoBuyModuleCardHandlerBounds17, metrics37);
      float floatValue289 = this.measure14(metrics37);
      if (this.catalogSearch.value != null && !this.catalogSearch.value.isEmpty()) {
         list.add(
            ClickGuiHitTarget.resolve()
               .setIntValue(0)
               .setFloatValue(floatValue286 + floatValue288 - metrics37.measure(34.0F))
               .setFloatValue2(floatValue287)
               .setFloatValue3(metrics37.measure(34.0F))
               .setFloatValue4(floatValue289)
               .setClickGuiAction(clickGuiState47 -> {
                  this.catalogSearch.value = "";
                  clickGuiState47.setNumberSetting((NumberSetting)null);
                  this.invoke54();
               })
               .resolve()
         );
      }

      list.add(ClickGuiHitTarget.resolve().setIntValue(0).setFloatValue(floatValue286).setFloatValue2(floatValue287).setFloatValue3(floatValue288).setFloatValue4(floatValue289).setClickGuiAction(clickGuiState48 -> {
         clickGuiState48.setFlag5(false);
         clickGuiState48.invoke52(this.catalogSearch);
      }).resolve());
   }

   private void invoke39(List<ClickGuiHitTarget> list, ClickGuiState clickGuiState49, AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerBounds autoBuyModuleCardHandlerBounds18, Metrics metrics38) {
      this.invoke41(list, autoBuyModuleCardHandlerBounds18, metrics38);
      List items6 = this.resolve17();
      AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerScrollLayout autoBuyModuleCardHandlerScrollLayout3 = this.resolve5(
         autoBuyModuleCardHandlerBounds18.rulesScrollbarX(),
         autoBuyModuleCardHandlerBounds18.rulesViewportY(),
         autoBuyModuleCardHandlerBounds18.scrollbarW(),
         autoBuyModuleCardHandlerBounds18.rulesViewportH(),
         this.measure4(autoBuyModuleCardHandlerBounds18, metrics38),
         this.typeUtils2.measure(),
         metrics38
      );
      float floatValue290 = this.typeUtils2.measure();
      float floatValue291 = this.measure15(metrics38);
      float floatValue292 = this.measure21(metrics38);
      float floatValue293 = metrics38.measure(6.0F);
      float floatValue294 = autoBuyModuleCardHandlerBounds18.rulesViewportX() + floatValue293;
      float floatValue295 = autoBuyModuleCardHandlerBounds18.rulesViewportW() - floatValue293 * 2.0F;
      float floatValue296 = autoBuyModuleCardHandlerBounds18.rulesViewportY() + floatValue290;

      for (int intValue63 = 0; intValue63 < items6.size(); intValue63++) {
         String text53 = (String)items6.get(intValue63);
         float floatValue297 = this.measure20(clickGuiState49, text53);
         float floatValue298 = this.measure16(text53, metrics38, floatValue297);
         if (!(floatValue296 > autoBuyModuleCardHandlerBounds18.rulesViewportY() + autoBuyModuleCardHandlerBounds18.rulesViewportH()) && !(floatValue296 + floatValue298 < autoBuyModuleCardHandlerBounds18.rulesViewportY())) {
            float floatValue299 = floatValue296 + (floatValue291 - metrics38.measure(29.0F)) * 0.5F;
            boolean flag34 = this.check8(text53);
            float floatValue300 = floatValue294 + floatValue295 - metrics38.measure(237.0F);
            float floatValue301 = floatValue294 + floatValue295 - metrics38.measure(134.0F);
            float floatValue302 = floatValue294 + floatValue295 - metrics38.measure(68.0F);
            float floatValue303 = floatValue294 + floatValue295 - metrics38.measure(34.0F);
            list.add(
               ClickGuiHitTarget.resolve()
                  .setIntValue(0)
                  .setFloatValue(floatValue302)
                  .setFloatValue2(floatValue299)
                  .setFloatValue3(metrics38.measure(29.0F))
                  .setFloatValue4(metrics38.measure(29.0F))
                  .setClickGuiAction(clickGuiState50 -> this.invoke52(text53, clickGuiState50))
                  .resolve()
            );
            if (flag34) {
               list.add(
                  ClickGuiHitTarget.resolve()
                     .setIntValue(0)
                     .setFloatValue(floatValue303)
                     .setFloatValue2(floatValue299)
                     .setFloatValue3(metrics38.measure(29.0F))
                     .setFloatValue4(metrics38.measure(29.0F))
                     .setClickGuiAction(clickGuiState51 -> {
                        this.text3 = text53.equals(this.text3) ? null : text53;
                        clickGuiState51.setFlag5(false);
                        if (!text53.equals(this.resolve20(clickGuiState51))) {
                           clickGuiState51.setNumberSetting((NumberSetting)null);
                        }
                     })
                     .resolve()
               );
            }

            list.add(
               ClickGuiHitTarget.resolve()
                  .setIntValue(0)
                  .setFloatValue(floatValue301)
                  .setFloatValue2(floatValue299)
                  .setFloatValue3(metrics38.measure(58.0F))
                  .setFloatValue4(metrics38.measure(29.0F))
                  .setClickGuiAction(clickGuiState52 -> {
                     if (AutoBuy.VALUES_3.contains(text53)) {
                        AutoBuy.VALUES_3.remove(text53);
                     } else {
                        AutoBuy.VALUES_3.add(text53);
                     }

                     clickGuiState52.setFlag5(false);
                     clickGuiState52.invoke66();
                  })
                  .resolve()
            );
            list.add(
               ClickGuiHitTarget.resolve()
                  .setIntValue(0)
                  .setFloatValue(floatValue300)
                  .setFloatValue2(floatValue299)
                  .setFloatValue3(metrics38.measure(95.0F))
                  .setFloatValue4(metrics38.measure(29.0F))
                  .setClickGuiAction(clickGuiState53 -> {
                     clickGuiState53.setFlag5(false);
                     clickGuiState53.invoke52(this.resolve18(text53));
                  })
                  .resolve()
            );
            if (text53.equals(this.text3) && this.check8(text53) && floatValue297 > 0.95F) {
               this.invoke40(
                  list, text53, floatValue294, floatValue296 + floatValue291 + metrics38.measure(6.0F) * floatValue297, floatValue295, this.measure17(text53, metrics38) * floatValue297, metrics38
               );
            }

            floatValue296 += floatValue298 + floatValue292;
         } else {
            floatValue296 += floatValue298 + floatValue292;
         }
      }

      this.invoke47(list, "rules", autoBuyModuleCardHandlerScrollLayout3, metrics38);
   }

   private void invoke40(List<ClickGuiHitTarget> list, String string, float f, float g, float h, float i, Metrics metrics39) {
      float floatValue304 = f + metrics39.measure(16.0F);
      float floatValue305 = g + metrics39.measure(58.0F);
      float floatValue306 = h - metrics39.measure(32.0F);
      list.add(
         ClickGuiHitTarget.resolve()
            .setIntValue(0)
            .setFloatValue(floatValue304 - metrics39.measure(6.0F))
            .setFloatValue2(floatValue305 + metrics39.measure(12.0F))
            .setFloatValue3(floatValue306 + metrics39.measure(12.0F))
            .setFloatValue4(metrics39.measure(24.0F))
            .setClickGuiAction(clickGuiState54 -> {
               this.invoke43(string, clickGuiState54.getFloatValue(), floatValue304, floatValue306);
               this.invoke44(string, clickGuiState54.getFloatValue(), clickGuiState54);
            })
            .resolve()
      );
      AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerData autoBuyModuleCardHandlerData4 = this.resolve11(string);
      float floatValue307 = g + metrics39.measure(128.0F);
      float floatValue308 = (h - metrics39.measure(40.0F)) * 0.5F;
      float floatValue309 = metrics39.measure(8.0F);
      float floatValue310 = metrics39.measure(22.0F);

      for (int intValue64 = 0; intValue64 < autoBuyModuleCardHandlerData4.enchantments().size(); intValue64++) {
         AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerDisplayEntry2 autoBuyModuleCardHandlerDisplayEntry23 = autoBuyModuleCardHandlerData4.enchantments().get(intValue64);
         this.invoke42(
            list,
            string,
            autoBuyModuleCardHandlerDisplayEntry23.key(),
            f + metrics39.measure(16.0F) + intValue64 % 2 * (floatValue308 + floatValue309),
            floatValue307 + intValue64 / 2 * (floatValue310 + metrics39.measure(4.0F)),
            floatValue308,
            floatValue310
         );
      }
   }

   private void invoke41(List<ClickGuiHitTarget> list, AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerBounds autoBuyModuleCardHandlerBounds19, Metrics metrics40) {
      AutoBuy autoBuy16 = AutoBuy.instance;
      if (autoBuy16 != null) {
         AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerData2 autoBuyModuleCardHandlerData22 = this.resolve4(autoBuyModuleCardHandlerBounds19, metrics40);
         if (autoBuyModuleCardHandlerData22.visible()) {
            list.add(
               ClickGuiHitTarget.resolve()
                  .setIntValue(0)
                  .setFloatValue(autoBuyModuleCardHandlerData22.chipX())
                  .setFloatValue2(autoBuyModuleCardHandlerData22.chipY())
                  .setFloatValue3(autoBuyModuleCardHandlerData22.chipW())
                  .setFloatValue4(autoBuyModuleCardHandlerData22.chipH())
                  .setClickGuiAction(clickGuiState55 -> {
                     autoBuy16.detektZamedleniyaAuka.setValue(!autoBuy16.detektZamedleniyaAuka.isEnabled());
                     clickGuiState55.setFlag5(false);
                     clickGuiState55.invoke66();
                  })
                  .resolve()
            );
            list.add(
               ClickGuiHitTarget.resolve()
                  .setIntValue(0)
                  .setFloatValue(autoBuyModuleCardHandlerData22.fixX())
                  .setFloatValue2(autoBuyModuleCardHandlerData22.chipY())
                  .setFloatValue3(autoBuyModuleCardHandlerData22.fixW())
                  .setFloatValue4(autoBuyModuleCardHandlerData22.chipH())
                  .setClickGuiAction(clickGuiState56 -> {
                     autoBuy16.avtoFiksZamedleniya.setValue(!autoBuy16.avtoFiksZamedleniya.isEnabled());
                     clickGuiState56.setFlag5(false);
                     clickGuiState56.invoke66();
                  })
                  .resolve()
            );
            list.add(
               ClickGuiHitTarget.resolve()
                  .setIntValue(0)
                  .setFloatValue(autoBuyModuleCardHandlerData22.statX())
                  .setFloatValue2(autoBuyModuleCardHandlerData22.chipY())
                  .setFloatValue3(autoBuyModuleCardHandlerData22.statW())
                  .setFloatValue4(autoBuyModuleCardHandlerData22.chipH())
                  .setClickGuiAction(clickGuiState57 -> {
                     autoBuy16.lagStatistikaVChat.setValue(!autoBuy16.lagStatistikaVChat.isEnabled());
                     clickGuiState57.setFlag5(false);
                     clickGuiState57.invoke66();
                  })
                  .resolve()
            );
         }
      }
   }

   private void invoke42(List<ClickGuiHitTarget> list, String string, String string2, float f, float g, float h, float i) {
      list.add(ClickGuiHitTarget.resolve().setIntValue(0).setFloatValue(f).setFloatValue2(g).setFloatValue3(h).setFloatValue4(i).setClickGuiAction(clickGuiState58 -> {
         AutoBuy.invoke47(string, string2, !AutoBuy.check34(string, string2));
         clickGuiState58.setFlag5(false);
         clickGuiState58.invoke66();
      }).resolve());
   }

   private void invoke43(String string, float f, float g, float h) {
      int intValue65 = AutoBuy.compute22(string);
      int intValue66 = AutoBuy.compute23(string);
      float floatValue311 = g + h * intValue65 / 100.0F;
      float floatValue312 = g + h * intValue66 / 100.0F;
      this.text4 = string;
      this.flag2 = Math.abs(f - floatValue312) < Math.abs(f - floatValue311);
      this.floatValue9 = g;
      this.floatValue10 = Math.max(1.0F, h);
   }

   private void invoke44(String string, float f, ClickGuiState clickGuiState59) {
      int intValue67 = (int)RenderMath.measure35((double)(this.measure22((f - this.floatValue9) / this.floatValue10, 0.0F, 1.0F) * 100.0F), 0);
      int intValue68 = AutoBuy.compute22(string);
      int intValue69 = AutoBuy.compute23(string);
      if (this.flag2) {
         intValue69 = Math.max(intValue68, intValue67);
      } else {
         intValue68 = Math.min(intValue69, intValue67);
      }

      AutoBuy.invoke46(string, intValue68, intValue69);
      clickGuiState59.setFlag5(false);
      clickGuiState59.invoke66();
   }

   private void invoke45(List<ClickGuiHitTarget> list, AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerBounds autoBuyModuleCardHandlerBounds20, Metrics metrics41) {
      float floatValue313 = this.measure6(autoBuyModuleCardHandlerBounds20, metrics41);
      float floatValue314 = metrics41.measure(42.0F);
      AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerScrollLayout autoBuyModuleCardHandlerScrollLayout4 = this.resolve5(
         autoBuyModuleCardHandlerBounds20.x() + autoBuyModuleCardHandlerBounds20.width() - metrics41.measure(10.0F),
         autoBuyModuleCardHandlerBounds20.panelY() + floatValue314,
         autoBuyModuleCardHandlerBounds20.scrollbarW(),
         autoBuyModuleCardHandlerBounds20.panelH() - floatValue314 - metrics41.measure(10.0F),
         floatValue313,
         this.typeUtils3.measure(),
         metrics41
      );
      this.invoke47(list, "history", autoBuyModuleCardHandlerScrollLayout4, metrics41);
      float floatValue315 = autoBuyModuleCardHandlerBounds20.panelY() + metrics41.measure(14.0F);
      float floatValue316 = metrics41.measure(64.0F);
      float floatValue317 = metrics41.measure(20.0F);
      float floatValue318 = autoBuyModuleCardHandlerBounds20.x() + autoBuyModuleCardHandlerBounds20.width() - metrics41.measure(16.0F) - floatValue316;
      list.add(ClickGuiHitTarget.resolve().setIntValue(0).setFloatValue(floatValue318).setFloatValue2(floatValue315).setFloatValue3(floatValue316).setFloatValue4(floatValue317).setClickGuiAction(clickGuiState60 -> {
         AutoBuy.ITEMS_2.clear();
         clickGuiState60.invoke66();
      }).resolve());
      float floatValue319 = metrics41.measure(42.0F);
      float floatValue320 = metrics41.measure(6.0F);
      float floatValue321 = autoBuyModuleCardHandlerBounds20.x() + metrics41.measure(16.0F);
      float floatValue322 = autoBuyModuleCardHandlerBounds20.panelY() + floatValue314;
      float floatValue323 = autoBuyModuleCardHandlerBounds20.width() - metrics41.measure(20.0F) - autoBuyModuleCardHandlerBounds20.scrollbarW();
      float floatValue324 = autoBuyModuleCardHandlerBounds20.panelH() - floatValue314 - metrics41.measure(10.0F);
      float floatValue325 = this.typeUtils3.measure();
      float floatValue326 = floatValue323 - metrics41.measure(36.0F);

      for (int intValue70 = 0; intValue70 < AutoBuy.ITEMS_2.size(); intValue70++) {
         float floatValue327 = floatValue322 + floatValue325 + intValue70 * (floatValue319 + floatValue320);
         if (!(floatValue327 > floatValue322 + floatValue324) && !(floatValue327 + floatValue319 < floatValue322)) {
            float floatValue328 = metrics41.measure(26.0F);
            float floatValue329 = floatValue321 + floatValue326 + metrics41.measure(6.0F);
            int intValue71 = intValue70;
            list.add(
               ClickGuiHitTarget.resolve()
                  .setIntValue(0)
                  .setFloatValue(floatValue329)
                  .setFloatValue2(floatValue327 + (floatValue319 - floatValue328) * 0.5F)
                  .setFloatValue3(floatValue328)
                  .setFloatValue4(floatValue328)
                  .setClickGuiAction(clickGuiState61 -> {
                     if (intValue71 < AutoBuy.ITEMS_2.size()) {
                        AutoBuy.ITEMS_2.remove(intValue71);
                        clickGuiState61.invoke66();
                     }
                  })
                  .resolve()
            );
         }
      }
   }

   private void invoke46(List<ClickGuiHitTarget> list, AutoBuy autoBuy17, AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerBounds autoBuyModuleCardHandlerBounds21, Metrics metrics42) {
      float floatValue330 = this.measure7(autoBuyModuleCardHandlerBounds21, metrics42);
      float floatValue331 = metrics42.measure(62.0F);
      AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerScrollLayout autoBuyModuleCardHandlerScrollLayout5 = this.resolve5(
         autoBuyModuleCardHandlerBounds21.x() + autoBuyModuleCardHandlerBounds21.width() - metrics42.measure(10.0F),
         autoBuyModuleCardHandlerBounds21.panelY() + floatValue331,
         autoBuyModuleCardHandlerBounds21.scrollbarW(),
         autoBuyModuleCardHandlerBounds21.panelH() - floatValue331 - metrics42.measure(10.0F),
         floatValue330,
         this.typeUtils4.measure(),
         metrics42
      );
      this.invoke47(list, "cloud", autoBuyModuleCardHandlerScrollLayout5, metrics42);
      float floatValue332 = autoBuyModuleCardHandlerBounds21.panelY() + metrics42.measure(14.0F);
      float floatValue333 = metrics42.measure(28.0F);
      float floatValue334 = metrics42.measure(8.0F);
      float floatValue335 = autoBuyModuleCardHandlerBounds21.x() + autoBuyModuleCardHandlerBounds21.width() - metrics42.measure(16.0F) - floatValue333;
      list.add(ClickGuiHitTarget.resolve().setIntValue(0).setFloatValue(floatValue335).setFloatValue2(floatValue332).setFloatValue3(floatValue333).setFloatValue4(floatValue333).setClickGuiAction(clickGuiState62 -> {
         try {
            File file4 = autoBuy17.resolve5();
            String text54 = System.getProperty("os.name").toLowerCase();
            if (text54.contains("win")) {
               Runtime.getRuntime().exec(new String[]{"explorer", file4.getAbsolutePath()});
            } else if (text54.contains("mac")) {
               Runtime.getRuntime().exec(new String[]{"open", file4.getAbsolutePath()});
            } else {
               Runtime.getRuntime().exec(new String[]{"xdg-open", file4.getAbsolutePath()});
            }
         } catch (Exception exception2) {
         }
      }).resolve());
      floatValue335 -= floatValue333 + floatValue334;
      list.add(
         ClickGuiHitTarget.resolve()
            .setIntValue(0)
            .setFloatValue(floatValue335)
            .setFloatValue2(floatValue332)
            .setFloatValue3(floatValue333)
            .setFloatValue4(floatValue333)
            .setClickGuiAction(clickGuiState63 -> this.invoke6(autoBuy17))
            .resolve()
      );
      floatValue335 -= floatValue333 + floatValue334;
      list.add(ClickGuiHitTarget.resolve().setIntValue(0).setFloatValue(floatValue335).setFloatValue2(floatValue332).setFloatValue3(floatValue333).setFloatValue4(floatValue333).setClickGuiAction(clickGuiState64 -> {
         String text55 = "Default";
         String text56 = text55;
         int var5x = 1;

         for (File var6x = autoBuy17.resolve5(); new File(var6x, text56 + ".json").exists(); var5x++) {
            text56 = text55 + var5x;
         }

         autoBuy17.invoke3(text56);
         this.text = text56;
         this.invoke6(autoBuy17);
      }).resolve());
      float floatValue336 = metrics42.measure(58.0F);
      float floatValue337 = metrics42.measure(8.0F);
      float floatValue338 = autoBuyModuleCardHandlerBounds21.x() + metrics42.measure(16.0F);
      float floatValue339 = autoBuyModuleCardHandlerBounds21.panelY() + floatValue331;
      float floatValue340 = autoBuyModuleCardHandlerBounds21.width() - metrics42.measure(25.0F) - autoBuyModuleCardHandlerBounds21.scrollbarW();
      float floatValue341 = autoBuyModuleCardHandlerBounds21.panelH() - floatValue331 - metrics42.measure(10.0F);
      float floatValue342 = this.typeUtils4.measure();
      float floatValue343 = floatValue340 - metrics42.measure(24.0F);

      for (int intValue72 = 0; intValue72 < this.items2.size(); intValue72++) {
         AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerTimedEntry autoBuyModuleCardHandlerTimedEntry5 = this.items2.get(intValue72);
         float floatValue344 = floatValue339 + floatValue342 + intValue72 * (floatValue336 + floatValue337);
         if (!(floatValue344 > floatValue339 + floatValue341) && !(floatValue344 + floatValue336 < floatValue339)) {
            float floatValue345 = metrics42.measure(26.0F);
            float floatValue346 = metrics42.measure(8.0F);
            float floatValue347 = floatValue338 + floatValue343 - metrics42.measure(12.0F) - floatValue345;
            list.add(
               ClickGuiHitTarget.resolve()
                  .setIntValue(0)
                  .setFloatValue(floatValue347)
                  .setFloatValue2(floatValue344 + (floatValue336 - floatValue345) * 0.5F)
                  .setFloatValue3(floatValue345)
                  .setFloatValue4(floatValue345)
                  .setClickGuiAction(clickGuiState65 -> {
                     autoBuy17.invoke5(autoBuyModuleCardHandlerTimedEntry5.name);
                     if (this.text.equals(autoBuyModuleCardHandlerTimedEntry5.name)) {
                        this.text = "";
                     }

                     this.invoke6(autoBuy17);
                  })
                  .resolve()
            );
            floatValue347 -= floatValue345 + floatValue346;
            list.add(
               ClickGuiHitTarget.resolve()
                  .setIntValue(0)
                  .setFloatValue(floatValue347)
                  .setFloatValue2(floatValue344 + (floatValue336 - floatValue345) * 0.5F)
                  .setFloatValue3(floatValue345)
                  .setFloatValue4(floatValue345)
                  .setClickGuiAction(clickGuiState66 -> {
                     autoBuy17.invoke4(autoBuyModuleCardHandlerTimedEntry5.name);
                     this.text = autoBuyModuleCardHandlerTimedEntry5.name;
                  })
                  .resolve()
            );
            float floatValue348 = floatValue344 + metrics42.measure(12.0F);
            float floatValue349 = ClickGuiRenderUtils.measure(FontRegistry.fontObject4, autoBuyModuleCardHandlerTimedEntry5.name, 13.0F);
            list.add(
               ClickGuiHitTarget.resolve()
                  .setIntValue(0)
                  .setFloatValue(floatValue338 + metrics42.measure(12.0F))
                  .setFloatValue2(floatValue348 - metrics42.measure(4.0F))
                  .setFloatValue3(floatValue349 + metrics42.measure(24.0F))
                  .setFloatValue4(metrics42.measure(18.0F))
                  .setClickGuiAction(
                     clickGuiState67 -> clickGuiState67.invoke52(this.valuesByKey.computeIfAbsent(autoBuyModuleCardHandlerTimedEntry5.name, string -> new TextSetting("Name", string)))
                  )
                  .resolve()
            );
         }
      }
   }

   private void invoke47(List<ClickGuiHitTarget> list, String string, AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerScrollLayout autoBuyModuleCardHandlerScrollLayout6, Metrics metrics43) {
      if (autoBuyModuleCardHandlerScrollLayout6.visible()) {
         float floatValue350 = metrics43.measure(5.0F);
         list.add(
            ClickGuiHitTarget.resolve()
               .setIntValue(0)
               .setFloatValue(autoBuyModuleCardHandlerScrollLayout6.x() - floatValue350)
               .setFloatValue2(autoBuyModuleCardHandlerScrollLayout6.y())
               .setFloatValue3(autoBuyModuleCardHandlerScrollLayout6.w() + floatValue350 * 2.0F)
               .setFloatValue4(autoBuyModuleCardHandlerScrollLayout6.h())
               .setClickGuiAction(clickGuiState68 -> {
                  this.invoke48(string, clickGuiState68.getFloatValue2(), autoBuyModuleCardHandlerScrollLayout6);
                  clickGuiState68.setFlag5(false);
                  if (!this.check9(clickGuiState68.getTextSetting()) && this.resolve(clickGuiState68) == null) {
                     clickGuiState68.setNumberSetting((NumberSetting)null);
                  }
               })
               .resolve()
         );
      }
   }

   private AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerScrollLayout resolve5(float f, float g, float h, float i, float j, float k, Metrics metrics44) {
      if (!(j <= 0.5F) && !(i <= metrics44.measure(8.0F))) {
         float floatValue351 = Math.max(metrics44.measure(34.0F), i * (i / (i + j)));
         floatValue351 = Math.min(i, floatValue351);
         float floatValue352 = Math.max(0.0F, i - floatValue351);
         float floatValue353 = j <= 0.001F ? 0.0F : this.measure22(-k / j, 0.0F, 1.0F);
         float floatValue354 = g + floatValue352 * floatValue353;
         return new AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerScrollLayout(f, g, h, i, j, floatValue354, floatValue351, true);
      } else {
         return AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerScrollLayout.hidden(f, g, h, i);
      }
   }

   private void invoke48(String string, float f, AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerScrollLayout autoBuyModuleCardHandlerScrollLayout7) {
      if (autoBuyModuleCardHandlerScrollLayout7.visible()) {
         this.flag3 = "catalog".equals(string);
         this.flag4 = "rules".equals(string);
         this.flag5 = "history".equals(string);
         this.flag6 = "cloud".equals(string);
         if (f >= autoBuyModuleCardHandlerScrollLayout7.thumbY() && f <= autoBuyModuleCardHandlerScrollLayout7.thumbY() + autoBuyModuleCardHandlerScrollLayout7.thumbH()) {
            this.floatValue11 = f - autoBuyModuleCardHandlerScrollLayout7.thumbY();
         } else {
            this.floatValue11 = autoBuyModuleCardHandlerScrollLayout7.thumbH() * 0.5F;
         }

         this.invoke49(string, f, autoBuyModuleCardHandlerScrollLayout7);
      }
   }

   private void invoke49(String string, float f, AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerScrollLayout autoBuyModuleCardHandlerScrollLayout8) {
      if (autoBuyModuleCardHandlerScrollLayout8.visible()) {
         float floatValue355 = autoBuyModuleCardHandlerScrollLayout8.travel();
         float floatValue356 = this.measure22(f - this.floatValue11, autoBuyModuleCardHandlerScrollLayout8.y(), autoBuyModuleCardHandlerScrollLayout8.y() + floatValue355);
         float floatValue357 = floatValue355 <= 0.001F ? 0.0F : (floatValue356 - autoBuyModuleCardHandlerScrollLayout8.y()) / floatValue355;
         float floatValue358 = -autoBuyModuleCardHandlerScrollLayout8.maxScroll() * floatValue357;
         if ("catalog".equals(string)) {
            this.invoke57(this.typeUtils, floatValue358);
         } else if ("rules".equals(string)) {
            this.invoke57(this.typeUtils2, floatValue358);
         } else if ("history".equals(string)) {
            this.invoke57(this.typeUtils3, floatValue358);
         } else if ("cloud".equals(string)) {
            this.invoke57(this.typeUtils4, floatValue358);
         }
      }
   }

   private AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerBounds resolve6(ModulePlacement modulePlacement4, Metrics metrics45) {
      float floatValue359 = modulePlacement4.getFloatValue() + metrics45.measure(16.0F);
      float floatValue360 = metrics45.measure(5.0F);
      float floatValue361 = modulePlacement4.getFloatValue2() + metrics45.getFloatValue15() + metrics45.measure(10.0F) + floatValue360;
      float floatValue362 = modulePlacement4.getFloatValue3() - metrics45.measure(32.0F);
      float floatValue363 = Math.max(0.0F, modulePlacement4.getFloatValue5() - metrics45.measure(20.0F) - floatValue360);
      float floatValue364 = metrics45.measure(34.0F);
      float floatValue365 = metrics45.measure(8.0F);
      float floatValue366 = floatValue361 + floatValue364 + floatValue365;
      float floatValue367 = Math.max(metrics45.measure(80.0F), floatValue363 - floatValue364 - floatValue365);
      float floatValue368 = metrics45.measure(10.0F);
      float floatValue369 = Math.min(metrics45.measure(300.0F), floatValue362 * 0.44F);
      float floatValue370 = Math.min(metrics45.measure(180.0F), floatValue362 * 0.46F);
      float floatValue371 = Math.min(metrics45.measure(220.0F), floatValue362 * 0.46F);
      float floatValue372 = Math.max(metrics45.measure(120.0F), floatValue362 - floatValue371 - floatValue368);
      floatValue369 = Math.max(floatValue370, Math.min(floatValue369, floatValue372));
      if (floatValue369 + floatValue368 + metrics45.measure(120.0F) > floatValue362) {
         floatValue369 = Math.max(metrics45.measure(120.0F), floatValue362 - metrics45.measure(120.0F) - floatValue368);
      }

      float floatValue373 = Math.max(metrics45.measure(120.0F), floatValue362 - floatValue369 - floatValue368);
      float floatValue374 = floatValue359 + floatValue369 + floatValue368;
      float floatValue375 = metrics45.measure(10.0F);
      float floatValue376 = metrics45.measure(82.0F);
      float floatValue377 = metrics45.measure(48.0F);
      float floatValue378 = Math.max(metrics45.measure(5.5F), 4.0F);
      float floatValue379 = floatValue359 + floatValue375;
      float floatValue380 = floatValue366 + floatValue376;
      float floatValue381 = Math.max(metrics45.measure(60.0F), floatValue369 - floatValue375 * 2.0F - floatValue378 - metrics45.measure(5.0F));
      float floatValue382 = Math.max(metrics45.measure(30.0F), floatValue367 - floatValue376 - metrics45.measure(12.0F));
      float floatValue383 = floatValue374 + floatValue375;
      float floatValue384 = floatValue366 + floatValue377;
      float floatValue385 = Math.max(metrics45.measure(120.0F), floatValue373 - floatValue375 * 2.0F - floatValue378 - metrics45.measure(5.0F));
      float floatValue386 = Math.max(metrics45.measure(30.0F), floatValue367 - floatValue377 - metrics45.measure(12.0F));
      return new AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerBounds(
         floatValue359,
         floatValue361,
         floatValue362,
         floatValue363,
         floatValue359,
         floatValue374,
         floatValue369,
         floatValue373,
         floatValue366,
         floatValue367,
         floatValue379,
         floatValue380,
         floatValue381,
         floatValue382,
         floatValue379 + floatValue381 + metrics45.measure(5.0F),
         floatValue383,
         floatValue384,
         floatValue385,
         floatValue386,
         floatValue383 + floatValue385 + metrics45.measure(5.0F),
         floatValue378
      );
   }

   private List<AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerDisplayEntry> resolve7(AutoBuy autoBuy18) {
      return this.resolve8(autoBuy18, "");
   }

   private List<AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerDisplayEntry> resolve8(AutoBuy autoBuy19, String string) {
      ArrayList arrayList = new ArrayList();
      if (autoBuy19 != null && autoBuy19.rezhimServera.is("HolyWorld")) {
         for (HolyWorldItemParser.HolyWorldItemParserDisplayEntry holyWorldItemParserDisplayEntry : HolyWorldItemParser.getITEMS()) {
            arrayList.add(new AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerDisplayEntry(holyWorldItemParserDisplayEntry.key(), holyWorldItemParserDisplayEntry.label(), new ItemStack(holyWorldItemParserDisplayEntry.item()), true));
         }
      } else {
         for (String text57 : ITEMS) {
            arrayList.add(new AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerDisplayEntry(text57, text57, this.resolve12(text57), true));
         }
      }

      arrayList.addAll(resolve9());
      String text58 = string == null ? "" : string.trim().toLowerCase(Locale.ROOT);
      if (text58.isEmpty()) {
         return arrayList;
      } else {
         ArrayList arrayList2 = new ArrayList();

         for (AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerDisplayEntry autoBuyModuleCardHandlerDisplayEntry9 : (List<AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerDisplayEntry>)arrayList) {
            if (autoBuyModuleCardHandlerDisplayEntry9.label().toLowerCase(Locale.ROOT).contains(text58) || autoBuyModuleCardHandlerDisplayEntry9.key().toLowerCase(Locale.ROOT).contains(text58)) {
               arrayList2.add(autoBuyModuleCardHandlerDisplayEntry9);
            }
         }

         return arrayList2;
      }
   }

   private static List<AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerDisplayEntry> resolve9() {
      if (items != null) {
         return items;
      } else {
         ArrayList arrayList3 = new ArrayList();

         for (Item item : Registries.ITEM) {
            if (item != Items.AIR) {
               Identifier identifier = Registries.ITEM.getId(item);
               if (identifier != null && "minecraft".equals(identifier.getNamespace())) {
                  ItemStack itemStack3 = item.getDefaultStack();
                  arrayList3.add(new AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerDisplayEntry(identifier.toString(), itemStack3.getName().getString(), itemStack3, false));
               }
            }
         }

         arrayList3.sort(Comparator.comparing(AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerDisplayEntry::label, String.CASE_INSENSITIVE_ORDER));
         items = List.copyOf(arrayList3);
         return items;
      }
   }

   private AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerDisplayEntry resolve10(String string) {
      if (HolyWorldItemParser.check(string)) {
         HolyWorldItemParser.HolyWorldItemParserDisplayEntry holyWorldItemParserDisplayEntry2 = HolyWorldItemParser.resolve(string);
         if (holyWorldItemParserDisplayEntry2 != null) {
            ItemStack itemStack4 = HolyWorldItemParser.resolve4(holyWorldItemParserDisplayEntry2.key());
            if (itemStack4.isEmpty()) {
               itemStack4 = new ItemStack(holyWorldItemParserDisplayEntry2.item());
            }

            return new AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerDisplayEntry(holyWorldItemParserDisplayEntry2.key(), holyWorldItemParserDisplayEntry2.label(), itemStack4, true);
         } else {
            return new AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerDisplayEntry(string == null ? "" : string, string == null ? "" : string, ItemStack.EMPTY, true);
         }
      } else if (ITEMS.contains(string)) {
         return new AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerDisplayEntry(string, string, this.resolve12(string), true);
      } else {
         if (string != null && string.startsWith("minecraft:")) {
            Identifier identifier2 = Identifier.tryParse(string);
            if (identifier2 != null) {
               Item item2 = (Item)Registries.ITEM.get(identifier2);
               if (item2 != Items.AIR) {
                  ItemStack itemStack5 = item2.getDefaultStack();
                  return new AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerDisplayEntry(string, itemStack5.getName().getString(), itemStack5, false);
               }
            }
         }

         return new AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerDisplayEntry(string == null ? "" : string, string == null ? "" : string, ItemStack.EMPTY, true);
      }
   }

   private AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerData resolve11(String string) {
      HolyWorldItemParser.HolyWorldItemParserDisplayEntry holyWorldItemParserDisplayEntry3 = HolyWorldItemParser.resolve(string);
      if (holyWorldItemParserDisplayEntry3 != null) {
         ArrayList arrayList4 = new ArrayList();

         for (String text59 : holyWorldItemParserDisplayEntry3.enchantments()) {
            String text60 = HolyWorldItemParser.resolve3(text59);
            if (!text60.isBlank()) {
               arrayList4.add(new AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerDisplayEntry2(text60, this.resolve16(text59)));
            }
         }

         return new AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerData(arrayList4);
      } else {
         ItemStack itemStack6 = this.resolve13(string);
         return !itemStack6.isEmpty() ? new AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerData(this.resolve14(itemStack6)) : new AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerData(List.of());
      }
   }

   private ItemStack resolve12(String string) {
      ItemStack itemStack7 = this.resolve13(string);
      if (!itemStack7.isEmpty()) {
         return itemStack7;
      } else {
         ItemStack itemStack8 = SpecialItemIconRenderer.resolve(string);
         return itemStack8 == null ? ItemStack.EMPTY : itemStack8;
      }
   }

   private ItemStack resolve13(String string) {
      if (string == null) {
         return ItemStack.EMPTY;
      } else {
         return switch (string) {
            case "Шлем Крушителя" -> SpecialItemCatalog.resolve();
            case "Нагрудник Крушителя" -> SpecialItemCatalog.resolve2();
            case "Поножи Крушителя" -> SpecialItemCatalog.resolve3();
            case "Ботинки Крушителя" -> SpecialItemCatalog.resolve4();
            case "Меч Крушителя" -> SpecialItemCatalog.resolve5();
            case "Кирка Крушителя" -> SpecialItemCatalog.resolve6();
            case "Арбалет Крушителя" -> SpecialItemCatalog.resolve7();
            case "Трезубец Крушителя" -> SpecialItemCatalog.resolve8();
            case "Булава Крушителя" -> SpecialItemCatalog.resolve9();
            default -> ItemStack.EMPTY;
         };
      }
   }

   private List<AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerDisplayEntry2> resolve14(ItemStack itemStack) {
      ItemEnchantmentsComponent itemEnchantmentsComponent = (ItemEnchantmentsComponent)itemStack.get(DataComponentTypes.ENCHANTMENTS);
      if (itemEnchantmentsComponent != null && !itemEnchantmentsComponent.isEmpty()) {
         ArrayList arrayList5 = new ArrayList();

         for (it.unimi.dsi.fastutil.objects.Object2IntMap.Entry entry3 : itemEnchantmentsComponent.getEnchantmentEntries()) {
            String text61 = this.resolve15((RegistryEntry<Enchantment>)entry3.getKey());
            if (!text61.isBlank()) {
               String text62 = text61 + ":" + entry3.getIntValue();
               String text63 = HolyWorldItemParser.resolve3(text62);
               if (!text63.isBlank()) {
                  arrayList5.add(new AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerDisplayEntry2(text63, this.resolve16(text62)));
               }
            }
         }

         return arrayList5;
      } else {
         return List.of();
      }
   }

   private String resolve15(RegistryEntry<Enchantment> registryEntry) {
      return registryEntry.getKey().map(registryKey -> registryKey.getValue().toString()).orElse("");
   }

   private String resolve16(String string) {
      if (string != null && !string.isBlank()) {
         String[] texts = string.split(":");
         String text64 = texts.length >= 2 ? texts[1] : string.replace("minecraft:", "");
         String text65 = VALUES_BY_KEY.getOrDefault(text64, text64.replace('_', ' '));
         return texts.length >= 3 ? text65 + " " + texts[2] : text65;
      } else {
         return "";
      }
   }

   private boolean check8(String string) {
      HolyWorldItemParser.HolyWorldItemParserDisplayEntry holyWorldItemParserDisplayEntry4 = HolyWorldItemParser.resolve(string);
      if (holyWorldItemParserDisplayEntry4 != null) {
         return AutoBuy.check36(holyWorldItemParserDisplayEntry4.item());
      } else {
         AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerDisplayEntry autoBuyModuleCardHandlerDisplayEntry10 = this.resolve10(string);
         return autoBuyModuleCardHandlerDisplayEntry10 != null && !autoBuyModuleCardHandlerDisplayEntry10.stack().isEmpty() && AutoBuy.check36(autoBuyModuleCardHandlerDisplayEntry10.stack().getItem());
      }
   }

   private List<String> resolve17() {
      return new ArrayList<>(AutoBuy.VALUES_BY_KEY.keySet());
   }

   private TextSetting resolve18(String string) {
      return this.valuesByKey2.computeIfAbsent(string, stringx -> new TextSetting("Макс. цена", this.resolve19(stringx)));
   }

   private String resolve19(String string) {
      long longValue3 = AutoBuy.VALUES_BY_KEY.getOrDefault(string, 0L);
      return longValue3 <= 0L ? "" : Long.toString(longValue3);
   }

   private void invoke50(ClickGuiState clickGuiState69) {
      this.valuesByKey2
         .entrySet()
         .removeIf(entry -> !AutoBuy.VALUES_BY_KEY.containsKey(entry.getKey()) && clickGuiState69.getTextSetting() != entry.getValue());
   }

   private boolean check9(TextSetting textSetting8) {
      return textSetting8 != null && this.valuesByKey2.containsValue(textSetting8);
   }

   private String resolve20(ClickGuiState clickGuiState70) {
      TextSetting textSetting9 = clickGuiState70.getTextSetting();
      if (textSetting9 == null) {
         return null;
      } else {
         for (Entry entry4 : this.valuesByKey2.entrySet()) {
            if (entry4.getValue() == textSetting9) {
               return (String)entry4.getKey();
            }
         }

         return null;
      }
   }

   private void invoke51(String string, String string2, ClickGuiState clickGuiState71) {
      AutoBuy.VALUES_BY_KEY.put(string, this.compute(string2));
      clickGuiState71.invoke66();
   }

   private long compute(String string) {
      if (string != null && !string.isBlank()) {
         try {
            return Long.parseLong(string);
         } catch (NumberFormatException numberFormatException) {
            return 0L;
         }
      } else {
         return 0L;
      }
   }

   private void invoke52(String string, ClickGuiState clickGuiState72) {
      TextSetting textSetting10 = this.valuesByKey2.remove(string);
      if (clickGuiState72.getTextSetting() == textSetting10) {
         clickGuiState72.setNumberSetting((NumberSetting)null);
      }

      if (string != null && string.equals(this.text3)) {
         this.text3 = null;
      }

      AutoBuy.VALUES_BY_KEY.remove(string);
      AutoBuy.VALUES_BY_KEY_2.remove(string);
      AutoBuy.VALUES_BY_KEY_3.remove(string);
      AutoBuy.VALUES_BY_KEY_4.remove(string);
      AutoBuy.VALUES_3.remove(string);
      AutoBuy.ITEMS.remove(string);
      clickGuiState72.setFlag5(false);
      clickGuiState72.invoke66();
   }

   private void invoke53(
      RenderManager renderManager30, DrawContext drawContext, AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerDisplayEntry autoBuyModuleCardHandlerDisplayEntry11, float f, float g, float h, float i, float j, float k, float l, float m
   ) {
      if (autoBuyModuleCardHandlerDisplayEntry11 != null && !(i < 0.05F)) {
         if (!(f + h <= j) && !(g + h <= k) && !(f >= j + l) && !(g >= k + m)) {
            ItemStack itemStack9 = autoBuyModuleCardHandlerDisplayEntry11.custom() ? SpecialItemIconRenderer.resolve(autoBuyModuleCardHandlerDisplayEntry11.key()) : autoBuyModuleCardHandlerDisplayEntry11.stack();
            if (itemStack9 != null && !itemStack9.isEmpty()) {
               float floatValue387 = h / 16.0F;
               float floatValue388 = i < 0.95F ? i : Math.min(1.0F, 0.5F + 0.5F * i);
               if (floatValue388 >= 0.999F) {
                  ItemRenderUtil.invoke3(renderManager30, itemStack9, f, g, floatValue387, 0, false, 0);
               } else {
                  float floatValue389 = f + h * 0.5F;
                  float floatValue390 = g + h * 0.5F;
                  renderManager30.invoke62(floatValue388, floatValue389, floatValue390);

                  try {
                     ItemRenderUtil.invoke3(renderManager30, itemStack9, f, g, floatValue387, 0, false, 0);
                  } finally {
                     renderManager30.invoke64();
                  }
               }
            }
         }
      }
   }

   private float measure3(AutoBuy autoBuy20, AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerBounds autoBuyModuleCardHandlerBounds22, Metrics metrics46) {
      int intValue73 = this.compute2(autoBuyModuleCardHandlerBounds22, metrics46);
      int intValue74 = Math.max(1, (this.resolve8(autoBuy20, this.catalogSearch.value).size() + intValue73 - 1) / intValue73);
      float floatValue391 = intValue74 * this.measure9(metrics46) + Math.max(0, intValue74 - 1) * this.measure10(metrics46);
      return Math.max(0.0F, floatValue391 - autoBuyModuleCardHandlerBounds22.catalogViewportH());
   }

   private float measure4(AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerBounds autoBuyModuleCardHandlerBounds23, Metrics metrics47) {
      return this.measure5(autoBuyModuleCardHandlerBounds23, metrics47, null);
   }

   private float measure5(AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerBounds autoBuyModuleCardHandlerBounds24, Metrics metrics48, ClickGuiState clickGuiState73) {
      List items7 = this.resolve17();
      float floatValue392 = 0.0F;

      for (int intValue75 = 0; intValue75 < items7.size(); intValue75++) {
         floatValue392 += this.measure16(
            (String)items7.get(intValue75),
            metrics48,
            clickGuiState73 == null ? this.measure18((String)items7.get(intValue75)) : this.measure20(clickGuiState73, (String)items7.get(intValue75))
         );
         if (intValue75 < items7.size() - 1) {
            floatValue392 += this.measure21(metrics48);
         }
      }

      return Math.max(0.0F, floatValue392 - autoBuyModuleCardHandlerBounds24.rulesViewportH());
   }

   private float measure6(AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerBounds autoBuyModuleCardHandlerBounds25, Metrics metrics49) {
      float floatValue393 = metrics49.measure(42.0F);
      float floatValue394 = metrics49.measure(6.0F);
      float floatValue395 = metrics49.measure(42.0F);
      float floatValue396 = autoBuyModuleCardHandlerBounds25.panelH() - floatValue395 - metrics49.measure(10.0F);
      float floatValue397 = AutoBuy.ITEMS_2.size() * floatValue393 + Math.max(0, AutoBuy.ITEMS_2.size() - 1) * floatValue394;
      return Math.max(0.0F, floatValue397 - floatValue396);
   }

   private float measure7(AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerBounds autoBuyModuleCardHandlerBounds26, Metrics metrics50) {
      float floatValue398 = metrics50.measure(58.0F);
      float floatValue399 = metrics50.measure(8.0F);
      float floatValue400 = autoBuyModuleCardHandlerBounds26.panelH() - metrics50.measure(72.0F);
      float floatValue401 = this.items2.size() * floatValue398 + Math.max(0, this.items2.size() - 1) * floatValue399;
      return Math.max(0.0F, floatValue401 - floatValue400);
   }

   private int compute2(AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerBounds autoBuyModuleCardHandlerBounds27, Metrics metrics51) {
      float floatValue402 = this.measure8(metrics51);
      float floatValue403 = this.measure10(metrics51);
      return Math.max(1, (int)((autoBuyModuleCardHandlerBounds27.catalogViewportW() + floatValue403) / (floatValue402 + floatValue403)));
   }

   private float measure8(Metrics metrics52) {
      return metrics52.measure(72.0F);
   }

   private float measure9(Metrics metrics53) {
      return metrics53.measure(76.0F);
   }

   private float measure10(Metrics metrics54) {
      return metrics54.measure(8.0F);
   }

   private float measure11(AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerBounds autoBuyModuleCardHandlerBounds28, Metrics metrics55) {
      return autoBuyModuleCardHandlerBounds28.leftX() + metrics55.measure(10.0F);
   }

   private float measure12(AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerBounds autoBuyModuleCardHandlerBounds29, Metrics metrics56) {
      return autoBuyModuleCardHandlerBounds29.panelY() + metrics56.measure(45.0F);
   }

   private float measure13(AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerBounds autoBuyModuleCardHandlerBounds30, Metrics metrics57) {
      return Math.max(metrics57.measure(80.0F), autoBuyModuleCardHandlerBounds30.leftW() - metrics57.measure(20.0F));
   }

   private float measure14(Metrics metrics58) {
      return metrics58.measure(27.0F);
   }

   private void invoke54() {
      this.invoke57(this.typeUtils, 0.0F);
   }

   private void invoke55() {
      this.invoke57(this.typeUtils, 0.0F);
      this.invoke57(this.typeUtils2, 0.0F);
      this.invoke57(this.typeUtils3, 0.0F);
      this.invoke57(this.typeUtils4, 0.0F);
      this.autoBuyModuleCardHandlerScrollLayout = AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerScrollLayout.hidden();
      this.autoBuyModuleCardHandlerScrollLayout2 = AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerScrollLayout.hidden();
      this.autoBuyModuleCardHandlerScrollLayout3 = AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerScrollLayout.hidden();
      this.autoBuyModuleCardHandlerScrollLayout4 = AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerScrollLayout.hidden();
      this.flag3 = false;
      this.flag4 = false;
      this.flag5 = false;
      this.flag6 = false;
      this.floatValue11 = 0.0F;
      this.valuesByKey2.clear();
      this.text3 = null;
      this.text4 = null;
      this.valuesByKey.clear();
      this.text2 = null;
      this.intValue = -1;
      this.timedAnimation.setDoubleValue3(0.0);
      this.timedAnimation.invoke();
   }

   private float measure15(Metrics metrics59) {
      return metrics59.measure(62.2F);
   }

   private float measure16(String string, Metrics metrics60, float f) {
      return this.measure15(metrics60) + (metrics60.measure(6.0F) + this.measure17(string, metrics60)) * this.measure22(f, 0.0F, 1.0F);
   }

   private float measure17(String string, Metrics metrics61) {
      int intValue76 = this.resolve11(string).enchantments().size();
      if (intValue76 == 0) {
         return metrics61.measure(112.0F);
      } else {
         int intValue77 = (intValue76 + 1) / 2;
         return metrics61.measure(128.0F + intValue77 * 22.0F + Math.max(0, intValue77 - 1) * 4.0F + 14.0F);
      }
   }

   private float measure18(String string) {
      return string != null && string.equals(this.text3) && this.check8(string) ? 1.0F : 0.0F;
   }

   private float measure19(ClickGuiState clickGuiState74, String string) {
      return clickGuiState74.measure9(this.resolve21(string), this.measure18(string), SPRING_SPEC);
   }

   private float measure20(ClickGuiState clickGuiState75, String string) {
      return clickGuiState75.measure7(this.resolve21(string));
   }

   private String resolve21(String string) {
      return "ab:armor-settings:open:" + string;
   }

   private float measure21(Metrics metrics62) {
      return metrics62.measure(8.0F);
   }

   private String resolve22(long l) {
      return "$" + String.format(Locale.ROOT, "%,d", Math.max(0L, l)).replace(',', ' ');
   }

   private float measure22(float f, float g, float h) {
      return Math.max(g, Math.min(h, f));
   }

   private float measure23(TypeUtils typeUtils, float f) {
      typeUtils.setFloatValue3(-f);
      typeUtils.setFloatValue(this.measure22(typeUtils.getFloatValue(), -f, 0.0F));
      typeUtils.invoke();
      return typeUtils.measure();
   }

   private void invoke56(TypeUtils typeUtils2, float f, double d) {
      typeUtils2.setFloatValue3(-f);
      typeUtils2.invoke2(d);
      typeUtils2.setFloatValue(this.measure22(typeUtils2.getFloatValue(), -f, 0.0F));
   }

   private void invoke57(TypeUtils typeUtils3, float f) {
      typeUtils3.setFloatValue(f);
      typeUtils3.setFloatValue2(f);
   }

   record AutoBuyModuleCardHandlerDisplayEntry(String key, String label, ItemStack stack, boolean custom) {
   }

   record AutoBuyModuleCardHandlerTimedEntry(String name, String author, long timestamp) {
   }

   record AutoBuyModuleCardHandlerDisplayEntry2(String key, String label) {
   }

   record AutoBuyModuleCardHandlerBounds(
      float x,
      float y,
      float width,
      float height,
      float leftX,
      float rightX,
      float leftW,
      float rightW,
      float panelY,
      float panelH,
      float catalogViewportX,
      float catalogViewportY,
      float catalogViewportW,
      float catalogViewportH,
      float catalogScrollbarX,
      float rulesViewportX,
      float rulesViewportY,
      float rulesViewportW,
      float rulesViewportH,
      float rulesScrollbarX,
      float scrollbarW
   ) {
   }

   record AutoBuyModuleCardHandlerData(List<AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerDisplayEntry2> enchantments) {
   }

   record AutoBuyModuleCardHandlerData2(boolean visible, float chipX, float chipY, float chipW, float chipH, float fixX, float fixW, float statX, float statW) {
   }

   record AutoBuyModuleCardHandlerScrollLayout(float x, float y, float w, float h, float maxScroll, float thumbY, float thumbH, boolean visible) {
      static AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerScrollLayout hidden() {
         return hidden(0.0F, 0.0F, 0.0F, 0.0F);
      }

      static AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerScrollLayout hidden(float f, float g, float h, float i) {
         return new AutoBuyModuleCardHandler.AutoBuyModuleCardHandlerScrollLayout(f, g, h, i, 0.0F, g, 0.0F, false);
      }

      float travel() {
         return Math.max(0.0F, this.h - this.thumbH);
      }
   }

   record AutoBuyModuleCardHandlerData3(
      float stripH,
      float modeX,
      float modeY,
      float toggleX,
      float toggleW,
      float gap,
      float tabBtnSize,
      float chipW,
      boolean showReparse,
      float reparseX,
      float reparseW,
      float reparseToggleW,
      float reparseSliderX,
      float reparseSliderW
   ) {
   }
}
