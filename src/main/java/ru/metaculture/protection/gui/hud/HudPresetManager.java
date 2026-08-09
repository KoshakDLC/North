package ru.metaculture.protection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import ru.metaculture.sdk.Compile;
import ru.metaculture.sdk.Loader;

public class HudPresetManager {
   private static final List<ConfigurableHudElement> ITEMS = new ArrayList<>();
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   private static JsonObject jsonObject = new JsonObject();
   private static boolean flag;

   private static File resolve() {
      return new File(WildClient.INSTANCE.file, "hudP.cfg");
   }

   @Compile
   public static void invoke() {}

   @Compile
   public static void invoke2(ConfigurableHudElement configurableHudElement) {}

   public static List<ConfigurableHudElement> resolve2() {
      return ITEMS.stream().filter(configurableHudElement2 -> configurableHudElement2 != null && check(configurableHudElement2.getClass())).toList();
   }

   public static void invoke3() {
      jsonObject = new JsonObject();

      for (ConfigurableHudElement configurableHudElement3 : resolve2()) {
         for (Setting setting : configurableHudElement3.resolve()) {
            if (setting != null && !setting.configTransient) {
               setting.resetToDefault();
            }
         }
      }

      invoke5();
   }

   public static boolean check(Class<?> class_) {
      return class_ != null && ModuleManager.hasAccess(class_.getAnnotation(ModuleAccess.class));
   }

   @Compile
   private static void invoke4(ConfigurableHudElement configurableHudElement4) {}

   public static void invoke5() {
      jsonObject = resolve3();
      invoke8();
      if (WildClient.INSTANCE != null && WildClient.INSTANCE.configManager != null) {
         WildClient.INSTANCE.configManager.scheduleSave();
      }
   }

   public static JsonObject resolve3() {
      JsonObject jsonObject2 = new JsonObject();

      for (ConfigurableHudElement configurableHudElement5 : resolve2()) {
         HudElementInfo hudElementInfo = configurableHudElement5.getClass().getAnnotation(HudElementInfo.class);
         if (hudElementInfo != null) {
            JsonObject jsonObject3 = new JsonObject();

            for (Setting setting2 : configurableHudElement5.resolve()) {
               if (setting2 != null && !setting2.configTransient) {
                  if (setting2 instanceof BooleanSetting booleanSetting) {
                     jsonObject3.addProperty(booleanSetting.name, booleanSetting.isEnabled());
                  } else if (setting2 instanceof NumberSetting numberSetting) {
                     jsonObject3.addProperty(numberSetting.name, numberSetting.getValue());
                  } else if (setting2 instanceof ModeSetting modeSetting) {
                     jsonObject3.addProperty(modeSetting.name, modeSetting.getValue());
                  } else if (setting2 instanceof FoundryShaderSetting foundryShaderSetting) {
                     jsonObject3.addProperty(foundryShaderSetting.name, foundryShaderSetting.getSelectedPreset());
                  } else if (setting2 instanceof GroupSetting groupSetting) {
                     JsonObject jsonObject4 = new JsonObject();

                     for (BooleanSetting booleanSetting2 : groupSetting.options) {
                        jsonObject4.addProperty(booleanSetting2.name, booleanSetting2.isEnabled());
                     }

                     jsonObject3.add(groupSetting.name, jsonObject4);
                  }
               }
            }

            jsonObject2.add(hudElementInfo.resolve(), jsonObject3);
         }
      }

      return jsonObject2;
   }

   public static void invoke6(JsonObject jsonObject) {
      if (jsonObject != null) {
         jsonObject = jsonObject.deepCopy();
         flag = true;
         invoke7();
         invoke8();
      }
   }

   private static void invoke7() {
      for (ConfigurableHudElement configurableHudElement6 : ITEMS) {
         invoke4(configurableHudElement6);
      }
   }

   private static void invoke8() {
      try {
         File file = resolve();
         if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
         }

         try (FileWriter fileWriter = new FileWriter(file)) {
            GSON.toJson(jsonObject, fileWriter);
         }
      } catch (Exception exception) {
         exception.printStackTrace();
      }
   }

   static {
      Loader.initialize();
   }
}
