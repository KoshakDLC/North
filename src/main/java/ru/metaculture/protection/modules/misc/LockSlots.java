package ru.metaculture.protection;

import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "LockSlots",
   category = Category.Misc,
   description = "Блокирует выкидывание выбранных слотов хотбара"
)
public class LockSlots extends Module {
   public final GroupSetting sloty = new GroupSetting(
      "Слоты: ",
      new BooleanSetting("1", false),
      new BooleanSetting("2", false),
      new BooleanSetting("3", false),
      new BooleanSetting("4", false),
      new BooleanSetting("5", false),
      new BooleanSetting("6", false),
      new BooleanSetting("7", false),
      new BooleanSetting("8", false),
      new BooleanSetting("9", false)
   );
   private final BooleanSetting rabotatTolkoVKd = new BooleanSetting("Работать только в КД", false);

   public LockSlots() {
      this.addSettings(new Setting[]{this.sloty, this.rabotatTolkoVKd});
   }

   public boolean check(int i) {
      if (i < 0 || i > 8) {
         return false;
      } else {
         return !this.check2() ? false : this.sloty.isEnabled(i);
      }
   }

   private boolean check2() {
      return !this.rabotatTolkoVKd.isEnabled() || ServerStatsParser.check();
   }
}
