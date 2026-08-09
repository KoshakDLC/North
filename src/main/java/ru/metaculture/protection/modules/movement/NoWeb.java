package ru.metaculture.protection;

import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "NoWeb",
   description = "Убирает замедление в паутине",
   category = Category.Movement
)
public class NoWeb extends Module {
   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (!ServerBlockUtils.check() && ServerBlockUtils.check3()) {
         double[] doubleValues = MovementUtils.resolve((double)MathUtils.measure17(0.62F, 0.64F));
         CLIENT.player
            .setVelocity(doubleValues[0], CLIENT.options.jumpKey.isPressed() ? 1.2 : (CLIENT.options.sneakKey.isPressed() ? -2.0 : 0.0), doubleValues[1]);
      }
   }
}
