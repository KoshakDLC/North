package ru.metaculture.protection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public final class ClickGuiDragRegistry {
   public static final long TIMESTAMP = 1L;
   public static final long TIMESTAMP_2 = 2L;
   public static final long TIMESTAMP_3 = 3L;
   private static final long TIMESTAMP_4 = 1L;
   private static final long TIMESTAMP_5 = 2L;
   private static final Map<Long, ClickGuiDragRegistry.ClickGuiDragRegistryState> VALUES_BY_KEY = new HashMap<>();
   private static long timestamp;
   private static long timestamp2;
   private static long timestamp3;
   private static float floatValue;

   private ClickGuiDragRegistry() {
   }

   public static void invoke() {
      timestamp++;
      Iterator iterator = VALUES_BY_KEY.entrySet().iterator();

      while (iterator.hasNext()) {
         Entry entry = (Entry)iterator.next();
         if (timestamp - ((ClickGuiDragRegistry.ClickGuiDragRegistryState)entry.getValue()).timestamp > 2L) {
            if (timestamp3 == (Long)entry.getKey()) {
               timestamp3 = 0L;
            }

            iterator.remove();
         }
      }
   }

   public static float measure(long l, float f, float g, float h, float i, float j, float k, float m, float n, float o, ClickGuiDragRegistry.ClickGuiDragRegistryListener clickGuiDragRegistryListener) {
      if (l != 0L && clickGuiDragRegistryListener != null) {
         ClickGuiDragRegistry.ClickGuiDragRegistryState clickGuiDragRegistryState = VALUES_BY_KEY.computeIfAbsent(l, long_ -> new ClickGuiDragRegistry.ClickGuiDragRegistryState());
         long longValue = System.currentTimeMillis();
         float floatValue = clickGuiDragRegistryState.timestamp3 == 0L ? 16.0F : Math.min(80.0F, Math.max(1.0F, (float)(longValue - clickGuiDragRegistryState.timestamp3)));
         clickGuiDragRegistryState.floatValue = f;
         clickGuiDragRegistryState.floatValue2 = g;
         clickGuiDragRegistryState.floatValue3 = h;
         clickGuiDragRegistryState.floatValue4 = i;
         clickGuiDragRegistryState.floatValue5 = j;
         clickGuiDragRegistryState.floatValue6 = k;
         clickGuiDragRegistryState.floatValue7 = Math.max(2.0F, m);
         clickGuiDragRegistryState.clickGuiDragRegistryListener = clickGuiDragRegistryListener;
         clickGuiDragRegistryState.timestamp = timestamp;
         clickGuiDragRegistryState.timestamp2 = ++timestamp2;
         clickGuiDragRegistryState.timestamp3 = longValue;
         boolean flag = timestamp3 == l;
         boolean flag2 = flag || compute(clickGuiDragRegistryState, n, o) != 0;
         float floatValue2 = flag ? 1.0F : (flag2 ? 0.55F : 0.0F);
         float floatValue3 = floatValue2 > clickGuiDragRegistryState.floatValue8 ? 90.0F : 260.0F;
         clickGuiDragRegistryState.floatValue8 = SpringAnimation.measure5(clickGuiDragRegistryState.floatValue8, floatValue2, floatValue, floatValue3);
         return clickGuiDragRegistryState.floatValue8;
      } else {
         return 0.0F;
      }
   }

   public static boolean check(long l) {
      return l != 0L && timestamp3 == l;
   }

   public static boolean check2(float f, float g) {
      long longValue2 = 0L;
      ClickGuiDragRegistry.ClickGuiDragRegistryState clickGuiDragRegistryState2 = null;
      int intValue = 0;

      for (Entry entry2 : VALUES_BY_KEY.entrySet()) {
         ClickGuiDragRegistry.ClickGuiDragRegistryState clickGuiDragRegistryState3 = (ClickGuiDragRegistry.ClickGuiDragRegistryState)entry2.getValue();
         if (check5(clickGuiDragRegistryState3) && clickGuiDragRegistryState3.clickGuiDragRegistryListener != null) {
            int intValue2 = compute(clickGuiDragRegistryState3, f, g);
            if (intValue2 != 0 && (clickGuiDragRegistryState2 == null || clickGuiDragRegistryState3.timestamp2 > clickGuiDragRegistryState2.timestamp2)) {
               clickGuiDragRegistryState2 = clickGuiDragRegistryState3;
               longValue2 = (Long)entry2.getKey();
               intValue = intValue2;
            }
         }
      }

      if (clickGuiDragRegistryState2 == null) {
         return false;
      } else {
         timestamp3 = longValue2;
         if (intValue == 1) {
            floatValue = g - clickGuiDragRegistryState2.floatValue5;
         } else {
            floatValue = clickGuiDragRegistryState2.floatValue6 * 0.5F;
            invoke3(clickGuiDragRegistryState2, g);
         }

         return true;
      }
   }

   public static boolean check3(float f, float g) {
      if (timestamp3 == 0L) {
         return false;
      } else {
         ClickGuiDragRegistry.ClickGuiDragRegistryState clickGuiDragRegistryState4 = VALUES_BY_KEY.get(timestamp3);
         if (clickGuiDragRegistryState4 != null && clickGuiDragRegistryState4.clickGuiDragRegistryListener != null && check5(clickGuiDragRegistryState4)) {
            invoke3(clickGuiDragRegistryState4, g);
            return true;
         } else {
            timestamp3 = 0L;
            return false;
         }
      }
   }

   public static boolean check4() {
      boolean flag3 = timestamp3 != 0L;
      timestamp3 = 0L;
      return flag3;
   }

   public static void invoke2() {
      timestamp3 = 0L;
      timestamp2 = 0L;
      VALUES_BY_KEY.clear();
   }

   private static boolean check5(ClickGuiDragRegistry.ClickGuiDragRegistryState clickGuiDragRegistryState5) {
      return timestamp - clickGuiDragRegistryState5.timestamp <= 1L;
   }

   private static void invoke3(ClickGuiDragRegistry.ClickGuiDragRegistryState clickGuiDragRegistryState6, float f) {
      float floatValue4 = Math.max(1.0F, clickGuiDragRegistryState6.floatValue4 - clickGuiDragRegistryState6.floatValue6);
      float floatValue5 = (f - floatValue - clickGuiDragRegistryState6.floatValue2) / floatValue4;
      clickGuiDragRegistryState6.clickGuiDragRegistryListener.applyRatio(Math.max(0.0F, Math.min(1.0F, floatValue5)));
   }

   private static int compute(ClickGuiDragRegistry.ClickGuiDragRegistryState clickGuiDragRegistryState7, float f, float g) {
      if (!(clickGuiDragRegistryState7.floatValue3 <= 0.0F) && !(clickGuiDragRegistryState7.floatValue4 <= 0.0F)) {
         float floatValue6 = clickGuiDragRegistryState7.floatValue7;
         if (f < clickGuiDragRegistryState7.floatValue - floatValue6 || f > clickGuiDragRegistryState7.floatValue + clickGuiDragRegistryState7.floatValue3 + floatValue6) {
            return 0;
         } else if (!(g < clickGuiDragRegistryState7.floatValue2 - floatValue6 * 0.5F) && !(g > clickGuiDragRegistryState7.floatValue2 + clickGuiDragRegistryState7.floatValue4 + floatValue6 * 0.5F)) {
            float floatValue7 = Math.min(floatValue6, 4.0F);
            return g >= clickGuiDragRegistryState7.floatValue5 - floatValue7 && g <= clickGuiDragRegistryState7.floatValue5 + clickGuiDragRegistryState7.floatValue6 + floatValue7 ? 1 : 2;
         } else {
            return 0;
         }
      } else {
         return 0;
      }
   }

   static final class ClickGuiDragRegistryState {
      float floatValue;
      float floatValue2;
      float floatValue3;
      float floatValue4;
      float floatValue5;
      float floatValue6;
      float floatValue7;
      ClickGuiDragRegistry.ClickGuiDragRegistryListener clickGuiDragRegistryListener;
      long timestamp;
      long timestamp2;
      long timestamp3;
      float floatValue8;
   }

   @FunctionalInterface
   public interface ClickGuiDragRegistryListener {
      void applyRatio(float f);
   }
}
