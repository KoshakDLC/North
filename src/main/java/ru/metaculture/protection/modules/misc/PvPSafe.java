package ru.metaculture.protection;

import java.util.Locale;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ClientBossBar;
import net.minecraft.client.network.ServerInfo;
import org.wild.mixin.acceser.BossBarHudAccessor;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "PvPSafe",
   category = Category.Misc,
   description = "Защищает вас от лива в КД"
)
public class PvPSafe extends Module {
   private static final Set<String> VALUES = Set.of(
      "hub",
      "lobby",
      "spawn",
      "leave",
      "quit",
      "disconnect",
      "server",
      "servers",
      "an",
      "anarchy",
      "realm",
      "menu",
      "logout",
      "reconnect",
      "play",
      "warp",
      "duel",
      "l"
   );
   private final GroupSetting server = new GroupSetting(
      "Сервер",
      new BooleanSetting("FunTime", true),
      new BooleanSetting("HolyWorld", true),
      new BooleanSetting("SpookyTime", true),
      new BooleanSetting("Любой другой", true)
   );
   private final BooleanSetting notifications = new BooleanSetting("Notifications", true);
   private long timestamp;

   public PvPSafe() {
      this.addSettings(new Setting[]{this.server, this.notifications});
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (this.check6()) {
         this.invoke2(AutoLeave.class);
         this.invoke2(ServerJoiner.class);
      }
   }

   public static boolean check() {
      PvPSafe pvPSafe = resolve4();
      return pvPSafe != null && pvPSafe.check6();
   }

   public static boolean check2() {
      return check8(MinecraftClient.getInstance());
   }

   public static boolean check3(String string) {
      if (string == null) {
         return false;
      } else {
         String text = string.trim();
         return !text.startsWith("/") ? false : check4(text.substring(1));
      }
   }

   public static boolean check4(String string) {
      if (string == null) {
         return false;
      } else {
         String text2 = resolve2(string);
         if (text2.isEmpty()) {
            return false;
         } else {
            String text3 = text2.split("\\s+", 2)[0];
            boolean flag = VALUES.contains(text3) || text3.matches("an\\d{1,5}");
            return !flag ? false : check10("command /" + text2);
         }
      }
   }

   public static boolean check5(boolean bl) {
      return check10(bl ? "server transfer" : "disconnect");
   }

   private boolean check6() {
      return this.enabled && CLIENT != null && CLIENT.player != null && CLIENT.world != null
         ? this.check7() && check8(CLIENT)
         : false;
   }

   private boolean check7() {
      String text4 = this.resolve();
      if (text4.contains("funtime") || text4.contains("fun-time")) {
         return this.server.isEnabled("FunTime");
      } else if (text4.contains("holyworld") || text4.contains("holy-world") || text4.contains("holy")) {
         return this.server.isEnabled("HolyWorld");
      } else {
         return !text4.contains("spookytime") && !text4.contains("spooky-time") && !text4.contains("spooky")
            ? this.server.isEnabled("Любой другой")
            : this.server.isEnabled("SpookyTime");
      }
   }

   private String resolve() {
      MinecraftClient client = MinecraftClient.getInstance();
      if (client == null) {
         return "";
      } else {
         ServerInfo serverInfo = client.getCurrentServerEntry();
         return serverInfo != null && serverInfo.address != null ? serverInfo.address.toLowerCase(Locale.ROOT) : "";
      }
   }

   private static boolean check8(MinecraftClient minecraftClient) {
      if (minecraftClient != null && minecraftClient.inGameHud != null && minecraftClient.inGameHud.getBossBarHud() != null) {
         Map valuesByKey = ((BossBarHudAccessor)minecraftClient.inGameHud.getBossBarHud()).getBossBars();

         for (ClientBossBar clientBossBar : (Iterable<ClientBossBar>)valuesByKey.values()) {
            String text5 = resolve3(clientBossBar.getName().getString());
            if (check9(text5)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private static boolean check9(String string) {
      return string.contains("pvp")
         || string.contains("пвп")
         || string.contains("combat")
         || string.contains("fight")
         || string.contains("battle")
         || string.contains("режим боя")
         || string.contains("в бою")
         || string.contains("до выхода")
         || string.contains("нельзя выйти")
         || string.contains("не выходите")
         || string.contains("выход через");
   }

   private static boolean check10(String string) {
      PvPSafe pvPSafe2 = resolve4();
      if (pvPSafe2 != null && pvPSafe2.check6()) {
         pvPSafe2.invoke(string);
         return true;
      } else {
         return false;
      }
   }

   private void invoke(String string) {
      if (this.notifications.isEnabled()) {
         long longValue = System.currentTimeMillis();
         if (longValue - this.timestamp >= 1200L) {
            this.timestamp = longValue;
            ChatUtil.sendClientMessage("[PvPSafe] заблокировал " + string);
         }
      }
   }

   private static String resolve2(String string) {
      String text6 = string.trim().toLowerCase(Locale.ROOT);

      while (text6.startsWith("/")) {
         text6 = text6.substring(1).trim();
      }

      return text6.replaceAll("\\s+", " ");
   }

   private static String resolve3(String string) {
      return string == null ? "" : string.replaceAll("(?i)§[0-9a-fk-or]", "").toLowerCase(Locale.ROOT).trim();
   }

   private static PvPSafe resolve4() {
      return WildClient.isInitialized() && WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null
         ? WildClient.INSTANCE.moduleManager.getModule(PvPSafe.class)
         : null;
   }

   private <T extends Module> void invoke2(Class<T> class_) {
      Module module = WildClient.INSTANCE.moduleManager.getModule(class_);
      if (module != null && module.enabled) {
         module.setEnabled(false);
      }
   }
}
