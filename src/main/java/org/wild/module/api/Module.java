package org.wild.module.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Generated;
import net.minecraft.client.MinecraftClient;
import ru.metaculture.protection.BooleanSetting;
import ru.metaculture.protection.ButtonSetting;
import ru.metaculture.protection.Category;
import ru.metaculture.protection.ClientUtil;
import ru.metaculture.protection.ColorSetting;
import ru.metaculture.protection.EventManager;
import ru.metaculture.protection.GroupSetting;
import ru.metaculture.protection.KeybindSetting;
import ru.metaculture.protection.MenuModule;
import ru.metaculture.protection.ModeSetting;
import ru.metaculture.protection.ModuleAccess;
import ru.metaculture.protection.MultiSelectSetting;
import ru.metaculture.protection.NotificationsHud;
import ru.metaculture.protection.NumberSetting;
import ru.metaculture.protection.ModuleRiskLevel;
import ru.metaculture.protection.SettingContainer;
import ru.metaculture.protection.DynamicButtonSetting;
import ru.metaculture.protection.SpacerSetting;
import ru.metaculture.protection.FoundryShaderSetting;
import ru.metaculture.protection.Animation;
import ru.metaculture.protection.DirectionalAnimation;
import ru.metaculture.protection.Vector2Smoother;
import ru.metaculture.protection.EasedAnimation;
import ru.metaculture.protection.Easings;
import ru.metaculture.protection.EaseInOutQuadAnimation;
import ru.metaculture.protection.SoundUtils;
import ru.metaculture.protection.Setting;
import ru.metaculture.protection.TextArraySetting;
import ru.metaculture.protection.TextSetting;
import ru.metaculture.protection.WildClient;

public class Module extends SettingContainer {
   private static final String RESET_SETTINGS_TITLE = "Сброс настроек";
   private static final String RESET_TO_DEFAULT_LABEL = "До заводских";
   public ModuleRegister metadata = this.getClass().getAnnotation(ModuleRegister.class);
   public ModuleAccess accessRules = this.getClass().getAnnotation(ModuleAccess.class);
   public static MinecraftClient CLIENT = MinecraftClient.getInstance();
   public String name;
   public int bindKey;
   public boolean enabled;
   public boolean bindPressed = false;
   public Category category;
   public String displayName;
   public String description;
   public boolean expanded;
   public boolean visible = true;
   public Vector2Smoother positionSmoother = new Vector2Smoother(0.0F, 0.0F);
   private final Set<ModuleRiskLevel> riskLevels = new HashSet<>();
   private boolean riskWarningShown;
   private final DynamicButtonSetting resetSettingsButton = new DynamicButtonSetting("Сброс настроек", 0, () -> "До заводских").onClick(this::resetSettingsToDefault);
   public Animation lifecycleAnimation = new Animation();
   public DirectionalAnimation enableAnimation = new EaseInOutQuadAnimation(300, 1.0);
   public DirectionalAnimation hoverAnimation = new EaseInOutQuadAnimation(300, 1.0);
   public final EasedAnimation toggleAnimation = new EasedAnimation();

   public Module() {
      this.name = this.metadata.name();
      this.category = this.metadata.category();
      this.bindKey = -1;
      this.enabled = false;
      this.description = this.metadata.description();
      this.displayName = this.name;
      Collections.addAll(this.riskLevels, this.metadata.riskLevels());
   }

   public void onEnable() {
      try {
         EventManager.register(this);
      } catch (Exception exception) {
         exception.printStackTrace();
         this.enabled = false;
         return;
      }

      if (CLIENT.player != null && !(this instanceof MenuModule)) {
         NotificationsHud.showModuleToggle(this.name, true);
         if (WildClient.INSTANCE.moduleManager.getModule(ClientUtil.class).enabled
            && ClientUtil.clientSounds.isEnabled()
            && ClientUtil.soundOptions.isEnabled("Модули")) {
            SoundUtils.play("Function_ON", ClientUtil.soundVolume.getValue() / 250.0F);
         }
      }

      this.toggleAnimation.animateTo(1.0, 0.24F, Easings.EASE_OUT_QUART);
   }

   public void onDisable() {
      EventManager.unregister(this);
      if (CLIENT.player != null && !WildClient.isShuttingDown() && !(this instanceof MenuModule)) {
         NotificationsHud.showModuleToggle(this.name, false);
         if (WildClient.INSTANCE.moduleManager.getModule(ClientUtil.class).enabled
            && ClientUtil.clientSounds.isEnabled()
            && ClientUtil.soundOptions.isEnabled("Модули")) {
            SoundUtils.play("Function_OFF", ClientUtil.soundVolume.getValue() / 250.0F);
         }
      }

      this.toggleAnimation.animateTo(0.0, 0.24F, Easings.EASE_OUT_QUART);
   }

   public void toggle() {
      this.updateEnabled(!this.enabled, true);
   }

   public JsonObject toJson() {
      JsonObject jsonObject = new JsonObject();
      if (this.enabled) {
         jsonObject.addProperty("enable", this.enabled);
      }

      if (this.bindKey != -1) {
         jsonObject.addProperty("keyIndex", this.bindKey);
      }

      JsonObject jsonObject2 = new JsonObject();

      for (Object object : this.getAllSettings()) {
         if (object != null && !((Setting)object).configTransient) {
            String text = ((Setting)object).getName();
            switch (object) {
               case BooleanSetting booleanSetting:
                  jsonObject2.addProperty(text, booleanSetting.getValue());
                  if (booleanSetting.bindKey != -1) {
                     JsonObject jsonObject3 = new JsonObject();
                     jsonObject3.addProperty("key", booleanSetting.bindKey);
                     jsonObject3.addProperty("hold", booleanSetting.holdToEnable);
                     jsonObject2.add(text + "$bind", jsonObject3);
                  }
                  break;
               case ModeSetting modeSetting:
                  jsonObject2.addProperty(text, modeSetting.value);
                  break;
               case FoundryShaderSetting foundryShaderSetting:
                  jsonObject2.addProperty(text, foundryShaderSetting.getSelectedPreset());
                  break;
               case MultiSelectSetting multiSelectSetting:
                  jsonObject2.addProperty(text, String.join(", ", multiSelectSetting.selectedValues));
                  break;
               case NumberSetting numberSetting:
                  jsonObject2.addProperty(text, numberSetting.value);
                  break;
               case KeybindSetting keybindSetting:
                  jsonObject2.addProperty(text, keybindSetting.keyCode);
                  break;
               case TextSetting textSetting:
                  jsonObject2.addProperty(text, textSetting.value);
                  break;
               case TextArraySetting textArraySetting:
                  jsonObject2.add(text, textArraySetting.toJson());
                  break;
               case ColorSetting colorSetting:
                  JsonObject jsonObject4 = new JsonObject();
                  jsonObject4.addProperty("current", colorSetting.hueValue);
                  jsonObject4.addProperty("saturation", colorSetting.saturation);
                  jsonObject4.addProperty("brightness", colorSetting.brightness);
                  jsonObject2.add(text, jsonObject4);
                  break;
               case GroupSetting groupSetting:
                  GroupSetting groupSetting2 = (GroupSetting)object;
                  JsonObject jsonObject5 = new JsonObject();
                  JsonObject jsonObject6 = new JsonObject();

                  for (BooleanSetting booleanSetting2 : groupSetting2.options) {
                     jsonObject5.addProperty(booleanSetting2.name, booleanSetting2.getValue());
                     if (booleanSetting2.bindKey != -1) {
                        JsonObject jsonObject7 = new JsonObject();
                        jsonObject7.addProperty("key", booleanSetting2.bindKey);
                        jsonObject7.addProperty("hold", booleanSetting2.holdToEnable);
                        jsonObject6.add(booleanSetting2.name, jsonObject7);
                     }
                  }

                  jsonObject2.add(text, jsonObject5);
                  if (jsonObject6.size() > 0) {
                     jsonObject2.add(text + "$binds", jsonObject6);
                  }
                  continue;
               default:
            }
         }
      }

      jsonObject.add("Settings", jsonObject2);
      return jsonObject;
   }

   public void loadFromJson(JsonObject jsonObject) {
      if (jsonObject != null) {
         try {
            if (jsonObject.has("enable")) {
               this.setEnabled(jsonObject.get("enable").getAsBoolean());
            }
         } catch (Throwable exception2) {
         }

         try {
            if (jsonObject.has("keyIndex")) {
               this.bindKey = jsonObject.get("keyIndex").getAsInt();
            }
         } catch (Throwable exception3) {
         }

         JsonObject jsonObject8 = null;

         try {
            jsonObject8 = jsonObject.getAsJsonObject("Settings");
         } catch (Throwable exception4) {
         }

         if (jsonObject8 != null) {
            for (Object object2 : this.getAllSettings()) {
               if (object2 != null && !((Setting)object2).configTransient) {
                  String text2 = ((Setting)object2).getName();
                  if (jsonObject8.has(text2)) {
                     try {
                        switch (object2) {
                           case BooleanSetting booleanSetting3:
                              booleanSetting3.setValue(jsonObject8.get(text2).getAsBoolean());
                              JsonElement jsonElement = jsonObject8.get(text2 + "$bind");
                              if (jsonElement != null && jsonElement.isJsonObject()) {
                                 JsonObject jsonObject9 = jsonElement.getAsJsonObject();
                                 if (jsonObject9.has("key")) {
                                    booleanSetting3.bindKey = jsonObject9.get("key").getAsInt();
                                 }

                                 if (jsonObject9.has("hold")) {
                                    booleanSetting3.holdToEnable = jsonObject9.get("hold").getAsBoolean();
                                 }
                              }
                              break;
                           case ModeSetting modeSetting2:
                              String text3 = jsonObject8.get(text2).getAsString();
                              if (modeSetting2.options != null && modeSetting2.options.contains(text3)) {
                                 modeSetting2.value = text3;
                                 modeSetting2.selectedIndex = modeSetting2.options.indexOf(text3);
                              }
                              break;
                           case NumberSetting numberSetting2:
                              float floatValue = jsonObject8.get(text2).getAsFloat();
                              if (!Float.isNaN(floatValue) && !Float.isInfinite(floatValue)) {
                                 numberSetting2.value = Math.max(numberSetting2.minimum, Math.min(numberSetting2.maximum, floatValue));
                              }
                              break;
                           case FoundryShaderSetting foundryShaderSetting2:
                              foundryShaderSetting2.setSelectedPreset(jsonObject8.get(text2).getAsString());
                              break;
                           case KeybindSetting keybindSetting2:
                              keybindSetting2.keyCode = jsonObject8.get(text2).getAsInt();
                              break;
                           case TextSetting textSetting2:
                              textSetting2.setValue(jsonObject8.get(text2).getAsString());
                              break;
                           case TextArraySetting textArraySetting2:
                              textArraySetting2.loadFromJson(jsonObject8.get(text2));
                              break;
                           case ColorSetting colorSetting2:
                              JsonElement jsonElement2 = jsonObject8.get(text2);
                              if (jsonElement2 != null && jsonElement2.isJsonObject()) {
                                 JsonObject jsonObject10 = jsonElement2.getAsJsonObject();
                                 if (jsonObject10.has("current")) {
                                    float floatValue2 = jsonObject10.get("current").getAsFloat();
                                    if (!Float.isNaN(floatValue2) && !Float.isInfinite(floatValue2)) {
                                       colorSetting2.hueValue = Math.max(colorSetting2.minimumHue, Math.min(colorSetting2.maximumHue, floatValue2));
                                    }
                                 }

                                 if (jsonObject10.has("saturation")) {
                                    float floatValue3 = jsonObject10.get("saturation").getAsFloat();
                                    if (!Float.isNaN(floatValue3) && !Float.isInfinite(floatValue3)) {
                                       colorSetting2.saturation = Math.max(0.0F, Math.min(1.0F, floatValue3));
                                    }
                                 }

                                 if (jsonObject10.has("brightness")) {
                                    float floatValue4 = jsonObject10.get("brightness").getAsFloat();
                                    if (!Float.isNaN(floatValue4) && !Float.isInfinite(floatValue4)) {
                                       colorSetting2.brightness = Math.max(0.0F, Math.min(1.0F, floatValue4));
                                    }
                                 }
                              } else if (jsonElement2 != null) {
                                 float floatValue5 = jsonElement2.getAsFloat();
                                 if (!Float.isNaN(floatValue5) && !Float.isInfinite(floatValue5)) {
                                    colorSetting2.hueValue = Math.max(colorSetting2.minimumHue, Math.min(colorSetting2.maximumHue, floatValue5));
                                 }
                              }
                              break;
                           case GroupSetting groupSetting3:
                              GroupSetting groupSetting4 = (GroupSetting)object2;
                              JsonElement jsonElement3 = jsonObject8.get(text2);
                              if (jsonElement3 != null && jsonElement3.isJsonObject()) {
                                 JsonObject jsonObject11 = jsonElement3.getAsJsonObject();

                                 for (BooleanSetting booleanSetting4 : groupSetting4.options) {
                                    if (jsonObject11.has(booleanSetting4.name)) {
                                       try {
                                          booleanSetting4.setValue(jsonObject11.get(booleanSetting4.name).getAsBoolean());
                                       } catch (Throwable exception5) {
                                       }
                                    }
                                 }
                              }

                              JsonElement jsonElement4 = jsonObject8.get(text2 + "$binds");
                              if (jsonElement4 != null && jsonElement4.isJsonObject()) {
                                 JsonObject jsonObject12 = jsonElement4.getAsJsonObject();

                                 for (BooleanSetting booleanSetting5 : groupSetting4.options) {
                                    if (jsonObject12.has(booleanSetting5.name)) {
                                       try {
                                          JsonObject jsonObject13 = jsonObject12.getAsJsonObject(booleanSetting5.name);
                                          if (jsonObject13.has("key")) {
                                             booleanSetting5.bindKey = jsonObject13.get("key").getAsInt();
                                          }

                                          if (jsonObject13.has("hold")) {
                                             booleanSetting5.holdToEnable = jsonObject13.get("hold").getAsBoolean();
                                          }
                                       } catch (Throwable exception6) {
                                       }
                                    }
                                 }
                              }
                              break;
                           case MultiSelectSetting multiSelectSetting2:
                              multiSelectSetting2.refreshOptions();
                              JsonElement jsonElement5 = jsonObject8.get(text2);
                              if (jsonElement5 != null) {
                                 String text4 = jsonElement5.getAsString();
                                 String[] texts = text4.split(",");
                                 ArrayList arrayList = new ArrayList();

                                 for (String text5 : texts) {
                                    if (text5 != null) {
                                       String text6 = text5.trim();
                                       if (!text6.isEmpty() && multiSelectSetting2.options != null && multiSelectSetting2.options.contains(text6)) {
                                          arrayList.add(text6);
                                       }
                                    }
                                 }

                                 multiSelectSetting2.selectedValues = arrayList;
                              }
                              break;
                           default:
                        }
                     } catch (Throwable exception7) {
                     }
                  }
               }
            }
         }
      }
   }

   public void setEnabled(boolean bl) {
      this.updateEnabled(bl, false);
   }

   public void applyEnabledState(boolean bl) {
      this.setEnabled(bl);
   }

   public void reset() {
      if (this.enabled) {
         this.setEnabled(false);
      }

      this.bindKey = -1;
      this.bindPressed = false;
      this.expanded = false;

      for (Setting setting : this.getAllSettings()) {
         if (setting != null && !setting.configTransient) {
            setting.resetToDefault();
         }
      }
   }

   @Override
   public List<Setting> getVisibleSettings() {
      List items = super.getVisibleSettings();
      if (this.hasResettableSettings()) {
         items.add(this.resetSettingsButton);
      }

      return items;
   }

   private void resetSettingsToDefault() {
      for (Setting setting2 : this.getAllSettings()) {
         if (this.isResettableSetting(setting2)) {
            setting2.resetToDefault();
         }
      }

      if (WildClient.INSTANCE != null && WildClient.INSTANCE.configManager != null) {
         WildClient.INSTANCE.configManager.scheduleSave();
      }
   }

   private boolean hasResettableSettings() {
      for (Setting setting3 : this.getAllSettings()) {
         if (this.isResettableSetting(setting3)) {
            return true;
         }
      }

      return false;
   }

   private boolean isResettableSetting(Setting setting) {
      return setting != null
         && setting != this.resetSettingsButton
         && !setting.configTransient
         && !(setting instanceof ButtonSetting)
         && !(setting instanceof SpacerSetting);
   }

   public Module addRiskLevel(ModuleRiskLevel riskLevel) {
      if (riskLevel != null) {
         this.riskLevels.add(riskLevel);
      }

      return this;
   }

   public Module addRiskLevels(ModuleRiskLevel... riskLevels) {
      if (riskLevels != null) {
         Collections.addAll(this.riskLevels, riskLevels);
         this.riskLevels.remove(null);
      }

      return this;
   }

   public boolean hasRiskLevel(ModuleRiskLevel riskLevel) {
      return riskLevel != null && this.riskLevels.contains(riskLevel);
   }

   public Set<ModuleRiskLevel> getRiskLevels() {
      return Collections.unmodifiableSet(this.riskLevels);
   }

   private void updateEnabled(boolean bl, boolean bl2) {
      if (bl && WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null && !WildClient.INSTANCE.moduleManager.isAvailable(this)) {
         this.enabled = false;
      } else if (this.enabled != bl) {
         this.enabled = bl;
         if (bl) {
            this.onEnable();
         } else {
            this.onDisable();
         }

         if (bl2 && WildClient.INSTANCE != null && WildClient.INSTANCE.configManager != null) {
            WildClient.INSTANCE.configManager.scheduleSave();
         }
      }
   }

   private void showRiskWarning() {
      if (!this.riskWarningShown && CLIENT.player != null && (this.hasRiskLevel(ModuleRiskLevel.RISKY) || this.hasRiskLevel(ModuleRiskLevel.PATCHED))) {
         this.riskWarningShown = true;
         String text7 = this.hasRiskLevel(ModuleRiskLevel.RISKY) && this.hasRiskLevel(ModuleRiskLevel.PATCHED)
            ? "Risky/Patched"
            : (this.hasRiskLevel(ModuleRiskLevel.RISKY) ? "Risky" : "Patched");
         NotificationsHud.show("warn", "Warning: " + this.name + " is currently flagged as " + text7 + ".", 3500L);
      }
   }

   @Generated
   public ModuleAccess getAccessRules() {
      return this.accessRules;
   }

   @Generated
   public int getBindKey() {
      return this.bindKey;
   }

   @Generated
   public String getDisplayName() {
      return this.displayName;
   }
}
