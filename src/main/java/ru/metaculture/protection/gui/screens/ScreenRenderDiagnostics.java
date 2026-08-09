package ru.metaculture.protection;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screen.Screen;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.slf4j.Logger;

public final class ScreenRenderDiagnostics {
   private static final Logger LOGGER = LogUtils.getLogger();
   private static final boolean FLAG = Boolean.parseBoolean(System.getProperty("wild.debug.screenRender.enable", "false"));
   private static final long TIMESTAMP = 2000000000L;
   private static volatile String unknown = "unknown";
   private static volatile boolean flag;
   private static long timestamp;
   private static long timestamp2;

   private ScreenRenderDiagnostics() {
   }

   public static boolean isFLAG() {
      return FLAG;
   }

   public static void invoke(Screen screen, Screen screen2) {
      timestamp2 = 0L;
      timestamp = 0L;
      invoke7();
      LOGGER.info("[ScreenRender] screen={} kind={} from={} gpu={}", new Object[]{resolve2(screen2), resolve3(screen2), resolve2(screen), unknown});
   }

   public static void invoke2(Object object, String string) {
      invoke3(object, string, null);
   }

   public static void invoke3(Object object, String string, String string2) {
      timestamp2++;
      long longValue = System.nanoTime();
      boolean flag = longValue - timestamp >= 2000000000L;
      if (FLAG || flag) {
         timestamp = longValue;
         ScreenRenderDiagnostics.ScreenRenderDiagnosticsData screenRenderDiagnosticsData = resolve();
         if (string2 != null && !string2.isBlank()) {
            LOGGER.info(
               "[ScreenRender] phase={} screen={} kind={} frame={} detail={} gl={}",
               new Object[]{string, resolve2(object), resolve3(object), timestamp2, string2, screenRenderDiagnosticsData}
            );
         } else {
            LOGGER.info(
               "[ScreenRender] phase={} screen={} kind={} frame={} gl={}", new Object[]{string, resolve2(object), resolve3(object), timestamp2, screenRenderDiagnosticsData}
            );
         }
      }
   }

   public static void invoke4(Object object, String string, boolean bl, String string2) {
      if (!bl || FLAG) {
         LOGGER.info(
            "[ScreenRender] backdrop={} success={} screen={} kind={} detail={} gl={}",
            new Object[]{string, bl, resolve2(object), resolve3(object), string2 == null ? "" : string2, resolve()}
         );
      }
   }

   public static void invoke5(Object object, String string, boolean bl, String string2, Throwable throwable) {
      if (!bl || FLAG) {
         if (throwable != null) {
            LOGGER.warn(
               "[ScreenRender] postRender={} success={} screen={} kind={} detail={} gl={}",
               new Object[]{string, false, resolve2(object), resolve3(object), string2 == null ? "" : string2, resolve(), throwable}
            );
         } else {
            LOGGER.info(
               "[ScreenRender] postRender={} success={} screen={} kind={} detail={} gl={}",
               new Object[]{string, bl, resolve2(object), resolve3(object), string2 == null ? "" : string2, resolve()}
            );
         }
      }
   }

   public static void invoke6(String string, Object object, String string2, Throwable throwable) {
      if (throwable != null) {
         LOGGER.warn(
            "[ScreenRender] failure={} screen={} kind={} reason={} gl={}",
            new Object[]{string, resolve2(object), resolve3(object), string2, resolve(), throwable}
         );
      } else {
         LOGGER.warn(
            "[ScreenRender] failure={} screen={} kind={} reason={} gl={}", new Object[]{string, resolve2(object), resolve3(object), string2, resolve()}
         );
      }
   }

   public static ScreenRenderDiagnostics.ScreenRenderDiagnosticsData resolve() {
      try {
         int intValue = GL11.glGetInteger(36006);
         int intValue2 = GL11.glGetInteger(36010);
         int intValue3 = GL11.glGetInteger(35725);
         int[] intValues = new int[4];
         GL11.glGetIntegerv(2978, intValues);
         int intValue4 = intValue == 0 ? '賕' : GL30.glCheckFramebufferStatus(36009);
         boolean flag2 = GL11.glGetBoolean(3107);
         boolean flag3 = GL11.glGetBoolean(3042);
         boolean flag4 = GL11.glGetBoolean(2929);
         boolean flag5 = GL11.glGetBoolean(3089);
         return new ScreenRenderDiagnostics.ScreenRenderDiagnosticsData(intValue, intValue2, intValue3, intValues, intValue4, flag2, flag3, flag4, flag5);
      } catch (Throwable exception) {
         return new ScreenRenderDiagnostics.ScreenRenderDiagnosticsData(-1, -1, -1, new int[4], -1, false, false, false, false);
      }
   }

   private static void invoke7() {
      if (!flag) {
         synchronized (ScreenRenderDiagnostics.class) {
            if (!flag) {
               try {
                  String text = GL11.glGetString(7936);
                  String text2 = GL11.glGetString(7937);
                  String text3 = GL11.glGetString(7938);
                  unknown = "vendor=" + resolve4(text) + " renderer=" + resolve4(text2) + " version=" + resolve4(text3);
               } catch (Throwable exception2) {
                  unknown = "unavailable";
               }

               flag = true;
            }
         }
      }
   }

   private static String resolve2(Object object) {
      return object == null ? "<none>" : object.getClass().getSimpleName();
   }

   private static String resolve3(Object object) {
      if (object == null) {
         return "none";
      } else if (object instanceof BackdropScreen) {
         return "raw-overlay";
      } else if (object instanceof Screen) {
         String text4 = object.getClass().getName();
         if (text4.startsWith("org.wild.")) {
            return "wild-custom";
         } else {
            return text4.startsWith("net.minecraft.") ? "vanilla" : "external";
         }
      } else {
         return "external";
      }
   }

   private static String resolve4(String string) {
      return string == null ? "?" : string.replace('\n', ' ').trim();
   }

   public record ScreenRenderDiagnosticsData(
      int drawFbo, int readFbo, int program, int[] viewport, int drawFramebufferStatus, boolean colorMask, boolean blend, boolean depthTest, boolean scissor
   ) {
      @Override
      public String toString() {
         return "drawFbo="
            + this.drawFbo
            + " readFbo="
            + this.readFbo
            + " program="
            + this.program
            + " viewport="
            + this.viewport[0]
            + "x"
            + this.viewport[1]
            + "+"
            + this.viewport[2]
            + "x"
            + this.viewport[3]
            + " fbStatus=0x"
            + Integer.toHexString(this.drawFramebufferStatus)
            + " colorMask="
            + this.colorMask
            + " blend="
            + this.blend
            + " depth="
            + this.depthTest
            + " scissor="
            + this.scissor;
      }
   }
}
