package ru.metaculture.protection;

import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.option.Perspective;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "Animations",
   category = Category.Visuals,
   description = "Анимки на все действия, таб, открытие инва итд"
)
public class Animations extends Module {
   private static final long TIMESTAMP = 240L;
   private static final long TIMESTAMP_2 = 220L;
   private static final long TIMESTAMP_3 = 300L;
   private static final long TIMESTAMP_4 = 240L;
   private static final long TIMESTAMP_5 = 90L;
   private static final long TIMESTAMP_6 = 120L;
   private static final long TIMESTAMP_7 = 260L;
   public final GroupSetting animirovat = new GroupSetting(
      "Анимировать",
      new BooleanSetting("Чат", false),
      new BooleanSetting("Таб", false),
      new BooleanSetting("Инвентарь", false),
      new BooleanSetting("Сундуки", false),
      new BooleanSetting("Кнопки", false),
      new BooleanSetting("F5", false)
   );
   public final ModeSetting rezhimAnimatsii = new ModeSetting(
      "Режим анимации",
      "Ease Out Back",
      "Linear",
      "Ease Out Quad",
      "Ease Out Cubic",
      "Ease Out Quart",
      "Ease Out Expo",
      "Ease Out Back",
      "Ease Out Elastic",
      "Ease Out Bounce",
      "Shrink Easing"
   );
   public final NumberSetting skorostChata = new NumberSetting("Скорость чата", 1.0F, 0.1F, 3.0F, 0.1F, false)
      .setVisibilityCondition(() -> !this.animirovat.isEnabled("Чат"));
   public final NumberSetting skorostTaba = new NumberSetting("Скорость таба", 1.0F, 0.1F, 3.0F, 0.1F, false)
      .setVisibilityCondition(() -> !this.animirovat.isEnabled("Таб"));
   public final NumberSetting skorostInventarya = new NumberSetting("Скорость инвентаря", 1.0F, 0.1F, 3.0F, 0.1F, false)
      .setVisibilityCondition(() -> !this.animirovat.isEnabled("Инвентарь"));
   public final NumberSetting skorostSundukov = new NumberSetting("Скорость сундуков", 1.0F, 0.1F, 3.0F, 0.1F, false)
      .setVisibilityCondition(() -> !this.animirovat.isEnabled("Сундуки"));
   public final NumberSetting skorostKnopok = new NumberSetting("Скорость кнопок", 1.0F, 0.1F, 3.0F, 0.1F, false)
      .setVisibilityCondition(() -> !this.animirovat.isEnabled("Кнопки"));
   public final NumberSetting skorostF5 = new NumberSetting("Скорость F5", 1.0F, 0.1F, 3.0F, 0.1F, false)
      .setVisibilityCondition(() -> !this.animirovat.isEnabled("F5"));
   public TimedAnimation timedAnimation;
   public TimedAnimation timedAnimation2;
   public TimedAnimation timedAnimation3;
   public static float floatValue = 1.0F;
   private Perspective perspective = Perspective.FIRST_PERSON;
   private Perspective perspective2 = Perspective.FIRST_PERSON;
   private Perspective perspective3 = Perspective.FIRST_PERSON;
   private long timestamp;
   private boolean flag;
   private boolean flag2;
   private long timestamp2;
   private boolean flag3;
   private long timestamp3;
   private Screen screen;
   private boolean flag4;

   public Animations() {
      this.addSettings(
         new Setting[]{
            this.animirovat,
            this.rezhimAnimatsii,
            this.skorostChata,
            this.skorostTaba,
            this.skorostInventarya,
            this.skorostSundukov,
            this.skorostKnopok,
            this.skorostF5
         }
      );
   }

   public AnimationMode resolve() {
      String text = this.rezhimAnimatsii.getValue();

      for (AnimationMode animationMode : AnimationMode.values()) {
         if (animationMode.toString().equalsIgnoreCase(text)) {
            return animationMode;
         }
      }

      return AnimationMode.EASE_OUT_BACK;
   }

   public AnimationMode resolve2() {
      return this.resolve3();
   }

   public AnimationMode resolve3() {
      AnimationMode animationMode2 = this.resolve();

      return switch (animationMode2) {
         case EASE_OUT_BACK, EASE_OUT_ELASTIC, SHRINK_EASING -> AnimationMode.EASE_OUT_QUAD;
         default -> animationMode2;
      };
   }

   public float measure() {
      return measure13(this.skorostKnopok);
   }

   public void invoke() {
      if (this.flag2 && this.timedAnimation2 != null && !this.timedAnimation2.isFlag()) {
         this.flag2 = false;
         this.timestamp2 = 0L;
         float floatValue = (float)this.timedAnimation2.getDoubleValue3();
         long longValue = this.compute();
         long longValue2 = Math.max(compute7(80L, this.skorostChata), Math.round(longValue * (1.0 - floatValue)));
         this.timedAnimation2.setAnimationMode(this.resolve());
         this.timedAnimation2.setTimestamp(longValue2);
         this.timedAnimation2.setTimestamp2(1.0);
      } else {
         this.flag2 = false;
         this.timestamp2 = 0L;
         if (this.timedAnimation2 == null) {
            this.timedAnimation2 = new TimedAnimation(this.resolve(), this.compute());
         }

         this.timedAnimation2.setAnimationMode(this.resolve());
         this.timedAnimation2.setTimestamp(this.compute());
         this.timedAnimation2.setTimestamp2(1.0);
      }
   }

   public void invoke2() {
      if (this.timedAnimation2 == null) {
         this.timedAnimation2 = new TimedAnimation(this.resolve2(), this.compute2());
         this.timedAnimation2.setDoubleValue3(1.0);
         this.timedAnimation2.setDoubleValue(1.0);
         this.timedAnimation2.setDoubleValue2(1.0);
         this.timedAnimation2.setFlag(true);
      }

      if (!this.flag2) {
         this.flag2 = true;
         this.timestamp2 = System.currentTimeMillis();
         float floatValue2 = (float)this.timedAnimation2.getDoubleValue3();
         long longValue3 = this.compute2();
         long longValue4 = Math.max(compute7(80L, this.skorostChata), (long)Math.round((float)longValue3 * floatValue2));
         this.timedAnimation2.setAnimationMode(this.resolve2());
         this.timedAnimation2.setTimestamp(longValue4);
      }

      this.timedAnimation2.setTimestamp2(0.0);
   }

   public boolean isFlag2() {
      return this.flag2;
   }

   public boolean check() {
      if (!this.flag2) {
         return false;
      } else if (this.timedAnimation2 == null) {
         return true;
      } else {
         long longValue5 = System.currentTimeMillis() - this.timestamp2;
         return longValue5 >= this.timedAnimation2.getTimestamp() + 20L ? true : this.timedAnimation2.isFlag() && this.timedAnimation2.getDoubleValue3() <= 0.001;
      }
   }

   public float measure2() {
      return this.timedAnimation2 == null ? 0.0F : (float)this.timedAnimation2.getDoubleValue3();
   }

   public void invoke3() {
      this.timedAnimation2 = null;
      this.flag2 = false;
      this.timestamp2 = 0L;
   }

   public boolean check2(Screen screen) {
      if (screen != null && this.enabled) {
         boolean flag = screen instanceof InventoryScreen;
         return flag && this.animirovat.isEnabled("Инвентарь") ? true : !flag && this.animirovat.isEnabled("Сундуки");
      } else {
         return false;
      }
   }

   public float measure3(Screen screen) {
      if (!this.check2(screen)) {
         return 1.0F;
      } else {
         boolean flag2 = this.flag3 && (this.screen == null || this.screen == screen);
         long longValue6 = this.compute4(screen, flag2);
         if (this.timedAnimation == null) {
            this.timedAnimation = new TimedAnimation(flag2 ? this.resolve3() : this.resolve(), longValue6);
            if (flag2) {
               this.timedAnimation.setDoubleValue3(1.0);
               this.timedAnimation.setDoubleValue(1.0);
               this.timedAnimation.setDoubleValue2(1.0);
               this.timedAnimation.setFlag(true);
            }
         }

         this.timedAnimation.setAnimationMode(flag2 ? this.resolve3() : this.resolve());
         this.timedAnimation.setTimestamp(longValue6);
         this.timedAnimation.setTimestamp2(flag2 ? 0.0 : 1.0);
         return measure12((float)this.timedAnimation.getDoubleValue3());
      }
   }

   public void invoke4(Screen screen) {
      if (screen != null && !this.flag4 && this.check2(screen)) {
         if (this.timedAnimation == null) {
            this.timedAnimation = new TimedAnimation(this.resolve3(), this.compute5(screen));
            this.timedAnimation.setDoubleValue3(1.0);
            this.timedAnimation.setDoubleValue(1.0);
            this.timedAnimation.setDoubleValue2(1.0);
            this.timedAnimation.setFlag(true);
         }

         if (!this.flag3 || this.screen != screen) {
            this.flag3 = true;
            this.timestamp3 = System.currentTimeMillis();
            this.screen = screen;
            this.timedAnimation.setAnimationMode(this.resolve3());
            this.timedAnimation.setTimestamp(this.compute5(screen));
         }

         this.timedAnimation.setTimestamp2(0.0);
      }
   }

   public boolean isFlag3() {
      return this.flag3;
   }

   public boolean isFlag4() {
      return this.flag4;
   }

   public void invoke5() {
      this.timedAnimation = null;
      this.flag3 = false;
      this.timestamp3 = 0L;
      this.screen = null;
      this.flag4 = false;
   }

   public boolean check3(boolean bl) {
      if (!this.enabled || !this.animirovat.isEnabled("Таб")) {
         return bl;
      } else {
         return bl ? true : this.timedAnimation3 != null && (!this.timedAnimation3.isFlag() || this.timedAnimation3.getDoubleValue3() > 0.001);
      }
   }

   public float measure4(boolean bl) {
      long longValue7 = this.compute3(bl);
      if (this.timedAnimation3 == null) {
         this.timedAnimation3 = new TimedAnimation(bl ? this.resolve() : this.resolve3(), longValue7);
         if (!bl) {
            this.timedAnimation3.setDoubleValue3(1.0);
            this.timedAnimation3.setDoubleValue(1.0);
            this.timedAnimation3.setDoubleValue2(1.0);
            this.timedAnimation3.setFlag(true);
         }
      }

      this.timedAnimation3.setAnimationMode(bl ? this.resolve() : this.resolve3());
      this.timedAnimation3.setTimestamp(longValue7);
      this.timedAnimation3.setTimestamp2(bl ? 1.0 : 0.0);
      float floatValue3 = measure12((float)this.timedAnimation3.getDoubleValue3());
      if (!bl && this.timedAnimation3.isFlag() && floatValue3 <= 0.001F) {
         this.timedAnimation3 = null;
      }

      return floatValue3;
   }

   public float measure5() {
      if (this.enabled && this.animirovat.isEnabled("F5") && this.flag) {
         long longValue8 = System.currentTimeMillis() - this.timestamp;
         floatValue = measure12((float)longValue8 / (float)this.compute6());
         if (floatValue >= 1.0F) {
            floatValue = 1.0F;
            this.flag = false;
         }

         return floatValue;
      } else {
         floatValue = 1.0F;
         this.flag = false;
         return floatValue;
      }
   }

   public float measure6() {
      float floatValue4 = this.measure5();
      return 1.0F - (float)Math.pow(1.0F - floatValue4, 3.0);
   }

   public boolean check4() {
      return this.flag && this.perspective2 == Perspective.FIRST_PERSON && this.perspective3 == Perspective.THIRD_PERSON_BACK;
   }

   public boolean check5() {
      return this.flag && this.perspective2 != Perspective.FIRST_PERSON && this.perspective3 == Perspective.FIRST_PERSON;
   }

   public boolean check6() {
      return this.flag
         && (
            this.perspective2 == Perspective.THIRD_PERSON_BACK && this.perspective3 == Perspective.THIRD_PERSON_FRONT
               || this.perspective2 == Perspective.THIRD_PERSON_FRONT && this.perspective3 == Perspective.THIRD_PERSON_BACK
         );
   }

   public boolean check7() {
      return this.check6() && this.perspective2 == Perspective.THIRD_PERSON_BACK && this.perspective3 == Perspective.THIRD_PERSON_FRONT;
   }

   public boolean check8() {
      return this.check6() && this.perspective2 == Perspective.THIRD_PERSON_FRONT && this.perspective3 == Perspective.THIRD_PERSON_BACK;
   }

   public float measure7() {
      float floatValue5 = this.measure6();
      if (this.perspective2 == Perspective.THIRD_PERSON_BACK && this.perspective3 == Perspective.THIRD_PERSON_FRONT) {
         return 180.0F * floatValue5;
      } else if (this.perspective2 == Perspective.THIRD_PERSON_FRONT && this.perspective3 == Perspective.THIRD_PERSON_BACK) {
         return 180.0F * (1.0F - floatValue5);
      } else {
         return this.perspective3 == Perspective.THIRD_PERSON_FRONT ? 180.0F : 0.0F;
      }
   }

   public float measure8(float f) {
      float floatValue6 = this.measure6();
      if (this.perspective2 == Perspective.THIRD_PERSON_BACK && this.perspective3 == Perspective.THIRD_PERSON_FRONT) {
         return measure14(f, -f, floatValue6);
      } else if (this.perspective2 == Perspective.THIRD_PERSON_FRONT && this.perspective3 == Perspective.THIRD_PERSON_BACK) {
         return measure14(f, -f, 1.0F - floatValue6);
      } else {
         return this.perspective3 == Perspective.THIRD_PERSON_FRONT ? -f : f;
      }
   }

   public float measure9() {
      if (!this.check5()) {
         return 0.0F;
      } else {
         return this.perspective2 == Perspective.THIRD_PERSON_FRONT ? 180.0F * (1.0F - this.measure6()) : 0.0F;
      }
   }

   public float measure10(float f) {
      if (!this.check5()) {
         return f;
      } else {
         return this.perspective2 == Perspective.THIRD_PERSON_FRONT ? measure14(-f, f, this.measure6()) : f;
      }
   }

   public float measure11() {
      return this.check5() ? 1.0F - this.measure6() : 0.0F;
   }

   private boolean check9() {
      if (!this.flag3) {
         return false;
      } else if (this.timedAnimation == null) {
         return true;
      } else {
         long longValue9 = System.currentTimeMillis() - this.timestamp3;
         return longValue9 >= this.timedAnimation.getTimestamp() + 40L ? true : this.timedAnimation.isFlag() && this.timedAnimation.getDoubleValue3() <= 0.001;
      }
   }

   private void invoke6() {
      Screen screen2 = this.screen;
      this.flag4 = true;

      try {
         if (screen2 != null && CLIENT.currentScreen == screen2) {
            screen2.close();
         } else if (CLIENT.currentScreen != null && this.check2(CLIENT.currentScreen)) {
            CLIENT.setScreen(null);
         }
      } finally {
         this.invoke5();
      }
   }

   private static float measure12(float f) {
      return Math.max(0.0F, Math.min(1.0F, f));
   }

   private long compute() {
      return compute7(240L, this.skorostChata);
   }

   private long compute2() {
      return compute7(220L, this.skorostChata);
   }

   private long compute3(boolean bl) {
      return compute7(bl ? 300L : 240L, this.skorostTaba);
   }

   private long compute4(Screen screen, boolean bl) {
      return compute7(bl ? 120L : 90L, this.resolve4(screen));
   }

   private long compute5(Screen screen) {
      return compute7(120L, this.resolve4(screen));
   }

   private long compute6() {
      return compute7(260L, this.skorostF5);
   }

   private NumberSetting resolve4(Screen screen) {
      return screen instanceof InventoryScreen ? this.skorostInventarya : this.skorostSundukov;
   }

   private static long compute7(long l, NumberSetting numberSetting) {
      return Math.max(1L, (long)Math.round((float)l / measure13(numberSetting)));
   }

   private static float measure13(NumberSetting numberSetting2) {
      if (numberSetting2 == null) {
         return 1.0F;
      } else {
         float floatValue7 = numberSetting2.getValue();
         return Float.isFinite(floatValue7) && !(floatValue7 <= 0.0F) ? floatValue7 : 1.0F;
      }
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (!ServerModeDetector.check()) {
         if (!CLIENT.options.playerListKey.isPressed() && this.timedAnimation3 != null) {
            this.measure4(false);
         }

         Perspective perspective = CLIENT.options.getPerspective();
         if (perspective != this.perspective) {
            this.perspective2 = this.perspective;
            this.perspective3 = perspective;
            this.perspective = perspective;
            if (this.enabled && this.animirovat.isEnabled("F5")) {
               floatValue = 0.0F;
               this.timestamp = System.currentTimeMillis();
               this.flag = true;
            } else {
               floatValue = 1.0F;
               this.flag = false;
            }
         }

         this.measure5();
         if (this.flag2 && CLIENT.currentScreen instanceof ChatScreen && this.check()) {
            CLIENT.setScreen(null);
            this.invoke3();
         }

         if (this.flag3 && this.check9()) {
            this.invoke6();
         }
      }
   }

   private static float measure14(float f, float g, float h) {
      return f + (g - f) * measure12(h);
   }
}
