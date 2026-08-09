package ru.metaculture.protection;

import lombok.Generated;
import net.minecraft.client.gui.screen.Screen;

public class ScreenHandlerOpenEvent extends Event {
   private Screen screen;
   private int intValue;

   @Generated
   public Screen getScreen() {
      return this.screen;
   }

   @Generated
   public int getIntValue() {
      return this.intValue;
   }

   @Generated
   public ScreenHandlerOpenEvent(Screen screen, int i) {
      this.screen = screen;
      this.intValue = i;
   }
}
