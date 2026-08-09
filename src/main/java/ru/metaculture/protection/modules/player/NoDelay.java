package ru.metaculture.protection;

import net.minecraft.item.Items;
import org.wild.mixin.acceser.ClientPlayerInteractionManagerAccessor;
import org.wild.mixin.acceser.MinecraftClientAccessor;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "NoDelay",
   description = "Убирает задержку",
   category = Category.Player
)
public class NoDelay extends Module {
   public static BooleanSetting pryzhki = new BooleanSetting("Прыжки", true);
   public static BooleanSetting polomkaBlokov = new BooleanSetting("Поломка блоков", false);
   public static BooleanSetting lkm = new BooleanSetting("ЛКМ", false);
   public static BooleanSetting pkm = new BooleanSetting("ПКМ", false);
   public static BooleanSetting puzyrkiOpyta = new BooleanSetting("Пузырьки опыта", true);
   public static NumberSetting skorostPryzhka = new NumberSetting("Скорость прыжка", 0.0F, 0.0F, 10.0F, 1.0F, false).setVisibilityCondition(() -> !pryzhki.isEnabled());
   public static NumberSetting skorostPolomkiBloka = new NumberSetting("Скорость поломки блока", 0.0F, 0.0F, 5.0F, 1.0F, false)
      .setVisibilityCondition(() -> !polomkaBlokov.isEnabled());
   public static NumberSetting lkmZaderzhka = new NumberSetting("ЛКМ задержка", 0.0F, 0.0F, 10.0F, 1.0F, false).setVisibilityCondition(() -> !lkm.isEnabled());
   public static NumberSetting pkmZaderzhka = new NumberSetting("ПКМ задержка", 0.0F, 0.0F, 4.0F, 1.0F, false).setVisibilityCondition(() -> !pkm.isEnabled());

   public NoDelay() {
      this.addSettings(
         new Setting[]{pryzhki, skorostPryzhka, polomkaBlokov, skorostPolomkiBloka, lkm, lkmZaderzhka, pkm, pkmZaderzhka, puzyrkiOpyta}
      );
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player != null) {
         if (polomkaBlokov.isEnabled() && CLIENT.interactionManager != null) {
            ClientPlayerInteractionManagerAccessor clientPlayerInteractionManagerAccessor = (ClientPlayerInteractionManagerAccessor)CLIENT.interactionManager;
            if (clientPlayerInteractionManagerAccessor.getBlockBreakingCooldown() > skorostPolomkiBloka.getValue()) {
               clientPlayerInteractionManagerAccessor.setBlockBreakingCooldown((int)skorostPolomkiBloka.getValue());
            }
         }

         if (puzyrkiOpyta.isEnabled()) {
            boolean flag = CLIENT.player.getMainHandStack().getItem() == Items.EXPERIENCE_BOTTLE
               || CLIENT.player.getOffHandStack().getItem() == Items.EXPERIENCE_BOTTLE;
            if (flag) {
               ((MinecraftClientAccessor)CLIENT).setItemUseCooldown(0);
            }
         }
      }
   }

   @Override
   public void onDisable() {
      super.onDisable();
      if (CLIENT.interactionManager != null) {
         ((ClientPlayerInteractionManagerAccessor)CLIENT.interactionManager).setBlockBreakingCooldown(5);
      }
   }
}
