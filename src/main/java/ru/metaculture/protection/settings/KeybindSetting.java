package ru.metaculture.protection;

import java.util.function.Supplier;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeybindSetting extends Setting {
   public int keyCode;
   public String displayText;
   public boolean allowMouseButtons;
   public boolean waitingForBind;
   private final int defaultKeyCode;
   private final boolean defaultAllowMouseButtons;

   public KeybindSetting(String string, int i, boolean bl) {
      this.name = string;
      this.keyCode = i;
      this.allowMouseButtons = bl;
      this.defaultKeyCode = i;
      this.defaultAllowMouseButtons = bl;
   }

   public KeybindSetting(String string, int i) {
      this(string, i, false);
   }

   public int getKeyCode() {
      return this.keyCode;
   }

   public void setKeyCode(int i) {
      this.keyCode = i;
   }

   public KeybindSetting visibleWhen(Supplier<Boolean> supplier) {
      this.visibilityCondition = supplier;
      return this;
   }

   @Override
   public void resetToDefault() {
      this.keyCode = this.defaultKeyCode;
      this.allowMouseButtons = this.defaultAllowMouseButtons;
      this.waitingForBind = false;
   }

   public static boolean isPressed(int i) {
      if (MinecraftAccessor.a_.currentScreen != null) {
         return false;
      } else {
         long longValue = MinecraftAccessor.a_.getWindow().getHandle();
         if (i >= 0) {
            return InputUtil.isKeyPressed(longValue, i);
         } else if (i <= -100) {
            int intValue = -i - 100;
            return GLFW.glfwGetMouseButton(longValue, intValue) == 1;
         } else {
            return false;
         }
      }
   }
}
