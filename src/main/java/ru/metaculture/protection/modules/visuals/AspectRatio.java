package ru.metaculture.protection;

import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "AspectRation",
   description = "Изменяет разрешение экрана",
   category = Category.Visuals
)
public class AspectRatio extends Module {
   public static final ModeSetting SOOTNOSHENIE_EKRANA = new ModeSetting(
      "Соотношение экрана", "16:9", "16:9", "4:3", "1:1", "16:10", "21:9", "32:9", "5:4", "2:1", "Кастомное"
   );
   public static final NumberSetting KASTOMOE_ZNACHNIE = new NumberSetting("Кастомое значние", 2.0F, 1.0F, 3.0F, 0.1F, false)
      .setVisibilityCondition(() -> !SOOTNOSHENIE_EKRANA.is("Кастомное"));

   public AspectRatio() {
      this.addSettings(new Setting[]{SOOTNOSHENIE_EKRANA, KASTOMOE_ZNACHNIE});
   }

   public static float measure() {
      AspectRatioController aspectRatioController = new AspectRatioController(CLIENT);
      if (!WildClient.INSTANCE.moduleManager.findModule(AspectRatio.class).enabled) {
         return 0.0F;
      } else {
         float floatValue = (float)aspectRatioController.getIntValue3() / aspectRatioController.getIntValue22();
         String text = SOOTNOSHENIE_EKRANA.getValue();

         float floatValue2 = switch (text) {
            case "16:9" -> 1.7777778F;
            case "4:3" -> 1.3333334F;
            case "1:1" -> 1.0F;
            case "16:10" -> 1.6F;
            case "21:9" -> 2.3333333F;
            case "32:9" -> 3.5555556F;
            case "5:4" -> 1.25F;
            case "2:1" -> 2.0F;
            default -> KASTOMOE_ZNACHNIE.getValue();
         };
         return floatValue2 - floatValue;
      }
   }
}
