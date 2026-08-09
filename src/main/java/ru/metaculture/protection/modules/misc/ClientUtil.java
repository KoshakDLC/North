package ru.metaculture.protection;

import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "ClientUtil",
   category = Category.Misc,
   description = "Настройки для клиента"
)
public class ClientUtil extends Module {
   public static final BooleanSetting telegramNotifications = new BooleanSetting("Уведомления в Telegram", true);
   public static final BooleanSetting clientSounds = new BooleanSetting("Звуки клиента", true);
   public static GroupSetting soundOptions = new GroupSetting(
      "Звуки", new BooleanSetting("Модули", true), new BooleanSetting("Уведомления", true).visibleWhen(() -> !clientSounds.isEnabled())
   );
   public static NumberSetting soundVolume = new NumberSetting("Громкость", 100.0F, 10.0F, 100.0F, 1.0F, false).setVisibilityCondition(() -> !clientSounds.isEnabled());

   public ClientUtil() {
      this.addSettings(new Setting[]{telegramNotifications, clientSounds, soundOptions, soundVolume});
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (telegramNotifications.isEnabled() && !TelegramApi.check()) {
         ChatUtil.sendClientMessage("§cСписок пуст для отправки сообщений. Настройте API через .tapi");
         telegramNotifications.setValue(false);
      }
   }
}
