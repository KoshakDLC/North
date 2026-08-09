package ru.metaculture.protection;

import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "DragonFly",
   category = Category.Movement,
   description = "Ускоряет вас в воздухе"
)
public class DragonFly extends Module {
   public final NumberSetting skorostPoX = new NumberSetting("Скорость по X", 1.0F, 1.0F, 100.0F, 1.0F, false);
   public final NumberSetting skorostPoY = new NumberSetting("Скорость по Y", 1.0F, 1.0F, 100.0F, 1.0F, false);

   public DragonFly() {
      this.addSettings(new Setting[]{this.skorostPoX, this.skorostPoY});
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (!ServerModeDetector.check()) {
         if (CLIENT.player.getAbilities().flying) {
            double doubleValue = this.skorostPoX.getValue() / 10.0;
            double doubleValue2 = this.skorostPoY.getValue() / 10.0;
            double doubleValue3;
            if (CLIENT.options.jumpKey.isPressed()) {
               doubleValue3 = doubleValue2;
            } else if (CLIENT.options.sneakKey.isPressed()) {
               doubleValue3 = -doubleValue2;
            } else {
               doubleValue3 = 0.0;
            }

            if (MovementUtils.check()) {
               double[] doubleValues = MovementUtils.resolve(doubleValue);
               CLIENT.player.setVelocity(doubleValues[0], doubleValue3, doubleValues[1]);
            } else {
               CLIENT.player.setVelocity(0.0, doubleValue3, 0.0);
            }
         }
      }
   }
}
