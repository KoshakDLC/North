package ru.metaculture.protection;

import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.text.Text;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "Sprint",
   description = "Сложна понять че за модуль",
   category = Category.Movement
)
public class Sprint extends Module {
   public static BooleanSetting sohranyatSprint = new BooleanSetting("Сохранять спринт", false);
   public static NumberSetting silaSohraneniya = new NumberSetting("Сила сохранения", 0.6F, 0.2F, 1.0F, 0.1F, false).setVisibilityCondition(() -> !sohranyatSprint.isEnabled());
   public final BooleanSetting ignorirovatGolod = new BooleanSetting("Игнорировать голод", false);
   public static int intValue = 0;

   public Sprint() {
      this.addSettings(new Setting[]{sohranyatSprint, silaSohraneniya, this.ignorirovatGolod});
   }

   @EventHandler
   public void onPlayerEntityTick(PlayerEntityTickEvent playerEntityTickEvent) {
      if (CLIENT.player != null && !CLIENT.player.hasStatusEffect(StatusEffects.BLINDNESS)) {
         if (sohranyatSprint.isEnabled()) {
            CLIENT.player
               .setVelocity(
                  CLIENT.player.getVelocity().x / silaSohraneniya.getValue(),
                  CLIENT.player.getVelocity().y,
                  CLIENT.player.getVelocity().z / silaSohraneniya.getValue()
               );
            CLIENT.player.setSprinting(true);
         }
      }
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player != null && CLIENT.world != null) {
         if (CLIENT.player.hasStatusEffect(StatusEffects.BLINDNESS)) {
            CLIENT.player.setSprinting(false);
            CLIENT.options.sprintKey.setPressed(false);
         } else {
            boolean flag = CLIENT.player.horizontalCollision && !CLIENT.player.collidedSoftly;
            if (intValue != 0) {
               CLIENT.player.setSprinting(false);
               CLIENT.options.sprintKey.setPressed(false);
               intValue--;
            } else {
               if (AttackUtils.check18(AttackAura.livingEntity, AttackAura.resolve2(AttackAura.livingEntity))) {
                  CLIENT.player.setSprinting(false);
                  CLIENT.options.sprintKey.setPressed(false);
               }

               if (!CLIENT.player.isSneaking() && !flag && CLIENT.options.forwardKey.isPressed()) {
                  CLIENT.player.setSprinting(true);
                  CLIENT.options.sprintKey.setPressed(true);
               }
            }
         }
      }
   }

   @Override
   public void onDisable() {
      super.onDisable();
      if (CLIENT.player != null) {
         CLIENT.player.setSprinting(false);
      }
   }

   @Override
   public void onEnable() {
      super.onEnable();
      if (this.ignorirovatGolod.isEnabled() && !ServerModeDetector.check3() && CLIENT.player != null) {
         CLIENT.player.sendMessage(Text.of("§cДанная настройка работает только на FunTime!"), true);
      }
   }
}
