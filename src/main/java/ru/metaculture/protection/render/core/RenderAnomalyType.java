package ru.metaculture.protection;

public final class RenderAnomalyType {
   public static final int GL_ERROR = 4097;
   public static final int GL_STATE_LEAK = 4098;
   public static final int PHASE_ORDER = 8193;
   public static final int MATRIX_INVALID = 12289;
   public static final int SNAPSHOT_FAILURE = 16385;
   public static final int MANUAL_SNAPSHOT = 20481;
   public static final int SHADER_EXCEPTION = 24577;

   private RenderAnomalyType() {
   }

   public static String label(int i) {
      return switch (i) {
         case 4097 -> "GL_ERROR";
         case 4098 -> "GL_STATE_LEAK";
         case 8193 -> "PHASE_ORDER";
         case 12289 -> "MATRIX_INVALID";
         case 16385 -> "SNAPSHOT_FAILURE";
         case 20481 -> "MANUAL_SNAPSHOT";
         case 24577 -> "SHADER_EXCEPTION";
         default -> "0x" + Integer.toHexString(i);
      };
   }
}
