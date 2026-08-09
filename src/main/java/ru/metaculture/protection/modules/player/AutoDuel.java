package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "AutoDuel",
   category = Category.Player,
   description = "Кидает за вас дуэли на ReallyWorld"
)
public class AutoDuel extends Module {
   public final GroupSetting rezhim = new GroupSetting(
      "Режим: ",
      new BooleanSetting("Щиты", false),
      new BooleanSetting("Шипы 3", false),
      new BooleanSetting("Лук", false),
      new BooleanSetting("Тотемы", false),
      new BooleanSetting("НоДебафф", false),
      new BooleanSetting("Шары", true),
      new BooleanSetting("Классик", false),
      new BooleanSetting("Читерский рай", false),
      new BooleanSetting("Без эндер-жемчуга", false)
   );
   private final List<String> items = new ArrayList<>();
   private long timestamp = 0L;
   private long timestamp2 = 0L;
   private long timestamp3 = 0L;
   private static final Pattern PATTERN = Pattern.compile("^\\w{3,16}$");
   private static final String[] SCHITY = new String[]{
      "Щиты", "Шипы 3", "Лук", "Тотемы", "НоДебафф", "Шары", "Классик", "Читерский рай", "Без эндер-жемчуга"
   };

   public AutoDuel() {
      this.addSettings(new Setting[]{this.rezhim});
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.items.clear();
      this.timestamp = 0L;
      this.timestamp2 = System.currentTimeMillis();
      this.timestamp3 = 0L;
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player != null && CLIENT.getNetworkHandler() != null) {
         this.invoke();
         List items = this.resolve();
         long longValue = System.currentTimeMillis();
         if (longValue - this.timestamp2 > 800L * Math.max(items.size(), 1)) {
            this.items.clear();
            this.timestamp2 = longValue;
         }

         if (longValue - this.timestamp > 1000L) {
            for (String text : (List<String>)items) {
               if (!this.items.contains(text) && !text.equals(CLIENT.player.getGameProfile().getName())) {
                  CLIENT.getNetworkHandler().sendChatCommand("duel " + text);
                  this.items.add(text);
                  this.timestamp = longValue;
                  break;
               }
            }
         }
      }
   }

   @EventHandler
   public void onPacket(PacketEvent packetEvent) {
      if (packetEvent.getPacket() instanceof GameMessageS2CPacket gameMessageS2CPacket) {
         String text2 = gameMessageS2CPacket.content().getString().toLowerCase();
         if (text2.contains("начало") && text2.contains("через") && text2.contains("секунд")
            || text2.contains("дуэли » во время поединка запрещено использовать команды")
            || text2.contains("duel") && text2.contains("during") && text2.contains("forbidden")) {
            this.toggle();
         }
      }
   }

   private List<String> resolve() {
      return CLIENT.getNetworkHandler()
         .getPlayerList()
         .stream()
         .map(playerListEntry -> playerListEntry.getProfile().getName())
         .filter(string -> PATTERN.matcher(string).matches())
         .collect(Collectors.toList());
   }

   private void invoke() {
      if (CLIENT.currentScreen instanceof GenericContainerScreen genericContainerScreen) {
         String text3 = genericContainerScreen.getTitle().getString();
         long longValue2 = System.currentTimeMillis();
         if (text3.contains("Выбор набора") || text3.contains("Kit selection")) {
            if (longValue2 - this.timestamp3 > 90L) {
               ArrayList arrayList = new ArrayList();

               for (int intValue = 0; intValue < SCHITY.length; intValue++) {
                  if (this.rezhim.isEnabled(SCHITY[intValue])) {
                     arrayList.add(intValue);
                  }
               }

               if (!arrayList.isEmpty()) {
                  Collections.shuffle(arrayList);
                  int intValue2 = (Integer)arrayList.get(0);
                  CLIENT.interactionManager
                     .clickSlot(((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).syncId, intValue2, 0, SlotActionType.QUICK_MOVE, CLIENT.player);
                  this.timestamp3 = longValue2;
               }
            }
         } else if ((text3.contains("Настройка поединка") || text3.contains("Duel setup")) && longValue2 - this.timestamp3 > 90L) {
            CLIENT.interactionManager
               .clickSlot(((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).syncId, 0, 0, SlotActionType.QUICK_MOVE, CLIENT.player);
            this.timestamp3 = longValue2;
         }
      }
   }
}
