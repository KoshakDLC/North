package ru.metaculture.protection;

import java.util.HashSet;
import java.util.Set;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.PlayerInput;

public class InputUtils implements MinecraftAccessor {
   private static final InputUtils INSTANCE = new InputUtils();
   public final Set<String> values = new HashSet<>();

   private InputUtils() {
   }

   public static InputUtils getINSTANCE() {
      return INSTANCE;
   }

   public void invoke(String string) {
      if (a_.player != null && a_.player.isAlive() && a_.world != null) {
         AttackAura.flag = true;
         this.values.add(string);
         this.invoke3(false);
         if (a_.player.isSprinting()) {
            a_.player.setSprinting(false);
         }

         if (a_.player.input != null) {
            a_.player.input.playerInput = PlayerInput.DEFAULT;
         }
      }
   }

   public void invoke2(String string) {
      if (a_.player != null && a_.player.isAlive() && a_.world != null) {
         this.values.remove(string);
         if (this.values.isEmpty() && a_.currentScreen == null) {
            this.invoke3(true);
            AttackAura.flag = false;
         }
      }
   }

   private void invoke3(boolean bl) {
      if (a_.options != null && a_.getWindow() != null) {
         KeyBinding[] keyBindings = new KeyBinding[]{
            a_.options.forwardKey, a_.options.backKey, a_.options.leftKey, a_.options.rightKey, a_.options.jumpKey, a_.options.sprintKey
         };
         long longValue = a_.getWindow().getHandle();

         for (KeyBinding keyBinding : keyBindings) {
            boolean flag = bl && InputUtil.isKeyPressed(longValue, keyBinding.getDefaultKey().getCode());
            keyBinding.setPressed(flag);
         }
      }
   }
}
