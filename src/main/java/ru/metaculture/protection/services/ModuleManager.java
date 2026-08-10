package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.stream.Collectors;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;
import org.wild.module.api.Module;
import ru.metaculture.profile.Profile;
import ru.metaculture.profile.Role;
import ru.metaculture.sdk.Compile;
import ru.metaculture.sdk.Loader;

public class ModuleManager {
   public final ArrayList<Module> modules = new ArrayList<>();

   public ModuleManager() {
      this.registerModules();
   }

   @Compile
   public void registerModules() {
      Class<?>[] classValues = new Class<?>[]{
         ActionRecorder.class, AhHelper.class, AirStuck.class, AncientXray.class, Animations.class, AntiAFK.class,
         AntiBot.class, AntiCrystal.class, AppleFarmer.class, Arrows.class, AspectRatio.class, AtmoDawnFog.class,
         AttackAura.class, AttackEffect.class, AutoAccept.class, AutoAncientBot.class, AutoAuth.class, AutoBuy.class,
         AutoClan.class, AutoCraft.class, AutoCopperDange.class, AutoCristal.class, AutoDrop.class, AutoDuel.class, AutoExp.class,
         AutoFish.class, AutoGApple.class, AutoInvisible.class, AutoLeave.class, AutoLes.class, AutoMace.class,
         AutoMine.class, AutoPotion.class, AutoPottBot.class, AutoResell.class, AutoSell.class, AutoSwap.class,
         AutoTool.class, AutoTotem.class, AutoVillageTrade.class, AutoWood.class, BaseFinder.class, Blink.class,
         BlockESP.class, BlockOutline.class, BowHelper.class, CameraClip.class, Cape.class, Chams.class,
         ChatHelper.class, ChestStealer.class, ChinaHat.class, ChorusFarm.class, ClanUpgrade.class, ClickPearl.class,
         ClientUtil.class, CocoaFarm.class, ColorPlus.class, CreeperFarm.class, Criticals.class, DeadEffect.class,
         DragonFly.class, ElytraHelper.class, ElytraTarget.class, ESP.class, FakePlayer.class, FastBow.class,
         FastBreak.class, FreeCamera.class, FreeLock.class, FriendManager.class, FullBright.class, GeyserHelper.class,
         GlowESP.class, GrimGlide.class, Hands.class, HitBox.class, HitSounds.class, HudModule.class,
         InvMove.class, ItemPhysic.class, ItemScroller.class, Jesus.class, JumpCircle.class, LockSlots.class,
         MenuModule.class, MoneyFarm.class, MotionBlur.class, NameTags.class, NoDelay.class, NoInteract.class,
         NoPush.class, NoSlow.class, NoWeb.class, OpenWalls.class, Particles.class, PlayerHelper.class,
         PotionCombiner.class, Predictions.class, ProtectInfo.class, PvPSafe.class, Removals.class, RotationLab.class,
         Scaffold.class, SeeInvisibles.class, ServerDHelper.class, ServerHelper.class, ServerJoiner.class, Speed.class,
         Spider.class, Sprint.class, Stardust.class, SwingAnimation.class, TapeMouse.class, TargetESP.class,
         TargetPearl.class, TestModule.class, TotemVoices.class, Trails.class, TriggerBot.class, UnHook.class,
         UseTracker.class, Velocity.class, WardenFarm.class, WaterSpeed.class, WorldTweaks.class
      };
      for (Class<?> moduleClass : classValues) {
         try {
            this.registerModule((Module)moduleClass.getDeclaredConstructor().newInstance());
         } catch (Throwable exception) {
            System.out.println("[North] module register failed: " + moduleClass.getSimpleName() + ": " + exception);
         }
      }

   }

   @Compile
   private void registerModule(Module module) {
      if (module != null) {
         this.modules.add(module);
      }
   }

   public ArrayList<Module> getModules() {
      return this.modules.stream().filter(this::isAvailable).collect(Collectors.toCollection(ArrayList::new));
   }

   public <T extends Module> T getModule(Class<T> class_) {
      for (Module module2 : this.modules) {
         if (module2.getClass() == class_) {
            return (T)module2;
         }
      }

      return null;
   }

   public Module findModule(Class<?> class_) {
      for (Module module3 : this.modules) {
         if (module3.getClass() == class_) {
            return module3;
         }
      }

      return null;
   }

   public ArrayList<Module> getModules(Category category) {
      return this.modules
         .stream()
         .filter(this::isAvailable)
         .filter(module -> module.category == category)
         .collect(Collectors.toCollection(ArrayList::new));
   }

   public Module[] getBoundModules(int keyCode) {
      if (keyCode == -1) {
         return new Module[0];
      }

      return this.modules.stream().filter(this::isAvailable).filter(module -> module.bindKey == keyCode).toArray(Module[]::new);
   }

   @EventHandler
   public void handleRawInput(RawInputEvent inputEvent) {
      if (inputEvent == null || inputEvent.getAction() != GLFW.GLFW_PRESS || inputEvent.getKeyCode() == -1 || inputEvent.isInvalidated()) {
         return;
      }

      MinecraftClient client = MinecraftClient.getInstance();
      if (client == null || client.currentScreen != null) {
         return;
      }

      boolean settingChanged = false;
      for (Module module : this.getModules()) {
         if (!module.enabled) {
            continue;
         }

         for (Setting setting : module.getAllSettings()) {
            if (setting instanceof BooleanSetting booleanSetting) {
               settingChanged |= toggleBooleanBind(booleanSetting, inputEvent.getKeyCode());
            } else if (setting instanceof GroupSetting groupSetting) {
               for (BooleanSetting option : groupSetting.options) {
                  settingChanged |= toggleBooleanBind(option, inputEvent.getKeyCode());
               }
            }
         }
      }

      for (Module module : this.getBoundModules(inputEvent.getKeyCode())) {
         if (!(module instanceof MenuModule)) {
            module.toggle();
         }
      }

      if (settingChanged && WildClient.INSTANCE != null && WildClient.INSTANCE.configManager != null) {
         WildClient.INSTANCE.configManager.scheduleSave();
      }
   }

   private static boolean toggleBooleanBind(BooleanSetting setting, int keyCode) {
      if (setting == null || setting.bindKey != keyCode || setting.holdToEnable || setting.waitingForBind) {
         return false;
      }

      setting.setValue(!setting.getValue());
      return true;
   }

   public boolean isAvailable(Module module) {
      return module == null ? false : hasAccess(module.getAccessRules());
   }

   public boolean isAvailable(Class<? extends Module> class_) {
      return class_ == null ? false : hasAccess(class_.getAnnotation(ModuleAccess.class));
   }

   public static boolean hasAccess(ModuleAccess access) {
      if (access == null) {
         return true;
      } else {
         boolean restricted = access.minimumRole() != Role.DEFAULT
            || access.roles().length > 0
            || access.usernames().length > 0
            || access.uids().length > 0;
         if (!restricted) {
            return true;
         } else if (Profile.isUid(access.uids())) {
            return true;
         } else if (Profile.isUsername(access.usernames())) {
            return true;
         } else {
            return Profile.hasRole(access.roles())
               ? true
               : access.minimumRole() != Role.DEFAULT && Profile.hasRoleAtLeast(access.minimumRole());
         }
      }
   }

   static {
      Loader.initialize();
   }
}
