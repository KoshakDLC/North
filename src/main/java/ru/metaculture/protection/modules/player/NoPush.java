package ru.metaculture.protection;

import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "NoPush",
   description = "Убирает отталкивания от игроков, мобов и блоков",
   category = Category.Player
)
public class NoPush extends Module {
   public BooleanSetting players = new BooleanSetting("Players", true);
   public BooleanSetting mobs = new BooleanSetting("Mobs", true);
   public BooleanSetting blocks = new BooleanSetting("Blocks", true);

   public NoPush() {
      this.addSettings(new Setting[]{this.players, this.mobs, this.blocks});
   }
}
