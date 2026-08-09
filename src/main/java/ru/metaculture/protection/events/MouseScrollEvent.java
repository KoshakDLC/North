package ru.metaculture.protection;

public final class MouseScrollEvent extends Event {
   private final long windowHandle;
   private final double horizontalOffset;
   private final double verticalOffset;
   private final double cursorX;
   private final double cursorY;

   public MouseScrollEvent(long windowHandle, double horizontalOffset, double verticalOffset, double cursorX, double cursorY) {
      this.windowHandle = windowHandle;
      this.horizontalOffset = horizontalOffset;
      this.verticalOffset = verticalOffset;
      this.cursorX = cursorX;
      this.cursorY = cursorY;
   }

   public long getWindowHandle() {
      return this.windowHandle;
   }

   public double getHorizontalOffset() {
      return this.horizontalOffset;
   }

   public double getVerticalOffset() {
      return this.verticalOffset;
   }

   public double getCursorX() {
      return this.cursorX;
   }

   public double getCursorY() {
      return this.cursorY;
   }
}
