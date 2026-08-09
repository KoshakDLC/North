package ru.metaculture.protection;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.Perspective;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "FreeLock",
   category = Category.Misc,
   description = "Вид от третьего лица"
)
public class FreeLock extends Module {
   private final ModeSetting rezhim = new ModeSetting("Режим", "По удержанию", "По удержанию", "По бинду");
   private final KeybindSetting bind = new KeybindSetting("Бинд", -1, true);
   private Perspective perspective;

   public FreeLock() {
      this.addSettings(new Setting[]{this.rezhim, this.bind});
   }

   public boolean check() {
      return this.perspective != null;
   }

   @EventHandler
   public void onRawInput(RawInputEvent rawInputEvent) {
      if (!ServerModeDetector.check()) {
         if (this.check2(CLIENT.currentScreen)) {
            this.invoke2();
         } else if (rawInputEvent.getKeyCode() == this.bind.getKeyCode()) {
            boolean flag = KeybindSetting.isPressed(this.bind.getKeyCode());
            if (this.rezhim.is("По удержанию")) {
               if (flag && this.perspective == null) {
                  this.invoke();
               } else if (!flag && this.perspective != null) {
                  this.invoke2();
               }
            } else if (this.rezhim.is("По бинду") && flag) {
               if (this.perspective != null) {
                  this.invoke2();
               } else {
                  this.invoke();
               }
            }
         }
      }
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (this.check2(CLIENT.currentScreen)) {
         this.invoke2();
      }
   }

   @Override
   public void onEnable() {
      super.onEnable();
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.invoke2();
   }

   private void invoke() {
      if (!ServerModeDetector.check() && !this.check2(CLIENT.currentScreen)) {
         AttackAura attackAura = WildClient.INSTANCE.moduleManager.getModule(AttackAura.class);
         if (attackAura != null && attackAura.enabled) {
            ChatUtil.sendClientMessage("Отключите ауру для использования фрилука");
         } else if (CLIENT.options != null) {
            this.perspective = CLIENT.options.getPerspective();
            CLIENT.options.setPerspective(Perspective.THIRD_PERSON_BACK);
            FreeLookController.floatValue = CLIENT.gameRenderer.getCamera().getYaw();
            FreeLookController.floatValue2 = CLIENT.gameRenderer.getCamera().getPitch();
            FreeLookController.flag = true;
            FreeLookController.active = true;
         }
      }
   }

   private void invoke2() {
      if (this.perspective != null) {
         if (CLIENT.options != null) {
            CLIENT.options.setPerspective(this.perspective);
         }

         this.perspective = null;
         FreeLookController.flag = false;
         FreeLookController.active = false;
      }
   }

   private boolean check2(Screen screen) {
      return screen != null;
   }
}
