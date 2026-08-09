package ru.metaculture.protection;

public final class RawInputEvent extends Event {
   private final long windowHandle;
   private final int keyCode;
   private final int scanCode;
   private final int action;
   private final int modifiers;

   public RawInputEvent(long windowHandle, int keyCode, int scanCode, int action, int modifiers) {
      this.windowHandle = windowHandle;
      this.keyCode = keyCode;
      this.scanCode = scanCode;
      this.action = action;
      this.modifiers = modifiers;
   }

   public long getWindowHandle() {
      return this.windowHandle;
   }

   public int getKeyCode() {
      return this.keyCode;
   }

   public int getScanCode() {
      return this.scanCode;
   }

   public int getAction() {
      return this.action;
   }

   public int getModifiers() {
      return this.modifiers;
   }
}
