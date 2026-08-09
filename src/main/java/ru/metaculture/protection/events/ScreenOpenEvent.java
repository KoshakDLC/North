package ru.metaculture.protection;

import net.minecraft.client.gui.screen.Screen;

public class ScreenOpenEvent extends Event {
   private final Screen screen;
   private boolean flag;

   public ScreenOpenEvent(Screen screen) {
      this.screen = screen;
   }

   public Screen getScreen() {
      return this.screen;
   }

   public void invoke() {
      this.flag = true;
   }

   public boolean isFlag() {
      return this.flag;
   }
}
