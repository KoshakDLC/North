package ru.metaculture.protection;

import net.minecraft.client.MinecraftClient;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "Menu",
   description = "Настройки клиента",
   category = Category.Visuals
)
public class MenuModule extends Module {
   public static MenuModule instance;
   private static final float FLOAT_VALUE = 0.86F;
   private static final float FLOAT_VALUE_2 = 0.86F;
   public static final String GRAFIKA = "Графика";
   public static final String EFFEKTY = "Эффекты";
   public static final String TEMY = "Темы";
   public static final String PROIZVODITELNOST = "Производительность";
   public static final ModeSetting KATEGORIYA = new ModeSetting("Категория", "Графика", "Графика", "Эффекты", "Темы", "Производительность");
   public static final NumberSetting KACHESTVO_GRAFIKI = new NumberSetting("Качество графики", 2.0F, 0.0F, 4.0F, 1.0F, false)
      .setText2(GraphicsQuality.resolve())
      .setVisibilityCondition(() -> !KATEGORIYA.is("Графика"));
   public static final BooleanSetting PRIMENYAT_PRESET_AVTOMATICHESKI = new BooleanSetting("Применять пресет автоматически", true)
      .visibleWhen(() -> !KATEGORIYA.is("Графика"));
   public static final ModeSetting STIL_ANIMATSIY = new ModeSetting("Стиль анимаций", "Smooth", "Smooth", "Snappy", "Bouncy", "Cinematic", "Linear")
      .setVisibilityCondition(() -> !KATEGORIYA.is("Графика"));
   public static final NumberSetting MASSHTAB_GUI = new NumberSetting("Масштаб GUI", 0.86F, 0.55F, 1.7F, 0.01F, false).setVisibilityCondition(() -> true);
   public static final NumberSetting MASSHTAB_PANELI_TEMY = new NumberSetting("Масштаб панели темы", 0.86F, 0.55F, 1.7F, 0.01F, false).setVisibilityCondition(() -> true);
   public static final BooleanSetting VOLNY_KLIKA = new BooleanSetting("Волны клика", true).visibleWhen(() -> !KATEGORIYA.is("Эффекты"));
   public static final BooleanSetting VOLNY_TEMY = new BooleanSetting("Волны темы", true).visibleWhen(() -> !KATEGORIYA.is("Эффекты"));
   public static final BooleanSetting UDARNAYA_VOLNA_TEMY = new BooleanSetting("Ударная волна темы", true).visibleWhen(() -> !KATEGORIYA.is("Эффекты"));
   public static final BooleanSetting RAZMYTIE_SKROLLA = new BooleanSetting("Размытие скролла", true).visibleWhen(() -> !KATEGORIYA.is("Эффекты"));
   public static final BooleanSetting PEREHODY_KART = new BooleanSetting("Переходы карт", true).visibleWhen(() -> !KATEGORIYA.is("Эффекты"));
   public static final BooleanSetting PEREHODY_EKRANA = new BooleanSetting("Переходы экрана", true).visibleWhen(() -> !KATEGORIYA.is("Эффекты"));
   public static final BooleanSetting DREYF_TSVETA_TEMY = new BooleanSetting("Дрейф цвета темы", true).visibleWhen(() -> !KATEGORIYA.is("Эффекты"));
   public static final BooleanSetting VNUTRENNEE_SVECHENIE = new BooleanSetting("Внутреннее свечение", true).visibleWhen(() -> !KATEGORIYA.is("Эффекты"));
   public static final BooleanSetting ZERNO_PLYONKI = new BooleanSetting("Зерно плёнки", true).visibleWhen(() -> !KATEGORIYA.is("Эффекты"));
   public static final BooleanSetting PULSATSIYA_HOTBARA = new BooleanSetting("Пульсация хотбара", true).visibleWhen(() -> !KATEGORIYA.is("Эффекты"));
   public static final BooleanSetting ANIMATSII_STATUSOV = new BooleanSetting("Анимации статусов", true).visibleWhen(() -> !KATEGORIYA.is("Эффекты"));
   public static final BooleanSetting VSPYSHKA_URONA = new BooleanSetting("Вспышка урона", true).visibleWhen(() -> !KATEGORIYA.is("Эффекты"));
   public static final BooleanSetting PULSATSIYA_REGENERATSII = new BooleanSetting("Пульсация регенерации", true).visibleWhen(() -> !KATEGORIYA.is("Эффекты"));
   public static final BooleanSetting TRYASKA_PRI_NIZKOM_ZDOROVE = new BooleanSetting("Тряска при низком здоровье", true)
      .visibleWhen(() -> !KATEGORIYA.is("Эффекты"));
   public static final BooleanSetting SLED_KURSORA_VMENYU = new BooleanSetting("След курсора в меню", true).visibleWhen(() -> !KATEGORIYA.is("Эффекты"));
   public static final BooleanSetting PARALLAKS_GLAVNOGO_MENYU = new BooleanSetting("Параллакс главного меню", true)
      .visibleWhen(() -> !KATEGORIYA.is("Эффекты"));
   public static final ColorSetting AKTSENT_TEMY = new ColorSetting("Акцент темы", 66.0F, 0.64F, 1.0F).setVisibilityCondition(() -> !KATEGORIYA.is("Темы"));
   public static final ColorSetting TSVET_PANELI = new ColorSetting("Цвет панели", 68.0F, 0.28F, 0.08F).setVisibilityCondition(() -> !KATEGORIYA.is("Темы"));
   public static final ColorSetting TSVET_POVERHNOSTI = new ColorSetting("Цвет поверхности", 68.0F, 0.24F, 0.12F)
      .setVisibilityCondition(() -> !KATEGORIYA.is("Темы"));
   public static final ColorSetting TSVET_OBVODKI = new ColorSetting("Цвет обводки", 68.0F, 0.32F, 0.38F).setVisibilityCondition(() -> !KATEGORIYA.is("Темы"));
   public static final ColorSetting TSVET_TEKSTA = new ColorSetting("Цвет текста", 0.0F, 0.0F, 1.0F).setVisibilityCondition(() -> !KATEGORIYA.is("Темы"));
   public static final ColorSetting TSVET_PRIGLUSHYONNOGO_TEKSTA = new ColorSetting("Цвет приглушённого текста", 68.0F, 0.14F, 0.62F)
      .setVisibilityCondition(() -> !KATEGORIYA.is("Темы"));
   public static final String STANDART = "Стандарт";
   public static final String GOLOGRAMMA = "Голограмма";
   public static final ModeSetting FON_CLICKGUI = new ModeSetting("Фон ClickGUI", "Голограмма", "Стандарт", "Голограмма")
      .setVisibilityCondition(() -> !KATEGORIYA.is("Темы"));
   public static final FoundryShaderSetting FOUNDRY_SHADER = new FoundryShaderSetting("Foundry Shader", ShaderSurface.BACKGROUND)
      .setVisibilityCondition(() -> !KATEGORIYA.is("Темы"));
   public static final NumberSetting MAKSIMALNYY_BLYUR = new NumberSetting("Максимальный блюр", 32.0F, 8.0F, 64.0F, 1.0F, false)
      .setVisibilityCondition(() -> !KATEGORIYA.is("Темы") || !FON_CLICKGUI.is("Голограмма"));
   public static final NumberSetting IRIDISTSENTNYY_OTLIV = new NumberSetting("Иридисцентный отлив", 0.6F, 0.0F, 1.0F, 0.01F, true)
      .setVisibilityCondition(() -> !KATEGORIYA.is("Темы") || !FON_CLICKGUI.is("Голограмма"));
   public static final NumberSetting PRITYAZHENIE_KKURSORU = new NumberSetting("Притяжение к курсору", 0.18F, 0.0F, 0.4F, 0.01F, false)
      .setVisibilityCondition(() -> !KATEGORIYA.is("Темы") || !FON_CLICKGUI.is("Голограмма"));
   public static final NumberSetting RADIUS_PROZRACHNOSTI_UKURSORA = new NumberSetting("Радиус прозрачности у курсора", 0.28F, 0.05F, 0.6F, 0.01F, false)
      .setVisibilityCondition(() -> !KATEGORIYA.is("Темы") || !FON_CLICKGUI.is("Голограмма"));
   public static final NumberSetting RAZMER_OSTROVKOV = new NumberSetting("Размер островков", 1.8F, 0.8F, 3.5F, 0.05F, false)
      .setVisibilityCondition(() -> !KATEGORIYA.is("Темы") || !FON_CLICKGUI.is("Голограмма"));
   public static final NumberSetting SKOROST_TECHENIYA = new NumberSetting("Скорость течения", 0.55F, 0.0F, 1.5F, 0.01F, false)
      .setVisibilityCondition(() -> !KATEGORIYA.is("Темы") || !FON_CLICKGUI.is("Голограмма"));
   public static final NumberSetting KONTRAST_OSTROVKOV = new NumberSetting("Контраст островков", 0.55F, 0.0F, 1.0F, 0.01F, true)
      .setVisibilityCondition(() -> !KATEGORIYA.is("Темы") || !FON_CLICKGUI.is("Голограмма"));
   public static final NumberSetting VINETKA = new NumberSetting("Виньетка", 0.35F, 0.0F, 1.0F, 0.01F, true)
      .setVisibilityCondition(() -> !KATEGORIYA.is("Темы") || !FON_CLICKGUI.is("Голограмма"));
   public static final NumberSetting YARKOST = new NumberSetting("Яркость", 0.55F, 0.0F, 1.0F, 0.01F, true)
      .setVisibilityCondition(() -> !KATEGORIYA.is("Темы") || !FON_CLICKGUI.is("Голограмма"));
   public static final NumberSetting NASYSCHENNOST = new NumberSetting("Насыщенность", 0.45F, 0.0F, 1.0F, 0.01F, true)
      .setVisibilityCondition(() -> !KATEGORIYA.is("Темы") || !FON_CLICKGUI.is("Голограмма"));
   public static final BooleanSetting UPROSCHYONNYE_TENI_HUD = new BooleanSetting("Упрощённые тени HUD", false)
      .visibleWhen(() -> !KATEGORIYA.is("Производительность"));
   public static final BooleanSetting OTKLYUCHIT_BLYUR = new BooleanSetting("Отключить блюр", false)
      .visibleWhen(() -> !KATEGORIYA.is("Производительность"));
   public static final BooleanSetting BYSTRYE_ANIMATSII = new BooleanSetting("Быстрые анимации", false)
      .visibleWhen(() -> !KATEGORIYA.is("Производительность"));
   public static final BooleanSetting PROPUSKAT_CHASTITSY_KLIENTA = new BooleanSetting("Пропускать частицы клиента", false)
      .visibleWhen(() -> !KATEGORIYA.is("Производительность"));
   private static int intValue = -1;
   public static final BooleanSetting AUTO_GUI_SCALE_INITIALIZED = new BooleanSetting("Auto GUI scale initialized", false).visibleWhen(() -> true);

   public MenuModule() {
      instance = this;
      this.displayName = "Menu";
      this.bindKey = 344;
      this.addSettings(
         new Setting[]{
            KATEGORIYA,
            KACHESTVO_GRAFIKI,
            PRIMENYAT_PRESET_AVTOMATICHESKI,
            STIL_ANIMATSIY,
            MASSHTAB_GUI,
            MASSHTAB_PANELI_TEMY,
            AUTO_GUI_SCALE_INITIALIZED,
            VOLNY_KLIKA,
            VOLNY_TEMY,
            UDARNAYA_VOLNA_TEMY,
            RAZMYTIE_SKROLLA,
            PEREHODY_KART,
            PEREHODY_EKRANA,
            DREYF_TSVETA_TEMY,
            VNUTRENNEE_SVECHENIE,
            ZERNO_PLYONKI,
            PULSATSIYA_HOTBARA,
            ANIMATSII_STATUSOV,
            VSPYSHKA_URONA,
            PULSATSIYA_REGENERATSII,
            TRYASKA_PRI_NIZKOM_ZDOROVE,
            SLED_KURSORA_VMENYU,
            PARALLAKS_GLAVNOGO_MENYU,
            AKTSENT_TEMY,
            TSVET_PANELI,
            TSVET_POVERHNOSTI,
            TSVET_OBVODKI,
            TSVET_TEKSTA,
            TSVET_PRIGLUSHYONNOGO_TEKSTA,
            FON_CLICKGUI,
            FOUNDRY_SHADER,
            MAKSIMALNYY_BLYUR,
            IRIDISTSENTNYY_OTLIV,
            PRITYAZHENIE_KKURSORU,
            RADIUS_PROZRACHNOSTI_UKURSORA,
            RAZMER_OSTROVKOV,
            SKOROST_TECHENIYA,
            KONTRAST_OSTROVKOV,
            VINETKA,
            YARKOST,
            NASYSCHENNOST,
            UPROSCHYONNYE_TENI_HUD,
            OTKLYUCHIT_BLYUR,
            BYSTRYE_ANIMATSII,
            PROPUSKAT_CHASTITSY_KLIENTA
         }
      );
   }

   public static boolean check(BooleanSetting booleanSetting) {
      invoke();

      try {
         return booleanSetting == null || booleanSetting.isEnabled();
      } catch (Throwable exception) {
         return true;
      }
   }

   public static void invoke() {
      try {
         if (!PRIMENYAT_PRESET_AVTOMATICHESKI.isEnabled()) {
            intValue = (int)KACHESTVO_GRAFIKI.value;
            return;
         }

         int intValue = Math.round(KACHESTVO_GRAFIKI.value);
         if (intValue != intValue) {
            MenuModule.intValue = intValue;
            GraphicsQuality.resolve2(intValue).invoke();
         }
      } catch (Throwable exception2) {
      }
   }

   public static GraphicsQuality resolve() {
      return GraphicsQuality.resolve2(Math.round(KACHESTVO_GRAFIKI.value));
   }

   public static void invoke2(int i) {
      int intValue2 = Math.max(0, Math.min(GraphicsQuality.values().length - 1, i));
      KACHESTVO_GRAFIKI.value = intValue2;
      if (PRIMENYAT_PRESET_AVTOMATICHESKI.isEnabled()) {
         intValue = intValue2;
         GraphicsQuality.resolve2(intValue2).invoke();
      }
   }

   public static void invoke3() {
      int intValue3 = GraphicsQuality.ULTRA.ordinal();
      KACHESTVO_GRAFIKI.value = intValue3;
      intValue = intValue3;
   }

   public static MenuModule getInstance() {
      return instance;
   }

   public static void invoke4() {
      Theme.CUSTOM
         .invoke(
            AKTSENT_TEMY.getColor(),
            TSVET_PANELI.getColor(),
            TSVET_POVERHNOSTI.getColor(),
            TSVET_OBVODKI.getColor(),
            TSVET_TEKSTA.getColor(),
            TSVET_PRIGLUSHYONNOGO_TEKSTA.getColor()
         );
   }

   public static void invoke5(MinecraftClient minecraftClient, LayoutSpec layoutSpec) {
      if (AUTO_GUI_SCALE_INITIALIZED != null && !AUTO_GUI_SCALE_INITIALIZED.isEnabled()) {
         if (minecraftClient != null && minecraftClient.getWindow() != null && layoutSpec != null) {
            int intValue4 = minecraftClient.getWindow().getFramebufferWidth();
            int intValue5 = minecraftClient.getWindow().getFramebufferHeight();
            if (intValue4 > 0 && intValue5 > 0) {
               if (!(Math.abs(MASSHTAB_GUI.getValue() - 0.86F) > 0.005F) && !(Math.abs(MASSHTAB_PANELI_TEMY.getValue() - 0.86F) > 0.005F)) {
                  float floatValue = measure(minecraftClient);
                  float floatValue2 = Math.min(intValue4, intValue5);
                  float floatValue3 = measure2(floatValue2 * 0.025F, 18.0F, 42.0F);
                  float floatValue4 = Math.min((intValue4 - floatValue3 * 2.0F) / layoutSpec.getFloatValue(), (intValue5 - floatValue3 * 2.0F) / layoutSpec.getFloatValue2());
                  float floatValue5 = intValue4 / Math.max(1.0F, (float)intValue5);
                  float floatValue6 = floatValue5 > 2.05F ? 0.58F : (floatValue5 < 1.45F ? 0.74F : 0.68F);
                  float floatValue7 = floatValue5 > 2.05F ? 0.8F : 0.76F;
                  float floatValue8 = Math.min(intValue4 * floatValue6 / layoutSpec.getFloatValue(), intValue5 * floatValue7 / layoutSpec.getFloatValue2());
                  floatValue8 = measure2(Math.min(floatValue8, floatValue4), layoutSpec.getFloatValue18(), layoutSpec.getFloatValue19());
                  float floatValue9 = measure2(floatValue8 / Math.max(0.001F, floatValue), MASSHTAB_GUI.minimum, MASSHTAB_GUI.maximum);
                  MASSHTAB_GUI.invoke(floatValue9);
                  MASSHTAB_PANELI_TEMY.invoke(measure2(floatValue9 * 0.94F, MASSHTAB_PANELI_TEMY.minimum, MASSHTAB_PANELI_TEMY.maximum));
                  AUTO_GUI_SCALE_INITIALIZED.setValue(true);
                  invoke6();
               } else {
                  AUTO_GUI_SCALE_INITIALIZED.setValue(true);
                  invoke6();
               }
            }
         }
      }
   }

   private static float measure(MinecraftClient minecraftClient) {
      float floatValue10;
      try {
         floatValue10 = Math.max(1.0F, (float)minecraftClient.getWindow().getScaleFactor());
      } catch (Throwable exception3) {
         int intValue6 = Math.max(1, minecraftClient.getWindow().getScaledWidth());
         floatValue10 = Math.max(1.0F, (float)minecraftClient.getWindow().getFramebufferWidth() / intValue6);
      }

      return 0.68F + Math.min(floatValue10, 2.0F) * 0.28F;
   }

   private static float measure2(float f, float g, float h) {
      return Math.max(g, Math.min(h, f));
   }

   private static void invoke6() {
      if (WildClient.INSTANCE != null && WildClient.INSTANCE.configManager != null) {
         WildClient.INSTANCE.configManager.scheduleSave();
      }
   }
}
