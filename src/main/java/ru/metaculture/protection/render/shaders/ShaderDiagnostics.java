package ru.metaculture.protection;

import java.util.Locale;

public final class ShaderDiagnostics {
   public static final int INT_VALUE = 3;
   public static final int INT_VALUE_2 = 14;
   private static final String NONE = "none";
   private static final String UNIFORM = "uniform";
   private static final String VARYING = "varying";
   private static final String LINK = "link";
   private static final String COMPILE = "compile";
   private static final String SAMPLER = "sampler";
   private static final String BIND = "bind";

   private ShaderDiagnostics() {
   }

   public static String resolve(String string, int i) {
      return "SHADER FAILURE #" + i + " stage=" + resolve9(string, 96);
   }

   public static String resolve2(int i, Throwable throwable) {
      return throwable == null ? "cause[" + i + "]=unknown" : "cause[" + i + "]=" + throwable.getClass().getName();
   }

   public static String resolve3(Throwable throwable) {
      return throwable == null ? "message=no throwable" : "message=" + resolve9(throwable.getMessage(), 260);
   }

   public static String resolve4(Throwable throwable) {
      String text = resolve8(throwable).toLowerCase(Locale.ROOT);
      if (text.contains("uniform")) {
         return "GLSL DETAIL broken uniform binding/type; check declared name, std140 layout and Java upload type";
      } else if (text.contains("varying") || text.contains("in/out")) {
         return "GLSL DETAIL varying mismatch; check vertex output and fragment input names/types";
      } else if (text.contains("link")) {
         return "GLSL DETAIL program link failed; inspect attached shader interface and sampler layout";
      } else if (text.contains("compile")) {
         return "GLSL DETAIL shader compile failed; inspect syntax, version and include expansion";
      } else {
         return !text.contains("sampler") && !text.contains("bind")
            ? "none"
            : "GLSL DETAIL sampler/binding failure; check texture view lifetime and texture unit isolation";
      }
   }

   public static String resolve5(StackTraceElement stackTraceElement) {
      return stackTraceElement == null
         ? "none"
         : "  at "
            + stackTraceElement.getClassName()
            + "."
            + stackTraceElement.getMethodName()
            + "("
            + stackTraceElement.getFileName()
            + ":"
            + stackTraceElement.getLineNumber()
            + ")";
   }

   public static String resolve6(String string, int i) {
      return "OPENGL ERROR stage="
         + resolve9(string, 96)
         + " code=0x"
         + Integer.toHexString(i).toUpperCase(Locale.ROOT)
         + " name="
         + GlStateInspector.glErrorName(i);
   }

   public static String resolve7() {
      return "GL STATE program=" + GlStateInspector.getCurrentProgram() + " activeTexture=" + GlStateInspector.getActiveTexture() + " texture2D=" + GlStateInspector.getTextureBinding2D();
   }

   private static String resolve8(Throwable throwable) {
      if (throwable == null) {
         return "none";
      } else {
         String text2 = throwable.getMessage();
         return text2 != null && !text2.isBlank() ? text2 : throwable.getClass().getName();
      }
   }

   private static String resolve9(String string, int i) {
      if (string != null && !string.isBlank()) {
         String text3 = string.replace('\n', ' ').replace('\r', ' ').trim();
         return text3.length() <= i ? text3 : text3.substring(0, Math.max(0, i - 3)) + "...";
      } else {
         return "none";
      }
   }
}
