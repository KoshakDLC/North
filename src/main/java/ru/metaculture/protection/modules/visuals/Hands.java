package ru.metaculture.protection;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import org.lwjgl.glfw.GLFW;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "Hands",
   description = "Свечение и настройка предметов в руках",
   category = Category.Visuals
)
public final class Hands extends Module {
   public final GroupSetting ruki = new GroupSetting("Руки", new BooleanSetting("Правая", true), new BooleanSetting("Левая", true));
   public final ModeSetting effekt = new ModeSetting("Эффект", "Свечение + контур", "Свечение + контур", "Свечение", "Контур");
   public final NumberSetting radius = new NumberSetting("Радиус", 8.0F, 2.0F, 24.0F, 1.0F, false).setVisibilityCondition(this::check3);
   public final NumberSetting silaSvecheniya = new NumberSetting("Сила свечения", 1.8F, 0.25F, 5.0F, 0.05F, false).setVisibilityCondition(this::check3);
   public final NumberSetting tolschinaKontura = new NumberSetting("Толщина контура", 1.5F, 0.5F, 6.0F, 0.5F, false).setVisibilityCondition(this::check2);
   public final NumberSetting prozrachnost = new NumberSetting("Прозрачность", 0.9F, 0.05F, 1.0F, 0.01F, true);
   public final ModeSetting istochnikTsveta = new ModeSetting("Источник цвета", "Предмет", "Предмет", "Тема", "Свой");
   public final ModeSetting otobrazhenieTsveta = new ModeSetting("Отображение цвета", "Градиент", "Градиент", "Статичный");
   public final ColorSetting osnovnoyTsvet = new ColorSetting("Основной цвет", 55.0F, 0.72F, 1.0F).setVisibilityCondition(() -> !this.istochnikTsveta.is("Свой"));
   public final ColorSetting vtoroyTsvet = new ColorSetting("Второй цвет", 76.0F, 0.78F, 1.0F)
      .setVisibilityCondition(() -> !this.istochnikTsveta.is("Свой") || this.otobrazhenieTsveta.is("Статичный"));
   private GlowEspRenderer glowEspRenderer;
   private static final Hand[] HANDS = Hand.values();
   private final float[] floats = new float[3];
   private final float[] floats2 = new float[3];

   public Hands() {
      this.addSettings(
         new Setting[]{
            this.ruki,
            this.effekt,
            this.radius,
            this.silaSvecheniya,
            this.tolschinaKontura,
            this.prozrachnost,
            this.istochnikTsveta,
            this.otobrazhenieTsveta,
            this.osnovnoyTsvet,
            this.vtoroyTsvet
         }
      );
   }

   @Override
   public void onDisable() {
      this.invoke4();
      super.onDisable();
   }

   public boolean check(Hand hand) {
      if (this.enabled && hand != null && CLIENT.player != null && !this.check5() && this.check4()) {
         Arm arm2 = hand == Hand.MAIN_HAND ? CLIENT.player.getMainArm() : resolve(CLIENT.player.getMainArm());
         return this.ruki.isEnabled(arm2 == Arm.RIGHT ? "Правая" : "Левая");
      } else {
         return false;
      }
   }

   @EventHandler(
      priority = 0
   )
   public void onHudRender(HudRenderEvent hudRenderEvent) {
      if (!IrisCompatibility.check() && hudRenderEvent != null && hudRenderEvent.getRenderManager() != null) {
         hudRenderEvent.getRenderManager().invoke20();
         this.invoke2(hudRenderEvent.getIntValue(), hudRenderEvent.getIntValue2());
         hudRenderEvent.getRenderManager().invoke20();
      }
   }

   public void invoke() {
      if (IrisCompatibility.check() && CLIENT.getWindow() != null) {
         this.invoke2(CLIENT.getWindow().getFramebufferWidth(), CLIENT.getWindow().getFramebufferHeight());
      }
   }

   private void invoke2(int i, int j) {
      if (this.enabled
         && CLIENT.world != null
         && CLIENT.player != null
         && i > 0
         && j > 0
         && CLIENT.getWindow() != null
         && !CLIENT.getWindow().hasZeroWidthOrHeight()) {
         HandMaskRenderer handMaskRenderer = HandMaskRenderer.getINSTANCE();
         if (this.check5()) {
            handMaskRenderer.invoke(false, false, i, j);
         } else if (!this.check4()) {
            handMaskRenderer.invoke(false, false, i, j);
         } else {
            boolean flag = false;

            for (Hand hand2 : HANDS) {
               if (this.check(hand2) && handMaskRenderer.check(hand2)) {
                  int intValue = handMaskRenderer.compute(hand2);
                  if (intValue > 0) {
                     if (this.glowEspRenderer == null) {
                        this.glowEspRenderer = new GlowEspRenderer();
                     }

                     if (!flag) {
                        this.invoke3(this.floats, this.floats2);
                        flag = true;
                     }

                     int intValue2 = handMaskRenderer.compute3(hand2);
                     this.glowEspRenderer
                        .check4(
                           intValue,
                           handMaskRenderer.compute2(hand2),
                           intValue2 > 0 ? intValue2 : intValue,
                           i,
                           j,
                           new GlowEspRenderer.GlowEspRendererData(
                              this.radius.getValue() * 2.0F,
                              this.tolschinaKontura.getValue(),
                              this.check3() ? 0.0F : this.silaSvecheniya.getValue() * 2.0F,
                              this.check2() ? 0.0F : 1.35F,
                              this.prozrachnost.getValue(),
                              0,
                              this.otobrazhenieTsveta.is("Статичный") ? 1 : 0,
                              this.istochnikTsveta.is("Предмет") ? 1 : 0,
                              this.floats[0],
                              this.floats[1],
                              this.floats[2],
                              this.floats2[0],
                              this.floats2[1],
                              this.floats2[2]
                           )
                        );
                  }
               }
            }
         }
      }
   }

   private void invoke3(float[] fs, float[] gs) {
      if (this.istochnikTsveta.is("Свой")) {
         invoke5(this.osnovnoyTsvet.getColor().getRGB(), fs);
         invoke5(this.vtoroyTsvet.getColor().getRGB(), gs);
      } else {
         Theme theme = WildClient.INSTANCE != null && WildClient.INSTANCE.themeManager != null ? WildClient.INSTANCE.themeManager.getTheme() : Theme.WILD;
         ColorScheme colorScheme = ColorScheme.resolve2(theme, NeumorphismRenderer.check13());
         invoke5(colorScheme.getIntValue14(), fs);
         invoke5(colorScheme.getIntValue15(), gs);
      }
   }

   private boolean check2() {
      return this.effekt.is("Свечение");
   }

   private boolean check3() {
      return this.effekt.is("Контур");
   }

   private boolean check4() {
      return CLIENT.options != null && CLIENT.options.getPerspective() != null && CLIENT.options.getPerspective().isFirstPerson();
   }

   private boolean check5() {
      return CLIENT.options != null && CLIENT.options.hudHidden;
   }

   private void invoke4() {
      Runnable runnable = () -> {
         HandMaskRenderer.getINSTANCE().invoke3();
         GlowEspRenderer var1x = this.glowEspRenderer;
         this.glowEspRenderer = null;
         if (var1x != null) {
            var1x.close();
         }
      };
      if (RenderSystem.isOnRenderThread() && GLFW.glfwGetCurrentContext() != 0L) {
         runnable.run();
      } else if (CLIENT != null) {
         CLIENT.execute(runnable);
      }
   }

   private static Arm resolve(Arm arm) {
      return arm == Arm.RIGHT ? Arm.LEFT : Arm.RIGHT;
   }

   private static void invoke5(int i, float[] fs) {
      fs[0] = (i >> 16 & 0xFF) / 255.0F;
      fs[1] = (i >> 8 & 0xFF) / 255.0F;
      fs[2] = (i & 0xFF) / 255.0F;
   }
}
