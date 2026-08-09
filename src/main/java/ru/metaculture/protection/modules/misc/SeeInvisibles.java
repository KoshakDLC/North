package ru.metaculture.protection;

import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "SeeInvisibles",
   category = Category.Misc,
   description = "Показ игроков в невидимости"
)
public class SeeInvisibles extends Module {
   public final NumberSetting opacity = new NumberSetting("Прозрачность", 0.5F, 0.3F, 1.0F, 0.1F, false);

   public SeeInvisibles() {
      this.addSettings(new Setting[]{this.opacity});
   }
}
