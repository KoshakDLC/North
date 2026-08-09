package ru.metaculture.protection;

import java.nio.file.Files;
import java.nio.file.Path;
import net.minecraft.text.Text;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleAccess(
   usernames = {"lichoday"}
)
@ModuleRegister(
   name = "RotationLab",
   category = Category.Player,
   description = "Тренажёр человеческих паттернов ротации",
   riskLevels = {ModuleRiskLevel.NEW}
)
public class RotationLab extends Module {
   private final TextSetting asset = new TextSetting("Asset", "rotation_lab").resolve(48);
   private final ButtonSetting deleteAsset = new ButtonSetting("Delete Asset", 0).setRun("Delete").setRunnable(this::invoke3);
   private final ModeSetting mode = new ModeSetting("Mode", "Mixed", "Mixed", "Flick", "Tracking", "Micro", "Vertical", "Diagonal", "Idle", "Attack");
   private final BooleanSetting autoCapture = new BooleanSetting("Auto Capture", true);
   private final NumberSetting targetRadius = new NumberSetting("Target Radius", 11.0F, 5.0F, 30.0F, 1.0F, false);
   private final NumberSetting spread = new NumberSetting("Spread", 78.0F, 25.0F, 95.0F, 1.0F, false);
   private final NumberSetting targets = new NumberSetting("Targets", 80.0F, 5.0F, 500.0F, 1.0F, false);
   private RotationLabScreen rotationLabScreen;

   public RotationLab() {
      this.addSettings(
         new Setting[]{
            this.asset, this.deleteAsset, this.mode, this.autoCapture, this.targetRadius, this.spread, this.targets
         }
      );
   }

   @Override
   public void onEnable() {
      super.onEnable();
      if (CLIENT != null) {
         CLIENT.execute(this::invoke);
      }
   }

   @Override
   public void onDisable() {
      if (this.rotationLabScreen != null) {
         this.rotationLabScreen.invoke();
      }

      if (this.rotationLabScreen != null && CLIENT.currentScreen == this.rotationLabScreen) {
         CLIENT.setScreen(null);
      }

      this.rotationLabScreen = null;
      super.onDisable();
   }

   private void invoke() {
      if (this.enabled && CLIENT.getWindow() != null) {
         this.rotationLabScreen = new RotationLabScreen(this);
         CLIENT.setScreen(this.rotationLabScreen);
         if (CLIENT.player != null) {
            CLIENT.player.sendMessage(Text.of("RotationLab opened"), true);
         }
      }
   }

   public void invoke2(RotationLabScreen rotationLabScreen) {
      if (this.rotationLabScreen == rotationLabScreen) {
         this.rotationLabScreen = null;
      }

      if (this.enabled) {
         this.setEnabled(false);
      }
   }

   public String resolve() {
      return this.asset.getValue();
   }

   public String resolve2() {
      return this.mode.getValue();
   }

   public boolean check() {
      return this.autoCapture.isEnabled();
   }

   public int compute() {
      return Math.max(5, Math.round(this.targetRadius.getValue()));
   }

   public float measure() {
      return Math.max(0.25F, Math.min(0.95F, this.spread.getValue() / 100.0F));
   }

   public int compute2() {
      return Math.max(1, Math.round(this.targets.getValue()));
   }

   public void invoke3() {
      Path path = RotationAssetLoader.resolve4(this.resolve());

      try {
         if (this.rotationLabScreen != null) {
            this.rotationLabScreen.invoke2();
         }

         if (Files.deleteIfExists(path)) {
            ChatUtil.sendClientMessage("[RotationLab] Deleted " + path.getFileName());
         } else {
            ChatUtil.sendClientMessage("[RotationLab] Asset not found: " + path.getFileName());
         }
      } catch (Throwable exception) {
         ChatUtil.sendClientMessage("[RotationLab] Delete failed: " + exception.getClass().getSimpleName());
      }
   }
}
