package ru.metaculture.protection;

import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "CameraClip",
   description = "Камера проходит сквозь блоки",
   category = Category.Player
)
public class CameraClip extends Module {
   @EventHandler
   public void onCameraClip(CameraClipEvent cameraClipEvent) {
      cameraClipEvent.invalidate();
   }
}
