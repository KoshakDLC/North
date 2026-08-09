package ru.metaculture.protection;

import java.util.Locale;
import net.minecraft.client.toast.AdvancementToast;
import net.minecraft.client.toast.NowPlayingToast;
import net.minecraft.client.toast.RecipeToast;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.TutorialToast;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "Removals",
   description = "Гибкое отключение мешающих оверлеев, эффектов, частиц и звуков",
   category = Category.Misc
)
public class Removals extends Module {
   public static final String OGON = "Огонь";
   public static final String VODA = "Вода";
   public static final String STENA_VGLAZAH = "Стена в глазах";
   public static final String TYKVA = "Тыква";
   public static final String POROSHKOVYY_SNEG = "Порошковый снег";
   public static final String PODZORNAYA_TRUBA = "Подзорная труба";
   public static final String PORTAL = "Портал";
   public static final String TOSHNOTA_EKRAN = "Тошнота (экран)";
   public static final String VINETKA = "Виньетка";
   public static final String TRYASKA_OT_URONA = "Тряска от урона";
   public static final String TMA = "Тьма";
   public static final String SLEPOTA = "Слепота";
   public static final String TOSHNOTA_EFFEKT = "Тошнота (эффект)";
   public static final String TUMAN = "Туман";
   public static final String IKONKI_EFFEKTOV = "Иконки эффектов";
   public static final String VZRYVY = "Взрывы";
   public static final String TOTEM = "Тотем";
   public static final String EFFEKTY_ZELIY = "Эффекты зелий";
   public static final String KRITY_IUDARY = "Криты и удары";
   public static final String CHARY_STOLA = "Чары стола";
   public static final String KAPLI_IVODA = "Капли и вода";
   public static final String REDSTOUN = "Редстоун";
   public static final String DYM_IOGON = "Дым и огонь";
   public static final String SERDECHKI_IDEREVNYA = "Сердечки и деревня";
   public static final String PORTALY_CHASTITSY = "Порталы (частицы)";
   public static final String FEYERVERKI_CHASTITSY = "Фейерверки (частицы)";
   public static final String DRAKON_ISKALK = "Дракон и скалк";
   public static final String PRIRODNYY_EMBIENT = "Природный эмбиент";
   public static final String VETROZARYAD = "Ветрозаряд";
   public static final String VZRYVY_ZVUK = "Взрывы (звук)";
   public static final String PORSHNI = "Поршни";
   public static final String VODA_ILAVA = "Вода и лава";
   public static final String EMBIENT_PESCHERY_MOBY = "Эмбиент (пещеры, мобы)";
   public static final String PORTALY_ZVUK = "Порталы (звук)";
   public static final String MAYAK = "Маяк";
   public static final String OPYT_IUROVEN = "Опыт и уровень";
   public static final String PODBOR_PREDMETOV = "Подбор предметов";
   public static final String FEYERVERKI_ZVUK = "Фейерверки (звук)";
   public static final String NOT_BLOKI = "Нот-блоки";
   public static final String DVERI_IKONTEYNERY = "Двери и контейнеры";
   public static final String GROM_IMOLNIYA = "Гром и молния";
   public static final String KOLOKOL = "Колокол";
   public static final String TOTEMY_ZVUK = "Тотемы (звук)";
   public static final String NAKOVALNYA = "Наковальня";
   public static final String ELITRA = "Элитра";
   public static final String BOSSY_RYOV = "Боссы (рёв)";
   public static final String VETROZARYAD_IMEYS = "Ветрозаряд и Мейс";
   public static final String TRAVA = "Трава";
   public static final String RASTENIYA_ITSVETY = "Растения и цветы";
   public static final String LISTVA = "Листва";
   public static final String SNEG_POKROV = "Снег (покров)";
   public static final String STOYKI_BRONI = "Стойки брони";
   public static final String RAMKI = "Рамки";
   public static final String KARTINY = "Картины";
   public static final String DROP_PREDMETOV = "Дроп предметов";
   public static final String OPYT_ORBY = "Опыт-орбы";
   public static final String POGODA_DOZHD_SNEG = "Погода (дождь/снег)";
   public static final String DIKTOR = "Диктор";
   public static final String TOSTY_IACHIVKI = "Тосты и ачивки";
   public static final String ANIMATSIYA_TOTEMA = "Анимация тотема";
   public static final GroupSetting OVERLEI_EKRANA = new GroupSetting(
      "Оверлеи экрана",
      new BooleanSetting("Огонь", false),
      new BooleanSetting("Вода", false),
      new BooleanSetting("Стена в глазах", false),
      new BooleanSetting("Тыква", false),
      new BooleanSetting("Порошковый снег", false),
      new BooleanSetting("Подзорная труба", false),
      new BooleanSetting("Портал", false),
      new BooleanSetting("Тошнота (экран)", false),
      new BooleanSetting("Виньетка", false),
      new BooleanSetting("Тряска от урона", false)
   );
   public static final GroupSetting EFFEKTY_ITUMAN = new GroupSetting(
      "Эффекты и туман",
      new BooleanSetting("Тьма", false),
      new BooleanSetting("Слепота", false),
      new BooleanSetting("Тошнота (эффект)", false),
      new BooleanSetting("Туман", false),
      new BooleanSetting("Иконки эффектов", false)
   );
   public static final GroupSetting CHASTITSY = new GroupSetting(
      "Частицы",
      new BooleanSetting("Взрывы", false),
      new BooleanSetting("Тотем", false),
      new BooleanSetting("Эффекты зелий", false),
      new BooleanSetting("Криты и удары", false),
      new BooleanSetting("Чары стола", false),
      new BooleanSetting("Капли и вода", false),
      new BooleanSetting("Редстоун", false),
      new BooleanSetting("Дым и огонь", false),
      new BooleanSetting("Сердечки и деревня", false),
      new BooleanSetting("Порталы (частицы)", false),
      new BooleanSetting("Фейерверки (частицы)", false),
      new BooleanSetting("Дракон и скалк", false),
      new BooleanSetting("Природный эмбиент", false),
      new BooleanSetting("Ветрозаряд", false)
   );
   public static final GroupSetting ZVUKI = new GroupSetting(
      "Звуки",
      new BooleanSetting("Взрывы (звук)", false),
      new BooleanSetting("Поршни", false),
      new BooleanSetting("Вода и лава", false),
      new BooleanSetting("Эмбиент (пещеры, мобы)", false),
      new BooleanSetting("Порталы (звук)", false),
      new BooleanSetting("Маяк", false),
      new BooleanSetting("Опыт и уровень", false),
      new BooleanSetting("Подбор предметов", false),
      new BooleanSetting("Фейерверки (звук)", false),
      new BooleanSetting("Нот-блоки", false),
      new BooleanSetting("Двери и контейнеры", false),
      new BooleanSetting("Гром и молния", false),
      new BooleanSetting("Колокол", false),
      new BooleanSetting("Тотемы (звук)", false),
      new BooleanSetting("Наковальня", false),
      new BooleanSetting("Элитра", false),
      new BooleanSetting("Боссы (рёв)", false),
      new BooleanSetting("Ветрозаряд и Мейс", false)
   );
   public static final GroupSetting MIR_ISUSCHNOSTI = new GroupSetting(
      "Мир и сущности",
      new BooleanSetting("Трава", true),
      new BooleanSetting("Растения и цветы", true),
      new BooleanSetting("Листва", false),
      new BooleanSetting("Снег (покров)", false),
      new BooleanSetting("Стойки брони", true),
      new BooleanSetting("Рамки", true),
      new BooleanSetting("Картины", true),
      new BooleanSetting("Дроп предметов", false),
      new BooleanSetting("Опыт-орбы", false),
      new BooleanSetting("Погода (дождь/снег)", false)
   );
   public static final GroupSetting INTERFEYS = new GroupSetting(
      "Интерфейс", new BooleanSetting("Диктор", true), new BooleanSetting("Тосты и ачивки", false), new BooleanSetting("Анимация тотема", false)
   );
   public static final BooleanSetting NE_SKRYVAT_KARTY = new BooleanSetting("Не скрывать карты", true).visibleWhen(() -> !MIR_ISUSCHNOSTI.isEnabled("Рамки"));
   public static final TextSetting SVOI_ZVUKI_CHEREZ_ZAPYATUYU = new TextSetting("Свои звуки (через запятую)", "").resolve(512);
   public static final TextSetting SVOI_CHASTITSY_CHEREZ_ZAPYATUYU = new TextSetting("Свои частицы (через запятую)", "").resolve(512);
   private static final Removals.RemovalsState[] REMOVALS_STATES = new Removals.RemovalsState[]{
      new Removals.RemovalsState("Взрывы (звук)", "explode"),
      new Removals.RemovalsState("Поршни", "piston"),
      new Removals.RemovalsState("Вода и лава", "water", "lava", "bubble", "splash", "swim"),
      new Removals.RemovalsState("Эмбиент (пещеры, мобы)", "ambient"),
      new Removals.RemovalsState("Порталы (звук)", "portal"),
      new Removals.RemovalsState("Маяк", "beacon"),
      new Removals.RemovalsState("Опыт и уровень", "experience_orb", "levelup"),
      new Removals.RemovalsState("Подбор предметов", "item.pickup"),
      new Removals.RemovalsState("Фейерверки (звук)", "firework"),
      new Removals.RemovalsState("Нот-блоки", "note_block"),
      new Removals.RemovalsState("Двери и контейнеры", "door", "chest", "barrel", "shulker_box", "ender_chest"),
      new Removals.RemovalsState("Гром и молния", "thunder", "lightning"),
      new Removals.RemovalsState("Колокол", "bell"),
      new Removals.RemovalsState("Тотемы (звук)", "totem"),
      new Removals.RemovalsState("Наковальня", "anvil"),
      new Removals.RemovalsState("Элитра", "elytra"),
      new Removals.RemovalsState("Боссы (рёв)", "wither.spawn", "wither.death", "ender_dragon.death", "ender_dragon.growl"),
      new Removals.RemovalsState("Ветрозаряд и Мейс", "wind_charge", "breeze", "mace.smash")
   };
   private static final Removals.RemovalsState[] REMOVALS_STATES_2 = new Removals.RemovalsState[]{
      new Removals.RemovalsState("Взрывы", "explosion"),
      new Removals.RemovalsState("Тотем", "totem_of_undying"),
      new Removals.RemovalsState("Эффекты зелий", "effect"),
      new Removals.RemovalsState("Криты и удары", "crit", "enchanted_hit", "sweep_attack", "damage_indicator"),
      new Removals.RemovalsState("Чары стола", "enchant", "nautilus").setText3(new String[]{"enchanted_hit"}),
      new Removals.RemovalsState("Капли и вода", "water", "splash", "bubble", "fishing", "rain", "lava"),
      new Removals.RemovalsState("Редстоун", "dust").setText3(new String[]{"falling_dust"}),
      new Removals.RemovalsState("Дым и огонь", "smoke", "flame", "campfire", "spark"),
      new Removals.RemovalsState("Сердечки и деревня", "heart", "angry_villager", "happy_villager"),
      new Removals.RemovalsState("Порталы (частицы)", "portal"),
      new Removals.RemovalsState("Фейерверки (частицы)", "firework", "flash"),
      new Removals.RemovalsState("Дракон и скалк", "sculk", "dragon_breath", "sonic_boom", "shriek", "vibration"),
      new Removals.RemovalsState("Природный эмбиент", "white_ash", "spore", "mycelium", "leaves", "snowflake", "cherry"),
      new Removals.RemovalsState("Ветрозаряд", "gust")
   };
   private static Removals instance;
   private int intValue = -1;

   public Removals() {
      instance = this;
      this.addSettings(
         new Setting[]{OVERLEI_EKRANA, EFFEKTY_ITUMAN, CHASTITSY, ZVUKI, MIR_ISUSCHNOSTI, NE_SKRYVAT_KARTY, INTERFEYS, SVOI_ZVUKI_CHEREZ_ZAPYATUYU, SVOI_CHASTITSY_CHEREZ_ZAPYATUYU}
      );
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.intValue = this.compute();
      this.invoke();
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.invoke();
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      int intValue = this.compute();
      if (intValue != this.intValue) {
         this.intValue = intValue;
         this.invoke();
      }
   }

   private int compute() {
      if (!this.enabled) {
         return 0;
      } else {
         byte byteValue = 1;
         if (MIR_ISUSCHNOSTI.isEnabled("Трава")) {
            byteValue |= 2;
         }

         if (MIR_ISUSCHNOSTI.isEnabled("Растения и цветы")) {
            byteValue |= 4;
         }

         if (MIR_ISUSCHNOSTI.isEnabled("Листва")) {
            byteValue |= 8;
         }

         if (MIR_ISUSCHNOSTI.isEnabled("Снег (покров)")) {
            byteValue |= 16;
         }

         return byteValue;
      }
   }

   private void invoke() {
      if (!WildClient.isShuttingDown() && CLIENT != null && CLIENT.worldRenderer != null && CLIENT.world != null) {
         CLIENT.worldRenderer.reload();
      }
   }

   public static boolean check(String string) {
      return !check11()
         ? false
         : OVERLEI_EKRANA.isEnabled(string) || EFFEKTY_ITUMAN.isEnabled(string) || MIR_ISUSCHNOSTI.isEnabled(string) || INTERFEYS.isEnabled(string);
   }

   public static boolean check2(String string) {
      return check11() && MIR_ISUSCHNOSTI.isEnabled(string);
   }

   public static boolean check3(RegistryEntry<StatusEffect> registryEntry) {
      if (!check11()) {
         return false;
      } else if (registryEntry == StatusEffects.DARKNESS) {
         return EFFEKTY_ITUMAN.isEnabled("Тьма");
      } else if (registryEntry == StatusEffects.BLINDNESS) {
         return EFFEKTY_ITUMAN.isEnabled("Слепота");
      } else {
         return registryEntry == StatusEffects.NAUSEA ? EFFEKTY_ITUMAN.isEnabled("Тошнота (эффект)") : false;
      }
   }

   public static boolean check4(ParticleEffect particleEffect) {
      return check11() && CHASTITSY.isEnabled("Капли и вода");
   }

   public static boolean check5(Identifier identifier) {
      if (check11() && identifier != null && check10()) {
         String text = identifier.getPath();

         for (Removals.RemovalsState removalsState : REMOVALS_STATES) {
            if (ZVUKI.isEnabled(removalsState.text) && removalsState.check(text)) {
               return true;
            }
         }

         return check8(SVOI_ZVUKI_CHEREZ_ZAPYATUYU.getValue(), identifier);
      } else {
         return false;
      }
   }

   public static boolean check6(ParticleEffect particleEffect) {
      if (check11() && particleEffect != null && check9()) {
         Identifier identifier2 = Registries.PARTICLE_TYPE.getId(particleEffect.getType());
         if (identifier2 == null) {
            return false;
         } else {
            String text2 = identifier2.getPath();

            for (Removals.RemovalsState removalsState2 : REMOVALS_STATES_2) {
               if (CHASTITSY.isEnabled(removalsState2.text) && removalsState2.check(text2)) {
                  return true;
               }
            }

            return check8(SVOI_CHASTITSY_CHEREZ_ZAPYATUYU.getValue(), identifier2);
         }
      } else {
         return false;
      }
   }

   public static boolean check7(Toast toast) {
      return check11() && toast != null && INTERFEYS.isEnabled("Тосты и ачивки")
         ? toast instanceof AdvancementToast || toast instanceof RecipeToast || toast instanceof TutorialToast || toast instanceof NowPlayingToast
         : false;
   }

   public static boolean shouldBlockNarratorHotkey() {
      return check11() && INTERFEYS.isEnabled("Диктор");
   }

   private static boolean check8(String string, Identifier identifier) {
      if (string != null && !string.isBlank()) {
         String text3 = identifier.toString();

         for (String text4 : string.toLowerCase(Locale.ROOT).split(",")) {
            String text5 = text4.trim();
            if (!text5.isEmpty() && text3.contains(text5)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private static boolean check9() {
      for (BooleanSetting booleanSetting : CHASTITSY.options) {
         if (booleanSetting.isEnabled()) {
            return true;
         }
      }

      return !SVOI_CHASTITSY_CHEREZ_ZAPYATUYU.getValue().isBlank();
   }

   private static boolean check10() {
      for (BooleanSetting booleanSetting2 : ZVUKI.options) {
         if (booleanSetting2.isEnabled()) {
            return true;
         }
      }

      return !SVOI_ZVUKI_CHEREZ_ZAPYATUYU.getValue().isBlank();
   }

   private static boolean check11() {
      Removals removals = resolve();
      return removals != null && removals.enabled;
   }

   private static Removals resolve() {
      if (instance != null) {
         return instance;
      } else {
         return WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null ? WildClient.INSTANCE.moduleManager.getModule(Removals.class) : null;
      }
   }

   static final class RemovalsState {
      final String text;
      final String[] text2;
      String[] text3 = new String[0];

      RemovalsState(String string, String... strings) {
         this.text = string;
         this.text2 = strings;
      }

      Removals.RemovalsState setText3(String... strings) {
         this.text3 = strings;
         return this;
      }

      boolean check(String string) {
         for (String text6 : this.text3) {
            if (string.contains(text6)) {
               return false;
            }
         }

         for (String text7 : this.text2) {
            if (string.contains(text7)) {
               return true;
            }
         }

         return false;
      }
   }
}
