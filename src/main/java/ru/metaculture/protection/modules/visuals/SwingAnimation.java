package ru.metaculture.protection;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "Swing Animation",
   description = "Кастомизация анимации руки",
   category = Category.Visuals
)
public class SwingAnimation extends Module {
   public static ModeSetting animatsiya = new ModeSetting("Анимация", "Smooth", "Smooth", "Swipe", "Swipe back", "SwipeD", "Down", "Spin", "Off");
   public static NumberSetting skorostAnimatsii = new NumberSetting("Скорость анимации", 1.0F, 0.1F, 3.0F, 0.1F, false);
   public static NumberSetting razmerAnimatsii = new NumberSetting("Размер анимации", 3.7F, 1.0F, 10.0F, 0.1F, false)
      .setVisibilityCondition(() -> animatsiya.is("Off"));
   public static NumberSetting razmerPredmetaSprava = new NumberSetting("Размер предмета справа", 1.0F, 0.2F, 2.5F, 0.05F, false);
   public static NumberSetting razmerPredmetaSleva = new NumberSetting("Размер предмета слева", 1.0F, 0.2F, 2.5F, 0.05F, false);
   public static BooleanSetting tolkoAura = new BooleanSetting("Только Аура", false);
   public static BooleanSetting modelRuki = new BooleanSetting("Модель Руки", false);
   public static BooleanSetting menyatObeRuki = new BooleanSetting("Менять обе руки", false).visibleWhen(() -> !modelRuki.isEnabled());
   public static NumberSetting x = new NumberSetting("X", 0.0F, -2.0F, 2.0F, 0.01F, false)
      .setVisibilityCondition(() -> !modelRuki.isEnabled() || !menyatObeRuki.isEnabled());
   public static NumberSetting y = new NumberSetting("Y", 0.0F, -2.0F, 2.0F, 0.01F, false)
      .setVisibilityCondition(() -> !modelRuki.isEnabled() || !menyatObeRuki.isEnabled());
   public static NumberSetting z = new NumberSetting("Z", 0.0F, -2.0F, 2.0F, 0.01F, false)
      .setVisibilityCondition(() -> !modelRuki.isEnabled() || !menyatObeRuki.isEnabled());
   public static NumberSetting xPravaya = new NumberSetting("X правая", 0.0F, -2.0F, 2.0F, 0.01F, false)
      .setVisibilityCondition(() -> !modelRuki.isEnabled() || menyatObeRuki.isEnabled());
   public static NumberSetting yPravaya = new NumberSetting("Y правая", 0.0F, -2.0F, 2.0F, 0.01F, false)
      .setVisibilityCondition(() -> !modelRuki.isEnabled() || menyatObeRuki.isEnabled());
   public static NumberSetting zPravaya = new NumberSetting("Z правая", 0.0F, -2.0F, 2.0F, 0.01F, false)
      .setVisibilityCondition(() -> !modelRuki.isEnabled() || menyatObeRuki.isEnabled());
   public static NumberSetting xLevaya = new NumberSetting("X левая", 0.0F, -2.0F, 2.0F, 0.01F, false)
      .setVisibilityCondition(() -> !modelRuki.isEnabled() || menyatObeRuki.isEnabled());
   public static NumberSetting yLevaya = new NumberSetting("Y левая", 0.0F, -2.0F, 2.0F, 0.01F, false)
      .setVisibilityCondition(() -> !modelRuki.isEnabled() || menyatObeRuki.isEnabled());
   public static NumberSetting zLevaya = new NumberSetting("Z левая", 0.0F, -2.0F, 2.0F, 0.01F, false)
      .setVisibilityCondition(() -> !modelRuki.isEnabled() || menyatObeRuki.isEnabled());

   public SwingAnimation() {
      this.addSettings(
         new Setting[]{
            animatsiya,
            skorostAnimatsii,
            razmerPredmetaSprava,
            razmerPredmetaSleva,
            razmerAnimatsii,
            tolkoAura,
            modelRuki,
            menyatObeRuki,
            x,
            y,
            z,
            xPravaya,
            yPravaya,
            zPravaya,
            xLevaya,
            yLevaya,
            zLevaya
         }
      );
   }

   @EventHandler
   public void onHandRender(HandRenderEvent handRenderEvent) {
      if (this.enabled && !animatsiya.is("Off")) {
         if (check() && handRenderEvent.getHand().equals(Hand.MAIN_HAND)) {
            String text = animatsiya.getValue();
            if (!text.equals("Off")) {
               if (handRenderEvent.getHand().equals(Hand.MAIN_HAND)) {
                  MatrixStack matrices = handRenderEvent.getMatrixStack();
                  float floatValue = handRenderEvent.getFloatValue();
                  int intValue = CLIENT.player.getMainArm().equals(Arm.RIGHT) ? 1 : -1;
                  float floatValue2 = (float)Math.sin(floatValue * (Math.PI / 2) * 2.0);
                  float floatValue3 = (float)Math.sin(floatValue * (Math.PI / 2) * 2.0);
                  float floatValue4 = (float)(Math.sin(floatValue * Math.PI) * 0.5);
                  float floatValue5 = MathHelper.sin(floatValue * floatValue * (float) Math.PI);
                  float floatValue6 = MathHelper.sin(MathHelper.sqrt(floatValue) * (float) Math.PI);
                  String text2 = animatsiya.getValue();
                  switch (text2) {
                     case "Swipe":
                        matrices.translate(intValue * 0.67F, -0.32F, -1.0F);
                        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90 * intValue));
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-60 * intValue));
                        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(floatValue2 * -razmerAnimatsii.getValue() * 10.0F));
                        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90.0F));
                        break;
                     case "Swipe back":
                        matrices.translate(intValue * 0.67F, -0.32F, -1.0F);
                        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90 * intValue));
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-60 * intValue));
                        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(floatValue2 * razmerAnimatsii.getValue() * 10.0F));
                        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90.0F));
                        break;
                     case "SwipeD":
                        matrices.translate(intValue * 0.67F, -0.32F, -1.0F);
                        matrices.translate(floatValue6 * -razmerAnimatsii.getValue() / 35.0F, 0.0F, floatValue6 * -razmerAnimatsii.getValue() / 35.0F);
                        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(25.0F));
                        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(floatValue6 * -razmerAnimatsii.getValue() * 5.0F));
                        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(30.0F));
                        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90.0F));
                        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(50.0F));
                        break;
                     case "Down":
                        matrices.translate(intValue * 0.67F, -0.32F, -1.0F);
                        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(80 * intValue));
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-30 * intValue));
                        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(floatValue2 * -razmerAnimatsii.getValue() * 10.0F));
                        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-100.0F));
                        break;
                     case "Spin":
                        matrices.translate(intValue * 0.56F, -0.42F, -0.72F);
                        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(0.0F + floatValue * 360.0F));
                        matrices.translate(0.0, -0.1, 0.0);
                        break;
                     case "Smooth":
                        matrices.translate(intValue * 0.56F, -0.42F, -0.72F);
                        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(intValue * (45.0F + floatValue2 * -razmerAnimatsii.getValue() * 3.0F)));
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(intValue * floatValue3 * -razmerAnimatsii.getValue() * 2.0F));
                        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(floatValue3 * -razmerAnimatsii.getValue() * 10.0F));
                        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(intValue * -45.0F));
                        matrices.translate(0.0, -0.1, 0.0);
                  }

                  handRenderEvent.invalidate();
               }
            }
         }
      }
   }

   public static boolean check() {
      if (!tolkoAura.isEnabled()) {
         return true;
      } else {
         AttackAura attackAura = (AttackAura)WildClient.INSTANCE.moduleManager.findModule(AttackAura.class);
         return attackAura != null && attackAura.enabled && AttackAura.livingEntity != null;
      }
   }

   public static float measure(Hand hand) {
      if (hand != null && WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null && CLIENT.player != null) {
         SwingAnimation swingAnimation = WildClient.INSTANCE.moduleManager.getModule(SwingAnimation.class);
         if (swingAnimation != null && swingAnimation.enabled) {
            Arm arm = hand == Hand.MAIN_HAND ? CLIENT.player.getMainArm() : (CLIENT.player.getMainArm() == Arm.RIGHT ? Arm.LEFT : Arm.RIGHT);
            return arm == Arm.RIGHT ? razmerPredmetaSprava.getValue() : razmerPredmetaSleva.getValue();
         } else {
            return 1.0F;
         }
      } else {
         return 1.0F;
      }
   }

   @EventHandler
   public void onHandVisibility(HandVisibilityEvent handVisibilityEvent) {
      boolean flag = handVisibilityEvent.check();
      MatrixStack matrices2 = handVisibilityEvent.getMatrixStack();
      if (modelRuki.isEnabled() && menyatObeRuki.isEnabled()) {
         if (flag) {
            matrices2.translate(x.getValue(), y.getValue(), z.getValue());
         } else {
            matrices2.translate(-x.getValue(), y.getValue(), z.getValue());
         }
      }

      if (modelRuki.isEnabled() && !menyatObeRuki.isEnabled()) {
         if (flag) {
            matrices2.translate(xPravaya.getValue(), yPravaya.getValue(), zPravaya.getValue());
         } else {
            matrices2.translate(xLevaya.getValue(), yLevaya.getValue(), zLevaya.getValue());
         }
      }
   }
}
