package ru.metaculture.protection;

public final class RenderAuditPhase {
   public static final int INT_VALUE = 257;
   public static final int INT_VALUE_2 = 258;
   public static final int INT_VALUE_3 = 513;
   public static final int INT_VALUE_4 = 514;
   public static final int INT_VALUE_5 = 769;
   public static final int INT_VALUE_6 = 770;
   public static final int INT_VALUE_7 = 1025;
   public static final int INT_VALUE_8 = 1026;
   public static final int INT_VALUE_9 = 1281;
   public static final int INT_VALUE_10 = 1282;

   private RenderAuditPhase() {
   }

   public static String resolve(int i) {
      return switch (i) {
         case 257 -> "CLIENT_TICK_HEAD";
         case 258 -> "CLIENT_TICK_TAIL";
         case 513 -> "GAME_RENDER_HEAD";
         case 514 -> "GAME_RENDER_TAIL";
         case 769 -> "SCREEN_RENDER_HEAD";
         case 770 -> "SCREEN_RENDER_TAIL";
         case 1025 -> "GUI_RENDER_BEGIN";
         case 1026 -> "GUI_RENDER_END";
         case 1281 -> "SHADER_DRAW_BEGIN";
         case 1282 -> "SHADER_DRAW_END";
         default -> "UNKNOWN_" + Integer.toHexString(i);
      };
   }
}
