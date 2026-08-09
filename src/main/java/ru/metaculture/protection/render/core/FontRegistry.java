package ru.metaculture.protection;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;

public final class FontRegistry {
   private static final Map<String, MsdfFontAtlas> VALUES_BY_KEY = new HashMap<>();
   private static final Map<String, FontObject> VALUES_BY_KEY_2 = new HashMap<>();
   private static final Map<String, FontRegistry.FontRegistryData> VALUES_BY_KEY_3 = new LinkedHashMap<>();
   private static RenderEngine renderEngine;
   private static boolean flag = false;
   private static boolean flag2 = false;
   private static long timestamp;
   public static FontObject fontObject;
   public static FontObject fontObject2;
   public static FontObject fontObject3;
   public static FontObject fontObject4;
   public static FontObject fontObject5;
   public static FontObject fontObject6;
   public static FontObject fontObject7;
   public static FontObject fontObject8;

   private FontRegistry() {
   }

   public static synchronized void invoke(RenderEngine renderEngine, RenderManager renderManager) {
      invoke4(renderEngine);
      Objects.requireNonNull(renderManager, "renderer");
      if (!flag2) {
         renderManager.invoke68(fontObject, resolve2(fontObject));
         renderManager.invoke68(fontObject2, resolve2(fontObject2));
         renderManager.invoke68(fontObject3, resolve2(fontObject3));
         renderManager.invoke68(fontObject4, resolve2(fontObject4));
         renderManager.invoke68(fontObject5, resolve2(fontObject5));
         renderManager.invoke68(fontObject6, resolve2(fontObject6));
         renderManager.invoke68(fontObject7, resolve2(fontObject7));
         renderManager.invoke68(fontObject8, resolve2(fontObject8));
         flag2 = true;
      }
   }

   public static synchronized FontObject resolve(String string, String string2, String string3) {
      invoke6();
      Objects.requireNonNull(string, "id");
      Objects.requireNonNull(string2, "jsonResourcePath");
      Objects.requireNonNull(string3, "textureResourcePath");
      if (VALUES_BY_KEY.containsKey(string)) {
         throw new IllegalStateException("Font already registered: " + string);
      } else {
         MsdfFontAtlas msdfFontAtlas = MsdfFontAtlas.resolve(renderEngine, string2, string3);
         VALUES_BY_KEY.put(string, msdfFontAtlas);
         VALUES_BY_KEY_3.put(string, new FontRegistry.FontRegistryData(string2, string3));
         FontObject fontObject = new FontObject(string);
         VALUES_BY_KEY_2.put(string, fontObject);
         return fontObject;
      }
   }

   public static synchronized void invoke2() {
      if (flag && renderEngine != null) {
         for (Entry entry : VALUES_BY_KEY_3.entrySet()) {
            String text = (String)entry.getKey();
            MsdfFontAtlas msdfFontAtlas2 = VALUES_BY_KEY.get(text);
            if (msdfFontAtlas2 != null) {
               msdfFontAtlas2.invoke(renderEngine);
            } else {
               try {
                  FontRegistry.FontRegistryData fontRegistryData = (FontRegistry.FontRegistryData)entry.getValue();
                  MsdfFontAtlas msdfFontAtlas3 = MsdfFontAtlas.resolve(renderEngine, fontRegistryData.json, fontRegistryData.texture);
                  VALUES_BY_KEY.put(text, msdfFontAtlas3);
               } catch (Throwable exception) {
               }
            }
         }
      }
   }

   public static synchronized void invoke3() {
      if (flag && renderEngine != null) {
         long longValue = System.currentTimeMillis();
         if (longValue - timestamp >= 1000L) {
            timestamp = longValue;

            for (MsdfFontAtlas msdfFontAtlas4 : VALUES_BY_KEY.values()) {
               if (msdfFontAtlas4 != null && !msdfFontAtlas4.check()) {
                  msdfFontAtlas4.invoke(renderEngine);
               }
            }
         }
      }
   }

   public static synchronized FontRenderer resolve2(FontObject fontObject2) {
      invoke6();
      MsdfFontAtlas msdfFontAtlas5 = resolve5(fontObject2);
      return new FontRenderer(renderEngine, msdfFontAtlas5);
   }

   public static synchronized float measure(FontObject fontObject3, int i, float f) {
      invoke6();
      if (fontObject3 != null && !(f <= 0.0F)) {
         MsdfFontAtlas msdfFontAtlas6 = resolve5(fontObject3);
         MsdfFontAtlas.MsdfFontAtlasState msdfFontAtlasState = msdfFontAtlas6.resolve3(i);
         if (msdfFontAtlasState != null && msdfFontAtlasState.flag) {
            float floatValue = Math.max(1.0E-6F, msdfFontAtlas6.getFloatValue2());
            float floatValue2 = f / floatValue;
            return (msdfFontAtlasState.floatValue5 + msdfFontAtlasState.floatValue3) * 0.5F * floatValue2;
         } else {
            return 0.0F;
         }
      } else {
         return 0.0F;
      }
   }

   public static synchronized FontObject resolve3(String string) {
      invoke6();
      FontObject fontObject4 = VALUES_BY_KEY_2.get(string);
      if (fontObject4 == null) {
         throw new IllegalArgumentException("Font not registered: " + string);
      } else {
         return fontObject4;
      }
   }

   public static synchronized FontObject resolve4() {
      invoke6();
      return fontObject8;
   }

   static synchronized MsdfFontAtlas resolve5(FontObject fontObject5) {
      invoke6();
      MsdfFontAtlas msdfFontAtlas7 = VALUES_BY_KEY.get(fontObject5.text);
      if (msdfFontAtlas7 == null) {
         throw new IllegalStateException("Font not registered: " + fontObject5.text);
      } else {
         return msdfFontAtlas7;
      }
   }

   private static void invoke4(RenderEngine renderEngine2) {
      Objects.requireNonNull(renderEngine2, "backend");
      if (flag) {
         if (renderEngine != renderEngine2) {
            throw new IllegalStateException("FontRegistry already initialized with a different backend instance");
         }
      } else {
         renderEngine = renderEngine2;
         flag = true;
         invoke5();
      }
   }

   private static void invoke5() {
      fontObject = resolve("inter_medium", "assets/wild/fonts/medium.json", "assets/wild/fonts/medium.png");
      fontObject2 = resolve("inter_medium_ext", "assets/wild/fonts/Inter_Medium.json", "assets/wild/fonts/Inter_Medium.png");
      fontObject3 = resolve("icons", "assets/wild/fonts/icons.json", "assets/wild/fonts/icons.png");
      fontObject4 = resolve("inter_semibold", "assets/wild/fonts/semibold.json", "assets/wild/fonts/semibold.png");
      fontObject5 = resolve("new_ico", "assets/wild/fonts/new_ico.json", "assets/wild/fonts/new_ico.png");
      fontObject6 = resolve("notifff", "assets/wild/fonts/notifff.json", "assets/wild/fonts/notifff.png");
      fontObject7 = resolve("waypoints", "assets/wild/fonts/waypoint_icons.json", "assets/wild/fonts/waypoint_icons.png");
      fontObject8 = resolve("wild", "assets/wild/fonts/wild.json", "assets/wild/fonts/wildICO.png");
   }

   private static void invoke6() {
      if (!flag || renderEngine == null) {
         throw new IllegalStateException("FontRegistry.initialize(backend, renderer) must be called before use");
      }
   }

   record FontRegistryData(String json, String texture) {
   }
}
