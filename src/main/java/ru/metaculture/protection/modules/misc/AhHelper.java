package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "AhHelper",
   description = "Помощник на аукционе",
   category = Category.Misc
)
public class AhHelper extends Module {
   public static KeybindSetting poiskPredmetaVRuke = new KeybindSetting("Поиск предмета в руке", -1);
   public static BooleanSetting pokazSamyhDeshevyhPredmetov = new BooleanSetting("Показ самых дешевых предметов", true);
   public static BooleanSetting pokazyvatPoTsenam = new BooleanSetting("Показывать по ценам", true);
   public static final List<Integer> ITEMS = new ArrayList<>();
   private static AhHelper instance;
   private static String text = "";
   private static long timestamp;
   private static boolean flag;
   private static boolean flag2;
   private static boolean flag3;
   private static long timestamp2;
   private final ResettableTimer resettableTimer = new ResettableTimer();

   public AhHelper() {
      instance = this;
      this.addSettings(new Setting[]{poiskPredmetaVRuke, pokazSamyhDeshevyhPredmetov, pokazyvatPoTsenam});
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      invoke6();
      if (CLIENT.player != null && pokazSamyhDeshevyhPredmetov.isEnabled()) {
         if (CLIENT.currentScreen instanceof HandledScreen handledScreen2) {
            if (check(handledScreen2)) {
               this.invoke(handledScreen2);
            } else {
               ITEMS.clear();
            }
         } else {
            ITEMS.clear();
         }
      } else {
         ITEMS.clear();
      }
   }

   public static boolean check(HandledScreen<?> handledScreen) {
      if (handledScreen == null) {
         return false;
      } else {
         String text = resolve(handledScreen.getTitle().getString());
         return check3(text);
      }
   }

   public static boolean check2(HandledScreen<?> handledScreen) {
      if (handledScreen == null) {
         return false;
      } else {
         String text2 = resolve(handledScreen.getTitle().getString());
         return text2.contains("поиск:")
            || text2.contains("search:")
            || text2.contains("п:")
            || text2.contains("漢:")
            || text2.contains("\ud83d\udd0e")
            || text2.contains("\ud83d\udd0d");
      }
   }

   private static boolean check3(String string) {
      return string.contains("аукцион")
         || string.contains("auction")
         || string.contains("поиск:")
         || string.contains("search:")
         || string.contains("п:")
         || string.contains("漢:")
         || string.contains("\ud83d\udd0e:")
         || string.contains("\ud83d\udd0d:");
   }

   private static String resolve(String string) {
      return string == null
         ? ""
         : string.replaceAll("(?i)§.", "").replace(' ', ' ').replace('：', ':').replaceAll("\\s*:\\s*", ":").trim().toLowerCase(Locale.ROOT);
   }

   private void invoke(HandledScreen<?> handledScreen) {
      ITEMS.clear();
      int[] intValues = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE};
      int[] intValues2 = new int[]{-1, -1, -1, -1};

      for (Slot slot2 : handledScreen.getScreenHandler().slots) {
         if (!check4(handledScreen, slot2)) {
            int intValue = AuctionSellerParser.compute(slot2);
            if (intValue > 0) {
               for (int intValue2 = 0; intValue2 < 4; intValue2++) {
                  if (intValue < intValues[intValue2]) {
                     for (int intValue3 = 3; intValue3 > intValue2; intValue3--) {
                        intValues[intValue3] = intValues[intValue3 - 1];
                        intValues2[intValue3] = intValues2[intValue3 - 1];
                     }

                     intValues[intValue2] = intValue;
                     intValues2[intValue2] = slot2.id;
                     break;
                  }
               }
            }
         }
      }

      for (int intValue4 = 0; intValue4 < 4; intValue4++) {
         if (intValues2[intValue4] != -1) {
            ITEMS.add(intValues2[intValue4]);
         }
      }
   }

   @EventHandler
   public void onRawInput(RawInputEvent rawInputEvent) {
      if (this.resettableTimer.check3(300L) && rawInputEvent.getKeyCode() == poiskPredmetaVRuke.getKeyCode()) {
         this.invoke2();
         this.resettableTimer.invoke();
      }
   }

   private void invoke2() {
      if (CLIENT.player != null) {
         ItemStack itemStack = CLIENT.player.getMainHandStack();
         if (!itemStack.isEmpty()) {
            String text3 = this.resolve2(itemStack.getName().getString());
            String text4 = this.resolve3(text3);
            if (!text4.equals(text3)) {
               CLIENT.player.networkHandler.sendChatCommand("ah search " + text4);
            } else {
               if (text3.isEmpty()) {
                  text3 = itemStack.getItem().getName().getString();
               }

               if (!text3.isEmpty()) {
                  CLIENT.player.networkHandler.sendChatCommand("ah search " + text3);
               }
            }
         }
      }
   }

   private String resolve2(String string) {
      return string == null
         ? ""
         : string.replaceAll("(?i)§.", "")
            .replaceAll("(?i)&.", "")
            .replaceAll("\\[[^\\]]*]", " ")
            .replaceAll("[★✦✧✪✫✬✭✮✯✰❄☃⚒☠❤❣♕♛♜♞♟]", " ")
            .replace("xxx", " ")
            .replaceAll("\\s+", " ")
            .trim();
   }

   public String resolve3(String string) {
      if (string == null) {
         return "";
      } else if (string.contains("Рассадник монстров")) {
         return "Спавнер";
      } else if (string.contains("TIER WHITE")) {
         return "вайт";
      } else if (string.contains("TIER BLACK")) {
         return "блэк";
      } else {
         return string.contains("Прогрузчик чанков [1x1]") ? "Прогрузчик чанков" : string;
      }
   }

   public static void invoke3(String string, long l) {
      text = resolve4(string);
      timestamp = l;
      flag = !text.isEmpty() && l > 0L;
      flag2 = true;
      flag3 = false;
      timestamp2 = System.currentTimeMillis();
   }

   public static void invoke4(long l) {
      text = "";
      timestamp = l;
      flag = l > 0L;
      flag2 = false;
      flag3 = false;
      timestamp2 = System.currentTimeMillis();
   }

   public static void invoke5() {
      text = "";
      timestamp = 0L;
      flag = false;
      flag2 = false;
      flag3 = false;
      timestamp2 = 0L;
      ITEMS.clear();
   }

   public static boolean isFlag() {
      return flag;
   }

   public static String getText() {
      return text;
   }

   public static long getTimestamp() {
      return timestamp;
   }

   public static boolean check4(HandledScreen<?> handledScreen, Slot slot) {
      if (instance == null || !instance.enabled || !pokazyvatPoTsenam.isEnabled() || !flag) {
         return false;
      } else if (handledScreen != null && slot != null && slot.hasStack() && check5(handledScreen)) {
         if (CLIENT.player != null && slot.inventory == CLIENT.player.getInventory()) {
            return false;
         } else {
            int intValue5 = AuctionSellerParser.compute(slot);
            return intValue5 <= 0 ? false : intValue5 > timestamp;
         }
      } else {
         return false;
      }
   }

   private static void invoke6() {
      if (flag) {
         if (CLIENT.currentScreen instanceof HandledScreen handledScreen3) {
            if (check5(handledScreen3)) {
               flag3 = true;
            } else {
               if (flag3 || System.currentTimeMillis() - timestamp2 > 5000L) {
                  invoke5();
               }
            }
         } else {
            if (flag3 || System.currentTimeMillis() - timestamp2 > 5000L) {
               invoke5();
            }
         }
      }
   }

   private static boolean check5(HandledScreen<?> handledScreen) {
      return flag2 ? check2(handledScreen) : check(handledScreen);
   }

   private static String resolve4(String string) {
      return string == null
         ? ""
         : string.replaceAll("(?i)§.", "")
            .replaceAll("(?i)&.", "")
            .replace(' ', ' ')
            .replace('_', ' ')
            .replace('-', ' ')
            .replaceAll("(?i)\\bminecraft:", "")
            .replaceAll("[^\\p{L}\\p{N}: ]", " ")
            .replaceAll("\\s+", " ")
            .trim()
            .toLowerCase(Locale.ROOT);
   }
}
