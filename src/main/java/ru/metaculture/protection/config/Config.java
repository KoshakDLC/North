package ru.metaculture.protection;

import com.google.gson.JsonObject;
import java.io.File;
import java.util.HashMap;
import java.util.Map.Entry;
import org.wild.module.api.Module;

public final class Config implements JsonConfigSerializable {
   private final String text;
   private final File file;
   public DirectionalAnimation directionalAnimation = new EaseInOutQuadAnimation(500, 1.0);
   public DirectionalAnimation directionalAnimation2 = new EaseInOutQuadAnimation(300, 1.0);
   public DirectionalAnimation directionalAnimation3 = new EaseInOutQuadAnimation(300, 1.0);
   public DirectionalAnimation directionalAnimation4 = new EaseInOutQuadAnimation(300, 1.0);
   public DirectionalAnimation directionalAnimation5 = new EaseInOutQuadAnimation(500, 1.0);

   public Config(String string) {
      this.text = string;
      this.file = new File(ConfigManager.FILE, string + ".json");
      if (!this.file.exists()) {
         try {
            File file = this.file.getParentFile();
            if (file != null && !file.exists() && !file.mkdirs()) {
               System.out.println("[Config] Warning: failed to create parent dir for " + string);
            }

            if (!this.file.createNewFile()) {
               System.out.println("[Config] Warning: failed to create file " + this.file.getAbsolutePath());
            }
         } catch (Exception exception) {
            System.out.println("[Config] Cannot create config '" + string + "': " + exception.getMessage());
         }
      }
   }

   public File getFile() {
      return this.file;
   }

   public String getText() {
      return this.text;
   }

   @Override
   public JsonObject resolve() {
      JsonObject jsonObject2 = new JsonObject();
      JsonObject jsonObject3 = new JsonObject();

      for (Module module2 : WildClient.INSTANCE.moduleManager.modules) {
         JsonObject jsonObject4 = module2.toJson();
         this.check(module2, jsonObject4);
         jsonObject3.add(module2.name, jsonObject4);
      }

      jsonObject2.add("Features", jsonObject3);
      JsonObject jsonObject5 = new JsonObject();

      for (Entry entry : HudEditorRenderer.getINSTANCE().getValuesByKey2().entrySet()) {
         JsonObject jsonObject6 = new JsonObject();
         jsonObject6.addProperty("x", ((HudEditorRenderer.HudEditorRendererData2)entry.getValue()).nx());
         jsonObject6.addProperty("y", ((HudEditorRenderer.HudEditorRendererData2)entry.getValue()).ny());
         jsonObject6.addProperty("scaleX", ((HudEditorRenderer.HudEditorRendererData2)entry.getValue()).scaleX());
         jsonObject6.addProperty("scaleY", ((HudEditorRenderer.HudEditorRendererData2)entry.getValue()).scaleY());
         jsonObject6.addProperty("resized", ((HudEditorRenderer.HudEditorRendererData2)entry.getValue()).userResized());
         jsonObject5.add((String)entry.getKey(), jsonObject6);
      }

      jsonObject2.add("DraggablePositions", jsonObject5);
      JsonObject jsonObject7 = new JsonObject();

      for (Entry entry2 : HudEditorRenderer.getINSTANCE().getValuesByKey3().entrySet()) {
         jsonObject7.addProperty((String)entry2.getKey(), (Number)entry2.getValue());
      }

      jsonObject2.add("PendingDraggableScales", jsonObject7);
      jsonObject2.add("HUDSettings", HudPresetManager.resolve3());
      return jsonObject2;
   }

   @Override
   public void invoke(JsonObject jsonObject) {
      System.out.println("[Config] Loading config: " + this.text);
      if (jsonObject != null) {
         boolean flag = false;
         if (jsonObject.has("Features")) {
            JsonObject jsonObject8;
            try {
               jsonObject8 = jsonObject.getAsJsonObject("Features");
            } catch (Throwable exception2) {
               System.out.println("[Config] 'Features' object malformed, skipping");
               jsonObject8 = null;
            }

            if (jsonObject8 != null) {
               this.invoke2(jsonObject8);
               int intValue = 0;

               for (Module module3 : WildClient.INSTANCE.moduleManager.modules) {
                  try {
                     if (module3.enabled) {
                        module3.setEnabled(false);
                     }

                     if (WildClient.INSTANCE.moduleManager.isAvailable(module3) && jsonObject8.has(module3.name)) {
                        JsonObject jsonObject9 = null;

                        try {
                           jsonObject9 = jsonObject8.getAsJsonObject(module3.name);
                        } catch (Throwable exception3) {
                        }

                        if (jsonObject9 != null) {
                           flag |= this.check(module3, jsonObject9);
                           module3.loadFromJson(jsonObject9);
                        }

                        if (module3.enabled) {
                           intValue++;
                        }
                     }
                  } catch (Throwable exception4) {
                     System.out.println("[Config] Failed to load module '" + module3.name + "': " + exception4.getMessage());
                  }
               }
            }
         }

         if (jsonObject.has("HUDSettings")) {
            try {
               HudPresetManager.invoke6(jsonObject.getAsJsonObject("HUDSettings"));
            } catch (Throwable exception5) {
               System.out.println("[Config] Failed to load HUD settings: " + exception5.getMessage());
            }
         }

         if (jsonObject.has("DraggablePositions")) {
            JsonObject jsonObject10 = jsonObject.getAsJsonObject("DraggablePositions");
            HashMap hashMap = new HashMap();

            for (String text : jsonObject10.keySet()) {
               JsonObject jsonObject11 = jsonObject10.getAsJsonObject(text);
               if (jsonObject11.has("x") && jsonObject11.has("y")) {
                  float floatValue = jsonObject11.get("x").getAsFloat();
                  float floatValue2 = jsonObject11.get("y").getAsFloat();
                  float floatValue3 = jsonObject11.has("scaleX") ? jsonObject11.get("scaleX").getAsFloat() : 1.0F;
                  float floatValue4 = jsonObject11.has("scaleY") ? jsonObject11.get("scaleY").getAsFloat() : 1.0F;
                  if (floatValue3 > 10.0F || floatValue3 <= 0.0F) {
                     floatValue3 = 1.0F;
                  }

                  if (floatValue4 > 10.0F || floatValue4 <= 0.0F) {
                     floatValue4 = 1.0F;
                  }

                  boolean flag2 = jsonObject11.has("resized") && jsonObject11.get("resized").getAsBoolean();

                  try {
                     hashMap.put(text, new HudEditorRenderer.HudEditorRendererData2(floatValue, floatValue2, floatValue3, floatValue4, flag2));
                  } catch (Exception exception6) {
                     System.out.println("[Config] Failed to load position for: " + text);
                  }
               }
            }

            HudEditorRenderer.getINSTANCE().invoke16(hashMap);
            System.out.println("[Config] Loaded " + hashMap.size() + " draggable positions");
         }

         HashMap hashMap2 = new HashMap();
         if (jsonObject.has("PendingDraggableScales")) {
            try {
               JsonObject jsonObject12 = jsonObject.getAsJsonObject("PendingDraggableScales");

               for (String text2 : jsonObject12.keySet()) {
                  float floatValue5 = jsonObject12.get(text2).getAsFloat();
                  if (Float.isFinite(floatValue5) && floatValue5 > 0.0F && floatValue5 <= 10.0F) {
                     hashMap2.put(text2, floatValue5);
                  }
               }
            } catch (Throwable exception7) {
            }
         }

         HudEditorRenderer.getINSTANCE().invoke17(hashMap2);
         if (flag && WildClient.INSTANCE != null && WildClient.INSTANCE.configManager != null) {
            WildClient.INSTANCE.configManager.check3(this.text);
         }
      }
   }

   private void invoke2(JsonObject jsonObject) {
      if (jsonObject != null && jsonObject.has("NoRender")) {
         JsonObject jsonObject13 = null;

         try {
            jsonObject13 = jsonObject.getAsJsonObject("NoRender");
         } catch (Throwable exception8) {
         }

         if (jsonObject13 != null && jsonObject13.has("Settings")) {
            JsonObject jsonObject14 = null;

            try {
               jsonObject14 = jsonObject13.getAsJsonObject("Settings");
            } catch (Throwable exception9) {
            }

            if (jsonObject14 != null) {
               boolean flag3 = false;

               try {
                  flag3 = jsonObject13.has("enable") && jsonObject13.get("enable").getAsBoolean();
               } catch (Throwable exception10) {
               }

               if (flag3) {
                  JsonObject jsonObject15 = null;

                  try {
                     jsonObject15 = jsonObject.has("Removals") ? jsonObject.getAsJsonObject("Removals") : new JsonObject();
                  } catch (Throwable exception11) {
                  }

                  if (jsonObject15 == null) {
                     jsonObject15 = new JsonObject();
                  }

                  JsonObject jsonObject16 = null;

                  try {
                     jsonObject16 = jsonObject15.has("Settings") ? jsonObject15.getAsJsonObject("Settings") : new JsonObject();
                  } catch (Throwable exception12) {
                  }

                  if (jsonObject16 == null) {
                     jsonObject16 = new JsonObject();
                  }

                  boolean flag4 = false;

                  try {
                     flag4 = jsonObject15.has("enable") && jsonObject15.get("enable").getAsBoolean();
                  } catch (Throwable exception13) {
                  }

                  if (jsonObject13.has("enable") && !jsonObject15.has("enable")) {
                     try {
                        jsonObject15.add("enable", jsonObject13.get("enable").deepCopy());
                     } catch (Throwable exception14) {
                     }
                  }

                  if (!flag4) {
                     jsonObject16.addProperty("Убрать траву", false);
                     jsonObject16.addProperty("Убрать растения", false);
                     jsonObject16.addProperty("Убрать стойки", false);
                     jsonObject16.addProperty("Убрать рамки", false);
                     jsonObject16.addProperty("Убрать картины", false);
                     jsonObject16.addProperty("Убрать дроп", false);
                     jsonObject16.addProperty("Убрать опыт", false);
                     jsonObject16.addProperty("Откл. диктор", false);
                  }

                  for (String text3 : jsonObject14.keySet()) {
                     if (!jsonObject16.has(text3)) {
                        try {
                           jsonObject16.add(text3, jsonObject14.get(text3).deepCopy());
                        } catch (Throwable exception15) {
                        }
                     }
                  }

                  if (jsonObject14.has("Не рендерить") && !jsonObject16.has("Не рендерить")) {
                     try {
                        jsonObject16.add("Не рендерить", jsonObject14.get("Не рендерить").deepCopy());
                     } catch (Throwable exception16) {
                     }
                  }

                  jsonObject15.add("Settings", jsonObject16);
                  jsonObject.add("Removals", jsonObject15);
               }
            }
         }
      }
   }

   private boolean check(Module module, JsonObject jsonObject) {
      if (module == null || jsonObject == null) {
         return false;
      } else {
         return module instanceof UnHook ? jsonObject.remove("enable") != null : false;
      }
   }
}
