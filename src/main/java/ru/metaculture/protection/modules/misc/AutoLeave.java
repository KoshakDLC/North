package ru.metaculture.protection;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.text.Text;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "AutoLeave",
   category = Category.Misc,
   description = "Автоматический выход"
)
public class AutoLeave extends Module {
   public final ModeSetting rezhimRaboty = new ModeSetting("Режим работы", "Хаб", "Хаб", "Меню");
   public final ModeSetting triggery = new ModeSetting("Триггеры", "Игрок рядом", "Игрок рядом", "ХП");
   public final NumberSetting radiusIgroka = new NumberSetting("Радиус игрока", 30.0F, 10.0F, 100.0F, 1.0F, false)
      .setVisibilityCondition(() -> !this.triggery.is("Игрок рядом"));
   public final NumberSetting porogZdorovya = new NumberSetting("Порог здоровья", 10.0F, 1.0F, 20.0F, 1.0F, false)
      .setVisibilityCondition(() -> !this.triggery.is("ХП"));
   private final Stopwatch stopwatch = new Stopwatch();

   public AutoLeave() {
      this.addSettings(new Setting[]{this.rezhimRaboty, this.triggery, this.radiusIgroka, this.porogZdorovya});
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player != null && CLIENT.world != null) {
         if (this.stopwatch.check2(100.0)) {
            boolean flag = false;
            String text = "";
            if (this.triggery.is("ХП")) {
               float floatValue = CLIENT.player.getHealth() + CLIENT.player.getAbsorptionAmount();
               if (floatValue <= this.porogZdorovya.getValue()) {
                  flag = true;
                  text = "Мало здоровья (" + (int)floatValue + " HP)";
               }
            } else if (this.triggery.is("Игрок рядом")) {
               for (AbstractClientPlayerEntity abstractClientPlayerEntity : CLIENT.world.getPlayers()) {
                  if (abstractClientPlayerEntity != CLIENT.player && !FriendCommand.check(abstractClientPlayerEntity.getName().getString())) {
                     double doubleValue = CLIENT.player.distanceTo(abstractClientPlayerEntity);
                     if (doubleValue <= this.radiusIgroka.getValue()) {
                        flag = true;
                        text = abstractClientPlayerEntity.getName().getString();
                        break;
                     }
                  }
               }
            }

            if (flag) {
               this.invoke(text);
               this.stopwatch.invoke();
               this.toggle();
            }
         }
      }
   }

   private void invoke(String string) {
      if (this.rezhimRaboty.is("Хаб")) {
         if (CLIENT.player.networkHandler != null) {
            CLIENT.player.networkHandler.sendChatMessage("/hub");
            if (ClientUtil.telegramNotifications.isEnabled()) {
               TelegramApi.invoke2("[AutoLeave] Был замечен игрок, его ник - " + string);
            }
         }
      } else if (this.rezhimRaboty.is("Меню") && CLIENT.getNetworkHandler() != null && CLIENT.getNetworkHandler().getConnection() != null) {
         String text2 = string;
         if (this.triggery.is("Игрок рядом")) {
            text2 = "Был замечен игрок, его ник - " + string;
         }

         CLIENT.getNetworkHandler().getConnection().disconnect(Text.of(text2));
      }
   }
}
