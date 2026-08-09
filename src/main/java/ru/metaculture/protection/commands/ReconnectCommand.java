package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardDisplaySlot;
import net.minecraft.scoreboard.ScoreboardEntry;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.Team;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import ru.metaculture.sdk.Compile;
import ru.metaculture.sdk.Loader;

public final class ReconnectCommand extends Command {
   private static final int INT_VALUE = 1;
   private static final int INT_VALUE_2 = 66;
   private static final long TIMESTAMP = 180L;
   private static final long TIMESTAMP_2 = 650L;
   private static final long TIMESTAMP_3 = 20000L;
   private static final long TIMESTAMP_4 = 300000L;
   private static final long TIMESTAMP_5 = 600000L;
   private static ReconnectCommand instance;
   private static final Pattern PATTERN = Pattern.compile(
      "(?iu)(?:клан\\s*лайт|кланлайт|clan\\s*lite|clanlite|лайт|lite|анарх(?:ия|ии)?|anarchy)[^\\d#№]{0,24}[#№]?\\s*(\\d{1,2})(?!\\d)"
   );
   private static final Pattern PATTERN_2 = Pattern.compile("(?u)[#№]\\s*(\\d{1,2})(?!\\d)");
   private int intValue = -1;
   private boolean flag;
   private boolean flag2;
   private boolean flag3;
   private long timestamp;
   private long timestamp2;
   private long timestamp3;
   private long timestamp4;
   private boolean flag4;
   private long timestamp5;

   public ReconnectCommand() {
      super("rct", "Перезаход на выбранную Лайт анархию", ".rct [1-66]");
      instance = this;
   }

   public static ReconnectCommand getInstance() {
      return instance;
   }

   public void invoke(boolean bl) {
      if (this.flag4 != bl) {
         this.flag4 = bl;
         this.timestamp5 = bl ? this.compute() : 0L;
      }
   }

   private long compute() {
      return System.currentTimeMillis() + ThreadLocalRandom.current().nextLong(300000L, 600001L);
   }

   private void invoke2() {
      if (this.flag4) {
         if (this.timestamp5 == 0L) {
            this.timestamp5 = this.compute();
         } else if (System.currentTimeMillis() >= this.timestamp5) {
            int intValue = this.compute2(this.compute3());
            this.timestamp5 = this.compute();
            this.execute(new String[]{String.valueOf(intValue)});
         }
      }
   }

   private int compute2(int i) {
      byte byteValue = 66;
      if (byteValue <= 1) {
         return 1;
      } else {
         int intValue2;
         do {
            intValue2 = 1 + ThreadLocalRandom.current().nextInt(byteValue);
         } while (intValue2 == i);

         return intValue2;
      }
   }

   @Compile
   @Override
   public void execute(String[] strings) {
      int intValue3;
      if (strings.length == 0) {
         intValue3 = this.compute3();
      } else if (strings.length == 1) {
         intValue3 = this.compute6(strings[0]);
      } else {
         this.invoke10();
         return;
      }

      if (!this.check(intValue3)) {
         this.invoke10();
         return;
      }

      if (a_.player == null || a_.world == null) {
         ChatUtil.sendClientMessage("§c[RCT] Игрок не подключён к миру.");
         return;
      }

      this.invoke9();
      this.intValue = intValue3;
      this.flag = true;
      this.timestamp = System.currentTimeMillis();
      this.timestamp2 = 0L;
      ChatUtil.sendClientMessage("§a[RCT] Подключаю к Лайт анархии #" + intValue3 + "...");
      this.invoke6();
   }

   @EventHandler
   public void onClientTick(ClientTickEvent clientTickEvent) {
      if (a_.player != null && a_.world != null && a_.interactionManager != null) {
         if (!this.flag) {
            this.invoke2();
         } else {
            long longValue = System.currentTimeMillis();
            if (longValue - this.timestamp > 20000L) {
               this.invoke8("Истекло время ожидания меню или подключения.");
            } else if (this.flag3 && this.compute3() == this.intValue) {
               this.invoke7();
            } else if (this.flag3 && longValue - this.timestamp3 > 8000L) {
               this.invoke8("Сервер не подтвердил подключение к анархии #" + this.intValue + ".");
            } else if (a_.currentScreen instanceof GenericContainerScreen genericContainerScreen) {
               this.invoke3(genericContainerScreen, longValue);
            } else {
               if (longValue - this.timestamp2 >= 650L) {
                  this.invoke6();
                  this.timestamp2 = longValue;
               }
            }
         }
      }
   }

   private void invoke3(GenericContainerScreen genericContainerScreen, long l) {
      if (l - this.timestamp2 >= 180L) {
         String text = this.resolve5(genericContainerScreen.getTitle().getString());
         if (!text.contains("выберите режим") && !text.contains("select mode")) {
            if (text.contains("выбор лайт анархии") || text.contains("lite anarchy")) {
               Slot modeSlot = this.resolve2(genericContainerScreen);
               if (modeSlot == null) {
                  if (this.flag2 && l - this.timestamp2 >= 1200L) {
                     this.flag2 = false;
                  }

                  if (!this.flag2) {
                     List items = this.resolve3(genericContainerScreen)
                        .stream()
                        .filter(slot -> slot.getStack().isOf(Items.ARMOR_STAND))
                        .sorted(Comparator.comparingInt(slot -> slot.id))
                        .toList();
                     int intValue4 = this.compute7(this.intValue);
                     if (intValue4 >= 0 && intValue4 < items.size()) {
                        this.invoke4(genericContainerScreen, (Slot)items.get(intValue4));
                        this.flag2 = true;
                        this.timestamp2 = l;
                     }
                  }
               } else {
                  if (!this.flag3 || l - this.timestamp2 >= 1200L) {
                     this.invoke4(genericContainerScreen, modeSlot);
                     this.flag3 = true;
                     this.timestamp3 = l;
                     this.timestamp2 = l;
                  }
               }
            }
         } else {
            if (this.timestamp4 == 0L) {
               this.timestamp4 = l;
            }

            Slot slot2 = this.resolve(genericContainerScreen);
            if (slot2 != null) {
               this.invoke5(genericContainerScreen, slot2, SlotActionType.PICKUP);
               this.flag2 = false;
               this.timestamp4 = 0L;
               this.timestamp2 = l;
            } else if (l - this.timestamp4 >= 3000L) {
               this.invoke8("Режим Лайт отсутствует в меню выбора.");
            }
         }
      }
   }

   private Slot resolve(GenericContainerScreen genericContainerScreen) {
      Slot slot3 = null;

      for (Slot slot4 : this.resolve3(genericContainerScreen)) {
         ItemStack itemStack = slot4.getStack();
         if (itemStack.isOf(Items.PLAYER_HEAD)) {
            String text2 = this.resolve5(itemStack.getName().getString());
            if (text2.equals("лайт") || text2.equals("lite")) {
               return slot4;
            }

            String text3 = this.resolve4(itemStack);
            if ((text3.contains("анархия лайт") || text3.contains("lite anarchy"))
               && (text3.matches("(?s).*анархия\\s*1\\D+16.*") || text3.matches("(?s).*anarchy\\s*1\\D+16.*"))) {
               slot3 = slot4;
            }
         }
      }

      return slot3;
   }

   private Slot resolve2(GenericContainerScreen genericContainerScreen) {
      Pattern pattern = Pattern.compile("(?iu)#\\s*0*" + this.intValue + "(?!\\d)");

      for (Slot slot5 : this.resolve3(genericContainerScreen)) {
         ItemStack itemStack2 = slot5.getStack();
         if (!itemStack2.isEmpty() && !itemStack2.isOf(Items.ARMOR_STAND) && pattern.matcher(this.resolve4(itemStack2)).find()) {
            return slot5;
         }
      }

      return null;
   }

   private List<Slot> resolve3(GenericContainerScreen genericContainerScreen) {
      ArrayList arrayList = new ArrayList();
      ScreenHandler screenHandler = genericContainerScreen.getScreenHandler();

      for (Slot slot6 : screenHandler.slots) {
         if (a_.player == null || slot6.inventory != a_.player.getInventory()) {
            arrayList.add(slot6);
         }
      }

      return arrayList;
   }

   private String resolve4(ItemStack itemStack) {
      StringBuilder stringBuilder = new StringBuilder(itemStack.getName().getString());
      LoreComponent loreComponent = (LoreComponent)itemStack.get(DataComponentTypes.LORE);
      if (loreComponent != null) {
         for (Text text4 : loreComponent.lines()) {
            stringBuilder.append(' ').append(text4.getString());
         }
      }

      return this.resolve5(stringBuilder.toString());
   }

   private void invoke4(GenericContainerScreen genericContainerScreen, Slot slot) {
      this.invoke5(genericContainerScreen, slot, SlotActionType.QUICK_MOVE);
   }

   private void invoke5(GenericContainerScreen genericContainerScreen, Slot slot, SlotActionType slotActionType) {
      a_.interactionManager.clickSlot(((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).syncId, slot.id, 0, slotActionType, a_.player);
   }

   @EventHandler
   public void onPacket(PacketEvent packetEvent) {
      if (this.flag && packetEvent.getPacketEventState() == PacketEvent.PacketEventState.RECEIVE) {
         if (packetEvent.getPacket() instanceof GameMessageS2CPacket gameMessageS2CPacket) {
            String text5 = this.resolve5(gameMessageS2CPacket.content().getString());
            if (!text5.isEmpty()) {
               if (!this.flag3 || !text5.contains("вы уже подключены к этому серверу") && !text5.contains("already connected to this server")) {
                  if (this.check2(text5)) {
                     this.invoke8("Подключение не выполнено: " + gameMessageS2CPacket.content().getString());
                  }
               } else {
                  this.invoke7();
               }
            }
         }
      }
   }

   private void invoke6() {
      PlayerInventory playerInventory = a_.player.getInventory();

      for (int intValue5 = 0; intValue5 < 9; intValue5++) {
         if (playerInventory.getStack(intValue5).isOf(Items.COMPASS)) {
            if (playerInventory.getSelectedSlot() != intValue5) {
               playerInventory.setSelectedSlot(intValue5);
               a_.player.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(intValue5));
            }

            a_.interactionManager.interactItem(a_.player, Hand.MAIN_HAND);
            return;
         }
      }
   }

   private int compute3() {
      int intValue6 = this.compute4();
      if (this.check(intValue6)) {
         return intValue6;
      } else {
         ServerStatsParser.INSTANCE.invoke2();
         return this.compute6(ServerStatsParser.INSTANCE.getNA2());
      }
   }

   private int compute4() {
      if (a_.world == null) {
         return -1;
      } else {
         Scoreboard scoreboard = a_.world.getScoreboard();
         ScoreboardObjective scoreboardObjective = scoreboard.getObjectiveForSlot(ScoreboardDisplaySlot.SIDEBAR);
         if (scoreboardObjective == null) {
            return -1;
         } else {
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(scoreboardObjective.getDisplayName().getString());

            for (ScoreboardEntry scoreboardEntry : scoreboard.getScoreboardEntries(scoreboardObjective)) {
               Team team = scoreboard.getScoreHolderTeam(scoreboardEntry.owner());
               arrayList2.add(Team.decorateName(team, Text.literal(scoreboardEntry.owner())).getString());
            }

            for (String text6 : (List<String>)arrayList2) {
               int intValue7 = this.compute5(PATTERN, text6);
               if (this.check(intValue7)) {
                  return intValue7;
               }
            }

            for (String text7 : (List<String>)arrayList2) {
               int intValue8 = this.compute5(PATTERN_2, text7);
               if (this.check(intValue8)) {
                  return intValue8;
               }
            }

            return -1;
         }
      }
   }

   private int compute5(Pattern pattern, String string) {
      Matcher matcher = pattern.matcher(this.resolve5(string));
      return !matcher.find() ? -1 : this.compute6(matcher.group(1));
   }

   private boolean check(int i) {
      return i >= 1 && i <= 66;
   }

   private int compute6(String string) {
      if (string == null) {
         return -1;
      } else {
         String text8 = string.replaceAll("\\D+", "");
         if (text8.isEmpty()) {
            return -1;
         } else {
            try {
               return Integer.parseInt(text8);
            } catch (NumberFormatException numberFormatException) {
               return -1;
            }
         }
      }
   }

   private int compute7(int i) {
      if (i <= 15) {
         return 0;
      } else if (i <= 31) {
         return 1;
      } else {
         return i <= 47 ? 2 : 3;
      }
   }

   private boolean check2(String string) {
      return string.contains("сервер заполнен")
         || string.contains("были кикнуты при подключении")
         || string.contains("не удалось подключ")
         || string.contains("ошибка подключения")
         || string.contains("сервер недоступен")
         || string.contains("нет свободных слотов")
         || string.contains("failed to connect")
         || string.contains("could not connect")
         || string.contains("server is full")
         || string.contains("server unavailable");
   }

   private String resolve5(String string) {
      return string == null ? "" : string.replaceAll("(?i)§.", "").replace(' ', ' ').replaceAll("\\s+", " ").trim().toLowerCase(Locale.ROOT);
   }

   private void invoke7() {
      this.invoke9();
   }

   private void invoke8(String string) {
      ChatUtil.sendClientMessage("§c[RCT] " + string);
      this.invoke9();
   }

   private void invoke9() {
      this.flag = false;
      this.flag2 = false;
      this.flag3 = false;
      this.intValue = -1;
      this.timestamp = 0L;
      this.timestamp2 = 0L;
      this.timestamp3 = 0L;
      this.timestamp4 = 0L;
   }

   private void invoke10() {
      ChatUtil.sendClientMessage("§cИспользование: " + this.getUsage());
      ChatUtil.sendClientMessage("§7Без номера команда использует текущую анархию из scoreboard.");
   }

   static {
      Loader.initialize();
   }
}
