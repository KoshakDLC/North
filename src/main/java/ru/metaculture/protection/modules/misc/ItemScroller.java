package ru.metaculture.protection;

import lombok.Generated;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.lwjgl.glfw.GLFW;
import org.wild.mixin.acceser.HandledScreenAccessor;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "ItemScroller",
   description = "Ускоряет перекладывание",
   category = Category.Misc
)
public class ItemScroller extends Module {
   public final NumberSetting zaderzhka = new NumberSetting("Задержка", 10.0F, 0.0F, 100.0F, 1.0F, false);
   private static ItemScroller instance;
   private final DualTimer dualTimer = new DualTimer();

   public ItemScroller() {
      this.addSettings(new Setting[]{this.zaderzhka});
      instance = this;
   }

   @EventHandler
   public void onClientTick(ClientTickEvent clientTickEvent) {
      if (CLIENT.player != null && CLIENT.currentScreen != null) {
         if (CLIENT.currentScreen instanceof HandledScreen handledScreen) {
            if (CLIENT.getWindow() != null) {
               long longValue = CLIENT.getWindow().getHandle();
               boolean flag = GLFW.glfwGetKey(longValue, 340) == 1 || GLFW.glfwGetKey(longValue, 344) == 1;
               boolean flag2 = GLFW.glfwGetMouseButton(longValue, 0) == 1;
               if (flag && flag2) {
                  long longValue2 = (long)this.zaderzhka.getValue();
                  if (this.dualTimer.check5(longValue2)) {
                     double doubleValue = CLIENT.mouse.getX() * CLIENT.getWindow().getScaledWidth() / CLIENT.getWindow().getWidth();
                     double doubleValue2 = CLIENT.mouse.getY() * CLIENT.getWindow().getScaledHeight() / CLIENT.getWindow().getHeight();
                     Slot slot = ((HandledScreenAccessor)handledScreen).getSlotAtPosition(doubleValue, doubleValue2);
                     if (slot != null && slot.hasStack()) {
                        CLIENT.interactionManager.clickSlot(handledScreen.getScreenHandler().syncId, slot.id, 0, SlotActionType.QUICK_MOVE, CLIENT.player);
                        this.dualTimer.invoke();
                     }
                  }
               }
            }
         }
      }
   }

   @Generated
   public static ItemScroller getInstance() {
      return instance;
   }
}
