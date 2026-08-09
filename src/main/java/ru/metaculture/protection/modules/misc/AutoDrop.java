package ru.metaculture.protection;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "AutoDrop",
   category = Category.Misc,
   description = "Автоматически выбрасывает мусор"
)
public class AutoDrop extends Module {
   private final BooleanSetting kamen = new BooleanSetting("Камень", false);
   private final BooleanSetting bulyzhnik = new BooleanSetting("Булыжник", false);
   private final BooleanSetting granit = new BooleanSetting("Гранит", false);
   private final BooleanSetting palki = new BooleanSetting("Палки", false);
   private final BooleanSetting slanets = new BooleanSetting("Сланец", false);
   private final BooleanSetting andezit = new BooleanSetting("Андезит", false);
   private final BooleanSetting nezerak = new BooleanSetting("Незерак", false);
   private final BooleanSetting bazalt = new BooleanSetting("Базальт", false);
   private final BooleanSetting chernit = new BooleanSetting("Чернит", false);
   private final BooleanSetting blokiDush = new BooleanSetting("Блоки душ", false);
   private final BooleanSetting rudyAda = new BooleanSetting("Руды ада", false);
   private final BooleanSetting graviy = new BooleanSetting("Гравий", false);
   private int intValue = 9;
   private final DualTimer dualTimer = new DualTimer();

   public AutoDrop() {
      this.addSettings(
         new Setting[]{
            this.kamen,
            this.bulyzhnik,
            this.granit,
            this.palki,
            this.slanets,
            this.andezit,
            this.nezerak,
            this.bazalt,
            this.chernit,
            this.blokiDush,
            this.rudyAda,
            this.graviy
         }
      );
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      this.invoke();
   }

   private void invoke() {
      if (this.intValue > 44) {
         this.intValue = 9;
      } else {
         Slot slot = CLIENT.player.playerScreenHandler.getSlot(this.intValue);
         if (!slot.hasStack()) {
            this.intValue++;
         } else {
            Item item2 = slot.getStack().getItem();
            if (this.check(item2)) {
               int intValue = CLIENT.player.playerScreenHandler.syncId;
               CLIENT.interactionManager.clickSlot(intValue, this.intValue, 1, SlotActionType.THROW, CLIENT.player);
               this.intValue++;
            } else {
               this.intValue++;
            }
         }
      }
   }

   private boolean check(Item item) {
      if (item == Items.STONE && this.kamen.isEnabled()) {
         return true;
      } else if (item == Items.COBBLESTONE && this.bulyzhnik.isEnabled()) {
         return true;
      } else if (item == Items.GRANITE && this.granit.isEnabled()) {
         return true;
      } else if (item == Items.STICK && this.palki.isEnabled()) {
         return true;
      } else if (item == Items.ANDESITE && this.andezit.isEnabled()) {
         return true;
      } else if ((item == Items.DEEPSLATE || item == Items.COBBLED_DEEPSLATE) && this.slanets.isEnabled()) {
         return true;
      } else if (item == Items.NETHERRACK && this.nezerak.isEnabled()) {
         return true;
      } else if ((item == Items.BASALT || item == Items.SMOOTH_BASALT || item == Items.POLISHED_BASALT) && this.bazalt.isEnabled()) {
         return true;
      } else if ((item == Items.BLACKSTONE || item == Items.GILDED_BLACKSTONE) && this.chernit.isEnabled()) {
         return true;
      } else if ((item == Items.SOUL_SAND || item == Items.SOUL_SOIL) && this.blokiDush.isEnabled()) {
         return true;
      } else {
         return (item == Items.NETHER_QUARTZ_ORE || item == Items.NETHER_GOLD_ORE || item == Items.QUARTZ || item == Items.GOLD_NUGGET)
               && this.rudyAda.isEnabled()
            ? true
            : item == Items.GRAVEL && this.graviy.isEnabled();
      }
   }
}
