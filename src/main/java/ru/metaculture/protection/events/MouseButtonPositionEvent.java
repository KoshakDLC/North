package ru.metaculture.protection;

public final class MouseButtonPositionEvent extends Event {
   private final long windowHandle;
   private final int button;
   private final int action;
   private final int modifiers;
   private final double cursorX;
   private final double cursorY;

   public MouseButtonPositionEvent(long windowHandle, int button, int action, int modifiers, double cursorX, double cursorY) {
      this.windowHandle = windowHandle;
      this.button = button;
      this.action = action;
      this.modifiers = modifiers;
      this.cursorX = cursorX;
      this.cursorY = cursorY;
   }

   public long getWindowHandle() {
      return this.windowHandle;
   }

   public int getButton() {
      return this.button;
   }

   public int getAction() {
      return this.action;
   }

   public int getModifiers() {
      return this.modifiers;
   }

   public double getCursorX() {
      return this.cursorX;
   }

   public double getCursorY() {
      return this.cursorY;
   }

   public boolean isPress() {
      return this.action == 1;
   }

   public boolean isRelease() {
      return this.action == 0;
   }
}
