package ru.metaculture.protection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public final class HudLayoutManager {
   public static final String HUD_HOTKEYS = "HUD_HotKeys";
   public static final String HUD_INVENTORY = "HUD_Inventory";
   public static final String HUD_POTIONS = "HUD_Potions";
   public static final String HUD_COOLDOWNS = "HUD_CoolDowns";
   public static final String HUD_INFO = "HUD_Info";
   public static final String HUD_WATERMARK = "HUD_WaterMark";
   public static final String HUD_ARRAYLIST = "HUD_ArrayList";
   public static final String HUD_TARGETHUD = "HUD_TargetHUD";
   public static final String HUD_ARMOR = "hud_armor";
   public static final String HUD_HOTBAR = "HUD_HotBar";
   public static final String HUD_NOTIFICATIONS = "HUD_Notifications";
   public static final String HUD_AUTOBUYINFO = "HUD_AutoBuyInfo";
   public static final String HUD_AISTATUS = "HUD_AIStatus";
   public static final String HUD_MUSICPLAYER = "HUD_MusicPlayer";
   public static final String HUD_SERVERHELPER = "HUD_ServerHelper";
   static final String[] HUD_HOTKEYS_2 = new String[]{
      "HUD_HotKeys",
      "HUD_Inventory",
      "HUD_Potions",
      "HUD_CoolDowns",
      "HUD_Info",
      "HUD_WaterMark",
      "HUD_ArrayList",
      "HUD_TargetHUD",
      "hud_armor",
      "HUD_HotBar",
      "HUD_Notifications",
      "HUD_AutoBuyInfo",
      "HUD_AIStatus",
      "HUD_MusicPlayer",
      "HUD_ServerHelper"
   };
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   private static HudLayoutManager.HudLayoutManagerState2 hudLayoutManagerState2;
   private static boolean flag;

   private HudLayoutManager() {
   }

   public static synchronized HudLayoutManager.HudLayoutManagerState resolve() {
      return resolve4("HUD_HotKeys");
   }

   public static synchronized HudLayoutManager.HudLayoutManagerState resolve2() {
      return resolve4("HUD_Inventory");
   }

   public static synchronized HudLayoutManager.HudLayoutManagerState resolve3() {
      return resolve4("HUD_Potions");
   }

   public static synchronized HudLayoutManager.HudLayoutManagerState resolve4(String string) {
      invoke4();
      String text = resolve6(string);
      HudLayoutManager.HudLayoutManagerState hudLayoutManagerState = hudLayoutManagerState2.valuesByKey.get(text);
      if (hudLayoutManagerState == null) {
         hudLayoutManagerState = HudLayoutManager.HudLayoutManagerState.resolve2(text);
         hudLayoutManagerState2.valuesByKey.put(text, hudLayoutManagerState);
      }

      hudLayoutManagerState.invoke();
      return hudLayoutManagerState;
   }

   public static synchronized void invoke() {
      invoke2("HUD_HotKeys");
   }

   public static synchronized void invoke2(String string) {
      invoke4();
      String text2 = resolve6(string);
      hudLayoutManagerState2.valuesByKey.put(text2, HudLayoutManager.HudLayoutManagerState.resolve2(text2));
      HudEditorRenderer.getINSTANCE().invoke18(text2);
      invoke3();
      if (WildClient.INSTANCE != null && WildClient.INSTANCE.configManager != null) {
         WildClient.INSTANCE.configManager.scheduleSave();
      }
   }

   public static synchronized void invoke3() {
      invoke4();
      File file = resolve5();
      if (file != null) {
         try {
            File file2 = file.getParentFile();
            if (file2 != null && !file2.exists()) {
               file2.mkdirs();
            }

            try (FileWriter fileWriter = new FileWriter(file)) {
               GSON.toJson(hudLayoutManagerState2, fileWriter);
            }
         } catch (Throwable exception) {
         }
      }
   }

   private static void invoke4() {
      if (!flag) {
         flag = true;
         hudLayoutManagerState2 = new HudLayoutManager.HudLayoutManagerState2();
         File file3 = resolve5();
         if (file3 != null && file3.exists()) {
            try (FileReader fileReader = new FileReader(file3)) {
               HudLayoutManager.HudLayoutManagerState2 hudLayoutManagerState2 = (HudLayoutManager.HudLayoutManagerState2)GSON.fromJson(fileReader, HudLayoutManager.HudLayoutManagerState2.class);
               if (hudLayoutManagerState2 != null) {
                  HudLayoutManager.hudLayoutManagerState2 = hudLayoutManagerState2;
               }
            } catch (Throwable exception2) {
               hudLayoutManagerState2 = new HudLayoutManager.HudLayoutManagerState2();
            }

            hudLayoutManagerState2.invoke();
         } else {
            hudLayoutManagerState2.invoke();
         }
      }
   }

   private static File resolve5() {
      return WildClient.INSTANCE != null && WildClient.INSTANCE.file != null
         ? new File(WildClient.INSTANCE.file, "hud-layouts.json")
         : null;
   }

   static float measure(float f, float g, float h) {
      return !Float.isFinite(f) ? g : Math.max(g, Math.min(h, f));
   }

   private static String resolve6(String string) {
      for (String text3 : HUD_HOTKEYS_2) {
         if (text3.equals(string)) {
            return text3;
         }
      }

      return "HUD_HotKeys";
   }

   public static final class HudLayoutManagerState {
      public float floatValue = 14.0F;
      public float floatValue2 = 11.0F;
      public float floatValue3 = 7.0F;
      public float floatValue4 = 7.0F;
      public float floatValue5 = 7.0F;
      public float floatValue6 = 6.0F;
      public float floatValue7 = 4.0F;
      public float floatValue8 = 7.0F;
      public float floatValue9 = 5.0F;
      public float floatValue10 = 32.0F;
      public float floatValue11 = 22.0F;
      public float floatValue12 = 28.0F;
      public float floatValue13 = 22.0F;
      public float floatValue14 = 0.0F;
      public float floatValue15 = 2.0F;
      public HudLayoutManager.HudLayoutManagerState3 hudLayoutManagerState3 = new HudLayoutManager.HudLayoutManagerState3(17.0F, 29.0F, false);
      public HudLayoutManager.HudLayoutManagerState3 hudLayoutManagerState32 = new HudLayoutManager.HudLayoutManagerState3(-34.0F, 29.5F, true);
      public HudLayoutManager.HudLayoutManagerState3 hudLayoutManagerState33 = new HudLayoutManager.HudLayoutManagerState3(0.0F, 0.0F, false);
      public HudLayoutManager.HudLayoutManagerState3 hudLayoutManagerState34 = new HudLayoutManager.HudLayoutManagerState3(0.0F, 0.0F, false);

      public static HudLayoutManager.HudLayoutManagerState resolve() {
         return resolve2("HUD_HotKeys");
      }

      public static HudLayoutManager.HudLayoutManagerState resolve2(String string) {
         HudLayoutManager.HudLayoutManagerState hudLayoutManagerState3 = new HudLayoutManager.HudLayoutManagerState();
         if ("HUD_Inventory".equals(string)) {
            hudLayoutManagerState3.hudLayoutManagerState3 = new HudLayoutManager.HudLayoutManagerState3(17.0F, 29.0F, false);
            hudLayoutManagerState3.hudLayoutManagerState32 = new HudLayoutManager.HudLayoutManagerState3(-34.0F, 30.0F, true);
            hudLayoutManagerState3.floatValue3 = 9.0F;
            hudLayoutManagerState3.floatValue4 = 9.0F;
            hudLayoutManagerState3.floatValue12 = 26.0F;
            hudLayoutManagerState3.floatValue13 = 28.0F;
         } else if ("HUD_Potions".equals(string)) {
            hudLayoutManagerState3.hudLayoutManagerState3 = new HudLayoutManager.HudLayoutManagerState3(17.0F, 29.0F, false);
            hudLayoutManagerState3.hudLayoutManagerState32 = new HudLayoutManager.HudLayoutManagerState3(-34.0F, 28.5F, true);
            hudLayoutManagerState3.floatValue13 = 24.0F;
         } else if ("HUD_WaterMark".equals(string)) {
            hudLayoutManagerState3.floatValue10 = 32.0F;
            hudLayoutManagerState3.floatValue11 = 32.0F;
            hudLayoutManagerState3.floatValue12 = 24.0F;
            hudLayoutManagerState3.floatValue13 = 26.0F;
            hudLayoutManagerState3.floatValue = 14.0F;
            hudLayoutManagerState3.floatValue8 = 7.0F;
            hudLayoutManagerState3.floatValue9 = 5.0F;
         } else if ("HUD_ArrayList".equals(string)) {
            hudLayoutManagerState3.floatValue = 15.0F;
            hudLayoutManagerState3.floatValue2 = 15.0F;
            hudLayoutManagerState3.floatValue3 = 15.0F;
            hudLayoutManagerState3.floatValue4 = 15.0F;
            hudLayoutManagerState3.floatValue6 = 15.0F;
            hudLayoutManagerState3.floatValue8 = 4.0F;
            hudLayoutManagerState3.floatValue9 = 0.0F;
            hudLayoutManagerState3.floatValue10 = 0.0F;
            hudLayoutManagerState3.floatValue11 = 32.0F;
         } else if ("HUD_TargetHUD".equals(string)) {
            hudLayoutManagerState3.floatValue = 15.0F;
            hudLayoutManagerState3.floatValue2 = 12.0F;
            hudLayoutManagerState3.floatValue3 = 10.0F;
            hudLayoutManagerState3.floatValue4 = 10.0F;
            hudLayoutManagerState3.floatValue5 = 10.0F;
            hudLayoutManagerState3.floatValue11 = 24.0F;
            hudLayoutManagerState3.floatValue13 = 28.0F;
         } else if ("HUD_HotBar".equals(string) || "hud_armor".equals(string)) {
            hudLayoutManagerState3.floatValue = 10.0F;
            hudLayoutManagerState3.floatValue3 = 5.0F;
            hudLayoutManagerState3.floatValue7 = 4.0F;
            hudLayoutManagerState3.floatValue8 = 5.0F;
            hudLayoutManagerState3.floatValue9 = 3.0F;
         }

         return hudLayoutManagerState3;
      }

      public void invoke() {
         this.floatValue = HudLayoutManager.measure(this.floatValue, 0.0F, 32.0F);
         this.floatValue2 = HudLayoutManager.measure(this.floatValue2, 0.0F, 28.0F);
         this.floatValue3 = HudLayoutManager.measure(this.floatValue3, 0.0F, 24.0F);
         this.floatValue4 = HudLayoutManager.measure(this.floatValue4, 0.0F, 24.0F);
         this.floatValue5 = HudLayoutManager.measure(this.floatValue5, 0.0F, 24.0F);
         this.floatValue6 = HudLayoutManager.measure(this.floatValue6, 0.0F, 22.0F);
         this.floatValue7 = HudLayoutManager.measure(this.floatValue7, 0.0F, 14.0F);
         this.floatValue8 = HudLayoutManager.measure(this.floatValue8, 2.0F, 18.0F);
         this.floatValue9 = HudLayoutManager.measure(this.floatValue9, 0.0F, 18.0F);
         this.floatValue10 = HudLayoutManager.measure(this.floatValue10, 0.0F, 48.0F);
         this.floatValue11 = HudLayoutManager.measure(this.floatValue11, 14.0F, 42.0F);
         this.floatValue12 = HudLayoutManager.measure(this.floatValue12, 14.0F, 38.0F);
         this.floatValue13 = HudLayoutManager.measure(this.floatValue13, 12.0F, 38.0F);
         this.floatValue14 = HudLayoutManager.measure(this.floatValue14, -24.0F, 90.0F);
         this.floatValue15 = HudLayoutManager.measure(this.floatValue15, 0.0F, 7.0F);
         if (this.hudLayoutManagerState3 == null) {
            this.hudLayoutManagerState3 = new HudLayoutManager.HudLayoutManagerState3(17.0F, 29.0F, false);
         }

         if (this.hudLayoutManagerState32 == null) {
            this.hudLayoutManagerState32 = new HudLayoutManager.HudLayoutManagerState3(-34.0F, 29.5F, true);
         }

         if (this.hudLayoutManagerState33 == null) {
            this.hudLayoutManagerState33 = new HudLayoutManager.HudLayoutManagerState3(0.0F, 0.0F, false);
         }

         if (this.hudLayoutManagerState34 == null) {
            this.hudLayoutManagerState34 = new HudLayoutManager.HudLayoutManagerState3(0.0F, 0.0F, false);
         }

         this.hudLayoutManagerState3.floatValue = HudLayoutManager.measure(this.hudLayoutManagerState3.floatValue, -80.0F, 260.0F);
         this.hudLayoutManagerState3.floatValue2 = HudLayoutManager.measure(this.hudLayoutManagerState3.floatValue2, -40.0F, 180.0F);
         this.hudLayoutManagerState32.floatValue = HudLayoutManager.measure(this.hudLayoutManagerState32.floatValue, -220.0F, 80.0F);
         this.hudLayoutManagerState32.floatValue2 = HudLayoutManager.measure(this.hudLayoutManagerState32.floatValue2, -40.0F, 180.0F);
         this.hudLayoutManagerState33.floatValue = HudLayoutManager.measure(this.hudLayoutManagerState33.floatValue, -100.0F, 140.0F);
         this.hudLayoutManagerState33.floatValue2 = HudLayoutManager.measure(this.hudLayoutManagerState33.floatValue2, -60.0F, 140.0F);
         this.hudLayoutManagerState34.floatValue = HudLayoutManager.measure(this.hudLayoutManagerState34.floatValue, -100.0F, 140.0F);
         this.hudLayoutManagerState34.floatValue2 = HudLayoutManager.measure(this.hudLayoutManagerState34.floatValue2, -60.0F, 140.0F);
      }
   }

   static final class HudLayoutManagerState2 {
      public int intValue = 1;
      public HudLayoutManager.HudLayoutManagerState hudLayoutManagerState;
      public Map<String, HudLayoutManager.HudLayoutManagerState> valuesByKey = new HashMap<>();

      void invoke() {
         if (this.valuesByKey == null) {
            this.valuesByKey = new HashMap<>();
         }

         if (this.hudLayoutManagerState != null) {
            this.valuesByKey.putIfAbsent("HUD_HotKeys", this.hudLayoutManagerState);
            this.hudLayoutManagerState = null;
         }

         for (String text4 : HudLayoutManager.HUD_HOTKEYS_2) {
            this.valuesByKey.putIfAbsent(text4, HudLayoutManager.HudLayoutManagerState.resolve2(text4));
         }

         for (HudLayoutManager.HudLayoutManagerState hudLayoutManagerState4 : this.valuesByKey.values()) {
            if (hudLayoutManagerState4 != null) {
               hudLayoutManagerState4.invoke();
            }
         }
      }
   }

   public static final class HudLayoutManagerState3 {
      public float floatValue;
      public float floatValue2;
      public boolean flag;

      public HudLayoutManagerState3() {
      }

      public HudLayoutManagerState3(float f, float g, boolean bl) {
         this.floatValue = f;
         this.floatValue2 = g;
         this.flag = bl;
      }
   }
}
