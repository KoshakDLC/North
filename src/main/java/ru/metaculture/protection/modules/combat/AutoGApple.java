package ru.metaculture.protection;

import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "AutoGApple",
   category = Category.Combat,
   description = "Автоматически есть яблочки"
)
public class AutoGApple extends Module {
   public final NumberSetting zdorove = new NumberSetting("Здоровье", 10.0F, 1.0F, 20.0F, 1.0F, false);

   public AutoGApple() {
      this.addSettings(new Setting[]{this.zdorove});
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
   }
}
